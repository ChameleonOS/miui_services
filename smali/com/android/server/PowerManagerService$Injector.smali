.class Lcom/android/server/PowerManagerService$Injector;
.super Ljava/lang/Object;
.source "PowerManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/PowerManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# static fields
.field static FALSE:Z


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 103
    const/4 v0, 0x0

    sput-boolean v0, Lcom/android/server/PowerManagerService$Injector;->FALSE:Z

    return-void
.end method

.method constructor <init>()V
    .registers 1

    .prologue
    .line 102
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static animateTo(Lcom/android/server/PowerManagerService;Lcom/android/server/PowerManagerService$ScreenBrightnessAnimator;III)V
    .registers 7
    .parameter "service"
    .parameter "animator"
    .parameter "target"
    .parameter "mask"
    .parameter "animationDuration"

    .prologue
    .line 114
    monitor-enter p1

    .line 115
    :try_start_1
    iput p2, p1, Lcom/android/server/PowerManagerService$ScreenBrightnessAnimator;->endValue:I

    iput p2, p1, Lcom/android/server/PowerManagerService$ScreenBrightnessAnimator;->currentValue:I

    .line 116
    monitor-exit p1
    :try_end_6
    .catchall {:try_start_1 .. :try_end_6} :catchall_f

    .line 117
    invoke-virtual {p0}, Lcom/android/server/PowerManagerService;->getScreenBrightnessHandler()Landroid/os/Handler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 118
    return-void

    .line 116
    :catchall_f
    move-exception v0

    :try_start_10
    monitor-exit p1
    :try_end_11
    .catchall {:try_start_10 .. :try_end_11} :catchall_f

    throw v0
.end method

.method static sleepIfProximitySensorActive(Lcom/android/server/PowerManagerService;)V
    .registers 4
    .parameter "service"

    .prologue
    .line 106
    invoke-virtual {p0}, Lcom/android/server/PowerManagerService;->getProximitySensorActive()Z

    move-result v0

    if-eqz v0, :cond_1a

    invoke-virtual {p0}, Lcom/android/server/PowerManagerService;->getPowerState()I

    move-result v0

    and-int/lit8 v0, v0, 0x1

    if-eqz v0, :cond_1a

    .line 107
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v0

    const/4 v2, 0x4

    invoke-virtual {p0, v0, v1, v2}, Lcom/android/server/PowerManagerService;->callGoToSleepLocked(JI)V

    .line 109
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/server/PowerManagerService;->setProxIgnoredBecauseScreenTurnedOff(Z)V

    .line 111
    :cond_1a
    return-void
.end method
