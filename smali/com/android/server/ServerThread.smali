.class Lcom/android/server/ServerThread;
.super Ljava/lang/Thread;
.source "SystemServer.java"


# static fields
.field private static final ENCRYPTED_STATE:Ljava/lang/String; = "1"

.field private static final ENCRYPTING_STATE:Ljava/lang/String; = "trigger_restart_min_framework"

.field private static final TAG:Ljava/lang/String; = "SystemServer"


# instance fields
.field mContentResolver:Landroid/content/ContentResolver;


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 71
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method

.method static final startSystemUi(Landroid/content/Context;)V
    .registers 5
    .parameter "context"

    .prologue
    .line 899
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 900
    .local v0, intent:Landroid/content/Intent;
    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.android.systemui"

    const-string v3, "com.android.systemui.SystemUIService"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 902
    const-string v1, "SystemServer"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Starting service: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 903
    invoke-virtual {p0, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 904
    return-void
.end method


# virtual methods
.method reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V
    .registers 6
    .parameter "msg"
    .parameter "e"

    .prologue
    .line 79
    const-string v0, "SystemServer"

    const-string v1, "***********************************************"

    invoke-static {v0, v1}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 80
    const-string v0, "SystemServer"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "BOOT FAILURE "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1, p2}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 81
    return-void
.end method

.method public run()V
    .registers 121
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 86
    const/16 v3, 0xbc2

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v9

    invoke-static {v3, v9, v10}, Landroid/util/EventLog;->writeEvent(IJ)I

    .line 89
    invoke-static {}, Landroid/os/Looper;->prepare()V

    .line 91
    const/4 v3, -0x2

    invoke-static {v3}, Landroid/os/Process;->setThreadPriority(I)V

    .line 94
    const/4 v3, 0x1

    invoke-static {v3}, Lcom/android/internal/os/BinderInternal;->disableBackgroundScheduling(Z)V

    .line 95
    const/4 v3, 0x0

    invoke-static {v3}, Landroid/os/Process;->setCanSelfBackground(Z)V

    .line 99
    const-string v3, "sys.shutdown.requested"

    const-string v9, ""

    invoke-static {v3, v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v99

    .line 101
    .local v99, shutdownAction:Ljava/lang/String;
    if-eqz v99, :cond_4e

    invoke-virtual/range {v99 .. v99}, Ljava/lang/String;->length()I

    move-result v3

    if-lez v3, :cond_4e

    .line 102
    const/4 v3, 0x0

    move-object/from16 v0, v99

    invoke-virtual {v0, v3}, Ljava/lang/String;->charAt(I)C

    move-result v3

    const/16 v9, 0x31

    if-ne v3, v9, :cond_66f

    const/16 v93, 0x1

    .line 105
    .local v93, reboot:Z
    :goto_35
    invoke-virtual/range {v99 .. v99}, Ljava/lang/String;->length()I

    move-result v3

    const/4 v9, 0x1

    if-le v3, v9, :cond_673

    .line 106
    const/4 v3, 0x1

    invoke-virtual/range {v99 .. v99}, Ljava/lang/String;->length()I

    move-result v9

    move-object/from16 v0, v99

    invoke-virtual {v0, v3, v9}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v92

    .line 111
    .local v92, reason:Ljava/lang/String;
    :goto_47
    move/from16 v0, v93

    move-object/from16 v1, v92

    invoke-static {v0, v1}, Lcom/android/server/pm/ShutdownThread;->rebootOrShutdown(ZLjava/lang/String;)V

    .line 115
    .end local v92           #reason:Ljava/lang/String;
    .end local v93           #reboot:Z
    :cond_4e
    const-string v3, "ro.factorytest"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v69

    .line 116
    .local v69, factoryTestStr:Ljava/lang/String;
    const-string v3, ""

    move-object/from16 v0, v69

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_677

    const/16 v68, 0x0

    .line 118
    .local v68, factoryTest:I
    :goto_60
    const-string v3, "1"

    const-string v9, "ro.config.headless"

    const-string v10, "0"

    invoke-static {v9, v10}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v16

    .line 120
    .local v16, headless:Z
    const/16 v41, 0x0

    .line 121
    .local v41, accountManager:Landroid/accounts/AccountManagerService;
    const/16 v57, 0x0

    .line 122
    .local v57, contentService:Landroid/content/ContentService;
    const/16 v74, 0x0

    .line 123
    .local v74, lights:Lcom/android/server/LightsService;
    const/16 v91, 0x0

    .line 124
    .local v91, power:Lcom/android/server/PowerManagerService;
    const/16 v46, 0x0

    .line 125
    .local v46, battery:Lcom/android/server/BatteryService;
    const/16 v110, 0x0

    .line 126
    .local v110, vibrator:Lcom/android/server/VibratorService;
    const/16 v43, 0x0

    .line 127
    .local v43, alarm:Lcom/android/server/AlarmManagerService;
    const/4 v13, 0x0

    .line 128
    .local v13, networkManagement:Lcom/android/server/NetworkManagementService;
    const/4 v12, 0x0

    .line 129
    .local v12, networkStats:Lcom/android/server/net/NetworkStatsService;
    const/16 v83, 0x0

    .line 130
    .local v83, networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    const/16 v55, 0x0

    .line 131
    .local v55, connectivity:Lcom/android/server/ConnectivityService;
    const/16 v117, 0x0

    .line 132
    .local v117, wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    const/16 v115, 0x0

    .line 133
    .local v115, wifi:Lcom/android/server/WifiService;
    const/16 v98, 0x0

    .line 134
    .local v98, serviceDiscovery:Lcom/android/server/NsdService;
    const/16 v90, 0x0

    .line 135
    .local v90, pm:Landroid/content/pm/IPackageManager;
    const/4 v4, 0x0

    .line 136
    .local v4, context:Landroid/content/Context;
    const/16 v119, 0x0

    .line 137
    .local v119, wm:Lcom/android/server/wm/WindowManagerService;
    const/16 v47, 0x0

    .line 138
    .local v47, bluetooth:Landroid/server/BluetoothService;
    const/16 v49, 0x0

    .line 139
    .local v49, bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    const/16 v63, 0x0

    .line 140
    .local v63, dock:Lcom/android/server/DockObserver;
    const/16 v108, 0x0

    .line 141
    .local v108, usb:Lcom/android/server/usb/UsbService;
    const/16 v96, 0x0

    .line 142
    .local v96, serial:Lcom/android/server/SerialService;
    const/16 v106, 0x0

    .line 143
    .local v106, uiMode:Lcom/android/server/UiModeManagerService;
    const/16 v94, 0x0

    .line 144
    .local v94, recognition:Lcom/android/server/RecognitionManagerService;
    const/16 v102, 0x0

    .line 145
    .local v102, throttle:Lcom/android/server/ThrottleService;
    const/16 v85, 0x0

    .line 146
    .local v85, networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    const/16 v52, 0x0

    .line 147
    .local v52, commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    const/16 v73, 0x0

    .line 151
    .local v73, inputManager:Lcom/android/server/input/InputManagerService;
    :try_start_a3
    const-string v3, "SystemServer"

    const-string v9, "Entropy Mixer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 152
    const-string v3, "entropy"

    new-instance v9, Lcom/android/server/EntropyMixer;

    invoke-direct {v9}, Lcom/android/server/EntropyMixer;-><init>()V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 154
    const-string v3, "SystemServer"

    const-string v9, "Power Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 155
    new-instance v6, Lcom/android/server/PowerManagerService;

    invoke-direct {v6}, Lcom/android/server/PowerManagerService;-><init>()V
    :try_end_c0
    .catch Ljava/lang/RuntimeException; {:try_start_a3 .. :try_end_c0} :catch_9e7

    .line 156
    .end local v91           #power:Lcom/android/server/PowerManagerService;
    .local v6, power:Lcom/android/server/PowerManagerService;
    :try_start_c0
    const-string v3, "power"

    invoke-static {v3, v6}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 158
    const-string v3, "SystemServer"

    const-string v9, "Activity Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 159
    invoke-static/range {v68 .. v68}, Lcom/android/server/am/ActivityManagerService;->main(I)Landroid/content/Context;

    move-result-object v4

    .line 161
    const-string v3, "SystemServer"

    const-string v9, "Telephony Registry"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 162
    const-string v3, "telephony.registry"

    new-instance v9, Lcom/android/server/TelephonyRegistry;

    invoke-direct {v9, v4}, Lcom/android/server/TelephonyRegistry;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 164
    const-string v3, "SystemServer"

    const-string v9, "Scheduling Policy"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 165
    const-string v3, "scheduling_policy"

    new-instance v9, Landroid/os/SchedulingPolicyService;

    invoke-direct {v9}, Landroid/os/SchedulingPolicyService;-><init>()V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 168
    invoke-static {v4}, Lcom/android/server/AttributeCache;->init(Landroid/content/Context;)V

    .line 170
    const-string v3, "SystemServer"

    const-string v9, "Package Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 172
    const-string v3, "vold.decrypt"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v60

    .line 173
    .local v60, cryptState:Ljava/lang/String;
    const/16 v89, 0x0

    .line 174
    .local v89, onlyCore:Z
    const-string v3, "trigger_restart_min_framework"

    move-object/from16 v0, v60

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_67d

    .line 175
    const-string v3, "SystemServer"

    const-string v9, "Detected encryption in progress - only parsing core apps"

    invoke-static {v3, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 176
    const/16 v89, 0x1

    .line 182
    :cond_117
    :goto_117
    if-eqz v68, :cond_692

    const/4 v3, 0x1

    :goto_11a
    move/from16 v0, v89

    invoke-static {v4, v3, v0}, Lcom/android/server/pm/PackageManagerService;->main(Landroid/content/Context;ZZ)Landroid/content/pm/IPackageManager;
    :try_end_11f
    .catch Ljava/lang/RuntimeException; {:try_start_c0 .. :try_end_11f} :catch_6a1

    move-result-object v90

    .line 185
    const/16 v70, 0x0

    .line 187
    .local v70, firstBoot:Z
    :try_start_122
    invoke-interface/range {v90 .. v90}, Landroid/content/pm/IPackageManager;->isFirstBoot()Z
    :try_end_125
    .catch Landroid/os/RemoteException; {:try_start_122 .. :try_end_125} :catch_97c
    .catch Ljava/lang/RuntimeException; {:try_start_122 .. :try_end_125} :catch_6a1

    move-result v70

    .line 191
    :goto_126
    :try_start_126
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->setSystemProcess()V

    .line 193
    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    move-object/from16 v0, p0

    iput-object v3, v0, Lcom/android/server/ServerThread;->mContentResolver:Landroid/content/ContentResolver;
    :try_end_131
    .catch Ljava/lang/RuntimeException; {:try_start_126 .. :try_end_131} :catch_6a1

    .line 197
    :try_start_131
    const-string v3, "SystemServer"

    const-string v9, "Account Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 198
    new-instance v42, Landroid/accounts/AccountManagerService;

    move-object/from16 v0, v42

    invoke-direct {v0, v4}, Landroid/accounts/AccountManagerService;-><init>(Landroid/content/Context;)V
    :try_end_13f
    .catch Ljava/lang/Throwable; {:try_start_131 .. :try_end_13f} :catch_695
    .catch Ljava/lang/RuntimeException; {:try_start_131 .. :try_end_13f} :catch_6a1

    .line 199
    .end local v41           #accountManager:Landroid/accounts/AccountManagerService;
    .local v42, accountManager:Landroid/accounts/AccountManagerService;
    :try_start_13f
    const-string v3, "account"

    move-object/from16 v0, v42

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_146
    .catch Ljava/lang/Throwable; {:try_start_13f .. :try_end_146} :catch_a26
    .catch Ljava/lang/RuntimeException; {:try_start_13f .. :try_end_146} :catch_9f0

    move-object/from16 v41, v42

    .line 204
    .end local v42           #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v41       #accountManager:Landroid/accounts/AccountManagerService;
    :goto_148
    :try_start_148
    const-string v3, "SystemServer"

    const-string v9, "Content Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 205
    const/4 v3, 0x1

    move/from16 v0, v68

    if-ne v0, v3, :cond_6b8

    const/4 v3, 0x1

    :goto_155
    invoke-static {v4, v3}, Landroid/content/ContentService;->main(Landroid/content/Context;Z)Landroid/content/ContentService;

    move-result-object v57

    .line 208
    const-string v3, "SystemServer"

    const-string v9, "System Content Providers"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 209
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->installSystemProviders()V

    .line 211
    const-string v3, "SystemServer"

    const-string v9, "Lights Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 212
    new-instance v75, Lcom/android/server/MiuiLightsService;

    move-object/from16 v0, v75

    invoke-direct {v0, v4}, Lcom/android/server/MiuiLightsService;-><init>(Landroid/content/Context;)V
    :try_end_171
    .catch Ljava/lang/RuntimeException; {:try_start_148 .. :try_end_171} :catch_6a1

    .line 214
    .end local v74           #lights:Lcom/android/server/LightsService;
    .local v75, lights:Lcom/android/server/LightsService;
    :try_start_171
    const-string v3, "SystemServer"

    const-string v9, "Battery Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 215
    new-instance v5, Lcom/android/server/BatteryService;

    move-object/from16 v0, v75

    invoke-direct {v5, v4, v0}, Lcom/android/server/BatteryService;-><init>(Landroid/content/Context;Lcom/android/server/LightsService;)V
    :try_end_17f
    .catch Ljava/lang/RuntimeException; {:try_start_171 .. :try_end_17f} :catch_9f9

    .line 216
    .end local v46           #battery:Lcom/android/server/BatteryService;
    .local v5, battery:Lcom/android/server/BatteryService;
    :try_start_17f
    const-string v3, "battery"

    invoke-static {v3, v5}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 218
    const-string v3, "SystemServer"

    const-string v9, "Vibrator Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 219
    new-instance v111, Lcom/android/server/VibratorService;

    move-object/from16 v0, v111

    invoke-direct {v0, v4}, Lcom/android/server/VibratorService;-><init>(Landroid/content/Context;)V
    :try_end_192
    .catch Ljava/lang/RuntimeException; {:try_start_17f .. :try_end_192} :catch_a02

    .line 220
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .local v111, vibrator:Lcom/android/server/VibratorService;
    :try_start_192
    const-string v3, "vibrator"

    move-object/from16 v0, v111

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 224
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    move-object/from16 v0, v75

    invoke-virtual {v6, v4, v0, v3, v5}, Lcom/android/server/PowerManagerService;->init(Landroid/content/Context;Lcom/android/server/LightsService;Landroid/app/IActivityManager;Lcom/android/server/BatteryService;)V

    .line 226
    const-string v3, "SystemServer"

    const-string v9, "Alarm Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 227
    new-instance v7, Lcom/android/server/AlarmManagerService;

    invoke-direct {v7, v4}, Lcom/android/server/AlarmManagerService;-><init>(Landroid/content/Context;)V
    :try_end_1ae
    .catch Ljava/lang/RuntimeException; {:try_start_192 .. :try_end_1ae} :catch_a09

    .line 228
    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .local v7, alarm:Lcom/android/server/AlarmManagerService;
    :try_start_1ae
    const-string v3, "alarm"

    invoke-static {v3, v7}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 230
    const-string v3, "SystemServer"

    const-string v9, "Init Watchdog"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 231
    invoke-static {}, Lcom/android/server/Watchdog;->getInstance()Lcom/android/server/Watchdog;

    move-result-object v3

    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v8

    invoke-virtual/range {v3 .. v8}, Lcom/android/server/Watchdog;->init(Landroid/content/Context;Lcom/android/server/BatteryService;Lcom/android/server/PowerManagerService;Lcom/android/server/AlarmManagerService;Lcom/android/server/am/ActivityManagerService;)V

    .line 234
    const-string v3, "SystemServer"

    const-string v9, "Window Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 235
    const/4 v3, 0x1

    move/from16 v0, v68

    if-eq v0, v3, :cond_6bb

    const/4 v3, 0x1

    move v9, v3

    :goto_1d3
    if-nez v70, :cond_6bf

    const/4 v3, 0x1

    :goto_1d6
    move/from16 v0, v89

    invoke-static {v4, v6, v9, v3, v0}, Lcom/android/server/wm/WindowManagerService;->main(Landroid/content/Context;Lcom/android/server/PowerManagerService;ZZZ)Lcom/android/server/wm/WindowManagerService;

    move-result-object v119

    .line 238
    const-string v3, "window"

    move-object/from16 v0, v119

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 239
    invoke-virtual/range {v119 .. v119}, Lcom/android/server/wm/WindowManagerService;->getInputManagerService()Lcom/android/server/input/InputManagerService;

    move-result-object v73

    .line 240
    const-string v3, "input"

    move-object/from16 v0, v73

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 242
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    move-object/from16 v0, v119

    invoke-virtual {v3, v0}, Lcom/android/server/am/ActivityManagerService;->setWindowManager(Lcom/android/server/wm/WindowManagerService;)V

    .line 247
    const-string v3, "ro.kernel.qemu"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v9, "1"

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_6c2

    .line 248
    const-string v3, "SystemServer"

    const-string v9, "No Bluetooh Service (emulator)"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_20c
    .catch Ljava/lang/RuntimeException; {:try_start_1ae .. :try_end_20c} :catch_6d0

    :goto_20c
    move-object/from16 v110, v111

    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v74, v75

    .line 276
    .end local v60           #cryptState:Ljava/lang/String;
    .end local v70           #firstBoot:Z
    .end local v75           #lights:Lcom/android/server/LightsService;
    .end local v89           #onlyCore:Z
    .restart local v74       #lights:Lcom/android/server/LightsService;
    :goto_210
    const/16 v61, 0x0

    .line 277
    .local v61, devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    const/16 v100, 0x0

    .line 278
    .local v100, statusBar:Lcom/android/server/StatusBarManagerService;
    const/16 v71, 0x0

    .line 279
    .local v71, imm:Lcom/android/server/InputMethodManagerService;
    const/16 v44, 0x0

    .line 280
    .local v44, appWidget:Lcom/android/server/AppWidgetService;
    const/16 v87, 0x0

    .line 281
    .local v87, notification:Lcom/android/server/NotificationManagerService;
    const/16 v113, 0x0

    .line 282
    .local v113, wallpaper:Lcom/android/server/WallpaperManagerService;
    const/16 v76, 0x0

    .line 283
    .local v76, location:Lcom/android/server/LocationManagerService;
    const/16 v58, 0x0

    .line 284
    .local v58, countryDetector:Lcom/android/server/CountryDetectorService;
    const/16 v104, 0x0

    .line 285
    .local v104, tsms:Lcom/android/server/TextServicesManagerService;
    const/16 v78, 0x0

    .line 286
    .local v78, lockSettings:Lcom/android/internal/widget/LockSettingsService;
    const/16 v65, 0x0

    .line 289
    .local v65, dreamy:Landroid/service/dreams/DreamManagerService;
    const/4 v3, 0x1

    move/from16 v0, v68

    if-eq v0, v3, :cond_255

    .line 291
    :try_start_22b
    const-string v3, "SystemServer"

    const-string v9, "Input Method Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 292
    new-instance v72, Lcom/android/server/InputMethodManagerService;

    move-object/from16 v0, v72

    move-object/from16 v1, v119

    invoke-direct {v0, v4, v1}, Lcom/android/server/InputMethodManagerService;-><init>(Landroid/content/Context;Lcom/android/server/wm/WindowManagerService;)V
    :try_end_23b
    .catch Ljava/lang/Throwable; {:try_start_22b .. :try_end_23b} :catch_725

    .line 293
    .end local v71           #imm:Lcom/android/server/InputMethodManagerService;
    .local v72, imm:Lcom/android/server/InputMethodManagerService;
    :try_start_23b
    const-string v3, "input_method"

    move-object/from16 v0, v72

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_242
    .catch Ljava/lang/Throwable; {:try_start_23b .. :try_end_242} :catch_9e2

    move-object/from16 v71, v72

    .line 299
    .end local v72           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v71       #imm:Lcom/android/server/InputMethodManagerService;
    :goto_244
    :try_start_244
    const-string v3, "SystemServer"

    const-string v9, "Accessibility Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 300
    const-string v3, "accessibility"

    new-instance v9, Lcom/android/server/accessibility/AccessibilityManagerService;

    invoke-direct {v9, v4}, Lcom/android/server/accessibility/AccessibilityManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_255
    .catch Ljava/lang/Throwable; {:try_start_244 .. :try_end_255} :catch_731

    .line 308
    :cond_255
    :goto_255
    :try_start_255
    invoke-virtual/range {v119 .. v119}, Lcom/android/server/wm/WindowManagerService;->displayReady()V
    :try_end_258
    .catch Ljava/lang/Throwable; {:try_start_255 .. :try_end_258} :catch_73d

    .line 314
    :goto_258
    :try_start_258
    invoke-interface/range {v90 .. v90}, Landroid/content/pm/IPackageManager;->performBootDexOpt()V
    :try_end_25b
    .catch Ljava/lang/Throwable; {:try_start_258 .. :try_end_25b} :catch_749

    .line 320
    :goto_25b
    :try_start_25b
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v3

    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v9

    const v10, 0x10403e5

    invoke-virtual {v9, v10}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v9

    const/4 v10, 0x0

    invoke-interface {v3, v9, v10}, Landroid/app/IActivityManager;->showBootMessage(Ljava/lang/CharSequence;Z)V
    :try_end_26e
    .catch Landroid/os/RemoteException; {:try_start_25b .. :try_end_26e} :catch_9df

    .line 327
    :goto_26e
    const/4 v3, 0x1

    move/from16 v0, v68

    if-eq v0, v3, :cond_a2b

    .line 328
    const/16 v81, 0x0

    .line 329
    .local v81, mountService:Lcom/android/server/MountService;
    const-string v3, "0"

    const-string v9, "system_init.startmountservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_29a

    .line 335
    :try_start_283
    const-string v3, "SystemServer"

    const-string v9, "Mount Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 336
    new-instance v82, Lcom/android/server/MountService;

    move-object/from16 v0, v82

    invoke-direct {v0, v4}, Lcom/android/server/MountService;-><init>(Landroid/content/Context;)V
    :try_end_291
    .catch Ljava/lang/Throwable; {:try_start_283 .. :try_end_291} :catch_755

    .line 337
    .end local v81           #mountService:Lcom/android/server/MountService;
    .local v82, mountService:Lcom/android/server/MountService;
    :try_start_291
    const-string v3, "mount"

    move-object/from16 v0, v82

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_298
    .catch Ljava/lang/Throwable; {:try_start_291 .. :try_end_298} :catch_9da

    move-object/from16 v81, v82

    .line 344
    .end local v82           #mountService:Lcom/android/server/MountService;
    .restart local v81       #mountService:Lcom/android/server/MountService;
    :cond_29a
    :goto_29a
    :try_start_29a
    const-string v3, "SystemServer"

    const-string v9, "LockSettingsService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 345
    new-instance v79, Lcom/android/internal/widget/LockSettingsService;

    move-object/from16 v0, v79

    invoke-direct {v0, v4}, Lcom/android/internal/widget/LockSettingsService;-><init>(Landroid/content/Context;)V
    :try_end_2a8
    .catch Ljava/lang/Throwable; {:try_start_29a .. :try_end_2a8} :catch_761

    .line 346
    .end local v78           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .local v79, lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :try_start_2a8
    const-string v3, "lock_settings"

    move-object/from16 v0, v79

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2af
    .catch Ljava/lang/Throwable; {:try_start_2a8 .. :try_end_2af} :catch_9d5

    move-object/from16 v78, v79

    .line 352
    .end local v79           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v78       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :goto_2b1
    :try_start_2b1
    const-string v3, "SystemServer"

    const-string v9, "Device Policy"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 353
    new-instance v62, Lcom/android/server/DevicePolicyManagerService;

    move-object/from16 v0, v62

    invoke-direct {v0, v4}, Lcom/android/server/DevicePolicyManagerService;-><init>(Landroid/content/Context;)V
    :try_end_2bf
    .catch Ljava/lang/Throwable; {:try_start_2b1 .. :try_end_2bf} :catch_76d

    .line 354
    .end local v61           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .local v62, devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :try_start_2bf
    const-string v3, "device_policy"

    move-object/from16 v0, v62

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2c6
    .catch Ljava/lang/Throwable; {:try_start_2bf .. :try_end_2c6} :catch_9d0

    move-object/from16 v61, v62

    .line 360
    .end local v62           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v61       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :goto_2c8
    :try_start_2c8
    const-string v3, "SystemServer"

    const-string v9, "Status Bar"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 361
    new-instance v101, Lcom/android/server/StatusBarManagerService;

    move-object/from16 v0, v101

    move-object/from16 v1, v119

    invoke-direct {v0, v4, v1}, Lcom/android/server/StatusBarManagerService;-><init>(Landroid/content/Context;Lcom/android/server/wm/WindowManagerService;)V
    :try_end_2d8
    .catch Ljava/lang/Throwable; {:try_start_2c8 .. :try_end_2d8} :catch_779

    .line 362
    .end local v100           #statusBar:Lcom/android/server/StatusBarManagerService;
    .local v101, statusBar:Lcom/android/server/StatusBarManagerService;
    :try_start_2d8
    const-string v3, "statusbar"

    move-object/from16 v0, v101

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2df
    .catch Ljava/lang/Throwable; {:try_start_2d8 .. :try_end_2df} :catch_9cb

    move-object/from16 v100, v101

    .line 368
    .end local v101           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v100       #statusBar:Lcom/android/server/StatusBarManagerService;
    :goto_2e1
    :try_start_2e1
    const-string v3, "SystemServer"

    const-string v9, "Clipboard Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 369
    const-string v3, "clipboard"

    new-instance v9, Lcom/android/server/ClipboardService;

    invoke-direct {v9, v4}, Lcom/android/server/ClipboardService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2f2
    .catch Ljava/lang/Throwable; {:try_start_2e1 .. :try_end_2f2} :catch_785

    .line 376
    :goto_2f2
    :try_start_2f2
    const-string v3, "SystemServer"

    const-string v9, "NetworkManagement Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 377
    invoke-static {v4}, Lcom/android/server/NetworkManagementService;->create(Landroid/content/Context;)Lcom/android/server/NetworkManagementService;

    move-result-object v13

    .line 378
    const-string v3, "network_management"

    invoke-static {v3, v13}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_302
    .catch Ljava/lang/Throwable; {:try_start_2f2 .. :try_end_302} :catch_791

    .line 384
    :goto_302
    :try_start_302
    const-string v3, "SystemServer"

    const-string v9, "Text Service Manager Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 385
    new-instance v105, Lcom/android/server/TextServicesManagerService;

    move-object/from16 v0, v105

    invoke-direct {v0, v4}, Lcom/android/server/TextServicesManagerService;-><init>(Landroid/content/Context;)V
    :try_end_310
    .catch Ljava/lang/Throwable; {:try_start_302 .. :try_end_310} :catch_79d

    .line 386
    .end local v104           #tsms:Lcom/android/server/TextServicesManagerService;
    .local v105, tsms:Lcom/android/server/TextServicesManagerService;
    :try_start_310
    const-string v3, "textservices"

    move-object/from16 v0, v105

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_317
    .catch Ljava/lang/Throwable; {:try_start_310 .. :try_end_317} :catch_9c6

    move-object/from16 v104, v105

    .line 392
    .end local v105           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v104       #tsms:Lcom/android/server/TextServicesManagerService;
    :goto_319
    :try_start_319
    const-string v3, "SystemServer"

    const-string v9, "NetworkStats Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 393
    new-instance v84, Lcom/android/server/net/NetworkStatsService;

    move-object/from16 v0, v84

    invoke-direct {v0, v4, v13, v7}, Lcom/android/server/net/NetworkStatsService;-><init>(Landroid/content/Context;Landroid/os/INetworkManagementService;Landroid/app/IAlarmManager;)V
    :try_end_327
    .catch Ljava/lang/Throwable; {:try_start_319 .. :try_end_327} :catch_7a9

    .line 394
    .end local v12           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .local v84, networkStats:Lcom/android/server/net/NetworkStatsService;
    :try_start_327
    const-string v3, "netstats"

    move-object/from16 v0, v84

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_32e
    .catch Ljava/lang/Throwable; {:try_start_327 .. :try_end_32e} :catch_9c1

    move-object/from16 v12, v84

    .line 400
    .end local v84           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    :goto_330
    :try_start_330
    const-string v3, "SystemServer"

    const-string v9, "NetworkPolicy Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 401
    new-instance v8, Lcom/android/server/net/NetworkPolicyManagerService;

    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v10

    move-object v9, v4

    move-object v11, v6

    invoke-direct/range {v8 .. v13}, Lcom/android/server/net/NetworkPolicyManagerService;-><init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;)V
    :try_end_342
    .catch Ljava/lang/Throwable; {:try_start_330 .. :try_end_342} :catch_7b5

    .line 404
    .end local v83           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .local v8, networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    :try_start_342
    const-string v3, "netpolicy"

    invoke-static {v3, v8}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_347
    .catch Ljava/lang/Throwable; {:try_start_342 .. :try_end_347} :catch_9be

    .line 410
    :goto_347
    :try_start_347
    const-string v3, "SystemServer"

    const-string v9, "Wi-Fi P2pService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 411
    new-instance v118, Landroid/net/wifi/p2p/WifiP2pService;

    move-object/from16 v0, v118

    invoke-direct {v0, v4}, Landroid/net/wifi/p2p/WifiP2pService;-><init>(Landroid/content/Context;)V
    :try_end_355
    .catch Ljava/lang/Throwable; {:try_start_347 .. :try_end_355} :catch_7c3

    .line 412
    .end local v117           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .local v118, wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :try_start_355
    const-string v3, "wifip2p"

    move-object/from16 v0, v118

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_35c
    .catch Ljava/lang/Throwable; {:try_start_355 .. :try_end_35c} :catch_9b9

    move-object/from16 v117, v118

    .line 418
    .end local v118           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v117       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :goto_35e
    :try_start_35e
    const-string v3, "SystemServer"

    const-string v9, "Wi-Fi Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 419
    new-instance v116, Lcom/android/server/WifiService;

    move-object/from16 v0, v116

    invoke-direct {v0, v4}, Lcom/android/server/WifiService;-><init>(Landroid/content/Context;)V
    :try_end_36c
    .catch Ljava/lang/Throwable; {:try_start_35e .. :try_end_36c} :catch_7cf

    .line 420
    .end local v115           #wifi:Lcom/android/server/WifiService;
    .local v116, wifi:Lcom/android/server/WifiService;
    :try_start_36c
    const-string v3, "wifi"

    move-object/from16 v0, v116

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_373
    .catch Ljava/lang/Throwable; {:try_start_36c .. :try_end_373} :catch_9b4

    move-object/from16 v115, v116

    .line 426
    .end local v116           #wifi:Lcom/android/server/WifiService;
    .restart local v115       #wifi:Lcom/android/server/WifiService;
    :goto_375
    :try_start_375
    const-string v3, "SystemServer"

    const-string v9, "Connectivity Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 427
    new-instance v56, Lcom/android/server/ConnectivityService;

    move-object/from16 v0, v56

    invoke-direct {v0, v4, v13, v12, v8}, Lcom/android/server/ConnectivityService;-><init>(Landroid/content/Context;Landroid/os/INetworkManagementService;Landroid/net/INetworkStatsService;Landroid/net/INetworkPolicyManager;)V
    :try_end_383
    .catch Ljava/lang/Throwable; {:try_start_375 .. :try_end_383} :catch_7db

    .line 429
    .end local v55           #connectivity:Lcom/android/server/ConnectivityService;
    .local v56, connectivity:Lcom/android/server/ConnectivityService;
    :try_start_383
    const-string v3, "connectivity"

    move-object/from16 v0, v56

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 430
    move-object/from16 v0, v56

    invoke-virtual {v12, v0}, Lcom/android/server/net/NetworkStatsService;->bindConnectivityManager(Landroid/net/IConnectivityManager;)V

    .line 431
    move-object/from16 v0, v56

    invoke-virtual {v8, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->bindConnectivityManager(Landroid/net/IConnectivityManager;)V

    .line 432
    invoke-virtual/range {v115 .. v115}, Lcom/android/server/WifiService;->checkAndStartWifi()V

    .line 433
    invoke-virtual/range {v117 .. v117}, Landroid/net/wifi/p2p/WifiP2pService;->connectivityServiceReady()V
    :try_end_39a
    .catch Ljava/lang/Throwable; {:try_start_383 .. :try_end_39a} :catch_9af

    move-object/from16 v55, v56

    .line 439
    .end local v56           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v55       #connectivity:Lcom/android/server/ConnectivityService;
    :goto_39c
    :try_start_39c
    const-string v3, "SystemServer"

    const-string v9, "Network Service Discovery Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 440
    invoke-static {v4}, Lcom/android/server/NsdService;->create(Landroid/content/Context;)Lcom/android/server/NsdService;

    move-result-object v98

    .line 441
    const-string v3, "servicediscovery"

    move-object/from16 v0, v98

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3ae
    .catch Ljava/lang/Throwable; {:try_start_39c .. :try_end_3ae} :catch_7e7

    .line 448
    :goto_3ae
    :try_start_3ae
    const-string v3, "SystemServer"

    const-string v9, "Throttle Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 449
    new-instance v103, Lcom/android/server/ThrottleService;

    move-object/from16 v0, v103

    invoke-direct {v0, v4}, Lcom/android/server/ThrottleService;-><init>(Landroid/content/Context;)V
    :try_end_3bc
    .catch Ljava/lang/Throwable; {:try_start_3ae .. :try_end_3bc} :catch_7f3

    .line 450
    .end local v102           #throttle:Lcom/android/server/ThrottleService;
    .local v103, throttle:Lcom/android/server/ThrottleService;
    :try_start_3bc
    const-string v3, "throttle"

    move-object/from16 v0, v103

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3c3
    .catch Ljava/lang/Throwable; {:try_start_3bc .. :try_end_3c3} :catch_9aa

    move-object/from16 v102, v103

    .line 457
    .end local v103           #throttle:Lcom/android/server/ThrottleService;
    .restart local v102       #throttle:Lcom/android/server/ThrottleService;
    :goto_3c5
    :try_start_3c5
    const-string v3, "SystemServer"

    const-string v9, "UpdateLock Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 458
    const-string v3, "updatelock"

    new-instance v9, Lcom/android/server/UpdateLockService;

    invoke-direct {v9, v4}, Lcom/android/server/UpdateLockService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3d6
    .catch Ljava/lang/Throwable; {:try_start_3c5 .. :try_end_3d6} :catch_7ff

    .line 469
    :goto_3d6
    if-eqz v81, :cond_3db

    .line 470
    invoke-virtual/range {v81 .. v81}, Lcom/android/server/MountService;->waitForAsecScan()V

    .line 474
    :cond_3db
    if-eqz v41, :cond_3e0

    .line 475
    :try_start_3dd
    invoke-virtual/range {v41 .. v41}, Landroid/accounts/AccountManagerService;->systemReady()V
    :try_end_3e0
    .catch Ljava/lang/Throwable; {:try_start_3dd .. :try_end_3e0} :catch_80b

    .line 481
    :cond_3e0
    :goto_3e0
    if-eqz v57, :cond_3e5

    .line 482
    :try_start_3e2
    invoke-virtual/range {v57 .. v57}, Landroid/content/ContentService;->systemReady()V
    :try_end_3e5
    .catch Ljava/lang/Throwable; {:try_start_3e2 .. :try_end_3e5} :catch_817

    .line 488
    :cond_3e5
    :goto_3e5
    :try_start_3e5
    const-string v3, "SystemServer"

    const-string v9, "Notification Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 489
    new-instance v88, Lcom/android/server/NotificationManagerService;

    move-object/from16 v0, v88

    move-object/from16 v1, v100

    move-object/from16 v2, v74

    invoke-direct {v0, v4, v1, v2}, Lcom/android/server/NotificationManagerService;-><init>(Landroid/content/Context;Lcom/android/server/StatusBarManagerService;Lcom/android/server/LightsService;)V
    :try_end_3f7
    .catch Ljava/lang/Throwable; {:try_start_3e5 .. :try_end_3f7} :catch_823

    .line 490
    .end local v87           #notification:Lcom/android/server/NotificationManagerService;
    .local v88, notification:Lcom/android/server/NotificationManagerService;
    :try_start_3f7
    const-string v3, "notification"

    move-object/from16 v0, v88

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 491
    move-object/from16 v0, v88

    invoke-virtual {v8, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->bindNotificationManager(Landroid/app/INotificationManager;)V
    :try_end_403
    .catch Ljava/lang/Throwable; {:try_start_3f7 .. :try_end_403} :catch_9a5

    move-object/from16 v87, v88

    .line 497
    .end local v88           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v87       #notification:Lcom/android/server/NotificationManagerService;
    :goto_405
    :try_start_405
    const-string v3, "SystemServer"

    const-string v9, "Device Storage Monitor"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 498
    const-string v3, "devicestoragemonitor"

    new-instance v9, Lcom/android/server/DeviceStorageMonitorService;

    invoke-direct {v9, v4}, Lcom/android/server/DeviceStorageMonitorService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_416
    .catch Ljava/lang/Throwable; {:try_start_405 .. :try_end_416} :catch_82f

    .line 505
    :goto_416
    :try_start_416
    const-string v3, "SystemServer"

    const-string v9, "Location Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 506
    new-instance v77, Lcom/android/server/LocationManagerService;

    move-object/from16 v0, v77

    invoke-direct {v0, v4}, Lcom/android/server/LocationManagerService;-><init>(Landroid/content/Context;)V
    :try_end_424
    .catch Ljava/lang/Throwable; {:try_start_416 .. :try_end_424} :catch_83b

    .line 507
    .end local v76           #location:Lcom/android/server/LocationManagerService;
    .local v77, location:Lcom/android/server/LocationManagerService;
    :try_start_424
    const-string v3, "location"

    move-object/from16 v0, v77

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_42b
    .catch Ljava/lang/Throwable; {:try_start_424 .. :try_end_42b} :catch_9a0

    move-object/from16 v76, v77

    .line 513
    .end local v77           #location:Lcom/android/server/LocationManagerService;
    .restart local v76       #location:Lcom/android/server/LocationManagerService;
    :goto_42d
    :try_start_42d
    const-string v3, "SystemServer"

    const-string v9, "Country Detector"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 514
    new-instance v59, Lcom/android/server/CountryDetectorService;

    move-object/from16 v0, v59

    invoke-direct {v0, v4}, Lcom/android/server/CountryDetectorService;-><init>(Landroid/content/Context;)V
    :try_end_43b
    .catch Ljava/lang/Throwable; {:try_start_42d .. :try_end_43b} :catch_847

    .line 515
    .end local v58           #countryDetector:Lcom/android/server/CountryDetectorService;
    .local v59, countryDetector:Lcom/android/server/CountryDetectorService;
    :try_start_43b
    const-string v3, "country_detector"

    move-object/from16 v0, v59

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_442
    .catch Ljava/lang/Throwable; {:try_start_43b .. :try_end_442} :catch_99b

    move-object/from16 v58, v59

    .line 521
    .end local v59           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v58       #countryDetector:Lcom/android/server/CountryDetectorService;
    :goto_444
    :try_start_444
    const-string v3, "SystemServer"

    const-string v9, "Search Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 522
    const-string v3, "search"

    new-instance v9, Landroid/server/search/SearchManagerService;

    invoke-direct {v9, v4}, Landroid/server/search/SearchManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_455
    .catch Ljava/lang/Throwable; {:try_start_444 .. :try_end_455} :catch_853

    .line 529
    :goto_455
    :try_start_455
    const-string v3, "SystemServer"

    const-string v9, "DropBox Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 530
    const-string v3, "dropbox"

    new-instance v9, Lcom/android/server/DropBoxManagerService;

    new-instance v10, Ljava/io/File;

    const-string v11, "/data/system/dropbox"

    invoke-direct {v10, v11}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-direct {v9, v4, v10}, Lcom/android/server/DropBoxManagerService;-><init>(Landroid/content/Context;Ljava/io/File;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_46d
    .catch Ljava/lang/Throwable; {:try_start_455 .. :try_end_46d} :catch_85f

    .line 536
    :goto_46d
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v9, 0x1110024

    invoke-virtual {v3, v9}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v3

    if-eqz v3, :cond_493

    .line 539
    :try_start_47a
    const-string v3, "SystemServer"

    const-string v9, "Wallpaper Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 540
    if-nez v16, :cond_493

    .line 541
    new-instance v114, Lcom/android/server/WallpaperManagerService;

    move-object/from16 v0, v114

    invoke-direct {v0, v4}, Lcom/android/server/WallpaperManagerService;-><init>(Landroid/content/Context;)V
    :try_end_48a
    .catch Ljava/lang/Throwable; {:try_start_47a .. :try_end_48a} :catch_86b

    .line 542
    .end local v113           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .local v114, wallpaper:Lcom/android/server/WallpaperManagerService;
    :try_start_48a
    const-string v3, "wallpaper"

    move-object/from16 v0, v114

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_491
    .catch Ljava/lang/Throwable; {:try_start_48a .. :try_end_491} :catch_996

    move-object/from16 v113, v114

    .line 549
    .end local v114           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v113       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :cond_493
    :goto_493
    const-string v3, "0"

    const-string v9, "system_init.startaudioservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4b2

    .line 551
    :try_start_4a1
    const-string v3, "SystemServer"

    const-string v9, "Audio Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 552
    const-string v3, "audio"

    new-instance v9, Landroid/media/AudioService;

    invoke-direct {v9, v4}, Landroid/media/AudioService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_4b2
    .catch Ljava/lang/Throwable; {:try_start_4a1 .. :try_end_4b2} :catch_877

    .line 559
    :cond_4b2
    :goto_4b2
    :try_start_4b2
    const-string v3, "SystemServer"

    const-string v9, "Dock Observer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 561
    new-instance v64, Lcom/android/server/DockObserver;

    move-object/from16 v0, v64

    invoke-direct {v0, v4, v6}, Lcom/android/server/DockObserver;-><init>(Landroid/content/Context;Lcom/android/server/PowerManagerService;)V
    :try_end_4c0
    .catch Ljava/lang/Throwable; {:try_start_4b2 .. :try_end_4c0} :catch_883

    .end local v63           #dock:Lcom/android/server/DockObserver;
    .local v64, dock:Lcom/android/server/DockObserver;
    move-object/from16 v63, v64

    .line 567
    .end local v64           #dock:Lcom/android/server/DockObserver;
    .restart local v63       #dock:Lcom/android/server/DockObserver;
    :goto_4c2
    :try_start_4c2
    const-string v3, "SystemServer"

    const-string v9, "Wired Accessory Observer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 569
    new-instance v3, Lcom/android/server/WiredAccessoryObserver;

    invoke-direct {v3, v4}, Lcom/android/server/WiredAccessoryObserver;-><init>(Landroid/content/Context;)V
    :try_end_4ce
    .catch Ljava/lang/Throwable; {:try_start_4c2 .. :try_end_4ce} :catch_88f

    .line 575
    :goto_4ce
    :try_start_4ce
    const-string v3, "SystemServer"

    const-string v9, "USB Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 577
    new-instance v109, Lcom/android/server/usb/UsbService;

    move-object/from16 v0, v109

    invoke-direct {v0, v4}, Lcom/android/server/usb/UsbService;-><init>(Landroid/content/Context;)V
    :try_end_4dc
    .catch Ljava/lang/Throwable; {:try_start_4ce .. :try_end_4dc} :catch_89b

    .line 578
    .end local v108           #usb:Lcom/android/server/usb/UsbService;
    .local v109, usb:Lcom/android/server/usb/UsbService;
    :try_start_4dc
    const-string v3, "usb"

    move-object/from16 v0, v109

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_4e3
    .catch Ljava/lang/Throwable; {:try_start_4dc .. :try_end_4e3} :catch_991

    move-object/from16 v108, v109

    .line 584
    .end local v109           #usb:Lcom/android/server/usb/UsbService;
    .restart local v108       #usb:Lcom/android/server/usb/UsbService;
    :goto_4e5
    :try_start_4e5
    const-string v3, "SystemServer"

    const-string v9, "Serial Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 586
    new-instance v97, Lcom/android/server/SerialService;

    move-object/from16 v0, v97

    invoke-direct {v0, v4}, Lcom/android/server/SerialService;-><init>(Landroid/content/Context;)V
    :try_end_4f3
    .catch Ljava/lang/Throwable; {:try_start_4e5 .. :try_end_4f3} :catch_8a7

    .line 587
    .end local v96           #serial:Lcom/android/server/SerialService;
    .local v97, serial:Lcom/android/server/SerialService;
    :try_start_4f3
    const-string v3, "serial"

    move-object/from16 v0, v97

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_4fa
    .catch Ljava/lang/Throwable; {:try_start_4f3 .. :try_end_4fa} :catch_98c

    move-object/from16 v96, v97

    .line 593
    .end local v97           #serial:Lcom/android/server/SerialService;
    .restart local v96       #serial:Lcom/android/server/SerialService;
    :goto_4fc
    :try_start_4fc
    const-string v3, "SystemServer"

    const-string v9, "UI Mode Manager Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 595
    new-instance v107, Lcom/android/server/UiModeManagerService;

    move-object/from16 v0, v107

    invoke-direct {v0, v4}, Lcom/android/server/UiModeManagerService;-><init>(Landroid/content/Context;)V
    :try_end_50a
    .catch Ljava/lang/Throwable; {:try_start_4fc .. :try_end_50a} :catch_8b3

    .end local v106           #uiMode:Lcom/android/server/UiModeManagerService;
    .local v107, uiMode:Lcom/android/server/UiModeManagerService;
    move-object/from16 v106, v107

    .line 601
    .end local v107           #uiMode:Lcom/android/server/UiModeManagerService;
    .restart local v106       #uiMode:Lcom/android/server/UiModeManagerService;
    :goto_50c
    :try_start_50c
    const-string v3, "SystemServer"

    const-string v9, "Backup Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 602
    const-string v3, "backup"

    new-instance v9, Lcom/android/server/BackupManagerService;

    invoke-direct {v9, v4}, Lcom/android/server/BackupManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_51d
    .catch Ljava/lang/Throwable; {:try_start_50c .. :try_end_51d} :catch_8bf

    .line 609
    :goto_51d
    :try_start_51d
    const-string v3, "SystemServer"

    const-string v9, "AppWidget Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 610
    new-instance v45, Lcom/android/server/AppWidgetService;

    move-object/from16 v0, v45

    invoke-direct {v0, v4}, Lcom/android/server/AppWidgetService;-><init>(Landroid/content/Context;)V
    :try_end_52b
    .catch Ljava/lang/Throwable; {:try_start_51d .. :try_end_52b} :catch_8cb

    .line 611
    .end local v44           #appWidget:Lcom/android/server/AppWidgetService;
    .local v45, appWidget:Lcom/android/server/AppWidgetService;
    :try_start_52b
    const-string v3, "appwidget"

    move-object/from16 v0, v45

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_532
    .catch Ljava/lang/Throwable; {:try_start_52b .. :try_end_532} :catch_987

    move-object/from16 v44, v45

    .line 617
    .end local v45           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v44       #appWidget:Lcom/android/server/AppWidgetService;
    :goto_534
    :try_start_534
    const-string v3, "SystemServer"

    const-string v9, "Recognition Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 618
    new-instance v95, Lcom/android/server/RecognitionManagerService;

    move-object/from16 v0, v95

    invoke-direct {v0, v4}, Lcom/android/server/RecognitionManagerService;-><init>(Landroid/content/Context;)V
    :try_end_542
    .catch Ljava/lang/Throwable; {:try_start_534 .. :try_end_542} :catch_8d7

    .end local v94           #recognition:Lcom/android/server/RecognitionManagerService;
    .local v95, recognition:Lcom/android/server/RecognitionManagerService;
    move-object/from16 v94, v95

    .line 624
    .end local v95           #recognition:Lcom/android/server/RecognitionManagerService;
    .restart local v94       #recognition:Lcom/android/server/RecognitionManagerService;
    :goto_544
    :try_start_544
    const-string v3, "SystemServer"

    const-string v9, "DiskStats Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 625
    const-string v3, "diskstats"

    new-instance v9, Lcom/android/server/DiskStatsService;

    invoke-direct {v9, v4}, Lcom/android/server/DiskStatsService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_555
    .catch Ljava/lang/Throwable; {:try_start_544 .. :try_end_555} :catch_8e3

    .line 635
    :goto_555
    :try_start_555
    const-string v3, "SystemServer"

    const-string v9, "SamplingProfiler Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 636
    const-string v3, "samplingprofiler"

    new-instance v9, Lcom/android/server/SamplingProfilerService;

    invoke-direct {v9, v4}, Lcom/android/server/SamplingProfilerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_566
    .catch Ljava/lang/Throwable; {:try_start_555 .. :try_end_566} :catch_8ef

    .line 643
    :goto_566
    :try_start_566
    const-string v3, "SystemServer"

    const-string v9, "NetworkTimeUpdateService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 644
    new-instance v86, Lcom/android/server/NetworkTimeUpdateService;

    move-object/from16 v0, v86

    invoke-direct {v0, v4}, Lcom/android/server/NetworkTimeUpdateService;-><init>(Landroid/content/Context;)V
    :try_end_574
    .catch Ljava/lang/Throwable; {:try_start_566 .. :try_end_574} :catch_8fb

    .end local v85           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .local v86, networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    move-object/from16 v85, v86

    .line 650
    .end local v86           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .restart local v85       #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    :goto_576
    :try_start_576
    const-string v3, "SystemServer"

    const-string v9, "CommonTimeManagementService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 651
    new-instance v53, Lcom/android/server/CommonTimeManagementService;

    move-object/from16 v0, v53

    invoke-direct {v0, v4}, Lcom/android/server/CommonTimeManagementService;-><init>(Landroid/content/Context;)V
    :try_end_584
    .catch Ljava/lang/Throwable; {:try_start_576 .. :try_end_584} :catch_907

    .line 652
    .end local v52           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .local v53, commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :try_start_584
    const-string v3, "commontime_management"

    move-object/from16 v0, v53

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_58b
    .catch Ljava/lang/Throwable; {:try_start_584 .. :try_end_58b} :catch_983

    move-object/from16 v52, v53

    .line 658
    .end local v53           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v52       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :goto_58d
    :try_start_58d
    const-string v3, "SystemServer"

    const-string v9, "CertBlacklister"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 659
    new-instance v3, Lcom/android/server/CertBlacklister;

    invoke-direct {v3, v4}, Lcom/android/server/CertBlacklister;-><init>(Landroid/content/Context;)V
    :try_end_599
    .catch Ljava/lang/Throwable; {:try_start_58d .. :try_end_599} :catch_913

    .line 664
    :goto_599
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v9, 0x1110038

    invoke-virtual {v3, v9}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v3

    if-eqz v3, :cond_5bd

    .line 667
    :try_start_5a6
    const-string v3, "SystemServer"

    const-string v9, "Dreams Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 669
    new-instance v66, Landroid/service/dreams/DreamManagerService;

    move-object/from16 v0, v66

    invoke-direct {v0, v4}, Landroid/service/dreams/DreamManagerService;-><init>(Landroid/content/Context;)V
    :try_end_5b4
    .catch Ljava/lang/Throwable; {:try_start_5a6 .. :try_end_5b4} :catch_91f

    .line 670
    .end local v65           #dreamy:Landroid/service/dreams/DreamManagerService;
    .local v66, dreamy:Landroid/service/dreams/DreamManagerService;
    :try_start_5b4
    const-string v3, "dreams"

    move-object/from16 v0, v66

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_5bb
    .catch Ljava/lang/Throwable; {:try_start_5b4 .. :try_end_5bb} :catch_97f

    move-object/from16 v65, v66

    .line 679
    .end local v66           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v81           #mountService:Lcom/android/server/MountService;
    .restart local v65       #dreamy:Landroid/service/dreams/DreamManagerService;
    :cond_5bd
    :goto_5bd
    invoke-virtual/range {v119 .. v119}, Lcom/android/server/wm/WindowManagerService;->detectSafeMode()Z

    move-result v28

    .line 680
    .local v28, safeMode:Z
    if-eqz v28, :cond_92b

    .line 681
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/server/am/ActivityManagerService;->enterSafeMode()V

    .line 683
    const/4 v3, 0x1

    sput-boolean v3, Ldalvik/system/Zygote;->systemInSafeMode:Z

    .line 685
    invoke-static {}, Ldalvik/system/VMRuntime;->getRuntime()Ldalvik/system/VMRuntime;

    move-result-object v3

    invoke-virtual {v3}, Ldalvik/system/VMRuntime;->disableJitCompilation()V

    .line 694
    :goto_5d4
    :try_start_5d4
    invoke-virtual/range {v110 .. v110}, Lcom/android/server/VibratorService;->systemReady()V
    :try_end_5d7
    .catch Ljava/lang/Throwable; {:try_start_5d4 .. :try_end_5d7} :catch_934

    .line 699
    :goto_5d7
    if-eqz v61, :cond_5dc

    .line 701
    :try_start_5d9
    invoke-virtual/range {v61 .. v61}, Lcom/android/server/DevicePolicyManagerService;->systemReady()V
    :try_end_5dc
    .catch Ljava/lang/Throwable; {:try_start_5d9 .. :try_end_5dc} :catch_940

    .line 707
    :cond_5dc
    :goto_5dc
    if-eqz v87, :cond_5e1

    .line 709
    :try_start_5de
    invoke-virtual/range {v87 .. v87}, Lcom/android/server/NotificationManagerService;->systemReady()V
    :try_end_5e1
    .catch Ljava/lang/Throwable; {:try_start_5de .. :try_end_5e1} :catch_94c

    .line 716
    :cond_5e1
    :goto_5e1
    :try_start_5e1
    invoke-virtual/range {v119 .. v119}, Lcom/android/server/wm/WindowManagerService;->systemReady()V
    :try_end_5e4
    .catch Ljava/lang/Throwable; {:try_start_5e1 .. :try_end_5e4} :catch_958

    .line 721
    :goto_5e4
    if-eqz v28, :cond_5ed

    .line 722
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/server/am/ActivityManagerService;->showSafeModeOverlay()V

    .line 728
    :cond_5ed
    invoke-virtual/range {v119 .. v119}, Lcom/android/server/wm/WindowManagerService;->computeNewConfiguration()Landroid/content/res/Configuration;

    move-result-object v54

    .line 729
    .local v54, config:Landroid/content/res/Configuration;
    new-instance v80, Landroid/util/DisplayMetrics;

    invoke-direct/range {v80 .. v80}, Landroid/util/DisplayMetrics;-><init>()V

    .line 730
    .local v80, metrics:Landroid/util/DisplayMetrics;
    const-string v3, "window"

    invoke-virtual {v4, v3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v112

    check-cast v112, Landroid/view/WindowManager;

    .line 731
    .local v112, w:Landroid/view/WindowManager;
    invoke-interface/range {v112 .. v112}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    move-object/from16 v0, v80

    invoke-virtual {v3, v0}, Landroid/view/Display;->getMetrics(Landroid/util/DisplayMetrics;)V

    .line 732
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    move-object/from16 v0, v54

    move-object/from16 v1, v80

    invoke-virtual {v3, v0, v1}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    .line 734
    invoke-virtual {v6}, Lcom/android/server/PowerManagerService;->systemReady()V

    .line 736
    :try_start_615
    invoke-interface/range {v90 .. v90}, Landroid/content/pm/IPackageManager;->systemReady()V
    :try_end_618
    .catch Ljava/lang/Throwable; {:try_start_615 .. :try_end_618} :catch_964

    .line 741
    :goto_618
    :try_start_618
    invoke-virtual/range {v78 .. v78}, Lcom/android/internal/widget/LockSettingsService;->systemReady()V
    :try_end_61b
    .catch Ljava/lang/Throwable; {:try_start_618 .. :try_end_61b} :catch_970

    .line 747
    :goto_61b
    move-object/from16 v17, v4

    .line 748
    .local v17, contextF:Landroid/content/Context;
    move-object/from16 v18, v5

    .line 749
    .local v18, batteryF:Lcom/android/server/BatteryService;
    move-object/from16 v19, v13

    .line 750
    .local v19, networkManagementF:Lcom/android/server/NetworkManagementService;
    move-object/from16 v20, v12

    .line 751
    .local v20, networkStatsF:Lcom/android/server/net/NetworkStatsService;
    move-object/from16 v21, v8

    .line 752
    .local v21, networkPolicyF:Lcom/android/server/net/NetworkPolicyManagerService;
    move-object/from16 v22, v55

    .line 753
    .local v22, connectivityF:Lcom/android/server/ConnectivityService;
    move-object/from16 v23, v63

    .line 754
    .local v23, dockF:Lcom/android/server/DockObserver;
    move-object/from16 v24, v108

    .line 755
    .local v24, usbF:Lcom/android/server/usb/UsbService;
    move-object/from16 v34, v102

    .line 756
    .local v34, throttleF:Lcom/android/server/ThrottleService;
    move-object/from16 v25, v106

    .line 757
    .local v25, uiModeF:Lcom/android/server/UiModeManagerService;
    move-object/from16 v27, v44

    .line 758
    .local v27, appWidgetF:Lcom/android/server/AppWidgetService;
    move-object/from16 v29, v113

    .line 759
    .local v29, wallpaperF:Lcom/android/server/WallpaperManagerService;
    move-object/from16 v30, v71

    .line 760
    .local v30, immF:Lcom/android/server/InputMethodManagerService;
    move-object/from16 v26, v94

    .line 761
    .local v26, recognitionF:Lcom/android/server/RecognitionManagerService;
    move-object/from16 v32, v76

    .line 762
    .local v32, locationF:Lcom/android/server/LocationManagerService;
    move-object/from16 v33, v58

    .line 763
    .local v33, countryDetectorF:Lcom/android/server/CountryDetectorService;
    move-object/from16 v35, v85

    .line 764
    .local v35, networkTimeUpdaterF:Lcom/android/server/NetworkTimeUpdateService;
    move-object/from16 v36, v52

    .line 765
    .local v36, commonTimeMgmtServiceF:Lcom/android/server/CommonTimeManagementService;
    move-object/from16 v37, v104

    .line 766
    .local v37, textServiceManagerServiceF:Lcom/android/server/TextServicesManagerService;
    move-object/from16 v31, v100

    .line 767
    .local v31, statusBarF:Lcom/android/server/StatusBarManagerService;
    move-object/from16 v38, v65

    .line 768
    .local v38, dreamyF:Landroid/service/dreams/DreamManagerService;
    move-object/from16 v39, v73

    .line 769
    .local v39, inputManagerF:Lcom/android/server/input/InputManagerService;
    move-object/from16 v40, v47

    .line 776
    .local v40, bluetoothF:Landroid/server/BluetoothService;
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    new-instance v14, Lcom/android/server/ServerThread$1;

    move-object/from16 v15, p0

    invoke-direct/range {v14 .. v40}, Lcom/android/server/ServerThread$1;-><init>(Lcom/android/server/ServerThread;ZLandroid/content/Context;Lcom/android/server/BatteryService;Lcom/android/server/NetworkManagementService;Lcom/android/server/net/NetworkStatsService;Lcom/android/server/net/NetworkPolicyManagerService;Lcom/android/server/ConnectivityService;Lcom/android/server/DockObserver;Lcom/android/server/usb/UsbService;Lcom/android/server/UiModeManagerService;Lcom/android/server/RecognitionManagerService;Lcom/android/server/AppWidgetService;ZLcom/android/server/WallpaperManagerService;Lcom/android/server/InputMethodManagerService;Lcom/android/server/StatusBarManagerService;Lcom/android/server/LocationManagerService;Lcom/android/server/CountryDetectorService;Lcom/android/server/ThrottleService;Lcom/android/server/NetworkTimeUpdateService;Lcom/android/server/CommonTimeManagementService;Lcom/android/server/TextServicesManagerService;Landroid/service/dreams/DreamManagerService;Lcom/android/server/input/InputManagerService;Landroid/server/BluetoothService;)V

    invoke-virtual {v3, v14}, Lcom/android/server/am/ActivityManagerService;->systemReady(Ljava/lang/Runnable;)V

    .line 890
    invoke-static {}, Landroid/os/StrictMode;->conditionallyEnableDebugLogging()Z

    move-result v3

    if-eqz v3, :cond_664

    .line 891
    const-string v3, "SystemServer"

    const-string v9, "Enabled StrictMode for system server main thread."

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 894
    :cond_664
    invoke-static {}, Landroid/os/Looper;->loop()V

    .line 895
    const-string v3, "SystemServer"

    const-string v9, "System ServerThread is exiting!"

    invoke-static {v3, v9}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 896
    return-void

    .line 102
    .end local v4           #context:Landroid/content/Context;
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v6           #power:Lcom/android/server/PowerManagerService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v8           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v12           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .end local v13           #networkManagement:Lcom/android/server/NetworkManagementService;
    .end local v16           #headless:Z
    .end local v17           #contextF:Landroid/content/Context;
    .end local v18           #batteryF:Lcom/android/server/BatteryService;
    .end local v19           #networkManagementF:Lcom/android/server/NetworkManagementService;
    .end local v20           #networkStatsF:Lcom/android/server/net/NetworkStatsService;
    .end local v21           #networkPolicyF:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v22           #connectivityF:Lcom/android/server/ConnectivityService;
    .end local v23           #dockF:Lcom/android/server/DockObserver;
    .end local v24           #usbF:Lcom/android/server/usb/UsbService;
    .end local v25           #uiModeF:Lcom/android/server/UiModeManagerService;
    .end local v26           #recognitionF:Lcom/android/server/RecognitionManagerService;
    .end local v27           #appWidgetF:Lcom/android/server/AppWidgetService;
    .end local v28           #safeMode:Z
    .end local v29           #wallpaperF:Lcom/android/server/WallpaperManagerService;
    .end local v30           #immF:Lcom/android/server/InputMethodManagerService;
    .end local v31           #statusBarF:Lcom/android/server/StatusBarManagerService;
    .end local v32           #locationF:Lcom/android/server/LocationManagerService;
    .end local v33           #countryDetectorF:Lcom/android/server/CountryDetectorService;
    .end local v34           #throttleF:Lcom/android/server/ThrottleService;
    .end local v35           #networkTimeUpdaterF:Lcom/android/server/NetworkTimeUpdateService;
    .end local v36           #commonTimeMgmtServiceF:Lcom/android/server/CommonTimeManagementService;
    .end local v37           #textServiceManagerServiceF:Lcom/android/server/TextServicesManagerService;
    .end local v38           #dreamyF:Landroid/service/dreams/DreamManagerService;
    .end local v39           #inputManagerF:Lcom/android/server/input/InputManagerService;
    .end local v40           #bluetoothF:Landroid/server/BluetoothService;
    .end local v41           #accountManager:Landroid/accounts/AccountManagerService;
    .end local v44           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v47           #bluetooth:Landroid/server/BluetoothService;
    .end local v49           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .end local v52           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .end local v54           #config:Landroid/content/res/Configuration;
    .end local v55           #connectivity:Lcom/android/server/ConnectivityService;
    .end local v57           #contentService:Landroid/content/ContentService;
    .end local v58           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v61           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v63           #dock:Lcom/android/server/DockObserver;
    .end local v65           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v68           #factoryTest:I
    .end local v69           #factoryTestStr:Ljava/lang/String;
    .end local v71           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v73           #inputManager:Lcom/android/server/input/InputManagerService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v76           #location:Lcom/android/server/LocationManagerService;
    .end local v78           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v80           #metrics:Landroid/util/DisplayMetrics;
    .end local v85           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .end local v87           #notification:Lcom/android/server/NotificationManagerService;
    .end local v90           #pm:Landroid/content/pm/IPackageManager;
    .end local v94           #recognition:Lcom/android/server/RecognitionManagerService;
    .end local v96           #serial:Lcom/android/server/SerialService;
    .end local v98           #serviceDiscovery:Lcom/android/server/NsdService;
    .end local v100           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v102           #throttle:Lcom/android/server/ThrottleService;
    .end local v104           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v106           #uiMode:Lcom/android/server/UiModeManagerService;
    .end local v108           #usb:Lcom/android/server/usb/UsbService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .end local v112           #w:Landroid/view/WindowManager;
    .end local v113           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .end local v115           #wifi:Lcom/android/server/WifiService;
    .end local v117           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .end local v119           #wm:Lcom/android/server/wm/WindowManagerService;
    :cond_66f
    const/16 v93, 0x0

    goto/16 :goto_35

    .line 108
    .restart local v93       #reboot:Z
    :cond_673
    const/16 v92, 0x0

    .restart local v92       #reason:Ljava/lang/String;
    goto/16 :goto_47

    .line 116
    .end local v92           #reason:Ljava/lang/String;
    .end local v93           #reboot:Z
    .restart local v69       #factoryTestStr:Ljava/lang/String;
    :cond_677
    invoke-static/range {v69 .. v69}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v68

    goto/16 :goto_60

    .line 177
    .restart local v4       #context:Landroid/content/Context;
    .restart local v6       #power:Lcom/android/server/PowerManagerService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v13       #networkManagement:Lcom/android/server/NetworkManagementService;
    .restart local v16       #headless:Z
    .restart local v41       #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v47       #bluetooth:Landroid/server/BluetoothService;
    .restart local v49       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v52       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v55       #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v57       #contentService:Landroid/content/ContentService;
    .restart local v60       #cryptState:Ljava/lang/String;
    .restart local v63       #dock:Lcom/android/server/DockObserver;
    .restart local v68       #factoryTest:I
    .restart local v73       #inputManager:Lcom/android/server/input/InputManagerService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    .restart local v83       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v85       #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .restart local v89       #onlyCore:Z
    .restart local v90       #pm:Landroid/content/pm/IPackageManager;
    .restart local v94       #recognition:Lcom/android/server/RecognitionManagerService;
    .restart local v96       #serial:Lcom/android/server/SerialService;
    .restart local v98       #serviceDiscovery:Lcom/android/server/NsdService;
    .restart local v102       #throttle:Lcom/android/server/ThrottleService;
    .restart local v106       #uiMode:Lcom/android/server/UiModeManagerService;
    .restart local v108       #usb:Lcom/android/server/usb/UsbService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    .restart local v115       #wifi:Lcom/android/server/WifiService;
    .restart local v117       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v119       #wm:Lcom/android/server/wm/WindowManagerService;
    :cond_67d
    :try_start_67d
    const-string v3, "1"

    move-object/from16 v0, v60

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_117

    .line 178
    const-string v3, "SystemServer"

    const-string v9, "Device encrypted - only parsing core apps"

    invoke-static {v3, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 179
    const/16 v89, 0x1

    goto/16 :goto_117

    .line 182
    :cond_692
    const/4 v3, 0x0

    goto/16 :goto_11a

    .line 200
    .restart local v70       #firstBoot:Z
    :catch_695
    move-exception v67

    .line 201
    .local v67, e:Ljava/lang/Throwable;
    :goto_696
    const-string v3, "SystemServer"

    const-string v9, "Failure starting Account Manager"

    move-object/from16 v0, v67

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_69f
    .catch Ljava/lang/RuntimeException; {:try_start_67d .. :try_end_69f} :catch_6a1

    goto/16 :goto_148

    .line 271
    .end local v60           #cryptState:Ljava/lang/String;
    .end local v67           #e:Ljava/lang/Throwable;
    .end local v70           #firstBoot:Z
    .end local v89           #onlyCore:Z
    :catch_6a1
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v46

    .line 272
    .end local v46           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .local v67, e:Ljava/lang/RuntimeException;
    :goto_6a6
    const-string v3, "System"

    const-string v9, "******************************************"

    invoke-static {v3, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 273
    const-string v3, "System"

    const-string v9, "************ Failure starting core service"

    move-object/from16 v0, v67

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_210

    .line 205
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v67           #e:Ljava/lang/RuntimeException;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v60       #cryptState:Ljava/lang/String;
    .restart local v70       #firstBoot:Z
    .restart local v89       #onlyCore:Z
    :cond_6b8
    const/4 v3, 0x0

    goto/16 :goto_155

    .line 235
    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v46           #battery:Lcom/android/server/BatteryService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    .restart local v111       #vibrator:Lcom/android/server/VibratorService;
    :cond_6bb
    const/4 v3, 0x0

    move v9, v3

    goto/16 :goto_1d3

    :cond_6bf
    const/4 v3, 0x0

    goto/16 :goto_1d6

    .line 249
    :cond_6c2
    const/4 v3, 0x1

    move/from16 v0, v68

    if-ne v0, v3, :cond_6d6

    .line 250
    :try_start_6c7
    const-string v3, "SystemServer"

    const-string v9, "No Bluetooth Service (factory test)"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_20c

    .line 271
    :catch_6d0
    move-exception v67

    move-object/from16 v110, v111

    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto :goto_6a6

    .line 252
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    .restart local v111       #vibrator:Lcom/android/server/VibratorService;
    :cond_6d6
    const-string v3, "SystemServer"

    const-string v9, "Bluetooth Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 253
    new-instance v48, Landroid/server/BluetoothService;

    move-object/from16 v0, v48

    invoke-direct {v0, v4}, Landroid/server/BluetoothService;-><init>(Landroid/content/Context;)V
    :try_end_6e4
    .catch Ljava/lang/RuntimeException; {:try_start_6c7 .. :try_end_6e4} :catch_6d0

    .line 254
    .end local v47           #bluetooth:Landroid/server/BluetoothService;
    .local v48, bluetooth:Landroid/server/BluetoothService;
    :try_start_6e4
    const-string v3, "bluetooth"

    move-object/from16 v0, v48

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 255
    invoke-virtual/range {v48 .. v48}, Landroid/server/BluetoothService;->initAfterRegistration()V

    .line 257
    const-string v3, "0"

    const-string v9, "system_init.startaudioservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_711

    .line 258
    new-instance v50, Landroid/server/BluetoothA2dpService;

    move-object/from16 v0, v50

    move-object/from16 v1, v48

    invoke-direct {v0, v4, v1}, Landroid/server/BluetoothA2dpService;-><init>(Landroid/content/Context;Landroid/server/BluetoothService;)V
    :try_end_705
    .catch Ljava/lang/RuntimeException; {:try_start_6e4 .. :try_end_705} :catch_a12

    .line 259
    .end local v49           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .local v50, bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    :try_start_705
    const-string v3, "bluetooth_a2dp"

    move-object/from16 v0, v50

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 261
    invoke-virtual/range {v48 .. v48}, Landroid/server/BluetoothService;->initAfterA2dpRegistration()V
    :try_end_70f
    .catch Ljava/lang/RuntimeException; {:try_start_705 .. :try_end_70f} :catch_a1b

    move-object/from16 v49, v50

    .line 264
    .end local v50           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v49       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    :cond_711
    :try_start_711
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/ServerThread;->mContentResolver:Landroid/content/ContentResolver;

    const-string v9, "bluetooth_on"

    const/4 v10, 0x0

    invoke-static {v3, v9, v10}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v51

    .line 266
    .local v51, bluetoothOn:I
    if-eqz v51, :cond_721

    .line 267
    invoke-virtual/range {v48 .. v48}, Landroid/server/BluetoothService;->enable()Z
    :try_end_721
    .catch Ljava/lang/RuntimeException; {:try_start_711 .. :try_end_721} :catch_a12

    :cond_721
    move-object/from16 v47, v48

    .end local v48           #bluetooth:Landroid/server/BluetoothService;
    .restart local v47       #bluetooth:Landroid/server/BluetoothService;
    goto/16 :goto_20c

    .line 294
    .end local v51           #bluetoothOn:I
    .end local v60           #cryptState:Ljava/lang/String;
    .end local v70           #firstBoot:Z
    .end local v75           #lights:Lcom/android/server/LightsService;
    .end local v89           #onlyCore:Z
    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v44       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v58       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v61       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v65       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v71       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    .restart local v76       #location:Lcom/android/server/LocationManagerService;
    .restart local v78       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v87       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v100       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v104       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    .restart local v113       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_725
    move-exception v67

    .line 295
    .local v67, e:Ljava/lang/Throwable;
    :goto_726
    const-string v3, "starting Input Manager Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_244

    .line 302
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_731
    move-exception v67

    .line 303
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Accessibility Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_255

    .line 309
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_73d
    move-exception v67

    .line 310
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making display ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_258

    .line 315
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_749
    move-exception v67

    .line 316
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "performing boot dexopt"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_25b

    .line 338
    .end local v67           #e:Ljava/lang/Throwable;
    .restart local v81       #mountService:Lcom/android/server/MountService;
    :catch_755
    move-exception v67

    .line 339
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_756
    const-string v3, "starting Mount Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_29a

    .line 347
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_761
    move-exception v67

    .line 348
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_762
    const-string v3, "starting LockSettingsService service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2b1

    .line 355
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_76d
    move-exception v67

    .line 356
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_76e
    const-string v3, "starting DevicePolicyService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2c8

    .line 363
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_779
    move-exception v67

    .line 364
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_77a
    const-string v3, "starting StatusBarManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2e1

    .line 371
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_785
    move-exception v67

    .line 372
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Clipboard Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2f2

    .line 379
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_791
    move-exception v67

    .line 380
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting NetworkManagement Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_302

    .line 387
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_79d
    move-exception v67

    .line 388
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_79e
    const-string v3, "starting Text Service Manager Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_319

    .line 395
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7a9
    move-exception v67

    .line 396
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7aa
    const-string v3, "starting NetworkStats Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_330

    .line 405
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7b5
    move-exception v67

    move-object/from16 v8, v83

    .line 406
    .end local v83           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7b8
    const-string v3, "starting NetworkPolicy Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_347

    .line 413
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7c3
    move-exception v67

    .line 414
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7c4
    const-string v3, "starting Wi-Fi P2pService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_35e

    .line 421
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7cf
    move-exception v67

    .line 422
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7d0
    const-string v3, "starting Wi-Fi Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_375

    .line 434
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7db
    move-exception v67

    .line 435
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7dc
    const-string v3, "starting Connectivity Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_39c

    .line 443
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7e7
    move-exception v67

    .line 444
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Service Discovery Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3ae

    .line 452
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7f3
    move-exception v67

    .line 453
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_7f4
    const-string v3, "starting ThrottleService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3c5

    .line 460
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_7ff
    move-exception v67

    .line 461
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting UpdateLockService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3d6

    .line 476
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_80b
    move-exception v67

    .line 477
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Account Manager Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3e0

    .line 483
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_817
    move-exception v67

    .line 484
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Content Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3e5

    .line 492
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_823
    move-exception v67

    .line 493
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_824
    const-string v3, "starting Notification Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_405

    .line 500
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_82f
    move-exception v67

    .line 501
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting DeviceStorageMonitor service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_416

    .line 508
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_83b
    move-exception v67

    .line 509
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_83c
    const-string v3, "starting Location Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_42d

    .line 516
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_847
    move-exception v67

    .line 517
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_848
    const-string v3, "starting Country Detector"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_444

    .line 524
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_853
    move-exception v67

    .line 525
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Search Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_455

    .line 532
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_85f
    move-exception v67

    .line 533
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting DropBoxManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_46d

    .line 544
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_86b
    move-exception v67

    .line 545
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_86c
    const-string v3, "starting Wallpaper Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_493

    .line 553
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_877
    move-exception v67

    .line 554
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Audio Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4b2

    .line 562
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_883
    move-exception v67

    .line 563
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting DockObserver"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4c2

    .line 570
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_88f
    move-exception v67

    .line 571
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting WiredAccessoryObserver"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4ce

    .line 579
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_89b
    move-exception v67

    .line 580
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_89c
    const-string v3, "starting UsbService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4e5

    .line 588
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8a7
    move-exception v67

    .line 589
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_8a8
    const-string v3, "SystemServer"

    const-string v9, "Failure starting SerialService"

    move-object/from16 v0, v67

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_4fc

    .line 596
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8b3
    move-exception v67

    .line 597
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting UiModeManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_50c

    .line 604
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8bf
    move-exception v67

    .line 605
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "SystemServer"

    const-string v9, "Failure starting Backup Service"

    move-object/from16 v0, v67

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_51d

    .line 612
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8cb
    move-exception v67

    .line 613
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_8cc
    const-string v3, "starting AppWidget Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_534

    .line 619
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8d7
    move-exception v67

    .line 620
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting Recognition Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_544

    .line 626
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8e3
    move-exception v67

    .line 627
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting DiskStats Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_555

    .line 638
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8ef
    move-exception v67

    .line 639
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting SamplingProfiler Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_566

    .line 645
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_8fb
    move-exception v67

    .line 646
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting NetworkTimeUpdate service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_576

    .line 653
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_907
    move-exception v67

    .line 654
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_908
    const-string v3, "starting CommonTimeManagementService service"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_58d

    .line 660
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_913
    move-exception v67

    .line 661
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "starting CertBlacklister"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_599

    .line 671
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_91f
    move-exception v67

    .line 672
    .restart local v67       #e:Ljava/lang/Throwable;
    :goto_920
    const-string v3, "starting DreamManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5bd

    .line 688
    .end local v67           #e:Ljava/lang/Throwable;
    .end local v81           #mountService:Lcom/android/server/MountService;
    .restart local v28       #safeMode:Z
    :cond_92b
    invoke-static {}, Ldalvik/system/VMRuntime;->getRuntime()Ldalvik/system/VMRuntime;

    move-result-object v3

    invoke-virtual {v3}, Ldalvik/system/VMRuntime;->startJitCompilation()V

    goto/16 :goto_5d4

    .line 695
    :catch_934
    move-exception v67

    .line 696
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Vibrator Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5d7

    .line 702
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_940
    move-exception v67

    .line 703
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Device Policy Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5dc

    .line 710
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_94c
    move-exception v67

    .line 711
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Notification Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5e1

    .line 717
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_958
    move-exception v67

    .line 718
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Window Manager Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5e4

    .line 737
    .end local v67           #e:Ljava/lang/Throwable;
    .restart local v54       #config:Landroid/content/res/Configuration;
    .restart local v80       #metrics:Landroid/util/DisplayMetrics;
    .restart local v112       #w:Landroid/view/WindowManager;
    :catch_964
    move-exception v67

    .line 738
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Package Manager Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_618

    .line 742
    .end local v67           #e:Ljava/lang/Throwable;
    :catch_970
    move-exception v67

    .line 743
    .restart local v67       #e:Ljava/lang/Throwable;
    const-string v3, "making Lock Settings Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v67

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_61b

    .line 188
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v8           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v28           #safeMode:Z
    .end local v44           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v54           #config:Landroid/content/res/Configuration;
    .end local v58           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v61           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v65           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v67           #e:Ljava/lang/Throwable;
    .end local v71           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v76           #location:Lcom/android/server/LocationManagerService;
    .end local v78           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v80           #metrics:Landroid/util/DisplayMetrics;
    .end local v87           #notification:Lcom/android/server/NotificationManagerService;
    .end local v100           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v104           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v112           #w:Landroid/view/WindowManager;
    .end local v113           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v60       #cryptState:Ljava/lang/String;
    .restart local v70       #firstBoot:Z
    .restart local v83       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v89       #onlyCore:Z
    :catch_97c
    move-exception v3

    goto/16 :goto_126

    .line 671
    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v46           #battery:Lcom/android/server/BatteryService;
    .end local v60           #cryptState:Ljava/lang/String;
    .end local v70           #firstBoot:Z
    .end local v83           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v89           #onlyCore:Z
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v44       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v58       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v61       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v66       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v71       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v76       #location:Lcom/android/server/LocationManagerService;
    .restart local v78       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v81       #mountService:Lcom/android/server/MountService;
    .restart local v87       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v100       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v104       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v113       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_97f
    move-exception v67

    move-object/from16 v65, v66

    .end local v66           #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v65       #dreamy:Landroid/service/dreams/DreamManagerService;
    goto :goto_920

    .line 653
    .end local v52           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v53       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :catch_983
    move-exception v67

    move-object/from16 v52, v53

    .end local v53           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v52       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    goto :goto_908

    .line 612
    .end local v44           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v45       #appWidget:Lcom/android/server/AppWidgetService;
    :catch_987
    move-exception v67

    move-object/from16 v44, v45

    .end local v45           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v44       #appWidget:Lcom/android/server/AppWidgetService;
    goto/16 :goto_8cc

    .line 588
    .end local v96           #serial:Lcom/android/server/SerialService;
    .restart local v97       #serial:Lcom/android/server/SerialService;
    :catch_98c
    move-exception v67

    move-object/from16 v96, v97

    .end local v97           #serial:Lcom/android/server/SerialService;
    .restart local v96       #serial:Lcom/android/server/SerialService;
    goto/16 :goto_8a8

    .line 579
    .end local v108           #usb:Lcom/android/server/usb/UsbService;
    .restart local v109       #usb:Lcom/android/server/usb/UsbService;
    :catch_991
    move-exception v67

    move-object/from16 v108, v109

    .end local v109           #usb:Lcom/android/server/usb/UsbService;
    .restart local v108       #usb:Lcom/android/server/usb/UsbService;
    goto/16 :goto_89c

    .line 544
    .end local v113           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v114       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_996
    move-exception v67

    move-object/from16 v113, v114

    .end local v114           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v113       #wallpaper:Lcom/android/server/WallpaperManagerService;
    goto/16 :goto_86c

    .line 516
    .end local v58           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v59       #countryDetector:Lcom/android/server/CountryDetectorService;
    :catch_99b
    move-exception v67

    move-object/from16 v58, v59

    .end local v59           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v58       #countryDetector:Lcom/android/server/CountryDetectorService;
    goto/16 :goto_848

    .line 508
    .end local v76           #location:Lcom/android/server/LocationManagerService;
    .restart local v77       #location:Lcom/android/server/LocationManagerService;
    :catch_9a0
    move-exception v67

    move-object/from16 v76, v77

    .end local v77           #location:Lcom/android/server/LocationManagerService;
    .restart local v76       #location:Lcom/android/server/LocationManagerService;
    goto/16 :goto_83c

    .line 492
    .end local v87           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v88       #notification:Lcom/android/server/NotificationManagerService;
    :catch_9a5
    move-exception v67

    move-object/from16 v87, v88

    .end local v88           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v87       #notification:Lcom/android/server/NotificationManagerService;
    goto/16 :goto_824

    .line 452
    .end local v102           #throttle:Lcom/android/server/ThrottleService;
    .restart local v103       #throttle:Lcom/android/server/ThrottleService;
    :catch_9aa
    move-exception v67

    move-object/from16 v102, v103

    .end local v103           #throttle:Lcom/android/server/ThrottleService;
    .restart local v102       #throttle:Lcom/android/server/ThrottleService;
    goto/16 :goto_7f4

    .line 434
    .end local v55           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v56       #connectivity:Lcom/android/server/ConnectivityService;
    :catch_9af
    move-exception v67

    move-object/from16 v55, v56

    .end local v56           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v55       #connectivity:Lcom/android/server/ConnectivityService;
    goto/16 :goto_7dc

    .line 421
    .end local v115           #wifi:Lcom/android/server/WifiService;
    .restart local v116       #wifi:Lcom/android/server/WifiService;
    :catch_9b4
    move-exception v67

    move-object/from16 v115, v116

    .end local v116           #wifi:Lcom/android/server/WifiService;
    .restart local v115       #wifi:Lcom/android/server/WifiService;
    goto/16 :goto_7d0

    .line 413
    .end local v117           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v118       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :catch_9b9
    move-exception v67

    move-object/from16 v117, v118

    .end local v118           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v117       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    goto/16 :goto_7c4

    .line 405
    :catch_9be
    move-exception v67

    goto/16 :goto_7b8

    .line 395
    .end local v8           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v12           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v83       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v84       #networkStats:Lcom/android/server/net/NetworkStatsService;
    :catch_9c1
    move-exception v67

    move-object/from16 v12, v84

    .end local v84           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    goto/16 :goto_7aa

    .line 387
    .end local v104           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v105       #tsms:Lcom/android/server/TextServicesManagerService;
    :catch_9c6
    move-exception v67

    move-object/from16 v104, v105

    .end local v105           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v104       #tsms:Lcom/android/server/TextServicesManagerService;
    goto/16 :goto_79e

    .line 363
    .end local v100           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v101       #statusBar:Lcom/android/server/StatusBarManagerService;
    :catch_9cb
    move-exception v67

    move-object/from16 v100, v101

    .end local v101           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v100       #statusBar:Lcom/android/server/StatusBarManagerService;
    goto/16 :goto_77a

    .line 355
    .end local v61           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v62       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :catch_9d0
    move-exception v67

    move-object/from16 v61, v62

    .end local v62           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v61       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    goto/16 :goto_76e

    .line 347
    .end local v78           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v79       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :catch_9d5
    move-exception v67

    move-object/from16 v78, v79

    .end local v79           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v78       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    goto/16 :goto_762

    .line 338
    .end local v81           #mountService:Lcom/android/server/MountService;
    .restart local v82       #mountService:Lcom/android/server/MountService;
    :catch_9da
    move-exception v67

    move-object/from16 v81, v82

    .end local v82           #mountService:Lcom/android/server/MountService;
    .restart local v81       #mountService:Lcom/android/server/MountService;
    goto/16 :goto_756

    .line 324
    .end local v81           #mountService:Lcom/android/server/MountService;
    :catch_9df
    move-exception v3

    goto/16 :goto_26e

    .line 294
    .end local v71           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v72       #imm:Lcom/android/server/InputMethodManagerService;
    :catch_9e2
    move-exception v67

    move-object/from16 v71, v72

    .end local v72           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v71       #imm:Lcom/android/server/InputMethodManagerService;
    goto/16 :goto_726

    .line 271
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v6           #power:Lcom/android/server/PowerManagerService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v44           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v58           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v61           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v65           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v71           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v76           #location:Lcom/android/server/LocationManagerService;
    .end local v78           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v87           #notification:Lcom/android/server/NotificationManagerService;
    .end local v100           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v104           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v113           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v91       #power:Lcom/android/server/PowerManagerService;
    :catch_9e7
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v46

    .end local v46           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    move-object/from16 v6, v91

    .end local v91           #power:Lcom/android/server/PowerManagerService;
    .restart local v6       #power:Lcom/android/server/PowerManagerService;
    goto/16 :goto_6a6

    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v41           #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v42       #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v60       #cryptState:Ljava/lang/String;
    .restart local v70       #firstBoot:Z
    .restart local v89       #onlyCore:Z
    :catch_9f0
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v46

    .end local v46           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    move-object/from16 v41, v42

    .end local v42           #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v41       #accountManager:Landroid/accounts/AccountManagerService;
    goto/16 :goto_6a6

    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    :catch_9f9
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v46

    .end local v46           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_6a6

    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    :catch_a02
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_6a6

    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    .restart local v111       #vibrator:Lcom/android/server/VibratorService;
    :catch_a09
    move-exception v67

    move-object/from16 v7, v43

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v110, v111

    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_6a6

    .end local v47           #bluetooth:Landroid/server/BluetoothService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .restart local v48       #bluetooth:Landroid/server/BluetoothService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    .restart local v111       #vibrator:Lcom/android/server/VibratorService;
    :catch_a12
    move-exception v67

    move-object/from16 v47, v48

    .end local v48           #bluetooth:Landroid/server/BluetoothService;
    .restart local v47       #bluetooth:Landroid/server/BluetoothService;
    move-object/from16 v110, v111

    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_6a6

    .end local v47           #bluetooth:Landroid/server/BluetoothService;
    .end local v49           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .end local v74           #lights:Lcom/android/server/LightsService;
    .end local v110           #vibrator:Lcom/android/server/VibratorService;
    .restart local v48       #bluetooth:Landroid/server/BluetoothService;
    .restart local v50       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v75       #lights:Lcom/android/server/LightsService;
    .restart local v111       #vibrator:Lcom/android/server/VibratorService;
    :catch_a1b
    move-exception v67

    move-object/from16 v49, v50

    .end local v50           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v49       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    move-object/from16 v47, v48

    .end local v48           #bluetooth:Landroid/server/BluetoothService;
    .restart local v47       #bluetooth:Landroid/server/BluetoothService;
    move-object/from16 v110, v111

    .end local v111           #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v74, v75

    .end local v75           #lights:Lcom/android/server/LightsService;
    .restart local v74       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_6a6

    .line 200
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v41           #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v42       #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v43       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v46       #battery:Lcom/android/server/BatteryService;
    :catch_a26
    move-exception v67

    move-object/from16 v41, v42

    .end local v42           #accountManager:Landroid/accounts/AccountManagerService;
    .restart local v41       #accountManager:Landroid/accounts/AccountManagerService;
    goto/16 :goto_696

    .end local v43           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v46           #battery:Lcom/android/server/BatteryService;
    .end local v60           #cryptState:Ljava/lang/String;
    .end local v70           #firstBoot:Z
    .end local v89           #onlyCore:Z
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v58       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v61       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v65       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v71       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v76       #location:Lcom/android/server/LocationManagerService;
    .restart local v78       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v87       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v100       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v104       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v113       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :cond_a2b
    move-object/from16 v8, v83

    .end local v83           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    goto/16 :goto_5bd
.end method
