// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.os.*;
import java.io.*;

public class DiskStatsService extends Binder {

    public DiskStatsService(Context context) {
        mContext = context;
    }

    private void reportFreeSpace(File file, String s, PrintWriter printwriter) {
        IllegalArgumentException illegalargumentexception;
        long l;
        long l1;
        long l2;
        StatFs statfs = new StatFs(file.getPath());
        l = statfs.getBlockSize();
        l1 = statfs.getAvailableBlocks();
        l2 = statfs.getBlockCount();
        if(l <= 0L || l2 <= 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid stat: bsize=").append(l).append(" avail=").append(l1).append(" total=").append(l2).toString());
          goto _L1
_L3:
        return;
_L1:
        try {
            printwriter.print(s);
            printwriter.print("-Free: ");
            printwriter.print((l1 * l) / 1024L);
            printwriter.print("K / ");
            printwriter.print((l2 * l) / 1024L);
            printwriter.print("K total = ");
            printwriter.print((100L * l1) / l2);
            printwriter.println("% free");
        }
        // Misplaced declaration of an exception variable
        catch(IllegalArgumentException illegalargumentexception) {
            printwriter.print(s);
            printwriter.print("-Error: ");
            printwriter.println(illegalargumentexception.toString());
        }
        if(true) goto _L3; else goto _L2
_L2:
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        byte abyte0[];
        File file;
        FileOutputStream fileoutputstream;
        IOException ioexception;
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "DiskStatsService");
        abyte0 = new byte[512];
        for(int i = 0; i < abyte0.length; i++)
            abyte0[i] = (byte)i;

        file = new File(Environment.getDataDirectory(), "system/perftest.tmp");
        fileoutputstream = null;
        ioexception = null;
        l = SystemClock.uptimeMillis();
        FileOutputStream fileoutputstream1 = new FileOutputStream(file);
        fileoutputstream1.write(abyte0);
        IOException ioexception1;
        long l1;
        Exception exception;
        if(fileoutputstream1 != null)
            try {
                fileoutputstream1.close();
            }
            catch(IOException ioexception4) { }
        l1 = SystemClock.uptimeMillis();
        if(file.exists())
            file.delete();
        if(ioexception != null) {
            printwriter.print("Test-Error: ");
            printwriter.println(ioexception.toString());
        } else {
            printwriter.print("Latency: ");
            printwriter.print(l1 - l);
            printwriter.println("ms [512B Data Write]");
        }
        reportFreeSpace(Environment.getDataDirectory(), "Data", printwriter);
        reportFreeSpace(Environment.getDownloadCacheDirectory(), "Cache", printwriter);
        reportFreeSpace(new File("/system"), "System", printwriter);
        return;
        ioexception1;
_L4:
        ioexception = ioexception1;
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception2) { }
        break MISSING_BLOCK_LABEL_96;
        exception;
_L2:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception3) { }
        throw exception;
        exception;
        fileoutputstream = fileoutputstream1;
        if(true) goto _L2; else goto _L1
_L1:
        ioexception1;
        fileoutputstream = fileoutputstream1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final String TAG = "DiskStatsService";
    private final Context mContext;
}
