// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.PackageParser;
import android.os.FileUtils;
import android.util.Log;
import java.io.*;
import java.util.*;
import miui.os.Build;

// Referenced classes of package com.android.server.pm:
//            PackageSetting, Settings, BasePermission, GrantedPermissions, 
//            PackageManagerService, PackageSettingBase

class ExtraPackageManagerServices {

    ExtraPackageManagerServices() {
    }

    private static void deleteOdexFile(String s) {
        String s1 = s.replace(".apk", ".odex");
        File file = new File((new StringBuilder()).append("/data/app/").append(s1).toString());
        if(file.exists())
            file.delete();
    }

    private static void installPreinstallApp(File file) {
        String s = file.getName();
        File file1 = new File((new StringBuilder()).append("/data/app/").append(s).toString());
        FileUtils.copyFile(file, file1);
        file1.setReadable(true, false);
        deleteOdexFile(s);
    }

    private static final boolean isPackageFilename(String s) {
        boolean flag;
        if(s != null && s.endsWith(".apk"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static void packagePermissionsUpdate(Settings settings, PackageSetting packagesetting, android.content.pm.PackageParser.Package package1) {
        Object obj;
        int i;
        int j;
        if(packagesetting.sharedUser != null)
            obj = packagesetting.sharedUser;
        else
            obj = packagesetting;
        i = package1.requestedPermissions.size();
        j = 0;
        while(j < i)  {
            String s = (String)package1.requestedPermissions.get(j);
            BasePermission basepermission = (BasePermission)settings.mPermissions.get(s);
            if(basepermission != null) {
                String s1 = basepermission.name;
                if(!((GrantedPermissions) (obj)).grantedPermissions.contains(s1)) {
                    ((GrantedPermissions) (obj)).grantedPermissions.add(s1);
                    obj.gids = PackageManagerService.appendInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
                } else
                if(!((PackageSettingBase) (packagesetting)).haveGids)
                    obj.gids = PackageManagerService.appendInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
            }
            j++;
        }
    }

    private static android.content.pm.PackageParser.Package parsePackage(File file) {
        String s = file.getPath();
        return (new PackageParser(s)).parsePackage(file, s, null, 4);
    }

    public static void performPreinstallApp(Settings settings) {
        String s;
        File file;
        String as[];
        if(Build.IS_XIAOMI)
            s = "/data/media/preinstall_apps/";
        else
            s = "/data/preinstall_apps/";
        file = new File(s);
        as = file.list();
        if(as != null) goto _L2; else goto _L1
_L1:
        Log.d("ExtraPackageManagerServices", (new StringBuilder()).append("No files in preinstall app dir ").append(file).toString());
_L4:
        return;
_L2:
        ArrayList arraylist = readPreinstallAppHistory("/data/system/preinstall_history");
        File file1 = new File((new StringBuilder()).append(s).append("reinstall_apps").toString());
        boolean flag = file1.exists();
        int i = 0;
        while(i < as.length)  {
            String s1 = as[i];
            if(isPackageFilename(s1)) {
                boolean flag1 = arraylist.contains(s1);
                File file2 = new File(file, s1);
                android.content.pm.PackageParser.Package package1 = parsePackage(file2);
                if(package1 == null) {
                    Log.d("ExtraPackageManagerServices", (new StringBuilder()).append("preinstall app ").append(s1).append(" package parser fail!").toString());
                } else {
                    PackageSetting packagesetting = settings.peekPackageLPr(package1.packageName);
                    if(packagesetting != null) {
                        if(package1.mVersionCode > ((PackageSettingBase) (packagesetting)).versionCode) {
                            if((1 & ((GrantedPermissions) (packagesetting)).pkgFlags) == 0) {
                                File file3 = ((PackageSettingBase) (packagesetting)).codePath;
                                deleteOdexFile(file3.getName());
                                file3.delete();
                            }
                            installPreinstallApp(file2);
                            packagePermissionsUpdate(settings, packagesetting, package1);
                        }
                    } else
                    if(!flag1 || flag)
                        installPreinstallApp(file2);
                    if(!flag1)
                        writePreinstallAppHistory("/data/system/preinstall_history", s1);
                }
            }
            i++;
        }
        if(flag)
            file1.delete();
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static ArrayList readPreinstallAppHistory(String s) {
        ArrayList arraylist = new ArrayList();
        try {
            File file = new File(s);
            if(file.exists()) {
                FileReader filereader = new FileReader(file);
                BufferedReader bufferedreader = new BufferedReader(filereader);
                do {
                    String s1 = bufferedreader.readLine();
                    if(s1 == null)
                        break;
                    arraylist.add(s1);
                } while(true);
                bufferedreader.close();
                filereader.close();
            }
        }
        catch(IOException ioexception) { }
        return arraylist;
    }

    private static void writePreinstallAppHistory(String s, String s1) {
        File file = new File(s);
        if(!file.exists())
            file.createNewFile();
        FileWriter filewriter = new FileWriter(file, true);
        BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
        bufferedwriter.write(s1);
        bufferedwriter.write("\n");
        bufferedwriter.close();
        filewriter.close();
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static final String INSTALL_DIR = "/data/app/";
    private static final String PREINSTALL_HISTORY_FILE = "/data/system/preinstall_history";
    private static final String REINSTALL_MARK_FILE = "reinstall_apps";
    private static final String TAG = "ExtraPackageManagerServices";
    private static final String THIRD_PART_DEV_PREINSTALL_DIR = "/data/preinstall_apps/";
    private static final String XIAOMI_PREINSTALL_DIR = "/data/media/preinstall_apps/";
}
