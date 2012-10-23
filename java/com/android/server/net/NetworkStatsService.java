// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.app.IAlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.net.*;
import android.os.*;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.*;
import com.android.internal.util.*;
import com.android.server.EventLogTags;
import com.android.server.NetworkManagementSocketTagger;
import com.google.android.collect.Maps;
import java.io.*;
import java.util.*;

// Referenced classes of package com.android.server.net:
//            NetworkStatsRecorder, NetworkStatsCollection, NetworkIdentitySet, NetworkAlertObserver

public class NetworkStatsService extends android.net.INetworkStatsService.Stub {
    private static class DefaultNetworkStatsSettings
        implements NetworkStatsSettings {

        private boolean getSecureBoolean(String s, boolean flag) {
            boolean flag1 = true;
            int i;
            if(flag)
                i = ((flag1) ? 1 : 0);
            else
                i = 0;
            if(android.provider.Settings.Secure.getInt(mResolver, s, i) == 0)
                flag1 = false;
            return flag1;
        }

        private long getSecureLong(String s, long l) {
            return android.provider.Settings.Secure.getLong(mResolver, s, l);
        }

        public NetworkStatsSettings.Config getDevConfig() {
            return new NetworkStatsSettings.Config(getSecureLong("netstats_dev_bucket_duration", 0x36ee80L), getSecureLong("netstats_dev_rotate_age", 0x4d3f6400L), getSecureLong("netstats_dev_delete_age", 0x1cf7c5800L));
        }

        public long getDevPersistBytes(long l) {
            return getSecureLong("netstats_dev_persist_bytes", l);
        }

        public long getGlobalAlertBytes(long l) {
            return getSecureLong("netstats_global_alert_bytes", l);
        }

        public long getPollInterval() {
            return getSecureLong("netstats_poll_interval", 0x1b7740L);
        }

        public boolean getReportXtOverDev() {
            return getSecureBoolean("netstats_report_xt_over_dev", true);
        }

        public boolean getSampleEnabled() {
            return getSecureBoolean("netstats_sample_enabled", true);
        }

        public long getTimeCacheMaxAge() {
            return getSecureLong("netstats_time_cache_max_age", 0x5265c00L);
        }

        public NetworkStatsSettings.Config getUidConfig() {
            return new NetworkStatsSettings.Config(getSecureLong("netstats_uid_bucket_duration", 0x6ddd00L), getSecureLong("netstats_uid_rotate_age", 0x4d3f6400L), getSecureLong("netstats_uid_delete_age", 0x1cf7c5800L));
        }

        public long getUidPersistBytes(long l) {
            return getSecureLong("netstats_uid_persist_bytes", l);
        }

        public NetworkStatsSettings.Config getUidTagConfig() {
            return new NetworkStatsSettings.Config(getSecureLong("netstats_uid_tag_bucket_duration", 0x6ddd00L), getSecureLong("netstats_uid_tag_rotate_age", 0x19bfcc00L), getSecureLong("netstats_uid_tag_delete_age", 0x4d3f6400L));
        }

        public long getUidTagPersistBytes(long l) {
            return getSecureLong("netstats_uid_tag_persist_bytes", l);
        }

        public NetworkStatsSettings.Config getXtConfig() {
            return getDevConfig();
        }

        public long getXtPersistBytes(long l) {
            return getDevPersistBytes(l);
        }

        private final ContentResolver mResolver;

        public DefaultNetworkStatsSettings(Context context) {
            mResolver = (ContentResolver)Preconditions.checkNotNull(context.getContentResolver());
        }
    }

    private class DropBoxNonMonotonicObserver
        implements android.net.NetworkStats.NonMonotonicObserver {

        public volatile void foundNonMonotonic(NetworkStats networkstats, int i, NetworkStats networkstats1, int j, Object obj) {
            foundNonMonotonic(networkstats, i, networkstats1, j, (String)obj);
        }

        public void foundNonMonotonic(NetworkStats networkstats, int i, NetworkStats networkstats1, int j, String s) {
            Log.w("NetworkStats", "found non-monotonic values; saving to dropbox");
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append((new StringBuilder()).append("found non-monotonic ").append(s).append(" values at left[").append(i).append("] - right[").append(j).append("]\n").toString());
            stringbuilder.append("left=").append(networkstats).append('\n');
            stringbuilder.append("right=").append(networkstats1).append('\n');
            ((DropBoxManager)mContext.getSystemService("dropbox")).addText("netstats_error", stringbuilder.toString());
        }

        final NetworkStatsService this$0;

        private DropBoxNonMonotonicObserver() {
            this$0 = NetworkStatsService.this;
            super();
        }

    }

    public static interface NetworkStatsSettings {
        public static class Config {

            public final long bucketDuration;
            public final long deleteAgeMillis;
            public final long rotateAgeMillis;

            public Config(long l, long l1, long l2) {
                bucketDuration = l;
                rotateAgeMillis = l1;
                deleteAgeMillis = l2;
            }
        }


        public abstract Config getDevConfig();

        public abstract long getDevPersistBytes(long l);

        public abstract long getGlobalAlertBytes(long l);

        public abstract long getPollInterval();

        public abstract boolean getReportXtOverDev();

        public abstract boolean getSampleEnabled();

        public abstract long getTimeCacheMaxAge();

        public abstract Config getUidConfig();

        public abstract long getUidPersistBytes(long l);

        public abstract Config getUidTagConfig();

        public abstract long getUidTagPersistBytes(long l);

        public abstract Config getXtConfig();

        public abstract long getXtPersistBytes(long l);
    }


    public NetworkStatsService(Context context, INetworkManagementService inetworkmanagementservice, IAlarmManager ialarmmanager) {
        this(context, inetworkmanagementservice, ialarmmanager, ((TrustedTime) (NtpTrustedTime.getInstance(context))), getDefaultSystemDir(), ((NetworkStatsSettings) (new DefaultNetworkStatsSettings(context))));
    }

    public NetworkStatsService(Context context, INetworkManagementService inetworkmanagementservice, IAlarmManager ialarmmanager, TrustedTime trustedtime, File file, NetworkStatsSettings networkstatssettings) {
        mStatsLock = new Object();
        mActiveIfaces = Maps.newHashMap();
        mMobileIfaces = new String[0];
        mNonMonotonicObserver = new DropBoxNonMonotonicObserver();
        mActiveUidCounterSet = new SparseIntArray();
        mUidOperations = new NetworkStats(0L, 10);
        mPersistThreshold = 0x200000L;
        mLastPhoneState = -1;
        mLastPhoneNetworkType = 0;
        mContext = (Context)Preconditions.checkNotNull(context, "missing Context");
        mNetworkManager = (INetworkManagementService)Preconditions.checkNotNull(inetworkmanagementservice, "missing INetworkManagementService");
        mAlarmManager = (IAlarmManager)Preconditions.checkNotNull(ialarmmanager, "missing IAlarmManager");
        mTime = (TrustedTime)Preconditions.checkNotNull(trustedtime, "missing TrustedTime");
        mTeleManager = (TelephonyManager)Preconditions.checkNotNull(TelephonyManager.getDefault(), "missing TelephonyManager");
        mSettings = (NetworkStatsSettings)Preconditions.checkNotNull(networkstatssettings, "missing NetworkStatsSettings");
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "NetworkStats");
        mHandlerThread = new HandlerThread("NetworkStats");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper(), mHandlerCallback);
        mSystemDir = (File)Preconditions.checkNotNull(file);
        mBaseDir = new File(file, "netstats");
        mBaseDir.mkdirs();
    }

    private void assertBandwidthControlEnabled() {
        if(!isBandwidthControlEnabled())
            throw new IllegalStateException("Bandwidth module disabled");
        else
            return;
    }

    private void bootstrapStatsLocked() {
        long l;
        NetworkStats networkstats;
        NetworkStats networkstats1;
        NetworkStats networkstats2;
        if(mTime.hasCache())
            l = mTime.currentTimeMillis();
        else
            l = System.currentTimeMillis();
        networkstats = getNetworkStatsUidDetail();
        networkstats1 = mNetworkManager.getNetworkStatsSummaryXt();
        networkstats2 = mNetworkManager.getNetworkStatsSummaryDev();
        mDevRecorder.recordSnapshotLocked(networkstats2, mActiveIfaces, l);
        mXtRecorder.recordSnapshotLocked(networkstats1, mActiveIfaces, l);
        mUidRecorder.recordSnapshotLocked(networkstats, mActiveIfaces, l);
        mUidTagRecorder.recordSnapshotLocked(networkstats, mActiveIfaces, l);
_L2:
        return;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Slog.w("NetworkStats", (new StringBuilder()).append("problem reading network stats: ").append(illegalstateexception).toString());
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private NetworkStatsRecorder buildRecorder(String s, NetworkStatsSettings.Config config, boolean flag) {
        DropBoxManager dropboxmanager = (DropBoxManager)mContext.getSystemService("dropbox");
        NetworkStatsRecorder networkstatsrecorder = new NetworkStatsRecorder(new FileRotator(mBaseDir, s, config.rotateAgeMillis, config.deleteAgeMillis), mNonMonotonicObserver, dropboxmanager, s, config.bucketDuration, flag);
        return networkstatsrecorder;
    }

    private static File getDefaultSystemDir() {
        return new File(Environment.getDataDirectory(), "system");
    }

    private NetworkStats getNetworkStatsTethering() throws RemoteException {
        NetworkStats networkstats1;
        String as[] = mConnManager.getTetheredIfacePairs();
        networkstats1 = mNetworkManager.getNetworkStatsTethering(as);
        NetworkStats networkstats = networkstats1;
_L2:
        return networkstats;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Log.wtf("NetworkStats", "problem reading network stats", illegalstateexception);
        networkstats = new NetworkStats(0L, 10);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private NetworkStats getNetworkStatsUidDetail() throws RemoteException {
        NetworkStats networkstats = mNetworkManager.getNetworkStatsUidDetail(-1);
        networkstats.combineAllValues(getNetworkStatsTethering());
        networkstats.combineAllValues(mUidOperations);
        return networkstats;
    }

    private NetworkStatsHistory internalGetHistoryForNetwork(NetworkTemplate networktemplate, int i) {
        NetworkStatsHistory networkstatshistory1;
        if(!mSettings.getReportXtOverDev()) {
            networkstatshistory1 = mDevStatsCached.getHistory(networktemplate, -1, -1, 0, i);
        } else {
            long l = mXtStatsCached.getFirstAtomicBucketMillis();
            NetworkStatsHistory networkstatshistory = mDevStatsCached.getHistory(networktemplate, -1, -1, 0, i, 0x8000000000000000L, l);
            networkstatshistory1 = mXtStatsCached.getHistory(networktemplate, -1, -1, 0, i, l, 0x7fffffffffffffffL);
            networkstatshistory1.recordEntireHistory(networkstatshistory);
        }
        return networkstatshistory1;
    }

    private NetworkStats internalGetSummaryForNetwork(NetworkTemplate networktemplate, long l, long l1) {
        NetworkStats networkstats1;
        if(!mSettings.getReportXtOverDev()) {
            networkstats1 = mDevStatsCached.getSummary(networktemplate, l, l1);
        } else {
            long l2 = mXtStatsCached.getFirstAtomicBucketMillis();
            NetworkStats networkstats = mDevStatsCached.getSummary(networktemplate, Math.min(l, l2), Math.min(l1, l2));
            networkstats1 = mXtStatsCached.getSummary(networktemplate, Math.max(l, l2), Math.max(l1, l2));
            networkstats1.combineAllValues(networkstats);
        }
        return networkstats1;
    }

    private boolean isBandwidthControlEnabled() {
        long l = Binder.clearCallingIdentity();
        boolean flag1 = mNetworkManager.isBandwidthControlEnabled();
        boolean flag = flag1;
_L2:
        Binder.restoreCallingIdentity(l);
        return flag;
        RemoteException remoteexception;
        remoteexception;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void maybeUpgradeLegacyStatsLocked() {
        File file = new File(mSystemDir, "netstats.bin");
        if(file.exists()) {
            mDevRecorder.importLegacyNetworkLocked(file);
            file.delete();
        }
        File file1 = new File(mSystemDir, "netstats_xt.bin");
        if(file1.exists())
            file1.delete();
        File file2 = new File(mSystemDir, "netstats_uid.bin");
        if(file2.exists()) {
            mUidRecorder.importLegacyUidLocked(file2);
            mUidTagRecorder.importLegacyUidLocked(file2);
            file2.delete();
        }
_L1:
        return;
        IOException ioexception;
        ioexception;
        Log.wtf("NetworkStats", "problem during legacy upgrade", ioexception);
          goto _L1
    }

    private void performPoll(int i) {
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        mWakeLock.acquire();
        if(mTime.getCacheAge() > mSettings.getTimeCacheMaxAge())
            mTime.forceRefresh();
        performPollLocked(i);
        mWakeLock.release();
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        mWakeLock.release();
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    private void performPollLocked(int i) {
        boolean flag = true;
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        return;
_L2:
        boolean flag1;
        boolean flag2;
        long l;
        SystemClock.elapsedRealtime();
        NetworkStats networkstats;
        NetworkStats networkstats1;
        NetworkStats networkstats2;
        Intent intent;
        if((i & 1) != 0)
            flag1 = flag;
        else
            flag1 = false;
        if((i & 2) != 0)
            flag2 = flag;
        else
            flag2 = false;
        if((i & 0x100) == 0)
            flag = false;
        if(mTime.hasCache())
            l = mTime.currentTimeMillis();
        else
            l = System.currentTimeMillis();
        networkstats = getNetworkStatsUidDetail();
        networkstats1 = mNetworkManager.getNetworkStatsSummaryXt();
        networkstats2 = mNetworkManager.getNetworkStatsSummaryDev();
        mDevRecorder.recordSnapshotLocked(networkstats2, mActiveIfaces, l);
        mXtRecorder.recordSnapshotLocked(networkstats1, mActiveIfaces, l);
        mUidRecorder.recordSnapshotLocked(networkstats, mActiveIfaces, l);
        mUidTagRecorder.recordSnapshotLocked(networkstats, mActiveIfaces, l);
        RemoteException remoteexception;
        IllegalStateException illegalstateexception;
        if(flag) {
            mDevRecorder.forcePersistLocked(l);
            mXtRecorder.forcePersistLocked(l);
            mUidRecorder.forcePersistLocked(l);
            mUidTagRecorder.forcePersistLocked(l);
        } else {
            if(flag1) {
                mDevRecorder.maybePersistLocked(l);
                mXtRecorder.maybePersistLocked(l);
            }
            if(flag2) {
                mUidRecorder.maybePersistLocked(l);
                mUidTagRecorder.maybePersistLocked(l);
            }
        }
        if(mSettings.getSampleEnabled())
            performSampleLocked();
        intent = new Intent("com.android.server.action.NETWORK_STATS_UPDATED");
        intent.setFlags(0x40000000);
        mContext.sendBroadcast(intent, "android.permission.READ_NETWORK_USAGE_HISTORY");
        continue; /* Loop/switch isn't completed */
        illegalstateexception;
        Log.wtf("NetworkStats", "problem reading network stats", illegalstateexception);
        continue; /* Loop/switch isn't completed */
        remoteexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void performSampleLocked() {
        long l;
        NetworkTemplate networktemplate;
        android.net.NetworkStats.Entry entry;
        android.net.NetworkStats.Entry entry1;
        android.net.NetworkStats.Entry entry2;
        NetworkTemplate networktemplate1;
        android.net.NetworkStats.Entry entry3;
        android.net.NetworkStats.Entry entry4;
        android.net.NetworkStats.Entry entry5;
        if(mTime.hasCache())
            l = mTime.currentTimeMillis();
        else
            l = -1L;
        networktemplate = NetworkTemplate.buildTemplateMobileWildcard();
        entry = mDevRecorder.getTotalSinceBootLocked(networktemplate);
        entry1 = mXtRecorder.getTotalSinceBootLocked(networktemplate);
        entry2 = mUidRecorder.getTotalSinceBootLocked(networktemplate);
        EventLogTags.writeNetstatsMobileSample(entry.rxBytes, entry.rxPackets, entry.txBytes, entry.txPackets, entry1.rxBytes, entry1.rxPackets, entry1.txBytes, entry1.txPackets, entry2.rxBytes, entry2.rxPackets, entry2.txBytes, entry2.txPackets, l);
        networktemplate1 = NetworkTemplate.buildTemplateWifiWildcard();
        entry3 = mDevRecorder.getTotalSinceBootLocked(networktemplate1);
        entry4 = mXtRecorder.getTotalSinceBootLocked(networktemplate1);
        entry5 = mUidRecorder.getTotalSinceBootLocked(networktemplate1);
        EventLogTags.writeNetstatsWifiSample(entry3.rxBytes, entry3.rxPackets, entry3.txBytes, entry3.txPackets, entry4.rxBytes, entry4.rxPackets, entry4.txBytes, entry4.txPackets, entry5.rxBytes, entry5.rxPackets, entry5.txBytes, entry5.txPackets, l);
    }

    private void registerGlobalAlert() {
        mNetworkManager.setGlobalAlert(mGlobalAlertBytes);
_L2:
        return;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Slog.w("NetworkStats", (new StringBuilder()).append("problem registering for global alert: ").append(illegalstateexception).toString());
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void registerPollAlarmLocked() {
        if(mPollIntent != null)
            mAlarmManager.remove(mPollIntent);
        mPollIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("com.android.server.action.NETWORK_STATS_POLL"), 0);
        long l = SystemClock.elapsedRealtime();
        mAlarmManager.setInexactRepeating(3, l, mSettings.getPollInterval(), mPollIntent);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void removeUidLocked(int i) {
        performPollLocked(3);
        mUidRecorder.removeUidLocked(i);
        mUidTagRecorder.removeUidLocked(i);
        NetworkManagementSocketTagger.resetKernelUidStats(i);
    }

    private void shutdownLocked() {
        mContext.unregisterReceiver(mConnReceiver);
        mContext.unregisterReceiver(mTetherReceiver);
        mContext.unregisterReceiver(mPollReceiver);
        mContext.unregisterReceiver(mRemovedReceiver);
        mContext.unregisterReceiver(mShutdownReceiver);
        long l;
        if(mTime.hasCache())
            l = mTime.currentTimeMillis();
        else
            l = System.currentTimeMillis();
        mDevRecorder.forcePersistLocked(l);
        mXtRecorder.forcePersistLocked(l);
        mUidRecorder.forcePersistLocked(l);
        mUidTagRecorder.forcePersistLocked(l);
        mDevRecorder = null;
        mXtRecorder = null;
        mUidRecorder = null;
        mUidTagRecorder = null;
        mDevStatsCached = null;
        mXtStatsCached = null;
        mSystemReady = false;
    }

    private void updateIfaces() {
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        mWakeLock.acquire();
        updateIfacesLocked();
        mWakeLock.release();
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        mWakeLock.release();
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    private void updateIfacesLocked() {
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        return;
_L2:
        performPollLocked(1);
        NetworkState anetworkstate[];
        LinkProperties linkproperties;
        anetworkstate = mConnManager.getAllNetworkState();
        linkproperties = mConnManager.getActiveLinkProperties();
        RemoteException remoteexception;
        String s;
        int i;
        int j;
        if(linkproperties != null)
            s = linkproperties.getInterfaceName();
        else
            s = null;
        mActiveIface = s;
        mActiveIfaces.clear();
        i = anetworkstate.length;
        j = 0;
        while(j < i)  {
            NetworkState networkstate = anetworkstate[j];
            if(networkstate.networkInfo.isConnected()) {
                String s1 = networkstate.linkProperties.getInterfaceName();
                NetworkIdentitySet networkidentityset = (NetworkIdentitySet)mActiveIfaces.get(s1);
                if(networkidentityset == null) {
                    networkidentityset = new NetworkIdentitySet();
                    mActiveIfaces.put(s1, networkidentityset);
                }
                networkidentityset.add(NetworkIdentity.buildNetworkIdentity(mContext, networkstate));
                if(ConnectivityManager.isNetworkTypeMobile(networkstate.networkInfo.getType()) && !ArrayUtils.contains(mMobileIfaces, s1))
                    mMobileIfaces = (String[])ArrayUtils.appendElement(java/lang/String, mMobileIfaces, s1);
            }
            j++;
        }
        continue; /* Loop/switch isn't completed */
        remoteexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void updatePersistThresholds() {
        mDevRecorder.setPersistThreshold(mSettings.getDevPersistBytes(mPersistThreshold));
        mXtRecorder.setPersistThreshold(mSettings.getXtPersistBytes(mPersistThreshold));
        mUidRecorder.setPersistThreshold(mSettings.getUidPersistBytes(mPersistThreshold));
        mUidTagRecorder.setPersistThreshold(mSettings.getUidTagPersistBytes(mPersistThreshold));
        mGlobalAlertBytes = mSettings.getGlobalAlertBytes(mPersistThreshold);
    }

    public void advisePersistThreshold(long l) {
        long l1;
        Object obj;
        mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_NETWORK_ACCOUNTING", "NetworkStats");
        assertBandwidthControlEnabled();
        mPersistThreshold = MathUtils.constrain(l, 0x20000L, 0x200000L);
        if(mTime.hasCache())
            l1 = mTime.currentTimeMillis();
        else
            l1 = System.currentTimeMillis();
        obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        return;
_L2:
        updatePersistThresholds();
        mDevRecorder.maybePersistLocked(l1);
        mXtRecorder.maybePersistLocked(l1);
        mUidRecorder.maybePersistLocked(l1);
        mUidTagRecorder.maybePersistLocked(l1);
        obj;
        JVM INSTR monitorexit ;
        registerGlobalAlert();
          goto _L1
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void bindConnectivityManager(IConnectivityManager iconnectivitymanager) {
        mConnManager = (IConnectivityManager)Preconditions.checkNotNull(iconnectivitymanager, "missing IConnectivityManager");
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "NetworkStats");
        HashSet hashset = new HashSet();
        int i = as.length;
        for(int j = 0; j < i; j++)
            hashset.add(as[j]);

        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        IndentingPrintWriter indentingprintwriter;
        Object obj;
        Iterator iterator;
        Exception exception;
        String s;
        NetworkIdentitySet networkidentityset;
        String as1[];
        int k;
        int l;
        if(hashset.contains("--poll") || hashset.contains("poll"))
            flag = true;
        else
            flag = false;
        flag1 = hashset.contains("--checkin");
        if(hashset.contains("--full") || hashset.contains("full"))
            flag2 = true;
        else
            flag2 = false;
        if(hashset.contains("--uid") || hashset.contains("detail"))
            flag3 = true;
        else
            flag3 = false;
        if(hashset.contains("--tag") || hashset.contains("detail"))
            flag4 = true;
        else
            flag4 = false;
        indentingprintwriter = new IndentingPrintWriter(printwriter, "  ");
        obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        if(!flag) goto _L2; else goto _L1
_L1:
        performPollLocked(259);
        indentingprintwriter.println("Forced poll");
          goto _L3
_L2:
        if(!flag1) goto _L5; else goto _L4
_L4:
        indentingprintwriter.println("Current files:");
        indentingprintwriter.increaseIndent();
        as1 = mBaseDir.list();
        k = as1.length;
        for(l = 0; l < k; l++)
            indentingprintwriter.println(as1[l]);

        indentingprintwriter.decreaseIndent();
          goto _L3
        exception;
        throw exception;
_L5:
        indentingprintwriter.println("Active interfaces:");
        indentingprintwriter.increaseIndent();
        for(iterator = mActiveIfaces.keySet().iterator(); iterator.hasNext(); indentingprintwriter.println(networkidentityset.toString())) {
            s = (String)iterator.next();
            networkidentityset = (NetworkIdentitySet)mActiveIfaces.get(s);
            indentingprintwriter.print("iface=");
            indentingprintwriter.print(s);
            indentingprintwriter.print(" ident=");
        }

        indentingprintwriter.decreaseIndent();
        indentingprintwriter.println("Dev stats:");
        indentingprintwriter.increaseIndent();
        mDevRecorder.dumpLocked(indentingprintwriter, flag2);
        indentingprintwriter.decreaseIndent();
        indentingprintwriter.println("Xt stats:");
        indentingprintwriter.increaseIndent();
        mXtRecorder.dumpLocked(indentingprintwriter, flag2);
        indentingprintwriter.decreaseIndent();
        if(flag3) {
            indentingprintwriter.println("UID stats:");
            indentingprintwriter.increaseIndent();
            mUidRecorder.dumpLocked(indentingprintwriter, flag2);
            indentingprintwriter.decreaseIndent();
        }
        if(flag4) {
            indentingprintwriter.println("UID tag stats:");
            indentingprintwriter.increaseIndent();
            mUidTagRecorder.dumpLocked(indentingprintwriter, flag2);
            indentingprintwriter.decreaseIndent();
        }
        obj;
        JVM INSTR monitorexit ;
_L3:
    }

    public void forceUpdate() {
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.READ_NETWORK_USAGE_HISTORY", "NetworkStats");
        assertBandwidthControlEnabled();
        l = Binder.clearCallingIdentity();
        performPoll(3);
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public NetworkStats getDataLayerSnapshotForUid(int i) throws RemoteException {
        long l;
        if(Binder.getCallingUid() != i)
            mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", "NetworkStats");
        assertBandwidthControlEnabled();
        l = Binder.clearCallingIdentity();
        NetworkStats networkstats = mNetworkManager.getNetworkStatsUidDetail(i);
        NetworkStats networkstats1;
        Binder.restoreCallingIdentity(l);
        networkstats.spliceOperationsFrom(mUidOperations);
        networkstats1 = new NetworkStats(networkstats.getElapsedRealtime(), networkstats.size());
        android.net.NetworkStats.Entry entry = null;
        for(int j = 0; j < networkstats.size(); j++) {
            entry = networkstats.getValues(j, entry);
            entry.iface = NetworkStats.IFACE_ALL;
            networkstats1.combineValues(entry);
        }

        break MISSING_BLOCK_LABEL_129;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        return networkstats1;
    }

    public String[] getMobileIfaces() {
        return mMobileIfaces;
    }

    public long getNetworkTotalBytes(NetworkTemplate networktemplate, long l, long l1) {
        mContext.enforceCallingOrSelfPermission("android.permission.READ_NETWORK_USAGE_HISTORY", "NetworkStats");
        assertBandwidthControlEnabled();
        return internalGetSummaryForNetwork(networktemplate, l, l1).getTotalBytes();
    }

    public void incrementOperationCount(int i, int j, int k) {
        if(Binder.getCallingUid() != i)
            mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_NETWORK_ACCOUNTING", "NetworkStats");
        if(k < 0)
            throw new IllegalArgumentException("operation count can only be incremented");
        if(j == 0)
            throw new IllegalArgumentException("operation count must have specific tag");
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        int l = mActiveUidCounterSet.get(i, 0);
        mUidOperations.combineValues(mActiveIface, i, l, j, 0L, 0L, 0L, 0L, k);
        mUidOperations.combineValues(mActiveIface, i, l, 0, 0L, 0L, 0L, 0L, k);
        return;
    }

    public INetworkStatsSession openSession() {
        mContext.enforceCallingOrSelfPermission("android.permission.READ_NETWORK_USAGE_HISTORY", "NetworkStats");
        assertBandwidthControlEnabled();
        return new android.net.INetworkStatsSession.Stub() {

            private NetworkStatsCollection getUidComplete() {
                if(mUidComplete == null)
                    synchronized(mStatsLock) {
                        mUidComplete = mUidRecorder.getOrLoadCompleteLocked();
                    }
                return mUidComplete;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
            }

            private NetworkStatsCollection getUidTagComplete() {
                if(mUidTagComplete == null)
                    synchronized(mStatsLock) {
                        mUidTagComplete = mUidTagRecorder.getOrLoadCompleteLocked();
                    }
                return mUidTagComplete;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
            }

            public void close() {
                mUidComplete = null;
                mUidTagComplete = null;
            }

            public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate networktemplate, int i) {
                return internalGetHistoryForNetwork(networktemplate, i);
            }

            public NetworkStatsHistory getHistoryForUid(NetworkTemplate networktemplate, int i, int j, int k, int l) {
                NetworkStatsHistory networkstatshistory;
                if(k == 0)
                    networkstatshistory = getUidComplete().getHistory(networktemplate, i, j, k, l);
                else
                    networkstatshistory = getUidTagComplete().getHistory(networktemplate, i, j, k, l);
                return networkstatshistory;
            }

            public NetworkStats getSummaryForAllUid(NetworkTemplate networktemplate, long l, long l1, boolean flag) {
                NetworkStats networkstats = getUidComplete().getSummary(networktemplate, l, l1);
                if(flag)
                    networkstats.combineAllValues(getUidTagComplete().getSummary(networktemplate, l, l1));
                return networkstats;
            }

            public NetworkStats getSummaryForNetwork(NetworkTemplate networktemplate, long l, long l1) {
                return internalGetSummaryForNetwork(networktemplate, l, l1);
            }

            private NetworkStatsCollection mUidComplete;
            private NetworkStatsCollection mUidTagComplete;
            final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
        };
    }

    public void setUidForeground(int i, boolean flag) {
        int j;
        j = 0;
        mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_NETWORK_ACCOUNTING", "NetworkStats");
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        if(flag)
            j = 1;
        if(mActiveUidCounterSet.get(i, 0) != j) {
            mActiveUidCounterSet.put(i, j);
            NetworkManagementSocketTagger.setKernelCounterSet(i, j);
        }
        return;
    }

    public void systemReady() {
        mSystemReady = true;
        if(!isBandwidthControlEnabled()) {
            Slog.w("NetworkStats", "bandwidth controls disabled, unable to track stats");
        } else {
            mDevRecorder = buildRecorder("dev", mSettings.getDevConfig(), false);
            mXtRecorder = buildRecorder("xt", mSettings.getXtConfig(), false);
            mUidRecorder = buildRecorder("uid", mSettings.getUidConfig(), false);
            mUidTagRecorder = buildRecorder("uid_tag", mSettings.getUidTagConfig(), true);
            updatePersistThresholds();
            synchronized(mStatsLock) {
                maybeUpgradeLegacyStatsLocked();
                mDevStatsCached = mDevRecorder.getOrLoadCompleteLocked();
                mXtStatsCached = mXtRecorder.getOrLoadCompleteLocked();
                bootstrapStatsLocked();
            }
            IntentFilter intentfilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
            mContext.registerReceiver(mConnReceiver, intentfilter, "android.permission.CONNECTIVITY_INTERNAL", mHandler);
            IntentFilter intentfilter1 = new IntentFilter("android.net.conn.TETHER_STATE_CHANGED");
            mContext.registerReceiver(mTetherReceiver, intentfilter1, "android.permission.CONNECTIVITY_INTERNAL", mHandler);
            IntentFilter intentfilter2 = new IntentFilter("com.android.server.action.NETWORK_STATS_POLL");
            mContext.registerReceiver(mPollReceiver, intentfilter2, "android.permission.READ_NETWORK_USAGE_HISTORY", mHandler);
            IntentFilter intentfilter3 = new IntentFilter("android.intent.action.UID_REMOVED");
            mContext.registerReceiver(mRemovedReceiver, intentfilter3, null, mHandler);
            IntentFilter intentfilter4 = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
            mContext.registerReceiver(mShutdownReceiver, intentfilter4);
            try {
                mNetworkManager.registerObserver(mAlertObserver);
            }
            catch(RemoteException remoteexception) { }
            registerPollAlarmLocked();
            registerGlobalAlert();
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public static final String ACTION_NETWORK_STATS_POLL = "com.android.server.action.NETWORK_STATS_POLL";
    public static final String ACTION_NETWORK_STATS_UPDATED = "com.android.server.action.NETWORK_STATS_UPDATED";
    private static final int FLAG_PERSIST_ALL = 3;
    private static final int FLAG_PERSIST_FORCE = 256;
    private static final int FLAG_PERSIST_NETWORK = 1;
    private static final int FLAG_PERSIST_UID = 2;
    private static final boolean LOGV = false;
    private static final int MSG_PERFORM_POLL = 1;
    private static final int MSG_REGISTER_GLOBAL_ALERT = 3;
    private static final int MSG_UPDATE_IFACES = 2;
    private static final String PREFIX_DEV = "dev";
    private static final String PREFIX_UID = "uid";
    private static final String PREFIX_UID_TAG = "uid_tag";
    private static final String PREFIX_XT = "xt";
    private static final String TAG = "NetworkStats";
    private static final String TAG_NETSTATS_ERROR = "netstats_error";
    private String mActiveIface;
    private HashMap mActiveIfaces;
    private SparseIntArray mActiveUidCounterSet;
    private final IAlarmManager mAlarmManager;
    private INetworkManagementEventObserver mAlertObserver = new NetworkAlertObserver() {

        public void limitReached(String s, String s1) {
            mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkStats");
            if("globalAlert".equals(s)) {
                mHandler.obtainMessage(1, 1, 0).sendToTarget();
                mHandler.obtainMessage(3).sendToTarget();
            }
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final File mBaseDir;
    private IConnectivityManager mConnManager;
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            updateIfaces();
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final Context mContext;
    private NetworkStatsRecorder mDevRecorder;
    private NetworkStatsCollection mDevStatsCached;
    private long mGlobalAlertBytes;
    private final Handler mHandler;
    private android.os.Handler.Callback mHandlerCallback = new android.os.Handler.Callback() {

        public boolean handleMessage(Message message) {
            boolean flag = true;
            message.what;
            JVM INSTR tableswitch 1 3: default 32
        //                       1 36
        //                       2 52
        //                       3 62;
               goto _L1 _L2 _L3 _L4
_L1:
            flag = false;
_L6:
            return flag;
_L2:
            int i = message.arg1;
            performPoll(i);
            continue; /* Loop/switch isn't completed */
_L3:
            updateIfaces();
            continue; /* Loop/switch isn't completed */
_L4:
            registerGlobalAlert();
            if(true) goto _L6; else goto _L5
_L5:
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final HandlerThread mHandlerThread;
    private int mLastPhoneNetworkType;
    private int mLastPhoneState;
    private String mMobileIfaces[];
    private final INetworkManagementService mNetworkManager;
    private final DropBoxNonMonotonicObserver mNonMonotonicObserver;
    private long mPersistThreshold;
    private PhoneStateListener mPhoneListener = new PhoneStateListener() {

        public void onDataConnectionStateChanged(int i, int j) {
            boolean flag;
            boolean flag1;
            if(i != mLastPhoneState)
                flag = true;
            else
                flag = false;
            if(j != mLastPhoneNetworkType)
                flag1 = true;
            else
                flag1 = false;
            if(flag1 && !flag)
                mHandler.sendMessageDelayed(mHandler.obtainMessage(2), 1000L);
            mLastPhoneState = i;
            mLastPhoneNetworkType = j;
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private PendingIntent mPollIntent;
    private BroadcastReceiver mPollReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            performPoll(3);
            registerGlobalAlert();
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private BroadcastReceiver mRemovedReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            int i = intent.getIntExtra("android.intent.extra.UID", 0);
            Object obj = mStatsLock;
            obj;
            JVM INSTR monitorenter ;
            mWakeLock.acquire();
            removeUidLocked(i);
            mWakeLock.release();
            obj;
            JVM INSTR monitorexit ;
            return;
            Exception exception1;
            exception1;
            mWakeLock.release();
            throw exception1;
            Exception exception;
            exception;
            throw exception;
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final NetworkStatsSettings mSettings;
    private BroadcastReceiver mShutdownReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            Object obj = mStatsLock;
            obj;
            JVM INSTR monitorenter ;
            shutdownLocked();
            return;
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final Object mStatsLock;
    private final File mSystemDir;
    private boolean mSystemReady;
    private final TelephonyManager mTeleManager;
    private BroadcastReceiver mTetherReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            performPoll(1);
        }

        final NetworkStatsService this$0;

             {
                this$0 = NetworkStatsService.this;
                super();
            }
    };
    private final TrustedTime mTime;
    private NetworkStats mUidOperations;
    private NetworkStatsRecorder mUidRecorder;
    private NetworkStatsRecorder mUidTagRecorder;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private NetworkStatsRecorder mXtRecorder;
    private NetworkStatsCollection mXtStatsCached;








/*
    static int access$1402(NetworkStatsService networkstatsservice, int i) {
        networkstatsservice.mLastPhoneState = i;
        return i;
    }

*/



/*
    static int access$1502(NetworkStatsService networkstatsservice, int i) {
        networkstatsservice.mLastPhoneNetworkType = i;
        return i;
    }

*/








}
