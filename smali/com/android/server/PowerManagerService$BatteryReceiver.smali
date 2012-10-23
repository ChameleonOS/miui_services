.class final Lcom/android/server/PowerManagerService$BatteryReceiver;
.super Landroid/content/BroadcastReceiver;
.source "PowerManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/PowerManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x12
    name = "BatteryReceiver"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/PowerManagerService;


# direct methods
.method private constructor <init>(Lcom/android/server/PowerManagerService;)V
    .registers 2
    .parameter

    .prologue
    .line 411
    iput-object p1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/android/server/PowerManagerService;Lcom/android/server/PowerManagerService$1;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 411
    invoke-direct {p0, p1}, Lcom/android/server/PowerManagerService$BatteryReceiver;-><init>(Lcom/android/server/PowerManagerService;)V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .registers 7
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 414
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v2

    monitor-enter v2

    .line 415
    :try_start_7
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mIsPowered:Z
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$600(Lcom/android/server/PowerManagerService;)Z

    move-result v0

    .line 416
    .local v0, wasPowered:Z
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    iget-object v3, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBatteryService:Lcom/android/server/BatteryService;
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$700(Lcom/android/server/PowerManagerService;)Lcom/android/server/BatteryService;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/server/BatteryService;->isPowered()Z

    move-result v3

    #setter for: Lcom/android/server/PowerManagerService;->mIsPowered:Z
    invoke-static {v1, v3}, Lcom/android/server/PowerManagerService;->access$602(Lcom/android/server/PowerManagerService;Z)Z

    .line 418
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mIsPowered:Z
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$600(Lcom/android/server/PowerManagerService;)Z

    move-result v1

    if-eq v1, v0, :cond_48

    .line 420
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #calls: Lcom/android/server/PowerManagerService;->updateWakeLockLocked()V
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$800(Lcom/android/server/PowerManagerService;)V

    .line 432
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v3

    monitor-enter v3
    :try_end_30
    .catchall {:try_start_7 .. :try_end_30} :catchall_4d

    .line 433
    if-eqz v0, :cond_42

    :try_start_32
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mPowerState:I
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$900(Lcom/android/server/PowerManagerService;)I

    move-result v1

    and-int/lit8 v1, v1, 0x1

    if-nez v1, :cond_42

    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    iget-boolean v1, v1, Lcom/android/server/PowerManagerService;->mUnplugTurnsOnScreen:Z

    if-eqz v1, :cond_47

    .line 435
    :cond_42
    iget-object v1, p0, Lcom/android/server/PowerManagerService$BatteryReceiver;->this$0:Lcom/android/server/PowerManagerService;

    #calls: Lcom/android/server/PowerManagerService;->forceUserActivityLocked()V
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$1000(Lcom/android/server/PowerManagerService;)V

    .line 437
    :cond_47
    monitor-exit v3
    :try_end_48
    .catchall {:try_start_32 .. :try_end_48} :catchall_4a

    .line 439
    :cond_48
    :try_start_48
    monitor-exit v2
    :try_end_49
    .catchall {:try_start_48 .. :try_end_49} :catchall_4d

    .line 440
    return-void

    .line 437
    :catchall_4a
    move-exception v1

    :try_start_4b
    monitor-exit v3
    :try_end_4c
    .catchall {:try_start_4b .. :try_end_4c} :catchall_4a

    :try_start_4c
    throw v1

    .line 439
    .end local v0           #wasPowered:Z
    :catchall_4d
    move-exception v1

    monitor-exit v2
    :try_end_4f
    .catchall {:try_start_4c .. :try_end_4f} :catchall_4d

    throw v1
.end method
