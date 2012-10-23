// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.app.*;
import android.bluetooth.IBluetooth;
import android.content.*;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.INfcAdapter;
import android.os.*;
import android.os.storage.IMountService;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.telephony.ITelephony;
import com.android.server.PowerManagerService;

public final class ShutdownThread extends Thread {
    private static class CloseDialogReceiver extends BroadcastReceiver
        implements android.content.DialogInterface.OnDismissListener {

        public void onDismiss(DialogInterface dialoginterface) {
            mContext.unregisterReceiver(this);
            Object obj = ShutdownThread.sIsStartedGuard;
            obj;
            JVM INSTR monitorenter ;
            if(!ShutdownThread.sIsStarted) {
                ShutdownThread.mReboot = false;
                ShutdownThread.mRebootReason = null;
            }
            return;
        }

        public void onReceive(Context context, Intent intent) {
            dialog.cancel();
        }

        public Dialog dialog;
        private Context mContext;

        CloseDialogReceiver(Context context) {
            mContext = context;
            context.registerReceiver(this, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        }
    }


    private ShutdownThread() {
    }

    private static void beginShutdownSequence(Context context) {
        synchronized(sIsStartedGuard) {
            if(sIsStarted) {
                Log.d("ShutdownThread", "Shutdown sequence already running, returning.");
                break MISSING_BLOCK_LABEL_278;
            }
            sIsStarted = true;
        }
        ProgressDialog progressdialog = new ProgressDialog(context);
        progressdialog.setTitle(context.getText(0x60c0191));
        progressdialog.setMessage(context.getText(0x60c01aa));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        progressdialog.getWindow().setType(2009);
        createShutDownDialog(context);
        sInstance.mContext = context;
        sInstance.mPowerManager = (PowerManager)context.getSystemService("power");
        sInstance.mCpuWakeLock = null;
        try {
            sInstance.mCpuWakeLock = sInstance.mPowerManager.newWakeLock(1, "ShutdownThread-cpu");
            sInstance.mCpuWakeLock.setReferenceCounted(false);
            sInstance.mCpuWakeLock.acquire();
        }
        catch(SecurityException securityexception) {
            Log.w("ShutdownThread", "No permission to acquire wake lock", securityexception);
            sInstance.mCpuWakeLock = null;
        }
        sInstance.mScreenWakeLock = null;
        if(sInstance.mPowerManager.isScreenOn())
            try {
                sInstance.mScreenWakeLock = sInstance.mPowerManager.newWakeLock(26, "ShutdownThread-screen");
                sInstance.mScreenWakeLock.setReferenceCounted(false);
                sInstance.mScreenWakeLock.acquire();
            }
            catch(SecurityException securityexception1) {
                Log.w("ShutdownThread", "No permission to acquire wake lock", securityexception1);
                sInstance.mScreenWakeLock = null;
            }
        sInstance.mHandler = new Handler() {

        };
        sInstance.start();
          goto _L1
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    private static void createShutDownDialog(Context context) {
        Dialog dialog = new Dialog(context, 0x10300f1);
        View view = LayoutInflater.from(dialog.getContext()).inflate(0x603002e, null);
        TextView textview = (TextView)view.findViewById(0x60b0021);
        ImageView imageview = (ImageView)view.findViewById(0x60b0022);
        if(mReboot)
            textview.setText(0x60c0001);
        else
            textview.setText(0x60c01aa);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setType(2021);
        dialog.getWindow().setBackgroundDrawableResource(0x6020034);
        dialog.show();
        ((AnimationDrawable)imageview.getDrawable()).start();
    }

    private static int getResourceId(int i) {
        if(mReboot)
            i = 0x60c0000;
        return i;
    }

    private static int getTitleResourceId() {
        int i;
        if(mReboot)
            i = 0x60c018c;
        else
            i = 0x60c0191;
        return i;
    }

    public static void reboot(Context context, String s, boolean flag) {
        mReboot = true;
        mRebootSafeMode = false;
        mRebootReason = s;
        shutdownInner(context, flag);
    }

    public static void rebootOrShutdown(boolean flag, String s) {
        if(flag) {
            Log.i("ShutdownThread", (new StringBuilder()).append("Rebooting, reason: ").append(s).toString());
            try {
                PowerManagerService.lowLevelReboot(s);
            }
            catch(Exception exception1) {
                Log.e("ShutdownThread", "Reboot failed, will attempt shutdown instead", exception1);
            }
        } else {
            SystemVibrator systemvibrator = new SystemVibrator();
            try {
                systemvibrator.vibrate(500L);
            }
            catch(Exception exception) {
                Log.w("ShutdownThread", "Failed to vibrate during shutdown.", exception);
            }
            try {
                Thread.sleep(500L);
            }
            catch(InterruptedException interruptedexception) { }
        }
        Log.i("ShutdownThread", "Performing low-level shutdown...");
        PowerManagerService.lowLevelShutdown();
    }

    public static void rebootSafeMode(Context context, boolean flag) {
        mReboot = true;
        mRebootSafeMode = false;
        mRebootReason = null;
        shutdownInner(context, flag);
    }

    public static void shutdown(Context context, boolean flag) {
        mReboot = false;
        mRebootSafeMode = false;
        shutdownInner(context, flag);
    }

    static void shutdownInner(final Context context, boolean flag) {
        synchronized(sIsStartedGuard) {
            if(sIsStarted) {
                Log.d("ShutdownThread", "Request to shutdown already running, returning.");
                break MISSING_BLOCK_LABEL_208;
            }
        }
        int i = context.getResources().getInteger(0x10e0016);
        int j;
        int k;
        if(mRebootSafeMode)
            j = 0x1040132;
        else
        if(i == 2)
            j = 0x1040130;
        else
            j = 0x104012f;
        k = getResourceId(j);
        Log.d("ShutdownThread", (new StringBuilder()).append("Notifying thread to start shutdown longPressBehavior=").append(i).toString());
        if(flag) {
            CloseDialogReceiver closedialogreceiver = new CloseDialogReceiver(context);
            AlertDialog alertdialog = (new android.app.AlertDialog.Builder(context)).setTitle(getTitleResourceId()).setMessage(k).setPositiveButton(0x1040013, new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int l) {
                    ShutdownThread.beginShutdownSequence(context);
                }

                final Context val$context;

             {
                context = context1;
                super();
            }
            }).setNegativeButton(0x1040009, null).create();
            closedialogreceiver.dialog = alertdialog;
            alertdialog.setOnDismissListener(closedialogreceiver);
            alertdialog.getWindow().setType(2009);
            alertdialog.show();
        } else {
            beginShutdownSequence(context);
        }
          goto _L1
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    private void shutdownRadios(int i) {
        final long endTime = SystemClock.elapsedRealtime() + (long)i;
        final boolean done[] = new boolean[1];
        Thread thread = new Thread() {

            public void run() {
                INfcAdapter infcadapter;
                ITelephony itelephony;
                IBluetooth ibluetooth;
                infcadapter = android.nfc.INfcAdapter.Stub.asInterface(ServiceManager.checkService("nfc"));
                itelephony = com.android.internal.telephony.ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
                ibluetooth = android.bluetooth.IBluetooth.Stub.asInterface(ServiceManager.checkService("bluetooth"));
                if(infcadapter == null) goto _L2; else goto _L1
_L1:
                if(infcadapter.getState() != 1) goto _L3; else goto _L2
_L18:
                boolean flag;
                if(!flag) {
                    Log.w("ShutdownThread", "Turning off NFC...");
                    infcadapter.disable(false);
                }
_L19:
                if(ibluetooth == null) goto _L5; else goto _L4
_L4:
                if(ibluetooth.getBluetoothState() != 10) goto _L6; else goto _L5
_L20:
                boolean flag1;
                if(!flag1) {
                    Log.w("ShutdownThread", "Disabling Bluetooth...");
                    ibluetooth.disable(false);
                }
_L21:
                if(itelephony == null)
                    break MISSING_BLOCK_LABEL_461;
                if(!itelephony.isRadioOn())
                    break MISSING_BLOCK_LABEL_461;
                  goto _L7
_L22:
                boolean flag2;
                if(!flag2) {
                    Log.w("ShutdownThread", "Turning off radio...");
                    itelephony.setRadio(false);
                }
_L23:
                Log.i("ShutdownThread", "Waiting for NFC, Bluetooth and Radio...");
_L27:
                if(SystemClock.elapsedRealtime() >= endTime) goto _L9; else goto _L8
_L8:
                if(flag1) goto _L11; else goto _L10
_L10:
                int k = ibluetooth.getBluetoothState();
                RemoteException remoteexception;
                RemoteException remoteexception1;
                RemoteException remoteexception2;
                int j;
                boolean flag3;
                if(k == 10)
                    flag1 = true;
                else
                    flag1 = false;
_L24:
                if(flag1)
                    Log.i("ShutdownThread", "Bluetooth turned off.");
_L11:
                if(flag2) goto _L13; else goto _L12
_L12:
                flag3 = itelephony.isRadioOn();
                RemoteException remoteexception5;
                if(!flag3)
                    flag2 = true;
                else
                    flag2 = false;
_L25:
                if(flag2)
                    Log.i("ShutdownThread", "Radio turned off.");
_L13:
                if(flag) goto _L15; else goto _L14
_L14:
                j = infcadapter.getState();
                RemoteException remoteexception4;
                if(j == 1)
                    flag = true;
                else
                    flag = false;
_L26:
                if(flag2)
                    Log.i("ShutdownThread", "NFC turned off.");
_L15:
                if(!flag2 || !flag1 || !flag) goto _L17; else goto _L16
_L16:
                Log.i("ShutdownThread", "NFC, Radio and Bluetooth shutdown complete.");
                done[0] = true;
_L9:
                return;
_L3:
                flag = false;
                  goto _L18
                remoteexception;
                Log.e("ShutdownThread", "RemoteException during NFC shutdown", remoteexception);
                flag = true;
                  goto _L19
_L6:
                flag1 = false;
                  goto _L20
                remoteexception1;
                Log.e("ShutdownThread", "RemoteException during bluetooth shutdown", remoteexception1);
                flag1 = true;
                  goto _L21
_L7:
                flag2 = false;
                  goto _L22
                