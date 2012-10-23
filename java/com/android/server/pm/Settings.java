// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import com.android.internal.util.*;
import com.android.server.IntentResolver;
import com.android.server.PreferredComponent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import libcore.io.IoUtils;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.pm:
//            PackageSetting, PackageSettingBase, PackageManagerService, SharedUserSetting, 
//            GrantedPermissions, PackageSignatures, PendingPackage, BasePermission, 
//            PreferredActivity

final class Settings {

    Settings() {
        this(Environment.getDataDirectory());
    }

    Settings(File file) {
        mPackages = new HashMap();
        mDisabledSysPackages = new HashMap();
        mPreferredActivities = new IntentResolver() {

            protected volatile void dumpFilter(PrintWriter printwriter, String s, IntentFilter intentfilter) {
                dumpFilter(printwriter, s, (PreferredActivity)intentfilter);
            }

            protected void dumpFilter(PrintWriter printwriter, String s, PreferredActivity preferredactivity) {
                preferredactivity.mPref.dump(printwriter, s, preferredactivity);
            }

            protected volatile String packageForFilter(IntentFilter intentfilter) {
                return packageForFilter((PreferredActivity)intentfilter);
            }

            protected String packageForFilter(PreferredActivity preferredactivity) {
                return preferredactivity.mPref.mComponent.getPackageName();
            }

            final Settings this$0;

             {
                this$0 = Settings.this;
                super();
            }
        };
        mSharedUsers = new HashMap();
        mUserIds = new ArrayList();
        mOtherUserIds = new SparseArray();
        mPastSignatures = new ArrayList();
        mPermissions = new HashMap();
        mPermissionTrees = new HashMap();
        mPackagesToBeCleaned = new ArrayList();
        mRenamedPackages = new HashMap();
        mReadMessages = new StringBuilder();
        mPendingPackages = new ArrayList();
        mSystemDir = new File(file, "system");
        mSystemDir.mkdirs();
        FileUtils.setPermissions(mSystemDir.toString(), 509, -1, -1);
        mSettingsFilename = new File(mSystemDir, "packages.xml");
        mBackupSettingsFilename = new File(mSystemDir, "packages-backup.xml");
        mPackageListFilename = new File(mSystemDir, "packages.list");
        mStoppedPackagesFilename = new File(mSystemDir, "packages-stopped.xml");
        mBackupStoppedPackagesFilename = new File(mSystemDir, "packages-stopped-backup.xml");
    }

    private void addPackageSettingLPw(PackageSetting packagesetting, String s, SharedUserSetting sharedusersetting) {
        mPackages.put(s, packagesetting);
        if(sharedusersetting == null) goto _L2; else goto _L1
_L1:
        if(packagesetting.sharedUser == null || packagesetting.sharedUser == sharedusersetting) goto _L4; else goto _L3
_L3:
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Package ").append(((PackageSettingBase) (packagesetting)).name).append(" was user ").append(packagesetting.sharedUser).append(" but is now ").append(sharedusersetting).append("; I am not changing its files so it will probably fail!").toString());
        packagesetting.sharedUser.packages.remove(packagesetting);
_L6:
        sharedusersetting.packages.add(packagesetting);
        packagesetting.sharedUser = sharedusersetting;
        packagesetting.appId = sharedusersetting.userId;
_L2:
        return;
_L4:
        if(packagesetting.appId != sharedusersetting.userId)
            PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Package ").append(((PackageSettingBase) (packagesetting)).name).append(" was user id ").append(packagesetting.appId).append(" but is now user ").append(sharedusersetting).append(" with id ").append(sharedusersetting.userId).append("; I am not changing its files so it will probably fail!").toString());
        if(true) goto _L6; else goto _L5
_L5:
    }

    private boolean addUserIdLPw(int i, Object obj, Object obj1) {
        boolean flag = false;
        int k;
        if(i <= 19999)
            if(i >= 10000) {
label0:
                {
                    int j = mUserIds.size();
                    for(k = i - 10000; k >= j; j++)
                        mUserIds.add(null);

                    if(mUserIds.get(k) == null)
                        break label0;
                    PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Adding duplicate user id: ").append(i).append(" name=").append(obj1).toString());
                }
            } else {
label1:
                {
                    if(mOtherUserIds.get(i) == null)
                        break label1;
                    PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Adding duplicate shared id: ").append(i).append(" name=").append(obj1).toString());
                }
            }
_L2:
        return flag;
        mUserIds.set(k, obj);
        do {
            flag = true;
            if(true)
                break MISSING_BLOCK_LABEL_10;
            mOtherUserIds.put(i, obj);
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private List getAllUsers() {
        long l = Binder.clearCallingIdentity();
        IPackageManager ipackagemanager = AppGlobals.getPackageManager();
        List list1 = ipackagemanager.getUsers();
        List list;
        list = list1;
        Binder.restoreCallingIdentity(l);
_L2:
        return list;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        NullPointerException nullpointerexception;
        nullpointerexception;
_L3:
        Binder.restoreCallingIdentity(l);
        list = null;
        if(true) goto _L2; else goto _L1
_L1:
        NullPointerException nullpointerexception1;
        nullpointerexception1;
          goto _L3
        RemoteException remoteexception;
        remoteexception;
          goto _L3
    }

    private PackageSetting getPackageLPw(String s, PackageSetting packagesetting, String s1, SharedUserSetting sharedusersetting, File file, File file1, String s2, 
            int i, int j, boolean flag, boolean flag1) {
        PackageSetting packagesetting1;
        PackageSetting packagesetting2;
        packagesetting1 = (PackageSetting)mPackages.get(s);
        if(packagesetting1 != null) {
            if(!((PackageSettingBase) (packagesetting1)).codePath.equals(file))
                if((1 & ((GrantedPermissions) (packagesetting1)).pkgFlags) != 0) {
                    Slog.w("PackageManager", (new StringBuilder()).append("Trying to update system app code path from ").append(((PackageSettingBase) (packagesetting1)).codePathString).append(" to ").append(file.toString()).toString());
                } else {
                    Slog.i("PackageManager", (new StringBuilder()).append("Package ").append(s).append(" codePath changed from ").append(((PackageSettingBase) (packagesetting1)).codePath).append(" to ").append(file).append("; Retaining data and using new").toString());
                    packagesetting1.nativeLibraryPathString = s2;
                }
            if(packagesetting1.sharedUser != sharedusersetting) {
                StringBuilder stringbuilder = (new StringBuilder()).append("Package ").append(s).append(" shared user changed from ");
                String s4;
                StringBuilder stringbuilder1;
                String s5;
                if(packagesetting1.sharedUser != null)
                    s4 = packagesetting1.sharedUser.name;
                else
                    s4 = "<nothing>";
                stringbuilder1 = stringbuilder.append(s4).append(" to ");
                if(sharedusersetting != null)
                    s5 = sharedusersetting.name;
                else
                    s5 = "<nothing>";
                PackageManagerService.reportSettingsProblem(5, stringbuilder1.append(s5).append("; replacing with new").toString());
                packagesetting1 = null;
            } else
            if((j & 1) != 0)
                packagesetting1.pkgFlags = 1 | ((GrantedPermissions) (packagesetting1)).pkgFlags;
        }
        if(packagesetting1 != null) goto _L2; else goto _L1
_L1:
        if(flag) goto _L4; else goto _L3
_L3:
        packagesetting2 = null;
_L6:
        return packagesetting2;
_L4:
        if(packagesetting != null) {
            packagesetting1 = new PackageSetting(((PackageSettingBase) (packagesetting)).name, s, file, file1, s2, i, j);
            PackageSignatures packagesignatures = ((PackageSettingBase) (packagesetting1)).signatures;
            packagesetting1.copyFrom(packagesetting);
            packagesetting1.signatures = packagesignatures;
            packagesetting1.sharedUser = packagesetting.sharedUser;
            packagesetting1.appId = packagesetting.appId;
            packagesetting1.origPackage = packagesetting;
            HashMap hashmap = mRenamedPackages;
            String s3 = ((PackageSettingBase) (packagesetting)).name;
            hashmap.put(s, s3);
            s = ((PackageSettingBase) (packagesetting)).name;
            packagesetting1.setTimeStamp(file.lastModified());
        } else {
            packagesetting1 = new PackageSetting(s, s1, file, file1, s2, i, j);
            packagesetting1.setTimeStamp(file.lastModified());
            packagesetting1.sharedUser = sharedusersetting;
            if((j & 1) == 0) {
                List list1 = getAllUsers();
                if(list1 != null) {
                    UserInfo userinfo;
                    for(Iterator iterator1 = list1.iterator(); iterator1.hasNext(); writePackageRestrictionsLPr(userinfo.id)) {
                        userinfo = (UserInfo)iterator1.next();
                        packagesetting1.setStopped(true, userinfo.id);
                        packagesetting1.setNotLaunched(true, userinfo.id);
                    }

                }
            }
            if(sharedusersetting != null) {
                packagesetting1.appId = sharedusersetting.userId;
            } else {
                PackageSetting packagesetting3 = (PackageSetting)mDisabledSysPackages.get(s);
                if(packagesetting3 != null) {
                    if(((PackageSettingBase) (packagesetting3)).signatures.mSignatures != null)
                        ((PackageSettingBase) (packagesetting1)).signatures.mSignatures = (android.content.pm.Signature[])((PackageSettingBase) (packagesetting3)).signatures.mSignatures.clone();
                    packagesetting1.appId = packagesetting3.appId;
                    packagesetting1.grantedPermissions = new HashSet(((GrantedPermissions) (packagesetting3)).grantedPermissions);
                    List list = getAllUsers();
                    if(list != null) {
                        int k;
                        for(Iterator iterator = list.iterator(); iterator.hasNext(); packagesetting1.setEnabledComponents(new HashSet(packagesetting3.getEnabledComponents(k)), k)) {
                            k = ((UserInfo)iterator.next()).id;
                            packagesetting1.setDisabledComponents(new HashSet(packagesetting3.getDisabledComponents(k)), k);
                        }

                    }
                    addUserIdLPw(packagesetting1.appId, packagesetting1, s);
                } else {
                    packagesetting1.appId = newUserIdLPw(packagesetting1);
                }
            }
        }
        if(packagesetting1.appId < 0) {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Package ").append(s).append(" could not be assigned a valid uid").toString());
            packagesetting2 = null;
            continue; /* Loop/switch isn't completed */
        }
        if(flag1)
            addPackageSettingLPw(packagesetting1, s, sharedusersetting);
_L2:
        packagesetting2 = packagesetting1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private File getUserPackagesStateBackupFile(int i) {
        return new File(mSystemDir, (new StringBuilder()).append("users/").append(i).append("/package-restrictions-backup.xml").toString());
    }

    private File getUserPackagesStateFile(int i) {
        return new File(mSystemDir, (new StringBuilder()).append("users/").append(i).append("/package-restrictions.xml").toString());
    }

    private int newUserIdLPw(Object obj) {
        int i;
        int j;
        i = mUserIds.size();
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_52;
        if(mUserIds.get(j) != null) goto _L2; else goto _L1
_L1:
        int k;
        mUserIds.set(j, obj);
        k = j + 10000;
_L4:
        return k;
_L2:
        j++;
          goto _L3
        if(i > 9999) {
            k = -1;
        } else {
            mUserIds.add(obj);
            k = i + 10000;
        }
          goto _L4
    }

    static final void printFlags(PrintWriter printwriter, int i, Object aobj[]) {
        printwriter.print("[ ");
        for(int j = 0; j < aobj.length; j += 2)
            if((i & ((Integer)aobj[j]).intValue()) != 0) {
                printwriter.print(aobj[j + 1]);
                printwriter.print(" ");
            }

        printwriter.print("]");
    }

    private HashSet readComponentsLPr(XmlPullParser xmlpullparser) throws IOException, XmlPullParserException {
        HashSet hashset = new HashSet();
        int i = xmlpullparser.getDepth();
        do {
            int j = xmlpullparser.next();
            if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                break;
            if(j != 3 && j != 4 && xmlpullparser.getName().equals("item")) {
                String s = xmlpullparser.getAttributeValue(null, "name");
                if(s != null)
                    hashset.add(s);
            }
        } while(true);
        return hashset;
    }

    private void readDefaultPreferredAppsLPw() {
        File file = new File(Environment.getRootDirectory(), "etc/preferred-apps");
        if(file.exists() && file.isDirectory()) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(file.canRead())
            break; /* Loop/switch isn't completed */
        Slog.w("PackageSettings", (new StringBuilder()).append("Directory ").append(file).append(" cannot be read").toString());
        if(true) goto _L1; else goto _L3
_L3:
        File afile[];
        int i;
        int j;
        afile = file.listFiles();
        i = afile.length;
        j = 0;
_L5:
        if(j >= i) goto _L1; else goto _L4
_L4:
        File file1;
        file1 = afile[j];
        if(!file1.getPath().endsWith(".xml")) {
            Slog.i("PackageSettings", (new StringBuilder()).append("Non-xml file ").append(file1).append(" in ").append(file).append(" directory, ignoring").toString());
        } else {
label0:
            {
                if(file1.canRead())
                    break label0;
                Slog.w("PackageSettings", (new StringBuilder()).append("Preferred apps file ").append(file1).append(" cannot be read").toString());
            }
        }
_L6:
        j++;
          goto _L5
          goto _L1
        FileInputStream fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(file1);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
        int k;
        do
            k = xmlpullparser.next();
        while(k != 2 && k != 1);
        if(k == 2)
            break MISSING_BLOCK_LABEL_308;
        Slog.w("PackageSettings", (new StringBuilder()).append("Preferred apps file ").append(file1).append(" does not have start tag").toString());
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception1) { }
          goto _L6
        if("preferred-activities".equals(xmlpullparser.getName()))
            break MISSING_BLOCK_LABEL_370;
        Slog.w("PackageSettings", (new StringBuilder()).append("Preferred apps file ").append(file1).append(" does not start with 'preferred-activities'").toString());
        if(fileinputstream1 == null) goto _L6; else goto _L7
_L7:
        fileinputstream1.close();
          goto _L6
        readPreferredActivitiesLPw(xmlpullparser);
        if(fileinputstream1 == null) goto _L6; else goto _L8
_L8:
        fileinputstream1.close();
          goto _L6
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
_L14:
        Slog.w("PackageSettings", (new StringBuilder()).append("Error reading apps file ").append(file1).toString(), xmlpullparserexception);
        if(fileinputstream == null) goto _L6; else goto _L9
_L9:
        fileinputstream.close();
          goto _L6
        IOException ioexception2;
        ioexception2;
_L13:
        Slog.w("PackageSettings", (new StringBuilder()).append("Error reading apps file ").append(file1).toString(), ioexception2);
        if(fileinputstream == null) goto _L6; else goto _L10
_L10:
        fileinputstream.close();
          goto _L6
        Exception exception;
        exception;
_L12:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L12; else goto _L11
_L11:
        ioexception2;
        fileinputstream = fileinputstream1;
          goto _L13
        xmlpullparserexception;
        fileinputstream = fileinputstream1;
          goto _L14
    }

    private void readDisabledComponentsLPw(PackageSettingBase packagesettingbase, XmlPullParser xmlpullparser, int i) throws IOException, XmlPullParserException {
        int j = xmlpullparser.getDepth();
        do {
            int k = xmlpullparser.next();
            if(k == 1 || k == 3 && xmlpullparser.getDepth() <= j)
                break;
            if(k != 3 && k != 4) {
                if(xmlpullparser.getName().equals("item")) {
                    String s = xmlpullparser.getAttributeValue(null, "name");
                    if(s != null)
                        packagesettingbase.addDisabledComponent(s.intern(), i);
                    else
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <disabled-components> has no name at ").append(xmlpullparser.getPositionDescription()).toString());
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <disabled-components>: ").append(xmlpullparser.getName()).toString());
                }
                XmlUtils.skipCurrentTag(xmlpullparser);
            }
        } while(true);
    }

    private void readDisabledSysPackageLPw(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        String s5;
        int i;
        s = xmlpullparser.getAttributeValue(null, "name");
        s1 = xmlpullparser.getAttributeValue(null, "realName");
        s2 = xmlpullparser.getAttributeValue(null, "codePath");
        s3 = xmlpullparser.getAttributeValue(null, "resourcePath");
        s4 = xmlpullparser.getAttributeValue(null, "nativeLibraryPath");
        if(s3 == null)
            s3 = s2;
        s5 = xmlpullparser.getAttributeValue(null, "version");
        i = 0;
        if(s5 == null)
            break MISSING_BLOCK_LABEL_97;
        int k1 = Integer.parseInt(s5);
        i = k1;
_L1:
        int j = false | true;
        PackageSetting packagesetting = new PackageSetting(s, s1, new File(s2), new File(s3), s4, i, j);
        String s6 = xmlpullparser.getAttributeValue(null, "ft");
        String s8;
        String s9;
        String s10;
        int k;
        NumberFormatException numberformatexception4;
        if(s6 != null) {
            try {
                packagesetting.setTimeStamp(Long.parseLong(s6, 16));
            }
            catch(NumberFormatException numberformatexception3) { }
        } else {
            String s7 = xmlpullparser.getAttributeValue(null, "ts");
            if(s7 != null)
                try {
                    packagesetting.setTimeStamp(Long.parseLong(s7));
                }
                catch(NumberFormatException numberformatexception) { }
        }
        s8 = xmlpullparser.getAttributeValue(null, "it");
        if(s8 != null)
            try {
                packagesetting.firstInstallTime = Long.parseLong(s8, 16);
            }
            catch(NumberFormatException numberformatexception2) { }
        s9 = xmlpullparser.getAttributeValue(null, "ut");
        if(s9 != null)
            try {
                packagesetting.lastUpdateTime = Long.parseLong(s9, 16);
            }
            catch(NumberFormatException numberformatexception1) { }
        s10 = xmlpullparser.getAttributeValue(null, "userId");
        if(s10 != null)
            k = Integer.parseInt(s10);
        else
            k = 0;
        packagesetting.appId = k;
        if(packagesetting.appId <= 0) {
            String s11 = xmlpullparser.getAttributeValue(null, "sharedUserId");
            int l;
            int i1;
            int j1;
            if(s11 != null)
                j1 = Integer.parseInt(s11);
            else
                j1 = 0;
            packagesetting.appId = j1;
        }
        l = xmlpullparser.getDepth();
        do {
            i1 = xmlpullparser.next();
            if(i1 == 1 || i1 == 3 && xmlpullparser.getDepth() <= l)
                break;
            if(i1 != 3 && i1 != 4)
                if(xmlpullparser.getName().equals("perms")) {
                    readGrantedPermissionsLPw(xmlpullparser, ((GrantedPermissions) (packagesetting)).grantedPermissions);
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <updated-package>: ").append(xmlpullparser.getName()).toString());
                    XmlUtils.skipCurrentTag(xmlpullparser);
                }
        } while(true);
        mDisabledSysPackages.put(s, packagesetting);
        return;
        numberformatexception4;
          goto _L1
    }

    private void readEnabledComponentsLPw(PackageSettingBase packagesettingbase, XmlPullParser xmlpullparser, int i) throws IOException, XmlPullParserException {
        int j = xmlpullparser.getDepth();
        do {
            int k = xmlpullparser.next();
            if(k == 1 || k == 3 && xmlpullparser.getDepth() <= j)
                break;
            if(k != 3 && k != 4) {
                if(xmlpullparser.getName().equals("item")) {
                    String s = xmlpullparser.getAttributeValue(null, "name");
                    if(s != null)
                        packagesettingbase.addEnabledComponent(s.intern(), i);
                    else
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <enabled-components> has no name at ").append(xmlpullparser.getPositionDescription()).toString());
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <enabled-components>: ").append(xmlpullparser.getName()).toString());
                }
                XmlUtils.skipCurrentTag(xmlpullparser);
            }
        } while(true);
    }

    private void readGrantedPermissionsLPw(XmlPullParser xmlpullparser, HashSet hashset) throws IOException, XmlPullParserException {
        int i = xmlpullparser.getDepth();
        do {
            int j = xmlpullparser.next();
            if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                break;
            if(j != 3 && j != 4) {
                if(xmlpullparser.getName().equals("item")) {
                    String s = xmlpullparser.getAttributeValue(null, "name");
                    if(s != null)
                        hashset.add(s.intern());
                    else
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <perms> has no name at ").append(xmlpullparser.getPositionDescription()).toString());
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <perms>: ").append(xmlpullparser.getName()).toString());
                }
                XmlUtils.skipCurrentTag(xmlpullparser);
            }
        } while(true);
    }

    private int readInt(XmlPullParser xmlpullparser, String s, String s1, int i) {
        String s2 = xmlpullparser.getAttributeValue(s, s1);
        if(s2 != null) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        int j = Integer.parseInt(s2);
        i = j;
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: attribute ").append(s1).append(" has bad integer value ").append(s2).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void readPackageLPw(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        int i;
        long l;
        long l1;
        long l2;
        int j;
        s = null;
        s1 = null;
        s2 = null;
        s3 = null;
        s4 = null;
        i = 0;
        l = 0L;
        l1 = 0L;
        l2 = 0L;
        j = 0;
        String s8;
        String s9;
        String s10;
        String s11;
        String s12;
        s = xmlpullparser.getAttributeValue(null, "name");
        s8 = xmlpullparser.getAttributeValue(null, "realName");
        s1 = xmlpullparser.getAttributeValue(null, "userId");
        s4 = xmlpullparser.getAttributeValue(null, "uidError");
        s9 = xmlpullparser.getAttributeValue(null, "sharedUserId");
        s10 = xmlpullparser.getAttributeValue(null, "codePath");
        s11 = xmlpullparser.getAttributeValue(null, "resourcePath");
        s2 = xmlpullparser.getAttributeValue(null, "nativeLibraryPath");
        s12 = xmlpullparser.getAttributeValue(null, "version");
        if(s12 == null)
            break MISSING_BLOCK_LABEL_149;
        int j2 = Integer.parseInt(s12);
        j = j2;
_L21:
        String s13;
        s3 = xmlpullparser.getAttributeValue(null, "installer");
        s13 = xmlpullparser.getAttributeValue(null, "flags");
        if(s13 == null) goto _L2; else goto _L1
_L1:
        int i2 = Integer.parseInt(s13);
        i = i2;
_L22:
        String s15 = xmlpullparser.getAttributeValue(null, "ft");
        if(s15 == null) goto _L4; else goto _L3
_L3:
        long l6 = Long.parseLong(s15, 16);
        l = l6;
_L14:
        String s17 = xmlpullparser.getAttributeValue(null, "it");
        if(s17 == null)
            break MISSING_BLOCK_LABEL_249;
        long l4 = Long.parseLong(s17, 16);
        l1 = l4;
_L23:
        String s18 = xmlpullparser.getAttributeValue(null, "ut");
        if(s18 == null)
            break MISSING_BLOCK_LABEL_279;
        long l3 = Long.parseLong(s18, 16);
        l2 = l3;
_L24:
        if(s1 == null) goto _L6; else goto _L5
_L5:
        int j1 = Integer.parseInt(s1);
          goto _L7
_L27:
        if(s8 != null)
            s8 = s8.intern();
        if(s != null) goto _L9; else goto _L8
_L8:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <package> has no name at ").append(xmlpullparser.getPositionDescription()).toString());
        Object obj;
        obj = null;
        break MISSING_BLOCK_LABEL_340;
_L2:
        s14 = xmlpullparser.getAttributeValue(null, "system");
        if(s14 == null)
            break MISSING_BLOCK_LABEL_1334;
        if(!"true".equalsIgnoreCase(s14)) goto _L11; else goto _L10
_L10:
        boolean flag = true;
          goto _L12
_L4:
        s16 = xmlpullparser.getAttributeValue(null, "ts");
        if(s16 == null) goto _L14; else goto _L13
_L13:
        l5 = Long.parseLong(s16);
        l = l5;
          goto _L14
_L6:
        j1 = 0;
          goto _L7
_L9:
        if(s10 != null)
            break MISSING_BLOCK_LABEL_623;
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <package> has no codePath at ").append(xmlpullparser.getPositionDescription()).toString());
        obj = null;
          goto _L15
        if(j1 <= 0) goto _L17; else goto _L16
_L16:
        packagesetting = addPackageLPw(s.intern(), s8, new File(s10), new File(s11), s2, j1, j, i);
        obj = packagesetting;
        if(obj != null) goto _L19; else goto _L18
_L18:
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Failure adding uid ").append(j1).append(" while parsing settings at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L15
        numberformatexception3;
_L25:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: package ").append(s).append(" has bad userId ").append(s1).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L15
_L19:
        ((PackageSetting) (obj)).setTimeStamp(l);
        obj.firstInstallTime = l1;
        obj.lastUpdateTime = l2;
          goto _L15
_L17:
        if(s9 == null)
            break MISSING_BLOCK_LABEL_959;
        if(s9 == null)
            break MISSING_BLOCK_LABEL_897;
        k1 = Integer.parseInt(s9);
_L20:
        if(k1 <= 0)
            break MISSING_BLOCK_LABEL_903;
        obj = new PendingPackage(s.intern(), s8, new File(s10), new File(s11), s2, k1, j, i);
        ((PackageSettingBase) (obj)).setTimeStamp(l);
        obj.firstInstallTime = l1;
        obj.lastUpdateTime = l2;
        mPendingPackages.add((PendingPackage)obj);
          goto _L15
        k1 = 0;
          goto _L20
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: package ").append(s).append(" has bad sharedId ").append(s9).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
        obj = null;
          goto _L15
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: package ").append(s).append(" has bad userId ").append(s1).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
        obj = null;
_L15:
        if(obj != null) {
            obj.uidError = "true".equals(s4);
            obj.installerPackageName = s3;
            obj.nativeLibraryPathString = s2;
            String s5 = xmlpullparser.getAttributeValue(null, "enabled");
            String s6;
            int k;
            if(s5 != null)
                try {
                    ((PackageSettingBase) (obj)).setEnabled(Integer.parseInt(s5), 0);
                }
                catch(NumberFormatException numberformatexception1) {
                    if(s5.equalsIgnoreCase("true"))
                        ((PackageSettingBase) (obj)).setEnabled(1, 0);
                    else
                    if(s5.equalsIgnoreCase("false"))
                        ((PackageSettingBase) (obj)).setEnabled(2, 0);
                    else
                    if(s5.equalsIgnoreCase("default"))
                        ((PackageSettingBase) (obj)).setEnabled(0, 0);
                    else
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: package ").append(s).append(" has bad enabled value: ").append(s1).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
                }
            else
                ((PackageSettingBase) (obj)).setEnabled(0, 0);
            s6 = xmlpullparser.getAttributeValue(null, "installStatus");
            if(s6 != null)
                if(s6.equalsIgnoreCase("false"))
                    obj.installStatus = 0;
                else
                    obj.installStatus = 1;
            k = xmlpullparser.getDepth();
            do {
                int i1 = xmlpullparser.next();
                if(i1 == 1 || i1 == 3 && xmlpullparser.getDepth() <= k)
                    break;
                if(i1 != 3 && i1 != 4) {
                    String s7 = xmlpullparser.getName();
                    String s14;
                    String s16;
                    int k1;
                    NumberFormatException numberformatexception3;
                    PackageSetting packagesetting;
                    long l5;
                    if(s7.equals("disabled-components"))
                        readDisabledComponentsLPw(((PackageSettingBase) (obj)), xmlpullparser, 0);
                    else
                    if(s7.equals("enabled-components"))
                        readEnabledComponentsLPw(((PackageSettingBase) (obj)), xmlpullparser, 0);
                    else
                    if(s7.equals("sigs"))
                        ((PackageSettingBase) (obj)).signatures.readXml(xmlpullparser, mPastSignatures);
                    else
                    if(s7.equals("perms")) {
                        readGrantedPermissionsLPw(xmlpullparser, ((GrantedPermissions) (obj)).grantedPermissions);
                        obj.permissionsFixed = true;
                    } else {
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <package>: ").append(xmlpullparser.getName()).toString());
                        XmlUtils.skipCurrentTag(xmlpullparser);
                    }
                }
            } while(true);
        } else {
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
        return;
        NumberFormatException numberformatexception8;
        numberformatexception8;
          goto _L21
        NumberFormatException numberformatexception7;
        numberformatexception7;
          goto _L22
        NumberFormatException numberformatexception6;
        numberformatexception6;
          goto _L14
        NumberFormatException numberformatexception2;
        numberformatexception2;
          goto _L14
        NumberFormatException numberformatexception5;
        numberformatexception5;
          goto _L23
        NumberFormatException numberformatexception4;
        numberformatexception4;
          goto _L24
        NumberFormatException numberformatexception;
        numberformatexception;
        obj = null;
          goto _L25
_L7:
        if(s11 != null) goto _L27; else goto _L26
_L26:
        s11 = s10;
          goto _L27
_L12:
        i = false | flag;
          goto _L22
_L11:
        flag = false;
          goto _L12
        i = false | true;
          goto _L22
    }

    private void readPermissionsLPw(HashMap hashmap, XmlPullParser xmlpullparser) throws IOException, XmlPullParserException {
        int i = xmlpullparser.getDepth();
        do {
            int j = xmlpullparser.next();
            if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                break;
            if(j != 3 && j != 4) {
                if(xmlpullparser.getName().equals("item")) {
                    String s = xmlpullparser.getAttributeValue(null, "name");
                    String s1 = xmlpullparser.getAttributeValue(null, "package");
                    String s2 = xmlpullparser.getAttributeValue(null, "type");
                    if(s != null && s1 != null) {
                        boolean flag = "dynamic".equals(s2);
                        byte byte0;
                        BasePermission basepermission;
                        if(flag)
                            byte0 = 2;
                        else
                            byte0 = 0;
                        basepermission = new BasePermission(s, s1, byte0);
                        basepermission.protectionLevel = readInt(xmlpullparser, null, "protection", 0);
                        basepermission.protectionLevel = PermissionInfo.fixProtectionLevel(basepermission.protectionLevel);
                        if(flag) {
                            PermissionInfo permissioninfo = new PermissionInfo();
                            permissioninfo.packageName = s1.intern();
                            permissioninfo.name = s.intern();
                            permissioninfo.icon = readInt(xmlpullparser, null, "icon", 0);
                            permissioninfo.nonLocalizedLabel = xmlpullparser.getAttributeValue(null, "label");
                            permissioninfo.protectionLevel = basepermission.protectionLevel;
                            basepermission.pendingInfo = permissioninfo;
                        }
                        hashmap.put(basepermission.name, basepermission);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: permissions has no name at ").append(xmlpullparser.getPositionDescription()).toString());
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element reading permissions: ").append(xmlpullparser.getName()).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
                }
                XmlUtils.skipCurrentTag(xmlpullparser);
            }
        } while(true);
    }

    private void readPreferredActivitiesLPw(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        int i = xmlpullparser.getDepth();
        do {
            int j = xmlpullparser.next();
            if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                break;
            if(j != 3 && j != 4)
                if(xmlpullparser.getName().equals("item")) {
                    PreferredActivity preferredactivity = new PreferredActivity(xmlpullparser);
                    if(preferredactivity.mPref.getParseError() == null)
                        mPreferredActivities.addFilter(preferredactivity);
                    else
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <preferred-activity> ").append(preferredactivity.mPref.getParseError()).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
                } else {
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <preferred-activities>: ").append(xmlpullparser.getName()).toString());
                    XmlUtils.skipCurrentTag(xmlpullparser);
                }
        } while(true);
    }

    private void readSharedUserLPw(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        String s;
        String s1;
        int i;
        SharedUserSetting sharedusersetting;
        s = null;
        s1 = null;
        i = 0;
        sharedusersetting = null;
        s = xmlpullparser.getAttributeValue(null, "name");
        s1 = xmlpullparser.getAttributeValue(null, "userId");
        if(s1 == null) goto _L2; else goto _L1
_L1:
        int l = Integer.parseInt(s1);
_L7:
        if("true".equals(xmlpullparser.getAttributeValue(null, "system")))
            i = false | true;
        if(s != null) goto _L4; else goto _L3
_L3:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <shared-user> has no name at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L5
_L2:
        l = 0;
        continue; /* Loop/switch isn't completed */
_L4:
label0:
        {
            if(l != 0)
                break label0;
            try {
                PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: shared-user ").append(s).append(" has bad userId ").append(s1).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
            }
            catch(NumberFormatException numberformatexception) {
                PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: package ").append(s).append(" has bad userId ").append(s1).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
            }
        }
          goto _L5
        sharedusersetting = addSharedUserLPw(s.intern(), l, i);
        if(sharedusersetting == null)
            PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Occurred while parsing settings at ").append(xmlpullparser.getPositionDescription()).toString());
_L5:
        if(sharedusersetting != null) {
            int j = xmlpullparser.getDepth();
            do {
                int k = xmlpullparser.next();
                if(k == 1 || k == 3 && xmlpullparser.getDepth() <= j)
                    break;
                if(k != 3 && k != 4) {
                    String s2 = xmlpullparser.getName();
                    if(s2.equals("sigs"))
                        sharedusersetting.signatures.readXml(xmlpullparser, mPastSignatures);
                    else
                    if(s2.equals("perms")) {
                        readGrantedPermissionsLPw(xmlpullparser, ((GrantedPermissions) (sharedusersetting)).grantedPermissions);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <shared-user>: ").append(xmlpullparser.getName()).toString());
                        XmlUtils.skipCurrentTag(xmlpullparser);
                    }
                }
            } while(true);
        } else {
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
        return;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void removeUserIdLPw(int i) {
        if(i >= 10000) {
            int j = mUserIds.size();
            int k = i - 10000;
            if(k < j)
                mUserIds.set(k, null);
        } else {
            mOtherUserIds.remove(i);
        }
    }

    private void replacePackageLPw(String s, PackageSetting packagesetting) {
        PackageSetting packagesetting1 = (PackageSetting)mPackages.get(s);
        if(packagesetting1 != null)
            if(packagesetting1.sharedUser != null) {
                packagesetting1.sharedUser.packages.remove(packagesetting1);
                packagesetting1.sharedUser.packages.add(packagesetting);
            } else {
                replaceUserIdLPw(packagesetting1.appId, packagesetting);
            }
        mPackages.put(s, packagesetting);
    }

    private void replaceUserIdLPw(int i, Object obj) {
        if(i >= 10000) {
            int j = mUserIds.size();
            int k = i - 10000;
            if(k < j)
                mUserIds.set(k, obj);
        } else {
            mOtherUserIds.put(i, obj);
        }
    }

    PackageSetting addPackageLPw(String s, String s1, File file, File file1, String s2, int i, int j, 
            int k) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        PackageSetting packagesetting2;
        if(packagesetting != null) {
            if(packagesetting.appId == i) {
                packagesetting2 = packagesetting;
            } else {
                PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Adding duplicate package, keeping first: ").append(s).toString());
                packagesetting2 = null;
            }
        } else {
            PackageSetting packagesetting1 = new PackageSetting(s, s1, file, file1, s2, j, k);
            packagesetting1.appId = i;
            if(addUserIdLPw(i, packagesetting1, s)) {
                mPackages.put(s, packagesetting1);
                packagesetting2 = packagesetting1;
            } else {
                packagesetting2 = null;
            }
        }
        return packagesetting2;
    }

    SharedUserSetting addSharedUserLPw(String s, int i, int j) {
        SharedUserSetting sharedusersetting;
        SharedUserSetting sharedusersetting1;
        sharedusersetting = null;
        sharedusersetting1 = (SharedUserSetting)mSharedUsers.get(s);
        if(sharedusersetting1 == null) goto _L2; else goto _L1
_L1:
        if(sharedusersetting1.userId == i)
            sharedusersetting = sharedusersetting1;
        else
            PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Adding duplicate shared user, keeping first: ").append(s).toString());
_L4:
        return sharedusersetting;
_L2:
        SharedUserSetting sharedusersetting2 = new SharedUserSetting(s, j);
        sharedusersetting2.userId = i;
        if(addUserIdLPw(i, sharedusersetting2, s)) {
            mSharedUsers.put(s, sharedusersetting2);
            sharedusersetting = sharedusersetting2;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    boolean disableSystemPackageLPw(String s) {
        boolean flag;
        PackageSetting packagesetting;
        flag = false;
        packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting != null) goto _L2; else goto _L1
_L1:
        Log.w("PackageManager", (new StringBuilder()).append("Package:").append(s).append(" is not an installed package").toString());
_L4:
        return flag;
_L2:
        if((PackageSetting)mDisabledSysPackages.get(s) == null) {
            if(packagesetting.pkg != null && packagesetting.pkg.applicationInfo != null) {
                ApplicationInfo applicationinfo = packagesetting.pkg.applicationInfo;
                applicationinfo.flags = 0x80 | applicationinfo.flags;
            }
            mDisabledSysPackages.put(s, packagesetting);
            replacePackageLPw(s, new PackageSetting(packagesetting));
            flag = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    void dumpPackagesLPr(PrintWriter printwriter, String s, PackageManagerService.DumpState dumpstate) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        boolean flag = false;
        List list = getAllUsers();
        Iterator iterator = mPackages.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            PackageSetting packagesetting1 = (PackageSetting)iterator.next();
            if(s == null || s.equals(((PackageSettingBase) (packagesetting1)).realName) || s.equals(((PackageSettingBase) (packagesetting1)).name)) {
                if(s != null)
                    dumpstate.setSharedUser(packagesetting1.sharedUser);
                if(!flag) {
                    if(dumpstate.onTitlePrinted())
                        printwriter.println(" ");
                    printwriter.println("Packages:");
                    flag = true;
                }
                printwriter.print("  Package [");
                String s2;
                Iterator iterator3;
                if(((PackageSettingBase) (packagesetting1)).realName != null)
                    s2 = ((PackageSettingBase) (packagesetting1)).realName;
                else
                    s2 = ((PackageSettingBase) (packagesetting1)).name;
                printwriter.print(s2);
                printwriter.print("] (");
                printwriter.print(Integer.toHexString(System.identityHashCode(packagesetting1)));
                printwriter.println("):");
                if(((PackageSettingBase) (packagesetting1)).realName != null) {
                    printwriter.print("    compat name=");
                    printwriter.println(((PackageSettingBase) (packagesetting1)).name);
                }
                printwriter.print("    userId=");
                printwriter.print(packagesetting1.appId);
                printwriter.print(" gids=");
                printwriter.println(PackageManagerService.arrayToString(((GrantedPermissions) (packagesetting1)).gids));
                printwriter.print("    sharedUser=");
                printwriter.println(packagesetting1.sharedUser);
                printwriter.print("    pkg=");
                printwriter.println(packagesetting1.pkg);
                printwriter.print("    codePath=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).codePathString);
                printwriter.print("    resourcePath=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).resourcePathString);
                printwriter.print("    nativeLibraryPath=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).nativeLibraryPathString);
                printwriter.print("    versionCode=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).versionCode);
                if(packagesetting1.pkg != null) {
                    printwriter.print("    applicationInfo=");
                    printwriter.println(packagesetting1.pkg.applicationInfo.toString());
                    printwriter.print("    flags=");
                    printFlags(printwriter, packagesetting1.pkg.applicationInfo.flags, FLAG_DUMP_SPEC);
                    printwriter.println();
                    printwriter.print("    versionName=");
                    printwriter.println(packagesetting1.pkg.mVersionName);
                    printwriter.print("    dataDir=");
                    printwriter.println(packagesetting1.pkg.applicationInfo.dataDir);
                    printwriter.print("    targetSdk=");
                    printwriter.println(packagesetting1.pkg.applicationInfo.targetSdkVersion);
                    if(packagesetting1.pkg.mOperationPending)
                        printwriter.println("    mOperationPending=true");
                    printwriter.print("    supportsScreens=[");
                    boolean flag3 = true;
                    if((0x200 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        flag3 = false;
                        printwriter.print("small");
                    }
                    if((0x400 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        flag3 = false;
                        printwriter.print("medium");
                    }
                    if((0x800 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        flag3 = false;
                        printwriter.print("large");
                    }
                    if((0x80000 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        flag3 = false;
                        printwriter.print("xlarge");
                    }
                    if((0x1000 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        flag3 = false;
                        printwriter.print("resizeable");
                    }
                    if((0x2000 & packagesetting1.pkg.applicationInfo.flags) != 0) {
                        if(!flag3)
                            printwriter.print(", ");
                        printwriter.print("anyDensity");
                    }
                }
                printwriter.println("]");
                printwriter.print("    timeStamp=");
                date.setTime(((PackageSettingBase) (packagesetting1)).timeStamp);
                printwriter.println(simpledateformat.format(date));
                printwriter.print("    firstInstallTime=");
                date.setTime(((PackageSettingBase) (packagesetting1)).firstInstallTime);
                printwriter.println(simpledateformat.format(date));
                printwriter.print("    lastUpdateTime=");
                date.setTime(((PackageSettingBase) (packagesetting1)).lastUpdateTime);
                printwriter.println(simpledateformat.format(date));
                if(((PackageSettingBase) (packagesetting1)).installerPackageName != null) {
                    printwriter.print("    installerPackageName=");
                    printwriter.println(((PackageSettingBase) (packagesetting1)).installerPackageName);
                }
                printwriter.print("    signatures=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).signatures);
                printwriter.print("    permissionsFixed=");
                printwriter.print(((PackageSettingBase) (packagesetting1)).permissionsFixed);
                printwriter.print(" haveGids=");
                printwriter.println(((PackageSettingBase) (packagesetting1)).haveGids);
                printwriter.print("    pkgFlags=0x");
                printwriter.print(Integer.toHexString(((GrantedPermissions) (packagesetting1)).pkgFlags));
                printwriter.print(" installStatus=");
                printwriter.print(((PackageSettingBase) (packagesetting1)).installStatus);
                iterator3 = list.iterator();
                do {
                    if(!iterator3.hasNext())
                        break;
                    UserInfo userinfo = (UserInfo)iterator3.next();
                    printwriter.print(" User ");
                    printwriter.print(userinfo.id);
                    printwriter.print(": ");
                    printwriter.print(" stopped=");
                    printwriter.print(packagesetting1.getStopped(userinfo.id));
                    printwriter.print(" enabled=");
                    printwriter.println(packagesetting1.getEnabled(userinfo.id));
                    if(packagesetting1.getDisabledComponents(userinfo.id).size() > 0) {
                        printwriter.println("    disabledComponents:");
                        String s5;
                        for(Iterator iterator6 = packagesetting1.getDisabledComponents(userinfo.id).iterator(); iterator6.hasNext(); printwriter.println(s5)) {
                            s5 = (String)iterator6.next();
                            printwriter.print("      ");
                        }

                    }
                    if(packagesetting1.getEnabledComponents(userinfo.id).size() > 0) {
                        printwriter.println("    enabledComponents:");
                        Iterator iterator5 = packagesetting1.getEnabledComponents(userinfo.id).iterator();
                        while(iterator5.hasNext())  {
                            String s4 = (String)iterator5.next();
                            printwriter.print("      ");
                            printwriter.println(s4);
                        }
                    }
                } while(true);
                if(((GrantedPermissions) (packagesetting1)).grantedPermissions.size() > 0) {
                    printwriter.println("    grantedPermissions:");
                    Iterator iterator4 = ((GrantedPermissions) (packagesetting1)).grantedPermissions.iterator();
                    while(iterator4.hasNext())  {
                        String s3 = (String)iterator4.next();
                        printwriter.print("      ");
                        printwriter.println(s3);
                    }
                }
            }
        } while(true);
        boolean flag1 = false;
        if(mRenamedPackages.size() > 0) {
            Iterator iterator2 = mRenamedPackages.entrySet().iterator();
            do {
                if(!iterator2.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator2.next();
                if(s == null || s.equals(entry.getKey()) || s.equals(entry.getValue())) {
                    if(!flag1) {
                        if(dumpstate.onTitlePrinted())
                            printwriter.println(" ");
                        printwriter.println("Renamed packages:");
                        flag1 = true;
                    }
                    printwriter.print("  ");
                    printwriter.print((String)entry.getKey());
                    printwriter.print(" -> ");
                    printwriter.println((String)entry.getValue());
                }
            } while(true);
        }
        boolean flag2 = false;
        if(mDisabledSysPackages.size() > 0) {
            Iterator iterator1 = mDisabledSysPackages.values().iterator();
            do {
                if(!iterator1.hasNext())
                    break;
                PackageSetting packagesetting = (PackageSetting)iterator1.next();
                if(s == null || s.equals(((PackageSettingBase) (packagesetting)).realName) || s.equals(((PackageSettingBase) (packagesetting)).name)) {
                    if(!flag2) {
                        if(dumpstate.onTitlePrinted())
                            printwriter.println(" ");
                        printwriter.println("Hidden system packages:");
                        flag2 = true;
                    }
                    printwriter.print("  Package [");
                    String s1;
                    if(((PackageSettingBase) (packagesetting)).realName != null)
                        s1 = ((PackageSettingBase) (packagesetting)).realName;
                    else
                        s1 = ((PackageSettingBase) (packagesetting)).name;
                    printwriter.print(s1);
                    printwriter.print("] (");
                    printwriter.print(Integer.toHexString(System.identityHashCode(packagesetting)));
                    printwriter.println("):");
                    if(((PackageSettingBase) (packagesetting)).realName != null) {
                        printwriter.print("    compat name=");
                        printwriter.println(((PackageSettingBase) (packagesetting)).name);
                    }
                    if(packagesetting.pkg != null && packagesetting.pkg.applicationInfo != null) {
                        printwriter.print("    applicationInfo=");
                        printwriter.println(packagesetting.pkg.applicationInfo.toString());
                    }
                    printwriter.print("    userId=");
                    printwriter.println(packagesetting.appId);
                    printwriter.print("    sharedUser=");
                    printwriter.println(packagesetting.sharedUser);
                    printwriter.print("    codePath=");
                    printwriter.println(((PackageSettingBase) (packagesetting)).codePathString);
                    printwriter.print("    resourcePath=");
                    printwriter.println(((PackageSettingBase) (packagesetting)).resourcePathString);
                }
            } while(true);
        }
    }

    void dumpPermissionsLPr(PrintWriter printwriter, String s, PackageManagerService.DumpState dumpstate) {
        boolean flag = false;
        Iterator iterator = mPermissions.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            BasePermission basepermission = (BasePermission)iterator.next();
            if(s == null || s.equals(basepermission.sourcePackage)) {
                if(!flag) {
                    if(dumpstate.onTitlePrinted())
                        printwriter.println(" ");
                    printwriter.println("Permissions:");
                    flag = true;
                }
                printwriter.print("  Permission [");
                printwriter.print(basepermission.name);
                printwriter.print("] (");
                printwriter.print(Integer.toHexString(System.identityHashCode(basepermission)));
                printwriter.println("):");
                printwriter.print("    sourcePackage=");
                printwriter.println(basepermission.sourcePackage);
                printwriter.print("    uid=");
                printwriter.print(basepermission.uid);
                printwriter.print(" gids=");
                printwriter.print(PackageManagerService.arrayToString(basepermission.gids));
                printwriter.print(" type=");
                printwriter.print(basepermission.type);
                printwriter.print(" prot=");
                printwriter.println(PermissionInfo.protectionToString(basepermission.protectionLevel));
                if(basepermission.packageSetting != null) {
                    printwriter.print("    packageSetting=");
                    printwriter.println(basepermission.packageSetting);
                }
                if(basepermission.perm != null) {
                    printwriter.print("    perm=");
                    printwriter.println(basepermission.perm);
                }
                if("android.permission.READ_EXTERNAL_STORAGE".equals(basepermission.name)) {
                    printwriter.print("    enforced=");
                    printwriter.println(mReadExternalStorageEnforced);
                }
            }
        } while(true);
    }

    void dumpReadMessagesLPr(PrintWriter printwriter, PackageManagerService.DumpState dumpstate) {
        printwriter.println("Settings parse messages:");
        printwriter.print(mReadMessages.toString());
    }

    void dumpSharedUsersLPr(PrintWriter printwriter, String s, PackageManagerService.DumpState dumpstate) {
        boolean flag = false;
        Iterator iterator = mSharedUsers.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            SharedUserSetting sharedusersetting = (SharedUserSetting)iterator.next();
            if(s == null || sharedusersetting == dumpstate.getSharedUser()) {
                if(!flag) {
                    if(dumpstate.onTitlePrinted())
                        printwriter.println(" ");
                    printwriter.println("Shared users:");
                    flag = true;
                }
                printwriter.print("  SharedUser [");
                printwriter.print(sharedusersetting.name);
                printwriter.print("] (");
                printwriter.print(Integer.toHexString(System.identityHashCode(sharedusersetting)));
                printwriter.println("):");
                printwriter.print("    userId=");
                printwriter.print(sharedusersetting.userId);
                printwriter.print(" gids=");
                printwriter.println(PackageManagerService.arrayToString(((GrantedPermissions) (sharedusersetting)).gids));
                printwriter.println("    grantedPermissions:");
                Iterator iterator1 = ((GrantedPermissions) (sharedusersetting)).grantedPermissions.iterator();
                while(iterator1.hasNext())  {
                    String s1 = (String)iterator1.next();
                    printwriter.print("      ");
                    printwriter.println(s1);
                }
            }
        } while(true);
    }

    PackageSetting enableSystemPackageLPw(String s) {
        PackageSetting packagesetting = (PackageSetting)mDisabledSysPackages.get(s);
        PackageSetting packagesetting1;
        if(packagesetting == null) {
            Log.w("PackageManager", (new StringBuilder()).append("Package:").append(s).append(" is not disabled").toString());
            packagesetting1 = null;
        } else {
            if(packagesetting.pkg != null && packagesetting.pkg.applicationInfo != null) {
                ApplicationInfo applicationinfo = packagesetting.pkg.applicationInfo;
                applicationinfo.flags = 0xffffff7f & applicationinfo.flags;
            }
            packagesetting1 = addPackageLPw(s, ((PackageSettingBase) (packagesetting)).realName, ((PackageSettingBase) (packagesetting)).codePath, ((PackageSettingBase) (packagesetting)).resourcePath, ((PackageSettingBase) (packagesetting)).nativeLibraryPathString, packagesetting.appId, ((PackageSettingBase) (packagesetting)).versionCode, ((GrantedPermissions) (packagesetting)).pkgFlags);
            mDisabledSysPackages.remove(s);
        }
        return packagesetting1;
    }

    int getApplicationEnabledSettingLPr(String s, int i) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
        else
            return packagesetting.getEnabled(i);
    }

    int getComponentEnabledSettingLPr(ComponentName componentname, int i) {
        String s = componentname.getPackageName();
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown component: ").append(componentname).toString());
        else
            return packagesetting.getCurrentEnabledStateLPr(componentname.getClassName(), i);
    }

    public PackageSetting getDisabledSystemPkgLPr(String s) {
        return (PackageSetting)mDisabledSysPackages.get(s);
    }

    String getInstallerPackageNameLPr(String s) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
        else
            return ((PackageSettingBase) (packagesetting)).installerPackageName;
    }

    ArrayList getListOfIncompleteInstallPackagesLPr() {
        Iterator iterator = (new HashSet(mPackages.keySet())).iterator();
        ArrayList arraylist = new ArrayList();
        do {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
            if(packagesetting.getInstallStatus() == 0)
                arraylist.add(packagesetting);
        } while(true);
        return arraylist;
    }

    PackageSetting getPackageLPw(android.content.pm.PackageParser.Package package1, PackageSetting packagesetting, String s, SharedUserSetting sharedusersetting, File file, File file1, String s1, 
            int i, boolean flag, boolean flag1) {
        return getPackageLPw(package1.packageName, packagesetting, s, sharedusersetting, file, file1, s1, package1.mVersionCode, i, flag, flag1);
    }

    SharedUserSetting getSharedUserLPw(String s, int i, boolean flag) {
        SharedUserSetting sharedusersetting = (SharedUserSetting)mSharedUsers.get(s);
        if(sharedusersetting != null) goto _L2; else goto _L1
_L1:
        if(flag) goto _L4; else goto _L3
_L3:
        SharedUserSetting sharedusersetting1 = null;
_L6:
        return sharedusersetting1;
_L4:
        sharedusersetting = new SharedUserSetting(s, i);
        sharedusersetting.userId = newUserIdLPw(sharedusersetting);
        Log.i("PackageManager", (new StringBuilder()).append("New shared user ").append(s).append(": id=").append(sharedusersetting.userId).toString());
        if(sharedusersetting.userId >= 0)
            mSharedUsers.put(s, sharedusersetting);
_L2:
        sharedusersetting1 = sharedusersetting;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Object getUserIdLPr(int i) {
        Object obj;
        if(i >= 10000) {
            int j = mUserIds.size();
            int k = i - 10000;
            if(k < j)
                obj = mUserIds.get(k);
            else
                obj = null;
        } else {
            obj = mOtherUserIds.get(i);
        }
        return obj;
    }

    public VerifierDeviceIdentity getVerifierDeviceIdentityLPw() {
        if(mVerifierDeviceIdentity == null) {
            mVerifierDeviceIdentity = VerifierDeviceIdentity.generate();
            writeLPr();
        }
        return mVerifierDeviceIdentity;
    }

    void insertPackageSettingLPw(PackageSetting packagesetting, android.content.pm.PackageParser.Package package1) {
        packagesetting.pkg = package1;
        String s = package1.applicationInfo.sourceDir;
        String s1 = package1.applicationInfo.publicSourceDir;
        if(!s.equalsIgnoreCase(((PackageSettingBase) (packagesetting)).codePathString)) {
            Slog.w("PackageManager", (new StringBuilder()).append("Code path for pkg : ").append(packagesetting.pkg.packageName).append(" changing from ").append(((PackageSettingBase) (packagesetting)).codePathString).append(" to ").append(s).toString());
            packagesetting.codePath = new File(s);
            packagesetting.codePathString = s;
        }
        if(!s1.equalsIgnoreCase(((PackageSettingBase) (packagesetting)).resourcePathString)) {
            Slog.w("PackageManager", (new StringBuilder()).append("Resource path for pkg : ").append(packagesetting.pkg.packageName).append(" changing from ").append(((PackageSettingBase) (packagesetting)).resourcePathString).append(" to ").append(s1).toString());
            packagesetting.resourcePath = new File(s1);
            packagesetting.resourcePathString = s1;
        }
        String s2 = package1.applicationInfo.nativeLibraryDir;
        if(s2 != null && !s2.equalsIgnoreCase(((PackageSettingBase) (packagesetting)).nativeLibraryPathString))
            packagesetting.nativeLibraryPathString = s2;
        if(package1.mVersionCode != ((PackageSettingBase) (packagesetting)).versionCode)
            packagesetting.versionCode = package1.mVersionCode;
        if(((PackageSettingBase) (packagesetting)).signatures.mSignatures == null)
            ((PackageSettingBase) (packagesetting)).signatures.assignSignatures(package1.mSignatures);
        if(packagesetting.sharedUser != null && packagesetting.sharedUser.signatures.mSignatures == null)
            packagesetting.sharedUser.signatures.assignSignatures(package1.mSignatures);
        addPackageSettingLPw(packagesetting, package1.packageName, packagesetting.sharedUser);
    }

    boolean isDisabledSystemPackageLPr(String s) {
        return mDisabledSysPackages.containsKey(s);
    }

    boolean isEnabledLPr(ComponentInfo componentinfo, int i, int j) {
        boolean flag = true;
        if((i & 0x200) == 0) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        String s = componentinfo.packageName;
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null) {
            flag = false;
        } else {
            int k = packagesetting.getEnabled(j);
            if(k == 2 || k == 3 || packagesetting.pkg != null && !packagesetting.pkg.applicationInfo.enabled && k == 0)
                flag = false;
            else
            if(!packagesetting.getEnabledComponents(j).contains(componentinfo.name))
                if(packagesetting.getDisabledComponents(j).contains(componentinfo.name))
                    flag = false;
                else
                    flag = componentinfo.enabled;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    PackageSetting peekPackageLPr(String s) {
        return (PackageSetting)mPackages.get(s);
    }

    void readAllUsersPackageRestrictionsLPr() {
        List list = getAllUsers();
        if(list == null) {
            readPackageRestrictionsLPr(0);
        } else {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) 
                readPackageRestrictionsLPr(((UserInfo)iterator.next()).id);
        }
    }

    boolean readLPw(List list) {
        FileInputStream fileinputstream;
        fileinputstream = null;
        if(!mBackupSettingsFilename.exists())
            break MISSING_BLOCK_LABEL_94;
        FileInputStream fileinputstream2 = new FileInputStream(mBackupSettingsFilename);
        mReadMessages.append("Reading from backup settings file\n");
        PackageManagerService.reportSettingsProblem(4, "Need to read from backup settings file");
        if(mSettingsFilename.exists()) {
            Slog.w("PackageManager", (new StringBuilder()).append("Cleaning up settings file ").append(mSettingsFilename).toString());
            mSettingsFilename.delete();
        }
        fileinputstream = fileinputstream2;
_L11:
        mPendingPackages.clear();
        mPastSignatures.clear();
        if(fileinputstream != null)
            break MISSING_BLOCK_LABEL_166;
        boolean flag;
        if(!mSettingsFilename.exists()) {
            mReadMessages.append("No settings file found\n");
            PackageManagerService.reportSettingsProblem(4, "No settings file; creating initial state");
            readDefaultPreferredAppsLPw();
            flag = false;
            break; /* Loop/switch isn't completed */
        }
        FileInputStream fileinputstream1 = new FileInputStream(mSettingsFilename);
        fileinputstream = fileinputstream1;
        XmlPullParser xmlpullparser;
        int l;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
        int k;
        do
            k = xmlpullparser.next();
        while(k != 2 && k != 1);
        if(k != 2) {
            mReadMessages.append("No start tag found in settings file\n");
            PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager settings");
            Log.wtf("PackageManager", "No start tag found in package manager settings");
            flag = false;
            break; /* Loop/switch isn't completed */
        }
        l = xmlpullparser.getDepth();
_L3:
        String s2;
        int i1;
        do {
            i1 = xmlpullparser.next();
            if(i1 == 1 || i1 == 3 && xmlpullparser.getDepth() <= l)
                break MISSING_BLOCK_LABEL_1066;
        } while(i1 == 3 || i1 == 4);
        s2 = xmlpullparser.getName();
        if(!s2.equals("package")) goto _L2; else goto _L1
_L1:
        readPackageLPw(xmlpullparser);
          goto _L3
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(xmlpullparserexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading settings: ").append(xmlpullparserexception).toString());
        Log.wtf("PackageManager", "Error reading package manager settings", xmlpullparserexception);
_L4:
        int i = mPendingPackages.size();
        int j = 0;
        while(j < i)  {
            PendingPackage pendingpackage = (PendingPackage)mPendingPackages.get(j);
            Object obj1 = getUserIdLPr(pendingpackage.sharedId);
            if(obj1 != null && (obj1 instanceof SharedUserSetting)) {
                PackageSetting packagesetting1 = getPackageLPw(((PackageSettingBase) (pendingpackage)).name, null, ((PackageSettingBase) (pendingpackage)).realName, (SharedUserSetting)obj1, ((PackageSettingBase) (pendingpackage)).codePath, ((PackageSettingBase) (pendingpackage)).resourcePath, ((PackageSettingBase) (pendingpackage)).nativeLibraryPathString, ((PackageSettingBase) (pendingpackage)).versionCode, ((GrantedPermissions) (pendingpackage)).pkgFlags, true, true);
                IOException ioexception;
                String s3;
                IllegalArgumentException illegalargumentexception;
                NumberFormatException numberformatexception;
                String s4;
                String s5;
                String s6;
                String s7;
                String s8;
                if(packagesetting1 == null)
                    PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unable to create application package for ").append(((PackageSettingBase) (pendingpackage)).name).toString());
                else
                    packagesetting1.copyFrom(pendingpackage);
            } else
            if(obj1 != null) {
                String s1 = (new StringBuilder()).append("Bad package setting: package ").append(((PackageSettingBase) (pendingpackage)).name).append(" has shared uid ").append(pendingpackage.sharedId).append(" that is not a shared uid\n").toString();
                mReadMessages.append(s1);
                PackageManagerService.reportSettingsProblem(6, s1);
            } else {
                String s = (new StringBuilder()).append("Bad package setting: package ").append(((PackageSettingBase) (pendingpackage)).name).append(" has shared uid ").append(pendingpackage.sharedId).append(" that is not defined\n").toString();
                mReadMessages.append(s);
                PackageManagerService.reportSettingsProblem(6, s);
            }
            j++;
        }
        break MISSING_BLOCK_LABEL_1220;
_L2:
        if(!s2.equals("permissions"))
            break MISSING_BLOCK_LABEL_636;
        readPermissionsLPw(mPermissions, xmlpullparser);
          goto _L3
        ioexception;
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(ioexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading settings: ").append(ioexception).toString());
        Log.wtf("PackageManager", "Error reading package manager settings", ioexception);
          goto _L4
        if(s2.equals("permission-trees"))
            readPermissionsLPw(mPermissionTrees, xmlpullparser);
        else
        if(s2.equals("shared-user"))
            readSharedUserLPw(xmlpullparser);
        else
        if(!s2.equals("preferred-packages"))
            if(s2.equals("preferred-activities"))
                readPreferredActivitiesLPw(xmlpullparser);
            else
            if(s2.equals("updated-package"))
                readDisabledSysPackageLPw(xmlpullparser);
            else
            if(s2.equals("cleaning-package")) {
                s8 = xmlpullparser.getAttributeValue(null, "name");
                if(s8 != null)
                    mPackagesToBeCleaned.add(s8);
            } else {
                if(!s2.equals("renamed-package"))
                    break MISSING_BLOCK_LABEL_834;
                s6 = xmlpullparser.getAttributeValue(null, "new");
                s7 = xmlpullparser.getAttributeValue(null, "old");
                if(s6 != null && s7 != null)
                    mRenamedPackages.put(s6, s7);
            }
          goto _L3
        if(!s2.equals("last-platform-version"))
            break MISSING_BLOCK_LABEL_917;
        mExternalSdkPlatform = 0;
        mInternalSdkPlatform = 0;
        try {
            s4 = xmlpullparser.getAttributeValue(null, "internal");
            if(s4 != null)
                mInternalSdkPlatform = Integer.parseInt(s4);
            s5 = xmlpullparser.getAttributeValue(null, "external");
            if(s5 != null)
                mExternalSdkPlatform = Integer.parseInt(s5);
        }
        // Misplaced declaration of an exception variable
        catch(NumberFormatException numberformatexception) { }
          goto _L3
        if(!s2.equals("verifier"))
            break MISSING_BLOCK_LABEL_989;
        s3 = xmlpullparser.getAttributeValue(null, "device");
        mVerifierDeviceIdentity = VerifierDeviceIdentity.parse(s3);
          goto _L3
        illegalargumentexception;
        Slog.w("PackageManager", (new StringBuilder()).append("Discard invalid verifier device id: ").append(illegalargumentexception.getMessage()).toString());
          goto _L3
        if("read-external-storage".equals(s2)) {
            mReadExternalStorageEnforced = Boolean.valueOf("1".equals(xmlpullparser.getAttributeValue(null, "enforcement")));
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Unknown element under <packages>: ").append(xmlpullparser.getName()).toString());
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
          goto _L3
        fileinputstream.close();
          goto _L4
        mPendingPackages.clear();
        Iterator iterator = mDisabledSysPackages.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            PackageSetting packagesetting = (PackageSetting)iterator.next();
            Object obj = getUserIdLPr(packagesetting.appId);
            if(obj != null && (obj instanceof SharedUserSetting))
                packagesetting.sharedUser = (SharedUserSetting)obj;
        } while(true);
        if(!mBackupStoppedPackagesFilename.exists() && !mStoppedPackagesFilename.exists()) goto _L6; else goto _L5
_L5:
        readStoppedLPw();
        mBackupStoppedPackagesFilename.delete();
        mStoppedPackagesFilename.delete();
        writePackageRestrictionsLPr(0);
_L8:
        mReadMessages.append((new StringBuilder()).append("Read completed successfully: ").append(mPackages.size()).append(" packages, ").append(mSharedUsers.size()).append(" shared uids\n").toString());
        flag = true;
        break; /* Loop/switch isn't completed */
_L6:
        if(list != null)
            break; /* Loop/switch isn't completed */
        readPackageRestrictionsLPr(0);
        if(true) goto _L8; else goto _L7
_L7:
        Iterator iterator1 = list.iterator();
        while(iterator1.hasNext()) 
            readPackageRestrictionsLPr(((UserInfo)iterator1.next()).id);
        if(true) goto _L8; else goto _L9
_L9:
        IOException ioexception2;
        break; /* Loop/switch isn't completed */
        ioexception2;
        continue; /* Loop/switch isn't completed */
        IOException ioexception1;
        ioexception1;
        fileinputstream = fileinputstream2;
        if(true) goto _L11; else goto _L10
_L10:
        return flag;
    }

    void readPackageRestrictionsLPr(int i) {
        FileInputStream fileinputstream;
        File file;
        File file1;
        fileinputstream = null;
        file = getUserPackagesStateFile(i);
        file1 = getUserPackagesStateBackupFile(i);
        if(!file1.exists()) goto _L2; else goto _L1
_L1:
        FileInputStream fileinputstream1 = new FileInputStream(file1);
        mReadMessages.append("Reading from backup stopped packages file\n");
        PackageManagerService.reportSettingsProblem(4, "Need to read from backup stopped packages file");
        if(file.exists()) {
            Slog.w("PackageManager", (new StringBuilder()).append("Cleaning up stopped packages file ").append(file).toString());
            file.delete();
        }
_L8:
        if(fileinputstream1 != null) goto _L4; else goto _L3
_L3:
        if(file.exists()) goto _L6; else goto _L5
_L5:
        mReadMessages.append("No stopped packages file found\n");
        PackageManagerService.reportSettingsProblem(4, "No stopped packages file; assuming all started");
        PackageSetting packagesetting1;
        for(Iterator iterator = mPackages.values().iterator(); iterator.hasNext(); packagesetting1.setNotLaunched(false, i)) {
            packagesetting1 = (PackageSetting)iterator.next();
            packagesetting1.setStopped(false, i);
        }

          goto _L7
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        fileinputstream1;
_L21:
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(xmlpullparserexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading stopped packages: ").append(xmlpullparserexception).toString());
        Log.wtf("PackageManager", "Error reading package manager stopped packages", xmlpullparserexception);
_L9:
        return;
        IOException ioexception2;
        ioexception2;
_L19:
        fileinputstream1 = fileinputstream;
          goto _L8
_L7:
        fileinputstream1;
          goto _L9
_L6:
        FileInputStream fileinputstream2 = new FileInputStream(file);
_L20:
        XmlPullParser xmlpullparser;
label0:
        {
            xmlpullparser = Xml.newPullParser();
            xmlpullparser.setInput(fileinputstream2, null);
            int j;
            do
                j = xmlpullparser.next();
            while(j != 2 && j != 1);
            if(j == 2)
                break label0;
            mReadMessages.append("No start tag found in package restrictions file\n");
            PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager stopped packages");
        }
          goto _L9
        int k = xmlpullparser.getDepth();
_L13:
        int l = xmlpullparser.next();
        if(l == 1 || l == 3 && xmlpullparser.getDepth() <= k) goto _L11; else goto _L10
_L10:
        if(l == 3 || l == 4) goto _L13; else goto _L12
_L12:
        if(!xmlpullparser.getName().equals("pkg")) goto _L15; else goto _L14
_L14:
        String s;
        PackageSetting packagesetting;
        s = xmlpullparser.getAttributeValue(null, "name");
        packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting != null) goto _L17; else goto _L16
_L16:
        Slog.w("PackageManager", (new StringBuilder()).append("No package known for stopped package: ").append(s).toString());
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L13
        IOException ioexception;
        ioexception;
_L18:
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(ioexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading settings: ").append(ioexception).toString());
        Log.wtf("PackageManager", "Error reading package manager stopped packages", ioexception);
          goto _L9
_L17:
        String s1 = xmlpullparser.getAttributeValue(null, "enabled");
        int i1;
        String s2;
        boolean flag;
        String s3;
        boolean flag1;
        int j1;
        int k1;
        if(s1 == null)
            i1 = 0;
        else
            i1 = Integer.parseInt(s1);
        packagesetting.setEnabled(i1, i);
        s2 = xmlpullparser.getAttributeValue(null, "stopped");
        if(s2 == null)
            flag = false;
        else
            flag = Boolean.parseBoolean(s2);
        packagesetting.setStopped(flag, i);
        s3 = xmlpullparser.getAttributeValue(null, "nl");
        if(s2 == null)
            flag1 = false;
        else
            flag1 = Boolean.parseBoolean(s3);
        packagesetting.setNotLaunched(flag1, i);
        j1 = xmlpullparser.getDepth();
        k1 = xmlpullparser.next();
        if(k1 != 1 && (k1 != 3 || xmlpullparser.getDepth() > j1)) {
            if(k1 != 3 && k1 != 4) {
                String s4 = xmlpullparser.getName();
                if(s4.equals("enabled-components"))
                    packagesetting.setEnabledComponents(readComponentsLPr(xmlpullparser), i);
                else
                if(s4.equals("disabled-components"))
                    packagesetting.setDisabledComponents(readComponentsLPr(xmlpullparser), i);
            }
            break MISSING_BLOCK_LABEL_641;
        }
          goto _L13
_L15:
        Slog.w("PackageManager", (new StringBuilder()).append("Unknown element under <stopped-packages>: ").append(xmlpullparser.getName()).toString());
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L13
_L11:
        fileinputstream2.close();
          goto _L9
        ioexception;
        fileinputstream1;
          goto _L18
        IOException ioexception1;
        ioexception1;
        fileinputstream = fileinputstream1;
          goto _L19
_L4:
        fileinputstream2 = fileinputstream1;
          goto _L20
_L2:
        fileinputstream1 = null;
          goto _L8
        xmlpullparserexception;
          goto _L21
    }

    void readStoppedLPw() {
        FileInputStream fileinputstream = null;
        if(!mBackupStoppedPackagesFilename.exists()) goto _L2; else goto _L1
_L1:
        FileInputStream fileinputstream1 = new FileInputStream(mBackupStoppedPackagesFilename);
        mReadMessages.append("Reading from backup stopped packages file\n");
        PackageManagerService.reportSettingsProblem(4, "Need to read from backup stopped packages file");
        if(mSettingsFilename.exists()) {
            Slog.w("PackageManager", (new StringBuilder()).append("Cleaning up stopped packages file ").append(mStoppedPackagesFilename).toString());
            mStoppedPackagesFilename.delete();
        }
_L8:
        if(fileinputstream1 != null) goto _L4; else goto _L3
_L3:
        if(mStoppedPackagesFilename.exists()) goto _L6; else goto _L5
_L5:
        mReadMessages.append("No stopped packages file found\n");
        PackageManagerService.reportSettingsProblem(4, "No stopped packages file file; assuming all started");
        PackageSetting packagesetting1;
        for(Iterator iterator = mPackages.values().iterator(); iterator.hasNext(); packagesetting1.setNotLaunched(false, 0)) {
            packagesetting1 = (PackageSetting)iterator.next();
            packagesetting1.setStopped(false, 0);
        }

          goto _L7
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        fileinputstream1;
_L22:
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(xmlpullparserexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading stopped packages: ").append(xmlpullparserexception).toString());
        Log.wtf("PackageManager", "Error reading package manager stopped packages", xmlpullparserexception);
_L9:
        return;
        IOException ioexception2;
        ioexception2;
_L20:
        fileinputstream1 = fileinputstream;
          goto _L8
_L7:
        fileinputstream1;
          goto _L9
_L6:
        FileInputStream fileinputstream2 = new FileInputStream(mStoppedPackagesFilename);
_L21:
        XmlPullParser xmlpullparser;
label0:
        {
            xmlpullparser = Xml.newPullParser();
            xmlpullparser.setInput(fileinputstream2, null);
            int i;
            do
                i = xmlpullparser.next();
            while(i != 2 && i != 1);
            if(i == 2)
                break label0;
            mReadMessages.append("No start tag found in stopped packages file\n");
            PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager stopped packages");
        }
          goto _L9
        int j = xmlpullparser.getDepth();
_L13:
        int k = xmlpullparser.next();
        if(k == 1 || k == 3 && xmlpullparser.getDepth() <= j) goto _L11; else goto _L10
_L10:
        if(k == 3 || k == 4) goto _L13; else goto _L12
_L12:
        if(!xmlpullparser.getName().equals("pkg")) goto _L15; else goto _L14
_L14:
        String s;
        PackageSetting packagesetting;
        s = xmlpullparser.getAttributeValue(null, "name");
        packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null) goto _L17; else goto _L16
_L16:
        packagesetting.setStopped(true, 0);
        if("1".equals(xmlpullparser.getAttributeValue(null, "nl")))
            packagesetting.setNotLaunched(true, 0);
_L18:
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L13
        IOException ioexception;
        ioexception;
_L19:
        mReadMessages.append((new StringBuilder()).append("Error reading: ").append(ioexception.toString()).toString());
        PackageManagerService.reportSettingsProblem(6, (new StringBuilder()).append("Error reading settings: ").append(ioexception).toString());
        Log.wtf("PackageManager", "Error reading package manager stopped packages", ioexception);
          goto _L9
_L17:
        Slog.w("PackageManager", (new StringBuilder()).append("No package known for stopped package: ").append(s).toString());
          goto _L18
_L15:
        Slog.w("PackageManager", (new StringBuilder()).append("Unknown element under <stopped-packages>: ").append(xmlpullparser.getName()).toString());
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L13
_L11:
        fileinputstream2.close();
          goto _L9
        ioexception;
        fileinputstream1;
          goto _L19
        IOException ioexception1;
        ioexception1;
        fileinputstream = fileinputstream1;
          goto _L20
_L4:
        fileinputstream2 = fileinputstream1;
          goto _L21
_L2:
        fileinputstream1 = null;
          goto _L8
        xmlpullparserexception;
          goto _L22
    }

    void removeDisabledSystemPackageLPw(String s) {
        mDisabledSysPackages.remove(s);
    }

    int removePackageLPw(String s) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null) goto _L2; else goto _L1
_L1:
        mPackages.remove(s);
        if(packagesetting.sharedUser == null) goto _L4; else goto _L3
_L3:
        packagesetting.sharedUser.packages.remove(packagesetting);
        if(packagesetting.sharedUser.packages.size() != 0) goto _L2; else goto _L5
_L5:
        int i;
        mSharedUsers.remove(packagesetting.sharedUser.name);
        removeUserIdLPw(packagesetting.sharedUser.userId);
        i = packagesetting.sharedUser.userId;
_L7:
        return i;
_L4:
        removeUserIdLPw(packagesetting.appId);
        i = packagesetting.appId;
        continue; /* Loop/switch isn't completed */
_L2:
        i = -1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    void removeUserLPr(int i) {
        getUserPackagesStateFile(i).delete();
        getUserPackagesStateBackupFile(i).delete();
    }

    void setInstallStatus(String s, int i) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting != null && packagesetting.getInstallStatus() != i)
            packagesetting.setInstallStatus(i);
    }

    void setInstallerPackageName(String s, String s1) {
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting != null)
            packagesetting.setInstallerPackageName(s1);
    }

    boolean setPackageStoppedStateLPw(String s, boolean flag, boolean flag1, int i, int j) {
        int k = UserId.getAppId(i);
        PackageSetting packagesetting = (PackageSetting)mPackages.get(s);
        if(packagesetting == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
        if(!flag1 && k != packagesetting.appId)
            throw new SecurityException((new StringBuilder()).append("Permission Denial: attempt to change stopped state from pid=").append(Binder.getCallingPid()).append(", uid=").append(i).append(", package uid=").append(packagesetting.appId).toString());
        boolean flag2;
        if(packagesetting.getStopped(j) != flag) {
            packagesetting.setStopped(flag, j);
            if(packagesetting.getNotLaunched(j)) {
                if(((PackageSettingBase) (packagesetting)).installerPackageName != null)
                    PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_FIRST_LAUNCH", ((PackageSettingBase) (packagesetting)).name, null, ((PackageSettingBase) (packagesetting)).installerPackageName, null, j);
                packagesetting.setNotLaunched(false, j);
            }
            flag2 = true;
        } else {
            flag2 = false;
        }
        return flag2;
    }

    void transferPermissionsLPw(String s, String s1) {
label0:
        for(int i = 0; i < 2; i++) {
            HashMap hashmap;
            Iterator iterator;
            if(i == 0)
                hashmap = mPermissionTrees;
            else
                hashmap = mPermissions;
            iterator = hashmap.values().iterator();
            do {
                if(!iterator.hasNext())
                    continue label0;
                BasePermission basepermission = (BasePermission)iterator.next();
                if(s.equals(basepermission.sourcePackage)) {
                    basepermission.sourcePackage = s1;
                    basepermission.packageSetting = null;
                    basepermission.perm = null;
                    if(basepermission.pendingInfo != null)
                        basepermission.pendingInfo.packageName = s1;
                    basepermission.uid = 0;
                    basepermission.gids = null;
                }
            } while(true);
        }

    }

    void updateSharedUserPermsLPw(PackageSetting packagesetting, int ai[]) {
        if(packagesetting != null && packagesetting.pkg != null) goto _L2; else goto _L1
_L1:
        Slog.i("PackageManager", "Trying to update info for null package. Just ignoring");
_L4:
        return;
_L2:
        if(packagesetting.sharedUser == null)
            continue; /* Loop/switch isn't completed */
        SharedUserSetting sharedusersetting = packagesetting.sharedUser;
        Iterator iterator = packagesetting.pkg.requestedPermissions.iterator();
        do {
            if(!iterator.hasNext())
                break;
            String s1 = (String)iterator.next();
            boolean flag = false;
            if(!((GrantedPermissions) (sharedusersetting)).grantedPermissions.contains(s1))
                continue;
            Iterator iterator2 = sharedusersetting.packages.iterator();
            do {
                if(!iterator2.hasNext())
                    break;
                PackageSetting packagesetting1 = (PackageSetting)iterator2.next();
                if(packagesetting1.pkg == null || packagesetting1.pkg.packageName.equals(packagesetting.pkg.packageName) || !packagesetting1.pkg.requestedPermissions.contains(s1))
                    continue;
                flag = true;
                break;
            } while(true);
            if(!flag)
                ((GrantedPermissions) (sharedusersetting)).grantedPermissions.remove(s1);
        } while(true);
        int ai1[] = ai;
        Iterator iterator1 = ((GrantedPermissions) (sharedusersetting)).grantedPermissions.iterator();
        do {
            if(!iterator1.hasNext())
                break;
            String s = (String)iterator1.next();
            BasePermission basepermission = (BasePermission)mPermissions.get(s);
            if(basepermission != null)
                ai1 = PackageManagerService.appendInts(ai1, basepermission.gids);
        } while(true);
        sharedusersetting.gids = ai1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    void writeAllUsersPackageRestrictionsLPr() {
        List list = getAllUsers();
        if(list != null) {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) 
                writePackageRestrictionsLPr(((UserInfo)iterator.next()).id);
        }
    }

    void writeDisabledSysPackageLPr(XmlSerializer xmlserializer, PackageSetting packagesetting) throws IOException {
        xmlserializer.startTag(null, "updated-package");
        xmlserializer.attribute(null, "name", ((PackageSettingBase) (packagesetting)).name);
        if(((PackageSettingBase) (packagesetting)).realName != null)
            xmlserializer.attribute(null, "realName", ((PackageSettingBase) (packagesetting)).realName);
        xmlserializer.attribute(null, "codePath", ((PackageSettingBase) (packagesetting)).codePathString);
        xmlserializer.attribute(null, "ft", Long.toHexString(((PackageSettingBase) (packagesetting)).timeStamp));
        xmlserializer.attribute(null, "it", Long.toHexString(((PackageSettingBase) (packagesetting)).firstInstallTime));
        xmlserializer.attribute(null, "ut", Long.toHexString(((PackageSettingBase) (packagesetting)).lastUpdateTime));
        xmlserializer.attribute(null, "version", String.valueOf(((PackageSettingBase) (packagesetting)).versionCode));
        if(!((PackageSettingBase) (packagesetting)).resourcePathString.equals(((PackageSettingBase) (packagesetting)).codePathString))
            xmlserializer.attribute(null, "resourcePath", ((PackageSettingBase) (packagesetting)).resourcePathString);
        if(((PackageSettingBase) (packagesetting)).nativeLibraryPathString != null)
            xmlserializer.attribute(null, "nativeLibraryPath", ((PackageSettingBase) (packagesetting)).nativeLibraryPathString);
        if(packagesetting.sharedUser == null)
            xmlserializer.attribute(null, "userId", Integer.toString(packagesetting.appId));
        else
            xmlserializer.attribute(null, "sharedUserId", Integer.toString(packagesetting.appId));
        xmlserializer.startTag(null, "perms");
        if(packagesetting.sharedUser == null) {
            Iterator iterator = ((GrantedPermissions) (packagesetting)).grantedPermissions.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                String s = (String)iterator.next();
                if((BasePermission)mPermissions.get(s) != null) {
                    xmlserializer.startTag(null, "item");
                    xmlserializer.attribute(null, "name", s);
                    xmlserializer.endTag(null, "item");
                }
            } while(true);
        }
        xmlserializer.endTag(null, "perms");
        xmlserializer.endTag(null, "updated-package");
    }

    void writeLPr() {
        if(!mSettingsFilename.exists()) goto _L2; else goto _L1
_L1:
        if(mBackupSettingsFilename.exists()) goto _L4; else goto _L3
_L3:
        if(mSettingsFilename.renameTo(mBackupSettingsFilename)) goto _L2; else goto _L5
_L5:
        Log.wtf("PackageManager", "Unable to backup package manager settings,  current changes will be lost at reboot");
_L11:
        return;
_L4:
        mSettingsFilename.delete();
        Slog.w("PackageManager", "Preserving older settings backup");
_L2:
        mPastSignatures.clear();
        FileOutputStream fileoutputstream;
        BufferedOutputStream bufferedoutputstream;
        FastXmlSerializer fastxmlserializer;
        fileoutputstream = new FileOutputStream(mSettingsFilename);
        bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "packages");
        fastxmlserializer.startTag(null, "last-platform-version");
        fastxmlserializer.attribute(null, "internal", Integer.toString(mInternalSdkPlatform));
        fastxmlserializer.attribute(null, "external", Integer.toString(mExternalSdkPlatform));
        fastxmlserializer.endTag(null, "last-platform-version");
        if(mVerifierDeviceIdentity != null) {
            fastxmlserializer.startTag(null, "verifier");
            fastxmlserializer.attribute(null, "device", mVerifierDeviceIdentity.toString());
            fastxmlserializer.endTag(null, "verifier");
        }
        if(mReadExternalStorageEnforced == null) goto _L7; else goto _L6
_L6:
        fastxmlserializer.startTag(null, "read-external-storage");
        if(!mReadExternalStorageEnforced.booleanValue()) goto _L9; else goto _L8
_L8:
        String s3 = "1";
_L12:
        fastxmlserializer.attribute(null, "enforcement", s3);
        fastxmlserializer.endTag(null, "read-external-storage");
_L7:
        fastxmlserializer.startTag(null, "permission-trees");
        for(Iterator iterator = mPermissionTrees.values().iterator(); iterator.hasNext(); writePermissionLPr(fastxmlserializer, (BasePermission)iterator.next()));
          goto _L10
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Log.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", xmlpullparserexception);
_L13:
        if(mSettingsFilename.exists() && !mSettingsFilename.delete())
            Log.wtf("PackageManager", (new StringBuilder()).append("Failed to clean up mangled file: ").append(mSettingsFilename).toString());
          goto _L11
_L9:
        s3 = "0";
          goto _L12
_L10:
        fastxmlserializer.endTag(null, "permission-trees");
        fastxmlserializer.startTag(null, "permissions");
        for(Iterator iterator1 = mPermissions.values().iterator(); iterator1.hasNext(); writePermissionLPr(fastxmlserializer, (BasePermission)iterator1.next()));
        break MISSING_BLOCK_LABEL_509;
        IOException ioexception;
        ioexception;
        Log.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", ioexception);
          goto _L13
        JournaledFile journaledfile;
        FileOutputStream fileoutputstream1;
        BufferedOutputStream bufferedoutputstream1;
        fastxmlserializer.endTag(null, "permissions");
        for(Iterator iterator2 = mPackages.values().iterator(); iterator2.hasNext(); writePackageLPr(fastxmlserializer, (PackageSetting)iterator2.next()));
        for(Iterator iterator3 = mDisabledSysPackages.values().iterator(); iterator3.hasNext(); writeDisabledSysPackageLPr(fastxmlserializer, (PackageSetting)iterator3.next()));
        writePreferredActivitiesLPr(fastxmlserializer);
        for(Iterator iterator4 = mSharedUsers.values().iterator(); iterator4.hasNext(); fastxmlserializer.endTag(null, "shared-user")) {
            SharedUserSetting sharedusersetting = (SharedUserSetting)iterator4.next();
            fastxmlserializer.startTag(null, "shared-user");
            fastxmlserializer.attribute(null, "name", sharedusersetting.name);
            fastxmlserializer.attribute(null, "userId", Integer.toString(sharedusersetting.userId));
            sharedusersetting.signatures.writeXml(fastxmlserializer, "sigs", mPastSignatures);
            fastxmlserializer.startTag(null, "perms");
            for(Iterator iterator7 = ((GrantedPermissions) (sharedusersetting)).grantedPermissions.iterator(); iterator7.hasNext(); fastxmlserializer.endTag(null, "item")) {
                String s2 = (String)iterator7.next();
                fastxmlserializer.startTag(null, "item");
                fastxmlserializer.attribute(null, "name", s2);
            }

            fastxmlserializer.endTag(null, "perms");
        }

        if(mPackagesToBeCleaned.size() > 0) {
            for(int i = 0; i < mPackagesToBeCleaned.size(); i++) {
                fastxmlserializer.startTag(null, "cleaning-package");
                fastxmlserializer.attribute(null, "name", (String)mPackagesToBeCleaned.get(i));
                fastxmlserializer.endTag(null, "cleaning-package");
            }

        }
        if(mRenamedPackages.size() > 0) {
            for(Iterator iterator6 = mRenamedPackages.entrySet().iterator(); iterator6.hasNext(); fastxmlserializer.endTag(null, "renamed-package")) {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator6.next();
                fastxmlserializer.startTag(null, "renamed-package");
                fastxmlserializer.attribute(null, "new", (String)entry.getKey());
                fastxmlserializer.attribute(null, "old", (String)entry.getValue());
            }

        }
        fastxmlserializer.endTag(null, "packages");
        fastxmlserializer.endDocument();
        bufferedoutputstream.flush();
        FileUtils.sync(fileoutputstream);
        bufferedoutputstream.close();
        mBackupSettingsFilename.delete();
        FileUtils.setPermissions(mSettingsFilename.toString(), 432, -1, -1);
        File file = new File((new StringBuilder()).append(mPackageListFilename.toString()).append(".tmp").toString());
        journaledfile = new JournaledFile(mPackageListFilename, file);
        fileoutputstream1 = new FileOutputStream(journaledfile.chooseForWrite());
        bufferedoutputstream1 = new BufferedOutputStream(fileoutputstream1);
        StringBuilder stringbuilder;
        Iterator iterator5;
        stringbuilder = new StringBuilder();
        iterator5 = mPackages.values().iterator();
_L17:
        ApplicationInfo applicationinfo;
        String s;
        if(!iterator5.hasNext())
            break MISSING_BLOCK_LABEL_1360;
        applicationinfo = ((PackageSetting)iterator5.next()).pkg.applicationInfo;
        s = applicationinfo.dataDir;
        if((2 & applicationinfo.flags) == 0) goto _L15; else goto _L14
_L14:
        boolean flag = true;
_L20:
        if(s.indexOf(" ") >= 0 || applicationinfo.uid <= 10000) goto _L17; else goto _L16
_L16:
        stringbuilder.setLength(0);
        stringbuilder.append(applicationinfo.packageName);
        stringbuilder.append(" ");
        stringbuilder.append(applicationinfo.uid);
        if(!flag) goto _L19; else goto _L18
_L18:
        String s1 = " 1 ";
_L21:
        stringbuilder.append(s1);
        stringbuilder.append(s);
        stringbuilder.append("\n");
        bufferedoutputstream1.write(stringbuilder.toString().getBytes());
          goto _L17
        Exception exception;
        exception;
        IoUtils.closeQuietly(bufferedoutputstream1);
        journaledfile.rollback();
_L22:
        FileUtils.setPermissions(mPackageListFilename.toString(), 432, -1, -1);
        writeAllUsersPackageRestrictionsLPr();
          goto _L11
_L15:
        flag = false;
          goto _L20
_L19:
        s1 = " 0 ";
          goto _L21
        bufferedoutputstream1.flush();
        FileUtils.sync(fileoutputstream1);
        bufferedoutputstream1.close();
        journaledfile.commit();
          goto _L22
    }

    void writePackageLPr(XmlSerializer xmlserializer, PackageSetting packagesetting) throws IOException {
        xmlserializer.startTag(null, "package");
        xmlserializer.attribute(null, "name", ((PackageSettingBase) (packagesetting)).name);
        if(((PackageSettingBase) (packagesetting)).realName != null)
            xmlserializer.attribute(null, "realName", ((PackageSettingBase) (packagesetting)).realName);
        xmlserializer.attribute(null, "codePath", ((PackageSettingBase) (packagesetting)).codePathString);
        if(!((PackageSettingBase) (packagesetting)).resourcePathString.equals(((PackageSettingBase) (packagesetting)).codePathString))
            xmlserializer.attribute(null, "resourcePath", ((PackageSettingBase) (packagesetting)).resourcePathString);
        if(((PackageSettingBase) (packagesetting)).nativeLibraryPathString != null)
            xmlserializer.attribute(null, "nativeLibraryPath", ((PackageSettingBase) (packagesetting)).nativeLibraryPathString);
        xmlserializer.attribute(null, "flags", Integer.toString(((GrantedPermissions) (packagesetting)).pkgFlags));
        xmlserializer.attribute(null, "ft", Long.toHexString(((PackageSettingBase) (packagesetting)).timeStamp));
        xmlserializer.attribute(null, "it", Long.toHexString(((PackageSettingBase) (packagesetting)).firstInstallTime));
        xmlserializer.attribute(null, "ut", Long.toHexString(((PackageSettingBase) (packagesetting)).lastUpdateTime));
        xmlserializer.attribute(null, "version", String.valueOf(((PackageSettingBase) (packagesetting)).versionCode));
        if(packagesetting.sharedUser == null)
            xmlserializer.attribute(null, "userId", Integer.toString(packagesetting.appId));
        else
            xmlserializer.attribute(null, "sharedUserId", Integer.toString(packagesetting.appId));
        if(((PackageSettingBase) (packagesetting)).uidError)
            xmlserializer.attribute(null, "uidError", "true");
        if(((PackageSettingBase) (packagesetting)).installStatus == 0)
            xmlserializer.attribute(null, "installStatus", "false");
        if(((PackageSettingBase) (packagesetting)).installerPackageName != null)
            xmlserializer.attribute(null, "installer", ((PackageSettingBase) (packagesetting)).installerPackageName);
        ((PackageSettingBase) (packagesetting)).signatures.writeXml(xmlserializer, "sigs", mPastSignatures);
        if((1 & ((GrantedPermissions) (packagesetting)).pkgFlags) == 0) {
            xmlserializer.startTag(null, "perms");
            if(packagesetting.sharedUser == null) {
                for(Iterator iterator = ((GrantedPermissions) (packagesetting)).grantedPermissions.iterator(); iterator.hasNext(); xmlserializer.endTag(null, "item")) {
                    String s = (String)iterator.next();
                    xmlserializer.startTag(null, "item");
                    xmlserializer.attribute(null, "name", s);
                }

            }
            xmlserializer.endTag(null, "perms");
        }
        xmlserializer.endTag(null, "package");
    }

    void writePackageRestrictionsLPr(int i) {
        File file;
        File file1;
        file = getUserPackagesStateFile(i);
        file1 = getUserPackagesStateBackupFile(i);
        (new File(file.getParent())).mkdirs();
        if(!file.exists()) goto _L2; else goto _L1
_L1:
        if(file1.exists()) goto _L4; else goto _L3
_L3:
        if(file.renameTo(file1)) goto _L2; else goto _L5
_L5:
        Log.wtf("PackageManager", "Unable to backup user packages state file, current changes will be lost at reboot");
_L7:
        return;
_L4:
        file.delete();
        Slog.w("PackageManager", "Preserving older stopped packages backup");
_L2:
        FileOutputStream fileoutputstream;
        BufferedOutputStream bufferedoutputstream;
        FastXmlSerializer fastxmlserializer;
        Iterator iterator;
        fileoutputstream = new FileOutputStream(file);
        bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "package-restrictions");
        iterator = mPackages.values().iterator();
_L8:
        HashSet hashset1;
        PackageSetting packagesetting;
        do {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_633;
            packagesetting = (PackageSetting)iterator.next();
        } while(!packagesetting.getStopped(i) && !packagesetting.getNotLaunched(i) && packagesetting.getEnabled(i) == 0 && packagesetting.getEnabledComponents(i).size() <= 0 && packagesetting.getDisabledComponents(i).size() <= 0);
        fastxmlserializer.startTag(null, "pkg");
        fastxmlserializer.attribute(null, "name", ((PackageSettingBase) (packagesetting)).name);
        boolean flag = packagesetting.getStopped(i);
        boolean flag1 = packagesetting.getNotLaunched(i);
        int j = packagesetting.getEnabled(i);
        HashSet hashset = packagesetting.getEnabledComponents(i);
        hashset1 = packagesetting.getDisabledComponents(i);
        if(flag)
            fastxmlserializer.attribute(null, "stopped", "true");
        if(flag1)
            fastxmlserializer.attribute(null, "nl", "true");
        if(j != 0)
            fastxmlserializer.attribute(null, "enabled", Integer.toString(j));
        if(hashset.size() <= 0)
            break MISSING_BLOCK_LABEL_522;
        fastxmlserializer.startTag(null, "enabled-components");
        for(Iterator iterator2 = hashset.iterator(); iterator2.hasNext(); fastxmlserializer.endTag(null, "item")) {
            String s1 = (String)iterator2.next();
            fastxmlserializer.startTag(null, "item");
            fastxmlserializer.attribute(null, "name", s1);
        }

          goto _L6
        IOException ioexception;
        ioexception;
        Log.wtf("PackageManager", "Unable to write package manager user packages state,  current changes will be lost at reboot", ioexception);
        if(file.exists() && !file.delete())
            Log.i("PackageManager", (new StringBuilder()).append("Failed to clean up mangled file: ").append(mStoppedPackagesFilename).toString());
          goto _L7
_L6:
        fastxmlserializer.endTag(null, "enabled-components");
        if(hashset1.size() > 0) {
            fastxmlserializer.startTag(null, "disabled-components");
            for(Iterator iterator1 = hashset1.iterator(); iterator1.hasNext(); fastxmlserializer.endTag(null, "item")) {
                String s = (String)iterator1.next();
                fastxmlserializer.startTag(null, "item");
                fastxmlserializer.attribute(null, "name", s);
            }

            fastxmlserializer.endTag(null, "disabled-components");
        }
        fastxmlserializer.endTag(null, "pkg");
          goto _L8
        fastxmlserializer.endTag(null, "package-restrictions");
        fastxmlserializer.endDocument();
        bufferedoutputstream.flush();
        FileUtils.sync(fileoutputstream);
        bufferedoutputstream.close();
        file1.delete();
        FileUtils.setPermissions(file.toString(), 432, -1, -1);
          goto _L7
    }

    void writePermissionLPr(XmlSerializer xmlserializer, BasePermission basepermission) throws XmlPullParserException, IOException {
        if(basepermission.type != 1 && basepermission.sourcePackage != null) {
            xmlserializer.startTag(null, "item");
            xmlserializer.attribute(null, "name", basepermission.name);
            xmlserializer.attribute(null, "package", basepermission.sourcePackage);
            if(basepermission.protectionLevel != 0)
                xmlserializer.attribute(null, "protection", Integer.toString(basepermission.protectionLevel));
            if(basepermission.type == 2) {
                PermissionInfo permissioninfo;
                if(basepermission.perm != null)
                    permissioninfo = basepermission.perm.info;
                else
                    permissioninfo = basepermission.pendingInfo;
                if(permissioninfo != null) {
                    xmlserializer.attribute(null, "type", "dynamic");
                    if(permissioninfo.icon != 0)
                        xmlserializer.attribute(null, "icon", Integer.toString(permissioninfo.icon));
                    if(permissioninfo.nonLocalizedLabel != null)
                        xmlserializer.attribute(null, "label", permissioninfo.nonLocalizedLabel.toString());
                }
            }
            xmlserializer.endTag(null, "item");
        }
    }

    void writePreferredActivitiesLPr(XmlSerializer xmlserializer) throws IllegalArgumentException, IllegalStateException, IOException {
        xmlserializer.startTag(null, "preferred-activities");
        for(Iterator iterator = mPreferredActivities.filterSet().iterator(); iterator.hasNext(); xmlserializer.endTag(null, "item")) {
            PreferredActivity preferredactivity = (PreferredActivity)iterator.next();
            xmlserializer.startTag(null, "item");
            preferredactivity.writeToXml(xmlserializer);
        }

        xmlserializer.endTag(null, "preferred-activities");
    }

    private static final String ATTR_ENABLED = "enabled";
    private static final String ATTR_ENFORCEMENT = "enforcement";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_NOT_LAUNCHED = "nl";
    private static final String ATTR_STOPPED = "stopped";
    private static final boolean DEBUG_STOPPED = false;
    static final Object FLAG_DUMP_SPEC[];
    private static final String TAG = "PackageSettings";
    private static final String TAG_DISABLED_COMPONENTS = "disabled-components";
    private static final String TAG_ENABLED_COMPONENTS = "enabled-components";
    private static final String TAG_ITEM = "item";
    private static final String TAG_PACKAGE = "pkg";
    private static final String TAG_PACKAGE_RESTRICTIONS = "package-restrictions";
    private static final String TAG_READ_EXTERNAL_STORAGE = "read-external-storage";
    private final File mBackupSettingsFilename;
    private final File mBackupStoppedPackagesFilename;
    private final HashMap mDisabledSysPackages;
    int mExternalSdkPlatform;
    int mInternalSdkPlatform;
    private final SparseArray mOtherUserIds;
    private final File mPackageListFilename;
    final HashMap mPackages;
    final ArrayList mPackagesToBeCleaned;
    private final ArrayList mPastSignatures;
    private final ArrayList mPendingPackages;
    final HashMap mPermissionTrees;
    final HashMap mPermissions;
    final IntentResolver mPreferredActivities;
    Boolean mReadExternalStorageEnforced;
    final StringBuilder mReadMessages;
    final HashMap mRenamedPackages;
    private final File mSettingsFilename;
    final HashMap mSharedUsers;
    private final File mStoppedPackagesFilename;
    private final File mSystemDir;
    private final ArrayList mUserIds;
    private VerifierDeviceIdentity mVerifierDeviceIdentity;

    static  {
        Object aobj[] = new Object[36];
        aobj[0] = Integer.valueOf(1);
        aobj[1] = "SYSTEM";
        aobj[2] = Integer.valueOf(2);
        aobj[3] = "DEBUGGABLE";
        aobj[4] = Integer.valueOf(4);
        aobj[5] = "HAS_CODE";
        aobj[6] = Integer.valueOf(8);
        aobj[7] = "PERSISTENT";
        aobj[8] = Integer.valueOf(16);
        aobj[9] = "FACTORY_TEST";
        aobj[10] = Integer.valueOf(32);
        aobj[11] = "ALLOW_TASK_REPARENTING";
        aobj[12] = Integer.valueOf(64);
        aobj[13] = "ALLOW_CLEAR_USER_DATA";
        aobj[14] = Integer.valueOf(128);
        aobj[15] = "UPDATED_SYSTEM_APP";
        aobj[16] = Integer.valueOf(256);
        aobj[17] = "TEST_ONLY";
        aobj[18] = Integer.valueOf(16384);
        aobj[19] = "VM_SAFE_MODE";
        aobj[20] = Integer.valueOf(32768);
        aobj[21] = "ALLOW_BACKUP";
        aobj[22] = Integer.valueOf(0x10000);
        aobj[23] = "KILL_AFTER_RESTORE";
        aobj[24] = Integer.valueOf(0x20000);
        aobj[25] = "RESTORE_ANY_VERSION";
        aobj[26] = Integer.valueOf(0x40000);
        aobj[27] = "EXTERNAL_STORAGE";
        aobj[28] = Integer.valueOf(0x100000);
        aobj[29] = "LARGE_HEAP";
        aobj[30] = Integer.valueOf(0x200000);
        aobj[31] = "STOPPED";
        aobj[32] = Integer.valueOf(0x20000000);
        aobj[33] = "FORWARD_LOCK";
        aobj[34] = Integer.valueOf(0x10000000);
        aobj[35] = "CANT_SAVE_STATE";
        FLAG_DUMP_SPEC = aobj;
    }
}
