// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.util.EventLog;

public class EventLogTags {

    private EventLogTags() {
    }

    public static void writeActivityLaunchTime(int i, String s, long l) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Long.valueOf(l);
        EventLog.writeEvent(30009, aobj);
    }

    public static void writeAmAnr(int i, String s, int j, String s1) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = s1;
        EventLog.writeEvent(30008, aobj);
    }

    public static void writeAmBroadcastDiscardApp(int i, String s, int j, String s1) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = s1;
        EventLog.writeEvent(30025, aobj);
    }

    public static void writeAmBroadcastDiscardFilter(int i, String s, int j, int k) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = Integer.valueOf(k);
        EventLog.writeEvent(30024, aobj);
    }

    public static void writeAmCrash(int i, String s, int j, String s1, String s2, String s3, int k) {
        Object aobj[] = new Object[7];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = s1;
        aobj[4] = s2;
        aobj[5] = s3;
        aobj[6] = Integer.valueOf(k);
        EventLog.writeEvent(30039, aobj);
    }

    public static void writeAmCreateActivity(int i, int j, String s, String s1, String s2, String s3, int k) {
        Object aobj[] = new Object[7];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        aobj[3] = s1;
        aobj[4] = s2;
        aobj[5] = s3;
        aobj[6] = Integer.valueOf(k);
        EventLog.writeEvent(30005, aobj);
    }

    public static void writeAmCreateService(int i, String s, String s1, int j) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = s1;
        aobj[3] = Integer.valueOf(j);
        EventLog.writeEvent(30030, aobj);
    }

    public static void writeAmCreateTask(int i) {
        EventLog.writeEvent(30004, i);
    }

    public static void writeAmDestroyActivity(int i, int j, String s, String s1) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        aobj[3] = s1;
        EventLog.writeEvent(30018, aobj);
    }

    public static void writeAmDestroyService(int i, String s, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(30031, aobj);
    }

    public static void writeAmDropProcess(int i) {
        EventLog.writeEvent(30033, i);
    }

    public static void writeAmFailedToPause(int i, String s, String s1) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = s1;
        EventLog.writeEvent(30012, aobj);
    }

    public static void writeAmFinishActivity(int i, int j, String s, String s1) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        aobj[3] = s1;
        EventLog.writeEvent(30001, aobj);
    }

    public static void writeAmKill(int i, String s, int j, String s1) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = s1;
        EventLog.writeEvent(30023, aobj);
    }

    public static void writeAmLowMemory(int i) {
        EventLog.writeEvent(30017, i);
    }

    public static void writeAmNewIntent(int i, int j, String s, String s1, String s2, String s3, int k) {
        Object aobj[] = new Object[7];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        aobj[3] = s1;
        aobj[4] = s2;
        aobj[5] = s3;
        aobj[6] = Integer.valueOf(k);
        EventLog.writeEvent(30003, aobj);
    }

    public static void writeAmOnPausedCalled(String s) {
        EventLog.writeEvent(30021, s);
    }

    public static void writeAmOnResumeCalled(String s) {
        EventLog.writeEvent(30022, s);
    }

    public static void writeAmPauseActivity(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(30013, aobj);
    }

    public static void writeAmProcBad(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(30015, aobj);
    }

    public static void writeAmProcBound(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(30010, aobj);
    }

    public static void writeAmProcDied(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(30011, aobj);
    }

    public static void writeAmProcGood(int i, String s) {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        EventLog.writeEvent(30016, aobj);
    }

    public static void writeAmProcStart(int i, int j, String s, String s1, String s2) {
        Object aobj[] = new Object[5];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        aobj[3] = s1;
        aobj[4] = s2;
        EventLog.writeEvent(30014, aobj);
    }

    public static void writeAmProcessCrashedTooMuch(String s, int i) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        EventLog.writeEvent(30032, aobj);
    }

    public static void writeAmProcessStartTimeout(int i, int j, String s) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        EventLog.writeEvent(30037, aobj);
    }

    public static void writeAmProviderLostProcess(String s, int i, String s1) {
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s1;
        EventLog.writeEvent(30036, aobj);
    }

    public static void writeAmRelaunchActivity(int i, int j, String s) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        EventLog.writeEvent(30020, aobj);
    }

    public static void writeAmRelaunchResumeActivity(int i, int j, String s) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        EventLog.writeEvent(30019, aobj);
    }

    public static void writeAmRestartActivity(int i, int j, String s) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        EventLog.writeEvent(30006, aobj);
    }

    public static void writeAmResumeActivity(int i, int j, String s) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = s;
        EventLog.writeEvent(30007, aobj);
    }

    public static void writeAmScheduleServiceRestart(String s, long l) {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Long.valueOf(l);
        EventLog.writeEvent(30035, aobj);
    }

    public static void writeAmServiceCrashedTooMuch(int i, String s, int j) {
        Object aobj[] = new Object[3];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(30034, aobj);
    }

    public static void writeAmTaskToFront(int i) {
        EventLog.writeEvent(30002, i);
    }

    public static void writeAmWtf(int i, String s, int j, String s1, String s2) {
        Object aobj[] = new Object[5];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = s;
        aobj[2] = Integer.valueOf(j);
        aobj[3] = s1;
        aobj[4] = s2;
        EventLog.writeEvent(30040, aobj);
    }

    public static void writeBootProgressAmsReady(long l) {
        EventLog.writeEvent(3040, l);
    }

    public static void writeBootProgressEnableScreen(long l) {
        EventLog.writeEvent(3050, l);
    }

    public static void writeConfigurationChanged(int i) {
        EventLog.writeEvent(2719, i);
    }

    public static void writeCpu(int i, int j, int k, int l, int i1, int j1) {
        Object aobj[] = new Object[6];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Integer.valueOf(k);
        aobj[3] = Integer.valueOf(l);
        aobj[4] = Integer.valueOf(i1);
        aobj[5] = Integer.valueOf(j1);
        EventLog.writeEvent(2721, aobj);
    }

    public static final int ACTIVITY_LAUNCH_TIME = 30009;
    public static final int AM_ANR = 30008;
    public static final int AM_BROADCAST_DISCARD_APP = 30025;
    public static final int AM_BROADCAST_DISCARD_FILTER = 30024;
    public static final int AM_CRASH = 30039;
    public static final int AM_CREATE_ACTIVITY = 30005;
    public static final int AM_CREATE_SERVICE = 30030;
    public static final int AM_CREATE_TASK = 30004;
    public static final int AM_DESTROY_ACTIVITY = 30018;
    public static final int AM_DESTROY_SERVICE = 30031;
    public static final int AM_DROP_PROCESS = 30033;
    public static final int AM_FAILED_TO_PAUSE = 30012;
    public static final int AM_FINISH_ACTIVITY = 30001;
    public static final int AM_KILL = 30023;
    public static final int AM_LOW_MEMORY = 30017;
    public static final int AM_NEW_INTENT = 30003;
    public static final int AM_ON_PAUSED_CALLED = 30021;
    public static final int AM_ON_RESUME_CALLED = 30022;
    public static final int AM_PAUSE_ACTIVITY = 30013;
    public static final int AM_PROCESS_CRASHED_TOO_MUCH = 30032;
    public static final int AM_PROCESS_START_TIMEOUT = 30037;
    public static final int AM_PROC_BAD = 30015;
    public static final int AM_PROC_BOUND = 30010;
    public static final int AM_PROC_DIED = 30011;
    public static final int AM_PROC_GOOD = 30016;
    public static final int AM_PROC_START = 30014;
    public static final int AM_PROVIDER_LOST_PROCESS = 30036;
    public static final int AM_RELAUNCH_ACTIVITY = 30020;
    public static final int AM_RELAUNCH_RESUME_ACTIVITY = 30019;
    public static final int AM_RESTART_ACTIVITY = 30006;
    public static final int AM_RESUME_ACTIVITY = 30007;
    public static final int AM_SCHEDULE_SERVICE_RESTART = 30035;
    public static final int AM_SERVICE_CRASHED_TOO_MUCH = 30034;
    public static final int AM_TASK_TO_FRONT = 30002;
    public static final int AM_WTF = 30040;
    public static final int BOOT_PROGRESS_AMS_READY = 3040;
    public static final int BOOT_PROGRESS_ENABLE_SCREEN = 3050;
    public static final int CONFIGURATION_CHANGED = 2719;
    public static final int CPU = 2721;
}
