.class public Lcom/android/server/LightsService$Light;
.super Ljava/lang/Object;
.source "LightsService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_ACCESS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/LightsService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "Light"
.end annotation


# instance fields
.field private mColor:I

.field private mFlashing:Z

.field private mId:I

.field private mMode:I

.field private mOffMS:I

.field private mOnMS:I

.field final synthetic this$0:Lcom/android/server/LightsService;


# direct methods
.method private constructor <init>(Lcom/android/server/LightsService;I)V
    .registers 3
    .parameter
    .parameter "id"

    .prologue
    .line 72
    iput-object p1, p0, Lcom/android/server/LightsService$Light;->this$0:Lcom/android/server/LightsService;

    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 73
    iput p2, p0, Lcom/android/server/LightsService$Light;->mId:I

    .line 74
    return-void
.end method

.method constructor <init>(Lcom/android/server/LightsService;II)V
    .registers 4
    .parameter
    .parameter "id"
    .parameter "unused"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_METHOD:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 69
    invoke-direct {p0, p1, p2}, Lcom/android/server/LightsService$Light;-><init>(Lcom/android/server/LightsService;I)V

    .line 70
    return-void
.end method

.method synthetic constructor <init>(Lcom/android/server/LightsService;ILcom/android/server/LightsService$1;)V
    .registers 4
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 66
    invoke-direct {p0, p1, p2}, Lcom/android/server/LightsService$Light;-><init>(Lcom/android/server/LightsService;I)V

    return-void
.end method

.method static synthetic access$500(Lcom/android/server/LightsService$Light;)V
    .registers 1
    .parameter "x0"

    .prologue
    .line 66
    invoke-direct {p0}, Lcom/android/server/LightsService$Light;->stopFlashing()V

    return-void
.end method

.method private stopFlashing()V
    .registers 7

    .prologue
    .line 121
    monitor-enter p0

    .line 122
    :try_start_1
    iget v1, p0, Lcom/android/server/LightsService$Light;->mColor:I

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, p0

    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 123
    monitor-exit p0

    .line 124
    return-void

    .line 123
    :catchall_d
    move-exception v0

    monitor-exit p0
    :try_end_f
    .catchall {:try_start_1 .. :try_end_f} :catchall_d

    throw v0
.end method


# virtual methods
.method public pulse()V
    .registers 3

    .prologue
    .line 102
    const v0, 0xffffff

    const/4 v1, 0x7

    invoke-virtual {p0, v0, v1}, Lcom/android/server/LightsService$Light;->pulse(II)V

    .line 103
    return-void
.end method

.method public pulse(II)V
    .registers 9
    .parameter "color"
    .parameter "onMS"

    .prologue
    .line 106
    monitor-enter p0

    .line 107
    :try_start_1
    iget v0, p0, Lcom/android/server/LightsService$Light;->mColor:I

    if-nez v0, :cond_28

    iget-boolean v0, p0, Lcom/android/server/LightsService$Light;->mFlashing:Z

    if-nez v0, :cond_28

    .line 108
    const/4 v2, 0x2

    const/16 v4, 0x3e8

    const/4 v5, 0x0

    move-object v0, p0

    move v1, p1

    move v3, p2

    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 109
    iget-object v0, p0, Lcom/android/server/LightsService$Light;->this$0:Lcom/android/server/LightsService;

    #getter for: Lcom/android/server/LightsService;->mH:Landroid/os/Handler;
    invoke-static {v0}, Lcom/android/server/LightsService;->access$000(Lcom/android/server/LightsService;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/android/server/LightsService$Light;->this$0:Lcom/android/server/LightsService;

    #getter for: Lcom/android/server/LightsService;->mH:Landroid/os/Handler;
    invoke-static {v1}, Lcom/android/server/LightsService;->access$000(Lcom/android/server/LightsService;)Landroid/os/Handler;

    move-result-object v1

    const/4 v2, 0x1

    invoke-static {v1, v2, p0}, Landroid/os/Message;->obtain(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    int-to-long v2, p2

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 111
    :cond_28
    monitor-exit p0

    .line 112
    return-void

    .line 111
    :catchall_2a
    move-exception v0

    monitor-exit p0
    :try_end_2c
    .catchall {:try_start_1 .. :try_end_2c} :catchall_2a

    throw v0
.end method

.method public setBrightness(I)V
    .registers 3
    .parameter "brightness"

    .prologue
    .line 77
    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/android/server/LightsService$Light;->setBrightness(II)V

    .line 78
    return-void
.end method

.method public setBrightness(II)V
    .registers 9
    .parameter "brightness"
    .parameter "brightnessMode"

    .prologue
    .line 81
    monitor-enter p0

    .line 82
    and-int/lit16 v1, p1, 0xff

    .line 83
    .local v1, color:I
    const/high16 v0, -0x100

    shl-int/lit8 v2, v1, 0x10

    or-int/2addr v0, v2

    shl-int/lit8 v2, v1, 0x8

    or-int/2addr v0, v2

    or-int/2addr v1, v0

    .line 84
    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    move-object v0, p0

    move v5, p2

    :try_start_11
    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 85
    monitor-exit p0

    .line 86
    return-void

    .line 85
    :catchall_16
    move-exception v0

    monitor-exit p0
    :try_end_18
    .catchall {:try_start_11 .. :try_end_18} :catchall_16

    throw v0
.end method

.method public setColor(I)V
    .registers 8
    .parameter "color"

    .prologue
    .line 89
    monitor-enter p0

    .line 90
    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, p0

    move v1, p1

    :try_start_7
    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 91
    monitor-exit p0

    .line 92
    return-void

    .line 91
    :catchall_c
    move-exception v0

    monitor-exit p0
    :try_end_e
    .catchall {:try_start_7 .. :try_end_e} :catchall_c

    throw v0
.end method

.method public setFlashing(IIII)V
    .registers 11
    .parameter "color"
    .parameter "mode"
    .parameter "onMS"
    .parameter "offMS"

    .prologue
    .line 95
    monitor-enter p0

    .line 96
    const/4 v5, 0x0

    move-object v0, p0

    move v1, p1

    move v2, p2

    move v3, p3

    move v4, p4

    :try_start_7
    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 97
    monitor-exit p0

    .line 98
    return-void

    .line 97
    :catchall_c
    move-exception v0

    monitor-exit p0
    :try_end_e
    .catchall {:try_start_7 .. :try_end_e} :catchall_c

    throw v0
.end method

.method setLightLocked(IIIII)V
    .registers 13
    .parameter "color"
    .parameter "mode"
    .parameter "onMS"
    .parameter "offMS"
    .parameter "brightnessMode"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_ACCESS:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 128
    iget v0, p0, Lcom/android/server/LightsService$Light;->mColor:I

    if-ne p1, v0, :cond_10

    iget v0, p0, Lcom/android/server/LightsService$Light;->mMode:I

    if-ne p2, v0, :cond_10

    iget v0, p0, Lcom/android/server/LightsService$Light;->mOnMS:I

    if-ne p3, v0, :cond_10

    iget v0, p0, Lcom/android/server/LightsService$Light;->mOffMS:I

    if-eq p4, v0, :cond_28

    .line 131
    :cond_10
    iput p1, p0, Lcom/android/server/LightsService$Light;->mColor:I

    .line 132
    iput p2, p0, Lcom/android/server/LightsService$Light;->mMode:I

    .line 133
    iput p3, p0, Lcom/android/server/LightsService$Light;->mOnMS:I

    .line 134
    iput p4, p0, Lcom/android/server/LightsService$Light;->mOffMS:I

    .line 135
    iget-object v0, p0, Lcom/android/server/LightsService$Light;->this$0:Lcom/android/server/LightsService;

    #getter for: Lcom/android/server/LightsService;->mNativePointer:I
    invoke-static {v0}, Lcom/android/server/LightsService;->access$100(Lcom/android/server/LightsService;)I

    move-result v0

    iget v1, p0, Lcom/android/server/LightsService$Light;->mId:I

    move v2, p1

    move v3, p2

    move v4, p3

    move v5, p4

    move v6, p5

    #calls: Lcom/android/server/LightsService;->setLight_native(IIIIIII)V
    invoke-static/range {v0 .. v6}, Lcom/android/server/LightsService;->access$200(IIIIIII)V

    .line 137
    :cond_28
    return-void
.end method

.method public turnOff()V
    .registers 7

    .prologue
    .line 115
    monitor-enter p0

    .line 116
    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, p0

    :try_start_7
    invoke-virtual/range {v0 .. v5}, Lcom/android/server/LightsService$Light;->setLightLocked(IIIII)V

    .line 117
    monitor-exit p0

    .line 118
    return-void

    .line 117
    :catchall_c
    move-exception v0

    monitor-exit p0
    :try_end_e
    .catchall {:try_start_7 .. :try_end_e} :catchall_c

    throw v0
.end method
