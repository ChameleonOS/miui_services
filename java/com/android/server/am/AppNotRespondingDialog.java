// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.pm.*;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import android.view.Window;
import java.util.HashSet;

// Referenced classes of package com.android.server.am:
//            BaseErrorDialog, ActivityRecord, ProcessRecord, ActivityManagerService, 
//            MiuiErrorReport

class AppNotRespondingDialog extends BaseErrorDialog {

    public AppNotRespondingDialog(ActivityManagerService activitymanagerservice, Context context, ProcessRecord processrecord, ActivityRecord activityrecord) {
        Resources resources;
        Object obj;
        Object obj1;
        int i;
        String s;
        super(context);
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                android.content.Intent intent;
                MiuiErrorReport.sendAnrErrorReport(getContext(), mProc, false);
                intent = null;
                message.what;
                JVM INSTR tableswitch 1 3: default 52
            //                           1 68
            //                           2 92
            //                           3 92;
                   goto _L1 _L2 _L3 _L3
_L1:
                if(intent == null)
                    break MISSING_BLOCK_LABEL_67;
                getContext().startActivity(intent);
_L4:
                return;
_L2:
                mService.killAppAtUsersRequest(mProc, AppNotRespondingDialog.this);
                  goto _L1
_L3:
                ActivityManagerService activitymanagerservice1 = mService;
                activitymanagerservice1;
                JVM INSTR monitorenter ;
                ProcessRecord processrecord1 = mProc;
                if(message.what == 3)
                    intent = mService.createAppErrorIntentLocked(processrecord1, System.currentTimeMillis(), null);
                processrecord1.notResponding = false;
                processrecord1.notRespondingReport = null;
                if(processrecord1.anrDialog == AppNotRespondingDialog.this)
                    processrecord1.anrDialog = null;
                  goto _L1
                ActivityNotFoundException activitynotfoundexception;
                activitynotfoundexception;
                Slog.w("AppNotRespondingDialog", "bug report receiver dissappeared", activitynotfoundexception);
                  goto _L4
            }

            final AppNotRespondingDialog this$0;

             {
                this$0 = AppNotRespondingDialog.this;
                super();
            }
        };
        mService = activitymanagerservice;
        mProc = processrecord;
        resources = context.getResources();
        setCancelable(false);
        Object aobj1[];
        if(activityrecord != null)
            obj = activityrecord.info.loadLabel(context.getPackageManager());
        else
            obj = null;
        obj1 = null;
        if(processrecord.pkgList.size() != 1)
            break MISSING_BLOCK_LABEL_290;
        obj1 = context.getPackageManager().getApplicationLabel(processrecord.info);
        if(obj1 == null)
            break MISSING_BLOCK_LABEL_290;
        if(obj != null) {
            i = 0x10403d3;
        } else {
            obj = obj1;
            obj1 = processrecord.processName;
            i = 0x10403d5;
        }
_L1:
        if(obj1 != null) {
            aobj1 = new Object[2];
            aobj1[0] = obj.toString();
            aobj1[1] = obj1.toString();
            s = resources.getString(i, aobj1);
        } else {
            Object aobj[] = new Object[1];
            aobj[0] = obj.toString();
            s = resources.getString(i, aobj);
        }
        setMessage(s);
        setButton(-1, resources.getText(0x10403d7), mHandler.obtainMessage(1));
        setButton(-2, resources.getText(0x10403d9), mHandler.obtainMessage(2));
        if(processrecord.errorReportReceiver != null)
            setButton(-3, resources.getText(0x10403d8), mHandler.obtainMessage(3));
        setTitle(resources.getText(0x10403d2));
        getWindow().addFlags(0x40000000);
        getWindow().setTitle((new StringBuilder()).append("Application Not Responding: ").append(processrecord.info.processName).toString());
        return;
        if(obj != null) {
            obj1 = processrecord.processName;
            i = 0x10403d4;
        } else {
            obj = processrecord.processName;
            i = 0x10403d6;
        }
          goto _L1
    }

    public void onStop() {
    }

    static final int FORCE_CLOSE = 1;
    private static final String TAG = "AppNotRespondingDialog";
    static final int WAIT = 2;
    static final int WAIT_AND_REPORT = 3;
    private final Handler mHandler;
    private final ProcessRecord mProc;
    private final ActivityManagerService mService;


}
