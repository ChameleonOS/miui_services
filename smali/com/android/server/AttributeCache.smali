.class public final Lcom/android/server/AttributeCache;
.super Ljava/lang/Object;
.source "AttributeCache.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/AttributeCache$Entry;,
        Lcom/android/server/AttributeCache$Package;,
        Lcom/android/server/AttributeCache$Injector;
    }
.end annotation


# static fields
.field private static sInstance:Lcom/android/server/AttributeCache;


# instance fields
.field private final mConfiguration:Landroid/content/res/Configuration;

.field private final mContext:Landroid/content/Context;

.field private final mPackages:Ljava/util/WeakHashMap;
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/WeakHashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/ref/WeakReference",
            "<",
            "Lcom/android/server/AttributeCache$Package;",
            ">;>;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 52
    const/4 v0, 0x0

    sput-object v0, Lcom/android/server/AttributeCache;->sInstance:Lcom/android/server/AttributeCache;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .registers 3
    .parameter "context"

    .prologue
    .line 90
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 55
    new-instance v0, Ljava/util/WeakHashMap;

    invoke-direct {v0}, Ljava/util/WeakHashMap;-><init>()V

    iput-object v0, p0, Lcom/android/server/AttributeCache;->mPackages:Ljava/util/WeakHashMap;

    .line 58
    new-instance v0, Landroid/content/res/Configuration;

    invoke-direct {v0}, Landroid/content/res/Configuration;-><init>()V

    iput-object v0, p0, Lcom/android/server/AttributeCache;->mConfiguration:Landroid/content/res/Configuration;

    .line 91
    iput-object p1, p0, Lcom/android/server/AttributeCache;->mContext:Landroid/content/Context;

    .line 92
    return-void
.end method

.method public static init(Landroid/content/Context;)V
    .registers 2
    .parameter "context"

    .prologue
    .line 81
    sget-object v0, Lcom/android/server/AttributeCache;->sInstance:Lcom/android/server/AttributeCache;

    if-nez v0, :cond_b

    .line 82
    new-instance v0, Lcom/android/server/AttributeCache;

    invoke-direct {v0, p0}, Lcom/android/server/AttributeCache;-><init>(Landroid/content/Context;)V

    sput-object v0, Lcom/android/server/AttributeCache;->sInstance:Lcom/android/server/AttributeCache;

    .line 84
    :cond_b
    return-void
.end method

.method public static instance()Lcom/android/server/AttributeCache;
    .registers 1

    .prologue
    .line 87
    sget-object v0, Lcom/android/server/AttributeCache;->sInstance:Lcom/android/server/AttributeCache;

    return-object v0
.end method


# virtual methods
.method public get(Ljava/lang/String;I[I)Lcom/android/server/AttributeCache$Entry;
    .registers 13
    .parameter "packageName"
    .parameter "resId"
    .parameter "styleable"

    .prologue
    const/4 v6, 0x0

    .line 115
    monitor-enter p0

    .line 116
    :try_start_2
    iget-object v7, p0, Lcom/android/server/AttributeCache;->mPackages:Ljava/util/WeakHashMap;

    invoke-static {v7, p1}, Lcom/android/server/AttributeCache$Injector;->getPackage(Ljava/util/WeakHashMap;Ljava/lang/String;)Lcom/android/server/AttributeCache$Package;

    move-result-object v5

    .line 117
    .local v5, pkg:Lcom/android/server/AttributeCache$Package;
    const/4 v4, 0x0

    .line 118
    .local v4, map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    const/4 v2, 0x0

    .line 119
    .local v2, ent:Lcom/android/server/AttributeCache$Entry;
    if-eqz v5, :cond_23

    .line 120
    #getter for: Lcom/android/server/AttributeCache$Package;->mMap:Landroid/util/SparseArray;
    invoke-static {v5}, Lcom/android/server/AttributeCache$Package;->access$000(Lcom/android/server/AttributeCache$Package;)Landroid/util/SparseArray;

    move-result-object v7

    invoke-virtual {v7, p2}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    .end local v4           #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    check-cast v4, Ljava/util/HashMap;

    .line 121
    .restart local v4       #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    if-eqz v4, :cond_3e

    .line 122
    invoke-virtual {v4, p3}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .end local v2           #ent:Lcom/android/server/AttributeCache$Entry;
    check-cast v2, Lcom/android/server/AttributeCache$Entry;

    .line 123
    .restart local v2       #ent:Lcom/android/server/AttributeCache$Entry;
    if-eqz v2, :cond_3e

    .line 124
    monitor-exit p0
    :try_end_21
    .catchall {:try_start_2 .. :try_end_21} :catchall_2e

    move-object v6, v2

    .line 154
    :goto_22
    return-object v6

    .line 130
    :cond_23
    :try_start_23
    iget-object v7, p0, Lcom/android/server/AttributeCache;->mContext:Landroid/content/Context;

    const/4 v8, 0x0

    invoke-virtual {v7, p1, v8}, Landroid/content/Context;->createPackageContext(Ljava/lang/String;I)Landroid/content/Context;
    :try_end_29
    .catchall {:try_start_23 .. :try_end_29} :catchall_2e
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_23 .. :try_end_29} :catch_31

    move-result-object v0

    .line 131
    .local v0, context:Landroid/content/Context;
    if-nez v0, :cond_34

    .line 132
    :try_start_2c
    monitor-exit p0

    goto :goto_22

    .line 155
    .end local v0           #context:Landroid/content/Context;
    .end local v2           #ent:Lcom/android/server/AttributeCache$Entry;
    .end local v4           #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    .end local v5           #pkg:Lcom/android/server/AttributeCache$Package;
    :catchall_2e
    move-exception v6

    monitor-exit p0
    :try_end_30
    .catchall {:try_start_2c .. :try_end_30} :catchall_2e

    throw v6

    .line 134
    .restart local v2       #ent:Lcom/android/server/AttributeCache$Entry;
    .restart local v4       #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    .restart local v5       #pkg:Lcom/android/server/AttributeCache$Package;
    :catch_31
    move-exception v1

    .line 135
    .local v1, e:Landroid/content/pm/PackageManager$NameNotFoundException;
    :try_start_32
    monitor-exit p0

    goto :goto_22

    .line 137
    .end local v1           #e:Landroid/content/pm/PackageManager$NameNotFoundException;
    .restart local v0       #context:Landroid/content/Context;
    :cond_34
    new-instance v5, Lcom/android/server/AttributeCache$Package;

    .end local v5           #pkg:Lcom/android/server/AttributeCache$Package;
    invoke-direct {v5, v0}, Lcom/android/server/AttributeCache$Package;-><init>(Landroid/content/Context;)V

    .line 138
    .restart local v5       #pkg:Lcom/android/server/AttributeCache$Package;
    iget-object v7, p0, Lcom/android/server/AttributeCache;->mPackages:Ljava/util/WeakHashMap;

    invoke-static {v7, p1, v5}, Lcom/android/server/AttributeCache$Injector;->putPackage(Ljava/util/WeakHashMap;Ljava/lang/String;Lcom/android/server/AttributeCache$Package;)V

    .end local v0           #context:Landroid/content/Context;
    :cond_3e
    move-object v3, v2

    .line 141
    .end local v2           #ent:Lcom/android/server/AttributeCache$Entry;
    .local v3, ent:Lcom/android/server/AttributeCache$Entry;
    if-nez v4, :cond_4d

    .line 142
    new-instance v4, Ljava/util/HashMap;

    .end local v4           #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    invoke-direct {v4}, Ljava/util/HashMap;-><init>()V

    .line 143
    .restart local v4       #map:Ljava/util/HashMap;,"Ljava/util/HashMap<[ILcom/android/server/AttributeCache$Entry;>;"
    #getter for: Lcom/android/server/AttributeCache$Package;->mMap:Landroid/util/SparseArray;
    invoke-static {v5}, Lcom/android/server/AttributeCache$Package;->access$000(Lcom/android/server/AttributeCache$Package;)Landroid/util/SparseArray;

    move-result-object v7

    invoke-virtual {v7, p2, v4}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V
    :try_end_4d
    .catchall {:try_start_32 .. :try_end_4d} :catchall_2e

    .line 147
    :cond_4d
    :try_start_4d
    new-instance v2, Lcom/android/server/AttributeCache$Entry;

    iget-object v7, v5, Lcom/android/server/AttributeCache$Package;->context:Landroid/content/Context;

    iget-object v8, v5, Lcom/android/server/AttributeCache$Package;->context:Landroid/content/Context;

    invoke-virtual {v8, p2, p3}, Landroid/content/Context;->obtainStyledAttributes(I[I)Landroid/content/res/TypedArray;

    move-result-object v8

    invoke-direct {v2, v7, v8}, Lcom/android/server/AttributeCache$Entry;-><init>(Landroid/content/Context;Landroid/content/res/TypedArray;)V
    :try_end_5a
    .catchall {:try_start_4d .. :try_end_5a} :catchall_2e
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_4d .. :try_end_5a} :catch_60

    .line 149
    .end local v3           #ent:Lcom/android/server/AttributeCache$Entry;
    .restart local v2       #ent:Lcom/android/server/AttributeCache$Entry;
    :try_start_5a
    invoke-virtual {v4, p3, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_5d
    .catchall {:try_start_5a .. :try_end_5d} :catchall_2e
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_5a .. :try_end_5d} :catch_64

    .line 154
    :try_start_5d
    monitor-exit p0

    move-object v6, v2

    goto :goto_22

    .line 150
    .end local v2           #ent:Lcom/android/server/AttributeCache$Entry;
    .restart local v3       #ent:Lcom/android/server/AttributeCache$Entry;
    :catch_60
    move-exception v1

    move-object v2, v3

    .line 151
    .end local v3           #ent:Lcom/android/server/AttributeCache$Entry;
    .local v1, e:Landroid/content/res/Resources$NotFoundException;
    .restart local v2       #ent:Lcom/android/server/AttributeCache$Entry;
    :goto_62
    monitor-exit p0
    :try_end_63
    .catchall {:try_start_5d .. :try_end_63} :catchall_2e

    goto :goto_22

    .line 150
    .end local v1           #e:Landroid/content/res/Resources$NotFoundException;
    :catch_64
    move-exception v1

    goto :goto_62
.end method

.method public removePackage(Ljava/lang/String;)V
    .registers 3
    .parameter "packageName"

    .prologue
    .line 95
    monitor-enter p0

    .line 96
    :try_start_1
    iget-object v0, p0, Lcom/android/server/AttributeCache;->mPackages:Ljava/util/WeakHashMap;

    invoke-virtual {v0, p1}, Ljava/util/WeakHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 97
    monitor-exit p0

    .line 98
    return-void

    .line 97
    :catchall_8
    move-exception v0

    monitor-exit p0
    :try_end_a
    .catchall {:try_start_1 .. :try_end_a} :catchall_8

    throw v0
.end method

.method public updateConfiguration(Landroid/content/res/Configuration;)V
    .registers 4
    .parameter "config"

    .prologue
    .line 101
    monitor-enter p0

    .line 102
    :try_start_1
    iget-object v1, p0, Lcom/android/server/AttributeCache;->mConfiguration:Landroid/content/res/Configuration;

    invoke-virtual {v1, p1}, Landroid/content/res/Configuration;->updateFrom(Landroid/content/res/Configuration;)I

    move-result v0

    .line 103
    .local v0, changes:I
    const v1, -0x400000a1

    and-int/2addr v1, v0

    if-eqz v1, :cond_12

    .line 109
    iget-object v1, p0, Lcom/android/server/AttributeCache;->mPackages:Ljava/util/WeakHashMap;

    invoke-virtual {v1}, Ljava/util/WeakHashMap;->clear()V

    .line 111
    :cond_12
    monitor-exit p0

    .line 112
    return-void

    .line 111
    .end local v0           #changes:I
    :catchall_14
    move-exception v1

    monitor-exit p0
    :try_end_16
    .catchall {:try_start_1 .. :try_end_16} :catchall_14

    throw v1
.end method
