// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.IntentFilter;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.am:
//            ReceiverList

class BroadcastFilter extends IntentFilter {

    BroadcastFilter(IntentFilter intentfilter, ReceiverList receiverlist, String s, String s1) {
        super(intentfilter);
        receiverList = receiverlist;
        packageName = s;
        requiredPermission = s1;
    }

    public void dump(PrintWriter printwriter, String s) {
        dumpInReceiverList(printwriter, new PrintWriterPrinter(printwriter), s);
        receiverList.dumpLocal(printwriter, s);
    }

    public void dumpBrief(PrintWriter printwriter, String s) {
        dumpBroadcastFilterState(printwriter, s);
    }

    void dumpBroadcastFilterState(PrintWriter printwriter, String s) {
        if(requiredPermission != null) {
            printwriter.print(s);
            printwriter.print("requiredPermission=");
            printwriter.println(requiredPermission);
        }
    }

    public void dumpInReceiverList(PrintWriter printwriter, Printer printer, String s) {
        super.dump(printer, s);
        dumpBroadcastFilterState(printwriter, s);
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("BroadcastFilter{");
        stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringbuilder.append(' ');
        stringbuilder.append(receiverList);
        stringbuilder.append('}');
        return stringbuilder.toString();
    }

    final String packageName;
    final ReceiverList receiverList;
    final String requiredPermission;
}
