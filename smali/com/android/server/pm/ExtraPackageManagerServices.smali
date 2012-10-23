.class Lcom/android/server/pm/ExtraPackageManagerServices;
.super Ljava/lang/Object;
.source "ExtraPackageManagerServices.java"


# static fields
.field private static final INSTALL_DIR:Ljava/lang/String; = "/data/app/"

.field private static final PREINSTALL_DIR:Ljava/lang/String; = "/data/media/preinstall_apps/"

.field private static final PREINSTALL_HISTORY_FILE:Ljava/lang/String; = "/data/system/preinstall_history"

.field private static final TAG:Ljava/lang/String; = "ExtraPackageManager"


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 17
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static installPreinstallApp(Ljava/io/File;)V
    .registers 7
    .parameter "apkFile"

    .prologue
    .line 69
    invoke-virtual {p0}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v0

    .line 70
    .local v0, apkName:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "/data/app/"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v1, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 71
    .local v1, installApk:Ljava/io/File;
    invoke-static {p0, v1}, Landroid/os/FileUtils;->copyFile(Ljava/io/File;Ljava/io/File;)Z

    .line 72
    const/4 v4, 0x1

    const/4 v5, 0x0

    invoke-virtual {v1, v4, v5}, Ljava/io/File;->setReadable(ZZ)Z

    .line 74
    const-string v4, ".apk"

    const-string v5, ".odex"

    invoke-virtual {v0, v4, v5}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v3

    .line 75
    .local v3, odexName:Ljava/lang/String;
    new-instance v2, Ljava/io/File;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "/data/app/"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v2, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 76
    .local v2, installOdex:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v4

    if-eqz v4, :cond_4d

    .line 77
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    .line 79
    :cond_4d
    return-void
.end method

.method private static final isPackageFilename(Ljava/lang/String;)Z
    .registers 2
    .parameter "name"

    .prologue
    .line 82
    if-eqz p0, :cond_c

    const-string v0, ".apk"

    invoke-virtual {p0, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_c

    const/4 v0, 0x1

    :goto_b
    return v0

    :cond_c
    const/4 v0, 0x0

    goto :goto_b
.end method

.method public static performPreinstallApp(Lcom/android/server/pm/Settings;)V
    .registers 22
    .parameter "currPkgSettings"

    .prologue
    .line 86
    new-instance v12, Ljava/io/File;

    const-string v17, "/data/media/preinstall_apps/"

    move-object/from16 v0, v17

    invoke-direct {v12, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 88
    .local v12, preinstallAppDir:Ljava/io/File;
    invoke-virtual {v12}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object v13

    .line 89
    .local v13, preinstallAppNames:[Ljava/lang/String;
    if-nez v13, :cond_2a

    .line 90
    const-string v17, "ExtraPackageManager"

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "No files in preinstall app dir "

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v0, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 149
    :cond_29
    return-void

    .line 94
    :cond_2a
    new-instance v3, Ljava/io/File;

    const-string v17, "/data/app/"

    move-object/from16 v0, v17

    invoke-direct {v3, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 95
    .local v3, appDir:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object v6

    .line 97
    .local v6, installAppNames:[Ljava/lang/String;
    const-string v17, "/data/system/preinstall_history"

    invoke-static/range {v17 .. v17}, Lcom/android/server/pm/ExtraPackageManagerServices;->readPreinstallAppHistory(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v7

    .line 99
    .local v7, installHistory:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    const/4 v5, 0x0

    .local v5, i:I
    :goto_3e
    array-length v0, v13

    move/from16 v17, v0

    move/from16 v0, v17

    if-ge v5, v0, :cond_29

    .line 100
    aget-object v14, v13, v5

    .line 101
    .local v14, preinstallName:Ljava/lang/String;
    invoke-static {v14}, Lcom/android/server/pm/ExtraPackageManagerServices;->isPackageFilename(Ljava/lang/String;)Z

    move-result v17

    if-nez v17, :cond_50

    .line 99
    :cond_4d
    :goto_4d
    add-int/lit8 v5, v5, 0x1

    goto :goto_3e

    .line 106
    :cond_50
    new-instance v11, Ljava/io/File;

    invoke-direct {v11, v12, v14}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 107
    .local v11, preinstallApp:Ljava/io/File;
    new-instance v16, Ljava/io/File;

    move-object/from16 v0, v16

    invoke-direct {v0, v3, v14}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 108
    .local v16, preinstalledApp:Ljava/io/File;
    invoke-virtual {v7, v14}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v4

    .line 110
    .local v4, hasInstalled:Z
    if-eqz v4, :cond_78

    .line 112
    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->exists()Z

    move-result v17

    if-eqz v17, :cond_4d

    invoke-virtual/range {v16 .. v16}, Ljava/io/File;->length()J

    move-result-wide v17

    invoke-virtual {v11}, Ljava/io/File;->length()J

    move-result-wide v19

    cmp-long v17, v17, v19

    if-eqz v17, :cond_4d

    .line 113
    invoke-static {v11}, Lcom/android/server/pm/ExtraPackageManagerServices;->installPreinstallApp(Ljava/io/File;)V

    goto :goto_4d

    .line 121
    :cond_78
    if-eqz v6, :cond_7f

    array-length v0, v6

    move/from16 v17, v0

    if-nez v17, :cond_8c

    .line 122
    :cond_7f
    invoke-static {v11}, Lcom/android/server/pm/ExtraPackageManagerServices;->installPreinstallApp(Ljava/io/File;)V

    .line 146
    :cond_82
    :goto_82
    const-string v17, "/data/system/preinstall_history"

    invoke-virtual {v11}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Lcom/android/server/pm/ExtraPackageManagerServices;->writePreinstallAppHistory(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_4d

    .line 126
    :cond_8c
    invoke-virtual {v11}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v2

    .line 127
    .local v2, apkPath:Ljava/lang/String;
    const/4 v8, 0x4

    .line 128
    .local v8, parseFlags:I
    new-instance v10, Landroid/content/pm/PackageParser;

    invoke-direct {v10, v2}, Landroid/content/pm/PackageParser;-><init>(Ljava/lang/String;)V

    .line 129
    .local v10, pp:Landroid/content/pm/PackageParser;
    invoke-virtual {v11}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v17

    const/16 v18, 0x0

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    invoke-virtual {v10, v11, v0, v1, v8}, Landroid/content/pm/PackageParser;->parsePackage(Ljava/io/File;Ljava/lang/String;Landroid/util/DisplayMetrics;I)Landroid/content/pm/PackageParser$Package;

    move-result-object v15

    .line 132
    .local v15, preinstallPkg:Landroid/content/pm/PackageParser$Package;
    if-nez v15, :cond_c9

    .line 133
    const-string v17, "ExtraPackageManager"

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "preinstall app "

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual {v11}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    const-string v19, " package parser fail!"

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_4d

    .line 138
    :cond_c9
    iget-object v0, v15, Landroid/content/pm/PackageParser$Package;->packageName:Ljava/lang/String;

    move-object/from16 v17, v0

    move-object/from16 v0, p0

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Lcom/android/server/pm/Settings;->peekPackageLPr(Ljava/lang/String;)Lcom/android/server/pm/PackageSetting;

    move-result-object v9

    .line 141
    .local v9, pkgSetting:Lcom/android/server/pm/PackageSetting;
    if-eqz v9, :cond_df

    iget v0, v9, Lcom/android/server/pm/GrantedPermissions;->pkgFlags:I

    move/from16 v17, v0

    and-int/lit8 v17, v17, 0x1

    if-eqz v17, :cond_82

    .line 142
    :cond_df
    invoke-static {v11}, Lcom/android/server/pm/ExtraPackageManagerServices;->installPreinstallApp(Ljava/io/File;)V

    goto :goto_82
.end method

.method private static readPreinstallAppHistory(Ljava/lang/String;)Ljava/util/ArrayList;
    .registers 7
    .parameter "filePath"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    .line 24
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    .line 27
    .local v2, hisList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    :try_start_5
    new-instance v3, Ljava/io/File;

    invoke-direct {v3, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 29
    .local v3, installHistoryFile:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v5

    if-nez v5, :cond_11

    .line 46
    .end local v3           #installHistoryFile:Ljava/io/File;
    :goto_10
    return-object v2

    .line 33
    .restart local v3       #installHistoryFile:Ljava/io/File;
    :cond_11
    new-instance v1, Ljava/io/FileReader;

    invoke-direct {v1, v3}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    .line 34
    .local v1, fileReader:Ljava/io/FileReader;
    new-instance v0, Ljava/io/BufferedReader;

    invoke-direct {v0, v1}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 35
    .local v0, bufferReader:Ljava/io/BufferedReader;
    const/4 v4, 0x0

    .line 37
    .local v4, line:Ljava/lang/String;
    :goto_1c
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v4

    if-eqz v4, :cond_28

    .line 38
    invoke-virtual {v2, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_1c

    .line 43
    .end local v0           #bufferReader:Ljava/io/BufferedReader;
    .end local v1           #fileReader:Ljava/io/FileReader;
    .end local v3           #installHistoryFile:Ljava/io/File;
    .end local v4           #line:Ljava/lang/String;
    :catch_26
    move-exception v5

    goto :goto_10

    .line 41
    .restart local v0       #bufferReader:Ljava/io/BufferedReader;
    .restart local v1       #fileReader:Ljava/io/FileReader;
    .restart local v3       #installHistoryFile:Ljava/io/File;
    .restart local v4       #line:Ljava/lang/String;
    :cond_28
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V

    .line 42
    invoke-virtual {v1}, Ljava/io/FileReader;->close()V
    :try_end_2e
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_2e} :catch_26

    goto :goto_10
.end method

.method private static writePreinstallAppHistory(Ljava/lang/String;Ljava/lang/String;)V
    .registers 6
    .parameter "filePath"
    .parameter "installPkgName"

    .prologue
    .line 51
    :try_start_0
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 53
    .local v2, installHistoryFile:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    if-nez v3, :cond_e

    .line 54
    invoke-virtual {v2}, Ljava/io/File;->createNewFile()Z

    .line 57
    :cond_e
    new-instance v1, Ljava/io/FileWriter;

    const/4 v3, 0x1

    invoke-direct {v1, v2, v3}, Ljava/io/FileWriter;-><init>(Ljava/io/File;Z)V

    .line 58
    .local v1, fileWriter:Ljava/io/FileWriter;
    new-instance v0, Ljava/io/BufferedWriter;

    invoke-direct {v0, v1}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    .line 59
    .local v0, bufferWriter:Ljava/io/BufferedWriter;
    invoke-virtual {v0, p1}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 60
    const-string v3, "\n"

    invoke-virtual {v0, v3}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 62
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V

    .line 63
    invoke-virtual {v1}, Ljava/io/FileWriter;->close()V
    :try_end_27
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_27} :catch_28

    .line 66
    .end local v0           #bufferWriter:Ljava/io/BufferedWriter;
    .end local v1           #fileWriter:Ljava/io/FileWriter;
    .end local v2           #installHistoryFile:Ljava/io/File;
    :goto_27
    return-void

    .line 64
    :catch_28
    move-exception v3

    goto :goto_27
.end method
