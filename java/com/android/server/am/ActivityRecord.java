// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.ActivityOptions;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.*;
import android.util.*;
import com.android.internal.app.ResolverActivity;
import com.android.server.AttributeCache;
import com.android.server.wm.WindowManagerService;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ProcessRecord, ActivityStack, ActivityResult, ActivityManagerService, 
//            ThumbnailHolder, PendingIntentRecord, UriPermissionOwner, TaskRecord, 
//            UsageStatsService

final class ActivityRecord {
    static class Token extends android.view.IApplicationToken.Stub {

        public long getKeyDispatchingTimeout() throws RemoteException {
            ActivityRecord activityrecord = (ActivityRecord)weakActivity.get();
            long l;
            if(activityrecord != null)
                l = activityrecord.getKeyDispatchingTimeout();
            else
                l = 0L;
            return l;
        }

        public boolean keyDispatchingTimedOut() throws RemoteException {
            ActivityRecord activityrecord = (ActivityRecord)weakActivity.get();
            boolean flag;
            if(activityrecord != null)
                flag = activityrecord.keyDispatchingTimedOut();
            else
                flag = false;
            return flag;
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("Token{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(weakActivity.get());
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        public void windowsDrawn() throws RemoteException {
            ActivityRecord activityrecord = (ActivityRecord)weakActivity.get();
            if(activityrecord != null)
                activityrecord.windowsDrawn();
        }

        public void windowsGone() throws RemoteException {
            ActivityRecord activityrecord = (ActivityRecord)weakActivity.get();
            if(activityrecord != null)
                activityrecord.windowsGone();
        }

        public void windowsVisible() throws RemoteException {
            ActivityRecord activityrecord = (ActivityRecord)weakActivity.get();
            if(activityrecord != null)
                activityrecord.windowsVisible();
        }

        final WeakReference weakActivity;

        Token(ActivityRecord activityrecord) {
            weakActivity = new WeakReference(activityrecord);
        }
    }


    ActivityRecord(ActivityManagerService activitymanagerservice, ActivityStack activitystack, ProcessRecord processrecord, int i, Intent intent1, String s, ActivityInfo activityinfo, 
            Configuration configuration1, ActivityRecord activityrecord, String s1, int j, boolean flag) {
        service = activitymanagerservice;
        stack = activitystack;
        info = activityinfo;
        launchedFromUid = i;
        userId = UserId.getUserId(activityinfo.applicationInfo.uid);
        intent = intent1;
        shortComponentName = intent1.getComponent().flattenToShortString();
        resolvedType = s;
        componentSpecified = flag;
        configuration = configuration1;
        resultTo = activityrecord;
        resultWho = s1;
        requestCode = j;
        state = ActivityStack.ActivityState.INITIALIZING;
        frontOfTask = false;
        launchFailed = false;
        haveState = false;
        stopped = false;
        delayedResume = false;
        finishing = false;
        configDestroy = false;
        keysPaused = false;
        inHistory = false;
        visible = true;
        waitingVisible = false;
        nowVisible = false;
        thumbnailNeeded = false;
        idle = false;
        hasBeenLaunched = false;
        if(activityinfo != null) {
            boolean flag1;
            boolean flag2;
            boolean flag3;
            boolean flag4;
            if(activityinfo.targetActivity == null || activityinfo.launchMode == 0 || activityinfo.launchMode == 1)
                realActivity = intent1.getComponent();
            else
                realActivity = new ComponentName(((ComponentInfo) (activityinfo)).packageName, activityinfo.targetActivity);
            taskAffinity = activityinfo.taskAffinity;
            if((0x10 & activityinfo.flags) != 0)
                flag1 = true;
            else
                flag1 = false;
            stateNotNeeded = flag1;
            baseDir = activityinfo.applicationInfo.sourceDir;
            resDir = activityinfo.applicationInfo.publicSourceDir;
            dataDir = activityinfo.applicationInfo.dataDir;
            nonLocalizedLabel = activityinfo.nonLocalizedLabel;
            labelRes = activityinfo.labelRes;
            if(nonLocalizedLabel == null && labelRes == 0) {
                ApplicationInfo applicationinfo = activityinfo.applicationInfo;
                nonLocalizedLabel = applicationinfo.nonLocalizedLabel;
                labelRes = applicationinfo.labelRes;
            }
            icon = activityinfo.getIconResource();
            theme = activityinfo.getThemeResource();
            realTheme = theme;
            if(realTheme == 0) {
                com.android.server.AttributeCache.Entry entry;
                int k;
                if(activityinfo.applicationInfo.targetSdkVersion < 11)
                    k = 0x1030005;
                else
                    k = 0x103006b;
                realTheme = k;
            }
            if((0x200 & activityinfo.flags) != 0)
                windowFlags = 0x1000000 | windowFlags;
            if((1 & activityinfo.flags) != 0 && processrecord != null && (activityinfo.applicationInfo.uid == 1000 || activityinfo.applicationInfo.uid == processrecord.info.uid))
                processName = processrecord.processName;
            else
                processName = activityinfo.processName;
            if(intent != null && (0x20 & activityinfo.flags) != 0)
                intent.addFlags(0x800000);
            packageName = activityinfo.applicationInfo.packageName;
            launchMode = activityinfo.launchMode;
            entry = AttributeCache.instance().get(packageName, realTheme, com.android.internal.R.styleable.Window);
            if(entry != null && !entry.array.getBoolean(4, false) && !entry.array.getBoolean(5, false))
                flag2 = true;
            else
                flag2 = false;
            fullscreen = flag2;
            if(entry != null && entry.array.getBoolean(10, false))
                flag3 = true;
            else
                flag3 = false;
            noDisplay = flag3;
            if(!flag || i == Process.myUid() || i == 0) {
                if("android.intent.action.MAIN".equals(intent1.getAction()) && intent1.hasCategory("android.intent.category.HOME") && intent1.getCategories().size() == 1 && intent1.getData() == null && intent1.getType() == null && (0x10000000 & intent.getFlags()) != 0 && !com/android/internal/app/ResolverActivity.getName().equals(realActivity.getClassName()))
                    isHomeActivity = true;
                else
                    isHomeActivity = false;
            } else {
                isHomeActivity = false;
            }
            if((0x400 & activityinfo.flags) != 0)
                flag4 = true;
            else
                flag4 = false;
            immersive = flag4;
        } else {
            realActivity = null;
            taskAffinity = null;
            stateNotNeeded = false;
            baseDir = null;
            resDir = null;
            dataDir = null;
            processName = null;
            packageName = null;
            fullscreen = true;
            noDisplay = false;
            isHomeActivity = false;
            immersive = false;
        }
    }

    static ActivityRecord forToken(IBinder ibinder) {
        ActivityRecord activityrecord = null;
        if(ibinder == null) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord1 = (ActivityRecord)((Token)ibinder).weakActivity.get();
_L3:
        activityrecord = activityrecord1;
_L4:
        return activityrecord;
_L2:
        activityrecord1 = null;
          goto _L3
        ClassCastException classcastexception;
        classcastexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Bad activity token: ").append(ibinder).toString(), classcastexception);
          goto _L4
    }

    private ActivityRecord getWaitingHistoryRecordLocked() {
        ActivityRecord activityrecord = this;
        if(activityrecord.waitingVisible) {
            activityrecord = stack.mResumedActivity;
            if(activityrecord == null)
                activityrecord = stack.mPausingActivity;
            if(activityrecord == null)
                activityrecord = this;
        }
        return activityrecord;
    }

    void addNewIntentLocked(Intent intent1) {
        if(newIntents == null)
            newIntents = new ArrayList();
        newIntents.add(intent1);
    }

    void addResultLocked(ActivityRecord activityrecord, String s, int i, int j, Intent intent1) {
        ActivityResult activityresult = new ActivityResult(activityrecord, s, i, j, intent1);
        if(results == null)
            results = new ArrayList();
        results.add(activityresult);
    }

    void applyOptionsLocked() {
        if(pendingOptions == null) goto _L2; else goto _L1
_L1:
        int i = pendingOptions.getAnimationType();
        i;
        JVM INSTR tableswitch 1 4: default 48
    //                   1 54
    //                   2 95
    //                   3 204
    //                   4 204;
           goto _L3 _L4 _L5 _L6 _L6
_L3:
        pendingOptions = null;
_L2:
        return;
_L4:
        service.mWindowManager.overridePendingAppTransition(pendingOptions.getPackageName(), pendingOptions.getCustomEnterResId(), pendingOptions.getCustomExitResId(), pendingOptions.getOnAnimationStartListener());
        continue; /* Loop/switch isn't completed */
_L5:
        service.mWindowManager.overridePendingAppTransitionScaleUp(pendingOptions.getStartX(), pendingOptions.getStartY(), pendingOptions.getStartWidth(), pendingOptions.getStartHeight());
        if(intent.getSourceBounds() == null)
            intent.setSourceBounds(new Rect(pendingOptions.getStartX(), pendingOptions.getStartY(), pendingOptions.getStartX() + pendingOptions.getStartWidth(), pendingOptions.getStartY() + pendingOptions.getStartHeight()));
        continue; /* Loop/switch isn't completed */
_L6:
        boolean flag;
        if(i == 4)
            flag = true;
        else
            flag = false;
        service.mWindowManager.overridePendingAppTransitionThumb(pendingOptions.getThumbnail(), pendingOptions.getStartX(), pendingOptions.getStartY(), pendingOptions.getOnAnimationStartListener(), flag);
        if(intent.getSourceBounds() == null)
            intent.setSourceBounds(new Rect(pendingOptions.getStartX(), pendingOptions.getStartY(), pendingOptions.getStartX() + pendingOptions.getThumbnail().getWidth(), pendingOptions.getStartY() + pendingOptions.getThumbnail().getHeight()));
        if(true) goto _L3; else goto _L7
_L7:
    }

    void clearOptionsLocked() {
        if(pendingOptions != null) {
            pendingOptions.abort();
            pendingOptions = null;
        }
    }

    void clearThumbnail() {
        if(thumbHolder != null) {
            thumbHolder.lastThumbnail = null;
            thumbHolder.lastDescription = null;
        }
    }

    boolean continueLaunchTickingLocked() {
        boolean flag;
        if(launchTickTime != 0L) {
            Message message = stack.mHandler.obtainMessage(107);
            message.obj = this;
            stack.mHandler.removeMessages(107);
            stack.mHandler.sendMessageDelayed(message, 500L);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    final void deliverNewIntentLocked(int i, Intent intent1) {
        boolean flag;
        flag = false;
        if(state != ActivityStack.ActivityState.RESUMED && (!service.mSleeping || stack.topRunningActivityLocked(null) != this) || app == null || app.thread == null)
            break MISSING_BLOCK_LABEL_119;
        ArrayList arraylist;
        Intent intent2;
        arraylist = new ArrayList();
        intent2 = new Intent(intent1);
        arraylist.add(intent2);
        service.grantUriPermissionFromIntentLocked(i, packageName, intent2, getUriPermissionsLocked());
        app.thread.scheduleNewIntent(arraylist, appToken);
        flag = true;
        intent1 = intent2;
_L1:
        if(!flag)
            addNewIntentLocked(new Intent(intent1));
        return;
        RemoteException remoteexception;
        remoteexception;
_L3:
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown sending new intent to ").append(this).toString(), remoteexception);
          goto _L1
        NullPointerException nullpointerexception;
        nullpointerexception;
_L2:
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown sending new intent to ").append(this).toString(), nullpointerexception);
          goto _L1
        nullpointerexception;
        intent1 = intent2;
          goto _L2
        remoteexception;
        intent1 = intent2;
          goto _L3
    }

    void dump(PrintWriter printwriter, String s) {
        long l = SystemClock.uptimeMillis();
        printwriter.print(s);
        printwriter.print("packageName=");
        printwriter.print(packageName);
        printwriter.print(" processName=");
        printwriter.println(processName);
        printwriter.print(s);
        printwriter.print("launchedFromUid=");
        printwriter.print(launchedFromUid);
        printwriter.print(" userId=");
        printwriter.println(userId);
        printwriter.print(s);
        printwriter.print("app=");
        printwriter.println(app);
        printwriter.print(s);
        printwriter.println(intent.toInsecureStringWithClip());
        printwriter.print(s);
        printwriter.print("frontOfTask=");
        printwriter.print(frontOfTask);
        printwriter.print(" task=");
        printwriter.println(task);
        printwriter.print(s);
        printwriter.print("taskAffinity=");
        printwriter.println(taskAffinity);
        printwriter.print(s);
        printwriter.print("realActivity=");
        printwriter.println(realActivity.flattenToShortString());
        printwriter.print(s);
        printwriter.print("baseDir=");
        printwriter.println(baseDir);
        if(!resDir.equals(baseDir)) {
            printwriter.print(s);
            printwriter.print("resDir=");
            printwriter.println(resDir);
        }
        printwriter.print(s);
        printwriter.print("dataDir=");
        printwriter.println(dataDir);
        printwriter.print(s);
        printwriter.print("stateNotNeeded=");
        printwriter.print(stateNotNeeded);
        printwriter.print(" componentSpecified=");
        printwriter.print(componentSpecified);
        printwriter.print(" isHomeActivity=");
        printwriter.println(isHomeActivity);
        printwriter.print(s);
        printwriter.print("compat=");
        printwriter.print(compat);
        printwriter.print(" labelRes=0x");
        printwriter.print(Integer.toHexString(labelRes));
        printwriter.print(" icon=0x");
        printwriter.print(Integer.toHexString(icon));
        printwriter.print(" theme=0x");
        printwriter.println(Integer.toHexString(theme));
        printwriter.print(s);
        printwriter.print("config=");
        printwriter.println(configuration);
        if(resultTo != null || resultWho != null) {
            printwriter.print(s);
            printwriter.print("resultTo=");
            printwriter.print(resultTo);
            printwriter.print(" resultWho=");
            printwriter.print(resultWho);
            printwriter.print(" resultCode=");
            printwriter.println(requestCode);
        }
        if(results != null) {
            printwriter.print(s);
            printwriter.print("results=");
            printwriter.println(results);
        }
        if(pendingResults != null && pendingResults.size() > 0) {
            printwriter.print(s);
            printwriter.println("Pending Results:");
            for(Iterator iterator = pendingResults.iterator(); iterator.hasNext();) {
                WeakReference weakreference = (WeakReference)iterator.next();
                PendingIntentRecord pendingintentrecord;
                if(weakreference != null)
                    pendingintentrecord = (PendingIntentRecord)weakreference.get();
                else
                    pendingintentrecord = null;
                printwriter.print(s);
                printwriter.print("  - ");
                if(pendingintentrecord == null) {
                    printwriter.println("null");
                } else {
                    printwriter.println(pendingintentrecord);
                    pendingintentrecord.dump(printwriter, (new StringBuilder()).append(s).append("    ").toString());
                }
            }

        }
        if(newIntents != null && newIntents.size() > 0) {
            printwriter.print(s);
            printwriter.println("Pending New Intents:");
            int i = 0;
            while(i < newIntents.size())  {
                Intent intent1 = (Intent)newIntents.get(i);
                printwriter.print(s);
                printwriter.print("  - ");
                if(intent1 == null)
                    printwriter.println("null");
                else
                    printwriter.println(intent1.toShortString(false, true, false, true));
                i++;
            }
        }
        if(pendingOptions != null) {
            printwriter.print(s);
            printwriter.print("pendingOptions=");
            printwriter.println(pendingOptions);
        }
        if(uriPermissions != null) {
            if(uriPermissions.readUriPermissions != null) {
                printwriter.print(s);
                printwriter.print("readUriPermissions=");
                printwriter.println(uriPermissions.readUriPermissions);
            }
            if(uriPermissions.writeUriPermissions != null) {
                printwriter.print(s);
                printwriter.print("writeUriPermissions=");
                printwriter.println(uriPermissions.writeUriPermissions);
            }
        }
        printwriter.print(s);
        printwriter.print("launchFailed=");
        printwriter.print(launchFailed);
        printwriter.print(" haveState=");
        printwriter.print(haveState);
        printwriter.print(" icicle=");
        printwriter.println(icicle);
        printwriter.print(s);
        printwriter.print("state=");
        printwriter.print(state);
        printwriter.print(" stopped=");
        printwriter.print(stopped);
        printwriter.print(" delayedResume=");
        printwriter.print(delayedResume);
        printwriter.print(" finishing=");
        printwriter.println(finishing);
        printwriter.print(s);
        printwriter.print("keysPaused=");
        printwriter.print(keysPaused);
        printwriter.print(" inHistory=");
        printwriter.print(inHistory);
        printwriter.print(" visible=");
        printwriter.print(visible);
        printwriter.print(" sleeping=");
        printwriter.print(sleeping);
        printwriter.print(" idle=");
        printwriter.println(idle);
        printwriter.print(s);
        printwriter.print("fullscreen=");
        printwriter.print(fullscreen);
        printwriter.print(" noDisplay=");
        printwriter.print(noDisplay);
        printwriter.print(" immersive=");
        printwriter.print(immersive);
        printwriter.print(" launchMode=");
        printwriter.println(launchMode);
        printwriter.print(s);
        printwriter.print("frozenBeforeDestroy=");
        printwriter.print(frozenBeforeDestroy);
        printwriter.print(" thumbnailNeeded=");
        printwriter.print(thumbnailNeeded);
        printwriter.print(" forceNewConfig=");
        printwriter.println(forceNewConfig);
        printwriter.print(s);
        printwriter.print("thumbHolder=");
        printwriter.println(thumbHolder);
        if(launchTime != 0L || startTime != 0L) {
            printwriter.print(s);
            printwriter.print("launchTime=");
            if(launchTime == 0L)
                printwriter.print("0");
            else
                TimeUtils.formatDuration(launchTime, l, printwriter);
            printwriter.print(" startTime=");
            if(startTime == 0L)
                printwriter.print("0");
            else
                TimeUtils.formatDuration(startTime, l, printwriter);
            printwriter.println();
        }
        if(lastVisibleTime != 0L || waitingVisible || nowVisible) {
            printwriter.print(s);
            printwriter.print("waitingVisible=");
            printwriter.print(waitingVisible);
            printwriter.print(" nowVisible=");
            printwriter.print(nowVisible);
            printwriter.print(" lastVisibleTime=");
            if(lastVisibleTime == 0L)
                printwriter.print("0");
            else
                TimeUtils.formatDuration(lastVisibleTime, l, printwriter);
            printwriter.println();
        }
        if(configDestroy || configChangeFlags != 0) {
            printwriter.print(s);
            printwriter.print("configDestroy=");
            printwriter.print(configDestroy);
            printwriter.print(" configChangeFlags=");
            printwriter.println(Integer.toHexString(configChangeFlags));
        }
        if(connections != null) {
            printwriter.print(s);
            printwriter.print("connections=");
            printwriter.println(connections);
        }
    }

    void finishLaunchTickingLocked() {
        launchTickTime = 0L;
        stack.mHandler.removeMessages(107);
    }

    public long getKeyDispatchingTimeout() {
        ActivityManagerService activitymanagerservice = service;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = getWaitingHistoryRecordLocked();
        long l;
        if(activityrecord != null && activityrecord.app != null && (activityrecord.app.instrumentationClass != null || activityrecord.app.usingWrapper))
            l = 60000L;
        else
            l = 5000L;
        return l;
    }

    UriPermissionOwner getUriPermissionsLocked() {
        if(uriPermissions == null)
            uriPermissions = new UriPermissionOwner(service, this);
        return uriPermissions;
    }

    boolean isInHistory() {
        return inHistory;
    }

    public boolean isInterestingToUserLocked() {
        boolean flag;
        if(visible || nowVisible || state == ActivityStack.ActivityState.PAUSING || state == ActivityStack.ActivityState.RESUMED)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean keyDispatchingTimedOut() {
        boolean flag;
        ProcessRecord processrecord;
        flag = false;
        processrecord = null;
        ActivityManagerService activitymanagerservice = service;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord;
        activityrecord = getWaitingHistoryRecordLocked();
        if(activityrecord == null || activityrecord.app == null)
            break MISSING_BLOCK_LABEL_93;
        if(activityrecord.app.debugging)
            break MISSING_BLOCK_LABEL_167;
        if(service.mDidDexOpt) {
            service.mDidDexOpt = false;
            break MISSING_BLOCK_LABEL_167;
        }
        break MISSING_BLOCK_LABEL_76;
        Exception exception;
        exception;
        throw exception;
        if(activityrecord.app.instrumentationClass != null)
            break MISSING_BLOCK_LABEL_118;
        processrecord = activityrecord.app;
_L1:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        if(processrecord != null)
            service.appNotResponding(processrecord, activityrecord, this, "keyDispatchingTimedOut");
        flag = true;
        break MISSING_BLOCK_LABEL_167;
        Bundle bundle = new Bundle();
        bundle.putString("shortMsg", "keyDispatchingTimedOut");
        bundle.putString("longMsg", "Timed out while dispatching key event");
        service.finishInstrumentationLocked(activityrecord.app, 0, bundle);
          goto _L1
        return flag;
    }

    void makeFinishing() {
        if(!finishing) {
            finishing = true;
            if(task != null && inHistory) {
                TaskRecord taskrecord = task;
                taskrecord.numActivities = -1 + taskrecord.numActivities;
            }
            if(stopped)
                clearOptionsLocked();
        }
    }

    public boolean mayFreezeScreenLocked(ProcessRecord processrecord) {
        boolean flag;
        if(processrecord != null && !processrecord.crashing && !processrecord.notResponding)
            flag = true;
        else
            flag = false;
        return flag;
    }

    void pauseKeyDispatchingLocked() {
        if(!keysPaused) {
            keysPaused = true;
            service.mWindowManager.pauseKeyDispatching(appToken);
        }
    }

    void putInHistory() {
        if(!inHistory) {
            inHistory = true;
            if(task != null && !finishing) {
                TaskRecord taskrecord = task;
                taskrecord.numActivities = 1 + taskrecord.numActivities;
            }
        }
    }

    void removeResultsLocked(ActivityRecord activityrecord, String s, int i) {
        if(results != null) {
            int j = -1 + results.size();
            do {
                if(j < 0)
                    break;
                ActivityResult activityresult = (ActivityResult)results.get(j);
                if(activityresult.mFrom == activityrecord && (activityresult.mResultWho != null ? activityresult.mResultWho.equals(s) : s == null) && activityresult.mRequestCode == i)
                    results.remove(j);
                j--;
            } while(true);
        }
    }

    void removeUriPermissionsLocked() {
        if(uriPermissions != null) {
            uriPermissions.removeUriPermissionsLocked();
            uriPermissions = null;
        }
    }

    void resumeKeyDispatchingLocked() {
        if(keysPaused) {
            keysPaused = false;
            service.mWindowManager.resumeKeyDispatching(appToken);
        }
    }

    public void setSleeping(boolean flag) {
        if(sleeping != flag && app != null && app.thread != null)
            try {
                app.thread.scheduleSleeping(appToken, flag);
                if(sleeping && !stack.mGoingToSleepActivities.contains(this))
                    stack.mGoingToSleepActivities.add(this);
                sleeping = flag;
            }
            catch(RemoteException remoteexception) {
                Slog.w("ActivityManager", (new StringBuilder()).append("Exception thrown when sleeping: ").append(intent.getComponent()).toString(), remoteexception);
            }
    }

    void setTask(TaskRecord taskrecord, ThumbnailHolder thumbnailholder, boolean flag) {
        if(inHistory && !finishing) {
            if(task != null) {
                TaskRecord taskrecord1 = task;
                taskrecord1.numActivities = -1 + taskrecord1.numActivities;
            }
            if(taskrecord != null)
                taskrecord.numActivities = 1 + taskrecord.numActivities;
        }
        if(thumbnailholder == null)
            thumbnailholder = taskrecord;
        task = taskrecord;
        if(!flag && (0x80000 & intent.getFlags()) != 0) {
            if(thumbHolder == null)
                thumbHolder = new ThumbnailHolder();
        } else {
            thumbHolder = thumbnailholder;
        }
    }

    public void startFreezingScreenLocked(ProcessRecord processrecord, int i) {
        if(mayFreezeScreenLocked(processrecord))
            service.mWindowManager.startAppFreezingScreen(appToken, i);
    }

    void startLaunchTickingLocked() {
        if(!ActivityManagerService.IS_USER_BUILD && launchTickTime == 0L) {
            launchTickTime = SystemClock.uptimeMillis();
            continueLaunchTickingLocked();
        }
    }

    public void stopFreezingScreenLocked(boolean flag) {
        if(flag || frozenBeforeDestroy) {
            frozenBeforeDestroy = false;
            service.mWindowManager.stopAppFreezingScreen(appToken, flag);
        }
    }

    void takeFromHistory() {
        if(inHistory) {
            inHistory = false;
            if(task != null && !finishing) {
                TaskRecord taskrecord = task;
                taskrecord.numActivities = -1 + taskrecord.numActivities;
            }
            clearOptionsLocked();
        }
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ActivityRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(intent.getComponent().flattenToShortString());
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    void updateOptionsLocked(Bundle bundle) {
        if(bundle != null) {
            if(pendingOptions != null)
                pendingOptions.abort();
            pendingOptions = new ActivityOptions(bundle);
        }
    }

    void updateThumbnail(Bitmap bitmap, CharSequence charsequence) {
        if((0x80000 & intent.getFlags()) == 0);
        if(thumbHolder != null) {
            if(bitmap != null)
                thumbHolder.lastThumbnail = bitmap;
            thumbHolder.lastDescription = charsequence;
        }
    }

    public void windowsDrawn() {
        ActivityManagerService activitymanagerservice = service;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        if(launchTime != 0L) {
            long l = SystemClock.uptimeMillis();
            long l1 = l - launchTime;
            long l2;
            Object aobj[];
            StringBuilder stringbuilder;
            if(stack.mInitialStartTime != 0L)
                l2 = l - stack.mInitialStartTime;
            else
                l2 = l1;
            aobj = new Object[4];
            aobj[0] = Integer.valueOf(System.identityHashCode(this));
            aobj[1] = shortComponentName;
            aobj[2] = Long.valueOf(l1);
            aobj[3] = Long.valueOf(l2);
            EventLog.writeEvent(30009, aobj);
            stringbuilder = service.mStringBuilder;
            stringbuilder.setLength(0);
            stringbuilder.append("Displayed ");
            stringbuilder.append(shortComponentName);
            stringbuilder.append(": ");
            TimeUtils.formatDuration(l1, stringbuilder);
            if(l1 != l2) {
                stringbuilder.append(" (total ");
                TimeUtils.formatDuration(l2, stringbuilder);
                stringbuilder.append(")");
            }
            Log.i("ActivityManager", stringbuilder.toString());
            stack.reportActivityLaunchedLocked(false, this, l1, l2);
            if(l2 > 0L)
                service.mUsageStatsService.noteLaunchTime(realActivity, (int)l2);
            launchTime = 0L;
            stack.mInitialStartTime = 0L;
        }
        startTime = 0L;
        finishLaunchTickingLocked();
        return;
    }

    public void windowsGone() {
        nowVisible = false;
    }

    public void windowsVisible() {
        ActivityManagerService activitymanagerservice = service;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        stack.reportActivityVisibleLocked(this);
        if(nowVisible) goto _L2; else goto _L1
_L1:
        nowVisible = true;
        lastVisibleTime = SystemClock.uptimeMillis();
        if(idle) goto _L4; else goto _L3
_L3:
        stack.processStoppingActivitiesLocked(false);
_L6:
        service.scheduleAppGcsLocked();
_L2:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        return;
_L4:
        int i = stack.mWaitingVisibleActivities.size();
        if(i > 0) {
            for(int j = 0; j < i; j++)
                ((ActivityRecord)stack.mWaitingVisibleActivities.get(j)).waitingVisible = false;

            stack.mWaitingVisibleActivities.clear();
            Message message = Message.obtain();
            message.what = 103;
            stack.mHandler.sendMessage(message);
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    ProcessRecord app;
    final android.view.IApplicationToken.Stub appToken = new Token(this);
    final String baseDir;
    CompatibilityInfo compat;
    final boolean componentSpecified;
    int configChangeFlags;
    boolean configDestroy;
    Configuration configuration;
    HashSet connections;
    long cpuTimeAtResume;
    final String dataDir;
    boolean delayedResume;
    boolean finishing;
    boolean forceNewConfig;
    boolean frontOfTask;
    boolean frozenBeforeDestroy;
    final boolean fullscreen;
    boolean hasBeenLaunched;
    boolean haveState;
    Bundle icicle;
    int icon;
    boolean idle;
    boolean immersive;
    private boolean inHistory;
    final ActivityInfo info;
    final Intent intent;
    final boolean isHomeActivity;
    boolean keysPaused;
    int labelRes;
    long lastVisibleTime;
    boolean launchFailed;
    int launchMode;
    long launchTickTime;
    long launchTime;
    final int launchedFromUid;
    ArrayList newIntents;
    final boolean noDisplay;
    CharSequence nonLocalizedLabel;
    boolean nowVisible;
    final String packageName;
    long pauseTime;
    ActivityOptions pendingOptions;
    HashSet pendingResults;
    final String processName;
    final ComponentName realActivity;
    int realTheme;
    final int requestCode;
    final String resDir;
    final String resolvedType;
    ActivityRecord resultTo;
    final String resultWho;
    ArrayList results;
    final ActivityManagerService service;
    final String shortComponentName;
    boolean sleeping;
    final ActivityStack stack;
    long startTime;
    ActivityStack.ActivityState state;
    final boolean stateNotNeeded;
    boolean stopped;
    String stringName;
    TaskRecord task;
    final String taskAffinity;
    int theme;
    ThumbnailHolder thumbHolder;
    boolean thumbnailNeeded;
    UriPermissionOwner uriPermissions;
    final int userId;
    boolean visible;
    boolean waitingVisible;
    int windowFlags;
}
