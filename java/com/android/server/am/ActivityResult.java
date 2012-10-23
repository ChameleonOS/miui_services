// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.ResultInfo;
import android.content.Intent;

// Referenced classes of package com.android.server.am:
//            ActivityRecord

class ActivityResult extends ResultInfo {

    public ActivityResult(ActivityRecord activityrecord, String s, int i, int j, Intent intent) {
        super(s, i, j, intent);
        mFrom = activityrecord;
    }

    final ActivityRecord mFrom;
}
