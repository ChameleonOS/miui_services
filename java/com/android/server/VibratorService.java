// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.os.*;
import android.view.InputDevice;
import java.util.*;

public class VibratorService extends android.os.IVibratorService.Stub
    implements android.hardware.input.InputManager.InputDeviceListener {
    private class VibrateThread extends Thread {

        private void delay(long l) {
            if(l <= 0L) goto _L2; else goto _L1
_L1:
            long l1 = SystemClock.uptimeMillis();
_L4:
            try {
                wait(l);
            }
            catch(InterruptedException interruptedexception) { }
            if(!mDone) goto _L3; else goto _L2
_L2:
            return;
_L3:
            l = l - SystemClock.uptimeMillis() - l1;
            if(l > 0L) goto _L4; else goto _L2
        }

        public void run() {
            Process.setThreadPriority(-8);
            this;
            JVM INSTR monitorenter ;
            long al[];
            int i;
            int j;
            al = mVibration.mPattern;
            i = al.length;
            j = mVibration.mRepeat;
            long l;
            int k;
            l = 0L;
            k = 0;
_L9:
            boolean flag = mDone;
            if(flag) goto _L2; else goto _L1
_L1:
            int j1;
            if(k >= i)
                break MISSING_BLOCK_LABEL_73;
            j1 = k + 1;
            long l1 = al[k];
            l += l1;
            k = j1;
            boolean flag1;
            delay(l);
            flag1 = mDone;
            if(!flag1) goto _L4; else goto _L3
_L3:
            k;
_L11:
            mWakeLock.release();
            this;
            JVM INSTR monitorexit ;
            synchronized(mVibrations) {
                if(mThread == this)
                    mThread = null;
                if(!mDone) {
                    mVibrations.remove(mVibration);
                    unlinkVibration(mVibration);
                    startNextVibrationLocked();
                }
            }
            return;
_L4:
            if(k >= i) goto _L6; else goto _L5
_L5:
            int i1 = k + 1;
            l = al[k];
            if(l <= 0L) goto _L8; else goto _L7
_L7:
            doVibratorOn(l);
            k = i1;
              goto _L9
_L10:
            this;
            JVM INSTR monitorexit ;
            Exception exception;
            throw exception;
            exception1;
            linkedlist;
            JVM INSTR monitorexit ;
            throw exception1;
            exception;
            k;
              goto _L10
_L8:
            k = i1;
              goto _L9
_L2:
            k;
              goto _L11
_L6:
label0:
            {
                if(j >= 0)
                    break label0;
                k;
            }
            if(true) goto _L11; else goto _L12
_L12:
            l = 0L;
            k = j;
              goto _L9
            exception;
              goto _L10
        }

        boolean mDone;
        final Vibration mVibration;
        final VibratorService this$0;

        VibrateThread(Vibration vibration) {
            this$0 = VibratorService.this;
            super();
            mVibration = vibration;
            mTmpWorkSource.set(vibration.mUid);
            mWakeLock.setWorkSource(mTmpWorkSource);
            mWakeLock.acquire();
        }
    }

    private class Vibration
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            LinkedList linkedlist = mVibrations;
            linkedlist;
            JVM INSTR monitorenter ;
            mVibrations.remove(this);
            if(this == mCurrentVibration) {
                doCancelVibrateLocked();
                startNextVibrationLocked();
            }
            return;
        }

        public boolean hasLongerTimeout(long l) {
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_2;
            if(mTimeout != 0L && mStartTime + mTimeout >= l + SystemClock.uptimeMillis())
                flag = true;
            return flag;
        }

        private final long mPattern[];
        private final int mRepeat;
        private final long mStartTime;
        private final long mTimeout;
        private final IBinder mToken;
        private final int mUid;
        final VibratorService this$0;






        Vibration(IBinder ibinder, long l, int i) {
            this(ibinder, l, null, 0, i);
        }

        private Vibration(IBinder ibinder, long l, long al[], int i, int j) {
            this$0 = VibratorService.this;
            super();
            mToken = ibinder;
            mTimeout = l;
            mStartTime = SystemClock.uptimeMillis();
            mPattern = al;
            mRepeat = i;
            mUid = j;
        }

        Vibration(IBinder ibinder, long al[], int i, int j) {
            this(ibinder, 0L, al, i, j);
        }
    }


    VibratorService(Context context) {
        mIntentReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                if(!intent.getAction().equals("android.intent.action.SCREEN_OFF"))
                    break MISSING_BLOCK_LABEL_101;
                LinkedList linkedlist = mVibrations;
                linkedlist;
                JVM INSTR monitorenter ;
                doCancelVibrateLocked();
                int i = mVibrations.size();
                for(int j = 0; j < i; j++)
                    unlinkVibration((Vibration)mVibrations.get(j));

                mVibrations.clear();
            }

            final VibratorService this$0;

             {
                this$0 = VibratorService.this;
                super();
            }
        };
        vibratorOff();
        mContext = context;
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "*vibrator*");
        mWakeLock.setReferenceCounted(true);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(mIntentReceiver, intentfilter);
    }

    private void doCancelVibrateLocked() {
        if(mThread != null) {
            synchronized(mThread) {
                mThread.mDone = true;
                mThread.notify();
            }
            mThread = null;
        }
        doVibratorOff();
        mH.removeCallbacks(mVibrationRunnable);
        return;
        exception;
        vibratethread;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private boolean doVibratorExists() {
        return vibratorExists();
    }

    private void doVibratorOff() {
        ArrayList arraylist = mInputDeviceVibrators;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mInputDeviceVibrators.size();
        if(i != 0) {
            for(int j = 0; j < i; j++)
                ((Vibrator)mInputDeviceVibrators.get(j)).cancel();

        } else {
            vibratorOff();
        }
        return;
    }

    private void doVibratorOn(long l) {
        ArrayList arraylist = mInputDeviceVibrators;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mInputDeviceVibrators.size();
        if(i != 0) {
            for(int j = 0; j < i; j++)
                ((Vibrator)mInputDeviceVibrators.get(j)).vibrate(l);

        } else {
            vibratorOn(l);
        }
        return;
    }

    private boolean isAll0(long al[]) {
        int i;
        int j;
        i = al.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_30;
        if(al[j] == 0L) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = true;
          goto _L4
    }

    private Vibration removeVibrationLocked(IBinder ibinder) {
        ListIterator listiterator = mVibrations.listIterator(0);
_L4:
        if(!listiterator.hasNext()) goto _L2; else goto _L1
_L1:
        Vibration vibration = (Vibration)listiterator.next();
        if(vibration.mToken != ibinder) goto _L4; else goto _L3
_L3:
        listiterator.remove();
        unlinkVibration(vibration);
_L6:
        return vibration;
_L2:
        if(mCurrentVibration != null && mCurrentVibration.mToken == ibinder) {
            unlinkVibration(mCurrentVibration);
            vibration = mCurrentVibration;
        } else {
            vibration = null;
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    private void startNextVibrationLocked() {
        if(mVibrations.size() <= 0) {
            mCurrentVibration = null;
        } else {
            mCurrentVibration = (Vibration)mVibrations.getFirst();
            startVibrationLocked(mCurrentVibration);
        }
    }

    private void startVibrationLocked(Vibration vibration) {
        if(vibration.mTimeout != 0L) {
            doVibratorOn(vibration.mTimeout);
            mH.postDelayed(mVibrationRunnable, vibration.mTimeout);
        } else {
            mThread = new VibrateThread(vibration);
            mThread.start();
        }
    }

    private void unlinkVibration(Vibration vibration) {
        if(vibration.mPattern != null)
            vibration.mToken.unlinkToDeath(vibration, 0);
    }

    private void updateInputDeviceVibrators() {
        boolean flag = true;
        LinkedList linkedlist = mVibrations;
        linkedlist;
        JVM INSTR monitorenter ;
        doCancelVibrateLocked();
        ArrayList arraylist = mInputDeviceVibrators;
        arraylist;
        JVM INSTR monitorenter ;
        mVibrateInputDevicesSetting = false;
        if(android.provider.Settings.System.getInt(mContext.getContentResolver(), "vibrate_input_devices") <= 0)
            flag = false;
        mVibrateInputDevicesSetting = flag;
_L1:
        if(mVibrateInputDevicesSetting) {
            if(!mInputDeviceListenerRegistered) {
                mInputDeviceListenerRegistered = true;
                mIm.registerInputDeviceListener(this, mH);
            }
        } else
        if(mInputDeviceListenerRegistered) {
            mInputDeviceListenerRegistered = false;
            mIm.unregisterInputDeviceListener(this);
        }
        mInputDeviceVibrators.clear();
        if(mVibrateInputDevicesSetting) {
            int ai[] = mIm.getInputDeviceIds();
            Exception exception;
            Exception exception1;
            android.provider.Settings.SettingNotFoundException settingnotfoundexception;
            for(int i = 0; i < ai.length; i++) {
                Vibrator vibrator = mIm.getInputDevice(ai[i]).getVibrator();
                if(vibrator.hasVibrator())
                    mInputDeviceVibrators.add(vibrator);
                break MISSING_BLOCK_LABEL_202;
            }

        }
        break MISSING_BLOCK_LABEL_187;
        exception1;
        throw exception1;
        exception;
        throw exception;
        arraylist;
        JVM INSTR monitorexit ;
        startNextVibrationLocked();
        linkedlist;
        JVM INSTR monitorexit ;
        return;
        settingnotfoundexception;
          goto _L1
    }

    static native boolean vibratorExists();

    static native void vibratorOff();

    static native void vibratorOn(long l);

    public void cancelVibrate(IBinder ibinder) {
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "cancelVibrate");
        l = Binder.clearCallingIdentity();
        synchronized(mVibrations) {
            if(removeVibrationLocked(ibinder) == mCurrentVibration) {
                doCancelVibrateLocked();
                startNextVibrationLocked();
            }
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception1;
        linkedlist;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public boolean hasVibrator() {
        return doVibratorExists();
    }

    public void onInputDeviceAdded(int i) {
        updateInputDeviceVibrators();
    }

    public void onInputDeviceChanged(int i) {
        updateInputDeviceVibrators();
    }

    public void onInputDeviceRemoved(int i) {
        updateInputDeviceVibrators();
    }

    public void systemReady() {
        mIm = (InputManager)mContext.getSystemService("input");
        mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("vibrate_input_devices"), true, new ContentObserver(mH) {

            public void onChange(boolean flag) {
                updateInputDeviceVibrators();
            }

            final VibratorService this$0;

             {
                this$0 = VibratorService.this;
                super(handler);
            }
        });
        updateInputDeviceVibrators();
    }

    public void vibrate(long l, IBinder ibinder) {
        int i;
        if(mContext.checkCallingOrSelfPermission("android.permission.VIBRATE") != 0)
            throw new SecurityException("Requires VIBRATE permission");
        i = Binder.getCallingUid();
        if(l > 0L && (mCurrentVibration == null || !mCurrentVibration.hasLongerTimeout(l))) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Vibration vibration = new Vibration(ibinder, l, i);
        LinkedList linkedlist = mVibrations;
        linkedlist;
        JVM INSTR monitorenter ;
        removeVibrationLocked(ibinder);
        doCancelVibrateLocked();
        mCurrentVibration = vibration;
        startVibrationLocked(vibration);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void vibratePattern(long al[], int i, IBinder ibinder) {
        int j;
        long l;
        if(mContext.checkCallingOrSelfPermission("android.permission.VIBRATE") != 0)
            throw new SecurityException("Requires VIBRATE permission");
        j = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        if(al == null) goto _L2; else goto _L1
_L1:
        if(al.length == 0 || isAll0(al)) goto _L2; else goto _L3
_L3:
        int k = al.length;
        if(i < k && ibinder != null) goto _L4; else goto _L2
_L2:
        Binder.restoreCallingIdentity(l);
        return;
_L4:
        Vibration vibration = new Vibration(ibinder, al, i, j);
        ibinder.linkToDeath(vibration, 0);
        LinkedList linkedlist = mVibrations;
        linkedlist;
        JVM INSTR monitorenter ;
        removeVibrationLocked(ibinder);
        doCancelVibrateLocked();
        if(i < 0)
            break MISSING_BLOCK_LABEL_160;
        mVibrations.addFirst(vibration);
        startNextVibrationLocked();
_L5:
        linkedlist;
        JVM INSTR monitorexit ;
          goto _L2
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        RemoteException remoteexception;
        remoteexception;
          goto _L2
        mCurrentVibration = vibration;
        startVibrationLocked(vibration);
          goto _L5
    }

    private static final String TAG = "VibratorService";
    private final Context mContext;
    private Vibration mCurrentVibration;
    private final Handler mH = new Handler();
    private InputManager mIm;
    private boolean mInputDeviceListenerRegistered;
    private final ArrayList mInputDeviceVibrators = new ArrayList();
    BroadcastReceiver mIntentReceiver;
    volatile VibrateThread mThread;
    private final WorkSource mTmpWorkSource = new WorkSource();
    private boolean mVibrateInputDevicesSetting;
    private final Runnable mVibrationRunnable = new Runnable() {

        public void run() {
            LinkedList linkedlist = mVibrations;
            linkedlist;
            JVM INSTR monitorenter ;
            doCancelVibrateLocked();
            startNextVibrationLocked();
            return;
        }

        final VibratorService this$0;

             {
                this$0 = VibratorService.this;
                super();
            }
    };
    private final LinkedList mVibrations = new LinkedList();
    private final android.os.PowerManager.WakeLock mWakeLock;









}
