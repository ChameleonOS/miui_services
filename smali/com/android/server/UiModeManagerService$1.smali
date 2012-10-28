.class Lcom/android/server/UiModeManagerService$1;
.super Landroid/content/BroadcastReceiver;
.source "UiModeManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/UiModeManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/UiModeManagerService;


# direct methods
.method constructor <init>(Lcom/android/server/UiModeManagerService;)V
    .registers 2
    .parameter

    .prologue
    .line 158
    iput-object p1, p0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .registers 19
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 161
    invoke-virtual/range {p0 .. p0}, Lcom/android/server/UiModeManagerService$1;->getResultCode()I

    move-result v1

    const/4 v2, -0x1

    if-eq v1, v2, :cond_8

    .line 236
    :goto_7
    return-void

    .line 169
    :cond_8
    const-string v1, "enableFlags"

    const/4 v2, 0x0

    move-object/from16 v0, p2

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v14

    .line 170
    .local v14, enableFlags:I
    const-string v1, "disableFlags"

    const/4 v2, 0x0

    move-object/from16 v0, p2

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v12

    .line 172
    .local v12, disableFlags:I
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    iget-object v15, v1, Lcom/android/server/UiModeManagerService;->mLock:Ljava/lang/Object;

    monitor-enter v15

    .line 174
    const/4 v11, 0x0

    .line 175
    .local v11, category:Ljava/lang/String;
    :try_start_22
    sget-object v1, Landroid/app/UiModeManager;->ACTION_ENTER_CAR_MODE:Ljava/lang/String;

    invoke-virtual/range {p2 .. p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_93

    .line 178
    and-int/lit8 v1, v14, 0x1

    if-eqz v1, :cond_34

    .line 180
    const-string v11, "android.intent.category.CAR_DOCK"

    .line 203
    :cond_34
    :goto_34
    if-eqz v11, :cond_73

    .line 206
    invoke-static {v11}, Lcom/android/server/UiModeManagerService;->buildHomeIntent(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v3

    .line 215
    .local v3, homeIntent:Landroid/content/Intent;
    const/4 v9, 0x0

    .line 216
    .local v9, newConfig:Landroid/content/res/Configuration;
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    #getter for: Lcom/android/server/UiModeManagerService;->mHoldingConfiguration:Z
    invoke-static {v1}, Lcom/android/server/UiModeManagerService;->access$000(Lcom/android/server/UiModeManagerService;)Z

    move-result v1

    if-eqz v1, :cond_5d

    .line 217
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    const/4 v2, 0x0

    #setter for: Lcom/android/server/UiModeManagerService;->mHoldingConfiguration:Z
    invoke-static {v1, v2}, Lcom/android/server/UiModeManagerService;->access$002(Lcom/android/server/UiModeManagerService;Z)Z

    .line 218
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/android/server/UiModeManagerService;->updateConfigurationLocked(Z)V

    .line 219
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    #getter for: Lcom/android/server/UiModeManagerService;->mConfiguration:Landroid/content/res/Configuration;
    invoke-static {v1}, Lcom/android/server/UiModeManagerService;->access$100(Lcom/android/server/UiModeManagerService;)Landroid/content/res/Configuration;
    :try_end_5c
    .catchall {:try_start_22 .. :try_end_5c} :catchall_90

    move-result-object v9

    .line 222
    :cond_5d
    :try_start_5d
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v1

    const/4 v2, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    const/4 v8, 0x0

    const/4 v10, 0x0

    invoke-interface/range {v1 .. v10}, Landroid/app/IActivityManager;->startActivityWithConfig(Landroid/app/IApplicationThread;Landroid/content/Intent;Ljava/lang/String;Landroid/os/IBinder;Ljava/lang/String;IILandroid/content/res/Configuration;Landroid/os/Bundle;)I

    .line 225
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    const/4 v2, 0x0

    #setter for: Lcom/android/server/UiModeManagerService;->mHoldingConfiguration:Z
    invoke-static {v1, v2}, Lcom/android/server/UiModeManagerService;->access$002(Lcom/android/server/UiModeManagerService;Z)Z
    :try_end_73
    .catchall {:try_start_5d .. :try_end_73} :catchall_90
    .catch Landroid/os/RemoteException; {:try_start_5d .. :try_end_73} :catch_ad

    .line 231
    .end local v3           #homeIntent:Landroid/content/Intent;
    .end local v9           #newConfig:Landroid/content/res/Configuration;
    :cond_73
    :goto_73
    :try_start_73
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    #getter for: Lcom/android/server/UiModeManagerService;->mHoldingConfiguration:Z
    invoke-static {v1}, Lcom/android/server/UiModeManagerService;->access$000(Lcom/android/server/UiModeManagerService;)Z

    move-result v1

    if-eqz v1, :cond_8d

    .line 232
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    const/4 v2, 0x0

    #setter for: Lcom/android/server/UiModeManagerService;->mHoldingConfiguration:Z
    invoke-static {v1, v2}, Lcom/android/server/UiModeManagerService;->access$002(Lcom/android/server/UiModeManagerService;Z)Z

    .line 233
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/UiModeManagerService$1;->this$0:Lcom/android/server/UiModeManagerService;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/android/server/UiModeManagerService;->updateConfigurationLocked(Z)V

    .line 235
    :cond_8d
    monitor-exit v15

    goto/16 :goto_7

    :catchall_90
    move-exception v1

    monitor-exit v15
    :try_end_92
    .catchall {:try_start_73 .. :try_end_92} :catchall_90

    throw v1

    .line 182
    :cond_93
    :try_start_93
    sget-object v1, Landroid/app/UiModeManager;->ACTION_ENTER_DESK_MODE:Ljava/lang/String;

    invoke-virtual/range {p2 .. p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_a6

    .line 186
    and-int/lit8 v1, v14, 0x1

    if-eqz v1, :cond_34

    .line 188
    const-string v11, "android.intent.category.DESK_DOCK"

    goto :goto_34

    .line 192
    :cond_a6
    and-int/lit8 v1, v12, 0x1

    if-eqz v1, :cond_34

    .line 193
    const-string v11, "android.intent.category.HOME"

    goto :goto_34

    .line 226
    .restart local v3       #homeIntent:Landroid/content/Intent;
    .restart local v9       #newConfig:Landroid/content/res/Configuration;
    :catch_ad
    move-exception v13

    .line 227
    .local v13, e:Landroid/os/RemoteException;
    invoke-static {}, Lcom/android/server/UiModeManagerService;->access$200()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v13}, Landroid/os/RemoteException;->getCause()Ljava/lang/Throwable;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_b9
    .catchall {:try_start_93 .. :try_end_b9} :catchall_90

    goto :goto_73
.end method
