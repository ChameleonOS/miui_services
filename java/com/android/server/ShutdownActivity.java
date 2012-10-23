// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Slog;
import com.android.server.pm.ShutdownThread;

public class ShutdownActivity extends Activity {

    public ShutdownActivity() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        mReboot = "android.intent.action.REBOOT".equals(intent.getAction());
        mConfirm = intent.getBooleanExtra("android.intent.extra.KEY_CONFIRM", false);
        Slog.i("ShutdownActivity", (new StringBuilder()).append("onCreate(): confirm=").append(mConfirm).toString());
        (new Handler()).post(new Runnable() {

            public void run() {
                if(mReboot)
                    ShutdownThread.reboot(ShutdownActivity.this, null, mConfirm);
                else
                    ShutdownThread.shutdown(ShutdownActivity.this, mConfirm);
            }

            final ShutdownActivity this$0;

             {
                this$0 = ShutdownActivity.this;
                super();
            }
        });
    }

    private static final String TAG = "ShutdownActivity";
    private boolean mConfirm;
    private boolean mReboot;


}
