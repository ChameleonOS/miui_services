// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.Context;
import android.graphics.*;
import android.os.Debug;
import android.util.Slog;
import android.view.*;
import android.view.animation.*;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, WindowState, AppWindowToken, AppWindowAnimator, 
//            WindowAnimator, ScreenRotationAnimation, Session, WindowToken

class WindowStateAnimator {
    static class SurfaceTrace extends Surface {

        static void dumpAllSurfaces() {
            int i = sSurfaces.size();
            for(int j = 0; j < i; j++)
                Slog.i("WindowStateAnimator", (new StringBuilder()).append("SurfaceDump: ").append(sSurfaces.get(j)).toString());

        }

        public void destroy() {
            super.destroy();
            Slog.v("SurfaceTrace", (new StringBuilder()).append("destroy: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
            sSurfaces.remove(this);
        }

        public void hide() {
            super.hide();
            mShown = false;
            Slog.v("SurfaceTrace", (new StringBuilder()).append("hide: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public void release() {
            super.release();
            Slog.v("SurfaceTrace", (new StringBuilder()).append("release: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
            sSurfaces.remove(this);
        }

        public void setAlpha(float f) {
            super.setAlpha(f);
            mSurfaceTraceAlpha = f;
            Slog.v("SurfaceTrace", (new StringBuilder()).append("setAlpha: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public void setLayer(int i) {
            super.setLayer(i);
            mLayer = i;
            Slog.v("SurfaceTrace", (new StringBuilder()).append("setLayer: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
            sSurfaces.remove(this);
            int j = -1 + sSurfaces.size();
            do {
                if(j < 0 || ((SurfaceTrace)sSurfaces.get(j)).mLayer < i) {
                    sSurfaces.add(j + 1, this);
                    return;
                }
                j--;
            } while(true);
        }

        public void setPosition(float f, float f1) {
            super.setPosition(f, f1);
            mPosition.set(f, f1);
            Slog.v("SurfaceTrace", (new StringBuilder()).append("setPosition: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public void setSize(int i, int j) {
            super.setSize(i, j);
            mSize.set(i, j);
            Slog.v("SurfaceTrace", (new StringBuilder()).append("setSize: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public void setWindowCrop(Rect rect) {
            super.setWindowCrop(rect);
            if(rect != null)
                mWindowCrop.set(rect);
            Slog.v("SurfaceTrace", (new StringBuilder()).append("setWindowCrop: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public void show() {
            super.show();
            mShown = true;
            Slog.v("SurfaceTrace", (new StringBuilder()).append("show: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public String toString() {
            return (new StringBuilder()).append("Surface ").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(mName).append(": shown=").append(mShown).append(" layer=").append(mLayer).append(" alpha=").append(mSurfaceTraceAlpha).append(" ").append(mPosition.x).append(",").append(mPosition.y).append(" ").append(mSize.x).append("x").append(mSize.y).append(" crop=").append(mWindowCrop.toShortString()).toString();
        }

        private static final String SURFACE_TAG = "SurfaceTrace";
        static final ArrayList sSurfaces = new ArrayList();
        private int mLayer;
        private String mName;
        private final PointF mPosition;
        private boolean mShown;
        private final Point mSize;
        private float mSurfaceTraceAlpha;
        private final Rect mWindowCrop;


        public SurfaceTrace(SurfaceSession surfacesession, int i, int j, int k, int l, int i1, int j1) throws android.view.Surface.OutOfResourcesException {
            super(surfacesession, i, j, k, l, i1, j1);
            mSurfaceTraceAlpha = 0.0F;
            mPosition = new PointF();
            mSize = new Point();
            mWindowCrop = new Rect();
            mShown = false;
            mName = "Not named";
            mSize.set(k, l);
            Slog.v("SurfaceTrace", (new StringBuilder()).append("ctor: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }

        public SurfaceTrace(SurfaceSession surfacesession, int i, String s, int j, int k, int l, int i1, 
                int j1) throws android.view.Surface.OutOfResourcesException {
            super(surfacesession, i, s, j, k, l, i1, j1);
            mSurfaceTraceAlpha = 0.0F;
            mPosition = new PointF();
            mSize = new Point();
            mWindowCrop = new Rect();
            mShown = false;
            mName = "Not named";
            mName = s;
            mSize.set(k, l);
            Slog.v("SurfaceTrace", (new StringBuilder()).append("ctor: ").append(this).append(". Called by ").append(Debug.getCallers(3)).toString());
        }
    }


    public WindowStateAnimator(WindowManagerService windowmanagerservice, WindowState windowstate, WindowState windowstate1) {
        mShownAlpha = 0.0F;
        mAlpha = 0.0F;
        mLastAlpha = 0.0F;
        mDsDx = 1.0F;
        mDtDx = 0.0F;
        mDsDy = 0.0F;
        mDtDy = 1.0F;
        mLastDsDx = 1.0F;
        mLastDtDx = 0.0F;
        mLastDsDy = 0.0F;
        mLastDtDy = 1.0F;
        mService = windowmanagerservice;
        mWin = windowstate;
        mAttachedWindow = windowstate1;
        mAnimator = mService.mAnimator;
        mSession = windowstate.mSession;
        mPolicy = mService.mPolicy;
        mContext = mService.mContext;
        mAttrFlags = windowstate.mAttrs.flags;
        mAttrType = windowstate.mAttrs.type;
        mAnimDw = windowmanagerservice.mAppDisplayWidth;
        mAnimDh = windowmanagerservice.mAppDisplayHeight;
    }

    static String drawStateToString(int i) {
        i;
        JVM INSTR tableswitch 0 4: default 36
    //                   0 43
    //                   1 49
    //                   2 55
    //                   3 61
    //                   4 67;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        String s = Integer.toString(i);
_L8:
        return s;
_L2:
        s = "NO_SURFACE";
        continue; /* Loop/switch isn't completed */
_L3:
        s = "DRAW_PENDING";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "COMMIT_DRAW_PENDING";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "READY_TO_SHOW";
        continue; /* Loop/switch isn't completed */
_L6:
        s = "HAS_DRAWN";
        if(true) goto _L8; else goto _L7
_L7:
    }

    private boolean stepAnimation(long l) {
        boolean flag;
        if(mAnimation == null || !mLocalAnimating) {
            flag = false;
        } else {
            mTransformation.clear();
            flag = mAnimation.getTransformation(l, mTransformation);
        }
        return flag;
    }

    boolean applyAnimationLocked(int i, boolean flag) {
        boolean flag1 = true;
        if(!mLocalAnimating || mAnimationIsEntrance != flag) goto _L2; else goto _L1
_L1:
        return flag1;
_L2:
        int j;
        byte byte0;
        Animation animation;
        if(!mService.okToDisplay())
            break MISSING_BLOCK_LABEL_190;
        j = mPolicy.selectAnimationLw(mWin, i);
        byte0 = -1;
        animation = null;
        if(j == 0) goto _L4; else goto _L3
_L3:
        animation = AnimationUtils.loadAnimation(mContext, j);
_L10:
        if(animation != null) {
            setAnimation(animation);
            mAnimationIsEntrance = flag;
        }
_L12:
        if(mAnimation == null)
            flag1 = false;
          goto _L1
_L4:
        i;
        JVM INSTR lookupswitch 4: default 140
    //                   4097: 166
    //                   4099: 178
    //                   8194: 172
    //                   8196: 184;
           goto _L5 _L6 _L7 _L8 _L9
_L5:
        break; /* Loop/switch isn't completed */
_L9:
        break MISSING_BLOCK_LABEL_184;
_L11:
        if(byte0 >= 0)
            animation = mService.loadAnimation(mWin.mAttrs, byte0);
          goto _L10
_L6:
        byte0 = 0;
          goto _L11
_L8:
        byte0 = 1;
          goto _L11
_L7:
        byte0 = 2;
          goto _L11
        byte0 = 3;
          goto _L11
        clearAnimation();
          goto _L12
    }

    void applyEnterAnimationLocked() {
        char c;
        if(mEnterAnimationPending) {
            mEnterAnimationPending = false;
            c = '\u1001';
        } else {
            c = '\u1003';
        }
        applyAnimationLocked(c, true);
    }

    void cancelExitAnimationForNextAnimationLocked() {
        if(mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
            mLocalAnimating = false;
            destroySurfaceLocked();
        }
    }

    public void clearAnimation() {
        if(mAnimation != null) {
            mAnimating = true;
            mLocalAnimating = false;
            mAnimation.cancel();
            mAnimation = null;
        }
    }

    boolean commitFinishDrawingLocked(long l) {
        boolean flag = false;
        if(mDrawState == 2) {
            mDrawState = 3;
            if(mWin.mAttrs.type == 3)
                flag = true;
            AppWindowToken appwindowtoken = mWin.mAppToken;
            if(appwindowtoken == null || appwindowtoken.allDrawn || flag)
                performShowLocked();
            flag = true;
        }
        return flag;
    }

    void computeShownFrameLocked() {
        boolean flag = mHasLocalTransformation;
        Transformation transformation;
        AppWindowAnimator appwindowanimator;
        Transformation transformation1;
        boolean flag1;
        if(mAttachedWindow != null && mAttachedWindow.mWinAnimator.mHasLocalTransformation)
            transformation = mAttachedWindow.mWinAnimator.mTransformation;
        else
            transformation = null;
        if(mWin.mAppToken == null)
            appwindowanimator = null;
        else
            appwindowanimator = mWin.mAppToken.mAppAnimator;
        if(appwindowanimator != null && appwindowanimator.hasTransformation)
            transformation1 = appwindowanimator.transformation;
        else
            transformation1 = null;
        if(mWin.mAttrs.type == 2013 && mService.mLowerWallpaperTarget == null && mService.mWallpaperTarget != null) {
            if(mService.mWallpaperTarget.mWinAnimator.mHasLocalTransformation && mService.mWallpaperTarget.mWinAnimator.mAnimation != null && !mService.mWallpaperTarget.mWinAnimator.mAnimation.getDetachWallpaper())
                transformation = mService.mWallpaperTarget.mWinAnimator.mTransformation;
            Rect rect;
            float af[];
            float f;
            float f1;
            int i;
            int j;
            float f2;
            float f3;
            AppWindowAnimator appwindowanimator1;
            if(mService.mWallpaperTarget.mAppToken == null)
                appwindowanimator1 = null;
            else
                appwindowanimator1 = mService.mWallpaperTarget.mAppToken.mAppAnimator;
            if(appwindowanimator1 != null && appwindowanimator1.hasTransformation && appwindowanimator1.animation != null && !appwindowanimator1.animation.getDetachWallpaper())
                transformation1 = appwindowanimator1.transformation;
        }
        if(mService.mAnimator.mScreenRotationAnimation != null && mService.mAnimator.mScreenRotationAnimation.isAnimating())
            flag1 = true;
        else
            flag1 = false;
        if(!flag && transformation == null && transformation1 == null && !flag1) goto _L2; else goto _L1
_L1:
        rect = mWin.mFrame;
        af = mService.mTmpFloats;
        Matrix matrix = mWin.mTmpMatrix;
        if(flag1) {
            f2 = rect.width();
            f3 = rect.height();
            if(f2 >= 1.0F && f3 >= 1.0F)
                matrix.setScale(1.0F + 2.0F / f2, 1.0F + 2.0F / f3, f2 / 2.0F, f3 / 2.0F);
            else
                matrix.reset();
        } else {
            matrix.reset();
        }
        matrix.postScale(mWin.mGlobalScale, mWin.mGlobalScale);
        if(flag)
            matrix.postConcat(mTransformation.getMatrix());
        matrix.postTranslate(rect.left + mWin.mXOffset, rect.top + mWin.mYOffset);
        if(transformation != null)
            matrix.postConcat(transformation.getMatrix());
        if(transformation1 != null)
            matrix.postConcat(transformation1.getMatrix());
        if(flag1)
            matrix.postConcat(mService.mAnimator.mScreenRotationAnimation.getEnterTransformation().getMatrix());
        mHaveMatrix = true;
        matrix.getValues(af);
        mDsDx = af[0];
        mDtDx = af[3];
        mDsDy = af[1];
        mDtDy = af[4];
        f = af[2];
        f1 = af[5];
        i = rect.width();
        j = rect.height();
        mWin.mShownFrame.set(f, f1, f + (float)i, f1 + (float)j);
        mShownAlpha = mAlpha;
        if(!mService.mLimitedAlphaCompositing || !PixelFormat.formatHasAlpha(mWin.mAttrs.format) || mWin.isIdentityMatrix(mDsDx, mDtDx, mDsDy, mDtDy) && f == (float)rect.left && f1 == (float)rect.top) {
            if(flag)
                mShownAlpha = mShownAlpha * mTransformation.getAlpha();
            if(transformation != null)
                mShownAlpha = mShownAlpha * transformation.getAlpha();
            if(transformation1 != null)
                mShownAlpha = mShownAlpha * transformation1.getAlpha();
            if(flag1)
                mShownAlpha = mShownAlpha * mService.mAnimator.mScreenRotationAnimation.getEnterTransformation().getAlpha();
        }
_L4:
        return;
_L2:
        if(!mWin.mIsWallpaper || (1 & mAnimator.mPendingActions) == 0) {
            mWin.mShownFrame.set(mWin.mFrame);
            if(mWin.mXOffset != 0 || mWin.mYOffset != 0)
                mWin.mShownFrame.offset(mWin.mXOffset, mWin.mYOffset);
            mShownAlpha = mAlpha;
            mHaveMatrix = false;
            mDsDx = mWin.mGlobalScale;
            mDtDx = 0.0F;
            mDsDy = 0.0F;
            mDtDy = mWin.mGlobalScale;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    Surface createSurfaceLocked() {
        if(mSurface != null) goto _L2; else goto _L1
_L1:
        int i;
        android.view.WindowManager.LayoutParams layoutparams;
        int j;
        int k;
        mDrawState = 1;
        if(mWin.mAppToken != null)
            mWin.mAppToken.allDrawn = false;
        mService.makeWindowFreezingScreenIfNeededLocked(mWin);
        i = 0;
        layoutparams = mWin.mAttrs;
        if((0x2000 & layoutparams.flags) != 0)
            i = 0 | 0x80;
        j = mWin.mCompatFrame.width();
        k = mWin.mCompatFrame.height();
        if((0x4000 & layoutparams.flags) != 0) {
            j = mWin.mRequestedWidth;
            k = mWin.mRequestedHeight;
        }
        if(j <= 0)
            j = 1;
        if(k <= 0)
            k = 1;
        mSurfaceShown = false;
        mSurfaceLayer = 0;
        mSurfaceAlpha = 0.0F;
        mSurfaceX = 0.0F;
        mSurfaceY = 0.0F;
        mSurfaceW = j;
        mSurfaceH = k;
        mWin.mLastSystemDecorRect.set(0, 0, 0, 0);
        if((0x1000000 & layoutparams.flags) == 0) goto _L4; else goto _L3
_L3:
        boolean flag = true;
          goto _L5
_L6:
        int l;
        if(!PixelFormat.formatHasAlpha(layoutparams.format))
            i |= 0x400;
        mSurface = new Surface(mSession.mSurfaceSession, mSession.mPid, layoutparams.getTitle().toString(), 0, j, k, l, i);
        mWin.mHasSurface = true;
        Surface.openTransaction();
        mSurfaceX = mWin.mFrame.left + mWin.mXOffset;
        mSurfaceY = mWin.mFrame.top + mWin.mYOffset;
        mSurface.setPosition(mSurfaceX, mSurfaceY);
        mSurfaceLayer = mAnimLayer;
        mSurface.setLayer(mAnimLayer);
        mSurface.setAlpha(0.0F);
        mSurfaceShown = false;
        mSurface.hide();
        if((0x1000 & mWin.mAttrs.flags) != 0)
            mSurface.setFlags(4, 4);
_L8:
        mLastHidden = true;
        Surface.closeTransaction();
_L2:
        Surface surface = mSurface;
_L7:
        return surface;
_L4:
        flag = false;
          goto _L5
_L10:
        l = layoutparams.format;
          goto _L6
        android.view.Surface.OutOfResourcesException outofresourcesexception;
        outofresourcesexception;
        mWin.mHasSurface = false;
        Slog.w("WindowStateAnimator", "OutOfResourcesException creating surface");
        mService.reclaimSomeSurfaceMemoryLocked(this, "create", true);
        mDrawState = 0;
        surface = null;
          goto _L7
        Exception exception;
        exception;
        mWin.mHasSurface = false;
        Slog.e("WindowStateAnimator", "Exception creating surface", exception);
        mDrawState = 0;
        surface = null;
          goto _L7
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Error creating surface in ").append(j).toString(), runtimeexception);
        mService.reclaimSomeSurfaceMemoryLocked(this, "create-init", true);
          goto _L8
        Exception exception1;
        exception1;
        Surface.closeTransaction();
        throw exception1;
_L5:
        if(!flag) goto _L10; else goto _L9
_L9:
        l = -3;
          goto _L6
    }

    void destroyDeferredSurfaceLocked() {
        try {
            if(mPendingDestroySurface != null) {
                mPendingDestroySurface.destroy();
                mAnimator.hideWallpapersLocked(mWin);
            }
        }
        catch(RuntimeException runtimeexception) {
            Slog.w("WindowStateAnimator", (new StringBuilder()).append("Exception thrown when destroying Window ").append(this).append(" surface ").append(mPendingDestroySurface).append(" session ").append(mSession).append(": ").append(runtimeexception.toString()).toString());
        }
        mSurfaceDestroyDeferred = false;
        mPendingDestroySurface = null;
    }

    void destroySurfaceLocked() {
        if(mWin.mAppToken != null && mWin == mWin.mAppToken.startingWindow)
            mWin.mAppToken.startingDisplayed = false;
        if(mSurface == null) goto _L2; else goto _L1
_L1:
        for(int i = mWin.mChildWindows.size(); i > 0;) {
            i--;
            ((WindowState)mWin.mChildWindows.get(i)).mAttachedHidden = true;
        }

        if(!mSurfaceDestroyDeferred) goto _L4; else goto _L3
_L3:
        if(mSurface != null && mPendingDestroySurface != mSurface) {
            if(mPendingDestroySurface != null)
                mPendingDestroySurface.destroy();
            mPendingDestroySurface = mSurface;
        }
_L5:
        mAnimator.hideWallpapersLocked(mWin);
_L6:
        mSurfaceShown = false;
        mSurface = null;
        mWin.mHasSurface = false;
        mDrawState = 0;
_L2:
        return;
_L4:
        mSurface.destroy();
          goto _L5
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Exception thrown when destroying Window ").append(this).append(" surface ").append(mSurface).append(" session ").append(mSession).append(": ").append(runtimeexception.toString()).toString());
          goto _L6
    }

    public void dump(PrintWriter printwriter, String s, boolean flag) {
        if(mAnimating || mLocalAnimating || mAnimationIsEntrance || mAnimation != null) {
            printwriter.print(s);
            printwriter.print("mAnimating=");
            printwriter.print(mAnimating);
            printwriter.print(" mLocalAnimating=");
            printwriter.print(mLocalAnimating);
            printwriter.print(" mAnimationIsEntrance=");
            printwriter.print(mAnimationIsEntrance);
            printwriter.print(" mAnimation=");
            printwriter.println(mAnimation);
        }
        if(mHasTransformation || mHasLocalTransformation) {
            printwriter.print(s);
            printwriter.print("XForm: has=");
            printwriter.print(mHasTransformation);
            printwriter.print(" hasLocal=");
            printwriter.print(mHasLocalTransformation);
            printwriter.print(" ");
            mTransformation.printShortString(printwriter);
            printwriter.println();
        }
        if(mSurface != null) {
            if(flag) {
                printwriter.print(s);
                printwriter.print("mSurface=");
                printwriter.println(mSurface);
                printwriter.print(s);
                printwriter.print("mDrawState=");
                printwriter.print(drawStateToString(mDrawState));
                printwriter.print(" mLastHidden=");
                printwriter.println(mLastHidden);
            }
            printwriter.print(s);
            printwriter.print("Surface: shown=");
            printwriter.print(mSurfaceShown);
            printwriter.print(" layer=");
            printwriter.print(mSurfaceLayer);
            printwriter.print(" alpha=");
            printwriter.print(mSurfaceAlpha);
            printwriter.print(" rect=(");
            printwriter.print(mSurfaceX);
            printwriter.print(",");
            printwriter.print(mSurfaceY);
            printwriter.print(") ");
            printwriter.print(mSurfaceW);
            printwriter.print(" x ");
            printwriter.println(mSurfaceH);
        }
        if(mPendingDestroySurface != null) {
            printwriter.print(s);
            printwriter.print("mPendingDestroySurface=");
            printwriter.println(mPendingDestroySurface);
        }
        if(mSurfaceResized || mSurfaceDestroyDeferred) {
            printwriter.print(s);
            printwriter.print("mSurfaceResized=");
            printwriter.print(mSurfaceResized);
            printwriter.print(" mSurfaceDestroyDeferred=");
            printwriter.println(mSurfaceDestroyDeferred);
        }
        if(mShownAlpha != 1.0F || mAlpha != 1.0F || mLastAlpha != 1.0F) {
            printwriter.print(s);
            printwriter.print("mShownAlpha=");
            printwriter.print(mShownAlpha);
            printwriter.print(" mAlpha=");
            printwriter.print(mAlpha);
            printwriter.print(" mLastAlpha=");
            printwriter.println(mLastAlpha);
        }
        if(mHaveMatrix || mWin.mGlobalScale != 1.0F) {
            printwriter.print(s);
            printwriter.print("mGlobalScale=");
            printwriter.print(mWin.mGlobalScale);
            printwriter.print(" mDsDx=");
            printwriter.print(mDsDx);
            printwriter.print(" mDtDx=");
            printwriter.print(mDtDx);
            printwriter.print(" mDsDy=");
            printwriter.print(mDsDy);
            printwriter.print(" mDtDy=");
            printwriter.println(mDtDy);
        }
    }

    boolean finishDrawingLocked() {
        boolean flag = true;
        if(mDrawState == flag)
            mDrawState = 2;
        else
            flag = false;
        return flag;
    }

    void finishExit() {
        int i = mWin.mChildWindows.size();
        for(int j = 0; j < i; j++)
            ((WindowState)mWin.mChildWindows.get(j)).mWinAnimator.finishExit();

        break MISSING_BLOCK_LABEL_44;
        if(mWin.mExiting && !isWindowAnimating()) {
            if(mSurface != null) {
                mService.mDestroySurface.add(mWin);
                mWin.mDestroying = true;
                hide();
            }
            mWin.mExiting = false;
            if(mWin.mRemoveOnExit) {
                mService.mPendingRemove.add(mWin);
                mWin.mRemoveOnExit = false;
            }
            mAnimator.hideWallpapersLocked(mWin);
        }
        return;
    }

    void hide() {
        if(mLastHidden)
            break MISSING_BLOCK_LABEL_31;
        mLastHidden = true;
        if(mSurface == null)
            break MISSING_BLOCK_LABEL_31;
        mSurfaceShown = false;
        mSurface.hide();
_L1:
        return;
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Exception hiding surface in ").append(mWin).toString());
          goto _L1
    }

    boolean isAnimating() {
        WindowState windowstate = mAttachedWindow;
        AppWindowToken appwindowtoken = mWin.mAppToken;
        boolean flag;
        if(mAnimation != null || windowstate != null && windowstate.mWinAnimator.mAnimation != null || appwindowtoken != null && (appwindowtoken.mAppAnimator.animation != null || appwindowtoken.inPendingTransaction))
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isDummyAnimation() {
        AppWindowToken appwindowtoken = mWin.mAppToken;
        boolean flag;
        if(appwindowtoken != null && appwindowtoken.mAppAnimator.animation == AppWindowAnimator.sDummyAnimation)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isWindowAnimating() {
        boolean flag;
        if(mAnimation != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean performShowLocked() {
        boolean flag = true;
        if(mDrawState == 3 && mWin.isReadyForDisplayIgnoringKeyguard()) {
            mService.enableScreenIfNeededLocked();
            applyEnterAnimationLocked();
            mLastAlpha = -1F;
            mDrawState = 4;
            mService.scheduleAnimationLocked();
            int i = mWin.mChildWindows.size();
            do {
                if(i <= 0)
                    break;
                i--;
                WindowState windowstate = (WindowState)mWin.mChildWindows.get(i);
                if(windowstate.mAttachedHidden) {
                    windowstate.mAttachedHidden = false;
                    if(windowstate.mWinAnimator.mSurface != null) {
                        windowstate.mWinAnimator.performShowLocked();
                        mService.mLayoutNeeded = flag;
                    }
                }
            } while(true);
            if(mWin.mAttrs.type != 3 && mWin.mAppToken != null) {
                mWin.mAppToken.firstWindowDrawn = flag;
                if(mWin.mAppToken.startingData != null) {
                    clearAnimation();
                    mService.mFinishedStarting.add(mWin.mAppToken);
                    mService.mH.sendEmptyMessage(7);
                }
                mWin.mAppToken.updateReportedVisibilityLocked();
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public void prepareSurfaceLocked(boolean flag) {
        WindowState windowstate = mWin;
        if(mSurface != null) goto _L2; else goto _L1
_L1:
        if(windowstate.mOrientationChanging)
            windowstate.mOrientationChanging = false;
_L5:
        return;
_L2:
        boolean flag1;
        flag1 = false;
        computeShownFrameLocked();
        setSurfaceBoundaries(flag);
        if(!mWin.mIsWallpaper || mWin.mWallpaperVisible) goto _L4; else goto _L3
_L3:
        hide();
_L6:
        if(flag1) {
            RuntimeException runtimeexception;
            if(windowstate.mOrientationChanging)
                if(!windowstate.isDrawnLw()) {
                    WindowAnimator windowanimator = mAnimator;
                    windowanimator.mBulkUpdateParams = 8 | windowanimator.mBulkUpdateParams;
                } else {
                    windowstate.mOrientationChanging = false;
                }
            windowstate.mToken.hasVisible = true;
        }
        if(true) goto _L5; else goto _L4
_L4:
label0:
        {
            if(!windowstate.mAttachedHidden && windowstate.isReadyForDisplay())
                break label0;
            hide();
            mAnimator.hideWallpapersLocked(windowstate);
            if(windowstate.mOrientationChanging)
                windowstate.mOrientationChanging = false;
        }
          goto _L6
        if(mLastLayer == mAnimLayer && mLastAlpha == mShownAlpha && mLastDsDx == mDsDx && mLastDtDx == mDtDx && mLastDsDy == mDsDy && mLastDtDy == mDtDy && windowstate.mLastHScale == windowstate.mHScale && windowstate.mLastVScale == windowstate.mVScale && !mLastHidden)
            break MISSING_BLOCK_LABEL_524;
        flag1 = true;
        mLastAlpha = mShownAlpha;
        mLastLayer = mAnimLayer;
        mLastDsDx = mDsDx;
        mLastDtDx = mDtDx;
        mLastDsDy = mDsDy;
        mLastDtDy = mDtDy;
        windowstate.mLastHScale = windowstate.mHScale;
        windowstate.mLastVScale = windowstate.mVScale;
        if(mSurface == null) goto _L6; else goto _L7
_L7:
        mSurfaceAlpha = mShownAlpha;
        mSurface.setAlpha(mShownAlpha);
        mSurfaceLayer = mAnimLayer;
        mSurface.setLayer(mAnimLayer);
        mSurface.setMatrix(mDsDx * windowstate.mHScale, mDtDx * windowstate.mVScale, mDsDy * windowstate.mHScale, mDtDy * windowstate.mVScale);
        if(mLastHidden && mDrawState == 4) {
            if(!showSurfaceRobustlyLocked())
                break MISSING_BLOCK_LABEL_516;
            mLastHidden = false;
            if(windowstate.mIsWallpaper)
                mService.dispatchWallpaperVisibility(windowstate, true);
        }
_L8:
        if(mSurface != null)
            windowstate.mToken.hasVisible = true;
          goto _L6
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Error updating surface in ").append(windowstate).toString(), runtimeexception);
        if(!flag)
            mService.reclaimSomeSurfaceMemoryLocked(this, "update", true);
          goto _L6
        windowstate.mOrientationChanging = false;
          goto _L8
        flag1 = true;
          goto _L6
    }

    public void setAnimation(Animation animation) {
        mAnimating = false;
        mLocalAnimating = false;
        mAnimation = animation;
        mAnimation.restrictDuration(10000L);
        mAnimation.scaleCurrentDuration(mService.mWindowAnimationScale);
        mTransformation.clear();
        Transformation transformation = mTransformation;
        float f;
        if(mLastHidden)
            f = 0.0F;
        else
            f = 1.0F;
        transformation.setAlpha(f);
        mHasLocalTransformation = true;
    }

    void setSurfaceBoundaries(boolean flag) {
        WindowState windowstate;
        int i;
        int j;
        float f2;
        windowstate = mWin;
        boolean flag1;
        float f;
        float f1;
        WindowAnimator windowanimator;
        WindowAnimator windowanimator1;
        if((0x4000 & windowstate.mAttrs.flags) != 0) {
            i = windowstate.mRequestedWidth;
            j = windowstate.mRequestedHeight;
        } else {
            i = windowstate.mCompatFrame.width();
            j = windowstate.mCompatFrame.height();
        }
        if(i < 1)
            i = 1;
        if(j < 1)
            j = 1;
        if(mSurfaceW != (float)i || mSurfaceH != (float)j)
            flag1 = true;
        else
            flag1 = false;
        if(flag1) {
            mSurfaceW = i;
            mSurfaceH = j;
        }
        f = windowstate.mShownFrame.left;
        f1 = windowstate.mShownFrame.top;
        if(mSurfaceX != f || mSurfaceY != f1)
            try {
                mSurfaceX = f;
                mSurfaceY = f1;
                mSurface.setPosition(f, f1);
            }
            catch(RuntimeException runtimeexception) {
                Slog.w("WindowStateAnimator", (new StringBuilder()).append("Error positioning surface of ").append(windowstate).append(" pos=(").append(f).append(",").append(f1).append(")").toString(), runtimeexception);
                if(!flag)
                    mService.reclaimSomeSurfaceMemoryLocked(this, "position", true);
            }
        if(!flag1) goto _L2; else goto _L1
_L1:
        mSurfaceResized = true;
        mSurface.setSize(i, j);
        windowanimator = mAnimator;
        windowanimator.mPendingLayoutChanges = 4 | windowanimator.mPendingLayoutChanges;
        if((2 & windowstate.mAttrs.flags) == 0) goto _L2; else goto _L3
_L3:
        windowanimator1 = mAnimator;
        if(!windowstate.mExiting) goto _L5; else goto _L4
_L4:
        f2 = 0.0F;
_L6:
        windowanimator1.startDimming(this, f2, mService.mAppDisplayWidth, mService.mAppDisplayHeight);
_L2:
        updateSurfaceWindowCrop(flag);
        return;
_L5:
        f2 = windowstate.mAttrs.dimAmount;
          goto _L6
        RuntimeException runtimeexception1;
        runtimeexception1;
        Slog.e("WindowStateAnimator", (new StringBuilder()).append("Error resizing surface of ").append(windowstate).append(" size=(").append(i).append("x").append(j).append(")").toString(), runtimeexception1);
        if(!flag)
            mService.reclaimSomeSurfaceMemoryLocked(this, "size", true);
          goto _L2
    }

    void setTransparentRegionHint(Region region) {
        if(mSurface != null) goto _L2; else goto _L1
_L1:
        Slog.w("WindowStateAnimator", "setTransparentRegionHint: null mSurface after mHasSurface true");
_L4:
        return;
_L2:
        Surface.openTransaction();
        mSurface.setTransparentRegionHint(region);
        Surface.closeTransaction();
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        Surface.closeTransaction();
        throw exception;
    }

    void setWallpaperOffset(int i, int j) {
        mSurfaceX = i;
        mSurfaceY = j;
        if(!mAnimating) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Surface.openTransaction();
        mSurface.setPosition(i + mWin.mFrame.left, j + mWin.mFrame.top);
        updateSurfaceWindowCrop(false);
        Surface.closeTransaction();
          goto _L1
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Error positioning surface of ").append(mWin).append(" pos=(").append(i).append(",").append(j).append(")").toString(), runtimeexception);
        Surface.closeTransaction();
          goto _L1
        Exception exception;
        exception;
        Surface.closeTransaction();
        throw exception;
    }

    boolean showSurfaceRobustlyLocked() {
        boolean flag = true;
        try {
            if(mSurface != null) {
                mSurfaceShown = true;
                mSurface.show();
                if(mWin.mTurnOnScreen) {
                    mWin.mTurnOnScreen = false;
                    WindowAnimator windowanimator = mAnimator;
                    windowanimator.mBulkUpdateParams = 0x10 | windowanimator.mBulkUpdateParams;
                }
            }
        }
        catch(RuntimeException runtimeexception) {
            Slog.w("WindowStateAnimator", (new StringBuilder()).append("Failure showing surface ").append(mSurface).append(" in ").append(mWin).toString(), runtimeexception);
            mService.reclaimSomeSurfaceMemoryLocked(this, "show", flag);
            flag = false;
        }
        return flag;
    }

    boolean stepAnimationLocked(long l) {
        boolean flag;
        flag = true;
        mWasAnimating = mAnimating;
        if(!mService.okToDisplay()) goto _L2; else goto _L1
_L1:
        if(!mWin.isDrawnLw() || mAnimation == null) goto _L4; else goto _L3
_L3:
        mHasTransformation = flag;
        mHasLocalTransformation = flag;
        if(!mLocalAnimating) {
            mAnimation.initialize(mWin.mFrame.width(), mWin.mFrame.height(), mAnimDw, mAnimDh);
            mAnimDw = mService.mAppDisplayWidth;
            mAnimDh = mService.mAppDisplayHeight;
            mAnimation.setStartTime(l);
            mLocalAnimating = flag;
            mAnimating = flag;
        }
        if(mAnimation == null || !mLocalAnimating || !stepAnimation(l)) goto _L4; else goto _L5
_L5:
        return flag;
_L4:
        mHasLocalTransformation = false;
        if((!mLocalAnimating || mAnimationIsEntrance) && mWin.mAppToken != null && mWin.mAppToken.mAppAnimator.animation != null) {
            mAnimating = flag;
            mHasTransformation = flag;
            mTransformation.clear();
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        if(!mHasTransformation) goto _L7; else goto _L6
_L6:
        mAnimating = flag;
_L9:
        if(mAnimating || mLocalAnimating)
            break; /* Loop/switch isn't completed */
        flag = false;
        continue; /* Loop/switch isn't completed */
_L7:
        if(isAnimating())
            mAnimating = flag;
        continue; /* Loop/switch isn't completed */
_L2:
        if(mAnimation != null)
            mAnimating = flag;
        if(true) goto _L9; else goto _L8
_L8:
        mAnimating = false;
        mLocalAnimating = false;
        if(mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
        }
        if(mAnimator.mWindowDetachedWallpaper == mWin)
            mAnimator.mWindowDetachedWallpaper = null;
        mAnimLayer = mWin.mLayer;
        if(!mWin.mIsImWindow)
            break; /* Loop/switch isn't completed */
        mAnimLayer = mAnimLayer + mService.mInputMethodAnimLayerAdjustment;
_L12:
        mHasTransformation = false;
        mHasLocalTransformation = false;
        if(mWin.mPolicyVisibility != mWin.mPolicyVisibilityAfterAnim) {
            mWin.mPolicyVisibility = mWin.mPolicyVisibilityAfterAnim;
            mService.mLayoutNeeded = flag;
            if(!mWin.mPolicyVisibility) {
                if(mService.mCurrentFocus == mWin)
                    mService.mFocusMayChange = flag;
                mService.enableScreenIfNeededLocked();
            }
        }
        mTransformation.clear();
        if(mDrawState == 4 && mWin.mAttrs.type == 3 && mWin.mAppToken != null && mWin.mAppToken.firstWindowDrawn && mWin.mAppToken.startingData != null) {
            mService.mFinishedStarting.add(mWin.mAppToken);
            mService.mH.sendEmptyMessage(7);
        }
        finishExit();
        WindowAnimator windowanimator = mAnimator;
        windowanimator.mPendingLayoutChanges = 8 | windowanimator.mPendingLayoutChanges;
        mService.debugLayoutRepeats("WindowStateAnimator", mAnimator.mPendingLayoutChanges);
        if(mWin.mAppToken != null)
            mWin.mAppToken.updateReportedVisibilityLocked();
        flag = false;
        if(true) goto _L5; else goto _L10
_L10:
        if(!mWin.mIsWallpaper) goto _L12; else goto _L11
_L11:
        mAnimLayer = mAnimLayer + mService.mWallpaperAnimLayerAdjustment;
          goto _L12
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer("WindowStateAnimator (");
        stringbuffer.append((new StringBuilder()).append(mWin.mLastTitle).append("): ").toString());
        stringbuffer.append((new StringBuilder()).append("mSurface ").append(mSurface).toString());
        stringbuffer.append((new StringBuilder()).append(", mAnimation ").append(mAnimation).toString());
        return stringbuffer.toString();
    }

    void updateSurfaceWindowCrop(boolean flag) {
        WindowState windowstate;
        windowstate = mWin;
        if((0x4000 & windowstate.mAttrs.flags) != 0)
            windowstate.mSystemDecorRect.set(0, 0, windowstate.mRequestedWidth, windowstate.mRequestedHeight);
        else
        if(windowstate.mLayer >= mService.mSystemDecorLayer) {
            windowstate.mSystemDecorRect.set(0, 0, windowstate.mCompatFrame.width(), windowstate.mCompatFrame.height());
        } else {
            Rect rect = mService.mSystemDecorRect;
            int i = windowstate.mXOffset + windowstate.mFrame.left;
            int j = windowstate.mYOffset + windowstate.mFrame.top;
            windowstate.mSystemDecorRect.set(0, 0, windowstate.mFrame.width(), windowstate.mFrame.height());
            windowstate.mSystemDecorRect.intersect(rect.left - i, rect.top - j, rect.right - i, rect.bottom - j);
            if(windowstate.mEnforceSizeCompat && windowstate.mInvGlobalScale != 1.0F) {
                float f = windowstate.mInvGlobalScale;
                windowstate.mSystemDecorRect.left = (int)(f * (float)windowstate.mSystemDecorRect.left - 0.5F);
                windowstate.mSystemDecorRect.top = (int)(f * (float)windowstate.mSystemDecorRect.top - 0.5F);
                windowstate.mSystemDecorRect.right = (int)(f * (float)(1 + windowstate.mSystemDecorRect.right) - 0.5F);
                windowstate.mSystemDecorRect.bottom = (int)(f * (float)(1 + windowstate.mSystemDecorRect.bottom) - 0.5F);
            }
        }
        if(windowstate.mSystemDecorRect.equals(windowstate.mLastSystemDecorRect))
            break MISSING_BLOCK_LABEL_72;
        windowstate.mLastSystemDecorRect.set(windowstate.mSystemDecorRect);
        mSurface.setWindowCrop(windowstate.mSystemDecorRect);
_L1:
        return;
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("WindowStateAnimator", (new StringBuilder()).append("Error setting crop surface of ").append(windowstate).append(" crop=").append(windowstate.mSystemDecorRect.toShortString()).toString(), runtimeexception);
        if(!flag)
            mService.reclaimSomeSurfaceMemoryLocked(this, "crop", true);
          goto _L1
    }

    static final int COMMIT_DRAW_PENDING = 2;
    static final boolean DEBUG_ANIM = false;
    static final boolean DEBUG_LAYERS = false;
    static final boolean DEBUG_ORIENTATION = false;
    static final boolean DEBUG_STARTING_WINDOW = false;
    static final boolean DEBUG_SURFACE_TRACE = false;
    static final boolean DEBUG_VISIBILITY = false;
    static final int DRAW_PENDING = 1;
    static final int HAS_DRAWN = 4;
    static final int NO_SURFACE = 0;
    static final int READY_TO_SHOW = 3;
    static final boolean SHOW_LIGHT_TRANSACTIONS = false;
    static final boolean SHOW_SURFACE_ALLOC = false;
    static final boolean SHOW_TRANSACTIONS = false;
    static final String TAG = "WindowStateAnimator";
    static final boolean localLOGV;
    float mAlpha;
    int mAnimDh;
    int mAnimDw;
    int mAnimLayer;
    boolean mAnimating;
    Animation mAnimation;
    boolean mAnimationIsEntrance;
    final WindowAnimator mAnimator;
    final WindowState mAttachedWindow;
    int mAttrFlags;
    int mAttrType;
    final Context mContext;
    int mDrawState;
    float mDsDx;
    float mDsDy;
    float mDtDx;
    float mDtDy;
    boolean mEnterAnimationPending;
    boolean mHasLocalTransformation;
    boolean mHasTransformation;
    boolean mHaveMatrix;
    float mLastAlpha;
    float mLastDsDx;
    float mLastDsDy;
    float mLastDtDx;
    float mLastDtDy;
    boolean mLastHidden;
    int mLastLayer;
    boolean mLocalAnimating;
    Surface mPendingDestroySurface;
    final WindowManagerPolicy mPolicy;
    final WindowManagerService mService;
    final Session mSession;
    float mShownAlpha;
    Surface mSurface;
    float mSurfaceAlpha;
    boolean mSurfaceDestroyDeferred;
    float mSurfaceH;
    int mSurfaceLayer;
    boolean mSurfaceResized;
    boolean mSurfaceShown;
    float mSurfaceW;
    float mSurfaceX;
    float mSurfaceY;
    final Transformation mTransformation = new Transformation();
    boolean mWasAnimating;
    final WindowState mWin;
}
