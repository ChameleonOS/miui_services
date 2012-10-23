// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.*;

class StrictModeFlash {

    public StrictModeFlash(Display display, SurfaceSession surfacesession) {
        mThickness = 20;
        mSurface = new Surface(surfacesession, 0, "StrictModeFlash", -1, 1, 1, -3, 0);
        mSurface.setLayer(0xf6950);
        mSurface.setPosition(0, 0);
        mDrawNeeded = true;
_L2:
        return;
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void drawIfNeeded() {
        if(mDrawNeeded) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        int j;
        Rect rect;
        Canvas canvas;
        mDrawNeeded = false;
        i = mLastDW;
        j = mLastDH;
        rect = new Rect(0, 0, i, j);
        canvas = null;
        Canvas canvas1 = mSurface.lockCanvas(rect);
        canvas = canvas1;
_L4:
        if(canvas != null) {
            canvas.clipRect(new Rect(0, 0, i, 20), android.graphics.Region.Op.REPLACE);
            canvas.drawColor(0xffff0000);
            canvas.clipRect(new Rect(0, 0, 20, j), android.graphics.Region.Op.REPLACE);
            canvas.drawColor(0xffff0000);
            canvas.clipRect(new Rect(i - 20, 0, i, j), android.graphics.Region.Op.REPLACE);
            canvas.drawColor(0xffff0000);
            canvas.clipRect(new Rect(0, j - 20, i, j), android.graphics.Region.Op.REPLACE);
            canvas.drawColor(0xffff0000);
            mSurface.unlockCanvasAndPost(canvas);
        }
        if(true) goto _L1; else goto _L3
_L3:
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
          goto _L4
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
          goto _L4
    }

    void positionSurface(int i, int j) {
        if(mLastDW != i || mLastDH != j) {
            mLastDW = i;
            mLastDH = j;
            mSurface.setSize(i, j);
            mDrawNeeded = true;
        }
    }

    public void setVisibility(boolean flag) {
        if(mSurface != null) {
            drawIfNeeded();
            if(flag)
                mSurface.show();
            else
                mSurface.hide();
        }
    }

    private static final String TAG = "StrictModeFlash";
    boolean mDrawNeeded;
    int mLastDH;
    int mLastDW;
    Surface mSurface;
    final int mThickness;
}
