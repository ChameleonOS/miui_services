// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.*;
import com.android.server.input.InputManagerService;
import com.android.server.input.InputWindowHandle;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService, WindowStateAnimator, WindowToken, AppWindowToken, 
//            Session, AppWindowAnimator

final class WindowState
    implements android.view.WindowManagerPolicy.WindowState {
    private class DeathRecipient
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            HashMap hashmap = mService.mWindowMap;
            hashmap;
            JVM INSTR monitorenter ;
            WindowState windowstate = mService.windowForClientLocked(mSession, mClient, false);
            Slog.i("WindowState", (new StringBuilder()).append("WIN DEATH: ").append(windowstate).toString());
            if(windowstate != null)
                mService.removeWindowLocked(mSession, windowstate);
            break MISSING_BLOCK_LABEL_101;
            IllegalArgumentException illegalargumentexception;
            illegalargumentexception;
        }

        final WindowState this$0;

        private DeathRecipient() {
            this$0 = WindowState.this;
            super();
        }

    }


    WindowState(WindowManagerService windowmanagerservice, Session session, IWindow iwindow, WindowToken windowtoken, WindowState windowstate, int i, android.view.WindowManager.LayoutParams layoutparams, 
            int j) {
        mAttrs = new android.view.WindowManager.LayoutParams();
        mChildWindows = new ArrayList();
        mPolicyVisibility = true;
        mPolicyVisibilityAfterAnim = true;
        mLayoutSeq = -1;
        mConfiguration = null;
        mShownFrame = new RectF();
        mVisibleInsets = new Rect();
        mLastVisibleInsets = new Rect();
        mContentInsets = new Rect();
        mLastContentInsets = new Rect();
        mGivenContentInsets = new Rect();
        mGivenVisibleInsets = new Rect();
        mGivenTouchableRegion = new Region();
        mTouchableInsets = 0;
        mSystemDecorRect = new Rect();
        mLastSystemDecorRect = new Rect();
        mGlobalScale = 1.0F;
        mInvGlobalScale = 1.0F;
        mHScale = 1.0F;
        mVScale = 1.0F;
        mLastHScale = 1.0F;
        mLastVScale = 1.0F;
        mTmpMatrix = new Matrix();
        mFrame = new Rect();
        mLastFrame = new Rect();
        mCompatFrame = new Rect();
        mContainingFrame = new Rect();
        mDisplayFrame = new Rect();
        mContentFrame = new Rect();
        mParentFrame = new Rect();
        mVisibleFrame = new Rect();
        mWallpaperX = -1F;
        mWallpaperY = -1F;
        mWallpaperXStep = -1F;
        mWallpaperYStep = -1F;
        mHasSurface = false;
        mService = windowmanagerservice;
        mSession = session;
        mClient = iwindow;
        mToken = windowtoken;
        mAttrs.copyFrom(layoutparams);
        mViewVisibility = j;
        mPolicy = mService.mPolicy;
        mContext = mService.mContext;
        DeathRecipient deathrecipient = new DeathRecipient();
        mSeq = i;
        boolean flag;
        if((0x20000000 & mAttrs.flags) != 0)
            flag = true;
        else
            flag = false;
        mEnforceSizeCompat = flag;
        try {
            iwindow.asBinder().linkToDeath(deathrecipient, 0);
            break MISSING_BLOCK_LABEL_433;
        }
        catch(RemoteException remoteexception) {
            mDeathRecipient = null;
            mAttachedWindow = null;
            mLayoutAttached = false;
            mIsImWindow = false;
            mIsWallpaper = false;
            mIsFloatingLayer = false;
            mBaseLayer = 0;
            mSubLayer = 0;
            mInputWindowHandle = null;
            mWinAnimator = null;
        }
_L2:
        return;
        WindowToken windowtoken1;
        mDeathRecipient = deathrecipient;
        WindowState windowstate1;
        if(mAttrs.type >= 1000 && mAttrs.type <= 1999) {
            mBaseLayer = 1000 + 10000 * mPolicy.windowTypeToLayerLw(windowstate.mAttrs.type);
            mSubLayer = mPolicy.subWindowTypeToLayerLw(layoutparams.type);
            mAttachedWindow = windowstate;
            mAttachedWindow.mChildWindows.add(this);
            boolean flag4;
            boolean flag5;
            boolean flag6;
            boolean flag7;
            if(mAttrs.type != 1003)
                flag4 = true;
            else
                flag4 = false;
            mLayoutAttached = flag4;
            if(windowstate.mAttrs.type == 2011 || windowstate.mAttrs.type == 2012)
                flag5 = true;
            else
                flag5 = false;
            mIsImWindow = flag5;
            if(windowstate.mAttrs.type == 2013)
                flag6 = true;
            else
                flag6 = false;
            mIsWallpaper = flag6;
            if(mIsImWindow || mIsWallpaper)
                flag7 = true;
            else
                flag7 = false;
            mIsFloatingLayer = flag7;
        } else {
            mBaseLayer = 1000 + 10000 * mPolicy.windowTypeToLayerLw(layoutparams.type);
            mSubLayer = 0;
            mAttachedWindow = null;
            mLayoutAttached = false;
            boolean flag1;
            boolean flag2;
            boolean flag3;
            if(mAttrs.type == 2011 || mAttrs.type == 2012)
                flag1 = true;
            else
                flag1 = false;
            mIsImWindow = flag1;
            if(mAttrs.type == 2013)
                flag2 = true;
            else
                flag2 = false;
            mIsWallpaper = flag2;
            if(mIsImWindow || mIsWallpaper)
                flag3 = true;
            else
                flag3 = false;
            mIsFloatingLayer = flag3;
        }
        mWinAnimator = new WindowStateAnimator(windowmanagerservice, this, mAttachedWindow);
        mWinAnimator.mAlpha = layoutparams.alpha;
        for(windowstate1 = this; windowstate1.mAttachedWindow != null; windowstate1 = windowstate1.mAttachedWindow);
        windowtoken1 = windowstate1.mToken;
_L3:
label0:
        {
            WindowToken windowtoken2;
            if(windowtoken1.appWindowToken == null) {
                windowtoken2 = (WindowToken)mService.mTokenMap.get(windowtoken1.token);
                if(windowtoken2 != null && windowtoken1 != windowtoken2)
                    break label0;
            }
            mRootToken = windowtoken1;
            mAppToken = windowtoken1.appWindowToken;
            mRequestedWidth = 0;
            mRequestedHeight = 0;
            mLastRequestedWidth = 0;
            mLastRequestedHeight = 0;
            mXOffset = 0;
            mYOffset = 0;
            mLayer = 0;
            com.android.server.input.InputApplicationHandle inputapplicationhandle;
            if(mAppToken != null)
                inputapplicationhandle = mAppToken.mInputApplicationHandle;
            else
                inputapplicationhandle = null;
            mInputWindowHandle = new InputWindowHandle(inputapplicationhandle, this);
        }
        if(true) goto _L2; else goto _L1
_L1:
        windowtoken1 = windowtoken2;
          goto _L3
    }

    private static void applyInsets(Region region, Rect rect, Rect rect1) {
        region.set(rect.left + rect1.left, rect.top + rect1.top, rect.right - rect1.right, rect.bottom - rect1.bottom);
    }

    void attach() {
        mSession.windowAddedLocked();
    }

    public final boolean canReceiveKeys() {
        boolean flag;
        if(isVisibleOrAdding() && mViewVisibility == 0 && (8 & mAttrs.flags) == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void computeFrameLw(Rect rect, Rect rect1, Rect rect2, Rect rect3) {
        mHaveFrame = true;
        Rect rect4 = mContainingFrame;
        rect4.set(rect);
        mDisplayFrame.set(rect1);
        int i = rect4.right - rect4.left;
        int j = rect4.bottom - rect4.top;
        int k;
        int l;
        float f;
        float f1;
        if((0x4000 & mAttrs.flags) != 0) {
            Rect rect5;
            Rect rect6;
            Rect rect7;
            int i1;
            int j1;
            Rect rect8;
            Rect rect9;
            if(mAttrs.width < 0)
                k = i;
            else
            if(mEnforceSizeCompat)
                k = (int)(0.5F + (float)mAttrs.width * mGlobalScale);
            else
                k = mAttrs.width;
            if(mAttrs.height < 0)
                l = j;
            else
            if(mEnforceSizeCompat)
                l = (int)(0.5F + (float)mAttrs.height * mGlobalScale);
            else
                l = mAttrs.height;
        } else {
            if(mAttrs.width == -1)
                k = i;
            else
            if(mEnforceSizeCompat)
                k = (int)(0.5F + (float)mRequestedWidth * mGlobalScale);
            else
                k = mRequestedWidth;
            if(mAttrs.height == -1)
                l = j;
            else
            if(mEnforceSizeCompat)
                l = (int)(0.5F + (float)mRequestedHeight * mGlobalScale);
            else
                l = mRequestedHeight;
        }
        if(!mParentFrame.equals(rect)) {
            mParentFrame.set(rect);
            mContentChanged = true;
        }
        if(mRequestedWidth != mLastRequestedWidth || mRequestedHeight != mLastRequestedHeight) {
            mLastRequestedWidth = mRequestedWidth;
            mLastRequestedHeight = mRequestedHeight;
            mContentChanged = true;
        }
        rect5 = mContentFrame;
        rect5.set(rect2);
        rect6 = mVisibleFrame;
        rect6.set(rect3);
        rect7 = mFrame;
        i1 = rect7.width();
        j1 = rect7.height();
        if(mEnforceSizeCompat) {
            f = (float)mAttrs.x * mGlobalScale;
            f1 = (float)mAttrs.y * mGlobalScale;
        } else {
            f = mAttrs.x;
            f1 = mAttrs.y;
        }
        Gravity.apply(mAttrs.gravity, k, l, rect4, (int)(f + mAttrs.horizontalMargin * (float)i), (int)(f1 + mAttrs.verticalMargin * (float)j), rect7);
        Gravity.applyDisplay(mAttrs.gravity, rect1, rect7);
        if(rect5.left < rect7.left)
            rect5.left = rect7.left;
        if(rect5.top < rect7.top)
            rect5.top = rect7.top;
        if(rect5.right > rect7.right)
            rect5.right = rect7.right;
        if(rect5.bottom > rect7.bottom)
            rect5.bottom = rect7.bottom;
        if(rect6.left < rect7.left)
            rect6.left = rect7.left;
        if(rect6.top < rect7.top)
            rect6.top = rect7.top;
        if(rect6.right > rect7.right)
            rect6.right = rect7.right;
        if(rect6.bottom > rect7.bottom)
            rect6.bottom = rect7.bottom;
        rect8 = mContentInsets;
        rect8.left = rect5.left - rect7.left;
        rect8.top = rect5.top - rect7.top;
        rect8.right = rect7.right - rect5.right;
        rect8.bottom = rect7.bottom - rect5.bottom;
        rect9 = mVisibleInsets;
        rect9.left = rect6.left - rect7.left;
        rect9.top = rect6.top - rect7.top;
        rect9.right = rect7.right - rect6.right;
        rect9.bottom = rect7.bottom - rect6.bottom;
        mCompatFrame.set(rect7);
        if(mEnforceSizeCompat) {
            rect8.scale(mInvGlobalScale);
            rect9.scale(mInvGlobalScale);
            mCompatFrame.scale(mInvGlobalScale);
        }
        if(mIsWallpaper && (i1 != rect7.width() || j1 != rect7.height()))
            mService.updateWallpaperOffsetLocked(this, mService.mAppDisplayWidth, mService.mAppDisplayHeight, false);
    }

    void disposeInputChannel() {
        if(mInputChannel != null) {
            mService.mInputManager.unregisterInputChannel(mInputChannel);
            mInputChannel.dispose();
            mInputChannel = null;
        }
        mInputWindowHandle.inputChannel = null;
    }

    void dump(PrintWriter printwriter, String s, boolean flag) {
        printwriter.print(s);
        printwriter.print("mSession=");
        printwriter.print(mSession);
        printwriter.print(" mClient=");
        printwriter.println(mClient.asBinder());
        printwriter.print(s);
        printwriter.print("mAttrs=");
        printwriter.println(mAttrs);
        printwriter.print(s);
        printwriter.print("Requested w=");
        printwriter.print(mRequestedWidth);
        printwriter.print(" h=");
        printwriter.print(mRequestedHeight);
        printwriter.print(" mLayoutSeq=");
        printwriter.println(mLayoutSeq);
        if(mRequestedWidth != mLastRequestedWidth || mRequestedHeight != mLastRequestedHeight) {
            printwriter.print(s);
            printwriter.print("LastRequested w=");
            printwriter.print(mLastRequestedWidth);
            printwriter.print(" h=");
            printwriter.println(mLastRequestedHeight);
        }
        if(mAttachedWindow != null || mLayoutAttached) {
            printwriter.print(s);
            printwriter.print("mAttachedWindow=");
            printwriter.print(mAttachedWindow);
            printwriter.print(" mLayoutAttached=");
            printwriter.println(mLayoutAttached);
        }
        if(mIsImWindow || mIsWallpaper || mIsFloatingLayer) {
            printwriter.print(s);
            printwriter.print("mIsImWindow=");
            printwriter.print(mIsImWindow);
            printwriter.print(" mIsWallpaper=");
            printwriter.print(mIsWallpaper);
            printwriter.print(" mIsFloatingLayer=");
            printwriter.print(mIsFloatingLayer);
            printwriter.print(" mWallpaperVisible=");
            printwriter.println(mWallpaperVisible);
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("mBaseLayer=");
            printwriter.print(mBaseLayer);
            printwriter.print(" mSubLayer=");
            printwriter.print(mSubLayer);
            printwriter.print(" mAnimLayer=");
            printwriter.print(mLayer);
            printwriter.print("+");
            int i;
            if(mTargetAppToken != null)
                i = mTargetAppToken.mAppAnimator.animLayerAdjustment;
            else
            if(mAppToken != null)
                i = mAppToken.mAppAnimator.animLayerAdjustment;
            else
                i = 0;
            printwriter.print(i);
            printwriter.print("=");
            printwriter.print(mWinAnimator.mAnimLayer);
            printwriter.print(" mLastLayer=");
            printwriter.println(mWinAnimator.mLastLayer);
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("mToken=");
            printwriter.println(mToken);
            printwriter.print(s);
            printwriter.print("mRootToken=");
            printwriter.println(mRootToken);
            if(mAppToken != null) {
                printwriter.print(s);
                printwriter.print("mAppToken=");
                printwriter.println(mAppToken);
            }
            if(mTargetAppToken != null) {
                printwriter.print(s);
                printwriter.print("mTargetAppToken=");
                printwriter.println(mTargetAppToken);
            }
            printwriter.print(s);
            printwriter.print("mViewVisibility=0x");
            printwriter.print(Integer.toHexString(mViewVisibility));
            printwriter.print(" mHaveFrame=");
            printwriter.print(mHaveFrame);
            printwriter.print(" mObscured=");
            printwriter.println(mObscured);
            printwriter.print(s);
            printwriter.print("mSeq=");
            printwriter.print(mSeq);
            printwriter.print(" mSystemUiVisibility=0x");
            printwriter.println(Integer.toHexString(mSystemUiVisibility));
        }
        if(!mPolicyVisibility || !mPolicyVisibilityAfterAnim || mAttachedHidden) {
            printwriter.print(s);
            printwriter.print("mPolicyVisibility=");
            printwriter.print(mPolicyVisibility);
            printwriter.print(" mPolicyVisibilityAfterAnim=");
            printwriter.print(mPolicyVisibilityAfterAnim);
            printwriter.print(" mAttachedHidden=");
            printwriter.println(mAttachedHidden);
        }
        if(!mRelayoutCalled || mLayoutNeeded) {
            printwriter.print(s);
            printwriter.print("mRelayoutCalled=");
            printwriter.print(mRelayoutCalled);
            printwriter.print(" mLayoutNeeded=");
            printwriter.println(mLayoutNeeded);
        }
        if(mXOffset != 0 || mYOffset != 0) {
            printwriter.print(s);
            printwriter.print("Offsets x=");
            printwriter.print(mXOffset);
            printwriter.print(" y=");
            printwriter.println(mYOffset);
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("mGivenContentInsets=");
            mGivenContentInsets.printShortString(printwriter);
            printwriter.print(" mGivenVisibleInsets=");
            mGivenVisibleInsets.printShortString(printwriter);
            printwriter.println();
            if(mTouchableInsets != 0 || mGivenInsetsPending) {
                printwriter.print(s);
                printwriter.print("mTouchableInsets=");
                printwriter.print(mTouchableInsets);
                printwriter.print(" mGivenInsetsPending=");
                printwriter.println(mGivenInsetsPending);
            }
            printwriter.print(s);
            printwriter.print("mConfiguration=");
            printwriter.println(mConfiguration);
        }
        printwriter.print(s);
        printwriter.print("mHasSurface=");
        printwriter.print(mHasSurface);
        printwriter.print(" mShownFrame=");
        mShownFrame.printShortString(printwriter);
        printwriter.println();
        if(flag) {
            printwriter.print(s);
            printwriter.print("mFrame=");
            mFrame.printShortString(printwriter);
            printwriter.print(" last=");
            mLastFrame.printShortString(printwriter);
            printwriter.println();
            printwriter.print(s);
            printwriter.print("mSystemDecorRect=");
            mSystemDecorRect.printShortString(printwriter);
            printwriter.print(" last=");
            mLastSystemDecorRect.printShortString(printwriter);
            printwriter.println();
        }
        if(mEnforceSizeCompat) {
            printwriter.print(s);
            printwriter.print("mCompatFrame=");
            mCompatFrame.printShortString(printwriter);
            printwriter.println();
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("Frames: containing=");
            mContainingFrame.printShortString(printwriter);
            printwriter.print(" parent=");
            mParentFrame.printShortString(printwriter);
            printwriter.print(" display=");
            mDisplayFrame.printShortString(printwriter);
            printwriter.println();
            printwriter.print(s);
            printwriter.print("    content=");
            mContentFrame.printShortString(printwriter);
            printwriter.print(" visible=");
            mVisibleFrame.printShortString(printwriter);
            printwriter.println();
            printwriter.print(s);
            printwriter.print("Cur insets: content=");
            mContentInsets.printShortString(printwriter);
            printwriter.print(" visible=");
            mVisibleInsets.printShortString(printwriter);
            printwriter.println();
            printwriter.print(s);
            printwriter.print("Lst insets: content=");
            mLastContentInsets.printShortString(printwriter);
            printwriter.print(" visible=");
            mLastVisibleInsets.printShortString(printwriter);
            printwriter.println();
        }
        mWinAnimator.dump(printwriter, s, flag);
        if(mExiting || mRemoveOnExit || mDestroying || mRemoved) {
            printwriter.print(s);
            printwriter.print("mExiting=");
            printwriter.print(mExiting);
            printwriter.print(" mRemoveOnExit=");
            printwriter.print(mRemoveOnExit);
            printwriter.print(" mDestroying=");
            printwriter.print(mDestroying);
            printwriter.print(" mRemoved=");
            printwriter.println(mRemoved);
        }
        if(mOrientationChanging || mAppFreezing || mTurnOnScreen) {
            printwriter.print(s);
            printwriter.print("mOrientationChanging=");
            printwriter.print(mOrientationChanging);
            printwriter.print(" mAppFreezing=");
            printwriter.print(mAppFreezing);
            printwriter.print(" mTurnOnScreen=");
            printwriter.println(mTurnOnScreen);
        }
        if(mHScale != 1.0F || mVScale != 1.0F) {
            printwriter.print(s);
            printwriter.print("mHScale=");
            printwriter.print(mHScale);
            printwriter.print(" mVScale=");
            printwriter.println(mVScale);
        }
        if(mWallpaperX != -1F || mWallpaperY != -1F) {
            printwriter.print(s);
            printwriter.print("mWallpaperX=");
            printwriter.print(mWallpaperX);
            printwriter.print(" mWallpaperY=");
            printwriter.println(mWallpaperY);
        }
        if(mWallpaperXStep != -1F || mWallpaperYStep != -1F) {
            printwriter.print(s);
            printwriter.print("mWallpaperXStep=");
            printwriter.print(mWallpaperXStep);
            printwriter.print(" mWallpaperYStep=");
            printwriter.println(mWallpaperYStep);
        }
    }

    public IApplicationToken getAppToken() {
        IApplicationToken iapplicationtoken;
        if(mAppToken != null)
            iapplicationtoken = mAppToken.appToken;
        else
            iapplicationtoken = null;
        return iapplicationtoken;
    }

    public android.view.WindowManager.LayoutParams getAttrs() {
        return mAttrs;
    }

    public Rect getContentFrameLw() {
        return mContentFrame;
    }

    public Rect getDisplayFrameLw() {
        return mDisplayFrame;
    }

    public Rect getFrameLw() {
        return mFrame;
    }

    public Rect getGivenContentInsetsLw() {
        return mGivenContentInsets;
    }

    public boolean getGivenInsetsPendingLw() {
        return mGivenInsetsPending;
    }

    public Rect getGivenVisibleInsetsLw() {
        return mGivenVisibleInsets;
    }

    public long getInputDispatchingTimeoutNanos() {
        long l;
        if(mAppToken != null)
            l = mAppToken.inputDispatchingTimeoutNanos;
        else
            l = 0x12a05f200L;
        return l;
    }

    public boolean getNeedsMenuLw(android.view.WindowManagerPolicy.WindowState windowstate) {
        boolean flag;
        int i;
        WindowState windowstate1;
        flag = false;
        i = -1;
        windowstate1 = this;
_L7:
        if((8 & windowstate1.mAttrs.privateFlags) == 0) goto _L2; else goto _L1
_L1:
        if((0x8000000 & windowstate1.mAttrs.flags) != 0)
            flag = true;
_L4:
        return flag;
_L2:
        if(windowstate1 == windowstate) goto _L4; else goto _L3
_L3:
        if(i < 0)
            i = mService.mWindows.indexOf(windowstate1);
        if(--i < 0) goto _L4; else goto _L5
_L5:
        windowstate1 = (WindowState)mService.mWindows.get(i);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public RectF getShownFrameLw() {
        return mShownFrame;
    }

    public int getSurfaceLayer() {
        return mLayer;
    }

    public int getSystemUiVisibility() {
        return mSystemUiVisibility;
    }

    public void getTouchableRegion(Region region) {
        Rect rect = mFrame;
        mTouchableInsets;
        JVM INSTR tableswitch 1 3: default 36
    //                   1 43
    //                   2 55
    //                   3 67;
           goto _L1 _L2 _L3 _L4
_L1:
        region.set(rect);
_L6:
        return;
_L2:
        applyInsets(region, rect, mGivenContentInsets);
        continue; /* Loop/switch isn't completed */
_L3:
        applyInsets(region, rect, mGivenVisibleInsets);
        continue; /* Loop/switch isn't completed */
_L4:
        region.set(mGivenTouchableRegion);
        region.translate(rect.left, rect.top);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Rect getVisibleFrameLw() {
        return mVisibleFrame;
    }

    public boolean hasAppShownWindows() {
        boolean flag;
        if(mAppToken != null && (mAppToken.firstWindowDrawn || mAppToken.startingDisplayed))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean hasDrawnLw() {
        boolean flag;
        if(mWinAnimator.mDrawState == 4)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean hideLw(boolean flag) {
        return hideLw(flag, true);
    }

    boolean hideLw(boolean flag, boolean flag1) {
        boolean flag2;
        flag2 = false;
        if(flag && !mService.okToDisplay())
            flag = false;
        boolean flag3;
        if(flag)
            flag3 = mPolicyVisibilityAfterAnim;
        else
            flag3 = mPolicyVisibility;
        if(flag3) goto _L2; else goto _L1
_L1:
        return flag2;
_L2:
        if(flag) {
            mWinAnimator.applyAnimationLocked(8194, false);
            if(mWinAnimator.mAnimation == null)
                flag = false;
        }
        if(!flag)
            break; /* Loop/switch isn't completed */
        mPolicyVisibilityAfterAnim = false;
_L4:
        if(flag1)
            mService.scheduleAnimationLocked();
        flag2 = true;
        if(true) goto _L1; else goto _L3
_L3:
        mPolicyVisibilityAfterAnim = false;
        mPolicyVisibility = false;
        mService.enableScreenIfNeededLocked();
        if(mService.mCurrentFocus == this)
            mService.mFocusMayChange = true;
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    public boolean isAlive() {
        return mClient.asBinder().isBinderAlive();
    }

    public boolean isAnimatingLw() {
        boolean flag;
        if(mWinAnimator.mAnimation != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isDisplayedLw() {
        AppWindowToken appwindowtoken = mAppToken;
        boolean flag;
        if(isDrawnLw() && mPolicyVisibility && (!mAttachedHidden && (appwindowtoken == null || !appwindowtoken.hiddenRequested) || mWinAnimator.mAnimating))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isDrawnLw() {
        boolean flag;
        if(mHasSurface && !mDestroying && (mWinAnimator.mDrawState == 3 || mWinAnimator.mDrawState == 4))
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isFullscreen(int i, int j) {
        boolean flag;
        if(mFrame.left <= 0 && mFrame.top <= 0 && mFrame.right >= i && mFrame.bottom >= j)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isGoneForLayoutLw() {
        AppWindowToken appwindowtoken = mAppToken;
        boolean flag;
        if(mViewVisibility == 8 || !mRelayoutCalled || appwindowtoken == null && mRootToken.hidden || appwindowtoken != null && (appwindowtoken.hiddenRequested || ((WindowToken) (appwindowtoken)).hidden) || mAttachedHidden || mExiting || mDestroying)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isIdentityMatrix(float f, float f1, float f2, float f3) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_3;
        if(f >= 0.99999F && f <= 1.00001F && f3 >= 0.99999F && f3 <= 1.00001F && f1 >= -1E-06F && f1 <= 1E-06F && f2 >= -1E-06F && f2 <= 1E-06F)
            flag = true;
        return flag;
    }

    boolean isOnScreen() {
        boolean flag = false;
        if(mHasSurface && mPolicyVisibility && !mDestroying) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        AppWindowToken appwindowtoken = mAppToken;
        if(appwindowtoken != null) {
            if(!mAttachedHidden && !appwindowtoken.hiddenRequested || mWinAnimator.mAnimation != null || appwindowtoken.mAppAnimator.animation != null)
                flag = true;
        } else
        if(!mAttachedHidden || mWinAnimator.mAnimation != null)
            flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    boolean isOpaqueDrawn() {
        boolean flag;
        if((mAttrs.format == -1 || mAttrs.type == 2013) && isDrawnLw() && mWinAnimator.mAnimation == null && (mAppToken == null || mAppToken.mAppAnimator.animation == null))
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isPotentialDragTarget() {
        boolean flag;
        if(isVisibleNow() && !mRemoved && mInputChannel != null && mInputWindowHandle != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isReadyForDisplay() {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if((!mRootToken.waitingToShow || mService.mNextAppTransition == -1) && mHasSurface && mPolicyVisibility && !mDestroying && (!mAttachedHidden && mViewVisibility == 0 && !mRootToken.hidden || mWinAnimator.mAnimation != null || mAppToken != null && mAppToken.mAppAnimator.animation != null))
            flag = true;
        return flag;
    }

    boolean isReadyForDisplayIgnoringKeyguard() {
        boolean flag = false;
        if(!mRootToken.waitingToShow || mService.mNextAppTransition == -1) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        AppWindowToken appwindowtoken = mAppToken;
        if((appwindowtoken != null || mPolicyVisibility) && mHasSurface && !mDestroying && (!mAttachedHidden && mViewVisibility == 0 && !mRootToken.hidden || mWinAnimator.mAnimation != null || appwindowtoken != null && appwindowtoken.mAppAnimator.animation != null && !mWinAnimator.isDummyAnimation()))
            flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean isVisibleLw() {
        AppWindowToken appwindowtoken = mAppToken;
        boolean flag;
        if(mHasSurface && mPolicyVisibility && !mAttachedHidden && (appwindowtoken == null || !appwindowtoken.hiddenRequested) && !mExiting && !mDestroying)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isVisibleNow() {
        boolean flag;
        if(mHasSurface && mPolicyVisibility && !mAttachedHidden && !mRootToken.hidden && !mExiting && !mDestroying)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean isVisibleOrAdding() {
        AppWindowToken appwindowtoken = mAppToken;
        boolean flag;
        if((mHasSurface || !mRelayoutCalled && mViewVisibility == 0) && mPolicyVisibility && !mAttachedHidden && (appwindowtoken == null || !appwindowtoken.hiddenRequested) && !mExiting && !mDestroying)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isVisibleOrBehindKeyguardLw() {
        boolean flag = true;
        boolean flag1 = false;
        if(!mRootToken.waitingToShow || mService.mNextAppTransition == -1) {
            AppWindowToken appwindowtoken = mAppToken;
            boolean flag2;
            if(appwindowtoken != null) {
                if(appwindowtoken.mAppAnimator.animation != null)
                    flag2 = flag;
                else
                    flag2 = false;
            } else {
                flag2 = false;
            }
            if(!mHasSurface || mDestroying || mExiting || (appwindowtoken != null ? appwindowtoken.hiddenRequested : !mPolicyVisibility) || (mAttachedHidden || mViewVisibility != 0 || mRootToken.hidden) && mWinAnimator.mAnimation == null && !flag2)
                flag = false;
            flag1 = flag;
        }
        return flag1;
    }

    public boolean isWinVisibleLw() {
        AppWindowToken appwindowtoken = mAppToken;
        boolean flag;
        if(mHasSurface && mPolicyVisibility && !mAttachedHidden && (appwindowtoken == null || !appwindowtoken.hiddenRequested || appwindowtoken.mAppAnimator.animating) && !mExiting && !mDestroying)
            flag = true;
        else
            flag = false;
        return flag;
    }

    String makeInputChannelName() {
        return (new StringBuilder()).append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(mAttrs.getTitle()).toString();
    }

    void prelayout() {
        if(mEnforceSizeCompat) {
            mGlobalScale = mService.mCompatibleScreenScale;
            mInvGlobalScale = 1.0F / mGlobalScale;
        } else {
            mInvGlobalScale = 1.0F;
            mGlobalScale = 1.0F;
        }
    }

    void removeLocked() {
        disposeInputChannel();
        if(mAttachedWindow != null)
            mAttachedWindow.mChildWindows.remove(this);
        mWinAnimator.destroyDeferredSurfaceLocked();
        mWinAnimator.destroySurfaceLocked();
        mSession.windowRemovedLocked();
        mClient.asBinder().unlinkToDeath(mDeathRecipient, 0);
_L2:
        return;
        RuntimeException runtimeexception;
        runtimeexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void setInputChannel(InputChannel inputchannel) {
        if(mInputChannel != null) {
            throw new IllegalStateException("Window already has an input channel.");
        } else {
            mInputChannel = inputchannel;
            mInputWindowHandle.inputChannel = inputchannel;
            return;
        }
    }

    boolean shouldAnimateMove() {
        boolean flag;
        if(mContentChanged && !mExiting && !mWinAnimator.mLastHidden && mService.okToDisplay() && (mFrame.top != mLastFrame.top || mFrame.left != mLastFrame.left) && (mAttachedWindow == null || !mAttachedWindow.shouldAnimateMove()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean showLw(boolean flag) {
        return showLw(flag, true);
    }

    boolean showLw(boolean flag, boolean flag1) {
        boolean flag2 = true;
        if(!mPolicyVisibility || !mPolicyVisibilityAfterAnim) goto _L2; else goto _L1
_L1:
        flag2 = false;
_L4:
        return flag2;
_L2:
        if(flag) {
            if(mService.okToDisplay())
                break; /* Loop/switch isn't completed */
            flag = false;
        }
_L6:
        mPolicyVisibility = flag2;
        mPolicyVisibilityAfterAnim = flag2;
        if(flag)
            mWinAnimator.applyAnimationLocked(4097, flag2);
        if(flag1)
            mService.scheduleAnimationLocked();
        if(true) goto _L4; else goto _L3
_L3:
        if(!mPolicyVisibility || mWinAnimator.mAnimation != null) goto _L6; else goto _L5
_L5:
        flag = false;
          goto _L6
    }

    public String toString() {
        if(mStringNameCache == null || mLastTitle != mAttrs.getTitle() || mWasPaused != mToken.paused) {
            mLastTitle = mAttrs.getTitle();
            mWasPaused = mToken.paused;
            mStringNameCache = (new StringBuilder()).append("Window{").append(Integer.toHexString(System.identityHashCode(this))).append(" ").append(mLastTitle).append(" paused=").append(mWasPaused).append("}").toString();
        }
        return mStringNameCache;
    }

    static final boolean DEBUG_VISIBILITY = false;
    static final boolean SHOW_LIGHT_TRANSACTIONS = false;
    static final boolean SHOW_SURFACE_ALLOC = false;
    static final boolean SHOW_TRANSACTIONS = false;
    static final String TAG = "WindowState";
    boolean mAppFreezing;
    AppWindowToken mAppToken;
    boolean mAttachedHidden;
    final WindowState mAttachedWindow;
    final android.view.WindowManager.LayoutParams mAttrs;
    final int mBaseLayer;
    final ArrayList mChildWindows;
    final IWindow mClient;
    final Rect mCompatFrame;
    Configuration mConfiguration;
    final Rect mContainingFrame;
    boolean mContentChanged;
    final Rect mContentFrame;
    final Rect mContentInsets;
    boolean mContentInsetsChanged;
    final Context mContext;
    final DeathRecipient mDeathRecipient;
    boolean mDestroying;
    final Rect mDisplayFrame;
    boolean mEnforceSizeCompat;
    boolean mExiting;
    final Rect mFrame;
    final Rect mGivenContentInsets;
    boolean mGivenInsetsPending;
    final Region mGivenTouchableRegion;
    final Rect mGivenVisibleInsets;
    float mGlobalScale;
    float mHScale;
    boolean mHasSurface;
    boolean mHaveFrame;
    InputChannel mInputChannel;
    final InputWindowHandle mInputWindowHandle;
    float mInvGlobalScale;
    final boolean mIsFloatingLayer;
    final boolean mIsImWindow;
    final boolean mIsWallpaper;
    final Rect mLastContentInsets;
    final Rect mLastFrame;
    float mLastHScale;
    int mLastRequestedHeight;
    int mLastRequestedWidth;
    final Rect mLastSystemDecorRect;
    CharSequence mLastTitle;
    float mLastVScale;
    final Rect mLastVisibleInsets;
    int mLayer;
    final boolean mLayoutAttached;
    boolean mLayoutNeeded;
    int mLayoutSeq;
    boolean mObscured;
    boolean mOrientationChanging;
    final Rect mParentFrame;
    final WindowManagerPolicy mPolicy;
    boolean mPolicyVisibility;
    boolean mPolicyVisibilityAfterAnim;
    boolean mRebuilding;
    boolean mRelayoutCalled;
    boolean mRemoveOnExit;
    boolean mRemoved;
    int mRequestedHeight;
    int mRequestedWidth;
    WindowToken mRootToken;
    int mSeq;
    final WindowManagerService mService;
    final Session mSession;
    final RectF mShownFrame;
    String mStringNameCache;
    final int mSubLayer;
    final Rect mSystemDecorRect;
    int mSystemUiVisibility;
    AppWindowToken mTargetAppToken;
    final Matrix mTmpMatrix;
    WindowToken mToken;
    int mTouchableInsets;
    boolean mTurnOnScreen;
    float mVScale;
    int mViewVisibility;
    final Rect mVisibleFrame;
    final Rect mVisibleInsets;
    boolean mVisibleInsetsChanged;
    boolean mWallpaperVisible;
    float mWallpaperX;
    float mWallpaperXStep;
    float mWallpaperY;
    float mWallpaperYStep;
    boolean mWasPaused;
    final WindowStateAnimator mWinAnimator;
    int mXOffset;
    int mYOffset;
}
