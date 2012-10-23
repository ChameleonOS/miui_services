// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceSession;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService

class Watermark {

    Watermark(DisplayMetrics displaymetrics, SurfaceSession surfacesession, String as[]) {
        mTokens = as;
        StringBuilder stringbuilder = new StringBuilder(32);
        int i = -2 & mTokens[0].length();
        int j = 0;
        while(j < i)  {
            char c = mTokens[0].charAt(j);
            char c1 = mTokens[0].charAt(j + 1);
            int i2;
            int j2;
            if(c >= 'a' && c <= 'f')
                i2 = 10 + (c + -97);
            else
            if(c >= 'A' && c <= 'F')
                i2 = 10 + (c + -65);
            else
                i2 = c + -48;
            if(c1 >= 'a' && c1 <= 'f')
                j2 = 10 + (c1 + -97);
            else
            if(c1 >= 'A' && c1 <= 'F')
                j2 = 10 + (c1 + -65);
            else
                j2 = c1 + -48;
            stringbuilder.append(255 - (j2 + i2 * 16));
            j += 2;
        }
        mText = stringbuilder.toString();
        int k = WindowManagerService.getPropertyInt(as, 1, 1, 20, displaymetrics);
        mTextPaint = new Paint(1);
        mTextPaint.setTextSize(k);
        mTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 1));
        android.graphics.Paint.FontMetricsInt fontmetricsint = mTextPaint.getFontMetricsInt();
        mTextWidth = (int)mTextPaint.measureText(mText);
        mTextAscent = fontmetricsint.ascent;
        mTextDescent = fontmetricsint.descent;
        mTextHeight = fontmetricsint.descent - fontmetricsint.ascent;
        mDeltaX = WindowManagerService.getPropertyInt(as, 2, 0, 2 * mTextWidth, displaymetrics);
        mDeltaY = WindowManagerService.getPropertyInt(as, 3, 0, 3 * mTextHeight, displaymetrics);
        int l = WindowManagerService.getPropertyInt(as, 4, 0, 0xb0000000, displaymetrics);
        int i1 = WindowManagerService.getPropertyInt(as, 5, 0, 0x60ffffff, displaymetrics);
        int j1 = WindowManagerService.getPropertyInt(as, 6, 0, 7, displaymetrics);
        int k1 = WindowManagerService.getPropertyInt(as, 8, 0, 0, displaymetrics);
        int l1 = WindowManagerService.getPropertyInt(as, 9, 0, 0, displaymetrics);
        mTextPaint.setColor(i1);
        mTextPaint.setShadowLayer(j1, k1, l1, l);
        mSurface = new Surface(surfacesession, 0, "WatermarkSurface", -1, 1, 1, -3, 0);
        mSurface.setLayer(0xf4240);
        mSurface.setPosition(0, 0);
        mSurface.show();
_L2:
        return;
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void drawIfNeeded() {
        if(!mDrawNeeded) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        Rect rect;
        Canvas canvas;
        i = mLastDW;
        j = mLastDH;
        mDrawNeeded = false;
        rect = new Rect(0, 0, i, j);
        canvas = null;
        Canvas canvas1 = mSurface.lockCanvas(rect);
        canvas = canvas1;
_L4:
        if(canvas != null) {
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
            int k = mDeltaX;
            int l = mDeltaY;
            int i1 = (i + mTextWidth) / k;
            int j1 = (i + mTextWidth) - i1 * k;
            int k1 = k / 4;
            if(j1 < k1 || j1 > k - k1)
                k += k / 3;
            int l1 = -mTextHeight;
            int i2 = -mTextWidth;
            do {
                if(l1 >= j + mTextHeight)
                    break;
                canvas.drawText(mText, i2, l1, mTextPaint);
                i2 += k;
                if(i2 >= i) {
                    i2 -= i + mTextWidth;
                    l1 += l;
                }
            } while(true);
            mSurface.unlockCanvasAndPost(canvas);
        }
_L2:
        return;
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        continue; /* Loop/switch isn't completed */
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    void positionSurface(int i, int j) {
        if(mLastDW != i || mLastDH != j) {
            mLastDW = i;
            mLastDH = j;
            mSurface.setSize(i, j);
            mDrawNeeded = true;
        }
    }

    final int mDeltaX;
    final int mDeltaY;
    boolean mDrawNeeded;
    int mLastDH;
    int mLastDW;
    Surface mSurface;
    final String mText;
    final int mTextAscent;
    final int mTextDescent;
    final int mTextHeight;
    final Paint mTextPaint;
    final int mTextWidth;
    final String mTokens[];
}
