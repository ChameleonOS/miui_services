.class public Lcom/android/server/net/MiuiNetworkPolicyManagerService;
.super Lcom/android/server/net/NetworkPolicyManagerService;
.source "MiuiNetworkPolicyManagerService.java"


# static fields
.field private static sLastNotificationTimeArr:[J


# instance fields
.field private mContext:Landroid/content/Context;

.field private mTelephony:Landroid/telephony/TelephonyManager;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 23
    const/4 v0, 0x4

    new-array v0, v0, [J

    fill-array-data v0, :array_a

    sput-object v0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->sLastNotificationTimeArr:[J

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

.method public constructor <init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;)V
    .registers 8
    .parameter "context"
    .parameter "activityManager"
    .parameter "powerManager"
    .parameter "networkStats"
    .parameter "networkManagement"

    .prologue
    .line 31
    invoke-direct/range {p0 .. p5}, Lcom/android/server/net/NetworkPolicyManagerService;-><init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;)V

    .line 32
    iput-object p1, p0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->mContext:Landroid/content/Context;

    .line 33
    iget-object v0, p0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->mContext:Landroid/content/Context;

    const-string v1, "phone"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    iput-object v0, p0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->mTelephony:Landroid/telephony/TelephonyManager;

    .line 34
    return-void
.end method

.method private isIntervalValid(I)Z
    .registers 6
    .parameter "type"

    .prologue
    .line 65
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sget-object v2, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->sLastNotificationTimeArr:[J

    aget-wide v2, v2, p1

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


# virtual methods
.method protected enqueueNotification(Landroid/net/NetworkPolicy;IJ)V
    .registers 8
    .parameter "policy"
    .parameter "type"
    .parameter "totalBytes"

    .prologue
    .line 39
    invoke-direct {p0, p2}, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->isIntervalValid(I)Z

    move-result v0

    if-eqz v0, :cond_11

    .line 40
    sget-object v0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->sLastNotificationTimeArr:[J

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    aput-wide v1, v0, p2

    .line 41
    invoke-super {p0, p1, p2, p3, p4}, Lcom/android/server/net/NetworkPolicyManagerService;->enqueueNotification(Landroid/net/NetworkPolicy;IJ)V

    .line 43
    :cond_11
    return-void
.end method

.method protected setNetworkTemplateEnabled(Landroid/net/NetworkTemplate;Z)V
    .registers 6
    .parameter "template"
    .parameter "enabled"

    .prologue
    .line 48
    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getMatchRule()I

    move-result v0

    packed-switch v0, :pswitch_data_36

    .line 61
    :cond_7
    :goto_7
    invoke-super {p0, p1, p2}, Lcom/android/server/net/NetworkPolicyManagerService;->setNetworkTemplateEnabled(Landroid/net/NetworkTemplate;Z)V

    .line 62
    :cond_a
    return-void

    .line 52
    :pswitch_b
    if-nez p2, :cond_14

    const/4 v0, 0x2

    invoke-direct {p0, v0}, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->isIntervalValid(I)Z

    move-result v0

    if-eqz v0, :cond_a

    .line 56
    :cond_14
    iget-object v0, p0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->mTelephony:Landroid/telephony/TelephonyManager;

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1}, Landroid/net/NetworkTemplate;->getSubscriberId()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/internal/util/Objects;->equal(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_7

    .line 57
    iget-object v0, p0, Lcom/android/server/net/MiuiNetworkPolicyManagerService;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "mobile_policy"

    if-eqz p2, :cond_33

    const/4 v0, 0x1

    :goto_2f
    invoke-static {v1, v2, v0}, Landroid/provider/Settings$Secure;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    goto :goto_7

    :cond_33
    const/4 v0, 0x0

    goto :goto_2f

    .line 48
    nop

    :pswitch_data_36
    .packed-switch 0x1
        :pswitch_b
        :pswitch_b
        :pswitch_b
    .end packed-switch
.end method
