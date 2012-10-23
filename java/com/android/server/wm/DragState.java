// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.*;
import android.util.Slog;
import android.view.*;
import com.android.server.input.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, WindowState, Session, InputMonitor

class DragState {

    DragState(WindowManagerService windowmanagerservice, IBinder ibinder, Surface surface, int i, IBinder ibinder1) {
        mService = windowmanagerservice;
        mToken = ibinder;
        mSurface = surface;
        mFlags = i;
        mLocalWin = ibinder1;
        mNotifiedWindows = new ArrayList();
    }

    private WindowState getTouchedWinAtPointLw(float f, float f1) {
        WindowState windowstate;
        int i;
        int j;
        ArrayList arraylist;
        int k;
        windowstate = null;
        i = (int)f;
        j = (int)f1;
        arraylist = mService.mWindows;
        k = -1 + arraylist.size();
_L3:
        WindowState windowstate1;
        int l;
        if(k < 0)
            break; /* Loop/switch isn't completed */
        windowstate1 = (WindowState)arraylist.get(k);
        l = windowstate1.mAttrs.flags;
          goto _L1
_L5:
        k--;
        if(true) goto _L3; else goto _L2
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        if(!windowstate1.isVisibleLw() || (l & 0x10) != 0) goto _L5; else goto _L4
_L4:
        windowstate1.getTouchableRegion(mTmpRegion);
        int i1 = l & 0x28;
        if(!mTmpRegion.contains(i, j) && i1 != 0)
            continue; /* Loop/switch isn't completed */
        windowstate = windowstate1;
        break; /* Loop/switch isn't completed */
        if(true) goto _L5; else goto _L6
_L6:
        return windowstate;
    }

    private static DragEvent obtainDragEvent(WindowState windowstate, int i, float f, float f1, Object obj, ClipDescription clipdescription, ClipData clipdata, boolean flag) {
        float f2 = f - (float)windowstate.mFrame.left;
        float f3 = f1 - (float)windowstate.mFrame.top;
        if(windowstate.mEnforceSizeCompat) {
            f2 *= windowstate.mGlobalScale;
            f3 *= windowstate.mGlobalScale;
        }
        return DragEvent.obtain(i, f2, f3, obj, clipdescription, clipdata, flag);
    }

    private void sendDragStartedLw(WindowState windowstate, float f, float f1, ClipDescription clipdescription) {
_L2:
        return;
        if((1 & mFlags) == 0 && windowstate.mClient.asBinder() != mLocalWin || !mDragInProgress || !windowstate.isPotentialDragTarget()) goto _L2; else goto _L1
_L1:
        DragEvent dragevent = obtainDragEvent(windowstate, 1, f, f1, null, clipdescription, null, false);
        windowstate.mClient.dispatchDragEvent(dragevent);
        mNotifiedWindows.add(windowstate);
        if(Process.myPid() != windowstate.mSession.mPid)
            dragevent.recycle();
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        Slog.w("WindowManager", (new StringBuilder()).append("Unable to drag-start window ").append(windowstate).toString());
        if(Process.myPid() != windowstate.mSession.mPid)
            dragevent.recycle();
        if(true) goto _L2; else goto _L3
_L3:
        Exception exception;
        exception;
        if(Process.myPid() != windowstate.mSession.mPid)
            dragevent.recycle();
        throw exception;
    }

    void broadcastDragEndedLw() {
        DragEvent dragevent = DragEvent.obtain(4, 0.0F, 0.0F, null, null, null, mDragResult);
        for(Iterator iterator = mNotifiedWindows.iterator(); iterator.hasNext();) {
            WindowState windowstate = (WindowState)iterator.next();
            try {
                windowstate.mClient.dispatchDragEvent(dragevent);
            }
            catch(RemoteException remoteexception) {
                Slog.w("WindowManager", (new StringBuilder()).append("Unable to drag-end window ").append(windowstate).toString());
            }
        }

        mNotifiedWindows.clear();
        mDragInProgress = false;
        dragevent.recycle();
    }

    void broadcastDragStartedLw(float f, float f1) {
        ClipDescription clipdescription;
        int i;
        if(mData != null)
            clipdescription = mData.getDescription();
        else
            clipdescription = null;
        mDataDescription = clipdescription;
        mNotifiedWindows.clear();
        mDragInProgress = true;
        i = mService.mWindows.size();
        for(int j = 0; j < i; j++)
            sendDragStartedLw((WindowState)mService.mWindows.get(j), f, f1, mDataDescription);

    }

    void endDragLw() {
        mService.mDragState.broadcastDragEndedLw();
        mService.mDragState.unregister();
        mService.mInputMonitor.updateInputWindowsLw(true);
        mService.mDragState.reset();
        mService.mDragState = null;
    }

    int getDragLayerLw() {
        return 1000 + 10000 * mService.mPolicy.windowTypeToLayerLw(2016);
    }

    boolean notifyDropLw(float f, float f1) {
        WindowState windowstate = getTouchedWinAtPointLw(f, f1);
        if(windowstate != null) goto _L2; else goto _L1
_L1:
        boolean flag;
        mDragResult = false;
        flag = true;
_L4:
        return flag;
_L2:
        int i;
        IBinder ibinder;
        DragEvent dragevent;
        i = Process.myPid();
        ibinder = windowstate.mClient.asBinder();
        dragevent = obtainDragEvent(windowstate, 3, f, f1, null, null, mData, false);
        windowstate.mClient.dispatchDragEvent(dragevent);
        mService.mH.removeMessages(21, ibinder);
        android.os.Message message = mService.mH.obtainMessage(21, ibinder);
        mService.mH.sendMessageDelayed(message, 5000L);
        if(i != windowstate.mSession.mPid)
            dragevent.recycle();
        mToken = ibinder;
        flag = false;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        Slog.w("WindowManager", (new StringBuilder()).append("can't send drop notification to win ").append(windowstate).toString());
        flag = true;
        if(i != windowstate.mSession.mPid)
            dragevent.recycle();
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        if(i != windowstate.mSession.mPid)
            dragevent.recycle();
        throw exception;
    }

    void notifyMoveLw(float f, float f1) {
        int i;
        i = Process.myPid();
        Surface.openTransaction();
        mSurface.setPosition(f - mThumbOffsetX, f1 - mThumbOffsetY);
        Surface.closeTransaction();
        WindowState windowstate = getTouchedWinAtPointLw(f, f1);
        Exception exception;
        if(windowstate != null) {
            if((1 & mFlags) == 0 && windowstate.mClient.asBinder() != mLocalWin)
                windowstate = null;
            try {
                if(windowstate != mTargetWindow && mTargetWindow != null) {
                    DragEvent dragevent1 = obtainDragEvent(mTargetWindow, 6, f, f1, null, null, null, false);
                    mTargetWindow.mClient.dispatchDragEvent(dragevent1);
                    if(i != mTargetWindow.mSession.mPid)
                        dragevent1.recycle();
                }
                if(windowstate != null) {
                    DragEvent dragevent = obtainDragEvent(windowstate, 2, f, f1, null, null, null, false);
                    windowstate.mClient.dispatchDragEvent(dragevent);
                    if(i != windowstate.mSession.mPid)
                        dragevent.recycle();
                }
            }
            catch(RemoteException remoteexception) {
                Slog.w("WindowManager", "can't send drag notification to windows");
            }
            mTargetWindow = windowstate;
        }
        return;
        exception;
        Surface.closeTransaction();
        throw exception;
    }

    void register() {
        if(mClientChannel != null) {
            Slog.e("WindowManager", "Duplicate register of drag input channel");
        } else {
            InputChannel ainputchannel[] = InputChannel.openInputChannelPair("drag");
            mServerChannel = ainputchannel[0];
            mClientChannel = ainputchannel[1];
            mService.mInputManager.registerInputChannel(mServerChannel, null);
            WindowManagerService windowmanagerservice = mService;
            windowmanagerservice.getClass();
            mInputEventReceiver = new WindowManagerService.DragInputEventReceiver(windowmanagerservice, mClientChannel, mService.mH.getLooper());
            mDragApplicationHandle = new InputApplicationHandle(null);
            mDragApplicationHandle.name = "drag";
            mDragApplicationHandle.dispatchingTimeoutNanos = 0x12a05f200L;
            mDragWindowHandle = new InputWindowHandle(mDragApplicationHandle, null);
            mDragWindowHandle.name = "drag";
            mDragWindowHandle.inputChannel = mServerChannel;
            mDragWindowHandle.layer = getDragLayerLw();
            mDragWindowHandle.layoutParamsFlags = 0;
            mDragWindowHandle.layoutParamsType = 2016;
            mDragWindowHandle.dispatchingTimeoutNanos = 0x12a05f200L;
            mDragWindowHandle.visible = true;
            mDragWindowHandle.canReceiveKeys = false;
            mDragWindowHandle.hasFocus = true;
            mDragWindowHandle.hasWallpaper = false;
            mDragWindowHandle.paused = false;
            mDragWindowHandle.ownerPid = Process.myPid();
            mDragWindowHandle.ownerUid = Process.myUid();
            mDragWindowHandle.inputFeatures = 0;
            mDragWindowHandle.scaleFactor = 1.0F;
            mDragWindowHandle.touchableRegion.setEmpty();
            mDragWindowHandle.frameLeft = 0;
            mDragWindowHandle.frameTop = 0;
            mDragWindowHandle.frameRight = mService.mCurDisplayWidth;
            mDragWindowHandle.frameBottom = mService.mCurDisplayHeight;
            mService.pauseRotationLocked();
        }
    }

    void reset() {
        if(mSurface != null)
            mSurface.destroy();
        mSurface = null;
        mFlags = 0;
        mLocalWin = null;
        mToken = null;
        mData = null;
        mThumbOffsetY = 0.0F;
        mThumbOffsetX = 0.0F;
        mNotifiedWindows = null;
    }

    void sendDragStartedIfNeededLw(WindowState windowstate) {
        if(!mDragInProgress) goto _L2; else goto _L1
_L1:
        Iterator iterator = mNotifiedWindows.iterator();
_L5:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        if((WindowState)iterator.next() != windowstate) goto _L5; else goto _L2
_L2:
        return;
_L4:
        sendDragStartedLw(windowstate, mCurrentX, mCurrentY, mDataDescription);
        if(true) goto _L2; else goto _L6
_L6:
    }

    void unregister() {
        if(mClientChannel == null) {
            Slog.e("WindowManager", "Unregister of nonexistent drag input channel");
        } else {
            mService.mInputManager.unregisterInputChannel(mServerChannel);
            mInputEventReceiver.dispose();
            mInputEventReceiver = null;
            mClientChannel.dispose();
            mServerChannel.dispose();
            mClientChannel = null;
            mServerChannel = null;
            mDragWindowHandle = null;
            mDragApplicationHandle = null;
            mService.resumeRotationLocked();
        }
    }

    InputChannel mClientChannel;
    float mCurrentX;
    float mCurrentY;
    ClipData mData;
    ClipDescription mDataDescription;
    InputApplicationHandle mDragApplicationHandle;
    boolean mDragInProgress;
    boolean mDragResult;
    InputWindowHandle mDragWindowHandle;
    int mFlags;
    WindowManagerService.DragInputEventReceiver mInputEventReceiver;
    IBinder mLocalWin;
    ArrayList mNotifiedWindows;
    InputChannel mServerChannel;
    final WindowManagerService mService;
    Surface mSurface;
    WindowState mTargetWindow;
    float mThumbOffsetX;
    float mThumbOffsetY;
    private final Region mTmpRegion = new Region();
    IBinder mToken;
}
