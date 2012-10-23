// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

// Referenced classes of package com.android.server.am:
//            ActivityRecord

public class LaunchWarningWindow extends Dialog {

    public LaunchWarningWindow(Context context, ActivityRecord activityrecord, ActivityRecord activityrecord1) {
        super(context, 0x10302f4);
        requestWindowFeature(3);
        getWindow().setType(2003);
        getWindow().addFlags(24);
        setContentView(0x109005d);
        setTitle(context.getText(0x10403db));
        TypedValue typedvalue = new TypedValue();
        getContext().getTheme().resolveAttribute(0x1010355, typedvalue, true);
        getWindow().setFeatureDrawableResource(3, typedvalue.resourceId);
        ((ImageView)findViewById(0x10202cb)).setImageDrawable(activityrecord1.info.applicationInfo.loadIcon(context.getPackageManager()));
        TextView textview = (TextView)findViewById(0x10202cc);
        Resources resources = context.getResources();
        Object aobj[] = new Object[1];
        aobj[0] = activityrecord1.info.applicationInfo.loadLabel(context.getPackageManager()).toString();
        textview.setText(resources.getString(0x10403dc, aobj));
        ((ImageView)findViewById(0x10202cd)).setImageDrawable(activityrecord.info.applicationInfo.loadIcon(context.getPackageManager()));
        TextView textview1 = (TextView)findViewById(0x10202ce);
        Resources resources1 = context.getResources();
        Object aobj1[] = new Object[1];
        aobj1[0] = activityrecord.info.applicationInfo.loadLabel(context.getPackageManager()).toString();
        textview1.setText(resources1.getString(0x10403dd, aobj1));
    }
}
