// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.*;
import android.content.pm.*;
import android.net.Uri;
import android.os.*;
import android.util.Slog;
import android.util.SparseArray;
import java.util.HashSet;

public class ClipboardService extends android.content.IClipboard.Stub {
    private class PerUserClipboard {

        final HashSet activePermissionOwners = new HashSet();
        ClipData primaryClip;
        final RemoteCallbackList primaryClipListeners = new RemoteCallbackList();
        final ClipboardService this$0;
        final int userId;

        PerUserClipboard(int i) {
            this$0 = ClipboardService.this;
            super();
            userId = i;
        }
    }


    public ClipboardService(Context context) {
        IBinder ibinder;
        mClipboards = new SparseArray();
        mContext = context;
        mAm = ActivityManagerNative.getDefault();
        mPm = context.getPackageManager();
        ibinder = null;
        IBinder ibinder1 = mAm.newUriPermissionOwner("clipboard");
        ibinder = ibinder1;
_L2:
        mPermissionOwner = ibinder;
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.USER_REMOVED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                if("android.intent.action.USER_REMOVED".equals(intent.getAction()))
                    removeClipboard(intent.getIntExtra("android.intent.extra.user_id", 0));
            }

            final ClipboardService this$0;

             {
                this$0 = ClipboardService.this;
                super();
            }
        }, intentfilter);
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("clipboard", "AM dead", remoteexception);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private final void addActiveOwnerLocked(int i, String s) {
        try {
            if(!UserId.isSameApp(mPm.getPackageInfo(s, 0).applicationInfo.uid, i))
                throw new SecurityException((new StringBuilder()).append("Calling uid ").append(i).append(" does not own package ").append(s).toString());
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package ").append(s).toString(), namenotfoundexception);
        }
        PerUserClipboard peruserclipboard = getClipboard();
        if(peruserclipboard.primaryClip != null && !peruserclipboard.activePermissionOwners.contains(s)) {
            int j = peruserclipboard.primaryClip.getItemCount();
            for(int k = 0; k < j; k++)
                grantItemLocked(peruserclipboard.primaryClip.getItemAt(k), s);

            peruserclipboard.activePermissionOwners.add(s);
        }
    }

    private final void checkDataOwnerLocked(ClipData clipdata, int i) {
        int j = clipdata.getItemCount();
        for(int k = 0; k < j; k++)
            checkItemOwnerLocked(clipdata.getItemAt(k), i);

    }

    private final void checkItemOwnerLocked(android.content.ClipData.Item item, int i) {
        if(item.getUri() != null)
            checkUriOwnerLocked(item.getUri(), i);
        Intent intent = item.getIntent();
        if(intent != null && intent.getData() != null)
            checkUriOwnerLocked(intent.getData(), i);
    }

    private final void checkUriOwnerLocked(Uri uri, int i) {
        if("content".equals(uri.getScheme())) {
            long l = Binder.clearCallingIdentity();
            Exception exception;
            try {
                mAm.checkGrantUriPermission(i, null, uri, 1);
            }
            catch(RemoteException remoteexception) { }
            finally {
                Binder.restoreCallingIdentity(l);
            }
            Binder.restoreCallingIdentity(l);
        }
        return;
        throw exception;
    }

    private final void clearActiveOwnersLocked() {
        PerUserClipboard peruserclipboard = getClipboard();
        peruserclipboard.activePermissionOwners.clear();
        if(peruserclipboard.primaryClip != null) {
            int i = peruserclipboard.primaryClip.getItemCount();
            int j = 0;
            while(j < i)  {
                revokeItemLocked(peruserclipboard.primaryClip.getItemAt(j));
                j++;
            }
        }
    }

    private PerUserClipboard getClipboard() {
        return getClipboard(UserId.getCallingUserId());
    }

    private PerUserClipboard getClipboard(int i) {
        SparseArray sparsearray = mClipboards;
        sparsearray;
        JVM INSTR monitorenter ;
        Slog.i("ClipboardService", (new StringBuilder()).append("Got clipboard for user=").append(i).toString());
        PerUserClipboard peruserclipboard = (PerUserClipboard)mClipboards.get(i);
        if(peruserclipboard == null) {
            peruserclipboard = new PerUserClipboard(i);
            mClipboards.put(i, peruserclipboard);
        }
        return peruserclipboard;
    }

    private final void grantItemLocked(android.content.ClipData.Item item, String s) {
        if(item.getUri() != null)
            grantUriLocked(item.getUri(), s);
        Intent intent = item.getIntent();
        if(intent != null && intent.getData() != null)
            grantUriLocked(intent.getData(), s);
    }

    private final void grantUriLocked(Uri uri, String s) {
        long l = Binder.clearCallingIdentity();
        Exception exception;
        try {
            mAm.grantUriPermissionFromOwner(mPermissionOwner, Process.myUid(), s, uri, 1);
        }
        catch(RemoteException remoteexception) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        return;
        throw exception;
    }

    private void removeClipboard(int i) {
        SparseArray sparsearray = mClipboards;
        sparsearray;
        JVM INSTR monitorenter ;
        mClipboards.remove(i);
        return;
    }

    private final void revokeItemLocked(android.content.ClipData.Item item) {
        if(item.getUri() != null)
            revokeUriLocked(item.getUri());
        Intent intent = item.getIntent();
        if(intent != null && intent.getData() != null)
            revokeUriLocked(intent.getData());
    }

    private final void revokeUriLocked(Uri uri) {
        long l = Binder.clearCallingIdentity();
        Exception exception;
        try {
            mAm.revokeUriPermissionFromOwner(mPermissionOwner, uri, 3);
        }
        catch(RemoteException remoteexception) { }
        finally {
            Binder.restoreCallingIdentity(l);
        }
        Binder.restoreCallingIdentity(l);
        return;
        throw exception;
    }

    public void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener ionprimaryclipchangedlistener) {
        this;
        JVM INSTR monitorenter ;
        getClipboard().primaryClipListeners.register(ionprimaryclipchangedlistener);
        return;
    }

    public ClipData getPrimaryClip(String s) {
        this;
        JVM INSTR monitorenter ;
        addActiveOwnerLocked(Binder.getCallingUid(), s);
        ClipData clipdata = getClipboard().primaryClip;
        return clipdata;
    }

    public ClipDescription getPrimaryClipDescription() {
        this;
        JVM INSTR monitorenter ;
        PerUserClipboard peruserclipboard = getClipboard();
        ClipDescription clipdescription;
        if(peruserclipboard.primaryClip != null)
            clipdescription = peruserclipboard.primaryClip.getDescription();
        else
            clipdescription = null;
        return clipdescription;
    }

    public boolean hasClipboardText() {
        boolean flag = false;
        this;
        JVM INSTR monitorenter ;
        PerUserClipboard peruserclipboard = getClipboard();
        if(peruserclipboard.primaryClip != null) {
            CharSequence charsequence = peruserclipboard.primaryClip.getItemAt(0).getText();
            if(charsequence != null && charsequence.length() > 0)
                flag = true;
        }
        return flag;
    }

    public boolean hasPrimaryClip() {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        if(getClipboard().primaryClip != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j) throws RemoteException {
        boolean flag;
        try {
            flag = super.onTransact(i, parcel, parcel1, j);
        }
        catch(RuntimeException runtimeexception) {
            Slog.w("clipboard", "Exception: ", runtimeexception);
            throw runtimeexception;
        }
        return flag;
    }

    public void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener ionprimaryclipchangedlistener) {
        this;
        JVM INSTR monitorenter ;
        getClipboard().primaryClipListeners.unregister(ionprimaryclipchangedlistener);
        return;
    }

    public void setPrimaryClip(ClipData clipdata) {
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        if(clipdata != null && clipdata.getItemCount() <= 0)
            throw new IllegalArgumentException("No items");
        if(true)
            break MISSING_BLOCK_LABEL_31;
        JVM INSTR monitorexit ;
        throw exception;
        PerUserClipboard peruserclipboard;
        int i;
        checkDataOwnerLocked(clipdata, Binder.getCallingUid());
        clearActiveOwnersLocked();
        peruserclipboard = getClipboard();
        peruserclipboard.primaryClip = clipdata;
        i = peruserclipboard.primaryClipListeners.beginBroadcast();
        int j = 0;
        while(j < i)  {
            try {
                ((IOnPrimaryClipChangedListener)peruserclipboard.primaryClipListeners.getBroadcastItem(j)).dispatchPrimaryClipChanged();
            }
            catch(RemoteException remoteexception) { }
            finally {
                this;
            }
            j++;
        }
        peruserclipboard.primaryClipListeners.finishBroadcast();
        this;
        JVM INSTR monitorexit ;
    }

    private static final String TAG = "ClipboardService";
    private final IActivityManager mAm;
    private SparseArray mClipboards;
    private final Context mContext;
    private final IBinder mPermissionOwner;
    private final PackageManager mPm;

}
