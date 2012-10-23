// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.net.NetworkStats;
import android.net.NetworkTemplate;
import android.os.DropBoxManager;
import android.util.Log;
import android.util.MathUtils;
import com.android.internal.util.*;
import com.google.android.collect.Sets;
import java.io.*;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import libcore.io.IoUtils;

// Referenced classes of package com.android.server.net:
//            NetworkStatsCollection, NetworkIdentitySet

public class NetworkStatsRecorder {
    public static class RemoveUidRewriter
        implements com.android.internal.util.FileRotator.Rewriter {

        public void read(InputStream inputstream) throws IOException {
            mTemp.read(inputstream);
            mTemp.clearDirty();
            mTemp.removeUid(mUid);
        }

        public void reset() {
            mTemp.reset();
        }

        public boolean shouldWrite() {
            return mTemp.isDirty();
        }

        public void write(OutputStream outputstream) throws IOException {
            mTemp.write(new DataOutputStream(outputstream));
        }

        private final NetworkStatsCollection mTemp;
        private final int mUid;

        public RemoveUidRewriter(long l, int i) {
            mTemp = new NetworkStatsCollection(l);
            mUid = i;
        }
    }

    private static class CombiningRewriter
        implements com.android.internal.util.FileRotator.Rewriter {

        public void read(InputStream inputstream) throws IOException {
            mCollection.read(inputstream);
        }

        public void reset() {
        }

        public boolean shouldWrite() {
            return true;
        }

        public void write(OutputStream outputstream) throws IOException {
            mCollection.write(new DataOutputStream(outputstream));
            mCollection.reset();
        }

        private final NetworkStatsCollection mCollection;

        public CombiningRewriter(NetworkStatsCollection networkstatscollection) {
            mCollection = (NetworkStatsCollection)Preconditions.checkNotNull(networkstatscollection, "missing NetworkStatsCollection");
        }
    }


    public NetworkStatsRecorder(FileRotator filerotator, android.net.NetworkStats.NonMonotonicObserver nonmonotonicobserver, DropBoxManager dropboxmanager, String s, long l, boolean flag) {
        mPersistThresholdBytes = 0x200000L;
        mRotator = (FileRotator)Preconditions.checkNotNull(filerotator, "missing FileRotator");
        mObserver = (android.net.NetworkStats.NonMonotonicObserver)Preconditions.checkNotNull(nonmonotonicobserver, "missing NonMonotonicObserver");
        mDropBox = (DropBoxManager)Preconditions.checkNotNull(dropboxmanager, "missing DropBoxManager");
        mCookie = s;
        mBucketDuration = l;
        mOnlyTags = flag;
        mPending = new NetworkStatsCollection(l);
        mSinceBoot = new NetworkStatsCollection(l);
        mPendingRewriter = new CombiningRewriter(mPending);
    }

    private void recoverFromWtf() {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        mRotator.dumpAll(bytearrayoutputstream);
_L2:
        IoUtils.closeQuietly(bytearrayoutputstream);
        mDropBox.addData("netstats_dump", bytearrayoutputstream.toByteArray(), 0);
        mRotator.deleteAll();
        return;
        IOException ioexception;
        ioexception;
        bytearrayoutputstream.reset();
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        IoUtils.closeQuietly(bytearrayoutputstream);
        throw exception;
    }

    public void dumpLocked(IndentingPrintWriter indentingprintwriter, boolean flag) {
        indentingprintwriter.print("Pending bytes: ");
        indentingprintwriter.println(mPending.getTotalBytes());
        if(flag) {
            indentingprintwriter.println("Complete history:");
            getOrLoadCompleteLocked().dump(indentingprintwriter);
        } else {
            indentingprintwriter.println("History since boot:");
            mSinceBoot.dump(indentingprintwriter);
        }
    }

    public void forcePersistLocked(long l) {
        if(!mPending.isDirty())
            break MISSING_BLOCK_LABEL_37;
        mRotator.rewriteActive(mPendingRewriter, l);
        mRotator.maybeRotate(l);
        mPending.reset();
_L1:
        return;
        IOException ioexception;
        ioexception;
        Log.wtf("NetworkStatsRecorder", "problem persisting pending stats", ioexception);
        recoverFromWtf();
          goto _L1
    }

    public NetworkStatsCollection getOrLoadCompleteLocked() {
        NetworkStatsCollection networkstatscollection;
        NetworkStatsCollection networkstatscollection1;
        if(mComplete != null)
            networkstatscollection = (NetworkStatsCollection)mComplete.get();
        else
            networkstatscollection = null;
        if(networkstatscollection != null) goto _L2; else goto _L1
_L1:
        networkstatscollection1 = new NetworkStatsCollection(mBucketDuration);
        mRotator.readMatching(networkstatscollection1, 0x8000000000000000L, 0x7fffffffffffffffL);
        networkstatscollection1.recordCollection(mPending);
        mComplete = new WeakReference(networkstatscollection1);
_L5:
        return networkstatscollection1;
        IOException ioexception;
        ioexception;
        networkstatscollection1 = networkstatscollection;
_L3:
        Log.wtf("NetworkStatsRecorder", "problem completely reading network stats", ioexception);
        recoverFromWtf();
        continue; /* Loop/switch isn't completed */
        ioexception;
        if(true) goto _L3; else goto _L2
_L2:
        networkstatscollection1 = networkstatscollection;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public android.net.NetworkStats.Entry getTotalSinceBootLocked(NetworkTemplate networktemplate) {
        return mSinceBoot.getSummary(networktemplate, 0x8000000000000000L, 0x7fffffffffffffffL).getTotal(null);
    }

    public void importLegacyNetworkLocked(File file) throws IOException {
        mRotator.deleteAll();
        NetworkStatsCollection networkstatscollection = new NetworkStatsCollection(mBucketDuration);
        networkstatscollection.readLegacyNetwork(file);
        long l = networkstatscollection.getStartMillis();
        long l1 = networkstatscollection.getEndMillis();
        if(!networkstatscollection.isEmpty()) {
            mRotator.rewriteActive(new CombiningRewriter(networkstatscollection), l);
            mRotator.maybeRotate(l1);
        }
    }

    public void importLegacyUidLocked(File file) throws IOException {
        mRotator.deleteAll();
        NetworkStatsCollection networkstatscollection = new NetworkStatsCollection(mBucketDuration);
        networkstatscollection.readLegacyUid(file, mOnlyTags);
        long l = networkstatscollection.getStartMillis();
        long l1 = networkstatscollection.getEndMillis();
        if(!networkstatscollection.isEmpty()) {
            mRotator.rewriteActive(new CombiningRewriter(networkstatscollection), l);
            mRotator.maybeRotate(l1);
        }
    }

    public void maybePersistLocked(long l) {
        if(mPending.getTotalBytes() >= mPersistThresholdBytes)
            forcePersistLocked(l);
        else
            mRotator.maybeRotate(l);
    }

    public void recordSnapshotLocked(NetworkStats networkstats, Map map, long l) {
        HashSet hashset = Sets.newHashSet();
        if(networkstats != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(mLastSnapshot == null) {
            mLastSnapshot = networkstats;
            continue; /* Loop/switch isn't completed */
        }
        NetworkStatsCollection networkstatscollection;
        NetworkStats networkstats1;
        long l1;
        android.net.NetworkStats.Entry entry;
        int i;
        if(mComplete != null)
            networkstatscollection = (NetworkStatsCollection)mComplete.get();
        else
            networkstatscollection = null;
        networkstats1 = NetworkStats.subtract(networkstats, mLastSnapshot, mObserver, mCookie);
        l1 = l - networkstats1.getElapsedRealtime();
        entry = null;
        i = 0;
        while(i < networkstats1.size())  {
            entry = networkstats1.getValues(i, entry);
            NetworkIdentitySet networkidentityset = (NetworkIdentitySet)map.get(entry.iface);
            if(networkidentityset == null)
                hashset.add(entry.iface);
            else
            if(!entry.isEmpty()) {
                boolean flag;
                if(entry.tag == 0)
                    flag = true;
                else
                    flag = false;
                if(flag != mOnlyTags) {
                    mPending.recordData(networkidentityset, entry.uid, entry.set, entry.tag, l1, l, entry);
                    if(mSinceBoot != null)
                        mSinceBoot.recordData(networkidentityset, entry.uid, entry.set, entry.tag, l1, l, entry);
                    if(networkstatscollection != null) {
                        int j = entry.uid;
                        int k = entry.set;
                        int i1 = entry.tag;
                        networkstatscollection.recordData(networkidentityset, j, k, i1, l1, l, entry);
                    }
                }
            }
            i++;
        }
        mLastSnapshot = networkstats;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void removeUidLocked(int i) {
        NetworkStatsCollection networkstatscollection;
        try {
            mRotator.rewriteAll(new RemoveUidRewriter(mBucketDuration, i));
        }
        catch(IOException ioexception) {
            Log.wtf("NetworkStatsRecorder", (new StringBuilder()).append("problem removing UID ").append(i).toString(), ioexception);
            recoverFromWtf();
        }
        if(mLastSnapshot != null)
            mLastSnapshot = mLastSnapshot.withoutUid(i);
        if(mComplete != null)
            networkstatscollection = (NetworkStatsCollection)mComplete.get();
        else
            networkstatscollection = null;
        if(networkstatscollection != null)
            networkstatscollection.removeUid(i);
    }

    public void resetLocked() {
        mLastSnapshot = null;
        mPending.reset();
        mSinceBoot.reset();
        mComplete.clear();
    }

    public void setPersistThreshold(long l) {
        mPersistThresholdBytes = MathUtils.constrain(l, 1024L, 0x6400000L);
    }

    private static final boolean DUMP_BEFORE_DELETE = true;
    private static final boolean LOGD = false;
    private static final boolean LOGV = false;
    private static final String TAG = "NetworkStatsRecorder";
    private static final String TAG_NETSTATS_DUMP = "netstats_dump";
    private final long mBucketDuration;
    private WeakReference mComplete;
    private final String mCookie;
    private final DropBoxManager mDropBox;
    private NetworkStats mLastSnapshot;
    private final android.net.NetworkStats.NonMonotonicObserver mObserver;
    private final boolean mOnlyTags;
    private final NetworkStatsCollection mPending;
    private final CombiningRewriter mPendingRewriter;
    private long mPersistThresholdBytes;
    private final FileRotator mRotator;
    private final NetworkStatsCollection mSinceBoot;
}
