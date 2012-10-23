// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class UpdateLockService extends android.os.IUpdateLock.Stub {
    class LockWatcher extends TokenWatcher {

        public void acquired() {
            sendLockChangedBroadcast(false);
        }

        public void released() {
            sendLockChangedBroadcast(true);
        }

        final UpdateLockService this$0;

        LockWatcher(Handler handler, String s) {
            this$0 = UpdateLockService.this;
            super(handler, s);
        }
    }


    UpdateLockService(Context context) {
        mContext = context;
        mLocks = new LockWatcher(new Handler(), "UpdateLocks");
        sendLockChangedBroadcast(true);
    }

    private String makeTag(String s) {
        return (new StringBuilder()).append("{tag=").append(s).append(" uid=").append(Binder.getCallingUid()).append(" pid=").append(Binder.getCallingPid()).append('}').toString();
    }

    public void acquireUpdateLock(IBinder ibinder, String s) throws RemoteException {
        mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_LOCK", "acquireUpdateLock");
        mLocks.acquire(ibinder, makeTag(s));
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0)
            printwriter.println((new StringBuilder()).append("Permission Denial: can't dump update lock service from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
        else
            mLocks.dump(printwriter);
    }

    public void releaseUpdateLock(IBinder ibinder) throws RemoteException {
        mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_LOCK", "releaseUpdateLock");
        mLocks.release(ibinder);
    }

    void sendLockChangedBroadcast(boolean flag) {
        long l = Binder.clearCallingIdentity();
        Intent intent = (new Intent("android.os.UpdateLock.UPDATE_LOCK_CHANGED")).putExtra("nowisconvenient", flag).putExtra("timestamp", System.currentTimeMillis()).addFlags(0x8000000);
        mContext.sendStickyBroadcast(intent);
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    static final boolean DEBUG = false;
    static final String PERMISSION = "android.permission.UPDATE_LOCK";
    static final String TAG = "UpdateLockService";
    Context mContext;
    LockWatcher mLocks;
}
