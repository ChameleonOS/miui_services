// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;


// Referenced classes of package com.android.server:
//            NativeDaemonEvent

public class NativeDaemonConnectorException extends Exception {

    public NativeDaemonConnectorException(String s) {
        super(s);
    }

    public NativeDaemonConnectorException(String s, NativeDaemonEvent nativedaemonevent) {
        super((new StringBuilder()).append("command '").append(s).append("' failed with '").append(nativedaemonevent).append("'").toString());
        mCmd = s;
        mEvent = nativedaemonevent;
    }

    public NativeDaemonConnectorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public String getCmd() {
        return mCmd;
    }

    public int getCode() {
        return mEvent.getCode();
    }

    public IllegalArgumentException rethrowAsParcelableException() {
        throw new IllegalStateException(getMessage(), this);
    }

    private String mCmd;
    private NativeDaemonEvent mEvent;
}
