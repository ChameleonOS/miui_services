// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.*;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.app.IUsageStats;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.AtomicFile;
import com.android.internal.os.PkgUsageStats;
import com.android.internal.util.FastXmlSerializer;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.xmlpull.v1.*;

public final class UsageStatsService extends com.android.internal.app.IUsageStats.Stub {
    private class PkgUsageStatsExtended {

        void addLaunchCount(String s) {
            TimeStats timestats = (TimeStats)mLaunchTimes.get(s);
            if(timestats == null) {
                timestats = new TimeStats();
                mLaunchTimes.put(s, timestats);
            }
            timestats.incCount();
        }

        void addLaunchTime(String s, int i) {
            TimeStats timestats = (TimeStats)mLaunchTimes.get(s);
            if(timestats == null) {
                timestats = new TimeStats();
                mLaunchTimes.put(s, timestats);
            }
            timestats.add(i);
        }

        void clear() {
            mLaunchTimes.clear();
            mLaunchCount = 0;
            mUsageTime = 0L;
        }

        void updatePause() {
            mPausedTime = SystemClock.elapsedRealtime();
            mUsageTime = mUsageTime + (mPausedTime - mResumedTime);
        }

        void updateResume(String s, boolean flag) {
            if(flag)
                mLaunchCount = 1 + mLaunchCount;
            mResumedTime = SystemClock.elapsedRealtime();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeInt(mLaunchCount);
            parcel.writeLong(mUsageTime);
            int i = mLaunchTimes.size();
            parcel.writeInt(i);
            if(i > 0) {
                java.util.Map.Entry entry;
                for(Iterator iterator = mLaunchTimes.entrySet().iterator(); iterator.hasNext(); ((TimeStats)entry.getValue()).writeToParcel(parcel)) {
                    entry = (java.util.Map.Entry)iterator.next();
                    parcel.writeString((String)entry.getKey());
                }

            }
        }

        int mLaunchCount;
        final HashMap mLaunchTimes;
        long mPausedTime;
        long mResumedTime;
        long mUsageTime;
        final UsageStatsService this$0;

        PkgUsageStatsExtended() {
            this$0 = UsageStatsService.this;
            super();
            mLaunchTimes = new HashMap();
            mLaunchCount = 0;
            mUsageTime = 0L;
        }

        PkgUsageStatsExtended(Parcel parcel) {
            this$0 = UsageStatsService.this;
            super();
            mLaunchTimes = new HashMap();
            mLaunchCount = parcel.readInt();
            mUsageTime = parcel.readLong();
            int i = parcel.readInt();
            for(int j = 0; j < i; j++) {
                String s = parcel.readString();
                TimeStats timestats = new TimeStats(parcel);
                mLaunchTimes.put(s, timestats);
            }

        }
    }

    static class TimeStats {

        void add(int i) {
            int ai[];
            int j;
            ai = UsageStatsService.LAUNCH_TIME_BINS;
            j = 0;
_L3:
            if(j >= 9)
                break MISSING_BLOCK_LABEL_42;
            if(i >= ai[j]) goto _L2; else goto _L1
_L1:
            int ai2[] = times;
            ai2[j] = 1 + ai2[j];
_L4:
            return;
_L2:
            j++;
              goto _L3
            int ai1[] = times;
            ai1[9] = 1 + ai1[9];
              goto _L4
        }

        void incCount() {
            count = 1 + count;
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeInt(count);
            int ai[] = times;
            for(int i = 0; i < 10; i++)
                parcel.writeInt(ai[i]);

        }

        int count;
        int times[];

        TimeStats() {
            times = new int[10];
        }

        TimeStats(Parcel parcel) {
            times = new int[10];
            count = parcel.readInt();
            int ai[] = times;
            for(int i = 0; i < 10; i++)
                ai[i] = parcel.readInt();

        }
    }


    UsageStatsService(String s) {
        mDir = new File(s);
        mCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        mDir.mkdir();
        File file = mDir.getParentFile();
        String as[] = file.list();
        if(as != null) {
            String s1 = (new StringBuilder()).append(mDir.getName()).append(".").toString();
            int i = as.length;
            do {
                if(i <= 0)
                    break;
                i--;
                if(as[i].startsWith(s1)) {
                    Slog.i("UsageStats", (new StringBuilder()).append("Deleting old usage file: ").append(as[i]).toString());
                    (new File(file, as[i])).delete();
                }
            } while(true);
        }
        mFileLeaf = getCurrentDateStr("usage-");
        mFile = new File(mDir, mFileLeaf);
        mHistoryFile = new AtomicFile(new File(mDir, "usage-history.xml"));
        readStatsFromFile();
        readHistoryStatsFromFile();
        mLastWriteElapsedTime.set(SystemClock.elapsedRealtime());
        mLastWriteDay.set(mCal.get(6));
    }

    private void checkFileLimitFLOCK() {
        ArrayList arraylist = getUsageStatsFileListFLOCK();
        if(arraylist != null) goto _L2; else goto _L1
_L1:
        int i;
        return;
_L2:
        if((i = arraylist.size()) > 5) {
            Collections.sort(arraylist);
            int j = i - 5;
            int k = 0;
            while(k < j)  {
                String s = (String)arraylist.get(k);
                File file = new File(mDir, s);
                Slog.i("UsageStats", (new StringBuilder()).append("Deleting usage file : ").append(s).toString());
                file.delete();
                k++;
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void collectDumpInfoFLOCK(PrintWriter printwriter, boolean flag, boolean flag1, HashSet hashset) {
        ArrayList arraylist = getUsageStatsFileListFLOCK();
        if(arraylist != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Iterator iterator;
        Collections.sort(arraylist);
        iterator = arraylist.iterator();
_L5:
        if(!iterator.hasNext()) goto _L1; else goto _L3
_L3:
        String s = (String)iterator.next();
        if(flag1 && s.equalsIgnoreCase(mFileLeaf)) goto _L5; else goto _L4
_L4:
        File file;
        String s1;
        file = new File(mDir, s);
        s1 = s.substring("usage-".length());
        collectDumpInfoFromParcelFLOCK(getParcelForFile(file), printwriter, s1, flag, hashset);
        if(flag1)
            file.delete();
          goto _L5
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w("UsageStats", (new StringBuilder()).append("Failed with ").append(filenotfoundexception).append(" when collecting dump info from file : ").append(s).toString());
          goto _L1
        IOException ioexception;
        ioexception;
        Slog.w("UsageStats", (new StringBuilder()).append("Failed with ").append(ioexception).append(" when collecting dump info from file : ").append(s).toString());
          goto _L5
    }

    private void collectDumpInfoFromParcelFLOCK(Parcel parcel, PrintWriter printwriter, String s, boolean flag, HashSet hashset) {
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder(512);
        if(flag) {
            stringbuilder.append("D:");
            stringbuilder.append(4);
            stringbuilder.append(',');
        } else {
            stringbuilder.append("Date: ");
        }
        stringbuilder.append(s);
        if(parcel.readInt() == 1007) goto _L2; else goto _L1
_L1:
        stringbuilder.append(" (old data version)");
        printwriter.println(stringbuilder.toString());
_L4:
        return;
_L2:
        int i;
        printwriter.println(stringbuilder.toString());
        i = parcel.readInt();
_L6:
        if(i <= 0) goto _L4; else goto _L3
_L3:
        String s1;
        i--;
        s1 = parcel.readString();
        if(s1 == null) goto _L4; else goto _L5
_L5:
        stringbuilder.setLength(0);
        PkgUsageStatsExtended pkgusagestatsextended = new PkgUsageStatsExtended(parcel);
        if(hashset == null || hashset.contains(s1))
            if(flag) {
                stringbuilder.append("P:");
                stringbuilder.append(s1);
                stringbuilder.append(',');
                stringbuilder.append(pkgusagestatsextended.mLaunchCount);
                stringbuilder.append(',');
                stringbuilder.append(pkgusagestatsextended.mUsageTime);
                stringbuilder.append('\n');
                if(pkgusagestatsextended.mLaunchTimes.size() > 0) {
                    Iterator iterator1 = pkgusagestatsextended.mLaunchTimes.entrySet().iterator();
                    while(iterator1.hasNext())  {
                        java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator1.next();
                        stringbuilder.append("A:");
                        String s2 = (String)entry1.getKey();
                        TimeStats timestats1;
                        if(s2.startsWith(s1)) {
                            stringbuilder.append('*');
                            stringbuilder.append(s2.substring(s1.length(), s2.length()));
                        } else {
                            stringbuilder.append(s2);
                        }
                        timestats1 = (TimeStats)entry1.getValue();
                        stringbuilder.append(',');
                        stringbuilder.append(timestats1.count);
                        for(int l = 0; l < 10; l++) {
                            stringbuilder.append(",");
                            stringbuilder.append(timestats1.times[l]);
                        }

                        stringbuilder.append('\n');
                    }
                }
            } else {
                stringbuilder.append("  ");
                stringbuilder.append(s1);
                stringbuilder.append(": ");
                stringbuilder.append(pkgusagestatsextended.mLaunchCount);
                stringbuilder.append(" times, ");
                stringbuilder.append(pkgusagestatsextended.mUsageTime);
                stringbuilder.append(" ms");
                stringbuilder.append('\n');
                if(pkgusagestatsextended.mLaunchTimes.size() > 0) {
                    Iterator iterator = pkgusagestatsextended.mLaunchTimes.entrySet().iterator();
                    while(iterator.hasNext())  {
                        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                        stringbuilder.append("    ");
                        stringbuilder.append((String)entry.getKey());
                        TimeStats timestats = (TimeStats)entry.getValue();
                        stringbuilder.append(": ");
                        stringbuilder.append(timestats.count);
                        stringbuilder.append(" starts");
                        int j = 0;
                        for(int k = 0; k < 9; k++) {
                            if(timestats.times[k] != 0) {
                                stringbuilder.append(", ");
                                stringbuilder.append(j);
                                stringbuilder.append('-');
                                stringbuilder.append(LAUNCH_TIME_BINS[k]);
                                stringbuilder.append("ms=");
                                stringbuilder.append(timestats.times[k]);
                            }
                            j = LAUNCH_TIME_BINS[k];
                        }

                        if(timestats.times[9] != 0) {
                            stringbuilder.append(", ");
                            stringbuilder.append(">=");
                            stringbuilder.append(j);
                            stringbuilder.append("ms=");
                            stringbuilder.append(timestats.times[9]);
                        }
                        stringbuilder.append('\n');
                    }
                }
            }
        printwriter.write(stringbuilder.toString());
          goto _L6
    }

    private void filterHistoryStats() {
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        HashMap hashmap = new HashMap(mLastResumeTimes);
        mLastResumeTimes.clear();
        Iterator iterator = mContext.getPackageManager().getInstalledPackages(0).iterator();
        do {
            if(!iterator.hasNext())
                break;
            PackageInfo packageinfo = (PackageInfo)iterator.next();
            if(hashmap.containsKey(packageinfo.packageName))
                mLastResumeTimes.put(packageinfo.packageName, hashmap.get(packageinfo.packageName));
        } while(true);
        break MISSING_BLOCK_LABEL_116;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
    }

    private String getCurrentDateStr(String s) {
        StringBuilder stringbuilder = new StringBuilder();
        synchronized(mCal) {
            mCal.setTimeInMillis(System.currentTimeMillis());
            if(s != null)
                stringbuilder.append(s);
            stringbuilder.append(mCal.get(1));
            int i = 1 + (0 + mCal.get(2));
            if(i < 10)
                stringbuilder.append("0");
            stringbuilder.append(i);
            int j = mCal.get(5);
            if(j < 10)
                stringbuilder.append("0");
            stringbuilder.append(j);
        }
        return stringbuilder.toString();
        exception;
        calendar;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private Parcel getParcelForFile(File file) throws IOException {
        FileInputStream fileinputstream = new FileInputStream(file);
        byte abyte0[] = readFully(fileinputstream);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(abyte0, 0, abyte0.length);
        parcel.setDataPosition(0);
        fileinputstream.close();
        return parcel;
    }

    public static IUsageStats getService() {
        IUsageStats iusagestats;
        if(sService != null) {
            iusagestats = sService;
        } else {
            sService = asInterface(ServiceManager.getService("usagestats"));
            iusagestats = sService;
        }
        return iusagestats;
    }

    private ArrayList getUsageStatsFileListFLOCK() {
        String as[] = mDir.list();
        ArrayList arraylist;
        if(as == null) {
            arraylist = null;
        } else {
            arraylist = new ArrayList();
            int i = as.length;
            int j = 0;
            while(j < i)  {
                String s = as[j];
                if(s.startsWith("usage-"))
                    if(s.endsWith(".bak"))
                        (new File(mDir, s)).delete();
                    else
                        arraylist.add(s);
                j++;
            }
        }
        return arraylist;
    }

    static byte[] readFully(FileInputStream fileinputstream) throws IOException {
        int i = 0;
        byte abyte0[] = new byte[fileinputstream.available()];
        do {
            int k;
            do {
                int j = fileinputstream.read(abyte0, i, abyte0.length - i);
                if(j <= 0)
                    return abyte0;
                i += j;
                k = fileinputstream.available();
            } while(k <= abyte0.length - i);
            byte abyte1[] = new byte[i + k];
            System.arraycopy(abyte0, 0, abyte1, 0, i);
            abyte0 = abyte1;
        } while(true);
    }

    private void readHistoryStatsFLOCK(AtomicFile atomicfile) {
        FileInputStream fileinputstream = null;
        XmlPullParser xmlpullparser;
        fileinputstream = mHistoryFile.openRead();
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
        for(int i = xmlpullparser.getEventType(); i != 2; i = xmlpullparser.next());
        if(!"usage-history".equals(xmlpullparser.getName())) goto _L2; else goto _L1
_L1:
        String s = null;
_L7:
        int j = xmlpullparser.next();
        if(j != 2) goto _L4; else goto _L3
_L3:
        String s1;
        int k;
        s1 = xmlpullparser.getName();
        k = xmlpullparser.getDepth();
        if(!"pkg".equals(s1) || k != 2) goto _L6; else goto _L5
_L5:
        String s4 = xmlpullparser.getAttributeValue(null, "name");
        s = s4;
_L9:
        if(j != 1) goto _L7; else goto _L2
_L2:
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_151;
        fileinputstream.close();
_L12:
        return;
_L6:
        if(!"comp".equals(s1) || k != 3 || s == null) goto _L9; else goto _L8
_L8:
        String s2;
        String s3;
        s2 = xmlpullparser.getAttributeValue(null, "name");
        s3 = xmlpullparser.getAttributeValue(null, "lrt");
        if(s2 == null || s3 == null) goto _L9; else goto _L10
_L10:
        long l = Long.parseLong(s3);
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        Object obj1 = (Map)mLastResumeTimes.get(s);
        if(obj1 == null) {
            obj1 = new HashMap();
            mLastResumeTimes.put(s, obj1);
        }
        ((Map) (obj1)).put(s2, Long.valueOf(l));
        obj;
        JVM INSTR monitorexit ;
          goto _L9
        NumberFormatException numberformatexception;
        numberformatexception;
          goto _L9
_L4:
        if(j != 3) goto _L9; else goto _L11
_L11:
        boolean flag = "pkg".equals(xmlpullparser.getName());
        if(flag)
            s = null;
          goto _L9
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Slog.w("UsageStats", (new StringBuilder()).append("Error reading history stats: ").append(xmlpullparserexception).toString());
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception3) { }
          goto _L12
        IOException ioexception1;
        ioexception1;
        Slog.w("UsageStats", (new StringBuilder()).append("Error reading history stats: ").append(ioexception1).toString());
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L12
        Exception exception;
        exception;
        IOException ioexception4;
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        ioexception4;
          goto _L12
    }

    private void readHistoryStatsFromFile() {
        Object obj = mFileLock;
        obj;
        JVM INSTR monitorenter ;
        if(mHistoryFile.getBaseFile().exists())
            readHistoryStatsFLOCK(mHistoryFile);
        return;
    }

    private void readStatsFLOCK(File file) throws IOException {
        Parcel parcel = getParcelForFile(file);
        if(parcel.readInt() == 1007) goto _L2; else goto _L1
_L1:
        Slog.w("UsageStats", "Usage stats version changed; dropping");
_L4:
        return;
_L2:
        int i = parcel.readInt();
_L6:
        if(i <= 0) goto _L4; else goto _L3
_L3:
        String s;
        i--;
        s = parcel.readString();
        if(s == null) goto _L4; else goto _L5
_L5:
        PkgUsageStatsExtended pkgusagestatsextended = new PkgUsageStatsExtended(parcel);
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        mStats.put(s, pkgusagestatsextended);
          goto _L6
    }

    private void readStatsFromFile() {
        File file = mFile;
        Object obj = mFileLock;
        obj;
        JVM INSTR monitorenter ;
        if(!file.exists()) goto _L2; else goto _L1
_L1:
        readStatsFLOCK(file);
_L4:
        return;
_L2:
        Exception exception;
        try {
            checkFileLimitFLOCK();
            file.createNewFile();
        }
        catch(IOException ioexception) {
            Slog.w("UsageStats", (new StringBuilder()).append("Error : ").append(ioexception).append(" reading data from file:").append(file).toString());
        }
        finally {
            obj;
        }
        if(true) goto _L4; else goto _L3
_L3:
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static boolean scanArgs(String as[], String s) {
        int i;
        int j;
        if(as == null)
            break MISSING_BLOCK_LABEL_37;
        i = as.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_37;
        if(!s.equals(as[j])) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private static String scanArgsData(String as[], String s) {
        String s1 = null;
        if(as == null) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = as.length;
        j = 0;
_L7:
        if(j >= i) goto _L2; else goto _L3
_L3:
        if(!s.equals(as[j])) goto _L5; else goto _L4
_L4:
        int k = j + 1;
        if(k < i)
            s1 = as[k];
_L2:
        return s1;
_L5:
        j++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void writeHistoryStatsFLOCK(AtomicFile atomicfile) {
        FileOutputStream fileoutputstream = null;
        FastXmlSerializer fastxmlserializer;
        fileoutputstream = atomicfile.startWrite();
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "usage-history");
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        Iterator iterator = mLastResumeTimes.entrySet().iterator();
_L2:
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_323;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        fastxmlserializer.startTag(null, "pkg");
        fastxmlserializer.attribute(null, "name", (String)entry.getKey());
        for(Iterator iterator1 = ((Map)entry.getValue()).entrySet().iterator(); iterator1.hasNext(); fastxmlserializer.endTag(null, "comp")) {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator1.next();
            fastxmlserializer.startTag(null, "comp");
            fastxmlserializer.attribute(null, "name", (String)entry1.getKey());
            fastxmlserializer.attribute(null, "lrt", ((Long)entry1.getValue()).toString());
        }

          goto _L1
        Exception exception;
        exception;
        try {
            throw exception;
        }
        catch(IOException ioexception) {
            Slog.w("UsageStats", (new StringBuilder()).append("Error writing history stats").append(ioexception).toString());
        }
        if(fileoutputstream != null)
            atomicfile.failWrite(fileoutputstream);
_L3:
        return;
_L1:
        fastxmlserializer.endTag(null, "pkg");
          goto _L2
        fastxmlserializer.endTag(null, "usage-history");
        fastxmlserializer.endDocument();
        atomicfile.finishWrite(fileoutputstream);
          goto _L3
    }

    private void writeStatsFLOCK(File file) throws IOException {
        FileOutputStream fileoutputstream = new FileOutputStream(file);
        Parcel parcel = Parcel.obtain();
        writeStatsToParcelFLOCK(parcel);
        fileoutputstream.write(parcel.marshall());
        parcel.recycle();
        fileoutputstream.flush();
        FileUtils.sync(fileoutputstream);
        fileoutputstream.close();
        return;
        Exception exception;
        exception;
        FileUtils.sync(fileoutputstream);
        fileoutputstream.close();
        throw exception;
    }

    private void writeStatsToFile(boolean flag, boolean flag1) {
        int i;
        boolean flag2;
        long l;
        synchronized(mCal) {
            mCal.setTimeInMillis(System.currentTimeMillis());
            i = mCal.get(6);
        }
        if(i != mLastWriteDay.get())
            flag2 = true;
        else
            flag2 = false;
        l = SystemClock.elapsedRealtime();
        if(flag) goto _L2; else goto _L1
_L3:
        return;
        exception;
        calendar;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
        if((flag2 || l - mLastWriteElapsedTime.get() >= 0x1b7740L) && mUnforcedDiskWriteRunning.compareAndSet(false, true))
            (new Thread("UsageStatsService_DiskWriter") {

                public void run() {
                    writeStatsToFile(true, false);
                    mUnforcedDiskWriteRunning.set(false);
                    return;
                    Exception exception3;
                    exception3;
                    mUnforcedDiskWriteRunning.set(false);
                    throw exception3;
                }

                final UsageStatsService this$0;

             {
                this$0 = UsageStatsService.this;
                super(s);
            }
            }).start();
          goto _L3
_L2:
        Object obj = mFileLock;
        obj;
        JVM INSTR monitorenter ;
        Exception exception1;
        File file;
        mFileLeaf = getCurrentDateStr("usage-");
        file = null;
        if(mFile == null || !mFile.exists())
            break MISSING_BLOCK_LABEL_244;
        file = new File((new StringBuilder()).append(mFile.getPath()).append(".bak").toString());
        if(file.exists())
            break MISSING_BLOCK_LABEL_236;
        if(mFile.renameTo(file))
            break MISSING_BLOCK_LABEL_244;
        Slog.w("UsageStats", "Failed to persist new stats");
        if(true) goto _L3; else goto _L4
_L4:
        JVM INSTR monitorexit ;
        throw exception1;
        mFile.delete();
        writeStatsFLOCK(mFile);
        mLastWriteElapsedTime.set(l);
        if(!flag2) goto _L6; else goto _L5
_L5:
        mLastWriteDay.set(i);
        synchronized(mStats) {
            mStats.clear();
        }
        mFile = new File(mDir, mFileLeaf);
        checkFileLimitFLOCK();
          goto _L6
_L8:
        writeHistoryStatsFLOCK(mHistoryFile);
_L9:
        if(file != null)
            file.delete();
_L7:
        obj;
        JVM INSTR monitorexit ;
          goto _L3
        exception2;
        map;
        JVM INSTR monitorexit ;
        try {
            throw exception2;
        }
        catch(IOException ioexception) { }
        finally {
            obj;
        }
        Slog.w("UsageStats", (new StringBuilder()).append("Failed writing stats to file:").append(mFile).toString());
        if(file != null) {
            mFile.delete();
            file.renameTo(mFile);
        }
          goto _L7
_L6:
        if(!flag2 && !flag1) goto _L9; else goto _L8
    }

    private void writeStatsToParcelFLOCK(Parcel parcel) {
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        parcel.writeInt(1007);
        Set set = mStats.keySet();
        parcel.writeInt(set.size());
        PkgUsageStatsExtended pkgusagestatsextended;
        for(Iterator iterator = set.iterator(); iterator.hasNext(); pkgusagestatsextended.writeToParcel(parcel)) {
            String s = (String)iterator.next();
            pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
            parcel.writeString(s);
        }

        break MISSING_BLOCK_LABEL_103;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump UsageStats from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L4:
        return;
_L2:
        boolean flag1;
        boolean flag2;
        HashSet hashset;
        boolean flag = scanArgs(as, "--checkin");
        String s;
        if(flag || scanArgs(as, "-c"))
            flag1 = true;
        else
            flag1 = false;
        if(flag || scanArgs(as, "-d"))
            flag2 = true;
        else
            flag2 = false;
        s = scanArgsData(as, "--packages");
        if(!flag2)
            writeStatsToFile(true, false);
        hashset = null;
        if(s != null) {
            if(!"*".equals(s)) {
                String as1[] = s.split(",");
                int i = as1.length;
                for(int j = 0; j < i; j++) {
                    String s1 = as1[j];
                    if(hashset == null)
                        hashset = new HashSet();
                    hashset.add(s1);
                }

            }
        } else
        if(flag) {
            Slog.w("UsageStats", "Checkin without packages");
            continue; /* Loop/switch isn't completed */
        }
        Object obj = mFileLock;
        obj;
        JVM INSTR monitorenter ;
        collectDumpInfoFLOCK(printwriter, flag1, flag2, hashset);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void enforceCallingPermission() {
        if(Binder.getCallingPid() != Process.myPid())
            mContext.enforcePermission("android.permission.UPDATE_DEVICE_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
    }

    public PkgUsageStats[] getAllPkgUsageStats() {
        PkgUsageStats apkgusagestats[];
        apkgusagestats = null;
        mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        int i = mLastResumeTimes.size();
        if(i > 0) {
            apkgusagestats = new PkgUsageStats[i];
            int j = 0;
            for(Iterator iterator = mLastResumeTimes.entrySet().iterator(); iterator.hasNext();) {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                String s = (String)entry.getKey();
                long l = 0L;
                int k = 0;
                PkgUsageStatsExtended pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
                if(pkgusagestatsextended != null) {
                    l = pkgusagestatsextended.mUsageTime;
                    k = pkgusagestatsextended.mLaunchCount;
                }
                apkgusagestats[j] = new PkgUsageStats(s, k, l, (Map)entry.getValue());
                j++;
            }

        }
        return apkgusagestats;
    }

    public PkgUsageStats getPkgUsageStats(ComponentName componentname) {
        PkgUsageStats pkgusagestats;
        pkgusagestats = null;
        mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
        if(componentname == null) goto _L2; else goto _L1
_L1:
        String s = componentname.getPackageName();
        if(s != null) goto _L3; else goto _L2
_L2:
        return pkgusagestats;
_L3:
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        PkgUsageStatsExtended pkgusagestatsextended;
        Map map;
        pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
        map = (Map)mLastResumeTimes.get(s);
        if(pkgusagestatsextended != null || map != null) goto _L4; else goto _L2
_L4:
        break MISSING_BLOCK_LABEL_91;
        Exception exception;
        exception;
        throw exception;
        if(pkgusagestatsextended == null) goto _L6; else goto _L5
_L5:
        int i = pkgusagestatsextended.mLaunchCount;
_L7:
        long l;
        if(pkgusagestatsextended == null)
            break MISSING_BLOCK_LABEL_142;
        l = pkgusagestatsextended.mUsageTime;
_L8:
        pkgusagestats = new PkgUsageStats(s, i, l, map);
        obj;
        JVM INSTR monitorexit ;
          goto _L2
_L6:
        i = 0;
          goto _L7
        l = 0L;
          goto _L8
    }

    public void monitorPackages() {
        mPackageMonitor = new PackageMonitor() {

            public void onPackageRemoved(String s, int i) {
                Object obj = mStatsLock;
                obj;
                JVM INSTR monitorenter ;
                mLastResumeTimes.remove(s);
                return;
            }

            final UsageStatsService this$0;

             {
                this$0 = UsageStatsService.this;
                super();
            }
        };
        mPackageMonitor.register(mContext, null, true);
        filterHistoryStats();
    }

    public void noteLaunchTime(ComponentName componentname, int i) {
        enforceCallingPermission();
        if(componentname == null) goto _L2; else goto _L1
_L1:
        String s = componentname.getPackageName();
        if(s != null) goto _L3; else goto _L2
_L2:
        return;
_L3:
        writeStatsToFile(false, false);
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        PkgUsageStatsExtended pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
        if(pkgusagestatsextended != null)
            pkgusagestatsextended.addLaunchTime(componentname.getClassName(), i);
        if(true) goto _L2; else goto _L4
_L4:
    }

    public void notePauseComponent(ComponentName componentname) {
        enforceCallingPermission();
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
        String s;
label0:
        {
            if(componentname != null) {
                s = componentname.getPackageName();
                if(s != null)
                    break label0;
            }
            break MISSING_BLOCK_LABEL_119;
        }
        if(!mIsResumed)
            break MISSING_BLOCK_LABEL_119;
        break MISSING_BLOCK_LABEL_48;
        Exception exception;
        exception;
        throw exception;
        PkgUsageStatsExtended pkgusagestatsextended;
        mIsResumed = false;
        pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
        if(pkgusagestatsextended != null)
            break MISSING_BLOCK_LABEL_106;
        Slog.i("UsageStats", (new StringBuilder()).append("No package stats for pkg:").append(s).toString());
        obj;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_119;
        pkgusagestatsextended.updatePause();
        obj;
        JVM INSTR monitorexit ;
        writeStatsToFile(false, false);
    }

    public void noteResumeComponent(ComponentName componentname) {
        boolean flag;
        flag = true;
        enforceCallingPermission();
        Object obj = mStatsLock;
        obj;
        JVM INSTR monitorenter ;
label0:
        {
            String s;
label1:
            {
                if(componentname != null) {
                    s = componentname.getPackageName();
                    if(s != null)
                        break label1;
                }
                break label0;
            }
            boolean flag1 = s.equals(mLastResumedPkg);
            if(mIsResumed && mLastResumedPkg != null) {
                PkgUsageStatsExtended pkgusagestatsextended1 = (PkgUsageStatsExtended)mStats.get(mLastResumedPkg);
                if(pkgusagestatsextended1 != null)
                    pkgusagestatsextended1.updatePause();
            }
            boolean flag2;
            PkgUsageStatsExtended pkgusagestatsextended;
            String s1;
            Object obj1;
            if(flag1 && componentname.getClassName().equals(mLastResumedComp))
                flag2 = flag;
            else
                flag2 = false;
            mIsResumed = true;
            mLastResumedPkg = s;
            mLastResumedComp = componentname.getClassName();
            pkgusagestatsextended = (PkgUsageStatsExtended)mStats.get(s);
            if(pkgusagestatsextended == null) {
                pkgusagestatsextended = new PkgUsageStatsExtended();
                mStats.put(s, pkgusagestatsextended);
            }
            s1 = mLastResumedComp;
            if(flag1)
                flag = false;
            pkgusagestatsextended.updateResume(s1, flag);
            if(!flag2)
                pkgusagestatsextended.addLaunchCount(mLastResumedComp);
            obj1 = (Map)mLastResumeTimes.get(s);
            if(obj1 == null) {
                obj1 = new HashMap();
                mLastResumeTimes.put(s, obj1);
            }
            ((Map) (obj1)).put(mLastResumedComp, Long.valueOf(System.currentTimeMillis()));
        }
        return;
    }

    public void publish(Context context) {
        mContext = context;
        ServiceManager.addService("usagestats", asBinder());
    }

    public void shutdown() {
        if(mPackageMonitor != null)
            mPackageMonitor.unregister();
        Slog.i("UsageStats", "Writing usage stats before shutdown...");
        writeStatsToFile(true, true);
    }

    private static final int CHECKIN_VERSION = 4;
    private static final String FILE_HISTORY = "usage-history.xml";
    private static final String FILE_PREFIX = "usage-";
    private static final int FILE_WRITE_INTERVAL = 0x1b7740;
    private static final int LAUNCH_TIME_BINS[];
    private static final int MAX_NUM_FILES = 5;
    private static final int NUM_LAUNCH_TIME_BINS = 10;
    private static final boolean REPORT_UNEXPECTED = false;
    public static final String SERVICE_NAME = "usagestats";
    private static final String TAG = "UsageStats";
    private static final int VERSION = 1007;
    private static final boolean localLOGV;
    static IUsageStats sService;
    private Calendar mCal;
    private Context mContext;
    private File mDir;
    private File mFile;
    private String mFileLeaf;
    final Object mFileLock = new Object();
    private AtomicFile mHistoryFile;
    private boolean mIsResumed;
    private final Map mLastResumeTimes = new HashMap();
    private String mLastResumedComp;
    private String mLastResumedPkg;
    private final AtomicInteger mLastWriteDay = new AtomicInteger(-1);
    private final AtomicLong mLastWriteElapsedTime = new AtomicLong(0L);
    private PackageMonitor mPackageMonitor;
    private final Map mStats = new HashMap();
    final Object mStatsLock = new Object();
    private final AtomicBoolean mUnforcedDiskWriteRunning = new AtomicBoolean(false);

    static  {
        int ai[] = new int[9];
        ai[0] = 250;
        ai[1] = 500;
        ai[2] = 750;
        ai[3] = 1000;
        ai[4] = 1500;
        ai[5] = 2000;
        ai[6] = 3000;
        ai[7] = 4000;
        ai[8] = 5000;
        LAUNCH_TIME_BINS = ai;
    }




}
