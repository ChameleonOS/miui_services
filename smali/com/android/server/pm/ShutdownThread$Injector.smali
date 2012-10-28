.class Lcom/android/server/pm/ShutdownThread$Injector;
.super Ljava/lang/Object;
.source "ShutdownThread.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/pm/ShutdownThread;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 75
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static createShutDownDialog(Landroid/content/Context;)V
    .registers 9
    .parameter "context"

    .prologue
    .line 86
    new-instance v2, Landroid/app/Dialog;

    const v6, 0x10300f1

    invoke-direct {v2, p0, v6}, Landroid/app/Dialog;-><init>(Landroid/content/Context;I)V

    .line 87
    .local v2, bootMsgDialog:Landroid/app/Dialog;
    invoke-virtual {v2}, Landroid/app/Dialog;->getContext()Landroid/content/Context;

    move-result-object v6

    invoke-static {v6}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v3

    .line 88
    .local v3, layoutInflater:Landroid/view/LayoutInflater;
    const v6, 0x603002e

    const/4 v7, 0x0

    invoke-virtual {v3, v6, v7}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v5

    .line 89
    .local v5, view:Landroid/view/View;
    const v6, 0x60b0021

    invoke-virtual {v5, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 90
    .local v4, msgText:Landroid/widget/TextView;
    const v6, 0x60b0022

    invoke-virtual {v5, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageView;

    .line 91
    .local v1, animationView:Landroid/widget/ImageView;
    invoke-static {}, Lcom/android/server/pm/ShutdownThread;->getReboot()Z

    move-result v6

    if-eqz v6, :cond_5d

    .line 92
    const v6, 0x60c0001

    invoke-virtual {v4, v6}, Landroid/widget/TextView;->setText(I)V

    .line 98
    :goto_36
    invoke-virtual {v2, v5}, Landroid/app/Dialog;->setContentView(Landroid/view/View;)V

    .line 99
    const/4 v6, 0x0

    invoke-virtual {v2, v6}, Landroid/app/Dialog;->setCancelable(Z)V

    .line 100
    invoke-virtual {v2}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v6

    const/16 v7, 0x7e5

    invoke-virtual {v6, v7}, Landroid/view/Window;->setType(I)V

    .line 101
    invoke-virtual {v2}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v6

    const v7, 0x6020034

    invoke-virtual {v6, v7}, Landroid/view/Window;->setBackgroundDrawableResource(I)V

    .line 102
    invoke-virtual {v2}, Landroid/app/Dialog;->show()V

    .line 104
    invoke-virtual {v1}, Landroid/widget/ImageView;->getDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    check-cast v0, Landroid/graphics/drawable/AnimationDrawable;

    .line 105
    .local v0, animationDrawable:Landroid/graphics/drawable/AnimationDrawable;
    invoke-virtual {v0}, Landroid/graphics/drawable/AnimationDrawable;->start()V

    .line 106
    return-void

    .line 95
    .end local v0           #animationDrawable:Landroid/graphics/drawable/AnimationDrawable;
    :cond_5d
    const v6, 0x60c01aa

    invoke-virtual {v4, v6}, Landroid/widget/TextView;->setText(I)V

    goto :goto_36
.end method

.method static getResourceId(I)I
    .registers 2
    .parameter "id"

    .prologue
    .line 77
    invoke-static {}, Lcom/android/server/pm/ShutdownThread;->getReboot()Z

    move-result v0

    if-eqz v0, :cond_8

    const/high16 p0, 0x60c

    .end local p0
    :cond_8
    return p0
.end method

.method static onDismiss(Landroid/content/DialogInterface;)V
    .registers 3
    .parameter "unused"

    .prologue
    .line 109
    invoke-static {}, Lcom/android/server/pm/ShutdownThread;->getIsStartedGuard()Ljava/lang/Object;

    move-result-object v1

    monitor-enter v1

    .line 110
    :try_start_5
    invoke-static {}, Lcom/android/server/pm/ShutdownThread;->getIsStarted()Z

    move-result v0

    if-nez v0, :cond_13

    .line 111
    const/4 v0, 0x0

    invoke-static {v0}, Lcom/android/server/pm/ShutdownThread;->setReboot(Z)V

    .line 112
    const/4 v0, 0x0

    invoke-static {v0}, Lcom/android/server/pm/ShutdownThread;->setRebootReason(Ljava/lang/String;)V

    .line 114
    :cond_13
    monitor-exit v1

    .line 115
    return-void

    .line 114
    :catchall_15
    move-exception v0

    monitor-exit v1
    :try_end_17
    .catchall {:try_start_5 .. :try_end_17} :catchall_15

    throw v0
.end method

.method static setDialogTitle(Landroid/app/Dialog;)V
    .registers 3
    .parameter "dialog"

    .prologue
    .line 81
    invoke-static {}, Lcom/android/server/pm/ShutdownThread;->getReboot()Z

    move-result v1

    if-eqz v1, :cond_d

    const v0, 0x60c018c

    .line 82
    .local v0, id:I
    :goto_9
    invoke-virtual {p0, v0}, Landroid/app/Dialog;->setTitle(I)V

    .line 83
    return-void

    .line 81
    .end local v0           #id:I
    :cond_d
    const v0, 0x60c0191

    goto :goto_9
.end method
