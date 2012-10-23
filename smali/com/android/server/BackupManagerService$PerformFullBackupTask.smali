.class Lcom/android/server/BackupManagerService$PerformFullBackupTask;
.super Ljava/lang/Object;
.source "BackupManagerService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/BackupManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "PerformFullBackupTask"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    }
.end annotation


# instance fields
.field mAllApps:Z

.field mCurrentPassword:Ljava/lang/String;

.field mDeflater:Ljava/util/zip/DeflaterOutputStream;

.field mEncryptPassword:Ljava/lang/String;

.field mFilesDir:Ljava/io/File;

.field mIncludeApks:Z

.field mIncludeShared:Z

.field final mIncludeSystem:Z

.field mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

.field mManifestFile:Ljava/io/File;

.field mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

.field mOutputFile:Landroid/os/ParcelFileDescriptor;

.field mPackages:[Ljava/lang/String;

.field final synthetic this$0:Lcom/android/server/BackupManagerService;


# direct methods
.method constructor <init>(Lcom/android/server/BackupManagerService;Landroid/os/ParcelFileDescriptor;Landroid/app/backup/IFullBackupRestoreObserver;ZZLjava/lang/String;Ljava/lang/String;ZZ[Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicBoolean;)V
    .registers 15
    .parameter
    .parameter "fd"
    .parameter "observer"
    .parameter "includeApks"
    .parameter "includeShared"
    .parameter "curPassword"
    .parameter "encryptPassword"
    .parameter "doAllApps"
    .parameter "doSystem"
    .parameter "packages"
    .parameter "latch"

    .prologue
    .line 2382
    iput-object p1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 2383
    iput-object p2, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    .line 2384
    iput-object p3, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    .line 2385
    iput-boolean p4, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeApks:Z

    .line 2386
    iput-boolean p5, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeShared:Z

    .line 2387
    iput-boolean p8, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mAllApps:Z

    .line 2388
    iput-boolean p9, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeSystem:Z

    .line 2389
    iput-object p10, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mPackages:[Ljava/lang/String;

    .line 2390
    iput-object p6, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mCurrentPassword:Ljava/lang/String;

    .line 2395
    if-eqz p7, :cond_1f

    const-string v0, ""

    invoke-virtual {v0, p7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_38

    .line 2396
    :cond_1f
    iput-object p6, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mEncryptPassword:Ljava/lang/String;

    .line 2400
    :goto_21
    iput-object p11, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 2402
    new-instance v0, Ljava/io/File;

    const-string v1, "/data/system"

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mFilesDir:Ljava/io/File;

    .line 2403
    new-instance v0, Ljava/io/File;

    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mFilesDir:Ljava/io/File;

    const-string v2, "_manifest"

    invoke-direct {v0, v1, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    iput-object v0, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mManifestFile:Ljava/io/File;

    .line 2404
    return-void

    .line 2398
    :cond_38
    iput-object p7, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mEncryptPassword:Ljava/lang/String;

    goto :goto_21
.end method

.method static synthetic access$1000(Lcom/android/server/BackupManagerService$PerformFullBackupTask;Landroid/content/pm/PackageInfo;Landroid/app/backup/BackupDataOutput;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 2311
    invoke-direct {p0, p1, p2}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->writeApkToBackup(Landroid/content/pm/PackageInfo;Landroid/app/backup/BackupDataOutput;)V

    return-void
.end method

.method static synthetic access$900(Lcom/android/server/BackupManagerService$PerformFullBackupTask;Landroid/content/pm/PackageInfo;Ljava/io/File;Z)V
    .registers 4
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"
    .parameter "x3"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 2311
    invoke-direct {p0, p1, p2, p3}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->writeAppManifest(Landroid/content/pm/PackageInfo;Ljava/io/File;Z)V

    return-void
.end method

.method private backupOnePackage(Landroid/content/pm/PackageInfo;Ljava/io/OutputStream;)V
    .registers 23
    .parameter "pkg"
    .parameter "out"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .prologue
    .line 2662
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Binding to full backup agent : "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p1

    iget-object v5, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2664
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v0, p1

    iget-object v3, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    const/4 v5, 0x1

    invoke-virtual {v2, v3, v5}, Lcom/android/server/BackupManagerService;->bindToAgentSynchronous(Landroid/content/pm/ApplicationInfo;I)Landroid/app/IBackupAgent;

    move-result-object v4

    .line 2666
    .local v4, agent:Landroid/app/IBackupAgent;
    if-eqz v4, :cond_19a

    .line 2667
    const/16 v16, 0x0

    .line 2669
    .local v16, pipes:[Landroid/os/ParcelFileDescriptor;
    :try_start_2d
    invoke-static {}, Landroid/os/ParcelFileDescriptor;->createPipe()[Landroid/os/ParcelFileDescriptor;

    move-result-object v16

    .line 2671
    move-object/from16 v0, p1

    iget-object v9, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    .line 2672
    .local v9, app:Landroid/content/pm/ApplicationInfo;
    move-object/from16 v0, p1

    iget-object v2, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    const-string v3, "com.android.sharedstoragebackup"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    .line 2673
    .local v14, isSharedStorage:Z
    move-object/from16 v0, p0

    iget-boolean v2, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeApks:Z

    if-eqz v2, :cond_c5

    if-nez v14, :cond_c5

    iget v2, v9, Landroid/content/pm/ApplicationInfo;->flags:I

    const/high16 v3, 0x2000

    and-int/2addr v2, v3

    if-nez v2, :cond_c5

    iget v2, v9, Landroid/content/pm/ApplicationInfo;->flags:I

    and-int/lit8 v2, v2, 0x1

    if-eqz v2, :cond_5a

    iget v2, v9, Landroid/content/pm/ApplicationInfo;->flags:I

    and-int/lit16 v2, v2, 0x80

    if-eqz v2, :cond_c5

    :cond_5a
    const/4 v7, 0x1

    .line 2679
    .local v7, sendApk:Z
    :goto_5b
    if-eqz v14, :cond_c7

    const-string v2, "Shared storage"

    :goto_5f
    move-object/from16 v0, p0

    invoke-virtual {v0, v2}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendOnBackupPackage(Ljava/lang/String;)V

    .line 2681
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    invoke-virtual {v2}, Lcom/android/server/BackupManagerService;->generateToken()I

    move-result v6

    .line 2682
    .local v6, token:I
    new-instance v1, Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;

    const/4 v2, 0x1

    aget-object v5, v16, v2

    if-nez v14, :cond_cc

    const/4 v8, 0x1

    :goto_74
    move-object/from16 v2, p0

    move-object/from16 v3, p1

    invoke-direct/range {v1 .. v8}, Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;-><init>(Lcom/android/server/BackupManagerService$PerformFullBackupTask;Landroid/content/pm/PackageInfo;Landroid/app/IBackupAgent;Landroid/os/ParcelFileDescriptor;IZZ)V

    .line 2684
    .local v1, runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    const/4 v2, 0x1

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->close()V

    .line 2685
    const/4 v2, 0x1

    const/4 v3, 0x0

    aput-object v3, v16, v2

    .line 2686
    new-instance v18, Ljava/lang/Thread;

    move-object/from16 v0, v18

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 2687
    .local v18, t:Ljava/lang/Thread;
    invoke-virtual/range {v18 .. v18}, Ljava/lang/Thread;->start()V
    :try_end_8f
    .catchall {:try_start_2d .. :try_end_8f} :catchall_17d
    .catch Ljava/io/IOException; {:try_start_2d .. :try_end_8f} :catch_13b

    .line 2691
    :try_start_8f
    new-instance v17, Ljava/io/FileInputStream;

    const/4 v2, 0x0

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v2

    move-object/from16 v0, v17

    invoke-direct {v0, v2}, Ljava/io/FileInputStream;-><init>(Ljava/io/FileDescriptor;)V

    .line 2692
    .local v17, raw:Ljava/io/FileInputStream;
    new-instance v13, Ljava/io/DataInputStream;

    move-object/from16 v0, v17

    invoke-direct {v13, v0}, Ljava/io/DataInputStream;-><init>(Ljava/io/InputStream;)V

    .line 2694
    .local v13, in:Ljava/io/DataInputStream;
    const/16 v2, 0x4000

    new-array v10, v2, [B

    .line 2696
    .local v10, buffer:[B
    :cond_a8
    invoke-virtual {v13}, Ljava/io/DataInputStream;->readInt()I

    move-result v11

    .local v11, chunkTotal:I
    if-lez v11, :cond_d9

    .line 2697
    :goto_ae
    if-lez v11, :cond_a8

    .line 2698
    array-length v2, v10

    if-le v11, v2, :cond_ce

    array-length v0, v10

    move/from16 v19, v0

    .line 2700
    .local v19, toRead:I
    :goto_b6
    const/4 v2, 0x0

    move/from16 v0, v19

    invoke-virtual {v13, v10, v2, v0}, Ljava/io/DataInputStream;->read([BII)I

    move-result v15

    .line 2701
    .local v15, nRead:I
    const/4 v2, 0x0

    move-object/from16 v0, p2

    invoke-virtual {v0, v10, v2, v15}, Ljava/io/OutputStream;->write([BII)V
    :try_end_c3
    .catchall {:try_start_8f .. :try_end_c3} :catchall_17d
    .catch Ljava/io/IOException; {:try_start_8f .. :try_end_c3} :catch_d1

    .line 2702
    sub-int/2addr v11, v15

    .line 2703
    goto :goto_ae

    .line 2673
    .end local v1           #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .end local v6           #token:I
    .end local v7           #sendApk:Z
    .end local v10           #buffer:[B
    .end local v11           #chunkTotal:I
    .end local v13           #in:Ljava/io/DataInputStream;
    .end local v15           #nRead:I
    .end local v17           #raw:Ljava/io/FileInputStream;
    .end local v18           #t:Ljava/lang/Thread;
    .end local v19           #toRead:I
    :cond_c5
    const/4 v7, 0x0

    goto :goto_5b

    .line 2679
    .restart local v7       #sendApk:Z
    :cond_c7
    :try_start_c7
    move-object/from16 v0, p1

    iget-object v2, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    goto :goto_5f

    .line 2682
    .restart local v6       #token:I
    :cond_cc
    const/4 v8, 0x0

    goto :goto_74

    .restart local v1       #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .restart local v10       #buffer:[B
    .restart local v11       #chunkTotal:I
    .restart local v13       #in:Ljava/io/DataInputStream;
    .restart local v17       #raw:Ljava/io/FileInputStream;
    .restart local v18       #t:Ljava/lang/Thread;
    :cond_ce
    move/from16 v19, v11

    .line 2698
    goto :goto_b6

    .line 2705
    .end local v10           #buffer:[B
    .end local v11           #chunkTotal:I
    .end local v13           #in:Ljava/io/DataInputStream;
    .end local v17           #raw:Ljava/io/FileInputStream;
    :catch_d1
    move-exception v12

    .line 2706
    .local v12, e:Ljava/io/IOException;
    const-string v2, "BackupManagerService"

    const-string v3, "Caught exception reading from agent"

    invoke-static {v2, v3, v12}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 2709
    .end local v12           #e:Ljava/io/IOException;
    :cond_d9
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    invoke-virtual {v2, v6}, Lcom/android/server/BackupManagerService;->waitUntilOperationComplete(I)Z

    move-result v2

    if-nez v2, :cond_11e

    .line 2710
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Full backup failed on package "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p1

    iget-object v5, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_ff
    .catchall {:try_start_c7 .. :try_end_ff} :catchall_17d
    .catch Ljava/io/IOException; {:try_start_c7 .. :try_end_ff} :catch_13b

    .line 2720
    :goto_ff
    :try_start_ff
    invoke-virtual/range {p2 .. p2}, Ljava/io/OutputStream;->flush()V

    .line 2721
    if-eqz v16, :cond_11a

    .line 2722
    const/4 v2, 0x0

    aget-object v2, v16, v2

    if-eqz v2, :cond_10f

    const/4 v2, 0x0

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->close()V

    .line 2723
    :cond_10f
    const/4 v2, 0x1

    aget-object v2, v16, v2

    if-eqz v2, :cond_11a

    const/4 v2, 0x1

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_11a
    .catch Ljava/io/IOException; {:try_start_ff .. :try_end_11a} :catch_1c1

    .line 2732
    .end local v1           #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .end local v6           #token:I
    .end local v7           #sendApk:Z
    .end local v9           #app:Landroid/content/pm/ApplicationInfo;
    .end local v14           #isSharedStorage:Z
    .end local v16           #pipes:[Landroid/os/ParcelFileDescriptor;
    .end local v18           #t:Ljava/lang/Thread;
    :cond_11a
    :goto_11a
    invoke-direct/range {p0 .. p1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2733
    return-void

    .line 2712
    .restart local v1       #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .restart local v6       #token:I
    .restart local v7       #sendApk:Z
    .restart local v9       #app:Landroid/content/pm/ApplicationInfo;
    .restart local v14       #isSharedStorage:Z
    .restart local v16       #pipes:[Landroid/os/ParcelFileDescriptor;
    .restart local v18       #t:Ljava/lang/Thread;
    :cond_11e
    :try_start_11e
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Full package backup success: "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p1

    iget-object v5, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_13a
    .catchall {:try_start_11e .. :try_end_13a} :catchall_17d
    .catch Ljava/io/IOException; {:try_start_11e .. :try_end_13a} :catch_13b

    goto :goto_ff

    .line 2715
    .end local v1           #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .end local v6           #token:I
    .end local v7           #sendApk:Z
    .end local v9           #app:Landroid/content/pm/ApplicationInfo;
    .end local v14           #isSharedStorage:Z
    .end local v18           #t:Ljava/lang/Thread;
    :catch_13b
    move-exception v12

    .line 2716
    .restart local v12       #e:Ljava/io/IOException;
    :try_start_13c
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Error backing up "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p1

    iget-object v5, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3, v12}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_158
    .catchall {:try_start_13c .. :try_end_158} :catchall_17d

    .line 2720
    :try_start_158
    invoke-virtual/range {p2 .. p2}, Ljava/io/OutputStream;->flush()V

    .line 2721
    if-eqz v16, :cond_11a

    .line 2722
    const/4 v2, 0x0

    aget-object v2, v16, v2

    if-eqz v2, :cond_168

    const/4 v2, 0x0

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->close()V

    .line 2723
    :cond_168
    const/4 v2, 0x1

    aget-object v2, v16, v2

    if-eqz v2, :cond_11a

    const/4 v2, 0x1

    aget-object v2, v16, v2

    invoke-virtual {v2}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_173
    .catch Ljava/io/IOException; {:try_start_158 .. :try_end_173} :catch_174

    goto :goto_11a

    .line 2725
    :catch_174
    move-exception v12

    .line 2726
    const-string v2, "BackupManagerService"

    const-string v3, "Error bringing down backup stack"

    :goto_179
    invoke-static {v2, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_11a

    .line 2718
    .end local v12           #e:Ljava/io/IOException;
    :catchall_17d
    move-exception v2

    .line 2720
    :try_start_17e
    invoke-virtual/range {p2 .. p2}, Ljava/io/OutputStream;->flush()V

    .line 2721
    if-eqz v16, :cond_199

    .line 2722
    const/4 v3, 0x0

    aget-object v3, v16, v3

    if-eqz v3, :cond_18e

    const/4 v3, 0x0

    aget-object v3, v16, v3

    invoke-virtual {v3}, Landroid/os/ParcelFileDescriptor;->close()V

    .line 2723
    :cond_18e
    const/4 v3, 0x1

    aget-object v3, v16, v3

    if-eqz v3, :cond_199

    const/4 v3, 0x1

    aget-object v3, v16, v3

    invoke-virtual {v3}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_199
    .catch Ljava/io/IOException; {:try_start_17e .. :try_end_199} :catch_1b8

    .line 2718
    :cond_199
    :goto_199
    throw v2

    .line 2730
    .end local v16           #pipes:[Landroid/os/ParcelFileDescriptor;
    :cond_19a
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Unable to bind to full agent for "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p1

    iget-object v5, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_11a

    .line 2725
    .restart local v16       #pipes:[Landroid/os/ParcelFileDescriptor;
    :catch_1b8
    move-exception v12

    .line 2726
    .restart local v12       #e:Ljava/io/IOException;
    const-string v3, "BackupManagerService"

    const-string v5, "Error bringing down backup stack"

    invoke-static {v3, v5}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_199

    .line 2725
    .end local v12           #e:Ljava/io/IOException;
    .restart local v1       #runner:Lcom/android/server/BackupManagerService$PerformFullBackupTask$FullBackupRunner;
    .restart local v6       #token:I
    .restart local v7       #sendApk:Z
    .restart local v9       #app:Landroid/content/pm/ApplicationInfo;
    .restart local v14       #isSharedStorage:Z
    .restart local v18       #t:Ljava/lang/Thread;
    :catch_1c1
    move-exception v12

    .line 2726
    .restart local v12       #e:Ljava/io/IOException;
    const-string v2, "BackupManagerService"

    const-string v3, "Error bringing down backup stack"

    goto :goto_179
.end method

.method private emitAesBackupHeader(Ljava/lang/StringBuilder;Ljava/io/OutputStream;)Ljava/io/OutputStream;
    .registers 23
    .parameter "headerbuf"
    .parameter "ofstream"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 2592
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    const/16 v18, 0x200

    #calls: Lcom/android/server/BackupManagerService;->randomBytes(I)[B
    invoke-static/range {v17 .. v18}, Lcom/android/server/BackupManagerService;->access$1100(Lcom/android/server/BackupManagerService;I)[B

    move-result-object v15

    .line 2593
    .local v15, newUserSalt:[B
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mEncryptPassword:Ljava/lang/String;

    move-object/from16 v18, v0

    const/16 v19, 0x2710

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    move/from16 v2, v19

    #calls: Lcom/android/server/BackupManagerService;->buildPasswordKey(Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;
    invoke-static {v0, v1, v15, v2}, Lcom/android/server/BackupManagerService;->access$1200(Lcom/android/server/BackupManagerService;Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;

    move-result-object v16

    .line 2597
    .local v16, userKey:Ljavax/crypto/SecretKey;
    const/16 v17, 0x20

    move/from16 v0, v17

    new-array v11, v0, [B

    .line 2598
    .local v11, masterPw:[B
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    #getter for: Lcom/android/server/BackupManagerService;->mRng:Ljava/security/SecureRandom;
    invoke-static/range {v17 .. v17}, Lcom/android/server/BackupManagerService;->access$1300(Lcom/android/server/BackupManagerService;)Ljava/security/SecureRandom;

    move-result-object v17

    move-object/from16 v0, v17

    invoke-virtual {v0, v11}, Ljava/security/SecureRandom;->nextBytes([B)V

    .line 2599
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    const/16 v18, 0x200

    #calls: Lcom/android/server/BackupManagerService;->randomBytes(I)[B
    invoke-static/range {v17 .. v18}, Lcom/android/server/BackupManagerService;->access$1100(Lcom/android/server/BackupManagerService;I)[B

    move-result-object v7

    .line 2602
    .local v7, checksumSalt:[B
    const-string v17, "AES/CBC/PKCS5Padding"

    invoke-static/range {v17 .. v17}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v5

    .line 2603
    .local v5, c:Ljavax/crypto/Cipher;
    new-instance v10, Ljavax/crypto/spec/SecretKeySpec;

    const-string v17, "AES"

    move-object/from16 v0, v17

    invoke-direct {v10, v11, v0}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 2604
    .local v10, masterKeySpec:Ljavax/crypto/spec/SecretKeySpec;
    const/16 v17, 0x1

    move/from16 v0, v17

    invoke-virtual {v5, v0, v10}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 2605
    new-instance v9, Ljavax/crypto/CipherOutputStream;

    move-object/from16 v0, p2

    invoke-direct {v9, v0, v5}, Ljavax/crypto/CipherOutputStream;-><init>(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V

    .line 2608
    .local v9, finalOutput:Ljava/io/OutputStream;
    const-string v17, "AES-256"

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2609
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2611
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    #calls: Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;
    invoke-static {v0, v15}, Lcom/android/server/BackupManagerService;->access$1400(Lcom/android/server/BackupManagerService;[B)Ljava/lang/String;

    move-result-object v17

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2612
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2614
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    #calls: Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;
    invoke-static {v0, v7}, Lcom/android/server/BackupManagerService;->access$1400(Lcom/android/server/BackupManagerService;[B)Ljava/lang/String;

    move-result-object v17

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2615
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2617
    const/16 v17, 0x2710

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 2618
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2621
    const-string v17, "AES/CBC/PKCS5Padding"

    invoke-static/range {v17 .. v17}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v13

    .line 2622
    .local v13, mkC:Ljavax/crypto/Cipher;
    const/16 v17, 0x1

    move/from16 v0, v17

    move-object/from16 v1, v16

    invoke-virtual {v13, v0, v1}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 2624
    invoke-virtual {v13}, Ljavax/crypto/Cipher;->getIV()[B

    move-result-object v3

    .line 2625
    .local v3, IV:[B
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    #calls: Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;
    invoke-static {v0, v3}, Lcom/android/server/BackupManagerService;->access$1400(Lcom/android/server/BackupManagerService;[B)Ljava/lang/String;

    move-result-object v17

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2626
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2638
    invoke-virtual {v5}, Ljavax/crypto/Cipher;->getIV()[B

    move-result-object v3

    .line 2639
    invoke-virtual {v10}, Ljavax/crypto/spec/SecretKeySpec;->getEncoded()[B

    move-result-object v12

    .line 2640
    .local v12, mk:[B
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    invoke-virtual {v10}, Ljavax/crypto/spec/SecretKeySpec;->getEncoded()[B

    move-result-object v18

    const/16 v19, 0x2710

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    move/from16 v2, v19

    #calls: Lcom/android/server/BackupManagerService;->makeKeyChecksum([B[BI)[B
    invoke-static {v0, v1, v7, v2}, Lcom/android/server/BackupManagerService;->access$1500(Lcom/android/server/BackupManagerService;[B[BI)[B

    move-result-object v6

    .line 2643
    .local v6, checksum:[B
    new-instance v4, Ljava/io/ByteArrayOutputStream;

    array-length v0, v3

    move/from16 v17, v0

    array-length v0, v12

    move/from16 v18, v0

    add-int v17, v17, v18

    array-length v0, v6

    move/from16 v18, v0

    add-int v17, v17, v18

    add-int/lit8 v17, v17, 0x3

    move/from16 v0, v17

    invoke-direct {v4, v0}, Ljava/io/ByteArrayOutputStream;-><init>(I)V

    .line 2645
    .local v4, blob:Ljava/io/ByteArrayOutputStream;
    new-instance v14, Ljava/io/DataOutputStream;

    invoke-direct {v14, v4}, Ljava/io/DataOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 2646
    .local v14, mkOut:Ljava/io/DataOutputStream;
    array-length v0, v3

    move/from16 v17, v0

    move/from16 v0, v17

    invoke-virtual {v14, v0}, Ljava/io/DataOutputStream;->writeByte(I)V

    .line 2647
    invoke-virtual {v14, v3}, Ljava/io/DataOutputStream;->write([B)V

    .line 2648
    array-length v0, v12

    move/from16 v17, v0

    move/from16 v0, v17

    invoke-virtual {v14, v0}, Ljava/io/DataOutputStream;->writeByte(I)V

    .line 2649
    invoke-virtual {v14, v12}, Ljava/io/DataOutputStream;->write([B)V

    .line 2650
    array-length v0, v6

    move/from16 v17, v0

    move/from16 v0, v17

    invoke-virtual {v14, v0}, Ljava/io/DataOutputStream;->writeByte(I)V

    .line 2651
    invoke-virtual {v14, v6}, Ljava/io/DataOutputStream;->write([B)V

    .line 2652
    invoke-virtual {v14}, Ljava/io/DataOutputStream;->flush()V

    .line 2653
    invoke-virtual {v4}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v17

    move-object/from16 v0, v17

    invoke-virtual {v13, v0}, Ljavax/crypto/Cipher;->doFinal([B)[B

    move-result-object v8

    .line 2654
    .local v8, encryptedMk:[B
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    #calls: Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;
    invoke-static {v0, v8}, Lcom/android/server/BackupManagerService;->access$1400(Lcom/android/server/BackupManagerService;[B)Ljava/lang/String;

    move-result-object v17

    move-object/from16 v0, p1

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2655
    const/16 v17, 0xa

    move-object/from16 v0, p1

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2657
    return-object v9
.end method

.method private finalizeBackup(Ljava/io/OutputStream;)V
    .registers 6
    .parameter "out"

    .prologue
    .line 2761
    const/16 v2, 0x400

    :try_start_2
    new-array v1, v2, [B

    .line 2762
    .local v1, eof:[B
    invoke-virtual {p1, v1}, Ljava/io/OutputStream;->write([B)V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_7} :catch_8

    .line 2766
    .end local v1           #eof:[B
    :goto_7
    return-void

    .line 2763
    :catch_8
    move-exception v0

    .line 2764
    .local v0, e:Ljava/io/IOException;
    const-string v2, "BackupManagerService"

    const-string v3, "Error attempting to finalize backup stream"

    invoke-static {v2, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_7
.end method

.method private tearDown(Landroid/content/pm/PackageInfo;)V
    .registers 7
    .parameter "pkg"

    .prologue
    .line 2808
    if-eqz p1, :cond_28

    .line 2809
    iget-object v0, p1, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    .line 2810
    .local v0, app:Landroid/content/pm/ApplicationInfo;
    if-eqz v0, :cond_28

    .line 2813
    :try_start_6
    iget-object v2, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;
    invoke-static {v2}, Lcom/android/server/BackupManagerService;->access$800(Lcom/android/server/BackupManagerService;)Landroid/app/IActivityManager;

    move-result-object v2

    invoke-interface {v2, v0}, Landroid/app/IActivityManager;->unbindBackupAgent(Landroid/content/pm/ApplicationInfo;)V

    .line 2816
    iget v2, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    const/16 v3, 0x3e8

    if-eq v2, v3, :cond_28

    iget v2, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    const/16 v3, 0x3e9

    if-eq v2, v3, :cond_28

    .line 2819
    iget-object v2, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;
    invoke-static {v2}, Lcom/android/server/BackupManagerService;->access$800(Lcom/android/server/BackupManagerService;)Landroid/app/IActivityManager;

    move-result-object v2

    iget-object v3, v0, Landroid/content/pm/ApplicationInfo;->processName:Ljava/lang/String;

    iget v4, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-interface {v2, v3, v4}, Landroid/app/IActivityManager;->killApplicationProcess(Ljava/lang/String;I)V
    :try_end_28
    .catch Landroid/os/RemoteException; {:try_start_6 .. :try_end_28} :catch_29

    .line 2828
    .end local v0           #app:Landroid/content/pm/ApplicationInfo;
    :cond_28
    :goto_28
    return-void

    .line 2823
    .restart local v0       #app:Landroid/content/pm/ApplicationInfo;
    :catch_29
    move-exception v1

    .line 2824
    .local v1, e:Landroid/os/RemoteException;
    const-string v2, "BackupManagerService"

    const-string v3, "Lost app trying to shut down"

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_28
.end method

.method private writeApkToBackup(Landroid/content/pm/PackageInfo;Landroid/app/backup/BackupDataOutput;)V
    .registers 21
    .parameter "pkg"
    .parameter "output"

    .prologue
    .line 2737
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v5, v1, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    .line 2738
    .local v5, appSourceDir:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->getParent()Ljava/lang/String;

    move-result-object v4

    .line 2739
    .local v4, apkDir:Ljava/lang/String;
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    const-string v2, "a"

    const/4 v3, 0x0

    move-object/from16 v6, p2

    invoke-static/range {v1 .. v6}, Landroid/app/backup/FullBackup;->backupToTar(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/backup/BackupDataOutput;)I

    .line 2744
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-static {v1}, Landroid/os/Environment;->getExternalStorageAppObbDirectory(Ljava/lang/String;)Ljava/io/File;

    move-result-object v16

    .line 2745
    .local v16, obbDir:Ljava/io/File;
    if-eqz v16, :cond_4a

    .line 2747
    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v17

    .line 2748
    .local v17, obbFiles:[Ljava/io/File;
    if-eqz v17, :cond_4a

    .line 2749
    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    .line 2750
    .local v9, obbDirName:Ljava/lang/String;
    move-object/from16 v12, v17

    .local v12, arr$:[Ljava/io/File;
    array-length v14, v12

    .local v14, len$:I
    const/4 v13, 0x0

    .local v13, i$:I
    :goto_33
    if-ge v13, v14, :cond_4a

    aget-object v15, v12, v13

    .line 2751
    .local v15, obb:Ljava/io/File;
    move-object/from16 v0, p1

    iget-object v6, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    const-string v7, "obb"

    const/4 v8, 0x0

    invoke-virtual {v15}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v10

    move-object/from16 v11, p2

    invoke-static/range {v6 .. v11}, Landroid/app/backup/FullBackup;->backupToTar(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/backup/BackupDataOutput;)I

    .line 2750
    add-int/lit8 v13, v13, 0x1

    goto :goto_33

    .line 2756
    .end local v9           #obbDirName:Ljava/lang/String;
    .end local v12           #arr$:[Ljava/io/File;
    .end local v13           #i$:I
    .end local v14           #len$:I
    .end local v15           #obb:Ljava/io/File;
    .end local v17           #obbFiles:[Ljava/io/File;
    :cond_4a
    return-void
.end method

.method private writeAppManifest(Landroid/content/pm/PackageInfo;Ljava/io/File;Z)V
    .registers 14
    .parameter "pkg"
    .parameter "manifestFile"
    .parameter "withApk"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 2781
    new-instance v1, Ljava/lang/StringBuilder;

    const/16 v8, 0x1000

    invoke-direct {v1, v8}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 2782
    .local v1, builder:Ljava/lang/StringBuilder;
    new-instance v6, Landroid/util/StringBuilderPrinter;

    invoke-direct {v6, v1}, Landroid/util/StringBuilderPrinter;-><init>(Ljava/lang/StringBuilder;)V

    .line 2784
    .local v6, printer:Landroid/util/StringBuilderPrinter;
    const/4 v8, 0x1

    invoke-static {v8}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2785
    iget-object v8, p1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2786
    iget v8, p1, Landroid/content/pm/PackageInfo;->versionCode:I

    invoke-static {v8}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2787
    sget v8, Landroid/os/Build$VERSION;->SDK_INT:I

    invoke-static {v8}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2789
    iget-object v8, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    #getter for: Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;
    invoke-static {v8}, Lcom/android/server/BackupManagerService;->access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;

    move-result-object v8

    iget-object v9, p1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v8, v9}, Landroid/content/pm/PackageManager;->getInstallerPackageName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 2790
    .local v3, installerName:Ljava/lang/String;
    if-eqz v3, :cond_60

    .end local v3           #installerName:Ljava/lang/String;
    :goto_39
    invoke-virtual {v6, v3}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2792
    if-eqz p3, :cond_63

    const-string v8, "1"

    :goto_40
    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2793
    iget-object v8, p1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    if-nez v8, :cond_66

    .line 2794
    const-string v8, "0"

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2802
    :cond_4c
    new-instance v5, Ljava/io/FileOutputStream;

    invoke-direct {v5, p2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 2803
    .local v5, outstream:Ljava/io/FileOutputStream;
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/String;->getBytes()[B

    move-result-object v8

    invoke-virtual {v5, v8}, Ljava/io/FileOutputStream;->write([B)V

    .line 2804
    invoke-virtual {v5}, Ljava/io/FileOutputStream;->close()V

    .line 2805
    return-void

    .line 2790
    .end local v5           #outstream:Ljava/io/FileOutputStream;
    .restart local v3       #installerName:Ljava/lang/String;
    :cond_60
    const-string v3, ""

    goto :goto_39

    .line 2792
    .end local v3           #installerName:Ljava/lang/String;
    :cond_63
    const-string v8, "0"

    goto :goto_40

    .line 2796
    :cond_66
    iget-object v8, p1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    array-length v8, v8

    invoke-static {v8}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2797
    iget-object v0, p1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    .local v0, arr$:[Landroid/content/pm/Signature;
    array-length v4, v0

    .local v4, len$:I
    const/4 v2, 0x0

    .local v2, i$:I
    :goto_74
    if-ge v2, v4, :cond_4c

    aget-object v7, v0, v2

    .line 2798
    .local v7, sig:Landroid/content/pm/Signature;
    invoke-virtual {v7}, Landroid/content/pm/Signature;->toCharsString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Landroid/util/StringBuilderPrinter;->println(Ljava/lang/String;)V

    .line 2797
    add-int/lit8 v2, v2, 0x1

    goto :goto_74
.end method


# virtual methods
.method public run()V
    .registers 26

    .prologue
    .line 2408
    new-instance v18, Ljava/util/ArrayList;

    invoke-direct/range {v18 .. v18}, Ljava/util/ArrayList;-><init>()V

    .line 2410
    .local v18, packagesToBackup:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/PackageInfo;>;"
    const-string v21, "BackupManagerService"

    const-string v22, "--- Performing full-dataset backup ---"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2411
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendStartBackup()V

    .line 2414
    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mAllApps:Z

    move/from16 v21, v0

    if-eqz v21, :cond_59

    .line 2415
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    #getter for: Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;
    invoke-static/range {v21 .. v21}, Lcom/android/server/BackupManagerService;->access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;

    move-result-object v21

    const/16 v22, 0x40

    invoke-virtual/range {v21 .. v22}, Landroid/content/pm/PackageManager;->getInstalledPackages(I)Ljava/util/List;

    move-result-object v18

    .line 2418
    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeSystem:Z

    move/from16 v21, v0

    if-nez v21, :cond_59

    .line 2419
    const/4 v13, 0x0

    .local v13, i:I
    :goto_30
    invoke-interface/range {v18 .. v18}, Ljava/util/List;->size()I

    move-result v21

    move/from16 v0, v21

    if-ge v13, v0, :cond_59

    .line 2420
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v19

    check-cast v19, Landroid/content/pm/PackageInfo;

    .line 2421
    .local v19, pkg:Landroid/content/pm/PackageInfo;
    move-object/from16 v0, v19

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget v0, v0, Landroid/content/pm/ApplicationInfo;->flags:I

    move/from16 v21, v0

    and-int/lit8 v21, v21, 0x1

    if-eqz v21, :cond_56

    .line 2422
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    goto :goto_30

    .line 2424
    :cond_56
    add-int/lit8 v13, v13, 0x1

    goto :goto_30

    .line 2433
    .end local v13           #i:I
    .end local v19           #pkg:Landroid/content/pm/PackageInfo;
    :cond_59
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mPackages:[Ljava/lang/String;

    move-object/from16 v21, v0

    if-eqz v21, :cond_af

    .line 2434
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mPackages:[Ljava/lang/String;

    .local v4, arr$:[Ljava/lang/String;
    array-length v15, v4

    .local v15, len$:I
    const/4 v14, 0x0

    .local v14, i$:I
    :goto_67
    if-ge v14, v15, :cond_af

    aget-object v20, v4, v14

    .line 2436
    .local v20, pkgName:Ljava/lang/String;
    :try_start_6b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    #getter for: Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;
    invoke-static/range {v21 .. v21}, Lcom/android/server/BackupManagerService;->access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;

    move-result-object v21

    const/16 v22, 0x40

    move-object/from16 v0, v21

    move-object/from16 v1, v20

    move/from16 v2, v22

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v21

    move-object/from16 v0, v18

    move-object/from16 v1, v21

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_88
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_6b .. :try_end_88} :catch_8b

    .line 2434
    :goto_88
    add-int/lit8 v14, v14, 0x1

    goto :goto_67

    .line 2438
    :catch_8b
    move-exception v7

    .line 2439
    .local v7, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v21, "BackupManagerService"

    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v23, "Unknown package "

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v23, ", skipping"

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_88

    .line 2447
    .end local v4           #arr$:[Ljava/lang/String;
    .end local v7           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v14           #i$:I
    .end local v15           #len$:I
    .end local v20           #pkgName:Ljava/lang/String;
    :cond_af
    const/4 v13, 0x0

    .restart local v13       #i:I
    :goto_b0
    invoke-interface/range {v18 .. v18}, Ljava/util/List;->size()I

    move-result v21

    move/from16 v0, v21

    if-ge v13, v0, :cond_ea

    .line 2448
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v19

    check-cast v19, Landroid/content/pm/PackageInfo;

    .line 2449
    .restart local v19       #pkg:Landroid/content/pm/PackageInfo;
    move-object/from16 v0, v19

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget v0, v0, Landroid/content/pm/ApplicationInfo;->flags:I

    move/from16 v21, v0

    const v22, 0x8000

    and-int v21, v21, v22

    if-eqz v21, :cond_e1

    move-object/from16 v0, v19

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    move-object/from16 v21, v0

    const-string v22, "com.android.sharedstoragebackup"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v21

    if-eqz v21, :cond_e7

    .line 2451
    :cond_e1
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    goto :goto_b0

    .line 2453
    :cond_e7
    add-int/lit8 v13, v13, 0x1

    goto :goto_b0

    .line 2459
    .end local v19           #pkg:Landroid/content/pm/PackageInfo;
    :cond_ea
    const/4 v13, 0x0

    :goto_eb
    invoke-interface/range {v18 .. v18}, Ljava/util/List;->size()I

    move-result v21

    move/from16 v0, v21

    if-ge v13, v0, :cond_126

    .line 2460
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v19

    check-cast v19, Landroid/content/pm/PackageInfo;

    .line 2461
    .restart local v19       #pkg:Landroid/content/pm/PackageInfo;
    move-object/from16 v0, v19

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget v0, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    move/from16 v21, v0

    const/16 v22, 0x2710

    move/from16 v0, v21

    move/from16 v1, v22

    if-ge v0, v1, :cond_123

    move-object/from16 v0, v19

    iget-object v0, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Landroid/content/pm/ApplicationInfo;->backupAgentName:Ljava/lang/String;

    move-object/from16 v21, v0

    if-nez v21, :cond_123

    .line 2466
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    goto :goto_eb

    .line 2468
    :cond_123
    add-int/lit8 v13, v13, 0x1

    goto :goto_eb

    .line 2472
    .end local v19           #pkg:Landroid/content/pm/PackageInfo;
    :cond_126
    new-instance v16, Ljava/io/FileOutputStream;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v21

    move-object/from16 v0, v16

    move-object/from16 v1, v21

    invoke-direct {v0, v1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/FileDescriptor;)V

    .line 2473
    .local v16, ofstream:Ljava/io/FileOutputStream;
    const/16 v17, 0x0

    .line 2475
    .local v17, out:Ljava/io/OutputStream;
    const/16 v19, 0x0

    .line 2477
    .restart local v19       #pkg:Landroid/content/pm/PackageInfo;
    :try_start_13d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mEncryptPassword:Ljava/lang/String;

    move-object/from16 v21, v0

    if-eqz v21, :cond_1e6

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mEncryptPassword:Ljava/lang/String;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/String;->length()I

    move-result v21

    if-lez v21, :cond_1e6

    const/4 v8, 0x1

    .line 2478
    .local v8, encrypting:Z
    :goto_152
    const/4 v5, 0x1

    .line 2479
    .local v5, compressing:Z
    move-object/from16 v9, v16

    .line 2483
    .local v9, finalOutput:Ljava/io/OutputStream;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Lcom/android/server/BackupManagerService;->hasBackupPassword()Z

    move-result v21

    if-eqz v21, :cond_1e9

    .line 2484
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mCurrentPassword:Ljava/lang/String;

    move-object/from16 v22, v0

    const/16 v23, 0x2710

    invoke-virtual/range {v21 .. v23}, Lcom/android/server/BackupManagerService;->passwordMatchesSaved(Ljava/lang/String;I)Z

    move-result v21

    if-nez v21, :cond_1e9

    .line 2485
    const-string v21, "BackupManagerService"

    const-string v22, "Backup password mismatch; aborting"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_17c
    .catchall {:try_start_13d .. :try_end_17c} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_13d .. :try_end_17c} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_13d .. :try_end_17c} :catch_3e0

    .line 2569
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_186

    :try_start_185
    throw v17

    .line 2572
    :cond_186
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_18f
    .catch Ljava/io/IOException; {:try_start_185 .. :try_end_18f} :catch_4e9

    .line 2576
    :goto_18f
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_19c
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_1ac
    .catchall {:try_start_19c .. :try_end_1ac} :catchall_4d1

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_1b3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    const/16 v23, 0x1

    move-object/from16 v0, v21

    move/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_1cc
    .catchall {:try_start_1b3 .. :try_end_1cc} :catchall_4d4

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v21, "BackupManagerService"

    const-string v22, "Full backup pass complete."

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v21, v0

    .end local v5           #compressing:Z
    .end local v8           #encrypting:Z
    .end local v9           #finalOutput:Ljava/io/OutputStream;
    :goto_1e2
    invoke-virtual/range {v21 .. v21}, Landroid/os/PowerManager$WakeLock;->release()V

    .line 2587
    return-void

    .line 2477
    :cond_1e6
    const/4 v8, 0x0

    goto/16 :goto_152

    .line 2515
    .restart local v5       #compressing:Z
    .restart local v8       #encrypting:Z
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    :cond_1e9
    :try_start_1e9
    new-instance v12, Ljava/lang/StringBuilder;

    const/16 v21, 0x400

    move/from16 v0, v21

    invoke-direct {v12, v0}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 2517
    .local v12, headerbuf:Ljava/lang/StringBuilder;
    const-string v21, "ANDROID BACKUP\n"

    move-object/from16 v0, v21

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 2518
    const/16 v21, 0x1

    move/from16 v0, v21

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 2519
    if-eqz v5, :cond_273

    const-string v21, "\n1\n"

    :goto_204
    move-object/from16 v0, v21

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_209
    .catchall {:try_start_1e9 .. :try_end_209} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_1e9 .. :try_end_209} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_1e9 .. :try_end_209} :catch_3e0

    .line 2523
    if-eqz v8, :cond_276

    .line 2524
    :try_start_20b
    move-object/from16 v0, p0

    invoke-direct {v0, v12, v9}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->emitAesBackupHeader(Ljava/lang/StringBuilder;Ljava/io/OutputStream;)Ljava/io/OutputStream;
    :try_end_210
    .catchall {:try_start_20b .. :try_end_210} :catchall_456
    .catch Ljava/lang/Exception; {:try_start_20b .. :try_end_210} :catch_27f
    .catch Landroid/os/RemoteException; {:try_start_20b .. :try_end_210} :catch_2fd

    move-result-object v9

    move-object v10, v9

    .line 2529
    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .local v10, finalOutput:Ljava/io/OutputStream;
    :goto_212
    :try_start_212
    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    const-string v22, "UTF-8"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v11

    .line 2530
    .local v11, header:[B
    move-object/from16 v0, v16

    invoke-virtual {v0, v11}, Ljava/io/FileOutputStream;->write([B)V

    .line 2533
    if-eqz v5, :cond_4f9

    .line 2534
    new-instance v6, Ljava/util/zip/Deflater;

    const/16 v21, 0x9

    move/from16 v0, v21

    invoke-direct {v6, v0}, Ljava/util/zip/Deflater;-><init>(I)V

    .line 2535
    .local v6, deflater:Ljava/util/zip/Deflater;
    new-instance v9, Ljava/util/zip/DeflaterOutputStream;

    const/16 v21, 0x1

    move/from16 v0, v21

    invoke-direct {v9, v10, v6, v0}, Ljava/util/zip/DeflaterOutputStream;-><init>(Ljava/io/OutputStream;Ljava/util/zip/Deflater;Z)V
    :try_end_235
    .catchall {:try_start_212 .. :try_end_235} :catchall_456
    .catch Ljava/lang/Exception; {:try_start_212 .. :try_end_235} :catch_4f5
    .catch Landroid/os/RemoteException; {:try_start_212 .. :try_end_235} :catch_2fd

    .line 2538
    .end local v6           #deflater:Ljava/util/zip/Deflater;
    .end local v10           #finalOutput:Ljava/io/OutputStream;
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    :goto_235
    move-object/from16 v17, v9

    .line 2546
    :try_start_237
    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mIncludeShared:Z

    move/from16 v21, v0
    :try_end_23d
    .catchall {:try_start_237 .. :try_end_23d} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_237 .. :try_end_23d} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_237 .. :try_end_23d} :catch_3e0

    if-eqz v21, :cond_254

    .line 2548
    :try_start_23f
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    #getter for: Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;
    invoke-static/range {v21 .. v21}, Lcom/android/server/BackupManagerService;->access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;

    move-result-object v21

    const-string v22, "com.android.sharedstoragebackup"

    const/16 v23, 0x0

    invoke-virtual/range {v21 .. v23}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v19

    .line 2549
    invoke-interface/range {v18 .. v19}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_254
    .catchall {:try_start_23f .. :try_end_254} :catchall_456
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_23f .. :try_end_254} :catch_2f3
    .catch Landroid/os/RemoteException; {:try_start_23f .. :try_end_254} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_23f .. :try_end_254} :catch_3e0

    .line 2556
    :cond_254
    :goto_254
    :try_start_254
    invoke-interface/range {v18 .. v18}, Ljava/util/List;->size()I

    move-result v3

    .line 2557
    .local v3, N:I
    const/4 v13, 0x0

    :goto_259
    if-ge v13, v3, :cond_36f

    .line 2558
    move-object/from16 v0, v18

    invoke-interface {v0, v13}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v21

    move-object/from16 v0, v21

    check-cast v0, Landroid/content/pm/PackageInfo;

    move-object/from16 v19, v0

    .line 2559
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    move-object/from16 v2, v17

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->backupOnePackage(Landroid/content/pm/PackageInfo;Ljava/io/OutputStream;)V

    .line 2557
    add-int/lit8 v13, v13, 0x1

    goto :goto_259

    .line 2519
    .end local v3           #N:I
    .end local v11           #header:[B
    :cond_273
    const-string v21, "\n0\n"
    :try_end_275
    .catchall {:try_start_254 .. :try_end_275} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_254 .. :try_end_275} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_254 .. :try_end_275} :catch_3e0

    goto :goto_204

    .line 2526
    :cond_276
    :try_start_276
    const-string v21, "none\n"

    move-object/from16 v0, v21

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_27d
    .catchall {:try_start_276 .. :try_end_27d} :catchall_456
    .catch Ljava/lang/Exception; {:try_start_276 .. :try_end_27d} :catch_27f
    .catch Landroid/os/RemoteException; {:try_start_276 .. :try_end_27d} :catch_2fd

    move-object v10, v9

    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .restart local v10       #finalOutput:Ljava/io/OutputStream;
    goto :goto_212

    .line 2539
    .end local v10           #finalOutput:Ljava/io/OutputStream;
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    :catch_27f
    move-exception v7

    .line 2541
    .local v7, e:Ljava/lang/Exception;
    :goto_280
    :try_start_280
    const-string v21, "BackupManagerService"

    const-string v22, "Unable to emit archive header"

    move-object/from16 v0, v21

    move-object/from16 v1, v22

    invoke-static {v0, v1, v7}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_28b
    .catchall {:try_start_280 .. :try_end_28b} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_280 .. :try_end_28b} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_280 .. :try_end_28b} :catch_3e0

    .line 2569
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_295

    :try_start_294
    throw v17

    .line 2572
    :cond_295
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_29e
    .catch Ljava/io/IOException; {:try_start_294 .. :try_end_29e} :catch_4e6

    .line 2576
    :goto_29e
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_2ab
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_2bb
    .catchall {:try_start_2ab .. :try_end_2bb} :catchall_4d7

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_2c2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    const/16 v23, 0x1

    move-object/from16 v0, v21

    move/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_2db
    .catchall {:try_start_2c2 .. :try_end_2db} :catchall_4da

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v21, "BackupManagerService"

    const-string v22, "Full backup pass complete."

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v21, v0

    goto/16 :goto_1e2

    .line 2550
    .end local v7           #e:Ljava/lang/Exception;
    .restart local v11       #header:[B
    :catch_2f3
    move-exception v7

    .line 2551
    .local v7, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    :try_start_2f4
    const-string v21, "BackupManagerService"

    const-string v22, "Unable to find shared-storage backup handler"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2fb
    .catchall {:try_start_2f4 .. :try_end_2fb} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_2f4 .. :try_end_2fb} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_2f4 .. :try_end_2fb} :catch_3e0

    goto/16 :goto_254

    .line 2564
    .end local v5           #compressing:Z
    .end local v7           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v8           #encrypting:Z
    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .end local v11           #header:[B
    .end local v12           #headerbuf:Ljava/lang/StringBuilder;
    :catch_2fd
    move-exception v7

    .line 2565
    .local v7, e:Landroid/os/RemoteException;
    :try_start_2fe
    const-string v21, "BackupManagerService"

    const-string v22, "App died during full backup"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_305
    .catchall {:try_start_2fe .. :try_end_305} :catchall_456

    .line 2569
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_311

    :try_start_30e
    invoke-virtual/range {v17 .. v17}, Ljava/io/OutputStream;->close()V

    .line 2572
    :cond_311
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_31a
    .catch Ljava/io/IOException; {:try_start_30e .. :try_end_31a} :catch_4ef

    .line 2576
    :goto_31a
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_327
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_337
    .catchall {:try_start_327 .. :try_end_337} :catchall_4c5

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_33e
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    const/16 v23, 0x1

    move-object/from16 v0, v21

    move/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_357
    .catchall {:try_start_33e .. :try_end_357} :catchall_4c8

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v21, "BackupManagerService"

    const-string v22, "Full backup pass complete."

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v21, v0

    goto/16 :goto_1e2

    .line 2563
    .end local v7           #e:Landroid/os/RemoteException;
    .restart local v3       #N:I
    .restart local v5       #compressing:Z
    .restart local v8       #encrypting:Z
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    .restart local v11       #header:[B
    .restart local v12       #headerbuf:Ljava/lang/StringBuilder;
    :cond_36f
    :try_start_36f
    move-object/from16 v0, p0

    move-object/from16 v1, v17

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->finalizeBackup(Ljava/io/OutputStream;)V
    :try_end_376
    .catchall {:try_start_36f .. :try_end_376} :catchall_456
    .catch Landroid/os/RemoteException; {:try_start_36f .. :try_end_376} :catch_2fd
    .catch Ljava/lang/Exception; {:try_start_36f .. :try_end_376} :catch_3e0

    .line 2569
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_382

    :try_start_37f
    invoke-virtual/range {v17 .. v17}, Ljava/io/OutputStream;->close()V

    .line 2572
    :cond_382
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_38b
    .catch Ljava/io/IOException; {:try_start_37f .. :try_end_38b} :catch_4e3

    .line 2576
    :goto_38b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_398
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_3a8
    .catchall {:try_start_398 .. :try_end_3a8} :catchall_4dd

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_3af
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    const/16 v23, 0x1

    move-object/from16 v0, v21

    move/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_3c8
    .catchall {:try_start_3af .. :try_end_3c8} :catchall_4e0

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v21, "BackupManagerService"

    const-string v22, "Full backup pass complete."

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v21, v0

    goto/16 :goto_1e2

    .line 2566
    .end local v3           #N:I
    .end local v5           #compressing:Z
    .end local v8           #encrypting:Z
    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .end local v11           #header:[B
    .end local v12           #headerbuf:Ljava/lang/StringBuilder;
    :catch_3e0
    move-exception v7

    .line 2567
    .local v7, e:Ljava/lang/Exception;
    :try_start_3e1
    const-string v21, "BackupManagerService"

    const-string v22, "Internal exception during full backup"

    move-object/from16 v0, v21

    move-object/from16 v1, v22

    invoke-static {v0, v1, v7}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_3ec
    .catchall {:try_start_3e1 .. :try_end_3ec} :catchall_456

    .line 2569
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_3f8

    :try_start_3f5
    invoke-virtual/range {v17 .. v17}, Ljava/io/OutputStream;->close()V

    .line 2572
    :cond_3f8
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_401
    .catch Ljava/io/IOException; {:try_start_3f5 .. :try_end_401} :catch_4ec

    .line 2576
    :goto_401
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_40e
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_41e
    .catchall {:try_start_40e .. :try_end_41e} :catchall_4cb

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_425
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    const/16 v23, 0x1

    move-object/from16 v0, v21

    move/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_43e
    .catchall {:try_start_425 .. :try_end_43e} :catchall_4ce

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v21, "BackupManagerService"

    const-string v22, "Full backup pass complete."

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v21, v0

    goto/16 :goto_1e2

    .line 2569
    .end local v7           #e:Ljava/lang/Exception;
    :catchall_456
    move-exception v21

    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->tearDown(Landroid/content/pm/PackageInfo;)V

    .line 2571
    if-eqz v17, :cond_463

    :try_start_460
    invoke-virtual/range {v17 .. v17}, Ljava/io/OutputStream;->close()V

    .line 2572
    :cond_463
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mOutputFile:Landroid/os/ParcelFileDescriptor;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_46c
    .catch Ljava/io/IOException; {:try_start_460 .. :try_end_46c} :catch_4f2

    .line 2576
    :goto_46c
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2577
    :try_start_479
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Landroid/util/SparseArray;->clear()V

    .line 2578
    monitor-exit v22
    :try_end_489
    .catchall {:try_start_479 .. :try_end_489} :catchall_4bf

    .line 2579
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 2580
    :try_start_490
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v23, v0

    const/16 v24, 0x1

    invoke-virtual/range {v23 .. v24}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 2581
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mLatchObject:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/lang/Object;->notifyAll()V

    .line 2582
    monitor-exit v22
    :try_end_4a5
    .catchall {:try_start_490 .. :try_end_4a5} :catchall_4c2

    .line 2583
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->sendEndBackup()V

    .line 2584
    const-string v22, "BackupManagerService"

    const-string v23, "Full backup pass complete."

    invoke-static/range {v22 .. v23}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 2585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->this$0:Lcom/android/server/BackupManagerService;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Landroid/os/PowerManager$WakeLock;->release()V

    .line 2569
    throw v21

    .line 2578
    :catchall_4bf
    move-exception v21

    :try_start_4c0
    monitor-exit v22
    :try_end_4c1
    .catchall {:try_start_4c0 .. :try_end_4c1} :catchall_4bf

    throw v21

    .line 2582
    :catchall_4c2
    move-exception v21

    :try_start_4c3
    monitor-exit v22
    :try_end_4c4
    .catchall {:try_start_4c3 .. :try_end_4c4} :catchall_4c2

    throw v21

    .line 2578
    .local v7, e:Landroid/os/RemoteException;
    :catchall_4c5
    move-exception v21

    :try_start_4c6
    monitor-exit v22
    :try_end_4c7
    .catchall {:try_start_4c6 .. :try_end_4c7} :catchall_4c5

    throw v21

    .line 2582
    :catchall_4c8
    move-exception v21

    :try_start_4c9
    monitor-exit v22
    :try_end_4ca
    .catchall {:try_start_4c9 .. :try_end_4ca} :catchall_4c8

    throw v21

    .line 2578
    .local v7, e:Ljava/lang/Exception;
    :catchall_4cb
    move-exception v21

    :try_start_4cc
    monitor-exit v22
    :try_end_4cd
    .catchall {:try_start_4cc .. :try_end_4cd} :catchall_4cb

    throw v21

    .line 2582
    :catchall_4ce
    move-exception v21

    :try_start_4cf
    monitor-exit v22
    :try_end_4d0
    .catchall {:try_start_4cf .. :try_end_4d0} :catchall_4ce

    throw v21

    .line 2578
    .end local v7           #e:Ljava/lang/Exception;
    .restart local v5       #compressing:Z
    .restart local v8       #encrypting:Z
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    :catchall_4d1
    move-exception v21

    :try_start_4d2
    monitor-exit v22
    :try_end_4d3
    .catchall {:try_start_4d2 .. :try_end_4d3} :catchall_4d1

    throw v21

    .line 2582
    :catchall_4d4
    move-exception v21

    :try_start_4d5
    monitor-exit v22
    :try_end_4d6
    .catchall {:try_start_4d5 .. :try_end_4d6} :catchall_4d4

    throw v21

    .line 2578
    .restart local v7       #e:Ljava/lang/Exception;
    .restart local v12       #headerbuf:Ljava/lang/StringBuilder;
    :catchall_4d7
    move-exception v21

    :try_start_4d8
    monitor-exit v22
    :try_end_4d9
    .catchall {:try_start_4d8 .. :try_end_4d9} :catchall_4d7

    throw v21

    .line 2582
    :catchall_4da
    move-exception v21

    :try_start_4db
    monitor-exit v22
    :try_end_4dc
    .catchall {:try_start_4db .. :try_end_4dc} :catchall_4da

    throw v21

    .line 2578
    .end local v7           #e:Ljava/lang/Exception;
    .restart local v3       #N:I
    .restart local v11       #header:[B
    :catchall_4dd
    move-exception v21

    :try_start_4de
    monitor-exit v22
    :try_end_4df
    .catchall {:try_start_4de .. :try_end_4df} :catchall_4dd

    throw v21

    .line 2582
    :catchall_4e0
    move-exception v21

    :try_start_4e1
    monitor-exit v22
    :try_end_4e2
    .catchall {:try_start_4e1 .. :try_end_4e2} :catchall_4e0

    throw v21

    .line 2573
    :catch_4e3
    move-exception v21

    goto/16 :goto_38b

    .end local v3           #N:I
    .end local v11           #header:[B
    .restart local v7       #e:Ljava/lang/Exception;
    :catch_4e6
    move-exception v21

    goto/16 :goto_29e

    .end local v7           #e:Ljava/lang/Exception;
    .end local v12           #headerbuf:Ljava/lang/StringBuilder;
    :catch_4e9
    move-exception v21

    goto/16 :goto_18f

    .end local v5           #compressing:Z
    .end local v8           #encrypting:Z
    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .restart local v7       #e:Ljava/lang/Exception;
    :catch_4ec
    move-exception v21

    goto/16 :goto_401

    .local v7, e:Landroid/os/RemoteException;
    :catch_4ef
    move-exception v21

    goto/16 :goto_31a

    .end local v7           #e:Landroid/os/RemoteException;
    :catch_4f2
    move-exception v22

    goto/16 :goto_46c

    .line 2539
    .restart local v5       #compressing:Z
    .restart local v8       #encrypting:Z
    .restart local v10       #finalOutput:Ljava/io/OutputStream;
    .restart local v12       #headerbuf:Ljava/lang/StringBuilder;
    :catch_4f5
    move-exception v7

    move-object v9, v10

    .end local v10           #finalOutput:Ljava/io/OutputStream;
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    goto/16 :goto_280

    .end local v9           #finalOutput:Ljava/io/OutputStream;
    .restart local v10       #finalOutput:Ljava/io/OutputStream;
    .restart local v11       #header:[B
    :cond_4f9
    move-object v9, v10

    .end local v10           #finalOutput:Ljava/io/OutputStream;
    .restart local v9       #finalOutput:Ljava/io/OutputStream;
    goto/16 :goto_235
.end method

.method sendEndBackup()V
    .registers 4

    .prologue
    .line 2855
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    if-eqz v1, :cond_9

    .line 2857
    :try_start_4
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    invoke-interface {v1}, Landroid/app/backup/IFullBackupRestoreObserver;->onEndBackup()V
    :try_end_9
    .catch Landroid/os/RemoteException; {:try_start_4 .. :try_end_9} :catch_a

    .line 2863
    :cond_9
    :goto_9
    return-void

    .line 2858
    :catch_a
    move-exception v0

    .line 2859
    .local v0, e:Landroid/os/RemoteException;
    const-string v1, "BackupManagerService"

    const-string v2, "full backup observer went away: endBackup"

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 2860
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    goto :goto_9
.end method

.method sendOnBackupPackage(Ljava/lang/String;)V
    .registers 5
    .parameter "name"

    .prologue
    .line 2843
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    if-eqz v1, :cond_9

    .line 2846
    :try_start_4
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    invoke-interface {v1, p1}, Landroid/app/backup/IFullBackupRestoreObserver;->onBackupPackage(Ljava/lang/String;)V
    :try_end_9
    .catch Landroid/os/RemoteException; {:try_start_4 .. :try_end_9} :catch_a

    .line 2852
    :cond_9
    :goto_9
    return-void

    .line 2847
    :catch_a
    move-exception v0

    .line 2848
    .local v0, e:Landroid/os/RemoteException;
    const-string v1, "BackupManagerService"

    const-string v2, "full backup observer went away: backupPackage"

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 2849
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    goto :goto_9
.end method

.method sendStartBackup()V
    .registers 4

    .prologue
    .line 2832
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    if-eqz v1, :cond_9

    .line 2834
    :try_start_4
    iget-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    invoke-interface {v1}, Landroid/app/backup/IFullBackupRestoreObserver;->onStartBackup()V
    :try_end_9
    .catch Landroid/os/RemoteException; {:try_start_4 .. :try_end_9} :catch_a

    .line 2840
    :cond_9
    :goto_9
    return-void

    .line 2835
    :catch_a
    move-exception v0

    .line 2836
    .local v0, e:Landroid/os/RemoteException;
    const-string v1, "BackupManagerService"

    const-string v2, "full backup observer went away: startBackup"

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 2837
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/server/BackupManagerService$PerformFullBackupTask;->mObserver:Landroid/app/backup/IFullBackupRestoreObserver;

    goto :goto_9
.end method
