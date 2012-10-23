// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.database.ContentObserver;
import android.net.nsd.DnsSdTxtRecord;
import android.net.nsd.NsdServiceInfo;
import android.os.*;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.*;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

// Referenced classes of package com.android.server:
//            NativeDaemonConnector, NativeDaemonConnectorException, INativeDaemonConnectorCallbacks, NativeDaemonEvent

public class NsdService extends android.net.nsd.INsdManager.Stub {
    private class ClientInfo {

        public String toString() {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append("mChannel ").append(mChannel).append("\n");
            stringbuffer.append("mMessenger ").append(mMessenger).append("\n");
            stringbuffer.append("mResolvedService ").append(mResolvedService).append("\n");
            for(int i = 0; i < mClientIds.size(); i++) {
                stringbuffer.append("clientId ").append(mClientIds.keyAt(i));
                stringbuffer.append(" mDnsId ").append(mClientIds.valueAt(i)).append("\n");
            }

            return stringbuffer.toString();
        }

        private static final int MAX_LIMIT = 10;
        private AsyncChannel mChannel;
        private SparseArray mClientIds;
        private Messenger mMessenger;
        private NsdServiceInfo mResolvedService;
        final NsdService this$0;




/*
        static NsdServiceInfo access$2302(ClientInfo clientinfo, NsdServiceInfo nsdserviceinfo) {
            clientinfo.mResolvedService = nsdserviceinfo;
            return nsdserviceinfo;
        }

*/


        private ClientInfo(AsyncChannel asyncchannel, Messenger messenger) {
            this$0 = NsdService.this;
            super();
            mClientIds = new SparseArray();
            mChannel = asyncchannel;
            mMessenger = messenger;
            Slog.d("NsdService", (new StringBuilder()).append("New client, channel: ").append(asyncchannel).append(" messenger: ").append(messenger).toString());
        }

    }

    class NativeCallbackReceiver
        implements INativeDaemonConnectorCallbacks {

        public void onDaemonConnected() {
            mNativeDaemonConnected.countDown();
        }

        public boolean onEvent(int i, String s, String as[]) {
            NativeEvent nativeevent = new NativeEvent(i, s);
            mNsdStateMachine.sendMessage(0x6001a, nativeevent);
            return true;
        }

        final NsdService this$0;

        NativeCallbackReceiver() {
            this$0 = NsdService.this;
            super();
        }
    }

    private class NativeEvent {

        int code;
        String raw;
        final NsdService this$0;

        NativeEvent(int i, String s) {
            this$0 = NsdService.this;
            super();
            code = i;
            raw = s;
        }
    }

    class NativeResponseCode {

        public static final int SERVICE_DISCOVERY_FAILED = 602;
        public static final int SERVICE_FOUND = 603;
        public static final int SERVICE_GET_ADDR_FAILED = 611;
        public static final int SERVICE_GET_ADDR_SUCCESS = 612;
        public static final int SERVICE_LOST = 604;
        public static final int SERVICE_REGISTERED = 606;
        public static final int SERVICE_REGISTRATION_FAILED = 605;
        public static final int SERVICE_RESOLUTION_FAILED = 607;
        public static final int SERVICE_RESOLVED = 608;
        public static final int SERVICE_UPDATED = 609;
        public static final int SERVICE_UPDATE_FAILED = 610;
        final NsdService this$0;

        NativeResponseCode() {
            this$0 = NsdService.this;
            super();
        }
    }

    private class NsdStateMachine extends StateMachine {
        class EnabledState extends State {

            private void removeRequestMap(int i, int j, ClientInfo clientinfo) {
                clientinfo.mClientIds.remove(i);
                mIdToClientInfoMap.remove(j);
            }

            private boolean requestLimitReached(ClientInfo clientinfo) {
                boolean flag;
                if(clientinfo.mClientIds.size() >= 10) {
                    Slog.d("NsdService", (new StringBuilder()).append("Exceeded max outstanding requests ").append(clientinfo).toString());
                    flag = true;
                } else {
                    flag = false;
                }
                return flag;
            }

            private void storeRequestMap(int i, int j, ClientInfo clientinfo) {
                clientinfo.mClientIds.put(i, Integer.valueOf(j));
                mIdToClientInfoMap.put(j, clientinfo);
            }

            public void enter() {
                sendNsdStateChangeBroadcast(true);
                if(mClients.size() > 0)
                    startMDnsDaemon();
            }

            public void exit() {
                if(mClients.size() > 0)
                    stopMDnsDaemon();
            }

            public boolean processMessage(Message message) {
                boolean flag = true;
                message.what;
                JVM INSTR lookupswitch 9: default 88
            //                           69632: 92
            //                           69636: 131
            //                           393217: 181
            //                           393222: 384
            //                           393225: 513
            //                           393228: 686
            //                           393234: 815
            //                           393241: 164
            //                           393242: 953;
                   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10
_L1:
                flag = false;
_L12:
                return flag;
_L2:
                if(message.arg1 == 0 && mClients.size() == 0)
                    startMDnsDaemon();
                flag = false;
                continue; /* Loop/switch isn't completed */
_L3:
                if(mClients.size() == 1)
                    stopMDnsDaemon();
                flag = false;
                continue; /* Loop/switch isn't completed */
_L9:
                transitionTo(mDisabledState);
                continue; /* Loop/switch isn't completed */
_L4:
                Slog.d("NsdService", "Discover services");
                NsdServiceInfo nsdserviceinfo1 = (NsdServiceInfo)message.obj;
                ClientInfo clientinfo4 = (ClientInfo)mClients.get(message.replyTo);
                if(requestLimitReached(clientinfo4)) {
                    replyToMessage(message, 0x60003, 4);
                } else {
                    int i1 = getUniqueId();
                    if(discoverServices(i1, nsdserviceinfo1.getServiceType())) {
                        Slog.d("NsdService", (new StringBuilder()).append("Discover ").append(message.arg2).append(" ").append(i1).append(nsdserviceinfo1.getServiceType()).toString());
                        storeRequestMap(message.arg2, i1, clientinfo4);
                        replyToMessage(message, 0x60002, nsdserviceinfo1);
                    } else {
                        stopServiceDiscovery(i1);
                        replyToMessage(message, 0x60003, 0);
                    }
                }
                continue; /* Loop/switch isn't completed */
_L5:
                Slog.d("NsdService", "Stop service discovery");
                ClientInfo clientinfo3 = (ClientInfo)mClients.get(message.replyTo);
                int l;
                try {
                    l = ((Integer)clientinfo3.mClientIds.get(message.arg2)).intValue();
                }
                catch(NullPointerException nullpointerexception1) {
                    replyToMessage(message, 0x60007, 0);
                    continue; /* Loop/switch isn't completed */
                }
                removeRequestMap(message.arg2, l, clientinfo3);
                if(stopServiceDiscovery(l))
                    replyToMessage(message, 0x60008);
                else
                    replyToMessage(message, 0x60007, 0);
                continue; /* Loop/switch isn't completed */
_L6:
                Slog.d("NsdService", "Register service");
                ClientInfo clientinfo2 = (ClientInfo)mClients.get(message.replyTo);
                if(requestLimitReached(clientinfo2)) {
                    replyToMessage(message, 0x6000a, 4);
                } else {
                    int k = getUniqueId();
                    if(registerService(k, (NsdServiceInfo)message.obj)) {
                        Slog.d("NsdService", (new StringBuilder()).append("Register ").append(message.arg2).append(" ").append(k).toString());
                        storeRequestMap(message.arg2, k, clientinfo2);
                    } else {
                        unregisterService(k);
                        replyToMessage(message, 0x6000a, 0);
                    }
                }
                continue; /* Loop/switch isn't completed */
_L7:
                Slog.d("NsdService", "unregister service");
                ClientInfo clientinfo1 = (ClientInfo)mClients.get(message.replyTo);
                int j;
                try {
                    j = ((Integer)clientinfo1.mClientIds.get(message.arg2)).intValue();
                }
                catch(NullPointerException nullpointerexception) {
                    replyToMessage(message, 0x6000d, 0);
                    continue; /* Loop/switch isn't completed */
                }
                removeRequestMap(message.arg2, j, clientinfo1);
                if(unregisterService(j))
                    replyToMessage(message, 0x6000e);
                else
                    replyToMessage(message, 0x6000d, 0);
                continue; /* Loop/switch isn't completed */
_L8:
                Slog.d("NsdService", "Resolve service");
                NsdServiceInfo nsdserviceinfo = (NsdServiceInfo)message.obj;
                ClientInfo clientinfo = (ClientInfo)mClients.get(message.replyTo);
                if(clientinfo.mResolvedService != null) {
                    replyToMessage(message, 0x60013, 3);
                } else {
                    int i = getUniqueId();
                    if(resolveService(i, nsdserviceinfo)) {
                        clientinfo.mResolvedService = new NsdServiceInfo();
                        storeRequestMap(message.arg2, i, clientinfo);
                    } else {
                        replyToMessage(message, 0x60013, 0);
                    }
                }
                continue; /* Loop/switch isn't completed */
_L10:
                NativeEvent nativeevent = (NativeEvent)message.obj;
                handleNativeEvent(nativeevent.code, nativeevent.raw, NativeDaemonEvent.unescapeArgs(nativeevent.raw));
                if(true) goto _L12; else goto _L11
_L11:
            }

            final NsdStateMachine this$1;

            EnabledState() {
                this$1 = NsdStateMachine.this;
                super();
            }
        }

        class DisabledState extends State {

            public void enter() {
                sendNsdStateChangeBroadcast(false);
            }

            public boolean processMessage(Message message) {
                message.what;
                JVM INSTR tableswitch 393240 393240: default 24
            //                           393240 28;
                   goto _L1 _L2
_L1:
                boolean flag = false;
_L4:
                return flag;
_L2:
                transitionTo(mEnabledState);
                flag = true;
                if(true) goto _L4; else goto _L3
_L3:
            }

            final NsdStateMachine this$1;

            DisabledState() {
                this$1 = NsdStateMachine.this;
                super();
            }
        }

        class DefaultState extends State {

            public boolean processMessage(Message message) {
                boolean flag = false;
                message.what;
                JVM INSTR lookupswitch 8: default 80
            //                           69632: 107
            //                           69633: 285
            //                           69636: 217
            //                           393217: 319
            //                           393222: 336
            //                           393225: 353
            //                           393228: 370
            //                           393234: 387;
                   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L9:
                break MISSING_BLOCK_LABEL_387;
_L1:
                Slog.e("NsdService", (new StringBuilder()).append("Unhandled ").append(message).toString());
_L10:
                return flag;
_L2:
                if(message.arg1 == 0) {
                    AsyncChannel asyncchannel = (AsyncChannel)message.obj;
                    Slog.d("NsdService", "New client listening to asynchronous messages");
                    asyncchannel.sendMessage(0x11002);
                    ClientInfo clientinfo = new ClientInfo(asyncchannel, message.replyTo);
                    mClients.put(message.replyTo, clientinfo);
                } else {
                    Slog.e("NsdService", (new StringBuilder()).append("Client connection failure, error=").append(message.arg1).toString());
                }
_L11:
                flag = true;
                if(true) goto _L10; else goto _L4
_L4:
                if(message.arg1 == 2)
                    Slog.e("NsdService", "Send failed, client connection lost");
                else
                    Slog.d("NsdService", (new StringBuilder()).append("Client connection lost with reason: ").append(message.arg1).toString());
                mClients.remove(message.replyTo);
                  goto _L11
_L3:
                (new AsyncChannel()).connect(mContext, getHandler(), message.replyTo);
                  goto _L11
_L5:
                replyToMessage(message, 0x60003, 0);
                  goto _L11
_L6:
                replyToMessage(message, 0x60007, 0);
                  goto _L11
_L7:
                replyToMessage(message, 0x6000a, 0);
                  goto _L11
_L8:
                replyToMessage(message, 0x6000d, 0);
                  goto _L11
                replyToMessage(message, 0x60013, 0);
                  goto _L11
            }

            final NsdStateMachine this$1;

            DefaultState() {
                this$1 = NsdStateMachine.this;
                super();
            }
        }


        private void registerForNsdSetting() {
            ContentObserver contentobserver = new ContentObserver(getHandler()) {

                public void onChange(boolean flag) {
                    if(isNsdEnabled())
                        mNsdStateMachine.sendMessage(0x60018);
                    else
                        mNsdStateMachine.sendMessage(0x60019);
                }

                final NsdStateMachine this$1;

                 {
                    this$1 = NsdStateMachine.this;
                    super(handler);
                }
            };
            mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("nsd_on"), false, contentobserver);
        }

        protected String getMessageInfo(Message message) {
            return NsdService.cmdToString(message.what);
        }

        private final DefaultState mDefaultState = new DefaultState();
        private final DisabledState mDisabledState = new DisabledState();
        private final EnabledState mEnabledState = new EnabledState();
        final NsdService this$0;





        NsdStateMachine(String s) {
            this$0 = NsdService.this;
            super(s);
            addState(mDefaultState);
            addState(mDisabledState, mDefaultState);
            addState(mEnabledState, mDefaultState);
            if(isNsdEnabled())
                setInitialState(mEnabledState);
            else
                setInitialState(mDisabledState);
            setProcessedMessagesSize(25);
            registerForNsdSetting();
        }
    }


    private NsdService(Context context) {
        mClients = new HashMap();
        mIdToClientInfoMap = new SparseArray();
        mReplyChannel = new AsyncChannel();
        INVALID_ID = 0;
        mUniqueId = 1;
        mContext = context;
        mContentResolver = context.getContentResolver();
        mNativeConnector = new NativeDaemonConnector(new NativeCallbackReceiver(), "mdns", 10, "mDnsConnector", 25);
        mNsdStateMachine = new NsdStateMachine("NsdService");
        mNsdStateMachine.start();
        (new Thread(mNativeConnector, "mDnsConnector")).start();
    }

    private static String cmdToString(int i) {
        int j = i - 0x60000;
        String s;
        if(j >= 0 && j < sCmdToString.length)
            s = sCmdToString[j];
        else
            s = null;
        return s;
    }

    public static NsdService create(Context context) throws InterruptedException {
        NsdService nsdservice = new NsdService(context);
        nsdservice.mNativeDaemonConnected.await();
        return nsdservice;
    }

    private boolean discoverServices(int i, String s) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("discoverServices: ").append(i).append(" ").append(s).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "discover";
            aobj[1] = Integer.valueOf(i);
            aobj[2] = s;
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to discoverServices ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean getAddrInfo(int i, String s) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("getAdddrInfo: ").append(i).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "getaddrinfo";
            aobj[1] = Integer.valueOf(i);
            aobj[2] = s;
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to getAddrInfo ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private int getUniqueId() {
        int i = 1 + mUniqueId;
        mUniqueId = i;
        int j;
        if(i == INVALID_ID) {
            j = 1 + mUniqueId;
            mUniqueId = j;
        } else {
            j = mUniqueId;
        }
        return j;
    }

    private void handleNativeEvent(int i, String s, String as[]) {
        int j;
        ClientInfo clientinfo;
        j = Integer.parseInt(as[1]);
        clientinfo = (ClientInfo)mIdToClientInfoMap.get(j);
        if(clientinfo != null) goto _L2; else goto _L1
_L1:
        Slog.e("NsdService", (new StringBuilder()).append("Unique id with no client mapping: ").append(j).toString());
_L4:
        return;
_L2:
        int k = -1;
        int l = clientinfo.mClientIds.indexOfValue(Integer.valueOf(j));
        if(l != -1)
            k = clientinfo.mClientIds.keyAt(l);
        switch(i) {
        case 602: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_DISC_FAILED Raw: ").append(s).toString());
            clientinfo.mChannel.sendMessage(0x60003, 0, k);
            break;

        case 603: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_FOUND Raw: ").append(s).toString());
            NsdServiceInfo nsdserviceinfo2 = new NsdServiceInfo(as[2], as[3], null);
            clientinfo.mChannel.sendMessage(0x60004, 0, k, nsdserviceinfo2);
            break;

        case 604: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_LOST Raw: ").append(s).toString());
            NsdServiceInfo nsdserviceinfo1 = new NsdServiceInfo(as[2], as[3], null);
            clientinfo.mChannel.sendMessage(0x60005, 0, k, nsdserviceinfo1);
            break;

        case 606: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_REGISTERED Raw: ").append(s).toString());
            NsdServiceInfo nsdserviceinfo = new NsdServiceInfo(as[2], null, null);
            clientinfo.mChannel.sendMessage(0x6000b, j, k, nsdserviceinfo);
            break;

        case 605: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_REGISTER_FAILED Raw: ").append(s).toString());
            clientinfo.mChannel.sendMessage(0x6000a, 0, k);
            break;

        case 608: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_RESOLVED Raw: ").append(s).toString());
            int i1 = as[2].indexOf(".");
            if(i1 == -1) {
                Slog.e("NsdService", (new StringBuilder()).append("Invalid service found ").append(s).toString());
            } else {
                String s1 = as[2].substring(0, i1);
                String s2 = as[2].substring(i1).replace(".local.", "");
                clientinfo.mResolvedService.setServiceName(s1);
                clientinfo.mResolvedService.setServiceType(s2);
                clientinfo.mResolvedService.setPort(Integer.parseInt(as[4]));
                stopResolveService(j);
                if(!getAddrInfo(j, as[3])) {
                    clientinfo.mChannel.sendMessage(0x60013, 0, k);
                    mIdToClientInfoMap.remove(j);
                    clientinfo.mResolvedService = null;
                }
            }
            break;

        case 607: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_RESOLVE_FAILED Raw: ").append(s).toString());
            stopResolveService(j);
            mIdToClientInfoMap.remove(j);
            clientinfo.mResolvedService = null;
            clientinfo.mChannel.sendMessage(0x60013, 0, k);
            break;

        case 611: 
            stopGetAddrInfo(j);
            mIdToClientInfoMap.remove(j);
            clientinfo.mResolvedService = null;
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_RESOLVE_FAILED Raw: ").append(s).toString());
            clientinfo.mChannel.sendMessage(0x60013, 0, k);
            break;

        case 612: 
            Slog.d("NsdService", (new StringBuilder()).append("SERVICE_GET_ADDR_SUCCESS Raw: ").append(s).toString());
            try {
                clientinfo.mResolvedService.setHost(InetAddress.getByName(as[4]));
                clientinfo.mChannel.sendMessage(0x60014, 0, k, clientinfo.mResolvedService);
            }
            catch(UnknownHostException unknownhostexception) {
                clientinfo.mChannel.sendMessage(0x60013, 0, k);
            }
            stopGetAddrInfo(j);
            mIdToClientInfoMap.remove(j);
            clientinfo.mResolvedService = null;
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean isNsdEnabled() {
        boolean flag = true;
        if(android.provider.Settings.Secure.getInt(mContentResolver, "nsd_on", flag) != flag)
            flag = false;
        Slog.d("NsdService", (new StringBuilder()).append("Network service discovery enabled ").append(flag).toString());
        return flag;
    }

    private Message obtainMessage(Message message) {
        Message message1 = Message.obtain();
        message1.arg2 = message.arg2;
        return message1;
    }

    private boolean registerService(int i, NsdServiceInfo nsdserviceinfo) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("registerService: ").append(i).append(" ").append(nsdserviceinfo).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[5];
            aobj[0] = "register";
            aobj[1] = Integer.valueOf(i);
            aobj[2] = nsdserviceinfo.getServiceName();
            aobj[3] = nsdserviceinfo.getServiceType();
            aobj[4] = Integer.valueOf(nsdserviceinfo.getPort());
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to execute registerService ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private void replyToMessage(Message message, int i) {
        if(message.replyTo != null) {
            Message message1 = obtainMessage(message);
            message1.what = i;
            mReplyChannel.replyToMessage(message, message1);
        }
    }

    private void replyToMessage(Message message, int i, int j) {
        if(message.replyTo != null) {
            Message message1 = obtainMessage(message);
            message1.what = i;
            message1.arg1 = j;
            mReplyChannel.replyToMessage(message, message1);
        }
    }

    private void replyToMessage(Message message, int i, Object obj) {
        if(message.replyTo != null) {
            Message message1 = obtainMessage(message);
            message1.what = i;
            message1.obj = obj;
            mReplyChannel.replyToMessage(message, message1);
        }
    }

    private boolean resolveService(int i, NsdServiceInfo nsdserviceinfo) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("resolveService: ").append(i).append(" ").append(nsdserviceinfo).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[5];
            aobj[0] = "resolve";
            aobj[1] = Integer.valueOf(i);
            aobj[2] = nsdserviceinfo.getServiceName();
            aobj[3] = nsdserviceinfo.getServiceType();
            aobj[4] = "local.";
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to resolveService ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private void sendNsdStateChangeBroadcast(boolean flag) {
        Intent intent = new Intent("android.net.nsd.STATE_CHANGED");
        intent.addFlags(0x8000000);
        if(flag)
            intent.putExtra("nsd_state", 2);
        else
            intent.putExtra("nsd_state", 1);
        mContext.sendStickyBroadcast(intent);
    }

    private boolean startMDnsDaemon() {
        boolean flag = true;
        Slog.d("NsdService", "startMDnsDaemon");
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "start-service";
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to start daemon").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean stopGetAddrInfo(int i) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("stopGetAdddrInfo: ").append(i).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "stop-getaddrinfo";
            aobj[1] = Integer.valueOf(i);
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to stopGetAddrInfo ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean stopMDnsDaemon() {
        boolean flag = true;
        Slog.d("NsdService", "stopMDnsDaemon");
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[1];
            aobj[0] = "stop-service";
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to start daemon").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean stopResolveService(int i) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("stopResolveService: ").append(i).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "stop-resolve";
            aobj[1] = Integer.valueOf(i);
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to stop resolve ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean stopServiceDiscovery(int i) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("stopServiceDiscovery: ").append(i).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "stop-discover";
            aobj[1] = Integer.valueOf(i);
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to stopServiceDiscovery ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean unregisterService(int i) {
        boolean flag = true;
        Slog.d("NsdService", (new StringBuilder()).append("unregisterService: ").append(i).toString());
        try {
            NativeDaemonConnector nativedaemonconnector = mNativeConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "stop-register";
            aobj[1] = Integer.valueOf(i);
            nativedaemonconnector.execute("mdnssd", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("NsdService", (new StringBuilder()).append("Failed to execute unregisterService ").append(nativedaemonconnectorexception).toString());
            flag = false;
        }
        return flag;
    }

    private boolean updateService(int i, DnsSdTxtRecord dnssdtxtrecord) {
        boolean flag;
        flag = false;
        Slog.d("NsdService", (new StringBuilder()).append("updateService: ").append(i).append(" ").append(dnssdtxtrecord).toString());
        if(dnssdtxtrecord != null) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        NativeDaemonConnector nativedaemonconnector = mNativeConnector;
        Object aobj[] = new Object[4];
        aobj[0] = "update";
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(dnssdtxtrecord.size());
        aobj[3] = dnssdtxtrecord.getRawData();
        nativedaemonconnector.execute("mdnssd", aobj);
        flag = true;
        continue; /* Loop/switch isn't completed */
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        Slog.e("NsdService", (new StringBuilder()).append("Failed to updateServices ").append(nativedaemonconnectorexception).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ServiceDiscoverService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
        } else {
            ClientInfo clientinfo;
            for(Iterator iterator = mClients.values().iterator(); iterator.hasNext(); printwriter.println(clientinfo)) {
                clientinfo = (ClientInfo)iterator.next();
                printwriter.println("Client Info");
            }

            mNsdStateMachine.dump(filedescriptor, printwriter, as);
        }
    }

    public Messenger getMessenger() {
        mContext.enforceCallingOrSelfPermission("android.permission.INTERNET", "NsdService");
        return new Messenger(mNsdStateMachine.getHandler());
    }

    public void setEnabled(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NsdService");
        ContentResolver contentresolver = mContentResolver;
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        android.provider.Settings.Secure.putInt(contentresolver, "nsd_on", i);
        if(flag)
            mNsdStateMachine.sendMessage(0x60018);
        else
            mNsdStateMachine.sendMessage(0x60019);
    }

    private static final int BASE = 0x60000;
    private static final int CMD_TO_STRING_COUNT = 19;
    private static final boolean DBG = true;
    private static final String MDNS_TAG = "mDnsConnector";
    private static final String TAG = "NsdService";
    private static String sCmdToString[];
    private int INVALID_ID;
    private HashMap mClients;
    private ContentResolver mContentResolver;
    private Context mContext;
    private SparseArray mIdToClientInfoMap;
    private NativeDaemonConnector mNativeConnector;
    private final CountDownLatch mNativeDaemonConnected = new CountDownLatch(1);
    private NsdStateMachine mNsdStateMachine;
    private AsyncChannel mReplyChannel;
    private int mUniqueId;

    static  {
        sCmdToString = new String[19];
        sCmdToString[1] = "DISCOVER";
        sCmdToString[6] = "STOP-DISCOVER";
        sCmdToString[9] = "REGISTER";
        sCmdToString[12] = "UNREGISTER";
        sCmdToString[18] = "RESOLVE";
    }




















}
