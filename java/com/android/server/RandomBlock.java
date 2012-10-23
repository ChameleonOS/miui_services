// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.util.Slog;
import java.io.*;

class RandomBlock {

    private RandomBlock() {
        block = new byte[4096];
    }

    private static void close(Closeable closeable) {
        if(closeable != null)
            try {
                closeable.close();
            }
            catch(IOException ioexception) {
                Slog.w("RandomBlock", "IOException thrown while closing Closeable", ioexception);
            }
    }

    static RandomBlock fromFile(String s) throws IOException {
        Object obj = null;
        FileInputStream fileinputstream = new FileInputStream(s);
        RandomBlock randomblock = fromStream(fileinputstream);
        close(fileinputstream);
        return randomblock;
        Exception exception;
        exception;
_L2:
        close(((Closeable) (obj)));
        throw exception;
        exception;
        obj = fileinputstream;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static RandomBlock fromStream(InputStream inputstream) throws IOException {
        RandomBlock randomblock = new RandomBlock();
        int j;
        for(int i = 0; i < 4096; i += j) {
            j = inputstream.read(randomblock.block, i, 4096 - i);
            if(j == -1)
                throw new EOFException();
        }

        return randomblock;
    }

    private void toDataOut(DataOutput dataoutput) throws IOException {
        dataoutput.write(block);
    }

    private static void truncateIfPossible(RandomAccessFile randomaccessfile) {
        randomaccessfile.setLength(4096L);
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void toFile(String s, boolean flag) throws IOException {
        Object obj = null;
        String s1;
        if(!flag)
            break MISSING_BLOCK_LABEL_39;
        s1 = "rws";
_L1:
        RandomAccessFile randomaccessfile = new RandomAccessFile(s, s1);
        toDataOut(randomaccessfile);
        truncateIfPossible(randomaccessfile);
        close(randomaccessfile);
        return;
        s1 = "rw";
          goto _L1
        Exception exception;
        exception;
_L3:
        close(((Closeable) (obj)));
        throw exception;
        exception;
        obj = randomaccessfile;
        if(true) goto _L3; else goto _L2
_L2:
    }

    private static final int BLOCK_SIZE = 4096;
    private static final boolean DEBUG = false;
    private static final String TAG = "RandomBlock";
    private byte block[];
}
