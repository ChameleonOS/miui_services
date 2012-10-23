// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.PermissionInfo;

// Referenced classes of package com.android.server.pm:
//            PackageSettingBase

final class BasePermission {

    BasePermission(String s, String s1, int i) {
        name = s;
        sourcePackage = s1;
        type = i;
        protectionLevel = 2;
    }

    public String toString() {
        return (new StringBuilder()).append("BasePermission{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(name).append("}").toString();
    }

    static final int TYPE_BUILTIN = 1;
    static final int TYPE_DYNAMIC = 2;
    static final int TYPE_NORMAL;
    int gids[];
    final String name;
    PackageSettingBase packageSetting;
    PermissionInfo pendingInfo;
    android.content.pm.PackageParser.Permission perm;
    int protectionLevel;
    String sourcePackage;
    final int type;
    int uid;
}
