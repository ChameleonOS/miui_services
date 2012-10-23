// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.os.RemoteException;
import android.view.*;
import com.android.server.input.InputApplicationHandle;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.android.server.wm:
//            WindowToken, WindowManagerService, AppWindowAnimator, WindowState, 
//            WindowStateAnimator, WindowAnimator, StartingData

class AppWindowToken extends WindowToken {

    AppWindowToken(WindowManagerService windowmanagerservice, IApplicationToken iapplicationtoken) {
        super(windowmanagerservice, iapplicationtoken.asBinder(), 2, true);
        groupId = -1;
        requestedOrientation = -1;
        lastTransactionSequence = 0x8000000000000000L;
        super.appWindowToken = this;
        appToken = iapplicationtoken;
        mAnimator = super.service.mAnimator;
        mAppAnimator = new AppWindowAnimator(windowmanagerservice, this);
    }

    void dump(PrintWriter printwriter, String s) {
        super.dump(printwriter, s);
        if(appToken != null) {
            printwriter.print(s);
            printwriter.println("app=true");
        }
        if(allAppWindows.size() > 0) {
            printwriter.print(s);
            printwriter.print("allAppWindows=");
            printwriter.println(allAppWindows);
        }
        printwriter.print(s);
        printwriter.print("groupId=");
        printwriter.print(groupId);
        printwriter.print(" appFullscreen=");
        printwriter.print(appFullscreen);
        printwriter.print(" requestedOrientation=");
        printwriter.println(requestedOrientation);
        printwriter.print(s);
        printwriter.print("hiddenRequested=");
        printwriter.print(hiddenRequested);
        printwriter.print(" clientHidden=");
        printwriter.print(clientHidden);
        printwriter.print(" willBeHidden=");
        printwriter.print(willBeHidden);
        printwriter.print(" reportedDrawn=");
        printwriter.print(reportedDrawn);
        printwriter.print(" reportedVisible=");
        printwriter.println(reportedVisible);
        if(super.paused) {
            printwriter.print(s);
            printwriter.print("paused=");
            printwriter.println(super.paused);
        }
        if(numInterestingWindows != 0 || numDrawnWindows != 0 || allDrawn || mAppAnimator.allDrawn) {
            printwriter.print(s);
            printwriter.print("numInterestingWindows=");
            printwriter.print(numInterestingWindows);
            printwriter.print(" numDrawnWindows=");
            printwriter.print(numDrawnWindows);
            printwriter.print(" inPendingTransaction=");
            printwriter.print(inPendingTransaction);
            printwriter.print(" allDrawn=");
            printwriter.print(allDrawn);
            printwriter.print(" (animator=");
            printwriter.print(mAppAnimator.allDrawn);
            printwriter.println(")");
        }
        if(inPendingTransaction) {
            printwriter.print(s);
            printwriter.print("inPendingTransaction=");
            printwriter.println(inPendingTransaction);
        }
        if(startingData != null || removed || firstWindowDrawn) {
            printwriter.print(s);
            printwriter.print("startingData=");
            printwriter.print(startingData);
            printwriter.print(" removed=");
            printwriter.print(removed);
            printwriter.print(" firstWindowDrawn=");
            printwriter.println(firstWindowDrawn);
        }
        if(startingWindow != null || startingView != null || startingDisplayed || startingMoved) {
            printwriter.print(s);
            printwriter.print("startingWindow=");
            printwriter.print(startingWindow);
            printwriter.print(" startingView=");
            printwriter.print(startingView);
            printwriter.print(" startingDisplayed=");
            printwriter.print(startingDisplayed);
            printwriter.print(" startingMoved");
            printwriter.println(startingMoved);
        }
    }

    WindowState findMainWindow() {
        int i = super.windows.size();
_L4:
        if(i <= 0) goto _L2; else goto _L1
_L1:
        WindowState windowstate;
        i--;
        windowstate = (WindowState)super.windows.get(i);
        if(windowstate.mAttrs.type != 1 && windowstate.mAttrs.type != 3) goto _L4; else goto _L3
_L3:
        return windowstate;
_L2:
        windowstate = null;
        if(true) goto _L3; else goto _L5
_L5:
    }

    void sendAppVisibilityToClients() {
        int i;
        int j;
        i = allAppWindows.size();
        j = 0;
_L2:
        WindowState windowstate;
        if(j >= i)
            break MISSING_BLOCK_LABEL_87;
        windowstate = (WindowState)allAppWindows.get(j);
        if(windowstate != startingWindow || !clientHidden)
            break; /* Loop/switch isn't completed */
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        IWindow iwindow;
        boolean flag;
        iwindow = windowstate.mClient;
        if(clientHidden)
            break MISSING_BLOCK_LABEL_81;
        flag = true;
_L4:
        iwindow.dispatchAppVisibility(flag);
          goto _L3
        RemoteException remoteexception;
        remoteexception;
          goto _L3
        flag = false;
          goto _L4
          goto _L3
    }

    public String toString() {
        if(super.stringName == null) {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("AppWindowToken{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(" token=");
            stringbuilder.append(super.token);
            stringbuilder.append('}');
            super.stringName = stringbuilder.toString();
        }
        return super.stringName;
    }

    void updateReportedVisibilityLocked() {
        int i = 1;
        if(appToken != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j = 0;
        int k = 0;
        int l = 0;
        boolean flag = true;
        int i1 = allAppWindows.size();
        int j1 = 0;
        while(j1 < i1)  {
            WindowState windowstate = (WindowState)allAppWindows.get(j1);
            if(windowstate != startingWindow && !windowstate.mAppFreezing && windowstate.mViewVisibility == 0 && windowstate.mAttrs.type != 3 && !windowstate.mDestroying) {
                j++;
                if(windowstate.isDrawnLw()) {
                    l++;
                    if(!windowstate.mWinAnimator.isAnimating())
                        k++;
                    flag = false;
                } else
                if(windowstate.mWinAnimator.isAnimating())
                    flag = false;
            }
            j1++;
        }
        boolean flag1;
        boolean flag2;
        if(j > 0 && l >= j)
            flag1 = i;
        else
            flag1 = false;
        if(j > 0 && k >= j)
            flag2 = i;
        else
            flag2 = false;
        if(!flag) {
            if(!flag1)
                flag1 = reportedDrawn;
            if(!flag2)
                flag2 = reportedVisible;
        }
        if(flag1 != reportedDrawn) {
            if(flag1) {
                android.os.Message message1 = super.service.mH.obtainMessage(9, this);
                super.service.mH.sendMessage(message1);
            }
            reportedDrawn = flag1;
        }
        if(flag2 != reportedVisible) {
            reportedVisible = flag2;
            WindowManagerService.H h = super.service.mH;
            int k1;
            android.os.Message message;
            if(flag2)
                k1 = i;
            else
                k1 = 0;
            if(!flag)
                i = 0;
            message = h.obtainMessage(8, k1, i, this);
            super.service.mH.sendMessage(message);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    final ArrayList allAppWindows = new ArrayList();
    boolean allDrawn;
    boolean appFullscreen;
    final IApplicationToken appToken;
    boolean clientHidden;
    boolean firstWindowDrawn;
    int groupId;
    boolean hiddenRequested;
    boolean inPendingTransaction;
    long inputDispatchingTimeoutNanos;
    long lastTransactionSequence;
    final WindowAnimator mAnimator;
    final AppWindowAnimator mAppAnimator;
    final InputApplicationHandle mInputApplicationHandle = new InputApplicationHandle(this);
    int numDrawnWindows;
    int numInterestingWindows;
    boolean removed;
    boolean reportedDrawn;
    boolean reportedVisible;
    int requestedOrientation;
    StartingData startingData;
    boolean startingDisplayed;
    boolean startingMoved;
    View startingView;
    WindowState startingWindow;
    boolean willBeHidden;
}
