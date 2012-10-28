.class Lcom/android/server/UiModeManagerService$Injector;
.super Ljava/lang/Object;
.source "UiModeManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/UiModeManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 67
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static registerUIModeScaleChangedOjbserver(Lcom/android/server/UiModeManagerService;Landroid/content/Context;)V
    .registers 6
    .parameter "service"
    .parameter "context"

    .prologue
    const/4 v3, 0x0

    .line 69
    new-instance v0, Lcom/android/server/UiModeManagerService$Injector$1;

    new-instance v1, Landroid/os/Handler;

    invoke-direct {v1}, Landroid/os/Handler;-><init>()V

    invoke-direct {v0, v1, p0, p1}, Lcom/android/server/UiModeManagerService$Injector$1;-><init>(Landroid/os/Handler;Lcom/android/server/UiModeManagerService;Landroid/content/Context;)V

    .line 80
    .local v0, observer:Landroid/database/ContentObserver;
    invoke-virtual {p1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "ui_mode_scale"

    invoke-static {v2}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v2

    invoke-virtual {v1, v2, v3, v0}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 84
    invoke-virtual {v0, v3}, Lcom/android/server/UiModeManagerService$Injector$1;->onChange(Z)V

    .line 85
    return-void
.end method
