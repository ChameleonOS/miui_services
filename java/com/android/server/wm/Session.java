// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.ClipData;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.*;
import android.util.Slog;
import android.view.*;
import com.android.internal.view.*;
import com.android.server.input.InputManagerService;
import java.io.PrintWriter;
import java.util.HashSet;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, DragState, InputMonitor, WindowState

final class Session extends android.view.IWindowSession.Stub
    implements android.os.IBinder.DeathRecipient {

    public Session(WindowManagerService windowmanagerservice, IInputMethodClient iinputmethodclient, IInputContext iinputcontext) {
        long l;
        mNumWindow = 0;
        mClientDead = false;
        mService = windowmanagerservice;
        mClient = iinputmethodclient;
        mInputContext = iinputcontext;
        mUid = Binder.getCallingUid();
        mPid = Binder.getCallingPid();
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Session{");
        stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringbuilder.append(" uid ");
        stringbuilder.append(mUid);
        stringbuilder.append("}");
        mStringName = stringbuilder.toString();
        synchronized(mService.mWindowMap) {
            if(mService.mInputMethodManager == null && mService.mHaveInputMethods) {
                IBinder ibinder = ServiceManager.getService("input_method");
                mService.mInputMethodManager = com.android.internal.view.IInputMethodManager.Stub.asInterface(ibinder);
            }
        }
        l = Binder.clearCallingIdentity();
        if(mService.mInputMethodManager == null) goto _L2; else goto _L1
_L1:
        mService.mInputMethodManager.addClient(iinputmethodclient, iinputcontext, mUid, mPid);
_L3:
        iinputmethodclient.asBinder().linkToDeath(this, 0);
        Binder.restoreCallingIdentity(l);
_L5:
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        iinputmethodclient.setUsingInputMethod(false);
          goto _L3
        RemoteException remoteexception;
        remoteexception;
        Exception exception1;
        try {
            if(mService.mInputMethodManager != null)
                mService.mInputMethodManager.removeClient(iinputmethodclient);
        }
        catch(RemoteException remoteexception1) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        if(true) goto _L5; else goto _L4
_L4:
        throw exception1;
          goto _L3
    }

    public int add(IWindow iwindow, int i, android.view.WindowManager.LayoutParams layoutparams, int j, Rect rect, InputChannel inputchannel) {
        return mService.addWindow(this, iwindow, i, layoutparams, j, rect, inputchannel);
    }

    public int addWithoutInputChannel(IWindow iwindow, int i, android.view.WindowManager.LayoutParams layoutparams, int j, Rect rect) {
        return mService.addWindow(this, iwindow, i, layoutparams, j, rect, null);
    }

    public void binderDied() {
        java.util.HashMap hashmap;
        try {
            if(mService.mInputMethodManager != null)
                mService.mInputMethodManager.removeClient(mClient);
        }
        catch(RemoteException remoteexception) { }
        hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mClient.asBinder().unlinkToDeath(this, 0);
        mClientDead = true;
        killSessionLocked();
        return;
    }

    public void dragRecipientEntered(IWindow iwindow) {
    }

    public void dragRecipientExited(IWindow iwindow) {
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("mNumWindow=");
        printwriter.print(mNumWindow);
        printwriter.print(" mClientDead=");
        printwriter.print(mClientDead);
        printwriter.print(" mSurfaceSession=");
        printwriter.println(mSurfaceSession);
    }

    public void finishDrawing(IWindow iwindow) {
        mService.finishDrawingWindow(this, iwindow);
    }

    public void getDisplayFrame(IWindow iwindow, Rect rect) {
        mService.getWindowDisplayFrame(this, iwindow, rect);
    }

    public boolean getInTouchMode() {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = mService.mInTouchMode;
        return flag;
    }

    void killSessionLocked() {
        if(mNumWindow <= 0 && mClientDead) {
            mService.mSessions.remove(this);
            if(mSurfaceSession != null) {
                try {
                    mSurfaceSession.kill();
                }
                catch(Exception exception) {
                    Slog.w("WindowManager", (new StringBuilder()).append("Exception thrown when killing surface session ").append(mSurfaceSession).append(" in session ").append(this).append(": ").append(exception.toString()).toString());
                }
                mSurfaceSession = null;
            }
        }
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j) throws RemoteException {
        boolean flag;
        try {
            flag = super.onTransact(i, parcel, parcel1, j);
        }
        catch(RuntimeException runtimeexception) {
            if(!(runtimeexception instanceof SecurityException))
                Slog.e("WindowManager", "Window Session Crash", runtimeexception);
            throw runtimeexception;
        }
        return flag;
    }

    public boolean outOfMemory(IWindow iwindow) {
        return mService.outOfMemoryWindow(this, iwindow);
    }

    public void performDeferredDestroy(IWindow iwindow) {
        mService.performDeferredDestroyWindow(this, iwindow);
    }

    public boolean performDrag(IWindow iwindow, IBinder ibinder, float f, float f1, float f2, float f3, ClipData clipdata) {
        boolean flag = false;
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mService.mDragState == null) {
            Slog.w("WindowManager", "No drag prepared");
            throw new IllegalStateException("performDrag() without prepareDrag()");
        }
        break MISSING_BLOCK_LABEL_53;
        Exception exception;
        exception;
        throw exception;
        WindowState windowstate;
        if(ibinder != mService.mDragState.mToken) {
            Slog.w("WindowManager", "Performing mismatched drag");
            throw new IllegalStateException("performDrag() does not match prepareDrag()");
        }
        windowstate = mService.windowForClientLocked(null, iwindow, false);
        if(windowstate != null)
            break MISSING_BLOCK_LABEL_136;
        Slog.w("WindowManager", (new StringBuilder()).append("Bad requesting window ").append(iwindow).toString());
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_404;
        mService.mH.removeMessages(20, iwindow.asBinder());
        mService.mDragState.register();
        mService.mInputMonitor.updateInputWindowsLw(true);
        if(mService.mInputManager.transferTouchFocus(windowstate.mInputChannel, mService.mDragState.mServerChannel))
            break MISSING_BLOCK_LABEL_247;
        Slog.e("WindowManager", "Unable to transfer touch focus");
        mService.mDragState.unregister();
        mService.mDragState = null;
        mService.mInputMonitor.updateInputWindowsLw(true);
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_404;
        Surface surface;
        mService.mDragState.mData = clipdata;
        mService.mDragState.mCurrentX = f;
        mService.mDragState.mCurrentY = f1;
        mService.mDragState.broadcastDragStartedLw(f, f1);
        mService.mDragState.mThumbOffsetX = f2;
        mService.mDragState.mThumbOffsetY = f3;
        surface = mService.mDragState.mSurface;
        Surface.openTransaction();
        float f4;
        float f5;
        f4 = f - f2;
        f5 = f1 - f3;
        surface.setPosition(f4, f5);
        surface.setAlpha(0.7071F);
        surface.setLayer(mService.mDragState.getDragLayerLw());
        surface.show();
        Surface.closeTransaction();
        hashmap;
        JVM INSTR monitorexit ;
        flag = true;
        break MISSING_BLOCK_LABEL_404;
        Exception exception1;
        exception1;
        Surface.closeTransaction();
        throw exception1;
        return flag;
    }

    public boolean performHapticFeedback(IWindow iwindow, int i, boolean flag) {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        boolean flag1 = mService.mPolicy.performHapticFeedbackLw(mService.windowForClientLocked(this, iwindow, true), i, flag);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        return flag1;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public IBinder prepareDrag(IWindow iwindow, int i, int j, int k, Surface surface) {
        return mService.prepareDragSurface(iwindow, mSurfaceSession, i, j, k, surface);
    }

    public int relayout(IWindow iwindow, int i, android.view.WindowManager.LayoutParams layoutparams, int j, int k, int l, int i1, 
            Rect rect, Rect rect1, Rect rect2, Configuration configuration, Surface surface) {
        return mService.relayoutWindow(this, iwindow, i, layoutparams, j, k, l, i1, rect, rect1, rect2, configuration, surface);
    }

    public void remove(IWindow iwindow) {
        mService.removeWindow(this, iwindow);
    }

    public void reportDropResult(IWindow iwindow, boolean flag) {
        IBinder ibinder = iwindow.asBinder();
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        if(mService.mDragState != null) goto _L2; else goto _L1
_L1:
        Slog.w("WindowManager", "Drop result given but no drag in progress");
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
_L5:
        return;
_L2:
        if(mService.mDragState.mToken != ibinder) {
            Slog.w("WindowManager", (new StringBuilder()).append("Invalid drop-result claim by ").append(iwindow).toString());
            throw new IllegalStateException("reportDropResult() by non-recipient");
        }
        break MISSING_BLOCK_LABEL_121;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
        mService.mH.removeMessages(21, iwindow.asBinder());
        if(mService.windowForClientLocked(null, iwindow, false) != null) goto _L4; else goto _L3
_L3:
        Slog.w("WindowManager", (new StringBuilder()).append("Bad result-reporting window ").append(iwindow).toString());
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L4:
        mService.mDragState.mDragResult = flag;
        mService.mDragState.endDragLw();
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
    }

    public Bundle sendWallpaperCommand(IBinder ibinder, String s, int i, int j, int k, Bundle bundle, boolean flag) {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        Bundle bundle1 = mService.sendWindowWallpaperCommandLocked(mService.windowForClientLocked(this, ibinder, true), s, i, j, k, bundle, flag);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        return bundle1;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public void setInTouchMode(boolean flag) {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        mService.mInTouchMode = flag;
        return;
    }

    public void setInsets(IWindow iwindow, int i, Rect rect, Rect rect1, Region region) {
        mService.setInsetsWindow(this, iwindow, i, rect, rect1, region);
    }

    public void setTransparentRegion(IWindow iwindow, Region region) {
        mService.setTransparentRegionWindow(this, iwindow, region);
    }

    public void setWallpaperPosition(IBinder ibinder, float f, float f1, float f2, float f3) {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        mService.setWindowWallpaperPositionLocked(mService.windowForClientLocked(this, ibinder, true), f, f1, f2, f3);
        Binder.restoreCallingIdentity(l);
        hashmap;
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

    public String toString() {
        return mStringName;
    }

    public void wallpaperCommandComplete(IBinder ibinder, Bundle bundle) {
        mService.wallpaperCommandComplete(ibinder, bundle);
    }

    public void wallpaperOffsetsComplete(IBinder ibinder) {
        mService.wallpaperOffsetsComplete(ibinder);
    }

    void windowAddedLocked() {
        if(mSurfaceSession == null) {
            mSurfaceSession = new SurfaceSession();
            mService.mSessions.add(this);
        }
        mNumWindow = 1 + mNumWindow;
    }

    void windowRemovedLocked() {
        mNumWindow = -1 + mNumWindow;
        killSessionLocked();
    }

    final IInputMethodClient mClient;
    boolean mClientDead;
    final IInputContext mInputContext;
    int mNumWindow;
    final int mPid;
    final WindowManagerService mService;
    final String mStringName;
    SurfaceSession mSurfaceSession;
    final int mUid;
}
