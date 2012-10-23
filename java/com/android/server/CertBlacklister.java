// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.FileUtils;
import android.util.Slog;
import java.io.*;
import libcore.io.IoUtils;

public class CertBlacklister extends Binder {
    private static class BlacklistObserver extends ContentObserver {

        private void writeBlacklist() {
            (new Thread("BlacklistUpdater") {

                public void run() {
                    File file = mTmpDir;
                    file;
                    JVM INSTR monitorenter ;
                    String s;
                    s = getValue();
                    if(s == null)
                        break MISSING_BLOCK_LABEL_116;
                    Slog.i("CertBlacklister", "Certificate blacklist changed, updating...");
                    Object obj = null;
                    File file1;
                    FileOutputStream fileoutputstream;
                    file1 = File.createTempFile("journal", "", mTmpDir);
                    file1.setReadable(true, false);
                    fileoutputstream = new FileOutputStream(file1);
                    fileoutputstream.write(s.getBytes());
                    FileUtils.sync(fileoutputstream);
                    file1.renameTo(new File(mPath));
                    Slog.i("CertBlacklister", "Certificate blacklist updated");
                    IoUtils.closeQuietly(fileoutputstream);
_L1:
                    file;
                    JVM INSTR monitorexit ;
                    return;
                    IOException ioexception;
                    ioexception;
_L4:
                    Slog.e("CertBlacklister", "Failed to write blacklist", ioexception);
                    IoUtils.closeQuietly(((AutoCloseable) (obj)));
                      goto _L1
                    Exception exception;
                    exception;
                    throw exception;
                    Exception exception1;
                    exception1;
_L3:
                    IoUtils.closeQuietly(((AutoCloseable) (obj)));
                    throw exception1;
                    exception1;
                    obj = fileoutputstream;
                    if(true) goto _L3; else goto _L2
_L2:
                    ioexception;
                    obj = fileoutputstream;
                      goto _L4
                }

                final BlacklistObserver this$0;

                 {
                    this$0 = BlacklistObserver.this;
                    super(s);
                }
            }).start();
        }

        public String getValue() {
            return android.provider.Settings.Secure.getString(mContentResolver, mKey);
        }

        public void onChange(boolean flag) {
            super.onChange(flag);
            writeBlacklist();
        }

        private final ContentResolver mContentResolver;
        private final String mKey;
        private final String mName;
        private final String mPath;
        private final File mTmpDir;



        public BlacklistObserver(String s, String s1, String s2, ContentResolver contentresolver) {
            super(null);
            mKey = s;
            mName = s1;
            mPath = s2;
            mTmpDir = (new File(mPath)).getParentFile();
            mContentResolver = contentresolver;
        }
    }


    public CertBlacklister(Context context) {
        registerObservers(context.getContentResolver());
    }

    private BlacklistObserver buildPubkeyObserver(ContentResolver contentresolver) {
        return new BlacklistObserver("pubkey_blacklist", "pubkey", PUBKEY_PATH, contentresolver);
    }

    private BlacklistObserver buildSerialObserver(ContentResolver contentresolver) {
        return new BlacklistObserver("serial_blacklist", "serial", SERIAL_PATH, contentresolver);
    }

    private void registerObservers(ContentResolver contentresolver) {
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("pubkey_blacklist"), true, buildPubkeyObserver(contentresolver));
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("serial_blacklist"), true, buildSerialObserver(contentresolver));
    }

    private static final String BLACKLIST_ROOT = (new StringBuilder()).append(System.getenv("ANDROID_DATA")).append("/misc/keychain/").toString();
    public static final String PUBKEY_BLACKLIST_KEY = "pubkey_blacklist";
    public static final String PUBKEY_PATH = (new StringBuilder()).append(BLACKLIST_ROOT).append("pubkey_blacklist.txt").toString();
    public static final String SERIAL_BLACKLIST_KEY = "serial_blacklist";
    public static final String SERIAL_PATH = (new StringBuilder()).append(BLACKLIST_ROOT).append("serial_blacklist.txt").toString();
    private static final String TAG = "CertBlacklister";

}
