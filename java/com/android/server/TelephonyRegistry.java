// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.net.LinkCapabilities;
import android.net.LinkProperties;
import android.os.*;
import android.telephony.*;
import android.text.TextUtils;
import com.android.internal.app.IBatteryStats;
import com.android.internal.telephony.DefaultPhoneNotifier;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.server.am.BatteryStatsService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

class TelephonyRegistry extends com.android.internal.telephony.ITelephonyRegistry.Stub {
    private static class Record {

        IBinder binder;
        IPhoneStateListener callback;
        int events;
        String pkgForDebug;

        private Record() {
        }

    }


    TelephonyRegistry(Context context) {
        mCallState = 0;
        mCallIncomingNumber = "";
        mServiceState = new ServiceState();
        mSignalStrength = new SignalStrength();
        mMessageWaiting = false;
        mCallForwarding = false;
        mDataActivity = 0;
        mDataConnectionState = -1;
        mDataConnectionPossible = false;
        mDataConnectionReason = "";
        mDataConnectionApn = "";
        mCellLocation = new Bundle();
        mOtaspMode = 1;
        mCellInfo = null;
        CellLocation celllocation = CellLocation.getEmpty();
        if(celllocation != null)
            celllocation.fillInNotifierBundle(mCellLocation);
        mContext = context;
        mConnectedApns = new ArrayList();
    }

    private void broadcastCallStateChanged(int i, String s) {
        Exception exception;
label0:
        {
            {
                long l = Binder.clearCallingIdentity();
                if(i != 0)
                    break label0;
                Intent intent;
                try {
                    mBatteryStats.notePhoneOff();
                }
                catch(RemoteException remoteexception) { }
                finally {
                    Binder.restoreCallingIdentity(l);
                }
            }
            Binder.restoreCallingIdentity(l);
            intent = new Intent("android.intent.action.PHONE_STATE");
            intent.putExtra("state", DefaultPhoneNotifier.convertCallState(i).toString());
            if(!TextUtils.isEmpty(s))
                intent.putExtra("incoming_number", s);
            mContext.sendBroadcast(intent, "android.permission.READ_PHONE_STATE");
            return;
        }
        mBatteryStats.notePhoneOn();
        if(true)
            break MISSING_BLOCK_LABEL_17;
        else
            throw exception;
    }

    private void broadcastDataConnectionFailed(String s, String s1) {
        Intent intent = new Intent("android.intent.action.DATA_CONNECTION_FAILED");
        intent.putExtra("reason", s);
        intent.putExtra("apnType", s1);
        mContext.sendStickyBroadcast(intent);
    }

    private void broadcastDataConnectionStateChanged(int i, boolean flag, String s, String s1, String s2, LinkProperties linkproperties, LinkCapabilities linkcapabilities, 
            boolean flag1) {
        Intent intent = new Intent("android.intent.action.ANY_DATA_STATE");
        intent.putExtra("state", DefaultPhoneNotifier.convertDataState(i).toString());
        if(!flag)
            intent.putExtra("networkUnvailable", true);
        if(s != null)
            intent.putExtra("reason", s);
        if(linkproperties != null) {
            intent.putExtra("linkProperties", linkproperties);
            String s3 = linkproperties.getInterfaceName();
            if(s3 != null)
                intent.putExtra("iface", s3);
        }
        if(linkcapabilities != null)
            intent.putExtra("linkCapabilities", linkcapabilities);
        if(flag1)
            intent.putExtra("networkRoaming", true);
        intent.putExtra("apn", s1);
        intent.putExtra("apnType", s2);
        mContext.sendStickyBroadcast(intent);
    }

    private void broadcastServiceStateChanged(ServiceState servicestate) {
        long l = Binder.clearCallingIdentity();
        Intent intent;
        Bundle bundle;
        Exception exception;
        try {
            mBatteryStats.notePhoneState(servicestate.getState());
        }
        catch(RemoteException remoteexception) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        intent = new Intent("android.intent.action.SERVICE_STATE");
        bundle = new Bundle();
        servicestate.fillInNotifierBundle(bundle);
        intent.putExtras(bundle);
        mContext.sendStickyBroadcast(intent);
        return;
        throw exception;
    }

    private void broadcastSignalStrengthChanged(SignalStrength signalstrength) {
        long l = Binder.clearCallingIdentity();
        Intent intent;
        Bundle bundle;
        Exception exception;
        try {
            mBatteryStats.notePhoneSignalStrength(signalstrength);
        }
        catch(RemoteException remoteexception) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        intent = new Intent("android.intent.action.SIG_STR");
        intent.addFlags(0x20000000);
        bundle = new Bundle();
        signalstrength.fillInNotifierBundle(bundle);
        intent.putExtras(bundle);
        mContext.sendStickyBroadcast(intent);
        return;
        throw exception;
    }

    private void checkListenerPermission(int i) {
        if((i & 0x10) != 0)
            mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION", null);
        if((i & 0x400) != 0)
            mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION", null);
        if((i & 0xec) != 0)
            mContext.enforceCallingOrSelfPermission("android.permission.READ_PHONE_STATE", null);
    }

    private boolean checkNotifyPermission(String s) {
        boolean flag;
        if(mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0) {
            flag = true;
        } else {
            (new StringBuilder()).append("Modify Phone State Permission Denial: ").append(s).append(" from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString();
            flag = false;
        }
        return flag;
    }

    private void handleRemoveListLocked() {
        if(mRemoveList.size() > 0) {
            for(Iterator iterator = mRemoveList.iterator(); iterator.hasNext(); remove((IBinder)iterator.next()));
            mRemoveList.clear();
        }
    }

    private void remove(IBinder ibinder) {
        ArrayList arraylist = mRecords;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mRecords.size();
        int j = 0;
        do {
label0:
            {
                if(j < i) {
                    if(((Record)mRecords.get(j)).binder != ibinder)
                        break label0;
                    mRecords.remove(j);
                }
                return;
            }
            j++;
        } while(true);
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump telephony.registry from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L3:
        return;
_L2:
        ArrayList arraylist = mRecords;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mRecords.size();
        printwriter.println("last known state:");
        printwriter.println((new StringBuilder()).append("  mCallState=").append(mCallState).toString());
        printwriter.println((new StringBuilder()).append("  mCallIncomingNumber=").a