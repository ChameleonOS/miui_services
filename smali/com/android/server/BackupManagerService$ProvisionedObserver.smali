.class Lcom/android/server/BackupManagerService$ProvisionedObserver;
.super Landroid/database/ContentObserver;
.source "BackupManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/BackupManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ProvisionedObserver"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/BackupManagerService;


# direct methods
.method public constructor <init>(Lcom/android/server/BackupManagerService;Landroid/os/Handler;)V
    .registers 3
    .parameter
    .parameter "handler"

    .prologue
    .line 299
    iput-object p1, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    .line 300
    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    .line 301
    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .registers 8
    .parameter "selfChange"

    .prologue
    .line 304
    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    iget-boolean v1, v2, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    .line 305
    .local v1, wasProvisioned:Z
    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    invoke-virtual {v2}, Lcom/android/server/BackupManagerService;->deviceIsProvisioned()Z

    move-result v0

    .line 307
    .local v0, isProvisioned:Z
    iget-object v3, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    if-nez v1, :cond_10

    if-eqz v0, :cond_30

    :cond_10
    const/4 v2, 0x1

    :goto_11
    iput-boolean v2, v3, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    .line 313
    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v3, v2, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v3

    .line 314
    :try_start_18
    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    iget-boolean v2, v2, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    if-eqz v2, :cond_2e

    if-nez v1, :cond_2e

    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    iget-boolean v2, v2, Lcom/android/server/BackupManagerService;->mEnabled:Z

    if-eqz v2, :cond_2e

    .line 317
    iget-object v2, p0, Lcom/android/server/BackupManagerService$ProvisionedObserver;->this$0:Lcom/android/server/BackupManagerService;

    const-wide/32 v4, 0x2932e00

    #calls: Lcom/android/server/BackupManagerService;->startBackupAlarmsLocked(J)V
    invoke-static {v2, v4, v5}, Lcom/android/server/BackupManagerService;->access$000(Lcom/android/server/BackupManagerService;J)V

    .line 319
    :cond_2e
    monitor-exit v3

    .line 320
    return-void

    .line 307
    :cond_30
    const/4 v2, 0x0

    goto :goto_11

    .line 319
    :catchall_32
    move-exception v2

    monitor-exit v3
    :try_end_34
    .catchall {:try_start_18 .. :try_end_34} :catchall_32

    throw v2
.end method
