// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.util.Slog;
import android.view.Surface;
import android.view.SurfaceSession;
import java.io.PrintWriter;

class DimSurface {

    DimSurface(SurfaceSession surfacesession) {
        mDimShown = false;
        mDimColor = 0;
        mLayer = -1;
        if(mDimSurface != null)
            break MISSING_BLOCK_LABEL_60;
        mDimSurface = new Surface(surfacesession, 0, "DimSurface", -1, 16, 16, -1, 0x20000);
        mDimSurface.setAlpha(0.0F);
_L1:
        return;
        Exception exception;
        exception;
        Slog.e("WindowManager", "Exception creating Dim surface", exception);
          goto _L1
    }

    void hide() {
        if(!mDimShown)
            break MISSING_BLOCK_LABEL_19;
        mDimShown = false;
        mDimSurface.hide();
_L1:
        return;
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowManager", "Illegal argument exception hiding dim surface");
          goto _L1
    }

    public void printTo(String s, PrintWriter printwriter) {
        printwriter.print(s);
        printwriter.print("mDimSurface=");
        printwriter.println(mDimSurface);
        printwriter.print(s);
        printwriter.print("mDimShown=");
        printwriter.print(mDimShown);
        printwriter.print(" mLayer=");
        printwriter.print(mLayer);
        printwriter.print(" mDimColor=0x");
        printwriter.println(Integer.toHexString(mDimColor));
        printwriter.print(s);
        printwriter.print("mLastDimWidth=");
        printwriter.print(mLastDimWidth);
        printwriter.print(" mLastDimWidth=");
        printwriter.println(mLastDimWidth);
    }

    void show(int i, int j, int k, int l) {
        if(mDimShown)
            break MISSING_BLOCK_LABEL_71;
        mDimShown = true;
        mLastDimWidth = i;
        mLastDimHeight = j;
        mDimSurface.setPosition(0, 0);
        mDimSurface.setSize(i, j);
        mDimSurface.setLayer(k);
        mDimSurface.show();
_L1:
        return;
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowManager", "Failure showing dim surface", runtimeexception);
          goto _L1
        if(mLastDimWidth != i || mLastDimHeight != j || mDimColor != l || mLayer != k) {
            mLastDimWidth = i;
            mLastDimHeight = j;
            mLayer = k;
            mDimColor = l;
            mDimSurface.setSize(i, j);
            mDimSurface.setLayer(k);
            mDimSurface.setAlpha((float)(0xff & l >> 24) / 255F);
        }
          goto _L1
    }

    int mDimColor;
    boolean mDimShown;
    Surface mDimSurface;
    int mLastDimHeight;
    int mLastDimWidth;
    int mLayer;
}
