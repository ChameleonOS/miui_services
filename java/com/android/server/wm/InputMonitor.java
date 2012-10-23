// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Slog;
import android.view.*;
import com.android.server.input.*;
import java.util.ArrayList;
import java.util.Arrays;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, WindowState, AppWindowToken, WindowToken, 
//            DragState, FakeWindowImpl, Session

final class InputMonitor
    implements com.android.server.input.InputManagerService.Callbacks {

    public InputMonitor(WindowManagerService windowmanagerservice) {
        mUpdateInputWindowsNeeded = true;
        mService = windowmanagerservice;
    }

    private void addInputWindowHandleLw(InputWindowHandle inputwindowhandle) {
        if(mInputWindowHandles == null)
            mInputWindowHandles = new InputWindowHandle[16];
        if(mInputWindowHandleCount >= mInputWindowHandles.length)
            mInputWindowHandles = (InputWindowHandle[])Arrays.copyOf(mInputWindowHandles, 2 * mInputWindowHandleCount);
        InputWindowHandle ainputwindowhandle[] = mInputWindowHandles;
        int i = mInputWindowHandleCount;
        mInputWindowHandleCount = i + 1;
        ainputwindowhandle[i] = inputwindowhandle;
    }

    private void clearInputWindowHandlesLw() {
        while(mInputWindowHandleCount != 0)  {
            InputWindowHandle ainputwindowhandle[] = mInputWindowHandles;
            int i = -1 + mInputWindowHandleCount;
            mInputWindowHandleCount = i;
            ainputwindowhandle[i] = null;
        }
    }

    private void updateInputDispatchModeLw() {
        mService.mInputManager.setInputDispatchMode(mInputDispatchEnabled, mInputDispatchFrozen);
    }

    public KeyEvent dispatchUnhandledKey(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i) {
        WindowState windowstate;
        if(inputwindowhandle != null)
            windowstate = (WindowState)inputwindowhandle.windowState;
        else
            windowstate = null;
        return mService.mPolicy.dispatchUnhandledKey(windowstate, keyevent, i);
    }

    public void freezeInputDispatchingLw() {
        if(!mInputDispatchFrozen) {
            mInputDispatchFrozen = true;
            updateInputDispatchModeLw();
        }
    }

    public int getPointerLayer() {
        return 1000 + 10000 * mService.mPolicy.windowTypeToLayerLw(2018);
    }

    public long interceptKeyBeforeDispatching(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i) {
        WindowState windowstate;
        if(inputwindowhandle != null)
            windowstate = (WindowState)inputwindowhandle.windowState;
        else
            windowstate = null;
        return mService.mPolicy.interceptKeyBeforeDispatching(windowstate, keyevent, i);
    }

    public int interceptKeyBeforeQueueing(KeyEvent keyevent, int i, boolean flag) {
        return mService.mPolicy.interceptKeyBeforeQueueing(keyevent, i, flag);
    }

    public int interceptMotionBeforeQueueingWhenScreenOff(int i) {
        return mService.mPolicy.interceptMotionBeforeQueueingWhenScreenOff(i);
    }

    public long notifyANR(InputApplicationHandle inputapplicationhandle, InputWindowHandle inputwindowhandle) {
        AppWindowToken appwindowtoken = null;
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate;
        windowstate = null;
        if(inputwindowhandle == null)
            break MISSING_BLOCK_LABEL_41;
        windowstate = (WindowState)inputwindowhandle.windowState;
        if(windowstate != null)
            appwindowtoken = windowstate.mAppToken;
        if(appwindowtoken == null && inputapplicationhandle != null)
            appwindowtoken = (AppWindowToken)inputapplicationhandle.appWindowToken;
        if(windowstate == null) goto _L2; else goto _L1
_L1:
        Slog.i("WindowManager", (new StringBuilder()).append("Input event dispatching timed out sending to ").append(windowstate.mAttrs.getTitle()).toString());
_L3:
        mService.saveANRStateLocked(appwindowtoken, windowstate);
        hashmap;
        JVM INSTR monitorexit ;
        if(appwindowtoken == null || appwindowtoken.appToken == null)
            break MISSING_BLOCK_LABEL_195;
        long l;
        if(appwindowtoken.appToken.keyDispatchingTimedOut())
            break MISSING_BLOCK_LABEL_195;
        l = appwindowtoken.inputDispatchingTimeoutNanos;
_L4:
        return l;
_L2:
        if(appwindowtoken == null)
            break MISSING_BLOCK_LABEL_182;
        Slog.i("WindowManager", (new StringBuilder()).append("Input event dispatching timed out sending to application ").append(((WindowToken) (appwindowtoken)).stringName).toString());
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Slog.i("WindowManager", "Input event dispatching timed out.");
          goto _L3
        RemoteException remoteexception;
        remoteexception;
        l = 0L;
          goto _L4
    }

    public void notifyConfigurationChanged() {
        mService.sendNewConfiguration();
        Object obj = mInputDevicesReadyMonitor;
        obj;
        JVM INSTR monitorenter ;
        if(!mInputDevicesReady) {
            mInputDevicesReady = true;
            mInputDevicesReadyMonitor.notifyAll();
        }
        return;
    }

    public void notifyInputChannelBroken(InputWindowHandle inputwindowhandle) {
        if(inputwindowhandle != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        WindowState windowstate = (WindowState)inputwindowhandle.windowState;
        if(windowstate != null) {
            Slog.i("WindowManager", (new StringBuilder()).append("WINDOW DIED ").append(windowstate).toString());
            mService.removeWindowLocked(windowstate.mSession, windowstate);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void notifyLidSwitchChanged(long l, boolean flag) {
        mService.mPolicy.notifyLidSwitchChanged(l, flag);
    }

    public void pauseDispatchingLw(WindowToken windowtoken) {
        if(!windowtoken.paused) {
            windowtoken.paused = true;
            updateInputWindowsLw(true);
        }
    }

    public void resumeDispatchingLw(WindowToken windowtoken) {
        if(windowtoken.paused) {
            windowtoken.paused = false;
            updateInputWindowsLw(true);
        }
    }

    public void setEventDispatchingLw(boolean flag) {
        if(mInputDispatchEnabled != flag) {
            mInputDispatchEnabled = flag;
            updateInputDispatchModeLw();
        }
    }

    public void setFocusedAppLw(AppWindowToken appwindowtoken) {
        if(appwindowtoken == null) {
            mService.mInputManager.setFocusedApplication(null);
        } else {
            InputApplicationHandle inputapplicationhandle = appwindowtoken.mInputApplicationHandle;
            inputapplicationhandle.name = appwindowtoken.toString();
            inputapplicationhandle.dispatchingTimeoutNanos = appwindowtoken.inputDispatchingTimeoutNanos;
            mService.mInputManager.setFocusedApplication(inputapplicationhandle);
        }
    }

    public void setInputFocusLw(WindowState windowstate, boolean flag) {
        if(windowstate != mInputFocus) {
            if(windowstate != null && windowstate.canReceiveKeys())
                windowstate.mToken.paused = false;
            mInputFocus = windowstate;
            setUpdateInputWindowsNeededLw();
            if(flag)
                updateInputWindowsLw(false);
        }
    }

    public void setUpdateInputWindowsNeededLw() {
        mUpdateInputWindowsNeeded = true;
    }

    public void thawInputDispatchingLw() {
        if(mInputDispatchFrozen) {
            mInputDispatchFrozen = false;
            updateInputDispatchModeLw();
        }
    }

    public void updateInputWindowsLw(boolean flag) {
        if(flag || mUpdateInputWindowsNeeded) {
            mUpdateInputWindowsNeeded = false;
            ArrayList arraylist = mService.mWindows;
            boolean flag1;
            if(mService.mDragState != null)
                flag1 = true;
            else
                flag1 = false;
            if(flag1) {
                InputWindowHandle inputwindowhandle1 = mService.mDragState.mDragWindowHandle;
                int i;
                int j;
                if(inputwindowhandle1 != null)
                    addInputWindowHandleLw(inputwindowhandle1);
                else
                    Slog.w("WindowManager", "Drag is in progress but there is no drag window handle.");
            }
            i = mService.mFakeWindows.size();
            for(j = 0; j < i; j++)
                addInputWindowHandleLw(((FakeWindowImpl)mService.mFakeWindows.get(j)).mWindowHandle);

            int k = -1 + arraylist.size();
            while(k >= 0)  {
                WindowState windowstate = (WindowState)arraylist.get(k);
                android.view.InputChannel inputchannel = windowstate.mInputChannel;
                InputWindowHandle inputwindowhandle = windowstate.mInputWindowHandle;
                if(inputchannel != null && inputwindowhandle != null && !windowstate.mRemoved) {
                    int l = windowstate.mAttrs.flags;
                    int i1 = windowstate.mAttrs.type;
                    boolean flag2;
                    boolean flag3;
                    boolean flag4;
                    boolean flag5;
                    Rect rect;
                    if(windowstate == mInputFocus)
                        flag2 = true;
                    else
                        flag2 = false;
                    flag3 = windowstate.isVisibleLw();
                    if(windowstate == mService.mWallpaperTarget && i1 != 2004)
                        flag4 = true;
                    else
                        flag4 = false;
                    if(flag1 && flag3)
                        mService.mDragState.sendDragStartedIfNeededLw(windowstate);
                    inputwindowhandle.name = windowstate.toString();
                    inputwindowhandle.layoutParamsFlags = l;
                    inputwindowhandle.layoutParamsType = i1;
                    inputwindowhandle.dispatchingTimeoutNanos = windowstate.getInputDispatchingTimeoutNanos();
                    inputwindowhandle.visible = flag3;
                    inputwindowhandle.canReceiveKeys = windowstate.canReceiveKeys();
                    inputwindowhandle.hasFocus = flag2;
                    inputwindowhandle.hasWallpaper = flag4;
                    if(windowstate.mAppToken != null)
                        flag5 = ((WindowToken) (windowstate.mAppToken)).paused;
                    else
                        flag5 = false;
                    inputwindowhandle.paused = flag5;
                    inputwindowhandle.layer = windowstate.mLayer;
                    inputwindowhandle.ownerPid = windowstate.mSession.mPid;
                    inputwindowhandle.ownerUid = windowstate.mSession.mUid;
                    inputwindowhandle.inputFeatures = windowstate.mAttrs.inputFeatures;
                    rect = windowstate.mFrame;
                    inputwindowhandle.frameLeft = rect.left;
                    inputwindowhandle.frameTop = rect.top;
                    inputwindowhandle.frameRight = rect.right;
                    inputwindowhandle.frameBottom = rect.bottom;
                    if(windowstate.mGlobalScale != 1.0F)
                        inputwindowhandle.scaleFactor = 1.0F / windowstate.mGlobalScale;
                    else
                        inputwindowhandle.scaleFactor = 1.0F;
                    windowstate.getTouchableRegion(inputwindowhandle.touchableRegion);
                    addInputWindowHandleLw(inputwindowhandle);
                }
                k--;
            }
            mService.mInputManager.setInputWindows(mInputWindowHandles);
            clearInputWindowHandlesLw();
        }
    }

    public boolean waitForInputDevicesReady(long l) {
        Object obj = mInputDevicesReadyMonitor;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = mInputDevicesReady;
        Exception exception;
        boolean flag1;
        if(!flag)
            try {
                mInputDevicesReadyMonitor.wait(l);
            }
            catch(InterruptedException interruptedexception) { }
            finally {
                obj;
            }
        flag1 = mInputDevicesReady;
        obj;
        JVM INSTR monitorexit ;
        return flag1;
        throw exception;
    }

    private boolean mInputDevicesReady;
    private final Object mInputDevicesReadyMonitor = new Object();
    private boolean mInputDispatchEnabled;
    private boolean mInputDispatchFrozen;
    private WindowState mInputFocus;
    private int mInputWindowHandleCount;
    private InputWindowHandle mInputWindowHandles[];
    private final WindowManagerService mService;
    private boolean mUpdateInputWindowsNeeded;
}
