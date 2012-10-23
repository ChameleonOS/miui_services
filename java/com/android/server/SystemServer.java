// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.os.SystemClock;
import android.util.Slog;
import com.android.internal.os.SamplingProfilerIntegration;
import dalvik.system.VMRuntime;
import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.android.server:
//            ServerThread

public class SystemServer {

    public SystemServer() {
    }

    public static native void init1(String as[]);

    public static final void init2() {
        Slog.i("SystemServer", "Entered the Android system server!");
        ServerThread serverthread = new ServerThread();
        serverthread.setName("android.server.ServerThread");
        serverthread.start();
    }

    public static void main(String args[]) {
        if(System.currentTimeMillis() < 0x5265c00L) {
            Slog.w("SystemServer", "System clock is before 1970; setting to 1970.");
            SystemClock.setCurrentTimeMillis(0x5265c00L);
        }
        if(SamplingProfilerIntegration.isEnabled()) {
            SamplingProfilerIntegration.start();
            timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {
                    SamplingProfilerIntegration.writeSnapshot("system_server", null);
                }

            }, 0x36ee80L, 0x36ee80L);
        }
        VMRuntime.getRuntime().clearGrowthLimit();
        VMRuntime.getRuntime().setTargetHeapUtilization(0.8F);
        System.loadLibrary("android_servers");
        init1(args);
    }

    private static final long EARLIEST_SUPPORTED_TIME = 0x5265c00L;
    public static final int FACTORY_TEST_HIGH_LEVEL = 2;
    public static final int FACTORY_TEST_LOW_LEVEL = 1;
    public static final int FACTORY_TEST_OFF = 0;
    static final long SNAPSHOT_INTERVAL = 0x36ee80L;
    private static final String TAG = "SystemServer";
    static Timer timer;
}
