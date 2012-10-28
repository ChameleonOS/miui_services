.class Lcom/android/server/NotificationManagerService$Injector;
.super Ljava/lang/Object;
.source "NotificationManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/NotificationManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 101
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static updateNotificationLight(Lcom/android/server/NotificationManagerService;)V
    .registers 10
    .parameter "service"

    .prologue
    .line 103
    iget-object v1, p0, Lcom/android/server/NotificationManagerService;->mContext:Landroid/content/Context;

    .line 104
    .local v1, context:Landroid/content/Context;
    invoke-virtual {p0}, Lcom/android/server/NotificationManagerService;->getDefaultNotificationColor()I

    move-result v3

    .line 105
    .local v3, defaultNotificationColor:I
    invoke-virtual {p0}, Lcom/android/server/NotificationManagerService;->getLedNotification()Lcom/android/server/NotificationManagerService$NotificationRecord;

    move-result-object v5

    .line 106
    .local v5, ledNotification:Lcom/android/server/NotificationManagerService$NotificationRecord;
    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v7

    const-string v8, "breathing_light_color"

    invoke-static {v7, v8, v3}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    .line 109
    .local v0, color:I
    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x608000a

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v2

    .line 111
    .local v2, defaultFreq:I
    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v7

    const-string v8, "breathing_light_freq"

    invoke-static {v7, v8, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v4

    .line 114
    .local v4, freq:I
    invoke-static {v4}, Lmiui/app/ExtraNotification;->getLedPwmOffOn(I)[I

    move-result-object v6

    .line 115
    .local v6, offOn:[I
    iget-object v7, v5, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    iput v0, v7, Landroid/app/Notification;->ledARGB:I

    .line 116
    iget-object v7, v5, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    const/4 v8, 0x1

    aget v8, v6, v8

    iput v8, v7, Landroid/app/Notification;->ledOnMS:I

    .line 117
    iget-object v7, v5, Lcom/android/server/NotificationManagerService$NotificationRecord;->notification:Landroid/app/Notification;

    const/4 v8, 0x0

    aget v8, v6, v8

    iput v8, v7, Landroid/app/Notification;->ledOffMS:I

    .line 118
    return-void
.end method
