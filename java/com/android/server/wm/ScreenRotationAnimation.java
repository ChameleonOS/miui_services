// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Slog;
import android.view.Surface;
import android.view.SurfaceSession;
import android.view.animation.*;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.wm:
//            BlackFrame

class ScreenRotationAnimation {

    public ScreenRotationAnimation(Context context, SurfaceSession surfacesession, boolean flag, int i, int j, int k) {
        mStartExitTransformation = new Transformation();
        mStartEnterTransformation = new Transformation();
        mStartFrameTransformation = new Transformation();
        mFinishExitTransformation = new Transformation();
        mFinishEnterTransformation = new Transformation();
        mFinishFrameTransformation = new Transformation();
        mRotateExitTransformation = new Transformation();
        mRotateEnterTransformation = new Transformation();
        mRotateFrameTransformation = new Transformation();
        mLastRotateExitTransformation = new Transformation();
        mLastRotateEnterTransformation = new Transformation();
        mLastRotateFrameTransformation = new Transformation();
        mExitTransformation = new Transformation();
        mEnterTransformation = new Transformation();
        mFrameTransformation = new Transformation();
        mFrameInitialMatrix = new Matrix();
        mSnapshotInitialMatrix = new Matrix();
        mSnapshotFinalMatrix = new Matrix();
        mExitFrameFinalMatrix = new Matrix();
        mTmpMatrix = new Matrix();
        mTmpFloats = new float[9];
        mContext = context;
        if(k == 1 || k == 3) {
            mWidth = j;
            mHeight = i;
        } else {
            mWidth = i;
            mHeight = j;
        }
        mOriginalRotation = k;
        mOriginalWidth = i;
        mOriginalHeight = j;
        if(!flag)
            Surface.openTransaction();
        mSurface = new Surface(surfacesession, 0, "FreezeSurface", -1, mWidth, mHeight, -1, 0x30004);
        if(mSurface.isValid()) goto _L2; else goto _L1
_L1:
        mSurface = null;
        if(!flag)
            Surface.closeTransaction();
_L5:
        return;
_L2:
        mSurface.setLayer(0x1e8481);
        mSurface.setAlpha(0.0F);
        mSurface.show();
_L3:
        setRotation(k);
        if(!flag)
            Surface.closeTransaction();
        continue; /* Loop/switch isn't completed */
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        Slog.w("ScreenRotationAnimation", "Unable to allocate freeze surface", outofresourcesexception);
          goto _L3
        Exception exception;
        exception;
        if(!flag)
            Surface.closeTransaction();
        throw exception;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static void createRotationMatrix(int i, int j, int k, Matrix matrix) {
        i;
        JVM INSTR tableswitch 0 3: default 32
    //                   0 33
    //                   1 40
    //                   2 59
    //                   3 79;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return;
_L2:
        matrix.reset();
        continue; /* Loop/switch isn't completed */
_L3:
        matrix.setRotate(90F, 0.0F, 0.0F);
        matrix.postTranslate(k, 0.0F);
        continue; /* Loop/switch isn't completed */
_L4:
        matrix.setRotate(180F, 0.0F, 0.0F);
        matrix.postTranslate(j, k);
        continue; /* Loop/switch isn't completed */
_L5:
        matrix.setRotate(270F, 0.0F, 0.0F);
        matrix.postTranslate(0.0F, j);
        if(true) goto _L1; else goto _L6
_L6:
    }

    static int deltaRotation(int i, int j) {
        int k = j - i;
        if(k < 0)
            k += 4;
        return k;
    }

    private boolean hasAnimations() {
        boolean flag;
        if(mRotateEnterAnimation != null || mRotateExitAnimation != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void setRotation(int i) {
        mCurRotation = i;
        createRotationMatrix(deltaRotation(i, 0), mWidth, mHeight, mSnapshotInitialMatrix);
        setSnapshotTransform(mSnapshotInitialMatrix, 1.0F);
    }

    private boolean startAnimation(SurfaceSession surfacesession, long l, float f, int i, int j, boolean flag) {
        if(mSurface != null) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L10:
        return flag1;
_L2:
        int k;
        if(mStarted) {
            flag1 = true;
            continue; /* Loop/switch isn't completed */
        }
        mStarted = true;
        k = deltaRotation(mCurRotation, mOriginalRotation);
        k;
        JVM INSTR tableswitch 0 3: default 76
    //                   0 297
    //                   1 328
    //                   2 359
    //                   3 404;
           goto _L3 _L4 _L5 _L6 _L7
_L3:
        (i + mOriginalWidth) / 2;
        (j + mOriginalHeight) / 2;
        mRotateEnterAnimation.initialize(i, j, mOriginalWidth, mOriginalHeight);
        mRotateExitAnimation.initialize(i, j, mOriginalWidth, mOriginalHeight);
        mAnimRunning = false;
        mFinishAnimReady = false;
        mFinishAnimStartTime = -1L;
        mRotateExitAnimation.restrictDuration(l);
        mRotateExitAnimation.scaleCurrentDuration(f);
        mRotateEnterAnimation.restrictDuration(l);
        mRotateEnterAnimation.scaleCurrentDuration(f);
        if(mExitingBlackFrame != null)
            break MISSING_BLOCK_LABEL_291;
        Surface.openTransaction();
        createRotationMatrix(k, mOriginalWidth, mOriginalHeight, mFrameInitialMatrix);
        mExitingBlackFrame = new BlackFrame(surfacesession, new Rect(1 * -mOriginalWidth, 1 * -mOriginalHeight, 2 * mOriginalWidth, 2 * mOriginalHeight), new Rect(0, 0, mOriginalWidth, mOriginalHeight), 0x1e8482);
        mExitingBlackFrame.setMatrix(mFrameInitialMatrix);
        Surface.closeTransaction();
_L8:
        flag1 = true;
        continue; /* Loop/switch isn't completed */
_L4:
        mRotateExitAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0038);
        mRotateEnterAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0037);
          goto _L3
_L5:
        mRotateExitAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0044);
        mRotateEnterAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0043);
          goto _L3
_L6:
        mRotateExitAnimation = AnimationUtils.loadAnimation(mContext, 0x10a003b);
        mRotateEnterAnimation = AnimationUtils.loadAnimation(mContext, 0x10a003a);
        mRotateFrameAnimation = AnimationUtils.loadAnimation(mContext, 0x10a003c);
          goto _L3
_L7:
        mRotateExitAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0041);
        mRotateEnterAnimation = AnimationUtils.loadAnimation(mContext, 0x10a0040);
          goto _L3
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        Slog.w("ScreenRotationAnimation", "Unable to allocate black surface", outofresourcesexception);
        Surface.closeTransaction();
          goto _L8
        Exception exception;
        exception;
        Surface.closeTransaction();
        throw exception;
        if(true) goto _L10; else goto _L9
_L9:
    }

    private boolean stepAnimation(long l) {
        boolean flag = false;
        if(l > mHalfwayPoint)
            mHalfwayPoint = 0x7fffffffffffffffL;
        if(mFinishAnimReady && mFinishAnimStartTime < 0L)
            mFinishAnimStartTime = l;
        if(mFinishAnimReady) {
            long _tmp = l - mFinishAnimStartTime;
        }
        mMoreRotateExit = false;
        if(mRotateExitAnimation != null)
            mMoreRotateExit = mRotateExitAnimation.getTransformation(l, mRotateExitTransformation);
        mMoreRotateEnter = false;
        if(mRotateEnterAnimation != null)
            mMoreRotateEnter = mRotateEnterAnimation.getTransformation(l, mRotateEnterTransformation);
        if(!mMoreRotateExit && mRotateExitAnimation != null) {
            mRotateExitAnimation.cancel();
            mRotateExitAnimation = null;
            mRotateExitTransformation.clear();
        }
        if(!mMoreRotateEnter && mRotateEnterAnimation != null) {
            mRotateEnterAnimation.cancel();
            mRotateEnterAnimation = null;
            mRotateEnterTransformation.clear();
        }
        mExitTransformation.set(mRotateExitTransformation);
        mEnterTransformation.set(mRotateEnterTransformation);
        if(mMoreRotateEnter || mMoreRotateExit || !mFinishAnimReady)
            flag = true;
        mSnapshotFinalMatrix.setConcat(mExitTransformation.getMatrix(), mSnapshotInitialMatrix);
        return flag;
    }

    public boolean dismiss(SurfaceSession surfacesession, long l, float f, int i, int j) {
        boolean flag = true;
        if(mSurface == null) {
            flag = false;
        } else {
            if(!mStarted)
                startAnimation(surfacesession, l, f, i, j, flag);
            if(!mStarted)
                flag = false;
            else
                mFinishAnimReady = flag;
        }
        return flag;
    }

    public Transformation getEnterTransformation() {
        return mEnterTransformation;
    }

    boolean hasScreenshot() {
        boolean flag;
        if(mSurface != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isAnimating() {
        boolean flag;
        if(!hasAnimations())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public void kill() {
        if(mSurface != null) {
            mSurface.destroy();
            mSurface = null;
        }
        if(mCustomBlackFrame != null) {
            mCustomBlackFrame.kill();
            mCustomBlackFrame = null;
        }
        if(mExitingBlackFrame != null) {
            mExitingBlackFrame.kill();
            mExitingBlackFrame = null;
        }
        if(mEnteringBlackFrame != null) {
            mEnteringBlackFrame.kill();
            mEnteringBlackFrame = null;
        }
        if(mRotateExitAnimation != null) {
            mRotateExitAnimation.cancel();
            mRotateExitAnimation = null;
        }
        if(mRotateEnterAnimation != null) {
            mRotateEnterAnimation.cancel();
            mRotateEnterAnimation = null;
        }
    }

    public void printTo(String s, PrintWriter printwriter) {
        printwriter.print(s);
        printwriter.print("mSurface=");
        printwriter.print(mSurface);
        printwriter.print(" mWidth=");
        printwriter.print(mWidth);
        printwriter.print(" mHeight=");
        printwriter.println(mHeight);
        printwriter.print(s);
        printwriter.print("mExitingBlackFrame=");
        printwriter.println(mExitingBlackFrame);
        if(mExitingBlackFrame != null)
            mExitingBlackFrame.printTo((new StringBuilder()).append(s).append("  ").toString(), printwriter);
        printwriter.print(s);
        printwriter.print("mEnteringBlackFrame=");
        printwriter.println(mEnteringBlackFrame);
        if(mEnteringBlackFrame != null)
            mEnteringBlackFrame.printTo((new StringBuilder()).append(s).append("  ").toString(), printwriter);
        printwriter.print(s);
        printwriter.print("mCurRotation=");
        printwriter.print(mCurRotation);
        printwriter.print(" mOriginalRotation=");
        printwriter.println(mOriginalRotation);
        printwriter.print(s);
        printwriter.print("mOriginalWidth=");
        printwriter.print(mOriginalWidth);
        printwriter.print(" mOriginalHeight=");
        printwriter.println(mOriginalHeight);
        printwriter.print(s);
        printwriter.print("mStarted=");
        printwriter.print(mStarted);
        printwriter.print(" mAnimRunning=");
        printwriter.print(mAnimRunning);
        printwriter.print(" mFinishAnimReady=");
        printwriter.print(mFinishAnimReady);
        printwriter.print(" mFinishAnimStartTime=");
        printwriter.println(mFinishAnimStartTime);
        printwriter.print(s);
        printwriter.print("mStartExitAnimation=");
        printwriter.print(mStartExitAnimation);
        printwriter.print(" ");
        mStartExitTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mStartEnterAnimation=");
        printwriter.print(mStartEnterAnimation);
        printwriter.print(" ");
        mStartEnterTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mStartFrameAnimation=");
        printwriter.print(mStartFrameAnimation);
        printwriter.print(" ");
        mStartFrameTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mFinishExitAnimation=");
        printwriter.print(mFinishExitAnimation);
        printwriter.print(" ");
        mFinishExitTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mFinishEnterAnimation=");
        printwriter.print(mFinishEnterAnimation);
        printwriter.print(" ");
        mFinishEnterTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mFinishFrameAnimation=");
        printwriter.print(mFinishFrameAnimation);
        printwriter.print(" ");
        mFinishFrameTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mRotateExitAnimation=");
        printwriter.print(mRotateExitAnimation);
        printwriter.print(" ");
        mRotateExitTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mRotateEnterAnimation=");
        printwriter.print(mRotateEnterAnimation);
        printwriter.print(" ");
        mRotateEnterTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mRotateFrameAnimation=");
        printwriter.print(mRotateFrameAnimation);
        printwriter.print(" ");
        mRotateFrameTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mExitTransformation=");
        mExitTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mEnterTransformation=");
        mEnterTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mFrameTransformation=");
        mEnterTransformation.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mFrameInitialMatrix=");
        mFrameInitialMatrix.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mSnapshotInitialMatrix=");
        mSnapshotInitialMatrix.printShortString(printwriter);
        printwriter.print(" mSnapshotFinalMatrix=");
        mSnapshotFinalMatrix.printShortString(printwriter);
        printwriter.println();
        printwriter.print(s);
        printwriter.print("mExitFrameFinalMatrix=");
        mExitFrameFinalMatrix.printShortString(printwriter);
        printwriter.println();
    }

    public boolean setRotation(int i, SurfaceSession surfacesession, long l, float f, int j, int k) {
        setRotation(i);
        return false;
    }

    void setSnapshotTransform(Matrix matrix, float f) {
        if(mSurface != null) {
            matrix.getValues(mTmpFloats);
            mSurface.setPosition(mTmpFloats[2], mTmpFloats[5]);
            mSurface.setMatrix(mTmpFloats[0], mTmpFloats[3], mTmpFloats[1], mTmpFloats[4]);
            mSurface.setAlpha(f);
        }
    }

    public boolean stepAnimationLocked(long l) {
        boolean flag = false;
        if(!hasAnimations()) {
            mFinishAnimReady = false;
        } else {
            if(!mAnimRunning) {
                if(mRotateEnterAnimation != null)
                    mRotateEnterAnimation.setStartTime(l);
                if(mRotateExitAnimation != null)
                    mRotateExitAnimation.setStartTime(l);
                mAnimRunning = true;
                mHalfwayPoint = l + mRotateEnterAnimation.getDuration() / 2L;
            }
            flag = stepAnimation(l);
        }
        return flag;
    }

    void updateSurfaces() {
        if(mStarted) {
            if(mSurface != null && !mMoreStartExit && !mMoreFinishExit && !mMoreRotateExit)
                mSurface.hide();
            if(mCustomBlackFrame != null)
                if(!mMoreStartFrame && !mMoreFinishFrame && !mMoreRotateFrame)
                    mCustomBlackFrame.hide();
                else
                    mCustomBlackFrame.setMatrix(mFrameTransformation.getMatrix());
            if(mExitingBlackFrame != null)
                if(!mMoreStartExit && !mMoreFinishExit && !mMoreRotateExit) {
                    mExitingBlackFrame.hide();
                } else {
                    mExitFrameFinalMatrix.setConcat(mExitTransformation.getMatrix(), mFrameInitialMatrix);
                    mExitingBlackFrame.setMatrix(mExitFrameFinalMatrix);
                }
            if(mEnteringBlackFrame != null)
                if(!mMoreStartEnter && !mMoreFinishEnter && !mMoreRotateEnter)
                    mEnteringBlackFrame.hide();
                else
                    mEnteringBlackFrame.setMatrix(mEnterTransformation.getMatrix());
            setSnapshotTransform(mSnapshotFinalMatrix, mExitTransformation.getAlpha());
        }
    }

    static final boolean DEBUG_STATE = false;
    static final boolean DEBUG_TRANSFORMS = false;
    static final int FREEZE_LAYER = 0x1e8480;
    static final String TAG = "ScreenRotationAnimation";
    static final boolean TWO_PHASE_ANIMATION;
    static final boolean USE_CUSTOM_BLACK_FRAME;
    boolean mAnimRunning;
    final Context mContext;
    int mCurRotation;
    BlackFrame mCustomBlackFrame;
    final Transformation mEnterTransformation;
    BlackFrame mEnteringBlackFrame;
    final Matrix mExitFrameFinalMatrix;
    final Transformation mExitTransformation;
    BlackFrame mExitingBlackFrame;
    boolean mFinishAnimReady;
    long mFinishAnimStartTime;
    Animation mFinishEnterAnimation;
    final Transformation mFinishEnterTransformation;
    Animation mFinishExitAnimation;
    final Transformation mFinishExitTransformation;
    Animation mFinishFrameAnimation;
    final Transformation mFinishFrameTransformation;
    final Matrix mFrameInitialMatrix;
    final Transformation mFrameTransformation;
    long mHalfwayPoint;
    int mHeight;
    Animation mLastRotateEnterAnimation;
    final Transformation mLastRotateEnterTransformation;
    Animation mLastRotateExitAnimation;
    final Transformation mLastRotateExitTransformation;
    Animation mLastRotateFrameAnimation;
    final Transformation mLastRotateFrameTransformation;
    private boolean mMoreFinishEnter;
    private boolean mMoreFinishExit;
    private boolean mMoreFinishFrame;
    private boolean mMoreRotateEnter;
    private boolean mMoreRotateExit;
    private boolean mMoreRotateFrame;
    private boolean mMoreStartEnter;
    private boolean mMoreStartExit;
    private boolean mMoreStartFrame;
    int mOriginalHeight;
    int mOriginalRotation;
    int mOriginalWidth;
    Animation mRotateEnterAnimation;
    final Transformation mRotateEnterTransformation;
    Animation mRotateExitAnimation;
    final Transformation mRotateExitTransformation;
    Animation mRotateFrameAnimation;
    final Transformation mRotateFrameTransformation;
    final Matrix mSnapshotFinalMatrix;
    final Matrix mSnapshotInitialMatrix;
    Animation mStartEnterAnimation;
    final Transformation mStartEnterTransformation;
    Animation mStartExitAnimation;
    final Transformation mStartExitTransformation;
    Animation mStartFrameAnimation;
    final Transformation mStartFrameTransformation;
    boolean mStarted;
    Surface mSurface;
    final float mTmpFloats[];
    final Matrix mTmpMatrix;
    int mWidth;
}
