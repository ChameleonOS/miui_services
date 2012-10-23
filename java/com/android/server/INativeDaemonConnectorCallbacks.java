// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;


interface INativeDaemonConnectorCallbacks {

    public abstract void onDaemonConnected();

    public abstract boolean onEvent(int i, String s, String as[]);
}
