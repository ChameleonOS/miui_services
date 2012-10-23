// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.os.*;
import android.provider.Downloads;
import android.util.Slog;
import java.io.File;
import java.io.IOException;

public class BootReceiver extends BroadcastReceiver {

    public BootReceiver() {
    }

    private static void addFileToDropBox(DropBoxManager dropboxmanager, SharedPreferences sharedpreferences, String s, String s1, int i, String s2) throws IOException {
        if(dropboxmanager != null && dropboxmanager.isTagEnabled(s2)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        File file = new File(s1);
        long l = file.lastModified();
        if(l <= 0L)
            continue; /* Loop/switch isn't completed */
        if(sharedpreferences != null) {
            if(sharedpreferences.getLong(s1, 0L) == l)
                continue; /* Loop/switch isn't completed */
            sharedpreferences.edit().putLong(s1, l).apply();
        }
        Slog.i("BootReceiver", (new StringBuilder()).append("Copying ").append(s1).append(" to DropBox (").append(s2).append(")").toString());
        dropboxmanager.addText(s2, (new StringBuilder()).append(s).append(FileUtils.readTextFile(file, i, "[[TRUNCATED]]\n")).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void logBootEvents(Context context) throws IOException {
        DropBoxManager dropboxmanager;
        SharedPreferences sharedpreferences;
        String s;
        dropboxmanager = (DropBoxManager)context.getSystemService("dropbox");
        sharedpreferences = context.getSharedPreferences("log_files", 0);
        s = (new StringBuilder(512)).append("Build: ").append(Build.FINGERPRINT).append("\n").append("Hardware: ").append(Build.BOARD).append("\n").append("Bootloader: ").append(Build.BOOTLOADER).append("\n").append("Radio: ").append(Build.RADIO).append("\n").append("Kernel: ").append(FileUtils.readTextFile(new File("/proc/version"), 1024, "...\n")).append("\n").toString();
        String s1 = RecoverySystem.handleAftermath();
        if(s1 != null && dropboxmanager != null)
            dropboxmanager.addText("SYSTEM_RECOVERY_LOG", (new StringBuilder()).append(s).append(s1).toString());
        if(SystemProperties.getLong("ro.runtime.firstboot", 0L) != 0L) goto _L2; else goto _L1
_L1:
        SystemProperties.set("ro.runtime.firstboot", Long.toString(System.currentTimeMillis()));
        if(dropboxmanager != null)
            dropboxmanager.addText("SYSTEM_BOOT", s);
        addFileToDropBox(dropboxmanager, sharedpreferences, s, "/proc/last_kmsg", -LOG_SIZE, "SYSTEM_LAST_KMSG");
        addFileToDropBox(dropboxmanager, sharedpreferences, s, "/cache/recovery/log", -LOG_SIZE, "SYSTEM_RECOVERY_LOG");
        addFileToDropBox(dropboxmanager, sharedpreferences, s, "/data/dontpanic/apanic_console", -LOG_SIZE, "APANIC_CONSOLE");
        addFileToDropBox(dropboxmanager, sharedpreferences, s, "/data/dontpanic/apanic_threads", -LOG_SIZE, "APANIC_THREADS");
_L4:
        File afile[] = TOMBSTONE_DIR.listFiles();
        for(int i = 0; afile != null && i < afile.length; i++)
            addFileToDropBox(dropboxmanager, sharedpreferences, s, afile[i].getPath(), LOG_SIZE, "SYSTEM_TOMBSTONE");

        break; /* Loop/switch isn't completed */
_L2:
        if(dropboxmanager != null)
            dropboxmanager.addText("SYSTEM_RESTART", s);
        if(true) goto _L4; else goto _L3
_L3:
        sTombstoneObserver = new FileObserver(sharedpreferences, s) {

            public void onEvent(int j, String s2) {
                String s3 = (new File(BootReceiver.TOMBSTONE_DIR, s2)).getPath();
                BootReceiver.addFileToDropBox(db, prefs, headers, s3, BootReceiver.LOG_SIZE, "SYSTEM_TOMBSTONE");
_L1:
                return;
                IOException ioexception;
                ioexception;
                Slog.e("BootReceiver", "Can't log tombstone", ioexception);
                  goto _L1
            }

            final BootReceiver this$0;
            final DropBoxManager val$db;
            final String val$headers;
            final SharedPreferences val$prefs;

             {
                this$0 = BootReceiver.this;
                db = dropboxmanager;
                prefs = sharedpreferences;
                headers = s1;
                super(final_s, final_i);
            }
        };
        sTombstoneObserver.startWatching();
        return;
    }

    private void removeOldUpdatePackages(Context context) {
        Downloads.removeAllDownloadsByPackage(context, "com.google.android.systemupdater", "com.google.android.systemupdater.SystemUpdateReceiver");
    }

    public void onReceive(final Context context, Intent intent) {
        (new Thread() {

            public void run() {
                try {
                    logBootEvents(context);
                }
                catch(Exception exception) {
                    Slog.e("BootReceiver", "Can't log boot events", exception);
                }
                removeOldUpdatePackages(context);
_L1:
                return;
                Exception exception1;
                exception1;
                Slog.e("BootReceiver", "Can't remove old update packages", exception1);
                  goto _L1
            }

            final BootReceiver this$0;
            final Context val$context;

             {
                this$0 = BootReceiver.this;
                context = context1;
                super();
            }
        }).start();
    }

    private static final int LOG_SIZE = 0;
    private static final String OLD_UPDATER_CLASS = "com.google.android.systemupdater.SystemUpdateReceiver";
    private static final String OLD_UPDATER_PACKAGE = "com.google.android.systemupdater";
    private static final String TAG = "BootReceiver";
    private static final File TOMBSTONE_DIR = new File("/data/tombstones");
    private static FileObserver sTombstoneObserver = null;

    static  {
        int i;
        if(SystemProperties.getInt("ro.debuggable", 0) == 1)
            i = 0x18000;
        else
            i = 0x10000;
        LOG_SIZE = i;
    }





}
