.class Lcom/android/server/PowerManagerService$WakeLock;
.super Ljava/lang/Object;
.source "PowerManagerService.java"

# interfaces
.implements Landroid/os/IBinder$DeathRecipient;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/PowerManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "WakeLock"
.end annotation


# instance fields
.field activated:Z

.field final binder:Landroid/os/IBinder;

.field final flags:I

.field minState:I

.field final monitorType:I

.field final pid:I

.field final tag:Ljava/lang/String;

.field final synthetic this$0:Lcom/android/server/PowerManagerService;

.field final uid:I

.field ws:Landroid/os/WorkSource;


# direct methods
.method constructor <init>(Lcom/android/server/PowerManagerService;ILandroid/os/IBinder;Ljava/lang/String;II)V
    .registers 12
    .parameter
    .parameter "f"
    .parameter "b"
    .parameter "t"
    .parameter "u"
    .parameter "p"

    .prologue
    const/4 v2, 0x0

    const/4 v3, 0x1

    .line 769
    iput-object p1, p0, Lcom/android/server/PowerManagerService$WakeLock;->this$0:Lcom/android/server/PowerManagerService;

    .line 770
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 803
    iput-boolean v3, p0, Lcom/android/server/PowerManagerService$WakeLock;->activated:Z

    .line 771
    iput p2, p0, Lcom/android/server/PowerManagerService$WakeLock;->flags:I

    .line 772
    iput-object p3, p0, Lcom/android/server/PowerManagerService$WakeLock;->binder:Landroid/os/IBinder;

    .line 773
    iput-object p4, p0, Lcom/android/server/PowerManagerService$WakeLock;->tag:Ljava/lang/String;

    .line 774
    #getter for: Lcom/android/server/PowerManagerService;->MY_UID:I
    invoke-static {p1}, Lcom/android/server/PowerManagerService;->access$200(Lcom/android/server/PowerManagerService;)I

    move-result v1

    if-ne p5, v1, :cond_41

    const/16 v1, 0x3e8

    :goto_17
    iput v1, p0, Lcom/android/server/PowerManagerService$WakeLock;->uid:I

    .line 775
    iput p6, p0, Lcom/android/server/PowerManagerService$WakeLock;->pid:I

    .line 776
    #getter for: Lcom/android/server/PowerManagerService;->MY_UID:I
    invoke-static {p1}, Lcom/android/server/PowerManagerService;->access$200(Lcom/android/server/PowerManagerService;)I

    move-result v1

    if-ne p5, v1, :cond_35

    const-string v1, "KEEP_SCREEN_ON_FLAG"

    iget-object v4, p0, Lcom/android/server/PowerManagerService$WakeLock;->tag:Ljava/lang/String;

    invoke-virtual {v1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_45

    const-string v1, "KeyInputQueue"

    iget-object v4, p0, Lcom/android/server/PowerManagerService$WakeLock;->tag:Ljava/lang/String;

    invoke-virtual {v1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_45

    .line 779
    :cond_35
    and-int/lit8 v1, p2, 0x3f

    if-ne v1, v3, :cond_43

    move v1, v2

    :goto_3a
    iput v1, p0, Lcom/android/server/PowerManagerService$WakeLock;->monitorType:I

    .line 786
    :goto_3c
    const/4 v1, 0x0

    :try_start_3d
    invoke-interface {p3, p0, v1}, Landroid/os/IBinder;->linkToDeath(Landroid/os/IBinder$DeathRecipient;I)V
    :try_end_40
    .catch Landroid/os/RemoteException; {:try_start_3d .. :try_end_40} :catch_49

    .line 790
    :goto_40
    return-void

    :cond_41
    move v1, p5

    .line 774
    goto :goto_17

    :cond_43
    move v1, v3

    .line 779
    goto :goto_3a

    .line 783
    :cond_45
    const/4 v1, -0x1

    iput v1, p0, Lcom/android/server/PowerManagerService$WakeLock;->monitorType:I

    goto :goto_3c

    .line 787
    :catch_49
    move-exception v0

    .line 788
    .local v0, e:Landroid/os/RemoteException;
    invoke-virtual {p0}, Lcom/android/server/PowerManagerService$WakeLock;->binderDied()V

    goto :goto_40
.end method


# virtual methods
.method public binderDied()V
    .registers 6

    .prologue
    .line 792
    iget-object v0, p0, Lcom/android/server/PowerManagerService$WakeLock;->this$0:Lcom/android/server/PowerManagerService;

    #getter for: Lcom/android/server/PowerManagerService;->mLocks:Lcom/android/server/PowerManagerService$LockList;
    invoke-static {v0}, Lcom/android/server/PowerManagerService;->access$500(Lcom/android/server/PowerManagerService;)Lcom/android/server/PowerManagerService$LockList;

    move-result-object v1

    monitor-enter v1

    .line 793
    :try_start_7
    iget-object v0, p0, Lcom/android/server/PowerManagerService$WakeLock;->this$0:Lcom/android/server/PowerManagerService;

    iget-object v2, p0, Lcom/android/server/PowerManagerService$WakeLock;->binder:Landroid/os/IBinder;

    const/4 v3, 0x0

    const/4 v4, 0x1

    #calls: Lcom/android/server/PowerManagerService;->releaseWakeLockLocked(Landroid/os/IBinder;IZ)V
    invoke-static {v0, v2, v3, v4}, Lcom/android/server/PowerManagerService;->access$400(Lcom/android/server/PowerManagerService;Landroid/os/IBinder;IZ)V

    .line 794
    monitor-exit v1

    .line 795
    return-void

    .line 794
    :catchall_12
    move-exception v0

    monitor-exit v1
    :try_end_14
    .catchall {:try_start_7 .. :try_end_14} :catchall_12

    throw v0
.end method
