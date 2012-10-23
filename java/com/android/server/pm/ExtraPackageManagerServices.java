// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.PackageParser;
import android.os.FileUtils;
import android.util.Log;
import java.io.*;
import java.util.ArrayList;

// Referenced classes of package com.android.server.pm:
//            Settings, GrantedPermissions

class ExtraPackageManagerServices {

    ExtraPackageManagerServices() {
    }

    private static void installPreinstallApp(File file) {
        String s = file.getName();
        File file1 = new File((new StringBuilder()).append("/data/app/").append(s).toString());
        FileUtils.copyFile(file, file1);
        file1.setReadable(true, false);
        String s1 = s.replace(".apk", ".odex");
        File file2 = new File((new StringBuilder()).append("/data/app/").append(s1).toString());
        if(file2.exists())
            file2.delete();
    }

    private static final boolean isPackageFilename(String s) {
        boolean flag;
        if(s != null && s.endsWith(".apk"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void performPreinstallApp(Settings settings) {
        File file;
        String as[];
        file = new File("/data/media/preinstall_apps/");
        as = file.list();
        if(as != null) goto _L2; else goto _L1
_L1:
        Log.d("ExtraPackageManager", (new StringBuilder()).append("No files in preinstall app dir ").append(file).toString());
_L4:
        return;
_L2:
        File file1;
        String as1[];
        ArrayList arraylist;
        int i;
        file1 = new File("/data/app/");
        as1 = file1.list();
        arraylist = readPreinstallAppHistory("/data/system/preinstall_history");
        i = 0;
_L5:
        if(i >= as.length) goto _L4; else goto _L3
_L3:
        File file2;
        String s = as[i];
        if(isPackageFilename(s)) {
label0:
            {
                file2 = new File(file, s);
                File file3 = new File(file1, s);
                if(!arraylist.contains(s))
                    break label0;
                if(file3.exists() && file3.length() != file2.length())
                    installPreinstallApp(file2);
            }
        }
_L8:
        i++;
          goto _L5
          goto _L4
        if(as1 != null && as1.length != 0) goto _L7; else goto _L6
_L6:
        installPreinstallApp(file2);
_L9:
        writePreinstallAppHistory("/data/system/preinstall_history", file2.getName());
          goto _L8
_L7:
        android.content.pm.PackageParser.Package package1;
label1:
        {
            package1 = (new PackageParser(file2.getPath())).parsePackage(file2, file2.getPath(), null, 4);
            if(package1 != null)
                break label1;
            Log.d("ExtraPackageManager", (new StringBuilder()).append("preinstall app ").append(file2.getName()).append(" package parser fail!").toString());
        }
          goto _L8
        PackageSetting packagesetting = settings.peekPackageLPr(package1.packageName);
        if(packagesetting == null || (1 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
            installPreinstallApp(file2);
          goto _L9
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
    private static final String PREINSTALL_DIR = "/data/media/preinstall_apps/";
    private static final String PREINSTALL_HISTORY_FILE = "/data/system/preinstall_history";
    private static final String TAG = "ExtraPackageManager";
}
