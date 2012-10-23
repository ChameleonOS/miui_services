// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.*;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.os.*;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.server.NotificationManagerService;
import java.io.PrintWriter;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService, IntentBindRecord, UriPermissionOwner, ProcessRecord, 
//            AppBindRecord

class ServiceRecord extends Binder {
    static class StartItem {

        UriPermissionOwner getUriPermissionsLocked() {
            if(uriPermissions == null)
                uriPermissions = new UriPermissionOwner(sr.ams, this);
            return uriPermissions;
        }

        void removeUriPermissionsLocked() {
            if(uriPermissions != null) {
                uriPermissions.removeUriPermissionsLocked();
                uriPermissions = null;
            }
        }

        public String toString() {
            String s;
            if(stringName != null) {
                s = stringName;
            } else {
                StringBuilder stringbuilder = new StringBuilder(128);
                stringbuilder.append("ServiceRecord{").append(Integer.toHexString(System.identityHashCode(sr))).append(' ').append(sr.shortName).append(" StartItem ").append(Integer.toHexString(System.identityHashCode(this))).append(" id=").append(id).append('}');
                s = stringbuilder.toString();
                stringName = s;
            }
            return s;
        }

        long deliveredTime;
        int deliveryCount;
        int doneExecutingCount;
        final int id;
        final Intent intent;
        final ActivityManagerService.NeededUriGrants neededGrants;
        final ServiceRecord sr;
        String stringName;
        final boolean taskRemoved;
        UriPermissionOwner uriPermissions;

        StartItem(ServiceRecord servicerecord, boolean flag, int i, Intent intent1, ActivityManagerService.NeededUriGrants neededurigrants) {
            sr = servicerecord;
            taskRemoved = flag;
            id = i;
            intent = intent1;
            neededGrants = neededurigrants;
        }
    }


    ServiceRecord(ActivityManagerService activitymanagerservice, com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv serv, ComponentName componentname, android.content.Intent.FilterComparison filtercomparison, ServiceInfo serviceinfo, Runnable runnable) {
        ams = activitymanagerservice;
        stats = serv;
        name = componentname;
        shortName = componentname.flattenToShortString();
        intent = filtercomparison;
        serviceInfo = serviceinfo;
        appInfo = serviceinfo.applicationInfo;
        packageName = serviceinfo.applicationInfo.packageName;
        processName = serviceinfo.processName;
        permission = serviceinfo.permission;
        baseDir = serviceinfo.applicationInfo.sourceDir;
        resDir = serviceinfo.applicationInfo.publicSourceDir;
        dataDir = serviceinfo.applicationInfo.dataDir;
        exported = serviceinfo.exported;
        restarter = runnable;
        lastActivity = SystemClock.uptimeMillis();
        userId = UserId.getUserId(appInfo.uid);
    }

    public void cancelNotification() {
        if(foregroundId != 0) {
            final String localPackageName = packageName;
            final int localForegroundId = foregroundId;
            ams.mHandler.post(new Runnable() {

                public void run() {
                    INotificationManager inotificationmanager = NotificationManager.getService();
                    if(inotificationmanager != null)
                        try {
                            inotificationmanager.cancelNotification(localPackageName, localForegroundId);
                        }
                        catch(RuntimeException runtimeexception) {
                            Slog.w("ActivityManager", "Error canceling notification for service", runtimeexception);
                        }
                        catch(RemoteException remoteexception) { }
                }

                final ServiceRecord this$0;
                final int val$localForegroundId;
                final String val$localPackageName;

             {
                this$0 = ServiceRecord.this;
                localPackageName = s;
                localForegroundId = i;
                super();
            }
            });
        }
    }

    public void clearDeliveredStartsLocked() {
        for(int i = -1 + deliveredStarts.size(); i >= 0; i--)
            ((StartItem)deliveredStarts.get(i)).removeUriPermissionsLocked();

        deliveredStarts.clear();
    }

    void dump(PrintWriter printwriter, String s) {
label0:
        {
            printwriter.print(s);
            printwriter.print("intent={");
            printwriter.print(intent.getIntent().toShortString(false, true, false, true));
            printwriter.println('}');
            printwriter.print(s);
            printwriter.print("packageName=");
            printwriter.println(packageName);
            printwriter.print(s);
            printwriter.print("processName=");
            printwriter.println(processName);
            if(permission != null) {
                printwriter.print(s);
                printwriter.print("permission=");
                printwriter.println(permission);
            }
            long l = SystemClock.uptimeMillis();
            long l1 = SystemClock.elapsedRealtime();
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
            printwriter.print("app=");
            printwriter.println(app);
            if(isolatedProc != null) {
                printwriter.print(s);
                printwriter.print("isolatedProc=");
                printwriter.println(isolatedProc);
            }
            if(isForeground || foregroundId != 0) {
                printwriter.print(s);
                printwriter.print("isForeground=");
                printwriter.print(isForeground);
                printwriter.print(" foregroundId=");
                printwriter.print(foregroundId);
                printwriter.print(" foregroundNoti=");
                printwriter.println(foregroundNoti);
            }
            printwriter.print(s);
            printwriter.print("createTime=");
            TimeUtils.formatDuration(createTime, l1, printwriter);
            printwriter.print(" lastActivity=");
            TimeUtils.formatDuration(lastActivity, l, printwriter);
            printwriter.println("");
            printwriter.print(s);
            printwriter.print("executingStart=");
            TimeUtils.formatDuration(executingStart, l, printwriter);
            printwriter.print(" restartTime=");
            TimeUtils.formatDuration(restartTime, l, printwriter);
            printwriter.println("");
            if(startRequested || lastStartId != 0) {
                printwriter.print(s);
                printwriter.print("startRequested=");
                printwriter.print(startRequested);
                printwriter.print(" stopIfKilled=");
                printwriter.print(stopIfKilled);
                printwriter.print(" callStart=");
                printwriter.print(callStart);
                printwriter.print(" lastStartId=");
                printwriter.println(lastStartId);
            }
            if(executeNesting != 0 || crashCount != 0 || restartCount != 0 || restartDelay != 0L || nextRestartTime != 0L) {
                printwriter.print(s);
                printwriter.print("executeNesting=");
                printwriter.print(executeNesting);
                printwriter.print(" restartCount=");
                printwriter.print(restartCount);
                printwriter.print(" restartDelay=");
                TimeUtils.formatDuration(restartDelay, l, printwriter);
                printwriter.print(" nextRestartTime=");
                TimeUtils.formatDuration(nextRestartTime, l, printwriter);
                printwriter.print(" crashCount=");
                printwriter.println(crashCount);
            }
            if(deliveredStarts.size() > 0) {
                printwriter.print(s);
                printwriter.println("Delivered Starts:");
                dumpStartList(printwriter, s, deliveredStarts, l);
            }
            if(pendingStarts.size() > 0) {
                printwriter.print(s);
                printwriter.println("Pending Starts:");
                dumpStartList(printwriter, s, pendingStarts, 0L);
            }
            if(bindings.size() > 0) {
                Iterator iterator1 = bindings.values().iterator();
                printwriter.print(s);
                printwriter.println("Bindings:");
                IntentBindRecord intentbindrecord;
                for(; iterator1.hasNext(); intentbindrecord.dumpInService(printwriter, (new StringBuilder()).append(s).append("  ").toString())) {
                    intentbindrecord = (IntentBindRecord)iterator1.next();
                    printwriter.print(s);
                    printwriter.print("* IntentBindRecord{");
                    printwriter.print(Integer.toHexString(System.identityHashCode(intentbindrecord)));
                    printwriter.println("}:");
                }

            }
            if(connections.size() <= 0)
                break label0;
            printwriter.print(s);
            printwriter.println("All Connections:");
            Iterator iterator = connections.values().iterator();
            do {
                if(!iterator.hasNext())
                    break label0;
                ArrayList arraylist = (ArrayList)iterator.next();
                int i = 0;
                do {
                    int j = arraylist.size();
                    if(i >= j)
                        break;
                    printwriter.print(s);
                    printwriter.print("  ");
                    printwriter.println(arraylist.get(i));
                    i++;
                } while(true);
            } while(true);
        }
    }

    void dumpStartList(PrintWriter printwriter, String s, List list, long l) {
        int i = list.size();
        int j = 0;
        while(j < i)  {
            StartItem startitem = (StartItem)list.get(j);
            printwriter.print(s);
            printwriter.print("#");
            printwriter.print(j);
            printwriter.print(" id=");
            printwriter.print(startitem.id);
            if(l != 0L) {
                printwriter.print(" dur=");
                TimeUtils.formatDuration(startitem.deliveredTime, l, printwriter);
            }
            if(startitem.deliveryCount != 0) {
                printwriter.print(" dc=");
                printwriter.print(startitem.deliveryCount);
            }
            if(startitem.doneExecutingCount != 0) {
                printwriter.print(" dxc=");
                printwriter.print(startitem.doneExecutingCount);
            }
            printwriter.println("");
            printwriter.print(s);
            printwriter.print("  intent=");
            if(startitem.intent != null)
                printwriter.println(startitem.intent.toString());
            else
                printwriter.println("null");
            if(startitem.neededGrants != null) {
                printwriter.print(s);
                printwriter.print("  neededGrants=");
                printwriter.println(startitem.neededGrants);
            }
            if(startitem.uriPermissions == null)
                continue;
            if(startitem.uriPermissions.readUriPermissions != null) {
                printwriter.print(s);
                printwriter.print("  readUriPermissions=");
                printwriter.println(startitem.uriPermissions.readUriPermissions);
            }
            if(startitem.uriPermissions.writeUriPermissions != null) {
                printwriter.print(s);
                printwriter.print("  writeUriPermissions=");
                printwriter.println(startitem.uriPermissions.writeUriPermissions);
            }
            j++;
        }
    }

    public StartItem findDeliveredStart(int i, boolean flag) {
        int j;
        int k;
        j = deliveredStarts.size();
        k = 0;
_L3:
        StartItem startitem;
        if(k >= j)
            break MISSING_BLOCK_LABEL_63;
        startitem = (StartItem)deliveredStarts.get(k);
        if(startitem.id != i) goto _L2; else goto _L1
_L1:
        if(flag)
            deliveredStarts.remove(k);
_L4:
        return startitem;
_L2:
        k++;
          goto _L3
        startitem = null;
          goto _L4
    }

    public int getLastStartId() {
        return lastStartId;
    }

    public int makeNextStartId() {
        lastStartId = 1 + lastStartId;
        if(lastStartId < 1)
            lastStartId = 1;
        return lastStartId;
    }

    public void postNotification() {
        final int appUid = appInfo.uid;
        final int appPid = app.pid;
        if(foregroundId != 0 && foregroundNoti != null) {
            final String localPackageName = packageName;
            final int localForegroundId = foregroundId;
            final Notification localForegroundNoti = foregroundNoti;
            ams.mHandler.post(new Runnable() {

                public void run() {
                    NotificationManagerService notificationmanagerservice = (NotificationManagerService)NotificationManager.getService();
                    if(notificationmanagerservice != null)
                        try {
                            int ai[] = new int[1];
                            notificationmanagerservice.enqueueNotificationInternal(localPackageName, appUid, appPid, null, localForegroundId, localForegroundNoti, ai);
                        }
                        catch(RuntimeException runtimeexception) {
                            Slog.w("ActivityManager", "Error showing notification for service", runtimeexception);
                            ams.setServiceForeground(name, ServiceRecord.this, 0, null, true);
                            ams.crashApplication(appUid, appPid, localPackageName, (new StringBuilder()).append("Bad notification for startForeground: ").append(runtimeexception).toString());
                        }
                }

                final ServiceRecord this$0;
                final int val$appPid;
                final int val$appUid;
                final int val$localForegroundId;
                final Notification val$localForegroundNoti;
                final String val$localPackageName;

             {
                this$0 = ServiceRecord.this;
                localPackageName = s;
                appUid = i;
                appPid = j;
                localForegroundId = k;
                localForegroundNoti = notification;
                super();
            }
            });
        }
    }

    public void resetRestartCounter() {
        restartCount = 0;
        restartDelay = 0L;
        restartTime = 0L;
    }

    public AppBindRecord retrieveAppBindingLocked(Intent intent1, ProcessRecord processrecord) {
        android.content.Intent.FilterComparison filtercomparison = new android.content.Intent.FilterComparison(intent1);
        IntentBindRecord intentbindrecord = (IntentBindRecord)bindings.get(filtercomparison);
        if(intentbindrecord == null) {
            intentbindrecord = new IntentBindRecord(this, filtercomparison);
            bindings.put(filtercomparison, intentbindrecord);
        }
        AppBindRecord appbindrecord = (AppBindRecord)intentbindrecord.apps.get(processrecord);
        AppBindRecord appbindrecord2;
        if(appbindrecord != null) {
            appbindrecord2 = appbindrecord;
        } else {
            AppBindRecord appbindrecord1 = new AppBindRecord(this, intentbindrecord, processrecord);
            intentbindrecord.apps.put(processrecord, appbindrecord1);
            appbindrecord2 = appbindrecord1;
        }
        return appbindrecord2;
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ServiceRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(' ').append(shortName).append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    static final int MAX_DELIVERY_COUNT = 3;
    static final int MAX_DONE_EXECUTING_COUNT = 6;
    final ActivityManagerService ams;
    ProcessRecord app;
    final ApplicationInfo appInfo;
    final String baseDir;
    final HashMap bindings = new HashMap();
    boolean callStart;
    final HashMap connections = new HashMap();
    int crashCount;
    final long createTime = SystemClock.elapsedRealtime();
    final String dataDir;
    final ArrayList deliveredStarts = new ArrayList();
    int executeNesting;
    long executingStart;
    final boolean exported;
    int foregroundId;
    Notification foregroundNoti;
    final android.content.Intent.FilterComparison intent;
    boolean isForeground;
    ProcessRecord isolatedProc;
    long lastActivity;
    private int lastStartId;
    final ComponentName name;
    long nextRestartTime;
    final String packageName;
    final ArrayList pendingStarts = new ArrayList();
    final String permission;
    final String processName;
    final String resDir;
    int restartCount;
    long restartDelay;
    long restartTime;
    final Runnable restarter;
    final ServiceInfo serviceInfo;
    final String shortName;
    boolean startRequested;
    final com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv stats;
    boolean stopIfKilled;
    String stringName;
    int totalRestartCount;
    final int userId;
}
