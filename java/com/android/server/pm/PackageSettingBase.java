// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.util.SparseArray;
import android.util.SparseIntArray;
import java.io.File;
import java.util.HashSet;

// Referenced classes of package com.android.server.pm:
//            GrantedPermissions, PackageSignatures

class PackageSettingBase extends GrantedPermissions {

    PackageSettingBase(PackageSettingBase packagesettingbase) {
        super(packagesettingbase);
        signatures = new PackageSignatures();
        stopped = new SparseArray();
        notLaunched = new SparseArray();
        disabledComponents = new SparseArray();
        enabledComponents = new SparseArray();
        enabled = new SparseIntArray();
        installStatus = 1;
        name = packagesettingbase.name;
        realName = packagesettingbase.realName;
        codePath = packagesettingbase.codePath;
        codePathString = packagesettingbase.codePathString;
        resourcePath = packagesettingbase.resourcePath;
        resourcePathString = packagesettingbase.resourcePathString;
        nativeLibraryPathString = packagesettingbase.nativeLibraryPathString;
        timeStamp = packagesettingbase.timeStamp;
        firstInstallTime = packagesettingbase.firstInstallTime;
        lastUpdateTime = packagesettingbase.lastUpdateTime;
        versionCode = packagesettingbase.versionCode;
        uidError = packagesettingbase.uidError;
        signatures = new PackageSignatures(packagesettingbase.signatures);
        permissionsFixed = packagesettingbase.permissionsFixed;
        haveGids = packagesettingbase.haveGids;
        notLaunched = packagesettingbase.notLaunched;
        disabledComponents = packagesettingbase.disabledComponents.clone();
        enabledComponents = packagesettingbase.enabledComponents.clone();
        enabled = packagesettingbase.enabled.clone();
        stopped = packagesettingbase.stopped.clone();
        installStatus = packagesettingbase.installStatus;
        origPackage = packagesettingbase.origPackage;
        installerPackageName = packagesettingbase.installerPackageName;
    }

    PackageSettingBase(String s, String s1, File file, File file1, String s2, int i, int j) {
        super(j);
        signatures = new PackageSignatures();
        stopped = new SparseArray();
        notLaunched = new SparseArray();
        disabledComponents = new SparseArray();
        enabledComponents = new SparseArray();
        enabled = new SparseIntArray();
        installStatus = 1;
        name = s;
        realName = s1;
        init(file, file1, s2, i);
    }

    private HashSet getComponentHashSet(SparseArray sparsearray, int i) {
        HashSet hashset = (HashSet)sparsearray.get(i);
        if(hashset == null) {
            hashset = new HashSet(1);
            sparsearray.put(i, hashset);
        }
        return hashset;
    }

    void addDisabledComponent(String s, int i) {
        getComponentHashSet(disabledComponents, i).add(s);
    }

    void addEnabledComponent(String s, int i) {
        getComponentHashSet(enabledComponents, i).add(s);
    }

    public void copyFrom(PackageSettingBase packagesettingbase) {
        super.grantedPermissions = ((GrantedPermissions) (packagesettingbase)).grantedPermissions;
        super.gids = ((GrantedPermissions) (packagesettingbase)).gids;
        timeStamp = packagesettingbase.timeStamp;
        firstInstallTime = packagesettingbase.firstInstallTime;
        lastUpdateTime = packagesettingbase.lastUpdateTime;
        signatures = packagesettingbase.signatures;
        permissionsFixed = packagesettingbase.permissionsFixed;
        haveGids = packagesettingbase.haveGids;
        stopped = packagesettingbase.stopped;
        notLaunched = packagesettingbase.notLaunched;
        disabledComponents = packagesettingbase.disabledComponents;
        enabledComponents = packagesettingbase.enabledComponents;
        enabled = packagesettingbase.enabled;
        installStatus = packagesettingbase.installStatus;
    }

    boolean disableComponentLPw(String s, int i) {
        HashSet hashset = getComponentHashSet(disabledComponents, i);
        return getComponentHashSet(enabledComponents, i).remove(s) | hashset.add(s);
    }

    boolean enableComponentLPw(String s, int i) {
        HashSet hashset = getComponentHashSet(disabledComponents, i);
        HashSet hashset1 = getComponentHashSet(enabledComponents, i);
        return hashset.remove(s) | hashset1.add(s);
    }

    int getCurrentEnabledStateLPr(String s, int i) {
        HashSet hashset = getComponentHashSet(disabledComponents, i);
        int j;
        if(getComponentHashSet(enabledComponents, i).contains(s))
            j = 1;
        else
        if(hashset.contains(s))
            j = 2;
        else
            j = 0;
        return j;
    }

    HashSet getDisabledComponents(int i) {
        return getComponentHashSet(disabledComponents, i);
    }

    int getEnabled(int i) {
        return enabled.get(i, 0);
    }

    HashSet getEnabledComponents(int i) {
        return getComponentHashSet(enabledComponents, i);
    }

    public int getInstallStatus() {
        return installStatus;
    }

    String getInstallerPackageName() {
        return installerPackageName;
    }

    boolean getNotLaunched(int i) {
        return ((Boolean)notLaunched.get(i, Boolean.valueOf(false))).booleanValue();
    }

    boolean getStopped(int i) {
        return ((Boolean)stopped.get(i, Boolean.valueOf(false))).booleanValue();
    }

    void init(File file, File file1, String s, int i) {
        codePath = file;
        codePathString = file.toString();
        resourcePath = file1;
        resourcePathString = file1.toString();
        nativeLibraryPathString = s;
        versionCode = i;
    }

    void removeUser(int i) {
        enabled.delete(i);
        stopped.delete(i);
        enabledComponents.delete(i);
        disabledComponents.delete(i);
        notLaunched.delete(i);
    }

    boolean restoreComponentLPw(String s, int i) {
        HashSet hashset = getComponentHashSet(disabledComponents, i);
        return getComponentHashSet(enabledComponents, i).remove(s) | hashset.remove(s);
    }

    void setDisabledComponents(HashSet hashset, int i) {
        disabledComponents.put(i, hashset);
    }

    void setEnabled(int i, int j) {
        enabled.put(j, i);
    }

    void setEnabledComponents(HashSet hashset, int i) {
        enabledComponents.put(i, hashset);
    }

    public void setInstallStatus(int i) {
        installStatus = i;
    }

    public void setInstallerPackageName(String s) {
        installerPackageName = s;
    }

    void setNotLaunched(boolean flag, int i) {
        notLaunched.put(i, Boolean.valueOf(flag));
    }

    void setStopped(boolean flag, int i) {
        stopped.put(i, Boolean.valueOf(flag));
    }

    public void setTimeStamp(long l) {
        timeStamp = l;
    }

    static final int PKG_INSTALL_COMPLETE = 1;
    static final int PKG_INSTALL_INCOMPLETE;
    File codePath;
    String codePathString;
    private SparseArray disabledComponents;
    private SparseIntArray enabled;
    private SparseArray enabledComponents;
    long firstInstallTime;
    boolean haveGids;
    int installStatus;
    String installerPackageName;
    long lastUpdateTime;
    final String name;
    String nativeLibraryPathString;
    private SparseArray notLaunched;
    PackageSettingBase origPackage;
    boolean permissionsFixed;
    final String realName;
    File resourcePath;
    String resourcePathString;
    PackageSignatures signatures;
    private SparseArray stopped;
    long timeStamp;
    boolean uidError;
    int versionCode;
}
