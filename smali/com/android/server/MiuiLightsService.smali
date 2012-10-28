.class public Lcom/android/server/MiuiLightsService;
.super Lcom/android/server/LightsService;
.source "MiuiLightsService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/server/MiuiLightsService$1;,
        Lcom/android/server/MiuiLightsService$Light;
    }
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mResolver:Landroid/content/ContentResolver;


# direct methods
.method constructor <init>(Landroid/content/Context;)V
    .registers 6
    .parameter "context"

    .prologue
    const/4 v3, 0x0

    const/4 v2, 0x4

    const/4 v1, 0x2

    .line 16
    invoke-direct {p0, p1}, Lcom/android/server/LightsService;-><init>(Landroid/content/Context;)V

    .line 17
    iput-object p1, p0, Lcom/android/server/MiuiLightsService;->mContext:Landroid/content/Context;

    .line 18
    invoke-virtual {p1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    iput-object v0, p0, Lcom/android/server/MiuiLightsService;->mResolver:Landroid/content/ContentResolver;

    .line 19
    new-instance v0, Lcom/android/server/MiuiLightsService$Light;

    invoke-direct {v0, p0, v1, v3}, Lcom/android/server/MiuiLightsService$Light;-><init>(Lcom/android/server/MiuiLightsService;ILcom/android/server/MiuiLightsService$1;)V

    invoke-virtual {p0, v1, v0}, Lcom/android/server/MiuiLightsService;->setLight(ILcom/android/server/LightsService$Light;)V

    .line 20
    new-instance v0, Lcom/android/server/MiuiLightsService$Light;

    invoke-direct {v0, p0, v2, v3}, Lcom/android/server/MiuiLightsService$Light;-><init>(Lcom/android/server/MiuiLightsService;ILcom/android/server/MiuiLightsService$1;)V

    invoke-virtual {p0, v2, v0}, Lcom/android/server/MiuiLightsService;->setLight(ILcom/android/server/LightsService$Light;)V

    .line 21
    return-void
.end method

.method static synthetic access$100(Lcom/android/server/MiuiLightsService;)Landroid/content/ContentResolver;
    .registers 2
    .parameter "x0"

    .prologue
    .line 11
    iget-object v0, p0, Lcom/android/server/MiuiLightsService;->mResolver:Landroid/content/ContentResolver;

    return-object v0
.end method

.method static synthetic access$1400(Lcom/android/server/MiuiLightsService;)Landroid/content/Context;
    .registers 2
    .parameter "x0"

    .prologue
    .line 11
    iget-object v0, p0, Lcom/android/server/MiuiLightsService;->mContext:Landroid/content/Context;

    return-object v0
.end method
