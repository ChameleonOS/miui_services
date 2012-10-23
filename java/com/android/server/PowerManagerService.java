// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.*;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.*;
import android.os.*;
import android.util.*;
import android.view.WindowManagerPolicy;
import com.android.internal.app.IBatteryStats;
import com.android.server.am.BatteryStatsService;
import com.android.server.pm.ShutdownThread;
import java.io.*;
import java.util.*;

// Referenced classes of package com.android.server:
//            Watchdog, BatteryService, LightsService

public class PowerManagerService extends android.os.IPowerManager.Stub
    implements LocalPowerManager, Watchdog.Monitor {
    private class LockList extends ArrayList {

        void addLock(WakeLock wakelock) {
            if(getIndex(wakelock.binder) < 0)
                add(wakelock);
        }

        int gatherState() {
            int i = 0;
            int j = size();
            for(int k = 0; k < j; k++) {
                WakeLock wakelock = (WakeLock)get(k);
                if(wakelock.activated && isScreenLock(wakelock.flags))
                    i |= wakelock.minState;
            }

            return i;
        }

        int getIndex(IBinder ibinder) {
            int i;
            int j;
            i = size();
            j = 0;
_L3:
            if(j >= i)
                break MISSING_BLOCK_LABEL_35;
            if(((WakeLock)get(j)).binder != ibinder) goto _L2; else goto _L1
_L1:
            return j;
_L2:
            j++;
              goto _L3
            j = -1;
              goto _L1
        }

        int reactivateScreenLocksLocked() {
            int i = 0;
            int j = size();
            for(int k = 0; k < j; k++) {
                WakeLock wakelock = (WakeLock)get(k);
                if(isScreenLock(wakelock.flags)) {
                    wakelock.activated = true;
                    i |= wakelock.minState;
                }
            }

            mProxIgnoredBecauseScreenTurnedOff = false;
            return i;
        }

        WakeLock removeLock(IBinder ibinder) {
            int i = getIndex(ibinder);
            WakeLock wakelock;
            if(i >= 0)
                wakelock = (WakeLock)remove(i);
            else
                wakelock = null;
            return wakelock;
        }

        final PowerManagerService this$0;

        private LockList() {
            this$0 = PowerManagerService.this;
            super();
        }

    }

    class ScreenBrightnessAnimator extends HandlerThread {

        private void animateInternal(int i, boolean flag, int j) {
            int k = 0;
            this;
            JVM INSTR monitorenter ;
            if(currentValue == endValue) goto _L2; else goto _L1
_L1:
            int l = (int)(SystemClock.elapsedRealtime() - startTimeMillis);
            if(l >= duration) goto _L4; else goto _L3
_L3:
            int i1;
            int k1;
            k1 = endValue - startValue;
            i1 = Math.min(255, Math.max(0, startValue + (k1 * l) / duration));
            if(j <= 0 || i1 != currentValue) goto _L6; else goto _L5
_L6:
            int l1 = endSensorValue - startSensorValue;
            mHighestLightSensorValue = startSensorValue + (l1 * l) / duration;
_L11:
            if(!flag || mHeadless || mAnimateScreenLights) goto _L8; else goto _L7
_L7:
            if(mScreenOffReason != 4) goto _L10; else goto _L9
_L9:
            mScreenBrightnessHandler.obtainMessage(11, k, 0).sendToTarget();
_L8:
            mScreenBrightnessHandler.removeMessages(10);
            Message message = mScreenBrightnessHandler.obtainMessage(10, i, i1);
            mScreenBrightnessHandler.sendMessageDelayed(message, j);
_L2:
            this;
            JVM INSTR monitorexit ;
            return;
_L4:
            i1 = endValue;
            mHighestLightSensorValue = endSensorValue;
            if(endValue > 0)
                mInitialAnimation = false;
              goto _L11
            exception;
            throw exception;
_L10:
            j1 = mAnimationSetting;
            k = j1;
              goto _L9
_L5:
            int i2 = duration / Math.abs(k1);
            j = Math.min(duration - l, i2);
            Exception exception;
            int j1;
            byte byte0;
            if(k1 < 0)
                byte0 = -1;
            else
                byte0 = 1;
            i1 += byte0;
            if(true) goto _L6; else goto _L12
_L12:
        }

        public void animateTo(int i, int j, int k) {
            animateTo(i, mHighestLightSensorValue, j, k);
        }

        public void animateTo(int i, int j, int k, int l) {
            boolean flag = true;
            this;
            JVM INSTR monitorenter ;
            if((k & 2) == 0) {
                if((k & 4) != 0)
                    mButtonLight.setBrightness(i);
                if((k & 8) != 0)
                    mKeyboardLight.setBrightness(i);
            } else {
                if(isAnimating() && (k ^ currentMask) != 0)
                    cancelAnimation();
                if(mInitialAnimation) {
                    l = 0;
                    if(i > 0)
                        mInitialAnimation = false;
                }
                startValue = currentValue;
                endValue = i;
                startSensorValue = mHighestLightSensorValue;
                endSensorValue = j;
                currentMask = k;
                duration = (int)(mWindowScaleAnimation * (float)l);
                startTimeMillis = SystemClock.elapsedRealtime();
                if(i != currentValue) {
                    boolean flag1;
                    if((k & 3) != 0)
                        flag1 = flag;
                    else
                        flag1 = false;
                    if(endValue != 0)
                        flag = false;
                    if(flag && flag1) {
                        mScreenBrightnessHandler.removeCallbacksAndMessages(null);
                        screenOffFinishedAnimatingLocked(mScreenOffReason);
                        duration = 200;
                    }
                    if(flag1)
                        animateInternal(k, flag, 0);
                }
            }
            return;
        }

        public void cancelAnimation() {
            animateTo(endValue, currentMask, 0);
        }

        public void dump(PrintWriter printwriter, String s) {
            printwriter.println(s);
            printwriter.println((new StringBuilder()).append("  animating: start:").append(startValue).append(", end:").append(endValue).append(", duration:").append(duration).append(", current:").append(currentValue).toString());
            printwriter.println((new StringBuilder()).append("  startSensorValue:").append(startSensorValue).append(" endSensorValue:").append(endSensorValue).toString());
            printwriter.println((new StringBuilder()).append("  startTimeMillis:").append(startTimeMillis).append(" now:").append(SystemClock.elapsedRealtime()).toString());
            printwriter.println((new StringBuilder()).append("  currentMask:").append(PowerManagerService.dumpPowerState(currentMask)).toString());
        }

        public int getCurrentBrightness() {
            this;
            JVM INSTR monitorenter ;
            int i = currentValue;
            return i;
        }

        public boolean isAnimating() {
            this;
            JVM INSTR monitorenter ;
            boolean flag;
            if(currentValue != endValue)
                flag = true;
            else
                flag = false;
            return flag;
        }

        protected void onLooperPrepared() {
            mScreenBrightnessHandler = new Handler() {

                public void handleMessage(Message message) {
                    int i;
                    if(mAutoBrightessEnabled && !mInitialAnimation)
                        i = 1;
                    else
                        i = 0;
                    if(message.what != 10) goto _L2; else goto _L1
_L1:
                    int k = message.arg1;
                    int l = message.arg2;
                    long l1 = SystemClock.uptimeMillis();
                    if((k & 2) != 0)
                        mLcdLight.setBrightness(l, i);
                    long l2 = SystemClock.uptimeMillis() - l1;
                    if((k & 4) != 0)
                        mButtonLight.setBrightness(l);
                    if((k & 8) != 0)
                        mKeyboardLight.setBrightness(l);
                    if(l2 > 100L)
                        Slog.e("PowerManagerService", (new StringBuilder()).append("Excessive delay setting brightness: ").append(l2).append("ms, mask=").append(k).toString());
                    byte byte0;
                    if(l2 < 16L)
                        byte0 = 16;
                    else
                        byte0 = 1;
                    this;
                    JVM INSTR monitorenter ;
                    currentValue = l;
                    this;
                    JVM INSTR monitorexit ;
                    animateInternal(k, false, byte0);
_L4:
                    return;
                    Exception exception;
                    exception;
                    this;
                    JVM INSTR monitorexit ;
                    throw exception;
_L2:
                    if(message.what == 11) {
                        int j = message.arg1;
                        nativeStartSurfaceFlingerAnimation(j);
                    }
                    if(true) goto _L4; else goto _L3
_L3:
                }

                final ScreenBrightnessAnimator this$1;

                 {
                    this$1 = ScreenBrightnessAnimator.this;
                    super();
                }
            };
            this;
            JVM INSTR monitorenter ;
            mInitComplete = true;
            notifyAll();
            return;
        }

        static final int ANIMATE_LIGHTS = 10;
        static final int ANIMATE_POWER_OFF = 11;
        private int currentMask;
        volatile int currentValue;
        private int duration;
        volatile int endSensorValue;
        volatile int endValue;
        private final String prefix;
        volatile int startSensorValue;
        private long startTimeMillis;
        volatile int startValue;
        final PowerManagerService this$0;



        public ScreenBrightnessAnimator(String s, int i) {
            this$0 = PowerManagerService.this;
            super(s, i);
            prefix = s;
        }
    }

    private class TimeoutTask
        implements Runnable {

        public void run() {
            LockList locklist = mLocks;
            locklist;
            JVM INSTR monitorenter ;
            if(nextState != -1) goto _L2; else goto _L1
_L2:
            long l;
            mUserState = nextState;
            setPowerState(nextState | mWakeLockState);
            l = SystemClock.uptimeMillis();
            nextState;
            JVM INSTR tableswitch 1 3: default 92
        //                       1 129
        //                       2 92
        //                       3 102;
               goto _L3 _L4 _L3 _L5
_L3:
            locklist;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
            Exception exception;
            exception;
            throw exception;
_L5:
            if(mDimDelay >= 0) {
                setTimeoutLocked(l, remainingTimeoutOverride, 1);
                continue; /* Loop/switch isn't completed */
            }
_L4:
            setTimeoutLocked(l, remainingTimeoutOverride, 0);
            if(true) goto _L3; else goto _L1
_L1:
        }

        int nextState;
        long remainingTimeoutOverride;
        final PowerManagerService this$0;

        private TimeoutTask() {
            this$0 = PowerManagerService.this;
            super();
        }

    }

    private class PokeLock
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            setPokeLock(0, binder, tag);
        }

        boolean awakeOnSet;
        IBinder binder;
        int pokey;
        String tag;
        final PowerManagerService this$0;

        PokeLock(int i, IBinder ibinder, String s) {
            this$0 = PowerManagerService.this;
            super();
            pokey = i;
            binder = ibinder;
            tag = s;
            ibinder.linkToDeath(this, 0);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            binderDied();
              goto _L1
        }
    }

    private class WakeLock
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            LockList locklist = mLocks;
            locklist;
            JVM INSTR monitorenter ;
            releaseWakeLockLocked(binder, 0, true);
            return;
        }

        boolean activated;
        final IBinder binder;
        final int flags;
        int minState;
        final int monitorType;
        final int pid;
        final String tag;
        final PowerManagerService this$0;
        final int uid;
        WorkSource ws;

        WakeLock(int i, IBinder ibinder, String s, int j, int k) {
            this$0 = PowerManagerService.this;
            super();
            activated = true;
            flags = i;
            binder = ibinder;
            tag = s;
            int l;
            if(j == MY_UID)
                l = 1000;
            else
                l = j;
            uid = l;
            pid = k;
            if(j != MY_UID || !"KEEP_SCREEN_ON_FLAG".equals(tag) && !"KeyInputQueue".equals(tag)) {
                int i1;
                if((i & 0x3f) == 1)
                    i1 = 0;
                else
                    i1 = 1;
                monitorType = i1;
            } else {
                monitorType = -1;
            }
            ibinder.linkToDeath(this, 0);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            binderDied();
              goto _L1
        }
    }

    private final class DockReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            int i = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
            dockStateChanged(i);
        }

        final PowerManagerService this$0;

        private DockReceiver() {
            this$0 = PowerManagerService.this;
            super();
        }

    }

    private final class BootCompletedReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            bootCompleted();
        }

        final PowerManagerService this$0;

        private BootCompletedReceiver() {
            this$0 = PowerManagerService.this;
            super();
        }

    }

    private final class BatteryReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            LockList locklist = mLocks;
            locklist;
            JVM INSTR monitorenter ;
            boolean flag;
            flag = mIsPowered;
            mIsPowered = mBatteryService.isPowered();
            if(mIsPowered == flag) goto _L2; else goto _L1
_L1:
            updateWakeLockLocked();
            LockList locklist1 = mLocks;
            locklist1;
            JVM INSTR monitorenter ;
            if(!flag)
                break MISSING_BLOCK_LABEL_95;
            if((1 & mPowerState) == 0 && !mUnplugTurnsOnScreen)
                break MISSING_BLOCK_LABEL_102;
            forceUserActivityLocked();
            locklist1;
            JVM INSTR monitorexit ;
_L2:
            locklist;
            JVM INSTR monitorexit ;
            return;
            Exception exception1;
            exception1;
            locklist1;
            JVM INSTR monitorexit ;
            throw exception1;
            Exception exception;
            exception;
            throw exception;
        }

        final PowerManagerService this$0;

        private BatteryReceiver() {
            this$0 = PowerManagerService.this;
            super();
        }

    }

    private class UnsynchronizedWakeLock {

        public void acquire() {
            long l;
            if(mRefCounted) {
                int i = mCount;
                mCount = i + 1;
                if(i != 0)
                    break MISSING_BLOCK_LABEL_73;
            }
            l = Binder.clearCallingIdentity();
            acquireWakeLockLocked(mFlags, mToken, MY_UID, MY_PID, mTag, null);
            mHeld = true;
            Binder.restoreCallingIdentity(l);
            return;
            Exception exception;
            exception;
            Binder.restoreCallingIdentity(l);
            throw exception;
        }

        public boolean isHeld() {
            return mHeld;
        }

        public void release() {
label0:
            {
                if(mRefCounted) {
                    int i = -1 + mCount;
                    mCount = i;
                    if(i != 0)
                        break label0;
                }
                releaseWakeLockLocked(mToken, 0, false);
                mHeld = false;
            }
            if(mCount < 0)
                throw new RuntimeException((new StringBuilder()).append("WakeLock under-locked ").append(mTag).toString());
            else
                return;
        }

        public String toString() {
            return (new StringBuilder()).append("UnsynchronizedWakeLock(mFlags=0x").append(Integer.toHexString(mFlags)).append(" mCount=").append(mCount).append(" mHeld=").append(mHeld).append(")").toString();
        }

        int mCount;
        int mFlags;
        boolean mHeld;
        boolean mRefCounted;
        String mTag;
        IBinder mToken;
        final PowerManagerService this$0;

        UnsynchronizedWakeLock(int i, String s, boolean flag) {
            this$0 = PowerManagerService.this;
            super();
            mCount = 0;
            mFlags = i;
            mTag = s;
            mToken = new Binder();
            mRefCounted = flag;
        }
    }


    PowerManagerService() {
        mShortKeylightDelay = 6000;
        mAnimateScreenLights = true;
        mDoneBooting = false;
        mBootCompleted = false;
        mHeadless = false;
        mStayOnConditions = 0;
        int ai[] = new int[3];
        ai[0] = -1;
        ai[1] = -1;
        ai[2] = -1;
        mBroadcastQueue = ai;
        mPreparingForScreenOn = false;
        mSkippedScreenOn = false;
        mInitialized = false;
        mPartialCount = 0;
        mKeyboardVisible = false;
        mUserActivityAllowed = true;
        mProximityWakeLockCount = 0;
        mProximitySensorEnabled = false;
        mProximitySensorActive = false;
        mProximityPendingValue = -1;
        mMaximumScreenOffTimeout = 0x7fffffff;
        mLastEventTime = 0L;
        mWaitingForFirstLightSensor = false;
        mIsPowered = false;
        mLightSensorValue = -1F;
        mProxIgnoredBecauseScreenTurnedOff = false;
        mHighestLightSensorValue = -1;
        mLightSensorPendingDecrease = false;
        mLightSensorPendingIncrease = false;
        mLightSensorPendingValue = -1F;
        mLightSensorAdjustSetting = 0.0F;
        mLightSensorScreenBrightness = -1;
        mLightSensorButtonBrightness = -1;
        mLightSensorKeyboardBrightness = -1;
        mDimScreen = true;
        mIsDocked = false;
        mPokey = 0;
        mPokeAwakeOnSet = false;
        mInitComplete = false;
        mScreenBrightnessSetting = 192;
        mScreenBrightnessOverride = -1;
        mButtonBrightnessOverride = -1;
        mAnimationSetting = 16;
        mScreenOnListener = new android.view.WindowManagerPolicy.ScreenOnListener() {

            public void onScreenOn() {
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                if(mPreparingForScreenOn) {
                    mPreparingForScreenOn = false;
                    updateLightsLocked(mPowerState, 1);
                    Object aobj[] = new Object[2];
                    aobj[0] = Integer.valueOf(4);
                    aobj[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
                    EventLog.writeEvent(2727, aobj);
                    mBroadcastWakeLock.release();
                }
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mNotificationTask = new Runnable() {

            public void run() {
_L2:
                int i;
                int j;
                WindowManagerPolicy windowmanagerpolicy;
                synchronized(mLocks) {
                    i = mBroadcastQueue[0];
                    j = mBroadcastWhy[0];
                    for(int k = 0; k < 2; k++) {
                        mBroadcastQueue[k] = mBroadcastQueue[k + 1];
                        mBroadcastWhy[k] = mBroadcastWhy[k + 1];
                    }

                    windowmanagerpolicy = getPolicyLocked();
                    if(i == 1 && !mPreparingForScreenOn) {
                        mPreparingForScreenOn = true;
                        mBroadcastWakeLock.acquire();
                        EventLog.writeEvent(2725, mBroadcastWakeLock.mCount);
                    }
                }
                if(i != 1)
                    break MISSING_BLOCK_LABEL_324;
                mScreenOnStart = SystemClock.uptimeMillis();
                windowmanagerpolicy.screenTurningOn(mScreenOnListener);
                RemoteException remoteexception;
                LockList locklist1;
                Object aobj[];
                LockList locklist2;
                Object aobj1[];
                try {
                    ActivityManagerNative.getDefault().wakingUp();
                }
                catch(RemoteException remoteexception1) { }
                if(mContext != null && ActivityManagerNative.isSystemReady()) {
                    mContext.sendOrderedBroadcast(mScreenOnIntent, null, mScreenOnBroadcastDone, mHandler, 0, null, null);
                    continue; /* Loop/switch isn't completed */
                }
                break MISSING_BLOCK_LABEL_248;
                exception;
                locklist;
                JVM INSTR monitorexit ;
                throw exception;
                locklist2 = mLocks;
                locklist2;
                JVM INSTR monitorenter ;
                aobj1 = new Object[2];
                aobj1[0] = Integer.valueOf(2);
                aobj1[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
                EventLog.writeEvent(2727, aobj1);
                mBroadcastWakeLock.release();
                continue; /* Loop/switch isn't completed */
                if(i != 0)
                    break; /* Loop/switch isn't completed */
                mScreenOffStart = SystemClock.uptimeMillis();
                windowmanagerpolicy.screenTurnedOff(j);
                try {
                    ActivityManagerNative.getDefault().goingToSleep();
                }
                // Misplaced declaration of an exception variable
                catch(RemoteException remoteexception) { }
                if(mContext != null && ActivityManagerNative.isSystemReady()) {
                    mContext.sendOrderedBroadcast(mScreenOffIntent, null, mScreenOffBroadcastDone, mHandler, 0, null, null);
                    continue; /* Loop/switch isn't completed */
                }
                locklist1 = mLocks;
                locklist1;
                JVM INSTR monitorenter ;
                aobj = new Object[2];
                aobj[0] = Integer.valueOf(3);
                aobj[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
                EventLog.writeEvent(2727, aobj);
                updateLightsLocked(mPowerState, 1);
                mBroadcastWakeLock.release();
                if(true) goto _L2; else goto _L1
_L1:
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mScreenOnBroadcastDone = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                Object aobj[] = new Object[3];
                aobj[0] = Integer.valueOf(1);
                aobj[1] = Long.valueOf(SystemClock.uptimeMillis() - mScreenOnStart);
                aobj[2] = Integer.valueOf(mBroadcastWakeLock.mCount);
                EventLog.writeEvent(2726, aobj);
                mBroadcastWakeLock.release();
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mScreenOffBroadcastDone = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                Object aobj[] = new Object[3];
                aobj[0] = Integer.valueOf(0);
                aobj[1] = Long.valueOf(SystemClock.uptimeMillis() - mScreenOffStart);
                aobj[2] = Integer.valueOf(mBroadcastWakeLock.mCount);
                EventLog.writeEvent(2726, aobj);
                mBroadcastWakeLock.release();
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mForceReenableScreenTask = new Runnable() {

            public void run() {
                forceReenableScreen();
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mReleaseProximitySensorRunnable = new Runnable() {

            public void run() {
                mContext.sendBroadcast(new Intent("miui.intent.action.RELEASE_PROXIMITY_SENSOR"));
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mProximityTask = new Runnable() {

            public void run() {
                boolean flag = true;
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                if(mProximityPendingValue != -1) {
                    PowerManagerService powermanagerservice = PowerManagerService.this;
                    if(mProximityPendingValue != flag)
                        flag = false;
                    powermanagerservice.proximityChangedLocked(flag);
                    mProximityPendingValue = -1;
                }
                if(mProximityPartialLock.isHeld())
                    mProximityPartialLock.release();
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mAutoBrightnessTask = new Runnable() {

            public void run() {
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                if(mLightSensorPendingDecrease || mLightSensorPendingIncrease) {
                    int i = (int)mLightSensorPendingValue;
                    mLightSensorPendingDecrease = false;
                    mLightSensorPendingIncrease = false;
                    lightSensorChangedLocked(i, false);
                }
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mInitialAnimation = true;
        mProximityListener = new SensorEventListener() {

            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorevent) {
                long l1 = SystemClock.elapsedRealtime();
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                float f = sensorevent.values[0];
                long l2 = l1 - mLastProximityEventTime;
                mLastProximityEventTime = l1;
                mHandler.removeCallbacks(mProximityTask);
                boolean flag = false;
                boolean flag1;
                if((double)f >= 0.0D && f < 5F && f < mProximitySensor.getMaximumRange())
                    flag1 = true;
                else
                    flag1 = false;
                if(l2 < 1000L) {
                    PowerManagerService powermanagerservice = PowerManagerService.this;
                    Exception exception;
                    boolean flag2;
                    int i;
                    if(flag1)
                        i = 1;
                    else
                        i = 0;
                    powermanagerservice.mProximityPendingValue = i;
                    mHandler.postDelayed(mProximityTask, 1000L - l2);
                    flag = true;
                } else {
                    mProximityPendingValue = -1;
                    proximityChangedLocked(flag1);
                }
                flag2 = mProximityPartialLock.isHeld();
                if(flag2 || !flag) goto _L2; else goto _L1
_L1:
                mProximityPartialLock.acquire();
_L4:
                locklist;
                JVM INSTR monitorexit ;
                return;
                exception;
                throw exception;
_L2:
                if(!flag2 || flag) goto _L4; else goto _L3
_L3:
                mProximityPartialLock.release();
                  goto _L4
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        mLightListener = new SensorEventListener() {

            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorevent) {
                LockList locklist = mLocks;
                locklist;
                JVM INSTR monitorenter ;
                if(!isScreenTurningOffLocked()) {
                    handleLightSensorValue((int)sensorevent.values[0], mWaitingForFirstLightSensor);
                    if(mWaitingForFirstLightSensor && !mPreparingForScreenOn)
                        mWaitingForFirstLightSensor = false;
                }
                return;
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super();
            }
        };
        long l = Binder.clearCallingIdentity();
        Binder.restoreCallingIdentity(l);
        mPowerState = 0;
        mUserState = 0;
        Watchdog.getInstance().addMonitor(this);
        nativeInit();
    }

    private int applyButtonState(int i) {
        int j = -1;
        if((i & 0x10) == 0) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        if(mButtonBrightnessOverride < 0) goto _L4; else goto _L3
_L3:
        j = mButtonBrightnessOverride;
_L6:
        if(j <= 0)
            break; /* Loop/switch isn't completed */
        i |= 4;
        continue; /* Loop/switch isn't completed */
_L4:
        if(mLightSensorButtonBrightness >= 0 && mUseSoftwareAutoBrightness)
            j = mLightSensorButtonBrightness;
        if(true) goto _L6; else goto _L5
_L5:
        if(j == 0)
            i &= -5;
        if(true) goto _L1; else goto _L7
_L7:
    }

    private int applyKeyboardState(int i) {
        int j = -1;
        if((i & 0x10) == 0) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        if(mKeyboardVisible) goto _L4; else goto _L3
_L3:
        j = 0;
_L6:
        if(j <= 0)
            break; /* Loop/switch isn't completed */
        i |= 8;
        continue; /* Loop/switch isn't completed */
_L4:
        if(mButtonBrightnessOverride >= 0)
            j = mButtonBrightnessOverride;
        else
        if(mLightSensorKeyboardBrightness >= 0 && mUseSoftwareAutoBrightness)
            j = mLightSensorKeyboardBrightness;
        if(true) goto _L6; else goto _L5
_L5:
        if(j == 0)
            i &= -9;
        if(true) goto _L1; else goto _L7
_L7:
    }

    private boolean batteryIsLow() {
        boolean flag;
        if(!mIsPowered && mBatteryService.getBatteryLevel() <= 10)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void cancelTimerLocked() {
        mHandler.removeCallbacks(mTimeoutTask);
        mTimeoutTask.nextState = -1;
    }

    private void disableProximityLockLocked() {
        long l;
        if(!mProximitySensorEnabled)
            break MISSING_BLOCK_LABEL_82;
        l = Binder.clearCallingIdentity();
        mSensorManager.unregisterListener(mProximityListener);
        mHandler.removeCallbacks(mProximityTask);
        if(mProximityPartialLock.isHeld())
            mProximityPartialLock.release();
        mProximitySensorEnabled = false;
        Binder.restoreCallingIdentity(l);
        if(mProximitySensorActive) {
            mProximitySensorActive = false;
            if(!mProxIgnoredBecauseScreenTurnedOff)
                forceUserActivityLocked();
        }
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void dockStateChanged(int i) {
        boolean flag = false;
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(i != 0)
            flag = true;
        mIsDocked = flag;
        if(mIsDocked)
            mHighestLightSensorValue = -1;
        if((1 & mPowerState) != 0) {
            int j = (int)mLightSensorValue;
            mLightSensorValue = -1F;
            lightSensorChangedLocked(j, false);
        }
        return;
    }

    private static String dumpPowerState(int i) {
        StringBuilder stringbuilder = new StringBuilder();
        String s;
        StringBuilder stringbuilder1;
        String s1;
        StringBuilder stringbuilder2;
        String s2;
        StringBuilder stringbuilder3;
        String s3;
        if((i & 8) != 0)
            s = "KEYBOARD_BRIGHT_BIT ";
        else
            s = "";
        stringbuilder1 = stringbuilder.append(s);
        if((i & 2) != 0)
            s1 = "SCREEN_BRIGHT_BIT ";
        else
            s1 = "";
        stringbuilder2 = stringbuilder1.append(s1);
        if((i & 1) != 0)
            s2 = "SCREEN_ON_BIT ";
        else
            s2 = "";
        stringbuilder3 = stringbuilder2.append(s2);
        if((i & 0x10) != 0)
            s3 = "BATTERY_LOW_BIT ";
        else
            s3 = "";
        return stringbuilder3.append(s3).toString();
    }

    private void enableLightSensorLocked(boolean flag) {
        if(!mAutoBrightessEnabled)
            flag = false;
        if(mSensorManager == null || mLightSensorEnabled == flag) goto _L2; else goto _L1
_L1:
        long l;
        mLightSensorEnabled = flag;
        l = Binder.clearCallingIdentity();
        if(!flag) goto _L4; else goto _L3
_L3:
        mHighestLightSensorValue = -1;
        int i = (int)mLightSensorValue;
        if(i >= 0) {
            mLightSensorValue = -1F;
            handleLightSensorValue(i, true);
        }
        mSensorManager.registerListener(mLightListener, mLightSensor, 0xf4240);
_L6:
        Binder.restoreCallingIdentity(l);
_L2:
        return;
_L4:
        mSensorManager.unregisterListener(mLightListener);
        mHandler.removeCallbacks(mAutoBrightnessTask);
        mLightSensorPendingDecrease = false;
        mLightSensorPendingIncrease = false;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void enableProximityLockLocked() {
        if(mProximitySensorEnabled) goto _L2; else goto _L1
_L1:
        long l = Binder.clearCallingIdentity();
        mSensorManager.registerListener(mProximityListener, mProximitySensor, 3);
        mProximitySensorEnabled = true;
        Binder.restoreCallingIdentity(l);
_L4:
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L2:
        proximityChangedLocked(mProximitySensorActive);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void forceReenableScreen() {
        if(!mPreventScreenOn) {
            Slog.w("PowerManagerService", "forceReenableScreen: mPreventScreenOn is false, nothing to do");
        } else {
            Slog.w("PowerManagerService", "App called preventScreenOn(true) but didn't promptly reenable the screen! Forcing the screen back on...");
            preventScreenOn(false);
        }
    }

    private void forceUserActivityLocked() {
        if(isScreenTurningOffLocked())
            mScreenBrightnessAnimator.cancelAnimation();
        boolean flag = mUserActivityAllowed;
        mUserActivityAllowed = true;
        userActivity(SystemClock.uptimeMillis(), false);
        mUserActivityAllowed = flag;
    }

    private int getAutoBrightnessValue(int i, int ai[]) {
        int j = 0;
_L6:
        int l;
        int i1;
        int j1;
        float f;
        if(j < mAutoBrightnessLevels.length && i >= mAutoBrightnessLevels[j])
            break MISSING_BLOCK_LABEL_232;
        l = ai[0];
        i1 = ai[mAutoBrightnessLevels.length];
        j1 = 20 + (i1 - l);
        f = (float)(10 + (ai[j] - l)) / (float)j1;
        if(mLightSensorAdjustSetting <= 0.0F || mLightSensorAdjustSetting > 1.0F) goto _L2; else goto _L1
_L1:
        int k;
        float f1 = (float)Math.sqrt(1.0F - mLightSensorAdjustSetting);
        if((double)f1 <= 1.0000000000000001E-05D)
            f = 1.0F;
        else
            f /= f1;
_L4:
        k = -10 + (int)((f + mLightSensorAdjustSetting / 8F) * (float)j1 + (float)l);
        if(k < l) {
            k = l;
            break MISSING_BLOCK_LABEL_229;
        }
        break MISSING_BLOCK_LABEL_197;
_L2:
        if(mLightSensorAdjustSetting >= 0.0F || mLightSensorAdjustSetting < -1F) goto _L4; else goto _L3
_L3:
        double d = Math.sqrt(1.0F + mLightSensorAdjustSetting);
        f *= (float)d;
          goto _L4
        if(k > i1)
            k = i1;
        break MISSING_BLOCK_LABEL_229;
        Exception exception;
        exception;
        Slog.e("PowerManagerService", "Values array must be non-empty and must be one element longer than the auto-brightness levels array.  Check config.xml.", exception);
        k = 255;
        return k;
        j++;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private int getPreferredBrightness() {
        int i;
        if(mScreenBrightnessOverride >= 0)
            i = mScreenBrightnessOverride;
        else
        if(mLightSensorScreenBrightness >= 0 && mUseSoftwareAutoBrightness && mAutoBrightessEnabled)
            i = mLightSensorScreenBrightness;
        else
            i = Math.max(mScreenBrightnessSetting, mScreenBrightnessDim);
        return i;
    }

    private void goToSleepLocked(long l, int i) {
        if(mLastEventTime <= l) {
            mLastEventTime = l;
            mWakeLockState = 0;
            int j = mLocks.size();
            int k = 0;
            boolean flag = false;
            int i1 = 0;
            while(i1 < j)  {
                WakeLock wakelock = (WakeLock)mLocks.get(i1);
                if(isScreenLock(wakelock.flags))
                    if((0x3f & wakelock.flags) == 32 && i == 4) {
                        flag = true;
                    } else {
                        ((WakeLock)mLocks.get(i1)).activated = false;
                        k++;
                    }
                i1++;
            }
            if(!flag)
                mProxIgnoredBecauseScreenTurnedOff = true;
            EventLog.writeEvent(2724, k);
            mStillNeedSleepNotification = true;
            mUserState = 0;
            setPowerState(0, false, i);
            cancelTimerLocked();
        }
    }

    private void handleLightSensorValue(int i, boolean flag) {
        boolean flag1;
        long l;
        flag1 = true;
        l = SystemClock.elapsedRealtime();
        if(mLightSensorValue != -1F && l >= mLastScreenOnTime + (long)mLightSensorWarmupTime && !mWaitingForFirstLightSensor) goto _L2; else goto _L1
_L1:
        mHandler.removeCallbacks(mAutoBrightnessTask);
        mLightSensorPendingDecrease = false;
        mLightSensorPendingIncrease = false;
        lightSensorChangedLocked(i, flag);
_L4:
        return;
_L2:
        if((float)i > mLightSensorValue && mLightSensorPendingDecrease || (float)i < mLightSensorValue && mLightSensorPendingIncrease || (float)i == mLightSensorValue || !mLightSensorPendingDecrease && !mLightSensorPendingIncrease) {
            mHandler.removeCallbacks(mAutoBrightnessTask);
            boolean flag2;
            if((float)i < mLightSensorValue)
                flag2 = flag1;
            else
                flag2 = false;
            mLightSensorPendingDecrease = flag2;
            if((float)i <= mLightSensorValue)
                flag1 = false;
            mLightSensorPendingIncrease = flag1;
            if(mLightSensorPendingDecrease || mLightSensorPendingIncrease) {
                mLightSensorPendingValue = i;
                mHandler.postDelayed(mAutoBrightnessTask, 2000L);
            }
        } else {
            mLightSensorPendingValue = i;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean isScreenLock(int i) {
        int j = i & 0x3f;
        boolean flag;
        if(j == 26 || j == 10 || j == 6 || j == 32)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isScreenTurningOffLocked() {
        boolean flag;
        if(mScreenBrightnessAnimator.isAnimating() && mScreenBrightnessAnimator.endValue == 0 && (2 & mScreenBrightnessAnimator.currentMask) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void lightSensorChangedLocked(int i, boolean flag) {
_L2:
        return;
        if((1 & mPowerState) == 0 || mLightSensorValue == (float)i) goto _L2; else goto _L1
_L1:
        mLightSensorValue = i;
        if((0x10 & mPowerState) != 0) goto _L2; else goto _L3
_L3:
        int j;
        int i1;
        j = getAutoBrightnessValue(i, mLcdBacklightValues);
        int k = getAutoBrightnessValue(i, mButtonBacklightValues);
        int l;
        if(mKeyboardVisible)
            l = getAutoBrightnessValue(i, mKeyboardBacklightValues);
        else
            l = 0;
        mLightSensorScreenBrightness = j;
        mLightSensorButtonBrightness = k;
        mLightSensorKeyboardBrightness = l;
        if(!mAutoBrightessEnabled || mScreenBrightnessOverride >= 0 || mSkippedScreenOn || mInitialAnimation) goto _L5; else goto _L4
_L4:
        if(!flag) goto _L7; else goto _L6
_L6:
        i1 = 4;
_L8:
        mScreenBrightnessAnimator.animateTo(j, i, 2, i1 * 16);
_L5:
        if(mButtonBrightnessOverride < 0)
            mButtonLight.setBrightness(k);
        if(mButtonBrightnessOverride < 0 || !mKeyboardVisible)
            mKeyboardLight.setBrightness(l);
          goto _L2
_L7:
        ScreenBrightnessAnimator screenbrightnessanimator = mScreenBrightnessAnimator;
        screenbrightnessanimator;
        JVM INSTR monitorenter ;
        if(mScreenBrightnessAnimator.currentValue <= j)
            i1 = 120;
        else
            i1 = 0x147ae1;
          goto _L8
    }

    private static String lockType(int i) {
        i;
        JVM INSTR lookupswitch 5: default 52
    //                   1: 79
    //                   6: 72
    //                   10: 65
    //                   26: 58
    //                   32: 86;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        String s = "???                           ";
_L8:
        return s;
_L5:
        s = "FULL_WAKE_LOCK                ";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "SCREEN_BRIGHT_WAKE_LOCK       ";
        continue; /* Loop/switch isn't completed */
_L3:
        s = "SCREEN_DIM_WAKE_LOCK          ";
        continue; /* Loop/switch isn't completed */
_L2:
        s = "PARTIAL_WAKE_LOCK             ";
        continue; /* Loop/switch isn't completed */
_L6:
        s = "PROXIMITY_SCREEN_OFF_WAKE_LOCK";
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static void lowLevelReboot(String s) throws IOException {
        nativeReboot(s);
    }

    public static void lowLevelShutdown() {
        nativeShutdown();
    }

    private static native void nativeAcquireWakeLock(int i, String s);

    private native void nativeInit();

    private static native void nativeReboot(String s) throws IOException;

    private static native void nativeReleaseWakeLock(String s);

    private native void nativeSetPowerState(boolean flag, boolean flag1);

    private static native int nativeSetScreenState(boolean flag);

    private static native void nativeShutdown();

    private native void nativeStartSurfaceFlingerAnimation(int i);

    private void proximityChangedLocked(boolean flag) {
        if(mProximitySensorEnabled) goto _L2; else goto _L1
_L1:
        Slog.d("PowerManagerService", "Ignoring proximity change after sensor is disabled");
_L4:
        return;
_L2:
        if(flag) {
            if(!mProxIgnoredBecauseScreenTurnedOff)
                goToSleepLocked(SystemClock.uptimeMillis(), 4);
            mProximitySensorActive = true;
        } else {
            mProximitySensorActive = false;
            if(!mProxIgnoredBecauseScreenTurnedOff)
                forceUserActivityLocked();
            if(mProximityWakeLockCount == 0)
                disableProximityLockLocked();
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void releaseProximitySensor(int i, int j) {
        if((i & 1) == 0) goto _L2; else goto _L1
_L1:
        mHandler.removeCallbacks(mReleaseProximitySensorRunnable);
_L4:
        return;
_L2:
        if(mBootCompleted && 4 != j)
            mHandler.post(mReleaseProximitySensorRunnable);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void releaseWakeLockLocked(IBinder ibinder, int i, boolean flag) {
        WakeLock wakelock = mLocks.removeLock(ibinder);
        if(wakelock != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!isScreenLock(wakelock.flags))
            break; /* Loop/switch isn't completed */
        if((0x3f & wakelock.flags) == 32) {
            mProximityWakeLockCount = -1 + mProximityWakeLockCount;
            if(mProximityWakeLockCount == 0 && (!mProximitySensorActive || (i & 1) == 0))
                disableProximityLockLocked();
        } else {
            mWakeLockState = mLocks.gatherState();
            if((0x20000000 & wakelock.flags) != 0)
                userActivity(SystemClock.uptimeMillis(), -1L, false, 0, false, true);
            setPowerState(mWakeLockState | mUserState);
        }
_L5:
        wakelock.binder.unlinkToDeath(wakelock, 0);
        noteStopWakeLocked(wakelock, wakelock.ws);
        if(true) goto _L1; else goto _L3
_L3:
        if((0x3f & wakelock.flags) != 1) goto _L5; else goto _L4
_L4:
        mPartialCount = -1 + mPartialCount;
        if(mPartialCount == 0)
            nativeReleaseWakeLock("PowerManagerService");
          goto _L5
    }

    private int screenOffFinishedAnimatingLocked(int i) {
        Object aobj[] = new Object[4];
        aobj[0] = Integer.valueOf(0);
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Long.valueOf(mTotalTouchDownTime);
        aobj[3] = Integer.valueOf(mTouchCycles);
        EventLog.writeEvent(2728, aobj);
        mLastTouchDown = 0L;
        int j = setScreenStateLocked(false);
        if(j == 0) {
            mScreenOffReason = i;
            sendNotificationLocked(false, i);
        }
        return j;
    }

    private void sendNotificationLocked(boolean flag, int i) {
        if(mInitialized) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!flag)
            mStillNeedSleepNotification = false;
        int j;
        for(j = 0; mBroadcastQueue[j] != -1; j++);
        int ai[] = mBroadcastQueue;
        int k;
        if(flag)
            k = 1;
        else
            k = 0;
        ai[j] = k;
        mBroadcastWhy[j] = i;
        if(j == 2) {
            if(!flag && mBroadcastWhy[0] > i)
                mBroadcastWhy[0] = i;
            int ai1[] = mBroadcastQueue;
            Object aobj[];
            int l;
            Object aobj1[];
            Object aobj2[];
            if(flag)
                l = 1;
            else
                l = 0;
            ai1[0] = l;
            mBroadcastQueue[1] = -1;
            mBroadcastQueue[2] = -1;
            aobj1 = new Object[2];
            aobj1[0] = Integer.valueOf(1);
            aobj1[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
            EventLog.writeEvent(2727, aobj1);
            mBroadcastWakeLock.release();
            aobj2 = new Object[2];
            aobj2[0] = Integer.valueOf(1);
            aobj2[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
            EventLog.writeEvent(2727, aobj2);
            mBroadcastWakeLock.release();
            j = 0;
        }
        if(j == 1 && !flag) {
            mBroadcastQueue[0] = -1;
            mBroadcastQueue[1] = -1;
            j = -1;
            aobj = new Object[2];
            aobj[0] = Integer.valueOf(1);
            aobj[1] = Integer.valueOf(mBroadcastWakeLock.mCount);
            EventLog.writeEvent(2727, aobj);
            mBroadcastWakeLock.release();
        }
        if(mSkippedScreenOn)
            updateLightsLocked(mPowerState, 1);
        if(j >= 0) {
            mBroadcastWakeLock.acquire();
            EventLog.writeEvent(2725, mBroadcastWakeLock.mCount);
            mHandler.post(mNotificationTask);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void setLightBrightness(int i, int j) {
        mScreenBrightnessAnimator.animateTo(j, i, 0);
    }

    private void setPowerState(int i) {
        setPowerState(i, false, 3);
    }

    private void setPowerState(int i, boolean flag, int j) {
        releaseProximitySensor(i, j);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        int k;
        boolean flag2;
        if(flag) {
            int j1 = i & 0xfffffff1;
            i = j1 | 0xe & mPowerState;
        }
        if(!mProximitySensorActive);
        Exception exception;
        int l;
        boolean flag1;
        boolean flag3;
        long l1;
        Exception exception1;
        RemoteException remoteexception;
        boolean flag4;
        int i1;
        Object aobj[];
        long l2;
        Exception exception2;
        RemoteException remoteexception1;
        if(batteryIsLow())
            k = i | 0x10;
        else
            k = i & 0xffffffef;
        l = mPowerState;
        if(k != l || !mInitialized) goto _L2; else goto _L1
_L2:
        if(!mBootCompleted && !mUseSoftwareAutoBrightness)
            k |= 0xf;
        if((1 & mPowerState) != 0)
            flag1 = true;
        else
            flag1 = false;
          goto _L3
_L17:
        if(mPowerState != k)
            flag3 = true;
        else
            flag3 = false;
        if(!flag3 || j != 3 || mPolicy == null || !mPolicy.isScreenSaverEnabled() || !mPolicy.startScreenSaver()) goto _L4; else goto _L1
        exception;
        throw exception;
_L15:
        flag2 = false;
        continue; /* Loop/switch isn't completed */
_L4:
        if(flag1 == flag2) goto _L6; else goto _L5
_L5:
        if(!flag2) goto _L8; else goto _L7
_L7:
        if(mStillNeedSleepNotification)
            sendNotificationLocked(false, 2);
        flag4 = true;
        if(mPreventScreenOn)
            flag4 = false;
        if(!flag4) goto _L10; else goto _L9
_L9:
        i1 = setScreenStateLocked(true);
        l2 = Binder.clearCallingIdentity();
        mBatteryStats.noteScreenBrightness(getPreferredBrightness());
        mBatteryStats.noteScreenOn();
        Binder.restoreCallingIdentity(l2);
_L11:
        mLastTouchDown = 0L;
        mTotalTouchDownTime = 0L;
        mTouchCycles = 0;
        aobj = new Object[4];
        aobj[0] = Integer.valueOf(1);
        aobj[1] = Integer.valueOf(j);
        aobj[2] = Long.valueOf(mTotalTouchDownTime);
        aobj[3] = Integer.valueOf(mTouchCycles);
        EventLog.writeEvent(2728, aobj);
        if(i1 == 0) {
            sendNotificationLocked(true, -1);
            if(flag3)
                updateLightsLocked(k, 0);
            mPowerState = 1 | mPowerState;
        }
_L12:
        mPowerState = 0xfffffff1 & mPowerState | k & 0xe;
        updateNativePowerStateLocked();
        locklist;
        JVM INSTR monitorexit ;
          goto _L1
        remoteexception1;
        Slog.w("PowerManagerService", "RemoteException calling noteScreenOn on BatteryStatsService", remoteexception1);
        Binder.restoreCallingIdentity(l2);
          goto _L11
        exception2;
        Binder.restoreCallingIdentity(l2);
        throw exception2;
_L10:
        setScreenStateLocked(false);
        i1 = 0;
          goto _L11
_L8:
        mScreenOffReason = j;
        if(flag3)
            updateLightsLocked(k, 0);
        mHandler.removeCallbacks(mAutoBrightnessTask);
        mLightSensorPendingDecrease = false;
        mLightSensorPendingIncrease = false;
        mScreenOffTime = SystemClock.elapsedRealtime();
        l1 = Binder.clearCallingIdentity();
        mBatteryStats.noteScreenOff();
        Binder.restoreCallingIdentity(l1);
_L13:
        mPowerState = -2 & mPowerState;
        if(mScreenBrightnessAnimator.isAnimating())
            break MISSING_BLOCK_LABEL_582;
        screenOffFinishedAnimatingLocked(j);
          goto _L12
        remoteexception;
        Slog.w("PowerManagerService", "RemoteException calling noteScreenOff on BatteryStatsService", remoteexception);
        Binder.restoreCallingIdentity(l1);
          goto _L13
        exception1;
        Binder.restoreCallingIdentity(l1);
        throw exception1;
        mLastTouchDown = 0L;
          goto _L12
_L6:
        if(flag3)
            updateLightsLocked(k, 0);
          goto _L12
_L1:
        return;
_L3:
        if((k & 1) == 0) goto _L15; else goto _L14
_L14:
        flag2 = true;
        if(true) goto _L17; else goto _L16
_L16:
    }

    private void setScreenBrightnessMode(int i) {
        boolean flag = true;
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        boolean flag1;
        if(i == flag)
            flag1 = flag;
        else
            flag1 = false;
        if(mUseSoftwareAutoBrightness && mAutoBrightessEnabled != flag1) {
            mAutoBrightessEnabled = flag1;
            if(!mAutoBrightessEnabled || !isScreenOn())
                flag = false;
            enableLightSensorLocked(flag);
        }
        return;
    }

    private void setScreenOffTimeoutsLocked() {
        if((2 & mPokey) == 0) goto _L2; else goto _L1
_L1:
        mKeylightDelay = mShortKeylightDelay;
        mDimDelay = -1;
        mScreenOffDelay = 0;
_L8:
        return;
_L2:
        if((4 & mPokey) == 0) goto _L4; else goto _L3
_L3:
        mKeylightDelay = 15000;
        mDimDelay = -1;
        mScreenOffDelay = 0;
          goto _L5
_L4:
        int i;
        i = mScreenOffTimeoutSetting;
        if(i > mMaximumScreenOffTimeout)
            i = mMaximumScreenOffTimeout;
        mKeylightDelay = 6000;
        if(i >= 0) goto _L7; else goto _L6
_L6:
        mScreenOffDelay = mMaximumScreenOffTimeout;
_L9:
        if(mDimScreen && i >= 13000) {
            mDimDelay = -7000 + mScreenOffDelay;
            mScreenOffDelay = 7000;
        } else {
            mDimDelay = -1;
        }
_L5:
        if(true) goto _L8; else goto _L7
_L7:
        if(mKeylightDelay < i)
            mScreenOffDelay = i - mKeylightDelay;
        else
            mScreenOffDelay = 0;
          goto _L9
    }

    private int setScreenStateLocked(boolean flag) {
        if(flag && mInitialized && (1 & mPowerState) != 0)
            if(!mSkippedScreenOn);
        int i = nativeSetScreenState(flag);
        if(i == 0) {
            long l;
            if(flag)
                l = SystemClock.elapsedRealtime();
            else
                l = 0L;
            mLastScreenOnTime = l;
            if(mUseSoftwareAutoBrightness) {
                enableLightSensorLocked(flag);
                if(flag) {
                    mWaitingForFirstLightSensor = mAutoBrightessEnabled;
                } else {
                    mButtonLight.turnOff();
                    mKeyboardLight.turnOff();
                }
            }
        }
        return i;
    }

    private void setTimeoutLocked(long l, int i) {
        setTimeoutLocked(l, -1L, i);
    }

    private void setTimeoutLocked(long l, long l1, int i) {
        long l2 = l1;
        if(!mBootCompleted) goto _L2; else goto _L1
_L1:
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(l2 > 0L) goto _L4; else goto _L3
_L3:
        i;
        JVM INSTR tableswitch 0 3: default 60
    //                   0 201
    //                   1 147
    //                   2 60
    //                   3 135;
           goto _L5 _L6 _L7 _L5 _L8
_L5:
        long l3 = l;
_L13:
        TimeoutTask timeouttask;
        mHandler.removeCallbacks(mTimeoutTask);
        mTimeoutTask.nextState = i;
        timeouttask = mTimeoutTask;
        if(l2 <= 0L) goto _L10; else goto _L9
_L9:
        long l4 = l1 - l2;
_L11:
        timeouttask.remainingTimeoutOverride = l4;
        mHandler.postAtTime(mTimeoutTask, l3);
        mNextTimeout = l3;
        locklist;
        JVM INSTR monitorexit ;
          goto _L2
_L8:
        l3 = l + (long)mKeylightDelay;
        continue; /* Loop/switch isn't completed */
_L7:
        if(mDimDelay >= 0) {
            l3 = l + (long)mDimDelay;
            continue; /* Loop/switch isn't completed */
        }
        Slog.w("PowerManagerService", (new StringBuilder()).append("mDimDelay=").append(mDimDelay).append(" while trying to dim").toString());
_L6:
        LockList locklist1 = mLocks;
        locklist1;
        JVM INSTR monitorenter ;
        l3 = l + (long)mScreenOffDelay;
        locklist1;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L4:
        int j;
        if(l2 <= (long)mScreenOffDelay) {
            l3 = l + l2;
            i = 0;
            continue; /* Loop/switch isn't completed */
        }
        l2 -= mScreenOffDelay;
        if(mDimDelay < 0)
            break MISSING_BLOCK_LABEL_318;
        if(l2 <= (long)mDimDelay) {
            l3 = l + l2;
            i = 1;
            continue; /* Loop/switch isn't completed */
        }
        j = mDimDelay;
        l2 -= j;
        l3 = l + l2;
        i = 3;
        continue; /* Loop/switch isn't completed */
_L10:
        l4 = -1L;
          goto _L11
_L2:
        return;
        if(true) goto _L13; else goto _L12
_L12:
    }

    private boolean shouldDeferScreenOnLocked() {
        boolean flag = true;
        if(!mPreparingForScreenOn) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        for(int i = 0; i < mBroadcastQueue.length; i++)
            if(mBroadcastQueue[i] == flag)
                continue; /* Loop/switch isn't completed */

        flag = false;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private boolean shouldLog(long l) {
        boolean flag = true;
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(l > 0x36ee80L + mWarningSpewThrottleTime) {
            mWarningSpewThrottleTime = l;
            mWarningSpewThrottleCount = 0;
            break MISSING_BLOCK_LABEL_78;
        }
        if(mWarningSpewThrottleCount < 30) {
            mWarningSpewThrottleCount = 1 + mWarningSpewThrottleCount;
            break MISSING_BLOCK_LABEL_78;
        }
        break MISSING_BLOCK_LABEL_73;
        Exception exception;
        exception;
        throw exception;
        locklist;
        JVM INSTR monitorexit ;
        flag = false;
        return flag;
    }

    private void updateLightsLocked(int i, int j) {
        int k;
        int l;
        int i1;
        k = mPowerState;
        if((k & 1) == 0 || mSkippedScreenOn) {
            boolean flag = shouldDeferScreenOnLocked();
            mSkippedScreenOn = flag;
            if(flag)
                i &= -4;
        }
        if((i & 1) != 0)
            i = applyKeyboardState(applyButtonState(i));
        l = i ^ k;
        i1 = l | j;
        if(i1 != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        long l3;
        int j1 = 0;
        int k1 = 0;
        int l1 = getPreferredBrightness();
        int i2;
        int j2;
        int i3;
        if((i1 & 8) != 0)
            if((i & 8) == 0)
                j1 = 0 | 8;
            else
                k1 = 0 | 8;
        if((i1 & 4) != 0)
            if((i & 4) == 0)
                j1 |= 4;
            else
                k1 |= 4;
        if((i1 & 3) == 0) goto _L4; else goto _L3
_L3:
        if((l & 3) == 0) goto _L6; else goto _L5
_L5:
        k & 3;
        JVM INSTR tableswitch 0 3: default 172
    //                   0 413
    //                   1 405
    //                   2 172
    //                   3 402;
           goto _L7 _L8 _L9 _L7 _L8
_L8:
        break; /* Loop/switch isn't completed */
_L7:
        mScreenBrightnessAnimator.getCurrentBrightness();
_L6:
        int k2 = l1;
        int l2 = 60;
        if((i & 2) == 0) {
            float f = (float)mScreenBrightnessDim / (float)l1;
            if(f > 1.0F)
                f = 1.0F;
            if((i & 1) == 0) {
                if((k & 2) != 0)
                    l2 = 60;
                else
                    l2 = (int)(1.5F * (60F * f));
                k2 = 0;
            } else {
                int j3;
                if((k & 1) != 0)
                    l2 = (int)(1.5F * (60F * (1.0F - f)));
                else
                    l2 = (int)(60F * f);
                j3 = getStayOnConditionsLocked();
                if(j3 != 0 && mBatteryService.isPowered(j3))
                    mScreenOffTime = SystemClock.elapsedRealtime();
                k2 = mScreenBrightnessDim;
            }
        }
        if(mWaitingForFirstLightSensor && (i & 1) != 0)
            l2 = 4;
        l3 = Binder.clearCallingIdentity();
        mBatteryStats.noteScreenBrightness(k2);
        Binder.restoreCallingIdentity(l3);
_L10:
        if(!mSkippedScreenOn) {
            i3 = l2 * 16;
            mScreenBrightnessAnimator.animateTo(k2, 2, i3);
        }
_L4:
        if(j1 != 0)
            setLightBrightness(j1, 0);
        if(false) {
            j2 = mScreenBrightnessDim;
            if((i & 0x10) != 0 && j2 > 10)
                j2 = 10;
            setLightBrightness(0, j2);
        }
        if(k1 != 0) {
            i2 = getPreferredBrightness();
            if((i & 0x10) != 0 && i2 > 10)
                i2 = 10;
            setLightBrightness(k1, i2);
        }
        continue; /* Loop/switch isn't completed */
_L9:
        mScreenBrightnessDim;
          goto _L6
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l3);
          goto _L10
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l3);
        throw exception;
        if(true) goto _L1; else goto _L11
_L11:
    }

    private void updateNativePowerStateLocked() {
        boolean flag = true;
        if(!mHeadless) {
            boolean flag1;
            if((1 & mPowerState) != 0)
                flag1 = flag;
            else
                flag1 = false;
            if((3 & mPowerState) != 3)
                flag = false;
            nativeSetPowerState(flag1, flag);
        }
    }

    private void updateSettingsValues() {
        mShortKeylightDelay = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "short_keylight_delay_ms", 6000);
    }

    private void updateWakeLockLocked() {
        int i = getStayOnConditionsLocked();
        if(i != 0 && mBatteryService.isPowered(i)) {
            mStayOnWhilePluggedInScreenDimLock.acquire();
            mStayOnWhilePluggedInPartialLock.acquire();
        } else {
            mStayOnWhilePluggedInScreenDimLock.release();
            mStayOnWhilePluggedInPartialLock.release();
        }
    }

    private void userActivity(long l, long l1, boolean flag, int i, boolean flag1, 
            boolean flag2) {
        if((1 & mPokey) == 0 || i != 2) goto _L2; else goto _L1
_L1:
        return;
_L2:
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(!isScreenTurningOffLocked())
            break MISSING_BLOCK_LABEL_55;
        Slog.d("PowerManagerService", "ignoring user activity while turning off screen");
          goto _L1
        Exception exception;
        exception;
        throw exception;
        if(!flag2) goto _L4; else goto _L3
_L3:
        if((1 & mPowerState) != 0) goto _L4; else goto _L5
_L5:
        locklist;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        if(mProximitySensorActive)
            if(mProximityWakeLockCount != 0);
        if(mLastEventTime > l && !flag1) goto _L7; else goto _L6
_L6:
        mLastEventTime = l;
        if(!mUserActivityAllowed && !flag1) goto _L7; else goto _L8
_L8:
        if(i != 1 || mUseSoftwareAutoBrightness) goto _L10; else goto _L9
_L9:
        if(!mKeyboardVisible) goto _L12; else goto _L11
_L11:
        int k = 15;
_L13:
        mUserState = k;
_L14:
        int j;
        long l2;
        j = Binder.getCallingUid();
        l2 = Binder.clearCallingIdentity();
        mBatteryStats.noteUserActivity(j, i);
        Binder.restoreCallingIdentity(l2);
_L15:
        mWakeLockState = mLocks.reactivateScreenLocksLocked();
        setPowerState(mUserState | mWakeLockState, flag, 2);
        setTimeoutLocked(l, l1, 3);
_L7:
        locklist;
        JVM INSTR monitorexit ;
        if(mPolicy != null)
            mPolicy.userActivity();
          goto _L1
_L12:
        k = 7;
          goto _L13
_L10:
        mUserState = 3 | mUserState;
          goto _L14
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l2);
          goto _L15
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l2);
        throw exception1;
          goto _L1
    }

    public void acquireWakeLock(int i, IBinder ibinder, String s, WorkSource worksource) {
        int j;
        int k;
        long l;
        j = Binder.getCallingUid();
        k = Binder.getCallingPid();
        if(j != Process.myUid())
            mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
        if(worksource != null)
            enforceWakeSourcePermission(j, k);
        l = Binder.clearCallingIdentity();
        synchronized(mLocks) {
            acquireWakeLockLocked(i, ibinder, j, k, s, worksource);
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception1;
        locklist;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void acquireWakeLockLocked(int i, IBinder ibinder, int j, int k, String s, WorkSource worksource) {
        int l;
        WakeLock wakelock;
        if(worksource != null && worksource.size() == 0)
            worksource = null;
        l = mLocks.getIndex(ibinder);
        if(l >= 0)
            break MISSING_BLOCK_LABEL_321;
        wakelock = new WakeLock(i, ibinder, s, j, k);
        0x3f & wakelock.flags;
        JVM INSTR lookupswitch 5: default 108
    //                   1: 165
    //                   6: 312
    //                   10: 303
    //                   26: 152
    //                   32: 165;
           goto _L1 _L2 _L3 _L4 _L5 _L2
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        Slog.e("PowerManagerService", (new StringBuilder()).append("bad wakelock type for lock '").append(s).append("' ").append(" flags=").append(i).toString());
_L6:
        return;
_L5:
        boolean flag;
        boolean flag1;
        WorkSource worksource1;
        if(mUseSoftwareAutoBrightness) {
            wakelock.minState = 3;
        } else {
            int i1;
            if(mKeyboardVisible)
                i1 = 15;
            else
                i1 = 7;
            wakelock.minState = i1;
        }
_L7:
        mLocks.addLock(wakelock);
        if(worksource != null)
            wakelock.ws = new WorkSource(worksource);
        flag = true;
        flag1 = false;
        worksource1 = null;
_L8:
        if(isScreenLock(i)) {
            if((i & 0x3f) == 32) {
                mProximityWakeLockCount = 1 + mProximityWakeLockCount;
                if(mProximityWakeLockCount == 1)
                    enableProximityLockLocked();
            } else {
                if((0x10000000 & wakelock.flags) != 0) {
                    mWakeLockState;
                    mWakeLockState = mLocks.reactivateScreenLocksLocked();
                    if((1 & mWakeLockState) != 0 && mProximitySensorActive)
                        if(mProximityWakeLockCount != 0);
                } else {
                    mWakeLockState = (mUserState | mWakeLockState) & mLocks.gatherState();
                }
                setPowerState(mWakeLockState | mUserState);
            }
        } else
        if((i & 0x3f) == 1) {
            if(flag) {
                mPartialCount = 1 + mPartialCount;
                if(mPartialCount != 1);
            }
            nativeAcquireWakeLock(1, "PowerManagerService");
        }
        if(flag1)
            noteStopWakeLocked(wakelock, worksource1);
        if(flag || flag1)
            noteStartWakeLocked(wakelock, worksource);
          goto _L6
_L4:
        wakelock.minState = 3;
          goto _L7
_L3:
        wakelock.minState = 1;
          goto _L7
        wakelock = (WakeLock)mLocks.get(l);
        flag = false;
        worksource1 = wakelock.ws;
        if(worksource1 != null) {
            if(worksource == null) {
                wakelock.ws = null;
                flag1 = true;
            } else {
                flag1 = worksource1.diff(worksource);
            }
        } else
        if(worksource != null)
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
            wakelock.ws = new WorkSource(worksource);
          goto _L8
    }

    void bootCompleted() {
        Slog.d("PowerManagerService", "bootCompleted");
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        mBootCompleted = true;
        userActivity(SystemClock.uptimeMillis(), false, 1, true);
        updateWakeLockLocked();
        mLocks.notifyAll();
        return;
    }

    public void clearUserActivityTimeout(long l, long l1) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        Slog.i("PowerManagerService", (new StringBuilder()).append("clearUserActivity for ").append(l1).append("ms from now").toString());
        userActivity(l, l1, false, 0, false, false);
    }

    public void crash(String s) {
        Thread thread;
        mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
        thread = new Thread(s) {

            public void run() {
                throw new RuntimeException(message);
            }

            final PowerManagerService this$0;
            final String val$message;

             {
                this$0 = PowerManagerService.this;
                message = s1;
                super(final_s);
            }
        };
        thread.start();
        thread.join();
_L1:
        return;
        InterruptedException interruptedexception;
        interruptedexception;
        Log.wtf("PowerManagerService", interruptedexception);
          goto _L1
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump PowerManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L7:
        return;
_L2:
        long l = SystemClock.uptimeMillis();
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        Iterator iterator;
        printwriter.println("Power Manager State:");
        printwriter.println((new StringBuilder()).append("  mIsPowered=").append(mIsPowered).append(" mPowerState=").append(mPowerState).append(" mScreenOffTime=").append(SystemClock.elapsedRealtime() - mScreenOffTime).append(" ms").toString());
        printwriter.println((new StringBuilder()).append("  mPartialCount=").append(mPartialCount).toString());
        printwriter.println((new StringBuilder()).append("  mWakeLockState=").append(dumpPowerState(mWakeLockState)).toString());
        printwriter.println((new StringBuilder()).append("  mUserState=").append(dumpPowerState(mUserState)).toString());
        printwriter.println((new StringBuilder()).append("  mPowerState=").append(dumpPowerState(mPowerState)).toString());
        printwriter.println((new StringBuilder()).append("  mLocks.gather=").append(dumpPowerState(mLocks.gatherState())).toString());
        printwriter.println((new StringBuilder()).append("  mNextTimeout=").append(mNextTimeout).append(" now=").append(l).append(" ").append((mNextTimeout - l) / 1000L).append("s from now").toString());
        printwriter.println((new StringBuilder()).append("  mDimScreen=").append(mDimScreen).append(" mStayOnConditions=").append(mStayOnConditions).append(" mPreparingForScreenOn=").append(mPreparingForScreenOn).append(" mSkippedScreenOn=").append(mSkippedScreenOn).toString());
        printwriter.println((new StringBuilder()).append("  mScreenOffReason=").append(mScreenOffReason).append(" mUserState=").append(mUserState).toString());
        printwriter.println((new StringBuilder()).append("  mBroadcastQueue={").append(mBroadcastQueue[0]).append(',').append(mBroadcastQueue[1]).append(',').append(mBroadcastQueue[2]).append("}").toString());
        printwriter.println((new StringBuilder()).append("  mBroadcastWhy={").append(mBroadcastWhy[0]).append(',').append(mBroadcastWhy[1]).append(',').append(mBroadcastWhy[2]).append("}").toString());
        printwriter.println((new StringBuilder()).append("  mPokey=").append(mPokey).append(" mPokeAwakeonSet=").append(mPokeAwakeOnSet).toString());
        printwriter.println((new StringBuilder()).append("  mKeyboardVisible=").append(mKeyboardVisible).append(" mUserActivityAllowed=").append(mUserActivityAllowed).toString());
        printwriter.println((new StringBuilder()).append("  mKeylightDelay=").append(mKeylightDelay).append(" mDimDelay=").append(mDimDelay).append(" mScreenOffDelay=").append(mScreenOffDelay).toString());
        printwriter.println((new StringBuilder()).append("  mPreventScreenOn=").append(mPreventScreenOn).append("  mScreenBrightnessOverride=").append(mScreenBrightnessOverride).append("  mButtonBrightnessOverride=").append(mButtonBrightnessOverride).toString());
        printwriter.println((new StringBuilder()).append("  mScreenOffTimeoutSetting=").append(mScreenOffTimeoutSetting).append(" mMaximumScreenOffTimeout=").append(mMaximumScreenOffTimeout).toString());
        printwriter.println((new StringBuilder()).append("  mLastScreenOnTime=").append(mLastScreenOnTime).toString());
        printwriter.println((new StringBuilder()).append("  mBroadcastWakeLock=").append(mBroadcastWakeLock).toString());
        printwriter.println((new StringBuilder()).append("  mStayOnWhilePluggedInScreenDimLock=").append(mStayOnWhilePluggedInScreenDimLock).toString());
        printwriter.println((new StringBuilder()).append("  mStayOnWhilePluggedInPartialLock=").append(mStayOnWhilePluggedInPartialLock).toString());
        printwriter.println((new StringBuilder()).append("  mPreventScreenOnPartialLock=").append(mPreventScreenOnPartialLock).toString());
        printwriter.println((new StringBuilder()).append("  mProximityPartialLock=").append(mProximityPartialLock).toString());
        printwriter.println((new StringBuilder()).append("  mProximityWakeLockCount=").append(mProximityWakeLockCount).toString());
        printwriter.println((new StringBuilder()).append("  mProximitySensorEnabled=").append(mProximitySensorEnabled).toString());
        printwriter.println((new StringBuilder()).append("  mProximitySensorActive=").append(mProximitySensorActive).toString());
        printwriter.println((new StringBuilder()).append("  mProximityPendingValue=").append(mProximityPendingValue).toString());
        printwriter.println((new StringBuilder()).append("  mLastProximityEventTime=").append(mLastProximityEventTime).toString());
        printwriter.println((new StringBuilder()).append("  mLightSensorEnabled=").append(mLightSensorEnabled).append(" mLightSensorAdjustSetting=").append(mLightSensorAdjustSetting).toString());
        printwriter.println((new StringBuilder()).append("  mLightSensorValue=").append(mLightSensorValue).append(" mLightSensorPendingValue=").append(mLightSensorPendingValue).toString());
        printwriter.println((new StringBuilder()).append("  mHighestLightSensorValue=").append(mHighestLightSensorValue).append(" mWaitingForFirstLightSensor=").append(mWaitingForFirstLightSensor).toString());
        printwriter.println((new StringBuilder()).append("  mLightSensorPendingDecrease=").append(mLightSensorPendingDecrease).append(" mLightSensorPendingIncrease=").append(mLightSensorPendingIncrease).toString());
        printwriter.println((new StringBuilder()).append("  mLightSensorScreenBrightness=").append(mLightSensorScreenBrightness).append(" mLightSensorButtonBrightness=").append(mLightSensorButtonBrightness).append(" mLightSensorKeyboardBrightness=").append(mLightSensorKeyboardBrightness).toString());
        printwriter.println((new StringBuilder()).append("  mUseSoftwareAutoBrightness=").append(mUseSoftwareAutoBrightness).toString());
        printwriter.println((new StringBuilder()).append("  mAutoBrightessEnabled=").append(mAutoBrightessEnabled).toString());
        mScreenBrightnessAnimator.dump(printwriter, "mScreenBrightnessAnimator: ");
        int i = mLocks.size();
        printwriter.println();
        printwriter.println((new StringBuilder()).append("mLocks.size=").append(i).append(":").toString());
        for(int j = 0; j < i; j++) {
            WakeLock wakelock = (WakeLock)mLocks.get(j);
            String s3 = lockType(0x3f & wakelock.flags);
            String s4 = "";
            if((0x10000000 & wakelock.flags) != 0)
                s4 = "ACQUIRE_CAUSES_WAKEUP ";
            String s5 = "";
            if(wakelock.activated)
                s5 = " activated";
            printwriter.println((new StringBuilder()).append("  ").append(s3).append(" '").append(wakelock.tag).append("'").append(s4).append(s5).append(" (minState=").append(wakelock.minState).append(", uid=").append(wakelock.uid).append(", pid=").append(wakelock.pid).append(")").toString());
        }

        printwriter.println();
        printwriter.println((new StringBuilder()).append("mPokeLocks.size=").append(mPokeLocks.size()).append(":").toString());
        iterator = mPokeLocks.values().iterator();
_L5:
        PokeLock pokelock;
        StringBuilder stringbuilder;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_1814;
        pokelock = (PokeLock)iterator.next();
        stringbuilder = (new StringBuilder()).append("    poke lock '").append(pokelock.tag).append("':");
        if((1 & pokelock.pokey) == 0) goto _L4; else goto _L3
_L3:
        String s = " POKE_LOCK_IGNORE_TOUCH_EVENTS";
_L6:
        StringBuilder stringbuilder1 = stringbuilder.append(s);
        Exception exception;
        String s1;
        StringBuilder stringbuilder2;
        String s2;
        if((2 & pokelock.pokey) != 0)
            s1 = " POKE_LOCK_SHORT_TIMEOUT";
        else
            s1 = "";
        stringbuilder2 = stringbuilder1.append(s1);
        if((4 & pokelock.pokey) != 0)
            s2 = " POKE_LOCK_MEDIUM_TIMEOUT";
        else
            s2 = "";
        printwriter.println(stringbuilder2.append(s2).toString());
        if(true) goto _L5; else goto _L4
        exception;
        throw exception;
_L4:
        s = "";
          goto _L6
        printwriter.println();
        locklist;
        JVM INSTR monitorexit ;
          goto _L7
    }

    public void enableUserActivity(boolean flag) {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        mUserActivityAllowed = flag;
        if(!flag)
            setTimeoutLocked(SystemClock.uptimeMillis(), 0);
        return;
    }

    void enforceWakeSourcePermission(int i, int j) {
        if(i != Process.myUid())
            mContext.enforcePermission("android.permission.UPDATE_DEVICE_STATS", j, i, null);
    }

    WindowManagerPolicy getPolicyLocked() {
        while(mPolicy == null || !mDoneBooting) 
            try {
                mLocks.wait();
            }
            catch(InterruptedException interruptedexception) { }
        return mPolicy;
    }

    int getStayOnConditionsLocked() {
        int i;
        if(mMaximumScreenOffTimeout <= 0 || mMaximumScreenOffTimeout == 0x7fffffff)
            i = mStayOnConditions;
        else
            i = 0;
        return i;
    }

    public int getSupportedWakeLockFlags() {
        int i = 31;
        if(mProximitySensor != null)
            i |= 0x20;
        return i;
    }

    public void goToSleep(long l) {
        goToSleepWithReason(l, 2);
    }

    public void goToSleepWithReason(long l, int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        goToSleepLocked(l, i);
        return;
    }

    void init(Context context, LightsService lightsservice, IActivityManager iactivitymanager, BatteryService batteryservice) {
        mLightsService = lightsservice;
        mContext = context;
        mActivityService = iactivitymanager;
        mBatteryStats = BatteryStatsService.getService();
        mBatteryService = batteryservice;
        mLcdLight = lightsservice.getLight(0);
        mButtonLight = lightsservice.getLight(2);
        mKeyboardLight = lightsservice.getLight(1);
        mAttentionLight = lightsservice.getLight(5);
        mHeadless = "1".equals(SystemProperties.get("ro.config.headless", "0"));
        mInitComplete = false;
        mScreenBrightnessAnimator = new ScreenBrightnessAnimator("mScreenBrightnessUpdaterThread", -4);
        mScreenBrightnessAnimator.start();
        ScreenBrightnessAnimator screenbrightnessanimator = mScreenBrightnessAnimator;
        screenbrightnessanimator;
        JVM INSTR monitorenter ;
_L3:
        boolean flag = mInitComplete;
        if(flag) goto _L2; else goto _L1
_L1:
        Exception exception;
        HandlerThread handlerthread;
        boolean flag1;
        try {
            mScreenBrightnessAnimator.wait();
        }
        catch(InterruptedException interruptedexception1) { }
        finally { }
        if(true) goto _L3; else goto _L2
_L2:
        screenbrightnessanimator;
        JVM INSTR monitorexit ;
        mInitComplete = false;
        mHandlerThread = new HandlerThread("PowerManagerService") {

            protected void onLooperPrepared() {
                super.onLooperPrepared();
                initInThread();
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super(s);
            }
        };
        mHandlerThread.start();
        handlerthread = mHandlerThread;
        handlerthread;
        JVM INSTR monitorenter ;
_L6:
        flag1 = mInitComplete;
        if(flag1) goto _L5; else goto _L4
_L4:
        Exception exception1;
        try {
            mHandlerThread.wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally { }
        if(true) goto _L6; else goto _L5
        screenbrightnessanimator;
        JVM INSTR monitorexit ;
        throw exception;
_L5:
        handlerthread;
        JVM INSTR monitorexit ;
        synchronized(mLocks) {
            updateNativePowerStateLocked();
            forceUserActivityLocked();
            mInitialized = true;
        }
        return;
        handlerthread;
        JVM INSTR monitorexit ;
        throw exception1;
        exception2;
        locklist;
        JVM INSTR monitorexit ;
        throw exception2;
    }

    void initInThread() {
        mHandler = new Handler();
        mBroadcastWakeLock = new UnsynchronizedWakeLock(1, "sleep_broadcast", true);
        mStayOnWhilePluggedInScreenDimLock = new UnsynchronizedWakeLock(6, "StayOnWhilePluggedIn Screen Dim", false);
        mStayOnWhilePluggedInPartialLock = new UnsynchronizedWakeLock(1, "StayOnWhilePluggedIn Partial", false);
        mPreventScreenOnPartialLock = new UnsynchronizedWakeLock(1, "PreventScreenOn Partial", false);
        mProximityPartialLock = new UnsynchronizedWakeLock(1, "Proximity Partial", false);
        mScreenOnIntent = new Intent("android.intent.action.SCREEN_ON");
        mScreenOnIntent.addFlags(0x50000000);
        mScreenOffIntent = new Intent("android.intent.action.SCREEN_OFF");
        mScreenOffIntent.addFlags(0x50000000);
        Resources resources = mContext.getResources();
        mAnimateScreenLights = resources.getBoolean(0x1110014);
        mUnplugTurnsOnScreen = resources.getBoolean(0x1110013);
        mScreenBrightnessDim = resources.getInteger(0x10e0022);
        mUseSoftwareAutoBrightness = resources.getBoolean(0x1110011);
        if(mUseSoftwareAutoBrightness) {
            mAutoBrightnessLevels = resources.getIntArray(0x1070027);
            mLcdBacklightValues = resources.getIntArray(0x1070028);
            mButtonBacklightValues = resources.getIntArray(0x1070029);
            mKeyboardBacklightValues = resources.getIntArray(0x107002a);
            mLightSensorWarmupTime = resources.getInteger(0x10e0023);
        }
        ContentResolver contentresolver = mContext.getContentResolver();
        android.net.Uri uri = android.provider.Settings.System.CONTENT_URI;
        String as[] = new String[7];
        as[0] = "stay_on_while_plugged_in";
        as[1] = "screen_off_timeout";
        as[2] = "dim_screen";
        as[3] = "screen_brightness";
        as[4] = "screen_brightness_mode";
        as[5] = "window_animation_scale";
        as[6] = "transition_animation_scale";
        mSettings = new ContentQueryMap(contentresolver.query(uri, null, "(name=?) or (name=?) or (name=?) or (name=?) or (name=?) or (name=?) or (name=?) or (name=?)", as, null), "name", true, mHandler);
        SettingsObserver settingsobserver = new SettingsObserver();
        mSettings.addObserver(settingsobserver);
        settingsobserver.update(mSettings, null);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.BATTERY_CHANGED");
        mContext.registerReceiver(new BatteryReceiver(), intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.BOOT_COMPLETED");
        mContext.registerReceiver(new BootCompletedReceiver(), intentfilter1);
        IntentFilter intentfilter2 = new IntentFilter();
        intentfilter2.addAction("android.intent.action.DOCK_EVENT");
        mContext.registerReceiver(new DockReceiver(), intentfilter2);
        mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.CONTENT_URI, true, new ContentObserver(new Handler()) {

            public void onChange(boolean flag) {
                updateSettingsValues();
            }

            final PowerManagerService this$0;

             {
                this$0 = PowerManagerService.this;
                super(handler);
            }
        });
        updateSettingsValues();
        HandlerThread handlerthread = mHandlerThread;
        handlerthread;
        JVM INSTR monitorenter ;
        mInitComplete = true;
        mHandlerThread.notifyAll();
        return;
    }

    boolean isScreenBright() {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        boolean flag;
        if((3 & mPowerState) == 3)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isScreenOn() {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        boolean flag;
        if((1 & mPowerState) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    void logPointerDownEvent() {
        if(mLastTouchDown == 0L) {
            mLastTouchDown = SystemClock.elapsedRealtime();
            mTouchCycles = 1 + mTouchCycles;
        }
    }

    void logPointerUpEvent() {
        mTotalTouchDownTime = mTotalTouchDownTime + (SystemClock.elapsedRealtime() - mLastTouchDown);
        mLastTouchDown = 0L;
    }

    public void monitor() {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
    }

    void noteStartWakeLocked(WakeLock wakelock, WorkSource worksource) {
        if(wakelock.monitorType < 0) goto _L2; else goto _L1
_L1:
        long l = Binder.clearCallingIdentity();
        if(worksource == null) goto _L4; else goto _L3
_L3:
        mBatteryStats.noteStartWakelockFromSource(worksource, wakelock.pid, wakelock.tag, wakelock.monitorType);
_L7:
        Binder.restoreCallingIdentity(l);
_L2:
        return;
_L4:
        try {
            mBatteryStats.noteStartWakelock(wakelock.uid, wakelock.pid, wakelock.tag, wakelock.monitorType);
            continue; /* Loop/switch isn't completed */
        }
        catch(RemoteException remoteexception) {
            Binder.restoreCallingIdentity(l);
        }
        finally {
            Binder.restoreCallingIdentity(l);
            throw exception;
        }
        if(true) goto _L2; else goto _L5
_L5:
        if(true) goto _L7; else goto _L6
_L6:
    }

    void noteStopWakeLocked(WakeLock wakelock, WorkSource worksource) {
        if(wakelock.monitorType < 0) goto _L2; else goto _L1
_L1:
        long l = Binder.clearCallingIdentity();
        if(worksource == null) goto _L4; else goto _L3
_L3:
        mBatteryStats.noteStopWakelockFromSource(worksource, wakelock.pid, wakelock.tag, wakelock.monitorType);
_L7:
        Binder.restoreCallingIdentity(l);
_L2:
        return;
_L4:
        try {
            mBatteryStats.noteStopWakelock(wakelock.uid, wakelock.pid, wakelock.tag, wakelock.monitorType);
            continue; /* Loop/switch isn't completed */
        }
        catch(RemoteException remoteexception) {
            Binder.restoreCallingIdentity(l);
        }
        finally {
            Binder.restoreCallingIdentity(l);
            throw exception;
        }
        if(true) goto _L2; else goto _L5
_L5:
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void preventScreenOn(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(!flag) goto _L2; else goto _L1
_L1:
        mPreventScreenOnPartialLock.acquire();
        mHandler.removeCallbacks(mForceReenableScreenTask);
        mHandler.postDelayed(mForceReenableScreenTask, 5000L);
        mPreventScreenOn = true;
_L4:
        locklist;
        JVM INSTR monitorexit ;
        return;
_L2:
        mPreventScreenOn = false;
        mHandler.removeCallbacks(mForceReenableScreenTask);
        if((1 & mPowerState) != 0) {
            if(mProximitySensorActive)
                break; /* Loop/switch isn't completed */
            int i = setScreenStateLocked(true);
            if(i != 0)
                Slog.w("PowerManagerService", (new StringBuilder()).append("preventScreenOn: error from setScreenStateLocked(): ").append(i).toString());
        }
_L5:
        mPreventScreenOnPartialLock.release();
        if(true) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L3:
        goToSleepLocked(SystemClock.uptimeMillis(), 4);
        mProxIgnoredBecauseScreenTurnedOff = false;
          goto _L5
    }

    public void reboot(final String finalReason) {
        Runnable runnable;
        mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
        if(mHandler == null || !ActivityManagerNative.isSystemReady())
            throw new IllegalStateException("Too early to call reboot()");
        runnable = new Runnable() {

            public void run() {
                this;
                JVM INSTR monitorenter ;
                ShutdownThread.reboot(mContext, finalReason, false);
                return;
            }

            final PowerManagerService this$0;
            final String val$finalReason;

             {
                this$0 = PowerManagerService.this;
                finalReason = s;
                super();
            }
        };
        mHandler.post(runnable);
        runnable;
        JVM INSTR monitorenter ;
        Exception exception;
        do
            try {
                runnable.wait();
            }
            catch(InterruptedException interruptedexception) { }
            finally { }
        while(true);
        runnable;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void releaseWakeLock(IBinder ibinder, int i) {
        if(Binder.getCallingUid() != Process.myUid())
            mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        releaseWakeLockLocked(ibinder, i, false);
        return;
    }

    public void setAttentionLight(boolean flag, int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LightsService.Light light = mAttentionLight;
        byte byte0;
        if(flag)
            byte0 = 3;
        else
            byte0 = 0;
        light.setFlashing(i, 2, byte0, 0);
    }

    public void setAutoBrightnessAdjustment(float f) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        long l;
        mLightSensorAdjustSetting = f;
        if(mSensorManager == null || !mLightSensorEnabled)
            break MISSING_BLOCK_LABEL_77;
        l = Binder.clearCallingIdentity();
        if(mLightSensorValue >= 0.0F) {
            int i = (int)mLightSensorValue;
            mLightSensorValue = -1F;
            handleLightSensorValue(i, true);
        }
        Binder.restoreCallingIdentity(l);
        locklist;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public void setBacklightBrightness(int i) {
        int j;
        j = 0;
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        int k;
        long l;
        k = Math.max(i, mScreenBrightnessDim);
        mLcdLight.setBrightness(k);
        LightsService.Light light = mKeyboardLight;
        if(mKeyboardVisible)
            j = k;
        light.setBrightness(j);
        mButtonLight.setBrightness(k);
        l = Binder.clearCallingIdentity();
        mBatteryStats.noteScreenBrightness(k);
        Binder.restoreCallingIdentity(l);
_L1:
        mScreenBrightnessAnimator.animateTo(k, 2, 0);
        locklist;
        JVM INSTR monitorexit ;
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("PowerManagerService", "RemoteException calling noteScreenBrightness on BatteryStatsService", remoteexception);
        Binder.restoreCallingIdentity(l);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    public void setButtonBrightnessOverride(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(mButtonBrightnessOverride != i) {
            mButtonBrightnessOverride = i;
            if(isScreenOn())
                updateLightsLocked(mPowerState, 12);
        }
        return;
    }

    public void setKeyboardVisibility(boolean flag) {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(mKeyboardVisible != flag) {
            mKeyboardVisible = flag;
            if((1 & mPowerState) != 0) {
                if(mUseSoftwareAutoBrightness && mLightSensorValue >= 0.0F) {
                    int i = (int)mLightSensorValue;
                    mLightSensorValue = -1F;
                    lightSensorChangedLocked(i, false);
                }
                userActivity(SystemClock.uptimeMillis(), false, 1, true);
            }
        }
        return;
    }

    public void setMaximumScreenOffTimeount(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        mMaximumScreenOffTimeout = i;
        setScreenOffTimeoutsLocked();
        return;
    }

    public void setPokeLock(int i, IBinder ibinder, String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        if(ibinder != null) goto _L2; else goto _L1
_L1:
        Slog.e("PowerManagerService", (new StringBuilder()).append("setPokeLock got null token for tag='").append(s).append("'").toString());
_L4:
        return;
_L2:
        if((i & 6) == 6)
            throw new IllegalArgumentException("setPokeLock can't have both POKE_LOCK_SHORT_TIMEOUT and POKE_LOCK_MEDIUM_TIMEOUT");
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        int j;
        int k;
        boolean flag;
        if(i == 0)
            break MISSING_BLOCK_LABEL_257;
        PokeLock pokelock2 = (PokeLock)mPokeLocks.get(ibinder);
        int l = 0;
        Iterator iterator;
        PokeLock pokelock1;
        int i1;
        int j1;
        if(pokelock2 != null) {
            l = pokelock2.pokey;
            pokelock2.pokey = i;
        } else {
            pokelock2 = new PokeLock(i, ibinder, s);
            mPokeLocks.put(ibinder, pokelock2);
        }
        i1 = l & 6;
        j1 = i & 6;
        if((1 & mPowerState) == 0 && i1 != j1)
            pokelock2.awakeOnSet = true;
_L3:
        j = mPokey;
        k = 0;
        flag = false;
        iterator = mPokeLocks.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            pokelock1 = (PokeLock)iterator.next();
            k |= pokelock1.pokey;
            if(pokelock1.awakeOnSet)
                flag = true;
        } while(true);
        break MISSING_BLOCK_LABEL_288;
        Exception exception;
        exception;
        throw exception;
        PokeLock pokelock = (PokeLock)mPokeLocks.remove(ibinder);
        if(pokelock != null)
            ibinder.unlinkToDeath(pokelock, 0);
          goto _L3
        mPokey = k;
        mPokeAwakeOnSet = flag;
        if((j & 6) != (i & 6)) {
            setScreenOffTimeoutsLocked();
            setTimeoutLocked(SystemClock.uptimeMillis(), mTimeoutTask.nextState);
        }
        locklist;
        JVM INSTR monitorexit ;
          goto _L4
    }

    public void setPolicy(WindowManagerPolicy windowmanagerpolicy) {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        mPolicy = windowmanagerpolicy;
        mLocks.notifyAll();
        return;
    }

    public void setScreenBrightnessOverride(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        if(mScreenBrightnessOverride != i) {
            mScreenBrightnessOverride = i;
            if(isScreenOn())
                updateLightsLocked(mPowerState, 1);
        }
        return;
    }

    public void setStayOnSetting(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SETTINGS", null);
        android.provider.Settings.System.putInt(mContext.getContentResolver(), "stay_on_while_plugged_in", i);
    }

    void systemReady() {
        boolean flag;
        LockList locklist;
        long l;
        flag = true;
        mSensorManager = new SystemSensorManager(mHandlerThread.getLooper());
        mProximitySensor = mSensorManager.getDefaultSensor(8);
        if(mUseSoftwareAutoBrightness)
            mLightSensor = mSensorManager.getDefaultSensor(5);
        if(mUseSoftwareAutoBrightness)
            setPowerState(3);
        else
            setPowerState(15);
        locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        Slog.d("PowerManagerService", "system ready!");
        mDoneBooting = true;
        if(!mUseSoftwareAutoBrightness || !mAutoBrightessEnabled)
            flag = false;
        enableLightSensorLocked(flag);
        l = Binder.clearCallingIdentity();
        mBatteryStats.noteScreenBrightness(getPreferredBrightness());
        mBatteryStats.noteScreenOn();
        Binder.restoreCallingIdentity(l);
_L1:
        locklist;
        JVM INSTR monitorexit ;
        return;
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    public long timeSinceScreenOn() {
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        long l;
        if((1 & mPowerState) != 0)
            l = 0L;
        else
            l = SystemClock.elapsedRealtime() - mScreenOffTime;
        return l;
    }

    public void updateWakeLockWorkSource(IBinder ibinder, WorkSource worksource) {
        int i = Binder.getCallingUid();
        int j = Binder.getCallingPid();
        if(worksource != null && worksource.size() == 0)
            worksource = null;
        if(worksource != null)
            enforceWakeSourcePermission(i, j);
        LockList locklist = mLocks;
        locklist;
        JVM INSTR monitorenter ;
        int k;
        k = mLocks.getIndex(ibinder);
        if(k < 0)
            throw new IllegalArgumentException("Wake lock not active");
        break MISSING_BLOCK_LABEL_76;
        Exception exception;
        exception;
        throw exception;
        WakeLock wakelock;
        WorkSource worksource1;
        WorkSource worksource2;
        wakelock = (WakeLock)mLocks.get(k);
        worksource1 = wakelock.ws;
        if(worksource == null)
            break MISSING_BLOCK_LABEL_137;
        worksource2 = new WorkSource(worksource);
_L1:
        wakelock.ws = worksource2;
        noteStopWakeLocked(wakelock, worksource1);
        noteStartWakeLocked(wakelock, worksource);
        locklist;
        JVM INSTR monitorexit ;
        return;
        worksource2 = null;
          goto _L1
    }

    public void userActivity(long l, boolean flag) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
            if(shouldLog(l))
                Slog.w("PowerManagerService", (new StringBuilder()).append("Caller does not have DEVICE_POWER permission.  pid=").append(Binder.getCallingPid()).append(" uid=").append(Binder.getCallingUid()).toString());
        } else {
            userActivity(l, -1L, flag, 0, false, false);
        }
    }

    public void userActivity(long l, boolean flag, int i) {
        userActivity(l, -1L, flag, i, false, false);
    }

    public void userActivity(long l, boolean flag, int i, boolean flag1) {
        userActivity(l, -1L, flag, i, flag1, false);
    }

    public void userActivityWithForce(long l, boolean flag, boolean flag1) {
        mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        userActivity(l, -1L, flag, 0, flag1, false);
    }

    private static final int ALL_BRIGHT = 15;
    private static final int ALL_LIGHTS_OFF = 0;
    private static final int ANIM_SETTING_OFF = 16;
    private static final int ANIM_SETTING_ON = 1;
    static final int ANIM_STEPS = 60;
    static final int AUTOBRIGHTNESS_ANIM_STEPS = 120;
    static final int AUTODIMNESS_ANIM_STEPS = 0x147ae1;
    private static final int BATTERY_LOW_BIT = 16;
    private static final int BUTTON_BRIGHT_BIT = 4;
    static final boolean DEBUG_SCREEN_ON = false;
    private static final int DEFAULT_SCREEN_BRIGHTNESS = 192;
    private static final int DEFAULT_SCREEN_OFF_TIMEOUT = 15000;
    private static final int FULL_WAKE_LOCK_ID = 2;
    static final int IMMEDIATE_ANIM_STEPS = 4;
    static final int INITIAL_BUTTON_BRIGHTNESS = 0;
    static final int INITIAL_KEYBOARD_BRIGHTNESS = 0;
    static final int INITIAL_SCREEN_BRIGHTNESS = 255;
    private static final int KEYBOARD_BRIGHT_BIT = 8;
    private static final int LIGHTS_MASK = 14;
    private static final int LIGHT_SENSOR_DELAY = 2000;
    private static final int LIGHT_SENSOR_OFFSET_SCALE = 8;
    private static final int LIGHT_SENSOR_RANGE_EXPANSION = 20;
    private static final int LIGHT_SENSOR_RATE = 0xf4240;
    private static final int LOCK_MASK = 63;
    private static final boolean LOG_PARTIAL_WL = false;
    private static final boolean LOG_TOUCH_DOWNS = true;
    private static final int LONG_DIM_TIME = 7000;
    private static final int LONG_KEYLIGHT_DELAY = 6000;
    private static final int LOW_BATTERY_THRESHOLD = 10;
    private static final int MEDIUM_KEYLIGHT_DELAY = 15000;
    private static final int NOMINAL_FRAME_TIME_MS = 16;
    static final String PARTIAL_NAME = "PowerManagerService";
    private static final int PARTIAL_WAKE_LOCK_ID = 1;
    private static final int PROXIMITY_SENSOR_DELAY = 1000;
    private static final float PROXIMITY_THRESHOLD = 5F;
    private static final int SCREEN_BRIGHT = 3;
    private static final int SCREEN_BRIGHT_BIT = 2;
    private static final int SCREEN_BUTTON_BRIGHT = 7;
    private static final int SCREEN_DIM = 1;
    private static final int SCREEN_OFF = 0;
    private static final int SCREEN_ON_BIT = 1;
    private static final int SHORT_KEYLIGHT_DELAY_DEFAULT = 6000;
    private static final String TAG = "PowerManagerService";
    private static final boolean mDebugLightAnimation;
    private static final boolean mDebugLightSensor;
    private static final boolean mDebugProximitySensor;
    private static final boolean mSpew;
    private final int MY_PID = Process.myPid();
    private final int MY_UID = Process.myUid();
    private IActivityManager mActivityService;
    boolean mAnimateScreenLights;
    private int mAnimationSetting;
    private LightsService.Light mAttentionLight;
    private boolean mAutoBrightessEnabled;
    private int mAutoBrightnessLevels[];
    private Runnable mAutoBrightnessTask;
    private BatteryService mBatteryService;
    private IBatteryStats mBatteryStats;
    private boolean mBootCompleted;
    private final int mBroadcastQueue[];
    private UnsynchronizedWakeLock mBroadcastWakeLock;
    private final int mBroadcastWhy[] = new int[3];
    private int mButtonBacklightValues[];
    private int mButtonBrightnessOverride;
    private LightsService.Light mButtonLight;
    private Context mContext;
    private int mDimDelay;
    private boolean mDimScreen;
    private boolean mDoneBooting;
    private Runnable mForceReenableScreenTask;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private boolean mHeadless;
    private int mHighestLightSensorValue;
    private volatile boolean mInitComplete;
    private boolean mInitialAnimation;
    private boolean mInitialized;
    private boolean mIsDocked;
    private boolean mIsPowered;
    private int mKeyboardBacklightValues[];
    private LightsService.Light mKeyboardLight;
    private boolean mKeyboardVisible;
    private int mKeylightDelay;
    private long mLastEventTime;
    private long mLastProximityEventTime;
    private long mLastScreenOnTime;
    private long mLastTouchDown;
    private int mLcdBacklightValues[];
    private LightsService.Light mLcdLight;
    SensorEventListener mLightListener;
    private Sensor mLightSensor;
    private float mLightSensorAdjustSetting;
    private int mLightSensorButtonBrightness;
    private boolean mLightSensorEnabled;
    private int mLightSensorKeyboardBrightness;
    private boolean mLightSensorPendingDecrease;
    private boolean mLightSensorPendingIncrease;
    private float mLightSensorPendingValue;
    private int mLightSensorScreenBrightness;
    private float mLightSensorValue;
    private int mLightSensorWarmupTime;
    private LightsService mLightsService;
    private final LockList mLocks = new LockList();
    private int mMaximumScreenOffTimeout;
    private long mNextTimeout;
    private Runnable mNotificationTask;
    private int mPartialCount;
    private volatile boolean mPokeAwakeOnSet;
    private final HashMap mPokeLocks = new HashMap();
    private volatile int mPokey;
    private volatile WindowManagerPolicy mPolicy;
    private int mPowerState;
    private boolean mPreparingForScreenOn;
    private boolean mPreventScreenOn;
    private UnsynchronizedWakeLock mPreventScreenOnPartialLock;
    private boolean mProxIgnoredBecauseScreenTurnedOff;
    SensorEventListener mProximityListener;
    private UnsynchronizedWakeLock mProximityPartialLock;
    private int mProximityPendingValue;
    private Sensor mProximitySensor;
    private boolean mProximitySensorActive;
    private boolean mProximitySensorEnabled;
    private Runnable mProximityTask;
    private int mProximityWakeLockCount;
    private Runnable mReleaseProximitySensorRunnable;
    private ScreenBrightnessAnimator mScreenBrightnessAnimator;
    private int mScreenBrightnessDim;
    private Handler mScreenBrightnessHandler;
    private int mScreenBrightnessOverride;
    private int mScreenBrightnessSetting;
    private BroadcastReceiver mScreenOffBroadcastDone;
    private int mScreenOffDelay;
    private Handler mScreenOffHandler;
    private Intent mScreenOffIntent;
    private int mScreenOffReason;
    long mScreenOffStart;
    private long mScreenOffTime;
    private int mScreenOffTimeoutSetting;
    private BroadcastReceiver mScreenOnBroadcastDone;
    private Intent mScreenOnIntent;
    private android.view.WindowManagerPolicy.ScreenOnListener mScreenOnListener;
    long mScreenOnStart;
    private SensorManager mSensorManager;
    private ContentQueryMap mSettings;
    private int mShortKeylightDelay;
    private boolean mSkippedScreenOn;
    private int mStayOnConditions;
    private UnsynchronizedWakeLock mStayOnWhilePluggedInPartialLock;
    private UnsynchronizedWakeLock mStayOnWhilePluggedInScreenDimLock;
    private boolean mStillNeedSleepNotification;
    private final TimeoutTask mTimeoutTask = new TimeoutTask();
    private long mTotalTouchDownTime;
    private int mTouchCycles;
    boolean mUnplugTurnsOnScreen;
    private boolean mUseSoftwareAutoBrightness;
    private boolean mUserActivityAllowed;
    private int mUserState;
    private boolean mWaitingForFirstLightSensor;
    private int mWakeLockState;
    private int mWarningSpewThrottleCount;
    private long mWarningSpewThrottleTime;
    private float mWindowScaleAnimation;





/*
    static int access$1302(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mStayOnConditions = i;
        return i;
    }

*/


/*
    static int access$1402(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mScreenOffTimeoutSetting = i;
        return i;
    }

*/


/*
    static int access$1502(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mScreenBrightnessSetting = i;
        return i;
    }

*/


/*
    static float access$1602(PowerManagerService powermanagerservice, float f) {
        powermanagerservice.mLightSensorAdjustSetting = f;
        return f;
    }

*/





/*
    static float access$1902(PowerManagerService powermanagerservice, float f) {
        powermanagerservice.mWindowScaleAnimation = f;
        return f;
    }

*/




/*
    static int access$2002(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mAnimationSetting = i;
        return i;
    }

*/


/*
    static int access$2076(PowerManagerService powermanagerservice, int i) {
        int j = i | powermanagerservice.mAnimationSetting;
        powermanagerservice.mAnimationSetting = j;
        return j;
    }

*/



/*
    static int access$2602(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mUserState = i;
        return i;
    }

*/








/*
    static boolean access$3102(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mPreparingForScreenOn = flag;
        return flag;
    }

*/
















/*
    static Handler access$4402(PowerManagerService powermanagerservice, Handler handler) {
        powermanagerservice.mScreenBrightnessHandler = handler;
        return handler;
    }

*/




/*
    static boolean access$4602(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mInitialAnimation = flag;
        return flag;
    }

*/







/*
    static boolean access$5202(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mInitComplete = flag;
        return flag;
    }

*/



/*
    static int access$5302(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mHighestLightSensorValue = i;
        return i;
    }

*/







/*
    static int access$5902(PowerManagerService powermanagerservice, int i) {
        powermanagerservice.mProximityPendingValue = i;
        return i;
    }

*/




/*
    static boolean access$602(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mIsPowered = flag;
        return flag;
    }

*/




/*
    static boolean access$6202(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mLightSensorPendingDecrease = flag;
        return flag;
    }

*/



/*
    static boolean access$6302(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mLightSensorPendingIncrease = flag;
        return flag;
    }

*/





/*
    static boolean access$6702(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mProxIgnoredBecauseScreenTurnedOff = flag;
        return flag;
    }

*/



/*
    static long access$6802(PowerManagerService powermanagerservice, long l) {
        powermanagerservice.mLastProximityEventTime = l;
        return l;
    }

*/







/*
    static boolean access$7202(PowerManagerService powermanagerservice, boolean flag) {
        powermanagerservice.mWaitingForFirstLightSensor = flag;
        return flag;
    }

*/



}
