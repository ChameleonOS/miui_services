// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.backup.*;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.util.Slog;
import java.io.File;
import java.io.IOException;

// Referenced classes of package com.android.server:
//            WallpaperManagerService

public class SystemBackupAgent extends BackupAgentHelper {

    public SystemBackupAgent() {
    }

    private void fullWallpaperBackup(FullBackupDataOutput fullbackupdataoutput) {
        FullBackup.backupToTar(getPackageName(), "r", null, "/data/system/users/0", "/data/system/users/0/wallpaper_info.xml", fullbackupdataoutput.getData());
        FullBackup.backupToTar(getPackageName(), "r", null, "/data/system/users/0", "/data/system/users/0/wallpaper", fullbackupdataoutput.getData());
    }

    public void onBackup(ParcelFileDescriptor parcelfiledescriptor, BackupDataOutput backupdataoutput, ParcelFileDescriptor parcelfiledescriptor1) throws IOException {
        WallpaperManagerService wallpapermanagerservice = (WallpaperManagerService)ServiceManager.getService("wallpaper");
        String as[] = new String[2];
        as[0] = "/data/system/users/0/wallpaper";
        as[1] = "/data/system/users/0/wallpaper_info.xml";
        String as1[] = new String[2];
        as1[0] = "/data/data/com.android.settings/files/wallpaper";
        as1[1] = "/data/system/wallpaper_info.xml";
        if(wallpapermanagerservice != null && wallpapermanagerservice.getName() != null && wallpapermanagerservice.getName().length() > 0) {
            as = new String[1];
            as[0] = "/data/system/users/0/wallpaper_info.xml";
            as1 = new String[1];
            as1[0] = "/data/system/wallpaper_info.xml";
        }
        addHelper("wallpaper", new WallpaperBackupHelper(this, as, as1));
        super.onBackup(parcelfiledescriptor, backupdataoutput, parcelfiledescriptor1);
    }

    public void onFullBackup(FullBackupDataOutput fullbackupdataoutput) throws IOException {
        fullWallpaperBackup(fullbackupdataoutput);
    }

    public void onRestore(BackupDataInput backupdatainput, int i, ParcelFileDescriptor parcelfiledescriptor) throws IOException {
        String as[] = new String[2];
        as[0] = "/data/system/users/0/wallpaper";
        as[1] = "/data/system/users/0/wallpaper_info.xml";
        String as1[] = new String[2];
        as1[0] = "/data/data/com.android.settings/files/wallpaper";
        as1[1] = "/data/system/wallpaper_info.xml";
        addHelper("wallpaper", new WallpaperBackupHelper(this, as, as1));
        String as2[] = new String[1];
        as2[0] = "/data/system/users/0/wallpaper";
        String as3[] = new String[1];
        as3[0] = "/data/data/com.android.settings/files/wallpaper";
        addHelper("system_files", new WallpaperBackupHelper(this, as2, as3));
        super.onRestore(backupdatainput, i, parcelfiledescriptor);
        ((WallpaperManagerService)ServiceManager.getService("wallpaper")).settingsRestored();
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.d("SystemBackupAgent", "restore failed", ioexception);
        (new File("/data/system/users/0/wallpaper")).delete();
        (new File("/data/system/users/0/wallpaper_info.xml")).delete();
          goto _L1
    }

    public void onRestoreFile(ParcelFileDescriptor parcelfiledescriptor, long l, int i, String s, String s1, long l1, long l2) throws IOException {
        boolean flag;
        Slog.i("SystemBackupAgent", (new StringBuilder()).append("Restoring file domain=").append(s).append(" path=").append(s1).toString());
        flag = false;
        File file = null;
        if(s.equals("r"))
            if(s1.equals("wallpaper_info.xml")) {
                file = new File("/data/system/users/0/wallpaper_info.xml");
                flag = true;
            } else
            if(s1.equals("wallpaper")) {
                file = new File("/data/system/users/0/wallpaper");
                flag = true;
            }
        if(file != null)
            break MISSING_BLOCK_LABEL_122;
        Slog.w("SystemBackupAgent", (new StringBuilder()).append("Skipping unrecognized system file: [ ").append(s).append(" : ").append(s1).append(" ]").toString());
        FullBackup.restoreFile(parcelfiledescriptor, l, i, l1, l2, file);
        if(flag)
            ((WallpaperManagerService)ServiceManager.getService("wallpaper")).settingsRestored();
_L1:
        return;
        IOException ioexception;
        ioexception;
        if(flag) {
            (new File("/data/system/users/0/wallpaper")).delete();
            (new File("/data/system/users/0/wallpaper_info.xml")).delete();
        }
          goto _L1
    }

    private static final String TAG = "SystemBackupAgent";
    private static final String WALLPAPER_IMAGE = "/data/system/users/0/wallpaper";
    private static final String WALLPAPER_IMAGE_DIR = "/data/system/users/0";
    private static final String WALLPAPER_IMAGE_FILENAME = "wallpaper";
    private static final String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    private static final String WALLPAPER_INFO = "/data/system/users/0/wallpaper_info.xml";
    private static final String WALLPAPER_INFO_DIR = "/data/system/users/0";
    private static final String WALLPAPER_INFO_FILENAME = "wallpaper_info.xml";
    private static final String WALLPAPER_INFO_KEY = "/data/system/wallpaper_info.xml";
}
