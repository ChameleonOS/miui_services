.class Lcom/android/server/PowerManagerService$SettingsObserver;
.super Ljava/lang/Object;
.source "PowerManagerService.java"

# interfaces
.implements Ljava/util/Observer;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/PowerManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "SettingsObserver"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/PowerManagerService;


# direct methods
.method private constructor <init>(Lcom/android/server/PowerManagerService;)V
    .registers 2
    .parameter

    .prologue
    .line 525
    iput-object p1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/android/server/PowerManagerService;Lcom/android/server/PowerManagerService$1;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 525
    invoke-direct {p0, p1}, Lcom/android/server/PowerManagerService$SettingsObserver;-><init>(Lcom/android/server/PowerManagerService;)V

    return-void
.end method

.method private getFloat(Ljava/lang/String;F)F
    .registers 6
    .parameter "name"
    .parameter "defValue"

    .prologue
    .line 533
    iget-object v2, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mSettings:Landroid/content/ContentQueryMap;
    invoke-static {v2}, Lcom/android/server/PowerManagerService;->access$1200(Lcom/android/server/PowerManagerService;)Landroid/content/ContentQueryMap;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/ContentQueryMap;->getValues(Ljava/lang/String;)Landroid/content/ContentValues;

    move-result-object v1

    .line 534
    .local v1, values:Landroid/content/ContentValues;
    if-eqz v1, :cond_19

    const-string v2, "value"

    invoke-virtual {v1, v2}, Landroid/content/ContentValues;->getAsFloat(Ljava/lang/String;)Ljava/lang/Float;

    move-result-object v0

    .line 535
    .local v0, fVal:Ljava/lang/Float;
    :goto_12
    if-eqz v0, :cond_18

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result p2

    .end local p2
    :cond_18
    return p2

    .line 534
    .end local v0           #fVal:Ljava/lang/Float;
    .restart local p2
    :cond_19
    const/4 v0, 0x0

    goto :goto_12
.end method

.method private getInt(Ljava/lang/String;I)I
    .registers 6
    .parameter "name"
    .parameter "defValue"

    .prologue
    .line 527
    iget-object v2, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mSettings:Landroid/content/ContentQueryMap;
    invoke-static {v2}, Lcom/android/server/PowerManagerService;->access$1200(Lcom/android/server/PowerManagerService;)Landroid/content/ContentQueryMap;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/ContentQueryMap;->getValues(Ljava/lang/String;)Landroid/content/ContentValues;

    move-result-object v1

    .line 528
    .local v1, values:Landroid/content/ContentValues;
    if-eqz v1, :cond_19

    const-string v2, "value"

    invoke-virtual {v1, v2}, Landroid/content/ContentValues;->getAsInteger(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v0

    .line 529
    .local v0, iVal:Ljava/lang/Integer;
    :goto_12
    if-eqz v0, :cond_18

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result p2

    .end local p2
    :cond_18
    return p2

    .line 528
    .end local v0           #iVal:Ljava/lang/Integer;
    .restart local p2
    :cond_19
    const/4 v0, 0x0

    goto :goto_12
.end method


# virtual methods
.method public update(Ljava/util/Observable;Ljava/lang/Object;)V
    .registers 9
    .parameter "o"
    .parameter "arg"

    .prologue
    const/high16 v5, 0x3f00

    .line 539
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v2

    monitor-enter v2

    .line 541
    :try_start_9
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const-string v3, "stay_on_while_plugged_in"

    const/4 v4, 0x1

    invoke-direct {p0, v3, v4}, Lcom/android/server/PowerManagerService$SettingsObserver;->getInt(Ljava/lang/String;I)I

    move-result v3

    #setter for: Lcom/android/server/PowerManagerService;->mStayOnConditions:I
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1302(Lcom/android/server/PowerManagerService;I)I

    .line 543
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #calls: Lcom/android/server/PowerManagerService;->updateWakeLockLocked()V
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$800(Lcom/android/server/PowerManagerService;)V

    .line 546
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const-string v3, "screen_off_timeout"

    const/16 v4, 0x3a98

    invoke-direct {p0, v3, v4}, Lcom/android/server/PowerManagerService$SettingsObserver;->getInt(Ljava/lang/String;I)I

    move-result v3

    #setter for: Lcom/android/server/PowerManagerService;->mScreenOffTimeoutSetting:I
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1402(Lcom/android/server/PowerManagerService;I)I

    .line 551
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const-string v3, "screen_brightness"

    const/16 v4, 0xc0

    invoke-direct {p0, v3, v4}, Lcom/android/server/PowerManagerService$SettingsObserver;->getInt(Ljava/lang/String;I)I

    move-result v3

    #setter for: Lcom/android/server/PowerManagerService;->mScreenBrightnessSetting:I
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1502(Lcom/android/server/PowerManagerService;I)I

    .line 552
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const/4 v3, 0x0

    #setter for: Lcom/android/server/PowerManagerService;->mLightSensorAdjustSetting:F
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1602(Lcom/android/server/PowerManagerService;F)F

    .line 555
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const-string v3, "screen_brightness_mode"

    const/4 v4, 0x0

    invoke-direct {p0, v3, v4}, Lcom/android/server/PowerManagerService$SettingsObserver;->getInt(Ljava/lang/String;I)I

    move-result v3

    #calls: Lcom/android/server/PowerManagerService;->setScreenBrightnessMode(I)V
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1700(Lcom/android/server/PowerManagerService;I)V

    .line 559
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #calls: Lcom/android/server/PowerManagerService;->setScreenOffTimeoutsLocked()V
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$1800(Lcom/android/server/PowerManagerService;)V

    .line 561
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const-string v3, "window_animation_scale"

    const/high16 v4, 0x3f80

    invoke-direct {p0, v3, v4}, Lcom/android/server/PowerManagerService$SettingsObserver;->getFloat(Ljava/lang/String;F)F

    move-result v3

    #setter for: Lcom/android/server/PowerManagerService;->mWindowScaleAnimation:F
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$1902(Lcom/android/server/PowerManagerService;F)F

    .line 562
    const-string v1, "transition_animation_scale"

    const/high16 v3, 0x3f80

    invoke-direct {p0, v1, v3}, Lcom/android/server/PowerManagerService$SettingsObserver;->getFloat(Ljava/lang/String;F)F

    move-result v0

    .line 563
    .local v0, transitionScale:F
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const/4 v3, 0x0

    #setter for: Lcom/android/server/PowerManagerService;->mAnimationSetting:I
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$2002(Lcom/android/server/PowerManagerService;I)I

    .line 564
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mWindowScaleAnimation:F
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$1900(Lcom/android/server/PowerManagerService;)F

    move-result v1

    cmpl-float v1, v1, v5

    if-lez v1, :cond_77

    .line 565
    iget-object v1, p0, Lcom/android/server/PowerManagerService$SettingsObserver;->this$0:Lcom/android/server/PowerManagerService;

    const/16 v3, 0x10

    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$2076(Lcom/android/server/PowerManagerService;I)I

    .line 567
    :cond_77
    cmpl-float v1, v0, v5

    if-lez v1, :cond_7b

    .line 571
    :cond_7b
    monitor-exit v2

    .line 572
    return-void

    .line 571
    .end local v0           #transitionScale:F
    :catchall_7d
    move-exception v1

    monitor-exit v2
    :try_end_7f
    .catchall {:try_start_9 .. :try_end_7f} :catchall_7d

    throw v1
.end method
