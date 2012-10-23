// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.bluetooth.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.*;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.internal.app.IBatteryStats;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.PowerProfile;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class BatteryStatsService extends com.android.internal.app.IBatteryStats.Stub {

    BatteryStatsService(String s) {
        mBluetoothProfileServiceListener = new android.bluetooth.BluetoothProfile.ServiceListener() {

            public void onServiceConnected(int i, BluetoothProfile bluetoothprofile) {
                mBluetoothHeadset = (BluetoothHeadset)bluetoothprofile;
                BatteryStatsImpl batterystatsimpl = mStats;
                batterystatsimpl;
                JVM INSTR monitorenter ;
                if(mBluetoothPendingStats) {
                    mStats.noteBluetoothOnLocked();
                    mStats.setBtHeadset(mBluetoothHeadset);
                    mBluetoothPendingStats = false;
                }
                return;
            }

            public void onServiceDisconnected(int i) {
                mBluetoothHeadset = null;
            }

            final BatteryStatsService this$0;

             {
                this$0 = BatteryStatsService.this;
                super();
            }
        };
        mStats = new BatteryStatsImpl(s);
    }

    private void dumpHelp(PrintWriter printwriter) {
        printwriter.println("Battery stats (batteryinfo) dump options:");
        printwriter.println("  [--checkin] [--reset] [--write] [-h]");
        printwriter.println("  --checkin: format output for a checkin report.");
        printwriter.println("  --reset: reset the stats, clearing all current data.");
        printwriter.println("  --write: force write current collected stats to disk.");
        printwriter.println("  -h: print this help text.");
    }

    public static IBatteryStats getService() {
        IBatteryStats ibatterystats;
        if(sService != null) {
            ibatterystats = sService;
        } else {
            sService = asInterface(ServiceManager.getService("batteryinfo"));
            ibatterystats = sService;
        }
        return ibatterystats;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump BatteryStats from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L7:
        return;
_L2:
        boolean flag;
        boolean flag1;
        int i;
        int j;
        flag = false;
        flag1 = false;
        if(as == null)
            break MISSING_BLOCK_LABEL_266;
        i = as.length;
        j = 0;
_L4:
        String s;
        if(j >= i)
            break MISSING_BLOCK_LABEL_266;
        s = as[j];
        if(!"--checkin".equals(s))
            break; /* Loop/switch isn't completed */
        flag = true;
_L5:
        j++;
        if(true) goto _L4; else goto _L3
_L3:
        if(!"--reset".equals(s))
            break MISSING_BLOCK_LABEL_157;
        BatteryStatsImpl batterystatsimpl3 = mStats;
        batterystatsimpl3;
        JVM INSTR monitorenter ;
        mStats.resetAllStatsLocked();
        printwriter.println("Battery stats reset.");
        flag1 = true;
          goto _L5
        if(!"--write".equals(s))
            break MISSING_BLOCK_LABEL_206;
        BatteryStatsImpl batterystatsimpl2 = mStats;
        batterystatsimpl2;
        JVM INSTR monitorenter ;
        mStats.writeSyncLocked();
        printwriter.println("Battery stats written.");
        flag1 = true;
          goto _L5
        if("-h".equals(s)) {
            dumpHelp(printwriter);
            continue; /* Loop/switch isn't completed */
        }
        if(!"-a".equals(s)) {
            printwriter.println((new StringBuilder()).append("Unknown option: ").append(s).toString());
            dumpHelp(printwriter);
        }
          goto _L5
        java.util.List list;
        if(flag1)
            continue; /* Loop/switch isn't completed */
        if(!flag)
            break MISSING_BLOCK_LABEL_323;
        list = mContext.getPackageManager().getInstalledApplications(0);
        BatteryStatsImpl batterystatsimpl1 = mStats;
        batterystatsimpl1;
        JVM INSTR monitorenter ;
        mStats.dumpCheckinLocked(printwriter, as, list);
        continue; /* Loop/switch isn't completed */
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.dumpLocked(printwriter);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void enforceCallingPermission() {
        if(Binder.getCallingPid() != Process.myPid())
            mContext.enforcePermission("android.permission.UPDATE_DEVICE_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
    }

    public BatteryStatsImpl getActiveStatistics() {
        return mStats;
    }

    public long getAwakeTimeBattery() {
        mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        return mStats.getAwakeTimeBattery();
    }

    public long getAwakeTimePlugged() {
        mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        return mStats.getAwakeTimePlugged();
    }

    public byte[] getStatistics() {
        mContext.enforceCallingPermission("android.permission.BATTERY_STATS", null);
        Parcel parcel = Parcel.obtain();
        mStats.writeToParcel(parcel, 0);
        byte abyte0[] = parcel.marshall();
        parcel.recycle();
        return abyte0;
    }

    public boolean isOnBattery() {
        return mStats.isOnBattery();
    }

    public void noteBluetoothOff() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mBluetoothPendingStats = false;
        mStats.noteBluetoothOffLocked();
        return;
    }

    public void noteBluetoothOn() {
        enforceCallingPermission();
        BluetoothAdapter bluetoothadapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothadapter != null)
            bluetoothadapter.getProfileProxy(mContext, mBluetoothProfileServiceListener, 1);
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        if(mBluetoothHeadset != null) {
            mStats.noteBluetoothOnLocked();
            mStats.setBtHeadset(mBluetoothHeadset);
        } else {
            mBluetoothPendingStats = true;
        }
        return;
    }

    public void noteFullWifiLockAcquired(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteFullWifiLockAcquiredLocked(i);
        return;
    }

    public void noteFullWifiLockAcquiredFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteFullWifiLockAcquiredFromSourceLocked(worksource);
        return;
    }

    public void noteFullWifiLockReleased(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteFullWifiLockReleasedLocked(i);
        return;
    }

    public void noteFullWifiLockReleasedFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteFullWifiLockReleasedFromSourceLocked(worksource);
        return;
    }

    public void noteInputEvent() {
        enforceCallingPermission();
        mStats.noteInputEventAtomic();
    }

    public void noteNetworkInterfaceType(String s, int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteNetworkInterfaceTypeLocked(s, i);
        return;
    }

    public void notePhoneDataConnectionState(int i, boolean flag) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.notePhoneDataConnectionStateLocked(i, flag);
        return;
    }

    public void notePhoneOff() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.notePhoneOffLocked();
        return;
    }

    public void notePhoneOn() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.notePhoneOnLocked();
        return;
    }

    public void notePhoneSignalStrength(SignalStrength signalstrength) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.notePhoneSignalStrengthLocked(signalstrength);
        return;
    }

    public void notePhoneState(int i) {
        int j;
        enforceCallingPermission();
        j = TelephonyManager.getDefault().getSimState();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.notePhoneStateLocked(i, j);
        return;
    }

    public void noteScanWifiLockAcquired(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScanWifiLockAcquiredLocked(i);
        return;
    }

    public void noteScanWifiLockAcquiredFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScanWifiLockAcquiredFromSourceLocked(worksource);
        return;
    }

    public void noteScanWifiLockReleased(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScanWifiLockReleasedLocked(i);
        return;
    }

    public void noteScanWifiLockReleasedFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScanWifiLockReleasedFromSourceLocked(worksource);
        return;
    }

    public void noteScreenBrightness(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScreenBrightnessLocked(i);
        return;
    }

    public void noteScreenOff() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScreenOffLocked();
        return;
    }

    public void noteScreenOn() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteScreenOnLocked();
        return;
    }

    public void noteStartAudio(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteAudioOnLocked(i);
        return;
    }

    public void noteStartGps(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStartGpsLocked(i);
        return;
    }

    public void noteStartSensor(int i, int j) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStartSensorLocked(i, j);
        return;
    }

    public void noteStartVideo(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteVideoOnLocked(i);
        return;
    }

    public void noteStartWakelock(int i, int j, String s, int k) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStartWakeLocked(i, j, s, k);
        return;
    }

    public void noteStartWakelockFromSource(WorkSource worksource, int i, String s, int j) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStartWakeFromSourceLocked(worksource, i, s, j);
        return;
    }

    public void noteStopAudio(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteAudioOffLocked(i);
        return;
    }

    public void noteStopGps(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStopGpsLocked(i);
        return;
    }

    public void noteStopSensor(int i, int j) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStopSensorLocked(i, j);
        return;
    }

    public void noteStopVideo(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteVideoOffLocked(i);
        return;
    }

    public void noteStopWakelock(int i, int j, String s, int k) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStopWakeLocked(i, j, s, k);
        return;
    }

    public void noteStopWakelockFromSource(WorkSource worksource, int i, String s, int j) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteStopWakeFromSourceLocked(worksource, i, s, j);
        return;
    }

    public void noteUserActivity(int i, int j) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteUserActivityLocked(i, j);
        return;
    }

    public void noteWifiMulticastDisabled(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiMulticastDisabledLocked(i);
        return;
    }

    public void noteWifiMulticastDisabledFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiMulticastDisabledFromSourceLocked(worksource);
        return;
    }

    public void noteWifiMulticastEnabled(int i) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiMulticastEnabledLocked(i);
        return;
    }

    public void noteWifiMulticastEnabledFromSource(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiMulticastEnabledFromSourceLocked(worksource);
        return;
    }

    public void noteWifiOff() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiOffLocked();
        return;
    }

    public void noteWifiOn() {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiOnLocked();
        return;
    }

    public void noteWifiRunning(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiRunningLocked(worksource);
        return;
    }

    public void noteWifiRunningChanged(WorkSource worksource, WorkSource worksource1) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiRunningChangedLocked(worksource, worksource1);
        return;
    }

    public void noteWifiStopped(WorkSource worksource) {
        enforceCallingPermission();
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.noteWifiStoppedLocked(worksource);
        return;
    }

    public void publish(Context context) {
        mContext = context;
        ServiceManager.addService("batteryinfo", asBinder());
        mStats.setNumSpeedSteps((new PowerProfile(mContext)).getNumSpeedSteps());
        mStats.setRadioScanningTimeout(1000L * (long)mContext.getResources().getInteger(0x10e0008));
    }

    public void setBatteryState(int i, int j, int k, int l, int i1, int j1) {
        enforceCallingPermission();
        mStats.setBatteryState(i, j, k, l, i1, j1);
    }

    public void shutdown() {
        Slog.w("BatteryStats", "Writing battery stats before shutdown...");
        BatteryStatsImpl batterystatsimpl = mStats;
        batterystatsimpl;
        JVM INSTR monitorenter ;
        mStats.shutdownLocked();
        return;
    }

    static IBatteryStats sService;
    private BluetoothHeadset mBluetoothHeadset;
    private boolean mBluetoothPendingStats;
    private android.bluetooth.BluetoothProfile.ServiceListener mBluetoothProfileServiceListener;
    Context mContext;
    final BatteryStatsImpl mStats;



/*
    static BluetoothHeadset access$002(BatteryStatsService batterystatsservice, BluetoothHeadset bluetoothheadset) {
        batterystatsservice.mBluetoothHeadset = bluetoothheadset;
        return bluetoothheadset;
    }

*/



/*
    static boolean access$102(BatteryStatsService batterystatsservice, boolean flag) {
        batterystatsservice.mBluetoothPendingStats = flag;
        return flag;
    }

*/
}
