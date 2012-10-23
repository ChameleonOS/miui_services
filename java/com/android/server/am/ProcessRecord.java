// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.*;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.os.*;
import android.util.PrintWriterPrinter;
import android.util.TimeUtils;
import com.android.internal.os.BatteryStatsImpl;
import java.io.PrintWriter;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ProcessList, ServiceRecord, ConnectionRecord, ContentProviderConnection, 
//            ReceiverList, ActivityRecord, BroadcastRecord

class ProcessRecord {

    ProcessRecord(com.android.internal.os.BatteryStatsImpl.Uid.Proc proc, IApplicationThread iapplicationthread, ApplicationInfo applicationinfo, String s, int i) {
        batteryStats = proc;
        info = applicationinfo;
        boolean flag;
        if(applicationinfo.uid != i)
            flag = true;
        else
            flag = false;
        isolated = flag;
        uid = i;
        userId = UserId.getUserId(i);
        processName = s;
        pkgList.add(applicationinfo.packageName);
        thread = iapplicationthread;
        maxAdj = 15;
        hiddenAdj = ProcessList.HIDDEN_APP_MIN_ADJ;
        setRawAdj = -100;
        curRawAdj = -100;
        setAdj = -100;
        curAdj = -100;
        persistent = false;
        removed = false;
    }

    public boolean addPackage(String s) {
        boolean flag;
        if(!pkgList.contains(s)) {
            pkgList.add(s);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    void dump(PrintWriter printwriter, String s) {
        long l = SystemClock.uptimeMillis();
        printwriter.print(s);
        printwriter.print("user #");
        printwriter.print(userId);
        printwriter.print(" uid=");
        printwriter.print(info.uid);
        if(uid != info.uid) {
            printwriter.print(" ISOLATED uid=");
            printwriter.print(uid);
        }
        printwriter.println();
        if(info.className != null) {
            printwriter.print(s);
            printwriter.print("class=");
            printwriter.println(info.className);
        }
        if(info.manageSpaceActivityName != null) {
            printwriter.print(s);
            printwriter.print("manageSpaceActivityName=");
            printwriter.println(info.manageSpaceActivityName);
        }
        printwriter.print(s);
        printwriter.print("dir=");
        printwriter.print(info.sourceDir);
        printwriter.print(" publicDir=");
        printwriter.print(info.publicSourceDir);
        printwriter.print(" data=");
        printwriter.println(info.dataDir);
        printwriter.print(s);
        printwriter.print("packageList=");
        printwriter.println(pkgList);
        printwriter.print(s);
        printwriter.print("compat=");
        printwriter.println(compat);
        if(instrumentationClass != null || instrumentationProfileFile != null || instrumentationArguments != null) {
            printwriter.print(s);
            printwriter.print("instrumentationClass=");
            printwriter.print(instrumentationClass);
            printwriter.print(" instrumentationProfileFile=");
            printwriter.println(instrumentationProfileFile);
            printwriter.print(s);
            printwriter.print("instrumentationArguments=");
            printwriter.println(instrumentationArguments);
            printwriter.print(s);
            printwriter.print("instrumentationInfo=");
            printwriter.println(instrumentationInfo);
            if(instrumentationInfo != null) {
                ApplicationInfo applicationinfo = instrumentationInfo;
                PrintWriterPrinter printwriterprinter = new PrintWriterPrinter(printwriter);
                applicationinfo.dump(printwriterprinter, (new StringBuilder()).append(s).append("  ").toString());
            }
        }
        printwriter.print(s);
        printwriter.print("thread=");
        printwriter.print(thread);
        printwriter.print(" curReceiver=");
        printwriter.println(curReceiver);
        printwriter.print(s);
        printwriter.print("pid=");
        printwriter.print(pid);
        printwriter.print(" starting=");
        printwriter.print(starting);
        printwriter.print(" lastPss=");
        printwriter.println(lastPss);
        printwriter.print(s);
        printwriter.print("lastActivityTime=");
        TimeUtils.formatDuration(lastActivityTime, l, printwriter);
        printwriter.print(" lruWeight=");
        printwriter.print(lruWeight);
        printwriter.print(" serviceb=");
        printwriter.print(serviceb);
        printwriter.print(" keeping=");
        printwriter.print(keeping);
        printwriter.print(" hidden=");
        printwriter.print(hidden);
        printwriter.print(" empty=");
        printwriter.println(empty);
        printwriter.print(s);
        printwriter.print("oom: max=");
        printwriter.print(maxAdj);
        printwriter.print(" hidden=");
        printwriter.print(hiddenAdj);
        printwriter.print(" curRaw=");
        printwriter.print(curRawAdj);
        printwriter.print(" setRaw=");
        printwriter.print(setRawAdj);
        printwriter.print(" nonStopping=");
        printwriter.print(nonStoppingAdj);
        printwriter.print(" cur=");
        printwriter.print(curAdj);
        printwriter.print(" set=");
        printwriter.println(setAdj);
        printwriter.print(s);
        printwriter.print("curSchedGroup=");
        printwriter.print(curSchedGroup);
        printwriter.print(" setSchedGroup=");
        printwriter.print(setSchedGroup);
        printwriter.print(" systemNoUi=");
        printwriter.print(systemNoUi);
        printwriter.print(" trimMemoryLevel=");
        printwriter.println(trimMemoryLevel);
        printwriter.print(s);
        printwriter.print("hasShownUi=");
        printwriter.print(hasShownUi);
        printwriter.print(" pendingUiClean=");
        printwriter.print(pendingUiClean);
        printwriter.print(" hasAboveClient=");
        printwriter.println(hasAboveClient);
        printwriter.print(s);
        printwriter.print("setIsForeground=");
        printwriter.print(setIsForeground);
        printwriter.print(" foregroundServices=");
        printwriter.print(foregroundServices);
        printwriter.print(" forcingToForeground=");
        printwriter.println(forcingToForeground);
        printwriter.print(s);
        printwriter.print("persistent=");
        printwriter.print(persistent);
        printwriter.print(" removed=");
        printwriter.println(removed);
        printwriter.print(s);
        printwriter.print("adjSeq=");
        printwriter.print(adjSeq);
        printwriter.print(" lruSeq=");
        printwriter.println(lruSeq);
        if(!keeping) {
            long l1;
            synchronized(batteryStats.getBatteryStats()) {
                l1 = batteryStats.getBatteryStats().getProcessWakeTime(info.uid, pid, SystemClock.elapsedRealtime());
            }
            long l2 = l1 - lastWakeTime;
            printwriter.print(s);
            printwriter.print("lastWakeTime=");
            printwriter.print(lastWakeTime);
            printwriter.print(" time used=");
            TimeUtils.formatDuration(l2, printwriter);
            printwriter.println("");
            printwriter.print(s);
            printwriter.print("lastCpuTime=");
            printwriter.print(lastCpuTime);
            printwriter.print(" time used=");
            TimeUtils.formatDuration(curCpuTime - lastCpuTime, printwriter);
            printwriter.println("");
        }
        printwriter.print(s);
        printwriter.print("lastRequestedGc=");
        TimeUtils.formatDuration(lastRequestedGc, l, printwriter);
        printwriter.print(" lastLowMemory=");
        TimeUtils.formatDuration(lastLowMemory, l, printwriter);
        printwriter.print(" reportLowMemory=");
        printwriter.println(reportLowMemory);
        if(killedBackground || waitingToKill != null) {
            printwriter.print(s);
            printwriter.print("killedBackground=");
            printwriter.print(killedBackground);
            printwriter.print(" waitingToKill=");
            printwriter.println(waitingToKill);
        }
        if(debugging || crashing || crashDialog != null || notResponding || anrDialog != null || bad) {
            printwriter.print(s);
            printwriter.print("debugging=");
            printwriter.print(debugging);
            printwriter.print(" crashing=");
            printwriter.print(crashing);
            printwriter.print(" ");
            printwriter.print(crashDialog);
            printwriter.print(" notResponding=");
            printwriter.print(notResponding);
            printwriter.print(" ");
            printwriter.print(anrDialog);
            printwriter.print(" bad=");
            printwriter.print(bad);
            if(errorReportReceiver != null) {
                printwriter.print(" errorReportReceiver=");
                printwriter.print(errorReportReceiver.flattenToShortString());
            }
            printwriter.println();
        }
        if(activities.size() > 0) {
            printwriter.print(s);
            printwriter.println("Activities:");
            for(int j = 0; j < activities.size(); j++) {
                printwriter.print(s);
                printwriter.print("  - ");
                printwriter.println(activities.get(j));
            }

        }
        break MISSING_BLOCK_LABEL_1390;
        exception;
        batterystatsimpl;
        JVM INSTR monitorexit ;
        throw exception;
        if(services.size() > 0) {
            printwriter.print(s);
            printwriter.println("Services:");
            ServiceRecord servicerecord1;
            for(Iterator iterator4 = services.iterator(); iterator4.hasNext(); printwriter.println(servicerecord1)) {
                servicerecord1 = (ServiceRecord)iterator4.next();
                printwriter.print(s);
                printwriter.print("  - ");
            }

        }
        if(executingServices.size() > 0) {
            printwriter.print(s);
            printwriter.println("Executing Services:");
            ServiceRecord servicerecord;
            for(Iterator iterator3 = executingServices.iterator(); iterator3.hasNext(); printwriter.println(servicerecord)) {
                servicerecord = (ServiceRecord)iterator3.next();
                printwriter.print(s);
                printwriter.print("  - ");
            }

        }
        if(connections.size() > 0) {
            printwriter.print(s);
            printwriter.println("Connections:");
            ConnectionRecord connectionrecord;
            for(Iterator iterator2 = connections.iterator(); iterator2.hasNext(); printwriter.println(connectionrecord)) {
                connectionrecord = (ConnectionRecord)iterator2.next();
                printwriter.print(s);
                printwriter.print("  - ");
            }

        }
        if(pubProviders.size() > 0) {
            printwriter.print(s);
            printwriter.println("Published Providers:");
            java.util.Map.Entry entry;
            for(Iterator iterator1 = pubProviders.entrySet().iterator(); iterator1.hasNext(); printwriter.println(entry.getValue())) {
                entry = (java.util.Map.Entry)iterator1.next();
                printwriter.print(s);
                printwriter.print("  - ");
                printwriter.println((String)entry.getKey());
                printwriter.print(s);
                printwriter.print("    -> ");
            }

        }
        if(conProviders.size() > 0) {
            printwriter.print(s);
            printwriter.println("Connected Providers:");
            for(int i = 0; i < conProviders.size(); i++) {
                printwriter.print(s);
                printwriter.print("  - ");
                printwriter.println(((ContentProviderConnection)conProviders.get(i)).toShortString());
            }

        }
        if(receivers.size() > 0) {
            printwriter.print(s);
            printwriter.println("Receivers:");
            ReceiverList receiverlist;
            for(Iterator iterator = receivers.iterator(); iterator.hasNext(); printwriter.println(receiverlist)) {
                receiverlist = (ReceiverList)iterator.next();
                printwriter.print(s);
                printwriter.print("  - ");
            }

        }
        return;
    }

    public String[] getPackageList() {
        int i = pkgList.size();
        String as[];
        if(i == 0) {
            as = null;
        } else {
            as = new String[i];
            pkgList.toArray(as);
        }
        return as;
    }

    public boolean isInterestingToUserLocked() {
        int i;
        int j;
        i = activities.size();
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_42;
        if(!((ActivityRecord)activities.get(j)).isInterestingToUserLocked()) goto _L2; else goto _L1
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

    public void resetPackageList() {
        pkgList.clear();
        pkgList.add(info.packageName);
    }

    public void setPid(int i) {
        pid = i;
        shortStringName = null;
        stringName = null;
    }

    public void stopFreezingAllLocked() {
        for(int i = activities.size(); i > 0;) {
            i--;
            ((ActivityRecord)activities.get(i)).stopFreezingScreenLocked(true);
        }

    }

    public String toShortString() {
        String s;
        if(shortStringName != null) {
            s = shortStringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            toShortString(stringbuilder);
            s = stringbuilder.toString();
            shortStringName = s;
        }
        return s;
    }

    void toShortString(StringBuilder stringbuilder) {
        stringbuilder.append(pid);
        stringbuilder.append(':');
        stringbuilder.append(processName);
        stringbuilder.append('/');
        if(info.uid >= 10000) goto _L2; else goto _L1
_L1:
        stringbuilder.append(uid);
_L4:
        return;
_L2:
        stringbuilder.append('u');
        stringbuilder.append(userId);
        stringbuilder.append('a');
        stringbuilder.append(info.uid % 10000);
        if(uid != info.uid) {
            stringbuilder.append('i');
            stringbuilder.append(UserId.getAppId(uid) - 0x182b8);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ProcessRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            toShortString(stringbuilder);
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    public void unlinkDeathRecipient() {
        if(deathRecipient != null && thread != null)
            thread.asBinder().unlinkToDeath(deathRecipient, 0);
        deathRecipient = null;
    }

    void updateHasAboveClientLocked() {
label0:
        {
            hasAboveClient = false;
            if(connections.size() <= 0)
                break label0;
            Iterator iterator = connections.iterator();
            do
                if(!iterator.hasNext())
                    break label0;
            while((8 & ((ConnectionRecord)iterator.next()).flags) == 0);
            hasAboveClient = true;
        }
    }

    final ArrayList activities = new ArrayList();
    int adjSeq;
    Object adjSource;
    int adjSourceOom;
    Object adjTarget;
    String adjType;
    int adjTypeCode;
    Dialog anrDialog;
    boolean bad;
    final com.android.internal.os.BatteryStatsImpl.Uid.Proc batteryStats;
    CompatibilityInfo compat;
    final ArrayList conProviders = new ArrayList();
    final HashSet connections = new HashSet();
    Dialog crashDialog;
    boolean crashing;
    android.app.ActivityManager.ProcessErrorStateInfo crashingReport;
    int curAdj;
    long curCpuTime;
    int curRawAdj;
    BroadcastRecord curReceiver;
    int curSchedGroup;
    android.os.IBinder.DeathRecipient deathRecipient;
    boolean debugging;
    boolean empty;
    ComponentName errorReportReceiver;
    final HashSet executingServices = new HashSet();
    IBinder forcingToForeground;
    boolean foregroundActivities;
    boolean foregroundServices;
    boolean hasAboveClient;
    boolean hasShownUi;
    boolean hidden;
    int hiddenAdj;
    final ApplicationInfo info;
    Bundle instrumentationArguments;
    ComponentName instrumentationClass;
    ApplicationInfo instrumentationInfo;
    String instrumentationProfileFile;
    ComponentName instrumentationResultClass;
    IInstrumentationWatcher instrumentationWatcher;
    final boolean isolated;
    boolean keeping;
    boolean killedBackground;
    long lastActivityTime;
    long lastCpuTime;
    long lastLowMemory;
    int lastPss;
    long lastRequestedGc;
    long lastWakeTime;
    int lruSeq;
    long lruWeight;
    int maxAdj;
    int memImportance;
    int nonStoppingAdj;
    boolean notResponding;
    android.app.ActivityManager.ProcessErrorStateInfo notRespondingReport;
    boolean pendingUiClean;
    boolean persistent;
    int pid;
    final HashSet pkgList = new HashSet();
    final String processName;
    final HashMap pubProviders = new HashMap();
    final HashSet receivers = new HashSet();
    boolean removed;
    boolean reportLowMemory;
    boolean serviceb;
    final HashSet services = new HashSet();
    int setAdj;
    boolean setIsForeground;
    int setRawAdj;
    int setSchedGroup;
    String shortStringName;
    boolean starting;
    String stringName;
    boolean systemNoUi;
    IApplicationThread thread;
    int trimMemoryLevel;
    final int uid;
    final int userId;
    boolean usingWrapper;
    Dialog waitDialog;
    boolean waitedForDebugger;
    String waitingToKill;
}
