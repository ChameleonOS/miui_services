// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.pm.IPackageManager;
import android.os.*;
import android.util.EventLog;
import android.util.Slog;
import java.io.File;

// Referenced classes of package com.android.server:
//            EventLogTags

public class DeviceStorageMonitorService extends Binder {
    public static class CacheFileDeletedObserver extends FileObserver {

        public void onEvent(int i, String s) {
            EventLogTags.writeCacheFileDeleted(s);
        }

        public CacheFileDeletedObserver() {
            super(Environment.getDownloadCacheDirectory().getAbsolutePath(), 512);
        }
    }

    class CachePackageDataObserver extends android.content.pm.IPackageDataObserver.Stub {

        public void onRemoveCompleted(String s, boolean flag) {
            mClearSucceeded = flag;
            mClearingCache = false;
            postCheckMemoryMsg(false, 0L);
        }

        final DeviceStorageMonitorService this$0;

        CachePackageDataObserver() {
            this$0 = DeviceStorageMonitorService.this;
            super();
        }
    }


    public DeviceStorageMonitorService(Context context) {
        mLowMemFlag = false;
        mMemFullFlag = false;
        mThreadStartTime = -1L;
        mClearSucceeded = false;
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                boolean flag = true;
                if(message.what != flag) {
                    Slog.e("DeviceStorageMonitorService", "Will not process invalid message");
                } else {
                    DeviceStorageMonitorService devicestoragemonitorservice = DeviceStorageMonitorService.this;
                    if(message.arg1 != flag)
                        flag = false;
                    devicestoragemonitorservice.checkMemory(flag);
                }
            }

            final DeviceStorageMonitorService this$0;

             {
                this$0 = DeviceStorageMonitorService.this;
                super();
            }
        };
        mLastReportedFreeMemTime = 0L;
        mContext = context;
        mContentResolver = mContext.getContentResolver();
        mDataFileStats = new StatFs("/data");
        mSystemFileStats = new StatFs("/system");
        mCacheFileStats = new StatFs("/cache");
        mTotalMemory = ((long)mDataFileStats.getBlockCount() * (long)mDataFileStats.getBlockSize()) / 100L;
        mStorageLowIntent = new Intent("android.intent.action.DEVICE_STORAGE_LOW");
        mStorageLowIntent.addFlags(0x8000000);
        mStorageOkIntent = new Intent("android.intent.action.DEVICE_STORAGE_OK");
        mStorageOkIntent.addFlags(0x8000000);
        mStorageFullIntent = new Intent("android.intent.action.DEVICE_STORAGE_FULL");
        mStorageFullIntent.addFlags(0x8000000);
        mStorageNotFullIntent = new Intent("android.intent.action.DEVICE_STORAGE_NOT_FULL");
        mStorageNotFullIntent.addFlags(0x8000000);
        mMemLowThreshold = getMemThreshold();
        mMemFullThreshold = getMemFullThreshold();
        checkMemory(true);
        mCacheFileDeletedObserver.startWatching();
    }

    private final void cancelFullNotification() {
        mContext.removeStickyBroadcast(mStorageFullIntent);
        mContext.sendBroadcast(mStorageNotFullIntent);
    }

    private final void cancelNotification() {
        ((NotificationManager)mContext.getSystemService("notification")).cancel(1);
        mContext.removeStickyBroadcast(mStorageLowIntent);
        mContext.sendBroadcast(mStorageOkIntent);
    }

    private final void checkMemory(boolean flag) {
        if(!mClearingCache) goto _L2; else goto _L1
_L1:
        if(System.currentTimeMillis() - mThreadStartTime > 0x927c0L)
            Slog.w("DeviceStorageMonitorService", "Thread that clears cache file seems to run for ever");
_L8:
        postCheckMemoryMsg(true, 60000L);
        return;
_L2:
        restatDataDir();
        if(mFreeMem >= mMemLowThreshold) goto _L4; else goto _L3
_L3:
        if(!mLowMemFlag)
            if(flag) {
                mThreadStartTime = System.currentTimeMillis();
                mClearSucceeded = false;
                clearCache();
            } else {
                Slog.i("DeviceStorageMonitorService", "Running low on memory. Sending notification");
                sendNotification();
                mLowMemFlag = true;
            }
_L6:
        if(mFreeMem >= (long)mMemFullThreshold)
            break; /* Loop/switch isn't completed */
        if(!mMemFullFlag) {
            sendFullNotification();
            mMemFullFlag = true;
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if(mLowMemFlag) {
            Slog.i("DeviceStorageMonitorService", "Memory available. Cancelling notification");
            cancelNotification();
            mLowMemFlag = false;
        }
        if(true) goto _L6; else goto _L5
_L5:
        if(mMemFullFlag) {
            cancelFullNotification();
            mMemFullFlag = false;
        }
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final void clearCache() {
        if(mClearCacheObserver == null)
            mClearCacheObserver = new CachePackageDataObserver();
        mClearingCache = true;
        android.content.pm.IPackageManager.Stub.asInterface(ServiceManager.getService("package")).freeStorageAndNotify(mMemLowThreshold, mClearCacheObserver);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("DeviceStorageMonitorService", (new StringBuilder()).append("Failed to get handle for PackageManger Exception: ").append(remoteexception).toString());
        mClearingCache = false;
        mClearSucceeded = false;
          goto _L1
    }

    private int getMemFullThreshold() {
        return android.provider.Settings.Secure.getInt(mContentResolver, "sys_storage_full_threshold_bytes", 0x100000);
    }

    private long getMemThreshold() {
        long l = (long)android.provider.Settings.Secure.getInt(mContentResolver, "sys_storage_threshold_percentage", 10) * mTotalMemory;
        long l1 = android.provider.Settings.Secure.getInt(mContentResolver, "sys_storage_threshold_max_bytes", 0x1f400000);
        if(l >= l1)
            l = l1;
        return l;
    }

    private void postCheckMemoryMsg(boolean flag, long l) {
        mHandler.removeMessages(1);
        Handler handler = mHandler;
        Handler handler1 = mHandler;
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        handler.sendMessageDelayed(handler1.obtainMessage(1, i, 0), l);
    }

    private final void restatDataDir() {
        String s;
        long l;
        long l1;
        long l2;
        long l3;
        IllegalArgumentException illegalargumentexception1;
        IllegalArgumentException illegalargumentexception2;
        Object aobj[];
        long l4;
        long l5;
        long l6;
        int i;
        long l7;
        int j;
        try {
            mDataFileStats.restat("/data");
            mFreeMem = (long)mDataFileStats.getAvailableBlocks() * (long)mDataFileStats.getBlockSize();
        }
        catch(IllegalArgumentException illegalargumentexception) { }
        s = SystemProperties.get("debug.freemem");
        if(!"".equals(s))
            mFreeMem = Long.parseLong(s);
        l = 1000L * (60L * android.provider.Settings.Secure.getLong(mContentResolver, "sys_free_storage_log_interval", 720L));
        l1 = SystemClock.elapsedRealtime();
        if(mLastReportedFreeMemTime != 0L && l1 - mLastReportedFreeMemTime < l) goto _L2; else goto _L1
_L1:
        mLastReportedFreeMemTime = l1;
        l2 = -1L;
        l3 = -1L;
        mSystemFileStats.restat("/system");
        l7 = mSystemFileStats.getAvailableBlocks();
        j = mSystemFileStats.getBlockSize();
        l2 = l7 * (long)j;
_L6:
        mCacheFileStats.restat("/cache");
        l6 = mCacheFileStats.getAvailableBlocks();
        i = mCacheFileStats.getBlockSize();
        l3 = l6 * (long)i;
_L4:
        aobj = new Object[3];
        aobj[0] = Long.valueOf(mFreeMem);
        aobj[1] = Long.valueOf(l2);
        aobj[2] = Long.valueOf(l3);
        EventLog.writeEvent(2746, aobj);
_L2:
        l4 = android.provider.Settings.Secure.getLong(mContentResolver, "disk_free_change_reporting_threshold", 0x200000L);
        l5 = mFreeMem - mLastReportedFreeMem;
        if(l5 > l4 || l5 < -l4) {
            mLastReportedFreeMem = mFreeMem;
            EventLog.writeEvent(2744, mFreeMem);
        }
        return;
        illegalargumentexception2;
        if(true) goto _L4; else goto _L3
_L3:
        illegalargumentexception1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private final void sendFullNotification() {
        mContext.sendStickyBroadcast(mStorageFullIntent);
    }

    private final void sendNotification() {
        EventLog.writeEvent(2745, mFreeMem);
        String s;
        Intent intent;
        NotificationManager notificationmanager;
        CharSequence charsequence;
        CharSequence charsequence1;
        PendingIntent pendingintent;
        Notification notification;
        if(Environment.isExternalStorageEmulated())
            s = "android.settings.INTERNAL_STORAGE_SETTINGS";
        else
            s = "android.intent.action.MANAGE_PACKAGE_STORAGE";
        intent = new Intent(s);
        intent.putExtra("memory", mFreeMem);
        intent.addFlags(0x10000000);
        notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        charsequence = mContext.getText(0x10403c4);
        charsequence1 = mContext.getText(0x10403c5);
        pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
        notification = new Notification();
        notification.icon = 0x1080510;
        notification.tickerText = charsequence;
        notification.flags = 0x20 | notification.flags;
        notification.setLatestEventInfo(mContext, charsequence, charsequence1, pendingintent);
        notificationmanager.notify(1, notification);
        mContext.sendStickyBroadcast(mStorageLowIntent);
    }

    public long getMemoryLowThreshold() {
        return mMemLowThreshold;
    }

    public boolean isMemoryLow() {
        return mLowMemFlag;
    }

    public void updateMemory() {
        if(getCallingUid() == 1000)
            postCheckMemoryMsg(true, 0L);
    }

    private static final String CACHE_PATH = "/cache";
    private static final String DATA_PATH = "/data";
    private static final boolean DEBUG = false;
    private static final long DEFAULT_CHECK_INTERVAL = 60000L;
    private static final long DEFAULT_DISK_FREE_CHANGE_REPORTING_THRESHOLD = 0x200000L;
    private static final int DEFAULT_FREE_STORAGE_LOG_INTERVAL_IN_MINUTES = 720;
    private static final int DEFAULT_FULL_THRESHOLD_BYTES = 0x100000;
    private static final int DEFAULT_THRESHOLD_MAX_BYTES = 0x1f400000;
    private static final int DEFAULT_THRESHOLD_PERCENTAGE = 10;
    private static final int DEVICE_MEMORY_WHAT = 1;
    private static final int LOW_MEMORY_NOTIFICATION_ID = 1;
    private static final int MONITOR_INTERVAL = 1;
    public static final String SERVICE = "devicestoragemonitor";
    private static final String SYSTEM_PATH = "/system";
    private static final String TAG = "DeviceStorageMonitorService";
    private static final int _FALSE = 0;
    private static final int _TRUE = 1;
    private static final boolean localLOGV;
    private final CacheFileDeletedObserver mCacheFileDeletedObserver = new CacheFileDeletedObserver();
    private StatFs mCacheFileStats;
    private CachePackageDataObserver mClearCacheObserver;
    private boolean mClearSucceeded;
    private boolean mClearingCache;
    private ContentResolver mContentResolver;
    private Context mContext;
    private StatFs mDataFileStats;
    private long mFreeMem;
    Handler mHandler;
    private long mLastReportedFreeMem;
    private long mLastReportedFreeMemTime;
    private boolean mLowMemFlag;
    private boolean mMemFullFlag;
    private int mMemFullThreshold;
    private long mMemLowThreshold;
    private Intent mStorageFullIntent;
    private Intent mStorageLowIntent;
    private Intent mStorageNotFullIntent;
    private Intent mStorageOkIntent;
    private StatFs mSystemFileStats;
    private long mThreadStartTime;
    private long mTotalMemory;



/*
    static boolean access$102(DeviceStorageMonitorService devicestoragemonitorservice, boolean flag) {
        devicestoragemonitorservice.mClearSucceeded = flag;
        return flag;
    }

*/


/*
    static boolean access$202(DeviceStorageMonitorService devicestoragemonitorservice, boolean flag) {
        devicestoragemonitorservice.mClearingCache = flag;
        return flag;
    }

*/

}
