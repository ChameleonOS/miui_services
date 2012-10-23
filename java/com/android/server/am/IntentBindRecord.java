// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.Intent;
import android.os.IBinder;
import java.io.PrintWriter;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            AppBindRecord, ServiceRecord

class IntentBindRecord {

    IntentBindRecord(ServiceRecord servicerecord, android.content.Intent.FilterComparison filtercomparison) {
        service = servicerecord;
        intent = filtercomparison;
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("service=");
        printwriter.println(service);
        dumpInService(printwriter, s);
    }

    void dumpInService(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("intent={");
        printwriter.print(intent.getIntent().toShortString(false, true, false, false));
        printwriter.println('}');
        printwriter.print(s);
        printwriter.print("binder=");
        printwriter.println(binder);
        printwriter.print(s);
        printwriter.print("requested=");
        printwriter.print(requested);
        printwriter.print(" received=");
        printwriter.print(received);
        printwriter.print(" hasBound=");
        printwriter.print(hasBound);
        printwriter.print(" doRebind=");
        printwriter.println(doRebind);
        if(apps.size() > 0) {
            AppBindRecord appbindrecord;
            for(Iterator iterator = apps.values().iterator(); iterator.hasNext(); appbindrecord.dumpInIntentBind(printwriter, (new StringBuilder()).append(s).append("  ").toString())) {
                appbindrecord = (AppBindRecord)iterator.next();
                printwriter.print(s);
                printwriter.print("* Client AppBindRecord{");
                printwriter.print(Integer.toHexString(System.identityHashCode(appbindrecord)));
                printwriter.print(' ');
                printwriter.print(appbindrecord.client);
                printwriter.println('}');
            }

        }
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("IntentBindRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(service.shortName);
            stringbuilder.append(':');
            if(intent != null)
                intent.getIntent().toShortString(stringbuilder, false, false, false, false);
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    final HashMap apps = new HashMap();
    IBinder binder;
    boolean doRebind;
    boolean hasBound;
    final android.content.Intent.FilterComparison intent;
    boolean received;
    boolean requested;
    final ServiceRecord service;
    String stringName;
}
