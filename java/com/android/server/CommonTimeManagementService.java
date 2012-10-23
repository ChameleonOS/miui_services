// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.net.INetworkManagementEventObserver;
import android.net.InterfaceConfiguration;
import android.os.*;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

class CommonTimeManagementService extends Binder {
    private static class InterfaceScoreRule {

        public final String mPrefix;
        public final byte mScore;

        public InterfaceScoreRule(String s, byte byte0) {
            mPrefix = s;
            mScore = byte0;
        }
    }


    public CommonTimeManagementService(Context context) {
        mReconnectHandler = new Handler();
        mNoInterfaceHandler = new Handler();
        mLock = new Object();
        mDetectedAtStartup = false;
        mEffectivePrio = BASE_SERVER_PRIO;
        mIfaceObserver = new android.net.INetworkManagementEventObserver.Stub() {

            public void interfaceAdded(String s) {
                reevaluateServiceState();
            }

            public void interfaceLinkStateChanged(String s, boolean flag) {
                reevaluateServiceState();
            }

            public void interfaceRemoved(String s) {
                reevaluateServiceState();
            }

            public void interfaceStatusChanged(String s, boolean flag) {
                reevaluateServiceState();
            }

            public void limitReached(String s, String s1) {
            }

            final CommonTimeManagementService this$0;

             {
                this$0 = CommonTimeManagementService.this;
                super();
            }
        };
        mConnectivityMangerObserver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                reevaluateServiceState();
            }

            final CommonTimeManagementService this$0;

             {
                this$0 = CommonTimeManagementService.this;
                super();
            }
        };
        mCTServerDiedListener = new android.os.CommonTimeConfig.OnServerDiedListener() {

            public void onServerDied() {
                scheduleTimeConfigReconnect();
            }

            final CommonTimeManagementService this$0;

             {
                this$0 = CommonTimeManagementService.this;
                super();
            }
        };
        mReconnectRunnable = new Runnable() {

            public void run() {
                connectToTimeConfig();
            }

            final CommonTimeManagementService this$0;

             {
                this$0 = CommonTimeManagementService.this;
                super();
            }
        };
        mNoInterfaceRunnable = new Runnable() {

            public void run() {
                handleNoInterfaceTimeout();
            }

            final CommonTimeManagementService this$0;

             {
                this$0 = CommonTimeManagementService.this;
                super();
            }
        };
        mContext = context;
    }

    private void cleanupTimeConfig() {
        mReconnectHandler.removeCallbacks(mReconnectRunnable);
        mNoInterfaceHandler.removeCallbacks(mNoInterfaceRunnable);
        if(mCTConfig != null) {
            mCTConfig.release();
            mCTConfig = null;
        }
    }

    private void connectToTimeConfig() {
        cleanupTimeConfig();
        synchronized(mLock) {
            mCTConfig = new CommonTimeConfig();
            mCTConfig.setServerDiedListener(mCTServerDiedListener);
            mCurIface = mCTConfig.getInterfaceBinding();
            mCTConfig.setAutoDisable(AUTO_DISABLE);
            mCTConfig.setMasterElectionPriority(mEffectivePrio);
        }
        if(NO_INTERFACE_TIMEOUT >= 0)
            mNoInterfaceHandler.postDelayed(mNoInterfaceRunnable, NO_INTERFACE_TIMEOUT);
        reevaluateServiceState();
_L1:
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        try {
            throw exception;
        }
        catch(RemoteException remoteexception) {
            scheduleTimeConfigReconnect();
        }
          goto _L1
    }

    private void handleNoInterfaceTimeout() {
        if(mCTConfig != null) {
            Log.i(TAG, "Timeout waiting for interface to come up.  Forcing networkless master mode.");
            if(-7 == mCTConfig.forceNetworklessMasterMode())
                scheduleTimeConfigReconnect();
        }
    }

    private void reevaluateServiceState() {
        Object obj;
        byte byte0;
        obj = null;
        byte0 = -1;
        String as[] = mNetMgr.listInterfaces();
        if(as == null) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        j = as.length;
        k = 0;
_L11:
        if(k >= j) goto _L2; else goto _L3
_L3:
        String s2;
        byte byte1;
        InterfaceScoreRule ainterfacescorerule[];
        int l;
        int i1;
        s2 = as[k];
        byte1 = -1;
        ainterfacescorerule = IFACE_SCORE_RULES;
        l = ainterfacescorerule.length;
        i1 = 0;
_L12:
        if(i1 < l) {
            InterfaceScoreRule interfacescorerule = ainterfacescorerule[i1];
            if(!s2.contains(interfacescorerule.mPrefix))
                break MISSING_BLOCK_LABEL_441;
            byte1 = interfacescorerule.mScore;
        }
          goto _L4
_L10:
        boolean flag1;
        InterfaceConfiguration interfaceconfiguration = mNetMgr.getInterfaceConfig(s2);
        if(interfaceconfiguration == null)
            break; /* Loop/switch isn't completed */
        flag1 = interfaceconfiguration.isActive();
        if(flag1) {
            obj = s2;
            byte0 = byte1;
        }
        break; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        obj = null;
_L2:
        boolean flag = true;
        Object obj1 = mLock;
        obj1;
        JVM INSTR monitorenter ;
        if(obj == null) goto _L6; else goto _L5
_L5:
        if(mCurIface != null) goto _L6; else goto _L7
_L7:
        String s1 = TAG;
        Object aobj1[] = new Object[1];
        aobj1[0] = obj;
        Log.e(s1, String.format("Binding common time service to %s.", aobj1));
        mCurIface = ((String) (obj));
_L8:
        obj1;
        JVM INSTR monitorexit ;
        if(flag && mCTConfig != null) {
            Exception exception;
            int i;
            String s;
            Object aobj[];
            if(byte0 > 0)
                i = byte0 * BASE_SERVER_PRIO;
            else
                i = BASE_SERVER_PRIO;
            if(i != mEffectivePrio) {
                mEffectivePrio = i;
                mCTConfig.setMasterElectionPriority(mEffectivePrio);
            }
            if(mCTConfig.setNetworkBinding(mCurIface) != 0)
                scheduleTimeConfigReconnect();
            else
            if(NO_INTERFACE_TIMEOUT >= 0) {
                mNoInterfaceHandler.removeCallbacks(mNoInterfaceRunnable);
                if(mCurIface == null)
                    mNoInterfaceHandler.postDelayed(mNoInterfaceRunnable, NO_INTERFACE_TIMEOUT);
            }
        }
        return;
_L6:
        if(obj != null || mCurIface == null)
            break MISSING_BLOCK_LABEL_304;
        Log.e(TAG, "Unbinding common time service.");
        mCurIface = null;
          goto _L8
        exception;
        throw exception;
        if(obj == null)
            break MISSING_BLOCK_LABEL_372;
        if(mCurIface == null || ((String) (obj)).equals(mCurIface))
            break MISSING_BLOCK_LABEL_372;
        s = TAG;
        aobj = new Object[2];
        aobj[0] = mCurIface;
        aobj[1] = obj;
        Log.e(s, String.format("Switching common time service binding from %s to %s.", aobj));
        mCurIface = ((String) (obj));
          goto _L8
        flag = false;
          goto _L8
_L4:
        if(byte1 > byte0) goto _L10; else goto _L9
_L9:
        k++;
          goto _L11
        i1++;
          goto _L12
    }

    private void scheduleTimeConfigReconnect() {
        cleanupTimeConfig();
        String s = TAG;
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(5000);
        Log.w(s, String.format("Native service died, will reconnect in %d mSec", aobj));
        mReconnectHandler.postDelayed(mReconnectRunnable, 5000L);
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            Object aobj6[] = new Object[2];
            aobj6[0] = Integer.valueOf(Binder.getCallingPid());
            aobj6[1] = Integer.valueOf(Binder.getCallingUid());
            printwriter.println(String.format("Permission Denial: can't dump CommonTimeManagement service from from pid=%d, uid=%d", aobj6));
        } else {
label0:
            {
                if(mDetectedAtStartup)
                    break label0;
                printwriter.println("Native Common Time service was not detected at startup.  Service is unavailable");
            }
        }
_L5:
        return;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Object aobj[];
        printwriter.println("Current Common Time Management Service Config:");
        aobj = new Object[1];
        if(mCTConfig != null) goto _L2; else goto _L1
_L1:
        String s = "reconnecting";
_L6:
        Object aobj1[];
        aobj[0] = s;
        printwriter.println(String.format("  Native service     : %s", aobj));
        aobj1 = new Object[1];
        if(mCurIface != null) goto _L4; else goto _L3
_L3:
        String s1 = "unbound";
_L7:
        aobj1[0] = s1;
        printwriter.println(String.format("  Bound interface    : %s", aobj1));
        Object aobj2[] = new Object[1];
        Exception exception;
        String s2;
        Object aobj3[];
        String s3;
        Object aobj4[];
        Object aobj5[];
        if(ALLOW_WIFI)
            s2 = "yes";
        else
            s2 = "no";
        aobj2[0] = s2;
        printwriter.println(String.format("  Allow WiFi         : %s", aobj2));
        aobj3 = new Object[1];
        if(!AUTO_DISABLE)
            break MISSING_BLOCK_LABEL_315;
        s3 = "yes";
_L8:
        aobj3[0] = s3;
        printwriter.println(String.format("  Allow Auto Disable : %s", aobj3));
        aobj4 = new Object[1];
        aobj4[0] = Byte.valueOf(mEffectivePrio);
        printwriter.println(String.format("  Server Priority    : %d", aobj4));
        aobj5 = new Object[1];
        aobj5[0] = Integer.valueOf(NO_INTERFACE_TIMEOUT);
        printwriter.println(String.format("  No iface timeout   : %d", aobj5));
          goto _L5
        exception;
        throw exception;
_L2:
        s = "alive";
          goto _L6
_L4:
        s1 = mCurIface;
          goto _L7
        s3 = "no";
          goto _L8
    }

    void systemReady() {
        if(ServiceManager.checkService("common_time.config") == null) {
            Log.i(TAG, "No common time service detected on this platform.  Common time services will be unavailable.");
        } else {
            mDetectedAtStartup = true;
            mNetMgr = android.os.INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
            IntentFilter intentfilter;
            try {
                mNetMgr.registerObserver(mIfaceObserver);
            }
            catch(RemoteException remoteexception) { }
            intentfilter = new IntentFilter();
            intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            mContext.registerReceiver(mConnectivityMangerObserver, intentfilter);
            connectToTimeConfig();
        }
    }

    private static final boolean ALLOW_WIFI = false;
    private static final String ALLOW_WIFI_PROP = "ro.common_time.allow_wifi";
    private static final boolean AUTO_DISABLE = false;
    private static final String AUTO_DISABLE_PROP = "ro.common_time.auto_disable";
    private static final byte BASE_SERVER_PRIO = 0;
    private static final InterfaceScoreRule IFACE_SCORE_RULES[];
    private static final int NATIVE_SERVICE_RECONNECT_TIMEOUT = 5000;
    private static final int NO_INTERFACE_TIMEOUT = 0;
    private static final String NO_INTERFACE_TIMEOUT_PROP = "ro.common_time.no_iface_timeout";
    private static final String SERVER_PRIO_PROP = "ro.common_time.server_prio";
    private static final String TAG = com/android/server/CommonTimeManagementService.getSimpleName();
    private CommonTimeConfig mCTConfig;
    private android.os.CommonTimeConfig.OnServerDiedListener mCTServerDiedListener;
    private BroadcastReceiver mConnectivityMangerObserver;
    private final Context mContext;
    private String mCurIface;
    private boolean mDetectedAtStartup;
    private byte mEffectivePrio;
    private INetworkManagementEventObserver mIfaceObserver;
    private Object mLock;
    private INetworkManagementService mNetMgr;
    private Handler mNoInterfaceHandler;
    private Runnable mNoInterfaceRunnable;
    private Handler mReconnectHandler;
    private Runnable mReconnectRunnable;

    static  {
        boolean flag;
        boolean flag1;
        int i;
        if(SystemProperties.getInt("ro.common_time.auto_disable", 1) != 0)
            flag = true;
        else
            flag = false;
        AUTO_DISABLE = flag;
        if(SystemProperties.getInt("ro.common_time.allow_wifi", 0) != 0)
            flag1 = true;
        else
            flag1 = false;
        ALLOW_WIFI = flag1;
        i = SystemProperties.getInt("ro.common_time.server_prio", 1);
        NO_INTERFACE_TIMEOUT = SystemProperties.getInt("ro.common_time.no_iface_timeout", 60000);
        if(i < 1)
            BASE_SERVER_PRIO = 1;
        else
        if(i > 30)
            BASE_SERVER_PRIO = 30;
        else
            BASE_SERVER_PRIO = i;
        if(ALLOW_WIFI) {
            InterfaceScoreRule ainterfacescorerule1[] = new InterfaceScoreRule[2];
            ainterfacescorerule1[0] = new InterfaceScoreRule("wlan", (byte)1);
            ainterfacescorerule1[1] = new InterfaceScoreRule("eth", (byte)2);
            IFACE_SCORE_RULES = ainterfacescorerule1;
        } else {
            InterfaceScoreRule ainterfacescorerule[] = new InterfaceScoreRule[1];
            ainterfacescorerule[0] = new InterfaceScoreRule("eth", (byte)2);
            IFACE_SCORE_RULES = ainterfacescorerule;
        }
    }




}
