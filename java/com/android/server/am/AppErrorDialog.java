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
//            BaseErrorDialog, ProcessRecord, AppErrorResult, MiuiErrorReport

class AppErrorDialog extends BaseErrorDialog {
    static class Injector {

        static void sendFcReport(AppErrorDialog apperrordialog, Message message) {
            boolean flag = true;
            if(apperrordialog.getProc() != null && apperrordialog.mCrashInfo != null) {
                Context context = apperrordialog.getContext();
                ProcessRecord processrecord = apperrordialog.getProc();
                android.app.ApplicationErrorReport.CrashInfo crashinfo = apperrordialog.mCrashInfo;
                if(message.what != flag)
                    flag = false;
                MiuiErrorReport.sendFcErrorReport(context, processrecord, crashinfo, flag);
            }
        }

        Injector() {
        }
    }


    public AppErrorDialog(Context context, AppErrorResult apperrorresult, ProcessRecord processrecord) {
        Resources resources;
        super(context);
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                Injector.sendFcReport(AppErrorDialog.this, message);
                synchronized(mProc) {
                    if(mProc != null && mProc.crashDialog == AppErrorDialog.this)
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

            final AppErrorDialog this$0;

             {
                this$0 = AppErrorDialog.this;
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
        setMessage(resources.getString(0x10403d0, aobj1));
_L5:
        setCancelable(false);
        setButton(-1, resources.getText(0x10403d7), mHandler.obtainMessage(0));
        if(processrecord.errorReportReceiver != null)
            setButton(-2, resources.getText(0x10403d8), mHandler.obtainMessage(1));
        setTitle(resources.getText(0x10403cf));
        getWindow().addFlags(0x40000000);
        getWindow().setTitle((new StringBuilder()).append("Application Error: ").append(processrecord.info.processName).toString());
        if(processrecord.persistent)
            getWindow().setType(2010);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 0x493e0L);
        return;
_L2:
        String s = processrecord.processName;
        Object aobj[] = new Object[1];
        aobj[0] = s.toString();
        setMessage(resources.getString(0x10403d1, aobj));
        if(true) goto _L5; else goto _L4
_L4:
    }

    public AppErrorDialog(Context context, AppErrorResult apperrorresult, ProcessRecord processrecord, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        this(context, apperrorresult, processrecord);
        mCrashInfo = crashinfo;
        setButton(-2, (new StringBuilder()).append(context.getResources().getText(0x10403d8)).append(" MIUI").toString(), mHandler.obtainMessage(1));
    }

    ProcessRecord getProc() {
        return mProc;
    }

    static final long DISMISS_TIMEOUT = 0x493e0L;
    static final int FORCE_QUIT = 0;
    static final int FORCE_QUIT_AND_REPORT = 1;
    private static final String TAG = "AppErrorDialog";
    android.app.ApplicationErrorReport.CrashInfo mCrashInfo;
    private final Handler mHandler;
    private final ProcessRecord mProc;
    private final AppErrorResult mResult;


}
