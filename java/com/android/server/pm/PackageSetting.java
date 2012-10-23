// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import java.io.File;

// Referenced classes of package com.android.server.pm:
//            PackageSettingBase, SharedUserSetting

final class PackageSetting extends PackageSettingBase {

    PackageSetting(PackageSetting packagesetting) {
        super(packagesetting);
        appId = packagesetting.appId;
        pkg = packagesetting.pkg;
        sharedUser = packagesetting.sharedUser;
    }

    PackageSetting(String s, String s1, File file, File file1, String s2, int i, int j) {
        super(s, s1, file, file1, s2, i, j);
    }

    public String toString() {
        return (new StringBuilder()).append("PackageSetting{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(super.name).append("/").append(appId).append("}").toString();
    }

    int appId;
    android.content.pm.PackageParser.Package pkg;
    SharedUserSetting sharedUser;
}
