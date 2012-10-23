.class public Lcom/android/server/wm/WindowAnimator;
.super Ljava/lang/Object;
.source "WindowAnimator.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/wm/WindowAnimator$SetAnimationParams;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "WindowAnimator"

.field static final WALLPAPER_ACTION_PENDING:I = 0x1


# instance fields
.field mAdjResult:I

.field private mAnimTransactionSequence:I

.field mAnimating:Z

.field mBulkUpdateParams:I

.field final mContext:Landroid/content/Context;

.field mCurrentFocus:Lcom/android/server/wm/WindowState;

.field mCurrentTime:J

.field mDetachedWallpaper:Lcom/android/server/wm/WindowState;

.field mDh:I

.field mDimAnimator:Lcom/android/server/wm/DimAnimator;

.field mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

.field mDw:I

.field mForceHiding:Z

.field mInnerDh:I

.field mInnerDw:I

.field mPendingActions:I

.field mPendingLayoutChanges:I

.field final mPolicy:Landroid/view/WindowManagerPolicy;

.field mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

.field final mService:Lcom/android/server/wm/WindowManagerService;

.field mWinAnimators:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/wm/WindowStateAnimator;",
            ">;"
        }
    .end annotation
.end field

.field mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

.field mWindowAnimationBackgroundColor:I

.field mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

.field mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;


# direct methods
.method constructor <init>(Lcom/android/server/wm/WindowManagerService;Landroid/content/Context;Landroid/view/WindowManagerPolicy;)V
    .registers 6
    .parameter "service"
    .parameter "context"
    .parameter "policy"

    .prologue
    const/4 v1, 0x0

    .line 82
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 39
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mWinAnimators:Ljava/util/ArrayList;

    .line 63
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    .line 69
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 70
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 71
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    .line 73
    const/4 v0, 0x0

    iput v0, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 75
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    .line 76
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    .line 83
    iput-object p1, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    .line 84
    iput-object p2, p0, Lcom/android/server/wm/WindowAnimator;->mContext:Landroid/content/Context;

    .line 85
    iput-object p3, p0, Lcom/android/server/wm/WindowAnimator;->mPolicy:Landroid/view/WindowManagerPolicy;

    .line 86
    return-void
.end method

.method private performAnimationsLocked()V
    .registers 3

    .prologue
    const/4 v1, 0x0

    const/4 v0, 0x0

    .line 414
    iput-boolean v0, p0, Lcom/android/server/wm/WindowAnimator;->mForceHiding:Z

    .line 415
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 416
    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    .line 417
    iput v0, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundColor:I

    .line 419
    invoke-direct {p0}, Lcom/android/server/wm/WindowAnimator;->updateWindowsAndWallpaperLocked()V

    .line 420
    iget v0, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    and-int/lit8 v0, v0, 0x4

    if-eqz v0, :cond_19

    .line 421
    iget v0, p0, Lcom/android/server/wm/WindowAnimator;->mPendingActions:I

    or-int/lit8 v0, v0, 0x1

    iput v0, p0, Lcom/android/server/wm/WindowAnimator;->mPendingActions:I

    .line 424
    :cond_19
    invoke-direct {p0}, Lcom/android/server/wm/WindowAnimator;->testTokenMayBeDrawnLocked()V

    .line 425
    return-void
.end method

.method private testTokenMayBeDrawnLocked()V
    .registers 9

    .prologue
    .line 377
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v2, v5, Lcom/android/server/wm/WindowManagerService;->mAnimatingAppTokens:Ljava/util/ArrayList;

    .line 378
    .local v2, appTokens:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/wm/AppWindowToken;>;"
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 379
    .local v0, NT:I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_9
    if-ge v3, v0, :cond_5f

    .line 380
    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/wm/AppWindowToken;

    .line 381
    .local v4, wtoken:Lcom/android/server/wm/AppWindowToken;
    iget-boolean v1, v4, Lcom/android/server/wm/AppWindowToken;->allDrawn:Z

    .line 382
    .local v1, allDrawn:Z
    iget-object v5, v4, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    iget-boolean v5, v5, Lcom/android/server/wm/AppWindowAnimator;->allDrawn:Z

    if-eq v1, v5, :cond_37

    .line 383
    iget-object v5, v4, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    iput-boolean v1, v5, Lcom/android/server/wm/AppWindowAnimator;->allDrawn:Z

    .line 384
    if-eqz v1, :cond_37

    .line 387
    iget-object v5, v4, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    iget-boolean v5, v5, Lcom/android/server/wm/AppWindowAnimator;->freezingScreen:Z

    if-eqz v5, :cond_3a

    .line 388
    iget-object v5, v4, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    invoke-virtual {v5}, Lcom/android/server/wm/AppWindowAnimator;->showAllWindowsLocked()Z

    .line 389
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const/4 v6, 0x0

    const/4 v7, 0x1

    invoke-virtual {v5, v4, v6, v7}, Lcom/android/server/wm/WindowManagerService;->unsetAppFreezingScreenLocked(Lcom/android/server/wm/AppWindowToken;ZZ)V

    .line 395
    iget v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v5, v5, 0x4

    iput v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 379
    :cond_37
    :goto_37
    add-int/lit8 v3, v3, 0x1

    goto :goto_9

    .line 397
    :cond_3a
    iget v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v5, v5, 0x8

    iput v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 399
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const-string v6, "testTokenMayBeDrawnLocked"

    iget v7, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    invoke-virtual {v5, v6, v7}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    .line 404
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v5, v5, Lcom/android/server/wm/WindowManagerService;->mOpeningApps:Ljava/util/ArrayList;

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_37

    .line 405
    iget-boolean v5, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    iget-object v6, v4, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    invoke-virtual {v6}, Lcom/android/server/wm/AppWindowAnimator;->showAllWindowsLocked()Z

    move-result v6

    or-int/2addr v5, v6

    iput-boolean v5, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    goto :goto_37

    .line 411
    .end local v1           #allDrawn:Z
    .end local v4           #wtoken:Lcom/android/server/wm/AppWindowToken;
    :cond_5f
    return-void
.end method

.method private testWallpaperAndBackgroundLocked()V
    .registers 10

    .prologue
    .line 106
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;

    iget-object v7, p0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    if-eq v6, v7, :cond_10

    .line 110
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    iput-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 111
    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    or-int/lit8 v6, v6, 0x2

    iput v6, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 114
    :cond_10
    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundColor:I

    if-eqz v6, :cond_66

    .line 118
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    .line 119
    .local v4, target:Lcom/android/server/wm/WindowState;
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-eq v6, v4, :cond_28

    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mLowerWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-eq v6, v4, :cond_28

    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mUpperWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-ne v6, v4, :cond_42

    .line 122
    :cond_28
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mWindows:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 123
    .local v0, N:I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_31
    if-ge v3, v0, :cond_42

    .line 124
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mWindows:Ljava/util/ArrayList;

    invoke-virtual {v6, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/android/server/wm/WindowState;

    .line 125
    .local v5, w:Lcom/android/server/wm/WindowState;
    iget-boolean v6, v5, Lcom/android/server/wm/WindowState;->mIsWallpaper:Z

    if-eqz v6, :cond_63

    .line 126
    move-object v4, v5

    .line 131
    .end local v0           #N:I
    .end local v3           #i:I
    .end local v5           #w:Lcom/android/server/wm/WindowState;
    :cond_42
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    if-nez v6, :cond_51

    .line 132
    new-instance v6, Lcom/android/server/wm/DimSurface;

    iget-object v7, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v7, v7, Lcom/android/server/wm/WindowManagerService;->mFxSession:Landroid/view/SurfaceSession;

    invoke-direct {v6, v7}, Lcom/android/server/wm/DimSurface;-><init>(Landroid/view/SurfaceSession;)V

    iput-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    .line 134
    :cond_51
    iget v2, p0, Lcom/android/server/wm/WindowAnimator;->mDw:I

    .line 135
    .local v2, dw:I
    iget v1, p0, Lcom/android/server/wm/WindowAnimator;->mDh:I

    .line 136
    .local v1, dh:I
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    iget-object v7, v4, Lcom/android/server/wm/WindowState;->mWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    iget v7, v7, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    add-int/lit8 v7, v7, -0x1

    iget v8, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundColor:I

    invoke-virtual {v6, v2, v1, v7, v8}, Lcom/android/server/wm/DimSurface;->show(IIII)V

    .line 142
    .end local v1           #dh:I
    .end local v2           #dw:I
    .end local v4           #target:Lcom/android/server/wm/WindowState;
    :cond_62
    :goto_62
    return-void

    .line 123
    .restart local v0       #N:I
    .restart local v3       #i:I
    .restart local v4       #target:Lcom/android/server/wm/WindowState;
    .restart local v5       #w:Lcom/android/server/wm/WindowState;
    :cond_63
    add-int/lit8 v3, v3, 0x1

    goto :goto_31

    .line 139
    .end local v0           #N:I
    .end local v3           #i:I
    .end local v4           #target:Lcom/android/server/wm/WindowState;
    .end local v5           #w:Lcom/android/server/wm/WindowState;
    :cond_66
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    if-eqz v6, :cond_62

    .line 140
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    invoke-virtual {v6}, Lcom/android/server/wm/DimSurface;->hide()V

    goto :goto_62
.end method

.method private updateWindowsAndWallpaperLocked()V
    .registers 18

    .prologue
    .line 197
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mAnimTransactionSequence:I

    add-int/lit8 v14, v14, 0x1

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mAnimTransactionSequence:I

    .line 199
    const/4 v9, 0x0

    .line 200
    .local v9, unForceHiding:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/wm/WindowStateAnimator;>;"
    const/4 v10, 0x0

    .line 202
    .local v10, wallpaperInUnForceHiding:Z
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v14, v14, Lcom/android/server/wm/WindowManagerService;->mWindows:Ljava/util/ArrayList;

    invoke-virtual {v14}, Ljava/util/ArrayList;->size()I

    move-result v14

    add-int/lit8 v7, v14, -0x1

    .local v7, i:I
    :goto_18
    if-ltz v7, :cond_223

    .line 203
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v14, v14, Lcom/android/server/wm/WindowManagerService;->mWindows:Ljava/util/ArrayList;

    invoke-virtual {v14, v7}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Lcom/android/server/wm/WindowState;

    .line 204
    .local v12, win:Lcom/android/server/wm/WindowState;
    iget-object v13, v12, Lcom/android/server/wm/WindowState;->mWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    .line 205
    .local v13, winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    iget v6, v13, Lcom/android/server/wm/WindowStateAnimator;->mAttrFlags:I

    .line 207
    .local v6, flags:I
    iget-object v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mSurface:Landroid/view/Surface;

    if-eqz v14, :cond_132

    .line 208
    iget-boolean v11, v13, Lcom/android/server/wm/WindowStateAnimator;->mWasAnimating:Z

    .line 209
    .local v11, wasAnimating:Z
    move-object/from16 v0, p0

    iget-wide v14, v0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    invoke-virtual {v13, v14, v15}, Lcom/android/server/wm/WindowStateAnimator;->stepAnimationLocked(J)Z

    move-result v8

    .line 219
    .local v8, nowAnimating:Z
    if-eqz v8, :cond_76

    .line 220
    iget-object v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimation:Landroid/view/animation/Animation;

    if-eqz v14, :cond_71

    .line 221
    const/high16 v14, 0x10

    and-int/2addr v14, v6

    if-eqz v14, :cond_4f

    iget-object v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimation:Landroid/view/animation/Animation;

    invoke-virtual {v14}, Landroid/view/animation/Animation;->getDetachWallpaper()Z

    move-result v14

    if-eqz v14, :cond_4f

    .line 223
    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 225
    :cond_4f
    iget-object v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimation:Landroid/view/animation/Animation;

    invoke-virtual {v14}, Landroid/view/animation/Animation;->getBackgroundColor()I

    move-result v4

    .line 226
    .local v4, backgroundColor:I
    if-eqz v4, :cond_71

    .line 227
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    if-eqz v14, :cond_69

    iget v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    iget-object v15, v15, Lcom/android/server/wm/WindowState;->mWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    iget v15, v15, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    if-ge v14, v15, :cond_71

    .line 230
    :cond_69
    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    .line 231
    move-object/from16 v0, p0

    iput v4, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundColor:I

    .line 235
    .end local v4           #backgroundColor:I
    :cond_71
    const/4 v14, 0x1

    move-object/from16 v0, p0

    iput-boolean v14, v0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 241
    :cond_76
    iget-object v14, v12, Lcom/android/server/wm/WindowState;->mAppToken:Lcom/android/server/wm/AppWindowToken;

    if-nez v14, :cond_186

    const/4 v2, 0x0

    .line 243
    .local v2, appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    :goto_7b
    if-eqz v2, :cond_b8

    iget-object v14, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    if-eqz v14, :cond_b8

    iget-boolean v14, v2, Lcom/android/server/wm/AppWindowAnimator;->animating:Z

    if-eqz v14, :cond_b8

    .line 245
    const/high16 v14, 0x10

    and-int/2addr v14, v6

    if-eqz v14, :cond_96

    iget-object v14, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    invoke-virtual {v14}, Landroid/view/animation/Animation;->getDetachWallpaper()Z

    move-result v14

    if-eqz v14, :cond_96

    .line 247
    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/android/server/wm/WindowAnimator;->mDetachedWallpaper:Lcom/android/server/wm/WindowState;

    .line 249
    :cond_96
    iget-object v14, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    invoke-virtual {v14}, Landroid/view/animation/Animation;->getBackgroundColor()I

    move-result v4

    .line 250
    .restart local v4       #backgroundColor:I
    if-eqz v4, :cond_b8

    .line 251
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    if-eqz v14, :cond_b0

    iget v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    iget-object v15, v15, Lcom/android/server/wm/WindowState;->mWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    iget v15, v15, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    if-ge v14, v15, :cond_b8

    .line 254
    :cond_b0
    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackground:Lcom/android/server/wm/WindowState;

    .line 255
    move-object/from16 v0, p0

    iput v4, v0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundColor:I

    .line 260
    .end local v4           #backgroundColor:I
    :cond_b8
    if-eqz v11, :cond_e9

    iget-boolean v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimating:Z

    if-nez v14, :cond_e9

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v14, v14, Lcom/android/server/wm/WindowManagerService;->mWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-ne v14, v12, :cond_e9

    .line 261
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    or-int/lit8 v14, v14, 0x2

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 262
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v14, v14, 0x4

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 264
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const-string v15, "updateWindowsAndWallpaperLocked 2"

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    move/from16 v16, v0

    invoke-virtual/range {v14 .. v16}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    .line 269
    :cond_e9
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mPolicy:Landroid/view/WindowManagerPolicy;

    iget-object v15, v12, Lcom/android/server/wm/WindowState;->mAttrs:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v14, v12, v15}, Landroid/view/WindowManagerPolicy;->doesForceHide(Landroid/view/WindowManagerPolicy$WindowState;Landroid/view/WindowManager$LayoutParams;)Z

    move-result v14

    if-eqz v14, :cond_18c

    .line 270
    if-nez v11, :cond_123

    if-eqz v8, :cond_123

    .line 274
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    or-int/lit8 v14, v14, 0x4

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 275
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v14, v14, 0x4

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 277
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const-string v15, "updateWindowsAndWallpaperLocked 3"

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    move/from16 v16, v0

    invoke-virtual/range {v14 .. v16}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    .line 280
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const/4 v15, 0x1

    iput-boolean v15, v14, Lcom/android/server/wm/WindowManagerService;->mFocusMayChange:Z

    .line 282
    :cond_123
    invoke-virtual {v12}, Lcom/android/server/wm/WindowState;->isReadyForDisplay()Z

    move-result v14

    if-eqz v14, :cond_132

    iget-boolean v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimationIsEntrance:Z

    if-eqz v14, :cond_132

    .line 283
    const/4 v14, 0x1

    move-object/from16 v0, p0

    iput-boolean v14, v0, Lcom/android/server/wm/WindowAnimator;->mForceHiding:Z

    .line 335
    .end local v2           #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    .end local v8           #nowAnimating:Z
    .end local v11           #wasAnimating:Z
    :cond_132
    :goto_132
    iget-object v3, v12, Lcom/android/server/wm/WindowState;->mAppToken:Lcom/android/server/wm/AppWindowToken;

    .line 336
    .local v3, atoken:Lcom/android/server/wm/AppWindowToken;
    iget v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mDrawState:I

    const/4 v15, 0x3

    if-ne v14, v15, :cond_15e

    .line 337
    if-eqz v3, :cond_13f

    iget-boolean v14, v3, Lcom/android/server/wm/AppWindowToken;->allDrawn:Z

    if-eqz v14, :cond_15e

    .line 338
    :cond_13f
    invoke-virtual {v13}, Lcom/android/server/wm/WindowStateAnimator;->performShowLocked()Z

    move-result v14

    if-eqz v14, :cond_15e

    .line 339
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v14, v14, 0x8

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 341
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const-string v15, "updateWindowsAndWallpaperLocked 5"

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    move/from16 v16, v0

    invoke-virtual/range {v14 .. v16}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    .line 347
    :cond_15e
    if-nez v3, :cond_21f

    const/4 v2, 0x0

    .line 349
    .restart local v2       #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    :goto_161
    if-eqz v2, :cond_182

    iget-object v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnail:Landroid/view/Surface;

    if-eqz v14, :cond_182

    .line 350
    iget v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnailTransactionSeq:I

    move-object/from16 v0, p0

    iget v15, v0, Lcom/android/server/wm/WindowAnimator;->mAnimTransactionSequence:I

    if-eq v14, v15, :cond_178

    .line 351
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mAnimTransactionSequence:I

    iput v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnailTransactionSeq:I

    .line 352
    const/4 v14, 0x0

    iput v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnailLayer:I

    .line 354
    :cond_178
    iget v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnailLayer:I

    iget v15, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    if-ge v14, v15, :cond_182

    .line 355
    iget v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    iput v14, v2, Lcom/android/server/wm/AppWindowAnimator;->thumbnailLayer:I

    .line 202
    :cond_182
    add-int/lit8 v7, v7, -0x1

    goto/16 :goto_18

    .line 241
    .end local v2           #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    .end local v3           #atoken:Lcom/android/server/wm/AppWindowToken;
    .restart local v8       #nowAnimating:Z
    .restart local v11       #wasAnimating:Z
    :cond_186
    iget-object v14, v12, Lcom/android/server/wm/WindowState;->mAppToken:Lcom/android/server/wm/AppWindowToken;

    iget-object v2, v14, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    goto/16 :goto_7b

    .line 294
    .restart local v2       #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    :cond_18c
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mPolicy:Landroid/view/WindowManagerPolicy;

    iget-object v15, v12, Lcom/android/server/wm/WindowState;->mAttrs:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v14, v12, v15}, Landroid/view/WindowManagerPolicy;->canBeForceHidden(Landroid/view/WindowManagerPolicy$WindowState;Landroid/view/WindowManager$LayoutParams;)Z

    move-result v14

    if-eqz v14, :cond_132

    .line 296
    move-object/from16 v0, p0

    iget-boolean v14, v0, Lcom/android/server/wm/WindowAnimator;->mForceHiding:Z

    if-eqz v14, :cond_1dd

    invoke-virtual {v13}, Lcom/android/server/wm/WindowStateAnimator;->isAnimating()Z

    move-result v14

    if-eqz v14, :cond_1ab

    iget v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAttrFlags:I

    const/high16 v15, 0x8

    and-int/2addr v14, v15

    if-nez v14, :cond_1dd

    .line 298
    :cond_1ab
    const/4 v14, 0x0

    const/4 v15, 0x0

    invoke-virtual {v12, v14, v15}, Lcom/android/server/wm/WindowState;->hideLw(ZZ)Z

    move-result v5

    .line 324
    .local v5, changed:Z
    :cond_1b1
    :goto_1b1
    if-eqz v5, :cond_132

    const/high16 v14, 0x10

    and-int/2addr v14, v6

    if-eqz v14, :cond_132

    .line 325
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    or-int/lit8 v14, v14, 0x2

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 326
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v14, v14, 0x4

    move-object/from16 v0, p0

    iput v14, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 328
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const-string v15, "updateWindowsAndWallpaperLocked 4"

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    move/from16 v16, v0

    invoke-virtual/range {v14 .. v16}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    goto/16 :goto_132

    .line 302
    .end local v5           #changed:Z
    :cond_1dd
    const/4 v14, 0x0

    const/4 v15, 0x0

    invoke-virtual {v12, v14, v15}, Lcom/android/server/wm/WindowState;->showLw(ZZ)Z

    move-result v5

    .line 305
    .restart local v5       #changed:Z
    if-eqz v5, :cond_1b1

    .line 306
    move-object/from16 v0, p0

    iget v14, v0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    and-int/lit8 v14, v14, 0x4

    if-eqz v14, :cond_207

    invoke-virtual {v12}, Lcom/android/server/wm/WindowState;->isVisibleNow()Z

    move-result v14

    if-eqz v14, :cond_207

    .line 308
    if-nez v9, :cond_1fa

    .line 309
    new-instance v9, Ljava/util/ArrayList;

    .end local v9           #unForceHiding:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/wm/WindowStateAnimator;>;"
    invoke-direct {v9}, Ljava/util/ArrayList;-><init>()V

    .line 311
    .restart local v9       #unForceHiding:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/wm/WindowStateAnimator;>;"
    :cond_1fa
    invoke-virtual {v9, v13}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 312
    iget-object v14, v12, Lcom/android/server/wm/WindowState;->mAttrs:Landroid/view/WindowManager$LayoutParams;

    iget v14, v14, Landroid/view/WindowManager$LayoutParams;->flags:I

    const/high16 v15, 0x10

    and-int/2addr v14, v15

    if-eqz v14, :cond_207

    .line 313
    const/4 v10, 0x1

    .line 316
    :cond_207
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mCurrentFocus:Lcom/android/server/wm/WindowState;

    if-eqz v14, :cond_217

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mCurrentFocus:Lcom/android/server/wm/WindowState;

    iget v14, v14, Lcom/android/server/wm/WindowState;->mLayer:I

    iget v15, v12, Lcom/android/server/wm/WindowState;->mLayer:I

    if-ge v14, v15, :cond_1b1

    .line 320
    :cond_217
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const/4 v15, 0x1

    iput-boolean v15, v14, Lcom/android/server/wm/WindowManagerService;->mFocusMayChange:Z

    goto :goto_1b1

    .line 347
    .end local v2           #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    .end local v5           #changed:Z
    .end local v8           #nowAnimating:Z
    .end local v11           #wasAnimating:Z
    .restart local v3       #atoken:Lcom/android/server/wm/AppWindowToken;
    :cond_21f
    iget-object v2, v3, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    goto/16 :goto_161

    .line 362
    .end local v3           #atoken:Lcom/android/server/wm/AppWindowToken;
    .end local v6           #flags:I
    .end local v12           #win:Lcom/android/server/wm/WindowState;
    .end local v13           #winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    :cond_223
    if-eqz v9, :cond_246

    .line 363
    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v14

    add-int/lit8 v7, v14, -0x1

    :goto_22b
    if-ltz v7, :cond_246

    .line 364
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/wm/WindowAnimator;->mPolicy:Landroid/view/WindowManagerPolicy;

    invoke-interface {v14, v10}, Landroid/view/WindowManagerPolicy;->createForceHideEnterAnimation(Z)Landroid/view/animation/Animation;

    move-result-object v1

    .line 365
    .local v1, a:Landroid/view/animation/Animation;
    if-eqz v1, :cond_243

    .line 366
    invoke-virtual {v9, v7}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Lcom/android/server/wm/WindowStateAnimator;

    .line 367
    .restart local v13       #winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    invoke-virtual {v13, v1}, Lcom/android/server/wm/WindowStateAnimator;->setAnimation(Landroid/view/animation/Animation;)V

    .line 368
    const/4 v14, 0x1

    iput-boolean v14, v13, Lcom/android/server/wm/WindowStateAnimator;->mAnimationIsEntrance:Z

    .line 363
    .end local v13           #winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    :cond_243
    add-int/lit8 v7, v7, -0x1

    goto :goto_22b

    .line 372
    .end local v1           #a:Landroid/view/animation/Animation;
    :cond_246
    return-void
.end method

.method private updateWindowsAppsAndRotationAnimationsLocked()V
    .registers 13

    .prologue
    const/4 v8, 0x0

    const/4 v7, 0x1

    .line 145
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v3, v6, Lcom/android/server/wm/WindowManagerService;->mAnimatingAppTokens:Ljava/util/ArrayList;

    .line 147
    .local v3, appTokens:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/wm/AppWindowToken;>;"
    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 148
    .local v0, NAT:I
    const/4 v4, 0x0

    .local v4, i:I
    :goto_b
    if-ge v4, v0, :cond_5e

    .line 149
    invoke-virtual {v3, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lcom/android/server/wm/AppWindowToken;

    iget-object v2, v6, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    .line 150
    .local v2, appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    iget-object v6, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    if-eqz v6, :cond_31

    iget-object v6, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    sget-object v9, Lcom/android/server/wm/AppWindowAnimator;->sDummyAnimation:Landroid/view/animation/Animation;

    if-eq v6, v9, :cond_31

    move v5, v7

    .line 152
    .local v5, wasAnimating:Z
    :goto_20
    iget-wide v9, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDw:I

    iget v11, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDh:I

    invoke-virtual {v2, v9, v10, v6, v11}, Lcom/android/server/wm/AppWindowAnimator;->stepAnimationLocked(JII)Z

    move-result v6

    if-eqz v6, :cond_33

    .line 153
    iput-boolean v7, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 148
    :cond_2e
    :goto_2e
    add-int/lit8 v4, v4, 0x1

    goto :goto_b

    .end local v5           #wasAnimating:Z
    :cond_31
    move v5, v8

    .line 150
    goto :goto_20

    .line 154
    .restart local v5       #wasAnimating:Z
    :cond_33
    if-eqz v5, :cond_2e

    .line 156
    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v6, v6, 0x4

    iput v6, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 158
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "appToken "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-object v10, v2, Lcom/android/server/wm/AppWindowAnimator;->mAppToken:Lcom/android/server/wm/AppWindowToken;

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " done"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    iget v10, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    invoke-virtual {v6, v9, v10}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    goto :goto_2e

    .line 166
    .end local v2           #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    .end local v5           #wasAnimating:Z
    :cond_5e
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mExitingAppTokens:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v1

    .line 167
    .local v1, NEAT:I
    const/4 v4, 0x0

    :goto_67
    if-ge v4, v1, :cond_be

    .line 168
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v6, v6, Lcom/android/server/wm/WindowManagerService;->mExitingAppTokens:Ljava/util/ArrayList;

    invoke-virtual {v6, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lcom/android/server/wm/AppWindowToken;

    iget-object v2, v6, Lcom/android/server/wm/AppWindowToken;->mAppAnimator:Lcom/android/server/wm/AppWindowAnimator;

    .line 169
    .restart local v2       #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    iget-object v6, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    if-eqz v6, :cond_91

    iget-object v6, v2, Lcom/android/server/wm/AppWindowAnimator;->animation:Landroid/view/animation/Animation;

    sget-object v9, Lcom/android/server/wm/AppWindowAnimator;->sDummyAnimation:Landroid/view/animation/Animation;

    if-eq v6, v9, :cond_91

    move v5, v7

    .line 171
    .restart local v5       #wasAnimating:Z
    :goto_80
    iget-wide v9, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDw:I

    iget v11, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDh:I

    invoke-virtual {v2, v9, v10, v6, v11}, Lcom/android/server/wm/AppWindowAnimator;->stepAnimationLocked(JII)Z

    move-result v6

    if-eqz v6, :cond_93

    .line 172
    iput-boolean v7, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 167
    :cond_8e
    :goto_8e
    add-int/lit8 v4, v4, 0x1

    goto :goto_67

    .end local v5           #wasAnimating:Z
    :cond_91
    move v5, v8

    .line 169
    goto :goto_80

    .line 173
    .restart local v5       #wasAnimating:Z
    :cond_93
    if-eqz v5, :cond_8e

    .line 175
    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v6, v6, 0x4

    iput v6, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 177
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "exiting appToken "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-object v10, v2, Lcom/android/server/wm/AppWindowAnimator;->mAppToken:Lcom/android/server/wm/AppWindowToken;

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " done"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    iget v10, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    invoke-virtual {v6, v9, v10}, Lcom/android/server/wm/WindowManagerService;->debugLayoutRepeats(Ljava/lang/String;I)V

    goto :goto_8e

    .line 185
    .end local v2           #appAnimator:Lcom/android/server/wm/AppWindowAnimator;
    .end local v5           #wasAnimating:Z
    :cond_be
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    if-eqz v6, :cond_d6

    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    invoke-virtual {v6}, Lcom/android/server/wm/ScreenRotationAnimation;->isAnimating()Z

    move-result v6

    if-eqz v6, :cond_d6

    .line 186
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    iget-wide v8, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    invoke-virtual {v6, v8, v9}, Lcom/android/server/wm/ScreenRotationAnimation;->stepAnimationLocked(J)Z

    move-result v6

    if-eqz v6, :cond_d7

    .line 187
    iput-boolean v7, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 194
    :cond_d6
    :goto_d6
    return-void

    .line 189
    :cond_d7
    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    or-int/lit8 v6, v6, 0x1

    iput v6, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 190
    iget-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    invoke-virtual {v6}, Lcom/android/server/wm/ScreenRotationAnimation;->kill()V

    .line 191
    const/4 v6, 0x0

    iput-object v6, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    goto :goto_d6
.end method


# virtual methods
.method declared-synchronized animate()V
    .registers 13

    .prologue
    const/4 v5, 0x1

    const/4 v6, 0x0

    .line 428
    monitor-enter p0

    const/4 v4, 0x0

    :try_start_4
    iput v4, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    .line 429
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v7

    iput-wide v7, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    .line 430
    const/4 v4, 0x0

    iput v4, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    .line 431
    iget-boolean v3, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 432
    .local v3, wasAnimating:Z
    const/4 v4, 0x0

    iput-boolean v4, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 439
    invoke-static {}, Landroid/view/Surface;->openTransaction()V
    :try_end_17
    .catchall {:try_start_4 .. :try_end_17} :catchall_cd

    .line 442
    :try_start_17
    invoke-direct {p0}, Lcom/android/server/wm/WindowAnimator;->updateWindowsAppsAndRotationAnimationsLocked()V

    .line 443
    invoke-direct {p0}, Lcom/android/server/wm/WindowAnimator;->performAnimationsLocked()V

    .line 444
    invoke-direct {p0}, Lcom/android/server/wm/WindowAnimator;->testWallpaperAndBackgroundLocked()V

    .line 448
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    if-eqz v4, :cond_29

    .line 449
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    invoke-virtual {v4}, Lcom/android/server/wm/ScreenRotationAnimation;->updateSurfaces()V

    .line 452
    :cond_29
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mWinAnimators:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 453
    .local v0, N:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_30
    if-ge v2, v0, :cond_41

    .line 454
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mWinAnimators:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/wm/WindowStateAnimator;

    const/4 v7, 0x1

    invoke-virtual {v4, v7}, Lcom/android/server/wm/WindowStateAnimator;->prepareSurfaceLocked(Z)V

    .line 453
    add-int/lit8 v2, v2, 0x1

    goto :goto_30

    .line 457
    :cond_41
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    if-eqz v4, :cond_54

    .line 458
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    iget-object v7, p0, Lcom/android/server/wm/WindowAnimator;->mContext:Landroid/content/Context;

    invoke-virtual {v7}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    iget-object v8, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    iget-wide v9, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    invoke-virtual {v4, v7, v8, v9, v10}, Lcom/android/server/wm/DimAnimator;->updateParameters(Landroid/content/res/Resources;Lcom/android/server/wm/DimAnimator$Parameters;J)V

    .line 460
    :cond_54
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    if-eqz v4, :cond_78

    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    iget-boolean v4, v4, Lcom/android/server/wm/DimAnimator;->mDimShown:Z

    if-eqz v4, :cond_78

    .line 461
    iget-boolean v7, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    iget-object v8, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    invoke-virtual {p0}, Lcom/android/server/wm/WindowAnimator;->isDimming()Z

    move-result v9

    iget-wide v10, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentTime:J

    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    invoke-virtual {v4}, Lcom/android/server/wm/WindowManagerService;->okToDisplay()Z

    move-result v4

    if-nez v4, :cond_b7

    move v4, v5

    :goto_71
    invoke-virtual {v8, v9, v10, v11, v4}, Lcom/android/server/wm/DimAnimator;->updateSurface(ZJZ)Z

    move-result v4

    or-int/2addr v4, v7

    iput-boolean v4, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    .line 465
    :cond_78
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v4, v4, Lcom/android/server/wm/WindowManagerService;->mBlackFrame:Lcom/android/server/wm/BlackFrame;

    if-eqz v4, :cond_93

    .line 466
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    if-eqz v4, :cond_b9

    .line 467
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v4, v4, Lcom/android/server/wm/WindowManagerService;->mBlackFrame:Lcom/android/server/wm/BlackFrame;

    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mScreenRotationAnimation:Lcom/android/server/wm/ScreenRotationAnimation;

    invoke-virtual {v5}, Lcom/android/server/wm/ScreenRotationAnimation;->getEnterTransformation()Landroid/view/animation/Transformation;

    move-result-object v5

    invoke-virtual {v5}, Landroid/view/animation/Transformation;->getMatrix()Landroid/graphics/Matrix;

    move-result-object v5

    invoke-virtual {v4, v5}, Lcom/android/server/wm/BlackFrame;->setMatrix(Landroid/graphics/Matrix;)V

    .line 474
    :cond_93
    :goto_93
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v4, v4, Lcom/android/server/wm/WindowManagerService;->mWatermark:Lcom/android/server/wm/Watermark;

    if-eqz v4, :cond_a0

    .line 475
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v4, v4, Lcom/android/server/wm/WindowManagerService;->mWatermark:Lcom/android/server/wm/Watermark;

    invoke-virtual {v4}, Lcom/android/server/wm/Watermark;->drawIfNeeded()V
    :try_end_a0
    .catchall {:try_start_17 .. :try_end_a0} :catchall_d0
    .catch Ljava/lang/RuntimeException; {:try_start_17 .. :try_end_a0} :catch_c1

    .line 480
    :cond_a0
    :try_start_a0
    invoke-static {}, Landroid/view/Surface;->closeTransaction()V

    .line 483
    .end local v0           #N:I
    .end local v2           #i:I
    :goto_a3
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget v5, p0, Lcom/android/server/wm/WindowAnimator;->mBulkUpdateParams:I

    iget v6, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    invoke-virtual {v4, v5, v6}, Lcom/android/server/wm/WindowManagerService;->bulkSetParameters(II)V

    .line 485
    iget-boolean v4, p0, Lcom/android/server/wm/WindowAnimator;->mAnimating:Z

    if-eqz v4, :cond_d5

    .line 486
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    invoke-virtual {v4}, Lcom/android/server/wm/WindowManagerService;->scheduleAnimationLocked()V
    :try_end_b5
    .catchall {:try_start_a0 .. :try_end_b5} :catchall_cd

    .line 495
    :cond_b5
    :goto_b5
    monitor-exit p0

    return-void

    .restart local v0       #N:I
    .restart local v2       #i:I
    :cond_b7
    move v4, v6

    .line 461
    goto :goto_71

    .line 470
    :cond_b9
    :try_start_b9
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v4, v4, Lcom/android/server/wm/WindowManagerService;->mBlackFrame:Lcom/android/server/wm/BlackFrame;

    invoke-virtual {v4}, Lcom/android/server/wm/BlackFrame;->clearMatrix()V
    :try_end_c0
    .catchall {:try_start_b9 .. :try_end_c0} :catchall_d0
    .catch Ljava/lang/RuntimeException; {:try_start_b9 .. :try_end_c0} :catch_c1

    goto :goto_93

    .line 477
    .end local v0           #N:I
    .end local v2           #i:I
    :catch_c1
    move-exception v1

    .line 478
    .local v1, e:Ljava/lang/RuntimeException;
    :try_start_c2
    const-string v4, "WindowAnimator"

    const-string v5, "Unhandled exception in Window Manager"

    invoke-static {v4, v5, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_c9
    .catchall {:try_start_c2 .. :try_end_c9} :catchall_d0

    .line 480
    :try_start_c9
    invoke-static {}, Landroid/view/Surface;->closeTransaction()V
    :try_end_cc
    .catchall {:try_start_c9 .. :try_end_cc} :catchall_cd

    goto :goto_a3

    .line 428
    .end local v1           #e:Ljava/lang/RuntimeException;
    .end local v3           #wasAnimating:Z
    :catchall_cd
    move-exception v4

    monitor-exit p0

    throw v4

    .line 480
    .restart local v3       #wasAnimating:Z
    :catchall_d0
    move-exception v4

    :try_start_d1
    invoke-static {}, Landroid/view/Surface;->closeTransaction()V

    throw v4

    .line 487
    :cond_d5
    if-eqz v3, :cond_b5

    .line 488
    iget-object v4, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    invoke-virtual {v4}, Lcom/android/server/wm/WindowManagerService;->requestTraversalLocked()V
    :try_end_dc
    .catchall {:try_start_d1 .. :try_end_dc} :catchall_cd

    goto :goto_b5
.end method

.method declared-synchronized clearPendingActions()V
    .registers 2

    .prologue
    .line 576
    monitor-enter p0

    const/4 v0, 0x0

    :try_start_2
    iput v0, p0, Lcom/android/server/wm/WindowAnimator;->mPendingActions:I
    :try_end_4
    .catchall {:try_start_2 .. :try_end_4} :catchall_6

    .line 577
    monitor-exit p0

    return-void

    .line 576
    :catchall_6
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public dump(Ljava/io/PrintWriter;Ljava/lang/String;Z)V
    .registers 7
    .parameter "pw"
    .parameter "prefix"
    .parameter "dumpAll"

    .prologue
    .line 541
    if-eqz p3, :cond_68

    .line 542
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;

    if-eqz v0, :cond_13

    .line 543
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v0, "mWindowDetachedWallpaper="

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 544
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mWindowDetachedWallpaper:Lcom/android/server/wm/WindowState;

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 546
    :cond_13
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v0, "mAnimTransactionSequence="

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 547
    iget v0, p0, Lcom/android/server/wm/WindowAnimator;->mAnimTransactionSequence:I

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->println(I)V

    .line 548
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    if-eqz v0, :cond_44

    .line 549
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v0, "mWindowAnimationBackgroundSurface:"

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 550
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mWindowAnimationBackgroundSurface:Lcom/android/server/wm/DimSurface;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "  "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1, p1}, Lcom/android/server/wm/DimSurface;->printTo(Ljava/lang/String;Ljava/io/PrintWriter;)V

    .line 552
    :cond_44
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    if-eqz v0, :cond_69

    .line 553
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v0, "mDimAnimator:"

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 554
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "  "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1, p1}, Lcom/android/server/wm/DimAnimator;->printTo(Ljava/lang/String;Ljava/io/PrintWriter;)V

    .line 559
    :cond_68
    :goto_68
    return-void

    .line 556
    :cond_69
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const-string v0, "no DimAnimator "

    invoke-virtual {p1, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    goto :goto_68
.end method

.method hideWallpapersLocked(Lcom/android/server/wm/WindowState;)V
    .registers 9
    .parameter "w"

    .prologue
    .line 89
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v5, v5, Lcom/android/server/wm/WindowManagerService;->mWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-ne v5, p1, :cond_c

    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v5, v5, Lcom/android/server/wm/WindowManagerService;->mLowerWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-eqz v5, :cond_12

    :cond_c
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v5, v5, Lcom/android/server/wm/WindowManagerService;->mWallpaperTarget:Lcom/android/server/wm/WindowState;

    if-nez v5, :cond_52

    .line 91
    :cond_12
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v5, v5, Lcom/android/server/wm/WindowManagerService;->mWallpaperTokens:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_1a
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_52

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/wm/WindowToken;

    .line 92
    .local v2, token:Lcom/android/server/wm/WindowToken;
    iget-object v5, v2, Lcom/android/server/wm/WindowToken;->windows:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_2c
    :goto_2c
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_4e

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/wm/WindowState;

    .line 93
    .local v3, wallpaper:Lcom/android/server/wm/WindowState;
    iget-object v4, v3, Lcom/android/server/wm/WindowState;->mWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    .line 94
    .local v4, winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    iget-boolean v5, v4, Lcom/android/server/wm/WindowStateAnimator;->mLastHidden:Z

    if-nez v5, :cond_2c

    .line 95
    invoke-virtual {v4}, Lcom/android/server/wm/WindowStateAnimator;->hide()V

    .line 96
    iget-object v5, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    const/4 v6, 0x0

    invoke-virtual {v5, v3, v6}, Lcom/android/server/wm/WindowManagerService;->dispatchWallpaperVisibility(Lcom/android/server/wm/WindowState;Z)V

    .line 97
    iget v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    or-int/lit8 v5, v5, 0x4

    iput v5, p0, Lcom/android/server/wm/WindowAnimator;->mPendingLayoutChanges:I

    goto :goto_2c

    .line 100
    .end local v3           #wallpaper:Lcom/android/server/wm/WindowState;
    .end local v4           #winAnimator:Lcom/android/server/wm/WindowStateAnimator;
    :cond_4e
    const/4 v5, 0x1

    iput-boolean v5, v2, Lcom/android/server/wm/WindowToken;->hidden:Z

    goto :goto_1a

    .line 103
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #token:Lcom/android/server/wm/WindowToken;
    :cond_52
    return-void
.end method

.method isDimming()Z
    .registers 2

    .prologue
    .line 533
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    if-eqz v0, :cond_6

    const/4 v0, 0x1

    :goto_5
    return v0

    :cond_6
    const/4 v0, 0x0

    goto :goto_5
.end method

.method isDimming(Lcom/android/server/wm/WindowStateAnimator;)Z
    .registers 3
    .parameter "winAnimator"

    .prologue
    .line 537
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    if-eqz v0, :cond_c

    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    iget-object v0, v0, Lcom/android/server/wm/DimAnimator$Parameters;->mDimWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    if-ne v0, p1, :cond_c

    const/4 v0, 0x1

    :goto_b
    return v0

    :cond_c
    const/4 v0, 0x0

    goto :goto_b
.end method

.method setCurrentFocus(Lcom/android/server/wm/WindowState;)V
    .registers 2
    .parameter "currentFocus"

    .prologue
    .line 499
    iput-object p1, p0, Lcom/android/server/wm/WindowAnimator;->mCurrentFocus:Lcom/android/server/wm/WindowState;

    .line 500
    return-void
.end method

.method setDisplayDimensions(IIII)V
    .registers 5
    .parameter "curWidth"
    .parameter "curHeight"
    .parameter "appWidth"
    .parameter "appHeight"

    .prologue
    .line 504
    iput p1, p0, Lcom/android/server/wm/WindowAnimator;->mDw:I

    .line 505
    iput p2, p0, Lcom/android/server/wm/WindowAnimator;->mDh:I

    .line 506
    iput p3, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDw:I

    .line 507
    iput p4, p0, Lcom/android/server/wm/WindowAnimator;->mInnerDh:I

    .line 508
    return-void
.end method

.method startDimming(Lcom/android/server/wm/WindowStateAnimator;FII)V
    .registers 10
    .parameter "winAnimator"
    .parameter "target"
    .parameter "width"
    .parameter "height"

    .prologue
    .line 512
    iget-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    if-nez v1, :cond_f

    .line 513
    new-instance v1, Lcom/android/server/wm/DimAnimator;

    iget-object v2, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v2, v2, Lcom/android/server/wm/WindowManagerService;->mFxSession:Landroid/view/SurfaceSession;

    invoke-direct {v1, v2}, Lcom/android/server/wm/DimAnimator;-><init>(Landroid/view/SurfaceSession;)V

    iput-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimAnimator:Lcom/android/server/wm/DimAnimator;

    .line 516
    :cond_f
    iget-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    if-nez v1, :cond_3c

    const/4 v0, 0x0

    .line 519
    .local v0, dimWinAnimator:Lcom/android/server/wm/WindowStateAnimator;
    :goto_14
    iget-boolean v1, p1, Lcom/android/server/wm/WindowStateAnimator;->mSurfaceShown:Z

    if-eqz v1, :cond_3b

    if-eqz v0, :cond_24

    iget-boolean v1, v0, Lcom/android/server/wm/WindowStateAnimator;->mSurfaceShown:Z

    if-eqz v1, :cond_24

    iget v1, v0, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    iget v2, p1, Lcom/android/server/wm/WindowStateAnimator;->mAnimLayer:I

    if-ge v1, v2, :cond_3b

    .line 522
    :cond_24
    iget-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v1, v1, Lcom/android/server/wm/WindowManagerService;->mH:Lcom/android/server/wm/WindowManagerService$H;

    iget-object v2, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v2, v2, Lcom/android/server/wm/WindowManagerService;->mH:Lcom/android/server/wm/WindowManagerService$H;

    const v3, 0x186a3

    new-instance v4, Lcom/android/server/wm/DimAnimator$Parameters;

    invoke-direct {v4, p1, p3, p4, p2}, Lcom/android/server/wm/DimAnimator$Parameters;-><init>(Lcom/android/server/wm/WindowStateAnimator;IIF)V

    invoke-virtual {v2, v3, v4}, Lcom/android/server/wm/WindowManagerService$H;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/android/server/wm/WindowManagerService$H;->sendMessage(Landroid/os/Message;)Z

    .line 525
    :cond_3b
    return-void

    .line 516
    .end local v0           #dimWinAnimator:Lcom/android/server/wm/WindowStateAnimator;
    :cond_3c
    iget-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mDimParams:Lcom/android/server/wm/DimAnimator$Parameters;

    iget-object v0, v1, Lcom/android/server/wm/DimAnimator$Parameters;->mDimWinAnimator:Lcom/android/server/wm/WindowStateAnimator;

    goto :goto_14
.end method

.method stopDimming()V
    .registers 5

    .prologue
    .line 529
    iget-object v0, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v0, v0, Lcom/android/server/wm/WindowManagerService;->mH:Lcom/android/server/wm/WindowManagerService$H;

    iget-object v1, p0, Lcom/android/server/wm/WindowAnimator;->mService:Lcom/android/server/wm/WindowManagerService;

    iget-object v1, v1, Lcom/android/server/wm/WindowManagerService;->mH:Lcom/android/server/wm/WindowManagerService$H;

    const v2, 0x186a3

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Lcom/android/server/wm/WindowManagerService$H;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/android/server/wm/WindowManagerService$H;->sendMessage(Landroid/os/Message;)Z

    .line 530
    return-void
.end method
