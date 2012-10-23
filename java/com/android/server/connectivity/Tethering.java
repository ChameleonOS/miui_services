// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.connectivity;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.hardware.usb.UsbManager;
import android.net.*;
import android.os.*;
import android.util.Log;
import com.android.internal.util.*;
import com.google.android.collect.Lists;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;

public class Tethering extends android.net.INetworkManagementEventObserver.Stub {
    class TetherMasterSM extends StateMachine {
        class SetDnsForwardersErrorState extends ErrorState {

            public void enter() {
                Log.e("Tethering", "Error in setDnsForwarders");
                notify(11);
                Exception exception1;
                try {
                    mNMService.stopTethering();
                }
                catch(Exception exception) { }
                mNMService.setIpForwardingEnabled(false);
_L2:
                return;
                exception1;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final TetherMasterSM this$1;

            SetDnsForwardersErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class StopTetheringErrorState extends ErrorState {

            public void enter() {
                Log.e("Tethering", "Error in stopTethering");
                notify(10);
                mNMService.setIpForwardingEnabled(false);
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final TetherMasterSM this$1;

            StopTetheringErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class StartTetheringErrorState extends ErrorState {

            public void enter() {
                Log.e("Tethering", "Error in startTethering");
                notify(9);
                mNMService.setIpForwardingEnabled(false);
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final TetherMasterSM this$1;

            StartTetheringErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class SetIpForwardingDisabledErrorState extends ErrorState {

            public void enter() {
                Log.e("Tethering", "Error in setIpForwardingDisabled");
                notify(8);
            }

            final TetherMasterSM this$1;

            SetIpForwardingDisabledErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class SetIpForwardingEnabledErrorState extends ErrorState {

            public void enter() {
                Log.e("Tethering", "Error in setIpForwardingEnabled");
                notify(7);
            }

            final TetherMasterSM this$1;

            SetIpForwardingEnabledErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class ErrorState extends State {

            void notify(int i) {
                mErrorNotification = i;
                for(Iterator iterator = mNotifyList.iterator(); iterator.hasNext(); ((TetherInterfaceSM)iterator.next()).sendMessage(i));
            }

            public boolean processMessage(Message message) {
                boolean flag = true;
                message.what;
                JVM INSTR tableswitch 1 1: default 24
            //                           1 28;
                   goto _L1 _L2
_L1:
                flag = false;
_L4:
                return flag;
_L2:
                ((TetherInterfaceSM)message.obj).sendMessage(mErrorNotification);
                if(true) goto _L4; else goto _L3
_L3:
            }

            int mErrorNotification;
            final TetherMasterSM this$1;

            ErrorState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class TetherModeAliveState extends TetherMasterUtilState {

            public void enter() {
                boolean flag = true;
                turnOnMasterTetherSettings();
                mTryCell = flag;
                chooseUpstreamType(mTryCell);
                if(mTryCell)
                    flag = false;
                mTryCell = flag;
            }

            public void exit() {
                turnOffUpstreamMobileConnection();
                notifyTetheredOfNewUpstreamIface(null);
            }

            public boolean processMessage(Message message) {
                boolean flag;
                boolean flag1;
                flag = true;
                Log.d("Tethering", (new StringBuilder()).append("TetherModeAliveState.processMessage what=").append(message.what).toString());
                flag1 = true;
                message.what;
                JVM INSTR tableswitch 1 5: default 72
            //                           1 78
            //                           2 117
            //                           3 181
            //                           4 214
            //                           5 243;
                   goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
                flag1 = false;
_L8:
                return flag1;
_L2:
                TetherInterfaceSM tetherinterfacesm1 = (TetherInterfaceSM)message.obj;
                mNotifyList.add(tetherinterfacesm1);
                tetherinterfacesm1.sendMessage(12, mUpstreamIfaceName);
                continue; /* Loop/switch isn't completed */
_L3:
                TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)message.obj;
                int i = mNotifyList.indexOf(tetherinterfacesm);
                if(i != -1) {
                    mNotifyList.remove(i);
                    if(mNotifyList.isEmpty())
                        turnOffMasterTetherSettings();
                }
                continue; /* Loop/switch isn't completed */
_L4:
                mTryCell = flag;
                chooseUpstreamType(mTryCell);
                if(mTryCell)
                    flag = false;
                mTryCell = flag;
                continue; /* Loop/switch isn't completed */
_L5:
                if(mCurrentConnectionSequence == message.arg1)
                    turnOnUpstreamMobileConnection(mMobileApnReserved);
                continue; /* Loop/switch isn't completed */
_L6:
                chooseUpstreamType(mTryCell);
                if(mTryCell)
                    flag = false;
                mTryCell = flag;
                if(true) goto _L8; else goto _L7
_L7:
            }

            boolean mTryCell;
            final TetherMasterSM this$1;

            TetherModeAliveState() {
                this$1 = TetherMasterSM.this;
                super();
                mTryCell = true;
            }
        }

        class InitialState extends TetherMasterUtilState {

            public void enter() {
            }

            public boolean processMessage(Message message) {
                boolean flag;
                Log.d("Tethering", (new StringBuilder()).append("MasterInitialState.processMessage what=").append(message.what).toString());
                flag = true;
                message.what;
                JVM INSTR tableswitch 1 2: default 56
            //                           1 60
            //                           2 99;
                   goto _L1 _L2 _L3
_L1:
                flag = false;
_L5:
                return flag;
_L2:
                TetherInterfaceSM tetherinterfacesm1 = (TetherInterfaceSM)message.obj;
                mNotifyList.add(tetherinterfacesm1);
                transitionTo(mTetherModeAliveState);
                continue; /* Loop/switch isn't completed */
_L3:
                TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)message.obj;
                if(mNotifyList.indexOf(tetherinterfacesm) != -1)
                    mNotifyList.remove(tetherinterfacesm);
                if(true) goto _L5; else goto _L4
_L4:
            }

            final TetherMasterSM this$1;

            InitialState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }

        class TetherMasterUtilState extends State {

            protected void chooseUpstreamType(boolean flag) {
                int i;
                String s;
                i = -1;
                s = null;
                updateConfiguration();
                Object obj = mPublicSync;
                obj;
                JVM INSTR monitorenter ;
                Iterator iterator = mUpstreamIfaceTypes.iterator();
_L4:
                if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
                Integer integer = (Integer)iterator.next();
                NetworkInfo networkinfo = null;
                NetworkInfo networkinfo1 = mConnService.getNetworkInfo(integer.intValue());
                networkinfo = networkinfo1;
_L11:
                if(networkinfo == null) goto _L4; else goto _L3
_L3:
                if(!networkinfo.isConnected()) goto _L4; else goto _L5
_L5:
                i = integer.intValue();
_L2:
                obj;
                JVM INSTR monitorexit ;
                Log.d("Tethering", (new StringBuilder()).append("chooseUpstreamType(").append(flag).append("), preferredApn =").append(mPreferredUpstreamMobileApn).append(", got type=").append(i).toString());
                Exception exception;
                boolean flag1;
                if(i == 4 || i == 5)
                    turnOnUpstreamMobileConnection(i);
                else
                if(i != -1)
                    turnOffUpstreamMobileConnection();
                if(i != -1) goto _L7; else goto _L6
_L6:
                flag1 = true;
                if(flag && turnOnUpstreamMobileConnection(mPreferredUpstreamMobileApn))
                    flag1 = false;
                if(flag1)
                    sendMessageDelayed(5, 10000L);
_L9:
                notifyTetheredOfNewUpstreamIface(s);
                return;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
_L7:
                LinkProperties linkproperties = null;
                LinkProperties linkproperties1 = mConnService.getLinkProperties(i);
                linkproperties = linkproperties1;
_L10:
                if(linkproperties != null) {
                    s = linkproperties.getInterfaceName();
                    String as[] = mDefaultDnsServers;
                    Collection collection = linkproperties.getDnses();
                    if(collection != null) {
                        ArrayList arraylist = new ArrayList(collection.size());
                        Iterator iterator1 = collection.iterator();
                        do {
                            if(!iterator1.hasNext())
                                break;
                            InetAddress inetaddress = (InetAddress)iterator1.next();
                            if(inetaddress instanceof Inet4Address)
                                arraylist.add(inetaddress);
                        } while(true);
                        if(arraylist.size() > 0)
                            as = NetworkUtils.makeStrings(arraylist);
                    }
                    try {
                        mNMService.setDnsForwarders(as);
                    }
                    catch(Exception exception1) {
                        transitionTo(mSetDnsForwardersErrorState);
                    }
                }
                if(true) goto _L9; else goto _L8
_L8:
                RemoteException remoteexception;
                remoteexception;
                  goto _L10
                RemoteException remoteexception1;
                remoteexception1;
                  goto _L11
            }

            protected String enableString(int i) {
                i;
                JVM INSTR tableswitch 0 5: default 40
            //                           0 50
            //                           1 40
            //                           2 40
            //                           3 40
            //                           4 44
            //                           5 50;
                   goto _L1 _L2 _L1 _L1 _L1 _L3 _L2
_L1:
                String s = null;
_L5:
                return s;
_L3:
                s = "enableDUNAlways";
                continue; /* Loop/switch isn't completed */
_L2:
                s = "enableHIPRI";
                if(true) goto _L5; else goto _L4
_L4:
            }

            protected void notifyTetheredOfNewUpstreamIface(String s) {
                Log.d("Tethering", (new StringBuilder()).append("notifying tethered with iface =").append(s).toString());
                mUpstreamIfaceName = s;
                for(Iterator iterator = mNotifyList.iterator(); iterator.hasNext(); ((TetherInterfaceSM)iterator.next()).sendMessage(12, s));
            }

            public boolean processMessage(Message message) {
                return false;
            }

            protected boolean turnOffMasterTetherSettings() {
                boolean flag = false;
                try {
                    mNMService.stopTethering();
                }
                catch(Exception exception) {
                    transitionTo(mStopTetheringErrorState);
                    continue; /* Loop/switch isn't completed */
                }
                mNMService.setIpForwardingEnabled(false);
                transitionTo(mInitialState);
                flag = true;
_L2:
                return flag;
                Exception exception1;
                exception1;
                transitionTo(mSetIpForwardingDisabledErrorState);
                if(true) goto _L2; else goto _L1
_L1:
            }

            protected boolean turnOffUpstreamMobileConnection() {
                boolean flag;
                flag = false;
                int i = 1 + 
// JavaClassFileOutputException: get_constant: invalid tag

            protected boolean turnOnMasterTetherSettings() {
                boolean flag = false;
                mNMService.setIpForwardingEnabled(true);
                mNMService.startTethering(mDhcpRange);
_L2:
                mNMService.setDnsForwarders(mDefaultDnsServers);
                flag = true;
_L1:
                return flag;
                Exception exception;
                exception;
                transitionTo(mSetIpForwardingEnabledErrorState);
                  goto _L1
                Exception exception1;
                exception1;
                mNMService.stopTethering();
                mNMService.startTethering(mDhcpRange);
                  goto _L2
                Exception exception2;
                exception2;
                transitionTo(mStartTetheringErrorState);
                  goto _L1
                Exception exception3;
                exception3;
                transitionTo(mSetDnsForwardersErrorState);
                  goto _L1
            }

            protected boolean turnOnUpstreamMobileConnection(int i) {
                boolean flag;
                boolean flag1;
                flag = false;
                flag1 = true;
                if(i != -1) goto _L2; else goto _L1
_L1:
                return flag;
_L2:
                int j;
                String s;
                if(i != mMobileApnReserved)
                    turnOffUpstreamMobileConnection();
                j = 3;
                s = enableString(i);
                if(s == null) goto _L1; else goto _L3
_L3:
                int k = mConnService.startUsingNetworkFeature(0, s, new Binder());
                j = k;
_L5:
                switch(j) {
                default:
                    flag1 = false;
                    break;

                case 0: // '\0'
                case 1: // '\001'
                    break MISSING_BLOCK_LABEL_107;
                }
_L4:
                flag = flag1;
                  goto _L1
                mMobileApnReserved = i;
                Message message = obtainMessage(4);
                message.arg1 = int i = 1 + 
// JavaClassFileOutputException: get_constant: invalid tag

            protected static final boolean TRY_TO_SETUP_MOBILE_CONNECTION = true;
            protected static final boolean WAIT_FOR_NETWORK_TO_SETTLE;
            final TetherMasterSM this$1;

            TetherMasterUtilState() {
                this$1 = TetherMasterSM.this;
                super();
            }
        }


        private static final int CELL_CONNECTION_RENEW_MS = 40000;
        static final int CMD_CELL_CONNECTION_RENEW = 4;
        static final int CMD_RETRY_UPSTREAM = 5;
        static final int CMD_TETHER_MODE_REQUESTED = 1;
        static final int CMD_TETHER_MODE_UNREQUESTED = 2;
        static final int CMD_UPSTREAM_CHANGED = 3;
        private static final int UPSTREAM_SETTLE_TIME_MS = 10000;
        private int mCurrentConnectionSequence;
        private State mInitialState;
        private int mMobileApnReserved;
        private ArrayList mNotifyList;
        private int mSequenceNumber;
        private State mSetDnsForwardersErrorState;
        private State mSetIpForwardingDisabledErrorState;
        private State mSetIpForwardingEnabledErrorState;
        private State mStartTetheringErrorState;
        private State mStopTetheringErrorState;
        private State mTetherModeAliveState;
        private String mUpstreamIfaceName;
        final Tethering this$0;



/*
        static int access$2902(TetherMasterSM tethermastersm, int i) {
            tethermastersm.mMobileApnReserved = i;
            return i;
        }

*/



/*
        static int access$3104(TetherMasterSM tethermastersm) {
            int i = 1 + tethermastersm.mCurrentConnectionSequence;
            tethermastersm.mCurrentConnectionSequence = i;
            return i;
        }

*/
















/*
        static String access$4902(TetherMasterSM tethermastersm, String s) {
            tethermastersm.mUpstreamIfaceName = s;
            return s;
        }

*/




        TetherMasterSM(String s, Looper looper) {
            this$0 = Tethering.this;
            super(s, looper);
            mMobileApnReserved = -1;
            mUpstreamIfaceName = null;
            mInitialState = new InitialState();
            addState(mInitialState);
            mTetherModeAliveState = new TetherModeAliveState();
            addState(mTetherModeAliveState);
            mSetIpForwardingEnabledErrorState = new SetIpForwardingEnabledErrorState();
            addState(mSetIpForwardingEnabledErrorState);
            mSetIpForwardingDisabledErrorState = new SetIpForwardingDisabledErrorState();
            addState(mSetIpForwardingDisabledErrorState);
            mStartTetheringErrorState = new StartTetheringErrorState();
            addState(mStartTetheringErrorState);
            mStopTetheringErrorState = new StopTetheringErrorState();
            addState(mStopTetheringErrorState);
            mSetDnsForwardersErrorState = new SetDnsForwardersErrorState();
            addState(mSetDnsForwardersErrorState);
            mNotifyList = new ArrayList();
            setInitialState(mInitialState);
        }
    }

    class TetherInterfaceSM extends StateMachine {
        class UnavailableState extends State {

            public void enter() {
                setAvailable(false);
                setLastError(0);
                setTethered(false);
                sendTetherStateChangedBroadcast();
            }

            public boolean processMessage(Message message) {
                boolean flag = true;
                message.what;
                JVM INSTR tableswitch 5 5: default 24
            //                           5 28;
                   goto _L1 _L2
_L1:
                flag = false;
_L4:
                return flag;
_L2:
                transitionTo(mInitialState);
                if(true) goto _L4; else goto _L3
_L3:
            }

            final TetherInterfaceSM this$1;

            UnavailableState() {
                this$1 = TetherInterfaceSM.this;
                super();
            }
        }

        class TetheredState extends State {

            private void cleanupUpstream() {
                if(mMyUpstreamIfaceName != null) {
                    try {
                        mStatsService.forceUpdate();
                    }
                    catch(Exception exception) { }
                    try {
                        mNMService.disableNat(mIfaceName, mMyUpstreamIfaceName);
                    }
                    catch(Exception exception1) { }
                    mMyUpstreamIfaceName = null;
                }
            }

            public void enter() {
                mNMService.tetherInterface(mIfaceName);
                Log.d("Tethering", (new StringBuilder()).append("Tethered ").append(mIfaceName).toString());
                setAvailable(false);
                setTethered(true);
                sendTetherStateChangedBroadcast();
_L2:
                return;
                Exception exception;
                exception;
                Log.e("Tethering", (new StringBuilder()).append("Error Tethering: ").append(exception.toString()).toString());
                setLastError(6);
                transitionTo(mInitialState);
                if(true) goto _L2; else goto _L1
_L1:
            }

            public boolean processMessage(Message message) {
                boolean flag;
                boolean flag1;
                Log.d("Tethering", (new StringBuilder()).append("TetheredState.processMessage what=").append(message.what).toString());
                flag = true;
                flag1 = false;
                message.what;
                JVM INSTR tableswitch 1 12: default 100
            //                           1 462
            //                           2 100
            //                           3 108
            //                           4 108
            //                           5 100
            //                           6 459
            //                           7 459
            //                           8 459
            //                           9 459
            //                           10 459
            //                           11 459
            //                           12 280;
                   goto _L1 _L2 _L1 _L3 _L3 _L1 _L4 _L4 _L4 _L4 _L4 _L4 _L5
_L1:
                flag = false;
_L10:
                boolean flag2 = flag;
_L8:
                return flag2;
_L3:
                cleanupUpstream();
                try {
                    mNMService.untetherInterface(mIfaceName);
                }
                catch(Exception exception3) {
                    setLastErrorAndTransitionToInitialState(7);
                    continue; /* Loop/switch isn't completed */
                }
                mTetherMasterSM.sendMessage(2, TetherInterfaceSM.this);
                if(message.what == 3) {
                    if(mUsb && !configureUsbIface(false))
                        setLastError(10);
                    transitionTo(mInitialState);
                } else
                if(message.what == 4)
                    transitionTo(mUnavailableState);
                Log.d("Tethering", (new StringBuilder()).append("Untethered ").append(mIfaceName).toString());
                continue; /* Loop/switch isn't completed */
_L5:
                String s;
                s = (String)(String)message.obj;
                if(mMyUpstreamIfaceName == null && s == null || mMyUpstreamIfaceName != null && mMyUpstreamIfaceName.equals(s))
                    continue; /* Loop/switch isn't completed */
                cleanupUpstream();
                if(s == null) goto _L7; else goto _L6
_L6:
                mNMService.enableNat(mIfaceName, s);
_L7:
                mMyUpstreamIfaceName = s;
                continue; /* Loop/switch isn't completed */
                Exception exception1;
                exception1;
                Log.e("Tethering", (new StringBuilder()).append("Exception enabling Nat: ").append(exception1.toString()).toString());
                Exception exception;
                try {
                    mNMService.untetherInterface(mIfaceName);
                }
                catch(Exception exception2) { }
                setLastError(8);
                transitionTo(mInitialState);
                flag2 = true;
                  goto _L8
_L4:
                flag1 = true;
_L2:
                cleanupUpstream();
                mNMService.untetherInterface(mIfaceName);
                if(flag1) {
                    setLastErrorAndTransitionToInitialState(5);
                } else {
                    Log.d("Tethering", (new StringBuilder()).append("Tether lost upstream connection ").append(mIfaceName).toString());
                    sendTetherStateChangedBroadcast();
                    if(mUsb && !configureUsbIface(false))
                        setLastError(10);
                    transitionTo(mInitialState);
                }
                continue; /* Loop/switch isn't completed */
                exception;
                setLastErrorAndTransitionToInitialState(7);
                if(true) goto _L10; else goto _L9
_L9:
            }

            final TetherInterfaceSM this$1;

            TetheredState() {
                this$1 = TetherInterfaceSM.this;
                super();
            }
        }

        class StartingState extends State {

            public void enter() {
                setAvailable(false);
                if(mUsb && !configureUsbIface(true)) {
                    mTetherMasterSM.sendMessage(2, TetherInterfaceSM.this);
                    setLastError(10);
                    transitionTo(mInitialState);
                } else {
                    sendTetherStateChangedBroadcast();
                    transitionTo(mTetheredState);
                }
            }

            public boolean processMessage(Message message) {
                boolean flag;
                Log.d("Tethering", (new StringBuilder()).append("StartingState.processMessage what=").append(message.what).toString());
                flag = true;
                message.what;
                JVM INSTR tableswitch 3 11: default 84
            //                           3 88
            //                           4 170
            //                           5 84
            //                           6 159
            //                           7 159
            //                           8 159
            //                           9 159
            //                           10 159
            //                           11 159;
                   goto _L1 _L2 _L3 _L1 _L4 _L4 _L4 _L4 _L4 _L4
_L1:
                flag = false;
_L6:
                return flag;
_L2:
                mTetherMasterSM.sendMessage(2, TetherInterfaceSM.this);
                if(mUsb && !configureUsbIface(false))
                    setLastErrorAndTransitionToInitialState(10);
                else
                    transitionTo(mInitialState);
                continue; /* Loop/switch isn't completed */
_L4:
                setLastErrorAndTransitionToInitialState(5);
                continue; /* Loop/switch isn't completed */
_L3:
                mTetherMasterSM.sendMessage(2, TetherInterfaceSM.this);
                transitionTo(mUnavailableState);
                if(true) goto _L6; else goto _L5
_L5:
            }

            final TetherInterfaceSM this$1;

            StartingState() {
                this$1 = TetherInterfaceSM.this;
                super();
            }
        }

        class InitialState extends State {

            public void enter() {
                setAvailable(true);
                setTethered(false);
                sendTetherStateChangedBroadcast();
            }

            public boolean processMessage(Message message) {
                boolean flag;
                Log.d("Tethering", (new StringBuilder()).append("InitialState.processMessage what=").append(message.what).toString());
                flag = true;
                message.what;
                JVM INSTR tableswitch 2 4: default 60
            //                           2 64
            //                           3 60
            //                           4 107;
                   goto _L1 _L2 _L1 _L3
_L1:
                flag = false;
_L5:
                return flag;
_L2:
                setLastError(0);
                mTetherMasterSM.sendMessage(1, TetherInterfaceSM.this);
                transitionTo(mStartingState);
                continue; /* Loop/switch isn't completed */
_L3:
                transitionTo(mUnavailableState);
                if(true) goto _L5; else goto _L4
_L4:
            }

            final TetherInterfaceSM this$1;

            InitialState() {
                this$1 = TetherInterfaceSM.this;
                super();
            }
        }


        private void setAvailable(boolean flag) {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            mAvailable = flag;
            return;
        }

        private void setLastError(int i) {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            mLastError = i;
            if(isErrored() && mUsb)
                configureUsbIface(false);
            return;
        }

        private void setTethered(boolean flag) {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            mTethered = flag;
            return;
        }

        public int getLastError() {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            int i = mLastError;
            return i;
        }

        public boolean isAvailable() {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            boolean flag = mAvailable;
            return flag;
        }

        public boolean isErrored() {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            boolean flag;
            if(mLastError != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isTethered() {
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            boolean flag = mTethered;
            return flag;
        }

        void setLastErrorAndTransitionToInitialState(int i) {
            setLastError(i);
            transitionTo(mInitialState);
        }

        public String toString() {
            String s = new String();
            String s1 = (new StringBuilder()).append(s).append(mIfaceName).append(" - ").toString();
            IState istate = getCurrentState();
            if(istate == mInitialState)
                s1 = (new StringBuilder()).append(s1).append("InitialState").toString();
            if(istate == mStartingState)
                s1 = (new StringBuilder()).append(s1).append("StartingState").toString();
            if(istate == mTetheredState)
                s1 = (new StringBuilder()).append(s1).append("TetheredState").toString();
            if(istate == mUnavailableState)
                s1 = (new StringBuilder()).append(s1).append("UnavailableState").toString();
            if(mAvailable)
                s1 = (new StringBuilder()).append(s1).append(" - Available").toString();
            if(mTethered)
                s1 = (new StringBuilder()).append(s1).append(" - Tethered").toString();
            return (new StringBuilder()).append(s1).append(" - lastError =").append(mLastError).toString();
        }

        static final int CMD_CELL_DUN_ERROR = 6;
        static final int CMD_INTERFACE_DOWN = 4;
        static final int CMD_INTERFACE_UP = 5;
        static final int CMD_IP_FORWARDING_DISABLE_ERROR = 8;
        static final int CMD_IP_FORWARDING_ENABLE_ERROR = 7;
        static final int CMD_SET_DNS_FORWARDERS_ERROR = 11;
        static final int CMD_START_TETHERING_ERROR = 9;
        static final int CMD_STOP_TETHERING_ERROR = 10;
        static final int CMD_TETHER_CONNECTION_CHANGED = 12;
        static final int CMD_TETHER_MODE_DEAD = 1;
        static final int CMD_TETHER_REQUESTED = 2;
        static final int CMD_TETHER_UNREQUESTED = 3;
        private boolean mAvailable;
        private State mDefaultState;
        String mIfaceName;
        private State mInitialState;
        int mLastError;
        String mMyUpstreamIfaceName;
        private State mStartingState;
        private boolean mTethered;
        private State mTetheredState;
        private State mUnavailableState;
        boolean mUsb;
        final Tethering this$0;




















        TetherInterfaceSM(String s, Looper looper, boolean flag) {
            this$0 = Tethering.this;
            super(s, looper);
            mIfaceName = s;
            mUsb = flag;
            setLastError(0);
            mInitialState = new InitialState();
            addState(mInitialState);
            mStartingState = new StartingState();
            addState(mStartingState);
            mTetheredState = new TetheredState();
            addState(mTetheredState);
            mUnavailableState = new UnavailableState();
            addState(mUnavailableState);
            setInitialState(mInitialState);
        }
    }

    private class StateReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s;
            s = intent.getAction();
            if(!s.equals("android.hardware.usb.action.USB_STATE"))
                break MISSING_BLOCK_LABEL_106;
            Object obj = mPublicSync;
            obj;
            JVM INSTR monitorenter ;
            boolean flag = intent.getBooleanExtra("connected", false);
            mRndisEnabled = intent.getBooleanExtra("rndis", false);
            if(flag && mRndisEnabled && mUsbTetherRequested)
                tetherUsb(true);
            mUsbTetherRequested = false;
            break MISSING_BLOCK_LABEL_126;
            if(s.equals("android.net.conn.CONNECTIVITY_CHANGE"))
                mTetherMasterSM.sendMessage(3);
        }

        final Tethering this$0;

        private StateReceiver() {
            this$0 = Tethering.this;
            super();
        }

    }


    public Tethering(Context context, INetworkManagementService inetworkmanagementservice, INetworkStatsService inetworkstatsservice, IConnectivityManager iconnectivitymanager, Looper looper) {
        mPreferredUpstreamMobileApn = -1;
        mContext = context;
        mNMService = inetworkmanagementservice;
        mStatsService = inetworkstatsservice;
        mConnService = iconnectivitymanager;
        mLooper = looper;
        mPublicSync = new Object();
        mIfaces = new HashMap();
        mThread = new HandlerThread("Tethering");
        mThread.start();
        mLooper = mThread.getLooper();
        mTetherMasterSM = new TetherMasterSM("TetherMaster", mLooper);
        mTetherMasterSM.start();
        mStateReceiver = new StateReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.hardware.usb.action.USB_STATE");
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mStateReceiver, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.MEDIA_SHARED");
        intentfilter1.addAction("android.intent.action.MEDIA_UNSHARED");
        intentfilter1.addDataScheme("file");
        mContext.registerReceiver(mStateReceiver, intentfilter1);
        mDhcpRange = context.getResources().getStringArray(0x107001b);
        if(mDhcpRange.length == 0 || mDhcpRange.length % 2 == 1)
            mDhcpRange = DHCP_DEFAULT_RANGE;
        updateConfiguration();
        mDefaultDnsServers = new String[2];
        mDefaultDnsServers[0] = "8.8.8.8";
        mDefaultDnsServers[1] = "8.8.4.4";
    }

    private void clearTetheredNotification() {
        NotificationManager notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        if(notificationmanager != null && mTetheredNotification != null) {
            notificationmanager.cancel(mTetheredNotification.icon);
            mTetheredNotification = null;
        }
    }

    private boolean configureUsbIface(boolean flag) {
        boolean flag1;
        flag1 = false;
        new String[0];
        String as[] = mNMService.listInterfaces();
        int i;
        int j;
        i = as.length;
        j = 0;
_L6:
        String s;
        if(j >= i)
            break MISSING_BLOCK_LABEL_179;
        s = as[j];
        if(!isUsb(s)) goto _L2; else goto _L1
_L1:
        InterfaceConfiguration interfaceconfiguration = mNMService.getInterfaceConfig(s);
        if(interfaceconfiguration == null) goto _L2; else goto _L3
_L3:
        interfaceconfiguration.setLinkAddress(new LinkAddress(NetworkUtils.numericToInetAddress("192.168.42.129"), 24));
        if(!flag) goto _L5; else goto _L4
_L4:
        interfaceconfiguration.setInterfaceUp();
_L7:
        interfaceconfiguration.clearFlag("running");
        mNMService.setInterfaceConfig(s, interfaceconfiguration);
_L2:
        j++;
          goto _L6
        Exception exception;
        exception;
        Log.e("Tethering", "Error listing Interfaces", exception);
_L8:
        return flag1;
_L5:
        interfaceconfiguration.setInterfaceDown();
          goto _L7
        Exception exception1;
        exception1;
        Log.e("Tethering", (new StringBuilder()).append("Error configuring interface ").append(s).toString(), exception1);
          goto _L8
        flag1 = true;
          goto _L8
    }

    private boolean isUsb(String s) {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        String as[] = mTetherableUsbRegexs;
        int i = as.length;
        int j = 0;
        do {
label0:
            {
                boolean flag;
                if(j < i) {
                    if(!s.matches(as[j]))
                        break label0;
                    flag = true;
                } else {
                    flag = false;
                }
                return flag;
            }
            j++;
        } while(true);
    }

    private void sendTetherStateChangedBroadcast() {
        boolean flag;
        try {
            flag = mConnService.isTetheringSupported();
        }
        catch(RemoteException remoteexception) {
            continue; /* Loop/switch isn't completed */
        }
        if(flag) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ArrayList arraylist;
        ArrayList arraylist1;
        ArrayList arraylist2;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        arraylist2 = new ArrayList();
        flag1 = false;
        flag2 = false;
        flag3 = false;
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mIfaces.keySet().iterator();
_L4:
        Object obj1;
        TetherInterfaceSM tetherinterfacesm;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        obj1 = iterator.next();
        tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(obj1);
        if(tetherinterfacesm == null)
            continue; /* Loop/switch isn't completed */
        if(tetherinterfacesm.isErrored()) {
            arraylist2.add((String)obj1);
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_145;
        Exception exception;
        exception;
        throw exception;
        if(tetherinterfacesm.isAvailable())
            arraylist.add((String)obj1);
        else
        if(tetherinterfacesm.isTethered()) {
            if(isUsb((String)obj1))
                flag2 = true;
            else
            if(isWifi((String)obj1))
                flag1 = true;
            else
            if(isBluetooth((String)obj1))
                flag3 = true;
            arraylist1.add((String)obj1);
        }
        if(true) goto _L4; else goto _L3
_L3:
        Intent intent = new Intent("android.net.conn.TETHER_STATE_CHANGED");
        intent.addFlags(0x28000000);
        intent.putStringArrayListExtra("availableArray", arraylist);
        intent.putStringArrayListExtra("activeArray", arraylist1);
        intent.putStringArrayListExtra("erroredArray", arraylist2);
        mContext.sendStickyBroadcast(intent);
        Log.d("Tethering", (new StringBuilder()).append("sendTetherStateChangedBroadcast ").append(arraylist.size()).append(", ").append(arraylist1.size()).append(", ").append(arraylist2.size()).toString());
        if(flag2) {
            if(flag1 || flag3)
                showTetheredNotification(0x1080556);
            else
                showTetheredNotification(0x1080557);
        } else
        if(flag1) {
            if(flag3)
                showTetheredNotification(0x1080556);
            else
                showTetheredNotification(0x1080558);
        } else
        if(flag3)
            showTetheredNotification(0x1080555);
        else
            clearTetheredNotification();
        if(true) goto _L1; else goto _L5
_L5:
    }

    private void showTetheredNotification(int i) {
        NotificationManager notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        if(notificationmanager != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(mTetheredNotification != null) {
            if(mTetheredNotification.icon == i)
                continue; /* Loop/switch isn't completed */
            notificationmanager.cancel(mTetheredNotification.icon);
        }
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.TetherSettings");
        intent.setFlags(0x40000000);
        PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
        Resources resources = Resources.getSystem();
        CharSequence charsequence = resources.getText(0x1040485);
        CharSequence charsequence1 = resources.getText(0x1040486);
        if(mTetheredNotification == null) {
            mTetheredNotification = new Notification();
            mTetheredNotification.when = 0L;
        }
        mTetheredNotification.icon = i;
        Notification notification = mTetheredNotification;
        notification.defaults = -2 & notification.defaults;
        mTetheredNotification.flags = 2;
        mTetheredNotification.tickerText = charsequence;
        mTetheredNotification.setLatestEventInfo(mContext, charsequence, charsequence1, pendingintent);
        notificationmanager.notify(mTetheredNotification.icon, mTetheredNotification);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void tetherUsb(boolean flag) {
        int j;
        new String[0];
        String as[];
        int i;
        try {
            as = mNMService.listInterfaces();
        }
        catch(Exception exception) {
            Log.e("Tethering", "Error listing Interfaces", exception);
            break MISSING_BLOCK_LABEL_64;
        }
        i = as.length;
        j = 0;
_L3:
        if(j >= i) goto _L2; else goto _L1
_L1:
        String s = as[j];
        if(!isUsb(s))
            continue; /* Loop/switch isn't completed */
        int k;
        if(flag)
            k = tether(s);
        else
            k = untether(s);
        if(k != 0)
            continue; /* Loop/switch isn't completed */
_L4:
        return;
        j++;
          goto _L3
_L2:
        Log.e("Tethering", "unable start or stop USB tethering");
          goto _L4
    }

    public void checkDunRequired() {
        byte byte0;
        int i;
        byte0 = 5;
        i = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "tether_dun_required", 2);
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        if(i == 2)
            break MISSING_BLOCK_LABEL_138;
        if(i == 1)
            byte0 = 4;
        if(byte0 != 4)
            break MISSING_BLOCK_LABEL_161;
        for(; mUpstreamIfaceTypes.contains(MOBILE_TYPE); mUpstreamIfaceTypes.remove(MOBILE_TYPE));
        break MISSING_BLOCK_LABEL_79;
        Exception exception;
        exception;
        throw exception;
        for(; mUpstreamIfaceTypes.contains(HIPRI_TYPE); mUpstreamIfaceTypes.remove(HIPRI_TYPE));
        if(!mUpstreamIfaceTypes.contains(DUN_TYPE))
            mUpstreamIfaceTypes.add(DUN_TYPE);
_L1:
        if(mUpstreamIfaceTypes.contains(DUN_TYPE))
            mPreferredUpstreamMobileApn = 4;
        else
            mPreferredUpstreamMobileApn = 5;
        obj;
        JVM INSTR monitorexit ;
        return;
        for(; mUpstreamIfaceTypes.contains(DUN_TYPE); mUpstreamIfaceTypes.remove(DUN_TYPE));
        if(!mUpstreamIfaceTypes.contains(MOBILE_TYPE))
            mUpstreamIfaceTypes.add(MOBILE_TYPE);
        if(!mUpstreamIfaceTypes.contains(HIPRI_TYPE))
            mUpstreamIfaceTypes.add(HIPRI_TYPE);
          goto _L1
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ConnectivityService.Tether from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("mUpstreamIfaceTypes: ");
        Integer integer;
        for(Iterator iterator = mUpstreamIfaceTypes.iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append(" ").append(integer).toString()))
            integer = (Integer)iterator.next();

        break MISSING_BLOCK_LABEL_137;
        Exception exception;
        exception;
        throw exception;
        printwriter.println();
        printwriter.println("Tether state:");
        TetherInterfaceSM tetherinterfacesm;
        for(Iterator iterator1 = mIfaces.values().iterator(); iterator1.hasNext(); printwriter.println((new StringBuilder()).append(" ").append(tetherinterfacesm.toString()).toString()))
            tetherinterfacesm = (TetherInterfaceSM)iterator1.next();

        obj;
        JVM INSTR monitorexit ;
        printwriter.println();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String[] getErroredIfaces() {
        ArrayList arraylist = new ArrayList();
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mIfaces.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            Object obj1 = iterator.next();
            if(((TetherInterfaceSM)mIfaces.get(obj1)).isErrored())
                arraylist.add((String)obj1);
        } while(true);
        break MISSING_BLOCK_LABEL_84;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        String as[] = new String[arraylist.size()];
        for(int i = 0; i < arraylist.size(); i++)
            as[i] = (String)arraylist.get(i);

        return as;
    }

    public int getLastTetherError(String s) {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(s);
        int i;
        if(tetherinterfacesm == null) {
            Log.e("Tethering", (new StringBuilder()).append("Tried to getLastTetherError on an unknown iface :").append(s).append(", ignoring").toString());
            i = 1;
        } else {
            i = tetherinterfacesm.getLastError();
        }
        return i;
    }

    public String[] getTetherableBluetoothRegexs() {
        return mTetherableBluetoothRegexs;
    }

    public String[] getTetherableIfaces() {
        ArrayList arraylist = new ArrayList();
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mIfaces.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            Object obj1 = iterator.next();
            if(((TetherInterfaceSM)mIfaces.get(obj1)).isAvailable())
                arraylist.add((String)obj1);
        } while(true);
        break MISSING_BLOCK_LABEL_84;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        String as[] = new String[arraylist.size()];
        for(int i = 0; i < arraylist.size(); i++)
            as[i] = (String)arraylist.get(i);

        return as;
    }

    public String[] getTetherableUsbRegexs() {
        return mTetherableUsbRegexs;
    }

    public String[] getTetherableWifiRegexs() {
        return mTetherableWifiRegexs;
    }

    public String[] getTetheredIfacePairs() {
        ArrayList arraylist = Lists.newArrayList();
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mIfaces.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)iterator.next();
            if(tetherinterfacesm.isTethered()) {
                arraylist.add(tetherinterfacesm.mMyUpstreamIfaceName);
                arraylist.add(tetherinterfacesm.mIfaceName);
            }
        } while(true);
        break MISSING_BLOCK_LABEL_83;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public String[] getTetheredIfaces() {
        ArrayList arraylist = new ArrayList();
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mIfaces.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            Object obj1 = iterator.next();
            if(((TetherInterfaceSM)mIfaces.get(obj1)).isTethered())
                arraylist.add((String)obj1);
        } while(true);
        break MISSING_BLOCK_LABEL_84;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        String as[] = new String[arraylist.size()];
        for(int i = 0; i < arraylist.size(); i++)
            as[i] = (String)arraylist.get(i);

        return as;
    }

    public int[] getUpstreamIfaceTypes() {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        updateConfiguration();
        int ai[] = new int[mUpstreamIfaceTypes.size()];
        Iterator iterator = mUpstreamIfaceTypes.iterator();
        for(int i = 0; i < mUpstreamIfaceTypes.size(); i++)
            ai[i] = ((Integer)iterator.next()).intValue();

        return ai;
    }

    public void handleTetherIfaceChange() {
        mTetherMasterSM.sendMessage(3);
    }

    public void interfaceAdded(String s) {
        boolean flag;
        boolean flag1;
        flag = false;
        flag1 = false;
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        if(isWifi(s))
            flag = true;
        if(isUsb(s)) {
            flag = true;
            flag1 = true;
        }
        if(isBluetooth(s))
            flag = true;
        if(flag && (TetherInterfaceSM)mIfaces.get(s) == null) goto _L2; else goto _L1
        Exception exception;
        exception;
        throw exception;
_L2:
        TetherInterfaceSM tetherinterfacesm = new TetherInterfaceSM(s, mLooper, flag1);
        mIfaces.put(s, tetherinterfacesm);
        tetherinterfacesm.start();
        obj;
        JVM INSTR monitorexit ;
_L1:
    }

    public void interfaceLinkStateChanged(String s, boolean flag) {
        interfaceStatusChanged(s, flag);
    }

    public void interfaceRemoved(String s) {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(s);
        if(tetherinterfacesm != null) {
            tetherinterfacesm.sendMessage(4);
            mIfaces.remove(s);
        }
        return;
    }

    public void interfaceStatusChanged(String s, boolean flag) {
        boolean flag1;
        boolean flag2;
        flag1 = false;
        flag2 = false;
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        if(!isWifi(s)) goto _L2; else goto _L1
_L1:
        flag1 = true;
_L5:
        if(flag1)
            break; /* Loop/switch isn't completed */
          goto _L3
_L2:
        if(isUsb(s)) {
            flag1 = true;
            flag2 = true;
        } else
        if(isBluetooth(s))
            flag1 = true;
        if(true) goto _L5; else goto _L4
_L4:
        TetherInterfaceSM tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(s);
        if(!flag) goto _L7; else goto _L6
_L6:
        if(tetherinterfacesm == null) {
            TetherInterfaceSM tetherinterfacesm1 = new TetherInterfaceSM(s, mLooper, flag2);
            mIfaces.put(s, tetherinterfacesm1);
            tetherinterfacesm1.start();
        }
_L8:
        obj;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L7:
        if(!isUsb(s) && tetherinterfacesm != null) {
            tetherinterfacesm.sendMessage(4);
            mIfaces.remove(s);
        }
        if(true) goto _L8; else goto _L3
_L3:
    }

    public boolean isBluetooth(String s) {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        String as[] = mTetherableBluetoothRegexs;
        int i = as.length;
        int j = 0;
        do {
label0:
            {
                boolean flag;
                if(j < i) {
                    if(!s.matches(as[j]))
                        break label0;
                    flag = true;
                } else {
                    flag = false;
                }
                return flag;
            }
            j++;
        } while(true);
    }

    public boolean isWifi(String s) {
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        String as[] = mTetherableWifiRegexs;
        int i = as.length;
        int j = 0;
        do {
label0:
            {
                boolean flag;
                if(j < i) {
                    if(!s.matches(as[j]))
                        break label0;
                    flag = true;
                } else {
                    flag = false;
                }
                return flag;
            }
            j++;
        } while(true);
    }

    public void limitReached(String s, String s1) {
    }

    public int setUsbTethering(boolean flag) {
        UsbManager usbmanager = (UsbManager)mContext.getSystemService("usb");
        Object obj = mPublicSync;
        obj;
        JVM INSTR monitorenter ;
        if(!flag)
            break MISSING_BLOCK_LABEL_64;
        if(mRndisEnabled) {
            tetherUsb(true);
        } else {
            mUsbTetherRequested = true;
            usbmanager.setCurrentFunction("rndis", false);
        }
_L1:
        obj;
        JVM INSTR monitorexit ;
        return 0;
        Exception exception;
        exception;
        throw exception;
        tetherUsb(false);
        if(mRndisEnabled)
            usbmanager.setCurrentFunction(null, false);
        mUsbTetherRequested = false;
          goto _L1
    }

    public int tether(String s) {
        Log.d("Tethering", (new StringBuilder()).append("Tethering ").append(s).toString());
        TetherInterfaceSM tetherinterfacesm;
        synchronized(mPublicSync) {
            tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(s);
        }
        int i;
        if(tetherinterfacesm == null) {
            Log.e("Tethering", (new StringBuilder()).append("Tried to Tether an unknown iface :").append(s).append(", ignoring").toString());
            i = 1;
        } else
        if(!tetherinterfacesm.isAvailable() && !tetherinterfacesm.isErrored()) {
            Log.e("Tethering", (new StringBuilder()).append("Tried to Tether an unavailable iface :").append(s).append(", ignoring").toString());
            i = 4;
        } else {
            tetherinterfacesm.sendMessage(2);
            i = 0;
        }
        return i;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int untether(String s) {
        Log.d("Tethering", (new StringBuilder()).append("Untethering ").append(s).toString());
        TetherInterfaceSM tetherinterfacesm;
        synchronized(mPublicSync) {
            tetherinterfacesm = (TetherInterfaceSM)mIfaces.get(s);
        }
        int i;
        if(tetherinterfacesm == null) {
            Log.e("Tethering", (new StringBuilder()).append("Tried to Untether an unknown iface :").append(s).append(", ignoring").toString());
            i = 1;
        } else
        if(tetherinterfacesm.isErrored()) {
            Log.e("Tethering", (new StringBuilder()).append("Tried to Untethered an errored iface :").append(s).append(", ignoring").toString());
            i = 4;
        } else {
            tetherinterfacesm.sendMessage(3);
            i = 0;
        }
        return i;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void updateConfiguration() {
        String as[] = mContext.getResources().getStringArray(0x1070017);
        String as1[] = mContext.getResources().getStringArray(0x1070018);
        String as2[] = mContext.getResources().getStringArray(0x107001a);
        int ai[] = mContext.getResources().getIntArray(0x107001d);
        ArrayList arraylist = new ArrayList();
        int i = ai.length;
        for(int j = 0; j < i; j++)
            arraylist.add(new Integer(ai[j]));

        synchronized(mPublicSync) {
            mTetherableUsbRegexs = as;
            mTetherableWifiRegexs = as1;
            mTetherableBluetoothRegexs = as2;
            mUpstreamIfaceTypes = arraylist;
        }
        checkDunRequired();
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static final boolean DBG = true;
    private static final String DHCP_DEFAULT_RANGE[];
    private static final String DNS_DEFAULT_SERVER1 = "8.8.8.8";
    private static final String DNS_DEFAULT_SERVER2 = "8.8.4.4";
    private static final Integer DUN_TYPE = new Integer(4);
    private static final Integer HIPRI_TYPE = new Integer(5);
    private static final Integer MOBILE_TYPE = new Integer(0);
    private static final String TAG = "Tethering";
    private static final String USB_NEAR_IFACE_ADDR = "192.168.42.129";
    private static final int USB_PREFIX_LENGTH = 24;
    private static final boolean VDBG;
    private final IConnectivityManager mConnService;
    private Context mContext;
    private String mDefaultDnsServers[];
    private String mDhcpRange[];
    private HashMap mIfaces;
    private Looper mLooper;
    private final INetworkManagementService mNMService;
    private int mPreferredUpstreamMobileApn;
    private Object mPublicSync;
    private boolean mRndisEnabled;
    private BroadcastReceiver mStateReceiver;
    private final INetworkStatsService mStatsService;
    private StateMachine mTetherMasterSM;
    private String mTetherableBluetoothRegexs[];
    private String mTetherableUsbRegexs[];
    private String mTetherableWifiRegexs[];
    private Notification mTetheredNotification;
    private HandlerThread mThread;
    private Collection mUpstreamIfaceTypes;
    private boolean mUsbTetherRequested;

    static  {
        String as[] = new String[14];
        as[0] = "192.168.42.2";
        as[1] = "192.168.42.254";
        as[2] = "192.168.43.2";
        as[3] = "192.168.43.254";
        as[4] = "192.168.44.2";
        as[5] = "192.168.44.254";
        as[6] = "192.168.45.2";
        as[7] = "192.168.45.254";
        as[8] = "192.168.46.2";
        as[9] = "192.168.46.254";
        as[10] = "192.168.47.2";
        as[11] = "192.168.47.254";
        as[12] = "192.168.48.2";
        as[13] = "192.168.48.254";
        DHCP_DEFAULT_RANGE = as;
    }




/*
    static boolean access$202(Tethering tethering, boolean flag) {
        tethering.mRndisEnabled = flag;
        return flag;
    }

*/






/*
    static boolean access$302(Tethering tethering, boolean flag) {
        tethering.mUsbTetherRequested = flag;
        return flag;
    }

*/








}
