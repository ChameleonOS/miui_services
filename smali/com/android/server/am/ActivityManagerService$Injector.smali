.class Lcom/android/server/am/ActivityManagerService$Injector;
.super Ljava/lang/Object;
.source "ActivityManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/am/ActivityManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 167
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static getCallingUidPackage(Lcom/android/server/am/ActivityManagerService;Landroid/os/IBinder;)Ljava/lang/String;
    .registers 7
    .parameter "service"
    .parameter "token"

    .prologue
    const/4 v3, 0x0

    .line 196
    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService;->mMainStack:Lcom/android/server/am/ActivityStack;

    invoke-virtual {v4, p1}, Lcom/android/server/am/ActivityStack;->isInStackLocked(Landroid/os/IBinder;)Lcom/android/server/am/ActivityRecord;

    move-result-object v2

    .line 197
    .local v2, r:Lcom/android/server/am/ActivityRecord;
    if-nez v2, :cond_a

    .line 210
    :cond_9
    :goto_9
    return-object v3

    .line 198
    :cond_a
    iget v0, v2, Lcom/android/server/am/ActivityRecord;->launchedFromUid:I

    .line 201
    .local v0, callingUid:I
    if-lez v0, :cond_9

    .line 203
    :try_start_e
    invoke-static {}, Landroid/app/AppGlobals;->getPackageManager()Landroid/content/pm/IPackageManager;

    move-result-object v4

    invoke-interface {v4, v0}, Landroid/content/pm/IPackageManager;->getPackagesForUid(I)[Ljava/lang/String;

    move-result-object v1

    .line 204
    .local v1, packages:[Ljava/lang/String;
    array-length v4, v1

    if-lez v4, :cond_9

    .line 205
    const/4 v4, 0x0

    aget-object v3, v1, v4
    :try_end_1c
    .catch Landroid/os/RemoteException; {:try_start_e .. :try_end_1c} :catch_1d

    goto :goto_9

    .line 207
    .end local v1           #packages:[Ljava/lang/String;
    :catch_1d
    move-exception v4

    goto :goto_9
.end method

.method static isForegroudApp(Lcom/android/server/am/ActivityManagerService;Lcom/android/server/am/ProcessRecord;Lcom/android/server/am/ProcessRecord;)Z
    .registers 6
    .parameter "service"
    .parameter "app"
    .parameter "TOP_APP"

    .prologue
    const/4 v0, 0x1

    .line 169
    if-eq p1, p2, :cond_15

    iget-object v1, p0, Lcom/android/server/am/ActivityManagerService;->mHomeProcess:Lcom/android/server/am/ProcessRecord;

    if-ne p1, v1, :cond_16

    iget-object v1, p0, Lcom/android/server/am/ActivityManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "keep_launcher_in_memory"

    invoke-static {v1, v2, v0}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v1

    if-eqz v1, :cond_16

    .line 174
    :cond_15
    :goto_15
    return v0

    :cond_16
    const/4 v0, 0x0

    goto :goto_15
.end method

.method static showAppCrashDialog(Lcom/android/server/am/ActivityManagerService;Ljava/util/HashMap;)Z
    .registers 8
    .parameter "service"
    .parameter "data"

    .prologue
    const/4 v5, 0x0

    .line 178
    const-string v4, "app"

    invoke-virtual {p1, v4}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/am/ProcessRecord;

    .line 179
    .local v2, proc:Lcom/android/server/am/ProcessRecord;
    const-string v4, "result"

    invoke-virtual {p1, v4}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/am/AppErrorResult;

    .line 180
    .local v3, res:Lcom/android/server/am/AppErrorResult;
    const-string v4, "crash"

    invoke-virtual {p1, v4}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/ApplicationErrorReport$CrashInfo;

    .line 181
    .local v0, crashInfo:Landroid/app/ApplicationErrorReport$CrashInfo;
    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService;->mContext:Landroid/content/Context;

    invoke-static {v4}, Lmiui/provider/ExtraSettings$Secure;->isForceCloseDialogEnabled(Landroid/content/Context;)Z

    move-result v4

    if-eqz v4, :cond_2f

    .line 182
    new-instance v1, Lcom/android/server/am/AppErrorDialog;

    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService;->mContext:Landroid/content/Context;

    invoke-direct {v1, v4, v3, v2, v0}, Lcom/android/server/am/AppErrorDialog;-><init>(Landroid/content/Context;Lcom/android/server/am/AppErrorResult;Lcom/android/server/am/ProcessRecord;Landroid/app/ApplicationErrorReport$CrashInfo;)V

    .line 183
    .local v1, d:Landroid/app/Dialog;
    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 184
    iput-object v1, v2, Lcom/android/server/am/ProcessRecord;->crashDialog:Landroid/app/Dialog;

    .line 192
    .end local v1           #d:Landroid/app/Dialog;
    :goto_2d
    const/4 v4, 0x1

    return v4

    .line 186
    :cond_2f
    if-eqz v2, :cond_38

    if-eqz v0, :cond_38

    .line 187
    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService;->mContext:Landroid/content/Context;

    invoke-static {v4, v2, v0, v5}, Lcom/android/server/am/MiuiErrorReport;->sendFcErrorReport(Landroid/content/Context;Lcom/android/server/am/ProcessRecord;Landroid/app/ApplicationErrorReport$CrashInfo;Z)V

    .line 189
    :cond_38
    iget-object v4, p0, Lcom/android/server/am/ActivityManagerService;->mMainStack:Lcom/android/server/am/ActivityStack;

    invoke-virtual {v4}, Lcom/android/server/am/ActivityStack;->moveHomeToFrontLocked()V

    .line 190
    invoke-virtual {v3, v5}, Lcom/android/server/am/AppErrorResult;->set(I)V

    goto :goto_2d
.end method
