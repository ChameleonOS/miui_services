.class final Lcom/android/server/pm/PackageManagerService$AppDirObserver;
.super Landroid/os/FileObserver;
.source "PackageManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/pm/PackageManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x12
    name = "AppDirObserver"
.end annotation


# instance fields
.field private final mIsRom:Z

.field private final mRootDir:Ljava/lang/String;

.field final synthetic this$0:Lcom/android/server/pm/PackageManagerService;


# direct methods
.method public constructor <init>(Lcom/android/server/pm/PackageManagerService;Ljava/lang/String;IZ)V
    .registers 5
    .parameter
    .parameter "path"
    .parameter "mask"
    .parameter "isrom"

    .prologue
    .line 5256
    iput-object p1, p0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    .line 5257
    invoke-direct {p0, p2, p3}, Landroid/os/FileObserver;-><init>(Ljava/lang/String;I)V

    .line 5258
    iput-object p2, p0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->mRootDir:Ljava/lang/String;

    .line 5259
    iput-boolean p4, p0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->mIsRom:Z

    .line 5260
    return-void
.end method


# virtual methods
.method public onEvent(ILjava/lang/String;)V
    .registers 19
    .parameter "event"
    .parameter "path"

    .prologue
    .line 5263
    const/4 v14, 0x0

    .line 5264
    .local v14, removedPackage:Ljava/lang/String;
    const/4 v15, -0x1

    .line 5265
    .local v15, removedUid:I
    const/4 v9, 0x0

    .line 5266
    .local v9, addedPackage:Ljava/lang/String;
    const/4 v10, -0x1

    .line 5269
    .local v10, addedUid:I
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v7, v1, Lcom/android/server/pm/PackageManagerService;->mInstallLock:Ljava/lang/Object;

    monitor-enter v7

    .line 5270
    const/4 v12, 0x0

    .line 5271
    .local v12, fullPathStr:Ljava/lang/String;
    const/4 v2, 0x0

    .line 5272
    .local v2, fullPath:Ljava/io/File;
    if-eqz p2, :cond_1f

    .line 5273
    :try_start_f
    new-instance v11, Ljava/io/File;

    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->mRootDir:Ljava/lang/String;

    move-object/from16 v0, p2

    invoke-direct {v11, v1, v0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1a
    .catchall {:try_start_f .. :try_end_1a} :catchall_2f

    .line 5274
    .end local v2           #fullPath:Ljava/io/File;
    .local v11, fullPath:Ljava/io/File;
    :try_start_1a
    invoke-virtual {v11}, Ljava/io/File;->getPath()Ljava/lang/String;
    :try_end_1d
    .catchall {:try_start_1a .. :try_end_1d} :catchall_fa

    move-result-object v12

    move-object v2, v11

    .line 5280
    .end local v11           #fullPath:Ljava/io/File;
    .restart local v2       #fullPath:Ljava/io/File;
    :cond_1f
    :try_start_1f
    #calls: Lcom/android/server/pm/PackageManagerService;->isPackageFilename(Ljava/lang/String;)Z
    invoke-static/range {p2 .. p2}, Lcom/android/server/pm/PackageManagerService;->access$1200(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_27

    .line 5283
    monitor-exit v7

    .line 5349
    :cond_26
    :goto_26
    return-void

    .line 5288
    :cond_27
    #calls: Lcom/android/server/pm/PackageManagerService;->ignoreCodePath(Ljava/lang/String;)Z
    invoke-static {v12}, Lcom/android/server/pm/PackageManagerService;->access$1300(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_32

    .line 5289
    monitor-exit v7

    goto :goto_26

    .line 5334
    :catchall_2f
    move-exception v1

    :goto_30
    monitor-exit v7
    :try_end_31
    .catchall {:try_start_1f .. :try_end_31} :catchall_2f

    throw v1

    .line 5291
    :cond_32
    const/4 v13, 0x0

    .line 5293
    .local v13, p:Landroid/content/pm/PackageParser$Package;
    :try_start_33
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v3, v1, Lcom/android/server/pm/PackageManagerService;->mPackages:Ljava/util/HashMap;

    monitor-enter v3
    :try_end_3a
    .catchall {:try_start_33 .. :try_end_3a} :catchall_2f

    .line 5294
    :try_start_3a
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v1, v1, Lcom/android/server/pm/PackageManagerService;->mAppDirs:Ljava/util/HashMap;

    invoke-virtual {v1, v12}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    move-object v0, v1

    check-cast v0, Landroid/content/pm/PackageParser$Package;

    move-object v13, v0

    .line 5295
    monitor-exit v3
    :try_end_49
    .catchall {:try_start_3a .. :try_end_49} :catchall_ed

    .line 5296
    move/from16 v0, p1

    and-int/lit16 v1, v0, 0x248

    if-eqz v1, :cond_61

    .line 5297
    if-eqz v13, :cond_61

    .line 5298
    :try_start_51
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    const/4 v3, 0x1

    invoke-virtual {v1, v13, v3}, Lcom/android/server/pm/PackageManagerService;->removePackageLI(Landroid/content/pm/PackageParser$Package;Z)V

    .line 5299
    iget-object v1, v13, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v14, v1, Landroid/content/pm/ApplicationInfo;->packageName:Ljava/lang/String;

    .line 5300
    iget-object v1, v13, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v15, v1, Landroid/content/pm/ApplicationInfo;->uid:I

    .line 5304
    :cond_61
    move/from16 v0, p1

    and-int/lit16 v1, v0, 0x88

    if-eqz v1, :cond_a7

    .line 5305
    if-nez v13, :cond_a7

    .line 5306
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    move-object/from16 v0, p0

    iget-boolean v3, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->mIsRom:Z

    if-eqz v3, :cond_f0

    const/16 v3, 0x41

    :goto_75
    or-int/lit8 v3, v3, 0x2

    or-int/lit8 v3, v3, 0x4

    const/16 v4, 0x61

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v5

    #calls: Lcom/android/server/pm/PackageManagerService;->scanPackageLI(Ljava/io/File;IIJ)Landroid/content/pm/PackageParser$Package;
    invoke-static/range {v1 .. v6}, Lcom/android/server/pm/PackageManagerService;->access$1400(Lcom/android/server/pm/PackageManagerService;Ljava/io/File;IIJ)Landroid/content/pm/PackageParser$Package;

    move-result-object v13

    .line 5313
    if-eqz v13, :cond_a7

    .line 5320
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v3, v1, Lcom/android/server/pm/PackageManagerService;->mPackages:Ljava/util/HashMap;

    monitor-enter v3
    :try_end_8c
    .catchall {:try_start_51 .. :try_end_8c} :catchall_2f

    .line 5321
    :try_start_8c
    move-object/from16 v0, p0

    iget-object v4, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v6, v13, Landroid/content/pm/PackageParser$Package;->packageName:Ljava/lang/String;

    iget-object v1, v13, Landroid/content/pm/PackageParser$Package;->permissions:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-lez v1, :cond_f2

    const/4 v1, 0x1

    :goto_9b
    #calls: Lcom/android/server/pm/PackageManagerService;->updatePermissionsLPw(Ljava/lang/String;Landroid/content/pm/PackageParser$Package;I)V
    invoke-static {v4, v6, v13, v1}, Lcom/android/server/pm/PackageManagerService;->access$1500(Lcom/android/server/pm/PackageManagerService;Ljava/lang/String;Landroid/content/pm/PackageParser$Package;I)V

    .line 5323
    monitor-exit v3
    :try_end_9f
    .catchall {:try_start_8c .. :try_end_9f} :catchall_f4

    .line 5324
    :try_start_9f
    iget-object v1, v13, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget-object v9, v1, Landroid/content/pm/ApplicationInfo;->packageName:Ljava/lang/String;

    .line 5325
    iget-object v1, v13, Landroid/content/pm/PackageParser$Package;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v10, v1, Landroid/content/pm/ApplicationInfo;->uid:I

    .line 5331
    :cond_a7
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v3, v1, Lcom/android/server/pm/PackageManagerService;->mPackages:Ljava/util/HashMap;

    monitor-enter v3
    :try_end_ae
    .catchall {:try_start_9f .. :try_end_ae} :catchall_2f

    .line 5332
    :try_start_ae
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/android/server/pm/PackageManagerService$AppDirObserver;->this$0:Lcom/android/server/pm/PackageManagerService;

    iget-object v1, v1, Lcom/android/server/pm/PackageManagerService;->mSettings:Lcom/android/server/pm/Settings;

    invoke-virtual {v1}, Lcom/android/server/pm/Settings;->writeLPr()V

    .line 5333
    monitor-exit v3
    :try_end_b8
    .catchall {:try_start_ae .. :try_end_b8} :catchall_f7

    .line 5334
    :try_start_b8
    monitor-exit v7
    :try_end_b9
    .catchall {:try_start_b8 .. :try_end_b9} :catchall_2f

    .line 5336
    if-eqz v14, :cond_d5

    .line 5337
    new-instance v5, Landroid/os/Bundle;

    const/4 v1, 0x1

    invoke-direct {v5, v1}, Landroid/os/Bundle;-><init>(I)V

    .line 5338
    .local v5, extras:Landroid/os/Bundle;
    const-string v1, "android.intent.extra.UID"

    invoke-virtual {v5, v1, v15}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 5339
    const-string v1, "android.intent.extra.DATA_REMOVED"

    const/4 v3, 0x0

    invoke-virtual {v5, v1, v3}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    .line 5340
    const-string v3, "android.intent.action.PACKAGE_REMOVED"

    const/4 v6, 0x0

    const/4 v7, 0x0

    const/4 v8, -0x1

    move-object v4, v14

    invoke-static/range {v3 .. v8}, Lcom/android/server/pm/PackageManagerService;->sendPackageBroadcast(Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;Ljava/lang/String;Landroid/content/IIntentReceiver;I)V

    .line 5343
    .end local v5           #extras:Landroid/os/Bundle;
    :cond_d5
    if-eqz v9, :cond_26

    .line 5344
    new-instance v5, Landroid/os/Bundle;

    const/4 v1, 0x1

    invoke-direct {v5, v1}, Landroid/os/Bundle;-><init>(I)V

    .line 5345
    .restart local v5       #extras:Landroid/os/Bundle;
    const-string v1, "android.intent.extra.UID"

    invoke-virtual {v5, v1, v10}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 5346
    const-string v3, "android.intent.action.PACKAGE_ADDED"

    const/4 v6, 0x0

    const/4 v7, 0x0

    const/4 v8, -0x1

    move-object v4, v9

    invoke-static/range {v3 .. v8}, Lcom/android/server/pm/PackageManagerService;->sendPackageBroadcast(Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;Ljava/lang/String;Landroid/content/IIntentReceiver;I)V

    goto/16 :goto_26

    .line 5295
    .end local v5           #extras:Landroid/os/Bundle;
    :catchall_ed
    move-exception v1

    :try_start_ee
    monitor-exit v3
    :try_end_ef
    .catchall {:try_start_ee .. :try_end_ef} :catchall_ed

    :try_start_ef
    throw v1
    :try_end_f0
    .catchall {:try_start_ef .. :try_end_f0} :catchall_2f

    .line 5306
    :cond_f0
    const/4 v3, 0x0

    goto :goto_75

    .line 5321
    :cond_f2
    const/4 v1, 0x0

    goto :goto_9b

    .line 5323
    :catchall_f4
    move-exception v1

    :try_start_f5
    monitor-exit v3
    :try_end_f6
    .catchall {:try_start_f5 .. :try_end_f6} :catchall_f4

    :try_start_f6
    throw v1
    :try_end_f7
    .catchall {:try_start_f6 .. :try_end_f7} :catchall_2f

    .line 5333
    :catchall_f7
    move-exception v1

    :try_start_f8
    monitor-exit v3
    :try_end_f9
    .catchall {:try_start_f8 .. :try_end_f9} :catchall_f7

    :try_start_f9
    throw v1
    :try_end_fa
    .catchall {:try_start_f9 .. :try_end_fa} :catchall_2f

    .line 5334
    .end local v2           #fullPath:Ljava/io/File;
    .end local v13           #p:Landroid/content/pm/PackageParser$Package;
    .restart local v11       #fullPath:Ljava/io/File;
    :catchall_fa
    move-exception v1

    move-object v2, v11

    .end local v11           #fullPath:Ljava/io/File;
    .restart local v2       #fullPath:Ljava/io/File;
    goto/16 :goto_30
.end method
