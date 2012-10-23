// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.*;
import android.text.format.Time;
import android.util.Slog;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public final class DropBoxManagerService extends com.android.internal.os.IDropBoxManagerService.Stub {
    private static final class EntryFile
        implements Comparable {

        public final int compareTo(EntryFile entryfile) {
            int i = -1;
            if(timestampMillis >= entryfile.timestampMillis) goto _L2; else goto _L1
_L1:
            return i;
_L2:
            if(timestampMillis > entryfile.timestampMillis)
                i = 1;
            else
            if(file != null && entryfile.file != null)
                i = file.compareTo(entryfile.file);
            else
            if(entryfile.file == null)
                if(file != null)
                    i = 1;
                else
                if(this == entryfile)
                    i = 0;
                else
                if(hashCode() >= entryfile.hashCode())
                    if(hashCode() > entryfile.hashCode())
                        i = 1;
                    else
                        i = 0;
            if(true) goto _L1; else goto _L3
_L3:
        }

        public volatile int compareTo(Object obj) {
            return compareTo((EntryFile)obj);
        }

        public final int blocks;
        public final File file;
        public final int flags;
        public final String tag;
        public final long timestampMillis;

        public EntryFile(long l) {
            tag = null;
            timestampMillis = l;
            flags = 1;
            file = null;
            blocks = 0;
        }

        public EntryFile(File file1, int i) {
            String s;
            int j;
            file = file1;
            blocks = (int)(((file.length() + (long)i) - 1L) / (long)i);
            s = file1.getName();
            j = s.lastIndexOf('@');
            if(j >= 0) goto _L2; else goto _L1
_L1:
            tag = null;
            timestampMillis = 0L;
            flags = 1;
_L3:
            return;
_L2:
            long l;
            int k = 0;
            tag = Uri.decode(s.substring(0, j));
            if(s.endsWith(".gz")) {
                k = 0 | 4;
                s = s.substring(0, -3 + s.length());
            }
            String s1;
            long l1;
            if(s.endsWith(".lost")) {
                k |= 1;
                s1 = s.substring(j + 1, -5 + s.length());
            } else
            if(s.endsWith(".txt")) {
                k |= 2;
                s1 = s.substring(j + 1, -4 + s.length());
            } else {
label0:
                {
                    if(!s.endsWith(".dat"))
                        break label0;
                    s1 = s.substring(j + 1, -4 + s.length());
                }
            }
            flags = k;
            l1 = Long.valueOf(s1).longValue();
            l = l1;
_L4:
            timestampMillis = l;
              goto _L3
            flags = 1;
            timestampMillis = 0L;
              goto _L3
            NumberFormatException numberformatexception;
            numberformatexception;
            l = 0L;
              goto _L4
        }

        public EntryFile(File file1, File file2, String s, long l, int i, int j) throws IOException {
            if((i & 1) != 0)
                throw new IllegalArgumentException();
            tag = s;
            timestampMillis = l;
            flags = i;
            StringBuilder stringbuilder = (new StringBuilder()).append(Uri.encode(s)).append("@").append(l);
            String s1;
            StringBuilder stringbuilder1;
            String s2;
            if((i & 2) != 0)
                s1 = ".txt";
            else
                s1 = ".dat";
            stringbuilder1 = stringbuilder.append(s1);
            if((i & 4) != 0)
                s2 = ".gz";
            else
                s2 = "";
            file = new File(file2, stringbuilder1.append(s2).toString());
            if(!file1.renameTo(file)) {
                throw new IOException((new StringBuilder()).append("Can't rename ").append(file1).append(" to ").append(file).toString());
            } else {
                blocks = (int)(((file.length() + (long)j) - 1L) / (long)j);
                return;
            }
        }

        public EntryFile(File file1, String s, long l) throws IOException {
            tag = s;
            timestampMillis = l;
            flags = 1;
            file = new File(file1, (new StringBuilder()).append(Uri.encode(s)).append("@").append(l).append(".lost").toString());
            blocks = 0;
            (new FileOutputStream(file)).close();
        }
    }

    private static final class FileList
        implements Comparable {

        public final int compareTo(FileList filelist) {
            int i = 0;
            if(blocks == filelist.blocks) goto _L2; else goto _L1
_L1:
            i = filelist.blocks - blocks;
_L4:
            return i;
_L2:
            if(this != filelist)
                if(hashCode() < filelist.hashCode())
                    i = -1;
                else
                if(hashCode() > filelist.hashCode())
                    i = 1;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public volatile int compareTo(Object obj) {
            return compareTo((FileList)obj);
        }

        public int blocks;
        public final TreeSet contents;

        private FileList() {
            blocks = 0;
            contents = new TreeSet();
        }

    }


    public DropBoxManagerService(Context context, File file) {
        mAllFiles = null;
        mFilesByTag = null;
        mStatFs = null;
        mBlockSize = 0;
        mCachedQuotaBlocks = 0;
        mCachedQuotaUptimeMillis = 0L;
        mBooted = false;
        mDropBoxDir = file;
        mContext = context;
        mContentResolver = context.getContentResolver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
        intentfilter.addAction("android.intent.action.BOOT_COMPLETED");
        context.registerReceiver(mReceiver, intentfilter);
        mContentResolver.registerContentObserver(android.provider.Settings.Secure.CONTENT_URI, true, new ContentObserver(context) {

            public void onChange(boolean flag) {
                mReceiver.onReceive(context, (Intent)null);
            }

            final DropBoxManagerService this$0;
            final Context val$context;

             {
                this$0 = DropBoxManagerService.this;
                context = context1;
                super(final_handler);
            }
        });
    }

    /**
     * @deprecated Method createEntry is deprecated
     */

    private long createEntry(File file, String s, int i) throws IOException {
        this;
        JVM INSTR monitorenter ;
        long l;
        EntryFile aentryfile1[];
        int j;
        l = System.currentTimeMillis();
        SortedSet sortedset = mAllFiles.contents.tailSet(new EntryFile(10000L + l));
        EntryFile aentryfile[] = null;
        if(!sortedset.isEmpty()) {
            aentryfile = (EntryFile[])sortedset.toArray(new EntryFile[sortedset.size()]);
            sortedset.clear();
        }
        if(!mAllFiles.contents.isEmpty())
            l = Math.max(l, 1L + ((EntryFile)mAllFiles.contents.last()).timestampMillis);
        if(aentryfile == null)
            break MISSING_BLOCK_LABEL_330;
        aentryfile1 = aentryfile;
        j = aentryfile1.length;
        Exception exception;
        for(int k = 0; k < j; k++) {
            EntryFile entryfile = aentryfile1[k];
            FileList filelist = mAllFiles;
            filelist.blocks = filelist.blocks - entryfile.blocks;
            FileList filelist1 = (FileList)mFilesByTag.get(entryfile.tag);
            if(filelist1 != null && filelist1.contents.remove(entryfile))
                filelist1.blocks = filelist1.blocks - entryfile.blocks;
            if((1 & entryfile.flags) == 0) {
                File file2 = entryfile.file;
                File file3 = mDropBoxDir;
                String s2 = entryfile.tag;
                long l2 = l + 1L;
                enrollEntry(new EntryFile(file2, file3, s2, l, entryfile.flags, mBlockSize));
                l = l2;
            } else {
                File file1 = mDropBoxDir;
                String s1 = entryfile.tag;
                long l1 = l + 1L;
                enrollEntry(new EntryFile(file1, s1, l));
                l = l1;
            }
            break MISSING_BLOCK_LABEL_391;
        }

        if(file != null) goto _L2; else goto _L1
_L1:
        enrollEntry(new EntryFile(mDropBoxDir, s, l));
_L4:
        this;
        JVM INSTR monitorexit ;
        return l;
_L2:
        enrollEntry(new EntryFile(file, mDropBoxDir, s, l, i, mBlockSize));
        if(true) goto _L4; else goto _L3
_L3:
        exception;
        throw exception;
    }

    /**
     * @deprecated Method enrollEntry is deprecated
     */

    private void enrollEntry(EntryFile entryfile) {
        this;
        JVM INSTR monitorenter ;
        mAllFiles.contents.add(entryfile);
        FileList filelist = mAllFiles;
        filelist.blocks = filelist.blocks + entryfile.blocks;
        if(entryfile.tag != null && entryfile.file != null && entryfile.blocks > 0) {
            FileList filelist1 = (FileList)mFilesByTag.get(entryfile.tag);
            if(filelist1 == null) {
                filelist1 = new FileList();
                mFilesByTag.put(entryfile.tag, filelist1);
            }
            filelist1.contents.add(entryfile);
            filelist1.blocks = filelist1.blocks + entryfile.blocks;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method init is deprecated
     */

    private void init() throws IOException {
        this;
        JVM INSTR monitorenter ;
        if(mStatFs != null)
            break MISSING_BLOCK_LABEL_94;
        if(!mDropBoxDir.isDirectory() && !mDropBoxDir.mkdirs())
            throw new IOException((new StringBuilder()).append("Can't mkdir: ").append(mDropBoxDir).toString());
        break MISSING_BLOCK_LABEL_65;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        File afile[];
        try {
            mStatFs = new StatFs(mDropBoxDir.getPath());
            mBlockSize = mStatFs.getBlockSize();
        }
        catch(IllegalArgumentException illegalargumentexception) {
            throw new IOException((new StringBuilder()).append("Can't statfs: ").append(mDropBoxDir).toString());
        }
        int i;
        if(mAllFiles != null)
            break MISSING_BLOCK_LABEL_376;
        afile = mDropBoxDir.listFiles();
        if(afile == null)
            throw new IOException((new StringBuilder()).append("Can't list files: ").append(mDropBoxDir).toString());
        mAllFiles = new FileList();
        mFilesByTag = new HashMap();
        i = afile.length;
        for(int j = 0; j < i; j++) {
            File file = afile[j];
            if(file.getName().endsWith(".tmp")) {
                Slog.i("DropBoxManagerService", (new StringBuilder()).append("Cleaning temp file: ").append(file).toString());
                file.delete();
            } else {
                EntryFile entryfile = new EntryFile(file, mBlockSize);
                if(entryfile.tag == null)
                    Slog.w("DropBoxManagerService", (new StringBuilder()).append("Unrecognized file: ").append(file).toString());
                else
                if(entryfile.timestampMillis == 0L) {
                    Slog.w("DropBoxManagerService", (new StringBuilder()).append("Invalid filename: ").append(file).toString());
                    file.delete();
                } else {
                    enrollEntry(entryfile);
                }
            }
            break MISSING_BLOCK_LABEL_379;
        }

        this;
        JVM INSTR monitorexit ;
    }

    /**
     * @deprecated Method trimToFit is deprecated
     */

    private long trimToFit() {
        this;
        JVM INSTR monitorenter ;
        int j;
        long l;
        int i = android.provider.Settings.Secure.getInt(mContentResolver, "dropbox_age_seconds", 0x3f480);
        j = android.provider.Settings.Secure.getInt(mContentResolver, "dropbox_max_files", 1000);
        l = System.currentTimeMillis() - (long)(i * 1000);
_L11:
        if(mAllFiles.contents.isEmpty()) goto _L2; else goto _L1
_L1:
        EntryFile entryfile1 = (EntryFile)mAllFiles.contents.first();
        if(entryfile1.timestampMillis <= l || mAllFiles.contents.size() >= j) goto _L3; else goto _L2
_L2:
        long l1 = SystemClock.uptimeMillis();
        if(l1 > 5000L + mCachedQuotaUptimeMillis) {
            int j2 = android.provider.Settings.Secure.getInt(mContentResolver, "dropbox_quota_percent", 10);
            int k2 = android.provider.Settings.Secure.getInt(mContentResolver, "dropbox_reserve_percent", 10);
            int i3 = android.provider.Settings.Secure.getInt(mContentResolver, "dropbox_quota_kb", 5120);
            mStatFs.restat(mDropBoxDir.getPath());
            int j3 = mStatFs.getAvailableBlocks() - (k2 * mStatFs.getBlockCount()) / 100;
            mCachedQuotaBlocks = Math.min((i3 * 1024) / mBlockSize, Math.max(0, (j3 * j2) / 100));
            mCachedQuotaUptimeMillis = l1;
        }
        if(mAllFiles.blocks <= mCachedQuotaBlocks) goto _L5; else goto _L4
_L4:
        int j1;
        int k1;
        TreeSet treeset;
        Iterator iterator;
        j1 = mAllFiles.blocks;
        k1 = 0;
        treeset = new TreeSet(mFilesByTag.values());
        iterator = treeset.iterator();
_L12:
        if(!iterator.hasNext()) goto _L7; else goto _L6
_L6:
        FileList filelist2 = (FileList)iterator.next();
        if(k1 <= 0 || filelist2.blocks > (mCachedQuotaBlocks - j1) / k1) goto _L8; else goto _L7
_L7:
        int i2;
        Iterator iterator1;
        i2 = (mCachedQuotaBlocks - j1) / k1;
        iterator1 = treeset.iterator();
_L14:
        if(!iterator1.hasNext()) goto _L5; else goto _L9
_L9:
        FileList filelist = (FileList)iterator1.next();
        if(mAllFiles.blocks >= mCachedQuotaBlocks) goto _L10; else goto _L5
_L5:
        int k;
        int i1;
        k = mCachedQuotaBlocks;
        i1 = mBlockSize;
        long l2 = k * i1;
        this;
        JVM INSTR monitorexit ;
        return l2;
_L3:
        FileList filelist3 = (FileList)mFilesByTag.get(entryfile1.tag);
        if(filelist3 != null && filelist3.contents.remove(entryfile1))
            filelist3.blocks = filelist3.blocks - entryfile1.blocks;
        if(mAllFiles.contents.remove(entryfile1)) {
            FileList filelist4 = mAllFiles;
            filelist4.blocks = filelist4.blocks - entryfile1.blocks;
        }
        if(entryfile1.file != null)
            entryfile1.file.delete();
          goto _L11
        Exception exception;
        exception;
        throw exception;
_L8:
        j1 -= filelist2.blocks;
        k1++;
          goto _L12
_L10:
        if(filelist.blocks <= i2 || filelist.contents.isEmpty()) goto _L14; else goto _L13
_L13:
        EntryFile entryfile;
        entryfile = (EntryFile)filelist.contents.first();
        if(filelist.contents.remove(entryfile))
            filelist.blocks = filelist.blocks - entryfile.blocks;
        if(mAllFiles.contents.remove(entryfile)) {
            FileList filelist1 = mAllFiles;
            filelist1.blocks = filelist1.blocks - entryfile.blocks;
        }
        if(entryfile.file != null)
            entryfile.file.delete();
        enrollEntry(new EntryFile(mDropBoxDir, entryfile.tag, entryfile.timestampMillis));
          goto _L10
        IOException ioexception;
        ioexception;
        Slog.e("DropBoxManagerService", "Can't write tombstone file", ioexception);
        if(true) goto _L10; else goto _L15
_L15:
          goto _L14
    }

    public void add(android.os.DropBoxManager.Entry entry) {
        File file;
        Object obj;
        String s;
        file = null;
        obj = null;
        s = entry.getTag();
        int i;
        i = entry.getFlags();
        if((i & 1) != 0)
            throw new IllegalArgumentException();
          goto _L1
        IOException ioexception1;
        ioexception1;
_L16:
        Slog.e("DropBoxManagerService", (new StringBuilder()).append("Can't write: ").append(s).toString(), ioexception1);
        Exception exception;
        File file1;
        int i1;
        BufferedOutputStream bufferedoutputstream;
        IOException ioexception;
        boolean flag;
        long l;
        long l1;
        byte abyte0[];
        InputStream inputstream;
        int j;
        int k;
        FileOutputStream fileoutputstream;
        int j1;
        long l2;
        long l3;
        Intent intent;
        int k1;
        int i2;
        if(obj != null)
            try {
                ((OutputStream) (obj)).close();
            }
            catch(IOException ioexception2) { }
        entry.close();
        if(file == null) goto _L3; else goto _L2
_L2:
        file.delete();
_L3:
        return;
_L1:
        init();
        flag = isTagEnabled(s);
        if(flag)
            break MISSING_BLOCK_LABEL_118;
        if(false)
            try {
                throw null;
            }
            catch(IOException ioexception4) { }
        entry.close();
        if(true) goto _L3; else goto _L4
_L4:
        break; /* Loop/switch isn't completed */
        l = trimToFit();
        l1 = System.currentTimeMillis();
        abyte0 = new byte[mBlockSize];
        inputstream = entry.getInputStream();
        j = 0;
_L14:
        k = abyte0.length;
        if(j >= k) goto _L6; else goto _L5
_L5:
        k1 = abyte0.length - j;
        i2 = inputstream.read(abyte0, j, k1);
        if(i2 > 0) goto _L7; else goto _L6
_L6:
        file1 = new File(mDropBoxDir, (new StringBuilder()).append("drop").append(Thread.currentThread().getId()).append(".tmp").toString());
        i1 = mBlockSize;
        if(i1 > 4096)
            i1 = 4096;
        break MISSING_BLOCK_LABEL_666;
_L22:
        fileoutputstream = new FileOutputStream(file1);
        bufferedoutputstream = new BufferedOutputStream(fileoutputstream, i1);
        j1 = abyte0.length;
        if(j != j1 || (i & 4) != 0) goto _L9; else goto _L8
_L8:
        obj = new GZIPOutputStream(bufferedoutputstream);
        i |= 4;
_L18:
        ((OutputStream) (obj)).write(abyte0, 0, j);
        l2 = System.currentTimeMillis();
        if(l2 - l1 > 30000L) {
            l = trimToFit();
            l1 = l2;
        }
        j = inputstream.read(abyte0);
        if(j > 0) goto _L11; else goto _L10
_L10:
        FileUtils.sync(fileoutputstream);
        ((OutputStream) (obj)).close();
        obj = null;
_L15:
        if(file1.length() <= l) goto _L13; else goto _L12
_L12:
        Slog.w("DropBoxManagerService", (new StringBuilder()).append("Dropping: ").append(s).append(" (").append(file1.length()).append(" > ").append(l).append(" bytes)").toString());
        file1.delete();
        file = null;
_L19:
        l3 = createEntry(file, s, i);
        file = null;
        intent = new Intent("android.intent.action.DROPBOX_ENTRY_ADDED");
        intent.putExtra("tag", s);
        intent.putExtra("time", l3);
        if(!mBooted)
            intent.addFlags(0x40000000);
        mHandler.sendMessage(mHandler.obtainMessage(1, intent));
        if(obj != null)
            try {
                ((OutputStream) (obj)).close();
            }
            catch(IOException ioexception3) { }
        entry.close();
        if(true) goto _L3; else goto _L2
_L7:
        j += i2;
          goto _L14
_L11:
        ((OutputStream) (obj)).flush();
          goto _L15
        ioexception1;
        file = file1;
          goto _L16
_L13:
        if(j > 0) goto _L18; else goto _L17
_L17:
        file = file1;
          goto _L19
        exception;
_L21:
        if(obj != null)
            try {
                ((OutputStream) (obj)).close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) { }
        entry.close();
        if(file != null)
            file.delete();
        throw exception;
        exception;
        file = file1;
        continue; /* Loop/switch isn't completed */
        exception;
        obj = bufferedoutputstream;
        file = file1;
        if(true) goto _L21; else goto _L20
_L20:
        ioexception1;
        obj = bufferedoutputstream;
        file = file1;
          goto _L16
_L9:
        obj = bufferedoutputstream;
          goto _L18
        if(i1 < 512)
            i1 = 512;
          goto _L22
    }

    /**
     * @deprecated Method dump is deprecated
     */

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        this;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println("Permission Denial: Can't dump DropBoxManagerService");
_L9:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        init();
        StringBuilder stringbuilder;
        boolean flag;
        boolean flag1;
        ArrayList arraylist;
        int i;
        stringbuilder = new StringBuilder();
        flag = false;
        flag1 = false;
        arraylist = new ArrayList();
        i = 0;
_L44:
        if(as == null) goto _L4; else goto _L3
_L3:
        int k1 = as.length;
        if(i >= k1) goto _L4; else goto _L5
_L5:
        if(!as[i].equals("-p") && !as[i].equals("--print")) goto _L7; else goto _L6
        IOException ioexception;
        ioexception;
        Exception exception;
        printwriter.println((new StringBuilder()).append("Can't initialize: ").append(ioexception).toString());
        Slog.e("DropBoxManagerService", "Can't init", ioexception);
        if(true) goto _L9; else goto _L8
_L8:
        JVM INSTR monitorexit ;
        throw exception;
_L7:
        if(!as[i].equals("-f") && !as[i].equals("--file")) goto _L11; else goto _L10
_L11:
        if(as[i].startsWith("-"))
            stringbuilder.append("Unknown argument: ").append(as[i]).append("\n");
        else
            arraylist.add(as[i]);
          goto _L12
_L4:
        int j;
        int k;
        Time time;
        Iterator iterator;
        stringbuilder.append("Drop box contents: ").append(mAllFiles.contents.size()).append(" entries\n");
        if(!arraylist.isEmpty()) {
            stringbuilder.append("Searching for:");
            String s5;
            for(Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); stringbuilder.append(" ").append(s5))
                s5 = (String)iterator1.next();

            stringbuilder.append("\n");
        }
        j = 0;
        k = arraylist.size();
        time = new Time();
        stringbuilder.append("\n");
        iterator = mAllFiles.contents.iterator();
_L18:
        if(!iterator.hasNext()) goto _L14; else goto _L13
_L13:
        EntryFile entryfile;
        String s;
        boolean flag2;
        int l;
        entryfile = (EntryFile)iterator.next();
        time.set(entryfile.timestampMillis);
        s = time.format("%Y-%m-%d %H:%M:%S");
        flag2 = true;
        l = 0;
_L45:
        if(l >= k || !flag2) goto _L16; else goto _L15
_L15:
        String s4 = (String)arraylist.get(l);
        StringBuilder stringbuilder1;
        String s1;
        String s2;
        int i1;
        InputStreamReader inputstreamreader;
        android.os.DropBoxManager.Entry entry;
        Exception exception1;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        String s3;
        boolean flag3;
        InputStreamReader inputstreamreader1;
        char ac[];
        boolean flag4;
        int j1;
        if(s.contains(s4) || s4.equals(entryfile.tag))
            flag2 = true;
        else
            flag2 = false;
        break MISSING_BLOCK_LABEL_1233;
_L16:
        if(!flag2) goto _L18; else goto _L17
_L17:
        j++;
        if(flag)
            stringbuilder.append("========================================\n");
        stringbuilder1 = stringbuilder.append(s).append(" ");
        if(entryfile.tag == null)
            s1 = "(no tag)";
        else
            s1 = entryfile.tag;
        stringbuilder1.append(s1);
        if(entryfile.file != null) goto _L20; else goto _L19
_L19:
        stringbuilder.append(" (no file)\n");
          goto _L18
_L20:
        if((1 & entryfile.flags) == 0) goto _L22; else goto _L21
_L21:
        stringbuilder.append(" (contents lost)\n");
          goto _L18
_L22:
        stringbuilder.append(" (");
        if((4 & entryfile.flags) != 0)
            stringbuilder.append("compressed ");
        if((2 & entryfile.flags) == 0) goto _L24; else goto _L23
_L23:
        s2 = "text";
_L32:
        stringbuilder.append(s2);
        stringbuilder.append(", ").append(entryfile.file.length()).append(" bytes)\n");
        if(flag1 || flag && (2 & entryfile.flags) == 0) {
            if(!flag)
                stringbuilder.append("    ");
            stringbuilder.append(entryfile.file.getPath()).append("\n");
        }
        i1 = entryfile.flags;
        if((i1 & 2) == 0 || !flag && flag1) goto _L26; else goto _L25
_L25:
        inputstreamreader = null;
        entry = new android.os.DropBoxManager.Entry(entryfile.tag, entryfile.timestampMillis, entryfile.file, entryfile.flags);
        if(!flag) goto _L28; else goto _L27
_L27:
        inputstreamreader1 = new InputStreamReader(entry.getInputStream());
        ac = new char[4096];
        flag4 = false;
_L35:
        j1 = inputstreamreader1.read(ac);
        if(j1 > 0) goto _L30; else goto _L29
_L29:
        if(!flag4)
            stringbuilder.append("\n");
        inputstreamreader = inputstreamreader1;
_L40:
        if(entry == null)
            break MISSING_BLOCK_LABEL_834;
        entry.close();
        if(inputstreamreader != null)
            try {
                inputstreamreader.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception3) { }
            finally {
                this;
            }
_L26:
        if(!flag) goto _L18; else goto _L31
_L31:
        stringbuilder.append("\n");
          goto _L18
_L24:
        s2 = "data";
          goto _L32
_L30:
        stringbuilder.append(ac, 0, j1);
        if(ac[j1 - 1] != '\n') goto _L34; else goto _L33
_L33:
        flag4 = true;
_L39:
        if(stringbuilder.length() > 0x10000) {
            printwriter.write(stringbuilder.toString());
            stringbuilder.setLength(0);
        }
          goto _L35
        ioexception2;
        inputstreamreader = inputstreamreader1;
_L41:
        stringbuilder.append("*** ").append(ioexception2.toString()).append("\n");
        Slog.e("DropBoxManagerService", (new StringBuilder()).append("Can't read: ").append(entryfile.file).toString(), ioexception2);
        if(entry == null) goto _L37; else goto _L36
_L36:
        entry.close();
_L37:
        if(inputstreamreader == null) goto _L26; else goto _L38
_L38:
        inputstreamreader.close();
          goto _L26
_L34:
        flag4 = false;
          goto _L39
_L28:
        s3 = entry.getText(70);
        if(s3.length() != 70)
            break MISSING_BLOCK_LABEL_1096;
        flag3 = true;
_L42:
        stringbuilder.append("    ").append(s3.trim().replace('\n', '/'));
        if(flag3)
            stringbuilder.append(" ...");
        stringbuilder.append("\n");
          goto _L40
        ioexception2;
          goto _L41
        flag3 = false;
          goto _L42
        exception1;
        entry = null;
_L43:
        if(entry == null)
            break MISSING_BLOCK_LABEL_1117;
        entry.close();
        if(inputstreamreader == null)
            break MISSING_BLOCK_LABEL_1127;
        try {
            inputstreamreader.close();
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception1) { }
        throw exception1;
_L14:
        if(j == 0)
            stringbuilder.append("(No entries found.)\n");
        if(as == null || as.length == 0) {
            if(!flag)
                stringbuilder.append("\n");
            stringbuilder.append("Usage: dumpsys dropbox [--print|--file] [YYYY-mm-dd] [HH:MM:SS] [tag]\n");
        }
        printwriter.write(stringbuilder.toString());
          goto _L9
        exception1;
          goto _L43
        exception1;
        inputstreamreader = inputstreamreader1;
          goto _L43
        ioexception2;
        entry = null;
          goto _L41
_L6:
        flag = true;
_L12:
        i++;
          goto _L44
_L10:
        flag1 = true;
          goto _L12
        l++;
          goto _L45
    }

    /**
     * @deprecated Method getNextEntry is deprecated
     */

    public android.os.DropBoxManager.Entry getNextEntry(String s, long l) {
        this;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.READ_LOGS") != 0)
            throw new SecurityException("READ_LOGS permission required");
        break MISSING_BLOCK_LABEL_33;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        init();
        if(s != null) goto _L2; else goto _L1
_L1:
        FileList filelist = mAllFiles;
_L6:
        if(filelist != null) goto _L4; else goto _L3
_L3:
        android.os.DropBoxManager.Entry entry = null;
_L5:
        this;
        JVM INSTR monitorexit ;
        return entry;
        IOException ioexception;
        ioexception;
        Slog.e("DropBoxManagerService", "Can't init", ioexception);
        entry = null;
          goto _L5
_L2:
        filelist = (FileList)mFilesByTag.get(s);
          goto _L6
_L4:
        Iterator iterator = filelist.contents.tailSet(new EntryFile(1L + l)).iterator();
_L9:
        EntryFile entryfile;
        do {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_251;
            entryfile = (EntryFile)iterator.next();
        } while(entryfile.tag == null);
        if((1 & entryfile.flags) == 0) goto _L8; else goto _L7
_L7:
        entry = new android.os.DropBoxManager.Entry(entryfile.tag, entryfile.timestampMillis);
          goto _L5
_L8:
        entry = new android.os.DropBoxManager.Entry(entryfile.tag, entryfile.timestampMillis, entryfile.file, entryfile.flags);
          goto _L5
        IOException ioexception1;
        ioexception1;
        Slog.e("DropBoxManagerService", (new StringBuilder()).append("Can't read: ").append(entryfile.file).toString(), ioexception1);
          goto _L9
        entry = null;
          goto _L5
    }

    public boolean isTagEnabled(String s) {
        boolean flag;
        if(!"disabled".equals(android.provider.Settings.Secure.getString(mContentResolver, (new StringBuilder()).append("dropbox:").append(s).toString())))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void stop() {
        mContext.unregisterReceiver(mReceiver);
    }

    private static final int DEFAULT_AGE_SECONDS = 0x3f480;
    private static final int DEFAULT_MAX_FILES = 1000;
    private static final int DEFAULT_QUOTA_KB = 5120;
    private static final int DEFAULT_QUOTA_PERCENT = 10;
    private static final int DEFAULT_RESERVE_PERCENT = 10;
    private static final int MSG_SEND_BROADCAST = 1;
    private static final boolean PROFILE_DUMP = false;
    private static final int QUOTA_RESCAN_MILLIS = 5000;
    private static final String TAG = "DropBoxManagerService";
    private FileList mAllFiles;
    private int mBlockSize;
    private volatile boolean mBooted;
    private int mCachedQuotaBlocks;
    private long mCachedQuotaUptimeMillis;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final File mDropBoxDir;
    private HashMap mFilesByTag;
    private final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            if(message.what == 1)
                mContext.sendBroadcast((Intent)message.obj, "android.permission.READ_LOGS");
        }

        final DropBoxManagerService this$0;

             {
                this$0 = DropBoxManagerService.this;
                super();
            }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            if(intent != null && "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                mBooted = true;
            } else {
                mCachedQuotaUptimeMillis = 0L;
                (new Thread() {

                    public void run() {
                        init();
                        trimToFit();
_L1:
                        return;
                        IOException ioexception;
                        ioexception;
                        Slog.e("DropBoxManagerService", "Can't init", ioexception);
                          goto _L1
                    }

                    final _cls1 this$1;

                     {
                        this$1 = _cls1.this;
                        super();
                    }
                }).start();
            }
        }

        final DropBoxManagerService this$0;

             {
                this$0 = DropBoxManagerService.this;
                super();
            }
    };
    private StatFs mStatFs;


/*
    static boolean access$002(DropBoxManagerService dropboxmanagerservice, boolean flag) {
        dropboxmanagerservice.mBooted = flag;
        return flag;
    }

*/


/*
    static long access$102(DropBoxManagerService dropboxmanagerservice, long l) {
        dropboxmanagerservice.mCachedQuotaUptimeMillis = l;
        return l;
    }

*/




}
