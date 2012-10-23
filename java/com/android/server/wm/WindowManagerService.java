// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import com.android.internal.app.IBatteryStats;
import com.android.internal.policy.PolicyManager;
import com.android.internal.view.*;
import com.android.server.*;
import com.android.server.am.BatteryStatsService;
import com.android.server.input.InputFilter;
import com.android.server.input.InputManagerService;
import com.android.server.pm.ShutdownThread;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.util.*;

// Referenced classes of package com.android.server.wm:
//            WindowState, InputMonitor, WindowAnimator, AppWindowToken, 
//            WindowToken, AppWindowAnimator, WindowStateAnimator, Watermark, 
//            StrictModeFlash, FakeWindowImpl, BlackFrame, Session, 
//            ScreenRotationAnimation, ViewServer, DragState, StartingData

public class WindowManagerService extends android.view.IWindowManager.Stub
    implements com.android.server.Watchdog.Monitor, android.view.WindowManagerPolicy.WindowManagerFuncs {
    public static interface OnHardKeyboardStatusChangeListener {

        public abstract void onHardKeyboardStatusChange(boolean flag, boolean flag1);
    }

    final class H extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR lookupswitch 29: default 248
        //                       2: 249
        //                       3: 414
        //                       4: 509
        //                       5: 550
        //                       6: 769
        //                       7: 892
        //                       8: 1070
        //                       9: 1043
        //                       11: 1127
        //                       12: 1250
        //                       13: 1365
        //                       14: 1454
        //                       15: 1529
        //                       16: 1619
        //                       17: 1629
        //                       18: 1776
        //                       19: 1792
        //                       20: 1844
        //                       21: 1927
        //                       22: 1992
        //                       23: 2002
        //                       24: 2012
        //                       25: 2117
        //                       26: 2345
        //                       27: 2455
        //                       100001: 2359
        //                       100002: 2387
        //                       100003: 2415
        //                       100004: 2442;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L19 _L20 _L21 _L22 _L23 _L24 _L25 _L26 _L27 _L28 _L29 _L30
_L1:
            return;
_L2:
            HashMap hashmap15 = mWindowMap;
            hashmap15;
            JVM INSTR monitorenter ;
            WindowState windowstate1;
            WindowState windowstate2;
            windowstate1 = mLastFocus;
            windowstate2 = mCurrentFocus;
            if(windowstate1 == windowstate2)
                continue; /* Loop/switch isn't completed */
            break MISSING_BLOCK_LABEL_300;
            Exception exception16;
            exception16;
            throw exception16;
            mLastFocus = windowstate2;
            if(windowstate2 != null && windowstate1 != null && !windowstate2.isDisplayedLw()) {
                mLosingFocus.add(windowstate1);
                windowstate1 = null;
            }
            hashmap15;
            JVM INSTR monitorexit ;
            if(windowstate1 != windowstate2) {
                if(windowstate2 != null) {
                    RemoteException remoteexception;
                    Pair pair;
                    HashMap hashmap;
                    boolean flag;
                    Exception exception;
                    boolean flag1;
                    WindowManagerService windowmanagerservice;
                    HashMap hashmap1;
                    Exception exception1;
                    Pair pair1;
                    RemoteException remoteexception1;
                    HashMap hashmap2;
                    HashMap hashmap3;
                    HashMap hashmap5;
                    Exception exception3;
                    WindowAnimator windowanimator;
                    Exception exception4;
                    int i;
                    AppWindowToken appwindowtoken;
                    HashMap hashmap6;
                    Exception exception5;
                    HashMap hashmap7;
                    Session session;
                    Session session1;
                    RemoteException remoteexception2;
                    HashMap hashmap9;
                    Exception exception7;
                    int j;
                    WindowState windowstate;
                    AppWindowToken appwindowtoken1;
                    AppWindowToken appwindowtoken2;
                    RemoteException remoteexception4;
                    HashMap hashmap10;
                    Exception exception8;
                    int k;
                    AppWindowToken appwindowtoken3;
                    android.view.View view;
                    IBinder ibinder;
                    Exception exception9;
                    AppWindowToken appwindowtoken4;
                    IBinder ibinder1;
                    android.view.View view1;
                    Exception exception11;
                    AppWindowToken appwindowtoken5;
                    StartingData startingdata;
                    android.view.View view2;
                    Exception exception12;
                    boolean flag3;
                    HashMap hashmap12;
                    Exception exception13;
                    Exception exception14;
                    android.view.View view3;
                    HashMap hashmap13;
                    ArrayList arraylist;
                    int l;
                    int i1;
                    RemoteException remoteexception5;
                    RemoteException remoteexception6;
                    try {
                        windowstate2.mClient.windowFocusChanged(true, mInTouchMode);
                    }
                    catch(RemoteException remoteexception7) { }
                    notifyFocusChanged();
                }
                if(windowstate1 != null)
                    try {
                        windowstate1.mClient.windowFocusChanged(false, mInTouchMode);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RemoteException remoteexception6) { }
            }
            continue; /* Loop/switch isn't completed */
_L3:
            synchronized(mWindowMap) {
                arraylist = mLosingFocus;
                mLosingFocus = new ArrayList();
            }
            l = arraylist.size();
            i1 = 0;
            while(i1 < l)  {
                try {
                    ((WindowState)arraylist.get(i1)).mClient.windowFocusChanged(false, mInTouchMode);
                }
                // Misplaced declaration of an exception variable
                catch(RemoteException remoteexception5) { }
                i1++;
            }
            continue; /* Loop/switch isn't completed */
            exception15;
            hashmap14;
            JVM INSTR monitorexit ;
            throw exception15;
_L4:
            hashmap13 = mWindowMap;
            hashmap13;
            JVM INSTR monitorenter ;
            mTraversalScheduled = false;
            performLayoutAndPlaceSurfacesLocked();
            continue; /* Loop/switch isn't completed */
_L5:
            appwindowtoken5 = (AppWindowToken)message.obj;
            startingdata = appwindowtoken5.startingData;
            if(startingdata == null)
                continue; /* Loop/switch isn't completed */
            view2 = null;
            view3 = mPolicy.addStartingWindow(((WindowToken) (appwindowtoken5)).token, startingdata.pkg, startingdata.theme, startingdata.compatInfo, startingdata.nonLocalizedLabel, startingdata.labelRes, startingdata.icon, startingdata.windowFlags);
            view2 = view3;
_L31:
            if(view2 == null)
                continue; /* Loop/switch isn't completed */
            flag3 = false;
            hashmap12 = mWindowMap;
            hashmap12;
            JVM INSTR monitorenter ;
            if(!appwindowtoken5.removed && appwindowtoken5.startingData != null)
                break MISSING_BLOCK_LABEL_751;
            if(appwindowtoken5.startingWindow != null) {
                appwindowtoken5.startingWindow = null;
                appwindowtoken5.startingData = null;
                flag3 = true;
            }
_L32:
            if(flag3)
                try {
                    mPolicy.removeStartingWindow(((WindowToken) (appwindowtoken5)).token, view2);
                }
                // Misplaced declaration of an exception variable
                catch(Exception exception14) {
                    Slog.w("WindowManager", "Exception when removing starting window", exception14);
                }
            continue; /* Loop/switch isn't completed */
            exception12;
            Slog.w("WindowManager", "Exception when adding starting window", exception12);
              goto _L31
            appwindowtoken5.startingView = view2;
              goto _L32
            exception13;
            throw exception13;
_L6:
            appwindowtoken4 = (AppWindowToken)message.obj;
            ibinder1 = null;
            view1 = null;
            synchronized(mWindowMap) {
                if(appwindowtoken4.startingWindow != null) {
                    view1 = appwindowtoken4.startingView;
                    ibinder1 = ((WindowToken) (appwindowtoken4)).token;
                    appwindowtoken4.startingData = null;
                    appwindowtoken4.startingView = null;
                    appwindowtoken4.startingWindow = null;
                    appwindowtoken4.startingDisplayed = false;
                }
            }
            if(view1 != null)
                try {
                    mPolicy.removeStartingWindow(ibinder1, view1);
                }
                // Misplaced declaration of an exception variable
                catch(Exception exception11) {
                    Slog.w("WindowManager", "Exception when removing starting window", exception11);
                }
            continue; /* Loop/switch isn't completed */
            exception10;
            hashmap11;
            JVM INSTR monitorexit ;
            throw exception10;
_L7:
            hashmap10 = mWindowMap;
            hashmap10;
            JVM INSTR monitorenter ;
            k = mFinishedStarting.size();
            if(k <= 0)
                continue; /* Loop/switch isn't completed */
            break MISSING_BLOCK_LABEL_935;
            exception8;
            throw exception8;
            appwindowtoken3 = (AppWindowToken)mFinishedStarting.remove(k - 1);
            if(appwindowtoken3.startingWindow != null)
                break MISSING_BLOCK_LABEL_968;
            hashmap10;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
            view = appwindowtoken3.startingView;
            ibinder = ((WindowToken) (appwindowtoken3)).token;
            appwindowtoken3.startingData = null;
            appwindowtoken3.startingView = null;
            appwindowtoken3.startingWindow = null;
            appwindowtoken3.startingDisplayed = false;
            hashmap10;
            JVM INSTR monitorexit ;
            try {
                mPolicy.removeStartingWindow(ibinder, view);
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception9) {
                Slog.w("WindowManager", "Exception when removing starting window", exception9);
            }
            if(true) goto _L7; else goto _L9
_L9:
            appwindowtoken2 = (AppWindowToken)message.obj;
            try {
                appwindowtoken2.appToken.windowsDrawn();
            }
            // Misplaced declaration of an exception variable
            catch(RemoteException remoteexception4) { }
            continue; /* Loop/switch isn't completed */
_L8:
            appwindowtoken1 = (AppWindowToken)message.obj;
            boolean flag2;
            RemoteException remoteexception3;
            if(message.arg1 != 0)
                flag2 = true;
            else
                flag2 = false;
            if(message.arg2 == 0);
            if(!flag2)
                break MISSING_BLOCK_LABEL_1114;
            appwindowtoken1.appToken.windowsVisible();
            continue; /* Loop/switch isn't completed */
            appwindowtoken1.appToken.windowsGone();
            continue; /* Loop/switch isn't completed */
_L10:
            hashmap9 = mWindowMap;
            hashmap9;
            JVM INSTR monitorenter ;
            Slog.w("WindowManager", "Window freeze timeout expired.");
            j = mWindows.size();
            do {
                if(j <= 0)
                    break;
                j--;
                windowstate = (WindowState)mWindows.get(j);
                if(windowstate.mOrientationChanging) {
                    windowstate.mOrientationChanging = false;
                    Slog.w("WindowManager", (new StringBuilder()).append("Force clearing orientation change: ").append(windowstate).toString());
                }
            } while(true);
            break MISSING_BLOCK_LABEL_1237;
            exception7;
            throw exception7;
            performLayoutAndPlaceSurfacesLocked();
            hashmap9;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
_L11:
            synchronized(mWindowMap) {
                session = mLastReportedHold;
                session1 = (Session)message.obj;
                mLastReportedHold = session1;
            }
            if(session == session1)
                continue; /* Loop/switch isn't completed */
            if(session == null)
                break MISSING_BLOCK_LABEL_1321;
            mBatteryStats.noteStopWakelock(session.mUid, -1, "window", 2);
            if(session1 != null)
                mBatteryStats.noteStartWakelock(session1.mUid, -1, "window", 2);
            continue; /* Loop/switch isn't completed */
            remoteexception2;
            continue; /* Loop/switch isn't completed */
            exception6;
            hashmap8;
            JVM INSTR monitorexit ;
            throw exception6;
_L12:
            hashmap7 = mWindowMap;
            hashmap7;
            JVM INSTR monitorenter ;
            if(mNextAppTransition != -1) {
                mAppTransitionReady = true;
                mAppTransitionTimeout = true;
                mAnimatingAppTokens.clear();
                mAnimatingAppTokens.addAll(mAppTokens);
                performLayoutAndPlaceSurfacesLocked();
            }
            continue; /* Loop/switch isn't completed */
_L13:
            android.provider.Settings.System.putFloat(mContext.getContentResolver(), "window_animation_scale", mWindowAnimationScale);
            android.provider.Settings.System.putFloat(mContext.getContentResolver(), "transition_animation_scale", mTransitionAnimationScale);
            android.provider.Settings.System.putFloat(mContext.getContentResolver(), "animator_duration_scale", mAnimatorDurationScale);
            continue; /* Loop/switch isn't completed */
_L14:
            hashmap6 = mWindowMap;
            hashmap6;
            JVM INSTR monitorenter ;
            if(mAnimationScheduled) {
                mH.sendMessageDelayed(mH.obtainMessage(15), 2000L);
                continue; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_1591;
            exception5;
            throw exception5;
            if(!mDisplayFrozen)
                break MISSING_BLOCK_LABEL_1607;
            hashmap6;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
            hashmap6;
            JVM INSTR monitorexit ;
            Runtime.getRuntime().gc();
            continue; /* Loop/switch isn't completed */
_L15:
            performEnableScreen();
            continue; /* Loop/switch isn't completed */
_L16:
            hashmap5 = mWindowMap;
            hashmap5;
            JVM INSTR monitorenter ;
            windowanimator = mAnimator;
            windowanimator;
            JVM INSTR monitorenter ;
            Slog.w("WindowManager", "App freeze timeout expired.");
            i = mAppTokens.size();
            do {
                if(i <= 0)
                    break;
                i--;
                appwindowtoken = (AppWindowToken)mAppTokens.get(i);
                if(appwindowtoken.mAppAnimator.freezingScreen) {
                    Slog.w("WindowManager", (new StringBuilder()).append("Force clearing freeze: ").append(appwindowtoken).toString());
                    unsetAppFreezingScreenLocked(appwindowtoken, true, true);
                }
            } while(true);
            break MISSING_BLOCK_LABEL_1767;
            exception4;
            throw exception4;
            exception3;
            throw exception3;
            windowanimator;
            JVM INSTR monitorexit ;
            hashmap5;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
_L17:
            removeMessages(18);
            sendNewConfiguration();
            continue; /* Loop/switch isn't completed */
_L18:
            if(mWindowsChanged) {
                synchronized(mWindowMap) {
                    mWindowsChanged = false;
                }
                notifyWindowsChanged();
            }
            continue; /* Loop/switch isn't completed */
            exception2;
            hashmap4;
            JVM INSTR monitorexit ;
            throw exception2;
_L19:
            (IBinder)message.obj;
            hashmap3 = mWindowMap;
            hashmap3;
            JVM INSTR monitorenter ;
            if(mDragState != null) {
                mDragState.unregister();
                mInputMonitor.updateInputWindowsLw(true);
                mDragState.reset();
                mDragState = null;
            }
            continue; /* Loop/switch isn't completed */
_L20:
            (IBinder)message.obj;
            hashmap2 = mWindowMap;
            hashmap2;
            JVM INSTR monitorenter ;
            if(mDragState != null) {
                mDragState.mDragResult = false;
                mDragState.endDragLw();
            }
            continue; /* Loop/switch isn't completed */
_L21:
            notifyHardKeyboardStatusChange();
            continue; /* Loop/switch isn't completed */
_L22:
            performBootTimeout();
            continue; /* Loop/switch isn't completed */
_L23:
            hashmap1 = mWindowMap;
            hashmap1;
            JVM INSTR monitorenter ;
            pair1 = (Pair)message.obj;
            Slog.w("WindowManager", (new StringBuilder()).append("Timeout waiting for drawn: ").append(pair1.first).toString());
            if(!mWaitingForDrawn.remove(pair1))
                continue; /* Loop/switch isn't completed */
            break MISSING_BLOCK_LABEL_2092;
            exception1;
            throw exception1;
            hashmap1;
            JVM INSTR monitorexit ;
            try {
                ((IRemoteCallback)pair1.second).sendResult(null);
            }
            // Misplaced declaration of an exception variable
            catch(RemoteException remoteexception1) { }
            continue; /* Loop/switch isn't completed */
_L24:
            hashmap = mWindowMap;
            hashmap;
            JVM INSTR monitorenter ;
            flag = false;
            if((1 & message.arg1) != 0) {
                mInnerFields.mUpdateRotation = true;
                flag = true;
            }
            if((2 & message.arg1) != 0) {
                mInnerFields.mWallpaperMayChange = true;
                flag = true;
            }
            if((4 & message.arg1) != 0) {
                mInnerFields.mWallpaperForceHidingChanged = true;
                flag = true;
            }
            if((8 & message.arg1) == 0) goto _L34; else goto _L33
_L33:
            mInnerFields.mOrientationChangeComplete = false;
_L35:
            if((0x10 & message.arg1) != 0)
                mTurnOnScreen = true;
            windowmanagerservice = WindowManagerService.this;
            windowmanagerservice.mPendingLayoutChanges = windowmanagerservice.mPendingLayoutChanges | message.arg2;
            if(mPendingLayoutChanges != 0)
                flag = true;
            if(flag) {
                mH.sendEmptyMessage(0x186a4);
                performLayoutAndPlaceSurfacesLocked();
            }
            continue; /* Loop/switch isn't completed */
            exception;
            throw exception;
_L34:
            mInnerFields.mOrientationChangeComplete = true;
            flag1 = mWindowsFreezingScreen;
            if(flag1)
                flag = true;
            if(true) goto _L35; else goto _L25
_L25:
            showStrictModeViolation(message.arg1);
            continue; /* Loop/switch isn't completed */
_L27:
            pair = (Pair)message.obj;
            ((WindowStateAnimator)pair.first).setTransparentRegionHint((Region)pair.second);
            continue; /* Loop/switch isn't completed */
_L28:
            ((WindowStateAnimator)message.obj).setWallpaperOffset(message.arg1, message.arg2);
            scheduleAnimationLocked();
            continue; /* Loop/switch isn't completed */
_L29:
            mAnimator.mDimParams = (DimAnimator.Parameters)message.obj;
            scheduleAnimationLocked();
            continue; /* Loop/switch isn't completed */
_L30:
            mAnimator.clearPendingActions();
            continue; /* Loop/switch isn't completed */
_L26:
            try {
                ((IRemoteCallback)message.obj).sendResult(null);
            }
            // Misplaced declaration of an exception variable
            catch(RemoteException remoteexception) { }
            continue; /* Loop/switch isn't completed */
            remoteexception3;
            if(true) goto _L1; else goto _L36
_L36:
        }

        public static final int ADD_STARTING = 5;
        public static final int ANIMATOR_WHAT_OFFSET = 0x186a0;
        public static final int APP_FREEZE_TIMEOUT = 17;
        public static final int APP_TRANSITION_TIMEOUT = 13;
        public static final int BOOT_TIMEOUT = 23;
        public static final int BULK_UPDATE_PARAMETERS = 25;
        public static final int CLEAR_PENDING_ACTIONS = 0x186a4;
        public static final int DO_ANIMATION_CALLBACK = 27;
        public static final int DO_TRAVERSAL = 4;
        public static final int DRAG_END_TIMEOUT = 21;
        public static final int DRAG_START_TIMEOUT = 20;
        public static final int ENABLE_SCREEN = 16;
        public static final int FINISHED_STARTING = 7;
        public static final int FORCE_GC = 15;
        public static final int HOLD_SCREEN_CHANGED = 12;
        public static final int PERSIST_ANIMATION_SCALE = 14;
        public static final int REMOVE_STARTING = 6;
        public static final int REPORT_APPLICATION_TOKEN_DRAWN = 9;
        public static final int REPORT_APPLICATION_TOKEN_WINDOWS = 8;
        public static final int REPORT_FOCUS_CHANGE = 2;
        public static final int REPORT_HARD_KEYBOARD_STATUS_CHANGE = 22;
        public static final int REPORT_LOSING_FOCUS = 3;
        public static final int REPORT_WINDOWS_CHANGE = 19;
        public static final int SEND_NEW_CONFIGURATION = 18;
        public static final int SET_DIM_PARAMETERS = 0x186a3;
        public static final int SET_TRANSPARENT_REGION = 0x186a1;
        public static final int SET_WALLPAPER_OFFSET = 0x186a2;
        public static final int SHOW_STRICT_MODE_VIOLATION = 26;
        public static final int WAITING_FOR_DRAWN_TIMEOUT = 24;
        public static final int WINDOW_FREEZE_TIMEOUT = 11;
        private Session mLastReportedHold;
        final WindowManagerService this$0;

        public H() {
            this$0 = WindowManagerService.this;
            super();
        }
    }

    static class PolicyThread extends Thread {

        public void run() {
            Looper.prepare();
            WindowManagerPolicyThread.set(this, Looper.myLooper());
            Process.setThreadPriority(-2);
            Process.setCanSelfBackground(false);
            mPolicy.init(mContext, mService, mService, mPM);
            this;
            JVM INSTR monitorenter ;
            mRunning = true;
            notifyAll();
            this;
            JVM INSTR monitorexit ;
            if(StrictMode.conditionallyEnableDebugLogging())
                Slog.i("WindowManager", "Enabled StrictMode for PolicyThread's Looper");
            Looper.loop();
            return;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
        }

        private final Context mContext;
        private final PowerManagerService mPM;
        private final WindowManagerPolicy mPolicy;
        boolean mRunning;
        private final WindowManagerService mService;

        public PolicyThread(WindowManagerPolicy windowmanagerpolicy, WindowManagerService windowmanagerservice, Context context, PowerManagerService powermanagerservice) {
            super("WindowManagerPolicy");
            mRunning = false;
            mPolicy = windowmanagerpolicy;
            mService = windowmanagerservice;
            mContext = context;
            mPM = powermanagerservice;
        }
    }

    static class WMThread extends Thread {

        public void run() {
            WindowManagerService windowmanagerservice;
            Looper.prepare();
            windowmanagerservice = new WindowManagerService(mContext, mPM, mHaveInputMethods, mAllowBootMessages, mOnlyCore);
            Process.setThreadPriority(-4);
            Process.setCanSelfBackground(false);
            this;
            JVM INSTR monitorenter ;
            mService = windowmanagerservice;
            notifyAll();
            this;
            JVM INSTR monitorexit ;
            if(StrictMode.conditionallyEnableDebugLogging())
                Slog.i("WindowManager", "Enabled StrictMode logging for WMThread's Looper");
            Looper.loop();
            return;
            Exception exception;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
        }

        private final boolean mAllowBootMessages;
        private final Context mContext;
        private final boolean mHaveInputMethods;
        private final boolean mOnlyCore;
        private final PowerManagerService mPM;
        WindowManagerService mService;

        public WMThread(Context context, PowerManagerService powermanagerservice, boolean flag, boolean flag1, boolean flag2) {
            super("WindowManager");
            mContext = context;
            mPM = powermanagerservice;
            mHaveInputMethods = flag;
            mAllowBootMessages = flag1;
            mOnlyCore = flag2;
        }
    }

    public static interface WindowChangeListener {

        public abstract void focusChanged();

        public abstract void windowsChanged();
    }

    final class DragInputEventReceiver extends InputEventReceiver {

        public void onInputEvent(InputEvent inputevent) {
            boolean flag = false;
            if(!(inputevent instanceof MotionEvent) || (2 & inputevent.getSource()) == 0 || mDragState == null) goto _L2; else goto _L1
_L1:
            MotionEvent motionevent;
            boolean flag1;
            float f;
            float f1;
            motionevent = (MotionEvent)inputevent;
            flag1 = false;
            f = motionevent.getRawX();
            f1 = motionevent.getRawY();
            motionevent.getAction();
            JVM INSTR tableswitch 0 3: default 88
        //                       0 88
        //                       1 188
        //                       2 127
        //                       3 239;
               goto _L3 _L3 _L4 _L5 _L6
_L3:
            if(flag1)
                synchronized(mWindowMap) {
                    mDragState.endDragLw();
                }
            flag = true;
_L2:
            finishInputEvent(inputevent, flag);
_L7:
            return;
_L5:
            HashMap hashmap2 = mWindowMap;
            hashmap2;
            JVM INSTR monitorenter ;
            mDragState.notifyMoveLw(f, f1);
            hashmap2;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
            Exception exception1;
            exception1;
            Slog.e("WindowManager", "Exception caught by drag handleMotion", exception1);
            finishInputEvent(inputevent, false);
            if(true) goto _L7; else goto _L4
_L4:
            HashMap hashmap1 = mWindowMap;
            hashmap1;
            JVM INSTR monitorenter ;
            flag1 = mDragState.notifyDropLw(f, f1);
            hashmap1;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
            Exception exception;
            exception;
            finishInputEvent(inputevent, false);
            throw exception;
_L6:
            flag1 = true;
            if(true) goto _L3; else goto _L8
_L8:
            exception2;
            hashmap;
            JVM INSTR monitorexit ;
            throw exception2;
        }

        final WindowManagerService this$0;

        public DragInputEventReceiver(InputChannel inputchannel, Looper looper) {
            this$0 = WindowManagerService.this;
            super(inputchannel, looper);
        }
    }

    private final class AnimationRunnable
        implements Runnable {

        public void run() {
            HashMap hashmap = mWindowMap;
            hashmap;
            JVM INSTR monitorenter ;
            mAnimationScheduled = false;
            WindowAnimator windowanimator = mAnimator;
            windowanimator;
            JVM INSTR monitorenter ;
            ArrayList arraylist;
            int i;
            Trace.traceBegin(32L, "wmAnimate");
            arraylist = mAnimator.mWinAnimators;
            arraylist.clear();
            i = mWindows.size();
            Exception exception;
            Exception exception1;
            for(int j = 0; j < i; j++) {
                WindowStateAnimator windowstateanimator = ((WindowState)mWindows.get(j)).mWinAnimator;
                if(windowstateanimator.mSurface != null)
                    arraylist.add(windowstateanimator);
                break MISSING_BLOCK_LABEL_147;
            }

            mAnimator.animate();
            Trace.traceEnd(32L);
            hashmap;
            JVM INSTR monitorexit ;
            return;
            exception1;
            windowanimator;
            JVM INSTR monitorexit ;
            throw exception1;
            exception;
            throw exception;
        }

        final WindowManagerService this$0;

        private AnimationRunnable() {
            this$0 = WindowManagerService.this;
            super();
        }

    }

    class LayoutFields {

        static final int CLEAR_ORIENTATION_CHANGE_COMPLETE = 8;
        static final int SET_FORCE_HIDING_CHANGED = 4;
        static final int SET_TURN_ON_SCREEN = 16;
        static final int SET_UPDATE_ROTATION = 1;
        static final int SET_WALLPAPER_MAY_CHANGE = 2;
        int mAdjResult;
        private float mButtonBrightness;
        boolean mDimming;
        private Session mHoldScreen;
        private boolean mObscured;
        boolean mOrientationChangeComplete;
        private float mScreenBrightness;
        private boolean mSyswin;
        private boolean mUpdateRotation;
        boolean mWallpaperForceHidingChanged;
        boolean mWallpaperMayChange;
        final WindowManagerService this$0;



/*
        static Session access$1102(LayoutFields layoutfields, Session session) {
            layoutfields.mHoldScreen = session;
            return session;
        }

*/



/*
        static boolean access$1202(LayoutFields layoutfields, boolean flag) {
            layoutfields.mSyswin = flag;
            return flag;
        }

*/



/*
        static float access$1302(LayoutFields layoutfields, float f) {
            layoutfields.mScreenBrightness = f;
            return f;
        }

*/



/*
        static float access$1402(LayoutFields layoutfields, float f) {
            layoutfields.mButtonBrightness = f;
            return f;
        }

*/



/*
        static boolean access$1502(LayoutFields layoutfields, boolean flag) {
            layoutfields.mObscured = flag;
            return flag;
        }

*/



/*
        static boolean access$902(LayoutFields layoutfields, boolean flag) {
            layoutfields.mUpdateRotation = flag;
            return flag;
        }

*/

        LayoutFields() {
            this$0 = WindowManagerService.this;
            super();
            mWallpaperForceHidingChanged = false;
            mWallpaperMayChange = false;
            mOrientationChangeComplete = true;
            mAdjResult = 0;
            mHoldScreen = null;
            mObscured = false;
            mDimming = false;
            mSyswin = false;
            mScreenBrightness = -1F;
            mButtonBrightness = -1F;
            mUpdateRotation = false;
        }
    }


    private WindowManagerService(Context context, PowerManagerService powermanagerservice, boolean flag, boolean flag1, boolean flag2) {
        PolicyThread policythread;
        mKeyguardDisabled = false;
        mAllowDisableKeyguard = -1;
        mKeyguardTokenWatcher = new TokenWatcher(new Handler(), "WindowManagerService.mKeyguardTokenWatcher") {

            public void acquired() {
                if(shouldAllowDisableKeyguard()) {
                    mPolicy.enableKeyguard(false);
                    mKeyguardDisabled = true;
                } else {
                    Log.v("WindowManager", "Not disabling keyguard since device policy is enforced");
                }
            }

            public void released() {
                mPolicy.enableKeyguard(true);
                TokenWatcher tokenwatcher = mKeyguardTokenWatcher;
                tokenwatcher;
                JVM INSTR monitorenter ;
                mKeyguardDisabled = false;
                mKeyguardTokenWatcher.notifyAll();
                return;
            }

            final WindowManagerService this$0;

             {
                this$0 = WindowManagerService.this;
                super(handler, s);
            }
        };
        mBroadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                mPolicy.enableKeyguard(true);
                TokenWatcher tokenwatcher = mKeyguardTokenWatcher;
                tokenwatcher;
                JVM INSTR monitorenter ;
                mAllowDisableKeyguard = -1;
                mKeyguardDisabled = false;
                return;
            }

            final WindowManagerService this$0;

             {
                this$0 = WindowManagerService.this;
                super();
            }
        };
        mPolicy = PolicyManager.makeNewWindowManager();
        mSessions = new HashSet();
        mWindowMap = new HashMap();
        mTokenMap = new HashMap();
        mExitingTokens = new ArrayList();
        mAppTokens = new ArrayList();
        mAnimatingAppTokens = new ArrayList();
        mExitingAppTokens = new ArrayList();
        mFinishedStarting = new ArrayList();
        mWindows = new ArrayList();
        mFakeWindows = new ArrayList();
        mResizingWindows = new ArrayList();
        mPendingRemove = new ArrayList();
        mPendingRemoveTmp = new WindowState[20];
        mDestroySurface = new ArrayList();
        mLosingFocus = new ArrayList();
        mWaitingForDrawn = new ArrayList();
        mRelayoutWhileAnimating = new ArrayList();
        mRebuildTmp = new WindowState[20];
        mTmpFloats = new float[9];
        mDisplayEnabled = false;
        mSystemBooted = false;
        mForceDisplayEnabled = false;
        mShowingBootMessages = false;
        mDisplaySizeLock = new Object();
        mInitialDisplayWidth = 0;
        mInitialDisplayHeight = 0;
        mBaseDisplayWidth = 0;
        mBaseDisplayHeight = 0;
        mCurDisplayWidth = 0;
        mCurDisplayHeight = 0;
        mAppDisplayWidth = 0;
        mAppDisplayHeight = 0;
        mSmallestDisplayWidth = 0;
        mSmallestDisplayHeight = 0;
        mLargestDisplayWidth = 0;
        mLargestDisplayHeight = 0;
        mRotation = 0;
        mForcedAppOrientation = -1;
        mAltOrientation = false;
        mRotationWatchers = new ArrayList();
        mSystemDecorRect = new Rect();
        mSystemDecorLayer = 0;
        mPendingLayoutChanges = 0;
        mLayoutNeeded = true;
        mTraversalScheduled = false;
        mDisplayFrozen = false;
        mWaitingForConfig = false;
        mWindowsFreezingScreen = false;
        mAppsFreezingScreen = 0;
        mLastWindowForcedOrientation = -1;
        mLayoutSeq = 0;
        mLastStatusBarVisibility = 0;
        mCurConfiguration = new Configuration();
        mNextAppTransition = -1;
        mNextAppTransitionType = 0;
        mAppTransitionReady = false;
        mAppTransitionRunning = false;
        mAppTransitionTimeout = false;
        mStartingIconInTransition = false;
        mSkipAppTransitionAnimation = false;
        mOpeningApps = new ArrayList();
        mClosingApps = new ArrayList();
        mDisplayMetrics = new DisplayMetrics();
        mRealDisplayMetrics = new DisplayMetrics();
        mTmpDisplayMetrics = new DisplayMetrics();
        mCompatDisplayMetrics = new DisplayMetrics();
        mH = new H();
        mChoreographer = Choreographer.getInstance();
        mCurrentFocus = null;
        mLastFocus = null;
        mInputMethodTarget = null;
        mInputMethodWindow = null;
        mInputMethodDialogs = new ArrayList();
        mWallpaperTokens = new ArrayList();
        mWallpaperTarget = null;
        mLowerWallpaperTarget = null;
        mUpperWallpaperTarget = null;
        mLastWallpaperX = -1F;
        mLastWallpaperY = -1F;
        mLastWallpaperXStep = -1F;
        mLastWallpaperYStep = -1F;
        mFocusedApp = null;
        mWindowAnimationScale = 1.0F;
        mTransitionAnimationScale = 1.0F;
        mAnimatorDurationScale = 1.0F;
        mDragState = null;
        mInnerFields = new LayoutFields();
        mAnimationRunnable = new AnimationRunnable();
        mInTouchMode = true;
        mWindowChangeListeners = new ArrayList();
        mWindowsChanged = false;
        mTempConfiguration = new Configuration();
        mInputMonitor = new InputMonitor(this);
        mInLayout = false;
        mContext = context;
        mHaveInputMethods = flag;
        mAllowBootMessages = flag1;
        mOnlyCore = flag2;
        mLimitedAlphaCompositing = context.getResources().getBoolean(0x1110007);
        mHeadless = "1".equals(SystemProperties.get("ro.config.headless", "0"));
        mPowerManager = powermanagerservice;
        mPowerManager.setPolicy(mPolicy);
        PowerManager powermanager = (PowerManager)context.getSystemService("power");
        mScreenFrozenLock = powermanager.newWakeLock(1, "SCREEN_FROZEN");
        mScreenFrozenLock.setReferenceCounted(false);
        mActivityManager = ActivityManagerNative.getDefault();
        mBatteryStats = BatteryStatsService.getService();
        mWindowAnimationScale = android.provider.Settings.System.getFloat(context.getContentResolver(), "window_animation_scale", mWindowAnimationScale);
        mTransitionAnimationScale = android.provider.Settings.System.getFloat(context.getContentResolver(), "transition_animation_scale", mTransitionAnimationScale);
        mAnimatorDurationScale = android.provider.Settings.System.getFloat(context.getContentResolver(), "animator_duration_scale", mTransitionAnimationScale);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter);
        mHoldingScreenWakeLock = powermanager.newWakeLock(0x2000000a, "KEEP_SCREEN_ON_FLAG");
        mHoldingScreenWakeLock.setReferenceCounted(false);
        mInputManager = new InputManagerService(context, mInputMonitor);
        mAnimator = new WindowAnimator(this, context, mPolicy);
        policythread = new PolicyThread(mPolicy, this, context, powermanagerservice);
        policythread.start();
        policythread;
        JVM INSTR monitorenter ;
_L3:
        boolean flag3 = policythread.mRunning;
        if(flag3) goto _L2; else goto _L1
_L1:
        Exception exception;
        try {
            policythread.wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally { }
        if(true) goto _L3; else goto _L2
_L2:
        policythread;
        JVM INSTR monitorexit ;
        mInputManager.start();
        Watchdog.getInstance().addMonitor(this);
        mFxSession = new SurfaceSession();
        Surface.openTransaction();
        createWatermark();
        Surface.closeTransaction();
        return;
        policythread;
        JVM INSTR monitorexit ;
        throw exception;
    }


    private void addAppTokenToAnimating(int i, AppWindowToken appwindowtoken) {
        if(i == 0 || i == mAnimatingAppTokens.size()) {
            mAnimatingAppTokens.add(i, appwindowtoken);
        } else {
            AppWindowToken appwindowtoken1 = (AppWindowToken)mAppTokens.get(i + 1);
            mAnimatingAppTokens.add(mAnimatingAppTokens.indexOf(appwindowtoken1), appwindowtoken);
        }
    }

    private void addWindowToListInOrderLocked(WindowState windowstate, boolean flag) {
        WindowToken windowtoken;
        ArrayList arraylist;
        int i;
        WindowState windowstate1;
        windowstate.mClient;
        windowtoken = windowstate.mToken;
        arraylist = mWindows;
        i = arraylist.size();
        windowstate1 = windowstate.mAttachedWindow;
        if(windowstate1 != null) goto _L2; else goto _L1
_L1:
        int k1 = windowtoken.windows.size();
        if(windowtoken.appWindowToken == null) goto _L4; else goto _L3
_L3:
        int j2 = k1 - 1;
        if(j2 < 0) goto _L6; else goto _L5
_L5:
        if(windowstate.mAttrs.type == 1) {
            placeWindowBefore((WindowState)windowtoken.windows.get(0), windowstate);
            k1 = 0;
        } else {
            AppWindowToken appwindowtoken2 = windowstate.mAppToken;
            if(appwindowtoken2 != null && windowtoken.windows.get(j2) == appwindowtoken2.startingWindow) {
                placeWindowBefore((WindowState)windowtoken.windows.get(j2), windowstate);
                k1--;
            } else {
                int i4 = findIdxBasedOnAppTokens(windowstate);
                if(i4 != -1) {
                    arraylist.add(i4 + 1, windowstate);
                    mWindowsChanged = true;
                }
            }
        }
_L13:
        if(flag)
            windowtoken.windows.add(k1, windowstate);
_L18:
        if(windowstate.mAppToken != null && flag)
            windowstate.mAppToken.allAppWindows.add(windowstate);
        return;
_L6:
        WindowState windowstate4;
        int l2;
        int k2 = mAnimatingAppTokens.size();
        windowstate4 = null;
        l2 = k2 - 1;
_L14:
        if(l2 < 0) goto _L8; else goto _L7
_L7:
        AppWindowToken appwindowtoken1 = (AppWindowToken)mAnimatingAppTokens.get(l2);
        if(appwindowtoken1 != windowtoken) goto _L10; else goto _L9
_L9:
        l2--;
_L8:
        if(windowstate4 == null) goto _L12; else goto _L11
_L11:
        WindowToken windowtoken2 = (WindowToken)mTokenMap.get(windowstate4.mClient.asBinder());
        if(windowtoken2 != null && windowtoken2.windows.size() > 0) {
            WindowState windowstate6 = (WindowState)windowtoken2.windows.get(0);
            if(windowstate6.mSubLayer < 0)
                windowstate4 = windowstate6;
        }
        placeWindowBefore(windowstate4, windowstate);
          goto _L13
_L10:
        if(!((WindowToken) (appwindowtoken1)).sendingToBottom && ((WindowToken) (appwindowtoken1)).windows.size() > 0)
            windowstate4 = (WindowState)((WindowToken) (appwindowtoken1)).windows.get(0);
        l2--;
          goto _L14
_L16:
        l2--;
_L12:
        if(l2 < 0)
            break; /* Loop/switch isn't completed */
        AppWindowToken appwindowtoken = (AppWindowToken)mAnimatingAppTokens.get(l2);
        int l3 = ((WindowToken) (appwindowtoken)).windows.size();
        if(l3 <= 0)
            continue; /* Loop/switch isn't completed */
        windowstate4 = (WindowState)((WindowToken) (appwindowtoken)).windows.get(l3 - 1);
        break; /* Loop/switch isn't completed */
        if(true) goto _L16; else goto _L15
_L15:
        if(windowstate4 != null) {
            WindowToken windowtoken1 = (WindowToken)mTokenMap.get(windowstate4.mClient.asBinder());
            if(windowtoken1 != null) {
                int k3 = windowtoken1.windows.size();
                if(k3 > 0) {
                    WindowState windowstate5 = (WindowState)windowtoken1.windows.get(k3 - 1);
                    if(windowstate5.mSubLayer >= 0)
                        windowstate4 = windowstate5;
                }
            }
            placeWindowAfter(windowstate4, windowstate);
        } else {
label0:
            {
                int i3 = windowstate.mBaseLayer;
                for(int j3 = 0; j3 < i && ((WindowState)arraylist.get(j3)).mBaseLayer <= i3; j3++)
                    break label0;

                arraylist.add(j3, windowstate);
                mWindowsChanged = true;
            }
        }
          goto _L13
_L4:
        int l1;
        int i2;
        l1 = windowstate.mBaseLayer;
        i2 = i - 1;
_L17:
label1:
        {
            if(i2 >= 0) {
                if(((WindowState)arraylist.get(i2)).mBaseLayer > l1)
                    break label1;
                i2++;
            }
            if(i2 < 0)
                i2 = 0;
            arraylist.add(i2, windowstate);
            mWindowsChanged = true;
        }
          goto _L13
        i2--;
          goto _L17
_L2:
        int j;
        int k;
        int l;
        WindowState windowstate2;
        int i1;
        j = windowtoken.windows.size();
        k = windowstate.mSubLayer;
        l = 0x80000000;
        windowstate2 = null;
        i1 = 0;
_L19:
        if(i1 < j) {
            WindowState windowstate3 = (WindowState)windowtoken.windows.get(i1);
            int j1 = windowstate3.mSubLayer;
            if(j1 >= l) {
                l = j1;
                windowstate2 = windowstate3;
            }
            if(k < 0) {
                if(j1 < k)
                    break MISSING_BLOCK_LABEL_830;
                if(flag)
                    windowtoken.windows.add(i1, windowstate);
                if(j1 >= 0)
                    windowstate3 = windowstate1;
                placeWindowBefore(windowstate3, windowstate);
            } else {
                if(j1 <= k)
                    break MISSING_BLOCK_LABEL_830;
                if(flag)
                    windowtoken.windows.add(i1, windowstate);
                placeWindowBefore(windowstate3, windowstate);
            }
        }
        if(i1 >= j) {
            if(flag)
                windowtoken.windows.add(windowstate);
            if(k < 0) {
                placeWindowBefore(windowstate1, windowstate);
            } else {
                if(l < 0)
                    windowstate2 = windowstate1;
                placeWindowAfter(windowstate2, windowstate);
            }
        }
          goto _L18
        i1++;
          goto _L19
    }

    private void adjustDisplaySizeRanges(int i, int j, int k) {
        int l = mPolicy.getConfigDisplayWidth(j, k, i);
        if(l < mSmallestDisplayWidth)
            mSmallestDisplayWidth = l;
        if(l > mLargestDisplayWidth)
            mLargestDisplayWidth = l;
        int i1 = mPolicy.getConfigDisplayHeight(j, k, i);
        if(i1 < mSmallestDisplayHeight)
            mSmallestDisplayHeight = i1;
        if(i1 > mLargestDisplayHeight)
            mLargestDisplayHeight = i1;
    }

    private int animateAwayWallpaperLocked() {
        int i = 0;
        WindowState _tmp = mWallpaperTarget;
        if(mLowerWallpaperTarget != null && mLowerWallpaperTarget.mAppToken != null && ((WindowToken) (mLowerWallpaperTarget.mAppToken)).hidden) {
            mUpperWallpaperTarget = null;
            mLowerWallpaperTarget = null;
            i = 0 | 8;
        }
        LayoutFields layoutfields = mInnerFields;
        layoutfields.mAdjResult = layoutfields.mAdjResult | adjustWallpaperWindowsLocked();
        return i;
    }

    private boolean applyAnimationLocked(AppWindowToken appwindowtoken, android.view.WindowManager.LayoutParams layoutparams, int i, boolean flag) {
        boolean flag1;
        Animation animation;
        flag1 = true;
        if(!okToDisplay())
            break MISSING_BLOCK_LABEL_467;
        boolean flag2 = false;
        if(mNextAppTransitionType == flag1) {
            String s = mNextAppTransitionPackage;
            int k;
            if(flag)
                k = mNextAppTransitionEnter;
            else
                k = mNextAppTransitionExit;
            animation = loadAnimation(s, k);
        } else
        if(mNextAppTransitionType == 2) {
            animation = createScaleUpAnimationLocked(i, flag);
            flag2 = true;
        } else {
label0:
            {
                if(mNextAppTransitionType != 3 && mNextAppTransitionType != 4)
                    break label0;
                boolean flag3;
                if(mNextAppTransitionType == 4)
                    flag3 = flag1;
                else
                    flag3 = false;
                animation = createThumbnailAnimationLocked(i, flag, false, flag3);
                flag2 = true;
            }
        }
_L12:
        if(animation != null)
            appwindowtoken.mAppAnimator.setAnimation(animation, flag2);
_L14:
        int j;
        if(appwindowtoken.mAppAnimator.animation == null)
            flag1 = false;
        return flag1;
        j = 0;
        i;
        JVM INSTR lookupswitch 10: default 256
    //                   4102: 273
    //                   4104: 309
    //                   4106: 347
    //                   4109: 385
    //                   4110: 423
    //                   8199: 290
    //                   8201: 328
    //                   8203: 366
    //                   8204: 404
    //                   8207: 442;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11
_L1:
        break; /* Loop/switch isn't completed */
_L11:
        break MISSING_BLOCK_LABEL_442;
_L13:
        if(j != 0)
            animation = loadAnimation(layoutparams, j);
        else
            animation = null;
          goto _L12
_L2:
        if(flag)
            j = 4;
        else
            j = 5;
          goto _L13
_L7:
        if(flag)
            j = 6;
        else
            j = 7;
          goto _L13
_L3:
        if(flag)
            j = 8;
        else
            j = 9;
          goto _L13
_L8:
        if(flag)
            j = 10;
        else
            j = 11;
          goto _L13
_L4:
        if(flag)
            j = 12;
        else
            j = 13;
          goto _L13
_L9:
        if(flag)
            j = 14;
        else
            j = 15;
          goto _L13
_L5:
        if(flag)
            j = 16;
        else
            j = 17;
          goto _L13
_L10:
        if(flag)
            j = 18;
        else
            j = 19;
          goto _L13
_L6:
        if(flag)
            j = 20;
        else
            j = 21;
          goto _L13
        if(flag)
            j = 22;
        else
            j = 23;
          goto _L13
        appwindowtoken.mAppAnimator.clearAnimation();
          goto _L14
    }

    private final void assignLayersLocked() {
        int i;
        int j;
        int k;
        int l;
        i = mWindows.size();
        j = 0;
        k = 0;
        l = 0;
_L7:
        if(l >= i) goto _L2; else goto _L1
_L1:
        WindowState windowstate;
        WindowStateAnimator windowstateanimator;
        windowstate = (WindowState)mWindows.get(l);
        windowstateanimator = windowstate.mWinAnimator;
        boolean flag = false;
        int i1 = windowstate.mLayer;
        int j1;
        if(windowstate.mBaseLayer == j || windowstate.mIsImWindow || l > 0 && windowstate.mIsWallpaper) {
            k += 5;
            windowstate.mLayer = k;
        } else {
            k = windowstate.mBaseLayer;
            j = k;
            windowstate.mLayer = k;
        }
        if(windowstate.mLayer != i1)
            flag = true;
        j1 = windowstateanimator.mAnimLayer;
        if(windowstate.mTargetAppToken != null)
            windowstateanimator.mAnimLayer = windowstate.mLayer + windowstate.mTargetAppToken.mAppAnimator.animLayerAdjustment;
        else
        if(windowstate.mAppToken != null)
            windowstateanimator.mAnimLayer = windowstate.mLayer + windowstate.mAppToken.mAppAnimator.animLayerAdjustment;
        else
            windowstateanimator.mAnimLayer = windowstate.mLayer;
        if(!windowstate.mIsImWindow) goto _L4; else goto _L3
_L3:
        windowstateanimator.mAnimLayer = windowstateanimator.mAnimLayer + mInputMethodAnimLayerAdjustment;
_L5:
        if(windowstateanimator.mAnimLayer != j1)
            flag = true;
        if(flag && mAnimator.isDimming(windowstateanimator))
            scheduleAnimationLocked();
        l++;
        continue; /* Loop/switch isn't completed */
_L4:
        if(windowstate.mIsWallpaper)
            windowstateanimator.mAnimLayer = windowstateanimator.mAnimLayer + mWallpaperAnimLayerAdjustment;
        if(true) goto _L5; else goto _L2
_L2:
        return;
        if(true) goto _L7; else goto _L6
_L6:
    }

    static boolean canBeImeTarget(WindowState windowstate) {
        int i = 0x20008 & windowstate.mAttrs.flags;
        boolean flag;
        if(i == 0 || i == 0x20008 || windowstate.mAttrs.type == 3)
            flag = windowstate.isVisibleOrAdding();
        else
            flag = false;
        return flag;
    }

    private int computeCompatSmallestWidth(boolean flag, DisplayMetrics displaymetrics, int i, int j) {
        mTmpDisplayMetrics.setTo(displaymetrics);
        DisplayMetrics displaymetrics1 = mTmpDisplayMetrics;
        int k;
        int l;
        if(flag) {
            k = j;
            l = i;
        } else {
            k = i;
            l = j;
        }
        return reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(0, 0, displaymetrics1, k, l), 1, displaymetrics1, l, k), 2, displaymetrics1, k, l), 3, displaymetrics1, l, k);
    }

    private WindowState computeFocusedWindowLocked() {
        WindowState windowstate;
        WindowState windowstate1;
        int i;
        Object obj;
        WindowState windowstate2;
        AppWindowToken appwindowtoken;
        windowstate = null;
        windowstate1 = null;
        i = -1 + mAppTokens.size();
        int j;
        if(i >= 0)
            obj = (AppWindowToken)mAppTokens.get(i);
        else
            obj = null;
        j = -1 + mWindows.size();
        if(j < 0)
            break MISSING_BLOCK_LABEL_207;
        windowstate2 = (WindowState)mWindows.get(j);
        appwindowtoken = windowstate2.mAppToken;
        if(appwindowtoken == null || !appwindowtoken.removed && !((WindowToken) (appwindowtoken)).sendingToBottom)
            break; /* Loop/switch isn't completed */
_L8:
        j--;
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_44;
_L1:
        int k;
        if(appwindowtoken == null || obj == null || appwindowtoken == obj || windowstate2.mAttrs.type == 3)
            continue; /* Loop/switch isn't completed */
        k = i;
_L6:
        if(i <= 0)
            break; /* Loop/switch isn't completed */
        if(obj != mFocusedApp) goto _L4; else goto _L3
_L3:
        return windowstate;
_L4:
        i--;
        obj = (WindowToken)mAppTokens.get(i);
        if(obj != appwindowtoken) goto _L6; else goto _L5
_L5:
        if(appwindowtoken != obj) {
            i = k;
            obj = (WindowToken)mAppTokens.get(i);
        }
        if(!windowstate2.canReceiveKeys()) goto _L8; else goto _L7
_L7:
        windowstate1 = windowstate2;
        windowstate = windowstate1;
          goto _L3
    }

    private static float computePivot(int i, float f) {
        float f1 = f - 1.0F;
        float f2;
        if(Math.abs(f1) < 1E-04F)
            f2 = i;
        else
            f2 = (float)(-i) / f1;
        return f2;
    }

    private void computeSizeRangesAndScreenLayout(boolean flag, int i, int j, float f, Configuration configuration) {
        int k;
        int l;
        int i1;
        if(flag) {
            k = j;
            l = i;
        } else {
            k = i;
            l = j;
        }
        mSmallestDisplayWidth = 0x40000000;
        mSmallestDisplayHeight = 0x40000000;
        mLargestDisplayWidth = 0;
        mLargestDisplayHeight = 0;
        adjustDisplaySizeRanges(0, k, l);
        adjustDisplaySizeRanges(1, l, k);
        adjustDisplaySizeRanges(2, k, l);
        adjustDisplaySizeRanges(3, l, k);
        i1 = reduceConfigLayout(reduceConfigLayout(reduceConfigLayout(reduceConfigLayout(36, 0, f, k, l), 1, f, l, k), 2, f, k, l), 3, f, l, k);
        configuration.smallestScreenWidthDp = (int)((float)mSmallestDisplayWidth / f);
        configuration.screenLayout = i1;
    }

    private Animation createExitAnimationLocked(int i, int j) {
        AlphaAnimation alphaanimation1;
        if(i == 4110 || i == 8207) {
            AlphaAnimation alphaanimation = new AlphaAnimation(1.0F, 0.0F);
            alphaanimation.setDetachWallpaper(true);
            alphaanimation.setDuration(j);
            alphaanimation1 = alphaanimation;
        } else {
            AlphaAnimation alphaanimation2 = new AlphaAnimation(1.0F, 1.0F);
            alphaanimation2.setDuration(j);
            alphaanimation1 = alphaanimation2;
        }
        return alphaanimation1;
    }

    private Animation createScaleUpAnimationLocked(int i, boolean flag) {
        int j;
        switch(i) {
        default:
            j = 300;
            break;

        case 4102: 
        case 8199: 
            break MISSING_BLOCK_LABEL_206;
        }
_L1:
        Object obj;
        if(flag) {
            float f = (float)mNextAppTransitionStartWidth / (float)mAppDisplayWidth;
            float f1 = (float)mNextAppTransitionStartHeight / (float)mAppDisplayHeight;
            ScaleAnimation scaleanimation = new ScaleAnimation(f, 1.0F, f1, 1.0F, computePivot(mNextAppTransitionStartX, f), computePivot(mNextAppTransitionStartY, f1));
            scaleanimation.setDuration(j);
            AnimationSet animationset = new AnimationSet(true);
            AlphaAnimation alphaanimation = new AlphaAnimation(0.0F, 1.0F);
            scaleanimation.setDuration(j);
            animationset.addAnimation(scaleanimation);
            alphaanimation.setDuration(j);
            animationset.addAnimation(alphaanimation);
            animationset.setDetachWallpaper(true);
            obj = animationset;
        } else {
            obj = createExitAnimationLocked(i, j);
        }
        ((Animation) (obj)).setFillAfter(true);
        ((Animation) (obj)).setInterpolator(AnimationUtils.loadInterpolator(mContext, 0x10c0003));
        ((Animation) (obj)).initialize(mAppDisplayWidth, mAppDisplayHeight, mAppDisplayWidth, mAppDisplayHeight);
        return ((Animation) (obj));
        j = mContext.getResources().getInteger(0x10e0000);
          goto _L1
    }

    private Animation createThumbnailAnimationLocked(int i, boolean flag, boolean flag1, boolean flag2) {
        float f;
        float f1;
        int l;
        int i1;
        Object obj;
        int j = mNextAppTransitionThumbnail.getWidth();
        int k;
        float f4;
        float f5;
        ScaleAnimation scaleanimation1;
        AnimationSet animationset;
        AlphaAnimation alphaanimation;
        if(j > 0)
            f = j;
        else
            f = 1.0F;
        k = mNextAppTransitionThumbnail.getHeight();
        if(k > 0)
            f1 = k;
        else
            f1 = 1.0F;
        if(flag2)
            l = 270;
        else
            l = 0;
        switch(i) {
        default:
            if(flag2)
                i1 = 250;
            else
                i1 = 300;
            break;

        case 4102: 
        case 8199: 
            break MISSING_BLOCK_LABEL_298;
        }
        if(flag1) {
            f4 = (float)mAppDisplayWidth / f;
            f5 = (float)mAppDisplayHeight / f1;
            scaleanimation1 = new ScaleAnimation(1.0F, f4, 1.0F, f5, computePivot(mNextAppTransitionStartX, 1.0F / f4), computePivot(mNextAppTransitionStartY, 1.0F / f5));
            animationset = new AnimationSet(true);
            alphaanimation = new AlphaAnimation(1.0F, 0.0F);
            scaleanimation1.setDuration(i1);
            scaleanimation1.setInterpolator(new DecelerateInterpolator(1.5F));
            animationset.addAnimation(scaleanimation1);
            alphaanimation.setDuration(i1);
            animationset.addAnimation(alphaanimation);
            animationset.setFillBefore(true);
            if(l > 0)
                animationset.setStartOffset(l);
            obj = animationset;
        } else
        if(flag) {
            float f2 = f / (float)mAppDisplayWidth;
            float f3 = f1 / (float)mAppDisplayHeight;
            ScaleAnimation scaleanimation = new ScaleAnimation(f2, 1.0F, f3, 1.0F, computePivot(mNextAppTransitionStartX, f2), computePivot(mNextAppTransitionStartY, f3));
            scaleanimation.setDuration(i1);
            scaleanimation.setInterpolator(new DecelerateInterpolator(1.5F));
            scaleanimation.setFillBefore(true);
            if(l > 0)
                scaleanimation.setStartOffset(l);
            obj = scaleanimation;
        } else
        if(flag2) {
            obj = new AlphaAnimation(1.0F, 0.0F);
            ((Animation) (obj)).setStartOffset(0L);
            ((Animation) (obj)).setDuration(l - 120);
            ((Animation) (obj)).setBackgroundColor(0xff000000);
        } else {
            obj = createExitAnimationLocked(i, i1);
        }
        ((Animation) (obj)).setFillAfter(true);
        ((Animation) (obj)).setInterpolator(AnimationUtils.loadInterpolator(mContext, 0x10c0001));
        ((Animation) (obj)).initialize(mAppDisplayWidth, mAppDisplayHeight, mAppDisplayWidth, mAppDisplayHeight);
        return ((Animation) (obj));
        i1 = mContext.getResources().getInteger(0x10e0000);
        break MISSING_BLOCK_LABEL_86;
    }

    private int findIdxBasedOnAppTokens(WindowState windowstate) {
        ArrayList arraylist;
        int i;
        arraylist = mWindows;
        i = arraylist.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        for(j = i - 1; j >= 0; j--)
            if(((WindowState)arraylist.get(j)).mAppToken == windowstate.mAppToken)
                continue; /* Loop/switch isn't completed */

        j = -1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private WindowState findWindow(int i) {
        if(i != -1) goto _L2; else goto _L1
_L1:
        WindowState windowstate = getFocusedWindow();
_L6:
        return windowstate;
_L2:
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        int j;
        int k;
        arraylist = mWindows;
        j = arraylist.size();
        k = 0;
_L4:
        if(k >= j)
            break; /* Loop/switch isn't completed */
        windowstate = (WindowState)arraylist.get(k);
        if(System.identityHashCode(windowstate) == i)
            continue; /* Loop/switch isn't completed */
        break MISSING_BLOCK_LABEL_76;
        Exception exception;
        exception;
        throw exception;
        k++;
        if(true) goto _L4; else goto _L3
_L3:
        hashmap;
        JVM INSTR monitorexit ;
        windowstate = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private int findWindowOffsetLocked(int i) {
        int j = mWindows.size();
        if(i < mAnimatingAppTokens.size()) goto _L2; else goto _L1
_L1:
        int l1 = j;
_L5:
        if(l1 <= 0) goto _L2; else goto _L3
_L3:
        l1--;
        if(((WindowState)mWindows.get(l1)).getAppToken() == null) goto _L5; else goto _L4
_L4:
        int k = l1 + 1;
_L7:
        return k;
_L2:
        do {
            if(i <= 0)
                break;
            AppWindowToken appwindowtoken = (AppWindowToken)mAnimatingAppTokens.get(i - 1);
            if(((WindowToken) (appwindowtoken)).sendingToBottom) {
                i--;
                continue;
            }
            for(int l = ((WindowToken) (appwindowtoken)).windows.size(); l > 0;) {
                l--;
                WindowState windowstate = (WindowState)((WindowToken) (appwindowtoken)).windows.get(l);
                int i1 = windowstate.mChildWindows.size();
                do {
                    if(i1 <= 0)
                        break;
                    i1--;
                    WindowState windowstate1 = (WindowState)windowstate.mChildWindows.get(i1);
                    if(windowstate1.mSubLayer >= 0) {
                        int k1 = j - 1;
                        while(k1 >= 0)  {
                            if(mWindows.get(k1) == windowstate1) {
                                k = k1 + 1;
                                continue; /* Loop/switch isn't completed */
                            }
                            k1--;
                        }
                    }
                } while(true);
                int j1 = j - 1;
                while(j1 >= 0)  {
                    if(mWindows.get(j1) == windowstate) {
                        k = j1 + 1;
                        continue; /* Loop/switch isn't completed */
                    }
                    j1--;
                }
            }

            i--;
        } while(true);
        k = 0;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void finishUpdateFocusedWindowAfterAssignLayersLocked(boolean flag) {
        mInputMonitor.setInputFocusLw(mCurrentFocus, flag);
    }

    static float fixScale(float f) {
        if(f >= 0.0F) goto _L2; else goto _L1
_L1:
        f = 0.0F;
_L4:
        return Math.abs(f);
_L2:
        if(f > 20F)
            f = 20F;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private com.android.server.AttributeCache.Entry getCachedAnimations(android.view.WindowManager.LayoutParams layoutparams) {
        com.android.server.AttributeCache.Entry entry;
        if(layoutparams != null && layoutparams.windowAnimations != 0) {
            String s;
            int i;
            if(layoutparams.packageName != null)
                s = layoutparams.packageName;
            else
                s = "android";
            i = layoutparams.windowAnimations;
            if((0xff000000 & i) == 0x1000000)
                s = "android";
            entry = AttributeCache.instance().get(s, i, com.android.internal.R.styleable.WindowAnimation);
        } else {
            entry = null;
        }
        return entry;
    }

    private com.android.server.AttributeCache.Entry getCachedAnimations(String s, int i) {
        com.android.server.AttributeCache.Entry entry;
        if(s != null) {
            if((0xff000000 & i) == 0x1000000)
                s = "android";
            entry = AttributeCache.instance().get(s, i, com.android.internal.R.styleable.WindowAnimation);
        } else {
            entry = null;
        }
        return entry;
    }

    private WindowState getFocusedWindow() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = getFocusedWindowLocked();
        return windowstate;
    }

    private WindowState getFocusedWindowLocked() {
        return mCurrentFocus;
    }

    static int getPropertyInt(String as[], int i, int j, int k, DisplayMetrics displaymetrics) {
        if(i >= as.length) goto _L2; else goto _L1
_L1:
        String s = as[i];
        if(s == null || s.length() <= 0) goto _L2; else goto _L3
_L3:
        int i1 = Integer.parseInt(s);
        int l = i1;
_L5:
        return l;
        Exception exception;
        exception;
_L2:
        if(j == 0)
            l = k;
        else
            l = (int)TypedValue.applyDimension(j, k, displaymetrics);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private int handleAnimatingStoppedAndTransitionLocked() {
        mAppTransitionRunning = false;
        for(int i = -1 + mAnimatingAppTokens.size(); i >= 0; i--)
            ((AppWindowToken)mAnimatingAppTokens.get(i)).sendingToBottom = false;

        mAnimatingAppTokens.clear();
        mAnimatingAppTokens.addAll(mAppTokens);
        rebuildAppWindowListLocked();
        int j = false | true;
        LayoutFields layoutfields = mInnerFields;
        layoutfields.mAdjResult = 2 | layoutfields.mAdjResult;
        moveInputMethodWindowsIfNeededLocked(true);
        mInnerFields.mWallpaperMayChange = true;
        mFocusMayChange = true;
        return j;
    }

    private void handleNotObscuredLocked(WindowState windowstate, long l, int i, int j) {
        android.view.WindowManager.LayoutParams layoutparams;
        int k;
        boolean flag;
        layoutparams = windowstate.mAttrs;
        k = layoutparams.flags;
        flag = windowstate.isDisplayedLw();
        if(windowstate.mHasSurface) {
            if((k & 0x80) != 0)
                mInnerFields.mHoldScreen = windowstate.mSession;
            if(!mInnerFields.mSyswin && windowstate.mAttrs.screenBrightness >= 0.0F && mInnerFields.mScreenBrightness < 0.0F)
                mInnerFields.mScreenBrightness = windowstate.mAttrs.screenBrightness;
            if(!mInnerFields.mSyswin && windowstate.mAttrs.buttonBrightness >= 0.0F && mInnerFields.mButtonBrightness < 0.0F)
                mInnerFields.mButtonBrightness = windowstate.mAttrs.buttonBrightness;
            if(flag && (layoutparams.type == 2008 || layoutparams.type == 2004 || layoutparams.type == 2010))
                mInnerFields.mSyswin = true;
        }
        boolean flag1;
        if(flag && windowstate.isOpaqueDrawn())
            flag1 = true;
        else
            flag1 = false;
        if(!flag1 || !windowstate.isFullscreen(i, j)) goto _L2; else goto _L1
_L1:
        mInnerFields.mObscured = true;
_L4:
        return;
_L2:
        if(flag && (k & 2) != 0 && (windowstate.mAppToken == null || !windowstate.mAppToken.hiddenRequested) && !windowstate.mExiting && !mInnerFields.mDimming) {
            mInnerFields.mDimming = true;
            WindowStateAnimator windowstateanimator = windowstate.mWinAnimator;
            if(!mAnimator.isDimming(windowstateanimator)) {
                int i1;
                int j1;
                WindowAnimator windowanimator;
                float f;
                if(layoutparams.type == 2021) {
                    i1 = mCurDisplayWidth;
                    j1 = mCurDisplayHeight;
                } else {
                    i1 = i;
                    j1 = j;
                }
                windowanimator = mAnimator;
                if(windowstate.mExiting)
                    f = 0.0F;
                else
                    f = windowstate.mAttrs.dimAmount;
                windowanimator.startDimming(windowstateanimator, f, i1, j1);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean isSystemSecure() {
        boolean flag;
        if("1".equals(SystemProperties.get("ro.secure", "1")) && "0".equals(SystemProperties.get("ro.debuggable", "0")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private Animation loadAnimation(String s, int i) {
        int j = 0;
        Context context = mContext;
        if(i >= 0) {
            com.android.server.AttributeCache.Entry entry = getCachedAnimations(s, i);
            if(entry != null) {
                context = entry.context;
                j = i;
            }
        }
        Animation animation;
        if(j != 0)
            animation = AnimationUtils.loadAnimation(context, j);
        else
            animation = null;
        return animation;
    }

    static void logSurface(Surface surface, String s, String s1, RuntimeException runtimeexception) {
        String s2 = (new StringBuilder()).append("  SURFACE ").append(surface).append(": ").append(s1).append(" / ").append(s).toString();
        if(runtimeexception != null)
            Slog.i("WindowManager", s2, runtimeexception);
        else
            Slog.i("WindowManager", s2);
    }

    static void logSurface(WindowState windowstate, String s, RuntimeException runtimeexception) {
        String s1 = (new StringBuilder()).append("  SURFACE ").append(s).append(": ").append(windowstate).toString();
        if(runtimeexception != null)
            Slog.i("WindowManager", s1, runtimeexception);
        else
            Slog.i("WindowManager", s1);
    }

    public static WindowManagerService main(Context context, PowerManagerService powermanagerservice, boolean flag, boolean flag1, boolean flag2) {
        WMThread wmthread;
        wmthread = new WMThread(context, powermanagerservice, flag, flag1, flag2);
        wmthread.start();
        wmthread;
        JVM INSTR monitorenter ;
_L3:
        WindowManagerService windowmanagerservice = wmthread.mService;
        if(windowmanagerservice != null) goto _L2; else goto _L1
_L1:
        Exception exception;
        try {
            wmthread.wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally {
            wmthread;
        }
        if(true) goto _L3; else goto _L2
_L2:
        WindowManagerService windowmanagerservice1 = wmthread.mService;
        return windowmanagerservice1;
        throw exception;
    }

    private void moveAppWindowsLocked(AppWindowToken appwindowtoken, int i, boolean flag) {
        tmpRemoveAppWindowsLocked(appwindowtoken);
        reAddAppWindowsLocked(findWindowOffsetLocked(i), appwindowtoken);
        if(flag) {
            mInputMonitor.setUpdateInputWindowsNeededLw();
            if(!updateFocusedWindowLocked(3, false))
                assignLayersLocked();
            mLayoutNeeded = true;
            if(!mInLayout)
                performLayoutAndPlaceSurfacesLocked();
            mInputMonitor.updateInputWindowsLw(false);
        }
    }

    private void moveAppWindowsLocked(List list, int i) {
        int j = list.size();
        for(int k = 0; k < j; k++) {
            WindowToken windowtoken1 = (WindowToken)mTokenMap.get(list.get(k));
            if(windowtoken1 != null)
                tmpRemoveAppWindowsLocked(windowtoken1);
        }

        int l = findWindowOffsetLocked(i);
        for(int i1 = 0; i1 < j; i1++) {
            WindowToken windowtoken = (WindowToken)mTokenMap.get(list.get(i1));
            if(windowtoken != null)
                l = reAddAppWindowsLocked(l, windowtoken);
        }

        mInputMonitor.setUpdateInputWindowsNeededLw();
        if(!updateFocusedWindowLocked(3, false))
            assignLayersLocked();
        mLayoutNeeded = true;
        performLayoutAndPlaceSurfacesLocked();
        mInputMonitor.updateInputWindowsLw(false);
    }

    private void notifyFocusChanged() {
        WindowChangeListener awindowchangelistener1[];
        synchronized(mWindowMap) {
            if(mWindowChangeListeners.isEmpty())
                break MISSING_BLOCK_LABEL_84;
            WindowChangeListener awindowchangelistener[] = new WindowChangeListener[mWindowChangeListeners.size()];
            awindowchangelistener1 = (WindowChangeListener[])mWindowChangeListeners.toArray(awindowchangelistener);
        }
        int i = awindowchangelistener1.length;
        for(int j = 0; j < i; j++)
            awindowchangelistener1[j].focusChanged();

          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    private void notifyWindowsChanged() {
        WindowChangeListener awindowchangelistener1[];
        synchronized(mWindowMap) {
            if(mWindowChangeListeners.isEmpty())
                break MISSING_BLOCK_LABEL_84;
            WindowChangeListener awindowchangelistener[] = new WindowChangeListener[mWindowChangeListeners.size()];
            awindowchangelistener1 = (WindowChangeListener[])mWindowChangeListeners.toArray(awindowchangelistener);
        }
        int i = awindowchangelistener1.length;
        for(int j = 0; j < i; j++)
            awindowchangelistener1[j].windowsChanged();

          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    private final void performLayoutAndPlaceSurfacesLocked() {
        if(!mInLayout) goto _L2; else goto _L1
_L1:
        Slog.w("WindowManager", (new StringBuilder()).append("performLayoutAndPlaceSurfacesLocked called while in layout. Callers=").append(Debug.getCallers(3)).toString());
_L4:
        return;
_L2:
        if(mWaitingForConfig || mDisplay == null) goto _L4; else goto _L3
_L3:
        boolean flag;
        Trace.traceBegin(32L, "wmLayout");
        mInLayout = true;
        flag = false;
        if(mForceRemoves == null)
            break MISSING_BLOCK_LABEL_186;
        flag = true;
        for(int l = 0; l < mForceRemoves.size(); l++) {
            WindowState windowstate1 = (WindowState)mForceRemoves.get(l);
            Slog.i("WindowManager", (new StringBuilder()).append("Force removing: ").append(windowstate1).toString());
            removeWindowInnerLocked(windowstate1.mSession, windowstate1);
        }

        mForceRemoves = null;
        Slog.w("WindowManager", "Due to memory failure, waiting a bit for next layout");
        synchronized(new Object()) {
            try {
                obj.wait(250L);
            }
            catch(InterruptedException interruptedexception) { }
        }
_L10:
        int i;
        performLayoutAndPlaceSurfacesLockedInner(flag);
        i = mPendingRemove.size();
        if(i <= 0) goto _L6; else goto _L5
_L5:
        int k;
        if(mPendingRemoveTmp.length < i)
            mPendingRemoveTmp = new WindowState[i + 10];
        mPendingRemove.toArray(mPendingRemoveTmp);
        mPendingRemove.clear();
        k = 0;
_L9:
        if(k >= i) goto _L8; else goto _L7
_L7:
        WindowState windowstate = mPendingRemoveTmp[k];
        removeWindowInnerLocked(windowstate.mSession, windowstate);
        k++;
          goto _L9
        exception;
        obj;
        JVM INSTR monitorexit ;
        try {
            throw exception;
        }
        catch(RuntimeException runtimeexception) {
            Log.wtf("WindowManager", "Unhandled exception while force removing for memory", runtimeexception);
        }
          goto _L10
_L8:
        mInLayout = false;
        assignLayersLocked();
        mLayoutNeeded = true;
        Trace.traceEnd(32L);
        performLayoutAndPlaceSurfacesLocked();
        Trace.traceBegin(32L, "wmLayout");
_L13:
        int j;
        if(!mLayoutNeeded)
            break MISSING_BLOCK_LABEL_467;
        j = 1 + mLayoutRepeatCount;
        mLayoutRepeatCount = j;
        if(j >= 6) goto _L12; else goto _L11
_L11:
        requestTraversalLocked();
_L15:
        if(mWindowsChanged && !mWindowChangeListeners.isEmpty()) {
            mH.removeMessages(19);
            mH.sendMessage(mH.obtainMessage(19));
        }
_L14:
        Trace.traceEnd(32L);
          goto _L4
_L6:
        mInLayout = false;
          goto _L13
        RuntimeException runtimeexception1;
        runtimeexception1;
        mInLayout = false;
        Log.wtf("WindowManager", "Unhandled exception while laying out windows", runtimeexception1);
          goto _L14
_L12:
        Slog.e("WindowManager", "Performed 6 layouts in a row. Skipping");
        mLayoutRepeatCount = 0;
          goto _L15
        mLayoutRepeatCount = 0;
          goto _L15
    }

    private final void performLayoutAndPlaceSurfacesLockedInner(boolean flag) {
        if(mDisplay != null) goto _L2; else goto _L1
_L1:
        Slog.i("WindowManager", "skipping performLayoutAndPlaceSurfacesLockedInner with no mDisplay");
_L36:
        return;
_L2:
        long l;
        int i;
        int j;
        int k;
        int i1;
        int l1;
        l = SystemClock.uptimeMillis();
        i = mCurDisplayWidth;
        j = mCurDisplayHeight;
        k = mAppDisplayWidth;
        i1 = mAppDisplayHeight;
        if(mFocusMayChange) {
            mFocusMayChange = false;
            updateFocusedWindowLocked(3, false);
        }
        for(int j1 = -1 + mExitingTokens.size(); j1 >= 0; j1--)
            ((WindowToken)mExitingTokens.get(j1)).hasVisible = false;

        for(int k1 = -1 + mExitingAppTokens.size(); k1 >= 0; k1--)
            ((AppWindowToken)mExitingAppTokens.get(k1)).hasVisible = false;

        mInnerFields.mHoldScreen = null;
        mInnerFields.mScreenBrightness = -1F;
        mInnerFields.mButtonBrightness = -1F;
        mTransactionSequence = 1 + mTransactionSequence;
        Surface.openTransaction();
        if(mWatermark != null)
            mWatermark.positionSurface(i, j);
        if(mStrictModeFlash != null)
            mStrictModeFlash.positionSurface(i, j);
        l1 = 0;
_L31:
        if(++l1 <= 6) goto _L4; else goto _L3
_L3:
        Slog.w("WindowManager", "Animation repeat aborted after too many iterations");
        mLayoutNeeded = false;
_L30:
        if(mLosingFocus.isEmpty()) goto _L6; else goto _L5
_L5:
        boolean flag7 = true;
_L41:
        boolean flag8;
        boolean flag9;
        int i4;
        mInnerFields.mObscured = false;
        mInnerFields.mDimming = false;
        mInnerFields.mSyswin = false;
        flag8 = false;
        flag9 = false;
        i4 = -1 + mWindows.size();
_L15:
        if(i4 < 0) goto _L8; else goto _L7
_L7:
        WindowState windowstate2;
        boolean flag10;
        windowstate2 = (WindowState)mWindows.get(i4);
        if(windowstate2.mObscured == mInnerFields.mObscured)
            break MISSING_BLOCK_LABEL_2410;
        flag10 = true;
_L42:
        WindowStateAnimator windowstateanimator1;
        windowstate2.mObscured = mInnerFields.mObscured;
        if(!mInnerFields.mObscured)
            handleNotObscuredLocked(windowstate2, l, k, i1);
        if(flag10 && mWallpaperTarget == windowstate2 && windowstate2.isVisibleLw())
            updateWallpaperVisibilityLocked();
        windowstateanimator1 = windowstate2.mWinAnimator;
        if(windowstate2.mHasSurface && windowstate2.shouldAnimateMove()) {
            windowstateanimator1.setAnimation(AnimationUtils.loadAnimation(mContext, 0x10a0068));
            windowstateanimator1.mAnimDw = windowstate2.mLastFrame.left - windowstate2.mFrame.left;
            windowstateanimator1.mAnimDh = windowstate2.mLastFrame.top - windowstate2.mFrame.top;
        }
        windowstate2.mContentChanged = false;
        if(!windowstate2.mHasSurface) goto _L10; else goto _L9
_L9:
        AppWindowToken appwindowtoken1;
        if(windowstateanimator1.commitFinishDrawingLocked(l) && (0x100000 & windowstate2.mAttrs.flags) != 0) {
            mInnerFields.mWallpaperMayChange = true;
            mPendingLayoutChanges = 4 | mPendingLayoutChanges;
            debugLayoutRepeats("updateWindowsAndWallpaperLocked 1", mPendingLayoutChanges);
        }
        windowstateanimator1.setSurfaceBoundaries(flag);
        appwindowtoken1 = windowstate2.mAppToken;
        if(appwindowtoken1 == null || appwindowtoken1.allDrawn && !appwindowtoken1.mAppAnimator.freezingScreen) goto _L10; else goto _L11
_L11:
        if(appwindowtoken1.lastTransactionSequence != (long)mTransactionSequence) {
            appwindowtoken1.lastTransactionSequence = mTransactionSequence;
            appwindowtoken1.numDrawnWindows = 0;
            appwindowtoken1.numInterestingWindows = 0;
            appwindowtoken1.startingDisplayed = false;
        }
        if(!windowstate2.isOnScreen() && windowstateanimator1.mAttrType != 1 || windowstate2.mExiting || windowstate2.mDestroying) goto _L10; else goto _L12
_L12:
        if(windowstate2 == appwindowtoken1.startingWindow) goto _L14; else goto _L13
_L13:
        if(!appwindowtoken1.mAppAnimator.freezingScreen || !windowstate2.mAppFreezing) {
            appwindowtoken1.numInterestingWindows = 1 + appwindowtoken1.numInterestingWindows;
            if(windowstate2.isDrawnLw()) {
                appwindowtoken1.numDrawnWindows = 1 + appwindowtoken1.numDrawnWindows;
                flag9 = true;
            }
        }
_L10:
        if(flag7 && windowstate2 == mCurrentFocus && windowstate2.isDisplayedLw())
            flag8 = true;
        updateResizingWindows(windowstate2);
        i4--;
          goto _L15
_L4:
        debugLayoutRepeats("On entry to LockedInner", mPendingLayoutChanges);
        if((4 & mPendingLayoutChanges) != 0 && (2 & adjustWallpaperWindowsLocked()) != 0) {
            assignLayersLocked();
            mLayoutNeeded = true;
        }
        if((2 & mPendingLayoutChanges) != 0 && updateOrientationFromAppTokensLocked(true)) {
            mLayoutNeeded = true;
            mH.sendEmptyMessage(18);
        }
        if((1 & mPendingLayoutChanges) != 0)
            mLayoutNeeded = true;
          goto _L16
_L39:
        boolean flag11;
        performLayoutLockedInner(flag11, false);
_L20:
        int l3;
        mPendingLayoutChanges = 0;
        debugLayoutRepeats((new StringBuilder()).append("loop number ").append(mLayoutRepeatCount).toString(), mPendingLayoutChanges);
        mPolicy.beginAnimationLw(i, j);
        l3 = -1 + mWindows.size();
_L40:
        if(l3 < 0) goto _L18; else goto _L17
_L17:
        WindowState windowstate3 = (WindowState)mWindows.get(l3);
        if(windowstate3.mHasSurface)
            mPolicy.animatingWindowLw(windowstate3, windowstate3.mAttrs);
          goto _L19
_L38:
        Slog.w("WindowManager", "Layout repeat skipped after too many iterations");
          goto _L20
        RuntimeException runtimeexception;
        runtimeexception;
        Log.wtf("WindowManager", "Unhandled exception in Window Manager", runtimeexception);
        Surface.closeTransaction();
_L32:
        WindowState windowstate1;
        boolean flag5;
        boolean flag6;
        Configuration configuration;
        if(mAppTransitionReady) {
            mPendingLayoutChanges = mPendingLayoutChanges | handleAppTransitionReadyLocked();
            debugLayoutRepeats("after handleAppTransitionReadyLocked", mPendingLayoutChanges);
        }
        mInnerFields.mAdjResult = 0;
        if(!mAnimator.mAnimating && mAppTransitionRunning) {
            mPendingLayoutChanges = mPendingLayoutChanges | handleAnimatingStoppedAndTransitionLocked();
            debugLayoutRepeats("after handleAnimStopAndXitionLock", mPendingLayoutChanges);
        }
        if(mInnerFields.mWallpaperForceHidingChanged && mPendingLayoutChanges == 0 && !mAppTransitionReady) {
            mPendingLayoutChanges = mPendingLayoutChanges | animateAwayWallpaperLocked();
            debugLayoutRepeats("after animateAwayWallpaperLocked", mPendingLayoutChanges);
        }
        mInnerFields.mWallpaperForceHidingChanged = false;
        if(mInnerFields.mWallpaperMayChange) {
            LayoutFields layoutfields = mInnerFields;
            layoutfields.mAdjResult = layoutfields.mAdjResult | adjustWallpaperWindowsLocked();
        }
        Exception exception;
        int i3;
        WindowStateAnimator windowstateanimator;
        IWindow iwindow;
        int j3;
        int k3;
        Rect rect;
        Rect rect1;
        if((2 & mInnerFields.mAdjResult) != 0) {
            mPendingLayoutChanges = 1 | mPendingLayoutChanges;
            assignLayersLocked();
        } else
        if((4 & mInnerFields.mAdjResult) != 0)
            mPendingLayoutChanges = 1 | mPendingLayoutChanges;
        if(mFocusMayChange) {
            mFocusMayChange = false;
            if(updateFocusedWindowLocked(2, false)) {
                mPendingLayoutChanges = 8 | mPendingLayoutChanges;
                mInnerFields.mAdjResult = 0;
            }
        }
        if(mLayoutNeeded) {
            mPendingLayoutChanges = 1 | mPendingLayoutChanges;
            debugLayoutRepeats("mLayoutNeeded", mPendingLayoutChanges);
        }
        if(mResizingWindows.isEmpty()) goto _L22; else goto _L21
_L21:
        i3 = -1 + mResizingWindows.size();
_L29:
        if(i3 < 0) goto _L24; else goto _L23
_L23:
        windowstate1 = (WindowState)mResizingWindows.get(i3);
        windowstateanimator = windowstate1.mWinAnimator;
        RemoteException remoteexception1;
        if(windowstate1.mConfiguration == mCurConfiguration || windowstate1.mConfiguration != null && mCurConfiguration.diff(windowstate1.mConfiguration) == 0)
            flag5 = false;
        else
            flag5 = true;
        windowstate1.mConfiguration = mCurConfiguration;
        iwindow = windowstate1.mClient;
        j3 = (int)windowstateanimator.mSurfaceW;
        k3 = (int)windowstateanimator.mSurfaceH;
        rect = windowstate1.mLastContentInsets;
        rect1 = windowstate1.mLastVisibleInsets;
        if(windowstateanimator.mDrawState != 1) goto _L26; else goto _L25
_L25:
        flag6 = true;
_L33:
        if(!flag5) goto _L28; else goto _L27
_L27:
        configuration = windowstate1.mConfiguration;
_L34:
        iwindow.resized(j3, k3, rect, rect1, flag6, configuration);
        windowstate1.mContentInsetsChanged = false;
        windowstate1.mVisibleInsetsChanged = false;
        windowstateanimator.mSurfaceResized = false;
_L35:
        i3--;
          goto _L29
_L18:
        mPendingLayoutChanges = mPendingLayoutChanges | mPolicy.finishAnimationLw();
        debugLayoutRepeats("after finishAnimationLw", mPendingLayoutChanges);
        if(mPendingLayoutChanges != 0) goto _L31; else goto _L30
_L14:
        if(windowstate2.isDrawnLw())
            appwindowtoken1.startingDisplayed = true;
          goto _L10
        exception;
        Surface.closeTransaction();
        throw exception;
_L8:
        if(!flag9)
            break MISSING_BLOCK_LABEL_1526;
        updateAllDrawnLocked();
        if(flag8)
            mH.sendEmptyMessage(3);
        if(!mInnerFields.mDimming && mAnimator.isDimming())
            mAnimator.stopDimming();
        Surface.closeTransaction();
          goto _L32
_L26:
        flag6 = false;
          goto _L33
_L28:
        configuration = null;
          goto _L34
        remoteexception1;
        windowstate1.mOrientationChanging = false;
          goto _L35
_L24:
        mResizingWindows.clear();
_L22:
        if(mInnerFields.mOrientationChangeComplete) {
            if(mWindowsFreezingScreen) {
                mWindowsFreezingScreen = false;
                mH.removeMessages(11);
            }
            stopFreezingDisplayLocked();
        }
        boolean flag1 = false;
        int i2 = mDestroySurface.size();
        if(i2 > 0) {
            do {
                i2--;
                WindowState windowstate = (WindowState)mDestroySurface.get(i2);
                windowstate.mDestroying = false;
                if(mInputMethodWindow == windowstate)
                    mInputMethodWindow = null;
                if(windowstate == mWallpaperTarget)
                    flag1 = true;
                windowstate.mWinAnimator.destroySurfaceLocked();
            } while(i2 > 0);
            mDestroySurface.clear();
        }
        for(int j2 = -1 + mExitingTokens.size(); j2 >= 0; j2--) {
            WindowToken windowtoken = (WindowToken)mExitingTokens.get(j2);
            if(windowtoken.hasVisible)
                continue;
            mExitingTokens.remove(j2);
            if(windowtoken.windowType == 2013)
                mWallpaperTokens.remove(windowtoken);
        }

        for(int k2 = -1 + mExitingAppTokens.size(); k2 >= 0; k2--) {
            AppWindowToken appwindowtoken = (AppWindowToken)mExitingAppTokens.get(k2);
            if(!((WindowToken) (appwindowtoken)).hasVisible && !mClosingApps.contains(appwindowtoken)) {
                appwindowtoken.mAppAnimator.clearAnimation();
                appwindowtoken.mAppAnimator.animating = false;
                mAppTokens.remove(appwindowtoken);
                mAnimatingAppTokens.remove(appwindowtoken);
                mExitingAppTokens.remove(k2);
            }
        }

        if(!mAnimator.mAnimating && mRelayoutWhileAnimating.size() > 0) {
            int l2 = -1 + mRelayoutWhileAnimating.size();
            while(l2 >= 0)  {
                boolean flag2;
                Message message;
                boolean flag3;
                boolean flag4;
                try {
                    ((WindowState)mRelayoutWhileAnimating.get(l2)).mClient.doneAnimating();
                }
                catch(RemoteException remoteexception) { }
                l2--;
            }
            mRelayoutWhileAnimating.clear();
        }
        if(flag1) {
            flag3 = mLayoutNeeded;
            if(adjustWallpaperWindowsLocked() != 0)
                flag4 = true;
            else
                flag4 = false;
            mLayoutNeeded = flag4 | flag3;
        }
        if(mPendingLayoutChanges != 0)
            mLayoutNeeded = true;
        mInputMonitor.updateInputWindowsLw(true);
        if(mInnerFields.mHoldScreen != null)
            flag2 = true;
        else
            flag2 = false;
        setHoldScreenLocked(flag2);
        if(!mDisplayFrozen) {
            if(mInnerFields.mScreenBrightness < 0.0F || mInnerFields.mScreenBrightness > 1.0F)
                mPowerManager.setScreenBrightnessOverride(-1);
            else
                mPowerManager.setScreenBrightnessOverride((int)(255F * mInnerFields.mScreenBrightness));
            if(mInnerFields.mButtonBrightness < 0.0F || mInnerFields.mButtonBrightness > 1.0F)
                mPowerManager.setButtonBrightnessOverride(-1);
            else
                mPowerManager.setButtonBrightnessOverride((int)(255F * mInnerFields.mButtonBrightness));
        }
        if(mInnerFields.mHoldScreen != mHoldingScreenOn) {
            mHoldingScreenOn = mInnerFields.mHoldScreen;
            message = mH.obtainMessage(12, mInnerFields.mHoldScreen);
            mH.sendMessage(message);
        }
        if(mTurnOnScreen) {
            mPowerManager.userActivity(SystemClock.uptimeMillis(), false, 1, true);
            mTurnOnScreen = false;
        }
        if(mInnerFields.mUpdateRotation)
            if(updateRotationUncheckedLocked(false))
                mH.sendEmptyMessage(18);
            else
                mInnerFields.mUpdateRotation = false;
        if(mInnerFields.mOrientationChangeComplete && !mLayoutNeeded && !mInnerFields.mUpdateRotation)
            checkDrawnWindowsLocked();
        enableScreenIfNeededLocked();
        scheduleAnimationLocked();
          goto _L36
_L16:
        if(l1 >= 4) goto _L38; else goto _L37
_L37:
        if(l1 == 1)
            flag11 = true;
        else
            flag11 = false;
          goto _L39
_L19:
        l3--;
          goto _L40
_L6:
        flag7 = false;
          goto _L41
        flag10 = false;
          goto _L42
    }

    private final void performLayoutLockedInner(boolean flag, boolean flag1) {
        if(mLayoutNeeded) goto _L2; else goto _L1
_L1:
        return;
_L2:
        mLayoutNeeded = false;
        int i = mCurDisplayWidth;
        int j = mCurDisplayHeight;
        int k = mFakeWindows.size();
        for(int l = 0; l < k; l++)
            ((FakeWindowImpl)mFakeWindows.get(l)).layout(i, j);

        int i1 = mWindows.size();
        mPolicy.beginLayoutLw(i, j, mRotation);
        mSystemDecorLayer = mPolicy.getSystemDecorRectLw(mSystemDecorRect);
        int j1 = 1 + mLayoutSeq;
        if(j1 < 0)
            j1 = 0;
        mLayoutSeq = j1;
        int k1 = -1;
        int l1 = i1 - 1;
        while(l1 >= 0)  {
            WindowState windowstate1 = (WindowState)mWindows.get(l1);
            if(!windowstate1.isGoneForLayoutLw() || !windowstate1.mHaveFrame || windowstate1.mLayoutNeeded)
                if(!windowstate1.mLayoutAttached) {
                    if(flag)
                        windowstate1.mContentChanged = false;
                    windowstate1.mLayoutNeeded = false;
                    windowstate1.prelayout();
                    mPolicy.layoutWindowLw(windowstate1, windowstate1.mAttrs, null);
                    windowstate1.mLayoutSeq = j1;
                } else
                if(k1 < 0)
                    k1 = l1;
            l1--;
        }
        for(int i2 = k1; i2 >= 0; i2--) {
            WindowState windowstate = (WindowState)mWindows.get(i2);
            if(!windowstate.mLayoutAttached || (windowstate.mViewVisibility == 8 || !windowstate.mRelayoutCalled) && windowstate.mHaveFrame && !windowstate.mLayoutNeeded)
                continue;
            if(flag)
                windowstate.mContentChanged = false;
            windowstate.mLayoutNeeded = false;
            windowstate.prelayout();
            mPolicy.layoutWindowLw(windowstate, windowstate.mAttrs, windowstate.mAttachedWindow);
            windowstate.mLayoutSeq = j1;
        }

        mInputMonitor.setUpdateInputWindowsNeededLw();
        if(flag1)
            mInputMonitor.updateInputWindowsLw(false);
        mPolicy.finishLayoutLw();
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void placeWindowAfter(WindowState windowstate, WindowState windowstate1) {
        int i = mWindows.indexOf(windowstate);
        mWindows.add(i + 1, windowstate1);
        mWindowsChanged = true;
    }

    private void placeWindowBefore(WindowState windowstate, WindowState windowstate1) {
        int i = mWindows.indexOf(windowstate);
        mWindows.add(i, windowstate1);
        mWindowsChanged = true;
    }

    private final int reAddAppWindowsLocked(int i, WindowToken windowtoken) {
        int j = windowtoken.windows.size();
        for(int k = 0; k < j; k++)
            i = reAddWindowLocked(i, (WindowState)windowtoken.windows.get(k));

        return i;
    }

    private final int reAddWindowLocked(int i, WindowState windowstate) {
        int j = windowstate.mChildWindows.size();
        boolean flag = false;
        for(int k = 0; k < j; k++) {
            WindowState windowstate1 = (WindowState)windowstate.mChildWindows.get(k);
            if(!flag && windowstate1.mSubLayer >= 0) {
                windowstate.mRebuilding = false;
                mWindows.add(i, windowstate);
                i++;
                flag = true;
            }
            windowstate1.mRebuilding = false;
            mWindows.add(i, windowstate1);
            i++;
        }

        if(!flag) {
            windowstate.mRebuilding = false;
            mWindows.add(i, windowstate);
            i++;
        }
        mWindowsChanged = true;
        return i;
    }

    private void reAddWindowToListInOrderLocked(WindowState windowstate) {
        addWindowToListInOrderLocked(windowstate, false);
        int i = mWindows.indexOf(windowstate);
        if(i >= 0) {
            mWindows.remove(i);
            mWindowsChanged = true;
            reAddWindowLocked(i, windowstate);
        }
    }

    private void readForcedDisplaySizeLocked() {
        String s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "display_size_forced");
        if(s != null && s.length() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        i = s.indexOf(',');
        if(i <= 0 || s.lastIndexOf(',') != i)
            continue; /* Loop/switch isn't completed */
        int j;
        int k;
        j = Integer.parseInt(s.substring(0, i));
        k = Integer.parseInt(s.substring(i + 1));
        setForcedDisplaySizeLocked(j, k);
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void rebuildBlackFrame() {
        int i = 1;
        if(mBlackFrame != null) {
            mBlackFrame.kill();
            mBlackFrame = null;
        }
        if(mBaseDisplayWidth >= mInitialDisplayWidth && mBaseDisplayHeight >= mInitialDisplayHeight)
            break MISSING_BLOCK_LABEL_134;
        int j;
        int k;
        int l;
        int i1;
        Rect rect;
        Rect rect1;
        if(mRotation != i && mRotation != 3)
            i = 0;
        if(i != 0) {
            j = mInitialDisplayHeight;
            k = mInitialDisplayWidth;
            l = mBaseDisplayHeight;
            i1 = mBaseDisplayWidth;
        } else {
            j = mInitialDisplayWidth;
            k = mInitialDisplayHeight;
            l = mBaseDisplayWidth;
            i1 = mBaseDisplayHeight;
        }
        rect = new Rect(0, 0, j, k);
        rect1 = new Rect(0, 0, l, i1);
        mBlackFrame = new BlackFrame(mFxSession, rect, rect1, 0x1e8480);
_L2:
        return;
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private int reduceCompatConfigWidthSize(int i, int j, DisplayMetrics displaymetrics, int k, int l) {
        displaymetrics.noncompatWidthPixels = mPolicy.getNonDecorDisplayWidth(k, l, j);
        displaymetrics.noncompatHeightPixels = mPolicy.getNonDecorDisplayHeight(k, l, j);
        float f = CompatibilityInfo.computeCompatibleScaling(displaymetrics, null);
        int i1 = (int)(0.5F + (float)displaymetrics.noncompatWidthPixels / f / displaymetrics.density);
        if(i == 0 || i1 < i)
            i = i1;
        return i;
    }

    private int reduceConfigLayout(int i, int j, float f, int k, int l) {
        int i2;
        int j2;
        int i1 = mPolicy.getNonDecorDisplayWidth(k, l, j);
        int j1 = mPolicy.getNonDecorDisplayHeight(k, l, j);
        int k1 = i1;
        int l1 = j1;
        if(k1 < l1) {
            int l2 = k1;
            k1 = l1;
            l1 = l2;
        }
        i2 = (int)((float)k1 / f);
        j2 = (int)((float)l1 / f);
        if(i2 >= 470) goto _L2; else goto _L1
_L1:
        int k2;
        boolean flag;
        boolean flag1;
        k2 = 1;
        flag1 = false;
        flag = false;
_L5:
        if(!flag1)
            i = 0x10 | i & 0xffffffcf;
        if(flag)
            i |= 0x10000000;
        if(k2 < (i & 0xf))
            i = k2 | i & 0xfffffff0;
        return i;
_L2:
        if(i2 < 960 || j2 < 720) goto _L4; else goto _L3
_L3:
        k2 = 4;
_L6:
        if(j2 > 321 || i2 > 570)
            flag = true;
        else
            flag = false;
        if((i2 * 3) / 5 >= j2 - 1)
            flag1 = true;
        else
            flag1 = false;
        if(true) goto _L5; else goto _L4
_L4:
        if(i2 >= 640 && j2 >= 480)
            k2 = 3;
        else
            k2 = 2;
          goto _L6
    }

    private void removeAppTokensLocked(List list) {
        int i = list.size();
        for(int j = 0; j < i; j++) {
            IBinder ibinder = (IBinder)list.get(j);
            AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
            if(!mAppTokens.remove(appwindowtoken)) {
                Slog.w("WindowManager", (new StringBuilder()).append("Attempting to reorder token that doesn't exist: ").append(ibinder).append(" (").append(appwindowtoken).append(")").toString());
                j--;
                i--;
            }
        }

    }

    private void removeWindowInnerLocked(Session session, WindowState windowstate) {
        if(!windowstate.mRemoved) goto _L2; else goto _L1
_L1:
        return;
_L2:
        for(int i = -1 + windowstate.mChildWindows.size(); i >= 0; i--) {
            WindowState windowstate1 = (WindowState)windowstate.mChildWindows.get(i);
            Slog.w("WindowManager", (new StringBuilder()).append("Force-removing child win ").append(windowstate1).append(" from container ").append(windowstate).toString());
            removeWindowInnerLocked(windowstate1.mSession, windowstate1);
        }

        windowstate.mRemoved = true;
        if(mInputMethodTarget == windowstate)
            moveInputMethodWindowsIfNeededLocked(false);
        mPolicy.removeWindowLw(windowstate);
        windowstate.removeLocked();
        mWindowMap.remove(windowstate.mClient.asBinder());
        mWindows.remove(windowstate);
        mPendingRemove.remove(windowstate);
        mWindowsChanged = true;
        WindowToken windowtoken;
        AppWindowToken appwindowtoken;
        if(mInputMethodWindow == windowstate)
            mInputMethodWindow = null;
        else
        if(windowstate.mAttrs.type == 2012)
            mInputMethodDialogs.remove(windowstate);
        windowtoken = windowstate.mToken;
        appwindowtoken = windowstate.mAppToken;
        windowtoken.windows.remove(windowstate);
        if(appwindowtoken != null)
            appwindowtoken.allAppWindows.remove(windowstate);
        if(windowtoken.windows.size() == 0)
            if(!windowtoken.explicit)
                mTokenMap.remove(windowtoken.token);
            else
            if(appwindowtoken != null)
                appwindowtoken.firstWindowDrawn = false;
        if(appwindowtoken != null)
            if(appwindowtoken.startingWindow == windowstate)
                appwindowtoken.startingWindow = null;
            else
            if(appwindowtoken.allAppWindows.size() == 0 && appwindowtoken.startingData != null)
                appwindowtoken.startingData = null;
            else
            if(appwindowtoken.allAppWindows.size() == 1 && appwindowtoken.startingView != null) {
                Message message = mH.obtainMessage(6, appwindowtoken);
                mH.sendMessage(message);
            }
        if(windowstate.mAttrs.type != 2013)
            break; /* Loop/switch isn't completed */
        mLastWallpaperTimeoutTime = 0L;
        adjustWallpaperWindowsLocked();
_L5:
        if(!mInLayout) {
            assignLayersLocked();
            mLayoutNeeded = true;
            performLayoutAndPlaceSurfacesLocked();
            if(windowstate.mAppToken != null)
                windowstate.mAppToken.updateReportedVisibilityLocked();
        }
        mInputMonitor.updateInputWindowsLw(true);
        if(true) goto _L1; else goto _L3
_L3:
        if((0x100000 & windowstate.mAttrs.flags) == 0) goto _L5; else goto _L4
_L4:
        adjustWallpaperWindowsLocked();
          goto _L5
    }

    private void scheduleAnimationCallback(IRemoteCallback iremotecallback) {
        if(iremotecallback != null)
            mH.sendMessage(mH.obtainMessage(27, iremotecallback));
    }

    private void sendScreenStatusToClientsLocked() {
        int i = mWindows.size();
        boolean flag = mPowerManager.isScreenOn();
        int j = i - 1;
        while(j >= 0)  {
            WindowState windowstate = (WindowState)mWindows.get(j);
            try {
                windowstate.mClient.dispatchScreenState(flag);
            }
            catch(RemoteException remoteexception) { }
            j--;
        }
    }

    private void setForcedDisplaySizeLocked(int i, int j) {
        Slog.i("WindowManager", (new StringBuilder()).append("Using new display size: ").append(i).append("x").append(j).toString());
        synchronized(mDisplaySizeLock) {
            mBaseDisplayWidth = i;
            mBaseDisplayHeight = j;
        }
        mPolicy.setInitialDisplaySize(mDisplay, mBaseDisplayWidth, mBaseDisplayHeight);
        mLayoutNeeded = true;
        boolean flag = updateOrientationFromAppTokensLocked(false);
        mTempConfiguration.setToDefaults();
        mTempConfiguration.fontScale = mCurConfiguration.fontScale;
        if(computeScreenConfigurationLocked(mTempConfiguration) && mCurConfiguration.diff(mTempConfiguration) != 0)
            flag = true;
        if(flag) {
            mWaitingForConfig = true;
            startFreezingDisplayLocked(false);
            mH.sendEmptyMessage(18);
        }
        rebuildBlackFrame();
        performLayoutAndPlaceSurfacesLocked();
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private boolean shouldAllowDisableKeyguard() {
        boolean flag = true;
        if(mAllowDisableKeyguard == -1) {
            DevicePolicyManager devicepolicymanager = (DevicePolicyManager)mContext.getSystemService("device_policy");
            if(devicepolicymanager != null) {
                int i;
                if(devicepolicymanager.getPasswordQuality(null) == 0)
                    i = ((flag) ? 1 : 0);
                else
                    i = 0;
                mAllowDisableKeyguard = i;
            }
        }
        if(mAllowDisableKeyguard != flag)
            flag = false;
        return flag;
    }

    private void showStrictModeViolation(int i) {
        int k;
        boolean flag;
        int j;
        HashMap hashmap;
        Exception exception;
        Exception exception1;
        boolean flag1;
        WindowState windowstate;
        if(i != 0)
            flag = true;
        else
            flag = false;
        j = Binder.getCallingPid();
        hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(!flag) goto _L2; else goto _L1
_L1:
        flag1 = false;
        k = -1 + mWindows.size();
_L5:
        if(k >= 0) {
            windowstate = (WindowState)mWindows.get(k);
            if(windowstate.mSession.mPid != j || !windowstate.isVisibleLw())
                break MISSING_BLOCK_LABEL_159;
            flag1 = true;
        }
        if(flag1) goto _L2; else goto _L3
_L3:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L4
_L2:
        Surface.openTransaction();
        if(mStrictModeFlash == null)
            mStrictModeFlash = new StrictModeFlash(mDisplay, mFxSession);
        mStrictModeFlash.setVisibility(flag);
        Surface.closeTransaction();
        hashmap;
        JVM INSTR monitorexit ;
          goto _L4
        exception;
        throw exception;
        exception1;
        Surface.closeTransaction();
        throw exception1;
_L4:
        return;
        k--;
          goto _L5
    }

    private void startFreezingDisplayLocked(boolean flag) {
_L2:
        return;
        if(mDisplayFrozen || mDisplay == null || !mPolicy.isScreenOnFully()) goto _L2; else goto _L1
_L1:
        mScreenFrozenLock.acquire();
        mDisplayFrozen = true;
        mInputMonitor.freezeInputDispatchingLw();
        if(mNextAppTransition != -1) {
            mNextAppTransition = -1;
            mNextAppTransitionType = 0;
            mNextAppTransitionPackage = null;
            mNextAppTransitionThumbnail = null;
            mAppTransitionReady = true;
        }
        if(mAnimator.mScreenRotationAnimation != null) {
            mAnimator.mScreenRotationAnimation.kill();
            mAnimator.mScreenRotationAnimation = null;
        }
        mAnimator.mScreenRotationAnimation = new ScreenRotationAnimation(mContext, mFxSession, flag, mCurDisplayWidth, mCurDisplayHeight, mDisplay.getRotation());
        if(!mAnimator.mScreenRotationAnimation.hasScreenshot())
            Surface.freezeDisplay(0);
        if(true) goto _L2; else goto _L3
_L3:
    }

    private void stopFreezingDisplayLocked() {
_L2:
        return;
        if(!mDisplayFrozen || mWaitingForConfig || mAppsFreezingScreen > 0 || mWindowsFreezingScreen) goto _L2; else goto _L1
_L1:
        mDisplayFrozen = false;
        mH.removeMessages(17);
        boolean flag = false;
        boolean flag1;
        if(mAnimator.mScreenRotationAnimation != null && mAnimator.mScreenRotationAnimation.hasScreenshot()) {
            if(mAnimator.mScreenRotationAnimation.dismiss(mFxSession, 10000L, mTransitionAnimationScale, mCurDisplayWidth, mCurDisplayHeight)) {
                scheduleAnimationLocked();
            } else {
                mAnimator.mScreenRotationAnimation.kill();
                mAnimator.mScreenRotationAnimation = null;
                flag = true;
            }
        } else {
            if(mAnimator.mScreenRotationAnimation != null) {
                mAnimator.mScreenRotationAnimation.kill();
                mAnimator.mScreenRotationAnimation = null;
            }
            flag = true;
        }
        Surface.unfreezeDisplay(0);
        mInputMonitor.thawInputDispatchingLw();
        flag1 = updateOrientationFromAppTokensLocked(false);
        mH.removeMessages(15);
        mH.sendMessageDelayed(mH.obtainMessage(15), 2000L);
        mScreenFrozenLock.release();
        if(flag)
            flag1 |= updateRotationUncheckedLocked(false);
        if(flag1)
            mH.sendEmptyMessage(18);
        if(true) goto _L2; else goto _L3
_L3:
    }

    private boolean tmpRemoveAppWindowsLocked(WindowToken windowtoken) {
        boolean flag = true;
        int i = windowtoken.windows.size();
        if(i > 0)
            mWindowsChanged = flag;
        for(int j = 0; j < i; j++) {
            WindowState windowstate = (WindowState)windowtoken.windows.get(j);
            mWindows.remove(windowstate);
            for(int k = windowstate.mChildWindows.size(); k > 0;) {
                k--;
                WindowState windowstate1 = (WindowState)windowstate.mChildWindows.get(k);
                mWindows.remove(windowstate1);
            }

        }

        if(i <= 0)
            flag = false;
        return flag;
    }

    private int tmpRemoveWindowLocked(int i, WindowState windowstate) {
        int j = mWindows.indexOf(windowstate);
        if(j >= 0) {
            if(j < i)
                i--;
            mWindows.remove(j);
            mWindowsChanged = true;
            int k = windowstate.mChildWindows.size();
            do {
                if(k <= 0)
                    break;
                k--;
                WindowState windowstate1 = (WindowState)windowstate.mChildWindows.get(k);
                int l = mWindows.indexOf(windowstate1);
                if(l >= 0) {
                    if(l < i)
                        i--;
                    mWindows.remove(l);
                }
            } while(true);
        }
        return i;
    }

    private void updateAllDrawnLocked() {
        ArrayList arraylist = mAnimatingAppTokens;
        int i = arraylist.size();
        for(int j = 0; j < i; j++) {
            AppWindowToken appwindowtoken = (AppWindowToken)arraylist.get(j);
            if(appwindowtoken.allDrawn)
                continue;
            int k = appwindowtoken.numInterestingWindows;
            if(k > 0 && appwindowtoken.numDrawnWindows >= k)
                appwindowtoken.allDrawn = true;
        }

    }

    private boolean updateFocusedWindowLocked(int i, boolean flag) {
        boolean flag1 = false;
        boolean flag2 = true;
        WindowState windowstate = computeFocusedWindowLocked();
        if(mCurrentFocus != windowstate) {
            Trace.traceBegin(32L, "wmUpdateFocus");
            mH.removeMessages(2);
            mH.sendEmptyMessage(2);
            WindowState windowstate1 = mCurrentFocus;
            mCurrentFocus = windowstate;
            mAnimator.setCurrentFocus(windowstate);
            mLosingFocus.remove(windowstate);
            int j = mPolicy.focusChangedLw(windowstate1, windowstate);
            WindowState windowstate2 = mInputMethodWindow;
            if(windowstate != windowstate2 && windowstate1 != windowstate2) {
                if(i != flag2 && i != 3)
                    flag1 = flag2;
                if(moveInputMethodWindowsIfNeededLocked(flag1))
                    mLayoutNeeded = flag2;
                if(i == 2) {
                    performLayoutLockedInner(flag2, flag);
                    j &= -2;
                } else
                if(i == 3)
                    assignLayersLocked();
            }
            if((j & 1) != 0) {
                mLayoutNeeded = flag2;
                if(i == 2)
                    performLayoutLockedInner(flag2, flag);
            }
            if(i != flag2)
                finishUpdateFocusedWindowAfterAssignLayersLocked(flag);
            Trace.traceEnd(32L);
        } else {
            flag2 = false;
        }
        return flag2;
    }

    private Configuration updateOrientationFromAppTokensLocked(Configuration configuration, IBinder ibinder) {
        Configuration configuration1 = null;
        if(!updateOrientationFromAppTokensLocked(false)) goto _L2; else goto _L1
_L1:
        if(ibinder != null) {
            AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
            if(appwindowtoken != null)
                startAppFreezingScreenLocked(appwindowtoken, 128);
        }
        configuration1 = computeNewConfigurationLocked();
_L4:
        return configuration1;
_L2:
        if(configuration != null) {
            mTempConfiguration.setToDefaults();
            mTempConfiguration.fontScale = configuration.fontScale;
            if(computeScreenConfigurationLocked(mTempConfiguration) && configuration.diff(mTempConfiguration) != 0) {
                mWaitingForConfig = true;
                mLayoutNeeded = true;
                startFreezingDisplayLocked(false);
                configuration1 = new Configuration(mTempConfiguration);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateResizingWindows(WindowState windowstate) {
        WindowStateAnimator windowstateanimator = windowstate.mWinAnimator;
        if(!windowstate.mHasSurface || windowstate.mAppFreezing || windowstate.mLayoutSeq != mLayoutSeq) goto _L2; else goto _L1
_L1:
        boolean flag = windowstate.mContentInsetsChanged;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        if(!windowstate.mLastContentInsets.equals(windowstate.mContentInsets))
            flag1 = true;
        else
            flag1 = false;
        windowstate.mContentInsetsChanged = flag1 | flag;
        flag2 = windowstate.mVisibleInsetsChanged;
        if(!windowstate.mLastVisibleInsets.equals(windowstate.mVisibleInsets))
            flag3 = true;
        else
            flag3 = false;
        windowstate.mVisibleInsetsChanged = flag3 | flag2;
        if(windowstate.mConfiguration != mCurConfiguration && (windowstate.mConfiguration == null || mCurConfiguration.diff(windowstate.mConfiguration) != 0))
            flag4 = true;
        else
            flag4 = false;
        windowstate.mLastFrame.set(windowstate.mFrame);
        if(!windowstate.mContentInsetsChanged && !windowstate.mVisibleInsetsChanged && !windowstateanimator.mSurfaceResized && !flag4) goto _L4; else goto _L3
_L3:
        windowstate.mLastContentInsets.set(windowstate.mContentInsets);
        windowstate.mLastVisibleInsets.set(windowstate.mVisibleInsets);
        makeWindowFreezingScreenIfNeededLocked(windowstate);
        if(windowstate.mOrientationChanging) {
            windowstateanimator.mDrawState = 1;
            if(windowstate.mAppToken != null)
                windowstate.mAppToken.allDrawn = false;
        }
        if(!mResizingWindows.contains(windowstate))
            mResizingWindows.add(windowstate);
_L2:
        return;
_L4:
        if(windowstate.mOrientationChanging && windowstate.isDrawnLw())
            windowstate.mOrientationChanging = false;
        if(true) goto _L2; else goto _L5
_L5:
    }

    public void addAppToken(int i, IApplicationToken iapplicationtoken, int j, int k, boolean flag) {
        long l;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "addAppToken()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        long l1;
        try {
            l1 = iapplicationtoken.getKeyDispatchingTimeout();
        }
        catch(RemoteException remoteexception) {
            Slog.w("WindowManager", "Could not get dispatching timeout.", remoteexception);
            l = 0x12a05f200L;
            continue; /* Loop/switch isn't completed */
        }
        l = l1 * 0xf4240L;
_L4:
        synchronized(mWindowMap) {
            if(findAppWindowToken(iapplicationtoken.asBinder()) == null)
                break MISSING_BLOCK_LABEL_113;
            Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add existing app token: ").append(iapplicationtoken).toString());
        }
_L2:
        return;
        AppWindowToken appwindowtoken = new AppWindowToken(this, iapplicationtoken);
        appwindowtoken.inputDispatchingTimeoutNanos = l;
        appwindowtoken.groupId = j;
        appwindowtoken.appFullscreen = flag;
        appwindowtoken.requestedOrientation = k;
        mAppTokens.add(i, appwindowtoken);
        addAppTokenToAnimating(i, appwindowtoken);
        mTokenMap.put(iapplicationtoken.asBinder(), appwindowtoken);
        appwindowtoken.hidden = true;
        appwindowtoken.hiddenRequested = true;
        if(true) goto _L2; else goto _L1
_L1:
        exception;
        throw exception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public android.view.WindowManagerPolicy.FakeWindow addFakeWindow(Looper looper, android.view.InputEventReceiver.Factory factory, String s, int i, int j, boolean flag, boolean flag1, 
            boolean flag2) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        FakeWindowImpl fakewindowimpl;
        for(fakewindowimpl = new FakeWindowImpl(this, looper, factory, s, i, j, flag, flag1, flag2); mFakeWindows.size() < 0 && ((FakeWindowImpl)mFakeWindows.get(0)).mWindowLayer > fakewindowimpl.mWindowLayer;);
        mFakeWindows.add(0, fakewindowimpl);
        mInputMonitor.updateInputWindowsLw(true);
        return fakewindowimpl;
    }

    void addInputMethodWindowToListLocked(WindowState windowstate) {
        int i = findDesiredInputMethodWindowIndexLocked(true);
        if(i >= 0) {
            windowstate.mTargetAppToken = mInputMethodTarget.mAppToken;
            mWindows.add(i, windowstate);
            mWindowsChanged = true;
            moveInputMethodDialogsLocked(i + 1);
        } else {
            windowstate.mTargetAppToken = null;
            addWindowToListInOrderLocked(windowstate, true);
            moveInputMethodDialogsLocked(i);
        }
    }

    public int addWindow(Session session, IWindow iwindow, int i, android.view.WindowManager.LayoutParams layoutparams, int j, Rect rect, InputChannel inputchannel) {
        int k = mPolicy.checkAddPermission(layoutparams);
        if(k == 0) goto _L2; else goto _L1
_L1:
        int l = k;
_L6:
        return l;
_L2:
        boolean flag;
        WindowState windowstate;
        flag = false;
        windowstate = null;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mDisplay == null)
            throw new IllegalStateException("Display has not been initialialized");
          goto _L3
        Exception exception;
        exception;
_L24:
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L3:
        if(!mWindowMap.containsKey(iwindow.asBinder())) goto _L5; else goto _L4
_L4:
        Slog.w("WindowManager", (new StringBuilder()).append("Window ").append(iwindow).append(" is already added").toString());
        l = -5;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L5:
        if(layoutparams.type < 1000 || layoutparams.type > 1999) goto _L8; else goto _L7
_L7:
        windowstate = windowForClientLocked(null, layoutparams.token, false);
        if(windowstate != null) goto _L10; else goto _L9
_L9:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add window with token that is not a window: ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -2;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L10:
        if(windowstate.mAttrs.type < 1000 || windowstate.mAttrs.type > 1999) goto _L8; else goto _L11
_L11:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add window with token that is a sub-window: ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -2;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L8:
        boolean flag1;
        WindowToken windowtoken;
        flag1 = false;
        windowtoken = (WindowToken)mTokenMap.get(layoutparams.token);
        if(windowtoken != null) goto _L13; else goto _L12
_L12:
        if(layoutparams.type < 1 || layoutparams.type > 99) goto _L15; else goto _L14
_L14:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add application window with unknown token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L15:
        if(layoutparams.type != 2011) goto _L17; else goto _L16
_L16:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add input method window with unknown token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L17:
        if(layoutparams.type != 2013) goto _L19; else goto _L18
_L18:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add wallpaper window with unknown token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L19:
        if(layoutparams.type != 2023) goto _L21; else goto _L20
_L20:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add Dream window with unknown token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L21:
        windowtoken = new WindowToken(this, layoutparams.token, -1, false);
        flag1 = true;
_L30:
        WindowState windowstate1 = new WindowState(this, session, iwindow, windowtoken, windowstate, i, layoutparams, j);
        if(windowstate1.mDeathRecipient != null) goto _L23; else goto _L22
_L22:
        Slog.w("WindowManager", (new StringBuilder()).append("Adding window client ").append(iwindow.asBinder()).append(" that is dead, aborting.").toString());
        l = -4;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
        exception;
          goto _L24
_L13:
        if(layoutparams.type < 1 || layoutparams.type > 99) goto _L26; else goto _L25
_L25:
        AppWindowToken appwindowtoken = windowtoken.appWindowToken;
        if(appwindowtoken != null) goto _L28; else goto _L27
_L27:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add window with non-application token ").append(windowtoken).append(".  Aborting.").toString());
        l = -3;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L28:
        if(!appwindowtoken.removed)
            continue; /* Loop/switch isn't completed */
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add window with exiting application token ").append(windowtoken).append(".  Aborting.").toString());
        l = -4;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
        if(layoutparams.type != 3 || !appwindowtoken.firstWindowDrawn) goto _L30; else goto _L29
_L29:
        l = -6;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L26:
        if(layoutparams.type != 2011) goto _L32; else goto _L31
_L31:
        if(windowtoken.windowType == 2011) goto _L30; else goto _L33
_L33:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add input method window with bad token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L32:
        if(layoutparams.type != 2013)
            continue; /* Loop/switch isn't completed */
        if(windowtoken.windowType == 2013) goto _L30; else goto _L34
_L34:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add wallpaper window with bad token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
        if(layoutparams.type != 2023 || windowtoken.windowType == 2023) goto _L30; else goto _L35
_L35:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add Dream window with bad token ").append(layoutparams.token).append(".  Aborting.").toString());
        l = -1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L23:
        int i1;
        mPolicy.adjustWindowParamsLw(windowstate1.mAttrs);
        i1 = mPolicy.prepareAddWindowLw(windowstate1, layoutparams);
        if(i1 == 0)
            break MISSING_BLOCK_LABEL_1047;
        hashmap;
        JVM INSTR monitorexit ;
        l = i1;
          goto _L6
        int j1;
        long l1;
        boolean flag2;
        if(inputchannel != null && (2 & layoutparams.inputFeatures) == 0) {
            InputChannel ainputchannel[] = InputChannel.openInputChannelPair(windowstate1.makeInputChannelName());
            windowstate1.setInputChannel(ainputchannel[0]);
            ainputchannel[1].transferTo(inputchannel);
            mInputManager.registerInputChannel(windowstate1.mInputChannel, windowstate1.mInputWindowHandle);
        }
        j1 = 0;
        l1 = Binder.clearCallingIdentity();
        if(flag1)
            mTokenMap.put(layoutparams.token, windowtoken);
        windowstate1.attach();
        mWindowMap.put(iwindow.asBinder(), windowstate1);
        if(layoutparams.type == 3 && windowtoken.appWindowToken != null)
            windowtoken.appWindowToken.startingWindow = windowstate1;
        flag2 = true;
        if(layoutparams.type != 2011)
            break MISSING_BLOCK_LABEL_1384;
        windowstate1.mGivenInsetsPending = true;
        mInputMethodWindow = windowstate1;
        addInputMethodWindowToListLocked(windowstate1);
        flag2 = false;
_L36:
        windowstate1.mWinAnimator.mEnterAnimationPending = true;
        mPolicy.getContentInsetHintLw(layoutparams, rect);
        if(mInTouchMode)
            j1 = false | true;
        boolean flag3;
        if(windowstate1.mAppToken == null || !windowstate1.mAppToken.clientHidden)
            j1 |= 2;
        mInputMonitor.setUpdateInputWindowsNeededLw();
        flag3 = false;
        if(windowstate1.canReceiveKeys()) {
            flag3 = updateFocusedWindowLocked(1, false);
            if(flag3)
                flag2 = false;
        }
        if(flag2)
            moveInputMethodWindowsIfNeededLocked(false);
        assignLayersLocked();
        if(flag3)
            finishUpdateFocusedWindowAfterAssignLayersLocked(false);
        mInputMonitor.updateInputWindowsLw(false);
        if(windowstate1.isVisibleOrAdding() && updateOrientationFromAppTokensLocked(false))
            flag = true;
        hashmap;
        JVM INSTR monitorexit ;
        if(flag)
            sendNewConfiguration();
        Binder.restoreCallingIdentity(l1);
        l = j1;
          goto _L6
        if(layoutparams.type == 2012) {
            mInputMethodDialogs.add(windowstate1);
            addWindowToListInOrderLocked(windowstate1, true);
            adjustInputMethodDialogsLocked();
            flag2 = false;
        } else {
            addWindowToListInOrderLocked(windowstate1, true);
            if(layoutparams.type == 2013) {
                mLastWallpaperTimeoutTime = 0L;
                adjustWallpaperWindowsLocked();
            } else
            if((0x100000 & layoutparams.flags) != 0)
                adjustWallpaperWindowsLocked();
        }
          goto _L36
    }

    public void addWindowChangeListener(WindowChangeListener windowchangelistener) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mWindowChangeListeners.add(windowchangelistener);
        return;
    }

    public void addWindowToken(IBinder ibinder, int i) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "addWindowToken()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if((WindowToken)mTokenMap.get(ibinder) != null) {
            Slog.w("WindowManager", (new StringBuilder()).append("Attempted to add existing input method token: ").append(ibinder).toString());
        } else {
            WindowToken windowtoken = new WindowToken(this, ibinder, i, true);
            mTokenMap.put(ibinder, windowtoken);
            if(i == 2013)
                mWallpaperTokens.add(windowtoken);
        }
        return;
    }

    void adjustInputMethodDialogsLocked() {
        moveInputMethodDialogsLocked(findDesiredInputMethodWindowIndexLocked(true));
    }

    int adjustWallpaperWindowsLocked() {
        int i;
        int j;
        int k;
        ArrayList arraylist;
        int l;
        WindowState windowstate;
        WindowState windowstate1;
        int i1;
        WindowState windowstate2;
        int j1;
        int k1;
        mInnerFields.mWallpaperMayChange = false;
        i = 0;
        j = mAppDisplayWidth;
        k = mAppDisplayHeight;
        arraylist = mWindows;
        l = arraylist.size();
        windowstate = null;
        windowstate1 = null;
        i1 = 0;
        windowstate2 = null;
        j1 = 0;
        k1 = -1;
_L3:
        int l1;
        for(l1 = l; l1 > 0;) {
label0:
            {
                l1--;
                windowstate = (WindowState)arraylist.get(l1);
                if(windowstate.mAttrs.type != 2013)
                    break label0;
                if(windowstate2 == null) {
                    windowstate2 = windowstate;
                    j1 = l1;
                }
            }
        }

          goto _L1
        WindowState windowstate7;
        windowstate2 = null;
        windowstate7 = mAnimator.mWindowDetachedWallpaper;
        if(windowstate != windowstate7 && windowstate.mAppToken != null && ((WindowToken) (windowstate.mAppToken)).hidden && windowstate.mAppToken.mAppAnimator.animation == null) goto _L3; else goto _L2
_L2:
        if((0x100000 & windowstate.mAttrs.flags) == 0 || !windowstate.isReadyForDisplay() || mWallpaperTarget != windowstate && !windowstate.isDrawnLw()) goto _L5; else goto _L4
_L4:
        WindowState windowstate9;
        windowstate1 = windowstate;
        i1 = l1;
        windowstate9 = mWallpaperTarget;
        if(windowstate == windowstate9 && windowstate.mWinAnimator.isAnimating()) goto _L3; else goto _L1
_L1:
        if(windowstate1 == null && k1 >= 0) {
            windowstate1 = windowstate;
            i1 = k1;
        }
        if(mNextAppTransition == -1) goto _L7; else goto _L6
_L6:
        if(mWallpaperTarget == null || mWallpaperTarget.mAppToken == null) goto _L9; else goto _L8
_L8:
        int j2 = 0;
_L11:
        return j2;
_L5:
        WindowState windowstate8 = mAnimator.mWindowDetachedWallpaper;
        if(windowstate == windowstate8)
            k1 = l1;
          goto _L3
_L9:
        if(windowstate1 == null || windowstate1.mAppToken == null) goto _L7; else goto _L10
_L10:
        j2 = 0;
          goto _L11
_L7:
        WindowState windowstate5;
        boolean flag2;
        if(mWallpaperTarget != windowstate1) {
            mLowerWallpaperTarget = null;
            mUpperWallpaperTarget = null;
            WindowState windowstate6 = mWallpaperTarget;
            mWallpaperTarget = windowstate1;
            if(windowstate1 != null && windowstate6 != null) {
                boolean flag4;
                boolean flag5;
                if(windowstate6.mWinAnimator.mAnimation != null || windowstate6.mAppToken != null && windowstate6.mAppToken.mAppAnimator.animation != null)
                    flag4 = true;
                else
                    flag4 = false;
                if(windowstate1.mWinAnimator.mAnimation != null || windowstate1.mAppToken != null && windowstate1.mAppToken.mAppAnimator.animation != null)
                    flag5 = true;
                else
                    flag5 = false;
                if(flag5 && flag4) {
                    int k3 = arraylist.indexOf(windowstate6);
                    int i2;
                    WindowToken windowtoken;
                    int k2;
                    int j3;
                    if(k3 >= 0)
                        if(windowstate1.mAppToken != null && windowstate1.mAppToken.hiddenRequested) {
                            mWallpaperTarget = windowstate6;
                            windowstate1 = windowstate6;
                            i1 = k3;
                        } else
                        if(i1 > k3) {
                            mUpperWallpaperTarget = windowstate1;
                            mLowerWallpaperTarget = windowstate6;
                            windowstate1 = windowstate6;
                            i1 = k3;
                        } else {
                            mUpperWallpaperTarget = windowstate6;
                            mLowerWallpaperTarget = windowstate1;
                        }
                }
            }
        } else
        if(mLowerWallpaperTarget != null) {
            boolean flag;
            boolean flag1;
            if(mLowerWallpaperTarget.mWinAnimator.mAnimation != null || mLowerWallpaperTarget.mAppToken != null && mLowerWallpaperTarget.mAppToken.mAppAnimator.animation != null)
                flag = true;
            else
                flag = false;
            if(mUpperWallpaperTarget.mWinAnimator.mAnimation != null || mUpperWallpaperTarget.mAppToken != null && mUpperWallpaperTarget.mAppToken.mAppAnimator.animation != null)
                flag1 = true;
            else
                flag1 = false;
            if(!flag || !flag1) {
                mLowerWallpaperTarget = null;
                mUpperWallpaperTarget = null;
            }
        }
        if(windowstate1 != null)
            flag2 = true;
        else
            flag2 = false;
        if(!flag2) goto _L13; else goto _L12
_L12:
        flag2 = isWallpaperVisible(windowstate1);
        int i3;
        if(mLowerWallpaperTarget == null && windowstate1.mAppToken != null)
            i3 = windowstate1.mAppToken.mAppAnimator.animLayerAdjustment;
        else
            i3 = 0;
        mWallpaperAnimLayerAdjustment = i3;
        j3 = 1000 + 10000 * mPolicy.getMaxWallpaperLayer();
_L17:
        if(i1 <= 0) goto _L13; else goto _L14
_L14:
        windowstate5 = (WindowState)arraylist.get(i1 - 1);
        if(windowstate5.mBaseLayer >= j3 || windowstate5.mAttachedWindow == windowstate1 || windowstate1.mAttachedWindow != null && windowstate5.mAttachedWindow == windowstate1.mAttachedWindow || windowstate5.mAttrs.type == 3 && windowstate1.mToken != null && windowstate5.mToken == windowstate1.mToken) goto _L15; else goto _L13
_L13:
        WindowState windowstate3;
        if(windowstate1 == null && windowstate2 != null) {
            windowstate3 = windowstate2;
            i1 = j1 + 1;
        } else
        if(i1 > 0)
            windowstate3 = (WindowState)arraylist.get(i1 - 1);
        else
            windowstate3 = null;
        if(flag2) {
            if(mWallpaperTarget.mWallpaperX >= 0.0F) {
                mLastWallpaperX = mWallpaperTarget.mWallpaperX;
                mLastWallpaperXStep = mWallpaperTarget.mWallpaperXStep;
            }
            if(mWallpaperTarget.mWallpaperY >= 0.0F) {
                mLastWallpaperY = mWallpaperTarget.mWallpaperY;
                mLastWallpaperYStep = mWallpaperTarget.mWallpaperYStep;
            }
        }
        for(i2 = mWallpaperTokens.size(); i2 > 0;) {
            i2--;
            windowtoken = (WindowToken)mWallpaperTokens.get(i2);
            if(windowtoken.hidden == flag2) {
                i |= 4;
                boolean flag3;
                if(!flag2)
                    flag3 = true;
                else
                    flag3 = false;
                windowtoken.hidden = flag3;
                mLayoutNeeded = true;
            }
            k2 = windowtoken.windows.size();
            while(k2 > 0)  {
                k2--;
                WindowState windowstate4 = (WindowState)windowtoken.windows.get(k2);
                if(flag2)
                    updateWallpaperOffsetLocked(windowstate4, j, k, false);
                dispatchWallpaperVisibility(windowstate4, flag2);
                windowstate4.mWinAnimator.mAnimLayer = windowstate4.mLayer + mWallpaperAnimLayerAdjustment;
                if(windowstate4 == windowstate3) {
                    if(--i1 > 0)
                        windowstate3 = (WindowState)arraylist.get(i1 - 1);
                    else
                        windowstate3 = null;
                } else {
                    int l2 = arraylist.indexOf(windowstate4);
                    if(l2 >= 0) {
                        arraylist.remove(l2);
                        mWindowsChanged = true;
                        if(l2 < i1)
                            i1--;
                    }
                    arraylist.add(i1, windowstate4);
                    mWindowsChanged = true;
                    i |= 2;
                }
            }
        }

        break; /* Loop/switch isn't completed */
_L15:
        windowstate1 = windowstate5;
        i1--;
        if(true) goto _L17; else goto _L16
_L16:
        j2 = i;
          goto _L11
    }

    void bulkSetParameters(int i, int j) {
        mH.sendMessage(mH.obtainMessage(25, i, j));
    }

    boolean checkCallingPermission(String s, String s1) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_2;
        if(Binder.getCallingPid() != Process.myPid() && mContext.checkCallingPermission(s) != 0) {
            Slog.w("WindowManager", (new StringBuilder()).append("Permission Denial: ").append(s1).append(" from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append(s).toString());
            flag = false;
        }
        return flag;
    }

    void checkDrawnWindowsLocked() {
        if(mWaitingForDrawn.size() > 0) {
            int i = -1 + mWaitingForDrawn.size();
            while(i >= 0)  {
                Pair pair = (Pair)mWaitingForDrawn.get(i);
                WindowState windowstate = (WindowState)pair.first;
                if(windowstate.mRemoved || !windowstate.isVisibleLw()) {
                    Slog.w("WindowManager", (new StringBuilder()).append("Aborted waiting for drawn: ").append(pair.first).toString());
                    RemoteException remoteexception1;
                    try {
                        ((IRemoteCallback)pair.second).sendResult(null);
                    }
                    catch(RemoteException remoteexception) { }
                    mWaitingForDrawn.remove(pair);
                    mH.removeMessages(24, pair);
                } else
                if(windowstate.mWinAnimator.mSurfaceShown) {
                    try {
                        ((IRemoteCallback)pair.second).sendResult(null);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RemoteException remoteexception1) { }
                    mWaitingForDrawn.remove(pair);
                    mH.removeMessages(24, pair);
                }
                i--;
            }
        }
    }

    public void clearForcedDisplaySize() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        setForcedDisplaySizeLocked(mInitialDisplayWidth, mInitialDisplayHeight);
        android.provider.Settings.Secure.putString(mContext.getContentResolver(), "display_size_forced", "");
        return;
    }

    public void closeSystemDialogs(String s) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i = -1 + mWindows.size();
_L1:
        WindowState windowstate;
        boolean flag;
        if(i < 0)
            break MISSING_BLOCK_LABEL_67;
        windowstate = (WindowState)mWindows.get(i);
        flag = windowstate.mHasSurface;
        Exception exception;
        if(flag)
            try {
                windowstate.mClient.closeSystemDialogs(s);
            }
            catch(RemoteException remoteexception) { }
            finally {
                hashmap;
            }
        i--;
          goto _L1
        hashmap;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    int computeForcedAppOrientationLocked() {
        int i = getOrientationFromWindowsLocked();
        if(i == -1)
            i = getOrientationFromAppTokensLocked();
        return i;
    }

    public Configuration computeNewConfiguration() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        Configuration configuration = computeNewConfigurationLocked();
        if(configuration == null && mWaitingForConfig) {
            mWaitingForConfig = false;
            performLayoutAndPlaceSurfacesLocked();
        }
        return configuration;
    }

    Configuration computeNewConfigurationLocked() {
        Configuration configuration = new Configuration();
        configuration.fontScale = 0.0F;
        if(!computeScreenConfigurationLocked(configuration))
            configuration = null;
        return configuration;
    }

    boolean computeScreenConfigurationLocked(Configuration configuration) {
        if(mDisplay != null) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L7:
        return flag1;
_L2:
        int i;
        int j;
        Object obj;
        int k1;
        int l1;
        boolean flag;
        DisplayMetrics displaymetrics;
        int i1;
        int j1;
        InputDevice ainputdevice[];
        int i2;
        int j2;
        InputDevice inputdevice;
        int j3;
        if(mRotation == 1 || mRotation == 3)
            flag = true;
        else
            flag = false;
        if(flag)
            i = mBaseDisplayHeight;
        else
            i = mBaseDisplayWidth;
        if(flag)
            j = mBaseDisplayWidth;
        else
            j = mBaseDisplayHeight;
        obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        if(!mAltOrientation)
            break MISSING_BLOCK_LABEL_613;
        mCurDisplayWidth = i;
        mCurDisplayHeight = j;
        if(i <= j) goto _L4; else goto _L3
_L3:
        j3 = (int)((float)j / 1.3F);
        if(j3 < i)
            mCurDisplayWidth = j3;
_L5:
        int k = mCurDisplayWidth;
        int l = mCurDisplayHeight;
        if(configuration != null) {
            int l2 = 3;
            Exception exception;
            int i3;
            if(k < l)
                l2 = 1;
            else
            if(k > l)
                l2 = 2;
            configuration.orientation = l2;
        }
        mDisplay.getMetricsWithSize(mRealDisplayMetrics, mCurDisplayWidth, mCurDisplayHeight);
        displaymetrics = mDisplayMetrics;
        i1 = mPolicy.getNonDecorDisplayWidth(k, l, mRotation);
        j1 = mPolicy.getNonDecorDisplayHeight(k, l, mRotation);
        synchronized(mDisplaySizeLock) {
            mAppDisplayWidth = i1;
            mAppDisplayHeight = j1;
            mAnimator.setDisplayDimensions(mCurDisplayWidth, mCurDisplayHeight, mAppDisplayWidth, mAppDisplayHeight);
        }
        mDisplay.getMetricsWithSize(displaymetrics, mAppDisplayWidth, mAppDisplayHeight);
        mCompatibleScreenScale = CompatibilityInfo.computeCompatibleScaling(displaymetrics, mCompatDisplayMetrics);
        if(configuration == null)
            break MISSING_BLOCK_LABEL_789;
        configuration.screenWidthDp = (int)((float)mPolicy.getConfigDisplayWidth(k, l, mRotation) / displaymetrics.density);
        configuration.screenHeightDp = (int)((float)mPolicy.getConfigDisplayHeight(k, l, mRotation) / displaymetrics.density);
        computeSizeRangesAndScreenLayout(flag, k, l, displaymetrics.density, configuration);
        configuration.compatScreenWidthDp = (int)((float)configuration.screenWidthDp / mCompatibleScreenScale);
        configuration.compatScreenHeightDp = (int)((float)configuration.screenHeightDp / mCompatibleScreenScale);
        configuration.compatSmallestScreenWidthDp = computeCompatSmallestWidth(flag, displaymetrics, k, l);
        configuration.touchscreen = 1;
        configuration.keyboard = 1;
        configuration.navigation = 1;
        k1 = 0;
        l1 = 0;
        ainputdevice = mInputManager.getInputDevices();
        i2 = ainputdevice.length;
        j2 = 0;
        while(j2 < i2)  {
            inputdevice = ainputdevice[j2];
            if(!inputdevice.isVirtual()) {
                int k2 = inputdevice.getSources();
                byte byte0;
                if(inputdevice.isExternal())
                    byte0 = 2;
                else
                    byte0 = 1;
                if(mIsTouchDevice) {
                    if((k2 & 0x1002) == 4098)
                        configuration.touchscreen = 3;
                } else {
                    configuration.touchscreen = 1;
                }
                if((0x10004 & k2) == 0x10004) {
                    configuration.navigation = 3;
                    l1 |= byte0;
                } else
                if((k2 & 0x201) == 513 && configuration.navigation == 1) {
                    configuration.navigation = 2;
                    l1 |= byte0;
                }
                if(inputdevice.getKeyboardType() == 2) {
                    configuration.keyboard = 2;
                    k1 |= byte0;
                }
            }
            j2++;
        }
        break MISSING_BLOCK_LABEL_697;
_L4:
        i3 = (int)((float)i / 1.3F);
        if(i3 < j)
            mCurDisplayHeight = i3;
          goto _L5
        exception;
        throw exception;
        mCurDisplayWidth = i;
        mCurDisplayHeight = j;
          goto _L5
        exception1;
        obj1;
        JVM INSTR monitorexit ;
        throw exception1;
        boolean flag2;
        if(configuration.keyboard != 1)
            flag2 = true;
        else
            flag2 = false;
        if(flag2 != mHardKeyboardAvailable) {
            mHardKeyboardAvailable = flag2;
            mHardKeyboardEnabled = flag2;
            mH.removeMessages(22);
            mH.sendEmptyMessage(22);
        }
        if(!mHardKeyboardEnabled)
            configuration.keyboard = 1;
        configuration.keyboardHidden = 1;
        configuration.hardKeyboardHidden = 1;
        configuration.navigationHidden = 1;
        mPolicy.adjustConfigurationLw(configuration, k1, l1);
        flag1 = true;
        if(true) goto _L7; else goto _L6
_L6:
    }

    void createWatermark() {
        if(mWatermark == null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        File file;
        FileInputStream fileinputstream;
        file = new File("/system/etc/setup.conf");
        fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(file);
        String s = (new DataInputStream(fileinputstream1)).readLine();
        if(s != null) {
            String as[] = s.split("%");
            if(as != null && as.length > 0)
                mWatermark = new Watermark(mRealDisplayMetrics, mFxSession, as);
        }
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception4) { }
          goto _L1
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
_L6:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
          goto _L1
        IOException ioexception5;
        ioexception5;
_L5:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L1
        Exception exception;
        exception;
_L4:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception3) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L4; else goto _L3
_L3:
        IOException ioexception1;
        ioexception1;
        fileinputstream = fileinputstream1;
          goto _L5
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        fileinputstream = fileinputstream1;
          goto _L6
    }

    void debugLayoutRepeats(String s, int i) {
        if(mLayoutRepeatCount >= 4)
            Slog.v("WindowManager", (new StringBuilder()).append("Layouts looping: ").append(s).append(", mPendingLayoutChanges = 0x").append(Integer.toHexString(i)).toString());
    }

    public boolean detectSafeMode() {
        boolean flag = false;
        if(!mInputMonitor.waitForInputDevicesReady(1000L))
            Slog.w("WindowManager", "Devices still not ready after waiting 1000 milliseconds before attempting to detect safe mode.");
        int i = mInputManager.getKeyCodeState(-1, -256, 82);
        int j = mInputManager.getKeyCodeState(-1, -256, 47);
        int k = mInputManager.getKeyCodeState(-1, 513, 23);
        int l = mInputManager.getScanCodeState(-1, 0x10004, 272);
        int i1 = mInputManager.getKeyCodeState(-1, -256, 25);
        if(i > 0 || j > 0 || k > 0 || l > 0 || i1 > 0)
            flag = true;
        mSafeMode = flag;
        try {
            if(SystemProperties.getInt("persist.sys.safemode", 0) != 0) {
                mSafeMode = true;
                SystemProperties.set("persist.sys.safemode", "");
            }
        }
        catch(IllegalArgumentException illegalargumentexception) { }
        if(mSafeMode)
            Log.i("WindowManager", (new StringBuilder()).append("SAFE MODE ENABLED (menu=").append(i).append(" s=").append(j).append(" dpad=").append(k).append(" trackball=").append(l).append(")").toString());
        else
            Log.i("WindowManager", "SAFE MODE not enabled");
        mPolicy.setSafeMode(mSafeMode);
        return mSafeMode;
    }

    public void disableKeyguard(IBinder ibinder, String s) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0)
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        TokenWatcher tokenwatcher = mKeyguardTokenWatcher;
        tokenwatcher;
        JVM INSTR monitorenter ;
        mKeyguardTokenWatcher.acquire(ibinder, s);
        return;
    }

    public void dismissKeyguard() {
        if(mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0)
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mPolicy.dismissKeyguardLw();
        return;
    }

    void dispatchWallpaperVisibility(WindowState windowstate, boolean flag) {
        if(windowstate.mWallpaperVisible == flag)
            break MISSING_BLOCK_LABEL_23;
        windowstate.mWallpaperVisible = flag;
        windowstate.mClient.dispatchAppVisibility(flag);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void displayReady() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mDisplay != null)
            throw new IllegalStateException("Display already initialized");
        break MISSING_BLOCK_LABEL_30;
        Exception exception;
        exception;
        throw exception;
        mDisplay = ((WindowManager)mContext.getSystemService("window")).getDefaultDisplay();
        mIsTouchDevice = mContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen");
        synchronized(mDisplaySizeLock) {
            mInitialDisplayWidth = mDisplay.getRawWidth();
            mInitialDisplayHeight = mDisplay.getRawHeight();
            int i = mDisplay.getRotation();
            if(i == 1 || i == 3) {
                int j = mInitialDisplayWidth;
                mInitialDisplayWidth = mInitialDisplayHeight;
                mInitialDisplayHeight = j;
            }
            int k = mInitialDisplayWidth;
            mAppDisplayWidth = k;
            mCurDisplayWidth = k;
            mBaseDisplayWidth = k;
            int l = mInitialDisplayHeight;
            mAppDisplayHeight = l;
            mCurDisplayHeight = l;
            mBaseDisplayHeight = l;
            mAnimator.setDisplayDimensions(mCurDisplayWidth, mCurDisplayHeight, mAppDisplayWidth, mAppDisplayHeight);
        }
        mInputManager.setDisplaySize(0, mDisplay.getRawWidth(), mDisplay.getRawHeight(), mDisplay.getRawExternalWidth(), mDisplay.getRawExternalHeight());
        mInputManager.setDisplayOrientation(0, mDisplay.getRotation(), mDisplay.getExternalRotation());
        mPolicy.setInitialDisplaySize(mDisplay, mInitialDisplayWidth, mInitialDisplayHeight);
        hashmap;
        JVM INSTR monitorexit ;
        try {
            mActivityManager.updateConfiguration(null);
        }
        catch(RemoteException remoteexception) { }
        synchronized(mWindowMap) {
            readForcedDisplaySizeLocked();
        }
        return;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        exception2;
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception2;
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump WindowManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L6:
        return;
_L2:
        boolean flag;
        int i;
        flag = false;
        i = 0;
_L4:
        String s;
        int j;
        String s1;
        if(i < as.length) {
            s1 = as[i];
            if(s1 != null && s1.length() > 0 && s1.charAt(0) == '-')
                break MISSING_BLOCK_LABEL_164;
        }
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_638;
        s = as[i];
        j = i + 1;
        if(!"lastanr".equals(s) && !"l".equals(s))
            break; /* Loop/switch isn't completed */
        HashMap hashmap1 = mWindowMap;
        hashmap1;
        JVM INSTR monitorenter ;
        dumpLastANRLocked(printwriter);
        continue; /* Loop/switch isn't completed */
        i++;
        if("-a".equals(s1)) {
            flag = true;
        } else {
            if("-h".equals(s1)) {
                printwriter.println("Window manager dump options:");
                printwriter.println("  [-a] [-h] [cmd] ...");
                printwriter.println("  cmd may be one of:");
                printwriter.println("    l[astanr]: last ANR information");
                printwriter.println("    p[policy]: policy state");
                printwriter.println("    s[essions]: active sessions");
                printwriter.println("    t[okens]: token list");
                printwriter.println("    w[indows]: window list");
                printwriter.println("  cmd may also be a NAME to dump windows.  NAME may");
                printwriter.println("    be a partial substring in a window name, a");
                printwriter.println("    Window hex object identifier, or");
                printwriter.println("    \"all\" for all windows, or");
                printwriter.println("    \"visible\" for the visible windows.");
                printwriter.println("  -a: include all available server state.");
                continue; /* Loop/switch isn't completed */
            }
            printwriter.println((new StringBuilder()).append("Unknown argument: ").append(s1).append("; use -h for help").toString());
        }
        if(true) goto _L4; else goto _L3
_L3:
        if(!"policy".equals(s) && !"p".equals(s))
            break MISSING_BLOCK_LABEL_382;
        HashMap hashmap2 = mWindowMap;
        hashmap2;
        JVM INSTR monitorenter ;
        dumpPolicyLocked(printwriter, as, true);
        continue; /* Loop/switch isn't completed */
        if(!"sessions".equals(s) && !"s".equals(s))
            break MISSING_BLOCK_LABEL_433;
        HashMap hashmap3 = mWindowMap;
        hashmap3;
        JVM INSTR monitorenter ;
        dumpSessionsLocked(printwriter, true);
        continue; /* Loop/switch isn't completed */
        if(!"tokens".equals(s) && !"t".equals(s))
            break MISSING_BLOCK_LABEL_484;
        HashMap hashmap4 = mWindowMap;
        hashmap4;
        JVM INSTR monitorenter ;
        dumpTokensLocked(printwriter, true);
        continue; /* Loop/switch isn't completed */
        if(!"windows".equals(s) && !"w".equals(s))
            break MISSING_BLOCK_LABEL_536;
        HashMap hashmap5 = mWindowMap;
        hashmap5;
        JVM INSTR monitorenter ;
        dumpWindowsLocked(printwriter, true, null);
        continue; /* Loop/switch isn't completed */
        if(!"all".equals(s) && !"a".equals(s))
            break MISSING_BLOCK_LABEL_588;
        HashMap hashmap6 = mWindowMap;
        hashmap6;
        JVM INSTR monitorenter ;
        dumpWindowsLocked(printwriter, true, null);
        continue; /* Loop/switch isn't completed */
        if(!dumpWindows(printwriter, s, as, j, flag)) {
            printwriter.println((new StringBuilder()).append("Bad window command, or no windows match: ").append(s).toString());
            printwriter.println("Use -h for help.");
        }
        continue; /* Loop/switch isn't completed */
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        printwriter.println();
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpLastANRLocked(printwriter);
        printwriter.println();
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpPolicyLocked(printwriter, as, flag);
        printwriter.println();
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpSessionsLocked(printwriter, flag);
        printwriter.println();
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpTokensLocked(printwriter, flag);
        printwriter.println();
        if(flag)
            printwriter.println("-------------------------------------------------------------------------------");
        dumpWindowsLocked(printwriter, flag, null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    void dumpAnimatingAppTokensLocked() {
        for(int i = -1 + mAnimatingAppTokens.size(); i >= 0; i--)
            Slog.v("WindowManager", (new StringBuilder()).append("  #").append(i).append(": ").append(((WindowToken) ((AppWindowToken)mAnimatingAppTokens.get(i))).token).toString());

    }

    void dumpAppTokensLocked() {
        for(int i = -1 + mAppTokens.size(); i >= 0; i--)
            Slog.v("WindowManager", (new StringBuilder()).append("  #").append(i).append(": ").append(((WindowToken) ((AppWindowToken)mAppTokens.get(i))).token).toString());

    }

    void dumpLastANRLocked(PrintWriter printwriter) {
        printwriter.println("WINDOW MANAGER LAST ANR (dumpsys window lastanr)");
        if(mLastANRState == null)
            printwriter.println("  <no ANR has occurred since boot>");
        else
            printwriter.println(mLastANRState);
    }

    void dumpPolicyLocked(PrintWriter printwriter, String as[], boolean flag) {
        printwriter.println("WINDOW MANAGER POLICY STATE (dumpsys window policy)");
        mPolicy.dump("    ", printwriter, as);
    }

    void dumpSessionsLocked(PrintWriter printwriter, boolean flag) {
        printwriter.println("WINDOW MANAGER SESSIONS (dumpsys window sessions)");
        if(mSessions.size() > 0) {
            Session session;
            for(Iterator iterator = mSessions.iterator(); iterator.hasNext(); session.dump(printwriter, "    ")) {
                session = (Session)iterator.next();
                printwriter.print("  Session ");
                printwriter.print(session);
                printwriter.println(':');
            }

        }
    }

    void dumpTokensLocked(PrintWriter printwriter, boolean flag) {
        printwriter.println("WINDOW MANAGER TOKENS (dumpsys window tokens)");
        if(mTokenMap.size() > 0) {
            printwriter.println("  All tokens:");
            for(Iterator iterator = mTokenMap.values().iterator(); iterator.hasNext();) {
                WindowToken windowtoken5 = (WindowToken)iterator.next();
                printwriter.print("  Token ");
                printwriter.print(windowtoken5.token);
                if(flag) {
                    printwriter.println(':');
                    windowtoken5.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
            }

        }
        if(mWallpaperTokens.size() > 0) {
            printwriter.println();
            printwriter.println("  Wallpaper tokens:");
            int j1 = -1 + mWallpaperTokens.size();
            while(j1 >= 0)  {
                WindowToken windowtoken4 = (WindowToken)mWallpaperTokens.get(j1);
                printwriter.print("  Wallpaper #");
                printwriter.print(j1);
                printwriter.print(' ');
                printwriter.print(windowtoken4);
                if(flag) {
                    printwriter.println(':');
                    windowtoken4.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
                j1--;
            }
        }
        if(mAppTokens.size() > 0) {
            printwriter.println();
            printwriter.println("  Application tokens in Z order:");
            for(int i1 = -1 + mAppTokens.size(); i1 >= 0; i1--) {
                printwriter.print("  App #");
                printwriter.print(i1);
                printwriter.println(": ");
                ((AppWindowToken)mAppTokens.get(i1)).dump(printwriter, "    ");
            }

        }
        if(mFinishedStarting.size() > 0) {
            printwriter.println();
            printwriter.println("  Finishing start of application tokens:");
            int l = -1 + mFinishedStarting.size();
            while(l >= 0)  {
                WindowToken windowtoken3 = (WindowToken)mFinishedStarting.get(l);
                printwriter.print("  Finished Starting #");
                printwriter.print(l);
                printwriter.print(' ');
                printwriter.print(windowtoken3);
                if(flag) {
                    printwriter.println(':');
                    windowtoken3.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
                l--;
            }
        }
        if(mExitingTokens.size() > 0) {
            printwriter.println();
            printwriter.println("  Exiting tokens:");
            int k = -1 + mExitingTokens.size();
            while(k >= 0)  {
                WindowToken windowtoken2 = (WindowToken)mExitingTokens.get(k);
                printwriter.print("  Exiting #");
                printwriter.print(k);
                printwriter.print(' ');
                printwriter.print(windowtoken2);
                if(flag) {
                    printwriter.println(':');
                    windowtoken2.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
                k--;
            }
        }
        if(mExitingAppTokens.size() > 0) {
            printwriter.println();
            printwriter.println("  Exiting application tokens:");
            int j = -1 + mExitingAppTokens.size();
            while(j >= 0)  {
                WindowToken windowtoken1 = (WindowToken)mExitingAppTokens.get(j);
                printwriter.print("  Exiting App #");
                printwriter.print(j);
                printwriter.print(' ');
                printwriter.print(windowtoken1);
                if(flag) {
                    printwriter.println(':');
                    windowtoken1.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
                j--;
            }
        }
        if(mAppTransitionRunning && mAnimatingAppTokens.size() > 0) {
            printwriter.println();
            printwriter.println("  Application tokens during animation:");
            int i = -1 + mAnimatingAppTokens.size();
            while(i >= 0)  {
                WindowToken windowtoken = (WindowToken)mAnimatingAppTokens.get(i);
                printwriter.print("  App moving to bottom #");
                printwriter.print(i);
                printwriter.print(' ');
                printwriter.print(windowtoken);
                if(flag) {
                    printwriter.println(':');
                    windowtoken.dump(printwriter, "    ");
                } else {
                    printwriter.println();
                }
                i--;
            }
        }
        if(mOpeningApps.size() > 0 || mClosingApps.size() > 0) {
            printwriter.println();
            if(mOpeningApps.size() > 0) {
                printwriter.print("  mOpeningApps=");
                printwriter.println(mOpeningApps);
            }
            if(mClosingApps.size() > 0) {
                printwriter.print("  mClosingApps=");
                printwriter.println(mClosingApps);
            }
        }
    }

    boolean dumpWindows(PrintWriter printwriter, String s, String as[], int i, boolean flag) {
        ArrayList arraylist = new ArrayList();
        if(!"visible".equals(s)) goto _L2; else goto _L1
_L1:
        synchronized(mWindowMap) {
            int j;
            RuntimeException runtimeexception;
            HashMap hashmap;
            Exception exception;
            HashMap hashmap1;
            boolean flag1;
            WindowState windowstate;
            int l;
            for(int i1 = -1 + mWindows.size(); i1 >= 0; i1--) {
                WindowState windowstate1 = (WindowState)mWindows.get(i1);
                if(windowstate1.mWinAnimator.mSurfaceShown)
                    arraylist.add(windowstate1);
                break MISSING_BLOCK_LABEL_271;
            }

        }
_L5:
        if(arraylist.size() > 0) goto _L4; else goto _L3
_L3:
        flag1 = false;
_L6:
        return flag1;
        exception1;
        hashmap2;
        JVM INSTR monitorexit ;
        throw exception1;
_L2:
        j = 0;
        l = Integer.parseInt(s, 16);
        j = l;
        s = null;
_L7:
        hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        for(int k = -1 + mWindows.size(); k >= 0; k--) {
            windowstate = (WindowState)mWindows.get(k);
            if(s != null) {
                if(windowstate.mAttrs.getTitle().toString().contains(s))
                    arraylist.add(windowstate);
            } else
            if(System.identityHashCode(windowstate) == j)
                arraylist.add(windowstate);
            break MISSING_BLOCK_LABEL_277;
        }

        break MISSING_BLOCK_LABEL_225;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L4:
        hashmap1 = mWindowMap;
        hashmap1;
        JVM INSTR monitorenter ;
        dumpWindowsLocked(printwriter, flag, arraylist);
        flag1 = true;
          goto _L6
        runtimeexception;
          goto _L7
    }

    void dumpWindowsLocked() {
        for(int i = -1 + mWindows.size(); i >= 0; i--)
            Slog.v("WindowManager", (new StringBuilder()).append("  #").append(i).append(": ").append(mWindows.get(i)).toString());

    }

    void dumpWindowsLocked(PrintWriter printwriter, boolean flag, ArrayList arraylist) {
        printwriter.println("WINDOW MANAGER WINDOWS (dumpsys window windows)");
        dumpWindowsNoHeaderLocked(printwriter, flag, arraylist);
    }

    void dumpWindowsNoHeaderLocked(PrintWriter printwriter, boolean flag, ArrayList arraylist) {
        int i = -1 + mWindows.size();
        while(i >= 0)  {
            WindowState windowstate6 = (WindowState)mWindows.get(i);
            if(arraylist == null || arraylist.contains(windowstate6)) {
                printwriter.print("  Window #");
                printwriter.print(i);
                printwriter.print(' ');
                printwriter.print(windowstate6);
                printwriter.println(":");
                boolean flag1;
                if(flag || arraylist != null)
                    flag1 = true;
                else
                    flag1 = false;
                windowstate6.dump(printwriter, "    ", flag1);
            }
            i--;
        }
        if(mInputMethodDialogs.size() > 0) {
            printwriter.println();
            printwriter.println("  Input method dialogs:");
            for(int j2 = -1 + mInputMethodDialogs.size(); j2 >= 0; j2--) {
                WindowState windowstate5 = (WindowState)mInputMethodDialogs.get(j2);
                if(arraylist == null || arraylist.contains(windowstate5)) {
                    printwriter.print("  IM Dialog #");
                    printwriter.print(j2);
                    printwriter.print(": ");
                    printwriter.println(windowstate5);
                }
            }

        }
        if(mPendingRemove.size() > 0) {
            printwriter.println();
            printwriter.println("  Remove pending for:");
            int i2 = -1 + mPendingRemove.size();
            while(i2 >= 0)  {
                WindowState windowstate4 = (WindowState)mPendingRemove.get(i2);
                if(arraylist == null || arraylist.contains(windowstate4)) {
                    printwriter.print("  Remove #");
                    printwriter.print(i2);
                    printwriter.print(' ');
                    printwriter.print(windowstate4);
                    if(flag) {
                        printwriter.println(":");
                        windowstate4.dump(printwriter, "    ", true);
                    } else {
                        printwriter.println();
                    }
                }
                i2--;
            }
        }
        if(mForceRemoves != null && mForceRemoves.size() > 0) {
            printwriter.println();
            printwriter.println("  Windows force removing:");
            int l1 = -1 + mForceRemoves.size();
            while(l1 >= 0)  {
                WindowState windowstate3 = (WindowState)mForceRemoves.get(l1);
                printwriter.print("  Removing #");
                printwriter.print(l1);
                printwriter.print(' ');
                printwriter.print(windowstate3);
                if(flag) {
                    printwriter.println(":");
                    windowstate3.dump(printwriter, "    ", true);
                } else {
                    printwriter.println();
                }
                l1--;
            }
        }
        if(mDestroySurface.size() > 0) {
            printwriter.println();
            printwriter.println("  Windows waiting to destroy their surface:");
            int k1 = -1 + mDestroySurface.size();
            while(k1 >= 0)  {
                WindowState windowstate2 = (WindowState)mDestroySurface.get(k1);
                if(arraylist == null || arraylist.contains(windowstate2)) {
                    printwriter.print("  Destroy #");
                    printwriter.print(k1);
                    printwriter.print(' ');
                    printwriter.print(windowstate2);
                    if(flag) {
                        printwriter.println(":");
                        windowstate2.dump(printwriter, "    ", true);
                    } else {
                        printwriter.println();
                    }
                }
                k1--;
            }
        }
        if(mLosingFocus.size() > 0) {
            printwriter.println();
            printwriter.println("  Windows losing focus:");
            int j1 = -1 + mLosingFocus.size();
            while(j1 >= 0)  {
                WindowState windowstate1 = (WindowState)mLosingFocus.get(j1);
                if(arraylist == null || arraylist.contains(windowstate1)) {
                    printwriter.print("  Losing #");
                    printwriter.print(j1);
                    printwriter.print(' ');
                    printwriter.print(windowstate1);
                    if(flag) {
                        printwriter.println(":");
                        windowstate1.dump(printwriter, "    ", true);
                    } else {
                        printwriter.println();
                    }
                }
                j1--;
            }
        }
        if(mResizingWindows.size() > 0) {
            printwriter.println();
            printwriter.println("  Windows waiting to resize:");
            int i1 = -1 + mResizingWindows.size();
            while(i1 >= 0)  {
                WindowState windowstate = (WindowState)mResizingWindows.get(i1);
                if(arraylist == null || arraylist.contains(windowstate)) {
                    printwriter.print("  Resizing #");
                    printwriter.print(i1);
                    printwriter.print(' ');
                    printwriter.print(windowstate);
                    if(flag) {
                        printwriter.println(":");
                        windowstate.dump(printwriter, "    ", true);
                    } else {
                        printwriter.println();
                    }
                }
                i1--;
            }
        }
        if(mWaitingForDrawn.size() > 0) {
            printwriter.println();
            printwriter.println("  Clients waiting for these windows to be drawn:");
            for(int l = -1 + mWaitingForDrawn.size(); l >= 0; l--) {
                Pair pair = (Pair)mWaitingForDrawn.get(l);
                printwriter.print("  Waiting #");
                printwriter.print(l);
                printwriter.print(' ');
                printwriter.print(pair.first);
                printwriter.print(": ");
                printwriter.println(pair.second);
            }

        }
        printwriter.println();
        if(mDisplay != null) {
            printwriter.print("  Display: init=");
            printwriter.print(mInitialDisplayWidth);
            printwriter.print("x");
            printwriter.print(mInitialDisplayHeight);
            if(mInitialDisplayWidth != mBaseDisplayWidth || mInitialDisplayHeight != mBaseDisplayHeight) {
                printwriter.print(" base=");
                printwriter.print(mBaseDisplayWidth);
                printwriter.print("x");
                printwriter.print(mBaseDisplayHeight);
            }
            int j = mDisplay.getRawWidth();
            int k = mDisplay.getRawHeight();
            if(j != mCurDisplayWidth || k != mCurDisplayHeight) {
                printwriter.print(" raw=");
                printwriter.print(j);
                printwriter.print("x");
                printwriter.print(k);
            }
            printwriter.print(" cur=");
            printwriter.print(mCurDisplayWidth);
            printwriter.print("x");
            printwriter.print(mCurDisplayHeight);
            printwriter.print(" app=");
            printwriter.print(mAppDisplayWidth);
            printwriter.print("x");
            printwriter.print(mAppDisplayHeight);
            printwriter.print(" rng=");
            printwriter.print(mSmallestDisplayWidth);
            printwriter.print("x");
            printwriter.print(mSmallestDisplayHeight);
            printwriter.print("-");
            printwriter.print(mLargestDisplayWidth);
            printwriter.print("x");
            printwriter.println(mLargestDisplayHeight);
        } else {
            printwriter.println("  NO DISPLAY");
        }
        printwriter.print("  mCurConfiguration=");
        printwriter.println(mCurConfiguration);
        printwriter.print("  mCurrentFocus=");
        printwriter.println(mCurrentFocus);
        if(mLastFocus != mCurrentFocus) {
            printwriter.print("  mLastFocus=");
            printwriter.println(mLastFocus);
        }
        printwriter.print("  mFocusedApp=");
        printwriter.println(mFocusedApp);
        if(mInputMethodTarget != null) {
            printwriter.print("  mInputMethodTarget=");
            printwriter.println(mInputMethodTarget);
        }
        printwriter.print("  mInTouchMode=");
        printwriter.print(mInTouchMode);
        printwriter.print(" mLayoutSeq=");
        printwriter.println(mLayoutSeq);
        if(!flag) goto _L2; else goto _L1
_L1:
        printwriter.print("  mSystemDecorRect=");
        printwriter.print(mSystemDecorRect.toShortString());
        printwriter.print(" mSystemDecorLayer=");
        printwriter.println(mSystemDecorLayer);
        if(mLastStatusBarVisibility != 0) {
            printwriter.print("  mLastStatusBarVisibility=0x");
            printwriter.println(Integer.toHexString(mLastStatusBarVisibility));
        }
        if(mInputMethodWindow != null) {
            printwriter.print("  mInputMethodWindow=");
            printwriter.println(mInputMethodWindow);
        }
        printwriter.print("  mWallpaperTarget=");
        printwriter.println(mWallpaperTarget);
        if(mLowerWallpaperTarget != null && mUpperWallpaperTarget != null) {
            printwriter.print("  mLowerWallpaperTarget=");
            printwriter.println(mLowerWallpaperTarget);
            printwriter.print("  mUpperWallpaperTarget=");
            printwriter.println(mUpperWallpaperTarget);
        }
        printwriter.print("  mLastWallpaperX=");
        printwriter.print(mLastWallpaperX);
        printwriter.print(" mLastWallpaperY=");
        printwriter.println(mLastWallpaperY);
        if(mInputMethodAnimLayerAdjustment != 0 || mWallpaperAnimLayerAdjustment != 0) {
            printwriter.print("  mInputMethodAnimLayerAdjustment=");
            printwriter.print(mInputMethodAnimLayerAdjustment);
            printwriter.print("  mWallpaperAnimLayerAdjustment=");
            printwriter.println(mWallpaperAnimLayerAdjustment);
        }
        printwriter.print("  mSystemBooted=");
        printwriter.print(mSystemBooted);
        printwriter.print(" mDisplayEnabled=");
        printwriter.println(mDisplayEnabled);
        printwriter.print("  mLayoutNeeded=");
        printwriter.print(mLayoutNeeded);
        printwriter.print("mTransactionSequence=");
        printwriter.println(mTransactionSequence);
        printwriter.print("  mDisplayFrozen=");
        printwriter.print(mDisplayFrozen);
        printwriter.print(" mWindowsFreezingScreen=");
        printwriter.print(mWindowsFreezingScreen);
        printwriter.print(" mAppsFreezingScreen=");
        printwriter.print(mAppsFreezingScreen);
        printwriter.print(" mWaitingForConfig=");
        printwriter.println(mWaitingForConfig);
        printwriter.print("  mRotation=");
        printwriter.print(mRotation);
        printwriter.print(" mAltOrientation=");
        printwriter.println(mAltOrientation);
        printwriter.print("  mLastWindowForcedOrientation=");
        printwriter.print(mLastWindowForcedOrientation);
        printwriter.print(" mForcedAppOrientation=");
        printwriter.println(mForcedAppOrientation);
        printwriter.print("  mDeferredRotationPauseCount=");
        printwriter.println(mDeferredRotationPauseCount);
        if(mAnimator.mScreenRotationAnimation != null) {
            printwriter.println("  mScreenRotationAnimation:");
            mAnimator.mScreenRotationAnimation.printTo("    ", printwriter);
        }
        printwriter.print("  mWindowAnimationScale=");
        printwriter.print(mWindowAnimationScale);
        printwriter.print(" mTransitionWindowAnimationScale=");
        printwriter.print(mTransitionAnimationScale);
        printwriter.print(" mAnimatorDurationScale=");
        printwriter.println(mAnimatorDurationScale);
        printwriter.print("  mTraversalScheduled=");
        printwriter.print(mTraversalScheduled);
        printwriter.print(" mNextAppTransition=0x");
        printwriter.print(Integer.toHexString(mNextAppTransition));
        printwriter.print(" mAppTransitionReady=");
        printwriter.println(mAppTransitionReady);
        printwriter.print("  mAppTransitionRunning=");
        printwriter.print(mAppTransitionRunning);
        printwriter.print(" mAppTransitionTimeout=");
        printwriter.println(mAppTransitionTimeout);
        if(mNextAppTransitionType != 0) {
            printwriter.print("  mNextAppTransitionType=");
            printwriter.println(mNextAppTransitionType);
        }
        mNextAppTransitionType;
        JVM INSTR tableswitch 1 4: default 1944
    //                   1 2026
    //                   2 2080
    //                   3 2143
    //                   4 2143;
           goto _L3 _L4 _L5 _L6 _L6
_L3:
        if(mNextAppTransitionCallback != null) {
            printwriter.print("  mNextAppTransitionCallback=");
            printwriter.println(mNextAppTransitionCallback);
        }
        printwriter.print("  mStartingIconInTransition=");
        printwriter.print(mStartingIconInTransition);
        printwriter.print(" mSkipAppTransitionAnimation=");
        printwriter.println(mSkipAppTransitionAnimation);
        printwriter.println("  Window Animator:");
        mAnimator.dump(printwriter, "    ", flag);
_L2:
        return;
_L4:
        printwriter.print("  mNextAppTransitionPackage=");
        printwriter.println(mNextAppTransitionPackage);
        printwriter.print("  mNextAppTransitionEnter=0x");
        printwriter.print(Integer.toHexString(mNextAppTransitionEnter));
        printwriter.print(" mNextAppTransitionExit=0x");
        printwriter.println(Integer.toHexString(mNextAppTransitionExit));
        continue; /* Loop/switch isn't completed */
_L5:
        printwriter.print("  mNextAppTransitionStartX=");
        printwriter.print(mNextAppTransitionStartX);
        printwriter.print(" mNextAppTransitionStartY=");
        printwriter.println(mNextAppTransitionStartY);
        printwriter.print("  mNextAppTransitionStartWidth=");
        printwriter.print(mNextAppTransitionStartWidth);
        printwriter.print(" mNextAppTransitionStartHeight=");
        printwriter.println(mNextAppTransitionStartHeight);
        continue; /* Loop/switch isn't completed */
_L6:
        printwriter.print("  mNextAppTransitionThumbnail=");
        printwriter.print(mNextAppTransitionThumbnail);
        printwriter.print(" mNextAppTransitionStartX=");
        printwriter.print(mNextAppTransitionStartX);
        printwriter.print(" mNextAppTransitionStartY=");
        printwriter.println(mNextAppTransitionStartY);
        printwriter.print("  mNextAppTransitionDelayed=");
        printwriter.println(mNextAppTransitionDelayed);
        if(true) goto _L3; else goto _L7
_L7:
    }

    public void enableScreenAfterBoot() {
        synchronized(mWindowMap) {
            if(mSystemBooted)
                break MISSING_BLOCK_LABEL_73;
            mSystemBooted = true;
            hideBootMessagesLocked();
            Message message = mH.obtainMessage(23);
            mH.sendMessageDelayed(message, 30000L);
        }
        mPolicy.systemBooted();
        performEnableScreen();
          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    void enableScreenIfNeededLocked() {
        if(!mDisplayEnabled && (mSystemBooted || mShowingBootMessages))
            mH.sendMessage(mH.obtainMessage(16));
    }

    public void executeAppTransition() {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "executeAppTransition()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mNextAppTransition != -1) {
            mAppTransitionReady = true;
            long l = Binder.clearCallingIdentity();
            performLayoutAndPlaceSurfacesLocked();
            Binder.restoreCallingIdentity(l);
        }
        return;
    }

    public void exitKeyguardSecurely(final IOnKeyguardExitResult callback) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        } else {
            mPolicy.exitKeyguardSecurely(new android.view.WindowManagerPolicy.OnKeyguardExitResult() {

                public void onKeyguardExitResult(boolean flag) {
                    callback.onKeyguardExitResult(flag);
_L2:
                    return;
                    RemoteException remoteexception;
                    remoteexception;
                    if(true) goto _L2; else goto _L1
_L1:
                }

                final WindowManagerService this$0;
                final IOnKeyguardExitResult val$callback;

             {
                this$0 = WindowManagerService.this;
                callback = ionkeyguardexitresult;
                super();
            }
            });
            return;
        }
    }

    AppWindowToken findAppWindowToken(IBinder ibinder) {
        WindowToken windowtoken = (WindowToken)mTokenMap.get(ibinder);
        AppWindowToken appwindowtoken;
        if(windowtoken == null)
            appwindowtoken = null;
        else
            appwindowtoken = windowtoken.appWindowToken;
        return appwindowtoken;
    }

    int findDesiredInputMethodWindowIndexLocked(boolean flag) {
        ArrayList arraylist;
        WindowState windowstate;
        int j;
        WindowState windowstate1;
        AppWindowToken appwindowtoken;
        WindowState windowstate2;
        int l;
        arraylist = mWindows;
        int i = arraylist.size();
        windowstate = null;
        j = i;
        do {
            if(j <= 0)
                break;
            j--;
            windowstate = (WindowState)arraylist.get(j);
            if(!canBeImeTarget(windowstate))
                continue;
            if(!flag && windowstate.mAttrs.type == 3 && j > 0) {
                WindowState windowstate4 = (WindowState)arraylist.get(j - 1);
                if(windowstate4.mAppToken == windowstate.mAppToken && canBeImeTarget(windowstate4)) {
                    j--;
                    windowstate = windowstate4;
                }
            }
            break;
        } while(true);
        if(mInputMethodTarget != null && windowstate != null && mInputMethodTarget.isDisplayedLw() && mInputMethodTarget.mExiting && mInputMethodTarget.mWinAnimator.mAnimLayer > windowstate.mWinAnimator.mAnimLayer) {
            windowstate = mInputMethodTarget;
            j = arraylist.indexOf(windowstate);
        }
        if(!flag || windowstate == null)
            break MISSING_BLOCK_LABEL_399;
        windowstate1 = mInputMethodTarget;
        if(windowstate1 == null || windowstate1.mAppToken == null)
            break MISSING_BLOCK_LABEL_399;
        appwindowtoken = windowstate1.mAppToken;
        windowstate2 = null;
        l = 0;
        if(!appwindowtoken.mAppAnimator.animating && appwindowtoken.mAppAnimator.animation == null) goto _L2; else goto _L1
_L1:
        int i1 = arraylist.indexOf(windowstate1);
_L5:
        if(i1 < 0) goto _L2; else goto _L3
_L3:
        WindowState windowstate3 = (WindowState)arraylist.get(i1);
        if(windowstate3.mAppToken == appwindowtoken) goto _L4; else goto _L2
_L2:
        int k;
        if(windowstate2 == null)
            break MISSING_BLOCK_LABEL_399;
        if(mNextAppTransition != -1) {
            mInputMethodTargetWaitingAnim = true;
            mInputMethodTarget = windowstate2;
            k = l + 1;
        } else {
            if(!windowstate2.mWinAnimator.isAnimating() || windowstate2.mWinAnimator.mAnimLayer <= windowstate.mWinAnimator.mAnimLayer)
                break MISSING_BLOCK_LABEL_399;
            mInputMethodTargetWaitingAnim = true;
            mInputMethodTarget = windowstate2;
            k = l + 1;
        }
_L6:
        return k;
_L4:
        if(!windowstate3.mRemoved && (windowstate2 == null || windowstate3.mWinAnimator.mAnimLayer > windowstate2.mWinAnimator.mAnimLayer)) {
            windowstate2 = windowstate3;
            l = i1;
        }
        i1--;
          goto _L5
        if(windowstate != null) {
            if(flag) {
                mInputMethodTarget = windowstate;
                mInputMethodTargetWaitingAnim = false;
                if(windowstate.mAppToken != null)
                    setInputMethodAnimLayerAdjustment(windowstate.mAppToken.mAppAnimator.animLayerAdjustment);
                else
                    setInputMethodAnimLayerAdjustment(0);
            }
            k = j + 1;
        } else {
            if(flag) {
                mInputMethodTarget = null;
                setInputMethodAnimLayerAdjustment(0);
            }
            k = -1;
        }
          goto _L6
    }

    public void finishDrawingWindow(Session session, IWindow iwindow) {
        long l = Binder.clearCallingIdentity();
        synchronized(mWindowMap) {
            WindowState windowstate = windowForClientLocked(session, iwindow, false);
            if(windowstate != null && windowstate.mWinAnimator.finishDrawingLocked()) {
                if((0x100000 & windowstate.mAttrs.flags) != 0)
                    adjustWallpaperWindowsLocked();
                mLayoutNeeded = true;
                performLayoutAndPlaceSurfacesLocked();
            }
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void freezeRotation(int i) {
        if(!checkCallingPermission("android.permission.SET_ORIENTATION", "freezeRotation()"))
            throw new SecurityException("Requires SET_ORIENTATION permission");
        if(i < -1 || i > 3)
            throw new IllegalArgumentException("Rotation argument must be -1 or a valid rotation constant.");
        WindowManagerPolicy windowmanagerpolicy = mPolicy;
        if(i == -1)
            i = mRotation;
        windowmanagerpolicy.setUserRotationMode(1, i);
        updateRotationUnchecked(false, false);
    }

    public float getAnimationScale(int i) {
        i;
        JVM INSTR tableswitch 0 2: default 28
    //                   0 32
    //                   1 40
    //                   2 48;
           goto _L1 _L2 _L3 _L4
_L1:
        float f = 0.0F;
_L6:
        return f;
_L2:
        f = mWindowAnimationScale;
        continue; /* Loop/switch isn't completed */
_L3:
        f = mTransitionAnimationScale;
        continue; /* Loop/switch isn't completed */
_L4:
        f = mAnimatorDurationScale;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public float[] getAnimationScales() {
        float af[] = new float[3];
        af[0] = mWindowAnimationScale;
        af[1] = mTransitionAnimationScale;
        af[2] = mAnimatorDurationScale;
        return af;
    }

    public int getAppOrientation(IApplicationToken iapplicationtoken) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(iapplicationtoken.asBinder());
        int i;
        if(appwindowtoken == null)
            i = -1;
        else
            i = appwindowtoken.requestedOrientation;
        return i;
    }

    public void getCurrentSizeRange(Point point, Point point1) {
        Object obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        point.x = mSmallestDisplayWidth;
        point.y = mSmallestDisplayHeight;
        point1.x = mLargestDisplayWidth;
        point1.y = mLargestDisplayHeight;
        return;
    }

    public void getDisplaySize(Point point) {
        Object obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        point.x = mAppDisplayWidth;
        point.y = mAppDisplayHeight;
        return;
    }

    public IBinder getFocusedWindowClientToken() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = getFocusedWindowLocked();
        IBinder ibinder;
        if(windowstate != null)
            ibinder = windowstate.mClient.asBinder();
        else
            ibinder = null;
        return ibinder;
    }

    public void getInitialDisplaySize(Point point) {
        Object obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        point.x = mInitialDisplayWidth;
        point.y = mInitialDisplayHeight;
        return;
    }

    public InputManagerService getInputManagerService() {
        return mInputManager;
    }

    public int getLidState() {
        int i = 0;
        int j = mInputManager.getSwitchState(-1, -256, 0);
        if(j <= 0)
            if(j == 0)
                i = 1;
            else
                i = -1;
        return i;
    }

    public int getMaximumSizeDimension() {
        Object obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        int i;
        if(mBaseDisplayWidth > mBaseDisplayHeight)
            i = mBaseDisplayWidth;
        else
            i = mBaseDisplayHeight;
        return i;
    }

    public int getOrientationFromAppTokensLocked() {
        int i;
        int j;
        boolean flag;
        boolean flag1;
        boolean flag2;
        int k;
        i = 0;
        j = -1;
        flag = false;
        flag1 = false;
        flag2 = false;
        k = -1 + mAppTokens.size();
_L2:
        AppWindowToken appwindowtoken;
        if(k < 0)
            break MISSING_BLOCK_LABEL_198;
        appwindowtoken = (AppWindowToken)mAppTokens.get(k);
        if(flag || ((WindowToken) (appwindowtoken)).hidden || !appwindowtoken.hiddenRequested)
            break; /* Loop/switch isn't completed */
_L6:
        k--;
        if(true) goto _L2; else goto _L1
_L1:
        if(!flag1 || i == appwindowtoken.groupId || j == 3 || !flag2) goto _L4; else goto _L3
_L3:
        int l = j;
_L8:
        return l;
_L4:
        if(appwindowtoken.hiddenRequested || appwindowtoken.willBeHidden) goto _L6; else goto _L5
_L5:
        if(!flag1) {
            flag1 = true;
            i = appwindowtoken.groupId;
            j = appwindowtoken.requestedOrientation;
        }
        l = appwindowtoken.requestedOrientation;
        flag2 = appwindowtoken.appFullscreen;
        if(flag2 && l != 3 || l != -1 && l != 3) goto _L8; else goto _L7
_L7:
        boolean flag3;
        if(l == 3)
            flag3 = true;
        else
            flag3 = false;
        flag |= flag3;
          goto _L6
        l = -1;
          goto _L8
    }

    public int getOrientationFromWindowsLocked() {
        if(!mDisplayFrozen && mOpeningApps.size() <= 0 && mClosingApps.size() <= 0) goto _L2; else goto _L1
_L1:
        int i = mLastWindowForcedOrientation;
_L4:
        return i;
_L2:
        int j = -1 + mWindows.size();
        do {
            do {
                WindowState windowstate;
                if(j >= 0) {
                    windowstate = (WindowState)mWindows.get(j);
                    j--;
                    if(windowstate.mAppToken == null)
                        continue;
                    mLastWindowForcedOrientation = -1;
                    i = -1;
                } else {
                    mLastWindowForcedOrientation = -1;
                    i = -1;
                }
                continue; /* Loop/switch isn't completed */
            } while(!windowstate.isVisibleLw() || !windowstate.mPolicyVisibilityAfterAnim);
            i = windowstate.mAttrs.screenOrientation;
        } while(i == -1 || i == 3);
        mLastWindowForcedOrientation = i;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getPendingAppTransition() {
        return mNextAppTransition;
    }

    public int getPreferredOptionsPanelGravity() {
        byte byte0 = 81;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i = getRotation();
        if(mInitialDisplayWidth >= mInitialDisplayHeight) goto _L2; else goto _L1
_L1:
        i;
        JVM INSTR tableswitch 1 3: default 56
    //                   1 61
    //                   2 69
    //                   3 79;
           goto _L3 _L4 _L5 _L6
_L5:
        break; /* Loop/switch isn't completed */
_L3:
        break; /* Loop/switch isn't completed */
_L4:
        byte0 = 85;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L6:
        hashmap;
        JVM INSTR monitorexit ;
        byte0 = 83;
        break; /* Loop/switch isn't completed */
_L11:
        byte0 = 85;
        break; /* Loop/switch isn't completed */
_L9:
        byte0 = 83;
        break; /* Loop/switch isn't completed */
_L8:
        return byte0;
_L2:
        i;
        JVM INSTR tableswitch 1 3: default 87
    //                   1 95
    //                   2 100
    //                   3 108;
           goto _L7 _L8 _L9 _L8
_L7:
        if(true) goto _L11; else goto _L10
_L10:
    }

    public void getRealDisplaySize(Point point) {
        Object obj = mDisplaySizeLock;
        obj;
        JVM INSTR monitorenter ;
        point.x = mCurDisplayWidth;
        point.y = mCurDisplayHeight;
        return;
    }

    public int getRotation() {
        return mRotation;
    }

    public float getWindowCompatibilityScale(IBinder ibinder) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = (WindowState)mWindowMap.get(ibinder);
        float f;
        if(windowstate != null)
            f = windowstate.mGlobalScale;
        else
            f = 1.0F;
        return f;
    }

    public void getWindowDisplayFrame(Session session, IWindow iwindow, Rect rect) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(session, iwindow, false);
        if(windowstate == null)
            rect.setEmpty();
        else
            rect.set(windowstate.mDisplayFrame);
        return;
    }

    public boolean getWindowFrame(IBinder ibinder, Rect rect) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = (WindowState)mWindowMap.get(ibinder);
        boolean flag;
        if(windowstate != null) {
            rect.set(windowstate.getFrameLw());
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public int handleAppTransitionReadyLocked() {
        int i;
        int k;
        android.view.WindowManager.LayoutParams layoutparams;
        AppWindowToken appwindowtoken;
        int l1;
        i = 0;
        int j = mOpeningApps.size();
        boolean flag = true;
        if(!mDisplayFrozen && !mAppTransitionTimeout) {
            for(int l3 = 0; l3 < j && flag; l3++) {
                AppWindowToken appwindowtoken4 = (AppWindowToken)mOpeningApps.get(l3);
                if(!appwindowtoken4.allDrawn && !appwindowtoken4.startingDisplayed && !appwindowtoken4.startingMoved)
                    flag = false;
            }

        }
        if(flag) {
            k = mNextAppTransition;
            if(mSkipAppTransitionAnimation)
                k = -1;
            mNextAppTransition = -1;
            mAppTransitionReady = false;
            mAppTransitionRunning = true;
            mAppTransitionTimeout = false;
            mStartingIconInTransition = false;
            mSkipAppTransitionAnimation = false;
            mH.removeMessages(13);
            rebuildAppWindowListLocked();
            WindowState windowstate;
            int l;
            boolean flag1;
            boolean flag2;
            int i1;
            int j1;
            int k1;
            if(mWallpaperTarget != null && mWallpaperTarget.mWinAnimator.isAnimating() && !mWallpaperTarget.mWinAnimator.isDummyAnimation())
                windowstate = null;
            else
                windowstate = mWallpaperTarget;
            adjustWallpaperWindowsLocked();
            mInnerFields.mWallpaperMayChange = false;
            layoutparams = null;
            l = -1;
            flag1 = false;
            flag2 = false;
            i1 = mClosingApps.size();
            j1 = i1 + mOpeningApps.size();
            k1 = 0;
            while(k1 < j1)  {
                AppWindowToken appwindowtoken3;
                boolean flag3;
                if(k1 < i1) {
                    appwindowtoken3 = (AppWindowToken)mClosingApps.get(k1);
                    flag3 = true;
                } else {
                    appwindowtoken3 = (AppWindowToken)mOpeningApps.get(k1 - i1);
                    flag3 = 2;
                }
                if(mLowerWallpaperTarget != null && (mLowerWallpaperTarget.mAppToken == appwindowtoken3 || mUpperWallpaperTarget.mAppToken == appwindowtoken3))
                    flag2 |= flag3;
                if(appwindowtoken3.appFullscreen) {
                    WindowState windowstate3 = appwindowtoken3.findMainWindow();
                    if(windowstate3 != null) {
                        layoutparams = windowstate3.mAttrs;
                        l = windowstate3.mLayer;
                        flag1 = true;
                    }
                } else
                if(!flag1) {
                    WindowState windowstate2 = appwindowtoken3.findMainWindow();
                    if(windowstate2 != null && windowstate2.mLayer > l) {
                        layoutparams = windowstate2.mAttrs;
                        l = windowstate2.mLayer;
                    }
                }
                k1++;
            }
            if(flag2 == 3) {
                int i2;
                WindowAnimator windowanimator;
                int j3;
                int k3;
                WindowState windowstate1;
                switch(k) {
                case 4102: 
                case 4104: 
                case 4106: 
                    k = 4110;
                    continue; /* Loop/switch isn't completed */

                case 8199: 
                case 8201: 
                case 8203: 
                    k = 8207;
                    continue; /* Loop/switch isn't completed */
                }
                break;
            } else {
                if(windowstate != null && !mOpeningApps.contains(windowstate.mAppToken))
                    k = 8204;
                else
                if(mWallpaperTarget != null)
                    k = 4109;
                continue; /* Loop/switch isn't completed */
            }
        }
          goto _L1
_L3:
        if(!mPolicy.allowAppAnimationsLw())
            layoutparams = null;
        appwindowtoken = null;
        l1 = 0;
        i2 = mOpeningApps.size();
        for(int j2 = 0; j2 < i2; j2++) {
            AppWindowToken appwindowtoken2 = (AppWindowToken)mOpeningApps.get(j2);
            appwindowtoken2.mAppAnimator.clearThumbnail();
            appwindowtoken2.reportedVisible = false;
            appwindowtoken2.inPendingTransaction = false;
            appwindowtoken2.mAppAnimator.animation = null;
            setTokenVisibilityLocked(appwindowtoken2, layoutparams, true, k, false);
            appwindowtoken2.updateReportedVisibilityLocked();
            appwindowtoken2.waitingToShow = false;
            windowanimator = mAnimator;
            windowanimator.mAnimating = windowanimator.mAnimating | appwindowtoken2.mAppAnimator.showAllWindowsLocked();
            if(layoutparams == null)
                continue;
            int i3 = -1;
            j3 = 0;
            do {
                k3 = ((WindowToken) (appwindowtoken2)).windows.size();
                if(j3 >= k3)
                    break;
                windowstate1 = (WindowState)((WindowToken) (appwindowtoken2)).windows.get(j3);
                if(windowstate1.mWinAnimator.mAnimLayer > i3)
                    i3 = windowstate1.mWinAnimator.mAnimLayer;
                j3++;
            } while(true);
            if(appwindowtoken == null || i3 > l1) {
                appwindowtoken = appwindowtoken2;
                l1 = i3;
            }
        }

        int k2 = mClosingApps.size();
        for(int l2 = 0; l2 < k2; l2++) {
            AppWindowToken appwindowtoken1 = (AppWindowToken)mClosingApps.get(l2);
            appwindowtoken1.mAppAnimator.clearThumbnail();
            appwindowtoken1.inPendingTransaction = false;
            appwindowtoken1.mAppAnimator.animation = null;
            setTokenVisibilityLocked(appwindowtoken1, layoutparams, false, k, false);
            appwindowtoken1.updateReportedVisibilityLocked();
            appwindowtoken1.waitingToHide = false;
            appwindowtoken1.allDrawn = true;
        }

        if(mNextAppTransitionThumbnail != null && appwindowtoken != null && appwindowtoken.mAppAnimator.animation != null) {
            Rect rect = new Rect(0, 0, mNextAppTransitionThumbnail.getWidth(), mNextAppTransitionThumbnail.getHeight());
            try {
                Surface surface = new Surface(mFxSession, Process.myPid(), "thumbnail anim", 0, rect.width(), rect.height(), -3, 4);
                appwindowtoken.mAppAnimator.thumbnail = surface;
                Surface surface1 = new Surface();
                surface1.copyFrom(surface);
                Canvas canvas = surface1.lockCanvas(rect);
                canvas.drawBitmap(mNextAppTransitionThumbnail, 0.0F, 0.0F, null);
                surface1.unlockCanvasAndPost(canvas);
                surface1.release();
                appwindowtoken.mAppAnimator.thumbnailLayer = l1;
                Animation animation = createThumbnailAnimationLocked(k, true, true, mNextAppTransitionDelayed);
                appwindowtoken.mAppAnimator.thumbnailAnimation = animation;
                animation.restrictDuration(10000L);
                animation.scaleCurrentDuration(mTransitionAnimationScale);
                appwindowtoken.mAppAnimator.thumbnailX = mNextAppTransitionStartX;
                appwindowtoken.mAppAnimator.thumbnailY = mNextAppTransitionStartY;
            }
            catch(android.view.Surface.OutOfResourcesException outofresourcesexception) {
                Slog.e("WindowManager", (new StringBuilder()).append("Can't allocate thumbnail surface w=").append(rect.width()).append(" h=").append(rect.height()).toString(), outofresourcesexception);
                appwindowtoken.mAppAnimator.clearThumbnail();
            }
        }
        mNextAppTransitionType = 0;
        mNextAppTransitionPackage = null;
        mNextAppTransitionThumbnail = null;
        scheduleAnimationCallback(mNextAppTransitionCallback);
        mNextAppTransitionCallback = null;
        mOpeningApps.clear();
        mClosingApps.clear();
        i = 0 | 3;
        mLayoutNeeded = true;
        if(!moveInputMethodWindowsIfNeededLocked(true))
            assignLayersLocked();
        updateFocusedWindowLocked(2, false);
        mFocusMayChange = false;
_L1:
        return i;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public boolean hasNavigationBar() {
        return mPolicy.hasNavigationBar();
    }

    public boolean hasSystemNavBar() {
        return mPolicy.hasSystemNavBar();
    }

    public void hideBootMessagesLocked() {
        if(mShowingBootMessages) {
            mShowingBootMessages = false;
            mPolicy.hideBootMessages();
        }
    }

    public boolean inKeyguardRestrictedInputMode() {
        return mPolicy.inKeyguardRestrictedKeyInputMode();
    }

    public boolean inputMethodClientHasFocus(IInputMethodClient iinputmethodclient) {
        boolean flag = true;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i = findDesiredInputMethodWindowIndexLocked(false);
        if(i <= 0) goto _L2; else goto _L1
_L1:
        WindowState windowstate = (WindowState)mWindows.get(i - 1);
        if(windowstate == null) goto _L2; else goto _L3
_L3:
        if(windowstate.mAttrs.type != 3 || windowstate.mAppToken == null) goto _L5; else goto _L4
_L4:
        int j = 0;
_L11:
        if(j >= ((WindowToken) (windowstate.mAppToken)).windows.size()) goto _L5; else goto _L6
_L6:
        WindowState windowstate1 = (WindowState)((WindowToken) (windowstate.mAppToken)).windows.get(j);
        if(windowstate1 == windowstate) goto _L8; else goto _L7
_L7:
        Log.i("WindowManager", (new StringBuilder()).append("Switching to real app window: ").append(windowstate1).toString());
        windowstate = windowstate1;
_L5:
        if((windowstate.mSession.mClient == null || windowstate.mSession.mClient.asBinder() != iinputmethodclient.asBinder()) && (mCurrentFocus == null || mCurrentFocus.mSession.mClient == null || mCurrentFocus.mSession.mClient.asBinder() != iinputmethodclient.asBinder())) goto _L2; else goto _L9
        Exception exception;
        exception;
        throw exception;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        flag = false;
_L9:
        return flag;
_L8:
        j++;
        if(true) goto _L11; else goto _L10
_L10:
    }

    public boolean isHardKeyboardAvailable() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = mHardKeyboardAvailable;
        return flag;
    }

    public boolean isHardKeyboardEnabled() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = mHardKeyboardEnabled;
        return flag;
    }

    public boolean isKeyguardLocked() {
        return mPolicy.isKeyguardLocked();
    }

    public boolean isKeyguardSecure() {
        return mPolicy.isKeyguardSecure();
    }

    public boolean isViewServerRunning() {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if(!isSystemSecure() && checkCallingPermission("android.permission.DUMP", "isViewServerRunning") && mViewServer != null && mViewServer.isRunning())
            flag = true;
        return flag;
    }

    final boolean isWallpaperVisible(WindowState windowstate) {
        boolean flag;
        if(windowstate != null && (!windowstate.mObscured || windowstate.mAppToken != null && windowstate.mAppToken.mAppAnimator.animation != null) || mUpperWallpaperTarget != null || mLowerWallpaperTarget != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    Animation loadAnimation(android.view.WindowManager.LayoutParams layoutparams, int i) {
        int j = 0;
        Context context = mContext;
        if(i >= 0) {
            com.android.server.AttributeCache.Entry entry = getCachedAnimations(layoutparams);
            if(entry != null) {
                context = entry.context;
                j = entry.array.getResourceId(i, 0);
            }
        }
        Animation animation;
        if(j != 0)
            animation = AnimationUtils.loadAnimation(context, j);
        else
            animation = null;
        return animation;
    }

    public void lockNow() {
        mPolicy.lockNow();
    }

    void logWindowList(String s) {
        for(int i = mWindows.size(); i > 0;) {
            i--;
            Slog.v("WindowManager", (new StringBuilder()).append(s).append("#").append(i).append(": ").append(mWindows.get(i)).toString());
        }

    }

    void makeWindowFreezingScreenIfNeededLocked(WindowState windowstate) {
        if(!okToDisplay()) {
            windowstate.mOrientationChanging = true;
            mInnerFields.mOrientationChangeComplete = false;
            if(!mWindowsFreezingScreen) {
                mWindowsFreezingScreen = true;
                mH.removeMessages(11);
                mH.sendMessageDelayed(mH.obtainMessage(11), 2000L);
            }
        }
    }

    public void monitor() {
        synchronized(mWindowMap) { }
        synchronized(mKeyguardTokenWatcher) { }
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        tokenwatcher;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public InputChannel monitorInput(String s) {
        return mInputManager.monitorInput(s);
    }

    public void moveAppToken(int i, IBinder ibinder) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "moveAppToken()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(mAppTokens.indexOf(appwindowtoken) > i && mNextAppTransition != -1 && !mAppTransitionRunning) {
            mAnimatingAppTokens.clear();
            mAnimatingAppTokens.addAll(mAppTokens);
        }
        if(appwindowtoken == null || !mAppTokens.remove(appwindowtoken)) {
            Slog.w("WindowManager", (new StringBuilder()).append("Attempting to reorder token that doesn't exist: ").append(ibinder).append(" (").append(appwindowtoken).append(")").toString());
        } else {
            mAppTokens.add(i, appwindowtoken);
            if(mNextAppTransition == -1 && !mAppTransitionRunning) {
                mAnimatingAppTokens.clear();
                mAnimatingAppTokens.addAll(mAppTokens);
                long l = Binder.clearCallingIdentity();
                if(tmpRemoveAppWindowsLocked(appwindowtoken)) {
                    reAddAppWindowsLocked(findWindowOffsetLocked(i), appwindowtoken);
                    updateFocusedWindowLocked(3, false);
                    mLayoutNeeded = true;
                    mInputMonitor.setUpdateInputWindowsNeededLw();
                    performLayoutAndPlaceSurfacesLocked();
                    mInputMonitor.updateInputWindowsLw(false);
                }
                Binder.restoreCallingIdentity(l);
            }
        }
        return;
    }

    public void moveAppTokensToBottom(List list) {
        long l;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "moveAppTokensToBottom()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i;
        int j;
        int k;
        i = list.size();
        if(i > 0 && !mAppTransitionRunning) {
            mAnimatingAppTokens.clear();
            mAnimatingAppTokens.addAll(mAppTokens);
        }
        removeAppTokensLocked(list);
        j = 0;
        k = 0;
_L4:
        if(k >= i) goto _L2; else goto _L1
_L1:
        AppWindowToken appwindowtoken = findAppWindowToken((IBinder)list.get(k));
        if(appwindowtoken == null)
            break MISSING_BLOCK_LABEL_196;
        mAppTokens.add(j, appwindowtoken);
        if(mNextAppTransition != -1)
            appwindowtoken.sendingToBottom = true;
          goto _L3
_L2:
        if(!mAppTransitionRunning) {
            mAnimatingAppTokens.clear();
            mAnimatingAppTokens.addAll(mAppTokens);
            moveAppWindowsLocked(list, 0);
        }
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L3:
        j++;
        k++;
          goto _L4
    }

    public void moveAppTokensToTop(List list) {
        long l;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "moveAppTokensToTop()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i;
        removeAppTokensLocked(list);
        i = list.size();
        Exception exception;
        for(int j = 0; j < i; j++) {
            AppWindowToken appwindowtoken = findAppWindowToken((IBinder)list.get(j));
            if(appwindowtoken != null) {
                mAppTokens.add(appwindowtoken);
                if(mNextAppTransition != -1)
                    appwindowtoken.sendingToBottom = false;
            }
            break MISSING_BLOCK_LABEL_164;
        }

        if(!mAppTransitionRunning) {
            mAnimatingAppTokens.clear();
            mAnimatingAppTokens.addAll(mAppTokens);
            moveAppWindowsLocked(list, mAppTokens.size());
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void moveInputMethodDialogsLocked(int i) {
        ArrayList arraylist = mInputMethodDialogs;
        int j = arraylist.size();
        for(int k = 0; k < j; k++)
            i = tmpRemoveWindowLocked(i, (WindowState)arraylist.get(k));

        if(i >= 0) {
            AppWindowToken appwindowtoken = mInputMethodTarget.mAppToken;
            if(i < mWindows.size() && (WindowState)mWindows.get(i) == mInputMethodWindow)
                i++;
            for(int i1 = 0; i1 < j; i1++) {
                WindowState windowstate1 = (WindowState)arraylist.get(i1);
                windowstate1.mTargetAppToken = appwindowtoken;
                i = reAddWindowLocked(i, windowstate1);
            }

        } else {
            for(int l = 0; l < j; l++) {
                WindowState windowstate = (WindowState)arraylist.get(l);
                windowstate.mTargetAppToken = null;
                reAddWindowToListInOrderLocked(windowstate);
            }

        }
    }

    boolean moveInputMethodWindowsIfNeededLocked(boolean flag) {
        WindowState windowstate;
        WindowState windowstate1;
        int i;
        windowstate = null;
        windowstate1 = mInputMethodWindow;
        i = mInputMethodDialogs.size();
        if(windowstate1 != null || i != 0) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L8:
        return flag1;
_L2:
        int j;
        int i1;
        int j1;
        j = findDesiredInputMethodWindowIndexLocked(true);
        if(j < 0)
            break; /* Loop/switch isn't completed */
        int k = mWindows.size();
        if(j < k)
            windowstate = (WindowState)mWindows.get(j);
        WindowState windowstate2;
        if(windowstate1 != null)
            windowstate2 = windowstate1;
        else
            windowstate2 = (WindowState)mInputMethodDialogs.get(0);
        if(windowstate2.mChildWindows.size() > 0) {
            WindowState windowstate3 = (WindowState)windowstate2.mChildWindows.get(0);
            if(windowstate3.mSubLayer < 0)
                windowstate2 = windowstate3;
        }
        if(windowstate != windowstate2)
            break MISSING_BLOCK_LABEL_225;
        i1 = j + 1;
_L5:
        if(i1 < k && ((WindowState)mWindows.get(i1)).mIsImWindow) goto _L4; else goto _L3
_L3:
        j1 = i1 + 1;
_L6:
        if(j1 >= k || ((WindowState)mWindows.get(j1)).mIsImWindow) {
            if(j1 >= k) {
                flag1 = false;
                continue; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_225;
        }
        break MISSING_BLOCK_LABEL_219;
_L4:
        i1++;
          goto _L5
        j1++;
          goto _L6
        if(windowstate1 != null) {
            int l = tmpRemoveWindowLocked(j, windowstate1);
            windowstate1.mTargetAppToken = mInputMethodTarget.mAppToken;
            reAddWindowLocked(l, windowstate1);
            if(i > 0)
                moveInputMethodDialogsLocked(l + 1);
        } else {
            moveInputMethodDialogsLocked(j);
        }
_L9:
        if(flag)
            assignLayersLocked();
        flag1 = true;
        if(true) goto _L8; else goto _L7
_L7:
        if(windowstate1 != null) {
            tmpRemoveWindowLocked(0, windowstate1);
            windowstate1.mTargetAppToken = null;
            reAddWindowToListInOrderLocked(windowstate1);
            if(i > 0)
                moveInputMethodDialogsLocked(-1);
        } else {
            moveInputMethodDialogsLocked(-1);
        }
          goto _L9
        if(true) goto _L8; else goto _L10
_L10:
    }

    void notifyHardKeyboardStatusChange() {
        OnHardKeyboardStatusChangeListener onhardkeyboardstatuschangelistener;
        boolean flag;
        boolean flag1;
        synchronized(mWindowMap) {
            onhardkeyboardstatuschangelistener = mHardKeyboardStatusChangeListener;
            flag = mHardKeyboardAvailable;
            flag1 = mHardKeyboardEnabled;
        }
        if(onhardkeyboardstatuschangelistener != null)
            onhardkeyboardstatuschangelistener.onHardKeyboardStatusChange(flag, flag1);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    boolean okToDisplay() {
        boolean flag;
        if(!mDisplayFrozen && mDisplayEnabled && mPolicy.isScreenOnFully())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j) throws RemoteException {
        boolean flag;
        try {
            flag = super.onTransact(i, parcel, parcel1, j);
        }
        catch(RuntimeException runtimeexception) {
            if(!(runtimeexception instanceof SecurityException))
                Log.wtf("WindowManager", "Window Manager Crash", runtimeexception);
            throw runtimeexception;
        }
        return flag;
    }

    public IWindowSession openSession(IInputMethodClient iinputmethodclient, IInputContext iinputcontext) {
        if(iinputmethodclient == null)
            throw new IllegalArgumentException("null client");
        if(iinputcontext == null)
            throw new IllegalArgumentException("null inputContext");
        else
            return new Session(this, iinputmethodclient, iinputcontext);
    }

    public boolean outOfMemoryWindow(Session session, IWindow iwindow) {
        boolean flag;
        long l;
        flag = false;
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(session, iwindow, false);
        if(windowstate != null) goto _L2; else goto _L1
_L1:
        Binder.restoreCallingIdentity(l);
_L4:
        return flag;
_L2:
        flag = reclaimSomeSurfaceMemoryLocked(windowstate.mWinAnimator, "from-client", false);
        hashmap;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception1;
        exception1;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void overridePendingAppTransition(String s, int i, int j, IRemoteCallback iremotecallback) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mNextAppTransition != -1) {
            mNextAppTransitionType = 1;
            mNextAppTransitionPackage = s;
            mNextAppTransitionThumbnail = null;
            mNextAppTransitionEnter = i;
            mNextAppTransitionExit = j;
            scheduleAnimationCallback(mNextAppTransitionCallback);
            mNextAppTransitionCallback = iremotecallback;
        } else {
            scheduleAnimationCallback(iremotecallback);
        }
        return;
    }

    public void overridePendingAppTransitionScaleUp(int i, int j, int k, int l) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mNextAppTransition != -1) {
            mNextAppTransitionType = 2;
            mNextAppTransitionPackage = null;
            mNextAppTransitionThumbnail = null;
            mNextAppTransitionStartX = i;
            mNextAppTransitionStartY = j;
            mNextAppTransitionStartWidth = k;
            mNextAppTransitionStartHeight = l;
            scheduleAnimationCallback(mNextAppTransitionCallback);
            mNextAppTransitionCallback = null;
        }
        return;
    }

    public void overridePendingAppTransitionThumb(Bitmap bitmap, int i, int j, IRemoteCallback iremotecallback, boolean flag) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mNextAppTransition != -1) {
            int k;
            if(flag)
                k = 4;
            else
                k = 3;
            mNextAppTransitionType = k;
            mNextAppTransitionPackage = null;
            mNextAppTransitionThumbnail = bitmap;
            mNextAppTransitionDelayed = flag;
            mNextAppTransitionStartX = i;
            mNextAppTransitionStartY = j;
            scheduleAnimationCallback(mNextAppTransitionCallback);
            mNextAppTransitionCallback = iremotecallback;
        } else {
            scheduleAnimationCallback(iremotecallback);
        }
        return;
    }

    public void pauseKeyDispatching(IBinder ibinder) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "pauseKeyDispatching()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowToken windowtoken = (WindowToken)mTokenMap.get(ibinder);
        if(windowtoken != null)
            mInputMonitor.pauseDispatchingLw(windowtoken);
        return;
    }

    void pauseRotationLocked() {
        mDeferredRotationPauseCount = 1 + mDeferredRotationPauseCount;
    }

    public void performBootTimeout() {
        synchronized(mWindowMap) {
            if(mDisplayEnabled || mHeadless)
                break MISSING_BLOCK_LABEL_54;
            Slog.w("WindowManager", "***** BOOT TIMEOUT: forcing display enabled");
            mForceDisplayEnabled = true;
        }
        performEnableScreen();
          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    public void performDeferredDestroyWindow(Session session, IWindow iwindow) {
        long l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(session, iwindow, false);
        if(windowstate != null) goto _L2; else goto _L1
_L1:
        Binder.restoreCallingIdentity(l);
_L4:
        return;
_L2:
        windowstate.mWinAnimator.destroyDeferredSurfaceLocked();
        hashmap;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception1;
        exception1;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void performEnableScreen() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(!mDisplayEnabled && (mSystemBooted || mShowingBootMessages)) goto _L2; else goto _L1
        Exception exception;
        exception;
        throw exception;
_L2:
        if(mForceDisplayEnabled) goto _L4; else goto _L3
_L3:
        boolean flag;
        boolean flag1;
        boolean flag2;
        flag = false;
        flag1 = false;
        flag2 = false;
        if(!mContext.getResources().getBoolean(0x1110024) || mOnlyCore) goto _L6; else goto _L5
_L5:
        boolean flag3 = true;
_L20:
        boolean flag4;
        int i;
        int j;
        flag4 = true;
        i = mWindows.size();
        j = 0;
_L21:
        if(j >= i) goto _L8; else goto _L7
_L7:
        WindowState windowstate = (WindowState)mWindows.get(j);
        if(windowstate.mAttrs.type != 2004) goto _L10; else goto _L9
_L9:
        if(windowstate.mViewVisibility != 0 || !windowstate.mPolicyVisibility) goto _L12; else goto _L11
_L11:
        boolean flag5 = true;
          goto _L13
_L10:
        if(!windowstate.isVisibleLw() || windowstate.mObscured || windowstate.isDrawnLw()) goto _L15; else goto _L14
_L14:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L15:
        if(windowstate.isDrawnLw())
            if(windowstate.mAttrs.type == 2021)
                flag = true;
            else
            if(windowstate.mAttrs.type == 2)
                flag1 = true;
            else
            if(windowstate.mAttrs.type == 2013)
                flag2 = true;
            else
            if(windowstate.mAttrs.type == 2004)
                flag4 = true;
          goto _L16
_L8:
        if(mSystemBooted || flag) goto _L18; else goto _L17
_L17:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L22:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        mDisplayEnabled = true;
        IBinder ibinder = ServiceManager.getService("SurfaceFlinger");
        if(ibinder != null) {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("android.ui.ISurfaceComposer");
            ibinder.transact(1, parcel, null, 0);
            parcel.recycle();
        }
_L19:
        mInputMonitor.setEventDispatchingLw(mEventDispatchingEnabled);
        hashmap;
        JVM INSTR monitorexit ;
        mPolicy.enableScreenAfterBoot();
        updateRotationUnchecked(false, false);
          goto _L1
        RemoteException remoteexception;
        remoteexception;
        Slog.e("WindowManager", "Boot completed: SurfaceFlinger is dead!");
          goto _L19
_L1:
        return;
_L13:
        if(!flag5)
            flag4 = true;
        else
            flag4 = false;
          goto _L10
_L6:
        flag3 = false;
          goto _L20
_L12:
        flag5 = false;
          goto _L13
_L16:
        j++;
          goto _L21
_L18:
        if(!mSystemBooted || (flag1 || flag4) && (!flag3 || flag2)) goto _L4; else goto _L22
    }

    public void prepareAppTransition(int i, boolean flag) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "prepareAppTransition()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(!okToDisplay()) goto _L2; else goto _L1
_L1:
        if(mNextAppTransition != -1 && mNextAppTransition != 0) goto _L4; else goto _L3
_L3:
        mNextAppTransition = i;
_L6:
        mAppTransitionReady = false;
        mAppTransitionTimeout = false;
        mStartingIconInTransition = false;
        mSkipAppTransitionAnimation = false;
        mH.removeMessages(13);
        mH.sendMessageDelayed(mH.obtainMessage(13), 5000L);
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        return;
_L4:
        if(flag) goto _L6; else goto _L5
_L5:
        if(i != 4104 || mNextAppTransition != 8201) goto _L8; else goto _L7
_L7:
        mNextAppTransition = i;
          goto _L6
        Exception exception;
        exception;
        throw exception;
_L8:
        if(i != 4102) goto _L6; else goto _L9
_L9:
        if(mNextAppTransition != 8199) goto _L6; else goto _L10
_L10:
        mNextAppTransition = i;
          goto _L6
    }

    IBinder prepareDragSurface(IWindow iwindow, SurfaceSession surfacesession, int i, int j, int k, Surface surface) {
        int l;
        long l1;
        Binder binder;
        l = Binder.getCallingPid();
        l1 = Binder.clearCallingIdentity();
        binder = null;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mDragState != null) goto _L2; else goto _L1
_L1:
        Binder binder1;
        Surface surface1;
        IBinder ibinder;
        surface1 = new Surface(surfacesession, l, "drag surface", 0, j, k, -3, 4);
        surface.copyFrom(surface1);
        ibinder = iwindow.asBinder();
        binder1 = new Binder();
        mDragState = new DragState(this, binder1, surface1, 0, ibinder);
        DragState dragstate = mDragState;
        binder = new Binder();
        dragstate.mToken = binder;
        mH.removeMessages(20, ibinder);
        Message message = mH.obtainMessage(20, ibinder);
        mH.sendMessageDelayed(message, 5000L);
        binder1 = binder;
_L3:
        hashmap;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l1);
        return binder1;
_L2:
        Slog.w("WindowManager", "Drag already in progress");
        binder1 = null;
          goto _L3
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        binder1 = binder;
_L7:
        Slog.e("WindowManager", (new StringBuilder()).append("Can't allocate drag surface w=").append(j).append(" h=").append(k).toString(), outofresourcesexception);
        if(mDragState != null) {
            mDragState.reset();
            mDragState = null;
        }
          goto _L3
_L6:
        hashmap;
        JVM INSTR monitorexit ;
        Exception exception1;
        throw exception1;
        Exception exception;
        exception;
_L5:
        Binder.restoreCallingIdentity(l1);
        throw exception;
        exception;
        if(true) goto _L5; else goto _L4
_L4:
        exception1;
        binder;
          goto _L6
        outofresourcesexception;
          goto _L7
        exception1;
          goto _L6
    }

    public void rebootSafeMode() {
        ShutdownThread.rebootSafeMode(mContext, true);
    }

    final void rebuildAppWindowListLocked() {
        int i = mWindows.size();
        int j = -1;
        int k = 0;
        if(mRebuildTmp.length < i)
            mRebuildTmp = new WindowState[i + 10];
        for(int l = 0; l < i;) {
            WindowState windowstate1 = (WindowState)mWindows.get(l);
            if(windowstate1.mAppToken != null) {
                WindowState windowstate2 = (WindowState)mWindows.remove(l);
                windowstate2.mRebuilding = true;
                mRebuildTmp[k] = windowstate2;
                mWindowsChanged = true;
                i--;
                k++;
            } else {
                if(windowstate1.mAttrs.type == 2013 && j == l - 1)
                    j = l;
                l++;
            }
        }

        int i1 = j + 1;
        int j1 = i1;
        int k1 = mExitingAppTokens.size();
        for(int l1 = 0; l1 < k1; l1++)
            j1 = reAddAppWindowsLocked(j1, (WindowToken)mExitingAppTokens.get(l1));

        int i2 = mAnimatingAppTokens.size();
        for(int j2 = 0; j2 < i2; j2++)
            j1 = reAddAppWindowsLocked(j1, (WindowToken)mAnimatingAppTokens.get(j2));

        int k2 = j1 - i1;
        if(k2 != k) {
            Slog.w("WindowManager", (new StringBuilder()).append("Rebuild removed ").append(k).append(" windows but added ").append(k2).toString());
            for(int l2 = 0; l2 < k; l2++) {
                WindowState windowstate = mRebuildTmp[l2];
                if(windowstate.mRebuilding) {
                    StringWriter stringwriter = new StringWriter();
                    PrintWriter printwriter = new PrintWriter(stringwriter);
                    windowstate.dump(printwriter, "", true);
                    printwriter.flush();
                    Slog.w("WindowManager", (new StringBuilder()).append("This window was lost: ").append(windowstate).toString());
                    Slog.w("WindowManager", stringwriter.toString());
                    windowstate.mWinAnimator.destroySurfaceLocked();
                }
            }

            Slog.w("WindowManager", "Current app token list:");
            dumpAnimatingAppTokensLocked();
            Slog.w("WindowManager", "Final window list:");
            dumpWindowsLocked();
        }
    }

    boolean reclaimSomeSurfaceMemoryLocked(WindowStateAnimator windowstateanimator, String s, boolean flag) {
        Surface surface;
        boolean flag1;
        boolean flag2;
        long l;
        surface = windowstateanimator.mSurface;
        flag1 = false;
        flag2 = false;
        Object aobj[] = new Object[3];
        aobj[0] = windowstateanimator.mWin.toString();
        aobj[1] = Integer.valueOf(windowstateanimator.mSession.mPid);
        aobj[2] = s;
        EventLog.writeEvent(31000, aobj);
        if(mForceRemoves == null)
            mForceRemoves = new ArrayList();
        l = Binder.clearCallingIdentity();
        int i;
        i = mWindows.size();
        Slog.i("WindowManager", "Out of memory for surface!  Looking for leaks...");
        Exception exception;
        RemoteException remoteexception;
        boolean flag3;
        SparseIntArray sparseintarray;
        int ai[];
        int i1;
        RemoteException remoteexception1;
        boolean flag4;
        WindowStateAnimator windowstateanimator1;
        for(int j = 0; j < i; j++) {
            WindowState windowstate = (WindowState)mWindows.get(j);
            WindowStateAnimator windowstateanimator2 = windowstate.mWinAnimator;
            if(windowstateanimator2.mSurface != null)
                if(!mSessions.contains(windowstateanimator2.mSession)) {
                    Slog.w("WindowManager", (new StringBuilder()).append("LEAKED SURFACE (session doesn't exist): ").append(windowstate).append(" surface=").append(windowstateanimator2.mSurface).append(" token=").append(windowstate.mToken).append(" pid=").append(windowstate.mSession.mPid).append(" uid=").append(windowstate.mSession.mUid).toString());
                    windowstateanimator2.mSurface.destroy();
                    windowstateanimator2.mSurfaceShown = false;
                    windowstateanimator2.mSurface = null;
                    windowstate.mHasSurface = false;
                    mForceRemoves.add(windowstate);
                    j--;
                    i--;
                    flag1 = true;
                } else
                if(windowstate.mAppToken != null && windowstate.mAppToken.clientHidden) {
                    Slog.w("WindowManager", (new StringBuilder()).append("LEAKED SURFACE (app token hidden): ").append(windowstate).append(" surface=").append(windowstateanimator2.mSurface).append(" token=").append(windowstate.mAppToken).toString());
                    windowstateanimator2.mSurface.destroy();
                    windowstateanimator2.mSurfaceShown = false;
                    windowstateanimator2.mSurface = null;
                    windowstate.mHasSurface = false;
                    flag1 = true;
                }
            break MISSING_BLOCK_LABEL_654;
        }

        if(flag1)
            break MISSING_BLOCK_LABEL_548;
        Slog.w("WindowManager", "No leaked surfaces; killing applicatons!");
        sparseintarray = new SparseIntArray();
        for(int k = 0; k < i; k++) {
            windowstateanimator1 = ((WindowState)mWindows.get(k)).mWinAnimator;
            if(windowstateanimator1.mSurface != null)
                sparseintarray.append(windowstateanimator1.mSession.mPid, windowstateanimator1.mSession.mPid);
            break MISSING_BLOCK_LABEL_660;
        }

        if(sparseintarray.size() <= 0)
            break MISSING_BLOCK_LABEL_548;
        ai = new int[sparseintarray.size()];
        i1 = 0;
_L1:
        if(i1 >= ai.length)
            break MISSING_BLOCK_LABEL_523;
        ai[i1] = sparseintarray.keyAt(i1);
        i1++;
          goto _L1
        flag4 = mActivityManager.killPids(ai, "Free memory", flag);
        if(flag4)
            flag2 = true;
_L3:
        if(!flag1 && !flag2)
            break MISSING_BLOCK_LABEL_607;
        Slog.w("WindowManager", "Looks like we have reclaimed some memory, clearing surface for retry.");
        if(surface != null) {
            surface.destroy();
            windowstateanimator.mSurfaceShown = false;
            windowstateanimator.mSurface = null;
            windowstateanimator.mWin.mHasSurface = false;
        }
        try {
            windowstateanimator.mWin.mClient.dispatchGetNewSurface();
        }
        // Misplaced declaration of an exception variable
        catch(RemoteException remoteexception) { }
        Binder.restoreCallingIdentity(l);
        if(flag1 || flag2)
            flag3 = true;
        else
            flag3 = false;
        return flag3;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        remoteexception1;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void reenableKeyguard(IBinder ibinder) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0)
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        TokenWatcher tokenwatcher = mKeyguardTokenWatcher;
        tokenwatcher;
        JVM INSTR monitorenter ;
        mKeyguardTokenWatcher.release(ibinder);
        if(mKeyguardTokenWatcher.isAcquired())
            break MISSING_BLOCK_LABEL_86;
_L2:
        boolean flag = mKeyguardDisabled;
        Exception exception;
        if(!flag)
            break MISSING_BLOCK_LABEL_86;
        try {
            mKeyguardTokenWatcher.wait();
        }
        catch(InterruptedException interruptedexception) {
            Thread.currentThread().interrupt();
        }
        finally {
            tokenwatcher;
        }
        if(true) goto _L2; else goto _L1
_L1:
        JVM INSTR monitorexit ;
        throw exception;
        tokenwatcher;
        JVM INSTR monitorexit ;
    }

    public void reevaluateStatusBarVisibility() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        updateStatusBarVisibilityLocked(mPolicy.adjustSystemUiVisibilityLw(mLastStatusBarVisibility));
        performLayoutAndPlaceSurfacesLocked();
        return;
    }

    public int relayoutWindow(Session session, IWindow iwindow, int i, android.view.WindowManager.LayoutParams layoutparams, int j, int k, int l, 
            int i1, Rect rect, Rect rect1, Rect rect2, Configuration configuration, Surface surface) {
        boolean flag;
        boolean flag1;
        int j1;
        long l1;
        flag = false;
        flag1 = false;
        j1 = 0;
        if(layoutparams != null) {
            j1 = layoutparams.systemUiVisibility | layoutparams.subtreeSystemUiVisibility;
            if((0x1ff0000 & j1) != 0 && mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR") != 0)
                j1 &= 0xfe00ffff;
        }
        l1 = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(session, iwindow, false);
        if(windowstate != null) goto _L2; else goto _L1
_L1:
        int i3 = 0;
          goto _L3
_L2:
        WindowStateAnimator windowstateanimator;
        windowstateanimator = windowstate.mWinAnimator;
        if(windowstate.mRequestedWidth != j || windowstate.mRequestedHeight != k) {
            windowstate.mLayoutNeeded = true;
            windowstate.mRequestedWidth = j;
            windowstate.mRequestedHeight = k;
        }
        if(layoutparams != null && i == windowstate.mSeq)
            windowstate.mSystemUiVisibility = j1;
        if(layoutparams != null)
            mPolicy.adjustWindowParamsLw(layoutparams);
          goto _L4
_L8:
        boolean flag2;
        int k1;
        int i2;
        windowstateanimator.mSurfaceDestroyDeferred = flag2;
        k1 = 0;
        i2 = 0;
        if(layoutparams == null) goto _L6; else goto _L5
_L5:
        if(windowstate.mAttrs.type != layoutparams.type) {
            IllegalArgumentException illegalargumentexception = new IllegalArgumentException("Window type can not be changed after the window is added.");
            throw illegalargumentexception;
        }
          goto _L7
        Exception exception;
        exception;
        throw exception;
_L44:
        flag2 = false;
          goto _L8
_L7:
        android.view.WindowManager.LayoutParams layoutparams2 = windowstate.mAttrs;
        i2 = layoutparams2.flags ^ layoutparams.flags;
        layoutparams2.flags = i2;
        k1 = windowstate.mAttrs.copyFrom(layoutparams);
        if((k1 & 0x2001) != 0)
            windowstate.mLayoutNeeded = true;
_L6:
        if((0x20000000 & windowstate.mAttrs.flags) == 0) goto _L10; else goto _L9
_L9:
        boolean flag3 = true;
_L33:
        windowstate.mEnforceSizeCompat = flag3;
        if((k1 & 0x80) != 0)
            windowstateanimator.mAlpha = layoutparams.alpha;
        if((0x4000 & windowstate.mAttrs.flags) == 0) goto _L12; else goto _L11
_L11:
        boolean flag4 = true;
_L34:
        if(!flag4) goto _L14; else goto _L13
_L13:
        if(layoutparams.width == j) goto _L16; else goto _L15
_L15:
        float f = (float)layoutparams.width / (float)j;
_L35:
        windowstate.mHScale = f;
        if(layoutparams.height == k) goto _L18; else goto _L17
_L17:
        float f1 = (float)layoutparams.height / (float)k;
_L36:
        windowstate.mVScale = f1;
          goto _L19
_L37:
        boolean flag5;
        boolean flag6;
        boolean flag7;
        boolean flag8;
        boolean flag9;
        int j2;
        boolean flag10;
        boolean flag11;
        boolean flag12;
        boolean flag13;
        boolean flag14;
        Surface surface1;
        if(windowstate.mViewVisibility == l && (i2 & 8) == 0 && windowstate.mRelayoutCalled)
            flag6 = false;
        else
            flag6 = true;
        if(windowstate.mViewVisibility == l || (0x100000 & windowstate.mAttrs.flags) == 0) goto _L21; else goto _L20
_L20:
        flag7 = true;
          goto _L22
_L38:
        flag9 = flag7 | flag8;
        windowstate.mRelayoutCalled = true;
        j2 = windowstate.mViewVisibility;
        windowstate.mViewVisibility = l;
        if(l != 0 || windowstate.mAppToken != null && windowstate.mAppToken.clientHidden) goto _L24; else goto _L23
_L23:
        if(windowstate.isVisibleLw()) goto _L26; else goto _L25
_L25:
        flag = true;
_L39:
        if(windowstate.mExiting) {
            windowstateanimator.cancelExitAnimationForNextAnimationLocked();
            windowstate.mExiting = false;
        }
        if(windowstate.mDestroying) {
            windowstate.mDestroying = false;
            mDestroySurface.remove(windowstate);
        }
        if(j2 == 8)
            windowstateanimator.mEnterAnimationPending = true;
        if(flag) {
            if(windowstate.isDrawnLw() && okToDisplay())
                windowstateanimator.applyEnterAnimationLocked();
            if((0x200000 & windowstate.mAttrs.flags) != 0)
                windowstate.mTurnOnScreen = true;
            if(windowstate.mConfiguration != mCurConfiguration && (windowstate.mConfiguration == null || mCurConfiguration.diff(windowstate.mConfiguration) != 0)) {
                windowstate.mConfiguration = mCurConfiguration;
                configuration.setTo(mCurConfiguration);
            }
        }
        if((k1 & 8) == 0) goto _L28; else goto _L27
_L27:
        windowstateanimator.destroySurfaceLocked();
        flag = true;
        flag1 = true;
_L28:
        if(!windowstate.mHasSurface)
            flag1 = true;
        surface1 = windowstateanimator.createSurfaceLocked();
        if(surface1 == null) goto _L30; else goto _L29
_L29:
        surface.copyFrom(surface1);
_L40:
        if(flag)
            flag6 = true;
        if(windowstate.mAttrs.type == 2011 && mInputMethodWindow == null) {
            mInputMethodWindow = windowstate;
            flag5 = true;
        }
        if(windowstate.mAttrs.type == 1 && windowstate.mAppToken != null && windowstate.mAppToken.startingWindow != null) {
            android.view.WindowManager.LayoutParams layoutparams1 = windowstate.mAppToken.startingWindow.mAttrs;
            layoutparams1.flags = 0xffb7fffe & layoutparams1.flags | 0x480001 & windowstate.mAttrs.flags;
        }
_L41:
        if(flag6 && updateFocusedWindowLocked(3, false))
            flag5 = false;
        flag10 = false;
        if(flag5 && (moveInputMethodWindowsIfNeededLocked(false) || flag))
            flag10 = true;
        if(flag9 && (2 & adjustWallpaperWindowsLocked()) != 0)
            flag10 = true;
        mLayoutNeeded = true;
        if((i1 & 1) == 0) goto _L32; else goto _L31
_L31:
        flag11 = true;
_L42:
        windowstate.mGivenInsetsPending = flag11;
        if(flag10)
            assignLayersLocked();
        flag12 = updateOrientationFromAppTokensLocked(false);
        performLayoutAndPlaceSurfacesLocked();
        if(flag && windowstate.mIsWallpaper)
            updateWallpaperOffsetLocked(windowstate, mAppDisplayWidth, mAppDisplayHeight, false);
        if(windowstate.mAppToken != null)
            windowstate.mAppToken.updateReportedVisibilityLocked();
        rect.set(windowstate.mCompatFrame);
        rect1.set(windowstate.mContentInsets);
        rect2.set(windowstate.mVisibleInsets);
        flag13 = mInTouchMode;
        flag14 = mAnimator.mAnimating;
        if(flag14 && !mRelayoutWhileAnimating.contains(windowstate))
            mRelayoutWhileAnimating.add(windowstate);
        mInputMonitor.updateInputWindowsLw(true);
        hashmap;
        JVM INSTR monitorexit ;
        if(flag12)
            sendNewConfiguration();
        Binder.restoreCallingIdentity(l1);
        boolean flag15;
        byte byte0;
        int k2;
        byte byte1;
        int l2;
        byte byte2;
        char c;
        Exception exception1;
        if(flag13)
            flag15 = true;
        else
            flag15 = false;
        if(flag)
            byte0 = 2;
        else
            byte0 = 0;
        k2 = flag15 | byte0;
        if(flag1)
            byte1 = 4;
        else
            byte1 = 0;
        l2 = k2 | byte1;
        if(flag14)
            byte2 = 8;
        else
            byte2 = 0;
        i3 = byte2 | l2;
          goto _L3
_L10:
        flag3 = false;
          goto _L33
_L12:
        flag4 = false;
          goto _L34
_L16:
        f = 1.0F;
          goto _L35
_L18:
        f1 = 1.0F;
          goto _L36
_L14:
        windowstate.mVScale = 1.0F;
        windowstate.mHScale = 1.0F;
          goto _L19
_L46:
        flag5 = false;
          goto _L37
_L21:
        flag7 = false;
          goto _L22
_L48:
        flag8 = false;
          goto _L38
_L26:
        flag = false;
          goto _L39
_L30:
        surface.release();
          goto _L40
        exception1;
        mInputMonitor.updateInputWindowsLw(true);
        Slog.w("WindowManager", (new StringBuilder()).append("Exception thrown when creating surface for client ").append(iwindow).append(" (").append(windowstate.mAttrs.getTitle()).append(")").toString(), exception1);
        Binder.restoreCallingIdentity(l1);
        i3 = 0;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L24:
        windowstateanimator.mEnterAnimationPending = false;
        if(windowstateanimator.mSurface != null && !windowstate.mExiting) {
            flag1 = true;
            c = '\u2002';
            if(windowstate.mAttrs.type == 3)
                c = '\005';
            if(windowstate.isWinVisibleLw() && windowstateanimator.applyAnimationLocked(c, false)) {
                flag6 = true;
                windowstate.mExiting = true;
            } else
            if(windowstate.mWinAnimator.isAnimating())
                windowstate.mExiting = true;
            else
            if(windowstate == mWallpaperTarget) {
                windowstate.mExiting = true;
                windowstate.mWinAnimator.mAnimating = true;
            } else {
                if(mInputMethodWindow == windowstate)
                    mInputMethodWindow = null;
                windowstateanimator.destroySurfaceLocked();
            }
        }
        surface.release();
          goto _L41
_L32:
        flag11 = false;
          goto _L42
_L3:
        return i3;
_L4:
        if((i1 & 2) == 0) goto _L44; else goto _L43
_L43:
        flag2 = true;
          goto _L8
_L19:
        if((0x20008 & i2) == 0) goto _L46; else goto _L45
_L45:
        flag5 = true;
          goto _L37
_L22:
        if((0x100000 & i2) == 0) goto _L48; else goto _L47
_L47:
        flag8 = true;
          goto _L38
    }

    public void removeAppToken(IBinder ibinder) {
        AppWindowToken appwindowtoken;
        AppWindowToken appwindowtoken1;
        boolean flag;
        long l;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "removeAppToken()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        appwindowtoken = null;
        appwindowtoken1 = null;
        flag = false;
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowToken windowtoken = (WindowToken)mTokenMap.remove(ibinder);
        if(windowtoken == null)
            break MISSING_BLOCK_LABEL_302;
        appwindowtoken = windowtoken.appWindowToken;
        if(appwindowtoken == null)
            break MISSING_BLOCK_LABEL_302;
        flag = setTokenVisibilityLocked(appwindowtoken, null, false, -1, true);
        appwindowtoken.inPendingTransaction = false;
        mOpeningApps.remove(appwindowtoken);
        appwindowtoken.waitingToShow = false;
        if(!mClosingApps.contains(appwindowtoken)) goto _L2; else goto _L1
_L1:
        flag = true;
_L5:
        if(!flag) goto _L4; else goto _L3
_L3:
        mExitingAppTokens.add(appwindowtoken);
_L6:
        mAppTokens.remove(appwindowtoken);
        mAnimatingAppTokens.remove(appwindowtoken);
        appwindowtoken.removed = true;
        if(appwindowtoken.startingData != null)
            appwindowtoken1 = appwindowtoken;
        unsetAppFreezingScreenLocked(appwindowtoken, true, true);
        if(mFocusedApp == appwindowtoken) {
            mFocusedApp = null;
            updateFocusedWindowLocked(0, true);
            mInputMonitor.setFocusedAppLw(null);
        }
_L7:
        if(!flag && appwindowtoken != null)
            appwindowtoken.updateReportedVisibilityLocked();
        Binder.restoreCallingIdentity(l);
        if(appwindowtoken1 != null) {
            Message message = mH.obtainMessage(6, appwindowtoken1);
            mH.sendMessage(message);
        }
        return;
_L2:
        if(mNextAppTransition != -1) {
            mClosingApps.add(appwindowtoken);
            appwindowtoken.waitingToHide = true;
            flag = true;
        }
          goto _L5
_L4:
        appwindowtoken.mAppAnimator.clearAnimation();
        appwindowtoken.mAppAnimator.animating = false;
          goto _L6
        Exception exception;
        exception;
        throw exception;
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to remove non-existing app token: ").append(ibinder).toString());
          goto _L7
    }

    boolean removeFakeWindowLocked(android.view.WindowManagerPolicy.FakeWindow fakewindow) {
        boolean flag = true;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mFakeWindows.remove(fakewindow))
            mInputMonitor.updateInputWindowsLw(true);
        else
            flag = false;
        return flag;
    }

    public void removeWindow(Session session, IWindow iwindow) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(session, iwindow, false);
        if(windowstate != null)
            removeWindowLocked(session, windowstate);
        return;
    }

    public void removeWindowChangeListener(WindowChangeListener windowchangelistener) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mWindowChangeListeners.remove(windowchangelistener);
        return;
    }

    public void removeWindowLocked(Session session, WindowState windowstate) {
        long l;
        boolean flag;
        l = Binder.clearCallingIdentity();
        windowstate.disposeInputChannel();
        flag = false;
        if(!windowstate.mHasSurface || !okToDisplay()) goto _L2; else goto _L1
_L1:
        flag = windowstate.isWinVisibleLw();
        if(flag) {
            char c = '\u2002';
            if(windowstate.mAttrs.type == 3)
                c = '\005';
            if(windowstate.mWinAnimator.applyAnimationLocked(c, false))
                windowstate.mExiting = true;
        }
        if(!windowstate.mExiting && !windowstate.mWinAnimator.isAnimating()) goto _L2; else goto _L3
_L3:
        windowstate.mExiting = true;
        windowstate.mRemoveOnExit = true;
        mLayoutNeeded = true;
        updateFocusedWindowLocked(3, false);
        performLayoutAndPlaceSurfacesLocked();
        mInputMonitor.updateInputWindowsLw(false);
        if(windowstate.mAppToken != null)
            windowstate.mAppToken.updateReportedVisibilityLocked();
        Binder.restoreCallingIdentity(l);
_L5:
        return;
_L2:
        removeWindowInnerLocked(session, windowstate);
        if(flag && computeForcedAppOrientationLocked() != mForcedAppOrientation && updateOrientationFromAppTokensLocked(false))
            mH.sendEmptyMessage(18);
        updateFocusedWindowLocked(0, true);
        Binder.restoreCallingIdentity(l);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void removeWindowToken(IBinder ibinder) {
        long l;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "removeWindowToken()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowToken windowtoken;
        boolean flag;
        windowtoken = (WindowToken)mTokenMap.remove(ibinder);
        if(windowtoken == null)
            break MISSING_BLOCK_LABEL_235;
        flag = false;
        if(windowtoken.hidden) goto _L2; else goto _L1
_L1:
        int i;
        boolean flag1;
        windowtoken.hidden = true;
        i = windowtoken.windows.size();
        flag1 = false;
        Exception exception;
        for(int j = 0; j < i; j++) {
            WindowState windowstate = (WindowState)windowtoken.windows.get(j);
            if(windowstate.mWinAnimator.isAnimating())
                flag = true;
            if(windowstate.isVisibleNow()) {
                windowstate.mWinAnimator.applyAnimationLocked(8194, false);
                flag1 = true;
            }
            break MISSING_BLOCK_LABEL_264;
        }

        if(flag1) {
            mLayoutNeeded = true;
            performLayoutAndPlaceSurfacesLocked();
            updateFocusedWindowLocked(0, false);
        }
        if(!flag) goto _L4; else goto _L3
_L3:
        mExitingTokens.add(windowtoken);
_L2:
        mInputMonitor.updateInputWindowsLw(true);
_L5:
        Binder.restoreCallingIdentity(l);
        return;
_L4:
        if(windowtoken.windowType == 2013)
            mWallpaperTokens.remove(windowtoken);
          goto _L2
        exception;
        throw exception;
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to remove non-existing token: ").append(ibinder).toString());
          goto _L5
    }

    void requestTraversalLocked() {
        if(!mTraversalScheduled) {
            mTraversalScheduled = true;
            mH.sendEmptyMessage(4);
        }
    }

    public void resumeKeyDispatching(IBinder ibinder) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "resumeKeyDispatching()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowToken windowtoken = (WindowToken)mTokenMap.get(ibinder);
        if(windowtoken != null)
            mInputMonitor.resumeDispatchingLw(windowtoken);
        return;
    }

    void resumeRotationLocked() {
        if(mDeferredRotationPauseCount > 0) {
            mDeferredRotationPauseCount = -1 + mDeferredRotationPauseCount;
            if(mDeferredRotationPauseCount == 0 && updateRotationUncheckedLocked(false))
                mH.sendEmptyMessage(18);
        }
    }

    public void saveANRStateLocked(AppWindowToken appwindowtoken, WindowState windowstate) {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter);
        printwriter.println((new StringBuilder()).append("  ANR time: ").append(DateFormat.getInstance().format(new Date())).toString());
        if(appwindowtoken != null)
            printwriter.println((new StringBuilder()).append("  Application at fault: ").append(((WindowToken) (appwindowtoken)).stringName).toString());
        if(windowstate != null)
            printwriter.println((new StringBuilder()).append("  Window at fault: ").append(windowstate.mAttrs.getTitle()).toString());
        printwriter.println();
        dumpWindowsNoHeaderLocked(printwriter, true, null);
        printwriter.close();
        mLastANRState = stringwriter.toString();
    }

    public void saveLastInputMethodWindowForTransition() {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mInputMethodWindow != null)
            mPolicy.setLastInputMethodWindowLw(mInputMethodWindow, mInputMethodTarget);
        return;
    }

    void scheduleAnimationLocked() {
        if(!mAnimationScheduled) {
            mChoreographer.postCallback(1, mAnimationRunnable, null);
            mAnimationScheduled = true;
        }
    }

    public Bitmap screenshotApplications(IBinder ibinder, int i, int j) {
        int k;
        Rect rect;
        if(!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "screenshotApplications()"))
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        k = 0;
        rect = new Rect();
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int i1;
        int j1;
        Bitmap bitmap;
        int i2;
        float f2;
        int l2;
        int i3;
        long l = Binder.clearCallingIdentity();
        i1 = mCurDisplayWidth;
        j1 = mCurDisplayHeight;
        int k1 = 10000 + (1000 + 10000 * mPolicy.windowTypeToLayerLw(2));
        Exception exception;
        boolean flag;
        boolean flag1;
        int l1;
        int j2;
        int k2;
        float f;
        float f1;
        Bitmap bitmap1;
        Matrix matrix;
        Canvas canvas;
        WindowState windowstate;
        int k3;
        Rect rect1;
        Rect rect2;
        if(mInputMethodTarget != null && mInputMethodTarget.mAppToken != null && mInputMethodTarget.mAppToken.appToken != null && mInputMethodTarget.mAppToken.appToken.asBinder() == ibinder)
            flag = true;
        else
            flag = false;
        flag1 = false;
        l1 = -1 + mWindows.size();
        if(l1 >= 0) {
            windowstate = (WindowState)mWindows.get(l1);
            if(windowstate.mHasSurface && windowstate.mLayer < k1 && (flag1 || ibinder == null || windowstate.mIsImWindow && flag || windowstate.mAppToken != null && ((WindowToken) (windowstate.mAppToken)).token == ibinder)) {
                if(!windowstate.mIsImWindow && !windowstate.isFullscreen(i1, j1))
                    flag1 = true;
                else
                    flag1 = false;
                k3 = windowstate.mWinAnimator.mSurfaceLayer;
                if(k < k3)
                    k = windowstate.mWinAnimator.mSurfaceLayer;
                if(!windowstate.mIsWallpaper) {
                    rect1 = windowstate.mFrame;
                    rect2 = windowstate.mContentInsets;
                    rect.union(rect1.left + rect2.left, rect1.top + rect2.top, rect1.right - rect2.right, rect1.bottom - rect2.bottom);
                }
            }
            l1--;
            break MISSING_BLOCK_LABEL_153;
        }
        break MISSING_BLOCK_LABEL_373;
        exception;
        throw exception;
        Binder.restoreCallingIdentity(l);
        rect.intersect(0, 0, i1, j1);
        if(!rect.isEmpty() && k != 0) goto _L2; else goto _L1
_L1:
        bitmap = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        i2 = mDisplay.getRotation();
        j2 = rect.width();
        k2 = rect.height();
        f = (float)i / (float)j2;
        f1 = (float)j / (float)k2;
        if(i1 > j1) goto _L5; else goto _L4
_L4:
        f2 = f;
        if(f1 > f2 && (int)(f1 * (float)j2) == i)
            f2 = f1;
          goto _L6
_L7:
        bitmap1 = Surface.screenshot(l2, i3, 0, k);
        hashmap;
        JVM INSTR monitorexit ;
        if(bitmap1 == null) {
            Slog.w("WindowManager", (new StringBuilder()).append("Failure taking screenshot for (").append(l2).append("x").append(i3).append(") to layer ").append(k).toString());
            bitmap = null;
        } else {
            bitmap = Bitmap.createBitmap(i, j, bitmap1.getConfig());
            matrix = new Matrix();
            ScreenRotationAnimation.createRotationMatrix(i2, l2, i3, matrix);
            matrix.postTranslate(-FloatMath.ceil(f2 * (float)rect.left), -FloatMath.ceil(f2 * (float)rect.top));
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap1, matrix, null);
            canvas.setBitmap(null);
            bitmap1.recycle();
        }
          goto _L3
_L5:
        f2 = f1;
        if(f > f2 && (int)(f * (float)k2) == j)
            f2 = f;
          goto _L6
_L10:
        i2 = 1;
          goto _L7
_L3:
        return bitmap;
_L6:
        l2 = (int)(f2 * (float)i1);
        i3 = (int)(f2 * (float)j1);
        if(i2 != 1 && i2 != 3) goto _L7; else goto _L8
_L8:
        int j3 = l2;
        l2 = i3;
        i3 = j3;
        if(i2 != 1) goto _L10; else goto _L9
_L9:
        i2 = 3;
          goto _L7
    }

    void sendNewConfiguration() {
        mActivityManager.updateConfiguration(null);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Bundle sendWindowWallpaperCommandLocked(WindowState windowstate, String s, int i, int j, int k, Bundle bundle, boolean flag) {
        if(windowstate != mWallpaperTarget && windowstate != mLowerWallpaperTarget && windowstate != mUpperWallpaperTarget) goto _L2; else goto _L1
_L1:
        boolean flag1;
        int l;
        flag1 = flag;
        l = mWallpaperTokens.size();
_L8:
        if(l <= 0) goto _L4; else goto _L3
_L3:
        WindowToken windowtoken;
        int i1;
        l--;
        windowtoken = (WindowToken)mWallpaperTokens.get(l);
        i1 = windowtoken.windows.size();
_L6:
        WindowState windowstate1;
        if(i1 <= 0)
            continue; /* Loop/switch isn't completed */
        i1--;
        windowstate1 = (WindowState)windowtoken.windows.get(i1);
        windowstate1.mClient.dispatchWallpaperCommand(s, i, j, k, bundle, flag);
        flag = false;
        break; /* Loop/switch isn't completed */
_L4:
        if(!flag1);
_L2:
        return null;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L6; else goto _L5
_L5:
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void setAnimationScale(int i, float f) {
        float f1;
        if(!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()"))
            throw new SecurityException("Requires SET_ANIMATION_SCALE permission");
        if(f < 0.0F)
            f = 0.0F;
        else
        if(f > 20F)
            f = 20F;
        f1 = Math.abs(f);
        i;
        JVM INSTR tableswitch 0 2: default 64
    //                   0 92
    //                   1 103
    //                   2 114;
           goto _L1 _L2 _L3 _L4
_L1:
        mH.obtainMessage(14).sendToTarget();
        return;
_L2:
        mWindowAnimationScale = fixScale(f1);
        continue; /* Loop/switch isn't completed */
_L3:
        mTransitionAnimationScale = fixScale(f1);
        continue; /* Loop/switch isn't completed */
_L4:
        mAnimatorDurationScale = fixScale(f1);
        if(true) goto _L1; else goto _L5
_L5:
    }

    public void setAnimationScales(float af[]) {
        if(!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()"))
            throw new SecurityException("Requires SET_ANIMATION_SCALE permission");
        if(af != null) {
            if(af.length >= 1)
                mWindowAnimationScale = fixScale(af[0]);
            if(af.length >= 2)
                mTransitionAnimationScale = fixScale(af[1]);
            if(af.length >= 3)
                mAnimatorDurationScale = fixScale(af[2]);
        }
        mH.obtainMessage(14).sendToTarget();
    }

    public void setAppGroupId(IBinder ibinder, int i) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppGroupId()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken == null)
            Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set group id of non-existing app token: ").append(ibinder).toString());
        else
            appwindowtoken.groupId = i;
        return;
    }

    public void setAppOrientation(IApplicationToken iapplicationtoken, int i) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppOrientation()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(iapplicationtoken.asBinder());
        if(appwindowtoken == null)
            Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set orientation of non-existing app token: ").append(iapplicationtoken).toString());
        else
            appwindowtoken.requestedOrientation = i;
        return;
    }

    public void setAppStartingWindow(IBinder ibinder, String s, int i, CompatibilityInfo compatibilityinfo, CharSequence charsequence, int j, int k, 
            int l, IBinder ibinder1, boolean flag) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppStartingWindow()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken != null) goto _L2; else goto _L1
_L1:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set icon of non-existing app token: ").append(ibinder).toString());
          goto _L3
_L2:
        if(okToDisplay()) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L4:
        if(appwindowtoken.startingData == null) goto _L6; else goto _L5
_L5:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L6:
        if(ibinder1 == null) goto _L8; else goto _L7
_L7:
        AppWindowToken appwindowtoken1 = findAppWindowToken(ibinder1);
        if(appwindowtoken1 == null) goto _L8; else goto _L9
_L9:
        WindowState windowstate = appwindowtoken1.startingWindow;
        if(windowstate == null) goto _L11; else goto _L10
_L10:
        if(mStartingIconInTransition)
            mSkipAppTransitionAnimation = true;
        long l1 = Binder.clearCallingIdentity();
        appwindowtoken.startingData = appwindowtoken1.startingData;
        appwindowtoken.startingView = appwindowtoken1.startingView;
        appwindowtoken.startingDisplayed = appwindowtoken1.startingDisplayed;
        appwindowtoken.startingWindow = windowstate;
        appwindowtoken.reportedVisible = appwindowtoken1.reportedVisible;
        appwindowtoken1.startingData = null;
        appwindowtoken1.startingView = null;
        appwindowtoken1.startingWindow = null;
        appwindowtoken1.startingMoved = true;
        windowstate.mToken = appwindowtoken;
        windowstate.mRootToken = appwindowtoken;
        windowstate.mAppToken = appwindowtoken;
        mWindows.remove(windowstate);
        mWindowsChanged = true;
        ((WindowToken) (appwindowtoken1)).windows.remove(windowstate);
        appwindowtoken1.allAppWindows.remove(windowstate);
        addWindowToListInOrderLocked(windowstate, true);
        if(appwindowtoken1.allDrawn)
            appwindowtoken.allDrawn = true;
        if(appwindowtoken1.firstWindowDrawn)
            appwindowtoken.firstWindowDrawn = true;
        if(!((WindowToken) (appwindowtoken1)).hidden) {
            appwindowtoken.hidden = false;
            appwindowtoken.hiddenRequested = false;
            appwindowtoken.willBeHidden = false;
        }
        if(appwindowtoken.clientHidden != appwindowtoken1.clientHidden) {
            appwindowtoken.clientHidden = appwindowtoken1.clientHidden;
            appwindowtoken.sendAppVisibilityToClients();
        }
        AppWindowAnimator appwindowanimator2 = appwindowtoken1.mAppAnimator;
        AppWindowAnimator appwindowanimator3 = appwindowtoken.mAppAnimator;
        if(appwindowanimator2.animation != null) {
            appwindowanimator3.animation = appwindowanimator2.animation;
            appwindowanimator3.animating = appwindowanimator2.animating;
            appwindowanimator3.animLayerAdjustment = appwindowanimator2.animLayerAdjustment;
            appwindowanimator2.animation = null;
            appwindowanimator2.animLayerAdjustment = 0;
            appwindowanimator3.updateLayers();
            appwindowanimator2.updateLayers();
        }
        updateFocusedWindowLocked(3, true);
        mLayoutNeeded = true;
        performLayoutAndPlaceSurfacesLocked();
        Binder.restoreCallingIdentity(l1);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L11:
        if(appwindowtoken1.startingData == null) goto _L13; else goto _L12
_L12:
        appwindowtoken.startingData = appwindowtoken1.startingData;
        appwindowtoken1.startingData = null;
        appwindowtoken1.startingMoved = true;
        Message message1 = mH.obtainMessage(5, appwindowtoken);
        mH.sendMessageAtFrontOfQueue(message1);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L13:
        AppWindowAnimator appwindowanimator = appwindowtoken1.mAppAnimator;
        AppWindowAnimator appwindowanimator1 = appwindowtoken.mAppAnimator;
        if(appwindowanimator.thumbnail != null) {
            if(appwindowanimator1.thumbnail != null)
                appwindowanimator1.thumbnail.destroy();
            appwindowanimator1.thumbnail = appwindowanimator.thumbnail;
            appwindowanimator1.thumbnailX = appwindowanimator.thumbnailX;
            appwindowanimator1.thumbnailY = appwindowanimator.thumbnailY;
            appwindowanimator1.thumbnailLayer = appwindowanimator.thumbnailLayer;
            appwindowanimator1.thumbnailAnimation = appwindowanimator.thumbnailAnimation;
            appwindowanimator.thumbnail = null;
        }
_L8:
        if(flag) goto _L15; else goto _L14
_L14:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L15:
        if(i == 0) goto _L17; else goto _L16
_L16:
        com.android.server.AttributeCache.Entry entry = AttributeCache.instance().get(s, i, com.android.internal.R.styleable.Window);
        if(entry != null) goto _L19; else goto _L18
_L18:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L19:
        if(!entry.array.getBoolean(5, false)) goto _L21; else goto _L20
_L20:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L21:
        if(!entry.array.getBoolean(4, false)) goto _L23; else goto _L22
_L22:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L23:
        if(!entry.array.getBoolean(14, false)) goto _L17; else goto _L24
_L24:
        if(mWallpaperTarget != null) goto _L26; else goto _L25
_L25:
        l |= 0x100000;
_L17:
        mStartingIconInTransition = true;
        appwindowtoken.startingData = new StartingData(s, i, compatibilityinfo, charsequence, j, k, l);
        Message message = mH.obtainMessage(5, appwindowtoken);
        mH.sendMessageAtFrontOfQueue(message);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L26:
        hashmap;
        JVM INSTR monitorexit ;
_L3:
    }

    public void setAppVisibility(IBinder ibinder, boolean flag) {
        boolean flag1;
        flag1 = true;
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppVisibility()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken != null) goto _L2; else goto _L1
_L1:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set visibility of non-existing app token: ").append(ibinder).toString());
          goto _L3
_L2:
        if(!okToDisplay() || mNextAppTransition == -1) goto _L5; else goto _L4
_L4:
        if(appwindowtoken.hiddenRequested == flag) goto _L6; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L6:
        if(flag)
            flag1 = false;
        appwindowtoken.hiddenRequested = flag1;
        if(!appwindowtoken.startingDisplayed)
            appwindowtoken.mAppAnimator.setDummyAnimation();
        mOpeningApps.remove(appwindowtoken);
        mClosingApps.remove(appwindowtoken);
        appwindowtoken.waitingToHide = false;
        appwindowtoken.waitingToShow = false;
        appwindowtoken.inPendingTransaction = true;
        if(!flag) goto _L8; else goto _L7
_L7:
        mOpeningApps.add(appwindowtoken);
        appwindowtoken.startingMoved = false;
        if(((WindowToken) (appwindowtoken)).hidden) {
            appwindowtoken.allDrawn = false;
            appwindowtoken.waitingToShow = true;
            if(appwindowtoken.clientHidden) {
                appwindowtoken.clientHidden = false;
                appwindowtoken.sendAppVisibilityToClients();
            }
        }
_L9:
        hashmap;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
_L8:
        mClosingApps.add(appwindowtoken);
        if(!((WindowToken) (appwindowtoken)).hidden)
            appwindowtoken.waitingToHide = true;
        if(true) goto _L9; else goto _L3
_L5:
        long l = Binder.clearCallingIdentity();
        setTokenVisibilityLocked(appwindowtoken, null, flag, -1, true);
        appwindowtoken.updateReportedVisibilityLocked();
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
_L3:
    }

    public void setAppWillBeHidden(IBinder ibinder) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppWillBeHidden()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken == null)
            Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set will be hidden of non-existing app token: ").append(ibinder).toString());
        else
            appwindowtoken.willBeHidden = true;
        return;
    }

    public void setEventDispatching(boolean flag) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setEventDispatching()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mEventDispatchingEnabled = flag;
        if(mDisplayEnabled)
            mInputMonitor.setEventDispatchingLw(flag);
        sendScreenStatusToClientsLocked();
        return;
    }

    public void setFocusedApp(IBinder ibinder, boolean flag) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setFocusedApp()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(ibinder != null) goto _L2; else goto _L1
_L1:
        Exception exception;
        AppWindowToken appwindowtoken;
        boolean flag1;
        long l;
        if(mFocusedApp != null)
            flag1 = true;
        else
            flag1 = false;
        mFocusedApp = null;
        if(flag1)
            mInputMonitor.setFocusedAppLw(null);
        if(flag && flag1) {
            l = Binder.clearCallingIdentity();
            updateFocusedWindowLocked(0, true);
            Binder.restoreCallingIdentity(l);
        }
          goto _L3
_L2:
        appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken != null) goto _L5; else goto _L4
_L4:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to set focus to non-existing app token: ").append(ibinder).toString());
          goto _L3
        exception;
        throw exception;
_L5:
        if(mFocusedApp == appwindowtoken) goto _L7; else goto _L6
_L6:
        flag1 = true;
_L8:
        mFocusedApp = appwindowtoken;
        if(flag1)
            mInputMonitor.setFocusedAppLw(appwindowtoken);
        continue; /* Loop/switch isn't completed */
_L7:
        flag1 = false;
        if(true) goto _L8; else goto _L3
_L3:
        return;
        if(true) goto _L10; else goto _L9
_L10:
        break MISSING_BLOCK_LABEL_63;
_L9:
    }

    public void setForcedDisplaySize(int i, int j) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        int k;
        int l;
        if(mInitialDisplayWidth < mInitialDisplayHeight) {
            if(j < mInitialDisplayWidth)
                k = j;
            else
                k = mInitialDisplayWidth;
            if(i < mInitialDisplayHeight)
                l = i;
            else
                l = mInitialDisplayHeight;
        } else {
            if(i < mInitialDisplayWidth)
                k = i;
            else
                k = mInitialDisplayWidth;
            if(j < mInitialDisplayHeight)
                l = j;
            else
                l = mInitialDisplayHeight;
        }
        setForcedDisplaySizeLocked(k, l);
        android.provider.Settings.Secure.putString(mContext.getContentResolver(), "display_size_forced", (new StringBuilder()).append(k).append(",").append(l).toString());
        return;
    }

    public void setHardKeyboardEnabled(boolean flag) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mHardKeyboardEnabled != flag) {
            mHardKeyboardEnabled = flag;
            mH.sendEmptyMessage(18);
        }
        return;
    }

    void setHoldScreenLocked(boolean flag) {
        if(flag != mHoldingScreenWakeLock.isHeld())
            if(flag) {
                mPolicy.screenOnStartedLw();
                mHoldingScreenWakeLock.acquire();
            } else {
                mPolicy.screenOnStoppedLw();
                mHoldingScreenWakeLock.release();
            }
    }

    public void setInTouchMode(boolean flag) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mInTouchMode = flag;
        return;
    }

    public void setInputFilter(InputFilter inputfilter) {
        mInputManager.setInputFilter(inputfilter);
    }

    void setInputMethodAnimLayerAdjustment(int i) {
        mInputMethodAnimLayerAdjustment = i;
        WindowState windowstate = mInputMethodWindow;
        if(windowstate != null) {
            windowstate.mWinAnimator.mAnimLayer = i + windowstate.mLayer;
            for(int k = windowstate.mChildWindows.size(); k > 0;) {
                k--;
                WindowState windowstate2 = (WindowState)windowstate.mChildWindows.get(k);
                windowstate2.mWinAnimator.mAnimLayer = i + windowstate2.mLayer;
            }

        }
        for(int j = mInputMethodDialogs.size(); j > 0;) {
            j--;
            WindowState windowstate1 = (WindowState)mInputMethodDialogs.get(j);
            windowstate1.mWinAnimator.mAnimLayer = i + windowstate1.mLayer;
        }

    }

    void setInsetsWindow(Session session, IWindow iwindow, int i, Rect rect, Rect rect1, Region region) {
        long l = Binder.clearCallingIdentity();
        synchronized(mWindowMap) {
            WindowState windowstate = windowForClientLocked(session, iwindow, false);
            if(windowstate != null) {
                windowstate.mGivenInsetsPending = false;
                windowstate.mGivenContentInsets.set(rect);
                windowstate.mGivenVisibleInsets.set(rect1);
                windowstate.mGivenTouchableRegion.set(region);
                windowstate.mTouchableInsets = i;
                if(windowstate.mGlobalScale != 1.0F) {
                    windowstate.mGivenContentInsets.scale(windowstate.mGlobalScale);
                    windowstate.mGivenVisibleInsets.scale(windowstate.mGlobalScale);
                    windowstate.mGivenTouchableRegion.scale(windowstate.mGlobalScale);
                }
                mLayoutNeeded = true;
                performLayoutAndPlaceSurfacesLocked();
            }
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception1;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void setNewConfiguration(Configuration configuration) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setNewConfiguration()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mCurConfiguration = new Configuration(configuration);
        mWaitingForConfig = false;
        performLayoutAndPlaceSurfacesLocked();
        return;
    }

    public void setOnHardKeyboardStatusChangeListener(OnHardKeyboardStatusChangeListener onhardkeyboardstatuschangelistener) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mHardKeyboardStatusChangeListener = onhardkeyboardstatuschangelistener;
        return;
    }

    public void setStrictModeVisualIndicatorPreference(String s) {
        SystemProperties.set("persist.sys.strictmode.visual", s);
    }

    boolean setTokenVisibilityLocked(AppWindowToken appwindowtoken, android.view.WindowManager.LayoutParams layoutparams, boolean flag, int i, boolean flag1) {
        boolean flag2;
        flag2 = false;
        if(appwindowtoken.clientHidden == flag) {
            int k;
            int l;
            boolean flag6;
            if(!flag)
                flag6 = true;
            else
                flag6 = false;
            appwindowtoken.clientHidden = flag6;
            appwindowtoken.sendAppVisibilityToClients();
        }
        appwindowtoken.willBeHidden = false;
        if(((WindowToken) (appwindowtoken)).hidden != flag) goto _L2; else goto _L1
_L1:
        boolean flag3 = false;
        boolean flag4 = false;
        if(i != -1) {
            if(appwindowtoken.mAppAnimator.animation == AppWindowAnimator.sDummyAnimation)
                appwindowtoken.mAppAnimator.animation = null;
            if(applyAnimationLocked(appwindowtoken, layoutparams, i, flag)) {
                flag4 = true;
                flag2 = flag4;
            }
            flag3 = true;
        }
        k = appwindowtoken.allAppWindows.size();
        l = 0;
        while(l < k)  {
            WindowState windowstate1 = (WindowState)appwindowtoken.allAppWindows.get(l);
            if(windowstate1 != appwindowtoken.startingWindow)
                if(flag) {
                    if(!windowstate1.isVisibleNow()) {
                        if(!flag4)
                            windowstate1.mWinAnimator.applyAnimationLocked(4097, true);
                        flag3 = true;
                    }
                } else
                if(windowstate1.isVisibleNow()) {
                    if(!flag4)
                        windowstate1.mWinAnimator.applyAnimationLocked(8194, false);
                    flag3 = true;
                }
            l++;
        }
        int j;
        boolean flag5;
        if(!flag)
            flag5 = true;
        else
            flag5 = false;
        appwindowtoken.hiddenRequested = flag5;
        appwindowtoken.hidden = flag5;
        if(flag) goto _L4; else goto _L3
_L3:
        unsetAppFreezingScreenLocked(appwindowtoken, true, true);
_L6:
        if(flag3) {
            mLayoutNeeded = true;
            mInputMonitor.setUpdateInputWindowsNeededLw();
            if(flag1) {
                updateFocusedWindowLocked(3, false);
                performLayoutAndPlaceSurfacesLocked();
            }
            mInputMonitor.updateInputWindowsLw(false);
        }
_L2:
        if(appwindowtoken.mAppAnimator.animation != null)
            flag2 = true;
        for(j = -1 + appwindowtoken.allAppWindows.size(); j >= 0 && !flag2; j--)
            if(((WindowState)appwindowtoken.allAppWindows.get(j)).mWinAnimator.isWindowAnimating())
                flag2 = true;

        break; /* Loop/switch isn't completed */
_L4:
        WindowState windowstate = appwindowtoken.startingWindow;
        if(windowstate != null && !windowstate.isDrawnLw()) {
            windowstate.mPolicyVisibility = false;
            windowstate.mPolicyVisibilityAfterAnim = false;
        }
        if(true) goto _L6; else goto _L5
_L5:
        return flag2;
    }

    void setTransparentRegionHint(WindowStateAnimator windowstateanimator, Region region) {
        mH.sendMessage(mH.obtainMessage(0x186a1, new Pair(windowstateanimator, region)));
    }

    void setTransparentRegionWindow(Session session, IWindow iwindow, Region region) {
        long l = Binder.clearCallingIdentity();
        synchronized(mWindowMap) {
            WindowState windowstate = windowForClientLocked(session, iwindow, false);
            if(windowstate != null && windowstate.mHasSurface)
                setTransparentRegionHint(windowstate.mWinAnimator, region);
        }
        Binder.restoreCallingIdentity(l);
        return;
        exception1;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    void setWallpaperAnimLayerAdjustmentLocked(int i) {
        mWallpaperAnimLayerAdjustment = i;
        for(int j = mWallpaperTokens.size(); j > 0;) {
            j--;
            WindowToken windowtoken = (WindowToken)mWallpaperTokens.get(j);
            int k = windowtoken.windows.size();
            while(k > 0)  {
                k--;
                WindowState windowstate = (WindowState)windowtoken.windows.get(k);
                windowstate.mWinAnimator.mAnimLayer = i + windowstate.mLayer;
            }
        }

    }

    void setWallpaperOffset(WindowStateAnimator windowstateanimator, int i, int j) {
        mH.sendMessage(mH.obtainMessage(0x186a2, i, j, windowstateanimator));
    }

    public void setWindowWallpaperPositionLocked(WindowState windowstate, float f, float f1, float f2, float f3) {
        if(windowstate.mWallpaperX != f || windowstate.mWallpaperY != f1) {
            windowstate.mWallpaperX = f;
            windowstate.mWallpaperY = f1;
            windowstate.mWallpaperXStep = f2;
            windowstate.mWallpaperYStep = f3;
            updateWallpaperOffsetLocked(windowstate, true);
        }
    }

    public void showBootMessage(CharSequence charsequence, boolean flag) {
        boolean flag1 = false;
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(!mAllowBootMessages)
            break MISSING_BLOCK_LABEL_91;
        if(mShowingBootMessages)
            break MISSING_BLOCK_LABEL_51;
        if(!flag)
            break MISSING_BLOCK_LABEL_91;
        break MISSING_BLOCK_LABEL_49;
        Exception exception;
        exception;
        throw exception;
        flag1 = true;
        if(!mSystemBooted)
            break MISSING_BLOCK_LABEL_64;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_91;
        mShowingBootMessages = true;
        mPolicy.showBootMessage(charsequence, flag);
        hashmap;
        JVM INSTR monitorexit ;
        if(flag1)
            performEnableScreen();
    }

    public void showStrictModeViolation(boolean flag) {
        if(!mHeadless) {
            H h = mH;
            H h1 = mH;
            int i;
            if(flag)
                i = 1;
            else
                i = 0;
            h.sendMessage(h1.obtainMessage(26, i, 0));
        }
    }

    public void shutdown() {
        ShutdownThread.shutdown(mContext, true);
    }

    public void startAppFreezingScreen(IBinder ibinder, int i) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppFreezingScreen()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(i != 0 || !okToDisplay()) goto _L2; else goto _L1
_L2:
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken != null && appwindowtoken.appToken != null) goto _L4; else goto _L3
_L3:
        Slog.w("WindowManager", (new StringBuilder()).append("Attempted to freeze screen with non-existing app token: ").append(appwindowtoken).toString());
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L4:
        long l = Binder.clearCallingIdentity();
        startAppFreezingScreenLocked(appwindowtoken, i);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
_L1:
    }

    public void startAppFreezingScreenLocked(AppWindowToken appwindowtoken, int i) {
        if(!appwindowtoken.hiddenRequested) {
            if(!appwindowtoken.mAppAnimator.freezingScreen) {
                appwindowtoken.mAppAnimator.freezingScreen = true;
                mAppsFreezingScreen = 1 + mAppsFreezingScreen;
                if(mAppsFreezingScreen == 1) {
                    startFreezingDisplayLocked(false);
                    mH.removeMessages(17);
                    mH.sendMessageDelayed(mH.obtainMessage(17), 5000L);
                }
            }
            int j = appwindowtoken.allAppWindows.size();
            for(int k = 0; k < j; k++)
                ((WindowState)appwindowtoken.allAppWindows.get(k)).mAppFreezing = true;

        }
    }

    public boolean startViewServer(int i) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
_L2:
        do
            return flag;
        while(isSystemSecure() || !checkCallingPermission("android.permission.DUMP", "startViewServer") || i < 1024);
        if(mViewServer != null) {
            if(mViewServer.isRunning())
                continue; /* Loop/switch isn't completed */
            boolean flag2;
            try {
                flag2 = mViewServer.start();
            }
            catch(IOException ioexception1) {
                Slog.w("WindowManager", "View server did not start");
                continue; /* Loop/switch isn't completed */
            }
            flag = flag2;
            continue; /* Loop/switch isn't completed */
        }
        boolean flag1;
        mViewServer = new ViewServer(this, i);
        flag1 = mViewServer.start();
        flag = flag1;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.w("WindowManager", "View server did not start");
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void statusBarVisibilityChanged(int i) {
        if(mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR") != 0)
            throw new SecurityException("Caller does not hold permission android.permission.STATUS_BAR");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mLastStatusBarVisibility = i;
        updateStatusBarVisibilityLocked(mPolicy.adjustSystemUiVisibilityLw(i));
        return;
    }

    public void stopAppFreezingScreen(IBinder ibinder, boolean flag) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setAppFreezingScreen()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        AppWindowToken appwindowtoken = findAppWindowToken(ibinder);
        if(appwindowtoken != null && appwindowtoken.appToken != null) {
            long l = Binder.clearCallingIdentity();
            unsetAppFreezingScreenLocked(appwindowtoken, true, flag);
            Binder.restoreCallingIdentity(l);
        }
        return;
    }

    public boolean stopViewServer() {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if(!isSystemSecure() && checkCallingPermission("android.permission.DUMP", "stopViewServer") && mViewServer != null)
            flag = mViewServer.stop();
        return flag;
    }

    public void switchKeyboardLayout(int i, int j) {
        mInputManager.switchKeyboardLayout(i, j);
    }

    public void systemReady() {
        mPolicy.systemReady();
    }

    public void thawRotation() {
        if(!checkCallingPermission("android.permission.SET_ORIENTATION", "thawRotation()")) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        } else {
            mPolicy.setUserRotationMode(0, 777);
            updateRotationUnchecked(false, false);
            return;
        }
    }

    void unsetAppFreezingScreenLocked(AppWindowToken appwindowtoken, boolean flag, boolean flag1) {
        if(appwindowtoken.mAppAnimator.freezingScreen) {
            int i = appwindowtoken.allAppWindows.size();
            boolean flag2 = false;
            for(int j = 0; j < i; j++) {
                WindowState windowstate = (WindowState)appwindowtoken.allAppWindows.get(j);
                if(!windowstate.mAppFreezing)
                    continue;
                windowstate.mAppFreezing = false;
                if(windowstate.mHasSurface && !windowstate.mOrientationChanging) {
                    windowstate.mOrientationChanging = true;
                    mInnerFields.mOrientationChangeComplete = false;
                }
                flag2 = true;
            }

            if(flag1 || flag2) {
                appwindowtoken.mAppAnimator.freezingScreen = false;
                mAppsFreezingScreen = -1 + mAppsFreezingScreen;
            }
            if(flag) {
                if(flag2) {
                    mLayoutNeeded = true;
                    performLayoutAndPlaceSurfacesLocked();
                }
                stopFreezingDisplayLocked();
            }
        }
    }

    public Configuration updateOrientationFromAppTokens(Configuration configuration, IBinder ibinder) {
        if(!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "updateOrientationFromAppTokens()"))
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        long l = Binder.clearCallingIdentity();
        Configuration configuration1;
        synchronized(mWindowMap) {
            configuration1 = updateOrientationFromAppTokensLocked(configuration, ibinder);
        }
        Binder.restoreCallingIdentity(l);
        return configuration1;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    boolean updateOrientationFromAppTokensLocked(boolean flag) {
        long l = Binder.clearCallingIdentity();
        int i = computeForcedAppOrientationLocked();
        if(i == mForcedAppOrientation) goto _L2; else goto _L1
_L1:
        boolean flag2;
        mForcedAppOrientation = i;
        mPolicy.setCurrentOrientationLw(i);
        flag2 = updateRotationUncheckedLocked(flag);
        if(!flag2) goto _L2; else goto _L3
_L3:
        boolean flag1;
        flag1 = true;
        Binder.restoreCallingIdentity(l);
_L5:
        return flag1;
_L2:
        flag1 = false;
        Binder.restoreCallingIdentity(l);
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void updateRotation(boolean flag, boolean flag1) {
        updateRotationUnchecked(flag, flag1);
    }

    public void updateRotationUnchecked(boolean flag, boolean flag1) {
        long l = Binder.clearCallingIdentity();
        boolean flag2;
        synchronized(mWindowMap) {
            flag2 = updateRotationUncheckedLocked(false);
            if(!flag2 || flag1) {
                mLayoutNeeded = true;
                performLayoutAndPlaceSurfacesLocked();
            }
        }
        if(flag2 || flag)
            sendNewConfiguration();
        Binder.restoreCallingIdentity(l);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean updateRotationUncheckedLocked(boolean flag) {
        boolean flag1;
        flag1 = false;
        break MISSING_BLOCK_LABEL_2;
_L2:
        int i;
        do
            return flag1;
        while(mDeferredRotationPauseCount > 0 || mAnimator.mScreenRotationAnimation != null && mAnimator.mScreenRotationAnimation.isAnimating() || !mDisplayEnabled);
        i = mPolicy.rotationForOrientationLw(mForcedAppOrientation, mRotation);
        boolean flag2;
        InputManagerService inputmanagerservice;
        int j;
        int k;
        WindowState windowstate;
        if(!mPolicy.rotationHasCompatibleMetricsLw(mForcedAppOrientation, i))
            flag2 = true;
        else
            flag2 = false;
        if(mRotation == i && mAltOrientation == flag2)
            continue; /* Loop/switch isn't completed */
        mRotation = i;
        mAltOrientation = flag2;
        mPolicy.setRotationLw(mRotation);
        mWindowsFreezingScreen = true;
        mH.removeMessages(11);
        mH.sendMessageDelayed(mH.obtainMessage(11), 2000L);
        mWaitingForConfig = true;
        mLayoutNeeded = true;
        startFreezingDisplayLocked(flag);
        inputmanagerservice = mInputManager;
        if(mDisplay != null)
            j = mDisplay.getExternalRotation();
        else
            j = 0;
        inputmanagerservice.setDisplayOrientation(0, i, j);
        computeScreenConfigurationLocked(null);
        if(!flag)
            Surface.openTransaction();
        if(mAnimator.mScreenRotationAnimation != null && mAnimator.mScreenRotationAnimation.hasScreenshot() && mAnimator.mScreenRotationAnimation.setRotation(i, mFxSession, 10000L, mTransitionAnimationScale, mCurDisplayWidth, mCurDisplayHeight))
            scheduleAnimationLocked();
        Surface.setOrientation(0, i);
        if(!flag)
            Surface.closeTransaction();
        rebuildBlackFrame();
        for(k = -1 + mWindows.size(); k >= 0; k--) {
            windowstate = (WindowState)mWindows.get(k);
            if(windowstate.mHasSurface) {
                windowstate.mOrientationChanging = true;
                mInnerFields.mOrientationChangeComplete = false;
            }
        }

        break MISSING_BLOCK_LABEL_372;
        Exception exception;
        exception;
        if(!flag)
            Surface.closeTransaction();
        throw exception;
        int l = -1 + mRotationWatchers.size();
        while(l >= 0)  {
            try {
                ((IRotationWatcher)mRotationWatchers.get(l)).onRotationChanged(i);
            }
            catch(RemoteException remoteexception) { }
            l--;
        }
        flag1 = true;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void updateStatusBarVisibilityLocked(int i) {
        mInputManager.setSystemUiVisibility(i);
        int j = mWindows.size();
        int k = 0;
        while(k < j)  {
            WindowState windowstate = (WindowState)mWindows.get(k);
            try {
                int l = windowstate.mSystemUiVisibility;
                int i1 = 7 & (l ^ i) & ~i;
                int j1 = l & ~i1 | i & i1;
                if(j1 != l) {
                    windowstate.mSeq = 1 + windowstate.mSeq;
                    windowstate.mSystemUiVisibility = j1;
                }
                if(j1 != l || windowstate.mAttrs.hasSystemUiListeners)
                    windowstate.mClient.dispatchSystemUiVisibilityChanged(windowstate.mSeq, i, j1, i1);
            }
            catch(RemoteException remoteexception) { }
            k++;
        }
    }

    void updateWallpaperOffsetLocked(WindowState windowstate, boolean flag) {
        int i;
        int j;
        WindowState windowstate1;
        i = mAppDisplayWidth;
        j = mAppDisplayHeight;
        windowstate1 = mWallpaperTarget;
        if(windowstate1 == null) goto _L2; else goto _L1
_L1:
        if(windowstate1.mWallpaperX < 0.0F) goto _L4; else goto _L3
_L3:
        mLastWallpaperX = windowstate1.mWallpaperX;
_L9:
        if(windowstate1.mWallpaperY < 0.0F) goto _L6; else goto _L5
_L5:
        mLastWallpaperY = windowstate1.mWallpaperY;
_L2:
        int k = mWallpaperTokens.size();
        do {
            if(k <= 0)
                break; /* Loop/switch isn't completed */
            k--;
            WindowToken windowtoken = (WindowToken)mWallpaperTokens.get(k);
            int l = windowtoken.windows.size();
            while(l > 0)  {
                l--;
                WindowState windowstate2 = (WindowState)windowtoken.windows.get(l);
                if(updateWallpaperOffsetLocked(windowstate2, i, j, flag)) {
                    WindowStateAnimator windowstateanimator = windowstate2.mWinAnimator;
                    windowstateanimator.computeShownFrameLocked();
                    if(windowstateanimator.mSurfaceX != windowstate2.mShownFrame.left || windowstateanimator.mSurfaceY != windowstate2.mShownFrame.top) {
                        Surface.openTransaction();
                        try {
                            setWallpaperOffset(windowstateanimator, (int)windowstate2.mShownFrame.left, (int)windowstate2.mShownFrame.top);
                        }
                        catch(RuntimeException runtimeexception) {
                            Slog.w("WindowManager", (new StringBuilder()).append("Error positioning surface of ").append(windowstate2).append(" pos=(").append(windowstate2.mShownFrame.left).append(",").append(windowstate2.mShownFrame.top).append(")").toString(), runtimeexception);
                        }
                        Surface.closeTransaction();
                    }
                    flag = false;
                }
            }
        } while(true);
_L4:
        if(windowstate.mWallpaperX >= 0.0F)
            mLastWallpaperX = windowstate.mWallpaperX;
        continue; /* Loop/switch isn't completed */
_L6:
        if(windowstate.mWallpaperY >= 0.0F)
            mLastWallpaperY = windowstate.mWallpaperY;
        if(true) goto _L2; else goto _L7
_L7:
        return;
        if(true) goto _L9; else goto _L8
_L8:
    }

    boolean updateWallpaperOffsetLocked(WindowState windowstate, int i, int j, boolean flag) {
        boolean flag1 = false;
        float f;
        float f1;
        int k;
        int l;
        boolean flag2;
        float f2;
        float f3;
        int i1;
        int j1;
        long l1;
        long l2;
        long l3;
        if(mLastWallpaperX >= 0.0F)
            f = mLastWallpaperX;
        else
            f = 0.5F;
        if(mLastWallpaperXStep >= 0.0F)
            f1 = mLastWallpaperXStep;
        else
            f1 = -1F;
        k = windowstate.mFrame.right - windowstate.mFrame.left - i;
        if(k > 0)
            l = -(int)(0.5F + f * (float)k);
        else
            l = 0;
        if(windowstate.mXOffset != l)
            flag2 = true;
        else
            flag2 = false;
        if(flag2)
            windowstate.mXOffset = l;
        if(windowstate.mWallpaperX != f || windowstate.mWallpaperXStep != f1) {
            windowstate.mWallpaperX = f;
            windowstate.mWallpaperXStep = f1;
            flag1 = true;
        }
        if(mLastWallpaperY >= 0.0F)
            f2 = mLastWallpaperY;
        else
            f2 = 0.5F;
        if(mLastWallpaperYStep >= 0.0F)
            f3 = mLastWallpaperYStep;
        else
            f3 = -1F;
        i1 = windowstate.mFrame.bottom - windowstate.mFrame.top - j;
        if(i1 > 0)
            j1 = -(int)(0.5F + f2 * (float)i1);
        else
            j1 = 0;
        if(windowstate.mYOffset != j1) {
            flag2 = true;
            windowstate.mYOffset = j1;
        }
        if(windowstate.mWallpaperY != f2 || windowstate.mWallpaperYStep != f3) {
            windowstate.mWallpaperY = f2;
            windowstate.mWallpaperYStep = f3;
            flag1 = true;
        }
        if(!flag1 || (4 & windowstate.mAttrs.privateFlags) == 0) goto _L2; else goto _L1
_L1:
        if(!flag)
            break MISSING_BLOCK_LABEL_277;
        mWaitingOnWallpaper = windowstate;
        windowstate.mClient.dispatchWallpaperOffsets(windowstate.mWallpaperX, windowstate.mWallpaperY, windowstate.mWallpaperXStep, windowstate.mWallpaperYStep, flag);
        if(!flag || mWaitingOnWallpaper == null) goto _L2; else goto _L3
_L3:
        l1 = SystemClock.uptimeMillis();
        l2 = mLastWallpaperTimeoutTime;
        if(l2 + 10000L >= l1) goto _L5; else goto _L4
_L4:
        mWindowMap.wait(150L);
_L8:
        l3 = 150L + l1;
        if(l3 < SystemClock.uptimeMillis()) {
            Slog.i("WindowManager", (new StringBuilder()).append("Timeout waiting for wallpaper to offset: ").append(windowstate).toString());
            mLastWallpaperTimeoutTime = l1;
        }
_L5:
        mWaitingOnWallpaper = null;
_L2:
        return flag2;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L6
_L6:
        InterruptedException interruptedexception;
        interruptedexception;
        if(true) goto _L8; else goto _L7
_L7:
    }

    void updateWallpaperVisibilityLocked() {
        boolean flag = isWallpaperVisible(mWallpaperTarget);
        int i = mAppDisplayWidth;
        int j = mAppDisplayHeight;
        for(int k = mWallpaperTokens.size(); k > 0;) {
            k--;
            WindowToken windowtoken = (WindowToken)mWallpaperTokens.get(k);
            if(windowtoken.hidden == flag) {
                int l;
                WindowState windowstate;
                boolean flag1;
                if(!flag)
                    flag1 = true;
                else
                    flag1 = false;
                windowtoken.hidden = flag1;
                mLayoutNeeded = true;
            }
            l = windowtoken.windows.size();
            while(l > 0)  {
                l--;
                windowstate = (WindowState)windowtoken.windows.get(l);
                if(flag)
                    updateWallpaperOffsetLocked(windowstate, i, j, false);
                dispatchWallpaperVisibility(windowstate, flag);
            }
        }

    }

    public void validateAppTokens(List list) {
        int i = -1 + list.size();
        int j;
        for(j = -1 + mAppTokens.size(); i >= 0 && j >= 0;) {
            AppWindowToken appwindowtoken1 = (AppWindowToken)mAppTokens.get(j);
            if(appwindowtoken1.removed) {
                j--;
            } else {
                if(list.get(i) != ((WindowToken) (appwindowtoken1)).token)
                    Slog.w("WindowManager", (new StringBuilder()).append("Tokens out of sync: external is ").append(list.get(i)).append(" @ ").append(i).append(", internal is ").append(((WindowToken) (appwindowtoken1)).token).append(" @ ").append(j).toString());
                i--;
                j--;
            }
        }

        for(; i >= 0; i--)
            Slog.w("WindowManager", (new StringBuilder()).append("External token not found: ").append(list.get(i)).append(" @ ").append(i).toString());

        for(; j >= 0; j--) {
            AppWindowToken appwindowtoken = (AppWindowToken)mAppTokens.get(j);
            if(!appwindowtoken.removed)
                Slog.w("WindowManager", (new StringBuilder()).append("Invalid internal token: ").append(((WindowToken) (appwindowtoken)).token).append(" @ ").append(j).toString());
        }

    }

    boolean viewServerGetFocusedWindow(Socket socket) {
        if(!isSystemSecure()) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        WindowState windowstate;
        BufferedWriter bufferedwriter;
        flag = true;
        windowstate = getFocusedWindow();
        bufferedwriter = null;
        BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
        if(windowstate == null)
            break MISSING_BLOCK_LABEL_80;
        bufferedwriter1.write(Integer.toHexString(System.identityHashCode(windowstate)));
        bufferedwriter1.write(32);
        bufferedwriter1.append(windowstate.mAttrs.getTitle());
        bufferedwriter1.write(10);
        bufferedwriter1.flush();
        if(bufferedwriter1 != null)
            try {
                bufferedwriter1.close();
            }
            catch(IOException ioexception2) {
                flag = false;
            }
        continue; /* Loop/switch isn't completed */
        Exception exception2;
        exception2;
_L7:
        flag = false;
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception) {
                flag = false;
            }
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception1;
        exception1;
_L6:
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception1) { }
        throw exception1;
        exception1;
        bufferedwriter = bufferedwriter1;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        bufferedwriter = bufferedwriter1;
          goto _L7
    }

    boolean viewServerListWindows(Socket socket) {
        if(!isSystemSecure()) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L5:
        return flag;
_L2:
        WindowState awindowstate[];
        BufferedWriter bufferedwriter;
        flag = true;
        synchronized(mWindowMap) {
            awindowstate = (WindowState[])mWindows.toArray(new WindowState[mWindows.size()]);
        }
        bufferedwriter = null;
        BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
        int i;
        int j;
        i = awindowstate.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_146;
        WindowState windowstate = awindowstate[j];
        bufferedwriter1.write(Integer.toHexString(System.identityHashCode(windowstate)));
        bufferedwriter1.write(32);
        bufferedwriter1.append(windowstate.mAttrs.getTitle());
        bufferedwriter1.write(10);
        j++;
          goto _L3
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        bufferedwriter1.write("DONE.\n");
        bufferedwriter1.flush();
        if(bufferedwriter1 != null)
            try {
                bufferedwriter1.close();
            }
            catch(IOException ioexception2) {
                flag = false;
            }
        continue; /* Loop/switch isn't completed */
        Exception exception3;
        exception3;
_L8:
        flag = false;
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception) {
                flag = false;
            }
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception2;
        exception2;
_L7:
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception1) { }
        throw exception2;
        exception2;
        bufferedwriter = bufferedwriter1;
        if(true) goto _L7; else goto _L6
_L6:
        Exception exception1;
        exception1;
        bufferedwriter = bufferedwriter1;
          goto _L8
    }

    boolean viewServerWindowCommand(Socket socket, String s, String s1) {
        if(!isSystemSecure()) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L3:
        return flag;
_L2:
        Parcel parcel;
        Parcel parcel1;
        BufferedWriter bufferedwriter;
        flag = true;
        parcel = null;
        parcel1 = null;
        bufferedwriter = null;
        int j;
        int i = s1.indexOf(' ');
        if(i == -1)
            i = s1.length();
        j = (int)Long.parseLong(s1.substring(0, i), 16);
        if(i >= s1.length())
            break MISSING_BLOCK_LABEL_122;
        int k = i + 1;
        s1 = s1.substring(k);
_L4:
        WindowState windowstate = findWindow(j);
        if(windowstate != null)
            break MISSING_BLOCK_LABEL_129;
        flag = false;
        if(false)
            throw null;
        if(false)
            throw null;
        if(false)
            try {
                throw null;
            }
            catch(IOException ioexception3) { }
          goto _L3
        s1 = "";
          goto _L4
        BufferedWriter bufferedwriter1;
        parcel = Parcel.obtain();
        parcel.writeInterfaceToken("android.view.IWindow");
        parcel.writeString(s);
        parcel.writeString(s1);
        parcel.writeInt(1);
        ParcelFileDescriptor.fromSocket(socket).writeToParcel(parcel, 0);
        parcel1 = Parcel.obtain();
        windowstate.mClient.asBinder().transact(1, parcel, parcel1, 0);
        parcel1.readException();
        if(socket.isOutputShutdown())
            break MISSING_BLOCK_LABEL_246;
        bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedwriter1.write("DONE\n");
        bufferedwriter1.flush();
        bufferedwriter = bufferedwriter1;
        if(parcel != null)
            parcel.recycle();
        if(parcel1 != null)
            parcel1.recycle();
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception2) { }
          goto _L3
        Exception exception1;
        exception1;
_L7:
        Slog.w("WindowManager", (new StringBuilder()).append("Could not send command ").append(s).append(" with parameters ").append(s1).toString(), exception1);
        flag = false;
        if(parcel != null)
            parcel.recycle();
        if(parcel1 != null)
            parcel1.recycle();
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception1) { }
          goto _L3
        Exception exception;
        exception;
_L6:
        if(parcel != null)
            parcel.recycle();
        if(parcel1 != null)
            parcel1.recycle();
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        exception;
        bufferedwriter = bufferedwriter1;
        if(true) goto _L6; else goto _L5
_L5:
        exception1;
        bufferedwriter = bufferedwriter1;
          goto _L7
    }

    public void waitForWindowDrawn(IBinder ibinder, IRemoteCallback iremotecallback) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = windowForClientLocked(null, ibinder, true);
        if(windowstate != null) {
            Pair pair = new Pair(windowstate, iremotecallback);
            Message message = mH.obtainMessage(24, pair);
            mH.sendMessageDelayed(message, 2000L);
            mWaitingForDrawn.add(pair);
            checkDrawnWindowsLocked();
        }
        return;
    }

    void wallpaperCommandComplete(IBinder ibinder, Bundle bundle) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mWaitingOnWallpaper != null && mWaitingOnWallpaper.mClient.asBinder() == ibinder) {
            mWaitingOnWallpaper = null;
            mWindowMap.notifyAll();
        }
        return;
    }

    void wallpaperOffsetsComplete(IBinder ibinder) {
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mWaitingOnWallpaper != null && mWaitingOnWallpaper.mClient.asBinder() == ibinder) {
            mWaitingOnWallpaper = null;
            mWindowMap.notifyAll();
        }
        return;
    }

    public int watchRotation(IRotationWatcher irotationwatcher) {
        android.os.IBinder.DeathRecipient deathrecipient = new android.os.IBinder.DeathRecipient() {

            public void binderDied() {
                HashMap hashmap1 = mWindowMap;
                hashmap1;
                JVM INSTR monitorenter ;
                int j = 0;
                do {
label0:
                    {
                        if(j < mRotationWatchers.size()) {
                            if(watcherBinder != ((IRotationWatcher)mRotationWatchers.get(j)).asBinder())
                                break label0;
                            IRotationWatcher irotationwatcher1 = (IRotationWatcher)mRotationWatchers.remove(j);
                            if(irotationwatcher1 != null)
                                irotationwatcher1.asBinder().unlinkToDeath(this, 0);
                        } else {
                            return;
                        }
                        j--;
                    }
                    j++;
                } while(true);
            }

            final WindowManagerService this$0;
            final IBinder val$watcherBinder;

             {
                this$0 = WindowManagerService.this;
                watcherBinder = ibinder;
                super();
            }
        };
        HashMap hashmap = mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        Exception exception;
        int i;
        try {
            irotationwatcher.asBinder().linkToDeath(deathrecipient, 0);
            mRotationWatchers.add(irotationwatcher);
        }
        catch(RemoteException remoteexception) { }
        finally {
            hashmap;
        }
        i = mRotation;
        return i;
        throw exception;
    }

    final WindowState windowForClientLocked(Session session, IBinder ibinder, boolean flag) {
        WindowState windowstate = (WindowState)mWindowMap.get(ibinder);
        if(windowstate != null) goto _L2; else goto _L1
_L1:
        IllegalArgumentException illegalargumentexception = new IllegalArgumentException((new StringBuilder()).append("Requested window ").append(ibinder).append(" does not exist").toString());
        if(flag)
            throw illegalargumentexception;
        Slog.w("WindowManager", "Failed looking up window", illegalargumentexception);
        windowstate = null;
_L4:
        return windowstate;
_L2:
        if(session != null && windowstate.mSession != session) {
            IllegalArgumentException illegalargumentexception1 = new IllegalArgumentException((new StringBuilder()).append("Requested window ").append(ibinder).append(" is in session ").append(windowstate.mSession).append(", not ").append(session).toString());
            if(flag)
                throw illegalargumentexception1;
            Slog.w("WindowManager", "Failed looking up window", illegalargumentexception1);
            windowstate = null;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    final WindowState windowForClientLocked(Session session, IWindow iwindow, boolean flag) {
        return windowForClientLocked(session, iwindow.asBinder(), flag);
    }

    static final int ADJUST_WALLPAPER_LAYERS_CHANGED = 2;
    static final int ADJUST_WALLPAPER_VISIBILITY_CHANGED = 4;
    private static final int ALLOW_DISABLE_NO = 0;
    private static final int ALLOW_DISABLE_UNKNOWN = -1;
    private static final int ALLOW_DISABLE_YES = 1;
    static final boolean CUSTOM_SCREEN_ROTATION = true;
    static final boolean DEBUG = false;
    static final boolean DEBUG_ADD_REMOVE = false;
    static final boolean DEBUG_ANIM = false;
    static final boolean DEBUG_APP_ORIENTATION = false;
    static final boolean DEBUG_APP_TRANSITIONS = false;
    static final boolean DEBUG_BOOT = false;
    static final boolean DEBUG_CONFIGURATION = false;
    static final boolean DEBUG_DRAG = false;
    static final boolean DEBUG_FOCUS = false;
    static final boolean DEBUG_INPUT = false;
    static final boolean DEBUG_INPUT_METHOD = false;
    static final boolean DEBUG_LAYERS = false;
    static final boolean DEBUG_LAYOUT = false;
    static final boolean DEBUG_LAYOUT_REPEATS = true;
    static final boolean DEBUG_ORIENTATION = false;
    static final boolean DEBUG_REORDER = false;
    static final boolean DEBUG_RESIZE = false;
    static final boolean DEBUG_SCREENSHOT = false;
    static final boolean DEBUG_SCREEN_ON = false;
    static final boolean DEBUG_STARTING_WINDOW = false;
    static final boolean DEBUG_SURFACE_TRACE = false;
    static final boolean DEBUG_TOKEN_MOVEMENT = false;
    static final boolean DEBUG_VISIBILITY = false;
    static final boolean DEBUG_WALLPAPER = false;
    static final boolean DEBUG_WINDOW_MOVEMENT = false;
    static final boolean DEBUG_WINDOW_TRACE = false;
    static final int DEFAULT_DIM_DURATION = 200;
    static final int DEFAULT_FADE_IN_OUT_DURATION = 400;
    static final long DEFAULT_INPUT_DISPATCHING_TIMEOUT_NANOS = 0x12a05f200L;
    static final int FREEZE_LAYER = 0x1e8481;
    static final boolean HIDE_STACK_CRAWLS = true;
    private static final int INPUT_DEVICES_READY_FOR_SAFE_MODE_DETECTION_TIMEOUT_MILLIS = 1000;
    static final int LAYER_OFFSET_BLUR = 2;
    static final int LAYER_OFFSET_DIM = 1;
    static final int LAYER_OFFSET_THUMBNAIL = 4;
    static final int LAYOUT_REPEAT_THRESHOLD = 4;
    static final int MASK_LAYER = 0x1e8480;
    static final int MAX_ANIMATION_DURATION = 10000;
    static final boolean PROFILE_ORIENTATION = false;
    static final boolean SHOW_LIGHT_TRANSACTIONS = false;
    static final boolean SHOW_SURFACE_ALLOC = false;
    static final boolean SHOW_TRANSACTIONS = false;
    private static final String SYSTEM_DEBUGGABLE = "ro.debuggable";
    private static final String SYSTEM_HEADLESS = "ro.config.headless";
    private static final String SYSTEM_SECURE = "ro.secure";
    static final String TAG = "WindowManager";
    private static final float THUMBNAIL_ANIMATION_DECELERATE_FACTOR = 1.5F;
    static final int TYPE_LAYER_MULTIPLIER = 10000;
    static final int TYPE_LAYER_OFFSET = 1000;
    static final int UPDATE_FOCUS_NORMAL = 0;
    static final int UPDATE_FOCUS_PLACING_SURFACES = 2;
    static final int UPDATE_FOCUS_WILL_ASSIGN_LAYERS = 1;
    static final int UPDATE_FOCUS_WILL_PLACE_SURFACES = 3;
    static final long WALLPAPER_TIMEOUT = 150L;
    static final long WALLPAPER_TIMEOUT_RECOVERY = 10000L;
    static final int WINDOW_LAYER_MULTIPLIER = 5;
    static final boolean localLOGV;
    final IActivityManager mActivityManager;
    final boolean mAllowBootMessages;
    private int mAllowDisableKeyguard;
    boolean mAltOrientation;
    ArrayList mAnimatingAppTokens;
    final AnimationRunnable mAnimationRunnable;
    boolean mAnimationScheduled;
    final WindowAnimator mAnimator;
    float mAnimatorDurationScale;
    int mAppDisplayHeight;
    int mAppDisplayWidth;
    final ArrayList mAppTokens;
    boolean mAppTransitionReady;
    boolean mAppTransitionRunning;
    boolean mAppTransitionTimeout;
    int mAppsFreezingScreen;
    int mBaseDisplayHeight;
    int mBaseDisplayWidth;
    final IBatteryStats mBatteryStats;
    BlackFrame mBlackFrame;
    final BroadcastReceiver mBroadcastReceiver;
    final Choreographer mChoreographer;
    final ArrayList mClosingApps;
    final DisplayMetrics mCompatDisplayMetrics;
    float mCompatibleScreenScale;
    final Context mContext;
    Configuration mCurConfiguration;
    int mCurDisplayHeight;
    int mCurDisplayWidth;
    WindowState mCurrentFocus;
    int mDeferredRotationPauseCount;
    final ArrayList mDestroySurface;
    Display mDisplay;
    boolean mDisplayEnabled;
    boolean mDisplayFrozen;
    final DisplayMetrics mDisplayMetrics;
    final Object mDisplaySizeLock;
    DragState mDragState;
    private boolean mEventDispatchingEnabled;
    final ArrayList mExitingAppTokens;
    final ArrayList mExitingTokens;
    final ArrayList mFakeWindows;
    final ArrayList mFinishedStarting;
    boolean mFocusMayChange;
    AppWindowToken mFocusedApp;
    boolean mForceDisplayEnabled;
    ArrayList mForceRemoves;
    int mForcedAppOrientation;
    final SurfaceSession mFxSession;
    final H mH;
    boolean mHardKeyboardAvailable;
    boolean mHardKeyboardEnabled;
    OnHardKeyboardStatusChangeListener mHardKeyboardStatusChangeListener;
    final boolean mHaveInputMethods;
    private final boolean mHeadless;
    Session mHoldingScreenOn;
    android.os.PowerManager.WakeLock mHoldingScreenWakeLock;
    private boolean mInLayout;
    boolean mInTouchMode;
    int mInitialDisplayHeight;
    int mInitialDisplayWidth;
    LayoutFields mInnerFields;
    final InputManagerService mInputManager;
    int mInputMethodAnimLayerAdjustment;
    final ArrayList mInputMethodDialogs;
    IInputMethodManager mInputMethodManager;
    WindowState mInputMethodTarget;
    boolean mInputMethodTargetWaitingAnim;
    WindowState mInputMethodWindow;
    final InputMonitor mInputMonitor;
    boolean mIsTouchDevice;
    private boolean mKeyguardDisabled;
    final TokenWatcher mKeyguardTokenWatcher;
    int mLargestDisplayHeight;
    int mLargestDisplayWidth;
    String mLastANRState;
    WindowState mLastFocus;
    int mLastStatusBarVisibility;
    long mLastWallpaperTimeoutTime;
    float mLastWallpaperX;
    float mLastWallpaperXStep;
    float mLastWallpaperY;
    float mLastWallpaperYStep;
    int mLastWindowForcedOrientation;
    boolean mLayoutNeeded;
    private int mLayoutRepeatCount;
    int mLayoutSeq;
    final boolean mLimitedAlphaCompositing;
    ArrayList mLosingFocus;
    WindowState mLowerWallpaperTarget;
    int mNextAppTransition;
    IRemoteCallback mNextAppTransitionCallback;
    boolean mNextAppTransitionDelayed;
    int mNextAppTransitionEnter;
    int mNextAppTransitionExit;
    String mNextAppTransitionPackage;
    int mNextAppTransitionStartHeight;
    int mNextAppTransitionStartWidth;
    int mNextAppTransitionStartX;
    int mNextAppTransitionStartY;
    Bitmap mNextAppTransitionThumbnail;
    int mNextAppTransitionType;
    final boolean mOnlyCore;
    final ArrayList mOpeningApps;
    int mPendingLayoutChanges;
    final ArrayList mPendingRemove;
    WindowState mPendingRemoveTmp[];
    final WindowManagerPolicy mPolicy;
    PowerManagerService mPowerManager;
    final DisplayMetrics mRealDisplayMetrics;
    WindowState mRebuildTmp[];
    final ArrayList mRelayoutWhileAnimating;
    final ArrayList mResizingWindows;
    int mRotation;
    ArrayList mRotationWatchers;
    boolean mSafeMode;
    android.os.PowerManager.WakeLock mScreenFrozenLock;
    final HashSet mSessions;
    boolean mShowingBootMessages;
    boolean mSkipAppTransitionAnimation;
    int mSmallestDisplayHeight;
    int mSmallestDisplayWidth;
    boolean mStartingIconInTransition;
    StrictModeFlash mStrictModeFlash;
    boolean mSystemBooted;
    int mSystemDecorLayer;
    final Rect mSystemDecorRect;
    final Configuration mTempConfiguration;
    final DisplayMetrics mTmpDisplayMetrics;
    final float mTmpFloats[];
    final HashMap mTokenMap;
    private int mTransactionSequence;
    float mTransitionAnimationScale;
    boolean mTraversalScheduled;
    boolean mTurnOnScreen;
    WindowState mUpperWallpaperTarget;
    private ViewServer mViewServer;
    boolean mWaitingForConfig;
    ArrayList mWaitingForDrawn;
    WindowState mWaitingOnWallpaper;
    int mWallpaperAnimLayerAdjustment;
    WindowState mWallpaperTarget;
    final ArrayList mWallpaperTokens;
    Watermark mWatermark;
    float mWindowAnimationScale;
    private ArrayList mWindowChangeListeners;
    final HashMap mWindowMap;
    final ArrayList mWindows;
    private boolean mWindowsChanged;
    boolean mWindowsFreezingScreen;




/*
    static boolean access$102(WindowManagerService windowmanagerservice, boolean flag) {
        windowmanagerservice.mKeyguardDisabled = flag;
        return flag;
    }

*/


/*
    static int access$202(WindowManagerService windowmanagerservice, int i) {
        windowmanagerservice.mAllowDisableKeyguard = i;
        return i;
    }

*/





/*
    static boolean access$702(WindowManagerService windowmanagerservice, boolean flag) {
        windowmanagerservice.mWindowsChanged = flag;
        return flag;
    }

*/

}
