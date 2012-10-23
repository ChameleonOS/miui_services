// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.*;
import android.util.Slog;
import java.io.*;

public class SamplingProfilerService extends Binder {
    private class SamplingProfilerSettingsObserver extends ContentObserver {

        public void onChange(boolean flag) {
            SystemProperties.set("persist.sys.profiler_ms", Integer.valueOf(android.provider.Settings.Secure.getInt(mContentResolver, "sampling_profiler_ms", 0)).toString());
        }

        private ContentResolver mContentResolver;
        final SamplingProfilerService this$0;

        public SamplingProfilerSettingsObserver(ContentResolver contentresolver) {
            this$0 = SamplingProfilerService.this;
            super(null);
            mContentResolver = contentresolver;
            onChange(false);
        }
    }


    public SamplingProfilerService(Context context) {
        mContext = context;
        registerSettingObserver(context);
        startWorking(context);
    }

    private void handleSnapshotFile(File file, DropBoxManager dropboxmanager) {
        dropboxmanager.addFile("SamplingProfilerService", file, 0);
_L2:
        file.delete();
        return;
        IOException ioexception;
        ioexception;
        Slog.e("SamplingProfilerService", (new StringBuilder()).append("Can't add ").append(file.getPath()).append(" to dropbox").toString(), ioexception);
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        file.delete();
        throw exception;
    }

    private void registerSettingObserver(Context context) {
        ContentResolver contentresolver = context.getContentResolver();
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("sampling_profiler_ms"), false, new SamplingProfilerSettingsObserver(contentresolver));
    }

    private void startWorking(Context context) {
        DropBoxManager dropboxmanager = (DropBoxManager)context.getSystemService("dropbox");
        File afile[] = (new File("/data/snapshots")).listFiles();
        for(int i = 0; afile != null && i < afile.length; i++)
            handleSnapshotFile(afile[i], dropboxmanager);

        snapshotObserver = new FileObserver(4, dropboxmanager) {

            public void onEvent(int j, String s) {
                handleSnapshotFile(new File("/data/snapshots", s), dropbox);
            }

            final SamplingProfilerService this$0;
            final DropBoxManager val$dropbox;

             {
                this$0 = SamplingProfilerService.this;
                dropbox = dropboxmanager;
                super(final_s, i);
            }
        };
        snapshotObserver.startWatching();
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "SamplingProfilerService");
        printwriter.println("SamplingProfilerService:");
        printwriter.println("Watching directory: /data/snapshots");
    }

    private static final boolean LOCAL_LOGV = false;
    public static final String SNAPSHOT_DIR = "/data/snapshots";
    private static final String TAG = "SamplingProfilerService";
    private final Context mContext;
    private FileObserver snapshotObserver;

}
