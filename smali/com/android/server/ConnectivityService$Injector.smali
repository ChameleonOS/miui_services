.class Lcom/android/server/ConnectivityService$Injector;
.super Ljava/lang/Object;
.source "ConnectivityService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/ConnectivityService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 115
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static startUsingNetworkFeature(I)V
    .registers 4
    .parameter "usedNetworkType"

    .prologue
    .line 117
    invoke-static {}, Lmiui/net/FirewallManager;->getInstance()Lmiui/net/FirewallManager;

    move-result-object v0

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    invoke-static {}, Landroid/os/Binder;->getCallingPid()I

    move-result v2

    invoke-virtual {v0, v1, v2, p0}, Lmiui/net/FirewallManager;->onStartUsingNetworkFeature(III)V

    .line 118
    return-void
.end method

.method public static stopUsingNetworkFeature(Lcom/android/server/ConnectivityService$FeatureUser;I)V
    .registers 5
    .parameter "u"
    .parameter "usedNetworkType"

    .prologue
    .line 121
    invoke-static {}, Lmiui/net/FirewallManager;->getInstance()Lmiui/net/FirewallManager;

    move-result-object v0

    iget v1, p0, Lcom/android/server/ConnectivityService$FeatureUser;->mUid:I

    iget v2, p0, Lcom/android/server/ConnectivityService$FeatureUser;->mPid:I

    invoke-virtual {v0, v1, v2, p1}, Lmiui/net/FirewallManager;->onStopUsingNetworkFeature(III)V

    .line 122
    return-void
.end method
