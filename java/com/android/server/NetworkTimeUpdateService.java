// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.util.NtpTrustedTime;
import android.util.TrustedTime;

public class NetworkTimeUpdateService {
    private static class SettingsObserver extends ContentObserver {

        void observe(Context context) {
            context.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("auto_time"), false, this);
        }

        public void onChange(boolean flag) {
            mHandler.obtainMessage(mMsg).sendToTarget();
        }

        private Handler mHandler;
        private int mMsg;

        SettingsObserver(Handler handler, int i) {
            super(handler);
            mHandler = handler;
            mMsg = i;
        }
    }

    private class MyHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 3: default 32
        //                       1 33
        //                       2 33
        //                       3 33;
               goto _L1 _L2 _L2 _L2
_L1:
            return;
_L2:
            onPollNetworkTime(message.what);
            if(true) goto _L1; else goto _L3
_L3:
        }

        final NetworkTimeUpdateService this$0;

        public MyHandler(Looper looper) {
            this$0 = NetworkTimeUpdateService.this;
            super(looper);
        }
    }


    public NetworkTimeUpdateService(Context context) {
        mNitzTimeSetTime = -1L;
        mNitzZoneSetTime = -1L;
        mLastNtpFetchTime = -1L;
        mNitzReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent1) {
                String s = intent1.getAction();
                if(!"android.intent.action.NETWORK_SET_TIME".equals(s)) goto _L2; else goto _L1
_L1:
                mNitzTimeSetTime = SystemClock.elapsedRealtime();
_L4:
                return;
_L2:
                if("android.intent.action.NETWORK_SET_TIMEZONE".equals(s))
                    mNitzZoneSetTime = SystemClock.elapsedRealtime();
                if(true) goto _L4; else goto _L3
_L3:
            }

            final NetworkTimeUpdateService this$0;

             {
                this$0 = NetworkTimeUpdateService.this;
                super();
            }
        };
        mConnectivityReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent1) {
                if("android.net.conn.CONNECTIVITY_CHANGE".equals(intent1.getAction())) {
                    NetworkInfo networkinfo = ((ConnectivityManager)context1.getSystemService("connectivity")).getActiveNetworkInfo();
                    if(networkinfo != null && networkinfo.getState() == android.net.NetworkInfo.State.CONNECTED && (networkinfo.getType() == 1 || networkinfo.getType() == 9))
                        mHandler.obtainMessage(3).sendToTarget();
                }
            }

            final NetworkTimeUpdateService this$0;

             {
                this$0 = NetworkTimeUpdateService.this;
                super();
            }
        };
        mContext = context;
        mTime = NtpTrustedTime.getInstance(context);
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        Intent intent = new Intent("com.android.server.NetworkTimeUpdateService.action.POLL", null);
        mPendingPollIntent = PendingIntent.getBroadcast(mContext, POLL_REQUEST, intent, 0);
    }

    private boolean isAutomaticTimeRequested() {
        boolean flag = false;
        if(android.provider.Settings.System.getInt(mContext.getContentResolver(), "auto_time", 0) != 0)
            flag = true;
        return flag;
    }

    private void onPollNetworkTime(int i) {
        if(isAutomaticTimeRequested()) goto _L2; else goto _L1
_L1:
        return;
_L2:
label0:
        {
            long l = SystemClock.elapsedRealtime();
            if(mNitzTimeSetTime != -1L && l - mNitzTimeSetTime < 0x5265c00L) {
                resetAlarm(0x5265c00L);
                continue; /* Loop/switch isn't completed */
            }
            long l1 = System.currentTimeMillis();
            if(mLastNtpFetchTime == -1L || l >= 0x5265c00L + mLastNtpFetchTime || i == 1) {
                if(mTime.getCacheAge() >= 0x5265c00L)
                    mTime.forceRefresh();
                if(mTime.getCacheAge() >= 0x5265c00L)
                    break label0;
                long l2 = mTime.currentTimeMillis();
                mTryAgainCounter = 0;
                if((Math.abs(l2 - l1) > 5000L || mLastNtpFetchTime == -1L) && l2 / 1000L < 0x7fffffffL)
                    SystemClock.setCurrentTimeMillis(l2);
                mLastNtpFetchTime = SystemClock.elapsedRealtime();
            }
            resetAlarm(0x5265c00L);
            continue; /* Loop/switch isn't completed */
        }
        mTryAgainCounter = 1 + mTryAgainCounter;
        if(mTryAgainCounter <= 3) {
            resetAlarm(60000L);
        } else {
            mTryAgainCounter = 0;
            resetAlarm(0x5265c00L);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void registerForAlarms() {
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                mHandler.obtainMessage(2).sendToTarget();
            }

            final NetworkTimeUpdateService this$0;

             {
                this$0 = NetworkTimeUpdateService.this;
                super();
            }
        }, new IntentFilter("com.android.server.NetworkTimeUpdateService.action.POLL"));
    }

    private void registerForConnectivityIntents() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mConnectivityReceiver, intentfilter);
    }

    private void registerForTelephonyIntents() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.NETWORK_SET_TIME");
        intentfilter.addAction("android.intent.action.NETWORK_SET_TIMEZONE");
        mContext.registerReceiver(mNitzReceiver, intentfilter);
    }

    private void resetAlarm(long l) {
        mAlarmManager.cancel(mPendingPollIntent);
        long l1 = l + SystemClock.elapsedRealtime();
        mAlarmManager.set(3, l1, mPendingPollIntent);
    }

    public void systemReady() {
        registerForTelephonyIntents();
        registerForAlarms();
        registerForConnectivityIntents();
        mThread = new HandlerThread("NetworkTimeUpdateService");
        mThread.start();
        mHandler = new MyHandler(mThread.getLooper());
        mHandler.obtainMessage(2).sendToTarget();
        mSettingsObserver = new SettingsObserver(mHandler, 1);
        mSettingsObserver.observe(mContext);
    }

    private static final String ACTION_POLL = "com.android.server.NetworkTimeUpdateService.action.POLL";
    private static final boolean DBG = false;
    private static final int EVENT_AUTO_TIME_CHANGED = 1;
    private static final int EVENT_NETWORK_CONNECTED = 3;
    private static final int EVENT_POLL_NETWORK_TIME = 2;
    private static final long NOT_SET = -1L;
    private static final long POLLING_INTERVAL_MS = 0x5265c00L;
    private static final long POLLING_INTERVAL_SHORTER_MS = 60000L;
    private static int POLL_REQUEST = 0;
    private static final String TAG = "NetworkTimeUpdateService";
    private static final int TIME_ERROR_THRESHOLD_MS = 5000;
    private static final int TRY_AGAIN_TIMES_MAX = 3;
    private AlarmManager mAlarmManager;
    private BroadcastReceiver mConnectivityReceiver;
    private Context mContext;
    private Handler mHandler;
    private long mLastNtpFetchTime;
    private BroadcastReceiver mNitzReceiver;
    private long mNitzTimeSetTime;
    private long mNitzZoneSetTime;
    private PendingIntent mPendingPollIntent;
    private SettingsObserver mSettingsObserver;
    private HandlerThread mThread;
    private TrustedTime mTime;
    private int mTryAgainCounter;

    static  {
        POLL_REQUEST = 0;
    }



/*
    static long access$102(NetworkTimeUpdateService networktimeupdateservice, long l) {
        networktimeupdateservice.mNitzTimeSetTime = l;
        return l;
    }

*/


/*
    static long access$202(NetworkTimeUpdateService networktimeupdateservice, long l) {
        networktimeupdateservice.mNitzZoneSetTime = l;
        return l;
    }

*/

}
