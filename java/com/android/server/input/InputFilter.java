// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.input;

import android.os.*;
import android.view.InputEvent;
import android.view.InputEventConsistencyVerifier;

public abstract class InputFilter {
    static interface Host {

        public abstract void sendInputEvent(InputEvent inputevent, int i);
    }

    private final class H extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 3: default 32
        //                       1 33
        //                       2 98
        //                       3 131;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L2:
            mHost = (Host)message.obj;
            if(mInboundInputEventConsistencyVerifier != null)
                mInboundInputEventConsistencyVerifier.reset();
            if(mOutboundInputEventConsistencyVerifier != null)
                mOutboundInputEventConsistencyVerifier.reset();
            onInstalled();
              goto _L1
_L3:
            onUninstalled();
            mHost = null;
              goto _L1
            Exception exception1;
            exception1;
            mHost = null;
            throw exception1;
_L4:
            InputEvent inputevent = (InputEvent)message.obj;
            if(mInboundInputEventConsistencyVerifier != null)
                mInboundInputEventConsistencyVerifier.onInputEvent(inputevent, 0);
            onInputEvent(inputevent, message.arg1);
            inputevent.recycle();
              goto _L1
            Exception exception;
            exception;
            inputevent.recycle();
            throw exception;
        }

        final InputFilter this$0;

        public H(Looper looper) {
            this$0 = InputFilter.this;
            super(looper);
        }
    }


    public InputFilter(Looper looper) {
        InputEventConsistencyVerifier inputeventconsistencyverifier = null;
        super();
        InputEventConsistencyVerifier inputeventconsistencyverifier1;
        if(InputEventConsistencyVerifier.isInstrumentationEnabled())
            inputeventconsistencyverifier1 = new InputEventConsistencyVerifier(this, 1, "InputFilter#InboundInputEventConsistencyVerifier");
        else
            inputeventconsistencyverifier1 = null;
        mInboundInputEventConsistencyVerifier = inputeventconsistencyverifier1;
        if(InputEventConsistencyVerifier.isInstrumentationEnabled())
            inputeventconsistencyverifier = new InputEventConsistencyVerifier(this, 1, "InputFilter#OutboundInputEventConsistencyVerifier");
        mOutboundInputEventConsistencyVerifier = inputeventconsistencyverifier;
        mH = new H(looper);
    }

    final void filterInputEvent(InputEvent inputevent, int i) {
        mH.obtainMessage(3, i, 0, inputevent).sendToTarget();
    }

    final void install(Host host) {
        mH.obtainMessage(1, host).sendToTarget();
    }

    public void onInputEvent(InputEvent inputevent, int i) {
        sendInputEvent(inputevent, i);
    }

    public void onInstalled() {
    }

    public void onUninstalled() {
    }

    public void sendInputEvent(InputEvent inputevent, int i) {
        if(inputevent == null)
            throw new IllegalArgumentException("event must not be null");
        if(mHost == null)
            throw new IllegalStateException("Cannot send input event because the input filter is not installed.");
        if(mOutboundInputEventConsistencyVerifier != null)
            mOutboundInputEventConsistencyVerifier.onInputEvent(inputevent, 0);
        mHost.sendInputEvent(inputevent, i);
    }

    final void uninstall() {
        mH.obtainMessage(2).sendToTarget();
    }

    private static final int MSG_INPUT_EVENT = 3;
    private static final int MSG_INSTALL = 1;
    private static final int MSG_UNINSTALL = 2;
    private final H mH;
    private Host mHost;
    private final InputEventConsistencyVerifier mInboundInputEventConsistencyVerifier;
    private final InputEventConsistencyVerifier mOutboundInputEventConsistencyVerifier;


/*
    static Host access$002(InputFilter inputfilter, Host host) {
        inputfilter.mHost = host;
        return host;
    }

*/


}
