// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.PrintWriterPrinter;
import android.util.TimeUtils;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

// Referenced classes of package com.android.server.am:
//            ProcessRecord, BroadcastFilter, BroadcastQueue

class BroadcastRecord extends Binder {

    BroadcastRecord(BroadcastQueue broadcastqueue, Intent intent1, ProcessRecord processrecord, String s, int i, int j, String s1, 
            List list, IIntentReceiver iintentreceiver, int k, String s2, Bundle bundle, boolean flag, boolean flag1, 
            boolean flag2) {
        queue = broadcastqueue;
        intent = intent1;
        callerApp = processrecord;
        callerPackage = s;
        callingPid = i;
        callingUid = j;
        requiredPermission = s1;
        receivers = list;
        resultTo = iintentreceiver;
        resultCode = k;
        resultData = s2;
        resultExtras = bundle;
        ordered = flag;
        sticky = flag1;
        initialSticky = flag2;
        nextReceiver = 0;
        state = 0;
    }

    void dump(PrintWriter printwriter, String s) {
        String s2;
        long l = SystemClock.uptimeMillis();
        printwriter.print(s);
        printwriter.println(this);
        printwriter.print(s);
        printwriter.println(intent);
        if(sticky) {
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                printwriter.print(s);
                printwriter.print("extras: ");
                printwriter.println(bundle.toString());
            }
        }
        printwriter.print(s);
        printwriter.print("caller=");
        printwriter.print(callerPackage);
        printwriter.print(" ");
        String s1;
        if(callerApp != null)
            s1 = callerApp.toShortString();
        else
            s1 = "null";
        printwriter.print(s1);
        printwriter.print(" pid=");
        printwriter.print(callingPid);
        printwriter.print(" uid=");
        printwriter.println(callingUid);
        if(requiredPermission != null) {
            printwriter.print(s);
            printwriter.print("requiredPermission=");
            printwriter.println(requiredPermission);
        }
        printwriter.print(s);
        printwriter.print("dispatchClockTime=");
        printwriter.println(new Date(dispatchClockTime));
        printwriter.print(s);
        printwriter.print("dispatchTime=");
        TimeUtils.formatDuration(dispatchTime, l, printwriter);
        if(finishTime != 0L) {
            printwriter.print(" finishTime=");
            TimeUtils.formatDuration(finishTime, l, printwriter);
        } else {
            printwriter.print(" receiverTime=");
            TimeUtils.formatDuration(receiverTime, l, printwriter);
        }
        printwriter.println("");
        if(anrCount != 0) {
            printwriter.print(s);
            printwriter.print("anrCount=");
            printwriter.println(anrCount);
        }
        if(resultTo != null || resultCode != -1 || resultData != null) {
            printwriter.print(s);
            printwriter.print("resultTo=");
            printwriter.print(resultTo);
            printwriter.print(" resultCode=");
            printwriter.print(resultCode);
            printwriter.print(" resultData=");
            printwriter.println(resultData);
        }
        if(resultExtras != null) {
            printwriter.print(s);
            printwriter.print("resultExtras=");
            printwriter.println(resultExtras);
        }
        if(resultAbort || ordered || sticky || initialSticky) {
            printwriter.print(s);
            printwriter.print("resultAbort=");
            printwriter.print(resultAbort);
            printwriter.print(" ordered=");
            printwriter.print(ordered);
            printwriter.print(" sticky=");
            printwriter.print(sticky);
            printwriter.print(" initialSticky=");
            printwriter.println(initialSticky);
        }
        if(nextReceiver != 0 || receiver != null) {
            printwriter.print(s);
            printwriter.print("nextReceiver=");
            printwriter.print(nextReceiver);
            printwriter.print(" receiver=");
            printwriter.println(receiver);
        }
        if(curFilter != null) {
            printwriter.print(s);
            printwriter.print("curFilter=");
            printwriter.println(curFilter);
        }
        if(curReceiver != null) {
            printwriter.print(s);
            printwriter.print("curReceiver=");
            printwriter.println(curReceiver);
        }
        if(curApp != null) {
            printwriter.print(s);
            printwriter.print("curApp=");
            printwriter.println(curApp);
            printwriter.print(s);
            printwriter.print("curComponent=");
            int j;
            String s4;
            if(curComponent != null)
                s4 = curComponent.toShortString();
            else
                s4 = "--";
            printwriter.println(s4);
            if(curReceiver != null && curReceiver.applicationInfo != null) {
                printwriter.print(s);
                printwriter.print("curSourceDir=");
                printwriter.println(curReceiver.applicationInfo.sourceDir);
            }
        }
        s2 = " (?)";
        state;
        JVM INSTR tableswitch 0 3: default 696
    //                   0 885
    //                   1 893
    //                   2 901
    //                   3 909;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        break; /* Loop/switch isn't completed */
_L5:
        break MISSING_BLOCK_LABEL_909;
_L6:
        printwriter.print(s);
        printwriter.print("state=");
        printwriter.print(state);
        printwriter.println(s2);
        int i;
        String s3;
        PrintWriterPrinter printwriterprinter;
        if(receivers != null)
            i = receivers.size();
        else
            i = 0;
        s3 = (new StringBuilder()).append(s).append("  ").toString();
        printwriterprinter = new PrintWriterPrinter(printwriter);
        j = 0;
        do {
            if(j >= i)
                break;
            Object obj = receivers.get(j);
            printwriter.print(s);
            printwriter.print("Receiver #");
            printwriter.print(j);
            printwriter.print(": ");
            printwriter.println(obj);
            if(obj instanceof BroadcastFilter)
                ((BroadcastFilter)obj).dumpBrief(printwriter, s3);
            else
            if(obj instanceof ResolveInfo)
                ((ResolveInfo)obj).dump(printwriterprinter, s3);
            j++;
        } while(true);
        break MISSING_BLOCK_LABEL_946;
_L2:
        s2 = " (IDLE)";
          goto _L6
_L3:
        s2 = " (APP_RECEIVE)";
          goto _L6
_L4:
        s2 = " (CALL_IN_RECEIVE)";
          goto _L6
        s2 = " (CALL_DONE_RECEIVE)";
          goto _L6
    }

    public String toString() {
        return (new StringBuilder()).append("BroadcastRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(intent.getAction()).append("}").toString();
    }

    static final int APP_RECEIVE = 1;
    static final int CALL_DONE_RECEIVE = 3;
    static final int CALL_IN_RECEIVE = 2;
    static final int IDLE;
    int anrCount;
    final ProcessRecord callerApp;
    final String callerPackage;
    final int callingPid;
    final int callingUid;
    ProcessRecord curApp;
    ComponentName curComponent;
    BroadcastFilter curFilter;
    ActivityInfo curReceiver;
    long dispatchClockTime;
    long dispatchTime;
    long finishTime;
    final boolean initialSticky;
    final Intent intent;
    int nextReceiver;
    final boolean ordered;
    BroadcastQueue queue;
    IBinder receiver;
    long receiverTime;
    final List receivers;
    final String requiredPermission;
    boolean resultAbort;
    int resultCode;
    String resultData;
    Bundle resultExtras;
    IIntentReceiver resultTo;
    int state;
    final boolean sticky;
}
