.class public Lcom/android/server/accessibility/AccessibilityManagerService;
.super Landroid/view/accessibility/IAccessibilityManager$Stub;
.source "AccessibilityManagerService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;,
        Lcom/android/server/accessibility/AccessibilityManagerService$Service;,
        Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;,
        Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;
    }
.end annotation


# static fields
.field private static final COMPONENT_NAME_SEPARATOR:C = ':'

.field private static final DEBUG:Z = false

.field private static final FUNCTION_REGISTER_UI_TEST_AUTOMATION_SERVICE:Ljava/lang/String; = "registerUiTestAutomationService"

.field private static final LOG_TAG:Ljava/lang/String; = "AccessibilityManagerService"

.field private static final MSG_SEND_ACCESSIBILITY_EVENT_TO_INPUT_FILTER:I = 0x3

.field private static final MSG_SHOW_ENABLE_TOUCH_EXPLORATION_DIALOG:I = 0x1

.field private static final MSG_TOGGLE_TOUCH_EXPLORATION:I = 0x2

.field private static final OWN_PROCESS_ID:I

.field private static sIdCounter:I

.field private static sNextWindowId:I


# instance fields
.field final mClients:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Landroid/view/accessibility/IAccessibilityManagerClient;",
            ">;"
        }
    .end annotation
.end field

.field final mComponentNameToServiceMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map",
            "<",
            "Landroid/content/ComponentName;",
            "Lcom/android/server/accessibility/AccessibilityManagerService$Service;",
            ">;"
        }
    .end annotation
.end field

.field final mContext:Landroid/content/Context;

.field private mEnableTouchExplorationDialog:Landroid/app/AlertDialog;

.field private final mEnabledServices:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set",
            "<",
            "Landroid/content/ComponentName;",
            ">;"
        }
    .end annotation
.end field

.field private final mEnabledServicesForFeedbackTempList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Landroid/accessibilityservice/AccessibilityServiceInfo;",
            ">;"
        }
    .end annotation
.end field

.field private mHandledFeedbackTypes:I

.field private mHasInputFilter:Z

.field private mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

.field private final mInstalledServices:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Landroid/accessibilityservice/AccessibilityServiceInfo;",
            ">;"
        }
    .end annotation
.end field

.field private mIsAccessibilityEnabled:Z

.field private mIsTouchExplorationEnabled:Z

.field final mLock:Ljava/lang/Object;

.field private final mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

.field private mPackageManager:Landroid/content/pm/PackageManager;

.field private mQueryBridge:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

.field private final mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

.field final mServices:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lcom/android/server/accessibility/AccessibilityManagerService$Service;",
            ">;"
        }
    .end annotation
.end field

.field private final mStringColonSplitter:Landroid/text/TextUtils$SimpleStringSplitter;

.field private mTouchExplorationGestureEnded:Z

.field private mTouchExplorationGestureStarted:Z

.field private final mTouchExplorationGrantedServices:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set",
            "<",
            "Landroid/content/ComponentName;",
            ">;"
        }
    .end annotation
.end field

.field private mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

.field private final mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;",
            ">;"
        }
    .end annotation
.end field

.field private final mWindowIdToWindowTokenMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Landroid/os/IBinder;",
            ">;"
        }
    .end annotation
.end field

.field private final mWindowManagerService:Lcom/android/server/wm/WindowManagerService;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 110
    invoke-static {}, Landroid/os/Process;->myPid()I

    move-result v0

    sput v0, Lcom/android/server/accessibility/AccessibilityManagerService;->OWN_PROCESS_ID:I

    .line 118
    const/4 v0, 0x0

    sput v0, Lcom/android/server/accessibility/AccessibilityManagerService;->sIdCounter:I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .registers 4
    .parameter "context"

    .prologue
    .line 181
    invoke-direct {p0}, Landroid/view/accessibility/IAccessibilityManager$Stub;-><init>()V

    .line 124
    new-instance v0, Ljava/lang/Object;

    invoke-direct/range {v0 .. v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    .line 126
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    .line 128
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mClients:Ljava/util/List;

    .line 131
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mComponentNameToServiceMap:Ljava/util/Map;

    .line 133
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInstalledServices:Ljava/util/List;

    .line 135
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServices:Ljava/util/Set;

    .line 137
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGrantedServices:Ljava/util/Set;

    .line 139
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;

    .line 142
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    .line 144
    new-instance v0, Landroid/text/TextUtils$SimpleStringSplitter;

    const/16 v1, 0x3a

    invoke-direct {v0, v1}, Landroid/text/TextUtils$SimpleStringSplitter;-><init>(C)V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mStringColonSplitter:Landroid/text/TextUtils$SimpleStringSplitter;

    .line 148
    const/4 v0, 0x0

    iput v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHandledFeedbackTypes:I

    .line 156
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServicesForFeedbackTempList:Ljava/util/List;

    .line 182
    iput-object p1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    .line 183
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    .line 184
    const-string v0, "window"

    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    check-cast v0, Lcom/android/server/wm/WindowManagerService;

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowManagerService:Lcom/android/server/wm/WindowManagerService;

    .line 186
    new-instance v0, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    invoke-direct {v0, p0}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;)V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    .line 187
    new-instance v0, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$1;)V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    .line 188
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->registerPackageChangeAndBootCompletedBroadcastReceiver()V

    .line 189
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->registerSettingsContentObservers()V

    .line 190
    return-void
.end method

.method static synthetic access$100(Lcom/android/server/accessibility/AccessibilityManagerService;)Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->handleTouchExplorationEnabledSettingChangedLocked()V

    return-void
.end method

.method static synthetic access$102(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$Service;)Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    iput-object p1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    return-object p1
.end method

.method static synthetic access$1100(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->updateInputFilterLocked()V

    return-void
.end method

.method static synthetic access$1200(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->sendStateToClientsLocked()V

    return-void
.end method

.method static synthetic access$1300(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->unbindAllServicesLocked()V

    return-void
.end method

.method static synthetic access$1700(Lcom/android/server/accessibility/AccessibilityManagerService;I)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->removeAccessibilityInteractionConnectionLocked(I)V

    return-void
.end method

.method static synthetic access$1800(Lcom/android/server/accessibility/AccessibilityManagerService;)Z
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    return v0
.end method

.method static synthetic access$1900(Lcom/android/server/accessibility/AccessibilityManagerService;)Landroid/app/AlertDialog;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnableTouchExplorationDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$1902(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    iput-object p1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnableTouchExplorationDialog:Landroid/app/AlertDialog;

    return-object p1
.end method

.method static synthetic access$200(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->populateAccessibilityServiceListLocked()V

    return-void
.end method

.method static synthetic access$2000(Lcom/android/server/accessibility/AccessibilityManagerService;)Z
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    return v0
.end method

.method static synthetic access$2100(Lcom/android/server/accessibility/AccessibilityManagerService;)Lcom/android/server/accessibility/AccessibilityInputFilter;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

    return-object v0
.end method

.method static synthetic access$2200(Lcom/android/server/accessibility/AccessibilityManagerService;)Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    return-object v0
.end method

.method static synthetic access$2508()I
    .registers 2

    .prologue
    .line 99
    sget v0, Lcom/android/server/accessibility/AccessibilityManagerService;->sIdCounter:I

    add-int/lit8 v1, v0, 0x1

    sput v1, Lcom/android/server/accessibility/AccessibilityManagerService;->sIdCounter:I

    return v0
.end method

.method static synthetic access$2600(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryEnableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V

    return-void
.end method

.method static synthetic access$2700(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryDisableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V

    return-void
.end method

.method static synthetic access$2800(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$Service;)Z
    .registers 3
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryRemoveServiceLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)Z

    move-result v0

    return v0
.end method

.method static synthetic access$2900(Lcom/android/server/accessibility/AccessibilityManagerService;Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 99
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryAddServiceLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V

    return-void
.end method

.method static synthetic access$300(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->manageServicesLocked()V

    return-void
.end method

.method static synthetic access$3000(Lcom/android/server/accessibility/AccessibilityManagerService;)Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    return-object v0
.end method

.method static synthetic access$3100(Lcom/android/server/accessibility/AccessibilityManagerService;)Landroid/util/SparseArray;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    return-object v0
.end method

.method static synthetic access$3200(Lcom/android/server/accessibility/AccessibilityManagerService;)Lcom/android/server/wm/WindowManagerService;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowManagerService:Lcom/android/server/wm/WindowManagerService;

    return-object v0
.end method

.method static synthetic access$3300(Lcom/android/server/accessibility/AccessibilityManagerService;)Landroid/util/SparseArray;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;

    return-object v0
.end method

.method static synthetic access$3500()I
    .registers 1

    .prologue
    .line 99
    sget v0, Lcom/android/server/accessibility/AccessibilityManagerService;->OWN_PROCESS_ID:I

    return v0
.end method

.method static synthetic access$400(Lcom/android/server/accessibility/AccessibilityManagerService;)Ljava/util/Set;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServices:Ljava/util/Set;

    return-object v0
.end method

.method static synthetic access$500(Lcom/android/server/accessibility/AccessibilityManagerService;Ljava/lang/String;Ljava/util/Set;)V
    .registers 3
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 99
    invoke-direct {p0, p1, p2}, Lcom/android/server/accessibility/AccessibilityManagerService;->persistComponentNamesToSettingLocked(Ljava/lang/String;Ljava/util/Set;)V

    return-void
.end method

.method static synthetic access$600(Lcom/android/server/accessibility/AccessibilityManagerService;)Ljava/util/Set;
    .registers 2
    .parameter "x0"

    .prologue
    .line 99
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGrantedServices:Ljava/util/Set;

    return-object v0
.end method

.method static synthetic access$700(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->populateEnabledAccessibilityServicesLocked()V

    return-void
.end method

.method static synthetic access$800(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->populateTouchExplorationGrantedAccessibilityServicesLocked()V

    return-void
.end method

.method static synthetic access$900(Lcom/android/server/accessibility/AccessibilityManagerService;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 99
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->handleAccessibilityEnabledSettingChangedLocked()V

    return-void
.end method

.method private canDispathEventLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;Landroid/view/accessibility/AccessibilityEvent;I)Z
    .registers 10
    .parameter "service"
    .parameter "event"
    .parameter "handledFeedbackTypes"

    .prologue
    const/4 v4, 0x0

    .line 779
    invoke-virtual {p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->isConfigured()Z

    move-result v5

    if-nez v5, :cond_8

    .line 804
    :cond_7
    :goto_7
    return v4

    .line 783
    :cond_8
    invoke-virtual {p2}, Landroid/view/accessibility/AccessibilityEvent;->isImportantForAccessibility()Z

    move-result v5

    if-nez v5, :cond_12

    iget-boolean v5, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mIncludeNotImportantViews:Z

    if-eqz v5, :cond_7

    .line 788
    :cond_12
    invoke-virtual {p2}, Landroid/view/accessibility/AccessibilityEvent;->getEventType()I

    move-result v0

    .line 789
    .local v0, eventType:I
    iget v5, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mEventTypes:I

    and-int/2addr v5, v0

    if-ne v5, v0, :cond_7

    .line 793
    iget-object v3, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mPackageNames:Ljava/util/Set;

    .line 794
    .local v3, packageNames:Ljava/util/Set;,"Ljava/util/Set<Ljava/lang/String;>;"
    invoke-virtual {p2}, Landroid/view/accessibility/AccessibilityEvent;->getPackageName()Ljava/lang/CharSequence;

    move-result-object v2

    .line 796
    .local v2, packageName:Ljava/lang/CharSequence;
    invoke-interface {v3}, Ljava/util/Set;->isEmpty()Z

    move-result v5

    if-nez v5, :cond_2d

    invoke-interface {v3, v2}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_7

    .line 797
    :cond_2d
    iget v1, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mFeedbackType:I

    .line 798
    .local v1, feedbackType:I
    and-int v5, p3, v1

    if-ne v5, v1, :cond_37

    const/16 v5, 0x10

    if-ne v1, v5, :cond_7

    .line 800
    :cond_37
    const/4 v4, 0x1

    goto :goto_7
.end method

.method private getQueryBridge()Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .registers 5

    .prologue
    .line 600
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mQueryBridge:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    if-nez v1, :cond_12

    .line 601
    new-instance v0, Landroid/accessibilityservice/AccessibilityServiceInfo;

    invoke-direct {v0}, Landroid/accessibilityservice/AccessibilityServiceInfo;-><init>()V

    .line 602
    .local v0, info:Landroid/accessibilityservice/AccessibilityServiceInfo;
    new-instance v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    const/4 v2, 0x0

    const/4 v3, 0x1

    invoke-direct {v1, p0, v2, v0, v3}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/content/ComponentName;Landroid/accessibilityservice/AccessibilityServiceInfo;Z)V

    iput-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mQueryBridge:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 604
    .end local v0           #info:Landroid/accessibilityservice/AccessibilityServiceInfo;
    :cond_12
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mQueryBridge:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    return-object v1
.end method

.method private getState()I
    .registers 3

    .prologue
    .line 949
    const/4 v0, 0x0

    .line 950
    .local v0, state:I
    iget-boolean v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-eqz v1, :cond_7

    .line 951
    or-int/lit8 v0, v0, 0x1

    .line 954
    :cond_7
    iget-boolean v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-eqz v1, :cond_11

    iget-boolean v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    if-eqz v1, :cond_11

    .line 955
    or-int/lit8 v0, v0, 0x2

    .line 957
    :cond_11
    return v0
.end method

.method private handleAccessibilityEnabledSettingChangedLocked()V
    .registers 5

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    .line 984
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string v3, "accessibility_enabled"

    invoke-static {v2, v3, v1}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    if-ne v2, v0, :cond_1a

    :goto_10
    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    .line 987
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-eqz v0, :cond_1c

    .line 988
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->manageServicesLocked()V

    .line 992
    :goto_19
    return-void

    :cond_1a
    move v0, v1

    .line 984
    goto :goto_10

    .line 990
    :cond_1c
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->unbindAllServicesLocked()V

    goto :goto_19
.end method

.method private handleTouchExplorationEnabledSettingChangedLocked()V
    .registers 5

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    .line 998
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string v3, "touch_exploration_enabled"

    invoke-static {v2, v3, v1}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v2

    if-ne v2, v0, :cond_13

    :goto_10
    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    .line 1001
    return-void

    :cond_13
    move v0, v1

    .line 998
    goto :goto_10
.end method

.method private manageServicesLocked()V
    .registers 5

    .prologue
    .line 811
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInstalledServices:Ljava/util/List;

    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServices:Ljava/util/Set;

    invoke-direct {p0, v1, v2}, Lcom/android/server/accessibility/AccessibilityManagerService;->updateServicesStateLocked(Ljava/util/List;Ljava/util/Set;)I

    move-result v0

    .line 815
    .local v0, enabledInstalledServicesCount:I
    iget-boolean v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-eqz v1, :cond_1a

    if-nez v0, :cond_1a

    .line 816
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "accessibility_enabled"

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 819
    :cond_1a
    return-void
.end method

.method private notifyAccessibilityServicesDelayedLocked(Landroid/view/accessibility/AccessibilityEvent;Z)V
    .registers 9
    .parameter "event"
    .parameter "isDefault"

    .prologue
    .line 706
    const/4 v1, 0x0

    .local v1, i:I
    :try_start_1
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v0

    .local v0, count:I
    :goto_7
    if-ge v1, v0, :cond_2b

    .line 707
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 709
    .local v3, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    iget-boolean v4, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mIsDefault:Z

    if-ne v4, p2, :cond_27

    .line 710
    iget v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHandledFeedbackTypes:I

    invoke-direct {p0, v3, p1, v4}, Lcom/android/server/accessibility/AccessibilityManagerService;->canDispathEventLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;Landroid/view/accessibility/AccessibilityEvent;I)Z

    move-result v4

    if-eqz v4, :cond_27

    .line 711
    iget v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHandledFeedbackTypes:I

    iget v5, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mFeedbackType:I

    or-int/2addr v4, v5

    iput v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHandledFeedbackTypes:I

    .line 712
    invoke-virtual {v3, p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->notifyAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)V
    :try_end_27
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_1 .. :try_end_27} :catch_2a

    .line 706
    :cond_27
    add-int/lit8 v1, v1, 0x1

    goto :goto_7

    .line 716
    .end local v0           #count:I
    .end local v3           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :catch_2a
    move-exception v2

    .line 722
    :cond_2b
    return-void
.end method

.method private notifyGestureLocked(IZ)Z
    .registers 6
    .parameter "gestureId"
    .parameter "isDefault"

    .prologue
    .line 625
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    add-int/lit8 v0, v2, -0x1

    .local v0, i:I
    :goto_8
    if-ltz v0, :cond_22

    .line 626
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 627
    .local v1, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    iget-boolean v2, v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mReqeustTouchExplorationMode:Z

    if-eqz v2, :cond_1f

    iget-boolean v2, v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mIsDefault:Z

    if-ne v2, p2, :cond_1f

    .line 628
    invoke-virtual {v1, p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->notifyGesture(I)V

    .line 629
    const/4 v2, 0x1

    .line 632
    .end local v1           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :goto_1e
    return v2

    .line 625
    .restart local v1       #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_1f
    add-int/lit8 v0, v0, -0x1

    goto :goto_8

    .line 632
    .end local v1           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_22
    const/4 v2, 0x0

    goto :goto_1e
.end method

.method private persistComponentNamesToSettingLocked(Ljava/lang/String;Ljava/util/Set;)V
    .registers 8
    .parameter "settingName"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/util/Set",
            "<",
            "Landroid/content/ComponentName;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 874
    .local p2, componentNames:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/ComponentName;>;"
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 875
    .local v0, builder:Ljava/lang/StringBuilder;
    invoke-interface {p2}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :goto_9
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_28

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/ComponentName;

    .line 876
    .local v1, componentName:Landroid/content/ComponentName;
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->length()I

    move-result v3

    if-lez v3, :cond_20

    .line 877
    const/16 v3, 0x3a

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 879
    :cond_20
    invoke-virtual {v1}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_9

    .line 881
    .end local v1           #componentName:Landroid/content/ComponentName;
    :cond_28
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, p1, v4}, Landroid/provider/Settings$Secure;->putString(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;)Z

    .line 882
    return-void
.end method

.method private populateAccessibilityServiceListLocked()V
    .registers 14

    .prologue
    .line 652
    iget-object v8, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInstalledServices:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->clear()V

    .line 654
    iget-object v8, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mPackageManager:Landroid/content/pm/PackageManager;

    new-instance v9, Landroid/content/Intent;

    const-string v10, "android.accessibilityservice.AccessibilityService"

    invoke-direct {v9, v10}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const/16 v10, 0x84

    invoke-virtual {v8, v9, v10}, Landroid/content/pm/PackageManager;->queryIntentServices(Landroid/content/Intent;I)Ljava/util/List;

    move-result-object v3

    .line 658
    .local v3, installedServices:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    const/4 v2, 0x0

    .local v2, i:I
    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v1

    .local v1, count:I
    :goto_19
    if-ge v2, v1, :cond_88

    .line 659
    invoke-interface {v3, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/content/pm/ResolveInfo;

    .line 660
    .local v5, resolveInfo:Landroid/content/pm/ResolveInfo;
    iget-object v6, v5, Landroid/content/pm/ResolveInfo;->serviceInfo:Landroid/content/pm/ServiceInfo;

    .line 663
    .local v6, serviceInfo:Landroid/content/pm/ServiceInfo;
    iget-object v8, v6, Landroid/content/pm/ServiceInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v8, v8, Landroid/content/pm/ApplicationInfo;->targetSdkVersion:I

    const/16 v9, 0x10

    if-lt v8, v9, :cond_69

    const-string v8, "android.permission.BIND_ACCESSIBILITY_SERVICE"

    iget-object v9, v6, Landroid/content/pm/ServiceInfo;->permission:Ljava/lang/String;

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-nez v8, :cond_69

    .line 666
    const-string v8, "AccessibilityManagerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Skipping accessibilty service "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    new-instance v10, Landroid/content/ComponentName;

    iget-object v11, v6, Landroid/content/pm/ComponentInfo;->packageName:Ljava/lang/String;

    iget-object v12, v6, Landroid/content/pm/ComponentInfo;->name:Ljava/lang/String;

    invoke-direct {v10, v11, v12}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v10}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, ": it does not require the permission "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "android.permission.BIND_ACCESSIBILITY_SERVICE"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 658
    :goto_66
    add-int/lit8 v2, v2, 0x1

    goto :goto_19

    .line 674
    :cond_69
    :try_start_69
    new-instance v0, Landroid/accessibilityservice/AccessibilityServiceInfo;

    iget-object v8, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-direct {v0, v5, v8}, Landroid/accessibilityservice/AccessibilityServiceInfo;-><init>(Landroid/content/pm/ResolveInfo;Landroid/content/Context;)V

    .line 675
    .local v0, accessibilityServiceInfo:Landroid/accessibilityservice/AccessibilityServiceInfo;
    iget-object v8, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInstalledServices:Ljava/util/List;

    invoke-interface {v8, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_75
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_69 .. :try_end_75} :catch_76
    .catch Ljava/io/IOException; {:try_start_69 .. :try_end_75} :catch_7f

    goto :goto_66

    .line 676
    .end local v0           #accessibilityServiceInfo:Landroid/accessibilityservice/AccessibilityServiceInfo;
    :catch_76
    move-exception v7

    .line 677
    .local v7, xppe:Lorg/xmlpull/v1/XmlPullParserException;
    const-string v8, "AccessibilityManagerService"

    const-string v9, "Error while initializing AccessibilityServiceInfo"

    invoke-static {v8, v9, v7}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_66

    .line 678
    .end local v7           #xppe:Lorg/xmlpull/v1/XmlPullParserException;
    :catch_7f
    move-exception v4

    .line 679
    .local v4, ioe:Ljava/io/IOException;
    const-string v8, "AccessibilityManagerService"

    const-string v9, "Error while initializing AccessibilityServiceInfo"

    invoke-static {v8, v9, v4}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_66

    .line 682
    .end local v4           #ioe:Ljava/io/IOException;
    .end local v5           #resolveInfo:Landroid/content/pm/ResolveInfo;
    .end local v6           #serviceInfo:Landroid/content/pm/ServiceInfo;
    :cond_88
    return-void
.end method

.method private populateComponentNamesFromSettingLocked(Ljava/lang/String;Ljava/util/Set;)V
    .registers 8
    .parameter "settingName"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/util/Set",
            "<",
            "Landroid/content/ComponentName;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 845
    .local p2, outComponentNames:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/ComponentName;>;"
    invoke-interface {p2}, Ljava/util/Set;->clear()V

    .line 847
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    invoke-static {v4, p1}, Landroid/provider/Settings$Secure;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 849
    .local v1, settingValue:Ljava/lang/String;
    if-eqz v1, :cond_30

    .line 850
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mStringColonSplitter:Landroid/text/TextUtils$SimpleStringSplitter;

    .line 851
    .local v2, splitter:Landroid/text/TextUtils$SimpleStringSplitter;
    invoke-virtual {v2, v1}, Landroid/text/TextUtils$SimpleStringSplitter;->setString(Ljava/lang/String;)V

    .line 852
    :cond_14
    :goto_14
    invoke-virtual {v2}, Landroid/text/TextUtils$SimpleStringSplitter;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_30

    .line 853
    invoke-virtual {v2}, Landroid/text/TextUtils$SimpleStringSplitter;->next()Ljava/lang/String;

    move-result-object v3

    .line 854
    .local v3, str:Ljava/lang/String;
    if-eqz v3, :cond_14

    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v4

    if-lez v4, :cond_14

    .line 857
    invoke-static {v3}, Landroid/content/ComponentName;->unflattenFromString(Ljava/lang/String;)Landroid/content/ComponentName;

    move-result-object v0

    .line 858
    .local v0, enabledService:Landroid/content/ComponentName;
    if-eqz v0, :cond_14

    .line 859
    invoke-interface {p2, v0}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    goto :goto_14

    .line 863
    .end local v0           #enabledService:Landroid/content/ComponentName;
    .end local v2           #splitter:Landroid/text/TextUtils$SimpleStringSplitter;
    .end local v3           #str:Ljava/lang/String;
    :cond_30
    return-void
.end method

.method private populateEnabledAccessibilityServicesLocked()V
    .registers 3

    .prologue
    .line 685
    const-string v0, "enabled_accessibility_services"

    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServices:Ljava/util/Set;

    invoke-direct {p0, v0, v1}, Lcom/android/server/accessibility/AccessibilityManagerService;->populateComponentNamesFromSettingLocked(Ljava/lang/String;Ljava/util/Set;)V

    .line 688
    return-void
.end method

.method private populateTouchExplorationGrantedAccessibilityServicesLocked()V
    .registers 3

    .prologue
    .line 691
    const-string v0, "touch_exploration_granted_accessibility_services"

    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGrantedServices:Ljava/util/Set;

    invoke-direct {p0, v0, v1}, Lcom/android/server/accessibility/AccessibilityManagerService;->populateComponentNamesFromSettingLocked(Ljava/lang/String;Ljava/util/Set;)V

    .line 694
    return-void
.end method

.method private registerPackageChangeAndBootCompletedBroadcastReceiver()V
    .registers 7

    .prologue
    const/4 v5, 0x0

    .line 197
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    .line 199
    .local v1, context:Landroid/content/Context;
    new-instance v2, Lcom/android/server/accessibility/AccessibilityManagerService$1;

    invoke-direct {v2, p0}, Lcom/android/server/accessibility/AccessibilityManagerService$1;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;)V

    .line 283
    .local v2, monitor:Lcom/android/internal/content/PackageMonitor;
    const/4 v3, 0x1

    invoke-virtual {v2, v1, v5, v3}, Lcom/android/internal/content/PackageMonitor;->register(Landroid/content/Context;Landroid/os/Looper;Z)V

    .line 286
    new-instance v0, Landroid/content/IntentFilter;

    const-string v3, "android.intent.action.BOOT_COMPLETED"

    invoke-direct {v0, v3}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 287
    .local v0, bootFiler:Landroid/content/IntentFilter;
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Lcom/android/internal/content/PackageMonitor;->getRegisteredHandler()Landroid/os/Handler;

    move-result-object v4

    invoke-virtual {v3, v2, v0, v5, v4}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;

    .line 288
    return-void
.end method

.method private registerSettingsContentObservers()V
    .registers 9

    .prologue
    const/4 v7, 0x0

    .line 295
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    .line 297
    .local v2, contentResolver:Landroid/content/ContentResolver;
    const-string v5, "accessibility_enabled"

    invoke-static {v5}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    .line 299
    .local v0, accessibilityEnabledUri:Landroid/net/Uri;
    new-instance v5, Lcom/android/server/accessibility/AccessibilityManagerService$2;

    new-instance v6, Landroid/os/Handler;

    invoke-direct {v6}, Landroid/os/Handler;-><init>()V

    invoke-direct {v5, p0, v6}, Lcom/android/server/accessibility/AccessibilityManagerService$2;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/os/Handler;)V

    invoke-virtual {v2, v0, v7, v5}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 315
    const-string v5, "touch_exploration_enabled"

    invoke-static {v5}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v4

    .line 317
    .local v4, touchExplorationRequestedUri:Landroid/net/Uri;
    new-instance v5, Lcom/android/server/accessibility/AccessibilityManagerService$3;

    new-instance v6, Landroid/os/Handler;

    invoke-direct {v6}, Landroid/os/Handler;-><init>()V

    invoke-direct {v5, p0, v6}, Lcom/android/server/accessibility/AccessibilityManagerService$3;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/os/Handler;)V

    invoke-virtual {v2, v4, v7, v5}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 333
    const-string v5, "enabled_accessibility_services"

    invoke-static {v5}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    .line 335
    .local v1, accessibilityServicesUri:Landroid/net/Uri;
    new-instance v5, Lcom/android/server/accessibility/AccessibilityManagerService$4;

    new-instance v6, Landroid/os/Handler;

    invoke-direct {v6}, Landroid/os/Handler;-><init>()V

    invoke-direct {v5, p0, v6}, Lcom/android/server/accessibility/AccessibilityManagerService$4;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/os/Handler;)V

    invoke-virtual {v2, v1, v7, v5}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 350
    const-string v5, "touch_exploration_granted_accessibility_services"

    invoke-static {v5}, Landroid/provider/Settings$Secure;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v3

    .line 352
    .local v3, touchExplorationGrantedServicesUri:Landroid/net/Uri;
    new-instance v5, Lcom/android/server/accessibility/AccessibilityManagerService$5;

    new-instance v6, Landroid/os/Handler;

    invoke-direct {v6}, Landroid/os/Handler;-><init>()V

    invoke-direct {v5, p0, v6}, Lcom/android/server/accessibility/AccessibilityManagerService$5;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/os/Handler;)V

    invoke-virtual {v2, v3, v7, v5}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 367
    return-void
.end method

.method private removeAccessibilityInteractionConnectionLocked(I)V
    .registers 3
    .parameter "windowId"

    .prologue
    .line 641
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->remove(I)V

    .line 642
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->remove(I)V

    .line 646
    return-void
.end method

.method private sendStateToClientsLocked()V
    .registers 6

    .prologue
    .line 931
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->getState()I

    move-result v3

    .line 932
    .local v3, state:I
    const/4 v1, 0x0

    .local v1, i:I
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mClients:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v0

    .local v0, count:I
    :goto_b
    if-ge v1, v0, :cond_26

    .line 934
    :try_start_d
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mClients:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/view/accessibility/IAccessibilityManagerClient;

    invoke-interface {v4, v3}, Landroid/view/accessibility/IAccessibilityManagerClient;->setState(I)V
    :try_end_18
    .catch Landroid/os/RemoteException; {:try_start_d .. :try_end_18} :catch_1b

    .line 932
    :goto_18
    add-int/lit8 v1, v1, 0x1

    goto :goto_b

    .line 935
    :catch_1b
    move-exception v2

    .line 936
    .local v2, re:Landroid/os/RemoteException;
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mClients:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    .line 937
    add-int/lit8 v0, v0, -0x1

    .line 938
    add-int/lit8 v1, v1, -0x1

    goto :goto_18

    .line 941
    .end local v2           #re:Landroid/os/RemoteException;
    :cond_26
    return-void
.end method

.method private tryAddServiceLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 4
    .parameter "service"

    .prologue
    .line 731
    :try_start_0
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_e

    invoke-virtual {p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->isConfigured()Z

    move-result v0

    if-nez v0, :cond_f

    .line 742
    :cond_e
    :goto_e
    return-void

    .line 734
    :cond_f
    invoke-virtual {p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->linkToOwnDeath()V

    .line 735
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 736
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mComponentNameToServiceMap:Ljava/util/Map;

    iget-object v1, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mComponentName:Landroid/content/ComponentName;

    invoke-interface {v0, v1, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 737
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->updateInputFilterLocked()V

    .line 738
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryEnableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    :try_end_24
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_24} :catch_25

    goto :goto_e

    .line 739
    :catch_25
    move-exception v0

    goto :goto_e
.end method

.method private tryDisableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 10
    .parameter "service"

    .prologue
    .line 1017
    iget-boolean v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    if-eqz v3, :cond_1f

    .line 1018
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v4

    .line 1019
    :try_start_7
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v2

    .line 1020
    .local v2, serviceCount:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_e
    if-ge v0, v2, :cond_23

    .line 1021
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v3, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 1022
    .local v1, other:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    if-eq v1, p1, :cond_20

    iget-boolean v3, v1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mRequestTouchExplorationMode:Z

    if-eqz v3, :cond_20

    .line 1023
    monitor-exit v4

    .line 1029
    .end local v0           #i:I
    .end local v1           #other:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .end local v2           #serviceCount:I
    :cond_1f
    :goto_1f
    return-void

    .line 1020
    .restart local v0       #i:I
    .restart local v1       #other:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .restart local v2       #serviceCount:I
    :cond_20
    add-int/lit8 v0, v0, 0x1

    goto :goto_e

    .line 1026
    .end local v1           #other:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_23
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v7, 0x0

    invoke-virtual {v3, v5, v6, v7}, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v3

    invoke-virtual {v3}, Landroid/os/Message;->sendToTarget()V

    .line 1027
    monitor-exit v4

    goto :goto_1f

    .end local v0           #i:I
    .end local v2           #serviceCount:I
    :catchall_31
    move-exception v3

    monitor-exit v4
    :try_end_33
    .catchall {:try_start_7 .. :try_end_33} :catchall_31

    throw v3
.end method

.method private tryEnableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V
    .registers 7
    .parameter "service"

    .prologue
    const/4 v4, 0x1

    .line 1004
    iget-boolean v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    if-nez v1, :cond_20

    iget-boolean v1, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mRequestTouchExplorationMode:Z

    if-eqz v1, :cond_20

    .line 1005
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGrantedServices:Ljava/util/Set;

    iget-object v2, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mComponentName:Landroid/content/ComponentName;

    invoke-interface {v1, v2}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v0

    .line 1007
    .local v0, canToggleTouchExploration:Z
    iget-boolean v1, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mIsAutomation:Z

    if-nez v1, :cond_21

    if-nez v0, :cond_21

    .line 1008
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    invoke-virtual {v1, v4, p1}, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 1014
    .end local v0           #canToggleTouchExploration:Z
    :cond_20
    :goto_20
    return-void

    .line 1011
    .restart local v0       #canToggleTouchExploration:Z
    :cond_21
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    const/4 v2, 0x2

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v4, v3}, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    goto :goto_20
.end method

.method private tryRemoveServiceLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)Z
    .registers 5
    .parameter "service"

    .prologue
    .line 751
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    move-result v0

    .line 752
    .local v0, removed:Z
    if-nez v0, :cond_a

    .line 753
    const/4 v0, 0x0

    .line 760
    .end local v0           #removed:Z
    :goto_9
    return v0

    .line 755
    .restart local v0       #removed:Z
    :cond_a
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mComponentNameToServiceMap:Ljava/util/Map;

    iget-object v2, p1, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mComponentName:Landroid/content/ComponentName;

    invoke-interface {v1, v2}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 756
    invoke-virtual {p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->unlinkToOwnDeath()V

    .line 757
    invoke-virtual {p1}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->dispose()V

    .line 758
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->updateInputFilterLocked()V

    .line 759
    invoke-direct {p0, p1}, Lcom/android/server/accessibility/AccessibilityManagerService;->tryDisableTouchExplorationLocked(Lcom/android/server/accessibility/AccessibilityManagerService$Service;)V

    goto :goto_9
.end method

.method private unbindAllServicesLocked()V
    .registers 6

    .prologue
    .line 825
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    .line 827
    .local v3, services:Ljava/util/List;,"Ljava/util/List<Lcom/android/server/accessibility/AccessibilityManagerService$Service;>;"
    const/4 v1, 0x0

    .local v1, i:I
    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v0

    .local v0, count:I
    :goto_7
    if-ge v1, v0, :cond_1c

    .line 828
    invoke-interface {v3, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 829
    .local v2, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    invoke-virtual {v2}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->unbind()Z

    move-result v4

    if-eqz v4, :cond_19

    .line 830
    add-int/lit8 v1, v1, -0x1

    .line 831
    add-int/lit8 v0, v0, -0x1

    .line 827
    :cond_19
    add-int/lit8 v1, v1, 0x1

    goto :goto_7

    .line 834
    .end local v2           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_1c
    return-void
.end method

.method private updateInputFilterLocked()V
    .registers 3

    .prologue
    .line 964
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-eqz v0, :cond_24

    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsTouchExplorationEnabled:Z

    if-eqz v0, :cond_24

    .line 965
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    if-nez v0, :cond_23

    .line 966
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    .line 967
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

    if-nez v0, :cond_1c

    .line 968
    new-instance v0, Lcom/android/server/accessibility/AccessibilityInputFilter;

    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1, p0}, Lcom/android/server/accessibility/AccessibilityInputFilter;-><init>(Landroid/content/Context;Lcom/android/server/accessibility/AccessibilityManagerService;)V

    iput-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

    .line 970
    :cond_1c
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowManagerService:Lcom/android/server/wm/WindowManagerService;

    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

    invoke-virtual {v0, v1}, Lcom/android/server/wm/WindowManagerService;->setInputFilter(Lcom/android/server/input/InputFilter;)V

    .line 978
    :cond_23
    :goto_23
    return-void

    .line 974
    :cond_24
    iget-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    if-eqz v0, :cond_23

    .line 975
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    .line 976
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowManagerService:Lcom/android/server/wm/WindowManagerService;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/server/wm/WindowManagerService;->setInputFilter(Lcom/android/server/input/InputFilter;)V

    goto :goto_23
.end method

.method private updateServicesStateLocked(Ljava/util/List;Ljava/util/Set;)I
    .registers 12
    .parameter
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List",
            "<",
            "Landroid/accessibilityservice/AccessibilityServiceInfo;",
            ">;",
            "Ljava/util/Set",
            "<",
            "Landroid/content/ComponentName;",
            ">;)I"
        }
    .end annotation

    .prologue
    .line 895
    .local p1, installedServices:Ljava/util/List;,"Ljava/util/List<Landroid/accessibilityservice/AccessibilityServiceInfo;>;"
    .local p2, enabledServices:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/ComponentName;>;"
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mComponentNameToServiceMap:Ljava/util/Map;

    .line 896
    .local v1, componentNameToServiceMap:Ljava/util/Map;,"Ljava/util/Map<Landroid/content/ComponentName;Lcom/android/server/accessibility/AccessibilityManagerService$Service;>;"
    iget-boolean v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    .line 898
    .local v6, isEnabled:Z
    const/4 v3, 0x0

    .line 899
    .local v3, enabledInstalledServices:I
    const/4 v4, 0x0

    .local v4, i:I
    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v2

    .local v2, count:I
    :goto_a
    if-ge v4, v2, :cond_44

    .line 900
    invoke-interface {p1, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/accessibilityservice/AccessibilityServiceInfo;

    .line 901
    .local v5, installedService:Landroid/accessibilityservice/AccessibilityServiceInfo;
    invoke-virtual {v5}, Landroid/accessibilityservice/AccessibilityServiceInfo;->getId()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Landroid/content/ComponentName;->unflattenFromString(Ljava/lang/String;)Landroid/content/ComponentName;

    move-result-object v0

    .line 903
    .local v0, componentName:Landroid/content/ComponentName;
    invoke-interface {v1, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 905
    .local v7, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    if-eqz v6, :cond_3e

    .line 906
    invoke-interface {p2, v0}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_38

    .line 907
    if-nez v7, :cond_30

    .line 908
    new-instance v7, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .end local v7           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    const/4 v8, 0x0

    invoke-direct {v7, p0, v0, v5, v8}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/content/ComponentName;Landroid/accessibilityservice/AccessibilityServiceInfo;Z)V

    .line 910
    .restart local v7       #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_30
    invoke-virtual {v7}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->bind()Z

    .line 911
    add-int/lit8 v3, v3, 0x1

    .line 899
    :cond_35
    :goto_35
    add-int/lit8 v4, v4, 0x1

    goto :goto_a

    .line 913
    :cond_38
    if-eqz v7, :cond_35

    .line 914
    invoke-virtual {v7}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->unbind()Z

    goto :goto_35

    .line 918
    :cond_3e
    if-eqz v7, :cond_35

    .line 919
    invoke-virtual {v7}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->unbind()Z

    goto :goto_35

    .line 924
    .end local v0           #componentName:Landroid/content/ComponentName;
    .end local v5           #installedService:Landroid/accessibilityservice/AccessibilityServiceInfo;
    .end local v7           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_44
    return v3
.end method


# virtual methods
.method public addAccessibilityInteractionConnection(Landroid/view/IWindow;Landroid/view/accessibility/IAccessibilityInteractionConnection;)I
    .registers 9
    .parameter "windowToken"
    .parameter "connection"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .prologue
    .line 470
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v4

    .line 471
    move-object v0, p1

    .line 472
    .local v0, addedWindowToken:Landroid/view/IWindow;
    :try_start_4
    sget v1, Lcom/android/server/accessibility/AccessibilityManagerService;->sNextWindowId:I

    add-int/lit8 v3, v1, 0x1

    sput v3, Lcom/android/server/accessibility/AccessibilityManagerService;->sNextWindowId:I

    .line 473
    .local v1, windowId:I
    new-instance v2, Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;

    invoke-direct {v2, p0, v1, p2}, Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;ILandroid/view/accessibility/IAccessibilityInteractionConnection;)V

    .line 475
    .local v2, wrapper:Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;
    invoke-virtual {v2}, Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;->linkToDeath()V

    .line 476
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-interface {v0}, Landroid/view/IWindow;->asBinder()Landroid/os/IBinder;

    move-result-object v5

    invoke-virtual {v3, v1, v5}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 477
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;

    invoke-virtual {v3, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 481
    monitor-exit v4

    return v1

    .line 482
    .end local v1           #windowId:I
    .end local v2           #wrapper:Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;
    :catchall_22
    move-exception v3

    monitor-exit v4
    :try_end_24
    .catchall {:try_start_4 .. :try_end_24} :catchall_22

    throw v3
.end method

.method public addClient(Landroid/view/accessibility/IAccessibilityManagerClient;)I
    .registers 7
    .parameter "client"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .prologue
    .line 370
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v2

    .line 371
    move-object v0, p1

    .line 372
    .local v0, addedClient:Landroid/view/accessibility/IAccessibilityManagerClient;
    :try_start_4
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mClients:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 375
    invoke-interface {p1}, Landroid/view/accessibility/IAccessibilityManagerClient;->asBinder()Landroid/os/IBinder;

    move-result-object v1

    new-instance v3, Lcom/android/server/accessibility/AccessibilityManagerService$6;

    invoke-direct {v3, p0, v0}, Lcom/android/server/accessibility/AccessibilityManagerService$6;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/view/accessibility/IAccessibilityManagerClient;)V

    const/4 v4, 0x0

    invoke-interface {v1, v3, v4}, Landroid/os/IBinder;->linkToDeath(Landroid/os/IBinder$DeathRecipient;I)V

    .line 383
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->getState()I

    move-result v1

    monitor-exit v2

    return v1

    .line 384
    :catchall_1c
    move-exception v1

    monitor-exit v2
    :try_end_1e
    .catchall {:try_start_4 .. :try_end_1e} :catchall_1c

    throw v1
.end method

.method getAccessibilityFocusBoundsInActiveWindow(Landroid/graphics/Rect;)Z
    .registers 9
    .parameter "outBounds"

    .prologue
    const/4 v5, 0x0

    .line 560
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->getQueryBridge()Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    move-result-object v4

    .line 561
    .local v4, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    iget v1, v4, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mId:I

    .line 562
    .local v1, connectionId:I
    invoke-static {}, Landroid/view/accessibility/AccessibilityInteractionClient;->getInstance()Landroid/view/accessibility/AccessibilityInteractionClient;

    move-result-object v0

    .line 563
    .local v0, client:Landroid/view/accessibility/AccessibilityInteractionClient;
    invoke-virtual {v0, v1, v4}, Landroid/view/accessibility/AccessibilityInteractionClient;->addConnection(ILandroid/accessibilityservice/IAccessibilityServiceConnection;)V

    .line 565
    :try_start_e
    invoke-static {}, Landroid/view/accessibility/AccessibilityInteractionClient;->getInstance()Landroid/view/accessibility/AccessibilityInteractionClient;

    move-result-object v6

    invoke-virtual {v6, v1}, Landroid/view/accessibility/AccessibilityInteractionClient;->getRootInActiveWindow(I)Landroid/view/accessibility/AccessibilityNodeInfo;
    :try_end_15
    .catchall {:try_start_e .. :try_end_15} :catchall_2f

    move-result-object v3

    .line 567
    .local v3, root:Landroid/view/accessibility/AccessibilityNodeInfo;
    if-nez v3, :cond_1c

    .line 578
    invoke-virtual {v0, v1}, Landroid/view/accessibility/AccessibilityInteractionClient;->removeConnection(I)V

    :goto_1b
    return v5

    .line 570
    :cond_1c
    const/4 v6, 0x2

    :try_start_1d
    invoke-virtual {v3, v6}, Landroid/view/accessibility/AccessibilityNodeInfo;->findFocus(I)Landroid/view/accessibility/AccessibilityNodeInfo;
    :try_end_20
    .catchall {:try_start_1d .. :try_end_20} :catchall_2f

    move-result-object v2

    .line 572
    .local v2, focus:Landroid/view/accessibility/AccessibilityNodeInfo;
    if-nez v2, :cond_27

    .line 578
    invoke-virtual {v0, v1}, Landroid/view/accessibility/AccessibilityInteractionClient;->removeConnection(I)V

    goto :goto_1b

    .line 575
    :cond_27
    :try_start_27
    invoke-virtual {v2, p1}, Landroid/view/accessibility/AccessibilityNodeInfo;->getBoundsInScreen(Landroid/graphics/Rect;)V
    :try_end_2a
    .catchall {:try_start_27 .. :try_end_2a} :catchall_2f

    .line 576
    const/4 v5, 0x1

    .line 578
    invoke-virtual {v0, v1}, Landroid/view/accessibility/AccessibilityInteractionClient;->removeConnection(I)V

    goto :goto_1b

    .end local v2           #focus:Landroid/view/accessibility/AccessibilityNodeInfo;
    .end local v3           #root:Landroid/view/accessibility/AccessibilityNodeInfo;
    :catchall_2f
    move-exception v5

    invoke-virtual {v0, v1}, Landroid/view/accessibility/AccessibilityInteractionClient;->removeConnection(I)V

    throw v5
.end method

.method getActiveWindowBounds(Landroid/graphics/Rect;)V
    .registers 6
    .parameter "outBounds"

    .prologue
    .line 588
    iget-object v3, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v3

    .line 589
    :try_start_3
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    #getter for: Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->mActiveWindowId:I
    invoke-static {v2}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->access$1600(Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;)I

    move-result v1

    .line 590
    .local v1, windowId:I
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-virtual {v2, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/IBinder;

    .line 591
    .local v0, token:Landroid/os/IBinder;
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowManagerService:Lcom/android/server/wm/WindowManagerService;

    invoke-virtual {v2, v0, p1}, Lcom/android/server/wm/WindowManagerService;->getWindowFrame(Landroid/os/IBinder;Landroid/graphics/Rect;)Z

    .line 592
    monitor-exit v3

    .line 593
    return-void

    .line 592
    .end local v0           #token:Landroid/os/IBinder;
    .end local v1           #windowId:I
    :catchall_18
    move-exception v2

    monitor-exit v3
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_18

    throw v2
.end method

.method getActiveWindowId()I
    .registers 2

    .prologue
    .line 596
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    #getter for: Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->mActiveWindowId:I
    invoke-static {v0}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->access$1600(Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;)I

    move-result v0

    return v0
.end method

.method public getEnabledAccessibilityServiceList(I)Ljava/util/List;
    .registers 11
    .parameter "feedbackType"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/List",
            "<",
            "Landroid/accessibilityservice/AccessibilityServiceInfo;",
            ">;"
        }
    .end annotation

    .prologue
    .line 435
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mEnabledServicesForFeedbackTempList:Ljava/util/List;

    .line 436
    .local v2, result:Ljava/util/List;,"Ljava/util/List<Landroid/accessibilityservice/AccessibilityServiceInfo;>;"
    invoke-interface {v2}, Ljava/util/List;->clear()V

    .line 437
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    .line 438
    .local v5, services:Ljava/util/List;,"Ljava/util/List<Lcom/android/server/accessibility/AccessibilityManagerService$Service;>;"
    iget-object v7, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v7

    .line 439
    :cond_a
    if-eqz p1, :cond_30

    .line 440
    const/4 v6, 0x1

    :try_start_d
    invoke-static {p1}, Ljava/lang/Integer;->numberOfTrailingZeros(I)I

    move-result v8

    shl-int v0, v6, v8

    .line 441
    .local v0, feedbackTypeBit:I
    xor-int/lit8 v6, v0, -0x1

    and-int/2addr p1, v6

    .line 442
    invoke-interface {v5}, Ljava/util/List;->size()I

    move-result v4

    .line 443
    .local v4, serviceCount:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_1b
    if-ge v1, v4, :cond_a

    .line 444
    invoke-interface {v5, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 445
    .local v3, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    iget v6, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mFeedbackType:I

    and-int/2addr v6, v0

    if-eqz v6, :cond_2d

    .line 446
    iget-object v6, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mAccessibilityServiceInfo:Landroid/accessibilityservice/AccessibilityServiceInfo;

    invoke-interface {v2, v6}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 443
    :cond_2d
    add-int/lit8 v1, v1, 0x1

    goto :goto_1b

    .line 450
    .end local v0           #feedbackTypeBit:I
    .end local v1           #i:I
    .end local v3           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    .end local v4           #serviceCount:I
    :cond_30
    monitor-exit v7

    .line 451
    return-object v2

    .line 450
    :catchall_32
    move-exception v6

    monitor-exit v7
    :try_end_34
    .catchall {:try_start_d .. :try_end_34} :catchall_32

    throw v6
.end method

.method public getInstalledAccessibilityServiceList()Ljava/util/List;
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List",
            "<",
            "Landroid/accessibilityservice/AccessibilityServiceInfo;",
            ">;"
        }
    .end annotation

    .prologue
    .line 429
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v1

    .line 430
    :try_start_3
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInstalledServices:Ljava/util/List;

    monitor-exit v1

    return-object v0

    .line 431
    :catchall_7
    move-exception v0

    monitor-exit v1
    :try_end_9
    .catchall {:try_start_3 .. :try_end_9} :catchall_7

    throw v0
.end method

.method public interrupt()V
    .registers 9

    .prologue
    .line 455
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v5

    .line 456
    const/4 v1, 0x0

    .local v1, i:I
    :try_start_4
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v0

    .local v0, count:I
    :goto_a
    if-ge v1, v0, :cond_3b

    .line 457
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :try_end_14
    .catchall {:try_start_4 .. :try_end_14} :catchall_38

    .line 459
    .local v3, service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :try_start_14
    iget-object v4, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mServiceInterface:Landroid/accessibilityservice/IAccessibilityServiceClient;

    invoke-interface {v4}, Landroid/accessibilityservice/IAccessibilityServiceClient;->onInterrupt()V
    :try_end_19
    .catchall {:try_start_14 .. :try_end_19} :catchall_38
    .catch Landroid/os/RemoteException; {:try_start_14 .. :try_end_19} :catch_1c

    .line 456
    :goto_19
    add-int/lit8 v1, v1, 0x1

    goto :goto_a

    .line 460
    :catch_1c
    move-exception v2

    .line 461
    .local v2, re:Landroid/os/RemoteException;
    :try_start_1d
    const-string v4, "AccessibilityManagerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Error during sending interrupt request to "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget-object v7, v3, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mService:Landroid/os/IBinder;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6, v2}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_19

    .line 465
    .end local v0           #count:I
    .end local v2           #re:Landroid/os/RemoteException;
    .end local v3           #service:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :catchall_38
    move-exception v4

    monitor-exit v5
    :try_end_3a
    .catchall {:try_start_1d .. :try_end_3a} :catchall_38

    throw v4

    .restart local v0       #count:I
    :cond_3b
    :try_start_3b
    monitor-exit v5
    :try_end_3c
    .catchall {:try_start_3b .. :try_end_3c} :catchall_38

    .line 466
    return-void
.end method

.method onGesture(I)Z
    .registers 5
    .parameter "gestureId"

    .prologue
    .line 540
    iget-object v2, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v2

    .line 541
    const/4 v1, 0x0

    :try_start_4
    invoke-direct {p0, p1, v1}, Lcom/android/server/accessibility/AccessibilityManagerService;->notifyGestureLocked(IZ)Z

    move-result v0

    .line 542
    .local v0, handled:Z
    if-nez v0, :cond_f

    .line 543
    const/4 v1, 0x1

    invoke-direct {p0, p1, v1}, Lcom/android/server/accessibility/AccessibilityManagerService;->notifyGestureLocked(IZ)Z

    move-result v0

    .line 545
    :cond_f
    monitor-exit v2

    return v0

    .line 546
    .end local v0           #handled:Z
    :catchall_11
    move-exception v1

    monitor-exit v2
    :try_end_13
    .catchall {:try_start_4 .. :try_end_13} :catchall_11

    throw v1
.end method

.method public registerUiTestAutomationService(Landroid/accessibilityservice/IAccessibilityServiceClient;Landroid/accessibilityservice/AccessibilityServiceInfo;)V
    .registers 11
    .parameter "serviceClient"
    .parameter "accessibilityServiceInfo"

    .prologue
    const/4 v7, 0x1

    .line 503
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    const-string v5, "android.permission.RETRIEVE_WINDOW_CONTENT"

    const-string v6, "registerUiTestAutomationService"

    #calls: Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V
    invoke-static {v4, v5, v6}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->access$1500(Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;Ljava/lang/String;Ljava/lang/String;)V

    .line 505
    new-instance v0, Landroid/content/ComponentName;

    const-string v4, "foo.bar"

    const-string v5, "AutomationAccessibilityService"

    invoke-direct {v0, v4, v5}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 507
    .local v0, componentName:Landroid/content/ComponentName;
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v5

    .line 513
    :try_start_16
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v3

    .line 514
    .local v3, runningServiceCount:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_1d
    if-ge v1, v3, :cond_2d

    .line 515
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mServices:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 516
    .local v2, runningService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    invoke-virtual {v2}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->unbind()Z

    .line 514
    add-int/lit8 v1, v1, 0x1

    goto :goto_1d

    .line 519
    .end local v2           #runningService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;
    :cond_2d
    iget-boolean v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    if-nez v4, :cond_37

    .line 520
    const/4 v4, 0x1

    iput-boolean v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mIsAccessibilityEnabled:Z

    .line 521
    invoke-direct {p0}, Lcom/android/server/accessibility/AccessibilityManagerService;->sendStateToClientsLocked()V

    .line 523
    :cond_37
    monitor-exit v5
    :try_end_38
    .catchall {:try_start_16 .. :try_end_38} :catchall_49

    .line 525
    new-instance v4, Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    invoke-direct {v4, p0, v0, p2, v7}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;-><init>(Lcom/android/server/accessibility/AccessibilityManagerService;Landroid/content/ComponentName;Landroid/accessibilityservice/AccessibilityServiceInfo;Z)V

    iput-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    .line 526
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    invoke-interface {p1}, Landroid/accessibilityservice/IAccessibilityServiceClient;->asBinder()Landroid/os/IBinder;

    move-result-object v5

    invoke-virtual {v4, v0, v5}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V

    .line 527
    return-void

    .line 523
    .end local v1           #i:I
    .end local v3           #runningServiceCount:I
    :catchall_49
    move-exception v4

    :try_start_4a
    monitor-exit v5
    :try_end_4b
    .catchall {:try_start_4a .. :try_end_4b} :catchall_49

    throw v4
.end method

.method public removeAccessibilityInteractionConnection(Landroid/view/IWindow;)V
    .registers 9
    .parameter "windowToken"

    .prologue
    .line 486
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v5

    .line 487
    :try_start_3
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-virtual {v4}, Landroid/util/SparseArray;->size()I

    move-result v0

    .line 488
    .local v0, count:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_a
    if-ge v1, v0, :cond_31

    .line 489
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v4

    invoke-interface {p1}, Landroid/view/IWindow;->asBinder()Landroid/os/IBinder;

    move-result-object v6

    if-ne v4, v6, :cond_2e

    .line 490
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToWindowTokenMap:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->keyAt(I)I

    move-result v2

    .line 491
    .local v2, windowId:I
    iget-object v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mWindowIdToInteractionConnectionWrapperMap:Landroid/util/SparseArray;

    invoke-virtual {v4, v2}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;

    .line 493
    .local v3, wrapper:Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;
    invoke-virtual {v3}, Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;->unlinkToDeath()V

    .line 494
    invoke-direct {p0, v2}, Lcom/android/server/accessibility/AccessibilityManagerService;->removeAccessibilityInteractionConnectionLocked(I)V

    .line 495
    monitor-exit v5

    .line 499
    .end local v2           #windowId:I
    .end local v3           #wrapper:Lcom/android/server/accessibility/AccessibilityManagerService$AccessibilityConnectionWrapper;
    :goto_2d
    return-void

    .line 488
    :cond_2e
    add-int/lit8 v1, v1, 0x1

    goto :goto_a

    .line 498
    :cond_31
    monitor-exit v5

    goto :goto_2d

    .end local v0           #count:I
    .end local v1           #i:I
    :catchall_33
    move-exception v4

    monitor-exit v5
    :try_end_35
    .catchall {:try_start_3 .. :try_end_35} :catchall_33

    throw v4
.end method

.method public sendAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)Z
    .registers 11
    .parameter "event"

    .prologue
    const/4 v3, 0x1

    const/4 v4, 0x0

    .line 388
    invoke-virtual {p1}, Landroid/view/accessibility/AccessibilityEvent;->getEventType()I

    move-result v0

    .line 392
    .local v0, eventType:I
    const/16 v5, 0x80

    if-ne v0, v5, :cond_19

    iget-boolean v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureStarted:Z

    if-eqz v5, :cond_19

    .line 394
    iput-boolean v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureStarted:Z

    .line 395
    const/16 v5, 0x200

    invoke-static {v5}, Landroid/view/accessibility/AccessibilityEvent;->obtain(I)Landroid/view/accessibility/AccessibilityEvent;

    move-result-object v2

    .line 397
    .local v2, gestureStartEvent:Landroid/view/accessibility/AccessibilityEvent;
    invoke-virtual {p0, v2}, Lcom/android/server/accessibility/AccessibilityManagerService;->sendAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)Z

    .line 400
    .end local v2           #gestureStartEvent:Landroid/view/accessibility/AccessibilityEvent;
    :cond_19
    iget-object v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v5

    .line 401
    :try_start_1c
    iget-object v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    #calls: Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->canDispatchAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)Z
    invoke-static {v6, p1}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->access$1400(Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;Landroid/view/accessibility/AccessibilityEvent;)Z

    move-result v6

    if-eqz v6, :cond_31

    .line 402
    iget-object v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mSecurityPolicy:Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;

    invoke-virtual {v6, p1}, Lcom/android/server/accessibility/AccessibilityManagerService$SecurityPolicy;->updateActiveWindowAndEventSourceLocked(Landroid/view/accessibility/AccessibilityEvent;)V

    .line 403
    const/4 v6, 0x0

    invoke-direct {p0, p1, v6}, Lcom/android/server/accessibility/AccessibilityManagerService;->notifyAccessibilityServicesDelayedLocked(Landroid/view/accessibility/AccessibilityEvent;Z)V

    .line 404
    const/4 v6, 0x1

    invoke-direct {p0, p1, v6}, Lcom/android/server/accessibility/AccessibilityManagerService;->notifyAccessibilityServicesDelayedLocked(Landroid/view/accessibility/AccessibilityEvent;Z)V

    .line 406
    :cond_31
    iget-boolean v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHasInputFilter:Z

    if-eqz v6, :cond_47

    iget-object v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mInputFilter:Lcom/android/server/accessibility/AccessibilityInputFilter;

    if-eqz v6, :cond_47

    .line 407
    iget-object v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mMainHandler:Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;

    const/4 v7, 0x3

    invoke-static {p1}, Landroid/view/accessibility/AccessibilityEvent;->obtain(Landroid/view/accessibility/AccessibilityEvent;)Landroid/view/accessibility/AccessibilityEvent;

    move-result-object v8

    invoke-virtual {v6, v7, v8}, Lcom/android/server/accessibility/AccessibilityManagerService$MainHanler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v6

    invoke-virtual {v6}, Landroid/os/Message;->sendToTarget()V

    .line 411
    :cond_47
    invoke-virtual {p1}, Landroid/view/accessibility/AccessibilityEvent;->recycle()V

    .line 412
    const/4 v6, 0x0

    iput v6, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mHandledFeedbackTypes:I

    .line 413
    monitor-exit v5
    :try_end_4e
    .catchall {:try_start_1c .. :try_end_4e} :catchall_6a

    .line 417
    const/16 v5, 0x100

    if-ne v0, v5, :cond_61

    iget-boolean v5, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureEnded:Z

    if-eqz v5, :cond_61

    .line 419
    iput-boolean v4, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureEnded:Z

    .line 420
    const/16 v5, 0x400

    invoke-static {v5}, Landroid/view/accessibility/AccessibilityEvent;->obtain(I)Landroid/view/accessibility/AccessibilityEvent;

    move-result-object v1

    .line 422
    .local v1, gestureEndEvent:Landroid/view/accessibility/AccessibilityEvent;
    invoke-virtual {p0, v1}, Lcom/android/server/accessibility/AccessibilityManagerService;->sendAccessibilityEvent(Landroid/view/accessibility/AccessibilityEvent;)Z

    .line 425
    .end local v1           #gestureEndEvent:Landroid/view/accessibility/AccessibilityEvent;
    :cond_61
    sget v5, Lcom/android/server/accessibility/AccessibilityManagerService;->OWN_PROCESS_ID:I

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v6

    if-eq v5, v6, :cond_6d

    :goto_69
    return v3

    .line 413
    :catchall_6a
    move-exception v3

    :try_start_6b
    monitor-exit v5
    :try_end_6c
    .catchall {:try_start_6b .. :try_end_6c} :catchall_6a

    throw v3

    :cond_6d
    move v3, v4

    .line 425
    goto :goto_69
.end method

.method public touchExplorationGestureEnded()V
    .registers 2

    .prologue
    .line 608
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureEnded:Z

    .line 609
    return-void
.end method

.method public touchExplorationGestureStarted()V
    .registers 2

    .prologue
    .line 612
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mTouchExplorationGestureStarted:Z

    .line 613
    return-void
.end method

.method public unregisterUiTestAutomationService(Landroid/accessibilityservice/IAccessibilityServiceClient;)V
    .registers 4
    .parameter "serviceClient"

    .prologue
    .line 530
    iget-object v1, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v1

    .line 532
    :try_start_3
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    if-eqz v0, :cond_12

    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    iget-object v0, v0, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->mServiceInterface:Landroid/accessibilityservice/IAccessibilityServiceClient;

    if-ne v0, p1, :cond_12

    .line 534
    iget-object v0, p0, Lcom/android/server/accessibility/AccessibilityManagerService;->mUiAutomationService:Lcom/android/server/accessibility/AccessibilityManagerService$Service;

    invoke-virtual {v0}, Lcom/android/server/accessibility/AccessibilityManagerService$Service;->binderDied()V

    .line 536
    :cond_12
    monitor-exit v1

    .line 537
    return-void

    .line 536
    :catchall_14
    move-exception v0

    monitor-exit v1
    :try_end_16
    .catchall {:try_start_3 .. :try_end_16} :catchall_14

    throw v0
.end method
