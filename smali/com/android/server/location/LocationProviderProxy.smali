.class public Lcom/android/server/location/LocationProviderProxy;
.super Ljava/lang/Object;
.source "LocationProviderProxy.java"

# interfaces
.implements Lcom/android/server/location/LocationProviderInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/location/LocationProviderProxy$1;,
        Lcom/android/server/location/LocationProviderProxy$Connection;
    }
.end annotation


# static fields
.field public static final SERVICE_ACTION:Ljava/lang/String; = "com.android.location.service.NetworkLocationProvider"

.field private static final TAG:Ljava/lang/String; = "LocationProviderProxy"


# instance fields
.field private final mContext:Landroid/content/Context;

.field private mEnabled:Z

.field private final mHandler:Landroid/os/Handler;

.field private final mIntent:Landroid/content/Intent;

.field private mLocationTracking:Z

.field private mMinTime:J

.field private mMinTimeSource:Landroid/os/WorkSource;

.field private final mMutex:Ljava/lang/Object;

.field private final mName:Ljava/lang/String;

.field private mNetworkInfo:Landroid/net/NetworkInfo;

.field private mNetworkState:I

.field private mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;


# direct methods
.method public constructor <init>(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Landroid/os/Handler;)V
    .registers 7
    .parameter "context"
    .parameter "name"
    .parameter "packageName"
    .parameter "handler"

    .prologue
    const/4 v1, 0x0

    .line 65
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 52
    new-instance v0, Ljava/lang/Object;

    invoke-direct/range {v0 .. v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    .line 56
    iput-boolean v1, p0, Lcom/android/server/location/LocationProviderProxy;->mLocationTracking:Z

    .line 57
    iput-boolean v1, p0, Lcom/android/server/location/LocationProviderProxy;->mEnabled:Z

    .line 58
    const-wide/16 v0, -0x1

    iput-wide v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTime:J

    .line 59
    new-instance v0, Landroid/os/WorkSource;

    invoke-direct {v0}, Landroid/os/WorkSource;-><init>()V

    iput-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTimeSource:Landroid/os/WorkSource;

    .line 66
    iput-object p1, p0, Lcom/android/server/location/LocationProviderProxy;->mContext:Landroid/content/Context;

    .line 67
    iput-object p2, p0, Lcom/android/server/location/LocationProviderProxy;->mName:Ljava/lang/String;

    .line 68
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.android.location.service.NetworkLocationProvider"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mIntent:Landroid/content/Intent;

    .line 69
    iput-object p4, p0, Lcom/android/server/location/LocationProviderProxy;->mHandler:Landroid/os/Handler;

    .line 70
    invoke-virtual {p0, p3}, Lcom/android/server/location/LocationProviderProxy;->reconnect(Ljava/lang/String;)V

    .line 71
    return-void
.end method

.method static synthetic access$100(Lcom/android/server/location/LocationProviderProxy;)Landroid/os/Handler;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/android/server/location/LocationProviderProxy;)Ljava/lang/String;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mName:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/server/location/LocationProviderProxy;)Ljava/lang/Object;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/server/location/LocationProviderProxy;)Lcom/android/server/location/LocationProviderProxy$Connection;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    return-object v0
.end method

.method static synthetic access$400(Lcom/android/server/location/LocationProviderProxy;)Z
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-boolean v0, p0, Lcom/android/server/location/LocationProviderProxy;->mEnabled:Z

    return v0
.end method

.method static synthetic access$500(Lcom/android/server/location/LocationProviderProxy;)Z
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-boolean v0, p0, Lcom/android/server/location/LocationProviderProxy;->mLocationTracking:Z

    return v0
.end method

.method static synthetic access$600(Lcom/android/server/location/LocationProviderProxy;)J
    .registers 3
    .parameter "x0"

    .prologue
    .line 41
    iget-wide v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTime:J

    return-wide v0
.end method

.method static synthetic access$700(Lcom/android/server/location/LocationProviderProxy;)Landroid/os/WorkSource;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTimeSource:Landroid/os/WorkSource;

    return-object v0
.end method

.method static synthetic access$800(Lcom/android/server/location/LocationProviderProxy;)Landroid/net/NetworkInfo;
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mNetworkInfo:Landroid/net/NetworkInfo;

    return-object v0
.end method

.method static synthetic access$900(Lcom/android/server/location/LocationProviderProxy;)I
    .registers 2
    .parameter "x0"

    .prologue
    .line 41
    iget v0, p0, Lcom/android/server/location/LocationProviderProxy;->mNetworkState:I

    return v0
.end method

.method private getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;
    .registers 3

    .prologue
    .line 171
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v1

    .line 172
    :try_start_3
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v0}, Lcom/android/server/location/LocationProviderProxy$Connection;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    monitor-exit v1

    return-object v0

    .line 173
    :catchall_b
    move-exception v0

    monitor-exit v1
    :try_end_d
    .catchall {:try_start_3 .. :try_end_d} :catchall_b

    throw v0
.end method


# virtual methods
.method public addListener(I)V
    .registers 5
    .parameter "uid"

    .prologue
    .line 451
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 452
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_8
    .catchall {:try_start_3 .. :try_end_8} :catchall_10

    move-result-object v0

    .line 453
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_e

    .line 455
    :try_start_b
    invoke-interface {v0, p1}, Landroid/location/ILocationProvider;->addListener(I)V
    :try_end_e
    .catchall {:try_start_b .. :try_end_e} :catchall_10
    .catch Landroid/os/RemoteException; {:try_start_b .. :try_end_e} :catch_13

    .line 459
    :cond_e
    :goto_e
    :try_start_e
    monitor-exit v2

    .line 460
    return-void

    .line 459
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_10
    move-exception v1

    monitor-exit v2
    :try_end_12
    .catchall {:try_start_e .. :try_end_12} :catchall_10

    throw v1

    .line 456
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_13
    move-exception v1

    goto :goto_e
.end method

.method public disable()V
    .registers 4

    .prologue
    .line 303
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 304
    const/4 v1, 0x0

    :try_start_4
    iput-boolean v1, p0, Lcom/android/server/location/LocationProviderProxy;->mEnabled:Z

    .line 305
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_b
    .catchall {:try_start_4 .. :try_end_b} :catchall_13

    move-result-object v0

    .line 306
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_11

    .line 308
    :try_start_e
    invoke-interface {v0}, Landroid/location/ILocationProvider;->disable()V
    :try_end_11
    .catchall {:try_start_e .. :try_end_11} :catchall_13
    .catch Landroid/os/RemoteException; {:try_start_e .. :try_end_11} :catch_16

    .line 312
    :cond_11
    :goto_11
    :try_start_11
    monitor-exit v2

    .line 313
    return-void

    .line 312
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_13
    move-exception v1

    monitor-exit v2
    :try_end_15
    .catchall {:try_start_11 .. :try_end_15} :catchall_13

    throw v1

    .line 309
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_16
    move-exception v1

    goto :goto_11
.end method

.method public enable()V
    .registers 4

    .prologue
    .line 290
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 291
    const/4 v1, 0x1

    :try_start_4
    iput-boolean v1, p0, Lcom/android/server/location/LocationProviderProxy;->mEnabled:Z

    .line 292
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_b
    .catchall {:try_start_4 .. :try_end_b} :catchall_13

    move-result-object v0

    .line 293
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_11

    .line 295
    :try_start_e
    invoke-interface {v0}, Landroid/location/ILocationProvider;->enable()V
    :try_end_11
    .catchall {:try_start_e .. :try_end_11} :catchall_13
    .catch Landroid/os/RemoteException; {:try_start_e .. :try_end_11} :catch_16

    .line 299
    :cond_11
    :goto_11
    :try_start_11
    monitor-exit v2

    .line 300
    return-void

    .line 299
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_13
    move-exception v1

    monitor-exit v2
    :try_end_15
    .catchall {:try_start_11 .. :try_end_15} :catchall_13

    throw v1

    .line 296
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_16
    move-exception v1

    goto :goto_11
.end method

.method public enableLocationTracking(Z)V
    .registers 7
    .parameter "enable"

    .prologue
    .line 371
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 372
    :try_start_3
    iput-boolean p1, p0, Lcom/android/server/location/LocationProviderProxy;->mLocationTracking:Z

    .line 373
    if-nez p1, :cond_10

    .line 374
    const-wide/16 v3, -0x1

    iput-wide v3, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTime:J

    .line 375
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTimeSource:Landroid/os/WorkSource;

    invoke-virtual {v1}, Landroid/os/WorkSource;->clear()V

    .line 377
    :cond_10
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_15
    .catchall {:try_start_3 .. :try_end_15} :catchall_1d

    move-result-object v0

    .line 378
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_1b

    .line 380
    :try_start_18
    invoke-interface {v0, p1}, Landroid/location/ILocationProvider;->enableLocationTracking(Z)V
    :try_end_1b
    .catchall {:try_start_18 .. :try_end_1b} :catchall_1d
    .catch Landroid/os/RemoteException; {:try_start_18 .. :try_end_1b} :catch_20

    .line 384
    :cond_1b
    :goto_1b
    :try_start_1b
    monitor-exit v2

    .line 385
    return-void

    .line 384
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_1d
    move-exception v1

    monitor-exit v2
    :try_end_1f
    .catchall {:try_start_1b .. :try_end_1f} :catchall_1d

    throw v1

    .line 381
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_20
    move-exception v1

    goto :goto_1b
.end method

.method public getAccuracy()I
    .registers 3

    .prologue
    .line 249
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 250
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 251
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->getAccuracy()I

    move-result v1

    .line 253
    :goto_a
    return v1

    :cond_b
    const/4 v1, -0x1

    goto :goto_a
.end method

.method public getInternalState()Ljava/lang/String;
    .registers 5

    .prologue
    .line 351
    iget-object v3, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v3

    .line 352
    :try_start_3
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v2}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;

    move-result-object v1

    .line 353
    .local v1, provider:Landroid/location/ILocationProvider;
    monitor-exit v3
    :try_end_a
    .catchall {:try_start_3 .. :try_end_a} :catchall_11

    .line 354
    if-eqz v1, :cond_1c

    .line 356
    :try_start_c
    invoke-interface {v1}, Landroid/location/ILocationProvider;->getInternalState()Ljava/lang/String;
    :try_end_f
    .catch Landroid/os/RemoteException; {:try_start_c .. :try_end_f} :catch_14

    move-result-object v2

    .line 361
    :goto_10
    return-object v2

    .line 353
    .end local v1           #provider:Landroid/location/ILocationProvider;
    :catchall_11
    move-exception v2

    :try_start_12
    monitor-exit v3
    :try_end_13
    .catchall {:try_start_12 .. :try_end_13} :catchall_11

    throw v2

    .line 357
    .restart local v1       #provider:Landroid/location/ILocationProvider;
    :catch_14
    move-exception v0

    .line 358
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "LocationProviderProxy"

    const-string v3, "getInternalState failed"

    invoke-static {v2, v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 361
    .end local v0           #e:Landroid/os/RemoteException;
    :cond_1c
    const/4 v2, 0x0

    goto :goto_10
.end method

.method public getMinTime()J
    .registers 5

    .prologue
    .line 392
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v1

    .line 393
    :try_start_3
    iget-wide v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTime:J

    monitor-exit v1

    return-wide v2

    .line 394
    :catchall_7
    move-exception v0

    monitor-exit v1
    :try_end_9
    .catchall {:try_start_3 .. :try_end_9} :catchall_7

    throw v0
.end method

.method public getName()Ljava/lang/String;
    .registers 2

    .prologue
    .line 167
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mName:Ljava/lang/String;

    return-object v0
.end method

.method public getPowerRequirement()I
    .registers 3

    .prologue
    .line 240
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 241
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 242
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->getPowerRequirement()I

    move-result v1

    .line 244
    :goto_a
    return v1

    :cond_b
    const/4 v1, -0x1

    goto :goto_a
.end method

.method public getStatus(Landroid/os/Bundle;)I
    .registers 5
    .parameter "extras"

    .prologue
    .line 323
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 324
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;

    move-result-object v0

    .line 325
    .local v0, provider:Landroid/location/ILocationProvider;
    monitor-exit v2
    :try_end_a
    .catchall {:try_start_3 .. :try_end_a} :catchall_11

    .line 326
    if-eqz v0, :cond_15

    .line 328
    :try_start_c
    invoke-interface {v0, p1}, Landroid/location/ILocationProvider;->getStatus(Landroid/os/Bundle;)I
    :try_end_f
    .catch Landroid/os/RemoteException; {:try_start_c .. :try_end_f} :catch_14

    move-result v1

    .line 332
    :goto_10
    return v1

    .line 325
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_11
    move-exception v1

    :try_start_12
    monitor-exit v2
    :try_end_13
    .catchall {:try_start_12 .. :try_end_13} :catchall_11

    throw v1

    .line 329
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_14
    move-exception v1

    .line 332
    :cond_15
    const/4 v1, 0x0

    goto :goto_10
.end method

.method public getStatusUpdateTime()J
    .registers 4

    .prologue
    .line 337
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 338
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;

    move-result-object v0

    .line 339
    .local v0, provider:Landroid/location/ILocationProvider;
    monitor-exit v2
    :try_end_a
    .catchall {:try_start_3 .. :try_end_a} :catchall_11

    .line 340
    if-eqz v0, :cond_15

    .line 342
    :try_start_c
    invoke-interface {v0}, Landroid/location/ILocationProvider;->getStatusUpdateTime()J
    :try_end_f
    .catch Landroid/os/RemoteException; {:try_start_c .. :try_end_f} :catch_14

    move-result-wide v1

    .line 346
    :goto_10
    return-wide v1

    .line 339
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_11
    move-exception v1

    :try_start_12
    monitor-exit v2
    :try_end_13
    .catchall {:try_start_12 .. :try_end_13} :catchall_11

    throw v1

    .line 343
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_14
    move-exception v1

    .line 346
    :cond_15
    const-wide/16 v1, 0x0

    goto :goto_10
.end method

.method public hasMonetaryCost()Z
    .registers 3

    .prologue
    .line 204
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 205
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 206
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->hasMonetaryCost()Z

    move-result v1

    .line 208
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public isEnabled()Z
    .registers 3

    .prologue
    .line 316
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v1

    .line 317
    :try_start_3
    iget-boolean v0, p0, Lcom/android/server/location/LocationProviderProxy;->mEnabled:Z

    monitor-exit v1

    return v0

    .line 318
    :catchall_7
    move-exception v0

    monitor-exit v1
    :try_end_9
    .catchall {:try_start_3 .. :try_end_9} :catchall_7

    throw v0
.end method

.method public isLocationTracking()Z
    .registers 3

    .prologue
    .line 365
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v1

    .line 366
    :try_start_3
    iget-boolean v0, p0, Lcom/android/server/location/LocationProviderProxy;->mLocationTracking:Z

    monitor-exit v1

    return v0

    .line 367
    :catchall_7
    move-exception v0

    monitor-exit v1
    :try_end_9
    .catchall {:try_start_3 .. :try_end_9} :catchall_7

    throw v0
.end method

.method public meetsCriteria(Landroid/location/Criteria;)Z
    .registers 7
    .parameter "criteria"

    .prologue
    const/4 v2, 0x0

    .line 258
    iget-object v3, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v3

    .line 259
    :try_start_4
    iget-object v4, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v4}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_9
    .catchall {:try_start_4 .. :try_end_9} :catchall_56

    move-result-object v1

    .line 260
    .local v1, provider:Landroid/location/ILocationProvider;
    if-eqz v1, :cond_13

    .line 262
    :try_start_c
    invoke-interface {v1, p1}, Landroid/location/ILocationProvider;->meetsCriteria(Landroid/location/Criteria;)Z
    :try_end_f
    .catchall {:try_start_c .. :try_end_f} :catchall_56
    .catch Landroid/os/RemoteException; {:try_start_c .. :try_end_f} :catch_12

    move-result v2

    :try_start_10
    monitor-exit v3

    .line 286
    :cond_11
    :goto_11
    return v2

    .line 263
    :catch_12
    move-exception v4

    .line 266
    :cond_13
    monitor-exit v3
    :try_end_14
    .catchall {:try_start_10 .. :try_end_14} :catchall_56

    .line 268
    invoke-virtual {p1}, Landroid/location/Criteria;->getAccuracy()I

    move-result v3

    if-eqz v3, :cond_24

    invoke-virtual {p1}, Landroid/location/Criteria;->getAccuracy()I

    move-result v3

    invoke-virtual {p0}, Lcom/android/server/location/LocationProviderProxy;->getAccuracy()I

    move-result v4

    if-lt v3, v4, :cond_11

    .line 272
    :cond_24
    invoke-virtual {p1}, Landroid/location/Criteria;->getPowerRequirement()I

    move-result v0

    .line 273
    .local v0, criteriaPower:I
    if-eqz v0, :cond_30

    invoke-virtual {p0}, Lcom/android/server/location/LocationProviderProxy;->getPowerRequirement()I

    move-result v3

    if-lt v0, v3, :cond_11

    .line 277
    :cond_30
    invoke-virtual {p1}, Landroid/location/Criteria;->isAltitudeRequired()Z

    move-result v3

    if-eqz v3, :cond_3c

    invoke-virtual {p0}, Lcom/android/server/location/LocationProviderProxy;->supportsAltitude()Z

    move-result v3

    if-eqz v3, :cond_11

    .line 280
    :cond_3c
    invoke-virtual {p1}, Landroid/location/Criteria;->isSpeedRequired()Z

    move-result v3

    if-eqz v3, :cond_48

    invoke-virtual {p0}, Lcom/android/server/location/LocationProviderProxy;->supportsSpeed()Z

    move-result v3

    if-eqz v3, :cond_11

    .line 283
    :cond_48
    invoke-virtual {p1}, Landroid/location/Criteria;->isBearingRequired()Z

    move-result v3

    if-eqz v3, :cond_54

    invoke-virtual {p0}, Lcom/android/server/location/LocationProviderProxy;->supportsBearing()Z

    move-result v3

    if-eqz v3, :cond_11

    .line 286
    :cond_54
    const/4 v2, 0x1

    goto :goto_11

    .line 266
    .end local v0           #criteriaPower:I
    .end local v1           #provider:Landroid/location/ILocationProvider;
    :catchall_56
    move-exception v2

    :try_start_57
    monitor-exit v3
    :try_end_58
    .catchall {:try_start_57 .. :try_end_58} :catchall_56

    throw v2
.end method

.method public reconnect(Ljava/lang/String;)V
    .registers 7
    .parameter "packageName"

    .prologue
    .line 75
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v1

    .line 76
    :try_start_3
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    if-eqz v0, :cond_e

    .line 77
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v0, v2}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 79
    :cond_e
    new-instance v0, Lcom/android/server/location/LocationProviderProxy$Connection;

    const/4 v2, 0x0

    invoke-direct {v0, p0, v2}, Lcom/android/server/location/LocationProviderProxy$Connection;-><init>(Lcom/android/server/location/LocationProviderProxy;Lcom/android/server/location/LocationProviderProxy$1;)V

    iput-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    .line 80
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mIntent:Landroid/content/Intent;

    invoke-virtual {v0, p1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 81
    iget-object v0, p0, Lcom/android/server/location/LocationProviderProxy;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mIntent:Landroid/content/Intent;

    iget-object v3, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    const/16 v4, 0x15

    invoke-virtual {v0, v2, v3, v4}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 84
    monitor-exit v1

    .line 85
    return-void

    .line 84
    :catchall_28
    move-exception v0

    monitor-exit v1
    :try_end_2a
    .catchall {:try_start_3 .. :try_end_2a} :catchall_28

    throw v0
.end method

.method public removeListener(I)V
    .registers 5
    .parameter "uid"

    .prologue
    .line 463
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 464
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_8
    .catchall {:try_start_3 .. :try_end_8} :catchall_10

    move-result-object v0

    .line 465
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_e

    .line 467
    :try_start_b
    invoke-interface {v0, p1}, Landroid/location/ILocationProvider;->removeListener(I)V
    :try_end_e
    .catchall {:try_start_b .. :try_end_e} :catchall_10
    .catch Landroid/os/RemoteException; {:try_start_b .. :try_end_e} :catch_13

    .line 471
    :cond_e
    :goto_e
    :try_start_e
    monitor-exit v2

    .line 472
    return-void

    .line 471
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_10
    move-exception v1

    monitor-exit v2
    :try_end_12
    .catchall {:try_start_e .. :try_end_12} :catchall_10

    throw v1

    .line 468
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_13
    move-exception v1

    goto :goto_e
.end method

.method public requestSingleShotFix()Z
    .registers 2

    .prologue
    .line 388
    const/4 v0, 0x0

    return v0
.end method

.method public requiresCell()Z
    .registers 3

    .prologue
    .line 195
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 196
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 197
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->requiresCell()Z

    move-result v1

    .line 199
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public requiresNetwork()Z
    .registers 3

    .prologue
    .line 177
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 178
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 179
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->requiresNetwork()Z

    move-result v1

    .line 181
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public requiresSatellite()Z
    .registers 3

    .prologue
    .line 186
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 187
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 188
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->requiresSatellite()Z

    move-result v1

    .line 190
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public sendExtraCommand(Ljava/lang/String;Landroid/os/Bundle;)Z
    .registers 6
    .parameter "command"
    .parameter "extras"

    .prologue
    .line 438
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 439
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_8
    .catchall {:try_start_3 .. :try_end_8} :catchall_15

    move-result-object v0

    .line 440
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_12

    .line 442
    :try_start_b
    invoke-interface {v0, p1, p2}, Landroid/location/ILocationProvider;->sendExtraCommand(Ljava/lang/String;Landroid/os/Bundle;)Z
    :try_end_e
    .catchall {:try_start_b .. :try_end_e} :catchall_15
    .catch Landroid/os/RemoteException; {:try_start_b .. :try_end_e} :catch_11

    move-result v1

    :try_start_f
    monitor-exit v2

    .line 447
    :goto_10
    return v1

    .line 443
    :catch_11
    move-exception v1

    .line 446
    :cond_12
    monitor-exit v2

    .line 447
    const/4 v1, 0x0

    goto :goto_10

    .line 446
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_15
    move-exception v1

    monitor-exit v2
    :try_end_17
    .catchall {:try_start_f .. :try_end_17} :catchall_15

    throw v1
.end method

.method public setMinTime(JLandroid/os/WorkSource;)V
    .registers 7
    .parameter "minTime"
    .parameter "ws"

    .prologue
    .line 398
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 399
    :try_start_3
    iput-wide p1, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTime:J

    .line 400
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mMinTimeSource:Landroid/os/WorkSource;

    invoke-virtual {v1, p3}, Landroid/os/WorkSource;->set(Landroid/os/WorkSource;)V

    .line 401
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_f
    .catchall {:try_start_3 .. :try_end_f} :catchall_17

    move-result-object v0

    .line 402
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_15

    .line 404
    :try_start_12
    invoke-interface {v0, p1, p2, p3}, Landroid/location/ILocationProvider;->setMinTime(JLandroid/os/WorkSource;)V
    :try_end_15
    .catchall {:try_start_12 .. :try_end_15} :catchall_17
    .catch Landroid/os/RemoteException; {:try_start_12 .. :try_end_15} :catch_1a

    .line 408
    :cond_15
    :goto_15
    :try_start_15
    monitor-exit v2

    .line 409
    return-void

    .line 408
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_17
    move-exception v1

    monitor-exit v2
    :try_end_19
    .catchall {:try_start_15 .. :try_end_19} :catchall_17

    throw v1

    .line 405
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_1a
    move-exception v1

    goto :goto_15
.end method

.method public supportsAltitude()Z
    .registers 3

    .prologue
    .line 213
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 214
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 215
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->supportsAltitude()Z

    move-result v1

    .line 217
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public supportsBearing()Z
    .registers 3

    .prologue
    .line 231
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 232
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 233
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->supportsBearing()Z

    move-result v1

    .line 235
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public supportsSpeed()Z
    .registers 3

    .prologue
    .line 222
    invoke-direct {p0}, Lcom/android/server/location/LocationProviderProxy;->getCachedAttributes()Lcom/android/internal/location/DummyLocationProvider;

    move-result-object v0

    .line 223
    .local v0, cachedAttributes:Lcom/android/internal/location/DummyLocationProvider;
    if-eqz v0, :cond_b

    .line 224
    invoke-virtual {v0}, Lcom/android/internal/location/DummyLocationProvider;->supportsSpeed()Z

    move-result v1

    .line 226
    :goto_a
    return v1

    :cond_b
    const/4 v1, 0x0

    goto :goto_a
.end method

.method public updateLocation(Landroid/location/Location;)V
    .registers 5
    .parameter "location"

    .prologue
    .line 426
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 427
    :try_start_3
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_8
    .catchall {:try_start_3 .. :try_end_8} :catchall_10

    move-result-object v0

    .line 428
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_e

    .line 430
    :try_start_b
    invoke-interface {v0, p1}, Landroid/location/ILocationProvider;->updateLocation(Landroid/location/Location;)V
    :try_end_e
    .catchall {:try_start_b .. :try_end_e} :catchall_10
    .catch Landroid/os/RemoteException; {:try_start_b .. :try_end_e} :catch_13

    .line 434
    :cond_e
    :goto_e
    :try_start_e
    monitor-exit v2

    .line 435
    return-void

    .line 434
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_10
    move-exception v1

    monitor-exit v2
    :try_end_12
    .catchall {:try_start_e .. :try_end_12} :catchall_10

    throw v1

    .line 431
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_13
    move-exception v1

    goto :goto_e
.end method

.method public updateNetworkState(ILandroid/net/NetworkInfo;)V
    .registers 6
    .parameter "state"
    .parameter "info"

    .prologue
    .line 412
    iget-object v2, p0, Lcom/android/server/location/LocationProviderProxy;->mMutex:Ljava/lang/Object;

    monitor-enter v2

    .line 413
    :try_start_3
    iput p1, p0, Lcom/android/server/location/LocationProviderProxy;->mNetworkState:I

    .line 414
    iput-object p2, p0, Lcom/android/server/location/LocationProviderProxy;->mNetworkInfo:Landroid/net/NetworkInfo;

    .line 415
    iget-object v1, p0, Lcom/android/server/location/LocationProviderProxy;->mServiceConnection:Lcom/android/server/location/LocationProviderProxy$Connection;

    invoke-virtual {v1}, Lcom/android/server/location/LocationProviderProxy$Connection;->getProvider()Landroid/location/ILocationProvider;
    :try_end_c
    .catchall {:try_start_3 .. :try_end_c} :catchall_14

    move-result-object v0

    .line 416
    .local v0, provider:Landroid/location/ILocationProvider;
    if-eqz v0, :cond_12

    .line 418
    :try_start_f
    invoke-interface {v0, p1, p2}, Landroid/location/ILocationProvider;->updateNetworkState(ILandroid/net/NetworkInfo;)V
    :try_end_12
    .catchall {:try_start_f .. :try_end_12} :catchall_14
    .catch Landroid/os/RemoteException; {:try_start_f .. :try_end_12} :catch_17

    .line 422
    :cond_12
    :goto_12
    :try_start_12
    monitor-exit v2

    .line 423
    return-void

    .line 422
    .end local v0           #provider:Landroid/location/ILocationProvider;
    :catchall_14
    move-exception v1

    monitor-exit v2
    :try_end_16
    .catchall {:try_start_12 .. :try_end_16} :catchall_14

    throw v1

    .line 419
    .restart local v0       #provider:Landroid/location/ILocationProvider;
    :catch_17
    move-exception v1

    goto :goto_12
.end method
