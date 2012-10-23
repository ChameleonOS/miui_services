// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.os.RecoverySystem;
import android.util.Log;
import android.util.Slog;
import java.io.IOException;

public class MasterClearReceiver extends BroadcastReceiver {

    public MasterClearReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE") && !"google.com".equals(intent.getStringExtra("from"))) {
            Slog.w("MasterClear", "Ignoring master clear request -- not from trusted server.");
        } else {
            Slog.w("MasterClear", "!!! FACTORY RESET !!!");
            (new Thread(context) {

                public void run() {
                    RecoverySystem.rebootWipeUserData(context);
                    Log.wtf("MasterClear", "Still running after master clear?!");
_L1:
                    return;
                    IOException ioexception;
                    ioexception;
                    Slog.e("MasterClear", "Can't perform master clear/factory reset", ioexception);
                      goto _L1
                }

                final MasterClearReceiver this$0;
                final Context val$context;

             {
                this$0 = MasterClearReceiver.this;
                context = context1;
                super(final_s);
            }
            }).start();
        }
    }

    private static final String TAG = "MasterClear";
}
