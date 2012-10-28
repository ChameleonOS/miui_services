// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.location.*;
import android.os.*;
import android.text.format.Time;
import android.util.Slog;
import com.android.internal.app.DisableCarModeActivity;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.android.server:
//            TwilightCalculator

class UiModeManagerService extends android.app.IUiModeManager.Stub {
    static class Injector {

        static void registerUIModeScaleChangedOjbserver(UiModeManagerService uimodemanagerservice, Context context) {
            ContentObserver contentobserver = new ContentObserver(context) {

                public void onChange(boolean flag) {
                    Object obj = service.mLock;
                    obj;
                    JVM INSTR monitorenter ;
                    service.mNormalType = android.provider.Settings.System.getInt(context.getContentResolver(), "ui_mode_scale", 1);
                    return;
                }

                final Context val$context;
                final UiModeManagerService val$service;

                 {
                    service = uimodemanagerservice;
                    context = context1;
                    super(final_handler);
                }
            };
            context.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("ui_mode_scale"), false, contentobserver);
            contentobserver.onChange(false);
        }

        Injector() {
        }
    }


    public UiModeManagerService(Context context) {
        boolean flag = true;
        super();
        mNormalType = ((flag) ? 1 : 0);
        mDockState = 0;
        mLastBroadcastState = 0;
        mNightMode = ((flag) ? 1 : 0);
        mCarModeEnabled = false;
        mCharging = false;
        mCurUiMode = 0;
        mSetUiMode = 0;
        mHoldingConfiguration = false;
        mConfiguration = new Configuration();
        mContext = context;
        ServiceManager.addService("uimode", this);
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        mLocationManager = (LocationManager)mContext.getSystemService("location");
        mContext.registerReceiver(mTwilightUpdateReceiver, new IntentFilter("com.android.server.action.UPDATE_NIGHT_MODE"));
        mContext.registerReceiver(mDockModeReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
        mContext.registerReceiver(mBatteryReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        IntentFilter intentfilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        intentfilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        mContext.registerReceiver(mUpdateLocationReceiver, intentfilter);
        Injector.registerUIModeScaleChangedOjbserver(this, mContext);
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(26, TAG);
        mConfiguration.setToDefaults();
        mDefaultUiModeType = context.getResources().getInteger(0x10e0011);
        boolean flag1;
        if(context.getResources().getInteger(0x10e0013) == flag)
            flag1 = flag;
        else
            flag1 = false;
        mCarModeKeepsScreenOn = flag1;
        if(context.getResources().getInteger(0x10e0012) != flag)
            flag = false;
        mDeskModeKeepsScreenOn = flag;
        mTelevision = context.getPackageManager().hasSystemFeature("android.hardware.type.television");
        mNightMode = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "ui_night_mode", 0);
    }

    private void adjustStatusBarCarModeLocked() {
        if(mStatusBarManager == null)
            mStatusBarManager = (StatusBarManager)mContext.getSystemService("statusbar");
        if(mStatusBarManager != null) {
            StatusBarManager statusbarmanager = mStatusBarManager;
            Intent intent;
            Notification notification;
            int i;
            if(mCarModeEnabled)
                i = 0x80000;
            else
                i = 0;
            statusbarmanager.disable(i);
        }
        if(mNotificationManager == null)
            mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
        if(mNotificationManager != null)
            if(mCarModeEnabled) {
                intent = new Intent(mContext, com/android/internal/app/DisableCarModeActivity);
                notification = new Notification();
                notification.icon = 0x108050f;
                notification.defaults = 4;
                notification.flags = 2;
                notification.when = 0L;
                notification.setLatestEventInfo(mContext, mContext.getString(0x1040483), mContext.getString(0x1040484), PendingIntent.getActivity(mContext, 0, intent, 0));
                mNotificationManager.notify(0, notification);
            } else {
                mNotificationManager.cancel(0);
            }
    }

    static Intent buildHomeIntent(String s) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(s);
        intent.setFlags(0x10200000);
        return intent;
    }

    static final boolean isDeskDockState(int i) {
        i;
        JVM INSTR tableswitch 1 4: default 32
    //                   1 36
    //                   2 32
    //                   3 36
    //                   4 36;
           goto _L1 _L2 _L1 _L2 _L2
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void disableCarMode(int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        setCarModeLocked(false);
        if(mSystemReady)
            updateLocked(0, i);
        return;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump uimode service from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("Current UI Mode Service state:");
        printwriter.print("  mDockState=");
        printwriter.print(mDockState);
        printwriter.print(" mLastBroadcastState=");
        printwriter.println(mLastBroadcastState);
        printwriter.print("  mNightMode=");
        printwriter.print(mNightMode);
        printwriter.print(" mCarModeEnabled=");
        printwriter.print(mCarModeEnabled);
        printwriter.print(" mComputedNightMode=");
        printwriter.println(mComputedNightMode);
        printwriter.print("  mCurUiMode=0x");
        printwriter.print(Integer.toHexString(mCurUiMode));
        printwriter.print(" mSetUiMode=0x");
        printwriter.println(Integer.toHexString(mSetUiMode));
        printwriter.print("  mHoldingConfiguration=");
        printwriter.print(mHoldingConfiguration);
        printwriter.print(" mSystemReady=");
        printwriter.println(mSystemReady);
        if(mLocation != null) {
            printwriter.print("  mLocation=");
            printwriter.println(mLocation);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void enableCarMode(int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        setCarModeLocked(true);
        if(mSystemReady)
            updateLocked(i, 0);
        return;
    }

    public int getCurrentModeType() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = 0xf & mCurUiMode;
        return i;
    }

    public int getNightMode() throws RemoteException {
        return mNightMode;
    }

    boolean isDoingNightMode() {
        boolean flag;
        if(mCarModeEnabled || mDockState != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    void setCarModeLocked(boolean flag) {
        if(mCarModeEnabled != flag)
            mCarModeEnabled = flag;
    }

    public void setNightMode(int i) throws RemoteException {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        switch(i) {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown mode: ").append(i).toString());

        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
            break;
        }
        break MISSING_BLOCK_LABEL_69;
        Exception exception;
        exception;
        throw exception;
        if(isDoingNightMode()) goto _L2; else goto _L1
_L1:
        obj;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        if(mNightMode != i) {
            long l = Binder.clearCallingIdentity();
            android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "ui_night_mode", i);
            Binder.restoreCallingIdentity(l);
            mNightMode = i;
            updateLocked(0, 0);
        }
        obj;
        JVM INSTR monitorexit ;
_L3:
    }

    void systemReady() {
        boolean flag = true;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        mSystemReady = true;
        if(mDockState != 2)
            flag = false;
        mCarModeEnabled = flag;
        updateLocked(0, 0);
        mHandler.sendEmptyMessage(1);
        return;
    }

    final void updateConfigurationLocked(boolean flag) {
        int i;
        int j;
        if(mTelevision)
            i = 4;
        else
            i = mNormalType;
        if(mCarModeEnabled)
            i = 3;
        else
        if(isDeskDockState(mDockState))
            i = 2;
        if(mCarModeEnabled) {
            if(mNightMode == 0) {
                updateTwilightLocked();
                byte byte0;
                if(mComputedNightMode)
                    byte0 = 32;
                else
                    byte0 = 16;
                j = i | byte0;
            } else {
                j = i | mNightMode << 4;
            }
        } else {
            j = 0x10 | i & 0xffffffcf;
        }
        mCurUiMode = j;
        if(mHoldingConfiguration || j == mSetUiMode)
            break MISSING_BLOCK_LABEL_101;
        mSetUiMode = j;
        mConfiguration.uiMode = j;
        if(!flag)
            break MISSING_BLOCK_LABEL_101;
        ActivityManagerNative.getDefault().updateConfiguration(mConfiguration);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w(TAG, "Failure communicating with activity manager", remoteexception);
          goto _L1
    }

    void updateDockState(int i) {
        boolean flag = true;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(i != mDockState) {
            mDockState = i;
            if(mDockState != 2)
                flag = false;
            setCarModeLocked(flag);
            if(mSystemReady)
                updateLocked(1, 0);
        }
        return;
    }

    final void updateLocked(int i, int j) {
        long l;
        String s;
        String s1;
        l = Binder.clearCallingIdentity();
        s = null;
        s1 = null;
        if(mLastBroadcastState != 2) goto _L2; else goto _L1
_L1:
        adjustStatusBarCarModeLocked();
        s1 = UiModeManager.ACTION_EXIT_CAR_MODE;
_L12:
        if(!mCarModeEnabled) goto _L4; else goto _L3
_L3:
        if(mLastBroadcastState != 2) {
            adjustStatusBarCarModeLocked();
            if(s1 != null)
                mContext.sendBroadcast(new Intent(s1));
            mLastBroadcastState = 2;
            s = UiModeManager.ACTION_ENTER_CAR_MODE;
        }
_L13:
        if(s == null) goto _L6; else goto _L5
_L5:
        Intent intent = new Intent(s);
        intent.putExtra("enableFlags", i);
        intent.putExtra("disableFlags", j);
        mContext.sendOrderedBroadcast(intent, null, mResultReceiver, null, -1, null, null);
        mHoldingConfiguration = true;
_L18:
        updateConfigurationLocked(true);
        Exception exception;
        boolean flag;
        Intent intent1;
        Intent intent2;
        if(mCharging && (mCarModeEnabled && mCarModeKeepsScreenOn || mCurUiMode == 2 && mDeskModeKeepsScreenOn))
            flag = true;
        else
            flag = false;
        if(flag == mWakeLock.isHeld()) goto _L8; else goto _L7
_L7:
        if(!flag) goto _L10; else goto _L9
_L9:
        mWakeLock.acquire();
_L8:
        Binder.restoreCallingIdentity(l);
        return;
_L2:
        if(!isDeskDockState(mLastBroadcastState)) goto _L12; else goto _L11
_L11:
        s1 = UiModeManager.ACTION_EXIT_DESK_MODE;
          goto _L12
_L4:
        if(isDeskDockState(mDockState)) {
            if(!isDeskDockState(mLastBroadcastState)) {
                if(s1 != null)
                    mContext.sendBroadcast(new Intent(s1));
                mLastBroadcastState = mDockState;
                s = UiModeManager.ACTION_ENTER_DESK_MODE;
            }
        } else {
            mLastBroadcastState = 0;
            s = s1;
        }
          goto _L13
_L6:
        intent1 = null;
        if(!mCarModeEnabled) goto _L15; else goto _L14
_L14:
        if((i & 1) == 0) goto _L17; else goto _L16
_L16:
        intent2 = buildHomeIntent("android.intent.category.CAR_DOCK");
        intent1 = intent2;
_L17:
        if(intent1 != null)
            try {
                mContext.startActivity(intent1);
            }
            catch(ActivityNotFoundException activitynotfoundexception) { }
            finally {
                Binder.restoreCallingIdentity(l);
            }
          goto _L18
_L15:
        if(isDeskDockState(mDockState)) {
            if((i & 1) != 0)
                intent1 = buildHomeIntent("android.intent.category.DESK_DOCK");
        } else
        if((j & 1) != 0)
            intent1 = buildHomeIntent("android.intent.category.HOME");
          goto _L17
_L10:
        mWakeLock.release();
          goto _L8
        throw exception;
          goto _L12
    }

    void updateTwilightLocked() {
        if(mLocation != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        long l;
        TwilightCalculator twilightcalculator;
        long l1;
        l = System.currentTimeMillis();
        twilightcalculator = new TwilightCalculator();
        twilightcalculator.calculateTwilight(l, mLocation.getLatitude(), mLocation.getLongitude());
        boolean flag;
        Intent intent;
        PendingIntent pendingintent;
        if(twilightcalculator.mState == 0)
            flag = false;
        else
            flag = true;
        if(twilightcalculator.mSunrise != -1L && twilightcalculator.mSunset != -1L)
            break; /* Loop/switch isn't completed */
        l1 = l + 0x2932e00L;
_L4:
        intent = new Intent("com.android.server.action.UPDATE_NIGHT_MODE");
        pendingintent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mAlarmManager.cancel(pendingintent);
        mAlarmManager.set(0, l1, pendingintent);
        mComputedNightMode = flag;
        if(true) goto _L1; else goto _L3
_L3:
        int i = twilightcalculator.mState;
        long l2 = 0L + 60000L;
        if(l > twilightcalculator.mSunset)
            twilightcalculator.calculateTwilight(l + 0x5265c00L, mLocation.getLatitude(), mLocation.getLongitude());
        if(i == 1)
            l1 = l2 + twilightcalculator.mSunrise;
        else
            l1 = l2 + twilightcalculator.mSunset;
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    private static final String ACTION_UPDATE_NIGHT_MODE = "com.android.server.action.UPDATE_NIGHT_MODE";
    private static final boolean ENABLE_LAUNCH_CAR_DOCK_APP = true;
    private static final boolean ENABLE_LAUNCH_DESK_DOCK_APP = true;
    private static final double FACTOR_GMT_OFFSET_LONGITUDE = 0.0041666666666666666D;
    private static final String KEY_LAST_UPDATE_INTERVAL = "LAST_UPDATE_INTERVAL";
    private static final float LOCATION_UPDATE_DISTANCE_METER = 20000F;
    private static final long LOCATION_UPDATE_ENABLE_INTERVAL_MAX = 0xdbba0L;
    private static final long LOCATION_UPDATE_ENABLE_INTERVAL_MIN = 5000L;
    private static final long LOCATION_UPDATE_MS = 0x5265c00L;
    private static final boolean LOG = false;
    private static final long MIN_LOCATION_UPDATE_MS = 0x1b7740L;
    private static final int MSG_ENABLE_LOCATION_UPDATES = 1;
    private static final int MSG_GET_NEW_LOCATION_UPDATE = 2;
    private static final int MSG_UPDATE_TWILIGHT;
    private static final String TAG = android/app/UiModeManager.getSimpleName();
    private AlarmManager mAlarmManager;
    private final BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            boolean flag2 = false;
            UiModeManagerService uimodemanagerservice = UiModeManagerService.this;
            if(intent.getIntExtra("plugged", 0) != 0)
                flag2 = true;
            uimodemanagerservice.mCharging = flag2;
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            if(mSystemReady)
                updateLocked(0, 0);
            return;
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private boolean mCarModeEnabled;
    private final boolean mCarModeKeepsScreenOn;
    private boolean mCharging;
    private boolean mComputedNightMode;
    private Configuration mConfiguration;
    private final Context mContext;
    private int mCurUiMode;
    private final int mDefaultUiModeType;
    private final boolean mDeskModeKeepsScreenOn;
    private final BroadcastReceiver mDockModeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            int i = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
            updateDockState(i);
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private int mDockState;
    private final LocationListener mEmptyLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private final Handler mHandler = new Handler() {

        private void retrieveLocation() {
            Location location = null;
            Iterator iterator = mLocationManager.getProviders(new Criteria(), true).iterator();
            do {
                if(!iterator.hasNext())
                    break;
                Location location1 = mLocationManager.getLastKnownLocation((String)iterator.next());
                if(location == null || location1 != null && location.getTime() < location1.getTime())
                    location = location1;
            } while(true);
            if(location == null) {
                Time time = new Time();
                time.set(System.currentTimeMillis());
                long l = time.gmtoff;
                int i;
                double d;
                Object obj;
                if(time.isDst > 0)
                    i = 3600;
                else
                    i = 0;
                d = 0.0041666666666666666D * (double)(l - (long)i);
                location = new Location("fake");
                location.setLongitude(d);
                location.setLatitude(0.0D);
                location.setAccuracy(417000F);
                location.setTime(System.currentTimeMillis());
            }
            obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mLocation = location;
            return;
        }

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 0 2: default 32
        //                       0 33
        //                       1 149
        //                       2 105;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L2:
            Object obj1 = mLock;
            obj1;
            JVM INSTR monitorenter ;
            if(isDoingNightMode() && mLocation != null && mNightMode == 0) {
                updateTwilightLocked();
                updateLocked(0, 0);
            }
              goto _L1
_L4:
            if(!mNetworkListenerEnabled || 0x1b7740L + mLastNetworkRegisterTime >= SystemClock.elapsedRealtime()) goto _L1; else goto _L5
_L5:
            mNetworkListenerEnabled = false;
            mLocationManager.removeUpdates(mEmptyLocationListener);
_L3:
            boolean flag5 = mLocationManager.isProviderEnabled("network");
            boolean flag2 = flag5;
_L6:
            if(!mNetworkListenerEnabled && flag2) {
                mNetworkListenerEnabled = true;
                mLastNetworkRegisterTime = SystemClock.elapsedRealtime();
                mLocationManager.requestLocationUpdates("network", 0x5265c00L, 0.0F, mEmptyLocationListener);
                if(!mDidFirstInit) {
                    mDidFirstInit = true;
                    if(mLocation == null)
                        retrieveLocation();
                    synchronized(mLock) {
                        if(isDoingNightMode() && mLocation != null && mNightMode == 0) {
                            updateTwilightLocked();
                            updateLocked(0, 0);
                        }
                    }
                }
            }
            boolean flag4 = mLocationManager.isProviderEnabled("passive");
            boolean flag3 = flag4;
_L7:
            if(!mPassiveListenerEnabled && flag3) {
                mPassiveListenerEnabled = true;
                mLocationManager.requestLocationUpdates("passive", 0L, 20000F, mLocationListener);
            }
            if(!mNetworkListenerEnabled || !mPassiveListenerEnabled) {
                long l = (long)(1.5D * (double)message.getData().getLong("LAST_UPDATE_INTERVAL"));
                Exception exception;
                Exception exception1;
                Bundle bundle;
                Message message1;
                if(l == 0L)
                    l = 5000L;
                else
                if(l > 0xdbba0L)
                    l = 0xdbba0L;
                bundle = new Bundle();
                bundle.putLong("LAST_UPDATE_INTERVAL", l);
                message1 = mHandler.obtainMessage(1);
                message1.setData(bundle);
                mHandler.sendMessageDelayed(message1, l);
            }
              goto _L1
            exception;
            flag2 = false;
              goto _L6
            exception2;
            obj;
            JVM INSTR monitorexit ;
            throw exception2;
            exception1;
            flag3 = false;
              goto _L7
        }

        boolean mDidFirstInit;
        long mLastNetworkRegisterTime;
        boolean mNetworkListenerEnabled;
        boolean mPassiveListenerEnabled;
        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
                mLastNetworkRegisterTime = 0xffffffffffe488c0L;
            }
    };
    private boolean mHoldingConfiguration;
    private int mLastBroadcastState;
    private Location mLocation;
    private final LocationListener mLocationListener = new LocationListener() {

        private boolean hasMoved(Location location) {
            boolean flag2;
            boolean flag3;
            flag2 = true;
            flag3 = false;
            if(location != null) goto _L2; else goto _L1
_L1:
            return flag3;
_L2:
            if(mLocation == null)
                flag3 = flag2;
            else
            if(location.getTime() >= mLocation.getTime()) {
                if(mLocation.distanceTo(location) < mLocation.getAccuracy() + location.getAccuracy())
                    flag2 = false;
                flag3 = flag2;
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onLocationChanged(Location location) {
            boolean flag3;
            boolean flag2 = false;
            flag3 = hasMoved(location);
            if(mLocation == null || location.getAccuracy() < mLocation.getAccuracy())
                flag2 = true;
            if(!flag3 && !flag2)
                break MISSING_BLOCK_LABEL_117;
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mLocation = location;
            if(flag3 && isDoingNightMode() && mNightMode == 0)
                mHandler.sendEmptyMessage(0);
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private LocationManager mLocationManager;
    final Object mLock = new Object();
    private int mNightMode;
    int mNormalType;
    private NotificationManager mNotificationManager;
    private final BroadcastReceiver mResultReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            if(getResultCode() == -1) goto _L2; else goto _L1
_L1:
            return;
_L2:
            int i;
            int j;
            i = intent.getIntExtra("enableFlags", 0);
            j = intent.getIntExtra("disableFlags", 0);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            String s = null;
            if(!UiModeManager.ACTION_ENTER_CAR_MODE.equals(intent.getAction())) goto _L4; else goto _L3
_L3:
            if((i & 1) != 0)
                s = "android.intent.category.CAR_DOCK";
_L10:
            if(s == null) goto _L6; else goto _L5
_L5:
            Intent intent1;
            Configuration configuration;
            intent1 = UiModeManagerService.buildHomeIntent(s);
            configuration = null;
            if(!mHoldingConfiguration) goto _L8; else goto _L7
_L7:
            Configuration configuration1;
            mHoldingConfiguration = false;
            updateConfigurationLocked(false);
            configuration1 = mConfiguration;
            configuration = configuration1;
_L8:
            Exception exception;
            try {
                ActivityManagerNative.getDefault().startActivityWithConfig(null, intent1, null, null, null, 0, 0, configuration, null);
                mHoldingConfiguration = false;
            }
            catch(RemoteException remoteexception) {
                Slog.w(UiModeManagerService.TAG, remoteexception.getCause());
            }
            finally {
                obj;
            }
_L6:
            if(mHoldingConfiguration) {
                mHoldingConfiguration = false;
                updateConfigurationLocked(true);
            }
            if(true) goto _L1; else goto _L9
_L9:
            JVM INSTR monitorexit ;
            throw exception;
_L4:
label0:
            {
                if(!UiModeManager.ACTION_ENTER_DESK_MODE.equals(intent.getAction()))
                    break label0;
                if((i & 1) != 0)
                    s = "android.intent.category.DESK_DOCK";
            }
              goto _L10
            if((j & 1) != 0)
                s = "android.intent.category.HOME";
              goto _L10
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private int mSetUiMode;
    private StatusBarManager mStatusBarManager;
    private boolean mSystemReady;
    private final boolean mTelevision;
    private final BroadcastReceiver mTwilightUpdateReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            if(isDoingNightMode() && mNightMode == 0)
                mHandler.sendEmptyMessage(0);
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private final BroadcastReceiver mUpdateLocationReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            if("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                if(!intent.getBooleanExtra("state", false))
                    mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(2);
            }
        }

        final UiModeManagerService this$0;

             {
                this$0 = UiModeManagerService.this;
                super();
            }
    };
    private final android.os.PowerManager.WakeLock mWakeLock;




/*
    static boolean access$002(UiModeManagerService uimodemanagerservice, boolean flag) {
        uimodemanagerservice.mHoldingConfiguration = flag;
        return flag;
    }

*/







/*
    static boolean access$502(UiModeManagerService uimodemanagerservice, boolean flag) {
        uimodemanagerservice.mCharging = flag;
        return flag;
    }

*/




/*
    static Location access$702(UiModeManagerService uimodemanagerservice, Location location) {
        uimodemanagerservice.mLocation = location;
        return location;
    }

*/


}
