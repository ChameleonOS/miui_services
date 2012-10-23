// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.os.IBinder;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.android.server.wm:
//            AppWindowToken, WindowManagerService

class WindowToken {

    WindowToken(WindowManagerService windowmanagerservice, IBinder ibinder, int i, boolean flag) {
        paused = false;
        service = windowmanagerservice;
        token = ibinder;
        windowType = i;
        explicit = flag;
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("token=");
        printwriter.println(token);
        printwriter.print(s);
        printwriter.print("windows=");
        printwriter.println(windows);
        printwriter.print(s);
        printwriter.print("windowType=");
        printwriter.print(windowType);
        printwriter.print(" hidden=");
        printwriter.print(hidden);
        printwriter.print(" hasVisible=");
        printwriter.println(hasVisible);
        if(waitingToShow || waitingToHide || sendingToBottom) {
            printwriter.print(s);
            printwriter.print("waitingToShow=");
            printwriter.print(waitingToShow);
            printwriter.print(" waitingToHide=");
            printwriter.print(waitingToHide);
            printwriter.print(" sendingToBottom=");
            printwriter.print(sendingToBottom);
        }
    }

    public String toString() {
        if(stringName == null) {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("WindowToken{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(" token=");
            stringbuilder.append(token);
            stringbuilder.append('}');
            stringName = stringbuilder.toString();
        }
        return stringName;
    }

    AppWindowToken appWindowToken;
    final boolean explicit;
    boolean hasVisible;
    boolean hidden;
    boolean paused;
    boolean sendingToBottom;
    final WindowManagerService service;
    String stringName;
    final IBinder token;
    boolean waitingToHide;
    boolean waitingToShow;
    final int windowType;
    final ArrayList windows = new ArrayList();
}
