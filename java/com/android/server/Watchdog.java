// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.PendingIntent;
import android.content.*;
import android.os.*;
import android.util.*;
import com.android.server.am.ActivityManagerService;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

// Referenced classes of package com.android.server:
//            AlarmManagerService, PowerManagerService, BatteryService

public class Watchdog extends Thread {
    public static interface Monitor {

        public abstract void monitor();
    }

    final class RebootRequestReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            Watchdog watchdog = Watchdog.this;
            boolean flag;
            Object aobj[];
            int i;
            if(intent.getIntExtra("nowait", 0) != 0)
                flag = true;
            else
                flag = false;
            watchdog.mReqRebootNoWait = flag;
            mReqRebootInterval = intent.getIntExtra("interval", -1);
            mReqRebootStartTime = intent.getIntExtra("startTime", -1);
            mReqRebootWindow = intent.getIntExtra("window", -1);
            mReqMinScreenOff = intent.getIntExtra("minScreenOff", -1);
            mReqMinNextAlarm = intent.getIntExtra("minNextAlarm", -1);
            mReqRecheckInterval = intent.getIntExtra("recheckInterval", -1);
            aobj = new Object[7];
            if(mReqRebootNoWait)
                i = 1;
            else
                i = 0;
            aobj[0] = Integer.valueOf(i);
            aobj[1] = Integer.valueOf(mReqRebootInterval);
            aobj[2] = Integer.valueOf(mReqRecheckInterval);
            aobj[3] = Integer.valueOf(mReqRebootStartTime);
            aobj[4] = Integer.valueOf(mReqRebootWindow);
            aobj[5] = Integer.valueOf(mReqMinScreenOff);
            aobj[6] = Integer.valueOf(mReqMinNextAlarm);
            EventLog.writeEvent(2811, aobj);
            checkReboot(true);
        }

        final Watchdog this$0;

        RebootRequestReceiver() {
            this$0 = Watchdog.this;
            super();
        }
    }

    final class RebootReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            checkReboot(true);
        }

        final Watchdog this$0;

        RebootReceiver() {
            this$0 = Watchdog.this;
            super();
        }
    }

    final class HeartbeatHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 2718 2718: default 24
        //                       2718 25;
               goto _L1 _L2
_L1:
            return;
_L2:
            int i;
            int j;
            if(mReqRebootInterval >= 0)
                i = mReqRebootInterval;
            else
                i = android.provider.Settings.Secure.getInt(mResolver, "reboot_interval", 0);
            if(mRebootInterval != i) {
                mRebootInterval = i;
                checkReboot(false);
            }
            j = mMonitors.size();
            for(int k = 0; k < j; k++) {
                mCurrentMonitor = (Monitor)mMonitors.get(k);
                mCurrentMonitor.monitor();
            }

            Watchdog watchdog = Watchdog.this;
            watchdog;
            JVM INSTR monitorenter ;
            mCompleted = true;
            mCurrentMonitor = null;
            if(true) goto _L1; else goto _L3
_L3:
        }

        final Watchdog this$0;

        HeartbeatHandler() {
            this$0 = Watchdog.this;
            super();
        }
    }


    private Watchdog() {
        super("watchdog");
        mMinScreenOff = 300;
        mMinAlarm = 180;
        mReqRebootInterval = -1;
        mReqRebootStartTime = -1;
        mReqRebootWindow = -1;
        mReqMinScreenOff = -1;
        mReqMinNextAlarm = -1;
        mReqRecheckInterval = -1;
    }

    static long computeCalendarTime(Calendar calendar, long l, long l1) {
        calendar.setTimeInMillis(l);
        int i = (int)l1 / 3600;
        calendar.set(11, i);
        long l2 = l1 - (long)(i * 3600);
        int j = (int)l2 / 60;
        calendar.set(12, j);
        calendar.set(13, (int)l2 - j * 60);
        calendar.set(14, 0);
        long l3 = calendar.getTimeInMillis();
        if(l3 < l) {
            calendar.add(5, 1);
            l3 = calendar.getTimeInMillis();
        }
        return l3;
    }

    private File dumpKernelStackTraces() {
        File file = null;
        String s = SystemProperties.get("dalvik.vm.stack-trace-file", null);
        if(s != null && s.length() != 0) {
            native_dumpKernelStacks(s);
            file = new File(s);
        }
        return file;
    }

    public static Watchdog getInstance() {
        if(sWatchdog == null)
            sWatchdog = new Watchdog();
        return sWatchdog;
    }

    private native void native_dumpKernelStacks(String s);

    public void addMonitor(Monitor monitor) {
        this;
        JVM INSTR monitorenter ;
        if(isAlive())
            throw new RuntimeException("Monitors can't be added while the Watchdog is running");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        mMonitors.add(monitor);
        this;
        JVM INSTR monitorexit ;
    }

    void checkReboot(boolean flag) {
        int i;
        if(mReqRebootInterval >= 0)
            i = mReqRebootInterval;
        else
            i = android.provider.Settings.Secure.getInt(mResolver, "reboot_interval", 0);
        mRebootInterval = i;
        if(i > 0) goto _L2; else goto _L1
_L1:
        mAlarm.remove(mRebootIntent);
_L5:
        return;
_L2:
        long l;
        long l2;
        long l4;
        long l5;
        long l6;
        long l7;
        long l1;
        long l3;
        Exception exception;
        Object aobj1[];
        if(mReqRebootStartTime >= 0)
            l = mReqRebootStartTime;
        else
            l = android.provider.Settings.Secure.getLong(mResolver, "reboot_start_time", 10800L);
        if(mReqRebootWindow >= 0)
            l1 = mReqRebootWindow;
        else
            l1 = android.provider.Settings.Secure.getLong(mResolver, "reboot_window", 3600L);
        l2 = l1 * 1000L;
        if(mReqRecheckInterval >= 0)
            l3 = mReqRecheckInterval;
        else
            l3 = android.provider.Settings.Secure.getLong(mResolver, "memcheck_recheck_interval", 300L);
        l4 = l3 * 1000L;
        retrieveBrutalityAmount();
        this;
        JVM INSTR monitorenter ;
        l5 = System.currentTimeMillis();
        l6 = computeCalendarTime(mCalendar, l5, l);
        l7 = 1000 * (60 * (60 * (i * 24)));
        if(!mReqRebootNoWait && l5 - mBootTime < l7 - l2) goto _L4; else goto _L3
_L3:
        if(!flag || l2 > 0L)
            break MISSING_BLOCK_LABEL_313;
        aobj1 = new Object[5];
        aobj1[0] = Long.valueOf(l5);
        aobj1[1] = Integer.valueOf((int)l7);
        aobj1[2] = Integer.valueOf(1000 * (int)l);
        aobj1[3] = Integer.valueOf((int)l2);
        aobj1[4] = "";
        EventLog.writeEvent(2808, aobj1);
        rebootSystem("Checkin scheduled forced");
          goto _L5
        exception;
        throw exception;
        if(l5 >= l6) goto _L7; else goto _L6
_L6:
        l6 = computeCalendarTime(mCalendar, l5, l);
_L4:
        this;
        JVM INSTR monitorexit ;
        mAlarm.remove(mRebootIntent);
        mAlarm.set(0, l6, mRebootIntent);
          goto _L5
_L7:
        if(l5 >= l6 + l2) goto _L9; else goto _L8
_L8:
        String s;
        Object aobj[];
        String s1;
        s = shouldWeBeBrutalLocked(l5);
        aobj = new Object[5];
        aobj[0] = Long.valueOf(l5);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(1000 * (int)l);
        aobj[3] = Integer.valueOf((int)l2);
        if(s == null)
            break MISSING_BLOCK_LABEL_523;
        s1 = s;
_L12:
        aobj[4] = s1;
        EventLog.writeEvent(2808, aobj);
        if(s != null) goto _L11; else goto _L10
_L10:
        rebootSystem("Checked scheduled range");
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L11:
        if(l5 + l4 < l6 + l2)
            break MISSING_BLOCK_LABEL_530;
        l6 = computeCalendarTime(mCalendar, l5 + l7, l);
          goto _L4
_L9:
        long l8 = computeCalendarTime(mCalendar, l5 + l7, l);
        l6 = l8;
          goto _L4
        s1 = "";
          goto _L12
        l6 = l5 + l4;
          goto _L4
    }

    public void init(Context context, BatteryService batteryservice, PowerManagerService powermanagerservice, AlarmManagerService alarmmanagerservice, ActivityManagerService activitymanagerservice) {
        mResolver = context.getContentResolver();
        mBattery = batteryservice;
        mPower = powermanagerservice;
        mAlarm = alarmmanagerservice;
        mActivity = activitymanagerservice;
        context.registerReceiver(new RebootReceiver(), new IntentFilter("com.android.service.Watchdog.REBOOT"));
        mRebootIntent = PendingIntent.getBroadcast(context, 0, new Intent("com.android.service.Watchdog.REBOOT"), 0);
        context.registerReceiver(new RebootRequestReceiver(), new IntentFilter("android.intent.action.REBOOT"), "android.permission.REBOOT", null);
        mBootTime = System.currentTimeMillis();
    }

    public void processStarted(String s, int i) {
        this;
        JVM INSTR monitorenter ;
        if("com.android.phone".equals(s))
            mPhonePid = i;
        return;
    }

    void rebootSystem(String s) {
        Slog.i("Watchdog", (new StringBuilder()).append("Rebooting system because: ").append(s).toString());
        ((PowerManagerService)ServiceManager.getService("power")).reboot(s);
    }

    void retrieveBrutalityAmount() {
        int i;
        int j;
        if(mReqMinScreenOff >= 0)
            i = mReqMinScreenOff;
        else
            i = android.provider.Settings.Secure.getInt(mResolver, "memcheck_min_screen_off", 300);
        mMinScreenOff = i * 1000;
        if(mReqMinNextAlarm >= 0)
            j = mReqMinNextAlarm;
        else
            j = android.provider.Settings.Secure.getInt(mResolver, "memcheck_min_alarm", 180);
        mMinAlarm = j * 1000;
    }

    public void run() {
        boolean flag = false;
_L4:
        mCompleted = false;
        mHandler.sendEmptyMessage(2718);
        this;
        JVM INSTR monitorenter ;
        long l = 30000L;
        long l1 = SystemClock.uptimeMillis();
_L2:
        boolean flag2;
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_85;
        flag2 = mForceKillSystem;
        Exception exception;
        if(flag2)
            break MISSING_BLOCK_LABEL_85;
        try {
            wait(l);
        }
        catch(InterruptedException interruptedexception1) {
            Log.wtf("Watchdog", interruptedexception1);
        }
        finally {
            this;
        }
        l = 30000L - (SystemClock.uptimeMillis() - l1);
        if(true) goto _L2; else goto _L1
_L1:
        JVM INSTR monitorexit ;
        throw exception;
        if(!mCompleted || mForceKillSystem)
            break MISSING_BLOCK_LABEL_106;
        flag = false;
        this;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        if(flag)
            break MISSING_BLOCK_LABEL_150;
        ArrayList arraylist = new ArrayList();
        arraylist.add(Integer.valueOf(Process.myPid()));
        ActivityManagerService.dumpStackTraces(true, arraylist, null, null, NATIVE_STACKS_OF_INTEREST);
        flag = true;
        this;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        this;
        JVM INSTR monitorexit ;
        String s;
        ArrayList arraylist1;
        boolean flag1;
        File file;
        Thread thread;
        if(mCurrentMonitor != null)
            s = mCurrentMonitor.getClass().getName();
        else
            s = "null";
        EventLog.writeEvent(2802, s);
        arraylist1 = new ArrayList();
        arraylist1.add(Integer.valueOf(Process.myPid()));
        if(mPhonePid > 0)
            arraylist1.add(Integer.valueOf(mPhonePid));
        if(!flag)
            flag1 = true;
        else
            flag1 = false;
        file = ActivityManagerService.dumpStackTraces(flag1, arraylist1, null, null, NATIVE_STACKS_OF_INTEREST);
        SystemClock.sleep(2000L);
        dumpKernelStackTraces();
        thread = new Thread(file) {

            public void run() {
                mActivity.addErrorToDropBox("watchdog", null, "system_server", null, null, name, null, stack, null);
            }

            final Watchdog this$0;
            final String val$name;
            final File val$stack;

             {
                this$0 = Watchdog.this;
                name = s1;
                stack = file;
                super(final_s);
            }
        };
        thread.start();
        try {
            thread.join(2000L);
        }
        catch(InterruptedException interruptedexception) { }
        if(!Debug.isDebuggerConnected()) {
            Slog.w("Watchdog", (new StringBuilder()).append("*** WATCHDOG KILLING SYSTEM PROCESS: ").append(s).toString());
            Process.killProcess(Process.myPid());
            System.exit(10);
        } else {
            Slog.w("Watchdog", "Debugger connected: Watchdog is *not* killing the system process");
        }
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    String shouldWeBeBrutalLocked(long l) {
        String s;
        if(mBattery == null || !mBattery.isPowered())
            s = "battery";
        else
        if(mMinScreenOff >= 0 && (mPower == null || mPower.timeSinceScreenOn() < (long)mMinScreenOff))
            s = "screen";
        else
        if(mMinAlarm >= 0 && (mAlarm == null || mAlarm.timeToNextAlarm() < (long)mMinAlarm))
            s = "alarm";
        else
            s = null;
        return s;
    }

    static final boolean DB = false;
    static final int MEMCHECK_DEFAULT_MIN_ALARM = 180;
    static final int MEMCHECK_DEFAULT_MIN_SCREEN_OFF = 300;
    static final int MEMCHECK_DEFAULT_RECHECK_INTERVAL = 300;
    static final int MONITOR = 2718;
    static final String NATIVE_STACKS_OF_INTEREST[];
    static final String REBOOT_ACTION = "com.android.service.Watchdog.REBOOT";
    static final int REBOOT_DEFAULT_INTERVAL = 0;
    static final int REBOOT_DEFAULT_START_TIME = 10800;
    static final int REBOOT_DEFAULT_WINDOW = 3600;
    static final boolean RECORD_KERNEL_THREADS = true;
    static final String TAG = "Watchdog";
    static final int TIME_TO_RESTART = 60000;
    static final int TIME_TO_WAIT = 30000;
    static final boolean localLOGV;
    static Watchdog sWatchdog;
    ActivityManagerService mActivity;
    AlarmManagerService mAlarm;
    BatteryService mBattery;
    long mBootTime;
    final Calendar mCalendar = Calendar.getInstance();
    PendingIntent mCheckupIntent;
    boolean mCompleted;
    Monitor mCurrentMonitor;
    boolean mForceKillSystem;
    final Handler mHandler = new HeartbeatHandler();
    int mMinAlarm;
    int mMinScreenOff;
    final ArrayList mMonitors = new ArrayList();
    boolean mNeedScheduledCheck;
    int mPhonePid;
    PowerManagerService mPower;
    PendingIntent mRebootIntent;
    int mRebootInterval;
    int mReqMinNextAlarm;
    int mReqMinScreenOff;
    int mReqRebootInterval;
    boolean mReqRebootNoWait;
    int mReqRebootStartTime;
    int mReqRebootWindow;
    int mReqRecheckInterval;
    ContentResolver mResolver;

    static  {
        String as[] = new String[3];
        as[0] = "/system/bin/mediaserver";
        as[1] = "/system/bin/sdcard";
        as[2] = "/system/bin/surfaceflinger";
        NATIVE_STACKS_OF_INTEREST = as;
    }
}
