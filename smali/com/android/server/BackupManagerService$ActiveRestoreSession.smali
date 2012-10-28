.class Lcom/android/server/BackupManagerService$ActiveRestoreSession;
.super Landroid/app/backup/IRestoreSession$Stub;
.source "BackupManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/BackupManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ActiveRestoreSession"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/BackupManagerService$ActiveRestoreSession$EndRestoreRunnable;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "RestoreSession"


# instance fields
.field mEnded:Z

.field private mPackageName:Ljava/lang/String;

.field mRestoreSets:[Landroid/app/backup/RestoreSet;

.field private mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

.field final synthetic this$0:Lcom/android/server/BackupManagerService;


# direct methods
.method constructor <init>(Lcom/android/server/BackupManagerService;Ljava/lang/String;Ljava/lang/String;)V
    .registers 5
    .parameter
    .parameter "packageName"
    .parameter "transport"

    .prologue
    const/4 v0, 0x0

    .line 5560
    iput-object p1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    invoke-direct {p0}, Landroid/app/backup/IRestoreSession$Stub;-><init>()V

    .line 5556
    iput-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    .line 5557
    iput-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    .line 5558
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    .line 5561
    iput-object p2, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    .line 5562
    #calls: Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;
    invoke-static {p1, p3}, Lcom/android/server/BackupManagerService;->access$100(Lcom/android/server/BackupManagerService;Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    .line 5563
    return-void
.end method

.method static synthetic access$2000(Lcom/android/server/BackupManagerService$ActiveRestoreSession;)Lcom/android/internal/backup/IBackupTransport;
    .registers 2
    .parameter "x0"

    .prologue
    .line 5552
    iget-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    return-object v0
.end method

.method static synthetic access$2002(Lcom/android/server/BackupManagerService$ActiveRestoreSession;Lcom/android/internal/backup/IBackupTransport;)Lcom/android/internal/backup/IBackupTransport;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 5552
    iput-object p1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    return-object p1
.end method


# virtual methods
.method public declared-synchronized endRestoreSession()V
    .registers 4

    .prologue
    .line 5792
    monitor-enter p0

    :try_start_1
    const-string v0, "RestoreSession"

    const-string v1, "endRestoreSession"

    invoke-static {v0, v1}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5794
    iget-boolean v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    if-eqz v0, :cond_17

    .line 5795
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "Restore session already ended"

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_14
    .catchall {:try_start_1 .. :try_end_14} :catchall_14

    .line 5792
    :catchall_14
    move-exception v0

    monitor-exit p0

    throw v0

    .line 5798
    :cond_17
    :try_start_17
    iget-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    new-instance v1, Lcom/android/server/BackupManagerService$ActiveRestoreSession$EndRestoreRunnable;

    iget-object v2, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    invoke-direct {v1, p0, v2, p0}, Lcom/android/server/BackupManagerService$ActiveRestoreSession$EndRestoreRunnable;-><init>(Lcom/android/server/BackupManagerService$ActiveRestoreSession;Lcom/android/server/BackupManagerService;Lcom/android/server/BackupManagerService$ActiveRestoreSession;)V

    invoke-virtual {v0, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->post(Ljava/lang/Runnable;)Z
    :try_end_25
    .catchall {:try_start_17 .. :try_end_25} :catchall_14

    .line 5799
    monitor-exit p0

    return-void
.end method

.method public declared-synchronized getAvailableRestoreSets(Landroid/app/backup/IRestoreObserver;)I
    .registers 12
    .parameter "observer"

    .prologue
    const/4 v4, -0x1

    .line 5567
    monitor-enter p0

    :try_start_2
    iget-object v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;
    invoke-static {v5}, Lcom/android/server/BackupManagerService;->access$1900(Lcom/android/server/BackupManagerService;)Landroid/content/Context;

    move-result-object v5

    const-string v6, "android.permission.BACKUP"

    const-string v7, "getAvailableRestoreSets"

    invoke-virtual {v5, v6, v7}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5569
    if-nez p1, :cond_1c

    .line 5570
    new-instance v4, Ljava/lang/IllegalArgumentException;

    const-string v5, "Observer must not be null"

    invoke-direct {v4, v5}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v4
    :try_end_19
    .catchall {:try_start_2 .. :try_end_19} :catchall_19

    .line 5567
    :catchall_19
    move-exception v4

    monitor-exit p0

    throw v4

    .line 5573
    :cond_1c
    :try_start_1c
    iget-boolean v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    if-eqz v5, :cond_28

    .line 5574
    new-instance v4, Ljava/lang/IllegalStateException;

    const-string v5, "Restore session already ended"

    invoke-direct {v4, v5}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v4

    .line 5577
    :cond_28
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_2b
    .catchall {:try_start_1c .. :try_end_2b} :catchall_19

    move-result-wide v2

    .line 5579
    .local v2, oldId:J
    :try_start_2c
    iget-object v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    if-nez v5, :cond_3c

    .line 5580
    const-string v5, "RestoreSession"

    const-string v6, "Null transport getting restore sets"

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_37
    .catchall {:try_start_2c .. :try_end_37} :catchall_6d
    .catch Ljava/lang/Exception; {:try_start_2c .. :try_end_37} :catch_61

    .line 5593
    :try_start_37
    invoke-static {v2, v3}, Landroid/os/Binder;->restoreCallingIdentity(J)V
    :try_end_3a
    .catchall {:try_start_37 .. :try_end_3a} :catchall_19

    .line 5591
    :goto_3a
    monitor-exit p0

    return v4

    .line 5584
    :cond_3c
    :try_start_3c
    iget-object v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v5, v5, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v5}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5585
    iget-object v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v5, v5, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v6, 0x6

    new-instance v7, Lcom/android/server/BackupManagerService$RestoreGetSetsParams;

    iget-object v8, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v9, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    invoke-direct {v7, v8, v9, p0, p1}, Lcom/android/server/BackupManagerService$RestoreGetSetsParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Lcom/android/server/BackupManagerService$ActiveRestoreSession;Landroid/app/backup/IRestoreObserver;)V

    invoke-virtual {v5, v6, v7}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    .line 5587
    .local v1, msg:Landroid/os/Message;
    iget-object v5, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v5, v5, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v5, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z
    :try_end_5c
    .catchall {:try_start_3c .. :try_end_5c} :catchall_6d
    .catch Ljava/lang/Exception; {:try_start_3c .. :try_end_5c} :catch_61

    .line 5588
    const/4 v4, 0x0

    .line 5593
    :try_start_5d
    invoke-static {v2, v3}, Landroid/os/Binder;->restoreCallingIdentity(J)V
    :try_end_60
    .catchall {:try_start_5d .. :try_end_60} :catchall_19

    goto :goto_3a

    .line 5589
    .end local v1           #msg:Landroid/os/Message;
    :catch_61
    move-exception v0

    .line 5590
    .local v0, e:Ljava/lang/Exception;
    :try_start_62
    const-string v5, "RestoreSession"

    const-string v6, "Error in getAvailableRestoreSets"

    invoke-static {v5, v6, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_69
    .catchall {:try_start_62 .. :try_end_69} :catchall_6d

    .line 5593
    :try_start_69
    invoke-static {v2, v3}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    goto :goto_3a

    .end local v0           #e:Ljava/lang/Exception;
    :catchall_6d
    move-exception v4

    invoke-static {v2, v3}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v4
    :try_end_72
    .catchall {:try_start_69 .. :try_end_72} :catchall_19
.end method

.method public declared-synchronized restoreAll(JLandroid/app/backup/IRestoreObserver;)I
    .registers 16
    .parameter "token"
    .parameter "observer"

    .prologue
    const/4 v0, -0x1

    .line 5598
    monitor-enter p0

    :try_start_2
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;
    invoke-static {v1}, Lcom/android/server/BackupManagerService;->access$1900(Lcom/android/server/BackupManagerService;)Landroid/content/Context;

    move-result-object v1

    const-string v2, "android.permission.BACKUP"

    const-string v3, "performRestore"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5601
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "restoreAll token="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p1, p2}, Ljava/lang/Long;->toHexString(J)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " observer="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5604
    iget-boolean v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    if-eqz v1, :cond_44

    .line 5605
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "Restore session already ended"

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_41
    .catchall {:try_start_2 .. :try_end_41} :catchall_41

    .line 5598
    :catchall_41
    move-exception v0

    monitor-exit p0

    throw v0

    .line 5608
    :cond_44
    :try_start_44
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    if-eqz v1, :cond_4c

    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    if-nez v1, :cond_55

    .line 5609
    :cond_4c
    const-string v1, "RestoreSession"

    const-string v2, "Ignoring restoreAll() with no restore set"

    invoke-static {v1, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_53
    .catchall {:try_start_44 .. :try_end_53} :catchall_41

    .line 5633
    :goto_53
    monitor-exit p0

    return v0

    .line 5613
    :cond_55
    :try_start_55
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    if-eqz v1, :cond_61

    .line 5614
    const-string v1, "RestoreSession"

    const-string v2, "Ignoring restoreAll() on single-package session"

    invoke-static {v1, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_53

    .line 5618
    :cond_61
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v11, v1, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v11
    :try_end_66
    .catchall {:try_start_55 .. :try_end_66} :catchall_41

    .line 5619
    const/4 v7, 0x0

    .local v7, i:I
    :goto_67
    :try_start_67
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    array-length v1, v1

    if-ge v7, v1, :cond_ab

    .line 5620
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    aget-object v1, v1, v7

    iget-wide v1, v1, Landroid/app/backup/RestoreSet;->token:J

    cmp-long v1, p1, v1

    if-nez v1, :cond_a8

    .line 5621
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v9

    .line 5622
    .local v9, oldId:J
    iget-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5623
    iget-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v8

    .line 5624
    .local v8, msg:Landroid/os/Message;
    new-instance v0, Lcom/android/server/BackupManagerService$RestoreParams;

    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v2, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    const/4 v6, 0x1

    move-object v3, p3

    move-wide v4, p1

    invoke-direct/range {v0 .. v6}, Lcom/android/server/BackupManagerService$RestoreParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Landroid/app/backup/IRestoreObserver;JZ)V

    iput-object v0, v8, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 5625
    iget-object v0, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v0, v8}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5626
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5627
    const/4 v0, 0x0

    monitor-exit v11

    goto :goto_53

    .line 5630
    .end local v8           #msg:Landroid/os/Message;
    .end local v9           #oldId:J
    :catchall_a5
    move-exception v0

    monitor-exit v11
    :try_end_a7
    .catchall {:try_start_67 .. :try_end_a7} :catchall_a5

    :try_start_a7
    throw v0
    :try_end_a8
    .catchall {:try_start_a7 .. :try_end_a8} :catchall_41

    .line 5619
    :cond_a8
    add-int/lit8 v7, v7, 0x1

    goto :goto_67

    .line 5630
    :cond_ab
    :try_start_ab
    monitor-exit v11
    :try_end_ac
    .catchall {:try_start_ab .. :try_end_ac} :catchall_a5

    .line 5632
    :try_start_ac
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Restore token "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p1, p2}, Ljava/lang/Long;->toHexString(J)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " not found"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_ce
    .catchall {:try_start_ac .. :try_end_ce} :catchall_41

    goto :goto_53
.end method

.method public declared-synchronized restorePackage(Ljava/lang/String;Landroid/app/backup/IRestoreObserver;)I
    .registers 18
    .parameter "packageName"
    .parameter "observer"

    .prologue
    .line 5698
    monitor-enter p0

    :try_start_1
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "restorePackage pkg="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " obs="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5700
    iget-boolean v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    if-eqz v1, :cond_36

    .line 5701
    new-instance v1, Ljava/lang/IllegalStateException;

    const-string v2, "Restore session already ended"

    invoke-direct {v1, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_33
    .catchall {:try_start_1 .. :try_end_33} :catchall_33

    .line 5698
    :catchall_33
    move-exception v1

    monitor-exit p0

    throw v1

    .line 5704
    :cond_36
    :try_start_36
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    if-eqz v1, :cond_6d

    .line 5705
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    move-object/from16 v0, p1

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_6d

    .line 5706
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Ignoring attempt to restore pkg="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " on session for package "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_6a
    .catchall {:try_start_36 .. :try_end_6a} :catchall_33

    .line 5708
    const/4 v1, -0x1

    .line 5757
    :goto_6b
    monitor-exit p0

    return v1

    .line 5712
    :cond_6d
    const/4 v7, 0x0

    .line 5714
    .local v7, app:Landroid/content/pm/PackageInfo;
    :try_start_6e
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;
    invoke-static {v1}, Lcom/android/server/BackupManagerService;->access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;

    move-result-object v1

    const/4 v2, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v1, v0, v2}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    :try_end_7a
    .catchall {:try_start_6e .. :try_end_7a} :catchall_33
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_6e .. :try_end_7a} :catch_cc

    move-result-object v7

    .line 5722
    :try_start_7b
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;
    invoke-static {v1}, Lcom/android/server/BackupManagerService;->access$1900(Lcom/android/server/BackupManagerService;)Landroid/content/Context;

    move-result-object v1

    const-string v2, "android.permission.BACKUP"

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v3

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v4

    invoke-virtual {v1, v2, v3, v4}, Landroid/content/Context;->checkPermission(Ljava/lang/String;II)I

    move-result v14

    .line 5724
    .local v14, perm:I
    const/4 v1, -0x1

    if-ne v14, v1, :cond_e9

    iget-object v1, v7, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v1, v1, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v2

    if-eq v1, v2, :cond_e9

    .line 5726
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "restorePackage: bad packageName="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " or calling uid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5728
    new-instance v1, Ljava/lang/SecurityException;

    const-string v2, "No permission to restore other packages"

    invoke-direct {v1, v2}, Ljava/lang/SecurityException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 5715
    .end local v14           #perm:I
    :catch_cc
    move-exception v11

    .line 5716
    .local v11, nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Asked to restore nonexistent pkg "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5717
    const/4 v1, -0x1

    goto :goto_6b

    .line 5732
    .end local v11           #nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v14       #perm:I
    :cond_e9
    iget-object v1, v7, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v1, v1, Landroid/content/pm/ApplicationInfo;->backupAgentName:Ljava/lang/String;

    if-nez v1, :cond_112

    .line 5733
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Asked to restore package "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " with no agent"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5734
    const/4 v1, -0x1

    goto/16 :goto_6b

    .line 5740
    :cond_112
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v0, p1

    invoke-virtual {v1, v0}, Lcom/android/server/BackupManagerService;->getAvailableRestoreToken(Ljava/lang/String;)J

    move-result-wide v5

    .line 5745
    .local v5, token:J
    const-wide/16 v1, 0x0

    cmp-long v1, v5, v1

    if-nez v1, :cond_12a

    .line 5746
    const-string v1, "RestoreSession"

    const-string v2, "No data available for this package; not restoring"

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5747
    const/4 v1, -0x1

    goto/16 :goto_6b

    .line 5751
    :cond_12a
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v12

    .line 5752
    .local v12, oldId:J
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v1}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5753
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v2, 0x3

    invoke-virtual {v1, v2}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v10

    .line 5754
    .local v10, msg:Landroid/os/Message;
    new-instance v1, Lcom/android/server/BackupManagerService$RestoreParams;

    iget-object v2, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v3, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    const/4 v8, 0x0

    const/4 v9, 0x0

    move-object/from16 v4, p2

    invoke-direct/range {v1 .. v9}, Lcom/android/server/BackupManagerService$RestoreParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Landroid/app/backup/IRestoreObserver;JLandroid/content/pm/PackageInfo;IZ)V

    iput-object v1, v10, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 5755
    iget-object v1, p0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v1, v10}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5756
    invoke-static {v12, v13}, Landroid/os/Binder;->restoreCallingIdentity(J)V
    :try_end_157
    .catchall {:try_start_7b .. :try_end_157} :catchall_33

    .line 5757
    const/4 v1, 0x0

    goto/16 :goto_6b
.end method

.method public declared-synchronized restoreSome(JLandroid/app/backup/IRestoreObserver;[Ljava/lang/String;)I
    .registers 25
    .parameter "token"
    .parameter "observer"
    .parameter "packages"

    .prologue
    .line 5638
    monitor-enter p0

    :try_start_1
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;
    invoke-static {v1}, Lcom/android/server/BackupManagerService;->access$1900(Lcom/android/server/BackupManagerService;)Landroid/content/Context;

    move-result-object v1

    const-string v2, "android.permission.BACKUP"

    const-string v3, "performRestore"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5642
    new-instance v10, Ljava/lang/StringBuilder;

    const/16 v1, 0x80

    invoke-direct {v10, v1}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 5643
    .local v10, b:Ljava/lang/StringBuilder;
    const-string v1, "restoreSome token="

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5644
    invoke-static/range {p1 .. p2}, Ljava/lang/Long;->toHexString(J)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5645
    const-string v1, " observer="

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5646
    invoke-virtual/range {p3 .. p3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5647
    const-string v1, " packages="

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5648
    if-nez p4, :cond_55

    .line 5649
    const-string v1, "null"

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5661
    :goto_3b
    const-string v1, "RestoreSession"

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5664
    move-object/from16 v0, p0

    iget-boolean v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mEnded:Z

    if-eqz v1, :cond_7a

    .line 5665
    new-instance v1, Ljava/lang/IllegalStateException;

    const-string v2, "Restore session already ended"

    invoke-direct {v1, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_52
    .catchall {:try_start_1 .. :try_end_52} :catchall_52

    .line 5638
    .end local v10           #b:Ljava/lang/StringBuilder;
    :catchall_52
    move-exception v1

    monitor-exit p0

    throw v1

    .line 5651
    .restart local v10       #b:Ljava/lang/StringBuilder;
    :cond_55
    const/16 v1, 0x7b

    :try_start_57
    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 5652
    const/4 v11, 0x1

    .line 5653
    .local v11, first:Z
    move-object/from16 v9, p4

    .local v9, arr$:[Ljava/lang/String;
    array-length v14, v9

    .local v14, len$:I
    const/4 v13, 0x0

    .local v13, i$:I
    :goto_5f
    if-ge v13, v14, :cond_74

    aget-object v18, v9, v13

    .line 5654
    .local v18, s:Ljava/lang/String;
    if-nez v11, :cond_72

    .line 5655
    const-string v1, ", "

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5657
    :goto_6a
    move-object/from16 v0, v18

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 5653
    add-int/lit8 v13, v13, 0x1

    goto :goto_5f

    .line 5656
    :cond_72
    const/4 v11, 0x0

    goto :goto_6a

    .line 5659
    .end local v18           #s:Ljava/lang/String;
    :cond_74
    const/16 v1, 0x7d

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    goto :goto_3b

    .line 5668
    .end local v9           #arr$:[Ljava/lang/String;
    .end local v11           #first:Z
    .end local v13           #i$:I
    .end local v14           #len$:I
    :cond_7a
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    if-eqz v1, :cond_86

    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    if-nez v1, :cond_90

    .line 5669
    :cond_86
    const-string v1, "RestoreSession"

    const-string v2, "Ignoring restoreAll() with no restore set"

    invoke-static {v1, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_8d
    .catchall {:try_start_57 .. :try_end_8d} :catchall_52

    .line 5670
    const/4 v1, -0x1

    .line 5694
    :goto_8e
    monitor-exit p0

    return v1

    .line 5673
    :cond_90
    :try_start_90
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mPackageName:Ljava/lang/String;

    if-eqz v1, :cond_9f

    .line 5674
    const-string v1, "RestoreSession"

    const-string v2, "Ignoring restoreAll() on single-package session"

    invoke-static {v1, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 5675
    const/4 v1, -0x1

    goto :goto_8e

    .line 5678
    :cond_9f
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v0, v1, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    move-object/from16 v19, v0

    monitor-enter v19
    :try_end_a8
    .catchall {:try_start_90 .. :try_end_a8} :catchall_52

    .line 5679
    const/4 v12, 0x0

    .local v12, i:I
    :goto_a9
    :try_start_a9
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    array-length v1, v1

    if-ge v12, v1, :cond_ff

    .line 5680
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreSets:[Landroid/app/backup/RestoreSet;

    aget-object v1, v1, v12

    iget-wide v1, v1, Landroid/app/backup/RestoreSet;->token:J

    cmp-long v1, p1, v1

    if-nez v1, :cond_fc

    .line 5681
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v16

    .line 5682
    .local v16, oldId:J
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v1}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5683
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v2, 0x3

    invoke-virtual {v1, v2}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v15

    .line 5684
    .local v15, msg:Landroid/os/Message;
    new-instance v1, Lcom/android/server/BackupManagerService$RestoreParams;

    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->mRestoreTransport:Lcom/android/internal/backup/IBackupTransport;

    const/4 v8, 0x1

    move-object/from16 v4, p3

    move-wide/from16 v5, p1

    move-object/from16 v7, p4

    invoke-direct/range {v1 .. v8}, Lcom/android/server/BackupManagerService$RestoreParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Landroid/app/backup/IRestoreObserver;J[Ljava/lang/String;Z)V

    iput-object v1, v15, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 5686
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/BackupManagerService$ActiveRestoreSession;->this$0:Lcom/android/server/BackupManagerService;

    iget-object v1, v1, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v1, v15}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5687
    invoke-static/range {v16 .. v17}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5688
    const/4 v1, 0x0

    monitor-exit v19

    goto :goto_8e

    .line 5691
    .end local v15           #msg:Landroid/os/Message;
    .end local v16           #oldId:J
    :catchall_f9
    move-exception v1

    monitor-exit v19
    :try_end_fb
    .catchall {:try_start_a9 .. :try_end_fb} :catchall_f9

    :try_start_fb
    throw v1
    :try_end_fc
    .catchall {:try_start_fb .. :try_end_fc} :catchall_52

    .line 5679
    :cond_fc
    add-int/lit8 v12, v12, 0x1

    goto :goto_a9

    .line 5691
    :cond_ff
    :try_start_ff
    monitor-exit v19
    :try_end_100
    .catchall {:try_start_ff .. :try_end_100} :catchall_f9

    .line 5693
    :try_start_100
    const-string v1, "RestoreSession"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Restore token "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static/range {p1 .. p2}, Ljava/lang/Long;->toHexString(J)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " not found"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_122
    .catchall {:try_start_100 .. :try_end_122} :catchall_52

    .line 5694
    const/4 v1, -0x1

    goto/16 :goto_8e
.end method
