.class final Lcom/android/server/UiModeManagerService$Injector$1;
.super Landroid/database/ContentObserver;
.source "UiModeManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/server/UiModeManagerService$Injector;->registerUIModeScaleChangedOjbserver(Lcom/android/server/UiModeManagerService;Landroid/content/Context;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# instance fields
.field final synthetic val$context:Landroid/content/Context;

.field final synthetic val$service:Lcom/android/server/UiModeManagerService;


# direct methods
.method constructor <init>(Landroid/os/Handler;Lcom/android/server/UiModeManagerService;Landroid/content/Context;)V
    .registers 4
    .parameter "x0"
    .parameter
    .parameter

    .prologue
    .line 69
    iput-object p2, p0, Lcom/android/server/UiModeManagerService$Injector$1;->val$service:Lcom/android/server/UiModeManagerService;

    iput-object p3, p0, Lcom/android/server/UiModeManagerService$Injector$1;->val$context:Landroid/content/Context;

    invoke-direct {p0, p1}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .registers 7
    .parameter "selfChange"

    .prologue
    .line 72
    iget-object v0, p0, Lcom/android/server/UiModeManagerService$Injector$1;->val$service:Lcom/android/server/UiModeManagerService;

    iget-object v1, v0, Lcom/android/server/UiModeManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v1

    .line 73
    :try_start_5
    iget-object v0, p0, Lcom/android/server/UiModeManagerService$Injector$1;->val$service:Lcom/android/server/UiModeManagerService;

    iget-object v2, p0, Lcom/android/server/UiModeManagerService$Injector$1;->val$context:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string v3, "ui_mode_scale"

    const/4 v4, 0x1

    invoke-static {v2, v3, v4}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    iput v2, v0, Lcom/android/server/UiModeManagerService;->mNormalType:I

    .line 77
    monitor-exit v1

    .line 78
    return-void

    .line 77
    :catchall_18
    move-exception v0

    monitor-exit v1
    :try_end_1a
    .catchall {:try_start_5 .. :try_end_1a} :catchall_18

    throw v0
.end method
