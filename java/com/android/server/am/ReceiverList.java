// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.IIntentReceiver;
import android.os.Binder;
import android.util.PrintWriterPrinter;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService, BroadcastFilter, ProcessRecord, BroadcastRecord

class ReceiverList extends ArrayList
    implements android.os.IBinder.DeathRecipient {

    ReceiverList(ActivityManagerService activitymanagerservice, ProcessRecord processrecord, int i, int j, IIntentReceiver iintentreceiver) {
        curBroadcast = null;
        linkedToDeath = false;
        owner = activitymanagerservice;
        receiver = iintentreceiver;
        app = processrecord;
        pid = i;
        uid = j;
    }

    public void binderDied() {
        linkedToDeath = false;
        owner.unregisterReceiver(receiver);
    }

    void dump(PrintWriter printwriter, String s) {
        PrintWriterPrinter printwriterprinter = new PrintWriterPrinter(printwriter);
        dumpLocal(printwriter, s);
        String s1 = (new StringBuilder()).append(s).append("  ").toString();
        int i = size();
        for(int j = 0; j < i; j++) {
            BroadcastFilter broadcastfilter = (BroadcastFilter)get(j);
            printwriter.print(s);
            printwriter.print("Filter #");
            printwriter.print(j);
            printwriter.print(": BroadcastFilter{");
            printwriter.print(Integer.toHexString(System.identityHashCode(broadcastfilter)));
            printwriter.println('}');
            broadcastfilter.dumpInReceiverList(printwriter, printwriterprinter, s1);
        }

    }

    void dumpLocal(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("app=");
        printwriter.print(app);
        printwriter.print(" pid=");
        printwriter.print(pid);
        printwriter.print(" uid=");
        printwriter.println(uid);
        if(curBroadcast != null || linkedToDeath) {
            printwriter.print(s);
            printwriter.print("curBroadcast=");
            printwriter.print(curBroadcast);
            printwriter.print(" linkedToDeath=");
            printwriter.println(linkedToDeath);
        }
    }

    public boolean equals(Object obj) {
        boolean flag;
        if(this == obj)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public String toString() {
        String s2;
        if(stringName != null) {
            s2 = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ReceiverList{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(pid);
            stringbuilder.append(' ');
            String s;
            String s1;
            if(app != null)
                s = app.processName;
            else
                s = "(unknown name)";
            stringbuilder.append(s);
            stringbuilder.append('/');
            stringbuilder.append(uid);
            if(receiver.asBinder() instanceof Binder)
                s1 = " local:";
            else
                s1 = " remote:";
            stringbuilder.append(s1);
            stringbuilder.append(Integer.toHexString(System.identityHashCode(receiver.asBinder())));
            stringbuilder.append('}');
            s2 = stringbuilder.toString();
            stringName = s2;
        }
        return s2;
    }

    public final ProcessRecord app;
    BroadcastRecord curBroadcast;
    boolean linkedToDeath;
    final ActivityManagerService owner;
    public final int pid;
    public final IIntentReceiver receiver;
    String stringName;
    public final int uid;
}
