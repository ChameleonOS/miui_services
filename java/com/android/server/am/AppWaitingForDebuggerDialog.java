// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

// Referenced classes of package com.android.server.am:
//            BaseErrorDialog, ProcessRecord, ActivityManagerService

class AppWaitingForDebuggerDialog extends BaseErrorDialog {

    public AppWaitingForDebuggerDialog(ActivityManagerService activitymanagerservice, Context context, ProcessRecord processrecord) {
        super(context);
        mService = activitymanagerservice;
        mProc = processrecord;
        mAppName = context.getPackageManager().getApplicationLabel(processrecord.info);
        setCancelable(false);
        StringBuilder stringbuilder = new StringBuilder();
        if(mAppName != null && mAppName.length() > 0) {
            stringbuilder.append("Application ");
            stringbuilder.append(mAppName);
            stringbuilder.append(" (process ");
            stringbuilder.append(processrecord.processName);
            stringbuilder.append(")");
        } else {
            stringbuilder.append("Process ");
            stringbuilder.append(processrecord.processName);
        }
        stringbuilder.append(" is waiting for the debugger to attach.");
        setMessage(stringbuilder.toString());
        setButton(-1, "Force Close", mHandler.obtainMessage(1, processrecord));
        setTitle("Waiting For Debugger");
        getWindow().setTitle((new StringBuilder()).append("Waiting For Debugger: ").append(processrecord.info.processName).toString());
    }

    public void onStop() {
    }

    private CharSequence mAppName;
    private final Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 1: default 24
        //                       1 25;
               goto _L1 _L2
_L1:
            return;
_L2:
            mService.killAppAtUsersRequest(mProc, AppWaitingForDebuggerDialog.this);
            if(true) goto _L1; else goto _L3
_L3:
        }

        final AppWaitingForDebuggerDialog this$0;

             {
                this$0 = AppWaitingForDebuggerDialog.this;
                super();
            }
    };
    final ProcessRecord mProc;
    final ActivityManagerService mService;
}
