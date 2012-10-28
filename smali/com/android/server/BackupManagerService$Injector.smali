.class Lcom/android/server/BackupManagerService$Injector;
.super Ljava/lang/Object;
.source "BackupManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/BackupManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 141
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static startConfirmationTimeout(Lcom/android/server/BackupManagerService;ILcom/android/server/BackupManagerService$FullParams;)V
    .registers 3
    .parameter "service"
    .parameter "token"
    .parameter "params"

    .prologue
    .line 169
    return-void
.end method

.method static startConfirmationUi(Lcom/android/server/BackupManagerService;ILjava/lang/String;)Z
    .registers 10
    .parameter "service"
    .parameter "token"
    .parameter "action"

    .prologue
    .line 144
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    monitor-enter v4

    .line 145
    :try_start_3
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/BackupManagerService$FullParams;

    .line 146
    .local v1, params:Lcom/android/server/BackupManagerService$FullParams;
    if-eqz v1, :cond_5c

    .line 147
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->delete(I)V

    .line 149
    instance-of v3, v1, Lcom/android/server/BackupManagerService$FullBackupParams;

    if-eqz v3, :cond_59

    const/4 v2, 0x2

    .line 150
    .local v2, verb:I
    :goto_17
    const/4 v3, 0x0

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->observer:Landroid/app/backup/IFullBackupRestoreObserver;

    .line 151
    const-string v3, ""

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->curPassword:Ljava/lang/String;

    .line 152
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    sget-object v5, Lmiui/provider/ExtraSettings$Secure;->APP_ENCRYPT_PASSWORD:Ljava/lang/String;

    invoke-static {v3, v5}, Landroid/provider/Settings$Secure;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->encryptPassword:Ljava/lang/String;

    .line 156
    const-string v3, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Sending conf message with verb "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 157
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v3}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 158
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v3, v2, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    .line 159
    .local v0, msg:Landroid/os/Message;
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v3, v0}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 163
    .end local v0           #msg:Landroid/os/Message;
    .end local v2           #verb:I
    :goto_56
    monitor-exit v4

    .line 164
    const/4 v3, 0x1

    return v3

    .line 149
    :cond_59
    const/16 v2, 0xa

    goto :goto_17

    .line 161
    :cond_5c
    const-string v3, "BackupManagerService"

    const-string v5, "Attempted to ack full backup/restore with invalid token"

    invoke-static {v3, v5}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_56

    .line 163
    .end local v1           #params:Lcom/android/server/BackupManagerService$FullParams;
    :catchall_64
    move-exception v3

    monitor-exit v4
    :try_end_66
    .catchall {:try_start_3 .. :try_end_66} :catchall_64

    throw v3
.end method
