.class public Lcom/android/server/NotificationManagerService;
.super Landroid/app/INotificationManager$Stub;
.source "NotificationManagerService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/NotificationManagerService$WorkerHandler;,
        Lcom/android/server/NotificationManagerService$SettingsObserver;,
        Lcom/android/server/NotificationManagerService$ToastRecord;,
        Lcom/android/server/NotificationManagerService$NotificationRecord;,
        Lcom/android/server/NotificationManagerService$Injector;
    }
.end annotation


# static fields
.field private static final ATTR_NAME:Ljava/lang/String; = "name"

.field private static final ATTR_VERSION:Ljava/lang/String; = "version"

.field private static final DBG:Z = false

.field private static final DB_VERSION:I = 0x1

.field private static final DEFAULT_STREAM_TYPE:I = 0x5

.field private static final DEFAULT_VIBRATE_PATTERN:[J = null

.field private static final ENABLE_BLOCKED_NOTIFICATIONS:Z = true

.field private static final ENABLE_BLOCKED_TOASTS:Z = true

.field private static final JUNK_SCORE:I = -0x3e8

.field private static final LONG_DELAY:I = 0xdac

.field private static final MAX_PACKAGE_NOTIFICATIONS:I = 0x32

.field private static final MESSAGE_TIMEOUT:I = 0x2

.field private static final NOTIFICATION_PRIORITY_MULTIPLIER:I = 0xa

.field private static final SCORE_DISPLAY_THRESHOLD:I = -0x14

.field private static final SCORE_ONGOING_HIGHER:Z = false

.field private static final SHORT_DELAY:I = 0x7d0

.field private static final TAG:Ljava/lang/String; = "NotificationService"

.field private static final TAG_BLOCKED_PKGS:Ljava/lang/String; = "blocked-packages"

.field private static final TAG_BODY:Ljava/lang/String; = "notification-policy"

.field private static final TAG_PACKAGE:Ljava/lang/String; = "package"


# instance fields
.field final mAm:Landroid/app/IActivityManager;

.field private mAttentionLight:Lcom/android/server/LightsService$Light;

.field private mAudioService:Landroid/media/IAudioService;

.field private mBlockedPackages:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field final mContext:Landroid/content/Context;

.field private mDefaultNotificationColor:I

.field private mDefaultNotificationLedOff:I

.field private mDefaultNotificationLedOn:I

.field private mDisabledNotifications:I

.field final mForegroundToken:Landroid/os/IBinder;

.field private mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

.field private mInCall:Z

.field private mIntentReceiver:Landroid/content/BroadcastReceiver;

.field private mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

.field private mLights:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/NotificationManagerService$NotificationRecord;",
            ">;"
        }
    .end annotation
.end field

.field private mNotificationCallbacks:Lcom/android/server/StatusBarManagerService$NotificationCallbacks;

.field private mNotificationLight:Lcom/android/server/LightsService$Light;

.field private final mNotificationList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/NotificationManagerService$NotificationRecord;",
            ">;"
        }
    .end annotation
.end field

.field private mNotificationPulseEnabled:Z

.field private mPolicyFile:Lcom/android/internal/os/AtomicFile;

.field private mScreenOn:Z

.field private mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

.field private mStatusBar:Lcom/android/server/StatusBarManagerService;

.field private mSystemReady:Z

.field private mToastQueue:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/NotificationManagerService$ToastRecord;",
            ">;"
        }
    .end annotation
.end field

.field private mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

.field private mVibrator:Landroid/os/Vibrator;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 132
    const/4 v0, 0x4

    new-array v0, v0, [J

    fill-array-data v0, :array_a

    sput-object v0, Lcom/android/server/NotificationManagerService;->DEFAULT_VIBRATE_PATTERN:[J

    return-void

    nop

    :array_a
    .array-data 0x8
        0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0xfat 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0xfat 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0xfat 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
    .end array-data
.end method

.method constructor <init>(Landroid/content/Context;Lcom/android/server/StatusBarManagerService;Lcom/android/server/LightsService;)V
    .registers 12
    .parameter "context"
    .parameter "statusBar"
    .parameter "lights"

    .prologue
    const/4 v7, 0x0

    .line 613
    invoke-direct {p0}, Landroid/app/INotificationManager$Stub;-><init>()V

    .line 146
    new-instance v5, Landroid/os/Binder;

    invoke-direct {v5}, Landroid/os/Binder;-><init>()V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mForegroundToken:Landroid/os/IBinder;

    .line 167
    const/4 v5, 0x1

    iput-boolean v5, p0, Lcom/android/server/NotificationManagerService;->mScreenOn:Z

    .line 168
    iput-boolean v7, p0, Lcom/android/server/NotificationManagerService;->mInCall:Z

    .line 171
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    .line 176
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    .line 181
    new-instance v5, Ljava/util/HashSet;

    invoke-direct {v5}, Ljava/util/HashSet;-><init>()V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    .line 431
    new-instance v5, Lcom/android/server/NotificationManagerService$1;

    invoke-direct {v5, p0}, Lcom/android/server/NotificationManagerService$1;-><init>(Lcom/android/server/NotificationManagerService;)V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mNotificationCallbacks:Lcom/android/server/StatusBarManagerService$NotificationCallbacks;

    .line 523
    new-instance v5, Lcom/android/server/NotificationManagerService$2;

    invoke-direct {v5, p0}, Lcom/android/server/NotificationManagerService$2;-><init>(Lcom/android/server/NotificationManagerService;)V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    .line 614
    iput-object p1, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    .line 615
    const-string v5, "vibrator"

    invoke-virtual {p1, v5}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/os/Vibrator;

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mVibrator:Landroid/os/Vibrator;

    .line 616
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v5

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mAm:Landroid/app/IActivityManager;

    .line 617
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    .line 618
    new-instance v5, Lcom/android/server/NotificationManagerService$WorkerHandler;

    const/4 v6, 0x0

    invoke-direct {v5, p0, v6}, Lcom/android/server/NotificationManagerService$WorkerHandler;-><init>(Lcom/android/server/NotificationManagerService;Lcom/android/server/NotificationManagerService$1;)V

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

    .line 620
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->loadBlockDb()V

    .line 622
    iput-object p2, p0, Lcom/android/server/NotificationManagerService;->mStatusBar:Lcom/android/server/StatusBarManagerService;

    .line 623
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mNotificationCallbacks:Lcom/android/server/StatusBarManagerService$NotificationCallbacks;

    invoke-virtual {p2, v5}, Lcom/android/server/StatusBarManagerService;->setNotificationCallbacks(Lcom/android/server/StatusBarManagerService$NotificationCallbacks;)V

    .line 625
    const/4 v5, 0x4

    invoke-virtual {p3, v5}, Lcom/android/server/LightsService;->getLight(I)Lcom/android/server/LightsService$Light;

    move-result-object v5

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mNotificationLight:Lcom/android/server/LightsService$Light;

    .line 626
    const/4 v5, 0x5

    invoke-virtual {p3, v5}, Lcom/android/server/LightsService;->getLight(I)Lcom/android/server/LightsService$Light;

    move-result-object v5

    iput-object v5, p0, Lcom/android/server/NotificationManagerService;->mAttentionLight:Lcom/android/server/LightsService$Light;

    .line 628
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    .line 629
    .local v3, resources:Landroid/content/res/Resources;
    const v5, 0x1060063

    invoke-virtual {v3, v5}, Landroid/content/res/Resources;->getColor(I)I

    move-result v5

    iput v5, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationColor:I

    .line 631
    const v5, 0x10e001a

    invoke-virtual {v3, v5}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v5

    iput v5, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationLedOn:I

    .line 633
    const v5, 0x10e001b

    invoke-virtual {v3, v5}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v5

    iput v5, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationLedOff:I

    .line 640
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v5

    const-string v6, "device_provisioned"

    invoke-static {v5, v6, v7}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v5

    if-nez v5, :cond_9f

    .line 642
    const/high16 v5, 0x4

    iput v5, p0, Lcom/android/server/NotificationManagerService;->mDisabledNotifications:I

    .line 646
    :cond_9f
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 647
    .local v0, filter:Landroid/content/IntentFilter;
    const-string v5, "android.intent.action.SCREEN_ON"

    invoke-virtual {v0, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 648
    const-string v5, "android.intent.action.SCREEN_OFF"

    invoke-virtual {v0, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 649
    const-string v5, "android.intent.action.PHONE_STATE"

    invoke-virtual {v0, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 650
    const-string v5, "android.intent.action.USER_PRESENT"

    invoke-virtual {v0, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 651
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v5, v6, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 652
    new-instance v2, Landroid/content/IntentFilter;

    invoke-direct {v2}, Landroid/content/IntentFilter;-><init>()V

    .line 653
    .local v2, pkgFilter:Landroid/content/IntentFilter;
    const-string v5, "android.intent.action.PACKAGE_REMOVED"

    invoke-virtual {v2, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 654
    const-string v5, "android.intent.action.PACKAGE_CHANGED"

    invoke-virtual {v2, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 655
    const-string v5, "android.intent.action.PACKAGE_RESTARTED"

    invoke-virtual {v2, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 656
    const-string v5, "android.intent.action.QUERY_PACKAGE_RESTART"

    invoke-virtual {v2, v5}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 657
    const-string v5, "package"

    invoke-virtual {v2, v5}, Landroid/content/IntentFilter;->addDataScheme(Ljava/lang/String;)V

    .line 658
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v5, v6, v2}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 659
    new-instance v4, Landroid/content/IntentFilter;

    const-string v5, "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"

    invoke-direct {v4, v5}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 660
    .local v4, sdFilter:Landroid/content/IntentFilter;
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v5, v6, v4}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 662
    new-instance v1, Lcom/android/server/NotificationManagerService$SettingsObserver;

    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

    invoke-direct {v1, p0, v5}, Lcom/android/server/NotificationManagerService$SettingsObserver;-><init>(Lcom/android/server/NotificationManagerService;Landroid/os/Handler;)V

    .line 663
    .local v1, observer:Lcom/android/server/NotificationManagerService$SettingsObserver;
    invoke-virtual {v1}, Lcom/android/server/NotificationManagerService$SettingsObserver;->observe()V

    .line 664
    return-void
.end method

.method static synthetic access$000(Landroid/content/Context;Ljava/lang/String;I)Ljava/lang/String;
    .registers 4
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 92
    invoke-static {p0, p1, p2}, Lcom/android/server/NotificationManagerService;->idDebugString(Landroid/content/Context;Ljava/lang/String;I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$100(Lcom/android/server/NotificationManagerService;)Ljava/util/ArrayList;
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/server/NotificationManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 92
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    return-void
.end method

.method static synthetic access$1102(Lcom/android/server/NotificationManagerService;Z)Z
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-boolean p1, p0, Lcom/android/server/NotificationManagerService;->mScreenOn:Z

    return p1
.end method

.method static synthetic access$1202(Lcom/android/server/NotificationManagerService;Z)Z
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-boolean p1, p0, Lcom/android/server/NotificationManagerService;->mInCall:Z

    return p1
.end method

.method static synthetic access$1300(Lcom/android/server/NotificationManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 92
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateNotificationPulse()V

    return-void
.end method

.method static synthetic access$1400(Lcom/android/server/NotificationManagerService;)Lcom/android/server/LightsService$Light;
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mNotificationLight:Lcom/android/server/LightsService$Light;

    return-object v0
.end method

.method static synthetic access$1500(Lcom/android/server/NotificationManagerService;)Z
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-boolean v0, p0, Lcom/android/server/NotificationManagerService;->mNotificationPulseEnabled:Z

    return v0
.end method

.method static synthetic access$1502(Lcom/android/server/NotificationManagerService;Z)Z
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-boolean p1, p0, Lcom/android/server/NotificationManagerService;->mNotificationPulseEnabled:Z

    return p1
.end method

.method static synthetic access$1700(Lcom/android/server/NotificationManagerService;Lcom/android/server/NotificationManagerService$ToastRecord;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    invoke-direct {p0, p1}, Lcom/android/server/NotificationManagerService;->handleTimeout(Lcom/android/server/NotificationManagerService$ToastRecord;)V

    return-void
.end method

.method static synthetic access$200(Lcom/android/server/NotificationManagerService;)I
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget v0, p0, Lcom/android/server/NotificationManagerService;->mDisabledNotifications:I

    return v0
.end method

.method static synthetic access$202(Lcom/android/server/NotificationManagerService;I)I
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput p1, p0, Lcom/android/server/NotificationManagerService;->mDisabledNotifications:I

    return p1
.end method

.method static synthetic access$300(Lcom/android/server/NotificationManagerService;)Landroid/media/IAudioService;
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mAudioService:Landroid/media/IAudioService;

    return-object v0
.end method

.method static synthetic access$400(Lcom/android/server/NotificationManagerService;)Landroid/os/Vibrator;
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mVibrator:Landroid/os/Vibrator;

    return-object v0
.end method

.method static synthetic access$500(Lcom/android/server/NotificationManagerService;Ljava/lang/String;Ljava/lang/String;IIIZ)V
    .registers 7
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"
    .parameter "x3"
    .parameter "x4"
    .parameter "x5"
    .parameter "x6"

    .prologue
    .line 92
    invoke-direct/range {p0 .. p6}, Lcom/android/server/NotificationManagerService;->cancelNotification(Ljava/lang/String;Ljava/lang/String;IIIZ)V

    return-void
.end method

.method static synthetic access$602(Lcom/android/server/NotificationManagerService;Lcom/android/server/NotificationManagerService$NotificationRecord;)Lcom/android/server/NotificationManagerService$NotificationRecord;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-object p1, p0, Lcom/android/server/NotificationManagerService;->mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    return-object p1
.end method

.method static synthetic access$702(Lcom/android/server/NotificationManagerService;Lcom/android/server/NotificationManagerService$NotificationRecord;)Lcom/android/server/NotificationManagerService$NotificationRecord;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-object p1, p0, Lcom/android/server/NotificationManagerService;->mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    return-object p1
.end method

.method static synthetic access$800(Lcom/android/server/NotificationManagerService;)Ljava/util/ArrayList;
    .registers 2
    .parameter "x0"

    .prologue
    .line 92
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$902(Lcom/android/server/NotificationManagerService;Lcom/android/server/NotificationManagerService$NotificationRecord;)Lcom/android/server/NotificationManagerService$NotificationRecord;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 92
    iput-object p1, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    return-object p1
.end method

.method private areNotificationsEnabledForPackageInt(Ljava/lang/String;)Z
    .registers 4
    .parameter "pkg"

    .prologue
    .line 282
    iget-object v1, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    invoke-virtual {v1, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_a

    const/4 v0, 0x1

    .line 286
    .local v0, enabled:Z
    :goto_9
    return v0

    .line 282
    .end local v0           #enabled:Z
    :cond_a
    const/4 v0, 0x0

    goto :goto_9
.end method

.method private cancelNotification(Ljava/lang/String;Ljava/lang/String;IIIZ)V
    .registers 13
    .parameter "pkg"
    .parameter "tag"
    .parameter "id"
    .parameter "mustHaveFlags"
    .parameter "mustNotHaveFlags"
    .parameter "sendDelete"

    .prologue
    .line 1209
    const/16 v2, 0xabf

    const/4 v3, 0x5

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    aput-object p1, v3, v4

    const/4 v4, 0x1

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    const/4 v4, 0x2

    aput-object p2, v3, v4

    const/4 v4, 0x3

    invoke-static {p4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    const/4 v4, 0x4

    invoke-static {p5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    invoke-static {v2, v3}, Landroid/util/EventLog;->writeEvent(I[Ljava/lang/Object;)I

    .line 1212
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v3

    .line 1213
    :try_start_26
    invoke-direct {p0, p1, p2, p3}, Lcom/android/server/NotificationManagerService;->indexOfNotificationLocked(Ljava/lang/String;Ljava/lang/String;I)I

    move-result v0

    .line 1214
    .local v0, index:I
    if-ltz v0, :cond_54

    .line 1215
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1217
    .local v1, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    iget-object v2, v1, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v2, v2, Landroid/app/Notification;->flags:I

    and-int/2addr v2, p4

    if-eq v2, p4, :cond_3d

    .line 1218
    monitor-exit v3

    .line 1230
    .end local v1           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :goto_3c
    return-void

    .line 1220
    .restart local v1       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_3d
    iget-object v2, v1, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v2, v2, Landroid/app/Notification;->flags:I

    and-int/2addr v2, p5

    if-eqz v2, :cond_49

    .line 1221
    monitor-exit v3

    goto :goto_3c

    .line 1229
    .end local v0           #index:I
    .end local v1           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :catchall_46
    move-exception v2

    monitor-exit v3
    :try_end_48
    .catchall {:try_start_26 .. :try_end_48} :catchall_46

    throw v2

    .line 1224
    .restart local v0       #index:I
    .restart local v1       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_49
    :try_start_49
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 1226
    invoke-direct {p0, v1, p6}, Lcom/android/server/NotificationManagerService;->cancelNotificationLocked(Lcom/android/server/NotificationManagerService$NotificationRecord;Z)V

    .line 1227
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    .line 1229
    .end local v1           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_54
    monitor-exit v3
    :try_end_55
    .catchall {:try_start_49 .. :try_end_55} :catchall_46

    goto :goto_3c
.end method

.method private cancelNotificationLocked(Lcom/android/server/NotificationManagerService$NotificationRecord;Z)V
    .registers 11
    .parameter "r"
    .parameter "sendDelete"

    .prologue
    const/4 v7, 0x0

    .line 1145
    if-eqz p2, :cond_10

    .line 1146
    iget-object v4, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget-object v4, v4, Landroid/app/Notification;->deleteIntent:Landroid/app/PendingIntent;

    if-eqz v4, :cond_10

    .line 1148
    :try_start_9
    iget-object v4, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget-object v4, v4, Landroid/app/Notification;->deleteIntent:Landroid/app/PendingIntent;

    invoke-virtual {v4}, Landroid/app/PendingIntent;->send()V
    :try_end_10
    .catch Landroid/app/PendingIntent$CanceledException; {:try_start_9 .. :try_end_10} :catch_5c

    .line 1158
    :cond_10
    :goto_10
    iget-object v4, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v4, v4, Landroid/app/Notification;->icon:I

    if-eqz v4, :cond_26

    .line 1159
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 1161
    .local v1, identity:J
    :try_start_1a
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mStatusBar:Lcom/android/server/StatusBarManagerService;

    iget-object v5, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    invoke-virtual {v4, v5}, Lcom/android/server/StatusBarManagerService;->removeNotification(Landroid/os/IBinder;)V
    :try_end_21
    .catchall {:try_start_1a .. :try_end_21} :catchall_78

    .line 1164
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1166
    iput-object v7, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    .line 1170
    .end local v1           #identity:J
    :cond_26
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    if-ne v4, p1, :cond_3e

    .line 1171
    iput-object v7, p0, Lcom/android/server/NotificationManagerService;->mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1172
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 1174
    .restart local v1       #identity:J
    :try_start_30
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mAudioService:Landroid/media/IAudioService;

    invoke-interface {v4}, Landroid/media/IAudioService;->getRingtonePlayer()Landroid/media/IRingtonePlayer;

    move-result-object v3

    .line 1175
    .local v3, player:Landroid/media/IRingtonePlayer;
    if-eqz v3, :cond_3b

    .line 1176
    invoke-interface {v3}, Landroid/media/IRingtonePlayer;->stopAsync()V
    :try_end_3b
    .catchall {:try_start_30 .. :try_end_3b} :catchall_7d
    .catch Landroid/os/RemoteException; {:try_start_30 .. :try_end_3b} :catch_87

    .line 1180
    .end local v3           #player:Landroid/media/IRingtonePlayer;
    :cond_3b
    :goto_3b
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1185
    .end local v1           #identity:J
    :cond_3e
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    if-ne v4, p1, :cond_50

    .line 1186
    iput-object v7, p0, Lcom/android/server/NotificationManagerService;->mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1187
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 1189
    .restart local v1       #identity:J
    :try_start_48
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mVibrator:Landroid/os/Vibrator;

    invoke-virtual {v4}, Landroid/os/Vibrator;->cancel()V
    :try_end_4d
    .catchall {:try_start_48 .. :try_end_4d} :catchall_82

    .line 1192
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1197
    .end local v1           #identity:J
    :cond_50
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    invoke-virtual {v4, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 1198
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    if-ne v4, p1, :cond_5b

    .line 1199
    iput-object v7, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1201
    :cond_5b
    return-void

    .line 1149
    :catch_5c
    move-exception v0

    .line 1152
    .local v0, ex:Landroid/app/PendingIntent$CanceledException;
    const-string v4, "NotificationService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "canceled PendingIntent for "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p1, Lcom/android/server/NotificationManagerService$NotificationRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5, v0}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_10

    .line 1164
    .end local v0           #ex:Landroid/app/PendingIntent$CanceledException;
    .restart local v1       #identity:J
    :catchall_78
    move-exception v4

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v4

    .line 1180
    :catchall_7d
    move-exception v4

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v4

    .line 1192
    :catchall_82
    move-exception v4

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v4

    .line 1178
    :catch_87
    move-exception v4

    goto :goto_3b
.end method

.method private cancelToastLocked(I)V
    .registers 7
    .parameter "index"

    .prologue
    .line 790
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 792
    .local v1, record:Lcom/android/server/NotificationManagerService$ToastRecord;
    :try_start_8
    iget-object v2, v1, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-interface {v2}, Landroid/app/ITransientNotification;->hide()V
    :try_end_d
    .catch Landroid/os/RemoteException; {:try_start_8 .. :try_end_d} :catch_23

    .line 799
    :goto_d
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 800
    iget v2, v1, Lcom/android/server/NotificationManagerService$ToastRecord;->pid:I

    invoke-direct {p0, v2}, Lcom/android/server/NotificationManagerService;->keepProcessAliveLocked(I)V

    .line 801
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    if-lez v2, :cond_22

    .line 805
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->showNextToastLocked()V

    .line 807
    :cond_22
    return-void

    .line 793
    :catch_23
    move-exception v0

    .line 794
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "NotificationService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Object died trying to hide notification "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, v1, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " in package "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, v1, Lcom/android/server/NotificationManagerService$ToastRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_d
.end method

.method private static final clamp(III)I
    .registers 3
    .parameter "x"
    .parameter "low"
    .parameter "high"

    .prologue
    .line 893
    if-ge p0, p1, :cond_3

    .end local p1
    :goto_2
    return p1

    .restart local p1
    :cond_3
    if-le p0, p2, :cond_7

    move p1, p2

    goto :goto_2

    :cond_7
    move p1, p0

    goto :goto_2
.end method

.method private handleTimeout(Lcom/android/server/NotificationManagerService$ToastRecord;)V
    .registers 6
    .parameter "record"

    .prologue
    .line 820
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    monitor-enter v2

    .line 821
    :try_start_3
    iget-object v1, p1, Lcom/android/server/NotificationManagerService$ToastRecord;->pkg:Ljava/lang/String;

    iget-object v3, p1, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-direct {p0, v1, v3}, Lcom/android/server/NotificationManagerService;->indexOfToastLocked(Ljava/lang/String;Landroid/app/ITransientNotification;)I

    move-result v0

    .line 822
    .local v0, index:I
    if-ltz v0, :cond_10

    .line 823
    invoke-direct {p0, v0}, Lcom/android/server/NotificationManagerService;->cancelToastLocked(I)V

    .line 825
    :cond_10
    monitor-exit v2

    .line 826
    return-void

    .line 825
    .end local v0           #index:I
    :catchall_12
    move-exception v1

    monitor-exit v2
    :try_end_14
    .catchall {:try_start_3 .. :try_end_14} :catchall_12

    throw v1
.end method

.method private static idDebugString(Landroid/content/Context;Ljava/lang/String;I)Ljava/lang/String;
    .registers 7
    .parameter "baseContext"
    .parameter "packageName"
    .parameter "id"

    .prologue
    .line 318
    const/4 v0, 0x0

    .line 320
    .local v0, c:Landroid/content/Context;
    if-eqz p1, :cond_14

    .line 322
    const/4 v3, 0x0

    :try_start_4
    invoke-virtual {p0, p1, v3}, Landroid/content/Context;->createPackageContext(Ljava/lang/String;I)Landroid/content/Context;
    :try_end_7
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_4 .. :try_end_7} :catch_11

    move-result-object v0

    .line 334
    :goto_8
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 336
    .local v2, r:Landroid/content/res/Resources;
    :try_start_c
    invoke-virtual {v2, p2}, Landroid/content/res/Resources;->getResourceName(I)Ljava/lang/String;
    :try_end_f
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_c .. :try_end_f} :catch_16

    move-result-object v3

    .line 338
    :goto_10
    return-object v3

    .line 323
    .end local v2           #r:Landroid/content/res/Resources;
    :catch_11
    move-exception v1

    .line 324
    .local v1, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    move-object v0, p0

    .line 325
    goto :goto_8

    .line 327
    .end local v1           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    :cond_14
    move-object v0, p0

    goto :goto_8

    .line 337
    .restart local v2       #r:Landroid/content/res/Resources;
    :catch_16
    move-exception v1

    .line 338
    .local v1, e:Landroid/content/res/Resources$NotFoundException;
    const-string v3, "<name unknown>"

    goto :goto_10
.end method

.method private indexOfNotificationLocked(Ljava/lang/String;Ljava/lang/String;I)I
    .registers 9
    .parameter "pkg"
    .parameter "tag"
    .parameter "id"

    .prologue
    .line 1373
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    .line 1374
    .local v2, list:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/NotificationManagerService$NotificationRecord;>;"
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v1

    .line 1375
    .local v1, len:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_7
    if-ge v0, v1, :cond_2d

    .line 1376
    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1377
    .local v3, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    if-nez p2, :cond_18

    .line 1378
    iget-object v4, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->tag:Ljava/lang/String;

    if-eqz v4, :cond_20

    .line 1375
    :cond_15
    add-int/lit8 v0, v0, 0x1

    goto :goto_7

    .line 1382
    :cond_18
    iget-object v4, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->tag:Ljava/lang/String;

    invoke-virtual {p2, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_15

    .line 1386
    :cond_20
    iget v4, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->id:I

    if-ne v4, p3, :cond_15

    iget-object v4, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v4, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_15

    .line 1390
    .end local v0           #i:I
    .end local v3           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :goto_2c
    return v0

    .restart local v0       #i:I
    :cond_2d
    const/4 v0, -0x1

    goto :goto_2c
.end method

.method private indexOfToastLocked(Ljava/lang/String;Landroid/app/ITransientNotification;)I
    .registers 9
    .parameter "pkg"
    .parameter "callback"

    .prologue
    .line 831
    invoke-interface {p2}, Landroid/app/ITransientNotification;->asBinder()Landroid/os/IBinder;

    move-result-object v0

    .line 832
    .local v0, cbak:Landroid/os/IBinder;
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    .line 833
    .local v3, list:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/NotificationManagerService$ToastRecord;>;"
    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v2

    .line 834
    .local v2, len:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_b
    if-ge v1, v2, :cond_27

    .line 835
    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 836
    .local v4, r:Lcom/android/server/NotificationManagerService$ToastRecord;
    iget-object v5, v4, Lcom/android/server/NotificationManagerService$ToastRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v5, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_24

    iget-object v5, v4, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-interface {v5}, Landroid/app/ITransientNotification;->asBinder()Landroid/os/IBinder;

    move-result-object v5

    if-ne v5, v0, :cond_24

    .line 840
    .end local v1           #i:I
    .end local v4           #r:Lcom/android/server/NotificationManagerService$ToastRecord;
    :goto_23
    return v1

    .line 834
    .restart local v1       #i:I
    .restart local v4       #r:Lcom/android/server/NotificationManagerService$ToastRecord;
    :cond_24
    add-int/lit8 v1, v1, 0x1

    goto :goto_b

    .line 840
    .end local v4           #r:Lcom/android/server/NotificationManagerService$ToastRecord;
    :cond_27
    const/4 v1, -0x1

    goto :goto_23
.end method

.method private keepProcessAliveLocked(I)V
    .registers 10
    .parameter "pid"

    .prologue
    .line 846
    const/4 v4, 0x0

    .line 847
    .local v4, toastCount:I
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    .line 848
    .local v2, list:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/NotificationManagerService$ToastRecord;>;"
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 849
    .local v0, N:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_8
    if-ge v1, v0, :cond_19

    .line 850
    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 851
    .local v3, r:Lcom/android/server/NotificationManagerService$ToastRecord;
    iget v5, v3, Lcom/android/server/NotificationManagerService$ToastRecord;->pid:I

    if-ne v5, p1, :cond_16

    .line 852
    add-int/lit8 v4, v4, 0x1

    .line 849
    :cond_16
    add-int/lit8 v1, v1, 0x1

    goto :goto_8

    .line 856
    .end local v3           #r:Lcom/android/server/NotificationManagerService$ToastRecord;
    :cond_19
    :try_start_19
    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mAm:Landroid/app/IActivityManager;

    iget-object v7, p0, Lcom/android/server/NotificationManagerService;->mForegroundToken:Landroid/os/IBinder;

    if-lez v4, :cond_24

    const/4 v5, 0x1

    :goto_20
    invoke-interface {v6, v7, p1, v5}, Landroid/app/IActivityManager;->setProcessForeground(Landroid/os/IBinder;IZ)V
    :try_end_23
    .catch Landroid/os/RemoteException; {:try_start_19 .. :try_end_23} :catch_26

    .line 860
    :goto_23
    return-void

    .line 856
    :cond_24
    const/4 v5, 0x0

    goto :goto_20

    .line 857
    :catch_26
    move-exception v5

    goto :goto_23
.end method

.method private loadBlockDb()V
    .registers 13

    .prologue
    const/4 v11, 0x1

    .line 193
    iget-object v8, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    monitor-enter v8

    .line 194
    :try_start_4
    iget-object v7, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    if-nez v7, :cond_7e

    .line 195
    new-instance v0, Ljava/io/File;

    const-string v7, "/data/system"

    invoke-direct {v0, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 196
    .local v0, dir:Ljava/io/File;
    new-instance v7, Lcom/android/internal/os/AtomicFile;

    new-instance v9, Ljava/io/File;

    const-string v10, "notification_policy.xml"

    invoke-direct {v9, v0, v10}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-direct {v7, v9}, Lcom/android/internal/os/AtomicFile;-><init>(Ljava/io/File;)V

    iput-object v7, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    .line 198
    iget-object v7, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    invoke-virtual {v7}, Ljava/util/HashSet;->clear()V
    :try_end_22
    .catchall {:try_start_4 .. :try_end_22} :catchall_98

    .line 200
    const/4 v2, 0x0

    .line 202
    .local v2, infile:Ljava/io/FileInputStream;
    :try_start_23
    iget-object v7, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v7}, Lcom/android/internal/os/AtomicFile;->openRead()Ljava/io/FileInputStream;

    move-result-object v2

    .line 203
    invoke-static {}, Landroid/util/Xml;->newPullParser()Lorg/xmlpull/v1/XmlPullParser;

    move-result-object v3

    .line 204
    .local v3, parser:Lorg/xmlpull/v1/XmlPullParser;
    const/4 v7, 0x0

    invoke-interface {v3, v2, v7}, Lorg/xmlpull/v1/XmlPullParser;->setInput(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 208
    const/4 v6, 0x1

    .line 209
    .local v6, version:I
    :cond_32
    :goto_32
    invoke-interface {v3}, Lorg/xmlpull/v1/XmlPullParser;->next()I

    move-result v5

    .local v5, type:I
    if-eq v5, v11, :cond_b8

    .line 210
    invoke-interface {v3}, Lorg/xmlpull/v1/XmlPullParser;->getName()Ljava/lang/String;

    move-result-object v4

    .line 211
    .local v4, tag:Ljava/lang/String;
    const/4 v7, 0x2

    if-ne v5, v7, :cond_32

    .line 212
    const-string v7, "notification-policy"

    invoke-virtual {v7, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_53

    .line 213
    const/4 v7, 0x0

    const-string v9, "version"

    invoke-interface {v3, v7, v9}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v6

    goto :goto_32

    .line 214
    :cond_53
    const-string v7, "blocked-packages"

    invoke-virtual {v7, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_32

    .line 215
    :cond_5b
    :goto_5b
    invoke-interface {v3}, Lorg/xmlpull/v1/XmlPullParser;->next()I

    move-result v5

    if-eq v5, v11, :cond_32

    .line 216
    invoke-interface {v3}, Lorg/xmlpull/v1/XmlPullParser;->getName()Ljava/lang/String;

    move-result-object v4

    .line 217
    const-string v7, "package"

    invoke-virtual {v7, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_80

    .line 218
    iget-object v7, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    const/4 v9, 0x0

    const-string v10, "name"

    invoke-interface {v3, v9, v10}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_79
    .catchall {:try_start_23 .. :try_end_79} :catchall_b3
    .catch Ljava/io/FileNotFoundException; {:try_start_23 .. :try_end_79} :catch_7a
    .catch Ljava/io/IOException; {:try_start_23 .. :try_end_79} :catch_8c
    .catch Ljava/lang/NumberFormatException; {:try_start_23 .. :try_end_79} :catch_9b
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_23 .. :try_end_79} :catch_a7

    goto :goto_5b

    .line 226
    .end local v3           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v4           #tag:Ljava/lang/String;
    .end local v5           #type:I
    .end local v6           #version:I
    :catch_7a
    move-exception v7

    .line 235
    :try_start_7b
    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    .line 238
    .end local v0           #dir:Ljava/io/File;
    .end local v2           #infile:Ljava/io/FileInputStream;
    :cond_7e
    :goto_7e
    monitor-exit v8
    :try_end_7f
    .catchall {:try_start_7b .. :try_end_7f} :catchall_98

    .line 239
    return-void

    .line 219
    .restart local v0       #dir:Ljava/io/File;
    .restart local v2       #infile:Ljava/io/FileInputStream;
    .restart local v3       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v4       #tag:Ljava/lang/String;
    .restart local v5       #type:I
    .restart local v6       #version:I
    :cond_80
    :try_start_80
    const-string v7, "blocked-packages"

    invoke-virtual {v7, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_85
    .catchall {:try_start_80 .. :try_end_85} :catchall_b3
    .catch Ljava/io/FileNotFoundException; {:try_start_80 .. :try_end_85} :catch_7a
    .catch Ljava/io/IOException; {:try_start_80 .. :try_end_85} :catch_8c
    .catch Ljava/lang/NumberFormatException; {:try_start_80 .. :try_end_85} :catch_9b
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_80 .. :try_end_85} :catch_a7

    move-result v7

    if-eqz v7, :cond_5b

    const/4 v7, 0x3

    if-ne v5, v7, :cond_5b

    goto :goto_32

    .line 228
    .end local v3           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v4           #tag:Ljava/lang/String;
    .end local v5           #type:I
    .end local v6           #version:I
    :catch_8c
    move-exception v1

    .line 229
    .local v1, e:Ljava/io/IOException;
    :try_start_8d
    const-string v7, "NotificationService"

    const-string v9, "Unable to read blocked notifications database"

    invoke-static {v7, v9, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_94
    .catchall {:try_start_8d .. :try_end_94} :catchall_b3

    .line 235
    :try_start_94
    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    goto :goto_7e

    .line 238
    .end local v0           #dir:Ljava/io/File;
    .end local v1           #e:Ljava/io/IOException;
    .end local v2           #infile:Ljava/io/FileInputStream;
    :catchall_98
    move-exception v7

    monitor-exit v8
    :try_end_9a
    .catchall {:try_start_94 .. :try_end_9a} :catchall_98

    throw v7

    .line 230
    .restart local v0       #dir:Ljava/io/File;
    .restart local v2       #infile:Ljava/io/FileInputStream;
    :catch_9b
    move-exception v1

    .line 231
    .local v1, e:Ljava/lang/NumberFormatException;
    :try_start_9c
    const-string v7, "NotificationService"

    const-string v9, "Unable to parse blocked notifications database"

    invoke-static {v7, v9, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_a3
    .catchall {:try_start_9c .. :try_end_a3} :catchall_b3

    .line 235
    :try_start_a3
    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V
    :try_end_a6
    .catchall {:try_start_a3 .. :try_end_a6} :catchall_98

    goto :goto_7e

    .line 232
    .end local v1           #e:Ljava/lang/NumberFormatException;
    :catch_a7
    move-exception v1

    .line 233
    .local v1, e:Lorg/xmlpull/v1/XmlPullParserException;
    :try_start_a8
    const-string v7, "NotificationService"

    const-string v9, "Unable to parse blocked notifications database"

    invoke-static {v7, v9, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_af
    .catchall {:try_start_a8 .. :try_end_af} :catchall_b3

    .line 235
    :try_start_af
    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    goto :goto_7e

    .end local v1           #e:Lorg/xmlpull/v1/XmlPullParserException;
    :catchall_b3
    move-exception v7

    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V

    throw v7

    .restart local v3       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v5       #type:I
    .restart local v6       #version:I
    :cond_b8
    invoke-static {v2}, Llibcore/io/IoUtils;->closeQuietly(Ljava/lang/AutoCloseable;)V
    :try_end_bb
    .catchall {:try_start_af .. :try_end_bb} :catchall_98

    goto :goto_7e
.end method

.method private scheduleTimeoutLocked(Lcom/android/server/NotificationManagerService$ToastRecord;Z)V
    .registers 8
    .parameter "r"
    .parameter "immediate"

    .prologue
    .line 811
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

    const/4 v4, 0x2

    invoke-static {v3, v4, p1}, Landroid/os/Message;->obtain(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v2

    .line 812
    .local v2, m:Landroid/os/Message;
    if-eqz p2, :cond_16

    const-wide/16 v0, 0x0

    .line 813
    .local v0, delay:J
    :goto_b
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

    invoke-virtual {v3, p1}, Lcom/android/server/NotificationManagerService$WorkerHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 814
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mHandler:Lcom/android/server/NotificationManagerService$WorkerHandler;

    invoke-virtual {v3, v2, v0, v1}, Lcom/android/server/NotificationManagerService$WorkerHandler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 815
    return-void

    .line 812
    .end local v0           #delay:J
    :cond_16
    iget v3, p1, Lcom/android/server/NotificationManagerService$ToastRecord;->duration:I

    const/4 v4, 0x1

    if-ne v3, v4, :cond_1f

    const/16 v3, 0xdac

    :goto_1d
    int-to-long v0, v3

    goto :goto_b

    :cond_1f
    const/16 v3, 0x7d0

    goto :goto_1d
.end method

.method private sendAccessibilityEvent(Landroid/app/Notification;Ljava/lang/CharSequence;)V
    .registers 7
    .parameter "notification"
    .parameter "packageName"

    .prologue
    .line 1125
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-static {v3}, Landroid/view/accessibility/AccessibilityManager;->getInstance(Landroid/content/Context;)Landroid/view/accessibility/AccessibilityManager;

    move-result-object v1

    .line 1126
    .local v1, manager:Landroid/view/accessibility/AccessibilityManager;
    invoke-virtual {v1}, Landroid/view/accessibility/AccessibilityManager;->isEnabled()Z

    move-result v3

    if-nez v3, :cond_d

    .line 1141
    :goto_c
    return-void

    .line 1130
    :cond_d
    const/16 v3, 0x40

    invoke-static {v3}, Landroid/view/accessibility/AccessibilityEvent;->obtain(I)Landroid/view/accessibility/AccessibilityEvent;

    move-result-object v0

    .line 1132
    .local v0, event:Landroid/view/accessibility/AccessibilityEvent;
    invoke-virtual {v0, p2}, Landroid/view/accessibility/AccessibilityEvent;->setPackageName(Ljava/lang/CharSequence;)V

    .line 1133
    const-class v3, Landroid/app/Notification;

    invoke-virtual {v3}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/view/accessibility/AccessibilityEvent;->setClassName(Ljava/lang/CharSequence;)V

    .line 1134
    invoke-virtual {v0, p1}, Landroid/view/accessibility/AccessibilityEvent;->setParcelableData(Landroid/os/Parcelable;)V

    .line 1135
    iget-object v2, p1, Landroid/app/Notification;->tickerText:Ljava/lang/CharSequence;

    .line 1136
    .local v2, tickerText:Ljava/lang/CharSequence;
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_31

    .line 1137
    invoke-virtual {v0}, Landroid/view/accessibility/AccessibilityEvent;->getText()Ljava/util/List;

    move-result-object v3

    invoke-interface {v3, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 1140
    :cond_31
    invoke-virtual {v1, v0}, Landroid/view/accessibility/AccessibilityManager;->sendAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)V

    goto :goto_c
.end method

.method private showNextToastLocked()V
    .registers 8

    .prologue
    const/4 v6, 0x0

    .line 764
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v3, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 765
    .local v2, record:Lcom/android/server/NotificationManagerService$ToastRecord;
    :goto_9
    if-eqz v2, :cond_14

    .line 768
    :try_start_b
    iget-object v3, v2, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-interface {v3}, Landroid/app/ITransientNotification;->show()V

    .line 769
    const/4 v3, 0x0

    invoke-direct {p0, v2, v3}, Lcom/android/server/NotificationManagerService;->scheduleTimeoutLocked(Lcom/android/server/NotificationManagerService$ToastRecord;Z)V
    :try_end_14
    .catch Landroid/os/RemoteException; {:try_start_b .. :try_end_14} :catch_15

    .line 787
    :cond_14
    return-void

    .line 771
    :catch_15
    move-exception v0

    .line 772
    .local v0, e:Landroid/os/RemoteException;
    const-string v3, "NotificationService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Object died trying to show notification "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, v2, Lcom/android/server/NotificationManagerService$ToastRecord;->callback:Landroid/app/ITransientNotification;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " in package "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, v2, Lcom/android/server/NotificationManagerService$ToastRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 775
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->indexOf(Ljava/lang/Object;)I

    move-result v1

    .line 776
    .local v1, index:I
    if-ltz v1, :cond_49

    .line 777
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 779
    :cond_49
    iget v3, v2, Lcom/android/server/NotificationManagerService$ToastRecord;->pid:I

    invoke-direct {p0, v3}, Lcom/android/server/NotificationManagerService;->keepProcessAliveLocked(I)V

    .line 780
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-lez v3, :cond_5f

    .line 781
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v3, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    .end local v2           #record:Lcom/android/server/NotificationManagerService$ToastRecord;
    check-cast v2, Lcom/android/server/NotificationManagerService$ToastRecord;

    .restart local v2       #record:Lcom/android/server/NotificationManagerService$ToastRecord;
    goto :goto_9

    .line 783
    :cond_5f
    const/4 v2, 0x0

    goto :goto_9
.end method

.method private updateLightsLocked()V
    .registers 7
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 1337
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    if-nez v4, :cond_18

    .line 1339
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v3

    .line 1340
    .local v3, n:I
    if-lez v3, :cond_18

    .line 1341
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    add-int/lit8 v5, v3, -0x1

    invoke-virtual {v4, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/NotificationManagerService$NotificationRecord;

    iput-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1346
    .end local v3           #n:I
    :cond_18
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    if-eqz v4, :cond_24

    iget-boolean v4, p0, Lcom/android/server/NotificationManagerService;->mInCall:Z

    if-nez v4, :cond_24

    iget-boolean v4, p0, Lcom/android/server/NotificationManagerService;->mScreenOn:Z

    if-eqz v4, :cond_2a

    .line 1347
    :cond_24
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mNotificationLight:Lcom/android/server/LightsService$Light;

    invoke-virtual {v4}, Lcom/android/server/LightsService$Light;->turnOff()V

    .line 1368
    :cond_29
    :goto_29
    return-void

    .line 1349
    :cond_2a
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v0, v4, Landroid/app/Notification;->ledARGB:I

    .line 1350
    .local v0, ledARGB:I
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v2, v4, Landroid/app/Notification;->ledOnMS:I

    .line 1351
    .local v2, ledOnMS:I
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v1, v4, Landroid/app/Notification;->ledOffMS:I

    .line 1352
    .local v1, ledOffMS:I
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v4, v4, Landroid/app/Notification;->defaults:I

    and-int/lit8 v4, v4, 0x4

    if-eqz v4, :cond_61

    .line 1353
    iget v0, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationColor:I

    .line 1354
    iget v2, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationLedOn:I

    .line 1355
    iget v1, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationLedOff:I

    .line 1357
    invoke-static {p0}, Lcom/android/server/NotificationManagerService$Injector;->updateNotificationLight(Lcom/android/server/NotificationManagerService;)V

    .line 1358
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v0, v4, Landroid/app/Notification;->ledARGB:I

    .line 1359
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v2, v4, Landroid/app/Notification;->ledOnMS:I

    .line 1360
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    iget-object v4, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v1, v4, Landroid/app/Notification;->ledOffMS:I

    .line 1362
    :cond_61
    iget-boolean v4, p0, Lcom/android/server/NotificationManagerService;->mNotificationPulseEnabled:Z

    if-eqz v4, :cond_29

    .line 1364
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mNotificationLight:Lcom/android/server/LightsService$Light;

    const/4 v5, 0x1

    invoke-virtual {v4, v0, v5, v2, v1}, Lcom/android/server/LightsService$Light;->setFlashing(IIII)V

    goto :goto_29
.end method

.method private updateNotificationPulse()V
    .registers 3

    .prologue
    .line 1394
    iget-object v1, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v1

    .line 1395
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    .line 1396
    monitor-exit v1

    .line 1397
    return-void

    .line 1396
    :catchall_8
    move-exception v0

    monitor-exit v1
    :try_end_a
    .catchall {:try_start_3 .. :try_end_a} :catchall_8

    throw v0
.end method

.method private writeBlockDb()V
    .registers 10

    .prologue
    .line 242
    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    monitor-enter v6

    .line 243
    const/4 v3, 0x0

    .line 245
    .local v3, outfile:Ljava/io/FileOutputStream;
    :try_start_4
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v5}, Lcom/android/internal/os/AtomicFile;->startWrite()Ljava/io/FileOutputStream;

    move-result-object v3

    .line 247
    new-instance v2, Lcom/android/internal/util/FastXmlSerializer;

    invoke-direct {v2}, Lcom/android/internal/util/FastXmlSerializer;-><init>()V

    .line 248
    .local v2, out:Lorg/xmlpull/v1/XmlSerializer;
    const-string v5, "utf-8"

    invoke-interface {v2, v3, v5}, Lorg/xmlpull/v1/XmlSerializer;->setOutput(Ljava/io/OutputStream;Ljava/lang/String;)V

    .line 250
    const/4 v5, 0x0

    const/4 v7, 0x1

    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v7

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->startDocument(Ljava/lang/String;Ljava/lang/Boolean;)V

    .line 252
    const/4 v5, 0x0

    const-string v7, "notification-policy"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 253
    const/4 v5, 0x0

    const-string v7, "version"

    const/4 v8, 0x1

    invoke-static {v8}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v8

    invoke-interface {v2, v5, v7, v8}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 254
    const/4 v5, 0x0

    const-string v7, "blocked-packages"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 256
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    invoke-virtual {v5}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_3a
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_63

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 257
    .local v4, pkg:Ljava/lang/String;
    const/4 v5, 0x0

    const-string v7, "package"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 258
    const/4 v5, 0x0

    const-string v7, "name"

    invoke-interface {v2, v5, v7, v4}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 259
    const/4 v5, 0x0

    const-string v7, "package"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;
    :try_end_58
    .catchall {:try_start_4 .. :try_end_58} :catchall_78
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_58} :catch_59

    goto :goto_3a

    .line 267
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #out:Lorg/xmlpull/v1/XmlSerializer;
    .end local v4           #pkg:Ljava/lang/String;
    :catch_59
    move-exception v0

    .line 268
    .local v0, e:Ljava/io/IOException;
    if-eqz v3, :cond_61

    .line 269
    :try_start_5c
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v5, v3}, Lcom/android/internal/os/AtomicFile;->failWrite(Ljava/io/FileOutputStream;)V

    .line 272
    .end local v0           #e:Ljava/io/IOException;
    :cond_61
    :goto_61
    monitor-exit v6
    :try_end_62
    .catchall {:try_start_5c .. :try_end_62} :catchall_78

    .line 273
    return-void

    .line 261
    .restart local v1       #i$:Ljava/util/Iterator;
    .restart local v2       #out:Lorg/xmlpull/v1/XmlSerializer;
    :cond_63
    const/4 v5, 0x0

    :try_start_64
    const-string v7, "blocked-packages"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 262
    const/4 v5, 0x0

    const-string v7, "notification-policy"

    invoke-interface {v2, v5, v7}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 264
    invoke-interface {v2}, Lorg/xmlpull/v1/XmlSerializer;->endDocument()V

    .line 266
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mPolicyFile:Lcom/android/internal/os/AtomicFile;

    invoke-virtual {v5, v3}, Lcom/android/internal/os/AtomicFile;->finishWrite(Ljava/io/FileOutputStream;)V
    :try_end_77
    .catchall {:try_start_64 .. :try_end_77} :catchall_78
    .catch Ljava/io/IOException; {:try_start_64 .. :try_end_77} :catch_59

    goto :goto_61

    .line 272
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #out:Lorg/xmlpull/v1/XmlSerializer;
    :catchall_78
    move-exception v5

    :try_start_79
    monitor-exit v6
    :try_end_7a
    .catchall {:try_start_79 .. :try_end_7a} :catchall_78

    throw v5
.end method


# virtual methods
.method public areNotificationsEnabledForPackage(Ljava/lang/String;)Z
    .registers 3
    .parameter "pkg"

    .prologue
    .line 276
    invoke-virtual {p0}, Lcom/android/server/NotificationManagerService;->checkCallerIsSystem()V

    .line 277
    invoke-direct {p0, p1}, Lcom/android/server/NotificationManagerService;->areNotificationsEnabledForPackageInt(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method cancelAll()V
    .registers 6

    .prologue
    .line 1316
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v4

    .line 1317
    :try_start_3
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1318
    .local v0, N:I
    add-int/lit8 v1, v0, -0x1

    .local v1, i:I
    :goto_b
    if-ltz v1, :cond_29

    .line 1319
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1321
    .local v2, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    iget-object v3, v2, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v3, v3, Landroid/app/Notification;->flags:I

    and-int/lit8 v3, v3, 0x22

    if-nez v3, :cond_26

    .line 1323
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 1324
    const/4 v3, 0x1

    invoke-direct {p0, v2, v3}, Lcom/android/server/NotificationManagerService;->cancelNotificationLocked(Lcom/android/server/NotificationManagerService$NotificationRecord;Z)V

    .line 1318
    :cond_26
    add-int/lit8 v1, v1, -0x1

    goto :goto_b

    .line 1328
    .end local v2           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_29
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    .line 1329
    monitor-exit v4

    .line 1330
    return-void

    .line 1329
    .end local v0           #N:I
    .end local v1           #i:I
    :catchall_2e
    move-exception v3

    monitor-exit v4
    :try_end_30
    .catchall {:try_start_3 .. :try_end_30} :catchall_2e

    throw v3
.end method

.method public cancelAllNotifications(Ljava/lang/String;)V
    .registers 5
    .parameter "pkg"

    .prologue
    .line 1283
    invoke-virtual {p0, p1}, Lcom/android/server/NotificationManagerService;->checkCallerIsSystemOrSameApp(Ljava/lang/String;)V

    .line 1287
    const/4 v0, 0x0

    const/16 v1, 0x40

    const/4 v2, 0x1

    invoke-virtual {p0, p1, v0, v1, v2}, Lcom/android/server/NotificationManagerService;->cancelAllNotificationsInt(Ljava/lang/String;IIZ)Z

    .line 1288
    return-void
.end method

.method cancelAllNotificationsInt(Ljava/lang/String;IIZ)Z
    .registers 14
    .parameter "pkg"
    .parameter "mustHaveFlags"
    .parameter "mustNotHaveFlags"
    .parameter "doit"

    .prologue
    const/4 v4, 0x1

    const/4 v7, 0x0

    .line 1238
    const/16 v5, 0xac0

    const/4 v6, 0x3

    new-array v6, v6, [Ljava/lang/Object;

    aput-object p1, v6, v7

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v4

    const/4 v7, 0x2

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v6, v7

    invoke-static {v5, v6}, Landroid/util/EventLog;->writeEvent(I[Ljava/lang/Object;)I

    .line 1241
    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v5

    .line 1242
    :try_start_1c
    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1243
    .local v0, N:I
    const/4 v1, 0x0

    .line 1244
    .local v1, canceledSomething:Z
    add-int/lit8 v2, v0, -0x1

    .local v2, i:I
    :goto_25
    if-ltz v2, :cond_5a

    .line 1245
    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v6, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1246
    .local v3, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    iget-object v6, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v6, v6, Landroid/app/Notification;->flags:I

    and-int/2addr v6, p2

    if-eq v6, p2, :cond_39

    .line 1244
    :cond_36
    :goto_36
    add-int/lit8 v2, v2, -0x1

    goto :goto_25

    .line 1249
    :cond_39
    iget-object v6, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v6, v6, Landroid/app/Notification;->flags:I

    and-int/2addr v6, p3

    if-nez v6, :cond_36

    .line 1252
    iget-object v6, v3, Lcom/android/server/NotificationManagerService$NotificationRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v6, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_36

    .line 1255
    const/4 v1, 0x1

    .line 1256
    if-nez p4, :cond_4d

    .line 1257
    monitor-exit v5

    .line 1265
    .end local v3           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :goto_4c
    return v4

    .line 1259
    .restart local v3       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_4d
    iget-object v6, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v6, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 1260
    const/4 v6, 0x0

    invoke-direct {p0, v3, v6}, Lcom/android/server/NotificationManagerService;->cancelNotificationLocked(Lcom/android/server/NotificationManagerService$NotificationRecord;Z)V

    goto :goto_36

    .line 1266
    .end local v0           #N:I
    .end local v1           #canceledSomething:Z
    .end local v2           #i:I
    .end local v3           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :catchall_57
    move-exception v4

    monitor-exit v5
    :try_end_59
    .catchall {:try_start_1c .. :try_end_59} :catchall_57

    throw v4

    .line 1262
    .restart local v0       #N:I
    .restart local v1       #canceledSomething:Z
    .restart local v2       #i:I
    :cond_5a
    if-eqz v1, :cond_5f

    .line 1263
    :try_start_5c
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    .line 1265
    :cond_5f
    monitor-exit v5
    :try_end_60
    .catchall {:try_start_5c .. :try_end_60} :catchall_57

    move v4, v1

    goto :goto_4c
.end method

.method public cancelNotification(Ljava/lang/String;I)V
    .registers 4
    .parameter "pkg"
    .parameter "id"
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .prologue
    .line 1271
    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0, p2}, Lcom/android/server/NotificationManagerService;->cancelNotificationWithTag(Ljava/lang/String;Ljava/lang/String;I)V

    .line 1272
    return-void
.end method

.method public cancelNotificationWithTag(Ljava/lang/String;Ljava/lang/String;I)V
    .registers 11
    .parameter "pkg"
    .parameter "tag"
    .parameter "id"

    .prologue
    const/4 v4, 0x0

    .line 1275
    invoke-virtual {p0, p1}, Lcom/android/server/NotificationManagerService;->checkCallerIsSystemOrSameApp(Ljava/lang/String;)V

    .line 1277
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    const/16 v1, 0x3e8

    if-ne v0, v1, :cond_16

    move v5, v4

    :goto_d
    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    move v3, p3

    move v6, v4

    invoke-direct/range {v0 .. v6}, Lcom/android/server/NotificationManagerService;->cancelNotification(Ljava/lang/String;Ljava/lang/String;IIIZ)V

    .line 1280
    return-void

    .line 1277
    :cond_16
    const/16 v5, 0x40

    goto :goto_d
.end method

.method public cancelToast(Ljava/lang/String;Landroid/app/ITransientNotification;)V
    .registers 10
    .parameter "pkg"
    .parameter "callback"

    .prologue
    .line 741
    const-string v3, "NotificationService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "cancelToast pkg="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " callback="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 743
    if-eqz p1, :cond_26

    if-nez p2, :cond_49

    .line 744
    :cond_26
    const-string v3, "NotificationService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Not cancelling notification. pkg="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " callback="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 761
    :goto_48
    return-void

    .line 748
    :cond_49
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    monitor-enter v4

    .line 749
    :try_start_4c
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_4f
    .catchall {:try_start_4c .. :try_end_4f} :catchall_5e

    move-result-wide v0

    .line 751
    .local v0, callingId:J
    :try_start_50
    invoke-direct {p0, p1, p2}, Lcom/android/server/NotificationManagerService;->indexOfToastLocked(Ljava/lang/String;Landroid/app/ITransientNotification;)I

    move-result v2

    .line 752
    .local v2, index:I
    if-ltz v2, :cond_61

    .line 753
    invoke-direct {p0, v2}, Lcom/android/server/NotificationManagerService;->cancelToastLocked(I)V
    :try_end_59
    .catchall {:try_start_50 .. :try_end_59} :catchall_84

    .line 758
    :goto_59
    :try_start_59
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 760
    monitor-exit v4

    goto :goto_48

    .end local v0           #callingId:J
    .end local v2           #index:I
    :catchall_5e
    move-exception v3

    monitor-exit v4
    :try_end_60
    .catchall {:try_start_59 .. :try_end_60} :catchall_5e

    throw v3

    .line 755
    .restart local v0       #callingId:J
    .restart local v2       #index:I
    :cond_61
    :try_start_61
    const-string v3, "NotificationService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Toast already cancelled. pkg="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " callback="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_83
    .catchall {:try_start_61 .. :try_end_83} :catchall_84

    goto :goto_59

    .line 758
    .end local v2           #index:I
    :catchall_84
    move-exception v3

    :try_start_85
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v3
    :try_end_89
    .catchall {:try_start_85 .. :try_end_89} :catchall_5e
.end method

.method checkCallerIsSystem()V
    .registers 5

    .prologue
    .line 1291
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    .line 1292
    .local v0, uid:I
    const/16 v1, 0x3e8

    if-eq v0, v1, :cond_a

    if-nez v0, :cond_b

    .line 1293
    :cond_a
    return-void

    .line 1295
    :cond_b
    new-instance v1, Ljava/lang/SecurityException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Disallowed call for uid "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/SecurityException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method checkCallerIsSystemOrSameApp(Ljava/lang/String;)V
    .registers 8
    .parameter "pkg"

    .prologue
    .line 1299
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v2

    .line 1300
    .local v2, uid:I
    const/16 v3, 0x3e8

    if-eq v2, v3, :cond_a

    if-nez v2, :cond_b

    .line 1313
    :cond_a
    return-void

    .line 1304
    :cond_b
    :try_start_b
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v3

    const/4 v4, 0x0

    invoke-virtual {v3, p1, v4}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v0

    .line 1306
    .local v0, ai:Landroid/content/pm/ApplicationInfo;
    iget v3, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-static {v3, v2}, Landroid/os/UserId;->isSameApp(II)Z

    move-result v3

    if-nez v3, :cond_a

    .line 1307
    new-instance v3, Ljava/lang/SecurityException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Calling uid "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " gave package"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " which is owned by uid "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget v5, v0, Landroid/content/pm/ApplicationInfo;->uid:I

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/SecurityException;-><init>(Ljava/lang/String;)V

    throw v3
    :try_end_4d
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_b .. :try_end_4d} :catch_4d

    .line 1310
    .end local v0           #ai:Landroid/content/pm/ApplicationInfo;
    :catch_4d
    move-exception v1

    .line 1311
    .local v1, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    new-instance v3, Ljava/lang/SecurityException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Unknown package "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/SecurityException;-><init>(Ljava/lang/String;)V

    throw v3
.end method

.method protected dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .registers 10
    .parameter "fd"
    .parameter "pw"
    .parameter "args"

    .prologue
    .line 1402
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.DUMP"

    invoke-virtual {v2, v3}, Landroid/content/Context;->checkCallingOrSelfPermission(Ljava/lang/String;)I

    move-result v2

    if-eqz v2, :cond_33

    .line 1404
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Permission Denial: can\'t dump NotificationManager from from pid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ", uid="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1450
    :goto_32
    return-void

    .line 1410
    :cond_33
    const-string v2, "Current Notification Manager state:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1414
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    monitor-enter v3

    .line 1415
    :try_start_3b
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1416
    .local v0, N:I
    if-lez v0, :cond_60

    .line 1417
    const-string v2, "  Toast Queue:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1418
    const/4 v1, 0x0

    .local v1, i:I
    :goto_49
    if-ge v1, v0, :cond_5b

    .line 1419
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$ToastRecord;

    const-string v4, "    "

    invoke-virtual {v2, p2, v4}, Lcom/android/server/NotificationManagerService$ToastRecord;->dump(Ljava/io/PrintWriter;Ljava/lang/String;)V

    .line 1418
    add-int/lit8 v1, v1, 0x1

    goto :goto_49

    .line 1421
    :cond_5b
    const-string v2, "  "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1424
    .end local v1           #i:I
    :cond_60
    monitor-exit v3
    :try_end_61
    .catchall {:try_start_3b .. :try_end_61} :catchall_86

    .line 1426
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v3

    .line 1427
    :try_start_64
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1428
    if-lez v0, :cond_8e

    .line 1429
    const-string v2, "  Notification List:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1430
    const/4 v1, 0x0

    .restart local v1       #i:I
    :goto_72
    if-ge v1, v0, :cond_89

    .line 1431
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$NotificationRecord;

    const-string v4, "    "

    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2, p2, v4, v5}, Lcom/android/server/NotificationManagerService$NotificationRecord;->dump(Ljava/io/PrintWriter;Ljava/lang/String;Landroid/content/Context;)V
    :try_end_83
    .catchall {:try_start_64 .. :try_end_83} :catchall_11c

    .line 1430
    add-int/lit8 v1, v1, 0x1

    goto :goto_72

    .line 1424
    .end local v0           #N:I
    .end local v1           #i:I
    :catchall_86
    move-exception v2

    :try_start_87
    monitor-exit v3
    :try_end_88
    .catchall {:try_start_87 .. :try_end_88} :catchall_86

    throw v2

    .line 1433
    .restart local v0       #N:I
    .restart local v1       #i:I
    :cond_89
    :try_start_89
    const-string v2, "  "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1436
    .end local v1           #i:I
    :cond_8e
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1437
    if-lez v0, :cond_b5

    .line 1438
    const-string v2, "  Lights List:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1439
    const/4 v1, 0x0

    .restart local v1       #i:I
    :goto_9c
    if-ge v1, v0, :cond_b0

    .line 1440
    iget-object v2, p0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$NotificationRecord;

    const-string v4, "    "

    iget-object v5, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2, p2, v4, v5}, Lcom/android/server/NotificationManagerService$NotificationRecord;->dump(Ljava/io/PrintWriter;Ljava/lang/String;Landroid/content/Context;)V

    .line 1439
    add-int/lit8 v1, v1, 0x1

    goto :goto_9c

    .line 1442
    :cond_b0
    const-string v2, "  "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1445
    .end local v1           #i:I
    :cond_b5
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "  mSoundNotification="

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1446
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "  mVibrateNotification="

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1447
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "  mDisabledNotifications=0x"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget v4, p0, Lcom/android/server/NotificationManagerService;->mDisabledNotifications:I

    invoke-static {v4}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1448
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "  mSystemReady="

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-boolean v4, p0, Lcom/android/server/NotificationManagerService;->mSystemReady:Z

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1449
    monitor-exit v3

    goto/16 :goto_32

    :catchall_11c
    move-exception v2

    monitor-exit v3
    :try_end_11e
    .catchall {:try_start_89 .. :try_end_11e} :catchall_11c

    throw v2
.end method

.method public enqueueNotification(Ljava/lang/String;ILandroid/app/Notification;[I)V
    .registers 11
    .parameter "pkg"
    .parameter "id"
    .parameter "notification"
    .parameter "idOut"
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .prologue
    .line 882
    const/4 v2, 0x0

    move-object v0, p0

    move-object v1, p1

    move v3, p2

    move-object v4, p3

    move-object v5, p4

    invoke-virtual/range {v0 .. v5}, Lcom/android/server/NotificationManagerService;->enqueueNotificationWithTag(Ljava/lang/String;Ljava/lang/String;ILandroid/app/Notification;[I)V

    .line 883
    return-void
.end method

.method public enqueueNotificationInternal(Ljava/lang/String;IILjava/lang/String;ILandroid/app/Notification;[I)V
    .registers 43
    .parameter "pkg"
    .parameter "callingUid"
    .parameter "callingPid"
    .parameter "tag"
    .parameter "id"
    .parameter "notification"
    .parameter "idOut"

    .prologue
    .line 905
    invoke-virtual/range {p0 .. p1}, Lcom/android/server/NotificationManagerService;->checkCallerIsSystemOrSameApp(Ljava/lang/String;)V

    .line 906
    const-string v5, "android"

    move-object/from16 v0, p1

    invoke-virtual {v5, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v27

    .line 910
    .local v27, isSystemNotification:Z
    if-nez v27, :cond_6e

    .line 911
    move-object/from16 v0, p0

    iget-object v6, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v6

    .line 912
    const/16 v22, 0x0

    .line 913
    .local v22, count:I
    :try_start_14
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v19

    .line 914
    .local v19, N:I
    const/16 v23, 0x0

    .local v23, i:I
    :goto_1e
    move/from16 v0, v23

    move/from16 v1, v19

    if-ge v0, v1, :cond_6d

    .line 915
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    move/from16 v0, v23

    invoke-virtual {v5, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 916
    .local v4, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    iget-object v5, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->pkg:Ljava/lang/String;

    move-object/from16 v0, p1

    invoke-virtual {v5, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_6a

    .line 917
    add-int/lit8 v22, v22, 0x1

    .line 918
    const/16 v5, 0x32

    move/from16 v0, v22

    if-lt v0, v5, :cond_6a

    .line 919
    const-string v5, "NotificationService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Package has already posted "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    move/from16 v0, v22

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " notifications.  Not showing more.  package="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    move-object/from16 v0, p1

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v5, v7}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 921
    monitor-exit v6

    .line 1122
    .end local v4           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    .end local v19           #N:I
    .end local v22           #count:I
    .end local v23           #i:I
    :cond_69
    :goto_69
    return-void

    .line 914
    .restart local v4       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    .restart local v19       #N:I
    .restart local v22       #count:I
    .restart local v23       #i:I
    :cond_6a
    add-int/lit8 v23, v23, 0x1

    goto :goto_1e

    .line 925
    .end local v4           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_6d
    monitor-exit v6
    :try_end_6e
    .catchall {:try_start_14 .. :try_end_6e} :catchall_d4

    .line 930
    .end local v19           #N:I
    .end local v22           #count:I
    .end local v23           #i:I
    :cond_6e
    const-string v5, "com.android.providers.downloads"

    move-object/from16 v0, p1

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_81

    const-string v5, "DownloadManager"

    const/4 v6, 0x2

    invoke-static {v5, v6}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    move-result v5

    if-eqz v5, :cond_9d

    .line 932
    :cond_81
    const/16 v5, 0xabe

    const/4 v6, 0x4

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object p1, v6, v7

    const/4 v7, 0x1

    invoke-static/range {p5 .. p5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x2

    aput-object p4, v6, v7

    const/4 v7, 0x3

    invoke-virtual/range {p6 .. p6}, Landroid/app/Notification;->toString()Ljava/lang/String;

    move-result-object v8

    aput-object v8, v6, v7

    invoke-static {v5, v6}, Landroid/util/EventLog;->writeEvent(I[Ljava/lang/Object;)I

    .line 936
    :cond_9d
    if-eqz p1, :cond_a1

    if-nez p6, :cond_d7

    .line 937
    :cond_a1
    new-instance v5, Ljava/lang/IllegalArgumentException;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "null not allowed: pkg="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p1

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " id="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move/from16 v0, p5

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " notification="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 925
    .restart local v22       #count:I
    :catchall_d4
    move-exception v5

    :try_start_d5
    monitor-exit v6
    :try_end_d6
    .catchall {:try_start_d5 .. :try_end_d6} :catchall_d4

    throw v5

    .line 940
    .end local v22           #count:I
    :cond_d7
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->icon:I

    if-eqz v5, :cond_116

    .line 941
    move-object/from16 v0, p6

    iget-object v5, v0, Landroid/app/Notification;->contentView:Landroid/widget/RemoteViews;

    if-nez v5, :cond_116

    .line 942
    new-instance v5, Ljava/lang/IllegalArgumentException;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "contentView required: pkg="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p1

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " id="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move/from16 v0, p5

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " notification="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 950
    :cond_116
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->priority:I

    const/4 v6, -0x2

    const/4 v7, 0x2

    invoke-static {v5, v6, v7}, Lcom/android/server/NotificationManagerService;->clamp(III)I

    move-result v5

    move-object/from16 v0, p6

    iput v5, v0, Landroid/app/Notification;->priority:I

    .line 952
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit16 v5, v5, 0x80

    if-eqz v5, :cond_138

    .line 953
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->priority:I

    const/4 v6, 0x2

    if-ge v5, v6, :cond_138

    const/4 v5, 0x2

    move-object/from16 v0, p6

    iput v5, v0, Landroid/app/Notification;->priority:I

    .line 959
    :cond_138
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->priority:I

    mul-int/lit8 v10, v5, 0xa

    .line 966
    .local v10, score:I
    if-nez v27, :cond_168

    invoke-direct/range {p0 .. p1}, Lcom/android/server/NotificationManagerService;->areNotificationsEnabledForPackageInt(Ljava/lang/String;)Z

    move-result v5

    if-nez v5, :cond_168

    .line 967
    const/16 v10, -0x3e8

    .line 968
    const-string v5, "NotificationService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Suppressing notification from package "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p1

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " by user request."

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 975
    :cond_168
    const/16 v5, -0x14

    if-lt v10, v5, :cond_69

    .line 980
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    move-object/from16 v34, v0

    monitor-enter v34

    .line 981
    :try_start_173
    new-instance v4, Lcom/android/server/NotificationManagerService$NotificationRecord;

    move-object/from16 v5, p1

    move-object/from16 v6, p4

    move/from16 v7, p5

    move/from16 v8, p2

    move/from16 v9, p3

    move-object/from16 v11, p6

    invoke-direct/range {v4 .. v11}, Lcom/android/server/NotificationManagerService$NotificationRecord;-><init>(Ljava/lang/String;Ljava/lang/String;IIIILandroid/app/Notification;)V

    .line 985
    .restart local v4       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    const/16 v29, 0x0

    .line 987
    .local v29, old:Lcom/android/server/NotificationManagerService$NotificationRecord;
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move-object/from16 v2, p4

    move/from16 v3, p5

    invoke-direct {v0, v1, v2, v3}, Lcom/android/server/NotificationManagerService;->indexOfNotificationLocked(Ljava/lang/String;Ljava/lang/String;I)I

    move-result v26

    .line 988
    .local v26, index:I
    if-gez v26, :cond_2c5

    .line 989
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1002
    :cond_19b
    :goto_19b
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x40

    if-eqz v5, :cond_1ad

    .line 1003
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    or-int/lit8 v5, v5, 0x22

    move-object/from16 v0, p6

    iput v5, v0, Landroid/app/Notification;->flags:I

    .line 1007
    :cond_1ad
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->icon:I

    if-eqz v5, :cond_31e

    .line 1008
    new-instance v11, Lcom/android/internal/statusbar/StatusBarNotification;

    iget v15, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->uid:I

    iget v0, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->initialPid:I

    move/from16 v16, v0

    move-object/from16 v12, p1

    move/from16 v13, p5

    move-object/from16 v14, p4

    move/from16 v17, v10

    move-object/from16 v18, p6

    invoke-direct/range {v11 .. v18}, Lcom/android/internal/statusbar/StatusBarNotification;-><init>(Ljava/lang/String;ILjava/lang/String;IIILandroid/app/Notification;)V

    .line 1010
    .local v11, n:Lcom/android/internal/statusbar/StatusBarNotification;
    if-eqz v29, :cond_2f7

    move-object/from16 v0, v29

    iget-object v5, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    if-eqz v5, :cond_2f7

    .line 1011
    move-object/from16 v0, v29

    iget-object v5, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    iput-object v5, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    .line 1012
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_1d9
    .catchall {:try_start_173 .. :try_end_1d9} :catchall_2ef

    move-result-wide v24

    .line 1014
    .local v24, identity:J
    :try_start_1da
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mStatusBar:Lcom/android/server/StatusBarManagerService;

    iget-object v6, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    invoke-virtual {v5, v6, v11}, Lcom/android/server/StatusBarManagerService;->updateNotification(Landroid/os/IBinder;Lcom/android/internal/statusbar/StatusBarNotification;)V
    :try_end_1e3
    .catchall {:try_start_1da .. :try_end_1e3} :catchall_2f2

    .line 1017
    :try_start_1e3
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1031
    :goto_1e6
    move-object/from16 v0, p0

    move-object/from16 v1, p6

    move-object/from16 v2, p1

    invoke-direct {v0, v1, v2}, Lcom/android/server/NotificationManagerService;->sendAccessibilityEvent(Landroid/app/Notification;Ljava/lang/CharSequence;)V

    .line 1046
    .end local v11           #n:Lcom/android/internal/statusbar/StatusBarNotification;
    .end local v24           #identity:J
    :cond_1ef
    :goto_1ef
    move-object/from16 v0, p0

    iget v5, v0, Lcom/android/server/NotificationManagerService;->mDisabledNotifications:I

    const/high16 v6, 0x4

    and-int/2addr v5, v6

    if-nez v5, :cond_297

    if-eqz v29, :cond_202

    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x8

    if-nez v5, :cond_297

    :cond_202
    move-object/from16 v0, p0

    iget-boolean v5, v0, Lcom/android/server/NotificationManagerService;->mSystemReady:Z

    if-eqz v5, :cond_297

    .line 1051
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    const-string v6, "audio"

    invoke-virtual {v5, v6}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v20

    check-cast v20, Landroid/media/AudioManager;

    .line 1054
    .local v20, audioManager:Landroid/media/AudioManager;
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->defaults:I

    and-int/lit8 v5, v5, 0x1

    if-eqz v5, :cond_359

    const/16 v32, 0x1

    .line 1056
    .local v32, useDefaultSound:Z
    :goto_21e
    if-nez v32, :cond_226

    move-object/from16 v0, p6

    iget-object v5, v0, Landroid/app/Notification;->sound:Landroid/net/Uri;

    if-eqz v5, :cond_266

    .line 1058
    :cond_226
    if-eqz v32, :cond_35d

    .line 1059
    sget-object v31, Landroid/provider/Settings$System;->DEFAULT_NOTIFICATION_URI:Landroid/net/Uri;

    .line 1063
    .local v31, uri:Landroid/net/Uri;
    :goto_22a
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x4

    if-eqz v5, :cond_365

    const/16 v28, 0x1

    .line 1065
    .local v28, looping:Z
    :goto_234
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->audioStreamType:I

    if-ltz v5, :cond_369

    .line 1066
    move-object/from16 v0, p6

    iget v0, v0, Landroid/app/Notification;->audioStreamType:I

    move/from16 v21, v0

    .line 1070
    .local v21, audioStreamType:I
    :goto_240
    move-object/from16 v0, p0

    iput-object v4, v0, Lcom/android/server/NotificationManagerService;->mSoundNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1073
    invoke-virtual/range {v20 .. v21}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v5

    if-eqz v5, :cond_266

    .line 1074
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_24d
    .catchall {:try_start_1e3 .. :try_end_24d} :catchall_2ef

    move-result-wide v24

    .line 1076
    .restart local v24       #identity:J
    :try_start_24e
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mAudioService:Landroid/media/IAudioService;

    invoke-interface {v5}, Landroid/media/IAudioService;->getRingtonePlayer()Landroid/media/IRingtonePlayer;

    move-result-object v30

    .line 1077
    .local v30, player:Landroid/media/IRingtonePlayer;
    if-eqz v30, :cond_263

    .line 1078
    move-object/from16 v0, v30

    move-object/from16 v1, v31

    move/from16 v2, v28

    move/from16 v3, v21

    invoke-interface {v0, v1, v2, v3}, Landroid/media/IRingtonePlayer;->playAsync(Landroid/net/Uri;ZI)V
    :try_end_263
    .catchall {:try_start_24e .. :try_end_263} :catchall_36d
    .catch Landroid/os/RemoteException; {:try_start_24e .. :try_end_263} :catch_391

    .line 1082
    :cond_263
    :try_start_263
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1088
    .end local v21           #audioStreamType:I
    .end local v24           #identity:J
    .end local v28           #looping:Z
    .end local v30           #player:Landroid/media/IRingtonePlayer;
    .end local v31           #uri:Landroid/net/Uri;
    :cond_266
    :goto_266
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->defaults:I

    and-int/lit8 v5, v5, 0x2

    if-eqz v5, :cond_372

    const/16 v33, 0x1

    .line 1090
    .local v33, useDefaultVibrate:Z
    :goto_270
    if-nez v33, :cond_278

    move-object/from16 v0, p6

    iget-object v5, v0, Landroid/app/Notification;->vibrate:[J

    if-eqz v5, :cond_297

    :cond_278
    invoke-virtual/range {v20 .. v20}, Landroid/media/AudioManager;->getRingerMode()I

    move-result v5

    if-eqz v5, :cond_297

    .line 1092
    move-object/from16 v0, p0

    iput-object v4, v0, Lcom/android/server/NotificationManagerService;->mVibrateNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1094
    move-object/from16 v0, p0

    iget-object v7, v0, Lcom/android/server/NotificationManagerService;->mVibrator:Landroid/os/Vibrator;

    if-eqz v33, :cond_376

    sget-object v5, Lcom/android/server/NotificationManagerService;->DEFAULT_VIBRATE_PATTERN:[J

    move-object v6, v5

    :goto_28b
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x4

    if-eqz v5, :cond_37d

    const/4 v5, 0x0

    :goto_294
    invoke-virtual {v7, v6, v5}, Landroid/os/Vibrator;->vibrate([JI)V

    .line 1104
    .end local v20           #audioManager:Landroid/media/AudioManager;
    .end local v32           #useDefaultSound:Z
    .end local v33           #useDefaultVibrate:Z
    :cond_297
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    move-object/from16 v0, v29

    invoke-virtual {v5, v0}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 1105
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    move-object/from16 v0, v29

    if-ne v5, v0, :cond_2ad

    .line 1106
    const/4 v5, 0x0

    move-object/from16 v0, p0

    iput-object v5, v0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 1110
    :cond_2ad
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x1

    if-eqz v5, :cond_380

    .line 1111
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mLights:Ljava/util/ArrayList;

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1112
    invoke-direct/range {p0 .. p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    .line 1119
    :cond_2bf
    :goto_2bf
    monitor-exit v34
    :try_end_2c0
    .catchall {:try_start_263 .. :try_end_2c0} :catchall_2ef

    .line 1121
    const/4 v5, 0x0

    aput p5, p7, v5

    goto/16 :goto_69

    .line 991
    :cond_2c5
    :try_start_2c5
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    move/from16 v0, v26

    invoke-virtual {v5, v0}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    move-result-object v29

    .end local v29           #old:Lcom/android/server/NotificationManagerService$NotificationRecord;
    check-cast v29, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 992
    .restart local v29       #old:Lcom/android/server/NotificationManagerService$NotificationRecord;
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    move/from16 v0, v26

    invoke-virtual {v5, v0, v4}, Ljava/util/ArrayList;->add(ILjava/lang/Object;)V

    .line 994
    if-eqz v29, :cond_19b

    .line 995
    move-object/from16 v0, p6

    iget v5, v0, Landroid/app/Notification;->flags:I

    move-object/from16 v0, v29

    iget-object v6, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v6, v6, Landroid/app/Notification;->flags:I

    and-int/lit8 v6, v6, 0x40

    or-int/2addr v5, v6

    move-object/from16 v0, p6

    iput v5, v0, Landroid/app/Notification;->flags:I

    goto/16 :goto_19b

    .line 1119
    .end local v4           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    .end local v26           #index:I
    .end local v29           #old:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :catchall_2ef
    move-exception v5

    monitor-exit v34
    :try_end_2f1
    .catchall {:try_start_2c5 .. :try_end_2f1} :catchall_2ef

    throw v5

    .line 1017
    .restart local v4       #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    .restart local v11       #n:Lcom/android/internal/statusbar/StatusBarNotification;
    .restart local v24       #identity:J
    .restart local v26       #index:I
    .restart local v29       #old:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :catchall_2f2
    move-exception v5

    :try_start_2f3
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v5

    .line 1020
    .end local v24           #identity:J
    :cond_2f7
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_2fa
    .catchall {:try_start_2f3 .. :try_end_2fa} :catchall_2ef

    move-result-wide v24

    .line 1022
    .restart local v24       #identity:J
    :try_start_2fb
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mStatusBar:Lcom/android/server/StatusBarManagerService;

    invoke-virtual {v5, v11}, Lcom/android/server/StatusBarManagerService;->addNotification(Lcom/android/internal/statusbar/StatusBarNotification;)Landroid/os/IBinder;

    move-result-object v5

    iput-object v5, v4, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    .line 1023
    iget-object v5, v11, Lcom/android/internal/statusbar/StatusBarNotification;->notification:Landroid/app/Notification;

    iget v5, v5, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x1

    if-eqz v5, :cond_314

    .line 1024
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mAttentionLight:Lcom/android/server/LightsService$Light;

    invoke-virtual {v5}, Lcom/android/server/LightsService$Light;->pulse()V
    :try_end_314
    .catchall {:try_start_2fb .. :try_end_314} :catchall_319

    .line 1028
    :cond_314
    :try_start_314
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    goto/16 :goto_1e6

    :catchall_319
    move-exception v5

    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v5

    .line 1033
    .end local v11           #n:Lcom/android/internal/statusbar/StatusBarNotification;
    .end local v24           #identity:J
    :cond_31e
    const-string v5, "NotificationService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Ignoring notification with icon==0: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v0, p6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1034
    if-eqz v29, :cond_1ef

    move-object/from16 v0, v29

    iget-object v5, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    if-eqz v5, :cond_1ef

    .line 1035
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_343
    .catchall {:try_start_314 .. :try_end_343} :catchall_2ef

    move-result-wide v24

    .line 1037
    .restart local v24       #identity:J
    :try_start_344
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/android/server/NotificationManagerService;->mStatusBar:Lcom/android/server/StatusBarManagerService;

    move-object/from16 v0, v29

    iget-object v6, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->statusBarKey:Landroid/os/IBinder;

    invoke-virtual {v5, v6}, Lcom/android/server/StatusBarManagerService;->removeNotification(Landroid/os/IBinder;)V
    :try_end_34f
    .catchall {:try_start_344 .. :try_end_34f} :catchall_354

    .line 1040
    :try_start_34f
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    goto/16 :goto_1ef

    :catchall_354
    move-exception v5

    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v5

    .line 1054
    .end local v24           #identity:J
    .restart local v20       #audioManager:Landroid/media/AudioManager;
    :cond_359
    const/16 v32, 0x0

    goto/16 :goto_21e

    .line 1061
    .restart local v32       #useDefaultSound:Z
    :cond_35d
    move-object/from16 v0, p6

    iget-object v0, v0, Landroid/app/Notification;->sound:Landroid/net/Uri;

    move-object/from16 v31, v0

    .restart local v31       #uri:Landroid/net/Uri;
    goto/16 :goto_22a

    .line 1063
    :cond_365
    const/16 v28, 0x0

    goto/16 :goto_234

    .line 1068
    .restart local v28       #looping:Z
    :cond_369
    const/16 v21, 0x5

    .restart local v21       #audioStreamType:I
    goto/16 :goto_240

    .line 1082
    .restart local v24       #identity:J
    :catchall_36d
    move-exception v5

    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v5

    .line 1088
    .end local v21           #audioStreamType:I
    .end local v24           #identity:J
    .end local v28           #looping:Z
    .end local v31           #uri:Landroid/net/Uri;
    :cond_372
    const/16 v33, 0x0

    goto/16 :goto_270

    .line 1094
    .restart local v33       #useDefaultVibrate:Z
    :cond_376
    move-object/from16 v0, p6

    iget-object v5, v0, Landroid/app/Notification;->vibrate:[J

    move-object v6, v5

    goto/16 :goto_28b

    :cond_37d
    const/4 v5, -0x1

    goto/16 :goto_294

    .line 1114
    .end local v20           #audioManager:Landroid/media/AudioManager;
    .end local v32           #useDefaultSound:Z
    .end local v33           #useDefaultVibrate:Z
    :cond_380
    if-eqz v29, :cond_2bf

    move-object/from16 v0, v29

    iget-object v5, v0, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iget v5, v5, Landroid/app/Notification;->flags:I

    and-int/lit8 v5, v5, 0x1

    if-eqz v5, :cond_2bf

    .line 1116
    invoke-direct/range {p0 .. p0}, Lcom/android/server/NotificationManagerService;->updateLightsLocked()V

    goto/16 :goto_2bf

    .line 1080
    .restart local v20       #audioManager:Landroid/media/AudioManager;
    .restart local v21       #audioStreamType:I
    .restart local v24       #identity:J
    .restart local v28       #looping:Z
    .restart local v31       #uri:Landroid/net/Uri;
    .restart local v32       #useDefaultSound:Z
    :catch_391
    move-exception v5

    .line 1082
    invoke-static/range {v24 .. v25}, Landroid/os/Binder;->restoreCallingIdentity(J)V
    :try_end_395
    .catchall {:try_start_34f .. :try_end_395} :catchall_2ef

    goto/16 :goto_266
.end method

.method public enqueueNotificationWithTag(Ljava/lang/String;Ljava/lang/String;ILandroid/app/Notification;[I)V
    .registers 14
    .parameter "pkg"
    .parameter "tag"
    .parameter "id"
    .parameter "notification"
    .parameter "idOut"

    .prologue
    .line 888
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v2

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v3

    move-object v0, p0

    move-object v1, p1

    move-object v4, p2

    move v5, p3

    move-object v6, p4

    move-object v7, p5

    invoke-virtual/range {v0 .. v7}, Lcom/android/server/NotificationManagerService;->enqueueNotificationInternal(Ljava/lang/String;IILjava/lang/String;ILandroid/app/Notification;[I)V

    .line 890
    return-void
.end method

.method public enqueueToast(Ljava/lang/String;Landroid/app/ITransientNotification;I)V
    .registers 21
    .parameter "pkg"
    .parameter "callback"
    .parameter "duration"

    .prologue
    .line 680
    if-eqz p1, :cond_4

    if-nez p2, :cond_2b

    .line 681
    :cond_4
    const-string v13, "NotificationService"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Not doing toast. pkg="

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    move-object/from16 v0, p1

    invoke-virtual {v14, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, " callback="

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    move-object/from16 v0, p2

    invoke-virtual {v14, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 738
    :goto_2a
    return-void

    .line 685
    :cond_2b
    const-string v13, "android"

    move-object/from16 v0, p1

    invoke-virtual {v13, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    .line 687
    .local v10, isSystemToast:Z
    if-nez v10, :cond_5c

    invoke-direct/range {p0 .. p1}, Lcom/android/server/NotificationManagerService;->areNotificationsEnabledForPackageInt(Ljava/lang/String;)Z

    move-result v13

    if-nez v13, :cond_5c

    .line 688
    const-string v13, "NotificationService"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Suppressing toast from package "

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    move-object/from16 v0, p1

    invoke-virtual {v14, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, " by user request."

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2a

    .line 692
    :cond_5c
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    monitor-enter v14

    .line 693
    :try_start_61
    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v6

    .line 694
    .local v6, callingPid:I
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_68
    .catchall {:try_start_61 .. :try_end_68} :catchall_88

    move-result-wide v4

    .line 697
    .local v4, callingId:J
    :try_start_69
    invoke-direct/range {p0 .. p2}, Lcom/android/server/NotificationManagerService;->indexOfToastLocked(Ljava/lang/String;Landroid/app/ITransientNotification;)I

    move-result v9

    .line 700
    .local v9, index:I
    if-ltz v9, :cond_8b

    .line 701
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v13, v9}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 702
    .local v12, record:Lcom/android/server/NotificationManagerService$ToastRecord;
    move/from16 v0, p3

    invoke-virtual {v12, v0}, Lcom/android/server/NotificationManagerService$ToastRecord;->update(I)V

    .line 731
    :goto_7e
    if-nez v9, :cond_83

    .line 732
    invoke-direct/range {p0 .. p0}, Lcom/android/server/NotificationManagerService;->showNextToastLocked()V
    :try_end_83
    .catchall {:try_start_69 .. :try_end_83} :catchall_103

    .line 735
    :cond_83
    :try_start_83
    invoke-static {v4, v5}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 737
    monitor-exit v14

    goto :goto_2a

    .end local v4           #callingId:J
    .end local v6           #callingPid:I
    .end local v9           #index:I
    .end local v12           #record:Lcom/android/server/NotificationManagerService$ToastRecord;
    :catchall_88
    move-exception v13

    monitor-exit v14
    :try_end_8a
    .catchall {:try_start_83 .. :try_end_8a} :catchall_88

    throw v13

    .line 706
    .restart local v4       #callingId:J
    .restart local v6       #callingPid:I
    .restart local v9       #index:I
    :cond_8b
    if-nez v10, :cond_e0

    .line 707
    const/4 v7, 0x0

    .line 708
    .local v7, count:I
    :try_start_8e
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v13}, Ljava/util/ArrayList;->size()I

    move-result v3

    .line 709
    .local v3, N:I
    const/4 v8, 0x0

    .local v8, i:I
    :goto_97
    if-ge v8, v3, :cond_e0

    .line 710
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v13, v8}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Lcom/android/server/NotificationManagerService$ToastRecord;

    .line 711
    .local v11, r:Lcom/android/server/NotificationManagerService$ToastRecord;
    iget-object v13, v11, Lcom/android/server/NotificationManagerService$ToastRecord;->pkg:Ljava/lang/String;

    move-object/from16 v0, p1

    invoke-virtual {v13, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_dd

    .line 712
    add-int/lit8 v7, v7, 0x1

    .line 713
    const/16 v13, 0x32

    if-lt v7, v13, :cond_dd

    .line 714
    const-string v13, "NotificationService"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Package has already posted "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, " toasts. Not showing more. Package="

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v13, v15}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_d7
    .catchall {:try_start_8e .. :try_end_d7} :catchall_103

    .line 735
    :try_start_d7
    invoke-static {v4, v5}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 716
    monitor-exit v14
    :try_end_db
    .catchall {:try_start_d7 .. :try_end_db} :catchall_88

    goto/16 :goto_2a

    .line 709
    :cond_dd
    add-int/lit8 v8, v8, 0x1

    goto :goto_97

    .line 722
    .end local v3           #N:I
    .end local v7           #count:I
    .end local v8           #i:I
    .end local v11           #r:Lcom/android/server/NotificationManagerService$ToastRecord;
    :cond_e0
    :try_start_e0
    new-instance v12, Lcom/android/server/NotificationManagerService$ToastRecord;

    move-object/from16 v0, p1

    move-object/from16 v1, p2

    move/from16 v2, p3

    invoke-direct {v12, v6, v0, v1, v2}, Lcom/android/server/NotificationManagerService$ToastRecord;-><init>(ILjava/lang/String;Landroid/app/ITransientNotification;I)V

    .line 723
    .restart local v12       #record:Lcom/android/server/NotificationManagerService$ToastRecord;
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v13, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 724
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/NotificationManagerService;->mToastQueue:Ljava/util/ArrayList;

    invoke-virtual {v13}, Ljava/util/ArrayList;->size()I

    move-result v13

    add-int/lit8 v9, v13, -0x1

    .line 725
    move-object/from16 v0, p0

    invoke-direct {v0, v6}, Lcom/android/server/NotificationManagerService;->keepProcessAliveLocked(I)V
    :try_end_101
    .catchall {:try_start_e0 .. :try_end_101} :catchall_103

    goto/16 :goto_7e

    .line 735
    .end local v9           #index:I
    .end local v12           #record:Lcom/android/server/NotificationManagerService$ToastRecord;
    :catchall_103
    move-exception v13

    :try_start_104
    invoke-static {v4, v5}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v13
    :try_end_108
    .catchall {:try_start_104 .. :try_end_108} :catchall_88
.end method

.method getDefaultNotificationColor()I
    .registers 2
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 95
    iget v0, p0, Lcom/android/server/NotificationManagerService;->mDefaultNotificationColor:I

    return v0
.end method

.method getLedNotification()Lcom/android/server/NotificationManagerService$NotificationRecord;
    .registers 2
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 98
    iget-object v0, p0, Lcom/android/server/NotificationManagerService;->mLedNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;

    return-object v0
.end method

.method public setNotificationsEnabledForPackage(Ljava/lang/String;Z)V
    .registers 8
    .parameter "pkg"
    .parameter "enabled"

    .prologue
    .line 290
    invoke-virtual {p0}, Lcom/android/server/NotificationManagerService;->checkCallerIsSystem()V

    .line 294
    if-eqz p2, :cond_e

    .line 295
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    invoke-virtual {v3, p1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    .line 313
    :goto_a
    invoke-direct {p0}, Lcom/android/server/NotificationManagerService;->writeBlockDb()V

    .line 314
    return-void

    .line 297
    :cond_e
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mBlockedPackages:Ljava/util/HashSet;

    invoke-virtual {v3, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 301
    iget-object v4, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    monitor-enter v4

    .line 302
    :try_start_16
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 303
    .local v0, N:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_1d
    if-ge v1, v0, :cond_36

    .line 304
    iget-object v3, p0, Lcom/android/server/NotificationManagerService;->mNotificationList:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/NotificationManagerService$NotificationRecord;

    .line 305
    .local v2, r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    iget-object v3, v2, Lcom/android/server/NotificationManagerService$NotificationRecord;->pkg:Ljava/lang/String;

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_33

    .line 306
    const/4 v3, 0x0

    invoke-direct {p0, v2, v3}, Lcom/android/server/NotificationManagerService;->cancelNotificationLocked(Lcom/android/server/NotificationManagerService$NotificationRecord;Z)V

    .line 303
    :cond_33
    add-int/lit8 v1, v1, 0x1

    goto :goto_1d

    .line 309
    .end local v2           #r:Lcom/android/server/NotificationManagerService$NotificationRecord;
    :cond_36
    monitor-exit v4

    goto :goto_a

    .end local v0           #N:I
    .end local v1           #i:I
    :catchall_38
    move-exception v3

    monitor-exit v4
    :try_end_3a
    .catchall {:try_start_16 .. :try_end_3a} :catchall_38

    throw v3
.end method

.method systemReady()V
    .registers 2

    .prologue
    .line 667
    const-string v0, "audio"

    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    invoke-static {v0}, Landroid/media/IAudioService$Stub;->asInterface(Landroid/os/IBinder;)Landroid/media/IAudioService;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/NotificationManagerService;->mAudioService:Landroid/media/IAudioService;

    .line 671
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/NotificationManagerService;->mSystemReady:Z

    .line 672
    return-void
.end method
