.class Lcom/android/server/am/BaseErrorDialog;
.super Landroid/app/AlertDialog;
.source "BaseErrorDialog.java"


# instance fields
.field private mConsuming:Z

.field private mHandler:Landroid/os/Handler;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 5
    .parameter "context"
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    const/high16 v2, 0x2

    .line 34
    const v0, 0x60d0020

    invoke-direct {p0, p1, v0}, Landroid/app/AlertDialog;-><init>(Landroid/content/Context;I)V

    .line 73
    new-instance v0, Lcom/android/server/am/BaseErrorDialog$1;

    invoke-direct {v0, p0}, Lcom/android/server/am/BaseErrorDialog$1;-><init>(Lcom/android/server/am/BaseErrorDialog;)V

    iput-object v0, p0, Lcom/android/server/am/BaseErrorDialog;->mHandler:Landroid/os/Handler;

    .line 82
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/server/am/BaseErrorDialog;->mConsuming:Z

    .line 36
    invoke-virtual {p0}, Lcom/android/server/am/BaseErrorDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    const/16 v1, 0x7d3

    invoke-virtual {v0, v1}, Landroid/view/Window;->setType(I)V

    .line 37
    invoke-virtual {p0}, Lcom/android/server/am/BaseErrorDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {v0, v2, v2}, Landroid/view/Window;->setFlags(II)V

    .line 39
    invoke-virtual {p0}, Lcom/android/server/am/BaseErrorDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    const-string v1, "Error Dialog"

    invoke-virtual {v0, v1}, Landroid/view/Window;->setTitle(Ljava/lang/CharSequence;)V

    .line 40
    const v0, 0x1010355

    invoke-virtual {p0, v0}, Lcom/android/server/am/BaseErrorDialog;->setIconAttribute(I)V

    .line 41
    return-void
.end method

.method static synthetic access$002(Lcom/android/server/am/BaseErrorDialog;Z)Z
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 31
    iput-boolean p1, p0, Lcom/android/server/am/BaseErrorDialog;->mConsuming:Z

    return p1
.end method

.method static synthetic access$100(Lcom/android/server/am/BaseErrorDialog;Z)V
    .registers 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 31
    invoke-direct {p0, p1}, Lcom/android/server/am/BaseErrorDialog;->setEnabled(Z)V

    return-void
.end method

.method private setEnabled(Z)V
    .registers 4
    .parameter "enabled"

    .prologue
    .line 59
    const v1, 0x1020019

    invoke-virtual {p0, v1}, Lcom/android/server/am/BaseErrorDialog;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 60
    .local v0, b:Landroid/widget/Button;
    if-eqz v0, :cond_e

    .line 61
    invoke-virtual {v0, p1}, Landroid/widget/Button;->setEnabled(Z)V

    .line 63
    :cond_e
    const v1, 0x102001a

    invoke-virtual {p0, v1}, Lcom/android/server/am/BaseErrorDialog;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #b:Landroid/widget/Button;
    check-cast v0, Landroid/widget/Button;

    .line 64
    .restart local v0       #b:Landroid/widget/Button;
    if-eqz v0, :cond_1c

    .line 65
    invoke-virtual {v0, p1}, Landroid/widget/Button;->setEnabled(Z)V

    .line 67
    :cond_1c
    const v1, 0x102001b

    invoke-virtual {p0, v1}, Lcom/android/server/am/BaseErrorDialog;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #b:Landroid/widget/Button;
    check-cast v0, Landroid/widget/Button;

    .line 68
    .restart local v0       #b:Landroid/widget/Button;
    if-eqz v0, :cond_2a

    .line 69
    invoke-virtual {v0, p1}, Landroid/widget/Button;->setEnabled(Z)V

    .line 71
    :cond_2a
    return-void
.end method


# virtual methods
.method public dispatchKeyEvent(Landroid/view/KeyEvent;)Z
    .registers 3
    .parameter "event"

    .prologue
    .line 50
    iget-boolean v0, p0, Lcom/android/server/am/BaseErrorDialog;->mConsuming:Z

    if-eqz v0, :cond_6

    .line 52
    const/4 v0, 0x1

    .line 55
    :goto_5
    return v0

    :cond_6
    invoke-super {p0, p1}, Landroid/app/AlertDialog;->dispatchKeyEvent(Landroid/view/KeyEvent;)Z

    move-result v0

    goto :goto_5
.end method

.method public onStart()V
    .registers 5

    .prologue
    const/4 v2, 0x0

    .line 44
    invoke-super {p0}, Landroid/app/AlertDialog;->onStart()V

    .line 45
    invoke-direct {p0, v2}, Lcom/android/server/am/BaseErrorDialog;->setEnabled(Z)V

    .line 46
    iget-object v0, p0, Lcom/android/server/am/BaseErrorDialog;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/server/am/BaseErrorDialog;->mHandler:Landroid/os/Handler;

    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    const-wide/16 v2, 0x3e8

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 47
    return-void
.end method
