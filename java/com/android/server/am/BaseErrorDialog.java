// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;

class BaseErrorDialog extends AlertDialog {

    public BaseErrorDialog(Context context) {
        super(context, 0x60d0020);
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                if(message.what == 0) {
                    mConsuming = false;
                    setEnabled(true);
                }
            }

            final BaseErrorDialog this$0;

             {
                this$0 = BaseErrorDialog.this;
                super();
            }
        };
        mConsuming = true;
        getWindow().setType(2003);
        getWindow().setFlags(0x20000, 0x20000);
        getWindow().setTitle("Error Dialog");
        setIconAttribute(0x1010355);
    }

    private void setEnabled(boolean flag) {
        Button button = (Button)findViewById(0x1020019);
        if(button != null)
            button.setEnabled(flag);
        Button button1 = (Button)findViewById(0x102001a);
        if(button1 != null)
            button1.setEnabled(flag);
        Button button2 = (Button)findViewById(0x102001b);
        if(button2 != null)
            button2.setEnabled(flag);
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent) {
        boolean flag;
        if(mConsuming)
            flag = true;
        else
            flag = super.dispatchKeyEvent(keyevent);
        return flag;
    }

    public void onStart() {
        super.onStart();
        setEnabled(false);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 1000L);
    }

    private boolean mConsuming;
    private Handler mHandler;


/*
    static boolean access$002(BaseErrorDialog baseerrordialog, boolean flag) {
        baseerrordialog.mConsuming = flag;
        return flag;
    }

*/

}
