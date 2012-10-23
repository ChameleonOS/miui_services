// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.content.res.Resources;
import android.os.*;
import android.util.Slog;
import com.android.internal.statusbar.*;
import com.android.server.wm.WindowManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;

public class StatusBarManagerService extends com.android.internal.statusbar.IStatusBarService.Stub
    implements com.android.server.wm.WindowManagerService.OnHardKeyboardStatusChangeListener {
    public static interface NotificationCallbacks {

        public abstract void onClearAll();

        public abstract void onNotificationClear(String s, String s1, int i);

        public abstract void onNotificationClick(String s, String s1, int i);

        public abstract void onNotificationError(String s, String s1, int i, int j, int k, String s2);

        public abstract void onPanelRevealed();

        public abstract void onSetDisabled(int i);
    }

    private class DisableRecord
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            Slog.i("StatusBarManagerService", (new StringBuilder()).append("binder died for pkg=").append(pkg).toString());
            disable(0, token, pkg);
            token.unlinkToDeath(this, 0);
        }

        String pkg;
        final StatusBarManagerService this$0;
        IBinder token;
        int what;

        private DisableRecord() {
            this$0 = StatusBarManagerService.this;
            super();
        }

    }


    public StatusBarManagerService(Context context, WindowManagerService windowmanagerservice) {
        mHandler = new Handler();
        mIcons = new StatusBarIconList();
        mNotifications = new HashMap();
        mDisableRecords = new ArrayList();
        mSysUiVisToken = new Binder();
        mDisabled = 0;
        mLock = new Object();
        mSystemUiVisibility = 0;
        mMenuVisible = false;
        mImeWindowVis = 0;
        mImeToken = null;
        mBroadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                String s = intent.getAction();
                if("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(s) || "android.intent.action.SCREEN_OFF".equals(s))
                    collapse();
            }

            final StatusBarManagerService this$0;

             {
                this$0 = StatusBarManagerService.this;
                super();
            }
        };
        mContext = context;
        mWindowManager = windowmanagerservice;
        mWindowManager.setOnHardKeyboardStatusChangeListener(this);
        Resources resources = context.getResources();
        mIcons.defineSlots(resources.getStringArray(0x1070011));
    }

    private void disableLocked(int i, IBinder ibinder, String s) {
        final int net;
        manageDisableListLocked(i, ibinder, s);
        net = gatherDisableActionsLocked();
        if(net == mDisabled)
            break MISSING_BLOCK_LABEL_64;
        mDisabled = net;
        mHandler.post(new Runnable() {

            public void run() {
                mNotificationCallbacks.onSetDisabled(net);
            }

            final StatusBarManagerService this$0;
            final int val$net;

             {
                this$0 = StatusBarManagerService.this;
                net = i;
                super();
            }
        });
        if(mBar == null)
            break MISSING_BLOCK_LABEL_64;
        mBar.disable(net);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void enforceExpandStatusBar() {
        mContext.enforceCallingOrSelfPermission("android.permission.EXPAND_STATUS_BAR", "StatusBarManagerService");
    }

    private void enforceStatusBar() {
        mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR", "StatusBarManagerService");
    }

    private void enforceStatusBarService() {
        mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "StatusBarManagerService");
    }

    private void updateUiVisibilityLocked(final int vis, final int mask) {
        if(mSystemUiVisibility != vis) {
            mSystemUiVisibility = vis;
            mHandler.post(new Runnable() {

                public void run() {
                    if(mBar == null)
                        break MISSING_BLOCK_LABEL_30;
                    mBar.setSystemUiVisibility(vis, mask);
_L2:
                    return;
                    RemoteException remoteexception;
                    remoteexception;
                    if(true) goto _L2; else goto _L1
_L1:
                }

                final StatusBarManagerService this$0;
                final int val$mask;
                final int val$vis;

             {
                this$0 = StatusBarManagerService.this;
                vis = i;
                mask = j;
                super();
            }
            });
        }
    }

    public IBinder addNotification(StatusBarNotification statusbarnotification) {
        HashMap hashmap = mNotifications;
        hashmap;
        JVM INSTR monitorenter ;
        Binder binder;
        IStatusBar istatusbar;
        binder = new Binder();
        mNotifications.put(binder, statusbarnotification);
        istatusbar = mBar;
        Exception exception;
        if(istatusbar != null)
            try {
                mBar.addNotification(binder, statusbarnotification);
            }
            catch(RemoteException remoteexception) { }
            finally {
                hashmap;
            }
        hashmap;
        JVM INSTR monitorexit ;
        return binder;
        throw exception;
    }

    public void cancelPreloadRecentApps() {
        if(mBar == null)
            break MISSING_BLOCK_LABEL_16;
        mBar.cancelPreloadRecentApps();
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void collapse() {
        enforceExpandStatusBar();
        if(mBar == null)
            break MISSING_BLOCK_LABEL_20;
        mBar.animateCollapse();
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void disable(int i, IBinder ibinder, String s) {
        enforceStatusBar();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        disableLocked(i, ibinder, s);
        return;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump StatusBar from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L6:
        return;
_L2:
        synchronized(mIcons) {
            mIcons.dump(printwriter);
        }
        HashMap hashmap = mNotifications;
        hashmap;
        JVM INSTR monitorenter ;
        int i = 0;
        Iterator iterator;
        printwriter.println("Notification list:");
        iterator = mNotifications.entrySet().iterator();
_L3:
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_179;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(i);
        aobj[1] = ((StatusBarNotification)entry.getValue()).toString();
        printwriter.printf("  %2d: %s\n", aobj);
        i++;
          goto _L3
        exception;
        statusbariconlist;
        JVM INSTR monitorexit ;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int j;
        int k;
        j = mDisableRecords.size();
        printwriter.println((new StringBuilder()).append("  mDisableRecords.size=").append(j).append(" mDisabled=0x").append(Integer.toHexString(mDisabled)).toString());
        k = 0;
_L4:
        if(k >= j)
            break MISSING_BLOCK_LABEL_349;
        DisableRecord disablerecord = (DisableRecord)mDisableRecords.get(k);
        printwriter.println((new StringBuilder()).append("    [").append(k).append("] what=0x").append(Integer.toHexString(disablerecord.what)).append(" pkg=").append(disablerecord.pkg).append(" token=").append(disablerecord.token).toString());
        k++;
          goto _L4
        Exception exception1;
        exception1;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception1;
        obj;
        JVM INSTR monitorexit ;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception2;
        exception2;
        throw exception2;
    }

    public void expand() {
        enforceExpandStatusBar();
        if(mBar == null)
            break MISSING_BLOCK_LABEL_20;
        mBar.animateExpand();
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    int gatherDisableActionsLocked() {
        int i = mDisableRecords.size();
        int j = 0;
        for(int k = 0; k < i; k++)
            j |= ((DisableRecord)mDisableRecords.get(k)).what;

        return j;
    }

    void manageDisableListLocked(int i, IBinder ibinder, String s) {
        int j;
        DisableRecord disablerecord;
        int k;
        j = mDisableRecords.size();
        disablerecord = null;
        k = 0;
_L7:
        if(k >= j) goto _L2; else goto _L1
_L1:
        DisableRecord disablerecord1 = (DisableRecord)mDisableRecords.get(k);
        if(disablerecord1.token != ibinder) goto _L4; else goto _L3
_L3:
        disablerecord = disablerecord1;
_L2:
        if(i != 0 && ibinder.isBinderAlive()) goto _L6; else goto _L5
_L5:
        if(disablerecord != null) {
            mDisableRecords.remove(k);
            disablerecord.token.unlinkToDeath(disablerecord, 0);
        }
_L8:
        return;
_L4:
        k++;
          goto _L7
_L6:
        if(disablerecord != null)
            break MISSING_BLOCK_LABEL_133;
        disablerecord = new DisableRecord();
        ibinder.linkToDeath(disablerecord, 0);
        mDisableRecords.add(disablerecord);
        disablerecord.what = i;
        disablerecord.token = ibinder;
        disablerecord.pkg = s;
          goto _L8
        RemoteException remoteexception;
        remoteexception;
          goto _L8
    }

    public void onClearAllNotifications() {
        enforceStatusBarService();
        mNotificationCallbacks.onClearAll();
    }

    public void onHardKeyboardStatusChange(final boolean available, final boolean enabled) {
        mHandler.post(new Runnable() {

            public void run() {
                if(mBar == null)
                    break MISSING_BLOCK_LABEL_30;
                mBar.setHardKeyboardStatus(available, enabled);
_L2:
                return;
                RemoteException remoteexception;
                remoteexception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final StatusBarManagerService this$0;
            final boolean val$available;
            final boolean val$enabled;

             {
                this$0 = StatusBarManagerService.this;
                available = flag;
                enabled = flag1;
                super();
            }
        });
    }

    public void onNotificationClear(String s, String s1, int i) {
        enforceStatusBarService();
        mNotificationCallbacks.onNotificationClear(s, s1, i);
    }

    public void onNotificationClick(String s, String s1, int i) {
        enforceStatusBarService();
        mNotificationCallbacks.onNotificationClick(s, s1, i);
    }

    public void onNotificationError(String s, String s1, int i, int j, int k, String s2) {
        enforceStatusBarService();
        mNotificationCallbacks.onNotificationError(s, s1, i, j, k, s2);
    }

    public void onPanelRevealed() {
        enforceStatusBarService();
        mNotificationCallbacks.onPanelRevealed();
    }

    public void preloadRecentApps() {
        if(mBar == null)
            break MISSING_BLOCK_LABEL_16;
        mBar.preloadRecentApps();
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void registerStatusBar(IStatusBar istatusbar, StatusBarIconList statusbariconlist, List list, List list1, int ai[], List list2) {
        int i;
        i = 1;
        enforceStatusBarService();
        Slog.i("StatusBarManagerService", (new StringBuilder()).append("registerStatusBar bar=").append(istatusbar).toString());
        mBar = istatusbar;
        synchronized(mIcons) {
            statusbariconlist.copyFrom(mIcons);
        }
        HashMap hashmap = mNotifications;
        hashmap;
        JVM INSTR monitorenter ;
        java.util.Map.Entry entry;
        for(Iterator iterator = mNotifications.entrySet().iterator(); iterator.hasNext(); list1.add(entry.getValue())) {
            entry = (java.util.Map.Entry)iterator.next();
            list.add(entry.getKey());
        }

        break MISSING_BLOCK_LABEL_151;
        Exception exception1;
        exception1;
        throw exception1;
        exception;
        statusbariconlist1;
        JVM INSTR monitorexit ;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int j;
        ai[0] = gatherDisableActionsLocked();
        ai[1] = mSystemUiVisibility;
        if(!mMenuVisible)
            break MISSING_BLOCK_LABEL_265;
        j = i;
_L1:
        ai[2] = j;
        ai[3] = mImeWindowVis;
        ai[4] = mImeBackDisposition;
        list2.add(mImeToken);
        Exception exception2;
        int k;
        if(mWindowManager.isHardKeyboardAvailable())
            k = i;
        else
            k = 0;
        ai[5] = k;
        if(!mWindowManager.isHardKeyboardEnabled())
            i = 0;
        ai[6] = i;
        return;
        j = 0;
          goto _L1
        exception2;
        obj;
        JVM INSTR monitorexit ;
        throw exception2;
    }

    public void removeIcon(String s) {
        enforceStatusBar();
        StatusBarIconList statusbariconlist = mIcons;
        statusbariconlist;
        JVM INSTR monitorenter ;
        int i;
        i = mIcons.getSlotIndex(s);
        if(i < 0)
            throw new SecurityException((new StringBuilder()).append("invalid status bar icon slot: ").append(s).toString());
        break MISSING_BLOCK_LABEL_59;
        Exception exception;
        exception;
        throw exception;
        IStatusBar istatusbar;
        mIcons.removeIcon(i);
        istatusbar = mBar;
        if(istatusbar == null)
            break MISSING_BLOCK_LABEL_90;
        try {
            mBar.removeIcon(i);
        }
        catch(RemoteException remoteexception) { }
        statusbariconlist;
        JVM INSTR monitorexit ;
    }

    public void removeNotification(IBinder ibinder) {
        HashMap hashmap = mNotifications;
        hashmap;
        JVM INSTR monitorenter ;
        IStatusBar istatusbar;
        if((StatusBarNotification)mNotifications.remove(ibinder) == null) {
            Slog.e("StatusBarManagerService", (new StringBuilder()).append("removeNotification key not found: ").append(ibinder).toString());
            break MISSING_BLOCK_LABEL_88;
        }
        istatusbar = mBar;
        Exception exception;
        if(istatusbar != null)
            try {
                mBar.removeNotification(ibinder);
            }
            catch(RemoteException remoteexception) { }
            finally {
                hashmap;
            }
        hashmap;
        JVM INSTR monitorexit ;
        if(true)
            break MISSING_BLOCK_LABEL_88;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setHardKeyboardEnabled(final boolean enabled) {
        mHandler.post(new Runnable() {

            public void run() {
                mWindowManager.setHardKeyboardEnabled(enabled);
            }

            final StatusBarManagerService this$0;
            final boolean val$enabled;

             {
                this$0 = StatusBarManagerService.this;
                enabled = flag;
                super();
            }
        });
    }

    public void setIcon(String s, String s1, int i, int j, String s2) {
        enforceStatusBar();
        StatusBarIconList statusbariconlist = mIcons;
        statusbariconlist;
        JVM INSTR monitorenter ;
        int k;
        k = mIcons.getSlotIndex(s);
        if(k < 0)
            throw new SecurityException((new StringBuilder()).append("invalid status bar icon slot: ").append(s).toString());
        break MISSING_BLOCK_LABEL_64;
        Exception exception;
        exception;
        throw exception;
        StatusBarIcon statusbaricon;
        IStatusBar istatusbar;
        statusbaricon = new StatusBarIcon(s1, i, j, 0, s2);
        mIcons.setIcon(k, statusbaricon);
        istatusbar = mBar;
        if(istatusbar == null)
            break MISSING_BLOCK_LABEL_115;
        try {
            mBar.setIcon(k, statusbaricon);
        }
        catch(RemoteException remoteexception) { }
        statusbariconlist;
        JVM INSTR monitorexit ;
    }

    public void setIconVisibility(String s, boolean flag) {
        enforceStatusBar();
        StatusBarIconList statusbariconlist = mIcons;
        statusbariconlist;
        JVM INSTR monitorenter ;
        int i;
        i = mIcons.getSlotIndex(s);
        if(i < 0)
            throw new SecurityException((new StringBuilder()).append("invalid status bar icon slot: ").append(s).toString());
        break MISSING_BLOCK_LABEL_61;
        Exception exception;
        exception;
        throw exception;
        StatusBarIcon statusbaricon = mIcons.getIcon(i);
        if(statusbaricon != null) goto _L2; else goto _L1
_L1:
        statusbariconlist;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        if(statusbaricon.visible == flag) goto _L5; else goto _L4
_L4:
        IStatusBar istatusbar;
        statusbaricon.visible = flag;
        istatusbar = mBar;
        if(istatusbar == null) goto _L5; else goto _L6
_L6:
        try {
            mBar.setIcon(i, statusbaricon);
        }
        catch(RemoteException remoteexception) { }
_L5:
        statusbariconlist;
        JVM INSTR monitorexit ;
_L3:
    }

    public void setImeWindowStatus(final IBinder token, final int vis, final int backDisposition) {
        enforceStatusBar();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        mImeWindowVis = vis;
        mImeBackDisposition = backDisposition;
        mImeToken = token;
        mHandler.post(new Runnable() {

            public void run() {
                if(mBar == null)
                    break MISSING_BLOCK_LABEL_34;
                mBar.setImeWindowStatus(token, vis, backDisposition);
_L2:
                return;
                RemoteException remoteexception;
                remoteexception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final StatusBarManagerService this$0;
            final int val$backDisposition;
            final IBinder val$token;
            final int val$vis;

             {
                this$0 = StatusBarManagerService.this;
                token = ibinder;
                vis = i;
                backDisposition = j;
                super();
            }
        });
        return;
    }

    public void setNotificationCallbacks(NotificationCallbacks notificationcallbacks) {
        mNotificationCallbacks = notificationcallbacks;
    }

    public void setSystemUiVisibility(int i, int j) {
        enforceStatusBarService();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        updateUiVisibilityLocked(i, j);
        disableLocked(0x1ff0000 & i, mSysUiVisToken, "WindowManager.LayoutParams");
        return;
    }

    public void toggleRecentApps() {
        if(mBar == null)
            break MISSING_BLOCK_LABEL_16;
        mBar.toggleRecentApps();
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void topAppWindowChanged(final boolean menuVisible) {
        enforceStatusBar();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        mMenuVisible = menuVisible;
        mHandler.post(new Runnable() {

            public void run() {
                if(mBar == null)
                    break MISSING_BLOCK_LABEL_26;
                mBar.topAppWindowChanged(menuVisible);
_L2:
                return;
                RemoteException remoteexception;
                remoteexception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final StatusBarManagerService this$0;
            final boolean val$menuVisible;

             {
                this$0 = StatusBarManagerService.this;
                menuVisible = flag;
                super();
            }
        });
        return;
    }

    public void updateNotification(IBinder ibinder, StatusBarNotification statusbarnotification) {
        HashMap hashmap = mNotifications;
        hashmap;
        JVM INSTR monitorenter ;
        if(!mNotifications.containsKey(ibinder))
            throw new IllegalArgumentException((new StringBuilder()).append("updateNotification key not found: ").append(ibinder).toString());
        break MISSING_BLOCK_LABEL_53;
        Exception exception;
        exception;
        throw exception;
        IStatusBar istatusbar;
        mNotifications.put(ibinder, statusbarnotification);
        istatusbar = mBar;
        if(istatusbar == null)
            break MISSING_BLOCK_LABEL_85;
        try {
            mBar.updateNotification(ibinder, statusbarnotification);
        }
        catch(RemoteException remoteexception) { }
        hashmap;
        JVM INSTR monitorexit ;
    }

    static final boolean SPEW = false;
    static final String TAG = "StatusBarManagerService";
    volatile IStatusBar mBar;
    private BroadcastReceiver mBroadcastReceiver;
    final Context mContext;
    ArrayList mDisableRecords;
    int mDisabled;
    Handler mHandler;
    StatusBarIconList mIcons;
    int mImeBackDisposition;
    IBinder mImeToken;
    int mImeWindowVis;
    Object mLock;
    boolean mMenuVisible;
    NotificationCallbacks mNotificationCallbacks;
    HashMap mNotifications;
    IBinder mSysUiVisToken;
    int mSystemUiVisibility;
    final WindowManagerService mWindowManager;
}
