.class Lcom/android/server/AttributeCache$Injector;
.super Ljava/lang/Object;
.source "AttributeCache.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/AttributeCache;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 40
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static getPackage(Ljava/util/WeakHashMap;Ljava/lang/String;)Lcom/android/server/AttributeCache$Package;
    .registers 4
    .parameter
    .parameter "packageName"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/WeakHashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/ref/WeakReference",
            "<",
            "Lcom/android/server/AttributeCache$Package;",
            ">;>;",
            "Ljava/lang/String;",
            ")",
            "Lcom/android/server/AttributeCache$Package;"
        }
    .end annotation

    .prologue
    .line 42
    .local p0, packages:Ljava/util/WeakHashMap;,"Ljava/util/WeakHashMap<Ljava/lang/String;Ljava/lang/ref/WeakReference<Lcom/android/server/AttributeCache$Package;>;>;"
    invoke-virtual {p0, p1}, Ljava/util/WeakHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/ref/WeakReference;

    .line 43
    .local v0, ref:Ljava/lang/ref/WeakReference;,"Ljava/lang/ref/WeakReference<Lcom/android/server/AttributeCache$Package;>;"
    if-eqz v0, :cond_f

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/server/AttributeCache$Package;

    .line 44
    :goto_e
    return-object v1

    :cond_f
    const/4 v1, 0x0

    goto :goto_e
.end method

.method static putPackage(Ljava/util/WeakHashMap;Ljava/lang/String;Lcom/android/server/AttributeCache$Package;)V
    .registers 4
    .parameter
    .parameter "packageName"
    .parameter "pkg"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/WeakHashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/ref/WeakReference",
            "<",
            "Lcom/android/server/AttributeCache$Package;",
            ">;>;",
            "Ljava/lang/String;",
            "Lcom/android/server/AttributeCache$Package;",
            ")V"
        }
    .end annotation

    .prologue
    .line 48
    .local p0, packages:Ljava/util/WeakHashMap;,"Ljava/util/WeakHashMap<Ljava/lang/String;Ljava/lang/ref/WeakReference<Lcom/android/server/AttributeCache$Package;>;>;"
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p2}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    invoke-virtual {p0, p1, v0}, Ljava/util/WeakHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 49
    return-void
.end method
