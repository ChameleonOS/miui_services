// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.input;


public final class InputApplicationHandle {

    public InputApplicationHandle(Object obj) {
        appWindowToken = obj;
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

    public final Object appWindowToken;
    public long dispatchingTimeoutNanos;
    public String name;
    private int ptr;
}
