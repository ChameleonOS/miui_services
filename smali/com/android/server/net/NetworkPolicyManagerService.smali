.class public Lcom/android/server/net/NetworkPolicyManagerService;
.super Landroid/net/INetworkPolicyManager$Stub;
.source "NetworkPolicyManagerService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;,
        Lcom/android/server/net/NetworkPolicyManagerService$Injector;
    }
.end annotation


# static fields
.field public static final ACTION_ALLOW_BACKGROUND:Ljava/lang/String; = "com.android.server.net.action.ALLOW_BACKGROUND"

.field public static final ACTION_SNOOZE_WARNING:Ljava/lang/String; = "com.android.server.net.action.SNOOZE_WARNING"

.field private static final ATTR_APP_ID:Ljava/lang/String; = "appId"

.field private static final ATTR_CYCLE_DAY:Ljava/lang/String; = "cycleDay"

.field private static final ATTR_CYCLE_TIMEZONE:Ljava/lang/String; = "cycleTimezone"

.field private static final ATTR_INFERRED:Ljava/lang/String; = "inferred"

.field private static final ATTR_LAST_LIMIT_SNOOZE:Ljava/lang/String; = "lastLimitSnooze"

.field private static final ATTR_LAST_SNOOZE:Ljava/lang/String; = "lastSnooze"

.field private static final ATTR_LAST_WARNING_SNOOZE:Ljava/lang/String; = "lastWarningSnooze"

.field private static final ATTR_LIMIT_BYTES:Ljava/lang/String; = "limitBytes"

.field private static final ATTR_METERED:Ljava/lang/String; = "metered"

.field private static final ATTR_NETWORK_ID:Ljava/lang/String; = "networkId"

.field private static final ATTR_NETWORK_TEMPLATE:Ljava/lang/String; = "networkTemplate"

.field private static final ATTR_POLICY:Ljava/lang/String; = "policy"

.field private static final ATTR_RESTRICT_BACKGROUND:Ljava/lang/String; = "restrictBackground"

.field private static final ATTR_SUBSCRIBER_ID:Ljava/lang/String; = "subscriberId"

.field private static final ATTR_UID:Ljava/lang/String; = "uid"

.field private static final ATTR_VERSION:Ljava/lang/String; = "version"

.field private static final ATTR_WARNING_BYTES:Ljava/lang/String; = "warningBytes"

.field private static final LOGD:Z = false

.field private static final LOGV:Z = false

.field private static final MSG_ADVISE_PERSIST_THRESHOLD:I = 0x7

.field private static final MSG_FOREGROUND_ACTIVITIES_CHANGED:I = 0x3

.field private static final MSG_LIMIT_REACHED:I = 0x5

.field private static final MSG_METERED_IFACES_CHANGED:I = 0x2

.field private static final MSG_PROCESS_DIED:I = 0x4

.field private static final MSG_RESTRICT_BACKGROUND_CHANGED:I = 0x6

.field private static final MSG_RULES_CHANGED:I = 0x1

.field private static final MSG_SCREEN_ON_CHANGED:I = 0x8

.field private static final TAG:Ljava/lang/String; = "NetworkPolicy"

.field private static final TAG_ALLOW_BACKGROUND:Ljava/lang/String; = "NetworkPolicy:allowBackground"

.field private static final TAG_APP_POLICY:Ljava/lang/String; = "app-policy"

.field private static final TAG_NETWORK_POLICY:Ljava/lang/String; = "network-policy"

.field private static final TAG_POLICY_LIST:Ljava/lang/String; = "policy-list"

.field private static final TAG_UID_POLICY:Ljava/lang/String; = "uid-policy"

.field private static final TIME_CACHE_MAX_AGE:J = 0x5265c00L

.field public static final TYPE_LIMIT:I = 0x2

.field public static final TYPE_LIMIT_SNOOZED:I = 0x3

.field public static final TYPE_WARNING:I = 0x1

.field private static final VERSION_ADDED_INFERRED:I = 0x7

.field private static final VERSION_ADDED_METERED:I = 0x4

.field private static final VERSION_ADDED_NETWORK_ID:I = 0x9

.field private static final VERSION_ADDED_RESTRICT_BACKGROUND:I = 0x3

.field private static final VERSION_ADDED_SNOOZE:I = 0x2

.field private static final VERSION_ADDED_TIMEZONE:I = 0x6

.field private static final VERSION_INIT:I = 0x1

.field private static final VERSION_LATEST:I = 0x9

.field private static final VERSION_SPLIT_SNOOZE:I = 0x5

.field private static final VERSION_SWITCH_APP_ID:I = 0x8


# instance fields
.field private mActiveNotifs:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private final mActivityManager:Landroid/app/IActivityManager;

.field private mAlertObserver:Landroid/net/INetworkManagementEventObserver;

.field private mAllowReceiver:Landroid/content/BroadcastReceiver;

.field private mAppPolicy:Landroid/util/SparseIntArray;

.field private mConnManager:Landroid/net/IConnectivityManager;

.field private mConnReceiver:Landroid/content/BroadcastReceiver;

.field private final mContext:Landroid/content/Context;

.field private final mHandler:Landroid/os/Handler;

.field private mHandlerCallback:Landroid/os/Handler$Callback;

.field private final mHandlerThread:Landroid/os/HandlerThread;

.field private final mListeners:Landroid/os/RemoteCallbackList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/RemoteCallbackList",
            "<",
            "Landroid/net/INetworkPolicyListener;",
            ">;"
        }
    .end annotation
.end field

.field private mMeteredIfaces:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private final mNetworkManager:Landroid/os/INetworkManagementService;

.field private mNetworkPolicy:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Landroid/net/NetworkTemplate;",
            "Landroid/net/NetworkPolicy;",
            ">;"
        }
    .end annotation
.end field

.field private mNetworkRules:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Landroid/net/NetworkPolicy;",
            "[",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private final mNetworkStats:Landroid/net/INetworkStatsService;

.field private mNotifManager:Landroid/app/INotificationManager;

.field private mOverLimitNotified:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Landroid/net/NetworkTemplate;",
            ">;"
        }
    .end annotation
.end field

.field private mPackageReceiver:Landroid/content/BroadcastReceiver;

.field private final mPolicyFile:Lcom/android/internal/os/AtomicFile;

.field private final mPowerManager:Landroid/os/IPowerManager;

.field private mProcessObserver:Landroid/app/IProcessObserver;

.field private volatile mRestrictBackground:Z

.field private final mRulesLock:Ljava/lang/Object;

.field private volatile mScreenOn:Z

.field private mScreenReceiver:Landroid/content/BroadcastReceiver;

.field private mSnoozeWarningReceiver:Landroid/content/BroadcastReceiver;

.field private mStatsReceiver:Landroid/content/BroadcastReceiver;

.field private final mSuppressDefaultPolicy:Z

.field private final mTime:Landroid/util/TrustedTime;

.field private mUidForeground:Landroid/util/SparseBooleanArray;

.field private mUidPidForeground:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Landroid/util/SparseBooleanArray;",
            ">;"
        }
    .end annotation
.end field

.field private mUidRules:Landroid/util/SparseIntArray;

.field private mWifiConfigReceiver:Landroid/content/BroadcastReceiver;

.field private mWifiStateReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;)V
    .registers 15
    .parameter "context"
    .parameter "activityManager"
    .parameter "powerManager"
    .parameter "networkStats"
    .parameter "networkManagement"

    .prologue
    .line 339
    invoke-static {p1}, Landroid/util/NtpTrustedTime;->getInstance(Landroid/content/Context;)Landroid/util/NtpTrustedTime;

    move-result-object v6

    invoke-static {}, Lcom/android/server/net/NetworkPolicyManagerService;->getSystemDir()Ljava/io/File;

    move-result-object v7

    const/4 v8, 0x0

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move-object v5, p5

    invoke-direct/range {v0 .. v8}, Lcom/android/server/net/NetworkPolicyManagerService;-><init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;Landroid/util/TrustedTime;Ljava/io/File;Z)V

    .line 341
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;Landroid/util/TrustedTime;Ljava/io/File;Z)V
    .registers 12
    .parameter "context"
    .parameter "activityManager"
    .parameter "powerManager"
    .parameter "networkStats"
    .parameter "networkManagement"
    .parameter "time"
    .parameter "systemDir"
    .parameter "suppressDefaultPolicy"

    .prologue
    .line 350
    invoke-direct {p0}, Landroid/net/INetworkPolicyManager$Stub;-><init>()V

    .line 293
    new-instance v0, Ljava/lang/Object;

    invoke-direct/range {v0 .. v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    .line 301
    invoke-static {}, Lcom/google/android/collect/Maps;->newHashMap()Ljava/util/HashMap;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    .line 303
    invoke-static {}, Lcom/google/android/collect/Maps;->newHashMap()Ljava/util/HashMap;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkRules:Ljava/util/HashMap;

    .line 306
    new-instance v0, Landroid/util/SparseIntArray;

    invoke-direct {v0}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    .line 308
    new-instance v0, Landroid/util/SparseIntArray;

    invoke-direct {v0}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    .line 311
    invoke-static {}, Lcom/google/android/collect/Sets;->newHashSet()Ljava/util/HashSet;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    .line 313
    invoke-static {}, Lcom/google/android/collect/Sets;->newHashSet()Ljava/util/HashSet;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mOverLimitNotified:Ljava/util/HashSet;

    .line 316
    invoke-static {}, Lcom/google/android/collect/Sets;->newHashSet()Ljava/util/HashSet;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    .line 319
    new-instance v0, Landroid/util/SparseBooleanArray;

    invoke-direct {v0}, Landroid/util/SparseBooleanArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    .line 320
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidPidForeground:Landroid/util/SparseArray;

    .line 323
    new-instance v0, Landroid/os/RemoteCallbackList;

    invoke-direct {v0}, Landroid/os/RemoteCallbackList;-><init>()V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mListeners:Landroid/os/RemoteCallbackList;

    .line 445
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$1;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$1;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mProcessObserver:Landroid/app/IProcessObserver;

    .line 462
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$2;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$2;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mScreenReceiver:Landroid/content/BroadcastReceiver;

    .line 473
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$3;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$3;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPackageReceiver:Landroid/content/BroadcastReceiver;

    .line 511
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$4;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$4;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mStatsReceiver:Landroid/content/BroadcastReceiver;

    .line 529
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$5;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$5;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAllowReceiver:Landroid/content/BroadcastReceiver;

    .line 543
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$6;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$6;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mSnoozeWarningReceiver:Landroid/content/BroadcastReceiver;

    .line 557
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$7;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$7;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mWifiConfigReceiver:Landroid/content/BroadcastReceiver;

    .line 585
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$8;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$8;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mWifiStateReceiver:Landroid/content/BroadcastReceiver;

    .line 626
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$9;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$9;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAlertObserver:Landroid/net/INetworkManagementEventObserver;

    .line 911
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$10;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$10;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mConnReceiver:Landroid/content/BroadcastReceiver;

    .line 1829
    new-instance v0, Lcom/android/server/net/NetworkPolicyManagerService$11;

    invoke-direct {v0, p0}, Lcom/android/server/net/NetworkPolicyManagerService$11;-><init>(Lcom/android/server/net/NetworkPolicyManagerService;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandlerCallback:Landroid/os/Handler$Callback;

    .line 351
    const-string v0, "missing context"

    invoke-static {p1, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    .line 352
    const-string v0, "missing activityManager"

    invoke-static {p2, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/IActivityManager;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActivityManager:Landroid/app/IActivityManager;

    .line 353
    const-string v0, "missing powerManager"

    invoke-static {p3, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/IPowerManager;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPowerManager:Landroid/os/IPowerManager;

    .line 354
    const-string v0, "missing networkStats"

    invoke-static {p4, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/INetworkStatsService;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkStats:Landroid/net/INetworkStatsService;

    .line 355
    const-string v0, "missing networkManagement"

    invoke-static {p5, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/INetworkManagementService;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    .line 356
    const-string v0, "missing TrustedTime"

    invoke-static {p6, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/util/TrustedTime;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mTime:Landroid/util/TrustedTime;

    .line 358
    new-instance v0, Landroid/os/HandlerThread;

    const-string v1, "NetworkPolicy"

    invoke-direct {v0, v1}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    .line 359
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->start()V

    .line 360
    new-instance v0, Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandlerThread:Landroid/os/HandlerThread;

    invoke-virtual {v1}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v1

    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandlerCallback:Landroid/os/Handler$Callback;

    invoke-direct {v0, v1, v2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;Landroid/os/Handler$Callback;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    .line 362
    iput-boolean p8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mSuppressDefaultPolicy:Z

    .line 364
    new-instance v0, Lcom/android/internal/os/AtomicFile;

    new-instance v1, Ljava/io/File;

    const-string v2, "netpolicy.xml"

    invoke-direct {v1, p7, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-direct {v0, v1}, Lcom/android/internal/os/AtomicFile;-><init>(Ljava/io/File;)V

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    .line 365
    return-void
.end method

.method static synthetic access$000(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/os/Handler;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$100(Lcom/android/server/net/NetworkPolicyManagerService;)Ljava/lang/Object;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkPolicy;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 172
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->addNetworkPolicyLocked(Landroid/net/NetworkPolicy;)V

    return-void
.end method

.method static synthetic access$1100(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V

    return-void
.end method

.method static synthetic access$1200(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/content/Context;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1300(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->ensureActiveMobilePolicyLocked()V

    return-void
.end method

.method static synthetic access$1400(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/os/RemoteCallbackList;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mListeners:Landroid/os/RemoteCallbackList;

    return-object v0
.end method

.method static synthetic access$1500(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/util/SparseArray;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidPidForeground:Landroid/util/SparseArray;

    return-object v0
.end method

.method static synthetic access$1600(Lcom/android/server/net/NetworkPolicyManagerService;I)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 172
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->computeUidForegroundLocked(I)V

    return-void
.end method

.method static synthetic access$1700(Lcom/android/server/net/NetworkPolicyManagerService;)Ljava/util/HashSet;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    return-object v0
.end method

.method static synthetic access$1800(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/net/INetworkStatsService;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkStats:Landroid/net/INetworkStatsService;

    return-object v0
.end method

.method static synthetic access$1900(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateScreenOn()V

    return-void
.end method

.method static synthetic access$200(Lcom/android/server/net/NetworkPolicyManagerService;I)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 172
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForAppLocked(I)V

    return-void
.end method

.method static synthetic access$300(Lcom/android/server/net/NetworkPolicyManagerService;)Landroid/util/SparseIntArray;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    return-object v0
.end method

.method static synthetic access$400(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    return-void
.end method

.method static synthetic access$500(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->maybeRefreshTrustedTime()V

    return-void
.end method

.method static synthetic access$600(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V

    return-void
.end method

.method static synthetic access$700(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 172
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    return-void
.end method

.method static synthetic access$800(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkTemplate;I)V
    .registers 3
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 172
    invoke-direct {p0, p1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->performSnooze(Landroid/net/NetworkTemplate;I)V

    return-void
.end method

.method static synthetic access$900(Lcom/android/server/net/NetworkPolicyManagerService;)Ljava/util/HashMap;
    .registers 2
    .parameter "x0"

    .prologue
    .line 172
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    return-object v0
.end method

.method private addNetworkPolicyLocked(Landroid/net/NetworkPolicy;)V
    .registers 4
    .parameter "policy"

    .prologue
    .line 1453
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    iget-object v1, p1, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v0, v1, p1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1455
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V

    .line 1456
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V

    .line 1457
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 1458
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1459
    return-void
.end method

.method private static buildAllowBackgroundDataIntent()Landroid/content/Intent;
    .registers 2

    .prologue
    .line 2036
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.android.server.net.action.ALLOW_BACKGROUND"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    return-object v0
.end method

.method private static buildNetworkOverLimitIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;
    .registers 5
    .parameter "template"

    .prologue
    .line 2046
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 2047
    .local v0, intent:Landroid/content/Intent;
    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.android.systemui"

    const-string v3, "com.android.systemui.net.NetworkOverLimitActivity"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 2049
    const/high16 v1, 0x1000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 2050
    const-string v1, "android.net.NETWORK_TEMPLATE"

    invoke-virtual {v0, v1, p0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 2051
    return-object v0
.end method

.method private buildNotificationTag(Landroid/net/NetworkPolicy;I)Ljava/lang/String;
    .registers 5
    .parameter "policy"
    .parameter "type"

    .prologue
    .line 738
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "NetworkPolicy:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p1, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v1}, Landroid/net/NetworkTemplate;->hashCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ":"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private static buildSnoozeWarningIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;
    .registers 3
    .parameter "template"

    .prologue
    .line 2040
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.android.server.net.action.SNOOZE_WARNING"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 2041
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "android.net.NETWORK_TEMPLATE"

    invoke-virtual {v0, v1, p0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 2042
    return-object v0
.end method

.method private static buildViewDataUsageIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;
    .registers 3
    .parameter "template"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 2056
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 2057
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "android.intent.action.VIEW_DATA_USAGE_SUMMARY"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 2058
    const/high16 v1, 0x1000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 2059
    const-string v1, "android.net.NETWORK_TEMPLATE"

    invoke-virtual {v0, v1, p0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 2060
    return-object v0
.end method

.method private cancelNotification(Ljava/lang/String;)V
    .registers 5
    .parameter "tag"

    .prologue
    .line 899
    :try_start_0
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v0

    .line 900
    .local v0, packageName:Ljava/lang/String;
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNotifManager:Landroid/app/INotificationManager;

    const/4 v2, 0x0

    invoke-interface {v1, v0, p1, v2}, Landroid/app/INotificationManager;->cancelNotificationWithTag(Ljava/lang/String;Ljava/lang/String;I)V
    :try_end_c
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_c} :catch_d

    .line 905
    .end local v0           #packageName:Ljava/lang/String;
    :goto_c
    return-void

    .line 902
    :catch_d
    move-exception v1

    goto :goto_c
.end method

.method private static collectKeys(Landroid/util/SparseBooleanArray;Landroid/util/SparseBooleanArray;)V
    .registers 6
    .parameter "source"
    .parameter "target"

    .prologue
    .line 2076
    invoke-virtual {p0}, Landroid/util/SparseBooleanArray;->size()I

    move-result v1

    .line 2077
    .local v1, size:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_5
    if-ge v0, v1, :cond_12

    .line 2078
    invoke-virtual {p0, v0}, Landroid/util/SparseBooleanArray;->keyAt(I)I

    move-result v2

    const/4 v3, 0x1

    invoke-virtual {p1, v2, v3}, Landroid/util/SparseBooleanArray;->put(IZ)V

    .line 2077
    add-int/lit8 v0, v0, 0x1

    goto :goto_5

    .line 2080
    :cond_12
    return-void
.end method

.method private static collectKeys(Landroid/util/SparseIntArray;Landroid/util/SparseBooleanArray;)V
    .registers 6
    .parameter "source"
    .parameter "target"

    .prologue
    .line 2069
    invoke-virtual {p0}, Landroid/util/SparseIntArray;->size()I

    move-result v1

    .line 2070
    .local v1, size:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_5
    if-ge v0, v1, :cond_12

    .line 2071
    invoke-virtual {p0, v0}, Landroid/util/SparseIntArray;->keyAt(I)I

    move-result v2

    const/4 v3, 0x1

    invoke-virtual {p1, v2, v3}, Landroid/util/SparseBooleanArray;->put(IZ)V

    .line 2070
    add-int/lit8 v0, v0, 0x1

    goto :goto_5

    .line 2073
    :cond_12
    return-void
.end method

.method private computeUidForegroundLocked(I)V
    .registers 9
    .parameter "uid"

    .prologue
    .line 1710
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidPidForeground:Landroid/util/SparseArray;

    invoke-virtual {v5, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/util/SparseBooleanArray;

    .line 1713
    .local v2, pidForeground:Landroid/util/SparseBooleanArray;
    const/4 v4, 0x0

    .line 1714
    .local v4, uidForeground:Z
    invoke-virtual {v2}, Landroid/util/SparseBooleanArray;->size()I

    move-result v3

    .line 1715
    .local v3, size:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_e
    if-ge v0, v3, :cond_17

    .line 1716
    invoke-virtual {v2, v0}, Landroid/util/SparseBooleanArray;->valueAt(I)Z

    move-result v5

    if-eqz v5, :cond_29

    .line 1717
    const/4 v4, 0x1

    .line 1722
    :cond_17
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    const/4 v6, 0x0

    invoke-virtual {v5, p1, v6}, Landroid/util/SparseBooleanArray;->get(IZ)Z

    move-result v1

    .line 1723
    .local v1, oldUidForeground:Z
    if-eq v1, v4, :cond_28

    .line 1725
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    invoke-virtual {v5, p1, v4}, Landroid/util/SparseBooleanArray;->put(IZ)V

    .line 1726
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForUidLocked(I)V

    .line 1728
    :cond_28
    return-void

    .line 1715
    .end local v1           #oldUidForeground:Z
    :cond_29
    add-int/lit8 v0, v0, 0x1

    goto :goto_e
.end method

.method private currentTimeMillis()J
    .registers 3

    .prologue
    .line 2032
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mTime:Landroid/util/TrustedTime;

    invoke-interface {v0}, Landroid/util/TrustedTime;->hasCache()Z

    move-result v0

    if-eqz v0, :cond_f

    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mTime:Landroid/util/TrustedTime;

    invoke-interface {v0}, Landroid/util/TrustedTime;->currentTimeMillis()J

    move-result-wide v0

    :goto_e
    return-wide v0

    :cond_f
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    goto :goto_e
.end method

.method private static dumpSparseBooleanArray(Ljava/io/PrintWriter;Landroid/util/SparseBooleanArray;)V
    .registers 6
    .parameter "fout"
    .parameter "value"

    .prologue
    .line 2083
    const-string v2, "["

    invoke-virtual {p0, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 2084
    invoke-virtual {p1}, Landroid/util/SparseBooleanArray;->size()I

    move-result v1

    .line 2085
    .local v1, size:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_a
    if-ge v0, v1, :cond_3a

    .line 2086
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, v0}, Landroid/util/SparseBooleanArray;->keyAt(I)I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p1, v0}, Landroid/util/SparseBooleanArray;->valueAt(I)Z

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 2087
    add-int/lit8 v2, v1, -0x1

    if-ge v0, v2, :cond_37

    const-string v2, ","

    invoke-virtual {p0, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 2085
    :cond_37
    add-int/lit8 v0, v0, 0x1

    goto :goto_a

    .line 2089
    :cond_3a
    const-string v2, "]"

    invoke-virtual {p0, v2}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 2090
    return-void
.end method

.method private enqueueNotification(Landroid/net/NetworkPolicy;IJ)V
    .registers 22
    .parameter "policy"
    .parameter "type"
    .parameter "totalBytes"

    .prologue
    .line 754
    invoke-direct/range {p0 .. p2}, Lcom/android/server/net/NetworkPolicyManagerService;->buildNotificationTag(Landroid/net/NetworkPolicy;I)Ljava/lang/String;

    move-result-object v3

    .line 755
    .local v3, tag:Ljava/lang/String;
    new-instance v8, Landroid/app/Notification$Builder;

    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-direct {v8, v1}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    .line 756
    .local v8, builder:Landroid/app/Notification$Builder;
    const/4 v1, 0x1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setOnlyAlertOnce(Z)Landroid/app/Notification$Builder;

    .line 757
    const-wide/16 v4, 0x0

    invoke-virtual {v8, v4, v5}, Landroid/app/Notification$Builder;->setWhen(J)Landroid/app/Notification$Builder;

    .line 759
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v12

    .line 760
    .local v12, res:Landroid/content/res/Resources;
    packed-switch p2, :pswitch_data_162

    .line 852
    :goto_21
    :try_start_21
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    .line 853
    .local v2, packageName:Ljava/lang/String;
    const/4 v1, 0x1

    new-array v6, v1, [I

    .line 854
    .local v6, idReceived:[I
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNotifManager:Landroid/app/INotificationManager;

    const/4 v4, 0x0

    invoke-virtual {v8}, Landroid/app/Notification$Builder;->getNotification()Landroid/app/Notification;

    move-result-object v5

    invoke-interface/range {v1 .. v6}, Landroid/app/INotificationManager;->enqueueNotificationWithTag(Ljava/lang/String;Ljava/lang/String;ILandroid/app/Notification;[I)V

    .line 856
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    invoke-virtual {v1, v3}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_3f
    .catch Landroid/os/RemoteException; {:try_start_21 .. :try_end_3f} :catch_15e

    .line 860
    .end local v2           #packageName:Ljava/lang/String;
    .end local v6           #idReceived:[I
    :goto_3f
    return-void

    .line 762
    :pswitch_40
    const v1, 0x10404d7

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 763
    .local v14, title:Ljava/lang/CharSequence;
    const v1, 0x10404d8

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 765
    .local v7, body:Ljava/lang/CharSequence;
    const v1, 0x1080078

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    .line 766
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setTicker(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 767
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 768
    invoke-virtual {v8, v7}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 770
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-static {v1}, Lcom/android/server/net/NetworkPolicyManagerService;->buildSnoozeWarningIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;

    move-result-object v13

    .line 771
    .local v13, snoozeIntent:Landroid/content/Intent;
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    const/high16 v5, 0x800

    invoke-static {v1, v4, v13, v5}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setDeleteIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    .line 774
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-static {v1}, Lcom/android/server/net/NetworkPolicyManagerService;->buildViewDataUsageIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;

    move-result-object v15

    .line 775
    .local v15, viewIntent:Landroid/content/Intent;
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    const/high16 v5, 0x800

    invoke-static {v1, v4, v15, v5}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    goto :goto_21

    .line 781
    .end local v7           #body:Ljava/lang/CharSequence;
    .end local v13           #snoozeIntent:Landroid/content/Intent;
    .end local v14           #title:Ljava/lang/CharSequence;
    .end local v15           #viewIntent:Landroid/content/Intent;
    :pswitch_8a
    const v1, 0x10404dd

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v7

    .line 784
    .restart local v7       #body:Ljava/lang/CharSequence;
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v1

    packed-switch v1, :pswitch_data_16c

    .line 798
    const/4 v14, 0x0

    .line 802
    .restart local v14       #title:Ljava/lang/CharSequence;
    :goto_9d
    const/4 v1, 0x1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    .line 803
    const v1, 0x1080510

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    .line 804
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setTicker(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 805
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 806
    invoke-virtual {v8, v7}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 808
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-static {v1}, Lcom/android/server/net/NetworkPolicyManagerService;->buildNetworkOverLimitIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;

    move-result-object v9

    .line 809
    .local v9, intent:Landroid/content/Intent;
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    const/high16 v5, 0x800

    invoke-static {v1, v4, v9, v5}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    goto/16 :goto_21

    .line 786
    .end local v9           #intent:Landroid/content/Intent;
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_c8
    const v1, 0x10404d9

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 787
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_9d

    .line 789
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_d0
    const v1, 0x10404da

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 790
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_9d

    .line 792
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_d8
    const v1, 0x10404db

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 793
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_9d

    .line 795
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_e0
    const v1, 0x10404dc

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 796
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_9d

    .line 814
    .end local v7           #body:Ljava/lang/CharSequence;
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_e8
    move-object/from16 v0, p1

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    sub-long v10, p3, v4

    .line 815
    .local v10, overBytes:J
    const v1, 0x10404e2

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    const/4 v5, 0x0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-static {v0, v10, v11}, Landroid/text/format/Formatter;->formatFileSize(Landroid/content/Context;J)Ljava/lang/String;

    move-result-object v16

    aput-object v16, v4, v5

    invoke-virtual {v12, v1, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    .line 819
    .restart local v7       #body:Ljava/lang/CharSequence;
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v1

    packed-switch v1, :pswitch_data_178

    .line 833
    const/4 v14, 0x0

    .line 837
    .restart local v14       #title:Ljava/lang/CharSequence;
    :goto_113
    const/4 v1, 0x1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    .line 838
    const v1, 0x1080078

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    .line 839
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setTicker(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 840
    invoke-virtual {v8, v14}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 841
    invoke-virtual {v8, v7}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 843
    move-object/from16 v0, p1

    iget-object v1, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-static {v1}, Lcom/android/server/net/NetworkPolicyManagerService;->buildViewDataUsageIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;

    move-result-object v9

    .line 844
    .restart local v9       #intent:Landroid/content/Intent;
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const/4 v4, 0x0

    const/high16 v5, 0x800

    invoke-static {v1, v4, v9, v5}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v8, v1}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    goto/16 :goto_21

    .line 821
    .end local v9           #intent:Landroid/content/Intent;
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_13e
    const v1, 0x10404de

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 822
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_113

    .line 824
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_146
    const v1, 0x10404df

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 825
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_113

    .line 827
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_14e
    const v1, 0x10404e0

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 828
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_113

    .line 830
    .end local v14           #title:Ljava/lang/CharSequence;
    :pswitch_156
    const v1, 0x10404e1

    invoke-virtual {v12, v1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v14

    .line 831
    .restart local v14       #title:Ljava/lang/CharSequence;
    goto :goto_113

    .line 857
    .end local v7           #body:Ljava/lang/CharSequence;
    .end local v10           #overBytes:J
    .end local v14           #title:Ljava/lang/CharSequence;
    :catch_15e
    move-exception v1

    goto/16 :goto_3f

    .line 760
    nop

    :pswitch_data_162
    .packed-switch 0x1
        :pswitch_40
        :pswitch_8a
        :pswitch_e8
    .end packed-switch

    .line 784
    :pswitch_data_16c
    .packed-switch 0x1
        :pswitch_d8
        :pswitch_c8
        :pswitch_d0
        :pswitch_e0
    .end packed-switch

    .line 819
    :pswitch_data_178
    .packed-switch 0x1
        :pswitch_14e
        :pswitch_13e
        :pswitch_146
        :pswitch_156
    .end packed-switch
.end method

.method private enqueueRestrictedNotification(Ljava/lang/String;)V
    .registers 13
    .parameter "tag"

    .prologue
    const/4 v3, 0x0

    const/4 v2, 0x1

    .line 867
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v9

    .line 868
    .local v9, res:Landroid/content/res/Resources;
    new-instance v7, Landroid/app/Notification$Builder;

    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-direct {v7, v0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    .line 870
    .local v7, builder:Landroid/app/Notification$Builder;
    const v0, 0x10404e3

    invoke-virtual {v9, v0}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v10

    .line 871
    .local v10, title:Ljava/lang/CharSequence;
    const v0, 0x10404e4

    invoke-virtual {v9, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v6

    .line 873
    .local v6, body:Ljava/lang/CharSequence;
    invoke-virtual {v7, v2}, Landroid/app/Notification$Builder;->setOnlyAlertOnce(Z)Landroid/app/Notification$Builder;

    .line 874
    invoke-virtual {v7, v2}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    .line 875
    const v0, 0x1080078

    invoke-virtual {v7, v0}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    .line 876
    invoke-virtual {v7, v10}, Landroid/app/Notification$Builder;->setTicker(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 877
    invoke-virtual {v7, v10}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 878
    invoke-virtual {v7, v6}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 880
    invoke-static {}, Lcom/android/server/net/NetworkPolicyManagerService;->buildAllowBackgroundDataIntent()Landroid/content/Intent;

    move-result-object v8

    .line 881
    .local v8, intent:Landroid/content/Intent;
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const/high16 v2, 0x800

    invoke-static {v0, v3, v8, v2}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v0

    invoke-virtual {v7, v0}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    .line 886
    :try_start_41
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    .line 887
    .local v1, packageName:Ljava/lang/String;
    const/4 v0, 0x1

    new-array v5, v0, [I

    .line 888
    .local v5, idReceived:[I
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNotifManager:Landroid/app/INotificationManager;

    const/4 v3, 0x0

    invoke-virtual {v7}, Landroid/app/Notification$Builder;->getNotification()Landroid/app/Notification;

    move-result-object v4

    move-object v2, p1

    invoke-interface/range {v0 .. v5}, Landroid/app/INotificationManager;->enqueueNotificationWithTag(Ljava/lang/String;Ljava/lang/String;ILandroid/app/Notification;[I)V

    .line 890
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_5a
    .catch Landroid/os/RemoteException; {:try_start_41 .. :try_end_5a} :catch_5b

    .line 894
    .end local v1           #packageName:Ljava/lang/String;
    .end local v5           #idReceived:[I
    :goto_5a
    return-void

    .line 891
    :catch_5b
    move-exception v0

    goto :goto_5a
.end method

.method private enqueueValidNotification(Landroid/net/NetworkPolicy;IJ)V
    .registers 6
    .parameter "policy"
    .parameter "type"
    .parameter "totalBytes"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 743
    invoke-static {p2}, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->isIntervalValid(I)Z

    move-result v0

    if-eqz v0, :cond_c

    .line 744
    invoke-static {p2}, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->setInterval(I)V

    .line 745
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueNotification(Landroid/net/NetworkPolicy;IJ)V

    .line 747
    :cond_c
    return-void
.end method

.method private ensureActiveMobilePolicyLocked()V
    .registers 24

    .prologue
    .line 1123
    move-object/from16 v0, p0

    iget-boolean v2, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mSuppressDefaultPolicy:Z

    if-eqz v2, :cond_7

    .line 1161
    :cond_6
    :goto_6
    return-void

    .line 1125
    :cond_7
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/telephony/TelephonyManager;->from(Landroid/content/Context;)Landroid/telephony/TelephonyManager;

    move-result-object v21

    .line 1128
    .local v21, tele:Landroid/telephony/TelephonyManager;
    invoke-virtual/range {v21 .. v21}, Landroid/telephony/TelephonyManager;->getSimState()I

    move-result v2

    const/4 v3, 0x5

    if-ne v2, v3, :cond_6

    .line 1130
    invoke-virtual/range {v21 .. v21}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v4

    .line 1131
    .local v4, subscriberId:Ljava/lang/String;
    new-instance v1, Landroid/net/NetworkIdentity;

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    invoke-direct/range {v1 .. v6}, Landroid/net/NetworkIdentity;-><init>(IILjava/lang/String;Ljava/lang/String;Z)V

    .line 1135
    .local v1, probeIdent:Landroid/net/NetworkIdentity;
    const/16 v20, 0x0

    .line 1136
    .local v20, mobileDefined:Z
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v2}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v2

    invoke-interface {v2}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v19

    .local v19, i$:Ljava/util/Iterator;
    :cond_31
    :goto_31
    invoke-interface/range {v19 .. v19}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_48

    invoke-interface/range {v19 .. v19}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/net/NetworkPolicy;

    .line 1137
    .local v5, policy:Landroid/net/NetworkPolicy;
    iget-object v2, v5, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v2, v1}, Landroid/net/NetworkTemplate;->matches(Landroid/net/NetworkIdentity;)Z

    move-result v2

    if-eqz v2, :cond_31

    .line 1138
    const/16 v20, 0x1

    goto :goto_31

    .line 1142
    .end local v5           #policy:Landroid/net/NetworkPolicy;
    :cond_48
    if-nez v20, :cond_6

    .line 1143
    const-string v2, "NetworkPolicy"

    const-string v3, "no policy for active mobile network; generating default policy"

    invoke-static {v2, v3}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1146
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x10e0031

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v2

    int-to-long v2, v2

    const-wide/32 v11, 0x100000

    mul-long v9, v2, v11

    .line 1150
    .local v9, warningBytes:J
    new-instance v22, Landroid/text/format/Time;

    invoke-direct/range {v22 .. v22}, Landroid/text/format/Time;-><init>()V

    .line 1151
    .local v22, time:Landroid/text/format/Time;
    invoke-virtual/range {v22 .. v22}, Landroid/text/format/Time;->setToNow()V

    .line 1153
    move-object/from16 v0, v22

    iget v7, v0, Landroid/text/format/Time;->monthDay:I

    .line 1154
    .local v7, cycleDay:I
    move-object/from16 v0, v22

    iget-object v8, v0, Landroid/text/format/Time;->timezone:Ljava/lang/String;

    .line 1156
    .local v8, cycleTimezone:Ljava/lang/String;
    invoke-static {v4}, Landroid/net/NetworkTemplate;->buildTemplateMobileAll(Ljava/lang/String;)Landroid/net/NetworkTemplate;

    move-result-object v6

    .line 1157
    .local v6, template:Landroid/net/NetworkTemplate;
    new-instance v5, Landroid/net/NetworkPolicy;

    const-wide/16 v11, -0x1

    const-wide/16 v13, -0x1

    const-wide/16 v15, -0x1

    const/16 v17, 0x1

    const/16 v18, 0x1

    invoke-direct/range {v5 .. v18}, Landroid/net/NetworkPolicy;-><init>(Landroid/net/NetworkTemplate;ILjava/lang/String;JJJJZZ)V

    .line 1159
    .restart local v5       #policy:Landroid/net/NetworkPolicy;
    move-object/from16 v0, p0

    invoke-direct {v0, v5}, Lcom/android/server/net/NetworkPolicyManagerService;->addNetworkPolicyLocked(Landroid/net/NetworkPolicy;)V

    goto/16 :goto_6
.end method

.method private findPolicyForNetworkLocked(Landroid/net/NetworkIdentity;)Landroid/net/NetworkPolicy;
    .registers 5
    .parameter "ident"

    .prologue
    .line 1537
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v2}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v2

    invoke-interface {v2}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_a
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1f

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/NetworkPolicy;

    .line 1538
    .local v1, policy:Landroid/net/NetworkPolicy;
    iget-object v2, v1, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v2, p1}, Landroid/net/NetworkTemplate;->matches(Landroid/net/NetworkIdentity;)Z

    move-result v2

    if-eqz v2, :cond_a

    .line 1542
    .end local v1           #policy:Landroid/net/NetworkPolicy;
    :goto_1e
    return-object v1

    :cond_1f
    const/4 v1, 0x0

    goto :goto_1e
.end method

.method private getNetworkQuotaInfoUnchecked(Landroid/net/NetworkState;)Landroid/net/NetworkQuotaInfo;
    .registers 24
    .parameter "state"

    .prologue
    .line 1560
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    move-object/from16 v0, p1

    invoke-static {v3, v0}, Landroid/net/NetworkIdentity;->buildNetworkIdentity(Landroid/content/Context;Landroid/net/NetworkState;)Landroid/net/NetworkIdentity;

    move-result-object v18

    .line 1563
    .local v18, ident:Landroid/net/NetworkIdentity;
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v4

    .line 1564
    :try_start_f
    move-object/from16 v0, p0

    move-object/from16 v1, v18

    invoke-direct {v0, v1}, Lcom/android/server/net/NetworkPolicyManagerService;->findPolicyForNetworkLocked(Landroid/net/NetworkIdentity;)Landroid/net/NetworkPolicy;

    move-result-object v19

    .line 1565
    .local v19, policy:Landroid/net/NetworkPolicy;
    monitor-exit v4
    :try_end_18
    .catchall {:try_start_f .. :try_end_18} :catchall_22

    .line 1567
    if-eqz v19, :cond_20

    invoke-virtual/range {v19 .. v19}, Landroid/net/NetworkPolicy;->hasCycle()Z

    move-result v3

    if-nez v3, :cond_25

    .line 1569
    :cond_20
    const/4 v9, 0x0

    .line 1585
    :goto_21
    return-object v9

    .line 1565
    .end local v19           #policy:Landroid/net/NetworkPolicy;
    :catchall_22
    move-exception v3

    :try_start_23
    monitor-exit v4
    :try_end_24
    .catchall {:try_start_23 .. :try_end_24} :catchall_22

    throw v3

    .line 1572
    .restart local v19       #policy:Landroid/net/NetworkPolicy;
    :cond_25
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->currentTimeMillis()J

    move-result-wide v16

    .line 1575
    .local v16, currentTime:J
    move-wide/from16 v0, v16

    move-object/from16 v2, v19

    invoke-static {v0, v1, v2}, Landroid/net/NetworkPolicyManager;->computeLastCycleBoundary(JLandroid/net/NetworkPolicy;)J

    move-result-wide v5

    .line 1576
    .local v5, start:J
    move-wide/from16 v7, v16

    .line 1577
    .local v7, end:J
    move-object/from16 v0, v19

    iget-object v4, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object/from16 v3, p0

    invoke-direct/range {v3 .. v8}, Lcom/android/server/net/NetworkPolicyManagerService;->getTotalBytes(Landroid/net/NetworkTemplate;JJ)J

    move-result-wide v10

    .line 1580
    .local v10, totalBytes:J
    move-object/from16 v0, v19

    iget-wide v3, v0, Landroid/net/NetworkPolicy;->warningBytes:J

    const-wide/16 v20, -0x1

    cmp-long v3, v3, v20

    if-eqz v3, :cond_5f

    move-object/from16 v0, v19

    iget-wide v12, v0, Landroid/net/NetworkPolicy;->warningBytes:J

    .line 1582
    .local v12, softLimitBytes:J
    :goto_4b
    move-object/from16 v0, v19

    iget-wide v3, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    const-wide/16 v20, -0x1

    cmp-long v3, v3, v20

    if-eqz v3, :cond_62

    move-object/from16 v0, v19

    iget-wide v14, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    .line 1585
    .local v14, hardLimitBytes:J
    :goto_59
    new-instance v9, Landroid/net/NetworkQuotaInfo;

    invoke-direct/range {v9 .. v15}, Landroid/net/NetworkQuotaInfo;-><init>(JJJ)V

    goto :goto_21

    .line 1580
    .end local v12           #softLimitBytes:J
    .end local v14           #hardLimitBytes:J
    :cond_5f
    const-wide/16 v12, -0x1

    goto :goto_4b

    .line 1582
    .restart local v12       #softLimitBytes:J
    :cond_62
    const-wide/16 v14, -0x1

    goto :goto_59
.end method

.method private static getSystemDir()Ljava/io/File;
    .registers 3

    .prologue
    .line 344
    new-instance v0, Ljava/io/File;

    invoke-static {}, Landroid/os/Environment;->getDataDirectory()Ljava/io/File;

    move-result-object v1

    const-string v2, "system"

    invoke-direct {v0, v1, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    return-object v0
.end method

.method private getTotalBytes(Landroid/net/NetworkTemplate;JJ)J
    .registers 15
    .parameter "template"
    .parameter "start"
    .parameter "end"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    const-wide/16 v7, 0x0

    .line 2000
    :try_start_2
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkStats:Landroid/net/INetworkStatsService;

    move-object v1, p1

    move-wide v2, p2

    move-wide v4, p4

    invoke-interface/range {v0 .. v5}, Landroid/net/INetworkStatsService;->getNetworkTotalBytes(Landroid/net/NetworkTemplate;JJ)J

    move-result-wide v0

    invoke-static/range {p0 .. p5}, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->adjustMobileDataUsage(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkTemplate;JJ)J
    :try_end_e
    .catch Ljava/lang/RuntimeException; {:try_start_2 .. :try_end_e} :catch_11
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_e} :catch_2c

    move-result-wide v2

    add-long/2addr v0, v2

    .line 2006
    :goto_10
    return-wide v0

    .line 2001
    :catch_11
    move-exception v6

    .line 2002
    .local v6, e:Ljava/lang/RuntimeException;
    const-string v0, "NetworkPolicy"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "problem reading network stats: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    move-wide v0, v7

    .line 2003
    goto :goto_10

    .line 2004
    .end local v6           #e:Ljava/lang/RuntimeException;
    :catch_2c
    move-exception v6

    .local v6, e:Landroid/os/RemoteException;
    move-wide v0, v7

    .line 2006
    goto :goto_10
.end method

.method private isBandwidthControlEnabled()Z
    .registers 5

    .prologue
    .line 2011
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 2013
    .local v1, token:J
    :try_start_4
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    invoke-interface {v3}, Landroid/os/INetworkManagementService;->isBandwidthControlEnabled()Z
    :try_end_9
    .catchall {:try_start_4 .. :try_end_9} :catchall_11
    .catch Landroid/os/RemoteException; {:try_start_4 .. :try_end_9} :catch_e

    move-result v3

    .line 2018
    :goto_a
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 2016
    return v3

    .line 2014
    :catch_e
    move-exception v0

    .line 2016
    .local v0, e:Landroid/os/RemoteException;
    const/4 v3, 0x0

    goto :goto_a

    .line 2018
    .end local v0           #e:Landroid/os/RemoteException;
    :catchall_11
    move-exception v3

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v3
.end method

.method private isTemplateRelevant(Landroid/net/NetworkTemplate;)Z
    .registers 5
    .parameter "template"

    .prologue
    .line 701
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-static {v1}, Landroid/telephony/TelephonyManager;->from(Landroid/content/Context;)Landroid/telephony/TelephonyManager;

    move-result-object v0

    .line 703
    .local v0, tele:Landroid/telephony/TelephonyManager;
    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v1

    packed-switch v1, :pswitch_data_26

    .line 715
    const/4 v1, 0x1

    :goto_e
    return v1

    .line 709
    :pswitch_f
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSimState()I

    move-result v1

    const/4 v2, 0x5

    if-ne v1, v2, :cond_23

    .line 710
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getSubscriberId()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/internal/util/Objects;->equal(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v1

    goto :goto_e

    .line 712
    :cond_23
    const/4 v1, 0x0

    goto :goto_e

    .line 703
    nop

    :pswitch_data_26
    .packed-switch 0x1
        :pswitch_f
        :pswitch_f
        :pswitch_f
    .end packed-switch
.end method

.method private static isUidValidForRules(I)Z
    .registers 2
    .parameter "uid"

    .prologue
    .line 1781
    const/16 v0, 0x3f5

    if-eq p0, v0, :cond_e

    const/16 v0, 0x3fb

    if-eq p0, v0, :cond_e

    invoke-static {p0}, Landroid/os/UserId;->isApp(I)Z

    move-result v0

    if-eqz v0, :cond_10

    .line 1783
    :cond_e
    const/4 v0, 0x1

    .line 1786
    :goto_f
    return v0

    :cond_10
    const/4 v0, 0x0

    goto :goto_f
.end method

.method private maybeRefreshTrustedTime()V
    .registers 5

    .prologue
    .line 2026
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mTime:Landroid/util/TrustedTime;

    invoke-interface {v0}, Landroid/util/TrustedTime;->getCacheAge()J

    move-result-wide v0

    const-wide/32 v2, 0x5265c00

    cmp-long v0, v0, v2

    if-lez v0, :cond_12

    .line 2027
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mTime:Landroid/util/TrustedTime;

    invoke-interface {v0}, Landroid/util/TrustedTime;->forceRefresh()Z

    .line 2029
    :cond_12
    return-void
.end method

.method private notifyOverLimitLocked(Landroid/net/NetworkTemplate;)V
    .registers 4
    .parameter "template"

    .prologue
    .line 723
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mOverLimitNotified:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_16

    .line 724
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-static {p1}, Lcom/android/server/net/NetworkPolicyManagerService;->buildNetworkOverLimitIntent(Landroid/net/NetworkTemplate;)Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    .line 725
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mOverLimitNotified:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 727
    :cond_16
    return-void
.end method

.method private notifyUnderLimitLocked(Landroid/net/NetworkTemplate;)V
    .registers 3
    .parameter "template"

    .prologue
    .line 730
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mOverLimitNotified:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    .line 731
    return-void
.end method

.method private performSnooze(Landroid/net/NetworkTemplate;I)V
    .registers 10
    .parameter "template"
    .parameter "type"

    .prologue
    .line 1484
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->maybeRefreshTrustedTime()V

    .line 1485
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->currentTimeMillis()J

    move-result-wide v0

    .line 1486
    .local v0, currentTime:J
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v4

    .line 1488
    :try_start_a
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v3, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/net/NetworkPolicy;

    .line 1489
    .local v2, policy:Landroid/net/NetworkPolicy;
    if-nez v2, :cond_30

    .line 1490
    new-instance v3, Ljava/lang/IllegalArgumentException;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "unable to find policy for "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-direct {v3, v5}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 1508
    .end local v2           #policy:Landroid/net/NetworkPolicy;
    :catchall_2d
    move-exception v3

    monitor-exit v4
    :try_end_2f
    .catchall {:try_start_a .. :try_end_2f} :catchall_2d

    throw v3

    .line 1493
    .restart local v2       #policy:Landroid/net/NetworkPolicy;
    :cond_30
    packed-switch p2, :pswitch_data_4e

    .line 1501
    :try_start_33
    new-instance v3, Ljava/lang/IllegalArgumentException;

    const-string v5, "unexpected type"

    invoke-direct {v3, v5}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 1495
    :pswitch_3b
    iput-wide v0, v2, Landroid/net/NetworkPolicy;->lastWarningSnooze:J

    .line 1504
    :goto_3d
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V

    .line 1505
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V

    .line 1506
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 1507
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1508
    monitor-exit v4

    .line 1509
    return-void

    .line 1498
    :pswitch_4b
    iput-wide v0, v2, Landroid/net/NetworkPolicy;->lastLimitSnooze:J
    :try_end_4d
    .catchall {:try_start_33 .. :try_end_4d} :catchall_2d

    goto :goto_3d

    .line 1493
    :pswitch_data_4e
    .packed-switch 0x1
        :pswitch_3b
        :pswitch_4b
    .end packed-switch
.end method

.method private readPolicyLocked()V
    .registers 32

    .prologue
    .line 1167
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v3}, Ljava/util/HashMap;->clear()V

    .line 1168
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v3}, Landroid/util/SparseIntArray;->clear()V

    .line 1170
    const/16 v19, 0x0

    .line 1172
    .local v19, fis:Ljava/io/FileInputStream;
    :try_start_10
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v3}, Lcom/android/internal/os/AtomicFile;->openRead()Ljava/io/FileInputStream;

    move-result-object v19

    .line 1173
    invoke-static {}, Landroid/util/Xml;->newPullParser()Lorg/xmlpull/v1/XmlPullParser;

    move-result-object v20

    .line 1174
    .local v20, in:Lorg/xmlpull/v1/XmlPullParser;
    const/4 v3, 0x0

    move-object/from16 v0, v20

    move-object/from16 v1, v19

    invoke-interface {v0, v1, v3}, Lorg/xmlpull/v1/XmlPullParser;->setInput(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 1177
    const/16 v28, 0x1

    .line 1178
    .local v28, version:I
    :cond_26
    :goto_26
    invoke-interface/range {v20 .. v20}, Lorg/xmlpull/v1/XmlPullParser;->next()I

    move-result v26

    .local v26, type:I
    const/4 v3, 0x1

    move/from16 v0, v26

    if-eq v0, v3, :cond_60

    .line 1179
    invoke-interface/range {v20 .. v20}, Lorg/xmlpull/v1/XmlPullParser;->getName()Ljava/lang/String;

    move-result-object v25

    .line 1180
    .local v25, tag:Ljava/lang/String;
    const/4 v3, 0x2

    move/from16 v0, v26

    if-ne v0, v3, :cond_26

    .line 1181
    const-string v3, "policy-list"

    move-object/from16 v0, v25

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_7c

    .line 1182
    const-string v3, "version"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v28

    .line 1183
    const/4 v3, 0x3

    move/from16 v0, v28

    if-lt v0, v3, :cond_64

    .line 1184
    const-string v3, "restrictBackground"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readBooleanAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Z

    move-result v3

    move-object/from16 v0, p0

    iput-boolean v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z
    :try_end_5b
    .catchall {:try_start_10 .. :try_end_5b} :catchall_77
    .catch Ljava/io/FileNotFoundException; {:try_start_10 .. :try_end_5b} :catch_5c
    .catch Ljava/io/IOException; {:try_start_10 .. :try_end_5b} :catch_6a
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_10 .. :try_end_5b} :catch_123

    goto :goto_26

    .line 1272
    .end local v20           #in:Lorg/xmlpull/v1/XmlPullParser;
    .end local v25           #tag:Ljava/lang/String;
    .end local v26           #type:I
    .end local v28           #version:I
    :catch_5c
    move-exception v18

    .line 1274
    .local v18, e:Ljava/io/FileNotFoundException;
    :try_start_5d
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->upgradeLegacyBackgroundData()V
    :try_end_60
    .catchall {:try_start_5d .. :try_end_60} :catchall_77

    .line 1280
    .end local v18           #e:Ljava/io/FileNotFoundException;
    :cond_60
    :goto_60
    invoke-static/range {v19 .. v19}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    .line 1282
    return-void

    .line 1187
    .restart local v20       #in:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v25       #tag:Ljava/lang/String;
    .restart local v26       #type:I
    .restart local v28       #version:I
    :cond_64
    const/4 v3, 0x0

    :try_start_65
    move-object/from16 v0, p0

    iput-boolean v3, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z
    :try_end_69
    .catchall {:try_start_65 .. :try_end_69} :catchall_77
    .catch Ljava/io/FileNotFoundException; {:try_start_65 .. :try_end_69} :catch_5c
    .catch Ljava/io/IOException; {:try_start_65 .. :try_end_69} :catch_6a
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_65 .. :try_end_69} :catch_123

    goto :goto_26

    .line 1275
    .end local v20           #in:Lorg/xmlpull/v1/XmlPullParser;
    .end local v25           #tag:Ljava/lang/String;
    .end local v26           #type:I
    .end local v28           #version:I
    :catch_6a
    move-exception v18

    .line 1276
    .local v18, e:Ljava/io/IOException;
    :try_start_6b
    const-string v3, "NetworkPolicy"

    const-string v29, "problem reading network policy"

    move-object/from16 v0, v29

    move-object/from16 v1, v18

    invoke-static {v3, v0, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_76
    .catchall {:try_start_6b .. :try_end_76} :catchall_77

    goto :goto_60

    .line 1280
    .end local v18           #e:Ljava/io/IOException;
    :catchall_77
    move-exception v3

    invoke-static/range {v19 .. v19}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    throw v3

    .line 1190
    .restart local v20       #in:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v25       #tag:Ljava/lang/String;
    .restart local v26       #type:I
    .restart local v28       #version:I
    :cond_7c
    :try_start_7c
    const-string v3, "network-policy"

    move-object/from16 v0, v25

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_156

    .line 1191
    const-string v3, "networkTemplate"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v22

    .line 1192
    .local v22, networkTemplate:I
    const/4 v3, 0x0

    const-string v29, "subscriberId"

    move-object/from16 v0, v20

    move-object/from16 v1, v29

    invoke-interface {v0, v3, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v24

    .line 1194
    .local v24, subscriberId:Ljava/lang/String;
    const/16 v3, 0x9

    move/from16 v0, v28

    if-lt v0, v3, :cond_131

    .line 1195
    const/4 v3, 0x0

    const-string v29, "networkId"

    move-object/from16 v0, v20

    move-object/from16 v1, v29

    invoke-interface {v0, v3, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v21

    .line 1199
    .local v21, networkId:Ljava/lang/String;
    :goto_aa
    const-string v3, "cycleDay"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v5

    .line 1201
    .local v5, cycleDay:I
    const/4 v3, 0x6

    move/from16 v0, v28

    if-lt v0, v3, :cond_135

    .line 1202
    const/4 v3, 0x0

    const-string v29, "cycleTimezone"

    move-object/from16 v0, v20

    move-object/from16 v1, v29

    invoke-interface {v0, v3, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 1206
    .local v6, cycleTimezone:Ljava/lang/String;
    :goto_c2
    const-string v3, "warningBytes"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readLongAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)J

    move-result-wide v7

    .line 1207
    .local v7, warningBytes:J
    const-string v3, "limitBytes"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readLongAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)J

    move-result-wide v9

    .line 1209
    .local v9, limitBytes:J
    const/4 v3, 0x5

    move/from16 v0, v28

    if-lt v0, v3, :cond_138

    .line 1210
    const-string v3, "lastLimitSnooze"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readLongAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)J

    move-result-wide v13

    .line 1217
    .local v13, lastLimitSnooze:J
    :goto_df
    const/4 v3, 0x4

    move/from16 v0, v28

    if-lt v0, v3, :cond_149

    .line 1218
    const-string v3, "metered"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readBooleanAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Z

    move-result v15

    .line 1231
    .local v15, metered:Z
    :goto_ec
    const/4 v3, 0x5

    move/from16 v0, v28

    if-lt v0, v3, :cond_150

    .line 1232
    const-string v3, "lastWarningSnooze"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readLongAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)J

    move-result-wide v11

    .line 1237
    .local v11, lastWarningSnooze:J
    :goto_f9
    const/4 v3, 0x7

    move/from16 v0, v28

    if-lt v0, v3, :cond_153

    .line 1238
    const-string v3, "inferred"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readBooleanAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Z

    move-result v16

    .line 1243
    .local v16, inferred:Z
    :goto_106
    new-instance v4, Landroid/net/NetworkTemplate;

    move/from16 v0, v22

    move-object/from16 v1, v24

    move-object/from16 v2, v21

    invoke-direct {v4, v0, v1, v2}, Landroid/net/NetworkTemplate;-><init>(ILjava/lang/String;Ljava/lang/String;)V

    .line 1245
    .local v4, template:Landroid/net/NetworkTemplate;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    move-object/from16 v29, v0

    new-instance v3, Landroid/net/NetworkPolicy;

    invoke-direct/range {v3 .. v16}, Landroid/net/NetworkPolicy;-><init>(Landroid/net/NetworkTemplate;ILjava/lang/String;JJJJZZ)V

    move-object/from16 v0, v29

    invoke-virtual {v0, v4, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_121
    .catchall {:try_start_7c .. :try_end_121} :catchall_77
    .catch Ljava/io/FileNotFoundException; {:try_start_7c .. :try_end_121} :catch_5c
    .catch Ljava/io/IOException; {:try_start_7c .. :try_end_121} :catch_6a
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_7c .. :try_end_121} :catch_123

    goto/16 :goto_26

    .line 1277
    .end local v4           #template:Landroid/net/NetworkTemplate;
    .end local v5           #cycleDay:I
    .end local v6           #cycleTimezone:Ljava/lang/String;
    .end local v7           #warningBytes:J
    .end local v9           #limitBytes:J
    .end local v11           #lastWarningSnooze:J
    .end local v13           #lastLimitSnooze:J
    .end local v15           #metered:Z
    .end local v16           #inferred:Z
    .end local v20           #in:Lorg/xmlpull/v1/XmlPullParser;
    .end local v21           #networkId:Ljava/lang/String;
    .end local v22           #networkTemplate:I
    .end local v24           #subscriberId:Ljava/lang/String;
    .end local v25           #tag:Ljava/lang/String;
    .end local v26           #type:I
    .end local v28           #version:I
    :catch_123
    move-exception v18

    .line 1278
    .local v18, e:Lorg/xmlpull/v1/XmlPullParserException;
    :try_start_124
    const-string v3, "NetworkPolicy"

    const-string v29, "problem reading network policy"

    move-object/from16 v0, v29

    move-object/from16 v1, v18

    invoke-static {v3, v0, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_12f
    .catchall {:try_start_124 .. :try_end_12f} :catchall_77

    goto/16 :goto_60

    .line 1197
    .end local v18           #e:Lorg/xmlpull/v1/XmlPullParserException;
    .restart local v20       #in:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v22       #networkTemplate:I
    .restart local v24       #subscriberId:Ljava/lang/String;
    .restart local v25       #tag:Ljava/lang/String;
    .restart local v26       #type:I
    .restart local v28       #version:I
    :cond_131
    const/16 v21, 0x0

    .restart local v21       #networkId:Ljava/lang/String;
    goto/16 :goto_aa

    .line 1204
    .restart local v5       #cycleDay:I
    :cond_135
    :try_start_135
    const-string v6, "UTC"

    .restart local v6       #cycleTimezone:Ljava/lang/String;
    goto :goto_c2

    .line 1211
    .restart local v7       #warningBytes:J
    .restart local v9       #limitBytes:J
    :cond_138
    const/4 v3, 0x2

    move/from16 v0, v28

    if-lt v0, v3, :cond_146

    .line 1212
    const-string v3, "lastSnooze"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readLongAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)J

    move-result-wide v13

    .restart local v13       #lastLimitSnooze:J
    goto :goto_df

    .line 1214
    .end local v13           #lastLimitSnooze:J
    :cond_146
    const-wide/16 v13, -0x1

    .restart local v13       #lastLimitSnooze:J
    goto :goto_df

    .line 1220
    :cond_149
    packed-switch v22, :pswitch_data_20a

    .line 1227
    const/4 v15, 0x0

    .restart local v15       #metered:Z
    goto :goto_ec

    .line 1224
    .end local v15           #metered:Z
    :pswitch_14e
    const/4 v15, 0x1

    .line 1225
    .restart local v15       #metered:Z
    goto :goto_ec

    .line 1234
    :cond_150
    const-wide/16 v11, -0x1

    .restart local v11       #lastWarningSnooze:J
    goto :goto_f9

    .line 1240
    :cond_153
    const/16 v16, 0x0

    .restart local v16       #inferred:Z
    goto :goto_106

    .line 1249
    .end local v5           #cycleDay:I
    .end local v6           #cycleTimezone:Ljava/lang/String;
    .end local v7           #warningBytes:J
    .end local v9           #limitBytes:J
    .end local v11           #lastWarningSnooze:J
    .end local v13           #lastLimitSnooze:J
    .end local v15           #metered:Z
    .end local v16           #inferred:Z
    .end local v21           #networkId:Ljava/lang/String;
    .end local v22           #networkTemplate:I
    .end local v24           #subscriberId:Ljava/lang/String;
    :cond_156
    const-string v3, "uid-policy"

    move-object/from16 v0, v25

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_1b2

    const/16 v3, 0x8

    move/from16 v0, v28

    if-ge v0, v3, :cond_1b2

    .line 1250
    const-string v3, "uid"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v27

    .line 1251
    .local v27, uid:I
    const-string v3, "policy"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v23

    .line 1253
    .local v23, policy:I
    invoke-static/range {v27 .. v27}, Landroid/os/UserId;->getAppId(I)I

    move-result v17

    .line 1254
    .local v17, appId:I
    invoke-static/range {v17 .. v17}, Landroid/os/UserId;->isApp(I)Z

    move-result v3

    if-eqz v3, :cond_18c

    .line 1255
    const/4 v3, 0x0

    move-object/from16 v0, p0

    move/from16 v1, v17

    move/from16 v2, v23

    invoke-direct {v0, v1, v2, v3}, Lcom/android/server/net/NetworkPolicyManagerService;->setAppPolicyUnchecked(IIZ)V

    goto/16 :goto_26

    .line 1257
    :cond_18c
    const-string v3, "NetworkPolicy"

    new-instance v29, Ljava/lang/StringBuilder;

    invoke-direct/range {v29 .. v29}, Ljava/lang/StringBuilder;-><init>()V

    const-string v30, "unable to apply policy to UID "

    invoke-virtual/range {v29 .. v30}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v29

    move-object/from16 v0, v29

    move/from16 v1, v27

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v29

    const-string v30, "; ignoring"

    invoke-virtual/range {v29 .. v30}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v29

    invoke-virtual/range {v29 .. v29}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v29

    move-object/from16 v0, v29

    invoke-static {v3, v0}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_26

    .line 1259
    .end local v17           #appId:I
    .end local v23           #policy:I
    .end local v27           #uid:I
    :cond_1b2
    const-string v3, "app-policy"

    move-object/from16 v0, v25

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_26

    const/16 v3, 0x8

    move/from16 v0, v28

    if-lt v0, v3, :cond_26

    .line 1260
    const-string v3, "appId"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v17

    .line 1261
    .restart local v17       #appId:I
    const-string v3, "policy"

    move-object/from16 v0, v20

    invoke-static {v0, v3}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->readIntAttribute(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)I

    move-result v23

    .line 1263
    .restart local v23       #policy:I
    invoke-static/range {v17 .. v17}, Landroid/os/UserId;->isApp(I)Z

    move-result v3

    if-eqz v3, :cond_1e4

    .line 1264
    const/4 v3, 0x0

    move-object/from16 v0, p0

    move/from16 v1, v17

    move/from16 v2, v23

    invoke-direct {v0, v1, v2, v3}, Lcom/android/server/net/NetworkPolicyManagerService;->setAppPolicyUnchecked(IIZ)V

    goto/16 :goto_26

    .line 1266
    :cond_1e4
    const-string v3, "NetworkPolicy"

    new-instance v29, Ljava/lang/StringBuilder;

    invoke-direct/range {v29 .. v29}, Ljava/lang/StringBuilder;-><init>()V

    const-string v30, "unable to apply policy to appId "

    invoke-virtual/range {v29 .. v30}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v29

    move-object/from16 v0, v29

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v29

    const-string v30, "; ignoring"

    invoke-virtual/range {v29 .. v30}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v29

    invoke-virtual/range {v29 .. v29}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v29

    move-object/from16 v0, v29

    invoke-static {v3, v0}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_208
    .catchall {:try_start_135 .. :try_end_208} :catchall_77
    .catch Ljava/io/FileNotFoundException; {:try_start_135 .. :try_end_208} :catch_5c
    .catch Ljava/io/IOException; {:try_start_135 .. :try_end_208} :catch_6a
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_135 .. :try_end_208} :catch_123

    goto/16 :goto_26

    .line 1220
    :pswitch_data_20a
    .packed-switch 0x1
        :pswitch_14e
        :pswitch_14e
        :pswitch_14e
    .end packed-switch
.end method

.method private removeInterfaceQuota(Ljava/lang/String;)V
    .registers 5
    .parameter "iface"

    .prologue
    .line 1968
    :try_start_0
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    invoke-interface {v1, p1}, Landroid/os/INetworkManagementService;->removeInterfaceQuota(Ljava/lang/String;)V
    :try_end_5
    .catch Ljava/lang/IllegalStateException; {:try_start_0 .. :try_end_5} :catch_6
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_5} :catch_f

    .line 1974
    :goto_5
    return-void

    .line 1969
    :catch_6
    move-exception v0

    .line 1970
    .local v0, e:Ljava/lang/IllegalStateException;
    const-string v1, "NetworkPolicy"

    const-string v2, "problem removing interface quota"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_5

    .line 1971
    .end local v0           #e:Ljava/lang/IllegalStateException;
    :catch_f
    move-exception v1

    goto :goto_5
.end method

.method private setAppPolicyUnchecked(IIZ)V
    .registers 7
    .parameter "appId"
    .parameter "policy"
    .parameter "persist"

    .prologue
    .line 1378
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v2

    .line 1379
    :try_start_3
    invoke-virtual {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->getAppPolicy(I)I

    move-result v0

    .line 1380
    .local v0, oldPolicy:I
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v1, p1, p2}, Landroid/util/SparseIntArray;->put(II)V

    .line 1383
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForAppLocked(I)V

    .line 1384
    if-eqz p3, :cond_14

    .line 1385
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1387
    :cond_14
    monitor-exit v2

    .line 1388
    return-void

    .line 1387
    .end local v0           #oldPolicy:I
    :catchall_16
    move-exception v1

    monitor-exit v2
    :try_end_18
    .catchall {:try_start_3 .. :try_end_18} :catchall_16

    throw v1
.end method

.method private setInterfaceQuota(Ljava/lang/String;J)V
    .registers 7
    .parameter "iface"
    .parameter "quotaBytes"

    .prologue
    .line 1958
    :try_start_0
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    invoke-interface {v1, p1, p2, p3}, Landroid/os/INetworkManagementService;->setInterfaceQuota(Ljava/lang/String;J)V
    :try_end_5
    .catch Ljava/lang/IllegalStateException; {:try_start_0 .. :try_end_5} :catch_6
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_5} :catch_f

    .line 1964
    :goto_5
    return-void

    .line 1959
    :catch_6
    move-exception v0

    .line 1960
    .local v0, e:Ljava/lang/IllegalStateException;
    const-string v1, "NetworkPolicy"

    const-string v2, "problem setting interface quota"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_5

    .line 1961
    .end local v0           #e:Ljava/lang/IllegalStateException;
    :catch_f
    move-exception v1

    goto :goto_5
.end method

.method private setNetworkTemplateEnabled(Landroid/net/NetworkTemplate;Z)V
    .registers 6
    .parameter "template"
    .parameter "enabled"

    .prologue
    .line 963
    invoke-static {p0, p1, p2}, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->setNetworkTemplateEnabled(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkTemplate;Z)V

    .line 965
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-static {v1}, Landroid/telephony/TelephonyManager;->from(Landroid/content/Context;)Landroid/telephony/TelephonyManager;

    move-result-object v0

    .line 967
    .local v0, tele:Landroid/telephony/TelephonyManager;
    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v1

    packed-switch v1, :pswitch_data_42

    .line 986
    new-instance v1, Ljava/lang/IllegalArgumentException;

    const-string v2, "unexpected template"

    invoke-direct {v1, v2}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 973
    :pswitch_18
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSimState()I

    move-result v1

    const/4 v2, 0x5

    if-ne v1, v2, :cond_35

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getSubscriberId()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/internal/util/Objects;->equal(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_35

    .line 975
    const/4 v1, 0x0

    invoke-direct {p0, v1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->setPolicyDataEnable(IZ)V

    .line 976
    const/4 v1, 0x6

    invoke-direct {p0, v1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->setPolicyDataEnable(IZ)V

    .line 988
    :cond_35
    :goto_35
    return-void

    .line 980
    :pswitch_36
    const/4 v1, 0x1

    invoke-direct {p0, v1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->setPolicyDataEnable(IZ)V

    goto :goto_35

    .line 983
    :pswitch_3b
    const/16 v1, 0x9

    invoke-direct {p0, v1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->setPolicyDataEnable(IZ)V

    goto :goto_35

    .line 967
    nop

    :pswitch_data_42
    .packed-switch 0x1
        :pswitch_18
        :pswitch_18
        :pswitch_18
        :pswitch_36
        :pswitch_3b
    .end packed-switch
.end method

.method private setPolicyDataEnable(IZ)V
    .registers 4
    .parameter "networkType"
    .parameter "enabled"

    .prologue
    .line 1991
    :try_start_0
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mConnManager:Landroid/net/IConnectivityManager;

    invoke-interface {v0, p1, p2}, Landroid/net/IConnectivityManager;->setPolicyDataEnable(IZ)V
    :try_end_5
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_5} :catch_6

    .line 1995
    :goto_5
    return-void

    .line 1992
    :catch_6
    move-exception v0

    goto :goto_5
.end method

.method private setUidNetworkRules(IZ)V
    .registers 6
    .parameter "uid"
    .parameter "rejectOnQuotaInterfaces"

    .prologue
    .line 1978
    :try_start_0
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    invoke-interface {v1, p1, p2}, Landroid/os/INetworkManagementService;->setUidNetworkRules(IZ)V
    :try_end_5
    .catch Ljava/lang/IllegalStateException; {:try_start_0 .. :try_end_5} :catch_6
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_5} :catch_f

    .line 1984
    :goto_5
    return-void

    .line 1979
    :catch_6
    move-exception v0

    .line 1980
    .local v0, e:Ljava/lang/IllegalStateException;
    const-string v1, "NetworkPolicy"

    const-string v2, "problem setting uid rules"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_5

    .line 1981
    .end local v0           #e:Ljava/lang/IllegalStateException;
    :catch_f
    move-exception v1

    goto :goto_5
.end method

.method private updateNetworkEnabledLocked()V
    .registers 18

    .prologue
    .line 937
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->currentTimeMillis()J

    move-result-wide v7

    .line 938
    .local v7, currentTime:J
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v1}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v9

    .local v9, i$:Ljava/util/Iterator;
    :goto_10
    invoke-interface {v9}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_5c

    invoke-interface {v9}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Landroid/net/NetworkPolicy;

    .line 940
    .local v12, policy:Landroid/net/NetworkPolicy;
    iget-wide v1, v12, Landroid/net/NetworkPolicy;->limitBytes:J

    const-wide/16 v15, -0x1

    cmp-long v1, v1, v15

    if-eqz v1, :cond_2a

    invoke-virtual {v12}, Landroid/net/NetworkPolicy;->hasCycle()Z

    move-result v1

    if-nez v1, :cond_33

    .line 941
    :cond_2a
    iget-object v1, v12, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    const/4 v2, 0x1

    move-object/from16 v0, p0

    invoke-direct {v0, v1, v2}, Lcom/android/server/net/NetworkPolicyManagerService;->setNetworkTemplateEnabled(Landroid/net/NetworkTemplate;Z)V

    goto :goto_10

    .line 945
    :cond_33
    invoke-static {v7, v8, v12}, Landroid/net/NetworkPolicyManager;->computeLastCycleBoundary(JLandroid/net/NetworkPolicy;)J

    move-result-wide v3

    .line 946
    .local v3, start:J
    move-wide v5, v7

    .line 947
    .local v5, end:J
    iget-object v2, v12, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object/from16 v1, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/server/net/NetworkPolicyManagerService;->getTotalBytes(Landroid/net/NetworkTemplate;JJ)J

    move-result-wide v13

    .line 950
    .local v13, totalBytes:J
    invoke-virtual {v12, v13, v14}, Landroid/net/NetworkPolicy;->isOverLimit(J)Z

    move-result v1

    if-eqz v1, :cond_58

    iget-wide v1, v12, Landroid/net/NetworkPolicy;->lastLimitSnooze:J

    cmp-long v1, v1, v3

    if-gez v1, :cond_58

    const/4 v11, 0x1

    .line 952
    .local v11, overLimitWithoutSnooze:Z
    :goto_4d
    if-nez v11, :cond_5a

    const/4 v10, 0x1

    .line 954
    .local v10, networkEnabled:Z
    :goto_50
    iget-object v1, v12, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object/from16 v0, p0

    invoke-direct {v0, v1, v10}, Lcom/android/server/net/NetworkPolicyManagerService;->setNetworkTemplateEnabled(Landroid/net/NetworkTemplate;Z)V

    goto :goto_10

    .line 950
    .end local v10           #networkEnabled:Z
    .end local v11           #overLimitWithoutSnooze:Z
    :cond_58
    const/4 v11, 0x0

    goto :goto_4d

    .line 952
    .restart local v11       #overLimitWithoutSnooze:Z
    :cond_5a
    const/4 v10, 0x0

    goto :goto_50

    .line 956
    .end local v3           #start:J
    .end local v5           #end:J
    .end local v11           #overLimitWithoutSnooze:Z
    .end local v12           #policy:Landroid/net/NetworkPolicy;
    .end local v13           #totalBytes:J
    :cond_5c
    return-void
.end method

.method private updateNetworkRulesLocked()V
    .registers 37

    .prologue
    .line 1000
    :try_start_0
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mConnManager:Landroid/net/IConnectivityManager;

    invoke-interface {v4}, Landroid/net/IConnectivityManager;->getAllNetworkState()[Landroid/net/NetworkState;
    :try_end_7
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_7} :catch_40

    move-result-object v31

    .line 1008
    .local v31, states:[Landroid/net/NetworkState;
    invoke-static {}, Lcom/google/android/collect/Maps;->newHashMap()Ljava/util/HashMap;

    move-result-object v25

    .line 1009
    .local v25, networks:Ljava/util/HashMap;,"Ljava/util/HashMap<Landroid/net/NetworkIdentity;Ljava/lang/String;>;"
    move-object/from16 v10, v31

    .local v10, arr$:[Landroid/net/NetworkState;
    array-length v0, v10

    move/from16 v21, v0

    .local v21, len$:I
    const/4 v15, 0x0

    .local v15, i$:I
    :goto_12
    move/from16 v0, v21

    if-ge v15, v0, :cond_42

    aget-object v30, v10, v15

    .line 1011
    .local v30, state:Landroid/net/NetworkState;
    move-object/from16 v0, v30

    iget-object v4, v0, Landroid/net/NetworkState;->networkInfo:Landroid/net/NetworkInfo;

    invoke-virtual {v4}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v4

    if-eqz v4, :cond_3d

    .line 1012
    move-object/from16 v0, v30

    iget-object v4, v0, Landroid/net/NetworkState;->linkProperties:Landroid/net/LinkProperties;

    invoke-virtual {v4}, Landroid/net/LinkProperties;->getInterfaceName()Ljava/lang/String;

    move-result-object v18

    .line 1013
    .local v18, iface:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    move-object/from16 v0, v30

    invoke-static {v4, v0}, Landroid/net/NetworkIdentity;->buildNetworkIdentity(Landroid/content/Context;Landroid/net/NetworkState;)Landroid/net/NetworkIdentity;

    move-result-object v17

    .line 1014
    .local v17, ident:Landroid/net/NetworkIdentity;
    move-object/from16 v0, v25

    move-object/from16 v1, v17

    move-object/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1009
    .end local v17           #ident:Landroid/net/NetworkIdentity;
    .end local v18           #iface:Ljava/lang/String;
    :cond_3d
    add-int/lit8 v15, v15, 0x1

    goto :goto_12

    .line 1001
    .end local v10           #arr$:[Landroid/net/NetworkState;
    .end local v15           #i$:I
    .end local v21           #len$:I
    .end local v25           #networks:Ljava/util/HashMap;,"Ljava/util/HashMap<Landroid/net/NetworkIdentity;Ljava/lang/String;>;"
    .end local v30           #state:Landroid/net/NetworkState;
    .end local v31           #states:[Landroid/net/NetworkState;
    :catch_40
    move-exception v11

    .line 1115
    :goto_41
    return-void

    .line 1019
    .restart local v10       #arr$:[Landroid/net/NetworkState;
    .restart local v15       #i$:I
    .restart local v21       #len$:I
    .restart local v25       #networks:Ljava/util/HashMap;,"Ljava/util/HashMap<Landroid/net/NetworkIdentity;Ljava/lang/String;>;"
    .restart local v31       #states:[Landroid/net/NetworkState;
    :cond_42
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkRules:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->clear()V

    .line 1020
    invoke-static {}, Lcom/google/android/collect/Lists;->newArrayList()Ljava/util/ArrayList;

    move-result-object v19

    .line 1021
    .local v19, ifaceList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v15

    .end local v15           #i$:I
    :cond_59
    :goto_59
    invoke-interface {v15}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_bc

    invoke-interface {v15}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v27

    check-cast v27, Landroid/net/NetworkPolicy;

    .line 1024
    .local v27, policy:Landroid/net/NetworkPolicy;
    invoke-virtual/range {v19 .. v19}, Ljava/util/ArrayList;->clear()V

    .line 1025
    invoke-virtual/range {v25 .. v25}, Ljava/util/HashMap;->entrySet()Ljava/util/Set;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v16

    .local v16, i$:Ljava/util/Iterator;
    :cond_70
    :goto_70
    invoke-interface/range {v16 .. v16}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_9c

    invoke-interface/range {v16 .. v16}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/util/Map$Entry;

    .line 1026
    .local v12, entry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Landroid/net/NetworkIdentity;Ljava/lang/String;>;"
    invoke-interface {v12}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v17

    check-cast v17, Landroid/net/NetworkIdentity;

    .line 1027
    .restart local v17       #ident:Landroid/net/NetworkIdentity;
    move-object/from16 v0, v27

    iget-object v4, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object/from16 v0, v17

    invoke-virtual {v4, v0}, Landroid/net/NetworkTemplate;->matches(Landroid/net/NetworkIdentity;)Z

    move-result v4

    if-eqz v4, :cond_70

    .line 1028
    invoke-interface {v12}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Ljava/lang/String;

    .line 1029
    .restart local v18       #iface:Ljava/lang/String;
    move-object/from16 v0, v19

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_70

    .line 1033
    .end local v12           #entry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Landroid/net/NetworkIdentity;Ljava/lang/String;>;"
    .end local v17           #ident:Landroid/net/NetworkIdentity;
    .end local v18           #iface:Ljava/lang/String;
    :cond_9c
    invoke-virtual/range {v19 .. v19}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-lez v4, :cond_59

    .line 1034
    invoke-virtual/range {v19 .. v19}, Ljava/util/ArrayList;->size()I

    move-result v4

    new-array v4, v4, [Ljava/lang/String;

    move-object/from16 v0, v19

    invoke-virtual {v0, v4}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v20

    check-cast v20, [Ljava/lang/String;

    .line 1035
    .local v20, ifaces:[Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkRules:Ljava/util/HashMap;

    move-object/from16 v0, v27

    move-object/from16 v1, v20

    invoke-virtual {v4, v0, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_59

    .line 1039
    .end local v16           #i$:Ljava/util/Iterator;
    .end local v20           #ifaces:[Ljava/lang/String;
    .end local v27           #policy:Landroid/net/NetworkPolicy;
    :cond_bc
    const-wide v22, 0x7fffffffffffffffL

    .line 1040
    .local v22, lowestRule:J
    invoke-static {}, Lcom/google/android/collect/Sets;->newHashSet()Ljava/util/HashSet;

    move-result-object v26

    .line 1044
    .local v26, newMeteredIfaces:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->currentTimeMillis()J

    move-result-wide v8

    .line 1045
    .local v8, currentTime:J
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkRules:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v15

    .end local v10           #arr$:[Landroid/net/NetworkState;
    :cond_d5
    :goto_d5
    invoke-interface {v15}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1ab

    invoke-interface {v15}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v27

    check-cast v27, Landroid/net/NetworkPolicy;

    .line 1046
    .restart local v27       #policy:Landroid/net/NetworkPolicy;
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkRules:Ljava/util/HashMap;

    move-object/from16 v0, v27

    invoke-virtual {v4, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v20

    check-cast v20, [Ljava/lang/String;

    .line 1050
    .restart local v20       #ifaces:[Ljava/lang/String;
    invoke-virtual/range {v27 .. v27}, Landroid/net/NetworkPolicy;->hasCycle()Z

    move-result v4

    if-eqz v4, :cond_15e

    .line 1051
    move-object/from16 v0, v27

    invoke-static {v8, v9, v0}, Landroid/net/NetworkPolicyManager;->computeLastCycleBoundary(JLandroid/net/NetworkPolicy;)J

    move-result-wide v6

    .line 1052
    .local v6, start:J
    move-object/from16 v0, v27

    iget-object v5, v0, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object/from16 v4, p0

    invoke-direct/range {v4 .. v9}, Lcom/android/server/net/NetworkPolicyManagerService;->getTotalBytes(Landroid/net/NetworkTemplate;JJ)J

    move-result-wide v32

    .line 1063
    .local v32, totalBytes:J
    :goto_103
    move-object/from16 v0, v27

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->warningBytes:J

    const-wide/16 v34, -0x1

    cmp-long v4, v4, v34

    if-eqz v4, :cond_166

    const/4 v14, 0x1

    .line 1064
    .local v14, hasWarning:Z
    :goto_10e
    move-object/from16 v0, v27

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    const-wide/16 v34, -0x1

    cmp-long v4, v4, v34

    if-eqz v4, :cond_168

    const/4 v13, 0x1

    .line 1065
    .local v13, hasLimit:Z
    :goto_119
    if-nez v13, :cond_121

    move-object/from16 v0, v27

    iget-boolean v4, v0, Landroid/net/NetworkPolicy;->metered:Z

    if-eqz v4, :cond_189

    .line 1067
    :cond_121
    if-nez v13, :cond_16a

    .line 1070
    const-wide v28, 0x7fffffffffffffffL

    .line 1082
    .local v28, quotaBytes:J
    :goto_128
    move-object/from16 v0, v20

    array-length v4, v0

    const/4 v5, 0x1

    if-le v4, v5, :cond_135

    .line 1084
    const-string v4, "NetworkPolicy"

    const-string v5, "shared quota unsupported; generating rule for each iface"

    invoke-static {v4, v5}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1087
    :cond_135
    move-object/from16 v10, v20

    .local v10, arr$:[Ljava/lang/String;
    array-length v0, v10

    move/from16 v21, v0

    const/16 v16, 0x0

    .local v16, i$:I
    :goto_13c
    move/from16 v0, v16

    move/from16 v1, v21

    if-ge v0, v1, :cond_189

    aget-object v18, v10, v16

    .line 1088
    .restart local v18       #iface:Ljava/lang/String;
    move-object/from16 v0, p0

    move-object/from16 v1, v18

    invoke-direct {v0, v1}, Lcom/android/server/net/NetworkPolicyManagerService;->removeInterfaceQuota(Ljava/lang/String;)V

    .line 1089
    move-object/from16 v0, p0

    move-object/from16 v1, v18

    move-wide/from16 v2, v28

    invoke-direct {v0, v1, v2, v3}, Lcom/android/server/net/NetworkPolicyManagerService;->setInterfaceQuota(Ljava/lang/String;J)V

    .line 1090
    move-object/from16 v0, v26

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 1087
    add-int/lit8 v16, v16, 0x1

    goto :goto_13c

    .line 1054
    .end local v6           #start:J
    .end local v10           #arr$:[Ljava/lang/String;
    .end local v13           #hasLimit:Z
    .end local v14           #hasWarning:Z
    .end local v16           #i$:I
    .end local v18           #iface:Ljava/lang/String;
    .end local v28           #quotaBytes:J
    .end local v32           #totalBytes:J
    :cond_15e
    const-wide v6, 0x7fffffffffffffffL

    .line 1055
    .restart local v6       #start:J
    const-wide/16 v32, 0x0

    .restart local v32       #totalBytes:J
    goto :goto_103

    .line 1063
    :cond_166
    const/4 v14, 0x0

    goto :goto_10e

    .line 1064
    .restart local v14       #hasWarning:Z
    :cond_168
    const/4 v13, 0x0

    goto :goto_119

    .line 1071
    .restart local v13       #hasLimit:Z
    :cond_16a
    move-object/from16 v0, v27

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->lastLimitSnooze:J

    cmp-long v4, v4, v6

    if-ltz v4, :cond_178

    .line 1074
    const-wide v28, 0x7fffffffffffffffL

    .restart local v28       #quotaBytes:J
    goto :goto_128

    .line 1079
    .end local v28           #quotaBytes:J
    :cond_178
    const-wide/16 v4, 0x1

    move-object/from16 v0, v27

    iget-wide v0, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    move-wide/from16 v34, v0

    sub-long v34, v34, v32

    move-wide/from16 v0, v34

    invoke-static {v4, v5, v0, v1}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v28

    .restart local v28       #quotaBytes:J
    goto :goto_128

    .line 1095
    .end local v28           #quotaBytes:J
    :cond_189
    if-eqz v14, :cond_199

    move-object/from16 v0, v27

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->warningBytes:J

    cmp-long v4, v4, v22

    if-gez v4, :cond_199

    .line 1096
    move-object/from16 v0, v27

    iget-wide v0, v0, Landroid/net/NetworkPolicy;->warningBytes:J

    move-wide/from16 v22, v0

    .line 1098
    :cond_199
    if-eqz v13, :cond_d5

    move-object/from16 v0, v27

    iget-wide v4, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    cmp-long v4, v4, v22

    if-gez v4, :cond_d5

    .line 1099
    move-object/from16 v0, v27

    iget-wide v0, v0, Landroid/net/NetworkPolicy;->limitBytes:J

    move-wide/from16 v22, v0

    goto/16 :goto_d5

    .line 1103
    .end local v6           #start:J
    .end local v13           #hasLimit:Z
    .end local v14           #hasWarning:Z
    .end local v20           #ifaces:[Ljava/lang/String;
    .end local v27           #policy:Landroid/net/NetworkPolicy;
    .end local v32           #totalBytes:J
    :cond_1ab
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    const/4 v5, 0x7

    invoke-static/range {v22 .. v23}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v34

    move-object/from16 v0, v34

    invoke-virtual {v4, v5, v0}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v4

    invoke-virtual {v4}, Landroid/os/Message;->sendToTarget()V

    .line 1106
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    invoke-virtual {v4}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v15

    .local v15, i$:Ljava/util/Iterator;
    :cond_1c5
    :goto_1c5
    invoke-interface {v15}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1e3

    invoke-interface {v15}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Ljava/lang/String;

    .line 1107
    .restart local v18       #iface:Ljava/lang/String;
    move-object/from16 v0, v26

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_1c5

    .line 1108
    move-object/from16 v0, p0

    move-object/from16 v1, v18

    invoke-direct {v0, v1}, Lcom/android/server/net/NetworkPolicyManagerService;->removeInterfaceQuota(Ljava/lang/String;)V

    goto :goto_1c5

    .line 1111
    .end local v18           #iface:Ljava/lang/String;
    :cond_1e3
    move-object/from16 v0, v26

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    .line 1113
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mMeteredIfaces:Ljava/util/HashSet;

    invoke-virtual {v5}, Ljava/util/HashSet;->size()I

    move-result v5

    new-array v5, v5, [Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/util/HashSet;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v24

    check-cast v24, [Ljava/lang/String;

    .line 1114
    .local v24, meteredIfaces:[Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    const/4 v5, 0x2

    move-object/from16 v0, v24

    invoke-virtual {v4, v5, v0}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v4

    invoke-virtual {v4}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_41
.end method

.method private updateNotificationsLocked()V
    .registers 15

    .prologue
    .line 646
    invoke-static {}, Lcom/google/android/collect/Sets;->newHashSet()Ljava/util/HashSet;

    move-result-object v6

    .line 647
    .local v6, beforeNotifs:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    invoke-virtual {v6, v0}, Ljava/util/HashSet;->addAll(Ljava/util/Collection;)Z

    .line 648
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    invoke-virtual {v0}, Ljava/util/HashSet;->clear()V

    .line 654
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->currentTimeMillis()J

    move-result-wide v7

    .line 655
    .local v7, currentTime:J
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v9

    .local v9, i$:Ljava/util/Iterator;
    :cond_1c
    :goto_1c
    invoke-interface {v9}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_73

    invoke-interface {v9}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Landroid/net/NetworkPolicy;

    .line 657
    .local v10, policy:Landroid/net/NetworkPolicy;
    iget-object v0, v10, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-direct {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->isTemplateRelevant(Landroid/net/NetworkTemplate;)Z

    move-result v0

    if-eqz v0, :cond_1c

    .line 658
    invoke-virtual {v10}, Landroid/net/NetworkPolicy;->hasCycle()Z

    move-result v0

    if-eqz v0, :cond_1c

    .line 660
    invoke-static {v7, v8, v10}, Landroid/net/NetworkPolicyManager;->computeLastCycleBoundary(JLandroid/net/NetworkPolicy;)J

    move-result-wide v2

    .line 661
    .local v2, start:J
    move-wide v4, v7

    .line 662
    .local v4, end:J
    iget-object v1, v10, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    move-object v0, p0

    invoke-direct/range {v0 .. v5}, Lcom/android/server/net/NetworkPolicyManagerService;->getTotalBytes(Landroid/net/NetworkTemplate;JJ)J

    move-result-wide v12

    .line 664
    .local v12, totalBytes:J
    invoke-virtual {v10, v12, v13}, Landroid/net/NetworkPolicy;->isOverLimit(J)Z

    move-result v0

    if-eqz v0, :cond_5d

    .line 665
    iget-wide v0, v10, Landroid/net/NetworkPolicy;->lastLimitSnooze:J

    cmp-long v0, v0, v2

    if-ltz v0, :cond_53

    .line 666
    const/4 v0, 0x3

    invoke-direct {p0, v10, v0, v12, v13}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueValidNotification(Landroid/net/NetworkPolicy;IJ)V

    goto :goto_1c

    .line 668
    :cond_53
    const/4 v0, 0x2

    invoke-direct {p0, v10, v0, v12, v13}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueValidNotification(Landroid/net/NetworkPolicy;IJ)V

    .line 669
    iget-object v0, v10, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-direct {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->notifyOverLimitLocked(Landroid/net/NetworkTemplate;)V

    goto :goto_1c

    .line 673
    :cond_5d
    iget-object v0, v10, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-direct {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->notifyUnderLimitLocked(Landroid/net/NetworkTemplate;)V

    .line 675
    invoke-virtual {v10, v12, v13}, Landroid/net/NetworkPolicy;->isOverWarning(J)Z

    move-result v0

    if-eqz v0, :cond_1c

    iget-wide v0, v10, Landroid/net/NetworkPolicy;->lastWarningSnooze:J

    cmp-long v0, v0, v2

    if-gez v0, :cond_1c

    .line 676
    const/4 v0, 0x1

    invoke-direct {p0, v10, v0, v12, v13}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueValidNotification(Landroid/net/NetworkPolicy;IJ)V

    goto :goto_1c

    .line 682
    .end local v2           #start:J
    .end local v4           #end:J
    .end local v10           #policy:Landroid/net/NetworkPolicy;
    .end local v12           #totalBytes:J
    :cond_73
    iget-boolean v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    if-eqz v0, :cond_7c

    .line 683
    const-string v0, "NetworkPolicy:allowBackground"

    invoke-direct {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueRestrictedNotification(Ljava/lang/String;)V

    .line 687
    :cond_7c
    invoke-virtual {v6}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v9

    :cond_80
    :goto_80
    invoke-interface {v9}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_98

    invoke-interface {v9}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/String;

    .line 688
    .local v11, tag:Ljava/lang/String;
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActiveNotifs:Ljava/util/HashSet;

    invoke-virtual {v0, v11}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_80

    .line 689
    invoke-direct {p0, v11}, Lcom/android/server/net/NetworkPolicyManagerService;->cancelNotification(Ljava/lang/String;)V

    goto :goto_80

    .line 692
    .end local v11           #tag:Ljava/lang/String;
    :cond_98
    return-void
.end method

.method private updateRulesForAppLocked(I)V
    .registers 6
    .parameter "appId"

    .prologue
    .line 1773
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/pm/PackageManager;->getUsers()Ljava/util/List;

    move-result-object v3

    invoke-interface {v3}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :goto_e
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_24

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/content/pm/UserInfo;

    .line 1774
    .local v2, user:Landroid/content/pm/UserInfo;
    iget v3, v2, Landroid/content/pm/UserInfo;->id:I

    invoke-static {v3, p1}, Landroid/os/UserId;->getUid(II)I

    move-result v1

    .line 1775
    .local v1, uid:I
    invoke-direct {p0, v1}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForUidLocked(I)V

    goto :goto_e

    .line 1777
    .end local v1           #uid:I
    .end local v2           #user:Landroid/content/pm/UserInfo;
    :cond_24
    return-void
.end method

.method private updateRulesForRestrictBackgroundLocked()V
    .registers 7

    .prologue
    .line 1760
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v4

    .line 1761
    .local v4, pm:Landroid/content/pm/PackageManager;
    const/4 v5, 0x0

    invoke-virtual {v4, v5}, Landroid/content/pm/PackageManager;->getInstalledApplications(I)Ljava/util/List;

    move-result-object v2

    .line 1762
    .local v2, apps:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ApplicationInfo;>;"
    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .local v3, i$:Ljava/util/Iterator;
    :goto_f
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_25

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/pm/ApplicationInfo;

    .line 1763
    .local v0, app:Landroid/content/pm/ApplicationInfo;
    iget v5, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-static {v5}, Landroid/os/UserId;->getAppId(I)I

    move-result v1

    .line 1764
    .local v1, appId:I
    invoke-direct {p0, v1}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForAppLocked(I)V

    goto :goto_f

    .line 1768
    .end local v0           #app:Landroid/content/pm/ApplicationInfo;
    .end local v1           #appId:I
    :cond_25
    const/16 v5, 0x3f5

    invoke-direct {p0, v5}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForUidLocked(I)V

    .line 1769
    const/16 v5, 0x3fb

    invoke-direct {p0, v5}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForUidLocked(I)V

    .line 1770
    return-void
.end method

.method private updateRulesForScreenLocked()V
    .registers 5

    .prologue
    .line 1746
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    invoke-virtual {v3}, Landroid/util/SparseBooleanArray;->size()I

    move-result v1

    .line 1747
    .local v1, size:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_7
    if-ge v0, v1, :cond_1d

    .line 1748
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    invoke-virtual {v3, v0}, Landroid/util/SparseBooleanArray;->valueAt(I)Z

    move-result v3

    if-eqz v3, :cond_1a

    .line 1749
    iget-object v3, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    invoke-virtual {v3, v0}, Landroid/util/SparseBooleanArray;->keyAt(I)I

    move-result v2

    .line 1750
    .local v2, uid:I
    invoke-direct {p0, v2}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForUidLocked(I)V

    .line 1747
    .end local v2           #uid:I
    :cond_1a
    add-int/lit8 v0, v0, 0x1

    goto :goto_7

    .line 1753
    :cond_1d
    return-void
.end method

.method private updateRulesForUidLocked(I)V
    .registers 9
    .parameter "uid"

    .prologue
    const/4 v5, 0x1

    .line 1790
    invoke-static {p1}, Lcom/android/server/net/NetworkPolicyManagerService;->isUidValidForRules(I)Z

    move-result v6

    if-nez v6, :cond_8

    .line 1827
    :goto_7
    return-void

    .line 1792
    :cond_8
    invoke-static {p1}, Landroid/os/UserId;->getAppId(I)I

    move-result v0

    .line 1793
    .local v0, appId:I
    invoke-virtual {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->getAppPolicy(I)I

    move-result v1

    .line 1794
    .local v1, appPolicy:I
    invoke-virtual {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->isUidForeground(I)Z

    move-result v3

    .line 1797
    .local v3, uidForeground:Z
    const/4 v4, 0x0

    .line 1798
    .local v4, uidRules:I
    if-nez v3, :cond_1c

    and-int/lit8 v6, v1, 0x1

    if-eqz v6, :cond_1c

    .line 1800
    const/4 v4, 0x1

    .line 1802
    :cond_1c
    if-nez v3, :cond_23

    iget-boolean v6, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    if-eqz v6, :cond_23

    .line 1804
    const/4 v4, 0x1

    .line 1809
    :cond_23
    if-nez v4, :cond_43

    .line 1810
    iget-object v6, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    invoke-virtual {v6, p1}, Landroid/util/SparseIntArray;->delete(I)V

    .line 1815
    :goto_2a
    and-int/lit8 v6, v4, 0x1

    if-eqz v6, :cond_49

    move v2, v5

    .line 1816
    .local v2, rejectMetered:Z
    :goto_2f
    invoke-direct {p0, p1, v2}, Lcom/android/server/net/NetworkPolicyManagerService;->setUidNetworkRules(IZ)V

    .line 1819
    iget-object v6, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v6, v5, p1, v4}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v5

    invoke-virtual {v5}, Landroid/os/Message;->sendToTarget()V

    .line 1823
    :try_start_3b
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkStats:Landroid/net/INetworkStatsService;

    invoke-interface {v5, p1, v3}, Landroid/net/INetworkStatsService;->setUidForeground(IZ)V
    :try_end_40
    .catch Landroid/os/RemoteException; {:try_start_3b .. :try_end_40} :catch_41

    goto :goto_7

    .line 1824
    :catch_41
    move-exception v5

    goto :goto_7

    .line 1812
    .end local v2           #rejectMetered:Z
    :cond_43
    iget-object v6, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    invoke-virtual {v6, p1, v4}, Landroid/util/SparseIntArray;->put(II)V

    goto :goto_2a

    .line 1815
    :cond_49
    const/4 v2, 0x0

    goto :goto_2f
.end method

.method private updateScreenOn()V
    .registers 3

    .prologue
    .line 1731
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v1

    .line 1733
    :try_start_3
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPowerManager:Landroid/os/IPowerManager;

    invoke-interface {v0}, Landroid/os/IPowerManager;->isScreenOn()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mScreenOn:Z
    :try_end_b
    .catchall {:try_start_3 .. :try_end_b} :catchall_10
    .catch Landroid/os/RemoteException; {:try_start_3 .. :try_end_b} :catch_13

    .line 1737
    :goto_b
    :try_start_b
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForScreenLocked()V

    .line 1738
    monitor-exit v1

    .line 1739
    return-void

    .line 1738
    :catchall_10
    move-exception v0

    monitor-exit v1
    :try_end_12
    .catchall {:try_start_b .. :try_end_12} :catchall_10

    throw v0

    .line 1734
    :catch_13
    move-exception v0

    goto :goto_b
.end method

.method private upgradeLegacyBackgroundData()V
    .registers 5

    .prologue
    const/4 v1, 0x1

    .line 1289
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string v3, "background_data"

    invoke-static {v2, v3, v1}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    if-eq v2, v1, :cond_22

    :goto_f
    iput-boolean v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    .line 1293
    iget-boolean v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    if-eqz v1, :cond_21

    .line 1294
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1296
    .local v0, broadcast:Landroid/content/Intent;
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    .line 1298
    .end local v0           #broadcast:Landroid/content/Intent;
    :cond_21
    return-void

    .line 1289
    :cond_22
    const/4 v1, 0x0

    goto :goto_f
.end method

.method private writePolicyLocked()V
    .registers 14

    .prologue
    .line 1303
    const/4 v2, 0x0

    .line 1305
    .local v2, fos:Ljava/io/FileOutputStream;
    :try_start_1
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v10}, Lcom/android/internal/os/AtomicFile;->startWrite()Ljava/io/FileOutputStream;

    move-result-object v2

    .line 1307
    new-instance v6, Lcom/android/internal/util/FastXmlSerializer;

    invoke-direct {v6}, Lcom/android/internal/util/FastXmlSerializer;-><init>()V

    .line 1308
    .local v6, out:Lorg/xmlpull/v1/XmlSerializer;
    const-string v10, "utf-8"

    invoke-interface {v6, v2, v10}, Lorg/xmlpull/v1/XmlSerializer;->setOutput(Ljava/io/OutputStream;Ljava/lang/String;)V

    .line 1309
    const/4 v10, 0x0

    const/4 v11, 0x1

    invoke-static {v11}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v11

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->startDocument(Ljava/lang/String;Ljava/lang/Boolean;)V

    .line 1311
    const/4 v10, 0x0

    const-string v11, "policy-list"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1312
    const-string v10, "version"

    const/16 v11, 0x9

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeIntAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;I)V

    .line 1313
    const-string v10, "restrictBackground"

    iget-boolean v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeBooleanAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Z)V

    .line 1316
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v10}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v10

    invoke-interface {v10}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v4

    .local v4, i$:Ljava/util/Iterator;
    :goto_38
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v10

    if-eqz v10, :cond_b6

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Landroid/net/NetworkPolicy;

    .line 1317
    .local v7, policy:Landroid/net/NetworkPolicy;
    iget-object v9, v7, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    .line 1319
    .local v9, template:Landroid/net/NetworkTemplate;
    const/4 v10, 0x0

    const-string v11, "network-policy"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1320
    const-string v10, "networkTemplate"

    invoke-virtual {v9}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v11

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeIntAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;I)V

    .line 1321
    invoke-virtual {v9}, Landroid/net/NetworkTemplate;->getSubscriberId()Ljava/lang/String;

    move-result-object v8

    .line 1322
    .local v8, subscriberId:Ljava/lang/String;
    if-eqz v8, :cond_61

    .line 1323
    const/4 v10, 0x0

    const-string v11, "subscriberId"

    invoke-interface {v6, v10, v11, v8}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1325
    :cond_61
    invoke-virtual {v9}, Landroid/net/NetworkTemplate;->getNetworkId()Ljava/lang/String;

    move-result-object v5

    .line 1326
    .local v5, networkId:Ljava/lang/String;
    if-eqz v5, :cond_6d

    .line 1327
    const/4 v10, 0x0

    const-string v11, "networkId"

    invoke-interface {v6, v10, v11, v5}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1329
    :cond_6d
    const-string v10, "cycleDay"

    iget v11, v7, Landroid/net/NetworkPolicy;->cycleDay:I

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeIntAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;I)V

    .line 1330
    const/4 v10, 0x0

    const-string v11, "cycleTimezone"

    iget-object v12, v7, Landroid/net/NetworkPolicy;->cycleTimezone:Ljava/lang/String;

    invoke-interface {v6, v10, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1331
    const-string v10, "warningBytes"

    iget-wide v11, v7, Landroid/net/NetworkPolicy;->warningBytes:J

    invoke-static {v6, v10, v11, v12}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeLongAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;J)V

    .line 1332
    const-string v10, "limitBytes"

    iget-wide v11, v7, Landroid/net/NetworkPolicy;->limitBytes:J

    invoke-static {v6, v10, v11, v12}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeLongAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;J)V

    .line 1333
    const-string v10, "lastWarningSnooze"

    iget-wide v11, v7, Landroid/net/NetworkPolicy;->lastWarningSnooze:J

    invoke-static {v6, v10, v11, v12}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeLongAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;J)V

    .line 1334
    const-string v10, "lastLimitSnooze"

    iget-wide v11, v7, Landroid/net/NetworkPolicy;->lastLimitSnooze:J

    invoke-static {v6, v10, v11, v12}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeLongAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;J)V

    .line 1335
    const-string v10, "metered"

    iget-boolean v11, v7, Landroid/net/NetworkPolicy;->metered:Z

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeBooleanAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Z)V

    .line 1336
    const-string v10, "inferred"

    iget-boolean v11, v7, Landroid/net/NetworkPolicy;->inferred:Z

    invoke-static {v6, v10, v11}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeBooleanAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Z)V

    .line 1337
    const/4 v10, 0x0

    const-string v11, "network-policy"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;
    :try_end_ac
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_ac} :catch_ad

    goto :goto_38

    .line 1358
    .end local v4           #i$:Ljava/util/Iterator;
    .end local v5           #networkId:Ljava/lang/String;
    .end local v6           #out:Lorg/xmlpull/v1/XmlSerializer;
    .end local v7           #policy:Landroid/net/NetworkPolicy;
    .end local v8           #subscriberId:Ljava/lang/String;
    .end local v9           #template:Landroid/net/NetworkTemplate;
    :catch_ad
    move-exception v1

    .line 1359
    .local v1, e:Ljava/io/IOException;
    if-eqz v2, :cond_b5

    .line 1360
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v10, v2}, Lcom/android/internal/os/AtomicFile;->failWrite(Ljava/io/FileOutputStream;)V

    .line 1363
    .end local v1           #e:Ljava/io/IOException;
    :cond_b5
    :goto_b5
    return-void

    .line 1341
    .restart local v4       #i$:Ljava/util/Iterator;
    .restart local v6       #out:Lorg/xmlpull/v1/XmlSerializer;
    :cond_b6
    const/4 v3, 0x0

    .local v3, i:I
    :goto_b7
    :try_start_b7
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v10}, Landroid/util/SparseIntArray;->size()I

    move-result v10

    if-ge v3, v10, :cond_e7

    .line 1342
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v10, v3}, Landroid/util/SparseIntArray;->keyAt(I)I

    move-result v0

    .line 1343
    .local v0, appId:I
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v10, v3}, Landroid/util/SparseIntArray;->valueAt(I)I

    move-result v7

    .line 1346
    .local v7, policy:I
    if-nez v7, :cond_d0

    .line 1341
    :goto_cd
    add-int/lit8 v3, v3, 0x1

    goto :goto_b7

    .line 1348
    :cond_d0
    const/4 v10, 0x0

    const-string v11, "app-policy"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1349
    const-string v10, "appId"

    invoke-static {v6, v10, v0}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeIntAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;I)V

    .line 1350
    const-string v10, "policy"

    invoke-static {v6, v10, v7}, Lcom/android/server/net/NetworkPolicyManagerService$XmlUtils;->writeIntAttribute(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;I)V

    .line 1351
    const/4 v10, 0x0

    const-string v11, "app-policy"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    goto :goto_cd

    .line 1354
    .end local v0           #appId:I
    .end local v7           #policy:I
    :cond_e7
    const/4 v10, 0x0

    const-string v11, "policy-list"

    invoke-interface {v6, v10, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1355
    invoke-interface {v6}, Lorg/xmlpull/v1/XmlSerializer;->endDocument()V

    .line 1357
    iget-object v10, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v10, v2}, Lcom/android/internal/os/AtomicFile;->finishWrite(Ljava/io/FileOutputStream;)V
    :try_end_f5
    .catch Ljava/io/IOException; {:try_start_b7 .. :try_end_f5} :catch_ad

    goto :goto_b5
.end method


# virtual methods
.method public addIdleHandler(Landroid/os/MessageQueue$IdleHandler;)V
    .registers 3
    .parameter "handler"

    .prologue
    .line 2065
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0}, Landroid/os/Handler;->getLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/Looper;->getQueue()Landroid/os/MessageQueue;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/os/MessageQueue;->addIdleHandler(Landroid/os/MessageQueue$IdleHandler;)V

    .line 2066
    return-void
.end method

.method public bindConnectivityManager(Landroid/net/IConnectivityManager;)V
    .registers 3
    .parameter "connManager"

    .prologue
    .line 368
    const-string v0, "missing IConnectivityManager"

    invoke-static {p1, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/IConnectivityManager;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mConnManager:Landroid/net/IConnectivityManager;

    .line 369
    return-void
.end method

.method public bindNotificationManager(Landroid/app/INotificationManager;)V
    .registers 3
    .parameter "notifManager"

    .prologue
    .line 372
    const-string v0, "missing INotificationManager"

    invoke-static {p1, v0}, Lcom/android/internal/util/Preconditions;->checkNotNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/INotificationManager;

    iput-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNotifManager:Landroid/app/INotificationManager;

    .line 373
    return-void
.end method

.method protected dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .registers 22
    .parameter "fd"
    .parameter "writer"
    .parameter "args"

    .prologue
    .line 1615
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v16, "android.permission.DUMP"

    const-string v17, "NetworkPolicy"

    invoke-virtual/range {v15 .. v17}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1617
    new-instance v6, Lcom/android/internal/util/IndentingPrintWriter;

    const-string v15, "  "

    move-object/from16 v0, p2

    invoke-direct {v6, v0, v15}, Lcom/android/internal/util/IndentingPrintWriter;-><init>(Ljava/io/Writer;Ljava/lang/String;)V

    .line 1619
    .local v6, fout:Lcom/android/internal/util/IndentingPrintWriter;
    new-instance v3, Ljava/util/HashSet;

    invoke-direct {v3}, Ljava/util/HashSet;-><init>()V

    .line 1620
    .local v3, argSet:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    move-object/from16 v4, p3

    .local v4, arr$:[Ljava/lang/String;
    array-length v10, v4

    .local v10, len$:I
    const/4 v8, 0x0

    .local v8, i$:I
    :goto_1d
    if-ge v8, v10, :cond_27

    aget-object v2, v4, v8

    .line 1621
    .local v2, arg:Ljava/lang/String;
    invoke-virtual {v3, v2}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 1620
    add-int/lit8 v8, v8, 0x1

    goto :goto_1d

    .line 1624
    .end local v2           #arg:Ljava/lang/String;
    :cond_27
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    move-object/from16 v16, v0

    monitor-enter v16

    .line 1625
    :try_start_2e
    const-string v15, "--unsnooze"

    invoke-virtual {v3, v15}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v15

    if-eqz v15, :cond_68

    .line 1626
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v15}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v15

    invoke-interface {v15}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v8

    .local v8, i$:Ljava/util/Iterator;
    :goto_42
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_55

    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Landroid/net/NetworkPolicy;

    .line 1627
    .local v11, policy:Landroid/net/NetworkPolicy;
    invoke-virtual {v11}, Landroid/net/NetworkPolicy;->clearSnooze()V

    goto :goto_42

    .line 1692
    .end local v8           #i$:Ljava/util/Iterator;
    .end local v11           #policy:Landroid/net/NetworkPolicy;
    :catchall_52
    move-exception v15

    monitor-exit v16
    :try_end_54
    .catchall {:try_start_2e .. :try_end_54} :catchall_52

    throw v15

    .line 1630
    .restart local v8       #i$:Ljava/util/Iterator;
    :cond_55
    :try_start_55
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V

    .line 1631
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V

    .line 1632
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 1633
    invoke-direct/range {p0 .. p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1635
    const-string v15, "Cleared snooze timestamps"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    .line 1636
    monitor-exit v16

    .line 1693
    :goto_67
    return-void

    .line 1639
    .local v8, i$:I
    :cond_68
    const-string v15, "Restrict background: "

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    move-object/from16 v0, p0

    iget-boolean v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Z)V

    .line 1640
    const-string v15, "Network policies:"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    .line 1641
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->increaseIndent()V

    .line 1642
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v15}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v15

    invoke-interface {v15}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v8

    .local v8, i$:Ljava/util/Iterator;
    :goto_88
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_9c

    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Landroid/net/NetworkPolicy;

    .line 1643
    .restart local v11       #policy:Landroid/net/NetworkPolicy;
    invoke-virtual {v11}, Landroid/net/NetworkPolicy;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    goto :goto_88

    .line 1645
    .end local v11           #policy:Landroid/net/NetworkPolicy;
    :cond_9c
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->decreaseIndent()V

    .line 1647
    const-string v15, "Policy for apps:"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    .line 1648
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->increaseIndent()V

    .line 1649
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v15}, Landroid/util/SparseIntArray;->size()I

    move-result v13

    .line 1650
    .local v13, size:I
    const/4 v7, 0x0

    .local v7, i:I
    :goto_b0
    if-ge v7, v13, :cond_d8

    .line 1651
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v15, v7}, Landroid/util/SparseIntArray;->keyAt(I)I

    move-result v1

    .line 1652
    .local v1, appId:I
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v15, v7}, Landroid/util/SparseIntArray;->valueAt(I)I

    move-result v11

    .line 1653
    .local v11, policy:I
    const-string v15, "appId="

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1654
    invoke-virtual {v6, v1}, Lcom/android/internal/util/IndentingPrintWriter;->print(I)V

    .line 1655
    const-string v15, " policy="

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1656
    invoke-static {v6, v11}, Landroid/net/NetworkPolicyManager;->dumpPolicy(Ljava/io/PrintWriter;I)V

    .line 1657
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->println()V

    .line 1650
    add-int/lit8 v7, v7, 0x1

    goto :goto_b0

    .line 1659
    .end local v1           #appId:I
    .end local v11           #policy:I
    :cond_d8
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->decreaseIndent()V

    .line 1661
    new-instance v9, Landroid/util/SparseBooleanArray;

    invoke-direct {v9}, Landroid/util/SparseBooleanArray;-><init>()V

    .line 1662
    .local v9, knownUids:Landroid/util/SparseBooleanArray;
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    invoke-static {v15, v9}, Lcom/android/server/net/NetworkPolicyManagerService;->collectKeys(Landroid/util/SparseBooleanArray;Landroid/util/SparseBooleanArray;)V

    .line 1663
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    invoke-static {v15, v9}, Lcom/android/server/net/NetworkPolicyManagerService;->collectKeys(Landroid/util/SparseIntArray;Landroid/util/SparseBooleanArray;)V

    .line 1665
    const-string v15, "Status for known UIDs:"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    .line 1666
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->increaseIndent()V

    .line 1667
    invoke-virtual {v9}, Landroid/util/SparseBooleanArray;->size()I

    move-result v13

    .line 1668
    const/4 v7, 0x0

    :goto_fb
    if-ge v7, v13, :cond_151

    .line 1669
    invoke-virtual {v9, v7}, Landroid/util/SparseBooleanArray;->keyAt(I)I

    move-result v14

    .line 1670
    .local v14, uid:I
    const-string v15, "UID="

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1671
    invoke-virtual {v6, v14}, Lcom/android/internal/util/IndentingPrintWriter;->print(I)V

    .line 1673
    const-string v15, " foreground="

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1674
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidPidForeground:Landroid/util/SparseArray;

    invoke-virtual {v15, v14}, Landroid/util/SparseArray;->indexOfKey(I)I

    move-result v5

    .line 1675
    .local v5, foregroundIndex:I
    if-gez v5, :cond_137

    .line 1676
    const-string v15, "UNKNOWN"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1681
    :goto_11d
    const-string v15, " rules="

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1682
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    invoke-virtual {v15, v14}, Landroid/util/SparseIntArray;->indexOfKey(I)I

    move-result v12

    .line 1683
    .local v12, rulesIndex:I
    if-gez v12, :cond_145

    .line 1684
    const-string v15, "UNKNOWN"

    invoke-virtual {v6, v15}, Lcom/android/internal/util/IndentingPrintWriter;->print(Ljava/lang/String;)V

    .line 1689
    :goto_131
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->println()V

    .line 1668
    add-int/lit8 v7, v7, 0x1

    goto :goto_fb

    .line 1678
    .end local v12           #rulesIndex:I
    :cond_137
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidPidForeground:Landroid/util/SparseArray;

    invoke-virtual {v15, v5}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/util/SparseBooleanArray;

    invoke-static {v6, v15}, Lcom/android/server/net/NetworkPolicyManagerService;->dumpSparseBooleanArray(Ljava/io/PrintWriter;Landroid/util/SparseBooleanArray;)V

    goto :goto_11d

    .line 1686
    .restart local v12       #rulesIndex:I
    :cond_145
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidRules:Landroid/util/SparseIntArray;

    invoke-virtual {v15, v12}, Landroid/util/SparseIntArray;->valueAt(I)I

    move-result v15

    invoke-static {v6, v15}, Landroid/net/NetworkPolicyManager;->dumpRules(Ljava/io/PrintWriter;I)V

    goto :goto_131

    .line 1691
    .end local v5           #foregroundIndex:I
    .end local v12           #rulesIndex:I
    .end local v14           #uid:I
    :cond_151
    invoke-virtual {v6}, Lcom/android/internal/util/IndentingPrintWriter;->decreaseIndent()V

    .line 1692
    monitor-exit v16
    :try_end_155
    .catchall {:try_start_55 .. :try_end_155} :catchall_52

    goto/16 :goto_67
.end method

.method public getAppPolicy(I)I
    .registers 5
    .parameter "appId"

    .prologue
    .line 1392
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1394
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v1

    .line 1395
    :try_start_c
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    const/4 v2, 0x0

    invoke-virtual {v0, p1, v2}, Landroid/util/SparseIntArray;->get(II)I

    move-result v0

    monitor-exit v1

    return v0

    .line 1396
    :catchall_15
    move-exception v0

    monitor-exit v1
    :try_end_17
    .catchall {:try_start_c .. :try_end_17} :catchall_15

    throw v0
.end method

.method public getAppsWithPolicy(I)[I
    .registers 9
    .parameter "policy"

    .prologue
    .line 1401
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v5, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v6, "NetworkPolicy"

    invoke-virtual {v4, v5, v6}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1403
    const/4 v4, 0x0

    new-array v1, v4, [I

    .line 1404
    .local v1, appIds:[I
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v5

    .line 1405
    const/4 v3, 0x0

    .local v3, i:I
    :goto_10
    :try_start_10
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v4}, Landroid/util/SparseIntArray;->size()I

    move-result v4

    if-ge v3, v4, :cond_2d

    .line 1406
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v4, v3}, Landroid/util/SparseIntArray;->keyAt(I)I

    move-result v0

    .line 1407
    .local v0, appId:I
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAppPolicy:Landroid/util/SparseIntArray;

    invoke-virtual {v4, v3}, Landroid/util/SparseIntArray;->valueAt(I)I

    move-result v2

    .line 1408
    .local v2, appPolicy:I
    if-ne v2, p1, :cond_2a

    .line 1409
    invoke-static {v1, v0}, Lcom/android/internal/util/ArrayUtils;->appendInt([II)[I

    move-result-object v1

    .line 1405
    :cond_2a
    add-int/lit8 v3, v3, 0x1

    goto :goto_10

    .line 1412
    .end local v0           #appId:I
    .end local v2           #appPolicy:I
    :cond_2d
    monitor-exit v5

    .line 1413
    return-object v1

    .line 1412
    :catchall_2f
    move-exception v4

    monitor-exit v5
    :try_end_31
    .catchall {:try_start_10 .. :try_end_31} :catchall_2f

    throw v4
.end method

.method getContext()Landroid/content/Context;
    .registers 2
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 174
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method public getNetworkPolicies()[Landroid/net/NetworkPolicy;
    .registers 4

    .prologue
    .line 1463
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1464
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.READ_PHONE_STATE"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1466
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v1

    .line 1467
    :try_start_15
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v2}, Ljava/util/HashMap;->size()I

    move-result v2

    new-array v2, v2, [Landroid/net/NetworkPolicy;

    invoke-interface {v0, v2}, Ljava/util/Collection;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Landroid/net/NetworkPolicy;

    monitor-exit v1

    return-object v0

    .line 1468
    :catchall_2b
    move-exception v0

    monitor-exit v1
    :try_end_2d
    .catchall {:try_start_15 .. :try_end_2d} :catchall_2b

    throw v0
.end method

.method public getNetworkQuotaInfo(Landroid/net/NetworkState;)Landroid/net/NetworkQuotaInfo;
    .registers 7
    .parameter "state"

    .prologue
    .line 1547
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.ACCESS_NETWORK_STATE"

    const-string v4, "NetworkPolicy"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1551
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v0

    .line 1553
    .local v0, token:J
    :try_start_d
    invoke-direct {p0, p1}, Lcom/android/server/net/NetworkPolicyManagerService;->getNetworkQuotaInfoUnchecked(Landroid/net/NetworkState;)Landroid/net/NetworkQuotaInfo;
    :try_end_10
    .catchall {:try_start_d .. :try_end_10} :catchall_15

    move-result-object v2

    .line 1555
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1553
    return-object v2

    .line 1555
    :catchall_15
    move-exception v2

    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v2
.end method

.method public getRestrictBackground()Z
    .registers 4

    .prologue
    .line 1529
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1531
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v1

    .line 1532
    :try_start_c
    iget-boolean v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    monitor-exit v1

    return v0

    .line 1533
    :catchall_10
    move-exception v0

    monitor-exit v1
    :try_end_12
    .catchall {:try_start_c .. :try_end_12} :catchall_10

    throw v0
.end method

.method public isNetworkMetered(Landroid/net/NetworkState;)Z
    .registers 7
    .parameter "state"

    .prologue
    const/4 v3, 0x1

    .line 1590
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-static {v4, p1}, Landroid/net/NetworkIdentity;->buildNetworkIdentity(Landroid/content/Context;Landroid/net/NetworkState;)Landroid/net/NetworkIdentity;

    move-result-object v0

    .line 1593
    .local v0, ident:Landroid/net/NetworkIdentity;
    invoke-virtual {v0}, Landroid/net/NetworkIdentity;->getRoaming()Z

    move-result v4

    if-eqz v4, :cond_e

    .line 1609
    :cond_d
    :goto_d
    return v3

    .line 1598
    :cond_e
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v4

    .line 1599
    :try_start_11
    invoke-direct {p0, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->findPolicyForNetworkLocked(Landroid/net/NetworkIdentity;)Landroid/net/NetworkPolicy;

    move-result-object v1

    .line 1600
    .local v1, policy:Landroid/net/NetworkPolicy;
    monitor-exit v4
    :try_end_16
    .catchall {:try_start_11 .. :try_end_16} :catchall_1b

    .line 1602
    if-eqz v1, :cond_1e

    .line 1603
    iget-boolean v3, v1, Landroid/net/NetworkPolicy;->metered:Z

    goto :goto_d

    .line 1600
    .end local v1           #policy:Landroid/net/NetworkPolicy;
    :catchall_1b
    move-exception v3

    :try_start_1c
    monitor-exit v4
    :try_end_1d
    .catchall {:try_start_1c .. :try_end_1d} :catchall_1b

    throw v3

    .line 1605
    .restart local v1       #policy:Landroid/net/NetworkPolicy;
    :cond_1e
    iget-object v4, p1, Landroid/net/NetworkState;->networkInfo:Landroid/net/NetworkInfo;

    invoke-virtual {v4}, Landroid/net/NetworkInfo;->getType()I

    move-result v2

    .line 1606
    .local v2, type:I
    invoke-static {v2}, Landroid/net/ConnectivityManager;->isNetworkTypeMobile(I)Z

    move-result v4

    if-nez v4, :cond_d

    const/4 v4, 0x6

    if-eq v2, v4, :cond_d

    .line 1609
    const/4 v3, 0x0

    goto :goto_d
.end method

.method public isUidForeground(I)Z
    .registers 6
    .parameter "uid"

    .prologue
    const/4 v0, 0x0

    .line 1697
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v3, "NetworkPolicy"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1699
    iget-object v1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v1

    .line 1701
    :try_start_d
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mUidForeground:Landroid/util/SparseBooleanArray;

    const/4 v3, 0x0

    invoke-virtual {v2, p1, v3}, Landroid/util/SparseBooleanArray;->get(IZ)Z

    move-result v2

    if-eqz v2, :cond_1b

    iget-boolean v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mScreenOn:Z

    if-eqz v2, :cond_1b

    const/4 v0, 0x1

    :cond_1b
    monitor-exit v1

    return v0

    .line 1702
    :catchall_1d
    move-exception v0

    monitor-exit v1
    :try_end_1f
    .catchall {:try_start_d .. :try_end_1f} :catchall_1d

    throw v0
.end method

.method public registerListener(Landroid/net/INetworkPolicyListener;)V
    .registers 5
    .parameter "listener"

    .prologue
    .line 1419
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.CONNECTIVITY_INTERNAL"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1421
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mListeners:Landroid/os/RemoteCallbackList;

    invoke-virtual {v0, p1}, Landroid/os/RemoteCallbackList;->register(Landroid/os/IInterface;)Z

    .line 1424
    return-void
.end method

.method public setAppPolicy(II)V
    .registers 6
    .parameter "appId"
    .parameter "policy"

    .prologue
    .line 1367
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1369
    invoke-static {p1}, Landroid/os/UserId;->isApp(I)Z

    move-result v0

    if-nez v0, :cond_28

    .line 1370
    new-instance v0, Ljava/lang/IllegalArgumentException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "cannot apply policy to appId "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 1373
    :cond_28
    const/4 v0, 0x1

    invoke-direct {p0, p1, p2, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->setAppPolicyUnchecked(IIZ)V

    .line 1374
    return-void
.end method

.method public setNetworkPolicies([Landroid/net/NetworkPolicy;)V
    .registers 9
    .parameter "policies"

    .prologue
    .line 1436
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v5, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v6, "NetworkPolicy"

    invoke-virtual {v4, v5, v6}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1438
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->maybeRefreshTrustedTime()V

    .line 1439
    iget-object v5, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v5

    .line 1440
    :try_start_f
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->clear()V

    .line 1441
    move-object v0, p1

    .local v0, arr$:[Landroid/net/NetworkPolicy;
    array-length v2, v0

    .local v2, len$:I
    const/4 v1, 0x0

    .local v1, i$:I
    :goto_17
    if-ge v1, v2, :cond_25

    aget-object v3, v0, v1

    .line 1442
    .local v3, policy:Landroid/net/NetworkPolicy;
    iget-object v4, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkPolicy:Ljava/util/HashMap;

    iget-object v6, v3, Landroid/net/NetworkPolicy;->template:Landroid/net/NetworkTemplate;

    invoke-virtual {v4, v6, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1441
    add-int/lit8 v1, v1, 0x1

    goto :goto_17

    .line 1445
    .end local v3           #policy:Landroid/net/NetworkPolicy;
    :cond_25
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V

    .line 1446
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V

    .line 1447
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 1448
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1449
    monitor-exit v5

    .line 1450
    return-void

    .line 1449
    .end local v0           #arr$:[Landroid/net/NetworkPolicy;
    .end local v1           #i$:I
    .end local v2           #len$:I
    :catchall_33
    move-exception v4

    monitor-exit v5
    :try_end_35
    .catchall {:try_start_f .. :try_end_35} :catchall_33

    throw v4
.end method

.method public setRestrictBackground(Z)V
    .registers 6
    .parameter "restrictBackground"

    .prologue
    const/4 v1, 0x0

    .line 1513
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v3, "NetworkPolicy"

    invoke-virtual {v0, v2, v3}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1515
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->maybeRefreshTrustedTime()V

    .line 1516
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v2

    .line 1517
    :try_start_10
    iput-boolean p1, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    .line 1518
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForRestrictBackgroundLocked()V

    .line 1519
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 1520
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->writePolicyLocked()V

    .line 1521
    monitor-exit v2
    :try_end_1c
    .catchall {:try_start_10 .. :try_end_1c} :catchall_2a

    .line 1523
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    const/4 v3, 0x6

    if-eqz p1, :cond_2d

    const/4 v0, 0x1

    :goto_22
    invoke-virtual {v2, v3, v0, v1}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 1525
    return-void

    .line 1521
    :catchall_2a
    move-exception v0

    :try_start_2b
    monitor-exit v2
    :try_end_2c
    .catchall {:try_start_2b .. :try_end_2c} :catchall_2a

    throw v0

    :cond_2d
    move v0, v1

    .line 1523
    goto :goto_22
.end method

.method public snoozeLimit(Landroid/net/NetworkTemplate;)V
    .registers 7
    .parameter "template"

    .prologue
    .line 1473
    iget-object v2, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.MANAGE_NETWORK_POLICY"

    const-string v4, "NetworkPolicy"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1475
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v0

    .line 1477
    .local v0, token:J
    const/4 v2, 0x2

    :try_start_e
    invoke-direct {p0, p1, v2}, Lcom/android/server/net/NetworkPolicyManagerService;->performSnooze(Landroid/net/NetworkTemplate;I)V
    :try_end_11
    .catchall {:try_start_e .. :try_end_11} :catchall_15

    .line 1479
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1481
    return-void

    .line 1479
    :catchall_15
    move-exception v2

    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v2
.end method

.method public systemReady()V
    .registers 13

    .prologue
    .line 376
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->isBandwidthControlEnabled()Z

    move-result v8

    if-nez v8, :cond_e

    .line 377
    const-string v8, "NetworkPolicy"

    const-string v9, "bandwidth controls disabled, unable to enforce policy"

    invoke-static {v8, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 443
    :goto_d
    return-void

    .line 381
    :cond_e
    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;

    monitor-enter v9

    .line 383
    :try_start_11
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->readPolicyLocked()V

    .line 385
    iget-boolean v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mRestrictBackground:Z

    if-eqz v8, :cond_1e

    .line 386
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateRulesForRestrictBackgroundLocked()V

    .line 387
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V

    .line 389
    :cond_1e
    monitor-exit v9
    :try_end_1f
    .catchall {:try_start_11 .. :try_end_1f} :catchall_cd

    .line 391
    invoke-direct {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->updateScreenOn()V

    .line 394
    :try_start_22
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mActivityManager:Landroid/app/IActivityManager;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mProcessObserver:Landroid/app/IProcessObserver;

    invoke-interface {v8, v9}, Landroid/app/IActivityManager;->registerProcessObserver(Landroid/app/IProcessObserver;)V

    .line 395
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mNetworkManager:Landroid/os/INetworkManagementService;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAlertObserver:Landroid/net/INetworkManagementEventObserver;

    invoke-interface {v8, v9}, Landroid/os/INetworkManagementService;->registerObserver(Landroid/net/INetworkManagementEventObserver;)V
    :try_end_30
    .catch Landroid/os/RemoteException; {:try_start_22 .. :try_end_30} :catch_d0

    .line 403
    :goto_30
    new-instance v3, Landroid/content/IntentFilter;

    invoke-direct {v3}, Landroid/content/IntentFilter;-><init>()V

    .line 404
    .local v3, screenFilter:Landroid/content/IntentFilter;
    const-string v8, "android.intent.action.SCREEN_ON"

    invoke-virtual {v3, v8}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 405
    const-string v8, "android.intent.action.SCREEN_OFF"

    invoke-virtual {v3, v8}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 406
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mScreenReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v8, v9, v3}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 409
    new-instance v1, Landroid/content/IntentFilter;

    const-string v8, "android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE"

    invoke-direct {v1, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 410
    .local v1, connFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mConnReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.CONNECTIVITY_INTERNAL"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v1, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 413
    new-instance v2, Landroid/content/IntentFilter;

    invoke-direct {v2}, Landroid/content/IntentFilter;-><init>()V

    .line 414
    .local v2, packageFilter:Landroid/content/IntentFilter;
    const-string v8, "android.intent.action.PACKAGE_ADDED"

    invoke-virtual {v2, v8}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 415
    const-string v8, "android.intent.action.UID_REMOVED"

    invoke-virtual {v2, v8}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 416
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mPackageReceiver:Landroid/content/BroadcastReceiver;

    const/4 v10, 0x0

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v2, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 419
    new-instance v5, Landroid/content/IntentFilter;

    const-string v8, "com.android.server.action.NETWORK_STATS_UPDATED"

    invoke-direct {v5, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 420
    .local v5, statsFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mStatsReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.READ_NETWORK_USAGE_HISTORY"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v5, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 424
    new-instance v0, Landroid/content/IntentFilter;

    const-string v8, "com.android.server.net.action.ALLOW_BACKGROUND"

    invoke-direct {v0, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 425
    .local v0, allowFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mAllowReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.MANAGE_NETWORK_POLICY"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v0, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 428
    new-instance v4, Landroid/content/IntentFilter;

    const-string v8, "com.android.server.net.action.SNOOZE_WARNING"

    invoke-direct {v4, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 429
    .local v4, snoozeWarningFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mSnoozeWarningReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.MANAGE_NETWORK_POLICY"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v4, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 433
    new-instance v6, Landroid/content/IntentFilter;

    const-string v8, "android.net.wifi.CONFIGURED_NETWORKS_CHANGE"

    invoke-direct {v6, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 434
    .local v6, wifiConfigFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mWifiConfigReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.CONNECTIVITY_INTERNAL"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v6, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 438
    new-instance v7, Landroid/content/IntentFilter;

    const-string v8, "android.net.wifi.STATE_CHANGE"

    invoke-direct {v7, v8}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 440
    .local v7, wifiStateFilter:Landroid/content/IntentFilter;
    iget-object v8, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    iget-object v9, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mWifiStateReceiver:Landroid/content/BroadcastReceiver;

    const-string v10, "android.permission.CONNECTIVITY_INTERNAL"

    iget-object v11, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mHandler:Landroid/os/Handler;

    invoke-virtual {v8, v9, v7, v10, v11}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    goto/16 :goto_d

    .line 389
    .end local v0           #allowFilter:Landroid/content/IntentFilter;
    .end local v1           #connFilter:Landroid/content/IntentFilter;
    .end local v2           #packageFilter:Landroid/content/IntentFilter;
    .end local v3           #screenFilter:Landroid/content/IntentFilter;
    .end local v4           #snoozeWarningFilter:Landroid/content/IntentFilter;
    .end local v5           #statsFilter:Landroid/content/IntentFilter;
    .end local v6           #wifiConfigFilter:Landroid/content/IntentFilter;
    .end local v7           #wifiStateFilter:Landroid/content/IntentFilter;
    :catchall_cd
    move-exception v8

    :try_start_ce
    monitor-exit v9
    :try_end_cf
    .catchall {:try_start_ce .. :try_end_cf} :catchall_cd

    throw v8

    .line 396
    :catch_d0
    move-exception v8

    goto/16 :goto_30
.end method

.method public unregisterListener(Landroid/net/INetworkPolicyListener;)V
    .registers 5
    .parameter "listener"

    .prologue
    .line 1429
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.CONNECTIVITY_INTERNAL"

    const-string v2, "NetworkPolicy"

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingOrSelfPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 1431
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService;->mListeners:Landroid/os/RemoteCallbackList;

    invoke-virtual {v0, p1}, Landroid/os/RemoteCallbackList;->unregister(Landroid/os/IInterface;)Z

    .line 1432
    return-void
.end method
