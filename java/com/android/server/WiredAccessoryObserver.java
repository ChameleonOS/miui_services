// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.media.AudioManager;
import android.os.*;
import android.util.Log;
import android.util.Slog;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class WiredAccessoryObserver extends UEventObserver {
    private final class BootCompletedReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            init();
            for(int i = 0; i < WiredAccessoryObserver.uEventInfo.size(); i++) {
                UEventInfo ueventinfo = (UEventInfo)WiredAccessoryObserver.uEventInfo.get(i);
                startObserving((new StringBuilder()).append("DEVPATH=").append(ueventinfo.getDevPath()).toString());
            }

        }

        final WiredAccessoryObserver this$0;

        private BootCompletedReceiver() {
            this$0 = WiredAccessoryObserver.this;
            super();
        }

    }

    private static class UEventInfo {

        public boolean checkSwitchExists() {
            File file = new File(getSwitchStatePath());
            boolean flag;
            if(file != null && file.exists())
                flag = true;
            else
                flag = false;
            return flag;
        }

        public int computeNewHeadsetState(int i, int j) {
            int k = -1 ^ (mState1Bits | mState2Bits);
            int l;
            if(j == 1)
                l = mState1Bits;
            else
            if(j == 2)
                l = mState2Bits;
            else
                l = 0;
            return l | i & k;
        }

        public String getDevName() {
            return mDevName;
        }

        public String getDevPath() {
            Object aobj[] = new Object[1];
            aobj[0] = mDevName;
            return String.format("/devices/virtual/switch/%s", aobj);
        }

        public String getSwitchStatePath() {
            Object aobj[] = new Object[1];
            aobj[0] = mDevName;
            return String.format("/sys/class/switch/%s/state", aobj);
        }

        private final String mDevName;
        private final int mState1Bits;
        private final int mState2Bits;

        public UEventInfo(String s, int i, int j) {
            mDevName = s;
            mState1Bits = i;
            mState2Bits = j;
        }
    }


    public WiredAccessoryObserver(Context context) {
        mContext = context;
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "WiredAccessoryObserver");
        mWakeLock.setReferenceCounted(false);
        mAudioManager = (AudioManager)context.getSystemService("audio");
        context.registerReceiver(new BootCompletedReceiver(), new IntentFilter("android.intent.action.BOOT_COMPLETED"), null, null);
    }

    /**
     * @deprecated Method init is deprecated
     */

    private final void init() {
        this;
        JVM INSTR monitorenter ;
        char ac[];
        int i;
        ac = new char[1024];
        mPrevHeadsetState = mHeadsetState;
        Slog.v(TAG, "init()");
        i = 0;
_L1:
        UEventInfo ueventinfo;
        if(i >= uEventInfo.size())
            break MISSING_BLOCK_LABEL_192;
        ueventinfo = (UEventInfo)uEventInfo.get(i);
        FileReader filereader = new FileReader(ueventinfo.getSwitchStatePath());
        int j = filereader.read(ac, 0, 1024);
        filereader.close();
        int k = Integer.valueOf((new String(ac, 0, j)).trim()).intValue();
        if(k > 0)
            updateState(ueventinfo.getDevPath(), ueventinfo.getDevName(), k);
_L2:
        i++;
          goto _L1
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w(TAG, (new StringBuilder()).append(ueventinfo.getSwitchStatePath()).append(" not found while attempting to determine initial switch state").toString());
          goto _L2
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Slog.e(TAG, "", exception1);
          goto _L2
        this;
        JVM INSTR monitorexit ;
    }

    private static List makeObservedUEventList() {
        ArrayList arraylist = new ArrayList();
        UEventInfo ueventinfo = new UEventInfo("h2w", 1, 2);
        UEventInfo ueventinfo1;
        UEventInfo ueventinfo2;
        if(ueventinfo.checkSwitchExists())
            arraylist.add(ueventinfo);
        else
            Slog.w(TAG, "This kernel does not have wired headset support");
        ueventinfo1 = new UEventInfo("usb_audio", 4, 8);
        if(ueventinfo1.checkSwitchExists())
            arraylist.add(ueventinfo1);
        else
            Slog.w(TAG, "This kernel does not have usb audio support");
        ueventinfo2 = new UEventInfo("hdmi_audio", 16, 0);
        if(ueventinfo2.checkSwitchExists()) {
            arraylist.add(ueventinfo2);
        } else {
            UEventInfo ueventinfo3 = new UEventInfo("hdmi", 16, 0);
            if(ueventinfo3.checkSwitchExists())
                arraylist.add(ueventinfo3);
            else
                Slog.w(TAG, "This kernel does not have HDMI audio support");
        }
        return arraylist;
    }

    private final void setDeviceState(int i, int j, int k, String s) {
        if((j & i) != (k & i)) {
            int l;
            char c;
            String s1;
            StringBuilder stringbuilder;
            String s2;
            if((j & i) != 0)
                l = 1;
            else
                l = 0;
            if(i == 1)
                c = '\004';
            else
            if(i == 2)
                c = '\b';
            else
            if(i == 4)
                c = '\u0800';
            else
            if(i == 8) {
                c = '\u1000';
            } else {
label0:
                {
                    if(i != 16)
                        break label0;
                    c = '\u0400';
                }
            }
            s1 = TAG;
            stringbuilder = (new StringBuilder()).append("device ").append(s);
            if(l == 1)
                s2 = " connected";
            else
                s2 = " disconnected";
            Slog.v(s1, stringbuilder.append(s2).toString());
            mAudioManager.setWiredDeviceConnectionState(c, l, s);
        }
        return;
        Slog.e(TAG, (new StringBuilder()).append("setDeviceState() invalid headset type: ").append(i).toString());
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_89;
    }

    /**
     * @deprecated Method setDevicesState is deprecated
     */

    private final void setDevicesState(int i, int j, String s) {
        this;
        JVM INSTR monitorenter ;
        int k;
        int l;
        k = 31;
        l = 1;
_L3:
        if(k == 0) goto _L2; else goto _L1
_L1:
        if((l & k) == 0)
            continue; /* Loop/switch isn't completed */
        setDeviceState(l, i, j, s);
        k &= ~l;
        l <<= 1;
          goto _L3
_L2:
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method update is deprecated
     */

    private final void update(String s, int i) {
        this;
        JVM INSTR monitorenter ;
        int j = i & 0x1f;
        boolean flag;
        boolean flag1;
        j | mHeadsetState;
        int k = j & 4;
        int l = j & 8;
        int i1 = j & 3;
        flag = true;
        flag1 = true;
        Slog.v(TAG, (new StringBuilder()).append("newState = ").append(i).append(", headsetState = ").append(j).append(",").append("mHeadsetState = ").append(mHeadsetState).toString());
        if(mHeadsetState == j || (i1 & i1 - 1) != 0) {
            Log.e(TAG, "unsetting h2w flag");
            flag = false;
        }
        if(k >> 2 == 1 && l >> 3 == 1) {
            Log.e(TAG, "unsetting usb flag");
            flag1 = false;
        }
        if(flag || flag1) goto _L2; else goto _L1
_L1:
        Log.e(TAG, "invalid transition, returning ...");
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        mHeadsetName = s;
        mPrevHeadsetState = mHeadsetState;
        mHeadsetState = j;
        mWakeLock.acquire();
        mHandler.sendMessage(mHandler.obtainMessage(0, mHeadsetState, mPrevHeadsetState, mHeadsetName));
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method updateState is deprecated
     */

    private final void updateState(String s, String s1, int i) {
        this;
        JVM INSTR monitorenter ;
        int j = 0;
_L2:
        if(j < uEventInfo.size()) {
            UEventInfo ueventinfo = (UEventInfo)uEventInfo.get(j);
            if(!s.equals(ueventinfo.getDevPath()))
                break MISSING_BLOCK_LABEL_63;
            update(s1, ueventinfo.computeNewHeadsetState(mHeadsetState, i));
        }
        this;
        JVM INSTR monitorexit ;
        return;
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public void onUEvent(android.os.UEventObserver.UEvent uevent) {
        Slog.v(TAG, (new StringBuilder()).append("Headset UEVENT: ").append(uevent.toString()).toString());
        updateState(uevent.get("DEVPATH"), uevent.get("SWITCH_NAME"), Integer.parseInt(uevent.get("SWITCH_STATE")));
_L1:
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        Slog.e(TAG, (new StringBuilder()).append("Could not parse switch state from event ").append(uevent).toString());
          goto _L1
    }

    private static final int BIT_HDMI_AUDIO = 16;
    private static final int BIT_HEADSET = 1;
    private static final int BIT_HEADSET_NO_MIC = 2;
    private static final int BIT_USB_HEADSET_ANLG = 4;
    private static final int BIT_USB_HEADSET_DGTL = 8;
    private static final int HEADSETS_WITH_MIC = 1;
    private static final boolean LOG = true;
    private static final int SUPPORTED_HEADSETS = 31;
    private static final String TAG = com/android/server/WiredAccessoryObserver.getSimpleName();
    private static List uEventInfo = makeObservedUEventList();
    private final AudioManager mAudioManager;
    private final Context mContext;
    private final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            setDevicesState(message.arg1, message.arg2, (String)message.obj);
            mWakeLock.release();
        }

        final WiredAccessoryObserver this$0;

             {
                this$0 = WiredAccessoryObserver.this;
                super();
            }
    };
    private String mHeadsetName;
    private int mHeadsetState;
    private int mPrevHeadsetState;
    private final android.os.PowerManager.WakeLock mWakeLock;





}
