.class Lcom/android/server/MiuiLightsService$Light$6;
.super Landroid/os/Handler;
.source "MiuiLightsService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/MiuiLightsService$Light;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/android/server/MiuiLightsService$Light;


# direct methods
.method constructor <init>(Lcom/android/server/MiuiLightsService$Light;)V
    .registers 2
    .parameter

    .prologue
    .line 112
    iput-object p1, p0, Lcom/android/server/MiuiLightsService$Light$6;->this$1:Lcom/android/server/MiuiLightsService$Light;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .registers 3
    .parameter "msg"

    .prologue
    .line 115
    iget v0, p1, Landroid/os/Message;->what:I

    packed-switch v0, :pswitch_data_c

    .line 120
    :goto_5
    return-void

    .line 117
    :pswitch_6
    iget-object v0, p0, Lcom/android/server/MiuiLightsService$Light$6;->this$1:Lcom/android/server/MiuiLightsService$Light;

    invoke-virtual {v0}, Lcom/android/server/MiuiLightsService$Light;->turnOff()V

    goto :goto_5

    .line 115
    :pswitch_data_c
    .packed-switch 0x1
        :pswitch_6
    .end packed-switch
.end method
