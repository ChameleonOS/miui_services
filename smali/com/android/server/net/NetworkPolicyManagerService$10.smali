.class Lcom/android/server/net/NetworkPolicyManagerService$10;
.super Landroid/content/BroadcastReceiver;
.source "NetworkPolicyManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/net/NetworkPolicyManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/net/NetworkPolicyManagerService;


# direct methods
.method constructor <init>(Lcom/android/server/net/NetworkPolicyManagerService;)V
    .registers 2
    .parameter

    .prologue
    .line 911
    iput-object p1, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .registers 5
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 917
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #calls: Lcom/android/server/net/NetworkPolicyManagerService;->maybeRefreshTrustedTime()V
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$500(Lcom/android/server/net/NetworkPolicyManagerService;)V

    .line 918
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #getter for: Lcom/android/server/net/NetworkPolicyManagerService;->mRulesLock:Ljava/lang/Object;
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$100(Lcom/android/server/net/NetworkPolicyManagerService;)Ljava/lang/Object;

    move-result-object v1

    monitor-enter v1

    .line 919
    :try_start_c
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #calls: Lcom/android/server/net/NetworkPolicyManagerService;->ensureActiveMobilePolicyLocked()V
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$1300(Lcom/android/server/net/NetworkPolicyManagerService;)V

    .line 920
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #calls: Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkEnabledLocked()V
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$600(Lcom/android/server/net/NetworkPolicyManagerService;)V

    .line 921
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #calls: Lcom/android/server/net/NetworkPolicyManagerService;->updateNetworkRulesLocked()V
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$1100(Lcom/android/server/net/NetworkPolicyManagerService;)V

    .line 922
    iget-object v0, p0, Lcom/android/server/net/NetworkPolicyManagerService$10;->this$0:Lcom/android/server/net/NetworkPolicyManagerService;

    #calls: Lcom/android/server/net/NetworkPolicyManagerService;->updateNotificationsLocked()V
    invoke-static {v0}, Lcom/android/server/net/NetworkPolicyManagerService;->access$700(Lcom/android/server/net/NetworkPolicyManagerService;)V

    .line 923
    monitor-exit v1

    .line 924
    return-void

    .line 923
    :catchall_22
    move-exception v0

    monitor-exit v1
    :try_end_24
    .catchall {:try_start_c .. :try_end_24} :catchall_22

    throw v0
.end method
