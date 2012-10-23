// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.Region;
import android.os.Looper;
import android.os.Process;
import android.view.*;
import com.android.server.input.*;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService

public final class FakeWindowImpl
    implements android.view.WindowManagerPolicy.FakeWindow {

    public FakeWindowImpl(WindowManagerService windowmanagerservice, Looper looper, android.view.InputEventReceiver.Factory factory, String s, int i, int j, boolean flag, 
            boolean flag1, boolean flag2) {
        mService = windowmanagerservice;
        InputChannel ainputchannel[] = InputChannel.openInputChannelPair(s);
        mServerChannel = ainputchannel[0];
        mClientChannel = ainputchannel[1];
        mService.mInputManager.registerInputChannel(mServerChannel, null);
        mInputEventReceiver = factory.createInputEventReceiver(mClientChannel, looper);
        mApplicationHandle.name = s;
        mApplicationHandle.dispatchingTimeoutNanos = 0x12a05f200L;
        mWindowHandle = new InputWindowHandle(mApplicationHandle, null);
        mWindowHandle.name = s;
        mWindowHandle.inputChannel = mServerChannel;
        mWindowLayer = getLayerLw(i);
        mWindowHandle.layer = mWindowLayer;
        mWindowHandle.layoutParamsFlags = j;
        mWindowHandle.layoutParamsType = i;
        mWindowHandle.dispatchingTimeoutNanos = 0x12a05f200L;
        mWindowHandle.visible = true;
        mWindowHandle.canReceiveKeys = flag;
        mWindowHandle.hasFocus = flag1;
        mWindowHandle.hasWallpaper = false;
        mWindowHandle.paused = false;
        mWindowHandle.ownerPid = Process.myPid();
        mWindowHandle.ownerUid = Process.myUid();
        mWindowHandle.inputFeatures = 0;
        mWindowHandle.scaleFactor = 1.0F;
        mTouchFullscreen = flag2;
    }

    private int getLayerLw(int i) {
        return 1000 + 10000 * mService.mPolicy.windowTypeToLayerLw(i);
    }

    public void dismiss() {
        java.util.HashMap hashmap = mService.mWindowMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mService.removeFakeWindowLocked(this)) {
            mInputEventReceiver.dispose();
            mService.mInputManager.unregisterInputChannel(mServerChannel);
            mClientChannel.dispose();
            mServerChannel.dispose();
        }
        return;
    }

    void layout(int i, int j) {
        if(mTouchFullscreen)
            mWindowHandle.touchableRegion.set(0, 0, i, j);
        else
            mWindowHandle.touchableRegion.setEmpty();
        mWindowHandle.frameLeft = 0;
        mWindowHandle.frameTop = 0;
        mWindowHandle.frameRight = i;
        mWindowHandle.frameBottom = j;
    }

    final InputApplicationHandle mApplicationHandle = new InputApplicationHandle(null);
    final InputChannel mClientChannel;
    final InputEventReceiver mInputEventReceiver;
    final InputChannel mServerChannel;
    final WindowManagerService mService;
    boolean mTouchFullscreen;
    final InputWindowHandle mWindowHandle;
    final int mWindowLayer;
}
