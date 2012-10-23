// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.Window;
import android.widget.*;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService, CompatModePackages

public class CompatModeDialog extends Dialog {

    public CompatModeDialog(ActivityManagerService activitymanagerservice, Context context, ApplicationInfo applicationinfo) {
        super(context, 0x1030070);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().requestFeature(1);
        getWindow().setType(2002);
        getWindow().setGravity(81);
        mService = activitymanagerservice;
        mAppInfo = applicationinfo;
        setContentView(0x1090028);
        mCompatEnabled.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) {
                ActivityManagerService activitymanagerservice1 = mService;
                activitymanagerservice1;
                JVM INSTR monitorenter ;
                CompatModePackages compatmodepackages = mService.mCompatModePackages;
                String s = mAppInfo.packageName;
                int i;
                if(mCompatEnabled.isChecked())
                    i = 1;
                else
                    i = 0;
                compatmodepackages.setPackageScreenCompatModeLocked(s, i);
                updateControls();
                return;
            }

            final CompatModeDialog this$0;

             {
                this$0 = CompatModeDialog.this;
                super();
            }
        });
        mAlwaysShow.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) {
                ActivityManagerService activitymanagerservice1 = mService;
                activitymanagerservice1;
                JVM INSTR monitorenter ;
                mService.mCompatModePackages.setPackageAskCompatModeLocked(mAppInfo.packageName, mAlwaysShow.isChecked());
                updateControls();
                return;
            }

            final CompatModeDialog this$0;

             {
                this$0 = CompatModeDialog.this;
                super();
            }
        });
        updateControls();
    }

    void updateControls() {
        boolean flag;
        byte byte0;
        flag = true;
        byte0 = 0;
        ActivityManagerService activitymanagerservice = mService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        int i = mService.mCompatModePackages.computeCompatModeLocked(mAppInfo);
        Switch switch1 = mCompatEnabled;
        boolean flag1;
        View view;
        if(i != flag)
            flag = false;
        switch1.setChecked(flag);
        flag1 = mService.mCompatModePackages.getPackageAskCompatModeLocked(mAppInfo.packageName);
        mAlwaysShow.setChecked(flag1);
        view = mHint;
        if(flag1)
            byte0 = 4;
        view.setVisibility(byte0);
        return;
    }

    final CheckBox mAlwaysShow = (CheckBox)findViewById(0x102025b);
    final ApplicationInfo mAppInfo;
    final Switch mCompatEnabled = (Switch)findViewById(0x102025a);
    final View mHint = findViewById(0x102025c);
    final ActivityManagerService mService;
}
