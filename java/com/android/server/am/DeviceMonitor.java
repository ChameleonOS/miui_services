// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.util.Slog;
import java.io.*;
import java.util.Arrays;

class DeviceMonitor {

    private DeviceMonitor() {
        running = false;
        (new Thread() {

            public void run() {
                monitor();
            }

            final DeviceMonitor this$0;

             {
                this$0 = DeviceMonitor.this;
                super();
            }
        }).start();
    }

    private static void closeQuietly(Closeable closeable) {
        if(closeable == null)
            break MISSING_BLOCK_LABEL_10;
        closeable.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.w(LOG_TAG, ioexception);
          goto _L1
    }

    private void dump() throws IOException {
        FileOutputStream fileoutputstream = new FileOutputStream(new File(BASE, String.valueOf(System.currentTimeMillis())));
        File afile[];
        int i;
        afile = PROC.listFiles();
        i = afile.length;
        Exception exception;
        File afile1[];
        int k;
        int l;
        for(int j = 0; j < i; j++) {
            File file = afile[j];
            if(isProcessDirectory(file))
                dump(new File(file, "stat"), ((OutputStream) (fileoutputstream)));
            break MISSING_BLOCK_LABEL_126;
        }

        afile1 = PATHS;
        k = afile1.length;
        l = 0;
_L1:
        if(l >= k)
            break MISSING_BLOCK_LABEL_114;
        dump(afile1[l], ((OutputStream) (fileoutputstream)));
        l++;
          goto _L1
        closeQuietly(fileoutputstream);
        return;
        exception;
        closeQuietly(fileoutputstream);
        throw exception;
    }

    private void dump(File file, OutputStream outputstream) throws IOException {
        Object obj;
        writeHeader(file, outputstream);
        obj = null;
        FileInputStream fileinputstream;
        fileinputstream = new FileInputStream(file);
        break MISSING_BLOCK_LABEL_17;
        Exception exception;
        exception;
        obj = fileinputstream;
_L2:
        closeQuietly(((Closeable) (obj)));
        throw exception;
        do {
            int i = fileinputstream.read(buffer);
            if(i == -1)
                break;
            outputstream.write(buffer, 0, i);
        } while(true);
        closeQuietly(fileinputstream);
        return;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static boolean isProcessDirectory(File file) {
        boolean flag1;
        Integer.parseInt(file.getName());
        flag1 = file.isDirectory();
        boolean flag = flag1;
_L2:
        return flag;
        NumberFormatException numberformatexception;
        numberformatexception;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void monitor() {
        do {
            waitForStart();
            purge();
            int i = 0;
            while(i < 10)  {
                try {
                    dump();
                }
                catch(IOException ioexception) {
                    Slog.w(LOG_TAG, "Dump failed.", ioexception);
                }
                pause();
                i++;
            }
            stop();
        } while(true);
    }

    private void pause() {
        Thread.sleep(1000L);
_L2:
        return;
        InterruptedException interruptedexception;
        interruptedexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void purge() {
        File afile[] = BASE.listFiles();
        int i = -30 + afile.length;
        if(i > 0) {
            Arrays.sort(afile);
            for(int j = 0; j < i; j++)
                if(!afile[j].delete())
                    Slog.w(LOG_TAG, (new StringBuilder()).append("Couldn't delete ").append(afile[j]).append(".").toString());

        }
    }

    static void start() {
        instance.startMonitoring();
    }

    /**
     * @deprecated Method startMonitoring is deprecated
     */

    private void startMonitoring() {
        this;
        JVM INSTR monitorenter ;
        if(!running) {
            running = true;
            notifyAll();
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method stop is deprecated
     */

    private void stop() {
        this;
        JVM INSTR monitorenter ;
        running = false;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method waitForStart is deprecated
     */

    private void waitForStart() {
        this;
        JVM INSTR monitorenter ;
_L3:
        boolean flag = running;
        if(flag) goto _L2; else goto _L1
_L1:
        Exception exception;
        try {
            wait();
        }
        catch(InterruptedException interruptedexception) { }
        finally {
            this;
        }
        if(true) goto _L3; else goto _L2
_L2:
        this;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    private static void writeHeader(File file, OutputStream outputstream) throws IOException {
        outputstream.write((new StringBuilder()).append("*** ").append(file.toString()).append("\n").toString().getBytes());
    }

    private static final File BASE;
    private static final int INTERVAL = 1000;
    private static final String LOG_TAG = com/android/server/am/DeviceMonitor.getName();
    private static final int MAX_FILES = 30;
    private static final File PATHS[];
    private static final File PROC;
    private static final int SAMPLE_COUNT = 10;
    private static DeviceMonitor instance;
    private final byte buffer[] = new byte[1024];
    private boolean running;

    static  {
        PROC = new File("/proc");
        BASE = new File("/data/anr/");
        if(!BASE.isDirectory() && !BASE.mkdirs()) {
            throw new AssertionError((new StringBuilder()).append("Couldn't create ").append(BASE).append(".").toString());
        } else {
            File afile[] = new File[4];
            afile[0] = new File(PROC, "zoneinfo");
            afile[1] = new File(PROC, "interrupts");
            afile[2] = new File(PROC, "meminfo");
            afile[3] = new File(PROC, "slabinfo");
            PATHS = afile;
            instance = new DeviceMonitor();
        }
    }

}
