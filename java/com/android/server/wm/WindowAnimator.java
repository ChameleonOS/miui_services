// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManagerPolicy;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, AppWindowToken, AppWindowAnimator, WindowState, 
//            DimSurface, WindowStateAnimator, ScreenRotationAnimation, DimAnimator, 
//            BlackFrame, Watermark, WindowToken

public class WindowAnimator {
    static class SetAnimationParams {

        final int mAnimDh;
        final int mAnimDw;
        final Animation mAnimation;
        final WindowStateAnimator mWinAnimator;

        public SetAnimationParams(WindowStateAnimator windowstateanimator, Animation animation, int i, int j) {
            mWinAnimator = windowstateanimator;
            mAnimation = animation;
            mAnimDw = i;
            mAnimDh = j;
        }
    }


    WindowAnimator(WindowManagerService windowmanagerservice, Context context, WindowManagerPolicy windowmanagerpolicy) {
        mWinAnimators = new ArrayList();
        mScreenRotationAnimation = null;
        mWindowDetachedWallpaper = null;
        mDetachedWallpaper = null;
        mWindowAnimationBackgroundSurface = null;
        mBulkUpdateParams = 0;
        mDimAnimator = null;
        mDimParams = null;
        mService = windowmanagerservice;
        mContext = context;
        mPolicy = windowmanagerpolicy;
    }

    private void performAnimationsLocked() {
        mForceHiding = false;
        mDetachedWallpaper = null;
        mWindowAnimationBackground = null;
        mWindowAnimationBackgroundColor = 0;
        updateWindowsAndWallpaperLocked();
        if((4 & mPendingLayoutChanges) != 0)
            mPendingActions = 1 | mPendingActions;
        testTokenMayBeDrawnLocked();
    }

    private void testTokenMayBeDrawnLocked() {
        ArrayList arraylist = mService.mAnimatingAppTokens;
        int i = arraylist.size();
        int j = 0;
        while(j < i)  {
            AppWindowToken appwindowtoken = (AppWindowToken)arraylist.get(j);
            boolean flag = appwindowtoken.allDrawn;
            if(flag == appwindowtoken.mAppAnimator.allDrawn)
                continue;
            appwindowtoken.mAppAnimator.allDrawn = flag;
            if(flag)
                if(appwindowtoken.mAppAnimator.freezingScreen) {
                    appwindowtoken.mAppAnimator.showAllWindowsLocked();
                    mService.unsetAppFreezingScreenLocked(appwindowtoken, false, true);
                    mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                } else {
                    mPendingLayoutChanges = 8 | mPendingLayoutChanges;
                    mService.debugLayoutRepeats("testTokenMayBeDrawnLocked", mPendingLayoutChanges);
                    if(!mService.mOpeningApps.contains(appwindowtoken))
                        mAnimating = mAnimating | appwindowtoken.mAppAnimator.showAllWindowsLocked();
                }
            j++;
        }
    }

    private void testWallpaperAndBackgroundLocked() {
        WindowState windowstate;
        if(mWindowDetachedWallpaper != mDetachedWallpaper) {
            mWindowDetachedWallpaper = mDetachedWallpaper;
            mBulkUpdateParams = 2 | mBulkUpdateParams;
        }
        if(mWindowAnimationBackgroundColor == 0)
            break MISSING_BLOCK_LABEL_188;
        windowstate = mWindowAnimationBackground;
        if(mService.mWallpaperTarget != windowstate && mService.mLowerWallpaperTarget != windowstate && mService.mUpperWallpaperTarget != windowstate) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = mService.mWindows.size();
        j = 0;
_L6:
        if(j >= i) goto _L2; else goto _L3
_L3:
        WindowState windowstate1 = (WindowState)mService.mWindows.get(j);
        if(!windowstate1.mIsWallpaper) goto _L5; else goto _L4
_L4:
        windowstate = windowstate1;
_L2:
        if(mWindowAnimationBackgroundSurface == null)
            mWindowAnimationBackgroundSurface = new DimSurface(mService.mFxSession);
        int k = mDw;
        int l = mDh;
        mWindowAnimationBackgroundSurface.show(k, l, -1 + windowstate.mWinAnimator.mAnimLayer, mWindowAnimationBackgroundColor);
_L7:
        return;
_L5:
        j++;
          goto _L6
        if(mWindowAnimationBackgroundSurface != null)
            mWindowAnimationBackgroundSurface.hide();
          goto _L7
    }

    private void updateWindowsAndWallpaperLocked() {
        mAnimTransactionSequence = 1 + mAnimTransactionSequence;
        ArrayList arraylist = null;
        boolean flag = false;
        int i = -1 + mService.mWindows.size();
        while(i >= 0)  {
            WindowState windowstate = (WindowState)mService.mWindows.get(i);
            WindowStateAnimator windowstateanimator1 = windowstate.mWinAnimator;
            int k = windowstateanimator1.mAttrFlags;
            AppWindowToken appwindowtoken;
            AppWindowAnimator appwindowanimator;
            if(windowstateanimator1.mSurface != null) {
                boolean flag1 = windowstateanimator1.mWasAnimating;
                boolean flag2 = windowstateanimator1.stepAnimationLocked(mCurrentTime);
                if(flag2) {
                    if(windowstateanimator1.mAnimation != null) {
                        if((0x100000 & k) != 0 && windowstateanimator1.mAnimation.getDetachWallpaper())
                            mDetachedWallpaper = windowstate;
                        int i1 = windowstateanimator1.mAnimation.getBackgroundColor();
                        if(i1 != 0 && (mWindowAnimationBackground == null || windowstateanimator1.mAnimLayer < mWindowAnimationBackground.mWinAnimator.mAnimLayer)) {
                            mWindowAnimationBackground = windowstate;
                            mWindowAnimationBackgroundColor = i1;
                        }
                    }
                    mAnimating = true;
                }
                AppWindowAnimator appwindowanimator1;
                if(windowstate.mAppToken == null)
                    appwindowanimator1 = null;
                else
                    appwindowanimator1 = windowstate.mAppToken.mAppAnimator;
                if(appwindowanimator1 != null && appwindowanimator1.animation != null && appwindowanimator1.animating) {
                    if((0x100000 & k) != 0 && appwindowanimator1.animation.getDetachWallpaper())
                        mDetachedWallpaper = windowstate;
                    int l = appwindowanimator1.animation.getBackgroundColor();
                    if(l != 0 && (mWindowAnimationBackground == null || windowstateanimator1.mAnimLayer < mWindowAnimationBackground.mWinAnimator.mAnimLayer)) {
                        mWindowAnimationBackground = windowstate;
                        mWindowAnimationBackgroundColor = l;
                    }
                }
                if(flag1 && !windowstateanimator1.mAnimating && mService.mWallpaperTarget == windowstate) {
                    mBulkUpdateParams = 2 | mBulkUpdateParams;
                    mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                    mService.debugLayoutRepeats("updateWindowsAndWallpaperLocked 2", mPendingLayoutChanges);
                }
                if(mPolicy.doesForceHide(windowstate, windowstate.mAttrs)) {
                    if(!flag1 && flag2) {
                        mBulkUpdateParams = 4 | mBulkUpdateParams;
                        mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                        mService.debugLayoutRepeats("updateWindowsAndWallpaperLocked 3", mPendingLayoutChanges);
                        mService.mFocusMayChange = true;
                    }
                    if(windowstate.isReadyForDisplay() && windowstateanimator1.mAnimationIsEntrance)
                        mForceHiding = true;
                } else
                if(mPolicy.canBeForceHidden(windowstate, windowstate.mAttrs)) {
                    boolean flag3;
                    if(mForceHiding && (!windowstateanimator1.isAnimating() || (0x80000 & windowstateanimator1.mAttrFlags) == 0)) {
                        flag3 = windowstate.hideLw(false, false);
                    } else {
                        flag3 = windowstate.showLw(false, false);
                        if(flag3) {
                            if((4 & mBulkUpdateParams) != 0 && windowstate.isVisibleNow()) {
                                if(arraylist == null)
                                    arraylist = new ArrayList();
                                arraylist.add(windowstateanimator1);
                                if((0x100000 & windowstate.mAttrs.flags) != 0)
                                    flag = true;
                            }
                            if(mCurrentFocus == null || mCurrentFocus.mLayer < windowstate.mLayer)
                                mService.mFocusMayChange = true;
                        }
                    }
                    if(flag3 && (0x100000 & k) != 0) {
                        mBulkUpdateParams = 2 | mBulkUpdateParams;
                        mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                        mService.debugLayoutRepeats("updateWindowsAndWallpaperLocked 4", mPendingLayoutChanges);
                    }
                }
            }
            appwindowtoken = windowstate.mAppToken;
            if(windowstateanimator1.mDrawState == 3 && (appwindowtoken == null || appwindowtoken.allDrawn) && windowstateanimator1.performShowLocked()) {
                mPendingLayoutChanges = 8 | mPendingLayoutChanges;
                mService.debugLayoutRepeats("updateWindowsAndWallpaperLocked 5", mPendingLayoutChanges);
            }
            if(appwindowtoken == null)
                appwindowanimator = null;
            else
                appwindowanimator = appwindowtoken.mAppAnimator;
            if(appwindowanimator != null && appwindowanimator.thumbnail != null) {
                if(appwindowanimator.thumbnailTransactionSeq != mAnimTransactionSequence) {
                    appwindowanimator.thumbnailTransactionSeq = mAnimTransactionSequence;
                    appwindowanimator.thumbnailLayer = 0;
                }
                if(appwindowanimator.thumbnailLayer < windowstateanimator1.mAnimLayer)
                    appwindowanimator.thumbnailLayer = windowstateanimator1.mAnimLayer;
            }
            i--;
        }
        if(arraylist != null) {
            for(int j = -1 + arraylist.size(); j >= 0; j--) {
                Animation animation = mPolicy.createForceHideEnterAnimation(flag);
                if(animation != null) {
                    WindowStateAnimator windowstateanimator = (WindowStateAnimator)arraylist.get(j);
                    windowstateanimator.setAnimation(animation);
                    windowstateanimator.mAnimationIsEntrance = true;
                }
            }

        }
    }

    private void updateWindowsAppsAndRotationAnimationsLocked() {
        ArrayList arraylist = mService.mAnimatingAppTokens;
        int i = arraylist.size();
        int j = 0;
        while(j < i)  {
            AppWindowAnimator appwindowanimator1 = ((AppWindowToken)arraylist.get(j)).mAppAnimator;
            boolean flag1;
            if(appwindowanimator1.animation != null && appwindowanimator1.animation != AppWindowAnimator.sDummyAnimation)
                flag1 = true;
            else
                flag1 = false;
            if(appwindowanimator1.stepAnimationLocked(mCurrentTime, mInnerDw, mInnerDh))
                mAnimating = true;
            else
            if(flag1) {
                mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                mService.debugLayoutRepeats((new StringBuilder()).append("appToken ").append(appwindowanimator1.mAppToken).append(" done").toString(), mPendingLayoutChanges);
            }
            j++;
        }
        int k = mService.mExitingAppTokens.size();
        int l = 0;
        while(l < k)  {
            AppWindowAnimator appwindowanimator = ((AppWindowToken)mService.mExitingAppTokens.get(l)).mAppAnimator;
            boolean flag;
            if(appwindowanimator.animation != null && appwindowanimator.animation != AppWindowAnimator.sDummyAnimation)
                flag = true;
            else
                flag = false;
            if(appwindowanimator.stepAnimationLocked(mCurrentTime, mInnerDw, mInnerDh))
                mAnimating = true;
            else
            if(flag) {
                mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                mService.debugLayoutRepeats((new StringBuilder()).append("exiting appToken ").append(appwindowanimator.mAppToken).append(" done").toString(), mPendingLayoutChanges);
            }
            l++;
        }
        if(mScreenRotationAnimation != null && mScreenRotationAnimation.isAnimating())
            if(mScreenRotationAnimation.stepAnimationLocked(mCurrentTime)) {
                mAnimating = true;
            } else {
                mBulkUpdateParams = 1 | mBulkUpdateParams;
                mScreenRotationAnimation.kill();
                mScreenRotationAnimation = null;
            }
    }

    /**
     * @deprecated Method animate is deprecated
     */

    void animate() {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        mPendingLayoutChanges = 0;
        mCurrentTime = SystemClock.uptimeMillis();
        mBulkUpdateParams = 0;
        flag = mAnimating;
        mAnimating = false;
        Surface.openTransaction();
        updateWindowsAppsAndRotationAnimationsLocked();
        performAnimationsLocked();
        testWallpaperAndBackgroundLocked();
        if(mScreenRotationAnimation != null)
            mScreenRotationAnimation.updateSurfaces();
        int i = mWinAnimators.size();
        for(int j = 0; j < i; j++)
            ((WindowStateAnimator)mWinAnimators.get(j)).prepareSurfaceLocked(true);

        if(mDimParams != null)
            mDimAnimator.updateParameters(mContext.getResources(), mDimParams, mCurrentTime);
        if(mDimAnimator == null || !mDimAnimator.mDimShown) goto _L2; else goto _L1
_L1:
        boolean flag1;
        DimAnimator dimanimator;
        boolean flag2;
        long l;
        flag1 = mAnimating;
        dimanimator = mDimAnimator;
        flag2 = isDimming();
        l = mCurrentTime;
        if(mService.okToDisplay()) goto _L4; else goto _L3
_L3:
        boolean flag3 = true;
_L9:
        mAnimating = flag1 | dimanimator.updateSurface(flag2, l, flag3);
_L2:
        if(mService.mBlackFrame == null) goto _L6; else goto _L5
_L5:
        if(mScreenRotationAnimation == null) goto _L8; else goto _L7
_L7:
        mService.mBlackFrame.setMatrix(mScreenRotationAnimation.getEnterTransformation().getMatrix());
_L6:
        if(mService.mWatermark != null)
            mService.mWatermark.drawIfNeeded();
        Surface.closeTransaction();
_L10:
        mService.bulkSetParameters(mBulkUpdateParams, mPendingLayoutChanges);
        if(!mAnimating)
            break MISSING_BLOCK_LABEL_341;
        mService.scheduleAnimationLocked();
_L11:
        this;
        JVM INSTR monitorexit ;
        return;
_L4:
        flag3 = false;
          goto _L9
_L8:
        mService.mBlackFrame.clearMatrix();
          goto _L6
        RuntimeException runtimeexception;
        runtimeexception;
        Log.wtf("WindowAnimator", "Unhandled exception in Window Manager", runtimeexception);
        Surface.closeTransaction();
          goto _L10
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Surface.closeTransaction();
        throw exception1;
        if(flag)
            mService.requestTraversalLocked();
          goto _L11
    }

    /**
     * @deprecated Method clearPendingActions is deprecated
     */

    void clearPendingActions() {
        this;
        JVM INSTR monitorenter ;
        mPendingActions = 0;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void dump(PrintWriter printwriter, String s, boolean flag) {
        if(flag) {
            if(mWindowDetachedWallpaper != null) {
                printwriter.print(s);
                printwriter.print("mWindowDetachedWallpaper=");
                printwriter.println(mWindowDetachedWallpaper);
            }
            printwriter.print(s);
            printwriter.print("mAnimTransactionSequence=");
            printwriter.println(mAnimTransactionSequence);
            if(mWindowAnimationBackgroundSurface != null) {
                printwriter.print(s);
                printwriter.print("mWindowAnimationBackgroundSurface:");
                mWindowAnimationBackgroundSurface.printTo((new StringBuilder()).append(s).append("  ").toString(), printwriter);
            }
            if(mDimAnimator != null) {
                printwriter.print(s);
                printwriter.print("mDimAnimator:");
                mDimAnimator.printTo((new StringBuilder()).append(s).append("  ").toString(), printwriter);
            } else {
                printwriter.print(s);
                printwriter.print("no DimAnimator ");
            }
        }
    }

    void hideWallpapersLocked(WindowState windowstate) {
        if(mService.mWallpaperTarget == windowstate && mService.mLowerWallpaperTarget == null || mService.mWallpaperTarget == null) {
            WindowToken windowtoken;
label0:
            for(Iterator iterator = mService.mWallpaperTokens.iterator(); iterator.hasNext(); windowtoken.hidden = true) {
                windowtoken = (WindowToken)iterator.next();
                Iterator iterator1 = windowtoken.windows.iterator();
                do {
                    if(!iterator1.hasNext())
                        continue label0;
                    WindowState windowstate1 = (WindowState)iterator1.next();
                    WindowStateAnimator windowstateanimator = windowstate1.mWinAnimator;
                    if(!windowstateanimator.mLastHidden) {
                        windowstateanimator.hide();
                        mService.dispatchWallpaperVisibility(windowstate1, false);
                        mPendingLayoutChanges = 4 | mPendingLayoutChanges;
                    }
                } while(true);
            }

        }
    }

    boolean isDimming() {
        boolean flag;
        if(mDimParams != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isDimming(WindowStateAnimator windowstateanimator) {
        boolean flag;
        if(mDimParams != null && mDimParams.mDimWinAnimator == windowstateanimator)
            flag = true;
        else
            flag = false;
        return flag;
    }

    void setCurrentFocus(WindowState windowstate) {
        mCurrentFocus = windowstate;
    }

    void setDisplayDimensions(int i, int j, int k, int l) {
        mDw = i;
        mDh = j;
        mInnerDw = k;
        mInnerDh = l;
    }

    void startDimming(WindowStateAnimator windowstateanimator, float f, int i, int j) {
        if(mDimAnimator == null)
            mDimAnimator = new DimAnimator(mService.mFxSession);
        WindowStateAnimator windowstateanimator1;
        if(mDimParams == null)
            windowstateanimator1 = null;
        else
            windowstateanimator1 = mDimParams.mDimWinAnimator;
        if(windowstateanimator.mSurfaceShown && (windowstateanimator1 == null || !windowstateanimator1.mSurfaceShown || windowstateanimator1.mAnimLayer < windowstateanimator.mAnimLayer))
            mService.mH.sendMessage(mService.mH.obtainMessage(0x186a3, new DimAnimator.Parameters(windowstateanimator, i, j, f)));
    }

    void stopDimming() {
        mService.mH.sendMessage(mService.mH.obtainMessage(0x186a3, null));
    }

    private static final String TAG = "WindowAnimator";
    static final int WALLPAPER_ACTION_PENDING = 1;
    int mAdjResult;
    private int mAnimTransactionSequence;
    boolean mAnimating;
    int mBulkUpdateParams;
    final Context mContext;
    WindowState mCurrentFocus;
    long mCurrentTime;
    WindowState mDetachedWallpaper;
    int mDh;
    DimAnimator mDimAnimator;
    DimAnimator.Parameters mDimParams;
    int mDw;
    boolean mForceHiding;
    int mInnerDh;
    int mInnerDw;
    int mPendingActions;
    int mPendingLayoutChanges;
    final WindowManagerPolicy mPolicy;
    ScreenRotationAnimation mScreenRotationAnimation;
    final WindowManagerService mService;
    ArrayList mWinAnimators;
    WindowState mWindowAnimationBackground;
    int mWindowAnimationBackgroundColor;
    DimSurface mWindowAnimationBackgroundSurface;
    WindowState mWindowDetachedWallpaper;
}
