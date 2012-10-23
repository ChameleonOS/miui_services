// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import android.server.BluetoothService;
import android.util.Log;
import android.util.Slog;
import java.io.FileNotFoundException;
import java.io.FileReader;

// Referenced classes of package com.android.server:
//            PowerManagerService

class DockObserver extends UEventObserver {

    public DockObserver(Context context, PowerManagerService powermanagerservice) {
        mDockState = 0;
        mPreviousDockState = 0;
        mContext = context;
        mPowerManager = powermanagerservice;
        init();
        startObserving("DEVPATH=/devices/virtual/switch/dock");
    }

    private final void init() {
        char ac[] = new char[1024];
        FileReader filereader = new FileReader("/sys/class/switch/dock/state");
        int i = filereader.read(ac, 0, 1024);
        filereader.close();
        int j = Integer.valueOf((new String(ac, 0, i)).trim()).intValue();
        mDockState = j;
        mPreviousDockState = j;
_L1:
        return;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w(TAG, "This kernel does not have dock station support");
          goto _L1
        Exception exception;
        exception;
        Slog.e(TAG, "", exception);
          goto _L1
    }

    private final void update() {
        mHandler.sendEmptyMessage(0);
    }

    public void onUEvent(android.os.UEventObserver.UEvent uevent) {
        if(Log.isLoggable(TAG, 2))
            Slog.v(TAG, (new StringBuilder()).append("Dock UEVENT: ").append(uevent.toString()).toString());
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        try {
            int i = Integer.parseInt(uevent.get("SWITCH_STATE"));
            if(i != mDockState) {
                mPreviousDockState = mDockState;
                mDockState = i;
                if(mSystemReady) {
                    if(mPreviousDockState != 1 && mPreviousDockState != 3 && mPreviousDockState != 4 || mDockState != 0)
                        mPowerManager.userActivityWithForce(SystemClock.uptimeMillis(), false, true);
                    update();
                }
            }
        }
        catch(NumberFormatException numberformatexception) {
            Slog.e(TAG, (new StringBuilder()).append("Could not parse switch state from event ").append(uevent).toString());
        }
        finally {
            this;
        }
        return;
        throw exception;
    }

    void systemReady() {
        this;
        JVM INSTR monitorenter ;
        if(mDockState != 0)
            update();
        mSystemReady = true;
        return;
    }

    private static final String DOCK_STATE_PATH = "/sys/class/switch/dock/state";
    private static final String DOCK_UEVENT_MATCH = "DEVPATH=/devices/virtual/switch/dock";
    private static final boolean LOG;
    private static final int MSG_DOCK_STATE;
    private static final String TAG = com/android/server/DockObserver.getSimpleName();
    private final Context mContext;
    private int mDockState;
    private final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 0 0: default 24
        //                       0 25;
               goto _L1 _L2
_L1:
            return;
_L2:
            this;
            JVM INSTR monitorenter ;
            android.content.ContentResolver contentresolver;
            Slog.i(DockObserver.TAG, (new StringBuilder()).append("Dock state changed: ").append(mDockState).toString());
            contentresolver = mContext.getContentResolver();
            if(android.provider.Settings.Secure.getInt(contentresolver, "device_provisioned", 0) != 0) goto _L4; else goto _L3
_L3:
            Slog.i(DockObserver.TAG, "Device not provisioned, skipping dock broadcast");
              goto _L1
            Exception exception;
            exception;
            throw exception;
_L4:
            Intent intent;
            intent = new Intent("android.intent.action.DOCK_EVENT");
            intent.addFlags(0x20000000);
            intent.putExtra("android.intent.extra.DOCK_STATE", mDockState);
            String s = BluetoothService.readDockBluetoothAddress();
            if(s != null)
                intent.putExtra("android.bluetooth.device.extra.DEVICE", BluetoothAdapter.getDefaultAdapter().getRemoteDevice(s));
            if(android.provider.Settings.System.getInt(contentresolver, "dock_sounds_enabled", 1) != 1) goto _L6; else goto _L5
_L5:
            String s1 = null;
            if(mDockState != 0) goto _L8; else goto _L7
_L7:
            if(mPreviousDockState != 1 && mPreviousDockState != 3 && mPreviousDockState != 4) goto _L10; else goto _L9
_L11:
            if(s1 != null) {
                String s2 = android.provider.Settings.System.getString(contentresolver, s1);
                if(s2 != null) {
                    Uri uri = Uri.parse((new StringBuilder()).append("file://").append(s2).toString());
                    if(uri != null) {
                        Ringtone ringtone = RingtoneManager.getRingtone(mContext, uri);
                        if(ringtone != null) {
                            ringtone.setStreamType(1);
                            ringtone.play();
                        }
                    }
                }
            }
_L6:
            mContext.sendStickyBroadcast(intent);
            this;
            JVM INSTR monitorexit ;
              goto _L1
_L10:
            if(mPreviousDockState == 2)
                s1 = "car_undock_sound";
              goto _L11
_L8:
            if(mDockState != 1 && mDockState != 3 && mDockState != 4) goto _L13; else goto _L12
_L13:
            if(mDockState == 2)
                s1 = "car_dock_sound";
              goto _L11
_L9:
            s1 = "desk_undock_sound";
              goto _L11
_L12:
            s1 = "desk_dock_sound";
              goto _L11
        }

        final DockObserver this$0;

             {
                this$0 = DockObserver.this;
                super();
            }
    };
    private PowerManagerService mPowerManager;
    private int mPreviousDockState;
    private boolean mSystemReady;





}
