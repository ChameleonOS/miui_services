// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

// Referenced classes of package com.android.server.am:
//            ConnectionRecord, ServiceRecord, ProcessRecord, IntentBindRecord

class AppBindRecord {

    AppBindRecord(ServiceRecord servicerecord, IntentBindRecord intentbindrecord, ProcessRecord processrecord) {
        service = servicerecord;
        intent = intentbindrecord;
        client = processrecord;
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.println((new StringBuilder()).append(s).append("service=").append(service).toString());
        printwriter.println((new StringBuilder()).append(s).append("client=").append(client).toString());
        dumpInIntentBind(printwriter, s);
    }

    void dumpInIntentBind(PrintWriter printwriter, String s) {
        if(connections.size() > 0) {
            printwriter.println((new StringBuilder()).append(s).append("Per-process Connections:").toString());
            ConnectionRecord connectionrecord;
            for(Iterator iterator = connections.iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append(s).append("  ").append(connectionrecord).toString()))
                connectionrecord = (ConnectionRecord)iterator.next();

        }
    }

    public String toString() {
        return (new StringBuilder()).append("AppBindRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(service.shortName).append(":").append(client.processName).append("}").toString();
    }

    final ProcessRecord client;
    final HashSet connections = new HashSet();
    final IntentBindRecord intent;
    final ServiceRecord service;
}
