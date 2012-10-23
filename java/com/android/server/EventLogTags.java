// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.util.EventLog;

public class EventLogTags {

    private EventLogTags() {
    }

    public static void writeBackupAgentFailure(String s, String s1) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = s1;
        EventLog.writeEvent(2823, aobj);
    }

    public static void writeBackupDataChanged(String s) {
        EventLog.writeEvent(2820, s);
    }

    public static void writeBackupInitialize() {
        EventLog.writeEvent(2827, new Object[0]);
    }

    public static void writeBackupPackage(String s, int i) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        EventLog.writeEvent(2824, aobj);
    }

    public static void writeBackupReset(String s) {
        EventLog.writeEvent(2826, s);
    }

    public static void writeBackupStart(String s) {
        EventLog.writeEvent(2821, s);
    }

    public static void writeBackupSuccess(int i, int j) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        EventLog.writeEvent(2825, aobj);
    }

    public static void writeBackupTransportFailure(String s) {
        EventLog.writeEvent(2822, s);
    }

    public static void writeBatteryDischarge(long l, int i, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(2730, aobj);
    }

    public static void writeBatteryLevel(int i, int j, int k) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        EventLog.writeEvent(2722, aobj);
    }

    public static void writeBatteryStatus(int i, int j, int k, int l, String s) {
        Object aobj[] = new Object[5];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = s;
        EventLog.writeEvent(2723, aobj);
    }

    public static void writeBootProgressPmsDataScanStart(long l) {
        EventLog.writeEvent(3080, l);
    }

    public static void writeBootProgressPmsReady(long l) {
        EventLog.writeEvent(3100, l);
    }

    public static void writeBootProgressPmsScanEnd(long l) {
        EventLog.writeEvent(3090, l);
    }

    public static void writeBootProgressPmsStart(long l) {
        EventLog.writeEvent(3060, l);
    }

    public static void writeBootProgressPmsSystemScanStart(long l) {
        EventLog.writeEvent(3070, l);
    }

    public static void writeBootProgressSystemRun(long l) {
        EventLog.writeEvent(3010, l);
    }

    public static void writeCacheFileDeleted(String s) {
        EventLog.writeEvent(2748, s);
    }

    public static void writeConnectivityStateChanged(int i) {
        EventLog.writeEvent(50020, i);
    }

    public static void writeFreeStorageChanged(long l) {
        EventLog.writeEvent(2744, l);
    }

    public static void writeFreeStorageLeft(long l, long l1, long l2) {
        Object aobj[] = new Object[3];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Long.valueOf(l1);
        aobj[2] = Long.valueOf(l2);
        EventLog.writeEvent(2746, aobj);
    }

    public static void writeImfForceReconnectIme(Object aobj[], long l, int i) {
        Object aobj1[] = new Object[3];
        aobj1[0] = ((Object) (aobj));
        aobj1[1] = Long.valueOf(l);
        aobj1[2] = Integer.valueOf(i);
        EventLog.writeEvent(32000, aobj1);
    }

    public static void writeLowStorage(long l) {
        EventLog.writeEvent(2745, l);
    }

    public static void writeNetstatsMobileSample(long l, long l1, long l2, long l3, 
            long l4, long l5, long l6, long l7, long l8, long l9, long l10, 
            long l11, long l12) {
        Object aobj[] = new Object[13];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Long.valueOf(l1);
        aobj[2] = Long.valueOf(l2);
        aobj[3] = Long.valueOf(l3);
        aobj[4] = Long.valueOf(l4);
        aobj[5] = Long.valueOf(l5);
        aobj[6] = Long.valueOf(l6);
        aobj[7] = Long.valueOf(l7);
        aobj[8] = Long.valueOf(l8);
        aobj[9] = Long.valueOf(l9);
        aobj[10] = Long.valueOf(l10);
        aobj[11] = Long.valueOf(l11);
        aobj[12] = Long.valueOf(l12);
        EventLog.writeEvent(51100, aobj);
    }

    public static void writeNetstatsWifiSample(long l, long l1, long l2, long l3, 
            long l4, long l5, long l6, long l7, long l8, long l9, long l10, 
            long l11, long l12) {
        Object aobj[] = new Object[13];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Long.valueOf(l1);
        aobj[2] = Long.valueOf(l2);
        aobj[3] = Long.valueOf(l3);
        aobj[4] = Long.valueOf(l4);
        aobj[5] = Long.valueOf(l5);
        aobj[6] = Long.valueOf(l6);
        aobj[7] = Long.valueOf(l7);
        aobj[8] = Long.valueOf(l8);
        aobj[9] = Long.valueOf(l9);
        aobj[10] = Long.valueOf(l10);
        aobj[11] = Long.valueOf(l11);
        aobj[12] = Long.valueOf(l12);
        EventLog.writeEvent(51101, aobj);
    }

    public static void writeNotificationCancel(String s, int i, String s1, int j, int k) {
        Object aobj[] = new Object[5];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s1;
        aobj[3] = Integer.valueOf(j);
        aobj[4] = Integer.valueOf(k);
        EventLog.writeEvent(2751, aobj);
    }

    public static void writeNotificationCancelAll(String s, int i, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(2752, aobj);
    }

    public static void writeNotificationEnqueue(String s, int i, String s1, String s2) {
        Object aobj[] = new Object[4];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s1;
        aobj[3] = s2;
        EventLog.writeEvent(2750, aobj);
    }

    public static void writePowerPartialWakeState(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(2729, aobj);
    }

    public static void writePowerScreenBroadcastDone(int i, long l, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Long.valueOf(l);
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(2726, aobj);
    }

    public static void writePowerScreenBroadcastSend(int i) {
        EventLog.writeEvent(2725, i);
    }

    public static void writePowerScreenBroadcastStop(int i, int j) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        EventLog.writeEvent(2727, aobj);
    }

    public static void writePowerScreenState(int i, int j, long l, int k) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Long.valueOf(l);
        aobj[3] = Integer.valueOf(k);
        EventLog.writeEvent(2728, aobj);
    }

    public static void writePowerSleepRequested(int i) {
        EventLog.writeEvent(2724, i);
    }

    public static void writeRestoreAgentFailure(String s, String s1) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = s1;
        EventLog.writeEvent(2832, aobj);
    }

    public static void writeRestorePackage(String s, int i) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        EventLog.writeEvent(2833, aobj);
    }

    public static void writeRestoreStart(String s, long l) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Long.valueOf(l);
        EventLog.writeEvent(2830, aobj);
    }

    public static void writeRestoreSuccess(int i, int j) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        EventLog.writeEvent(2834, aobj);
    }

    public static void writeRestoreTransportFailure() {
        EventLog.writeEvent(2831, new Object[0]);
    }

    public static void writeWatchdog(String s) {
        EventLog.writeEvent(2802, s);
    }

    public static void writeWatchdogHardReset(String s, int i, int j, int k) {
        Object aobj[] = new Object[4];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        aobj[3] = Integer.valueOf(k);
        EventLog.writeEvent(2805, aobj);
    }

    public static void writeWatchdogMeminfo(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            int i2, int j2, int k2) {
        Object aobj[] = new Object[11];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = Integer.valueOf(i1);
        aobj[5] = Integer.valueOf(j1);
        aobj[6] = Integer.valueOf(k1);
        aobj[7] = Integer.valueOf(l1);
        aobj[8] = Integer.valueOf(i2);
        aobj[9] = Integer.valueOf(j2);
        aobj[10] = Integer.valueOf(k2);
        EventLog.writeEvent(2809, aobj);
    }

    public static void writeWatchdogProcPss(String s, int i, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(2803, aobj);
    }

    public static void writeWatchdogProcStats(int i, int j, int k, int l, int i1) {
        Object aobj[] = new Object[5];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = Integer.valueOf(i1);
        EventLog.writeEvent(2807, aobj);
    }

    public static void writeWatchdogPssStats(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            int i2, int j2, int k2) {
        Object aobj[] = new Object[11];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = Integer.valueOf(i1);
        aobj[5] = Integer.valueOf(j1);
        aobj[6] = Integer.valueOf(k1);
        aobj[7] = Integer.valueOf(l1);
        aobj[8] = Integer.valueOf(i2);
        aobj[9] = Integer.valueOf(j2);
        aobj[10] = Integer.valueOf(k2);
        EventLog.writeEvent(2806, aobj);
    }

    public static void writeWatchdogRequestedReboot(int i, int j, int k, int l, int i1, int j1, int k1) {
        Object aobj[] = new Object[7];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = Integer.valueOf(i1);
        aobj[5] = Integer.valueOf(j1);
        aobj[6] = Integer.valueOf(k1);
        EventLog.writeEvent(2811, aobj);
    }

    public static void writeWatchdogScheduledReboot(long l, int i, int j, int k, String s) {
        Object aobj[] = new Object[5];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        aobj[3] = Integer.valueOf(k);
        aobj[4] = s;
        EventLog.writeEvent(2808, aobj);
    }

    public static void writeWatchdogSoftReset(String s, int i, int j, int k, String s1) {
        Object aobj[] = new Object[5];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        aobj[3] = Integer.valueOf(k);
        aobj[4] = s1;
        EventLog.writeEvent(2804, aobj);
    }

    public static void writeWatchdogVmstat(long l, int i, int j, int k, int i1, int j1) {
        Object aobj[] = new Object[6];
        aobj[0] = Long.valueOf(l);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        aobj[3] = Integer.valueOf(k);
        aobj[4] = Integer.valueOf(i1);
        aobj[5] = Integer.valueOf(j1);
        EventLog.writeEvent(2810, aobj);
    }

    public static void writeWmNoSurfaceMemory(String s, int i, String s1) {
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s1;
        EventLog.writeEvent(31000, aobj);
    }

    public static final int BACKUP_AGENT_FAILURE = 2823;
    public static final int BACKUP_DATA_CHANGED = 2820;
    public static final int BACKUP_INITIALIZE = 2827;
    public static final int BACKUP_PACKAGE = 2824;
    public static final int BACKUP_RESET = 2826;
    public static final int BACKUP_START = 2821;
    public static final int BACKUP_SUCCESS = 2825;
    public static final int BACKUP_TRANSPORT_FAILURE = 2822;
    public static final int BATTERY_DISCHARGE = 2730;
    public static final int BATTERY_LEVEL = 2722;
    public static final int BATTERY_STATUS = 2723;
    public static final int BOOT_PROGRESS_PMS_DATA_SCAN_START = 3080;
    public static final int BOOT_PROGRESS_PMS_READY = 3100;
    public static final int BOOT_PROGRESS_PMS_SCAN_END = 3090;
    public static final int BOOT_PROGRESS_PMS_START = 3060;
    public static final int BOOT_PROGRESS_PMS_SYSTEM_SCAN_START = 3070;
    public static final int BOOT_PROGRESS_SYSTEM_RUN = 3010;
    public static final int CACHE_FILE_DELETED = 2748;
    public static final int CONNECTIVITY_STATE_CHANGED = 50020;
    public static final int FREE_STORAGE_CHANGED = 2744;
    public static final int FREE_STORAGE_LEFT = 2746;
    public static final int IMF_FORCE_RECONNECT_IME = 32000;
    public static final int LOW_STORAGE = 2745;
    public static final int NETSTATS_MOBILE_SAMPLE = 51100;
    public static final int NETSTATS_WIFI_SAMPLE = 51101;
    public static final int NOTIFICATION_CANCEL = 2751;
    public static final int NOTIFICATION_CANCEL_ALL = 2752;
    public static final int NOTIFICATION_ENQUEUE = 2750;
    public static final int POWER_PARTIAL_WAKE_STATE = 2729;
    public static final int POWER_SCREEN_BROADCAST_DONE = 2726;
    public static final int POWER_SCREEN_BROADCAST_SEND = 2725;
    public static final int POWER_SCREEN_BROADCAST_STOP = 2727;
    public static final int POWER_SCREEN_STATE = 2728;
    public static final int POWER_SLEEP_REQUESTED = 2724;
    public static final int RESTORE_AGENT_FAILURE = 2832;
    public static final int RESTORE_PACKAGE = 2833;
    public static final int RESTORE_START = 2830;
    public static final int RESTORE_SUCCESS = 2834;
    public static final int RESTORE_TRANSPORT_FAILURE = 2831;
    public static final int WATCHDOG = 2802;
    public static final int WATCHDOG_HARD_RESET = 2805;
    public static final int WATCHDOG_MEMINFO = 2809;
    public static final int WATCHDOG_PROC_PSS = 2803;
    public static final int WATCHDOG_PROC_STATS = 2807;
    public static final int WATCHDOG_PSS_STATS = 2806;
    public static final int WATCHDOG_REQUESTED_REBOOT = 2811;
    public static final int WATCHDOG_SCHEDULED_REBOOT = 2808;
    public static final int WATCHDOG_SOFT_RESET = 2804;
    public static final int WATCHDOG_VMSTAT = 2810;
    public static final int WM_NO_SURFACE_MEMORY = 31000;
}
