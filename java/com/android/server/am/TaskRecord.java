// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.*;
import android.os.SystemClock;
import android.os.UserId;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.am:
//            ThumbnailHolder

class TaskRecord extends ThumbnailHolder {

    TaskRecord(int i, ActivityInfo activityinfo, Intent intent1) {
        taskId = i;
        affinity = activityinfo.taskAffinity;
        setIntent(intent1, activityinfo);
    }

    void dump(PrintWriter printwriter, String s) {
        if(numActivities != 0 || rootWasReset || userId != 0) {
            printwriter.print(s);
            printwriter.print("numActivities=");
            printwriter.print(numActivities);
            printwriter.print(" rootWasReset=");
            printwriter.print(rootWasReset);
            printwriter.print(" userId=");
            printwriter.println(userId);
        }
        if(affinity != null) {
            printwriter.print(s);
            printwriter.print("affinity=");
            printwriter.println(affinity);
        }
        if(intent != null) {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append(s);
            stringbuilder.append("intent={");
            intent.toShortString(stringbuilder, false, true, false, true);
            stringbuilder.append('}');
            printwriter.println(stringbuilder.toString());
        }
        if(affinityIntent != null) {
            StringBuilder stringbuilder1 = new StringBuilder(128);
            stringbuilder1.append(s);
            stringbuilder1.append("affinityIntent={");
            affinityIntent.toShortString(stringbuilder1, false, true, false, true);
            stringbuilder1.append('}');
            printwriter.println(stringbuilder1.toString());
        }
        if(origActivity != null) {
            printwriter.print(s);
            printwriter.print("origActivity=");
            printwriter.println(origActivity.flattenToShortString());
        }
        if(realActivity != null) {
            printwriter.print(s);
            printwriter.print("realActivity=");
            printwriter.println(realActivity.flattenToShortString());
        }
        if(!askedCompatMode) {
            printwriter.print(s);
            printwriter.print("askedCompatMode=");
            printwriter.println(askedCompatMode);
        }
        printwriter.print(s);
        printwriter.print("lastThumbnail=");
        printwriter.print(super.lastThumbnail);
        printwriter.print(" lastDescription=");
        printwriter.println(super.lastDescription);
        printwriter.print(s);
        printwriter.print("lastActiveTime=");
        printwriter.print(lastActiveTime);
        printwriter.print(" (inactive for ");
        printwriter.print(getInactiveDuration() / 1000L);
        printwriter.println("s)");
    }

    long getInactiveDuration() {
        return SystemClock.elapsedRealtime() - lastActiveTime;
    }

    void setIntent(Intent intent1, ActivityInfo activityinfo) {
        stringName = null;
        if(activityinfo.targetActivity == null) {
            if(intent1 != null && (intent1.getSelector() != null || intent1.getSourceBounds() != null)) {
                Intent intent3 = new Intent(intent1);
                intent3.setSelector(null);
                intent3.setSourceBounds(null);
                intent1 = intent3;
            }
            intent = intent1;
            ComponentName componentname1;
            if(intent1 != null)
                componentname1 = intent1.getComponent();
            else
                componentname1 = null;
            realActivity = componentname1;
            origActivity = null;
        } else {
            ComponentName componentname = new ComponentName(((ComponentInfo) (activityinfo)).packageName, activityinfo.targetActivity);
            if(intent1 != null) {
                Intent intent2 = new Intent(intent1);
                intent2.setComponent(componentname);
                intent2.setSelector(null);
                intent2.setSourceBounds(null);
                intent = intent2;
                realActivity = componentname;
                origActivity = intent1.getComponent();
            } else {
                intent = null;
                realActivity = componentname;
                origActivity = new ComponentName(((ComponentInfo) (activityinfo)).packageName, ((ComponentInfo) (activityinfo)).name);
            }
        }
        if(intent != null && (0x200000 & intent.getFlags()) != 0)
            rootWasReset = true;
        if(activityinfo.applicationInfo != null)
            userId = UserId.getUserId(activityinfo.applicationInfo.uid);
    }

    public String toString() {
        if(stringName == null) goto _L2; else goto _L1
_L1:
        String s = stringName;
_L4:
        return s;
_L2:
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder(128);
        stringbuilder.append("TaskRecord{");
        stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringbuilder.append(" #");
        stringbuilder.append(taskId);
        if(affinity == null)
            break; /* Loop/switch isn't completed */
        stringbuilder.append(" A ");
        stringbuilder.append(affinity);
_L5:
        stringbuilder.append(" U ");
        stringbuilder.append(userId);
        stringbuilder.append('}');
        s = stringbuilder.toString();
        stringName = s;
        if(true) goto _L4; else goto _L3
_L3:
        if(intent != null) {
            stringbuilder.append(" I ");
            stringbuilder.append(intent.getComponent().flattenToShortString());
        } else
        if(affinityIntent != null) {
            stringbuilder.append(" aI ");
            stringbuilder.append(affinityIntent.getComponent().flattenToShortString());
        } else {
            stringbuilder.append(" ??");
        }
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    void touchActiveTime() {
        lastActiveTime = SystemClock.elapsedRealtime();
    }

    final String affinity;
    Intent affinityIntent;
    boolean askedCompatMode;
    Intent intent;
    long lastActiveTime;
    int numActivities;
    ComponentName origActivity;
    ComponentName realActivity;
    boolean rootWasReset;
    String stringName;
    final int taskId;
    int userId;
}
