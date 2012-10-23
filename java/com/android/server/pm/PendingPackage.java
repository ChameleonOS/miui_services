// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import java.io.File;

// Referenced classes of package com.android.server.pm:
//            PackageSettingBase

final class PendingPackage extends PackageSettingBase {

    PendingPackage(String s, String s1, File file, File file1, String s2, int i, int j, 
            int k) {
        super(s, s1, file, file1, s2, j, k);
        sharedId = i;
    }

    final int sharedId;
}
