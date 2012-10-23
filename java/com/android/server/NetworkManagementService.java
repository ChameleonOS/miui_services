// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.net.*;
import android.net.wifi.WifiConfiguration;
import android.os.*;
import android.util.*;
import com.android.internal.net.NetworkStatsFactory;
import com.google.android.collect.Maps;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

// Referenced classes of package com.android.server:
//            NativeDaemonConnector, Watchdog, NativeDaemonConnectorException, NativeDaemonEvent, 
//            INativeDaemonConnectorCallbacks

public class NetworkManagementService extends android.os.INetworkManagementService.Stub
    implements Watchdog.Monitor {
    private class NetdCallbackReceiver
        implements INativeDaemonConnectorCallbacks {

        public void onDaemonConnected() {
            if(mConnectedSignal != null) {
                mConnectedSignal.countDown();
                mConnectedSignal = null;
            } else {
                mMainHandler.post(new Runnable() {

                    public void run() {
                        prepareNativeDaemon();
                    }

                    final NetdCallbackReceiver this$1;

                 {
                    this$1 = NetdCallbackReceiver.this;
                    super();
                }
                });
            }
        }

        public boolean onEvent(int i, String s, String as[]) {
            boolean flag = true;
            i;
            JVM INSTR tableswitch 600 601: default 28
        //                       600 34
        //                       601 230;
               goto _L1 _L2 _L3
_L1:
            flag = false;
_L5:
            return flag;
_L2:
            if(as.length < 4 || !as[flag].equals("Iface")) {
                Object aobj2[] = new Object[flag];
                aobj2[0] = s;
                throw new IllegalStateException(String.format("Invalid event from daemon (%s)", aobj2));
            }
            if(as[2].equals("added"))
                notifyInterfaceAdded(as[3]);
            else
            if(as[2].equals("removed"))
                notifyInterfaceRemoved(as[3]);
            else
            if(as[2].equals("changed") && as.length == 5)
                notifyInterfaceStatusChanged(as[3], as[4].equals("up"));
            else
            if(as[2].equals("linkstate") && as.length == 5) {
                notifyInterfaceLinkStateChanged(as[3], as[4].equals("up"));
            } else {
                Object aobj3[] = new Object[flag];
                aobj3[0] = s;
                throw new IllegalStateException(String.format("Invalid event from daemon (%s)", aobj3));
            }
            continue; /* Loop/switch isn't completed */
_L3:
            if(as.length < 5 || !as[flag].equals("limit")) {
                Object aobj[] = new Object[flag];
                aobj[0] = s;
                throw new IllegalStateException(String.format("Invalid event from daemon (%s)", aobj));
            }
            if(as[2].equals("alert")) {
                notifyLimitReached(as[3], as[4]);
            } else {
                Object aobj1[] = new Object[flag];
                aobj1[0] = s;
                throw new IllegalStateException(String.format("Invalid event from daemon (%s)", aobj1));
            }
            if(true) goto _L5; else goto _L4
_L4:
        }

        final NetworkManagementService this$0;

        private NetdCallbackReceiver() {
            this$0 = NetworkManagementService.this;
            super();
        }

    }

    class NetdResponseCode {

        public static final int BandwidthControl = 601;
        public static final int DnsProxyQueryResult = 222;
        public static final int InterfaceChange = 600;
        public static final int InterfaceGetCfgResult = 213;
        public static final int InterfaceListResult = 110;
        public static final int InterfaceRxCounterResult = 216;
        public static final int InterfaceRxThrottleResult = 218;
        public static final int InterfaceTxCounterResult = 217;
        public static final int InterfaceTxThrottleResult = 219;
        public static final int IpFwdStatusResult = 211;
        public static final int QuotaCounterResult = 220;
        public static final int SoftapStatusResult = 214;
        public static final int TetherDnsFwdTgtListResult = 112;
        public static final int TetherInterfaceListResult = 111;
        public static final int TetherStatusResult = 210;
        public static final int TetheringStatsResult = 221;
        public static final int TtyListResult = 113;
        final NetworkManagementService this$0;

        NetdResponseCode() {
            this$0 = NetworkManagementService.this;
            super();
        }
    }


    private NetworkManagementService(Context context) {
        mConnectedSignal = new CountDownLatch(1);
        mQuotaLock = new Object();
        mActiveQuotas = Maps.newHashMap();
        mActiveAlerts = Maps.newHashMap();
        mUidRejectOnQuota = new SparseBooleanArray();
        mContext = context;
        if(!"simulator".equals(SystemProperties.get("ro.product.device"))) {
            mConnector = new NativeDaemonConnector(new NetdCallbackReceiver(), "netd", 10, "NetdConnector", 160);
            mThread = new Thread(mConnector, "NetdConnector");
            Watchdog.getInstance().addMonitor(this);
        }
    }

    public static NetworkManagementService create(Context context) throws InterruptedException {
        NetworkManagementService networkmanagementservice = new NetworkManagementService(context);
        CountDownLatch countdownlatch = networkmanagementservice.mConnectedSignal;
        networkmanagementservice.mThread.start();
        countdownlatch.await();
        return networkmanagementservice;
    }

    private int getInterfaceThrottle(String s, boolean flag) {
        NativeDaemonConnector nativedaemonconnector;
        Object aobj[];
        String s1;
        nativedaemonconnector = mConnector;
        aobj = new Object[3];
        aobj[0] = "getthrottle";
        aobj[1] = s;
        if(!flag)
            break MISSING_BLOCK_LABEL_73;
        s1 = "rx";
_L1:
        NativeDaemonEvent nativedaemonevent;
        aobj[2] = s1;
        nativedaemonevent = nativedaemonconnector.execute("interface", aobj);
        NativeDaemonConnectorException nativedaemonconnectorexception;
        int i;
        if(flag)
            nativedaemonevent.checkCode(218);
        else
            nativedaemonevent.checkCode(219);
        try {
            i = Integer.parseInt(nativedaemonevent.getMessage());
        }
        catch(NumberFormatException numberformatexception) {
            throw new IllegalStateException((new StringBuilder()).append("unexpected response:").append(nativedaemonevent).toString());
        }
        return i;
        try {
            s1 = "tx";
        }
        // Misplaced declaration of an exception variable
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
          goto _L1
    }

    private android.net.NetworkStats.Entry getNetworkStatsTethering(String s, String s1) {
        NativeDaemonEvent nativedaemonevent;
        StringTokenizer stringtokenizer;
        android.net.NetworkStats.Entry entry;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "gettetherstats";
            aobj[1] = s;
            aobj[2] = s1;
            nativedaemonevent = nativedaemonconnector.execute("bandwidth", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        nativedaemonevent.checkCode(221);
        stringtokenizer = new StringTokenizer(nativedaemonevent.getMessage());
        stringtokenizer.nextToken();
        stringtokenizer.nextToken();
        try {
            entry = new android.net.NetworkStats.Entry();
            entry.iface = s;
            entry.uid = -5;
            entry.set = 0;
            entry.tag = 0;
            entry.rxBytes = Long.parseLong(stringtokenizer.nextToken());
            entry.rxPackets = Long.parseLong(stringtokenizer.nextToken());
            entry.txBytes = Long.parseLong(stringtokenizer.nextToken());
            entry.txPackets = Long.parseLong(stringtokenizer.nextToken());
        }
        catch(NumberFormatException numberformatexception) {
            throw new IllegalStateException((new StringBuilder()).append("problem parsing tethering stats for ").append(s).append(" ").append(s1).append(": ").append(numberformatexception).toString());
        }
        return entry;
    }

    private static String getSecurityType(WifiConfiguration wificonfiguration) {
        wificonfiguration.getAuthType();
        JVM INSTR tableswitch 1 4: default 36
    //                   1 42
    //                   2 36
    //                   3 36
    //                   4 49;
           goto _L1 _L2 _L1 _L1 _L3
_L1:
        String s = "open";
_L5:
        return s;
_L2:
        s = "wpa-psk";
        continue; /* Loop/switch isn't completed */
_L3:
        s = "wpa2-psk";
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void modifyNat(String s, String s1, String s2) throws SocketException {
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[1] = s1;
        aobj[2] = s2;
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("nat", aobj);
        NetworkInterface networkinterface = NetworkInterface.getByName(s1);
        if(networkinterface == null) {
            command.appendArg("0");
        } else {
            java.util.List list = networkinterface.getInterfaceAddresses();
            command.appendArg(Integer.valueOf(list.size()));
            Iterator iterator = list.iterator();
            while(iterator.hasNext())  {
                InterfaceAddress interfaceaddress = (InterfaceAddress)iterator.next();
                InetAddress inetaddress = NetworkUtils.getNetworkPart(interfaceaddress.getAddress(), interfaceaddress.getNetworkPrefixLength());
                command.appendArg((new StringBuilder()).append(inetaddress.getHostAddress()).append("/").append(interfaceaddress.getNetworkPrefixLength()).toString());
            }
        }
        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    private void modifyRoute(String s, String s1, RouteInfo routeinfo, String s2) {
        Object aobj[] = new Object[4];
        aobj[0] = "route";
        aobj[1] = s1;
        aobj[2] = s;
        aobj[3] = s2;
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("interface", aobj);
        LinkAddress linkaddress = routeinfo.getDestination();
        command.appendArg(linkaddress.getAddress().getHostAddress());
        command.appendArg(Integer.valueOf(linkaddress.getNetworkPrefixLength()));
        if(routeinfo.getGateway() == null) {
            if(linkaddress.getAddress() instanceof Inet4Address)
                command.appendArg("0.0.0.0");
            else
                command.appendArg("::0");
        } else {
            command.appendArg(routeinfo.getGateway().getHostAddress());
        }
        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    private void notifyInterfaceAdded(String s) {
        int i = mObservers.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((INetworkManagementEventObserver)mObservers.getBroadcastItem(j)).interfaceAdded(s);
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        mObservers.finishBroadcast();
    }

    private void notifyInterfaceLinkStateChanged(String s, boolean flag) {
        int i = mObservers.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((INetworkManagementEventObserver)mObservers.getBroadcastItem(j)).interfaceLinkStateChanged(s, flag);
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        mObservers.finishBroadcast();
    }

    private void notifyInterfaceRemoved(String s) {
        mActiveAlerts.remove(s);
        mActiveQuotas.remove(s);
        int i = mObservers.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((INetworkManagementEventObserver)mObservers.getBroadcastItem(j)).interfaceRemoved(s);
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        mObservers.finishBroadcast();
    }

    private void notifyInterfaceStatusChanged(String s, boolean flag) {
        int i = mObservers.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((INetworkManagementEventObserver)mObservers.getBroadcastItem(j)).interfaceStatusChanged(s, flag);
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        mObservers.finishBroadcast();
    }

    private void notifyLimitReached(String s, String s1) {
        int i = mObservers.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((INetworkManagementEventObserver)mObservers.getBroadcastItem(j)).limitReached(s, s1);
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        mObservers.finishBroadcast();
    }

    private void prepareNativeDaemon() {
        Object obj;
        mBandwidthControlEnabled = false;
        String s;
        if((new File("/proc/net/xt_qtaguid/ctrl")).exists()) {
            Slog.d("NetworkManagementService", "enabling bandwidth control");
            Exception exception;
            int i;
            HashMap hashmap1;
            Iterator iterator1;
            java.util.Map.Entry entry1;
            try {
                NativeDaemonConnector nativedaemonconnector = mConnector;
                Object aobj[] = new Object[1];
                aobj[0] = "enable";
                nativedaemonconnector.execute("bandwidth", aobj);
                mBandwidthControlEnabled = true;
            }
            catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
                Log.wtf("NetworkManagementService", "problem enabling bandwidth controls", nativedaemonconnectorexception);
            }
        } else {
            Slog.d("NetworkManagementService", "not enabling bandwidth control");
        }
        if(mBandwidthControlEnabled)
            s = "1";
        else
            s = "0";
        SystemProperties.set("net.qtaguid_enabled", s);
        obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        i = mActiveQuotas.size();
        if(i > 0) {
            Slog.d("NetworkManagementService", (new StringBuilder()).append("pushing ").append(i).append(" active quota rules").toString());
            hashmap1 = mActiveQuotas;
            mActiveQuotas = Maps.newHashMap();
            for(iterator1 = hashmap1.entrySet().iterator(); iterator1.hasNext(); setInterfaceQuota((String)entry1.getKey(), ((Long)entry1.getValue()).longValue()))
                entry1 = (java.util.Map.Entry)iterator1.next();

        }
        break MISSING_BLOCK_LABEL_256;
        exception;
        throw exception;
        int j = mActiveAlerts.size();
        if(j > 0) {
            Slog.d("NetworkManagementService", (new StringBuilder()).append("pushing ").append(j).append(" active alert rules").toString());
            HashMap hashmap = mActiveAlerts;
            mActiveAlerts = Maps.newHashMap();
            java.util.Map.Entry entry;
            for(Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext(); setInterfaceAlert((String)entry.getKey(), ((Long)entry.getValue()).longValue()))
                entry = (java.util.Map.Entry)iterator.next();

        }
        int k = mUidRejectOnQuota.size();
        if(k > 0) {
            Slog.d("NetworkManagementService", (new StringBuilder()).append("pushing ").append(k).append(" active uid rules").toString());
            SparseBooleanArray sparsebooleanarray = mUidRejectOnQuota;
            mUidRejectOnQuota = new SparseBooleanArray();
            for(int l = 0; l < sparsebooleanarray.size(); l++)
                setUidNetworkRules(sparsebooleanarray.keyAt(l), sparsebooleanarray.valueAt(l));

        }
        obj;
        JVM INSTR monitorexit ;
    }

    private ArrayList readRouteList(String s) {
        FileInputStream fileinputstream;
        ArrayList arraylist;
        fileinputstream = null;
        arraylist = new ArrayList();
        FileInputStream fileinputstream1 = new FileInputStream(s);
        Exception exception;
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new DataInputStream(fileinputstream1)));
        IOException ioexception1;
        do {
            String s1 = bufferedreader.readLine();
            if(s1 == null || s1.length() == 0)
                break;
            arraylist.add(s1);
        } while(true);
        IOException ioexception;
        IOException ioexception2;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception3) { }
          goto _L1
        ioexception1;
        fileinputstream = fileinputstream1;
_L7:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception2) { }
_L3:
        return arraylist;
_L1:
        if(true) goto _L3; else goto _L2
_L2:
        exception;
_L5:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L5; else goto _L4
_L4:
        IOException ioexception4;
        ioexception4;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void addRoute(String s, RouteInfo routeinfo) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        modifyRoute(s, "add", routeinfo, "default");
    }

    public void addSecondaryRoute(String s, RouteInfo routeinfo) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        modifyRoute(s, "add", routeinfo, "secondary");
    }

    public void attachPppd(String s, String s1, String s2, String s3, String s4) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[6];
            aobj[0] = "attach";
            aobj[1] = s;
            aobj[2] = NetworkUtils.numericToInetAddress(s1).getHostAddress();
            aobj[3] = NetworkUtils.numericToInetAddress(s2).getHostAddress();
            aobj[4] = NetworkUtils.numericToInetAddress(s3).getHostAddress();
            aobj[5] = NetworkUtils.numericToInetAddress(s4).getHostAddress();
            nativedaemonconnector.execute("pppd", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void clearInterfaceAddresses(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "clearaddrs";
            aobj[1] = s;
            nativedaemonconnector.execute("interface", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void detachPppd(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "detach";
            aobj[1] = s;
            nativedaemonconnector.execute("pppd", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void disableIpv6(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "ipv6";
            aobj[1] = s;
            aobj[2] = "disable";
            nativedaemonconnector.execute("interface", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void disableNat(String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            modifyNat("disable", s, s1);
            return;
        }
        catch(SocketException socketexception) {
            throw new IllegalStateException(socketexception);
        }
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "NetworkManagementService");
        printwriter.println("NetworkManagementService NativeDaemonConnector Log:");
        mConnector.dump(filedescriptor, printwriter, as);
        printwriter.println();
        printwriter.print("Bandwidth control enabled: ");
        printwriter.println(mBandwidthControlEnabled);
        synchronized(mQuotaLock) {
            printwriter.print("Active quota ifaces: ");
            printwriter.println(mActiveQuotas.toString());
            printwriter.print("Active alert ifaces: ");
            printwriter.println(mActiveAlerts.toString());
        }
        SparseBooleanArray sparsebooleanarray = mUidRejectOnQuota;
        sparsebooleanarray;
        JVM INSTR monitorenter ;
        int i;
        int j;
        printwriter.print("UID reject on quota ifaces: [");
        i = mUidRejectOnQuota.size();
        j = 0;
_L1:
        if(j >= i)
            break MISSING_BLOCK_LABEL_174;
        printwriter.print(mUidRejectOnQuota.keyAt(j));
        if(j < i - 1)
            printwriter.print(",");
        j++;
          goto _L1
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        printwriter.println("]");
        return;
        Exception exception1;
        exception1;
        throw exception1;
    }

    public void enableIpv6(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "ipv6";
            aobj[1] = s;
            aobj[2] = "enable";
            nativedaemonconnector.execute("interface", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void enableNat(String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            modifyNat("enable", s, s1);
            return;
        }
        catch(SocketException socketexception) {
            throw new IllegalStateException(socketexception);
        }
    }

    public void flushDefaultDnsCache() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "flushdefaultif";
            nativedaemonconnector.execute("resolver", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void flushInterfaceDnsCache(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "flushif";
            aobj[1] = s;
            nativedaemonconnector.execute("resolver", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public String[] getDnsForwarders() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        String as[];
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "dns";
            aobj[1] = "list";
            as = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("tether", aobj), 112);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        return as;
    }

    public InterfaceConfiguration getInterfaceConfig(String s) {
        InterfaceConfiguration interfaceconfiguration;
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        NativeDaemonEvent nativedaemonevent;
        StringTokenizer stringtokenizer;
        InetAddress inetaddress;
        int i;
        int j;
        InetAddress inetaddress1;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "getcfg";
            aobj[1] = s;
            nativedaemonevent = nativedaemonconnector.execute("interface", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        nativedaemonevent.checkCode(213);
        stringtokenizer = new StringTokenizer(nativedaemonevent.getMessage());
        try {
            interfaceconfiguration = new InterfaceConfiguration();
            interfaceconfiguration.setHardwareAddress(stringtokenizer.nextToken(" "));
        }
        catch(NoSuchElementException nosuchelementexception) {
            throw new IllegalStateException((new StringBuilder()).append("Invalid response from daemon: ").append(nativedaemonevent).toString());
        }
        inetaddress = null;
        i = 0;
        inetaddress1 = NetworkUtils.numericToInetAddress(stringtokenizer.nextToken());
        inetaddress = inetaddress1;
_L4:
        j = Integer.parseInt(stringtokenizer.nextToken());
        i = j;
_L2:
        interfaceconfiguration.setLinkAddress(new LinkAddress(inetaddress, i));
        for(; stringtokenizer.hasMoreTokens(); interfaceconfiguration.setFlag(stringtokenizer.nextToken()));
        break; /* Loop/switch isn't completed */
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        Slog.e("NetworkManagementService", "Failed to parse ipaddr", illegalargumentexception);
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        Slog.e("NetworkManagementService", "Failed to parse prefixLength", numberformatexception);
        if(true) goto _L2; else goto _L1
_L1:
        return interfaceconfiguration;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getInterfaceRxThrottle(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return getInterfaceThrottle(s, true);
    }

    public int getInterfaceTxThrottle(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return getInterfaceThrottle(s, false);
    }

    public boolean getIpForwardingEnabled() throws IllegalStateException {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        NativeDaemonEvent nativedaemonevent;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "status";
            nativedaemonevent = nativedaemonconnector.execute("ipfwd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        nativedaemonevent.checkCode(211);
        return nativedaemonevent.getMessage().endsWith("enabled");
    }

    public NetworkStats getNetworkStatsDetail() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return mStatsFactory.readNetworkStatsDetail(-1);
    }

    public NetworkStats getNetworkStatsSummaryDev() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return mStatsFactory.readNetworkStatsSummaryDev();
    }

    public NetworkStats getNetworkStatsSummaryXt() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return mStatsFactory.readNetworkStatsSummaryXt();
    }

    public NetworkStats getNetworkStatsTethering(String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(as.length % 2 != 0)
            throw new IllegalArgumentException((new StringBuilder()).append("unexpected ifacePairs; length=").append(as.length).toString());
        NetworkStats networkstats = new NetworkStats(SystemClock.elapsedRealtime(), 1);
        for(int i = 0; i < as.length; i += 2) {
            String s = as[i];
            String s1 = as[i + 1];
            if(s != null && s1 != null)
                networkstats.combineValues(getNetworkStatsTethering(s, s1));
        }

        return networkstats;
    }

    public NetworkStats getNetworkStatsUidDetail(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return mStatsFactory.readNetworkStatsDetail(i);
    }

    public RouteInfo[] getRoutes(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        ArrayList arraylist = new ArrayList();
        Iterator iterator = readRouteList("/proc/net/route").iterator();
        do {
            if(!iterator.hasNext())
                break;
            String s5 = (String)iterator.next();
            String as1[] = s5.split("\t");
            if(as1.length > 7 && s.equals(as1[0])) {
                String s6 = as1[1];
                String s7 = as1[2];
                String _tmp = as1[3];
                String s8 = as1[7];
                try {
                    LinkAddress linkaddress1 = new LinkAddress(NetworkUtils.intToInetAddress((int)Long.parseLong(s6, 16)), NetworkUtils.netmaskIntToPrefixLength((int)Long.parseLong(s8, 16)));
                    InetAddress inetaddress1 = NetworkUtils.intToInetAddress((int)Long.parseLong(s7, 16));
                    RouteInfo routeinfo1 = new RouteInfo(linkaddress1, inetaddress1);
                    arraylist.add(routeinfo1);
                }
                catch(Exception exception1) {
                    Log.e("NetworkManagementService", (new StringBuilder()).append("Error parsing route ").append(s5).append(" : ").append(exception1).toString());
                }
            }
        } while(true);
        Iterator iterator1 = readRouteList("/proc/net/ipv6_route").iterator();
        do {
            if(!iterator1.hasNext())
                break;
            String s1 = (String)iterator1.next();
            String as[] = s1.split("\\s+");
            if(as.length > 9 && s.equals(as[9].trim())) {
                String s2 = as[0];
                String s3 = as[1];
                String s4 = as[4];
                try {
                    int i = Integer.parseInt(s3, 16);
                    LinkAddress linkaddress = new LinkAddress(NetworkUtils.hexToInet6Address(s2), i);
                    InetAddress inetaddress = NetworkUtils.hexToInet6Address(s4);
                    RouteInfo routeinfo = new RouteInfo(linkaddress, inetaddress);
                    arraylist.add(routeinfo);
                }
                catch(Exception exception) {
                    Log.e("NetworkManagementService", (new StringBuilder()).append("Error parsing route ").append(s1).append(" : ").append(exception).toString());
                }
            }
        } while(true);
        return (RouteInfo[])arraylist.toArray(new RouteInfo[arraylist.size()]);
    }

    public boolean isBandwidthControlEnabled() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        return mBandwidthControlEnabled;
    }

    public boolean isTetheringStarted() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        NativeDaemonEvent nativedaemonevent;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "status";
            nativedaemonevent = nativedaemonconnector.execute("tether", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        nativedaemonevent.checkCode(210);
        return nativedaemonevent.getMessage().endsWith("started");
    }

    public String[] listInterfaces() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        String as[];
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "list";
            as = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("interface", aobj), 110);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        return as;
    }

    public String[] listTetheredInterfaces() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        String as[];
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "interface";
            aobj[1] = "list";
            as = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("tether", aobj), 111);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        return as;
    }

    public String[] listTtys() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        String as[];
        try {
            as = NativeDaemonEvent.filterMessageList(mConnector.executeForList("list_ttys", new Object[0]), 113);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        return as;
    }

    public void monitor() {
        if(mConnector != null)
            mConnector.monitor();
    }

    public void registerObserver(INetworkManagementEventObserver inetworkmanagementeventobserver) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        mObservers.register(inetworkmanagementeventobserver);
    }

    public void removeInterfaceAlert(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        if(mActiveAlerts.containsKey(s)) goto _L3; else goto _L1
_L3:
        break MISSING_BLOCK_LABEL_48;
        Exception exception;
        exception;
        throw exception;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "removeinterfacealert";
            aobj[1] = s;
            nativedaemonconnector.execute("bandwidth", aobj);
            mActiveAlerts.remove(s);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void removeInterfaceQuota(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        if(mActiveQuotas.containsKey(s)) goto _L3; else goto _L1
_L3:
        break MISSING_BLOCK_LABEL_48;
        Exception exception;
        exception;
        throw exception;
        mActiveQuotas.remove(s);
        mActiveAlerts.remove(s);
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "removeiquota";
            aobj[1] = s;
            nativedaemonconnector.execute("bandwidth", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void removeRoute(String s, RouteInfo routeinfo) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        modifyRoute(s, "remove", routeinfo, "default");
    }

    public void removeSecondaryRoute(String s, RouteInfo routeinfo) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        modifyRoute(s, "remove", routeinfo, "secondary");
    }

    public void setAccessPoint(WifiConfiguration wificonfiguration, String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(wificonfiguration != null)
            break MISSING_BLOCK_LABEL_59;
        NativeDaemonConnector nativedaemonconnector1 = mConnector;
        Object aobj1[] = new Object[3];
        aobj1[0] = "set";
        aobj1[1] = s;
        aobj1[2] = s1;
        nativedaemonconnector1.execute("softap", aobj1);
        break MISSING_BLOCK_LABEL_135;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[6];
        aobj[0] = "set";
        aobj[1] = s;
        aobj[2] = s1;
        aobj[3] = wificonfiguration.SSID;
        aobj[4] = getSecurityType(wificonfiguration);
        aobj[5] = wificonfiguration.preSharedKey;
        nativedaemonconnector.execute("softap", aobj);
        break MISSING_BLOCK_LABEL_135;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        throw nativedaemonconnectorexception.rethrowAsParcelableException();
    }

    public void setDefaultInterfaceForDns(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "setdefaultif";
            aobj[1] = s;
            nativedaemonconnector.execute("resolver", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setDnsForwarders(String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        Object aobj[] = new Object[2];
        aobj[0] = "dns";
        aobj[1] = "set";
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("tether", aobj);
        int i = as.length;
        for(int j = 0; j < i; j++)
            command.appendArg(NetworkUtils.numericToInetAddress(as[j]).getHostAddress());

        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setDnsServersForInterface(String s, String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        Object aobj[] = new Object[2];
        aobj[0] = "setifdns";
        aobj[1] = s;
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("resolver", aobj);
        int i = as.length;
        for(int j = 0; j < i; j++) {
            InetAddress inetaddress = NetworkUtils.numericToInetAddress(as[j]);
            if(!inetaddress.isAnyLocalAddress())
                command.appendArg(inetaddress.getHostAddress());
        }

        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setGlobalAlert(long l) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled)
            try {
                NativeDaemonConnector nativedaemonconnector = mConnector;
                Object aobj[] = new Object[2];
                aobj[0] = "setglobalalert";
                aobj[1] = Long.valueOf(l);
                nativedaemonconnector.execute("bandwidth", aobj);
            }
            catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
                throw nativedaemonconnectorexception.rethrowAsParcelableException();
            }
    }

    public void setInterfaceAlert(String s, long l) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!mActiveQuotas.containsKey(s))
            throw new IllegalStateException("setting alert requires existing quota on iface");
        Object obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        if(mActiveAlerts.containsKey(s))
            throw new IllegalStateException((new StringBuilder()).append("iface ").append(s).append(" already has alert").toString());
        break MISSING_BLOCK_LABEL_104;
        Exception exception;
        exception;
        throw exception;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "setinterfacealert";
            aobj[1] = s;
            aobj[2] = Long.valueOf(l);
            nativedaemonconnector.execute("bandwidth", aobj);
            mActiveAlerts.put(s, Long.valueOf(l));
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void setInterfaceConfig(String s, InterfaceConfiguration interfaceconfiguration) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        LinkAddress linkaddress = interfaceconfiguration.getLinkAddress();
        if(linkaddress == null || linkaddress.getAddress() == null)
            throw new IllegalStateException("Null LinkAddress given");
        Object aobj[] = new Object[4];
        aobj[0] = "setcfg";
        aobj[1] = s;
        aobj[2] = linkaddress.getAddress().getHostAddress();
        aobj[3] = Integer.valueOf(linkaddress.getNetworkPrefixLength());
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("interface", aobj);
        for(Iterator iterator = interfaceconfiguration.getFlags().iterator(); iterator.hasNext(); command.appendArg((String)iterator.next()));
        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setInterfaceDown(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        InterfaceConfiguration interfaceconfiguration = getInterfaceConfig(s);
        interfaceconfiguration.setInterfaceDown();
        setInterfaceConfig(s, interfaceconfiguration);
    }

    public void setInterfaceIpv6PrivacyExtensions(String s, boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "ipv6privacyextensions";
            aobj[1] = s;
            String s1;
            if(flag)
                s1 = "enable";
            else
                s1 = "disable";
            aobj[2] = s1;
            nativedaemonconnector.execute("interface", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setInterfaceQuota(String s, long l) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        if(mActiveQuotas.containsKey(s))
            throw new IllegalStateException((new StringBuilder()).append("iface ").append(s).append(" already has quota").toString());
        break MISSING_BLOCK_LABEL_82;
        Exception exception;
        exception;
        throw exception;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "setiquota";
            aobj[1] = s;
            aobj[2] = Long.valueOf(l);
            nativedaemonconnector.execute("bandwidth", aobj);
            mActiveQuotas.put(s, Long.valueOf(l));
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void setInterfaceThrottle(String s, int i, int j) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[4];
            aobj[0] = "setthrottle";
            aobj[1] = s;
            aobj[2] = Integer.valueOf(i);
            aobj[3] = Integer.valueOf(j);
            nativedaemonconnector.execute("interface", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setInterfaceUp(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        InterfaceConfiguration interfaceconfiguration = getInterfaceConfig(s);
        interfaceconfiguration.setInterfaceUp();
        setInterfaceConfig(s, interfaceconfiguration);
    }

    public void setIpForwardingEnabled(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            String s;
            if(flag)
                s = "enable";
            else
                s = "disable";
            aobj[0] = s;
            nativedaemonconnector.execute("ipfwd", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void setUidNetworkRules(int i, boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        if(mBandwidthControlEnabled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mQuotaLock;
        obj;
        JVM INSTR monitorenter ;
        if(mUidRejectOnQuota.get(i, false) != flag) goto _L3; else goto _L1
_L3:
        break MISSING_BLOCK_LABEL_52;
        Exception exception;
        exception;
        throw exception;
        NativeDaemonConnector nativedaemonconnector;
        Object aobj[];
        nativedaemonconnector = mConnector;
        aobj = new Object[2];
        if(!flag) goto _L5; else goto _L4
_L4:
        String s = "addnaughtyapps";
_L6:
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        nativedaemonconnector.execute("bandwidth", aobj);
        if(!flag)
            break MISSING_BLOCK_LABEL_124;
        mUidRejectOnQuota.put(i, true);
_L7:
        obj;
        JVM INSTR monitorexit ;
          goto _L1
_L5:
        s = "removenaughtyapps";
          goto _L6
        mUidRejectOnQuota.delete(i);
          goto _L7
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        throw nativedaemonconnectorexception.rethrowAsParcelableException();
          goto _L1
    }

    public void shutdown() {
        mContext.enforceCallingOrSelfPermission("android.permission.SHUTDOWN", "NetworkManagementService");
        Slog.d("NetworkManagementService", "Shutting down");
    }

    public void startAccessPoint(WifiConfiguration wificonfiguration, String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            wifiFirmwareReload(s, "AP");
            NativeDaemonConnector nativedaemonconnector1;
            Object aobj1[];
            if(wificonfiguration == null) {
                NativeDaemonConnector nativedaemonconnector2 = mConnector;
                Object aobj2[] = new Object[3];
                aobj2[0] = "set";
                aobj2[1] = s;
                aobj2[2] = s1;
                nativedaemonconnector2.execute("softap", aobj2);
            } else {
                NativeDaemonConnector nativedaemonconnector = mConnector;
                Object aobj[] = new Object[6];
                aobj[0] = "set";
                aobj[1] = s;
                aobj[2] = s1;
                aobj[3] = wificonfiguration.SSID;
                aobj[4] = getSecurityType(wificonfiguration);
                aobj[5] = wificonfiguration.preSharedKey;
                nativedaemonconnector.execute("softap", aobj);
            }
            nativedaemonconnector1 = mConnector;
            aobj1 = new Object[1];
            aobj1[0] = "startap";
            nativedaemonconnector1.execute("softap", aobj1);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void startTethering(String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        Object aobj[] = new Object[1];
        aobj[0] = "start";
        NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("tether", aobj);
        int i = as.length;
        for(int j = 0; j < i; j++)
            command.appendArg(as[j]);

        try {
            mConnector.execute(command);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void stopAccessPoint(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "stopap";
            nativedaemonconnector.execute("softap", aobj);
            NativeDaemonConnector nativedaemonconnector1 = mConnector;
            Object aobj1[] = new Object[2];
            aobj1[0] = "stop";
            aobj1[1] = s;
            nativedaemonconnector1.execute("softap", aobj1);
            wifiFirmwareReload(s, "STA");
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void stopTethering() {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "stop";
            nativedaemonconnector.execute("tether", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void systemReady() {
        prepareNativeDaemon();
    }

    public void tetherInterface(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "interface";
            aobj[1] = "add";
            aobj[2] = s;
            nativedaemonconnector.execute("tether", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void unregisterObserver(INetworkManagementEventObserver inetworkmanagementeventobserver) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        mObservers.unregister(inetworkmanagementeventobserver);
    }

    public void untetherInterface(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "interface";
            aobj[1] = "remove";
            aobj[2] = s;
            nativedaemonconnector.execute("tether", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    public void wifiFirmwareReload(String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkManagementService");
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "fwreload";
            aobj[1] = s;
            aobj[2] = s1;
            nativedaemonconnector.execute("softap", aobj);
            return;
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            throw nativedaemonconnectorexception.rethrowAsParcelableException();
        }
    }

    private static final String ADD = "add";
    private static final boolean DBG = false;
    private static final String DEFAULT = "default";
    public static final String LIMIT_GLOBAL_ALERT = "globalAlert";
    private static final String NETD_TAG = "NetdConnector";
    private static final String REMOVE = "remove";
    private static final String SECONDARY = "secondary";
    private static final String TAG = "NetworkManagementService";
    private HashMap mActiveAlerts;
    private HashMap mActiveQuotas;
    private volatile boolean mBandwidthControlEnabled;
    private CountDownLatch mConnectedSignal;
    private NativeDaemonConnector mConnector;
    private Context mContext;
    private final Handler mMainHandler = new Handler();
    private final RemoteCallbackList mObservers = new RemoteCallbackList();
    private Object mQuotaLock;
    private final NetworkStatsFactory mStatsFactory = new NetworkStatsFactory();
    private Thread mThread;
    private SparseBooleanArray mUidRejectOnQuota;



/*
    static CountDownLatch access$102(NetworkManagementService networkmanagementservice, CountDownLatch countdownlatch) {
        networkmanagementservice.mConnectedSignal = countdownlatch;
        return countdownlatch;
    }

*/







}
