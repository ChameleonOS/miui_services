// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.graphics.Matrix;
import android.view.Surface;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, WindowAnimator, ScreenRotationAnimation, AppWindowToken, 
//            WindowState, WindowStateAnimator, WindowToken

public class AppWindowAnimator {
    static final class DummyAnimation extends Animation {

        public boolean getTransformation(long l, Transformation transformation1) {
            return false;
        }

        DummyAnimation() {
        }
    }


    public AppWindowAnimator(WindowManagerService windowmanagerservice, AppWindowToken appwindowtoken) {
        mService = windowmanagerservice;
        mAppToken = appwindowtoken;
        mAnimator = windowmanagerservice.mAnimator;
    }

    private boolean stepAnimation(long l) {
        boolean flag;
        if(animation == null) {
            flag = false;
        } else {
            transformation.clear();
            flag = animation.getTransformation(l, transformation);
            if(!flag) {
                animation = null;
                clearThumbnail();
            }
            hasTransformation = flag;
        }
        return flag;
    }

    private void stepThumbnailAnimation(long l) {
        thumbnailTransformation.clear();
        thumbnailAnimation.getTransformation(l, thumbnailTransformation);
        thumbnailTransformation.getMatrix().preTranslate(thumbnailX, thumbnailY);
        boolean flag;
        float af[];
        if(mAnimator.mScreenRotationAnimation != null && mAnimator.mScreenRotationAnimation.isAnimating())
            flag = true;
        else
            flag = false;
        if(flag)
            thumbnailTransformation.postCompose(mAnimator.mScreenRotationAnimation.getEnterTransformation());
        af = mService.mTmpFloats;
        thumbnailTransformation.getMatrix().getValues(af);
        thumbnail.setPosition(af[2], af[5]);
        thumbnail.setAlpha(thumbnailTransformation.getAlpha());
        thumbnail.setLayer(-4 + (5 + thumbnailLayer));
        thumbnail.setMatrix(af[0], af[3], af[1], af[4]);
    }

    public void clearAnimation() {
        if(animation != null) {
            animation = null;
            animating = true;
            animInitialized = false;
        }
        clearThumbnail();
    }

    public void clearThumbnail() {
        if(thumbnail != null) {
            thumbnail.destroy();
            thumbnail = null;
        }
    }

    void dump(PrintWriter printwriter, String s) {
        if(freezingScreen) {
            printwriter.print(s);
            printwriter.print(" freezingScreen=");
            printwriter.println(freezingScreen);
        }
        if(animating || animation != null) {
            printwriter.print(s);
            printwriter.print("animating=");
            printwriter.print(animating);
            printwriter.print(" animation=");
            printwriter.println(animation);
        }
        if(hasTransformation) {
            printwriter.print(s);
            printwriter.print("XForm: ");
            transformation.printShortString(printwriter);
            printwriter.println();
        }
        if(animLayerAdjustment != 0) {
            printwriter.print(s);
            printwriter.print("animLayerAdjustment=");
            printwriter.println(animLayerAdjustment);
        }
        if(thumbnail != null) {
            printwriter.print(s);
            printwriter.print("thumbnail=");
            printwriter.print(thumbnail);
            printwriter.print(" x=");
            printwriter.print(thumbnailX);
            printwriter.print(" y=");
            printwriter.print(thumbnailY);
            printwriter.print(" layer=");
            printwriter.println(thumbnailLayer);
            printwriter.print(s);
            printwriter.print("thumbnailAnimation=");
            printwriter.println(thumbnailAnimation);
            printwriter.print(s);
            printwriter.print("thumbnailTransformation=");
            printwriter.println(thumbnailTransformation.toShortString());
        }
    }

    public void setAnimation(Animation animation1, boolean flag) {
        animation = animation1;
        animating = false;
        animInitialized = flag;
        animation1.restrictDuration(10000L);
        animation1.scaleCurrentDuration(mService.mTransitionAnimationScale);
        int i = animation1.getZAdjustment();
        int j = 0;
        Transformation transformation1;
        float f;
        if(i == 1)
            j = 1000;
        else
        if(i == -1)
            j = -1000;
        if(animLayerAdjustment != j) {
            animLayerAdjustment = j;
            updateLayers();
        }
        transformation.clear();
        transformation1 = transformation;
        if(mAppToken.reportedVisible)
            f = 1.0F;
        else
            f = 0.0F;
        transformation1.setAlpha(f);
        hasTransformation = true;
    }

    public void setDummyAnimation() {
        animation = sDummyAnimation;
        animInitialized = false;
        hasTransformation = true;
        transformation.clear();
        Transformation transformation1 = transformation;
        float f;
        if(mAppToken.reportedVisible)
            f = 1.0F;
        else
            f = 0.0F;
        transformation1.setAlpha(f);
    }

    boolean showAllWindowsLocked() {
        boolean flag = false;
        int i = mAppToken.allAppWindows.size();
        for(int j = 0; j < i; j++) {
            WindowStateAnimator windowstateanimator = ((WindowState)mAppToken.allAppWindows.get(j)).mWinAnimator;
            windowstateanimator.performShowLocked();
            flag |= windowstateanimator.isAnimating();
        }

        return flag;
    }

    boolean stepAnimationLocked(long l, int i, int j) {
        boolean flag = true;
        if(!mService.okToDisplay()) goto _L2; else goto _L1
_L1:
        if(animation != sDummyAnimation) goto _L4; else goto _L3
_L3:
        flag = false;
_L7:
        return flag;
_L4:
        if((mAppToken.allDrawn || animating || mAppToken.startingDisplayed) && animation != null) {
            if(!animating) {
                if(!animInitialized)
                    animation.initialize(i, j, i, j);
                animation.setStartTime(l);
                animating = flag;
                if(thumbnail != null) {
                    thumbnail.show();
                    thumbnailAnimation.setStartTime(l);
                }
            }
            if(stepAnimation(l)) {
                if(thumbnail != null)
                    stepThumbnailAnimation(l);
                continue; /* Loop/switch isn't completed */
            }
        }
          goto _L5
_L2:
        if(animation != null) {
            animating = flag;
            animation = null;
        }
_L5:
        hasTransformation = false;
        if(!animating && animation == null) {
            flag = false;
        } else {
            WindowAnimator windowanimator = mAnimator;
            windowanimator.mPendingLayoutChanges = 8 | windowanimator.mPendingLayoutChanges;
            mService.debugLayoutRepeats("AppWindowToken", mAnimator.mPendingLayoutChanges);
            clearAnimation();
            animating = false;
            if(animLayerAdjustment != 0) {
                animLayerAdjustment = 0;
                updateLayers();
            }
            if(mService.mInputMethodTarget != null && mService.mInputMethodTarget.mAppToken == mAppToken)
                mService.moveInputMethodWindowsIfNeededLocked(flag);
            transformation.clear();
            int k = ((WindowToken) (mAppToken)).windows.size();
            for(int i1 = 0; i1 < k; i1++)
                ((WindowState)((WindowToken) (mAppToken)).windows.get(i1)).mWinAnimator.finishExit();

            mAppToken.updateReportedVisibilityLocked();
            flag = false;
        }
        if(true) goto _L7; else goto _L6
_L6:
    }

    void updateLayers() {
        int i = mAppToken.allAppWindows.size();
        int j = animLayerAdjustment;
        thumbnailLayer = -1;
        for(int k = 0; k < i; k++) {
            WindowState windowstate = (WindowState)mAppToken.allAppWindows.get(k);
            WindowStateAnimator windowstateanimator = windowstate.mWinAnimator;
            windowstateanimator.mAnimLayer = j + windowstate.mLayer;
            if(windowstateanimator.mAnimLayer > thumbnailLayer)
                thumbnailLayer = windowstateanimator.mAnimLayer;
            if(windowstate == mService.mInputMethodTarget && !mService.mInputMethodTargetWaitingAnim)
                mService.setInputMethodAnimLayerAdjustment(j);
            if(windowstate == mService.mWallpaperTarget && mService.mLowerWallpaperTarget == null)
                mService.setWallpaperAnimLayerAdjustmentLocked(j);
        }

    }

    static final String TAG = "AppWindowAnimator";
    static final Animation sDummyAnimation = new DummyAnimation();
    boolean allDrawn;
    boolean animInitialized;
    int animLayerAdjustment;
    boolean animating;
    Animation animation;
    boolean freezingScreen;
    boolean hasTransformation;
    final WindowAnimator mAnimator;
    final AppWindowToken mAppToken;
    final WindowManagerService mService;
    Surface thumbnail;
    Animation thumbnailAnimation;
    int thumbnailLayer;
    int thumbnailTransactionSeq;
    final Transformation thumbnailTransformation = new Transformation();
    int thumbnailX;
    int thumbnailY;
    final Transformation transformation = new Transformation();

}
