// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.res.Resources;
import android.util.Slog;
import android.util.TypedValue;
import android.view.Surface;
import android.view.SurfaceSession;
import android.view.animation.Animation;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.wm:
//            WindowStateAnimator

class DimAnimator {
    static class Parameters {

        final int mDimHeight;
        final float mDimTarget;
        final int mDimWidth;
        final WindowStateAnimator mDimWinAnimator;

        Parameters(WindowStateAnimator windowstateanimator, int i, int j, float f) {
            mDimWinAnimator = windowstateanimator;
            mDimWidth = i;
            mDimHeight = j;
            mDimTarget = f;
        }
    }


    DimAnimator(SurfaceSession surfacesession) {
        mDimShown = false;
        if(mDimSurface != null)
            break MISSING_BLOCK_LABEL_49;
        mDimSurface = new Surface(surfacesession, 0, "DimAnimator", -1, 16, 16, -1, 0x20000);
        mDimSurface.setAlpha(0.0F);
_L1:
        return;
        Exception exception;
        exception;
        Slog.e("WindowManager", "Exception creating Dim surface", exception);
          goto _L1
    }

    public void printTo(String s, PrintWriter printwriter) {
        printwriter.print(s);
        printwriter.print("mDimSurface=");
        printwriter.print(mDimSurface);
        printwriter.print(" ");
        printwriter.print(mLastDimWidth);
        printwriter.print(" x ");
        printwriter.println(mLastDimHeight);
        printwriter.print(s);
        printwriter.print("mDimShown=");
        printwriter.print(mDimShown);
        printwriter.print(" current=");
        printwriter.print(mDimCurrentAlpha);
        printwriter.print(" target=");
        printwriter.print(mDimTargetAlpha);
        printwriter.print(" delta=");
        printwriter.print(mDimDeltaPerMs);
        printwriter.print(" lastAnimTime=");
        printwriter.println(mLastDimAnimTime);
    }

    void updateParameters(Resources resources, Parameters parameters, long l) {
        int i = (int)(1.5D * (double)parameters.mDimWidth);
        int j = (int)(1.5D * (double)parameters.mDimHeight);
        WindowStateAnimator windowstateanimator = parameters.mDimWinAnimator;
        float f = parameters.mDimTarget;
        if(!mDimShown) {
            mDimShown = true;
            try {
                mLastDimWidth = i;
                mLastDimHeight = j;
                mDimSurface.setPosition((i * -1) / 6, (j * -1) / 6);
                mDimSurface.setSize(i, j);
                mDimSurface.show();
            }
            catch(RuntimeException runtimeexception) {
                Slog.w("WindowManager", "Failure showing dim surface", runtimeexception);
            }
        } else
        if(mLastDimWidth != i || mLastDimHeight != j) {
            mLastDimWidth = i;
            mLastDimHeight = j;
            mDimSurface.setSize(i, j);
            mDimSurface.setPosition((i * -1) / 6, (j * -1) / 6);
        }
        mDimSurface.setLayer(-1 + windowstateanimator.mAnimLayer);
        if(mDimTargetAlpha != f) {
            mLastDimAnimTime = l;
            long l1;
            if(windowstateanimator.mAnimating && windowstateanimator.mAnimation != null)
                l1 = windowstateanimator.mAnimation.computeDurationHint();
            else
                l1 = 200L;
            if(f > mDimTargetAlpha) {
                TypedValue typedvalue = new TypedValue();
                resources.getValue(0x1120000, typedvalue, true);
                if(typedvalue.type == 6)
                    l1 = (long)typedvalue.getFraction(l1, l1);
                else
                if(typedvalue.type >= 16 && typedvalue.type <= 31)
                    l1 = typedvalue.data;
            }
            if(l1 < 1L)
                l1 = 1L;
            mDimTargetAlpha = f;
            mDimDeltaPerMs = (mDimTargetAlpha - mDimCurrentAlpha) / (float)l1;
        }
    }

    boolean updateSurface(boolean flag, long l, boolean flag1) {
        if(!flag && mDimTargetAlpha != 0.0F) {
            mLastDimAnimTime = l;
            mDimTargetAlpha = 0.0F;
            mDimDeltaPerMs = -mDimCurrentAlpha / 200F;
        }
        boolean flag2;
        if(mLastDimAnimTime != 0L)
            flag2 = true;
        else
            flag2 = false;
        if(flag2) {
            mDimCurrentAlpha = mDimCurrentAlpha + mDimDeltaPerMs * (float)(l - mLastDimAnimTime);
            if(flag1)
                flag2 = false;
            else
            if(mDimDeltaPerMs > 0.0F) {
                if(mDimCurrentAlpha > mDimTargetAlpha)
                    flag2 = false;
            } else
            if(mDimDeltaPerMs < 0.0F) {
                if(mDimCurrentAlpha < mDimTargetAlpha)
                    flag2 = false;
            } else {
                flag2 = false;
            }
            if(flag2) {
                mLastDimAnimTime = l;
                mDimSurface.setAlpha(mDimCurrentAlpha);
            } else {
                mDimCurrentAlpha = mDimTargetAlpha;
                mLastDimAnimTime = 0L;
                mDimSurface.setAlpha(mDimCurrentAlpha);
                if(!flag) {
                    try {
                        mDimSurface.hide();
                    }
                    catch(RuntimeException runtimeexception) {
                        Slog.w("WindowManager", "Illegal argument exception hiding dim surface");
                    }
                    mDimShown = false;
                }
            }
        }
        return flag2;
    }

    float mDimCurrentAlpha;
    float mDimDeltaPerMs;
    boolean mDimShown;
    Surface mDimSurface;
    float mDimTargetAlpha;
    long mLastDimAnimTime;
    int mLastDimHeight;
    int mLastDimWidth;
}
