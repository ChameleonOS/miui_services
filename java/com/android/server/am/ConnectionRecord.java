// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.am:
//            AppBindRecord, ServiceRecord, ActivityRecord

class ConnectionRecord {

    ConnectionRecord(AppBindRecord appbindrecord, ActivityRecord activityrecord, IServiceConnection iserviceconnection, int i, int j, PendingIntent pendingintent) {
        binding = appbindrecord;
        activity = activityrecord;
        conn = iserviceconnection;
        flags = i;
        clientLabel = j;
        clientIntent = pendingintent;
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.println((new StringBuilder()).append(s).append("binding=").append(binding).toString());
        if(activity != null)
            printwriter.println((new StringBuilder()).append(s).append("activity=").append(activity).toString());
        printwriter.println((new StringBuilder()).append(s).append("conn=").append(conn.asBinder()).append(" flags=0x").append(Integer.toHexString(flags)).toString());
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ConnectionRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            if(serviceDead)
                stringbuilder.append("DEAD ");
            stringbuilder.append(binding.service.shortName);
            stringbuilder.append(":@");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(conn.asBinder())));
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    final ActivityRecord activity;
    final AppBindRecord binding;
    final PendingIntent clientIntent;
    final int clientLabel;
    final IServiceConnection conn;
    final int flags;
    boolean serviceDead;
    String stringName;
}
