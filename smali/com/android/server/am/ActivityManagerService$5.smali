.class Lcom/android/server/am/ActivityManagerService$5;
.super Ljava/lang/Object;
.source "ActivityManagerService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/server/am/ActivityManagerService;->showLaunchWarningLocked(Lcom/android/server/am/ActivityRecord;Lcom/android/server/am/ActivityRecord;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/am/ActivityManagerService;

.field final synthetic val$cur:Lcom/android/server/am/ActivityRecord;

.field final synthetic val$next:Lcom/android/server/am/ActivityRecord;


# direct methods
.method constructor <init>(Lcom/android/server/am/ActivityManagerService;Lcom/android/server/am/ActivityRecord;Lcom/android/server/am/ActivityRecord;)V
    .registers 4
    .parameter
    .parameter
    .parameter

    .prologue
    .line 3375
    iput-object p1, p0, Lcom/android/server/am/ActivityManagerService$5;->this$0:Lcom/android/server/am/ActivityManagerService;

    iput-object p2, p0, Lcom/android/server/am/ActivityManagerService$5;->val$cur:Lcom/android/server/am/ActivityRecord;

    iput-object p3, p0, Lcom/android/server/am/ActivityManagerService$5;->val$next:Lcom/android/server/am/ActivityRecord;

    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 7

    .prologue
    .line 3378
    iget-object v2, p0, Lcom/android/server/am/ActivityManagerService$5;->this$0:Lcom/android/server/am/ActivityManagerService;

    monitor-enter v2

    .line 3379
    :try_start_3
    new-instance v0, Lcom/android/server/am/LaunchWarningWindow;

    iget-object v1, p0, Lcom/android/server/am/ActivityManagerService$5;->this$0:Lcom/android/server/am/ActivityManagerService;

    iget-object v1, v1, Lcom/android/server/am/ActivityManagerService;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/android/server/am/ActivityManagerService$5;->val$cur:Lcom/android/server/am/ActivityRecord;

    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService$5;->val$next:Lcom/android/server/am/ActivityRecord;

    invoke-direct {v0, v1, v3, v4}, Lcom/android/server/am/LaunchWarningWindow;-><init>(Landroid/content/Context;Lcom/android/server/am/ActivityRecord;Lcom/android/server/am/ActivityRecord;)V

    .line 3380
    .local v0, d:Landroid/app/Dialog;
    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    .line 3381
    iget-object v1, p0, Lcom/android/server/am/ActivityManagerService$5;->this$0:Lcom/android/server/am/ActivityManagerService;

    iget-object v1, v1, Lcom/android/server/am/ActivityManagerService;->mHandler:Landroid/os/Handler;

    new-instance v3, Lcom/android/server/am/ActivityManagerService$5$1;

    invoke-direct {v3, p0, v0}, Lcom/android/server/am/ActivityManagerService$5$1;-><init>(Lcom/android/server/am/ActivityManagerService$5;Landroid/app/Dialog;)V

    const-wide/16 v4, 0xfa0

    invoke-virtual {v1, v3, v4, v5}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 3390
    monitor-exit v2

    .line 3391
    return-void

    .line 3390
    .end local v0           #d:Landroid/app/Dialog;
    :catchall_23
    move-exception v1

    monitor-exit v2
    :try_end_25
    .catchall {:try_start_3 .. :try_end_25} :catchall_23

    throw v1
.end method
