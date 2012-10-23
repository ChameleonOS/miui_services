// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.*;
import android.util.*;
import com.android.internal.app.HeavyWeightSwitcherActivity;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.ProcessStats;
import com.android.server.ProcessMap;
import com.android.server.wm.WindowManagerService;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ActivityRecord, ActivityManagerService, BatteryStatsService, ProcessRecord, 
//            TaskRecord, PendingIntentRecord, ConnectionRecord, TaskAccessInfo, 
//            ThumbnailHolder

final class ActivityStack {
    static class ScheduleDestroyArgs {

        final boolean mOomAdj;
        final ProcessRecord mOwner;
        final String mReason;

        ScheduleDestroyArgs(ProcessRecord processrecord, boolean flag, String s) {
            mOwner = processrecord;
            mOomAdj = flag;
            mReason = s;
        }
    }

    static final class ActivityState extends Enum {

        public static ActivityState valueOf(String s) {
            return (ActivityState)Enum.valueOf(com/android/server/am/ActivityStack$ActivityState, s);
        }

        public static ActivityState[] values() {
            return (ActivityState[])$VALUES.clone();
        }

        private static final ActivityState $VALUES[];
        public static final ActivityState DESTROYED;
        public static final ActivityState DESTROYING;
        public static final ActivityState FINISHING;
        public static final ActivityState INITIALIZING;
        public static final ActivityState PAUSED;
        public static final ActivityState PAUSING;
        public static final ActivityState RESUMED;
        public static final ActivityState STOPPED;
        public static final ActivityState STOPPING;

        static  {
            INITIALIZING = new ActivityState("INITIALIZING", 0);
            RESUMED = new ActivityState("RESUMED", 1);
            PAUSING = new ActivityState("PAUSING", 2);
            PAUSED = new ActivityState("PAUSED", 3);
            STOPPING = new ActivityState("STOPPING", 4);
            STOPPED = new ActivityState("STOPPED", 5);
            FINISHING = new ActivityState("FINISHING", 6);
            DESTROYING = new ActivityState("DESTROYING", 7);
            DESTROYED = new ActivityState("DESTROYED", 8);
            ActivityState aactivitystate[] = new ActivityState[9];
            aactivitystate[0] = INITIALIZING;
            aactivitystate[1] = RESUMED;
            aactivitystate[2] = PAUSING;
            aactivitystate[3] = PAUSED;
            aactivitystate[4] = STOPPING;
            aactivitystate[5] = STOPPED;
            aactivitystate[6] = FINISHING;
            aactivitystate[7] = DESTROYING;
            aactivitystate[8] = DESTROYED;
            $VALUES = aactivitystate;
        }

        private ActivityState(String s, int i) {
            super(s, i);
        }
    }


    ActivityStack(ActivityManagerService activitymanagerservice, Context context, boolean flag) {
        mPausingActivity = null;
        mLastPausedActivity = null;
        mResumedActivity = null;
        mLastStartedActivity = null;
        mUserLeaving = false;
        mInitialStartTime = 0L;
        mSleepTimeout = false;
        mDismissKeyguardOnNextActivity = false;
        mThumbnailWidth = -1;
        mThumbnailHeight = -1;
        mService = activitymanagerservice;
        mContext = context;
        mMainStack = flag;
        PowerManager powermanager = (PowerManager)context.getSystemService("power");
        mGoingToSleep = powermanager.newWakeLock(1, "ActivityManager-Sleep");
        mLaunchingActivity = powermanager.newWakeLock(1, "ActivityManager-Launch");
        mLaunchingActivity.setReferenceCounted(false);
    }

    private final void completePauseLocked() {
        ActivityRecord activityrecord = mPausingActivity;
        if(activityrecord == null) goto _L2; else goto _L1
_L1:
        if(activityrecord.finishing)
            activityrecord = finishCurrentActivityLocked(activityrecord, 2);
        else
        if(activityrecord.app != null) {
            if(activityrecord.waitingVisible) {
                activityrecord.waitingVisible = false;
                mWaitingVisibleActivities.remove(activityrecord);
            }
            if(activityrecord.configDestroy) {
                destroyActivityLocked(activityrecord, true, false, "pause-config");
            } else {
                mStoppingActivities.add(activityrecord);
                if(mStoppingActivities.size() > 3)
                    scheduleIdleLocked();
                else
                    checkReadyForSleepLocked();
            }
        } else {
            activityrecord = null;
        }
_L8:
        mPausingActivity = null;
_L2:
        if(mService.isSleeping()) goto _L4; else goto _L3
_L3:
        resumeTopActivityLocked(activityrecord);
_L6:
        if(activityrecord != null)
            activityrecord.resumeKeyDispatchingLocked();
        if(activityrecord.app != null && activityrecord.cpuTimeAtResume > 0L && mService.mBatteryStatsService.isOnBattery()) {
            long l;
            synchronized(mService.mProcessStatsThread) {
                l = mService.mProcessStats.getCpuTimeForPid(activityrecord.app.pid) - activityrecord.cpuTimeAtResume;
            }
            if(l > 0L)
                synchronized(mService.mBatteryStatsService.getActiveStatistics()) {
                    com.android.internal.os.BatteryStatsImpl.Uid.Proc proc = batterystatsimpl.getProcessStatsLocked(activityrecord.info.applicationInfo.uid, ((ComponentInfo) (activityrecord.info)).packageName);
                    if(proc != null)
                        proc.addForegroundTimeLocked(l);
                }
        }
        activityrecord.cpuTimeAtResume = 0L;
        return;
_L4:
        checkReadyForSleepLocked();
        if(topRunningActivityLocked(null) == null)
            resumeTopActivityLocked(null);
        if(true) goto _L6; else goto _L5
_L5:
        exception;
        thread;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final void completeResumeLocked(ActivityRecord activityrecord) {
        activityrecord.idle = false;
        activityrecord.results = null;
        activityrecord.newIntents = null;
        Message message = mHandler.obtainMessage(102);
        message.obj = activityrecord;
        mHandler.sendMessageDelayed(message, 10000L);
        if(mMainStack)
            mService.reportResumedActivityLocked(activityrecord);
        activityrecord.clearThumbnail();
        if(mMainStack)
            mService.setFocusedActivityLocked(activityrecord);
        activityrecord.resumeKeyDispatchingLocked();
        ensureActivitiesVisibleLocked(null, 0);
        mService.mWindowManager.executeAppTransition();
        mNoAnimActivities.clear();
        if(activityrecord.app == null)
            break MISSING_BLOCK_LABEL_157;
        Thread thread = mService.mProcessStatsThread;
        thread;
        JVM INSTR monitorenter ;
        activityrecord.cpuTimeAtResume = mService.mProcessStats.getCpuTimeForPid(activityrecord.app.pid);
        break MISSING_BLOCK_LABEL_162;
        activityrecord.cpuTimeAtResume = 0L;
    }

    private final int findActivityInHistoryLocked(ActivityRecord activityrecord, int i) {
        int j = mHistory.size();
_L2:
        int k;
        ActivityRecord activityrecord1;
        do {
            if(j <= 0)
                break;
            j--;
            activityrecord1 = (ActivityRecord)mHistory.get(j);
            if(activityrecord1.finishing)
                continue;
            if(activityrecord1.task.taskId == i)
                continue; /* Loop/switch isn't completed */
            break;
        } while(true);
        k = -1;
_L3:
        return k;
        if(!activityrecord1.realActivity.equals(activityrecord.realActivity)) goto _L2; else goto _L1
_L1:
        k = j;
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private ActivityRecord findActivityLocked(Intent intent, ActivityInfo activityinfo) {
        ComponentName componentname;
        int i;
        int j;
        componentname = intent.getComponent();
        if(activityinfo.targetActivity != null)
            componentname = new ComponentName(((ComponentInfo) (activityinfo)).packageName, activityinfo.targetActivity);
        i = UserId.getUserId(activityinfo.applicationInfo.uid);
        j = -1 + mHistory.size();
_L3:
        ActivityRecord activityrecord;
        if(j < 0)
            break MISSING_BLOCK_LABEL_113;
        activityrecord = (ActivityRecord)mHistory.get(j);
        if(activityrecord.finishing || !activityrecord.intent.getComponent().equals(componentname) || activityrecord.userId != i) goto _L2; else goto _L1
_L1:
        return activityrecord;
_L2:
        j--;
          goto _L3
        activityrecord = null;
          goto _L1
    }

    private ActivityRecord findTaskLocked(Intent intent, ActivityInfo activityinfo) {
        ComponentName componentname;
        TaskRecord taskrecord;
        int i;
        int j;
        componentname = intent.getComponent();
        if(activityinfo.targetActivity != null)
            componentname = new ComponentName(((ComponentInfo) (activityinfo)).packageName, activityinfo.targetActivity);
        taskrecord = null;
        i = UserId.getUserId(activityinfo.applicationInfo.uid);
        j = -1 + mHistory.size();
_L3:
        if(j < 0) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord;
        activityrecord = (ActivityRecord)mHistory.get(j);
        if(activityrecord.finishing || activityrecord.task == taskrecord || activityrecord.userId != i || activityrecord.launchMode == 3)
            continue; /* Loop/switch isn't completed */
        taskrecord = activityrecord.task;
        if(activityrecord.task.affinity == null ? (activityrecord.task.intent == null || !activityrecord.task.intent.getComponent().equals(componentname)) && (activityrecord.task.affinityIntent == null || !activityrecord.task.affinityIntent.getComponent().equals(componentname)) : !activityrecord.task.affinity.equals(activityinfo.taskAffinity))
            continue; /* Loop/switch isn't completed */
_L4:
        return activityrecord;
        j--;
          goto _L3
_L2:
        activityrecord = null;
          goto _L4
    }

    private final ActivityRecord finishCurrentActivityLocked(ActivityRecord activityrecord, int i) {
        int j = indexOfActivityLocked(activityrecord);
        ActivityRecord activityrecord1;
        if(j < 0)
            activityrecord1 = null;
        else
            activityrecord1 = finishCurrentActivityLocked(activityrecord, j, i);
        return activityrecord1;
    }

    private final ActivityRecord finishCurrentActivityLocked(ActivityRecord activityrecord, int i, int j) {
        if(j != 2 || !activityrecord.nowVisible) goto _L2; else goto _L1
_L1:
        if(!mStoppingActivities.contains(activityrecord)) {
            mStoppingActivities.add(activityrecord);
            if(mStoppingActivities.size() > 3)
                scheduleIdleLocked();
            else
                checkReadyForSleepLocked();
        }
        activityrecord.state = ActivityState.STOPPING;
        mService.updateOomAdjLocked();
_L4:
        return activityrecord;
_L2:
        mStoppingActivities.remove(activityrecord);
        mGoingToSleepActivities.remove(activityrecord);
        mWaitingVisibleActivities.remove(activityrecord);
        if(mResumedActivity == activityrecord)
            mResumedActivity = null;
        ActivityState activitystate = activityrecord.state;
        activityrecord.state = ActivityState.FINISHING;
        if(j == 0 || activitystate == ActivityState.STOPPED || activitystate == ActivityState.INITIALIZING) {
            boolean flag = destroyActivityLocked(activityrecord, true, true, "finish-imm");
            if(flag)
                resumeTopActivityLocked(null);
            if(flag)
                activityrecord = null;
        } else {
            mFinishingActivities.add(activityrecord);
            resumeTopActivityLocked(null);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private final void finishTaskMoveLocked(int i) {
        resumeTopActivityLocked(null);
    }

    private final void logStartActivity(int i, ActivityRecord activityrecord, TaskRecord taskrecord) {
        Object aobj[] = new Object[7];
        aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
        aobj[1] = Integer.valueOf(taskrecord.taskId);
        aobj[2] = activityrecord.shortComponentName;
        aobj[3] = activityrecord.intent.getAction();
        aobj[4] = activityrecord.intent.getType();
        aobj[5] = activityrecord.intent.getDataString();
        aobj[6] = Integer.valueOf(activityrecord.intent.getFlags());
        EventLog.writeEvent(i, aobj);
    }

    private final ActivityRecord moveActivityToFrontLocked(int i) {
        ActivityRecord activityrecord = (ActivityRecord)mHistory.remove(i);
        int j = mHistory.size();
        ActivityRecord activityrecord1 = (ActivityRecord)mHistory.get(j - 1);
        mHistory.add(j, activityrecord);
        activityrecord1.frontOfTask = false;
        activityrecord.frontOfTask = true;
        return activityrecord;
    }

    private final void performClearTaskAtIndexLocked(int i, int j) {
        do {
            ActivityRecord activityrecord;
label0:
            {
                if(j < mHistory.size()) {
                    activityrecord = (ActivityRecord)mHistory.get(j);
                    if(activityrecord.task.taskId == i)
                        break label0;
                }
                return;
            }
            if(activityrecord.finishing)
                j++;
            else
            if(!finishActivityLocked(activityrecord, j, 0, null, "clear"))
                j++;
        } while(true);
    }

    private final ActivityRecord performClearTaskLocked(int i, ActivityRecord activityrecord, int j) {
        ActivityRecord activityrecord1;
        int k;
label0:
        {
            activityrecord1 = null;
            k = mHistory.size();
            do {
                if(k <= 0)
                    break label0;
                k--;
            } while(((ActivityRecord)mHistory.get(k)).task.taskId != i);
            k++;
        }
_L2:
        ActivityRecord activityrecord2;
label1:
        {
            do {
                if(k <= 0)
                    break label1;
                k--;
                activityrecord2 = (ActivityRecord)mHistory.get(k);
            } while(activityrecord2.finishing);
            if(activityrecord2.task.taskId == i)
                continue; /* Loop/switch isn't completed */
        }
_L3:
        return activityrecord1;
        if(!activityrecord2.realActivity.equals(activityrecord.realActivity)) goto _L2; else goto _L1
_L1:
label2:
        {
            ActivityRecord activityrecord3;
            if(k < -1 + mHistory.size()) {
                k++;
                activityrecord3 = (ActivityRecord)mHistory.get(k);
                if(activityrecord3.task.taskId == i)
                    break label2;
            }
            if(activityrecord2.launchMode == 0 && (0x20000000 & j) == 0 && !activityrecord2.finishing) {
                int l = indexOfTokenLocked(activityrecord2.appToken);
                if(l >= 0)
                    finishActivityLocked(activityrecord2, l, 0, null, "clear");
            } else {
                activityrecord1 = activityrecord2;
            }
        }
          goto _L3
        if(!activityrecord3.finishing && finishActivityLocked(activityrecord3, k, 0, null, "clear"))
            k--;
          goto _L1
    }

    private final void performClearTaskLocked(int i) {
        int j = mHistory.size();
        do {
            if(j <= 0)
                break;
            j--;
            if(((ActivityRecord)mHistory.get(j)).task.taskId != i)
                continue;
            j++;
            break;
        } while(true);
        do {
            if(j <= 0)
                break;
            j--;
            ActivityRecord activityrecord = (ActivityRecord)mHistory.get(j);
            if(activityrecord.finishing || activityrecord.task.taskId == i)
                continue;
            performClearTaskAtIndexLocked(i, j + 1);
            break;
        } while(true);
    }

    private final boolean relaunchActivityLocked(ActivityRecord activityrecord, int i, boolean flag) {
        boolean flag1 = false;
        ArrayList arraylist = null;
        ArrayList arraylist1 = null;
        if(flag) {
            arraylist = activityrecord.results;
            arraylist1 = activityrecord.newIntents;
        }
        char c;
        Object aobj[];
        if(flag)
            c = '\u7543';
        else
            c = '\u7544';
        aobj = new Object[3];
        aobj[flag1] = Integer.valueOf(System.identityHashCode(activityrecord));
        aobj[1] = Integer.valueOf(activityrecord.task.taskId);
        aobj[2] = activityrecord.shortComponentName;
        EventLog.writeEvent(c, aobj);
        activityrecord.startFreezingScreenLocked(activityrecord.app, 0);
        try {
            activityrecord.forceNewConfig = false;
            IApplicationThread iapplicationthread = activityrecord.app.thread;
            android.view.IApplicationToken.Stub stub = activityrecord.appToken;
            if(!flag)
                flag1 = true;
            iapplicationthread.scheduleRelaunchActivity(stub, arraylist, arraylist1, i, flag1, new Configuration(mService.mConfiguration));
        }
        catch(RemoteException remoteexception) { }
        if(flag) {
            activityrecord.results = null;
            activityrecord.newIntents = null;
            if(mMainStack)
                mService.reportResumedActivityLocked(activityrecord);
            activityrecord.state = ActivityState.RESUMED;
        } else {
            mHandler.removeMessages(101, activityrecord);
            activityrecord.state = ActivityState.PAUSED;
        }
        return true;
    }

    private void removeHistoryRecordsForAppLocked(ArrayList arraylist, ProcessRecord processrecord) {
        int i = arraylist.size();
        do {
            if(i <= 0)
                break;
            i--;
            ActivityRecord activityrecord = (ActivityRecord)arraylist.get(i);
            if(activityrecord.app == processrecord) {
                arraylist.remove(i);
                removeTimeoutsForActivityLocked(activityrecord);
            }
        } while(true);
    }

    private void removeTimeoutsForActivityLocked(ActivityRecord activityrecord) {
        mHandler.removeMessages(101, activityrecord);
        mHandler.removeMessages(108, activityrecord);
        mHandler.removeMessages(102, activityrecord);
        mHandler.removeMessages(105, activityrecord);
        activityrecord.finishLaunchTickingLocked();
    }

    private final ActivityRecord resetTaskIfNeededLocked(ActivityRecord activityrecord, ActivityRecord activityrecord1) {
        boolean flag;
        TaskRecord taskrecord;
        ActivityRecord activityrecord2;
        int i;
        int j;
        int k;
        int l;
        int i1;
        ActivityRecord activityrecord3;
        if((4 & activityrecord1.info.flags) != 0)
            flag = true;
        else
            flag = false;
        taskrecord = activityrecord.task;
        activityrecord2 = null;
        i = 0;
        j = -1;
        k = -1;
        l = -1;
        i1 = -1 + mHistory.size();
        if(i1 < -1) goto _L2; else goto _L1
_L1:
        if(i1 >= 0)
            activityrecord3 = (ActivityRecord)mHistory.get(i1);
        else
            activityrecord3 = null;
        if(activityrecord3 == null || !activityrecord3.finishing) goto _L4; else goto _L3
_L3:
        i1--;
        break MISSING_BLOCK_LABEL_50;
_L4:
        if(activityrecord3 == null || activityrecord3.userId == activityrecord.userId) goto _L5; else goto _L2
_L2:
        return activityrecord;
_L5:
label0:
        {
            if(activityrecord2 != null)
                break label0;
            activityrecord2 = activityrecord3;
            i = i1;
            k = -1;
        }
        continue; /* Loop/switch isn't completed */
        boolean flag1;
        boolean flag2;
        int j1 = activityrecord2.info.flags;
        if((j1 & 2) != 0)
            flag1 = true;
        else
            flag1 = false;
        if((j1 & 0x40) != 0)
            flag2 = true;
        else
            flag2 = false;
        if(activityrecord2.task == taskrecord) {
            if(j < 0)
                j = i;
            boolean flag3;
            ActivityRecord activityrecord7;
            int j2;
            int k2;
            int l2;
            if(activityrecord3 != null && activityrecord3.task == taskrecord) {
                if((0x80000 & activityrecord2.intent.getFlags()) != 0)
                    flag3 = true;
                else
                    flag3 = false;
                if(!flag1 && !flag3 && activityrecord2.resultTo != null) {
                    if(k < 0)
                        k = i;
                } else {
label1:
                    {
                        if(flag1 || flag3 || !flag2 || activityrecord2.taskAffinity == null || activityrecord2.taskAffinity.equals(taskrecord.affinity))
                            break label1;
                        ActivityRecord activityrecord8 = (ActivityRecord)mHistory.get(0);
                        int i3;
                        ThumbnailHolder thumbnailholder;
                        int j3;
                        if(activityrecord2.taskAffinity != null && activityrecord2.taskAffinity.equals(activityrecord8.task.affinity)) {
                            TaskRecord taskrecord2 = activityrecord8.task;
                            ThumbnailHolder thumbnailholder1 = activityrecord8.thumbHolder;
                            activityrecord2.setTask(taskrecord2, thumbnailholder1, false);
                        } else {
                            ActivityManagerService activitymanagerservice = mService;
                            activitymanagerservice.mCurTask = 1 + activitymanagerservice.mCurTask;
                            if(mService.mCurTask <= 0)
                                mService.mCurTask = 1;
                            TaskRecord taskrecord1 = new TaskRecord(mService.mCurTask, activityrecord2.info, null);
                            activityrecord2.setTask(taskrecord1, null, false);
                            activityrecord2.task.affinityIntent = activityrecord2.intent;
                        }
                        mService.mWindowManager.setAppGroupId(activityrecord2.appToken, taskrecord.taskId);
                        if(k < 0)
                            k = i;
                        i3 = 0;
                        thumbnailholder = activityrecord2.thumbHolder;
                        j3 = i;
                        while(j3 <= k)  {
                            activityrecord8 = (ActivityRecord)mHistory.get(j3);
                            if(!activityrecord8.finishing) {
                                activityrecord8.setTask(activityrecord2.task, thumbnailholder, false);
                                thumbnailholder = activityrecord8.thumbHolder;
                                mHistory.remove(j3);
                                mHistory.add(i3, activityrecord8);
                                mService.mWindowManager.moveAppToken(i3, activityrecord8.appToken);
                                mService.mWindowManager.setAppGroupId(activityrecord8.appToken, activityrecord8.task.taskId);
                                i3++;
                                i1++;
                            }
                            j3++;
                        }
                        if(activityrecord == activityrecord8)
                            activityrecord = activityrecord3;
                        if(j == k)
                            j = -1;
                        k = -1;
                    }
                }
            } else {
                k = -1;
            }
        } else {
label2:
            {
                if(activityrecord2.resultTo == null || activityrecord3 != null && activityrecord3.task != activityrecord2.task)
                    break label2;
                if(k < 0)
                    k = i;
            }
        }
_L9:
        activityrecord2 = activityrecord3;
        i = i1;
        if(true) goto _L3; else goto _L6
_L6:
        if(!flag && !flag1 && !flag3)
            break MISSING_BLOCK_LABEL_844;
        if(flag3) {
            k2 = i + 1;
            do {
                l2 = mHistory.size();
                if(k2 >= l2 || ((ActivityRecord)mHistory.get(k2)).task != taskrecord)
                    break;
                k2++;
            } while(true);
            k = k2 - 1;
        } else
        if(k < 0)
            k = i;
        activityrecord7 = null;
        j2 = i;
        if(j2 > k)
            break; /* Loop/switch isn't completed */
        activityrecord7 = (ActivityRecord)mHistory.get(j2);
        if(!activityrecord7.finishing && finishActivityLocked(activityrecord7, j2, 0, null, "reset")) {
            k--;
            j2--;
        }
        j2++;
        if(true) goto _L8; else goto _L7
_L8:
        break MISSING_BLOCK_LABEL_745;
_L7:
        if(activityrecord == activityrecord7)
            activityrecord = activityrecord3;
        if(j == k)
            j = -1;
        k = -1;
        break MISSING_BLOCK_LABEL_255;
        k = -1;
        break MISSING_BLOCK_LABEL_255;
label3:
        {
            if(j < 0 || !flag2 || taskrecord.affinity == null || !taskrecord.affinity.equals(activityrecord2.taskAffinity))
                break MISSING_BLOCK_LABEL_1274;
            if(!flag && !flag1)
                break label3;
            if(k < 0)
                k = i;
            int k1 = i;
            do {
                if(k1 > k)
                    break;
                ActivityRecord activityrecord4 = (ActivityRecord)mHistory.get(k1);
                if(!activityrecord4.finishing && finishActivityLocked(activityrecord4, k1, 0, null, "reset")) {
                    j--;
                    l--;
                    k--;
                    k1--;
                }
                k1++;
            } while(true);
            k = -1;
        }
          goto _L9
        if(k < 0)
            k = i;
        int l1 = k;
        while(l1 >= i)  {
            ActivityRecord activityrecord6 = (ActivityRecord)mHistory.get(l1);
            if(!activityrecord6.finishing) {
                WindowManagerService windowmanagerservice;
                android.view.IApplicationToken.Stub stub;
                if(l < 0) {
                    l = j;
                    activityrecord = activityrecord6;
                } else {
                    l--;
                }
                mHistory.remove(l1);
                activityrecord6.setTask(taskrecord, null, false);
                mHistory.add(l, activityrecord6);
                windowmanagerservice = mService.mWindowManager;
                stub = activityrecord6.appToken;
                windowmanagerservice.moveAppToken(l, stub);
                mService.mWindowManager.setAppGroupId(activityrecord6.appToken, activityrecord6.task.taskId);
            }
            l1--;
        }
        k = -1;
        if(activityrecord2.info.launchMode == 1) {
            int i2 = l - 1;
            while(i2 >= 0)  {
                ActivityRecord activityrecord5 = (ActivityRecord)mHistory.get(i2);
                if(!activityrecord5.finishing && activityrecord5.intent.getComponent().equals(activityrecord2.intent.getComponent()) && finishActivityLocked(activityrecord5, i2, 0, null, "replace")) {
                    j--;
                    l--;
                }
                i2--;
            }
        }
          goto _L9
        if(activityrecord3 != null && activityrecord3.task != activityrecord2.task)
            k = -1;
          goto _L9
    }

    private final void startActivityLocked(ActivityRecord activityrecord, boolean flag, boolean flag1, boolean flag2, Bundle bundle) {
        int i;
        int j;
        boolean flag5;
        int k1;
        i = mHistory.size();
        j = -1;
        if(flag)
            break MISSING_BLOCK_LABEL_150;
        flag5 = true;
        k1 = i - 1;
_L2:
        ActivityRecord activityrecord2;
        if(k1 < 0)
            break MISSING_BLOCK_LABEL_150;
        activityrecord2 = (ActivityRecord)mHistory.get(k1);
        if(!activityrecord2.finishing)
            break; /* Loop/switch isn't completed */
_L5:
        k1--;
        if(true) goto _L2; else goto _L1
_L1:
        if(activityrecord2.task != activityrecord.task) goto _L4; else goto _L3
_L3:
        j = k1 + 1;
        if(flag5)
            break MISSING_BLOCK_LABEL_150;
        mHistory.add(j, activityrecord);
        activityrecord.putInHistory();
        mService.mWindowManager.addAppToken(j, activityrecord.appToken, activityrecord.task.taskId, activityrecord.info.screenOrientation, activityrecord.fullscreen);
        ActivityOptions.abort(bundle);
_L6:
        return;
_L4:
        if(activityrecord2.fullscreen)
            flag5 = false;
          goto _L5
        if(j < 0)
            j = i;
        if(j < i)
            mUserLeaving = false;
        mHistory.add(j, activityrecord);
        activityrecord.putInHistory();
        activityrecord.frontOfTask = flag;
        if(i > 0) {
            boolean flag3 = flag;
            ProcessRecord processrecord = activityrecord.app;
            if(processrecord == null)
                processrecord = (ProcessRecord)mService.mProcessNames.get(activityrecord.processName, activityrecord.info.applicationInfo.uid);
            if(processrecord == null || processrecord.thread == null)
                flag3 = true;
            boolean flag4;
            if((0x10000 & activityrecord.intent.getFlags()) != 0) {
                mService.mWindowManager.prepareAppTransition(0, flag2);
                mNoAnimActivities.add(activityrecord);
            } else {
                WindowManagerService windowmanagerservice = mService.mWindowManager;
                char c;
                if(flag)
                    c = '\u1008';
                else
                    c = '\u1006';
                windowmanagerservice.prepareAppTransition(c, flag2);
                mNoAnimActivities.remove(activityrecord);
            }
            activityrecord.updateOptionsLocked(bundle);
            mService.mWindowManager.addAppToken(j, activityrecord.appToken, activityrecord.task.taskId, activityrecord.info.screenOrientation, activityrecord.fullscreen);
            flag4 = true;
            if(flag && (0x200000 & activityrecord.intent.getFlags()) != 0) {
                resetTaskIfNeededLocked(activityrecord, activityrecord);
                WindowManagerService windowmanagerservice1;
                android.view.IApplicationToken.Stub stub;
                String s;
                int k;
                android.content.res.CompatibilityInfo compatibilityinfo;
                CharSequence charsequence;
                int l;
                int i1;
                int j1;
                if(topRunningNonDelayedActivityLocked(null) == activityrecord)
                    flag4 = true;
                else
                    flag4 = false;
            }
            if(flag4) {
                ActivityRecord activityrecord1 = mResumedActivity;
                android.view.IApplicationToken.Stub stub1;
                if(activityrecord1 != null)
                    if(activityrecord1.task != activityrecord.task)
                        activityrecord1 = null;
                    else
                    if(activityrecord1.nowVisible)
                        activityrecord1 = null;
                windowmanagerservice1 = mService.mWindowManager;
                stub = activityrecord.appToken;
                s = activityrecord.packageName;
                k = activityrecord.theme;
                compatibilityinfo = mService.compatibilityInfoForPackageLocked(activityrecord.info.applicationInfo);
                charsequence = activityrecord.nonLocalizedLabel;
                l = activityrecord.labelRes;
                i1 = activityrecord.icon;
                j1 = activityrecord.windowFlags;
                if(activityrecord1 != null)
                    stub1 = activityrecord1.appToken;
                else
                    stub1 = null;
                windowmanagerservice1.setAppStartingWindow(stub, s, k, compatibilityinfo, charsequence, l, i1, j1, stub1, flag3);
            }
        } else {
            mService.mWindowManager.addAppToken(j, activityrecord.appToken, activityrecord.task.taskId, activityrecord.info.screenOrientation, activityrecord.fullscreen);
            ActivityOptions.abort(bundle);
        }
        if(flag1)
            resumeTopActivityLocked(null);
          goto _L6
    }

    private final void startPausingLocked(boolean flag, boolean flag1) {
        ActivityRecord activityrecord;
        if(mPausingActivity != null) {
            RuntimeException runtimeexception = new RuntimeException();
            Slog.e("ActivityManager", (new StringBuilder()).append("Trying to pause when pause is already pending for ").append(mPausingActivity).toString(), runtimeexception);
        }
        activityrecord = mResumedActivity;
        if(activityrecord != null) goto _L2; else goto _L1
_L1:
        Slog.e("ActivityManager", "Trying to pause when nothing is resumed", new RuntimeException());
        resumeTopActivityLocked(null);
_L5:
        return;
_L2:
        mResumedActivity = null;
        mPausingActivity = activityrecord;
        mLastPausedActivity = activityrecord;
        activityrecord.state = ActivityState.PAUSING;
        activityrecord.task.touchActiveTime();
        activityrecord.updateThumbnail(screenshotActivities(activityrecord), null);
        mService.updateCpuStats();
        if(activityrecord.app == null || activityrecord.app.thread == null) goto _L4; else goto _L3
_L3:
        try {
            Object aobj[] = new Object[2];
            aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
            aobj[1] = activityrecord.shortComponentName;
            EventLog.writeEvent(30013, aobj);
            activityrecord.app.thread.schedulePauseActivity(activityrecord.appToken, activityrecord.finishing, flag, activityrecord.configChangeFlags);
            if(mMainStack)
                mService.updateUsageStats(activityrecord, false);
        }
        catch(Exception exception) {
            Slog.w("ActivityManager", "Exception thrown during pause", exception);
            mPausingActivity = null;
            mLastPausedActivity = null;
        }
_L6:
        if(!mService.mSleeping && !mService.mShuttingDown) {
            mLaunchingActivity.acquire();
            if(!mHandler.hasMessages(104)) {
                Message message1 = mHandler.obtainMessage(104);
                mHandler.sendMessageDelayed(message1, 10000L);
            }
        }
        if(mPausingActivity != null) {
            if(!flag1)
                activityrecord.pauseKeyDispatchingLocked();
            Message message = mHandler.obtainMessage(101);
            message.obj = activityrecord;
            activityrecord.pauseTime = SystemClock.uptimeMillis();
            mHandler.sendMessageDelayed(message, 500L);
        } else {
            resumeTopActivityLocked(null);
        }
        if(true) goto _L5; else goto _L4
_L4:
        mPausingActivity = null;
        mLastPausedActivity = null;
          goto _L6
    }

    private final void startSpecificActivityLocked(ActivityRecord activityrecord, boolean flag, boolean flag1) {
        ProcessRecord processrecord = mService.getProcessRecordLocked(activityrecord.processName, activityrecord.info.applicationInfo.uid);
        if(activityrecord.launchTime == 0L) {
            activityrecord.launchTime = SystemClock.uptimeMillis();
            if(mInitialStartTime == 0L)
                mInitialStartTime = activityrecord.launchTime;
        } else
        if(mInitialStartTime == 0L)
            mInitialStartTime = SystemClock.uptimeMillis();
        if(processrecord == null || processrecord.thread == null)
            break MISSING_BLOCK_LABEL_151;
        processrecord.addPackage(((ComponentInfo) (activityrecord.info)).packageName);
        realStartActivityLocked(activityrecord, processrecord, flag, flag1);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception when starting activity ").append(activityrecord.intent.getComponent().flattenToShortString()).toString(), remoteexception);
        mService.startProcessLocked(activityrecord.processName, activityrecord.info.applicationInfo, true, 0, "activity", activityrecord.intent.getComponent(), false, false);
          goto _L1
    }

    private final void stopActivityLocked(ActivityRecord activityrecord) {
        if(((0x40000000 & activityrecord.intent.getFlags()) != 0 || (0x80 & activityrecord.info.flags) != 0) && !activityrecord.finishing && !mService.mSleeping)
            requestFinishActivityLocked(activityrecord.appToken, 0, null, "no-history");
        if(activityrecord.app == null || activityrecord.app.thread == null)
            break MISSING_BLOCK_LABEL_213;
        if(mMainStack && mService.mFocusedActivity == activityrecord)
            mService.setFocusedActivityLocked(topRunningActivityLocked(null));
        activityrecord.resumeKeyDispatchingLocked();
        activityrecord.stopped = false;
        activityrecord.state = ActivityState.STOPPING;
        if(!activityrecord.visible)
            mService.mWindowManager.setAppVisibility(activityrecord.appToken, false);
        activityrecord.app.thread.scheduleStopActivity(activityrecord.appToken, activityrecord.visible, activityrecord.configChangeFlags);
        if(mService.isSleeping())
            activityrecord.setSleeping(true);
        Message message = mHandler.obtainMessage(108);
        message.obj = activityrecord;
        mHandler.sendMessageDelayed(message, 10000L);
_L1:
        return;
        Exception exception;
        exception;
        Slog.w("ActivityManager", "Exception thrown during pause", exception);
        activityrecord.stopped = true;
        activityrecord.state = ActivityState.STOPPED;
        if(activityrecord.configDestroy)
            destroyActivityLocked(activityrecord, true, false, "stop-except");
          goto _L1
    }

    private final boolean updateLRUListLocked(ActivityRecord activityrecord) {
        boolean flag = mLRUActivities.remove(activityrecord);
        mLRUActivities.add(activityrecord);
        return flag;
    }

    final void activityDestroyed(IBinder ibinder) {
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        ActivityRecord activityrecord = ActivityRecord.forToken(ibinder);
        if(activityrecord != null)
            mHandler.removeMessages(105, activityrecord);
        if(indexOfActivityLocked(activityrecord) >= 0 && activityrecord.state == ActivityState.DESTROYING) {
            cleanUpActivityLocked(activityrecord, true, false);
            removeActivityFromHistoryLocked(activityrecord);
        }
        resumeTopActivityLocked(null);
        Binder.restoreCallingIdentity(l);
        activitymanagerservice;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    final ActivityRecord activityIdleInternal(IBinder ibinder, boolean flag, Configuration configuration) {
        ActivityRecord activityrecord;
        ArrayList arraylist;
        ArrayList arraylist1;
        IApplicationThread iapplicationthread;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        activityrecord = null;
        arraylist = null;
        arraylist1 = null;
        iapplicationthread = null;
        flag1 = false;
        flag2 = false;
        flag3 = false;
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord1;
        activityrecord1 = ActivityRecord.forToken(ibinder);
        if(activityrecord1 != null) {
            mHandler.removeMessages(102, activityrecord1);
            activityrecord1.finishLaunchTickingLocked();
        }
        if(indexOfActivityLocked(activityrecord1) < 0) goto _L2; else goto _L1
_L1:
        activityrecord = activityrecord1;
        if(flag)
            reportActivityLaunchedLocked(flag, activityrecord1, -1L, -1L);
        if(configuration != null)
            activityrecord1.configuration = configuration;
        if(mResumedActivity == activityrecord1 && mLaunchingActivity.isHeld()) {
            mHandler.removeMessages(104);
            mLaunchingActivity.release();
        }
        activityrecord1.idle = true;
        mService.scheduleAppGcsLocked();
        if(activityrecord1.thumbnailNeeded && activityrecord1.app != null && activityrecord1.app.thread != null) {
            iapplicationthread = activityrecord1.app.thread;
            activityrecord1.thumbnailNeeded = false;
        }
        ensureActivitiesVisibleLocked(null, 0);
        if(mMainStack && !mService.mBooted) {
            mService.mBooted = true;
            flag2 = true;
        }
_L12:
        ArrayList arraylist2 = processStoppingActivitiesLocked(true);
        if(arraylist2 == null) goto _L4; else goto _L3
_L3:
        int i = arraylist2.size();
_L13:
        int j = mFinishingActivities.size();
        if(j <= 0) goto _L6; else goto _L5
_L5:
        ArrayList arraylist3 = new ArrayList(mFinishingActivities);
        mFinishingActivities.clear();
        arraylist = arraylist3;
_L6:
        int k;
        ArrayList arraylist4;
        k = mService.mCancelledThumbnails.size();
        if(k <= 0)
            break MISSING_BLOCK_LABEL_326;
        arraylist4 = new ArrayList(mService.mCancelledThumbnails);
        mService.mCancelledThumbnails.clear();
        arraylist1 = arraylist4;
        if(mMainStack) {
            flag1 = mService.mBooting;
            mService.mBooting = false;
        }
        activitymanagerservice;
        JVM INSTR monitorexit ;
        Exception exception;
        ActivityRecord activityrecord4;
        ActivityManagerService activitymanagerservice2;
        int l;
        if(iapplicationthread != null)
            try {
                iapplicationthread.requestThumbnail(ibinder);
            }
            catch(Exception exception2) {
                Slog.w("ActivityManager", "Exception thrown when requesting thumbnail", exception2);
                mService.sendPendingThumbnail(null, ibinder, null, null, true);
            }
        l = 0;
        if(l >= i) goto _L8; else goto _L7
_L7:
        activityrecord4 = (ActivityRecord)arraylist2.get(l);
        activitymanagerservice2 = mService;
        activitymanagerservice2;
        JVM INSTR monitorenter ;
        if(!activityrecord4.finishing) goto _L10; else goto _L9
_L9:
        finishCurrentActivityLocked(activityrecord4, 0);
_L14:
        l++;
        break MISSING_BLOCK_LABEL_369;
_L2:
        if(!flag) goto _L12; else goto _L11
_L11:
        reportActivityLaunchedLocked(flag, null, -1L, -1L);
          goto _L12
_L17:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        i = 0;
          goto _L13
_L10:
        stopActivityLocked(activityrecord4);
          goto _L14
        Exception exception1;
        exception1;
        throw exception1;
_L8:
        int i1 = 0;
_L16:
        ActivityRecord activityrecord3;
        if(i1 >= j)
            break; /* Loop/switch isn't completed */
        activityrecord3 = (ActivityRecord)arraylist.get(i1);
        ActivityManagerService activitymanagerservice1 = mService;
        activitymanagerservice1;
        JVM INSTR monitorenter ;
        flag3 = destroyActivityLocked(activityrecord3, true, false, "finish-idle");
        i1++;
        if(true) goto _L16; else goto _L15
_L15:
        for(int j1 = 0; j1 < k; j1++) {
            ActivityRecord activityrecord2 = (ActivityRecord)arraylist1.get(j1);
            mService.sendPendingThumbnail(activityrecord2, null, null, null, true);
        }

        if(flag1)
            mService.finishBooting();
        mService.trimApplications();
        if(flag2)
            mService.enableScreenAfterBoot();
        if(flag3)
            resumeTopActivityLocked(null);
        return activityrecord;
        exception;
          goto _L17
        exception;
          goto _L17
        exception;
          goto _L17
    }

    final void activityPaused(IBinder ibinder, boolean flag) {
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        int i = indexOfTokenLocked(ibinder);
        if(i < 0) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord;
        activityrecord = (ActivityRecord)mHistory.get(i);
        mHandler.removeMessages(101, activityrecord);
        if(mPausingActivity != activityrecord) goto _L4; else goto _L3
_L3:
        activityrecord.state = ActivityState.PAUSED;
        completePauseLocked();
_L2:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        return;
_L4:
        Object aobj[];
        String s;
        aobj = new Object[3];
        aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
        aobj[1] = activityrecord.shortComponentName;
        if(mPausingActivity == null)
            break; /* Loop/switch isn't completed */
        s = mPausingActivity.shortComponentName;
_L6:
        aobj[2] = s;
        EventLog.writeEvent(30012, aobj);
        if(true) goto _L2; else goto _L5
        Exception exception;
        exception;
        throw exception;
_L5:
        s = "(none)";
          goto _L6
    }

    void activitySleptLocked(ActivityRecord activityrecord) {
        mGoingToSleepActivities.remove(activityrecord);
        checkReadyForSleepLocked();
    }

    final void activityStoppedLocked(ActivityRecord activityrecord, Bundle bundle, Bitmap bitmap, CharSequence charsequence) {
        if(activityrecord.state == ActivityState.STOPPING) goto _L2; else goto _L1
_L1:
        Slog.i("ActivityManager", (new StringBuilder()).append("Activity reported stop, but no longer stopping: ").append(activityrecord).toString());
        mHandler.removeMessages(108, activityrecord);
_L4:
        return;
_L2:
        ProcessRecord processrecord;
        if(bundle != null) {
            activityrecord.icicle = bundle;
            activityrecord.haveState = true;
            activityrecord.updateThumbnail(bitmap, charsequence);
        }
        if(activityrecord.stopped)
            continue; /* Loop/switch isn't completed */
        mHandler.removeMessages(108, activityrecord);
        activityrecord.stopped = true;
        activityrecord.state = ActivityState.STOPPED;
        if(activityrecord.finishing) {
            activityrecord.clearOptionsLocked();
            continue; /* Loop/switch isn't completed */
        }
        if(activityrecord.configDestroy) {
            destroyActivityLocked(activityrecord, true, false, "stop-config");
            resumeTopActivityLocked(null);
            continue; /* Loop/switch isn't completed */
        }
        processrecord = null;
        if(mResumedActivity == null)
            break; /* Loop/switch isn't completed */
        processrecord = mResumedActivity.app;
_L6:
        if(activityrecord.app != null && processrecord != null && activityrecord.app != processrecord && activityrecord.lastVisibleTime > mService.mPreviousProcessVisibleTime && activityrecord.app != mService.mHomeProcess) {
            mService.mPreviousProcess = activityrecord.app;
            mService.mPreviousProcessVisibleTime = activityrecord.lastVisibleTime;
        }
        if(true) goto _L4; else goto _L3
_L3:
        if(mPausingActivity == null) goto _L6; else goto _L5
_L5:
        processrecord = mPausingActivity.app;
          goto _L6
    }

    void awakeFromSleepingLocked() {
        mHandler.removeMessages(100);
        mSleepTimeout = false;
        if(mGoingToSleep.isHeld())
            mGoingToSleep.release();
        for(int i = -1 + mHistory.size(); i >= 0; i--)
            ((ActivityRecord)mHistory.get(i)).setSleeping(false);

        mGoingToSleepActivities.clear();
    }

    void checkReadyForSleepLocked() {
        if(mService.isSleeping()) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!mSleepTimeout) {
            if(mResumedActivity != null) {
                startPausingLocked(false, true);
                continue; /* Loop/switch isn't completed */
            }
            if(mPausingActivity != null)
                continue; /* Loop/switch isn't completed */
            if(mStoppingActivities.size() > 0) {
                scheduleIdleLocked();
                continue; /* Loop/switch isn't completed */
            }
            ensureActivitiesVisibleLocked(null, 0);
            for(int i = -1 + mHistory.size(); i >= 0; i--) {
                ActivityRecord activityrecord = (ActivityRecord)mHistory.get(i);
                if(activityrecord.state == ActivityState.STOPPING || activityrecord.state == ActivityState.STOPPED)
                    activityrecord.setSleeping(true);
            }

            if(mGoingToSleepActivities.size() > 0)
                continue; /* Loop/switch isn't completed */
        }
        mHandler.removeMessages(100);
        if(mGoingToSleep.isHeld())
            mGoingToSleep.release();
        if(mService.mShuttingDown)
            mService.notifyAll();
        if(true) goto _L1; else goto _L3
_L3:
    }

    final void cleanUpActivityLocked(ActivityRecord activityrecord, boolean flag, boolean flag1) {
        if(mResumedActivity == activityrecord)
            mResumedActivity = null;
        if(mService.mFocusedActivity == activityrecord)
            mService.mFocusedActivity = null;
        activityrecord.configDestroy = false;
        activityrecord.frozenBeforeDestroy = false;
        if(flag1)
            activityrecord.state = ActivityState.DESTROYED;
        mFinishingActivities.remove(activityrecord);
        mWaitingVisibleActivities.remove(activityrecord);
        if(activityrecord.finishing && activityrecord.pendingResults != null) {
            Iterator iterator = activityrecord.pendingResults.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                PendingIntentRecord pendingintentrecord = (PendingIntentRecord)((WeakReference)iterator.next()).get();
                if(pendingintentrecord != null)
                    mService.cancelIntentSenderLocked(pendingintentrecord, false);
            } while(true);
            activityrecord.pendingResults = null;
        }
        if(flag)
            cleanUpActivityServicesLocked(activityrecord);
        if(mService.mPendingThumbnails.size() > 0)
            mService.mCancelledThumbnails.add(activityrecord);
        removeTimeoutsForActivityLocked(activityrecord);
    }

    final void cleanUpActivityServicesLocked(ActivityRecord activityrecord) {
        if(activityrecord.connections != null) {
            ConnectionRecord connectionrecord;
            for(Iterator iterator = activityrecord.connections.iterator(); iterator.hasNext(); mService.removeConnectionLocked(connectionrecord, null, activityrecord))
                connectionrecord = (ConnectionRecord)iterator.next();

            activityrecord.connections = null;
        }
    }

    final void destroyActivitiesLocked(ProcessRecord processrecord, boolean flag, String s) {
        boolean flag1 = false;
        boolean flag2 = false;
        int i = -1 + mHistory.size();
        while(i >= 0)  {
            ActivityRecord activityrecord = (ActivityRecord)mHistory.get(i);
            if(!activityrecord.finishing) {
                if(activityrecord.fullscreen)
                    flag1 = true;
                if((processrecord == null || activityrecord.app == processrecord) && flag1 && activityrecord.app != null && activityrecord != mResumedActivity && activityrecord != mPausingActivity && activityrecord.haveState && !activityrecord.visible && activityrecord.stopped && activityrecord.state != ActivityState.DESTROYING && activityrecord.state != ActivityState.DESTROYED && destroyActivityLocked(activityrecord, true, flag, s))
                    flag2 = true;
            }
            i--;
        }
        if(flag2)
            resumeTopActivityLocked(null);
    }

    final boolean destroyActivityLocked(ActivityRecord activityrecord, boolean flag, boolean flag1, String s) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
        aobj[1] = Integer.valueOf(activityrecord.task.taskId);
        aobj[2] = activityrecord.shortComponentName;
        aobj[3] = s;
        EventLog.writeEvent(30018, aobj);
        boolean flag2 = false;
        cleanUpActivityLocked(activityrecord, false, false);
        boolean flag3;
        if(activityrecord.app != null)
            flag3 = true;
        else
            flag3 = false;
        if(flag3) {
            if(flag) {
                int i = activityrecord.app.activities.indexOf(activityrecord);
                if(i >= 0)
                    activityrecord.app.activities.remove(i);
                if(mService.mHeavyWeightProcess == activityrecord.app && activityrecord.app.activities.size() <= 0) {
                    mService.mHeavyWeightProcess = null;
                    mService.mHandler.sendEmptyMessage(25);
                }
                if(activityrecord.app.activities.size() == 0)
                    mService.updateLruProcessLocked(activityrecord.app, flag1, false);
            }
            boolean flag4 = false;
            try {
                activityrecord.app.thread.scheduleDestroyActivity(activityrecord.appToken, activityrecord.finishing, activityrecord.configChangeFlags);
            }
            catch(Exception exception) {
                if(activityrecord.finishing) {
                    removeActivityFromHistoryLocked(activityrecord);
                    flag2 = true;
                    flag4 = true;
                }
            }
            activityrecord.app = null;
            activityrecord.nowVisible = false;
            if(activityrecord.finishing && !flag4) {
                activityrecord.state = ActivityState.DESTROYING;
                Message message = mHandler.obtainMessage(105);
                message.obj = activityrecord;
                mHandler.sendMessageDelayed(message, 10000L);
            } else {
                activityrecord.state = ActivityState.DESTROYED;
            }
        } else
        if(activityrecord.finishing) {
            removeActivityFromHistoryLocked(activityrecord);
            flag2 = true;
        } else {
            activityrecord.state = ActivityState.DESTROYED;
        }
        activityrecord.configChangeFlags = 0;
        if(!mLRUActivities.remove(activityrecord) && flag3)
            Slog.w("ActivityManager", (new StringBuilder()).append("Activity ").append(activityrecord).append(" being finished, but not in LRU list").toString());
        return flag2;
    }

    public void dismissKeyguardOnNextActivityLocked() {
        mDismissKeyguardOnNextActivity = true;
    }

    final void ensureActivitiesVisibleLocked(ActivityRecord activityrecord, int i) {
        ActivityRecord activityrecord1 = topRunningActivityLocked(null);
        if(activityrecord1 != null)
            ensureActivitiesVisibleLocked(activityrecord1, activityrecord, null, i);
    }

    final void ensureActivitiesVisibleLocked(ActivityRecord activityrecord, ActivityRecord activityrecord1, String s, int i) {
        int j;
        boolean flag;
        for(j = -1 + mHistory.size(); mHistory.get(j) != activityrecord; j--);
        flag = false;
_L2:
        ActivityRecord activityrecord3;
        if(j < 0)
            break MISSING_BLOCK_LABEL_220;
        activityrecord3 = (ActivityRecord)mHistory.get(j);
        if(!activityrecord3.finishing)
            break; /* Loop/switch isn't completed */
_L4:
        j--;
        if(true) goto _L2; else goto _L1
_L1:
        boolean flag1;
        if(s == null || s.equals(activityrecord3.processName))
            flag1 = true;
        else
            flag1 = false;
        if(activityrecord3 != activityrecord1 && flag1)
            ensureActivityConfigurationLocked(activityrecord3, 0);
        if(activityrecord3.app == null || activityrecord3.app.thread == null) {
            if(s == null || s.equals(activityrecord3.processName)) {
                if(activityrecord3 != activityrecord1)
                    activityrecord3.startFreezingScreenLocked(activityrecord3.app, i);
                if(!activityrecord3.visible)
                    mService.mWindowManager.setAppVisibility(activityrecord3.appToken, true);
                if(activityrecord3 != activityrecord1)
                    startSpecificActivityLocked(activityrecord3, false, false);
            }
        } else
        if(activityrecord3.visible)
            activityrecord3.stopFreezingScreenLocked(false);
        else
        if(s == null) {
            activityrecord3.visible = true;
            if(activityrecord3.state != ActivityState.RESUMED && activityrecord3 != activityrecord1)
                try {
                    mService.mWindowManager.setAppVisibility(activityrecord3.appToken, true);
                    activityrecord3.sleeping = false;
                    activityrecord3.app.pendingUiClean = true;
                    activityrecord3.app.thread.scheduleWindowVisibility(activityrecord3.appToken, true);
                    activityrecord3.stopFreezingScreenLocked(false);
                }
                catch(Exception exception1) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown making visibile: ").append(activityrecord3.intent.getComponent()).toString(), exception1);
                }
        }
        i |= activityrecord3.configChangeFlags;
        if(!activityrecord3.fullscreen) goto _L4; else goto _L3
_L3:
        flag = true;
        j--;
        do {
            if(j < 0)
                break;
            ActivityRecord activityrecord2 = (ActivityRecord)mHistory.get(j);
            if(!activityrecord2.finishing)
                if(flag) {
                    if(activityrecord2.visible) {
                        activityrecord2.visible = false;
                        try {
                            mService.mWindowManager.setAppVisibility(activityrecord2.appToken, false);
                            if((activityrecord2.state == ActivityState.STOPPING || activityrecord2.state == ActivityState.STOPPED) && activityrecord2.app != null && activityrecord2.app.thread != null)
                                activityrecord2.app.thread.scheduleWindowVisibility(activityrecord2.appToken, false);
                        }
                        catch(Exception exception) {
                            Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown making hidden: ").append(activityrecord2.intent.getComponent()).toString(), exception);
                        }
                    }
                } else
                if(activityrecord2.fullscreen)
                    flag = true;
            j--;
        } while(true);
        return;
        break MISSING_BLOCK_LABEL_196;
    }

    final boolean ensureActivityConfigurationLocked(ActivityRecord activityrecord, int i) {
        boolean flag = true;
        if(!mConfigWillChange) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        Configuration configuration;
        int j;
        configuration = mService.mConfiguration;
        if(activityrecord.configuration == configuration && !activityrecord.forceNewConfig)
            continue; /* Loop/switch isn't completed */
        if(activityrecord.finishing) {
            activityrecord.stopFreezingScreenLocked(false);
            continue; /* Loop/switch isn't completed */
        }
        Configuration configuration1 = activityrecord.configuration;
        activityrecord.configuration = configuration;
        j = configuration1.diff(configuration);
        if(j == 0 && !activityrecord.forceNewConfig)
            continue; /* Loop/switch isn't completed */
        if(activityrecord.app == null || activityrecord.app.thread == null) {
            activityrecord.stopFreezingScreenLocked(false);
            activityrecord.forceNewConfig = false;
            continue; /* Loop/switch isn't completed */
        }
        if((j & (-1 ^ activityrecord.info.getRealConfigChanged())) == 0 && !activityrecord.forceNewConfig) goto _L4; else goto _L3
_L3:
        if(MiuiThemeHelper.needRestartActivity(((ComponentInfo) (activityrecord.info)).packageName, j, configuration))
            continue; /* Loop/switch isn't completed */
        activityrecord.configChangeFlags = j | activityrecord.configChangeFlags;
        activityrecord.startFreezingScreenLocked(activityrecord.app, i);
        activityrecord.forceNewConfig = false;
        if(activityrecord.app != null && activityrecord.app.thread != null) goto _L6; else goto _L5
_L5:
        destroyActivityLocked(activityrecord, flag, false, "config");
_L7:
        flag = false;
        continue; /* Loop/switch isn't completed */
_L6:
        if(activityrecord.state == ActivityState.PAUSING) {
            activityrecord.configDestroy = flag;
            continue; /* Loop/switch isn't completed */
        }
        if(activityrecord.state == ActivityState.RESUMED) {
            relaunchActivityLocked(activityrecord, activityrecord.configChangeFlags, flag);
            activityrecord.configChangeFlags = 0;
        } else {
            relaunchActivityLocked(activityrecord, activityrecord.configChangeFlags, false);
            activityrecord.configChangeFlags = 0;
        }
        if(true) goto _L7; else goto _L4
_L4:
        if(activityrecord.app != null && activityrecord.app.thread != null)
            try {
                activityrecord.app.thread.scheduleActivityConfigurationChanged(activityrecord.appToken);
            }
            catch(RemoteException remoteexception) { }
        activityrecord.stopFreezingScreenLocked(false);
        if(true) goto _L1; else goto _L8
_L8:
    }

    final boolean finishActivityAffinityLocked(IBinder ibinder) {
        boolean flag;
        int i;
        flag = false;
        i = indexOfTokenLocked(ibinder);
        if(i >= 0) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        ActivityRecord activityrecord = (ActivityRecord)mHistory.get(i);
_L6:
        ActivityRecord activityrecord1;
label0:
        {
            if(i < 0)
                break label0;
            activityrecord1 = (ActivityRecord)mHistory.get(i);
        }
          goto _L3
_L5:
        flag = true;
          goto _L1
_L3:
        if(activityrecord1.task != activityrecord.task || activityrecord1.taskAffinity == null && activityrecord.taskAffinity != null || activityrecord1.taskAffinity != null && !activityrecord1.taskAffinity.equals(activityrecord.taskAffinity)) goto _L5; else goto _L4
_L4:
        finishActivityLocked(activityrecord1, i, 0, null, "request-affinity");
        i--;
          goto _L6
    }

    final boolean finishActivityLocked(ActivityRecord activityrecord, int i, int j, Intent intent, String s) {
        return finishActivityLocked(activityrecord, i, j, intent, s, false);
    }

    final boolean finishActivityLocked(ActivityRecord activityrecord, int i, int j, Intent intent, String s, boolean flag) {
        boolean flag1;
        boolean flag2;
        flag1 = true;
        flag2 = false;
        if(!activityrecord.finishing) goto _L2; else goto _L1
_L1:
        Slog.w("ActivityManager", (new StringBuilder()).append("Duplicate finish request for ").append(activityrecord).toString());
_L4:
        return flag2;
_L2:
        activityrecord.makeFinishing();
        Object aobj[] = new Object[4];
        aobj[flag2] = Integer.valueOf(System.identityHashCode(activityrecord));
        aobj[flag1] = Integer.valueOf(activityrecord.task.taskId);
        aobj[2] = activityrecord.shortComponentName;
        aobj[3] = s;
        EventLog.writeEvent(30001, aobj);
        if(i < -1 + mHistory.size()) {
            ActivityRecord activityrecord1 = (ActivityRecord)mHistory.get(i + 1);
            if(activityrecord1.task == activityrecord.task) {
                if(activityrecord.frontOfTask)
                    activityrecord1.frontOfTask = flag1;
                if((0x80000 & activityrecord.intent.getFlags()) != 0)
                    activityrecord1.intent.addFlags(0x80000);
            }
        }
        activityrecord.pauseKeyDispatchingLocked();
        if(mMainStack && mService.mFocusedActivity == activityrecord)
            mService.setFocusedActivityLocked(topRunningActivityLocked(null));
        finishActivityResultsLocked(activityrecord, j, intent);
        if(mService.mPendingThumbnails.size() > 0)
            mService.mCancelledThumbnails.add(activityrecord);
        if(flag) {
            boolean flag4;
            if(finishCurrentActivityLocked(activityrecord, i, 0) == null)
                flag4 = flag1;
            else
                flag4 = false;
            flag2 = flag4;
        } else
        if(mResumedActivity == activityrecord) {
            boolean flag3;
            WindowManagerService windowmanagerservice;
            char c;
            if(i <= 0 || ((ActivityRecord)mHistory.get(i - 1)).task != activityrecord.task)
                flag3 = flag1;
            else
                flag3 = false;
            windowmanagerservice = mService.mWindowManager;
            if(flag3)
                c = '\u2009';
            else
                c = '\u2007';
            windowmanagerservice.prepareAppTransition(c, false);
            mService.mWindowManager.setAppVisibility(activityrecord.appToken, false);
            if(mPausingActivity == null)
                startPausingLocked(false, false);
        } else
        if(activityrecord.state != ActivityState.PAUSING) {
            if(finishCurrentActivityLocked(activityrecord, i, flag1) != null)
                flag1 = false;
            flag2 = flag1;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    final void finishActivityResultsLocked(ActivityRecord activityrecord, int i, Intent intent) {
        ActivityRecord activityrecord1 = activityrecord.resultTo;
        if(activityrecord1 != null) {
            if(activityrecord.info.applicationInfo.uid > 0)
                mService.grantUriPermissionFromIntentLocked(activityrecord.info.applicationInfo.uid, activityrecord1.packageName, intent, activityrecord1.getUriPermissionsLocked());
            activityrecord1.addResultLocked(activityrecord, activityrecord.resultWho, activityrecord.requestCode, i, intent);
            activityrecord.resultTo = null;
        }
        activityrecord.results = null;
        activityrecord.pendingResults = null;
        activityrecord.newIntents = null;
        activityrecord.icicle = null;
    }

    final void finishSubActivityLocked(IBinder ibinder, String s, int i) {
        ActivityRecord activityrecord = isInStackLocked(ibinder);
        if(activityrecord != null) {
            int j = -1 + mHistory.size();
            while(j >= 0)  {
                ActivityRecord activityrecord1 = (ActivityRecord)mHistory.get(j);
                if(activityrecord1.resultTo == activityrecord && activityrecord1.requestCode == i && (activityrecord1.resultWho == null && s == null || activityrecord1.resultWho != null && activityrecord1.resultWho.equals(s)))
                    finishActivityLocked(activityrecord1, j, 0, null, "request-sub");
                j--;
            }
        }
    }

    public TaskAccessInfo getTaskAccessInfoLocked(int i, boolean flag) {
        ActivityRecord activityrecord;
        final TaskAccessInfo thumbs;
        int j;
        int k;
        ThumbnailHolder thumbnailholder;
        activityrecord = mResumedActivity;
        thumbs = new TaskAccessInfo();
        j = mHistory.size();
        k = 0;
        thumbnailholder = null;
_L7:
        if(k >= j) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord3 = (ActivityRecord)mHistory.get(k);
        if(activityrecord3.finishing || activityrecord3.task.taskId != i) goto _L4; else goto _L3
_L3:
        thumbnailholder = activityrecord3.thumbHolder;
_L2:
        if(k < j) goto _L6; else goto _L5
_L5:
        return thumbs;
_L4:
        k++;
          goto _L7
_L6:
        ArrayList arraylist;
        ActivityRecord activityrecord1;
        thumbs.root = (ActivityRecord)mHistory.get(k);
        thumbs.rootIndex = k;
        arraylist = new ArrayList();
        thumbs.subtasks = arraylist;
        activityrecord1 = null;
_L8:
        ActivityRecord activityrecord2;
label0:
        {
label1:
            {
                do {
                    if(k >= j)
                        break label1;
                    activityrecord2 = (ActivityRecord)mHistory.get(k);
                    k++;
                } while(activityrecord2.finishing);
                if(activityrecord2.task.taskId == i)
                    break label0;
            }
            if(activityrecord1 != null && arraylist.size() > 0 && activityrecord == activityrecord1)
                ((TaskAccessInfo.SubTask)arraylist.get(-1 + arraylist.size())).thumbnail = activityrecord1.stack.screenshotActivities(activityrecord1);
            if(thumbs.numSubThumbbails > 0)
                thumbs.retriever = new android.app.IThumbnailRetriever.Stub() {

                    public Bitmap getThumbnail(int l) {
                        Bitmap bitmap;
                        if(l < 0 || l >= thumbs.subtasks.size())
                            bitmap = null;
                        else
                            bitmap = ((TaskAccessInfo.SubTask)thumbs.subtasks.get(l)).thumbnail;
                        return bitmap;
                    }

                    final ActivityStack this$0;
                    final TaskAccessInfo val$thumbs;

             {
                this$0 = ActivityStack.this;
                thumbs = taskaccessinfo;
                super();
            }
                };
        }
          goto _L5
        activityrecord1 = activityrecord2;
        if(activityrecord2.thumbHolder != thumbnailholder && thumbnailholder != null) {
            thumbs.numSubThumbbails = 1 + thumbs.numSubThumbbails;
            thumbnailholder = activityrecord2.thumbHolder;
            TaskAccessInfo.SubTask subtask = new TaskAccessInfo.SubTask();
            subtask.thumbnail = thumbnailholder.lastThumbnail;
            subtask.activity = activityrecord2;
            subtask.index = k - 1;
            arraylist.add(subtask);
        }
          goto _L8
    }

    public android.app.ActivityManager.TaskThumbnails getTaskThumbnailsLocked(TaskRecord taskrecord) {
        TaskAccessInfo taskaccessinfo = getTaskAccessInfoLocked(taskrecord.taskId, true);
        ActivityRecord activityrecord = mResumedActivity;
        if(activityrecord != null && activityrecord.thumbHolder == taskrecord)
            taskaccessinfo.mainThumbnail = activityrecord.stack.screenshotActivities(activityrecord);
        else
            taskaccessinfo.mainThumbnail = ((ThumbnailHolder) (taskrecord)).lastThumbnail;
        return taskaccessinfo;
    }

    final int indexOfActivityLocked(ActivityRecord activityrecord) {
        return mHistory.indexOf(activityrecord);
    }

    final int indexOfTokenLocked(IBinder ibinder) {
        return mHistory.indexOf(ActivityRecord.forToken(ibinder));
    }

    final ActivityRecord isInStackLocked(IBinder ibinder) {
        ActivityRecord activityrecord = ActivityRecord.forToken(ibinder);
        if(!mHistory.contains(activityrecord))
            activityrecord = null;
        return activityrecord;
    }

    final void moveHomeToFrontFromLaunchLocked(int i) {
        if((i & 0x10004000) == 0x10004000)
            moveHomeToFrontLocked();
    }

    final void moveHomeToFrontLocked() {
        TaskRecord taskrecord = null;
        int i = -1 + mHistory.size();
        do {
label0:
            {
                if(i >= 0) {
                    ActivityRecord activityrecord = (ActivityRecord)mHistory.get(i);
                    if(!activityrecord.isHomeActivity)
                        break label0;
                    taskrecord = activityrecord.task;
                }
                if(taskrecord != null)
                    moveTaskToFrontLocked(taskrecord, null, null);
                return;
            }
            i--;
        } while(true);
    }

    final boolean moveTaskToBackLocked(int i, ActivityRecord activityrecord) {
        boolean flag;
        ActivityRecord activityrecord3;
        boolean flag1;
        flag = false;
        Slog.i("ActivityManager", (new StringBuilder()).append("moveTaskToBack: ").append(i).toString());
        if(!mMainStack || mService.mController == null)
            break MISSING_BLOCK_LABEL_117;
        activityrecord3 = topRunningActivityLocked(null, i);
        if(activityrecord3 == null)
            activityrecord3 = topRunningActivityLocked(null, 0);
        if(activityrecord3 == null)
            break MISSING_BLOCK_LABEL_117;
        flag1 = true;
        boolean flag2 = mService.mController.activityResuming(activityrecord3.packageName);
        flag1 = flag2;
_L1:
        if(flag1)
            break MISSING_BLOCK_LABEL_117;
_L2:
        return flag;
        RemoteException remoteexception;
        remoteexception;
        mService.mController = null;
          goto _L1
        ArrayList arraylist = new ArrayList();
        int j = mHistory.size();
        int k = 0;
        for(int l = 0; l < j; l++) {
            ActivityRecord activityrecord2 = (ActivityRecord)mHistory.get(l);
            if(activityrecord2.task.taskId == i) {
                mHistory.remove(l);
                mHistory.add(k, activityrecord2);
                arraylist.add(activityrecord2.appToken);
                k++;
            }
        }

        if(activityrecord != null && (0x10000 & activityrecord.intent.getFlags()) != 0) {
            mService.mWindowManager.prepareAppTransition(0, false);
            ActivityRecord activityrecord1 = topRunningActivityLocked(null);
            if(activityrecord1 != null)
                mNoAnimActivities.add(activityrecord1);
        } else {
            mService.mWindowManager.prepareAppTransition(8203, false);
        }
        mService.mWindowManager.moveAppTokensToBottom(arraylist);
        finishTaskMoveLocked(i);
        flag = true;
          goto _L2
    }

    final void moveTaskToFrontLocked(TaskRecord taskrecord, ActivityRecord activityrecord, Bundle bundle) {
        int i = taskrecord.taskId;
        int j = -1 + mHistory.size();
        if(j < 0 || ((ActivityRecord)mHistory.get(j)).task.taskId == i) {
            if(activityrecord != null && (0x10000 & activityrecord.intent.getFlags()) != 0)
                ActivityOptions.abort(bundle);
            else
                updateTransitLocked(4106, bundle);
        } else {
            ArrayList arraylist = new ArrayList();
            int k = -1 + mHistory.size();
            for(int l = k; l >= 0; l--) {
                ActivityRecord activityrecord2 = (ActivityRecord)mHistory.get(l);
                if(activityrecord2.task.taskId == i) {
                    mHistory.remove(l);
                    mHistory.add(k, activityrecord2);
                    arraylist.add(0, activityrecord2.appToken);
                    k--;
                }
            }

            if(activityrecord != null && (0x10000 & activityrecord.intent.getFlags()) != 0) {
                mService.mWindowManager.prepareAppTransition(0, false);
                ActivityRecord activityrecord1 = topRunningActivityLocked(null);
                if(activityrecord1 != null)
                    mNoAnimActivities.add(activityrecord1);
                ActivityOptions.abort(bundle);
            } else {
                updateTransitLocked(4106, bundle);
            }
            mService.mWindowManager.moveAppTokensToTop(arraylist);
            finishTaskMoveLocked(i);
            EventLog.writeEvent(30002, i);
        }
    }

    final ArrayList processStoppingActivitiesLocked(boolean flag) {
        int i = mStoppingActivities.size();
        ArrayList arraylist;
        if(i <= 0) {
            arraylist = null;
        } else {
            arraylist = null;
            boolean flag1;
            int j;
            if(mResumedActivity != null && mResumedActivity.nowVisible && !mResumedActivity.waitingVisible)
                flag1 = true;
            else
                flag1 = false;
            j = 0;
            while(j < i)  {
                ActivityRecord activityrecord = (ActivityRecord)mStoppingActivities.get(j);
                if(activityrecord.waitingVisible && flag1) {
                    mWaitingVisibleActivities.remove(activityrecord);
                    activityrecord.waitingVisible = false;
                    if(activityrecord.finishing)
                        mService.mWindowManager.setAppVisibility(activityrecord.appToken, false);
                }
                if((!activityrecord.waitingVisible || mService.isSleeping()) && flag) {
                    if(arraylist == null)
                        arraylist = new ArrayList();
                    arraylist.add(activityrecord);
                    mStoppingActivities.remove(j);
                    i--;
                    j--;
                }
                j++;
            }
        }
        return arraylist;
    }

    final boolean realStartActivityLocked(ActivityRecord activityrecord, ProcessRecord processrecord, boolean flag, boolean flag1) throws RemoteException {
        RemoteException remoteexception;
        boolean flag2;
        activityrecord.startFreezingScreenLocked(processrecord, 0);
        mService.mWindowManager.setAppVisibility(activityrecord.appToken, true);
        activityrecord.startLaunchTickingLocked();
        if(flag1) {
            WindowManagerService windowmanagerservice = mService.mWindowManager;
            Configuration configuration1 = mService.mConfiguration;
            android.view.IApplicationToken.Stub stub1;
            Configuration configuration2;
            if(activityrecord.mayFreezeScreenLocked(processrecord))
                stub1 = activityrecord.appToken;
            else
                stub1 = null;
            configuration2 = windowmanagerservice.updateOrientationFromAppTokens(configuration1, stub1);
            mService.updateConfigurationLocked(configuration2, activityrecord, false, false);
        }
        activityrecord.app = processrecord;
        processrecord.waitingToKill = null;
        if(processrecord.activities.indexOf(activityrecord) < 0)
            processrecord.activities.add(activityrecord);
        mService.updateLruProcessLocked(processrecord, true, true);
        if(processrecord.thread == null)
            throw new RemoteException();
          goto _L1
        remoteexception;
        if(!activityrecord.launchFailed) goto _L3; else goto _L2
_L2:
        Slog.e("ActivityManager", (new StringBuilder()).append("Second failure launching ").append(activityrecord.intent.getComponent().flattenToShortString()).append(", giving up").toString(), remoteexception);
        mService.appDiedLocked(processrecord, processrecord.pid, processrecord.thread);
        requestFinishActivityLocked(activityrecord.appToken, 0, null, "2nd-crash");
        flag2 = false;
_L7:
        return flag2;
_L1:
        ArrayList arraylist;
        ArrayList arraylist1;
        arraylist = null;
        arraylist1 = null;
        if(!flag)
            break MISSING_BLOCK_LABEL_257;
        arraylist = activityrecord.results;
        arraylist1 = activityrecord.newIntents;
        String s;
        ParcelFileDescriptor parcelfiledescriptor;
        boolean flag3;
        if(flag) {
            Object aobj[] = new Object[3];
            aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
            aobj[1] = Integer.valueOf(activityrecord.task.taskId);
            aobj[2] = activityrecord.shortComponentName;
            EventLog.writeEvent(30006, aobj);
        }
        if(activityrecord.isHomeActivity)
            mService.mHomeProcess = processrecord;
        mService.ensurePackageDexOpt(activityrecord.intent.getComponent().getPackageName());
        activityrecord.sleeping = false;
        activityrecord.forceNewConfig = false;
        showAskCompatModeDialogLocked(activityrecord);
        activityrecord.compat = mService.compatibilityInfoForPackageLocked(activityrecord.info.applicationInfo);
        s = null;
        parcelfiledescriptor = null;
        flag3 = false;
        if(mService.mProfileApp != null && mService.mProfileApp.equals(processrecord.processName) && (mService.mProfileProc == null || mService.mProfileProc == processrecord)) {
            mService.mProfileProc = processrecord;
            s = mService.mProfileFile;
            parcelfiledescriptor = mService.mProfileFd;
            flag3 = mService.mAutoStopProfiler;
        }
        processrecord.hasShownUi = true;
        processrecord.pendingUiClean = true;
        if(parcelfiledescriptor == null)
            break MISSING_BLOCK_LABEL_492;
        ParcelFileDescriptor parcelfiledescriptor1 = parcelfiledescriptor.dup();
        parcelfiledescriptor = parcelfiledescriptor1;
_L4:
        IApplicationThread iapplicationthread;
        Intent intent;
        android.view.IApplicationToken.Stub stub;
        int i;
        ActivityInfo activityinfo;
        Configuration configuration;
        android.content.res.CompatibilityInfo compatibilityinfo;
        Bundle bundle;
        boolean flag4;
        iapplicationthread = processrecord.thread;
        intent = new Intent(activityrecord.intent);
        stub = activityrecord.appToken;
        i = System.identityHashCode(activityrecord);
        activityinfo = activityrecord.info;
        configuration = new Configuration(mService.mConfiguration);
        compatibilityinfo = activityrecord.compat;
        bundle = activityrecord.icicle;
        if(flag)
            break MISSING_BLOCK_LABEL_877;
        flag4 = true;
_L5:
        iapplicationthread.scheduleLaunchActivity(intent, stub, i, activityinfo, configuration, compatibilityinfo, bundle, arraylist, arraylist1, flag4, mService.isNextTransitionForward(), s, parcelfiledescriptor, flag3);
        if((0x10000000 & processrecord.info.flags) != 0 && processrecord.processName.equals(processrecord.info.packageName)) {
            if(mService.mHeavyWeightProcess != null && mService.mHeavyWeightProcess != processrecord)
                Log.w("ActivityManager", (new StringBuilder()).append("Starting new heavy weight process ").append(processrecord).append(" when already running ").append(mService.mHeavyWeightProcess).toString());
            mService.mHeavyWeightProcess = processrecord;
            Message message = mService.mHandler.obtainMessage(24);
            message.obj = activityrecord;
            mService.mHandler.sendMessage(message);
        }
        activityrecord.launchFailed = false;
        if(updateLRUListLocked(activityrecord))
            Slog.w("ActivityManager", (new StringBuilder()).append("Activity ").append(activityrecord).append(" being launched, but already in LRU list").toString());
        IOException ioexception;
        if(flag) {
            activityrecord.state = ActivityState.RESUMED;
            activityrecord.stopped = false;
            mResumedActivity = activityrecord;
            activityrecord.task.touchActiveTime();
            if(mMainStack)
                mService.addRecentTaskLocked(activityrecord.task);
            completeResumeLocked(activityrecord);
            checkReadyForSleepLocked();
            activityrecord.icicle = null;
            activityrecord.haveState = false;
        } else {
            activityrecord.state = ActivityState.STOPPED;
            activityrecord.stopped = true;
        }
        if(mMainStack)
            mService.startSetupActivityLocked();
        flag2 = true;
        continue; /* Loop/switch isn't completed */
        ioexception;
        parcelfiledescriptor = null;
          goto _L4
        flag4 = false;
          goto _L5
_L3:
        processrecord.activities.remove(activityrecord);
        throw remoteexception;
        if(true) goto _L7; else goto _L6
_L6:
    }

    final void removeActivityFromHistoryLocked(ActivityRecord activityrecord) {
        if(activityrecord.state != ActivityState.DESTROYED) {
            finishActivityResultsLocked(activityrecord, 0, null);
            activityrecord.makeFinishing();
            mHistory.remove(activityrecord);
            activityrecord.takeFromHistory();
            activityrecord.state = ActivityState.DESTROYED;
            mService.mWindowManager.removeAppToken(activityrecord.appToken);
            cleanUpActivityServicesLocked(activityrecord);
            activityrecord.removeUriPermissionsLocked();
        }
    }

    void removeHistoryRecordsForAppLocked(ProcessRecord processrecord) {
        removeHistoryRecordsForAppLocked(mLRUActivities, processrecord);
        removeHistoryRecordsForAppLocked(mStoppingActivities, processrecord);
        removeHistoryRecordsForAppLocked(mGoingToSleepActivities, processrecord);
        removeHistoryRecordsForAppLocked(mWaitingVisibleActivities, processrecord);
        removeHistoryRecordsForAppLocked(mFinishingActivities, processrecord);
    }

    public ActivityRecord removeTaskActivitiesLocked(int i, int j, boolean flag) {
        ActivityRecord activityrecord;
        TaskAccessInfo taskaccessinfo;
        activityrecord = null;
        taskaccessinfo = getTaskAccessInfoLocked(i, false);
        if(taskaccessinfo.root != null) goto _L2; else goto _L1
_L1:
        if(flag)
            Slog.w("ActivityManager", (new StringBuilder()).append("removeTaskLocked: unknown taskId ").append(i).toString());
_L4:
        return activityrecord;
_L2:
        if(j < 0) {
            performClearTaskAtIndexLocked(i, taskaccessinfo.rootIndex);
            activityrecord = taskaccessinfo.root;
        } else
        if(j >= taskaccessinfo.subtasks.size()) {
            if(flag)
                Slog.w("ActivityManager", (new StringBuilder()).append("removeTaskLocked: unknown subTaskIndex ").append(j).toString());
        } else {
            TaskAccessInfo.SubTask subtask = (TaskAccessInfo.SubTask)taskaccessinfo.subtasks.get(j);
            performClearTaskAtIndexLocked(i, subtask.index);
            activityrecord = subtask.activity;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    void reportActivityLaunchedLocked(boolean flag, ActivityRecord activityrecord, long l, long l1) {
        for(int i = -1 + mWaitingActivityLaunched.size(); i >= 0; i--) {
            android.app.IActivityManager.WaitResult waitresult = (android.app.IActivityManager.WaitResult)mWaitingActivityLaunched.get(i);
            waitresult.timeout = flag;
            if(activityrecord != null)
                waitresult.who = new ComponentName(((ComponentInfo) (activityrecord.info)).packageName, ((ComponentInfo) (activityrecord.info)).name);
            waitresult.thisTime = l;
            waitresult.totalTime = l1;
        }

        mService.notifyAll();
    }

    void reportActivityVisibleLocked(ActivityRecord activityrecord) {
        for(int i = -1 + mWaitingActivityVisible.size(); i >= 0; i--) {
            android.app.IActivityManager.WaitResult waitresult = (android.app.IActivityManager.WaitResult)mWaitingActivityVisible.get(i);
            waitresult.timeout = false;
            if(activityrecord != null)
                waitresult.who = new ComponentName(((ComponentInfo) (activityrecord.info)).packageName, ((ComponentInfo) (activityrecord.info)).name);
            waitresult.totalTime = SystemClock.uptimeMillis() - waitresult.thisTime;
            waitresult.thisTime = waitresult.totalTime;
        }

        mService.notifyAll();
        if(mDismissKeyguardOnNextActivity) {
            mDismissKeyguardOnNextActivity = false;
            mService.mWindowManager.dismissKeyguard();
        }
    }

    final boolean requestFinishActivityLocked(IBinder ibinder, int i, Intent intent, String s) {
        int j = indexOfTokenLocked(ibinder);
        boolean flag;
        if(j < 0) {
            flag = false;
        } else {
            finishActivityLocked((ActivityRecord)mHistory.get(j), j, i, intent, s);
            flag = true;
        }
        return flag;
    }

    ActivityInfo resolveActivity(Intent intent, String s, int i, String s1, ParcelFileDescriptor parcelfiledescriptor, int j) {
        boolean flag = true;
        ResolveInfo resolveinfo = AppGlobals.getPackageManager().resolveIntent(intent, s, 0x10400, j);
        if(resolveinfo == null) goto _L2; else goto _L1
_L1:
        ActivityInfo activityinfo = resolveinfo.activityInfo;
_L3:
        if(activityinfo != null) {
            intent.setComponent(new ComponentName(activityinfo.applicationInfo.packageName, ((ComponentInfo) (activityinfo)).name));
            if((i & 2) != 0 && !activityinfo.processName.equals("system"))
                mService.setDebugApp(activityinfo.processName, flag, false);
            if((i & 4) != 0 && !activityinfo.processName.equals("system"))
                mService.setOpenGlTraceApp(activityinfo.applicationInfo, activityinfo.processName);
            if(s1 != null && !activityinfo.processName.equals("system")) {
                ActivityManagerService activitymanagerservice = mService;
                ApplicationInfo applicationinfo = activityinfo.applicationInfo;
                String s2 = activityinfo.processName;
                RemoteException remoteexception;
                if((i & 8) == 0)
                    flag = false;
                activitymanagerservice.setProfileApp(applicationinfo, s2, s1, parcelfiledescriptor, flag);
            }
        }
        return activityinfo;
_L2:
        activityinfo = null;
          goto _L3
        remoteexception;
        activityinfo = null;
          goto _L3
    }

    final boolean resumeTopActivityLocked(ActivityRecord activityrecord) {
        return resumeTopActivityLocked(activityrecord, null);
    }

    final boolean resumeTopActivityLocked(ActivityRecord activityrecord, Bundle bundle) {
        ActivityRecord activityrecord1;
        boolean flag2;
        activityrecord1 = topRunningActivityLocked(null);
        boolean flag = mUserLeaving;
        mUserLeaving = false;
        if(activityrecord1 == null && mMainStack) {
            ActivityOptions.abort(bundle);
            flag2 = mService.startHomeActivityLocked(0);
        } else {
            activityrecord1.delayedResume = false;
            if(mResumedActivity == activityrecord1 && activityrecord1.state == ActivityState.RESUMED) {
                mService.mWindowManager.executeAppTransition();
                mNoAnimActivities.clear();
                ActivityOptions.abort(bundle);
                flag2 = false;
            } else
            if((mService.mSleeping || mService.mShuttingDown) && mLastPausedActivity == activityrecord1 && (activityrecord1.state == ActivityState.PAUSED || activityrecord1.state == ActivityState.STOPPED || activityrecord1.state == ActivityState.STOPPING)) {
                mService.mWindowManager.executeAppTransition();
                mNoAnimActivities.clear();
                ActivityOptions.abort(bundle);
                flag2 = false;
            } else {
                mStoppingActivities.remove(activityrecord1);
                mGoingToSleepActivities.remove(activityrecord1);
                activityrecord1.sleeping = false;
                mWaitingVisibleActivities.remove(activityrecord1);
                activityrecord1.updateOptionsLocked(bundle);
                if(mPausingActivity != null) {
                    flag2 = false;
                } else {
label0:
                    {
                        if(mResumedActivity == null)
                            break label0;
                        startPausingLocked(flag, false);
                        flag2 = true;
                    }
                }
            }
        }
_L3:
        return flag2;
        if(!mMainStack) goto _L2; else goto _L1
_L1:
        activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        windowmanagerservice = mService.mWindowManager;
        configuration = mService.mConfiguration;
        if(!activityrecord1.mayFreezeScreenLocked(activityrecord1.app))
            break MISSING_BLOCK_LABEL_979;
        stub = activityrecord1.appToken;
_L4:
        configuration1 = windowmanagerservice.updateOrientationFromAppTokens(configuration, stub);
        if(configuration1 != null)
            activityrecord1.frozenBeforeDestroy = true;
        flag3 = mService.updateConfigurationLocked(configuration1, activityrecord1, false, false);
_L2:
        if(flag3)
            break MISSING_BLOCK_LABEL_993;
        if(topRunningActivityLocked(null) != activityrecord1)
            mHandler.sendEmptyMessage(106);
        if(mMainStack)
            mService.setFocusedActivityLocked(activityrecord1);
        ensureActivitiesVisibleLocked(null, 0);
        mService.mWindowManager.executeAppTransition();
        mNoAnimActivities.clear();
        flag2 = true;
          goto _L3
        stub = null;
          goto _L4
        exception2;
        activitymanagerservice;
        JVM INSTR monitorexit ;
        throw exception2;
        arraylist = activityrecord1.results;
        if(arraylist != null) {
            i = arraylist.size();
            if(!activityrecord1.finishing && i > 0)
                activityrecord1.app.thread.scheduleSendResult(activityrecord1.appToken, arraylist);
        }
        if(activityrecord1.newIntents != null)
            activityrecord1.app.thread.scheduleNewIntent(activityrecord1.newIntents, activityrecord1.appToken);
        aobj = new Object[3];
        aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord1));
        aobj[1] = Integer.valueOf(activityrecord1.task.taskId);
        aobj[2] = activityrecord1.shortComponentName;
        EventLog.writeEvent(30007, aobj);
        activityrecord1.sleeping = false;
        showAskCompatModeDialogLocked(activityrecord1);
        activityrecord1.app.pendingUiClean = true;
        activityrecord1.app.thread.scheduleResumeActivity(activityrecord1.appToken, mService.isNextTransitionForward());
        checkReadyForSleepLocked();
        activityrecord1.visible = true;
        completeResumeLocked(activityrecord1);
        activityrecord1.icicle = null;
        activityrecord1.haveState = false;
        activityrecord1.stopped = false;
_L5:
        flag2 = true;
          goto _L3
        exception;
        activityrecord1.state = activitystate;
        mResumedActivity = activityrecord3;
        Slog.i("ActivityManager", (new StringBuilder()).append("Restarting because process died: ").append(activityrecord1).toString());
        if(!activityrecord1.hasBeenLaunched)
            activityrecord1.hasBeenLaunched = true;
        else
        if(mMainStack)
            mService.mWindowManager.setAppStartingWindow(activityrecord1.appToken, activityrecord1.packageName, activityrecord1.theme, mService.compatibilityInfoForPackageLocked(activityrecord1.info.applicationInfo), activityrecord1.nonLocalizedLabel, activityrecord1.labelRes, activityrecord1.icon, activityrecord1.windowFlags, null, true);
        startSpecificActivityLocked(activityrecord1, true, false);
        flag2 = true;
          goto _L3
        exception1;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown during resume of ").append(activityrecord1).toString(), exception1);
        requestFinishActivityLocked(activityrecord1.appToken, 0, null, "resume-exception");
        flag2 = true;
          goto _L3
        if(!activityrecord1.hasBeenLaunched)
            activityrecord1.hasBeenLaunched = true;
        else
            mService.mWindowManager.setAppStartingWindow(activityrecord1.appToken, activityrecord1.packageName, activityrecord1.theme, mService.compatibilityInfoForPackageLocked(activityrecord1.info.applicationInfo), activityrecord1.nonLocalizedLabel, activityrecord1.labelRes, activityrecord1.icon, activityrecord1.windowFlags, null, true);
        startSpecificActivityLocked(activityrecord1, true, true);
          goto _L5
    }

    final void scheduleDestroyActivities(ProcessRecord processrecord, boolean flag, String s) {
        Message message = mHandler.obtainMessage(109);
        message.obj = new ScheduleDestroyArgs(processrecord, flag, s);
        mHandler.sendMessage(message);
    }

    final void scheduleIdleLocked() {
        Message message = Message.obtain();
        message.what = 103;
        mHandler.sendMessage(message);
    }

    public final Bitmap screenshotActivities(ActivityRecord activityrecord) {
        Bitmap bitmap = null;
        if(!activityrecord.noDisplay) goto _L2; else goto _L1
_L1:
        return bitmap;
_L2:
        Resources resources = mService.mContext.getResources();
        int i = mThumbnailWidth;
        int j = mThumbnailHeight;
        if(i < 0) {
            i = resources.getDimensionPixelSize(0x1050002);
            mThumbnailWidth = i;
            j = resources.getDimensionPixelSize(0x1050001);
            mThumbnailHeight = j;
        }
        if(i > 0)
            bitmap = mService.mWindowManager.screenshotApplications(activityrecord.appToken, i, j);
        if(true) goto _L1; else goto _L3
_L3:
    }

    void sendActivityResultLocked(int i, ActivityRecord activityrecord, String s, int j, int k, Intent intent) {
        if(i > 0)
            mService.grantUriPermissionFromIntentLocked(i, activityrecord.packageName, intent, activityrecord.getUriPermissionsLocked());
        if(mResumedActivity != activityrecord || activityrecord.app == null || activityrecord.app.thread == null)
            break MISSING_BLOCK_LABEL_125;
        ArrayList arraylist = new ArrayList();
        arraylist.add(new ResultInfo(s, j, k, intent));
        activityrecord.app.thread.scheduleSendResult(activityrecord.appToken, arraylist);
_L1:
        return;
        Exception exception;
        exception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown sending result to ").append(activityrecord).toString(), exception);
        activityrecord.addResultLocked(null, s, j, k, intent);
          goto _L1
    }

    final void showAskCompatModeDialogLocked(ActivityRecord activityrecord) {
        Message message = Message.obtain();
        message.what = 30;
        if(activityrecord.task.askedCompatMode)
            activityrecord = null;
        message.obj = activityrecord;
        mService.mHandler.sendMessage(message);
    }

    final int startActivities(IApplicationThread iapplicationthread, int i, Intent aintent[], String as[], IBinder ibinder, Bundle bundle, int j) {
        ActivityRecord aactivityrecord[];
        int k;
        long l;
        ActivityManagerService activitymanagerservice;
        int i1;
        Intent intent;
        if(aintent == null)
            throw new NullPointerException("intents is null");
        if(as == null)
            throw new NullPointerException("resolvedTypes is null");
        if(aintent.length != as.length)
            throw new IllegalArgumentException("intents are length different than resolvedTypes");
        aactivityrecord = new ActivityRecord[1];
        int j1;
        if(i >= 0)
            k = -1;
        else
        if(iapplicationthread == null) {
            k = Binder.getCallingPid();
            i = Binder.getCallingUid();
        } else {
            i = -1;
            k = i;
        }
        l = Binder.clearCallingIdentity();
        activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        i1 = 0;
_L5:
        j1 = aintent.length;
        if(i1 >= j1) goto _L2; else goto _L1
_L1:
        intent = aintent[i1];
        if(intent != null) goto _L4; else goto _L3
_L3:
        i1++;
          goto _L5
_L4:
        if(intent != null && intent.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Intent");
        break MISSING_BLOCK_LABEL_176;
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        if(intent.getComponent() == null) goto _L7; else goto _L6
_L6:
        boolean flag = true;
_L15:
        Intent intent1;
        ActivityInfo activityinfo1;
        intent1 = new Intent(intent);
        ActivityInfo activityinfo = resolveActivity(intent1, as[i1], 0, null, null, j);
        activityinfo1 = mService.getActivityInfoForUser(activityinfo, j);
        if(mMainStack && activityinfo1 != null && (0x10000000 & activityinfo1.applicationInfo.flags) != 0)
            throw new IllegalArgumentException("FLAG_CANT_SAVE_STATE not supported here");
        if(bundle == null) goto _L9; else goto _L8
_L8:
        int l1 = -1 + aintent.length;
        if(i1 != l1) goto _L9; else goto _L10
_L10:
        Bundle bundle1 = bundle;
_L13:
        int k1 = startActivityLocked(iapplicationthread, intent1, as[i1], activityinfo1, ibinder, null, -1, k, i, 0, bundle1, flag, aactivityrecord);
        if(k1 >= 0) goto _L12; else goto _L11
_L11:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
_L14:
        return k1;
_L9:
        bundle1 = null;
          goto _L13
_L12:
        if(aactivityrecord[0] == null)
            break MISSING_BLOCK_LABEL_385;
        ibinder = aactivityrecord[0].appToken;
          goto _L3
_L2:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        k1 = 0;
          goto _L14
_L7:
        flag = false;
          goto _L15
        ibinder = null;
          goto _L3
    }

    final int startActivityLocked(IApplicationThread iapplicationthread, Intent intent, String s, ActivityInfo activityinfo, IBinder ibinder, String s1, int i, 
            int j, int k, int l, Bundle bundle, boolean flag, ActivityRecord aactivityrecord[]) {
        byte byte0;
        ProcessRecord processrecord;
        ActivityRecord activityrecord;
        ActivityRecord activityrecord1;
        int i2;
        byte0 = 0;
        processrecord = null;
        if(iapplicationthread != null) {
            processrecord = mService.getRecordForAppLocked(iapplicationthread);
            int j2;
            StringBuilder stringbuilder;
            if(processrecord != null) {
                j = processrecord.pid;
                k = processrecord.info.uid;
            } else {
                Slog.w("ActivityManager", (new StringBuilder()).append("Unable to find app for caller ").append(iapplicationthread).append(" (pid=").append(j).append(") when starting: ").append(intent.toString()).toString());
                byte0 = -4;
            }
        }
        if(byte0 == 0) {
            int k2;
            int l2;
            if(activityinfo != null)
                k2 = UserId.getUserId(activityinfo.applicationInfo.uid);
            else
                k2 = 0;
            stringbuilder = (new StringBuilder()).append("START {").append(intent.toShortString(true, true, true, false)).append(" u=").append(k2).append("} from pid ");
            if(processrecord != null)
                l2 = processrecord.pid;
            else
                l2 = j;
            Slog.i("ActivityManager", stringbuilder.append(l2).toString());
        }
        activityrecord = null;
        activityrecord1 = null;
        if(ibinder != null) {
            j2 = indexOfTokenLocked(ibinder);
            if(j2 >= 0) {
                activityrecord = (ActivityRecord)mHistory.get(j2);
                if(i >= 0 && !activityrecord.finishing)
                    activityrecord1 = activityrecord;
            }
        }
        if((0x2000000 & intent.getFlags()) == 0 || activityrecord == null) goto _L2; else goto _L1
_L1:
        if(i < 0) goto _L4; else goto _L3
_L3:
        ActivityOptions.abort(bundle);
        i2 = -3;
_L9:
        return i2;
_L4:
        activityrecord1 = activityrecord.resultTo;
        s1 = activityrecord.resultWho;
        i = activityrecord.requestCode;
        activityrecord.resultTo = null;
        if(activityrecord1 != null)
            activityrecord1.removeResultsLocked(activityrecord, s1, i);
_L2:
        if(byte0 == 0 && intent.getComponent() == null)
            byte0 = -1;
        if(byte0 == 0 && activityinfo == null)
            byte0 = -2;
        if(byte0 != 0) {
            if(activityrecord1 != null)
                sendActivityResultLocked(-1, activityrecord1, s1, i, 0, null);
            mDismissKeyguardOnNextActivity = false;
            ActivityOptions.abort(bundle);
            i2 = byte0;
            continue; /* Loop/switch isn't completed */
        }
        int i1 = mService.checkPermission("android.permission.START_ANY_ACTIVITY", j, k);
        ActivityManagerService activitymanagerservice = mService;
        String s2 = activityinfo.permission;
        int j1 = activityinfo.applicationInfo.uid;
        boolean flag1 = activityinfo.exported;
        int k1 = activitymanagerservice.checkComponentPermission(s2, j, k, j1, flag1);
        if(i1 != 0 && k1 != 0) {
            if(activityrecord1 != null)
                sendActivityResultLocked(-1, activityrecord1, s1, i, 0, null);
            mDismissKeyguardOnNextActivity = false;
            String s3;
            if(!activityinfo.exported)
                s3 = (new StringBuilder()).append("Permission Denial: starting ").append(intent.toString()).append(" from ").append(processrecord).append(" (pid=").append(j).append(", uid=").append(k).append(")").append(" not exported from uid ").append(activityinfo.applicationInfo.uid).toString();
            else
                s3 = (new StringBuilder()).append("Permission Denial: starting ").append(intent.toString()).append(" from ").append(processrecord).append(" (pid=").append(j).append(", uid=").append(k).append(")").append(" requires ").append(activityinfo.permission).toString();
            Slog.w("ActivityManager", s3);
            throw new SecurityException(s3);
        }
        if(!mMainStack || mService.mController == null) goto _L6; else goto _L5
_L5:
        boolean flag2 = false;
        boolean flag3;
        Intent intent1 = intent.cloneFilter();
        flag3 = mService.mController.activityStarting(intent1, activityinfo.applicationInfo.packageName);
        if(!flag3)
            flag2 = true;
        else
            flag2 = false;
_L7:
        if(!flag2)
            break; /* Loop/switch isn't completed */
        if(activityrecord1 != null)
            sendActivityResultLocked(-1, activityrecord1, s1, i, 0, null);
        mDismissKeyguardOnNextActivity = false;
        ActivityOptions.abort(bundle);
        i2 = 0;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        mService.mController = null;
        if(true) goto _L7; else goto _L6
_L6:
        ActivityManagerService activitymanagerservice1 = mService;
        Configuration configuration = mService.mConfiguration;
        ActivityRecord activityrecord2 = new ActivityRecord(activitymanagerservice1, this, processrecord, k, intent, s, activityinfo, configuration, activityrecord1, s1, i, flag);
        if(aactivityrecord != null)
            aactivityrecord[0] = activityrecord2;
        if(mMainStack) {
            if((mResumedActivity == null || mResumedActivity.info.applicationInfo.uid != k) && !mService.checkAppSwitchAllowedLocked(j, k, "Activity start")) {
                ActivityManagerService.PendingActivityLaunch pendingactivitylaunch = new ActivityManagerService.PendingActivityLaunch();
                pendingactivitylaunch.r = activityrecord2;
                pendingactivitylaunch.sourceRecord = activityrecord;
                pendingactivitylaunch.startFlags = l;
                mService.mPendingActivityLaunches.add(pendingactivitylaunch);
                mDismissKeyguardOnNextActivity = false;
                ActivityOptions.abort(bundle);
                i2 = 4;
                continue; /* Loop/switch isn't completed */
            }
            int l1;
            if(mService.mDidAppSwitch)
                mService.mAppSwitchesAllowedTime = 0L;
            else
                mService.mDidAppSwitch = true;
            mService.doPendingActivityLaunchesLocked(false);
        }
        l1 = startActivityUncheckedLocked(activityrecord2, activityrecord, l, true, bundle);
        if(mDismissKeyguardOnNextActivity && mPausingActivity == null) {
            mDismissKeyguardOnNextActivity = false;
            mService.mWindowManager.dismissKeyguard();
        }
        i2 = l1;
        if(true) goto _L9; else goto _L8
_L8:
    }

    final int startActivityMayWait(IApplicationThread iapplicationthread, int i, Intent intent, String s, IBinder ibinder, String s1, int j, 
            int k, String s2, ParcelFileDescriptor parcelfiledescriptor, android.app.IActivityManager.WaitResult waitresult, Configuration configuration, Bundle bundle, int l) {
        Intent intent1;
        ActivityInfo activityinfo1;
        ActivityManagerService activitymanagerservice;
        int i1;
        boolean flag1;
        ActivityInfo activityinfo2;
        Intent intent2;
        int k1;
        int i2;
        ActivityInfo activityinfo3;
        if(intent != null && intent.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Intent");
        boolean flag;
        ActivityInfo activityinfo;
        long l1;
        IApplicationThread iapplicationthread1;
        Intent intent3;
        String s3;
        int j1;
        int j2;
        ActivityManagerService activitymanagerservice1;
        Intent aintent[];
        String as[];
        android.content.IIntentSender iintentsender;
        Intent intent4;
        int k2;
        ResolveInfo resolveinfo;
        ActivityInfo activityinfo4;
        ActivityRecord activityrecord1;
        ProcessRecord processrecord;
        if(intent.getComponent() != null)
            flag = true;
        else
            flag = false;
        intent1 = new Intent(intent);
        activityinfo = resolveActivity(intent1, s, k, s2, parcelfiledescriptor, l);
        if(activityinfo != null && mService.isSingleton(activityinfo.processName, activityinfo.applicationInfo))
            l = 0;
        activityinfo1 = mService.getActivityInfoForUser(activityinfo, l);
        activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        if(i < 0) goto _L2; else goto _L1
_L1:
        i1 = -1;
_L20:
        if(configuration == null) goto _L4; else goto _L3
_L3:
        if(mService.mConfiguration.diff(configuration) == 0) goto _L4; else goto _L5
_L5:
        flag1 = true;
_L30:
        mConfigWillChange = flag1;
        l1 = Binder.clearCallingIdentity();
        if(!mMainStack || activityinfo1 == null || (0x10000000 & activityinfo1.applicationInfo.flags) == 0 || !activityinfo1.processName.equals(activityinfo1.applicationInfo.packageName) || mService.mHeavyWeightProcess == null || mService.mHeavyWeightProcess.info.uid == activityinfo1.applicationInfo.uid && mService.mHeavyWeightProcess.processName.equals(activityinfo1.processName)) goto _L7; else goto _L6
_L6:
        i2 = i1;
        j2 = i;
        if(iapplicationthread == null) goto _L9; else goto _L8
_L8:
        processrecord = mService.getRecordForAppLocked(iapplicationthread);
        if(processrecord == null) goto _L11; else goto _L10
_L10:
        processrecord.pid;
        j2 = processrecord.info.uid;
_L9:
        activitymanagerservice1 = mService;
        aintent = new Intent[1];
        aintent[0] = intent1;
        as = new String[1];
        as[0] = s;
        iintentsender = activitymanagerservice1.getIntentSenderLocked(2, "android", j2, null, null, 0, aintent, as, 0x50000000, null);
        intent4 = new Intent();
        if(j >= 0)
            intent4.putExtra("has_result", true);
        intent4.putExtra("intent", new IntentSender(iintentsender));
        if(mService.mHeavyWeightProcess.activities.size() > 0) {
            activityrecord1 = (ActivityRecord)mService.mHeavyWeightProcess.activities.get(0);
            intent4.putExtra("cur_app", activityrecord1.packageName);
            intent4.putExtra("cur_task", activityrecord1.task.taskId);
        }
        intent4.putExtra("new_app", ((ComponentInfo) (activityinfo1)).packageName);
        intent4.setFlags(intent1.getFlags());
        intent4.setClassName("android", com/android/internal/app/HeavyWeightSwitcherActivity.getName());
        intent2 = intent4;
        s = null;
        iapplicationthread = null;
        i = Binder.getCallingUid();
        k2 = Binder.getCallingPid();
        i1 = k2;
        flag = true;
        resolveinfo = AppGlobals.getPackageManager().resolveIntent(intent2, null, 0x10400, l);
        if(resolveinfo == null) goto _L13; else goto _L12
_L12:
        activityinfo3 = resolveinfo.activityInfo;
_L22:
        activityinfo4 = mService.getActivityInfoForUser(activityinfo3, l);
        activityinfo2 = activityinfo4;
_L23:
        iapplicationthread1 = iapplicationthread;
        intent3 = intent2;
        s3 = s;
        j1 = i;
        k1 = startActivityLocked(iapplicationthread1, intent3, s3, activityinfo2, ibinder, s1, j, i1, j1, k, bundle, flag, null);
        if(mConfigWillChange && mMainStack) {
            mService.enforceCallingPermission("android.permission.CHANGE_CONFIGURATION", "updateConfiguration()");
            mConfigWillChange = false;
            mService.updateConfigurationLocked(configuration, null, false, false);
        }
        Binder.restoreCallingIdentity(l1);
        if(waitresult == null) goto _L15; else goto _L14
_L14:
        waitresult.result = k1;
        if(k1 != 0) goto _L17; else goto _L16
_L16:
        mWaitingActivityLaunched.add(waitresult);
        Exception exception;
        ActivityRecord activityrecord;
        ComponentName componentname;
        RemoteException remoteexception;
        do
            try {
                mService.wait();
            }
            catch(InterruptedException interruptedexception1) { }
        while(!waitresult.timeout && waitresult.who == null);
_L15:
        activitymanagerservice;
        JVM INSTR monitorexit ;
_L21:
        return k1;
_L2:
        if(iapplicationthread != null) goto _L19; else goto _L18
_L18:
        i1 = Binder.getCallingPid();
        i = Binder.getCallingUid();
          goto _L20
_L11:
        Slog.w("ActivityManager", (new StringBuilder()).append("Unable to find app for caller ").append(iapplicationthread).append(" (pid=").append(i2).append(") when starting: ").append(intent1.toString()).toString());
        ActivityOptions.abort(bundle);
        k1 = -4;
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L21
_L13:
        activityinfo3 = null;
          goto _L22
        remoteexception;
_L29:
        activityinfo2 = null;
          goto _L23
_L17:
        if(k1 != 2) goto _L15; else goto _L24
_L24:
        activityrecord = topRunningActivityLocked(null);
        if(!activityrecord.nowVisible) goto _L26; else goto _L25
_L25:
        waitresult.timeout = false;
        waitresult.who = new ComponentName(((ComponentInfo) (activityrecord.info)).packageName, ((ComponentInfo) (activityrecord.info)).name);
        waitresult.totalTime = 0L;
        waitresult.thisTime = 0L;
          goto _L15
_L28:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        throw exception;
_L26:
        waitresult.thisTime = SystemClock.uptimeMillis();
        mWaitingActivityVisible.add(waitresult);
_L27:
        try {
            mService.wait();
        }
        catch(InterruptedException interruptedexception) { }
        if(waitresult.timeout)
            break; /* Loop/switch isn't completed */
        componentname = waitresult.who;
        if(componentname == null) goto _L27; else goto _L15
        exception;
          goto _L28
        exception;
          goto _L28
        RemoteException remoteexception1;
        remoteexception1;
          goto _L29
_L7:
        activityinfo2 = activityinfo1;
        intent2 = intent1;
          goto _L23
_L19:
        i = -1;
        i1 = i;
          goto _L20
_L4:
        flag1 = false;
          goto _L30
        exception;
          goto _L28
    }

    final int startActivityUncheckedLocked(ActivityRecord activityrecord, ActivityRecord activityrecord1, int i, boolean flag, Bundle bundle) {
        Intent intent;
        int j;
        int k;
        ActivityRecord activityrecord2;
        boolean flag2;
        boolean flag3;
        TaskRecord taskrecord;
        int l;
        ActivityRecord activityrecord7;
        intent = activityrecord.intent;
        j = activityrecord.launchedFromUid;
        activityrecord.userId;
        k = intent.getFlags();
        boolean flag1;
        ActivityRecord activityrecord8;
        if((0x40000 & k) == 0)
            flag1 = true;
        else
            flag1 = false;
        mUserLeaving = flag1;
        if(!flag)
            activityrecord.delayedResume = true;
        if((0x1000000 & k) != 0)
            activityrecord2 = activityrecord;
        else
            activityrecord2 = null;
        if((i & 1) != 0) {
            ActivityRecord activityrecord10 = activityrecord1;
            if(activityrecord10 == null)
                activityrecord10 = topRunningNonDelayedActivityLocked(activityrecord2);
            if(!activityrecord10.realActivity.equals(activityrecord.realActivity))
                i &= -2;
        }
        if(activityrecord1 == null) {
            if((0x10000000 & k) == 0) {
                Slog.w("ActivityManager", (new StringBuilder()).append("startActivity called from non-Activity context; forcing Intent.FLAG_ACTIVITY_NEW_TASK for: ").append(intent).toString());
                k |= 0x10000000;
            }
        } else
        if(activityrecord1.launchMode == 3)
            k |= 0x10000000;
        else
        if(activityrecord.launchMode == 3 || activityrecord.launchMode == 2)
            k |= 0x10000000;
        if(activityrecord.resultTo != null && (0x10000000 & k) != 0) {
            Slog.w("ActivityManager", "Activity is launching as a new task, so cancelling activity result.");
            sendActivityResultLocked(-1, activityrecord.resultTo, activityrecord.resultWho, activityrecord.requestCode, 0, null);
            activityrecord.resultTo = null;
        }
        flag2 = false;
        flag3 = false;
        taskrecord = null;
        if(((0x10000000 & k) == 0 || (0x8000000 & k) != 0) && activityrecord.launchMode != 2 && activityrecord.launchMode != 3 || activityrecord.resultTo != null) goto _L2; else goto _L1
_L1:
        if(activityrecord.launchMode != 3)
            activityrecord7 = findTaskLocked(intent, activityrecord.info);
        else
            activityrecord7 = findActivityLocked(intent, activityrecord.info);
        if(activityrecord7 == null) goto _L2; else goto _L3
_L3:
        if(activityrecord7.task.intent == null)
            activityrecord7.task.setIntent(intent, activityrecord.info);
        activityrecord8 = topRunningNonDelayedActivityLocked(activityrecord2);
        if(activityrecord8 != null && activityrecord8.task != activityrecord7.task) {
            activityrecord.intent.addFlags(0x400000);
            boolean flag6;
            if(activityrecord1 == null || activityrecord8.task == activityrecord1.task)
                flag6 = true;
            else
                flag6 = false;
            if(flag6) {
                flag3 = true;
                moveHomeToFrontFromLaunchLocked(k);
                moveTaskToFrontLocked(activityrecord7.task, activityrecord, bundle);
                bundle = null;
            }
        }
        if((0x200000 & k) != 0)
            activityrecord7 = resetTaskIfNeededLocked(activityrecord7, activityrecord);
        if((i & 1) == 0) goto _L5; else goto _L4
_L4:
        if(flag)
            resumeTopActivityLocked(null, bundle);
        else
            ActivityOptions.abort(bundle);
        l = 1;
_L7:
        return l;
_L5:
        if((0x10008000 & k) == 0x10008000) {
            taskrecord = activityrecord7.task;
            performClearTaskLocked(activityrecord7.task.taskId);
            Intent intent2 = activityrecord.intent;
            ActivityInfo activityinfo = activityrecord.info;
            taskrecord.setIntent(intent2, activityinfo);
        } else
        if((0x4000000 & k) != 0 || activityrecord.launchMode == 2 || activityrecord.launchMode == 3) {
            ActivityRecord activityrecord9 = performClearTaskLocked(activityrecord7.task.taskId, activityrecord, k);
            if(activityrecord9 != null) {
                if(activityrecord9.frontOfTask)
                    activityrecord9.task.setIntent(activityrecord.intent, activityrecord.info);
                logStartActivity(30003, activityrecord, activityrecord9.task);
                activityrecord9.deliverNewIntentLocked(j, activityrecord.intent);
            } else {
                flag2 = true;
                activityrecord1 = activityrecord7;
            }
        } else
        if(activityrecord.realActivity.equals(activityrecord7.task.realActivity)) {
            if((0x20000000 & k) != 0 && activityrecord7.realActivity.equals(activityrecord.realActivity)) {
                logStartActivity(30003, activityrecord, activityrecord7.task);
                if(activityrecord7.frontOfTask)
                    activityrecord7.task.setIntent(activityrecord.intent, activityrecord.info);
                Intent intent1 = activityrecord.intent;
                activityrecord7.deliverNewIntentLocked(j, intent1);
            } else
            if(!activityrecord.intent.filterEquals(activityrecord7.task.intent)) {
                flag2 = true;
                activityrecord1 = activityrecord7;
            }
        } else
        if((0x200000 & k) == 0) {
            flag2 = true;
            activityrecord1 = activityrecord7;
        } else
        if(!activityrecord7.task.rootWasReset)
            activityrecord7.task.setIntent(activityrecord.intent, activityrecord.info);
        if(flag2 || taskrecord != null) goto _L2; else goto _L6
_L6:
        if(flag)
            resumeTopActivityLocked(null, bundle);
        else
            ActivityOptions.abort(bundle);
        l = 2;
          goto _L7
_L2:
        if(activityrecord.packageName != null) {
            ActivityRecord activityrecord3 = topRunningNonDelayedActivityLocked(activityrecord2);
            if(activityrecord3 == null || activityrecord.resultTo != null || !activityrecord3.realActivity.equals(activityrecord.realActivity) || activityrecord3.userId != activityrecord.userId || activityrecord3.app == null || activityrecord3.app.thread == null || (0x20000000 & k) == 0 && activityrecord.launchMode != 1 && activityrecord.launchMode != 2)
                break MISSING_BLOCK_LABEL_1083;
            logStartActivity(30003, activityrecord3, activityrecord3.task);
            if(flag)
                resumeTopActivityLocked(null);
            ActivityOptions.abort(bundle);
            if((i & 1) != 0) {
                l = 1;
            } else {
                activityrecord3.deliverNewIntentLocked(j, activityrecord.intent);
                l = 3;
            }
        } else {
            if(activityrecord.resultTo != null)
                sendActivityResultLocked(-1, activityrecord.resultTo, activityrecord.resultWho, activityrecord.requestCode, 0, null);
            ActivityOptions.abort(bundle);
            l = -2;
        }
          goto _L7
        boolean flag4;
        boolean flag5;
        flag4 = false;
        flag5 = false;
        if(activityrecord.resultTo != null || flag2 || (0x10000000 & k) == 0) goto _L9; else goto _L8
_L8:
        if(taskrecord == null) {
            ActivityManagerService activitymanagerservice = mService;
            activitymanagerservice.mCurTask = 1 + activitymanagerservice.mCurTask;
            if(mService.mCurTask <= 0)
                mService.mCurTask = 1;
            activityrecord.setTask(new TaskRecord(mService.mCurTask, activityrecord.info, intent), null, true);
        } else {
            activityrecord.setTask(taskrecord, taskrecord, true);
        }
        flag4 = true;
        if(!flag3)
            moveHomeToFrontFromLaunchLocked(k);
_L10:
        mService.grantUriPermissionFromIntentLocked(j, activityrecord.packageName, intent, activityrecord.getUriPermissionsLocked());
        if(flag4)
            EventLog.writeEvent(30004, activityrecord.task.taskId);
        logStartActivity(30005, activityrecord, activityrecord.task);
        startActivityLocked(activityrecord, flag4, flag, flag5, bundle);
        l = 0;
          goto _L7
_L9:
label0:
        {
            if(activityrecord1 == null)
                break MISSING_BLOCK_LABEL_1465;
            if(!flag2 && (0x4000000 & k) != 0) {
                ActivityRecord activityrecord6 = performClearTaskLocked(activityrecord1.task.taskId, activityrecord, k);
                flag5 = true;
                if(activityrecord6 == null)
                    break label0;
                logStartActivity(30003, activityrecord, activityrecord6.task);
                activityrecord6.deliverNewIntentLocked(j, activityrecord.intent);
                if(flag)
                    resumeTopActivityLocked(null);
                ActivityOptions.abort(bundle);
                l = 3;
            } else {
                if(flag2 || (0x20000 & k) == 0)
                    break label0;
                int j1 = findActivityInHistoryLocked(activityrecord, activityrecord1.task.taskId);
                if(j1 < 0)
                    break label0;
                ActivityRecord activityrecord5 = moveActivityToFrontLocked(j1);
                logStartActivity(30003, activityrecord, activityrecord5.task);
                activityrecord5.updateOptionsLocked(bundle);
                activityrecord5.deliverNewIntentLocked(j, activityrecord.intent);
                if(flag)
                    resumeTopActivityLocked(null);
                l = 3;
            }
        }
          goto _L7
        activityrecord.setTask(activityrecord1.task, activityrecord1.thumbHolder, false);
          goto _L10
        int i1 = mHistory.size();
        ActivityRecord activityrecord4;
        TaskRecord taskrecord1;
        if(i1 > 0)
            activityrecord4 = (ActivityRecord)mHistory.get(i1 - 1);
        else
            activityrecord4 = null;
        if(activityrecord4 != null)
            taskrecord1 = activityrecord4.task;
        else
            taskrecord1 = new TaskRecord(mService.mCurTask, activityrecord.info, intent);
        activityrecord.setTask(taskrecord1, null, true);
          goto _L10
    }

    void stopIfSleepingLocked() {
        if(mService.isSleeping()) {
            if(!mGoingToSleep.isHeld()) {
                mGoingToSleep.acquire();
                if(mLaunchingActivity.isHeld()) {
                    mLaunchingActivity.release();
                    mService.mHandler.removeMessages(104);
                }
            }
            mHandler.removeMessages(100);
            Message message = mHandler.obtainMessage(100);
            mHandler.sendMessageDelayed(message, 5000L);
            checkReadyForSleepLocked();
        }
    }

    final boolean switchUser(int i) {
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        mCurrentUser = i;
        if(mHistory.size() >= 2) goto _L2; else goto _L1
_L1:
        boolean flag = false;
          goto _L3
_L2:
        ActivityRecord activityrecord;
        flag = false;
        activityrecord = (ActivityRecord)mHistory.get(-1 + mHistory.size());
        if(activityrecord.userId != i) goto _L5; else goto _L4
_L4:
        flag = true;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L5:
        int j;
        j = mHistory.size();
_L6:
        int k;
        for(k = 0; k < j;) {
            if(((ActivityRecord)mHistory.get(k)).userId != i)
                break MISSING_BLOCK_LABEL_161;
            ActivityRecord activityrecord1 = (ActivityRecord)mHistory.remove(k);
            mHistory.add(activityrecord1);
            j--;
            flag = true;
        }

        resumeTopActivityLocked(activityrecord);
        activitymanagerservice;
        JVM INSTR monitorexit ;
_L3:
        return flag;
        k++;
          goto _L6
    }

    final ActivityRecord topRunningActivityLocked(IBinder ibinder, int i) {
        int j = -1 + mHistory.size();
_L3:
        ActivityRecord activityrecord;
        if(j < 0)
            break MISSING_BLOCK_LABEL_66;
        activityrecord = (ActivityRecord)mHistory.get(j);
        if(activityrecord.finishing || ibinder == activityrecord.appToken || i == activityrecord.task.taskId) goto _L2; else goto _L1
_L1:
        return activityrecord;
_L2:
        j--;
          goto _L3
        activityrecord = null;
          goto _L1
    }

    final ActivityRecord topRunningActivityLocked(ActivityRecord activityrecord) {
        int i = -1 + mHistory.size();
_L3:
        ActivityRecord activityrecord1;
        if(i < 0)
            break MISSING_BLOCK_LABEL_47;
        activityrecord1 = (ActivityRecord)mHistory.get(i);
        if(activityrecord1.finishing || activityrecord1 == activityrecord) goto _L2; else goto _L1
_L1:
        return activityrecord1;
_L2:
        i--;
          goto _L3
        activityrecord1 = null;
          goto _L1
    }

    final ActivityRecord topRunningNonDelayedActivityLocked(ActivityRecord activityrecord) {
        int i = -1 + mHistory.size();
_L3:
        ActivityRecord activityrecord1;
        if(i < 0)
            break MISSING_BLOCK_LABEL_54;
        activityrecord1 = (ActivityRecord)mHistory.get(i);
        if(activityrecord1.finishing || activityrecord1.delayedResume || activityrecord1 == activityrecord) goto _L2; else goto _L1
_L1:
        return activityrecord1;
_L2:
        i--;
          goto _L3
        activityrecord1 = null;
          goto _L1
    }

    final void updateTransitLocked(int i, Bundle bundle) {
        if(bundle != null) {
            ActivityRecord activityrecord = topRunningActivityLocked(null);
            if(activityrecord != null && activityrecord.state != ActivityState.RESUMED)
                activityrecord.updateOptionsLocked(bundle);
            else
                ActivityOptions.abort(bundle);
        }
        mService.mWindowManager.prepareAppTransition(i, false);
    }

    final void validateAppTokensLocked() {
        mValidateAppTokens.clear();
        mValidateAppTokens.ensureCapacity(mHistory.size());
        for(int i = 0; i < mHistory.size(); i++)
            mValidateAppTokens.add(((ActivityRecord)mHistory.get(i)).appToken);

        mService.mWindowManager.validateAppTokens(mValidateAppTokens);
    }

    static final long ACTIVITY_INACTIVE_RESET_TIME = 0L;
    static final boolean DEBUG_ADD_REMOVE = false;
    static final boolean DEBUG_CONFIGURATION = false;
    static final boolean DEBUG_PAUSE = false;
    static final boolean DEBUG_RESULTS = false;
    static final boolean DEBUG_SAVED_STATE = false;
    static final boolean DEBUG_STATES = false;
    static final boolean DEBUG_SWITCH = false;
    static final boolean DEBUG_TASKS = false;
    static final boolean DEBUG_TRANSITION = false;
    static final boolean DEBUG_USER_LEAVING = false;
    static final boolean DEBUG_VISBILITY = false;
    static final int DESTROY_ACTIVITIES_MSG = 109;
    static final int DESTROY_TIMEOUT = 10000;
    static final int DESTROY_TIMEOUT_MSG = 105;
    private static final int FINISH_AFTER_PAUSE = 1;
    private static final int FINISH_AFTER_VISIBLE = 2;
    private static final int FINISH_IMMEDIATELY = 0;
    static final int IDLE_NOW_MSG = 103;
    static final int IDLE_TIMEOUT = 10000;
    static final int IDLE_TIMEOUT_MSG = 102;
    static final int LAUNCH_TICK = 500;
    static final int LAUNCH_TICK_MSG = 107;
    static final int LAUNCH_TIMEOUT = 10000;
    static final int LAUNCH_TIMEOUT_MSG = 104;
    static final int PAUSE_TIMEOUT = 500;
    static final int PAUSE_TIMEOUT_MSG = 101;
    static final int RESUME_TOP_ACTIVITY_MSG = 106;
    static final boolean SHOW_APP_STARTING_PREVIEW = true;
    static final int SLEEP_TIMEOUT = 5000;
    static final int SLEEP_TIMEOUT_MSG = 100;
    static final long START_WARN_TIME = 5000L;
    static final int STOP_TIMEOUT = 10000;
    static final int STOP_TIMEOUT_MSG = 108;
    static final String TAG = "ActivityManager";
    static final boolean VALIDATE_TOKENS;
    static final boolean localLOGV;
    boolean mConfigWillChange;
    final Context mContext;
    private int mCurrentUser;
    boolean mDismissKeyguardOnNextActivity;
    final ArrayList mFinishingActivities = new ArrayList();
    final android.os.PowerManager.WakeLock mGoingToSleep;
    final ArrayList mGoingToSleepActivities = new ArrayList();
    final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            android.view.IApplicationToken.Stub stub = null;
            message.what;
            JVM INSTR tableswitch 100 109: default 60
        //                       100 61
        //                       101 123
        //                       102 256
        //                       103 538
        //                       104 584
        //                       105 477
        //                       106 698
        //                       107 394
        //                       108 733
        //                       109 814;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11
_L1:
            return;
_L2:
            ActivityManagerService activitymanagerservice7 = mService;
            activitymanagerservice7;
            JVM INSTR monitorenter ;
            if(mService.isSleeping()) {
                Slog.w("ActivityManager", "Sleep timeout!  Sleeping now.");
                mSleepTimeout = true;
                checkReadyForSleepLocked();
            }
            continue; /* Loop/switch isn't completed */
_L3:
            ActivityRecord activityrecord5 = (ActivityRecord)message.obj;
            Slog.w("ActivityManager", (new StringBuilder()).append("Activity pause timeout for ").append(activityrecord5).toString());
            synchronized(mService) {
                if(activityrecord5.app != null)
                    mService.logAppTooSlow(activityrecord5.app, activityrecord5.pauseTime, (new StringBuilder()).append("pausing ").append(activityrecord5).toString());
            }
            ActivityStack activitystack3 = ActivityStack.this;
            if(activityrecord5 != null)
                stub = activityrecord5.appToken;
            activitystack3.activityPaused(stub, true);
            continue; /* Loop/switch isn't completed */
            exception;
            activitymanagerservice6;
            JVM INSTR monitorexit ;
            throw exception;
_L4:
            if(mService.mDidDexOpt) {
                mService.mDidDexOpt = false;
                Message message2 = mHandler.obtainMessage(102);
                message2.obj = message.obj;
                mHandler.sendMessageDelayed(message2, 10000L);
            } else {
                ActivityRecord activityrecord4 = (ActivityRecord)message.obj;
                Slog.w("ActivityManager", (new StringBuilder()).append("Activity idle timeout for ").append(activityrecord4).toString());
                ActivityStack activitystack2 = ActivityStack.this;
                android.view.IApplicationToken.Stub stub2;
                if(activityrecord4 != null)
                    stub2 = activityrecord4.appToken;
                else
                    stub2 = null;
                activitystack2.activityIdleInternal(stub2, true, null);
            }
            continue; /* Loop/switch isn't completed */
_L9:
            ActivityRecord activityrecord3 = (ActivityRecord)message.obj;
            ActivityManagerService activitymanagerservice5 = mService;
            activitymanagerservice5;
            JVM INSTR monitorenter ;
            if(activityrecord3.continueLaunchTickingLocked())
                mService.logAppTooSlow(activityrecord3.app, activityrecord3.launchTickTime, (new StringBuilder()).append("launching ").append(activityrecord3).toString());
            continue; /* Loop/switch isn't completed */
_L7:
            ActivityRecord activityrecord2 = (ActivityRecord)message.obj;
            Slog.w("ActivityManager", (new StringBuilder()).append("Activity destroy timeout for ").append(activityrecord2).toString());
            ActivityStack activitystack1 = ActivityStack.this;
            if(activityrecord2 != null)
                stub = activityrecord2.appToken;
            activitystack1.activityDestroyed(stub);
            continue; /* Loop/switch isn't completed */
_L5:
            ActivityRecord activityrecord1 = (ActivityRecord)message.obj;
            ActivityStack activitystack = ActivityStack.this;
            android.view.IApplicationToken.Stub stub1;
            if(activityrecord1 != null)
                stub1 = activityrecord1.appToken;
            else
                stub1 = null;
            activitystack.activityIdleInternal(stub1, false, null);
            continue; /* Loop/switch isn't completed */
_L6:
            if(mService.mDidDexOpt) {
                mService.mDidDexOpt = false;
                Message message1 = mHandler.obtainMessage(104);
                mHandler.sendMessageDelayed(message1, 10000L);
                continue; /* Loop/switch isn't completed */
            }
            ActivityManagerService activitymanagerservice4 = mService;
            activitymanagerservice4;
            JVM INSTR monitorenter ;
            if(mLaunchingActivity.isHeld()) {
                Slog.w("ActivityManager", "Launch timeout has expired, giving up wake lock!");
                mLaunchingActivity.release();
            }
            continue; /* Loop/switch isn't completed */
_L8:
            ActivityManagerService activitymanagerservice3 = mService;
            activitymanagerservice3;
            JVM INSTR monitorenter ;
            resumeTopActivityLocked(null);
            continue; /* Loop/switch isn't completed */
_L10:
            ActivityRecord activityrecord;
            activityrecord = (ActivityRecord)message.obj;
            Slog.w("ActivityManager", (new StringBuilder()).append("Activity stop timeout for ").append(activityrecord).toString());
            ActivityManagerService activitymanagerservice2 = mService;
            activitymanagerservice2;
            JVM INSTR monitorenter ;
            if(activityrecord.isInHistory())
                activityStoppedLocked(activityrecord, null, null, null);
            continue; /* Loop/switch isn't completed */
_L11:
            ScheduleDestroyArgs scheduledestroyargs = (ScheduleDestroyArgs)message.obj;
            ActivityManagerService activitymanagerservice1 = mService;
            activitymanagerservice1;
            JVM INSTR monitorenter ;
            destroyActivitiesLocked(scheduledestroyargs.mOwner, scheduledestroyargs.mOomAdj, scheduledestroyargs.mReason);
            if(true) goto _L1; else goto _L12
_L12:
        }

        final ActivityStack this$0;

             {
                this$0 = ActivityStack.this;
                super();
            }
    };
    final ArrayList mHistory = new ArrayList();
    long mInitialStartTime;
    final ArrayList mLRUActivities = new ArrayList();
    ActivityRecord mLastPausedActivity;
    ActivityRecord mLastStartedActivity;
    final android.os.PowerManager.WakeLock mLaunchingActivity;
    final boolean mMainStack;
    final ArrayList mNoAnimActivities = new ArrayList();
    ActivityRecord mPausingActivity;
    ActivityRecord mResumedActivity;
    final ActivityManagerService mService;
    boolean mSleepTimeout;
    final ArrayList mStoppingActivities = new ArrayList();
    int mThumbnailHeight;
    int mThumbnailWidth;
    boolean mUserLeaving;
    final ArrayList mValidateAppTokens = new ArrayList();
    final ArrayList mWaitingActivityLaunched = new ArrayList();
    final ArrayList mWaitingActivityVisible = new ArrayList();
    final ArrayList mWaitingVisibleActivities = new ArrayList();
}
