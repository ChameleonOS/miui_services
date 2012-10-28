.class Lcom/android/server/pm/PackageManagerService$Injector;
.super Ljava/lang/Object;
.source "PackageManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/pm/PackageManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 164
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static addMiuiSharedUids(Lcom/android/server/pm/PackageManagerService;)V
    .registers 3
    .parameter "service"

    .prologue
    .line 190
    iget-object v0, p0, Lcom/android/server/pm/PackageManagerService;->mSettings:Lcom/android/server/pm/Settings;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Lcom/android/server/pm/MiuiSharedUids;->add(Lcom/android/server/pm/Settings;Z)V

    .line 191
    return-void
.end method

.method static addPackageToSlice(Landroid/content/pm/ParceledListSlice;Landroid/content/pm/PackageInfo;I)Z
    .registers 5
    .parameter
    .parameter "pi"
    .parameter "flags"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/pm/ParceledListSlice",
            "<",
            "Landroid/content/pm/PackageInfo;",
            ">;",
            "Landroid/content/pm/PackageInfo;",
            "I)Z"
        }
    .end annotation

    .prologue
    .local p0, list:Landroid/content/pm/ParceledListSlice;,"Landroid/content/pm/ParceledListSlice<Landroid/content/pm/PackageInfo;>;"
    const/4 v1, 0x0

    .line 223
    const/high16 v0, 0x2

    and-int/2addr v0, p2

    if-eqz v0, :cond_11

    .line 224
    iget-object v0, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    if-eqz v0, :cond_33

    iget-object v0, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    array-length v0, v0

    if-lez v0, :cond_33

    .line 225
    iput-object v1, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    .line 235
    :cond_11
    :goto_11
    const/high16 v0, 0x4

    and-int/2addr v0, p2

    if-eqz v0, :cond_2c

    .line 236
    iget-object v0, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    if-eqz v0, :cond_1f

    iget-object v0, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    array-length v0, v0

    if-gtz v0, :cond_28

    :cond_1f
    iget-object v0, p1, Landroid/content/pm/PackageInfo;->services:[Landroid/content/pm/ServiceInfo;

    if-eqz v0, :cond_35

    iget-object v0, p1, Landroid/content/pm/PackageInfo;->services:[Landroid/content/pm/ServiceInfo;

    array-length v0, v0

    if-lez v0, :cond_35

    .line 238
    :cond_28
    iput-object v1, p1, Landroid/content/pm/PackageInfo;->activities:[Landroid/content/pm/ActivityInfo;

    .line 239
    iput-object v1, p1, Landroid/content/pm/PackageInfo;->services:[Landroid/content/pm/ServiceInfo;

    .line 245
    :cond_2c
    :goto_2c
    if-eqz p1, :cond_37

    invoke-virtual {p0, p1}, Landroid/content/pm/ParceledListSlice;->append(Landroid/os/Parcelable;)Z

    move-result v0

    :goto_32
    return v0

    .line 227
    :cond_33
    const/4 p1, 0x0

    goto :goto_11

    .line 241
    :cond_35
    const/4 p1, 0x0

    goto :goto_2c

    .line 245
    :cond_37
    const/4 v0, 0x0

    goto :goto_32
.end method

.method static checkApk(Lcom/android/server/pm/PackageManagerService;Landroid/os/Message;)Z
    .registers 7
    .parameter "service"
    .parameter "msg"

    .prologue
    .line 166
    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v1, Lcom/android/server/pm/PackageManagerService$HandlerParams;

    .line 167
    .local v1, params:Lcom/android/server/pm/PackageManagerService$HandlerParams;
    instance-of v2, v1, Lcom/android/server/pm/PackageManagerService$InstallParams;

    if-eqz v2, :cond_25

    move-object v0, v1

    .line 168
    check-cast v0, Lcom/android/server/pm/PackageManagerService$InstallParams;

    .line 169
    .local v0, insallParams:Lcom/android/server/pm/PackageManagerService$InstallParams;
    iget-object v2, p0, Lcom/android/server/pm/PackageManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Lcom/android/server/pm/PackageManagerService$InstallParams;->getPackageUri()Landroid/net/Uri;

    move-result-object v3

    invoke-static {v2, v3}, Lmiui/provider/ExtraGuard;->checkApk(Landroid/content/Context;Landroid/net/Uri;)Z

    move-result v2

    if-nez v2, :cond_25

    .line 170
    iget-object v2, v0, Lcom/android/server/pm/PackageManagerService$InstallParams;->observer:Landroid/content/pm/IPackageInstallObserver;

    if-eqz v2, :cond_23

    .line 172
    :try_start_1b
    iget-object v2, v0, Lcom/android/server/pm/PackageManagerService$InstallParams;->observer:Landroid/content/pm/IPackageInstallObserver;

    const/4 v3, 0x0

    const/16 v4, -0x16

    invoke-interface {v2, v3, v4}, Landroid/content/pm/IPackageInstallObserver;->packageInstalled(Ljava/lang/String;I)V
    :try_end_23
    .catch Landroid/os/RemoteException; {:try_start_1b .. :try_end_23} :catch_27

    .line 176
    :cond_23
    :goto_23
    const/4 v2, 0x0

    .line 179
    .end local v0           #insallParams:Lcom/android/server/pm/PackageManagerService$InstallParams;
    :goto_24
    return v2

    :cond_25
    const/4 v2, 0x1

    goto :goto_24

    .line 173
    .restart local v0       #insallParams:Lcom/android/server/pm/PackageManagerService$InstallParams;
    :catch_27
    move-exception v2

    goto :goto_23
.end method

.method static doHandleMessage(Lcom/android/server/pm/PackageManagerService;Lcom/android/server/pm/PackageManagerService$PackageHandler;Landroid/os/Message;)V
    .registers 5
    .parameter "service"
    .parameter "handler"
    .parameter "msg"

    .prologue
    .line 183
    iget v0, p2, Landroid/os/Message;->what:I

    const/4 v1, 0x5

    if-ne v0, v1, :cond_c

    invoke-static {p0, p2}, Lcom/android/server/pm/PackageManagerService$Injector;->checkApk(Lcom/android/server/pm/PackageManagerService;Landroid/os/Message;)Z

    move-result v0

    if-nez v0, :cond_c

    .line 187
    :goto_b
    return-void

    .line 186
    :cond_c
    invoke-virtual {p1, p2}, Lcom/android/server/pm/PackageManagerService$PackageHandler;->doHandleMessage(Landroid/os/Message;)V

    goto :goto_b
.end method

.method static ignoreMiuiFrameworkRes(Lcom/android/server/pm/PackageManagerService;Ljava/util/HashSet;)V
    .registers 4
    .parameter "service"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/server/pm/PackageManagerService;",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 194
    .local p1, libFiles:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/server/pm/PackageManagerService;->mFrameworkDir:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/framework-miui-res.apk"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 195
    return-void
.end method

.method static setAccessControl(Lcom/android/server/pm/PackageManagerService;Ljava/lang/String;II)Z
    .registers 12
    .parameter "service"
    .parameter "packageName"
    .parameter "newState"
    .parameter "flags"

    .prologue
    const v7, 0x7fffffff

    const/high16 v6, -0x8000

    .line 198
    iget-object v0, p0, Lcom/android/server/pm/PackageManagerService;->mPackages:Ljava/util/HashMap;

    .line 199
    .local v0, packages:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Landroid/content/pm/PackageParser$Package;>;"
    iget-object v3, p0, Lcom/android/server/pm/PackageManagerService;->mSettings:Lcom/android/server/pm/Settings;

    .line 200
    .local v3, settings:Lcom/android/server/pm/Settings;
    monitor-enter v0

    .line 201
    if-eq p2, v6, :cond_f

    const/4 v4, 0x0

    :try_start_d
    monitor-exit v0

    .line 214
    :goto_e
    return v4

    .line 202
    :cond_f
    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/PackageParser$Package;

    .line 203
    .local v1, pkg:Landroid/content/pm/PackageParser$Package;
    iget-object v4, v3, Lcom/android/server/pm/Settings;->mPackages:Ljava/util/HashMap;

    invoke-virtual {v4, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/pm/PackageSetting;

    .line 204
    .local v2, pkgSetting:Lcom/android/server/pm/PackageSetting;
    if-eqz v1, :cond_32

    if-eqz v2, :cond_32

    .line 205
    if-ne p3, v6, :cond_38

    .line 206
    iget v4, v2, Lcom/android/server/pm/GrantedPermissions;->pkgFlags:I

    or-int/2addr v4, v6

    iput v4, v2, Lcom/android/server/pm/GrantedPermissions;->pkgFlags:I

    .line 207
    iget-object v4, v1, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v5, v4, Landroid/content/pm/ApplicationInfo;->flags:I

    or-int/2addr v5, v6

    iput v5, v4, Landroid/content/pm/ApplicationInfo;->flags:I

    .line 212
    :goto_2f
    invoke-virtual {v3}, Lcom/android/server/pm/Settings;->writeLPr()V

    .line 214
    :cond_32
    const/4 v4, 0x1

    monitor-exit v0

    goto :goto_e

    .line 215
    .end local v1           #pkg:Landroid/content/pm/PackageParser$Package;
    .end local v2           #pkgSetting:Lcom/android/server/pm/PackageSetting;
    :catchall_35
    move-exception v4

    monitor-exit v0
    :try_end_37
    .catchall {:try_start_d .. :try_end_37} :catchall_35

    throw v4

    .line 209
    .restart local v1       #pkg:Landroid/content/pm/PackageParser$Package;
    .restart local v2       #pkgSetting:Lcom/android/server/pm/PackageSetting;
    :cond_38
    :try_start_38
    iget v4, v2, Lcom/android/server/pm/GrantedPermissions;->pkgFlags:I

    and-int/2addr v4, v7

    iput v4, v2, Lcom/android/server/pm/GrantedPermissions;->pkgFlags:I

    .line 210
    iget-object v4, v1, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v5, v4, Landroid/content/pm/ApplicationInfo;->flags:I

    and-int/2addr v5, v7

    iput v5, v4, Landroid/content/pm/ApplicationInfo;->flags:I
    :try_end_44
    .catchall {:try_start_38 .. :try_end_44} :catchall_35

    goto :goto_2f
.end method
