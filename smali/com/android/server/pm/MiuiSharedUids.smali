.class public Lcom/android/server/pm/MiuiSharedUids;
.super Ljava/lang/Object;
.source "MiuiSharedUids.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 7
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static add(Lcom/android/server/pm/Settings;Z)V
    .registers 6
    .parameter "settings"
    .parameter "multipleUids"

    .prologue
    const/16 v1, 0x2710

    const/4 v3, 0x1

    .line 9
    const-string v2, "android.uid.backup"

    if-eqz p1, :cond_1f

    const/16 v0, 0x2648

    :goto_9
    invoke-virtual {p0, v2, v0, v3}, Lcom/android/server/pm/Settings;->addSharedUserLPw(Ljava/lang/String;II)Lcom/android/server/pm/SharedUserSetting;

    .line 11
    const-string v2, "android.uid.theme"

    if-eqz p1, :cond_21

    const/16 v0, 0x2649

    :goto_12
    invoke-virtual {p0, v2, v0, v3}, Lcom/android/server/pm/Settings;->addSharedUserLPw(Ljava/lang/String;II)Lcom/android/server/pm/SharedUserSetting;

    .line 13
    const-string v0, "android.uid.updater"

    if-eqz p1, :cond_1b

    const/16 v1, 0x264a

    :cond_1b
    invoke-virtual {p0, v0, v1, v3}, Lcom/android/server/pm/Settings;->addSharedUserLPw(Ljava/lang/String;II)Lcom/android/server/pm/SharedUserSetting;

    .line 15
    return-void

    :cond_1f
    move v0, v1

    .line 9
    goto :goto_9

    :cond_21
    move v0, v1

    .line 11
    goto :goto_12
.end method
