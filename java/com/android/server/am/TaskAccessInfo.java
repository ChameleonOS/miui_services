// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.graphics.Bitmap;
import java.util.ArrayList;

// Referenced classes of package com.android.server.am:
//            ActivityRecord

final class TaskAccessInfo extends android.app.ActivityManager.TaskThumbnails {
    static final class SubTask {

        ActivityRecord activity;
        int index;
        Bitmap thumbnail;

        SubTask() {
        }
    }


    TaskAccessInfo() {
    }

    public ActivityRecord root;
    public int rootIndex;
    public ArrayList subtasks;
}
