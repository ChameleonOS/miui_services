// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import java.util.HashSet;

// Referenced classes of package com.android.server.am:
//            BaseErrorDialog, ProcessRecord, AppErrorResult

class StrictModeViolationDialog extends BaseErrorDialog {

    public StrictModeViolationDialog(Context context, AppErrorResult apperrorresult, ProcessRecord processrecord) {
        Resources resources;
        super(context);
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                synchronized(mProc) {
                    if(mProc != null && mProc.crashDialog == StrictModeViolationDialog.this)
                        mProc.crashDialog = null;
                }
                mResult.set(message.what);
                dismiss();
                return;
                exception;
                processrecord1;
                JVM INSTR monitorexit ;
                throw exception;
            }

            final StrictModeViolationDialog this$0;

             {
                this$0 = StrictModeViolationDialog.this;
                super();
            }
        };
        resources = context.getResources();
        mProc = processrecord;
        mResult = apperrorresult;
        if(processrecord.pkgList.size() != 1) goto _L2; else goto _L1
_L1:
        CharSequence charsequence = context.getPackageManager().getApplicationLabel(processrecord.info);
        if(charsequence == null) goto _L2; else goto _L3
_L3:
        Object aobj1[] = new Object[2];
        aobj1[0] = charsequence.toString();
        aobj1[1] = processrecord.info.processName;
        setMessage(resources.getString(0x10403e1, aobj1));
_L5:
        setCancelable(false);
        setButton(-1, resources.getText(0x104043b), mHandler.obtainMessage(0));
        if(processrecord.errorReportReceiver != null)
            setButton(-2, resources.getText(0x10403d8), mHandler.obtainMessage(1));
        setTitle(resources.getText(0x10403cf));
        getWindow().addFlags(0x40000000);
        getWindow().setTitle((new StringBuilder()).append("Strict Mode Violation: ").append(processrecord.info.processName).toString());
        mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 60000L);
        return;
_L2:
        String s = processrecord.processName;
        Object aobj[] = new Object[1];
        aobj[0] = s.toString();
        setMessage(resources.getString(0x10403e2, aobj));
        if(true) goto _L5; else goto _L4
_L4:
    }

    static final int ACTION_OK = 0;
    static final int ACTION_OK_AND_REPORT = 1;
    static final long DISMISS_TIMEOUT = 60000L;
    private static final String TAG = "StrictModeViolationDialog";
    private final Handler mHandler;
    private final ProcessRecord mProc;
    private final AppErrorResult mResult;


}
