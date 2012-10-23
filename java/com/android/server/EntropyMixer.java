// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.os.*;
import android.util.Slog;
import java.io.*;

// Referenced classes of package com.android.server:
//            RandomBlock

public class EntropyMixer extends Binder {

    public EntropyMixer() {
        this((new StringBuilder()).append(getSystemDir()).append("/entropy.dat").toString(), "/dev/urandom");
    }

    public EntropyMixer(String s, String s1) {
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                if(message.what != 1) {
                    Slog.e("EntropyMixer", "Will not process invalid message");
                } else {
                    writeEntropy();
                    scheduleEntropyWriter();
                }
            }

            final EntropyMixer this$0;

             {
                this$0 = EntropyMixer.this;
                super();
            }
        };
        if(s1 == null)
            throw new NullPointerException("randomDevice");
        if(s == null) {
            throw new NullPointerException("entropyFile");
        } else {
            randomDevice = s1;
            entropyFile = s;
            loadInitialEntropy();
            addDeviceSpecificEntropy();
            writeEntropy();
            scheduleEntropyWriter();
            return;
        }
    }

    private void addDeviceSpecificEntropy() {
        PrintWriter printwriter = null;
        PrintWriter printwriter1 = new PrintWriter(new FileOutputStream(randomDevice));
        printwriter1.println("Copyright (C) 2009 The Android Open Source Project");
        printwriter1.println("All Your Randomness Are Belong To Us");
        printwriter1.println(START_TIME);
        printwriter1.println(START_NANOTIME);
        printwriter1.println(SystemProperties.get("ro.serialno"));
        printwriter1.println(SystemProperties.get("ro.bootmode"));
        printwriter1.println(SystemProperties.get("ro.baseband"));
        printwriter1.println(SystemProperties.get("ro.carrier"));
        printwriter1.println(SystemProperties.get("ro.bootloader"));
        printwriter1.println(SystemProperties.get("ro.hardware"));
        printwriter1.println(SystemProperties.get("ro.revision"));
        printwriter1.println((new Object()).hashCode());
        printwriter1.println(System.currentTimeMillis());
        printwriter1.println(System.nanoTime());
        if(printwriter1 != null)
            printwriter1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L4:
        Slog.w("EntropyMixer", "Unable to add device specific data to the entropy pool", ioexception);
        if(printwriter != null)
            printwriter.close();
          goto _L1
        Exception exception;
        exception;
_L3:
        if(printwriter != null)
            printwriter.close();
        throw exception;
        exception;
        printwriter = printwriter1;
        if(true) goto _L3; else goto _L2
_L2:
        ioexception;
        printwriter = printwriter1;
          goto _L4
    }

    private static String getSystemDir() {
        File file = new File(Environment.getDataDirectory(), "system");
        file.mkdirs();
        return file.toString();
    }

    private void loadInitialEntropy() {
        RandomBlock.fromFile(entropyFile).toFile(randomDevice, false);
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.w("EntropyMixer", "unable to load initial entropy (first boot?)", ioexception);
          goto _L1
    }

    private void scheduleEntropyWriter() {
        mHandler.removeMessages(1);
        mHandler.sendEmptyMessageDelayed(1, 0xa4cb80L);
    }

    private void writeEntropy() {
        RandomBlock.fromFile(randomDevice).toFile(entropyFile, true);
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.w("EntropyMixer", "unable to write entropy", ioexception);
          goto _L1
    }

    private static final int ENTROPY_WHAT = 1;
    private static final int ENTROPY_WRITE_PERIOD = 0xa4cb80;
    private static final long START_NANOTIME = 0L;
    private static final long START_TIME = 0L;
    private static final String TAG = "EntropyMixer";
    private final String entropyFile;
    private final Handler mHandler;
    private final String randomDevice;

    static  {
        START_TIME = System.currentTimeMillis();
        START_NANOTIME = System.nanoTime();
    }


}
