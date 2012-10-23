// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

// Referenced classes of package com.android.server.am:
//            BaseErrorDialog

class FactoryErrorDialog extends BaseErrorDialog {

    public FactoryErrorDialog(Context context, CharSequence charsequence) {
        super(context);
        setCancelable(false);
        setTitle(context.getText(0x1040337));
        setMessage(charsequence);
        setButton(-1, context.getText(0x104033a), mHandler.obtainMessage(0));
        getWindow().setTitle("Factory Error");
    }

    public void onStop() {
    }

    private final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            throw new RuntimeException("Rebooting from failed factory test");
        }

        final FactoryErrorDialog this$0;

             {
                this$0 = FactoryErrorDialog.this;
                super();
            }
    };
}
