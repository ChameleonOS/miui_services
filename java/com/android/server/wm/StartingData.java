// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.res.CompatibilityInfo;

final class StartingData {

    StartingData(String s, int i, CompatibilityInfo compatibilityinfo, CharSequence charsequence, int j, int k, int l) {
        pkg = s;
        theme = i;
        compatInfo = compatibilityinfo;
        nonLocalizedLabel = charsequence;
        labelRes = j;
        icon = k;
        windowFlags = l;
    }

    final CompatibilityInfo compatInfo;
    final int icon;
    final int labelRes;
    final CharSequence nonLocalizedLabel;
    final String pkg;
    final int theme;
    final int windowFlags;
}
