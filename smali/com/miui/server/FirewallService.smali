.class public Lcom/miui/server/FirewallService;
.super Lmiui/net/IFirewall$Stub;
.source "FirewallService.java"


# static fields
.field private static final ADD_CHAIN_FOR_REJECT_CMD:Ljava/lang/String; = null

.field private static final AWK_CMD:Ljava/lang/String; = null

.field private static final CLEAR_ALL_MIUI_CHAIN_CMD:Ljava/lang/String; = null

.field private static final DEBUG:Z = false

.field private static final GREP_CMD:Ljava/lang/String; = null

.field private static final INSERT_ACCEPT_RULE_TO_CHAIN_CMD:Ljava/lang/String; = null

.field private static final IPTABLES_CMD:Ljava/lang/String; = null

.field private static final LOG_TAG:Ljava/lang/String; = "FirewallService"

.field private static final MIUI_CHAIN_PREFIX:Ljava/lang/String; = "miui_"

.field private static final REMOVE_CHAIN_CMD:Ljava/lang/String;

.field private static final REMOVE_RULE_OF_CHAIN_CMD:Ljava/lang/String;


# instance fields
.field private final mAccessControlPassPackages:Ljava/util/HashSet;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mAlarmBootCompleted:Z

.field private mContext:Landroid/content/Context;

.field private mCurrentMmsIfname:Ljava/lang/String;

.field private mIfnames:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mLastUsingMmsUids:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Integer;",
            "Ljava/util/HashSet",
            "<",
            "Ljava/lang/Integer;",
            ">;>;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 2

    .prologue
    .line 35
    invoke-static {}, Lcom/miui/server/FirewallService;->getIptablesCommand()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    .line 36
    invoke-static {}, Lcom/miui/server/FirewallService;->getAwkCommand()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    .line 37
    invoke-static {}, Lcom/miui/server/FirewallService;->getGrepCommand()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->GREP_CMD:Ljava/lang/String;

    .line 47
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "for chain in `"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -L | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->GREP_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \"^Chain "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "miui_"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\" | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \'{print $2}\'`; do "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -D OUTPUT `"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -S OUTPUT | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -v chain=${chain} \'$6==chain {print NR-2}\'`; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -F $chain; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -X $chain; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "done;"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->CLEAR_ALL_MIUI_CHAIN_CMD:Ljava/lang/String;

    .line 59
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -D OUTPUT `"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -S OUTPUT | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \'$6==\"%0%\" {print NR-1}\'`; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -F %0%; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -X %0%; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->REMOVE_CHAIN_CMD:Ljava/lang/String;

    .line 68
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -N %0%; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -A %0% -j REJECT; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -A OUTPUT -o %1% -j %0%; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->ADD_CHAIN_FOR_REJECT_CMD:Ljava/lang/String;

    .line 75
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -I %0% -m owner --uid-owner %1% -j ACCEPT; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->INSERT_ACCEPT_RULE_TO_CHAIN_CMD:Ljava/lang/String;

    .line 81
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -D %0% `"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -S %0% | "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \'$6==\"%1%\" {print NR-1}\'`; "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/miui/server/FirewallService;->REMOVE_RULE_OF_CHAIN_CMD:Ljava/lang/String;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .registers 3
    .parameter "context"

    .prologue
    .line 176
    invoke-direct {p0}, Lmiui/net/IFirewall$Stub;-><init>()V

    .line 93
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    .line 99
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/miui/server/FirewallService;->mIfnames:Ljava/util/HashMap;

    .line 104
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    .line 109
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    .line 177
    iput-object p1, p0, Lcom/miui/server/FirewallService;->mContext:Landroid/content/Context;

    .line 178
    return-void
.end method

.method private addQuoteMark(Ljava/lang/String;)Ljava/lang/String;
    .registers 8
    .parameter "commands"

    .prologue
    const/16 v5, 0x22

    .line 416
    const-string v4, "\""

    invoke-virtual {p1, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_13

    const-string v4, "\""

    invoke-virtual {p1, v4}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_13

    .line 439
    .end local p1
    :goto_12
    return-object p1

    .line 420
    .restart local p1
    :cond_13
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 421
    .local v0, builder:Ljava/lang/StringBuilder;
    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 422
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v3

    .local v3, length:I
    const/4 v2, 0x0

    .local v2, i:I
    :goto_20
    if-ge v2, v3, :cond_41

    .line 423
    invoke-virtual {p1, v2}, Ljava/lang/String;->charAt(I)C

    move-result v1

    .line 424
    .local v1, c:C
    sparse-switch v1, :sswitch_data_4a

    .line 435
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 422
    :goto_2c
    add-int/lit8 v2, v2, 0x1

    goto :goto_20

    .line 426
    :sswitch_2f
    const-string v4, "\\\""

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_2c

    .line 429
    :sswitch_35
    const-string v4, "\\\\"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_2c

    .line 432
    :sswitch_3b
    const-string v4, "\\$"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_2c

    .line 438
    .end local v1           #c:C
    :cond_41
    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 439
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    goto :goto_12

    .line 424
    nop

    :sswitch_data_4a
    .sparse-switch
        0x22 -> :sswitch_2f
        0x24 -> :sswitch_3b
        0x5c -> :sswitch_35
    .end sparse-switch
.end method

.method private addWhiteListChain(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Iterable;)V
    .registers 10
    .parameter "chain"
    .parameter "ifname"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Ljava/lang/Iterable",
            "<",
            "Ljava/lang/Integer;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 345
    .local p3, uids:Ljava/lang/Iterable;,"Ljava/lang/Iterable<Ljava/lang/Integer;>;"
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 347
    .local v0, builder:Ljava/lang/StringBuilder;
    sget-object v3, Lcom/miui/server/FirewallService;->REMOVE_CHAIN_CMD:Ljava/lang/String;

    const-string v4, "%0%"

    invoke-virtual {v3, v4, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 349
    sget-object v3, Lcom/miui/server/FirewallService;->ADD_CHAIN_FOR_REJECT_CMD:Ljava/lang/String;

    const-string v4, "%0%"

    invoke-virtual {v3, v4, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v4, "%1%"

    invoke-virtual {v3, v4, p2}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 350
    if-eqz p3, :cond_4d

    .line 352
    invoke-interface {p3}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_27
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_4d

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v2

    .line 353
    .local v2, uid:I
    sget-object v3, Lcom/miui/server/FirewallService;->INSERT_ACCEPT_RULE_TO_CHAIN_CMD:Ljava/lang/String;

    const-string v4, "%0%"

    invoke-virtual {v3, v4, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v4, "%1%"

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v3, v4, v5}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_27

    .line 357
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v2           #uid:I
    :cond_4d
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v3}, Lcom/miui/server/FirewallService;->runCommands(Ljava/lang/String;)V

    .line 358
    return-void
.end method

.method private clearChains()V
    .registers 2

    .prologue
    .line 386
    sget-object v0, Lcom/miui/server/FirewallService;->CLEAR_ALL_MIUI_CHAIN_CMD:Ljava/lang/String;

    invoke-direct {p0, v0}, Lcom/miui/server/FirewallService;->runCommands(Ljava/lang/String;)V

    .line 387
    return-void
.end method

.method private static getAwkCommand()Ljava/lang/String;
    .registers 1

    .prologue
    .line 155
    const-string v0, "awk"

    invoke-static {v0}, Lcom/miui/server/FirewallService;->hasCommand(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_b

    const-string v0, "awk"

    :goto_a
    return-object v0

    :cond_b
    const-string v0, "busybox"

    invoke-static {v0}, Lcom/miui/server/FirewallService;->hasCommand(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_16

    const-string v0, "busybox awk"

    goto :goto_a

    :cond_16
    const/4 v0, 0x0

    goto :goto_a
.end method

.method private static getGrepCommand()Ljava/lang/String;
    .registers 1

    .prologue
    .line 144
    const-string v0, "grep"

    invoke-static {v0}, Lcom/miui/server/FirewallService;->hasCommand(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_b

    const-string v0, "grep"

    :goto_a
    return-object v0

    :cond_b
    const-string v0, "busybox"

    invoke-static {v0}, Lcom/miui/server/FirewallService;->hasCommand(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_16

    const-string v0, "busybox grep"

    goto :goto_a

    :cond_16
    const/4 v0, 0x0

    goto :goto_a
.end method

.method private static getIptablesCommand()Ljava/lang/String;
    .registers 1

    .prologue
    .line 135
    const-string v0, "iptables"

    invoke-static {v0}, Lcom/miui/server/FirewallService;->hasCommand(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_b

    const-string v0, "iptables"

    :goto_a
    return-object v0

    :cond_b
    const/4 v0, 0x0

    goto :goto_a
.end method

.method private static hasCommand(Ljava/lang/String;)Z
    .registers 3
    .parameter "cmd"

    .prologue
    .line 167
    new-instance v0, Ljava/io/File;

    const-string v1, "/system/xbin/"

    invoke-direct {v0, v1, p0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    if-nez v0, :cond_1a

    new-instance v0, Ljava/io/File;

    const-string v1, "/system/bin/"

    invoke-direct {v0, v1, p0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_1c

    :cond_1a
    const/4 v0, 0x1

    :goto_1b
    return v0

    :cond_1c
    const/4 v0, 0x0

    goto :goto_1b
.end method

.method private insertWhiteListRule(Ljava/lang/String;I)V
    .registers 6
    .parameter "chain"
    .parameter "uid"

    .prologue
    .line 367
    sget-object v0, Lcom/miui/server/FirewallService;->INSERT_ACCEPT_RULE_TO_CHAIN_CMD:Ljava/lang/String;

    const-string v1, "%0%"

    invoke-virtual {v0, v1, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "%1%"

    invoke-static {p2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/miui/server/FirewallService;->runCommands(Ljava/lang/String;)V

    .line 369
    return-void
.end method

.method private removeChain(Ljava/lang/String;)V
    .registers 4
    .parameter "chain"

    .prologue
    .line 395
    sget-object v0, Lcom/miui/server/FirewallService;->REMOVE_CHAIN_CMD:Ljava/lang/String;

    const-string v1, "%0%"

    invoke-virtual {v0, v1, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/miui/server/FirewallService;->runCommands(Ljava/lang/String;)V

    .line 396
    return-void
.end method

.method private removeRule(Ljava/lang/String;I)V
    .registers 6
    .parameter "chain"
    .parameter "uid"

    .prologue
    .line 378
    sget-object v0, Lcom/miui/server/FirewallService;->REMOVE_RULE_OF_CHAIN_CMD:Ljava/lang/String;

    const-string v1, "%0%"

    invoke-virtual {v0, v1, p1}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "%1%"

    invoke-static {p2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/miui/server/FirewallService;->runCommands(Ljava/lang/String;)V

    .line 380
    return-void
.end method

.method private runCommands(Ljava/lang/String;)V
    .registers 5
    .parameter "commands"

    .prologue
    .line 406
    const-string v0, "root"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "sh -c "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-direct {p0, p1}, Lcom/miui/server/FirewallService;->addQuoteMark(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v2}, Lmiui/util/CommandLineUtils;->run(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Z

    .line 407
    return-void
.end method

.method public static setupService(Landroid/content/Context;)V
    .registers 5
    .parameter "context"

    .prologue
    .line 119
    sget-object v1, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    if-eqz v1, :cond_1e

    sget-object v1, Lcom/miui/server/FirewallService;->GREP_CMD:Ljava/lang/String;

    if-eqz v1, :cond_1e

    sget-object v1, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    if-eqz v1, :cond_1e

    .line 120
    new-instance v0, Lcom/miui/server/FirewallService;

    invoke-direct {v0, p0}, Lcom/miui/server/FirewallService;-><init>(Landroid/content/Context;)V

    .line 121
    .local v0, firewall:Lcom/miui/server/FirewallService;
    invoke-direct {v0}, Lcom/miui/server/FirewallService;->clearChains()V

    .line 122
    const-string v1, "miui.Firewall"

    invoke-virtual {v0}, Lcom/miui/server/FirewallService;->asBinder()Landroid/os/IBinder;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 127
    .end local v0           #firewall:Lcom/miui/server/FirewallService;
    :goto_1d
    return-void

    .line 124
    :cond_1e
    const-string v1, "FirewallService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "failed to setup service due to iptables="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Lcom/miui/server/FirewallService;->IPTABLES_CMD:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ",grep="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Lcom/miui/server/FirewallService;->GREP_CMD:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ",awk="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Lcom/miui/server/FirewallService;->AWK_CMD:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1d
.end method


# virtual methods
.method public addAccessControlPass(Ljava/lang/String;)V
    .registers 4
    .parameter "packageName"

    .prologue
    .line 312
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    monitor-enter v1

    .line 313
    :try_start_3
    iget-object v0, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 314
    monitor-exit v1

    .line 315
    return-void

    .line 314
    :catchall_a
    move-exception v0

    monitor-exit v1
    :try_end_c
    .catchall {:try_start_3 .. :try_end_c} :catchall_a

    throw v0
.end method

.method public checkAccessControlPass(Ljava/lang/String;)Z
    .registers 4
    .parameter "packageName"

    .prologue
    .line 330
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    monitor-enter v1

    .line 331
    :try_start_3
    iget-object v0, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->contains(Ljava/lang/Object;)Z

    move-result v0

    monitor-exit v1

    return v0

    .line 332
    :catchall_b
    move-exception v0

    monitor-exit v1
    :try_end_d
    .catchall {:try_start_3 .. :try_end_d} :catchall_b

    throw v0
.end method

.method public getAlarmBootCompleted()Z
    .registers 2

    .prologue
    .line 307
    iget-boolean v0, p0, Lcom/miui/server/FirewallService;->mAlarmBootCompleted:Z

    return v0
.end method

.method public onDataConnected(ILjava/lang/String;Ljava/lang/String;)V
    .registers 8
    .parameter "networkType"
    .parameter "key"
    .parameter "ifname"

    .prologue
    .line 258
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mIfnames:Ljava/util/HashMap;

    invoke-virtual {v1, p2, p3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 259
    invoke-static {p1}, Landroid/net/ConnectivityManager;->isNetworkTypeMobile(I)Z

    move-result v1

    if-eqz v1, :cond_4a

    iget-object v1, p0, Lcom/miui/server/FirewallService;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "mobile_data"

    const/4 v3, 0x1

    invoke-static {v1, v2, v3}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v1

    if-nez v1, :cond_4a

    .line 264
    invoke-static {p2}, Lmiui/net/FirewallManager;->decodeApnSetting(Ljava/lang/String;)Lcom/android/internal/telephony/ApnSetting;

    move-result-object v0

    .line 265
    .local v0, apn:Lcom/android/internal/telephony/ApnSetting;
    if-eqz v0, :cond_4a

    const-string v1, "mms"

    invoke-virtual {v0, v1}, Lcom/android/internal/telephony/ApnSetting;->canHandleType(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_4a

    .line 267
    iput-object p3, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    .line 268
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "miui_"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    iget-object v3, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-virtual {v3}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v3

    invoke-direct {p0, v1, v2, v3}, Lcom/miui/server/FirewallService;->addWhiteListChain(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Iterable;)V

    .line 272
    .end local v0           #apn:Lcom/android/internal/telephony/ApnSetting;
    :cond_4a
    return-void
.end method

.method public onDataDisconnected(ILjava/lang/String;)V
    .registers 6
    .parameter "networkType"
    .parameter "key"

    .prologue
    .line 285
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mIfnames:Ljava/util/HashMap;

    invoke-virtual {v1, p2}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 288
    .local v0, ifname:Ljava/lang/String;
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_34

    .line 290
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "miui_"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v1}, Lcom/miui/server/FirewallService;->removeChain(Ljava/lang/String;)V

    .line 291
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_34

    .line 294
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    .line 295
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-virtual {v1}, Ljava/util/HashMap;->clear()V

    .line 298
    :cond_34
    return-void
.end method

.method public onStartUsingNetworkFeature(III)V
    .registers 8
    .parameter "uid"
    .parameter "pid"
    .parameter "networkType"

    .prologue
    .line 192
    const/4 v2, 0x2

    if-ne p3, v2, :cond_4a

    .line 195
    iget-object v2, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/HashSet;

    .line 196
    .local v1, pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    const/4 v0, 0x0

    .line 197
    .local v0, newUser:Z
    if-nez v1, :cond_21

    .line 198
    const/4 v0, 0x1

    .line 199
    new-instance v1, Ljava/util/HashSet;

    .end local v1           #pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    invoke-direct {v1}, Ljava/util/HashSet;-><init>()V

    .line 200
    .restart local v1       #pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    iget-object v2, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v2, v3, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 202
    :cond_21
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    .line 206
    if-eqz v0, :cond_4a

    iget-object v2, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_4a

    .line 207
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "miui_"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v2, p1}, Lcom/miui/server/FirewallService;->insertWhiteListRule(Ljava/lang/String;I)V

    .line 210
    .end local v0           #newUser:Z
    .end local v1           #pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    :cond_4a
    return-void
.end method

.method public onStopUsingNetworkFeature(III)V
    .registers 7
    .parameter "uid"
    .parameter "pid"
    .parameter "networkType"

    .prologue
    .line 224
    const/4 v1, 0x2

    if-ne p3, v1, :cond_4a

    .line 226
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/HashSet;

    .line 227
    .local v0, pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    if-eqz v0, :cond_28

    .line 228
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    .line 229
    invoke-virtual {v0}, Ljava/util/HashSet;->size()I

    move-result v1

    if-nez v1, :cond_28

    .line 230
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mLastUsingMmsUids:Ljava/util/HashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 231
    const/4 v0, 0x0

    .line 238
    :cond_28
    if-nez v0, :cond_4a

    iget-object v1, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_4a

    .line 239
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "miui_"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/miui/server/FirewallService;->mCurrentMmsIfname:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v1, p1}, Lcom/miui/server/FirewallService;->removeRule(Ljava/lang/String;I)V

    .line 242
    .end local v0           #pids:Ljava/util/HashSet;,"Ljava/util/HashSet<Ljava/lang/Integer;>;"
    :cond_4a
    return-void
.end method

.method public removeAccessControlPass(Ljava/lang/String;)V
    .registers 4
    .parameter "packageName"

    .prologue
    .line 319
    iget-object v1, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    monitor-enter v1

    .line 320
    :try_start_3
    const-string v0, "*"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_12

    .line 321
    iget-object v0, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    invoke-virtual {v0}, Ljava/util/HashSet;->clear()V

    .line 325
    :goto_10
    monitor-exit v1

    .line 326
    return-void

    .line 323
    :cond_12
    iget-object v0, p0, Lcom/miui/server/FirewallService;->mAccessControlPassPackages:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    goto :goto_10

    .line 325
    :catchall_18
    move-exception v0

    monitor-exit v1
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_18

    throw v0
.end method

.method public setAlarmBootCompleted()V
    .registers 2

    .prologue
    .line 302
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/miui/server/FirewallService;->mAlarmBootCompleted:Z

    .line 303
    return-void
.end method
