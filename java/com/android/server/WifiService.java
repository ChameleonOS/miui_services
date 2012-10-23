// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.*;
import android.net.wifi.*;
import android.os.*;
import android.util.Slog;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.AsyncChannel;
import com.android.server.am.BatteryStatsService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WifiService extends android.net.wifi.IWifiManager.Stub {
    private class NotificationEnabledSettingObserver extends ContentObserver {

        private boolean getValue() {
            boolean flag = true;
            if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "wifi_networks_available_notification_on", flag) != flag)
                flag = false;
            return flag;
        }

        public void onChange(boolean flag) {
            super.onChange(flag);
            mNotificationEnabled = getValue();
            resetNotification();
        }

        public void register() {
            mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("wifi_networks_available_notification_on"), true, this);
            mNotificationEnabled = getValue();
        }

        final WifiService this$0;

        public NotificationEnabledSettingObserver(Handler handler) {
            this$0 = WifiService.this;
            super(handler);
        }
    }

    private class Multicaster extends DeathRecipient {

        public void binderDied() {
            Slog.e("WifiService", "Multicaster binderDied");
            List list = mMulticasters;
            list;
            JVM INSTR monitorenter ;
            int i = mMulticasters.indexOf(this);
            if(i != -1)
                removeMulticasterLocked(i, super.mMode);
            return;
        }

        public int getUid() {
            return super.mMode;
        }

        public String toString() {
            return (new StringBuilder()).append("Multicaster{").append(super.mTag).append(" binder=").append(super.mBinder).append("}").toString();
        }

        final WifiService this$0;

        Multicaster(String s, IBinder ibinder) {
            this$0 = WifiService.this;
            super(Binder.getCallingUid(), s, ibinder, null);
        }
    }

    private abstract class DeathRecipient
        implements android.os.IBinder.DeathRecipient {

        void unlinkDeathRecipient() {
            mBinder.unlinkToDeath(this, 0);
        }

        IBinder mBinder;
        int mMode;
        String mTag;
        WorkSource mWorkSource;
        final WifiService this$0;

        DeathRecipient(int i, String s, IBinder ibinder, WorkSource worksource) {
            this$0 = WifiService.this;
            super();
            mTag = s;
            mMode = i;
            mBinder = ibinder;
            mWorkSource = worksource;
            mBinder.linkToDeath(this, 0);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            binderDied();
              goto _L1
        }
    }

    private class LockList {

        private void addLock(WifiLock wifilock) {
            if(findLockByBinder(((DeathRecipient) (wifilock)).mBinder) < 0)
                mList.add(wifilock);
        }

        private void dump(PrintWriter printwriter) {
            WifiLock wifilock;
            for(Iterator iterator = mList.iterator(); iterator.hasNext(); printwriter.println(wifilock)) {
                wifilock = (WifiLock)iterator.next();
                printwriter.print("    ");
            }

        }

        private int findLockByBinder(IBinder ibinder) {
            int i = -1 + mList.size();
_L3:
            if(i < 0)
                break MISSING_BLOCK_LABEL_45;
            if(((DeathRecipient) ((WifiLock)mList.get(i))).mBinder != ibinder) goto _L2; else goto _L1
_L1:
            return i;
_L2:
            i--;
              goto _L3
            i = -1;
              goto _L1
        }

        /**
         * @deprecated Method getStrongestLockMode is deprecated
         */

        private int getStrongestLockMode() {
            int i = 1;
            this;
            JVM INSTR monitorenter ;
            boolean flag = mList.isEmpty();
            if(!flag) goto _L2; else goto _L1
_L1:
            this;
            JVM INSTR monitorexit ;
            return i;
_L2:
            int j;
            int k;
            if(mFullHighPerfLocksAcquired > mFullHighPerfLocksReleased) {
                i = 3;
                continue; /* Loop/switch isn't completed */
            }
            j = mFullLocksAcquired;
            k = mFullLocksReleased;
            if(j <= k)
                i = 2;
            if(true) goto _L1; else goto _L3
_L3:
            Exception exception;
            exception;
            throw exception;
        }

        /**
         * @deprecated Method hasLocks is deprecated
         */

        private boolean hasLocks() {
            this;
            JVM INSTR monitorenter ;
            boolean flag = mList.isEmpty();
            boolean flag1;
            if(!flag)
                flag1 = true;
            else
                flag1 = false;
            this;
            JVM INSTR monitorexit ;
            return flag1;
            Exception exception;
            exception;
            throw exception;
        }

        private WifiLock removeLock(IBinder ibinder) {
            int i = findLockByBinder(ibinder);
            WifiLock wifilock;
            if(i >= 0) {
                wifilock = (WifiLock)mList.remove(i);
                wifilock.unlinkDeathRecipient();
            } else {
                wifilock = null;
            }
            return wifilock;
        }

        private List mList;
        final WifiService this$0;








        private LockList() {
            this$0 = WifiService.this;
            super();
            mList = new ArrayList();
        }

    }

    private class WifiLock extends DeathRecipient {

        public void binderDied() {
            LockList locklist = mLocks;
            locklist;
            JVM INSTR monitorenter ;
            releaseWifiLockLocked(super.mBinder);
            return;
        }

        public String toString() {
            return (new StringBuilder()).append("WifiLock{").append(super.mTag).append(" type=").append(super.mMode).append(" binder=").append(super.mBinder).append("}").toString();
        }

        final WifiService this$0;

        WifiLock(int i, String s, IBinder ibinder, WorkSource worksource) {
            this$0 = WifiService.this;
            super(i, s, ibinder, worksource);
        }
    }

    private class WifiStateMachineHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR lookupswitch 2: default 32
        //                       69632: 58
        //                       69636: 120;
               goto _L1 _L2 _L3
_L1:
            Slog.d("WifiService", (new StringBuilder()).append("WifiStateMachineHandler.handleMessage ignoring msg=").append(message).toString());
_L5:
            return;
_L2:
            if(message.arg1 == 0) {
                mWifiStateMachineChannel = mWsmChannel;
            } else {
                Slog.e("WifiService", (new StringBuilder()).append("WifiStateMachine connection failure, error=").append(message.arg1).toString());
                mWifiStateMachineChannel = null;
            }
            continue; /* Loop/switch isn't completed */
_L3:
            Slog.e("WifiService", (new StringBuilder()).append("WifiStateMachine channel lost, msg.arg1 =").append(message.arg1).toString());
            mWifiStateMachineChannel = null;
            mWsmChannel.connect(mContext, this, mWifiStateMachine.getHandler());
            if(true) goto _L5; else goto _L4
_L4:
        }

        private AsyncChannel mWsmChannel;
        final WifiService this$0;

        WifiStateMachineHandler(Looper looper) {
            this$0 = WifiService.this;
            super(looper);
            mWsmChannel = new AsyncChannel();
            mWsmChannel.connect(mContext, this, mWifiStateMachine.getHandler());
        }
    }


    WifiService(Context context) {
        mEmergencyCallbackMode = false;
        mScanCount = new HashMap();
        mEnableTrafficStatsPoll = false;
        mTrafficStatsPollToken = 0;
        mPersistWifiState = new AtomicInteger(0);
        mAirplaneModeOn = new AtomicBoolean(false);
        mIsReceiverRegistered = false;
        mNetworkInfo = new NetworkInfo(1, 0, "WIFI", "");
        mClients = new ArrayList();
        mContext = context;
        mInterfaceName = SystemProperties.get("wifi.interface", "wlan0");
        mWifiStateMachine = new WifiStateMachine(mContext, mInterfaceName);
        mWifiStateMachine.enableRssiPolling(true);
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        Intent intent = new Intent("com.android.server.WifiManager.action.DEVICE_IDLE", null);
        mIdleIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent1) {
                mAirplaneModeOn.set(isAirplaneModeOn());
                handleAirplaneModeToggled(mAirplaneModeOn.get());
                updateWifiState();
            }

            final WifiService this$0;

             {
                this$0 = WifiService.this;
                super();
            }
        }, new IntentFilter("android.intent.action.AIRPLANE_MODE"));
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentfilter.addAction("android.net.wifi.STATE_CHANGE");
        intentfilter.addAction("android.net.wifi.SCAN_RESULTS");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent1) {
                boolean flag = true;
                if(!intent1.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) goto _L2; else goto _L1
_L1:
                int i = intent1.getIntExtra("wifi_state", flag);
                WifiService wifiservice = WifiService.this;
                if(i != 3)
                    flag = false;
                wifiservice.mWifiEnabled = flag;
                resetNotification();
_L4:
                return;
_L2:
                if(intent1.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                    mNetworkInfo = (NetworkInfo)intent1.getParcelableExtra("networkInfo");
                    class _cls4 {

                        static final int $SwitchMap$android$net$NetworkInfo$DetailedState[];

                        static  {
                            $SwitchMap$android$net$NetworkInfo$DetailedState = new int[android.net.NetworkInfo.DetailedState.values().length];
                            NoSuchFieldError nosuchfielderror1;
                            try {
                                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.CONNECTED.ordinal()] = 1;
                            }
                            catch(NoSuchFieldError nosuchfielderror) { }
                            $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.DISCONNECTED.ordinal()] = 2;
_L2:
                            return;
                            nosuchfielderror1;
                            if(true) goto _L2; else goto _L1
_L1:
                        }
                    }

                    switch(_cls4..SwitchMap.android.net.NetworkInfo.DetailedState[mNetworkInfo.getDetailedState().ordinal()]) {
                    case 1: // '\001'
                    case 2: // '\002'
                        evaluateTrafficStatsPolling();
                        resetNotification();
                        break;
                    }
                } else
                if(intent1.getAction().equals("android.net.wifi.SCAN_RESULTS"))
                    checkAndSetNotification();
                if(true) goto _L4; else goto _L3
_L3:
            }

            final WifiService this$0;

             {
                this$0 = WifiService.this;
                super();
            }
        }, intentfilter);
        HandlerThread handlerthread = new HandlerThread("WifiService");
        handlerthread.start();
        mAsyncServiceHandler = new AsyncServiceHandler(handlerthread.getLooper());
        mWifiStateMachineHandler = new WifiStateMachineHandler(handlerthread.getLooper());
        NOTIFICATION_REPEAT_DELAY_MS = 1000L * (long)android.provider.Settings.Secure.getInt(context.getContentResolver(), "wifi_networks_available_repeat_delay", 900);
        mNotificationEnabledSettingObserver = new NotificationEnabledSettingObserver(new Handler());
        mNotificationEnabledSettingObserver.register();
    }

    private boolean acquireWifiLockLocked(WifiLock wifilock) {
        long l;
        mLocks.addLock(wifilock);
        l = Binder.clearCallingIdentity();
        noteAcquireWifiLock(wifilock);
        ((DeathRecipient) (wifilock)).mMode;
        JVM INSTR tableswitch 1 3: default 48
    //                   1 66
    //                   2 101
    //                   3 79;
           goto _L1 _L2 _L3 _L4
_L1:
        reportStartWorkSource();
        updateWifiState();
        boolean flag = true;
_L5:
        Binder.restoreCallingIdentity(l);
        return flag;
_L2:
        mFullLocksAcquired = 1 + mFullLocksAcquired;
          goto _L1
_L4:
        mFullHighPerfLocksAcquired = 1 + mFullHighPerfLocksAcquired;
          goto _L1
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L3:
        mScanLocksAcquired = 1 + mScanLocksAcquired;
          goto _L1
        RemoteException remoteexception;
        remoteexception;
        flag = false;
          goto _L5
    }

    private void checkAndSetNotification() {
        if(mNotificationEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        android.net.NetworkInfo.State state = mNetworkInfo.getState();
        if(state == android.net.NetworkInfo.State.DISCONNECTED || state == android.net.NetworkInfo.State.UNKNOWN) {
            List list = mWifiStateMachine.syncGetScanResultsList();
            if(list != null) {
                int i = 0;
                for(int j = -1 + list.size(); j >= 0; j--) {
                    ScanResult scanresult = (ScanResult)list.get(j);
                    if(scanresult.capabilities != null && scanresult.capabilities.equals("[ESS]"))
                        i++;
                }

                if(i > 0) {
                    int k = 1 + mNumScansSinceNetworkStateChange;
                    mNumScansSinceNetworkStateChange = k;
                    if(k >= 3)
                        setNotificationVisible(true, i, false, 0);
                    continue; /* Loop/switch isn't completed */
                }
            }
        }
        setNotificationVisible(false, 0, false, 0);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void enforceAccessPermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE", "WifiService");
    }

    private void enforceChangePermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_WIFI_STATE", "WifiService");
    }

    private void enforceMulticastChangePermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_WIFI_MULTICAST_STATE", "WifiService");
    }

    private void evaluateTrafficStatsPolling() {
        Message message;
        if(mNetworkInfo.getDetailedState() == android.net.NetworkInfo.DetailedState.CONNECTED && !mScreenOff)
            message = Message.obtain(mAsyncServiceHandler, 0x25015, 1, 0);
        else
            message = Message.obtain(mAsyncServiceHandler, 0x25015, 0, 0);
        message.sendToTarget();
    }

    private int getPersistedWifiState() {
        int i;
        ContentResolver contentresolver;
        i = 0;
        contentresolver = mContext.getContentResolver();
        int j = android.provider.Settings.Secure.getInt(contentresolver, "wifi_on");
        i = j;
_L2:
        return i;
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        settingnotfoundexception;
        android.provider.Settings.Secure.putInt(contentresolver, "wifi_on", 0);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void handleAirplaneModeToggled(boolean flag) {
        if(!flag) goto _L2; else goto _L1
_L1:
        if(mWifiEnabled)
            persistWifiState(3);
_L4:
        return;
_L2:
        if(testAndClearWifiSavedState() || mPersistWifiState.get() == 2)
            persistWifiState(1);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleWifiToggled(boolean flag) {
        boolean flag1;
        if(mAirplaneModeOn.get() && isAirplaneToggleable())
            flag1 = true;
        else
            flag1 = false;
        if(flag) {
            if(flag1)
                persistWifiState(2);
            else
                persistWifiState(1);
        } else {
            persistWifiState(0);
        }
    }

    private boolean isAirplaneModeOn() {
        boolean flag = true;
        if(!isAirplaneSensitive() || android.provider.Settings.System.getInt(mContext.getContentResolver(), "airplane_mode_on", 0) != flag)
            flag = false;
        return flag;
    }

    private boolean isAirplaneSensitive() {
        String s = android.provider.Settings.System.getString(mContext.getContentResolver(), "airplane_mode_radios");
        boolean flag;
        if(s == null || s.contains("wifi"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isAirplaneToggleable() {
        String s = android.provider.Settings.System.getString(mContext.getContentResolver(), "airplane_mode_toggleable_radios");
        boolean flag;
        if(s != null && s.contains("wifi"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void noteAcquireWifiLock(WifiLock wifilock) throws RemoteException {
        ((DeathRecipient) (wifilock)).mMode;
        JVM INSTR tableswitch 1 3: default 32
    //                   1 33
    //                   2 49
    //                   3 33;
           goto _L1 _L2 _L3 _L2
_L1:
        return;
_L2:
        mBatteryStats.noteFullWifiLockAcquiredFromSource(((DeathRecipient) (wifilock)).mWorkSource);
        continue; /* Loop/switch isn't completed */
_L3:
        mBatteryStats.noteScanWifiLockAcquiredFromSource(((DeathRecipient) (wifilock)).mWorkSource);
        if(true) goto _L1; else goto _L4
_L4:
    }

    private void noteReleaseWifiLock(WifiLock wifilock) throws RemoteException {
        ((DeathRecipient) (wifilock)).mMode;
        JVM INSTR tableswitch 1 3: default 32
    //                   1 33
    //                   2 49
    //                   3 33;
           goto _L1 _L2 _L3 _L2
_L1:
        return;
_L2:
        mBatteryStats.noteFullWifiLockReleasedFromSource(((DeathRecipient) (wifilock)).mWorkSource);
        continue; /* Loop/switch isn't completed */
_L3:
        mBatteryStats.noteScanWifiLockReleasedFromSource(((DeathRecipient) (wifilock)).mWorkSource);
        if(true) goto _L1; else goto _L4
_L4:
    }

    private void notifyOnDataActivity() {
        long l = mTxPkts;
        long l1 = mRxPkts;
        int i = 0;
        mTxPkts = TrafficStats.getTxPackets(mInterfaceName);
        mRxPkts = TrafficStats.getRxPackets(mInterfaceName);
        if(l > 0L || l1 > 0L) {
            long l2 = mTxPkts - l;
            long l3 = mRxPkts - l1;
            if(l2 > 0L)
                i = 0 | 2;
            if(l3 > 0L)
                i |= 1;
            if(i != mDataActivity && !mScreenOff) {
                mDataActivity = i;
                for(Iterator iterator = mClients.iterator(); iterator.hasNext(); ((AsyncChannel)iterator.next()).sendMessage(1, mDataActivity));
            }
        }
    }

    private void persistWifiState(int i) {
        ContentResolver contentresolver = mContext.getContentResolver();
        mPersistWifiState.set(i);
        android.provider.Settings.Secure.putInt(contentresolver, "wifi_on", i);
    }

    private void registerForBroadcasts() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.SCREEN_ON");
        intentfilter.addAction("android.intent.action.SCREEN_OFF");
        intentfilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentfilter.addAction("com.android.server.WifiManager.action.DEVICE_IDLE");
        intentfilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intentfilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        mContext.registerReceiver(mReceiver, intentfilter);
    }

    private boolean releaseWifiLockLocked(IBinder ibinder) {
        long l;
        WifiLock wifilock = mLocks.removeLock(ibinder);
        boolean flag;
        if(wifilock != null)
            flag = true;
        else
            flag = false;
        l = Binder.clearCallingIdentity();
        if(!flag) goto _L2; else goto _L1
_L1:
        noteReleaseWifiLock(wifilock);
        ((DeathRecipient) (wifilock)).mMode;
        JVM INSTR tableswitch 1 3: default 60
    //                   1 76
    //                   2 112
    //                   3 89;
           goto _L2 _L3 _L4 _L5
_L2:
        updateWifiState();
_L6:
        Binder.restoreCallingIdentity(l);
        return flag;
_L3:
        mFullLocksReleased = 1 + mFullLocksReleased;
          goto _L2
_L5:
        mFullHighPerfLocksReleased = 1 + mFullHighPerfLocksReleased;
          goto _L2
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L4:
        mScanLocksReleased = 1 + mScanLocksReleased;
          goto _L2
        RemoteException remoteexception;
        remoteexception;
          goto _L6
    }

    private void removeMulticasterLocked(int i, int j) {
        Long long1;
        Multicaster multicaster = (Multicaster)mMulticasters.remove(i);
        if(multicaster != null)
            multicaster.unlinkDeathRecipient();
        if(mMulticasters.size() == 0)
            mWifiStateMachine.startFilteringMulticastV4Packets();
        long1 = Long.valueOf(Binder.clearCallingIdentity());
        mBatteryStats.noteWifiMulticastDisabled(j);
        long l = long1.longValue();
_L2:
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(long1.longValue());
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        l = long1.longValue();
        if(true) goto _L2; else goto _L1
_L1:
    }

    /**
     * @deprecated Method reportStartWorkSource is deprecated
     */

    private void reportStartWorkSource() {
        this;
        JVM INSTR monitorenter ;
        mTmpWorkSource.clear();
        if(mDeviceIdle) {
            for(int i = 0; i < mLocks.mList.size(); i++)
                mTmpWorkSource.add(((DeathRecipient) ((WifiLock)mLocks.mList.get(i))).mWorkSource);

        }
        mWifiStateMachine.updateBatteryWorkSource(mTmpWorkSource);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private void resetNotification() {
        mNotificationRepeatTime = 0L;
        mNumScansSinceNetworkStateChange = 0;
        setNotificationVisible(false, 0, false, 0);
    }

    private void setDeviceIdleAndUpdateWifi(boolean flag) {
        mDeviceIdle = flag;
        reportStartWorkSource();
        updateWifiState();
    }

    private void setNotificationVisible(boolean flag, int i, boolean flag1, int j) {
        if(flag || mNotificationShown || flag1) goto _L2; else goto _L1
_L1:
        return;
_L2:
        NotificationManager notificationmanager;
        notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        if(!flag)
            break; /* Loop/switch isn't completed */
        if(System.currentTimeMillis() < mNotificationRepeatTime)
            continue; /* Loop/switch isn't completed */
        if(mNotification == null) {
            mNotification = new Notification();
            mNotification.when = 0L;
            mNotification.icon = 0x1080516;
            mNotification.flags = 16;
            mNotification.contentIntent = PendingIntent.getActivity(mContext, 0, new Intent("android.net.wifi.PICK_WIFI_NETWORK"), 0);
        }
        CharSequence charsequence = mContext.getResources().getQuantityText(0x1130011, i);
        CharSequence charsequence1 = mContext.getResources().getQuantityText(0x1130012, i);
        mNotification.tickerText = charsequence;
        mNotification.setLatestEventInfo(mContext, charsequence, charsequence1, mNotification.contentIntent);
        mNotificationRepeatTime = System.currentTimeMillis() + NOTIFICATION_REPEAT_DELAY_MS;
        notificationmanager.notify(0x1080516, mNotification);
_L4:
        mNotificationShown = flag;
        if(true) goto _L1; else goto _L3
_L3:
        notificationmanager.cancel(0x1080516);
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    private boolean shouldWifiBeEnabled() {
        boolean flag = true;
        if(!mAirplaneModeOn.get()) goto _L2; else goto _L1
_L1:
        if(mPersistWifiState.get() != 2)
            flag = false;
_L4:
        return flag;
_L2:
        if(mPersistWifiState.get() == 0)
            flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean testAndClearWifiSavedState() {
        boolean flag = true;
        ContentResolver contentresolver = mContext.getContentResolver();
        int i = 0;
        try {
            i = android.provider.Settings.Secure.getInt(contentresolver, "wifi_saved_state");
            if(i == flag)
                android.provider.Settings.Secure.putInt(contentresolver, "wifi_saved_state", 0);
        }
        catch(android.provider.Settings.SettingNotFoundException settingnotfoundexception) { }
        if(i != flag)
            flag = false;
        return flag;
    }

    private void updateWifiState() {
        boolean flag = true;
        boolean flag1 = mLocks.hasLocks();
        int i = 1;
        boolean flag2;
        if(mEmergencyCallbackMode)
            flag2 = false;
        else
        if(!mDeviceIdle || flag1)
            flag2 = flag;
        else
            flag2 = false;
        if(flag1)
            i = mLocks.getStrongestLockMode();
        if(!mDeviceIdle && i == 2)
            i = 1;
        if(mAirplaneModeOn.get())
            mWifiStateMachine.setWifiApEnabled(null, false);
        if(shouldWifiBeEnabled()) {
            if(flag2) {
                reportStartWorkSource();
                mWifiStateMachine.setWifiEnabled(flag);
                WifiStateMachine wifistatemachine = mWifiStateMachine;
                boolean flag3;
                WifiStateMachine wifistatemachine1;
                if(i == 2)
                    flag3 = flag;
                else
                    flag3 = false;
                wifistatemachine.setScanOnlyMode(flag3);
                mWifiStateMachine.setDriverStart(flag, mEmergencyCallbackMode);
                wifistatemachine1 = mWifiStateMachine;
                if(i != 3)
                    flag = false;
                wifistatemachine1.setHighPerfModeEnabled(flag);
            } else {
                mWifiStateMachine.setDriverStart(false, mEmergencyCallbackMode);
            }
        } else {
            mWifiStateMachine.setWifiEnabled(false);
        }
    }

    public void acquireMulticastLock(IBinder ibinder, String s) {
        int i;
        Long long1;
        enforceMulticastChangePermission();
        synchronized(mMulticasters) {
            mMulticastEnabled = 1 + mMulticastEnabled;
            mMulticasters.add(new Multicaster(s, ibinder));
            mWifiStateMachine.stopFilteringMulticastV4Packets();
        }
        i = Binder.getCallingUid();
        long1 = Long.valueOf(Binder.clearCallingIdentity());
        mBatteryStats.noteWifiMulticastEnabled(i);
        long l = long1.longValue();
_L2:
        Binder.restoreCallingIdentity(l);
        return;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(long1.longValue());
        throw exception1;
        RemoteException remoteexception;
        remoteexception;
        l = long1.longValue();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean acquireWifiLock(IBinder ibinder, int i, String s, WorkSource worksource) {
        mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
        if(i == 1 || i == 2 || i == 3) goto _L2; else goto _L1
_L1:
        boolean flag;
        Slog.e("WifiService", (new StringBuilder()).append("Illegal argument, lockMode= ").append(i).toString());
        flag = false;
_L4:
        return flag;
_L2:
        WifiLock wifilock;
        if(worksource != null && worksource.size() == 0)
            worksource = null;
        if(worksource != null)
            enforceWakeSourcePermission(Binder.getCallingUid(), Binder.getCallingPid());
        if(worksource == null)
            worksource = new WorkSource(Binder.getCallingUid());
        wifilock = new WifiLock(i, s, ibinder, worksource);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        flag = acquireWifiLockLocked(wifilock);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int addOrUpdateNetwork(WifiConfiguration wificonfiguration) {
        enforceChangePermission();
        int i;
        if(mWifiStateMachineChannel != null) {
            i = mWifiStateMachine.syncAddOrUpdateNetwork(mWifiStateMachineChannel, wificonfiguration);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            i = -1;
        }
        return i;
    }

    public void addToBlacklist(String s) {
        enforceChangePermission();
        mWifiStateMachine.addToBlacklist(s);
    }

    public void checkAndStartWifi() {
        mAirplaneModeOn.set(isAirplaneModeOn());
        mPersistWifiState.set(getPersistedWifiState());
        boolean flag;
        StringBuilder stringbuilder;
        String s;
        if(shouldWifiBeEnabled() || testAndClearWifiSavedState())
            flag = true;
        else
            flag = false;
        stringbuilder = (new StringBuilder()).append("WifiService starting up with Wi-Fi ");
        if(flag)
            s = "enabled";
        else
            s = "disabled";
        Slog.i("WifiService", stringbuilder.append(s).toString());
        if(flag)
            setWifiEnabled(flag);
        mWifiWatchdogStateMachine = WifiWatchdogStateMachine.makeWifiWatchdogStateMachine(mContext);
    }

    public void clearBlacklist() {
        enforceChangePermission();
        mWifiStateMachine.clearBlacklist();
    }

    public boolean disableNetwork(int i) {
        enforceChangePermission();
        boolean flag;
        if(mWifiStateMachineChannel != null) {
            flag = mWifiStateMachine.syncDisableNetwork(mWifiStateMachineChannel, i);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            flag = false;
        }
        return flag;
    }

    public void disconnect() {
        enforceChangePermission();
        mWifiStateMachine.disconnectCommand();
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump WifiService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        printwriter.println((new StringBuilder()).append("Wi-Fi is ").append(mWifiStateMachine.syncGetWifiStateByName()).toString());
        printwriter.println((new StringBuilder()).append("Stay-awake conditions: ").append(android.provider.Settings.System.getInt(mContext.getContentResolver(), "stay_on_while_plugged_in", 0)).toString());
        printwriter.println();
        printwriter.println("Internal state:");
        printwriter.println(mWifiStateMachine);
        printwriter.println();
        printwriter.println("Latest scan results:");
        List list = mWifiStateMachine.syncGetScanResultsList();
        if(list != null && list.size() != 0) {
            printwriter.println("  BSSID              Frequency   RSSI  Flags             SSID");
            Iterator iterator1 = list.iterator();
            while(iterator1.hasNext())  {
                ScanResult scanresult = (ScanResult)iterator1.next();
                Object aobj[] = new Object[5];
                aobj[0] = scanresult.BSSID;
                aobj[1] = Integer.valueOf(scanresult.frequency);
                aobj[2] = Integer.valueOf(scanresult.level);
                aobj[3] = scanresult.capabilities;
                String s;
                if(scanresult.SSID == null)
                    s = "";
                else
                    s = scanresult.SSID;
                aobj[4] = s;
                printwriter.printf("  %17s  %9d  %5d  %-16s  %s%n", aobj);
            }
        }
        printwriter.println();
        printwriter.println((new StringBuilder()).append("Locks acquired: ").append(mFullLocksAcquired).append(" full, ").append(mFullHighPerfLocksAcquired).append(" full high perf, ").append(mScanLocksAcquired).append(" scan").toString());
        printwriter.println((new StringBuilder()).append("Locks released: ").append(mFullLocksReleased).append(" full, ").append(mFullHighPerfLocksReleased).append(" full high perf, ").append(mScanLocksReleased).append(" scan").toString());
        printwriter.println();
        printwriter.println("Locks held:");
        mLocks.dump(printwriter);
        printwriter.println("Scan count since last plugged in");
        HashMap hashmap = mScanCount;
        hashmap;
        JVM INSTR monitorenter ;
        int i;
        for(Iterator iterator = mScanCount.keySet().iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append("UID: ").append(i).append(" Scan count: ").append(mScanCount.get(Integer.valueOf(i))).toString()))
            i = ((Integer)iterator.next()).intValue();

        break MISSING_BLOCK_LABEL_553;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        printwriter.println();
        printwriter.println("WifiWatchdogStateMachine dump");
        mWifiWatchdogStateMachine.dump(printwriter);
        printwriter.println("WifiStateMachine dump");
        mWifiStateMachine.dump(filedescriptor, printwriter, as);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean enableNetwork(int i, boolean flag) {
        enforceChangePermission();
        boolean flag1;
        if(mWifiStateMachineChannel != null) {
            flag1 = mWifiStateMachine.syncEnableNetwork(mWifiStateMachineChannel, i, flag);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            flag1 = false;
        }
        return flag1;
    }

    void enforceWakeSourcePermission(int i, int j) {
        if(i != Process.myUid())
            mContext.enforcePermission("android.permission.UPDATE_DEVICE_STATS", j, i, null);
    }

    public String getConfigFile() {
        enforceAccessPermission();
        return mWifiStateMachine.getConfigFile();
    }

    public List getConfiguredNetworks() {
        enforceAccessPermission();
        List list;
        if(mWifiStateMachineChannel != null) {
            list = mWifiStateMachine.syncGetConfiguredNetworks(mWifiStateMachineChannel);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            list = null;
        }
        return list;
    }

    public WifiInfo getConnectionInfo() {
        enforceAccessPermission();
        return mWifiStateMachine.syncRequestConnectionInfo();
    }

    public DhcpInfo getDhcpInfo() {
        enforceAccessPermission();
        return mWifiStateMachine.syncGetDhcpInfo();
    }

    public int getFrequencyBand() {
        enforceAccessPermission();
        return mWifiStateMachine.getFrequencyBand();
    }

    public List getScanResults() {
        enforceAccessPermission();
        return mWifiStateMachine.syncGetScanResultsList();
    }

    public WifiConfiguration getWifiApConfiguration() {
        enforceAccessPermission();
        return mWifiStateMachine.syncGetWifiApConfiguration();
    }

    public int getWifiApEnabledState() {
        enforceAccessPermission();
        return mWifiStateMachine.syncGetWifiApState();
    }

    public int getWifiEnabledState() {
        enforceAccessPermission();
        return mWifiStateMachine.syncGetWifiState();
    }

    public Messenger getWifiServiceMessenger() {
        enforceAccessPermission();
        enforceChangePermission();
        return new Messenger(mAsyncServiceHandler);
    }

    public Messenger getWifiStateMachineMessenger() {
        enforceAccessPermission();
        enforceChangePermission();
        return mWifiStateMachine.getMessenger();
    }

    public void initializeMulticastFiltering() {
        enforceMulticastChangePermission();
        List list = mMulticasters;
        list;
        JVM INSTR monitorenter ;
        if(mMulticasters.size() == 0)
            mWifiStateMachine.startFilteringMulticastV4Packets();
        return;
    }

    public boolean isDualBandSupported() {
        return mContext.getResources().getBoolean(0x111000e);
    }

    public boolean isMulticastEnabled() {
        enforceAccessPermission();
        List list = mMulticasters;
        list;
        JVM INSTR monitorenter ;
        boolean flag;
        if(mMulticasters.size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean pingSupplicant() {
        enforceAccessPermission();
        boolean flag;
        if(mWifiStateMachineChannel != null) {
            flag = mWifiStateMachine.syncPingSupplicant(mWifiStateMachineChannel);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            flag = false;
        }
        return flag;
    }

    public void reassociate() {
        enforceChangePermission();
        mWifiStateMachine.reassociateCommand();
    }

    public void reconnect() {
        enforceChangePermission();
        mWifiStateMachine.reconnectCommand();
    }

    public void releaseMulticastLock() {
        int i;
        enforceMulticastChangePermission();
        i = Binder.getCallingUid();
        List list = mMulticasters;
        list;
        JVM INSTR monitorenter ;
        mMulticastDisabled = 1 + mMulticastDisabled;
        int j = -1 + mMulticasters.size();
        do {
            if(j >= 0) {
                Multicaster multicaster = (Multicaster)mMulticasters.get(j);
                if(multicaster != null && multicaster.getUid() == i)
                    removeMulticasterLocked(j, i);
            } else {
                return;
            }
            j--;
        } while(true);
    }

    public boolean releaseWifiLock(IBinder ibinder) {
        mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        boolean flag = releaseWifiLockLocked(ibinder);
        return flag;
    }

    public boolean removeNetwork(int i) {
        enforceChangePermission();
        boolean flag;
        if(mWifiStateMachineChannel != null) {
            flag = mWifiStateMachine.syncRemoveNetwork(mWifiStateMachineChannel, i);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            flag = false;
        }
        return flag;
    }

    public boolean saveConfiguration() {
        enforceChangePermission();
        boolean flag;
        if(mWifiStateMachineChannel != null) {
            flag = mWifiStateMachine.syncSaveConfig(mWifiStateMachineChannel);
        } else {
            Slog.e("WifiService", "mWifiStateMachineChannel is not initialized");
            flag = false;
        }
        return flag;
    }

    public void setCountryCode(String s, boolean flag) {
        Slog.i("WifiService", (new StringBuilder()).append("WifiService trying to set country code to ").append(s).append(" with persist set to ").append(flag).toString());
        enforceChangePermission();
        mWifiStateMachine.setCountryCode(s, flag);
    }

    public void setFrequencyBand(int i, boolean flag) {
        enforceChangePermission();
        if(isDualBandSupported()) {
            Slog.i("WifiService", (new StringBuilder()).append("WifiService trying to set frequency band to ").append(i).append(" with persist set to ").append(flag).toString());
            mWifiStateMachine.setFrequencyBand(i, flag);
        }
    }

    public void setWifiApConfiguration(WifiConfiguration wificonfiguration) {
        enforceChangePermission();
        if(wificonfiguration != null)
            mWifiStateMachine.setWifiApConfiguration(wificonfiguration);
    }

    public void setWifiApEnabled(WifiConfiguration wificonfiguration, boolean flag) {
        enforceChangePermission();
        mWifiStateMachine.setWifiApEnabled(wificonfiguration, flag);
    }

    /**
     * @deprecated Method setWifiEnabled is deprecated
     */

    public boolean setWifiEnabled(boolean flag) {
        this;
        JVM INSTR monitorenter ;
        enforceChangePermission();
        Slog.d("WifiService", (new StringBuilder()).append("setWifiEnabled: ").append(flag).append(" pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
        if(flag)
            reportStartWorkSource();
        mWifiStateMachine.setWifiEnabled(flag);
        long l = Binder.clearCallingIdentity();
        handleWifiToggled(flag);
        Binder.restoreCallingIdentity(l);
        if(!flag) goto _L2; else goto _L1
_L1:
        if(!mIsReceiverRegistered) {
            registerForBroadcasts();
            mIsReceiverRegistered = true;
        }
_L4:
        this;
        JVM INSTR monitorexit ;
        return true;
_L2:
        if(mIsReceiverRegistered) {
            mContext.unregisterReceiver(mReceiver);
            mIsReceiverRegistered = false;
        }
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void startScan(boolean flag) {
        enforceChangePermission();
        int i = Binder.getCallingUid();
        int j = 0;
        synchronized(mScanCount) {
            if(mScanCount.containsKey(Integer.valueOf(i)))
                j = ((Integer)mScanCount.get(Integer.valueOf(i))).intValue();
            mScanCount.put(Integer.valueOf(i), Integer.valueOf(j + 1));
        }
        mWifiStateMachine.startScan(flag);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void startWifi() {
        enforceChangePermission();
        mWifiStateMachine.setDriverStart(true, mEmergencyCallbackMode);
        mWifiStateMachine.reconnectCommand();
    }

    public void stopWifi() {
        enforceChangePermission();
        mWifiStateMachine.setDriverStart(false, mEmergencyCallbackMode);
    }

    public void updateWifiLockWorkSource(IBinder ibinder, WorkSource worksource) {
        int i;
        long l;
        i = Binder.getCallingUid();
        int j = Binder.getCallingPid();
        if(worksource != null && worksource.size() == 0)
            worksource = null;
        if(worksource != null)
            enforceWakeSourcePermission(i, j);
        l = Binder.clearCallingIdentity();
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        int k;
        k = mLocks.findLockByBinder(ibinder);
        if(k < 0)
            throw new IllegalArgumentException("Wifi lock not active");
        break MISSING_BLOCK_LABEL_89;
        Exception exception1;
        exception1;
        Exception exception;
        try {
            throw exception1;
        }
        catch(RemoteException remoteexception) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        return;
        WifiLock wifilock = (WifiLock)mLocks.mList.get(k);
        noteReleaseWifiLock(wifilock);
        WorkSource worksource1;
        if(worksource != null)
            worksource1 = new WorkSource(worksource);
        else
            worksource1 = new WorkSource(i);
        wifilock.mWorkSource = worksource1;
        noteAcquireWifiLock(wifilock);
        locklist;
        JVM INSTR monitorexit ;
        if(true)
            break MISSING_BLOCK_LABEL_83;
        throw exception;
    }

    private static final String ACTION_DEVICE_IDLE = "com.android.server.WifiManager.action.DEVICE_IDLE";
    private static final boolean DBG = false;
    private static final long DEFAULT_IDLE_MS = 0xdbba0L;
    private static final int ICON_NETWORKS_AVAILABLE = 0x1080516;
    private static final int IDLE_REQUEST = 0;
    private static final int NUM_SCANS_BEFORE_ACTUALLY_SCANNING = 3;
    private static final int POLL_TRAFFIC_STATS_INTERVAL_MSECS = 1000;
    private static final String TAG = "WifiService";
    private static final int WIFI_DISABLED = 0;
    private static final int WIFI_DISABLED_AIRPLANE_ON = 3;
    private static final int WIFI_ENABLED = 1;
    private static final int WIFI_ENABLED_AIRPLANE_OVERRIDE = 2;
    private final long NOTIFICATION_REPEAT_DELAY_MS;
    private AtomicBoolean mAirplaneModeOn;
    private AlarmManager mAlarmManager;
    private AsyncServiceHandler mAsyncServiceHandler;
    private final IBatteryStats mBatteryStats = BatteryStatsService.getService();
    private List mClients;
    private Context mContext;
    private int mDataActivity;
    private boolean mDeviceIdle;
    private boolean mEmergencyCallbackMode;
    private boolean mEnableTrafficStatsPoll;
    private int mFullHighPerfLocksAcquired;
    private int mFullHighPerfLocksReleased;
    private int mFullLocksAcquired;
    private int mFullLocksReleased;
    private PendingIntent mIdleIntent;
    private String mInterfaceName;
    private boolean mIsReceiverRegistered;
    private final LockList mLocks = new LockList();
    private int mMulticastDisabled;
    private int mMulticastEnabled;
    private final List mMulticasters = new ArrayList();
    NetworkInfo mNetworkInfo;
    private Notification mNotification;
    private boolean mNotificationEnabled;
    private NotificationEnabledSettingObserver mNotificationEnabledSettingObserver;
    private long mNotificationRepeatTime;
    private boolean mNotificationShown;
    private int mNumScansSinceNetworkStateChange;
    private AtomicInteger mPersistWifiState;
    private int mPluggedType;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        private boolean shouldDeviceStayAwake(int i, int j) {
            boolean flag;
            if((i & j) != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        private boolean shouldWifiStayAwake(int i, int j) {
            boolean flag;
            int k;
            flag = true;
            k = android.provider.Settings.System.getInt(mContext.getContentResolver(), "wifi_sleep_policy", 2);
            break MISSING_BLOCK_LABEL_20;
            if(k != 2 && (k != flag || j == 0))
                flag = shouldDeviceStayAwake(i, j);
            return flag;
        }

        public void onReceive(Context context1, Intent intent1) {
            String s;
            long l;
            int i;
            s = intent1.getAction();
            l = android.provider.Settings.Secure.getLong(mContext.getContentResolver(), "wifi_idle_ms", 0xdbba0L);
            i = android.provider.Settings.System.getInt(mContext.getContentResolver(), "stay_on_while_plugged_in", 0);
            if(!s.equals("android.intent.action.SCREEN_ON")) goto _L2; else goto _L1
_L1:
            mAlarmManager.cancel(mIdleIntent);
            mScreenOff = false;
            evaluateTrafficStatsPolling();
            setDeviceIdleAndUpdateWifi(false);
_L4:
            return;
_L2:
            if(s.equals("android.intent.action.SCREEN_OFF")) {
                mScreenOff = true;
                evaluateTrafficStatsPolling();
                if(!shouldWifiStayAwake(i, mPluggedType))
                    if(mNetworkInfo.getDetailedState() == android.net.NetworkInfo.DetailedState.CONNECTED)
                        mAlarmManager.set(0, l + System.currentTimeMillis(), mIdleIntent);
                    else
                        setDeviceIdleAndUpdateWifi(true);
            } else
            if(s.equals("com.android.server.WifiManager.action.DEVICE_IDLE"))
                setDeviceIdleAndUpdateWifi(true);
            else
            if(s.equals("android.intent.action.BATTERY_CHANGED")) {
                int k = intent1.getIntExtra("plugged", 0);
                if(mScreenOff && shouldWifiStayAwake(i, mPluggedType) && !shouldWifiStayAwake(i, k)) {
                    long l1 = l + System.currentTimeMillis();
                    mAlarmManager.set(0, l1, mIdleIntent);
                }
                if(k == 0)
                    synchronized(mScanCount) {
                        mScanCount.clear();
                    }
                mPluggedType = k;
            } else
            if(s.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")) {
                int j = intent1.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 0);
                mWifiStateMachine.sendBluetoothAdapterStateChange(j);
            } else
            if(s.equals("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED")) {
                mEmergencyCallbackMode = intent1.getBooleanExtra("phoneinECMState", false);
                updateWifiState();
            }
            continue; /* Loop/switch isn't completed */
            exception;
            hashmap;
            JVM INSTR monitorexit ;
            throw exception;
            if(true) goto _L4; else goto _L3
_L3:
        }

        final WifiService this$0;

             {
                this$0 = WifiService.this;
                super();
            }
    };
    private long mRxPkts;
    private HashMap mScanCount;
    private int mScanLocksAcquired;
    private int mScanLocksReleased;
    private boolean mScreenOff;
    private final WorkSource mTmpWorkSource = new WorkSource();
    private int mTrafficStatsPollToken;
    private long mTxPkts;
    private boolean mWifiEnabled;
    private final WifiStateMachine mWifiStateMachine;
    private AsyncChannel mWifiStateMachineChannel;
    WifiStateMachineHandler mWifiStateMachineHandler;
    private WifiWatchdogStateMachine mWifiWatchdogStateMachine;





/*
    static boolean access$1202(WifiService wifiservice, boolean flag) {
        wifiservice.mWifiEnabled = flag;
        return flag;
    }

*/








/*
    static boolean access$1802(WifiService wifiservice, boolean flag) {
        wifiservice.mScreenOff = flag;
        return flag;
    }

*/





/*
    static int access$2002(WifiService wifiservice, int i) {
        wifiservice.mPluggedType = i;
        return i;
    }

*/



/*
    static boolean access$2202(WifiService wifiservice, boolean flag) {
        wifiservice.mEmergencyCallbackMode = flag;
        return flag;
    }

*/







/*
    static boolean access$302(WifiService wifiservice, boolean flag) {
        wifiservice.mEnableTrafficStatsPoll = flag;
        return flag;
    }

*/






/*
    static boolean access$3802(WifiService wifiservice, boolean flag) {
        wifiservice.mNotificationEnabled = flag;
        return flag;
    }

*/



/*
    static int access$408(WifiService wifiservice) {
        int i = wifiservice.mTrafficStatsPollToken;
        wifiservice.mTrafficStatsPollToken = i + 1;
        return i;
    }

*/




/*
    static AsyncChannel access$702(WifiService wifiservice, AsyncChannel asyncchannel) {
        wifiservice.mWifiStateMachineChannel = asyncchannel;
        return asyncchannel;
    }

*/


}
