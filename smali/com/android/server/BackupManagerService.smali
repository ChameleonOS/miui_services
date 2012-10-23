.class Lcom/android/server/BackupManagerService;
.super Landroid/app/backup/IBackupManager$Stub;
.source "BackupManagerService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/BackupManagerService$4;,
        Lcom/android/server/BackupManagerService$ActiveRestoreSession;,
        Lcom/android/server/BackupManagerService$PerformInitializeTask;,
        Lcom/android/server/BackupManagerService$PerformClearTask;,
        Lcom/android/server/BackupManagerService$PerformRestoreTask;,
        Lcom/android/server/BackupManagerService$RestoreState;,
        Lcom/android/server/BackupManagerService$PerformFullRestoreTask;,
        Lcom/android/server/BackupManagerService$RestorePolicy;,
        Lcom/android/server/BackupManagerService$FileMetadata;,
        Lcom/android/server/BackupManagerService$PerformFullBackupTask;,
        Lcom/android/server/BackupManagerService$PerformBackupTask;,
        Lcom/android/server/BackupManagerService$BackupState;,
        Lcom/android/server/BackupManagerService$BackupRestoreTask;,
        Lcom/android/server/BackupManagerService$ClearDataObserver;,
        Lcom/android/server/BackupManagerService$RunInitializeReceiver;,
        Lcom/android/server/BackupManagerService$RunBackupReceiver;,
        Lcom/android/server/BackupManagerService$BackupHandler;,
        Lcom/android/server/BackupManagerService$Operation;,
        Lcom/android/server/BackupManagerService$FullRestoreParams;,
        Lcom/android/server/BackupManagerService$FullBackupParams;,
        Lcom/android/server/BackupManagerService$FullParams;,
        Lcom/android/server/BackupManagerService$ClearParams;,
        Lcom/android/server/BackupManagerService$RestoreParams;,
        Lcom/android/server/BackupManagerService$RestoreGetSetsParams;,
        Lcom/android/server/BackupManagerService$ProvisionedObserver;,
        Lcom/android/server/BackupManagerService$BackupRequest;
    }
.end annotation


# static fields
.field static final BACKUP_FILE_HEADER_MAGIC:Ljava/lang/String; = "ANDROID BACKUP\n"

.field static final BACKUP_FILE_VERSION:I = 0x1

.field private static final BACKUP_INTERVAL:J = 0x36ee80L

.field static final BACKUP_MANIFEST_FILENAME:Ljava/lang/String; = "_manifest"

.field static final BACKUP_MANIFEST_VERSION:I = 0x1

.field static final COMPRESS_FULL_BACKUPS:Z = true

.field static final CURRENT_ANCESTRAL_RECORD_VERSION:I = 0x1

.field private static final DEBUG:Z = true

.field static final DEBUG_BACKUP_TRACE:Z = true

.field static final ENCRYPTION_ALGORITHM_NAME:Ljava/lang/String; = "AES-256"

.field private static final FIRST_BACKUP_INTERVAL:J = 0x2932e00L

.field private static final FUZZ_MILLIS:I = 0x493e0

.field static final INIT_SENTINEL_FILE_NAME:Ljava/lang/String; = "_need_init_"

.field private static final MORE_DEBUG:Z = false

.field static final MSG_BACKUP_RESTORE_STEP:I = 0x14

.field private static final MSG_FULL_CONFIRMATION_TIMEOUT:I = 0x9

.field static final MSG_OP_COMPLETE:I = 0x15

.field private static final MSG_RESTORE_TIMEOUT:I = 0x8

.field private static final MSG_RUN_BACKUP:I = 0x1

.field private static final MSG_RUN_CLEAR:I = 0x4

.field private static final MSG_RUN_FULL_BACKUP:I = 0x2

.field private static final MSG_RUN_FULL_RESTORE:I = 0xa

.field private static final MSG_RUN_GET_RESTORE_SETS:I = 0x6

.field private static final MSG_RUN_INITIALIZE:I = 0x5

.field private static final MSG_RUN_RESTORE:I = 0x3

.field private static final MSG_TIMEOUT:I = 0x7

.field static final OP_ACKNOWLEDGED:I = 0x1

.field static final OP_PENDING:I = 0x0

.field static final OP_TIMEOUT:I = -0x1

.field static final PACKAGE_MANAGER_SENTINEL:Ljava/lang/String; = "@pm@"

.field static final PBKDF2_HASH_ROUNDS:I = 0x2710

.field static final PBKDF2_KEY_SIZE:I = 0x100

.field static final PBKDF2_SALT_SIZE:I = 0x200

.field private static final RUN_BACKUP_ACTION:Ljava/lang/String; = "android.app.backup.intent.RUN"

.field private static final RUN_CLEAR_ACTION:Ljava/lang/String; = "android.app.backup.intent.CLEAR"

.field private static final RUN_INITIALIZE_ACTION:Ljava/lang/String; = "android.app.backup.intent.INIT"

.field static final SHARED_BACKUP_AGENT_PACKAGE:Ljava/lang/String; = "com.android.sharedstoragebackup"

.field private static final TAG:Ljava/lang/String; = "BackupManagerService"

.field static final TIMEOUT_BACKUP_INTERVAL:J = 0x7530L

.field static final TIMEOUT_FULL_BACKUP_INTERVAL:J = 0x493e0L

.field static final TIMEOUT_FULL_CONFIRMATION:J = 0xea60L

.field static final TIMEOUT_INTERVAL:J = 0x2710L

.field static final TIMEOUT_RESTORE_INTERVAL:J = 0xea60L

.field static final TIMEOUT_SHARED_BACKUP_INTERVAL:J = 0x1b7740L


# instance fields
.field mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

.field private mActivityManager:Landroid/app/IActivityManager;

.field final mAgentConnectLock:Ljava/lang/Object;

.field private mAlarmManager:Landroid/app/AlarmManager;

.field mAncestralPackages:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field mAncestralToken:J

.field mAutoRestore:Z

.field mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

.field mBackupManagerBinder:Landroid/app/backup/IBackupManager;

.field final mBackupParticipants:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field

.field volatile mBackupRunning:Z

.field final mBackupTrace:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field mBaseStateDir:Ljava/io/File;

.field mBroadcastReceiver:Landroid/content/BroadcastReceiver;

.field final mClearDataLock:Ljava/lang/Object;

.field volatile mClearingData:Z

.field mConnectedAgent:Landroid/app/IBackupAgent;

.field volatile mConnecting:Z

.field private mContext:Landroid/content/Context;

.field final mCurrentOpLock:Ljava/lang/Object;

.field final mCurrentOperations:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Lcom/android/server/BackupManagerService$Operation;",
            ">;"
        }
    .end annotation
.end field

.field mCurrentToken:J

.field mCurrentTransport:Ljava/lang/String;

.field mDataDir:Ljava/io/File;

.field mEnabled:Z

.field private mEverStored:Ljava/io/File;

.field mEverStoredApps:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field final mFullConfirmations:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Lcom/android/server/BackupManagerService$FullParams;",
            ">;"
        }
    .end annotation
.end field

.field mGoogleConnection:Landroid/content/ServiceConnection;

.field mGoogleTransport:Lcom/android/internal/backup/IBackupTransport;

.field mHandlerThread:Landroid/os/HandlerThread;

.field mJournal:Ljava/io/File;

.field mJournalDir:Ljava/io/File;

.field volatile mLastBackupPass:J

.field mLocalTransport:Lcom/android/internal/backup/IBackupTransport;

.field private mMountService:Landroid/os/storage/IMountService;

.field volatile mNextBackupPass:J

.field private mPackageManager:Landroid/content/pm/PackageManager;

.field mPackageManagerBinder:Landroid/content/pm/IPackageManager;

.field private mPasswordHash:Ljava/lang/String;

.field private mPasswordHashFile:Ljava/io/File;

.field private mPasswordSalt:[B

.field mPendingBackups:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/String;",
            "Lcom/android/server/BackupManagerService$BackupRequest;",
            ">;"
        }
    .end annotation
.end field

.field mPendingInits:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPowerManager:Landroid/os/PowerManager;

.field mProvisioned:Z

.field mProvisionedObserver:Landroid/database/ContentObserver;

.field final mQueueLock:Ljava/lang/Object;

.field private final mRng:Ljava/security/SecureRandom;

.field mRunBackupIntent:Landroid/app/PendingIntent;

.field mRunBackupReceiver:Landroid/content/BroadcastReceiver;

.field mRunInitIntent:Landroid/app/PendingIntent;

.field mRunInitReceiver:Landroid/content/BroadcastReceiver;

.field mTokenFile:Ljava/io/File;

.field final mTokenGenerator:Ljava/util/Random;

.field final mTransports:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/String;",
            "Lcom/android/internal/backup/IBackupTransport;",
            ">;"
        }
    .end annotation
.end field

.field mWakelock:Landroid/os/PowerManager$WakeLock;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 26
    .parameter "context"

    .prologue
    .line 713
    invoke-direct/range {p0 .. p0}, Landroid/app/backup/IBackupManager$Stub;-><init>()V

    .line 210
    new-instance v21, Landroid/util/SparseArray;

    invoke-direct/range {v21 .. v21}, Landroid/util/SparseArray;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    .line 225
    new-instance v21, Ljava/util/HashMap;

    invoke-direct/range {v21 .. v21}, Ljava/util/HashMap;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPendingBackups:Ljava/util/HashMap;

    .line 232
    new-instance v21, Ljava/lang/Object;

    invoke-direct/range {v21 .. v21}, Ljava/lang/Object;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    .line 238
    new-instance v21, Ljava/lang/Object;

    invoke-direct/range {v21 .. v21}, Ljava/lang/Object;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    .line 247
    new-instance v21, Ljava/util/ArrayList;

    invoke-direct/range {v21 .. v21}, Ljava/util/ArrayList;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    .line 250
    new-instance v21, Ljava/lang/Object;

    invoke-direct/range {v21 .. v21}, Ljava/lang/Object;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mClearDataLock:Ljava/lang/Object;

    .line 254
    new-instance v21, Ljava/util/HashMap;

    invoke-direct/range {v21 .. v21}, Ljava/util/HashMap;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    .line 405
    new-instance v21, Landroid/util/SparseArray;

    invoke-direct/range {v21 .. v21}, Landroid/util/SparseArray;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    .line 406
    new-instance v21, Ljava/lang/Object;

    invoke-direct/range {v21 .. v21}, Ljava/lang/Object;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    .line 407
    new-instance v21, Ljava/util/Random;

    invoke-direct/range {v21 .. v21}, Ljava/util/Random;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mTokenGenerator:Ljava/util/Random;

    .line 409
    new-instance v21, Landroid/util/SparseArray;

    invoke-direct/range {v21 .. v21}, Landroid/util/SparseArray;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    .line 423
    new-instance v21, Ljava/security/SecureRandom;

    invoke-direct/range {v21 .. v21}, Ljava/security/SecureRandom;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mRng:Ljava/security/SecureRandom;

    .line 438
    new-instance v21, Ljava/util/HashSet;

    invoke-direct/range {v21 .. v21}, Ljava/util/HashSet;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    .line 442
    const/16 v21, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    .line 443
    const-wide/16 v21, 0x0

    move-wide/from16 v0, v21

    move-object/from16 v2, p0

    iput-wide v0, v2, Lcom/android/server/BackupManagerService;->mAncestralToken:J

    .line 444
    const-wide/16 v21, 0x0

    move-wide/from16 v0, v21

    move-object/from16 v2, p0

    iput-wide v0, v2, Lcom/android/server/BackupManagerService;->mCurrentToken:J

    .line 448
    new-instance v21, Ljava/util/HashSet;

    invoke-direct/range {v21 .. v21}, Ljava/util/HashSet;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    .line 1335
    new-instance v21, Lcom/android/server/BackupManagerService$1;

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$1;-><init>(Lcom/android/server/BackupManagerService;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    .line 1391
    new-instance v21, Lcom/android/server/BackupManagerService$2;

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService$2;-><init>(Lcom/android/server/BackupManagerService;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mGoogleConnection:Landroid/content/ServiceConnection;

    .line 714
    move-object/from16 v0, p1

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    .line 715
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    .line 716
    invoke-static {}, Landroid/app/AppGlobals;->getPackageManager()Landroid/content/pm/IPackageManager;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPackageManagerBinder:Landroid/content/pm/IPackageManager;

    .line 717
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;

    .line 719
    const-string v21, "alarm"

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v21

    check-cast v21, Landroid/app/AlarmManager;

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    .line 720
    const-string v21, "power"

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v21

    check-cast v21, Landroid/os/PowerManager;

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPowerManager:Landroid/os/PowerManager;

    .line 721
    const-string v21, "mount"

    invoke-static/range {v21 .. v21}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v21

    invoke-static/range {v21 .. v21}, Landroid/os/storage/IMountService$Stub;->asInterface(Landroid/os/IBinder;)Landroid/os/storage/IMountService;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mMountService:Landroid/os/storage/IMountService;

    .line 723
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService;->asBinder()Landroid/os/IBinder;

    move-result-object v21

    invoke-static/range {v21 .. v21}, Lcom/android/server/BackupManagerService;->asInterface(Landroid/os/IBinder;)Landroid/app/backup/IBackupManager;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBackupManagerBinder:Landroid/app/backup/IBackupManager;

    .line 726
    new-instance v21, Landroid/os/HandlerThread;

    const-string v22, "backup"

    const/16 v23, 0xa

    invoke-direct/range {v21 .. v23}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;I)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    .line 727
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/os/HandlerThread;->start()V

    .line 728
    new-instance v21, Lcom/android/server/BackupManagerService$BackupHandler;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v22

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    move-object/from16 v2, v22

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService$BackupHandler;-><init>(Lcom/android/server/BackupManagerService;Landroid/os/Looper;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    .line 731
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v17

    .line 732
    .local v17, resolver:Landroid/content/ContentResolver;
    const-string v21, "backup_enabled"

    const/16 v22, 0x0

    move-object/from16 v0, v17

    move-object/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v21

    if-eqz v21, :cond_42e

    const/4 v4, 0x1

    .line 734
    .local v4, areEnabled:Z
    :goto_181
    const-string v21, "device_provisioned"

    const/16 v22, 0x0

    move-object/from16 v0, v17

    move-object/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v21

    if-eqz v21, :cond_431

    const/16 v21, 0x1

    :goto_193
    move/from16 v0, v21

    move-object/from16 v1, p0

    iput-boolean v0, v1, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    .line 736
    const-string v21, "backup_auto_restore"

    const/16 v22, 0x1

    move-object/from16 v0, v17

    move-object/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v21

    if-eqz v21, :cond_435

    const/16 v21, 0x1

    :goto_1ab
    move/from16 v0, v21

    move-object/from16 v1, p0

    iput-boolean v0, v1, Lcom/android/server/BackupManagerService;->mAutoRestore:Z

    .line 739
    new-instance v21, Lcom/android/server/BackupManagerService$ProvisionedObserver;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    move-object/from16 v22, v0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    move-object/from16 v2, v22

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService$ProvisionedObserver;-><init>(Lcom/android/server/BackupManagerService;Landroid/os/Handler;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mProvisionedObserver:Landroid/database/ContentObserver;

    .line 740
    const-string v21, "device_provisioned"

    invoke-static/range {v21 .. v21}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v21

    const/16 v22, 0x0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mProvisionedObserver:Landroid/database/ContentObserver;

    move-object/from16 v23, v0

    move-object/from16 v0, v17

    move-object/from16 v1, v21

    move/from16 v2, v22

    move-object/from16 v3, v23

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 746
    new-instance v21, Ljava/io/File;

    invoke-static {}, Landroid/os/Environment;->getSecureDataDirectory()Ljava/io/File;

    move-result-object v22

    const-string v23, "backup"

    invoke-direct/range {v21 .. v23}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    .line 747
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/io/File;->mkdirs()Z

    .line 748
    invoke-static {}, Landroid/os/Environment;->getDownloadCacheDirectory()Ljava/io/File;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mDataDir:Ljava/io/File;

    .line 750
    new-instance v21, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v22, v0

    const-string v23, "pwhash"

    invoke-direct/range {v21 .. v23}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    .line 751
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/io/File;->exists()Z

    move-result v21

    if-eqz v21, :cond_26a

    .line 752
    const/4 v8, 0x0

    .line 753
    .local v8, fin:Ljava/io/FileInputStream;
    const/4 v10, 0x0

    .line 755
    .local v10, in:Ljava/io/DataInputStream;
    :try_start_226
    new-instance v9, Ljava/io/FileInputStream;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    invoke-direct {v9, v0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_233
    .catchall {:try_start_226 .. :try_end_233} :catchall_457
    .catch Ljava/io/IOException; {:try_start_226 .. :try_end_233} :catch_439

    .line 756
    .end local v8           #fin:Ljava/io/FileInputStream;
    .local v9, fin:Ljava/io/FileInputStream;
    :try_start_233
    new-instance v11, Ljava/io/DataInputStream;

    new-instance v21, Ljava/io/BufferedInputStream;

    move-object/from16 v0, v21

    invoke-direct {v0, v9}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    move-object/from16 v0, v21

    invoke-direct {v11, v0}, Ljava/io/DataInputStream;-><init>(Ljava/io/InputStream;)V
    :try_end_241
    .catchall {:try_start_233 .. :try_end_241} :catchall_499
    .catch Ljava/io/IOException; {:try_start_233 .. :try_end_241} :catch_4a0

    .line 759
    .end local v10           #in:Ljava/io/DataInputStream;
    .local v11, in:Ljava/io/DataInputStream;
    :try_start_241
    invoke-virtual {v11}, Ljava/io/DataInputStream;->readInt()I

    move-result v19

    .line 760
    .local v19, saltLen:I
    move/from16 v0, v19

    new-array v0, v0, [B

    move-object/from16 v18, v0

    .line 761
    .local v18, salt:[B
    move-object/from16 v0, v18

    invoke-virtual {v11, v0}, Ljava/io/DataInputStream;->readFully([B)V

    .line 762
    invoke-virtual {v11}, Ljava/io/DataInputStream;->readUTF()Ljava/lang/String;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    .line 763
    move-object/from16 v0, v18

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mPasswordSalt:[B
    :try_end_260
    .catchall {:try_start_241 .. :try_end_260} :catchall_49c
    .catch Ljava/io/IOException; {:try_start_241 .. :try_end_260} :catch_4a3

    .line 768
    if-eqz v11, :cond_265

    :try_start_262
    invoke-virtual {v11}, Ljava/io/DataInputStream;->close()V

    .line 769
    :cond_265
    if-eqz v9, :cond_26a

    invoke-virtual {v9}, Ljava/io/FileInputStream;->close()V
    :try_end_26a
    .catch Ljava/io/IOException; {:try_start_262 .. :try_end_26a} :catch_493

    .line 777
    .end local v9           #fin:Ljava/io/FileInputStream;
    .end local v11           #in:Ljava/io/DataInputStream;
    .end local v18           #salt:[B
    .end local v19           #saltLen:I
    :cond_26a
    :goto_26a
    new-instance v21, Lcom/android/server/BackupManagerService$RunBackupReceiver;

    const/16 v22, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    move-object/from16 v2, v22

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService$RunBackupReceiver;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/server/BackupManagerService$1;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mRunBackupReceiver:Landroid/content/BroadcastReceiver;

    .line 778
    new-instance v7, Landroid/content/IntentFilter;

    invoke-direct {v7}, Landroid/content/IntentFilter;-><init>()V

    .line 779
    .local v7, filter:Landroid/content/IntentFilter;
    const-string v21, "android.app.backup.intent.RUN"

    move-object/from16 v0, v21

    invoke-virtual {v7, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 780
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mRunBackupReceiver:Landroid/content/BroadcastReceiver;

    move-object/from16 v21, v0

    const-string v22, "android.permission.BACKUP"

    const/16 v23, 0x0

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    move-object/from16 v2, v22

    move-object/from16 v3, v23

    invoke-virtual {v0, v1, v7, v2, v3}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 783
    new-instance v21, Lcom/android/server/BackupManagerService$RunInitializeReceiver;

    const/16 v22, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    move-object/from16 v2, v22

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService$RunInitializeReceiver;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/server/BackupManagerService$1;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mRunInitReceiver:Landroid/content/BroadcastReceiver;

    .line 784
    new-instance v7, Landroid/content/IntentFilter;

    .end local v7           #filter:Landroid/content/IntentFilter;
    invoke-direct {v7}, Landroid/content/IntentFilter;-><init>()V

    .line 785
    .restart local v7       #filter:Landroid/content/IntentFilter;
    const-string v21, "android.app.backup.intent.INIT"

    move-object/from16 v0, v21

    invoke-virtual {v7, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 786
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mRunInitReceiver:Landroid/content/BroadcastReceiver;

    move-object/from16 v21, v0

    const-string v22, "android.permission.BACKUP"

    const/16 v23, 0x0

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    move-object/from16 v2, v22

    move-object/from16 v3, v23

    invoke-virtual {v0, v1, v7, v2, v3}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 789
    new-instance v5, Landroid/content/Intent;

    const-string v21, "android.app.backup.intent.RUN"

    move-object/from16 v0, v21

    invoke-direct {v5, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 790
    .local v5, backupIntent:Landroid/content/Intent;
    const/high16 v21, 0x4000

    move/from16 v0, v21

    invoke-virtual {v5, v0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 791
    const/16 v21, 0x1

    const/16 v22, 0x0

    move-object/from16 v0, p1

    move/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v5, v2}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mRunBackupIntent:Landroid/app/PendingIntent;

    .line 793
    new-instance v13, Landroid/content/Intent;

    const-string v21, "android.app.backup.intent.INIT"

    move-object/from16 v0, v21

    invoke-direct {v13, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 794
    .local v13, initIntent:Landroid/content/Intent;
    const/high16 v21, 0x4000

    move/from16 v0, v21

    invoke-virtual {v5, v0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 795
    const/16 v21, 0x5

    const/16 v22, 0x0

    move-object/from16 v0, p1

    move/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v13, v2}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mRunInitIntent:Landroid/app/PendingIntent;

    .line 798
    new-instance v21, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v22, v0

    const-string v23, "pending"

    invoke-direct/range {v21 .. v23}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mJournalDir:Ljava/io/File;

    .line 799
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mJournalDir:Ljava/io/File;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/io/File;->mkdirs()Z

    .line 800
    const/16 v21, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    .line 803
    invoke-direct/range {p0 .. p0}, Lcom/android/server/BackupManagerService;->initPackageTracking()V

    .line 808
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    move-object/from16 v22, v0

    monitor-enter v22

    .line 809
    const/16 v21, 0x0

    :try_start_34a
    move-object/from16 v0, p0

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Lcom/android/server/BackupManagerService;->addPackageParticipantsLocked([Ljava/lang/String;)V

    .line 810
    monitor-exit v22
    :try_end_352
    .catchall {:try_start_34a .. :try_end_352} :catchall_463

    .line 815
    new-instance v21, Lcom/android/internal/backup/LocalTransport;

    move-object/from16 v0, v21

    move-object/from16 v1, p1

    invoke-direct {v0, v1}, Lcom/android/internal/backup/LocalTransport;-><init>(Landroid/content/Context;)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mLocalTransport:Lcom/android/internal/backup/IBackupTransport;

    .line 816
    new-instance v15, Landroid/content/ComponentName;

    const-class v21, Lcom/android/internal/backup/LocalTransport;

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    invoke-direct {v15, v0, v1}, Landroid/content/ComponentName;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 817
    .local v15, localName:Landroid/content/ComponentName;
    invoke-virtual {v15}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v21

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mLocalTransport:Lcom/android/internal/backup/IBackupTransport;

    move-object/from16 v22, v0

    move-object/from16 v0, p0

    move-object/from16 v1, v21

    move-object/from16 v2, v22

    invoke-direct {v0, v1, v2}, Lcom/android/server/BackupManagerService;->registerTransport(Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;)V

    .line 819
    const/16 v21, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mGoogleTransport:Lcom/android/internal/backup/IBackupTransport;

    .line 820
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v21

    const-string v22, "backup_transport"

    invoke-static/range {v21 .. v22}, Landroid/provider/Settings$Secure;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 822
    const-string v21, ""

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    move-object/from16 v22, v0

    invoke-virtual/range {v21 .. v22}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v21

    if-eqz v21, :cond_3ad

    .line 823
    const/16 v21, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 825
    :cond_3ad
    const-string v21, "BackupManagerService"

    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v23, "Starting with transport "

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    move-object/from16 v23, v0

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 830
    new-instance v20, Landroid/content/ComponentName;

    const-string v21, "com.google.android.backup"

    const-string v22, "com.google.android.backup.BackupTransportService"

    invoke-direct/range {v20 .. v22}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 836
    .local v20, transportComponent:Landroid/content/ComponentName;
    :try_start_3d4
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    move-object/from16 v21, v0

    invoke-virtual/range {v20 .. v20}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v22

    const/16 v23, 0x0

    invoke-virtual/range {v21 .. v23}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v12

    .line 838
    .local v12, info:Landroid/content/pm/ApplicationInfo;
    iget v0, v12, Landroid/content/pm/ApplicationInfo;->flags:I

    move/from16 v21, v0

    and-int/lit8 v21, v21, 0x1

    if-eqz v21, :cond_466

    .line 839
    const-string v21, "BackupManagerService"

    const-string v22, "Binding to Google transport"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 840
    new-instance v21, Landroid/content/Intent;

    invoke-direct/range {v21 .. v21}, Landroid/content/Intent;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    move-result-object v14

    .line 841
    .local v14, intent:Landroid/content/Intent;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mGoogleConnection:Landroid/content/ServiceConnection;

    move-object/from16 v21, v0

    const/16 v22, 0x1

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    move/from16 v2, v22

    invoke-virtual {v0, v14, v1, v2}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    :try_end_411
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_3d4 .. :try_end_411} :catch_481

    .line 852
    .end local v12           #info:Landroid/content/pm/ApplicationInfo;
    .end local v14           #intent:Landroid/content/Intent;
    :goto_411
    invoke-direct/range {p0 .. p0}, Lcom/android/server/BackupManagerService;->parseLeftoverJournals()V

    .line 855
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPowerManager:Landroid/os/PowerManager;

    move-object/from16 v21, v0

    const/16 v22, 0x1

    const-string v23, "*backup*"

    invoke-virtual/range {v21 .. v23}, Landroid/os/PowerManager;->newWakeLock(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    .line 858
    move-object/from16 v0, p0

    invoke-virtual {v0, v4}, Lcom/android/server/BackupManagerService;->setBackupEnabled(Z)V

    .line 859
    return-void

    .line 732
    .end local v4           #areEnabled:Z
    .end local v5           #backupIntent:Landroid/content/Intent;
    .end local v7           #filter:Landroid/content/IntentFilter;
    .end local v13           #initIntent:Landroid/content/Intent;
    .end local v15           #localName:Landroid/content/ComponentName;
    .end local v20           #transportComponent:Landroid/content/ComponentName;
    :cond_42e
    const/4 v4, 0x0

    goto/16 :goto_181

    .line 734
    .restart local v4       #areEnabled:Z
    :cond_431
    const/16 v21, 0x0

    goto/16 :goto_193

    .line 736
    :cond_435
    const/16 v21, 0x0

    goto/16 :goto_1ab

    .line 764
    .restart local v8       #fin:Ljava/io/FileInputStream;
    .restart local v10       #in:Ljava/io/DataInputStream;
    :catch_439
    move-exception v6

    .line 765
    .local v6, e:Ljava/io/IOException;
    :goto_43a
    :try_start_43a
    const-string v21, "BackupManagerService"

    const-string v22, "Unable to read saved backup pw hash"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_441
    .catchall {:try_start_43a .. :try_end_441} :catchall_457

    .line 768
    if-eqz v10, :cond_446

    :try_start_443
    invoke-virtual {v10}, Ljava/io/DataInputStream;->close()V

    .line 769
    :cond_446
    if-eqz v8, :cond_26a

    invoke-virtual {v8}, Ljava/io/FileInputStream;->close()V
    :try_end_44b
    .catch Ljava/io/IOException; {:try_start_443 .. :try_end_44b} :catch_44d

    goto/16 :goto_26a

    .line 770
    :catch_44d
    move-exception v6

    .line 771
    const-string v21, "BackupManagerService"

    const-string v22, "Unable to close streams"

    .end local v8           #fin:Ljava/io/FileInputStream;
    .end local v10           #in:Ljava/io/DataInputStream;
    :goto_452
    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_26a

    .line 767
    .end local v6           #e:Ljava/io/IOException;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    .restart local v10       #in:Ljava/io/DataInputStream;
    :catchall_457
    move-exception v21

    .line 768
    :goto_458
    if-eqz v10, :cond_45d

    :try_start_45a
    invoke-virtual {v10}, Ljava/io/DataInputStream;->close()V

    .line 769
    :cond_45d
    if-eqz v8, :cond_462

    invoke-virtual {v8}, Ljava/io/FileInputStream;->close()V
    :try_end_462
    .catch Ljava/io/IOException; {:try_start_45a .. :try_end_462} :catch_48a

    .line 767
    :cond_462
    :goto_462
    throw v21

    .line 810
    .end local v8           #fin:Ljava/io/FileInputStream;
    .end local v10           #in:Ljava/io/DataInputStream;
    .restart local v5       #backupIntent:Landroid/content/Intent;
    .restart local v7       #filter:Landroid/content/IntentFilter;
    .restart local v13       #initIntent:Landroid/content/Intent;
    :catchall_463
    move-exception v21

    :try_start_464
    monitor-exit v22
    :try_end_465
    .catchall {:try_start_464 .. :try_end_465} :catchall_463

    throw v21

    .line 843
    .restart local v12       #info:Landroid/content/pm/ApplicationInfo;
    .restart local v15       #localName:Landroid/content/ComponentName;
    .restart local v20       #transportComponent:Landroid/content/ComponentName;
    :cond_466
    :try_start_466
    const-string v21, "BackupManagerService"

    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v23, "Possible Google transport spoof: ignoring "

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    invoke-virtual {v0, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_480
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_466 .. :try_end_480} :catch_481

    goto :goto_411

    .line 845
    .end local v12           #info:Landroid/content/pm/ApplicationInfo;
    :catch_481
    move-exception v16

    .line 847
    .local v16, nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v21, "BackupManagerService"

    const-string v22, "Google transport not present"

    invoke-static/range {v21 .. v22}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_411

    .line 770
    .end local v5           #backupIntent:Landroid/content/Intent;
    .end local v7           #filter:Landroid/content/IntentFilter;
    .end local v13           #initIntent:Landroid/content/Intent;
    .end local v15           #localName:Landroid/content/ComponentName;
    .end local v16           #nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v20           #transportComponent:Landroid/content/ComponentName;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    .restart local v10       #in:Ljava/io/DataInputStream;
    :catch_48a
    move-exception v6

    .line 771
    .restart local v6       #e:Ljava/io/IOException;
    const-string v22, "BackupManagerService"

    const-string v23, "Unable to close streams"

    invoke-static/range {v22 .. v23}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_462

    .line 770
    .end local v6           #e:Ljava/io/IOException;
    .end local v8           #fin:Ljava/io/FileInputStream;
    .end local v10           #in:Ljava/io/DataInputStream;
    .restart local v9       #fin:Ljava/io/FileInputStream;
    .restart local v11       #in:Ljava/io/DataInputStream;
    .restart local v18       #salt:[B
    .restart local v19       #saltLen:I
    :catch_493
    move-exception v6

    .line 771
    .restart local v6       #e:Ljava/io/IOException;
    const-string v21, "BackupManagerService"

    const-string v22, "Unable to close streams"

    goto :goto_452

    .line 767
    .end local v6           #e:Ljava/io/IOException;
    .end local v11           #in:Ljava/io/DataInputStream;
    .end local v18           #salt:[B
    .end local v19           #saltLen:I
    .restart local v10       #in:Ljava/io/DataInputStream;
    :catchall_499
    move-exception v21

    move-object v8, v9

    .end local v9           #fin:Ljava/io/FileInputStream;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    goto :goto_458

    .end local v8           #fin:Ljava/io/FileInputStream;
    .end local v10           #in:Ljava/io/DataInputStream;
    .restart local v9       #fin:Ljava/io/FileInputStream;
    .restart local v11       #in:Ljava/io/DataInputStream;
    :catchall_49c
    move-exception v21

    move-object v10, v11

    .end local v11           #in:Ljava/io/DataInputStream;
    .restart local v10       #in:Ljava/io/DataInputStream;
    move-object v8, v9

    .end local v9           #fin:Ljava/io/FileInputStream;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    goto :goto_458

    .line 764
    .end local v8           #fin:Ljava/io/FileInputStream;
    .restart local v9       #fin:Ljava/io/FileInputStream;
    :catch_4a0
    move-exception v6

    move-object v8, v9

    .end local v9           #fin:Ljava/io/FileInputStream;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    goto :goto_43a

    .end local v8           #fin:Ljava/io/FileInputStream;
    .end local v10           #in:Ljava/io/DataInputStream;
    .restart local v9       #fin:Ljava/io/FileInputStream;
    .restart local v11       #in:Ljava/io/DataInputStream;
    :catch_4a3
    move-exception v6

    move-object v10, v11

    .end local v11           #in:Ljava/io/DataInputStream;
    .restart local v10       #in:Ljava/io/DataInputStream;
    move-object v8, v9

    .end local v9           #fin:Ljava/io/FileInputStream;
    .restart local v8       #fin:Ljava/io/FileInputStream;
    goto :goto_43a
.end method

.method static synthetic access$000(Lcom/android/server/BackupManagerService;J)V
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2}, Lcom/android/server/BackupManagerService;->startBackupAlarmsLocked(J)V

    return-void
.end method

.method static synthetic access$100(Lcom/android/server/BackupManagerService;Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1100(Lcom/android/server/BackupManagerService;I)[B
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->randomBytes(I)[B

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1200(Lcom/android/server/BackupManagerService;Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;
    .registers 5
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"
    .parameter "x3"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2, p3}, Lcom/android/server/BackupManagerService;->buildPasswordKey(Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1300(Lcom/android/server/BackupManagerService;)Ljava/security/SecureRandom;
    .registers 2
    .parameter "x0"

    .prologue
    .line 136
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mRng:Ljava/security/SecureRandom;

    return-object v0
.end method

.method static synthetic access$1400(Lcom/android/server/BackupManagerService;[B)Ljava/lang/String;
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1500(Lcom/android/server/BackupManagerService;[B[BI)[B
    .registers 5
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"
    .parameter "x3"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2, p3}, Lcom/android/server/BackupManagerService;->makeKeyChecksum([B[BI)[B

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1600(Lcom/android/server/BackupManagerService;Ljava/lang/String;)[B
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->hexToByteArray(Ljava/lang/String;)[B

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1700(Lcom/android/server/BackupManagerService;[Landroid/content/pm/Signature;Landroid/content/pm/PackageInfo;)Z
    .registers 4
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2}, Lcom/android/server/BackupManagerService;->signaturesMatch([Landroid/content/pm/Signature;Landroid/content/pm/PackageInfo;)Z

    move-result v0

    return v0
.end method

.method static synthetic access$1800(Lcom/android/server/BackupManagerService;Ljava/lang/String;Ljava/util/HashSet;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;Ljava/util/HashSet;)V

    return-void
.end method

.method static synthetic access$1900(Lcom/android/server/BackupManagerService;)Landroid/content/Context;
    .registers 2
    .parameter "x0"

    .prologue
    .line 136
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$400(Lcom/android/server/BackupManagerService;)Landroid/app/AlarmManager;
    .registers 2
    .parameter "x0"

    .prologue
    .line 136
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    return-object v0
.end method

.method static synthetic access$500(Lcom/android/server/BackupManagerService;Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 136
    invoke-direct {p0, p1, p2}, Lcom/android/server/BackupManagerService;->registerTransport(Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;)V

    return-void
.end method

.method static synthetic access$600(Lcom/android/server/BackupManagerService;)Landroid/content/pm/PackageManager;
    .registers 2
    .parameter "x0"

    .prologue
    .line 136
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    return-object v0
.end method

.method static synthetic access$700(Lcom/android/server/BackupManagerService;Ljava/lang/String;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 136
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$800(Lcom/android/server/BackupManagerService;)Landroid/app/IActivityManager;
    .registers 2
    .parameter "x0"

    .prologue
    .line 136
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;

    return-object v0
.end method

.method private addPackageParticipantsLockedInner(Ljava/lang/String;Ljava/util/List;)V
    .registers 10
    .parameter "packageName"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/util/List",
            "<",
            "Landroid/content/pm/PackageInfo;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 1427
    .local p2, targetPkgs:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/PackageInfo;>;"
    invoke-interface {p2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_4
    :goto_4
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_67

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/PackageInfo;

    .line 1428
    .local v1, pkg:Landroid/content/pm/PackageInfo;
    if-eqz p1, :cond_1a

    iget-object v4, v1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v4, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 1429
    :cond_1a
    iget-object v4, v1, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v3, v4, Landroid/content/pm/ApplicationInfo;->uid:I

    .line 1430
    .local v3, uid:I
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v4, v3}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/HashSet;

    .line 1431
    .local v2, set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-nez v2, :cond_32

    .line 1432
    new-instance v2, Ljava/util/HashSet;

    .end local v2           #set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    invoke-direct {v2}, Ljava/util/HashSet;-><init>()V

    .line 1433
    .restart local v2       #set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v4, v3, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 1435
    :cond_32
    iget-object v4, v1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v2, v4}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 1439
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    iget-object v5, v1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_4

    .line 1440
    const-string v4, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "New app "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, v1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " never backed up; scheduling"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1442
    iget-object v4, v1, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-direct {p0, v4}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;)V

    goto :goto_4

    .line 1446
    .end local v1           #pkg:Landroid/content/pm/PackageInfo;
    .end local v2           #set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    .end local v3           #uid:I
    :cond_67
    return-void
.end method

.method private buildCharArrayKey([C[BI)Ljavax/crypto/SecretKey;
    .registers 9
    .parameter "pwArray"
    .parameter "salt"
    .parameter "rounds"

    .prologue
    .line 1046
    :try_start_0
    const-string v3, "PBKDF2WithHmacSHA1"

    invoke-static {v3}, Ljavax/crypto/SecretKeyFactory;->getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;

    move-result-object v1

    .line 1047
    .local v1, keyFactory:Ljavax/crypto/SecretKeyFactory;
    new-instance v2, Ljavax/crypto/spec/PBEKeySpec;

    const/16 v3, 0x100

    invoke-direct {v2, p1, p2, p3, v3}, Ljavax/crypto/spec/PBEKeySpec;-><init>([C[BII)V

    .line 1048
    .local v2, ks:Ljava/security/spec/KeySpec;
    invoke-virtual {v1, v2}, Ljavax/crypto/SecretKeyFactory;->generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    :try_end_10
    .catch Ljava/security/spec/InvalidKeySpecException; {:try_start_0 .. :try_end_10} :catch_12
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_10} :catch_1c

    move-result-object v3

    .line 1054
    .end local v1           #keyFactory:Ljavax/crypto/SecretKeyFactory;
    .end local v2           #ks:Ljava/security/spec/KeySpec;
    :goto_11
    return-object v3

    .line 1049
    :catch_12
    move-exception v0

    .line 1050
    .local v0, e:Ljava/security/spec/InvalidKeySpecException;
    const-string v3, "BackupManagerService"

    const-string v4, "Invalid key spec for PBKDF2!"

    invoke-static {v3, v4}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1054
    .end local v0           #e:Ljava/security/spec/InvalidKeySpecException;
    :goto_1a
    const/4 v3, 0x0

    goto :goto_11

    .line 1051
    :catch_1c
    move-exception v0

    .line 1052
    .local v0, e:Ljava/security/NoSuchAlgorithmException;
    const-string v3, "BackupManagerService"

    const-string v4, "PBKDF2 unavailable!"

    invoke-static {v3, v4}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1a
.end method

.method private buildPasswordHash(Ljava/lang/String;[BI)Ljava/lang/String;
    .registers 6
    .parameter "pw"
    .parameter "salt"
    .parameter "rounds"

    .prologue
    .line 1058
    invoke-direct {p0, p1, p2, p3}, Lcom/android/server/BackupManagerService;->buildPasswordKey(Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;

    move-result-object v0

    .line 1059
    .local v0, key:Ljavax/crypto/SecretKey;
    if-eqz v0, :cond_f

    .line 1060
    invoke-interface {v0}, Ljavax/crypto/SecretKey;->getEncoded()[B

    move-result-object v1

    invoke-direct {p0, v1}, Lcom/android/server/BackupManagerService;->byteArrayToHex([B)Ljava/lang/String;

    move-result-object v1

    .line 1062
    :goto_e
    return-object v1

    :cond_f
    const/4 v1, 0x0

    goto :goto_e
.end method

.method private buildPasswordKey(Ljava/lang/String;[BI)Ljavax/crypto/SecretKey;
    .registers 5
    .parameter "pw"
    .parameter "salt"
    .parameter "rounds"

    .prologue
    .line 1041
    invoke-virtual {p1}, Ljava/lang/String;->toCharArray()[C

    move-result-object v0

    invoke-direct {p0, v0, p2, p3}, Lcom/android/server/BackupManagerService;->buildCharArrayKey([C[BI)Ljavax/crypto/SecretKey;

    move-result-object v0

    return-object v0
.end method

.method private byteArrayToHex([B)Ljava/lang/String;
    .registers 6
    .parameter "data"

    .prologue
    .line 1066
    new-instance v0, Ljava/lang/StringBuilder;

    array-length v2, p1

    mul-int/lit8 v2, v2, 0x2

    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 1067
    .local v0, buf:Ljava/lang/StringBuilder;
    const/4 v1, 0x0

    .local v1, i:I
    :goto_9
    array-length v2, p1

    if-ge v1, v2, :cond_19

    .line 1068
    aget-byte v2, p1, v1

    const/4 v3, 0x1

    invoke-static {v2, v3}, Ljava/lang/Byte;->toHexString(BZ)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1067
    add-int/lit8 v1, v1, 0x1

    goto :goto_9

    .line 1070
    :cond_19
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    return-object v2
.end method

.method private dataChangedImpl(Ljava/lang/String;)V
    .registers 3
    .parameter "packageName"

    .prologue
    .line 4791
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->dataChangedTargets(Ljava/lang/String;)Ljava/util/HashSet;

    move-result-object v0

    .line 4792
    .local v0, targets:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    invoke-direct {p0, p1, v0}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;Ljava/util/HashSet;)V

    .line 4793
    return-void
.end method

.method private dataChangedImpl(Ljava/lang/String;Ljava/util/HashSet;)V
    .registers 8
    .parameter "packageName"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 4799
    .local p2, targets:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    const/16 v1, 0xb04

    invoke-static {v1, p1}, Landroid/util/EventLog;->writeEvent(ILjava/lang/String;)I

    .line 4801
    if-nez p2, :cond_34

    .line 4802
    const-string v1, "BackupManagerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "dataChanged but no participant pkg=\'"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\'"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " uid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 4831
    :goto_33
    return-void

    .line 4807
    :cond_34
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v2

    .line 4809
    :try_start_37
    invoke-virtual {p2, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_65

    .line 4812
    new-instance v0, Lcom/android/server/BackupManagerService$BackupRequest;

    invoke-direct {v0, p0, p1}, Lcom/android/server/BackupManagerService$BackupRequest;-><init>(Lcom/android/server/BackupManagerService;Ljava/lang/String;)V

    .line 4813
    .local v0, req:Lcom/android/server/BackupManagerService$BackupRequest;
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mPendingBackups:Ljava/util/HashMap;

    invoke-virtual {v1, p1, v0}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-nez v1, :cond_65

    .line 4814
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Now staging backup of "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 4819
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->writeToJournalLocked(Ljava/lang/String;)V

    .line 4830
    .end local v0           #req:Lcom/android/server/BackupManagerService$BackupRequest;
    :cond_65
    monitor-exit v2

    goto :goto_33

    :catchall_67
    move-exception v1

    monitor-exit v2
    :try_end_69
    .catchall {:try_start_37 .. :try_end_69} :catchall_67

    throw v1
.end method

.method private dataChangedTargets(Ljava/lang/String;)Ljava/util/HashSet;
    .registers 10
    .parameter "packageName"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    .line 4837
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v5, "android.permission.BACKUP"

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v6

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v7

    invoke-virtual {v4, v5, v6, v7}, Landroid/content/Context;->checkPermission(Ljava/lang/String;II)I

    move-result v4

    const/4 v5, -0x1

    if-ne v4, v5, :cond_27

    .line 4839
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    monitor-enter v5

    .line 4840
    :try_start_16
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v6

    invoke-virtual {v4, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/HashSet;

    monitor-exit v5

    .line 4856
    :goto_23
    return-object v4

    .line 4841
    :catchall_24
    move-exception v4

    monitor-exit v5
    :try_end_26
    .catchall {:try_start_16 .. :try_end_26} :catchall_24

    throw v4

    .line 4846
    :cond_27
    new-instance v3, Ljava/util/HashSet;

    invoke-direct {v3}, Ljava/util/HashSet;-><init>()V

    .line 4847
    .local v3, targets:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    monitor-enter v5

    .line 4848
    :try_start_2f
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v4}, Landroid/util/SparseArray;->size()I

    move-result v0

    .line 4849
    .local v0, N:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_36
    if-ge v1, v0, :cond_48

    .line 4850
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/HashSet;

    .line 4851
    .local v2, s:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-eqz v2, :cond_45

    .line 4852
    invoke-virtual {v3, v2}, Ljava/util/HashSet;->addAll(Ljava/util/Collection;)Z

    .line 4849
    :cond_45
    add-int/lit8 v1, v1, 0x1

    goto :goto_36

    .line 4855
    .end local v2           #s:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :cond_48
    monitor-exit v5

    move-object v4, v3

    .line 4856
    goto :goto_23

    .line 4855
    .end local v0           #N:I
    .end local v1           #i:I
    :catchall_4b
    move-exception v4

    monitor-exit v5
    :try_end_4d
    .catchall {:try_start_2f .. :try_end_4d} :catchall_4b

    throw v4
.end method

.method private dumpInternal(Ljava/io/PrintWriter;)V
    .registers 28
    .parameter "pw"

    .prologue
    .line 5811
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    move-object/from16 v23, v0

    monitor-enter v23

    .line 5812
    :try_start_7
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Backup Manager is "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService;->mEnabled:Z

    move/from16 v22, v0

    if-eqz v22, :cond_226

    const-string v22, "enabled"

    :goto_20
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, " / "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    move/from16 v22, v0

    if-nez v22, :cond_22a

    const-string v22, "not "

    :goto_3c
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, "provisioned / "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Ljava/util/HashSet;->size()I

    move-result v22

    if-nez v22, :cond_22e

    const-string v22, "not "

    :goto_5c
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, "pending init"

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5815
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Auto-restore is "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService;->mAutoRestore:Z

    move/from16 v22, v0

    if-eqz v22, :cond_232

    const-string v22, "enabled"

    :goto_92
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5816
    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/BackupManagerService;->mBackupRunning:Z

    move/from16 v22, v0

    if-eqz v22, :cond_b6

    const-string v22, "Backup currently running"

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5817
    :cond_b6
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Last backup pass started: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-wide v0, v0, Lcom/android/server/BackupManagerService;->mLastBackupPass:J

    move-wide/from16 v24, v0

    move-object/from16 v0, v22

    move-wide/from16 v1, v24

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, " (now = "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v24

    move-object/from16 v0, v22

    move-wide/from16 v1, v24

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v22

    const/16 v24, 0x29

    move-object/from16 v0, v22

    move/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5819
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "  next scheduled: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-wide v0, v0, Lcom/android/server/BackupManagerService;->mNextBackupPass:J

    move-wide/from16 v24, v0

    move-object/from16 v0, v22

    move-wide/from16 v1, v24

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5821
    const-string v22, "Available transports:"

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5822
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/BackupManagerService;->listAllTransports()[Ljava/lang/String;

    move-result-object v5

    .local v5, arr$:[Ljava/lang/String;
    array-length v13, v5

    .local v13, len$:I
    const/4 v11, 0x0

    .local v11, i$:I
    move v12, v11

    .end local v5           #arr$:[Ljava/lang/String;
    .end local v11           #i$:I
    .end local v13           #len$:I
    .local v12, i$:I
    :goto_136
    if-ge v12, v13, :cond_26b

    aget-object v19, v5, v12

    .line 5823
    .local v19, t:Ljava/lang/String;
    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    move-object/from16 v22, v0

    move-object/from16 v0, v19

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v22

    if-eqz v22, :cond_236

    const-string v22, "  * "

    :goto_151
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v19

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V
    :try_end_16c
    .catchall {:try_start_7 .. :try_end_16c} :catchall_2d0

    .line 5825
    :try_start_16c
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v1}, Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v20

    .line 5826
    .local v20, transport:Lcom/android/internal/backup/IBackupTransport;
    new-instance v7, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v22, v0

    invoke-interface/range {v20 .. v20}, Lcom/android/internal/backup/IBackupTransport;->transportDirName()Ljava/lang/String;

    move-result-object v24

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-direct {v7, v0, v1}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 5827
    .local v7, dir:Ljava/io/File;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "       destination: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-interface/range {v20 .. v20}, Lcom/android/internal/backup/IBackupTransport;->currentDestinationString()Ljava/lang/String;

    move-result-object v24

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5828
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "       intent: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-interface/range {v20 .. v20}, Lcom/android/internal/backup/IBackupTransport;->configurationIntent()Landroid/content/Intent;

    move-result-object v24

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5829
    invoke-virtual {v7}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v6

    .local v6, arr$:[Ljava/io/File;
    array-length v14, v6

    .local v14, len$:I
    const/4 v11, 0x0

    .end local v12           #i$:I
    .restart local v11       #i$:I
    :goto_1d9
    if-ge v11, v14, :cond_266

    aget-object v9, v6, v11

    .line 5830
    .local v9, f:Ljava/io/File;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "       "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual {v9}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v24

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, " - "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual {v9}, Ljava/io/File;->length()J

    move-result-wide v24

    move-object/from16 v0, v22

    move-wide/from16 v1, v24

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v22

    const-string v24, " state bytes"

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V
    :try_end_223
    .catchall {:try_start_16c .. :try_end_223} :catchall_2d0
    .catch Ljava/lang/Exception; {:try_start_16c .. :try_end_223} :catch_23a

    .line 5829
    add-int/lit8 v11, v11, 0x1

    goto :goto_1d9

    .line 5812
    .end local v6           #arr$:[Ljava/io/File;
    .end local v7           #dir:Ljava/io/File;
    .end local v9           #f:Ljava/io/File;
    .end local v11           #i$:I
    .end local v14           #len$:I
    .end local v19           #t:Ljava/lang/String;
    .end local v20           #transport:Lcom/android/internal/backup/IBackupTransport;
    :cond_226
    :try_start_226
    const-string v22, "disabled"

    goto/16 :goto_20

    :cond_22a
    const-string v22, ""

    goto/16 :goto_3c

    :cond_22e
    const-string v22, ""

    goto/16 :goto_5c

    .line 5815
    :cond_232
    const-string v22, "disabled"

    goto/16 :goto_92

    .line 5823
    .restart local v12       #i$:I
    .restart local v19       #t:Ljava/lang/String;
    :cond_236
    const-string v22, "    "

    goto/16 :goto_151

    .line 5832
    .end local v12           #i$:I
    :catch_23a
    move-exception v8

    .line 5833
    .local v8, e:Ljava/lang/Exception;
    const-string v22, "BackupManagerService"

    const-string v24, "Error in transport"

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-static {v0, v1, v8}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 5834
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "        Error: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5822
    .end local v8           #e:Ljava/lang/Exception;
    :cond_266
    add-int/lit8 v11, v12, 0x1

    .restart local v11       #i$:I
    move v12, v11

    .end local v11           #i$:I
    .restart local v12       #i$:I
    goto/16 :goto_136

    .line 5838
    .end local v19           #t:Ljava/lang/String;
    :cond_26b
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Pending init: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    move-object/from16 v24, v0

    invoke-virtual/range {v24 .. v24}, Ljava/util/HashSet;->size()I

    move-result v24

    move-object/from16 v0, v22

    move/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5839
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v11

    .end local v12           #i$:I
    .local v11, i$:Ljava/util/Iterator;
    :goto_2a1
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_2d3

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Ljava/lang/String;

    .line 5840
    .local v18, s:Ljava/lang/String;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "    "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_2a1

    .line 5883
    .end local v11           #i$:Ljava/util/Iterator;
    .end local v18           #s:Ljava/lang/String;
    :catchall_2d0
    move-exception v22

    monitor-exit v23
    :try_end_2d2
    .catchall {:try_start_226 .. :try_end_2d2} :catchall_2d0

    throw v22

    .line 5844
    .restart local v11       #i$:Ljava/util/Iterator;
    :cond_2d3
    :try_start_2d3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    move-object/from16 v24, v0

    monitor-enter v24
    :try_end_2da
    .catchall {:try_start_2d3 .. :try_end_2da} :catchall_2d0

    .line 5845
    :try_start_2da
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    move-object/from16 v22, v0

    invoke-interface/range {v22 .. v22}, Ljava/util/List;->isEmpty()Z

    move-result v22

    if-nez v22, :cond_32b

    .line 5846
    const-string v22, "Most recent backup trace:"

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5847
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    move-object/from16 v22, v0

    invoke-interface/range {v22 .. v22}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v11

    :goto_2f9
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_32b

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Ljava/lang/String;

    .line 5848
    .restart local v18       #s:Ljava/lang/String;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "   "

    move-object/from16 v0, v22

    move-object/from16 v1, v25

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_2f9

    .line 5851
    .end local v18           #s:Ljava/lang/String;
    :catchall_328
    move-exception v22

    monitor-exit v24
    :try_end_32a
    .catchall {:try_start_2da .. :try_end_32a} :catchall_328

    :try_start_32a
    throw v22
    :try_end_32b
    .catchall {:try_start_32a .. :try_end_32b} :catchall_2d0

    :cond_32b
    :try_start_32b
    monitor-exit v24
    :try_end_32c
    .catchall {:try_start_32b .. :try_end_32c} :catchall_328

    .line 5854
    :try_start_32c
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Landroid/util/SparseArray;->size()I

    move-result v3

    .line 5855
    .local v3, N:I
    const-string v22, "Participants:"

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5856
    const/4 v10, 0x0

    .local v10, i:I
    :goto_340
    if-ge v10, v3, :cond_3a0

    .line 5857
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    invoke-virtual {v0, v10}, Landroid/util/SparseArray;->keyAt(I)I

    move-result v21

    .line 5858
    .local v21, uid:I
    const-string v22, "  uid: "

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 5859
    move-object/from16 v0, p1

    move/from16 v1, v21

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(I)V

    .line 5860
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    invoke-virtual {v0, v10}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/util/HashSet;

    .line 5861
    .local v15, participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    invoke-virtual {v15}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v11

    :goto_370
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_39d

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 5862
    .local v4, app:Ljava/lang/String;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "    "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_370

    .line 5856
    .end local v4           #app:Ljava/lang/String;
    :cond_39d
    add-int/lit8 v10, v10, 0x1

    goto :goto_340

    .line 5866
    .end local v15           #participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    .end local v21           #uid:I
    :cond_3a0
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Ancestral packages: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    move-object/from16 v22, v0

    if-nez v22, :cond_40d

    const-string v22, "none"

    :goto_3b9
    move-object/from16 v0, v24

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5868
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    move-object/from16 v22, v0

    if-eqz v22, :cond_41c

    .line 5869
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    move-object/from16 v22, v0

    invoke-interface/range {v22 .. v22}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v11

    :goto_3de
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_41c

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v16

    check-cast v16, Ljava/lang/String;

    .line 5870
    .local v16, pkg:Ljava/lang/String;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "    "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v16

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_3de

    .line 5866
    .end local v16           #pkg:Ljava/lang/String;
    :cond_40d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    move-object/from16 v22, v0

    invoke-interface/range {v22 .. v22}, Ljava/util/Set;->size()I

    move-result v22

    invoke-static/range {v22 .. v22}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v22

    goto :goto_3b9

    .line 5874
    :cond_41c
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Ever backed up: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    move-object/from16 v24, v0

    invoke-virtual/range {v24 .. v24}, Ljava/util/HashSet;->size()I

    move-result v24

    move-object/from16 v0, v22

    move/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5875
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v11

    :goto_452
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_481

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v16

    check-cast v16, Ljava/lang/String;

    .line 5876
    .restart local v16       #pkg:Ljava/lang/String;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "    "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v16

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_452

    .line 5879
    .end local v16           #pkg:Ljava/lang/String;
    :cond_481
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "Pending backup: "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPendingBackups:Ljava/util/HashMap;

    move-object/from16 v24, v0

    invoke-virtual/range {v24 .. v24}, Ljava/util/HashMap;->size()I

    move-result v24

    move-object/from16 v0, v22

    move/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 5880
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPendingBackups:Ljava/util/HashMap;

    move-object/from16 v22, v0

    invoke-virtual/range {v22 .. v22}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v22

    invoke-interface/range {v22 .. v22}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v11

    :goto_4bb
    invoke-interface {v11}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_4ea

    invoke-interface {v11}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v17

    check-cast v17, Lcom/android/server/BackupManagerService$BackupRequest;

    .line 5881
    .local v17, req:Lcom/android/server/BackupManagerService$BackupRequest;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    const-string v24, "    "

    move-object/from16 v0, v22

    move-object/from16 v1, v24

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    move-object/from16 v0, v22

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    goto :goto_4bb

    .line 5883
    .end local v17           #req:Lcom/android/server/BackupManagerService$BackupRequest;
    :cond_4ea
    monitor-exit v23
    :try_end_4eb
    .catchall {:try_start_32c .. :try_end_4eb} :catchall_2d0

    .line 5884
    return-void
.end method

.method private getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;
    .registers 7
    .parameter "transportName"

    .prologue
    .line 1601
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v2

    .line 1602
    :try_start_3
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v1, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/internal/backup/IBackupTransport;

    .line 1603
    .local v0, transport:Lcom/android/internal/backup/IBackupTransport;
    if-nez v0, :cond_25

    .line 1604
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Requested unavailable transport: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1606
    :cond_25
    monitor-exit v2

    return-object v0

    .line 1607
    .end local v0           #transport:Lcom/android/internal/backup/IBackupTransport;
    :catchall_27
    move-exception v1

    monitor-exit v2
    :try_end_29
    .catchall {:try_start_3 .. :try_end_29} :catchall_27

    throw v1
.end method

.method private hexToByteArray(Ljava/lang/String;)[B
    .registers 8
    .parameter "digits"

    .prologue
    .line 1074
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v3

    div-int/lit8 v0, v3, 0x2

    .line 1075
    .local v0, bytes:I
    mul-int/lit8 v3, v0, 0x2

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v4

    if-eq v3, v4, :cond_16

    .line 1076
    new-instance v3, Ljava/lang/IllegalArgumentException;

    const-string v4, "Hex string must have an even number of digits"

    invoke-direct {v3, v4}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 1079
    :cond_16
    new-array v2, v0, [B

    .line 1080
    .local v2, result:[B
    const/4 v1, 0x0

    .local v1, i:I
    :goto_19
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v3

    if-ge v1, v3, :cond_33

    .line 1081
    div-int/lit8 v3, v1, 0x2

    add-int/lit8 v4, v1, 0x2

    invoke-virtual {p1, v1, v4}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v4

    const/16 v5, 0x10

    invoke-static {v4, v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v4

    int-to-byte v4, v4

    aput-byte v4, v2, v3

    .line 1080
    add-int/lit8 v1, v1, 0x2

    goto :goto_19

    .line 1083
    :cond_33
    return-object v2
.end method

.method private initPackageTracking()V
    .registers 23

    .prologue
    .line 920
    const-string v19, "BackupManagerService"

    const-string v20, "Initializing package tracking"

    invoke-static/range {v19 .. v20}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 923
    new-instance v19, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v20, v0

    const-string v21, "ancestral"

    invoke-direct/range {v19 .. v21}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    move-object/from16 v0, v19

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mTokenFile:Ljava/io/File;

    .line 925
    :try_start_1a
    new-instance v17, Ljava/io/RandomAccessFile;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mTokenFile:Ljava/io/File;

    move-object/from16 v19, v0

    const-string v20, "r"

    move-object/from16 v0, v17

    move-object/from16 v1, v19

    move-object/from16 v2, v20

    invoke-direct {v0, v1, v2}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 926
    .local v17, tf:Ljava/io/RandomAccessFile;
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->readInt()I

    move-result v18

    .line 927
    .local v18, version:I
    const/16 v19, 0x1

    move/from16 v0, v18

    move/from16 v1, v19

    if-ne v0, v1, :cond_73

    .line 928
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->readLong()J

    move-result-wide v19

    move-wide/from16 v0, v19

    move-object/from16 v2, p0

    iput-wide v0, v2, Lcom/android/server/BackupManagerService;->mAncestralToken:J

    .line 929
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->readLong()J

    move-result-wide v19

    move-wide/from16 v0, v19

    move-object/from16 v2, p0

    iput-wide v0, v2, Lcom/android/server/BackupManagerService;->mCurrentToken:J

    .line 931
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->readInt()I

    move-result v10

    .line 932
    .local v10, numPackages:I
    if-ltz v10, :cond_73

    .line 933
    new-instance v19, Ljava/util/HashSet;

    invoke-direct/range {v19 .. v19}, Ljava/util/HashSet;-><init>()V

    move-object/from16 v0, v19

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    .line 934
    const/4 v6, 0x0

    .local v6, i:I
    :goto_5f
    if-ge v6, v10, :cond_73

    .line 935
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->readUTF()Ljava/lang/String;

    move-result-object v12

    .line 936
    .local v12, pkgName:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    invoke-interface {v0, v12}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 934
    add-int/lit8 v6, v6, 0x1

    goto :goto_5f

    .line 940
    .end local v6           #i:I
    .end local v10           #numPackages:I
    .end local v12           #pkgName:Ljava/lang/String;
    :cond_73
    invoke-virtual/range {v17 .. v17}, Ljava/io/RandomAccessFile;->close()V
    :try_end_76
    .catch Ljava/io/FileNotFoundException; {:try_start_1a .. :try_end_76} :catch_f4
    .catch Ljava/io/IOException; {:try_start_1a .. :try_end_76} :catch_fe

    .line 951
    .end local v17           #tf:Ljava/io/RandomAccessFile;
    .end local v18           #version:I
    :goto_76
    new-instance v19, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v20, v0

    const-string v21, "processed"

    invoke-direct/range {v19 .. v21}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    move-object/from16 v0, v19

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    .line 952
    new-instance v16, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    move-object/from16 v19, v0

    const-string v20, "processed.new"

    move-object/from16 v0, v16

    move-object/from16 v1, v19

    move-object/from16 v2, v20

    invoke-direct {v0, v1, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 957
    .local v16, tempProcessedFile:Ljava/io/File;
    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->exists()Z

    move-result v19

    if-eqz v19, :cond_a5

    .line 958
    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->delete()Z

    .line 963
    :cond_a5
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Ljava/io/File;->exists()Z

    move-result v19

    if-eqz v19, :cond_153

    .line 964
    const/4 v14, 0x0

    .line 965
    .local v14, temp:Ljava/io/RandomAccessFile;
    const/4 v7, 0x0

    .line 968
    .local v7, in:Ljava/io/RandomAccessFile;
    :try_start_b3
    new-instance v15, Ljava/io/RandomAccessFile;

    const-string v19, "rws"

    move-object/from16 v0, v16

    move-object/from16 v1, v19

    invoke-direct {v15, v0, v1}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_be
    .catchall {:try_start_b3 .. :try_end_be} :catchall_1c0
    .catch Ljava/io/EOFException; {:try_start_b3 .. :try_end_be} :catch_10c
    .catch Ljava/io/IOException; {:try_start_b3 .. :try_end_be} :catch_1a7

    .line 969
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .local v15, temp:Ljava/io/RandomAccessFile;
    :try_start_be
    new-instance v8, Ljava/io/RandomAccessFile;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    move-object/from16 v19, v0

    const-string v20, "r"

    move-object/from16 v0, v19

    move-object/from16 v1, v20

    invoke-direct {v8, v0, v1}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_cf
    .catchall {:try_start_be .. :try_end_cf} :catchall_1d5
    .catch Ljava/io/EOFException; {:try_start_be .. :try_end_cf} :catch_1e3
    .catch Ljava/io/IOException; {:try_start_be .. :try_end_cf} :catch_1dc

    .line 973
    .end local v7           #in:Ljava/io/RandomAccessFile;
    .local v8, in:Ljava/io/RandomAccessFile;
    :goto_cf
    :try_start_cf
    invoke-virtual {v8}, Ljava/io/RandomAccessFile;->readUTF()Ljava/lang/String;
    :try_end_d2
    .catchall {:try_start_cf .. :try_end_d2} :catchall_1d8
    .catch Ljava/io/EOFException; {:try_start_cf .. :try_end_d2} :catch_1e7
    .catch Ljava/io/IOException; {:try_start_cf .. :try_end_d2} :catch_1df

    move-result-object v11

    .line 975
    .local v11, pkg:Ljava/lang/String;
    :try_start_d3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    move-object/from16 v19, v0

    const/16 v20, 0x0

    move-object/from16 v0, v19

    move/from16 v1, v20

    invoke-virtual {v0, v11, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v9

    .line 976
    .local v9, info:Landroid/content/pm/PackageInfo;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    invoke-virtual {v0, v11}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 977
    invoke-virtual {v15, v11}, Ljava/io/RandomAccessFile;->writeUTF(Ljava/lang/String;)V
    :try_end_f1
    .catchall {:try_start_d3 .. :try_end_f1} :catchall_1d8
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_d3 .. :try_end_f1} :catch_f2
    .catch Ljava/io/EOFException; {:try_start_d3 .. :try_end_f1} :catch_1e7
    .catch Ljava/io/IOException; {:try_start_d3 .. :try_end_f1} :catch_1df

    goto :goto_cf

    .line 979
    .end local v9           #info:Landroid/content/pm/PackageInfo;
    :catch_f2
    move-exception v19

    goto :goto_cf

    .line 941
    .end local v8           #in:Ljava/io/RandomAccessFile;
    .end local v11           #pkg:Ljava/lang/String;
    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .end local v16           #tempProcessedFile:Ljava/io/File;
    :catch_f4
    move-exception v5

    .line 943
    .local v5, fnf:Ljava/io/FileNotFoundException;
    const-string v19, "BackupManagerService"

    const-string v20, "No ancestral data"

    invoke-static/range {v19 .. v20}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_76

    .line 944
    .end local v5           #fnf:Ljava/io/FileNotFoundException;
    :catch_fe
    move-exception v3

    .line 945
    .local v3, e:Ljava/io/IOException;
    const-string v19, "BackupManagerService"

    const-string v20, "Unable to read token file"

    move-object/from16 v0, v19

    move-object/from16 v1, v20

    invoke-static {v0, v1, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_76

    .line 984
    .end local v3           #e:Ljava/io/IOException;
    .restart local v7       #in:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    .restart local v16       #tempProcessedFile:Ljava/io/File;
    :catch_10c
    move-exception v3

    .line 987
    .local v3, e:Ljava/io/EOFException;
    :goto_10d
    :try_start_10d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    move-object/from16 v19, v0

    move-object/from16 v0, v16

    move-object/from16 v1, v19

    invoke-virtual {v0, v1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v19

    if-nez v19, :cond_149

    .line 988
    const-string v19, "BackupManagerService"

    new-instance v20, Ljava/lang/StringBuilder;

    invoke-direct/range {v20 .. v20}, Ljava/lang/StringBuilder;-><init>()V

    const-string v21, "Error renaming "

    invoke-virtual/range {v20 .. v21}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v20

    move-object/from16 v0, v20

    move-object/from16 v1, v16

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v20

    const-string v21, " to "

    invoke-virtual/range {v20 .. v21}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v20

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    move-object/from16 v21, v0

    invoke-virtual/range {v20 .. v21}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v20

    invoke-static/range {v19 .. v20}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_149
    .catchall {:try_start_10d .. :try_end_149} :catchall_1c0

    .line 993
    :cond_149
    if-eqz v14, :cond_14e

    :try_start_14b
    invoke-virtual {v14}, Ljava/io/RandomAccessFile;->close()V
    :try_end_14e
    .catch Ljava/io/IOException; {:try_start_14b .. :try_end_14e} :catch_1d0

    .line 994
    :cond_14e
    :goto_14e
    if-eqz v7, :cond_153

    :try_start_150
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->close()V
    :try_end_153
    .catch Ljava/io/IOException; {:try_start_150 .. :try_end_153} :catch_1be

    .line 1000
    .end local v3           #e:Ljava/io/EOFException;
    .end local v7           #in:Ljava/io/RandomAccessFile;
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    :cond_153
    :goto_153
    new-instance v4, Landroid/content/IntentFilter;

    invoke-direct {v4}, Landroid/content/IntentFilter;-><init>()V

    .line 1001
    .local v4, filter:Landroid/content/IntentFilter;
    const-string v19, "android.intent.action.PACKAGE_ADDED"

    move-object/from16 v0, v19

    invoke-virtual {v4, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1002
    const-string v19, "android.intent.action.PACKAGE_REMOVED"

    move-object/from16 v0, v19

    invoke-virtual {v4, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1003
    const-string v19, "package"

    move-object/from16 v0, v19

    invoke-virtual {v4, v0}, Landroid/content/IntentFilter;->addDataScheme(Ljava/lang/String;)V

    .line 1004
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    move-object/from16 v19, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    move-object/from16 v20, v0

    move-object/from16 v0, v19

    move-object/from16 v1, v20

    invoke-virtual {v0, v1, v4}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 1006
    new-instance v13, Landroid/content/IntentFilter;

    invoke-direct {v13}, Landroid/content/IntentFilter;-><init>()V

    .line 1007
    .local v13, sdFilter:Landroid/content/IntentFilter;
    const-string v19, "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"

    move-object/from16 v0, v19

    invoke-virtual {v13, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1008
    const-string v19, "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"

    move-object/from16 v0, v19

    invoke-virtual {v13, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 1009
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    move-object/from16 v19, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/BackupManagerService;->mBroadcastReceiver:Landroid/content/BroadcastReceiver;

    move-object/from16 v20, v0

    move-object/from16 v0, v19

    move-object/from16 v1, v20

    invoke-virtual {v0, v1, v13}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 1010
    return-void

    .line 990
    .end local v4           #filter:Landroid/content/IntentFilter;
    .end local v13           #sdFilter:Landroid/content/IntentFilter;
    .restart local v7       #in:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    :catch_1a7
    move-exception v3

    .line 991
    .local v3, e:Ljava/io/IOException;
    :goto_1a8
    :try_start_1a8
    const-string v19, "BackupManagerService"

    const-string v20, "Error in processed file"

    move-object/from16 v0, v19

    move-object/from16 v1, v20

    invoke-static {v0, v1, v3}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_1b3
    .catchall {:try_start_1a8 .. :try_end_1b3} :catchall_1c0

    .line 993
    if-eqz v14, :cond_1b8

    :try_start_1b5
    invoke-virtual {v14}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1b8
    .catch Ljava/io/IOException; {:try_start_1b5 .. :try_end_1b8} :catch_1d3

    .line 994
    :cond_1b8
    :goto_1b8
    if-eqz v7, :cond_153

    :try_start_1ba
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1bd
    .catch Ljava/io/IOException; {:try_start_1ba .. :try_end_1bd} :catch_1be

    goto :goto_153

    .end local v3           #e:Ljava/io/IOException;
    :catch_1be
    move-exception v19

    goto :goto_153

    .line 993
    :catchall_1c0
    move-exception v19

    :goto_1c1
    if-eqz v14, :cond_1c6

    :try_start_1c3
    invoke-virtual {v14}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1c6
    .catch Ljava/io/IOException; {:try_start_1c3 .. :try_end_1c6} :catch_1cc

    .line 994
    :cond_1c6
    :goto_1c6
    if-eqz v7, :cond_1cb

    :try_start_1c8
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1cb
    .catch Ljava/io/IOException; {:try_start_1c8 .. :try_end_1cb} :catch_1ce

    .line 993
    :cond_1cb
    :goto_1cb
    throw v19

    :catch_1cc
    move-exception v20

    goto :goto_1c6

    .line 994
    :catch_1ce
    move-exception v20

    goto :goto_1cb

    .line 993
    .local v3, e:Ljava/io/EOFException;
    :catch_1d0
    move-exception v19

    goto/16 :goto_14e

    .local v3, e:Ljava/io/IOException;
    :catch_1d3
    move-exception v19

    goto :goto_1b8

    .end local v3           #e:Ljava/io/IOException;
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catchall_1d5
    move-exception v19

    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto :goto_1c1

    .end local v7           #in:Ljava/io/RandomAccessFile;
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v8       #in:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catchall_1d8
    move-exception v19

    move-object v7, v8

    .end local v8           #in:Ljava/io/RandomAccessFile;
    .restart local v7       #in:Ljava/io/RandomAccessFile;
    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto :goto_1c1

    .line 990
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catch_1dc
    move-exception v3

    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto :goto_1a8

    .end local v7           #in:Ljava/io/RandomAccessFile;
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v8       #in:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catch_1df
    move-exception v3

    move-object v7, v8

    .end local v8           #in:Ljava/io/RandomAccessFile;
    .restart local v7       #in:Ljava/io/RandomAccessFile;
    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto :goto_1a8

    .line 984
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catch_1e3
    move-exception v3

    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto/16 :goto_10d

    .end local v7           #in:Ljava/io/RandomAccessFile;
    .end local v14           #temp:Ljava/io/RandomAccessFile;
    .restart local v8       #in:Ljava/io/RandomAccessFile;
    .restart local v15       #temp:Ljava/io/RandomAccessFile;
    :catch_1e7
    move-exception v3

    move-object v7, v8

    .end local v8           #in:Ljava/io/RandomAccessFile;
    .restart local v7       #in:Ljava/io/RandomAccessFile;
    move-object v14, v15

    .end local v15           #temp:Ljava/io/RandomAccessFile;
    .restart local v14       #temp:Ljava/io/RandomAccessFile;
    goto/16 :goto_10d
.end method

.method private makeKeyChecksum([B[BI)[B
    .registers 8
    .parameter "pwBytes"
    .parameter "salt"
    .parameter "rounds"

    .prologue
    .line 1087
    array-length v3, p1

    new-array v2, v3, [C

    .line 1088
    .local v2, mkAsChar:[C
    const/4 v1, 0x0

    .local v1, i:I
    :goto_4
    array-length v3, p1

    if-ge v1, v3, :cond_f

    .line 1089
    aget-byte v3, p1, v1

    int-to-char v3, v3

    aput-char v3, v2, v1

    .line 1088
    add-int/lit8 v1, v1, 0x1

    goto :goto_4

    .line 1092
    :cond_f
    invoke-direct {p0, v2, p2, p3}, Lcom/android/server/BackupManagerService;->buildCharArrayKey([C[BI)Ljavax/crypto/SecretKey;

    move-result-object v0

    .line 1093
    .local v0, checksum:Ljava/security/Key;
    invoke-interface {v0}, Ljava/security/Key;->getEncoded()[B

    move-result-object v3

    return-object v3
.end method

.method private parseLeftoverJournals()V
    .registers 12

    .prologue
    .line 1013
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mJournalDir:Ljava/io/File;

    invoke-virtual {v8}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v0

    .local v0, arr$:[Ljava/io/File;
    array-length v6, v0

    .local v6, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_8
    if-ge v3, v6, :cond_7f

    aget-object v2, v0, v3

    .line 1014
    .local v2, f:Ljava/io/File;
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    if-eqz v8, :cond_18

    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    invoke-virtual {v2, v8}, Ljava/io/File;->compareTo(Ljava/io/File;)I

    move-result v8

    if-eqz v8, :cond_51

    .line 1018
    :cond_18
    const/4 v4, 0x0

    .line 1020
    .local v4, in:Ljava/io/RandomAccessFile;
    :try_start_19
    const-string v8, "BackupManagerService"

    const-string v9, "Found stale backup journal, scheduling"

    invoke-static {v8, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1021
    new-instance v5, Ljava/io/RandomAccessFile;

    const-string v8, "r"

    invoke-direct {v5, v2, v8}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_27
    .catchall {:try_start_19 .. :try_end_27} :catchall_75
    .catch Ljava/io/EOFException; {:try_start_19 .. :try_end_27} :catch_8a
    .catch Ljava/lang/Exception; {:try_start_19 .. :try_end_27} :catch_54

    .line 1023
    .end local v4           #in:Ljava/io/RandomAccessFile;
    .local v5, in:Ljava/io/RandomAccessFile;
    :goto_27
    :try_start_27
    invoke-virtual {v5}, Ljava/io/RandomAccessFile;->readUTF()Ljava/lang/String;

    move-result-object v7

    .line 1024
    .local v7, packageName:Ljava/lang/String;
    const-string v8, "BackupManagerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1025
    invoke-direct {p0, v7}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;)V
    :try_end_46
    .catchall {:try_start_27 .. :try_end_46} :catchall_84
    .catch Ljava/io/EOFException; {:try_start_27 .. :try_end_46} :catch_47
    .catch Ljava/lang/Exception; {:try_start_27 .. :try_end_46} :catch_87

    goto :goto_27

    .line 1027
    .end local v7           #packageName:Ljava/lang/String;
    :catch_47
    move-exception v8

    move-object v4, v5

    .line 1033
    .end local v5           #in:Ljava/io/RandomAccessFile;
    .restart local v4       #in:Ljava/io/RandomAccessFile;
    :goto_49
    if-eqz v4, :cond_4e

    :try_start_4b
    invoke-virtual {v4}, Ljava/io/RandomAccessFile;->close()V
    :try_end_4e
    .catch Ljava/io/IOException; {:try_start_4b .. :try_end_4e} :catch_80

    .line 1034
    :cond_4e
    :goto_4e
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    .line 1013
    .end local v4           #in:Ljava/io/RandomAccessFile;
    :cond_51
    add-int/lit8 v3, v3, 0x1

    goto :goto_8

    .line 1029
    .restart local v4       #in:Ljava/io/RandomAccessFile;
    :catch_54
    move-exception v1

    .line 1030
    .local v1, e:Ljava/lang/Exception;
    :goto_55
    :try_start_55
    const-string v8, "BackupManagerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Can\'t read "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9, v1}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_6d
    .catchall {:try_start_55 .. :try_end_6d} :catchall_75

    .line 1033
    if-eqz v4, :cond_4e

    :try_start_6f
    invoke-virtual {v4}, Ljava/io/RandomAccessFile;->close()V
    :try_end_72
    .catch Ljava/io/IOException; {:try_start_6f .. :try_end_72} :catch_73

    goto :goto_4e

    :catch_73
    move-exception v8

    goto :goto_4e

    .end local v1           #e:Ljava/lang/Exception;
    :catchall_75
    move-exception v8

    :goto_76
    if-eqz v4, :cond_7b

    :try_start_78
    invoke-virtual {v4}, Ljava/io/RandomAccessFile;->close()V
    :try_end_7b
    .catch Ljava/io/IOException; {:try_start_78 .. :try_end_7b} :catch_82

    .line 1034
    :cond_7b
    :goto_7b
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    .line 1033
    throw v8

    .line 1038
    .end local v2           #f:Ljava/io/File;
    .end local v4           #in:Ljava/io/RandomAccessFile;
    :cond_7f
    return-void

    .line 1033
    .restart local v2       #f:Ljava/io/File;
    .restart local v4       #in:Ljava/io/RandomAccessFile;
    :catch_80
    move-exception v8

    goto :goto_4e

    :catch_82
    move-exception v9

    goto :goto_7b

    .end local v4           #in:Ljava/io/RandomAccessFile;
    .restart local v5       #in:Ljava/io/RandomAccessFile;
    :catchall_84
    move-exception v8

    move-object v4, v5

    .end local v5           #in:Ljava/io/RandomAccessFile;
    .restart local v4       #in:Ljava/io/RandomAccessFile;
    goto :goto_76

    .line 1029
    .end local v4           #in:Ljava/io/RandomAccessFile;
    .restart local v5       #in:Ljava/io/RandomAccessFile;
    :catch_87
    move-exception v1

    move-object v4, v5

    .end local v5           #in:Ljava/io/RandomAccessFile;
    .restart local v4       #in:Ljava/io/RandomAccessFile;
    goto :goto_55

    .line 1027
    :catch_8a
    move-exception v8

    goto :goto_49
.end method

.method private randomBytes(I)[B
    .registers 4
    .parameter "bits"

    .prologue
    .line 1098
    div-int/lit8 v1, p1, 0x8

    new-array v0, v1, [B

    .line 1099
    .local v0, array:[B
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mRng:Ljava/security/SecureRandom;

    invoke-virtual {v1, v0}, Ljava/security/SecureRandom;->nextBytes([B)V

    .line 1100
    return-object v0
.end method

.method private registerTransport(Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;)V
    .registers 14
    .parameter "name"
    .parameter "transport"

    .prologue
    .line 1296
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v6

    .line 1297
    :try_start_3
    const-string v5, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Registering transport "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v5, v7}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1298
    if-eqz p2, :cond_62

    .line 1299
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v5, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1308
    monitor-exit v6
    :try_end_2d
    .catchall {:try_start_3 .. :try_end_2d} :catchall_78

    .line 1314
    :try_start_2d
    invoke-interface {p2}, Lcom/android/internal/backup/IBackupTransport;->transportDirName()Ljava/lang/String;

    move-result-object v4

    .line 1315
    .local v4, transportName:Ljava/lang/String;
    new-instance v3, Ljava/io/File;

    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    invoke-direct {v3, v5, v4}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 1316
    .local v3, stateDir:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->mkdirs()Z

    .line 1318
    new-instance v2, Ljava/io/File;

    const-string v5, "_need_init_"

    invoke-direct {v2, v3, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 1319
    .local v2, initSentinel:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_61

    .line 1320
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v6
    :try_end_4b
    .catch Landroid/os/RemoteException; {:try_start_2d .. :try_end_4b} :catch_7e

    .line 1321
    :try_start_4b
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    invoke-virtual {v5, v4}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 1324
    const-wide/32 v0, 0xea60

    .line 1325
    .local v0, delay:J
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    const/4 v7, 0x0

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    add-long/2addr v8, v0

    iget-object v10, p0, Lcom/android/server/BackupManagerService;->mRunInitIntent:Landroid/app/PendingIntent;

    invoke-virtual {v5, v7, v8, v9, v10}, Landroid/app/AlarmManager;->set(IJLandroid/app/PendingIntent;)V

    .line 1327
    monitor-exit v6
    :try_end_61
    .catchall {:try_start_4b .. :try_end_61} :catchall_7b

    .line 1332
    .end local v0           #delay:J
    .end local v2           #initSentinel:Ljava/io/File;
    .end local v3           #stateDir:Ljava/io/File;
    .end local v4           #transportName:Ljava/lang/String;
    :cond_61
    :goto_61
    return-void

    .line 1301
    :cond_62
    :try_start_62
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v5, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 1302
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    if-eqz v5, :cond_76

    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    invoke-virtual {v5, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_76

    .line 1303
    const/4 v5, 0x0

    iput-object v5, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 1306
    :cond_76
    monitor-exit v6

    goto :goto_61

    .line 1308
    :catchall_78
    move-exception v5

    monitor-exit v6
    :try_end_7a
    .catchall {:try_start_62 .. :try_end_7a} :catchall_78

    throw v5

    .line 1327
    .restart local v2       #initSentinel:Ljava/io/File;
    .restart local v3       #stateDir:Ljava/io/File;
    .restart local v4       #transportName:Ljava/lang/String;
    :catchall_7b
    move-exception v5

    :try_start_7c
    monitor-exit v6
    :try_end_7d
    .catchall {:try_start_7c .. :try_end_7d} :catchall_7b

    :try_start_7d
    throw v5
    :try_end_7e
    .catch Landroid/os/RemoteException; {:try_start_7d .. :try_end_7e} :catch_7e

    .line 1329
    .end local v2           #initSentinel:Ljava/io/File;
    .end local v3           #stateDir:Ljava/io/File;
    .end local v4           #transportName:Ljava/lang/String;
    :catch_7e
    move-exception v5

    goto :goto_61
.end method

.method private removePackageFromSetLocked(Ljava/util/HashSet;Ljava/lang/String;)V
    .registers 4
    .parameter
    .parameter "packageName"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 1472
    .local p1, set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    invoke-virtual {p1, p2}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_c

    .line 1477
    invoke-virtual {p0, p2}, Lcom/android/server/BackupManagerService;->removeEverBackedUp(Ljava/lang/String;)V

    .line 1478
    invoke-virtual {p1, p2}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    .line 1480
    :cond_c
    return-void
.end method

.method private signaturesMatch([Landroid/content/pm/Signature;Landroid/content/pm/PackageInfo;)Z
    .registers 13
    .parameter "storedSigs"
    .parameter "target"

    .prologue
    const/4 v7, 0x0

    const/4 v6, 0x1

    .line 4085
    iget-object v8, p2, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v8, v8, Landroid/content/pm/ApplicationInfo;->flags:I

    and-int/lit8 v8, v8, 0x1

    if-eqz v8, :cond_2b

    .line 4086
    const-string v7, "BackupManagerService"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "System app "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v9, p2, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " - skipping sig check"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4121
    :cond_2a
    :goto_2a
    return v6

    .line 4092
    :cond_2b
    iget-object v0, p2, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    .line 4095
    .local v0, deviceSigs:[Landroid/content/pm/Signature;
    if-eqz p1, :cond_32

    array-length v8, p1

    if-nez v8, :cond_37

    :cond_32
    if-eqz v0, :cond_2a

    array-length v8, v0

    if-eqz v8, :cond_2a

    .line 4099
    :cond_37
    if-eqz p1, :cond_3b

    if-nez v0, :cond_3d

    :cond_3b
    move v6, v7

    .line 4100
    goto :goto_2a

    .line 4106
    :cond_3d
    array-length v5, p1

    .line 4107
    .local v5, nStored:I
    array-length v4, v0

    .line 4109
    .local v4, nDevice:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_40
    if-ge v1, v5, :cond_2a

    .line 4110
    const/4 v3, 0x0

    .line 4111
    .local v3, match:Z
    const/4 v2, 0x0

    .local v2, j:I
    :goto_44
    if-ge v2, v4, :cond_51

    .line 4112
    aget-object v8, p1, v1

    aget-object v9, v0, v2

    invoke-virtual {v8, v9}, Landroid/content/pm/Signature;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_55

    .line 4113
    const/4 v3, 0x1

    .line 4117
    :cond_51
    if-nez v3, :cond_58

    move v6, v7

    .line 4118
    goto :goto_2a

    .line 4111
    :cond_55
    add-int/lit8 v2, v2, 0x1

    goto :goto_44

    .line 4109
    :cond_58
    add-int/lit8 v1, v1, 0x1

    goto :goto_40
.end method

.method private startBackupAlarmsLocked(J)V
    .registers 13
    .parameter "delayBeforeFirstBackup"

    .prologue
    const v6, 0x493e0

    .line 5283
    new-instance v7, Ljava/util/Random;

    invoke-direct {v7}, Ljava/util/Random;-><init>()V

    .line 5284
    .local v7, random:Ljava/util/Random;
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    add-long/2addr v0, p1

    invoke-virtual {v7, v6}, Ljava/util/Random;->nextInt(I)I

    move-result v4

    int-to-long v4, v4

    add-long v2, v0, v4

    .line 5286
    .local v2, when:J
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    const/4 v1, 0x0

    const-wide/32 v4, 0x36ee80

    invoke-virtual {v7, v6}, Ljava/util/Random;->nextInt(I)I

    move-result v6

    int-to-long v8, v6

    add-long/2addr v4, v8

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mRunBackupIntent:Landroid/app/PendingIntent;

    invoke-virtual/range {v0 .. v6}, Landroid/app/AlarmManager;->setRepeating(IJJLandroid/app/PendingIntent;)V

    .line 5288
    iput-wide v2, p0, Lcom/android/server/BackupManagerService;->mNextBackupPass:J

    .line 5289
    return-void
.end method

.method private writeToJournalLocked(Ljava/lang/String;)V
    .registers 8
    .parameter "str"

    .prologue
    .line 4860
    const/4 v1, 0x0

    .line 4862
    .local v1, out:Ljava/io/RandomAccessFile;
    :try_start_1
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    if-nez v3, :cond_10

    const-string v3, "journal"

    const/4 v4, 0x0

    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mJournalDir:Ljava/io/File;

    invoke-static {v3, v4, v5}, Ljava/io/File;->createTempFile(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/io/File;

    move-result-object v3

    iput-object v3, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    .line 4863
    :cond_10
    new-instance v2, Ljava/io/RandomAccessFile;

    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;

    const-string v4, "rws"

    invoke-direct {v2, v3, v4}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_19
    .catchall {:try_start_1 .. :try_end_19} :catchall_54
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_19} :catch_2a

    .line 4864
    .end local v1           #out:Ljava/io/RandomAccessFile;
    .local v2, out:Ljava/io/RandomAccessFile;
    :try_start_19
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->length()J

    move-result-wide v3

    invoke-virtual {v2, v3, v4}, Ljava/io/RandomAccessFile;->seek(J)V

    .line 4865
    invoke-virtual {v2, p1}, Ljava/io/RandomAccessFile;->writeUTF(Ljava/lang/String;)V
    :try_end_23
    .catchall {:try_start_19 .. :try_end_23} :catchall_5f
    .catch Ljava/io/IOException; {:try_start_19 .. :try_end_23} :catch_62

    .line 4870
    if-eqz v2, :cond_28

    :try_start_25
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->close()V
    :try_end_28
    .catch Ljava/io/IOException; {:try_start_25 .. :try_end_28} :catch_5d

    :cond_28
    :goto_28
    move-object v1, v2

    .line 4872
    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    :cond_29
    :goto_29
    return-void

    .line 4866
    :catch_2a
    move-exception v0

    .line 4867
    .local v0, e:Ljava/io/IOException;
    :goto_2b
    :try_start_2b
    const-string v3, "BackupManagerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Can\'t write "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " to backup journal"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 4868
    const/4 v3, 0x0

    iput-object v3, p0, Lcom/android/server/BackupManagerService;->mJournal:Ljava/io/File;
    :try_end_4c
    .catchall {:try_start_2b .. :try_end_4c} :catchall_54

    .line 4870
    if-eqz v1, :cond_29

    :try_start_4e
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_51
    .catch Ljava/io/IOException; {:try_start_4e .. :try_end_51} :catch_52

    goto :goto_29

    :catch_52
    move-exception v3

    goto :goto_29

    .end local v0           #e:Ljava/io/IOException;
    :catchall_54
    move-exception v3

    :goto_55
    if-eqz v1, :cond_5a

    :try_start_57
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_5a
    .catch Ljava/io/IOException; {:try_start_57 .. :try_end_5a} :catch_5b

    :cond_5a
    :goto_5a
    throw v3

    :catch_5b
    move-exception v4

    goto :goto_5a

    .end local v1           #out:Ljava/io/RandomAccessFile;
    .restart local v2       #out:Ljava/io/RandomAccessFile;
    :catch_5d
    move-exception v3

    goto :goto_28

    :catchall_5f
    move-exception v3

    move-object v1, v2

    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    goto :goto_55

    .line 4866
    .end local v1           #out:Ljava/io/RandomAccessFile;
    .restart local v2       #out:Ljava/io/RandomAccessFile;
    :catch_62
    move-exception v0

    move-object v1, v2

    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    goto :goto_2b
.end method


# virtual methods
.method public acknowledgeFullBackupOrRestore(IZLjava/lang/String;Ljava/lang/String;Landroid/app/backup/IFullBackupRestoreObserver;)V
    .registers 18
    .parameter "token"
    .parameter "allow"
    .parameter "curPassword"
    .parameter "encPpassword"
    .parameter "observer"

    .prologue
    .line 5156
    const-string v8, "BackupManagerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "acknowledgeFullBackupOrRestore : token="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " allow="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5161
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v9, "android.permission.BACKUP"

    const-string v10, "acknowledgeFullBackupOrRestore"

    invoke-virtual {v8, v9, v10}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5163
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v4

    .line 5167
    .local v4, oldId:J
    :try_start_2f
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    monitor-enter v9
    :try_end_32
    .catchall {:try_start_2f .. :try_end_32} :catchall_b9

    .line 5168
    :try_start_32
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v8, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lcom/android/server/BackupManagerService$FullParams;

    .line 5169
    .local v6, params:Lcom/android/server/BackupManagerService$FullParams;
    if-eqz v6, :cond_be

    .line 5170
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/16 v10, 0x9

    invoke-virtual {v8, v10, v6}, Lcom/android/server/BackupManagerService$BackupHandler;->removeMessages(ILjava/lang/Object;)V

    .line 5171
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v8, p1}, Landroid/util/SparseArray;->delete(I)V

    .line 5173
    if-eqz p2, :cond_ab

    .line 5174
    instance-of v8, v6, Lcom/android/server/BackupManagerService$FullBackupParams;

    if-eqz v8, :cond_99

    const/4 v7, 0x2

    .line 5178
    .local v7, verb:I
    :goto_4f
    move-object/from16 v0, p5

    iput-object v0, v6, Lcom/android/server/BackupManagerService$FullParams;->observer:Landroid/app/backup/IFullBackupRestoreObserver;

    .line 5179
    iput-object p3, v6, Lcom/android/server/BackupManagerService$FullParams;->curPassword:Ljava/lang/String;
    :try_end_55
    .catchall {:try_start_32 .. :try_end_55} :catchall_b6

    .line 5183
    :try_start_55
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mMountService:Landroid/os/storage/IMountService;

    invoke-interface {v8}, Landroid/os/storage/IMountService;->getEncryptionState()I

    move-result v8

    const/4 v10, 0x1

    if-eq v8, v10, :cond_9c

    const/4 v2, 0x1

    .line 5184
    .local v2, isEncrypted:Z
    :goto_5f
    if-eqz v2, :cond_68

    const-string v8, "BackupManagerService"

    const-string v10, "Device is encrypted; forcing enc password"

    invoke-static {v8, v10}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_68
    .catchall {:try_start_55 .. :try_end_68} :catchall_b6
    .catch Landroid/os/RemoteException; {:try_start_55 .. :try_end_68} :catch_9e

    .line 5190
    :cond_68
    :goto_68
    if-eqz v2, :cond_a8

    .end local p3
    :goto_6a
    :try_start_6a
    iput-object p3, v6, Lcom/android/server/BackupManagerService$FullParams;->encryptPassword:Ljava/lang/String;

    .line 5192
    const-string v8, "BackupManagerService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Sending conf message with verb "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v10}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5193
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v8}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5194
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v8, v7, v6}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v3

    .line 5195
    .local v3, msg:Landroid/os/Message;
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v8, v3}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5204
    .end local v2           #isEncrypted:Z
    .end local v3           #msg:Landroid/os/Message;
    .end local v7           #verb:I
    :goto_94
    monitor-exit v9
    :try_end_95
    .catchall {:try_start_6a .. :try_end_95} :catchall_b6

    .line 5206
    invoke-static {v4, v5}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5208
    return-void

    .line 5174
    .restart local p3
    :cond_99
    const/16 v7, 0xa

    goto :goto_4f

    .line 5183
    .restart local v7       #verb:I
    :cond_9c
    const/4 v2, 0x0

    goto :goto_5f

    .line 5185
    :catch_9e
    move-exception v1

    .line 5187
    .local v1, e:Landroid/os/RemoteException;
    :try_start_9f
    const-string v8, "BackupManagerService"

    const-string v10, "Unable to contact mount service!"

    invoke-static {v8, v10}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 5188
    const/4 v2, 0x1

    .restart local v2       #isEncrypted:Z
    goto :goto_68

    .end local v1           #e:Landroid/os/RemoteException;
    :cond_a8
    move-object/from16 p3, p4

    .line 5190
    goto :goto_6a

    .line 5197
    .end local v2           #isEncrypted:Z
    .end local v7           #verb:I
    :cond_ab
    const-string v8, "BackupManagerService"

    const-string v10, "User rejected full backup/restore operation"

    invoke-static {v8, v10}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5199
    invoke-virtual {p0, v6}, Lcom/android/server/BackupManagerService;->signalFullBackupRestoreCompletion(Lcom/android/server/BackupManagerService$FullParams;)V

    goto :goto_94

    .line 5204
    .end local v6           #params:Lcom/android/server/BackupManagerService$FullParams;
    .end local p3
    :catchall_b6
    move-exception v8

    monitor-exit v9
    :try_end_b8
    .catchall {:try_start_9f .. :try_end_b8} :catchall_b6

    :try_start_b8
    throw v8
    :try_end_b9
    .catchall {:try_start_b8 .. :try_end_b9} :catchall_b9

    .line 5206
    :catchall_b9
    move-exception v8

    invoke-static {v4, v5}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v8

    .line 5202
    .restart local v6       #params:Lcom/android/server/BackupManagerService$FullParams;
    .restart local p3
    :cond_be
    :try_start_be
    const-string v8, "BackupManagerService"

    const-string v10, "Attempted to ack full backup/restore with invalid token"

    invoke-static {v8, v10}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_c5
    .catchall {:try_start_be .. :try_end_c5} :catchall_b6

    goto :goto_94
.end method

.method addBackupTrace(Ljava/lang/String;)V
    .registers 4
    .parameter "s"

    .prologue
    .line 697
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    monitor-enter v1

    .line 698
    :try_start_3
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 699
    monitor-exit v1

    .line 701
    return-void

    .line 699
    :catchall_a
    move-exception v0

    monitor-exit v1
    :try_end_c
    .catchall {:try_start_3 .. :try_end_c} :catchall_a

    throw v0
.end method

.method addPackageParticipantsLocked([Ljava/lang/String;)V
    .registers 10
    .parameter "packageNames"

    .prologue
    .line 1409
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->allAgentPackages()Ljava/util/List;

    move-result-object v4

    .line 1410
    .local v4, targetApps:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/PackageInfo;>;"
    if-eqz p1, :cond_2c

    .line 1411
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "addPackageParticipantsLocked: #"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    array-length v7, p1

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1412
    move-object v0, p1

    .local v0, arr$:[Ljava/lang/String;
    array-length v2, v0

    .local v2, len$:I
    const/4 v1, 0x0

    .local v1, i$:I
    :goto_22
    if-ge v1, v2, :cond_37

    aget-object v3, v0, v1

    .line 1413
    .local v3, packageName:Ljava/lang/String;
    invoke-direct {p0, v3, v4}, Lcom/android/server/BackupManagerService;->addPackageParticipantsLockedInner(Ljava/lang/String;Ljava/util/List;)V

    .line 1412
    add-int/lit8 v1, v1, 0x1

    goto :goto_22

    .line 1416
    .end local v0           #arr$:[Ljava/lang/String;
    .end local v1           #i$:I
    .end local v2           #len$:I
    .end local v3           #packageName:Ljava/lang/String;
    :cond_2c
    const-string v5, "BackupManagerService"

    const-string v6, "addPackageParticipantsLocked: all"

    invoke-static {v5, v6}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1417
    const/4 v5, 0x0

    invoke-direct {p0, v5, v4}, Lcom/android/server/BackupManagerService;->addPackageParticipantsLockedInner(Ljava/lang/String;Ljava/util/List;)V

    .line 1419
    :cond_37
    return-void
.end method

.method public agentConnected(Ljava/lang/String;Landroid/os/IBinder;)V
    .registers 8
    .parameter "packageName"
    .parameter "agentBinder"

    .prologue
    .line 5398
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    monitor-enter v2

    .line 5399
    :try_start_3
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    const/16 v3, 0x3e8

    if-ne v1, v3, :cond_3d

    .line 5400
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "agentConnected pkg="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " agent="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5401
    invoke-static {p2}, Landroid/app/IBackupAgent$Stub;->asInterface(Landroid/os/IBinder;)Landroid/app/IBackupAgent;

    move-result-object v0

    .line 5402
    .local v0, agent:Landroid/app/IBackupAgent;
    iput-object v0, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;

    .line 5403
    const/4 v1, 0x0

    iput-boolean v1, p0, Lcom/android/server/BackupManagerService;->mConnecting:Z

    .line 5408
    .end local v0           #agent:Landroid/app/IBackupAgent;
    :goto_36
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    invoke-virtual {v1}, Ljava/lang/Object;->notifyAll()V

    .line 5409
    monitor-exit v2

    .line 5410
    return-void

    .line 5405
    :cond_3d
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Non-system process uid="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " claiming agent connected"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_36

    .line 5409
    :catchall_60
    move-exception v1

    monitor-exit v2
    :try_end_62
    .catchall {:try_start_3 .. :try_end_62} :catchall_60

    throw v1
.end method

.method public agentDisconnected(Ljava/lang/String;)V
    .registers 6
    .parameter "packageName"

    .prologue
    .line 5417
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    monitor-enter v1

    .line 5418
    :try_start_3
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    const/16 v2, 0x3e8

    if-ne v0, v2, :cond_18

    .line 5419
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;

    .line 5420
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/server/BackupManagerService;->mConnecting:Z

    .line 5425
    :goto_11
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    invoke-virtual {v0}, Ljava/lang/Object;->notifyAll()V

    .line 5426
    monitor-exit v1

    .line 5427
    return-void

    .line 5422
    :cond_18
    const-string v0, "BackupManagerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Non-system process uid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " claiming agent disconnected"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_11

    .line 5426
    :catchall_3b
    move-exception v0

    monitor-exit v1
    :try_end_3d
    .catchall {:try_start_3 .. :try_end_3d} :catchall_3b

    throw v0
.end method

.method allAgentPackages()Ljava/util/List;
    .registers 11
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List",
            "<",
            "Landroid/content/pm/PackageInfo;",
            ">;"
        }
    .end annotation

    .prologue
    .line 1485
    const/16 v4, 0x40

    .line 1486
    .local v4, flags:I
    iget-object v7, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    invoke-virtual {v7, v4}, Landroid/content/pm/PackageManager;->getInstalledPackages(I)Ljava/util/List;

    move-result-object v5

    .line 1487
    .local v5, packages:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/PackageInfo;>;"
    invoke-interface {v5}, Ljava/util/List;->size()I

    move-result v0

    .line 1488
    .local v0, N:I
    add-int/lit8 v1, v0, -0x1

    .local v1, a:I
    :goto_e
    if-ltz v1, :cond_40

    .line 1489
    invoke-interface {v5, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/content/pm/PackageInfo;

    .line 1491
    .local v6, pkg:Landroid/content/pm/PackageInfo;
    :try_start_16
    iget-object v2, v6, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    .line 1492
    .local v2, app:Landroid/content/pm/ApplicationInfo;
    iget v7, v2, Landroid/content/pm/ApplicationInfo;->flags:I

    const v8, 0x8000

    and-int/2addr v7, v8

    if-eqz v7, :cond_24

    iget-object v7, v2, Landroid/content/pm/ApplicationInfo;->backupAgentName:Ljava/lang/String;

    if-nez v7, :cond_2a

    .line 1494
    :cond_24
    invoke-interface {v5, v1}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    .line 1488
    .end local v2           #app:Landroid/content/pm/ApplicationInfo;
    :goto_27
    add-int/lit8 v1, v1, -0x1

    goto :goto_e

    .line 1498
    .restart local v2       #app:Landroid/content/pm/ApplicationInfo;
    :cond_2a
    iget-object v7, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    iget-object v8, v6, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    const/16 v9, 0x400

    invoke-virtual {v7, v8, v9}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v2

    .line 1500
    iget-object v7, v6, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v8, v2, Landroid/content/pm/ApplicationInfo;->sharedLibraryFiles:[Ljava/lang/String;

    iput-object v8, v7, Landroid/content/pm/ApplicationInfo;->sharedLibraryFiles:[Ljava/lang/String;
    :try_end_3a
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_16 .. :try_end_3a} :catch_3b

    goto :goto_27

    .line 1502
    .end local v2           #app:Landroid/content/pm/ApplicationInfo;
    :catch_3b
    move-exception v3

    .line 1503
    .local v3, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    invoke-interface {v5, v1}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    goto :goto_27

    .line 1506
    .end local v3           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v6           #pkg:Landroid/content/pm/PackageInfo;
    :cond_40
    return-object v5
.end method

.method public backupNow()V
    .registers 6

    .prologue
    .line 4940
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.BACKUP"

    const-string v3, "backupNow"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 4942
    const-string v1, "BackupManagerService"

    const-string v2, "Scheduling immediate backup pass"

    invoke-static {v1, v2}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4943
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v2

    .line 4947
    const-wide/32 v3, 0x36ee80

    :try_start_16
    invoke-direct {p0, v3, v4}, Lcom/android/server/BackupManagerService;->startBackupAlarmsLocked(J)V
    :try_end_19
    .catchall {:try_start_16 .. :try_end_19} :catchall_29

    .line 4949
    :try_start_19
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mRunBackupIntent:Landroid/app/PendingIntent;

    invoke-virtual {v1}, Landroid/app/PendingIntent;->send()V
    :try_end_1e
    .catchall {:try_start_19 .. :try_end_1e} :catchall_29
    .catch Landroid/app/PendingIntent$CanceledException; {:try_start_19 .. :try_end_1e} :catch_20

    .line 4954
    :goto_1e
    :try_start_1e
    monitor-exit v2

    .line 4955
    return-void

    .line 4950
    :catch_20
    move-exception v0

    .line 4952
    .local v0, e:Landroid/app/PendingIntent$CanceledException;
    const-string v1, "BackupManagerService"

    const-string v3, "run-backup intent cancelled!"

    invoke-static {v1, v3}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1e

    .line 4954
    .end local v0           #e:Landroid/app/PendingIntent$CanceledException;
    :catchall_29
    move-exception v1

    monitor-exit v2
    :try_end_2b
    .catchall {:try_start_1e .. :try_end_2b} :catchall_29

    throw v1
.end method

.method public beginRestoreSession(Ljava/lang/String;Ljava/lang/String;)Landroid/app/backup/IRestoreSession;
    .registers 10
    .parameter "packageName"
    .parameter "transport"

    .prologue
    .line 5470
    const-string v3, "BackupManagerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "beginRestoreSession: pkg="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " transport="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5473
    const/4 v1, 0x1

    .line 5474
    .local v1, needPermission:Z
    if-nez p2, :cond_3c

    .line 5475
    iget-object p2, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 5477
    if-eqz p1, :cond_3c

    .line 5478
    const/4 v0, 0x0

    .line 5480
    .local v0, app:Landroid/content/pm/PackageInfo;
    :try_start_2a
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    const/4 v4, 0x0

    invoke-virtual {v3, p1, v4}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    :try_end_30
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_2a .. :try_end_30} :catch_56

    move-result-object v0

    .line 5486
    iget-object v3, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v3, v3, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v4

    if-ne v3, v4, :cond_3c

    .line 5490
    const/4 v1, 0x0

    .line 5495
    .end local v0           #app:Landroid/content/pm/PackageInfo;
    :cond_3c
    if-eqz v1, :cond_8e

    .line 5496
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v4, "android.permission.BACKUP"

    const-string v5, "beginRestoreSession"

    invoke-virtual {v3, v4, v5}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5502
    :goto_47
    monitor-enter p0

    .line 5503
    :try_start_48
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    if-eqz v3, :cond_96

    .line 5504
    const-string v3, "BackupManagerService"

    const-string v4, "Restore session requested but one already active"

    invoke-static {v3, v4}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5505
    const/4 v3, 0x0

    monitor-exit p0
    :try_end_55
    .catchall {:try_start_48 .. :try_end_55} :catchall_ab

    .line 5510
    :goto_55
    return-object v3

    .line 5481
    .restart local v0       #app:Landroid/content/pm/PackageInfo;
    :catch_56
    move-exception v2

    .line 5482
    .local v2, nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v3, "BackupManagerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Asked to restore nonexistent pkg "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5483
    new-instance v3, Ljava/lang/IllegalArgumentException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Package "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " not found"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 5499
    .end local v0           #app:Landroid/content/pm/PackageInfo;
    .end local v2           #nnf:Landroid/content/pm/PackageManager$NameNotFoundException;
    :cond_8e
    const-string v3, "BackupManagerService"

    const-string v4, "restoring self on current transport; no permission needed"

    invoke-static {v3, v4}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_47

    .line 5507
    :cond_96
    :try_start_96
    new-instance v3, Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    invoke-direct {v3, p0, p1, p2}, Lcom/android/server/BackupManagerService$ActiveRestoreSession;-><init>(Lcom/android/server/BackupManagerService;Ljava/lang/String;Ljava/lang/String;)V

    iput-object v3, p0, Lcom/android/server/BackupManagerService;->mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    .line 5508
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/16 v4, 0x8

    const-wide/32 v5, 0xea60

    invoke-virtual {v3, v4, v5, v6}, Lcom/android/server/BackupManagerService$BackupHandler;->sendEmptyMessageDelayed(IJ)Z

    .line 5509
    monitor-exit p0
    :try_end_a8
    .catchall {:try_start_96 .. :try_end_a8} :catchall_ab

    .line 5510
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    goto :goto_55

    .line 5509
    :catchall_ab
    move-exception v3

    :try_start_ac
    monitor-exit p0
    :try_end_ad
    .catchall {:try_start_ac .. :try_end_ad} :catchall_ab

    throw v3
.end method

.method bindToAgentSynchronous(Landroid/content/pm/ApplicationInfo;I)Landroid/app/IBackupAgent;
    .registers 14
    .parameter "app"
    .parameter "mode"

    .prologue
    const/4 v10, 0x1

    const/4 v4, 0x0

    .line 1612
    const/4 v0, 0x0

    .line 1613
    .local v0, agent:Landroid/app/IBackupAgent;
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    monitor-enter v5

    .line 1614
    const/4 v6, 0x1

    :try_start_7
    iput-boolean v6, p0, Lcom/android/server/BackupManagerService;->mConnecting:Z

    .line 1615
    const/4 v6, 0x0

    iput-object v6, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;
    :try_end_c
    .catchall {:try_start_7 .. :try_end_c} :catchall_85

    .line 1617
    :try_start_c
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;

    invoke-interface {v6, p1, p2}, Landroid/app/IActivityManager;->bindBackupAgent(Landroid/content/pm/ApplicationInfo;I)Z

    move-result v6

    if-eqz v6, :cond_a4

    .line 1618
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "awaiting agent for "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1622
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v6

    const-wide/16 v8, 0x2710

    add-long v2, v6, v8

    .line 1624
    .local v2, timeoutMark:J
    :goto_34
    iget-boolean v6, p0, Lcom/android/server/BackupManagerService;->mConnecting:Z

    if-eqz v6, :cond_67

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;

    if-nez v6, :cond_67

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J
    :try_end_3f
    .catchall {:try_start_c .. :try_end_3f} :catchall_85
    .catch Landroid/os/RemoteException; {:try_start_c .. :try_end_3f} :catch_a7

    move-result-wide v6

    cmp-long v6, v6, v2

    if-gez v6, :cond_67

    .line 1626
    :try_start_44
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mAgentConnectLock:Ljava/lang/Object;

    const-wide/16 v7, 0x1388

    invoke-virtual {v6, v7, v8}, Ljava/lang/Object;->wait(J)V
    :try_end_4b
    .catchall {:try_start_44 .. :try_end_4b} :catchall_85
    .catch Ljava/lang/InterruptedException; {:try_start_44 .. :try_end_4b} :catch_4c
    .catch Landroid/os/RemoteException; {:try_start_44 .. :try_end_4b} :catch_a7

    goto :goto_34

    .line 1627
    :catch_4c
    move-exception v1

    .line 1629
    .local v1, e:Ljava/lang/InterruptedException;
    :try_start_4d
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Interrupted: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_65
    .catchall {:try_start_4d .. :try_end_65} :catchall_85
    .catch Landroid/os/RemoteException; {:try_start_4d .. :try_end_65} :catch_a7

    .line 1630
    :try_start_65
    monitor-exit v5
    :try_end_66
    .catchall {:try_start_65 .. :try_end_66} :catchall_85

    .line 1646
    .end local v1           #e:Ljava/lang/InterruptedException;
    .end local v2           #timeoutMark:J
    :goto_66
    return-object v4

    .line 1635
    .restart local v2       #timeoutMark:J
    :cond_67
    :try_start_67
    iget-boolean v6, p0, Lcom/android/server/BackupManagerService;->mConnecting:Z

    if-ne v6, v10, :cond_88

    .line 1636
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Timeout waiting for agent "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_83
    .catchall {:try_start_67 .. :try_end_83} :catchall_85
    .catch Landroid/os/RemoteException; {:try_start_67 .. :try_end_83} :catch_a7

    .line 1637
    :try_start_83
    monitor-exit v5

    goto :goto_66

    .line 1645
    .end local v2           #timeoutMark:J
    :catchall_85
    move-exception v4

    monitor-exit v5
    :try_end_87
    .catchall {:try_start_83 .. :try_end_87} :catchall_85

    throw v4

    .line 1639
    .restart local v2       #timeoutMark:J
    :cond_88
    :try_start_88
    const-string v4, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "got agent "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget-object v7, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1640
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mConnectedAgent:Landroid/app/IBackupAgent;
    :try_end_a4
    .catchall {:try_start_88 .. :try_end_a4} :catchall_85
    .catch Landroid/os/RemoteException; {:try_start_88 .. :try_end_a4} :catch_a7

    .line 1645
    .end local v2           #timeoutMark:J
    :cond_a4
    :goto_a4
    :try_start_a4
    monitor-exit v5
    :try_end_a5
    .catchall {:try_start_a4 .. :try_end_a5} :catchall_85

    move-object v4, v0

    .line 1646
    goto :goto_66

    .line 1642
    :catch_a7
    move-exception v4

    goto :goto_a4
.end method

.method clearApplicationDataSynchronous(Ljava/lang/String;)V
    .registers 13
    .parameter "packageName"

    .prologue
    .line 1653
    :try_start_0
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    const/4 v6, 0x0

    invoke-virtual {v5, p1, v6}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v1

    .line 1654
    .local v1, info:Landroid/content/pm/PackageInfo;
    iget-object v5, v1, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v5, v5, Landroid/content/pm/ApplicationInfo;->flags:I
    :try_end_b
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_b} :catch_10

    and-int/lit8 v5, v5, 0x40

    if-nez v5, :cond_30

    .line 1686
    .end local v1           #info:Landroid/content/pm/PackageInfo;
    :goto_f
    return-void

    .line 1659
    :catch_10
    move-exception v0

    .line 1660
    .local v0, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Tried to clear data for "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " but not found"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_f

    .line 1664
    .end local v0           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v1       #info:Landroid/content/pm/PackageInfo;
    :cond_30
    new-instance v2, Lcom/android/server/BackupManagerService$ClearDataObserver;

    invoke-direct {v2, p0}, Lcom/android/server/BackupManagerService$ClearDataObserver;-><init>(Lcom/android/server/BackupManagerService;)V

    .line 1666
    .local v2, observer:Lcom/android/server/BackupManagerService$ClearDataObserver;
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mClearDataLock:Ljava/lang/Object;

    monitor-enter v6

    .line 1667
    const/4 v5, 0x1

    :try_start_39
    iput-boolean v5, p0, Lcom/android/server/BackupManagerService;->mClearingData:Z
    :try_end_3b
    .catchall {:try_start_39 .. :try_end_3b} :catchall_65

    .line 1669
    :try_start_3b
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mActivityManager:Landroid/app/IActivityManager;

    invoke-static {}, Landroid/os/Binder;->getOrigCallingUser()I

    move-result v7

    invoke-interface {v5, p1, v2, v7}, Landroid/app/IActivityManager;->clearApplicationUserData(Ljava/lang/String;Landroid/content/pm/IPackageDataObserver;I)Z
    :try_end_44
    .catchall {:try_start_3b .. :try_end_44} :catchall_65
    .catch Landroid/os/RemoteException; {:try_start_3b .. :try_end_44} :catch_6a

    .line 1676
    :goto_44
    :try_start_44
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v7

    const-wide/16 v9, 0x2710

    add-long v3, v7, v9

    .line 1677
    .local v3, timeoutMark:J
    :goto_4c
    iget-boolean v5, p0, Lcom/android/server/BackupManagerService;->mClearingData:Z

    if-eqz v5, :cond_68

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J
    :try_end_53
    .catchall {:try_start_44 .. :try_end_53} :catchall_65

    move-result-wide v7

    cmp-long v5, v7, v3

    if-gez v5, :cond_68

    .line 1679
    :try_start_58
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mClearDataLock:Ljava/lang/Object;

    const-wide/16 v7, 0x1388

    invoke-virtual {v5, v7, v8}, Ljava/lang/Object;->wait(J)V
    :try_end_5f
    .catchall {:try_start_58 .. :try_end_5f} :catchall_65
    .catch Ljava/lang/InterruptedException; {:try_start_58 .. :try_end_5f} :catch_60

    goto :goto_4c

    .line 1680
    :catch_60
    move-exception v0

    .line 1682
    .local v0, e:Ljava/lang/InterruptedException;
    const/4 v5, 0x0

    :try_start_62
    iput-boolean v5, p0, Lcom/android/server/BackupManagerService;->mClearingData:Z

    goto :goto_4c

    .line 1685
    .end local v0           #e:Ljava/lang/InterruptedException;
    .end local v3           #timeoutMark:J
    :catchall_65
    move-exception v5

    monitor-exit v6
    :try_end_67
    .catchall {:try_start_62 .. :try_end_67} :catchall_65

    throw v5

    .restart local v3       #timeoutMark:J
    :cond_68
    :try_start_68
    monitor-exit v6
    :try_end_69
    .catchall {:try_start_68 .. :try_end_69} :catchall_65

    goto :goto_f

    .line 1671
    .end local v3           #timeoutMark:J
    :catch_6a
    move-exception v5

    goto :goto_44
.end method

.method public clearBackupData(Ljava/lang/String;)V
    .registers 16
    .parameter "packageName"

    .prologue
    .line 4893
    const-string v9, "BackupManagerService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "clearBackupData() of "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4896
    :try_start_18
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    const/16 v10, 0x40

    invoke-virtual {v9, p1, v10}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    :try_end_1f
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_18 .. :try_end_1f} :catch_74

    move-result-object v4

    .line 4905
    .local v4, info:Landroid/content/pm/PackageInfo;
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v10, "android.permission.BACKUP"

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v11

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v12

    invoke-virtual {v9, v10, v11, v12}, Landroid/content/Context;->checkPermission(Ljava/lang/String;II)I

    move-result v9

    const/4 v10, -0x1

    if-ne v9, v10, :cond_94

    .line 4907
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v10

    invoke-virtual {v9, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/HashSet;

    .line 4923
    .local v1, apps:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :cond_3f
    invoke-virtual {v1, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_73

    .line 4924
    const-string v9, "BackupManagerService"

    const-string v10, "Found the app - running clear process"

    invoke-static {v9, v10}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4926
    iget-object v10, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v10

    .line 4927
    :try_start_4f
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v6

    .line 4928
    .local v6, oldId:J
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v9}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 4929
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v11, 0x4

    new-instance v12, Lcom/android/server/BackupManagerService$ClearParams;

    iget-object v13, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    invoke-direct {p0, v13}, Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v13

    invoke-direct {v12, p0, v13, v4}, Lcom/android/server/BackupManagerService$ClearParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Landroid/content/pm/PackageInfo;)V

    invoke-virtual {v9, v11, v12}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v5

    .line 4931
    .local v5, msg:Landroid/os/Message;
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v9, v5}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 4932
    invoke-static {v6, v7}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 4933
    monitor-exit v10
    :try_end_73
    .catchall {:try_start_4f .. :try_end_73} :catchall_b9

    .line 4935
    .end local v1           #apps:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    .end local v4           #info:Landroid/content/pm/PackageInfo;
    .end local v5           #msg:Landroid/os/Message;
    .end local v6           #oldId:J
    :cond_73
    :goto_73
    return-void

    .line 4897
    :catch_74
    move-exception v2

    .line 4898
    .local v2, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    const-string v9, "BackupManagerService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "No such package \'"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "\' - not clearing backup data"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_73

    .line 4911
    .end local v2           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v4       #info:Landroid/content/pm/PackageInfo;
    :cond_94
    const-string v9, "BackupManagerService"

    const-string v10, "Privileged caller, allowing clear of other apps"

    invoke-static {v9, v10}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4912
    new-instance v1, Ljava/util/HashSet;

    invoke-direct {v1}, Ljava/util/HashSet;-><init>()V

    .line 4913
    .restart local v1       #apps:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v9}, Landroid/util/SparseArray;->size()I

    move-result v0

    .line 4914
    .local v0, N:I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_a7
    if-ge v3, v0, :cond_3f

    .line 4915
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v9, v3}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/util/HashSet;

    .line 4916
    .local v8, s:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-eqz v8, :cond_b6

    .line 4917
    invoke-virtual {v1, v8}, Ljava/util/HashSet;->addAll(Ljava/util/Collection;)Z

    .line 4914
    :cond_b6
    add-int/lit8 v3, v3, 0x1

    goto :goto_a7

    .line 4933
    .end local v0           #N:I
    .end local v3           #i:I
    .end local v8           #s:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :catchall_b9
    move-exception v9

    :try_start_ba
    monitor-exit v10
    :try_end_bb
    .catchall {:try_start_ba .. :try_end_bb} :catchall_b9

    throw v9
.end method

.method clearBackupTrace()V
    .registers 3

    .prologue
    .line 705
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    monitor-enter v1

    .line 706
    :try_start_3
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mBackupTrace:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->clear()V

    .line 707
    monitor-exit v1

    .line 709
    return-void

    .line 707
    :catchall_a
    move-exception v0

    monitor-exit v1
    :try_end_c
    .catchall {:try_start_3 .. :try_end_c} :catchall_a

    throw v0
.end method

.method clearRestoreSession(Lcom/android/server/BackupManagerService$ActiveRestoreSession;)V
    .registers 4
    .parameter "currentSession"

    .prologue
    .line 5514
    monitor-enter p0

    .line 5515
    :try_start_1
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    if-eq p1, v0, :cond_e

    .line 5516
    const-string v0, "BackupManagerService"

    const-string v1, "ending non-current restore session"

    invoke-static {v0, v1}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 5522
    :goto_c
    monitor-exit p0

    .line 5523
    return-void

    .line 5518
    :cond_e
    const-string v0, "BackupManagerService"

    const-string v1, "Clearing restore session and halting timeout"

    invoke-static {v0, v1}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5519
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/server/BackupManagerService;->mActiveRestoreSession:Lcom/android/server/BackupManagerService$ActiveRestoreSession;

    .line 5520
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->removeMessages(I)V

    goto :goto_c

    .line 5522
    :catchall_20
    move-exception v0

    monitor-exit p0
    :try_end_22
    .catchall {:try_start_1 .. :try_end_22} :catchall_20

    throw v0
.end method

.method public dataChanged(Ljava/lang/String;)V
    .registers 6
    .parameter "packageName"

    .prologue
    .line 4877
    invoke-direct {p0, p1}, Lcom/android/server/BackupManagerService;->dataChangedTargets(Ljava/lang/String;)Ljava/util/HashSet;

    move-result-object v0

    .line 4878
    .local v0, targets:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-nez v0, :cond_33

    .line 4879
    const-string v1, "BackupManagerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "dataChanged but no participant pkg=\'"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\'"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " uid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 4889
    :goto_32
    return-void

    .line 4884
    :cond_33
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    new-instance v2, Lcom/android/server/BackupManagerService$3;

    invoke-direct {v2, p0, p1, v0}, Lcom/android/server/BackupManagerService$3;-><init>(Lcom/android/server/BackupManagerService;Ljava/lang/String;Ljava/util/HashSet;)V

    invoke-virtual {v1, v2}, Lcom/android/server/BackupManagerService$BackupHandler;->post(Ljava/lang/Runnable;)Z

    goto :goto_32
.end method

.method deviceIsProvisioned()Z
    .registers 4

    .prologue
    const/4 v1, 0x0

    .line 4958
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    .line 4959
    .local v0, resolver:Landroid/content/ContentResolver;
    const-string v2, "device_provisioned"

    invoke-static {v0, v2, v1}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    if-eqz v2, :cond_10

    const/4 v1, 0x1

    :cond_10
    return v1
.end method

.method doNothing(ILcom/android/server/BackupManagerService$FullParams;)V
    .registers 3
    .parameter "token"
    .parameter "params"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 5110
    return-void
.end method

.method public dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .registers 9
    .parameter "fd"
    .parameter "pw"
    .parameter "args"

    .prologue
    .line 5800
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.DUMP"

    const-string v4, "BackupManagerService"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5802
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v0

    .line 5804
    .local v0, identityToken:J
    :try_start_d
    invoke-direct {p0, p2}, Lcom/android/server/BackupManagerService;->dumpInternal(Ljava/io/PrintWriter;)V
    :try_end_10
    .catchall {:try_start_d .. :try_end_10} :catchall_14

    .line 5806
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5808
    return-void

    .line 5806
    :catchall_14
    move-exception v2

    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v2
.end method

.method public fullBackup(Landroid/os/ParcelFileDescriptor;ZZZZ[Ljava/lang/String;)V
    .registers 19
    .parameter "fd"
    .parameter "includeApks"
    .parameter "includeShared"
    .parameter "doAllApps"
    .parameter "includeSystem"
    .parameter "pkgList"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 4968
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.BACKUP"

    const-string v4, "fullBackup"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 4971
    if-nez p4, :cond_1c

    .line 4972
    if-nez p3, :cond_1c

    .line 4976
    if-eqz p6, :cond_14

    move-object/from16 v0, p6

    array-length v2, v0

    if-nez v2, :cond_1c

    .line 4977
    :cond_14
    new-instance v2, Ljava/lang/IllegalArgumentException;

    const-string v3, "Backup requested but neither shared nor any apps named"

    invoke-direct {v2, v3}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v2

    .line 4983
    :cond_1c
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v9

    .line 4986
    .local v9, oldId:J
    :try_start_20
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->deviceIsProvisioned()Z

    move-result v2

    if-nez v2, :cond_3b

    .line 4987
    const-string v2, "BackupManagerService"

    const-string v3, "Full backup not supported before setup"

    invoke-static {v2, v3}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2d
    .catchall {:try_start_20 .. :try_end_2d} :catchall_d3

    .line 5022
    :try_start_2d
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_30
    .catch Ljava/io/IOException; {:try_start_2d .. :try_end_30} :catch_107

    .line 5026
    :goto_30
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5027
    const-string v2, "BackupManagerService"

    const-string v3, "Full backup processing complete."

    :goto_37
    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5029
    return-void

    .line 4991
    :cond_3b
    :try_start_3b
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Requesting full backup: apks="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " shared="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " all="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move/from16 v0, p4

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " pkgs="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    move-object/from16 v0, p6

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 4994
    const-string v2, "BackupManagerService"

    const-string v3, "Beginning full backup..."

    invoke-static {v2, v3}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 4996
    new-instance v1, Lcom/android/server/BackupManagerService$FullBackupParams;

    move-object v2, p0

    move-object v3, p1

    move v4, p2

    move v5, p3

    move/from16 v6, p4

    move/from16 v7, p5

    move-object/from16 v8, p6

    invoke-direct/range {v1 .. v8}, Lcom/android/server/BackupManagerService$FullBackupParams;-><init>(Lcom/android/server/BackupManagerService;Landroid/os/ParcelFileDescriptor;ZZZZ[Ljava/lang/String;)V

    .line 4998
    .local v1, params:Lcom/android/server/BackupManagerService$FullBackupParams;
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->generateToken()I

    move-result v11

    .line 4999
    .local v11, token:I
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    monitor-enter v3
    :try_end_92
    .catchall {:try_start_3b .. :try_end_92} :catchall_d3

    .line 5000
    :try_start_92
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v2, v11, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 5001
    monitor-exit v3
    :try_end_98
    .catchall {:try_start_92 .. :try_end_98} :catchall_d0

    .line 5004
    :try_start_98
    const-string v2, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Starting backup confirmation UI, token="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5005
    const-string v2, "fullback"

    invoke-virtual {p0, v11, v2}, Lcom/android/server/BackupManagerService;->startBackupRestore(ILjava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_e2

    .line 5006
    const-string v2, "BackupManagerService"

    const-string v3, "Unable to launch full backup confirmation"

    invoke-static {v2, v3}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 5007
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v2, v11}, Landroid/util/SparseArray;->delete(I)V
    :try_end_c4
    .catchall {:try_start_98 .. :try_end_c4} :catchall_d3

    .line 5022
    :try_start_c4
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_c7
    .catch Ljava/io/IOException; {:try_start_c4 .. :try_end_c7} :catch_10a

    .line 5026
    :goto_c7
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5027
    const-string v2, "BackupManagerService"

    const-string v3, "Full backup processing complete."

    goto/16 :goto_37

    .line 5001
    :catchall_d0
    move-exception v2

    :try_start_d1
    monitor-exit v3
    :try_end_d2
    .catchall {:try_start_d1 .. :try_end_d2} :catchall_d0

    :try_start_d2
    throw v2
    :try_end_d3
    .catchall {:try_start_d2 .. :try_end_d3} :catchall_d3

    .line 5021
    .end local v1           #params:Lcom/android/server/BackupManagerService$FullBackupParams;
    .end local v11           #token:I
    :catchall_d3
    move-exception v2

    .line 5022
    :try_start_d4
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_d7
    .catch Ljava/io/IOException; {:try_start_d4 .. :try_end_d7} :catch_105

    .line 5026
    :goto_d7
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5027
    const-string v3, "BackupManagerService"

    const-string v4, "Full backup processing complete."

    invoke-static {v3, v4}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5021
    throw v2

    .line 5012
    .restart local v1       #params:Lcom/android/server/BackupManagerService$FullBackupParams;
    .restart local v11       #token:I
    :cond_e2
    :try_start_e2
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mPowerManager:Landroid/os/PowerManager;

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v3

    const/4 v5, 0x0

    invoke-virtual {v2, v3, v4, v5}, Landroid/os/PowerManager;->userActivity(JZ)V

    .line 5015
    invoke-virtual {p0, v11, v1}, Lcom/android/server/BackupManagerService;->doNothing(ILcom/android/server/BackupManagerService$FullParams;)V

    .line 5018
    const-string v2, "BackupManagerService"

    const-string v3, "Waiting for full backup completion..."

    invoke-static {v2, v3}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5019
    invoke-virtual {p0, v1}, Lcom/android/server/BackupManagerService;->waitForCompletion(Lcom/android/server/BackupManagerService$FullParams;)V
    :try_end_f9
    .catchall {:try_start_e2 .. :try_end_f9} :catchall_d3

    .line 5022
    :try_start_f9
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_fc
    .catch Ljava/io/IOException; {:try_start_f9 .. :try_end_fc} :catch_10c

    .line 5026
    :goto_fc
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5027
    const-string v2, "BackupManagerService"

    const-string v3, "Full backup processing complete."

    goto/16 :goto_37

    .line 5023
    .end local v1           #params:Lcom/android/server/BackupManagerService$FullBackupParams;
    .end local v11           #token:I
    :catch_105
    move-exception v3

    goto :goto_d7

    :catch_107
    move-exception v2

    goto/16 :goto_30

    .restart local v1       #params:Lcom/android/server/BackupManagerService$FullBackupParams;
    .restart local v11       #token:I
    :catch_10a
    move-exception v2

    goto :goto_c7

    :catch_10c
    move-exception v2

    goto :goto_fc
.end method

.method public fullRestore(Landroid/os/ParcelFileDescriptor;)V
    .registers 11
    .parameter "fd"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 5033
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v6, "android.permission.BACKUP"

    const-string v7, "fullRestore"

    invoke-virtual {v5, v6, v7}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5035
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 5040
    .local v1, oldId:J
    :try_start_d
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->deviceIsProvisioned()Z

    move-result v5

    if-nez v5, :cond_28

    .line 5041
    const-string v5, "BackupManagerService"

    const-string v6, "Full restore not permitted before setup"

    invoke-static {v5, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1a
    .catchall {:try_start_d .. :try_end_1a} :catchall_7b

    .line 5072
    :try_start_1a
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_1d
    .catch Ljava/io/IOException; {:try_start_1a .. :try_end_1d} :catch_c7

    .line 5076
    :goto_1d
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5077
    const-string v5, "BackupManagerService"

    const-string v6, "Full restore processing complete."

    :goto_24
    invoke-static {v5, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5079
    return-void

    .line 5045
    :cond_28
    :try_start_28
    const-string v5, "BackupManagerService"

    const-string v6, "Beginning full restore..."

    invoke-static {v5, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5047
    new-instance v3, Lcom/android/server/BackupManagerService$FullRestoreParams;

    invoke-direct {v3, p0, p1}, Lcom/android/server/BackupManagerService$FullRestoreParams;-><init>(Lcom/android/server/BackupManagerService;Landroid/os/ParcelFileDescriptor;)V

    .line 5048
    .local v3, params:Lcom/android/server/BackupManagerService$FullRestoreParams;
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->generateToken()I

    move-result v4

    .line 5049
    .local v4, token:I
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    monitor-enter v6
    :try_end_3b
    .catchall {:try_start_28 .. :try_end_3b} :catchall_7b

    .line 5050
    :try_start_3b
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v5, v4, v3}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 5051
    monitor-exit v6
    :try_end_41
    .catchall {:try_start_3b .. :try_end_41} :catchall_78

    .line 5054
    :try_start_41
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Starting restore confirmation UI, token="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5055
    const-string v5, "fullrest"

    invoke-virtual {p0, v4, v5}, Lcom/android/server/BackupManagerService;->startBackupRestore(ILjava/lang/String;)Z

    move-result v5

    if-nez v5, :cond_8a

    .line 5056
    const-string v5, "BackupManagerService"

    const-string v6, "Unable to launch full restore confirmation"

    invoke-static {v5, v6}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 5057
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v5, v4}, Landroid/util/SparseArray;->delete(I)V
    :try_end_6d
    .catchall {:try_start_41 .. :try_end_6d} :catchall_7b

    .line 5072
    :try_start_6d
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_70
    .catch Ljava/io/IOException; {:try_start_6d .. :try_end_70} :catch_e2

    .line 5076
    :goto_70
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5077
    const-string v5, "BackupManagerService"

    const-string v6, "Full restore processing complete."

    goto :goto_24

    .line 5051
    :catchall_78
    move-exception v5

    :try_start_79
    monitor-exit v6
    :try_end_7a
    .catchall {:try_start_79 .. :try_end_7a} :catchall_78

    :try_start_7a
    throw v5
    :try_end_7b
    .catchall {:try_start_7a .. :try_end_7b} :catchall_7b

    .line 5071
    .end local v3           #params:Lcom/android/server/BackupManagerService$FullRestoreParams;
    .end local v4           #token:I
    :catchall_7b
    move-exception v5

    .line 5072
    :try_start_7c
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_7f
    .catch Ljava/io/IOException; {:try_start_7c .. :try_end_7f} :catch_ad

    .line 5076
    :goto_7f
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5077
    const-string v6, "BackupManagerService"

    const-string v7, "Full restore processing complete."

    invoke-static {v6, v7}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5071
    throw v5

    .line 5062
    .restart local v3       #params:Lcom/android/server/BackupManagerService$FullRestoreParams;
    .restart local v4       #token:I
    :cond_8a
    :try_start_8a
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mPowerManager:Landroid/os/PowerManager;

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v6

    const/4 v8, 0x0

    invoke-virtual {v5, v6, v7, v8}, Landroid/os/PowerManager;->userActivity(JZ)V

    .line 5065
    invoke-virtual {p0, v4, v3}, Lcom/android/server/BackupManagerService;->doNothing(ILcom/android/server/BackupManagerService$FullParams;)V

    .line 5068
    const-string v5, "BackupManagerService"

    const-string v6, "Waiting for full restore completion..."

    invoke-static {v5, v6}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 5069
    invoke-virtual {p0, v3}, Lcom/android/server/BackupManagerService;->waitForCompletion(Lcom/android/server/BackupManagerService$FullParams;)V
    :try_end_a1
    .catchall {:try_start_8a .. :try_end_a1} :catchall_7b

    .line 5072
    :try_start_a1
    invoke-virtual {p1}, Landroid/os/ParcelFileDescriptor;->close()V
    :try_end_a4
    .catch Ljava/io/IOException; {:try_start_a1 .. :try_end_a4} :catch_fd

    .line 5076
    :goto_a4
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 5077
    const-string v5, "BackupManagerService"

    const-string v6, "Full restore processing complete."

    goto/16 :goto_24

    .line 5073
    .end local v3           #params:Lcom/android/server/BackupManagerService$FullRestoreParams;
    .end local v4           #token:I
    :catch_ad
    move-exception v0

    .line 5074
    .local v0, e:Ljava/io/IOException;
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Error trying to close fd after full restore: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_7f

    .line 5073
    .end local v0           #e:Ljava/io/IOException;
    :catch_c7
    move-exception v0

    .line 5074
    .restart local v0       #e:Ljava/io/IOException;
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Error trying to close fd after full restore: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1d

    .line 5073
    .end local v0           #e:Ljava/io/IOException;
    .restart local v3       #params:Lcom/android/server/BackupManagerService$FullRestoreParams;
    .restart local v4       #token:I
    :catch_e2
    move-exception v0

    .line 5074
    .restart local v0       #e:Ljava/io/IOException;
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Error trying to close fd after full restore: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_70

    .line 5073
    .end local v0           #e:Ljava/io/IOException;
    :catch_fd
    move-exception v0

    .line 5074
    .restart local v0       #e:Ljava/io/IOException;
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Error trying to close fd after full restore: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_a4
.end method

.method generateToken()I
    .registers 4

    .prologue
    .line 454
    :cond_0
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mTokenGenerator:Ljava/util/Random;

    monitor-enter v2

    .line 455
    :try_start_3
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mTokenGenerator:Ljava/util/Random;

    invoke-virtual {v1}, Ljava/util/Random;->nextInt()I

    move-result v0

    .line 456
    .local v0, token:I
    monitor-exit v2

    .line 457
    if-ltz v0, :cond_0

    .line 458
    return v0

    .line 456
    .end local v0           #token:I
    :catchall_d
    move-exception v1

    monitor-exit v2
    :try_end_f
    .catchall {:try_start_3 .. :try_end_f} :catchall_d

    throw v1
.end method

.method getAvailableRestoreToken(Ljava/lang/String;)J
    .registers 6
    .parameter "packageName"

    .prologue
    .line 1700
    iget-wide v0, p0, Lcom/android/server/BackupManagerService;->mAncestralToken:J

    .line 1701
    .local v0, token:J
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v3

    .line 1702
    :try_start_5
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v2, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_f

    .line 1703
    iget-wide v0, p0, Lcom/android/server/BackupManagerService;->mCurrentToken:J

    .line 1705
    :cond_f
    monitor-exit v3

    .line 1706
    return-wide v0

    .line 1705
    :catchall_11
    move-exception v2

    monitor-exit v3
    :try_end_13
    .catchall {:try_start_5 .. :try_end_13} :catchall_11

    throw v2
.end method

.method public getConfigurationIntent(Ljava/lang/String;)Landroid/content/Intent;
    .registers 7
    .parameter "transportName"

    .prologue
    .line 5350
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.BACKUP"

    const-string v4, "getConfigurationIntent"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5353
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v3

    .line 5354
    :try_start_c
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/internal/backup/IBackupTransport;
    :try_end_14
    .catchall {:try_start_c .. :try_end_14} :catchall_20

    .line 5355
    .local v1, transport:Lcom/android/internal/backup/IBackupTransport;
    if-eqz v1, :cond_1d

    .line 5357
    :try_start_16
    invoke-interface {v1}, Lcom/android/internal/backup/IBackupTransport;->configurationIntent()Landroid/content/Intent;
    :try_end_19
    .catchall {:try_start_16 .. :try_end_19} :catchall_20
    .catch Landroid/os/RemoteException; {:try_start_16 .. :try_end_19} :catch_1c

    move-result-object v0

    .line 5360
    .local v0, intent:Landroid/content/Intent;
    :try_start_1a
    monitor-exit v3

    .line 5367
    .end local v0           #intent:Landroid/content/Intent;
    :goto_1b
    return-object v0

    .line 5361
    :catch_1c
    move-exception v2

    .line 5365
    :cond_1d
    monitor-exit v3

    .line 5367
    const/4 v0, 0x0

    goto :goto_1b

    .line 5365
    .end local v1           #transport:Lcom/android/internal/backup/IBackupTransport;
    :catchall_20
    move-exception v2

    monitor-exit v3
    :try_end_22
    .catchall {:try_start_1a .. :try_end_22} :catchall_20

    throw v2
.end method

.method public getCurrentTransport()Ljava/lang/String;
    .registers 4

    .prologue
    .line 5299
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.BACKUP"

    const-string v2, "getCurrentTransport"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5302
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    return-object v0
.end method

.method public getDestinationString(Ljava/lang/String;)Ljava/lang/String;
    .registers 7
    .parameter "transportName"

    .prologue
    .line 5376
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.BACKUP"

    const-string v4, "getDestinationString"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5379
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v3

    .line 5380
    :try_start_c
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/internal/backup/IBackupTransport;
    :try_end_14
    .catchall {:try_start_c .. :try_end_14} :catchall_20

    .line 5381
    .local v1, transport:Lcom/android/internal/backup/IBackupTransport;
    if-eqz v1, :cond_1d

    .line 5383
    :try_start_16
    invoke-interface {v1}, Lcom/android/internal/backup/IBackupTransport;->currentDestinationString()Ljava/lang/String;
    :try_end_19
    .catchall {:try_start_16 .. :try_end_19} :catchall_20
    .catch Landroid/os/RemoteException; {:try_start_16 .. :try_end_19} :catch_1c

    move-result-object v0

    .line 5385
    .local v0, text:Ljava/lang/String;
    :try_start_1a
    monitor-exit v3

    .line 5392
    .end local v0           #text:Ljava/lang/String;
    :goto_1b
    return-object v0

    .line 5386
    :catch_1c
    move-exception v2

    .line 5390
    :cond_1d
    monitor-exit v3

    .line 5392
    const/4 v0, 0x0

    goto :goto_1b

    .line 5390
    .end local v1           #transport:Lcom/android/internal/backup/IBackupTransport;
    :catchall_20
    move-exception v2

    monitor-exit v3
    :try_end_22
    .catchall {:try_start_1a .. :try_end_22} :catchall_20

    throw v2
.end method

.method handleTimeout(ILjava/lang/Object;)V
    .registers 10
    .parameter "token"
    .parameter "obj"

    .prologue
    const/4 v2, -0x1

    .line 1769
    const/4 v1, 0x0

    .line 1770
    .local v1, op:Lcom/android/server/BackupManagerService$Operation;
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    monitor-enter v4

    .line 1771
    :try_start_5
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    move-object v0, v3

    check-cast v0, Lcom/android/server/BackupManagerService$Operation;

    move-object v1, v0

    .line 1776
    if-eqz v1, :cond_13

    iget v2, v1, Lcom/android/server/BackupManagerService$Operation;->state:I

    .line 1777
    .local v2, state:I
    :cond_13
    if-nez v2, :cond_39

    .line 1778
    const-string v3, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "TIMEOUT: token="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-static {p1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1779
    const/4 v3, -0x1

    iput v3, v1, Lcom/android/server/BackupManagerService$Operation;->state:I

    .line 1780
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 1782
    :cond_39
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    invoke-virtual {v3}, Ljava/lang/Object;->notifyAll()V

    .line 1783
    monitor-exit v4
    :try_end_3f
    .catchall {:try_start_5 .. :try_end_3f} :catchall_4b

    .line 1786
    if-eqz v1, :cond_4a

    iget-object v3, v1, Lcom/android/server/BackupManagerService$Operation;->callback:Lcom/android/server/BackupManagerService$BackupRestoreTask;

    if-eqz v3, :cond_4a

    .line 1787
    iget-object v3, v1, Lcom/android/server/BackupManagerService$Operation;->callback:Lcom/android/server/BackupManagerService$BackupRestoreTask;

    invoke-interface {v3}, Lcom/android/server/BackupManagerService$BackupRestoreTask;->handleTimeout()V

    .line 1789
    :cond_4a
    return-void

    .line 1783
    .end local v2           #state:I
    :catchall_4b
    move-exception v3

    :try_start_4c
    monitor-exit v4
    :try_end_4d
    .catchall {:try_start_4c .. :try_end_4d} :catchall_4b

    throw v3
.end method

.method public hasBackupPassword()Z
    .registers 6

    .prologue
    const/4 v1, 0x1

    .line 1214
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.BACKUP"

    const-string v4, "hasBackupPassword"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1218
    :try_start_a
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mMountService:Landroid/os/storage/IMountService;

    invoke-interface {v2}, Landroid/os/storage/IMountService;->getEncryptionState()I

    move-result v2

    if-ne v2, v1, :cond_1e

    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    if-eqz v2, :cond_1f

    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    invoke-virtual {v2}, Ljava/lang/String;->length()I
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_a .. :try_end_1b} :catch_21

    move-result v2

    if-lez v2, :cond_1f

    .line 1223
    :cond_1e
    :goto_1e
    return v1

    .line 1218
    :cond_1f
    const/4 v1, 0x0

    goto :goto_1e

    .line 1220
    :catch_21
    move-exception v0

    .line 1223
    .local v0, e:Ljava/lang/Exception;
    goto :goto_1e
.end method

.method public isBackupEnabled()Z
    .registers 4

    .prologue
    .line 5293
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.BACKUP"

    const-string v2, "isBackupEnabled"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5294
    iget-boolean v0, p0, Lcom/android/server/BackupManagerService;->mEnabled:Z

    return v0
.end method

.method public listAllTransports()[Ljava/lang/String;
    .registers 8

    .prologue
    .line 5307
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v5, "android.permission.BACKUP"

    const-string v6, "listAllTransports"

    invoke-virtual {v4, v5, v6}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5309
    const/4 v3, 0x0

    .line 5310
    .local v3, list:[Ljava/lang/String;
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    .line 5311
    .local v2, known:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->entrySet()Ljava/util/Set;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_19
    :goto_19
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_33

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map$Entry;

    .line 5312
    .local v0, entry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;>;"
    invoke-interface {v0}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v4

    if-eqz v4, :cond_19

    .line 5313
    invoke-interface {v0}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_19

    .line 5317
    .end local v0           #entry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/internal/backup/IBackupTransport;>;"
    :cond_33
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-lez v4, :cond_42

    .line 5318
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v4

    new-array v3, v4, [Ljava/lang/String;

    .line 5319
    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 5321
    :cond_42
    return-object v3
.end method

.method logBackupComplete(Ljava/lang/String;)V
    .registers 9
    .parameter "packageName"

    .prologue
    .line 1512
    const-string v3, "@pm@"

    invoke-virtual {p1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_9

    .line 1528
    :goto_8
    return-void

    .line 1514
    :cond_9
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    monitor-enter v4

    .line 1515
    :try_start_c
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v3, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_19

    monitor-exit v4

    goto :goto_8

    .line 1527
    :catchall_16
    move-exception v3

    monitor-exit v4
    :try_end_18
    .catchall {:try_start_c .. :try_end_18} :catchall_16

    throw v3

    .line 1517
    :cond_19
    const/4 v1, 0x0

    .line 1519
    .local v1, out:Ljava/io/RandomAccessFile;
    :try_start_1a
    new-instance v2, Ljava/io/RandomAccessFile;

    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    const-string v5, "rws"

    invoke-direct {v2, v3, v5}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_23
    .catchall {:try_start_1a .. :try_end_23} :catchall_62
    .catch Ljava/io/IOException; {:try_start_1a .. :try_end_23} :catch_35

    .line 1520
    .end local v1           #out:Ljava/io/RandomAccessFile;
    .local v2, out:Ljava/io/RandomAccessFile;
    :try_start_23
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->length()J

    move-result-wide v5

    invoke-virtual {v2, v5, v6}, Ljava/io/RandomAccessFile;->seek(J)V

    .line 1521
    invoke-virtual {v2, p1}, Ljava/io/RandomAccessFile;->writeUTF(Ljava/lang/String;)V
    :try_end_2d
    .catchall {:try_start_23 .. :try_end_2d} :catchall_6d
    .catch Ljava/io/IOException; {:try_start_23 .. :try_end_2d} :catch_70

    .line 1525
    if-eqz v2, :cond_32

    :try_start_2f
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->close()V
    :try_end_32
    .catchall {:try_start_2f .. :try_end_32} :catchall_16
    .catch Ljava/io/IOException; {:try_start_2f .. :try_end_32} :catch_6b

    :cond_32
    :goto_32
    move-object v1, v2

    .line 1527
    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    :cond_33
    :goto_33
    :try_start_33
    monitor-exit v4
    :try_end_34
    .catchall {:try_start_33 .. :try_end_34} :catchall_16

    goto :goto_8

    .line 1522
    :catch_35
    move-exception v0

    .line 1523
    .local v0, e:Ljava/io/IOException;
    :goto_36
    :try_start_36
    const-string v3, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Can\'t log backup of "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " to "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_5a
    .catchall {:try_start_36 .. :try_end_5a} :catchall_62

    .line 1525
    if-eqz v1, :cond_33

    :try_start_5c
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_5f
    .catchall {:try_start_5c .. :try_end_5f} :catchall_16
    .catch Ljava/io/IOException; {:try_start_5c .. :try_end_5f} :catch_60

    goto :goto_33

    :catch_60
    move-exception v3

    goto :goto_33

    .end local v0           #e:Ljava/io/IOException;
    :catchall_62
    move-exception v3

    :goto_63
    if-eqz v1, :cond_68

    :try_start_65
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_68
    .catchall {:try_start_65 .. :try_end_68} :catchall_16
    .catch Ljava/io/IOException; {:try_start_65 .. :try_end_68} :catch_69

    :cond_68
    :goto_68
    :try_start_68
    throw v3
    :try_end_69
    .catchall {:try_start_68 .. :try_end_69} :catchall_16

    :catch_69
    move-exception v5

    goto :goto_68

    .end local v1           #out:Ljava/io/RandomAccessFile;
    .restart local v2       #out:Ljava/io/RandomAccessFile;
    :catch_6b
    move-exception v3

    goto :goto_32

    :catchall_6d
    move-exception v3

    move-object v1, v2

    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    goto :goto_63

    .line 1522
    .end local v1           #out:Ljava/io/RandomAccessFile;
    .restart local v2       #out:Ljava/io/RandomAccessFile;
    :catch_70
    move-exception v0

    move-object v1, v2

    .end local v2           #out:Ljava/io/RandomAccessFile;
    .restart local v1       #out:Ljava/io/RandomAccessFile;
    goto :goto_36
.end method

.method public opComplete(I)V
    .registers 8
    .parameter "token"

    .prologue
    .line 5530
    const/4 v2, 0x0

    .line 5531
    .local v2, op:Lcom/android/server/BackupManagerService$Operation;
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    monitor-enter v4

    .line 5532
    :try_start_4
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    move-object v0, v3

    check-cast v0, Lcom/android/server/BackupManagerService$Operation;

    move-object v2, v0

    .line 5533
    if-eqz v2, :cond_13

    .line 5534
    const/4 v3, 0x1

    iput v3, v2, Lcom/android/server/BackupManagerService$Operation;->state:I

    .line 5536
    :cond_13
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    invoke-virtual {v3}, Ljava/lang/Object;->notifyAll()V

    .line 5537
    monitor-exit v4
    :try_end_19
    .catchall {:try_start_4 .. :try_end_19} :catchall_2f

    .line 5540
    if-eqz v2, :cond_2e

    iget-object v3, v2, Lcom/android/server/BackupManagerService$Operation;->callback:Lcom/android/server/BackupManagerService$BackupRestoreTask;

    if-eqz v3, :cond_2e

    .line 5541
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/16 v4, 0x15

    iget-object v5, v2, Lcom/android/server/BackupManagerService$Operation;->callback:Lcom/android/server/BackupManagerService$BackupRestoreTask;

    invoke-virtual {v3, v4, v5}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    .line 5542
    .local v1, msg:Landroid/os/Message;
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v3, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5544
    .end local v1           #msg:Landroid/os/Message;
    :cond_2e
    return-void

    .line 5537
    :catchall_2f
    move-exception v3

    :try_start_30
    monitor-exit v4
    :try_end_31
    .catchall {:try_start_30 .. :try_end_31} :catchall_2f

    throw v3
.end method

.method passwordMatchesSaved(Ljava/lang/String;I)Z
    .registers 11
    .parameter "candidatePw"
    .parameter "rounds"

    .prologue
    const/4 v4, 0x1

    const/4 v5, 0x0

    .line 1108
    :try_start_2
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mMountService:Landroid/os/storage/IMountService;

    invoke-interface {v6}, Landroid/os/storage/IMountService;->getEncryptionState()I

    move-result v6

    if-eq v6, v4, :cond_1d

    move v2, v4

    .line 1109
    .local v2, isEncrypted:Z
    :goto_b
    if-eqz v2, :cond_30

    .line 1111
    const-string v6, "BackupManagerService"

    const-string v7, "Device encrypted; verifying against device data pw"

    invoke-static {v6, v7}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1117
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mMountService:Landroid/os/storage/IMountService;

    invoke-interface {v6, p1}, Landroid/os/storage/IMountService;->verifyEncryptionPassword(Ljava/lang/String;)I

    move-result v3

    .line 1118
    .local v3, result:I
    if-nez v3, :cond_1f

    .line 1154
    .end local v2           #isEncrypted:Z
    .end local v3           #result:I
    :cond_1c
    :goto_1c
    return v4

    :cond_1d
    move v2, v5

    .line 1108
    goto :goto_b

    .line 1121
    .restart local v2       #isEncrypted:Z
    .restart local v3       #result:I
    :cond_1f
    const/4 v4, -0x2

    if-eq v3, v4, :cond_24

    move v4, v5

    .line 1123
    goto :goto_1c

    .line 1129
    :cond_24
    const-string v4, "BackupManagerService"

    const-string v6, "verified encryption state mismatch against query; no match allowed"

    invoke-static {v4, v6}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2b} :catch_2d

    move v4, v5

    .line 1130
    goto :goto_1c

    .line 1133
    .end local v2           #isEncrypted:Z
    .end local v3           #result:I
    :catch_2d
    move-exception v1

    .local v1, e:Ljava/lang/Exception;
    move v4, v5

    .line 1136
    goto :goto_1c

    .line 1139
    .end local v1           #e:Ljava/lang/Exception;
    .restart local v2       #isEncrypted:Z
    :cond_30
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    if-nez v6, :cond_40

    .line 1141
    if-eqz p1, :cond_1c

    const-string v6, ""

    invoke-virtual {v6, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_1c

    :cond_3e
    move v4, v5

    .line 1154
    goto :goto_1c

    .line 1146
    :cond_40
    if-eqz p1, :cond_3e

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v6

    if-lez v6, :cond_3e

    .line 1147
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mPasswordSalt:[B

    invoke-direct {p0, p1, v6, p2}, Lcom/android/server/BackupManagerService;->buildPasswordHash(Ljava/lang/String;[BI)Ljava/lang/String;

    move-result-object v0

    .line 1148
    .local v0, currentPwHash:Ljava/lang/String;
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    invoke-virtual {v6, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_3e

    goto :goto_1c
.end method

.method prepareOperationTimeout(IJLcom/android/server/BackupManagerService$BackupRestoreTask;)V
    .registers 10
    .parameter "token"
    .parameter "interval"
    .parameter "callback"

    .prologue
    .line 1726
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    monitor-enter v2

    .line 1727
    :try_start_3
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    new-instance v3, Lcom/android/server/BackupManagerService$Operation;

    const/4 v4, 0x0

    invoke-direct {v3, p0, v4, p4}, Lcom/android/server/BackupManagerService$Operation;-><init>(Lcom/android/server/BackupManagerService;ILcom/android/server/BackupManagerService$BackupRestoreTask;)V

    invoke-virtual {v1, p1, v3}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 1729
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v3, 0x7

    const/4 v4, 0x0

    invoke-virtual {v1, v3, p1, v4, p4}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    .line 1730
    .local v0, msg:Landroid/os/Message;
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v1, v0, p2, p3}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 1731
    monitor-exit v2

    .line 1732
    return-void

    .line 1731
    .end local v0           #msg:Landroid/os/Message;
    :catchall_1d
    move-exception v1

    monitor-exit v2
    :try_end_1f
    .catchall {:try_start_3 .. :try_end_1f} :catchall_1d

    throw v1
.end method

.method recordInitPendingLocked(ZLjava/lang/String;)V
    .registers 10
    .parameter "isPending"
    .parameter "transportName"

    .prologue
    .line 1230
    const-string v4, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "recordInitPendingLocked: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " on transport "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1233
    :try_start_22
    invoke-direct {p0, p2}, Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v2

    .line 1234
    .local v2, transport:Lcom/android/internal/backup/IBackupTransport;
    invoke-interface {v2}, Lcom/android/internal/backup/IBackupTransport;->transportDirName()Ljava/lang/String;

    move-result-object v3

    .line 1235
    .local v3, transportDirName:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    invoke-direct {v1, v4, v3}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 1236
    .local v1, stateDir:Ljava/io/File;
    new-instance v0, Ljava/io/File;

    const-string v4, "_need_init_"

    invoke-direct {v0, v1, v4}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 1238
    .local v0, initPendingFile:Ljava/io/File;
    if-eqz p1, :cond_48

    .line 1242
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    invoke-virtual {v4, p2}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_3f
    .catch Landroid/os/RemoteException; {:try_start_22 .. :try_end_3f} :catch_51

    .line 1244
    :try_start_3f
    new-instance v4, Ljava/io/FileOutputStream;

    invoke-direct {v4, v0}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    invoke-virtual {v4}, Ljava/io/FileOutputStream;->close()V
    :try_end_47
    .catch Ljava/io/IOException; {:try_start_3f .. :try_end_47} :catch_53
    .catch Landroid/os/RemoteException; {:try_start_3f .. :try_end_47} :catch_51

    .line 1256
    .end local v0           #initPendingFile:Ljava/io/File;
    .end local v1           #stateDir:Ljava/io/File;
    .end local v2           #transport:Lcom/android/internal/backup/IBackupTransport;
    .end local v3           #transportDirName:Ljava/lang/String;
    :goto_47
    return-void

    .line 1250
    .restart local v0       #initPendingFile:Ljava/io/File;
    .restart local v1       #stateDir:Ljava/io/File;
    .restart local v2       #transport:Lcom/android/internal/backup/IBackupTransport;
    .restart local v3       #transportDirName:Ljava/lang/String;
    :cond_48
    :try_start_48
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 1251
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mPendingInits:Ljava/util/HashSet;

    invoke-virtual {v4, p2}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z
    :try_end_50
    .catch Landroid/os/RemoteException; {:try_start_48 .. :try_end_50} :catch_51

    goto :goto_47

    .line 1253
    .end local v0           #initPendingFile:Ljava/io/File;
    .end local v1           #stateDir:Ljava/io/File;
    .end local v2           #transport:Lcom/android/internal/backup/IBackupTransport;
    .end local v3           #transportDirName:Ljava/lang/String;
    :catch_51
    move-exception v4

    goto :goto_47

    .line 1245
    .restart local v0       #initPendingFile:Ljava/io/File;
    .restart local v1       #stateDir:Ljava/io/File;
    .restart local v2       #transport:Lcom/android/internal/backup/IBackupTransport;
    .restart local v3       #transportDirName:Ljava/lang/String;
    :catch_53
    move-exception v4

    goto :goto_47
.end method

.method removeEverBackedUp(Ljava/lang/String;)V
    .registers 12
    .parameter "packageName"

    .prologue
    .line 1532
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Removing backed-up knowledge of "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1535
    iget-object v7, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    monitor-enter v7

    .line 1539
    :try_start_1b
    new-instance v5, Ljava/io/File;

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mBaseStateDir:Ljava/io/File;

    const-string v8, "processed.new"

    invoke-direct {v5, v6, v8}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_24
    .catchall {:try_start_1b .. :try_end_24} :catchall_b1

    .line 1540
    .local v5, tempKnownFile:Ljava/io/File;
    const/4 v2, 0x0

    .line 1542
    .local v2, known:Ljava/io/RandomAccessFile;
    :try_start_25
    new-instance v3, Ljava/io/RandomAccessFile;

    const-string v6, "rws"

    invoke-direct {v3, v5, v6}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_2c
    .catchall {:try_start_25 .. :try_end_2c} :catchall_aa
    .catch Ljava/io/IOException; {:try_start_25 .. :try_end_2c} :catch_a8

    .line 1543
    .end local v2           #known:Ljava/io/RandomAccessFile;
    .local v3, known:Ljava/io/RandomAccessFile;
    :try_start_2c
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v6, p1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    .line 1544
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v6}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_37
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_77

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 1545
    .local v4, s:Ljava/lang/String;
    invoke-virtual {v3, v4}, Ljava/io/RandomAccessFile;->writeUTF(Ljava/lang/String;)V
    :try_end_46
    .catchall {:try_start_2c .. :try_end_46} :catchall_bc
    .catch Ljava/io/IOException; {:try_start_2c .. :try_end_46} :catch_47

    goto :goto_37

    .line 1553
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v4           #s:Ljava/lang/String;
    :catch_47
    move-exception v0

    move-object v2, v3

    .line 1558
    .end local v3           #known:Ljava/io/RandomAccessFile;
    .local v0, e:Ljava/io/IOException;
    .restart local v2       #known:Ljava/io/RandomAccessFile;
    :goto_49
    :try_start_49
    const-string v6, "BackupManagerService"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Error rewriting "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v6, v8, v0}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 1559
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v6}, Ljava/util/HashSet;->clear()V

    .line 1560
    invoke-virtual {v5}, Ljava/io/File;->delete()Z

    .line 1561
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v6}, Ljava/io/File;->delete()Z
    :try_end_70
    .catchall {:try_start_49 .. :try_end_70} :catchall_aa

    .line 1563
    if-eqz v2, :cond_75

    :try_start_72
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->close()V
    :try_end_75
    .catchall {:try_start_72 .. :try_end_75} :catchall_b1
    .catch Ljava/io/IOException; {:try_start_72 .. :try_end_75} :catch_b6

    .line 1565
    .end local v0           #e:Ljava/io/IOException;
    :cond_75
    :goto_75
    :try_start_75
    monitor-exit v7
    :try_end_76
    .catchall {:try_start_75 .. :try_end_76} :catchall_b1

    .line 1566
    return-void

    .line 1548
    .end local v2           #known:Ljava/io/RandomAccessFile;
    .restart local v1       #i$:Ljava/util/Iterator;
    .restart local v3       #known:Ljava/io/RandomAccessFile;
    :cond_77
    :try_start_77
    invoke-virtual {v3}, Ljava/io/RandomAccessFile;->close()V
    :try_end_7a
    .catchall {:try_start_77 .. :try_end_7a} :catchall_bc
    .catch Ljava/io/IOException; {:try_start_77 .. :try_end_7a} :catch_47

    .line 1549
    const/4 v2, 0x0

    .line 1550
    .end local v3           #known:Ljava/io/RandomAccessFile;
    .restart local v2       #known:Ljava/io/RandomAccessFile;
    :try_start_7b
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v5, v6}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v6

    if-nez v6, :cond_b8

    .line 1551
    new-instance v6, Ljava/io/IOException;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Can\'t rename "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " to "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-direct {v6, v8}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v6
    :try_end_a8
    .catchall {:try_start_7b .. :try_end_a8} :catchall_aa
    .catch Ljava/io/IOException; {:try_start_7b .. :try_end_a8} :catch_a8

    .line 1553
    .end local v1           #i$:Ljava/util/Iterator;
    :catch_a8
    move-exception v0

    goto :goto_49

    .line 1563
    :catchall_aa
    move-exception v6

    :goto_ab
    if-eqz v2, :cond_b0

    :try_start_ad
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->close()V
    :try_end_b0
    .catchall {:try_start_ad .. :try_end_b0} :catchall_b1
    .catch Ljava/io/IOException; {:try_start_ad .. :try_end_b0} :catch_b4

    :cond_b0
    :goto_b0
    :try_start_b0
    throw v6

    .line 1565
    .end local v2           #known:Ljava/io/RandomAccessFile;
    .end local v5           #tempKnownFile:Ljava/io/File;
    :catchall_b1
    move-exception v6

    monitor-exit v7
    :try_end_b3
    .catchall {:try_start_b0 .. :try_end_b3} :catchall_b1

    throw v6

    .line 1563
    .restart local v2       #known:Ljava/io/RandomAccessFile;
    .restart local v5       #tempKnownFile:Ljava/io/File;
    :catch_b4
    move-exception v8

    goto :goto_b0

    :catch_b6
    move-exception v6

    goto :goto_75

    .restart local v1       #i$:Ljava/util/Iterator;
    :cond_b8
    if-eqz v2, :cond_75

    :try_start_ba
    throw v2
    :try_end_bb
    .catchall {:try_start_ba .. :try_end_bb} :catchall_b1
    .catch Ljava/io/IOException; {:try_start_ba .. :try_end_bb} :catch_b6

    goto :goto_75

    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #known:Ljava/io/RandomAccessFile;
    .restart local v3       #known:Ljava/io/RandomAccessFile;
    :catchall_bc
    move-exception v6

    move-object v2, v3

    .end local v3           #known:Ljava/io/RandomAccessFile;
    .restart local v2       #known:Ljava/io/RandomAccessFile;
    goto :goto_ab
.end method

.method removePackageParticipantsLocked([Ljava/lang/String;I)V
    .registers 11
    .parameter "packageNames"
    .parameter "oldUid"

    .prologue
    .line 1450
    if-nez p1, :cond_a

    .line 1451
    const-string v5, "BackupManagerService"

    const-string v6, "removePackageParticipants with null list"

    invoke-static {v5, v6}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1468
    :cond_9
    return-void

    .line 1455
    :cond_a
    const-string v5, "BackupManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "removePackageParticipantsLocked: uid="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " #"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    array-length v7, p1

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1457
    move-object v0, p1

    .local v0, arr$:[Ljava/lang/String;
    array-length v2, v0

    .local v2, len$:I
    const/4 v1, 0x0

    .local v1, i$:I
    :goto_30
    if-ge v1, v2, :cond_9

    aget-object v3, v0, v1

    .line 1459
    .local v3, pkg:Ljava/lang/String;
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v5, p2}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/HashSet;

    .line 1460
    .local v4, set:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-eqz v4, :cond_52

    invoke-virtual {v4, v3}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_52

    .line 1461
    invoke-direct {p0, v4, v3}, Lcom/android/server/BackupManagerService;->removePackageFromSetLocked(Ljava/util/HashSet;Ljava/lang/String;)V

    .line 1462
    invoke-virtual {v4}, Ljava/util/HashSet;->isEmpty()Z

    move-result v5

    if-eqz v5, :cond_52

    .line 1464
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v5, p2}, Landroid/util/SparseArray;->remove(I)V

    .line 1457
    :cond_52
    add-int/lit8 v1, v1, 0x1

    goto :goto_30
.end method

.method resetBackupState(Ljava/io/File;)V
    .registers 14
    .parameter "stateFileDir"

    .prologue
    .line 1262
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v9

    .line 1264
    :try_start_3
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mEverStoredApps:Ljava/util/HashSet;

    invoke-virtual {v8}, Ljava/util/HashSet;->clear()V

    .line 1265
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mEverStored:Ljava/io/File;

    invoke-virtual {v8}, Ljava/io/File;->delete()Z

    .line 1267
    const-wide/16 v10, 0x0

    iput-wide v10, p0, Lcom/android/server/BackupManagerService;->mCurrentToken:J

    .line 1268
    invoke-virtual {p0}, Lcom/android/server/BackupManagerService;->writeRestoreTokens()V

    .line 1271
    invoke-virtual {p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v1

    .local v1, arr$:[Ljava/io/File;
    array-length v4, v1

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_1a
    if-ge v3, v4, :cond_30

    aget-object v7, v1, v3

    .line 1273
    .local v7, sf:Ljava/io/File;
    invoke-virtual {v7}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v8

    const-string v10, "_need_init_"

    invoke-virtual {v8, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-nez v8, :cond_2d

    .line 1274
    invoke-virtual {v7}, Ljava/io/File;->delete()Z

    .line 1271
    :cond_2d
    add-int/lit8 v3, v3, 0x1

    goto :goto_1a

    .line 1277
    .end local v7           #sf:Ljava/io/File;
    :cond_30
    monitor-exit v9
    :try_end_31
    .catchall {:try_start_3 .. :try_end_31} :catchall_5e

    .line 1280
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    monitor-enter v9

    .line 1281
    :try_start_34
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v8}, Landroid/util/SparseArray;->size()I

    move-result v0

    .line 1282
    .local v0, N:I
    const/4 v2, 0x0

    .end local v3           #i$:I
    .local v2, i:I
    :goto_3b
    if-ge v2, v0, :cond_64

    .line 1283
    iget-object v8, p0, Lcom/android/server/BackupManagerService;->mBackupParticipants:Landroid/util/SparseArray;

    invoke-virtual {v8, v2}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/HashSet;

    .line 1284
    .local v6, participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    if-eqz v6, :cond_61

    .line 1285
    invoke-virtual {v6}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .local v3, i$:Ljava/util/Iterator;
    :goto_4b
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_61

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 1286
    .local v5, packageName:Ljava/lang/String;
    invoke-direct {p0, v5}, Lcom/android/server/BackupManagerService;->dataChangedImpl(Ljava/lang/String;)V

    goto :goto_4b

    .line 1290
    .end local v0           #N:I
    .end local v2           #i:I
    .end local v3           #i$:Ljava/util/Iterator;
    .end local v5           #packageName:Ljava/lang/String;
    .end local v6           #participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :catchall_5b
    move-exception v8

    monitor-exit v9
    :try_end_5d
    .catchall {:try_start_34 .. :try_end_5d} :catchall_5b

    throw v8

    .line 1277
    .end local v1           #arr$:[Ljava/io/File;
    .end local v4           #len$:I
    :catchall_5e
    move-exception v8

    :try_start_5f
    monitor-exit v9
    :try_end_60
    .catchall {:try_start_5f .. :try_end_60} :catchall_5e

    throw v8

    .line 1282
    .restart local v0       #N:I
    .restart local v1       #arr$:[Ljava/io/File;
    .restart local v2       #i:I
    .restart local v4       #len$:I
    .restart local v6       #participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :cond_61
    add-int/lit8 v2, v2, 0x1

    goto :goto_3b

    .line 1290
    .end local v6           #participants:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    :cond_64
    :try_start_64
    monitor-exit v9
    :try_end_65
    .catchall {:try_start_64 .. :try_end_65} :catchall_5b

    .line 1291
    return-void
.end method

.method public restoreAtInstall(Ljava/lang/String;I)V
    .registers 13
    .parameter "packageName"
    .parameter "token"

    .prologue
    .line 5432
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    const/16 v1, 0x3e8

    if-eq v0, v1, :cond_2b

    .line 5433
    const-string v0, "BackupManagerService"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Non-system process uid="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " attemping install-time restore"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 5466
    :goto_2a
    return-void

    .line 5438
    :cond_2b
    invoke-virtual {p0, p1}, Lcom/android/server/BackupManagerService;->getAvailableRestoreToken(Ljava/lang/String;)J

    move-result-wide v4

    .line 5439
    .local v4, restoreSet:J
    const-string v0, "BackupManagerService"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "restoreAtInstall pkg="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " token="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p2}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5442
    iget-boolean v0, p0, Lcom/android/server/BackupManagerService;->mAutoRestore:Z

    if-eqz v0, :cond_8d

    iget-boolean v0, p0, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    if-eqz v0, :cond_8d

    const-wide/16 v0, 0x0

    cmp-long v0, v4, v0

    if-eqz v0, :cond_8d

    .line 5450
    new-instance v6, Landroid/content/pm/PackageInfo;

    invoke-direct {v6}, Landroid/content/pm/PackageInfo;-><init>()V

    .line 5451
    .local v6, pkg:Landroid/content/pm/PackageInfo;
    iput-object p1, v6, Landroid/content/pm/PackageInfo;->packageName:Ljava/lang/String;

    .line 5453
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5454
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v9

    .line 5455
    .local v9, msg:Landroid/os/Message;
    new-instance v0, Lcom/android/server/BackupManagerService$RestoreParams;

    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    invoke-direct {p0, v1}, Lcom/android/server/BackupManagerService;->getTransport(Ljava/lang/String;)Lcom/android/internal/backup/IBackupTransport;

    move-result-object v2

    const/4 v3, 0x0

    const/4 v8, 0x1

    move-object v1, p0

    move v7, p2

    invoke-direct/range {v0 .. v8}, Lcom/android/server/BackupManagerService$RestoreParams;-><init>(Lcom/android/server/BackupManagerService;Lcom/android/internal/backup/IBackupTransport;Landroid/app/backup/IRestoreObserver;JLandroid/content/pm/PackageInfo;IZ)V

    iput-object v0, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 5457
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v0, v9}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    goto :goto_2a

    .line 5461
    .end local v6           #pkg:Landroid/content/pm/PackageInfo;
    .end local v9           #msg:Landroid/os/Message;
    :cond_8d
    const-string v0, "BackupManagerService"

    const-string v1, "No restore set -- skipping restore"

    invoke-static {v0, v1}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5463
    :try_start_94
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mPackageManagerBinder:Landroid/content/pm/IPackageManager;

    invoke-interface {v0, p2}, Landroid/content/pm/IPackageManager;->finishPackageInstall(I)V
    :try_end_99
    .catch Landroid/os/RemoteException; {:try_start_94 .. :try_end_99} :catch_9a

    goto :goto_2a

    .line 5464
    :catch_9a
    move-exception v0

    goto :goto_2a
.end method

.method public selectBackupTransport(Ljava/lang/String;)Ljava/lang/String;
    .registers 7
    .parameter "transport"

    .prologue
    .line 5328
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.BACKUP"

    const-string v3, "selectBackupTransport"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5330
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v2

    .line 5331
    const/4 v0, 0x0

    .line 5332
    .local v0, prevTransport:Ljava/lang/String;
    :try_start_d
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v1, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_4a

    .line 5333
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 5334
    iput-object p1, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    .line 5335
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v3, "backup_transport"

    invoke-static {v1, v3, p1}, Landroid/provider/Settings$Secure;->putString(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;)Z

    .line 5337
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "selectBackupTransport() set "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mCurrentTransport:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " returning "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 5342
    :goto_48
    monitor-exit v2

    return-object v0

    .line 5340
    :cond_4a
    const-string v1, "BackupManagerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Attempt to select unavailable transport "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_48

    .line 5343
    :catchall_63
    move-exception v1

    monitor-exit v2
    :try_end_65
    .catchall {:try_start_d .. :try_end_65} :catchall_63

    throw v1
.end method

.method public setAutoRestore(Z)V
    .registers 5
    .parameter "doAutoRestore"

    .prologue
    .line 5257
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.BACKUP"

    const-string v2, "setAutoRestore"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5260
    const-string v0, "BackupManagerService"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Auto restore => "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5262
    monitor-enter p0

    .line 5263
    :try_start_22
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "backup_auto_restore"

    if-eqz p1, :cond_34

    const/4 v0, 0x1

    :goto_2d
    invoke-static {v1, v2, v0}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 5265
    iput-boolean p1, p0, Lcom/android/server/BackupManagerService;->mAutoRestore:Z

    .line 5266
    monitor-exit p0

    .line 5267
    return-void

    .line 5263
    :cond_34
    const/4 v0, 0x0

    goto :goto_2d

    .line 5266
    :catchall_36
    move-exception v0

    monitor-exit p0
    :try_end_38
    .catchall {:try_start_22 .. :try_end_38} :catchall_36

    throw v0
.end method

.method public setBackupEnabled(Z)V
    .registers 12
    .parameter "enable"

    .prologue
    const/4 v4, 0x1

    const/4 v5, 0x0

    .line 5212
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v7, "android.permission.BACKUP"

    const-string v8, "setBackupEnabled"

    invoke-virtual {v6, v7, v8}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5215
    const-string v6, "BackupManagerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Backup enabled => "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5217
    iget-boolean v3, p0, Lcom/android/server/BackupManagerService;->mEnabled:Z

    .line 5218
    .local v3, wasEnabled:Z
    monitor-enter p0

    .line 5219
    :try_start_26
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v6}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v6

    const-string v7, "backup_enabled"

    if-eqz p1, :cond_49

    :goto_30
    invoke-static {v6, v7, v4}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 5221
    iput-boolean p1, p0, Lcom/android/server/BackupManagerService;->mEnabled:Z

    .line 5222
    monitor-exit p0
    :try_end_36
    .catchall {:try_start_26 .. :try_end_36} :catchall_4b

    .line 5224
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mQueueLock:Ljava/lang/Object;

    monitor-enter v5

    .line 5225
    if-eqz p1, :cond_4e

    if-nez v3, :cond_4e

    :try_start_3d
    iget-boolean v4, p0, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    if-eqz v4, :cond_4e

    .line 5227
    const-wide/32 v6, 0x36ee80

    invoke-direct {p0, v6, v7}, Lcom/android/server/BackupManagerService;->startBackupAlarmsLocked(J)V

    .line 5252
    :cond_47
    :goto_47
    monitor-exit v5
    :try_end_48
    .catchall {:try_start_3d .. :try_end_48} :catchall_88

    .line 5253
    return-void

    :cond_49
    move v4, v5

    .line 5219
    goto :goto_30

    .line 5222
    :catchall_4b
    move-exception v4

    :try_start_4c
    monitor-exit p0
    :try_end_4d
    .catchall {:try_start_4c .. :try_end_4d} :catchall_4b

    throw v4

    .line 5228
    :cond_4e
    if-nez p1, :cond_47

    .line 5230
    :try_start_50
    const-string v4, "BackupManagerService"

    const-string v6, "Opting out of backup"

    invoke-static {v4, v6}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 5232
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mRunBackupIntent:Landroid/app/PendingIntent;

    invoke-virtual {v4, v6}, Landroid/app/AlarmManager;->cancel(Landroid/app/PendingIntent;)V

    .line 5237
    if-eqz v3, :cond_47

    iget-boolean v4, p0, Lcom/android/server/BackupManagerService;->mProvisioned:Z

    if-eqz v4, :cond_47

    .line 5241
    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    monitor-enter v6
    :try_end_67
    .catchall {:try_start_50 .. :try_end_67} :catchall_88

    .line 5242
    :try_start_67
    new-instance v0, Ljava/util/HashSet;

    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mTransports:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v4

    invoke-direct {v0, v4}, Ljava/util/HashSet;-><init>(Ljava/util/Collection;)V

    .line 5243
    .local v0, allTransports:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    monitor-exit v6
    :try_end_73
    .catchall {:try_start_67 .. :try_end_73} :catchall_8b

    .line 5245
    :try_start_73
    invoke-virtual {v0}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_77
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_8e

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 5246
    .local v2, transport:Ljava/lang/String;
    const/4 v4, 0x1

    invoke-virtual {p0, v4, v2}, Lcom/android/server/BackupManagerService;->recordInitPendingLocked(ZLjava/lang/String;)V

    goto :goto_77

    .line 5252
    .end local v0           #allTransports:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #transport:Ljava/lang/String;
    :catchall_88
    move-exception v4

    monitor-exit v5
    :try_end_8a
    .catchall {:try_start_73 .. :try_end_8a} :catchall_88

    throw v4

    .line 5243
    :catchall_8b
    move-exception v4

    :try_start_8c
    monitor-exit v6
    :try_end_8d
    .catchall {:try_start_8c .. :try_end_8d} :catchall_8b

    :try_start_8d
    throw v4

    .line 5248
    .restart local v0       #allTransports:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    .restart local v1       #i$:Ljava/util/Iterator;
    :cond_8e
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mAlarmManager:Landroid/app/AlarmManager;

    const/4 v6, 0x0

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v7

    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mRunInitIntent:Landroid/app/PendingIntent;

    invoke-virtual {v4, v6, v7, v8, v9}, Landroid/app/AlarmManager;->set(IJLandroid/app/PendingIntent;)V
    :try_end_9a
    .catchall {:try_start_8d .. :try_end_9a} :catchall_88

    goto :goto_47
.end method

.method public setBackupPassword(Ljava/lang/String;Ljava/lang/String;)Z
    .registers 15
    .parameter "currentPw"
    .parameter "newPw"

    .prologue
    .line 1159
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v10, "android.permission.BACKUP"

    const-string v11, "setBackupPassword"

    invoke-virtual {v9, v10, v11}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1163
    const/16 v9, 0x2710

    invoke-virtual {p0, p1, v9}, Lcom/android/server/BackupManagerService;->passwordMatchesSaved(Ljava/lang/String;I)Z

    move-result v9

    if-nez v9, :cond_13

    .line 1164
    const/4 v9, 0x0

    .line 1209
    :cond_12
    :goto_12
    return v9

    .line 1168
    :cond_13
    if-eqz p2, :cond_1b

    invoke-virtual {p2}, Ljava/lang/String;->isEmpty()Z

    move-result v9

    if-eqz v9, :cond_3c

    .line 1169
    :cond_1b
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    invoke-virtual {v9}, Ljava/io/File;->exists()Z

    move-result v9

    if-eqz v9, :cond_34

    .line 1170
    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    invoke-virtual {v9}, Ljava/io/File;->delete()Z

    move-result v9

    if-nez v9, :cond_34

    .line 1172
    const-string v9, "BackupManagerService"

    const-string v10, "Unable to clear backup password"

    invoke-static {v9, v10}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1173
    const/4 v9, 0x0

    goto :goto_12

    .line 1176
    :cond_34
    const/4 v9, 0x0

    iput-object v9, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    .line 1177
    const/4 v9, 0x0

    iput-object v9, p0, Lcom/android/server/BackupManagerService;->mPasswordSalt:[B

    .line 1178
    const/4 v9, 0x1

    goto :goto_12

    .line 1183
    :cond_3c
    const/16 v9, 0x200

    :try_start_3e
    invoke-direct {p0, v9}, Lcom/android/server/BackupManagerService;->randomBytes(I)[B

    move-result-object v8

    .line 1184
    .local v8, salt:[B
    const/16 v9, 0x2710

    invoke-direct {p0, p2, v8, v9}, Lcom/android/server/BackupManagerService;->buildPasswordHash(Ljava/lang/String;[BI)Ljava/lang/String;
    :try_end_47
    .catch Ljava/io/IOException; {:try_start_3e .. :try_end_47} :catch_7e

    move-result-object v3

    .line 1186
    .local v3, newPwHash:Ljava/lang/String;
    const/4 v6, 0x0

    .local v6, pwf:Ljava/io/OutputStream;
    const/4 v0, 0x0

    .line 1187
    .local v0, buffer:Ljava/io/OutputStream;
    const/4 v4, 0x0

    .line 1189
    .local v4, out:Ljava/io/DataOutputStream;
    :try_start_4b
    new-instance v7, Ljava/io/FileOutputStream;

    iget-object v9, p0, Lcom/android/server/BackupManagerService;->mPasswordHashFile:Ljava/io/File;

    invoke-direct {v7, v9}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V
    :try_end_52
    .catchall {:try_start_4b .. :try_end_52} :catchall_88

    .line 1190
    .end local v6           #pwf:Ljava/io/OutputStream;
    .local v7, pwf:Ljava/io/OutputStream;
    :try_start_52
    new-instance v1, Ljava/io/BufferedOutputStream;

    invoke-direct {v1, v7}, Ljava/io/BufferedOutputStream;-><init>(Ljava/io/OutputStream;)V
    :try_end_57
    .catchall {:try_start_52 .. :try_end_57} :catchall_99

    .line 1191
    .end local v0           #buffer:Ljava/io/OutputStream;
    .local v1, buffer:Ljava/io/OutputStream;
    :try_start_57
    new-instance v5, Ljava/io/DataOutputStream;

    invoke-direct {v5, v1}, Ljava/io/DataOutputStream;-><init>(Ljava/io/OutputStream;)V
    :try_end_5c
    .catchall {:try_start_57 .. :try_end_5c} :catchall_9c

    .line 1194
    .end local v4           #out:Ljava/io/DataOutputStream;
    .local v5, out:Ljava/io/DataOutputStream;
    :try_start_5c
    array-length v9, v8

    invoke-virtual {v5, v9}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 1195
    invoke-virtual {v5, v8}, Ljava/io/DataOutputStream;->write([B)V

    .line 1196
    invoke-virtual {v5, v3}, Ljava/io/DataOutputStream;->writeUTF(Ljava/lang/String;)V

    .line 1197
    invoke-virtual {v5}, Ljava/io/DataOutputStream;->flush()V

    .line 1198
    iput-object v3, p0, Lcom/android/server/BackupManagerService;->mPasswordHash:Ljava/lang/String;

    .line 1199
    iput-object v8, p0, Lcom/android/server/BackupManagerService;->mPasswordSalt:[B
    :try_end_6d
    .catchall {:try_start_5c .. :try_end_6d} :catchall_a0

    .line 1200
    const/4 v9, 0x1

    .line 1202
    if-eqz v5, :cond_73

    :try_start_70
    invoke-virtual {v5}, Ljava/io/DataOutputStream;->close()V

    .line 1203
    :cond_73
    if-eqz v1, :cond_78

    invoke-virtual {v1}, Ljava/io/BufferedOutputStream;->close()V

    .line 1204
    :cond_78
    if-eqz v7, :cond_12

    invoke-virtual {v7}, Ljava/io/FileOutputStream;->close()V
    :try_end_7d
    .catch Ljava/io/IOException; {:try_start_70 .. :try_end_7d} :catch_7e

    goto :goto_12

    .line 1206
    .end local v1           #buffer:Ljava/io/OutputStream;
    .end local v3           #newPwHash:Ljava/lang/String;
    .end local v5           #out:Ljava/io/DataOutputStream;
    .end local v7           #pwf:Ljava/io/OutputStream;
    .end local v8           #salt:[B
    :catch_7e
    move-exception v2

    .line 1207
    .local v2, e:Ljava/io/IOException;
    const-string v9, "BackupManagerService"

    const-string v10, "Unable to set backup password"

    invoke-static {v9, v10}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1209
    const/4 v9, 0x0

    goto :goto_12

    .line 1202
    .end local v2           #e:Ljava/io/IOException;
    .restart local v0       #buffer:Ljava/io/OutputStream;
    .restart local v3       #newPwHash:Ljava/lang/String;
    .restart local v4       #out:Ljava/io/DataOutputStream;
    .restart local v6       #pwf:Ljava/io/OutputStream;
    .restart local v8       #salt:[B
    :catchall_88
    move-exception v9

    :goto_89
    if-eqz v4, :cond_8e

    :try_start_8b
    invoke-virtual {v4}, Ljava/io/DataOutputStream;->close()V

    .line 1203
    :cond_8e
    if-eqz v0, :cond_93

    invoke-virtual {v0}, Ljava/io/BufferedOutputStream;->close()V

    .line 1204
    :cond_93
    if-eqz v6, :cond_98

    invoke-virtual {v6}, Ljava/io/FileOutputStream;->close()V

    .line 1202
    :cond_98
    throw v9
    :try_end_99
    .catch Ljava/io/IOException; {:try_start_8b .. :try_end_99} :catch_7e

    .end local v6           #pwf:Ljava/io/OutputStream;
    .restart local v7       #pwf:Ljava/io/OutputStream;
    :catchall_99
    move-exception v9

    move-object v6, v7

    .end local v7           #pwf:Ljava/io/OutputStream;
    .restart local v6       #pwf:Ljava/io/OutputStream;
    goto :goto_89

    .end local v0           #buffer:Ljava/io/OutputStream;
    .end local v6           #pwf:Ljava/io/OutputStream;
    .restart local v1       #buffer:Ljava/io/OutputStream;
    .restart local v7       #pwf:Ljava/io/OutputStream;
    :catchall_9c
    move-exception v9

    move-object v0, v1

    .end local v1           #buffer:Ljava/io/OutputStream;
    .restart local v0       #buffer:Ljava/io/OutputStream;
    move-object v6, v7

    .end local v7           #pwf:Ljava/io/OutputStream;
    .restart local v6       #pwf:Ljava/io/OutputStream;
    goto :goto_89

    .end local v0           #buffer:Ljava/io/OutputStream;
    .end local v4           #out:Ljava/io/DataOutputStream;
    .end local v6           #pwf:Ljava/io/OutputStream;
    .restart local v1       #buffer:Ljava/io/OutputStream;
    .restart local v5       #out:Ljava/io/DataOutputStream;
    .restart local v7       #pwf:Ljava/io/OutputStream;
    :catchall_a0
    move-exception v9

    move-object v4, v5

    .end local v5           #out:Ljava/io/DataOutputStream;
    .restart local v4       #out:Ljava/io/DataOutputStream;
    move-object v0, v1

    .end local v1           #buffer:Ljava/io/OutputStream;
    .restart local v0       #buffer:Ljava/io/OutputStream;
    move-object v6, v7

    .end local v7           #pwf:Ljava/io/OutputStream;
    .restart local v6       #pwf:Ljava/io/OutputStream;
    goto :goto_89
.end method

.method public setBackupProvisioned(Z)V
    .registers 5
    .parameter "available"

    .prologue
    .line 5271
    iget-object v0, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.BACKUP"

    const-string v2, "setBackupProvisioned"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 5276
    return-void
.end method

.method signalFullBackupRestoreCompletion(Lcom/android/server/BackupManagerService$FullParams;)V
    .registers 5
    .parameter "params"

    .prologue
    .line 5145
    iget-object v1, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    monitor-enter v1

    .line 5146
    :try_start_3
    iget-object v0, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 5147
    iget-object v0, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v0}, Ljava/lang/Object;->notifyAll()V

    .line 5148
    monitor-exit v1

    .line 5149
    return-void

    .line 5148
    :catchall_10
    move-exception v0

    monitor-exit v1
    :try_end_12
    .catchall {:try_start_3 .. :try_end_12} :catchall_10

    throw v0
.end method

.method startBackupRestore(ILjava/lang/String;)Z
    .registers 10
    .parameter "token"
    .parameter "action"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 5084
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    monitor-enter v4

    .line 5085
    :try_start_3
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/BackupManagerService$FullParams;

    .line 5086
    .local v1, params:Lcom/android/server/BackupManagerService$FullParams;
    if-eqz v1, :cond_5a

    .line 5087
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mFullConfirmations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->delete(I)V

    .line 5089
    instance-of v3, v1, Lcom/android/server/BackupManagerService$FullBackupParams;

    if-eqz v3, :cond_57

    const/4 v2, 0x2

    .line 5090
    .local v2, verb:I
    :goto_17
    const/4 v3, 0x0

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->observer:Landroid/app/backup/IFullBackupRestoreObserver;

    .line 5091
    const-string v3, ""

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->curPassword:Ljava/lang/String;

    .line 5092
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    sget-object v5, Lmiui/provider/ExtraSettings$Secure;->APP_ENCRYPT_PASSWORD:Ljava/lang/String;

    invoke-static {v3, v5}, Landroid/provider/Settings$Secure;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, v1, Lcom/android/server/BackupManagerService$FullParams;->encryptPassword:Ljava/lang/String;

    .line 5096
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

    .line 5097
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mWakelock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v3}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 5098
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v3, v2, v1}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    .line 5099
    .local v0, msg:Landroid/os/Message;
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    invoke-virtual {v3, v0}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessage(Landroid/os/Message;)Z

    .line 5103
    .end local v0           #msg:Landroid/os/Message;
    .end local v2           #verb:I
    :goto_54
    monitor-exit v4

    .line 5104
    const/4 v3, 0x1

    return v3

    .line 5089
    :cond_57
    const/16 v2, 0xa

    goto :goto_17

    .line 5101
    :cond_5a
    const-string v3, "BackupManagerService"

    const-string v5, "Attempted to ack full backup/restore with invalid token"

    invoke-static {v3, v5}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_54

    .line 5103
    .end local v1           #params:Lcom/android/server/BackupManagerService$FullParams;
    :catchall_62
    move-exception v3

    monitor-exit v4
    :try_end_64
    .catchall {:try_start_3 .. :try_end_64} :catchall_62

    throw v3
.end method

.method startConfirmationTimeout(ILcom/android/server/BackupManagerService$FullParams;)V
    .registers 7
    .parameter "token"
    .parameter "params"

    .prologue
    .line 5129
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/16 v2, 0x9

    const/4 v3, 0x0

    invoke-virtual {v1, v2, p1, v3, p2}, Lcom/android/server/BackupManagerService$BackupHandler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    .line 5131
    .local v0, msg:Landroid/os/Message;
    iget-object v1, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const-wide/32 v2, 0xea60

    invoke-virtual {v1, v0, v2, v3}, Lcom/android/server/BackupManagerService$BackupHandler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 5132
    return-void
.end method

.method startConfirmationUi(ILjava/lang/String;)Z
    .registers 7
    .parameter "token"
    .parameter "action"

    .prologue
    .line 5114
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0, p2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 5115
    .local v0, confIntent:Landroid/content/Intent;
    const-string v2, "com.android.backupconfirm"

    const-string v3, "com.android.backupconfirm.BackupRestoreConfirmation"

    invoke-virtual {v0, v2, v3}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 5117
    const-string v2, "conftoken"

    invoke-virtual {v0, v2, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 5118
    const/high16 v2, 0x1000

    invoke-virtual {v0, v2}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 5119
    iget-object v2, p0, Lcom/android/server/BackupManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    :try_end_1b
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_1b} :catch_1d

    .line 5123
    const/4 v2, 0x1

    .end local v0           #confIntent:Landroid/content/Intent;
    :goto_1c
    return v2

    .line 5120
    :catch_1d
    move-exception v1

    .line 5121
    .local v1, e:Landroid/content/ActivityNotFoundException;
    const/4 v2, 0x0

    goto :goto_1c
.end method

.method waitForCompletion(Lcom/android/server/BackupManagerService$FullParams;)V
    .registers 4
    .parameter "params"

    .prologue
    .line 5135
    iget-object v1, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    monitor-enter v1

    .line 5136
    :goto_3
    :try_start_3
    iget-object v0, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z
    :try_end_8
    .catchall {:try_start_3 .. :try_end_8} :catchall_15

    move-result v0

    if-nez v0, :cond_13

    .line 5138
    :try_start_b
    iget-object v0, p1, Lcom/android/server/BackupManagerService$FullParams;->latch:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v0}, Ljava/lang/Object;->wait()V
    :try_end_10
    .catchall {:try_start_b .. :try_end_10} :catchall_15
    .catch Ljava/lang/InterruptedException; {:try_start_b .. :try_end_10} :catch_11

    goto :goto_3

    .line 5139
    :catch_11
    move-exception v0

    goto :goto_3

    .line 5141
    :cond_13
    :try_start_13
    monitor-exit v1

    .line 5142
    return-void

    .line 5141
    :catchall_15
    move-exception v0

    monitor-exit v1
    :try_end_17
    .catchall {:try_start_13 .. :try_end_17} :catchall_15

    throw v0
.end method

.method waitUntilOperationComplete(I)Z
    .registers 8
    .parameter "token"

    .prologue
    const/4 v4, 0x1

    .line 1738
    const/4 v1, 0x0

    .line 1739
    .local v1, finalState:I
    const/4 v2, 0x0

    .line 1740
    .local v2, op:Lcom/android/server/BackupManagerService$Operation;
    iget-object v5, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    monitor-enter v5

    .line 1742
    :goto_6
    :try_start_6
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOperations:Landroid/util/SparseArray;

    invoke-virtual {v3, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    move-object v0, v3

    check-cast v0, Lcom/android/server/BackupManagerService$Operation;

    move-object v2, v0

    .line 1743
    if-nez v2, :cond_1d

    .line 1759
    :goto_12
    monitor-exit v5
    :try_end_13
    .catchall {:try_start_6 .. :try_end_13} :catchall_2c

    .line 1761
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mBackupHandler:Lcom/android/server/BackupManagerService$BackupHandler;

    const/4 v5, 0x7

    invoke-virtual {v3, v5}, Lcom/android/server/BackupManagerService$BackupHandler;->removeMessages(I)V

    .line 1764
    if-ne v1, v4, :cond_2f

    move v3, v4

    :goto_1c
    return v3

    .line 1747
    :cond_1d
    :try_start_1d
    iget v3, v2, Lcom/android/server/BackupManagerService$Operation;->state:I
    :try_end_1f
    .catchall {:try_start_1d .. :try_end_1f} :catchall_2c

    if-nez v3, :cond_29

    .line 1749
    :try_start_21
    iget-object v3, p0, Lcom/android/server/BackupManagerService;->mCurrentOpLock:Ljava/lang/Object;

    invoke-virtual {v3}, Ljava/lang/Object;->wait()V
    :try_end_26
    .catchall {:try_start_21 .. :try_end_26} :catchall_2c
    .catch Ljava/lang/InterruptedException; {:try_start_21 .. :try_end_26} :catch_27

    goto :goto_6

    .line 1750
    :catch_27
    move-exception v3

    goto :goto_6

    .line 1754
    :cond_29
    :try_start_29
    iget v1, v2, Lcom/android/server/BackupManagerService$Operation;->state:I

    .line 1755
    goto :goto_12

    .line 1759
    :catchall_2c
    move-exception v3

    monitor-exit v5
    :try_end_2e
    .catchall {:try_start_29 .. :try_end_2e} :catchall_2c

    throw v3

    .line 1764
    :cond_2f
    const/4 v3, 0x0

    goto :goto_1c
.end method

.method writeRestoreTokens()V
    .registers 8

    .prologue
    .line 1573
    :try_start_0
    new-instance v0, Ljava/io/RandomAccessFile;

    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mTokenFile:Ljava/io/File;

    const-string v5, "rwd"

    invoke-direct {v0, v4, v5}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 1576
    .local v0, af:Ljava/io/RandomAccessFile;
    const/4 v4, 0x1

    invoke-virtual {v0, v4}, Ljava/io/RandomAccessFile;->writeInt(I)V

    .line 1579
    iget-wide v4, p0, Lcom/android/server/BackupManagerService;->mAncestralToken:J

    invoke-virtual {v0, v4, v5}, Ljava/io/RandomAccessFile;->writeLong(J)V

    .line 1580
    iget-wide v4, p0, Lcom/android/server/BackupManagerService;->mCurrentToken:J

    invoke-virtual {v0, v4, v5}, Ljava/io/RandomAccessFile;->writeLong(J)V

    .line 1583
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    if-nez v4, :cond_23

    .line 1584
    const/4 v4, -0x1

    invoke-virtual {v0, v4}, Ljava/io/RandomAccessFile;->writeInt(I)V

    .line 1593
    :cond_1f
    invoke-virtual {v0}, Ljava/io/RandomAccessFile;->close()V

    .line 1597
    .end local v0           #af:Ljava/io/RandomAccessFile;
    :goto_22
    return-void

    .line 1586
    .restart local v0       #af:Ljava/io/RandomAccessFile;
    :cond_23
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    invoke-interface {v4}, Ljava/util/Set;->size()I

    move-result v4

    invoke-virtual {v0, v4}, Ljava/io/RandomAccessFile;->writeInt(I)V

    .line 1587
    const-string v4, "BackupManagerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Ancestral packages:  "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Slog;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1588
    iget-object v4, p0, Lcom/android/server/BackupManagerService;->mAncestralPackages:Ljava/util/Set;

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :goto_50
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1f

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    .line 1589
    .local v3, pkgName:Ljava/lang/String;
    invoke-virtual {v0, v3}, Ljava/io/RandomAccessFile;->writeUTF(Ljava/lang/String;)V
    :try_end_5f
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_5f} :catch_60

    goto :goto_50

    .line 1594
    .end local v0           #af:Ljava/io/RandomAccessFile;
    .end local v2           #i$:Ljava/util/Iterator;
    .end local v3           #pkgName:Ljava/lang/String;
    :catch_60
    move-exception v1

    .line 1595
    .local v1, e:Ljava/io/IOException;
    const-string v4, "BackupManagerService"

    const-string v5, "Unable to write token file:"

    invoke-static {v4, v5, v1}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_22
.end method
