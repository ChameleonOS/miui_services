// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.graphics.Point;
import android.util.Slog;
import com.android.internal.util.MemInfoReader;
import com.android.server.wm.WindowManagerService;
import java.io.FileOutputStream;
import java.io.IOException;

class ProcessList {

    ProcessList() {
        int ai[] = new int[6];
        ai[0] = 0;
        ai[1] = 1;
        ai[2] = 2;
        ai[3] = 4;
        ai[4] = HIDDEN_APP_MIN_ADJ;
        ai[5] = 15;
        mOomAdj = ai;
        long al[] = new long[6];
        al[0] = 8192L;
        al[1] = 12288L;
        al[2] = 16384L;
        al[3] = 24576L;
        al[4] = 28672L;
        al[5] = 32768L;
        mOomMinFreeLow = al;
        long al1[] = new long[6];
        al1[0] = 32768L;
        al1[1] = 40960L;
        al1[2] = 49152L;
        al1[3] = 57344L;
        al1[4] = 0x10000L;
        al1[5] = 0x14000L;
        mOomMinFreeHigh = al1;
        mOomMinFree = new long[mOomAdj.length];
        MemInfoReader meminforeader = new MemInfoReader();
        meminforeader.readMemInfo();
        mTotalMemMb = meminforeader.getTotalSize() / 0x100000L;
        updateOomLevels(0, 0, false);
    }

    private void updateOomLevels(int i, int j, boolean flag) {
        StringBuilder stringbuilder;
        StringBuilder stringbuilder1;
        float f2;
        float f = (float)(mTotalMemMb - 300L) / 400F;
        float f1 = ((float)(i * j) - (float)0x25800) / (float)0xd4800;
        stringbuilder = new StringBuilder();
        stringbuilder1 = new StringBuilder();
        int k;
        long l;
        long l1;
        if(f > f1)
            f2 = f;
        else
            f2 = f1;
        if(f2 >= 0.0F) goto _L2; else goto _L1
_L1:
        f2 = 0.0F;
_L4:
        for(k = 0; k < mOomAdj.length; k++) {
            l = mOomMinFreeLow[k];
            l1 = mOomMinFreeHigh[k];
            mOomMinFree[k] = (long)((float)l + f2 * (float)(l1 - l));
            if(k > 0) {
                stringbuilder.append(',');
                stringbuilder1.append(',');
            }
            stringbuilder.append(mOomAdj[k]);
            stringbuilder1.append((1024L * mOomMinFree[k]) / 4096L);
        }

        break; /* Loop/switch isn't completed */
_L2:
        if(f2 > 1.0F)
            f2 = 1.0F;
        if(true) goto _L4; else goto _L3
_L3:
        if(flag) {
            writeFile("/sys/module/lowmemorykiller/parameters/adj", stringbuilder.toString());
            writeFile("/sys/module/lowmemorykiller/parameters/minfree", stringbuilder1.toString());
        }
        return;
    }

    private void writeFile(String s, String s1) {
        FileOutputStream fileoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(s);
        fileoutputstream1.write(s1.getBytes());
        if(fileoutputstream1 == null)
            break MISSING_BLOCK_LABEL_31;
        fileoutputstream1.close();
_L1:
        return;
        IOException ioexception3;
        ioexception3;
          goto _L1
        IOException ioexception4;
        ioexception4;
_L4:
        Slog.w("ActivityManager", (new StringBuilder()).append("Unable to write ").append(s).toString());
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L1
        Exception exception;
        exception;
_L3:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        exception;
        fileoutputstream = fileoutputstream1;
        if(true) goto _L3; else goto _L2
_L2:
        IOException ioexception;
        ioexception;
        fileoutputstream = fileoutputstream1;
          goto _L4
    }

    void applyDisplaySize(WindowManagerService windowmanagerservice) {
        if(!mHaveDisplaySize) {
            Point point = new Point();
            windowmanagerservice.getInitialDisplaySize(point);
            if(point.x != 0 && point.y != 0) {
                updateOomLevels(point.x, point.y, true);
                mHaveDisplaySize = true;
            }
        }
    }

    long getMemLevel(int i) {
        int j = 0;
_L3:
        if(j >= mOomAdj.length)
            break MISSING_BLOCK_LABEL_40;
        if(i > mOomAdj[j]) goto _L2; else goto _L1
_L1:
        long l = 1024L * mOomMinFree[j];
_L4:
        return l;
_L2:
        j++;
          goto _L3
        l = 1024L * mOomMinFree[-1 + mOomAdj.length];
          goto _L4
    }

    static final int BACKUP_APP_ADJ = 4;
    static final long CONTENT_APP_IDLE_OFFSET = 15000L;
    static final long EMPTY_APP_IDLE_OFFSET = 0x1d4c0L;
    static final int FOREGROUND_APP_ADJ = 0;
    static final int HEAVY_WEIGHT_APP_ADJ = 3;
    static final int HIDDEN_APP_MAX_ADJ = 15;
    static int HIDDEN_APP_MIN_ADJ = 0;
    static final int HOME_APP_ADJ = 6;
    static final int MAX_HIDDEN_APPS = 15;
    static final int MIN_CRASH_INTERVAL = 60000;
    static final int MIN_HIDDEN_APPS = 2;
    static final int PAGE_SIZE = 4096;
    static final int PERCEPTIBLE_APP_ADJ = 2;
    static final int PERSISTENT_PROC_ADJ = -12;
    static final int PREVIOUS_APP_ADJ = 7;
    static final int SERVICE_ADJ = 5;
    static final int SERVICE_B_ADJ = 8;
    static final int SYSTEM_ADJ = -16;
    static final int VISIBLE_APP_ADJ = 1;
    private boolean mHaveDisplaySize;
    private final int mOomAdj[];
    private final long mOomMinFree[];
    private final long mOomMinFreeHigh[];
    private final long mOomMinFreeLow[];
    private final long mTotalMemMb;

    static  {
        HIDDEN_APP_MIN_ADJ = 9;
    }
}
