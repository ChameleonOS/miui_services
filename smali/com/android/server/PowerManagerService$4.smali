.class Lcom/android/server/PowerManagerService$4;
.super Ljava/lang/Object;
.source "PowerManagerService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/PowerManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/PowerManagerService;


# direct methods
.method constructor <init>(Lcom/android/server/PowerManagerService;)V
    .registers 2
    .parameter

    .prologue
    .line 1528
    iput-object p1, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 15

    .prologue
    const/4 v13, 0x2

    const/4 v2, 0x0

    const/4 v12, 0x1

    const/4 v5, 0x0

    .line 1535
    :goto_4
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v1

    monitor-enter v1

    .line 1536
    :try_start_b
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastQueue:[I
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3400(Lcom/android/server/PowerManagerService;)[I

    move-result-object v0

    const/4 v3, 0x0

    aget v10, v0, v3

    .line 1537
    .local v10, value:I
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWhy:[I
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3500(Lcom/android/server/PowerManagerService;)[I

    move-result-object v0

    const/4 v3, 0x0

    aget v11, v0, v3

    .line 1538
    .local v11, why:I
    const/4 v8, 0x0

    .local v8, i:I
    :goto_1e
    if-ge v8, v13, :cond_47

    .line 1539
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastQueue:[I
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3400(Lcom/android/server/PowerManagerService;)[I

    move-result-object v0

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastQueue:[I
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$3400(Lcom/android/server/PowerManagerService;)[I

    move-result-object v3

    add-int/lit8 v4, v8, 0x1

    aget v3, v3, v4

    aput v3, v0, v8

    .line 1540
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWhy:[I
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3500(Lcom/android/server/PowerManagerService;)[I

    move-result-object v0

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWhy:[I
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$3500(Lcom/android/server/PowerManagerService;)[I

    move-result-object v3

    add-int/lit8 v4, v8, 0x1

    aget v3, v3, v4

    aput v3, v0, v8

    .line 1538
    add-int/lit8 v8, v8, 0x1

    goto :goto_1e

    .line 1542
    :cond_47
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    invoke-virtual {v0}, Lcom/android/server/PowerManagerService;->getPolicyLocked()Landroid/view/WindowManagerPolicy;

    move-result-object v9

    .line 1543
    .local v9, policy:Landroid/view/WindowManagerPolicy;
    if-ne v10, v12, :cond_73

    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mPreparingForScreenOn:Z
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3100(Lcom/android/server/PowerManagerService;)Z

    move-result v0

    if-nez v0, :cond_73

    .line 1544
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    const/4 v3, 0x1

    #setter for: Lcom/android/server/PowerManagerService;->mPreparingForScreenOn:Z
    invoke-static {v0, v3}, Lcom/android/server/PowerManagerService;->access$3102(Lcom/android/server/PowerManagerService;Z)Z

    .line 1545
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->acquire()V

    .line 1546
    const/16 v0, 0xaa5

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v3

    iget v3, v3, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->mCount:I

    invoke-static {v0, v3}, Landroid/util/EventLog;->writeEvent(II)I

    .line 1549
    :cond_73
    monitor-exit v1
    :try_end_74
    .catchall {:try_start_b .. :try_end_74} :catchall_bb

    .line 1550
    if-ne v10, v12, :cond_f3

    .line 1551
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v3

    iput-wide v3, v0, Lcom/android/server/PowerManagerService;->mScreenOnStart:J

    .line 1553
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mScreenOnListener:Landroid/view/WindowManagerPolicy$ScreenOnListener;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3600(Lcom/android/server/PowerManagerService;)Landroid/view/WindowManagerPolicy$ScreenOnListener;

    move-result-object v0

    invoke-interface {v9, v0}, Landroid/view/WindowManagerPolicy;->screenTurningOn(Landroid/view/WindowManagerPolicy$ScreenOnListener;)V

    .line 1555
    :try_start_87
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v0

    invoke-interface {v0}, Landroid/app/IActivityManager;->wakingUp()V
    :try_end_8e
    .catch Landroid/os/RemoteException; {:try_start_87 .. :try_end_8e} :catch_178

    .line 1563
    :goto_8e
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3700(Lcom/android/server/PowerManagerService;)Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_be

    invoke-static {}, Landroid/app/ActivityManagerNative;->isSystemReady()Z

    move-result v0

    if-eqz v0, :cond_be

    .line 1564
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3700(Lcom/android/server/PowerManagerService;)Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mScreenOnIntent:Landroid/content/Intent;
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$3800(Lcom/android/server/PowerManagerService;)Landroid/content/Intent;

    move-result-object v1

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mScreenOnBroadcastDone:Landroid/content/BroadcastReceiver;
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$3900(Lcom/android/server/PowerManagerService;)Landroid/content/BroadcastReceiver;

    move-result-object v3

    iget-object v4, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mHandler:Landroid/os/Handler;
    invoke-static {v4}, Lcom/android/server/PowerManagerService;->access$4000(Lcom/android/server/PowerManagerService;)Landroid/os/Handler;

    move-result-object v4

    move-object v6, v2

    move-object v7, v2

    invoke-virtual/range {v0 .. v7}, Landroid/content/Context;->sendOrderedBroadcast(Landroid/content/Intent;Ljava/lang/String;Landroid/content/BroadcastReceiver;Landroid/os/Handler;ILjava/lang/String;Landroid/os/Bundle;)V

    goto/16 :goto_4

    .line 1549
    .end local v8           #i:I
    .end local v9           #policy:Landroid/view/WindowManagerPolicy;
    .end local v10           #value:I
    .end local v11           #why:I
    :catchall_bb
    move-exception v0

    :try_start_bc
    monitor-exit v1
    :try_end_bd
    .catchall {:try_start_bc .. :try_end_bd} :catchall_bb

    throw v0

    .line 1567
    .restart local v8       #i:I
    .restart local v9       #policy:Landroid/view/WindowManagerPolicy;
    .restart local v10       #value:I
    .restart local v11       #why:I
    :cond_be
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v1

    monitor-enter v1

    .line 1568
    const/16 v0, 0xaa7

    const/4 v3, 0x2

    :try_start_c8
    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    const/4 v6, 0x2

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v3, v4

    const/4 v4, 0x1

    iget-object v6, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v6}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v6

    iget v6, v6, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->mCount:I

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v3, v4

    invoke-static {v0, v3}, Landroid/util/EventLog;->writeEvent(I[Ljava/lang/Object;)I

    .line 1570
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->release()V

    .line 1571
    monitor-exit v1

    goto/16 :goto_4

    :catchall_f0
    move-exception v0

    monitor-exit v1
    :try_end_f2
    .catchall {:try_start_c8 .. :try_end_f2} :catchall_f0

    throw v0

    .line 1574
    :cond_f3
    if-nez v10, :cond_175

    .line 1575
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v3

    iput-wide v3, v0, Lcom/android/server/PowerManagerService;->mScreenOffStart:J

    .line 1577
    invoke-interface {v9, v11}, Landroid/view/WindowManagerPolicy;->screenTurnedOff(I)V

    .line 1579
    :try_start_100
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v0

    invoke-interface {v0}, Landroid/app/IActivityManager;->goingToSleep()V
    :try_end_107
    .catch Landroid/os/RemoteException; {:try_start_100 .. :try_end_107} :catch_176

    .line 1584
    :goto_107
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3700(Lcom/android/server/PowerManagerService;)Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_134

    invoke-static {}, Landroid/app/ActivityManagerNative;->isSystemReady()Z

    move-result v0

    if-eqz v0, :cond_134

    .line 1585
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3700(Lcom/android/server/PowerManagerService;)Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mScreenOffIntent:Landroid/content/Intent;
    invoke-static {v1}, Lcom/android/server/PowerManagerService;->access$4100(Lcom/android/server/PowerManagerService;)Landroid/content/Intent;

    move-result-object v1

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mScreenOffBroadcastDone:Landroid/content/BroadcastReceiver;
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$4200(Lcom/android/server/PowerManagerService;)Landroid/content/BroadcastReceiver;

    move-result-object v3

    iget-object v4, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mHandler:Landroid/os/Handler;
    invoke-static {v4}, Lcom/android/server/PowerManagerService;->access$4000(Lcom/android/server/PowerManagerService;)Landroid/os/Handler;

    move-result-object v4

    move-object v6, v2

    move-object v7, v2

    invoke-virtual/range {v0 .. v7}, Landroid/content/Context;->sendOrderedBroadcast(Landroid/content/Intent;Ljava/lang/String;Landroid/content/BroadcastReceiver;Landroid/os/Handler;ILjava/lang/String;Landroid/os/Bundle;)V

    goto/16 :goto_4

    .line 1588
    :cond_134
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v1

    monitor-enter v1

    .line 1589
    const/16 v0, 0xaa7

    const/4 v3, 0x2

    :try_start_13e
    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    const/4 v6, 0x3

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v3, v4

    const/4 v4, 0x1

    iget-object v6, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v6}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v6

    iget v6, v6, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->mCount:I

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v3, v4

    invoke-static {v0, v3}, Landroid/util/EventLog;->writeEvent(I[Ljava/lang/Object;)I

    .line 1591
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    iget-object v3, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mPowerState:I
    invoke-static {v3}, Lcom/android/server/PowerManagerService;->access$900(Lcom/android/server/PowerManagerService;)I

    move-result v3

    const/4 v4, 0x1

    #calls: Lcom/android/server/PowerManagerService;->updateLightsLocked(II)V
    invoke-static {v0, v3, v4}, Lcom/android/server/PowerManagerService;->access$3200(Lcom/android/server/PowerManagerService;II)V

    .line 1592
    iget-object v0, p0, Lcom/android/server/PowerManagerService$4;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mBroadcastWakeLock:Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$3300(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/server/PowerManagerService$UnsynchronizedWakeLock;->release()V

    .line 1593
    monitor-exit v1

    goto/16 :goto_4

    :catchall_172
    move-exception v0

    monitor-exit v1
    :try_end_174
    .catchall {:try_start_13e .. :try_end_174} :catchall_172

    throw v0

    .line 1602
    :cond_175
    return-void

    .line 1580
    :catch_176
    move-exception v0

    goto :goto_107

    .line 1556
    :catch_178
    move-exception v0

    goto/16 :goto_8e
.end method
