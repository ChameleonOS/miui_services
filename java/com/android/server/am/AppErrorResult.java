// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;


class AppErrorResult {

    AppErrorResult() {
        mHasResult = false;
    }

    public int get() {
        this;
        JVM INSTR monitorenter ;
_L3:
        boolean flag = mHasResult;
        if(flag) goto _L2; else goto _L1
_L1:
        Exception exception;
        try {
            wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally { }
        if(true) goto _L3; else goto _L2
_L2:
        this;
        JVM INSTR monitorexit ;
        return mResult;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void set(int i) {
        this;
        JVM INSTR monitorenter ;
        mHasResult = true;
        mResult = i;
        notifyAll();
        return;
    }

    boolean mHasResult;
    int mResult;
}
