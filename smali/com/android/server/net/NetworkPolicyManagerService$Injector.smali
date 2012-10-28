.class Lcom/android/server/net/NetworkPolicyManagerService$Injector;
.super Ljava/lang/Object;
.source "NetworkPolicyManagerService.java"


# annotations
.annotation build Landroid/annotation/MiuiHook;
    value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->NEW_CLASS:Landroid/annotation/MiuiHook$MiuiHookType;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/net/NetworkPolicyManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "Injector"
.end annotation


# static fields
.field private static sLastNotificationTimeArr:[J


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 178
    const/4 v0, 0x4

    new-array v0, v0, [J

    fill-array-data v0, :array_a

    sput-object v0, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->sLastNotificationTimeArr:[J

    return-void

    nop

    :array_a
    .array-data 0x8
        0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
        0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t 0x0t
    .end array-data
.end method

.method constructor <init>()V
    .registers 1

    .prologue
    .line 177
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static adjustMobileDataUsage(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkTemplate;JJ)J
    .registers 15
    .parameter "service"
    .parameter "template"
    .parameter "start"
    .parameter "end"

    .prologue
    const-wide/16 v5, 0x0

    .line 181
    invoke-virtual {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->getContext()Landroid/content/Context;

    move-result-object v4

    .line 182
    .local v4, context:Landroid/content/Context;
    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v7

    const/4 v8, 0x1

    if-ne v7, v8, :cond_2d

    .line 183
    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v7

    const-string v8, "data_usage_adjusting_time"

    invoke-static {v7, v8, v5, v6}, Landroid/provider/Settings$Secure;->getLong(Landroid/content/ContentResolver;Ljava/lang/String;J)J

    move-result-wide v0

    .line 185
    .local v0, adjustingTime:J
    cmp-long v7, v0, p2

    if-ltz v7, :cond_2d

    cmp-long v7, v0, p4

    if-gtz v7, :cond_2d

    .line 186
    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v7

    const-string v8, "data_usage_adjustment"

    invoke-static {v7, v8, v5, v6}, Landroid/provider/Settings$Secure;->getLong(Landroid/content/ContentResolver;Ljava/lang/String;J)J

    move-result-wide v2

    .line 188
    .local v2, adjustment:J
    invoke-static {v5, v6, v2, v3}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v5

    .line 192
    .end local v0           #adjustingTime:J
    .end local v2           #adjustment:J
    :cond_2d
    return-wide v5
.end method

.method static isIntervalValid(I)Z
    .registers 5
    .parameter "type"

    .prologue
    .line 196
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sget-object v2, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->sLastNotificationTimeArr:[J

    aget-wide v2, v2, p0

    sub-long/2addr v0, v2

    const-wide/32 v2, 0x5265c00

    cmp-long v0, v0, v2

    if-lez v0, :cond_12

    const/4 v0, 0x1

    :goto_11
    return v0

    :cond_12
    const/4 v0, 0x0

    goto :goto_11
.end method

.method static setInterval(I)V
    .registers 4
    .parameter "type"

    .prologue
    .line 217
    sget-object v0, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->sLastNotificationTimeArr:[J

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    aput-wide v1, v0, p0

    .line 218
    return-void
.end method

.method static setNetworkTemplateEnabled(Lcom/android/server/net/NetworkPolicyManagerService;Landroid/net/NetworkTemplate;Z)V
    .registers 7
    .parameter "service"
    .parameter "template"
    .parameter "enabled"

    .prologue
    .line 200
    invoke-virtual {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "phone"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    .line 201
    .local v0, telephony:Landroid/telephony/TelephonyManager;
    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v1

    packed-switch v1, :pswitch_data_3e

    .line 214
    :cond_13
    :goto_13
    return-void

    .line 205
    :pswitch_14
    if-nez p2, :cond_1d

    const/4 v1, 0x2

    invoke-static {v1}, Lcom/android/server/net/NetworkPolicyManagerService$Injector;->isIntervalValid(I)Z

    move-result v1

    if-eqz v1, :cond_13

    .line 209
    :cond_1d
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getSubscriberId()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/internal/util/Objects;->equal(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_13

    .line 210
    invoke-virtual {p0}, Lcom/android/server/net/NetworkPolicyManagerService;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v2

    const-string v3, "mobile_policy"

    if-eqz p2, :cond_3c

    const/4 v1, 0x1

    :goto_38
    invoke-static {v2, v3, v1}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    goto :goto_13

    :cond_3c
    const/4 v1, 0x0

    goto :goto_38

    .line 201
    :pswitch_data_3e
    .packed-switch 0x1
        :pswitch_14
        :pswitch_14
        :pswitch_14
    .end packed-switch
.end method
