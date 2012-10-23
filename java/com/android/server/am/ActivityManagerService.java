// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.app.AppGlobals;
import android.app.ApplicationErrorReport;
import android.app.Dialog;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.IInstrumentationWatcher;
import android.app.INotificationManager;
import android.app.IProcessObserver;
import android.app.IServiceConnection;
import android.app.IThumbnailReceiver;
import android.app.MiuiThemeHelper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.backup.IBackupManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ProxyProperties;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PatternMatcher;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserId;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.ProcessStats;
import com.android.server.AttributeCache;
import com.android.server.IntentResolver;
import com.android.server.ProcessMap;
import com.android.server.Watchdog;
import com.android.server.wm.WindowManagerService;
import dalvik.system.Zygote;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

// Referenced classes of package com.android.server.am:
//            BroadcastQueue, ProcessList, ProviderMap, BatteryStatsService, 
//            UsageStatsService, CompatModePackages, ProcessRecord, BackupRecord, 
//            CoreSettingsObserver, ActivityStack, ActivityRecord, ServiceRecord, 
//            ConnectionRecord, AppBindRecord, IntentBindRecord, BroadcastRecord, 
//            BroadcastFilter, ExtraActivityManagerService, UriPermission, ContentProviderRecord, 
//            ContentProviderConnection, ReceiverList, TaskRecord, AppErrorResult, 
//            TransferPipe, AppErrorDialog, MiuiErrorReport, PendingIntentRecord, 
//            ThumbnailHolder, PendingThumbnailsRecord, UriPermissionOwner, CompatModeDialog, 
//            AppNotRespondingDialog, StrictModeViolationDialog, FactoryErrorDialog, AppWaitingForDebuggerDialog, 
//            BaseErrorDialog, LaunchWarningWindow

public final class ActivityManagerService extends ActivityManagerNative
    implements com.android.server.Watchdog.Monitor, com.android.internal.os.BatteryStatsImpl.BatteryCallback {
    static class ServiceMap {

        private HashMap getServices(int i) {
            HashMap hashmap = (HashMap)mServicesByNamePerUser.get(i);
            if(hashmap == null) {
                hashmap = new HashMap();
                mServicesByNamePerUser.put(i, hashmap);
            }
            return hashmap;
        }

        private HashMap getServicesByIntent(int i) {
            HashMap hashmap = (HashMap)mServicesByIntentPerUser.get(i);
            if(hashmap == null) {
                hashmap = new HashMap();
                mServicesByIntentPerUser.put(i, hashmap);
            }
            return hashmap;
        }

        Collection getAllServices(int i) {
            return getServices(i).values();
        }

        ServiceRecord getServiceByIntent(android.content.Intent.FilterComparison filtercomparison) {
            return getServiceByIntent(filtercomparison, -1);
        }

        ServiceRecord getServiceByIntent(android.content.Intent.FilterComparison filtercomparison, int i) {
            return (ServiceRecord)getServicesByIntent(i).get(filtercomparison);
        }

        ServiceRecord getServiceByName(ComponentName componentname) {
            return getServiceByName(componentname, -1);
        }

        ServiceRecord getServiceByName(ComponentName componentname, int i) {
            return (ServiceRecord)getServices(i).get(componentname);
        }

        void putServiceByIntent(android.content.Intent.FilterComparison filtercomparison, int i, ServiceRecord servicerecord) {
            getServicesByIntent(i).put(filtercomparison, servicerecord);
        }

        void putServiceByName(ComponentName componentname, int i, ServiceRecord servicerecord) {
            getServices(i).put(componentname, servicerecord);
        }

        void removeServiceByIntent(android.content.Intent.FilterComparison filtercomparison, int i) {
            ServiceRecord _tmp = (ServiceRecord)getServicesByIntent(i).remove(filtercomparison);
        }

        void removeServiceByName(ComponentName componentname, int i) {
            ServiceRecord _tmp = (ServiceRecord)getServices(i).remove(componentname);
        }

        private final SparseArray mServicesByIntentPerUser = new SparseArray();
        private final SparseArray mServicesByNamePerUser = new SparseArray();

        ServiceMap() {
        }
    }

    private class ServiceRestarter
        implements Runnable {

        public void run() {
            ActivityManagerService activitymanagerservice = ActivityManagerService.this;
            activitymanagerservice;
            JVM INSTR monitorenter ;
            performServiceRestartLocked(mService);
            return;
        }

        void setService(ServiceRecord servicerecord) {
            mService = servicerecord;
        }

        private ServiceRecord mService;
        final ActivityManagerService this$0;

        private ServiceRestarter() {
            this$0 = ActivityManagerService.this;
            super();
        }

    }

    private final class ServiceLookupResult {

        final String permission;
        final ServiceRecord record;
        final ActivityManagerService this$0;

        ServiceLookupResult(ServiceRecord servicerecord, String s) {
            this$0 = ActivityManagerService.this;
            super();
            record = servicerecord;
            permission = s;
        }
    }

    static final class MemItem {

        final int id;
        final String label;
        final long pss;
        final String shortLabel;
        ArrayList subitems;

        public MemItem(String s, String s1, long l, int i) {
            label = s;
            shortLabel = s1;
            pss = l;
            id = i;
        }
    }

    static class ItemMatcher {

        int build(String as[], int i) {
            do {
                String s;
label0:
                {
                    if(i < as.length) {
                        s = as[i];
                        if(!"--".equals(s))
                            break label0;
                        i++;
                    }
                    return i;
                }
                build(s);
                i++;
            } while(true);
        }

        void build(String s) {
            ComponentName componentname = ComponentName.unflattenFromString(s);
            if(componentname != null) {
                if(components == null)
                    components = new ArrayList();
                components.add(componentname);
                all = false;
            } else {
                try {
                    int i = Integer.parseInt(s, 16);
                    if(objects == null)
                        objects = new ArrayList();
                    objects.add(Integer.valueOf(i));
                    all = false;
                }
                catch(RuntimeException runtimeexception) {
                    if(strings == null)
                        strings = new ArrayList();
                    strings.add(s);
                    all = false;
                }
            }
        }

        boolean match(Object obj, ComponentName componentname) {
            if(!all) goto _L2; else goto _L1
_L1:
            boolean flag = true;
_L4:
            return flag;
_L2:
            if(components != null) {
                int k = 0;
                do {
                    if(k >= components.size())
                        break;
                    if(((ComponentName)components.get(k)).equals(componentname)) {
                        flag = true;
                        continue; /* Loop/switch isn't completed */
                    }
                    k++;
                } while(true);
            }
            if(objects != null) {
                int j = 0;
                do {
                    if(j >= objects.size())
                        break;
                    if(System.identityHashCode(obj) == ((Integer)objects.get(j)).intValue()) {
                        flag = true;
                        continue; /* Loop/switch isn't completed */
                    }
                    j++;
                } while(true);
            }
            if(strings != null) {
                String s = componentname.flattenToString();
                int i = 0;
                do {
                    if(i >= strings.size())
                        break;
                    if(s.contains((CharSequence)strings.get(i))) {
                        flag = true;
                        continue; /* Loop/switch isn't completed */
                    }
                    i++;
                } while(true);
            }
            flag = false;
            if(true) goto _L4; else goto _L3
_L3:
        }

        boolean all;
        ArrayList components;
        ArrayList objects;
        ArrayList strings;

        ItemMatcher() {
            all = true;
        }
    }

    static class NeededUriGrants extends ArrayList {

        final int flags;
        final String targetPkg;
        final int targetUid;

        NeededUriGrants(String s, int i, int j) {
            targetPkg = s;
            targetUid = i;
            flags = j;
        }
    }

    static class PermissionController extends android.os.IPermissionController.Stub {

        public boolean checkPermission(String s, int i, int j) {
            boolean flag;
            if(mActivityManagerService.checkPermission(s, i, j) == 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        ActivityManagerService mActivityManagerService;

        PermissionController(ActivityManagerService activitymanagerservice) {
            mActivityManagerService = activitymanagerservice;
        }
    }

    static class CpuBinder extends Binder {

        protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
            if(mActivityManagerService.checkCallingPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
            printwriter.println((new StringBuilder()).append("Permission Denial: can't dump cpuinfo from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L4:
            return;
_L2:
            Thread thread = mActivityManagerService.mProcessStatsThread;
            thread;
            JVM INSTR monitorenter ;
            printwriter.print(mActivityManagerService.mProcessStats.printCurrentLoad());
            printwriter.print(mActivityManagerService.mProcessStats.printCurrentState(SystemClock.uptimeMillis()));
            if(true) goto _L4; else goto _L3
_L3:
        }

        ActivityManagerService mActivityManagerService;

        CpuBinder(ActivityManagerService activitymanagerservice) {
            mActivityManagerService = activitymanagerservice;
        }
    }

    static class DbBinder extends Binder {

        protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
            if(mActivityManagerService.checkCallingPermission("android.permission.DUMP") != 0)
                printwriter.println((new StringBuilder()).append("Permission Denial: can't dump dbinfo from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
            else
                mActivityManagerService.dumpDbInfo(filedescriptor, printwriter, as);
        }

        ActivityManagerService mActivityManagerService;

        DbBinder(ActivityManagerService activitymanagerservice) {
            mActivityManagerService = activitymanagerservice;
        }
    }

    static class GraphicsBinder extends Binder {

        protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
            if(mActivityManagerService.checkCallingPermission("android.permission.DUMP") != 0)
                printwriter.println((new StringBuilder()).append("Permission Denial: can't dump gfxinfo from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
            else
                mActivityManagerService.dumpGraphicsHardwareUsage(filedescriptor, printwriter, as);
        }

        ActivityManagerService mActivityManagerService;

        GraphicsBinder(ActivityManagerService activitymanagerservice) {
            mActivityManagerService = activitymanagerservice;
        }
    }

    static class MemBinder extends Binder {

        protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
            if(mActivityManagerService.checkCallingPermission("android.permission.DUMP") != 0)
                printwriter.println((new StringBuilder()).append("Permission Denial: can't dump meminfo from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
            else
                mActivityManagerService.dumpApplicationMemoryUsage(filedescriptor, printwriter, "  ", as, false, null, null, null);
        }

        ActivityManagerService mActivityManagerService;

        MemBinder(ActivityManagerService activitymanagerservice) {
            mActivityManagerService = activitymanagerservice;
        }
    }

    static class AThread extends Thread {

        public void run() {
            Looper.prepare();
            Process.setThreadPriority(-2);
            Process.setCanSelfBackground(false);
            ActivityManagerService activitymanagerservice = new ActivityManagerService();
            this;
            JVM INSTR monitorenter ;
            mService = activitymanagerservice;
            notifyAll();
            this;
            JVM INSTR monitorexit ;
            this;
            JVM INSTR monitorenter ;
_L3:
            boolean flag = mReady;
            if(flag) goto _L2; else goto _L1
_L1:
            Exception exception;
            Exception exception1;
            try {
                wait();
            }
            catch(InterruptedException interruptedexception) { }
            finally { }
            if(true) goto _L3; else goto _L2
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
_L2:
            this;
            JVM INSTR monitorexit ;
            if(StrictMode.conditionallyEnableDebugLogging())
                Slog.i("ActivityManager", "Enabled StrictMode logging for AThread's Looper");
            Looper.loop();
            return;
            this;
            JVM INSTR monitorexit ;
            throw exception1;
        }

        boolean mReady;
        ActivityManagerService mService;

        public AThread() {
            super("ActivityManager");
            mReady = false;
        }
    }

    private final class AppDeathRecipient
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            ActivityManagerService activitymanagerservice = ActivityManagerService.this;
            activitymanagerservice;
            JVM INSTR monitorenter ;
            appDiedLocked(mApp, mPid, mAppThread);
            return;
        }

        final ProcessRecord mApp;
        final IApplicationThread mAppThread;
        final int mPid;
        final ActivityManagerService this$0;

        AppDeathRecipient(ProcessRecord processrecord, int i, IApplicationThread iapplicationthread) {
            this$0 = ActivityManagerService.this;
            super();
            mApp = processrecord;
            mPid = i;
            mAppThread = iapplicationthread;
        }
    }

    static class ProcessChangeItem {

        static final int CHANGE_ACTIVITIES = 1;
        static final int CHANGE_IMPORTANCE = 2;
        int changes;
        boolean foregroundActivities;
        int importance;
        int pid;
        int uid;

        ProcessChangeItem() {
        }
    }

    private class Identity {

        public int pid;
        final ActivityManagerService this$0;
        public int uid;

        Identity(int i, int j) {
            this$0 = ActivityManagerService.this;
            super();
            pid = i;
            uid = j;
        }
    }

    abstract class ForegroundToken
        implements android.os.IBinder.DeathRecipient {

        int pid;
        final ActivityManagerService this$0;
        IBinder token;

        ForegroundToken() {
            this$0 = ActivityManagerService.this;
            super();
        }
    }

    static class PendingActivityLaunch {

        ActivityRecord r;
        ActivityRecord sourceRecord;
        int startFlags;

        PendingActivityLaunch() {
        }
    }


    private ActivityManagerService() {
        mShowDialogs = true;
        mPendingActivityLaunches = new ArrayList();
        mBroadcastQueues = new BroadcastQueue[2];
        mFocusedActivity = null;
        mRecentTasks = new ArrayList();
        mProcessList = new ProcessList();
        mProcessNames = new ProcessMap();
        mIsolatedProcesses = new SparseArray();
        mNextIsolatedProcessUid = 0;
        mHeavyWeightProcess = null;
        mProcessCrashTimes = new ProcessMap();
        mBadProcesses = new ProcessMap();
        mPidsSelfLocked = new SparseArray();
        mForegroundProcesses = new SparseArray();
        mProcessesOnHold = new ArrayList();
        mPersistentStartingProcesses = new ArrayList();
        mRemovedProcesses = new ArrayList();
        mLruProcesses = new ArrayList();
        mProcessesToGc = new ArrayList();
        mPendingResultRecords = new HashSet();
        mIntentSenderRecords = new HashMap();
        mAlreadyLoggedViolatedStacks = new HashSet();
        mStrictModeBuffer = new StringBuilder();
        mRegisteredReceivers = new HashMap();
        mReceiverResolver = new IntentResolver() {

            protected volatile boolean allowFilterResult(IntentFilter intentfilter, List list) {
                return allowFilterResult((BroadcastFilter)intentfilter, list);
            }

            protected boolean allowFilterResult(BroadcastFilter broadcastfilter, List list) {
                IBinder ibinder;
                int i;
                ibinder = broadcastfilter.receiverList.receiver.asBinder();
                i = -1 + list.size();
_L3:
                if(i < 0)
                    break MISSING_BLOCK_LABEL_67;
                if(((BroadcastFilter)list.get(i)).receiverList.receiver.asBinder() != ibinder) goto _L2; else goto _L1
_L1:
                boolean flag = false;
_L4:
                return flag;
_L2:
                i--;
                  goto _L3
                flag = true;
                  goto _L4
            }

            protected volatile String packageForFilter(IntentFilter intentfilter) {
                return packageForFilter((BroadcastFilter)intentfilter);
            }

            protected String packageForFilter(BroadcastFilter broadcastfilter) {
                return broadcastfilter.packageName;
            }

            final ActivityManagerService this$0;

             {
                this$0 = ActivityManagerService.this;
                super();
            }
        };
        mStickyBroadcasts = new HashMap();
        mServiceMap = new ServiceMap();
        mServiceConnections = new HashMap();
        mPendingServices = new ArrayList();
        mRestartingServices = new ArrayList();
        mStoppingServices = new ArrayList();
        mBackupAppName = null;
        mBackupTarget = null;
        mPendingThumbnails = new ArrayList();
        mCancelledThumbnails = new ArrayList();
        mProviderMap = new ProviderMap();
        mLaunchingProviders = new ArrayList();
        mGrantedUriPermissions = new SparseArray();
        mConfiguration = new Configuration();
        mConfigurationSeq = 0;
        mStringBuilder = new StringBuilder(256);
        mStartRunning = false;
        mProcessesReady = false;
        mSystemReady = false;
        mBooting = false;
        mWaitingUpdate = false;
        mDidUpdate = false;
        mOnBattery = false;
        mLaunchWarningShown = false;
        mSleeping = false;
        mWentToSleep = false;
        mLockScreenShown = false;
        mShuttingDown = false;
        mCurTask = 1;
        mAdjSeq = 0;
        mLruSeq = 0;
        mNumServiceProcs = 0;
        mNewNumServiceProcs = 0;
        mProcDeaths = new int[20];
        mDebugApp = null;
        mWaitForDebugger = false;
        mDebugTransient = false;
        mOrigDebugApp = null;
        mOrigWaitForDebugger = false;
        mAlwaysFinishActivities = false;
        mController = null;
        mProfileApp = null;
        mProfileProc = null;
        mProfileType = 0;
        mAutoStopProfiler = false;
        mOpenGlTraceApp = null;
        mProcessObservers = new RemoteCallbackList();
        mActiveProcessChanges = new ProcessChangeItem[5];
        mPendingProcessChanges = new ArrayList();
        mAvailProcessChanges = new ArrayList();
        mRequestPssList = new ArrayList();
        mProcessStats = new ProcessStats(false);
        mLastCpuTime = new AtomicLong(0L);
        mProcessStatsMutexFree = new AtomicBoolean(true);
        mLastWriteTime = 0L;
        mBooted = false;
        mProcessLimit = 15;
        mProcessLimitOverride = -1;
        mLastMemUsageReportTime = 0L;
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                message.what;
                JVM INSTR tableswitch 1 33: default 152
            //                           1 153
            //                           2 309
            //                           3 721
            //                           4 761
            //                           5 785
            //                           6 815
            //                           7 152
            //                           8 152
            //                           9 152
            //                           10 152
            //                           11 152
            //                           12 927
            //                           13 1004
            //                           14 1441
            //                           15 1573
            //                           16 152
            //                           17 152
            //                           18 152
            //                           19 152
            //                           20 1604
            //                           21 1705
            //                           22 1736
            //                           23 1813
            //                           24 1826
            //                           25 2070
            //                           26 528
            //                           27 2112
            //                           28 1130
            //                           29 1256
            //                           30 2163
            //                           31 2262
            //                           32 2272
            //                           33 2298;
                   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L1 _L1 _L1 _L1 _L1 _L8 _L9 _L10 _L11 _L1 _L1 _L1 _L1 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L19 _L20 _L21 _L22 _L23 _L24 _L25
_L1:
                return;
_L2:
                HashMap hashmap2 = (HashMap)message.obj;
                ActivityManagerService activitymanagerservice13 = ActivityManagerService.this;
                activitymanagerservice13;
                JVM INSTR monitorenter ;
                ProcessRecord processrecord8 = (ProcessRecord)hashmap2.get("app");
                if(processrecord8 != null && processrecord8.crashDialog != null) {
                    Slog.e("ActivityManager", (new StringBuilder()).append("App already has crash dialog: ").append(processrecord8).toString());
                    continue; /* Loop/switch isn't completed */
                }
                break MISSING_BLOCK_LABEL_236;
                Exception exception8;
                exception8;
                throw exception8;
                AppErrorResult apperrorresult1;
                apperrorresult1 = (AppErrorResult)hashmap2.get("result");
                if(!mShowDialogs || mSleeping || mShuttingDown)
                    break MISSING_BLOCK_LABEL_300;
                showAppCrashDialog(hashmap2);
_L26:
                activitymanagerservice13;
                JVM INSTR monitorexit ;
                ensureBootCompleted();
                continue; /* Loop/switch isn't completed */
                apperrorresult1.set(0);
                  goto _L26
_L3:
                ActivityManagerService activitymanagerservice12 = ActivityManagerService.this;
                activitymanagerservice12;
                JVM INSTR monitorenter ;
                HashMap hashmap1;
                ProcessRecord processrecord7;
                hashmap1 = (HashMap)message.obj;
                processrecord7 = (ProcessRecord)hashmap1.get("app");
                if(processrecord7 != null && processrecord7.anrDialog != null) {
                    Slog.e("ActivityManager", (new StringBuilder()).append("App already has anr dialog: ").append(processrecord7).toString());
                    continue; /* Loop/switch isn't completed */
                }
                break MISSING_BLOCK_LABEL_392;
                Exception exception7;
                exception7;
                throw exception7;
                Intent intent = new Intent("android.intent.action.ANR");
                if(!mProcessesReady)
                    intent.addFlags(0x50000000);
                broadcastIntentLocked(null, null, intent, null, null, 0, null, null, null, false, false, ActivityManagerService.MY_PID, 1000, 0);
                if(!mShowDialogs)
                    break MISSING_BLOCK_LABEL_515;
                AppNotRespondingDialog appnotrespondingdialog = new AppNotRespondingDialog(ActivityManagerService.this, mContext, processrecord7, (ActivityRecord)hashmap1.get("activity"));
                appnotrespondingdialog.show();
                processrecord7.anrDialog = appnotrespondingdialog;
_L27:
                activitymanagerservice12;
                JVM INSTR monitorexit ;
                ensureBootCompleted();
                continue; /* Loop/switch isn't completed */
                killAppAtUsersRequest(processrecord7, null);
                  goto _L27
_L18:
                HashMap hashmap = (HashMap)message.obj;
                ActivityManagerService activitymanagerservice11 = ActivityManagerService.this;
                activitymanagerservice11;
                JVM INSTR monitorenter ;
                ProcessRecord processrecord6;
                processrecord6 = (ProcessRecord)hashmap.get("app");
                if(processrecord6 == null) {
                    Slog.e("ActivityManager", "App not found when showing strict mode dialog.");
                    continue; /* Loop/switch isn't completed */
                }
                break MISSING_BLOCK_LABEL_585;
                Exception exception6;
                exception6;
                throw exception6;
                if(processrecord6.crashDialog == null)
                    break MISSING_BLOCK_LABEL_625;
                Slog.e("ActivityManager", (new StringBuilder()).append("App already has strict mode dialog: ").append(processrecord6).toString());
                activitymanagerservice11;
                JVM INSTR monitorexit ;
                continue; /* Loop/switch isn't completed */
                AppErrorResult apperrorresult;
                apperrorresult = (AppErrorResult)hashmap.get("result");
                if(!mShowDialogs || mSleeping || mShuttingDown)
                    break MISSING_BLOCK_LABEL_712;
                StrictModeViolationDialog strictmodeviolationdialog = new StrictModeViolationDialog(mContext, apperrorresult, processrecord6);
                strictmodeviolationdialog.show();
                processrecord6.crashDialog = strictmodeviolationdialog;
_L28:
                activitymanagerservice11;
                JVM INSTR monitorexit ;
                ensureBootCompleted();
                continue; /* Loop/switch isn't completed */
                apperrorresult.set(0);
                  goto _L28
_L4:
                FactoryErrorDialog factoryerrordialog = new FactoryErrorDialog(mContext, message.getData().getCharSequence("msg"));
                factoryerrordialog.show();
                ensureBootCompleted();
                continue; /* Loop/switch isn't completed */
_L5:
                android.provider.Settings.System.putConfiguration(mContext.getContentResolver(), (Configuration)message.obj);
                continue; /* Loop/switch isn't completed */
_L6:
                ActivityManagerService activitymanagerservice10 = ActivityManagerService.this;
                activitymanagerservice10;
                JVM INSTR monitorenter ;
                performAppGcsIfAppropriateLocked();
                continue; /* Loop/switch isn't completed */
_L7:
                ActivityManagerService activitymanagerservice9 = ActivityManagerService.this;
                activitymanagerservice9;
                JVM INSTR monitorenter ;
                ProcessRecord processrecord5 = (ProcessRecord)message.obj;
                if(message.arg1 == 0) goto _L30; else goto _L29
_L29:
                if(!processrecord5.waitedForDebugger) {
                    AppWaitingForDebuggerDialog appwaitingfordebuggerdialog = new AppWaitingForDebuggerDialog(ActivityManagerService.this, mContext, processrecord5);
                    processrecord5.waitDialog = appwaitingfordebuggerdialog;
                    processrecord5.waitedForDebugger = true;
                    appwaitingfordebuggerdialog.show();
                }
_L31:
                activitymanagerservice9;
                JVM INSTR monitorexit ;
                continue; /* Loop/switch isn't completed */
                Exception exception5;
                exception5;
                throw exception5;
_L30:
                if(processrecord5.waitDialog != null) {
                    processrecord5.waitDialog.dismiss();
                    processrecord5.waitDialog = null;
                }
                if(true) goto _L31; else goto _L8
_L8:
                if(mDidDexOpt) {
                    mDidDexOpt = false;
                    Message message2 = mHandler.obtainMessage(12);
                    message2.obj = message.obj;
                    mHandler.sendMessageDelayed(message2, 20000L);
                } else {
                    serviceTimeout((ProcessRecord)message.obj);
                }
                continue; /* Loop/switch isn't completed */
_L9:
                ActivityManagerService activitymanagerservice8 = ActivityManagerService.this;
                activitymanagerservice8;
                JVM INSTR monitorenter ;
                int k1 = -1 + mLruProcesses.size();
_L33:
                ProcessRecord processrecord4;
                IApplicationThread iapplicationthread2;
                if(k1 < 0)
                    break MISSING_BLOCK_LABEL_1124;
                processrecord4 = (ProcessRecord)mLruProcesses.get(k1);
                iapplicationthread2 = processrecord4.thread;
                Exception exception4;
                if(iapplicationthread2 != null)
                    try {
                        processrecord4.thread.updateTimeZone();
                    }
                    catch(RemoteException remoteexception4) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Failed to update time zone for: ").append(processrecord4.info.processName).toString());
                    }
                    finally {
                        activitymanagerservice8;
                    }
                k1--;
                if(true) goto _L33; else goto _L32
_L32:
                JVM INSTR monitorexit ;
                throw exception4;
                activitymanagerservice8;
                JVM INSTR monitorexit ;
                continue; /* Loop/switch isn't completed */
_L20:
                ActivityManagerService activitymanagerservice7 = ActivityManagerService.this;
                activitymanagerservice7;
                JVM INSTR monitorenter ;
                int j1 = -1 + mLruProcesses.size();
_L35:
                ProcessRecord processrecord3;
                IApplicationThread iapplicationthread1;
                if(j1 < 0)
                    break MISSING_BLOCK_LABEL_1250;
                processrecord3 = (ProcessRecord)mLruProcesses.get(j1);
                iapplicationthread1 = processrecord3.thread;
                Exception exception3;
                if(iapplicationthread1 != null)
                    try {
                        processrecord3.thread.clearDnsCache();
                    }
                    catch(RemoteException remoteexception3) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Failed to clear dns cache for: ").append(processrecord3.info.processName).toString());
                    }
                    finally {
                        activitymanagerservice7;
                    }
                j1--;
                if(true) goto _L35; else goto _L34
_L34:
                JVM INSTR monitorexit ;
                throw exception3;
                activitymanagerservice7;
                JVM INSTR monitorexit ;
                continue; /* Loop/switch isn't completed */
_L21:
                String s2;
                String s3;
                String s4;
                ProxyProperties proxyproperties = (ProxyProperties)message.obj;
                s2 = "";
                s3 = "";
                s4 = "";
                if(proxyproperties != null) {
                    s2 = proxyproperties.getHost();
                    s3 = Integer.toString(proxyproperties.getPort());
                    s4 = proxyproperties.getExclusionList();
                }
                ActivityManagerService activitymanagerservice6 = ActivityManagerService.this;
                activitymanagerservice6;
                JVM INSTR monitorenter ;
                int i1 = -1 + mLruProcesses.size();
_L37:
                ProcessRecord processrecord2;
                IApplicationThread iapplicationthread;
                if(i1 < 0)
                    break MISSING_BLOCK_LABEL_1435;
                processrecord2 = (ProcessRecord)mLruProcesses.get(i1);
                iapplicationthread = processrecord2.thread;
                Exception exception2;
                if(iapplicationthread != null)
                    try {
                        processrecord2.thread.setHttpProxy(s2, s3, s4);
                    }
                    catch(RemoteException remoteexception2) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Failed to update http proxy for: ").append(processrecord2.info.processName).toString());
                    }
                    finally {
                        activitymanagerservice6;
                    }
                i1--;
                if(true) goto _L37; else goto _L36
_L36:
                JVM INSTR monitorexit ;
                throw exception2;
                activitymanagerservice6;
                JVM INSTR monitorexit ;
                continue; /* Loop/switch isn't completed */
_L10:
                Log.e("ActivityManager", (new StringBuilder()).append("System UIDs Inconsistent").append(": ").append("UIDs on the system are inconsistent, you need to wipe your data partition or your device will be unstable.").toString());
                if(mShowDialogs) {
                    BaseErrorDialog baseerrordialog = new BaseErrorDialog(mContext);
                    baseerrordialog.getWindow().setType(2010);
                    baseerrordialog.setCancelable(false);
                    baseerrordialog.setTitle("System UIDs Inconsistent");
                    baseerrordialog.setMessage("UIDs on the system are inconsistent, you need to wipe your data partition or your device will be unstable.");
                    baseerrordialog.setButton(-1, "I'm Feeling Lucky", mHandler.obtainMessage(15));
                    mUidAlert = baseerrordialog;
                    baseerrordialog.show();
                }
                continue; /* Loop/switch isn't completed */
_L11:
                if(mUidAlert != null) {
                    mUidAlert.dismiss();
                    mUidAlert = null;
                }
                continue; /* Loop/switch isn't completed */
_L12:
                ProcessRecord processrecord1;
                if(mDidDexOpt) {
                    mDidDexOpt = false;
                    Message message1 = mHandler.obtainMessage(20);
                    message1.obj = message.obj;
                    mHandler.sendMessageDelayed(message1, 10000L);
                    continue; /* Loop/switch isn't completed */
                }
                processrecord1 = (ProcessRecord)message.obj;
                ActivityManagerService activitymanagerservice5 = ActivityManagerService.this;
                activitymanagerservice5;
                JVM INSTR monitorenter ;
                processStartTimedOutLocked(processrecord1);
                continue; /* Loop/switch isn't completed */
_L13:
                ActivityManagerService activitymanagerservice4 = ActivityManagerService.this;
                activitymanagerservice4;
                JVM INSTR monitorenter ;
                doPendingActivityLaunchesLocked(true);
                continue; /* Loop/switch isn't completed */
_L14:
                ActivityManagerService activitymanagerservice3 = ActivityManagerService.this;
                activitymanagerservice3;
                JVM INSTR monitorenter ;
                int k = message.arg1;
                boolean flag;
                String s1;
                if(message.arg2 == 1)
                    flag = true;
                else
                    flag = false;
                s1 = (String)message.obj;
                forceStopPackageLocked(s1, k, flag, false, true, false, UserId.getUserId(k));
                continue; /* Loop/switch isn't completed */
_L15:
                ((PendingIntentRecord)message.obj).completeFinalize();
                continue; /* Loop/switch isn't completed */
_L16:
                INotificationManager inotificationmanager1;
                ActivityRecord activityrecord1;
                ProcessRecord processrecord;
                inotificationmanager1 = NotificationManager.getService();
                if(inotificationmanager1 == null)
                    continue; /* Loop/switch isn't completed */
                activityrecord1 = (ActivityRecord)message.obj;
                processrecord = activityrecord1.app;
                if(processrecord == null)
                    continue; /* Loop/switch isn't completed */
                Notification notification;
                Context context = mContext.createPackageContext(processrecord.info.packageName, 0);
                Context context1 = mContext;
                Object aobj[] = new Object[1];
                aobj[0] = context.getApplicationInfo().loadLabel(context.getPackageManager());
                String s = context1.getString(0x10403e7, aobj);
                notification = new Notification();
                notification.icon = 0x1080518;
                notification.when = 0L;
                notification.flags = 2;
                notification.tickerText = s;
                notification.defaults = 0;
                notification.sound = null;
                notification.vibrate = null;
                notification.setLatestEventInfo(context, s, mContext.getText(0x10403e8), PendingIntent.getActivity(mContext, 0, activityrecord1.intent, 0x10000000));
                ActivityRecord activityrecord;
                ActivityManagerService activitymanagerservice;
                Exception exception;
                long l;
                Thread thread;
                int i;
                int j;
                ActivityManagerService activitymanagerservice1;
                Exception exception1;
                ActivityManagerService activitymanagerservice2;
                INotificationManager inotificationmanager;
                RemoteException remoteexception;
                RuntimeException runtimeexception;
                try {
                    inotificationmanager1.enqueueNotification("android", 0x10403e7, notification, new int[1]);
                }
                catch(RuntimeException runtimeexception1) {
                    try {
                        Slog.w("ActivityManager", "Error showing notification for heavy-weight app", runtimeexception1);
                    }
                    catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                        Slog.w("ActivityManager", "Unable to create context for heavy notification", namenotfoundexception);
                    }
                }
                catch(RemoteException remoteexception1) { }
                continue; /* Loop/switch isn't completed */
_L17:
                inotificationmanager = NotificationManager.getService();
                if(inotificationmanager != null)
                    try {
                        inotificationmanager.cancelNotification("android", 0x10403e7);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RuntimeException runtimeexception) {
                        Slog.w("ActivityManager", "Error canceling notification for service", runtimeexception);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RemoteException remoteexception) { }
                continue; /* Loop/switch isn't completed */
_L19:
                activitymanagerservice2 = ActivityManagerService.this;
                activitymanagerservice2;
                JVM INSTR monitorenter ;
                checkExcessivePowerUsageLocked(true);
                removeMessages(27);
                sendMessageDelayed(obtainMessage(27), 0xdbba0L);
                continue; /* Loop/switch isn't completed */
_L22:
                activitymanagerservice1 = ActivityManagerService.this;
                activitymanagerservice1;
                JVM INSTR monitorenter ;
                activityrecord = (ActivityRecord)message.obj;
                if(mCompatModeDialog != null) {
                    if(mCompatModeDialog.mAppInfo.packageName.equals(activityrecord.info.applicationInfo.packageName))
                        continue; /* Loop/switch isn't completed */
                    break MISSING_BLOCK_LABEL_2235;
                }
                  goto _L38
                exception1;
                throw exception1;
                mCompatModeDialog.dismiss();
                mCompatModeDialog = null;
                  goto _L38
_L23:
                dispatchProcessesChanged();
                continue; /* Loop/switch isn't completed */
_L24:
                i = message.arg1;
                j = message.arg2;
                dispatchProcessDied(i, j);
                continue; /* Loop/switch isn't completed */
_L25:
                if(!"1".equals(SystemProperties.get("ro.debuggable", "0")))
                    continue; /* Loop/switch isn't completed */
                activitymanagerservice = ActivityManagerService.this;
                activitymanagerservice;
                JVM INSTR monitorenter ;
                l = SystemClock.uptimeMillis();
                if(l < 0x493e0L + mLastMemUsageReportTime)
                    continue; /* Loop/switch isn't completed */
                break MISSING_BLOCK_LABEL_2355;
                exception;
                throw exception;
                mLastMemUsageReportTime = l;
                activitymanagerservice;
                JVM INSTR monitorexit ;
                thread = new Thread() {

                    public void run() {
                        StringBuilder stringbuilder;
                        StringBuilder stringbuilder1;
                        StringWriter stringwriter1;
                        PrintWriter printwriter1;
                        String as[];
                        StringBuilder stringbuilder2;
                        stringbuilder = new StringBuilder(1024);
                        stringbuilder1 = new StringBuilder(1024);
                        StringWriter stringwriter = new StringWriter();
                        PrintWriter printwriter = new PrintWriter(stringwriter);
                        stringwriter1 = new StringWriter();
                        printwriter1 = new PrintWriter(stringwriter1);
                        as = new String[0];
                        stringbuilder2 = new StringBuilder(128);
                        StringBuilder stringbuilder3 = new StringBuilder(128);
                        stringbuilder2.append("Low on memory -- ");
                        dumpApplicationMemoryUsage(null, printwriter, "  ", as, true, printwriter1, stringbuilder2, stringbuilder3);
                        stringbuilder.append(stringbuilder3);
                        stringbuilder.append('\n');
                        stringbuilder.append('\n');
                        String s5 = stringwriter.toString();
                        stringbuilder.append(s5);
                        stringbuilder.append('\n');
                        stringbuilder1.append(s5);
                        InputStreamReader inputstreamreader;
                        BufferedReader bufferedreader;
                        Runtime runtime = Runtime.getRuntime();
                        String as1[] = new String[1];
                        as1[0] = "procrank";
                        Process process = runtime.exec(as1);
                        inputstreamreader = new InputStreamReader(process.getInputStream());
                        bufferedreader = new BufferedReader(inputstreamreader);
_L3:
                        String s6 = bufferedreader.readLine();
                        if(s6 != null) goto _L2; else goto _L1
_L1:
                        inputstreamreader.close();
_L4:
                        synchronized(_fld0) {
                            printwriter1.println();
                            dumpProcessesLocked(null, printwriter1, as, 0, false, null);
                            printwriter1.println();
                            dumpServicesLocked(null, printwriter1, as, 0, false, false, null);
                            printwriter1.println();
                            dumpActivitiesLocked(null, printwriter1, as, 0, false, false, null);
                        }
                        stringbuilder.append(stringwriter1.toString());
                        addErrorToDropBox("lowmem", null, "system_server", null, null, stringbuilder2.toString(), stringbuilder.toString(), null, null);
                        Slog.i("ActivityManager", stringbuilder1.toString());
                        synchronized(_fld0) {
                            long l1 = SystemClock.uptimeMillis();
                            if(mLastMemUsageReportTime < l1)
                                mLastMemUsageReportTime = l1;
                        }
                        return;
_L2:
                        if(s6.length() > 0) {
                            stringbuilder1.append(s6);
                            stringbuilder1.append('\n');
                        }
                        stringbuilder.append(s6);
                        stringbuilder.append('\n');
                          goto _L3
                        IOException ioexception;
                        ioexception;
                          goto _L4
                        exception9;
                        activitymanagerservice14;
                        JVM INSTR monitorexit ;
                        throw exception9;
                        exception10;
                        activitymanagerservice15;
                        JVM INSTR monitorexit ;
                        throw exception10;
                          goto _L3
                    }

                    final _cls2 this$1;

                     {
                        this$1 = _cls2.this;
                        super();
                    }
                };
                thread.start();
                continue; /* Loop/switch isn't completed */
_L38:
                if(activityrecord == null);
                if(true) goto _L1; else goto _L39
_L39:
            }

            final ActivityManagerService this$0;

             {
                this$0 = ActivityManagerService.this;
                super();
            }
        };
        mLoggedInUsers = new SparseIntArray(5);
        Slog.i("ActivityManager", (new StringBuilder()).append("Memory class: ").append(ActivityManager.staticGetMemoryClass()).toString());
        mFgBroadcastQueue = new BroadcastQueue(this, "foreground", 10000L);
        mBgBroadcastQueue = new BroadcastQueue(this, "background", 60000L);
        mBroadcastQueues[0] = mFgBroadcastQueue;
        mBroadcastQueues[1] = mBgBroadcastQueue;
        File file = Environment.getDataDirectory();
        File file1 = new File(file, "system");
        file1.mkdirs();
        miui.os.Environment.init(file1, file);
        mBatteryStatsService = new BatteryStatsService((new File(file1, "batterystats.bin")).toString());
        mBatteryStatsService.getActiveStatistics().readLocked();
        mBatteryStatsService.getActiveStatistics().writeAsyncLocked();
        mOnBattery = mBatteryStatsService.getActiveStatistics().getIsOnBattery();
        mBatteryStatsService.getActiveStatistics().setCallback(this);
        mUsageStatsService = new UsageStatsService((new File(file1, "usagestats")).toString());
        mHeadless = "1".equals(SystemProperties.get("ro.config.headless", "0"));
        GL_ES_VERSION = SystemProperties.getInt("ro.opengles.version", 0);
        mConfiguration.setToDefaults();
        mConfiguration.locale = Locale.getDefault();
        mConfiguration.seq = 1;
        mConfigurationSeq = 1;
        mProcessStats.init();
        mCompatModePackages = new CompatModePackages(this, file1);
        Watchdog.getInstance().addMonitor(this);
        mProcessStatsThread = new Thread("ProcessStats") {

            public void run() {
_L2:
                this;
                JVM INSTR monitorenter ;
                long l = SystemClock.uptimeMillis();
                long l1 = (0xfffffffL + mLastCpuTime.get()) - l;
                long l2 = (0x1b7740L + mLastWriteTime) - l;
                if(l2 < l1)
                    l1 = l2;
                if(l1 > 0L) {
                    mProcessStatsMutexFree.set(true);
                    wait(l1);
                }
                this;
                JVM INSTR monitorexit ;
_L3:
                try {
                    updateCpuStatsNow();
                }
                catch(Exception exception) {
                    Slog.e("ActivityManager", "Unexpected exception collecting process stats", exception);
                }
                if(true) goto _L2; else goto _L1
_L1:
                Exception exception1;
                exception1;
                this;
                JVM INSTR monitorexit ;
                throw exception1;
                InterruptedException interruptedexception;
                interruptedexception;
                  goto _L3
            }

            final ActivityManagerService this$0;

             {
                this$0 = ActivityManagerService.this;
                super(s);
            }
        };
        mProcessStatsThread.start();
    }


    private void appendDropBoxProcessHeaders(ProcessRecord processrecord, String s, StringBuilder stringbuilder) {
        if(processrecord != null) goto _L2; else goto _L1
_L1:
        stringbuilder.append("Process: ").append(s).append("\n");
_L5:
        return;
_L2:
        this;
        JVM INSTR monitorenter ;
        IPackageManager ipackagemanager;
        Iterator iterator;
        stringbuilder.append("Process: ").append(s).append("\n");
        int i = processrecord.info.flags;
        ipackagemanager = AppGlobals.getPackageManager();
        stringbuilder.append("Flags: 0x").append(Integer.toString(i, 16)).append("\n");
        iterator = processrecord.pkgList.iterator();
_L3:
        String s1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_241;
        s1 = (String)iterator.next();
        stringbuilder.append("Package: ").append(s1);
        PackageInfo packageinfo = ipackagemanager.getPackageInfo(s1, 0, 0);
        if(packageinfo != null) {
            stringbuilder.append(" v").append(packageinfo.versionCode);
            if(packageinfo.versionName != null)
                stringbuilder.append(" (").append(packageinfo.versionName).append(")");
        }
_L4:
        stringbuilder.append("\n");
          goto _L3
        Exception exception;
        exception;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Slog.e("ActivityManager", (new StringBuilder()).append("Error getting package info: ").append(s1).toString(), remoteexception);
          goto _L4
        this;
        JVM INSTR monitorexit ;
          goto _L5
    }

    static final void appendMemBucket(StringBuilder stringbuilder, long l, String s, boolean flag) {
        int j;
        int k;
        int i1;
        int i = s.lastIndexOf('.');
        if(i >= 0)
            j = i + 1;
        else
            j = 0;
        k = s.length();
        i1 = 0;
_L3:
        if(i1 >= DUMP_MEM_BUCKETS.length)
            break MISSING_BLOCK_LABEL_111;
        if(DUMP_MEM_BUCKETS[i1] < l) goto _L2; else goto _L1
_L1:
        stringbuilder.append(DUMP_MEM_BUCKETS[i1] / 1024L);
        String s2;
        if(flag)
            s2 = "MB.";
        else
            s2 = "MB ";
        stringbuilder.append(s2);
        stringbuilder.append(s, j, k);
_L4:
        return;
_L2:
        i1++;
          goto _L3
        stringbuilder.append(l / 1024L);
        String s1;
        if(flag)
            s1 = "MB.";
        else
            s1 = "MB ";
        stringbuilder.append(s1);
        stringbuilder.append(s, j, k);
          goto _L4
    }

    private int applyUserId(int i, int j) {
        return UserId.getUid(j, i);
    }

    private final boolean attachApplicationLocked(IApplicationThread iapplicationthread, int i) {
        ProcessRecord processrecord;
        boolean flag;
        if(i != MY_PID && i >= 0)
            synchronized(mPidsSelfLocked) {
                processrecord = (ProcessRecord)mPidsSelfLocked.get(i);
            }
        else
            processrecord = null;
        if(processrecord != null) goto _L2; else goto _L1
_L1:
        Slog.w("ActivityManager", (new StringBuilder()).append("No pending application record for pid ").append(i).append(" (IApplicationThread ").append(iapplicationthread).append("); dropping process").toString());
        EventLog.writeEvent(30033, i);
        if(i > 0 && i != MY_PID)
            Process.killProcessQuiet(i);
        else
            try {
                iapplicationthread.scheduleExit();
            }
            catch(Exception exception5) { }
        flag = false;
_L24:
        return flag;
        exception6;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception6;
_L2:
        String s;
        byte byte0;
        boolean flag4;
        String s2;
        ApplicationInfo applicationinfo;
        boolean flag5;
        boolean flag7;
        boolean flag8;
        ActivityRecord activityrecord;
        ServiceRecord servicerecord;
        int j;
        if(processrecord.thread != null)
            handleAppDiedLocked(processrecord, true, true);
        s = processrecord.processName;
        Object aobj[];
        boolean flag1;
        List list;
        String s1;
        ParcelFileDescriptor parcelfiledescriptor;
        boolean flag2;
        boolean flag3;
        CompatibilityInfo compatibilityinfo;
        ComponentName componentname;
        Bundle bundle;
        IInstrumentationWatcher iinstrumentationwatcher;
        boolean flag6;
        Configuration configuration;
        long l;
        int k;
        ProcessRecord processrecord1;
        boolean flag10;
        try {
            AppDeathRecipient appdeathrecipient = new AppDeathRecipient(processrecord, i, iapplicationthread);
            iapplicationthread.asBinder().linkToDeath(appdeathrecipient, 0);
            processrecord.deathRecipient = appdeathrecipient;
        }
        catch(RemoteException remoteexception) {
            processrecord.resetPackageList();
            startProcessLocked(processrecord, "link fail", s);
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        aobj = new Object[2];
        aobj[0] = Integer.valueOf(processrecord.pid);
        aobj[1] = processrecord.processName;
        EventLog.writeEvent(30010, aobj);
        processrecord.thread = iapplicationthread;
        processrecord.setAdj = -100;
        processrecord.curAdj = -100;
        processrecord.curSchedGroup = -1;
        processrecord.setSchedGroup = 0;
        processrecord.forcingToForeground = null;
        processrecord.foregroundServices = false;
        processrecord.hasShownUi = false;
        processrecord.debugging = false;
        mHandler.removeMessages(20, processrecord);
        if(mProcessesReady || isAllowedWhileBooting(processrecord.info))
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
            list = generateApplicationProvidersLocked(processrecord);
        else
            list = null;
        if(!flag1)
            Slog.i("ActivityManager", (new StringBuilder()).append("Launching preboot mode app: ").append(processrecord).toString());
        byte0 = 0;
        if(mDebugApp == null || !mDebugApp.equals(s)) goto _L4; else goto _L3
_L3:
        if(!mWaitForDebugger) goto _L6; else goto _L5
_L5:
        byte0 = 2;
_L19:
        processrecord.debugging = true;
        if(mDebugTransient) {
            mDebugApp = mOrigDebugApp;
            mWaitForDebugger = mOrigWaitForDebugger;
        }
_L4:
        s1 = processrecord.instrumentationProfileFile;
        parcelfiledescriptor = null;
        flag2 = false;
        if(mProfileApp != null && mProfileApp.equals(s)) {
            mProfileProc = processrecord;
            s1 = mProfileFile;
            parcelfiledescriptor = mProfileFd;
            flag2 = mAutoStopProfiler;
        }
        flag3 = false;
        if(mOpenGlTraceApp != null && mOpenGlTraceApp.equals(s)) {
            flag3 = true;
            mOpenGlTraceApp = null;
        }
        flag4 = false;
        Exception exception;
        boolean flag9;
        Exception exception3;
        Exception exception4;
        boolean flag11;
        if(mBackupTarget != null && mBackupAppName.equals(s))
            if(mBackupTarget.backupMode != 2 && mBackupTarget.backupMode != 3 && mBackupTarget.backupMode != 1)
                flag4 = false;
            else
                flag4 = true;
        if(processrecord.instrumentationInfo == null) goto _L8; else goto _L7
_L7:
        s2 = processrecord.instrumentationInfo.packageName;
_L20:
        ensurePackageDexOpt(s2);
        if(processrecord.instrumentationClass != null)
            ensurePackageDexOpt(processrecord.instrumentationClass.getPackageName());
        if(processrecord.instrumentationInfo == null) goto _L10; else goto _L9
_L9:
        applicationinfo = processrecord.instrumentationInfo;
_L21:
        compatibilityinfo = compatibilityInfoForPackageLocked(applicationinfo);
        processrecord.compat = compatibilityinfo;
        if(parcelfiledescriptor != null)
            parcelfiledescriptor = parcelfiledescriptor.dup();
        componentname = processrecord.instrumentationClass;
        bundle = processrecord.instrumentationArguments;
        iinstrumentationwatcher = processrecord.instrumentationWatcher;
        if(!flag4 && flag1)
            flag5 = false;
        else
            flag5 = true;
        flag6 = processrecord.persistent;
        configuration = new Configuration(mConfiguration);
        iapplicationthread.bindApplication(s, applicationinfo, list, componentname, s1, parcelfiledescriptor, flag2, bundle, iinstrumentationwatcher, byte0, flag3, flag5, flag6, configuration, processrecord.compat, getCommonServicesLocked(), mCoreSettingsObserver.getCoreSettingsLocked());
        updateLruProcessLocked(processrecord, false, true);
        l = SystemClock.uptimeMillis();
        processrecord.lastLowMemory = l;
        processrecord.lastRequestedGc = l;
        mPersistentStartingProcesses.remove(processrecord);
        mProcessesOnHold.remove(processrecord);
        flag7 = false;
        flag8 = false;
        activityrecord = mMainStack.topRunningActivityLocked(null);
        if(activityrecord == null || !flag1) goto _L12; else goto _L11
_L11:
        if(activityrecord.app != null || processrecord.uid != activityrecord.info.applicationInfo.uid || !s.equals(activityrecord.processName)) goto _L14; else goto _L13
_L13:
        if(!mHeadless) goto _L16; else goto _L15
_L15:
        Slog.e("ActivityManager", (new StringBuilder()).append("Starting activities not supported on headless device: ").append(activityrecord).toString());
_L12:
        if(flag7 || mPendingServices.size() <= 0)
            break MISSING_BLOCK_LABEL_1229;
        servicerecord = null;
        j = 0;
_L18:
        k = mPendingServices.size();
        if(j >= k)
            break MISSING_BLOCK_LABEL_1229;
        servicerecord = (ServiceRecord)mPendingServices.get(j);
        processrecord1 = servicerecord.isolatedProc;
        if(processrecord == processrecord1)
            break; /* Loop/switch isn't completed */
        if(processrecord.uid != servicerecord.appInfo.uid)
            break MISSING_BLOCK_LABEL_963;
        flag10 = s.equals(servicerecord.processName);
        if(flag10)
            break; /* Loop/switch isn't completed */
_L22:
        j++;
        if(true) goto _L18; else goto _L17
_L6:
        byte0 = 1;
          goto _L19
_L8:
        s2 = processrecord.info.packageName;
          goto _L20
_L10:
        applicationinfo = processrecord.info;
          goto _L21
        exception;
        Slog.w("ActivityManager", "Exception thrown during bind!", exception);
        processrecord.resetPackageList();
        processrecord.unlinkDeathRecipient();
        startProcessLocked(processrecord, "bind fail", s);
        flag = false;
        continue; /* Loop/switch isn't completed */
_L16:
        flag11 = mMainStack.realStartActivityLocked(activityrecord, processrecord, true, true);
        if(flag11)
            flag8 = true;
          goto _L12
        exception4;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception in new application when starting activity ").append(activityrecord.intent.getComponent().flattenToShortString()).toString(), exception4);
        flag7 = true;
          goto _L12
_L14:
        mMainStack.ensureActivitiesVisibleLocked(activityrecord, null, s, 0);
          goto _L12
_L17:
        mPendingServices.remove(j);
        j--;
        realStartServiceLocked(servicerecord, processrecord);
        flag8 = true;
          goto _L22
        exception3;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception in new application when starting service ").append(servicerecord.shortName).toString(), exception3);
        flag7 = true;
        if(flag7 || !isPendingBroadcastProcessLocked(i))
            break MISSING_BLOCK_LABEL_1253;
        flag9 = sendPendingBroadcastsLocked(processrecord);
        flag8 = flag9;
_L25:
        if(!flag7 && mBackupTarget != null && mBackupTarget.appInfo.uid == processrecord.uid) {
            ensurePackageDexOpt(mBackupTarget.appInfo.packageName);
            Exception exception2;
            try {
                iapplicationthread.scheduleCreateBackupAgent(mBackupTarget.appInfo, compatibilityInfoForPackageLocked(mBackupTarget.appInfo), mBackupTarget.backupMode);
            }
            catch(Exception exception1) {
                Slog.w("ActivityManager", "Exception scheduling backup agent creation: ");
                exception1.printStackTrace();
            }
        }
        if(flag7) {
            handleAppDiedLocked(processrecord, false, true);
            flag = false;
        } else {
            if(!flag8)
                updateOomAdjLocked();
            flag = true;
        }
        if(true) goto _L24; else goto _L23
_L23:
        exception2;
        flag7 = true;
          goto _L25
    }

    private final void bringDownServiceLocked(ServiceRecord servicerecord, boolean flag) {
        if(flag || !servicerecord.startRequested) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(servicerecord.connections.size() > 0) {
            if(!flag) {
                for(Iterator iterator2 = servicerecord.connections.values().iterator(); iterator2.hasNext();) {
                    ArrayList arraylist1 = (ArrayList)iterator2.next();
                    int i1 = 0;
                    while(i1 < arraylist1.size())  {
                        if((1 & ((ConnectionRecord)arraylist1.get(i1)).flags) != 0)
                            continue; /* Loop/switch isn't completed */
                        i1++;
                    }
                }

            }
            for(Iterator iterator1 = servicerecord.connections.values().iterator(); iterator1.hasNext();) {
                ArrayList arraylist = (ArrayList)iterator1.next();
                int l = 0;
                while(l < arraylist.size())  {
                    ConnectionRecord connectionrecord = (ConnectionRecord)arraylist.get(l);
                    connectionrecord.serviceDead = true;
                    try {
                        connectionrecord.conn.connected(servicerecord.name, null);
                    }
                    catch(Exception exception3) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Failure disconnecting service ").append(servicerecord.name).append(" to connection ").append(((ConnectionRecord)arraylist.get(l)).conn.asBinder()).append(" (in ").append(((ConnectionRecord)arraylist.get(l)).binding.client.processName).append(")").toString(), exception3);
                    }
                    l++;
                }
            }

        }
        if(servicerecord.bindings.size() > 0 && servicerecord.app != null && servicerecord.app.thread != null) {
            Iterator iterator = servicerecord.bindings.values().iterator();
            do {
                if(!iterator.hasNext())
                    break;
                IntentBindRecord intentbindrecord = (IntentBindRecord)iterator.next();
                if(servicerecord.app != null && servicerecord.app.thread != null && intentbindrecord.hasBound)
                    try {
                        bumpServiceExecutingLocked(servicerecord, "bring down unbind");
                        updateOomAdjLocked(servicerecord.app);
                        intentbindrecord.hasBound = false;
                        servicerecord.app.thread.scheduleUnbindService(servicerecord, intentbindrecord.intent.getIntent());
                    }
                    catch(Exception exception2) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Exception when unbinding service ").append(servicerecord.shortName).toString(), exception2);
                        serviceDoneExecutingLocked(servicerecord, true);
                    }
            } while(true);
        }
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(System.identityHashCode(servicerecord));
        aobj[1] = servicerecord.shortName;
        int i;
        int j;
        if(servicerecord.app != null)
            i = servicerecord.app.pid;
        else
            i = -1;
        aobj[2] = Integer.valueOf(i);
        EventLog.writeEvent(30031, aobj);
        mServiceMap.removeServiceByName(servicerecord.name, servicerecord.userId);
        mServiceMap.removeServiceByIntent(servicerecord.intent, servicerecord.userId);
        servicerecord.totalRestartCount = 0;
        unscheduleServiceRestartLocked(servicerecord);
        j = mPendingServices.size();
        for(int k = 0; k < j; k++)
            if(mPendingServices.get(k) == servicerecord) {
                mPendingServices.remove(k);
                k--;
                j--;
            }

        servicerecord.cancelNotification();
        servicerecord.isForeground = false;
        servicerecord.foregroundId = 0;
        servicerecord.foregroundNoti = null;
        servicerecord.clearDeliveredStartsLocked();
        servicerecord.pendingStarts.clear();
        if(servicerecord.app != null) {
            synchronized(servicerecord.stats.getBatteryStats()) {
                servicerecord.stats.stopLaunchedLocked();
            }
            servicerecord.app.services.remove(servicerecord);
            if(servicerecord.app.thread != null) {
                try {
                    bumpServiceExecutingLocked(servicerecord, "stop");
                    mStoppingServices.add(servicerecord);
                    updateOomAdjLocked(servicerecord.app);
                    servicerecord.app.thread.scheduleStopService(servicerecord);
                }
                catch(Exception exception1) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Exception when stopping service ").append(servicerecord.shortName).toString(), exception1);
                    serviceDoneExecutingLocked(servicerecord, true);
                }
                updateServiceForegroundLocked(servicerecord.app, false);
            }
        }
        if(servicerecord.bindings.size() > 0)
            servicerecord.bindings.clear();
        if(servicerecord.restarter instanceof ServiceRestarter)
            ((ServiceRestarter)servicerecord.restarter).setService(null);
        if(true) goto _L1; else goto _L3
_L3:
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private final boolean bringUpServiceLocked(ServiceRecord servicerecord, int i, boolean flag) {
        if(servicerecord.app == null || servicerecord.app.thread == null) goto _L2; else goto _L1
_L1:
        boolean flag2;
        sendServiceArgsLocked(servicerecord, false);
        flag2 = true;
_L8:
        return flag2;
_L2:
        if(!flag && servicerecord.restartDelay > 0L) {
            flag2 = true;
            continue; /* Loop/switch isn't completed */
        }
        mRestartingServices.remove(servicerecord);
        boolean flag1;
        String s;
        ProcessRecord processrecord;
        ProcessRecord processrecord1;
        RemoteException remoteexception1;
        try {
            AppGlobals.getPackageManager().setPackageStoppedState(servicerecord.packageName, false, servicerecord.userId);
        }
        catch(RemoteException remoteexception) { }
        catch(IllegalArgumentException illegalargumentexception) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Failed trying to unstop package ").append(servicerecord.packageName).append(": ").append(illegalargumentexception).toString());
        }
        if((2 & servicerecord.serviceInfo.flags) != 0)
            flag1 = true;
        else
            flag1 = false;
        s = servicerecord.processName;
        if(flag1) goto _L4; else goto _L3
_L3:
        processrecord = getProcessRecordLocked(s, servicerecord.appInfo.uid);
        if(processrecord == null || processrecord.thread == null)
            break MISSING_BLOCK_LABEL_238;
        processrecord.addPackage(servicerecord.appInfo.packageName);
        realStartServiceLocked(servicerecord, processrecord);
        flag2 = true;
        continue; /* Loop/switch isn't completed */
        remoteexception1;
        Slog.w("ActivityManager", (new StringBuilder()).append("Exception when starting service ").append(servicerecord.shortName).toString(), remoteexception1);
_L6:
        if(processrecord != null)
            break MISSING_BLOCK_LABEL_374;
        processrecord1 = startProcessLocked(s, servicerecord.appInfo, true, i, "service", servicerecord.name, false, flag1);
        if(processrecord1 == null) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Unable to launch app ").append(servicerecord.appInfo.packageName).append("/").append(servicerecord.appInfo.uid).append(" for service ").append(servicerecord.intent.getIntent()).append(": process is bad").toString());
            bringDownServiceLocked(servicerecord, true);
            flag2 = false;
            continue; /* Loop/switch isn't completed */
        }
        break; /* Loop/switch isn't completed */
_L4:
        processrecord = servicerecord.isolatedProc;
        if(true) goto _L6; else goto _L5
_L5:
        if(flag1)
            servicerecord.isolatedProc = processrecord1;
        if(!mPendingServices.contains(servicerecord))
            mPendingServices.add(servicerecord);
        flag2 = true;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final int broadcastIntentLocked(ProcessRecord processrecord, String s, Intent intent, String s1, IIntentReceiver iintentreceiver, int i, String s2, 
            Bundle bundle, String s3, boolean flag, boolean flag1, int j, int k, int l) {
        Intent intent1;
        boolean flag2;
        intent1 = new Intent(intent);
        intent1.addFlags(16);
        if(iintentreceiver != null && !flag)
            Slog.w("ActivityManager", (new StringBuilder()).append("Broadcast ").append(intent1).append(" not ordered but result callback requested!").toString());
        flag2 = "android.intent.action.UID_REMOVED".equals(intent1.getAction());
        if(!"android.intent.action.PACKAGE_REMOVED".equals(intent1.getAction()) && !"android.intent.action.PACKAGE_CHANGED".equals(intent1.getAction()) && !"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(intent1.getAction()) && !flag2) goto _L2; else goto _L1
_L1:
        if(checkComponentPermission("android.permission.BROADCAST_PACKAGE_REMOVED", j, k, -1, true) != 0) goto _L4; else goto _L3
_L3:
        if(flag2) {
            Bundle bundle1 = intent1.getExtras();
            String s9;
            ProxyProperties proxyproperties;
            int i4;
            if(bundle1 != null)
                i4 = bundle1.getInt("android.intent.extra.UID");
            else
                i4 = -1;
            if(i4 >= 0)
                synchronized(mBatteryStatsService.getActiveStatistics()) {
                    batterystatsimpl.removeUidStatsLocked(i4);
                }
        } else
        if("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(intent1.getAction())) {
            String as3[] = intent1.getStringArrayExtra("android.intent.extra.changed_package_list");
            if(as3 != null && as3.length > 0) {
                int k3 = as3.length;
                for(int l3 = 0; l3 < k3; l3++)
                    forceStopPackageLocked(as3[l3], -1, false, true, true, false, l);

                sendPackageBroadcastLocked(1, as3);
            }
        } else {
            Uri uri = intent1.getData();
            if(uri != null) {
                String s5 = uri.getSchemeSpecificPart();
                if(s5 != null) {
                    if(!intent1.getBooleanExtra("android.intent.extra.DONT_KILL_APP", false))
                        forceStopPackageLocked(s5, intent1.getIntExtra("android.intent.extra.UID", -1), false, true, true, false, l);
                    if("android.intent.action.PACKAGE_REMOVED".equals(intent1.getAction())) {
                        String as[] = new String[1];
                        as[0] = s5;
                        sendPackageBroadcastLocked(0, as);
                    }
                }
            }
        }
_L9:
        if("android.intent.action.TIMEZONE_CHANGED".equals(intent1.getAction()))
            mHandler.sendEmptyMessage(13);
        if("android.intent.action.CLEAR_DNS_CACHE".equals(intent1.getAction()))
            mHandler.sendEmptyMessage(28);
        if("android.intent.action.PROXY_CHANGE".equals(intent1.getAction())) {
            proxyproperties = (ProxyProperties)intent1.getParcelableExtra("proxy");
            mHandler.sendMessage(mHandler.obtainMessage(29, proxyproperties));
        }
          goto _L5
_L11:
        if(!flag1) goto _L7; else goto _L6
_L6:
        byte byte0;
        String s4;
        RemoteException remoteexception2;
        String s10;
        Uri uri2;
        String s11;
        if(checkPermission("android.permission.BROADCAST_STICKY", j, k) != 0) {
            s9 = (new StringBuilder()).append("Permission Denial: broadcastIntent() requesting a sticky broadcast from pid=").append(j).append(", uid=").append(k).append(" requires ").append("android.permission.BROADCAST_STICKY").toString();
            Slog.w("ActivityManager", s9);
            throw new SecurityException(s9);
        }
label0:
        {
            if(s3 == null)
                break label0;
            Slog.w("ActivityManager", (new StringBuilder()).append("Can't broadcast sticky intent ").append(intent1).append(" and enforce permission ").append(s3).toString());
            byte0 = -1;
        }
          goto _L8
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        s4 = (new StringBuilder()).append("Permission Denial: ").append(intent1.getAction()).append(" broadcast from ").append(s).append(" (pid=").append(j).append(", uid=").append(k).append(")").append(" requires ").append("android.permission.BROADCAST_PACKAGE_REMOVED").toString();
        Slog.w("ActivityManager", s4);
        throw new SecurityException(s4);
_L2:
        if("android.intent.action.PACKAGE_ADDED".equals(intent1.getAction())) {
            uri2 = intent1.getData();
            if(uri2 != null) {
                s11 = uri2.getSchemeSpecificPart();
                if(s11 != null)
                    mCompatModePackages.handlePackageAddedLocked(s11, intent1.getBooleanExtra("android.intent.extra.REPLACING", false));
            }
        }
          goto _L9
_L5:
        if(k == 1000 || k == 1001 || k == 2000 || k == 0 || processrecord != null && processrecord.persistent) goto _L11; else goto _L10
_L10:
        if(!AppGlobals.getPackageManager().isProtectedBroadcast(intent1.getAction())) goto _L11; else goto _L12
_L12:
        s10 = (new StringBuilder()).append("Permission Denial: not allowed to send broadcast ").append(intent1.getAction()).append(" from pid=").append(j).append(", uid=").append(k).toString();
        Slog.w("ActivityManager", s10);
        throw new SecurityException(s10);
        remoteexception2;
        Slog.w("ActivityManager", "Remote exception", remoteexception2);
        byte0 = 0;
_L27:
        return byte0;
_L8:
        if(true) goto _L14; else goto _L13
_L13:
        ArrayList arraylist1;
        int i3;
        int j3;
        if(intent1.getComponent() != null)
            throw new SecurityException("Sticky broadcasts can't target a specific component");
        arraylist1 = (ArrayList)mStickyBroadcasts.get(intent1.getAction());
        if(arraylist1 == null) {
            arraylist1 = new ArrayList();
            mStickyBroadcasts.put(intent1.getAction(), arraylist1);
        }
        i3 = arraylist1.size();
        j3 = 0;
_L25:
        if(j3 >= i3) goto _L16; else goto _L15
_L15:
        if(!intent1.filterEquals((Intent)arraylist1.get(j3))) goto _L18; else goto _L17
_L17:
        Intent intent3 = new Intent(intent1);
        arraylist1.set(j3, intent3);
_L16:
        if(j3 >= i3) {
            Intent intent2 = new Intent(intent1);
            arraylist1.add(intent2);
        }
_L7:
        Object obj;
        List list;
        obj = null;
        list = null;
        if(intent1.getComponent() == null) goto _L20; else goto _L19
_L19:
        ActivityInfo activityinfo = AppGlobals.getPackageManager().getReceiverInfo(intent1.getComponent(), 1024, l);
        if(activityinfo == null) goto _L22; else goto _L21
_L21:
        ArrayList arraylist = new ArrayList();
        ResolveInfo resolveinfo1 = new ResolveInfo();
        if(!isSingleton(activityinfo.processName, activityinfo.applicationInfo)) goto _L24; else goto _L23
_L23:
        resolveinfo1.activityInfo = getActivityInfoForUser(activityinfo, 0);
_L26:
        arraylist.add(resolveinfo1);
        obj = arraylist;
          goto _L22
_L18:
        j3++;
          goto _L25
_L24:
        resolveinfo1.activityInfo = getActivityInfoForUser(activityinfo, l);
          goto _L26
        remoteexception1;
        obj = arraylist;
          goto _L22
_L20:
        if((0x40000000 & intent1.getFlags()) == 0)
            obj = AppGlobals.getPackageManager().queryIntentReceivers(intent1, s1, 1024, l);
        list2 = mReceiverResolver.queryIntent(intent1, s1, false, l);
        list = list2;
_L22:
        boolean flag3;
        int i1;
        int j1;
        if((0x20000000 & intent1.getFlags()) != 0)
            flag3 = true;
        else
            flag3 = false;
        if(list != null)
            i1 = list.size();
        else
            i1 = 0;
        if(!flag && i1 > 0) {
            BroadcastQueue broadcastqueue1 = broadcastQueueForIntent(intent1);
            BroadcastRecord broadcastrecord1 = new BroadcastRecord(broadcastqueue1, intent1, processrecord, s, j, k, s3, list, iintentreceiver, i, s2, bundle, flag, flag1, false);
            Uri uri1;
            String as2[];
            int i2;
            String s7;
            int k2;
            int l2;
            String s8;
            boolean flag5;
            List list2;
            RemoteException remoteexception1;
            if(flag3 && broadcastqueue1.replaceParallelBroadcastLocked(broadcastrecord1))
                flag5 = true;
            else
                flag5 = false;
            if(!flag5) {
                broadcastqueue1.enqueueParallelBroadcastLocked(broadcastrecord1);
                broadcastqueue1.scheduleBroadcastsLocked();
            }
            list = null;
            i1 = 0;
        }
        j1 = 0;
        if(obj != null) {
            String as1[] = null;
            if("android.intent.action.PACKAGE_ADDED".equals(intent1.getAction()) || "android.intent.action.PACKAGE_RESTARTED".equals(intent1.getAction()) || "android.intent.action.PACKAGE_DATA_CLEARED".equals(intent1.getAction())) {
                uri1 = intent1.getData();
                if(uri1 != null) {
                    s8 = uri1.getSchemeSpecificPart();
                    if(s8 != null) {
                        as1 = new String[1];
                        as1[0] = s8;
                    }
                }
            } else
            if("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(intent1.getAction()))
                as1 = intent1.getStringArrayExtra("android.intent.extra.changed_package_list");
            if(as1 != null && as1.length > 0) {
                as2 = as1;
                i2 = as2.length;
label1:
                for(int j2 = 0; j2 < i2; j2++) {
                    s7 = as2[j2];
                    if(s7 == null)
                        continue;
                    k2 = ((List) (obj)).size();
                    l2 = 0;
                    do {
                        if(l2 >= k2)
                            continue label1;
                        if(((ComponentInfo) (((ResolveInfo)((List) (obj)).get(l2)).activityInfo)).packageName.equals(s7)) {
                            ((List) (obj)).remove(l2);
                            l2--;
                            k2--;
                        }
                        l2++;
                    } while(true);
                }

            }
            int k1;
            int l1;
            ResolveInfo resolveinfo;
            BroadcastFilter broadcastfilter;
            if(obj != null)
                k1 = ((List) (obj)).size();
            else
                k1 = 0;
            l1 = 0;
            resolveinfo = null;
            broadcastfilter = null;
            while(l1 < k1 && j1 < i1)  {
                if(resolveinfo == null)
                    resolveinfo = (ResolveInfo)((List) (obj)).get(l1);
                if(broadcastfilter == null)
                    broadcastfilter = (BroadcastFilter)list.get(j1);
                if(broadcastfilter.getPriority() >= resolveinfo.priority) {
                    ((List) (obj)).add(l1, broadcastfilter);
                    j1++;
                    broadcastfilter = null;
                    l1++;
                    k1++;
                } else {
                    l1++;
                    resolveinfo = null;
                }
            }
        }
        for(; j1 < i1; j1++) {
            if(obj == null)
                obj = new ArrayList();
            Object obj1 = list.get(j1);
            ((List) (obj)).add(obj1);
        }

        if(obj != null && ((List) (obj)).size() > 0 || iintentreceiver != null) {
            BroadcastQueue broadcastqueue = broadcastQueueForIntent(intent1);
            List list1 = getRunningAppProcesses();
            String s6 = intent1.getAction();
            ExtraActivityManagerService.adjustMediaButtonReceivers(((List) (obj)), list1, s6);
            BroadcastRecord broadcastrecord = new BroadcastRecord(broadcastqueue, intent1, processrecord, s, j, k, s3, ((List) (obj)), iintentreceiver, i, s2, bundle, flag, flag1, false);
            boolean flag4;
            if(flag3 && broadcastqueue.replaceOrderedBroadcastLocked(broadcastrecord))
                flag4 = true;
            else
                flag4 = false;
            if(!flag4) {
                broadcastqueue.enqueueOrderedBroadcastLocked(broadcastrecord);
                broadcastqueue.scheduleBroadcastsLocked();
            }
        }
        byte0 = 0;
_L14:
        if(true) goto _L27; else goto _L20
        RemoteException remoteexception;
        remoteexception;
          goto _L22
    }

    private static String buildOomTag(String s, String s1, int i, int j) {
        if(i == j) {
            if(s1 != null)
                s = (new StringBuilder()).append(s).append("  ").toString();
        } else {
            s = (new StringBuilder()).append(s).append("+").append(Integer.toString(i - j)).toString();
        }
        return s;
    }

    private final void bumpServiceExecutingLocked(ServiceRecord servicerecord, String s) {
        long l = SystemClock.uptimeMillis();
        if(servicerecord.executeNesting == 0 && servicerecord.app != null) {
            if(servicerecord.app.executingServices.size() == 0) {
                Message message = mHandler.obtainMessage(12);
                message.obj = servicerecord.app;
                mHandler.sendMessageAtTime(message, 20000L + l);
            }
            servicerecord.app.executingServices.add(servicerecord);
        }
        servicerecord.executeNesting = 1 + servicerecord.executeNesting;
        servicerecord.executingStart = l;
    }

    private final boolean canGcNowLocked() {
        boolean flag = false;
        BroadcastQueue abroadcastqueue[] = mBroadcastQueues;
        int i = abroadcastqueue.length;
        for(int j = 0; j < i; j++) {
            BroadcastQueue broadcastqueue = abroadcastqueue[j];
            if(broadcastqueue.mParallelBroadcasts.size() != 0 || broadcastqueue.mOrderedBroadcasts.size() != 0)
                flag = true;
        }

        boolean flag1;
        if(!flag && (mSleeping || mMainStack.mResumedActivity != null && mMainStack.mResumedActivity.idle))
            flag1 = true;
        else
            flag1 = false;
        return flag1;
    }

    private final String checkContentProviderPermissionLocked(ProviderInfo providerinfo, ProcessRecord processrecord) {
        String s;
        int i;
        int j;
        s = null;
        if(processrecord != null)
            i = processrecord.pid;
        else
            i = Binder.getCallingPid();
        if(processrecord != null)
            j = processrecord.uid;
        else
            j = Binder.getCallingUid();
        break MISSING_BLOCK_LABEL_22;
_L2:
        do
            return s;
        while(checkComponentPermission(providerinfo.readPermission, i, j, providerinfo.applicationInfo.uid, providerinfo.exported) == 0 || checkComponentPermission(providerinfo.writePermission, i, j, providerinfo.applicationInfo.uid, providerinfo.exported) == 0);
label0:
        {
            PathPermission apathpermission[] = providerinfo.pathPermissions;
            if(apathpermission == null)
                break label0;
            int k = apathpermission.length;
            PathPermission pathpermission;
            do {
                if(k <= 0)
                    break label0;
                k--;
                pathpermission = apathpermission[k];
            } while(checkComponentPermission(pathpermission.getReadPermission(), i, j, providerinfo.applicationInfo.uid, providerinfo.exported) != 0 && checkComponentPermission(pathpermission.getWritePermission(), i, j, providerinfo.applicationInfo.uid, providerinfo.exported) != 0);
            continue; /* Loop/switch isn't completed */
        }
label1:
        {
            HashMap hashmap = (HashMap)mGrantedUriPermissions.get(j);
            if(hashmap == null)
                break label1;
            Iterator iterator = hashmap.entrySet().iterator();
            do
                if(!iterator.hasNext())
                    break label1;
            while(!((Uri)((java.util.Map.Entry)iterator.next()).getKey()).getAuthority().equals(providerinfo.authority));
            continue; /* Loop/switch isn't completed */
        }
        if(providerinfo.exported)
            break; /* Loop/switch isn't completed */
        StringBuilder stringbuilder1 = (new StringBuilder()).append("Permission Denial: opening provider ").append(((ComponentInfo) (providerinfo)).name).append(" from ");
        if(processrecord == null)
            processrecord = "(null)";
        s = stringbuilder1.append(processrecord).append(" (pid=").append(i).append(", uid=").append(j).append(") that is not exported from uid ").append(providerinfo.applicationInfo.uid).toString();
_L3:
        Slog.w("ActivityManager", s);
        if(true) goto _L2; else goto _L1
_L1:
        StringBuilder stringbuilder = (new StringBuilder()).append("Permission Denial: opening provider ").append(((ComponentInfo) (providerinfo)).name).append(" from ");
        if(processrecord == null)
            processrecord = "(null)";
        s = stringbuilder.append(processrecord).append(" (pid=").append(i).append(", uid=").append(j).append(") requires ").append(providerinfo.readPermission).append(" or ").append(providerinfo.writePermission).toString();
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private final boolean checkHoldingPermissionsLocked(IPackageManager ipackagemanager, ProviderInfo providerinfo, Uri uri, int i, int j) {
        boolean flag2;
        if(providerinfo.applicationInfo.uid == i) {
            flag2 = true;
        } else {
label0:
            {
                if(providerinfo.exported)
                    break label0;
                flag2 = false;
            }
        }
_L18:
        return flag2;
        boolean flag;
        boolean flag1;
        boolean flag3;
        boolean flag4;
        int k;
        PathPermission apathpermission[];
        String s;
        PathPermission pathpermission;
        String s1;
        int l;
        String s2;
        if((j & 1) == 0)
            flag = true;
        else
            flag = false;
        if((j & 2) == 0)
            flag1 = true;
        else
            flag1 = false;
        if(flag)
            break MISSING_BLOCK_LABEL_81;
        if(providerinfo.readPermission != null && ipackagemanager.checkUidPermission(providerinfo.readPermission, i) == 0)
            flag = true;
        if(!flag1 && providerinfo.writePermission != null && ipackagemanager.checkUidPermission(providerinfo.writePermission, i) == 0)
            flag1 = true;
        if(providerinfo.readPermission != null) goto _L2; else goto _L1
_L1:
        flag3 = true;
_L16:
        if(providerinfo.writePermission != null) goto _L4; else goto _L3
_L3:
        flag4 = true;
_L17:
        apathpermission = providerinfo.pathPermissions;
        if(apathpermission == null) goto _L6; else goto _L5
_L5:
        s = uri.getPath();
        k = apathpermission.length;
          goto _L7
_L19:
        k--;
        pathpermission = apathpermission[k];
        if(!pathpermission.match(s)) goto _L7; else goto _L8
_L8:
        if(flag) goto _L10; else goto _L9
_L9:
        s2 = pathpermission.getReadPermission();
        if(s2 == null) goto _L10; else goto _L11
_L11:
        if(ipackagemanager.checkUidPermission(s2, i) != 0) goto _L13; else goto _L12
_L12:
        flag = true;
_L10:
        if(flag1) goto _L7; else goto _L14
_L14:
        s1 = pathpermission.getWritePermission();
        if(s1 == null) goto _L7; else goto _L15
_L15:
        l = ipackagemanager.checkUidPermission(s1, i);
        if(l == 0)
            flag1 = true;
        else
            flag4 = false;
          goto _L7
_L2:
        flag3 = false;
          goto _L16
_L4:
        flag4 = false;
          goto _L17
_L13:
        flag3 = false;
          goto _L10
_L6:
        if(flag3)
            flag = true;
        if(flag4)
            flag1 = true;
        RemoteException remoteexception;
        if(flag && flag1)
            flag2 = true;
        else
            flag2 = false;
          goto _L18
        remoteexception;
        flag2 = false;
          goto _L18
_L7:
        if(k <= 0 || flag && flag1) goto _L6; else goto _L19
    }

    private final boolean checkUriPermissionLocked(Uri uri, int i, int j) {
        boolean flag = true;
        if(i != 0) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        HashMap hashmap = (HashMap)mGrantedUriPermissions.get(i);
        if(hashmap == null) {
            flag = false;
        } else {
            UriPermission uripermission = (UriPermission)hashmap.get(uri);
            if(uripermission == null)
                flag = false;
            else
            if((j & uripermission.modeFlags) != j)
                flag = false;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void checkValidCaller(int i, int j) {
        if(UserId.getUserId(i) == j || i == 1000 || i == 0)
            return;
        else
            throw new SecurityException((new StringBuilder()).append("Caller uid=").append(i).append(" is not privileged to communicate with user=").append(j).toString());
    }

    private final void cleanUpApplicationRecordLocked(ProcessRecord processrecord, boolean flag, boolean flag1, int i) {
        if(i >= 0)
            mLruProcesses.remove(i);
        mProcessesToGc.remove(processrecord);
        if(processrecord.crashDialog != null) {
            processrecord.crashDialog.dismiss();
            processrecord.crashDialog = null;
        }
        if(processrecord.anrDialog != null) {
            processrecord.anrDialog.dismiss();
            processrecord.anrDialog = null;
        }
        if(processrecord.waitDialog != null) {
            processrecord.waitDialog.dismiss();
            processrecord.waitDialog = null;
        }
        processrecord.crashing = false;
        processrecord.notResponding = false;
        processrecord.resetPackageList();
        processrecord.unlinkDeathRecipient();
        processrecord.thread = null;
        processrecord.forcingToForeground = null;
        processrecord.foregroundServices = false;
        processrecord.foregroundActivities = false;
        processrecord.hasShownUi = false;
        processrecord.hasAboveClient = false;
        killServicesLocked(processrecord, flag1);
        boolean flag2 = false;
        if(!processrecord.pubProviders.isEmpty()) {
            Iterator iterator1 = processrecord.pubProviders.values().iterator();
            while(iterator1.hasNext())  {
                ContentProviderRecord contentproviderrecord = (ContentProviderRecord)iterator1.next();
                boolean flag3;
                if(processrecord.bad || !flag1)
                    flag3 = true;
                else
                    flag3 = false;
                if(removeDyingProviderLocked(processrecord, contentproviderrecord, flag3) || flag3)
                    flag2 = true;
                contentproviderrecord.provider = null;
                contentproviderrecord.proc = null;
            }
            processrecord.pubProviders.clear();
        }
        if(checkAppInLaunchingProvidersLocked(processrecord, false))
            flag2 = true;
        if(!processrecord.conProviders.isEmpty()) {
            for(int k = 0; k < processrecord.conProviders.size(); k++) {
                ContentProviderConnection contentproviderconnection = (ContentProviderConnection)processrecord.conProviders.get(k);
                contentproviderconnection.provider.connections.remove(contentproviderconnection);
            }

            processrecord.conProviders.clear();
        }
        skipCurrentReceiverLocked(processrecord);
        if(processrecord.receivers.size() > 0) {
            for(Iterator iterator = processrecord.receivers.iterator(); iterator.hasNext(); removeReceiverLocked((ReceiverList)iterator.next()));
            processrecord.receivers.clear();
        }
        int j;
        if(mBackupTarget != null && processrecord.pid == mBackupTarget.app.pid)
            try {
                android.app.backup.IBackupManager.Stub.asInterface(ServiceManager.getService("backup")).agentDisconnected(processrecord.info.packageName);
            }
            catch(RemoteException remoteexception) { }
        for(j = -1 + mPendingProcessChanges.size(); j >= 0; j--) {
            ProcessChangeItem processchangeitem = (ProcessChangeItem)mPendingProcessChanges.get(j);
            if(processchangeitem.pid == processrecord.pid) {
                mPendingProcessChanges.remove(j);
                mAvailProcessChanges.add(processchangeitem);
            }
        }

        mHandler.obtainMessage(32, processrecord.pid, processrecord.info.uid, null).sendToTarget();
        if(!flag) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(processrecord.persistent && !processrecord.isolated) goto _L4; else goto _L3
_L3:
        mProcessNames.remove(processrecord.processName, processrecord.uid);
        mIsolatedProcesses.remove(processrecord.uid);
        if(mHeavyWeightProcess == processrecord) {
            mHeavyWeightProcess = null;
            mHandler.sendEmptyMessage(25);
        }
_L6:
        mProcessesOnHold.remove(processrecord);
        if(processrecord == mHomeProcess)
            mHomeProcess = null;
        if(processrecord == mPreviousProcess)
            mPreviousProcess = null;
        if(!flag2 || processrecord.isolated)
            break; /* Loop/switch isn't completed */
        mProcessNames.put(processrecord.processName, processrecord.uid, processrecord);
        startProcessLocked(processrecord, "restart", processrecord.processName);
        continue; /* Loop/switch isn't completed */
_L4:
        if(!processrecord.removed && mPersistentStartingProcesses.indexOf(processrecord) < 0) {
            mPersistentStartingProcesses.add(processrecord);
            flag2 = true;
        }
        if(true) goto _L6; else goto _L5
_L5:
        if(processrecord.pid > 0 && processrecord.pid != MY_PID) {
            synchronized(mPidsSelfLocked) {
                mPidsSelfLocked.remove(processrecord.pid);
                mHandler.removeMessages(20, processrecord);
            }
            processrecord.setPid(0);
        }
        if(true) goto _L1; else goto _L7
_L7:
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }


// JavaClassFileOutputException: Prev chain is broken

    private void clearProfilerLocked() {
        if(mProfileFd != null)
            try {
                mProfileFd.close();
            }
            catch(IOException ioexception) { }
        mProfileApp = null;
        mProfileProc = null;
        mProfileFile = null;
        mProfileType = 0;
        mAutoStopProfiler = false;
    }

    private void comeOutOfSleepIfNeededLocked() {
        if(!mWentToSleep && !mLockScreenShown && mSleeping) {
            mSleeping = false;
            mMainStack.awakeFromSleepingLocked();
            mMainStack.resumeTopActivityLocked(null);
        }
    }

    private final int computeOomAdjLocked(ProcessRecord processrecord, int i, ProcessRecord processrecord1, boolean flag, boolean flag1) {
        int l1;
        if(mAdjSeq == processrecord.adjSeq) {
            if(!flag && processrecord.hidden) {
                processrecord.nonStoppingAdj = i;
                processrecord.curRawAdj = i;
                processrecord.curAdj = i;
            }
            l1 = processrecord.curRawAdj;
        } else {
label0:
            {
                if(processrecord.thread != null)
                    break label0;
                processrecord.adjSeq = mAdjSeq;
                processrecord.curSchedGroup = 0;
                l1 = 15;
                processrecord.curAdj = l1;
            }
        }
_L3:
        return l1;
        int j;
        processrecord.adjTypeCode = 0;
        processrecord.adjSource = null;
        processrecord.adjTarget = null;
        processrecord.empty = false;
        processrecord.hidden = false;
        j = processrecord.activities.size();
        if(processrecord.maxAdj > 0)
            break MISSING_BLOCK_LABEL_247;
        processrecord.adjType = "fixed";
        processrecord.adjSeq = mAdjSeq;
        int i6 = processrecord.maxAdj;
        processrecord.nonStoppingAdj = i6;
        processrecord.curRawAdj = i6;
        processrecord.foregroundActivities = false;
        processrecord.keeping = true;
        processrecord.curSchedGroup = -1;
        processrecord.systemNoUi = true;
        if(processrecord != processrecord1) goto _L2; else goto _L1
_L1:
        processrecord.systemNoUi = false;
_L5:
        l1 = processrecord.maxAdj;
        processrecord.curAdj = l1;
          goto _L3
_L2:
        if(j <= 0) goto _L5; else goto _L4
_L4:
        int j6 = 0;
_L6:
        if(j6 < j) {
label1:
            {
                if(!((ActivityRecord)processrecord.activities.get(j6)).visible)
                    break label1;
                processrecord.systemNoUi = false;
            }
        }
          goto _L5
        j6++;
          goto _L6
        boolean flag2;
        boolean flag3;
        int k;
        int l;
        boolean flag4;
        int l5;
        ActivityRecord activityrecord1;
        processrecord.keeping = false;
        processrecord.systemNoUi = false;
        flag2 = false;
        flag3 = false;
        Iterator iterator1;
        Iterator iterator2;
        ArrayList arraylist;
        int k4;
        int i5;
        if(processrecord == processrecord1 || processrecord == mHomeProcess && android.provider.Settings.System.getInt(mContext.getContentResolver(), "keep_launcher_in_memory", 1) != 0) {
            k = 0;
            l = -1;
            processrecord.adjType = "top-activity";
            flag2 = true;
            flag3 = true;
        } else
        if(processrecord.instrumentationClass != null) {
            k = 0;
            l = -1;
            processrecord.adjType = "instrumentation";
            flag3 = true;
        } else {
            BroadcastQueue broadcastqueue = isReceivingBroadcast(processrecord);
            if(broadcastqueue != null) {
                k = 0;
                if(broadcastqueue == mFgBroadcastQueue)
                    l = -1;
                else
                    l = 0;
                processrecord.adjType = "broadcast";
            } else
            if(processrecord.executingServices.size() > 0) {
                k = 0;
                l = -1;
                processrecord.adjType = "exec-service";
            } else
            if(j > 0) {
                k = i;
                l = 0;
                processrecord.hidden = true;
                processrecord.adjType = "bg-activities";
            } else {
                k = i;
                l = 0;
                processrecord.hidden = true;
                processrecord.empty = true;
                processrecord.adjType = "bg-empty";
            }
        }
        flag4 = false;
        if(flag2 || j <= 0) goto _L8; else goto _L7
_L7:
        l5 = 0;
_L13:
        if(l5 >= j) goto _L8; else goto _L9
_L9:
        activityrecord1 = (ActivityRecord)processrecord.activities.get(l5);
        if(!activityrecord1.visible) goto _L11; else goto _L10
_L10:
        if(k > 1) {
            k = 1;
            processrecord.adjType = "visible";
        }
        l = -1;
        processrecord.hidden = false;
        flag2 = true;
_L8:
        long l4;
        if(k > 2)
            if(processrecord.foregroundServices) {
                k = 2;
                processrecord.hidden = false;
                processrecord.adjType = "foreground-service";
                l = -1;
            } else
            if(processrecord.forcingToForeground != null) {
                k = 2;
                processrecord.hidden = false;
                processrecord.adjType = "force-foreground";
                processrecord.adjSource = processrecord.forcingToForeground;
                l = -1;
            }
        if(processrecord.foregroundServices)
            flag3 = true;
        if(k > 3 && processrecord == mHeavyWeightProcess) {
            k = 3;
            l = 0;
            processrecord.hidden = false;
            processrecord.adjType = "heavy";
        }
        if(k > 6 && processrecord == mHomeProcess) {
            k = 6;
            l = 0;
            processrecord.hidden = false;
            processrecord.adjType = "home";
        }
        if(k > 7 && processrecord == mPreviousProcess && processrecord.activities.size() > 0) {
            k = 7;
            l = 0;
            processrecord.hidden = false;
            processrecord.adjType = "previous";
        }
        processrecord.adjSeq = mAdjSeq;
        processrecord.nonStoppingAdj = k;
        processrecord.curRawAdj = k;
        if(mBackupTarget != null && processrecord == mBackupTarget.app && k > 4) {
            k = 4;
            processrecord.adjType = "backup";
            processrecord.hidden = false;
        }
        if(processrecord.services.size() == 0 || k <= 0 && l != 0)
            break MISSING_BLOCK_LABEL_1633;
        l4 = SystemClock.uptimeMillis();
        iterator1 = processrecord.services.iterator();
label2:
        do {
            ServiceRecord servicerecord;
            do {
                if(!iterator1.hasNext() || k <= 0)
                    break label2;
                servicerecord = (ServiceRecord)iterator1.next();
                if(!servicerecord.startRequested)
                    continue;
                if(processrecord.hasShownUi && processrecord != mHomeProcess) {
                    if(k > 5)
                        processrecord.adjType = "started-bg-ui-services";
                } else {
                    if(l4 < 0x1b7740L + servicerecord.lastActivity && k > 5) {
                        k = 5;
                        processrecord.adjType = "started-services";
                        processrecord.hidden = false;
                    }
                    if(k > 5)
                        processrecord.adjType = "started-bg-services";
                }
                processrecord.keeping = true;
            } while(servicerecord.connections.size() <= 0 || k <= 0 && l != 0);
            iterator2 = servicerecord.connections.values().iterator();
label3:
            do {
                if(!iterator2.hasNext() || k <= 0)
                    continue label3;
                arraylist = (ArrayList)iterator2.next();
                k4 = 0;
                do {
                    i5 = arraylist.size();
                    if(k4 >= i5 || k <= 0)
                        continue label3;
                    ConnectionRecord connectionrecord = (ConnectionRecord)arraylist.get(k4);
                    if(connectionrecord.binding.client != processrecord) {
                        if((0x20 & connectionrecord.flags) == 0) {
                            ProcessRecord processrecord3 = connectionrecord.binding.client;
                            k;
                            int j5 = i;
                            ActivityRecord activityrecord;
                            int k5;
                            String s;
                            if(j5 > processrecord3.hiddenAdj)
                                if(processrecord3.hiddenAdj >= 1)
                                    j5 = processrecord3.hiddenAdj;
                                else
                                    j5 = 1;
                            k5 = computeOomAdjLocked(processrecord3, j5, processrecord1, true, flag1);
                            s = null;
                            if((0x10 & connectionrecord.flags) != 0)
                                if(processrecord.hasShownUi && processrecord != mHomeProcess) {
                                    if(k > k5)
                                        s = "bound-bg-ui-services";
                                    processrecord.hidden = false;
                                    k5 = k;
                                } else
                                if(l4 >= 0x1b7740L + servicerecord.lastActivity) {
                                    if(k > k5)
                                        s = "bound-bg-services";
                                    k5 = k;
                                }
                            if(k > k5)
                                if(processrecord.hasShownUi && processrecord != mHomeProcess && k5 > 2) {
                                    s = "bound-bg-ui-services";
                                } else {
                                    if((0x48 & connectionrecord.flags) != 0)
                                        k = k5;
                                    else
                                    if((0x40000000 & connectionrecord.flags) != 0 && k5 < 2 && k > 2)
                                        k = 2;
                                    else
                                    if(k5 > 1) {
                                        k = k5;
                                    } else {
                                        processrecord.pendingUiClean = true;
                                        if(k > 1)
                                            k = 1;
                                    }
                                    if(!processrecord3.hidden)
                                        processrecord.hidden = false;
                                    if(processrecord3.keeping)
                                        processrecord.keeping = true;
                                    s = "service";
                                }
                            if(s != null) {
                                processrecord.adjType = s;
                                processrecord.adjTypeCode = 2;
                                processrecord.adjSource = connectionrecord.binding.client;
                                processrecord.adjSourceOom = k5;
                                processrecord.adjTarget = servicerecord.name;
                            }
                            if((4 & connectionrecord.flags) == 0 && processrecord3.curSchedGroup == -1)
                                l = -1;
                        }
                        if((0x80 & connectionrecord.flags) != 0) {
                            activityrecord = connectionrecord.activity;
                            if(activityrecord != null && k > 0 && (activityrecord.visible || activityrecord.state == ActivityStack.ActivityState.RESUMED || activityrecord.state == ActivityStack.ActivityState.PAUSING)) {
                                k = 0;
                                if((4 & connectionrecord.flags) == 0)
                                    l = -1;
                                processrecord.hidden = false;
                                processrecord.adjType = "service";
                                processrecord.adjTypeCode = 2;
                                processrecord.adjSource = activityrecord;
                                processrecord.adjSourceOom = 0;
                                processrecord.adjTarget = servicerecord.name;
                            }
                        }
                    }
                    k4++;
                } while(true);
            } while(true);
        } while(true);
        break; /* Loop/switch isn't completed */
_L11:
        if(activityrecord1.state == ActivityStack.ActivityState.PAUSING || activityrecord1.state == ActivityStack.ActivityState.PAUSED) {
            if(k > 2) {
                k = 2;
                processrecord.adjType = "pausing";
            }
            processrecord.hidden = false;
            flag2 = true;
        } else
        if(activityrecord1.state == ActivityStack.ActivityState.STOPPING) {
            processrecord.hidden = false;
            flag2 = true;
            flag4 = true;
        }
        l5++;
        if(true) goto _L13; else goto _L12
_L12:
        if(k > i) {
            k = i;
            processrecord.hidden = false;
            processrecord.adjType = "bg-services";
        }
        int i2;
        ProcessChangeItem processchangeitem;
        int k1;
label4:
        {
            if(processrecord.pubProviders.size() != 0 && (k > 0 || l == 0)) {
                Iterator iterator = processrecord.pubProviders.values().iterator();
                do {
                    if(!iterator.hasNext() || k <= 0 && l != 0)
                        break;
                    ContentProviderRecord contentproviderrecord = (ContentProviderRecord)iterator.next();
                    int l3 = -1 + contentproviderrecord.connections.size();
                    while(l3 >= 0 && (k > 0 || l == 0))  {
                        ProcessRecord processrecord2 = ((ContentProviderConnection)contentproviderrecord.connections.get(l3)).client;
                        if(processrecord2 != processrecord) {
                            int i4 = i;
                            int j4;
                            if(i4 > processrecord2.hiddenAdj)
                                if(processrecord2.hiddenAdj > 0)
                                    i4 = processrecord2.hiddenAdj;
                                else
                                    i4 = 0;
                            j4 = computeOomAdjLocked(processrecord2, i4, processrecord1, true, flag1);
                            if(k > j4) {
                                if(processrecord.hasShownUi && processrecord != mHomeProcess && j4 > 2) {
                                    processrecord.adjType = "bg-ui-provider";
                                } else {
                                    if(j4 > 0)
                                        k = j4;
                                    else
                                        k = 0;
                                    processrecord.adjType = "provider";
                                }
                                if(!processrecord2.hidden)
                                    processrecord.hidden = false;
                                if(processrecord2.keeping)
                                    processrecord.keeping = true;
                                processrecord.adjTypeCode = 1;
                                processrecord.adjSource = processrecord2;
                                processrecord.adjSourceOom = j4;
                                processrecord.adjTarget = contentproviderrecord.name;
                            }
                            if(processrecord2.curSchedGroup == -1)
                                l = -1;
                        }
                        l3--;
                    }
                    if(contentproviderrecord.hasExternalProcessHandles() && k > 0) {
                        k = 0;
                        l = -1;
                        processrecord.hidden = false;
                        processrecord.keeping = true;
                        processrecord.adjType = "provider";
                        processrecord.adjTarget = contentproviderrecord.name;
                    }
                } while(true);
            }
            int i1;
            if(k == 5) {
                if(flag1) {
                    int j1;
                    boolean flag5;
                    int j2;
                    int k2;
                    boolean flag6;
                    int l2;
                    int i3;
                    int j3;
                    int k3;
                    boolean flag7;
                    if(mNewNumServiceProcs > mNumServiceProcs / 3)
                        flag7 = true;
                    else
                        flag7 = false;
                    processrecord.serviceb = flag7;
                    mNewNumServiceProcs = 1 + mNewNumServiceProcs;
                }
                if(processrecord.serviceb)
                    k = 8;
            } else {
                processrecord.serviceb = false;
            }
            processrecord.nonStoppingAdj = k;
            if(flag4 && k > 2) {
                k = 2;
                processrecord.adjType = "stopping";
            }
            processrecord.curRawAdj = k;
            if(k > processrecord.maxAdj) {
                k = processrecord.maxAdj;
                if(processrecord.maxAdj <= 2)
                    l = -1;
            }
            if(k < ProcessList.HIDDEN_APP_MIN_ADJ)
                processrecord.keeping = true;
            if(processrecord.hasAboveClient && k >= 0)
                if(k < 1)
                    k = 1;
                else
                if(k < 2)
                    k = 2;
                else
                if(k < ProcessList.HIDDEN_APP_MIN_ADJ)
                    k = ProcessList.HIDDEN_APP_MIN_ADJ;
                else
                if(k < 15)
                    k++;
            i1 = processrecord.memImportance;
            if(i1 != 0 && k == processrecord.curAdj) {
                k3 = processrecord.curSchedGroup;
                if(l == k3)
                    break label4;
            }
            processrecord.curAdj = k;
            processrecord.curSchedGroup = l;
            if(!flag3)
                i1 = 400;
            else
            if(k >= ProcessList.HIDDEN_APP_MIN_ADJ)
                i1 = 400;
            else
            if(k >= 8)
                i1 = 300;
            else
            if(k >= 6)
                i1 = 400;
            else
            if(k >= 5)
                i1 = 300;
            else
            if(k >= 3)
                i1 = 170;
            else
            if(k >= 2)
                i1 = 130;
            else
            if(k >= 1)
                i1 = 200;
            else
            if(k >= 0)
                i1 = 100;
            else
                i1 = 50;
        }
        j1 = processrecord.memImportance;
        if(i1 != j1)
            k1 = 2;
        else
            k1 = 0;
        flag5 = processrecord.foregroundActivities;
        if(flag2 != flag5)
            k1 |= 1;
        if(k1 == 0) goto _L15; else goto _L14
_L14:
        processrecord.memImportance = i1;
        processrecord.foregroundActivities = flag2;
        i2 = -1 + mPendingProcessChanges.size();
        processchangeitem = null;
_L16:
        if(i2 >= 0) {
            processchangeitem = (ProcessChangeItem)mPendingProcessChanges.get(i2);
            if(processchangeitem.pid != processrecord.pid)
                break MISSING_BLOCK_LABEL_2619;
        }
        if(i2 < 0) {
            l2 = mAvailProcessChanges.size();
            if(l2 > 0)
                processchangeitem = (ProcessChangeItem)mAvailProcessChanges.remove(l2 - 1);
            else
                processchangeitem = new ProcessChangeItem();
            processchangeitem.changes = 0;
            i3 = processrecord.pid;
            processchangeitem.pid = i3;
            j3 = processrecord.info.uid;
            processchangeitem.uid = j3;
            if(mPendingProcessChanges.size() == 0)
                mHandler.obtainMessage(31).sendToTarget();
            mPendingProcessChanges.add(processchangeitem);
        }
        j2 = k1 | processchangeitem.changes;
        processchangeitem.changes = j2;
        k2 = i1;
        processchangeitem.importance = k2;
        flag6 = flag2;
        processchangeitem.foregroundActivities = flag6;
_L15:
        l1 = processrecord.curRawAdj;
          goto _L3
        i2--;
          goto _L16
    }

    private void crashApplication(ProcessRecord processrecord, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        long l;
        String s;
        String s1;
        String s2;
        l = System.currentTimeMillis();
        s = crashinfo.exceptionClassName;
        s1 = crashinfo.exceptionMessage;
        s2 = crashinfo.stackTrace;
        if(s == null || s1 == null) goto _L2; else goto _L1
_L1:
        s1 = (new StringBuilder()).append(s).append(": ").append(s1).toString();
_L17:
        AppErrorResult apperrorresult = new AppErrorResult();
        this;
        JVM INSTR monitorenter ;
        IActivityController iactivitycontroller = mController;
        if(iactivitycontroller == null) goto _L4; else goto _L3
_L3:
        if(processrecord == null) goto _L6; else goto _L5
_L5:
        String s3 = processrecord.processName;
_L10:
        if(processrecord == null) goto _L8; else goto _L7
_L7:
        int k = processrecord.pid;
_L11:
        if(mController.appCrashed(s3, k, s, s1, l, crashinfo.stackTrace)) goto _L4; else goto _L9
_L9:
        Slog.w("ActivityManager", (new StringBuilder()).append("Force-killing crashed app ").append(s3).append(" at watcher's request").toString());
        Process.killProcess(k);
        this;
        JVM INSTR monitorexit ;
_L12:
        return;
_L2:
        if(s != null)
            s1 = s;
        continue; /* Loop/switch isn't completed */
_L6:
        s3 = null;
          goto _L10
_L8:
        int j = Binder.getCallingPid();
        k = j;
          goto _L11
        RemoteException remoteexception;
        remoteexception;
        mController = null;
_L4:
        long l1;
        l1 = Binder.clearCallingIdentity();
        if(processrecord == null || processrecord.instrumentationClass == null)
            break MISSING_BLOCK_LABEL_387;
        Slog.w("ActivityManager", (new StringBuilder()).append("Error in app ").append(processrecord.processName).append(" running instrumentation ").append(processrecord.instrumentationClass).append(":").toString());
        if(s != null)
            Slog.w("ActivityManager", (new StringBuilder()).append("  ").append(s).toString());
        if(s1 != null)
            Slog.w("ActivityManager", (new StringBuilder()).append("  ").append(s1).toString());
        Bundle bundle = new Bundle();
        bundle.putString("shortMsg", s);
        bundle.putString("longMsg", s1);
        finishInstrumentationLocked(processrecord, 0, bundle);
        Binder.restoreCallingIdentity(l1);
          goto _L12
        Exception exception;
        exception;
        throw exception;
        if(processrecord == null) goto _L14; else goto _L13
_L13:
        if(makeAppCrashingLocked(processrecord, s, s1, s2)) goto _L15; else goto _L14
_L14:
        Binder.restoreCallingIdentity(l1);
        this;
        JVM INSTR monitorexit ;
          goto _L12
_L15:
        Message message = Message.obtain();
        message.what = 1;
        HashMap hashmap = new HashMap();
        hashmap.put("result", apperrorresult);
        hashmap.put("app", processrecord);
        hashmap.put("crash", crashinfo);
        message.obj = hashmap;
        mHandler.sendMessage(message);
        Binder.restoreCallingIdentity(l1);
        this;
        JVM INSTR monitorexit ;
        int i = apperrorresult.get();
        Intent intent = null;
        this;
        JVM INSTR monitorenter ;
        if(processrecord == null)
            break MISSING_BLOCK_LABEL_538;
        if(!processrecord.isolated)
            mProcessCrashTimes.put(processrecord.info.processName, processrecord.uid, Long.valueOf(SystemClock.uptimeMillis()));
        if(i == 1)
            intent = createAppErrorIntentLocked(processrecord, l, crashinfo);
        this;
        JVM INSTR monitorexit ;
        if(intent != null)
            try {
                mContext.startActivity(intent);
            }
            catch(ActivityNotFoundException activitynotfoundexception) {
                Slog.w("ActivityManager", "bug report receiver dissappeared", activitynotfoundexception);
            }
          goto _L12
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        if(true) goto _L17; else goto _L16
_L16:
    }

    private ApplicationErrorReport createAppErrorReportLocked(ProcessRecord processrecord, long l, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        ApplicationErrorReport applicationerrorreport;
        applicationerrorreport = null;
        break MISSING_BLOCK_LABEL_3;
        while(true)  {
            do
                return applicationerrorreport;
            while(processrecord.errorReportReceiver == null || !processrecord.crashing && !processrecord.notResponding);
            applicationerrorreport = new ApplicationErrorReport();
            applicationerrorreport.packageName = processrecord.info.packageName;
            applicationerrorreport.installerPackageName = processrecord.errorReportReceiver.getPackageName();
            applicationerrorreport.processName = processrecord.processName;
            applicationerrorreport.time = l;
            boolean flag;
            if((1 & processrecord.info.flags) != 0)
                flag = true;
            else
                flag = false;
            applicationerrorreport.systemApp = flag;
            if(processrecord.crashing) {
                applicationerrorreport.type = 1;
                applicationerrorreport.crashInfo = crashinfo;
            } else
            if(processrecord.notResponding) {
                applicationerrorreport.type = 2;
                applicationerrorreport.anrInfo = new android.app.ApplicationErrorReport.AnrInfo();
                applicationerrorreport.anrInfo.activity = processrecord.notRespondingReport.tag;
                applicationerrorreport.anrInfo.cause = processrecord.notRespondingReport.shortMsg;
                applicationerrorreport.anrInfo.info = processrecord.notRespondingReport.longMsg;
            }
        }
    }

    private void dispatchProcessDied(int i, int j) {
        int k = mProcessObservers.beginBroadcast();
        do {
            if(k <= 0)
                break;
            k--;
            IProcessObserver iprocessobserver = (IProcessObserver)mProcessObservers.getBroadcastItem(k);
            if(iprocessobserver != null)
                try {
                    iprocessobserver.onProcessDied(i, j);
                }
                catch(RemoteException remoteexception) { }
        } while(true);
        mProcessObservers.finishBroadcast();
    }

    private void dispatchProcessesChanged() {
        this;
        JVM INSTR monitorenter ;
        int i;
        i = mPendingProcessChanges.size();
        if(mActiveProcessChanges.length < i)
            mActiveProcessChanges = new ProcessChangeItem[i];
        mPendingProcessChanges.toArray(mActiveProcessChanges);
        mAvailProcessChanges.addAll(mPendingProcessChanges);
        mPendingProcessChanges.clear();
        this;
        JVM INSTR monitorexit ;
        int j = mProcessObservers.beginBroadcast();
_L4:
        IProcessObserver iprocessobserver;
        int k;
        if(j <= 0)
            break MISSING_BLOCK_LABEL_189;
        j--;
        iprocessobserver = (IProcessObserver)mProcessObservers.getBroadcastItem(j);
        if(iprocessobserver == null)
            continue; /* Loop/switch isn't completed */
        k = 0;
_L2:
        if(k >= i)
            continue; /* Loop/switch isn't completed */
        ProcessChangeItem processchangeitem = mActiveProcessChanges[k];
        if((1 & processchangeitem.changes) != 0)
            iprocessobserver.onForegroundActivitiesChanged(processchangeitem.pid, processchangeitem.uid, processchangeitem.foregroundActivities);
        if((2 & processchangeitem.changes) != 0)
            iprocessobserver.onImportanceChanged(processchangeitem.pid, processchangeitem.uid, processchangeitem.importance);
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        mProcessObservers.finishBroadcast();
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void dumpActivity(String s, FileDescriptor filedescriptor, PrintWriter printwriter, ActivityRecord activityrecord, String as[], boolean flag) {
        String s1 = (new StringBuilder()).append(s).append("  ").toString();
        this;
        JVM INSTR monitorenter ;
        printwriter.print(s);
        printwriter.print("ACTIVITY ");
        printwriter.print(activityrecord.shortComponentName);
        printwriter.print(" ");
        printwriter.print(Integer.toHexString(System.identityHashCode(activityrecord)));
        printwriter.print(" pid=");
        if(activityrecord.app == null) goto _L2; else goto _L1
_L1:
        printwriter.println(activityrecord.app.pid);
_L3:
        if(flag)
            activityrecord.dump(printwriter, s1);
        this;
        JVM INSTR monitorexit ;
        if(activityrecord.app == null || activityrecord.app.thread == null)
            break MISSING_BLOCK_LABEL_179;
        printwriter.flush();
        TransferPipe transferpipe = new TransferPipe();
        activityrecord.app.thread.dumpActivity(transferpipe.getWriteFd().getFileDescriptor(), activityrecord.appToken, s1, as);
        transferpipe.go(filedescriptor);
        transferpipe.kill();
_L4:
        return;
_L2:
        printwriter.println("(not running)");
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        try {
            transferpipe.kill();
            throw exception1;
        }
        catch(IOException ioexception) {
            printwriter.println((new StringBuilder()).append(s1).append("Failure while dumping the activity: ").append(ioexception).toString());
        }
        catch(RemoteException remoteexception) {
            printwriter.println((new StringBuilder()).append(s1).append("Got a RemoteException while dumping the activity").toString());
        }
          goto _L4
    }

    private static final void dumpHistoryList(FileDescriptor filedescriptor, PrintWriter printwriter, List list, String s, String s1, boolean flag, boolean flag1, boolean flag2, 
            String s2) {
        TaskRecord taskrecord;
        boolean flag3;
        String s3;
        String as[];
        int i;
        taskrecord = null;
        flag3 = false;
        s3 = (new StringBuilder()).append(s).append("      ").toString();
        as = new String[0];
        i = -1 + list.size();
_L2:
        ActivityRecord activityrecord;
        if(i < 0)
            break MISSING_BLOCK_LABEL_537;
        activityrecord = (ActivityRecord)list.get(i);
        if(s2 == null || s2.equals(activityrecord.packageName))
            break; /* Loop/switch isn't completed */
_L4:
        i--;
        if(true) goto _L2; else goto _L1
_L1:
        TransferPipe transferpipe;
        boolean flag4;
        String s4;
        if(!flag1 && (flag || !activityrecord.isInHistory()))
            flag4 = true;
        else
            flag4 = false;
        if(flag3) {
            printwriter.println(" ");
            flag3 = false;
        }
        if(taskrecord != activityrecord.task) {
            taskrecord = activityrecord.task;
            printwriter.print(s);
            String s5;
            if(flag4)
                s5 = "* ";
            else
                s5 = "  ";
            printwriter.print(s5);
            printwriter.println(taskrecord);
            if(flag4)
                taskrecord.dump(printwriter, (new StringBuilder()).append(s).append("  ").toString());
            else
            if(flag && taskrecord.intent != null) {
                printwriter.print(s);
                printwriter.print("  ");
                printwriter.println(taskrecord.intent.toInsecureStringWithClip());
            }
        }
        printwriter.print(s);
        if(flag4)
            s4 = "  * ";
        else
            s4 = "    ";
        printwriter.print(s4);
        printwriter.print(s1);
        printwriter.print(" #");
        printwriter.print(i);
        printwriter.print(": ");
        printwriter.println(activityrecord);
        if(flag4)
            activityrecord.dump(printwriter, s3);
        else
        if(flag) {
            printwriter.print(s3);
            printwriter.println(activityrecord.intent.toInsecureString());
            if(activityrecord.app != null) {
                printwriter.print(s3);
                printwriter.println(activityrecord.app);
            }
        }
        if(!flag2 || activityrecord.app == null || activityrecord.app.thread == null) goto _L4; else goto _L3
_L3:
        printwriter.flush();
        transferpipe = new TransferPipe();
        activityrecord.app.thread.dumpActivity(transferpipe.getWriteFd().getFileDescriptor(), activityrecord.appToken, s3, as);
        transferpipe.go(filedescriptor, 2000L);
        Exception exception;
        try {
            transferpipe.kill();
        }
        catch(IOException ioexception) {
            printwriter.println((new StringBuilder()).append(s3).append("Failure while dumping the activity: ").append(ioexception).toString());
        }
        catch(RemoteException remoteexception) {
            printwriter.println((new StringBuilder()).append(s3).append("Got a RemoteException while dumping the activity").toString());
        }
        flag3 = true;
          goto _L4
        exception;
        transferpipe.kill();
        throw exception;
          goto _L4
    }

    static final void dumpMemItems(PrintWriter printwriter, String s, ArrayList arraylist, boolean flag) {
        if(flag)
            Collections.sort(arraylist, new Comparator() {

                public int compare(MemItem memitem1, MemItem memitem2) {
                    int j;
                    if(memitem1.pss < memitem2.pss)
                        j = 1;
                    else
                    if(memitem1.pss > memitem2.pss)
                        j = -1;
                    else
                        j = 0;
                    return j;
                }

                public volatile int compare(Object obj, Object obj1) {
                    return compare((MemItem)obj, (MemItem)obj1);
                }

            });
        for(int i = 0; i < arraylist.size(); i++) {
            MemItem memitem = (MemItem)arraylist.get(i);
            printwriter.print(s);
            Object aobj[] = new Object[1];
            aobj[0] = Long.valueOf(memitem.pss);
            printwriter.printf("%7d kB: ", aobj);
            printwriter.println(memitem.label);
            if(memitem.subitems != null)
                dumpMemItems(printwriter, (new StringBuilder()).append(s).append("           ").toString(), memitem.subitems, true);
        }

    }

    private static final int dumpProcessList(PrintWriter printwriter, ActivityManagerService activitymanagerservice, List list, String s, String s1, String s2, String s3) {
        int i = 0;
        int j = -1 + list.size();
        while(j >= 0)  {
            ProcessRecord processrecord = (ProcessRecord)list.get(j);
            if(s3 == null || s3.equals(processrecord.info.packageName)) {
                Object aobj[] = new Object[4];
                aobj[0] = s;
                String s4;
                if(processrecord.persistent)
                    s4 = s2;
                else
                    s4 = s1;
                aobj[1] = s4;
                aobj[2] = Integer.valueOf(j);
                aobj[3] = processrecord.toString();
                printwriter.println(String.format("%s%s #%2d: %s", aobj));
                if(processrecord.persistent)
                    i++;
            }
            j--;
        }
        return i;
    }

    private static final boolean dumpProcessOomList(PrintWriter printwriter, ActivityManagerService activitymanagerservice, List list, String s, String s1, String s2, boolean flag, String s3) {
        ArrayList arraylist;
        arraylist = new ArrayList(list.size());
        int i = 0;
        while(i < list.size())  {
            ProcessRecord processrecord1 = (ProcessRecord)list.get(i);
            if(s3 == null || s3.equals(processrecord1.info.packageName))
                arraylist.add(new Pair(list.get(i), Integer.valueOf(i)));
            i++;
        }
        if(arraylist.size() > 0) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L9:
        return flag1;
_L2:
        long l;
        long l1;
        long l2;
        int j;
        Collections.sort(arraylist, new Comparator() {

            public int compare(Pair pair, Pair pair1) {
                byte byte0 = -1;
                byte byte1;
                if(((ProcessRecord)pair.first).setAdj != ((ProcessRecord)pair1.first).setAdj) {
                    if(((ProcessRecord)pair.first).setAdj > ((ProcessRecord)pair1.first).setAdj)
                        byte1 = byte0;
                    else
                        byte1 = 1;
                } else
                if(((Integer)pair.second).intValue() != ((Integer)pair1.second).intValue()) {
                    if(((Integer)pair.second).intValue() <= ((Integer)pair1.second).intValue())
                        byte0 = 1;
                    byte1 = byte0;
                } else {
                    byte1 = 0;
                }
                return byte1;
            }

            public volatile int compare(Object obj, Object obj1) {
                return compare((Pair)obj, (Pair)obj1);
            }

        });
        l = SystemClock.elapsedRealtime();
        l1 = l - activitymanagerservice.mLastPowerCheckRealtime;
        l2 = SystemClock.uptimeMillis() - activitymanagerservice.mLastPowerCheckUptime;
        j = -1 + arraylist.size();
_L6:
        ProcessRecord processrecord;
        String s5;
        if(j < 0)
            break MISSING_BLOCK_LABEL_1346;
        processrecord = (ProcessRecord)((Pair)arraylist.get(j)).first;
        String s4;
        Object aobj[];
        long l3;
        long l4;
        long l5;
        if(processrecord.setAdj >= ProcessList.HIDDEN_APP_MIN_ADJ)
            s4 = buildOomTag("bak", "  ", processrecord.setAdj, ProcessList.HIDDEN_APP_MIN_ADJ);
        else
        if(processrecord.setAdj >= 8)
            s4 = buildOomTag("svcb ", null, processrecord.setAdj, 8);
        else
        if(processrecord.setAdj >= 7)
            s4 = buildOomTag("prev ", null, processrecord.setAdj, 7);
        else
        if(processrecord.setAdj >= 6)
            s4 = buildOomTag("home ", null, processrecord.setAdj, 6);
        else
        if(processrecord.setAdj >= 5)
            s4 = buildOomTag("svc  ", null, processrecord.setAdj, 5);
        else
        if(processrecord.setAdj >= 4)
            s4 = buildOomTag("bkup ", null, processrecord.setAdj, 4);
        else
        if(processrecord.setAdj >= 3)
            s4 = buildOomTag("hvy  ", null, processrecord.setAdj, 3);
        else
        if(processrecord.setAdj >= 2)
            s4 = buildOomTag("prcp ", null, processrecord.setAdj, 2);
        else
        if(processrecord.setAdj >= 1)
            s4 = buildOomTag("vis  ", null, processrecord.setAdj, 1);
        else
        if(processrecord.setAdj >= 0)
            s4 = buildOomTag("fore ", null, processrecord.setAdj, 0);
        else
        if(processrecord.setAdj >= -12)
            s4 = buildOomTag("pers ", null, processrecord.setAdj, -12);
        else
        if(processrecord.setAdj >= -16)
            s4 = buildOomTag("sys  ", null, processrecord.setAdj, -16);
        else
            s4 = Integer.toString(processrecord.setAdj);
        processrecord.setSchedGroup;
        JVM INSTR tableswitch -1 0: default 240
    //                   -1 1233
    //                   0 1225;
           goto _L3 _L4 _L5
_L3:
        s5 = Integer.toString(processrecord.setSchedGroup);
_L7:
        String s6;
        String s7;
        if(processrecord.foregroundActivities)
            s6 = "A";
        else
        if(processrecord.foregroundServices)
            s6 = "S";
        else
            s6 = " ";
        aobj = new Object[9];
        aobj[0] = s;
        if(processrecord.persistent)
            s7 = s2;
        else
            s7 = s1;
        aobj[1] = s7;
        aobj[2] = Integer.valueOf((-1 + list.size()) - ((Integer)((Pair)arraylist.get(j)).second).intValue());
        aobj[3] = s4;
        aobj[4] = s5;
        aobj[5] = s6;
        aobj[6] = Integer.valueOf(processrecord.trimMemoryLevel);
        aobj[7] = processrecord.toShortString();
        aobj[8] = processrecord.adjType;
        printwriter.println(String.format("%s%s #%2d: adj=%s/%s%s trm=%2d %s (%s)", aobj));
        if(processrecord.adjSource != null || processrecord.adjTarget != null) {
            printwriter.print(s);
            printwriter.print("    ");
            if(processrecord.adjTarget instanceof ComponentName)
                printwriter.print(((ComponentName)processrecord.adjTarget).flattenToShortString());
            else
            if(processrecord.adjTarget != null)
                printwriter.print(processrecord.adjTarget.toString());
            else
                printwriter.print("{null}");
            printwriter.print("<=");
            if(processrecord.adjSource instanceof ProcessRecord) {
                printwriter.print("Proc{");
                printwriter.print(((ProcessRecord)processrecord.adjSource).toShortString());
                printwriter.println("}");
            } else
            if(processrecord.adjSource != null)
                printwriter.println(processrecord.adjSource.toString());
            else
                printwriter.println("{null}");
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("    ");
            printwriter.print("oom: max=");
            printwriter.print(processrecord.maxAdj);
            printwriter.print(" hidden=");
            printwriter.print(processrecord.hiddenAdj);
            printwriter.print(" curRaw=");
            printwriter.print(processrecord.curRawAdj);
            printwriter.print(" setRaw=");
            printwriter.print(processrecord.setRawAdj);
            printwriter.print(" cur=");
            printwriter.print(processrecord.curAdj);
            printwriter.print(" set=");
            printwriter.println(processrecord.setAdj);
            printwriter.print(s);
            printwriter.print("    ");
            printwriter.print("keeping=");
            printwriter.print(processrecord.keeping);
            printwriter.print(" hidden=");
            printwriter.print(processrecord.hidden);
            printwriter.print(" empty=");
            printwriter.print(processrecord.empty);
            printwriter.print(" hasAboveClient=");
            printwriter.println(processrecord.hasAboveClient);
            if(!processrecord.keeping) {
                if(processrecord.lastWakeTime != 0L) {
                    synchronized(activitymanagerservice.mBatteryStatsService.getActiveStatistics()) {
                        l4 = batterystatsimpl.getProcessWakeTime(processrecord.info.uid, processrecord.pid, l);
                    }
                    l5 = l4 - processrecord.lastWakeTime;
                    printwriter.print(s);
                    printwriter.print("    ");
                    printwriter.print("keep awake over ");
                    TimeUtils.formatDuration(l1, printwriter);
                    printwriter.print(" used ");
                    TimeUtils.formatDuration(l5, printwriter);
                    printwriter.print(" (");
                    printwriter.print((100L * l5) / l1);
                    printwriter.println("%)");
                }
                if(processrecord.lastCpuTime != 0L) {
                    l3 = processrecord.curCpuTime - processrecord.lastCpuTime;
                    printwriter.print(s);
                    printwriter.print("    ");
                    printwriter.print("run cpu over ");
                    TimeUtils.formatDuration(l2, printwriter);
                    printwriter.print(" used ");
                    TimeUtils.formatDuration(l3, printwriter);
                    printwriter.print(" (");
                    printwriter.print((100L * l3) / l2);
                    printwriter.println("%)");
                }
            }
        }
        j--;
        if(true) goto _L6; else goto _L5
_L5:
        s5 = "B";
          goto _L7
_L4:
        s5 = "F";
          goto _L7
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
        flag1 = true;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private void dumpService(String s, FileDescriptor filedescriptor, PrintWriter printwriter, ServiceRecord servicerecord, String as[], boolean flag) {
        String s1 = (new StringBuilder()).append(s).append("  ").toString();
        this;
        JVM INSTR monitorenter ;
        printwriter.print(s);
        printwriter.print("SERVICE ");
        printwriter.print(servicerecord.shortName);
        printwriter.print(" ");
        printwriter.print(Integer.toHexString(System.identityHashCode(servicerecord)));
        printwriter.print(" pid=");
        if(servicerecord.app == null) goto _L2; else goto _L1
_L1:
        printwriter.println(servicerecord.app.pid);
_L3:
        if(flag)
            servicerecord.dump(printwriter, s1);
        this;
        JVM INSTR monitorexit ;
        if(servicerecord.app == null || servicerecord.app.thread == null)
            break MISSING_BLOCK_LABEL_211;
        printwriter.print(s);
        printwriter.println("  Client:");
        printwriter.flush();
        TransferPipe transferpipe = new TransferPipe();
        servicerecord.app.thread.dumpService(transferpipe.getWriteFd().getFileDescriptor(), servicerecord, as);
        transferpipe.setBufferPrefix((new StringBuilder()).append(s).append("    ").toString());
        transferpipe.go(filedescriptor);
        transferpipe.kill();
_L4:
        return;
_L2:
        printwriter.println("(not running)");
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        try {
            transferpipe.kill();
            throw exception1;
        }
        catch(IOException ioexception) {
            printwriter.println((new StringBuilder()).append(s).append("    Failure while dumping the service: ").append(ioexception).toString());
        }
        catch(RemoteException remoteexception) {
            printwriter.println((new StringBuilder()).append(s).append("    Got a RemoteException while dumping the service").toString());
        }
          goto _L4
    }

    public static File dumpStackTraces(boolean flag, ArrayList arraylist, ProcessStats processstats, SparseArray sparsearray, String as[]) {
        String s = SystemProperties.get("dalvik.vm.stack-trace-file", null);
        if(s != null && s.length() != 0) goto _L2; else goto _L1
_L1:
        File file = null;
_L4:
        return file;
_L2:
        file = new File(s);
        File file1 = file.getParentFile();
        if(!file1.exists())
            file.mkdirs();
        FileUtils.setPermissions(file1.getPath(), 509, -1, -1);
        if(flag && file.exists())
            file.delete();
        file.createNewFile();
        FileUtils.setPermissions(file.getPath(), 438, -1, -1);
        dumpStackTraces(s, arraylist, processstats, sparsearray, as);
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Unable to prepare ANR traces file: ").append(s).toString(), ioexception);
        file = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static void dumpStackTraces(String s, ArrayList arraylist, ProcessStats processstats, SparseArray sparsearray, String as[]) {
        FileObserver fileobserver = new FileObserver(s, 8) {

            /**
             * @deprecated Method onEvent is deprecated
             */

            public void onEvent(int i2, String s1) {
                this;
                JVM INSTR monitorenter ;
                notify();
                this;
                JVM INSTR monitorexit ;
                return;
                Exception exception3;
                exception3;
                throw exception3;
            }

        };
        fileobserver.startWatching();
        if(arraylist == null)
            break MISSING_BLOCK_LABEL_91;
        int k1;
        int l1;
        k1 = arraylist.size();
        l1 = 0;
_L1:
        if(l1 >= k1)
            break MISSING_BLOCK_LABEL_91;
        fileobserver;
        JVM INSTR monitorenter ;
        Process.sendSignal(((Integer)arraylist.get(l1)).intValue(), 3);
        fileobserver.wait(200L);
        fileobserver;
        JVM INSTR monitorexit ;
        l1++;
          goto _L1
        InterruptedException interruptedexception2;
        interruptedexception2;
        Log.wtf("ActivityManager", interruptedexception2);
        if(processstats == null) goto _L3; else goto _L2
_L2:
        processstats.init();
        System.gc();
        processstats.update();
        processstats;
        JVM INSTR monitorenter ;
        processstats.wait(500L);
        processstats;
        JVM INSTR monitorexit ;
_L10:
        int k;
        int l;
        int i1;
        processstats.update();
        k = processstats.countWorkingStats();
        l = 0;
        i1 = 0;
_L7:
        if(i1 >= k || l >= 5) goto _L3; else goto _L4
_L4:
        com.android.internal.os.ProcessStats.Stats stats;
        int j1;
        stats = processstats.getWorkingStats(i1);
        j1 = sparsearray.indexOfKey(stats.pid);
        if(j1 < 0) goto _L6; else goto _L5
_L5:
        l++;
        fileobserver;
        JVM INSTR monitorenter ;
        Process.sendSignal(stats.pid, 3);
        fileobserver.wait(200L);
        fileobserver;
        JVM INSTR monitorexit ;
_L6:
        i1++;
          goto _L7
        Exception exception2;
        exception2;
        processstats;
        JVM INSTR monitorexit ;
        Exception exception;
        InterruptedException interruptedexception1;
        Exception exception1;
        try {
            throw exception2;
        }
        catch(InterruptedException interruptedexception) { }
        finally {
            fileobserver.stopWatching();
        }
        continue; /* Loop/switch isn't completed */
        exception1;
        fileobserver;
        JVM INSTR monitorexit ;
        throw exception1;
        interruptedexception1;
        Log.wtf("ActivityManager", interruptedexception1);
        if(true) goto _L6; else goto _L8
_L8:
        throw exception;
_L3:
        fileobserver.stopWatching();
        if(as != null) {
            int ai[] = Process.getPidsForCommands(as);
            if(ai != null) {
                int i = ai.length;
                for(int j = 0; j < i; j++)
                    Debug.dumpNativeBacktraceToFile(ai[j], s);

            }
        }
        return;
        if(true) goto _L10; else goto _L9
_L9:
    }

    private void fillInProcMemInfo(ProcessRecord processrecord, android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo) {
        runningappprocessinfo.pid = processrecord.pid;
        runningappprocessinfo.uid = processrecord.info.uid;
        if(mHeavyWeightProcess == processrecord)
            runningappprocessinfo.flags = 1 | runningappprocessinfo.flags;
        if(processrecord.persistent)
            runningappprocessinfo.flags = 2 | runningappprocessinfo.flags;
        runningappprocessinfo.lastTrimLevel = processrecord.trimMemoryLevel;
        runningappprocessinfo.importance = oomAdjToImportance(processrecord.curAdj, runningappprocessinfo);
        runningappprocessinfo.importanceReasonCode = processrecord.adjTypeCode;
    }

    private final int findAffinityTaskTopLocked(int i, String s) {
        TaskRecord taskrecord;
        TaskRecord taskrecord1;
        int j;
        taskrecord = ((ActivityRecord)mMainStack.mHistory.get(i)).task;
        taskrecord1 = taskrecord;
        j = i - 1;
_L3:
        if(j < 0) goto _L2; else goto _L1
_L1:
        int i1;
        ActivityRecord activityrecord1 = (ActivityRecord)mMainStack.mHistory.get(j);
        if(activityrecord1.task == taskrecord1)
            continue; /* Loop/switch isn't completed */
        taskrecord1 = activityrecord1.task;
        if(!s.equals(taskrecord1.affinity))
            continue; /* Loop/switch isn't completed */
        i1 = j;
_L6:
        return i1;
        j--;
          goto _L3
_L2:
        int k;
        TaskRecord taskrecord2;
        int l;
        k = mMainStack.mHistory.size();
        taskrecord2 = taskrecord;
        l = i + 1;
_L7:
        if(l >= k) goto _L5; else goto _L4
_L4:
        ActivityRecord activityrecord;
label0:
        {
            activityrecord = (ActivityRecord)mMainStack.mHistory.get(l);
            if(activityrecord.task == taskrecord2)
                continue; /* Loop/switch isn't completed */
            if(!s.equals(taskrecord2.affinity))
                break label0;
            i1 = l;
        }
          goto _L6
        taskrecord2 = activityrecord.task;
        l++;
          goto _L7
_L5:
        if(s.equals(((ActivityRecord)mMainStack.mHistory.get(k - 1)).task.affinity))
            i1 = k - 1;
        else
            i1 = -1;
          goto _L6
    }

    private ProcessRecord findAppProcess(IBinder ibinder, String s) {
        if(ibinder != null) goto _L2; else goto _L1
_L1:
        ProcessRecord processrecord = null;
_L8:
        return processrecord;
_L2:
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = mProcessNames.getMap().values().iterator();
_L6:
        SparseArray sparsearray;
        int i;
        int j;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        sparsearray = (SparseArray)iterator.next();
        i = sparsearray.size();
        j = 0;
_L4:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        processrecord = (ProcessRecord)sparsearray.valueAt(j);
        if(processrecord.thread != null && processrecord.thread.asBinder() == ibinder)
            continue; /* Loop/switch isn't completed */
        break MISSING_BLOCK_LABEL_112;
        Exception exception;
        exception;
        throw exception;
        j++;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L6; else goto _L5
_L5:
        Slog.w("ActivityManager", (new StringBuilder()).append("Can't find mystery application for ").append(s).append(" from pid=").append(Binder.getCallingPid()).append(" uid=").append(Binder.getCallingUid()).append(": ").append(ibinder).toString());
        this;
        JVM INSTR monitorexit ;
        processrecord = null;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private ServiceLookupResult findServiceLocked(Intent intent, String s, int i) {
        ServiceRecord servicerecord;
        servicerecord = null;
        if(intent.getComponent() != null)
            servicerecord = mServiceMap.getServiceByName(intent.getComponent(), i);
        if(servicerecord == null) {
            android.content.Intent.FilterComparison filtercomparison = new android.content.Intent.FilterComparison(intent);
            servicerecord = mServiceMap.getServiceByIntent(filtercomparison, i);
        }
        if(servicerecord != null) goto _L2; else goto _L1
_L1:
        ServiceInfo serviceinfo;
        ResolveInfo resolveinfo = AppGlobals.getPackageManager().resolveService(intent, s, 0, i);
        if(resolveinfo == null)
            break MISSING_BLOCK_LABEL_410;
        serviceinfo = resolveinfo.serviceInfo;
          goto _L3
_L5:
        ServiceRecord servicerecord1;
        ComponentName componentname = new ComponentName(serviceinfo.applicationInfo.packageName, ((ComponentInfo) (serviceinfo)).name);
        servicerecord1 = mServiceMap.getServiceByName(componentname, Binder.getOrigCallingUser());
        servicerecord = servicerecord1;
_L2:
        ServiceLookupResult servicelookupresult;
        if(servicerecord != null) {
            int j = Binder.getCallingPid();
            int k = Binder.getCallingUid();
            if(checkComponentPermission(servicerecord.permission, j, k, servicerecord.appInfo.uid, servicerecord.exported) != 0) {
                if(!servicerecord.exported) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Permission Denial: Accessing service ").append(servicerecord.name).append(" from pid=").append(j).append(", uid=").append(k).append(" that is not exported from uid ").append(servicerecord.appInfo.uid).toString());
                    servicelookupresult = new ServiceLookupResult(null, (new StringBuilder()).append("not exported from uid ").append(servicerecord.appInfo.uid).toString());
                } else {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Permission Denial: Accessing service ").append(servicerecord.name).append(" from pid=").append(j).append(", uid=").append(k).append(" requires ").append(servicerecord.permission).toString());
                    servicelookupresult = new ServiceLookupResult(null, servicerecord.permission);
                }
            } else {
                servicelookupresult = new ServiceLookupResult(servicerecord, null);
            }
        } else {
            servicelookupresult = null;
        }
        break; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L3
_L3:
        if(serviceinfo != null)
            continue; /* Loop/switch isn't completed */
        servicelookupresult = null;
        break; /* Loop/switch isn't completed */
        if(true) goto _L5; else goto _L4
_L4:
        return servicelookupresult;
        serviceinfo = null;
          goto _L3
    }

    private final ServiceRecord findServiceLocked(ComponentName componentname, IBinder ibinder) {
        ServiceRecord servicerecord = mServiceMap.getServiceByName(componentname, Binder.getOrigCallingUser());
        if(servicerecord != ibinder)
            servicerecord = null;
        return servicerecord;
    }

    private final boolean finishReceiverLocked(IBinder ibinder, int i, String s, Bundle bundle, boolean flag, boolean flag1) {
        BroadcastRecord broadcastrecord = broadcastRecordForReceiverLocked(ibinder);
        boolean flag2;
        if(broadcastrecord == null) {
            Slog.w("ActivityManager", "finishReceiver called but not found on queue");
            flag2 = false;
        } else {
            flag2 = broadcastrecord.queue.finishReceiverLocked(broadcastrecord, i, s, bundle, flag, flag1);
        }
        return flag2;
    }

    private void forceStopPackageLocked(String s, int i) {
        forceStopPackageLocked(s, i, false, false, true, false, UserId.getUserId(i));
        Intent intent = new Intent("android.intent.action.PACKAGE_RESTARTED", Uri.fromParts("package", s, null));
        if(!mProcessesReady)
            intent.addFlags(0x40000000);
        intent.putExtra("android.intent.extra.UID", i);
        broadcastIntentLocked(null, null, intent, null, null, 0, null, null, null, false, false, MY_PID, 1000, UserId.getUserId(i));
    }

    private final boolean forceStopPackageLocked(String s, int i, boolean flag, boolean flag1, boolean flag2, boolean flag3, int j) {
        if(i >= 0)
            break MISSING_BLOCK_LABEL_20;
        int l1 = AppGlobals.getPackageManager().getPackageUid(s, j);
        i = l1;
_L13:
        boolean flag4;
        TaskRecord taskrecord;
        int k;
        if(flag2) {
            Slog.i("ActivityManager", (new StringBuilder()).append("Force stopping package ").append(s).append(" uid=").append(i).toString());
            Iterator iterator2 = mProcessCrashTimes.getMap().values().iterator();
            do {
                if(!iterator2.hasNext())
                    break;
                if(((SparseArray)iterator2.next()).get(i) != null)
                    iterator2.remove();
            } while(true);
        }
        flag4 = killPackageProcessesLocked(s, i, -100, flag, false, flag2, flag3, "force stop");
        taskrecord = null;
        k = 0;
_L8:
        if(k >= mMainStack.mHistory.size()) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord;
        boolean flag6;
        activityrecord = (ActivityRecord)mMainStack.mHistory.get(k);
        flag6 = activityrecord.packageName.equals(s);
        if(activityrecord.userId != j || !flag6 && activityrecord.task != taskrecord || activityrecord.app != null && !flag3 && activityrecord.app.persistent) goto _L4; else goto _L3
_L3:
        if(flag2) goto _L6; else goto _L5
_L5:
        if(!activityrecord.finishing) goto _L7; else goto _L4
_L4:
        k++;
          goto _L8
_L7:
        boolean flag5 = true;
_L9:
        return flag5;
_L6:
        flag4 = true;
        Slog.i("ActivityManager", (new StringBuilder()).append("  Force finishing activity ").append(activityrecord).toString());
        if(flag6) {
            if(activityrecord.app != null)
                activityrecord.app.removed = true;
            activityrecord.app = null;
        }
        taskrecord = activityrecord.task;
        if(activityrecord.stack.finishActivityLocked(activityrecord, k, 0, null, "force-stop", true))
            k--;
          goto _L4
_L2:
        ArrayList arraylist;
        Iterator iterator;
        arraylist = new ArrayList();
        iterator = mServiceMap.getAllServices(j).iterator();
_L10:
        ServiceRecord servicerecord;
label0:
        {
            do {
                if(!iterator.hasNext())
                    break MISSING_BLOCK_LABEL_513;
                servicerecord = (ServiceRecord)iterator.next();
            } while(!servicerecord.packageName.equals(s) || servicerecord.app != null && !flag3 && servicerecord.app.persistent);
            if(flag2)
                break label0;
            flag5 = true;
        }
          goto _L9
        flag4 = true;
        Slog.i("ActivityManager", (new StringBuilder()).append("  Force stopping service ").append(servicerecord).toString());
        if(servicerecord.app != null)
            servicerecord.app.removed = true;
        servicerecord.app = null;
        servicerecord.isolatedProc = null;
        arraylist.add(servicerecord);
          goto _L10
        ArrayList arraylist1;
        Iterator iterator1;
        int l = arraylist.size();
        for(int i1 = 0; i1 < l; i1++)
            bringDownServiceLocked((ServiceRecord)arraylist.get(i1), true);

        arraylist1 = new ArrayList();
        iterator1 = mProviderMap.getProvidersByClass(j).values().iterator();
_L11:
        ContentProviderRecord contentproviderrecord;
label1:
        {
            do {
                if(!iterator1.hasNext())
                    break MISSING_BLOCK_LABEL_665;
                contentproviderrecord = (ContentProviderRecord)iterator1.next();
            } while(!((ComponentInfo) (contentproviderrecord.info)).packageName.equals(s) || contentproviderrecord.proc != null && !flag3 && contentproviderrecord.proc.persistent);
            if(flag2)
                break label1;
            flag5 = true;
        }
          goto _L9
        flag4 = true;
        arraylist1.add(contentproviderrecord);
          goto _L11
        int j1 = arraylist1.size();
        for(int k1 = 0; k1 < j1; k1++)
            removeDyingProviderLocked(null, (ContentProviderRecord)arraylist1.get(k1), true);

        if(flag2) {
            if(flag1) {
                AttributeCache attributecache = AttributeCache.instance();
                if(attributecache != null)
                    attributecache.removePackage(s);
            }
            if(mBooted) {
                mMainStack.resumeTopActivityLocked(null);
                mMainStack.scheduleIdleLocked();
            }
        }
        flag5 = flag4;
          goto _L9
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L13; else goto _L12
_L12:
    }

    private final List generateApplicationProvidersLocked(ProcessRecord processrecord) {
        List list = null;
        List list1 = AppGlobals.getPackageManager().queryContentProviders(processrecord.processName, processrecord.uid, 3072);
        list = list1;
_L2:
        int i = processrecord.userId;
        if(list != null) {
            int j = list.size();
            for(int k = 0; k < j; k++) {
                ProviderInfo providerinfo = (ProviderInfo)list.get(k);
                ComponentName componentname = new ComponentName(((ComponentInfo) (providerinfo)).packageName, ((ComponentInfo) (providerinfo)).name);
                ContentProviderRecord contentproviderrecord = mProviderMap.getProviderByClass(componentname, i);
                if(contentproviderrecord == null) {
                    contentproviderrecord = new ContentProviderRecord(this, providerinfo, processrecord.info, componentname);
                    mProviderMap.putProviderByClass(componentname, contentproviderrecord);
                }
                processrecord.pubProviders.put(((ComponentInfo) (providerinfo)).name, contentproviderrecord);
                processrecord.addPackage(providerinfo.applicationInfo.packageName);
                ensurePackageDexOpt(providerinfo.applicationInfo.packageName);
            }

        }
        return list;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private android.app.ActivityManager.ProcessErrorStateInfo generateProcessError(ProcessRecord processrecord, int i, String s, String s1, String s2, String s3) {
        android.app.ActivityManager.ProcessErrorStateInfo processerrorstateinfo = new android.app.ActivityManager.ProcessErrorStateInfo();
        processerrorstateinfo.condition = i;
        processerrorstateinfo.processName = processrecord.processName;
        processerrorstateinfo.pid = processrecord.pid;
        processerrorstateinfo.uid = processrecord.info.uid;
        processerrorstateinfo.tag = s;
        processerrorstateinfo.shortMsg = s1;
        processerrorstateinfo.longMsg = s2;
        processerrorstateinfo.stackTrace = s3;
        return processerrorstateinfo;
    }

    private ApplicationInfo getAppInfoForUser(ApplicationInfo applicationinfo, int i) {
        ApplicationInfo applicationinfo1;
        if(applicationinfo == null) {
            applicationinfo1 = null;
        } else {
            applicationinfo1 = new ApplicationInfo(applicationinfo);
            applicationinfo1.uid = applyUserId(applicationinfo.uid, i);
            applicationinfo1.dataDir = (new StringBuilder()).append("/data/user/").append(i).append("/").append(applicationinfo.packageName).toString();
        }
        return applicationinfo1;
    }

    private static File getCalledPreBootReceiversFile() {
        return new File(new File(Environment.getDataDirectory(), "system"), "called_pre_boots.dat");
    }

    private ActivityRecord getCallingRecordLocked(IBinder ibinder) {
        ActivityRecord activityrecord = mMainStack.isInStackLocked(ibinder);
        ActivityRecord activityrecord1;
        if(activityrecord == null)
            activityrecord1 = null;
        else
            activityrecord1 = activityrecord.resultTo;
        return activityrecord1;
    }

    private HashMap getCommonServicesLocked() {
        if(mAppBindArgs == null) {
            mAppBindArgs = new HashMap();
            mAppBindArgs.put("package", ServiceManager.getService("package"));
            mAppBindArgs.put("window", ServiceManager.getService("window"));
            mAppBindArgs.put("alarm", ServiceManager.getService("alarm"));
        }
        return mAppBindArgs;
    }

    private android.app.IActivityManager.ContentProviderHolder getContentProviderExternalUnchecked(String s, IBinder ibinder) {
        return getContentProviderImpl(null, s, ibinder, true);
    }

    private final android.app.IActivityManager.ContentProviderHolder getContentProviderImpl(IApplicationThread iapplicationthread, String s, IBinder ibinder, boolean flag) {
        ContentProviderConnection contentproviderconnection = null;
        ProviderInfo providerinfo = null;
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord;
        processrecord = null;
        if(iapplicationthread != null) {
            processrecord = getRecordForAppLocked(iapplicationthread);
            if(processrecord == null)
                throw new SecurityException((new StringBuilder()).append("Unable to find app for caller ").append(iapplicationthread).append(" (pid=").append(Binder.getCallingPid()).append(") when getting content provider ").append(s).toString());
        }
        break MISSING_BLOCK_LABEL_84;
        Exception exception2;
        exception2;
        throw exception2;
        if(processrecord == null) goto _L2; else goto _L1
_L1:
        int i = processrecord.uid;
_L8:
        int j;
        ContentProviderRecord contentproviderrecord;
        j = UserId.getUserId(i);
        contentproviderrecord = mProviderMap.getProviderByName(s, j);
        if(contentproviderrecord == null) goto _L4; else goto _L3
_L3:
        boolean flag1 = true;
_L29:
        if(!flag1) goto _L6; else goto _L5
_L5:
        providerinfo = contentproviderrecord.info;
        String s2 = checkContentProviderPermissionLocked(providerinfo, processrecord);
        if(s2 != null)
            throw new SecurityException(s2);
          goto _L7
_L2:
        i = Binder.getCallingUid();
          goto _L8
_L7:
        if(processrecord == null || !contentproviderrecord.canRunHere(processrecord)) goto _L10; else goto _L9
_L9:
        android.app.IActivityManager.ContentProviderHolder contentproviderholder1;
        contentproviderholder1 = contentproviderrecord.newHolder(null);
        contentproviderholder1.provider = null;
        this;
        JVM INSTR monitorexit ;
          goto _L11
_L10:
        long l2;
        l2 = Binder.clearCallingIdentity();
        contentproviderconnection = incProviderCountLocked(processrecord, contentproviderrecord, ibinder, flag);
        if(contentproviderconnection != null && contentproviderconnection.stableCount + contentproviderconnection.unstableCount == 1 && contentproviderrecord.proc != null && processrecord.setAdj <= 2)
            updateLruProcessLocked(contentproviderrecord.proc, false, true);
        if(contentproviderrecord.proc == null || updateOomAdjLocked(contentproviderrecord.proc)) goto _L13; else goto _L12
_L12:
        Slog.i("ActivityManager", (new StringBuilder()).append("Existing provider ").append(contentproviderrecord.name.flattenToShortString()).append(" is crashing; detaching ").append(processrecord).toString());
        boolean flag3 = decProviderCountLocked(contentproviderconnection, contentproviderrecord, ibinder, flag);
        appDiedLocked(contentproviderrecord.proc, contentproviderrecord.proc.pid, contentproviderrecord.proc.thread);
        if(flag3)
            break MISSING_BLOCK_LABEL_1311;
        contentproviderholder1 = null;
        this;
        JVM INSTR monitorexit ;
          goto _L11
_L13:
        Binder.restoreCallingIdentity(l2);
_L6:
        if(flag1) goto _L15; else goto _L14
_L14:
        ProviderInfo providerinfo1 = AppGlobals.getPackageManager().resolveContentProvider(s, 3072, j);
        providerinfo = providerinfo1;
_L28:
        if(providerinfo != null)
            break MISSING_BLOCK_LABEL_426;
        contentproviderholder1 = null;
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        ComponentName componentname;
        ContentProviderRecord contentproviderrecord1;
        if(isSingleton(providerinfo.processName, providerinfo.applicationInfo))
            j = 0;
        ApplicationInfo applicationinfo = getAppInfoForUser(providerinfo.applicationInfo, j);
        providerinfo.applicationInfo = applicationinfo;
        String s1 = checkContentProviderPermissionLocked(providerinfo, processrecord);
        if(s1 != null)
            throw new SecurityException(s1);
        if(!mProcessesReady && !mDidUpdate && !mWaitingUpdate && !providerinfo.processName.equals("system"))
            throw new IllegalArgumentException("Attempt to launch content provider before system ready");
        componentname = new ComponentName(((ComponentInfo) (providerinfo)).packageName, ((ComponentInfo) (providerinfo)).name);
        contentproviderrecord1 = mProviderMap.getProviderByClass(componentname, j);
        boolean flag2;
        ApplicationInfo applicationinfo1;
        contentproviderrecord = contentproviderrecord1;
        if(contentproviderrecord == null)
            flag2 = true;
        else
            flag2 = false;
        if(!flag2)
            break MISSING_BLOCK_LABEL_688;
        applicationinfo1 = AppGlobals.getPackageManager().getApplicationInfo(providerinfo.applicationInfo.packageName, 1024, j);
        if(applicationinfo1 != null)
            break MISSING_BLOCK_LABEL_658;
        Slog.w("ActivityManager", (new StringBuilder()).append("No package info for content provider ").append(((ComponentInfo) (providerinfo)).name).toString());
        contentproviderholder1 = null;
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        ContentProviderRecord contentproviderrecord2;
        ApplicationInfo applicationinfo2 = getAppInfoForUser(applicationinfo1, j);
        contentproviderrecord2 = new ContentProviderRecord(this, providerinfo, applicationinfo2, componentname);
        contentproviderrecord = contentproviderrecord2;
_L27:
        if(processrecord == null)
            break MISSING_BLOCK_LABEL_716;
        if(!contentproviderrecord.canRunHere(processrecord))
            break MISSING_BLOCK_LABEL_716;
        contentproviderholder1 = contentproviderrecord.newHolder(null);
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        int k;
        int l;
        k = mLaunchingProviders.size();
        l = 0;
_L22:
        if(l < k && mLaunchingProviders.get(l) != contentproviderrecord) goto _L17; else goto _L16
_L16:
        if(l < k) goto _L19; else goto _L18
_L18:
        long l1 = Binder.clearCallingIdentity();
        AppGlobals.getPackageManager().setPackageStoppedState(contentproviderrecord.appInfo.packageName, false, j);
_L23:
        ProcessRecord processrecord1 = startProcessLocked(providerinfo.processName, contentproviderrecord.appInfo, false, 0, "content provider", new ComponentName(providerinfo.applicationInfo.packageName, ((ComponentInfo) (providerinfo)).name), false, false);
        if(processrecord1 != null) goto _L21; else goto _L20
_L20:
        Slog.w("ActivityManager", (new StringBuilder()).append("Unable to launch app ").append(providerinfo.applicationInfo.packageName).append("/").append(providerinfo.applicationInfo.uid).append(" for provider ").append(s).append(": process is bad").toString());
        contentproviderholder1 = null;
        Binder.restoreCallingIdentity(l1);
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
_L17:
        l++;
          goto _L22
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Failed trying to unstop package ").append(contentproviderrecord.appInfo.packageName).append(": ").append(illegalargumentexception).toString());
          goto _L23
        Exception exception3;
        exception3;
        Binder.restoreCallingIdentity(l1);
        throw exception3;
_L21:
        contentproviderrecord.launchingApp = processrecord1;
        mLaunchingProviders.add(contentproviderrecord);
        Binder.restoreCallingIdentity(l1);
_L19:
        if(flag2)
            mProviderMap.putProviderByClass(componentname, contentproviderrecord);
        mProviderMap.putProviderByName(s, contentproviderrecord);
        contentproviderconnection = incProviderCountLocked(processrecord, contentproviderrecord, ibinder, flag);
        if(contentproviderconnection != null)
            contentproviderconnection.waiting = true;
_L15:
        this;
        JVM INSTR monitorexit ;
        contentproviderrecord;
        JVM INSTR monitorenter ;
_L25:
        if(contentproviderrecord.provider != null)
            break MISSING_BLOCK_LABEL_1243;
        if(contentproviderrecord.launchingApp == null) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Unable to launch app ").append(providerinfo.applicationInfo.packageName).append("/").append(providerinfo.applicationInfo.uid).append(" for provider ").append(s).append(": launching app became null").toString());
            Object aobj[] = new Object[3];
            aobj[0] = providerinfo.applicationInfo.packageName;
            aobj[1] = Integer.valueOf(providerinfo.applicationInfo.uid);
            aobj[2] = s;
            EventLog.writeEvent(30036, aobj);
            contentproviderholder1 = null;
            break; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_1195;
        Exception exception;
        exception;
        throw exception;
        if(contentproviderconnection == null)
            break MISSING_BLOCK_LABEL_1206;
        contentproviderconnection.waiting = true;
        contentproviderrecord.wait();
        if(contentproviderconnection == null)
            continue; /* Loop/switch isn't completed */
        contentproviderconnection.waiting = false;
        continue; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        if(contentproviderconnection != null)
            contentproviderconnection.waiting = false;
        throw exception1;
        android.app.IActivityManager.ContentProviderHolder contentproviderholder;
        if(contentproviderrecord != null)
            contentproviderholder = contentproviderrecord.newHolder(contentproviderconnection);
        else
            contentproviderholder = null;
        contentproviderholder1 = contentproviderholder;
        break; /* Loop/switch isn't completed */
        InterruptedException interruptedexception;
        interruptedexception;
        if(contentproviderconnection == null)
            continue; /* Loop/switch isn't completed */
        contentproviderconnection.waiting = false;
        if(true) goto _L25; else goto _L24
_L24:
        RemoteException remoteexception1;
        break; /* Loop/switch isn't completed */
        remoteexception1;
          goto _L23
        RemoteException remoteexception2;
        remoteexception2;
        if(true) goto _L27; else goto _L26
_L26:
        RemoteException remoteexception;
        break; /* Loop/switch isn't completed */
        remoteexception;
        if(true) goto _L28; else goto _L11
_L4:
        flag1 = false;
          goto _L29
_L11:
        return contentproviderholder1;
        flag1 = false;
        contentproviderconnection = null;
          goto _L13
    }

    private final int getLRURecordIndexForAppLocked(IApplicationThread iapplicationthread) {
        IBinder ibinder;
        int i;
        ibinder = iapplicationthread.asBinder();
        i = -1 + mLruProcesses.size();
_L3:
        ProcessRecord processrecord;
        if(i < 0)
            break MISSING_BLOCK_LABEL_65;
        processrecord = (ProcessRecord)mLruProcesses.get(i);
        if(processrecord.thread == null || processrecord.thread.asBinder() != ibinder) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        i--;
          goto _L3
        i = -1;
          goto _L1
    }

    private final List getStickiesLocked(String s, IntentFilter intentfilter, List list) {
        android.content.ContentResolver contentresolver = mContext.getContentResolver();
        ArrayList arraylist = (ArrayList)mStickyBroadcasts.get(s);
        List list1;
        if(arraylist == null) {
            list1 = list;
        } else {
            int i = arraylist.size();
            for(int j = 0; j < i; j++) {
                Intent intent = (Intent)arraylist.get(j);
                if(intentfilter.match(contentresolver, intent, true, "ActivityManager") < 0)
                    continue;
                if(list == null)
                    list = new ArrayList();
                list.add(intent);
            }

            list1 = list;
        }
        return list1;
    }

    private boolean handleAppCrashLocked(ProcessRecord processrecord) {
        if(!mHeadless) goto _L2; else goto _L1
_L1:
        boolean flag;
        Log.e("ActivityManager", (new StringBuilder()).append("handleAppCrashLocked: ").append(processrecord.processName).toString());
        flag = false;
_L8:
        return flag;
_L2:
        long l;
        l = SystemClock.uptimeMillis();
        Long long1;
        Object aobj[];
        int k;
        ActivityRecord activityrecord3;
        if(!processrecord.isolated)
            long1 = (Long)mProcessCrashTimes.get(processrecord.info.processName, processrecord.uid);
        else
            long1 = null;
        if(long1 == null || l >= 60000L + long1.longValue()) goto _L4; else goto _L3
_L3:
        Slog.w("ActivityManager", (new StringBuilder()).append("Process ").append(processrecord.info.processName).append(" has crashed too many times: killing!").toString());
        aobj = new Object[2];
        aobj[0] = processrecord.info.processName;
        aobj[1] = Integer.valueOf(processrecord.uid);
        EventLog.writeEvent(30032, aobj);
        for(k = -1 + mMainStack.mHistory.size(); k >= 0; k--) {
            activityrecord3 = (ActivityRecord)mMainStack.mHistory.get(k);
            if(activityrecord3.app == processrecord) {
                Slog.w("ActivityManager", (new StringBuilder()).append("  Force finishing activity ").append(activityrecord3.intent.getComponent().flattenToShortString()).toString());
                activityrecord3.stack.finishActivityLocked(activityrecord3, k, 0, null, "crashed");
            }
        }

        if(!processrecord.persistent) {
            Object aobj1[] = new Object[2];
            aobj1[0] = Integer.valueOf(processrecord.uid);
            aobj1[1] = processrecord.info.processName;
            EventLog.writeEvent(30015, aobj1);
            if(!processrecord.isolated) {
                mBadProcesses.put(processrecord.info.processName, processrecord.uid, Long.valueOf(l));
                mProcessCrashTimes.remove(processrecord.info.processName, processrecord.uid);
            }
            processrecord.bad = true;
            processrecord.removed = true;
            removeProcessLocked(processrecord, false, false, "crash");
            mMainStack.resumeTopActivityLocked(null);
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        mMainStack.resumeTopActivityLocked(null);
_L6:
        if(processrecord.services.size() != 0) {
            for(Iterator iterator1 = processrecord.services.iterator(); iterator1.hasNext();) {
                ServiceRecord servicerecord = (ServiceRecord)iterator1.next();
                servicerecord.crashCount = 1 + servicerecord.crashCount;
            }

        }
        break; /* Loop/switch isn't completed */
_L4:
        ActivityRecord activityrecord = mMainStack.topRunningActivityLocked(null);
        if(activityrecord != null && activityrecord.app == processrecord) {
            Slog.w("ActivityManager", (new StringBuilder()).append("  Force finishing activity ").append(activityrecord.intent.getComponent().flattenToShortString()).toString());
            int i = mMainStack.indexOfActivityLocked(activityrecord);
            activityrecord.stack.finishActivityLocked(activityrecord, i, 0, null, "crashed");
            int j = i - 1;
            if(j >= 0) {
                ActivityRecord activityrecord1 = (ActivityRecord)mMainStack.mHistory.get(j);
                if((activityrecord1.state == ActivityStack.ActivityState.RESUMED || activityrecord1.state == ActivityStack.ActivityState.PAUSING || activityrecord1.state == ActivityStack.ActivityState.PAUSED) && (!activityrecord1.isHomeActivity || mHomeProcess != activityrecord1.app)) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("  Force finishing activity ").append(activityrecord1.intent.getComponent().flattenToShortString()).toString());
                    activityrecord1.stack.finishActivityLocked(activityrecord1, j, 0, null, "crashed");
                }
            }
        }
        if(true) goto _L6; else goto _L5
_L5:
        if(processrecord == mHomeProcess && mHomeProcess.activities.size() > 0 && (1 & mHomeProcess.info.flags) == 0) {
            Iterator iterator = mHomeProcess.activities.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                ActivityRecord activityrecord2 = (ActivityRecord)iterator.next();
                if(activityrecord2.isHomeActivity) {
                    Log.i("ActivityManager", (new StringBuilder()).append("Clearing package preferred activities from ").append(activityrecord2.packageName).toString());
                    try {
                        ActivityThread.getPackageManager().clearPackagePreferredActivities(activityrecord2.packageName);
                    }
                    catch(RemoteException remoteexception) { }
                }
            } while(true);
        }
        if(!processrecord.isolated)
            mProcessCrashTimes.put(processrecord.info.processName, processrecord.uid, Long.valueOf(l));
        flag = true;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final void handleAppDiedLocked(ProcessRecord processrecord, boolean flag, boolean flag1) {
        cleanUpApplicationRecordLocked(processrecord, flag, flag1, -1);
        if(!flag)
            mLruProcesses.remove(processrecord);
        if(mProfileProc == processrecord)
            clearProfilerLocked();
        if(mMainStack.mPausingActivity != null && mMainStack.mPausingActivity.app == processrecord)
            mMainStack.mPausingActivity = null;
        if(mMainStack.mLastPausedActivity != null && mMainStack.mLastPausedActivity.app == processrecord)
            mMainStack.mLastPausedActivity = null;
        mMainStack.removeHistoryRecordsForAppLocked(processrecord);
        boolean flag2 = false;
        for(int i = mMainStack.mHistory.size(); i > 0;) {
            i--;
            ActivityRecord activityrecord = (ActivityRecord)mMainStack.mHistory.get(i);
            if(activityrecord.app == processrecord) {
                if(!activityrecord.haveState && !activityrecord.stateNotNeeded || activityrecord.finishing) {
                    if(!activityrecord.finishing) {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Force removing ").append(activityrecord).append(": app died, no saved state").toString());
                        Object aobj[] = new Object[4];
                        aobj[0] = Integer.valueOf(System.identityHashCode(activityrecord));
                        aobj[1] = Integer.valueOf(activityrecord.task.taskId);
                        aobj[2] = activityrecord.shortComponentName;
                        aobj[3] = "proc died without state saved";
                        EventLog.writeEvent(30001, aobj);
                    }
                    mMainStack.removeActivityFromHistoryLocked(activityrecord);
                } else {
                    if(activityrecord.visible)
                        flag2 = true;
                    activityrecord.app = null;
                    activityrecord.nowVisible = false;
                    if(!activityrecord.haveState)
                        activityrecord.icicle = null;
                }
                activityrecord.stack.cleanUpActivityLocked(activityrecord, true, true);
            }
        }

        processrecord.activities.clear();
        if(processrecord.instrumentationClass != null) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Crash of app ").append(processrecord.processName).append(" running instrumentation ").append(processrecord.instrumentationClass).toString());
            Bundle bundle = new Bundle();
            bundle.putString("shortMsg", "Process crashed.");
            finishInstrumentationLocked(processrecord, 0, bundle);
        }
        if(!flag && !mMainStack.resumeTopActivityLocked(null) && flag2)
            mMainStack.ensureActivitiesVisibleLocked(null, 0);
    }

    public static final void installSystemProviders() {
        ActivityManagerService activitymanagerservice = mSelf;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        List list;
        ProcessRecord processrecord = (ProcessRecord)mSelf.mProcessNames.get("system", 1000);
        list = mSelf.generateApplicationProvidersLocked(processrecord);
        if(list != null) {
            Exception exception;
            for(int i = -1 + list.size(); i >= 0; i--) {
                ProviderInfo providerinfo = (ProviderInfo)list.get(i);
                if((1 & providerinfo.applicationInfo.flags) == 0) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Not installing system proc provider ").append(((ComponentInfo) (providerinfo)).name).append(": not system .apk").toString());
                    list.remove(i);
                }
                break MISSING_BLOCK_LABEL_171;
            }

        }
        if(list != null)
            mSystemThread.installSystemProviders(list);
        mSelf.mCoreSettingsObserver = new CoreSettingsObserver(mSelf);
        mSelf.mUsageStatsService.monitorPackages();
        return;
        exception;
        activitymanagerservice;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private BroadcastQueue isReceivingBroadcast(ProcessRecord processrecord) {
        BroadcastRecord broadcastrecord = processrecord.curReceiver;
        if(broadcastrecord == null) goto _L2; else goto _L1
_L1:
        BroadcastQueue broadcastqueue = broadcastrecord.queue;
_L6:
        return broadcastqueue;
_L2:
        this;
        JVM INSTR monitorenter ;
        BroadcastQueue abroadcastqueue[];
        int i;
        int j;
        abroadcastqueue = mBroadcastQueues;
        i = abroadcastqueue.length;
        j = 0;
_L4:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        broadcastqueue = abroadcastqueue[j];
        BroadcastRecord broadcastrecord1 = broadcastqueue.mPendingBroadcast;
        if(broadcastrecord1 != null && broadcastrecord1.curApp == processrecord)
            continue; /* Loop/switch isn't completed */
        break MISSING_BLOCK_LABEL_79;
        Exception exception;
        exception;
        throw exception;
        j++;
        if(true) goto _L4; else goto _L3
_L3:
        this;
        JVM INSTR monitorexit ;
        broadcastqueue = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private final boolean killPackageProcessesLocked(String s, int i, int j, boolean flag, boolean flag1, boolean flag2, boolean flag3, 
            String s1) {
        ArrayList arraylist;
        String s2;
        Iterator iterator;
        arraylist = new ArrayList();
        s2 = (new StringBuilder()).append(s).append(":").toString();
        iterator = mProcessNames.getMap().values().iterator();
_L2:
        SparseArray sparsearray;
        int i1;
        int j1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_231;
        sparsearray = (SparseArray)iterator.next();
        i1 = sparsearray.size();
        j1 = 0;
_L5:
        if(j1 >= i1) goto _L2; else goto _L1
_L1:
        ProcessRecord processrecord = (ProcessRecord)sparsearray.valueAt(j1);
        if(!processrecord.persistent || flag3) goto _L4; else goto _L3
_L3:
        j1++;
          goto _L5
_L4:
        if(!processrecord.removed) goto _L7; else goto _L6
_L6:
        if(flag2)
            arraylist.add(processrecord);
          goto _L3
_L7:
        if((i <= 0 || i == 1000 || processrecord.info.uid != i) && (!processrecord.processName.equals(s) && !processrecord.processName.startsWith(s2) || i >= 0) || processrecord.setAdj < j) goto _L3; else goto _L8
_L8:
        if(flag2) goto _L10; else goto _L9
_L9:
        boolean flag4 = true;
_L11:
        return flag4;
_L10:
        processrecord.removed = true;
        arraylist.add(processrecord);
          goto _L3
        int k = arraylist.size();
        for(int l = 0; l < k; l++)
            removeProcessLocked((ProcessRecord)arraylist.get(l), flag, flag1, s1);

        if(k > 0)
            flag4 = true;
        else
            flag4 = false;
          goto _L11
    }

    private boolean killProcessesBelowAdj(int i, String s) {
        boolean flag;
        if(Binder.getCallingUid() != 1000)
            throw new SecurityException("killProcessesBelowAdj() only available to system");
        flag = false;
        SparseArray sparsearray = mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        int j;
        int k;
        j = mPidsSelfLocked.size();
        k = 0;
_L2:
        if(k < j) {
            int l = mPidsSelfLocked.keyAt(k);
            ProcessRecord processrecord = (ProcessRecord)mPidsSelfLocked.valueAt(k);
            if(processrecord != null) {
                int i1 = processrecord.setAdj;
                if(i1 > i && !processrecord.killedBackground) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Killing ").append(processrecord).append(" (adj ").append(i1).append("): ").append(s).toString());
                    Object aobj[] = new Object[4];
                    aobj[0] = Integer.valueOf(processrecord.pid);
                    aobj[1] = processrecord.processName;
                    aobj[2] = Integer.valueOf(i1);
                    aobj[3] = s;
                    EventLog.writeEvent(30023, aobj);
                    flag = true;
                    processrecord.killedBackground = true;
                    Process.killProcessQuiet(l);
                }
            }
            break MISSING_BLOCK_LABEL_231;
        }
        break MISSING_BLOCK_LABEL_226;
        Exception exception;
        exception;
        throw exception;
        sparsearray;
        JVM INSTR monitorexit ;
        return flag;
        k++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private final void killServicesLocked(ProcessRecord processrecord, boolean flag) {
        Iterator iterator;
        if(processrecord.connections.size() > 0) {
            for(Iterator iterator2 = processrecord.connections.iterator(); iterator2.hasNext(); removeConnectionLocked((ConnectionRecord)iterator2.next(), processrecord, null));
        }
        processrecord.connections.clear();
        if(processrecord.services.size() == 0)
            break MISSING_BLOCK_LABEL_458;
        iterator = processrecord.services.iterator();
_L2:
        ServiceRecord servicerecord;
        boolean flag1;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        servicerecord = (ServiceRecord)iterator.next();
        synchronized(servicerecord.stats.getBatteryStats()) {
            servicerecord.stats.stopLaunchedLocked();
        }
        servicerecord.app = null;
        servicerecord.isolatedProc = null;
        servicerecord.executeNesting = 0;
        if(!mStoppingServices.remove(servicerecord));
        if(servicerecord.bindings.size() > 0)
            flag1 = true;
        else
            flag1 = false;
        if(flag1) {
            for(Iterator iterator1 = servicerecord.bindings.values().iterator(); iterator1.hasNext();) {
                IntentBindRecord intentbindrecord = (IntentBindRecord)iterator1.next();
                intentbindrecord.binder = null;
                intentbindrecord.hasBound = false;
                intentbindrecord.received = false;
                intentbindrecord.requested = false;
            }

        }
        break MISSING_BLOCK_LABEL_247;
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
        if(servicerecord.crashCount >= 2 && (8 & servicerecord.serviceInfo.applicationInfo.flags) == 0) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Service crashed ").append(servicerecord.crashCount).append(" times, stopping: ").append(servicerecord).toString());
            Object aobj[] = new Object[3];
            aobj[0] = Integer.valueOf(servicerecord.crashCount);
            aobj[1] = servicerecord.shortName;
            aobj[2] = Integer.valueOf(processrecord.pid);
            EventLog.writeEvent(30034, aobj);
            bringDownServiceLocked(servicerecord, true);
        } else
        if(!flag) {
            bringDownServiceLocked(servicerecord, true);
        } else {
            boolean flag2 = scheduleServiceRestartLocked(servicerecord, true);
            if(servicerecord.startRequested && (servicerecord.stopIfKilled || flag2) && servicerecord.pendingStarts.size() == 0) {
                servicerecord.startRequested = false;
                if(!flag1)
                    bringDownServiceLocked(servicerecord, true);
            }
        }
        if(true) goto _L2; else goto _L1
_L1:
        if(!flag)
            processrecord.services.clear();
        int i = mStoppingServices.size();
        do {
            if(i <= 0)
                break;
            i--;
            if(((ServiceRecord)mStoppingServices.get(i)).app == processrecord)
                mStoppingServices.remove(i);
        } while(true);
        processrecord.executingServices.clear();
        return;
    }

    private void logStrictModeViolationToDropBox(ProcessRecord processrecord, android.os.StrictMode.ViolationInfo violationinfo) {
        if(violationinfo != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        boolean flag;
        String s1;
        DropBoxManager dropboxmanager;
        StringBuilder stringbuilder;
        boolean flag1;
        String s;
        String as[];
        int i;
        int j;
        String s2;
        if(processrecord == null || (0x81 & processrecord.info.flags) != 0)
            flag = true;
        else
            flag = false;
        if(processrecord == null)
            s = "unknown";
        else
            s = processrecord.processName;
        if(flag)
            s1 = "system_app_strictmode";
        else
            s1 = "data_app_strictmode";
        dropboxmanager = (DropBoxManager)mContext.getSystemService("dropbox");
        if(dropboxmanager == null || !dropboxmanager.isTagEnabled(s1))
            continue; /* Loop/switch isn't completed */
        if(flag)
            stringbuilder = mStrictModeBuffer;
        else
            stringbuilder = new StringBuilder(1024);
        stringbuilder;
        JVM INSTR monitorenter ;
        if(stringbuilder.length() != 0)
            break MISSING_BLOCK_LABEL_417;
        flag1 = true;
_L4:
        appendDropBoxProcessHeaders(processrecord, s, stringbuilder);
        stringbuilder.append("Build: ").append(Build.FINGERPRINT).append("\n");
        stringbuilder.append("System-App: ").append(flag).append("\n");
        stringbuilder.append("Uptime-Millis: ").append(violationinfo.violationUptimeMillis).append("\n");
        if(violationinfo.violationNumThisLoop != 0)
            stringbuilder.append("Loop-Violation-Number: ").append(violationinfo.violationNumThisLoop).append("\n");
        if(violationinfo.numAnimationsRunning != 0)
            stringbuilder.append("Animations-Running: ").append(violationinfo.numAnimationsRunning).append("\n");
        if(violationinfo.broadcastIntentAction != null)
            stringbuilder.append("Broadcast-Intent-Action: ").append(violationinfo.broadcastIntentAction).append("\n");
        if(violationinfo.durationMillis != -1)
            stringbuilder.append("Duration-Millis: ").append(violationinfo.durationMillis).append("\n");
        if(violationinfo.numInstances != -1L)
            stringbuilder.append("Instance-Count: ").append(violationinfo.numInstances).append("\n");
        if(violationinfo.tags == null)
            break MISSING_BLOCK_LABEL_423;
        as = violationinfo.tags;
        i = as.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_423;
        s2 = as[j];
        stringbuilder.append("Span-Tag: ").append(s2).append("\n");
        j++;
          goto _L3
        flag1 = false;
          goto _L4
        boolean flag2;
        stringbuilder.append("\n");
        if(violationinfo.crashInfo != null && violationinfo.crashInfo.stackTrace != null)
            stringbuilder.append(violationinfo.crashInfo.stackTrace);
        stringbuilder.append("\n");
        if(stringbuilder.length() <= 0x10000)
            break MISSING_BLOCK_LABEL_538;
        flag2 = true;
_L5:
        stringbuilder;
        JVM INSTR monitorexit ;
        Exception exception;
        if(!flag || flag2)
            (new Thread(s1) {

                public void run() {
                    String s3;
                    synchronized(sb) {
                        s3 = sb.toString();
                        sb.delete(0, sb.length());
                        sb.trimToSize();
                    }
                    if(s3.length() != 0)
                        dbox.addText(dropboxTag, s3);
                    return;
                    exception1;
                    stringbuilder1;
                    JVM INSTR monitorexit ;
                    throw exception1;
                }

                final ActivityManagerService this$0;
                final DropBoxManager val$dbox;
                final String val$dropboxTag;
                final StringBuilder val$sb;

             {
                this$0 = ActivityManagerService.this;
                sb = stringbuilder;
                dbox = dropboxmanager;
                dropboxTag = s1;
                super(final_s);
            }
            }).start();
        else
        if(flag1)
            (new Thread(s1) {

                public void run() {
                    String s3;
                    try {
                        Thread.sleep(5000L);
                    }
                    catch(InterruptedException interruptedexception) { }
                    synchronized(mStrictModeBuffer) {
                        s3 = mStrictModeBuffer.toString();
                        if(s3.length() == 0)
                            break MISSING_BLOCK_LABEL_100;
                        mStrictModeBuffer.delete(0, mStrictModeBuffer.length());
                        mStrictModeBuffer.trimToSize();
                    }
                    dbox.addText(dropboxTag, s3);
                      goto _L1
                    exception1;
                    stringbuilder1;
                    JVM INSTR monitorexit ;
                    throw exception1;
_L1:
                }

                final ActivityManagerService this$0;
                final DropBoxManager val$dbox;
                final String val$dropboxTag;

             {
                this$0 = ActivityManagerService.this;
                dbox = dropboxmanager;
                dropboxTag = s1;
                super(final_s);
            }
            }).start();
        continue; /* Loop/switch isn't completed */
        flag2 = false;
          goto _L5
        exception;
        stringbuilder;
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L1; else goto _L6
_L6:
    }

    public static final Context main(int i) {
        AThread athread;
        athread = new AThread();
        athread.start();
        athread;
        JVM INSTR monitorenter ;
_L3:
        ActivityManagerService activitymanagerservice = athread.mService;
        if(activitymanagerservice != null) goto _L2; else goto _L1
_L1:
        Exception exception;
        ActivityManagerService activitymanagerservice1;
        android.app.ContextImpl contextimpl;
        try {
            athread.wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally { }
        if(true) goto _L3; else goto _L2
_L2:
        athread;
        JVM INSTR monitorexit ;
        activitymanagerservice1 = athread.mService;
        mSelf = activitymanagerservice1;
        ActivityThread activitythread = ActivityThread.systemMain();
        mSystemThread = activitythread;
        contextimpl = activitythread.getSystemContext();
        contextimpl.setTheme(0x103006e);
        activitymanagerservice1.mContext = contextimpl;
        activitymanagerservice1.mFactoryTest = i;
        activitymanagerservice1.mMainStack = new ActivityStack(activitymanagerservice1, contextimpl, true);
        activitymanagerservice1.mBatteryStatsService.publish(contextimpl);
        activitymanagerservice1.mUsageStatsService.publish(contextimpl);
        athread;
        JVM INSTR monitorenter ;
        athread.mReady = true;
        athread.notifyAll();
        athread;
        JVM INSTR monitorexit ;
        activitymanagerservice1.startRunning(null, null, null, null);
        return contextimpl;
        athread;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception1;
        exception1;
        athread;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    private boolean makeAppCrashingLocked(ProcessRecord processrecord, String s, String s1, String s2) {
        processrecord.crashing = true;
        processrecord.crashingReport = generateProcessError(processrecord, 1, null, s, s1, s2);
        startAppProblemLocked(processrecord);
        processrecord.stopFreezingAllLocked();
        return handleAppCrashLocked(processrecord);
    }

    private void makeAppNotRespondingLocked(ProcessRecord processrecord, String s, String s1, String s2) {
        processrecord.notResponding = true;
        processrecord.notRespondingReport = generateProcessError(processrecord, 2, s, s1, s2, null);
        startAppProblemLocked(processrecord);
        processrecord.stopFreezingAllLocked();
    }

    private final void moveTaskBackwardsLocked(int i) {
        Slog.e("ActivityManager", "moveTaskBackwards not yet implemented!");
    }

    private void onUserRemoved(Intent intent) {
        int i = intent.getIntExtra("android.intent.extra.user_id", -1);
        if(i >= 1) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ArrayList arraylist = new ArrayList();
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = mProcessNames.getMap().entrySet().iterator();
_L6:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        java.util.Map.Entry entry;
        SparseArray sparsearray;
        int j;
        entry = (java.util.Map.Entry)iterator.next();
        sparsearray = (SparseArray)entry.getValue();
        j = 0;
_L7:
        if(j >= sparsearray.size()) goto _L6; else goto _L5
_L4:
        Pair pair;
        for(Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); forceStopPackageLocked((String)pair.first, ((Integer)pair.second).intValue(), false, false, true, true, i))
            pair = (Pair)iterator1.next();

        break MISSING_BLOCK_LABEL_203;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
          goto _L1
_L5:
        Exception exception;
        if(UserId.getUserId(sparsearray.keyAt(j)) == i)
            arraylist.add(new Pair(entry.getKey(), Integer.valueOf(sparsearray.keyAt(j))));
        j++;
          goto _L7
    }

    static int oomAdjToImportance(int i, android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo) {
        char c = '\u0190';
        if(i < ProcessList.HIDDEN_APP_MIN_ADJ) goto _L2; else goto _L1
_L1:
        if(runningappprocessinfo != null)
            runningappprocessinfo.lru = 1 + (i - ProcessList.HIDDEN_APP_MIN_ADJ);
_L4:
        return c;
_L2:
        if(i >= 8)
            c = '\u012C';
        else
        if(i >= 6) {
            if(runningappprocessinfo != null)
                runningappprocessinfo.lru = 0;
        } else
        if(i >= 5)
            c = '\u012C';
        else
        if(i >= 3)
            c = '\252';
        else
        if(i >= 2)
            c = '\202';
        else
        if(i >= 1)
            c = '\310';
        else
            c = 'd';
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static String processClass(ProcessRecord processrecord) {
        String s;
        if(processrecord == null || processrecord.pid == MY_PID)
            s = "system_server";
        else
        if((1 & processrecord.info.flags) != 0)
            s = "system_app";
        else
            s = "data_app";
        return s;
    }

    private final void processStartTimedOutLocked(ProcessRecord processrecord) {
        int i;
        i = processrecord.pid;
        boolean flag = false;
        synchronized(mPidsSelfLocked) {
            ProcessRecord processrecord1 = (ProcessRecord)mPidsSelfLocked.get(i);
            if(processrecord1 != null && processrecord1.thread == null) {
                mPidsSelfLocked.remove(i);
                flag = true;
            }
        }
        if(!flag)
            break MISSING_BLOCK_LABEL_451;
        Slog.w("ActivityManager", (new StringBuilder()).append("Process ").append(processrecord).append(" failed to attach").toString());
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(processrecord.uid);
        aobj[2] = processrecord.processName;
        EventLog.writeEvent(30037, aobj);
        mProcessNames.remove(processrecord.processName, processrecord.uid);
        mIsolatedProcesses.remove(processrecord.uid);
        if(mHeavyWeightProcess == processrecord) {
            mHeavyWeightProcess = null;
            mHandler.sendEmptyMessage(25);
        }
        checkAppInLaunchingProvidersLocked(processrecord, true);
        for(int j = 0; j < mPendingServices.size(); j++) {
            ServiceRecord servicerecord = (ServiceRecord)mPendingServices.get(j);
            if(processrecord.uid == servicerecord.appInfo.uid && processrecord.processName.equals(servicerecord.processName) || servicerecord.isolatedProc == processrecord) {
                Slog.w("ActivityManager", (new StringBuilder()).append("Forcing bringing down service: ").append(servicerecord).toString());
                servicerecord.isolatedProc = null;
                mPendingServices.remove(j);
                j--;
                bringDownServiceLocked(servicerecord, true);
            }
        }

        break MISSING_BLOCK_LABEL_324;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
        Object aobj1[] = new Object[4];
        aobj1[0] = Integer.valueOf(i);
        aobj1[1] = processrecord.processName;
        aobj1[2] = Integer.valueOf(processrecord.setAdj);
        aobj1[3] = "start timeout";
        EventLog.writeEvent(30023, aobj1);
        Process.killProcessQuiet(i);
        if(mBackupTarget != null && mBackupTarget.app.pid == i) {
            Slog.w("ActivityManager", "Unattached app died before backup, skipping");
            try {
                android.app.backup.IBackupManager.Stub.asInterface(ServiceManager.getService("backup")).agentDisconnected(processrecord.info.packageName);
            }
            catch(RemoteException remoteexception) { }
        }
        if(isPendingBroadcastProcessLocked(i)) {
            Slog.w("ActivityManager", "Unattached app died before broadcast acknowledged, skipping");
            skipPendingBroadcastLocked(i);
        }
        return;
        Slog.w("ActivityManager", (new StringBuilder()).append("Spurious process start timeout - pid not known for ").append(processrecord).toString());
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_450;
    }

    private static ArrayList readLastDonePreBootReceivers() {
        ArrayList arraylist;
        File file;
        FileInputStream fileinputstream;
        arraylist = new ArrayList();
        file = getCalledPreBootReceiversFile();
        fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(file);
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(fileinputstream1, 2048));
        if(datainputstream.readInt() == 10000) {
            String s = datainputstream.readUTF();
            String s1 = datainputstream.readUTF();
            String s2 = datainputstream.readUTF();
            if(android.os.Build.VERSION.RELEASE.equals(s) && android.os.Build.VERSION.CODENAME.equals(s1) && android.os.Build.VERSION.INCREMENTAL.equals(s2)) {
                for(int i = datainputstream.readInt(); i > 0;) {
                    i--;
                    arraylist.add(new ComponentName(datainputstream.readUTF(), datainputstream.readUTF()));
                }

            }
        }
          goto _L1
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        fileinputstream = fileinputstream1;
_L8:
        IOException ioexception;
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception2) { }
_L2:
        return arraylist;
_L1:
        Exception exception;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception3) { }
          goto _L2
        ioexception;
_L6:
        Slog.w("ActivityManager", "Failure reading last done pre-boot receivers", ioexception);
        if(fileinputstream == null) goto _L2; else goto _L3
_L3:
        fileinputstream.close();
          goto _L2
        exception;
_L5:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L5; else goto _L4
_L4:
        ioexception;
        fileinputstream = fileinputstream1;
          goto _L6
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final void realStartServiceLocked(ServiceRecord servicerecord, ProcessRecord processrecord) throws RemoteException {
        if(processrecord.thread == null)
            throw new RemoteException();
        servicerecord.app = processrecord;
        long l = SystemClock.uptimeMillis();
        servicerecord.lastActivity = l;
        servicerecord.restartTime = l;
        processrecord.services.add(servicerecord);
        bumpServiceExecutingLocked(servicerecord, "create");
        updateLruProcessLocked(processrecord, true, true);
        mStringBuilder.setLength(0);
        servicerecord.intent.getIntent().toShortString(mStringBuilder, true, false, true, false);
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(System.identityHashCode(servicerecord));
        aobj[1] = servicerecord.shortName;
        aobj[2] = mStringBuilder.toString();
        aobj[3] = Integer.valueOf(servicerecord.app.pid);
        EventLog.writeEvent(30030, aobj);
        synchronized(servicerecord.stats.getBatteryStats()) {
            servicerecord.stats.startLaunchedLocked();
        }
        ensurePackageDexOpt(((ComponentInfo) (servicerecord.serviceInfo)).packageName);
        processrecord.thread.scheduleCreateService(servicerecord, servicerecord.serviceInfo, compatibilityInfoForPackageLocked(servicerecord.serviceInfo.applicationInfo));
        servicerecord.postNotification();
        if(false) {
            processrecord.services.remove(servicerecord);
            scheduleServiceRestartLocked(servicerecord, false);
        }
        requestServiceBindingsLocked(servicerecord);
        if(servicerecord.startRequested && servicerecord.callStart && servicerecord.pendingStarts.size() == 0)
            servicerecord.pendingStarts.add(new ServiceRecord.StartItem(servicerecord, false, servicerecord.makeNextStartId(), null, null));
        sendServiceArgsLocked(servicerecord, true);
        return;
        exception1;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        if(true) {
            processrecord.services.remove(servicerecord);
            scheduleServiceRestartLocked(servicerecord, false);
        }
        throw exception;
    }

    private void removeContentProviderExternalUnchecked(String s, IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ContentProviderRecord contentproviderrecord = mProviderMap.getProviderByName(s, Binder.getOrigCallingUser());
        if(contentproviderrecord != null) goto _L2; else goto _L1
_L2:
        ContentProviderRecord contentproviderrecord1;
        ComponentName componentname = new ComponentName(((ComponentInfo) (contentproviderrecord.info)).packageName, ((ComponentInfo) (contentproviderrecord.info)).name);
        contentproviderrecord1 = mProviderMap.getProviderByClass(componentname, Binder.getOrigCallingUser());
        if(!contentproviderrecord1.hasExternalProcessHandles()) goto _L4; else goto _L3
_L3:
        if(!contentproviderrecord1.removeExternalProcessHandleLocked(ibinder)) goto _L6; else goto _L5
_L5:
        updateOomAdjLocked();
_L7:
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L6:
        Slog.e("ActivityManager", (new StringBuilder()).append("Attmpt to remove content provider ").append(contentproviderrecord1).append(" with no external reference for token: ").append(ibinder).append(".").toString());
        continue; /* Loop/switch isn't completed */
_L4:
        Slog.e("ActivityManager", (new StringBuilder()).append("Attmpt to remove content provider: ").append(contentproviderrecord1).append(" with no external references.").toString());
        if(true) goto _L7; else goto _L1
_L1:
    }

    private final boolean removeDyingProviderLocked(ProcessRecord processrecord, ContentProviderRecord contentproviderrecord, boolean flag) {
        boolean flag1;
        flag1 = mLaunchingProviders.contains(contentproviderrecord);
        if(flag1 && !flag)
            break MISSING_BLOCK_LABEL_108;
        contentproviderrecord;
        JVM INSTR monitorenter ;
        contentproviderrecord.launchingApp = null;
        contentproviderrecord.notifyAll();
        contentproviderrecord;
        JVM INSTR monitorexit ;
        mProviderMap.removeProviderByClass(contentproviderrecord.name, UserId.getUserId(contentproviderrecord.uid));
        String as[] = contentproviderrecord.info.authority.split(";");
        for(int i = 0; i < as.length; i++)
            mProviderMap.removeProviderByName(as[i], UserId.getUserId(contentproviderrecord.uid));

        break MISSING_BLOCK_LABEL_108;
        Exception exception;
        exception;
        contentproviderrecord;
        JVM INSTR monitorexit ;
        throw exception;
        int j = 0;
        while(j < contentproviderrecord.connections.size())  {
            ContentProviderConnection contentproviderconnection = (ContentProviderConnection)contentproviderrecord.connections.get(j);
            if(!contentproviderconnection.waiting || !flag1 || flag) {
                ProcessRecord processrecord1 = contentproviderconnection.client;
                contentproviderconnection.dead = true;
                if(contentproviderconnection.stableCount > 0) {
                    if(!processrecord1.persistent && processrecord1.thread != null && processrecord1.pid != 0 && processrecord1.pid != MY_PID) {
                        StringBuilder stringbuilder = (new StringBuilder()).append("Kill ").append(processrecord1.processName).append(" (pid ").append(processrecord1.pid).append("): provider ").append(((ComponentInfo) (contentproviderrecord.info)).name).append(" in dying process ");
                        String s;
                        Object aobj[];
                        if(processrecord != null)
                            s = processrecord.processName;
                        else
                            s = "??";
                        Slog.i("ActivityManager", stringbuilder.append(s).toString());
                        aobj = new Object[4];
                        aobj[0] = Integer.valueOf(processrecord1.pid);
                        aobj[1] = processrecord1.processName;
                        aobj[2] = Integer.valueOf(processrecord1.setAdj);
                        aobj[3] = (new StringBuilder()).append("dying provider ").append(contentproviderrecord.name.toShortString()).toString();
                        EventLog.writeEvent(30023, aobj);
                        Process.killProcessQuiet(processrecord1.pid);
                    }
                } else
                if(processrecord1.thread != null && contentproviderconnection.provider.provider != null) {
                    try {
                        processrecord1.thread.unstableProviderDied(contentproviderconnection.provider.provider.asBinder());
                    }
                    catch(RemoteException remoteexception) { }
                    contentproviderrecord.connections.remove(j);
                    contentproviderconnection.client.conProviders.remove(contentproviderconnection);
                }
            }
            j++;
        }
        if(flag1 && flag)
            mLaunchingProviders.remove(contentproviderrecord);
        return flag1;
    }

    private final boolean removeProcessLocked(ProcessRecord processrecord, boolean flag, boolean flag1, String s) {
        String s1 = processrecord.processName;
        int i = processrecord.uid;
        mProcessNames.remove(s1, i);
        mIsolatedProcesses.remove(processrecord.uid);
        if(mHeavyWeightProcess == processrecord) {
            mHeavyWeightProcess = null;
            mHandler.sendEmptyMessage(25);
        }
        boolean flag2 = false;
        if(processrecord.pid > 0 && processrecord.pid != MY_PID) {
            int j = processrecord.pid;
            synchronized(mPidsSelfLocked) {
                mPidsSelfLocked.remove(j);
                mHandler.removeMessages(20, processrecord);
            }
            Slog.i("ActivityManager", (new StringBuilder()).append("Killing proc ").append(processrecord.toShortString()).append(": ").append(s).toString());
            handleAppDiedLocked(processrecord, true, flag1);
            mLruProcesses.remove(processrecord);
            Process.killProcessQuiet(j);
            if(processrecord.persistent && !processrecord.isolated)
                if(!flag)
                    addAppLocked(processrecord.info, false);
                else
                    flag2 = true;
        } else {
            mRemovedProcesses.add(processrecord);
        }
        return flag2;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void reportStartInstrumentationFailure(IInstrumentationWatcher iinstrumentationwatcher, ComponentName componentname, String s) {
        Slog.w("ActivityManager", s);
        if(iinstrumentationwatcher == null)
            break MISSING_BLOCK_LABEL_51;
        Bundle bundle = new Bundle();
        bundle.putString("id", "ActivityManagerService");
        bundle.putString("Error", s);
        iinstrumentationwatcher.instrumentationStatus(componentname, -1, bundle);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("ActivityManager", remoteexception);
          goto _L1
    }

    private final boolean requestServiceBindingLocked(ServiceRecord servicerecord, IntentBindRecord intentbindrecord, boolean flag) {
        boolean flag1 = false;
        if(servicerecord.app != null && servicerecord.app.thread != null) goto _L2; else goto _L1
_L1:
        return flag1;
_L2:
        if(intentbindrecord.requested && !flag || intentbindrecord.apps.size() <= 0)
            break MISSING_BLOCK_LABEL_92;
        bumpServiceExecutingLocked(servicerecord, "bind");
        servicerecord.app.thread.scheduleBindService(servicerecord, intentbindrecord.intent.getIntent(), flag);
        if(!flag)
            intentbindrecord.requested = true;
        intentbindrecord.hasBound = true;
        intentbindrecord.doRebind = false;
        flag1 = true;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private final void requestServiceBindingsLocked(ServiceRecord servicerecord) {
        for(Iterator iterator = servicerecord.bindings.values().iterator(); iterator.hasNext() && requestServiceBindingLocked(servicerecord, (IntentBindRecord)iterator.next(), false););
    }

    private final ActivityRecord resumedAppLocked() {
        ActivityRecord activityrecord = mMainStack.mResumedActivity;
        if(activityrecord == null || activityrecord.app == null) {
            activityrecord = mMainStack.mPausingActivity;
            if(activityrecord == null || activityrecord.app == null)
                activityrecord = mMainStack.topRunningActivityLocked(null);
        }
        return activityrecord;
    }

    private ServiceLookupResult retrieveServiceLocked(Intent intent, String s, int i, int j, int k) {
        ServiceRecord servicerecord;
        servicerecord = null;
        if(intent.getComponent() != null)
            servicerecord = mServiceMap.getServiceByName(intent.getComponent(), k);
        if(servicerecord == null) {
            android.content.Intent.FilterComparison filtercomparison = new android.content.Intent.FilterComparison(intent);
            servicerecord = mServiceMap.getServiceByIntent(filtercomparison, k);
        }
        if(servicerecord != null) goto _L2; else goto _L1
_L1:
        ServiceInfo serviceinfo;
        ResolveInfo resolveinfo = AppGlobals.getPackageManager().resolveService(intent, s, 1024, k);
        if(resolveinfo == null)
            break MISSING_BLOCK_LABEL_695;
        serviceinfo = resolveinfo.serviceInfo;
_L12:
        if(serviceinfo != null) goto _L4; else goto _L3
_L3:
        ServiceLookupResult servicelookupresult;
        Slog.w("ActivityManager", (new StringBuilder()).append("Unable to start service ").append(intent).append(": not found").toString());
        servicelookupresult = null;
          goto _L5
_L4:
        ComponentName componentname;
        ServiceRecord servicerecord1;
        if(k > 0) {
            if(isSingleton(serviceinfo.processName, serviceinfo.applicationInfo))
                k = 0;
            serviceinfo.applicationInfo = getAppInfoForUser(serviceinfo.applicationInfo, k);
        }
        componentname = new ComponentName(serviceinfo.applicationInfo.packageName, ((ComponentInfo) (serviceinfo)).name);
        servicerecord1 = mServiceMap.getServiceByName(componentname, k);
        if(servicerecord1 != null) goto _L7; else goto _L6
_L6:
        android.content.Intent.FilterComparison filtercomparison1;
        ServiceRestarter servicerestarter;
        filtercomparison1 = new android.content.Intent.FilterComparison(intent.cloneFilter());
        servicerestarter = new ServiceRestarter();
        com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv serv;
        synchronized(mBatteryStatsService.getActiveStatistics()) {
            serv = batterystatsimpl.getServiceStatsLocked(serviceinfo.applicationInfo.uid, ((ComponentInfo) (serviceinfo)).packageName, ((ComponentInfo) (serviceinfo)).name);
        }
        servicerecord = new ServiceRecord(this, serv, componentname, filtercomparison1, serviceinfo, servicerestarter);
        int l;
        int i1;
        servicerestarter.setService(servicerecord);
        mServiceMap.putServiceByName(componentname, UserId.getUserId(servicerecord.appInfo.uid), servicerecord);
        mServiceMap.putServiceByIntent(filtercomparison1, UserId.getUserId(servicerecord.appInfo.uid), servicerecord);
        l = mPendingServices.size();
        i1 = 0;
_L11:
        if(i1 >= l) goto _L2; else goto _L8
_L8:
        if(!((ServiceRecord)mPendingServices.get(i1)).name.equals(componentname)) goto _L10; else goto _L9
_L9:
        mPendingServices.remove(i1);
        i1--;
        l--;
_L10:
        i1++;
          goto _L11
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        try {
            throw exception;
        }
        catch(RemoteException remoteexception1) {
            servicerecord = servicerecord1;
        }
_L2:
        if(servicerecord != null) {
            if(checkComponentPermission(servicerecord.permission, i, j, servicerecord.appInfo.uid, servicerecord.exported) != 0) {
                if(!servicerecord.exported) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Permission Denial: Accessing service ").append(servicerecord.name).append(" from pid=").append(i).append(", uid=").append(j).append(" that is not exported from uid ").append(servicerecord.appInfo.uid).toString());
                    servicelookupresult = new ServiceLookupResult(null, (new StringBuilder()).append("not exported from uid ").append(servicerecord.appInfo.uid).toString());
                } else {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Permission Denial: Accessing service ").append(servicerecord.name).append(" from pid=").append(i).append(", uid=").append(j).append(" requires ").append(servicerecord.permission).toString());
                    servicelookupresult = new ServiceLookupResult(null, servicerecord.permission);
                }
            } else {
                servicelookupresult = new ServiceLookupResult(servicerecord, null);
            }
        } else {
            servicelookupresult = null;
        }
        break; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        continue; /* Loop/switch isn't completed */
_L7:
        servicerecord = servicerecord1;
        if(true) goto _L2; else goto _L5
_L5:
        return servicelookupresult;
        serviceinfo = null;
          goto _L12
    }

    private void retrieveSettings() {
        android.content.ContentResolver contentresolver = mContext.getContentResolver();
        String s = android.provider.Settings.System.getString(contentresolver, "debug_app");
        boolean flag;
        boolean flag1;
        Configuration configuration;
        if(android.provider.Settings.System.getInt(contentresolver, "wait_for_debugger", 0) != 0)
            flag = true;
        else
            flag = false;
        if(android.provider.Settings.System.getInt(contentresolver, "always_finish_activities", 0) != 0)
            flag1 = true;
        else
            flag1 = false;
        configuration = new Configuration();
        android.provider.Settings.System.getConfiguration(contentresolver, configuration);
        this;
        JVM INSTR monitorenter ;
        mOrigDebugApp = s;
        mDebugApp = s;
        mOrigWaitForDebugger = flag;
        mWaitForDebugger = flag;
        mAlwaysFinishActivities = flag1;
        updateConfigurationLocked(configuration, null, false, true);
        return;
    }

    private void revokeUriPermissionLocked(int i, Uri uri, int j) {
        int k = j & 3;
        if(k != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        IPackageManager ipackagemanager;
        String s;
        ProviderInfo providerinfo;
        int l;
        ContentProviderRecord contentproviderrecord;
        ipackagemanager = AppGlobals.getPackageManager();
        s = uri.getAuthority();
        providerinfo = null;
        l = UserId.getUserId(i);
        contentproviderrecord = mProviderMap.getProviderByName(s, l);
        if(contentproviderrecord == null) goto _L4; else goto _L3
_L3:
        providerinfo = contentproviderrecord.info;
_L5:
        ProviderInfo providerinfo1;
        if(providerinfo == null) {
            Slog.w("ActivityManager", (new StringBuilder()).append("No content provider found for permission revoke: ").append(uri.toSafeString()).toString());
        } else {
            if(!checkHoldingPermissionsLocked(ipackagemanager, providerinfo, uri, i, k))
                throw new SecurityException((new StringBuilder()).append("Uid ").append(i).append(" does not have permission to uri ").append(uri).toString());
            List list = uri.getPathSegments();
            if(list != null) {
                int i1 = list.size();
                int j1 = mGrantedUriPermissions.size();
                int k1 = 0;
                while(k1 < j1)  {
                    HashMap hashmap = (HashMap)mGrantedUriPermissions.valueAt(k1);
                    Iterator iterator = hashmap.values().iterator();
label0:
                    do {
                        if(!iterator.hasNext())
                            break;
                        UriPermission uripermission = (UriPermission)iterator.next();
                        Uri uri1 = uripermission.uri;
                        if(!s.equals(uri1.getAuthority()))
                            continue;
                        List list1 = uri1.getPathSegments();
                        if(list1 == null || list1.size() < i1)
                            continue;
                        for(int l1 = 0; l1 < i1; l1++)
                            if(!((String)list.get(l1)).equals(list1.get(l1)))
                                continue label0;

                        uripermission.clearModes(k);
                        if(uripermission.modeFlags == 0)
                            iterator.remove();
                    } while(true);
                    if(hashmap.size() == 0) {
                        mGrantedUriPermissions.remove(mGrantedUriPermissions.keyAt(k1));
                        j1--;
                        k1--;
                    }
                    k1++;
                }
            }
        }
        if(true) goto _L1; else goto _L4
_L4:
        providerinfo1 = ipackagemanager.resolveContentProvider(s, 2048, l);
        providerinfo = providerinfo1;
          goto _L5
        RemoteException remoteexception;
        remoteexception;
          goto _L5
    }

    private void saveLocaleLocked(Locale locale, boolean flag, boolean flag1) {
        if(flag) {
            SystemProperties.set("user.language", locale.getLanguage());
            SystemProperties.set("user.region", locale.getCountry());
        }
        if(flag1) {
            SystemProperties.set("persist.sys.language", locale.getLanguage());
            SystemProperties.set("persist.sys.country", locale.getCountry());
            SystemProperties.set("persist.sys.localevar", locale.getVariant());
        }
    }

    private static boolean scanArgs(String as[], String s) {
        int i;
        int j;
        if(as == null)
            break MISSING_BLOCK_LABEL_37;
        i = as.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_37;
        if(!s.equals(as[j])) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private final boolean scheduleServiceRestartLocked(ServiceRecord servicerecord, boolean flag) {
        int j;
        boolean flag1 = false;
        long l = SystemClock.uptimeMillis();
        long l1 = 5000L;
        long l2 = 60000L;
        if((8 & servicerecord.serviceInfo.applicationInfo.flags) != 0)
            l1 /= 4L;
        int i = servicerecord.deliveredStarts.size();
        if(i > 0) {
            int k = i - 1;
            while(k >= 0)  {
                ServiceRecord.StartItem startitem = (ServiceRecord.StartItem)servicerecord.deliveredStarts.get(k);
                startitem.removeUriPermissionsLocked();
                if(startitem.intent != null)
                    if(!flag || startitem.deliveryCount < 3 && startitem.doneExecutingCount < 6) {
                        servicerecord.pendingStarts.add(0, startitem);
                        long l3 = 2L * (SystemClock.uptimeMillis() - startitem.deliveredTime);
                        if(l1 < l3)
                            l1 = l3;
                        if(l2 < l3)
                            l2 = l3;
                    } else {
                        Slog.w("ActivityManager", (new StringBuilder()).append("Canceling start item ").append(startitem.intent).append(" in service ").append(servicerecord.name).toString());
                        flag1 = true;
                    }
                k--;
            }
            servicerecord.deliveredStarts.clear();
        }
        servicerecord.totalRestartCount = 1 + servicerecord.totalRestartCount;
        boolean flag2;
        Object aobj[];
        ServiceRecord servicerecord1;
        if(servicerecord.restartDelay == 0L) {
            servicerecord.restartCount = 1 + servicerecord.restartCount;
            servicerecord.restartDelay = l1;
        } else
        if(l > l2 + servicerecord.restartTime) {
            servicerecord.restartCount = 1;
            servicerecord.restartDelay = l1;
        } else
        if((8 & servicerecord.serviceInfo.applicationInfo.flags) != 0) {
            servicerecord.restartDelay = servicerecord.restartDelay + l1 / 2L;
        } else {
            servicerecord.restartDelay = 4L * servicerecord.restartDelay;
            if(servicerecord.restartDelay < l1)
                servicerecord.restartDelay = l1;
        }
        servicerecord.nextRestartTime = l + servicerecord.restartDelay;
        flag2 = false;
        j = -1 + mRestartingServices.size();
_L4:
        if(j < 0)
            continue; /* Loop/switch isn't completed */
        servicerecord1 = (ServiceRecord)mRestartingServices.get(j);
        if(servicerecord1 == servicerecord || servicerecord.nextRestartTime < servicerecord1.nextRestartTime - 10000L || servicerecord.nextRestartTime >= 10000L + servicerecord1.nextRestartTime)
            break MISSING_BLOCK_LABEL_620;
        servicerecord.nextRestartTime = 10000L + servicerecord1.nextRestartTime;
        servicerecord.restartDelay = servicerecord.nextRestartTime - l;
        flag2 = true;
        if(flag2) goto _L2; else goto _L1
_L1:
        break MISSING_BLOCK_LABEL_381;
_L2:
        break MISSING_BLOCK_LABEL_275;
        if(!mRestartingServices.contains(servicerecord))
            mRestartingServices.add(servicerecord);
        servicerecord.cancelNotification();
        mHandler.removeCallbacks(servicerecord.restarter);
        mHandler.postAtTime(servicerecord.restarter, servicerecord.nextRestartTime);
        servicerecord.nextRestartTime = SystemClock.uptimeMillis() + servicerecord.restartDelay;
        Slog.w("ActivityManager", (new StringBuilder()).append("Scheduling restart of crashed service ").append(servicerecord.shortName).append(" in ").append(servicerecord.restartDelay).append("ms").toString());
        aobj = new Object[2];
        aobj[0] = servicerecord.shortName;
        aobj[1] = Long.valueOf(servicerecord.restartDelay);
        EventLog.writeEvent(30035, aobj);
        return flag1;
        j--;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static ActivityManagerService self() {
        return mSelf;
    }

    private final void sendPackageBroadcastLocked(int i, String as[]) {
        int j = -1 + mLruProcesses.size();
        while(j >= 0)  {
            ProcessRecord processrecord = (ProcessRecord)mLruProcesses.get(j);
            if(processrecord.thread != null)
                try {
                    processrecord.thread.dispatchPackageBroadcast(i, as);
                }
                catch(RemoteException remoteexception) { }
            j--;
        }
    }

    private final void sendServiceArgsLocked(ServiceRecord servicerecord, boolean flag) {
        int i = servicerecord.pendingStarts.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(servicerecord.pendingStarts.size() <= 0)
            continue; /* Loop/switch isn't completed */
        ServiceRecord.StartItem startitem = (ServiceRecord.StartItem)servicerecord.pendingStarts.remove(0);
        if(startitem.intent != null || i <= 1) {
            startitem.deliveredTime = SystemClock.uptimeMillis();
            servicerecord.deliveredStarts.add(startitem);
            startitem.deliveryCount = 1 + startitem.deliveryCount;
            if(startitem.neededGrants != null)
                grantUriPermissionUncheckedFromIntentLocked(startitem.neededGrants, startitem.getUriPermissionsLocked());
            bumpServiceExecutingLocked(servicerecord, "start");
            if(!flag) {
                flag = true;
                updateOomAdjLocked(servicerecord.app);
            }
            int j = 0;
            if(startitem.deliveryCount > 1)
                j = 0 | 2;
            if(startitem.doneExecutingCount > 0)
                j |= 1;
            servicerecord.app.thread.scheduleServiceArgs(servicerecord, startitem.taskRemoved, startitem.id, j, startitem.intent);
        }
        if(true) goto _L2; else goto _L3
_L3:
        RemoteException remoteexception;
        remoteexception;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        Slog.w("ActivityManager", "Unexpected exception", exception);
        if(true) goto _L1; else goto _L4
_L4:
    }

    public static void setSystemProcess() {
        ApplicationInfo applicationinfo;
        ActivityManagerService activitymanagerservice = mSelf;
        ServiceManager.addService("activity", activitymanagerservice, true);
        ServiceManager.addService("meminfo", new MemBinder(activitymanagerservice));
        ServiceManager.addService("gfxinfo", new GraphicsBinder(activitymanagerservice));
        ServiceManager.addService("dbinfo", new DbBinder(activitymanagerservice));
        ServiceManager.addService("cpuinfo", new CpuBinder(activitymanagerservice));
        ServiceManager.addService("permission", new PermissionController(activitymanagerservice));
        applicationinfo = mSelf.mContext.getPackageManager().getApplicationInfo("android", 1024);
        mSystemThread.installSystemApplicationInfo(applicationinfo);
        ActivityManagerService activitymanagerservice1 = mSelf;
        activitymanagerservice1;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord;
        processrecord = mSelf.newProcessRecordLocked(mSystemThread.getApplicationThread(), applicationinfo, applicationinfo.processName, false);
        processrecord.persistent = true;
        processrecord.pid = MY_PID;
        processrecord.maxAdj = -16;
        mSelf.mProcessNames.put(processrecord.processName, processrecord.uid, processrecord);
        synchronized(mSelf.mPidsSelfLocked) {
            mSelf.mPidsSelfLocked.put(processrecord.pid, processrecord);
        }
        mSelf.updateLruProcessLocked(processrecord, true, true);
        activitymanagerservice1;
        JVM INSTR monitorexit ;
        return;
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        try {
            throw exception;
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            throw new RuntimeException("Unable to find android system package", namenotfoundexception);
        }
    }

    private static final boolean shouldShowDialogs(Configuration configuration) {
        boolean flag = true;
        if(configuration.keyboard == flag && configuration.touchscreen == flag)
            flag = false;
        return flag;
    }

    private void showAppCrashDialog(HashMap hashmap) {
        ProcessRecord processrecord = (ProcessRecord)hashmap.get("app");
        AppErrorResult apperrorresult = (AppErrorResult)hashmap.get("result");
        android.app.ApplicationErrorReport.CrashInfo crashinfo = (android.app.ApplicationErrorReport.CrashInfo)hashmap.get("crash");
        if(miui.provider.ExtraSettings.Secure.isForceCloseDialogEnabled(mContext)) {
            AppErrorDialog apperrordialog = new AppErrorDialog(mContext, apperrorresult, processrecord, crashinfo);
            apperrordialog.show();
            processrecord.crashDialog = apperrordialog;
        } else {
            if(processrecord != null && crashinfo != null)
                MiuiErrorReport.sendFcErrorReport(mContext, processrecord, crashinfo, false);
            mMainStack.moveHomeToFrontLocked();
            apperrorresult.set(0);
        }
    }

    private final void startProcessLocked(ProcessRecord processrecord, String s, String s1) {
        if(processrecord.pid > 0 && processrecord.pid != MY_PID) {
            synchronized(mPidsSelfLocked) {
                mPidsSelfLocked.remove(processrecord.pid);
                mHandler.removeMessages(20, processrecord);
            }
            processrecord.pid = 0;
        }
        mProcessesOnHold.remove(processrecord);
        updateCpuStats();
        System.arraycopy(mProcDeaths, 0, mProcDeaths, 1, -1 + mProcDeaths.length);
        mProcDeaths[0] = 0;
        int i;
        int ai[];
        boolean flag;
        i = processrecord.uid;
        ai = null;
        flag = processrecord.isolated;
        if(flag)
            break MISSING_BLOCK_LABEL_139;
        int ai1[] = mContext.getPackageManager().getPackageGids(processrecord.info.packageName);
        ai = ai1;
_L7:
        int j;
        if(mFactoryTest != 0) {
            if(mFactoryTest == 1 && mTopComponent != null && processrecord.processName.equals(mTopComponent.getPackageName()))
                i = 0;
            if(mFactoryTest == 2 && (0x10 & processrecord.info.flags) != 0)
                i = 0;
        }
        j = 0;
        if((2 & processrecord.info.flags) != 0)
            j = 2 | (false | true);
        RuntimeException runtimeexception;
        String s2;
        int k;
        android.os.Process.ProcessStartResult processstartresult;
        Object aobj[];
        String s3;
        StringBuilder stringbuilder;
        SparseArray sparsearray;
        int i1;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        if((0x4000 & processrecord.info.flags) != 0 || Zygote.systemInSafeMode)
            j |= 8;
        if("1".equals(SystemProperties.get("debug.checkjni")))
            j |= 2;
        if("1".equals(SystemProperties.get("debug.jni.logging")))
            j |= 0x10;
        if("1".equals(SystemProperties.get("debug.assert")))
            j |= 4;
        s2 = processrecord.processName;
        k = processrecord.info.targetSdkVersion;
        processstartresult = Process.start("android.app.ActivityThread", s2, i, i, ai, j, k, null);
        synchronized(processrecord.batteryStats.getBatteryStats()) {
            if(batterystatsimpl.isOnBattery())
                processrecord.batteryStats.incStartsLocked();
        }
        aobj = new Object[5];
        aobj[0] = Integer.valueOf(processstartresult.pid);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = processrecord.processName;
        aobj[3] = s;
        if(s1 == null) goto _L2; else goto _L1
_L1:
        s3 = s1;
_L9:
        aobj[4] = s3;
        EventLog.writeEvent(30014, aobj);
        if(processrecord.persistent)
            Watchdog.getInstance().processStarted(processrecord.processName, processstartresult.pid);
        stringbuilder = mStringBuilder;
        stringbuilder.setLength(0);
        stringbuilder.append("Start proc ");
        stringbuilder.append(processrecord.processName);
        stringbuilder.append(" for ");
        stringbuilder.append(s);
        if(s1 != null) {
            stringbuilder.append(" ");
            stringbuilder.append(s1);
        }
        stringbuilder.append(": pid=");
        stringbuilder.append(processstartresult.pid);
        stringbuilder.append(" uid=");
        stringbuilder.append(i);
        stringbuilder.append(" gids={");
        if(ai == null) goto _L4; else goto _L3
_L3:
        i1 = 0;
_L6:
        if(i1 >= ai.length) goto _L4; else goto _L5
_L5:
        if(i1 != 0)
            stringbuilder.append(", ");
        stringbuilder.append(ai[i1]);
        i1++;
          goto _L6
        exception1;
        sparsearray1;
        JVM INSTR monitorexit ;
        throw exception1;
        namenotfoundexception;
        Slog.w("ActivityManager", "Unable to retrieve gids", namenotfoundexception);
          goto _L7
        runtimeexception;
        processrecord.pid = 0;
        Slog.e("ActivityManager", (new StringBuilder()).append("Failure starting process ").append(processrecord.processName).toString(), runtimeexception);
_L8:
        return;
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        stringbuilder.append("}");
        Slog.i("ActivityManager", stringbuilder.toString());
        processrecord.pid = processstartresult.pid;
        processrecord.usingWrapper = processstartresult.usingWrapper;
        processrecord.removed = false;
        sparsearray = mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        mPidsSelfLocked.put(processstartresult.pid, processrecord);
        Message message = mHandler.obtainMessage(20);
        message.obj = processrecord;
        Handler handler = mHandler;
        long l;
        if(processstartresult.usingWrapper)
            l = 0x493e0L;
        else
            l = 10000L;
        handler.sendMessageDelayed(message, l);
        if(true) goto _L8; else goto _L2
_L2:
        s3 = "";
          goto _L9
    }

    private void stopProfilerLocked(ProcessRecord processrecord, String s, int i) {
        if(processrecord == null || processrecord == mProfileProc) {
            processrecord = mProfileProc;
            s = mProfileFile;
            i = mProfileType;
            clearProfilerLocked();
        }
        if(processrecord != null)
            try {
                processrecord.thread.profilerControl(false, s, null, i);
            }
            catch(RemoteException remoteexception) {
                throw new IllegalStateException("Process disappeared");
            }
    }

    private void stopServiceLocked(ServiceRecord servicerecord) {
        synchronized(servicerecord.stats.getBatteryStats()) {
            servicerecord.stats.stopRunningLocked();
        }
        servicerecord.startRequested = false;
        servicerecord.callStart = false;
        bringDownServiceLocked(servicerecord, false);
        return;
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private TaskRecord taskForIdLocked(int i) {
        int j;
        int k;
        j = mRecentTasks.size();
        k = 0;
_L3:
        TaskRecord taskrecord;
        if(k >= j)
            break MISSING_BLOCK_LABEL_46;
        taskrecord = (TaskRecord)mRecentTasks.get(k);
        if(taskrecord.taskId != i) goto _L2; else goto _L1
_L1:
        return taskrecord;
_L2:
        k++;
          goto _L3
        taskrecord = null;
          goto _L1
    }

    private final boolean unscheduleServiceRestartLocked(ServiceRecord servicerecord) {
        boolean flag;
        if(servicerecord.restartDelay == 0L) {
            flag = false;
        } else {
            servicerecord.resetRestartCounter();
            mRestartingServices.remove(servicerecord);
            mHandler.removeCallbacks(servicerecord.restarter);
            flag = true;
        }
        return flag;
    }

    private void updateEventDispatchingLocked() {
        WindowManagerService windowmanagerservice = mWindowManager;
        boolean flag;
        if(mBooted && !mWentToSleep && !mShuttingDown)
            flag = true;
        else
            flag = false;
        windowmanagerservice.setEventDispatching(flag);
    }

    private final void updateLruProcessInternalLocked(ProcessRecord processrecord, boolean flag, boolean flag1, int i) {
        int j = mLruProcesses.indexOf(processrecord);
        if(j >= 0)
            mLruProcesses.remove(j);
        int k = -1 + mLruProcesses.size();
        int l = 0;
        processrecord.lruSeq = mLruSeq;
        if(flag1)
            processrecord.lastActivityTime = SystemClock.uptimeMillis();
        if(processrecord.activities.size() > 0)
            processrecord.lruWeight = processrecord.lastActivityTime;
        else
        if(processrecord.pubProviders.size() > 0) {
            processrecord.lruWeight = processrecord.lastActivityTime - 15000L;
            l = 2;
        } else {
            processrecord.lruWeight = processrecord.lastActivityTime - 0x1d4c0L;
            l = 2;
        }
label0:
        do {
label1:
            {
                if(k >= 0) {
                    ProcessRecord processrecord1 = (ProcessRecord)mLruProcesses.get(k);
                    if(l > 0 && processrecord1.setAdj >= ProcessList.HIDDEN_APP_MIN_ADJ)
                        l--;
                    if(processrecord1.lruWeight > processrecord.lruWeight && k >= i)
                        break label1;
                    mLruProcesses.add(k + 1, processrecord);
                }
                if(k < 0)
                    mLruProcesses.add(0, processrecord);
                if(processrecord.connections.size() > 0) {
                    Iterator iterator = processrecord.connections.iterator();
                    do {
                        if(!iterator.hasNext())
                            break;
                        ConnectionRecord connectionrecord = (ConnectionRecord)iterator.next();
                        if(connectionrecord.binding != null && connectionrecord.binding.service != null && connectionrecord.binding.service.app != null && connectionrecord.binding.service.app.lruSeq != mLruSeq)
                            updateLruProcessInternalLocked(connectionrecord.binding.service.app, flag, flag1, k + 1);
                    } while(true);
                }
                break label0;
            }
            k--;
        } while(true);
        for(int i1 = -1 + processrecord.conProviders.size(); i1 >= 0; i1--) {
            ContentProviderRecord contentproviderrecord = ((ContentProviderConnection)processrecord.conProviders.get(i1)).provider;
            if(contentproviderrecord.proc != null && contentproviderrecord.proc.lruSeq != mLruSeq)
                updateLruProcessInternalLocked(contentproviderrecord.proc, flag, flag1, k + 1);
        }

        if(flag)
            updateOomAdjLocked();
    }

    private final boolean updateOomAdjLocked(ProcessRecord processrecord) {
        ActivityRecord activityrecord = resumedAppLocked();
        ProcessRecord processrecord1;
        int i;
        boolean flag;
        boolean flag1;
        boolean flag2;
        if(activityrecord != null)
            processrecord1 = activityrecord.app;
        else
            processrecord1 = null;
        i = processrecord.curAdj;
        if(i >= ProcessList.HIDDEN_APP_MIN_ADJ && i <= 15)
            flag = true;
        else
            flag = false;
        mAdjSeq = 1 + mAdjSeq;
        flag1 = updateOomAdjLocked(processrecord, processrecord.hiddenAdj, processrecord1, false);
        if(processrecord.curAdj >= ProcessList.HIDDEN_APP_MIN_ADJ && processrecord.curAdj <= 15)
            flag2 = true;
        else
            flag2 = false;
        if(flag2 != flag)
            updateOomAdjLocked();
        return flag1;
    }

    private final boolean updateOomAdjLocked(ProcessRecord processrecord, int i, ProcessRecord processrecord1, boolean flag) {
        processrecord.hiddenAdj = i;
        boolean flag2;
        if(processrecord.thread == null) {
            flag2 = false;
        } else {
            boolean flag1 = processrecord.keeping;
            flag2 = true;
            computeOomAdjLocked(processrecord, i, processrecord1, false, flag);
            if(processrecord.curRawAdj != processrecord.setRawAdj) {
                if(flag1 && !processrecord.keeping) {
                    synchronized(mBatteryStatsService.getActiveStatistics()) {
                        processrecord.lastWakeTime = batterystatsimpl.getProcessWakeTime(processrecord.info.uid, processrecord.pid, SystemClock.elapsedRealtime());
                    }
                    processrecord.lastCpuTime = processrecord.curCpuTime;
                }
                processrecord.setRawAdj = processrecord.curRawAdj;
            }
            if(processrecord.curAdj != processrecord.setAdj)
                if(Process.setOomAdj(processrecord.pid, processrecord.curAdj)) {
                    processrecord.setAdj = processrecord.curAdj;
                } else {
                    flag2 = false;
                    Slog.w("ActivityManager", (new StringBuilder()).append("Failed setting oom adj of ").append(processrecord).append(" to ").append(processrecord.curAdj).toString());
                }
            if(processrecord.setSchedGroup != processrecord.curSchedGroup) {
                processrecord.setSchedGroup = processrecord.curSchedGroup;
                if(processrecord.waitingToKill == null || processrecord.setSchedGroup != 0)
                    break MISSING_BLOCK_LABEL_347;
                Slog.i("ActivityManager", (new StringBuilder()).append("Killing ").append(processrecord.toShortString()).append(": ").append(processrecord.waitingToKill).toString());
                Object aobj[] = new Object[4];
                aobj[0] = Integer.valueOf(processrecord.pid);
                aobj[1] = processrecord.processName;
                aobj[2] = Integer.valueOf(processrecord.setAdj);
                aobj[3] = processrecord.waitingToKill;
                EventLog.writeEvent(30023, aobj);
                processrecord.killedBackground = true;
                Process.killProcessQuiet(processrecord.pid);
                flag2 = false;
            }
        }
_L2:
        return flag2;
        exception2;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception2;
        long l = Binder.clearCallingIdentity();
        Process.setProcessGroup(processrecord.pid, processrecord.curSchedGroup);
_L3:
        Binder.restoreCallingIdentity(l);
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception1;
        exception1;
        Slog.w("ActivityManager", (new StringBuilder()).append("Failed setting process group of ").append(processrecord.pid).append(" to ").append(processrecord.curSchedGroup).toString());
        exception1.printStackTrace();
          goto _L3
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private boolean userExists(int i) {
        boolean flag = false;
        UserInfo userinfo = AppGlobals.getPackageManager().getUser(i);
        if(userinfo != null)
            flag = true;
_L2:
        return flag;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static void writeLastDonePreBootReceivers(ArrayList arraylist) {
        File file;
        FileOutputStream fileoutputstream;
        DataOutputStream dataoutputstream;
        file = getCalledPreBootReceiversFile();
        fileoutputstream = null;
        dataoutputstream = null;
        FileOutputStream fileoutputstream1;
        Slog.i("ActivityManager", "Writing new set of last done pre-boot receivers...");
        fileoutputstream1 = new FileOutputStream(file);
        DataOutputStream dataoutputstream1 = new DataOutputStream(new BufferedOutputStream(fileoutputstream1, 2048));
        int i;
        dataoutputstream1.writeInt(10000);
        dataoutputstream1.writeUTF(android.os.Build.VERSION.RELEASE);
        dataoutputstream1.writeUTF(android.os.Build.VERSION.CODENAME);
        dataoutputstream1.writeUTF(android.os.Build.VERSION.INCREMENTAL);
        dataoutputstream1.writeInt(arraylist.size());
        i = 0;
_L1:
        if(i >= arraylist.size())
            break MISSING_BLOCK_LABEL_141;
        dataoutputstream1.writeUTF(((ComponentName)arraylist.get(i)).getPackageName());
        dataoutputstream1.writeUTF(((ComponentName)arraylist.get(i)).getClassName());
        i++;
          goto _L1
        FileUtils.sync(fileoutputstream1);
        if(dataoutputstream1 == null)
            break MISSING_BLOCK_LABEL_157;
        dataoutputstream1.close();
_L2:
        return;
        IOException ioexception1;
        ioexception1;
_L4:
        Slog.w("ActivityManager", "Failure writing last done pre-boot receivers", ioexception1);
        file.delete();
        FileUtils.sync(fileoutputstream);
        if(dataoutputstream != null)
            try {
                dataoutputstream.close();
            }
            catch(IOException ioexception2) {
                ioexception2.printStackTrace();
            }
          goto _L2
        Exception exception;
        exception;
_L3:
        FileUtils.sync(fileoutputstream);
        if(dataoutputstream != null)
            try {
                dataoutputstream.close();
            }
            catch(IOException ioexception) {
                ioexception.printStackTrace();
            }
        throw exception;
        IOException ioexception3;
        ioexception3;
        ioexception3.printStackTrace();
          goto _L2
        exception;
        fileoutputstream = fileoutputstream1;
          goto _L3
        exception;
        dataoutputstream = dataoutputstream1;
        fileoutputstream = fileoutputstream1;
          goto _L3
        ioexception1;
        fileoutputstream = fileoutputstream1;
          goto _L4
        ioexception1;
        dataoutputstream = dataoutputstream1;
        fileoutputstream = fileoutputstream1;
          goto _L4
    }

    public final void activityDestroyed(IBinder ibinder) {
        mMainStack.activityDestroyed(ibinder);
    }

    public final void activityIdle(IBinder ibinder, Configuration configuration, boolean flag) {
        long l;
        ActivityRecord activityrecord;
        l = Binder.clearCallingIdentity();
        activityrecord = mMainStack.activityIdleInternal(ibinder, false, configuration);
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorenter ;
        ParcelFileDescriptor parcelfiledescriptor;
        if(mProfileProc != activityrecord.app)
            break MISSING_BLOCK_LABEL_57;
        parcelfiledescriptor = mProfileFd;
        if(parcelfiledescriptor == null)
            break MISSING_BLOCK_LABEL_57;
        Exception exception;
        try {
            mProfileFd.close();
        }
        catch(IOException ioexception) { }
        clearProfilerLocked();
        this;
        JVM INSTR monitorexit ;
_L2:
        Binder.restoreCallingIdentity(l);
        return;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public final void activityPaused(IBinder ibinder) {
        long l = Binder.clearCallingIdentity();
        mMainStack.activityPaused(ibinder, false);
        Binder.restoreCallingIdentity(l);
    }

    public final void activitySlept(IBinder ibinder) {
        long l = Binder.clearCallingIdentity();
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord != null)
            mMainStack.activitySleptLocked(activityrecord);
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public final void activityStopped(IBinder ibinder, Bundle bundle, Bitmap bitmap, CharSequence charsequence) {
        if(bundle != null && bundle.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Bundle");
        long l = Binder.clearCallingIdentity();
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord;
        activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord != null)
            activityrecord.stack.activityStoppedLocked(activityrecord, bundle, bitmap, charsequence);
        this;
        JVM INSTR monitorexit ;
        if(activityrecord != null)
            sendPendingThumbnail(activityrecord, null, null, null, false);
        trimApplications();
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    final ProcessRecord addAppLocked(ApplicationInfo applicationinfo, boolean flag) {
        ProcessRecord processrecord;
        if(!flag)
            processrecord = getProcessRecordLocked(applicationinfo.processName, applicationinfo.uid);
        else
            processrecord = null;
        if(processrecord == null) {
            processrecord = newProcessRecordLocked(null, applicationinfo, null, flag);
            mProcessNames.put(applicationinfo.processName, processrecord.uid, processrecord);
            if(flag)
                mIsolatedProcesses.put(processrecord.uid, processrecord);
            updateLruProcessLocked(processrecord, true, true);
        }
        try {
            AppGlobals.getPackageManager().setPackageStoppedState(applicationinfo.packageName, false, UserId.getUserId(processrecord.uid));
        }
        catch(RemoteException remoteexception) { }
        catch(IllegalArgumentException illegalargumentexception) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Failed trying to unstop package ").append(applicationinfo.packageName).append(": ").append(illegalargumentexception).toString());
        }
        if((9 & applicationinfo.flags) == 9) {
            processrecord.persistent = true;
            processrecord.maxAdj = -12;
        }
        if(processrecord.thread == null && mPersistentStartingProcesses.indexOf(processrecord) < 0) {
            mPersistentStartingProcesses.add(processrecord);
            startProcessLocked(processrecord, "added application", processrecord.processName);
        }
        return processrecord;
    }

    public void addErrorToDropBox(String s, ProcessRecord processrecord, String s1, ActivityRecord activityrecord, ActivityRecord activityrecord1, String s2, String s3, 
            File file, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        String s4 = (new StringBuilder()).append(processClass(processrecord)).append("_").append(s).toString();
        DropBoxManager dropboxmanager = (DropBoxManager)mContext.getSystemService("dropbox");
        if(dropboxmanager != null && dropboxmanager.isTagEnabled(s4)) {
            StringBuilder stringbuilder = new StringBuilder(1024);
            appendDropBoxProcessHeaders(processrecord, s1, stringbuilder);
            if(activityrecord != null)
                stringbuilder.append("Activity: ").append(activityrecord.shortComponentName).append("\n");
            if(activityrecord1 != null && activityrecord1.app != null && activityrecord1.app.pid != processrecord.pid)
                stringbuilder.append("Parent-Process: ").append(activityrecord1.app.processName).append("\n");
            if(activityrecord1 != null && activityrecord1 != activityrecord)
                stringbuilder.append("Parent-Activity: ").append(activityrecord1.shortComponentName).append("\n");
            if(s2 != null)
                stringbuilder.append("Subject: ").append(s2).append("\n");
            stringbuilder.append("Build: ").append(Build.FINGERPRINT).append("\n");
            if(Debug.isDebuggerConnected())
                stringbuilder.append("Debugger: Connected\n");
            stringbuilder.append("\n");
            Thread thread = new Thread(dropboxmanager) {

                public void run() {
                    InputStreamReader inputstreamreader;
                    if(report != null)
                        sb.append(report);
                    String s5;
                    int i;
                    String as[];
                    Process process;
                    char ac[];
                    int j;
                    if(logFile != null)
                        try {
                            sb.append(FileUtils.readTextFile(logFile, 0x20000, "\n\n[[TRUNCATED]]"));
                        }
                        catch(IOException ioexception5) {
                            Slog.e("ActivityManager", (new StringBuilder()).append("Error reading ").append(logFile).toString(), ioexception5);
                        }
                    if(crashInfo != null && crashInfo.stackTrace != null)
                        sb.append(crashInfo.stackTrace);
                    s5 = (new StringBuilder()).append("logcat_for_").append(dropboxTag).toString();
                    i = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), s5, 0);
                    if(i <= 0) goto _L2; else goto _L1
_L1:
                    sb.append("\n");
                    inputstreamreader = null;
                    as = new String[11];
                    as[0] = "/system/bin/logcat";
                    as[1] = "-v";
                    as[2] = "time";
                    as[3] = "-b";
                    as[4] = "events";
                    as[5] = "-b";
                    as[6] = "system";
                    as[7] = "-b";
                    as[8] = "main";
                    as[9] = "-t";
                    as[10] = String.valueOf(i);
                    process = (new ProcessBuilder(as)).redirectErrorStream(true).start();
                    IOException ioexception;
                    Exception exception;
                    IOException ioexception1;
                    IOException ioexception2;
                    InputStreamReader inputstreamreader1;
                    try {
                        process.getOutputStream().close();
                    }
                    catch(IOException ioexception3) { }
                    try {
                        process.getErrorStream().close();
                    }
                    catch(IOException ioexception4) { }
                    inputstreamreader1 = new InputStreamReader(process.getInputStream());
                    ac = new char[8192];
_L5:
                    j = inputstreamreader1.read(ac);
                    if(j <= 0) goto _L4; else goto _L3
_L3:
                    sb.append(ac, 0, j);
                      goto _L5
                    ioexception;
                    inputstreamreader = inputstreamreader1;
_L9:
                    Slog.e("ActivityManager", "Error running logcat", ioexception);
                    if(inputstreamreader != null)
                        try {
                            inputstreamreader.close();
                        }
                        // Misplaced declaration of an exception variable
                        catch(IOException ioexception2) { }
_L2:
                    dbox.addText(dropboxTag, sb.toString());
                    return;
                    exception;
_L7:
                    if(inputstreamreader != null)
                        try {
                            inputstreamreader.close();
                        }
                        // Misplaced declaration of an exception variable
                        catch(IOException ioexception1) { }
                    throw exception;
_L4:
                    if(inputstreamreader1 == null) goto _L2; else goto _L6
_L6:
                    inputstreamreader1.close();
                      goto _L2
                    exception;
                    inputstreamreader = inputstreamreader1;
                      goto _L7
                    ioexception;
                    if(true) goto _L9; else goto _L8
_L8:
                }

                final ActivityManagerService this$0;
                final android.app.ApplicationErrorReport.CrashInfo val$crashInfo;
                final DropBoxManager val$dbox;
                final String val$dropboxTag;
                final File val$logFile;
                final String val$report;
                final StringBuilder val$sb;

             {
                this$0 = ActivityManagerService.this;
                report = s1;
                sb = stringbuilder;
                logFile = file;
                crashInfo = crashinfo;
                dropboxTag = s2;
                dbox = dropboxmanager;
                super(final_s);
            }
            };
            if(processrecord == null)
                thread.run();
            else
                thread.start();
        }
    }

    final void addProcessToGcListLocked(ProcessRecord processrecord) {
        boolean flag = false;
        int i = -1 + mProcessesToGc.size();
        do {
label0:
            {
                if(i >= 0) {
                    if(((ProcessRecord)mProcessesToGc.get(i)).lastRequestedGc >= processrecord.lastRequestedGc)
                        break label0;
                    flag = true;
                    mProcessesToGc.add(i + 1, processrecord);
                }
                if(!flag)
                    mProcessesToGc.add(0, processrecord);
                return;
            }
            i--;
        } while(true);
    }

    final void addRecentTaskLocked(TaskRecord taskrecord) {
        int i = mRecentTasks.size();
        if(i <= 0 || mRecentTasks.get(0) != taskrecord) {
            for(int j = 0; j < i; j++) {
                TaskRecord taskrecord1 = (TaskRecord)mRecentTasks.get(j);
                if(taskrecord.userId != taskrecord1.userId || (taskrecord.affinity == null || !taskrecord.affinity.equals(taskrecord1.affinity)) && (taskrecord.intent == null || !taskrecord.intent.filterEquals(taskrecord1.intent)))
                    continue;
                mRecentTasks.remove(j);
                j--;
                i--;
                if(taskrecord.intent == null)
                    taskrecord = taskrecord1;
            }

            if(i >= 20)
                mRecentTasks.remove(i - 1);
            mRecentTasks.add(0, taskrecord);
        }
    }

    final void appDiedLocked(ProcessRecord processrecord, int i, IApplicationThread iapplicationthread) {
        int ai[] = mProcDeaths;
        ai[0] = 1 + ai[0];
        synchronized(mBatteryStatsService.getActiveStatistics()) {
            batterystatsimpl.noteProcessDiedLocked(processrecord.info.uid, i);
        }
        if(processrecord.pid != i || processrecord.thread == null || processrecord.thread.asBinder() != iapplicationthread.asBinder()) goto _L2; else goto _L1
_L1:
        int j;
        if(!processrecord.killedBackground)
            Slog.i("ActivityManager", (new StringBuilder()).append("Process ").append(processrecord.processName).append(" (pid ").append(i).append(") has died.").toString());
        Object aobj1[] = new Object[2];
        aobj1[0] = Integer.valueOf(processrecord.pid);
        aobj1[1] = processrecord.processName;
        EventLog.writeEvent(30011, aobj1);
        boolean flag;
        boolean flag1;
        long l;
        int k;
        ProcessRecord processrecord2;
        if(processrecord.instrumentationClass == null)
            flag = true;
        else
            flag = false;
        handleAppDiedLocked(processrecord, false, true);
        if(!flag)
            break MISSING_BLOCK_LABEL_422;
        flag1 = false;
        j = -1 + mLruProcesses.size();
_L4:
        if(j >= 0) {
            processrecord2 = (ProcessRecord)mLruProcesses.get(j);
            if(processrecord2.thread == null || processrecord2.setAdj < ProcessList.HIDDEN_APP_MIN_ADJ)
                break MISSING_BLOCK_LABEL_389;
            flag1 = true;
        }
        if(flag1)
            break MISSING_BLOCK_LABEL_422;
        EventLog.writeEvent(30017, mLruProcesses.size());
        l = SystemClock.uptimeMillis();
        k = -1 + mLruProcesses.size();
        while(k >= 0)  {
            ProcessRecord processrecord1 = (ProcessRecord)mLruProcesses.get(k);
            if(processrecord1 != processrecord && processrecord1.thread != null && 60000L + processrecord1.lastLowMemory <= l) {
                if(processrecord1.setAdj <= 3)
                    processrecord1.lastRequestedGc = 0L;
                else
                    processrecord1.lastRequestedGc = processrecord1.lastLowMemory;
                processrecord1.reportLowMemory = true;
                processrecord1.lastLowMemory = l;
                mProcessesToGc.remove(processrecord1);
                addProcessToGcListLocked(processrecord1);
            }
            k--;
        }
        break; /* Loop/switch isn't completed */
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
        j--;
        if(true) goto _L4; else goto _L3
_L3:
        mHandler.sendEmptyMessage(33);
        scheduleAppGcsLocked();
_L6:
        return;
_L2:
        if(processrecord.pid != i) {
            Slog.i("ActivityManager", (new StringBuilder()).append("Process ").append(processrecord.processName).append(" (pid ").append(i).append(") has died and restarted (pid ").append(processrecord.pid).append(").").toString());
            Object aobj[] = new Object[2];
            aobj[0] = Integer.valueOf(processrecord.pid);
            aobj[1] = processrecord.processName;
            EventLog.writeEvent(30011, aobj);
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    final void appNotResponding(ProcessRecord processrecord, ActivityRecord activityrecord, ActivityRecord activityrecord1, String s) {
        ArrayList arraylist;
        SparseArray sparsearray;
        long l;
        arraylist = new ArrayList(5);
        sparsearray = new SparseArray(20);
        if(mController != null)
            try {
                if(mController.appEarlyNotResponding(processrecord.processName, processrecord.pid, s) < 0 && processrecord.pid != MY_PID)
                    Process.killProcess(processrecord.pid);
            }
            catch(RemoteException remoteexception1) {
                mController = null;
            }
        l = SystemClock.uptimeMillis();
        updateCpuStatsNow();
        this;
        JVM INSTR monitorenter ;
        if(!mShuttingDown) goto _L2; else goto _L1
_L1:
        Slog.i("ActivityManager", (new StringBuilder()).append("During shutdown skipping ANR: ").append(processrecord).append(" ").append(s).toString());
        this;
        JVM INSTR monitorexit ;
_L3:
        return;
_L2:
        if(!processrecord.notResponding)
            break MISSING_BLOCK_LABEL_191;
        Slog.i("ActivityManager", (new StringBuilder()).append("Skipping duplicate ANR: ").append(processrecord).append(" ").append(s).toString());
          goto _L3
        Exception exception;
        exception;
        throw exception;
        if(!processrecord.crashing) goto _L5; else goto _L4
_L4:
        Slog.i("ActivityManager", (new StringBuilder()).append("Crashing app skipping ANR: ").append(processrecord).append(" ").append(s).toString());
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L5:
        int i;
        int k;
        processrecord.notResponding = true;
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(processrecord.pid);
        aobj[1] = processrecord.processName;
        aobj[2] = Integer.valueOf(processrecord.info.flags);
        aobj[3] = s;
        EventLog.writeEvent(30008, aobj);
        arraylist.add(Integer.valueOf(processrecord.pid));
        i = processrecord.pid;
        if(activityrecord1 != null && activityrecord1.app != null && activityrecord1.app.pid > 0)
            i = activityrecord1.app.pid;
        int j = processrecord.pid;
        if(i != j)
            arraylist.add(Integer.valueOf(i));
        if(MY_PID != processrecord.pid && MY_PID != i)
            arraylist.add(Integer.valueOf(MY_PID));
        k = -1 + mLruProcesses.size();
_L9:
        if(k < 0) goto _L7; else goto _L6
_L6:
        ProcessRecord processrecord1 = (ProcessRecord)mLruProcesses.get(k);
        if(processrecord1 != null && processrecord1.thread != null) {
            int j1 = processrecord1.pid;
            if(j1 > 0 && j1 != processrecord.pid && j1 != i && j1 != MY_PID)
                if(processrecord1.persistent)
                    arraylist.add(Integer.valueOf(j1));
                else
                    sparsearray.put(j1, Boolean.TRUE);
        }
          goto _L8
_L7:
        this;
        JVM INSTR monitorexit ;
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder();
        stringbuilder.setLength(0);
        stringbuilder.append("ANR in ").append(processrecord.processName);
        if(activityrecord != null && activityrecord.shortComponentName != null)
            stringbuilder.append(" (").append(activityrecord.shortComponentName).append(")");
        stringbuilder.append("\n");
        if(s != null)
            stringbuilder.append("Reason: ").append(s).append("\n");
        if(activityrecord1 != null && activityrecord1 != activityrecord)
            stringbuilder.append("Parent: ").append(activityrecord1.shortComponentName).append("\n");
        ProcessStats processstats = new ProcessStats(true);
        File file = dumpStackTraces(true, arraylist, processstats, sparsearray, null);
        updateCpuStatsNow();
        String s1;
        synchronized(mProcessStatsThread) {
            s1 = mProcessStats.printCurrentState(l);
        }
        stringbuilder.append(processstats.printCurrentLoad());
        stringbuilder.append(s1);
        stringbuilder.append(processstats.printCurrentState(l));
        Slog.e("ActivityManager", stringbuilder.toString());
        if(file == null)
            Process.sendSignal(processrecord.pid, 3);
        addErrorToDropBox("anr", processrecord, processrecord.processName, activityrecord, activityrecord1, s, s1, file, null);
        if(mController == null)
            break MISSING_BLOCK_LABEL_843;
        int i1 = mController.appNotResponding(processrecord.processName, processrecord.pid, stringbuilder.toString());
        if(i1 == 0)
            break MISSING_BLOCK_LABEL_843;
        if(i1 < 0 && processrecord.pid != MY_PID)
            Process.killProcess(processrecord.pid);
          goto _L3
        RemoteException remoteexception;
        remoteexception;
        mController = null;
        boolean flag;
        Exception exception2;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "anr_show_background", 0) != 0)
            flag = true;
        else
            flag = false;
        this;
        JVM INSTR monitorenter ;
        if(flag || processrecord.isInterestingToUserLocked() || processrecord.pid == MY_PID)
            break MISSING_BLOCK_LABEL_1004;
        Slog.w("ActivityManager", (new StringBuilder()).append("Killing ").append(processrecord).append(": background ANR").toString());
        Object aobj1[] = new Object[4];
        aobj1[0] = Integer.valueOf(processrecord.pid);
        aobj1[1] = processrecord.processName;
        aobj1[2] = Integer.valueOf(processrecord.setAdj);
        aobj1[3] = "background ANR";
        EventLog.writeEvent(30023, aobj1);
        Process.killProcessQuiet(processrecord.pid);
          goto _L3
        exception2;
        throw exception2;
        exception1;
        thread;
        JVM INSTR monitorexit ;
        throw exception1;
        if(activityrecord == null)
            break MISSING_BLOCK_LABEL_1136;
        String s2 = activityrecord.shortComponentName;
_L10:
        String s3;
        Message message;
        HashMap hashmap;
        if(s != null)
            s3 = (new StringBuilder()).append("ANR ").append(s).toString();
        else
            s3 = "ANR";
        makeAppNotRespondingLocked(processrecord, s2, s3, stringbuilder.toString());
        message = Message.obtain();
        hashmap = new HashMap();
        message.what = 2;
        message.obj = hashmap;
        hashmap.put("app", processrecord);
        if(activityrecord != null)
            hashmap.put("activity", activityrecord);
        mHandler.sendMessage(message);
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L8:
        k--;
          goto _L9
        s2 = null;
          goto _L10
    }

    public final void attachApplication(IApplicationThread iapplicationthread) {
        this;
        JVM INSTR monitorenter ;
        int i = Binder.getCallingPid();
        long l = Binder.clearCallingIdentity();
        attachApplicationLocked(iapplicationthread, i);
        Binder.restoreCallingIdentity(l);
        return;
    }

    public void backupAgentCreated(String s, IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        if(s.equals(mBackupAppName))
            break MISSING_BLOCK_LABEL_50;
        Slog.e("ActivityManager", (new StringBuilder()).append("Backup agent created for ").append(s).append(" but not requested!").toString());
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_120;
        this;
        JVM INSTR monitorexit ;
        long l = Binder.clearCallingIdentity();
        android.app.backup.IBackupManager.Stub.asInterface(ServiceManager.getService("backup")).agentConnected(s, ibinder);
_L2:
        Binder.restoreCallingIdentity(l);
        break MISSING_BLOCK_LABEL_120;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception2;
        exception2;
        Slog.w("ActivityManager", "Exception trying to deliver BackupAgent binding: ");
        exception2.printStackTrace();
        continue; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void batteryNeedsCpuUpdate() {
        updateCpuStatsNow();
    }

    public void batteryPowerChanged(boolean flag) {
        updateCpuStatsNow();
        this;
        JVM INSTR monitorenter ;
        synchronized(mPidsSelfLocked) {
            mOnBattery = flag;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean bindBackupAgent(ApplicationInfo applicationinfo, int i) {
        enforceCallingPermission("android.permission.BACKUP", "startBackupAgent");
        this;
        JVM INSTR monitorenter ;
        com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv serv;
        synchronized(mBatteryStatsService.getActiveStatistics()) {
            serv = batterystatsimpl.getServiceStatsLocked(applicationinfo.uid, applicationinfo.packageName, applicationinfo.name);
        }
        AppGlobals.getPackageManager().setPackageStoppedState(applicationinfo.packageName, false, UserId.getUserId(applicationinfo.uid));
_L8:
        BackupRecord backuprecord = new BackupRecord(serv, applicationinfo, i);
        if(i != 0) goto _L2; else goto _L1
_L1:
        ComponentName componentname = new ComponentName(applicationinfo.packageName, applicationinfo.backupAgentName);
_L5:
        ProcessRecord processrecord = startProcessLocked(applicationinfo.processName, applicationinfo, false, 0, "backup", componentname, false, false);
        if(processrecord != null) goto _L4; else goto _L3
_L3:
        boolean flag;
        Slog.e("ActivityManager", (new StringBuilder()).append("Unable to start backup agent process ").append(backuprecord).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
_L6:
        return flag;
        exception1;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Failed trying to unstop package ").append(applicationinfo.packageName).append(": ").append(illegalargumentexception).toString());
        continue; /* Loop/switch isn't completed */
_L2:
        componentname = new ComponentName("android", "FullBackupAgent");
          goto _L5
_L4:
        IApplicationThread iapplicationthread;
        backuprecord.app = processrecord;
        mBackupTarget = backuprecord;
        mBackupAppName = applicationinfo.packageName;
        updateOomAdjLocked(processrecord);
        iapplicationthread = processrecord.thread;
        if(iapplicationthread == null)
            break MISSING_BLOCK_LABEL_293;
        try {
            processrecord.thread.scheduleCreateBackupAgent(applicationinfo, compatibilityInfoForPackageLocked(applicationinfo), i);
        }
        catch(RemoteException remoteexception1) { }
        this;
        JVM INSTR monitorexit ;
        flag = true;
          goto _L6
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public int bindService(IApplicationThread iapplicationthread, IBinder ibinder, Intent intent, String s, IServiceConnection iserviceconnection, int i, int j) {
        enforceNotIsolatedCaller("bindService");
        if(intent != null && intent.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Intent");
        checkValidCaller(Binder.getCallingUid(), j);
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        ProcessRecord processrecord;
        processrecord = getRecordForAppLocked(iapplicationthread);
        if(processrecord == null)
            throw new SecurityException((new StringBuilder()).append("Unable to find app for caller ").append(iapplicationthread).append(" (pid=").append(Binder.getCallingPid()).append(") when binding service ").append(intent).toString());
        if(true)
            break MISSING_BLOCK_LABEL_110;
        JVM INSTR monitorexit ;
        throw exception;
        ActivityRecord activityrecord = null;
        if(ibinder == null) goto _L2; else goto _L1
_L1:
        activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord != null) goto _L2; else goto _L3
_L3:
        byte byte0;
        Slog.w("ActivityManager", (new StringBuilder()).append("Binding with unknown activity: ").append(ibinder).toString());
        byte0 = 0;
        this;
        JVM INSTR monitorexit ;
          goto _L4
_L2:
        int k;
        PendingIntent pendingintent;
        int l;
        k = 0;
        pendingintent = null;
        l = processrecord.info.uid;
        if(l != 1000)
            break MISSING_BLOCK_LABEL_227;
        int i1;
        int j1;
        ServiceLookupResult servicelookupresult;
        ServiceRecord servicerecord;
        long l1;
        AppBindRecord appbindrecord;
        ConnectionRecord connectionrecord;
        IBinder ibinder1;
        ArrayList arraylist;
        ArrayList arraylist1;
        boolean flag;
        Exception exception1;
        int k1;
        int i2;
        try {
            pendingintent = (PendingIntent)intent.getParcelableExtra("android.intent.extra.client_intent");
        }
        catch(RuntimeException runtimeexception) { }
        finally {
            this;
        }
        if(pendingintent == null)
            break MISSING_BLOCK_LABEL_227;
        k = intent.getIntExtra("android.intent.extra.client_label", 0);
        if(k != 0)
            intent = intent.cloneFilter();
        i1 = Binder.getCallingPid();
        j1 = Binder.getCallingUid();
        servicelookupresult = retrieveServiceLocked(intent, s, i1, j1, j);
        if(servicelookupresult != null) goto _L6; else goto _L5
_L5:
        byte0 = 0;
        this;
        JVM INSTR monitorexit ;
          goto _L4
_L6:
        if(servicelookupresult.record != null) goto _L8; else goto _L7
_L7:
        byte0 = -1;
        this;
        JVM INSTR monitorexit ;
          goto _L4
_L8:
        if(isSingleton(servicelookupresult.record.processName, servicelookupresult.record.appInfo)) {
            k1 = Binder.getCallingPid();
            i2 = Binder.getCallingUid();
            servicelookupresult = retrieveServiceLocked(intent, s, k1, i2, 0);
        }
        servicerecord = servicelookupresult.record;
        l1 = Binder.clearCallingIdentity();
        if(!unscheduleServiceRestartLocked(servicerecord));
        appbindrecord = servicerecord.retrieveAppBindingLocked(intent, processrecord);
        connectionrecord = new ConnectionRecord(appbindrecord, activityrecord, iserviceconnection, i, k, pendingintent);
        ibinder1 = iserviceconnection.asBinder();
        arraylist = (ArrayList)servicerecord.connections.get(ibinder1);
        if(arraylist == null) {
            arraylist = new ArrayList();
            servicerecord.connections.put(ibinder1, arraylist);
        }
        arraylist.add(connectionrecord);
        appbindrecord.connections.add(connectionrecord);
        if(activityrecord != null) {
            if(activityrecord.connections == null)
                activityrecord.connections = new HashSet();
            activityrecord.connections.add(connectionrecord);
        }
        appbindrecord.client.connections.add(connectionrecord);
        if((8 & connectionrecord.flags) != 0)
            appbindrecord.client.hasAboveClient = true;
        arraylist1 = (ArrayList)mServiceConnections.get(ibinder1);
        if(arraylist1 == null) {
            arraylist1 = new ArrayList();
            mServiceConnections.put(ibinder1, arraylist1);
        }
        arraylist1.add(connectionrecord);
        if((i & 1) == 0) goto _L10; else goto _L9
_L9:
        servicerecord.lastActivity = SystemClock.uptimeMillis();
        if(bringUpServiceLocked(servicerecord, intent.getFlags(), false)) goto _L10; else goto _L11
_L11:
        byte0 = 0;
        this;
        JVM INSTR monitorexit ;
          goto _L4
_L10:
        if(servicerecord.app != null)
            updateOomAdjLocked(servicerecord.app);
        if(servicerecord.app == null) goto _L13; else goto _L12
_L12:
        flag = appbindrecord.intent.received;
        if(!flag) goto _L13; else goto _L14
_L14:
        try {
            connectionrecord.conn.connected(servicerecord.name, appbindrecord.intent.binder);
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Failure sending service ").append(servicerecord.shortName).append(" to connection ").append(connectionrecord.conn.asBinder()).append(" (in ").append(connectionrecord.binding.client.processName).append(")").toString(), exception1);
        }
        if(appbindrecord.intent.apps.size() == 1 && appbindrecord.intent.doRebind)
            requestServiceBindingLocked(servicerecord, appbindrecord.intent, true);
_L15:
        Binder.restoreCallingIdentity(l1);
        this;
        JVM INSTR monitorexit ;
        byte0 = 1;
        break; /* Loop/switch isn't completed */
_L13:
        if(!appbindrecord.intent.requested)
            requestServiceBindingLocked(servicerecord, appbindrecord.intent, false);
        if(true) goto _L15; else goto _L4
_L4:
        return byte0;
    }

    public final int broadcastIntent(IApplicationThread iapplicationthread, Intent intent, String s, IIntentReceiver iintentreceiver, int i, String s1, Bundle bundle, 
            String s2, boolean flag, boolean flag1, int j) {
        enforceNotIsolatedCaller("broadcastIntent");
        this;
        JVM INSTR monitorenter ;
        Intent intent1 = verifyBroadcastLocked(intent);
        ProcessRecord processrecord = getRecordForAppLocked(iapplicationthread);
        int k = Binder.getCallingPid();
        int l = Binder.getCallingUid();
        long l1 = Binder.clearCallingIdentity();
        String s3;
        int i1;
        if(processrecord != null)
            s3 = processrecord.info.packageName;
        else
            s3 = null;
        i1 = broadcastIntentLocked(processrecord, s3, intent1, s, iintentreceiver, i, s1, bundle, s2, flag, flag1, k, l, j);
        Binder.restoreCallingIdentity(l1);
        return i1;
    }

    int broadcastIntentInPackage(String s, int i, Intent intent, String s1, IIntentReceiver iintentreceiver, int j, String s2, 
            Bundle bundle, String s3, boolean flag, boolean flag1, int k) {
        this;
        JVM INSTR monitorenter ;
        Intent intent1 = verifyBroadcastLocked(intent);
        long l = Binder.clearCallingIdentity();
        int i1 = broadcastIntentLocked(null, s, intent1, s1, iintentreceiver, j, s2, bundle, s3, flag, flag1, -1, i, k);
        Binder.restoreCallingIdentity(l);
        return i1;
    }

    BroadcastQueue broadcastQueueForIntent(Intent intent) {
        boolean flag;
        BroadcastQueue broadcastqueue;
        if((0x10000000 & intent.getFlags()) != 0)
            flag = true;
        else
            flag = false;
        if(flag)
            broadcastqueue = mFgBroadcastQueue;
        else
            broadcastqueue = mBgBroadcastQueue;
        return broadcastqueue;
    }

    BroadcastRecord broadcastRecordForReceiverLocked(IBinder ibinder) {
        BroadcastQueue abroadcastqueue[];
        int i;
        int j;
        abroadcastqueue = mBroadcastQueues;
        i = abroadcastqueue.length;
        j = 0;
_L3:
        BroadcastRecord broadcastrecord;
        if(j >= i)
            break MISSING_BLOCK_LABEL_41;
        broadcastrecord = abroadcastqueue[j].getMatchingOrderedReceiver(ibinder);
        if(broadcastrecord == null) goto _L2; else goto _L1
_L1:
        return broadcastrecord;
_L2:
        j++;
          goto _L3
        broadcastrecord = null;
          goto _L1
    }

    public void cancelIntentSender(IIntentSender iintentsender) {
        if(iintentsender instanceof PendingIntentRecord) goto _L2; else goto _L1
_L1:
        return;
_L2:
        this;
        JVM INSTR monitorenter ;
        PendingIntentRecord pendingintentrecord = (PendingIntentRecord)iintentsender;
        try {
            if(!UserId.isSameApp(AppGlobals.getPackageManager().getPackageUid(pendingintentrecord.key.packageName, UserId.getCallingUserId()), Binder.getCallingUid())) {
                String s = (new StringBuilder()).append("Permission Denial: cancelIntentSender() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" is not allowed to cancel packges ").append(pendingintentrecord.key.packageName).toString();
                Slog.w("ActivityManager", s);
                throw new SecurityException(s);
            }
        }
        catch(RemoteException remoteexception) {
            throw new SecurityException(remoteexception);
        }
        break MISSING_BLOCK_LABEL_129;
        Exception exception;
        exception;
        throw exception;
        cancelIntentSenderLocked(pendingintentrecord, true);
        this;
        JVM INSTR monitorexit ;
          goto _L1
    }

    void cancelIntentSenderLocked(PendingIntentRecord pendingintentrecord, boolean flag) {
        pendingintentrecord.canceled = true;
        mIntentSenderRecords.remove(pendingintentrecord.key);
        if(flag && pendingintentrecord.key.activity != null)
            pendingintentrecord.key.activity.pendingResults.remove(pendingintentrecord.ref);
    }

    boolean checkAppInLaunchingProvidersLocked(ProcessRecord processrecord, boolean flag) {
        int i = mLaunchingProviders.size();
        boolean flag1 = false;
        int j = 0;
        while(j < i)  {
            ContentProviderRecord contentproviderrecord = (ContentProviderRecord)mLaunchingProviders.get(j);
            if(contentproviderrecord.launchingApp == processrecord)
                if(!flag && !processrecord.bad) {
                    flag1 = true;
                } else {
                    removeDyingProviderLocked(processrecord, contentproviderrecord, true);
                    i = mLaunchingProviders.size();
                }
            j++;
        }
        return flag1;
    }

    boolean checkAppSwitchAllowedLocked(int i, int j, String s) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_3;
        if(mAppSwitchesAllowedTime >= SystemClock.uptimeMillis() && checkComponentPermission("android.permission.STOP_APP_SWITCHES", i, j, -1, flag) != 0) {
            Slog.w("ActivityManager", (new StringBuilder()).append(s).append(" request from ").append(j).append(" stopped").toString());
            flag = false;
        }
        return flag;
    }

    int checkCallingPermission(String s) {
        return checkPermission(s, Binder.getCallingPid(), UserId.getAppId(Binder.getCallingUid()));
    }

    int checkComponentPermission(String s, int i, int j, int k, boolean flag) {
        Identity identity = (Identity)sCallerIdentity.get();
        if(identity != null) {
            Slog.d("ActivityManager", (new StringBuilder()).append("checkComponentPermission() adjusting {pid,uid} to {").append(identity.pid).append(",").append(identity.uid).append("}").toString());
            j = identity.uid;
            i = identity.pid;
        }
        int l;
        if(i == MY_PID)
            l = 0;
        else
            l = ActivityManager.checkComponentPermission(s, j, k, flag);
        return l;
    }

    final void checkExcessivePowerUsageLocked(boolean flag) {
        BatteryStatsImpl batterystatsimpl;
        boolean flag1;
        boolean flag2;
        long l;
        long l1;
        long l3;
        int i;
        updateCpuStatsNow();
        batterystatsimpl = mBatteryStatsService.getActiveStatistics();
        flag1 = flag;
        flag2 = flag;
        if(mLastPowerCheckRealtime == 0L)
            flag1 = false;
        if(mLastPowerCheckUptime == 0L)
            flag2 = false;
        if(batterystatsimpl.isScreenOn())
            flag1 = false;
        l = SystemClock.elapsedRealtime();
        l1 = l - mLastPowerCheckRealtime;
        long l2 = SystemClock.uptimeMillis();
        l3 = l2 - mLastPowerCheckUptime;
        mLastPowerCheckRealtime = l;
        mLastPowerCheckUptime = l2;
        if(l1 < 0x493e0L)
            flag1 = false;
        if(l3 < 0x493e0L)
            flag2 = false;
        i = mLruProcesses.size();
_L2:
        ProcessRecord processrecord;
        if(i <= 0)
            break; /* Loop/switch isn't completed */
        i--;
        processrecord = (ProcessRecord)mLruProcesses.get(i);
        if(processrecord.keeping)
            continue; /* Loop/switch isn't completed */
        batterystatsimpl;
        JVM INSTR monitorenter ;
        long l4 = batterystatsimpl.getProcessWakeTime(processrecord.info.uid, processrecord.pid, l);
        batterystatsimpl;
        JVM INSTR monitorexit ;
        long l5;
        long l6;
        l5 = l4 - processrecord.lastWakeTime;
        l6 = processrecord.curCpuTime - processrecord.lastCpuTime;
        if(!flag1 || l1 <= 0L || (100L * l5) / l1 < 50L)
            break MISSING_BLOCK_LABEL_397;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        batterystatsimpl.reportExcessiveWakeLocked(processrecord.info.uid, processrecord.processName, l1, l5);
        batterystatsimpl;
        JVM INSTR monitorexit ;
        Slog.w("ActivityManager", (new StringBuilder()).append("Excessive wake lock in ").append(processrecord.processName).append(" (pid ").append(processrecord.pid).append("): held ").append(l5).append(" during ").append(l1).toString());
        Object aobj1[] = new Object[4];
        aobj1[0] = Integer.valueOf(processrecord.pid);
        aobj1[1] = processrecord.processName;
        aobj1[2] = Integer.valueOf(processrecord.setAdj);
        aobj1[3] = "excessive wake lock";
        EventLog.writeEvent(30023, aobj1);
        Process.killProcessQuiet(processrecord.pid);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception2;
        exception2;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception2;
        if(!flag2 || l3 <= 0L || (100L * l6) / l3 < 50L)
            break MISSING_BLOCK_LABEL_589;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        batterystatsimpl.reportExcessiveCpuLocked(processrecord.info.uid, processrecord.processName, l3, l6);
        batterystatsimpl;
        JVM INSTR monitorexit ;
        Slog.w("ActivityManager", (new StringBuilder()).append("Excessive CPU in ").append(processrecord.processName).append(" (pid ").append(processrecord.pid).append("): used ").append(l6).append(" during ").append(l3).toString());
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(processrecord.pid);
        aobj[1] = processrecord.processName;
        aobj[2] = Integer.valueOf(processrecord.setAdj);
        aobj[3] = "excessive cpu";
        EventLog.writeEvent(30023, aobj);
        Process.killProcessQuiet(processrecord.pid);
        continue; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception1;
        processrecord.lastWakeTime = l4;
        processrecord.lastCpuTime = processrecord.curCpuTime;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public int checkGrantUriPermission(int i, String s, Uri uri, int j) {
        enforceNotIsolatedCaller("checkGrantUriPermission");
        this;
        JVM INSTR monitorenter ;
        int k = checkGrantUriPermissionLocked(i, s, uri, j, -1);
        return k;
    }

    NeededUriGrants checkGrantUriPermissionFromIntentLocked(int i, String s, Intent intent, int j, NeededUriGrants neededurigrants) {
        if(s == null)
            throw new NullPointerException("targetPkg");
        if(intent != null) goto _L2; else goto _L1
_L1:
        NeededUriGrants neededurigrants1 = null;
_L4:
        return neededurigrants1;
_L2:
        Uri uri = intent.getData();
        ClipData clipdata = intent.getClipData();
        if(uri == null && clipdata == null) {
            neededurigrants1 = null;
            continue; /* Loop/switch isn't completed */
        }
        if(uri != null) {
            int l;
            Uri uri1;
            int j1;
            int k1;
            int l1;
            if(neededurigrants != null)
                k1 = neededurigrants.targetUid;
            else
                k1 = -1;
            l1 = checkGrantUriPermissionLocked(i, s, uri, j, k1);
            if(l1 > 0) {
                if(neededurigrants == null)
                    neededurigrants = new NeededUriGrants(s, l1, j);
                neededurigrants.add(uri);
            }
        }
        if(clipdata != null) {
            int k = 0;
            do {
                l = clipdata.getItemCount();
                if(k >= l)
                    break;
                uri1 = clipdata.getItemAt(k).getUri();
                if(uri1 != null) {
                    int i1;
                    if(neededurigrants != null)
                        i1 = neededurigrants.targetUid;
                    else
                        i1 = -1;
                    j1 = checkGrantUriPermissionLocked(i, s, uri1, j, i1);
                    if(j1 > 0) {
                        if(neededurigrants == null)
                            neededurigrants = new NeededUriGrants(s, j1, j);
                        neededurigrants.add(uri1);
                    }
                } else {
                    Intent intent1 = clipdata.getItemAt(k).getIntent();
                    if(intent1 != null) {
                        NeededUriGrants neededurigrants2 = checkGrantUriPermissionFromIntentLocked(i, s, intent1, j, neededurigrants);
                        if(neededurigrants2 != null)
                            neededurigrants = neededurigrants2;
                    }
                }
                k++;
            } while(true);
        }
        neededurigrants1 = neededurigrants;
        if(true) goto _L4; else goto _L3
_L3:
    }

    int checkGrantUriPermissionLocked(int i, String s, Uri uri, int j, int k) {
        int l;
        IPackageManager ipackagemanager;
        int i1;
        l = j & 3;
        if(l == 0) {
            i1 = -1;
        } else {
label0:
            {
                if(s == null);
                ipackagemanager = AppGlobals.getPackageManager();
                if("content".equals(uri.getScheme()))
                    break label0;
                i1 = -1;
            }
        }
_L5:
        return i1;
        String s1;
        ProviderInfo providerinfo;
        ContentProviderRecord contentproviderrecord;
        s1 = uri.getAuthority();
        providerinfo = null;
        contentproviderrecord = mProviderMap.getProviderByName(s1, UserId.getUserId(i));
        if(contentproviderrecord == null) goto _L2; else goto _L1
_L1:
        providerinfo = contentproviderrecord.info;
_L6:
        if(providerinfo != null) goto _L4; else goto _L3
_L3:
        Slog.w("ActivityManager", (new StringBuilder()).append("No content provider found for permission check: ").append(uri.toSafeString()).toString());
        i1 = -1;
          goto _L5
_L2:
        ProviderInfo providerinfo1 = ipackagemanager.resolveContentProvider(s1, 2048, UserId.getUserId(i));
        providerinfo = providerinfo1;
          goto _L6
_L4:
        i1 = k;
        if(i1 >= 0 || s == null)
            break MISSING_BLOCK_LABEL_201;
        int l1 = ipackagemanager.getPackageUid(s, UserId.getUserId(i));
        i1 = l1;
        if(i1 >= 0)
            break MISSING_BLOCK_LABEL_201;
        i1 = -1;
          goto _L5
        RemoteException remoteexception1;
        remoteexception1;
        i1 = -1;
          goto _L5
        if(i1 >= 0) {
            if(!checkHoldingPermissionsLocked(ipackagemanager, providerinfo, uri, i1, l))
                break MISSING_BLOCK_LABEL_284;
            i1 = -1;
        } else {
            boolean flag = providerinfo.exported;
            if((l & 1) != 0 && providerinfo.readPermission != null)
                flag = false;
            if((l & 2) != 0 && providerinfo.writePermission != null)
                flag = false;
            if(!flag)
                break MISSING_BLOCK_LABEL_284;
            i1 = -1;
        }
          goto _L5
        if(!providerinfo.grantUriPermissions)
            throw new SecurityException((new StringBuilder()).append("Provider ").append(((ComponentInfo) (providerinfo)).packageName).append("/").append(((ComponentInfo) (providerinfo)).name).append(" does not allow granting of Uri permissions (uri ").append(uri).append(")").toString());
        if(providerinfo.uriPermissionPatterns == null)
            continue; /* Loop/switch isn't completed */
        int j1 = providerinfo.uriPermissionPatterns.length;
        boolean flag1 = false;
        int k1 = 0;
label1:
        do {
label2:
            {
                if(k1 < j1) {
                    if(providerinfo.uriPermissionPatterns[k1] == null || !providerinfo.uriPermissionPatterns[k1].match(uri.getPath()))
                        break label2;
                    flag1 = true;
                }
                if(!flag1)
                    throw new SecurityException((new StringBuilder()).append("Provider ").append(((ComponentInfo) (providerinfo)).packageName).append("/").append(((ComponentInfo) (providerinfo)).name).append(" does not allow granting of permission to path of Uri ").append(uri).toString());
                break label1;
            }
            k1++;
        } while(true);
        if(i == Process.myUid() || checkHoldingPermissionsLocked(ipackagemanager, providerinfo, uri, i, l) || checkUriPermissionLocked(uri, i, l)) goto _L5; else goto _L7
_L7:
        throw new SecurityException((new StringBuilder()).append("Uid ").append(i).append(" does not have permission to uri ").append(uri).toString());
        RemoteException remoteexception;
        remoteexception;
          goto _L6
    }

    public int checkPermission(String s, int i, int j) {
        int k = -1;
        if(s != null)
            k = checkComponentPermission(s, i, UserId.getAppId(j), k, true);
        return k;
    }

    public int checkUriPermission(Uri uri, int i, int j, int k) {
        int l;
        int i1;
        l = 0;
        enforceNotIsolatedCaller("checkUriPermission");
        Identity identity = (Identity)sCallerIdentity.get();
        if(identity != null) {
            j = identity.uid;
            i = identity.pid;
        }
        i1 = UserId.getAppId(j);
        if(i != MY_PID) goto _L2; else goto _L1
_L1:
        return l;
_L2:
        this;
        JVM INSTR monitorenter ;
        if(!checkUriPermissionLocked(uri, i1, k))
            l = -1;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean clearApplicationUserData(String s, IPackageDataObserver ipackagedataobserver, int i) {
        int j;
        int k;
        long l;
        enforceNotIsolatedCaller("clearApplicationUserData");
        j = Binder.getCallingUid();
        k = Binder.getCallingPid();
        l = Binder.clearCallingIdentity();
        IPackageManager ipackagemanager = AppGlobals.getPackageManager();
        int i1 = -1;
        this;
        JVM INSTR monitorenter ;
        int j1 = ipackagemanager.getPackageUid(s, i);
        i1 = j1;
_L8:
        if(i1 != -1) goto _L2; else goto _L1
_L1:
        boolean flag;
        Slog.w("ActivityManager", (new StringBuilder()).append("Invalid packageName:").append(s).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
_L6:
        return flag;
_L2:
        if(j == i1) goto _L4; else goto _L3
_L3:
        if(checkComponentPermission("android.permission.CLEAR_APP_USER_DATA", k, j, -1, true) != 0) goto _L5; else goto _L4
_L4:
        forceStopPackageLocked(s, i1);
        this;
        JVM INSTR monitorexit ;
        Exception exception;
        RemoteException remoteexception;
        Exception exception1;
        try {
            ipackagemanager.clearApplicationUserData(s, ipackagedataobserver, i);
            Intent intent = new Intent("android.intent.action.PACKAGE_DATA_CLEARED", Uri.fromParts("package", s, null));
            intent.putExtra("android.intent.extra.UID", i1);
            broadcastIntentInPackage("android", 1000, intent, null, null, 0, null, null, null, false, false, i);
        }
        catch(RemoteException remoteexception1) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        flag = true;
        if(true) goto _L6; else goto _L5
_L5:
        throw new SecurityException((new StringBuilder()).append(k).append(" does not have permission:").append("android.permission.CLEAR_APP_USER_DATA").append(" to clear data").append("for process:").append(s).toString());
        exception1;
        throw exception1;
        remoteexception;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void closeSystemDialogs(String s) {
        int i;
        enforceNotIsolatedCaller("closeSystemDialogs");
        i = Binder.getCallingUid();
        long l = Binder.clearCallingIdentity();
        this;
        JVM INSTR monitorenter ;
        closeSystemDialogsLocked(i, s);
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void closeSystemDialogsLocked(int i, String s) {
        Intent intent = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intent.addFlags(0x40000000);
        if(s != null)
            intent.putExtra("reason", s);
        mWindowManager.closeSystemDialogs(s);
        for(int j = -1 + mMainStack.mHistory.size(); j >= 0; j--) {
            ActivityRecord activityrecord = (ActivityRecord)mMainStack.mHistory.get(j);
            if((0x100 & activityrecord.info.flags) != 0)
                activityrecord.stack.finishActivityLocked(activityrecord, j, 0, null, "close-sys");
        }

        broadcastIntentLocked(null, null, intent, null, null, 0, null, null, null, false, false, -1, i, 0);
    }

    ArrayList collectProcesses(PrintWriter printwriter, int i, String as[]) {
        this;
        JVM INSTR monitorenter ;
        if(as == null) goto _L2; else goto _L1
_L1:
        if(as.length <= i || as[i].charAt(0) == '-') goto _L2; else goto _L3
_L3:
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1;
        int k;
        int j = -1;
        ProcessRecord processrecord;
        int l;
        try {
            l = Integer.parseInt(as[i]);
        }
        catch(NumberFormatException numberformatexception) {
            continue; /* Loop/switch isn't completed */
        }
        finally {
            throw exception;
        }
        j = l;
_L9:
        k = -1 + mLruProcesses.size();
_L7:
        if(k >= 0) {
            processrecord = (ProcessRecord)mLruProcesses.get(k);
            if(processrecord.pid == j)
                arraylist.add(processrecord);
            else
            if(processrecord.processName.equals(as[i]))
                arraylist.add(processrecord);
            k--;
            continue; /* Loop/switch isn't completed */
        }
        if(arraylist.size() > 0) goto _L5; else goto _L4
_L4:
        printwriter.println((new StringBuilder()).append("No process found for: ").append(as[i]).toString());
        arraylist1 = null;
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
_L2:
        arraylist = new ArrayList(mLruProcesses);
_L5:
        this;
        JVM INSTR monitorexit ;
        arraylist1 = arraylist;
        break; /* Loop/switch isn't completed */
        if(true) goto _L7; else goto _L6
_L6:
        return arraylist1;
        if(true) goto _L9; else goto _L8
_L8:
    }

    CompatibilityInfo compatibilityInfoForPackageLocked(ApplicationInfo applicationinfo) {
        return mCompatModePackages.compatibilityInfoForPackageLocked(applicationinfo);
    }

    public void crashApplication(int i, int j, String s, String s1) {
        if(checkCallingPermission("android.permission.FORCE_STOP_PACKAGES") != 0) {
            String s2 = (new StringBuilder()).append("Permission Denial: crashApplication() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.FORCE_STOP_PACKAGES").toString();
            Slog.w("ActivityManager", s2);
            throw new SecurityException(s2);
        }
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord = null;
        SparseArray sparsearray = mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        int k = 0;
_L13:
        if(k >= mPidsSelfLocked.size()) goto _L2; else goto _L1
_L1:
        ProcessRecord processrecord1 = (ProcessRecord)mPidsSelfLocked.valueAt(k);
        if(processrecord1.uid == i) goto _L4; else goto _L3
_L4:
        if(processrecord1.pid != j) goto _L6; else goto _L5
_L5:
        processrecord = processrecord1;
_L2:
        sparsearray;
        JVM INSTR monitorexit ;
        if(processrecord != null) goto _L8; else goto _L7
_L7:
        Slog.w("ActivityManager", (new StringBuilder()).append("crashApplication: nothing for uid=").append(i).append(" initialPid=").append(j).append(" packageName=").append(s).toString());
        this;
        JVM INSTR monitorexit ;
_L11:
        return;
_L6:
        Iterator iterator = processrecord1.pkgList.iterator();
        do {
            if(!iterator.hasNext())
                break;
            if(((String)iterator.next()).equals(s))
                processrecord = processrecord1;
        } while(true);
          goto _L3
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
_L8:
        if(processrecord.thread == null)
            break MISSING_BLOCK_LABEL_315;
        if(processrecord.pid != Process.myPid()) goto _L10; else goto _L9
_L9:
        Log.w("ActivityManager", "crashApplication: trying to crash self!");
        this;
        JVM INSTR monitorexit ;
          goto _L11
_L10:
        long l = Binder.clearCallingIdentity();
        try {
            processrecord.thread.scheduleCrash(s1);
        }
        catch(RemoteException remoteexception) { }
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
          goto _L11
_L3:
        k++;
        if(true) goto _L13; else goto _L12
_L12:
    }

    Intent createAppErrorIntentLocked(ProcessRecord processrecord, long l, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        ApplicationErrorReport applicationerrorreport = createAppErrorReportLocked(processrecord, l, crashinfo);
        Intent intent;
        if(applicationerrorreport == null) {
            intent = null;
        } else {
            intent = new Intent("android.intent.action.APP_ERROR");
            intent.setComponent(processrecord.errorReportReceiver);
            intent.putExtra("android.intent.extra.BUG_REPORT", applicationerrorreport);
            intent.addFlags(0x10000000);
        }
        return intent;
    }

    boolean decProviderCountLocked(ContentProviderConnection contentproviderconnection, ContentProviderRecord contentproviderrecord, IBinder ibinder, boolean flag) {
        boolean flag1 = false;
        if(contentproviderconnection != null) {
            ContentProviderRecord contentproviderrecord1 = contentproviderconnection.provider;
            if(flag)
                contentproviderconnection.stableCount = -1 + contentproviderconnection.stableCount;
            else
                contentproviderconnection.unstableCount = -1 + contentproviderconnection.unstableCount;
            if(contentproviderconnection.stableCount == 0 && contentproviderconnection.unstableCount == 0) {
                contentproviderrecord1.connections.remove(contentproviderconnection);
                contentproviderconnection.client.conProviders.remove(contentproviderconnection);
                flag1 = true;
            }
        } else {
            contentproviderrecord.removeExternalProcessHandleLocked(ibinder);
        }
        return flag1;
    }

    public void dismissKeyguardOnNextActivity() {
        enforceNotIsolatedCaller("dismissKeyguardOnNextActivity");
        long l = Binder.clearCallingIdentity();
        this;
        JVM INSTR monitorenter ;
        if(mLockScreenShown) {
            mLockScreenShown = false;
            comeOutOfSleepIfNeededLocked();
        }
        mMainStack.dismissKeyguardOnNextActivityLocked();
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    final void doPendingActivityLaunchesLocked(boolean flag) {
        int i = mPendingActivityLaunches.size();
        if(i > 0) {
            int j = 0;
            while(j < i)  {
                PendingActivityLaunch pendingactivitylaunch = (PendingActivityLaunch)mPendingActivityLaunches.get(j);
                ActivityStack activitystack = mMainStack;
                ActivityRecord activityrecord = pendingactivitylaunch.r;
                ActivityRecord activityrecord1 = pendingactivitylaunch.sourceRecord;
                int k = pendingactivitylaunch.startFlags;
                boolean flag1;
                if(flag && j == i - 1)
                    flag1 = true;
                else
                    flag1 = false;
                activitystack.startActivityUncheckedLocked(activityrecord, activityrecord1, k, flag1, null);
                j++;
            }
            mPendingActivityLaunches.clear();
        }
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(checkCallingPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ActivityManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L14:
        return;
_L2:
        boolean flag;
        boolean flag1;
        String s;
        int i;
        flag = false;
        flag1 = false;
        s = null;
        i = 0;
_L8:
        if(i >= as.length) goto _L4; else goto _L3
_L3:
        String s7 = as[i];
        if(s7 != null && s7.length() > 0 && s7.charAt(0) == '-') goto _L5; else goto _L4
_L4:
        long l;
        boolean flag2;
        String s1;
        l = Binder.clearCallingIdentity();
        flag2 = false;
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_1332;
        s1 = as[i];
        i++;
        if(!"activities".equals(s1) && !"a".equals(s1)) goto _L7; else goto _L6
_L6:
        this;
        JVM INSTR monitorenter ;
        String as2[] = as;
        dumpActivitiesLocked(filedescriptor, printwriter, as2, i, true, flag1, null);
        this;
        JVM INSTR monitorexit ;
_L9:
        if(!flag2) {
            Binder.restoreCallingIdentity(l);
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_1332;
_L5:
        i++;
        if("-a".equals(s7))
            flag = true;
        else
        if("-c".equals(s7)) {
            flag1 = true;
        } else {
            if("-h".equals(s7)) {
                printwriter.println("Activity manager dump options:");
                printwriter.println("  [-a] [-c] [-h] [cmd] ...");
                printwriter.println("  cmd may be one of:");
                printwriter.println("    a[ctivities]: activity stack state");
                printwriter.println("    b[roadcasts] [PACKAGE_NAME]: broadcast state");
                printwriter.println("    i[ntents] [PACKAGE_NAME]: pending intent state");
                printwriter.println("    p[rocesses] [PACKAGE_NAME]: process state");
                printwriter.println("    o[om]: out of memory management");
                printwriter.println("    prov[iders] [COMP_SPEC ...]: content provider state");
                printwriter.println("    provider [COMP_SPEC]: provider client-side state");
                printwriter.println("    s[ervices] [COMP_SPEC ...]: service state");
                printwriter.println("    service [COMP_SPEC]: service client-side state");
                printwriter.println("    package [PACKAGE_NAME]: all state related to given package");
                printwriter.println("    all: dump all activities");
                printwriter.println("    top: dump the top activity");
                printwriter.println("  cmd may also be a COMP_SPEC to dump activities.");
                printwriter.println("  COMP_SPEC may be a component name (com.foo/.myApp),");
                printwriter.println("    a partial substring in a component name, a");
                printwriter.println("    hex object identifier.");
                printwriter.println("  -a: include all available server state.");
                printwriter.println("  -c: include client state.");
                continue; /* Loop/switch isn't completed */
            }
            printwriter.println((new StringBuilder()).append("Unknown argument: ").append(s7).append("; use -h for help").toString());
        }
          goto _L8
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
_L7:
        if(!"broadcasts".equals(s1) && !"b".equals(s1))
            break MISSING_BLOCK_LABEL_547;
        String s2;
        String as4[];
        int i1;
        if(i >= as.length) {
            s2 = null;
            EMPTY_STRING_ARRAY;
        } else {
            s2 = as[i];
            i++;
            String as3[] = new String[as.length - i];
            if(as.length > 2) {
                int k = as.length - i;
                System.arraycopy(as, i, as3, 0, k);
            }
        }
        this;
        JVM INSTR monitorenter ;
        as4 = as;
        i1 = i;
        dumpBroadcastsLocked(filedescriptor, printwriter, as4, i1, true, s2);
          goto _L9
        if(!"intents".equals(s1) && !"i".equals(s1))
            break MISSING_BLOCK_LABEL_664;
        String s3;
        String as6[];
        int k1;
        if(i >= as.length) {
            s3 = null;
            EMPTY_STRING_ARRAY;
        } else {
            s3 = as[i];
            i++;
            String as5[] = new String[as.length - i];
            if(as.length > 2) {
                int j1 = as.length - i;
                System.arraycopy(as, i, as5, 0, j1);
            }
        }
        this;
        JVM INSTR monitorenter ;
        as6 = as;
        k1 = i;
        dumpPendingIntentsLocked(filedescriptor, printwriter, as6, k1, true, s3);
          goto _L9
        if(!"processes".equals(s1) && !"p".equals(s1))
            break MISSING_BLOCK_LABEL_781;
        String s4;
        String as8[];
        int i2;
        if(i >= as.length) {
            s4 = null;
            EMPTY_STRING_ARRAY;
        } else {
            s4 = as[i];
            i++;
            String as7[] = new String[as.length - i];
            if(as.length > 2) {
                int l1 = as.length - i;
                System.arraycopy(as, i, as7, 0, l1);
            }
        }
        this;
        JVM INSTR monitorenter ;
        as8 = as;
        i2 = i;
        dumpProcessesLocked(filedescriptor, printwriter, as8, i2, true, s4);
          goto _L9
        if(!"oom".equals(s1) && !"o".equals(s1))
            break MISSING_BLOCK_LABEL_832;
        this;
        JVM INSTR monitorenter ;
        String as9[] = as;
        dumpOomLocked(filedescriptor, printwriter, as9, i, true);
          goto _L9
label0:
        {
            if(!"provider".equals(s1))
                break label0;
            String s6;
            String as14[];
            if(i >= as.length) {
                s6 = null;
                as14 = EMPTY_STRING_ARRAY;
            } else {
                s6 = as[i];
                i++;
                as14 = new String[as.length - i];
                if(as.length > 2) {
                    int j3 = as.length - i;
                    System.arraycopy(as, i, as14, 0, j3);
                }
            }
            if(!dumpProvider(filedescriptor, printwriter, s6, as14, 0, flag)) {
                printwriter.println((new StringBuilder()).append("No providers match: ").append(s6).toString());
                printwriter.println("Use -h for help.");
            }
        }
        if(true) goto _L9; else goto _L10
_L10:
        if(!"providers".equals(s1) && !"prov".equals(s1))
            break MISSING_BLOCK_LABEL_1011;
        this;
        JVM INSTR monitorenter ;
        String as10[] = as;
        int j2 = i;
        dumpProvidersLocked(filedescriptor, printwriter, as10, j2, true, null);
          goto _L9
label1:
        {
            if(!"service".equals(s1))
                break label1;
            String s5;
            String as13[];
            if(i >= as.length) {
                s5 = null;
                as13 = EMPTY_STRING_ARRAY;
            } else {
                s5 = as[i];
                i++;
                as13 = new String[as.length - i];
                if(as.length > 2) {
                    int i3 = as.length - i;
                    System.arraycopy(as, i, as13, 0, i3);
                }
            }
            if(!dumpService(filedescriptor, printwriter, s5, as13, 0, flag)) {
                printwriter.println((new StringBuilder()).append("No services match: ").append(s5).toString());
                printwriter.println("Use -h for help.");
            }
        }
        if(true) goto _L9; else goto _L11
_L11:
label2:
        {
            if(!"package".equals(s1))
                break label2;
            if(i >= as.length) {
                printwriter.println("package: no package name specified");
                printwriter.println("Use -h for help.");
            } else {
                s = as[i];
                int k2 = i + 1;
                String as12[] = new String[as.length - k2];
                if(as.length > 2) {
                    int l2 = as.length - k2;
                    System.arraycopy(as, k2, as12, 0, l2);
                }
                as = as12;
                i = 0;
                flag2 = true;
            }
        }
        if(true) goto _L9; else goto _L12
_L12:
        if(!"services".equals(s1) && !"s".equals(s1))
            break MISSING_BLOCK_LABEL_1281;
        this;
        JVM INSTR monitorenter ;
        String as11[] = as;
        dumpServicesLocked(filedescriptor, printwriter, as11, i, true, flag1, null);
          goto _L9
        if(!dumpActivity(filedescriptor, printwriter, s1, as, i, flag)) {
            printwriter.println((new StringBuilder()).append("Bad activity command, or no activities match: ").append(s1).toString());
            printwriter.println("Use -h for help.");
        }
          goto _L9
        this;
        JVM INSTR monitorenter ;
        String as1[];
        int j;
        boolean flag3;
        as1 = as;
        j = i;
        flag3 = flag;
        if(dumpPendingIntentsLocked(filedescriptor, printwriter, as1, j, flag3, s))
            printwriter.println(" ");
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        if(dumpBroadcastsLocked(filedescriptor, printwriter, as, i, flag, s))
            printwriter.println(" ");
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        if(dumpProvidersLocked(filedescriptor, printwriter, as, i, flag, s))
            printwriter.println(" ");
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        if(dumpServicesLocked(filedescriptor, printwriter, as, i, flag, flag1, s))
            printwriter.println(" ");
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        if(dumpActivitiesLocked(filedescriptor, printwriter, as, i, flag, flag1, s))
            printwriter.println(" ");
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpProcessesLocked(filedescriptor, printwriter, as, i, flag, s);
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        if(true) goto _L14; else goto _L13
_L13:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    boolean dumpActivitiesLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, boolean flag1, String s) {
        printwriter.println("ACTIVITY MANAGER ACTIVITIES (dumpsys activity activities)");
        printwriter.println("  Main stack:");
        ArrayList arraylist = mMainStack.mHistory;
        boolean flag2;
        ArrayList arraylist1;
        boolean flag3;
        if(!flag)
            flag2 = true;
        else
            flag2 = false;
        dumpHistoryList(filedescriptor, printwriter, arraylist, "  ", "Hist", true, flag2, flag1, s);
        printwriter.println(" ");
        printwriter.println("  Running activities (most recent first):");
        arraylist1 = mMainStack.mLRUActivities;
        if(!flag)
            flag3 = true;
        else
            flag3 = false;
        dumpHistoryList(filedescriptor, printwriter, arraylist1, "  ", "Run", false, flag3, false, s);
        if(mMainStack.mWaitingVisibleActivities.size() > 0) {
            printwriter.println(" ");
            printwriter.println("  Activities waiting for another to become visible:");
            ArrayList arraylist5 = mMainStack.mWaitingVisibleActivities;
            int j;
            ArrayList arraylist2;
            ArrayList arraylist3;
            ArrayList arraylist4;
            boolean flag7;
            if(!flag)
                flag7 = true;
            else
                flag7 = false;
            dumpHistoryList(filedescriptor, printwriter, arraylist5, "  ", "Wait", false, flag7, false, s);
        }
        if(mMainStack.mStoppingActivities.size() > 0) {
            printwriter.println(" ");
            printwriter.println("  Activities waiting to stop:");
            arraylist4 = mMainStack.mStoppingActivities;
            boolean flag6;
            if(!flag)
                flag6 = true;
            else
                flag6 = false;
            dumpHistoryList(filedescriptor, printwriter, arraylist4, "  ", "Stop", false, flag6, false, s);
        }
        if(mMainStack.mGoingToSleepActivities.size() > 0) {
            printwriter.println(" ");
            printwriter.println("  Activities waiting to sleep:");
            arraylist3 = mMainStack.mGoingToSleepActivities;
            boolean flag5;
            if(!flag)
                flag5 = true;
            else
                flag5 = false;
            dumpHistoryList(filedescriptor, printwriter, arraylist3, "  ", "Sleep", false, flag5, false, s);
        }
        if(mMainStack.mFinishingActivities.size() > 0) {
            printwriter.println(" ");
            printwriter.println("  Activities waiting to finish:");
            arraylist2 = mMainStack.mFinishingActivities;
            boolean flag4;
            if(!flag)
                flag4 = true;
            else
                flag4 = false;
            dumpHistoryList(filedescriptor, printwriter, arraylist2, "  ", "Fin", false, flag4, false, s);
        }
        printwriter.println(" ");
        if(mMainStack.mPausingActivity != null)
            printwriter.println((new StringBuilder()).append("  mPausingActivity: ").append(mMainStack.mPausingActivity).toString());
        printwriter.println((new StringBuilder()).append("  mResumedActivity: ").append(mMainStack.mResumedActivity).toString());
        printwriter.println((new StringBuilder()).append("  mFocusedActivity: ").append(mFocusedActivity).toString());
        if(flag) {
            printwriter.println((new StringBuilder()).append("  mLastPausedActivity: ").append(mMainStack.mLastPausedActivity).toString());
            printwriter.println((new StringBuilder()).append("  mSleepTimeout: ").append(mMainStack.mSleepTimeout).toString());
            printwriter.println((new StringBuilder()).append("  mDismissKeyguardOnNextActivity: ").append(mMainStack.mDismissKeyguardOnNextActivity).toString());
        }
        if(mRecentTasks.size() > 0) {
            printwriter.println();
            printwriter.println("  Recent tasks:");
            j = mRecentTasks.size();
            int k = 0;
            while(k < j)  {
                TaskRecord taskrecord = (TaskRecord)mRecentTasks.get(k);
                if(s == null || taskrecord.realActivity != null && s.equals(taskrecord.realActivity)) {
                    printwriter.print("  * Recent #");
                    printwriter.print(k);
                    printwriter.print(": ");
                    printwriter.println(taskrecord);
                    if(flag)
                        ((TaskRecord)mRecentTasks.get(k)).dump(printwriter, "    ");
                }
                k++;
            }
        }
        if(flag) {
            printwriter.println(" ");
            printwriter.println((new StringBuilder()).append("  mCurTask: ").append(mCurTask).toString());
        }
        return true;
    }

    protected boolean dumpActivity(FileDescriptor filedescriptor, PrintWriter printwriter, String s, String as[], int i, boolean flag) {
        ArrayList arraylist = new ArrayList();
        if(!"all".equals(s)) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator1 = mMainStack.mHistory.iterator(); iterator1.hasNext(); arraylist.add((ActivityRecord)iterator1.next()));
        break MISSING_BLOCK_LABEL_69;
        Exception exception2;
        exception2;
        throw exception2;
        this;
        JVM INSTR monitorexit ;
_L5:
        if(arraylist.size() > 0) goto _L4; else goto _L3
_L3:
        boolean flag2 = false;
_L8:
        return flag2;
_L2:
        if(!"top".equals(s))
            break MISSING_BLOCK_LABEL_149;
        this;
        JVM INSTR monitorenter ;
        int k = mMainStack.mHistory.size();
        if(k > 0)
            arraylist.add((ActivityRecord)mMainStack.mHistory.get(k - 1));
          goto _L5
        ItemMatcher itemmatcher;
        itemmatcher = new ItemMatcher();
        itemmatcher.build(s);
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = mMainStack.mHistory.iterator();
        do {
            if(!iterator.hasNext())
                break;
            ActivityRecord activityrecord1 = (ActivityRecord)iterator.next();
            if(itemmatcher.match(activityrecord1, activityrecord1.intent.getComponent()))
                arraylist.add(activityrecord1);
        } while(true);
        break MISSING_BLOCK_LABEL_236;
        Exception exception;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L4:
        String as1[];
        TaskRecord taskrecord;
        boolean flag1;
        int j;
        as1 = new String[as.length - i];
        if(as.length > 2)
            System.arraycopy(as, i, as1, 0, as.length - i);
        taskrecord = null;
        flag1 = false;
        j = -1 + arraylist.size();
_L7:
        ActivityRecord activityrecord;
        if(j < 0)
            break; /* Loop/switch isn't completed */
        activityrecord = (ActivityRecord)arraylist.get(j);
        if(flag1)
            printwriter.println();
        flag1 = true;
        this;
        JVM INSTR monitorenter ;
        if(taskrecord != activityrecord.task) {
            taskrecord = activityrecord.task;
            printwriter.print("TASK ");
            printwriter.print(taskrecord.affinity);
            printwriter.print(" id=");
            printwriter.println(taskrecord.taskId);
            if(flag)
                taskrecord.dump(printwriter, "  ");
        }
        this;
        JVM INSTR monitorexit ;
        dumpActivity("  ", filedescriptor, printwriter, (ActivityRecord)arraylist.get(j), as1, flag);
        j--;
        if(true) goto _L7; else goto _L6
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
_L6:
        flag2 = true;
          goto _L8
    }

    final void dumpApplicationMemoryUsage(FileDescriptor filedescriptor, PrintWriter printwriter, String s, String as[], boolean flag, PrintWriter printwriter1, StringBuilder stringbuilder, 
            StringBuilder stringbuilder1) {
        boolean flag1;
        boolean flag2;
        int i;
        flag1 = false;
        flag2 = false;
        i = 0;
_L6:
        int j = as.length;
        if(i >= j) goto _L2; else goto _L1
_L1:
        String s3 = as[i];
        if(s3 != null && s3.length() > 0 && s3.charAt(0) == '-') goto _L3; else goto _L2
_L2:
        ArrayList arraylist = collectProcesses(printwriter, i, as);
        if(arraylist != null) goto _L5; else goto _L4
_L4:
        return;
_L3:
        i++;
        if("-a".equals(s3)) {
            flag1 = true;
        } else {
label0:
            {
                if(!"--oom".equals(s3))
                    break label0;
                flag2 = true;
            }
        }
          goto _L6
label1:
        {
            if(!"-h".equals(s3))
                break label1;
            printwriter.println("meminfo dump options: [-a] [--oom] [process]");
            printwriter.println("  -a: include all available information for each process.");
            printwriter.println("  --oom: only show processes organized by oom adj.");
            printwriter.println("If [process] is specified it can be the name or ");
            printwriter.println("pid of a specific process to dump.");
        }
          goto _L4
        printwriter.println((new StringBuilder()).append("Unknown argument: ").append(s3).append("; use -h for help").toString());
          goto _L6
_L5:
        boolean flag3;
        ArrayList arraylist1;
        long l2;
        long l3;
        long l4;
        long al[];
        long al1[];
        ArrayList aarraylist[];
        long l5;
        int i1;
        ProcessRecord processrecord;
        long l10;
        MemItem memitem3;
        flag3 = scanArgs(as, "--checkin");
        long l = SystemClock.uptimeMillis();
        long l1 = SystemClock.elapsedRealtime();
        if(arraylist.size() == 1 || flag3)
            flag1 = true;
        String as1[];
        int k;
        int j4;
        long l11;
        android.os.Debug.MemoryInfo memoryinfo1;
        if(flag3) {
            printwriter.println((new StringBuilder()).append(l).append(",").append(l1).toString());
            printwriter.flush();
        } else {
            printwriter.println("Applications Memory Usage (kB):");
            printwriter.println((new StringBuilder()).append("Uptime: ").append(l).append(" Realtime: ").append(l1).toString());
        }
        as1 = new String[as.length - i];
        k = as.length - i;
        System.arraycopy(as, i, as1, 0, k);
        arraylist1 = new ArrayList();
        l2 = 0L;
        l3 = 0L;
        l4 = 0L;
        al = new long[9];
        al1 = new long[DUMP_MEM_OOM_LABEL.length];
        aarraylist = (ArrayList[])new ArrayList[DUMP_MEM_OOM_LABEL.length];
        l5 = 0L;
        i1 = -1 + arraylist.size();
_L10:
        if(i1 < 0)
            break MISSING_BLOCK_LABEL_827;
        processrecord = (ProcessRecord)arraylist.get(i1);
        if(processrecord.thread == null) goto _L8; else goto _L7
_L7:
        if(!flag3 && flag1) {
            printwriter.println((new StringBuilder()).append("\n** MEMINFO in pid ").append(processrecord.pid).append(" [").append(processrecord.processName).append("] **").toString());
            printwriter.flush();
        }
        android.os.Debug.MemoryInfo memoryinfo = null;
        if(flag1) {
label2:
            {
                try {
                    memoryinfo1 = processrecord.thread.dumpMemInfo(filedescriptor, flag3, flag1, as1);
                }
                catch(RemoteException remoteexception) {
                    if(!flag3) {
                        printwriter.println("Got RemoteException!");
                        printwriter.flush();
                    }
                    continue; /* Loop/switch isn't completed */
                }
                memoryinfo = memoryinfo1;
                break label2;
            }
        }
        memoryinfo = new android.os.Debug.MemoryInfo();
        Debug.getMemoryInfo(processrecord.pid, memoryinfo);
        if(true) goto _L9; else goto _L8
_L9:
        int k4;
        if(flag3 || memoryinfo == null)
            break; /* Loop/switch isn't completed */
        l10 = memoryinfo.getTotalPss();
        l5 += l10;
        memitem3 = new MemItem((new StringBuilder()).append(processrecord.processName).append(" (pid ").append(processrecord.pid).append(")").toString(), processrecord.processName, l10, 0);
        arraylist1.add(memitem3);
        l2 += memoryinfo.nativePss;
        l3 += memoryinfo.dalvikPss;
        l4 += memoryinfo.otherPss;
        for(j4 = 0; j4 < 9; j4++) {
            l11 = memoryinfo.getOtherPss(j4);
            al[j4] = l11 + al[j4];
            l4 -= l11;
        }

        k4 = 0;
_L11:
        int i5 = al1.length;
        if(k4 < i5) {
            if(processrecord.setAdj > DUMP_MEM_OOM_ADJ[k4]) {
                int j5 = -1 + al1.length;
                if(k4 != j5)
                    break MISSING_BLOCK_LABEL_821;
            }
            al1[k4] = l10 + al1[k4];
            if(aarraylist[k4] == null)
                aarraylist[k4] = new ArrayList();
            aarraylist[k4].add(memitem3);
        }
_L8:
        i1--;
          goto _L10
        k4++;
          goto _L11
        if(!flag3 && arraylist.size() > 1) {
            ArrayList arraylist2 = new ArrayList();
            arraylist2.add(new MemItem("Native", "Native", l2, -1));
            arraylist2.add(new MemItem("Dalvik", "Dalvik", l3, -2));
            arraylist2.add(new MemItem("Unknown", "Unknown", l4, -3));
            for(int j1 = 0; j1 < 9; j1++) {
                String s2 = android.os.Debug.MemoryInfo.getOtherLabel(j1);
                arraylist2.add(new MemItem(s2, s2, al[j1], j1));
            }

            ArrayList arraylist3 = new ArrayList();
            int k1 = 0;
            do {
                int i2 = al1.length;
                if(k1 >= i2)
                    break;
                if(al1[k1] != 0L) {
                    String s1 = DUMP_MEM_OOM_LABEL[k1];
                    MemItem memitem2 = new MemItem(s1, s1, al1[k1], DUMP_MEM_OOM_ADJ[k1]);
                    memitem2.subitems = aarraylist[k1];
                    arraylist3.add(memitem2);
                }
                k1++;
            } while(true);
            if(stringbuilder != null || stringbuilder1 != null) {
                if(stringbuilder != null)
                    appendMemBucket(stringbuilder, l5, "total", false);
                if(stringbuilder1 != null)
                    appendMemBucket(stringbuilder1, l5, "total", true);
                boolean flag4 = true;
                int j2 = 0;
                do {
                    int k2 = arraylist3.size();
                    if(j2 >= k2)
                        break;
                    MemItem memitem = (MemItem)arraylist3.get(j2);
                    if(memitem.subitems != null && memitem.subitems.size() >= 1 && (memitem.id < 5 || memitem.id == 6 || memitem.id == 7)) {
                        if(stringbuilder != null && memitem.id <= 0)
                            stringbuilder.append(" / ");
                        int i3;
                        if(stringbuilder1 != null)
                            if(memitem.id >= 0) {
                                if(flag4) {
                                    stringbuilder1.append(":");
                                    flag4 = false;
                                }
                                stringbuilder1.append("\n\t at ");
                            } else {
                                stringbuilder1.append("$");
                            }
                        i3 = 0;
                        do {
                            int j3 = memitem.subitems.size();
                            if(i3 >= j3)
                                break;
                            MemItem memitem1 = (MemItem)memitem.subitems.get(i3);
                            if(i3 > 0) {
                                if(stringbuilder != null)
                                    stringbuilder.append(" ");
                                if(stringbuilder1 != null)
                                    stringbuilder1.append("$");
                            }
                            if(stringbuilder != null && memitem.id <= 0)
                                appendMemBucket(stringbuilder, memitem1.pss, memitem1.shortLabel, false);
                            if(stringbuilder1 != null)
                                appendMemBucket(stringbuilder1, memitem1.pss, memitem1.shortLabel, true);
                            i3++;
                        } while(true);
                        if(stringbuilder1 != null && memitem.id >= 0) {
                            stringbuilder1.append("(");
                            int k3 = 0;
                            do {
                                int i4 = DUMP_MEM_OOM_ADJ.length;
                                if(k3 >= i4)
                                    break;
                                if(DUMP_MEM_OOM_ADJ[k3] == memitem.id) {
                                    stringbuilder1.append(DUMP_MEM_OOM_LABEL[k3]);
                                    stringbuilder1.append(":");
                                    stringbuilder1.append(DUMP_MEM_OOM_ADJ[k3]);
                                }
                                k3++;
                            } while(true);
                            stringbuilder1.append(")");
                        }
                    }
                    j2++;
                } while(true);
            }
            if(!flag && !flag2) {
                printwriter.println();
                printwriter.println("Total PSS by process:");
                dumpMemItems(printwriter, "  ", arraylist1, true);
                printwriter.println();
            }
            printwriter.println("Total PSS by OOM adjustment:");
            dumpMemItems(printwriter, "  ", arraylist3, false);
            if(!flag2) {
                int ai[];
                long al2[];
                long l6;
                long l7;
                long l8;
                long l9;
                PrintWriter printwriter2;
                if(printwriter1 != null)
                    printwriter2 = printwriter1;
                else
                    printwriter2 = printwriter;
                printwriter2.println();
                printwriter2.println("Total PSS by category:");
                dumpMemItems(printwriter2, "  ", arraylist2, true);
            }
            printwriter.println();
            printwriter.print("Total PSS: ");
            printwriter.print(l5);
            printwriter.println(" kB");
            ai = new int[1];
            ai[0] = 8224;
            al2 = new long[1];
            Process.readProcFile("/sys/kernel/mm/ksm/pages_shared", ai, null, al2, null);
            l6 = (4096L * al2[0]) / 1024L;
            al2[0] = 0L;
            Process.readProcFile("/sys/kernel/mm/ksm/pages_sharing", ai, null, al2, null);
            l7 = (4096L * al2[0]) / 1024L;
            al2[0] = 0L;
            Process.readProcFile("/sys/kernel/mm/ksm/pages_unshared", ai, null, al2, null);
            l8 = (4096L * al2[0]) / 1024L;
            al2[0] = 0L;
            Process.readProcFile("/sys/kernel/mm/ksm/pages_volatile", ai, null, al2, null);
            l9 = (4096L * al2[0]) / 1024L;
            printwriter.print("      KSM: ");
            printwriter.print(l7);
            printwriter.print(" kB saved from shared ");
            printwriter.print(l6);
            printwriter.println(" kB");
            printwriter.print("           ");
            printwriter.print(l8);
            printwriter.print(" kB unshared; ");
            printwriter.print(l9);
            printwriter.println(" kB volatile");
        }
          goto _L4
    }

    boolean dumpBroadcastsLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, String s) {
        boolean flag1 = false;
        printwriter.println("ACTIVITY MANAGER BROADCAST STATE (dumpsys activity broadcasts)");
        if(flag) {
            if(mRegisteredReceivers.size() > 0) {
                boolean flag3 = false;
                Iterator iterator1 = mRegisteredReceivers.values().iterator();
                do {
                    if(!iterator1.hasNext())
                        break;
                    ReceiverList receiverlist = (ReceiverList)iterator1.next();
                    if(s == null || receiverlist.app != null && s.equals(receiverlist.app.info.packageName)) {
                        if(!flag3) {
                            printwriter.println("  Registered Receivers:");
                            flag1 = true;
                            flag3 = true;
                        }
                        printwriter.print("  * ");
                        printwriter.println(receiverlist);
                        receiverlist.dump(printwriter, "    ");
                    }
                } while(true);
            }
            IntentResolver intentresolver = mReceiverResolver;
            BroadcastQueue abroadcastqueue[];
            int j;
            int k;
            String s1;
            if(flag1)
                s1 = "\n  Receiver Resolver Table:";
            else
                s1 = "  Receiver Resolver Table:";
            if(intentresolver.dump(printwriter, s1, "    ", s, false))
                flag1 = true;
        }
        abroadcastqueue = mBroadcastQueues;
        j = abroadcastqueue.length;
        for(k = 0; k < j; k++)
            flag1 = abroadcastqueue[k].dumpLocked(filedescriptor, printwriter, as, i, flag, s, flag1);

        boolean flag2 = true;
        if(mStickyBroadcasts != null && s == null) {
            if(flag2)
                printwriter.println();
            printwriter.println("  Sticky broadcasts:");
            StringBuilder stringbuilder = new StringBuilder(128);
            for(Iterator iterator = mStickyBroadcasts.entrySet().iterator(); iterator.hasNext();) {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                printwriter.print("  * Sticky action ");
                printwriter.print((String)entry.getKey());
                if(flag) {
                    printwriter.println(":");
                    ArrayList arraylist = (ArrayList)entry.getValue();
                    int j1 = arraylist.size();
                    int k1 = 0;
                    while(k1 < j1)  {
                        stringbuilder.setLength(0);
                        stringbuilder.append("    Intent: ");
                        ((Intent)arraylist.get(k1)).toShortString(stringbuilder, false, true, false, false);
                        printwriter.println(stringbuilder.toString());
                        Bundle bundle = ((Intent)arraylist.get(k1)).getExtras();
                        if(bundle != null) {
                            printwriter.print("      ");
                            printwriter.println(bundle.toString());
                        }
                        k1++;
                    }
                } else {
                    printwriter.println("");
                }
            }

            flag2 = true;
        }
        if(flag) {
            printwriter.println();
            BroadcastQueue abroadcastqueue1[] = mBroadcastQueues;
            int l = abroadcastqueue1.length;
            for(int i1 = 0; i1 < l; i1++) {
                BroadcastQueue broadcastqueue = abroadcastqueue1[i1];
                printwriter.println((new StringBuilder()).append("  mBroadcastsScheduled [").append(broadcastqueue.mQueueName).append("]=").append(broadcastqueue.mBroadcastsScheduled).toString());
            }

            printwriter.println("  mHandler:");
            mHandler.dump(new PrintWriterPrinter(printwriter), "    ");
            flag2 = true;
        }
        return flag2;
    }

    final void dumpDbInfo(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        ArrayList arraylist = collectProcesses(printwriter, 0, as);
        if(arraylist != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        printwriter.println("Applications Database Info:");
        i = -1 + arraylist.size();
_L4:
        ProcessRecord processrecord;
        if(i < 0)
            continue; /* Loop/switch isn't completed */
        processrecord = (ProcessRecord)arraylist.get(i);
        if(processrecord.thread == null)
            break MISSING_BLOCK_LABEL_226;
        printwriter.println((new StringBuilder()).append("\n** Database info for pid ").append(processrecord.pid).append(" [").append(processrecord.processName).append("] **").toString());
        printwriter.flush();
        TransferPipe transferpipe = new TransferPipe();
        processrecord.thread.dumpDbInfo(transferpipe.getWriteFd().getFileDescriptor(), as);
        transferpipe.go(filedescriptor);
        transferpipe.kill();
        break MISSING_BLOCK_LABEL_226;
        Exception exception;
        exception;
        transferpipe.kill();
        throw exception;
        IOException ioexception;
        ioexception;
        printwriter.println((new StringBuilder()).append("Failure while dumping the app: ").append(processrecord).toString());
        printwriter.flush();
        break MISSING_BLOCK_LABEL_226;
        RemoteException remoteexception;
        remoteexception;
        printwriter.println((new StringBuilder()).append("Got a RemoteException while dumping the app ").append(processrecord).toString());
        printwriter.flush();
        i--;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L1; else goto _L5
_L5:
    }

    final void dumpGraphicsHardwareUsage(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        ArrayList arraylist = collectProcesses(printwriter, 0, as);
        if(arraylist != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        long l = SystemClock.uptimeMillis();
        long l1 = SystemClock.elapsedRealtime();
        printwriter.println("Applications Graphics Acceleration Info:");
        printwriter.println((new StringBuilder()).append("Uptime: ").append(l).append(" Realtime: ").append(l1).toString());
        i = -1 + arraylist.size();
_L4:
        ProcessRecord processrecord;
        if(i < 0)
            continue; /* Loop/switch isn't completed */
        processrecord = (ProcessRecord)arraylist.get(i);
        if(processrecord.thread == null)
            break MISSING_BLOCK_LABEL_272;
        printwriter.println((new StringBuilder()).append("\n** Graphics info for pid ").append(processrecord.pid).append(" [").append(processrecord.processName).append("] **").toString());
        printwriter.flush();
        TransferPipe transferpipe = new TransferPipe();
        processrecord.thread.dumpGfxInfo(transferpipe.getWriteFd().getFileDescriptor(), as);
        transferpipe.go(filedescriptor);
        transferpipe.kill();
        break MISSING_BLOCK_LABEL_272;
        Exception exception;
        exception;
        transferpipe.kill();
        throw exception;
        IOException ioexception;
        ioexception;
        printwriter.println((new StringBuilder()).append("Failure while dumping the app: ").append(processrecord).toString());
        printwriter.flush();
        break MISSING_BLOCK_LABEL_272;
        RemoteException remoteexception;
        remoteexception;
        printwriter.println((new StringBuilder()).append("Got a RemoteException while dumping the app ").append(processrecord).toString());
        printwriter.flush();
        i--;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L1; else goto _L5
_L5:
    }

    public boolean dumpHeap(String s, boolean flag, String s1, ParcelFileDescriptor parcelfiledescriptor) throws RemoteException {
        this;
        JVM INSTR monitorenter ;
        Exception exception1;
        if(checkCallingPermission("android.permission.SET_ACTIVITY_WATCHER") != 0)
            throw new SecurityException("Requires permission android.permission.SET_ACTIVITY_WATCHER");
        if(true)
            break MISSING_BLOCK_LABEL_58;
        JVM INSTR monitorexit ;
        Exception exception;
        try {
            throw exception1;
        }
        catch(RemoteException remoteexception) { }
        finally {
            if(parcelfiledescriptor == null) goto _L0; else goto _L0
        }
        throw new IllegalStateException("Process disappeared");
        if(parcelfiledescriptor != null)
            break MISSING_BLOCK_LABEL_74;
        throw new IllegalArgumentException("null fd");
        processrecord = null;
        i = Integer.parseInt(s);
        synchronized(mPidsSelfLocked) {
            processrecord = (ProcessRecord)mPidsSelfLocked.get(i);
        }
_L2:
        if(processrecord != null)
            break MISSING_BLOCK_LABEL_154;
        sparsearray = (SparseArray)mProcessNames.getMap().get(s);
        if(sparsearray != null && sparsearray.size() > 0)
            processrecord = (ProcessRecord)sparsearray.valueAt(0);
        if(processrecord == null || processrecord.thread == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown process: ").append(s).toString());
        break; /* Loop/switch isn't completed */
        exception2;
        sparsearray1;
        JVM INSTR monitorexit ;
        try {
            throw exception2;
        }
        // Misplaced declaration of an exception variable
        catch(NumberFormatException numberformatexception) { }
        finally {
            this;
        }
        if(true) goto _L2; else goto _L1
_L1:
        if(!"1".equals(SystemProperties.get("ro.debuggable", "0")) && (2 & processrecord.info.flags) == 0)
            throw new SecurityException((new StringBuilder()).append("Process not debuggable: ").append(processrecord).toString());
        processrecord.thread.dumpHeap(flag, s1, parcelfiledescriptor);
        parcelfiledescriptor = null;
        this;
        JVM INSTR monitorexit ;
        if(false)
            try {
                throw null;
            }
            catch(IOException ioexception1) { }
        return true;
    }

    boolean dumpOomLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag) {
        boolean flag1 = false;
        if(mLruProcesses.size() > 0) {
            if(false)
                printwriter.println(" ");
            printwriter.println("  OOM levels:");
            printwriter.print("    SYSTEM_ADJ: ");
            printwriter.println(-16);
            printwriter.print("    PERSISTENT_PROC_ADJ: ");
            printwriter.println(-12);
            printwriter.print("    FOREGROUND_APP_ADJ: ");
            printwriter.println(0);
            printwriter.print("    VISIBLE_APP_ADJ: ");
            printwriter.println(1);
            printwriter.print("    PERCEPTIBLE_APP_ADJ: ");
            printwriter.println(2);
            printwriter.print("    HEAVY_WEIGHT_APP_ADJ: ");
            printwriter.println(3);
            printwriter.print("    BACKUP_APP_ADJ: ");
            printwriter.println(4);
            printwriter.print("    SERVICE_ADJ: ");
            printwriter.println(5);
            printwriter.print("    HOME_APP_ADJ: ");
            printwriter.println(6);
            printwriter.print("    PREVIOUS_APP_ADJ: ");
            printwriter.println(7);
            printwriter.print("    SERVICE_B_ADJ: ");
            printwriter.println(8);
            printwriter.print("    HIDDEN_APP_MIN_ADJ: ");
            printwriter.println(ProcessList.HIDDEN_APP_MIN_ADJ);
            printwriter.print("    HIDDEN_APP_MAX_ADJ: ");
            printwriter.println(15);
            if(true)
                printwriter.println(" ");
            printwriter.println("  Process OOM control:");
            dumpProcessOomList(printwriter, this, mLruProcesses, "    ", "Proc", "PERS", true, null);
            flag1 = true;
        }
        dumpProcessesToGc(filedescriptor, printwriter, as, i, flag1, flag, null);
        printwriter.println();
        printwriter.println((new StringBuilder()).append("  mHomeProcess: ").append(mHomeProcess).toString());
        printwriter.println((new StringBuilder()).append("  mPreviousProcess: ").append(mPreviousProcess).toString());
        if(mHeavyWeightProcess != null)
            printwriter.println((new StringBuilder()).append("  mHeavyWeightProcess: ").append(mHeavyWeightProcess).toString());
        return true;
    }

    boolean dumpPendingIntentsLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, String s) {
        boolean flag1 = false;
        if(mIntentSenderRecords.size() > 0) {
            boolean flag2 = false;
            Iterator iterator = mIntentSenderRecords.values().iterator();
            do {
                if(!iterator.hasNext())
                    break;
                WeakReference weakreference = (WeakReference)iterator.next();
                PendingIntentRecord pendingintentrecord;
                if(weakreference != null)
                    pendingintentrecord = (PendingIntentRecord)weakreference.get();
                else
                    pendingintentrecord = null;
                if(s == null || pendingintentrecord != null && s.equals(pendingintentrecord.key.packageName)) {
                    if(!flag2) {
                        printwriter.println("ACTIVITY MANAGER PENDING INTENTS (dumpsys activity intents)");
                        flag2 = true;
                    }
                    flag1 = true;
                    if(pendingintentrecord != null) {
                        printwriter.print("  * ");
                        printwriter.println(pendingintentrecord);
                        if(flag)
                            pendingintentrecord.dump(printwriter, "    ");
                    } else {
                        printwriter.print("  * ");
                        printwriter.println(weakreference);
                    }
                }
            } while(true);
        }
        return flag1;
    }

    boolean dumpProcessesLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, String s) {
        boolean flag1;
        int j;
        flag1 = false;
        j = 0;
        printwriter.println("ACTIVITY MANAGER RUNNING PROCESSES (dumpsys activity processes)");
        if(flag) {
            for(Iterator iterator3 = mProcessNames.getMap().values().iterator(); iterator3.hasNext();) {
                SparseArray sparsearray4 = (SparseArray)iterator3.next();
                int j3 = sparsearray4.size();
                int k3 = 0;
                while(k3 < j3)  {
                    ProcessRecord processrecord5 = (ProcessRecord)sparsearray4.valueAt(k3);
                    if(s == null || s.equals(processrecord5.info.packageName)) {
                        if(!flag1) {
                            printwriter.println("  All known processes:");
                            flag1 = true;
                        }
                        String s4;
                        if(processrecord5.persistent)
                            s4 = "  *PERS*";
                        else
                            s4 = "  *APP*";
                        printwriter.print(s4);
                        printwriter.print(" UID ");
                        printwriter.print(sparsearray4.keyAt(k3));
                        printwriter.print(" ");
                        printwriter.println(processrecord5);
                        processrecord5.dump(printwriter, "    ");
                        if(processrecord5.persistent)
                            j++;
                    }
                    k3++;
                }
            }

        }
        if(mIsolatedProcesses.size() > 0) {
            if(flag1)
                printwriter.println(" ");
            flag1 = true;
            printwriter.println("  Isolated process list (sorted by uid):");
            int i3 = 0;
            while(i3 < mIsolatedProcesses.size())  {
                ProcessRecord processrecord4 = (ProcessRecord)mIsolatedProcesses.valueAt(i3);
                if(s == null || s.equals(processrecord4.info.packageName)) {
                    Object aobj[] = new Object[3];
                    aobj[0] = "    ";
                    aobj[1] = Integer.valueOf(i3);
                    aobj[2] = processrecord4.toString();
                    printwriter.println(String.format("%sIsolated #%2d: %s", aobj));
                }
                i3++;
            }
        }
        if(mLruProcesses.size() > 0) {
            if(flag1)
                printwriter.println(" ");
            printwriter.println("  Process LRU list (sorted by oom_adj):");
            dumpProcessOomList(printwriter, this, mLruProcesses, "    ", "Proc", "PERS", false, s);
            flag1 = true;
        }
        if(!flag) goto _L2; else goto _L1
_L1:
        SparseArray sparsearray3 = mPidsSelfLocked;
        sparsearray3;
        JVM INSTR monitorenter ;
        boolean flag6;
        int l2;
        flag6 = false;
        l2 = 0;
_L6:
        if(l2 < mPidsSelfLocked.size()) {
            ProcessRecord processrecord3 = (ProcessRecord)mPidsSelfLocked.valueAt(l2);
            boolean flag2;
            StringBuilder stringbuilder;
            boolean flag3;
            Iterator iterator;
            java.util.Map.Entry entry;
            String s1;
            int k;
            Iterator iterator1;
            java.util.Map.Entry entry1;
            String s2;
            SparseArray sparsearray;
            int l;
            int i1;
            int j1;
            ProcessRecord processrecord;
            boolean flag4;
            long l1;
            Iterator iterator2;
            java.util.Map.Entry entry2;
            String s3;
            SparseArray sparsearray1;
            int k1;
            int i2;
            int j2;
            ProcessRecord processrecord1;
            SparseArray sparsearray2;
            boolean flag5;
            Exception exception;
            ProcessRecord processrecord2;
            Exception exception1;
            if(s == null || s.equals(processrecord3.info.packageName)) {
                if(!flag6) {
                    if(flag1)
                        printwriter.println(" ");
                    flag1 = true;
                    printwriter.println("  PID mappings:");
                    flag6 = true;
                }
                printwriter.print("    PID #");
                printwriter.print(mPidsSelfLocked.keyAt(l2));
                printwriter.print(": ");
                printwriter.println(mPidsSelfLocked.valueAt(l2));
            }
            l2++;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_548;
        exception1;
        throw exception1;
        sparsearray3;
        JVM INSTR monitorexit ;
_L2:
        if(mForegroundProcesses.size() <= 0) goto _L4; else goto _L3
_L3:
        sparsearray2 = mPidsSelfLocked;
        sparsearray2;
        JVM INSTR monitorenter ;
        flag5 = false;
        for(int k2 = 0; k2 < mForegroundProcesses.size(); k2++) {
            processrecord2 = (ProcessRecord)mPidsSelfLocked.get(((ForegroundToken)mForegroundProcesses.valueAt(k2)).pid);
            if(s == null || processrecord2 != null && s.equals(processrecord2.info.packageName)) {
                if(!flag5) {
                    if(flag1)
                        printwriter.println(" ");
                    flag1 = true;
                    printwriter.println("  Foreground Processes:");
                    flag5 = true;
                }
                printwriter.print("    PID #");
                printwriter.print(mForegroundProcesses.keyAt(k2));
                printwriter.print(": ");
                printwriter.println(mForegroundProcesses.valueAt(k2));
            }
            break MISSING_BLOCK_LABEL_2484;
        }

        break MISSING_BLOCK_LABEL_725;
        exception;
        throw exception;
        sparsearray2;
        JVM INSTR monitorexit ;
_L4:
        if(mPersistentStartingProcesses.size() > 0) {
            if(flag1)
                printwriter.println(" ");
            printwriter.println("  Persisent processes that are starting:");
            dumpProcessList(printwriter, this, mPersistentStartingProcesses, "    ", "Starting Norm", "Restarting PERS", s);
            flag1 = true;
        }
        if(mRemovedProcesses.size() > 0) {
            if(flag1)
                printwriter.println(" ");
            printwriter.println("  Processes that are being removed:");
            dumpProcessList(printwriter, this, mRemovedProcesses, "    ", "Removed Norm", "Removed PERS", s);
            flag1 = true;
        }
        if(mProcessesOnHold.size() > 0) {
            if(flag1)
                printwriter.println(" ");
            printwriter.println("  Processes that are on old until the system is ready:");
            dumpProcessList(printwriter, this, mProcessesOnHold, "    ", "OnHold Norm", "OnHold PERS", s);
            flag1 = true;
        }
        flag2 = dumpProcessesToGc(filedescriptor, printwriter, as, i, flag1, flag, s);
        if(mProcessCrashTimes.getMap().size() > 0) {
            flag4 = false;
            l1 = SystemClock.uptimeMillis();
            for(iterator2 = mProcessCrashTimes.getMap().entrySet().iterator(); iterator2.hasNext();) {
                entry2 = (java.util.Map.Entry)iterator2.next();
                s3 = (String)entry2.getKey();
                sparsearray1 = (SparseArray)entry2.getValue();
                k1 = sparsearray1.size();
                i2 = 0;
                while(i2 < k1)  {
                    j2 = sparsearray1.keyAt(i2);
                    processrecord1 = (ProcessRecord)mProcessNames.get(s3, j2);
                    if(s == null || processrecord1 != null && s.equals(processrecord1.info.packageName)) {
                        if(!flag4) {
                            if(flag2)
                                printwriter.println(" ");
                            flag2 = true;
                            printwriter.println("  Time since processes crashed:");
                            flag4 = true;
                        }
                        printwriter.print("    Process ");
                        printwriter.print(s3);
                        printwriter.print(" uid ");
                        printwriter.print(j2);
                        printwriter.print(": last crashed ");
                        TimeUtils.formatDuration(l1 - ((Long)sparsearray1.valueAt(i2)).longValue(), printwriter);
                        printwriter.println(" ago");
                    }
                    i2++;
                }
            }

        }
        if(mBadProcesses.getMap().size() > 0)
            for(iterator1 = mBadProcesses.getMap().entrySet().iterator(); iterator1.hasNext();) {
                entry1 = (java.util.Map.Entry)iterator1.next();
                s2 = (String)entry1.getKey();
                sparsearray = (SparseArray)entry1.getValue();
                l = sparsearray.size();
                i1 = 0;
                while(i1 < l)  {
                    j1 = sparsearray.keyAt(i1);
                    processrecord = (ProcessRecord)mProcessNames.get(s2, j1);
                    if(s == null || processrecord != null && s.equals(processrecord.info.packageName)) {
                        if(true) {
                            if(flag2)
                                printwriter.println(" ");
                            flag2 = true;
                            printwriter.println("  Bad processes:");
                        }
                        printwriter.print("    Bad process ");
                        printwriter.print(s2);
                        printwriter.print(" uid ");
                        printwriter.print(j1);
                        printwriter.print(": crashed at time ");
                        printwriter.println(sparsearray.valueAt(i1));
                    }
                    i1++;
                }
            }

        printwriter.println();
        printwriter.println((new StringBuilder()).append("  mHomeProcess: ").append(mHomeProcess).toString());
        printwriter.println((new StringBuilder()).append("  mPreviousProcess: ").append(mPreviousProcess).toString());
        if(flag) {
            stringbuilder = new StringBuilder(128);
            stringbuilder.append("  mPreviousProcessVisibleTime: ");
            TimeUtils.formatDuration(mPreviousProcessVisibleTime, stringbuilder);
            printwriter.println(stringbuilder);
        }
        if(mHeavyWeightProcess != null)
            printwriter.println((new StringBuilder()).append("  mHeavyWeightProcess: ").append(mHeavyWeightProcess).toString());
        printwriter.println((new StringBuilder()).append("  mConfiguration: ").append(mConfiguration).toString());
        if(flag) {
            printwriter.println((new StringBuilder()).append("  mConfigWillChange: ").append(mMainStack.mConfigWillChange).toString());
            if(mCompatModePackages.getPackages().size() > 0) {
                flag3 = false;
                iterator = mCompatModePackages.getPackages().entrySet().iterator();
                do {
                    if(!iterator.hasNext())
                        break;
                    entry = (java.util.Map.Entry)iterator.next();
                    s1 = (String)entry.getKey();
                    k = ((Integer)entry.getValue()).intValue();
                    if(s == null || s.equals(s1)) {
                        if(!flag3) {
                            printwriter.println("  mScreenCompatPackages:");
                            flag3 = true;
                        }
                        printwriter.print("    ");
                        printwriter.print(s1);
                        printwriter.print(": ");
                        printwriter.print(k);
                        printwriter.println();
                    }
                } while(true);
            }
        }
        if(mSleeping || mWentToSleep || mLockScreenShown)
            printwriter.println((new StringBuilder()).append("  mSleeping=").append(mSleeping).append(" mWentToSleep=").append(mWentToSleep).append(" mLockScreenShown ").append(mLockScreenShown).toString());
        if(mShuttingDown)
            printwriter.println((new StringBuilder()).append("  mShuttingDown=").append(mShuttingDown).toString());
        if(mDebugApp != null || mOrigDebugApp != null || mDebugTransient || mOrigWaitForDebugger)
            printwriter.println((new StringBuilder()).append("  mDebugApp=").append(mDebugApp).append("/orig=").append(mOrigDebugApp).append(" mDebugTransient=").append(mDebugTransient).append(" mOrigWaitForDebugger=").append(mOrigWaitForDebugger).toString());
        if(mOpenGlTraceApp != null)
            printwriter.println((new StringBuilder()).append("  mOpenGlTraceApp=").append(mOpenGlTraceApp).toString());
        if(mProfileApp != null || mProfileProc != null || mProfileFile != null || mProfileFd != null) {
            printwriter.println((new StringBuilder()).append("  mProfileApp=").append(mProfileApp).append(" mProfileProc=").append(mProfileProc).toString());
            printwriter.println((new StringBuilder()).append("  mProfileFile=").append(mProfileFile).append(" mProfileFd=").append(mProfileFd).toString());
            printwriter.println((new StringBuilder()).append("  mProfileType=").append(mProfileType).append(" mAutoStopProfiler=").append(mAutoStopProfiler).toString());
        }
        if(mAlwaysFinishActivities || mController != null)
            printwriter.println((new StringBuilder()).append("  mAlwaysFinishActivities=").append(mAlwaysFinishActivities).append(" mController=").append(mController).toString());
        if(flag) {
            printwriter.println((new StringBuilder()).append("  Total persistent processes: ").append(j).toString());
            printwriter.println((new StringBuilder()).append("  mStartRunning=").append(mStartRunning).append(" mProcessesReady=").append(mProcessesReady).append(" mSystemReady=").append(mSystemReady).toString());
            printwriter.println((new StringBuilder()).append("  mBooting=").append(mBooting).append(" mBooted=").append(mBooted).append(" mFactoryTest=").append(mFactoryTest).toString());
            printwriter.print("  mLastPowerCheckRealtime=");
            TimeUtils.formatDuration(mLastPowerCheckRealtime, printwriter);
            printwriter.println("");
            printwriter.print("  mLastPowerCheckUptime=");
            TimeUtils.formatDuration(mLastPowerCheckUptime, printwriter);
            printwriter.println("");
            printwriter.println((new StringBuilder()).append("  mGoingToSleep=").append(mMainStack.mGoingToSleep).toString());
            printwriter.println((new StringBuilder()).append("  mLaunchingActivity=").append(mMainStack.mLaunchingActivity).toString());
            printwriter.println((new StringBuilder()).append("  mAdjSeq=").append(mAdjSeq).append(" mLruSeq=").append(mLruSeq).toString());
            printwriter.println((new StringBuilder()).append("  mNumServiceProcs=").append(mNumServiceProcs).append(" mNewNumServiceProcs=").append(mNewNumServiceProcs).toString());
        }
        return true;
        if(true) goto _L6; else goto _L5
_L5:
    }

    boolean dumpProcessesToGc(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, boolean flag1, String s) {
        if(mProcessesToGc.size() > 0) {
            boolean flag2 = false;
            long l = SystemClock.uptimeMillis();
            int j = 0;
            while(j < mProcessesToGc.size())  {
                ProcessRecord processrecord = (ProcessRecord)mProcessesToGc.get(j);
                if(s == null || s.equals(processrecord.info.packageName)) {
                    if(!flag2) {
                        if(flag)
                            printwriter.println(" ");
                        flag = true;
                        printwriter.println("  Processes that are waiting to GC:");
                        flag2 = true;
                    }
                    printwriter.print("    Process ");
                    printwriter.println(processrecord);
                    printwriter.print("      lowMem=");
                    printwriter.print(processrecord.reportLowMemory);
                    printwriter.print(", last gced=");
                    printwriter.print(l - processrecord.lastRequestedGc);
                    printwriter.print(" ms ago, last lowMem=");
                    printwriter.print(l - processrecord.lastLowMemory);
                    printwriter.println(" ms ago");
                }
                j++;
            }
        }
        return flag;
    }

    protected boolean dumpProvider(FileDescriptor filedescriptor, PrintWriter printwriter, String s, String as[], int i, boolean flag) {
        return mProviderMap.dumpProvider(filedescriptor, printwriter, s, as, i, flag);
    }

    boolean dumpProvidersLocked(FileDescriptor filedescriptor, PrintWriter printwriter, String as[], int i, boolean flag, String s) {
        boolean flag1 = true;
        (new ItemMatcher()).build(as, i);
        printwriter.println("ACTIVITY MANAGER CONTENT PROVIDERS (dumpsys activity providers)");
        mProviderMap.dumpProvidersLocked(printwriter, flag);
        if(mLaunchingProviders.size() > 0) {
            boolean flag2 = false;
            int l = -1 + mLaunchingProviders.size();
            while(l >= 0)  {
                ContentProviderRecord contentproviderrecord = (ContentProviderRecord)mLaunchingProviders.get(l);
                if(s == null || s.equals(contentproviderrecord.name.getPackageName())) {
                    if(!flag2) {
                        if(flag1)
                            printwriter.println(" ");
                        flag1 = true;
                        printwriter.println("  Launching content providers:");
                        flag2 = true;
                    }
                    printwriter.print("  Launching #");
                    printwriter.print(l);
                    printwriter.print(": ");
                    printwriter.println(contentproviderrecord);
                }
                l--;
            }
        }
        if(mGrantedUriPermissions.size() > 0) {
            if(flag1)
                printwriter.println();
            printwriter.println("Granted Uri Permissions:");
label0:
            for(int j = 0; j < mGrantedUriPermissions.size(); j++) {
                int k = mGrantedUriPermissions.keyAt(j);
                HashMap hashmap = (HashMap)mGrantedUriPermissions.valueAt(j);
                printwriter.print("  * UID ");
                printwriter.print(k);
                printwriter.println(" holds:");
                Iterator iterator = hashmap.values().iterator();
                do {
                    if(!iterator.hasNext())
                        continue label0;
                    UriPermission uripermission = (UriPermission)iterator.next();
                    printwriter.print("    ");
                    printwriter.println(uripermission);
                    if(flag)
                        uripermission.dump(printwriter, "      ");
                } while(true);
            }

            flag1 = true;
        }
        return flag1;
    }

    protected boolean dumpService(FileDescriptor filedescriptor, PrintWriter printwriter, String s, String as[], int i, boolean flag) {
        ArrayList arraylist = new ArrayList();
        if(!"all".equals(s)) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorenter ;
        try {
            for(Iterator iterator2 = AppGlobals.getPackageManager().getUsers().iterator(); iterator2.hasNext();) {
                UserInfo userinfo1 = (UserInfo)iterator2.next();
                Iterator iterator3 = mServiceMap.getAllServices(userinfo1.id).iterator();
                while(iterator3.hasNext()) 
                    arraylist.add((ServiceRecord)iterator3.next());
            }

        }
        catch(RemoteException remoteexception1) { }
        finally { }
        this;
        JVM INSTR monitorexit ;
_L10:
        ComponentName componentname;
        int j;
        Exception exception;
        RemoteException remoteexception;
        boolean flag2;
        Iterator iterator;
        UserInfo userinfo;
        Iterator iterator1;
        ServiceRecord servicerecord;
        int l;
        Exception exception1;
        if(arraylist.size() <= 0) {
            flag2 = false;
        } else {
            boolean flag1 = false;
            for(int k = 0; k < arraylist.size(); k++) {
                if(flag1)
                    printwriter.println();
                flag1 = true;
                dumpService("", filedescriptor, printwriter, (ServiceRecord)arraylist.get(k), as, flag);
            }

            flag2 = true;
        }
        return flag2;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
_L2:
        if(s != null)
            componentname = ComponentName.unflattenFromString(s);
        else
            componentname = null;
        j = 0;
        if(componentname != null)
            break MISSING_BLOCK_LABEL_166;
        l = Integer.parseInt(s, 16);
        j = l;
        s = null;
        componentname = null;
_L12:
        this;
        JVM INSTR monitorenter ;
        iterator = AppGlobals.getPackageManager().getUsers().iterator();
_L6:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        userinfo = (UserInfo)iterator.next();
        iterator1 = mServiceMap.getAllServices(userinfo.id).iterator();
_L9:
        if(!iterator1.hasNext()) goto _L6; else goto _L5
_L5:
        servicerecord = (ServiceRecord)iterator1.next();
        if(componentname == null) goto _L8; else goto _L7
_L7:
        if(servicerecord.name.equals(componentname))
            arraylist.add(servicerecord);
          goto _L9
        remoteexception;
_L4:
        this;
        JVM INSTR monitorexit ;
          goto _L10
        exception;
        throw exception;
_L8:
        if(s == null)
            break MISSING_BLOCK_LABEL_325;
        if(!servicerecord.name.flattenToString().contains(s)) goto _L9; else goto _L11
_L11:
        arraylist.add(servicerecord);
          goto _L9
        if(System.identityHashCode(servicerecord) == j)
            arraylist.add(servicerecord);
          goto _L9
        RuntimeException runtimeexception;
        runtimeexception;
          goto _L12
    }


// JavaClassFileOutputException: Prev chain is broken

    void enableScreenAfterBoot() {
        EventLog.writeEvent(3050, SystemClock.uptimeMillis());
        mWindowManager.enableScreenAfterBoot();
        this;
        JVM INSTR monitorenter ;
        updateEventDispatchingLocked();
        return;
    }

    void enforceCallingPermission(String s, String s1) {
        if(checkCallingPermission(s) == 0) {
            return;
        } else {
            String s2 = (new StringBuilder()).append("Permission Denial: ").append(s1).append(" from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append(s).toString();
            Slog.w("ActivityManager", s2);
            throw new SecurityException(s2);
        }
    }

    void enforceNotIsolatedCaller(String s) {
        if(UserId.isIsolated(Binder.getCallingUid()))
            throw new SecurityException((new StringBuilder()).append("Isolated process not allowed to call ").append(s).toString());
        else
            return;
    }

    final void ensureBootCompleted() {
        boolean flag = true;
        this;
        JVM INSTR monitorenter ;
        boolean flag1;
        flag1 = mBooting;
        mBooting = false;
        if(mBooted)
            flag = false;
        mBooted = true;
        this;
        JVM INSTR monitorexit ;
        if(flag1)
            finishBooting();
        if(flag)
            enableScreenAfterBoot();
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void ensurePackageDexOpt(String s) {
        IPackageManager ipackagemanager = AppGlobals.getPackageManager();
        if(ipackagemanager.performDexOpt(s))
            mDidDexOpt = true;
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public final void enterSafeMode() {
        this;
        JVM INSTR monitorenter ;
        boolean flag = mSystemReady;
        Exception exception;
        if(!flag)
            try {
                AppGlobals.getPackageManager().enterSafeMode();
            }
            catch(RemoteException remoteexception) { }
            finally {
                this;
            }
        this;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public final boolean finishActivity(IBinder ibinder, int i, Intent intent) {
        boolean flag;
        flag = false;
        if(intent != null && intent.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Intent");
        this;
        JVM INSTR monitorenter ;
        if(mController == null) goto _L2; else goto _L1
_L1:
        ActivityRecord activityrecord = mMainStack.topRunningActivityLocked(ibinder, 0);
        if(activityrecord == null) goto _L2; else goto _L3
_L3:
        boolean flag1 = true;
        boolean flag2 = mController.activityResuming(activityrecord.packageName);
        flag1 = flag2;
_L5:
        if(flag1)
            break; /* Loop/switch isn't completed */
          goto _L4
        RemoteException remoteexception;
        remoteexception;
        mController = null;
        if(true) goto _L5; else goto _L2
        Exception exception;
        exception;
        throw exception;
_L2:
        long l = Binder.clearCallingIdentity();
        flag = mMainStack.requestFinishActivityLocked(ibinder, i, intent, "app-request");
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
_L4:
        return flag;
    }

    public boolean finishActivityAffinity(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        boolean flag = mMainStack.finishActivityAffinityLocked(ibinder);
        Binder.restoreCallingIdentity(l);
        return flag;
    }

    final void finishBooting() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentfilter.addDataScheme("package");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                String as[] = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
                if(as == null) goto _L2; else goto _L1
_L1:
                int k;
                int l;
                k = as.length;
                l = 0;
_L3:
                String s;
                if(l >= k)
                    break; /* Loop/switch isn't completed */
                s = as[l];
                ActivityManagerService activitymanagerservice = ActivityManagerService.this;
                activitymanagerservice;
                JVM INSTR monitorenter ;
                if(forceStopPackageLocked(s, -1, false, false, false, false, 0)) {
                    setResultCode(-1);
                    break; /* Loop/switch isn't completed */
                }
                l++;
                if(true) goto _L3; else goto _L2
_L2:
            }

            final ActivityManagerService this$0;

             {
                this$0 = ActivityManagerService.this;
                super();
            }
        }, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.USER_REMOVED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                onUserRemoved(intent);
            }

            final ActivityManagerService this$0;

             {
                this$0 = ActivityManagerService.this;
                super();
            }
        }, intentfilter1);
        this;
        JVM INSTR monitorenter ;
        int i = mProcessesOnHold.size();
        if(i > 0) {
            ArrayList arraylist = new ArrayList(mProcessesOnHold);
            for(int j = 0; j < i; j++)
                startProcessLocked((ProcessRecord)arraylist.get(j), "on-hold", null);

        }
        if(mFactoryTest != 1) {
            Message message = mHandler.obtainMessage(27);
            mHandler.sendMessageDelayed(message, 0xdbba0L);
            SystemProperties.set("sys.boot_completed", "1");
            SystemProperties.set("dev.bootcomplete", "1");
            broadcastIntentLocked(null, null, new Intent("android.intent.action.BOOT_COMPLETED", null), null, null, 0, null, null, "android.permission.RECEIVE_BOOT_COMPLETED", false, false, MY_PID, 1000, Binder.getOrigCallingUser());
        }
        return;
    }

    public final void finishHeavyWeightApp() {
        if(checkCallingPermission("android.permission.FORCE_STOP_PACKAGES") != 0) {
            String s = (new StringBuilder()).append("Permission Denial: finishHeavyWeightApp() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.FORCE_STOP_PACKAGES").toString();
            Slog.w("ActivityManager", s);
            throw new SecurityException(s);
        }
        this;
        JVM INSTR monitorenter ;
        if(mHeavyWeightProcess != null) goto _L2; else goto _L1
_L2:
        ArrayList arraylist;
        int i;
        arraylist = new ArrayList(mHeavyWeightProcess.activities);
        i = 0;
_L4:
        if(i < arraylist.size()) {
            ActivityRecord activityrecord = (ActivityRecord)arraylist.get(i);
            if(!activityrecord.finishing) {
                int j = mMainStack.indexOfTokenLocked(activityrecord.appToken);
                if(j >= 0)
                    mMainStack.finishActivityLocked(activityrecord, j, 0, null, "finish-heavy");
            }
            i++;
            continue; /* Loop/switch isn't completed */
        }
        mHeavyWeightProcess = null;
        mHandler.sendEmptyMessage(25);
_L1:
        return;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void finishInstrumentation(IApplicationThread iapplicationthread, int i, Bundle bundle) {
        UserId.getCallingUserId();
        if(bundle != null && bundle.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Intent");
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord = getRecordForAppLocked(iapplicationthread);
        if(processrecord == null) {
            Slog.w("ActivityManager", (new StringBuilder()).append("finishInstrumentation: no app for ").append(iapplicationthread).toString());
        } else {
            long l = Binder.clearCallingIdentity();
            finishInstrumentationLocked(processrecord, i, bundle);
            Binder.restoreCallingIdentity(l);
        }
        return;
    }

    void finishInstrumentationLocked(ProcessRecord processrecord, int i, Bundle bundle) {
        if(processrecord.instrumentationWatcher != null)
            try {
                processrecord.instrumentationWatcher.instrumentationFinished(processrecord.instrumentationClass, i, bundle);
            }
            catch(RemoteException remoteexception) { }
        processrecord.instrumentationWatcher = null;
        processrecord.instrumentationClass = null;
        processrecord.instrumentationInfo = null;
        processrecord.instrumentationProfileFile = null;
        processrecord.instrumentationArguments = null;
        forceStopPackageLocked(processrecord.processName, -1, false, false, true, true, processrecord.userId);
    }

    public void finishReceiver(IBinder ibinder, int i, String s, Bundle bundle, boolean flag) {
        long l;
        if(bundle != null && bundle.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in Bundle");
        l = Binder.clearCallingIdentity();
        boolean flag1 = false;
        this;
        JVM INSTR monitorenter ;
        BroadcastRecord broadcastrecord;
        broadcastrecord = broadcastRecordForReceiverLocked(ibinder);
        if(broadcastrecord != null)
            flag1 = broadcastrecord.queue.finishReceiverLocked(broadcastrecord, i, s, bundle, flag, true);
        this;
        JVM INSTR monitorexit ;
        if(!flag1)
            break MISSING_BLOCK_LABEL_81;
        broadcastrecord.queue.processNextBroadcast(false);
        trimApplications();
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public final void finishSubActivity(IBinder ibinder, String s, int i) {
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        mMainStack.finishSubActivityLocked(ibinder, s, i);
        Binder.restoreCallingIdentity(l);
        return;
    }

    public void forceStopPackage(String s) {
        int i;
        long l;
        if(checkCallingPermission("android.permission.FORCE_STOP_PACKAGES") != 0) {
            String s1 = (new StringBuilder()).append("Permission Denial: forceStopPackage() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.FORCE_STOP_PACKAGES").toString();
            Slog.w("ActivityManager", s1);
            throw new SecurityException(s1);
        }
        i = UserId.getCallingUserId();
        l = Binder.clearCallingIdentity();
        IPackageManager ipackagemanager = AppGlobals.getPackageManager();
        int j = -1;
        this;
        JVM INSTR monitorenter ;
        int k = ipackagemanager.getPackageUid(s, i);
        j = k;
_L5:
        if(j != -1) goto _L2; else goto _L1
_L1:
        Slog.w("ActivityManager", (new StringBuilder()).append("Invalid packageName: ").append(s).toString());
        this;
        JVM INSTR monitorexit ;
_L3:
        Binder.restoreCallingIdentity(l);
        return;
_L2:
        forceStopPackageLocked(s, j);
        ipackagemanager.setPackageStoppedState(s, true, i);
_L4:
        this;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Failed trying to unstop package ").append(s).append(": ").append(illegalargumentexception).toString());
          goto _L4
        RemoteException remoteexception;
        remoteexception;
          goto _L5
        RemoteException remoteexception1;
        remoteexception1;
          goto _L4
    }

    void foregroundTokenDied(ForegroundToken foregroundtoken) {
        this;
        JVM INSTR monitorenter ;
        SparseArray sparsearray = mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        if((ForegroundToken)mForegroundProcesses.get(foregroundtoken.pid) == foregroundtoken) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
_L3:
        return;
_L2:
        ProcessRecord processrecord;
        mForegroundProcesses.remove(foregroundtoken.pid);
        processrecord = (ProcessRecord)mPidsSelfLocked.get(foregroundtoken.pid);
        if(processrecord != null)
            break MISSING_BLOCK_LABEL_76;
        sparsearray;
        JVM INSTR monitorexit ;
        this;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception;
        exception;
        throw exception;
        processrecord.forcingToForeground = null;
        processrecord.foregroundServices = false;
        sparsearray;
        JVM INSTR monitorexit ;
        updateOomAdjLocked();
        this;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception1;
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public ComponentName getActivityClassForToken(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = mMainStack.isInStackLocked(ibinder);
        ComponentName componentname;
        if(activityrecord == null)
            componentname = null;
        else
            componentname = activityrecord.intent.getComponent();
        return componentname;
    }

    ActivityInfo getActivityInfoForUser(ActivityInfo activityinfo, int i) {
        ActivityInfo activityinfo1;
        if(activityinfo == null || i < 1 && activityinfo.applicationInfo.uid < 0x186a0) {
            activityinfo1 = activityinfo;
        } else {
            activityinfo1 = new ActivityInfo(activityinfo);
            activityinfo1.applicationInfo = getAppInfoForUser(activityinfo1.applicationInfo, i);
        }
        return activityinfo1;
    }

    public ComponentName getCallingActivity(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = getCallingRecordLocked(ibinder);
        ComponentName componentname;
        if(activityrecord != null)
            componentname = activityrecord.intent.getComponent();
        else
            componentname = null;
        return componentname;
    }

    public String getCallingPackage(IBinder ibinder) {
        String s = null;
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord;
        activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord == null)
            break MISSING_BLOCK_LABEL_113;
        if(activityrecord.resultTo != null) {
            ActivityRecord activityrecord1 = activityrecord.resultTo;
            if(activityrecord1 != null && activityrecord1.app != null)
                s = ((ComponentInfo) (activityrecord1.info)).packageName;
            break MISSING_BLOCK_LABEL_113;
        }
        break MISSING_BLOCK_LABEL_71;
        Exception exception;
        exception;
        throw exception;
        int i = activityrecord.launchedFromUid;
        this;
        JVM INSTR monitorexit ;
        if(i > 0)
            try {
                String as[] = AppGlobals.getPackageManager().getPackagesForUid(i);
                if(as.length > 0)
                    s = as[0];
            }
            catch(RemoteException remoteexception) { }
        return s;
    }

    public Configuration getConfiguration() {
        this;
        JVM INSTR monitorenter ;
        Configuration configuration = new Configuration(mConfiguration);
        return configuration;
    }

    public final android.app.IActivityManager.ContentProviderHolder getContentProvider(IApplicationThread iapplicationthread, String s, boolean flag) {
        enforceNotIsolatedCaller("getContentProvider");
        if(iapplicationthread == null) {
            String s1 = (new StringBuilder()).append("null IApplicationThread when getting content provider ").append(s).toString();
            Slog.w("ActivityManager", s1);
            throw new SecurityException(s1);
        } else {
            return getContentProviderImpl(iapplicationthread, s, null, flag);
        }
    }

    public android.app.IActivityManager.ContentProviderHolder getContentProviderExternal(String s, IBinder ibinder) {
        enforceCallingPermission("android.permission.ACCESS_CONTENT_PROVIDERS_EXTERNALLY", "Do not have permission in call getContentProviderExternal()");
        return getContentProviderExternalUnchecked(s, ibinder);
    }

    public UserInfo getCurrentUser() throws RemoteException {
        int i = Binder.getCallingUid();
        UserInfo userinfo;
        if(i != 0 && i != Process.myUid()) {
            Slog.e("ActivityManager", "Trying to get user from unauthorized app");
            userinfo = null;
        } else {
            userinfo = AppGlobals.getPackageManager().getUser(mCurrentUserId);
        }
        return userinfo;
    }

    public ConfigurationInfo getDeviceConfigurationInfo() {
        ConfigurationInfo configurationinfo = new ConfigurationInfo();
        this;
        JVM INSTR monitorenter ;
        configurationinfo.reqTouchScreen = mConfiguration.touchscreen;
        configurationinfo.reqKeyboardType = mConfiguration.keyboard;
        configurationinfo.reqNavigation = mConfiguration.navigation;
        if(mConfiguration.navigation == 2 || mConfiguration.navigation == 3)
            configurationinfo.reqInputFeatures = 2 | configurationinfo.reqInputFeatures;
        if(mConfiguration.keyboard != 0 && mConfiguration.keyboard != 1)
            configurationinfo.reqInputFeatures = 1 | configurationinfo.reqInputFeatures;
        configurationinfo.reqGlEsVersion = GL_ES_VERSION;
        return configurationinfo;
    }

    public int getFrontActivityScreenCompatMode() {
        enforceNotIsolatedCaller("getFrontActivityScreenCompatMode");
        this;
        JVM INSTR monitorenter ;
        int i = mCompatModePackages.getFrontActivityScreenCompatModeLocked();
        return i;
    }

    public IIntentSender getIntentSender(int i, String s, IBinder ibinder, String s1, int j, Intent aintent[], String as[], 
            int k, Bundle bundle) {
        enforceNotIsolatedCaller("getIntentSender");
        if(aintent != null) {
            if(aintent.length < 1)
                throw new IllegalArgumentException("Intents array length must be >= 1");
            for(int j1 = 0; j1 < aintent.length; j1++) {
                Intent intent = aintent[j1];
                if(intent == null)
                    continue;
                if(intent.hasFileDescriptors())
                    throw new IllegalArgumentException("File descriptors passed in Intent");
                if(i == 1 && (0x4000000 & intent.getFlags()) != 0)
                    throw new IllegalArgumentException("Can't use FLAG_RECEIVER_BOOT_UPGRADE here");
                aintent[j1] = new Intent(intent);
            }

            if(as != null && as.length != aintent.length)
                throw new IllegalArgumentException("Intent array length does not match resolvedTypes length");
        }
        if(bundle != null && bundle.hasFileDescriptors())
            throw new IllegalArgumentException("File descriptors passed in options");
        this;
        JVM INSTR monitorenter ;
        int l = Binder.getCallingUid();
        Exception exception;
        if(l != 0 && l != 1000)
            try {
                int i1 = AppGlobals.getPackageManager().getPackageUid(s, UserId.getUserId(l));
                if(!UserId.isSameApp(l, i1)) {
                    String s2 = (new StringBuilder()).append("Permission Denial: getIntentSender() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(", (need uid=").append(i1).append(")").append(" is not allowed to send as package ").append(s).toString();
                    Slog.w("ActivityManager", s2);
                    throw new SecurityException(s2);
                }
            }
            catch(RemoteException remoteexception) {
                throw new SecurityException(remoteexception);
            }
            finally {
                this;
            }
        if(true)
            break MISSING_BLOCK_LABEL_315;
        JVM INSTR monitorexit ;
        throw exception;
        IIntentSender iintentsender = getIntentSenderLocked(i, s, Binder.getOrigCallingUid(), ibinder, s1, j, aintent, as, k, bundle);
        this;
        JVM INSTR monitorexit ;
        return iintentsender;
    }

    IIntentSender getIntentSenderLocked(int i, String s, int j, IBinder ibinder, String s1, int k, Intent aintent[], 
            String as[], int l, Bundle bundle) {
        ActivityRecord activityrecord = null;
        if(i != 3) goto _L2; else goto _L1
_L1:
        activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord != null) goto _L4; else goto _L3
_L3:
        Object obj = null;
_L6:
        return ((IIntentSender) (obj));
_L4:
        if(activityrecord.finishing) {
            obj = null;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        boolean flag;
        boolean flag1;
        boolean flag2;
        PendingIntentRecord.Key key;
        WeakReference weakreference;
        if((0x20000000 & l) != 0)
            flag = true;
        else
            flag = false;
        if((0x10000000 & l) != 0)
            flag1 = true;
        else
            flag1 = false;
        if((0x8000000 & l) != 0)
            flag2 = true;
        else
            flag2 = false;
        key = new PendingIntentRecord.Key(i, s, activityrecord, s1, k, aintent, as, l & 0xc7ffffff, bundle);
        weakreference = (WeakReference)mIntentSenderRecords.get(key);
        if(weakreference != null)
            obj = (PendingIntentRecord)weakreference.get();
        else
            obj = null;
        if(obj != null) {
            if(!flag1) {
                if(flag2) {
                    if(((PendingIntentRecord) (obj)).key.requestIntent != null) {
                        Intent intent = ((PendingIntentRecord) (obj)).key.requestIntent;
                        Intent intent1;
                        if(aintent != null)
                            intent1 = aintent[-1 + aintent.length];
                        else
                            intent1 = null;
                        intent.replaceExtras(intent1);
                    }
                    if(aintent != null) {
                        aintent[-1 + aintent.length] = ((PendingIntentRecord) (obj)).key.requestIntent;
                        ((PendingIntentRecord) (obj)).key.allIntents = aintent;
                        ((PendingIntentRecord) (obj)).key.allResolvedTypes = as;
                    } else {
                        ((PendingIntentRecord) (obj)).key.allIntents = null;
                        ((PendingIntentRecord) (obj)).key.allResolvedTypes = null;
                    }
                }
                continue; /* Loop/switch isn't completed */
            }
            obj.canceled = true;
            mIntentSenderRecords.remove(key);
        }
        if(!flag) {
            obj = new PendingIntentRecord(this, key, j);
            mIntentSenderRecords.put(key, ((PendingIntentRecord) (obj)).ref);
            if(i == 3) {
                if(activityrecord.pendingResults == null)
                    activityrecord.pendingResults = new HashSet();
                activityrecord.pendingResults.add(((PendingIntentRecord) (obj)).ref);
            }
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    public int getLaunchedFromUid(IBinder ibinder) {
        ActivityRecord activityrecord = ActivityRecord.forToken(ibinder);
        int i;
        if(activityrecord == null)
            i = -1;
        else
            i = activityrecord.launchedFromUid;
        return i;
    }

    public void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryinfo) {
        long l = mProcessList.getMemLevel(6);
        long l1 = mProcessList.getMemLevel(ProcessList.HIDDEN_APP_MIN_ADJ);
        memoryinfo.availMem = Process.getFreeMemory();
        memoryinfo.totalMem = Process.getTotalMemory();
        memoryinfo.threshold = l;
        boolean flag;
        if(memoryinfo.availMem < l + (l1 - l) / 2L)
            flag = true;
        else
            flag = false;
        memoryinfo.lowMemory = flag;
        memoryinfo.hiddenAppThreshold = l1;
        memoryinfo.secondaryServerThreshold = mProcessList.getMemLevel(5);
        memoryinfo.visibleAppThreshold = mProcessList.getMemLevel(1);
        memoryinfo.foregroundAppThreshold = mProcessList.getMemLevel(0);
    }

    public void getMyMemoryState(android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo) {
        enforceNotIsolatedCaller("getMyMemoryState");
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord;
        synchronized(mPidsSelfLocked) {
            processrecord = (ProcessRecord)mPidsSelfLocked.get(Binder.getCallingPid());
        }
        fillInProcMemInfo(processrecord, runningappprocessinfo);
        this;
        JVM INSTR monitorexit ;
        return;
        exception1;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean getPackageAskScreenCompat(String s) {
        enforceNotIsolatedCaller("getPackageAskScreenCompat");
        this;
        JVM INSTR monitorenter ;
        boolean flag = mCompatModePackages.getPackageAskCompatModeLocked(s);
        return flag;
    }

    public String getPackageForIntentSender(IIntentSender iintentsender) {
        String s = null;
        if(iintentsender instanceof PendingIntentRecord)
            try {
                s = ((PendingIntentRecord)iintentsender).key.packageName;
            }
            catch(ClassCastException classcastexception) { }
        return s;
    }

    public String getPackageForToken(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = mMainStack.isInStackLocked(ibinder);
        String s;
        if(activityrecord == null)
            s = null;
        else
            s = activityrecord.packageName;
        return s;
    }

    public int getPackageScreenCompatMode(String s) {
        enforceNotIsolatedCaller("getPackageScreenCompatMode");
        this;
        JVM INSTR monitorenter ;
        int i = mCompatModePackages.getPackageScreenCompatModeLocked(s);
        return i;
    }

    public int getProcessLimit() {
        this;
        JVM INSTR monitorenter ;
        int i = mProcessLimitOverride;
        return i;
    }

    public android.os.Debug.MemoryInfo[] getProcessMemoryInfo(int ai[]) throws RemoteException {
        enforceNotIsolatedCaller("getProcessMemoryInfo");
        android.os.Debug.MemoryInfo amemoryinfo[] = new android.os.Debug.MemoryInfo[ai.length];
        for(int i = -1 + ai.length; i >= 0; i--) {
            amemoryinfo[i] = new android.os.Debug.MemoryInfo();
            Debug.getMemoryInfo(ai[i], amemoryinfo[i]);
        }

        return amemoryinfo;
    }

    public long[] getProcessPss(int ai[]) throws RemoteException {
        enforceNotIsolatedCaller("getProcessPss");
        long al[] = new long[ai.length];
        for(int i = -1 + ai.length; i >= 0; i--)
            al[i] = Debug.getPss(ai[i]);

        return al;
    }

    final ProcessRecord getProcessRecordLocked(String s, int i) {
        if(i != 1000) goto _L2; else goto _L1
_L1:
        SparseArray sparsearray = (SparseArray)mProcessNames.getMap().get(s);
        if(sparsearray != null) goto _L4; else goto _L3
_L3:
        ProcessRecord processrecord = null;
_L6:
        return processrecord;
_L4:
        int j = sparsearray.size();
        int k = 0;
        do {
            if(k >= j)
                break;
            if(UserId.isSameUser(sparsearray.keyAt(k), i)) {
                processrecord = (ProcessRecord)sparsearray.valueAt(k);
                continue; /* Loop/switch isn't completed */
            }
            k++;
        } while(true);
_L2:
        processrecord = (ProcessRecord)mProcessNames.get(s, i);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public List getProcessesInErrorState() {
        enforceNotIsolatedCaller("getProcessesInErrorState");
        this;
        JVM INSTR monitorenter ;
        int i = mLruProcesses.size();
        int j;
        ArrayList arraylist;
        j = i - 1;
        arraylist = null;
_L9:
        if(j < 0) goto _L2; else goto _L1
_L1:
        ProcessRecord processrecord;
        android.app.ActivityManager.ProcessErrorStateInfo processerrorstateinfo;
        processrecord = (ProcessRecord)mLruProcesses.get(j);
        if(processrecord.thread == null || !processrecord.crashing && !processrecord.notResponding)
            break MISSING_BLOCK_LABEL_226;
        processerrorstateinfo = null;
        if(!processrecord.crashing) goto _L4; else goto _L3
_L3:
        processerrorstateinfo = processrecord.crashingReport;
_L11:
        if(processerrorstateinfo == null) goto _L6; else goto _L5
_L5:
        if(arraylist != null) goto _L8; else goto _L7
_L7:
        ArrayList arraylist1 = new ArrayList(1);
_L13:
        arraylist1.add(processerrorstateinfo);
_L14:
        j--;
        arraylist = arraylist1;
          goto _L9
_L4:
        if(!processrecord.notResponding) goto _L11; else goto _L10
_L10:
        processerrorstateinfo = processrecord.notRespondingReport;
          goto _L11
_L6:
        Slog.w("ActivityManager", (new StringBuilder()).append("Missing app error report, app = ").append(processrecord.processName).append(" crashing = ").append(processrecord.crashing).append(" notResponding = ").append(processrecord.notResponding).toString());
        break MISSING_BLOCK_LABEL_226;
_L2:
        return arraylist;
        Exception exception;
        exception;
_L12:
        this;
        JVM INSTR monitorexit ;
        throw exception;
        exception;
        arraylist;
        if(true) goto _L12; else goto _L8
_L8:
        arraylist1 = arraylist;
          goto _L13
        arraylist1 = arraylist;
          goto _L14
    }

    public String getProviderMimeType(Uri uri) {
        String s;
        long l;
        android.app.IActivityManager.ContentProviderHolder contentproviderholder;
        enforceNotIsolatedCaller("getProviderMimeType");
        s = uri.getAuthority();
        l = Binder.clearCallingIdentity();
        contentproviderholder = null;
        contentproviderholder = getContentProviderExternalUnchecked(s, null);
        if(contentproviderholder == null) goto _L2; else goto _L1
_L1:
        String s2 = contentproviderholder.provider.getType(uri);
        String s1;
        s1 = s2;
        if(contentproviderholder != null)
            removeContentProviderExternalUnchecked(s, null);
        Binder.restoreCallingIdentity(l);
_L4:
        return s1;
        RemoteException remoteexception;
        remoteexception;
        Log.w("ActivityManager", (new StringBuilder()).append("Content provider dead retrieving ").append(uri).toString(), remoteexception);
        if(contentproviderholder != null)
            removeContentProviderExternalUnchecked(s, null);
        Binder.restoreCallingIdentity(l);
        s1 = null;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(contentproviderholder != null)
            removeContentProviderExternalUnchecked(s, null);
        Binder.restoreCallingIdentity(l);
        throw exception;
_L2:
        if(contentproviderholder != null)
            removeContentProviderExternalUnchecked(s, null);
        Binder.restoreCallingIdentity(l);
        s1 = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public List getRecentTasks(int i, int j) {
        boolean flag;
        int l;
        IPackageManager ipackagemanager;
        int i1;
        int j1;
        ArrayList arraylist;
        int k1;
        TaskRecord taskrecord;
        flag = false;
        int k = Binder.getCallingUid();
        int l1;
        if(k == 1000)
            l = mCurrentUserId;
        else
            l = UserId.getUserId(k);
        this;
        JVM INSTR monitorenter ;
        enforceCallingPermission("android.permission.GET_TASKS", "getRecentTasks()");
        if(checkCallingPermission("android.permission.GET_DETAILED_TASKS") == 0)
            flag = true;
        ipackagemanager = AppGlobals.getPackageManager();
        i1 = mRecentTasks.size();
        if(i >= i1) goto _L2; else goto _L1
_L1:
        j1 = i;
_L8:
        arraylist = new ArrayList(j1);
        k1 = 0;
_L7:
        if(k1 >= i1 || i <= 0) goto _L4; else goto _L3
_L3:
        taskrecord = (TaskRecord)mRecentTasks.get(k1);
        l1 = taskrecord.userId;
        if(l1 == l) goto _L6; else goto _L5
_L5:
        k1++;
          goto _L7
_L2:
        j1 = i1;
          goto _L8
_L6:
        if(k1 == 0 || (j & 1) != 0) goto _L10; else goto _L9
_L9:
        if(taskrecord.intent != null && (0x800000 & taskrecord.intent.getFlags()) != 0) goto _L5; else goto _L10
_L10:
        android.app.ActivityManager.RecentTaskInfo recenttaskinfo;
        int i2;
        recenttaskinfo = new android.app.ActivityManager.RecentTaskInfo();
        if(taskrecord.numActivities <= 0)
            break MISSING_BLOCK_LABEL_396;
        i2 = taskrecord.taskId;
_L19:
        recenttaskinfo.id = i2;
        recenttaskinfo.persistentId = taskrecord.taskId;
        if(taskrecord.intent == null) goto _L12; else goto _L11
_L11:
        Intent intent = taskrecord.intent;
_L17:
        recenttaskinfo.baseIntent = new Intent(intent);
        if(!flag)
            recenttaskinfo.baseIntent.replaceExtras((Bundle)null);
        recenttaskinfo.origActivity = taskrecord.origActivity;
        recenttaskinfo.description = ((ThumbnailHolder) (taskrecord)).lastDescription;
        if((j & 2) == 0) goto _L14; else goto _L13
_L13:
        if(recenttaskinfo.origActivity == null) goto _L16; else goto _L15
_L15:
        ActivityInfo activityinfo = ipackagemanager.getActivityInfo(recenttaskinfo.origActivity, 0, l);
        if(activityinfo == null) goto _L5; else goto _L14
_L14:
        arraylist.add(recenttaskinfo);
        i--;
          goto _L5
_L12:
        intent = taskrecord.affinityIntent;
          goto _L17
_L16:
        if(recenttaskinfo.baseIntent == null) goto _L14; else goto _L18
_L18:
        List list = ipackagemanager.queryIntentActivities(recenttaskinfo.baseIntent, null, 0, l);
        if(list != null) goto _L14; else goto _L5
_L4:
        this;
        JVM INSTR monitorexit ;
        return arraylist;
        Exception exception;
        exception;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
          goto _L14
        i2 = -1;
          goto _L19
    }

    final ProcessRecord getRecordForAppLocked(IApplicationThread iapplicationthread) {
        ProcessRecord processrecord = null;
        if(iapplicationthread != null) goto _L2; else goto _L1
_L1:
        return processrecord;
_L2:
        int i = getLRURecordIndexForAppLocked(iapplicationthread);
        if(i >= 0)
            processrecord = (ProcessRecord)mLruProcesses.get(i);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public int getRequestedOrientation(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = mMainStack.isInStackLocked(ibinder);
        int i;
        if(activityrecord == null)
            i = -1;
        else
            i = mWindowManager.getAppOrientation(activityrecord.appToken);
        return i;
    }

    public List getRunningAppProcesses() {
        enforceNotIsolatedCaller("getRunningAppProcesses");
        this;
        JVM INSTR monitorenter ;
        int i = mLruProcesses.size();
        int j;
        ArrayList arraylist;
        j = i - 1;
        arraylist = null;
_L7:
        if(j < 0) goto _L2; else goto _L1
_L1:
        ProcessRecord processrecord;
        android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo;
        processrecord = (ProcessRecord)mLruProcesses.get(j);
        if(processrecord.thread == null || processrecord.crashing || processrecord.notResponding)
            break MISSING_BLOCK_LABEL_265;
        runningappprocessinfo = new android.app.ActivityManager.RunningAppProcessInfo(processrecord.processName, processrecord.pid, processrecord.getPackageList());
        fillInProcMemInfo(processrecord, runningappprocessinfo);
        if(!(processrecord.adjSource instanceof ProcessRecord)) goto _L4; else goto _L3
_L3:
        runningappprocessinfo.importanceReasonPid = ((ProcessRecord)processrecord.adjSource).pid;
        runningappprocessinfo.importanceReasonImportance = oomAdjToImportance(processrecord.adjSourceOom, null);
_L9:
        if(processrecord.adjTarget instanceof ComponentName)
            runningappprocessinfo.importanceReasonComponent = (ComponentName)processrecord.adjTarget;
        if(arraylist != null) goto _L6; else goto _L5
_L5:
        ArrayList arraylist1 = new ArrayList();
_L11:
        arraylist1.add(runningappprocessinfo);
_L12:
        j--;
        arraylist = arraylist1;
          goto _L7
_L4:
        if(!(processrecord.adjSource instanceof ActivityRecord)) goto _L9; else goto _L8
_L8:
        ActivityRecord activityrecord = (ActivityRecord)processrecord.adjSource;
        if(activityrecord.app != null)
            runningappprocessinfo.importanceReasonPid = activityrecord.app.pid;
          goto _L9
        Exception exception;
        exception;
        arraylist;
_L10:
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        this;
        JVM INSTR monitorexit ;
        return arraylist;
        exception;
        if(true) goto _L10; else goto _L6
_L6:
        arraylist1 = arraylist;
          goto _L11
        arraylist1 = arraylist;
          goto _L12
    }

    public List getRunningExternalApplications() {
        enforceNotIsolatedCaller("getRunningExternalApplications");
        List list = getRunningAppProcesses();
        ArrayList arraylist = new ArrayList();
        if(list != null && list.size() > 0) {
            HashSet hashset = new HashSet();
            Iterator iterator = list.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo = (android.app.ActivityManager.RunningAppProcessInfo)iterator.next();
                if(runningappprocessinfo.pkgList != null) {
                    String as[] = runningappprocessinfo.pkgList;
                    int i = as.length;
                    int j = 0;
                    while(j < i)  {
                        hashset.add(as[j]);
                        j++;
                    }
                }
            } while(true);
            IPackageManager ipackagemanager = AppGlobals.getPackageManager();
            Iterator iterator1 = hashset.iterator();
            do {
                if(!iterator1.hasNext())
                    break;
                String s = (String)iterator1.next();
                try {
                    ApplicationInfo applicationinfo = ipackagemanager.getApplicationInfo(s, 0, UserId.getCallingUserId());
                    if((0x40000 & applicationinfo.flags) != 0)
                        arraylist.add(applicationinfo);
                }
                catch(RemoteException remoteexception) { }
            } while(true);
        }
        return arraylist;
    }

    public PendingIntent getRunningServiceControlPanel(ComponentName componentname) {
        enforceNotIsolatedCaller("getRunningServiceControlPanel");
        this;
        JVM INSTR monitorenter ;
        ServiceRecord servicerecord;
        int i = UserId.getUserId(Binder.getCallingUid());
        servicerecord = mServiceMap.getServiceByName(componentname, i);
        if(servicerecord == null) goto _L2; else goto _L1
_L1:
        Iterator iterator = servicerecord.connections.values().iterator();
_L10:
        if(!iterator.hasNext()) goto _L2; else goto _L3
_L3:
        ArrayList arraylist;
        int j;
        arraylist = (ArrayList)iterator.next();
        j = 0;
_L8:
        if(j >= arraylist.size())
            continue; /* Loop/switch isn't completed */
        if(((ConnectionRecord)arraylist.get(j)).clientIntent == null) goto _L5; else goto _L4
_L4:
        PendingIntent pendingintent = ((ConnectionRecord)arraylist.get(j)).clientIntent;
          goto _L6
_L2:
        this;
        JVM INSTR monitorexit ;
        pendingintent = null;
_L6:
        return pendingintent;
_L5:
        j++;
        if(true) goto _L8; else goto _L7
_L7:
        if(true) goto _L10; else goto _L9
_L9:
    }

    public List getServices(int i, int j) {
        enforceNotIsolatedCaller("getServices");
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        arraylist = new ArrayList();
        int k = UserId.getUserId(Binder.getCallingUid());
        if(mServiceMap.getAllServices(k).size() > 0) {
            for(Iterator iterator = mServiceMap.getAllServices(k).iterator(); iterator.hasNext() && arraylist.size() < i; arraylist.add(makeRunningServiceInfoLocked((ServiceRecord)iterator.next())));
        }
        break MISSING_BLOCK_LABEL_105;
        Exception exception;
        exception;
        throw exception;
        int l = 0;
        while(l < mRestartingServices.size() && arraylist.size() < i)  {
            ServiceRecord servicerecord = (ServiceRecord)mRestartingServices.get(l);
            android.app.ActivityManager.RunningServiceInfo runningserviceinfo = makeRunningServiceInfoLocked(servicerecord);
            runningserviceinfo.restarting = servicerecord.nextRestartTime;
            arraylist.add(runningserviceinfo);
            l++;
        }
        this;
        JVM INSTR monitorexit ;
        return arraylist;
    }

    public int getTaskForActivity(IBinder ibinder, boolean flag) {
        this;
        JVM INSTR monitorenter ;
        int i = getTaskForActivityLocked(ibinder, flag);
        return i;
    }

    int getTaskForActivityLocked(IBinder ibinder, boolean flag) {
        int i = -1;
        int j = mMainStack.mHistory.size();
        TaskRecord taskrecord = null;
        int k = 0;
        do {
            ActivityRecord activityrecord;
label0:
            {
                if(k < j) {
                    activityrecord = (ActivityRecord)mMainStack.mHistory.get(k);
                    if(activityrecord.appToken != ibinder)
                        break label0;
                    if(!flag || taskrecord != activityrecord.task)
                        i = activityrecord.task.taskId;
                }
                return i;
            }
            taskrecord = activityrecord.task;
            k++;
        } while(true);
    }

    public android.app.ActivityManager.TaskThumbnails getTaskThumbnails(int i) {
        this;
        JVM INSTR monitorenter ;
        enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "getTaskThumbnails()");
        TaskRecord taskrecord = taskForIdLocked(i);
        android.app.ActivityManager.TaskThumbnails taskthumbnails;
        if(taskrecord != null)
            taskthumbnails = mMainStack.getTaskThumbnailsLocked(taskrecord);
        else
            taskthumbnails = null;
        return taskthumbnails;
    }

    public List getTasks(int i, int j, IThumbnailReceiver ithumbnailreceiver) {
        ArrayList arraylist;
        IApplicationThread iapplicationthread;
        arraylist = new ArrayList();
        iapplicationthread = null;
        ActivityRecord activityrecord = null;
        this;
        JVM INSTR monitorenter ;
        int k = checkCallingPermission("android.permission.GET_TASKS");
        if(k == 0) goto _L2; else goto _L1
_L1:
        if(ithumbnailreceiver == null)
            break MISSING_BLOCK_LABEL_41;
        Exception exception;
        int l;
        ActivityRecord activityrecord1;
        ActivityRecord activityrecord2;
        TaskRecord taskrecord;
        int i1;
        int j1;
        PendingThumbnailsRecord pendingthumbnailsrecord;
        ActivityRecord activityrecord3;
        android.app.ActivityManager.RunningTaskInfo runningtaskinfo;
        PendingThumbnailsRecord pendingthumbnailsrecord1;
        ActivityRecord activityrecord4;
        String s;
        try {
            ithumbnailreceiver.finished();
        }
        catch(RemoteException remoteexception1) { }
        s = (new StringBuilder()).append("Permission Denial: getTasks() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.GET_TASKS").toString();
        Slog.w("ActivityManager", s);
        throw new SecurityException(s);
_L23:
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        l = -1 + mMainStack.mHistory.size();
        if(l < 0) goto _L4; else goto _L3
_L3:
        activityrecord4 = (ActivityRecord)mMainStack.mHistory.get(l);
        activityrecord1 = activityrecord4;
_L18:
        activityrecord2 = null;
        taskrecord = null;
        i1 = 0;
        j1 = 0;
        pendingthumbnailsrecord = null;
_L17:
        if(l < 0 || i <= 0) goto _L6; else goto _L5
_L5:
        activityrecord3 = activityrecord1;
        if(--l < 0) goto _L8; else goto _L7
_L7:
        activityrecord1 = (ActivityRecord)mMainStack.mHistory.get(l);
_L19:
        if(activityrecord2 == null || activityrecord2.state == ActivityStack.ActivityState.INITIALIZING && activityrecord2.task == activityrecord3.task) {
            activityrecord2 = activityrecord3;
            taskrecord = activityrecord3.task;
            j1 = 0;
            i1 = 0;
        }
        i1++;
        if(activityrecord3.app != null && activityrecord3.app.thread != null)
            j1++;
        if(activityrecord1 != null && activityrecord1.task == taskrecord)
            break MISSING_BLOCK_LABEL_624;
        runningtaskinfo = new android.app.ActivityManager.RunningTaskInfo();
        runningtaskinfo.id = taskrecord.taskId;
        runningtaskinfo.baseActivity = activityrecord3.intent.getComponent();
        runningtaskinfo.topActivity = activityrecord2.intent.getComponent();
        if(activityrecord2.thumbHolder != null)
            runningtaskinfo.description = activityrecord2.thumbHolder.lastDescription;
        runningtaskinfo.numActivities = i1;
        runningtaskinfo.numRunning = j1;
        if(runningtaskinfo.thumbnail != null || ithumbnailreceiver == null) goto _L10; else goto _L9
_L9:
        if(activityrecord2.state != ActivityStack.ActivityState.RESUMED && activityrecord2.state != ActivityStack.ActivityState.PAUSING) goto _L12; else goto _L11
_L11:
        if(!activityrecord2.idle || activityrecord2.app == null || activityrecord2.app.thread == null) goto _L14; else goto _L13
_L13:
        activityrecord = activityrecord2;
        iapplicationthread = activityrecord2.app.thread;
_L12:
        if(pendingthumbnailsrecord != null) goto _L16; else goto _L15
_L15:
        pendingthumbnailsrecord1 = new PendingThumbnailsRecord(ithumbnailreceiver);
_L20:
        pendingthumbnailsrecord1.pendingRecords.add(activityrecord2);
_L21:
        arraylist.add(runningtaskinfo);
        i--;
        activityrecord2 = null;
_L22:
        pendingthumbnailsrecord = pendingthumbnailsrecord1;
          goto _L17
_L4:
        activityrecord1 = null;
          goto _L18
_L8:
        activityrecord1 = null;
          goto _L19
_L14:
        activityrecord2.thumbnailNeeded = true;
          goto _L12
_L6:
        if(pendingthumbnailsrecord != null)
            mPendingThumbnails.add(pendingthumbnailsrecord);
        this;
        JVM INSTR monitorexit ;
        if(iapplicationthread != null)
            try {
                android.view.IApplicationToken.Stub stub = activityrecord.appToken;
                iapplicationthread.requestThumbnail(stub);
            }
            catch(Exception exception1) {
                Slog.w("ActivityManager", "Exception thrown when requesting thumbnail", exception1);
                sendPendingThumbnail(null, activityrecord.appToken, null, null, true);
            }
        if(pendingthumbnailsrecord == null && ithumbnailreceiver != null)
            try {
                ithumbnailreceiver.finished();
            }
            catch(RemoteException remoteexception) { }
        return arraylist;
_L16:
        pendingthumbnailsrecord1 = pendingthumbnailsrecord;
          goto _L20
_L10:
        pendingthumbnailsrecord1 = pendingthumbnailsrecord;
          goto _L21
        pendingthumbnailsrecord1 = pendingthumbnailsrecord;
          goto _L22
        exception;
          goto _L23
        exception;
        pendingthumbnailsrecord;
          goto _L23
    }

    public int getUidForIntentSender(IIntentSender iintentsender) {
        if(!(iintentsender instanceof PendingIntentRecord)) goto _L2; else goto _L1
_L1:
        int i = ((PendingIntentRecord)iintentsender).uid;
_L4:
        return i;
        ClassCastException classcastexception;
        classcastexception;
_L2:
        i = -1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void goingToSleep() {
        if(checkCallingPermission("android.permission.DEVICE_POWER") != 0)
            throw new SecurityException("Requires permission android.permission.DEVICE_POWER");
        this;
        JVM INSTR monitorenter ;
        mWentToSleep = true;
        updateEventDispatchingLocked();
        if(!mSleeping) {
            mSleeping = true;
            mMainStack.stopIfSleepingLocked();
            checkExcessivePowerUsageLocked(false);
            mHandler.removeMessages(27);
            Message message = mHandler.obtainMessage(27);
            mHandler.sendMessageDelayed(message, 0xdbba0L);
        }
        return;
    }

    public void grantUriPermission(IApplicationThread iapplicationthread, String s, Uri uri, int i) {
        enforceNotIsolatedCaller("grantUriPermission");
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord;
        processrecord = getRecordForAppLocked(iapplicationthread);
        if(processrecord == null)
            throw new SecurityException((new StringBuilder()).append("Unable to find app for caller ").append(iapplicationthread).append(" when granting permission to uri ").append(uri).toString());
        break MISSING_BLOCK_LABEL_66;
        Exception exception;
        exception;
        throw exception;
        if(s != null)
            break MISSING_BLOCK_LABEL_81;
        throw new IllegalArgumentException("null target");
        if(uri == null)
            throw new IllegalArgumentException("null uri");
        grantUriPermissionLocked(processrecord.uid, s, uri, i, null);
        this;
        JVM INSTR monitorexit ;
    }

    void grantUriPermissionFromIntentLocked(int i, String s, Intent intent, UriPermissionOwner uripermissionowner) {
        int j;
        NeededUriGrants neededurigrants;
        if(intent != null)
            j = intent.getFlags();
        else
            j = 0;
        neededurigrants = checkGrantUriPermissionFromIntentLocked(i, s, intent, j, null);
        if(neededurigrants != null)
            grantUriPermissionUncheckedFromIntentLocked(neededurigrants, uripermissionowner);
    }

    public void grantUriPermissionFromOwner(IBinder ibinder, int i, String s, Uri uri, int j) {
        this;
        JVM INSTR monitorenter ;
        UriPermissionOwner uripermissionowner;
        uripermissionowner = UriPermissionOwner.fromExternalToken(ibinder);
        if(uripermissionowner == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown owner: ").append(ibinder).toString());
        break MISSING_BLOCK_LABEL_48;
        Exception exception;
        exception;
        throw exception;
        if(i != Binder.getCallingUid() && Binder.getCallingUid() != Process.myUid())
            throw new SecurityException("nice try");
        if(s == null)
            throw new IllegalArgumentException("null target");
        if(uri == null)
            throw new IllegalArgumentException("null uri");
        grantUriPermissionLocked(i, s, uri, j, uripermissionowner);
        this;
        JVM INSTR monitorexit ;
    }

    void grantUriPermissionLocked(int i, String s, Uri uri, int j, UriPermissionOwner uripermissionowner) {
        if(s == null)
            throw new NullPointerException("targetPkg");
        int k = checkGrantUriPermissionLocked(i, s, uri, j, -1);
        if(k >= 0)
            grantUriPermissionUncheckedLocked(k, s, uri, j, uripermissionowner);
    }

    void grantUriPermissionUncheckedFromIntentLocked(NeededUriGrants neededurigrants, UriPermissionOwner uripermissionowner) {
        if(neededurigrants != null) {
            for(int i = 0; i < neededurigrants.size(); i++)
                grantUriPermissionUncheckedLocked(neededurigrants.targetUid, neededurigrants.targetPkg, (Uri)neededurigrants.get(i), neededurigrants.flags, uripermissionowner);

        }
    }

    void grantUriPermissionUncheckedLocked(int i, String s, Uri uri, int j, UriPermissionOwner uripermissionowner) {
        int k = j & 3;
        if(k != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        HashMap hashmap = (HashMap)mGrantedUriPermissions.get(i);
        if(hashmap == null) {
            hashmap = new HashMap();
            mGrantedUriPermissions.put(i, hashmap);
        }
        UriPermission uripermission = (UriPermission)hashmap.get(uri);
        if(uripermission == null) {
            uripermission = new UriPermission(i, uri);
            hashmap.put(uri, uripermission);
        }
        uripermission.modeFlags = k | uripermission.modeFlags;
        if(uripermissionowner == null) {
            uripermission.globalModeFlags = k | uripermission.globalModeFlags;
        } else {
            if((k & 1) != 0) {
                uripermission.readOwners.add(uripermissionowner);
                uripermissionowner.addReadPermission(uripermission);
            }
            if((k & 2) != 0) {
                uripermission.writeOwners.add(uripermissionowner);
                uripermissionowner.addWritePermission(uripermission);
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void handleApplicationCrash(IBinder ibinder, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        ProcessRecord processrecord = findAppProcess(ibinder, "Crash");
        String s;
        Object aobj[];
        int i;
        if(ibinder == null)
            s = "system_server";
        else
        if(processrecord == null)
            s = "unknown";
        else
            s = processrecord.processName;
        aobj = new Object[7];
        aobj[0] = Integer.valueOf(Binder.getCallingPid());
        aobj[1] = s;
        if(processrecord == null)
            i = -1;
        else
            i = processrecord.info.flags;
        aobj[2] = Integer.valueOf(i);
        aobj[3] = crashinfo.exceptionClassName;
        aobj[4] = crashinfo.exceptionMessage;
        aobj[5] = crashinfo.throwFileName;
        aobj[6] = Integer.valueOf(crashinfo.throwLineNumber);
        EventLog.writeEvent(30039, aobj);
        addErrorToDropBox("crash", processrecord, s, null, null, null, null, null, crashinfo);
        crashApplication(processrecord, crashinfo);
    }

    public void handleApplicationStrictModeViolation(IBinder ibinder, int i, android.os.StrictMode.ViolationInfo violationinfo) {
        ProcessRecord processrecord = findAppProcess(ibinder, "StrictMode");
        if(processrecord != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if((i & 0x80) == 0) goto _L4; else goto _L3
_L3:
        Integer integer;
        boolean flag;
        integer = Integer.valueOf(violationinfo.hashCode());
        flag = true;
        HashSet hashset = mAlreadyLoggedViolatedStacks;
        hashset;
        JVM INSTR monitorenter ;
        if(!mAlreadyLoggedViolatedStacks.contains(integer))
            break MISSING_BLOCK_LABEL_225;
        flag = false;
_L6:
        if(flag)
            logStrictModeViolationToDropBox(processrecord, violationinfo);
_L4:
        if((i & 0x20) == 0)
            continue; /* Loop/switch isn't completed */
        AppErrorResult apperrorresult = new AppErrorResult();
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        Message message = Message.obtain();
        message.what = 26;
        HashMap hashmap = new HashMap();
        hashmap.put("result", apperrorresult);
        hashmap.put("app", processrecord);
        hashmap.put("violationMask", Integer.valueOf(i));
        hashmap.put("info", violationinfo);
        message.obj = hashmap;
        mHandler.sendMessage(message);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        int j = apperrorresult.get();
        Slog.w("ActivityManager", (new StringBuilder()).append("handleApplicationStrictModeViolation; res=").append(j).toString());
        continue; /* Loop/switch isn't completed */
        if(mAlreadyLoggedViolatedStacks.size() >= 5000)
            mAlreadyLoggedViolatedStacks.clear();
        mAlreadyLoggedViolatedStacks.add(integer);
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L1; else goto _L7
_L7:
    }

    public boolean handleApplicationWtf(IBinder ibinder, String s, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        ProcessRecord processrecord = findAppProcess(ibinder, "WTF");
        String s1;
        Object aobj[];
        int i;
        boolean flag;
        if(ibinder == null)
            s1 = "system_server";
        else
        if(processrecord == null)
            s1 = "unknown";
        else
            s1 = processrecord.processName;
        aobj = new Object[5];
        aobj[0] = Integer.valueOf(Binder.getCallingPid());
        aobj[1] = s1;
        if(processrecord == null)
            i = -1;
        else
            i = processrecord.info.flags;
        aobj[2] = Integer.valueOf(i);
        aobj[3] = s;
        aobj[4] = crashinfo.exceptionMessage;
        EventLog.writeEvent(30040, aobj);
        addErrorToDropBox("wtf", processrecord, s1, null, null, s, null, null, crashinfo);
        if(processrecord != null && processrecord.pid != Process.myPid() && android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "wtf_is_fatal", 0) != 0) {
            crashApplication(processrecord, crashinfo);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    ContentProviderConnection incProviderCountLocked(ProcessRecord processrecord, ContentProviderRecord contentproviderrecord, IBinder ibinder, boolean flag) {
        int i;
        if(processrecord == null)
            break MISSING_BLOCK_LABEL_173;
        i = 0;
_L5:
        if(i >= processrecord.conProviders.size()) goto _L2; else goto _L1
_L1:
        ContentProviderConnection contentproviderconnection = (ContentProviderConnection)processrecord.conProviders.get(i);
        if(contentproviderconnection.provider != contentproviderrecord) goto _L4; else goto _L3
_L3:
        if(flag) {
            contentproviderconnection.stableCount = 1 + contentproviderconnection.stableCount;
            contentproviderconnection.numStableIncs = 1 + contentproviderconnection.numStableIncs;
        } else {
            contentproviderconnection.unstableCount = 1 + contentproviderconnection.unstableCount;
            contentproviderconnection.numUnstableIncs = 1 + contentproviderconnection.numUnstableIncs;
        }
_L6:
        return contentproviderconnection;
_L4:
        i++;
          goto _L5
_L2:
        contentproviderconnection = new ContentProviderConnection(contentproviderrecord, processrecord);
        if(flag) {
            contentproviderconnection.stableCount = 1;
            contentproviderconnection.numStableIncs = 1;
        } else {
            contentproviderconnection.unstableCount = 1;
            contentproviderconnection.numUnstableIncs = 1;
        }
        contentproviderrecord.connections.add(contentproviderconnection);
        processrecord.conProviders.add(contentproviderconnection);
          goto _L6
        contentproviderrecord.addExternalProcessHandleLocked(ibinder);
        contentproviderconnection = null;
          goto _L6
    }

    boolean isAllowedWhileBooting(ApplicationInfo applicationinfo) {
        boolean flag;
        if((8 & applicationinfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isImmersive(IBinder ibinder) {
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord;
        activityrecord = mMainStack.isInStackLocked(ibinder);
        if(activityrecord == null)
            throw new IllegalArgumentException();
        break MISSING_BLOCK_LABEL_28;
        Exception exception;
        exception;
        throw exception;
        boolean flag = activityrecord.immersive;
        this;
        JVM INSTR monitorexit ;
        return flag;
    }

    public boolean isIntentSenderAnActivity(IIntentSender iintentsender) {
        boolean flag = false;
        if(iintentsender instanceof PendingIntentRecord) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        int i = ((PendingIntentRecord)iintentsender).key.type;
        if(i == 2)
            flag = true;
        continue; /* Loop/switch isn't completed */
        ClassCastException classcastexception;
        classcastexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean isIntentSenderTargetedToPackage(IIntentSender iintentsender) {
        boolean flag = false;
        if(iintentsender instanceof PendingIntentRecord) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        PendingIntentRecord pendingintentrecord;
        int i;
        pendingintentrecord = (PendingIntentRecord)iintentsender;
        if(pendingintentrecord.key.allIntents == null)
            continue; /* Loop/switch isn't completed */
        i = 0;
_L3:
        ComponentName componentname;
        if(i >= pendingintentrecord.key.allIntents.length)
            break MISSING_BLOCK_LABEL_84;
        Intent intent = pendingintentrecord.key.allIntents[i];
        if(intent.getPackage() == null)
            break MISSING_BLOCK_LABEL_78;
        componentname = intent.getComponent();
        if(componentname != null)
            continue; /* Loop/switch isn't completed */
        i++;
          goto _L3
        flag = true;
        continue; /* Loop/switch isn't completed */
        ClassCastException classcastexception;
        classcastexception;
        if(true) goto _L1; else goto _L4
_L4:
    }

    boolean isNextTransitionForward() {
        int i = mWindowManager.getPendingAppTransition();
        boolean flag;
        if(i == 4102 || i == 4104 || i == 4106)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isPendingBroadcastProcessLocked(int i) {
        boolean flag;
        if(mFgBroadcastQueue.isPendingBroadcastProcessLocked(i) || mBgBroadcastQueue.isPendingBroadcastProcessLocked(i))
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isSingleton(String s, ApplicationInfo applicationinfo) {
        boolean flag = false;
        if(UserId.getAppId(applicationinfo.uid) < 10000) goto _L2; else goto _L1
_L1:
        flag = false;
_L4:
        return flag;
_L2:
        if(s == applicationinfo.packageName) {
            if((8 & applicationinfo.flags) != 0)
                flag = true;
            else
                flag = false;
        } else
        if("system".equals(s))
            flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean isSleeping() {
        boolean flag;
        if(mSleeping || mShuttingDown)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isTopActivityImmersive() {
        enforceNotIsolatedCaller("startActivity");
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = mMainStack.topRunningActivityLocked(null);
        boolean flag;
        if(activityrecord != null)
            flag = activityrecord.immersive;
        else
            flag = false;
        return flag;
    }

    public boolean isUserAMonkey() {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        if(mController != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void killAllBackgroundProcesses() {
        if(checkCallingPermission("android.permission.KILL_BACKGROUND_PROCESSES") != 0) {
            String s = (new StringBuilder()).append("Permission Denial: killAllBackgroundProcesses() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.KILL_BACKGROUND_PROCESSES").toString();
            Slog.w("ActivityManager", s);
            throw new SecurityException(s);
        }
        long l = Binder.clearCallingIdentity();
        this;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        Iterator iterator;
        arraylist = new ArrayList();
        iterator = mProcessNames.getMap().values().iterator();
_L6:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        SparseArray sparsearray;
        int k;
        int i1;
        sparsearray = (SparseArray)iterator.next();
        k = sparsearray.size();
        i1 = 0;
_L4:
        ProcessRecord processrecord;
        if(i1 >= k)
            continue; /* Loop/switch isn't completed */
        processrecord = (ProcessRecord)sparsearray.valueAt(i1);
        if(processrecord.persistent)
            break MISSING_BLOCK_LABEL_281;
        if(processrecord.removed) {
            arraylist.add(processrecord);
            break MISSING_BLOCK_LABEL_281;
        }
        break MISSING_BLOCK_LABEL_203;
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        if(processrecord.setAdj >= ProcessList.HIDDEN_APP_MIN_ADJ) {
            processrecord.removed = true;
            arraylist.add(processrecord);
        }
        break MISSING_BLOCK_LABEL_281;
_L2:
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
            removeProcessLocked((ProcessRecord)arraylist.get(j), false, true, "kill all background");

        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
        i1++;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L6; else goto _L5
_L5:
    }

    void killAppAtUsersRequest(ProcessRecord processrecord, Dialog dialog) {
        this;
        JVM INSTR monitorenter ;
        processrecord.crashing = false;
        processrecord.crashingReport = null;
        processrecord.notResponding = false;
        processrecord.notRespondingReport = null;
        if(processrecord.anrDialog == dialog)
            processrecord.anrDialog = null;
        if(processrecord.waitDialog == dialog)
            processrecord.waitDialog = null;
        if(processrecord.pid > 0 && processrecord.pid != MY_PID) {
            handleAppCrashLocked(processrecord);
            Slog.i("ActivityManager", (new StringBuilder()).append("Killing ").append(processrecord).append(": user's request").toString());
            Object aobj[] = new Object[4];
            aobj[0] = Integer.valueOf(processrecord.pid);
            aobj[1] = processrecord.processName;
            aobj[2] = Integer.valueOf(processrecord.setAdj);
            aobj[3] = "user's request after error";
            EventLog.writeEvent(30023, aobj);
            Process.killProcessQuiet(processrecord.pid);
        }
        return;
    }

    public void killApplicationProcess(String s, int i) {
        if(s != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j;
        j = Binder.getCallingUid();
        if(j != 1000)
            break MISSING_BLOCK_LABEL_104;
        this;
        JVM INSTR monitorenter ;
        ProcessRecord processrecord;
        IApplicationThread iapplicationthread;
        processrecord = getProcessRecordLocked(s, i);
        if(processrecord == null)
            break MISSING_BLOCK_LABEL_65;
        iapplicationthread = processrecord.thread;
        if(iapplicationthread != null) {
            Exception exception;
            try {
                processrecord.thread.scheduleSuicide();
            }
            catch(RemoteException remoteexception) { }
            finally {
                this;
            }
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_65;
        throw exception;
        Slog.w("ActivityManager", (new StringBuilder()).append("Process/uid not found attempting kill of ").append(s).append(" / ").append(i).toString());
        if(true)
            break MISSING_BLOCK_LABEL_53;
        throw new SecurityException((new StringBuilder()).append(j).append(" cannot kill app process: ").append(s).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void killApplicationWithUid(String s, int i) {
        if(s != null)
            if(i < 0) {
                Slog.w("ActivityManager", (new StringBuilder()).append("Invalid uid specified for pkg : ").append(s).toString());
            } else {
                int j = Binder.getCallingUid();
                if(j == 1000) {
                    Message message = mHandler.obtainMessage(22);
                    message.arg1 = i;
                    message.arg2 = 0;
                    message.obj = s;
                    mHandler.sendMessage(message);
                } else {
                    throw new SecurityException((new StringBuilder()).append(j).append(" cannot kill pkg: ").append(s).toString());
                }
            }
    }

    public void killBackgroundProcesses(String s) {
        int i;
        long l;
        if(checkCallingPermission("android.permission.KILL_BACKGROUND_PROCESSES") != 0 && checkCallingPermission("android.permission.RESTART_PACKAGES") != 0) {
            String s1 = (new StringBuilder()).append("Permission Denial: killBackgroundProcesses() from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append("android.permission.KILL_BACKGROUND_PROCESSES").toString();
            Slog.w("ActivityManager", s1);
            throw new SecurityException(s1);
        }
        i = UserId.getCallingUserId();
        l = Binder.clearCallingIdentity();
        IPackageManager ipackagemanager = AppGlobals.getPackageManager();
        int j = -1;
        this;
        JVM INSTR monitorenter ;
        int k = ipackagemanager.getPackageUid(s, i);
        j = k;
_L6:
        if(j != -1) goto _L2; else goto _L1
_L1:
        Slog.w("ActivityManager", (new StringBuilder()).append("Invalid packageName: ").append(s).toString());
        this;
        JVM INSTR monitorexit ;
_L4:
        Binder.restoreCallingIdentity(l);
        return;
_L2:
        killPackageProcessesLocked(s, j, 5, false, true, true, false, "kill background");
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean killPids(int ai[], String s, boolean flag) {
        int i;
        if(Binder.getCallingUid() != 1000)
            throw new SecurityException("killPids only available to the system");
        String s1;
        boolean flag1;
        SparseArray sparsearray;
        Exception exception;
        int ai1[];
        int j;
        ProcessRecord processrecord;
        int l;
        Object aobj[];
        ProcessRecord processrecord1;
        int i1;
        if(s == null)
            s1 = "Unknown";
        else
            s1 = s;
        flag1 = false;
        sparsearray = mPidsSelfLocked;
        sparsearray;
        JVM INSTR monitorenter ;
        ai1 = new int[ai.length];
        i = 0;
        j = 0;
        break MISSING_BLOCK_LABEL_53;
        do {
label0:
            {
                if(j < ai.length) {
                    processrecord1 = (ProcessRecord)mPidsSelfLocked.get(ai[j]);
                    if(processrecord1 != null) {
                        i1 = processrecord1.setAdj;
                        ai1[j] = i1;
                        if(i1 > i)
                            i = i1;
                    }
                } else {
                    if(i < 15 && i > ProcessList.HIDDEN_APP_MIN_ADJ)
                        i = ProcessList.HIDDEN_APP_MIN_ADJ;
                    break label0;
                }
                j++;
            }
        } while(true);
        if(!flag && i < 5)
            i = 5;
        Slog.w("ActivityManager", (new StringBuilder()).append("Killing processes ").append(s1).append(" at adjustment ").append(i).toString());
        for(int k = 0; k < ai.length; k++) {
            processrecord = (ProcessRecord)mPidsSelfLocked.get(ai[k]);
            if(processrecord != null) {
                l = processrecord.setAdj;
                if(l >= i && !processrecord.killedBackground) {
                    Slog.w("ActivityManager", (new StringBuilder()).append("Killing ").append(processrecord).append(" (adj ").append(l).append("): ").append(s1).toString());
                    aobj = new Object[4];
                    aobj[0] = Integer.valueOf(processrecord.pid);
                    aobj[1] = processrecord.processName;
                    aobj[2] = Integer.valueOf(l);
                    aobj[3] = s1;
                    EventLog.writeEvent(30023, aobj);
                    flag1 = true;
                    processrecord.killedBackground = true;
                    Process.killProcessQuiet(ai[k]);
                }
            }
            break MISSING_BLOCK_LABEL_387;
        }

        break MISSING_BLOCK_LABEL_353;
        exception;
        throw exception;
        sparsearray;
        JVM INSTR monitorexit ;
        return flag1;
    }

    public boolean killProcessesBelowForeground(String s) {
        if(Binder.getCallingUid() != 1000)
            throw new SecurityException("killProcessesBelowForeground() only available to system");
        else
            return killProcessesBelowAdj(0, s);
    }

    final void logAppTooSlow(ProcessRecord processrecord, long l, String s) {
    }

    android.app.ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked(ServiceRecord servicerecord) {
        android.app.ActivityManager.RunningServiceInfo runningserviceinfo = new android.app.ActivityManager.RunningServiceInfo();
        runningserviceinfo.service = servicerecord.name;
        if(servicerecord.app != null)
            runningserviceinfo.pid = servicerecord.app.pid;
        runningserviceinfo.uid = servicerecord.appInfo.uid;
        runningserviceinfo.process = servicerecord.processName;
        runningserviceinfo.foreground = servicerecord.isForeground;
        runningserviceinfo.activeSince = servicerecord.createTime;
        runningserviceinfo.started = servicerecord.startRequested;
        runningserviceinfo.clientCount = servicerecord.connections.size();
        runningserviceinfo.crashCount = servicerecord.crashCount;
        runningserviceinfo.lastActivityTime = servicerecord.lastActivity;
        if(servicerecord.isForeground)
            runningserviceinfo.flags = 2 | runningserviceinfo.flags;
        if(servicerecord.startRequested)
            runningserviceinfo.flags = 1 | runningserviceinfo.flags;
        if(servicerecord.app != null && servicerecord.app.pid == MY_PID)
            runningserviceinfo.flags = 4 | runningserviceinfo.flags;
        if(servicerecord.app != null && servicerecord.app.persistent)
            runningserviceinfo.flags = 8 | runningserviceinfo.flags;
        Iterator iterator = servicerecord.connections.values().iterator();
label0:
        do {
label1:
            {
                if(iterator.hasNext()) {
                    ArrayList arraylist = (ArrayList)iterator.next();
                    ConnectionRecord connectionrecord;
                    for(int i = 0; i >= arraylist.size(); i++)
                        continue label0;

                    connectionrecord = (ConnectionRecord)arraylist.get(i);
                    if(connectionrecord.clientLabel == 0)
                        break label1;
                    runningserviceinfo.clientPackage = connectionrecord.binding.client.info.packageName;
                    runningserviceinfo.clientLabel = connectionrecord.clientLabel;
                }
                return runningserviceinfo;
            }
        } while(true);
    }

    public void monitor() {
        this;
        JVM INSTR monitorenter ;
    }

    public boolean moveActivityTaskToBack(IBinder ibinder, boolean flag) {
        boolean flag1;
        flag1 = false;
        enforceNotIsolatedCaller("moveActivityTaskToBack");
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        boolean flag2;
        int i;
        if(!flag)
            flag2 = true;
        else
            flag2 = false;
        i = getTaskForActivityLocked(ibinder, flag2);
        if(i >= 0)
            flag1 = mMainStack.moveTaskToBackLocked(i, null);
        else
            Binder.restoreCallingIdentity(l);
        return flag1;
    }

    public void moveTaskBackwards(int i) {
        enforceCallingPermission("android.permission.REORDER_TASKS", "moveTaskBackwards()");
        this;
        JVM INSTR monitorenter ;
        if(checkAppSwitchAllowedLocked(Binder.getCallingPid(), Binder.getCallingUid(), "Task backwards")) {
            long l = Binder.clearCallingIdentity();
            moveTaskBackwardsLocked(i);
            Binder.restoreCallingIdentity(l);
        }
        return;
    }

    public void moveTaskToBack(int i) {
        enforceCallingPermission("android.permission.REORDER_TASKS", "moveTaskToBack()");
        this;
        JVM INSTR monitorenter ;
        if(mMainStack.mResumedActivity == null || mMainStack.mResumedActivity.task.taskId != i || checkAppSwitchAllowedLocked(Binder.getCallingPid(), Binder.getCallingUid(), "Task to back")) {
            long l = Binder.clearCallingIdentity();
            mMainStack.moveTaskToBackLocked(i, null);
            Binder.restoreCallingIdentity(l);
        }
        return;
    }

    public void moveTaskToFront(int i, int j, Bundle bundle) {
        enforceCallingPermission("android.permission.REORDER_TASKS", "moveTaskToFront()");
        this;
        JVM INSTR monitorenter ;
        if(checkAppSwitchAllowedLocked(Binder.getCallingPid(), Binder.getCallingUid(), "Task to front")) goto _L2; else goto _L1
_L1:
        ActivityOptions.abort(bundle);
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        long l = Binder.clearCallingIdentity();
        TaskRecord taskrecord = taskForIdLocked(i);
        if(taskrecord == null) goto _L5; else goto _L4
_L4:
        if((j & 2) == 0)
            mMainStack.mUserLeaving = true;
        if((j & 1) != 0)
            mMainStack.moveHomeToFrontLocked();
        mMainStack.moveTaskToFrontLocked(taskrecord, null, bundle);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L5:
        int k = -1 + mMainStack.mHistory.size();
_L8:
        if(k < 0) goto _L7; else goto _L6
_L6:
        ActivityRecord activityrecord = (ActivityRecord)mMainStack.mHistory.get(k);
        if(activityrecord.task.taskId != i)
            break MISSING_BLOCK_LABEL_231;
        if((j & 2) == 0)
            mMainStack.mUserLeaving = true;
        if((j & 1) != 0)
            mMainStack.moveHomeToFrontLocked();
        mMainStack.moveTaskToFrontLocked(activityrecord.task, null, bundle);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
_L7:
        Binder.restoreCallingIdentity(l);
        ActivityOptions.abort(bundle);
        this;
        JVM INSTR monitorexit ;
_L3:
        return;
        k--;
          goto _L8
    }

    public boolean navigateUpTo(IBinder ibinder, Intent intent, int i, Intent intent1) {
        ComponentName componentname = intent.getComponent();
        this;
        JVM INSTR monitorenter ;
        ActivityRecord activityrecord = ActivityRecord.forToken(ibinder);
        if(activityrecord != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
          goto _L3
_L2:
        ArrayList arraylist;
        int j;
        arraylist = activityrecord.stack.mHistory;
        j = arraylist.indexOf(activityrecord);
        if(j >= 0) goto _L5; else goto _L4
_L4:
        flag = false;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L5:
        int k;
        ActivityRecord activityrecord1;
        k = j - 1;
        activityrecord1 = null;
        flag = false;
        if(componentname == null) goto _L7; else goto _L6
_L6:
        TaskRecord taskrecord;
        int j2;
        taskrecord = activityrecord.task;
        j2 = j - 1;
_L21:
        if(j2 < 0) goto _L7; else goto _L8
_L8:
        ActivityRecord activityrecord4 = (ActivityRecord)arraylist.get(j2);
        if(taskrecord == activityrecord4.task) goto _L10; else goto _L9
_L9:
        k = Math.min(j - 1, j2 + 1);
        activityrecord1 = (ActivityRecord)arraylist.get(k);
_L7:
        if(mController == null) goto _L12; else goto _L11
_L11:
        ActivityRecord activityrecord3 = mMainStack.topRunningActivityLocked(ibinder, 0);
        if(activityrecord3 == null) goto _L12; else goto _L13
_L13:
        boolean flag1 = true;
        boolean flag2 = mController.activityResuming(activityrecord3.packageName);
        flag1 = flag2;
_L15:
        if(flag1) goto _L12; else goto _L14
_L14:
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L10:
        if(!((ComponentInfo) (activityrecord4.info)).packageName.equals(componentname.getPackageName()) || !((ComponentInfo) (activityrecord4.info)).name.equals(componentname.getClassName()))
            break MISSING_BLOCK_LABEL_515;
        k = j2;
        activityrecord1 = activityrecord4;
        flag = true;
          goto _L7
        RemoteException remoteexception1;
        remoteexception1;
        mController = null;
          goto _L15
_L12:
        long l;
        l = Binder.clearCallingIdentity();
        for(int