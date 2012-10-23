// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.net.*;
import com.android.internal.os.AtomicFile;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Objects;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import java.io.*;
import java.net.ProtocolException;
import java.util.*;
import libcore.io.IoUtils;

// Referenced classes of package com.android.server.net:
//            NetworkIdentitySet

public class NetworkStatsCollection
    implements com.android.internal.util.FileRotator.Reader {
    private static class Key
        implements Comparable {

        public int compareTo(Key key) {
            return Integer.compare(uid, key.uid);
        }

        public volatile int compareTo(Object obj) {
            return compareTo((Key)obj);
        }

        public boolean equals(Object obj) {
            boolean flag = false;
            if(obj instanceof Key) {
                Key key = (Key)obj;
                if(uid == key.uid && set == key.set && tag == key.tag && Objects.equal(ident, key.ident))
                    flag = true;
            }
            return flag;
        }

        public int hashCode() {
            return hashCode;
        }

        private final int hashCode;
        public final NetworkIdentitySet ident;
        public final int set;
        public final int tag;
        public final int uid;

        public Key(NetworkIdentitySet networkidentityset, int i, int j, int k) {
            ident = networkidentityset;
            uid = i;
            set = j;
            tag = k;
            Object aobj[] = new Object[4];
            aobj[0] = networkidentityset;
            aobj[1] = Integer.valueOf(i);
            aobj[2] = Integer.valueOf(j);
            aobj[3] = Integer.valueOf(k);
            hashCode = Objects.hashCode(aobj);
        }
    }


    public NetworkStatsCollection(long l) {
        mStats = Maps.newHashMap();
        mBucketDuration = l;
        reset();
    }

    private int estimateBuckets() {
        return (int)(Math.min(mEndMillis - mStartMillis, 0xb43e9400L) / mBucketDuration);
    }

    private NetworkStatsHistory findOrCreateHistory(NetworkIdentitySet networkidentityset, int i, int j, int k) {
        Key key = new Key(networkidentityset, i, j, k);
        NetworkStatsHistory networkstatshistory = (NetworkStatsHistory)mStats.get(key);
        NetworkStatsHistory networkstatshistory1 = null;
        if(networkstatshistory == null)
            networkstatshistory1 = new NetworkStatsHistory(mBucketDuration, 10);
        else
        if(networkstatshistory.getBucketDuration() != mBucketDuration)
            networkstatshistory1 = new NetworkStatsHistory(networkstatshistory, mBucketDuration);
        if(networkstatshistory1 != null)
            mStats.put(key, networkstatshistory1);
        else
            networkstatshistory1 = networkstatshistory;
        return networkstatshistory1;
    }

    private void noteRecordedHistory(long l, long l1, long l2) {
        if(l < mStartMillis)
            mStartMillis = l;
        if(l1 > mEndMillis)
            mEndMillis = l1;
        mTotalBytes = l2 + mTotalBytes;
        mDirty = true;
    }

    private void recordHistory(Key key, NetworkStatsHistory networkstatshistory) {
        if(networkstatshistory.size() != 0) {
            noteRecordedHistory(networkstatshistory.getStart(), networkstatshistory.getEnd(), networkstatshistory.getTotalBytes());
            NetworkStatsHistory networkstatshistory1 = (NetworkStatsHistory)mStats.get(key);
            if(networkstatshistory1 == null) {
                networkstatshistory1 = new NetworkStatsHistory(networkstatshistory.getBucketDuration());
                mStats.put(key, networkstatshistory1);
            }
            networkstatshistory1.recordEntireHistory(networkstatshistory);
        }
    }

    private static boolean templateMatches(NetworkTemplate networktemplate, NetworkIdentitySet networkidentityset) {
        Iterator iterator = networkidentityset.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(!networktemplate.matches((NetworkIdentity)iterator.next())) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void clearDirty() {
        mDirty = false;
    }

    public void dump(IndentingPrintWriter indentingprintwriter) {
        ArrayList arraylist = Lists.newArrayList();
        arraylist.addAll(mStats.keySet());
        Collections.sort(arraylist);
        for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); indentingprintwriter.decreaseIndent()) {
            Key key = (Key)iterator.next();
            indentingprintwriter.print("ident=");
            indentingprintwriter.print(key.ident.toString());
            indentingprintwriter.print(" uid=");
            indentingprintwriter.print(key.uid);
            indentingprintwriter.print(" set=");
            indentingprintwriter.print(NetworkStats.setToString(key.set));
            indentingprintwriter.print(" tag=");
            indentingprintwriter.println(NetworkStats.tagToString(key.tag));
            NetworkStatsHistory networkstatshistory = (NetworkStatsHistory)mStats.get(key);
            indentingprintwriter.increaseIndent();
            networkstatshistory.dump(indentingprintwriter, true);
        }

    }

    public long getEndMillis() {
        return mEndMillis;
    }

    public long getFirstAtomicBucketMillis() {
        long l = 0x7fffffffffffffffL;
        if(mStartMillis != l)
            l = mStartMillis + mBucketDuration;
        return l;
    }

    public NetworkStatsHistory getHistory(NetworkTemplate networktemplate, int i, int j, int k, int l) {
        return getHistory(networktemplate, i, j, k, l, 0x8000000000000000L, 0x7fffffffffffffffL);
    }

    public NetworkStatsHistory getHistory(NetworkTemplate networktemplate, int i, int j, int k, int l, long l1, 
            long l2) {
        NetworkStatsHistory networkstatshistory = new NetworkStatsHistory(mBucketDuration, estimateBuckets(), l);
        Iterator iterator = mStats.entrySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            Key key = (Key)entry.getKey();
            boolean flag;
            if(j == -1 || key.set == j)
                flag = true;
            else
                flag = false;
            if(key.uid == i && flag && key.tag == k && templateMatches(networktemplate, key.ident))
                networkstatshistory.recordHistory((NetworkStatsHistory)entry.getValue(), l1, l2);
        } while(true);
        return networkstatshistory;
    }

    public long getStartMillis() {
        return mStartMillis;
    }

    public NetworkStats getSummary(NetworkTemplate networktemplate, long l, long l1) {
        long l2 = System.currentTimeMillis();
        NetworkStats networkstats = new NetworkStats(l1 - l, 24);
        android.net.NetworkStats.Entry entry = new android.net.NetworkStats.Entry();
        android.net.NetworkStatsHistory.Entry entry1 = null;
        if(l != l1) {
            Iterator iterator = mStats.entrySet().iterator();
            while(iterator.hasNext())  {
                java.util.Map.Entry entry2 = (java.util.Map.Entry)iterator.next();
                Key key = (Key)entry2.getKey();
                if(templateMatches(networktemplate, key.ident)) {
                    entry1 = ((NetworkStatsHistory)entry2.getValue()).getValues(l, l1, l2, entry1);
                    entry.iface = NetworkStats.IFACE_ALL;
                    entry.uid = key.uid;
                    entry.set = key.set;
                    entry.tag = key.tag;
                    entry.rxBytes = entry1.rxBytes;
                    entry.rxPackets = entry1.rxPackets;
                    entry.txBytes = entry1.txBytes;
                    entry.txPackets = entry1.txPackets;
                    entry.operations = entry1.operations;
                    if(!entry.isEmpty())
                        networkstats.combineValues(entry);
                }
            }
        }
        return networkstats;
    }

    public long getTotalBytes() {
        return mTotalBytes;
    }

    public boolean isDirty() {
        return mDirty;
    }

    public boolean isEmpty() {
        boolean flag;
        if(mStartMillis == 0x7fffffffffffffffL && mEndMillis == 0x8000000000000000L)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void read(DataInputStream datainputstream) throws IOException {
        int i = datainputstream.readInt();
        if(i != 0x414e4554)
            throw new ProtocolException((new StringBuilder()).append("unexpected magic: ").append(i).toString());
        int j = datainputstream.readInt();
        int k;
        switch(j) {
        default:
            throw new ProtocolException((new StringBuilder()).append("unexpected version: ").append(j).toString());

        case 16: // '\020'
            k = datainputstream.readInt();
            break;
        }
        for(int l = 0; l < k; l++) {
            NetworkIdentitySet networkidentityset = new NetworkIdentitySet(datainputstream);
            int i1 = datainputstream.readInt();
            for(int j1 = 0; j1 < i1; j1++)
                recordHistory(new Key(networkidentityset, datainputstream.readInt(), datainputstream.readInt(), datainputstream.readInt()), new NetworkStatsHistory(datainputstream));

        }

    }

    public void read(InputStream inputstream) throws IOException {
        read(new DataInputStream(inputstream));
    }

    public void readLegacyNetwork(File file) throws IOException {
        AtomicFile atomicfile;
        Object obj;
        atomicfile = new AtomicFile(file);
        obj = null;
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(atomicfile.openRead()));
        int i = datainputstream.readInt();
        if(i != 0x414e4554)
            throw new ProtocolException((new StringBuilder()).append("unexpected magic: ").append(i).toString());
          goto _L1
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        obj = datainputstream;
_L7:
        IoUtils.closeQuietly(((AutoCloseable) (obj)));
_L4:
        return;
_L1:
        int j = datainputstream.readInt();
        switch(j) {
        default:
            throw new ProtocolException((new StringBuilder()).append("unexpected version: ").append(j).toString());

        case 1: // '\001'
            break;
        }
          goto _L2
        Exception exception;
        exception;
        obj = datainputstream;
_L5:
        IoUtils.closeQuietly(((AutoCloseable) (obj)));
        throw exception;
_L2:
        int k;
        int l;
        k = datainputstream.readInt();
        l = 0;
_L3:
        if(l >= k)
            break MISSING_BLOCK_LABEL_218;
        NetworkIdentitySet networkidentityset = new NetworkIdentitySet(datainputstream);
        NetworkStatsHistory networkstatshistory = new NetworkStatsHistory(datainputstream);
        recordHistory(new Key(networkidentityset, -1, -1, 0), networkstatshistory);
        l++;
          goto _L3
        IoUtils.closeQuietly(datainputstream);
          goto _L4
        exception;
          goto _L5
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void readLegacyUid(File file, boolean flag) throws IOException {
        AtomicFile atomicfile;
        Object obj;
        atomicfile = new AtomicFile(file);
        obj = null;
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(atomicfile.openRead()));
        int i = datainputstream.readInt();
        if(i != 0x414e4554)
            throw new ProtocolException((new StringBuilder()).append("unexpected magic: ").append(i).toString());
          goto _L1
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        obj = datainputstream;
_L19:
        IoUtils.closeQuietly(((AutoCloseable) (obj)));
_L16:
        return;
_L1:
        int j = datainputstream.readInt();
        j;
        JVM INSTR tableswitch 1 4: default 128
    //                   1 317
    //                   2 317
    //                   3 171
    //                   4 171;
           goto _L2 _L3 _L3 _L4 _L4
_L2:
        throw new ProtocolException((new StringBuilder()).append("unexpected version: ").append(j).toString());
        Exception exception;
        exception;
        obj = datainputstream;
_L17:
        IoUtils.closeQuietly(((AutoCloseable) (obj)));
        throw exception;
_L4:
        int k;
        int l;
        k = datainputstream.readInt();
        l = 0;
_L15:
        if(l >= k) goto _L3; else goto _L5
_L5:
        NetworkIdentitySet networkidentityset;
        int i1;
        int j1;
        networkidentityset = new NetworkIdentitySet(datainputstream);
        i1 = datainputstream.readInt();
        j1 = 0;
_L12:
        if(j1 >= i1) goto _L7; else goto _L6
_L6:
        int k1 = datainputstream.readInt();
        if(j < 4) goto _L9; else goto _L8
_L8:
        int l1 = datainputstream.readInt();
_L13:
        int i2;
        Key key;
        NetworkStatsHistory networkstatshistory;
        i2 = datainputstream.readInt();
        key = new Key(networkidentityset, k1, l1, i2);
        networkstatshistory = new NetworkStatsHistory(datainputstream);
        if(i2 != 0) goto _L11; else goto _L10
_L10:
        boolean flag1 = true;
_L14:
        if(flag1 != flag)
            recordHistory(key, networkstatshistory);
        j1++;
          goto _L12
_L9:
        l1 = 0;
          goto _L13
_L11:
        flag1 = false;
          goto _L14
_L7:
        l++;
          goto _L15
_L3:
        IoUtils.closeQuietly(datainputstream);
          goto _L16
        exception;
          goto _L17
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
        if(true) goto _L19; else goto _L18
_L18:
    }

    public void recordCollection(NetworkStatsCollection networkstatscollection) {
        java.util.Map.Entry entry;
        for(Iterator iterator = networkstatscollection.mStats.entrySet().iterator(); iterator.hasNext(); recordHistory((Key)entry.getKey(), (NetworkStatsHistory)entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

    }

    public void recordData(NetworkIdentitySet networkidentityset, int i, int j, int k, long l, long l1, android.net.NetworkStats.Entry entry) {
        NetworkStatsHistory networkstatshistory = findOrCreateHistory(networkidentityset, i, j, k);
        networkstatshistory.recordData(l, l1, entry);
        noteRecordedHistory(networkstatshistory.getStart(), networkstatshistory.getEnd(), entry.rxBytes + entry.txBytes);
    }

    public void removeUid(int i) {
        ArrayList arraylist = Lists.newArrayList();
        arraylist.addAll(mStats.keySet());
        Iterator iterator = arraylist.iterator();
        do {
            if(!iterator.hasNext())
                break;
            Key key = (Key)iterator.next();
            if(key.uid == i) {
                if(key.tag == 0) {
                    NetworkStatsHistory networkstatshistory = (NetworkStatsHistory)mStats.get(key);
                    findOrCreateHistory(key.ident, -4, 0, 0).recordEntireHistory(networkstatshistory);
                }
                mStats.remove(key);
                mDirty = true;
            }
        } while(true);
    }

    public void reset() {
        mStats.clear();
        mStartMillis = 0x7fffffffffffffffL;
        mEndMillis = 0x8000000000000000L;
        mTotalBytes = 0L;
        mDirty = false;
    }

    public void write(DataOutputStream dataoutputstream) throws IOException {
        HashMap hashmap = Maps.newHashMap();
        Key key1;
        ArrayList arraylist1;
        for(Iterator iterator = mStats.keySet().iterator(); iterator.hasNext(); arraylist1.add(key1)) {
            key1 = (Key)iterator.next();
            arraylist1 = (ArrayList)hashmap.get(key1.ident);
            if(arraylist1 == null) {
                arraylist1 = Lists.newArrayList();
                hashmap.put(key1.ident, arraylist1);
            }
        }

        dataoutputstream.writeInt(0x414e4554);
        dataoutputstream.writeInt(16);
        dataoutputstream.writeInt(hashmap.size());
        for(Iterator iterator1 = hashmap.keySet().iterator(); iterator1.hasNext();) {
            NetworkIdentitySet networkidentityset = (NetworkIdentitySet)iterator1.next();
            ArrayList arraylist = (ArrayList)hashmap.get(networkidentityset);
            networkidentityset.writeToStream(dataoutputstream);
            dataoutputstream.writeInt(arraylist.size());
            Iterator iterator2 = arraylist.iterator();
            while(iterator2.hasNext())  {
                Key key = (Key)iterator2.next();
                NetworkStatsHistory networkstatshistory = (NetworkStatsHistory)mStats.get(key);
                dataoutputstream.writeInt(key.uid);
                dataoutputstream.writeInt(key.set);
                dataoutputstream.writeInt(key.tag);
                networkstatshistory.writeToStream(dataoutputstream);
            }
        }

        dataoutputstream.flush();
    }

    private static final int FILE_MAGIC = 0x414e4554;
    private static final int VERSION_NETWORK_INIT = 1;
    private static final int VERSION_UID_INIT = 1;
    private static final int VERSION_UID_WITH_IDENT = 2;
    private static final int VERSION_UID_WITH_SET = 4;
    private static final int VERSION_UID_WITH_TAG = 3;
    private static final int VERSION_UNIFIED_INIT = 16;
    private final long mBucketDuration;
    private boolean mDirty;
    private long mEndMillis;
    private long mStartMillis;
    private HashMap mStats;
    private long mTotalBytes;
}
