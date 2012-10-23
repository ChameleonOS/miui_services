// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceSession;
import java.io.PrintWriter;

public class BlackFrame {
    class BlackSurface {

        void clearMatrix() {
            surface.setMatrix(1.0F, 0.0F, 0.0F, 1.0F);
        }

        void setMatrix(Matrix matrix) {
            mTmpMatrix.setTranslate(left, top);
            mTmpMatrix.postConcat(matrix);
            mTmpMatrix.getValues(mTmpFloats);
            surface.setPosition(mTmpFloats[2], mTmpFloats[5]);
            surface.setMatrix(mTmpFloats[0], mTmpFloats[3], mTmpFloats[1], mTmpFloats[4]);
        }

        final int layer;
        final int left;
        final Surface surface;
        final BlackFrame this$0;
        final int top;

        BlackSurface(SurfaceSession surfacesession, int i, int j, int k, int l, int i1) throws android.view.Surface.OutOfResourcesException {
            this$0 = BlackFrame.this;
            super();
            left = j;
            top = k;
            layer = i;
            surface = new Surface(surfacesession, 0, "BlackSurface", -1, l - j, i1 - k, -1, 0x20000);
            surface.setAlpha(1.0F);
            surface.setLayer(i);
            surface.show();
        }
    }


    public BlackFrame(SurfaceSession surfacesession, Rect rect, Rect rect1, int i) throws android.view.Surface.OutOfResourcesException {
        mTmpMatrix = new Matrix();
        mTmpFloats = new float[9];
        mBlackSurfaces = new BlackSurface[4];
        mOuterRect = new Rect(rect);
        mInnerRect = new Rect(rect1);
        if(rect.top < rect1.top)
            mBlackSurfaces[0] = new BlackSurface(surfacesession, i, rect.left, rect.top, rect1.right, rect1.top);
        if(rect.left < rect1.left)
            mBlackSurfaces[1] = new BlackSurface(surfacesession, i, rect.left, rect1.top, rect1.left, rect.bottom);
        if(rect.bottom > rect1.bottom)
            mBlackSurfaces[2] = new BlackSurface(surfacesession, i, rect1.left, rect1.bottom, rect.right, rect.bottom);
        if(rect.right > rect1.right)
            mBlackSurfaces[3] = new BlackSurface(surfacesession, i, rect1.right, rect.top, rect.right, rect1.bottom);
        if(false)
            kill();
        return;
        Exception exception;
        exception;
        if(true)
            kill();
        throw exception;
    }

    public void clearMatrix() {
        for(int i = 0; i < mBlackSurfaces.length; i++)
            if(mBlackSurfaces[i] != null)
                mBlackSurfaces[i].clearMatrix();

    }

    public void hide() {
        if(mBlackSurfaces != null) {
            for(int i = 0; i < mBlackSurfaces.length; i++)
                if(mBlackSurfaces[i] != null)
                    mBlackSurfaces[i].surface.hide();

        }
    }

    public void kill() {
        if(mBlackSurfaces != null) {
            for(int i = 0; i < mBlackSurfaces.length; i++)
                if(mBlackSurfaces[i] != null) {
                    mBlackSurfaces[i].surface.destroy();
                    mBlackSurfaces[i] = null;
                }

        }
    }

    public void printTo(String s, PrintWriter printwriter) {
        printwriter.print(s);
        printwriter.print("Outer: ");
        mOuterRect.printShortString(printwriter);
        printwriter.print(" / Inner: ");
        mInnerRect.printShortString(printwriter);
        printwriter.println();
        for(int i = 0; i < mBlackSurfaces.length; i++) {
            BlackSurface blacksurface = mBlackSurfaces[i];
            printwriter.print(s);
            printwriter.print("#");
            printwriter.print(i);
            printwriter.print(": ");
            printwriter.print(blacksurface.surface);
            printwriter.print(" left=");
            printwriter.print(blacksurface.left);
            printwriter.print(" top=");
            printwriter.println(blacksurface.top);
        }

    }

    public void setMatrix(Matrix matrix) {
        for(int i = 0; i < mBlackSurfaces.length; i++)
            if(mBlackSurfaces[i] != null)
                mBlackSurfaces[i].setMatrix(matrix);

    }

    final BlackSurface mBlackSurfaces[];
    final Rect mInnerRect;
    final Rect mOuterRect;
    final float mTmpFloats[];
    final Matrix mTmpMatrix;
}
