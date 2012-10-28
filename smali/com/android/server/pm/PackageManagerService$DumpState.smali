.class Lcom/android/server/pm/PackageManagerService$DumpState;
.super Ljava/lang/Object;
.source "PackageManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/pm/PackageManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "DumpState"
.end annotation


# static fields
.field public static final DUMP_FEATURES:I = 0x2

.field public static final DUMP_LIBS:I = 0x1

.field public static final DUMP_MESSAGES:I = 0x40

.field public static final DUMP_PACKAGES:I = 0x10

.field public static final DUMP_PERMISSIONS:I = 0x8

.field public static final DUMP_PREFERRED:I = 0x200

.field public static final DUMP_PREFERRED_XML:I = 0x400

.field public static final DUMP_PROVIDERS:I = 0x80

.field public static final DUMP_RESOLVERS:I = 0x4

.field public static final DUMP_SHARED_USERS:I = 0x20

.field public static final DUMP_VERIFIERS:I = 0x100

.field public static final OPTION_SHOW_FILTERS:I = 0x1


# instance fields
.field private mOptions:I

.field private mSharedUser:Lcom/android/server/pm/SharedUserSetting;

.field private mTitlePrinted:Z

.field private mTypes:I


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 8534
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public getSharedUser()Lcom/android/server/pm/SharedUserSetting;
    .registers 2

    .prologue
    .line 8602
    iget-object v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mSharedUser:Lcom/android/server/pm/SharedUserSetting;

    return-object v0
.end method

.method public getTitlePrinted()Z
    .registers 2

    .prologue
    .line 8594
    iget-boolean v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTitlePrinted:Z

    return v0
.end method

.method public isDumping(I)Z
    .registers 4
    .parameter "type"

    .prologue
    const/4 v0, 0x1

    .line 8568
    iget v1, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTypes:I

    if-nez v1, :cond_a

    const/16 v1, 0x400

    if-eq p1, v1, :cond_a

    .line 8572
    :cond_9
    :goto_9
    return v0

    :cond_a
    iget v1, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTypes:I

    and-int/2addr v1, p1

    if-nez v1, :cond_9

    const/4 v0, 0x0

    goto :goto_9
.end method

.method public isOptionEnabled(I)Z
    .registers 3
    .parameter "option"

    .prologue
    .line 8580
    iget v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mOptions:I

    and-int/2addr v0, p1

    if-eqz v0, :cond_7

    const/4 v0, 0x1

    :goto_6
    return v0

    :cond_7
    const/4 v0, 0x0

    goto :goto_6
.end method

.method public onTitlePrinted()Z
    .registers 3

    .prologue
    .line 8588
    iget-boolean v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTitlePrinted:Z

    .line 8589
    .local v0, printed:Z
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTitlePrinted:Z

    .line 8590
    return v0
.end method

.method public setDump(I)V
    .registers 3
    .parameter "type"

    .prologue
    .line 8576
    iget v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTypes:I

    or-int/2addr v0, p1

    iput v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTypes:I

    .line 8577
    return-void
.end method

.method public setOptionEnabled(I)V
    .registers 3
    .parameter "option"

    .prologue
    .line 8584
    iget v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mOptions:I

    or-int/2addr v0, p1

    iput v0, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mOptions:I

    .line 8585
    return-void
.end method

.method public setSharedUser(Lcom/android/server/pm/SharedUserSetting;)V
    .registers 2
    .parameter "user"

    .prologue
    .line 8606
    iput-object p1, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mSharedUser:Lcom/android/server/pm/SharedUserSetting;

    .line 8607
    return-void
.end method

.method public setTitlePrinted(Z)V
    .registers 2
    .parameter "enabled"

    .prologue
    .line 8598
    iput-boolean p1, p0, Lcom/android/server/pm/PackageManagerService$DumpState;->mTitlePrinted:Z

    .line 8599
    return-void
.end method
