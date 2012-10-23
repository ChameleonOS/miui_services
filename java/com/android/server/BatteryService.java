// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.*;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.app.IBatteryStats;
import com.android.server.am.BatteryStatsService;
import java.io.*;

// Referenced classes of package com.android.server:
//            LightsService

class BatteryService extends Binder {
    class Led {

        void updateLightsLocked() {
            int i = mBatteryLevel;
            int j = mBatteryStatus;
            if(i < mLowBatteryWarningLevel) {
                if(j == 2)
                    mBatteryLight.setColor(mBatteryLowARGB);
                else
                    mBatteryLight.setFlashing(mBatteryLowARGB, 1, mBatteryLedOn, mBatteryLedOff);
            } else
            if(j == 2 || j == 5) {
                if(j == 5 || i >= 90)
                    mBatteryLight.setColor(mBatteryFullARGB);
                else
                    mBatteryLight.setColor(mBatteryMediumARGB);
            } else {
                mBatteryLight.turnOff();
            }
        }

        private boolean mBatteryCharging;
        private boolean mBatteryFull;
        private int mBatteryFullARGB;
        private int mBatteryLedOff;
        private int mBatteryLedOn;
        private LightsService.Light mBatteryLight;
        private boolean mBatteryLow;
        private int mBatteryLowARGB;
        private int mBatteryMediumARGB;
        private LightsService mLightsService;
        final BatteryService this$0;

        Led(Context context, LightsService lightsservice) {
            this$0 = BatteryService.this;
            super();
            mLightsService = lightsservice;
            mBatteryLight = lightsservice.getLight(3);
            mBatteryLowARGB = mContext.getResources().getInteger(0x10e001c);
            mBatteryMediumARGB = mContext.getResources().getInteger(0x10e001d);
            mBatteryFullARGB = mContext.getResources().getInteger(0x10e001e);
            mBatteryLedOn = mContext.getResources().getInteger(0x10e001f);
            mBatteryLedOff = mContext.getResources().getInteger(0x10e0020);
        }
    }


    public BatteryService(Context context, LightsService lightsservice) {
        mLastPlugType = -1;
        mSentLowBatteryBroadcast = false;
        mPowerSupplyObserver = new UEventObserver() {

            public void onUEvent(android.os.UEventObserver.UEvent uevent) {
                update();
            }

            final BatteryService this$0;

             {
                this$0 = BatteryService.this;
                super();
            }
        };
        mInvalidChargerObserver = new UEventObserver() {

            public void onUEvent(android.os.UEventObserver.UEvent uevent) {
                int i;
                if("1".equals(uevent.get("SWITCH_STATE")))
                    i = 1;
                else
                    i = 0;
                if(mInvalidCharger != i) {
                    mInvalidCharger = i;
                    update();
                }
            }

            final BatteryService this$0;

             {
                this$0 = BatteryService.this;
                super();
            }
        };
        mContext = context;
        mLed = new Led(context, lightsservice);
        mCriticalBatteryLevel = mContext.getResources().getInteger(0x10e0017);
        mLowBatteryWarningLevel = mContext.getResources().getInteger(0x10e0018);
        mLowBatteryCloseWarningLevel = mContext.getResources().getInteger(0x10e0019);
        mPowerSupplyObserver.startObserving("SUBSYSTEM=power_supply");
        if((new File("/sys/devices/virtual/switch/invalid_charger/state")).exists())
            mInvalidChargerObserver.startObserving("DEVPATH=/devices/virtual/switch/invalid_charger");
        update();
    }

    private final int getIcon(int i) {
        int j = 0x1080526;
        if(mBatteryStatus != 2) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        if(mBatteryStatus == 3)
            j = 0x1080518;
        else
        if(mBatteryStatus == 4 || mBatteryStatus == 5) {
            if(!isPowered() || mBatteryLevel < 100)
                j = 0x1080518;
        } else {
            j = 0x1080534;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private final void logBatteryStats() {
        IBinder ibinder = ServiceManager.getService("batteryinfo");
        if(ibinder != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        DropBoxManager dropboxmanager = (DropBoxManager)mContext.getSystemService("dropbox");
        if(dropboxmanager == null || !dropboxmanager.isTagEnabled("BATTERY_DISCHARGE_INFO")) goto _L1; else goto _L3
_L3:
        File file;
        FileOutputStream fileoutputstream;
        file = null;
        fileoutputstream = null;
        File file1 = new File("/data/system/batteryinfo.dump");
        FileOutputStream fileoutputstream1 = new FileOutputStream(file1);
        ibinder.dump(fileoutputstream1.getFD(), DUMPSYS_ARGS);
        FileUtils.sync(fileoutputstream1);
        dropboxmanager.addFile("BATTERY_DISCHARGE_INFO", file1, 2);
        if(fileoutputstream1 != null)
            try {
                fileoutputstream1.close();
            }
            catch(IOException ioexception4) {
                Slog.e(TAG, "failed to close dumpsys output stream");
            }
        if(file1 != null && !file1.delete())
            Slog.e(TAG, (new StringBuilder()).append("failed to delete temporary dumpsys file: ").append(file1.getAbsolutePath()).toString());
          goto _L1
        RemoteException remoteexception;
        remoteexception;
_L7:
        Slog.e(TAG, "failed to dump battery service", remoteexception);
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception1) {
                Slog.e(TAG, "failed to close dumpsys output stream");
            }
        if(file != null && !file.delete())
            Slog.e(TAG, (new StringBuilder()).append("failed to delete temporary dumpsys file: ").append(file.getAbsolutePath()).toString());
          goto _L1
        IOException ioexception2;
        ioexception2;
_L6:
        Slog.e(TAG, "failed to write dumpsys file", ioexception2);
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception3) {
                Slog.e(TAG, "failed to close dumpsys output stream");
            }
        if(file != null && !file.delete())
            Slog.e(TAG, (new StringBuilder()).append("failed to delete temporary dumpsys file: ").append(file.getAbsolutePath()).toString());
          goto _L1
        Exception exception;
        exception;
_L5:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception) {
                Slog.e(TAG, "failed to close dumpsys output stream");
            }
        if(file != null && !file.delete())
            Slog.e(TAG, (new StringBuilder()).append("failed to delete temporary dumpsys file: ").append(file.getAbsolutePath()).toString());
        throw exception;
        exception;
        file = file1;
        continue; /* Loop/switch isn't completed */
        exception;
        fileoutputstream = fileoutputstream1;
        file = file1;
        if(true) goto _L5; else goto _L4
_L4:
        ioexception2;
        file = file1;
          goto _L6
        ioexception2;
        fileoutputstream = fileoutputstream1;
        file = file1;
          goto _L6
        remoteexception;
        file = file1;
          goto _L7
        remoteexception;
        fileoutputstream = fileoutputstream1;
        file = file1;
          goto _L7
    }

    private final void logOutlier(long l) {
        String s;
        String s1;
        android.content.ContentResolver contentresolver = mContext.getContentResolver();
        s = android.provider.Settings.Secure.getString(contentresolver, "battery_discharge_threshold");
        s1 = android.provider.Settings.Secure.getString(contentresolver, "battery_discharge_duration_threshold");
        if(s == null || s1 == null)
            break MISSING_BLOCK_LABEL_75;
        long l1 = Long.parseLong(s1);
        int i = Integer.parseInt(s);
        if(l <= l1 && mDischargeStartLevel - mBatteryLevel >= i)
            logBatteryStats();
_L1:
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        Slog.e(TAG, (new StringBuilder()).append("Invalid DischargeThresholds GService string: ").append(s1).append(" or ").append(s).toString());
          goto _L1
    }

    private native void native_update();

    private void processValues() {
        boolean flag = false;
        long l = 0L;
        boolean flag1;
        if(mBatteryLevel <= mCriticalBatteryLevel)
            flag1 = true;
        else
            flag1 = false;
        mBatteryLevelCritical = flag1;
        if(mAcOnline)
            mPlugType = 1;
        else
        if(mUsbOnline)
            mPlugType = 2;
        else
            mPlugType = 0;
        try {
            mBatteryStats.setBatteryState(mBatteryStatus, mBatteryHealth, mPlugType, mBatteryLevel, mBatteryTemperature, mBatteryVoltage);
        }
        catch(RemoteException remoteexception) { }
        shutdownIfNoPower();
        shutdownIfOverTemp();
        if(mBatteryStatus != mLastBatteryStatus || mBatteryHealth != mLastBatteryHealth || mBatteryPresent != mLastBatteryPresent || mBatteryLevel != mLastBatteryLevel || mPlugType != mLastPlugType || mBatteryVoltage != mLastBatteryVoltage || mBatteryTemperature != mLastBatteryTemperature || mInvalidCharger != mLastInvalidCharger) {
            boolean flag2;
            boolean flag3;
            boolean flag4;
            Intent intent;
            if(mPlugType != mLastPlugType)
                if(mLastPlugType == 0) {
                    if(mDischargeStartTime != 0L && mDischargeStartLevel != mBatteryLevel) {
                        l = SystemClock.elapsedRealtime() - mDischargeStartTime;
                        flag = true;
                        Object aobj2[] = new Object[3];
                        aobj2[0] = Long.valueOf(l);
                        aobj2[1] = Integer.valueOf(mDischargeStartLevel);
                        aobj2[2] = Integer.valueOf(mBatteryLevel);
                        EventLog.writeEvent(2730, aobj2);
                        mDischargeStartTime = 0L;
                    }
                } else
                if(mPlugType == 0) {
                    mDischargeStartTime = SystemClock.elapsedRealtime();
                    mDischargeStartLevel = mBatteryLevel;
                }
            if(mBatteryStatus != mLastBatteryStatus || mBatteryHealth != mLastBatteryHealth || mBatteryPresent != mLastBatteryPresent || mPlugType != mLastPlugType) {
                Object aobj[] = new Object[5];
                aobj[0] = Integer.valueOf(mBatteryStatus);
                aobj[1] = Integer.valueOf(mBatteryHealth);
                int i;
                Object aobj1[];
                if(mBatteryPresent)
                    i = 1;
                else
                    i = 0;
                aobj[2] = Integer.valueOf(i);
                aobj[3] = Integer.valueOf(mPlugType);
                aobj[4] = mBatteryTechnology;
                EventLog.writeEvent(2723, aobj);
            }
            if(mBatteryLevel != mLastBatteryLevel || mBatteryVoltage != mLastBatteryVoltage || mBatteryTemperature != mLastBatteryTemperature) {
                aobj1 = new Object[3];
                aobj1[0] = Integer.valueOf(mBatteryLevel);
                aobj1[1] = Integer.valueOf(mBatteryVoltage);
                aobj1[2] = Integer.valueOf(mBatteryTemperature);
                EventLog.writeEvent(2722, aobj1);
            }
            if(mBatteryLevelCritical && !mLastBatteryLevelCritical && mPlugType == 0) {
                l = SystemClock.elapsedRealtime() - mDischargeStartTime;
                flag = true;
            }
            if(mPlugType != 0)
                flag2 = true;
            else
                flag2 = false;
            if(mLastPlugType != 0)
                flag3 = true;
            else
                flag3 = false;
            if(!flag2 && mBatteryStatus != 1 && mBatteryLevel <= mLowBatteryWarningLevel && (flag3 || mLastBatteryLevel > mLowBatteryWarningLevel))
                flag4 = true;
            else
                flag4 = false;
            sendIntent();
            intent = new Intent();
            intent.setFlags(0x8000000);
            if(mPlugType != 0 && mLastPlugType == 0) {
                intent.setAction("android.intent.action.ACTION_POWER_CONNECTED");
                mContext.sendBroadcast(intent);
            } else
            if(mPlugType == 0 && mLastPlugType != 0) {
                intent.setAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                mContext.sendBroadcast(intent);
            }
            if(flag4) {
                mSentLowBatteryBroadcast = true;
                intent.setAction("android.intent.action.BATTERY_LOW");
                mContext.sendBroadcast(intent);
            } else
            if(mSentLowBatteryBroadcast && mLastBatteryLevel >= mLowBatteryCloseWarningLevel) {
                mSentLowBatteryBroadcast = false;
                intent.setAction("android.intent.action.BATTERY_OKAY");
                mContext.sendBroadcast(intent);
            }
            mLed.updateLightsLocked();
            if(flag && l != 0L)
                logOutlier(l);
            mLastBatteryStatus = mBatteryStatus;
            mLastBatteryHealth = mBatteryHealth;
            mLastBatteryPresent = mBatteryPresent;
            mLastBatteryLevel = mBatteryLevel;
            mLastPlugType = mPlugType;
            mLastBatteryVoltage = mBatteryVoltage;
            mLastBatteryTemperature = mBatteryTemperature;
            mLastBatteryLevelCritical = mBatteryLevelCritical;
            mLastInvalidCharger = mInvalidCharger;
        }
    }

    private final void sendIntent() {
        Intent intent = new Intent("android.intent.action.BATTERY_CHANGED");
        intent.addFlags(0x60000000);
        int i = getIcon(mBatteryLevel);
        intent.putExtra("status", mBatteryStatus);
        intent.putExtra("health", mBatteryHealth);
        intent.putExtra("present", mBatteryPresent);
        intent.putExtra("level", mBatteryLevel);
        intent.putExtra("scale", 100);
        intent.putExtra("icon-small", i);
        intent.putExtra("plugged", mPlugType);
        intent.putExtra("voltage", mBatteryVoltage);
        intent.putExtra("temperature", mBatteryTemperature);
        intent.putExtra("technology", mBatteryTechnology);
        intent.putExtra("invalid_charger", mInvalidCharger);
        ActivityManagerNative.broadcastStickyIntent(intent, null);
    }

    private final void shutdownIfNoPower() {
        if(mBatteryLevel == 0 && !isPowered() && ActivityManagerNative.isSystemReady()) {
            Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
            intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
            intent.setFlags(0x10000000);
            mContext.startActivity(intent);
        }
    }

    private final void shutdownIfOverTemp() {
        if(mBatteryTemperature > 680 && ActivityManagerNative.isSystemReady()) {
            Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
            intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
            intent.setFlags(0x10000000);
            mContext.startActivity(intent);
        }
    }

    /**
     * @deprecated Method update is deprecated
     */

    private final void update() {
        this;
        JVM INSTR monitorenter ;
        native_update();
        processValues();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump Battery service from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        if(as != null && as.length != 0 && !"-a".equals(as[0]))
            continue; /* Loop/switch isn't completed */
        this;
        JVM INSTR monitorenter ;
        printwriter.println("Current Battery Service state:");
        printwriter.println((new StringBuilder()).append("  AC powered: ").append(mAcOnline).toString());
        printwriter.println((new StringBuilder()).append("  USB powered: ").append(mUsbOnline).toString());
        printwriter.println((new StringBuilder()).append("  status: ").append(mBatteryStatus).toString());
        printwriter.println((new StringBuilder()).append("  health: ").append(mBatteryHealth).toString());
        printwriter.println((new StringBuilder()).append("  present: ").append(mBatteryPresent).toString());
        printwriter.println((new StringBuilder()).append("  level: ").append(mBatteryLevel).toString());
        printwriter.println("  scale: 100");
        printwriter.println((new StringBuilder()).append("  voltage:").append(mBatteryVoltage).toString());
        printwriter.println((new StringBuilder()).append("  temperature: ").append(mBatteryTemperature).toString());
        printwriter.println((new StringBuilder()).append("  technology: ").append(mBatteryTechnology).toString());
        if(true) goto _L4; else goto _L3
_L3:
    }

    final int getBatteryLevel() {
        return mBatteryLevel;
    }

    final int getPlugType() {
        return mPlugType;
    }

    final boolean isPowered() {
        boolean flag = true;
        if(!mAcOnline && !mUsbOnline && mBatteryStatus != flag)
            flag = false;
        return flag;
    }

    final boolean isPowered(int i) {
        boolean flag = true;
        if(mBatteryStatus != flag) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        if(i == 0) {
            flag = false;
        } else {
            int j = 0;
            if(mAcOnline)
                j = false | true;
            if(mUsbOnline)
                j |= 2;
            if((i & j) == 0)
                flag = false;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    void systemReady() {
        shutdownIfNoPower();
        shutdownIfOverTemp();
    }

    private static final int BATTERY_PLUGGED_NONE = 0;
    static final int BATTERY_SCALE = 100;
    private static final String BATTERY_STATS_SERVICE_NAME = "batteryinfo";
    private static final String DUMPSYS_ARGS[];
    private static final String DUMPSYS_DATA_PATH = "/data/system/";
    private static final int DUMP_MAX_LENGTH = 24576;
    private static final boolean LOCAL_LOGV;
    private static final String TAG = com/android/server/BatteryService.getSimpleName();
    private boolean mAcOnline;
    private int mBatteryHealth;
    private int mBatteryLevel;
    private boolean mBatteryLevelCritical;
    private boolean mBatteryPresent;
    private final IBatteryStats mBatteryStats = BatteryStatsService.getService();
    private int mBatteryStatus;
    private String mBatteryTechnology;
    private int mBatteryTemperature;
    private int mBatteryVoltage;
    private final Context mContext;
    private int mCriticalBatteryLevel;
    private int mDischargeStartLevel;
    private long mDischargeStartTime;
    private int mInvalidCharger;
    private UEventObserver mInvalidChargerObserver;
    private int mLastBatteryHealth;
    private int mLastBatteryLevel;
    private boolean mLastBatteryLevelCritical;
    private boolean mLastBatteryPresent;
    private int mLastBatteryStatus;
    private int mLastBatteryTemperature;
    private int mLastBatteryVoltage;
    private int mLastInvalidCharger;
    private int mLastPlugType;
    private Led mLed;
    private int mLowBatteryCloseWarningLevel;
    private int mLowBatteryWarningLevel;
    private int mPlugType;
    private UEventObserver mPowerSupplyObserver;
    private boolean mSentLowBatteryBroadcast;
    private boolean mUsbOnline;

    static  {
        String as[] = new String[2];
        as[0] = "--checkin";
        as[1] = "-u";
        DUMPSYS_ARGS = as;
    }




/*
    static int access$102(BatteryService batteryservice, int i) {
        batteryservice.mInvalidCharger = i;
        return i;
    }

*/




}
