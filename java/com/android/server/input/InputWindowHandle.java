// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.input;

import android.graphics.Region;
import android.view.InputChannel;

// Referenced classes of package com.android.server.input:
//            InputApplicationHandle

public final class InputWindowHandle {

    public InputWindowHandle(InputApplicationHandle inputapplicationhandle, Object obj) {
        inputApplicationHandle = inputapplicationhandle;
        windowState = obj;
    }

    private native void nativeDispose();

    protected void finalize() throws Throwable {
        nativeDispose();
        super.finalize();
        return;
        Exception exception;
        exception;
        super.finalize();
        throw exception;
    }

    public boolean canReceiveKeys;
    public long dispatchingTimeoutNanos;
    public int frameBottom;
    public int frameLeft;
    public int frameRight;
    public int frameTop;
    public boolean hasFocus;
    public boolean hasWallpaper;
    public final InputApplicationHandle inputApplicationHandle;
    public InputChannel inputChannel;
    public int inputFeatures;
    public int layer;
    public int layoutParamsFlags;
    public int layoutParamsType;
    public String name;
    public int ownerPid;
    public int ownerUid;
    public boolean paused;
    private int ptr;
    public float scaleFactor;
    public final Region touchableRegion = new Region();
    public boolean visible;
    public final Object windowState;
}
