// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import java.util.HashSet;

// Referenced classes of package com.android.server.pm:
//            GrantedPermissions, PackageSignatures

final class SharedUserSetting extends GrantedPermissions {

    SharedUserSetting(String s, int i) {
        super(i);
        name = s;
    }

    public String toString() {
        return (new StringBuilder()).append("SharedUserSetting{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(name).append("/").append(userId).append("}").toString();
    }

    final String name;
    final HashSet packages = new HashSet();
    final PackageSignatures signatures = new PackageSignatures();
    int userId;
}
