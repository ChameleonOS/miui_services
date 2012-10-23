.class Lcom/android/server/AppWidgetServiceImpl;
.super Ljava/lang/Object;
.source "AppWidgetServiceImpl.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;,
        Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;,
        Lcom/android/server/AppWidgetServiceImpl$Host;,
        Lcom/android/server/AppWidgetServiceImpl$Provider;
    }
.end annotation


# static fields
.field private static final MIN_UPDATE_PERIOD:I = 0x1b7740

.field private static final SETTINGS_FILENAME:Ljava/lang/String; = "appwidgets.xml"

.field private static final TAG:Ljava/lang/String; = "AppWidgetServiceImpl"


# instance fields
.field mAlarmManager:Landroid/app/AlarmManager;

.field final mAppWidgetIds:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;",
            ">;"
        }
    .end annotation
.end field

.field private final mBoundRemoteViewsServices:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Landroid/util/Pair",
            "<",
            "Ljava/lang/Integer;",
            "Landroid/content/Intent$FilterComparison;",
            ">;",
            "Landroid/content/ServiceConnection;",
            ">;"
        }
    .end annotation
.end field

.field mContext:Landroid/content/Context;

.field mDeletedHosts:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/AppWidgetServiceImpl$Host;",
            ">;"
        }
    .end annotation
.end field

.field mDeletedProviders:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/AppWidgetServiceImpl$Provider;",
            ">;"
        }
    .end annotation
.end field

.field mHosts:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/AppWidgetServiceImpl$Host;",
            ">;"
        }
    .end annotation
.end field

.field mInstalledProviders:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/android/server/AppWidgetServiceImpl$Provider;",
            ">;"
        }
    .end annotation
.end field

.field mLocale:Ljava/util/Locale;

.field mMaxWidgetBitmapMemory:I

.field mNextAppWidgetId:I

.field mPackagesWithBindWidgetPermission:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field mPm:Landroid/content/pm/IPackageManager;

.field private final mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Landroid/content/Intent$FilterComparison;",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/Integer;",
            ">;>;"
        }
    .end annotation
.end field

.field mSafeMode:Z

.field mStateLoaded:Z

.field mUserId:I


# direct methods
.method constructor <init>(Landroid/content/Context;I)V
    .registers 5
    .parameter "context"
    .parameter "userId"

    .prologue
    .line 181
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 158
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    .line 160
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    .line 166
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    .line 167
    const/4 v0, 0x1

    iput v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mNextAppWidgetId:I

    .line 168
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    .line 169
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    .line 171
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    .line 178
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedProviders:Ljava/util/ArrayList;

    .line 179
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedHosts:Ljava/util/ArrayList;

    .line 182
    iput-object p1, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    .line 183
    invoke-static {}, Landroid/app/AppGlobals;->getPackageManager()Landroid/content/pm/IPackageManager;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mPm:Landroid/content/pm/IPackageManager;

    .line 184
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v1, "alarm"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/AlarmManager;

    iput-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mAlarmManager:Landroid/app/AlarmManager;

    .line 185
    iput p2, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    .line 186
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->computeMaximumWidgetBitmapMemory()V

    .line 187
    return-void
.end method

.method private bindAppWidgetIdImpl(ILandroid/content/ComponentName;)V
    .registers 12
    .parameter "appWidgetId"
    .parameter "provider"

    .prologue
    const/4 v7, 0x1

    .line 510
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 512
    .local v1, ident:J
    :try_start_5
    iget-object v6, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v6
    :try_end_8
    .catchall {:try_start_5 .. :try_end_8} :catchall_1c

    .line 513
    :try_start_8
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 514
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 515
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-nez v0, :cond_21

    .line 516
    new-instance v5, Ljava/lang/IllegalArgumentException;

    const-string v7, "bad appWidgetId"

    invoke-direct {v5, v7}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 548
    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_19
    move-exception v5

    monitor-exit v6
    :try_end_1b
    .catchall {:try_start_8 .. :try_end_1b} :catchall_19

    :try_start_1b
    throw v5
    :try_end_1c
    .catchall {:try_start_1b .. :try_end_1c} :catchall_1c

    .line 550
    :catchall_1c
    move-exception v5

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v5

    .line 518
    .restart local v0       #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_21
    :try_start_21
    iget-object v5, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v5, :cond_4e

    .line 519
    new-instance v5, Ljava/lang/IllegalArgumentException;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "appWidgetId "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " already bound to "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-object v8, v8, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v8, v8, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v5, v7}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 522
    :cond_4e
    invoke-virtual {p0, p2}, Lcom/android/server/AppWidgetServiceImpl;->lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v4

    .line 523
    .local v4, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-nez v4, :cond_6d

    .line 524
    new-instance v5, Ljava/lang/IllegalArgumentException;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "not a appwidget provider: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v5, v7}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 526
    :cond_6d
    iget-boolean v5, v4, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-eqz v5, :cond_8a

    .line 527
    new-instance v5, Ljava/lang/IllegalArgumentException;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "can\'t bind to a 3rd party provider in safe mode: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v5, v7}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 531
    :cond_8a
    iput-object v4, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 532
    iget-object v5, v4, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v5, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 533
    iget-object v5, v4, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v3

    .line 534
    .local v3, instancesSize:I
    if-ne v3, v7, :cond_9c

    .line 536
    invoke-virtual {p0, v4}, Lcom/android/server/AppWidgetServiceImpl;->sendEnableIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 543
    :cond_9c
    const/4 v5, 0x1

    new-array v5, v5, [I

    const/4 v7, 0x0

    aput p1, v5, v7

    invoke-virtual {p0, v4, v5}, Lcom/android/server/AppWidgetServiceImpl;->sendUpdateIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    .line 546
    invoke-static {v4}, Lcom/android/server/AppWidgetServiceImpl;->getAppWidgetIds(Lcom/android/server/AppWidgetServiceImpl$Provider;)[I

    move-result-object v5

    invoke-virtual {p0, v4, v5}, Lcom/android/server/AppWidgetServiceImpl;->registerForBroadcastsLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    .line 547
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 548
    monitor-exit v6
    :try_end_b0
    .catchall {:try_start_21 .. :try_end_b0} :catchall_19

    .line 550
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 552
    return-void
.end method

.method private callerHasBindAppWidgetPermission(Ljava/lang/String;)Z
    .registers 6
    .parameter "packageName"

    .prologue
    const/4 v2, 0x0

    .line 574
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    .line 576
    .local v0, callingUid:I
    :try_start_5
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->getUidForPackage(Ljava/lang/String;)I

    move-result v3

    invoke-static {v0, v3}, Landroid/os/UserId;->isSameApp(II)Z
    :try_end_c
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_c} :catch_10

    move-result v3

    if-nez v3, :cond_12

    .line 584
    :goto_f
    return v2

    .line 579
    :catch_10
    move-exception v1

    .line 580
    .local v1, e:Ljava/lang/Exception;
    goto :goto_f

    .line 582
    .end local v1           #e:Ljava/lang/Exception;
    :cond_12
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v3

    .line 583
    :try_start_15
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 584
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    invoke-virtual {v2, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v2

    monitor-exit v3

    goto :goto_f

    .line 585
    :catchall_20
    move-exception v2

    monitor-exit v3
    :try_end_22
    .catchall {:try_start_15 .. :try_end_22} :catchall_20

    throw v2
.end method

.method private decrementAppWidgetServiceRefCount(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V
    .registers 6
    .parameter "id"

    .prologue
    .line 765
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v3}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v3

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .line 766
    .local v1, it:Ljava/util/Iterator;,"Ljava/util/Iterator<Landroid/content/Intent$FilterComparison;>;"
    :cond_a
    :goto_a
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_3b

    .line 767
    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/content/Intent$FilterComparison;

    .line 768
    .local v2, key:Landroid/content/Intent$FilterComparison;
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v3, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/HashSet;

    .line 769
    .local v0, ids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    iget v3, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_a

    .line 772
    invoke-virtual {v0}, Ljava/util/HashSet;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_a

    .line 773
    invoke-virtual {v2}, Landroid/content/Intent$FilterComparison;->getIntent()Landroid/content/Intent;

    move-result-object v3

    invoke-direct {p0, v3, p1}, Lcom/android/server/AppWidgetServiceImpl;->destroyRemoteViewsService(Landroid/content/Intent;Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V

    .line 774
    invoke-interface {v1}, Ljava/util/Iterator;->remove()V

    goto :goto_a

    .line 778
    .end local v0           #ids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    .end local v2           #key:Landroid/content/Intent$FilterComparison;
    :cond_3b
    return-void
.end method

.method private destroyRemoteViewsService(Landroid/content/Intent;Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V
    .registers 9
    .parameter "intent"
    .parameter "id"

    .prologue
    .line 719
    new-instance v0, Lcom/android/server/AppWidgetServiceImpl$1;

    invoke-direct {v0, p0, p1}, Lcom/android/server/AppWidgetServiceImpl$1;-><init>(Lcom/android/server/AppWidgetServiceImpl;Landroid/content/Intent;)V

    .line 739
    .local v0, conn:Landroid/content/ServiceConnection;
    iget-object v4, p2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget v4, v4, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    invoke-static {v4}, Landroid/os/UserId;->getUserId(I)I

    move-result v3

    .line 742
    .local v3, userId:I
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v1

    .line 744
    .local v1, token:J
    :try_start_11
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const/4 v5, 0x1

    invoke-virtual {v4, p1, v0, v5, v3}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;II)Z
    :try_end_17
    .catchall {:try_start_11 .. :try_end_17} :catchall_1b

    .line 746
    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 748
    return-void

    .line 746
    :catchall_1b
    move-exception v4

    invoke-static {v1, v2}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v4
.end method

.method private dumpAppWidgetId(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;ILjava/io/PrintWriter;)V
    .registers 5
    .parameter "id"
    .parameter "index"
    .parameter "pw"

    .prologue
    .line 316
    const-string v0, "  ["

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    invoke-virtual {p3, p2}, Ljava/io/PrintWriter;->print(I)V

    const-string v0, "] id="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 317
    iget v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(I)V

    .line 318
    const-string v0, "    hostId="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 319
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(I)V

    const/16 v0, 0x20

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(C)V

    .line 320
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const/16 v0, 0x2f

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(C)V

    .line 321
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(I)V

    .line 322
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v0, :cond_4c

    .line 323
    const-string v0, "    provider="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 324
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v0}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 326
    :cond_4c
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    if-eqz v0, :cond_5c

    .line 327
    const-string v0, "    host.callbacks="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 329
    :cond_5c
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    if-eqz v0, :cond_6a

    .line 330
    const-string v0, "    views="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 332
    :cond_6a
    return-void
.end method

.method private dumpHost(Lcom/android/server/AppWidgetServiceImpl$Host;ILjava/io/PrintWriter;)V
    .registers 5
    .parameter "host"
    .parameter "index"
    .parameter "pw"

    .prologue
    .line 306
    const-string v0, "  ["

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    invoke-virtual {p3, p2}, Ljava/io/PrintWriter;->print(I)V

    const-string v0, "] hostId="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 307
    iget v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(I)V

    const/16 v0, 0x20

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(C)V

    .line 308
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    const/16 v0, 0x2f

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(C)V

    .line 309
    iget v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(I)V

    const/16 v0, 0x3a

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(C)V

    .line 310
    const-string v0, "    callbacks="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(Ljava/lang/Object;)V

    .line 311
    const-string v0, "    instances.size="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(I)V

    .line 312
    const-string v0, " zombie="

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-boolean v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->zombie:Z

    invoke-virtual {p3, v0}, Ljava/io/PrintWriter;->println(Z)V

    .line 313
    return-void
.end method

.method private dumpProvider(Lcom/android/server/AppWidgetServiceImpl$Provider;ILjava/io/PrintWriter;)V
    .registers 6
    .parameter "p"
    .parameter "index"
    .parameter "pw"

    .prologue
    .line 286
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    .line 287
    .local v0, info:Landroid/appwidget/AppWidgetProviderInfo;
    const-string v1, "  ["

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    invoke-virtual {p3, p2}, Ljava/io/PrintWriter;->print(I)V

    const-string v1, "] provider "

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 288
    iget-object v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v1}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 289
    const/16 v1, 0x3a

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->println(C)V

    .line 290
    const-string v1, "    min=("

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->minWidth:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 291
    const-string v1, "x"

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->minHeight:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 292
    const-string v1, ")   minResize=("

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->minResizeWidth:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 293
    const-string v1, "x"

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->minResizeHeight:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 294
    const-string v1, ") updatePeriodMillis="

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 295
    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->updatePeriodMillis:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 296
    const-string v1, " resizeMode="

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 297
    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->resizeMode:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 298
    const-string v1, " autoAdvanceViewId="

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 299
    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->autoAdvanceViewId:I

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(I)V

    .line 300
    const-string v1, " initialLayout=#"

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 301
    iget v1, v0, Landroid/appwidget/AppWidgetProviderInfo;->initialLayout:I

    invoke-static {v1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    .line 302
    const-string v1, " zombie="

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    iget-boolean v1, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    invoke-virtual {p3, v1}, Ljava/io/PrintWriter;->println(Z)V

    .line 303
    return-void
.end method

.method private ensureStateLoadedLocked()V
    .registers 2

    .prologue
    .line 381
    iget-boolean v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mStateLoaded:Z

    if-nez v0, :cond_d

    .line 382
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->loadAppWidgetList()V

    .line 383
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->loadStateLocked()V

    .line 384
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mStateLoaded:Z

    .line 386
    :cond_d
    return-void
.end method

.method static getAppWidgetIds(Lcom/android/server/AppWidgetServiceImpl$Provider;)[I
    .registers 5
    .parameter "p"

    .prologue
    .line 1226
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v2

    .line 1227
    .local v2, instancesSize:I
    new-array v0, v2, [I

    .line 1228
    .local v0, appWidgetIds:[I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_9
    if-ge v1, v2, :cond_1a

    .line 1229
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    iget v3, v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    aput v3, v0, v1

    .line 1228
    add-int/lit8 v1, v1, 0x1

    goto :goto_9

    .line 1231
    :cond_1a
    return-object v0
.end method

.method static getSettingsFile(I)Ljava/io/File;
    .registers 4
    .parameter "userId"

    .prologue
    .line 1613
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "/data/system/users/"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "/"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "appwidgets.xml"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    return-object v0
.end method

.method private incrementAppWidgetServiceRefCount(ILandroid/content/Intent$FilterComparison;)V
    .registers 5
    .parameter "appWidgetId"
    .parameter "fc"

    .prologue
    .line 752
    const/4 v0, 0x0

    .line 753
    .local v0, appWidgetIds:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v1, p2}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_19

    .line 754
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .end local v0           #appWidgetIds:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    check-cast v0, Ljava/util/HashSet;

    .line 759
    .restart local v0       #appWidgetIds:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    :goto_11
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 760
    return-void

    .line 756
    :cond_19
    new-instance v0, Ljava/util/HashSet;

    .end local v0           #appWidgetIds:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    .line 757
    .restart local v0       #appWidgetIds:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v1, p2, v0}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_11
.end method

.method private parseProviderInfoXml(Landroid/content/ComponentName;Landroid/content/pm/ResolveInfo;)Lcom/android/server/AppWidgetServiceImpl$Provider;
    .registers 20
    .parameter "component"
    .parameter "ri"

    .prologue
    .line 1247
    const/4 v7, 0x0

    .line 1249
    .local v7, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    move-object/from16 v0, p2

    iget-object v1, v0, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    .line 1250
    .local v1, activityInfo:Landroid/content/pm/ActivityInfo;
    const/4 v9, 0x0

    .line 1252
    .local v9, parser:Landroid/content/res/XmlResourceParser;
    :try_start_6
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v14}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v14

    const-string v15, "android.appwidget.provider"

    invoke-virtual {v1, v14, v15}, Landroid/content/pm/ActivityInfo;->loadXmlMetaData(Landroid/content/pm/PackageManager;Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v9

    .line 1254
    if-nez v9, :cond_3d

    .line 1255
    const-string v14, "AppWidgetServiceImpl"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "No android.appwidget.provider meta-data for AppWidget provider \'"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v15

    const/16 v16, 0x27

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_36
    .catchall {:try_start_6 .. :try_end_36} :catchall_167
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_36} :catch_13e

    .line 1257
    const/4 v8, 0x0

    .line 1327
    if-eqz v9, :cond_3c

    .line 1328
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->close()V

    .line 1330
    :cond_3c
    :goto_3c
    return-object v8

    .line 1260
    :cond_3d
    :try_start_3d
    invoke-static {v9}, Landroid/util/Xml;->asAttributeSet(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;

    move-result-object v2

    .line 1264
    .local v2, attrs:Landroid/util/AttributeSet;
    :cond_41
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v12

    .local v12, type:I
    const/4 v14, 0x1

    if-eq v12, v14, :cond_4b

    const/4 v14, 0x2

    if-ne v12, v14, :cond_41

    .line 1268
    :cond_4b
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v6

    .line 1269
    .local v6, nodeName:Ljava/lang/String;
    const-string v14, "appwidget-provider"

    invoke-virtual {v14, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-nez v14, :cond_7e

    .line 1270
    const-string v14, "AppWidgetServiceImpl"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Meta-data does not start with appwidget-provider tag for AppWidget provider \'"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v15

    const/16 v16, 0x27

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_77
    .catchall {:try_start_3d .. :try_end_77} :catchall_167
    .catch Ljava/lang/Exception; {:try_start_3d .. :try_end_77} :catch_13e

    .line 1272
    const/4 v8, 0x0

    .line 1327
    if-eqz v9, :cond_3c

    .line 1328
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->close()V

    goto :goto_3c

    .line 1275
    :cond_7e
    :try_start_7e
    new-instance v8, Lcom/android/server/AppWidgetServiceImpl$Provider;

    invoke-direct {v8}, Lcom/android/server/AppWidgetServiceImpl$Provider;-><init>()V
    :try_end_83
    .catchall {:try_start_7e .. :try_end_83} :catchall_167
    .catch Ljava/lang/Exception; {:try_start_7e .. :try_end_83} :catch_13e

    .line 1276
    .end local v7           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .local v8, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :try_start_83
    new-instance v5, Landroid/appwidget/AppWidgetProviderInfo;

    invoke-direct {v5}, Landroid/appwidget/AppWidgetProviderInfo;-><init>()V

    iput-object v5, v8, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    .line 1277
    .local v5, info:Landroid/appwidget/AppWidgetProviderInfo;
    move-object/from16 v0, p1

    iput-object v0, v5, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    .line 1278
    iget-object v14, v1, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v14, v14, Landroid/content/pm/ApplicationInfo;->uid:I

    iput v14, v8, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    .line 1280
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v14}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v14

    iget-object v15, v1, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    invoke-virtual {v14, v15}, Landroid/content/pm/PackageManager;->getResourcesForApplication(Landroid/content/pm/ApplicationInfo;)Landroid/content/res/Resources;

    move-result-object v10

    .line 1283
    .local v10, res:Landroid/content/res/Resources;
    sget-object v14, Lcom/android/internal/R$styleable;->AppWidgetProviderInfo:[I

    invoke-virtual {v10, v2, v14}, Landroid/content/res/Resources;->obtainAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object v11

    .line 1289
    .local v11, sa:Landroid/content/res/TypedArray;
    const/4 v14, 0x0

    invoke-virtual {v11, v14}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v13

    .line 1291
    .local v13, value:Landroid/util/TypedValue;
    if-eqz v13, :cond_133

    iget v14, v13, Landroid/util/TypedValue;->data:I

    :goto_b1
    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minWidth:I

    .line 1292
    const/4 v14, 0x1

    invoke-virtual {v11, v14}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v13

    .line 1293
    if-eqz v13, :cond_136

    iget v14, v13, Landroid/util/TypedValue;->data:I

    :goto_bc
    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minHeight:I

    .line 1294
    const/16 v14, 0x8

    invoke-virtual {v11, v14}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v13

    .line 1296
    if-eqz v13, :cond_138

    iget v14, v13, Landroid/util/TypedValue;->data:I

    :goto_c8
    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minResizeWidth:I

    .line 1297
    const/16 v14, 0x9

    invoke-virtual {v11, v14}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v13

    .line 1299
    if-eqz v13, :cond_13b

    iget v14, v13, Landroid/util/TypedValue;->data:I

    :goto_d4
    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minResizeHeight:I

    .line 1300
    const/4 v14, 0x2

    const/4 v15, 0x0

    invoke-virtual {v11, v14, v15}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->updatePeriodMillis:I

    .line 1302
    const/4 v14, 0x3

    const/4 v15, 0x0

    invoke-virtual {v11, v14, v15}, Landroid/content/res/TypedArray;->getResourceId(II)I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->initialLayout:I

    .line 1304
    const/4 v14, 0x4

    invoke-virtual {v11, v14}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v3

    .line 1306
    .local v3, className:Ljava/lang/String;
    if-eqz v3, :cond_f8

    .line 1307
    new-instance v14, Landroid/content/ComponentName;

    invoke-virtual/range {p1 .. p1}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v14, v15, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    iput-object v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->configure:Landroid/content/ComponentName;

    .line 1309
    :cond_f8
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v14}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v14

    invoke-virtual {v1, v14}, Landroid/content/pm/ActivityInfo;->loadLabel(Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v14

    iput-object v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->label:Ljava/lang/String;

    .line 1310
    invoke-virtual/range {p2 .. p2}, Landroid/content/pm/ResolveInfo;->getIconResource()I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->icon:I

    .line 1311
    const/4 v14, 0x5

    const/4 v15, 0x0

    invoke-virtual {v11, v14, v15}, Landroid/content/res/TypedArray;->getResourceId(II)I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->previewImage:I

    .line 1313
    const/4 v14, 0x6

    const/4 v15, -0x1

    invoke-virtual {v11, v14, v15}, Landroid/content/res/TypedArray;->getResourceId(II)I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->autoAdvanceViewId:I

    .line 1315
    const/4 v14, 0x7

    const/4 v15, 0x0

    invoke-virtual {v11, v14, v15}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v14

    iput v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->resizeMode:I

    .line 1319
    invoke-virtual {v11}, Landroid/content/res/TypedArray;->recycle()V
    :try_end_12b
    .catchall {:try_start_83 .. :try_end_12b} :catchall_16e
    .catch Ljava/lang/Exception; {:try_start_83 .. :try_end_12b} :catch_171

    .line 1327
    if-eqz v9, :cond_130

    .line 1328
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->close()V

    :cond_130
    move-object v7, v8

    .line 1330
    .end local v8           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v7       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    goto/16 :goto_3c

    .line 1291
    .end local v3           #className:Ljava/lang/String;
    .end local v7           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v8       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_133
    const/4 v14, 0x0

    goto/16 :goto_b1

    .line 1293
    :cond_136
    const/4 v14, 0x0

    goto :goto_bc

    .line 1296
    :cond_138
    :try_start_138
    iget v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minWidth:I

    goto :goto_c8

    .line 1299
    :cond_13b
    iget v14, v5, Landroid/appwidget/AppWidgetProviderInfo;->minHeight:I
    :try_end_13d
    .catchall {:try_start_138 .. :try_end_13d} :catchall_16e
    .catch Ljava/lang/Exception; {:try_start_138 .. :try_end_13d} :catch_171

    goto :goto_d4

    .line 1320
    .end local v2           #attrs:Landroid/util/AttributeSet;
    .end local v5           #info:Landroid/appwidget/AppWidgetProviderInfo;
    .end local v6           #nodeName:Ljava/lang/String;
    .end local v8           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .end local v10           #res:Landroid/content/res/Resources;
    .end local v11           #sa:Landroid/content/res/TypedArray;
    .end local v12           #type:I
    .end local v13           #value:Landroid/util/TypedValue;
    .restart local v7       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :catch_13e
    move-exception v4

    .line 1324
    .local v4, e:Ljava/lang/Exception;
    :goto_13f
    :try_start_13f
    const-string v14, "AppWidgetServiceImpl"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "XML parsing failed for AppWidget provider \'"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v15

    const/16 v16, 0x27

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_15f
    .catchall {:try_start_13f .. :try_end_15f} :catchall_167

    .line 1325
    const/4 v8, 0x0

    .line 1327
    if-eqz v9, :cond_3c

    .line 1328
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->close()V

    goto/16 :goto_3c

    .line 1327
    .end local v4           #e:Ljava/lang/Exception;
    :catchall_167
    move-exception v14

    :goto_168
    if-eqz v9, :cond_16d

    .line 1328
    invoke-interface {v9}, Landroid/content/res/XmlResourceParser;->close()V

    :cond_16d
    throw v14

    .line 1327
    .end local v7           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v2       #attrs:Landroid/util/AttributeSet;
    .restart local v6       #nodeName:Ljava/lang/String;
    .restart local v8       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v12       #type:I
    :catchall_16e
    move-exception v14

    move-object v7, v8

    .end local v8           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v7       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    goto :goto_168

    .line 1320
    .end local v7           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v8       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :catch_171
    move-exception v4

    move-object v7, v8

    .end local v8           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .restart local v7       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    goto :goto_13f
.end method

.method private unbindAppWidgetRemoteViewsServicesLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V
    .registers 7
    .parameter "id"

    .prologue
    .line 697
    iget v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    .line 699
    .local v0, appWidgetId:I
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v4}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 701
    .local v2, it:Ljava/util/Iterator;,"Ljava/util/Iterator<Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;>;"
    :cond_c
    :goto_c
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_36

    .line 702
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/util/Pair;

    .line 703
    .local v3, key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    iget-object v4, v3, Landroid/util/Pair;->first:Ljava/lang/Object;

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v4

    if-ne v4, v0, :cond_c

    .line 704
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v4, v3}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;

    .line 706
    .local v1, conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    invoke-virtual {v1}, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;->disconnect()V

    .line 707
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v4, v1}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 708
    invoke-interface {v2}, Ljava/util/Iterator;->remove()V

    goto :goto_c

    .line 714
    .end local v1           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .end local v3           #key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    :cond_36
    invoke-direct {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->decrementAppWidgetServiceRefCount(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V

    .line 715
    return-void
.end method


# virtual methods
.method addProviderLocked(Landroid/content/pm/ResolveInfo;)Z
    .registers 7
    .parameter "ri"

    .prologue
    const/4 v1, 0x0

    .line 1144
    iget-object v2, p1, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v2, v2, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v2, v2, Landroid/content/pm/ApplicationInfo;->flags:I

    const/high16 v3, 0x4

    and-int/2addr v2, v3

    if-eqz v2, :cond_d

    .line 1156
    :cond_c
    :goto_c
    return v1

    .line 1147
    :cond_d
    iget-object v2, p1, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    invoke-virtual {v2}, Landroid/content/pm/ActivityInfo;->isEnabled()Z

    move-result v2

    if-eqz v2, :cond_c

    .line 1150
    new-instance v2, Landroid/content/ComponentName;

    iget-object v3, p1, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v3, v3, Landroid/content/pm/ComponentInfo;->packageName:Ljava/lang/String;

    iget-object v4, p1, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v4, v4, Landroid/content/pm/ComponentInfo;->name:Ljava/lang/String;

    invoke-direct {v2, v3, v4}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-direct {p0, v2, p1}, Lcom/android/server/AppWidgetServiceImpl;->parseProviderInfoXml(Landroid/content/ComponentName;Landroid/content/pm/ResolveInfo;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v0

    .line 1152
    .local v0, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-eqz v0, :cond_c

    .line 1153
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1154
    const/4 v1, 0x1

    goto :goto_c
.end method

.method addProvidersForPackageLocked(Ljava/lang/String;)V
    .registers 13
    .parameter "pkgName"

    .prologue
    .line 1643
    new-instance v4, Landroid/content/Intent;

    const-string v7, "android.appwidget.action.APPWIDGET_UPDATE"

    invoke-direct {v4, v7}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1644
    .local v4, intent:Landroid/content/Intent;
    invoke-virtual {v4, p1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 1647
    :try_start_a
    iget-object v7, p0, Lcom/android/server/AppWidgetServiceImpl;->mPm:Landroid/content/pm/IPackageManager;

    iget-object v8, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v8}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v8

    invoke-virtual {v4, v8}, Landroid/content/Intent;->resolveTypeIfNeeded(Landroid/content/ContentResolver;)Ljava/lang/String;

    move-result-object v8

    const/16 v9, 0x80

    iget v10, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-interface {v7, v4, v8, v9, v10}, Landroid/content/pm/IPackageManager;->queryIntentReceivers(Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    :try_end_1d
    .catch Landroid/os/RemoteException; {:try_start_a .. :try_end_1d} :catch_38

    move-result-object v2

    .line 1654
    .local v2, broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    if-nez v2, :cond_3a

    const/4 v0, 0x0

    .line 1655
    .local v0, N:I
    :goto_21
    const/4 v3, 0x0

    .local v3, i:I
    :goto_22
    if-ge v3, v0, :cond_39

    .line 1656
    invoke-interface {v2, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/content/pm/ResolveInfo;

    .line 1657
    .local v6, ri:Landroid/content/pm/ResolveInfo;
    iget-object v1, v6, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    .line 1658
    .local v1, ai:Landroid/content/pm/ActivityInfo;
    iget-object v7, v1, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v7, v7, Landroid/content/pm/ApplicationInfo;->flags:I

    const/high16 v8, 0x4

    and-int/2addr v7, v8

    if-eqz v7, :cond_3f

    .line 1655
    :cond_35
    :goto_35
    add-int/lit8 v3, v3, 0x1

    goto :goto_22

    .line 1650
    .end local v0           #N:I
    .end local v1           #ai:Landroid/content/pm/ActivityInfo;
    .end local v2           #broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    .end local v3           #i:I
    .end local v6           #ri:Landroid/content/pm/ResolveInfo;
    :catch_38
    move-exception v5

    .line 1665
    :cond_39
    return-void

    .line 1654
    .restart local v2       #broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    :cond_3a
    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v0

    goto :goto_21

    .line 1661
    .restart local v0       #N:I
    .restart local v1       #ai:Landroid/content/pm/ActivityInfo;
    .restart local v3       #i:I
    .restart local v6       #ri:Landroid/content/pm/ResolveInfo;
    :cond_3f
    iget-object v7, v1, Landroid/content/pm/ComponentInfo;->packageName:Ljava/lang/String;

    invoke-virtual {p1, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_35

    .line 1662
    invoke-virtual {p0, v6}, Lcom/android/server/AppWidgetServiceImpl;->addProviderLocked(Landroid/content/pm/ResolveInfo;)Z

    goto :goto_35
.end method

.method public allocateAppWidgetId(Ljava/lang/String;I)I
    .registers 9
    .parameter "packageName"
    .parameter "hostId"

    .prologue
    .line 389
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->enforceCallingUid(Ljava/lang/String;)I

    move-result v1

    .line 390
    .local v1, callingUid:I
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v5

    .line 391
    :try_start_7
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 392
    iget v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mNextAppWidgetId:I

    add-int/lit8 v4, v0, 0x1

    iput v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mNextAppWidgetId:I

    .line 394
    .local v0, appWidgetId:I
    invoke-virtual {p0, v1, p1, p2}, Lcom/android/server/AppWidgetServiceImpl;->lookupOrAddHostLocked(ILjava/lang/String;I)Lcom/android/server/AppWidgetServiceImpl$Host;

    move-result-object v2

    .line 396
    .local v2, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    new-instance v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    invoke-direct {v3}, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;-><init>()V

    .line 397
    .local v3, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    iput v0, v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    .line 398
    iput-object v2, v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 400
    iget-object v4, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v4, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 401
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v4, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 403
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 405
    monitor-exit v5

    return v0

    .line 406
    .end local v0           #appWidgetId:I
    .end local v2           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    .end local v3           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_2c
    move-exception v4

    monitor-exit v5
    :try_end_2e
    .catchall {:try_start_7 .. :try_end_2e} :catchall_2c

    throw v4
.end method

.method public bindAppWidgetId(ILandroid/content/ComponentName;)V
    .registers 7
    .parameter "appWidgetId"
    .parameter "provider"

    .prologue
    .line 555
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.BIND_APPWIDGET"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "bindAppWidgetId appWidgetId="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " provider="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 557
    invoke-direct {p0, p1, p2}, Lcom/android/server/AppWidgetServiceImpl;->bindAppWidgetIdImpl(ILandroid/content/ComponentName;)V

    .line 558
    return-void
.end method

.method public bindAppWidgetIdIfAllowed(Ljava/lang/String;ILandroid/content/ComponentName;)Z
    .registers 8
    .parameter "packageName"
    .parameter "appWidgetId"
    .parameter "provider"

    .prologue
    .line 563
    :try_start_0
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.BIND_APPWIDGET"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_8
    .catch Ljava/lang/SecurityException; {:try_start_0 .. :try_end_8} :catch_d

    .line 569
    :cond_8
    invoke-direct {p0, p2, p3}, Lcom/android/server/AppWidgetServiceImpl;->bindAppWidgetIdImpl(ILandroid/content/ComponentName;)V

    .line 570
    const/4 v1, 0x1

    :goto_c
    return v1

    .line 564
    :catch_d
    move-exception v0

    .line 565
    .local v0, se:Ljava/lang/SecurityException;
    invoke-direct {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->callerHasBindAppWidgetPermission(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_8

    .line 566
    const/4 v1, 0x0

    goto :goto_c
.end method

.method public bindRemoteViewsService(ILandroid/content/Intent;Landroid/os/IBinder;)V
    .registers 21
    .parameter "appWidgetId"
    .parameter "intent"
    .parameter "connection"

    .prologue
    .line 617
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v14

    .line 618
    :try_start_5
    invoke-direct/range {p0 .. p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 619
    invoke-virtual/range {p0 .. p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v7

    .line 620
    .local v7, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-nez v7, :cond_19

    .line 621
    new-instance v13, Ljava/lang/IllegalArgumentException;

    const-string v15, "bad appWidgetId"

    invoke-direct {v13, v15}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v13

    .line 664
    .end local v7           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_16
    move-exception v13

    monitor-exit v14
    :try_end_18
    .catchall {:try_start_5 .. :try_end_18} :catchall_16

    throw v13

    .line 623
    .restart local v7       #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_19
    :try_start_19
    invoke-virtual/range {p2 .. p2}, Landroid/content/Intent;->getComponent()Landroid/content/ComponentName;
    :try_end_1c
    .catchall {:try_start_19 .. :try_end_1c} :catchall_16

    move-result-object v2

    .line 625
    .local v2, componentName:Landroid/content/ComponentName;
    :try_start_1d
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v13}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v13

    const/16 v15, 0x1000

    invoke-virtual {v13, v2, v15}, Landroid/content/pm/PackageManager;->getServiceInfo(Landroid/content/ComponentName;I)Landroid/content/pm/ServiceInfo;

    move-result-object v9

    .line 627
    .local v9, si:Landroid/content/pm/ServiceInfo;
    const-string v13, "android.permission.BIND_REMOTEVIEWS"

    iget-object v15, v9, Landroid/content/pm/ServiceInfo;->permission:Ljava/lang/String;

    invoke-virtual {v13, v15}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_68

    .line 628
    new-instance v13, Ljava/lang/SecurityException;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Selected service does not require android.permission.BIND_REMOTEVIEWS: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v13, v15}, Ljava/lang/SecurityException;-><init>(Ljava/lang/String;)V

    throw v13
    :try_end_4e
    .catchall {:try_start_1d .. :try_end_4e} :catchall_16
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_1d .. :try_end_4e} :catch_4e

    .line 631
    .end local v9           #si:Landroid/content/pm/ServiceInfo;
    :catch_4e
    move-exception v5

    .line 632
    .local v5, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    :try_start_4f
    new-instance v13, Ljava/lang/IllegalArgumentException;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Unknown component "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v13, v15}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v13

    .line 638
    .end local v5           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v9       #si:Landroid/content/pm/ServiceInfo;
    :cond_68
    const/4 v3, 0x0

    .line 639
    .local v3, conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    new-instance v6, Landroid/content/Intent$FilterComparison;

    move-object/from16 v0, p2

    invoke-direct {v6, v0}, Landroid/content/Intent$FilterComparison;-><init>(Landroid/content/Intent;)V

    .line 640
    .local v6, fc:Landroid/content/Intent$FilterComparison;
    invoke-static/range {p1 .. p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    invoke-static {v13, v6}, Landroid/util/Pair;->create(Ljava/lang/Object;Ljava/lang/Object;)Landroid/util/Pair;

    move-result-object v8

    .line 641
    .local v8, key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v13, v8}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_9d

    .line 642
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v13, v8}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;

    .line 643
    .restart local v3       #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    invoke-virtual {v3}, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;->disconnect()V

    .line 644
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v13, v3}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 645
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v13, v8}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    :cond_9d
    move-object v4, v3

    .line 648
    .end local v3           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .local v4, conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    iget-object v13, v7, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget v13, v13, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    invoke-static {v13}, Landroid/os/UserId;->getUserId(I)I

    move-result v12

    .line 651
    .local v12, userId:I
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J
    :try_end_a9
    .catchall {:try_start_4f .. :try_end_a9} :catchall_16

    move-result-wide v10

    .line 653
    .local v10, token:J
    :try_start_aa
    new-instance v3, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;

    move-object/from16 v0, p3

    invoke-direct {v3, v8, v0}, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;-><init>(Landroid/util/Pair;Landroid/os/IBinder;)V
    :try_end_b1
    .catchall {:try_start_aa .. :try_end_b1} :catchall_ce

    .line 654
    .end local v4           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .restart local v3       #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    :try_start_b1
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const/4 v15, 0x1

    move-object/from16 v0, p2

    invoke-virtual {v13, v0, v3, v15, v12}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;II)Z

    .line 655
    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v13, v8, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_c2
    .catchall {:try_start_b1 .. :try_end_c2} :catchall_d4

    .line 657
    :try_start_c2
    invoke-static {v10, v11}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 663
    move-object/from16 v0, p0

    move/from16 v1, p1

    invoke-direct {v0, v1, v6}, Lcom/android/server/AppWidgetServiceImpl;->incrementAppWidgetServiceRefCount(ILandroid/content/Intent$FilterComparison;)V

    .line 664
    monitor-exit v14

    .line 665
    return-void

    .line 657
    .end local v3           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .restart local v4       #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    :catchall_ce
    move-exception v13

    move-object v3, v4

    .end local v4           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .restart local v3       #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    :goto_d0
    invoke-static {v10, v11}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v13
    :try_end_d4
    .catchall {:try_start_c2 .. :try_end_d4} :catchall_16

    :catchall_d4
    move-exception v13

    goto :goto_d0
.end method

.method canAccessAppWidgetId(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;I)Z
    .registers 6
    .parameter "id"
    .parameter "callingUid"

    .prologue
    const/4 v0, 0x1

    .line 1054
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget v1, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    if-ne v1, p2, :cond_8

    .line 1067
    :cond_7
    :goto_7
    return v0

    .line 1058
    :cond_8
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v1, :cond_12

    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget v1, v1, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    if-eq v1, p2, :cond_7

    .line 1062
    :cond_12
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v2, "android.permission.BIND_APPWIDGET"

    invoke-virtual {v1, v2}, Landroid/content/Context;->checkCallingOrSelfPermission(Ljava/lang/String;)I

    move-result v1

    if-eqz v1, :cond_7

    .line 1067
    const/4 v0, 0x0

    goto :goto_7
.end method

.method cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V
    .registers 6
    .parameter "p"

    .prologue
    .line 497
    iget-object v2, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    if-eqz v2, :cond_1a

    .line 498
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAlarmManager:Landroid/app/AlarmManager;

    iget-object v3, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    invoke-virtual {v2, v3}, Landroid/app/AlarmManager;->cancel(Landroid/app/PendingIntent;)V

    .line 499
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v0

    .line 501
    .local v0, token:J
    :try_start_f
    iget-object v2, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    invoke-virtual {v2}, Landroid/app/PendingIntent;->cancel()V
    :try_end_14
    .catchall {:try_start_f .. :try_end_14} :catchall_1b

    .line 503
    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 505
    const/4 v2, 0x0

    iput-object v2, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    .line 507
    .end local v0           #token:J
    :cond_1a
    return-void

    .line 503
    .restart local v0       #token:J
    :catchall_1b
    move-exception v2

    invoke-static {v0, v1}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v2
.end method

.method computeMaximumWidgetBitmapMemory()V
    .registers 6

    .prologue
    .line 190
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v4, "window"

    invoke-virtual {v3, v4}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/view/WindowManager;

    .line 191
    .local v2, wm:Landroid/view/WindowManager;
    invoke-interface {v2}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    invoke-virtual {v3}, Landroid/view/Display;->getRawHeight()I

    move-result v0

    .line 192
    .local v0, height:I
    invoke-interface {v2}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    invoke-virtual {v3}, Landroid/view/Display;->getRawWidth()I

    move-result v1

    .line 195
    .local v1, width:I
    mul-int/lit8 v3, v1, 0x6

    mul-int/2addr v3, v0

    iput v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mMaxWidgetBitmapMemory:I

    .line 196
    return-void
.end method

.method public deleteAllHosts()V
    .registers 8

    .prologue
    .line 433
    iget-object v6, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v6

    .line 434
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 435
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    .line 436
    .local v1, callingUid:I
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 437
    .local v0, N:I
    const/4 v2, 0x0

    .line 438
    .local v2, changed:Z
    add-int/lit8 v4, v0, -0x1

    .local v4, i:I
    :goto_13
    if-ltz v4, :cond_28

    .line 439
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v5, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 440
    .local v3, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    iget v5, v3, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    if-ne v5, v1, :cond_25

    .line 441
    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->deleteHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 442
    const/4 v2, 0x1

    .line 438
    :cond_25
    add-int/lit8 v4, v4, -0x1

    goto :goto_13

    .line 445
    .end local v3           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_28
    if-eqz v2, :cond_2d

    .line 446
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 448
    :cond_2d
    monitor-exit v6

    .line 449
    return-void

    .line 448
    .end local v0           #N:I
    .end local v1           #callingUid:I
    .end local v2           #changed:Z
    .end local v4           #i:I
    :catchall_2f
    move-exception v5

    monitor-exit v6
    :try_end_31
    .catchall {:try_start_3 .. :try_end_31} :catchall_2f

    throw v5
.end method

.method public deleteAppWidgetId(I)V
    .registers 5
    .parameter "appWidgetId"

    .prologue
    .line 410
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 411
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 412
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 413
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-eqz v0, :cond_12

    .line 414
    invoke-virtual {p0, v0}, Lcom/android/server/AppWidgetServiceImpl;->deleteAppWidgetLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V

    .line 415
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 417
    :cond_12
    monitor-exit v2

    .line 418
    return-void

    .line 417
    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_14
    move-exception v1

    monitor-exit v2
    :try_end_16
    .catchall {:try_start_3 .. :try_end_16} :catchall_14

    throw v1
.end method

.method deleteAppWidgetLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V
    .registers 7
    .parameter "id"

    .prologue
    .line 466
    invoke-direct {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->unbindAppWidgetRemoteViewsServicesLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V

    .line 468
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 469
    .local v0, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    iget-object v3, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 470
    invoke-virtual {p0, v0}, Lcom/android/server/AppWidgetServiceImpl;->pruneHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 472
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 474
    iget-object v2, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 475
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-eqz v2, :cond_5b

    .line 476
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 477
    iget-boolean v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-nez v3, :cond_5b

    .line 479
    new-instance v1, Landroid/content/Intent;

    const-string v3, "android.appwidget.action.APPWIDGET_DELETED"

    invoke-direct {v1, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 480
    .local v1, intent:Landroid/content/Intent;
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v3, v3, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 481
    const-string v3, "appWidgetId"

    iget v4, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-virtual {v1, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 482
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    iget v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v3, v1, v4}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;I)V

    .line 483
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-nez v3, :cond_5b

    .line 485
    invoke-virtual {p0, v2}, Lcom/android/server/AppWidgetServiceImpl;->cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 488
    new-instance v1, Landroid/content/Intent;

    .end local v1           #intent:Landroid/content/Intent;
    const-string v3, "android.appwidget.action.APPWIDGET_DISABLED"

    invoke-direct {v1, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 489
    .restart local v1       #intent:Landroid/content/Intent;
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v3, v3, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 490
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    iget v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v3, v1, v4}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;I)V

    .line 494
    .end local v1           #intent:Landroid/content/Intent;
    :cond_5b
    return-void
.end method

.method public deleteHost(I)V
    .registers 6
    .parameter "hostId"

    .prologue
    .line 421
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v3

    .line 422
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 423
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    .line 424
    .local v0, callingUid:I
    invoke-virtual {p0, v0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupHostLocked(II)Lcom/android/server/AppWidgetServiceImpl$Host;

    move-result-object v1

    .line 425
    .local v1, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    if-eqz v1, :cond_16

    .line 426
    invoke-virtual {p0, v1}, Lcom/android/server/AppWidgetServiceImpl;->deleteHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 427
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 429
    :cond_16
    monitor-exit v3

    .line 430
    return-void

    .line 429
    .end local v0           #callingUid:I
    .end local v1           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    :catchall_18
    move-exception v2

    monitor-exit v3
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_18

    throw v2
.end method

.method deleteHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V
    .registers 6
    .parameter "host"

    .prologue
    .line 452
    iget-object v3, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 453
    .local v0, N:I
    add-int/lit8 v1, v0, -0x1

    .local v1, i:I
    :goto_8
    if-ltz v1, :cond_18

    .line 454
    iget-object v3, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 455
    .local v2, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    invoke-virtual {p0, v2}, Lcom/android/server/AppWidgetServiceImpl;->deleteAppWidgetLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;)V

    .line 453
    add-int/lit8 v1, v1, -0x1

    goto :goto_8

    .line 457
    .end local v2           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_18
    iget-object v3, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->clear()V

    .line 458
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 459
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedHosts:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 461
    const/4 v3, 0x0

    iput-object v3, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    .line 462
    return-void
.end method

.method dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .registers 8
    .parameter "fd"
    .parameter "pw"
    .parameter "args"

    .prologue
    .line 335
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v3, "android.permission.DUMP"

    invoke-virtual {v2, v3}, Landroid/content/Context;->checkCallingOrSelfPermission(Ljava/lang/String;)I

    move-result v2

    if-eqz v2, :cond_33

    .line 337
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Permission Denial: can\'t dump from from pid="

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

    .line 378
    :goto_32
    return-void

    .line 343
    :cond_33
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v3

    .line 344
    :try_start_36
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 345
    .local v0, N:I
    const-string v2, "Providers:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 346
    const/4 v1, 0x0

    .local v1, i:I
    :goto_42
    if-ge v1, v0, :cond_52

    .line 347
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    invoke-direct {p0, v2, v1, p2}, Lcom/android/server/AppWidgetServiceImpl;->dumpProvider(Lcom/android/server/AppWidgetServiceImpl$Provider;ILjava/io/PrintWriter;)V

    .line 346
    add-int/lit8 v1, v1, 0x1

    goto :goto_42

    .line 350
    :cond_52
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 351
    const-string v2, " "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 352
    const-string v2, "AppWidgetIds:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 353
    const/4 v1, 0x0

    :goto_63
    if-ge v1, v0, :cond_73

    .line 354
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    invoke-direct {p0, v2, v1, p2}, Lcom/android/server/AppWidgetServiceImpl;->dumpAppWidgetId(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;ILjava/io/PrintWriter;)V

    .line 353
    add-int/lit8 v1, v1, 0x1

    goto :goto_63

    .line 357
    :cond_73
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 358
    const-string v2, " "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 359
    const-string v2, "Hosts:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 360
    const/4 v1, 0x0

    :goto_84
    if-ge v1, v0, :cond_94

    .line 361
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Host;

    invoke-direct {p0, v2, v1, p2}, Lcom/android/server/AppWidgetServiceImpl;->dumpHost(Lcom/android/server/AppWidgetServiceImpl$Host;ILjava/io/PrintWriter;)V

    .line 360
    add-int/lit8 v1, v1, 0x1

    goto :goto_84

    .line 364
    :cond_94
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedProviders:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 365
    const-string v2, " "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 366
    const-string v2, "Deleted Providers:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 367
    const/4 v1, 0x0

    :goto_a5
    if-ge v1, v0, :cond_b5

    .line 368
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedProviders:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    invoke-direct {p0, v2, v1, p2}, Lcom/android/server/AppWidgetServiceImpl;->dumpProvider(Lcom/android/server/AppWidgetServiceImpl$Provider;ILjava/io/PrintWriter;)V

    .line 367
    add-int/lit8 v1, v1, 0x1

    goto :goto_a5

    .line 371
    :cond_b5
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedHosts:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 372
    const-string v2, " "

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 373
    const-string v2, "Deleted Hosts:"

    invoke-virtual {p2, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 374
    const/4 v1, 0x0

    :goto_c6
    if-ge v1, v0, :cond_d6

    .line 375
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedHosts:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Host;

    invoke-direct {p0, v2, v1, p2}, Lcom/android/server/AppWidgetServiceImpl;->dumpHost(Lcom/android/server/AppWidgetServiceImpl$Host;ILjava/io/PrintWriter;)V

    .line 374
    add-int/lit8 v1, v1, 0x1

    goto :goto_c6

    .line 377
    :cond_d6
    monitor-exit v3

    goto/16 :goto_32

    .end local v0           #N:I
    .end local v1           #i:I
    :catchall_d9
    move-exception v2

    monitor-exit v3
    :try_end_db
    .catchall {:try_start_36 .. :try_end_db} :catchall_d9

    throw v2
.end method

.method enforceCallingUid(Ljava/lang/String;)I
    .registers 8
    .parameter "packageName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/IllegalArgumentException;
        }
    .end annotation

    .prologue
    .line 1347
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    .line 1350
    .local v0, callingUid:I
    :try_start_4
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->getUidForPackage(Ljava/lang/String;)I
    :try_end_7
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_4 .. :try_end_7} :catch_27

    move-result v2

    .line 1355
    .local v2, packageUid:I
    invoke-static {v0, v2}, Landroid/os/UserId;->isSameApp(II)Z

    move-result v3

    if-nez v3, :cond_41

    .line 1356
    new-instance v3, Ljava/lang/IllegalArgumentException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "packageName and uid don\'t match packageName="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 1351
    .end local v2           #packageUid:I
    :catch_27
    move-exception v1

    .line 1352
    .local v1, ex:Landroid/content/pm/PackageManager$NameNotFoundException;
    new-instance v3, Ljava/lang/IllegalArgumentException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "packageName and uid don\'t match packageName="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 1359
    .end local v1           #ex:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v2       #packageUid:I
    :cond_41
    return v0
.end method

.method public getAppWidgetIds(Landroid/content/ComponentName;)[I
    .registers 6
    .parameter "provider"

    .prologue
    .line 1235
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 1236
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 1237
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v0

    .line 1238
    .local v0, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-eqz v0, :cond_1a

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    iget v3, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    if-ne v1, v3, :cond_1a

    .line 1239
    invoke-static {v0}, Lcom/android/server/AppWidgetServiceImpl;->getAppWidgetIds(Lcom/android/server/AppWidgetServiceImpl$Provider;)[I

    move-result-object v1

    monitor-exit v2

    .line 1241
    :goto_19
    return-object v1

    :cond_1a
    const/4 v1, 0x0

    new-array v1, v1, [I

    monitor-exit v2

    goto :goto_19

    .line 1243
    .end local v0           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :catchall_1f
    move-exception v1

    monitor-exit v2
    :try_end_21
    .catchall {:try_start_3 .. :try_end_21} :catchall_1f

    throw v1
.end method

.method public getAppWidgetInfo(I)Landroid/appwidget/AppWidgetProviderInfo;
    .registers 5
    .parameter "appWidgetId"

    .prologue
    .line 781
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 782
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 783
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 784
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-eqz v0, :cond_1c

    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v1, :cond_1c

    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-boolean v1, v1, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-nez v1, :cond_1c

    .line 785
    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-object v1, v1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    monitor-exit v2

    .line 787
    :goto_1b
    return-object v1

    :cond_1c
    const/4 v1, 0x0

    monitor-exit v2

    goto :goto_1b

    .line 788
    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_1f
    move-exception v1

    monitor-exit v2
    :try_end_21
    .catchall {:try_start_3 .. :try_end_21} :catchall_1f

    throw v1
.end method

.method public getAppWidgetOptions(I)Landroid/os/Bundle;
    .registers 5
    .parameter "appWidgetId"

    .prologue
    .line 865
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 866
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 867
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 868
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-eqz v0, :cond_14

    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->options:Landroid/os/Bundle;

    if-eqz v1, :cond_14

    .line 869
    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->options:Landroid/os/Bundle;

    monitor-exit v2

    .line 871
    :goto_13
    return-object v1

    :cond_14
    sget-object v1, Landroid/os/Bundle;->EMPTY:Landroid/os/Bundle;

    monitor-exit v2

    goto :goto_13

    .line 873
    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_18
    move-exception v1

    monitor-exit v2
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_18

    throw v1
.end method

.method public getAppWidgetViews(I)Landroid/widget/RemoteViews;
    .registers 5
    .parameter "appWidgetId"

    .prologue
    .line 792
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 793
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 794
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 795
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-eqz v0, :cond_10

    .line 796
    iget-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    monitor-exit v2

    .line 798
    :goto_f
    return-object v1

    :cond_10
    const/4 v1, 0x0

    monitor-exit v2

    goto :goto_f

    .line 799
    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :catchall_13
    move-exception v1

    monitor-exit v2
    :try_end_15
    .catchall {:try_start_3 .. :try_end_15} :catchall_13

    throw v1
.end method

.method public getInstalledProviders()Ljava/util/List;
    .registers 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List",
            "<",
            "Landroid/appwidget/AppWidgetProviderInfo;",
            ">;"
        }
    .end annotation

    .prologue
    .line 803
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v5

    .line 804
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 805
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 806
    .local v0, N:I
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3, v0}, Ljava/util/ArrayList;-><init>(I)V

    .line 807
    .local v3, result:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Landroid/appwidget/AppWidgetProviderInfo;>;"
    const/4 v1, 0x0

    .local v1, i:I
    :goto_12
    if-ge v1, v0, :cond_28

    .line 808
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 809
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-boolean v4, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-nez v4, :cond_25

    .line 810
    iget-object v4, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    invoke-virtual {v3, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 807
    :cond_25
    add-int/lit8 v1, v1, 0x1

    goto :goto_12

    .line 813
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_28
    monitor-exit v5

    return-object v3

    .line 814
    .end local v0           #N:I
    .end local v1           #i:I
    .end local v3           #result:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Landroid/appwidget/AppWidgetProviderInfo;>;"
    :catchall_2a
    move-exception v4

    monitor-exit v5
    :try_end_2c
    .catchall {:try_start_3 .. :try_end_2c} :catchall_2a

    throw v4
.end method

.method getUidForPackage(Ljava/lang/String;)I
    .registers 6
    .parameter "packageName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/content/pm/PackageManager$NameNotFoundException;
        }
    .end annotation

    .prologue
    .line 1334
    const/4 v0, 0x0

    .line 1336
    .local v0, pkgInfo:Landroid/content/pm/PackageInfo;
    :try_start_1
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mPm:Landroid/content/pm/IPackageManager;

    const/4 v2, 0x0

    iget v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-interface {v1, p1, v2, v3}, Landroid/content/pm/IPackageManager;->getPackageInfo(Ljava/lang/String;II)Landroid/content/pm/PackageInfo;
    :try_end_9
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_9} :catch_1b

    move-result-object v0

    .line 1340
    :goto_a
    if-eqz v0, :cond_10

    iget-object v1, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    if-nez v1, :cond_16

    .line 1341
    :cond_10
    new-instance v1, Landroid/content/pm/PackageManager$NameNotFoundException;

    invoke-direct {v1}, Landroid/content/pm/PackageManager$NameNotFoundException;-><init>()V

    throw v1

    .line 1343
    :cond_16
    iget-object v1, v0, Landroid/content/pm/PackageInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    iget v1, v1, Landroid/content/pm/ApplicationInfo;->uid:I

    return v1

    .line 1337
    :catch_1b
    move-exception v1

    goto :goto_a
.end method

.method public hasBindAppWidgetPermission(Ljava/lang/String;)Z
    .registers 6
    .parameter "packageName"

    .prologue
    .line 589
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "hasBindAppWidgetPermission packageName="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 593
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v1

    .line 594
    :try_start_1d
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 595
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v0

    monitor-exit v1

    return v0

    .line 596
    :catchall_28
    move-exception v0

    monitor-exit v1
    :try_end_2a
    .catchall {:try_start_1d .. :try_end_2a} :catchall_28

    throw v0
.end method

.method loadAppWidgetList()V
    .registers 10

    .prologue
    .line 1127
    new-instance v3, Landroid/content/Intent;

    const-string v5, "android.appwidget.action.APPWIDGET_UPDATE"

    invoke-direct {v3, v5}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1129
    .local v3, intent:Landroid/content/Intent;
    :try_start_7
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mPm:Landroid/content/pm/IPackageManager;

    iget-object v6, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v6}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v6

    invoke-virtual {v3, v6}, Landroid/content/Intent;->resolveTypeIfNeeded(Landroid/content/ContentResolver;)Ljava/lang/String;

    move-result-object v6

    const/16 v7, 0x80

    iget v8, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-interface {v5, v3, v6, v7, v8}, Landroid/content/pm/IPackageManager;->queryIntentReceivers(Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;

    move-result-object v1

    .line 1133
    .local v1, broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    if-nez v1, :cond_2d

    const/4 v0, 0x0

    .line 1134
    .local v0, N:I
    :goto_1e
    const/4 v2, 0x0

    .local v2, i:I
    :goto_1f
    if-ge v2, v0, :cond_33

    .line 1135
    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/pm/ResolveInfo;

    .line 1136
    .local v4, ri:Landroid/content/pm/ResolveInfo;
    invoke-virtual {p0, v4}, Lcom/android/server/AppWidgetServiceImpl;->addProviderLocked(Landroid/content/pm/ResolveInfo;)Z

    .line 1134
    add-int/lit8 v2, v2, 0x1

    goto :goto_1f

    .line 1133
    .end local v0           #N:I
    .end local v2           #i:I
    .end local v4           #ri:Landroid/content/pm/ResolveInfo;
    :cond_2d
    invoke-interface {v1}, Ljava/util/List;->size()I
    :try_end_30
    .catch Landroid/os/RemoteException; {:try_start_7 .. :try_end_30} :catch_32

    move-result v0

    goto :goto_1e

    .line 1138
    .end local v1           #broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    :catch_32
    move-exception v5

    .line 1141
    :cond_33
    return-void
.end method

.method loadStateLocked()V
    .registers 7

    .prologue
    .line 1380
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->savedStateFile()Lcom/android/internal/os/AtomicFile;

    move-result-object v1

    .line 1382
    .local v1, file:Lcom/android/internal/os/AtomicFile;
    :try_start_4
    invoke-virtual {v1}, Lcom/android/internal/os/AtomicFile;->openRead()Ljava/io/FileInputStream;

    move-result-object v2

    .line 1383
    .local v2, stream:Ljava/io/FileInputStream;
    invoke-virtual {p0, v2}, Lcom/android/server/AppWidgetServiceImpl;->readStateFromFileLocked(Ljava/io/FileInputStream;)V
    :try_end_b
    .catch Ljava/io/FileNotFoundException; {:try_start_4 .. :try_end_b} :catch_2b

    .line 1385
    if-eqz v2, :cond_10

    .line 1387
    :try_start_d
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_10
    .catch Ljava/io/IOException; {:try_start_d .. :try_end_10} :catch_11
    .catch Ljava/io/FileNotFoundException; {:try_start_d .. :try_end_10} :catch_2b

    .line 1395
    .end local v2           #stream:Ljava/io/FileInputStream;
    :cond_10
    :goto_10
    return-void

    .line 1388
    .restart local v2       #stream:Ljava/io/FileInputStream;
    :catch_11
    move-exception v0

    .line 1389
    .local v0, e:Ljava/io/IOException;
    :try_start_12
    const-string v3, "AppWidgetServiceImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Failed to close state FileInputStream "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2a
    .catch Ljava/io/FileNotFoundException; {:try_start_12 .. :try_end_2a} :catch_2b

    goto :goto_10

    .line 1392
    .end local v0           #e:Ljava/io/IOException;
    .end local v2           #stream:Ljava/io/FileInputStream;
    :catch_2b
    move-exception v0

    .line 1393
    .local v0, e:Ljava/io/FileNotFoundException;
    const-string v3, "AppWidgetServiceImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Failed to read state: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_10
.end method

.method lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    .registers 7
    .parameter "appWidgetId"

    .prologue
    .line 1071
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    .line 1072
    .local v1, callingUid:I
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1073
    .local v0, N:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_b
    if-ge v2, v0, :cond_23

    .line 1074
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 1075
    .local v3, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    iget v4, v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    if-ne v4, p1, :cond_20

    invoke-virtual {p0, v3, v1}, Lcom/android/server/AppWidgetServiceImpl;->canAccessAppWidgetId(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;I)Z

    move-result v4

    if-eqz v4, :cond_20

    .line 1079
    .end local v3           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :goto_1f
    return-object v3

    .line 1073
    .restart local v3       #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_20
    add-int/lit8 v2, v2, 0x1

    goto :goto_b

    .line 1079
    .end local v3           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_23
    const/4 v3, 0x0

    goto :goto_1f
.end method

.method lookupHostLocked(II)Lcom/android/server/AppWidgetServiceImpl$Host;
    .registers 7
    .parameter "uid"
    .parameter "hostId"

    .prologue
    .line 1094
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1095
    .local v0, N:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_7
    if-ge v2, v0, :cond_1d

    .line 1096
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1097
    .local v1, h:Lcom/android/server/AppWidgetServiceImpl$Host;
    iget v3, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    if-ne v3, p1, :cond_1a

    iget v3, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    if-ne v3, p2, :cond_1a

    .line 1101
    .end local v1           #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :goto_19
    return-object v1

    .line 1095
    .restart local v1       #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_1a
    add-int/lit8 v2, v2, 0x1

    goto :goto_7

    .line 1101
    .end local v1           #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_1d
    const/4 v1, 0x0

    goto :goto_19
.end method

.method lookupOrAddHostLocked(ILjava/lang/String;I)Lcom/android/server/AppWidgetServiceImpl$Host;
    .registers 9
    .parameter "uid"
    .parameter "packageName"
    .parameter "hostId"

    .prologue
    .line 1105
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1106
    .local v0, N:I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_7
    if-ge v3, v0, :cond_21

    .line 1107
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v4, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1108
    .local v1, h:Lcom/android/server/AppWidgetServiceImpl$Host;
    iget v4, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    if-ne v4, p3, :cond_1e

    iget-object v4, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    invoke-virtual {v4, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1e

    .line 1117
    .end local v1           #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :goto_1d
    return-object v1

    .line 1106
    .restart local v1       #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_1e
    add-int/lit8 v3, v3, 0x1

    goto :goto_7

    .line 1112
    .end local v1           #h:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_21
    new-instance v2, Lcom/android/server/AppWidgetServiceImpl$Host;

    invoke-direct {v2}, Lcom/android/server/AppWidgetServiceImpl$Host;-><init>()V

    .line 1113
    .local v2, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    iput-object p2, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    .line 1114
    iput p1, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I

    .line 1115
    iput p3, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    .line 1116
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move-object v1, v2

    .line 1117
    goto :goto_1d
.end method

.method lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;
    .registers 6
    .parameter "provider"

    .prologue
    .line 1083
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1084
    .local v0, N:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_7
    if-ge v1, v0, :cond_1f

    .line 1085
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1086
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v3, v3, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v3, p1}, Landroid/content/ComponentName;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_1c

    .line 1090
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :goto_1b
    return-object v2

    .line 1084
    .restart local v2       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_1c
    add-int/lit8 v1, v1, 0x1

    goto :goto_7

    .line 1090
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_1f
    const/4 v2, 0x0

    goto :goto_1b
.end method

.method public notifyAppWidgetViewDataChanged([II)V
    .registers 8
    .parameter "appWidgetIds"
    .parameter "viewId"

    .prologue
    .line 895
    if-nez p1, :cond_3

    .line 910
    :cond_2
    :goto_2
    return-void

    .line 898
    :cond_3
    array-length v3, p1

    if-eqz v3, :cond_2

    .line 901
    array-length v0, p1

    .line 903
    .local v0, N:I
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v4

    .line 904
    :try_start_a
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 905
    const/4 v1, 0x0

    .local v1, i:I
    :goto_e
    if-ge v1, v0, :cond_1c

    .line 906
    aget v3, p1, v1

    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v2

    .line 907
    .local v2, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    invoke-virtual {p0, v2, p2}, Lcom/android/server/AppWidgetServiceImpl;->notifyAppWidgetViewDataChangedInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;I)V

    .line 905
    add-int/lit8 v1, v1, 0x1

    goto :goto_e

    .line 909
    .end local v2           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_1c
    monitor-exit v4

    goto :goto_2

    .end local v1           #i:I
    :catchall_1e
    move-exception v3

    monitor-exit v4
    :try_end_20
    .catchall {:try_start_a .. :try_end_20} :catchall_1e

    throw v3
.end method

.method notifyAppWidgetViewDataChangedInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;I)V
    .registers 14
    .parameter "id"
    .parameter "viewId"

    .prologue
    .line 964
    if-eqz p1, :cond_7c

    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v9, :cond_7c

    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-boolean v9, v9, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-nez v9, :cond_7c

    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-boolean v9, v9, Lcom/android/server/AppWidgetServiceImpl$Host;->zombie:Z

    if-nez v9, :cond_7c

    .line 966
    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v9, v9, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    if-eqz v9, :cond_21

    .line 969
    :try_start_18
    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v9, v9, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    iget v10, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-interface {v9, v10, p2}, Lcom/android/internal/appwidget/IAppWidgetHost;->viewDataChanged(II)V
    :try_end_21
    .catch Landroid/os/RemoteException; {:try_start_18 .. :try_end_21} :catch_70

    .line 979
    :cond_21
    :goto_21
    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v9, v9, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    if-nez v9, :cond_7c

    .line 980
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v9}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v5

    .line 981
    .local v5, keys:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/Intent$FilterComparison;>;"
    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :cond_31
    :goto_31
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-eqz v9, :cond_7c

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/Intent$FilterComparison;

    .line 982
    .local v4, key:Landroid/content/Intent$FilterComparison;
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mRemoteViewsServicesAppWidgets:Ljava/util/HashMap;

    invoke-virtual {v9, v4}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/util/HashSet;

    iget v10, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_31

    .line 983
    invoke-virtual {v4}, Landroid/content/Intent$FilterComparison;->getIntent()Landroid/content/Intent;

    move-result-object v3

    .line 985
    .local v3, intent:Landroid/content/Intent;
    new-instance v0, Lcom/android/server/AppWidgetServiceImpl$2;

    invoke-direct {v0, p0}, Lcom/android/server/AppWidgetServiceImpl$2;-><init>(Lcom/android/server/AppWidgetServiceImpl;)V

    .line 1006
    .local v0, conn:Landroid/content/ServiceConnection;
    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget v9, v9, Lcom/android/server/AppWidgetServiceImpl$Provider;->uid:I

    invoke-static {v9}, Landroid/os/UserId;->getUserId(I)I

    move-result v8

    .line 1008
    .local v8, userId:I
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v6

    .line 1010
    .local v6, token:J
    :try_start_66
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const/4 v10, 0x1

    invoke-virtual {v9, v3, v0, v10, v8}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;II)Z
    :try_end_6c
    .catchall {:try_start_66 .. :try_end_6c} :catchall_77

    .line 1012
    invoke-static {v6, v7}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    goto :goto_31

    .line 970
    .end local v0           #conn:Landroid/content/ServiceConnection;
    .end local v2           #i$:Ljava/util/Iterator;
    .end local v3           #intent:Landroid/content/Intent;
    .end local v4           #key:Landroid/content/Intent$FilterComparison;
    .end local v5           #keys:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/Intent$FilterComparison;>;"
    .end local v6           #token:J
    .end local v8           #userId:I
    :catch_70
    move-exception v1

    .line 973
    .local v1, e:Landroid/os/RemoteException;
    iget-object v9, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    const/4 v10, 0x0

    iput-object v10, v9, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    goto :goto_21

    .line 1012
    .end local v1           #e:Landroid/os/RemoteException;
    .restart local v0       #conn:Landroid/content/ServiceConnection;
    .restart local v2       #i$:Ljava/util/Iterator;
    .restart local v3       #intent:Landroid/content/Intent;
    .restart local v4       #key:Landroid/content/Intent$FilterComparison;
    .restart local v5       #keys:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/Intent$FilterComparison;>;"
    .restart local v6       #token:J
    .restart local v8       #userId:I
    :catchall_77
    move-exception v9

    invoke-static {v6, v7}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v9

    .line 1018
    .end local v0           #conn:Landroid/content/ServiceConnection;
    .end local v2           #i$:Ljava/util/Iterator;
    .end local v3           #intent:Landroid/content/Intent;
    .end local v4           #key:Landroid/content/Intent$FilterComparison;
    .end local v5           #keys:Ljava/util/Set;,"Ljava/util/Set<Landroid/content/Intent$FilterComparison;>;"
    .end local v6           #token:J
    .end local v8           #userId:I
    :cond_7c
    return-void
.end method

.method onBroadcastReceived(Landroid/content/Intent;)V
    .registers 15
    .parameter "intent"

    .prologue
    const/4 v11, 0x0

    .line 225
    invoke-virtual {p1}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 226
    .local v0, action:Ljava/lang/String;
    const/4 v1, 0x0

    .line 227
    .local v1, added:Z
    const/4 v3, 0x0

    .line 228
    .local v3, changed:Z
    const/4 v7, 0x0

    .line 229
    .local v7, pkgList:[Ljava/lang/String;
    const-string v10, "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"

    invoke-virtual {v10, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-eqz v10, :cond_1d

    .line 230
    const-string v10, "android.intent.extra.changed_package_list"

    invoke-virtual {p1, v10}, Landroid/content/Intent;->getStringArrayExtra(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    .line 231
    const/4 v1, 0x1

    .line 248
    :goto_17
    if-eqz v7, :cond_1c

    array-length v10, v7

    if-nez v10, :cond_4b

    .line 283
    :cond_1c
    :goto_1c
    return-void

    .line 232
    :cond_1d
    const-string v10, "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"

    invoke-virtual {v10, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-eqz v10, :cond_2d

    .line 233
    const-string v10, "android.intent.extra.changed_package_list"

    invoke-virtual {p1, v10}, Landroid/content/Intent;->getStringArrayExtra(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    .line 234
    const/4 v1, 0x0

    goto :goto_17

    .line 236
    :cond_2d
    invoke-virtual {p1}, Landroid/content/Intent;->getData()Landroid/net/Uri;

    move-result-object v9

    .line 237
    .local v9, uri:Landroid/net/Uri;
    if-eqz v9, :cond_1c

    .line 240
    invoke-virtual {v9}, Landroid/net/Uri;->getSchemeSpecificPart()Ljava/lang/String;

    move-result-object v8

    .line 241
    .local v8, pkgName:Ljava/lang/String;
    if-eqz v8, :cond_1c

    .line 244
    const/4 v10, 0x1

    new-array v7, v10, [Ljava/lang/String;

    .end local v7           #pkgList:[Ljava/lang/String;
    aput-object v8, v7, v11

    .line 245
    .restart local v7       #pkgList:[Ljava/lang/String;
    const-string v10, "android.intent.action.PACKAGE_ADDED"

    invoke-virtual {v10, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    .line 246
    const-string v10, "android.intent.action.PACKAGE_CHANGED"

    invoke-virtual {v10, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    goto :goto_17

    .line 251
    .end local v8           #pkgName:Ljava/lang/String;
    .end local v9           #uri:Landroid/net/Uri;
    :cond_4b
    if-nez v1, :cond_4f

    if-eqz v3, :cond_88

    .line 252
    :cond_4f
    iget-object v11, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v11

    .line 253
    :try_start_52
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 254
    invoke-virtual {p1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v4

    .line 255
    .local v4, extras:Landroid/os/Bundle;
    if-nez v3, :cond_66

    if-eqz v4, :cond_73

    const-string v10, "android.intent.extra.REPLACING"

    const/4 v12, 0x0

    invoke-virtual {v4, v10, v12}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v10

    if-eqz v10, :cond_73

    .line 257
    :cond_66
    move-object v2, v7

    .local v2, arr$:[Ljava/lang/String;
    array-length v6, v2

    .local v6, len$:I
    const/4 v5, 0x0

    .local v5, i$:I
    :goto_69
    if-ge v5, v6, :cond_80

    aget-object v8, v2, v5

    .line 259
    .restart local v8       #pkgName:Ljava/lang/String;
    invoke-virtual {p0, v8}, Lcom/android/server/AppWidgetServiceImpl;->updateProvidersForPackageLocked(Ljava/lang/String;)V

    .line 257
    add-int/lit8 v5, v5, 0x1

    goto :goto_69

    .line 263
    .end local v2           #arr$:[Ljava/lang/String;
    .end local v5           #i$:I
    .end local v6           #len$:I
    .end local v8           #pkgName:Ljava/lang/String;
    :cond_73
    move-object v2, v7

    .restart local v2       #arr$:[Ljava/lang/String;
    array-length v6, v2

    .restart local v6       #len$:I
    const/4 v5, 0x0

    .restart local v5       #i$:I
    :goto_76
    if-ge v5, v6, :cond_80

    aget-object v8, v2, v5

    .line 264
    .restart local v8       #pkgName:Ljava/lang/String;
    invoke-virtual {p0, v8}, Lcom/android/server/AppWidgetServiceImpl;->addProvidersForPackageLocked(Ljava/lang/String;)V

    .line 263
    add-int/lit8 v5, v5, 0x1

    goto :goto_76

    .line 267
    .end local v8           #pkgName:Ljava/lang/String;
    :cond_80
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 268
    monitor-exit v11

    goto :goto_1c

    .end local v2           #arr$:[Ljava/lang/String;
    .end local v4           #extras:Landroid/os/Bundle;
    .end local v5           #i$:I
    .end local v6           #len$:I
    :catchall_85
    move-exception v10

    monitor-exit v11
    :try_end_87
    .catchall {:try_start_52 .. :try_end_87} :catchall_85

    throw v10

    .line 270
    :cond_88
    invoke-virtual {p1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v4

    .line 271
    .restart local v4       #extras:Landroid/os/Bundle;
    if-eqz v4, :cond_96

    const-string v10, "android.intent.extra.REPLACING"

    invoke-virtual {v4, v10, v11}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v10

    if-nez v10, :cond_1c

    .line 274
    :cond_96
    iget-object v11, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v11

    .line 275
    :try_start_99
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 276
    move-object v2, v7

    .restart local v2       #arr$:[Ljava/lang/String;
    array-length v6, v2

    .restart local v6       #len$:I
    const/4 v5, 0x0

    .restart local v5       #i$:I
    :goto_9f
    if-ge v5, v6, :cond_ac

    aget-object v8, v2, v5

    .line 277
    .restart local v8       #pkgName:Ljava/lang/String;
    invoke-virtual {p0, v8}, Lcom/android/server/AppWidgetServiceImpl;->removeProvidersForPackageLocked(Ljava/lang/String;)V

    .line 278
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 276
    add-int/lit8 v5, v5, 0x1

    goto :goto_9f

    .line 280
    .end local v8           #pkgName:Ljava/lang/String;
    :cond_ac
    monitor-exit v11

    goto/16 :goto_1c

    .end local v2           #arr$:[Ljava/lang/String;
    .end local v5           #i$:I
    .end local v6           #len$:I
    :catchall_af
    move-exception v10

    monitor-exit v11
    :try_end_b1
    .catchall {:try_start_99 .. :try_end_b1} :catchall_af

    throw v10
.end method

.method onConfigurationChanged()V
    .registers 8

    .prologue
    .line 207
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v4

    .line 208
    .local v4, revised:Ljava/util/Locale;
    if-eqz v4, :cond_12

    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mLocale:Ljava/util/Locale;

    if-eqz v5, :cond_12

    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mLocale:Ljava/util/Locale;

    invoke-virtual {v4, v5}, Ljava/util/Locale;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_3e

    .line 209
    :cond_12
    iput-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mLocale:Ljava/util/Locale;

    .line 211
    iget-object v6, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v6

    .line 212
    :try_start_17
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 213
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 214
    .local v0, N:I
    add-int/lit8 v1, v0, -0x1

    .local v1, i:I
    :goto_22
    if-ltz v1, :cond_3a

    .line 215
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v5, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 216
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-object v5, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v5, v5, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v5}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 217
    .local v3, pkgName:Ljava/lang/String;
    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->updateProvidersForPackageLocked(Ljava/lang/String;)V

    .line 214
    add-int/lit8 v1, v1, -0x1

    goto :goto_22

    .line 219
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .end local v3           #pkgName:Ljava/lang/String;
    :cond_3a
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 220
    monitor-exit v6

    .line 222
    .end local v0           #N:I
    .end local v1           #i:I
    :cond_3e
    return-void

    .line 220
    :catchall_3f
    move-exception v5

    monitor-exit v6
    :try_end_41
    .catchall {:try_start_17 .. :try_end_41} :catchall_3f

    throw v5
.end method

.method onUserRemoved()V
    .registers 5

    .prologue
    .line 1634
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1635
    .local v0, N:I
    add-int/lit8 v1, v0, -0x1

    .local v1, i:I
    :goto_8
    if-ltz v1, :cond_18

    .line 1636
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1637
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    invoke-virtual {p0, v2}, Lcom/android/server/AppWidgetServiceImpl;->cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1635
    add-int/lit8 v1, v1, -0x1

    goto :goto_8

    .line 1639
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_18
    iget v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-static {v3}, Lcom/android/server/AppWidgetServiceImpl;->getSettingsFile(I)Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->delete()Z

    .line 1640
    return-void
.end method

.method public partiallyUpdateAppWidgetIds([ILandroid/widget/RemoteViews;)V
    .registers 8
    .parameter "appWidgetIds"
    .parameter "views"

    .prologue
    .line 877
    if-nez p1, :cond_3

    .line 892
    :cond_2
    :goto_2
    return-void

    .line 880
    :cond_3
    array-length v3, p1

    if-eqz v3, :cond_2

    .line 883
    array-length v0, p1

    .line 885
    .local v0, N:I
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v4

    .line 886
    :try_start_a
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 887
    const/4 v1, 0x0

    .local v1, i:I
    :goto_e
    if-ge v1, v0, :cond_1d

    .line 888
    aget v3, p1, v1

    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v2

    .line 889
    .local v2, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    const/4 v3, 0x1

    invoke-virtual {p0, v2, p2, v3}, Lcom/android/server/AppWidgetServiceImpl;->updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;Z)V

    .line 887
    add-int/lit8 v1, v1, 0x1

    goto :goto_e

    .line 891
    .end local v2           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_1d
    monitor-exit v4

    goto :goto_2

    .end local v1           #i:I
    :catchall_1f
    move-exception v3

    monitor-exit v4
    :try_end_21
    .catchall {:try_start_a .. :try_end_21} :catchall_1f

    throw v3
.end method

.method pruneHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V
    .registers 3
    .parameter "host"

    .prologue
    .line 1121
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-nez v0, :cond_11

    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    if-nez v0, :cond_11

    .line 1122
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 1124
    :cond_11
    return-void
.end method

.method readStateFromFileLocked(Ljava/io/FileInputStream;)V
    .registers 28
    .parameter "stream"

    .prologue
    .line 1476
    const/16 v20, 0x0

    .line 1478
    .local v20, success:Z
    :try_start_2
    invoke-static {}, Landroid/util/Xml;->newPullParser()Lorg/xmlpull/v1/XmlPullParser;

    move-result-object v15

    .line 1479
    .local v15, parser:Lorg/xmlpull/v1/XmlPullParser;
    const/16 v23, 0x0

    move-object/from16 v0, p1

    move-object/from16 v1, v23

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->setInput(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 1482
    const/16 v18, 0x0

    .line 1483
    .local v18, providerIndex:I
    new-instance v10, Ljava/util/HashMap;

    invoke-direct {v10}, Ljava/util/HashMap;-><init>()V

    .line 1485
    .local v10, loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    :cond_16
    invoke-interface {v15}, Lorg/xmlpull/v1/XmlPullParser;->next()I

    move-result v22

    .line 1486
    .local v22, type:I
    const/16 v23, 0x2

    move/from16 v0, v22

    move/from16 v1, v23

    if-ne v0, v1, :cond_c0

    .line 1487
    invoke-interface {v15}, Lorg/xmlpull/v1/XmlPullParser;->getName()Ljava/lang/String;

    move-result-object v21

    .line 1488
    .local v21, tag:Ljava/lang/String;
    const-string v23, "p"

    move-object/from16 v0, v23

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v23

    if-eqz v23, :cond_10b

    .line 1491
    const/16 v23, 0x0

    const-string v24, "pkg"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v16

    .line 1492
    .local v16, pkg:Ljava/lang/String;
    const/16 v23, 0x0

    const-string v24, "cl"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 1494
    .local v3, cl:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;
    :try_end_53
    .catch Ljava/lang/NullPointerException; {:try_start_2 .. :try_end_53} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_2 .. :try_end_53} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_2 .. :try_end_53} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_53} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_2 .. :try_end_53} :catch_2dc

    move-result-object v13

    .line 1496
    .local v13, packageManager:Landroid/content/pm/PackageManager;
    :try_start_54
    new-instance v23, Landroid/content/ComponentName;

    move-object/from16 v0, v23

    move-object/from16 v1, v16

    invoke-direct {v0, v1, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    const/16 v24, 0x0

    move-object/from16 v0, v23

    move/from16 v1, v24

    invoke-virtual {v13, v0, v1}, Landroid/content/pm/PackageManager;->getReceiverInfo(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    :try_end_66
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_54 .. :try_end_66} :catch_f2
    .catch Ljava/lang/NullPointerException; {:try_start_54 .. :try_end_66} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_54 .. :try_end_66} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_54 .. :try_end_66} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_54 .. :try_end_66} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_54 .. :try_end_66} :catch_2dc

    .line 1503
    :goto_66
    :try_start_66
    new-instance v23, Landroid/content/ComponentName;

    move-object/from16 v0, v23

    move-object/from16 v1, v16

    invoke-direct {v0, v1, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    move-object/from16 v0, p0

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Lcom/android/server/AppWidgetServiceImpl;->lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v11

    .line 1504
    .local v11, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-nez v11, :cond_b3

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mSafeMode:Z

    move/from16 v23, v0

    if-eqz v23, :cond_b3

    .line 1506
    new-instance v11, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .end local v11           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    invoke-direct {v11}, Lcom/android/server/AppWidgetServiceImpl$Provider;-><init>()V

    .line 1507
    .restart local v11       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    new-instance v23, Landroid/appwidget/AppWidgetProviderInfo;

    invoke-direct/range {v23 .. v23}, Landroid/appwidget/AppWidgetProviderInfo;-><init>()V

    move-object/from16 v0, v23

    iput-object v0, v11, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    .line 1508
    iget-object v0, v11, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    move-object/from16 v23, v0

    new-instance v24, Landroid/content/ComponentName;

    move-object/from16 v0, v24

    move-object/from16 v1, v16

    invoke-direct {v0, v1, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    move-object/from16 v0, v24

    move-object/from16 v1, v23

    iput-object v0, v1, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    .line 1509
    const/16 v23, 0x1

    move/from16 v0, v23

    iput-boolean v0, v11, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    .line 1510
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v11}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1512
    :cond_b3
    if-eqz v11, :cond_be

    .line 1514
    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v23

    move-object/from16 v0, v23

    invoke-virtual {v10, v0, v11}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_be
    .catch Ljava/lang/NullPointerException; {:try_start_66 .. :try_end_be} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_66 .. :try_end_be} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_66 .. :try_end_be} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_66 .. :try_end_be} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_66 .. :try_end_be} :catch_2dc

    .line 1516
    :cond_be
    add-int/lit8 v18, v18, 0x1

    .line 1579
    .end local v3           #cl:Ljava/lang/String;
    .end local v11           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .end local v13           #packageManager:Landroid/content/pm/PackageManager;
    .end local v16           #pkg:Ljava/lang/String;
    .end local v21           #tag:Ljava/lang/String;
    :cond_c0
    :goto_c0
    const/16 v23, 0x1

    move/from16 v0, v22

    move/from16 v1, v23

    if-ne v0, v1, :cond_16

    .line 1580
    const/16 v20, 0x1

    .line 1593
    .end local v10           #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .end local v15           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v18           #providerIndex:I
    .end local v22           #type:I
    :goto_ca
    if-eqz v20, :cond_2f9

    .line 1596
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/util/ArrayList;->size()I

    move-result v23

    add-int/lit8 v8, v23, -0x1

    .local v8, i:I
    :goto_d8
    if-ltz v8, :cond_339

    .line 1597
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v8}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v23

    check-cast v23, Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v0, p0

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Lcom/android/server/AppWidgetServiceImpl;->pruneHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 1596
    add-int/lit8 v8, v8, -0x1

    goto :goto_d8

    .line 1497
    .end local v8           #i:I
    .restart local v3       #cl:Ljava/lang/String;
    .restart local v10       #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .restart local v13       #packageManager:Landroid/content/pm/PackageManager;
    .restart local v15       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v16       #pkg:Ljava/lang/String;
    .restart local v18       #providerIndex:I
    .restart local v21       #tag:Ljava/lang/String;
    .restart local v22       #type:I
    :catch_f2
    move-exception v4

    .line 1498
    .local v4, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    const/16 v23, 0x1

    :try_start_f5
    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/String;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    aput-object v16, v23, v24

    move-object/from16 v0, v23

    invoke-virtual {v13, v0}, Landroid/content/pm/PackageManager;->currentToCanonicalPackageNames([Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v17

    .line 1500
    .local v17, pkgs:[Ljava/lang/String;
    const/16 v23, 0x0

    aget-object v16, v17, v23

    goto/16 :goto_66

    .line 1517
    .end local v3           #cl:Ljava/lang/String;
    .end local v4           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v13           #packageManager:Landroid/content/pm/PackageManager;
    .end local v16           #pkg:Ljava/lang/String;
    .end local v17           #pkgs:[Ljava/lang/String;
    :cond_10b
    const-string v23, "h"

    move-object/from16 v0, v23

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v23

    if-eqz v23, :cond_1af

    .line 1518
    new-instance v7, Lcom/android/server/AppWidgetServiceImpl$Host;

    invoke-direct {v7}, Lcom/android/server/AppWidgetServiceImpl$Host;-><init>()V

    .line 1522
    .local v7, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    const/16 v23, 0x0

    const-string v24, "pkg"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v23

    move-object/from16 v0, v23

    iput-object v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;
    :try_end_12c
    .catch Ljava/lang/NullPointerException; {:try_start_f5 .. :try_end_12c} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_f5 .. :try_end_12c} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_f5 .. :try_end_12c} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_f5 .. :try_end_12c} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_f5 .. :try_end_12c} :catch_2dc

    .line 1524
    :try_start_12c
    iget-object v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    move-object/from16 v23, v0

    move-object/from16 v0, p0

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Lcom/android/server/AppWidgetServiceImpl;->getUidForPackage(Ljava/lang/String;)I

    move-result v23

    move/from16 v0, v23

    iput v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->uid:I
    :try_end_13c
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_12c .. :try_end_13c} :catch_18a
    .catch Ljava/lang/NullPointerException; {:try_start_12c .. :try_end_13c} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_12c .. :try_end_13c} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_12c .. :try_end_13c} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_12c .. :try_end_13c} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_12c .. :try_end_13c} :catch_2dc

    .line 1528
    :goto_13c
    :try_start_13c
    iget-boolean v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->zombie:Z

    move/from16 v23, v0

    if-eqz v23, :cond_14a

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mSafeMode:Z

    move/from16 v23, v0

    if-eqz v23, :cond_c0

    .line 1531
    :cond_14a
    const/16 v23, 0x0

    const-string v24, "id"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v23

    const/16 v24, 0x10

    invoke-static/range {v23 .. v24}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v23

    move/from16 v0, v23

    iput v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    .line 1533
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v7}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_16b
    .catch Ljava/lang/NullPointerException; {:try_start_13c .. :try_end_16b} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_13c .. :try_end_16b} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_13c .. :try_end_16b} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_13c .. :try_end_16b} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_13c .. :try_end_16b} :catch_2dc

    goto/16 :goto_c0

    .line 1581
    .end local v7           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    .end local v10           #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .end local v15           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v18           #providerIndex:I
    .end local v21           #tag:Ljava/lang/String;
    .end local v22           #type:I
    :catch_16d
    move-exception v4

    .line 1582
    .local v4, e:Ljava/lang/NullPointerException;
    const-string v23, "AppWidgetServiceImpl"

    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "failed parsing "

    invoke-virtual/range {v24 .. v25}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, v24

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v24

    invoke-virtual/range {v24 .. v24}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_ca

    .line 1525
    .end local v4           #e:Ljava/lang/NullPointerException;
    .restart local v7       #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    .restart local v10       #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .restart local v15       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v18       #providerIndex:I
    .restart local v21       #tag:Ljava/lang/String;
    .restart local v22       #type:I
    :catch_18a
    move-exception v5

    .line 1526
    .local v5, ex:Landroid/content/pm/PackageManager$NameNotFoundException;
    const/16 v23, 0x1

    :try_start_18d
    move/from16 v0, v23

    iput-boolean v0, v7, Lcom/android/server/AppWidgetServiceImpl$Host;->zombie:Z
    :try_end_191
    .catch Ljava/lang/NullPointerException; {:try_start_18d .. :try_end_191} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_18d .. :try_end_191} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_18d .. :try_end_191} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_18d .. :try_end_191} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_18d .. :try_end_191} :catch_2dc

    goto :goto_13c

    .line 1583
    .end local v5           #ex:Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v7           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    .end local v10           #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .end local v15           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v18           #providerIndex:I
    .end local v21           #tag:Ljava/lang/String;
    .end local v22           #type:I
    :catch_192
    move-exception v4

    .line 1584
    .local v4, e:Ljava/lang/NumberFormatException;
    const-string v23, "AppWidgetServiceImpl"

    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "failed parsing "

    invoke-virtual/range {v24 .. v25}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, v24

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v24

    invoke-virtual/range {v24 .. v24}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_ca

    .line 1535
    .end local v4           #e:Ljava/lang/NumberFormatException;
    .restart local v10       #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .restart local v15       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v18       #providerIndex:I
    .restart local v21       #tag:Ljava/lang/String;
    .restart local v22       #type:I
    :cond_1af
    :try_start_1af
    const-string v23, "b"

    move-object/from16 v0, v23

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v23

    if-eqz v23, :cond_1f3

    .line 1536
    const/16 v23, 0x0

    const-string v24, "packageName"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    .line 1537
    .local v14, packageName:Ljava/lang/String;
    if-eqz v14, :cond_c0

    .line 1538
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v14}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_1d4
    .catch Ljava/lang/NullPointerException; {:try_start_1af .. :try_end_1d4} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_1af .. :try_end_1d4} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_1af .. :try_end_1d4} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_1af .. :try_end_1d4} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_1af .. :try_end_1d4} :catch_2dc

    goto/16 :goto_c0

    .line 1585
    .end local v10           #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .end local v14           #packageName:Ljava/lang/String;
    .end local v15           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v18           #providerIndex:I
    .end local v21           #tag:Ljava/lang/String;
    .end local v22           #type:I
    :catch_1d6
    move-exception v4

    .line 1586
    .local v4, e:Lorg/xmlpull/v1/XmlPullParserException;
    const-string v23, "AppWidgetServiceImpl"

    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "failed parsing "

    invoke-virtual/range {v24 .. v25}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, v24

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v24

    invoke-virtual/range {v24 .. v24}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_ca

    .line 1540
    .end local v4           #e:Lorg/xmlpull/v1/XmlPullParserException;
    .restart local v10       #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .restart local v15       #parser:Lorg/xmlpull/v1/XmlPullParser;
    .restart local v18       #providerIndex:I
    .restart local v21       #tag:Ljava/lang/String;
    .restart local v22       #type:I
    :cond_1f3
    :try_start_1f3
    const-string v23, "g"

    move-object/from16 v0, v23

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v23

    if-eqz v23, :cond_c0

    .line 1541
    new-instance v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    invoke-direct {v9}, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;-><init>()V

    .line 1542
    .local v9, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    const/16 v23, 0x0

    const-string v24, "id"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v23

    const/16 v24, 0x10

    invoke-static/range {v23 .. v24}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v23

    move/from16 v0, v23

    iput v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    .line 1543
    iget v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    move/from16 v23, v0

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mNextAppWidgetId:I

    move/from16 v24, v0

    move/from16 v0, v23

    move/from16 v1, v24

    if-lt v0, v1, :cond_236

    .line 1544
    iget v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    move/from16 v23, v0

    add-int/lit8 v23, v23, 0x1

    move/from16 v0, v23

    move-object/from16 v1, p0

    iput v0, v1, Lcom/android/server/AppWidgetServiceImpl;->mNextAppWidgetId:I

    .line 1547
    :cond_236
    const/16 v23, 0x0

    const-string v24, "p"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v19

    .line 1548
    .local v19, providerString:Ljava/lang/String;
    if-eqz v19, :cond_264

    .line 1552
    const/16 v23, 0x10

    move-object/from16 v0, v19

    move/from16 v1, v23

    invoke-static {v0, v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v12

    .line 1553
    .local v12, pIndex:I
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v23

    move-object/from16 v0, v23

    invoke-virtual {v10, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v23

    check-cast v23, Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-object/from16 v0, v23

    iput-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1558
    iget-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-object/from16 v23, v0

    if-eqz v23, :cond_c0

    .line 1565
    .end local v12           #pIndex:I
    :cond_264
    const/16 v23, 0x0

    const-string v24, "h"

    move-object/from16 v0, v23

    move-object/from16 v1, v24

    invoke-interface {v15, v0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v23

    const/16 v24, 0x10

    invoke-static/range {v23 .. v24}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v6

    .line 1566
    .local v6, hIndex:I
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v23

    check-cast v23, Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v0, v23

    iput-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1567
    iget-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v23, v0

    if-eqz v23, :cond_c0

    .line 1572
    iget-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-object/from16 v23, v0

    if-eqz v23, :cond_2a3

    .line 1573
    iget-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v9}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1575
    :cond_2a3
    iget-object v0, v9, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v9}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1576
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v9}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_2bd
    .catch Ljava/lang/NullPointerException; {:try_start_1f3 .. :try_end_2bd} :catch_16d
    .catch Ljava/lang/NumberFormatException; {:try_start_1f3 .. :try_end_2bd} :catch_192
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_1f3 .. :try_end_2bd} :catch_1d6
    .catch Ljava/io/IOException; {:try_start_1f3 .. :try_end_2bd} :catch_2bf
    .catch Ljava/lang/IndexOutOfBoundsException; {:try_start_1f3 .. :try_end_2bd} :catch_2dc

    goto/16 :goto_c0

    .line 1587
    .end local v6           #hIndex:I
    .end local v9           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    .end local v10           #loadedProviders:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/Integer;Lcom/android/server/AppWidgetServiceImpl$Provider;>;"
    .end local v15           #parser:Lorg/xmlpull/v1/XmlPullParser;
    .end local v18           #providerIndex:I
    .end local v19           #providerString:Ljava/lang/String;
    .end local v21           #tag:Ljava/lang/String;
    .end local v22           #type:I
    :catch_2bf
    move-exception v4

    .line 1588
    .local v4, e:Ljava/io/IOException;
    const-string v23, "AppWidgetServiceImpl"

    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "failed parsing "

    invoke-virtual/range {v24 .. v25}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, v24

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v24

    invoke-virtual/range {v24 .. v24}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_ca

    .line 1589
    .end local v4           #e:Ljava/io/IOException;
    :catch_2dc
    move-exception v4

    .line 1590
    .local v4, e:Ljava/lang/IndexOutOfBoundsException;
    const-string v23, "AppWidgetServiceImpl"

    new-instance v24, Ljava/lang/StringBuilder;

    invoke-direct/range {v24 .. v24}, Ljava/lang/StringBuilder;-><init>()V

    const-string v25, "failed parsing "

    invoke-virtual/range {v24 .. v25}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v24

    move-object/from16 v0, v24

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v24

    invoke-virtual/range {v24 .. v24}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_ca

    .line 1601
    .end local v4           #e:Ljava/lang/IndexOutOfBoundsException;
    :cond_2f9
    const-string v23, "AppWidgetServiceImpl"

    const-string v24, "Failed to read state, clearing widgets and hosts."

    invoke-static/range {v23 .. v24}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1603
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/util/ArrayList;->clear()V

    .line 1604
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/util/ArrayList;->clear()V

    .line 1605
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/util/ArrayList;->size()I

    move-result v2

    .line 1606
    .local v2, N:I
    const/4 v8, 0x0

    .restart local v8       #i:I
    :goto_31d
    if-ge v8, v2, :cond_339

    .line 1607
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    invoke-virtual {v0, v8}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v23

    check-cast v23, Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/util/ArrayList;->clear()V

    .line 1606
    add-int/lit8 v8, v8, 0x1

    goto :goto_31d

    .line 1610
    .end local v2           #N:I
    :cond_339
    return-void
.end method

.method registerForBroadcastsLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V
    .registers 14
    .parameter "p"
    .parameter "appWidgetIds"

    .prologue
    const/4 v7, 0x1

    .line 1198
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->updatePeriodMillis:I

    if-lez v0, :cond_4e

    .line 1202
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    if-eqz v0, :cond_4f

    .line 1203
    .local v7, alreadyRegistered:Z
    :goto_b
    new-instance v8, Landroid/content/Intent;

    const-string v0, "android.appwidget.action.APPWIDGET_UPDATE"

    invoke-direct {v8, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1204
    .local v8, intent:Landroid/content/Intent;
    const-string v0, "appWidgetIds"

    invoke-virtual {v8, v0, p2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;[I)Landroid/content/Intent;

    .line 1205
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v8, v0}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 1206
    invoke-static {}, Landroid/os/Binder;->clearCallingIdentity()J

    move-result-wide v9

    .line 1208
    .local v9, token:J
    :try_start_22
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const/4 v1, 0x1

    const/high16 v2, 0x800

    invoke-static {v0, v1, v8, v2}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v0

    iput-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;
    :try_end_2d
    .catchall {:try_start_22 .. :try_end_2d} :catchall_51

    .line 1211
    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    .line 1213
    if-nez v7, :cond_4e

    .line 1214
    iget-object v0, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->updatePeriodMillis:I

    int-to-long v4, v0

    .line 1215
    .local v4, period:J
    const-wide/32 v0, 0x1b7740

    cmp-long v0, v4, v0

    if-gez v0, :cond_41

    .line 1216
    const-wide/32 v4, 0x1b7740

    .line 1218
    :cond_41
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mAlarmManager:Landroid/app/AlarmManager;

    const/4 v1, 0x2

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    add-long/2addr v2, v4

    iget-object v6, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->broadcast:Landroid/app/PendingIntent;

    invoke-virtual/range {v0 .. v6}, Landroid/app/AlarmManager;->setInexactRepeating(IJJLandroid/app/PendingIntent;)V

    .line 1223
    .end local v4           #period:J
    .end local v7           #alreadyRegistered:Z
    .end local v8           #intent:Landroid/content/Intent;
    .end local v9           #token:J
    :cond_4e
    return-void

    .line 1202
    :cond_4f
    const/4 v7, 0x0

    goto :goto_b

    .line 1211
    .restart local v7       #alreadyRegistered:Z
    .restart local v8       #intent:Landroid/content/Intent;
    .restart local v9       #token:J
    :catchall_51
    move-exception v0

    invoke-static {v9, v10}, Landroid/os/Binder;->restoreCallingIdentity(J)V

    throw v0
.end method

.method removeProviderLocked(ILcom/android/server/AppWidgetServiceImpl$Provider;)V
    .registers 8
    .parameter "index"
    .parameter "p"

    .prologue
    const/4 v4, 0x0

    .line 1161
    iget-object v3, p2, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1162
    .local v0, N:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_8
    if-ge v1, v0, :cond_30

    .line 1163
    iget-object v3, p2, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 1165
    .local v2, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    invoke-virtual {p0, v2, v4}, Lcom/android/server/AppWidgetServiceImpl;->updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;)V

    .line 1167
    invoke-virtual {p0, p2}, Lcom/android/server/AppWidgetServiceImpl;->cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1169
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v3, v3, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 1170
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 1171
    iput-object v4, v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1172
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->pruneHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 1173
    iput-object v4, v2, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1162
    add-int/lit8 v1, v1, 0x1

    goto :goto_8

    .line 1175
    .end local v2           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_30
    iget-object v3, p2, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->clear()V

    .line 1176
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v3, p1}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 1177
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mDeletedProviders:Ljava/util/ArrayList;

    invoke-virtual {v3, p2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1179
    invoke-virtual {p0, p2}, Lcom/android/server/AppWidgetServiceImpl;->cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1180
    return-void
.end method

.method removeProvidersForPackageLocked(Ljava/lang/String;)V
    .registers 7
    .parameter "pkgName"

    .prologue
    .line 1747
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1748
    .local v0, N:I
    add-int/lit8 v2, v0, -0x1

    .local v2, i:I
    :goto_8
    if-ltz v2, :cond_26

    .line 1749
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1750
    .local v3, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-object v4, v3, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v4, v4, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v4}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_23

    .line 1751
    invoke-virtual {p0, v2, v3}, Lcom/android/server/AppWidgetServiceImpl;->removeProviderLocked(ILcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1748
    :cond_23
    add-int/lit8 v2, v2, -0x1

    goto :goto_8

    .line 1759
    .end local v3           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_26
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1760
    add-int/lit8 v2, v0, -0x1

    :goto_2e
    if-ltz v2, :cond_46

    .line 1761
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1762
    .local v1, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    iget-object v4, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    invoke-virtual {p1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_43

    .line 1763
    invoke-virtual {p0, v1}, Lcom/android/server/AppWidgetServiceImpl;->deleteHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 1760
    :cond_43
    add-int/lit8 v2, v2, -0x1

    goto :goto_2e

    .line 1766
    .end local v1           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_46
    return-void
.end method

.method saveStateLocked()V
    .registers 7

    .prologue
    .line 1398
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->savedStateFile()Lcom/android/internal/os/AtomicFile;

    move-result-object v1

    .line 1401
    .local v1, file:Lcom/android/internal/os/AtomicFile;
    :try_start_4
    invoke-virtual {v1}, Lcom/android/internal/os/AtomicFile;->startWrite()Ljava/io/FileOutputStream;

    move-result-object v2

    .line 1402
    .local v2, stream:Ljava/io/FileOutputStream;
    invoke-virtual {p0, v2}, Lcom/android/server/AppWidgetServiceImpl;->writeStateToFileLocked(Ljava/io/FileOutputStream;)Z

    move-result v3

    if-eqz v3, :cond_12

    .line 1403
    invoke-virtual {v1, v2}, Lcom/android/internal/os/AtomicFile;->finishWrite(Ljava/io/FileOutputStream;)V

    .line 1411
    .end local v2           #stream:Ljava/io/FileOutputStream;
    :goto_11
    return-void

    .line 1405
    .restart local v2       #stream:Ljava/io/FileOutputStream;
    :cond_12
    invoke-virtual {v1, v2}, Lcom/android/internal/os/AtomicFile;->failWrite(Ljava/io/FileOutputStream;)V

    .line 1406
    const-string v3, "AppWidgetServiceImpl"

    const-string v4, "Failed to save state, restoring backup."

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1c
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_1c} :catch_1d

    goto :goto_11

    .line 1408
    .end local v2           #stream:Ljava/io/FileOutputStream;
    :catch_1d
    move-exception v0

    .line 1409
    .local v0, e:Ljava/io/IOException;
    const-string v3, "AppWidgetServiceImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Failed open state file for write: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_11
.end method

.method savedStateFile()Lcom/android/internal/os/AtomicFile;
    .registers 6

    .prologue
    .line 1617
    new-instance v0, Ljava/io/File;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "/data/system/users/"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v0, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1618
    .local v0, dir:Ljava/io/File;
    iget v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-static {v3}, Lcom/android/server/AppWidgetServiceImpl;->getSettingsFile(I)Ljava/io/File;

    move-result-object v2

    .line 1619
    .local v2, settingsFile:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    if-nez v3, :cond_3d

    iget v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    if-nez v3, :cond_3d

    .line 1620
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v3

    if-nez v3, :cond_33

    .line 1621
    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    .line 1624
    :cond_33
    new-instance v1, Ljava/io/File;

    const-string v3, "/data/system/appwidgets.xml"

    invoke-direct {v1, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1627
    .local v1, oldFile:Ljava/io/File;
    invoke-virtual {v1, v2}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    .line 1629
    .end local v1           #oldFile:Ljava/io/File;
    :cond_3d
    new-instance v3, Lcom/android/internal/os/AtomicFile;

    invoke-direct {v3, v2}, Lcom/android/internal/os/AtomicFile;-><init>(Ljava/io/File;)V

    return-object v3
.end method

.method sendEnableIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;)V
    .registers 5
    .parameter "p"

    .prologue
    .line 1183
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.appwidget.action.APPWIDGET_ENABLED"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1184
    .local v0, intent:Landroid/content/Intent;
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v1, v1, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 1185
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    iget v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v1, v0, v2}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;I)V

    .line 1186
    return-void
.end method

.method sendInitialBroadcasts()V
    .registers 7

    .prologue
    .line 1363
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v5

    .line 1364
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 1365
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1366
    .local v0, N:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_d
    if-ge v2, v0, :cond_2f

    .line 1367
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1368
    .local v3, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-object v4, v3, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-lez v4, :cond_2c

    .line 1369
    invoke-virtual {p0, v3}, Lcom/android/server/AppWidgetServiceImpl;->sendEnableIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1370
    invoke-static {v3}, Lcom/android/server/AppWidgetServiceImpl;->getAppWidgetIds(Lcom/android/server/AppWidgetServiceImpl$Provider;)[I

    move-result-object v1

    .line 1371
    .local v1, appWidgetIds:[I
    invoke-virtual {p0, v3, v1}, Lcom/android/server/AppWidgetServiceImpl;->sendUpdateIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    .line 1372
    invoke-virtual {p0, v3, v1}, Lcom/android/server/AppWidgetServiceImpl;->registerForBroadcastsLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    .line 1366
    .end local v1           #appWidgetIds:[I
    :cond_2c
    add-int/lit8 v2, v2, 0x1

    goto :goto_d

    .line 1375
    .end local v3           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_2f
    monitor-exit v5

    .line 1376
    return-void

    .line 1375
    .end local v0           #N:I
    .end local v2           #i:I
    :catchall_31
    move-exception v4

    monitor-exit v5
    :try_end_33
    .catchall {:try_start_3 .. :try_end_33} :catchall_31

    throw v4
.end method

.method sendUpdateIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V
    .registers 6
    .parameter "p"
    .parameter "appWidgetIds"

    .prologue
    .line 1189
    if-eqz p2, :cond_1f

    array-length v1, p2

    if-lez v1, :cond_1f

    .line 1190
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.appwidget.action.APPWIDGET_UPDATE"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1191
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "appWidgetIds"

    invoke-virtual {v0, v1, p2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;[I)Landroid/content/Intent;

    .line 1192
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v1, v1, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 1193
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    iget v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v1, v0, v2}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;I)V

    .line 1195
    .end local v0           #intent:Landroid/content/Intent;
    :cond_1f
    return-void
.end method

.method public setBindAppWidgetPermission(Ljava/lang/String;Z)V
    .registers 7
    .parameter "packageName"
    .parameter "permission"

    .prologue
    .line 600
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    const-string v1, "android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "setBindAppWidgetPermission packageName="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->enforceCallingPermission(Ljava/lang/String;Ljava/lang/String;)V

    .line 604
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v1

    .line 605
    :try_start_1d
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 606
    if-eqz p2, :cond_2c

    .line 607
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 611
    :goto_27
    monitor-exit v1
    :try_end_28
    .catchall {:try_start_1d .. :try_end_28} :catchall_32

    .line 612
    invoke-virtual {p0}, Lcom/android/server/AppWidgetServiceImpl;->saveStateLocked()V

    .line 613
    return-void

    .line 609
    :cond_2c
    :try_start_2c
    iget-object v0, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    goto :goto_27

    .line 611
    :catchall_32
    move-exception v0

    monitor-exit v1
    :try_end_34
    .catchall {:try_start_2c .. :try_end_34} :catchall_32

    throw v0
.end method

.method public startListening(Lcom/android/internal/appwidget/IAppWidgetHost;Ljava/lang/String;ILjava/util/List;)[I
    .registers 14
    .parameter "callbacks"
    .parameter "packageName"
    .parameter "hostId"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/internal/appwidget/IAppWidgetHost;",
            "Ljava/lang/String;",
            "I",
            "Ljava/util/List",
            "<",
            "Landroid/widget/RemoteViews;",
            ">;)[I"
        }
    .end annotation

    .prologue
    .line 1022
    .local p4, updatedViews:Ljava/util/List;,"Ljava/util/List<Landroid/widget/RemoteViews;>;"
    invoke-virtual {p0, p2}, Lcom/android/server/AppWidgetServiceImpl;->enforceCallingUid(Ljava/lang/String;)I

    move-result v1

    .line 1023
    .local v1, callingUid:I
    iget-object v8, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v8

    .line 1024
    :try_start_7
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 1025
    invoke-virtual {p0, v1, p2, p3}, Lcom/android/server/AppWidgetServiceImpl;->lookupOrAddHostLocked(ILjava/lang/String;I)Lcom/android/server/AppWidgetServiceImpl$Host;

    move-result-object v2

    .line 1026
    .local v2, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    iput-object p1, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    .line 1028
    invoke-interface {p4}, Ljava/util/List;->clear()V

    .line 1030
    iget-object v5, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->instances:Ljava/util/ArrayList;

    .line 1031
    .local v5, instances:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;>;"
    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1032
    .local v0, N:I
    new-array v6, v0, [I

    .line 1033
    .local v6, updatedIds:[I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_1c
    if-ge v3, v0, :cond_30

    .line 1034
    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 1035
    .local v4, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    iget v7, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    aput v7, v6, v3

    .line 1036
    iget-object v7, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    invoke-interface {p4, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 1033
    add-int/lit8 v3, v3, 0x1

    goto :goto_1c

    .line 1038
    .end local v4           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_30
    monitor-exit v8

    return-object v6

    .line 1039
    .end local v0           #N:I
    .end local v2           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    .end local v3           #i:I
    .end local v5           #instances:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;>;"
    .end local v6           #updatedIds:[I
    :catchall_32
    move-exception v7

    monitor-exit v8
    :try_end_34
    .catchall {:try_start_7 .. :try_end_34} :catchall_32

    throw v7
.end method

.method public stopListening(I)V
    .registers 5
    .parameter "hostId"

    .prologue
    .line 1043
    iget-object v2, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v2

    .line 1044
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 1045
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    invoke-virtual {p0, v1, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupHostLocked(II)Lcom/android/server/AppWidgetServiceImpl$Host;

    move-result-object v0

    .line 1046
    .local v0, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    if-eqz v0, :cond_16

    .line 1047
    const/4 v1, 0x0

    iput-object v1, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    .line 1048
    invoke-virtual {p0, v0}, Lcom/android/server/AppWidgetServiceImpl;->pruneHostLocked(Lcom/android/server/AppWidgetServiceImpl$Host;)V

    .line 1050
    :cond_16
    monitor-exit v2

    .line 1051
    return-void

    .line 1050
    .end local v0           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    :catchall_18
    move-exception v1

    monitor-exit v2
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_18

    throw v1
.end method

.method public systemReady(Z)V
    .registers 4
    .parameter "safeMode"

    .prologue
    .line 199
    iput-boolean p1, p0, Lcom/android/server/AppWidgetServiceImpl;->mSafeMode:Z

    .line 201
    iget-object v1, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v1

    .line 202
    :try_start_5
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 203
    monitor-exit v1

    .line 204
    return-void

    .line 203
    :catchall_a
    move-exception v0

    monitor-exit v1
    :try_end_c
    .catchall {:try_start_5 .. :try_end_c} :catchall_a

    throw v0
.end method

.method public unbindRemoteViewsService(ILandroid/content/Intent;)V
    .registers 9
    .parameter "appWidgetId"
    .parameter "intent"

    .prologue
    .line 669
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v4

    .line 670
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 673
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    new-instance v5, Landroid/content/Intent$FilterComparison;

    invoke-direct {v5, p2}, Landroid/content/Intent$FilterComparison;-><init>(Landroid/content/Intent;)V

    invoke-static {v3, v5}, Landroid/util/Pair;->create(Ljava/lang/Object;Ljava/lang/Object;)Landroid/util/Pair;

    move-result-object v2

    .line 675
    .local v2, key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v3, v2}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_43

    .line 679
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v1

    .line 680
    .local v1, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-nez v1, :cond_2c

    .line 681
    new-instance v3, Ljava/lang/IllegalArgumentException;

    const-string v5, "bad appWidgetId"

    invoke-direct {v3, v5}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 692
    .end local v1           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    .end local v2           #key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    :catchall_29
    move-exception v3

    monitor-exit v4
    :try_end_2b
    .catchall {:try_start_3 .. :try_end_2b} :catchall_29

    throw v3

    .line 684
    .restart local v1       #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    .restart local v2       #key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    :cond_2c
    :try_start_2c
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v3, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;

    .line 686
    .local v0, conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    invoke-virtual {v0}, Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;->disconnect()V

    .line 687
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v3, v0}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 688
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mBoundRemoteViewsServices:Ljava/util/HashMap;

    invoke-virtual {v3, v2}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 692
    .end local v0           #conn:Lcom/android/server/AppWidgetServiceImpl$ServiceConnectionProxy;
    .end local v1           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :goto_41
    monitor-exit v4

    .line 693
    return-void

    .line 690
    :cond_43
    const-string v3, "AppWidgetService"

    const-string v5, "Error (unbindRemoteViewsService): Connection not bound"

    invoke-static {v3, v5}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_4a
    .catchall {:try_start_2c .. :try_end_4a} :catchall_29

    goto :goto_41
.end method

.method public updateAppWidgetIds([ILandroid/widget/RemoteViews;)V
    .registers 10
    .parameter "appWidgetIds"
    .parameter "views"

    .prologue
    .line 818
    if-nez p1, :cond_3

    .line 842
    :cond_2
    :goto_2
    return-void

    .line 822
    :cond_3
    invoke-virtual {p2}, Landroid/widget/RemoteViews;->estimateMemoryUsage()I

    move-result v1

    .line 823
    .local v1, bitmapMemoryUsage:I
    iget v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mMaxWidgetBitmapMemory:I

    if-le v1, v4, :cond_3c

    .line 824
    new-instance v4, Ljava/lang/IllegalArgumentException;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "RemoteViews for widget update exceeds maximum bitmap memory usage (used: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", max: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget v6, p0, Lcom/android/server/AppWidgetServiceImpl;->mMaxWidgetBitmapMemory:I

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ") The total memory cannot exceed that required to"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " fill the device\'s screen once."

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-direct {v4, v5}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v4

    .line 830
    :cond_3c
    array-length v4, p1

    if-eqz v4, :cond_2

    .line 833
    array-length v0, p1

    .line 835
    .local v0, N:I
    iget-object v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v5

    .line 836
    :try_start_43
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 837
    const/4 v2, 0x0

    .local v2, i:I
    :goto_47
    if-ge v2, v0, :cond_55

    .line 838
    aget v4, p1, v2

    invoke-virtual {p0, v4}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v3

    .line 839
    .local v3, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    invoke-virtual {p0, v3, p2}, Lcom/android/server/AppWidgetServiceImpl;->updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;)V

    .line 837
    add-int/lit8 v2, v2, 0x1

    goto :goto_47

    .line 841
    .end local v3           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_55
    monitor-exit v5

    goto :goto_2

    .end local v2           #i:I
    :catchall_57
    move-exception v4

    monitor-exit v5
    :try_end_59
    .catchall {:try_start_43 .. :try_end_59} :catchall_57

    throw v4
.end method

.method updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;)V
    .registers 4
    .parameter "id"
    .parameter "views"

    .prologue
    .line 933
    const/4 v0, 0x0

    invoke-virtual {p0, p1, p2, v0}, Lcom/android/server/AppWidgetServiceImpl;->updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;Z)V

    .line 934
    return-void
.end method

.method updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;Z)V
    .registers 7
    .parameter "id"
    .parameter "views"
    .parameter "isPartialUpdate"

    .prologue
    .line 940
    if-eqz p1, :cond_25

    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v1, :cond_25

    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget-boolean v1, v1, Lcom/android/server/AppWidgetServiceImpl$Provider;->zombie:Z

    if-nez v1, :cond_25

    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-boolean v1, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->zombie:Z

    if-nez v1, :cond_25

    .line 943
    if-nez p3, :cond_16

    .line 944
    iput-object p2, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    .line 947
    :cond_16
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v1, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    if-eqz v1, :cond_25

    .line 950
    :try_start_1c
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget-object v1, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    iget v2, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-interface {v1, v2, p2}, Lcom/android/internal/appwidget/IAppWidgetHost;->updateAppWidget(ILandroid/widget/RemoteViews;)V
    :try_end_25
    .catch Landroid/os/RemoteException; {:try_start_1c .. :try_end_25} :catch_26

    .line 958
    :cond_25
    :goto_25
    return-void

    .line 951
    :catch_26
    move-exception v0

    .line 954
    .local v0, e:Landroid/os/RemoteException;
    iget-object v1, p1, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    const/4 v2, 0x0

    iput-object v2, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    goto :goto_25
.end method

.method public updateAppWidgetOptions(ILandroid/os/Bundle;)V
    .registers 9
    .parameter "appWidgetId"
    .parameter "options"

    .prologue
    .line 845
    iget-object v4, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v4

    .line 846
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 847
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupAppWidgetIdLocked(I)Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    move-result-object v0

    .line 849
    .local v0, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    if-nez v0, :cond_e

    .line 850
    monitor-exit v4

    .line 862
    :goto_d
    return-void

    .line 852
    :cond_e
    iget-object v2, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 853
    .local v2, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iput-object p2, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->options:Landroid/os/Bundle;

    .line 856
    new-instance v1, Landroid/content/Intent;

    const-string v3, "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS"

    invoke-direct {v1, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 857
    .local v1, intent:Landroid/content/Intent;
    iget-object v3, v2, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v3, v3, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 858
    const-string v3, "appWidgetId"

    iget v5, v0, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-virtual {v1, v3, v5}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 859
    const-string v3, "appWidgetOptions"

    invoke-virtual {v1, v3, p2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Bundle;)Landroid/content/Intent;

    .line 860
    iget-object v3, p0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    iget v5, p0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    invoke-virtual {v3, v1, v5}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;I)V

    .line 861
    monitor-exit v4

    goto :goto_d

    .end local v0           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    .end local v1           #intent:Landroid/content/Intent;
    .end local v2           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :catchall_35
    move-exception v3

    monitor-exit v4
    :try_end_37
    .catchall {:try_start_3 .. :try_end_37} :catchall_35

    throw v3
.end method

.method public updateAppWidgetProvider(Landroid/content/ComponentName;Landroid/widget/RemoteViews;)V
    .registers 13
    .parameter "provider"
    .parameter "views"

    .prologue
    .line 913
    iget-object v7, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    monitor-enter v7

    .line 914
    :try_start_3
    invoke-direct {p0}, Lcom/android/server/AppWidgetServiceImpl;->ensureStateLoadedLocked()V

    .line 915
    invoke-virtual {p0, p1}, Lcom/android/server/AppWidgetServiceImpl;->lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v5

    .line 916
    .local v5, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-nez v5, :cond_26

    .line 917
    const-string v6, "AppWidgetServiceImpl"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "updateAppWidgetProvider: provider doesn\'t exist: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v6, v8}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 918
    monitor-exit v7

    .line 930
    :goto_25
    return-void

    .line 920
    :cond_26
    iget-object v4, v5, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    .line 921
    .local v4, instances:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;>;"
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v1

    .line 922
    .local v1, callingUid:I
    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 923
    .local v0, N:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_31
    if-ge v2, v0, :cond_45

    .line 924
    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 925
    .local v3, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    invoke-virtual {p0, v3, v1}, Lcom/android/server/AppWidgetServiceImpl;->canAccessAppWidgetId(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;I)Z

    move-result v6

    if-eqz v6, :cond_42

    .line 926
    invoke-virtual {p0, v3, p2}, Lcom/android/server/AppWidgetServiceImpl;->updateAppWidgetInstanceLocked(Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;Landroid/widget/RemoteViews;)V

    .line 923
    :cond_42
    add-int/lit8 v2, v2, 0x1

    goto :goto_31

    .line 929
    .end local v3           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_45
    monitor-exit v7

    goto :goto_25

    .end local v0           #N:I
    .end local v1           #callingUid:I
    .end local v2           #i:I
    .end local v4           #instances:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;>;"
    .end local v5           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :catchall_47
    move-exception v6

    monitor-exit v7
    :try_end_49
    .catchall {:try_start_3 .. :try_end_49} :catchall_47

    throw v6
.end method

.method updateProvidersForPackageLocked(Ljava/lang/String;)V
    .registers 26
    .parameter "pkgName"

    .prologue
    .line 1668
    new-instance v15, Ljava/util/HashSet;

    invoke-direct {v15}, Ljava/util/HashSet;-><init>()V

    .line 1669
    .local v15, keep:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/String;>;"
    new-instance v13, Landroid/content/Intent;

    const-string v20, "android.appwidget.action.APPWIDGET_UPDATE"

    move-object/from16 v0, v20

    invoke-direct {v13, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 1670
    .local v13, intent:Landroid/content/Intent;
    move-object/from16 v0, p1

    invoke-virtual {v13, v0}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 1673
    :try_start_13
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mPm:Landroid/content/pm/IPackageManager;

    move-object/from16 v20, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mContext:Landroid/content/Context;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v13, v0}, Landroid/content/Intent;->resolveTypeIfNeeded(Landroid/content/ContentResolver;)Ljava/lang/String;

    move-result-object v21

    const/16 v22, 0x80

    move-object/from16 v0, p0

    iget v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mUserId:I

    move/from16 v23, v0

    move-object/from16 v0, v20

    move-object/from16 v1, v21

    move/from16 v2, v22

    move/from16 v3, v23

    invoke-interface {v0, v13, v1, v2, v3}, Landroid/content/pm/IPackageManager;->queryIntentReceivers(Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    :try_end_3c
    .catch Landroid/os/RemoteException; {:try_start_13 .. :try_end_3c} :catch_60

    move-result-object v8

    .line 1682
    .local v8, broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    if-nez v8, :cond_62

    const/4 v5, 0x0

    .line 1683
    .local v5, N:I
    :goto_40
    const/4 v11, 0x0

    .local v11, i:I
    :goto_41
    if-ge v11, v5, :cond_139

    .line 1684
    invoke-interface {v8, v11}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v19

    check-cast v19, Landroid/content/pm/ResolveInfo;

    .line 1685
    .local v19, ri:Landroid/content/pm/ResolveInfo;
    move-object/from16 v0, v19

    iget-object v6, v0, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    .line 1686
    .local v6, ai:Landroid/content/pm/ActivityInfo;
    iget-object v0, v6, Landroid/content/pm/ActivityInfo;->applicationInfo:Landroid/content/pm/ApplicationInfo;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget v0, v0, Landroid/content/pm/ApplicationInfo;->flags:I

    move/from16 v20, v0

    const/high16 v21, 0x4

    and-int v20, v20, v21

    if-eqz v20, :cond_67

    .line 1683
    :cond_5d
    :goto_5d
    add-int/lit8 v11, v11, 0x1

    goto :goto_41

    .line 1676
    .end local v5           #N:I
    .end local v6           #ai:Landroid/content/pm/ActivityInfo;
    .end local v8           #broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    .end local v11           #i:I
    .end local v19           #ri:Landroid/content/pm/ResolveInfo;
    :catch_60
    move-exception v18

    .line 1744
    :cond_61
    return-void

    .line 1682
    .restart local v8       #broadcastReceivers:Ljava/util/List;,"Ljava/util/List<Landroid/content/pm/ResolveInfo;>;"
    :cond_62
    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v5

    goto :goto_40

    .line 1689
    .restart local v5       #N:I
    .restart local v6       #ai:Landroid/content/pm/ActivityInfo;
    .restart local v11       #i:I
    .restart local v19       #ri:Landroid/content/pm/ResolveInfo;
    :cond_67
    iget-object v0, v6, Landroid/content/pm/ComponentInfo;->packageName:Ljava/lang/String;

    move-object/from16 v20, v0

    move-object/from16 v0, p1

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_5d

    .line 1690
    new-instance v9, Landroid/content/ComponentName;

    iget-object v0, v6, Landroid/content/pm/ComponentInfo;->packageName:Ljava/lang/String;

    move-object/from16 v20, v0

    iget-object v0, v6, Landroid/content/pm/ComponentInfo;->name:Ljava/lang/String;

    move-object/from16 v21, v0

    move-object/from16 v0, v20

    move-object/from16 v1, v21

    invoke-direct {v9, v0, v1}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 1691
    .local v9, component:Landroid/content/ComponentName;
    move-object/from16 v0, p0

    invoke-virtual {v0, v9}, Lcom/android/server/AppWidgetServiceImpl;->lookupProviderLocked(Landroid/content/ComponentName;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v16

    .line 1692
    .local v16, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-nez v16, :cond_a2

    .line 1693
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-virtual {v0, v1}, Lcom/android/server/AppWidgetServiceImpl;->addProviderLocked(Landroid/content/pm/ResolveInfo;)Z

    move-result v20

    if-eqz v20, :cond_5d

    .line 1694
    iget-object v0, v6, Landroid/content/pm/ComponentInfo;->name:Ljava/lang/String;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-virtual {v15, v0}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    goto :goto_5d

    .line 1697
    :cond_a2
    move-object/from16 v0, p0

    move-object/from16 v1, v19

    invoke-direct {v0, v9, v1}, Lcom/android/server/AppWidgetServiceImpl;->parseProviderInfoXml(Landroid/content/ComponentName;Landroid/content/pm/ResolveInfo;)Lcom/android/server/AppWidgetServiceImpl$Provider;

    move-result-object v17

    .line 1698
    .local v17, parsed:Lcom/android/server/AppWidgetServiceImpl$Provider;
    if-eqz v17, :cond_5d

    .line 1699
    iget-object v0, v6, Landroid/content/pm/ComponentInfo;->name:Ljava/lang/String;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-virtual {v15, v0}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 1701
    move-object/from16 v0, v17

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    move-object/from16 v1, v16

    iput-object v0, v1, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    .line 1703
    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Ljava/util/ArrayList;->size()I

    move-result v4

    .line 1704
    .local v4, M:I
    if-lez v4, :cond_5d

    .line 1705
    invoke-static/range {v16 .. v16}, Lcom/android/server/AppWidgetServiceImpl;->getAppWidgetIds(Lcom/android/server/AppWidgetServiceImpl$Provider;)[I

    move-result-object v7

    .line 1709
    .local v7, appWidgetIds:[I
    move-object/from16 v0, p0

    move-object/from16 v1, v16

    invoke-virtual {v0, v1}, Lcom/android/server/AppWidgetServiceImpl;->cancelBroadcasts(Lcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1710
    move-object/from16 v0, p0

    move-object/from16 v1, v16

    invoke-virtual {v0, v1, v7}, Lcom/android/server/AppWidgetServiceImpl;->registerForBroadcastsLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    .line 1713
    const/4 v14, 0x0

    .local v14, j:I
    :goto_e0
    if-ge v14, v4, :cond_130

    .line 1714
    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-virtual {v0, v14}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 1715
    .local v12, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    const/16 v20, 0x0

    move-object/from16 v0, v20

    iput-object v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->views:Landroid/widget/RemoteViews;

    .line 1716
    iget-object v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v20, v0

    if-eqz v20, :cond_11f

    iget-object v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    move-object/from16 v20, v0

    if-eqz v20, :cond_11f

    .line 1718
    :try_start_108
    iget-object v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    move-object/from16 v20, v0

    iget v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    move/from16 v21, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    move-object/from16 v22, v0

    invoke-interface/range {v20 .. v22}, Lcom/android/internal/appwidget/IAppWidgetHost;->providerChanged(ILandroid/appwidget/AppWidgetProviderInfo;)V
    :try_end_11f
    .catch Landroid/os/RemoteException; {:try_start_108 .. :try_end_11f} :catch_122

    .line 1713
    :cond_11f
    :goto_11f
    add-int/lit8 v14, v14, 0x1

    goto :goto_e0

    .line 1719
    :catch_122
    move-exception v10

    .line 1723
    .local v10, ex:Landroid/os/RemoteException;
    iget-object v0, v12, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    move-object/from16 v0, v21

    move-object/from16 v1, v20

    iput-object v0, v1, Lcom/android/server/AppWidgetServiceImpl$Host;->callbacks:Lcom/android/internal/appwidget/IAppWidgetHost;

    goto :goto_11f

    .line 1728
    .end local v10           #ex:Landroid/os/RemoteException;
    .end local v12           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_130
    move-object/from16 v0, p0

    move-object/from16 v1, v16

    invoke-virtual {v0, v1, v7}, Lcom/android/server/AppWidgetServiceImpl;->sendUpdateIntentLocked(Lcom/android/server/AppWidgetServiceImpl$Provider;[I)V

    goto/16 :goto_5d

    .line 1736
    .end local v4           #M:I
    .end local v6           #ai:Landroid/content/pm/ActivityInfo;
    .end local v7           #appWidgetIds:[I
    .end local v9           #component:Landroid/content/ComponentName;
    .end local v14           #j:I
    .end local v16           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .end local v17           #parsed:Lcom/android/server/AppWidgetServiceImpl$Provider;
    .end local v19           #ri:Landroid/content/pm/ResolveInfo;
    :cond_139
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Ljava/util/ArrayList;->size()I

    move-result v5

    .line 1737
    add-int/lit8 v11, v5, -0x1

    :goto_145
    if-ltz v11, :cond_61

    .line 1738
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-virtual {v0, v11}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v16

    check-cast v16, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1739
    .restart local v16       #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v20

    move-object/from16 v0, p1

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_18e

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v20

    move-object/from16 v0, v20

    invoke-virtual {v15, v0}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v20

    if-nez v20, :cond_18e

    .line 1741
    move-object/from16 v0, p0

    move-object/from16 v1, v16

    invoke-virtual {v0, v11, v1}, Lcom/android/server/AppWidgetServiceImpl;->removeProviderLocked(ILcom/android/server/AppWidgetServiceImpl$Provider;)V

    .line 1737
    :cond_18e
    add-int/lit8 v11, v11, -0x1

    goto :goto_145
.end method

.method writeStateToFileLocked(Ljava/io/FileOutputStream;)Z
    .registers 15
    .parameter "stream"

    .prologue
    const/4 v10, 0x1

    .line 1417
    :try_start_1
    new-instance v6, Lcom/android/internal/util/FastXmlSerializer;

    invoke-direct {v6}, Lcom/android/internal/util/FastXmlSerializer;-><init>()V

    .line 1418
    .local v6, out:Lorg/xmlpull/v1/XmlSerializer;
    const-string v9, "utf-8"

    invoke-interface {v6, p1, v9}, Lorg/xmlpull/v1/XmlSerializer;->setOutput(Ljava/io/OutputStream;Ljava/lang/String;)V

    .line 1419
    const/4 v9, 0x0

    const/4 v11, 0x1

    invoke-static {v11}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v11

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startDocument(Ljava/lang/String;Ljava/lang/Boolean;)V

    .line 1420
    const/4 v9, 0x0

    const-string v11, "gs"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1422
    const/4 v8, 0x0

    .line 1423
    .local v8, providerIndex:I
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1424
    .local v0, N:I
    const/4 v3, 0x0

    .local v3, i:I
    :goto_22
    if-ge v3, v0, :cond_63

    .line 1425
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mInstalledProviders:Ljava/util/ArrayList;

    invoke-virtual {v9, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/android/server/AppWidgetServiceImpl$Provider;

    .line 1426
    .local v7, p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    iget-object v9, v7, Lcom/android/server/AppWidgetServiceImpl$Provider;->instances:Ljava/util/ArrayList;

    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v9

    if-lez v9, :cond_60

    .line 1427
    const/4 v9, 0x0

    const-string v11, "p"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1428
    const/4 v9, 0x0

    const-string v11, "pkg"

    iget-object v12, v7, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v12, v12, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v12}, Landroid/content/ComponentName;->getPackageName()Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1429
    const/4 v9, 0x0

    const-string v11, "cl"

    iget-object v12, v7, Lcom/android/server/AppWidgetServiceImpl$Provider;->info:Landroid/appwidget/AppWidgetProviderInfo;

    iget-object v12, v12, Landroid/appwidget/AppWidgetProviderInfo;->provider:Landroid/content/ComponentName;

    invoke-virtual {v12}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1430
    const/4 v9, 0x0

    const-string v11, "p"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1431
    iput v8, v7, Lcom/android/server/AppWidgetServiceImpl$Provider;->tag:I

    .line 1432
    add-int/lit8 v8, v8, 0x1

    .line 1424
    :cond_60
    add-int/lit8 v3, v3, 0x1

    goto :goto_22

    .line 1436
    .end local v7           #p:Lcom/android/server/AppWidgetServiceImpl$Provider;
    :cond_63
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1437
    const/4 v3, 0x0

    :goto_6a
    if-ge v3, v0, :cond_99

    .line 1438
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mHosts:Ljava/util/ArrayList;

    invoke-virtual {v9, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/server/AppWidgetServiceImpl$Host;

    .line 1439
    .local v2, host:Lcom/android/server/AppWidgetServiceImpl$Host;
    const/4 v9, 0x0

    const-string v11, "h"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1440
    const/4 v9, 0x0

    const-string v11, "pkg"

    iget-object v12, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->packageName:Ljava/lang/String;

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1441
    const/4 v9, 0x0

    const-string v11, "id"

    iget v12, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->hostId:I

    invoke-static {v12}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1442
    const/4 v9, 0x0

    const-string v11, "h"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1443
    iput v3, v2, Lcom/android/server/AppWidgetServiceImpl$Host;->tag:I

    .line 1437
    add-int/lit8 v3, v3, 0x1

    goto :goto_6a

    .line 1446
    .end local v2           #host:Lcom/android/server/AppWidgetServiceImpl$Host;
    :cond_99
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v9}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1447
    const/4 v3, 0x0

    :goto_a0
    if-ge v3, v0, :cond_e5

    .line 1448
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mAppWidgetIds:Ljava/util/ArrayList;

    invoke-virtual {v9, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;

    .line 1449
    .local v4, id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    const/4 v9, 0x0

    const-string v11, "g"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1450
    const/4 v9, 0x0

    const-string v11, "id"

    iget v12, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->appWidgetId:I

    invoke-static {v12}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1451
    const/4 v9, 0x0

    const-string v11, "h"

    iget-object v12, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->host:Lcom/android/server/AppWidgetServiceImpl$Host;

    iget v12, v12, Lcom/android/server/AppWidgetServiceImpl$Host;->tag:I

    invoke-static {v12}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1452
    iget-object v9, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    if-eqz v9, :cond_dc

    .line 1453
    const/4 v9, 0x0

    const-string v11, "p"

    iget-object v12, v4, Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;->provider:Lcom/android/server/AppWidgetServiceImpl$Provider;

    iget v12, v12, Lcom/android/server/AppWidgetServiceImpl$Provider;->tag:I

    invoke-static {v12}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v12

    invoke-interface {v6, v9, v11, v12}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1455
    :cond_dc
    const/4 v9, 0x0

    const-string v11, "g"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1447
    add-int/lit8 v3, v3, 0x1

    goto :goto_a0

    .line 1458
    .end local v4           #id:Lcom/android/server/AppWidgetServiceImpl$AppWidgetId;
    :cond_e5
    iget-object v9, p0, Lcom/android/server/AppWidgetServiceImpl;->mPackagesWithBindWidgetPermission:Ljava/util/HashSet;

    invoke-virtual {v9}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    move-result-object v5

    .line 1459
    .local v5, it:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/lang/String;>;"
    :goto_eb
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-eqz v9, :cond_125

    .line 1460
    const/4 v9, 0x0

    const-string v11, "b"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1461
    const/4 v11, 0x0

    const-string v12, "packageName"

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/String;

    invoke-interface {v6, v11, v12, v9}, Lorg/xmlpull/v1/XmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1462
    const/4 v9, 0x0

    const-string v11, "b"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;
    :try_end_109
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_109} :catch_10a

    goto :goto_eb

    .line 1469
    .end local v0           #N:I
    .end local v3           #i:I
    .end local v5           #it:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/lang/String;>;"
    .end local v6           #out:Lorg/xmlpull/v1/XmlSerializer;
    .end local v8           #providerIndex:I
    :catch_10a
    move-exception v1

    .line 1470
    .local v1, e:Ljava/io/IOException;
    const-string v9, "AppWidgetServiceImpl"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Failed to write state: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1471
    const/4 v9, 0x0

    .end local v1           #e:Ljava/io/IOException;
    :goto_124
    return v9

    .line 1465
    .restart local v0       #N:I
    .restart local v3       #i:I
    .restart local v5       #it:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/lang/String;>;"
    .restart local v6       #out:Lorg/xmlpull/v1/XmlSerializer;
    .restart local v8       #providerIndex:I
    :cond_125
    const/4 v9, 0x0

    :try_start_126
    const-string v11, "gs"

    invoke-interface {v6, v9, v11}, Lorg/xmlpull/v1/XmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 1467
    invoke-interface {v6}, Lorg/xmlpull/v1/XmlSerializer;->endDocument()V
    :try_end_12e
    .catch Ljava/io/IOException; {:try_start_126 .. :try_end_12e} :catch_10a

    move v9, v10

    .line 1468
    goto :goto_124
.end method
