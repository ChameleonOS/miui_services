// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.bluetooth.BluetoothTetheringDataTracker;
import android.content.*;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.*;
import android.net.wifi.WifiStateTracker;
import android.os.*;
import android.text.TextUtils;
import android.util.*;
import com.android.internal.app.IBatteryStats;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.server.am.BatteryStatsService;
import com.android.server.connectivity.Tethering;
import com.android.server.connectivity.Vpn;
import com.google.android.collect.Lists;
import com.google.android.collect.Sets;
import com.miui.server.FirewallService;
import dalvik.system.DexClassLoader;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.*;
import miui.net.FirewallManager;

public class ConnectivityService extends android.net.IConnectivityManager.Stub {
    public class VpnCallback {

        public void override(List list, List list1) {
            if(list != null) goto _L2; else goto _L1
_L1:
            restore();
_L4:
            return;
_L2:
            ArrayList arraylist = new ArrayList();
            for(Iterator iterator = list.iterator(); iterator.hasNext();) {
                String s1 = (String)iterator.next();
                try {
                    arraylist.add(InetAddress.parseNumericAddress(s1));
                }
                catch(Exception exception1) { }
            }

            if(arraylist.isEmpty()) {
                restore();
                continue; /* Loop/switch isn't completed */
            }
            StringBuilder stringbuilder = new StringBuilder();
            if(list1 != null) {
                for(Iterator iterator1 = list1.iterator(); iterator1.hasNext(); stringbuilder.append((String)iterator1.next()).append(' '));
            }
            String s = stringbuilder.toString().trim();
            boolean flag;
            synchronized(mDnsLock) {
                flag = updateDns("VPN", "VPN", arraylist, s);
                mDnsOverridden = true;
            }
            if(flag)
                bumpDns();
            Object obj1 = mDefaultProxyLock;
            obj1;
            JVM INSTR monitorenter ;
            mDefaultProxyDisabled = true;
            if(mDefaultProxy != null)
                sendProxyBroadcast(null);
            if(true) goto _L4; else goto _L3
_L3:
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public void restore() {
            synchronized(mDnsLock) {
                if(mDnsOverridden) {
                    mDnsOverridden = false;
                    mHandler.sendEmptyMessage(111);
                }
            }
            synchronized(mDefaultProxyLock) {
                mDefaultProxyDisabled = false;
                if(mDefaultProxy != null)
                    sendProxyBroadcast(mDefaultProxy);
            }
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
            exception1;
            obj1;
            JVM INSTR monitorexit ;
            throw exception1;
        }

        final ConnectivityService this$0;

        private VpnCallback() {
            this$0 = ConnectivityService.this;
            super();
        }

    }

    private static class SettingsObserver extends ContentObserver {

        void observe(Context context) {
            context.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("http_proxy"), false, this);
        }

        public void onChange(boolean flag) {
            mHandler.obtainMessage(mWhat).sendToTarget();
        }

        private Handler mHandler;
        private int mWhat;

        SettingsObserver(Handler handler, int i) {
            super(handler);
            mHandler = handler;
            mWhat = i;
        }
    }

    private class MyHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR lookupswitch 13: default 120
        //                       1: 121
        //                       3: 332
        //                       101: 466
        //                       103: 531
        //                       104: 479
        //                       105: 505
        //                       107: 549
        //                       108: 357
        //                       109: 578
        //                       110: 588
        //                       111: 621
        //                       112: 650
        //                       113: 671;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14
_L1:
            return;
_L2:
            NetworkInfo networkinfo1 = (NetworkInfo)message.obj;
            networkinfo1.getType();
            android.net.NetworkInfo.State state = networkinfo1.getState();
            if(state == android.net.NetworkInfo.State.CONNECTED || state == android.net.NetworkInfo.State.DISCONNECTED)
                log((new StringBuilder()).append("ConnectivityChange for ").append(networkinfo1.getTypeName()).append(": ").append(state).append("/").append(networkinfo1.getDetailedState()).toString());
            EventLog.writeEvent(50020, 0xf & networkinfo1.getType() | (0x3f & networkinfo1.getDetailedState().ordinal()) << 4 | networkinfo1.getSubtype() << 10);
            if(networkinfo1.getDetailedState() == android.net.NetworkInfo.DetailedState.FAILED)
                handleConnectionFailure(networkinfo1);
            else
            if(state == android.net.NetworkInfo.State.DISCONNECTED)
                handleDisconnect(networkinfo1);
            else
            if(state == android.net.NetworkInfo.State.SUSPENDED)
                handleDisconnect(networkinfo1);
            else
            if(state == android.net.NetworkInfo.State.CONNECTED)
                handleConnect(networkinfo1);
            continue; /* Loop/switch isn't completed */
_L3:
            NetworkInfo networkinfo = (NetworkInfo)message.obj;
            handleConnectivityChange(networkinfo.getType(), false);
            continue; /* Loop/switch isn't completed */
_L9:
            String s = null;
            synchronized(ConnectivityService.this) {
                if(message.arg1 == mNetTransitionWakeLockSerialNumber && mNetTransitionWakeLock.isHeld()) {
                    mNetTransitionWakeLock.release();
                    s = mNetTransitionWakeLockCausedBy;
                }
            }
            if(s != null)
                log((new StringBuilder()).append("NetTransition Wakelock for ").append(s).append(" released by timeout").toString());
            continue; /* Loop/switch isn't completed */
            exception;
            connectivityservice;
            JVM INSTR monitorexit ;
            throw exception;
_L4:
            ((FeatureUser)message.obj).expire();
            continue; /* Loop/switch isn't completed */
_L6:
            int i1 = message.arg1;
            int j1 = message.arg2;
            handleInetConditionChange(i1, j1);
            continue; /* Loop/switch isn't completed */
_L7:
            int k = message.arg1;
            int l = message.arg2;
            handleInetConditionHoldEnd(k, l);
            continue; /* Loop/switch isn't completed */
_L5:
            int j = message.arg1;
            handleSetNetworkPreference(j);
            continue; /* Loop/switch isn't completed */
_L8:
            boolean flag2;
            if(message.arg1 == 1)
                flag2 = true;
            else
                flag2 = false;
            handleSetMobileData(flag2);
            continue; /* Loop/switch isn't completed */
_L10:
            handleDeprecatedGlobalHttpProxy();
            continue; /* Loop/switch isn't completed */
_L11:
            boolean flag1;
            if(message.arg1 == 1)
                flag1 = true;
            else
                flag1 = false;
            handleSetDependencyMet(message.arg2, flag1);
            continue; /* Loop/switch isn't completed */
_L12:
            if(mActiveDefaultNetwork != -1)
                handleDnsConfigurationChange(mActiveDefaultNetwork);
            continue; /* Loop/switch isn't completed */
_L13:
            Intent intent = (Intent)message.obj;
            sendStickyBroadcast(intent);
            continue; /* Loop/switch isn't completed */
_L14:
            int i = message.arg1;
            boolean flag;
            if(message.arg2 == 1)
                flag = true;
            else
                flag = false;
            handleSetPolicyDataEnable(i, flag);
            if(true) goto _L1; else goto _L15
_L15:
        }

        final ConnectivityService this$0;

        public MyHandler(Looper looper) {
            this$0 = ConnectivityService.this;
            super(looper);
        }
    }

    private class FeatureUser
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            log((new StringBuilder()).append("ConnectivityService FeatureUser binderDied(").append(mNetworkType).append(", ").append(mFeature).append(", ").append(mBinder).append("), created ").append(System.currentTimeMillis() - mCreateTime).append(" mSec ago").toString());
            stopUsingNetworkFeature(this, false);
        }

        public void expire() {
            stopUsingNetworkFeature(this, false);
        }

        public boolean isSameUser(int i, int j, int k, String s) {
            boolean flag;
            if(mPid == i && mUid == j && mNetworkType == k && TextUtils.equals(mFeature, s))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isSameUser(FeatureUser featureuser) {
            boolean flag;
            if(featureuser == null)
                flag = false;
            else
                flag = isSameUser(featureuser.mPid, featureuser.mUid, featureuser.mNetworkType, featureuser.mFeature);
            return flag;
        }

        public String toString() {
            return (new StringBuilder()).append("FeatureUser(").append(mNetworkType).append(",").append(mFeature).append(",").append(mPid).append(",").append(mUid).append("), created ").append(System.currentTimeMillis() - mCreateTime).append(" mSec ago").toString();
        }

        void unlinkDeathRecipient() {
            mBinder.unlinkToDeath(this, 0);
        }

        IBinder mBinder;
        long mCreateTime;
        String mFeature;
        int mNetworkType;
        int mPid;
        int mUid;
        final ConnectivityService this$0;

        FeatureUser(int i, String s, IBinder ibinder) {
            this$0 = ConnectivityService.this;
            super();
            mNetworkType = i;
            mFeature = s;
            mBinder = ibinder;
            mPid = Binder.getCallingPid();
            mUid = Binder.getCallingUid();
            mCreateTime = System.currentTimeMillis();
            mBinder.linkToDeath(this, 0);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            binderDied();
              goto _L1
        }
    }

    private static class RadioAttributes {

        public int mSimultaneity;
        public int mType;

        public RadioAttributes(String s) {
            String as[] = s.split(",");
            mType = Integer.parseInt(as[0]);
            mSimultaneity = Integer.parseInt(as[1]);
        }
    }

    static class Injector {

        static void startUsingNetworkFeature(int i) {
            FirewallManager.getInstance().onStartUsingNetworkFeature(Binder.getCallingUid(), Binder.getCallingPid(), i);
        }

        public static void stopUsingNetworkFeature(FeatureUser featureuser, int i) {
            FirewallManager.getInstance().onStopUsingNetworkFeature(featureuser.mUid, featureuser.mPid, i);
        }

        Injector() {
        }
    }


    public ConnectivityService(Context context, INetworkManagementService inetworkmanagementservice, INetworkStatsService inetworkstatsservice, INetworkPolicyManager inetworkpolicymanager) {
        String as1[];
        int k;
        int l;
        mTetheringConfigValid = false;
        mRulesLock = new Object();
        mUidRules = new SparseIntArray();
        mMeteredIfaces = Sets.newHashSet();
        mActiveDefaultNetwork = -1;
        mDefaultInetCondition = 0;
        mDefaultInetConditionPublished = 0;
        mInetConditionChangeInFlight = false;
        mDefaultConnectionSequence = 0;
        mDnsLock = new Object();
        mDnsOverridden = false;
        mNetTransitionWakeLockCausedBy = "";
        mAddedRoutes = new ArrayList();
        mDefaultProxy = null;
        mDefaultProxyLock = new Object();
        mDefaultProxyDisabled = false;
        mGlobalProxy = null;
        mGlobalProxyLock = new Object();
        mPolicyListener = new android.net.INetworkPolicyListener.Stub() {

            public void onMeteredIfacesChanged(String as2[]) {
                Object obj = mRulesLock;
                obj;
                JVM INSTR monitorenter ;
                mMeteredIfaces.clear();
                int i5 = as2.length;
                for(int j5 = 0; j5 < i5; j5++) {
                    String s3 = as2[j5];
                    mMeteredIfaces.add(s3);
                }

                return;
            }

            public void onRestrictBackgroundChanged(boolean flag2) {
                int i5 = mActiveDefaultNetwork;
                if(ConnectivityManager.isNetworkTypeValid(i5)) {
                    NetworkStateTracker networkstatetracker = mNetTrackers[i5];
                    if(networkstatetracker != null) {
                        NetworkInfo networkinfo = networkstatetracker.getNetworkInfo();
                        if(networkinfo != null && networkinfo.isConnected())
                            sendConnectedBroadcast(networkinfo);
                    }
                }
            }

            public void onUidRulesChanged(int i5, int j5) {
                Object obj = mRulesLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUidRules.get(i5, 0) != j5)
                    mUidRules.put(i5, j5);
                return;
            }

            final ConnectivityService this$0;

             {
                this$0 = ConnectivityService.this;
                super();
            }
        };
        log("ConnectivityService starting up");
        FirewallService.setupService(context);
        HandlerThread handlerthread = new HandlerThread("ConnectivityServiceThread");
        handlerthread.start();
        mHandler = new MyHandler(handlerthread.getLooper());
        if(TextUtils.isEmpty(SystemProperties.get("net.hostname"))) {
            String s2 = android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
            if(s2 != null && s2.length() > 0)
                SystemProperties.set("net.hostname", (new String("android-")).concat(s2));
        }
        String s = android.provider.Settings.Secure.getString(context.getContentResolver(), "default_dns_server");
        if(s == null || s.length() == 0)
            s = context.getResources().getString(0x104001f);
        String as[];
        int i;
        int j;
        try {
            mDefaultDns = NetworkUtils.numericToInetAddress(s);
        }
        catch(IllegalArgumentException illegalargumentexception) {
            loge((new StringBuilder()).append("Error setting defaultDns using ").append(s).toString());
        }
        mContext = (Context)checkNotNull(context, "missing Context");
        mNetd = (INetworkManagementService)checkNotNull(inetworkmanagementservice, "missing INetworkManagementService");
        mPolicyManager = (INetworkPolicyManager)checkNotNull(inetworkpolicymanager, "missing INetworkPolicyManager");
        try {
            mPolicyManager.registerListener(mPolicyListener);
        }
        catch(RemoteException remoteexception) {
            loge((new StringBuilder()).append("unable to register INetworkPolicyListener").append(remoteexception.toString()).toString());
        }
        mNetTransitionWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "ConnectivityService");
        mNetTransitionWakeLockTimeout = mContext.getResources().getInteger(0x10e0009);
        mNetTrackers = new NetworkStateTracker[14];
        mCurrentLinkProperties = new LinkProperties[14];
        mNetworkPreference = getPersistedNetworkPreference();
        mRadioAttributes = new RadioAttributes[14];
        mNetConfigs = new NetworkConfig[14];
        as = context.getResources().getStringArray(0x1070015);
        i = as.length;
        j = 0;
        while(j < i)  {
            RadioAttributes radioattributes = new RadioAttributes(as[j]);
            if(radioattributes.mType > 13)
                loge((new StringBuilder()).append("Error in radioAttributes - ignoring attempt to define type ").append(radioattributes.mType).toString());
            else
            if(mRadioAttributes[radioattributes.mType] != null)
                loge((new StringBuilder()).append("Error in radioAttributes - ignoring attempt to redefine type ").append(radioattributes.mType).toString());
            else
                mRadioAttributes[radioattributes.mType] = radioattributes;
            j++;
        }
        as1 = context.getResources().getStringArray(0x1070013);
        k = as1.length;
        l = 0;
_L11:
        {
            if(l < k) {
                String s1 = as1[l];
                int ai[];
                int i1;
                int j1;
                int k1;
                int l1;
                int i2;
                int ai1[];
                int j2;
                int k2;
                boolean flag;
                int ai2[];
                int l2;
                int i3;
                INetworkManagementService inetworkmanagementservice1;
                boolean flag1;
                RemoteException remoteexception1;
                int j3;
                int k3;
                NetworkConfig anetworkconfig[];
                int l3;
                int i4;
                int j4;
                NetworkConfig networkconfig;
                int ai3[];
                int k4;
                int l4;
                try {
                    NetworkConfig networkconfig1 = new NetworkConfig(s1);
                    if(networkconfig1.type > 13)
                        loge((new StringBuilder()).append("Error in networkAttributes - ignoring attempt to define type ").append(networkconfig1.type).toString());
                    else
                    if(mNetConfigs[networkconfig1.type] != null)
                        loge((new StringBuilder()).append("Error in networkAttributes - ignoring attempt to redefine type ").append(networkconfig1.type).toString());
                    else
                    if(mRadioAttributes[networkconfig1.radio] == null) {
                        loge((new StringBuilder()).append("Error in networkAttributes - ignoring attempt to use undefined radio ").append(networkconfig1.radio).append(" in network type ").append(networkconfig1.type).toString());
                    } else {
                        mNetConfigs[networkconfig1.type] = networkconfig1;
                        mNetworksDefined = 1 + mNetworksDefined;
                    }
                }
                catch(Exception exception) { }
                break MISSING_BLOCK_LABEL_1903;
            }
            mProtectedNetworks = new ArrayList();
            ai = context.getResources().getIntArray(0x1070014);
            i1 = ai.length;
            j1 = 0;
            while(j1 < i1)  {
                l4 = ai[j1];
                if(mNetConfigs[l4] != null && !mProtectedNetworks.contains(Integer.valueOf(l4)))
                    mProtectedNetworks.add(Integer.valueOf(l4));
                else
                    loge((new StringBuilder()).append("Ignoring protectedNetwork ").append(l4).toString());
                j1++;
            }
            mPriorityList = new int[mNetworksDefined];
            k1 = -1 + mNetworksDefined;
            l1 = 0;
            i2 = 0;
            while(k1 > -1)  {
                anetworkconfig = mNetConfigs;
                l3 = anetworkconfig.length;
                i4 = 0;
                j4 = k1;
                while(i4 < l3)  {
                    networkconfig = anetworkconfig[i4];
                    if(networkconfig == null)
                        k4 = j4;
                    else
                    if(networkconfig.priority < l1)
                        k4 = j4;
                    else
                    if(networkconfig.priority > l1) {
                        if(networkconfig.priority < i2 || i2 == 0) {
                            i2 = networkconfig.priority;
                            k4 = j4;
                        } else {
                            k4 = j4;
                        }
                    } else {
                        ai3 = mPriorityList;
                        k4 = j4 - 1;
                        ai3[j4] = networkconfig.type;
                    }
                    i4++;
                    j4 = k4;
                }
                l1 = i2;
                i2 = 0;
                k1 = j4;
            }
            mNetRequestersPids = new ArrayList[14];
            ai1 = mPriorityList;
            j2 = ai1.length;
            for(k2 = 0; k2 < j2; k2++) {
                k3 = ai1[k2];
                mNetRequestersPids[k3] = new ArrayList();
            }

            mFeatureUsers = new ArrayList();
            mNumDnsEntries = 0;
            if(SystemProperties.get("cm.test.mode").equals("true") && SystemProperties.get("ro.build.type").equals("eng"))
                flag = true;
            else
                flag = false;
            mTestMode = flag;
            ai2 = mPriorityList;
            l2 = ai2.length;
            i3 = 0;
        }
        if(i3 >= l2)
            break MISSING_BLOCK_LABEL_1680;
        j3 = ai2[i3];
        mNetConfigs[j3].radio;
        JVM INSTR tableswitch 0 9: default 1352
    //                   0 1487
    //                   1 1397
    //                   2 1352
    //                   3 1352
    //                   4 1352
    //                   5 1352
    //                   6 1609
    //                   7 1579
    //                   8 1533
    //                   9 1650;
           goto _L1 _L2 _L3 _L1 _L1 _L1 _L1 _L4 _L5 _L6 _L7
_L7:
        break MISSING_BLOCK_LABEL_1650;
_L1:
        loge((new StringBuilder()).append("Trying to create a DataStateTracker for an unknown radio type ").append(mNetConfigs[j3].radio).toString());
_L8:
        i3++;
        break MISSING_BLOCK_LABEL_1275;
_L3:
        mNetTrackers[j3] = new WifiStateTracker(j3, mNetConfigs[j3].name);
        mNetTrackers[j3].startMonitoring(context, mHandler);
_L9:
        mCurrentLinkProperties[j3] = null;
        if(mNetTrackers[j3] != null && mNetConfigs[j3].isDefault())
            mNetTrackers[j3].reconnect();
        if(true) goto _L8; else goto _L2
_L2:
        mNetTrackers[j3] = new MobileDataStateTracker(j3, mNetConfigs[j3].name);
        mNetTrackers[j3].startMonitoring(context, mHandler);
          goto _L9
_L6:
        mNetTrackers[j3] = new DummyDataStateTracker(j3, mNetConfigs[j3].name);
        mNetTrackers[j3].startMonitoring(context, mHandler);
          goto _L9
_L5:
        mNetTrackers[j3] = BluetoothTetheringDataTracker.getInstance();
        mNetTrackers[j3].startMonitoring(context, mHandler);
          goto _L9
_L4:
        mNetTrackers[j3] = makeWimaxStateTracker();
        if(mNetTrackers[j3] != null)
            mNetTrackers[j3].startMonitoring(context, mHandler);
          goto _L9
        mNetTrackers[j3] = EthernetDataTracker.getInstance();
        mNetTrackers[j3].startMonitoring(context, mHandler);
          goto _L9
        inetworkmanagementservice1 = android.os.INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        mTethering = new Tethering(mContext, inetworkmanagementservice1, inetworkstatsservice, this, mHandler.getLooper());
        if((mTethering.getTetherableUsbRegexs().length != 0 || mTethering.getTetherableWifiRegexs().length != 0 || mTethering.getTetherableBluetoothRegexs().length != 0) && mTethering.getUpstreamIfaceTypes().length != 0)
            flag1 = true;
        else
            flag1 = false;
        mTetheringConfigValid = flag1;
        mVpn = new Vpn(mContext, new VpnCallback());
        try {
            inetworkmanagementservice1.registerObserver(mTethering);
            inetworkmanagementservice1.registerObserver(mVpn);
        }
        // Misplaced declaration of an exception variable
        catch(RemoteException remoteexception1) {
            loge((new StringBuilder()).append("Error registering observer :").append(remoteexception1).toString());
        }
        mInetLog = new ArrayList();
        mSettingsObserver = new SettingsObserver(mHandler, 109);
        mSettingsObserver.observe(mContext);
        loadGlobalProxy();
        return;
        l++;
        if(true) goto _L11; else goto _L10
_L10:
    }

    private boolean addRoute(LinkProperties linkproperties, RouteInfo routeinfo, boolean flag) {
        return modifyRoute(linkproperties.getInterfaceName(), linkproperties, routeinfo, 0, true, flag);
    }

    private boolean addRouteToAddress(LinkProperties linkproperties, InetAddress inetaddress) {
        return modifyRouteToAddress(linkproperties, inetaddress, true, true);
    }

    private void bumpDns() {
        String s;
        int i;
        s = SystemProperties.get("net.dnschange");
        i = 0;
        if(s.length() == 0)
            break MISSING_BLOCK_LABEL_25;
        int j = Integer.parseInt(s);
        i = j;
_L2:
        SystemProperties.set("net.dnschange", (new StringBuilder()).append("").append(i + 1).toString());
        Intent intent = new Intent("android.intent.action.CLEAR_DNS_CACHE");
        intent.addFlags(0x20000000);
        intent.addFlags(0x8000000);
        mContext.sendBroadcast(intent);
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static Object checkNotNull(Object obj, String s) {
        if(obj == null)
            throw new NullPointerException(s);
        else
            return obj;
    }

    private void enforceAccessPermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", "ConnectivityService");
    }

    private void enforceChangePermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_NETWORK_STATE", "ConnectivityService");
    }

    private void enforceConnectivityInternalPermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "ConnectivityService");
    }

    private void enforcePreference() {
        if(!mNetTrackers[mNetworkPreference].getNetworkInfo().isConnected() && mNetTrackers[mNetworkPreference].isAvailable()) {
            int i = 0;
            while(i <= 13)  {
                if(i != mNetworkPreference && mNetTrackers[i] != null && mNetTrackers[i].getNetworkInfo().isConnected()) {
                    log((new StringBuilder()).append("tearing down ").append(mNetTrackers[i].getNetworkInfo()).append(" in enforcePreference").toString());
                    teardown(mNetTrackers[i]);
                }
                i++;
            }
        }
    }

    private void enforceTetherAccessPermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", "ConnectivityService");
    }

    private void enforceTetherChangePermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_NETWORK_STATE", "ConnectivityService");
    }

    private int getConnectivityChangeDelay() {
        return android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "connectivity_change_delay", SystemProperties.getInt("conn.connectivity_change_delay", 3000));
    }

    private NetworkInfo getFilteredNetworkInfo(NetworkStateTracker networkstatetracker, int i) {
        NetworkInfo networkinfo = networkstatetracker.getNetworkInfo();
        if(isNetworkBlocked(networkstatetracker, i)) {
            NetworkInfo networkinfo1 = new NetworkInfo(networkinfo);
            networkinfo1.setDetailedState(android.net.NetworkInfo.DetailedState.BLOCKED, null, null);
            networkinfo = networkinfo1;
        }
        return networkinfo;
    }

    private NetworkInfo getNetworkInfo(int i, int j) {
        NetworkInfo networkinfo = null;
        if(ConnectivityManager.isNetworkTypeValid(i)) {
            NetworkStateTracker networkstatetracker = mNetTrackers[i];
            if(networkstatetracker != null)
                networkinfo = getFilteredNetworkInfo(networkstatetracker, j);
        }
        return networkinfo;
    }

    private NetworkState getNetworkStateUnchecked(int i) {
        if(!ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        NetworkStateTracker networkstatetracker = mNetTrackers[i];
        if(networkstatetracker == null) goto _L2; else goto _L3
_L3:
        NetworkState networkstate = new NetworkState(networkstatetracker.getNetworkInfo(), networkstatetracker.getLinkProperties(), networkstatetracker.getLinkCapabilities());
_L5:
        return networkstate;
_L2:
        networkstate = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private int getPersistedNetworkPreference() {
        int i = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "network_preference", -1);
        if(i == -1)
            i = 1;
        return i;
    }

    private int getRestoreDefaultNetworkDelay(int i) {
        String s = SystemProperties.get("android.telephony.apn-restore");
        if(s == null || s.length() == 0) goto _L2; else goto _L1
_L1:
        int k = Integer.valueOf(s).intValue();
        int j = k;
_L4:
        return j;
        NumberFormatException numberformatexception;
        numberformatexception;
_L2:
        j = 60000;
        if(i <= 13 && mNetConfigs[i] != null)
            j = mNetConfigs[i].restoreTime;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleApplyDefaultProxy(ProxyProperties proxyproperties) {
        if(proxyproperties != null && TextUtils.isEmpty(proxyproperties.getHost()))
            proxyproperties = null;
        Object obj = mDefaultProxyLock;
        obj;
        JVM INSTR monitorenter ;
        if((mDefaultProxy == null || !mDefaultProxy.equals(proxyproperties)) && mDefaultProxy != proxyproperties) goto _L2; else goto _L1
        Exception exception;
        exception;
        throw exception;
_L2:
        mDefaultProxy = proxyproperties;
        if(!mDefaultProxyDisabled)
            sendProxyBroadcast(proxyproperties);
        obj;
        JVM INSTR monitorexit ;
_L1:
    }

    private void handleConnect(NetworkInfo networkinfo) {
        int i;
        NetworkStateTracker networkstatetracker;
        i = networkinfo.getType();
        networkinfo.isFailover();
        networkstatetracker = mNetTrackers[i];
        if(!mNetConfigs[i].isDefault()) goto _L2; else goto _L1
_L1:
        if(mActiveDefaultNetwork == -1 || mActiveDefaultNetwork == i) goto _L4; else goto _L3
_L3:
        if((i == mNetworkPreference || mNetConfigs[mActiveDefaultNetwork].priority <= mNetConfigs[i].priority) && mNetworkPreference != mActiveDefaultNetwork) goto _L6; else goto _L5
_L5:
        teardown(networkstatetracker);
_L8:
        return;
_L6:
        NetworkStateTracker networkstatetracker1 = mNetTrackers[mActiveDefaultNetwork];
        log((new StringBuilder()).append("Policy requires ").append(networkstatetracker1.getNetworkInfo().getTypeName()).append(" teardown").toString());
        if(!teardown(networkstatetracker1)) {
            loge("Network declined teardown request");
            teardown(networkstatetracker);
            continue; /* Loop/switch isn't completed */
        }
_L4:
        this;
        JVM INSTR monitorenter ;
        if(mNetTransitionWakeLock.isHeld())
            mHandler.sendMessageDelayed(mHandler.obtainMessage(108, mNetTransitionWakeLockSerialNumber, 0), 1000L);
        this;
        JVM INSTR monitorexit ;
        mActiveDefaultNetwork = i;
        mDefaultInetConditionPublished = 0;
        mDefaultConnectionSequence = 1 + mDefaultConnectionSequence;
        mInetConditionChangeInFlight = false;
_L2:
        networkstatetracker.setTeardownRequested(false);
        updateNetworkSettings(networkstatetracker);
        handleConnectivityChange(i, false);
        sendConnectedBroadcastDelayed(networkinfo, getConnectivityChangeDelay());
        String s = networkstatetracker.getLinkProperties().getInterfaceName();
        if(s != null)
            try {
                BatteryStatsService.getService().noteNetworkInterfaceType(s, i);
            }
            catch(RemoteException remoteexception) { }
        if(true) goto _L8; else goto _L7
_L7:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void handleConnectionFailure(NetworkInfo networkinfo) {
        mNetTrackers[networkinfo.getType()].setTeardownRequested(false);
        String s = networkinfo.getReason();
        String s1 = networkinfo.getExtraInfo();
        String s2;
        Intent intent;
        if(s == null)
            s2 = ".";
        else
            s2 = (new StringBuilder()).append(" (").append(s).append(").").toString();
        loge((new StringBuilder()).append("Attempt to connect to ").append(networkinfo.getTypeName()).append(" failed").append(s2).toString());
        intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        intent.putExtra("networkInfo", networkinfo);
        if(getActiveNetworkInfo() == null)
            intent.putExtra("noConnectivity", true);
        if(s != null)
            intent.putExtra("reason", s);
        if(s1 != null)
            intent.putExtra("extraInfo", s1);
        if(networkinfo.isFailover()) {
            intent.putExtra("isFailover", true);
            networkinfo.setFailover(false);
        }
        if(mNetConfigs[networkinfo.getType()].isDefault()) {
            tryFailover(networkinfo.getType());
            Intent intent1;
            if(mActiveDefaultNetwork != -1) {
                intent.putExtra("otherNetwork", mNetTrackers[mActiveDefaultNetwork].getNetworkInfo());
            } else {
                mDefaultInetConditionPublished = 0;
                intent.putExtra("noConnectivity", true);
            }
        }
        intent.putExtra("inetCondition", mDefaultInetConditionPublished);
        intent1 = new Intent(intent);
        intent1.setAction("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        sendStickyBroadcast(intent1);
        sendStickyBroadcast(intent);
        if(mActiveDefaultNetwork != -1)
            sendConnectedBroadcast(mNetTrackers[mActiveDefaultNetwork].getNetworkInfo());
    }

    private void handleConnectivityChange(int i, boolean flag) {
        int j;
        LinkProperties linkproperties;
        LinkProperties linkproperties1;
        if(flag)
            j = 3;
        else
            j = 0;
        handleDnsConfigurationChange(i);
        linkproperties = mCurrentLinkProperties[i];
        linkproperties1 = null;
        if(mNetTrackers[i].getNetworkInfo().isConnected()) {
            linkproperties1 = mNetTrackers[i].getLinkProperties();
            if(linkproperties != null)
                if(linkproperties.isIdenticalInterfaceName(linkproperties1)) {
                    android.net.LinkProperties.CompareResult compareresult = linkproperties.compareAddresses(linkproperties1);
                    boolean flag1;
                    LinkProperties linkproperties2;
                    String s;
                    if(compareresult.removed.size() != 0 || compareresult.added.size() != 0) {
                        Iterator iterator = compareresult.removed.iterator();
                        do {
                            if(!iterator.hasNext())
                                break;
                            LinkAddress linkaddress = (LinkAddress)iterator.next();
                            if(linkaddress.getAddress() instanceof Inet4Address)
                                j |= 1;
                            if(linkaddress.getAddress() instanceof Inet6Address)
                                j |= 2;
                        } while(true);
                        log((new StringBuilder()).append("handleConnectivityChange: addresses changed linkProperty[").append(i).append("]:").append(" resetMask=").append(j).append("\n   car=").append(compareresult).toString());
                    } else {
                        log((new StringBuilder()).append("handleConnectivityChange: address are the same reset per doReset linkProperty[").append(i).append("]:").append(" resetMask=").append(j).toString());
                    }
                } else {
                    j = 3;
                    log((new StringBuilder()).append("handleConnectivityChange: interface not not equivalent reset both linkProperty[").append(i).append("]:").append(" resetMask=").append(j).toString());
                }
            if(mNetConfigs[i].isDefault())
                handleApplyDefaultProxy(linkproperties1.getHttpProxy());
        }
        mCurrentLinkProperties[i] = linkproperties1;
        flag1 = updateRoutes(linkproperties1, linkproperties, mNetConfigs[i].isDefault());
        if(j != 0 || flag1) {
            linkproperties2 = mNetTrackers[i].getLinkProperties();
            if(linkproperties2 != null) {
                s = linkproperties2.getInterfaceName();
                if(!TextUtils.isEmpty(s)) {
                    if(j != 0) {
                        log((new StringBuilder()).append("resetConnections(").append(s).append(", ").append(j).append(")").toString());
                        NetworkUtils.resetConnections(s, j);
                        if((j & 1) != 0)
                            mVpn.interfaceStatusChanged(s, false);
                    }
                    if(flag1)
                        try {
                            mNetd.flushInterfaceDnsCache(s);
                        }
                        catch(Exception exception) {
                            loge((new StringBuilder()).append("Exception resetting dns cache: ").append(exception).toString());
                        }
                }
            }
        }
        if(TextUtils.equals(mNetTrackers[i].getNetworkInfo().getReason(), "linkPropertiesChanged") && isTetheringSupported())
            mTethering.handleTetherIfaceChange();
    }

    private void handleDeprecatedGlobalHttpProxy() {
        String as[];
        int i;
        String s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "http_proxy");
        if(TextUtils.isEmpty(s))
            break MISSING_BLOCK_LABEL_74;
        as = s.split(":");
        as[0];
        i = 8080;
        if(as.length <= 1)
            break MISSING_BLOCK_LABEL_56;
        int j = Integer.parseInt(as[1]);
        i = j;
        setGlobalProxy(new ProxyProperties(as[0], i, ""));
_L2:
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void handleDisconnect(NetworkInfo networkinfo) {
        int i;
        Intent intent;
        boolean flag;
        String s;
        NetworkStateTracker networkstatetracker;
        i = networkinfo.getType();
        mNetTrackers[i].setTeardownRequested(false);
        if(!mNetConfigs[i].isDefault()) {
            List list = mNetRequestersPids[i];
            for(int l = 0; l < list.size(); l++)
                reassessPidDns(((Integer)list.get(l)).intValue(), false);

        }
        intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        intent.putExtra("networkInfo", networkinfo);
        if(networkinfo.isFailover()) {
            intent.putExtra("isFailover", true);
            networkinfo.setFailover(false);
        }
        if(networkinfo.getReason() != null)
            intent.putExtra("reason", networkinfo.getReason());
        if(networkinfo.getExtraInfo() != null)
            intent.putExtra("extraInfo", networkinfo.getExtraInfo());
        if(mNetConfigs[i].isDefault()) {
            tryFailover(i);
            LinkProperties linkproperties;
            NetworkStateTracker anetworkstatetracker[];
            int j;
            int k;
            if(mActiveDefaultNetwork != -1) {
                intent.putExtra("otherNetwork", mNetTrackers[mActiveDefaultNetwork].getNetworkInfo());
            } else {
                mDefaultInetConditionPublished = 0;
                intent.putExtra("noConnectivity", true);
            }
        }
        intent.putExtra("inetCondition", mDefaultInetConditionPublished);
        flag = true;
        linkproperties = mNetTrackers[i].getLinkProperties();
        if(linkproperties == null)
            break MISSING_BLOCK_LABEL_365;
        s = linkproperties.getInterfaceName();
        if(TextUtils.isEmpty(s))
            break MISSING_BLOCK_LABEL_365;
        anetworkstatetracker = mNetTrackers;
        j = anetworkstatetracker.length;
        k = 0;
        if(k >= j)
            break MISSING_BLOCK_LABEL_365;
        networkstatetracker = anetworkstatetracker[k];
        if(networkstatetracker != null)
            break; /* Loop/switch isn't completed */
_L4:
        k++;
        if(true) goto _L2; else goto _L1
_L2:
        NetworkInfo networkinfo1;
        break MISSING_BLOCK_LABEL_267;
_L1:
        LinkProperties linkproperties1;
        if(!(networkinfo1 = networkstatetracker.getNetworkInfo()).isConnected() || networkinfo1.getType() == i || ((linkproperties1 = networkstatetracker.getLinkProperties()) == null || !s.equals(linkproperties1.getInterfaceName()))) goto _L4; else goto _L3
_L3:
        flag = false;
        handleConnectivityChange(i, flag);
        Intent intent1 = new Intent(intent);
        intent1.setAction("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        sendStickyBroadcast(intent1);
        sendStickyBroadcastDelayed(intent, getConnectivityChangeDelay());
        if(mActiveDefaultNetwork != -1)
            sendConnectedBroadcastDelayed(mNetTrackers[mActiveDefaultNetwork].getNetworkInfo(), getConnectivityChangeDelay());
        return;
    }

    private void handleDnsConfigurationChange(int i) {
        NetworkStateTracker networkstatetracker = mNetTrackers[i];
        if(networkstatetracker == null || !networkstatetracker.getNetworkInfo().isConnected() || networkstatetracker.isTeardownRequested()) goto _L2; else goto _L1
_L1:
        LinkProperties linkproperties = networkstatetracker.getLinkProperties();
        if(linkproperties != null) goto _L3; else goto _L2
_L2:
        return;
_L3:
        Collection collection = linkproperties.getDnses();
        boolean flag = false;
        if(mNetConfigs[i].isDefault()) {
            String s = networkstatetracker.getNetworkInfo().getTypeName();
            synchronized(mDnsLock) {
                if(!mDnsOverridden)
                    flag = updateDns(s, linkproperties.getInterfaceName(), collection, "");
            }
        } else {
            List list;
            int j;
            try {
                mNetd.setDnsServersForInterface(linkproperties.getInterfaceName(), NetworkUtils.makeStrings(collection));
            }
            catch(Exception exception) {
                loge((new StringBuilder()).append("exception setting dns servers: ").append(exception).toString());
            }
            list = mNetRequestersPids[i];
            j = 0;
            while(j < list.size())  {
                flag = writePidDns(collection, ((Integer)list.get(j)).intValue());
                j++;
            }
        }
        if(flag)
            bumpDns();
        if(true) goto _L2; else goto _L4
_L4:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    private void handleInetConditionChange(int i, int j) {
        if(mActiveDefaultNetwork != -1) goto _L2; else goto _L1
_L1:
        log("handleInetConditionChange: no active default network - ignore");
_L4:
        return;
_L2:
        if(mActiveDefaultNetwork != i) {
            log((new StringBuilder()).append("handleInetConditionChange: net=").append(i).append(" != default=").append(mActiveDefaultNetwork).append(" - ignore").toString());
        } else {
            mDefaultInetCondition = j;
            if(!mInetConditionChangeInFlight) {
                int k;
                if(mDefaultInetCondition > 50)
                    k = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "inet_condition_debounce_up_delay", 500);
                else
                    k = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "inet_condition_debounce_down_delay", 3000);
                mInetConditionChangeInFlight = true;
                mHandler.sendMessageDelayed(mHandler.obtainMessage(105, mActiveDefaultNetwork, mDefaultConnectionSequence), k);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleInetConditionHoldEnd(int i, int j) {
        log((new StringBuilder()).append("handleInetConditionHoldEnd: net=").append(i).append(", condition=").append(mDefaultInetCondition).append(", published condition=").append(mDefaultInetConditionPublished).toString());
        mInetConditionChangeInFlight = false;
        if(mActiveDefaultNetwork == -1)
            log("handleInetConditionHoldEnd: no active default network - ignoring");
        else
        if(mDefaultConnectionSequence != j) {
            log("handleInetConditionHoldEnd: event hold for obsolete network - ignoring");
        } else {
            NetworkInfo networkinfo = mNetTrackers[mActiveDefaultNetwork].getNetworkInfo();
            if(!networkinfo.isConnected()) {
                log("handleInetConditionHoldEnd: default network not connected - ignoring");
            } else {
                mDefaultInetConditionPublished = mDefaultInetCondition;
                sendInetConditionBroadcast(networkinfo);
            }
        }
    }

    private void handleSetDependencyMet(int i, boolean flag) {
        if(mNetTrackers[i] != null) {
            log((new StringBuilder()).append("handleSetDependencyMet(").append(i).append(", ").append(flag).append(")").toString());
            mNetTrackers[i].setDependencyMet(flag);
        }
    }

    private void handleSetMobileData(boolean flag) {
        if(mNetTrackers[0] != null)
            mNetTrackers[0].setUserDataEnable(flag);
        if(mNetTrackers[6] != null)
            mNetTrackers[6].setUserDataEnable(flag);
    }

    private void handleSetNetworkPreference(int i) {
        if(!ConnectivityManager.isNetworkTypeValid(i) || mNetConfigs[i] == null || !mNetConfigs[i].isDefault() || mNetworkPreference == i)
            break MISSING_BLOCK_LABEL_64;
        android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "network_preference", i);
        this;
        JVM INSTR monitorenter ;
        mNetworkPreference = i;
        this;
        JVM INSTR monitorexit ;
        enforcePreference();
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void handleSetPolicyDataEnable(int i, boolean flag) {
        if(ConnectivityManager.isNetworkTypeValid(i)) {
            NetworkStateTracker networkstatetracker = mNetTrackers[i];
            if(networkstatetracker != null)
                networkstatetracker.setPolicyDataEnable(flag);
        }
    }

    private boolean isNetworkBlocked(NetworkStateTracker networkstatetracker, int i) {
        boolean flag;
        String s;
        flag = false;
        s = networkstatetracker.getLinkProperties().getInterfaceName();
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag1 = mMeteredIfaces.contains(s);
        int j = mUidRules.get(i, 0);
        if(flag1 && (j & 1) != 0)
            flag = true;
        return flag;
    }

    private boolean isNetworkMeteredUnchecked(int i) {
        NetworkState networkstate = getNetworkStateUnchecked(i);
        if(networkstate == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = mPolicyManager.isNetworkMetered(networkstate);
        boolean flag = flag1;
_L4:
        return flag;
        RemoteException remoteexception;
        remoteexception;
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void loadGlobalProxy() {
        ProxyProperties proxyproperties;
        ContentResolver contentresolver = mContext.getContentResolver();
        String s = android.provider.Settings.Secure.getString(contentresolver, "global_http_proxy_host");
        int i = android.provider.Settings.Secure.getInt(contentresolver, "global_http_proxy_port", 0);
        String s1 = android.provider.Settings.Secure.getString(contentresolver, "global_http_proxy_exclusion_list");
        if(TextUtils.isEmpty(s))
            break MISSING_BLOCK_LABEL_83;
        proxyproperties = new ProxyProperties(s, i, s1);
        Object obj = mGlobalProxyLock;
        obj;
        JVM INSTR monitorenter ;
        mGlobalProxy = proxyproperties;
    }

    private void log(String s) {
        Slog.d("ConnectivityService", s);
    }

    private void loge(String s) {
        Slog.e("ConnectivityService", s);
    }

    private Intent makeGeneralIntent(NetworkInfo networkinfo, String s) {
        Intent intent = new Intent(s);
        intent.putExtra("networkInfo", networkinfo);
        if(networkinfo.isFailover()) {
            intent.putExtra("isFailover", true);
            networkinfo.setFailover(false);
        }
        if(networkinfo.getReason() != null)
            intent.putExtra("reason", networkinfo.getReason());
        if(networkinfo.getExtraInfo() != null)
            intent.putExtra("extraInfo", networkinfo.getExtraInfo());
        intent.putExtra("inetCondition", mDefaultInetConditionPublished);
        return intent;
    }

    private NetworkStateTracker makeWimaxStateTracker() {
        if(!mContext.getResources().getBoolean(0x1110037)) goto _L2; else goto _L1
_L1:
        String s2;
        String s3;
        String s4;
        DexClassLoader dexclassloader;
        String s = mContext.getResources().getString(0x1040027);
        String s1 = mContext.getResources().getString(0x1040028);
        s2 = mContext.getResources().getString(0x1040029);
        s3 = mContext.getResources().getString(0x104002a);
        s4 = mContext.getResources().getString(0x104002b);
        log((new StringBuilder()).append("wimaxJarLocation: ").append(s).toString());
        dexclassloader = new DexClassLoader(s, (new ContextWrapper(mContext)).getCacheDir().getAbsolutePath(), s1, ClassLoader.getSystemClassLoader());
        NetworkStateTracker networkstatetracker;
        Class class1;
        Class class2;
        NetworkStateTracker networkstatetracker1;
        try {
            dexclassloader.loadClass(s2);
            class1 = dexclassloader.loadClass(s4);
            class2 = dexclassloader.loadClass(s3);
        }
        catch(ClassNotFoundException classnotfoundexception) {
            try {
                loge((new StringBuilder()).append("Exception finding Wimax classes: ").append(classnotfoundexception.toString()).toString());
            }
            catch(android.content.res.Resources.NotFoundException notfoundexception) {
                loge("Wimax Resources does not exist!!! ");
                networkstatetracker = null;
                continue; /* Loop/switch isn't completed */
            }
            networkstatetracker = null;
            continue; /* Loop/switch isn't completed */
        }
        try {
            log("Starting Wimax Service... ");
            Class aclass[] = new Class[2];
            aclass[0] = android/content/Context;
            aclass[1] = android/os/Handler;
            Constructor constructor = class1.getConstructor(aclass);
            Object aobj[] = new Object[2];
            aobj[0] = mContext;
            aobj[1] = mHandler;
            networkstatetracker1 = (NetworkStateTracker)constructor.newInstance(aobj);
            Class aclass1[] = new Class[2];
            aclass1[0] = android/content/Context;
            aclass1[1] = class1;
            Constructor constructor1 = class2.getDeclaredConstructor(aclass1);
            constructor1.setAccessible(true);
            Object aobj1[] = new Object[2];
            aobj1[0] = mContext;
            aobj1[1] = networkstatetracker1;
            IBinder ibinder = (IBinder)constructor1.newInstance(aobj1);
            constructor1.setAccessible(false);
            ServiceManager.addService("WiMax", ibinder);
        }
        catch(Exception exception) {
            loge((new StringBuilder()).append("Exception creating Wimax classes: ").append(exception.toString()).toString());
            networkstatetracker = null;
            continue; /* Loop/switch isn't completed */
        }
        networkstatetracker = networkstatetracker1;
_L4:
        return networkstatetracker;
_L2:
        loge("Wimax is not enabled or not added to the network attributes!!! ");
        networkstatetracker = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean modifyRoute(String s, LinkProperties linkproperties, RouteInfo routeinfo, int i, boolean flag, boolean flag1) {
        if(s != null && linkproperties != null && routeinfo != null) goto _L2; else goto _L1
_L1:
        boolean flag2;
        log((new StringBuilder()).append("modifyRoute got unexpected null: ").append(s).append(", ").append(linkproperties).append(", ").append(routeinfo).toString());
        flag2 = false;
_L9:
        return flag2;
_L2:
        if(i > 10) {
            loge("Error modifying route - too much recursion");
            flag2 = false;
            continue; /* Loop/switch isn't completed */
        }
        if(!routeinfo.isHostRoute()) {
            RouteInfo routeinfo1 = RouteInfo.selectBestRoute(linkproperties.getRoutes(), routeinfo.getGateway());
            if(routeinfo1 != null) {
                RouteInfo routeinfo2;
                if(routeinfo1.getGateway().equals(routeinfo.getGateway()))
                    routeinfo2 = RouteInfo.makeHostRoute(routeinfo.getGateway());
                else
                    routeinfo2 = RouteInfo.makeHostRoute(routeinfo.getGateway(), routeinfo1.getGateway());
                modifyRoute(s, linkproperties, routeinfo2, i + 1, flag, flag1);
            }
        }
        if(!flag) goto _L4; else goto _L3
_L3:
        if(!flag1) goto _L6; else goto _L5
_L5:
        try {
            mAddedRoutes.add(routeinfo);
            mNetd.addRoute(s, routeinfo);
        }
        catch(Exception exception2) {
            loge((new StringBuilder()).append("Exception trying to add a route: ").append(exception2).toString());
            flag2 = false;
            continue; /* Loop/switch isn't completed */
        }
_L7:
        flag2 = true;
        continue; /* Loop/switch isn't completed */
_L6:
        mNetd.addSecondaryRoute(s, routeinfo);
        break MISSING_BLOCK_LABEL_180;
_L4:
label0:
        {
            if(!flag1)
                break label0;
            mAddedRoutes.remove(routeinfo);
            if(!mAddedRoutes.contains(routeinfo))
                try {
                    mNetd.removeRoute(s, routeinfo);
                }
                catch(Exception exception1) {
                    flag2 = false;
                    continue; /* Loop/switch isn't completed */
                }
        }
          goto _L7
        mNetd.removeSecondaryRoute(s, routeinfo);
          goto _L7
        Exception exception;
        exception;
        flag2 = false;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private boolean modifyRouteToAddress(LinkProperties linkproperties, InetAddress inetaddress, boolean flag, boolean flag1) {
        RouteInfo routeinfo = RouteInfo.selectBestRoute(linkproperties.getRoutes(), inetaddress);
        RouteInfo routeinfo1;
        if(routeinfo == null)
            routeinfo1 = RouteInfo.makeHostRoute(inetaddress);
        else
        if(routeinfo.getGateway().equals(inetaddress))
            routeinfo1 = RouteInfo.makeHostRoute(inetaddress);
        else
            routeinfo1 = RouteInfo.makeHostRoute(inetaddress, routeinfo.getGateway());
        return modifyRoute(linkproperties.getInterfaceName(), linkproperties, routeinfo1, 0, flag, flag1);
    }

    private void reassessPidDns(int i, boolean flag) {
        int ai[];
        int j;
        int k;
        ai = mPriorityList;
        j = ai.length;
        k = 0;
_L5:
        if(k >= j) goto _L2; else goto _L1
_L1:
        int i1 = ai[k];
        if(!mNetConfigs[i1].isDefault()) goto _L4; else goto _L3
_L3:
        NetworkStateTracker networkstatetracker;
        k++;
          goto _L5
_L4:
        LinkProperties linkproperties;
        if(!(networkstatetracker = mNetTrackers[i1]).getNetworkInfo().isConnected() || networkstatetracker.isTeardownRequested() || (linkproperties = networkstatetracker.getLinkProperties()) == null) goto _L3; else goto _L6
_L6:
        List list;
        int j1;
        list = mNetRequestersPids[i1];
        j1 = 0;
_L10:
        if(j1 >= list.size()) goto _L3; else goto _L7
_L7:
        if(((Integer)list.get(j1)).intValue() != i) goto _L9; else goto _L8
_L8:
        writePidDns(linkproperties.getDnses(), i);
        if(flag)
            bumpDns();
_L11:
        return;
_L9:
        j1++;
          goto _L10
_L2:
        int l = 1;
_L12:
        String s;
label0:
        {
            s = (new StringBuilder()).append("net.dns").append(l).append(".").append(i).toString();
            if(SystemProperties.get(s).length() != 0)
                break label0;
            if(flag)
                bumpDns();
        }
          goto _L11
        SystemProperties.set(s, "");
        l++;
          goto _L12
    }

    private boolean removeRoute(LinkProperties linkproperties, RouteInfo routeinfo, boolean flag) {
        return modifyRoute(linkproperties.getInterfaceName(), linkproperties, routeinfo, 0, false, flag);
    }

    private boolean removeRouteToAddress(LinkProperties linkproperties, InetAddress inetaddress) {
        return modifyRouteToAddress(linkproperties, inetaddress, false, true);
    }

    private void sendConnectedBroadcast(NetworkInfo networkinfo) {
        sendGeneralBroadcast(networkinfo, "android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        sendGeneralBroadcast(networkinfo, "android.net.conn.CONNECTIVITY_CHANGE");
    }

    private void sendConnectedBroadcastDelayed(NetworkInfo networkinfo, int i) {
        sendGeneralBroadcast(networkinfo, "android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        sendGeneralBroadcastDelayed(networkinfo, "android.net.conn.CONNECTIVITY_CHANGE", i);
    }

    private void sendGeneralBroadcast(NetworkInfo networkinfo, String s) {
        sendStickyBroadcast(makeGeneralIntent(networkinfo, s));
    }

    private void sendGeneralBroadcastDelayed(NetworkInfo networkinfo, String s, int i) {
        sendStickyBroadcastDelayed(makeGeneralIntent(networkinfo, s), i);
    }

    private void sendInetConditionBroadcast(NetworkInfo networkinfo) {
        sendGeneralBroadcast(networkinfo, "android.net.conn.INET_CONDITION_ACTION");
    }

    private void sendProxyBroadcast(ProxyProperties proxyproperties) {
        if(proxyproperties == null)
            proxyproperties = new ProxyProperties("", 0, "");
        log((new StringBuilder()).append("sending Proxy Broadcast for ").append(proxyproperties).toString());
        Intent intent = new Intent("android.intent.action.PROXY_CHANGE");
        intent.addFlags(0x28000000);
        intent.putExtra("proxy", proxyproperties);
        mContext.sendStickyBroadcast(intent);
    }

    private void sendStickyBroadcast(Intent intent) {
        this;
        JVM INSTR monitorenter ;
        if(!mSystemReady)
            mInitialBroadcast = new Intent(intent);
        intent.addFlags(0x8000000);
        mContext.sendStickyBroadcast(intent);
        return;
    }

    private void sendStickyBroadcastDelayed(Intent intent, int i) {
        if(i <= 0)
            sendStickyBroadcast(intent);
        else
            mHandler.sendMessageDelayed(mHandler.obtainMessage(112, intent), i);
    }

    private void setBufferSize(String s) {
        try {
            String as[] = s.split(",");
            if(as.length == 6) {
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_rmem_min", as[0]);
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_rmem_def", as[1]);
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_rmem_max", as[2]);
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_wmem_min", as[3]);
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_wmem_def", as[4]);
                FileUtils.stringToFile("/sys/kernel/ipv4/tcp_wmem_max", as[5]);
            } else {
                loge((new StringBuilder()).append("Invalid buffersize string: ").append(s).toString());
            }
        }
        catch(IOException ioexception) {
            loge((new StringBuilder()).append("Can't set tcp buffer sizes:").append(ioexception).toString());
        }
    }

    private int stopUsingNetworkFeature(FeatureUser featureuser, boolean flag) {
        int i;
        String s;
        int j;
        boolean flag1;
        i = featureuser.mNetworkType;
        s = featureuser.mFeature;
        j = featureuser.mPid;
        featureuser.mUid;
        flag1 = false;
        if(ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        byte byte0;
        log((new StringBuilder()).append("stopUsingNetworkFeature: net ").append(i).append(": ").append(s).append(", net is invalid").toString());
        byte0 = -1;
_L3:
        return byte0;
_L2:
        this;
        JVM INSTR monitorenter ;
        if(mFeatureUsers.contains(featureuser))
            break MISSING_BLOCK_LABEL_110;
        byte0 = 1;
          goto _L3
        Exception exception;
        exception;
        throw exception;
        featureuser.unlinkDeathRecipient();
        mFeatureUsers.remove(mFeatureUsers.indexOf(featureuser));
        if(flag) goto _L5; else goto _L4
_L4:
        Iterator iterator = mFeatureUsers.iterator();
_L8:
        if(!iterator.hasNext()) goto _L5; else goto _L6
_L6:
        if(!((FeatureUser)iterator.next()).isSameUser(featureuser)) goto _L8; else goto _L7
_L7:
        byte0 = 1;
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L5:
        int k;
        NetworkStateTracker networkstatetracker;
        k = convertFeatureToNetworkType(i, s);
        Injector.stopUsingNetworkFeature(featureuser, k);
        networkstatetracker = mNetTrackers[k];
        if(networkstatetracker != null) goto _L10; else goto _L9
_L9:
        log((new StringBuilder()).append("stopUsingNetworkFeature: net ").append(i).append(": ").append(s).append(" no known tracker for used net type ").append(k).toString());
        byte0 = -1;
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L10:
        if(k == i) goto _L12; else goto _L11
_L11:
        Integer integer = new Integer(j);
        mNetRequestersPids[k].remove(integer);
        reassessPidDns(j, true);
        if(mNetRequestersPids[k].size() == 0)
            break MISSING_BLOCK_LABEL_437;
        byte0 = 1;
        this;
        JVM INSTR monitorexit ;
          goto _L3
_L13:
        this;
        JVM INSTR monitorexit ;
        if(flag1) {
            log((new StringBuilder()).append("stopUsingNetworkFeature: teardown net ").append(i).append(": ").append(s).toString());
            networkstatetracker.teardown();
            byte0 = 1;
        } else {
            byte0 = -1;
        }
          goto _L3
_L12:
        log((new StringBuilder()).append("stopUsingNetworkFeature: net ").append(i).append(": ").append(s).append(" not a known feature - dropping").toString());
          goto _L13
        flag1 = true;
          goto _L13
    }

    private boolean teardown(NetworkStateTracker networkstatetracker) {
        boolean flag = true;
        if(networkstatetracker.teardown())
            networkstatetracker.setTeardownRequested(flag);
        else
            flag = false;
        return flag;
    }

    private void tryFailover(int i) {
        if(mNetConfigs[i].isDefault()) {
            if(mActiveDefaultNetwork == i)
                mActiveDefaultNetwork = -1;
            int j = 0;
            do {
                if(j > 13)
                    break;
                if(j != i && mNetConfigs[j] != null && mNetConfigs[j].isDefault() && mNetTrackers[j] != null) {
                    NetworkStateTracker networkstatetracker = mNetTrackers[j];
                    NetworkInfo networkinfo = networkstatetracker.getNetworkInfo();
                    if(!networkinfo.isConnectedOrConnecting() || networkstatetracker.isTeardownRequested()) {
                        networkinfo.setFailover(true);
                        networkstatetracker.reconnect();
                    }
                    log((new StringBuilder()).append("Attempting to switch to ").append(networkinfo.getTypeName()).toString());
                }
                j++;
            } while(true);
        }
    }

    private boolean updateDns(String s, String s1, Collection collection, String s2) {
        boolean flag = false;
        int i = 0;
        int j;
        if(collection.size() == 0 && mDefaultDns != null) {
            i = 0 + 1;
            String s6 = mDefaultDns.getHostAddress();
            if(!s6.equals(SystemProperties.get("net.dns1"))) {
                loge((new StringBuilder()).append("no dns provided for ").append(s).append(" - using ").append(s6).toString());
                flag = true;
                SystemProperties.set("net.dns1", s6);
            }
        } else {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())  {
                InetAddress inetaddress = (InetAddress)iterator.next();
                i++;
                String s3 = (new StringBuilder()).append("net.dns").append(i).toString();
                String s4 = inetaddress.getHostAddress();
                if(flag || !s4.equals(SystemProperties.get(s3))) {
                    flag = true;
                    SystemProperties.set(s3, s4);
                }
            }
        }
        for(j = i + 1; j <= mNumDnsEntries; j++) {
            String s5 = (new StringBuilder()).append("net.dns").append(j).toString();
            flag = true;
            SystemProperties.set(s5, "");
        }

        mNumDnsEntries = i;
        if(flag)
            try {
                mNetd.setDnsServersForInterface(s1, NetworkUtils.makeStrings(collection));
                mNetd.setDefaultInterfaceForDns(s1);
            }
            catch(Exception exception) {
                loge((new StringBuilder()).append("exception setting default dns interface: ").append(exception).toString());
            }
        if(!s2.equals(SystemProperties.get("net.dns.search"))) {
            SystemProperties.set("net.dns.search", s2);
            flag = true;
        }
        return flag;
    }

    private boolean updateRoutes(LinkProperties linkproperties, LinkProperties linkproperties1, boolean flag) {
        android.net.LinkProperties.CompareResult compareresult = new android.net.LinkProperties.CompareResult();
        android.net.LinkProperties.CompareResult compareresult1 = new android.net.LinkProperties.CompareResult();
        boolean flag1;
        Iterator iterator;
        if(linkproperties1 != null) {
            compareresult1 = linkproperties1.compareRoutes(linkproperties);
            compareresult = linkproperties1.compareDnses(linkproperties);
        } else
        if(linkproperties != null) {
            compareresult1.added = linkproperties.getRoutes();
            compareresult.added = linkproperties.getDnses();
        }
        if(compareresult1.removed.size() != 0 || compareresult1.added.size() != 0)
            flag1 = true;
        else
            flag1 = false;
        for(iterator = compareresult1.removed.iterator(); iterator.hasNext();) {
            RouteInfo routeinfo1 = (RouteInfo)iterator.next();
            if(flag || !routeinfo1.isDefaultRoute())
                removeRoute(linkproperties1, routeinfo1, true);
            if(!flag)
                removeRoute(linkproperties1, routeinfo1, false);
        }

        Iterator iterator1 = compareresult1.added.iterator();
        do {
            if(!iterator1.hasNext())
                break;
            RouteInfo routeinfo = (RouteInfo)iterator1.next();
            if(flag || !routeinfo.isDefaultRoute()) {
                addRoute(linkproperties, routeinfo, true);
            } else {
                addRoute(linkproperties, routeinfo, false);
                String s = linkproperties.getInterfaceName();
                if(!TextUtils.isEmpty(s) && !mAddedRoutes.contains(routeinfo))
                    try {
                        mNetd.removeRoute(s, routeinfo);
                    }
                    catch(Exception exception) {
                        loge((new StringBuilder()).append("Exception trying to remove a route: ").append(exception).toString());
                    }
            }
        } while(true);
        if(!flag)
            if(flag1) {
                if(linkproperties1 != null) {
                    for(Iterator iterator5 = linkproperties1.getDnses().iterator(); iterator5.hasNext(); removeRouteToAddress(linkproperties1, (InetAddress)iterator5.next()));
                }
                if(linkproperties != null) {
                    for(Iterator iterator4 = linkproperties.getDnses().iterator(); iterator4.hasNext(); addRouteToAddress(linkproperties, (InetAddress)iterator4.next()));
                }
            } else {
                for(Iterator iterator2 = compareresult.removed.iterator(); iterator2.hasNext(); removeRouteToAddress(linkproperties1, (InetAddress)iterator2.next()));
                for(Iterator iterator3 = compareresult.added.iterator(); iterator3.hasNext(); addRouteToAddress(linkproperties, (InetAddress)iterator3.next()));
            }
        return flag1;
    }

    private boolean writePidDns(Collection collection, int i) {
        int j = 1;
        boolean flag = false;
        for(Iterator iterator = collection.iterator(); iterator.hasNext();) {
            InetAddress inetaddress = (InetAddress)iterator.next();
            String s = inetaddress.getHostAddress();
            if(flag || !s.equals(SystemProperties.get((new StringBuilder()).append("net.dns").append(j).append(".").append(i).toString()))) {
                flag = true;
                SystemProperties.set((new StringBuilder()).append("net.dns").append(j).append(".").append(i).toString(), inetaddress.getHostAddress());
            }
            j++;
        }

        return flag;
    }

    int convertFeatureToNetworkType(int i, String s) {
        int j = i;
        if(i == 0) {
            if(TextUtils.equals(s, "enableMMS"))
                j = 2;
            else
            if(TextUtils.equals(s, "enableSUPL"))
                j = 3;
            else
            if(TextUtils.equals(s, "enableDUN") || TextUtils.equals(s, "enableDUNAlways"))
                j = 4;
            else
            if(TextUtils.equals(s, "enableHIPRI"))
                j = 5;
            else
            if(TextUtils.equals(s, "enableFOTA"))
                j = 10;
            else
            if(TextUtils.equals(s, "enableIMS"))
                j = 11;
            else
            if(TextUtils.equals(s, "enableCBS"))
                j = 12;
            else
                Slog.e("ConnectivityService", "Can't match any mobile netTracker!");
        } else
        if(i == 1) {
            if(TextUtils.equals(s, "p2p"))
                j = 13;
            else
                Slog.e("ConnectivityService", "Can't match any wifi netTracker!");
        } else {
            Slog.e("ConnectivityService", "Unexpected network type");
        }
        return j;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ConnectivityService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L5:
        return;
_L2:
        printwriter.println();
        NetworkStateTracker anetworkstatetracker[] = mNetTrackers;
        int i = anetworkstatetracker.length;
        for(int j = 0; j < i; j++) {
            NetworkStateTracker networkstatetracker = anetworkstatetracker[j];
            if(networkstatetracker == null)
                continue;
            if(networkstatetracker.getNetworkInfo().isConnected())
                printwriter.println((new StringBuilder()).append("Active network: ").append(networkstatetracker.getNetworkInfo().getTypeName()).toString());
            printwriter.println(networkstatetracker.getNetworkInfo());
            printwriter.println(networkstatetracker);
            printwriter.println();
        }

        printwriter.println("Network Requester Pids:");
        int ai[] = mPriorityList;
        int k = ai.length;
        for(int l = 0; l < k; l++) {
            int j1 = ai[l];
            String s1 = (new StringBuilder()).append(j1).append(": ").toString();
            for(Iterator iterator1 = mNetRequestersPids[j1].iterator(); iterator1.hasNext();) {
                Object obj = iterator1.next();
                s1 = (new StringBuilder()).append(s1).append(obj.toString()).append(", ").toString();
            }

            printwriter.println(s1);
        }

        printwriter.println();
        printwriter.println("FeatureUsers:");
        for(Iterator iterator = mFeatureUsers.iterator(); iterator.hasNext(); printwriter.println(((FeatureUser)iterator.next()).toString()));
        printwriter.println();
        this;
        JVM INSTR monitorenter ;
        StringBuilder stringbuilder;
        String s;
        stringbuilder = (new StringBuilder()).append("NetworkTranstionWakeLock is currently ");
        if(!mNetTransitionWakeLock.isHeld())
            break MISSING_BLOCK_LABEL_501;
        s = "";
_L3:
        printwriter.println(stringbuilder.append(s).append("held.").toString());
        printwriter.println((new StringBuilder()).append("It was last requested for ").append(mNetTransitionWakeLockCausedBy).toString());
        this;
        JVM INSTR monitorexit ;
        printwriter.println();
        mTethering.dump(filedescriptor, printwriter, as);
        if(mInetLog != null) {
            printwriter.println();
            printwriter.println("Inet condition reports:");
            int i1 = 0;
            while(i1 < mInetLog.size())  {
                printwriter.println(mInetLog.get(i1));
                i1++;
            }
        }
        continue; /* Loop/switch isn't completed */
        s = "not ";
          goto _L3
        Exception exception;
        exception;
        throw exception;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public ParcelFileDescriptor establishVpn(VpnConfig vpnconfig) {
        return mVpn.establish(vpnconfig);
    }

    public LinkProperties getActiveLinkProperties() {
        return getLinkProperties(mActiveDefaultNetwork);
    }

    public NetworkInfo getActiveNetworkInfo() {
        enforceAccessPermission();
        int i = Binder.getCallingUid();
        return getNetworkInfo(mActiveDefaultNetwork, i);
    }

    public NetworkInfo getActiveNetworkInfoForUid(int i) {
        enforceConnectivityInternalPermission();
        return getNetworkInfo(mActiveDefaultNetwork, i);
    }

    public NetworkQuotaInfo getActiveNetworkQuotaInfo() {
        long l;
        enforceAccessPermission();
        l = Binder.clearCallingIdentity();
        NetworkState networkstate = getNetworkStateUnchecked(mActiveDefaultNetwork);
        if(networkstate == null) goto _L2; else goto _L1
_L1:
        NetworkQuotaInfo networkquotainfo1 = mPolicyManager.getNetworkQuotaInfo(networkstate);
        NetworkQuotaInfo networkquotainfo = networkquotainfo1;
_L4:
        Binder.restoreCallingIdentity(l);
        return networkquotainfo;
        RemoteException remoteexception;
        remoteexception;
_L2:
        networkquotainfo = null;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public NetworkInfo[] getAllNetworkInfo() {
        int i;
        ArrayList arraylist;
        enforceAccessPermission();
        i = Binder.getCallingUid();
        arraylist = Lists.newArrayList();
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        NetworkStateTracker anetworkstatetracker[];
        int j;
        anetworkstatetracker = mNetTrackers;
        j = anetworkstatetracker.length;
        Exception exception;
        for(int k = 0; k < j; k++) {
            NetworkStateTracker networkstatetracker = anetworkstatetracker[k];
            if(networkstatetracker != null)
                arraylist.add(getFilteredNetworkInfo(networkstatetracker, i));
            break MISSING_BLOCK_LABEL_91;
        }

        return (NetworkInfo[])arraylist.toArray(new NetworkInfo[arraylist.size()]);
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public NetworkState[] getAllNetworkState() {
        int i;
        ArrayList arraylist;
        enforceAccessPermission();
        i = Binder.getCallingUid();
        arraylist = Lists.newArrayList();
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        NetworkStateTracker anetworkstatetracker[];
        int j;
        anetworkstatetracker = mNetTrackers;
        j = anetworkstatetracker.length;
        Exception exception;
        for(int k = 0; k < j; k++) {
            NetworkStateTracker networkstatetracker = anetworkstatetracker[k];
            if(networkstatetracker != null)
                arraylist.add(new NetworkState(getFilteredNetworkInfo(networkstatetracker, i), networkstatetracker.getLinkProperties(), networkstatetracker.getLinkCapabilities()));
            break MISSING_BLOCK_LABEL_112;
        }

        return (NetworkState[])arraylist.toArray(new NetworkState[arraylist.size()]);
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public ProxyProperties getGlobalProxy() {
        Object obj = mGlobalProxyLock;
        obj;
        JVM INSTR monitorenter ;
        ProxyProperties proxyproperties = mGlobalProxy;
        return proxyproperties;
    }

    public int getLastTetherError(String s) {
        enforceTetherAccessPermission();
        int i;
        if(isTetheringSupported())
            i = mTethering.getLastTetherError(s);
        else
            i = 3;
        return i;
    }

    public LegacyVpnInfo getLegacyVpnInfo() {
        return mVpn.getLegacyVpnInfo();
    }

    public LinkProperties getLinkProperties(int i) {
        enforceAccessPermission();
        if(!ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        NetworkStateTracker networkstatetracker = mNetTrackers[i];
        if(networkstatetracker == null) goto _L2; else goto _L3
_L3:
        LinkProperties linkproperties = networkstatetracker.getLinkProperties();
_L5:
        return linkproperties;
_L2:
        linkproperties = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean getMobileDataEnabled() {
        boolean flag = true;
        enforceAccessPermission();
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "mobile_data", flag) != flag)
            flag = false;
        return flag;
    }

    public NetworkInfo getNetworkInfo(int i) {
        enforceAccessPermission();
        return getNetworkInfo(i, Binder.getCallingUid());
    }

    public int getNetworkPreference() {
        enforceAccessPermission();
        this;
        JVM INSTR monitorenter ;
        int i = mNetworkPreference;
        return i;
    }

    public ProxyProperties getProxy() {
        Object obj = mDefaultProxyLock;
        obj;
        JVM INSTR monitorenter ;
        ProxyProperties proxyproperties;
        if(mDefaultProxyDisabled)
            proxyproperties = null;
        else
            proxyproperties = mDefaultProxy;
        return proxyproperties;
    }

    public String[] getTetherableBluetoothRegexs() {
        enforceTetherAccessPermission();
        String as[];
        if(isTetheringSupported())
            as = mTethering.getTetherableBluetoothRegexs();
        else
            as = new String[0];
        return as;
    }

    public String[] getTetherableIfaces() {
        enforceTetherAccessPermission();
        return mTethering.getTetherableIfaces();
    }

    public String[] getTetherableUsbRegexs() {
        enforceTetherAccessPermission();
        String as[];
        if(isTetheringSupported())
            as = mTethering.getTetherableUsbRegexs();
        else
            as = new String[0];
        return as;
    }

    public String[] getTetherableWifiRegexs() {
        enforceTetherAccessPermission();
        String as[];
        if(isTetheringSupported())
            as = mTethering.getTetherableWifiRegexs();
        else
            as = new String[0];
        return as;
    }

    public String[] getTetheredIfacePairs() {
        enforceTetherAccessPermission();
        return mTethering.getTetheredIfacePairs();
    }

    public String[] getTetheredIfaces() {
        enforceTetherAccessPermission();
        return mTethering.getTetheredIfaces();
    }

    public String[] getTetheringErroredIfaces() {
        enforceTetherAccessPermission();
        return mTethering.getErroredIfaces();
    }

    public boolean isActiveNetworkMetered() {
        long l;
        enforceAccessPermission();
        l = Binder.clearCallingIdentity();
        boolean flag = isNetworkMeteredUnchecked(mActiveDefaultNetwork);
        Binder.restoreCallingIdentity(l);
        return flag;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public boolean isNetworkSupported(int i) {
        enforceAccessPermission();
        boolean flag;
        if(ConnectivityManager.isNetworkTypeValid(i) && mNetTrackers[i] != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isTetheringSupported() {
        boolean flag = true;
        enforceTetherAccessPermission();
        int i;
        boolean flag1;
        if(SystemProperties.get("ro.tether.denied").equals("true"))
            i = 0;
        else
            i = ((flag) ? 1 : 0);
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "tether_supported", i) != 0)
            flag1 = flag;
        else
            flag1 = false;
        if(!flag1 || !mTetheringConfigValid)
            flag = false;
        return flag;
    }

    public boolean prepareVpn(String s, String s1) {
        return mVpn.prepare(s, s1);
    }

    public boolean protectVpn(ParcelFileDescriptor parcelfiledescriptor) {
        int i = mActiveDefaultNetwork;
        if(!ConnectivityManager.isNetworkTypeValid(i))
            break MISSING_BLOCK_LABEL_64;
        mVpn.protect(parcelfiledescriptor, mNetTrackers[i].getLinkProperties().getInterfaceName());
        boolean flag = true;
        Exception exception;
        Exception exception1;
        Exception exception2;
        Exception exception3;
        try {
            parcelfiledescriptor.close();
        }
        catch(Exception exception4) { }
        return flag;
        exception2;
        try {
            parcelfiledescriptor.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        throw exception2;
        exception;
        parcelfiledescriptor.close();
        break MISSING_BLOCK_LABEL_68;
        parcelfiledescriptor.close();
_L2:
        flag = false;
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_44;
        exception1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void reportInetCondition(int i, int j) {
        mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR", "ConnectivityService");
        int k = getCallingPid();
        int l = getCallingUid();
        StringBuilder stringbuilder = (new StringBuilder()).append(k).append("(").append(l).append(") reports inet is ");
        String s;
        String s1;
        if(j > 50)
            s = "connected";
        else
            s = "disconnected";
        s1 = stringbuilder.append(s).append(" (").append(j).append(") on ").append("network Type ").append(i).append(" at ").append(GregorianCalendar.getInstance().getTime()).toString();
        mInetLog.add(s1);
        for(; mInetLog.size() > 15; mInetLog.remove(0));
        mHandler.sendMessage(mHandler.obtainMessage(104, i, j));
    }

    public void requestNetworkTransitionWakelock(String s) {
        enforceConnectivityInternalPermission();
        this;
        JVM INSTR monitorenter ;
        if(!mNetTransitionWakeLock.isHeld())
            break MISSING_BLOCK_LABEL_21;
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_80;
        mNetTransitionWakeLockSerialNumber = 1 + mNetTransitionWakeLockSerialNumber;
        mNetTransitionWakeLock.acquire();
        mNetTransitionWakeLockCausedBy = s;
        this;
        JVM INSTR monitorexit ;
        mHandler.sendMessageDelayed(mHandler.obtainMessage(108, mNetTransitionWakeLockSerialNumber, 0), mNetTransitionWakeLockTimeout);
        break MISSING_BLOCK_LABEL_80;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean requestRouteToHost(int i, int j) {
        InetAddress inetaddress = NetworkUtils.intToInetAddress(j);
        boolean flag;
        if(inetaddress == null)
            flag = false;
        else
            flag = requestRouteToHostAddress(i, inetaddress.getAddress());
        return flag;
    }

    public boolean requestRouteToHostAddress(int i, byte abyte0[]) {
        boolean flag;
        flag = false;
        enforceChangePermission();
        if(mProtectedNetworks.contains(Integer.valueOf(i)))
            enforceConnectivityInternalPermission();
        if(ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        log((new StringBuilder()).append("requestRouteToHostAddress on invalid network: ").append(i).toString());
_L4:
        return flag;
_L2:
        NetworkStateTracker networkstatetracker;
        long l;
        networkstatetracker = mNetTrackers[i];
        if(networkstatetracker == null || !networkstatetracker.getNetworkInfo().isConnected() || networkstatetracker.isTeardownRequested())
            continue; /* Loop/switch isn't completed */
        l = Binder.clearCallingIdentity();
        boolean flag1;
        InetAddress inetaddress = InetAddress.getByAddress(abyte0);
        flag1 = addRouteToAddress(networkstatetracker.getLinkProperties(), inetaddress);
        flag = flag1;
_L5:
        Binder.restoreCallingIdentity(l);
        if(true) goto _L4; else goto _L3
_L3:
        UnknownHostException unknownhostexception;
        unknownhostexception;
        log((new StringBuilder()).append("requestRouteToHostAddress got ").append(unknownhostexception.toString()).toString());
          goto _L5
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void setDataDependency(int i, boolean flag) {
        enforceConnectivityInternalPermission();
        Handler handler = mHandler;
        Handler handler1 = mHandler;
        int j;
        if(flag)
            j = 1;
        else
            j = 0;
        handler.sendMessage(handler1.obtainMessage(110, j, i));
    }

    public void setGlobalProxy(ProxyProperties proxyproperties) {
        enforceChangePermission();
        Object obj = mGlobalProxyLock;
        obj;
        JVM INSTR monitorenter ;
        if(proxyproperties == mGlobalProxy || proxyproperties != null && proxyproperties.equals(mGlobalProxy))
            break MISSING_BLOCK_LABEL_203;
        break MISSING_BLOCK_LABEL_49;
        Exception exception;
        exception;
        throw exception;
        if(mGlobalProxy == null || !mGlobalProxy.equals(proxyproperties))
            break MISSING_BLOCK_LABEL_72;
        obj;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_203;
        String s;
        int i;
        String s1;
        s = "";
        i = 0;
        s1 = "";
        if(proxyproperties == null || TextUtils.isEmpty(proxyproperties.getHost()))
            break MISSING_BLOCK_LABEL_195;
        mGlobalProxy = new ProxyProperties(proxyproperties);
        s = mGlobalProxy.getHost();
        i = mGlobalProxy.getPort();
        s1 = mGlobalProxy.getExclusionList();
_L1:
        ContentResolver contentresolver = mContext.getContentResolver();
        android.provider.Settings.Secure.putString(contentresolver, "global_http_proxy_host", s);
        android.provider.Settings.Secure.putInt(contentresolver, "global_http_proxy_port", i);
        android.provider.Settings.Secure.putString(contentresolver, "global_http_proxy_exclusion_list", s1);
        obj;
        JVM INSTR monitorexit ;
        if(mGlobalProxy == null) {
            mDefaultProxy;
        }
        break MISSING_BLOCK_LABEL_203;
        mGlobalProxy = null;
          goto _L1
    }

    public void setMobileDataEnabled(boolean flag) {
        enforceChangePermission();
        log((new StringBuilder()).append("setMobileDataEnabled(").append(flag).append(")").toString());
        Handler handler = mHandler;
        Handler handler1 = mHandler;
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        handler.sendMessage(handler1.obtainMessage(107, i, 0));
    }

    public void setNetworkPreference(int i) {
        enforceChangePermission();
        mHandler.sendMessage(mHandler.obtainMessage(103, i, 0));
    }

    public void setPolicyDataEnable(int i, boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "ConnectivityService");
        Handler handler = mHandler;
        Handler handler1 = mHandler;
        int j;
        if(flag)
            j = 1;
        else
            j = 0;
        handler.sendMessage(handler1.obtainMessage(113, i, j));
    }

    public boolean setRadio(int i, boolean flag) {
        boolean flag1;
        flag1 = false;
        enforceChangePermission();
        if(ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        return flag1;
_L2:
        NetworkStateTracker networkstatetracker = mNetTrackers[i];
        if(networkstatetracker != null && networkstatetracker.setRadio(flag))
            flag1 = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean setRadios(boolean flag) {
        boolean flag1 = true;
        enforceChangePermission();
        NetworkStateTracker anetworkstatetracker[] = mNetTrackers;
        int i = anetworkstatetracker.length;
        int j = 0;
        while(j < i)  {
            NetworkStateTracker networkstatetracker = anetworkstatetracker[j];
            if(networkstatetracker != null)
                if(networkstatetracker.setRadio(flag) && flag1)
                    flag1 = true;
                else
                    flag1 = false;
            j++;
        }
        return flag1;
    }

    public int setUsbTethering(boolean flag) {
        enforceTetherAccessPermission();
        int i;
        if(isTetheringSupported())
            i = mTethering.setUsbTethering(flag);
        else
            i = 3;
        return i;
    }

    public void startLegacyVpn(VpnConfig vpnconfig, String as[], String as1[]) {
        mVpn.startLegacyVpn(vpnconfig, as, as1);
    }

    public int startUsingNetworkFeature(int i, String s, IBinder ibinder) {
        long l;
        l = SystemClock.elapsedRealtime();
        enforceChangePermission();
        if(!ConnectivityManager.isNetworkTypeValid(i)) goto _L2; else goto _L1
_L1:
        NetworkConfig networkconfig = mNetConfigs[i];
        if(networkconfig != null) goto _L3; else goto _L2
_L2:
        byte byte0;
        long l2;
        byte0 = 3;
        l2 = SystemClock.elapsedRealtime() - l;
        if(l2 <= 250L) goto _L5; else goto _L4
_L4:
        String s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l2).append("ms").toString();
_L14:
        loge(s1);
_L5:
        return byte0;
_L3:
        FeatureUser featureuser;
        int j;
        boolean flag;
        featureuser = new FeatureUser(i, s, ibinder);
        j = convertFeatureToNetworkType(i, s);
        if(mProtectedNetworks.contains(Integer.valueOf(j)))
            enforceConnectivityInternalPermission();
        flag = isNetworkMeteredUnchecked(j);
        long l10;
        int k;
        synchronized(mRulesLock) {
            k = mUidRules.get(Binder.getCallingUid(), 0);
        }
        if(!flag || (k & 1) == 0)
            break MISSING_BLOCK_LABEL_287;
        byte0 = 3;
        l10 = SystemClock.elapsedRealtime() - l;
        if(l10 <= 250L) goto _L5; else goto _L6
_L6:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l10).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        long l1 = SystemClock.elapsedRealtime() - l;
        if(l1 > 250L)
            loge((new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l1).append("ms").toString());
        throw exception;
        NetworkStateTracker networkstatetracker;
        Integer integer;
        NetworkInfo networkinfo;
        networkstatetracker = mNetTrackers[j];
        if(networkstatetracker == null)
            break MISSING_BLOCK_LABEL_964;
        integer = new Integer(getCallingPid());
        if(j == i)
            break MISSING_BLOCK_LABEL_856;
        networkinfo = networkstatetracker.getNetworkInfo();
        if(networkinfo.isAvailable())
            break MISSING_BLOCK_LABEL_454;
        if(TextUtils.equals(s, "enableDUNAlways"))
            break MISSING_BLOCK_LABEL_426;
        log((new StringBuilder()).append("special network not available ni=").append(networkinfo.getTypeName()).toString());
        long l8;
        byte0 = 2;
        l8 = SystemClock.elapsedRealtime() - l;
        if(l8 <= 250L) goto _L5; else goto _L7
_L7:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l8).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        log((new StringBuilder()).append("special network not available, but try anyway ni=").append(networkinfo.getTypeName()).toString());
        int i1 = getRestoreDefaultNetworkDelay(j);
        this;
        JVM INSTR monitorenter ;
        boolean flag1;
        flag1 = true;
        if(i1 >= 0)
            break MISSING_BLOCK_LABEL_514;
        Iterator iterator = mFeatureUsers.iterator();
        do
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_514;
        while(!((FeatureUser)iterator.next()).isSameUser(featureuser));
        flag1 = false;
        if(flag1)
            mFeatureUsers.add(featureuser);
        if(!mNetRequestersPids[j].contains(integer))
            mNetRequestersPids[j].add(integer);
        this;
        JVM INSTR monitorexit ;
        long l6;
        Injector.startUsingNetworkFeature(j);
        if(i1 >= 0)
            mHandler.sendMessageDelayed(mHandler.obtainMessage(101, featureuser), i1);
        if(!networkinfo.isConnectedOrConnecting() || networkstatetracker.isTeardownRequested())
            break MISSING_BLOCK_LABEL_762;
        if(!networkinfo.isConnected())
            break MISSING_BLOCK_LABEL_710;
        l6 = Binder.clearCallingIdentity();
        handleDnsConfigurationChange(j);
        Binder.restoreCallingIdentity(l6);
        long l7;
        byte0 = 0;
        l7 = SystemClock.elapsedRealtime() - l;
        if(l7 <= 250L) goto _L5; else goto _L8
_L8:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l7).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        Exception exception3;
        exception3;
        this;
        JVM INSTR monitorexit ;
        throw exception3;
        Exception exception4;
        exception4;
        Binder.restoreCallingIdentity(l6);
        throw exception4;
        long l5;
        byte0 = 1;
        l5 = SystemClock.elapsedRealtime() - l;
        if(l5 <= 250L) goto _L5; else goto _L9
_L9:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l5).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        log((new StringBuilder()).append("startUsingNetworkFeature reconnecting to ").append(i).append(": ").append(s).toString());
        networkstatetracker.reconnect();
        long l4;
        byte0 = 1;
        l4 = SystemClock.elapsedRealtime() - l;
        if(l4 <= 250L) goto _L5; else goto _L10
_L10:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l4).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        this;
        JVM INSTR monitorenter ;
        mFeatureUsers.add(featureuser);
        if(!mNetRequestersPids[j].contains(integer))
            mNetRequestersPids[j].add(integer);
        this;
        JVM INSTR monitorexit ;
        long l3;
        byte0 = -1;
        l3 = SystemClock.elapsedRealtime() - l;
        if(l3 <= 250L) goto _L5; else goto _L11
_L11:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l3).append("ms").toString();
        continue; /* Loop/switch isn't completed */
        Exception exception2;
        exception2;
        this;
        JVM INSTR monitorexit ;
        throw exception2;
        long l9;
        byte0 = 2;
        l9 = SystemClock.elapsedRealtime() - l;
        if(l9 <= 250L) goto _L5; else goto _L12
_L12:
        s1 = (new StringBuilder()).append("startUsingNetworkFeature took too long: ").append(l9).append("ms").toString();
        if(true) goto _L14; else goto _L13
_L13:
    }

    public int stopUsingNetworkFeature(int i, String s) {
        boolean flag;
        int j;
        int k;
        FeatureUser featureuser;
        flag = true;
        enforceChangePermission();
        j = getCallingPid();
        k = getCallingUid();
        featureuser = null;
        boolean flag1 = false;
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = mFeatureUsers.iterator();
        do {
            if(!iterator.hasNext())
                break;
            FeatureUser featureuser1 = (FeatureUser)iterator.next();
            if(!featureuser1.isSameUser(j, k, i, s))
                continue;
            featureuser = featureuser1;
            flag1 = true;
            break;
        } while(true);
        this;
        JVM INSTR monitorexit ;
        if(flag1 && featureuser != null)
            flag = stopUsingNetworkFeature(featureuser, flag);
        return ((flag) ? 1 : 0);
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void systemReady() {
        this;
        JVM INSTR monitorenter ;
        mSystemReady = true;
        if(mInitialBroadcast != null) {
            mContext.sendStickyBroadcast(mInitialBroadcast);
            mInitialBroadcast = null;
        }
        this;
        JVM INSTR monitorexit ;
        mHandler.sendMessage(mHandler.obtainMessage(109));
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int tether(String s) {
        enforceTetherChangePermission();
        int i;
        if(isTetheringSupported())
            i = mTethering.tether(s);
        else
            i = 3;
        return i;
    }

    public int untether(String s) {
        enforceTetherChangePermission();
        int i;
        if(isTetheringSupported())
            i = mTethering.untether(s);
        else
            i = 3;
        return i;
    }

    public void updateNetworkSettings(NetworkStateTracker networkstatetracker) {
        String s = SystemProperties.get(networkstatetracker.getTcpBufferSizesPropName());
        if(s.length() == 0)
            s = SystemProperties.get("net.tcp.buffersize.default");
        if(s.length() != 0)
            setBufferSize(s);
    }

    private static final boolean ADD = true;
    private static final boolean DBG = true;
    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    private static final int EVENT_APPLY_GLOBAL_HTTP_PROXY = 109;
    private static final int EVENT_CHANGE_MOBILE_DATA_ENABLED = 102;
    private static final int EVENT_CLEAR_NET_TRANSITION_WAKELOCK = 108;
    private static final int EVENT_INET_CONDITION_CHANGE = 104;
    private static final int EVENT_INET_CONDITION_HOLD_END = 105;
    private static final int EVENT_RESTORE_DEFAULT_NETWORK = 101;
    private static final int EVENT_RESTORE_DNS = 111;
    private static final int EVENT_SEND_STICKY_BROADCAST_INTENT = 112;
    private static final int EVENT_SET_DEPENDENCY_MET = 110;
    private static final int EVENT_SET_MOBILE_DATA = 107;
    private static final int EVENT_SET_NETWORK_PREFERENCE = 103;
    private static final int EVENT_SET_POLICY_DATA_ENABLE = 113;
    private static final int INET_CONDITION_LOG_MAX_SIZE = 15;
    private static final boolean LOGD_RULES = false;
    private static final int MAX_HOSTROUTE_CYCLE_COUNT = 10;
    private static final int MAX_NETWORK_STATE_TRACKER_EVENT = 100;
    private static final int MIN_NETWORK_STATE_TRACKER_EVENT = 1;
    private static final String NETWORK_RESTORE_DELAY_PROP_NAME = "android.telephony.apn-restore";
    private static final boolean REMOVE = false;
    private static final int RESTORE_DEFAULT_NETWORK_DELAY = 60000;
    private static final String TAG = "ConnectivityService";
    private static final boolean TO_DEFAULT_TABLE = true;
    private static final boolean TO_SECONDARY_TABLE;
    private static final boolean VDBG;
    private static ConnectivityService sServiceInstance;
    private int mActiveDefaultNetwork;
    private Collection mAddedRoutes;
    private Context mContext;
    private LinkProperties mCurrentLinkProperties[];
    private int mDefaultConnectionSequence;
    private InetAddress mDefaultDns;
    private int mDefaultInetCondition;
    private int mDefaultInetConditionPublished;
    private ProxyProperties mDefaultProxy;
    private boolean mDefaultProxyDisabled;
    private Object mDefaultProxyLock;
    private Object mDnsLock;
    private boolean mDnsOverridden;
    private List mFeatureUsers;
    private ProxyProperties mGlobalProxy;
    private final Object mGlobalProxyLock;
    private Handler mHandler;
    private boolean mInetConditionChangeInFlight;
    private ArrayList mInetLog;
    private Intent mInitialBroadcast;
    private HashSet mMeteredIfaces;
    NetworkConfig mNetConfigs[];
    private List mNetRequestersPids[];
    private NetworkStateTracker mNetTrackers[];
    private android.os.PowerManager.WakeLock mNetTransitionWakeLock;
    private String mNetTransitionWakeLockCausedBy;
    private int mNetTransitionWakeLockSerialNumber;
    private int mNetTransitionWakeLockTimeout;
    private INetworkManagementService mNetd;
    private int mNetworkPreference;
    int mNetworksDefined;
    private int mNumDnsEntries;
    private INetworkPolicyListener mPolicyListener;
    private INetworkPolicyManager mPolicyManager;
    private int mPriorityList[];
    List mProtectedNetworks;
    RadioAttributes mRadioAttributes[];
    private Object mRulesLock;
    private SettingsObserver mSettingsObserver;
    private boolean mSystemReady;
    private boolean mTestMode;
    private Tethering mTethering;
    private boolean mTetheringConfigValid;
    private SparseIntArray mUidRules;
    private Vpn mVpn;






















/*
    static boolean access$2702(ConnectivityService connectivityservice, boolean flag) {
        connectivityservice.mDnsOverridden = flag;
        return flag;
    }

*/





/*
    static boolean access$3002(ConnectivityService connectivityservice, boolean flag) {
        connectivityservice.mDefaultProxyDisabled = flag;
        return flag;
    }

*/









}
