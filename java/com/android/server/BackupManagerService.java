// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.app.backup.*;
import android.content.*;
import android.content.pm.*;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.*;
import android.os.storage.IMountService;
import android.util.*;
import com.android.internal.backup.IBackupTransport;
import com.android.internal.backup.LocalTransport;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.*;

// Referenced classes of package com.android.server:
//            PackageManagerBackupAgent

class BackupManagerService extends android.app.backup.IBackupManager.Stub {
    class ActiveRestoreSession extends android.app.backup.IRestoreSession.Stub {
        class EndRestoreRunnable
            implements Runnable {

            public void run() {
                ActiveRestoreSession activerestoresession = mSession;
                activerestoresession;
                JVM INSTR monitorenter ;
                if(mSession.mRestoreTransport != null)
                    mSession.mRestoreTransport.finishRestore();
                mSession.mRestoreTransport = null;
                mSession.mEnded = true;
_L1:
                activerestoresession;
                JVM INSTR monitorexit ;
                mBackupManager.clearRestoreSession(mSession);
                return;
                Exception exception2;
                exception2;
                Slog.e("RestoreSession", "Error in finishRestore", exception2);
                mSession.mRestoreTransport = null;
                mSession.mEnded = true;
                  goto _L1
                Exception exception1;
                exception1;
                throw exception1;
                Exception exception;
                exception;
                mSession.mRestoreTransport = null;
                mSession.mEnded = true;
                throw exception;
            }

            BackupManagerService mBackupManager;
            ActiveRestoreSession mSession;
            final ActiveRestoreSession this$1;

            EndRestoreRunnable(ActiveRestoreSession activerestoresession1) {
                this$1 = ActiveRestoreSession.this;
                super();
                mBackupManager = BackupManagerService.this;
                mSession = activerestoresession1;
            }
        }


        /**
         * @deprecated Method endRestoreSession is deprecated
         */

        public void endRestoreSession() {
            this;
            JVM INSTR monitorenter ;
            Slog.d("RestoreSession", "endRestoreSession");
            if(mEnded)
                throw new IllegalStateException("Restore session already ended");
            break MISSING_BLOCK_LABEL_32;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
            mBackupHandler.post(new EndRestoreRunnable(this));
            this;
            JVM INSTR monitorexit ;
        }

        /**
         * @deprecated Method getAvailableRestoreSets is deprecated
         */

        public int getAvailableRestoreSets(IRestoreObserver irestoreobserver) {
            byte byte0 = -1;
            this;
            JVM INSTR monitorenter ;
            mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getAvailableRestoreSets");
            if(irestoreobserver == null)
                throw new IllegalArgumentException("Observer must not be null");
            break MISSING_BLOCK_LABEL_38;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
            long l;
            if(mEnded)
                throw new IllegalStateException("Restore session already ended");
            l = Binder.clearCallingIdentity();
            if(mRestoreTransport != null) goto _L2; else goto _L1
_L1:
            Slog.w("RestoreSession", "Null transport getting restore sets");
            Binder.restoreCallingIdentity(l);
_L3:
            this;
            JVM INSTR monitorexit ;
            return byte0;
_L2:
            mWakelock.acquire();
            Message message = mBackupHandler.obtainMessage(6, new RestoreGetSetsParams(mRestoreTransport, this, irestoreobserver));
            mBackupHandler.sendMessage(message);
            byte0 = 0;
            Binder.restoreCallingIdentity(l);
              goto _L3
            Exception exception2;
            exception2;
            Slog.e("RestoreSession", "Error in getAvailableRestoreSets", exception2);
            Binder.restoreCallingIdentity(l);
              goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l);
            throw exception1;
        }

        /**
         * @deprecated Method restoreAll is deprecated
         */

        public int restoreAll(long l, IRestoreObserver irestoreobserver) {
            byte byte0 = -1;
            this;
            JVM INSTR monitorenter ;
            mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "performRestore");
            Slog.d("RestoreSession", (new StringBuilder()).append("restoreAll token=").append(Long.toHexString(l)).append(" observer=").append(irestoreobserver).toString());
            if(mEnded)
                throw new IllegalStateException("Restore session already ended");
            break MISSING_BLOCK_LABEL_81;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
            if(mRestoreTransport != null && mRestoreSets != null) goto _L2; else goto _L1
_L1:
            Slog.e("RestoreSession", "Ignoring restoreAll() with no restore set");
_L4:
            this;
            JVM INSTR monitorexit ;
            return byte0;
_L2:
label0:
            {
                if(mPackageName == null)
                    break label0;
                Slog.e("RestoreSession", "Ignoring restoreAll() on single-package session");
            }
            if(true) goto _L4; else goto _L3
_L3:
            Object obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            int i = 0;
_L5:
            if(i >= mRestoreSets.length)
                break MISSING_BLOCK_LABEL_258;
            if(l != mRestoreSets[i].token)
                break MISSING_BLOCK_LABEL_252;
            long l1 = Binder.clearCallingIdentity();
            mWakelock.acquire();
            Message message = mBackupHandler.obtainMessage(3);
            message.obj = new RestoreParams(mRestoreTransport, irestoreobserver, l, true);
            mBackupHandler.sendMessage(message);
            Binder.restoreCallingIdentity(l1);
            byte0 = 0;
              goto _L4
            Exception exception1;
            exception1;
            throw exception1;
            i++;
              goto _L5
            obj;
            JVM INSTR monitorexit ;
            Slog.w("RestoreSession", (new StringBuilder()).append("Restore token ").append(Long.toHexString(l)).append(" not found").toString());
              goto _L4
        }

        /**
         * @deprecated Method restorePackage is deprecated
         */

        public int restorePackage(String s, IRestoreObserver irestoreobserver) {
            this;
            JVM INSTR monitorenter ;
            Slog.v("RestoreSession", (new StringBuilder()).append("restorePackage pkg=").append(s).append(" obs=").append(irestoreobserver).toString());
            if(mEnded)
                throw new IllegalStateException("Restore session already ended");
            break MISSING_BLOCK_LABEL_58;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
            if(mPackageName == null || mPackageName.equals(s)) goto _L2; else goto _L1
_L1:
            Slog.e("RestoreSession", (new StringBuilder()).append("Ignoring attempt to restore pkg=").append(s).append(" on session for package ").append(mPackageName).toString());
            byte byte0 = -1;
_L4:
            this;
            JVM INSTR monitorexit ;
            return byte0;
_L2:
            PackageInfo packageinfo;
            try {
                packageinfo = mPackageManager.getPackageInfo(s, 0);
            }
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                Slog.w("RestoreSession", (new StringBuilder()).append("Asked to restore nonexistent pkg ").append(s).toString());
                byte0 = -1;
                continue; /* Loop/switch isn't completed */
            }
            if(mContext.checkPermission("android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid()) == -1 && packageinfo.applicationInfo.uid != Binder.getCallingUid()) {
                Slog.w("RestoreSession", (new StringBuilder()).append("restorePackage: bad packageName=").append(s).append(" or calling uid=").append(Binder.getCallingUid()).toString());
                throw new SecurityException("No permission to restore other packages");
            }
            if(packageinfo.applicationInfo.backupAgentName == null) {
                Slog.w("RestoreSession", (new StringBuilder()).append("Asked to restore package ").append(s).append(" with no agent").toString());
                byte0 = -1;
                continue; /* Loop/switch isn't completed */
            }
            long l = getAvailableRestoreToken(s);
            if(l == 0L) {
                Slog.w("RestoreSession", "No data available for this package; not restoring");
                byte0 = -1;
                continue; /* Loop/switch isn't completed */
            }
            long l1 = Binder.clearCallingIdentity();
            mWakelock.acquire();
            Message message = mBackupHandler.obtainMessage(3);
            message.obj = new RestoreParams(mRestoreTransport, irestoreobserver, l, packageinfo, 0, false);
            mBackupHandler.sendMessage(message);
            Binder.restoreCallingIdentity(l1);
            byte0 = 0;
            if(true) goto _L4; else goto _L3
_L3:
        }

        /**
         * @deprecated Method restoreSome is deprecated
         */

        public int restoreSome(long l, IRestoreObserver irestoreobserver, String as[]) {
            this;
            JVM INSTR monitorenter ;
            StringBuilder stringbuilder;
            mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "performRestore");
            stringbuilder = new StringBuilder(128);
            stringbuilder.append("restoreSome token=");
            stringbuilder.append(Long.toHexString(l));
            stringbuilder.append(" observer=");
            stringbuilder.append(irestoreobserver.toString());
            stringbuilder.append(" packages=");
            if(as != null) goto _L2; else goto _L1
_L1:
            stringbuilder.append("null");
_L9:
            Slog.d("RestoreSession", stringbuilder.toString());
            if(mEnded)
                throw new IllegalStateException("Restore session already ended");
              goto _L3
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
_L2:
            boolean flag;
            int i;
            int j;
            stringbuilder.append('{');
            flag = true;
            i = as.length;
            j = 0;
_L8:
            if(j >= i) goto _L5; else goto _L4
_L4:
            String s = as[j];
            if(flag) goto _L7; else goto _L6
_L6:
            stringbuilder.append(", ");
_L15:
            stringbuilder.append(s);
            j++;
              goto _L8
_L5:
            stringbuilder.append('}');
              goto _L9
_L3:
            if(mRestoreTransport != null && mRestoreSets != null) goto _L11; else goto _L10
_L10:
            Slog.e("RestoreSession", "Ignoring restoreAll() with no restore set");
            byte byte0 = -1;
_L14:
            this;
            JVM INSTR monitorexit ;
            return byte0;
_L11:
            if(mPackageName != null) {
                Slog.e("RestoreSession", "Ignoring restoreAll() on single-package session");
                byte0 = -1;
                continue; /* Loop/switch isn't completed */
            }
            Object obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            int k = 0;
_L13:
            if(k >= mRestoreSets.length)
                break; /* Loop/switch isn't completed */
            if(l == mRestoreSets[k].token) {
                long l1 = Binder.clearCallingIdentity();
                mWakelock.acquire();
                Message message = mBackupHandler.obtainMessage(3);
                message.obj = new RestoreParams(mRestoreTransport, irestoreobserver, l, as, true);
                mBackupHandler.sendMessage(message);
                Binder.restoreCallingIdentity(l1);
                byte0 = 0;
                continue; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_376;
            Exception exception1;
            exception1;
            throw exception1;
            k++;
            if(true) goto _L13; else goto _L12
_L12:
            obj;
            JVM INSTR monitorexit ;
            Slog.w("RestoreSession", (new StringBuilder()).append("Restore token ").append(Long.toHexString(l)).append(" not found").toString());
            byte0 = -1;
            if(true) goto _L14; else goto _L7
_L7:
            flag = false;
              goto _L15
        }

        private static final String TAG = "RestoreSession";
        boolean mEnded;
        private String mPackageName;
        RestoreSet mRestoreSets[];
        private IBackupTransport mRestoreTransport;
        final BackupManagerService this$0;



/*
        static IBackupTransport access$2002(ActiveRestoreSession activerestoresession, IBackupTransport ibackuptransport) {
            activerestoresession.mRestoreTransport = ibackuptransport;
            return ibackuptransport;
        }

*/

        ActiveRestoreSession(String s, String s1) {
            this$0 = BackupManagerService.this;
            super();
            mRestoreTransport = null;
            mRestoreSets = null;
            mEnded = false;
            mPackageName = s;
            mRestoreTransport = getTransport(s1);
        }
    }

    class PerformInitializeTask
        implements Runnable {

        public void run() {
            Iterator iterator = mQueue.iterator();
_L3:
            String s;
            IBackupTransport ibackuptransport;
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_452;
            s = (String)iterator.next();
            ibackuptransport = getTransport(s);
            if(ibackuptransport != null) goto _L2; else goto _L1
_L1:
            Slog.e("BackupManagerService", (new StringBuilder()).append("Requested init for ").append(s).append(" but not found").toString());
              goto _L3
            RemoteException remoteexception;
            remoteexception;
            android.os.PowerManager.WakeLock wakelock = mWakelock;
_L4:
            wakelock.release();
            return;
_L2:
            Slog.i("BackupManagerService", (new StringBuilder()).append("Initializing (wiping) backup transport storage: ").append(s).toString());
            EventLog.writeEvent(2821, ibackuptransport.transportDirName());
            long l = SystemClock.elapsedRealtime();
            int i = ibackuptransport.initializeDevice();
            if(i == 0)
                i = ibackuptransport.finishBackup();
            if(i != 0)
                break MISSING_BLOCK_LABEL_317;
            Slog.i("BackupManagerService", "Device init successful");
            int j = (int)(SystemClock.elapsedRealtime() - l);
            EventLog.writeEvent(2827, new Object[0]);
            resetBackupState(new File(mBaseStateDir, ibackuptransport.transportDirName()));
            Object aobj[] = new Object[2];
            aobj[0] = Integer.valueOf(0);
            aobj[1] = Integer.valueOf(j);
            EventLog.writeEvent(2825, aobj);
            Object obj1 = mQueueLock;
            obj1;
            JVM INSTR monitorenter ;
            recordInitPendingLocked(false, s);
            obj1;
            JVM INSTR monitorexit ;
              goto _L3
            Exception exception1;
            exception1;
            Slog.e("BackupManagerService", "Unexpected error performing init", exception1);
            wakelock = mWakelock;
              goto _L4
            Slog.e("BackupManagerService", "Transport error in initializeDevice()");
            EventLog.writeEvent(2822, "(initialize)");
            synchronized(mQueueLock) {
                recordInitPendingLocked(true, s);
            }
            long l1 = ibackuptransport.requestBackupTime();
            Slog.w("BackupManagerService", (new StringBuilder()).append("init failed on ").append(s).append(" resched in ").append(l1).toString());
            mAlarmManager.set(0, l1 + System.currentTimeMillis(), mRunInitIntent);
              goto _L3
            Exception exception;
            exception;
            mWakelock.release();
            throw exception;
            exception2;
            obj;
            JVM INSTR monitorexit ;
            throw exception2;
            wakelock = mWakelock;
              goto _L4
        }

        HashSet mQueue;
        final BackupManagerService this$0;

        PerformInitializeTask(HashSet hashset) {
            this$0 = BackupManagerService.this;
            super();
            mQueue = hashset;
        }
    }

    class PerformClearTask
        implements Runnable {

        public void run() {
            (new File(new File(mBaseStateDir, mTransport.transportDirName()), mPackage.packageName)).delete();
            mTransport.clearBackupData(mPackage);
            RemoteException remoteexception;
            android.os.PowerManager.WakeLock wakelock;
            Exception exception;
            Exception exception1;
            try {
                mTransport.finishBackup();
            }
            catch(RemoteException remoteexception4) { }
            wakelock = mWakelock;
_L2:
            wakelock.release();
            return;
            exception1;
            Slog.e("BackupManagerService", (new StringBuilder()).append("Transport threw attempting to clear data for ").append(mPackage).toString());
            try {
                mTransport.finishBackup();
            }
            catch(RemoteException remoteexception3) { }
            wakelock = mWakelock;
            continue; /* Loop/switch isn't completed */
            exception;
            try {
                mTransport.finishBackup();
            }
            catch(RemoteException remoteexception2) { }
            mWakelock.release();
            throw exception;
            remoteexception;
            try {
                mTransport.finishBackup();
            }
            catch(RemoteException remoteexception1) { }
            wakelock = mWakelock;
            if(true) goto _L2; else goto _L1
_L1:
        }

        PackageInfo mPackage;
        IBackupTransport mTransport;
        final BackupManagerService this$0;

        PerformClearTask(IBackupTransport ibackuptransport, PackageInfo packageinfo) {
            this$0 = BackupManagerService.this;
            super();
            mTransport = ibackuptransport;
            mPackage = packageinfo;
        }
    }

    class PerformRestoreTask
        implements BackupRestoreTask {
        class RestoreRequest {

            public PackageInfo app;
            public int storedAppVersion;
            final PerformRestoreTask this$1;

            RestoreRequest(PackageInfo packageinfo, int i) {
                this$1 = PerformRestoreTask.this;
                super();
                app = packageinfo;
                storedAppVersion = i;
            }
        }


        void agentCleanup() {
            mBackupDataName.delete();
            Object obj;
            try {
                if(mBackupData != null)
                    mBackupData.close();
            }
            catch(IOException ioexception) { }
            try {
                if(mNewState != null)
                    mNewState.close();
            }
            catch(IOException ioexception1) { }
            mNewState = null;
            mBackupData = null;
            mNewStateName.delete();
            if(mCurrentPackage.applicationInfo != null)
                try {
                    mActivityManager.unbindBackupAgent(mCurrentPackage.applicationInfo);
                    if(mTargetPackage == null && (0x10000 & mCurrentPackage.applicationInfo.flags) != 0) {
                        Slog.d("BackupManagerService", (new StringBuilder()).append("Restore complete, killing host process of ").append(mCurrentPackage.applicationInfo.processName).toString());
                        mActivityManager.killApplicationProcess(mCurrentPackage.applicationInfo.processName, mCurrentPackage.applicationInfo.uid);
                    }
                }
                catch(RemoteException remoteexception) { }
            mBackupHandler.removeMessages(7, this);
            obj = mCurrentOpLock;
            obj;
            JVM INSTR monitorenter ;
            mCurrentOperations.clear();
            return;
        }

        void agentErrorCleanup() {
            clearApplicationDataSynchronous(mCurrentPackage.packageName);
            agentCleanup();
        }

        void beginRestore() {
            mBackupHandler.removeMessages(8);
            mStatus = 1;
            Object aobj[] = new Object[2];
            aobj[0] = mTransport.transportDirName();
            aobj[1] = Long.valueOf(mToken);
            EventLog.writeEvent(2830, aobj);
            mRestorePackages = new ArrayList();
            PackageInfo packageinfo = new PackageInfo();
            packageinfo.packageName = "@pm@";
            mRestorePackages.add(packageinfo);
            mAgentPackages = allAgentPackages();
            if(mTargetPackage != null) goto _L2; else goto _L1
_L1:
            if(mFilterSet != null) {
                RemoteException remoteexception;
                IRestoreObserver irestoreobserver;
                RemoteException remoteexception1;
                for(int i = -1 + mAgentPackages.size(); i >= 0; i--) {
                    PackageInfo packageinfo1 = (PackageInfo)mAgentPackages.get(i);
                    if(!mFilterSet.contains(packageinfo1.packageName))
                        mAgentPackages.remove(i);
                    break MISSING_BLOCK_LABEL_285;
                }

            }
            mRestorePackages.addAll(mAgentPackages);
_L3:
            irestoreobserver = mObserver;
            if(irestoreobserver == null)
                break MISSING_BLOCK_LABEL_218;
            mObserver.restoreStarting(mRestorePackages.size());
_L5:
            mStatus = 0;
            executeNextState(RestoreState.DOWNLOAD_DATA);
_L4:
            return;
_L2:
            mRestorePackages.add(mTargetPackage);
              goto _L3
            remoteexception;
            Slog.e("BackupManagerService", "Error communicating with transport for restore");
            executeNextState(RestoreState.FINAL);
              goto _L4
            remoteexception1;
            Slog.d("BackupManagerService", "Restore observer died at restoreStarting");
            mObserver = null;
              goto _L5
        }

        void downloadRestoreData() {
            mStatus = mTransport.startRestore(mToken, (PackageInfo[])mRestorePackages.toArray(new PackageInfo[0]));
            if(mStatus == 0)
                break MISSING_BLOCK_LABEL_102;
            Slog.e("BackupManagerService", "Error starting restore operation");
            EventLog.writeEvent(2831, new Object[0]);
            executeNextState(RestoreState.FINAL);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.e("BackupManagerService", "Error communicating with transport for restore");
            EventLog.writeEvent(2831, new Object[0]);
            mStatus = 1;
            executeNextState(RestoreState.FINAL);
              goto _L1
            executeNextState(RestoreState.PM_METADATA);
              goto _L1
        }

        public void execute() {
            class _cls4 {

                static final int $SwitchMap$com$android$server$BackupManagerService$BackupState[];
                static final int $SwitchMap$com$android$server$BackupManagerService$RestorePolicy[];
                static final int $SwitchMap$com$android$server$BackupManagerService$RestoreState[];

                static  {
                    $SwitchMap$com$android$server$BackupManagerService$RestoreState = new int[RestoreState.values().length];
                    NoSuchFieldError nosuchfielderror10;
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestoreState[RestoreState.INITIAL.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestoreState[RestoreState.DOWNLOAD_DATA.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestoreState[RestoreState.PM_METADATA.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestoreState[RestoreState.RUNNING_QUEUE.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestoreState[RestoreState.FINAL.ordinal()] = 5;
                    }
                    catch(NoSuchFieldError nosuchfielderror4) { }
                    $SwitchMap$com$android$server$BackupManagerService$RestorePolicy = new int[RestorePolicy.values().length];
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestorePolicy[RestorePolicy.IGNORE.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror5) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestorePolicy[RestorePolicy.ACCEPT_IF_APK.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror6) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$RestorePolicy[RestorePolicy.ACCEPT.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror7) { }
                    $SwitchMap$com$android$server$BackupManagerService$BackupState = new int[BackupState.values().length];
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$BackupState[BackupState.INITIAL.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror8) { }
                    try {
                        $SwitchMap$com$android$server$BackupManagerService$BackupState[BackupState.RUNNING_QUEUE.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror9) { }
                    $SwitchMap$com$android$server$BackupManagerService$BackupState[BackupState.FINAL.ordinal()] = 3;
_L2:
                    return;
                    nosuchfielderror10;
                    if(true) goto _L2; else goto _L1
_L1:
                }
            }

            _cls4..SwitchMap.com.android.server.BackupManagerService.RestoreState[mCurrentState.ordinal()];
            JVM INSTR tableswitch 1 5: default 44
        //                       1 45
        //                       2 52
        //                       3 59
        //                       4 66
        //                       5 73;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
            return;
_L2:
            beginRestore();
            continue; /* Loop/switch isn't completed */
_L3:
            downloadRestoreData();
            continue; /* Loop/switch isn't completed */
_L4:
            restorePmMetadata();
            continue; /* Loop/switch isn't completed */
_L5:
            restoreNextAgent();
            continue; /* Loop/switch isn't completed */
_L6:
            if(!mFinished)
                finalizeRestore();
            else
                Slog.e("BackupManagerService", "Duplicate finish");
            mFinished = true;
            if(true) goto _L1; else goto _L7
_L7:
        }

        void executeNextState(RestoreState restorestate) {
            mCurrentState = restorestate;
            Message message = mBackupHandler.obtainMessage(20, this);
            mBackupHandler.sendMessage(message);
        }

        void finalizeRestore() {
            try {
                mTransport.finishRestore();
            }
            catch(RemoteException remoteexception) {
                Slog.e("BackupManagerService", "Error finishing restore", remoteexception);
            }
            if(mObserver != null)
                try {
                    mObserver.restoreFinished(mStatus);
                }
                catch(RemoteException remoteexception2) {
                    Slog.d("BackupManagerService", "Restore observer died at restoreFinished");
                }
            if(mTargetPackage == null && mPmAgent != null) {
                mAncestralPackages = mPmAgent.getRestoredPackages();
                mAncestralToken = mToken;
                writeRestoreTokens();
            }
            if(mPmToken > 0)
                try {
                    mPackageManagerBinder.finishPackageInstall(mPmToken);
                }
                catch(RemoteException remoteexception1) { }
            mBackupHandler.removeMessages(8);
            mBackupHandler.sendEmptyMessageDelayed(8, 60000L);
            Slog.i("BackupManagerService", "Restore complete.");
            mWakelock.release();
        }

        public void handleTimeout() {
            Slog.e("BackupManagerService", (new StringBuilder()).append("Timeout restoring application ").append(mCurrentPackage.packageName).toString());
            Object aobj[] = new Object[2];
            aobj[0] = mCurrentPackage.packageName;
            aobj[1] = "restore timeout";
            EventLog.writeEvent(2832, aobj);
            agentErrorCleanup();
            executeNextState(RestoreState.RUNNING_QUEUE);
        }

        void initiateOneRestore(PackageInfo packageinfo, int i, IBackupAgent ibackupagent, boolean flag) {
            mCurrentPackage = packageinfo;
            String s = packageinfo.packageName;
            Slog.d("BackupManagerService", (new StringBuilder()).append("initiateOneRestore packageName=").append(s).toString());
            mBackupDataName = new File(mDataDir, (new StringBuilder()).append(s).append(".restore").toString());
            mNewStateName = new File(mStateDir, (new StringBuilder()).append(s).append(".new").toString());
            mSavedStateName = new File(mStateDir, s);
            int j = generateToken();
            try {
                mBackupData = ParcelFileDescriptor.open(mBackupDataName, 0x3c000000);
                if(mTransport.getRestoreData(mBackupData) != 0) {
                    Slog.e("BackupManagerService", (new StringBuilder()).append("Error getting restore data for ").append(s).toString());
                    EventLog.writeEvent(2831, new Object[0]);
                    mBackupData.close();
                    mBackupDataName.delete();
                    executeNextState(RestoreState.FINAL);
                } else {
                    mBackupData.close();
                    mBackupData = ParcelFileDescriptor.open(mBackupDataName, 0x10000000);
                    mNewState = ParcelFileDescriptor.open(mNewStateName, 0x3c000000);
                    prepareOperationTimeout(j, 60000L, this);
                    ibackupagent.doRestore(mBackupData, i, mNewState, j, mBackupManagerBinder);
                }
            }
            catch(Exception exception) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Unable to call app for restore: ").append(s).toString(), exception);
                Object aobj[] = new Object[2];
                aobj[0] = s;
                aobj[1] = exception.toString();
                EventLog.writeEvent(2832, aobj);
                agentErrorCleanup();
                executeNextState(RestoreState.RUNNING_QUEUE);
            }
        }

        public void operationComplete() {
            int i = (int)mBackupDataName.length();
            Object aobj[] = new Object[2];
            aobj[0] = mCurrentPackage.packageName;
            aobj[1] = Integer.valueOf(i);
            EventLog.writeEvent(2833, aobj);
            agentCleanup();
            executeNextState(RestoreState.RUNNING_QUEUE);
        }

        void restoreNextAgent() {
            String s;
            try {
                s = mTransport.nextRestorePackage();
                if(s == null) {
                    Slog.e("BackupManagerService", "Error getting next restore package");
                    EventLog.writeEvent(2831, new Object[0]);
                    executeNextState(RestoreState.FINAL);
                    break MISSING_BLOCK_LABEL_818;
                }
                if(s.equals("")) {
                    Slog.v("BackupManagerService", "No next package, finishing restore");
                    int i = (int)(SystemClock.elapsedRealtime() - mStartRealtime);
                    Object aobj5[] = new Object[2];
                    aobj5[0] = Integer.valueOf(mCount);
                    aobj5[1] = Integer.valueOf(i);
                    EventLog.writeEvent(2834, aobj5);
                    executeNextState(RestoreState.FINAL);
                    break MISSING_BLOCK_LABEL_818;
                }
            }
            catch(RemoteException remoteexception) {
                Slog.e("BackupManagerService", "Unable to fetch restore data from transport");
                mStatus = 1;
                executeNextState(RestoreState.FINAL);
                break MISSING_BLOCK_LABEL_818;
            }
            IRestoreObserver irestoreobserver = mObserver;
            PackageManagerBackupAgent.Metadata metadata;
            if(irestoreobserver != null)
                try {
                    mObserver.onUpdate(mCount, s);
                }
                catch(RemoteException remoteexception1) {
                    Slog.d("BackupManagerService", "Restore observer died in onUpdate");
                    mObserver = null;
                }
            metadata = mPmAgent.getRestoredMetadata(s);
            if(metadata == null) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Missing metadata for ").append(s).toString());
                Object aobj4[] = new Object[2];
                aobj4[0] = s;
                aobj4[1] = "Package metadata missing";
                EventLog.writeEvent(2832, aobj4);
                executeNextState(RestoreState.RUNNING_QUEUE);
                break MISSING_BLOCK_LABEL_818;
            }
            PackageInfo packageinfo;
            IBackupAgent ibackupagent;
            try {
                packageinfo = mPackageManager.getPackageInfo(s, 64);
            }
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                Slog.e("BackupManagerService", "Invalid package restoring data", namenotfoundexception);
                Object aobj[] = new Object[2];
                aobj[0] = s;
                aobj[1] = "Package missing on device";
                EventLog.writeEvent(2832, aobj);
                executeNextState(RestoreState.RUNNING_QUEUE);
                break MISSING_BLOCK_LABEL_818;
            }
            if(metadata.versionCode > packageinfo.versionCode) {
                if((0x20000 & packageinfo.applicationInfo.flags) == 0) {
                    String s1 = (new StringBuilder()).append("Version ").append(metadata.versionCode).append(" > installed version ").append(packageinfo.versionCode).toString();
                    Slog.w("BackupManagerService", (new StringBuilder()).append("Package ").append(s).append(": ").append(s1).toString());
                    Object aobj3[] = new Object[2];
                    aobj3[0] = s;
                    aobj3[1] = s1;
                    EventLog.writeEvent(2832, aobj3);
                    executeNextState(RestoreState.RUNNING_QUEUE);
                    break MISSING_BLOCK_LABEL_818;
                }
                Slog.v("BackupManagerService", (new StringBuilder()).append("Version ").append(metadata.versionCode).append(" > installed ").append(packageinfo.versionCode).append(" but restoreAnyVersion").toString());
            }
            if(!signaturesMatch(metadata.signatures, packageinfo)) {
                Slog.w("BackupManagerService", (new StringBuilder()).append("Signature mismatch restoring ").append(s).toString());
                Object aobj2[] = new Object[2];
                aobj2[0] = s;
                aobj2[1] = "Signature mismatch";
                EventLog.writeEvent(2832, aobj2);
                executeNextState(RestoreState.RUNNING_QUEUE);
                break MISSING_BLOCK_LABEL_818;
            }
            Slog.v("BackupManagerService", (new StringBuilder()).append("Package ").append(s).append(" restore version [").append(metadata.versionCode).append("] is compatible with installed version [").append(packageinfo.versionCode).append("]").toString());
            ibackupagent = bindToAgentSynchronous(packageinfo.applicationInfo, 0);
            if(ibackupagent == null) {
                Slog.w("BackupManagerService", (new StringBuilder()).append("Can't find backup agent for ").append(s).toString());
                Object aobj1[] = new Object[2];
                aobj1[0] = s;
                aobj1[1] = "Restore agent missing";
                EventLog.writeEvent(2832, aobj1);
                executeNextState(RestoreState.RUNNING_QUEUE);
                break MISSING_BLOCK_LABEL_818;
            }
            initiateOneRestore(packageinfo, metadata.versionCode, ibackupagent, mNeedFullBackup);
            mCount = 1 + mCount;
            break MISSING_BLOCK_LABEL_818;
            Exception exception;
            exception;
            Slog.e("BackupManagerService", (new StringBuilder()).append("Error when attempting restore: ").append(exception.toString()).toString());
            agentErrorCleanup();
            executeNextState(RestoreState.RUNNING_QUEUE);
        }

        void restorePmMetadata() {
            String s;
            try {
                s = mTransport.nextRestorePackage();
                if(s == null) {
                    Slog.e("BackupManagerService", "Error getting first restore package");
                    EventLog.writeEvent(2831, new Object[0]);
                    mStatus = 1;
                    executeNextState(RestoreState.FINAL);
                    break MISSING_BLOCK_LABEL_390;
                }
                if(s.equals("")) {
                    Slog.i("BackupManagerService", "No restore data available");
                    int i = (int)(SystemClock.elapsedRealtime() - mStartRealtime);
                    Object aobj2[] = new Object[2];
                    aobj2[0] = Integer.valueOf(0);
                    aobj2[1] = Integer.valueOf(i);
                    EventLog.writeEvent(2834, aobj2);
                    mStatus = 0;
                    executeNextState(RestoreState.FINAL);
                    break MISSING_BLOCK_LABEL_390;
                }
            }
            catch(RemoteException remoteexception) {
                Slog.e("BackupManagerService", "Error communicating with transport for restore");
                EventLog.writeEvent(2831, new Object[0]);
                mStatus = 1;
                mBackupHandler.removeMessages(20, this);
                executeNextState(RestoreState.FINAL);
                break MISSING_BLOCK_LABEL_390;
            }
            if(!s.equals("@pm@")) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Expected restore data for \"@pm@\", found only \"").append(s).append("\"").toString());
                Object aobj1[] = new Object[2];
                aobj1[0] = "@pm@";
                aobj1[1] = "Package manager data missing";
                EventLog.writeEvent(2832, aobj1);
                executeNextState(RestoreState.FINAL);
            } else {
                PackageInfo packageinfo = new PackageInfo();
                packageinfo.packageName = "@pm@";
                mPmAgent = new PackageManagerBackupAgent(mPackageManager, mAgentPackages);
                initiateOneRestore(packageinfo, 0, android.app.IBackupAgent.Stub.asInterface(mPmAgent.onBind()), mNeedFullBackup);
                if(!mPmAgent.hasMetadata()) {
                    Slog.e("BackupManagerService", "No restore metadata available, so not restoring settings");
                    Object aobj[] = new Object[2];
                    aobj[0] = "@pm@";
                    aobj[1] = "Package manager restore metadata missing";
                    EventLog.writeEvent(2832, aobj);
                    mStatus = 1;
                    mBackupHandler.removeMessages(20, this);
                    executeNextState(RestoreState.FINAL);
                }
            }
        }

        private List mAgentPackages;
        private ParcelFileDescriptor mBackupData;
        private File mBackupDataName;
        private int mCount;
        private PackageInfo mCurrentPackage;
        private RestoreState mCurrentState;
        private HashSet mFilterSet;
        private boolean mFinished;
        private boolean mNeedFullBackup;
        private ParcelFileDescriptor mNewState;
        private File mNewStateName;
        private IRestoreObserver mObserver;
        private PackageManagerBackupAgent mPmAgent;
        private int mPmToken;
        private ArrayList mRestorePackages;
        private File mSavedStateName;
        private long mStartRealtime;
        private File mStateDir;
        private int mStatus;
        private PackageInfo mTargetPackage;
        private long mToken;
        private IBackupTransport mTransport;
        final BackupManagerService this$0;

        PerformRestoreTask(IBackupTransport ibackuptransport, IRestoreObserver irestoreobserver, long l, PackageInfo packageinfo, int i, 
                boolean flag, String as[]) {
            this$0 = BackupManagerService.this;
            super();
            mCurrentState = RestoreState.INITIAL;
            mFinished = false;
            mPmAgent = null;
            mTransport = ibackuptransport;
            mObserver = irestoreobserver;
            mToken = l;
            mTargetPackage = packageinfo;
            mPmToken = i;
            mNeedFullBackup = flag;
            if(as != null) {
                mFilterSet = new HashSet();
                int j = as.length;
                for(int k = 0; k < j; k++) {
                    String s = as[k];
                    mFilterSet.add(s);
                }

            } else {
                mFilterSet = null;
            }
            mStateDir = new File(mBaseStateDir, ibackuptransport.transportDirName());
_L2:
            return;
            RemoteException remoteexception;
            remoteexception;
            if(true) goto _L2; else goto _L1
_L1:
        }
    }

    static final class RestoreState extends Enum {

        public static RestoreState valueOf(String s) {
            return (RestoreState)Enum.valueOf(com/android/server/BackupManagerService$RestoreState, s);
        }

        public static RestoreState[] values() {
            return (RestoreState[])$VALUES.clone();
        }

        private static final RestoreState $VALUES[];
        public static final RestoreState DOWNLOAD_DATA;
        public static final RestoreState FINAL;
        public static final RestoreState INITIAL;
        public static final RestoreState PM_METADATA;
        public static final RestoreState RUNNING_QUEUE;

        static  {
            INITIAL = new RestoreState("INITIAL", 0);
            DOWNLOAD_DATA = new RestoreState("DOWNLOAD_DATA", 1);
            PM_METADATA = new RestoreState("PM_METADATA", 2);
            RUNNING_QUEUE = new RestoreState("RUNNING_QUEUE", 3);
            FINAL = new RestoreState("FINAL", 4);
            RestoreState arestorestate[] = new RestoreState[5];
            arestorestate[0] = INITIAL;
            arestorestate[1] = DOWNLOAD_DATA;
            arestorestate[2] = PM_METADATA;
            arestorestate[3] = RUNNING_QUEUE;
            arestorestate[4] = FINAL;
            $VALUES = arestorestate;
        }

        private RestoreState(String s, int i) {
            super(s, i);
        }
    }

    class PerformFullRestoreTask
        implements Runnable {
        class RestoreDeleteObserver extends android.content.pm.IPackageDeleteObserver.Stub {

            public void packageDeleted(String s, int i) throws RemoteException {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
                mResult = i;
                mDone.set(true);
                mDone.notifyAll();
                return;
            }

            public void reset() {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
                mDone.set(false);
                return;
            }

            public void waitForCompletion() {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
_L3:
                boolean flag = mDone.get();
                if(flag) goto _L2; else goto _L1
_L1:
                Exception exception;
                try {
                    mDone.wait();
                }
                catch(InterruptedException interruptedexception) { }
                finally {
                    atomicboolean;
                }
                if(true) goto _L3; else goto _L2
_L2:
                atomicboolean;
                JVM INSTR monitorexit ;
                return;
                throw exception;
            }

            final AtomicBoolean mDone = new AtomicBoolean();
            int mResult;
            final PerformFullRestoreTask this$1;

            RestoreDeleteObserver() {
                this$1 = PerformFullRestoreTask.this;
                super();
            }
        }

        class RestoreInstallObserver extends android.content.pm.IPackageInstallObserver.Stub {

            int getResult() {
                return mResult;
            }

            public void packageInstalled(String s, int i) throws RemoteException {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
                mResult = i;
                mPackageName = s;
                mDone.set(true);
                mDone.notifyAll();
                return;
            }

            public void reset() {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
                mDone.set(false);
                return;
            }

            public void waitForCompletion() {
                AtomicBoolean atomicboolean = mDone;
                atomicboolean;
                JVM INSTR monitorenter ;
_L3:
                boolean flag = mDone.get();
                if(flag) goto _L2; else goto _L1
_L1:
                Exception exception;
                try {
                    mDone.wait();
                }
                catch(InterruptedException interruptedexception) { }
                finally {
                    atomicboolean;
                }
                if(true) goto _L3; else goto _L2
_L2:
                atomicboolean;
                JVM INSTR monitorexit ;
                return;
                throw exception;
            }

            final AtomicBoolean mDone = new AtomicBoolean();
            String mPackageName;
            int mResult;
            final PerformFullRestoreTask this$1;

            RestoreInstallObserver() {
                this$1 = PerformFullRestoreTask.this;
                super();
            }
        }

        class RestoreFileRunnable
            implements Runnable {

            public void run() {
                mAgent.doRestoreFile(mSocket, mInfo.size, mInfo.type, mInfo.domain, mInfo.path, mInfo.mode, mInfo.mtime, mToken, mBackupManagerBinder);
_L2:
                return;
                RemoteException remoteexception;
                remoteexception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            IBackupAgent mAgent;
            FileMetadata mInfo;
            ParcelFileDescriptor mSocket;
            int mToken;
            final PerformFullRestoreTask this$1;

            RestoreFileRunnable(IBackupAgent ibackupagent, FileMetadata filemetadata, ParcelFileDescriptor parcelfiledescriptor, int i) throws IOException {
                this$1 = PerformFullRestoreTask.this;
                super();
                mAgent = ibackupagent;
                mInfo = filemetadata;
                mToken = i;
                mSocket = ParcelFileDescriptor.dup(parcelfiledescriptor.getFileDescriptor());
            }
        }


        private void HEXLOG(byte abyte0[]) {
            int i = 0;
            int j = abyte0.length;
            StringBuilder stringbuilder = new StringBuilder(64);
            while(j > 0)  {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(i);
                stringbuilder.append(String.format("%04x   ", aobj));
                int k;
                int l;
                if(j > 16)
                    k = 16;
                else
                    k = j;
                for(l = 0; l < k; l++) {
                    Object aobj1[] = new Object[1];
                    aobj1[0] = Byte.valueOf(abyte0[i + l]);
                    stringbuilder.append(String.format("%02x ", aobj1));
                }

                Slog.i("hexdump", stringbuilder.toString());
                stringbuilder.setLength(0);
                j -= k;
                i += k;
            }
        }

        InputStream decodeAesHeaderAndInitialize(String s, InputStream inputstream) {
            CipherInputStream cipherinputstream = null;
label0:
            {
                InvalidAlgorithmParameterException invalidalgorithmparameterexception;
                if(s.equals("AES-256")) {
                    String s1 = readHeaderLine(inputstream);
                    byte abyte0[] = hexToByteArray(s1);
                    String s2 = readHeaderLine(inputstream);
                    byte abyte1[] = hexToByteArray(s2);
                    int i = Integer.parseInt(readHeaderLine(inputstream));
                    String s3 = readHeaderLine(inputstream);
                    String s4 = readHeaderLine(inputstream);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    SecretKey secretkey = buildPasswordKey(mDecryptPassword, abyte0, i);
                    IvParameterSpec ivparameterspec = new IvParameterSpec(hexToByteArray(s3));
                    cipher.init(2, new SecretKeySpec(secretkey.getEncoded(), "AES"), ivparameterspec);
                    byte abyte2[] = cipher.doFinal(hexToByteArray(s4));
                    int j = 0 + 1;
                    byte byte0 = abyte2[0];
                    byte abyte3[] = Arrays.copyOfRange(abyte2, j, byte0 + 1);
                    int k = byte0 + 1;
                    int l = k + 1;
                    byte byte1 = abyte2[k];
                    byte abyte4[] = Arrays.copyOfRange(abyte2, l, l + byte1);
                    int i1 = l + byte1;
                    int j1 = i1 + 1;
                    byte abyte5[] = Arrays.copyOfRange(abyte2, j1, j1 + abyte2[i1]);
                    if(Arrays.equals(makeKeyChecksum(abyte4, abyte1, i), abyte5)) {
                        IvParameterSpec ivparameterspec1 = new IvParameterSpec(abyte3);
                        SecretKeySpec secretkeyspec = new SecretKeySpec(abyte4, "AES");
                        cipher.init(2, secretkeyspec, ivparameterspec1);
                        CipherInputStream cipherinputstream1 = new CipherInputStream(inputstream, cipher);
                        cipherinputstream = cipherinputstream1;
                    } else {
                        Slog.w("BackupManagerService", "Incorrect password");
                    }
                    break label0;
                }
                try {
                    Slog.w("BackupManagerService", (new StringBuilder()).append("Unsupported encryption method: ").append(s).toString());
                }
                // Misplaced declaration of an exception variable
                catch(InvalidAlgorithmParameterException invalidalgorithmparameterexception) {
                    Slog.e("BackupManagerService", "Needed parameter spec unavailable!", invalidalgorithmparameterexception);
                }
                catch(BadPaddingException badpaddingexception) {
                    Slog.w("BackupManagerService", "Incorrect password");
                }
                catch(IllegalBlockSizeException illegalblocksizeexception) {
                    Slog.w("BackupManagerService", "Invalid block size in master key");
                }
                catch(NoSuchAlgorithmException nosuchalgorithmexception) {
                    Slog.e("BackupManagerService", "Needed decryption algorithm unavailable!");
                }
                catch(NoSuchPaddingException nosuchpaddingexception) {
                    Slog.e("BackupManagerService", "Needed padding mechanism unavailable!");
                }
                catch(InvalidKeyException invalidkeyexception) {
                    Slog.w("BackupManagerService", "Illegal password; aborting");
                }
                catch(NumberFormatException numberformatexception) {
                    Slog.w("BackupManagerService", "Can't parse restore data header");
                }
                catch(IOException ioexception) {
                    Slog.w("BackupManagerService", "Can't read input header");
                }
            }
            return cipherinputstream;
        }

        void dumpFileMetadata(FileMetadata filemetadata) {
            char c = 'x';
            char c1 = 'w';
            char c2 = 'r';
            StringBuilder stringbuilder = new StringBuilder(128);
            char c3;
            char c4;
            char c5;
            char c6;
            char c7;
            char c8;
            char c9;
            Object aobj[];
            Date date;
            if(filemetadata.type == 2)
                c3 = 'd';
            else
                c3 = '-';
            stringbuilder.append(c3);
            if((256L & filemetadata.mode) != 0L)
                c4 = c2;
            else
                c4 = '-';
            stringbuilder.append(c4);
            if((128L & filemetadata.mode) != 0L)
                c5 = c1;
            else
                c5 = '-';
            stringbuilder.append(c5);
            if((64L & filemetadata.mode) != 0L)
                c6 = c;
            else
                c6 = '-';
            stringbuilder.append(c6);
            if((32L & filemetadata.mode) != 0L)
                c7 = c2;
            else
                c7 = '-';
            stringbuilder.append(c7);
            if((16L & filemetadata.mode) != 0L)
                c8 = c1;
            else
                c8 = '-';
            stringbuilder.append(c8);
            if((8L & filemetadata.mode) != 0L)
                c9 = c;
            else
                c9 = '-';
            stringbuilder.append(c9);
            if((4L & filemetadata.mode) == 0L)
                c2 = '-';
            stringbuilder.append(c2);
            if((2L & filemetadata.mode) == 0L)
                c1 = '-';
            stringbuilder.append(c1);
            if((1L & filemetadata.mode) == 0L)
                c = '-';
            stringbuilder.append(c);
            aobj = new Object[1];
            aobj[0] = Long.valueOf(filemetadata.size);
            stringbuilder.append(String.format(" %9d ", aobj));
            date = new Date(filemetadata.mtime);
            stringbuilder.append((new SimpleDateFormat("MMM dd kk:mm:ss ")).format(date));
            stringbuilder.append(filemetadata.packageName);
            stringbuilder.append(" :: ");
            stringbuilder.append(filemetadata.domain);
            stringbuilder.append(" :: ");
            stringbuilder.append(filemetadata.path);
            Slog.i("BackupManagerService", stringbuilder.toString());
        }

        int extractLine(byte abyte0[], int i, String as[]) throws IOException {
            int j = abyte0.length;
            if(i >= j)
                throw new IOException("Incomplete data");
            int k = i;
            do {
                if(k >= j || abyte0[k] == 10) {
                    as[0] = new String(abyte0, i, k - i);
                    return k + 1;
                }
                k++;
            } while(true);
        }

        long extractRadix(byte abyte0[], int i, int j, int k) throws IOException {
            long l = 0L;
            int i1 = i + j;
            int j1 = i;
            do {
                byte byte0;
label0:
                {
                    if(j1 < i1) {
                        byte0 = abyte0[j1];
                        if(byte0 != 0 && byte0 != 32)
                            break label0;
                    }
                    return l;
                }
                if(byte0 < 48 || byte0 > -1 + (k + 48))
                    throw new IOException((new StringBuilder()).append("Invalid number in header: '").append((char)byte0).append("' for radix ").append(k).toString());
                l = l * (long)k + (long)(byte0 - 48);
                j1++;
            } while(true);
        }

        String extractString(byte abyte0[], int i, int j) throws IOException {
            int k = i + j;
            int l;
            for(l = i; l < k && abyte0[l] != 0; l++);
            return new String(abyte0, i, l - i, "US-ASCII");
        }

        boolean installApk(FileMetadata filemetadata, String s, InputStream inputstream) {
            boolean flag;
            File file;
            flag = true;
            Slog.d("BackupManagerService", (new StringBuilder()).append("Installing from backup: ").append(filemetadata.packageName).toString());
            file = new File(mDataDir, filemetadata.packageName);
            FileOutputStream fileoutputstream;
            byte abyte0[];
            long l;
            fileoutputstream = new FileOutputStream(file);
            abyte0 = new byte[32768];
            l = filemetadata.size;
_L4:
            if(l <= 0L) goto _L2; else goto _L1
_L1:
            long l1;
            if((long)abyte0.length >= l)
                break MISSING_BLOCK_LABEL_584;
            l1 = abyte0.length;
_L12:
            int i = inputstream.read(abyte0, 0, (int)l1);
            if(i < 0) goto _L2; else goto _L3
_L3:
            mBytes = mBytes + (long)i;
            fileoutputstream.write(abyte0, 0, i);
            l -= i;
              goto _L4
_L2:
            fileoutputstream.close();
            file.setReadable(true, false);
            Uri uri = Uri.fromFile(file);
            mInstallObserver.reset();
            mPackageManager.installPackage(uri, mInstallObserver, 34, s);
            mInstallObserver.waitForCompletion();
            if(mInstallObserver.getResult() == 1) goto _L6; else goto _L5
_L5:
            Object obj;
            RestorePolicy restorepolicy;
            obj = mPackagePolicies.get(filemetadata.packageName);
            restorepolicy = RestorePolicy.ACCEPT;
            if(obj != restorepolicy)
                flag = false;
_L9:
            file.delete();
            return flag;
_L6:
            boolean flag1 = false;
            if(mInstallObserver.mPackageName.equals(filemetadata.packageName)) goto _L8; else goto _L7
_L7:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Restore stream claimed to include apk for ").append(filemetadata.packageName).append(" but apk was really ").append(mInstallObserver.mPackageName).toString());
            flag = false;
            flag1 = true;
_L10:
            if(flag1) {
                mDeleteObserver.reset();
                mPackageManager.deletePackage(mInstallObserver.mPackageName, mDeleteObserver, 0);
                mDeleteObserver.waitForCompletion();
            }
              goto _L9
            IOException ioexception;
            ioexception;
            Slog.e("BackupManagerService", "Unable to transcribe restored apk for install");
            flag = false;
              goto _L9
_L8:
            PackageInfo packageinfo;
label0:
            {
                packageinfo = mPackageManager.getPackageInfo(filemetadata.packageName, 64);
                if((0x8000 & packageinfo.applicationInfo.flags) != 0)
                    break label0;
                Slog.w("BackupManagerService", (new StringBuilder()).append("Restore stream contains apk of package ").append(filemetadata.packageName).append(" but it disallows backup/restore").toString());
                flag = false;
            }
              goto _L10
            Signature asignature[] = (Signature[])mManifestSignatures.get(filemetadata.packageName);
            if(signaturesMatch(asignature, packageinfo)) goto _L10; else goto _L11
_L11:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Installed app ").append(filemetadata.packageName).append(" signatures do not match restore manifest").toString());
            flag = false;
            flag1 = true;
              goto _L10
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            namenotfoundexception;
            Slog.w("BackupManagerService", (new StringBuilder()).append("Install of package ").append(filemetadata.packageName).append(" succeeded but now not found").toString());
            flag = false;
              goto _L10
            Exception exception;
            exception;
            file.delete();
            throw exception;
            l1 = l;
              goto _L12
        }

        RestorePolicy readAppManifest(FileMetadata filemetadata, InputStream inputstream) throws IOException {
            byte abyte0[];
            if(filemetadata.size > 0x10000L)
                throw new IOException((new StringBuilder()).append("Restore manifest too big; corrupt? size=").append(filemetadata.size).toString());
            abyte0 = new byte[(int)filemetadata.size];
            if((long)readExactly(inputstream, abyte0, 0, (int)filemetadata.size) != filemetadata.size) goto _L2; else goto _L1
_L1:
            RestorePolicy restorepolicy;
            String as[];
            mBytes = mBytes + filemetadata.size;
            restorepolicy = RestorePolicy.IGNORE;
            as = new String[1];
            int j;
            int k;
            String s;
            int i = extractLine(abyte0, 0, as);
            j = Integer.parseInt(as[0]);
            if(j != 1)
                break MISSING_BLOCK_LABEL_830;
            k = extractLine(abyte0, i, as);
            s = as[0];
            if(!s.equals(filemetadata.packageName)) goto _L4; else goto _L3
_L3:
            int i1;
            int k1;
            int l = extractLine(abyte0, k, as);
            i1 = Integer.parseInt(as[0]);
            int j1 = extractLine(abyte0, l, as);
            Integer.parseInt(as[0]);
            k1 = extractLine(abyte0, j1, as);
            if(as[0].length() <= 0) goto _L6; else goto _L5
_L5:
            String s1 = as[0];
_L12:
            boolean flag;
            int i2;
            int j2;
            filemetadata.installerPackageName = s1;
            int l1 = extractLine(abyte0, k1, as);
            flag = as[0].equals("1");
            i2 = extractLine(abyte0, l1, as);
            j2 = Integer.parseInt(as[0]);
            if(j2 <= 0) goto _L8; else goto _L7
_L7:
            Signature asignature[];
            int k2;
            asignature = new Signature[j2];
            k2 = 0;
_L11:
            if(k2 >= j2) goto _L10; else goto _L9
_L9:
            i2 = extractLine(abyte0, i2, as);
            asignature[k2] = new Signature(as[0]);
            k2++;
              goto _L11
_L2:
            throw new IOException("Unexpected EOF in manifest");
_L6:
            s1 = null;
              goto _L12
_L10:
            mManifestSignatures.put(filemetadata.packageName, asignature);
            PackageInfo packageinfo = mPackageManager.getPackageInfo(filemetadata.packageName, 64);
            if((0x8000 & packageinfo.applicationInfo.flags) == 0) goto _L14; else goto _L13
_L13:
            if(packageinfo.applicationInfo.uid < 10000 && packageinfo.applicationInfo.backupAgentName == null) goto _L16; else goto _L15
_L15:
            if(!signaturesMatch(asignature, packageinfo)) goto _L18; else goto _L17
_L17:
            if(packageinfo.versionCode < i1) goto _L20; else goto _L19
_L19:
            Slog.i("BackupManagerService", "Sig + version match; taking data");
            restorepolicy = RestorePolicy.ACCEPT;
_L21:
            if(restorepolicy == RestorePolicy.ACCEPT_IF_APK && !flag)
                Slog.i("BackupManagerService", (new StringBuilder()).append("Cannot restore package ").append(filemetadata.packageName).append(" without the matching .apk").toString());
_L22:
            return restorepolicy;
_L20:
            Slog.d("BackupManagerService", (new StringBuilder()).append("Data version ").append(i1).append(" is newer than installed version ").append(packageinfo.versionCode).append(" - requiring apk").toString());
            restorepolicy = RestorePolicy.ACCEPT_IF_APK;
              goto _L21
_L18:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Restore manifest signatures do not match installed application for ").append(filemetadata.packageName).toString());
              goto _L21
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            namenotfoundexception;
            Slog.i("BackupManagerService", (new StringBuilder()).append("Package ").append(filemetadata.packageName).append(" not installed; requiring apk in dataset").toString());
            restorepolicy = RestorePolicy.ACCEPT_IF_APK;
              goto _L21
_L16:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Package ").append(filemetadata.packageName).append(" is system level with no agent").toString());
              goto _L21
            NumberFormatException numberformatexception;
            numberformatexception;
            Slog.w("BackupManagerService", (new StringBuilder()).append("Corrupt restore manifest for package ").append(filemetadata.packageName).toString());
              goto _L22
_L14:
            Slog.i("BackupManagerService", (new StringBuilder()).append("Restore manifest from ").append(filemetadata.packageName).append(" but allowBackup=false").toString());
              goto _L21
            IllegalArgumentException illegalargumentexception;
            illegalargumentexception;
            Slog.w("BackupManagerService", illegalargumentexception.getMessage());
              goto _L22
_L8:
            Slog.i("BackupManagerService", (new StringBuilder()).append("Missing signature on backed-up package ").append(filemetadata.packageName).toString());
              goto _L22
_L4:
            Slog.i("BackupManagerService", (new StringBuilder()).append("Expected package ").append(filemetadata.packageName).append(" but restore manifest claims ").append(s).toString());
              goto _L22
            Slog.i("BackupManagerService", (new StringBuilder()).append("Unknown restore manifest version ").append(j).append(" for package ").append(filemetadata.packageName).toString());
              goto _L22
        }

        int readExactly(InputStream inputstream, byte abyte0[], int i, int j) throws IOException {
            if(j <= 0)
                throw new IllegalArgumentException("size must be > 0");
            int k = 0;
            do {
                int l;
label0:
                {
                    if(k < j) {
                        l = inputstream.read(abyte0, i + k, j - k);
                        if(l > 0)
                            break label0;
                    }
                    return k;
                }
                k += l;
            } while(true);
        }

        String readHeaderLine(InputStream inputstream) throws IOException {
            StringBuilder stringbuilder = new StringBuilder(80);
            do {
                int i = inputstream.read();
                if(i < 0 || i == 10)
                    return stringbuilder.toString();
                stringbuilder.append((char)i);
            } while(true);
        }

        boolean readPaxExtendedHeader(InputStream inputstream, FileMetadata filemetadata) throws IOException {
            if(filemetadata.size > 32768L) {
                Slog.w("BackupManagerService", (new StringBuilder()).append("Suspiciously large pax header size ").append(filemetadata.size).append(" - aborting").toString());
                throw new IOException((new StringBuilder()).append("Sanity failure: pax header size ").append(filemetadata.size).toString());
            }
            byte abyte0[] = new byte[512 * (int)(511L + filemetadata.size >> 9)];
            if(readExactly(inputstream, abyte0, 0, abyte0.length) < abyte0.length)
                throw new IOException("Unable to read full pax header");
            mBytes = mBytes + (long)abyte0.length;
            int i = (int)filemetadata.size;
            int j = 0;
            do {
                int k;
                for(k = j + 1; k < i && abyte0[k] != 32; k++);
                if(k >= i)
                    throw new IOException("Invalid pax data");
                int l = (int)extractRadix(abyte0, j, k - j, 10);
                int i1 = k + 1;
                int j1 = -1 + (j + l);
                int k1;
                for(k1 = i1 + 1; abyte0[k1] != 61 && k1 <= j1; k1++);
                if(k1 > j1)
                    throw new IOException("Invalid pax declaration");
                String s = new String(abyte0, i1, k1 - i1, "UTF-8");
                String s1 = new String(abyte0, k1 + 1, -1 + (j1 - k1), "UTF-8");
                if("path".equals(s))
                    filemetadata.path = s1;
                else
                if("size".equals(s))
                    filemetadata.size = Long.parseLong(s1);
                else
                    Slog.i("BackupManagerService", (new StringBuilder()).append("Unhandled pax key: ").append(i1).toString());
                j += l;
            } while(j < i);
            return true;
        }

        boolean readTarHeader(InputStream inputstream, byte abyte0[]) throws IOException {
            boolean flag = false;
            int i = readExactly(inputstream, abyte0, 0, 512);
            if(i != 0) {
                if(i < 512)
                    throw new IOException("Unable to read full block header");
                mBytes = 512L + mBytes;
                flag = true;
            }
            return flag;
        }

        FileMetadata readTarHeaders(InputStream inputstream) throws IOException {
            byte abyte0[];
            FileMetadata filemetadata;
            abyte0 = new byte[512];
            filemetadata = null;
            if(!readTarHeader(inputstream, abyte0)) goto _L2; else goto _L1
_L1:
            FileMetadata filemetadata1;
            FileMetadata filemetadata2;
            IOException ioexception;
            String s;
            byte byte0;
            try {
                filemetadata2 = new FileMetadata();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) {
                continue; /* Loop/switch isn't completed */
            }
            filemetadata2.size = extractRadix(abyte0, 124, 12, 8);
            filemetadata2.mtime = extractRadix(abyte0, 136, 12, 8);
            filemetadata2.mode = extractRadix(abyte0, 100, 8, 8);
            filemetadata2.path = extractString(abyte0, 345, 155);
            s = extractString(abyte0, 0, 100);
            if(s.length() > 0) {
                if(filemetadata2.path.length() > 0)
                    filemetadata2.path = (new StringBuilder()).append(filemetadata2.path).append('/').toString();
                filemetadata2.path = (new StringBuilder()).append(filemetadata2.path).append(s).toString();
            }
            byte0 = abyte0[156];
            if(byte0 != 120) goto _L4; else goto _L3
_L3:
            boolean flag = readPaxExtendedHeader(inputstream, filemetadata2);
            if(flag)
                flag = readTarHeader(inputstream, abyte0);
            if(!flag)
                throw new IOException("Bad or missing pax header");
              goto _L5
            ioexception;
_L16:
            Slog.e("BackupManagerService", (new StringBuilder()).append("Parse error in header: ").append(ioexception.getMessage()).toString());
            HEXLOG(abyte0);
            throw ioexception;
_L5:
            byte0 = abyte0[156];
              goto _L4
_L14:
            Slog.e("BackupManagerService", (new StringBuilder()).append("Unknown tar entity type: ").append(byte0).toString());
            throw new IOException((new StringBuilder()).append("Unknown entity type ").append(byte0).toString());
_L11:
            filemetadata2.type = 1;
_L7:
            if("shared/".regionMatches(0, filemetadata2.path, 0, "shared/".length())) {
                filemetadata2.path = filemetadata2.path.substring("shared/".length());
                filemetadata2.packageName = "com.android.sharedstoragebackup";
                filemetadata2.domain = "shared";
                Slog.i("BackupManagerService", (new StringBuilder()).append("File in shared storage: ").append(filemetadata2.path).toString());
            } else
            if("apps/".regionMatches(0, filemetadata2.path, 0, "apps/".length())) {
                filemetadata2.path = filemetadata2.path.substring("apps/".length());
                int i = filemetadata2.path.indexOf('/');
                if(i < 0)
                    throw new IOException((new StringBuilder()).append("Illegal semantic path in ").append(filemetadata2.path).toString());
                filemetadata2.packageName = filemetadata2.path.substring(0, i);
                filemetadata2.path = filemetadata2.path.substring(i + 1);
                if(!filemetadata2.path.equals("_manifest")) {
                    int j = filemetadata2.path.indexOf('/');
                    if(j < 0)
                        throw new IOException((new StringBuilder()).append("Illegal semantic path in non-manifest ").append(filemetadata2.path).toString());
                    filemetadata2.domain = filemetadata2.path.substring(0, j);
                    if(!filemetadata2.domain.equals("a") && !filemetadata2.domain.equals("f") && !filemetadata2.domain.equals("db") && !filemetadata2.domain.equals("r") && !filemetadata2.domain.equals("sp") && !filemetadata2.domain.equals("obb") && !filemetadata2.domain.equals("c"))
                        throw new IOException((new StringBuilder()).append("Unrecognized domain ").append(filemetadata2.domain).toString());
                    filemetadata2.path = filemetadata2.path.substring(j + 1);
                }
            }
              goto _L6
_L12:
            filemetadata2.type = 2;
            if(filemetadata2.size != 0L) {
                Slog.w("BackupManagerService", "Directory entry with nonzero size in header");
                filemetadata2.size = 0L;
            }
              goto _L7
_L10:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Saw type=0 in tar header block, info=").append(filemetadata2).toString());
            filemetadata1 = null;
              goto _L8
_L4:
            byte0;
            JVM INSTR lookupswitch 3: default 278
        //                       0: 464
        //                       48: 334
        //                       53: 430;
               goto _L9 _L10 _L11 _L12
_L9:
            if(true) goto _L14; else goto _L13
_L13:
_L6:
            filemetadata = filemetadata2;
_L2:
            filemetadata1 = filemetadata;
_L8:
            return filemetadata1;
            if(true) goto _L16; else goto _L15
_L15:
        }

        boolean restoreOneFile(InputStream inputstream, byte abyte0[]) {
            FileMetadata filemetadata = readTarHeaders(inputstream);
            if(filemetadata == null) goto _L2; else goto _L1
_L1:
            String s;
            s = filemetadata.packageName;
            if(!s.equals(mAgentPackage)) {
                if(!mPackagePolicies.containsKey(s))
                    mPackagePolicies.put(s, RestorePolicy.IGNORE);
                if(mAgent != null) {
                    Slog.d("BackupManagerService", "Saw new package; tearing down old one");
                    tearDownPipes();
                    tearDownAgent(mTargetApp);
                    mTargetApp = null;
                    mAgentPackage = null;
                }
            }
            if(!filemetadata.path.equals("_manifest")) goto _L4; else goto _L3
_L3:
            mPackagePolicies.put(s, readAppManifest(filemetadata, inputstream));
            mPackageInstallers.put(s, filemetadata.installerPackageName);
            skipTarPadding(filemetadata.size, inputstream);
            sendOnRestorePackage(s);
              goto _L2
_L4:
            boolean flag1;
            RestorePolicy restorepolicy;
            flag1 = true;
            restorepolicy = (RestorePolicy)mPackagePolicies.get(s);
            _cls4..SwitchMap.com.android.server.BackupManagerService.RestorePolicy[restorepolicy.ordinal()];
            JVM INSTR tableswitch 1 3: default 212
        //                       1 1269
        //                       2 853
        //                       3 966;
               goto _L5 _L6 _L7 _L8
_L5:
            Slog.e("BackupManagerService", "Invalid policy from manifest");
            flag1 = false;
            mPackagePolicies.put(s, RestorePolicy.IGNORE);
_L34:
            if(flag1 && mAgent != null)
                Slog.i("BackupManagerService", "Reusing existing agent instance");
            if(!flag1 || mAgent != null) goto _L10; else goto _L9
_L9:
            Slog.d("BackupManagerService", (new StringBuilder()).append("Need to launch agent for ").append(s).toString());
            mTargetApp = mPackageManager.getApplicationInfo(s, 0);
            if(mClearedPackages.contains(s)) goto _L12; else goto _L11
_L11:
            if(mTargetApp.backupAgentName != null) goto _L14; else goto _L13
_L13:
            Slog.d("BackupManagerService", "Clearing app data preparatory to full restore");
            clearApplicationDataSynchronous(s);
_L36:
            mClearedPackages.add(s);
_L37:
            setUpPipes();
            mAgent = bindToAgentSynchronous(mTargetApp, 3);
            mAgentPackage = s;
_L38:
            if(mAgent == null) {
                Slog.d("BackupManagerService", (new StringBuilder()).append("Unable to create agent for ").append(s).toString());
                flag1 = false;
                tearDownPipes();
                mPackagePolicies.put(s, RestorePolicy.IGNORE);
            }
_L10:
            if(flag1 && !s.equals(mAgentPackage)) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Restoring data for ").append(s).append(" but agent is for ").append(mAgentPackage).toString());
                flag1 = false;
            }
            if(!flag1) goto _L16; else goto _L15
_L15:
            boolean flag2;
            long l2;
            int j;
            flag2 = true;
            l2 = filemetadata.size;
            j = generateToken();
            Slog.d("BackupManagerService", (new StringBuilder()).append("Invoking agent to restore file ").append(filemetadata.path).toString());
            prepareOperationTimeout(j, 0x493e0L, null);
            if(!mTargetApp.processName.equals("system")) goto _L18; else goto _L17
_L17:
            Slog.d("BackupManagerService", "system process agent - spinning a thread");
            (new Thread(new RestoreFileRunnable(mAgent, filemetadata, mPipes[0], j))).start();
_L39:
            if(!flag1) goto _L20; else goto _L19
_L19:
            boolean flag3 = true;
            FileOutputStream fileoutputstream = new FileOutputStream(mPipes[1].getFileDescriptor());
_L42:
            if(l2 <= 0L) goto _L22; else goto _L21
_L21:
            if(l2 <= (long)abyte0.length) goto _L24; else goto _L23
_L23:
            int k = abyte0.length;
_L40:
            int i1;
            i1 = inputstream.read(abyte0, 0, k);
            if(i1 >= 0)
                mBytes = mBytes + (long)i1;
              goto _L25
_L22:
            skipTarPadding(filemetadata.size, inputstream);
            flag2 = waitUntilOperationComplete(j);
_L20:
            if(!flag2) {
                mBackupHandler.removeMessages(7);
                tearDownPipes();
                tearDownAgent(mTargetApp);
                mAgent = null;
                mPackagePolicies.put(s, RestorePolicy.IGNORE);
            }
_L16:
            if(flag1) goto _L2; else goto _L26
_L26:
            long l;
            Slog.d("BackupManagerService", "[discarding file content]");
            l = -512L & 511L + filemetadata.size;
_L48:
            if(l <= 0L) goto _L2; else goto _L27
_L27:
            if(l <= (long)abyte0.length) goto _L29; else goto _L28
_L28:
            int i = abyte0.length;
_L43:
            long l1;
            l1 = inputstream.read(abyte0, 0, i);
            if(l1 >= 0L)
                mBytes = l1 + mBytes;
              goto _L30
_L7:
            if(!filemetadata.domain.equals("a")) goto _L32; else goto _L31
_L31:
            boolean flag;
            Slog.d("BackupManagerService", "APK file; installing");
            boolean flag4 = installApk(filemetadata, (String)mPackageInstallers.get(s), inputstream);
            HashMap hashmap = mPackagePolicies;
            RestorePolicy restorepolicy1;
            if(flag4)
                restorepolicy1 = RestorePolicy.ACCEPT;
            else
                restorepolicy1 = RestorePolicy.IGNORE;
            hashmap.put(s, restorepolicy1);
            skipTarPadding(filemetadata.size, inputstream);
            flag = true;
              goto _L33
_L32:
            mPackagePolicies.put(s, RestorePolicy.IGNORE);
            flag1 = false;
              goto _L34
_L8:
            if(!filemetadata.domain.equals("a")) goto _L34; else goto _L35
_L35:
            Slog.d("BackupManagerService", "apk present but ACCEPT");
            flag1 = false;
              goto _L34
_L14:
            Slog.d("BackupManagerService", (new StringBuilder()).append("backup agent (").append(mTargetApp.backupAgentName).append(") => no clear").toString());
              goto _L36
_L12:
            Slog.d("BackupManagerService", "We've initialized this app already; no clear required");
              goto _L37
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            namenotfoundexception;
              goto _L38
_L18:
            mAgent.doRestoreFile(mPipes[0], filemetadata.size, filemetadata.type, filemetadata.domain, filemetadata.path, filemetadata.mode, filemetadata.mtime, j, mBackupManagerBinder);
              goto _L39
            IOException ioexception2;
            ioexception2;
            Slog.d("BackupManagerService", "Couldn't establish restore");
            flag2 = false;
            flag1 = false;
              goto _L39
            RemoteException remoteexception;
            remoteexception;
            Slog.e("BackupManagerService", "Agent crashed during full restore");
            flag2 = false;
            flag1 = false;
              goto _L39
_L24:
            k = (int)l2;
              goto _L40
_L46:
            l2 -= i1;
            if(!flag3) goto _L42; else goto _L41
_L41:
            fileoutputstream.write(abyte0, 0, i1);
              goto _L42
            IOException ioexception1;
            ioexception1;
            Slog.e("BackupManagerService", "Failed to write to restore pipe", ioexception1);
            flag3 = false;
              goto _L42
_L29:
            i = (int)l;
              goto _L43
            IOException ioexception;
            ioexception;
            Slog.w("BackupManagerService", "io exception on restore socket read", ioexception);
            filemetadata = null;
              goto _L2
_L45:
            flag = false;
              goto _L33
_L2:
            if(filemetadata == null) goto _L45; else goto _L44
_L44:
            flag = true;
_L33:
            return flag;
_L25:
            if(i1 > 0) goto _L46; else goto _L22
_L30:
            if(l1 <= 0L) goto _L2; else goto _L47
_L47:
            l -= l1;
              goto _L48
_L6:
            flag1 = false;
              goto _L34
            IOException ioexception3;
            ioexception3;
              goto _L38
        }

        public void run() {
            FileInputStream fileinputstream;
            DataInputStream datainputstream;
            Slog.i("BackupManagerService", "--- Performing full-dataset restore ---");
            sendStartRestore();
            if(Environment.getExternalStorageState().equals("mounted"))
                mPackagePolicies.put("com.android.sharedstoragebackup", RestorePolicy.ACCEPT);
            fileinputstream = null;
            datainputstream = null;
            if(!hasBackupPassword() || passwordMatchesSaved(mCurrentPassword, 10000)) goto _L2; else goto _L1
_L1:
            Slog.w("BackupManagerService", "Backup password mismatch; aborting");
            tearDownPipes();
            tearDownAgent(mTargetApp);
            if(true)
                break MISSING_BLOCK_LABEL_97;
            throw null;
            if(false)
                throw null;
            mInputFile.close();
_L22:
            android.os.PowerManager.WakeLock wakelock;
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndRestore();
            Slog.d("BackupManagerService", "Full restore pass complete.");
            wakelock = mWakelock;
_L16:
            wakelock.release();
_L13:
            return;
_L2:
            byte abyte0[];
            FileInputStream fileinputstream1;
            mBytes = 0L;
            abyte0 = new byte[32768];
            fileinputstream1 = new FileInputStream(mInputFile.getFileDescriptor());
            DataInputStream datainputstream1 = new DataInputStream(fileinputstream1);
            boolean flag;
            Object obj2;
            boolean flag1;
            flag = false;
            obj2 = fileinputstream1;
            flag1 = false;
            byte abyte1[];
            abyte1 = new byte["ANDROID BACKUP\n".length()];
            datainputstream1.readFully(abyte1);
            if(!Arrays.equals("ANDROID BACKUP\n".getBytes("UTF-8"), abyte1)) goto _L4; else goto _L3
_L3:
            String s = readHeaderLine(fileinputstream1);
            if(Integer.parseInt(s) != 1) goto _L6; else goto _L5
_L5:
            if(Integer.parseInt(readHeaderLine(fileinputstream1)) == 0) goto _L8; else goto _L7
_L7:
            flag = true;
_L14:
            String s1 = readHeaderLine(fileinputstream1);
            if(!s1.equals("none")) goto _L10; else goto _L9
_L9:
            flag1 = true;
_L15:
            if(flag1) goto _L12; else goto _L11
_L11:
            Slog.w("BackupManagerService", "Invalid restore data; aborting.");
            tearDownPipes();
            tearDownAgent(mTargetApp);
            if(datainputstream1 == null)
                break MISSING_BLOCK_LABEL_363;
            datainputstream1.close();
            if(fileinputstream1 != null)
                fileinputstream1.close();
            mInputFile.close();
_L23:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndRestore();
            Slog.d("BackupManagerService", "Full restore pass complete.");
            mWakelock.release();
              goto _L13
_L8:
            flag = false;
              goto _L14
_L10:
            if(mDecryptPassword != null && mDecryptPassword.length() > 0) {
                obj2 = decodeAesHeaderAndInitialize(s1, fileinputstream1);
                if(obj2 != null)
                    flag1 = true;
            } else {
                Slog.w("BackupManagerService", "Archive is encrypted but no password given");
            }
              goto _L15
            IOException ioexception3;
            ioexception3;
            datainputstream = datainputstream1;
            fileinputstream = fileinputstream1;
_L26:
            Slog.e("BackupManagerService", "Unable to read restore input");
            tearDownPipes();
            tearDownAgent(mTargetApp);
            if(datainputstream == null)
                break MISSING_BLOCK_LABEL_551;
            datainputstream.close();
            if(fileinputstream != null)
                fileinputstream.close();
            mInputFile.close();
_L21:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndRestore();
            Slog.d("BackupManagerService", "Full restore pass complete.");
            wakelock = mWakelock;
              goto _L16
_L6:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Wrong header version: ").append(s).toString());
              goto _L15
            Exception exception;
            exception;
            datainputstream = datainputstream1;
            fileinputstream = fileinputstream1;
_L25:
            tearDownPipes();
            tearDownAgent(mTargetApp);
            if(datainputstream == null)
                break MISSING_BLOCK_LABEL_701;
            datainputstream.close();
            if(fileinputstream != null)
                fileinputstream.close();
            mInputFile.close();
_L20:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndRestore();
            Slog.d("BackupManagerService", "Full restore pass complete.");
            mWakelock.release();
            throw exception;
_L4:
            Slog.w("BackupManagerService", "Didn't read the right header magic");
              goto _L15
_L12:
            if(!flag) goto _L18; else goto _L17
_L17:
            Object obj3 = new InflaterInputStream(((InputStream) (obj2)));
_L19:
            boolean flag2;
            do
                flag2 = restoreOneFile(((InputStream) (obj3)), abyte0);
            while(flag2);
            tearDownPipes();
            tearDownAgent(mTargetApp);
            if(datainputstream1 == null)
                break MISSING_BLOCK_LABEL_859;
            datainputstream1.close();
            if(fileinputstream1 != null)
                fileinputstream1.close();
            mInputFile.close();
_L24:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndRestore();
            Slog.d("BackupManagerService", "Full restore pass complete.");
            mWakelock.release();
              goto _L13
_L18:
            obj3 = obj2;
              goto _L19
            IOException ioexception1;
            ioexception1;
            Slog.w("BackupManagerService", "Close of restore data pipe threw", ioexception1);
              goto _L20
            exception1;
            obj;
            JVM INSTR monitorexit ;
            throw exception1;
            exception2;
            atomicboolean;
            JVM INSTR monitorexit ;
            throw exception2;
            IOException ioexception2;
            ioexception2;
            Slog.w("BackupManagerService", "Close of restore data pipe threw", ioexception2);
              goto _L21
            exception3;
            obj1;
            JVM INSTR monitorexit ;
            throw exception3;
            exception4;
            atomicboolean1;
            JVM INSTR monitorexit ;
            throw exception4;
            IOException ioexception7;
            ioexception7;
            Slog.w("BackupManagerService", "Close of restore data pipe threw", ioexception7);
              goto _L22
            exception9;
            obj6;
            JVM INSTR monitorexit ;
            throw exception9;
            exception10;
            atomicboolean4;
            JVM INSTR monitorexit ;
            throw exception10;
            IOException ioexception5;
            ioexception5;
            Slog.w("BackupManagerService", "Close of restore data pipe threw", ioexception5);
              goto _L23
            exception7;
            obj5;
            JVM INSTR monitorexit ;
            throw exception7;
            exception8;
            atomicboolean3;
            JVM INSTR monitorexit ;
            throw exception8;
            IOException ioexception4;
            ioexception4;
            Slog.w("BackupManagerService", "Close of restore data pipe threw", ioexception4);
              goto _L24
            exception5;
            obj4;
            JVM INSTR monitorexit ;
            throw exception5;
            exception6;
            atomicboolean2;
            JVM INSTR monitorexit ;
            throw exception6;
            exception;
              goto _L25
            exception;
            fileinputstream = fileinputstream1;
              goto _L25
            IOException ioexception;
            ioexception;
              goto _L26
            IOException ioexception6;
            ioexception6;
            fileinputstream = fileinputstream1;
              goto _L26
        }

        void sendEndRestore() {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_16;
            mObserver.onEndRestore();
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full restore observer went away: endRestore");
            mObserver = null;
              goto _L1
        }

        void sendOnRestorePackage(String s) {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_17;
            mObserver.onRestorePackage(s);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full restore observer went away: restorePackage");
            mObserver = null;
              goto _L1
        }

        void sendStartRestore() {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_16;
            mObserver.onStartRestore();
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full restore observer went away: startRestore");
            mObserver = null;
              goto _L1
        }

        void setUpPipes() throws IOException {
            mPipes = ParcelFileDescriptor.createPipe();
        }

        void skipTarPadding(long l, InputStream inputstream) throws IOException {
label0:
            {
                long l1 = (l + 512L) % 512L;
                if(l1 > 0L) {
                    int i = 512 - (int)l1;
                    if(readExactly(inputstream, new byte[i], 0, i) != i)
                        break label0;
                    mBytes = mBytes + (long)i;
                }
                return;
            }
            throw new IOException("Unexpected EOF in padding");
        }

        void tearDownAgent(ApplicationInfo applicationinfo) {
            if(mAgent == null) goto _L2; else goto _L1
_L1:
            mActivityManager.unbindBackupAgent(applicationinfo);
            if(applicationinfo.uid == 1000 || applicationinfo.packageName.equals("com.android.backupconfirm")) goto _L4; else goto _L3
_L3:
            Slog.d("BackupManagerService", "Killing host process");
            mActivityManager.killApplicationProcess(applicationinfo.processName, applicationinfo.uid);
_L6:
            mAgent = null;
_L2:
            return;
_L4:
            try {
                Slog.d("BackupManagerService", "Not killing after full restore");
            }
            catch(RemoteException remoteexception) {
                Slog.d("BackupManagerService", "Lost app trying to shut down");
            }
            if(true) goto _L6; else goto _L5
_L5:
        }

        void tearDownPipes() {
            if(mPipes != null) {
                try {
                    mPipes[0].close();
                    mPipes[0] = null;
                    mPipes[1].close();
                    mPipes[1] = null;
                }
                catch(IOException ioexception) {
                    Slog.w("BackupManagerService", "Couldn't close agent pipes", ioexception);
                }
                mPipes = null;
            }
        }

        IBackupAgent mAgent;
        String mAgentPackage;
        long mBytes;
        final HashSet mClearedPackages = new HashSet();
        String mCurrentPassword;
        String mDecryptPassword;
        final RestoreDeleteObserver mDeleteObserver = new RestoreDeleteObserver();
        ParcelFileDescriptor mInputFile;
        final RestoreInstallObserver mInstallObserver = new RestoreInstallObserver();
        AtomicBoolean mLatchObject;
        final HashMap mManifestSignatures = new HashMap();
        IFullBackupRestoreObserver mObserver;
        final HashMap mPackageInstallers = new HashMap();
        final HashMap mPackagePolicies = new HashMap();
        ParcelFileDescriptor mPipes[];
        ApplicationInfo mTargetApp;
        final BackupManagerService this$0;

        PerformFullRestoreTask(ParcelFileDescriptor parcelfiledescriptor, String s, String s1, IFullBackupRestoreObserver ifullbackuprestoreobserver, AtomicBoolean atomicboolean) {
            this$0 = BackupManagerService.this;
            super();
            mPipes = null;
            mInputFile = parcelfiledescriptor;
            mCurrentPassword = s;
            mDecryptPassword = s1;
            mObserver = ifullbackuprestoreobserver;
            mLatchObject = atomicboolean;
            mAgent = null;
            mAgentPackage = null;
            mTargetApp = null;
            mClearedPackages.add("android");
            mClearedPackages.add("com.android.providers.settings");
        }
    }

    static final class RestorePolicy extends Enum {

        public static RestorePolicy valueOf(String s) {
            return (RestorePolicy)Enum.valueOf(com/android/server/BackupManagerService$RestorePolicy, s);
        }

        public static RestorePolicy[] values() {
            return (RestorePolicy[])$VALUES.clone();
        }

        private static final RestorePolicy $VALUES[];
        public static final RestorePolicy ACCEPT;
        public static final RestorePolicy ACCEPT_IF_APK;
        public static final RestorePolicy IGNORE;

        static  {
            IGNORE = new RestorePolicy("IGNORE", 0);
            ACCEPT = new RestorePolicy("ACCEPT", 1);
            ACCEPT_IF_APK = new RestorePolicy("ACCEPT_IF_APK", 2);
            RestorePolicy arestorepolicy[] = new RestorePolicy[3];
            arestorepolicy[0] = IGNORE;
            arestorepolicy[1] = ACCEPT;
            arestorepolicy[2] = ACCEPT_IF_APK;
            $VALUES = arestorepolicy;
        }

        private RestorePolicy(String s, int i) {
            super(s, i);
        }
    }

    static class FileMetadata {

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("FileMetadata{");
            stringbuilder.append(packageName);
            stringbuilder.append(',');
            stringbuilder.append(type);
            stringbuilder.append(',');
            stringbuilder.append(domain);
            stringbuilder.append(':');
            stringbuilder.append(path);
            stringbuilder.append(',');
            stringbuilder.append(size);
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        String domain;
        String installerPackageName;
        long mode;
        long mtime;
        String packageName;
        String path;
        long size;
        int type;

        FileMetadata() {
        }
    }

    class PerformFullBackupTask
        implements Runnable {
        class FullBackupRunner
            implements Runnable {

            public void run() {
                BackupDataOutput backupdataoutput = new BackupDataOutput(mPipe.getFileDescriptor());
                if(mWriteManifest) {
                    writeAppManifest(mPackage, mManifestFile, mSendApk);
                    FullBackup.backupToTar(mPackage.packageName, null, null, mFilesDir.getAbsolutePath(), mManifestFile.getAbsolutePath(), backupdataoutput);
                }
                if(mSendApk)
                    writeApkToBackup(mPackage, backupdataoutput);
                Slog.d("BackupManagerService", (new StringBuilder()).append("Calling doFullBackup() on ").append(mPackage.packageName).toString());
                prepareOperationTimeout(mToken, 0x493e0L, null);
                mAgent.doFullBackup(mPipe, mToken, mBackupManagerBinder);
                mPipe.close();
_L1:
                return;
                IOException ioexception2;
                ioexception2;
                Slog.e("BackupManagerService", (new StringBuilder()).append("Error running full backup for ").append(mPackage.packageName).toString());
                try {
                    mPipe.close();
                }
                catch(IOException ioexception1) { }
                  goto _L1
                RemoteException remoteexception;
                remoteexception;
                Slog.e("BackupManagerService", (new StringBuilder()).append("Remote agent vanished during full backup of ").append(mPackage.packageName).toString());
                mPipe.close();
                  goto _L1
                Exception exception;
                exception;
                try {
                    mPipe.close();
                }
                catch(IOException ioexception) { }
                throw exception;
            }

            IBackupAgent mAgent;
            PackageInfo mPackage;
            ParcelFileDescriptor mPipe;
            boolean mSendApk;
            int mToken;
            boolean mWriteManifest;
            final PerformFullBackupTask this$1;

            FullBackupRunner(PackageInfo packageinfo, IBackupAgent ibackupagent, ParcelFileDescriptor parcelfiledescriptor, int i, boolean flag, boolean flag1) throws IOException {
                this$1 = PerformFullBackupTask.this;
                super();
                mPackage = packageinfo;
                mAgent = ibackupagent;
                mPipe = ParcelFileDescriptor.dup(parcelfiledescriptor.getFileDescriptor());
                mToken = i;
                mSendApk = flag;
                mWriteManifest = flag1;
            }
        }


        private void backupOnePackage(PackageInfo packageinfo, OutputStream outputstream) throws RemoteException {
            IBackupAgent ibackupagent;
            Slog.d("BackupManagerService", (new StringBuilder()).append("Binding to full backup agent : ").append(packageinfo.packageName).toString());
            ibackupagent = bindToAgentSynchronous(packageinfo.applicationInfo, 1);
            if(ibackupagent == null) goto _L2; else goto _L1
_L1:
            ParcelFileDescriptor aparcelfiledescriptor[] = null;
            ApplicationInfo applicationinfo;
            boolean flag;
            aparcelfiledescriptor = ParcelFileDescriptor.createPipe();
            applicationinfo = packageinfo.applicationInfo;
            flag = packageinfo.packageName.equals("com.android.sharedstoragebackup");
            Exception exception;
            IOException ioexception1;
            IOException ioexception2;
            String s;
            String s1;
            boolean flag1;
            String s2;
            int i;
            ParcelFileDescriptor parcelfiledescriptor;
            boolean flag2;
            DataInputStream datainputstream;
            IOException ioexception3;
            IOException ioexception4;
            byte abyte0[];
            int j;
            int k;
            int l;
            if(!mIncludeApks || flag || (0x20000000 & applicationinfo.flags) != 0 || (1 & applicationinfo.flags) != 0 && (0x80 & applicationinfo.flags) == 0)
                flag1 = false;
            else
                flag1 = true;
              goto _L3
_L10:
            sendOnBackupPackage(s2);
            i = generateToken();
            parcelfiledescriptor = aparcelfiledescriptor[1];
            if(flag) goto _L5; else goto _L4
_L4:
            flag2 = true;
_L18:
            FullBackupRunner fullbackuprunner = new FullBackupRunner(packageinfo, ibackupagent, parcelfiledescriptor, i, flag1, flag2);
            aparcelfiledescriptor[1].close();
            aparcelfiledescriptor[1] = null;
            Thread thread = new Thread(fullbackuprunner);
            thread.start();
            FileInputStream fileinputstream = new FileInputStream(aparcelfiledescriptor[0].getFileDescriptor());
            datainputstream = new DataInputStream(fileinputstream);
            abyte0 = new byte[16384];
_L9:
            j = datainputstream.readInt();
            if(j <= 0) goto _L7; else goto _L6
_L6:
            if(j <= 0) goto _L9; else goto _L8
_L8:
            if(j <= abyte0.length)
                break MISSING_BLOCK_LABEL_647;
            k = abyte0.length;
_L19:
            l = datainputstream.read(abyte0, 0, k);
            outputstream.write(abyte0, 0, l);
            j -= l;
              goto _L6
_L17:
            s2 = packageinfo.packageName;
              goto _L10
            ioexception3;
            Slog.i("BackupManagerService", "Caught exception reading from agent", ioexception3);
_L7:
            if(waitUntilOperationComplete(i)) goto _L12; else goto _L11
_L11:
            Slog.e("BackupManagerService", (new StringBuilder()).append("Full backup failed on package ").append(packageinfo.packageName).toString());
_L13:
            outputstream.flush();
            if(aparcelfiledescriptor != null) {
                if(aparcelfiledescriptor[0] != null)
                    aparcelfiledescriptor[0].close();
                if(aparcelfiledescriptor[1] != null)
                    aparcelfiledescriptor[1].close();
            }
_L14:
            tearDown(packageinfo);
            return;
_L12:
            Slog.d("BackupManagerService", (new StringBuilder()).append("Full package backup success: ").append(packageinfo.packageName).toString());
              goto _L13
            ioexception1;
            Slog.e("BackupManagerService", (new StringBuilder()).append("Error backing up ").append(packageinfo.packageName).toString(), ioexception1);
            outputstream.flush();
            if(aparcelfiledescriptor != null) {
                if(aparcelfiledescriptor[0] != null)
                    aparcelfiledescriptor[0].close();
                if(aparcelfiledescriptor[1] != null)
                    aparcelfiledescriptor[1].close();
            }
              goto _L14
            ioexception2;
            s = "BackupManagerService";
            s1 = "Error bringing down backup stack";
_L15:
            Slog.w(s, s1);
              goto _L14
            exception;
            try {
                outputstream.flush();
                if(aparcelfiledescriptor != null) {
                    if(aparcelfiledescriptor[0] != null)
                        aparcelfiledescriptor[0].close();
                    if(aparcelfiledescriptor[1] != null)
                        aparcelfiledescriptor[1].close();
                }
            }
            catch(IOException ioexception) {
                Slog.w("BackupManagerService", "Error bringing down backup stack");
            }
            throw exception;
_L2:
            Slog.w("BackupManagerService", (new StringBuilder()).append("Unable to bind to full agent for ").append(packageinfo.packageName).toString());
              goto _L14
            ioexception4;
            s = "BackupManagerService";
            s1 = "Error bringing down backup stack";
              goto _L15
_L3:
            if(!flag) goto _L17; else goto _L16
_L16:
            s2 = "Shared storage";
              goto _L10
_L5:
            flag2 = false;
              goto _L18
            k = j;
              goto _L19
        }

        private OutputStream emitAesBackupHeader(StringBuilder stringbuilder, OutputStream outputstream) throws Exception {
            byte abyte0[] = randomBytes(512);
            SecretKey secretkey = buildPasswordKey(mEncryptPassword, abyte0, 10000);
            byte abyte1[] = new byte[32];
            mRng.nextBytes(abyte1);
            byte abyte2[] = randomBytes(512);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretkeyspec = new SecretKeySpec(abyte1, "AES");
            cipher.init(1, secretkeyspec);
            CipherOutputStream cipheroutputstream = new CipherOutputStream(outputstream, cipher);
            stringbuilder.append("AES-256");
            stringbuilder.append('\n');
            stringbuilder.append(byteArrayToHex(abyte0));
            stringbuilder.append('\n');
            stringbuilder.append(byteArrayToHex(abyte2));
            stringbuilder.append('\n');
            stringbuilder.append(10000);
            stringbuilder.append('\n');
            Cipher cipher1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher1.init(1, secretkey);
            byte abyte3[] = cipher1.getIV();
            stringbuilder.append(byteArrayToHex(abyte3));
            stringbuilder.append('\n');
            byte abyte4[] = cipher.getIV();
            byte abyte5[] = secretkeyspec.getEncoded();
            byte abyte6[] = makeKeyChecksum(secretkeyspec.getEncoded(), abyte2, 10000);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(3 + (abyte4.length + abyte5.length + abyte6.length));
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
            dataoutputstream.writeByte(abyte4.length);
            dataoutputstream.write(abyte4);
            dataoutputstream.writeByte(abyte5.length);
            dataoutputstream.write(abyte5);
            dataoutputstream.writeByte(abyte6.length);
            dataoutputstream.write(abyte6);
            dataoutputstream.flush();
            byte abyte7[] = cipher1.doFinal(bytearrayoutputstream.toByteArray());
            stringbuilder.append(byteArrayToHex(abyte7));
            stringbuilder.append('\n');
            return cipheroutputstream;
        }

        private void finalizeBackup(OutputStream outputstream) {
            outputstream.write(new byte[1024]);
_L1:
            return;
            IOException ioexception;
            ioexception;
            Slog.w("BackupManagerService", "Error attempting to finalize backup stream");
              goto _L1
        }

        private void tearDown(PackageInfo packageinfo) {
            ApplicationInfo applicationinfo;
            if(packageinfo == null)
                break MISSING_BLOCK_LABEL_66;
            applicationinfo = packageinfo.applicationInfo;
            if(applicationinfo == null)
                break MISSING_BLOCK_LABEL_66;
            mActivityManager.unbindBackupAgent(applicationinfo);
            if(applicationinfo.uid != 1000 && applicationinfo.uid != 1001)
                mActivityManager.killApplicationProcess(applicationinfo.processName, applicationinfo.uid);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.d("BackupManagerService", "Lost app trying to shut down");
              goto _L1
        }

        private void writeApkToBackup(PackageInfo packageinfo, BackupDataOutput backupdataoutput) {
            String s = packageinfo.applicationInfo.sourceDir;
            String s1 = (new File(s)).getParent();
            FullBackup.backupToTar(packageinfo.packageName, "a", null, s1, s, backupdataoutput);
            File file = Environment.getExternalStorageAppObbDirectory(packageinfo.packageName);
            if(file != null) {
                File afile[] = file.listFiles();
                if(afile != null) {
                    String s2 = file.getAbsolutePath();
                    int i = afile.length;
                    for(int j = 0; j < i; j++) {
                        File file1 = afile[j];
                        FullBackup.backupToTar(packageinfo.packageName, "obb", null, s2, file1.getAbsolutePath(), backupdataoutput);
                    }

                }
            }
        }

        private void writeAppManifest(PackageInfo packageinfo, File file, boolean flag) throws IOException {
            StringBuilder stringbuilder = new StringBuilder(4096);
            StringBuilderPrinter stringbuilderprinter = new StringBuilderPrinter(stringbuilder);
            stringbuilderprinter.println(Integer.toString(1));
            stringbuilderprinter.println(packageinfo.packageName);
            stringbuilderprinter.println(Integer.toString(packageinfo.versionCode));
            stringbuilderprinter.println(Integer.toString(android.os.Build.VERSION.SDK_INT));
            String s = mPackageManager.getInstallerPackageName(packageinfo.packageName);
            String s1;
            FileOutputStream fileoutputstream;
            if(s == null)
                s = "";
            stringbuilderprinter.println(s);
            if(flag)
                s1 = "1";
            else
                s1 = "0";
            stringbuilderprinter.println(s1);
            if(packageinfo.signatures == null) {
                stringbuilderprinter.println("0");
            } else {
                stringbuilderprinter.println(Integer.toString(packageinfo.signatures.length));
                Signature asignature[] = packageinfo.signatures;
                int i = asignature.length;
                int j = 0;
                while(j < i)  {
                    stringbuilderprinter.println(asignature[j].toCharsString());
                    j++;
                }
            }
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(stringbuilder.toString().getBytes());
            fileoutputstream.close();
        }

        public void run() {
            Object obj;
            FileOutputStream fileoutputstream;
            DeflaterOutputStream deflateroutputstream;
            PackageInfo packageinfo;
            obj = new ArrayList();
            Slog.i("BackupManagerService", "--- Performing full-dataset backup ---");
            sendStartBackup();
            if(mAllApps) {
                obj = mPackageManager.getInstalledPackages(64);
                if(!mIncludeSystem) {
                    for(int k1 = 0; k1 < ((List) (obj)).size();)
                        if((1 & ((PackageInfo)((List) (obj)).get(k1)).applicationInfo.flags) != 0)
                            ((List) (obj)).remove(k1);
                        else
                            k1++;

                }
            }
            if(mPackages != null) {
                String as[] = mPackages;
                int i1 = as.length;
                int j1 = 0;
                while(j1 < i1)  {
                    String s1 = as[j1];
                    try {
                        PackageInfo packageinfo3 = mPackageManager.getPackageInfo(s1, 64);
                        ((List) (obj)).add(packageinfo3);
                    }
                    catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception1) {
                        Slog.w("BackupManagerService", (new StringBuilder()).append("Unknown package ").append(s1).append(", skipping").toString());
                    }
                    j1++;
                }
            }
            for(int i = 0; i < ((List) (obj)).size();) {
                PackageInfo packageinfo2 = (PackageInfo)((List) (obj)).get(i);
                if((0x8000 & packageinfo2.applicationInfo.flags) == 0 || packageinfo2.packageName.equals("com.android.sharedstoragebackup"))
                    ((List) (obj)).remove(i);
                else
                    i++;
            }

            for(int j = 0; j < ((List) (obj)).size();) {
                PackageInfo packageinfo1 = (PackageInfo)((List) (obj)).get(j);
                if(packageinfo1.applicationInfo.uid < 10000 && packageinfo1.applicationInfo.backupAgentName == null)
                    ((List) (obj)).remove(j);
                else
                    j++;
            }

            fileoutputstream = new FileOutputStream(mOutputFile.getFileDescriptor());
            deflateroutputstream = null;
            packageinfo = null;
            if(mEncryptPassword == null || mEncryptPassword.length() <= 0) goto _L2; else goto _L1
_L1:
            boolean flag = true;
_L5:
            if(!hasBackupPassword() || passwordMatchesSaved(mCurrentPassword, 10000)) goto _L4; else goto _L3
_L3:
            Slog.w("BackupManagerService", "Backup password mismatch; aborting");
            tearDown(null);
            if(true)
                break MISSING_BLOCK_LABEL_434;
            throw null;
            mOutputFile.close();
_L17:
            android.os.PowerManager.WakeLock wakelock;
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            wakelock = mWakelock;
_L13:
            wakelock.release();
            return;
_L2:
            flag = false;
              goto _L5
_L4:
            StringBuilder stringbuilder;
            stringbuilder = new StringBuilder(1024);
            stringbuilder.append("ANDROID BACKUP\n");
            stringbuilder.append(1);
            if(false) goto _L7; else goto _L6
_L6:
            String s = "\n1\n";
_L11:
            stringbuilder.append(s);
            if(!flag) goto _L9; else goto _L8
_L8:
            OutputStream outputstream = emitAesBackupHeader(stringbuilder, fileoutputstream);
            Object obj5 = outputstream;
_L12:
            Object obj6;
            fileoutputstream.write(stringbuilder.toString().getBytes("UTF-8"));
            if(false)
                break MISSING_BLOCK_LABEL_1429;
            obj6 = new DeflaterOutputStream(((OutputStream) (obj5)), new Deflater(9), true);
_L22:
            deflateroutputstream = ((DeflaterOutputStream) (obj6));
            boolean flag1 = mIncludeShared;
            if(!flag1)
                break MISSING_BLOCK_LABEL_671;
            packageinfo = mPackageManager.getPackageInfo("com.android.sharedstoragebackup", 0);
            ((List) (obj)).add(packageinfo);
_L14:
            int k = ((List) (obj)).size();
            for(int l = 0; l < k; l++) {
                packageinfo = (PackageInfo)((List) (obj)).get(l);
                backupOnePackage(packageinfo, deflateroutputstream);
            }

              goto _L10
_L7:
            s = "\n0\n";
              goto _L11
_L9:
            stringbuilder.append("none\n");
            obj5 = fileoutputstream;
              goto _L12
            Exception exception8;
            exception8;
_L21:
            Slog.e("BackupManagerService", "Unable to emit archive header", exception8);
            tearDown(null);
            if(true)
                break MISSING_BLOCK_LABEL_764;
            throw null;
            mOutputFile.close();
_L16:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            wakelock = mWakelock;
              goto _L13
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            namenotfoundexception;
            Slog.e("BackupManagerService", "Unable to find shared-storage backup handler");
              goto _L14
            RemoteException remoteexception;
            remoteexception;
            Slog.e("BackupManagerService", "App died during full backup");
            tearDown(packageinfo);
            if(deflateroutputstream == null)
                break MISSING_BLOCK_LABEL_889;
            deflateroutputstream.close();
            mOutputFile.close();
_L19:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            wakelock = mWakelock;
              goto _L13
_L10:
            finalizeBackup(deflateroutputstream);
            tearDown(packageinfo);
            if(deflateroutputstream == null)
                break MISSING_BLOCK_LABEL_995;
            deflateroutputstream.close();
            mOutputFile.close();
_L15:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            wakelock = mWakelock;
              goto _L13
            Exception exception3;
            exception3;
            Slog.e("BackupManagerService", "Internal exception during full backup", exception3);
            tearDown(packageinfo);
            if(deflateroutputstream == null)
                break MISSING_BLOCK_LABEL_1108;
            deflateroutputstream.close();
            mOutputFile.close();
_L18:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            wakelock = mWakelock;
              goto _L13
            Exception exception;
            exception;
            tearDown(packageinfo);
            if(deflateroutputstream == null)
                break MISSING_BLOCK_LABEL_1210;
            deflateroutputstream.close();
            mOutputFile.close();
_L20:
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            synchronized(mLatchObject) {
                mLatchObject.set(true);
                mLatchObject.notifyAll();
            }
            sendEndBackup();
            Slog.d("BackupManagerService", "Full backup pass complete.");
            mWakelock.release();
            throw exception;
            exception1;
            obj1;
            JVM INSTR monitorexit ;
            throw exception1;
            exception2;
            atomicboolean;
            JVM INSTR monitorexit ;
            throw exception2;
            exception6;
            obj3;
            JVM INSTR monitorexit ;
            throw exception6;
            exception7;
            atomicboolean2;
            JVM INSTR monitorexit ;
            throw exception7;
            exception4;
            obj2;
            JVM INSTR monitorexit ;
            throw exception4;
            exception5;
            atomicboolean1;
            JVM INSTR monitorexit ;
            throw exception5;
            exception13;
            obj8;
            JVM INSTR monitorexit ;
            throw exception13;
            exception14;
            atomicboolean5;
            JVM INSTR monitorexit ;
            throw exception14;
            exception9;
            obj4;
            JVM INSTR monitorexit ;
            throw exception9;
            exception10;
            atomicboolean3;
            JVM INSTR monitorexit ;
            throw exception10;
            exception11;
            obj7;
            JVM INSTR monitorexit ;
            throw exception11;
            exception12;
            atomicboolean4;
            JVM INSTR monitorexit ;
            throw exception12;
            IOException ioexception4;
            ioexception4;
              goto _L15
            IOException ioexception3;
            ioexception3;
              goto _L16
            IOException ioexception5;
            ioexception5;
              goto _L17
            IOException ioexception1;
            ioexception1;
              goto _L18
            IOException ioexception2;
            ioexception2;
              goto _L19
            IOException ioexception;
            ioexception;
              goto _L20
            exception8;
            obj5;
              goto _L21
            obj6 = obj5;
              goto _L22
        }

        void sendEndBackup() {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_16;
            mObserver.onEndBackup();
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full backup observer went away: endBackup");
            mObserver = null;
              goto _L1
        }

        void sendOnBackupPackage(String s) {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_17;
            mObserver.onBackupPackage(s);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full backup observer went away: backupPackage");
            mObserver = null;
              goto _L1
        }

        void sendStartBackup() {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_16;
            mObserver.onStartBackup();
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("BackupManagerService", "full backup observer went away: startBackup");
            mObserver = null;
              goto _L1
        }

        boolean mAllApps;
        String mCurrentPassword;
        DeflaterOutputStream mDeflater;
        String mEncryptPassword;
        File mFilesDir;
        boolean mIncludeApks;
        boolean mIncludeShared;
        final boolean mIncludeSystem;
        AtomicBoolean mLatchObject;
        File mManifestFile;
        IFullBackupRestoreObserver mObserver;
        ParcelFileDescriptor mOutputFile;
        String mPackages[];
        final BackupManagerService this$0;



        PerformFullBackupTask(ParcelFileDescriptor parcelfiledescriptor, IFullBackupRestoreObserver ifullbackuprestoreobserver, boolean flag, boolean flag1, String s, String s1, 
                boolean flag2, boolean flag3, String as[], AtomicBoolean atomicboolean) {
            this$0 = BackupManagerService.this;
            super();
            mOutputFile = parcelfiledescriptor;
            mObserver = ifullbackuprestoreobserver;
            mIncludeApks = flag;
            mIncludeShared = flag1;
            mAllApps = flag2;
            mIncludeSystem = flag3;
            mPackages = as;
            mCurrentPassword = s;
            if(s1 == null || "".equals(s1))
                mEncryptPassword = s;
            else
                mEncryptPassword = s1;
            mLatchObject = atomicboolean;
            mFilesDir = new File("/data/system");
            mManifestFile = new File(mFilesDir, "_manifest");
        }
    }

    class PerformBackupTask
        implements BackupRestoreTask {

        void agentErrorCleanup() {
            mBackupDataName.delete();
            mNewStateName.delete();
            clearAgentState();
            BackupState backupstate;
            if(mQueue.isEmpty())
                backupstate = BackupState.FINAL;
            else
                backupstate = BackupState.RUNNING_QUEUE;
            executeNextState(backupstate);
        }

        void beginBackup() {
            clearBackupTrace();
            StringBuilder stringbuilder = new StringBuilder(256);
            stringbuilder.append("beginBackup: [");
            BackupRequest backuprequest;
            for(Iterator iterator = mOriginalQueue.iterator(); iterator.hasNext(); stringbuilder.append(backuprequest.packageName)) {
                backuprequest = (BackupRequest)iterator.next();
                stringbuilder.append(' ');
            }

            stringbuilder.append(" ]");
            addBackupTrace(stringbuilder.toString());
            mStatus = 0;
            if(!mOriginalQueue.isEmpty()) goto _L2; else goto _L1
_L1:
            Slog.w("PerformBackupTask", "Backup begun with an empty queue - nothing to do.");
            addBackupTrace("queue empty at begin");
            executeNextState(BackupState.FINAL);
_L5:
            return;
_L2:
            File file;
            mQueue = (ArrayList)mOriginalQueue.clone();
            Slog.v("PerformBackupTask", (new StringBuilder()).append("Beginning backup of ").append(mQueue.size()).append(" targets").toString());
            file = new File(mStateDir, "@pm@");
            String s = mTransport.transportDirName();
            EventLog.writeEvent(2821, s);
            if(mStatus == 0 && file.length() <= 0L) {
                Slog.i("PerformBackupTask", "Initializing (wiping) backup state and transport storage");
                addBackupTrace((new StringBuilder()).append("initializing transport ").append(s).toString());
                resetBackupState(mStateDir);
                mStatus = mTransport.initializeDevice();
                addBackupTrace((new StringBuilder()).append("transport.initializeDevice() == ").append(mStatus).toString());
                if(mStatus != 0)
                    break MISSING_BLOCK_LABEL_497;
                EventLog.writeEvent(2827, new Object[0]);
            }
_L3:
            if(mStatus == 0) {
                mStatus = invokeAgentForBackup("@pm@", android.app.IBackupAgent.Stub.asInterface((new PackageManagerBackupAgent(mPackageManager, allAgentPackages())).onBind()), mTransport);
                addBackupTrace((new StringBuilder()).append("PMBA invoke: ").append(mStatus).toString());
            }
            if(mStatus == 2)
                EventLog.writeEvent(2826, mTransport.transportDirName());
            addBackupTrace((new StringBuilder()).append("exiting prelim: ").append(mStatus).toString());
            if(mStatus != 0) {
                resetBackupState(mStateDir);
                executeNextState(BackupState.FINAL);
            }
            continue; /* Loop/switch isn't completed */
            EventLog.writeEvent(2822, "(initialize)");
            Slog.e("PerformBackupTask", "Transport error in initializeDevice()");
              goto _L3
            Exception exception1;
            exception1;
            Slog.e("PerformBackupTask", "Error in backup thread", exception1);
            addBackupTrace((new StringBuilder()).append("Exception in backup thread: ").append(exception1).toString());
            mStatus = 1;
            addBackupTrace((new StringBuilder()).append("exiting prelim: ").append(mStatus).toString());
            if(mStatus != 0) {
                resetBackupState(mStateDir);
                executeNextState(BackupState.FINAL);
            }
            if(true) goto _L5; else goto _L4
_L4:
            Exception exception;
            exception;
            addBackupTrace((new StringBuilder()).append("exiting prelim: ").append(mStatus).toString());
            if(mStatus != 0) {
                resetBackupState(mStateDir);
                executeNextState(BackupState.FINAL);
            }
            throw exception;
        }

        void clearAgentState() {
            RemoteException remoteexception;
            try {
                if(mSavedState != null)
                    mSavedState.close();
            }
            catch(IOException ioexception) { }
            try {
                if(mBackupData != null)
                    mBackupData.close();
            }
            catch(IOException ioexception1) { }
            try {
                if(mNewState != null)
                    mNewState.close();
            }
            catch(IOException ioexception2) { }
            mNewState = null;
            mBackupData = null;
            mSavedState = null;
            synchronized(mCurrentOpLock) {
                mCurrentOperations.clear();
            }
            if(mCurrentPackage.applicationInfo == null)
                break MISSING_BLOCK_LABEL_144;
            addBackupTrace((new StringBuilder()).append("unbinding ").append(mCurrentPackage.packageName).toString());
            mActivityManager.unbindBackupAgent(mCurrentPackage.applicationInfo);
_L2:
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
            remoteexception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        void clearMetadata() {
            File file = new File(mStateDir, "@pm@");
            if(file.exists())
                file.delete();
        }

        public void execute() {
            _cls4..SwitchMap.com.android.server.BackupManagerService.BackupState[mCurrentState.ordinal()];
            JVM INSTR tableswitch 1 3: default 36
        //                       1 37
        //                       2 44
        //                       3 51;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L2:
            beginBackup();
            continue; /* Loop/switch isn't completed */
_L3:
            invokeNextAgent();
            continue; /* Loop/switch isn't completed */
_L4:
            if(!mFinished)
                finalizeBackup();
            else
                Slog.e("PerformBackupTask", "Duplicate finish");
            mFinished = true;
            if(true) goto _L1; else goto _L5
_L5:
        }

        void executeNextState(BackupState backupstate) {
            addBackupTrace((new StringBuilder()).append("executeNextState => ").append(backupstate).toString());
            mCurrentState = backupstate;
            Message message = mBackupHandler.obtainMessage(20, this);
            mBackupHandler.sendMessage(message);
        }

        void finalizeBackup() {
            addBackupTrace("finishing");
            if(mJournal != null && !mJournal.delete())
                Slog.e("PerformBackupTask", (new StringBuilder()).append("Unable to remove backup journal file ").append(mJournal).toString());
            if(mCurrentToken == 0L && mStatus == 0) {
                addBackupTrace("success; recording token");
                try {
                    mCurrentToken = mTransport.getCurrentRestoreSet();
                }
                catch(RemoteException remoteexception) { }
                writeRestoreTokens();
            }
            synchronized(mQueueLock) {
                mBackupRunning = false;
                if(mStatus == 2) {
                    clearMetadata();
                    Slog.d("PerformBackupTask", "Server requires init; rerunning");
                    addBackupTrace("init required; rerunning");
                    backupNow();
                }
            }
            clearBackupTrace();
            Slog.i("PerformBackupTask", "Backup pass finished.");
            mWakelock.release();
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public void handleTimeout() {
            Slog.e("PerformBackupTask", (new StringBuilder()).append("Timeout backing up ").append(mCurrentPackage.packageName).toString());
            Object aobj[] = new Object[2];
            aobj[0] = mCurrentPackage.packageName;
            aobj[1] = "timeout";
            EventLog.writeEvent(2823, aobj);
            addBackupTrace((new StringBuilder()).append("timeout of ").append(mCurrentPackage.packageName).toString());
            agentErrorCleanup();
            dataChangedImpl(mCurrentPackage.packageName);
        }

        int invokeAgentForBackup(String s, IBackupAgent ibackupagent, IBackupTransport ibackuptransport) {
            int i;
            Slog.d("PerformBackupTask", (new StringBuilder()).append("invokeAgentForBackup on ").append(s).toString());
            addBackupTrace((new StringBuilder()).append("invoking ").append(s).toString());
            mSavedStateName = new File(mStateDir, s);
            mBackupDataName = new File(mDataDir, (new StringBuilder()).append(s).append(".data").toString());
            mNewStateName = new File(mStateDir, (new StringBuilder()).append(s).append(".new").toString());
            mSavedState = null;
            mBackupData = null;
            mNewState = null;
            i = generateToken();
            if(s.equals("@pm@")) {
                mCurrentPackage = new PackageInfo();
                mCurrentPackage.packageName = s;
            }
            mSavedState = ParcelFileDescriptor.open(mSavedStateName, 0x18000000);
            mBackupData = ParcelFileDescriptor.open(mBackupDataName, 0x3c000000);
            mNewState = ParcelFileDescriptor.open(mNewStateName, 0x3c000000);
            addBackupTrace("setting timeout");
            prepareOperationTimeout(i, 30000L, this);
            addBackupTrace("calling agent doBackup()");
            ibackupagent.doBackup(mSavedState, mBackupData, mNewState, i, mBackupManagerBinder);
            int j;
            addBackupTrace("invoke success");
            j = 0;
_L2:
            return j;
            Exception exception;
            exception;
            Slog.e("PerformBackupTask", (new StringBuilder()).append("Error invoking for backup on ").append(s).toString());
            addBackupTrace((new StringBuilder()).append("exception: ").append(exception).toString());
            Object aobj[] = new Object[2];
            aobj[0] = s;
            aobj[1] = exception.toString();
            EventLog.writeEvent(2823, aobj);
            agentErrorCleanup();
            j = 3;
            if(true) goto _L2; else goto _L1
_L1:
        }

        void invokeNextAgent() {
            mStatus = 0;
            addBackupTrace((new StringBuilder()).append("invoke q=").append(mQueue.size()).toString());
            if(!mQueue.isEmpty()) goto _L2; else goto _L1
_L1:
            Slog.i("PerformBackupTask", "queue now empty");
            executeNextState(BackupState.FINAL);
_L20:
            return;
_L2:
            BackupRequest backuprequest;
            backuprequest = (BackupRequest)mQueue.get(0);
            mQueue.remove(0);
            Slog.d("PerformBackupTask", (new StringBuilder()).append("starting agent for backup of ").append(backuprequest).toString());
            addBackupTrace((new StringBuilder()).append("launch agent for ").append(backuprequest.packageName).toString());
            mCurrentPackage = mPackageManager.getPackageInfo(backuprequest.packageName, 64);
            if(mCurrentPackage.applicationInfo.backupAgentName != null)
                break MISSING_BLOCK_LABEL_298;
            Slog.i("PerformBackupTask", (new StringBuilder()).append("Package ").append(backuprequest.packageName).append(" no longer supports backup; skipping").toString());
            addBackupTrace("skipping - no agent, completion is noop");
            executeNextState(BackupState.RUNNING_QUEUE);
            BackupState backupstate1;
            mWakelock.setWorkSource(null);
            if(mStatus == 0)
                break; /* Loop/switch isn't completed */
            backupstate1 = BackupState.RUNNING_QUEUE;
            Exception exception;
            BackupState backupstate;
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            SecurityException securityexception;
            IBackupAgent ibackupagent;
            BackupManagerService backupmanagerservice1;
            StringBuilder stringbuilder;
            boolean flag;
            if(mStatus == 3) {
                dataChangedImpl(backuprequest.packageName);
                mStatus = 0;
                if(mQueue.isEmpty())
                    backupstate1 = BackupState.FINAL;
            } else
            if(mStatus == 4) {
                mStatus = 0;
            } else {
                revertAndEndBackup();
                backupstate1 = BackupState.FINAL;
            }
            executeNextState(backupstate1);
            continue; /* Loop/switch isn't completed */
            mWakelock.setWorkSource(new WorkSource(mCurrentPackage.applicationInfo.uid));
            ibackupagent = bindToAgentSynchronous(mCurrentPackage.applicationInfo, 0);
            backupmanagerservice1 = BackupManagerService.this;
            stringbuilder = (new StringBuilder()).append("agent bound; a? = ");
            if(ibackupagent == null) goto _L4; else goto _L3
_L3:
            flag = true;
_L8:
            backupmanagerservice1.addBackupTrace(stringbuilder.append(flag).toString());
            if(ibackupagent == null) goto _L6; else goto _L5
_L5:
            mStatus = invokeAgentForBackup(backuprequest.packageName, ibackupagent, mTransport);
_L9:
            mWakelock.setWorkSource(null);
            if(mStatus == 0)
                break MISSING_BLOCK_LABEL_815;
            backupstate1 = BackupState.RUNNING_QUEUE;
            BackupManagerService backupmanagerservice;
            String s;
            if(mStatus == 3) {
                dataChangedImpl(backuprequest.packageName);
                mStatus = 0;
                if(mQueue.isEmpty())
                    backupstate1 = BackupState.FINAL;
            } else
            if(mStatus == 4) {
                mStatus = 0;
            } else {
                revertAndEndBackup();
                backupstate1 = BackupState.FINAL;
            }
              goto _L7
_L4:
            flag = false;
              goto _L8
_L6:
            mStatus = 3;
              goto _L9
            securityexception;
            Slog.d("PerformBackupTask", "error in bind/backup", securityexception);
            mStatus = 3;
            addBackupTrace("agent SE");
              goto _L9
            namenotfoundexception;
            Slog.d("PerformBackupTask", "Package does not exist; skipping");
            addBackupTrace("no such package");
            mStatus = 4;
            mWakelock.setWorkSource(null);
            if(mStatus == 0) goto _L11; else goto _L10
_L10:
            backupstate1 = BackupState.RUNNING_QUEUE;
            if(mStatus == 3) {
                dataChangedImpl(backuprequest.packageName);
                mStatus = 0;
                if(mQueue.isEmpty())
                    backupstate1 = BackupState.FINAL;
            } else
            if(mStatus == 4) {
                mStatus = 0;
            } else {
                revertAndEndBackup();
                backupstate1 = BackupState.FINAL;
            }
_L7:
            if(true) goto _L12; else goto _L11
_L12:
            break MISSING_BLOCK_LABEL_289;
            exception;
            mWakelock.setWorkSource(null);
            if(mStatus == 0) goto _L14; else goto _L13
_L13:
            backupstate = BackupState.RUNNING_QUEUE;
            if(mStatus != 3) goto _L16; else goto _L15
_L15:
            dataChangedImpl(backuprequest.packageName);
            mStatus = 0;
            if(mQueue.isEmpty())
                backupstate = BackupState.FINAL;
_L18:
            executeNextState(backupstate);
_L17:
            throw exception;
_L14:
            addBackupTrace("expecting completion/timeout callback");
            if(true) goto _L17; else goto _L16
_L16:
            if(mStatus == 4) {
                mStatus = 0;
            } else {
                revertAndEndBackup();
                backupstate = BackupState.FINAL;
            }
            if(true) goto _L18; else goto _L11
_L11:
            backupmanagerservice = BackupManagerService.this;
            s = "expecting completion/timeout callback";
_L21:
            backupmanagerservice.addBackupTrace(s);
            if(true) goto _L20; else goto _L19
_L19:
            backupmanagerservice = BackupManagerService.this;
            s = "expecting completion/timeout callback";
              goto _L21
            backupmanagerservice = BackupManagerService.this;
            s = "expecting completion/timeout callback";
              goto _L21
        }

        public void operationComplete() {
            ParcelFileDescriptor parcelfiledescriptor;
            mBackupHandler.removeMessages(7);
            clearAgentState();
            addBackupTrace("operation complete");
            parcelfiledescriptor = null;
            mStatus = 0;
            int i = (int)mBackupDataName.length();
            if(i <= 0) goto _L2; else goto _L1
_L1:
            if(mStatus == 0) {
                parcelfiledescriptor = ParcelFileDescriptor.open(mBackupDataName, 0x10000000);
                addBackupTrace("sending data to transport");
                mStatus = mTransport.performBackup(mCurrentPackage, parcelfiledescriptor);
            }
            addBackupTrace((new StringBuilder()).append("data delivered: ").append(mStatus).toString());
            if(mStatus == 0) {
                addBackupTrace("finishing op on transport");
                mStatus = mTransport.finishBackup();
                addBackupTrace((new StringBuilder()).append("finished: ").append(mStatus).toString());
            }
_L5:
            if(mStatus != 0) goto _L4; else goto _L3
_L3:
            mBackupDataName.delete();
            mNewStateName.renameTo(mSavedStateName);
            Object aobj[] = new Object[2];
            aobj[0] = mCurrentPackage.packageName;
            aobj[1] = Integer.valueOf(i);
            EventLog.writeEvent(2824, aobj);
            logBackupComplete(mCurrentPackage.packageName);
_L8:
            Exception exception1;
            BackupState backupstate;
            if(parcelfiledescriptor != null)
                try {
                    parcelfiledescriptor.close();
                }
                catch(IOException ioexception1) { }
_L7:
            Exception exception;
            if(mStatus != 0) {
                revertAndEndBackup();
                backupstate = BackupState.FINAL;
            } else
            if(mQueue.isEmpty())
                backupstate = BackupState.FINAL;
            else
                backupstate = BackupState.RUNNING_QUEUE;
            executeNextState(backupstate);
            return;
_L2:
            Slog.i("PerformBackupTask", "no backup data written; not calling transport");
            addBackupTrace("no data to send");
              goto _L5
            exception1;
            Slog.e("PerformBackupTask", (new StringBuilder()).append("Transport error backing up ").append(mCurrentPackage.packageName).toString(), exception1);
            EventLog.writeEvent(2822, mCurrentPackage.packageName);
            mStatus = 1;
            if(parcelfiledescriptor == null) goto _L7; else goto _L6
_L6:
            parcelfiledescriptor.close();
              goto _L7
_L4:
            EventLog.writeEvent(2822, mCurrentPackage.packageName);
              goto _L8
            exception;
            if(parcelfiledescriptor != null)
                try {
                    parcelfiledescriptor.close();
                }
                catch(IOException ioexception) { }
            throw exception;
              goto _L5
        }

        void restartBackupAlarm() {
            addBackupTrace("setting backup trigger");
            Object obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            Exception exception;
            try {
                startBackupAlarmsLocked(mTransport.requestBackupTime());
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
            return;
            throw exception;
        }

        void revertAndEndBackup() {
            addBackupTrace("transport error; reverting");
            BackupRequest backuprequest;
            for(Iterator iterator = mOriginalQueue.iterator(); iterator.hasNext(); dataChangedImpl(backuprequest.packageName))
                backuprequest = (BackupRequest)iterator.next();

            restartBackupAlarm();
        }

        private static final String TAG = "PerformBackupTask";
        ParcelFileDescriptor mBackupData;
        File mBackupDataName;
        PackageInfo mCurrentPackage;
        BackupState mCurrentState;
        boolean mFinished;
        File mJournal;
        ParcelFileDescriptor mNewState;
        File mNewStateName;
        ArrayList mOriginalQueue;
        ArrayList mQueue;
        ParcelFileDescriptor mSavedState;
        File mSavedStateName;
        File mStateDir;
        int mStatus;
        IBackupTransport mTransport;
        final BackupManagerService this$0;

        public PerformBackupTask(IBackupTransport ibackuptransport, ArrayList arraylist, File file) {
            this$0 = BackupManagerService.this;
            super();
            mTransport = ibackuptransport;
            mOriginalQueue = arraylist;
            mJournal = file;
            try {
                mStateDir = new File(mBaseStateDir, ibackuptransport.transportDirName());
            }
            catch(RemoteException remoteexception) { }
            mCurrentState = BackupState.INITIAL;
            mFinished = false;
            addBackupTrace("STATE => INITIAL");
        }
    }

    static final class BackupState extends Enum {

        public static BackupState valueOf(String s) {
            return (BackupState)Enum.valueOf(com/android/server/BackupManagerService$BackupState, s);
        }

        public static BackupState[] values() {
            return (BackupState[])$VALUES.clone();
        }

        private static final BackupState $VALUES[];
        public static final BackupState FINAL;
        public static final BackupState INITIAL;
        public static final BackupState RUNNING_QUEUE;

        static  {
            INITIAL = new BackupState("INITIAL", 0);
            RUNNING_QUEUE = new BackupState("RUNNING_QUEUE", 1);
            FINAL = new BackupState("FINAL", 2);
            BackupState abackupstate[] = new BackupState[3];
            abackupstate[0] = INITIAL;
            abackupstate[1] = RUNNING_QUEUE;
            abackupstate[2] = FINAL;
            $VALUES = abackupstate;
        }

        private BackupState(String s, int i) {
            super(s, i);
        }
    }

    static interface BackupRestoreTask {

        public abstract void execute();

        public abstract void handleTimeout();

        public abstract void operationComplete();
    }

    class ClearDataObserver extends android.content.pm.IPackageDataObserver.Stub {

        public void onRemoveCompleted(String s, boolean flag) {
            Object obj = mClearDataLock;
            obj;
            JVM INSTR monitorenter ;
            mClearingData = false;
            mClearDataLock.notifyAll();
            return;
        }

        final BackupManagerService this$0;

        ClearDataObserver() {
            this$0 = BackupManagerService.this;
            super();
        }
    }

    private class RunInitializeReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if(!"android.app.backup.intent.INIT".equals(intent.getAction()))
                break MISSING_BLOCK_LABEL_78;
            Object obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            Slog.v("BackupManagerService", "Running a device init");
            mWakelock.acquire();
            Message message = mBackupHandler.obtainMessage(5);
            mBackupHandler.sendMessage(message);
        }

        final BackupManagerService this$0;

        private RunInitializeReceiver() {
            this$0 = BackupManagerService.this;
            super();
        }

    }

    private class RunBackupReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if(!"android.app.backup.intent.RUN".equals(intent.getAction())) goto _L2; else goto _L1
_L1:
            Object obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            if(mPendingInits.size() <= 0) goto _L4; else goto _L3
_L3:
            Slog.v("BackupManagerService", "Init pending at scheduled backup");
            try {
                mAlarmManager.cancel(mRunInitIntent);
                mRunInitIntent.send();
            }
            catch(android.app.PendingIntent.CanceledException canceledexception) {
                Slog.e("BackupManagerService", "Run init intent cancelled");
            }
_L5:
            obj;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
            Exception exception;
            exception;
            throw exception;
_L4:
            if(mEnabled && mProvisioned) {
                if(!mBackupRunning) {
                    Slog.v("BackupManagerService", "Running a backup pass");
                    mBackupRunning = true;
                    mWakelock.acquire();
                    Message message = mBackupHandler.obtainMessage(1);
                    mBackupHandler.sendMessage(message);
                } else {
                    Slog.i("BackupManagerService", "Backup time but one already running");
                }
            } else {
                Slog.w("BackupManagerService", (new StringBuilder()).append("Backup pass but e=").append(mEnabled).append(" p=").append(mProvisioned).toString());
            }
            if(true) goto _L5; else goto _L2
_L2:
        }

        final BackupManagerService this$0;

        private RunBackupReceiver() {
            this$0 = BackupManagerService.this;
            super();
        }

    }

    private class BackupHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 21: default 104
        //                       1 105
        //                       2 523
        //                       3 606
        //                       4 762
        //                       5 798
        //                       6 866
        //                       7 1187
        //                       8 1205
        //                       9 1276
        //                       10 704
        //                       11 104
        //                       12 104
        //                       13 104
        //                       14 104
        //                       15 104
        //                       16 104
        //                       17 104
        //                       18 104
        //                       19 104
        //                       20 427
        //                       21 475;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L12 _L13
_L1:
            return;
_L2:
            IBackupTransport ibackuptransport;
            mLastBackupPass = System.currentTimeMillis();
            mNextBackupPass = 0x36ee80L + mLastBackupPass;
            ibackuptransport = getTransport(mCurrentTransport);
            if(ibackuptransport == null) {
                Slog.v("BackupManagerService", "Backup requested but no transport available");
                synchronized(mQueueLock) {
                    mBackupRunning = false;
                }
                mWakelock.release();
                continue; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_206;
            exception11;
            obj3;
            JVM INSTR monitorexit ;
            throw exception11;
            ArrayList arraylist;
            File file;
            arraylist = new ArrayList();
            file = mJournal;
            Object obj1 = mQueueLock;
            obj1;
            JVM INSTR monitorenter ;
            if(mPendingBackups.size() <= 0)
                break MISSING_BLOCK_LABEL_329;
            for(Iterator iterator = mPendingBackups.values().iterator(); iterator.hasNext(); arraylist.add((BackupRequest)iterator.next()));
            break MISSING_BLOCK_LABEL_303;
            Exception exception9;
            exception9;
            throw exception9;
            Slog.v("BackupManagerService", "clearing pending backups");
            mPendingBackups.clear();
            mJournal = null;
            obj1;
            JVM INSTR monitorexit ;
            if(arraylist.size() > 0) {
                PerformBackupTask performbackuptask = new PerformBackupTask(ibackuptransport, arraylist, file);
                sendMessage(obtainMessage(20, performbackuptask));
            } else {
                Slog.v("BackupManagerService", "Backup requested but nothing pending");
                synchronized(mQueueLock) {
                    mBackupRunning = false;
                }
                mWakelock.release();
            }
            continue; /* Loop/switch isn't completed */
            exception10;
            obj2;
            JVM INSTR monitorexit ;
            throw exception10;
_L12:
            try {
                ((BackupRestoreTask)message.obj).execute();
            }
            catch(ClassCastException classcastexception1) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Invalid backup task in flight, obj=").append(message.obj).toString());
            }
            continue; /* Loop/switch isn't completed */
_L13:
            try {
                ((BackupRestoreTask)message.obj).operationComplete();
            }
            catch(ClassCastException classcastexception) {
                Slog.e("BackupManagerService", (new StringBuilder()).append("Invalid completion in flight, obj=").append(message.obj).toString());
            }
            continue; /* Loop/switch isn't completed */
_L3:
            FullBackupParams fullbackupparams = (FullBackupParams)message.obj;
            (new Thread(new PerformFullBackupTask(((FullParams) (fullbackupparams)).fd, ((FullParams) (fullbackupparams)).observer, fullbackupparams.includeApks, fullbackupparams.includeShared, ((FullParams) (fullbackupparams)).curPassword, ((FullParams) (fullbackupparams)).encryptPassword, fullbackupparams.allApps, fullbackupparams.includeSystem, fullbackupparams.packages, ((FullParams) (fullbackupparams)).latch))).start();
            continue; /* Loop/switch isn't completed */
_L4:
            RestoreParams restoreparams = (RestoreParams)message.obj;
            Slog.d("BackupManagerService", (new StringBuilder()).append("MSG_RUN_RESTORE observer=").append(restoreparams.observer).toString());
            sendMessage(obtainMessage(20, new PerformRestoreTask(restoreparams.transport, restoreparams.observer, restoreparams.token, restoreparams.pkgInfo, restoreparams.pmToken, restoreparams.needFullBackup, restoreparams.filterSet)));
            continue; /* Loop/switch isn't completed */
_L11:
            FullRestoreParams fullrestoreparams = (FullRestoreParams)message.obj;
            (new Thread(new PerformFullRestoreTask(((FullParams) (fullrestoreparams)).fd, ((FullParams) (fullrestoreparams)).curPassword, ((FullParams) (fullrestoreparams)).encryptPassword, ((FullParams) (fullrestoreparams)).observer, ((FullParams) (fullrestoreparams)).latch))).start();
            continue; /* Loop/switch isn't completed */
_L5:
            ClearParams clearparams = (ClearParams)message.obj;
            (new PerformClearTask(clearparams.transport, clearparams.packageInfo)).run();
            continue; /* Loop/switch isn't completed */
_L6:
            HashSet hashset;
            synchronized(mQueueLock) {
                hashset = new HashSet(mPendingInits);
                mPendingInits.clear();
            }
            (new PerformInitializeTask(hashset)).run();
            continue; /* Loop/switch isn't completed */
            exception8;
            obj;
            JVM INSTR monitorexit ;
            throw exception8;
_L7:
            RestoreSet arestoreset[];
            RestoreGetSetsParams restoregetsetsparams;
            arestoreset = null;
            restoregetsetsparams = (RestoreGetSetsParams)message.obj;
            arestoreset = restoregetsetsparams.transport.getAvailableRestoreSets();
            synchronized(restoregetsetsparams.session) {
                restoregetsetsparams.session.mRestoreSets = arestoreset;
            }
            if(arestoreset != null)
                break MISSING_BLOCK_LABEL_929;
            EventLog.writeEvent(2831, new Object[0]);
            Exception exception4;
            if(restoregetsetsparams.observer != null)
                try {
                    restoregetsetsparams.observer.restoreSetsAvailable(arestoreset);
                }
                catch(RemoteException remoteexception3) {
                    Slog.e("BackupManagerService", "Unable to report listing to observer");
                }
                catch(Exception exception7) {
                    Slog.e("BackupManagerService", "Restore observer threw", exception7);
                }
            removeMessages(8);
            sendEmptyMessageDelayed(8, 60000L);
            mWakelock.release();
            continue; /* Loop/switch isn't completed */
            exception6;
            activerestoresession1;
            JVM INSTR monitorexit ;
            try {
                throw exception6;
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception4) { }
            finally {
                if(restoregetsetsparams.observer == null) goto _L0; else goto _L0
            }
            Slog.e("BackupManagerService", "Error from transport getting set list");
            if(restoregetsetsparams.observer != null)
                try {
                    restoregetsetsparams.observer.restoreSetsAvailable(arestoreset);
                }
                catch(RemoteException remoteexception2) {
                    Slog.e("BackupManagerService", "Unable to report listing to observer");
                }
                catch(Exception exception5) {
                    Slog.e("BackupManagerService", "Restore observer threw", exception5);
                }
            removeMessages(8);
            sendEmptyMessageDelayed(8, 60000L);
            Exception exception2;
            mWakelock.release();
            if(false) {
                try {
                    restoregetsetsparams.observer.restoreSetsAvailable(arestoreset);
                }
                catch(RemoteException remoteexception1) {
                    Slog.e("BackupManagerService", "Unable to report listing to observer");
                }
                catch(Exception exception3) {
                    Slog.e("BackupManagerService", "Restore observer threw", exception3);
                }
                removeMessages(8);
                sendEmptyMessageDelayed(8, 60000L);
                mWakelock.release();
                throw exception2;
            }
            continue; /* Loop/switch isn't completed */
_L8:
            handleTimeout(message.arg1, message.obj);
            if(true) goto _L1; else goto _L9
_L9:
            synchronized(BackupManagerService.this) {
                if(mActiveRestoreSession != null) {
                    Slog.w("BackupManagerService", "Restore session timed out; aborting");
                    ActiveRestoreSession activerestoresession = mActiveRestoreSession;
                    activerestoresession.getClass();
                    post(activerestoresession. BackupManagerService.this. new ActiveRestoreSession.EndRestoreRunnable(mActiveRestoreSession));
                }
            }
_L10:
            SparseArray sparsearray = mFullConfirmations;
            sparsearray;
            JVM INSTR monitorenter ;
            FullParams fullparams;
            IFullBackupRestoreObserver ifullbackuprestoreobserver;
            fullparams = (FullParams)mFullConfirmations.get(message.arg1);
            if(fullparams == null)
                break MISSING_BLOCK_LABEL_1382;
            Slog.i("BackupManagerService", "Full backup/restore timed out waiting for user confirmation");
            signalFullBackupRestoreCompletion(fullparams);
            mFullConfirmations.delete(message.arg1);
            ifullbackuprestoreobserver = fullparams.observer;
            Exception exception;
            if(ifullbackuprestoreobserver != null)
                try {
                    fullparams.observer.onTimeout();
                }
                catch(RemoteException remoteexception) { }
                finally {
                    sparsearray;
                }
            sparsearray;
            JVM INSTR monitorexit ;
            if(true) goto _L1; else goto _L14
_L14:
            JVM INSTR monitorexit ;
            throw exception;
            exception1;
            backupmanagerservice;
            JVM INSTR monitorexit ;
            throw exception1;
            Slog.d("BackupManagerService", (new StringBuilder()).append("couldn't find params for token ").append(message.arg1).toString());
            break MISSING_BLOCK_LABEL_1364;
        }

        final BackupManagerService this$0;

        public BackupHandler(Looper looper) {
            this$0 = BackupManagerService.this;
            super(looper);
        }
    }

    class Operation {

        public BackupRestoreTask callback;
        public int state;
        final BackupManagerService this$0;

        Operation(int i, BackupRestoreTask backuprestoretask) {
            this$0 = BackupManagerService.this;
            super();
            state = i;
            callback = backuprestoretask;
        }
    }

    class FullRestoreParams extends FullParams {

        final BackupManagerService this$0;

        FullRestoreParams(ParcelFileDescriptor parcelfiledescriptor) {
            this$0 = BackupManagerService.this;
            super();
            super.fd = parcelfiledescriptor;
        }
    }

    class FullBackupParams extends FullParams {

        public boolean allApps;
        public boolean includeApks;
        public boolean includeShared;
        public boolean includeSystem;
        public String packages[];
        final BackupManagerService this$0;

        FullBackupParams(ParcelFileDescriptor parcelfiledescriptor, boolean flag, boolean flag1, boolean flag2, boolean flag3, String as[]) {
            this$0 = BackupManagerService.this;
            super();
            super.fd = parcelfiledescriptor;
            includeApks = flag;
            includeShared = flag1;
            allApps = flag2;
            includeSystem = flag3;
            packages = as;
        }
    }

    class FullParams {

        public String curPassword;
        public String encryptPassword;
        public ParcelFileDescriptor fd;
        public final AtomicBoolean latch = new AtomicBoolean(false);
        public IFullBackupRestoreObserver observer;
        final BackupManagerService this$0;

        FullParams() {
            this$0 = BackupManagerService.this;
            super();
        }
    }

    class ClearParams {

        public PackageInfo packageInfo;
        final BackupManagerService this$0;
        public IBackupTransport transport;

        ClearParams(IBackupTransport ibackuptransport, PackageInfo packageinfo) {
            this$0 = BackupManagerService.this;
            super();
            transport = ibackuptransport;
            packageInfo = packageinfo;
        }
    }

    class RestoreParams {

        public String filterSet[];
        public boolean needFullBackup;
        public IRestoreObserver observer;
        public PackageInfo pkgInfo;
        public int pmToken;
        final BackupManagerService this$0;
        public long token;
        public IBackupTransport transport;

        RestoreParams(IBackupTransport ibackuptransport, IRestoreObserver irestoreobserver, long l, PackageInfo packageinfo, int i, 
                boolean flag) {
            this$0 = BackupManagerService.this;
            super();
            transport = ibackuptransport;
            observer = irestoreobserver;
            token = l;
            pkgInfo = packageinfo;
            pmToken = i;
            needFullBackup = flag;
            filterSet = null;
        }

        RestoreParams(IBackupTransport ibackuptransport, IRestoreObserver irestoreobserver, long l, boolean flag) {
            this$0 = BackupManagerService.this;
            super();
            transport = ibackuptransport;
            observer = irestoreobserver;
            token = l;
            pkgInfo = null;
            pmToken = 0;
            needFullBackup = flag;
            filterSet = null;
        }

        RestoreParams(IBackupTransport ibackuptransport, IRestoreObserver irestoreobserver, long l, String as[], boolean flag) {
            this$0 = BackupManagerService.this;
            super();
            transport = ibackuptransport;
            observer = irestoreobserver;
            token = l;
            pkgInfo = null;
            pmToken = 0;
            needFullBackup = flag;
            filterSet = as;
        }
    }

    class RestoreGetSetsParams {

        public IRestoreObserver observer;
        public ActiveRestoreSession session;
        final BackupManagerService this$0;
        public IBackupTransport transport;

        RestoreGetSetsParams(IBackupTransport ibackuptransport, ActiveRestoreSession activerestoresession, IRestoreObserver irestoreobserver) {
            this$0 = BackupManagerService.this;
            super();
            transport = ibackuptransport;
            session = activerestoresession;
            observer = irestoreobserver;
        }
    }

    class ProvisionedObserver extends ContentObserver {

        public void onChange(boolean flag) {
            boolean flag1 = mProvisioned;
            boolean flag2 = deviceIsProvisioned();
            BackupManagerService backupmanagerservice = BackupManagerService.this;
            boolean flag3;
            Object obj;
            if(flag1 || flag2)
                flag3 = true;
            else
                flag3 = false;
            backupmanagerservice.mProvisioned = flag3;
            obj = mQueueLock;
            obj;
            JVM INSTR monitorenter ;
            if(mProvisioned && !flag1 && mEnabled)
                startBackupAlarmsLocked(0x2932e00L);
            return;
        }

        final BackupManagerService this$0;

        public ProvisionedObserver(Handler handler) {
            this$0 = BackupManagerService.this;
            super(handler);
        }
    }

    class BackupRequest {

        public String toString() {
            return (new StringBuilder()).append("BackupRequest{pkg=").append(packageName).append("}").toString();
        }

        public String packageName;
        final BackupManagerService this$0;

        BackupRequest(String s) {
            this$0 = BackupManagerService.this;
            super();
            packageName = s;
        }
    }


    public BackupManagerService(Context context) {
        ApplicationInfo applicationinfo;
        FileInputStream fileinputstream;
        DataInputStream datainputstream;
        FileInputStream fileinputstream1;
        DataInputStream datainputstream1;
        mBackupParticipants = new SparseArray();
        mPendingBackups = new HashMap();
        mQueueLock = new Object();
        mAgentConnectLock = new Object();
        mBackupTrace = new ArrayList();
        mClearDataLock = new Object();
        mTransports = new HashMap();
        mCurrentOperations = new SparseArray();
        mCurrentOpLock = new Object();
        mTokenGenerator = new Random();
        mFullConfirmations = new SparseArray();
        mRng = new SecureRandom();
        mEverStoredApps = new HashSet();
        mAncestralPackages = null;
        mAncestralToken = 0L;
        mCurrentToken = 0L;
        mPendingInits = new HashSet();
        BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent2) {
                String s2;
                boolean flag3;
                boolean flag4;
                Bundle bundle;
                String as[];
                Slog.d("BackupManagerService", (new StringBuilder()).append("Received broadcast ").append(intent2).toString());
                s2 = intent2.getAction();
                flag3 = false;
                flag4 = false;
                bundle = intent2.getExtras();
                as = null;
                if(!"android.intent.action.PACKAGE_ADDED".equals(s2) && !"android.intent.action.PACKAGE_REMOVED".equals(s2)) goto _L2; else goto _L1
_L1:
                Uri uri = intent2.getData();
                if(uri != null) goto _L4; else goto _L3
_L3:
                return;
_L4:
                String s3 = uri.getSchemeSpecificPart();
                if(s3 != null) {
                    as = new String[1];
                    as[0] = s3;
                }
                flag4 = "android.intent.action.PACKAGE_ADDED".equals(s2);
                flag3 = bundle.getBoolean("android.intent.extra.REPLACING", false);
_L6:
                int i;
                if(as == null || as.length == 0)
                    continue; /* Loop/switch isn't completed */
                i = bundle.getInt("android.intent.extra.UID");
                if(!flag4)
                    break; /* Loop/switch isn't completed */
                SparseArray sparsearray2 = mBackupParticipants;
                sparsearray2;
                JVM INSTR monitorenter ;
                if(flag3)
                    removePackageParticipantsLocked(as, i);
                addPackageParticipantsLocked(as);
                continue; /* Loop/switch isn't completed */
_L2:
                if("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(s2)) {
                    flag4 = true;
                    as = intent2.getStringArrayExtra("android.intent.extra.changed_package_list");
                } else
                if("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(s2)) {
                    flag4 = false;
                    as = intent2.getStringArrayExtra("android.intent.extra.changed_package_list");
                }
                if(true) goto _L6; else goto _L5
_L5:
                if(flag3)
                    continue; /* Loop/switch isn't completed */
                SparseArray sparsearray1 = mBackupParticipants;
                sparsearray1;
                JVM INSTR monitorenter ;
                removePackageParticipantsLocked(as, i);
                if(true) goto _L3; else goto _L7
_L7:
            }

            final BackupManagerService this$0;

             {
                this$0 = BackupManagerService.this;
                super();
            }
        };
        mBroadcastReceiver = broadcastreceiver;
        ServiceConnection serviceconnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName componentname1, IBinder ibinder) {
                Slog.v("BackupManagerService", "Connected to Google transport");
                mGoogleTransport = com.android.internal.backup.IBackupTransport.Stub.asInterface(ibinder);
                registerTransport(componentname1.flattenToShortString(), mGoogleTransport);
            }

            public void onServiceDisconnected(ComponentName componentname1) {
                Slog.v("BackupManagerService", "Disconnected from Google transport");
                mGoogleTransport = null;
                registerTransport(componentname1.flattenToShortString(), null);
            }

            final BackupManagerService this$0;

             {
                this$0 = BackupManagerService.this;
                super();
            }
        };
        mGoogleConnection = serviceconnection;
        mContext = context;
        mPackageManager = context.getPackageManager();
        mPackageManagerBinder = AppGlobals.getPackageManager();
        mActivityManager = ActivityManagerNative.getDefault();
        mAlarmManager = (AlarmManager)context.getSystemService("alarm");
        mPowerManager = (PowerManager)context.getSystemService("power");
        mMountService = android.os.storage.IMountService.Stub.asInterface(ServiceManager.getService("mount"));
        mBackupManagerBinder = asInterface(asBinder());
        mHandlerThread = new HandlerThread("backup", 10);
        mHandlerThread.start();
        BackupHandler backuphandler = new BackupHandler(mHandlerThread.getLooper());
        mBackupHandler = backuphandler;
        ContentResolver contentresolver = context.getContentResolver();
        boolean flag;
        boolean flag1;
        boolean flag2;
        ProvisionedObserver provisionedobserver;
        RunBackupReceiver runbackupreceiver;
        IntentFilter intentfilter;
        RunInitializeReceiver runinitializereceiver;
        IntentFilter intentfilter1;
        Intent intent;
        Intent intent1;
        LocalTransport localtransport;
        ComponentName componentname;
        BufferedInputStream bufferedinputstream;
        byte abyte0[];
        if(android.provider.Settings.Secure.getInt(contentresolver, "backup_enabled", 0) != 0)
            flag = true;
        else
            flag = false;
        if(android.provider.Settings.Secure.getInt(contentresolver, "device_provisioned", 0) != 0)
            flag1 = true;
        else
            flag1 = false;
        mProvisioned = flag1;
        if(android.provider.Settings.Secure.getInt(contentresolver, "backup_auto_restore", 1) != 0)
            flag2 = true;
        else
            flag2 = false;
        mAutoRestore = flag2;
        provisionedobserver = new ProvisionedObserver(mBackupHandler);
        mProvisionedObserver = provisionedobserver;
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("device_provisioned"), false, mProvisionedObserver);
        mBaseStateDir = new File(Environment.getSecureDataDirectory(), "backup");
        mBaseStateDir.mkdirs();
        mDataDir = Environment.getDownloadCacheDirectory();
        mPasswordHashFile = new File(mBaseStateDir, "pwhash");
        if(!mPasswordHashFile.exists())
            break MISSING_BLOCK_LABEL_577;
        fileinputstream = null;
        datainputstream = null;
        fileinputstream1 = new FileInputStream(mPasswordHashFile);
        bufferedinputstream = new BufferedInputStream(fileinputstream1);
        datainputstream1 = new DataInputStream(bufferedinputstream);
        abyte0 = new byte[datainputstream1.readInt()];
        datainputstream1.readFully(abyte0);
        mPasswordHash = datainputstream1.readUTF();
        mPasswordSalt = abyte0;
        if(datainputstream1 == null)
            break MISSING_BLOCK_LABEL_567;
        datainputstream1.close();
        if(fileinputstream1 != null)
            fileinputstream1.close();
_L3:
        runbackupreceiver = new RunBackupReceiver();
        mRunBackupReceiver = runbackupreceiver;
        intentfilter = new IntentFilter();
        intentfilter.addAction("android.app.backup.intent.RUN");
        context.registerReceiver(mRunBackupReceiver, intentfilter, "android.permission.BACKUP", null);
        runinitializereceiver = new RunInitializeReceiver();
        mRunInitReceiver = runinitializereceiver;
        intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.app.backup.intent.INIT");
        context.registerReceiver(mRunInitReceiver, intentfilter1, "android.permission.BACKUP", null);
        intent = new Intent("android.app.backup.intent.RUN");
        intent.addFlags(0x40000000);
        mRunBackupIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        intent1 = new Intent("android.app.backup.intent.INIT");
        intent.addFlags(0x40000000);
        mRunInitIntent = PendingIntent.getBroadcast(context, 5, intent1, 0);
        mJournalDir = new File(mBaseStateDir, "pending");
        mJournalDir.mkdirs();
        mJournal = null;
        initPackageTracking();
        synchronized(mBackupParticipants) {
            addPackageParticipantsLocked(null);
        }
        localtransport = new LocalTransport(context);
        mLocalTransport = localtransport;
        registerTransport((new ComponentName(context, com/android/internal/backup/LocalTransport)).flattenToShortString(), mLocalTransport);
        mGoogleTransport = null;
        mCurrentTransport = android.provider.Settings.Secure.getString(context.getContentResolver(), "backup_transport");
        if("".equals(mCurrentTransport))
            mCurrentTransport = null;
        Slog.v("BackupManagerService", (new StringBuilder()).append("Starting with transport ").append(mCurrentTransport).toString());
        componentname = new ComponentName("com.google.android.backup", "com.google.android.backup.BackupTransportService");
        applicationinfo = mPackageManager.getApplicationInfo(componentname.getPackageName(), 0);
        if((1 & applicationinfo.flags) == 0) goto _L2; else goto _L1
_L1:
        Slog.v("BackupManagerService", "Binding to Google transport");
        context.bindService((new Intent()).setComponent(componentname), mGoogleConnection, 1);
_L4:
        parseLeftoverJournals();
        mWakelock = mPowerManager.newWakeLock(1, "*backup*");
        setBackupEnabled(flag);
        return;
        IOException ioexception5;
        ioexception5;
_L8:
        Slog.e("BackupManagerService", "Unable to read saved backup pw hash");
        if(datainputstream == null)
            break MISSING_BLOCK_LABEL_1029;
        datainputstream.close();
        if(fileinputstream != null)
            fileinputstream.close();
          goto _L3
        IOException ioexception2;
        ioexception2;
        String s;
        String s1;
        s = "BackupManagerService";
        s1 = "Unable to close streams";
_L6:
        Slog.w(s, s1);
          goto _L3
        Exception exception1;
        exception1;
_L7:
        if(datainputstream == null)
            break MISSING_BLOCK_LABEL_1076;
        datainputstream.close();
        if(fileinputstream != null)
            fileinputstream.close();
_L5:
        throw exception1;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        try {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Possible Google transport spoof: ignoring ").append(applicationinfo).toString());
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            Slog.v("BackupManagerService", "Google transport not present");
        }
          goto _L4
        IOException ioexception1;
        ioexception1;
        Slog.w("BackupManagerService", "Unable to close streams");
          goto _L5
        IOException ioexception3;
        ioexception3;
        s = "BackupManagerService";
        s1 = "Unable to close streams";
          goto _L6
        exception1;
        fileinputstream = fileinputstream1;
          goto _L7
        exception1;
        datainputstream = datainputstream1;
        fileinputstream = fileinputstream1;
          goto _L7
        IOException ioexception4;
        ioexception4;
        fileinputstream = fileinputstream1;
          goto _L8
        IOException ioexception;
        ioexception;
        datainputstream = datainputstream1;
        fileinputstream = fileinputstream1;
          goto _L8
    }

    private void addPackageParticipantsLockedInner(String s, List list) {
        Iterator iterator = list.iterator();
        do {
            if(!iterator.hasNext())
                break;
            PackageInfo packageinfo = (PackageInfo)iterator.next();
            if(s == null || packageinfo.packageName.equals(s)) {
                int i = packageinfo.applicationInfo.uid;
                HashSet hashset = (HashSet)mBackupParticipants.get(i);
                if(hashset == null) {
                    hashset = new HashSet();
                    mBackupParticipants.put(i, hashset);
                }
                hashset.add(packageinfo.packageName);
                if(!mEverStoredApps.contains(packageinfo.packageName)) {
                    Slog.i("BackupManagerService", (new StringBuilder()).append("New app ").append(packageinfo.packageName).append(" never backed up; scheduling").toString());
                    dataChangedImpl(packageinfo.packageName);
                }
            }
        } while(true);
    }

    private SecretKey buildCharArrayKey(char ac[], byte abyte0[], int i) {
        SecretKey secretkey1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(ac, abyte0, i, 256));
        SecretKey secretkey = secretkey1;
_L2:
        return secretkey;
        InvalidKeySpecException invalidkeyspecexception;
        invalidkeyspecexception;
        Slog.e("BackupManagerService", "Invalid key spec for PBKDF2!");
_L3:
        secretkey = null;
        if(true) goto _L2; else goto _L1
_L1:
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        Slog.e("BackupManagerService", "PBKDF2 unavailable!");
          goto _L3
    }

    private String buildPasswordHash(String s, byte abyte0[], int i) {
        SecretKey secretkey = buildPasswordKey(s, abyte0, i);
        String s1;
        if(secretkey != null)
            s1 = byteArrayToHex(secretkey.getEncoded());
        else
            s1 = null;
        return s1;
    }

    private SecretKey buildPasswordKey(String s, byte abyte0[], int i) {
        return buildCharArrayKey(s.toCharArray(), abyte0, i);
    }

    private String byteArrayToHex(byte abyte0[]) {
        StringBuilder stringbuilder = new StringBuilder(2 * abyte0.length);
        for(int i = 0; i < abyte0.length; i++)
            stringbuilder.append(Byte.toHexString(abyte0[i], true));

        return stringbuilder.toString();
    }

    private void dataChangedImpl(String s) {
        dataChangedImpl(s, dataChangedTargets(s));
    }

    private void dataChangedImpl(String s, HashSet hashset) {
        EventLog.writeEvent(2820, s);
        if(hashset != null) goto _L2; else goto _L1
_L1:
        Slog.w("BackupManagerService", (new StringBuilder()).append("dataChanged but no participant pkg='").append(s).append("'").append(" uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        Object obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        if(hashset.contains(s)) {
            BackupRequest backuprequest = new BackupRequest(s);
            if(mPendingBackups.put(s, backuprequest) == null) {
                Slog.d("BackupManagerService", (new StringBuilder()).append("Now staging backup of ").append(s).toString());
                writeToJournalLocked(s);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private HashSet dataChangedTargets(String s) {
        if(mContext.checkPermission("android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid()) != -1) goto _L2; else goto _L1
_L1:
        SparseArray sparsearray1 = mBackupParticipants;
        sparsearray1;
        JVM INSTR monitorenter ;
        HashSet hashset1 = (HashSet)mBackupParticipants.get(Binder.getCallingUid());
          goto _L3
_L2:
        HashSet hashset = new HashSet();
        SparseArray sparsearray = mBackupParticipants;
        sparsearray;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = mBackupParticipants.size();
        j = 0;
_L5:
        if(j < i) {
            HashSet hashset2 = (HashSet)mBackupParticipants.valueAt(j);
            if(hashset2 != null)
                hashset.addAll(hashset2);
            j++;
            continue; /* Loop/switch isn't completed */
        }
        hashset1 = hashset;
_L3:
        return hashset1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void dumpInternal(PrintWriter printwriter) {
        Object obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        StringBuilder stringbuilder = (new StringBuilder()).append("Backup Manager is ");
        if(!mEnabled) goto _L2; else goto _L1
_L1:
        String s = "enabled";
_L14:
        StringBuilder stringbuilder1 = stringbuilder.append(s).append(" / ");
        if(mProvisioned) goto _L4; else goto _L3
_L3:
        String s1 = "not ";
_L15:
        StringBuilder stringbuilder2 = stringbuilder1.append(s1).append("provisioned / ");
        if(mPendingInits.size() != 0) goto _L6; else goto _L5
_L5:
        String s2 = "not ";
_L16:
        StringBuilder stringbuilder3;
        printwriter.println(stringbuilder2.append(s2).append("pending init").toString());
        stringbuilder3 = (new StringBuilder()).append("Auto-restore is ");
        if(!mAutoRestore) goto _L8; else goto _L7
_L7:
        String s3 = "enabled";
_L17:
        String as[];
        int i;
        int j;
        printwriter.println(stringbuilder3.append(s3).toString());
        if(mBackupRunning)
            printwriter.println("Backup currently running");
        printwriter.println((new StringBuilder()).append("Last backup pass started: ").append(mLastBackupPass).append(" (now = ").append(System.currentTimeMillis()).append(')').toString());
        printwriter.println((new StringBuilder()).append("  next scheduled: ").append(mNextBackupPass).toString());
        printwriter.println("Available transports:");
        as = listAllTransports();
        i = as.length;
        j = 0;
_L19:
        if(j >= i) goto _L10; else goto _L9
_L9:
        String s9;
        StringBuilder stringbuilder5;
        s9 = as[j];
        stringbuilder5 = new StringBuilder();
        if(!s9.equals(mCurrentTransport)) goto _L12; else goto _L11
_L11:
        String s10 = "  * ";
_L18:
        printwriter.println(stringbuilder5.append(s10).append(s9).toString());
        File afile[];
        int j1;
        int k1;
        IBackupTransport ibackuptransport = getTransport(s9);
        File file = new File(mBaseStateDir, ibackuptransport.transportDirName());
        printwriter.println((new StringBuilder()).append("       destination: ").append(ibackuptransport.currentDestinationString()).toString());
        printwriter.println((new StringBuilder()).append("       intent: ").append(ibackuptransport.configurationIntent()).toString());
        afile = file.listFiles();
        j1 = afile.length;
        k1 = 0;
_L13:
        if(k1 >= j1)
            break MISSING_BLOCK_LABEL_1192;
        File file1 = afile[k1];
        printwriter.println((new StringBuilder()).append("       ").append(file1.getName()).append(" - ").append(file1.length()).append(" state bytes").toString());
        k1++;
          goto _L13
_L2:
        s = "disabled";
          goto _L14
        Exception exception2;
        exception2;
        Slog.e("BackupManagerService", "Error in transport", exception2);
        printwriter.println((new StringBuilder()).append("        Error: ").append(exception2).toString());
        break MISSING_BLOCK_LABEL_1192;
_L10:
        printwriter.println((new StringBuilder()).append("Pending init: ").append(mPendingInits.size()).toString());
        String s8;
        for(Iterator iterator = mPendingInits.iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s8).toString()))
            s8 = (String)iterator.next();

        break MISSING_BLOCK_LABEL_631;
        Exception exception;
        exception;
        throw exception;
        List list = mBackupTrace;
        list;
        JVM INSTR monitorenter ;
        if(!mBackupTrace.isEmpty()) {
            printwriter.println("Most recent backup trace:");
            String s7;
            for(Iterator iterator5 = mBackupTrace.iterator(); iterator5.hasNext(); printwriter.println((new StringBuilder()).append("   ").append(s7).toString()))
                s7 = (String)iterator5.next();

        }
        break MISSING_BLOCK_LABEL_728;
        Exception exception1;
        exception1;
        throw exception1;
        list;
        JVM INSTR monitorexit ;
        int k;
        k = mBackupParticipants.size();
        printwriter.println("Participants:");
        StringBuilder stringbuilder4;
        Object obj1;
        Iterator iterator1;
        Iterator iterator2;
        BackupRequest backuprequest;
        String s4;
        Iterator iterator3;
        String s5;
        for(int l = 0; l < k; l++) {
            int i1 = mBackupParticipants.keyAt(l);
            printwriter.print("  uid: ");
            printwriter.println(i1);
            String s6;
            for(Iterator iterator4 = ((HashSet)mBackupParticipants.valueAt(l)).iterator(); iterator4.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s6).toString()))
                s6 = (String)iterator4.next();

            break MISSING_BLOCK_LABEL_1198;
        }

        stringbuilder4 = (new StringBuilder()).append("Ancestral packages: ");
        if(mAncestralPackages == null)
            obj1 = "none";
        else
            obj1 = Integer.valueOf(mAncestralPackages.size());
        printwriter.println(stringbuilder4.append(obj1).toString());
        if(mAncestralPackages != null)
            for(iterator3 = mAncestralPackages.iterator(); iterator3.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s5).toString()))
                s5 = (String)iterator3.next();

        printwriter.println((new StringBuilder()).append("Ever backed up: ").append(mEverStoredApps.size()).toString());
        for(iterator1 = mEverStoredApps.iterator(); iterator1.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s4).toString()))
            s4 = (String)iterator1.next();

        printwriter.println((new StringBuilder()).append("Pending backup: ").append(mPendingBackups.size()).toString());
        for(iterator2 = mPendingBackups.values().iterator(); iterator2.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(backuprequest).toString()))
            backuprequest = (BackupRequest)iterator2.next();

        obj;
        JVM INSTR monitorexit ;
        return;
_L4:
        s1 = "";
          goto _L15
_L6:
        s2 = "";
          goto _L16
_L8:
        s3 = "disabled";
          goto _L17
_L12:
        s10 = "    ";
          goto _L18
        j++;
          goto _L19
    }

    private IBackupTransport getTransport(String s) {
        HashMap hashmap = mTransports;
        hashmap;
        JVM INSTR monitorenter ;
        IBackupTransport ibackuptransport = (IBackupTransport)mTransports.get(s);
        if(ibackuptransport == null)
            Slog.w("BackupManagerService", (new StringBuilder()).append("Requested unavailable transport: ").append(s).toString());
        return ibackuptransport;
    }

    private byte[] hexToByteArray(String s) {
        int i = s.length() / 2;
        if(i * 2 != s.length())
            throw new IllegalArgumentException("Hex string must have an even number of digits");
        byte abyte0[] = new byte[i];
        for(int j = 0; j < s.length(); j += 2)
            abyte0[j / 2] = (byte)Integer.parseInt(s.substring(j, j + 2), 16);

        return abyte0;
    }

    private void initPackageTracking() {
        File file;
        RandomAccessFile randomaccessfile1;
        RandomAccessFile randomaccessfile2;
        RandomAccessFile randomaccessfile3;
        RandomAccessFile randomaccessfile4;
        Slog.v("BackupManagerService", "Initializing package tracking");
        mTokenFile = new File(mBaseStateDir, "ancestral");
        String s;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        try {
            RandomAccessFile randomaccessfile = new RandomAccessFile(mTokenFile, "r");
            if(randomaccessfile.readInt() == 1) {
                mAncestralToken = randomaccessfile.readLong();
                mCurrentToken = randomaccessfile.readLong();
                int i = randomaccessfile.readInt();
                if(i >= 0) {
                    mAncestralPackages = new HashSet();
                    for(int j = 0; j < i; j++) {
                        String s1 = randomaccessfile.readUTF();
                        mAncestralPackages.add(s1);
                    }

                }
            }
            randomaccessfile.close();
        }
        catch(FileNotFoundException filenotfoundexception) {
            Slog.v("BackupManagerService", "No ancestral data");
        }
        catch(IOException ioexception) {
            Slog.w("BackupManagerService", "Unable to read token file", ioexception);
        }
        mEverStored = new File(mBaseStateDir, "processed");
        file = new File(mBaseStateDir, "processed.new");
        if(file.exists())
            file.delete();
        if(!mEverStored.exists()) goto _L2; else goto _L1
_L1:
        randomaccessfile1 = null;
        randomaccessfile2 = null;
        randomaccessfile3 = new RandomAccessFile(file, "rws");
        randomaccessfile4 = new RandomAccessFile(mEverStored, "r");
_L4:
        s = randomaccessfile4.readUTF();
        try {
            mPackageManager.getPackageInfo(s, 0);
            mEverStoredApps.add(s);
            randomaccessfile3.writeUTF(s);
        }
        // Misplaced declaration of an exception variable
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) { }
        if(true) goto _L4; else goto _L3
_L3:
        EOFException eofexception2;
        eofexception2;
_L10:
        if(!file.renameTo(mEverStored))
            Slog.e("BackupManagerService", (new StringBuilder()).append("Error renaming ").append(file).append(" to ").append(mEverStored).toString());
        Exception exception;
        IOException ioexception5;
        IntentFilter intentfilter;
        IntentFilter intentfilter1;
        IOException ioexception1;
        IOException ioexception2;
        if(randomaccessfile1 != null)
            try {
                randomaccessfile1.close();
            }
            catch(IOException ioexception4) { }
        if(randomaccessfile2 != null)
            try {
                randomaccessfile2.close();
            }
            catch(IOException ioexception3) { }
_L2:
        intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter.addDataScheme("package");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter);
        intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        intentfilter1.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter1);
        return;
        ioexception5;
_L8:
        Slog.e("BackupManagerService", "Error in processed file", ioexception5);
        if(randomaccessfile1 != null)
            try {
                randomaccessfile1.close();
            }
            catch(IOException ioexception6) { }
        if(randomaccessfile2 == null) goto _L2; else goto _L5
_L5:
        randomaccessfile2.close();
          goto _L2
        exception;
_L7:
        if(randomaccessfile1 != null)
            try {
                randomaccessfile1.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception2) { }
        if(randomaccessfile2 != null)
            try {
                randomaccessfile2.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        throw exception;
        exception;
        randomaccessfile1 = randomaccessfile3;
        continue; /* Loop/switch isn't completed */
        exception;
        randomaccessfile2 = randomaccessfile4;
        randomaccessfile1 = randomaccessfile3;
        if(true) goto _L7; else goto _L6
_L6:
        ioexception5;
        randomaccessfile1 = randomaccessfile3;
          goto _L8
        ioexception5;
        randomaccessfile2 = randomaccessfile4;
        randomaccessfile1 = randomaccessfile3;
          goto _L8
        EOFException eofexception1;
        eofexception1;
        randomaccessfile1 = randomaccessfile3;
        continue; /* Loop/switch isn't completed */
        EOFException eofexception;
        eofexception;
        randomaccessfile2 = randomaccessfile4;
        randomaccessfile1 = randomaccessfile3;
        if(true) goto _L10; else goto _L9
_L9:
    }

    private byte[] makeKeyChecksum(byte abyte0[], byte abyte1[], int i) {
        char ac[] = new char[abyte0.length];
        for(int j = 0; j < abyte0.length; j++)
            ac[j] = (char)abyte0[j];

        return buildCharArrayKey(ac, abyte1, i).getEncoded();
    }

    private void parseLeftoverJournals() {
        File afile[];
        int i;
        int j;
        afile = mJournalDir.listFiles();
        i = afile.length;
        j = 0;
_L5:
        if(j >= i) goto _L2; else goto _L1
_L1:
        File file = afile[j];
        if(mJournal != null && file.compareTo(mJournal) == 0) goto _L4; else goto _L3
_L3:
        RandomAccessFile randomaccessfile = null;
        RandomAccessFile randomaccessfile1;
        Slog.i("BackupManagerService", "Found stale backup journal, scheduling");
        randomaccessfile1 = new RandomAccessFile(file, "r");
        do {
            String s = randomaccessfile1.readUTF();
            Slog.i("BackupManagerService", (new StringBuilder()).append("  ").append(s).toString());
            dataChangedImpl(s);
        } while(true);
        EOFException eofexception1;
        eofexception1;
        randomaccessfile = randomaccessfile1;
_L10:
        Exception exception;
        Exception exception1;
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception) { }
_L6:
        file.delete();
_L4:
        j++;
          goto _L5
        exception1;
_L9:
        Slog.e("BackupManagerService", (new StringBuilder()).append("Can't read ").append(file).toString(), exception1);
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception2) { }
          goto _L6
        exception;
_L8:
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception1) { }
        file.delete();
        throw exception;
_L2:
        return;
        exception;
        randomaccessfile = randomaccessfile1;
        if(true) goto _L8; else goto _L7
_L7:
        exception1;
        randomaccessfile = randomaccessfile1;
          goto _L9
        EOFException eofexception;
        eofexception;
          goto _L10
    }

    private byte[] randomBytes(int i) {
        byte abyte0[] = new byte[i / 8];
        mRng.nextBytes(abyte0);
        return abyte0;
    }

    private void registerTransport(String s, IBackupTransport ibackuptransport) {
        HashMap hashmap = mTransports;
        hashmap;
        JVM INSTR monitorenter ;
        Slog.v("BackupManagerService", (new StringBuilder()).append("Registering transport ").append(s).append(" = ").append(ibackuptransport).toString());
        if(ibackuptransport == null) goto _L2; else goto _L1
_L1:
        mTransports.put(s, ibackuptransport);
        String s1 = ibackuptransport.transportDirName();
        File file = new File(mBaseStateDir, s1);
        file.mkdirs();
        if((new File(file, "_need_init_")).exists())
            synchronized(mQueueLock) {
                mPendingInits.add(s1);
                mAlarmManager.set(0, 60000L + System.currentTimeMillis(), mRunInitIntent);
            }
_L4:
        return;
_L2:
        mTransports.remove(s);
        if(mCurrentTransport != null && mCurrentTransport.equals(s))
            mCurrentTransport = null;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        try {
            throw exception1;
        }
        catch(RemoteException remoteexception) { }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void removePackageFromSetLocked(HashSet hashset, String s) {
        if(hashset.contains(s)) {
            removeEverBackedUp(s);
            hashset.remove(s);
        }
    }

    private boolean signaturesMatch(Signature asignature[], PackageInfo packageinfo) {
        boolean flag;
        Signature asignature1[];
        flag = true;
        if((1 & packageinfo.applicationInfo.flags) != 0) {
            Slog.v("BackupManagerService", (new StringBuilder()).append("System app ").append(packageinfo.packageName).append(" - skipping sig check").toString());
        } else {
            asignature1 = packageinfo.signatures;
            if(asignature != null && asignature.length != 0 || asignature1 != null && asignature1.length != 0) {
label0:
                {
                    if(asignature != null && asignature1 != null)
                        break label0;
                    flag = false;
                }
            }
        }
_L2:
        return flag;
        int i;
        int j;
        int k;
        i = asignature.length;
        j = asignature1.length;
        k = 0;
_L4:
        if(k >= i) goto _L2; else goto _L1
_L1:
        boolean flag1;
        int l;
        flag1 = false;
        l = 0;
_L3:
label1:
        {
            if(l < j) {
                if(!asignature[k].equals(asignature1[l]))
                    break label1;
                flag1 = true;
            }
            if(flag1)
                break MISSING_BLOCK_LABEL_157;
            flag = false;
        }
          goto _L2
        l++;
          goto _L3
        k++;
          goto _L4
    }

    private void startBackupAlarmsLocked(long l) {
        Random random = new Random();
        long l1 = l + System.currentTimeMillis() + (long)random.nextInt(0x493e0);
        mAlarmManager.setRepeating(0, l1, 0x36ee80L + (long)random.nextInt(0x493e0), mRunBackupIntent);
        mNextBackupPass = l1;
    }

    private void writeToJournalLocked(String s) {
        RandomAccessFile randomaccessfile = null;
        RandomAccessFile randomaccessfile1;
        if(mJournal == null)
            mJournal = File.createTempFile("journal", null, mJournalDir);
        randomaccessfile1 = new RandomAccessFile(mJournal, "rws");
        randomaccessfile1.seek(randomaccessfile1.length());
        randomaccessfile1.writeUTF(s);
        if(randomaccessfile1 == null)
            break MISSING_BLOCK_LABEL_66;
        randomaccessfile1.close();
_L1:
        return;
        IOException ioexception1;
        ioexception1;
_L3:
        Slog.e("BackupManagerService", (new StringBuilder()).append("Can't write ").append(s).append(" to backup journal").toString(), ioexception1);
        mJournal = null;
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception2) { }
          goto _L1
        Exception exception;
        exception;
_L2:
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        IOException ioexception3;
        ioexception3;
          goto _L1
        exception;
        randomaccessfile = randomaccessfile1;
          goto _L2
        ioexception1;
        randomaccessfile = randomaccessfile1;
          goto _L3
    }

    public void acknowledgeFullBackupOrRestore(int i, boolean flag, String s, String s1, IFullBackupRestoreObserver ifullbackuprestoreobserver) {
        long l;
        Slog.d("BackupManagerService", (new StringBuilder()).append("acknowledgeFullBackupOrRestore : token=").append(i).append(" allow=").append(flag).toString());
        mContext.enforceCallingPermission("android.permission.BACKUP", "acknowledgeFullBackupOrRestore");
        l = Binder.clearCallingIdentity();
        SparseArray sparsearray = mFullConfirmations;
        sparsearray;
        JVM INSTR monitorenter ;
        FullParams fullparams;
        fullparams = (FullParams)mFullConfirmations.get(i);
        if(fullparams == null)
            break MISSING_BLOCK_LABEL_301;
        mBackupHandler.removeMessages(9, fullparams);
        mFullConfirmations.delete(i);
        if(!flag) goto _L2; else goto _L1
_L1:
        if(!(fullparams instanceof FullBackupParams)) goto _L4; else goto _L3
_L3:
        byte byte0 = 2;
_L7:
        fullparams.observer = ifullbackuprestoreobserver;
        fullparams.curPassword = s;
        if(mMountService.getEncryptionState() == 1) goto _L6; else goto _L5
_L5:
        boolean flag1 = true;
_L8:
        if(flag1)
            Slog.w("BackupManagerService", "Device is encrypted; forcing enc password");
_L9:
        Exception exception;
        Exception exception1;
        RemoteException remoteexception;
        Message message;
        if(!flag1)
            s = s1;
        fullparams.encryptPassword = s;
        Slog.d("BackupManagerService", (new StringBuilder()).append("Sending conf message with verb ").append(byte0).toString());
        mWakelock.acquire();
        message = mBackupHandler.obtainMessage(byte0, fullparams);
        mBackupHandler.sendMessage(message);
_L10:
        sparsearray;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
_L4:
        byte0 = 10;
          goto _L7
_L6:
        flag1 = false;
          goto _L8
        remoteexception;
        Slog.e("BackupManagerService", "Unable to contact mount service!");
        flag1 = true;
          goto _L9
_L2:
        Slog.w("BackupManagerService", "User rejected full backup/restore operation");
        signalFullBackupRestoreCompletion(fullparams);
          goto _L10
        exception1;
        throw exception1;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        Slog.w("BackupManagerService", "Attempted to ack full backup/restore with invalid token");
          goto _L10
    }

    void addBackupTrace(String s) {
        List list = mBackupTrace;
        list;
        JVM INSTR monitorenter ;
        mBackupTrace.add(s);
        return;
    }

    void addPackageParticipantsLocked(String as[]) {
        List list = allAgentPackages();
        if(as != null) {
            Slog.v("BackupManagerService", (new StringBuilder()).append("addPackageParticipantsLocked: #").append(as.length).toString());
            int i = as.length;
            for(int j = 0; j < i; j++)
                addPackageParticipantsLockedInner(as[j], list);

        } else {
            Slog.v("BackupManagerService", "addPackageParticipantsLocked: all");
            addPackageParticipantsLockedInner(null, list);
        }
    }

    public void agentConnected(String s, IBinder ibinder) {
        Object obj = mAgentConnectLock;
        obj;
        JVM INSTR monitorenter ;
        if(Binder.getCallingUid() == 1000) {
            Slog.d("BackupManagerService", (new StringBuilder()).append("agentConnected pkg=").append(s).append(" agent=").append(ibinder).toString());
            mConnectedAgent = android.app.IBackupAgent.Stub.asInterface(ibinder);
            mConnecting = false;
        } else {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Non-system process uid=").append(Binder.getCallingUid()).append(" claiming agent connected").toString());
        }
        mAgentConnectLock.notifyAll();
        return;
    }

    public void agentDisconnected(String s) {
        Object obj = mAgentConnectLock;
        obj;
        JVM INSTR monitorenter ;
        if(Binder.getCallingUid() == 1000) {
            mConnectedAgent = null;
            mConnecting = false;
        } else {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Non-system process uid=").append(Binder.getCallingUid()).append(" claiming agent disconnected").toString());
        }
        mAgentConnectLock.notifyAll();
        return;
    }

    List allAgentPackages() {
        List list = mPackageManager.getInstalledPackages(64);
        int i = -1 + list.size();
        do {
            if(i >= 0) {
                PackageInfo packageinfo = (PackageInfo)list.get(i);
                try {
                    ApplicationInfo applicationinfo = packageinfo.applicationInfo;
                    if((0x8000 & applicationinfo.flags) == 0 || applicationinfo.backupAgentName == null) {
                        list.remove(i);
                    } else {
                        ApplicationInfo applicationinfo1 = mPackageManager.getApplicationInfo(packageinfo.packageName, 1024);
                        packageinfo.applicationInfo.sharedLibraryFiles = applicationinfo1.sharedLibraryFiles;
                    }
                }
                catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                    list.remove(i);
                }
            } else {
                return list;
            }
            i--;
        } while(true);
    }

    public void backupNow() {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "backupNow");
        Slog.v("BackupManagerService", "Scheduling immediate backup pass");
        Object obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        startBackupAlarmsLocked(0x36ee80L);
        try {
            mRunBackupIntent.send();
        }
        catch(android.app.PendingIntent.CanceledException canceledexception) {
            Slog.e("BackupManagerService", "run-backup intent cancelled!");
        }
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public IRestoreSession beginRestoreSession(String s, String s1) {
        Object obj;
        Slog.v("BackupManagerService", (new StringBuilder()).append("beginRestoreSession: pkg=").append(s).append(" transport=").append(s1).toString());
        boolean flag = true;
        if(s1 == null) {
            s1 = mCurrentTransport;
            if(s != null) {
                PackageInfo packageinfo;
                try {
                    packageinfo = mPackageManager.getPackageInfo(s, 0);
                }
                catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                    Slog.w("BackupManagerService", (new StringBuilder()).append("Asked to restore nonexistent pkg ").append(s).toString());
                    throw new IllegalArgumentException((new StringBuilder()).append("Package ").append(s).append(" not found").toString());
                }
                if(packageinfo.applicationInfo.uid == Binder.getCallingUid())
                    flag = false;
            }
        }
        if(flag)
            mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "beginRestoreSession");
        else
            Slog.d("BackupManagerService", "restoring self on current transport; no permission needed");
        this;
        JVM INSTR monitorenter ;
        if(mActiveRestoreSession == null) goto _L2; else goto _L1
_L1:
        Slog.d("BackupManagerService", "Restore session requested but one already active");
        obj = null;
        this;
        JVM INSTR monitorexit ;
_L4:
        return ((IRestoreSession) (obj));
_L2:
        mActiveRestoreSession = new ActiveRestoreSession(s, s1);
        mBackupHandler.sendEmptyMessageDelayed(8, 60000L);
        this;
        JVM INSTR monitorexit ;
        obj = mActiveRestoreSession;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    IBackupAgent bindToAgentSynchronous(ApplicationInfo applicationinfo, int i) {
        IBackupAgent ibackupagent;
        IBackupAgent ibackupagent1;
        ibackupagent = null;
        ibackupagent1 = null;
        Object obj = mAgentConnectLock;
        obj;
        JVM INSTR monitorenter ;
        mConnecting = true;
        mConnectedAgent = null;
        if(!mActivityManager.bindBackupAgent(applicationinfo, i)) goto _L2; else goto _L1
_L1:
        long l;
        Slog.d("BackupManagerService", (new StringBuilder()).append("awaiting agent for ").append(applicationinfo).toString());
        l = 10000L + System.currentTimeMillis();
_L6:
        if(!mConnecting || mConnectedAgent != null) goto _L4; else goto _L3
_L3:
        long l1 = System.currentTimeMillis();
        if(l1 >= l) goto _L4; else goto _L5
_L5:
        mAgentConnectLock.wait(5000L);
          goto _L6
        InterruptedException interruptedexception;
        interruptedexception;
        Slog.w("BackupManagerService", (new StringBuilder()).append("Interrupted: ").append(interruptedexception).toString());
        obj;
        JVM INSTR monitorexit ;
_L8:
        return ibackupagent;
_L4:
        if(!mConnecting)
            break MISSING_BLOCK_LABEL_195;
        Slog.w("BackupManagerService", (new StringBuilder()).append("Timeout waiting for agent ").append(applicationinfo).toString());
        obj;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        try {
            Slog.i("BackupManagerService", (new StringBuilder()).append("got agent ").append(mConnectedAgent).toString());
            ibackupagent1 = mConnectedAgent;
        }
        catch(RemoteException remoteexception) { }
_L2:
        obj;
        JVM INSTR monitorexit ;
        ibackupagent = ibackupagent1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    void clearApplicationDataSynchronous(String s) {
        int i = mPackageManager.getPackageInfo(s, 0).applicationInfo.flags;
        if((i & 0x40) != 0) goto _L2; else goto _L1
_L1:
        return;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        Slog.w("BackupManagerService", (new StringBuilder()).append("Tried to clear data for ").append(s).append(" but not found").toString());
          goto _L1
_L2:
        ClearDataObserver cleardataobserver = new ClearDataObserver();
        Object obj = mClearDataLock;
        obj;
        JVM INSTR monitorenter ;
        mClearingData = true;
        Exception exception;
        long l;
        long l1;
        try {
            mActivityManager.clearApplicationUserData(s, cleardataobserver, Binder.getOrigCallingUser());
        }
        catch(RemoteException remoteexception) { }
        l = 10000L + System.currentTimeMillis();
_L4:
        if(!mClearingData)
            break MISSING_BLOCK_LABEL_162;
        l1 = System.currentTimeMillis();
        if(l1 >= l)
            break MISSING_BLOCK_LABEL_162;
        try {
            mClearDataLock.wait(5000L);
        }
        catch(InterruptedException interruptedexception) {
            mClearingData = false;
        }
        finally {
            obj;
        }
        if(true) goto _L4; else goto _L3
_L3:
        JVM INSTR monitorexit ;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void clearBackupData(String s) {
label0:
        {
            Slog.v("BackupManagerService", (new StringBuilder()).append("clearBackupData() of ").append(s).toString());
            PackageInfo packageinfo;
            HashSet hashset;
            try {
                packageinfo = mPackageManager.getPackageInfo(s, 64);
            }
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                Slog.d("BackupManagerService", (new StringBuilder()).append("No such package '").append(s).append("' - not clearing backup data").toString());
                if(false)
                    ;
                else
                    break label0;
            }
            if(mContext.checkPermission("android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
                hashset = (HashSet)mBackupParticipants.get(Binder.getCallingUid());
            } else {
                Slog.v("BackupManagerService", "Privileged caller, allowing clear of other apps");
                hashset = new HashSet();
                int i = mBackupParticipants.size();
                int j = 0;
                while(j < i)  {
                    HashSet hashset1 = (HashSet)mBackupParticipants.valueAt(j);
                    if(hashset1 != null)
                        hashset.addAll(hashset1);
                    j++;
                }
            }
            if(hashset.contains(s)) {
                Slog.v("BackupManagerService", "Found the app - running clear process");
                synchronized(mQueueLock) {
                    long l = Binder.clearCallingIdentity();
                    mWakelock.acquire();
                    Message message = mBackupHandler.obtainMessage(4, new ClearParams(getTransport(mCurrentTransport), packageinfo));
                    mBackupHandler.sendMessage(message);
                    Binder.restoreCallingIdentity(l);
                }
            }
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void clearBackupTrace() {
        List list = mBackupTrace;
        list;
        JVM INSTR monitorenter ;
        mBackupTrace.clear();
        return;
    }

    void clearRestoreSession(ActiveRestoreSession activerestoresession) {
        this;
        JVM INSTR monitorenter ;
        if(activerestoresession != mActiveRestoreSession) {
            Slog.e("BackupManagerService", "ending non-current restore session");
        } else {
            Slog.v("BackupManagerService", "Clearing restore session and halting timeout");
            mActiveRestoreSession = null;
            mBackupHandler.removeMessages(8);
        }
        return;
    }

    public void dataChanged(final String packageName) {
        final HashSet targets = dataChangedTargets(packageName);
        if(targets == null)
            Slog.w("BackupManagerService", (new StringBuilder()).append("dataChanged but no participant pkg='").append(packageName).append("'").append(" uid=").append(Binder.getCallingUid()).toString());
        else
            mBackupHandler.post(new Runnable() {

                public void run() {
                    dataChangedImpl(packageName, targets);
                }

                final BackupManagerService this$0;
                final String val$packageName;
                final HashSet val$targets;

             {
                this$0 = BackupManagerService.this;
                packageName = s;
                targets = hashset;
                super();
            }
            });
    }

    boolean deviceIsProvisioned() {
        boolean flag = false;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "device_provisioned", 0) != 0)
            flag = true;
        return flag;
    }

    void doNothing(int i, FullParams fullparams) {
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "BackupManagerService");
        l = Binder.clearCallingIdentity();
        dumpInternal(printwriter);
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void fullBackup(ParcelFileDescriptor parcelfiledescriptor, boolean flag, boolean flag1, boolean flag2, boolean flag3, String as[]) {
        long l;
        mContext.enforceCallingPermission("android.permission.BACKUP", "fullBackup");
        if(!flag2 && !flag1 && (as == null || as.length == 0))
            throw new IllegalArgumentException("Backup requested but neither shared nor any apps named");
        l = Binder.clearCallingIdentity();
        if(deviceIsProvisioned()) goto _L2; else goto _L1
_L1:
        Slog.i("BackupManagerService", "Full backup not supported before setup");
        Exception exception;
        IOException ioexception;
        FullBackupParams fullbackupparams;
        int i;
        String s;
        String s1;
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception3) { }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full backup processing complete.";
_L4:
        Slog.d(s, s1);
        return;
_L2:
        Slog.v("BackupManagerService", (new StringBuilder()).append("Requesting full backup: apks=").append(flag).append(" shared=").append(flag1).append(" all=").append(flag2).append(" pkgs=").append(as).toString());
        Slog.i("BackupManagerService", "Beginning full backup...");
        fullbackupparams = new FullBackupParams(parcelfiledescriptor, flag, flag1, flag2, flag3, as);
        i = generateToken();
        synchronized(mFullConfirmations) {
            mFullConfirmations.put(i, fullbackupparams);
        }
        Slog.d("BackupManagerService", (new StringBuilder()).append("Starting backup confirmation UI, token=").append(i).toString());
        if(startBackupRestore(i, "fullback"))
            break MISSING_BLOCK_LABEL_316;
        Slog.e("BackupManagerService", "Unable to launch full backup confirmation");
        mFullConfirmations.delete(i);
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception2) { }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full backup processing complete.";
        continue; /* Loop/switch isn't completed */
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
        exception;
        try {
            parcelfiledescriptor.close();
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) { }
        Binder.restoreCallingIdentity(l);
        Slog.d("BackupManagerService", "Full backup processing complete.");
        throw exception;
        mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
        doNothing(i, fullbackupparams);
        Slog.d("BackupManagerService", "Waiting for full backup completion...");
        waitForCompletion(fullbackupparams);
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception1) { }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full backup processing complete.";
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void fullRestore(ParcelFileDescriptor parcelfiledescriptor) {
        long l;
        mContext.enforceCallingPermission("android.permission.BACKUP", "fullRestore");
        l = Binder.clearCallingIdentity();
        if(deviceIsProvisioned()) goto _L2; else goto _L1
_L1:
        Slog.i("BackupManagerService", "Full restore not permitted before setup");
        Exception exception;
        IOException ioexception;
        FullRestoreParams fullrestoreparams;
        int i;
        String s;
        String s1;
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception3) {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Error trying to close fd after full restore: ").append(ioexception3).toString());
        }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full restore processing complete.";
_L4:
        Slog.i(s, s1);
        return;
_L2:
        Slog.i("BackupManagerService", "Beginning full restore...");
        fullrestoreparams = new FullRestoreParams(parcelfiledescriptor);
        i = generateToken();
        synchronized(mFullConfirmations) {
            mFullConfirmations.put(i, fullrestoreparams);
        }
        Slog.d("BackupManagerService", (new StringBuilder()).append("Starting restore confirmation UI, token=").append(i).toString());
        if(startBackupRestore(i, "fullrest"))
            break MISSING_BLOCK_LABEL_215;
        Slog.e("BackupManagerService", "Unable to launch full restore confirmation");
        mFullConfirmations.delete(i);
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception2) {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Error trying to close fd after full restore: ").append(ioexception2).toString());
        }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full restore processing complete.";
        continue; /* Loop/switch isn't completed */
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
        exception;
        try {
            parcelfiledescriptor.close();
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Error trying to close fd after full restore: ").append(ioexception).toString());
        }
        Binder.restoreCallingIdentity(l);
        Slog.i("BackupManagerService", "Full restore processing complete.");
        throw exception;
        mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
        doNothing(i, fullrestoreparams);
        Slog.d("BackupManagerService", "Waiting for full restore completion...");
        waitForCompletion(fullrestoreparams);
        try {
            parcelfiledescriptor.close();
        }
        catch(IOException ioexception1) {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Error trying to close fd after full restore: ").append(ioexception1).toString());
        }
        Binder.restoreCallingIdentity(l);
        s = "BackupManagerService";
        s1 = "Full restore processing complete.";
        if(true) goto _L4; else goto _L3
_L3:
    }

    int generateToken() {
_L2:
        Random random = mTokenGenerator;
        random;
        JVM INSTR monitorenter ;
        int i = mTokenGenerator.nextInt();
        if(i >= 0)
            return i;
        if(true) goto _L2; else goto _L1
_L1:
    }

    long getAvailableRestoreToken(String s) {
        long l = mAncestralToken;
        Object obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        if(mEverStoredApps.contains(s))
            l = mCurrentToken;
        return l;
    }

    public Intent getConfigurationIntent(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getConfigurationIntent");
        HashMap hashmap = mTransports;
        hashmap;
        JVM INSTR monitorenter ;
        IBackupTransport ibackuptransport = (IBackupTransport)mTransports.get(s);
        if(ibackuptransport == null) goto _L2; else goto _L1
_L1:
        Intent intent1 = ibackuptransport.configurationIntent();
        Intent intent = intent1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        intent = null;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L3:
        return intent;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L4
_L4:
    }

    public String getCurrentTransport() {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getCurrentTransport");
        return mCurrentTransport;
    }

    public String getDestinationString(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDestinationString");
        HashMap hashmap = mTransports;
        hashmap;
        JVM INSTR monitorenter ;
        IBackupTransport ibackuptransport = (IBackupTransport)mTransports.get(s);
        if(ibackuptransport == null) goto _L2; else goto _L1
_L1:
        String s2 = ibackuptransport.currentDestinationString();
        String s1 = s2;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        s1 = null;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L3:
        return s1;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L4
_L4:
    }

    void handleTimeout(int i, Object obj) {
        int j = -1;
        Operation operation;
        synchronized(mCurrentOpLock) {
            operation = (Operation)mCurrentOperations.get(i);
            if(operation != null)
                j = operation.state;
            if(j == 0) {
                Slog.v("BackupManagerService", (new StringBuilder()).append("TIMEOUT: token=").append(Integer.toHexString(i)).toString());
                operation.state = -1;
                mCurrentOperations.put(i, operation);
            }
            mCurrentOpLock.notifyAll();
        }
        if(operation != null && operation.callback != null)
            operation.callback.handleTimeout();
        return;
        exception;
        obj1;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean hasBackupPassword() {
        boolean flag;
        flag = true;
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "hasBackupPassword");
        if(mMountService.getEncryptionState() != flag) goto _L2; else goto _L1
_L1:
        if(mPasswordHash == null) goto _L4; else goto _L3
_L3:
        int i = mPasswordHash.length();
        if(i <= 0) goto _L4; else goto _L2
_L2:
        return flag;
_L4:
        flag = false;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L5
_L5:
    }

    public boolean isBackupEnabled() {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupEnabled");
        return mEnabled;
    }

    public String[] listAllTransports() {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "listAllTransports");
        String as[] = null;
        ArrayList arraylist = new ArrayList();
        Iterator iterator = mTransports.entrySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if(entry.getValue() != null)
                arraylist.add(entry.getKey());
        } while(true);
        if(arraylist.size() > 0) {
            as = new String[arraylist.size()];
            arraylist.toArray(as);
        }
        return as;
    }

    void logBackupComplete(String s) {
        if(!s.equals("@pm@")) goto _L2; else goto _L1
_L1:
        return;
_L2:
        HashSet hashset = mEverStoredApps;
        hashset;
        JVM INSTR monitorenter ;
        Exception exception;
        if(mEverStoredApps.add(s))
            break MISSING_BLOCK_LABEL_38;
        if(true)
            continue; /* Loop/switch isn't completed */
        JVM INSTR monitorexit ;
        throw exception;
        RandomAccessFile randomaccessfile = null;
        RandomAccessFile randomaccessfile1 = new RandomAccessFile(mEverStored, "rws");
        randomaccessfile1.seek(randomaccessfile1.length());
        randomaccessfile1.writeUTF(s);
        if(randomaccessfile1 == null)
            break MISSING_BLOCK_LABEL_83;
        Exception exception1;
        IOException ioexception4;
        try {
            randomaccessfile1.close();
        }
        catch(IOException ioexception3) { }
        hashset;
        JVM INSTR monitorexit ;
        if(true) goto _L1; else goto _L3
_L3:
        ioexception4;
_L7:
        Slog.e("BackupManagerService", (new StringBuilder()).append("Can't log backup of ").append(s).append(" to ").append(mEverStored).toString());
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception2) { }
            finally {
                hashset;
            }
        break MISSING_BLOCK_LABEL_83;
        exception1;
_L5:
        if(randomaccessfile == null)
            break MISSING_BLOCK_LABEL_159;
        try {
            randomaccessfile.close();
        }
        catch(IOException ioexception1) { }
        throw exception1;
        exception1;
        randomaccessfile = randomaccessfile1;
        if(true) goto _L5; else goto _L4
_L4:
        IOException ioexception;
        ioexception;
        randomaccessfile = randomaccessfile1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void opComplete(int i) {
        Operation operation;
        synchronized(mCurrentOpLock) {
            operation = (Operation)mCurrentOperations.get(i);
            if(operation != null)
                operation.state = 1;
            mCurrentOpLock.notifyAll();
        }
        if(operation != null && operation.callback != null) {
            Message message = mBackupHandler.obtainMessage(21, operation.callback);
            mBackupHandler.sendMessage(message);
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    boolean passwordMatchesSaved(String s, int i) {
        boolean flag = true;
        if(mMountService.getEncryptionState() == flag) goto _L2; else goto _L1
_L1:
        int j = ((flag) ? 1 : 0);
_L11:
        if(j == 0) goto _L4; else goto _L3
_L3:
        int k;
        Slog.i("BackupManagerService", "Device encrypted; verifying against device data pw");
        k = mMountService.verifyEncryptionPassword(s);
        if(k != 0) goto _L6; else goto _L5
_L13:
        Slog.e("BackupManagerService", "verified encryption state mismatch against query; no match allowed");
        flag = false;
          goto _L5
        Exception exception;
        exception;
        flag = false;
          goto _L5
_L4:
        if(mPasswordHash != null) goto _L8; else goto _L7
_L7:
        if(s == null || "".equals(s)) goto _L5; else goto _L9
_L9:
        flag = false;
        break; /* Loop/switch isn't completed */
_L8:
        if(s == null || s.length() <= 0) goto _L9; else goto _L10
_L10:
        String s1 = buildPasswordHash(s, mPasswordSalt, i);
        if(!mPasswordHash.equalsIgnoreCase(s1)) goto _L9; else goto _L5
_L5:
        return flag;
_L2:
        j = 0;
          goto _L11
_L6:
        if(k == -2) goto _L13; else goto _L12
_L12:
        flag = false;
          goto _L5
    }

    void prepareOperationTimeout(int i, long l, BackupRestoreTask backuprestoretask) {
        Object obj = mCurrentOpLock;
        obj;
        JVM INSTR monitorenter ;
        mCurrentOperations.put(i, new Operation(0, backuprestoretask));
        Message message = mBackupHandler.obtainMessage(7, i, 0, backuprestoretask);
        mBackupHandler.sendMessageDelayed(message, l);
        return;
    }

    void recordInitPendingLocked(boolean flag, String s) {
        Slog.i("BackupManagerService", (new StringBuilder()).append("recordInitPendingLocked: ").append(flag).append(" on transport ").append(s).toString());
        File file;
        String s1 = getTransport(s).transportDirName();
        file = new File(new File(mBaseStateDir, s1), "_need_init_");
        if(!flag) goto _L2; else goto _L1
_L1:
        mPendingInits.add(s);
        (new FileOutputStream(file)).close();
_L4:
        return;
_L2:
        try {
            file.delete();
            mPendingInits.remove(s);
        }
        catch(RemoteException remoteexception) { }
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    void removeEverBackedUp(String s) {
        Slog.v("BackupManagerService", (new StringBuilder()).append("Removing backed-up knowledge of ").append(s).toString());
        HashSet hashset = mEverStoredApps;
        hashset;
        JVM INSTR monitorenter ;
        File file = new File(mBaseStateDir, "processed.new");
        RandomAccessFile randomaccessfile = null;
        RandomAccessFile randomaccessfile1 = new RandomAccessFile(file, "rws");
        mEverStoredApps.remove(s);
        for(Iterator iterator = mEverStoredApps.iterator(); iterator.hasNext(); randomaccessfile1.writeUTF((String)iterator.next()));
          goto _L1
        IOException ioexception1;
        ioexception1;
        randomaccessfile = randomaccessfile1;
_L2:
        Slog.w("BackupManagerService", (new StringBuilder()).append("Error rewriting ").append(mEverStored).toString(), ioexception1);
        mEverStoredApps.clear();
        file.delete();
        mEverStored.delete();
        if(randomaccessfile == null)
            break MISSING_BLOCK_LABEL_180;
        Exception exception;
        try {
            randomaccessfile.close();
        }
        catch(IOException ioexception2) { }
_L4:
        hashset;
        JVM INSTR monitorexit ;
        return;
_L1:
        randomaccessfile1.close();
        randomaccessfile = null;
        try {
            if(!file.renameTo(mEverStored))
                throw new IOException((new StringBuilder()).append("Can't rename ").append(file).append(" to ").append(mEverStored).toString());
            continue; /* Loop/switch isn't completed */
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception1) { }
        finally { }
          goto _L2
_L5:
        randomaccessfile = randomaccessfile1;
        if(randomaccessfile != null)
            try {
                randomaccessfile.close();
            }
            catch(IOException ioexception) { }
            finally {
                hashset;
            }
        throw exception;
        throw exception1;
        if(true) goto _L4; else goto _L3
_L3:
        throw null;
        exception;
          goto _L5
    }

    void removePackageParticipantsLocked(String as[], int i) {
        if(as == null) {
            Slog.w("BackupManagerService", "removePackageParticipants with null list");
        } else {
            Slog.v("BackupManagerService", (new StringBuilder()).append("removePackageParticipantsLocked: uid=").append(i).append(" #").append(as.length).toString());
            int j = as.length;
            int k = 0;
            while(k < j)  {
                String s = as[k];
                HashSet hashset = (HashSet)mBackupParticipants.get(i);
                if(hashset != null && hashset.contains(s)) {
                    removePackageFromSetLocked(hashset, s);
                    if(hashset.isEmpty())
                        mBackupParticipants.remove(i);
                }
                k++;
            }
        }
    }

    void resetBackupState(File file) {
        Object obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        File afile[];
        int i;
        mEverStoredApps.clear();
        mEverStored.delete();
        mCurrentToken = 0L;
        writeRestoreTokens();
        afile = file.listFiles();
        i = afile.length;
        Exception exception;
        SparseArray sparsearray;
        Exception exception1;
        int k;
        int l;
        HashSet hashset;
        Iterator iterator;
        for(int j = 0; j < i; j++) {
            File file1 = afile[j];
            if(!file1.getName().equals("_need_init_"))
                file1.delete();
            break MISSING_BLOCK_LABEL_187;
        }

        sparsearray = mBackupParticipants;
        sparsearray;
        JVM INSTR monitorenter ;
        k = mBackupParticipants.size();
        for(l = 0; l < k; l++) {
            hashset = (HashSet)mBackupParticipants.valueAt(l);
            if(hashset == null)
                continue;
            for(iterator = hashset.iterator(); iterator.hasNext(); dataChangedImpl((String)iterator.next()));
        }

        break MISSING_BLOCK_LABEL_183;
        exception1;
        throw exception1;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        sparsearray;
        JVM INSTR monitorexit ;
    }

    public void restoreAtInstall(String s, int i) {
        if(Binder.getCallingUid() != 1000) {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Non-system process uid=").append(Binder.getCallingUid()).append(" attemping install-time restore").toString());
        } else {
            long l = getAvailableRestoreToken(s);
            Slog.v("BackupManagerService", (new StringBuilder()).append("restoreAtInstall pkg=").append(s).append(" token=").append(Integer.toHexString(i)).toString());
            if(mAutoRestore && mProvisioned && l != 0L) {
                PackageInfo packageinfo = new PackageInfo();
                packageinfo.packageName = s;
                mWakelock.acquire();
                Message message = mBackupHandler.obtainMessage(3);
                message.obj = new RestoreParams(getTransport(mCurrentTransport), null, l, packageinfo, i, true);
                mBackupHandler.sendMessage(message);
            } else {
                Slog.v("BackupManagerService", "No restore set -- skipping restore");
                try {
                    mPackageManagerBinder.finishPackageInstall(i);
                }
                catch(RemoteException remoteexception) { }
            }
        }
    }

    public String selectBackupTransport(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "selectBackupTransport");
        HashMap hashmap = mTransports;
        hashmap;
        JVM INSTR monitorenter ;
        String s1 = null;
        if(mTransports.get(s) != null) {
            s1 = mCurrentTransport;
            mCurrentTransport = s;
            android.provider.Settings.Secure.putString(mContext.getContentResolver(), "backup_transport", s);
            Slog.v("BackupManagerService", (new StringBuilder()).append("selectBackupTransport() set ").append(mCurrentTransport).append(" returning ").append(s1).toString());
        } else {
            Slog.w("BackupManagerService", (new StringBuilder()).append("Attempt to select unavailable transport ").append(s).toString());
        }
        return s1;
    }

    public void setAutoRestore(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setAutoRestore");
        Slog.i("BackupManagerService", (new StringBuilder()).append("Auto restore => ").append(flag).toString());
        this;
        JVM INSTR monitorenter ;
        ContentResolver contentresolver = mContext.getContentResolver();
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        android.provider.Settings.Secure.putInt(contentresolver, "backup_auto_restore", i);
        mAutoRestore = flag;
        return;
    }

    public void setBackupEnabled(boolean flag) {
        int i;
        i = 1;
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupEnabled");
        Slog.i("BackupManagerService", (new StringBuilder()).append("Backup enabled => ").append(flag).toString());
        boolean flag1 = mEnabled;
        this;
        JVM INSTR monitorenter ;
        ContentResolver contentresolver = mContext.getContentResolver();
        Object obj;
        if(!flag)
            i = 0;
        android.provider.Settings.Secure.putInt(contentresolver, "backup_enabled", i);
        mEnabled = flag;
        this;
        JVM INSTR monitorexit ;
        obj = mQueueLock;
        obj;
        JVM INSTR monitorenter ;
        if(!flag || flag1) goto _L2; else goto _L1
_L1:
        if(!mProvisioned) goto _L2; else goto _L3
_L3:
        startBackupAlarmsLocked(0x36ee80L);
_L5:
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        if(flag) goto _L5; else goto _L4
_L4:
        Slog.i("BackupManagerService", "Opting out of backup");
        mAlarmManager.cancel(mRunBackupIntent);
        if(!flag1 || !mProvisioned) goto _L5; else goto _L6
_L6:
        HashSet hashset;
        synchronized(mTransports) {
            hashset = new HashSet(mTransports.keySet());
        }
        for(Iterator iterator = hashset.iterator(); iterator.hasNext(); recordInitPendingLocked(true, (String)iterator.next()));
        break MISSING_BLOCK_LABEL_242;
        Exception exception1;
        exception1;
        throw exception1;
        exception2;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception2;
        mAlarmManager.set(0, System.currentTimeMillis(), mRunInitIntent);
          goto _L5
    }

    public boolean setBackupPassword(String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupPassword");
        if(passwordMatchesSaved(s, 10000)) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        if(s1 == null || s1.isEmpty()) {
            if(mPasswordHashFile.exists() && !mPasswordHashFile.delete()) {
                Slog.e("BackupManagerService", "Unable to clear backup password");
                flag = false;
            } else {
                mPasswordHash = null;
                mPasswordSalt = null;
                flag = true;
            }
            continue; /* Loop/switch isn't completed */
        }
        byte abyte0[];
        String s2;
        abyte0 = randomBytes(512);
        s2 = buildPasswordHash(s1, abyte0, 10000);
        FileOutputStream fileoutputstream;
        BufferedOutputStream bufferedoutputstream;
        DataOutputStream dataoutputstream;
        fileoutputstream = null;
        bufferedoutputstream = null;
        dataoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(mPasswordHashFile);
        BufferedOutputStream bufferedoutputstream1 = new BufferedOutputStream(fileoutputstream1);
        DataOutputStream dataoutputstream1 = new DataOutputStream(bufferedoutputstream1);
        dataoutputstream1.writeInt(abyte0.length);
        dataoutputstream1.write(abyte0);
        dataoutputstream1.writeUTF(s2);
        dataoutputstream1.flush();
        mPasswordHash = s2;
        mPasswordSalt = abyte0;
        flag = true;
        if(dataoutputstream1 == null)
            break MISSING_BLOCK_LABEL_204;
        dataoutputstream1.close();
        if(bufferedoutputstream1 != null)
            bufferedoutputstream1.close();
        if(fileoutputstream1 != null)
            fileoutputstream1.close();
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.e("BackupManagerService", "Unable to set backup password");
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
_L6:
        if(dataoutputstream == null)
            break MISSING_BLOCK_LABEL_255;
        dataoutputstream.close();
        if(bufferedoutputstream != null)
            bufferedoutputstream.close();
        if(fileoutputstream != null)
            fileoutputstream.close();
        throw exception;
        exception;
        fileoutputstream = fileoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        bufferedoutputstream = bufferedoutputstream1;
        fileoutputstream = fileoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        dataoutputstream = dataoutputstream1;
        bufferedoutputstream = bufferedoutputstream1;
        fileoutputstream = fileoutputstream1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void setBackupProvisioned(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupProvisioned");
    }

    void signalFullBackupRestoreCompletion(FullParams fullparams) {
        AtomicBoolean atomicboolean = fullparams.latch;
        atomicboolean;
        JVM INSTR monitorenter ;
        fullparams.latch.set(true);
        fullparams.latch.notifyAll();
        return;
    }

    boolean startBackupRestore(int i, String s) {
        SparseArray sparsearray = mFullConfirmations;
        sparsearray;
        JVM INSTR monitorenter ;
        FullParams fullparams = (FullParams)mFullConfirmations.get(i);
        if(fullparams != null) {
            mFullConfirmations.delete(i);
            byte byte0;
            Message message;
            if(fullparams instanceof FullBackupParams)
                byte0 = 2;
            else
                byte0 = 10;
            fullparams.observer = null;
            fullparams.curPassword = "";
            fullparams.encryptPassword = android.provider.Settings.Secure.getString(mContext.getContentResolver(), miui.provider.ExtraSettings.Secure.APP_ENCRYPT_PASSWORD);
            Slog.d("BackupManagerService", (new StringBuilder()).append("Sending conf message with verb ").append(byte0).toString());
            mWakelock.acquire();
            message = mBackupHandler.obtainMessage(byte0, fullparams);
            mBackupHandler.sendMessage(message);
        } else {
            Slog.w("BackupManagerService", "Attempted to ack full backup/restore with invalid token");
        }
        return true;
    }

    void startConfirmationTimeout(int i, FullParams fullparams) {
        Message message = mBackupHandler.obtainMessage(9, i, 0, fullparams);
        mBackupHandler.sendMessageDelayed(message, 60000L);
    }

    boolean startConfirmationUi(int i, String s) {
        Intent intent = new Intent(s);
        intent.setClassName("com.android.backupconfirm", "com.android.backupconfirm.BackupRestoreConfirmation");
        intent.putExtra("conftoken", i);
        intent.addFlags(0x10000000);
        mContext.startActivity(intent);
        boolean flag = true;
_L2:
        return flag;
        ActivityNotFoundException activitynotfoundexception;
        activitynotfoundexception;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void waitForCompletion(FullParams fullparams) {
        AtomicBoolean atomicboolean = fullparams.latch;
        atomicboolean;
        JVM INSTR monitorenter ;
_L3:
        boolean flag = fullparams.latch.get();
        if(flag) goto _L2; else goto _L1
_L1:
        Exception exception;
        try {
            fullparams.latch.wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally {
            atomicboolean;
        }
        if(true) goto _L3; else goto _L2
_L2:
        atomicboolean;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    boolean waitUntilOperationComplete(int i) {
        int j = 0;
        Object obj = mCurrentOpLock;
        obj;
        JVM INSTR monitorenter ;
_L5:
        Operation operation = (Operation)mCurrentOperations.get(i);
        if(operation != null) goto _L2; else goto _L1
_L1:
        mBackupHandler.removeMessages(7);
        Exception exception;
        int k;
        boolean flag;
        InterruptedException interruptedexception;
        if(j == true)
            flag = true;
        else
            flag = false;
        return flag;
_L2:
        k = operation.state;
        if(k == 0) {
            try {
                mCurrentOpLock.wait();
            }
            // Misplaced declaration of an exception variable
            catch(InterruptedException interruptedexception) { }
            finally {
                obj;
            }
            continue; /* Loop/switch isn't completed */
        }
        j = operation.state;
        if(true) goto _L1; else goto _L3
_L3:
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L5; else goto _L4
_L4:
    }

    void writeRestoreTokens() {
        try {
            RandomAccessFile randomaccessfile = new RandomAccessFile(mTokenFile, "rwd");
            randomaccessfile.writeInt(1);
            randomaccessfile.writeLong(mAncestralToken);
            randomaccessfile.writeLong(mCurrentToken);
            if(mAncestralPackages == null) {
                randomaccessfile.writeInt(-1);
            } else {
                randomaccessfile.writeInt(mAncestralPackages.size());
                Slog.v("BackupManagerService", (new StringBuilder()).append("Ancestral packages:  ").append(mAncestralPackages.size()).toString());
                Iterator iterator = mAncestralPackages.iterator();
                while(iterator.hasNext()) 
                    randomaccessfile.writeUTF((String)iterator.next());
            }
            randomaccessfile.close();
        }
        catch(IOException ioexception) {
            Slog.w("BackupManagerService", "Unable to write token file:", ioexception);
        }
    }

    static final String BACKUP_FILE_HEADER_MAGIC = "ANDROID BACKUP\n";
    static final int BACKUP_FILE_VERSION = 1;
    private static final long BACKUP_INTERVAL = 0x36ee80L;
    static final String BACKUP_MANIFEST_FILENAME = "_manifest";
    static final int BACKUP_MANIFEST_VERSION = 1;
    static final boolean COMPRESS_FULL_BACKUPS = true;
    static final int CURRENT_ANCESTRAL_RECORD_VERSION = 1;
    private static final boolean DEBUG = true;
    static final boolean DEBUG_BACKUP_TRACE = true;
    static final String ENCRYPTION_ALGORITHM_NAME = "AES-256";
    private static final long FIRST_BACKUP_INTERVAL = 0x2932e00L;
    private static final int FUZZ_MILLIS = 0x493e0;
    static final String INIT_SENTINEL_FILE_NAME = "_need_init_";
    private static final boolean MORE_DEBUG = false;
    static final int MSG_BACKUP_RESTORE_STEP = 20;
    private static final int MSG_FULL_CONFIRMATION_TIMEOUT = 9;
    static final int MSG_OP_COMPLETE = 21;
    private static final int MSG_RESTORE_TIMEOUT = 8;
    private static final int MSG_RUN_BACKUP = 1;
    private static final int MSG_RUN_CLEAR = 4;
    private static final int MSG_RUN_FULL_BACKUP = 2;
    private static final int MSG_RUN_FULL_RESTORE = 10;
    private static final int MSG_RUN_GET_RESTORE_SETS = 6;
    private static final int MSG_RUN_INITIALIZE = 5;
    private static final int MSG_RUN_RESTORE = 3;
    private static final int MSG_TIMEOUT = 7;
    static final int OP_ACKNOWLEDGED = 1;
    static final int OP_PENDING = 0;
    static final int OP_TIMEOUT = -1;
    static final String PACKAGE_MANAGER_SENTINEL = "@pm@";
    static final int PBKDF2_HASH_ROUNDS = 10000;
    static final int PBKDF2_KEY_SIZE = 256;
    static final int PBKDF2_SALT_SIZE = 512;
    private static final String RUN_BACKUP_ACTION = "android.app.backup.intent.RUN";
    private static final String RUN_CLEAR_ACTION = "android.app.backup.intent.CLEAR";
    private static final String RUN_INITIALIZE_ACTION = "android.app.backup.intent.INIT";
    static final String SHARED_BACKUP_AGENT_PACKAGE = "com.android.sharedstoragebackup";
    private static final String TAG = "BackupManagerService";
    static final long TIMEOUT_BACKUP_INTERVAL = 30000L;
    static final long TIMEOUT_FULL_BACKUP_INTERVAL = 0x493e0L;
    static final long TIMEOUT_FULL_CONFIRMATION = 60000L;
    static final long TIMEOUT_INTERVAL = 10000L;
    static final long TIMEOUT_RESTORE_INTERVAL = 60000L;
    static final long TIMEOUT_SHARED_BACKUP_INTERVAL = 0x1b7740L;
    ActiveRestoreSession mActiveRestoreSession;
    private IActivityManager mActivityManager;
    final Object mAgentConnectLock;
    private AlarmManager mAlarmManager;
    Set mAncestralPackages;
    long mAncestralToken;
    boolean mAutoRestore;
    BackupHandler mBackupHandler;
    IBackupManager mBackupManagerBinder;
    final SparseArray mBackupParticipants;
    volatile boolean mBackupRunning;
    final List mBackupTrace;
    File mBaseStateDir;
    BroadcastReceiver mBroadcastReceiver;
    final Object mClearDataLock;
    volatile boolean mClearingData;
    IBackupAgent mConnectedAgent;
    volatile boolean mConnecting;
    private Context mContext;
    final Object mCurrentOpLock;
    final SparseArray mCurrentOperations;
    long mCurrentToken;
    String mCurrentTransport;
    File mDataDir;
    boolean mEnabled;
    private File mEverStored;
    HashSet mEverStoredApps;
    final SparseArray mFullConfirmations;
    ServiceConnection mGoogleConnection;
    IBackupTransport mGoogleTransport;
    HandlerThread mHandlerThread;
    File mJournal;
    File mJournalDir;
    volatile long mLastBackupPass;
    IBackupTransport mLocalTransport;
    private IMountService mMountService;
    volatile long mNextBackupPass;
    private PackageManager mPackageManager;
    IPackageManager mPackageManagerBinder;
    private String mPasswordHash;
    private File mPasswordHashFile;
    private byte mPasswordSalt[];
    HashMap mPendingBackups;
    HashSet mPendingInits;
    private PowerManager mPowerManager;
    boolean mProvisioned;
    ContentObserver mProvisionedObserver;
    final Object mQueueLock;
    private final SecureRandom mRng;
    PendingIntent mRunBackupIntent;
    BroadcastReceiver mRunBackupReceiver;
    PendingIntent mRunInitIntent;
    BroadcastReceiver mRunInitReceiver;
    File mTokenFile;
    final Random mTokenGenerator;
    final HashMap mTransports;
    android.os.PowerManager.WakeLock mWakelock;
















}
