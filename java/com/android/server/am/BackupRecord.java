// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.pm.ApplicationInfo;

// Referenced classes of package com.android.server.am:
//            ProcessRecord

class BackupRecord {

    BackupRecord(com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv serv, ApplicationInfo applicationinfo, int i) {
        stats = serv;
        appInfo = applicationinfo;
        backupMode = i;
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("BackupRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(' ').append(appInfo.packageName).append(' ').append(appInfo.name).append(' ').append(appInfo.backupAgentName).append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    public static final int BACKUP_FULL = 1;
    public static final int BACKUP_NORMAL = 0;
    public static final int RESTORE = 2;
    public static final int RESTORE_FULL = 3;
    ProcessRecord app;
    final ApplicationInfo appInfo;
    final int backupMode;
    final com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv stats;
    String stringName;
}
