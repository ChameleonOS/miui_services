// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.AppGlobals;
import android.app.IApplicationThread;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.android.server.am:
//            BroadcastRecord, BroadcastFilter, ActivityManagerService, ReceiverList, 
//            ProcessRecord

public class BroadcastQueue {
    private final class AppNotResponding
        implements Runnable {

        public void run() {
            mService.appNotResponding(mApp, null, null, mAnnotation);
        }

        private final String mAnnotation;
        private final ProcessRecord mApp;
        final BroadcastQueue this$0;

        public AppNotResponding(ProcessRecord processrecord, String s) {
            this$0 = BroadcastQueue.this;
            super();
            mApp = processrecord;
            mAnnotation = s;
        }
    }


    BroadcastQueue(ActivityManagerService activitymanagerservice, String s, long l) {
        mBroadcastsScheduled = false;
        mPendingBroadcast = null;
        mService = activitymanagerservice;
        mQueueName = s;
        mTimeoutPeriod = l;
    }

    private final void addBroadcastToHistoryLocked(BroadcastRecord broadcastrecord) {
        if(broadcastrecord.callingUid >= 0) {
            System.arraycopy(mBroadcastHistory, 0, mBroadcastHistory, 1, 24);
            broadcastrecord.finishTime = SystemClock.uptimeMillis();
            mBroadcastHistory[0] = broadcastrecord;
        }
    }

    private final void deliverToRegisteredReceiverLocked(BroadcastRecord broadcastrecord, BroadcastFilter broadcastfilter, boolean flag) {
        boolean flag1 = false;
        if(broadcastfilter.requiredPermission != null && mService.checkComponentPermission(broadcastfilter.requiredPermission, broadcastrecord.callingPid, broadcastrecord.callingUid, -1, true) != 0) {
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Permission Denial: broadcasting ").append(broadcastrecord.intent.toString()).append(" from ").append(broadcastrecord.callerPackage).append(" (pid=").append(broadcastrecord.callingPid).append(", uid=").append(broadcastrecord.callingUid).append(")").append(" requires ").append(broadcastfilter.requiredPermission).append(" due to registered receiver ").append(broadcastfilter).toString());
            flag1 = true;
        }
        if(broadcastrecord.requiredPermission != null && mService.checkComponentPermission(broadcastrecord.requiredPermission, broadcastfilter.receiverList.pid, broadcastfilter.receiverList.uid, -1, true) != 0) {
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Permission Denial: receiving ").append(broadcastrecord.intent.toString()).append(" to ").append(broadcastfilter.receiverList.app).append(" (pid=").append(broadcastfilter.receiverList.pid).append(", uid=").append(broadcastfilter.receiverList.uid).append(")").append(" requires ").append(broadcastrecord.requiredPermission).append(" due to sender ").append(broadcastrecord.callerPackage).append(" (uid ").append(broadcastrecord.callingUid).append(")").toString());
            flag1 = true;
        }
        if(flag1)
            break MISSING_BLOCK_LABEL_433;
        if(flag) {
            broadcastrecord.receiver = broadcastfilter.receiverList.receiver.asBinder();
            broadcastrecord.curFilter = broadcastfilter;
            broadcastfilter.receiverList.curBroadcast = broadcastrecord;
            broadcastrecord.state = 2;
            if(broadcastfilter.receiverList.app != null) {
                broadcastrecord.curApp = broadcastfilter.receiverList.app;
                broadcastfilter.receiverList.app.curReceiver = broadcastrecord;
                mService.updateOomAdjLocked();
            }
        }
        performReceiveLocked(broadcastfilter.receiverList.app, broadcastfilter.receiverList.receiver, new Intent(broadcastrecord.intent), broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, broadcastrecord.ordered, broadcastrecord.initialSticky);
        if(flag)
            broadcastrecord.state = 3;
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Failure sending broadcast ").append(broadcastrecord.intent).toString(), remoteexception);
        if(flag) {
            broadcastrecord.receiver = null;
            broadcastrecord.curFilter = null;
            broadcastfilter.receiverList.curBroadcast = null;
            if(broadcastfilter.receiverList.app != null)
                broadcastfilter.receiverList.app.curReceiver = null;
        }
          goto _L1
    }

    private static void performReceiveLocked(ProcessRecord processrecord, IIntentReceiver iintentreceiver, Intent intent, int i, String s, Bundle bundle, boolean flag, boolean flag1) throws RemoteException {
        if(processrecord != null && processrecord.thread != null)
            processrecord.thread.scheduleRegisteredReceiver(iintentreceiver, intent, i, s, bundle, flag, flag1);
        else
            iintentreceiver.performReceive(intent, i, s, bundle, flag, flag1);
    }

    private final void processCurBroadcastLocked(BroadcastRecord broadcastrecord, ProcessRecord processrecord) throws RemoteException {
        if(processrecord.thread == null)
            throw new RemoteException();
        broadcastrecord.receiver = processrecord.thread.asBinder();
        broadcastrecord.curApp = processrecord;
        processrecord.curReceiver = broadcastrecord;
        mService.updateLruProcessLocked(processrecord, true, true);
        broadcastrecord.intent.setComponent(broadcastrecord.curComponent);
        mService.ensurePackageDexOpt(broadcastrecord.intent.getComponent().getPackageName());
        processrecord.thread.scheduleReceiver(new Intent(broadcastrecord.intent), broadcastrecord.curReceiver, mService.compatibilityInfoForPackageLocked(broadcastrecord.curReceiver.applicationInfo), broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, broadcastrecord.ordered);
        if(false) {
            broadcastrecord.receiver = null;
            broadcastrecord.curApp = null;
            processrecord.curReceiver = null;
        }
        return;
        Exception exception;
        exception;
        if(true) {
            broadcastrecord.receiver = null;
            broadcastrecord.curApp = null;
            processrecord.curReceiver = null;
        }
        throw exception;
    }

    final void broadcastTimeoutLocked(boolean flag) {
        if(flag)
            mPendingBroadcastTimeoutMessage = false;
        if(mOrderedBroadcasts.size() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        long l = SystemClock.uptimeMillis();
        BroadcastRecord broadcastrecord = (BroadcastRecord)mOrderedBroadcasts.get(0);
        if(flag) {
            if(mService.mDidDexOpt) {
                mService.mDidDexOpt = false;
                setBroadcastTimeoutLocked(SystemClock.uptimeMillis() + mTimeoutPeriod);
                continue; /* Loop/switch isn't completed */
            }
            if(!mService.mProcessesReady)
                continue; /* Loop/switch isn't completed */
            long l1 = broadcastrecord.receiverTime + mTimeoutPeriod;
            if(l1 > l) {
                setBroadcastTimeoutLocked(l1);
                continue; /* Loop/switch isn't completed */
            }
        }
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Timeout of broadcast ").append(broadcastrecord).append(" - receiver=").append(broadcastrecord.receiver).append(", started ").append(l - broadcastrecord.receiverTime).append("ms ago").toString());
        broadcastrecord.receiverTime = l;
        broadcastrecord.anrCount = 1 + broadcastrecord.anrCount;
        if(broadcastrecord.nextReceiver <= 0) {
            Slog.w("BroadcastQueue", "Timeout on receiver with nextReceiver <= 0");
        } else {
            ProcessRecord processrecord = null;
            String s = null;
            Object obj = broadcastrecord.receivers.get(-1 + broadcastrecord.nextReceiver);
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Receiver during timeout: ").append(obj).toString());
            logBroadcastReceiverDiscardLocked(broadcastrecord);
            if(obj instanceof BroadcastFilter) {
                BroadcastFilter broadcastfilter = (BroadcastFilter)obj;
                if(broadcastfilter.receiverList.pid != 0 && broadcastfilter.receiverList.pid != ActivityManagerService.MY_PID)
                    synchronized(mService.mPidsSelfLocked) {
                        processrecord = (ProcessRecord)mService.mPidsSelfLocked.get(broadcastfilter.receiverList.pid);
                    }
            } else {
                processrecord = broadcastrecord.curApp;
            }
            if(processrecord != null)
                s = (new StringBuilder()).append("Broadcast of ").append(broadcastrecord.intent.toString()).toString();
            if(mPendingBroadcast == broadcastrecord)
                mPendingBroadcast = null;
            finishReceiverLocked(broadcastrecord, broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, broadcastrecord.resultAbort, true);
            scheduleBroadcastsLocked();
            if(s != null)
                mHandler.post(new AppNotResponding(processrecord, s));
        }
        if(true) goto _L1; else goto _L3
_L3:
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }

    final void cancelBroadcastTimeoutLocked() {
        if(mPendingBroadcastTimeoutMessage) {
            mHandler.removeMessages(201, this);
            mPendingBroadcastTimeoutMessage = false;
        }
    }

    final boolean dumpLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, String s, boolean flag1) {
        boolean flag3;
        int l;
        BroadcastRecord broadcastrecord;
        if(mParallelBroadcasts.size() > 0 || mOrderedBroadcasts.size() > 0 || mPendingBroadcast != null) {
            boolean flag2 = false;
            int j = -1 + mParallelBroadcasts.size();
            while(j >= 0)  {
                BroadcastRecord broadcastrecord2 = (BroadcastRecord)mParallelBroadcasts.get(j);
                if(s == null || s.equals(broadcastrecord2.callerPackage)) {
                    if(!flag2) {
                        if(flag1) {
                            printwriter.println();
                            flag1 = false;
                        }
                        flag2 = true;
                        printwriter.println((new StringBuilder()).append("  Active broadcasts [").append(mQueueName).append("]:").toString());
                    }
                    printwriter.println((new StringBuilder()).append("  Broadcast #").append(j).append(":").toString());
                    broadcastrecord2.dump(printwriter, "    ");
                }
                j--;
            }
            flag1 = true;
            int k = -1 + mOrderedBroadcasts.size();
            while(k >= 0)  {
                BroadcastRecord broadcastrecord1 = (BroadcastRecord)mOrderedBroadcasts.get(k);
                if(s == null || s.equals(broadcastrecord1.callerPackage)) {
                    if(true) {
                        if(flag1)
                            printwriter.println();
                        flag1 = true;
                        printwriter.println((new StringBuilder()).append("  Active ordered broadcasts [").append(mQueueName).append("]:").toString());
                    }
                    printwriter.println((new StringBuilder()).append("  Ordered Broadcast #").append(k).append(":").toString());
                    ((BroadcastRecord)mOrderedBroadcasts.get(k)).dump(printwriter, "    ");
                }
                k--;
            }
            if(s == null || mPendingBroadcast != null && s.equals(mPendingBroadcast.callerPackage)) {
                if(flag1)
                    printwriter.println();
                printwriter.println((new StringBuilder()).append("  Pending broadcast [").append(mQueueName).append("]:").toString());
                if(mPendingBroadcast != null)
                    mPendingBroadcast.dump(printwriter, "    ");
                else
                    printwriter.println("    (null)");
                flag1 = true;
            }
        }
        flag3 = false;
        l = 0;
_L4:
        if(l >= 25) goto _L2; else goto _L1
_L1:
        broadcastrecord = mBroadcastHistory[l];
        if(broadcastrecord != null) goto _L3; else goto _L2
_L2:
        return flag1;
_L3:
        if(s == null || s.equals(broadcastrecord.callerPackage)) {
label0:
            {
                if(!flag3) {
                    if(flag1)
                        printwriter.println();
                    flag1 = true;
                    printwriter.println((new StringBuilder()).append("  Historical broadcasts [").append(mQueueName).append("]:").toString());
                    flag3 = true;
                }
                if(!flag)
                    break label0;
                printwriter.print("  Historical Broadcast #");
                printwriter.print(l);
                printwriter.println(":");
                broadcastrecord.dump(printwriter, "    ");
            }
        }
_L5:
        l++;
          goto _L4
label1:
        {
            if(l < 50)
                break label1;
            printwriter.println("  ...");
        }
          goto _L2
        printwriter.print("  #");
        printwriter.print(l);
        printwriter.print(": ");
        printwriter.println(broadcastrecord);
          goto _L5
    }

    public void enqueueOrderedBroadcastLocked(BroadcastRecord broadcastrecord) {
        mOrderedBroadcasts.add(broadcastrecord);
    }

    public void enqueueParallelBroadcastLocked(BroadcastRecord broadcastrecord) {
        mParallelBroadcasts.add(broadcastrecord);
    }

    public boolean finishReceiverLocked(BroadcastRecord broadcastrecord, int i, String s, Bundle bundle, boolean flag, boolean flag1) {
        boolean flag2 = false;
        int j = broadcastrecord.state;
        broadcastrecord.state = 0;
        if(j == 0 && flag1)
            Slog.w("BroadcastQueue", (new StringBuilder()).append("finishReceiver [").append(mQueueName).append("] called but state is IDLE").toString());
        broadcastrecord.receiver = null;
        broadcastrecord.intent.setComponent(null);
        if(broadcastrecord.curApp != null)
            broadcastrecord.curApp.curReceiver = null;
        if(broadcastrecord.curFilter != null)
            broadcastrecord.curFilter.receiverList.curBroadcast = null;
        broadcastrecord.curFilter = null;
        broadcastrecord.curApp = null;
        broadcastrecord.curComponent = null;
        broadcastrecord.curReceiver = null;
        mPendingBroadcast = null;
        broadcastrecord.resultCode = i;
        broadcastrecord.resultData = s;
        broadcastrecord.resultExtras = bundle;
        broadcastrecord.resultAbort = flag;
        if(j == 1 || j == 3)
            flag2 = true;
        return flag2;
    }

    public BroadcastRecord getMatchingOrderedReceiver(IBinder ibinder) {
        if(mOrderedBroadcasts.size() <= 0) goto _L2; else goto _L1
_L1:
        BroadcastRecord broadcastrecord = (BroadcastRecord)mOrderedBroadcasts.get(0);
        if(broadcastrecord == null || broadcastrecord.receiver != ibinder) goto _L2; else goto _L3
_L3:
        return broadcastrecord;
_L2:
        broadcastrecord = null;
        if(true) goto _L3; else goto _L4
_L4:
    }

    public boolean isPendingBroadcastProcessLocked(int i) {
        boolean flag;
        if(mPendingBroadcast != null && mPendingBroadcast.curApp.pid == i)
            flag = true;
        else
            flag = false;
        return flag;
    }

    final void logBroadcastReceiverDiscardLocked(BroadcastRecord broadcastrecord) {
        if(broadcastrecord.nextReceiver > 0) {
            Object obj = broadcastrecord.receivers.get(-1 + broadcastrecord.nextReceiver);
            if(obj instanceof BroadcastFilter) {
                BroadcastFilter broadcastfilter = (BroadcastFilter)obj;
                Object aobj2[] = new Object[4];
                aobj2[0] = Integer.valueOf(System.identityHashCode(broadcastrecord));
                aobj2[1] = broadcastrecord.intent.getAction();
                aobj2[2] = Integer.valueOf(-1 + broadcastrecord.nextReceiver);
                aobj2[3] = Integer.valueOf(System.identityHashCode(broadcastfilter));
                EventLog.writeEvent(30024, aobj2);
            } else {
                Object aobj1[] = new Object[4];
                aobj1[0] = Integer.valueOf(System.identityHashCode(broadcastrecord));
                aobj1[1] = broadcastrecord.intent.getAction();
                aobj1[2] = Integer.valueOf(-1 + broadcastrecord.nextReceiver);
                aobj1[3] = ((ResolveInfo)obj).toString();
                EventLog.writeEvent(30025, aobj1);
            }
        } else {
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Discarding broadcast before first receiver is invoked: ").append(broadcastrecord).toString());
            Object aobj[] = new Object[4];
            aobj[0] = Integer.valueOf(System.identityHashCode(broadcastrecord));
            aobj[1] = broadcastrecord.intent.getAction();
            aobj[2] = Integer.valueOf(broadcastrecord.nextReceiver);
            aobj[3] = "NONE";
            EventLog.writeEvent(30025, aobj);
        }
    }

    final void processNextBroadcast(boolean flag) {
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        mService.updateCpuStats();
        if(flag)
            mBroadcastsScheduled = false;
        BroadcastRecord broadcastrecord1;
        for(; mParallelBroadcasts.size() > 0; addBroadcastToHistoryLocked(broadcastrecord1)) {
            broadcastrecord1 = (BroadcastRecord)mParallelBroadcasts.remove(0);
            broadcastrecord1.dispatchTime = SystemClock.uptimeMillis();
            broadcastrecord1.dispatchClockTime = System.currentTimeMillis();
            int j2 = broadcastrecord1.receivers.size();
            for(int k2 = 0; k2 < j2; k2++)
                deliverToRegisteredReceiverLocked(broadcastrecord1, (BroadcastFilter)broadcastrecord1.receivers.get(k2), false);

        }

        break MISSING_BLOCK_LABEL_126;
        Exception exception;
        exception;
        throw exception;
        if(mPendingBroadcast == null) goto _L2; else goto _L1
_L1:
        SparseArray sparsearray = mService.mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        if(mService.mPidsSelfLocked.get(mPendingBroadcast.curApp.pid) != null) goto _L4; else goto _L3
_L3:
        boolean flag7 = true;
_L7:
        if(flag7) goto _L6; else goto _L5
_L5:
        activitymanagerservice;
        JVM INSTR monitorexit ;
_L10:
        return;
_L4:
        flag7 = false;
          goto _L7
        Exception exception1;
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
_L6:
        Slog.w("BroadcastQueue", (new StringBuilder()).append("pending app  [").append(mQueueName).append("]").append(mPendingBroadcast.curApp).append(" died before responding to broadcast").toString());
        mPendingBroadcast.state = 0;
        mPendingBroadcast.nextReceiver = mPendingBroadcastRecvIndex;
        mPendingBroadcast = null;
          goto _L2
_L18:
        if(mOrderedBroadcasts.size() != 0) goto _L9; else goto _L8
_L8:
        boolean flag1;
        mService.scheduleAppGcsLocked();
        if(flag1)
            mService.updateOomAdjLocked();
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L9:
        BroadcastRecord broadcastrecord;
        boolean flag2;
        broadcastrecord = (BroadcastRecord)mOrderedBroadcasts.get(0);
        flag2 = false;
        if(broadcastrecord.receivers == null) goto _L12; else goto _L11
_L11:
        int i = broadcastrecord.receivers.size();
_L42:
        if(mService.mProcessesReady && broadcastrecord.dispatchTime > 0L) {
            long l4 = SystemClock.uptimeMillis();
            if(i > 0 && l4 > broadcastrecord.dispatchTime + 2L * mTimeoutPeriod * (long)i) {
                Slog.w("BroadcastQueue", (new StringBuilder()).append("Hung broadcast [").append(mQueueName).append("] discarded after timeout failure:").append(" now=").append(l4).append(" dispatchTime=").append(broadcastrecord.dispatchTime).append(" startTime=").append(broadcastrecord.receiverTime).append(" intent=").append(broadcastrecord.intent).append(" numReceivers=").append(i).append(" nextReceiver=").append(broadcastrecord.nextReceiver).append(" state=").append(broadcastrecord.state).toString());
                broadcastTimeoutLocked(false);
                flag2 = true;
                broadcastrecord.state = 0;
            }
        }
        if(broadcastrecord.state == 0) goto _L14; else goto _L13
_L13:
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L14:
        if(broadcastrecord.receivers != null && broadcastrecord.nextReceiver < i && !broadcastrecord.resultAbort && !flag2) goto _L16; else goto _L15
_L15:
        IIntentReceiver iintentreceiver = broadcastrecord.resultTo;
        if(iintentreceiver == null)
            break MISSING_BLOCK_LABEL_643;
        int j;
        Object obj;
        try {
            performReceiveLocked(broadcastrecord.callerApp, broadcastrecord.resultTo, new Intent(broadcastrecord.intent), broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, false, false);
            broadcastrecord.resultTo = null;
        }
        catch(RemoteException remoteexception3) {
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Failure [").append(mQueueName).append("] sending broadcast result of ").append(broadcastrecord.intent).toString(), remoteexception3);
        }
        cancelBroadcastTimeoutLocked();
        addBroadcastToHistoryLocked(broadcastrecord);
        mOrderedBroadcasts.remove(0);
        broadcastrecord = null;
        flag1 = true;
_L16:
        if(broadcastrecord == null) goto _L18; else goto _L17
_L17:
        j = broadcastrecord.nextReceiver;
        int k = j + 1;
        broadcastrecord.nextReceiver = k;
        long l = SystemClock.uptimeMillis();
        broadcastrecord.receiverTime = l;
        if(j == 0) {
            long l2 = broadcastrecord.receiverTime;
            broadcastrecord.dispatchTime = l2;
            long l3 = System.currentTimeMillis();
            broadcastrecord.dispatchClockTime = l3;
        }
        if(!mPendingBroadcastTimeoutMessage)
            setBroadcastTimeoutLocked(broadcastrecord.receiverTime + mTimeoutPeriod);
        obj = broadcastrecord.receivers.get(j);
        if(!(obj instanceof BroadcastFilter)) goto _L20; else goto _L19
_L19:
        BroadcastFilter broadcastfilter = (BroadcastFilter)obj;
        boolean flag6 = broadcastrecord.ordered;
        deliverToRegisteredReceiverLocked(broadcastrecord, broadcastfilter, flag6);
        if(broadcastrecord.receiver == null || !broadcastrecord.ordered) {
            broadcastrecord.state = 0;
            scheduleBroadcastsLocked();
        }
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L20:
        ResolveInfo resolveinfo;
        boolean flag3;
        resolveinfo = (ResolveInfo)obj;
        flag3 = false;
        if(mService.checkComponentPermission(resolveinfo.activityInfo.permission, broadcastrecord.callingPid, broadcastrecord.callingUid, resolveinfo.activityInfo.applicationInfo.uid, resolveinfo.activityInfo.exported) == 0) goto _L22; else goto _L21
_L21:
        if(resolveinfo.activityInfo.exported) goto _L24; else goto _L23
_L23:
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Permission Denial: broadcasting ").append(broadcastrecord.intent.toString()).append(" from ").append(broadcastrecord.callerPackage).append(" (pid=").append(broadcastrecord.callingPid).append(", uid=").append(broadcastrecord.callingUid).append(")").append(" is not exported from uid ").append(resolveinfo.activityInfo.applicationInfo.uid).append(" due to receiver ").append(((ComponentInfo) (resolveinfo.activityInfo)).packageName).append("/").append(((ComponentInfo) (resolveinfo.activityInfo)).name).toString());
          goto _L25
_L22:
        if(resolveinfo.activityInfo.applicationInfo.uid == 1000) goto _L27; else goto _L26
_L26:
        String s2 = broadcastrecord.requiredPermission;
        if(s2 == null) goto _L27; else goto _L28
_L28:
        int i2 = AppGlobals.getPackageManager().checkPermission(broadcastrecord.requiredPermission, resolveinfo.activityInfo.applicationInfo.packageName);
        int l1 = i2;
_L43:
        if(l1 == 0) goto _L27; else goto _L29
_L29:
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Permission Denial: receiving ").append(broadcastrecord.intent).append(" to ").append(resolveinfo.activityInfo.applicationInfo.packageName).append(" requires ").append(broadcastrecord.requiredPermission).append(" due to sender ").append(broadcastrecord.callerPackage).append(" (uid ").append(broadcastrecord.callingUid).append(")").toString());
        flag3 = true;
_L27:
        if(broadcastrecord.curApp != null && broadcastrecord.curApp.crashing)
            flag3 = true;
        if(!flag3) goto _L31; else goto _L30
_L30:
        broadcastrecord.receiver = null;
        broadcastrecord.curFilter = null;
        broadcastrecord.state = 0;
        scheduleBroadcastsLocked();
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L24:
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Permission Denial: broadcasting ").append(broadcastrecord.intent.toString()).append(" from ").append(broadcastrecord.callerPackage).append(" (pid=").append(broadcastrecord.callingPid).append(", uid=").append(broadcastrecord.callingUid).append(")").append(" requires ").append(resolveinfo.activityInfo.permission).append(" due to receiver ").append(((ComponentInfo) (resolveinfo.activityInfo)).packageName).append("/").append(((ComponentInfo) (resolveinfo.activityInfo)).name).toString());
          goto _L25
_L31:
        String s;
        broadcastrecord.state = 1;
        s = resolveinfo.activityInfo.processName;
        ComponentName componentname = new ComponentName(resolveinfo.activityInfo.applicationInfo.packageName, ((ComponentInfo) (resolveinfo.activityInfo)).name);
        broadcastrecord.curComponent = componentname;
        if(broadcastrecord.callingUid == 1000) goto _L33; else goto _L32
_L32:
        if(!mService.isSingleton(resolveinfo.activityInfo.processName, resolveinfo.activityInfo.applicationInfo)) goto _L35; else goto _L34
_L34:
        int k1 = 0;
_L39:
        resolveinfo.activityInfo = mService.getActivityInfoForUser(resolveinfo.activityInfo, k1);
_L33:
        ActivityInfo activityinfo = resolveinfo.activityInfo;
        broadcastrecord.curReceiver = activityinfo;
        ProcessRecord processrecord;
        ActivityManagerService activitymanagerservice1;
        ApplicationInfo applicationinfo;
        int i1;
        ComponentName componentname1;
        boolean flag4;
        ProcessRecord processrecord1;
        IApplicationThread iapplicationthread;
        RemoteException remoteexception1;
        try {
            AppGlobals.getPackageManager().setPackageStoppedState(broadcastrecord.curComponent.getPackageName(), false, UserId.getUserId(broadcastrecord.callingUid));
        }
        catch(RemoteException remoteexception) { }
        catch(IllegalArgumentException illegalargumentexception) {
            Slog.w("BroadcastQueue", (new StringBuilder()).append("Failed trying to unstop package ").append(broadcastrecord.curComponent.getPackageName()).append(": ").append(illegalargumentexception).toString());
        }
        processrecord = mService.getProcessRecordLocked(s, resolveinfo.activityInfo.applicationInfo.uid);
        if(processrecord == null) goto _L37; else goto _L36
_L36:
        iapplicationthread = processrecord.thread;
        if(iapplicationthread == null) goto _L37; else goto _L38
_L38:
        processrecord.addPackage(((ComponentInfo) (resolveinfo.activityInfo)).packageName);
        processCurBroadcastLocked(broadcastrecord, processrecord);
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L35:
        k1 = UserId.getUserId(broadcastrecord.callingUid);
          goto _L39
        remoteexception1;
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Exception when sending broadcast to ").append(broadcastrecord.curComponent).toString(), remoteexception1);
_L37:
        activitymanagerservice1 = mService;
        applicationinfo = resolveinfo.activityInfo.applicationInfo;
        i1 = 4 | broadcastrecord.intent.getFlags();
        componentname1 = broadcastrecord.curComponent;
        if((0x4000000 & broadcastrecord.intent.getFlags()) == 0)
            break MISSING_BLOCK_LABEL_2015;
        flag4 = true;
_L44:
        processrecord1 = activitymanagerservice1.startProcessLocked(s, applicationinfo, true, i1, "broadcast", componentname1, flag4, false);
        broadcastrecord.curApp = processrecord1;
        if(processrecord1 != null) goto _L41; else goto _L40
_L40:
        Slog.w("BroadcastQueue", (new StringBuilder()).append("Unable to launch app ").append(resolveinfo.activityInfo.applicationInfo.packageName).append("/").append(resolveinfo.activityInfo.applicationInfo.uid).append(" for broadcast ").append(broadcastrecord.intent).append(": process is bad").toString());
        logBroadcastReceiverDiscardLocked(broadcastrecord);
        int j1 = broadcastrecord.resultCode;
        String s1 = broadcastrecord.resultData;
        Bundle bundle = broadcastrecord.resultExtras;
        boolean flag5 = broadcastrecord.resultAbort;
        finishReceiverLocked(broadcastrecord, j1, s1, bundle, flag5, true);
        scheduleBroadcastsLocked();
        broadcastrecord.state = 0;
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L41:
        mPendingBroadcast = broadcastrecord;
        mPendingBroadcastRecvIndex = j;
        activitymanagerservice;
        JVM INSTR monitorexit ;
          goto _L10
_L2:
        flag1 = false;
          goto _L18
_L12:
        i = 0;
          goto _L42
_L25:
        flag3 = true;
          goto _L22
        RemoteException remoteexception2;
        remoteexception2;
        l1 = -1;
          goto _L43
        flag4 = false;
          goto _L44
    }

    public final boolean replaceOrderedBroadcastLocked(BroadcastRecord broadcastrecord) {
        int i = -1 + mOrderedBroadcasts.size();
_L3:
        if(i <= 0)
            break MISSING_BLOCK_LABEL_59;
        if(!broadcastrecord.intent.filterEquals(((BroadcastRecord)mOrderedBroadcasts.get(i)).intent)) goto _L2; else goto _L1
_L1:
        boolean flag;
        mOrderedBroadcasts.set(i, broadcastrecord);
        flag = true;
_L4:
        return flag;
_L2:
        i--;
          goto _L3
        flag = false;
          goto _L4
    }

    public final boolean replaceParallelBroadcastLocked(BroadcastRecord broadcastrecord) {
        int i = -1 + mParallelBroadcasts.size();
_L3:
        if(i < 0)
            break MISSING_BLOCK_LABEL_59;
        if(!broadcastrecord.intent.filterEquals(((BroadcastRecord)mParallelBroadcasts.get(i)).intent)) goto _L2; else goto _L1
_L1:
        boolean flag;
        mParallelBroadcasts.set(i, broadcastrecord);
        flag = true;
_L4:
        return flag;
_L2:
        i--;
          goto _L3
        flag = false;
          goto _L4
    }

    public void scheduleBroadcastsLocked() {
        if(!mBroadcastsScheduled) {
            mHandler.sendMessage(mHandler.obtainMessage(200, this));
            mBroadcastsScheduled = true;
        }
    }

    public boolean sendPendingBroadcastsLocked(ProcessRecord processrecord) {
        boolean flag = false;
        BroadcastRecord broadcastrecord = mPendingBroadcast;
        if(broadcastrecord != null && broadcastrecord.curApp.pid == processrecord.pid) {
            try {
                mPendingBroadcast = null;
                processCurBroadcastLocked(broadcastrecord, processrecord);
            }
            catch(Exception exception) {
                Slog.w("BroadcastQueue", (new StringBuilder()).append("Exception in new application when starting receiver ").append(broadcastrecord.curComponent.flattenToShortString()).toString(), exception);
                logBroadcastReceiverDiscardLocked(broadcastrecord);
                finishReceiverLocked(broadcastrecord, broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, broadcastrecord.resultAbort, true);
                scheduleBroadcastsLocked();
                broadcastrecord.state = 0;
                throw new RuntimeException(exception.getMessage());
            }
            flag = true;
        }
        return flag;
    }

    final void setBroadcastTimeoutLocked(long l) {
        if(!mPendingBroadcastTimeoutMessage) {
            Message message = mHandler.obtainMessage(201, this);
            mHandler.sendMessageAtTime(message, l);
            mPendingBroadcastTimeoutMessage = true;
        }
    }

    public void skipCurrentReceiverLocked(ProcessRecord processrecord) {
        boolean flag = false;
        BroadcastRecord broadcastrecord = processrecord.curReceiver;
        if(broadcastrecord != null) {
            logBroadcastReceiverDiscardLocked(broadcastrecord);
            finishReceiverLocked(broadcastrecord, broadcastrecord.resultCode, broadcastrecord.resultData, broadcastrecord.resultExtras, broadcastrecord.resultAbort, true);
            flag = true;
        }
        BroadcastRecord broadcastrecord1 = mPendingBroadcast;
        if(broadcastrecord1 != null && broadcastrecord1.curApp == processrecord) {
            logBroadcastReceiverDiscardLocked(broadcastrecord1);
            finishReceiverLocked(broadcastrecord1, broadcastrecord1.resultCode, broadcastrecord1.resultData, broadcastrecord1.resultExtras, broadcastrecord1.resultAbort, true);
            flag = true;
        }
        if(flag)
            scheduleBroadcastsLocked();
    }

    public void skipPendingBroadcastLocked(int i) {
        BroadcastRecord broadcastrecord = mPendingBroadcast;
        if(broadcastrecord != null && broadcastrecord.curApp.pid == i) {
            broadcastrecord.state = 0;
            broadcastrecord.nextReceiver = mPendingBroadcastRecvIndex;
            mPendingBroadcast = null;
            scheduleBroadcastsLocked();
        }
    }

    static final int BROADCAST_INTENT_MSG = 200;
    static final int BROADCAST_TIMEOUT_MSG = 201;
    static final boolean DEBUG_BROADCAST = false;
    static final boolean DEBUG_BROADCAST_LIGHT = false;
    static final boolean DEBUG_MU = false;
    static final int MAX_BROADCAST_HISTORY = 25;
    static final String TAG = "BroadcastQueue";
    static final String TAG_MU = "ActivityManagerServiceMU";
    final BroadcastRecord mBroadcastHistory[] = new BroadcastRecord[25];
    boolean mBroadcastsScheduled;
    final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 200 201: default 28
        //                       200 29
        //                       201 40;
               goto _L1 _L2 _L3
_L1:
            return;
_L2:
            processNextBroadcast(true);
            continue; /* Loop/switch isn't completed */
_L3:
            ActivityManagerService activitymanagerservice1 = mService;
            activitymanagerservice1;
            JVM INSTR monitorenter ;
            broadcastTimeoutLocked(true);
            if(true) goto _L1; else goto _L4
_L4:
        }

        final BroadcastQueue this$0;

             {
                this$0 = BroadcastQueue.this;
                super();
            }
    };
    final ArrayList mOrderedBroadcasts = new ArrayList();
    final ArrayList mParallelBroadcasts = new ArrayList();
    BroadcastRecord mPendingBroadcast;
    int mPendingBroadcastRecvIndex;
    boolean mPendingBroadcastTimeoutMessage;
    final String mQueueName;
    final ActivityManagerService mService;
    final long mTimeoutPeriod;
}
