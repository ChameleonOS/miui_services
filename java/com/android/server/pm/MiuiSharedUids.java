// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;


// Referenced classes of package com.android.server.pm:
//            Settings

public class MiuiSharedUids {

    public MiuiSharedUids() {
    }

    public static void add(Settings settings, boolean flag) {
        char c = '\u2710';
        char c1;
        char c2;
        if(flag)
            c1 = '\u2648';
        else
            c1 = c;
        settings.addSharedUserLPw("android.uid.backup", c1, 1);
        if(flag)
            c2 = '\u2649';
        else
            c2 = c;
        settings.addSharedUserLPw("android.uid.theme", c2, 1);
        if(flag)
            c = '\u264A';
        settings.addSharedUserLPw("android.uid.updater", c, 1);
    }
}
