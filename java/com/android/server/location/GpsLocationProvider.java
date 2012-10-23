// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.database.Cursor;
import android.location.*;
import android.net.*;
import android.os.*;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.*;
import com.android.internal.app.IBatteryStats;
import com.android.internal.location.GpsNetInitiatedHandler;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

// Referenced classes of package com.android.server.location:
//            LocationProviderInterface, GpsXtraDownloader

public class GpsLocationProvider
    implements LocationProviderInterface {
    private final class GpsLocationProviderThread extends Thread {

        public void run() {
            Process.setThreadPriority(10);
            initialize();
            Looper.prepare();
            mHandler = new ProviderHandler();
            mInitializedLatch.countDown();
            Looper.loop();
        }

        final GpsLocationProvider this$0;

        public GpsLocationProviderThread() {
            this$0 = GpsLocationProvider.this;
            super("GpsLocationProvider");
        }
    }

    private final class Listener
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            synchronized(mListeners) {
                mListeners.remove(this);
            }
            if(mListener != null)
                mListener.asBinder().unlinkToDeath(this, 0);
            return;
            exception;
            arraylist;
            JVM INSTR monitorexit ;
            throw exception;
        }

        final IGpsStatusListener mListener;
        int mSensors;
        final GpsLocationProvider this$0;

        Listener(IGpsStatusListener igpsstatuslistener) {
            this$0 = GpsLocationProvider.this;
            super();
            mSensors = 0;
            mListener = igpsstatuslistener;
        }
    }


    public GpsLocationProvider(Context context, ILocationManager ilocationmanager) {
        String s;
        String s2;
        mLocationFlags = 0;
        mStatus = 1;
        mStatusUpdateTime = SystemClock.elapsedRealtime();
        mInjectNtpTimePending = true;
        mDownloadXtraDataPending = true;
        mFixInterval = 1000;
        mFixRequestTime = 0L;
        mTTFF = 0;
        mLocation = new Location("gps");
        mLocationExtras = new Bundle();
        mListeners = new ArrayList();
        mInitializedLatch = new CountDownLatch(1);
        mClientUids = new SparseIntArray();
        mGpsStatusProvider = new android.location.IGpsStatusProvider.Stub() {

            public void addGpsStatusListener(IGpsStatusListener igpsstatuslistener) throws RemoteException {
                if(igpsstatuslistener == null)
                    throw new NullPointerException("listener is null in addGpsStatusListener");
                ArrayList arraylist = mListeners;
                arraylist;
                JVM INSTR monitorenter ;
                IBinder ibinder = igpsstatuslistener.asBinder();
                int i = mListeners.size();
                int j = 0;
                do {
label0:
                    {
                        if(j < i) {
                            if(!ibinder.equals(((Listener)mListeners.get(j)).mListener.asBinder()))
                                break label0;
                        } else {
                            Listener listener = new Listener(igpsstatuslistener);
                            ibinder.linkToDeath(listener, 0);
                            mListeners.add(listener);
                        }
                        return;
                    }
                    j++;
                } while(true);
            }

            public void removeGpsStatusListener(IGpsStatusListener igpsstatuslistener) {
                if(igpsstatuslistener == null)
                    throw new NullPointerException("listener is null in addGpsStatusListener");
                ArrayList arraylist = mListeners;
                arraylist;
                JVM INSTR monitorenter ;
                IBinder ibinder = igpsstatuslistener.asBinder();
                Listener listener = null;
                int i = mListeners.size();
                int j = 0;
                do {
                    if(j < i && listener == null) {
                        Listener listener1 = (Listener)mListeners.get(j);
                        if(ibinder.equals(listener1.mListener.asBinder()))
                            listener = listener1;
                    } else {
                        if(listener != null) {
                            mListeners.remove(listener);
                            ibinder.unlinkToDeath(listener, 0);
                        }
                        return;
                    }
                    j++;
                } while(true);
            }

            final GpsLocationProvider this$0;

             {
                this$0 = GpsLocationProvider.this;
                super();
            }
        };
        mBroadcastReciever = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                String s4 = intent.getAction();
                if(!s4.equals("com.android.internal.location.ALARM_WAKEUP")) goto _L2; else goto _L1
_L1:
                startNavigating(false);
_L4:
                return;
_L2:
                if(s4.equals("com.android.internal.location.ALARM_TIMEOUT"))
                    hibernate();
                else
                if(s4.equals("android.intent.action.DATA_SMS_RECEIVED"))
                    checkSmsSuplInit(intent);
                else
                if(s4.equals("android.provider.Telephony.WAP_PUSH_RECEIVED"))
                    checkWapSuplInit(intent);
                if(true) goto _L4; else goto _L3
_L3:
            }

            final GpsLocationProvider this$0;

             {
                this$0 = GpsLocationProvider.this;
                super();
            }
        };
        mNetInitiatedListener = new android.location.INetInitiatedListener.Stub() {

            public boolean sendNiResponse(int i, int j) {
                new StringBuilder();
                native_send_ni_response(i, j);
                return true;
            }

            final GpsLocationProvider this$0;

             {
                this$0 = GpsLocationProvider.this;
                super();
            }
        };
        mSvs = new int[32];
        mSnrs = new float[32];
        mSvElevations = new float[32];
        mSvAzimuths = new float[32];
        mSvMasks = new int[3];
        mNmeaBuffer = new byte[120];
        mContext = context;
        mNtpTime = NtpTrustedTime.getInstance(context);
        mLocationManager = ilocationmanager;
        mNIHandler = new GpsNetInitiatedHandler(context);
        mLocation.setExtras(mLocationExtras);
        mWakeLock = ((PowerManager)mContext.getSystemService("power")).newWakeLock(1, "GpsLocationProvider");
        mWakeLock.setReferenceCounted(false);
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        mWakeupIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("com.android.internal.location.ALARM_WAKEUP"), 0);
        mTimeoutIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("com.android.internal.location.ALARM_TIMEOUT"), 0);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.DATA_SMS_RECEIVED");
        intentfilter.addDataScheme("sms");
        intentfilter.addDataAuthority("localhost", "7275");
        context.registerReceiver(mBroadcastReciever, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.provider.Telephony.WAP_PUSH_RECEIVED");
        FileInputStream fileinputstream;
        String s1;
        String s3;
        try {
            intentfilter1.addDataType("application/vnd.omaloc-supl-init");
        }
        catch(android.content.IntentFilter.MalformedMimeTypeException malformedmimetypeexception) {
            Log.w("GpsLocationProvider", "Malformed SUPL init mime type");
        }
        context.registerReceiver(mBroadcastReciever, intentfilter1);
        mConnMgr = (ConnectivityManager)context.getSystemService("connectivity");
        mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(ServiceManager.getService("batteryinfo"));
        mProperties = new Properties();
        fileinputstream = new FileInputStream(new File("/etc/gps.conf"));
        mProperties.load(fileinputstream);
        fileinputstream.close();
        mSuplServerHost = mProperties.getProperty("SUPL_HOST");
        s = mProperties.getProperty("SUPL_PORT");
        s1 = mSuplServerHost;
        if(s1 == null || s == null)
            break MISSING_BLOCK_LABEL_522;
        mSuplServerPort = Integer.parseInt(s);
_L1:
        mC2KServerHost = mProperties.getProperty("C2K_HOST");
        s2 = mProperties.getProperty("C2K_PORT");
        s3 = mC2KServerHost;
        if(s3 == null || s2 == null)
            break MISSING_BLOCK_LABEL_573;
        mC2KServerPort = Integer.parseInt(s2);
_L2:
        mThread = new GpsLocationProviderThread();
        mThread.start();
        IOException ioexception;
        NumberFormatException numberformatexception;
        NumberFormatException numberformatexception1;
        do
            try {
                mInitializedLatch.await();
                return;
            }
            catch(InterruptedException interruptedexception) {
                Thread.currentThread().interrupt();
            }
        while(true);
        numberformatexception1;
        Log.e("GpsLocationProvider", (new StringBuilder()).append("unable to parse SUPL_PORT: ").append(s).toString());
          goto _L1
        numberformatexception;
        try {
            Log.e("GpsLocationProvider", (new StringBuilder()).append("unable to parse C2K_PORT: ").append(s2).toString());
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) {
            Log.w("GpsLocationProvider", "Could not open GPS configuration file /etc/gps.conf");
        }
          goto _L2
    }

    private void appendUidExtra(Intent intent) {
        if(mNavigating && mClientUids.size() > 0)
            intent.putExtra("android.intent.extra.UID", mClientUids.keyAt(0));
    }

    private void checkSmsSuplInit(Intent intent) {
        SmsMessage asmsmessage[] = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(int i = 0; i < asmsmessage.length; i++) {
            byte abyte0[] = asmsmessage[i].getUserData();
            native_agps_ni_message(abyte0, abyte0.length);
        }

    }

    private void checkWapSuplInit(Intent intent) {
        byte abyte0[] = (byte[])(byte[])intent.getExtra("data");
        native_agps_ni_message(abyte0, abyte0.length);
    }

    private static native void class_init_native();

    private boolean deleteAidingData(Bundle bundle) {
        int i;
        boolean flag;
        if(bundle == null) {
            i = 65535;
        } else {
            i = 0;
            if(bundle.getBoolean("ephemeris"))
                i = false | true;
            if(bundle.getBoolean("almanac"))
                i |= 2;
            if(bundle.getBoolean("position"))
                i |= 4;
            if(bundle.getBoolean("time"))
                i |= 8;
            if(bundle.getBoolean("iono"))
                i |= 0x10;
            if(bundle.getBoolean("utc"))
                i |= 0x20;
            if(bundle.getBoolean("health"))
                i |= 0x40;
            if(bundle.getBoolean("svdir"))
                i |= 0x80;
            if(bundle.getBoolean("svsteer"))
                i |= 0x100;
            if(bundle.getBoolean("sadata"))
                i |= 0x200;
            if(bundle.getBoolean("rti"))
                i |= 0x400;
            if(bundle.getBoolean("celldb-info"))
                i |= 0x8000;
            if(bundle.getBoolean("all"))
                i |= 0xffff;
        }
        if(i != 0) {
            native_delete_aiding_data(i);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private String getSelectedApn() {
        String s;
        Cursor cursor;
        Uri uri = Uri.parse("content://telephony/carriers/preferapn");
        s = null;
        ContentResolver contentresolver = mContext.getContentResolver();
        String as[] = new String[1];
        as[0] = "apn";
        cursor = contentresolver.query(uri, as, null, null, "name ASC");
        if(cursor == null)
            break MISSING_BLOCK_LABEL_79;
        String s1;
        if(!cursor.moveToFirst())
            break MISSING_BLOCK_LABEL_72;
        s1 = cursor.getString(0);
        s = s1;
        cursor.close();
        return s;
        Exception exception;
        exception;
        cursor.close();
        throw exception;
    }

    private void handleAddListener(int i) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        if(mClientUids.indexOfKey(i) < 0) goto _L2; else goto _L1
_L1:
        Log.w("GpsLocationProvider", (new StringBuilder()).append("Duplicate add listener for uid ").append(i).toString());
          goto _L3
_L2:
        boolean flag;
        mClientUids.put(i, 0);
        flag = mNavigating;
        if(!flag)
            break MISSING_BLOCK_LABEL_79;
        mBatteryStats.noteStartGps(i);
_L4:
        arraylist;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Log.w("GpsLocationProvider", "RemoteException in addListener");
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleDisable() {
        if(mEnabled) {
            mEnabled = false;
            stopNavigating();
            native_cleanup();
        }
    }

    private void handleDownloadXtraData() {
        if(!mNetworkAvailable) {
            mDownloadXtraDataPending = true;
        } else {
            mDownloadXtraDataPending = false;
            byte abyte0[] = (new GpsXtraDownloader(mContext, mProperties)).downloadXtraData();
            if(abyte0 != null) {
                native_inject_xtra_data(abyte0, abyte0.length);
            } else {
                mHandler.removeMessages(6);
                mHandler.sendMessageDelayed(Message.obtain(mHandler, 6), 0x493e0L);
            }
        }
    }

    private void handleEnable() {
        if(!mEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        mEnabled = native_init();
        if(mEnabled) {
            mSupportsXtra = native_supports_xtra();
            if(mSuplServerHost != null)
                native_set_agps_server(1, mSuplServerHost, mSuplServerPort);
            if(mC2KServerHost != null)
                native_set_agps_server(2, mC2KServerHost, mC2KServerPort);
        } else {
            Log.w("GpsLocationProvider", "Failed to enable location provider");
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void handleEnableLocationTracking(boolean flag) {
        if(flag) {
            mTTFF = 0;
            mLastFixTime = 0L;
            startNavigating(false);
        } else {
            if(!hasCapability(1)) {
                mAlarmManager.cancel(mWakeupIntent);
                mAlarmManager.cancel(mTimeoutIntent);
            }
            stopNavigating();
        }
    }

    private void handleInjectNtpTime() {
        if(mNetworkAvailable) goto _L2; else goto _L1
_L1:
        mInjectNtpTimePending = true;
_L4:
        return;
_L2:
        mInjectNtpTimePending = false;
        if(mNtpTime.getCacheAge() >= 0x5265c00L)
            mNtpTime.forceRefresh();
        long l;
        if(mNtpTime.getCacheAge() < 0x5265c00L) {
            long l1 = mNtpTime.getCachedNtpTime();
            long l2 = mNtpTime.getCachedNtpTimeReference();
            long l3 = mNtpTime.getCacheCertainty();
            long l4 = System.currentTimeMillis();
            Log.d("GpsLocationProvider", (new StringBuilder()).append("NTP server returned: ").append(l1).append(" (").append(new Date(l1)).append(") reference: ").append(l2).append(" certainty: ").append(l3).append(" system time offset: ").append(l1 - l4).toString());
            native_inject_time(l1, l2, (int)l3);
            l = 0x5265c00L;
        } else {
            l = 0x493e0L;
        }
        if(mPeriodicTimeInjection) {
            mHandler.removeMessages(5);
            mHandler.sendMessageDelayed(Message.obtain(mHandler, 5), l);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleRemoveListener(int i) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        if(mClientUids.indexOfKey(i) >= 0) goto _L2; else goto _L1
_L1:
        Log.w("GpsLocationProvider", (new StringBuilder()).append("Unneeded remove listener for uid ").append(i).toString());
          goto _L3
_L2:
        boolean flag;
        mClientUids.delete(i);
        flag = mNavigating;
        if(!flag)
            break MISSING_BLOCK_LABEL_78;
        mBatteryStats.noteStopGps(i);
_L4:
        arraylist;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Log.w("GpsLocationProvider", "RemoteException in removeListener");
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleRequestSingleShot() {
        mTTFF = 0;
        mLastFixTime = 0L;
        startNavigating(true);
    }

    private void handleUpdateLocation(Location location) {
        if(location.hasAccuracy())
            native_inject_location(location.getLatitude(), location.getLongitude(), location.getAccuracy());
    }

    private void handleUpdateNetworkState(int i, NetworkInfo networkinfo) {
        boolean flag;
        if(i == 2)
            flag = true;
        else
            flag = false;
        mNetworkAvailable = flag;
        if(networkinfo != null) {
            String s;
            boolean flag1;
            boolean flag2;
            String s1;
            if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "mobile_data", 1) == 1)
                flag1 = true;
            else
                flag1 = false;
            if(networkinfo.isAvailable() && flag1)
                flag2 = true;
            else
                flag2 = false;
            s1 = getSelectedApn();
            if(s1 == null)
                s1 = "dummy-apn";
            native_update_network_state(networkinfo.isConnected(), networkinfo.getType(), networkinfo.isRoaming(), flag2, networkinfo.getExtraInfo(), s1);
        }
        if(networkinfo != null && networkinfo.getType() == 3 && mAGpsDataConnectionState == 1) {
            s = networkinfo.getExtraInfo();
            if(mNetworkAvailable) {
                if(s == null)
                    s = "dummy-apn";
                mAGpsApn = s;
                if(mAGpsDataConnectionIpAddr != -1 && !mConnMgr.requestRouteToHost(3, mAGpsDataConnectionIpAddr))
                    Log.d("GpsLocationProvider", "call requestRouteToHost failed");
                native_agps_data_conn_open(s);
                mAGpsDataConnectionState = 2;
            } else {
                mAGpsApn = null;
                mAGpsDataConnectionState = 0;
                native_agps_data_conn_failed();
            }
        }
        if(mNetworkAvailable) {
            if(mInjectNtpTimePending)
                sendMessage(5, 0, null);
            if(mDownloadXtraDataPending)
                sendMessage(6, 0, null);
        }
    }

    private boolean hasCapability(int i) {
        boolean flag;
        if((i & mEngineCapabilities) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void hibernate() {
        stopNavigating();
        mAlarmManager.cancel(mTimeoutIntent);
        mAlarmManager.cancel(mWakeupIntent);
        SystemClock.elapsedRealtime();
        mAlarmManager.set(2, SystemClock.elapsedRealtime() + (long)mFixInterval, mWakeupIntent);
    }

    private void initialize() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("com.android.internal.location.ALARM_WAKEUP");
        intentfilter.addAction("com.android.internal.location.ALARM_TIMEOUT");
        mContext.registerReceiver(mBroadcastReciever, intentfilter);
    }

    public static boolean isSupported() {
        return native_is_supported();
    }

    private native void native_agps_data_conn_closed();

    private native void native_agps_data_conn_failed();

    private native void native_agps_data_conn_open(String s);

    private native void native_agps_ni_message(byte abyte0[], int i);

    private native void native_agps_set_id(int i, String s);

    private native void native_agps_set_ref_location_cellid(int i, int j, int k, int l, int i1);

    private native void native_cleanup();

    private native void native_delete_aiding_data(int i);

    private native String native_get_internal_state();

    private native boolean native_init();

    private native void native_inject_location(double d, double d1, float f);

    private native void native_inject_time(long l, long l1, int i);

    private native void native_inject_xtra_data(byte abyte0[], int i);

    private static native boolean native_is_supported();

    private native int native_read_nmea(byte abyte0[], int i);

    private native int native_read_sv_status(int ai[], float af[], float af1[], float af2[], int ai1[]);

    private native void native_send_ni_response(int i, int j);

    private native void native_set_agps_server(int i, String s, int j);

    private native boolean native_set_position_mode(int i, int j, int k, int l, int i1);

    private native boolean native_start();

    private native boolean native_stop();

    private native boolean native_supports_xtra();

    private native void native_update_network_state(boolean flag, int i, boolean flag1, boolean flag2, String s, String s1);

    private void reportAGpsStatus(int i, int j, int k) {
        j;
        JVM INSTR tableswitch 1 4: default 32
    //                   1 33
    //                   2 185
    //                   3 32
    //                   4 32;
           goto _L1 _L2 _L3 _L1 _L1
_L1:
        return;
_L2:
        mAGpsDataConnectionState = 1;
        int l = mConnMgr.startUsingNetworkFeature(0, "enableSUPL");
        mAGpsDataConnectionIpAddr = k;
        if(l == 0) {
            if(mAGpsApn != null) {
                Log.d("GpsLocationProvider", (new StringBuilder()).append("mAGpsDataConnectionIpAddr ").append(mAGpsDataConnectionIpAddr).toString());
                if(mAGpsDataConnectionIpAddr != -1 && !mConnMgr.requestRouteToHost(3, mAGpsDataConnectionIpAddr))
                    Log.d("GpsLocationProvider", "call requestRouteToHost failed");
                native_agps_data_conn_open(mAGpsApn);
                mAGpsDataConnectionState = 2;
            } else {
                Log.e("GpsLocationProvider", "mAGpsApn not set when receiving Phone.APN_ALREADY_ACTIVE");
                mAGpsDataConnectionState = 0;
                native_agps_data_conn_failed();
            }
        } else
        if(l != 1) {
            mAGpsDataConnectionState = 0;
            native_agps_data_conn_failed();
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if(mAGpsDataConnectionState != 0) {
            mConnMgr.stopUsingNetworkFeature(0, "enableSUPL");
            native_agps_data_conn_closed();
            mAGpsDataConnectionState = 0;
        }
        if(true) goto _L1; else goto _L4
_L4:
    }

    private void reportLocation(int i, double d, double d1, double d2, 
            float f, float f1, float f2, long l) {
        Location location = mLocation;
        location;
        JVM INSTR monitorenter ;
        mLocationFlags = i;
        if((i & 1) == 1) {
            mLocation.setLatitude(d);
            mLocation.setLongitude(d1);
            mLocation.setTime(l);
        }
        if((i & 2) != 2) goto _L2; else goto _L1
_L1:
        mLocation.setAltitude(d2);
_L14:
        if((i & 4) != 4) goto _L4; else goto _L3
_L3:
        mLocation.setSpeed(f);
_L15:
        if((i & 8) != 8) goto _L6; else goto _L5
_L5:
        mLocation.setBearing(f1);
_L16:
        if((i & 0x10) != 16) goto _L8; else goto _L7
_L7:
        mLocation.setAccuracy(f2);
_L17:
        mLocation.setExtras(mLocationExtras);
        ArrayList arraylist;
        int j;
        Listener listener;
        Exception exception;
        int k;
        try {
            mLocationManager.reportLocation(mLocation, false);
        }
        catch(RemoteException remoteexception) {
            Log.e("GpsLocationProvider", "RemoteException calling reportLocation");
        }
        mLastFixTime = System.currentTimeMillis();
        if(mTTFF != 0 || (i & 1) != 1) goto _L10; else goto _L9
_L9:
        mTTFF = (int)(mLastFixTime - mFixRequestTime);
        arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        j = mListeners.size();
        k = 0;
_L13:
        if(k >= j) goto _L12; else goto _L11
_L11:
        listener = (Listener)mListeners.get(k);
        listener.mListener.onFirstFix(mTTFF);
_L18:
        k++;
          goto _L13
_L2:
        mLocation.removeAltitude();
          goto _L14
        exception;
        throw exception;
_L4:
        mLocation.removeSpeed();
          goto _L15
_L6:
        mLocation.removeBearing();
          goto _L16
_L8:
        mLocation.removeAccuracy();
          goto _L17
        RemoteException remoteexception1;
        remoteexception1;
        Log.w("GpsLocationProvider", "RemoteException in stopNavigating");
        mListeners.remove(listener);
        j--;
          goto _L18
_L12:
        arraylist;
        JVM INSTR monitorexit ;
_L10:
        if(mSingleShot)
            stopNavigating();
        if(mStarted && mStatus != 2) {
            if(!hasCapability(1) && mFixInterval < 60000)
                mAlarmManager.cancel(mTimeoutIntent);
            Intent intent = new Intent("android.location.GPS_FIX_CHANGE");
            intent.putExtra("enabled", true);
            mContext.sendBroadcast(intent);
            updateStatus(2, mSvCount);
        }
        if(!hasCapability(1) && mStarted && mFixInterval > 10000)
            hibernate();
        return;
        Exception exception1;
        exception1;
        arraylist;
        JVM INSTR monitorexit ;
        throw exception1;
          goto _L14
    }

    private void reportNmea(long l) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        int i;
        String s;
        int k;
        i = mListeners.size();
        if(i <= 0)
            break MISSING_BLOCK_LABEL_122;
        int j = native_read_nmea(mNmeaBuffer, mNmeaBuffer.length);
        s = new String(mNmeaBuffer, 0, j);
        k = 0;
_L1:
        Listener listener;
        if(k >= i)
            break MISSING_BLOCK_LABEL_122;
        listener = (Listener)mListeners.get(k);
        try {
            listener.mListener.onNmeaReceived(l, s);
        }
        catch(RemoteException remoteexception) {
            Log.w("GpsLocationProvider", "RemoteException in reportNmea");
            mListeners.remove(listener);
            i--;
        }
        k++;
          goto _L1
        arraylist;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private void reportStatus(int i) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        boolean flag = mNavigating;
        i;
        JVM INSTR tableswitch 1 4: default 44
    //                   1 109
    //                   2 127
    //                   3 135
    //                   4 143;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        if(flag == mNavigating) goto _L7; else goto _L6
_L6:
        int j;
        int k;
        j = mListeners.size();
        k = 0;
_L12:
        if(k >= j) goto _L9; else goto _L8
_L8:
        Listener listener = (Listener)mListeners.get(k);
        if(!mNavigating) goto _L11; else goto _L10
_L10:
        listener.mListener.onGpsStarted();
_L13:
        k++;
          goto _L12
_L2:
        mNavigating = true;
        mEngineOn = true;
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L3:
        mNavigating = false;
          goto _L1
_L4:
        mEngineOn = true;
          goto _L1
_L5:
        mEngineOn = false;
        mNavigating = false;
          goto _L1
_L11:
        listener.mListener.onGpsStopped();
          goto _L13
        RemoteException remoteexception1;
        remoteexception1;
        Log.w("GpsLocationProvider", "RemoteException in reportStatus");
        mListeners.remove(listener);
        j--;
          goto _L13
_L9:
        int l = -1 + mClientUids.size();
_L17:
        if(l < 0) goto _L15; else goto _L14
_L14:
        int i1 = mClientUids.keyAt(l);
        if(mNavigating)
            mBatteryStats.noteStartGps(i1);
        else
            mBatteryStats.noteStopGps(i1);
          goto _L16
        RemoteException remoteexception;
        remoteexception;
        Log.w("GpsLocationProvider", "RemoteException in reportStatus");
_L15:
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", mNavigating);
        appendUidExtra(intent);
        mContext.sendBroadcast(intent);
_L7:
        arraylist;
        JVM INSTR monitorexit ;
        return;
_L16:
        l--;
          goto _L17
    }

    private void reportSvStatus() {
        int i = native_read_sv_status(mSvs, mSnrs, mSvElevations, mSvAzimuths, mSvMasks);
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        int j;
        int k;
        j = mListeners.size();
        k = 0;
_L1:
        Listener listener;
        if(k >= j)
            break MISSING_BLOCK_LABEL_143;
        listener = (Listener)mListeners.get(k);
        listener.mListener.onSvStatusChanged(i, mSvs, mSnrs, mSvElevations, mSvAzimuths, mSvMasks[0], mSvMasks[1], mSvMasks[2]);
_L2:
        k++;
          goto _L1
        RemoteException remoteexception;
        remoteexception;
        Log.w("GpsLocationProvider", "RemoteException in reportSvInfo");
        mListeners.remove(listener);
        j--;
          goto _L2
        arraylist;
        JVM INSTR monitorexit ;
        updateStatus(mStatus, Integer.bitCount(mSvMasks[2]));
        if(mNavigating && mStatus == 2 && mLastFixTime > 0L && System.currentTimeMillis() - mLastFixTime > 10000L) {
            Intent intent = new Intent("android.location.GPS_FIX_CHANGE");
            intent.putExtra("enabled", false);
            mContext.sendBroadcast(intent);
            updateStatus(1, mSvCount);
        }
        return;
        Exception exception;
        exception;
        arraylist;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void requestRefLocation(int i) {
        TelephonyManager telephonymanager = (TelephonyManager)mContext.getSystemService("phone");
        if(telephonymanager.getPhoneType() == 1) {
            GsmCellLocation gsmcelllocation = (GsmCellLocation)telephonymanager.getCellLocation();
            if(gsmcelllocation != null && telephonymanager.getPhoneType() == 1 && telephonymanager.getNetworkOperator() != null && telephonymanager.getNetworkOperator().length() > 3) {
                int j = Integer.parseInt(telephonymanager.getNetworkOperator().substring(0, 3));
                int k = Integer.parseInt(telephonymanager.getNetworkOperator().substring(3));
                int l = telephonymanager.getNetworkType();
                byte byte0;
                if(l == 3 || l == 8 || l == 9 || l == 10)
                    byte0 = 2;
                else
                    byte0 = 1;
                native_agps_set_ref_location_cellid(byte0, j, k, gsmcelllocation.getLac(), gsmcelllocation.getCid());
            } else {
                Log.e("GpsLocationProvider", "Error getting cell location info.");
            }
        } else {
            Log.e("GpsLocationProvider", "CDMA not supported.");
        }
    }

    private void requestSetID(int i) {
        TelephonyManager telephonymanager;
        int j;
        String s;
        telephonymanager = (TelephonyManager)mContext.getSystemService("phone");
        j = 0;
        s = "";
        if((i & 1) != 1) goto _L2; else goto _L1
_L1:
        String s2 = telephonymanager.getSubscriberId();
        if(s2 != null) {
            s = s2;
            j = 1;
        }
_L4:
        native_agps_set_id(j, s);
        return;
_L2:
        if((i & 2) == 2) {
            String s1 = telephonymanager.getLine1Number();
            if(s1 != null) {
                s = s1;
                j = 2;
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void requestUtcTime() {
        sendMessage(5, 0, null);
    }

    private void sendMessage(int i, int j, Object obj) {
        android.os.PowerManager.WakeLock wakelock = mWakeLock;
        wakelock;
        JVM INSTR monitorenter ;
        mPendingMessageBits = mPendingMessageBits | 1 << i;
        mWakeLock.acquire();
        mHandler.removeMessages(i);
        Message message = Message.obtain(mHandler, i);
        message.arg1 = j;
        message.obj = obj;
        mHandler.sendMessage(message);
        return;
    }

    private void setEngineCapabilities(int i) {
        mEngineCapabilities = i;
        if(!hasCapability(16) && !mPeriodicTimeInjection) {
            mPeriodicTimeInjection = true;
            requestUtcTime();
        }
    }

    private void startNavigating(boolean flag) {
        if(!mStarted) {
            mStarted = true;
            mSingleShot = flag;
            mPositionMode = 0;
            int i;
            if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "assisted_gps_enabled", 1) != 0)
                if(flag && hasCapability(4))
                    mPositionMode = 2;
                else
                if(hasCapability(2))
                    mPositionMode = 1;
            if(hasCapability(1))
                i = mFixInterval;
            else
                i = 1000;
            if(!native_set_position_mode(mPositionMode, 0, i, 0, 0)) {
                mStarted = false;
                Log.e("GpsLocationProvider", "set_position_mode failed in startNavigating()");
            } else
            if(!native_start()) {
                mStarted = false;
                Log.e("GpsLocationProvider", "native_start failed in startNavigating()");
            } else {
                updateStatus(1, 0);
                mFixRequestTime = System.currentTimeMillis();
                if(!hasCapability(1) && mFixInterval >= 60000)
                    mAlarmManager.set(2, 60000L + SystemClock.elapsedRealtime(), mTimeoutIntent);
            }
        }
    }

    private void stopNavigating() {
        if(mStarted) {
            mStarted = false;
            mSingleShot = false;
            native_stop();
            mTTFF = 0;
            mLastFixTime = 0L;
            mLocationFlags = 0;
            updateStatus(1, 0);
        }
    }

    private void updateStatus(int i, int j) {
        if(i != mStatus || j != mSvCount) {
            mStatus = i;
            mSvCount = j;
            mLocationExtras.putInt("satellites", j);
            mStatusUpdateTime = SystemClock.elapsedRealtime();
        }
    }

    private void xtraDownloadRequest() {
        sendMessage(6, 0, null);
    }

    public void addListener(int i) {
        android.os.PowerManager.WakeLock wakelock = mWakeLock;
        wakelock;
        JVM INSTR monitorenter ;
        mPendingListenerMessages = 1 + mPendingListenerMessages;
        mWakeLock.acquire();
        Message message = Message.obtain(mHandler, 8);
        message.arg1 = i;
        mHandler.sendMessage(message);
        return;
    }

    public void disable() {
        Handler handler = mHandler;
        handler;
        JVM INSTR monitorenter ;
        sendMessage(2, 0, null);
        return;
    }

    public void enable() {
        Handler handler = mHandler;
        handler;
        JVM INSTR monitorenter ;
        sendMessage(2, 1, null);
        return;
    }

    public void enableLocationTracking(boolean flag) {
        Handler handler = mHandler;
        handler;
        JVM INSTR monitorenter ;
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        sendMessage(3, i, null);
        return;
    }

    public int getAccuracy() {
        return 1;
    }

    public IGpsStatusProvider getGpsStatusProvider() {
        return mGpsStatusProvider;
    }

    public String getInternalState() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("  mFixInterval=").append(mFixInterval).append("\n");
        stringbuilder.append("  mEngineCapabilities=0x").append(Integer.toHexString(mEngineCapabilities)).append(" (");
        if(hasCapability(1))
            stringbuilder.append("SCHED ");
        if(hasCapability(2))
            stringbuilder.append("MSB ");
        if(hasCapability(4))
            stringbuilder.append("MSA ");
        if(hasCapability(8))
            stringbuilder.append("SINGLE_SHOT ");
        if(hasCapability(16))
            stringbuilder.append("ON_DEMAND_TIME ");
        stringbuilder.append(")\n");
        stringbuilder.append(native_get_internal_state());
        return stringbuilder.toString();
    }

    public String getName() {
        return "gps";
    }

    public INetInitiatedListener getNetInitiatedListener() {
        return mNetInitiatedListener;
    }

    public int getPowerRequirement() {
        return 3;
    }

    public int getStatus(Bundle bundle) {
        if(bundle != null)
            bundle.putInt("satellites", mSvCount);
        return mStatus;
    }

    public long getStatusUpdateTime() {
        return mStatusUpdateTime;
    }

    public boolean hasMonetaryCost() {
        return false;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public boolean meetsCriteria(Criteria criteria) {
        boolean flag = true;
        if(criteria.getPowerRequirement() == flag)
            flag = false;
        return flag;
    }

    public void removeListener(int i) {
        android.os.PowerManager.WakeLock wakelock = mWakeLock;
        wakelock;
        JVM INSTR monitorenter ;
        mPendingListenerMessages = 1 + mPendingListenerMessages;
        mWakeLock.acquire();
        Message message = Message.obtain(mHandler, 9);
        message.arg1 = i;
        mHandler.sendMessage(message);
        return;
    }

    public void reportNiNotification(int i, int j, int k, int l, int i1, String s, String s1, 
            int j1, int k1, String s2) {
        Log.i("GpsLocationProvider", "reportNiNotification: entered");
        Log.i("GpsLocationProvider", (new StringBuilder()).append("notificationId: ").append(i).append(", niType: ").append(j).append(", notifyFlags: ").append(k).append(", timeout: ").append(l).append(", defaultResponse: ").append(i1).toString());
        Log.i("GpsLocationProvider", (new StringBuilder()).append("requestorId: ").append(s).append(", text: ").append(s1).append(", requestorIdEncoding: ").append(j1).append(", textEncoding: ").append(k1).toString());
        com.android.internal.location.GpsNetInitiatedHandler.GpsNiNotification gpsninotification = new com.android.internal.location.GpsNetInitiatedHandler.GpsNiNotification();
        gpsninotification.notificationId = i;
        gpsninotification.niType = j;
        boolean flag;
        boolean flag1;
        boolean flag2;
        Bundle bundle;
        Properties properties;
        Iterator iterator;
        java.util.Map.Entry entry;
        if((k & 1) != 0)
            flag = true;
        else
            flag = false;
        gpsninotification.needNotify = flag;
        if((k & 2) != 0)
            flag1 = true;
        else
            flag1 = false;
        gpsninotification.needVerify = flag1;
        if((k & 4) != 0)
            flag2 = true;
        else
            flag2 = false;
        gpsninotification.privacyOverride = flag2;
        gpsninotification.timeout = l;
        gpsninotification.defaultResponse = i1;
        gpsninotification.requestorId = s;
        gpsninotification.text = s1;
        gpsninotification.requestorIdEncoding = j1;
        gpsninotification.textEncoding = k1;
        bundle = new Bundle();
        if(s2 == null)
            s2 = "";
        properties = new Properties();
        try {
            properties.load(new StringReader(s2));
        }
        catch(IOException ioexception) {
            Log.e("GpsLocationProvider", (new StringBuilder()).append("reportNiNotification cannot parse extras data: ").append(s2).toString());
        }
        for(iterator = properties.entrySet().iterator(); iterator.hasNext(); bundle.putString((String)entry.getKey(), (String)entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

        gpsninotification.extras = bundle;
        mNIHandler.handleNiNotification(gpsninotification);
    }

    public boolean requestSingleShotFix() {
        if(!mStarted) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        Handler handler = mHandler;
        handler;
        JVM INSTR monitorenter ;
        mHandler.removeMessages(10);
        Message message = Message.obtain(mHandler, 10);
        mHandler.sendMessage(message);
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean requiresCell() {
        return false;
    }

    public boolean requiresNetwork() {
        return true;
    }

    public boolean requiresSatellite() {
        return true;
    }

    public boolean sendExtraCommand(String s, Bundle bundle) {
        long l;
        boolean flag;
        l = Binder.clearCallingIdentity();
        flag = false;
        if(!"delete_aiding_data".equals(s)) goto _L2; else goto _L1
_L1:
        flag = deleteAidingData(bundle);
_L4:
        Binder.restoreCallingIdentity(l);
        return flag;
_L2:
        if("force_time_injection".equals(s)) {
            sendMessage(5, 0, null);
            flag = true;
        } else
        if("force_xtra_injection".equals(s)) {
            if(mSupportsXtra) {
                xtraDownloadRequest();
                flag = true;
            }
        } else {
            Log.w("GpsLocationProvider", (new StringBuilder()).append("sendExtraCommand: unknown command ").append(s).toString());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setMinTime(long l, WorkSource worksource) {
        if(l >= 0L) {
            mFixInterval = (int)l;
            if(mStarted && hasCapability(1) && !native_set_position_mode(mPositionMode, 0, mFixInterval, 0, 0))
                Log.e("GpsLocationProvider", "set_position_mode failed in setMinTime()");
        }
    }

    public boolean supportsAltitude() {
        return true;
    }

    public boolean supportsBearing() {
        return true;
    }

    public boolean supportsSpeed() {
        return true;
    }

    public void updateLocation(Location location) {
        sendMessage(7, 0, location);
    }

    public void updateNetworkState(int i, NetworkInfo networkinfo) {
        sendMessage(4, i, networkinfo);
    }

    private static final int ADD_LISTENER = 8;
    private static final int AGPS_DATA_CONNECTION_CLOSED = 0;
    private static final int AGPS_DATA_CONNECTION_OPEN = 2;
    private static final int AGPS_DATA_CONNECTION_OPENING = 1;
    private static final int AGPS_REF_LOCATION_TYPE_GSM_CELLID = 1;
    private static final int AGPS_REF_LOCATION_TYPE_UMTS_CELLID = 2;
    private static final int AGPS_REG_LOCATION_TYPE_MAC = 3;
    private static final int AGPS_RIL_REQUEST_REFLOC_CELLID = 1;
    private static final int AGPS_RIL_REQUEST_REFLOC_MAC = 2;
    private static final int AGPS_RIL_REQUEST_SETID_IMSI = 1;
    private static final int AGPS_RIL_REQUEST_SETID_MSISDN = 2;
    private static final int AGPS_SETID_TYPE_IMSI = 1;
    private static final int AGPS_SETID_TYPE_MSISDN = 2;
    private static final int AGPS_SETID_TYPE_NONE = 0;
    private static final int AGPS_TYPE_C2K = 2;
    private static final int AGPS_TYPE_SUPL = 1;
    private static final String ALARM_TIMEOUT = "com.android.internal.location.ALARM_TIMEOUT";
    private static final String ALARM_WAKEUP = "com.android.internal.location.ALARM_WAKEUP";
    private static final int ALMANAC_MASK = 1;
    private static final int CHECK_LOCATION = 1;
    private static final boolean DEBUG = false;
    private static final int DOWNLOAD_XTRA_DATA = 6;
    private static final int ENABLE = 2;
    private static final int ENABLE_TRACKING = 3;
    private static final int EPHEMERIS_MASK = 0;
    private static final int GPS_AGPS_DATA_CONNECTED = 3;
    private static final int GPS_AGPS_DATA_CONN_DONE = 4;
    private static final int GPS_AGPS_DATA_CONN_FAILED = 5;
    private static final int GPS_CAPABILITY_MSA = 4;
    private static final int GPS_CAPABILITY_MSB = 2;
    private static final int GPS_CAPABILITY_ON_DEMAND_TIME = 16;
    private static final int GPS_CAPABILITY_SCHEDULING = 1;
    private static final int GPS_CAPABILITY_SINGLE_SHOT = 8;
    private static final int GPS_DELETE_ALL = 65535;
    private static final int GPS_DELETE_ALMANAC = 2;
    private static final int GPS_DELETE_CELLDB_INFO = 32768;
    private static final int GPS_DELETE_EPHEMERIS = 1;
    private static final int GPS_DELETE_HEALTH = 64;
    private static final int GPS_DELETE_IONO = 16;
    private static final int GPS_DELETE_POSITION = 4;
    private static final int GPS_DELETE_RTI = 1024;
    private static final int GPS_DELETE_SADATA = 512;
    private static final int GPS_DELETE_SVDIR = 128;
    private static final int GPS_DELETE_SVSTEER = 256;
    private static final int GPS_DELETE_TIME = 8;
    private static final int GPS_DELETE_UTC = 32;
    private static final int GPS_POLLING_THRESHOLD_INTERVAL = 10000;
    private static final int GPS_POSITION_MODE_MS_ASSISTED = 2;
    private static final int GPS_POSITION_MODE_MS_BASED = 1;
    private static final int GPS_POSITION_MODE_STANDALONE = 0;
    private static final int GPS_POSITION_RECURRENCE_PERIODIC = 0;
    private static final int GPS_POSITION_RECURRENCE_SINGLE = 1;
    private static final int GPS_RELEASE_AGPS_DATA_CONN = 2;
    private static final int GPS_REQUEST_AGPS_DATA_CONN = 1;
    private static final int GPS_STATUS_ENGINE_OFF = 4;
    private static final int GPS_STATUS_ENGINE_ON = 3;
    private static final int GPS_STATUS_NONE = 0;
    private static final int GPS_STATUS_SESSION_BEGIN = 1;
    private static final int GPS_STATUS_SESSION_END = 2;
    private static final int INJECT_NTP_TIME = 5;
    private static final int LOCATION_HAS_ACCURACY = 16;
    private static final int LOCATION_HAS_ALTITUDE = 2;
    private static final int LOCATION_HAS_BEARING = 8;
    private static final int LOCATION_HAS_LAT_LONG = 1;
    private static final int LOCATION_HAS_SPEED = 4;
    private static final int LOCATION_INVALID = 0;
    private static final int MAX_SVS = 32;
    private static final int NO_FIX_TIMEOUT = 60000;
    private static final long NTP_INTERVAL = 0x5265c00L;
    private static final String PROPERTIES_FILE = "/etc/gps.conf";
    private static final long RECENT_FIX_TIMEOUT = 10000L;
    private static final int REMOVE_LISTENER = 9;
    private static final int REQUEST_SINGLE_SHOT = 10;
    private static final long RETRY_INTERVAL = 0x493e0L;
    private static final String TAG = "GpsLocationProvider";
    private static final int UPDATE_LOCATION = 7;
    private static final int UPDATE_NETWORK_STATE = 4;
    private static final int USED_FOR_FIX_MASK = 2;
    private static final boolean VERBOSE = false;
    private static final String WAKELOCK_KEY = "GpsLocationProvider";
    private String mAGpsApn;
    private int mAGpsDataConnectionIpAddr;
    private int mAGpsDataConnectionState;
    private final AlarmManager mAlarmManager;
    private final IBatteryStats mBatteryStats;
    private final BroadcastReceiver mBroadcastReciever;
    private String mC2KServerHost;
    private int mC2KServerPort;
    private final SparseIntArray mClientUids;
    private final ConnectivityManager mConnMgr;
    private final Context mContext;
    private boolean mDownloadXtraDataPending;
    private volatile boolean mEnabled;
    private int mEngineCapabilities;
    private boolean mEngineOn;
    private int mFixInterval;
    private long mFixRequestTime;
    private final IGpsStatusProvider mGpsStatusProvider;
    private Handler mHandler;
    private final CountDownLatch mInitializedLatch;
    private boolean mInjectNtpTimePending;
    private long mLastFixTime;
    private ArrayList mListeners;
    private Location mLocation;
    private Bundle mLocationExtras;
    private int mLocationFlags;
    private final ILocationManager mLocationManager;
    private final GpsNetInitiatedHandler mNIHandler;
    private boolean mNavigating;
    private final INetInitiatedListener mNetInitiatedListener;
    private boolean mNetworkAvailable;
    private byte mNmeaBuffer[];
    private final NtpTrustedTime mNtpTime;
    private int mPendingListenerMessages;
    private int mPendingMessageBits;
    private boolean mPeriodicTimeInjection;
    private int mPositionMode;
    private Properties mProperties;
    private boolean mSingleShot;
    private float mSnrs[];
    private boolean mStarted;
    private int mStatus;
    private long mStatusUpdateTime;
    private String mSuplServerHost;
    private int mSuplServerPort;
    private boolean mSupportsXtra;
    private float mSvAzimuths[];
    private int mSvCount;
    private float mSvElevations[];
    private int mSvMasks[];
    private int mSvs[];
    private int mTTFF;
    private final Thread mThread;
    private final PendingIntent mTimeoutIntent;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final PendingIntent mWakeupIntent;

    static  {
        class_init_native();
    }













/*
    static int access$1872(GpsLocationProvider gpslocationprovider, int i) {
        int j = i & gpslocationprovider.mPendingMessageBits;
        gpslocationprovider.mPendingMessageBits = j;
        return j;
    }

*/



/*
    static int access$1910(GpsLocationProvider gpslocationprovider) {
        int i = gpslocationprovider.mPendingListenerMessages;
        gpslocationprovider.mPendingListenerMessages = i - 1;
        return i;
    }

*/




/*
    static Handler access$2102(GpsLocationProvider gpslocationprovider, Handler handler) {
        gpslocationprovider.mHandler = handler;
        return handler;
    }

*/








}
