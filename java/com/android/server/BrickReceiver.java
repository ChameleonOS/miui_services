// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.os.SystemService;
import android.util.Slog;

public class BrickReceiver extends BroadcastReceiver {

    public BrickReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Slog.w("BrickReceiver", "!!! BRICKING DEVICE !!!");
        SystemService.start("brick");
    }
}
