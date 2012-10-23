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
    .line 72
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method

.method static final startSystemUi(Landroid/content/Context;)V
    .registers 5
    .parameter "context"

    .prologue
    .line 884
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 885
    .local v0, intent:Landroid/content/Intent;
    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.android.systemui"

    const-string v3, "com.android.systemui.SystemUIService"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 887
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

    .line 888
    invoke-virtual {p0, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 889
    return-void
.end method


# virtual methods
.method reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V
    .registers 6
    .parameter "msg"
    .parameter "e"

    .prologue
    .line 80
    const-string v0, "SystemServer"

    const-string v1, "***********************************************"

    invoke-static {v0, v1}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 81
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

    .line 82
    return-void
.end method

.method public run()V
    .registers 118
    .annotation build Landroid/annotation/MiuiHook;
        value = .enum Landroid/annotation/MiuiHook$MiuiHookType;->CHANGE_CODE:Landroid/annotation/MiuiHook$MiuiHookType;
    .end annotation

    .prologue
    .line 87
    const/16 v3, 0xbc2

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v9

    invoke-static {v3, v9, v10}, Landroid/util/EventLog;->writeEvent(IJ)I

    .line 90
    invoke-static {}, Landroid/os/Looper;->prepare()V

    .line 92
    const/4 v3, -0x2

    invoke-static {v3}, Landroid/os/Process;->setThreadPriority(I)V

    .line 95
    const/4 v3, 0x1

    invoke-static {v3}, Lcom/android/internal/os/BinderInternal;->disableBackgroundScheduling(Z)V

    .line 96
    const/4 v3, 0x0

    invoke-static {v3}, Landroid/os/Process;->setCanSelfBackground(Z)V

    .line 100
    const-string v3, "sys.shutdown.requested"

    const-string v9, ""

    invoke-static {v3, v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v96

    .line 102
    .local v96, shutdownAction:Ljava/lang/String;
    if-eqz v96, :cond_4e

    invoke-virtual/range {v96 .. v96}, Ljava/lang/String;->length()I

    move-result v3

    if-lez v3, :cond_4e

    .line 103
    const/4 v3, 0x0

    move-object/from16 v0, v96

    invoke-virtual {v0, v3}, Ljava/lang/String;->charAt(I)C

    move-result v3

    const/16 v9, 0x31

    if-ne v3, v9, :cond_65a

    const/16 v90, 0x1

    .line 106
    .local v90, reboot:Z
    :goto_35
    invoke-virtual/range {v96 .. v96}, Ljava/lang/String;->length()I

    move-result v3

    const/4 v9, 0x1

    if-le v3, v9, :cond_65e

    .line 107
    const/4 v3, 0x1

    invoke-virtual/range {v96 .. v96}, Ljava/lang/String;->length()I

    move-result v9

    move-object/from16 v0, v96

    invoke-virtual {v0, v3, v9}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v89

    .line 112
    .local v89, reason:Ljava/lang/String;
    :goto_47
    move/from16 v0, v90

    move-object/from16 v1, v89

    invoke-static {v0, v1}, Lcom/android/server/pm/ShutdownThread;->rebootOrShutdown(ZLjava/lang/String;)V

    .line 116
    .end local v89           #reason:Ljava/lang/String;
    .end local v90           #reboot:Z
    :cond_4e
    const-string v3, "ro.factorytest"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v66

    .line 117
    .local v66, factoryTestStr:Ljava/lang/String;
    const-string v3, ""

    move-object/from16 v0, v66

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_662

    const/16 v65, 0x0

    .line 119
    .local v65, factoryTest:I
    :goto_60
    const-string v3, "1"

    const-string v9, "ro.config.headless"

    const-string v10, "0"

    invoke-static {v9, v10}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v16

    .line 121
    .local v16, headless:Z
    const/16 v71, 0x0

    .line 122
    .local v71, lights:Lcom/android/server/LightsService;
    const/16 v88, 0x0

    .line 123
    .local v88, power:Lcom/android/server/PowerManagerService;
    const/16 v44, 0x0

    .line 124
    .local v44, battery:Lcom/android/server/BatteryService;
    const/16 v107, 0x0

    .line 125
    .local v107, vibrator:Lcom/android/server/VibratorService;
    const/16 v41, 0x0

    .line 126
    .local v41, alarm:Lcom/android/server/AlarmManagerService;
    const/4 v13, 0x0

    .line 127
    .local v13, networkManagement:Lcom/android/server/NetworkManagementService;
    const/4 v12, 0x0

    .line 128
    .local v12, networkStats:Lcom/android/server/net/NetworkStatsService;
    const/16 v80, 0x0

    .line 129
    .local v80, networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    const/16 v53, 0x0

    .line 130
    .local v53, connectivity:Lcom/android/server/ConnectivityService;
    const/16 v114, 0x0

    .line 131
    .local v114, wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    const/16 v112, 0x0

    .line 132
    .local v112, wifi:Lcom/android/server/WifiService;
    const/16 v95, 0x0

    .line 133
    .local v95, serviceDiscovery:Lcom/android/server/NsdService;
    const/16 v87, 0x0

    .line 134
    .local v87, pm:Landroid/content/pm/IPackageManager;
    const/4 v4, 0x0

    .line 135
    .local v4, context:Landroid/content/Context;
    const/16 v116, 0x0

    .line 136
    .local v116, wm:Lcom/android/server/wm/WindowManagerService;
    const/16 v45, 0x0

    .line 137
    .local v45, bluetooth:Landroid/server/BluetoothService;
    const/16 v47, 0x0

    .line 138
    .local v47, bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    const/16 v60, 0x0

    .line 139
    .local v60, dock:Lcom/android/server/DockObserver;
    const/16 v105, 0x0

    .line 140
    .local v105, usb:Lcom/android/server/usb/UsbService;
    const/16 v93, 0x0

    .line 141
    .local v93, serial:Lcom/android/server/SerialService;
    const/16 v103, 0x0

    .line 142
    .local v103, uiMode:Lcom/android/server/UiModeManagerService;
    const/16 v91, 0x0

    .line 143
    .local v91, recognition:Lcom/android/server/RecognitionManagerService;
    const/16 v99, 0x0

    .line 144
    .local v99, throttle:Lcom/android/server/ThrottleService;
    const/16 v82, 0x0

    .line 145
    .local v82, networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    const/16 v50, 0x0

    .line 146
    .local v50, commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    const/16 v70, 0x0

    .line 150
    .local v70, inputManager:Lcom/android/server/input/InputManagerService;
    :try_start_9f
    const-string v3, "SystemServer"

    const-string v9, "Entropy Mixer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 151
    const-string v3, "entropy"

    new-instance v9, Lcom/android/server/EntropyMixer;

    invoke-direct {v9}, Lcom/android/server/EntropyMixer;-><init>()V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 153
    const-string v3, "SystemServer"

    const-string v9, "Power Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 154
    new-instance v6, Lcom/android/server/PowerManagerService;

    invoke-direct {v6}, Lcom/android/server/PowerManagerService;-><init>()V
    :try_end_bc
    .catch Ljava/lang/RuntimeException; {:try_start_9f .. :try_end_bc} :catch_9ba

    .line 155
    .end local v88           #power:Lcom/android/server/PowerManagerService;
    .local v6, power:Lcom/android/server/PowerManagerService;
    :try_start_bc
    const-string v3, "power"

    invoke-static {v3, v6}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 157
    const-string v3, "SystemServer"

    const-string v9, "Activity Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 158
    invoke-static/range {v65 .. v65}, Lcom/android/server/am/ActivityManagerService;->main(I)Landroid/content/Context;

    move-result-object v4

    .line 160
    const-string v3, "SystemServer"

    const-string v9, "Telephony Registry"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 161
    const-string v3, "telephony.registry"

    new-instance v9, Lcom/android/server/TelephonyRegistry;

    invoke-direct {v9, v4}, Lcom/android/server/TelephonyRegistry;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 163
    const-string v3, "SystemServer"

    const-string v9, "Scheduling Policy"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 164
    const-string v3, "scheduling_policy"

    new-instance v9, Landroid/os/SchedulingPolicyService;

    invoke-direct {v9}, Landroid/os/SchedulingPolicyService;-><init>()V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 167
    invoke-static {v4}, Lcom/android/server/AttributeCache;->init(Landroid/content/Context;)V

    .line 169
    const-string v3, "SystemServer"

    const-string v9, "Package Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 171
    const-string v3, "vold.decrypt"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v57

    .line 172
    .local v57, cryptState:Ljava/lang/String;
    const/16 v86, 0x0

    .line 173
    .local v86, onlyCore:Z
    const-string v3, "trigger_restart_min_framework"

    move-object/from16 v0, v57

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_668

    .line 174
    const-string v3, "SystemServer"

    const-string v9, "Detected encryption in progress - only parsing core apps"

    invoke-static {v3, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 175
    const/16 v86, 0x1

    .line 181
    :cond_113
    :goto_113
    if-eqz v65, :cond_67d

    const/4 v3, 0x1

    :goto_116
    move/from16 v0, v86

    invoke-static {v4, v3, v0}, Lcom/android/server/pm/PackageManagerService;->main(Landroid/content/Context;ZZ)Landroid/content/pm/IPackageManager;
    :try_end_11b
    .catch Ljava/lang/RuntimeException; {:try_start_bc .. :try_end_11b} :catch_68c

    move-result-object v87

    .line 184
    const/16 v67, 0x0

    .line 186
    .local v67, firstBoot:Z
    :try_start_11e
    invoke-interface/range {v87 .. v87}, Landroid/content/pm/IPackageManager;->isFirstBoot()Z
    :try_end_121
    .catch Landroid/os/RemoteException; {:try_start_11e .. :try_end_121} :catch_94f
    .catch Ljava/lang/RuntimeException; {:try_start_11e .. :try_end_121} :catch_68c

    move-result v67

    .line 190
    :goto_122
    :try_start_122
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->setSystemProcess()V

    .line 192
    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    move-object/from16 v0, p0

    iput-object v3, v0, Lcom/android/server/ServerThread;->mContentResolver:Landroid/content/ContentResolver;
    :try_end_12d
    .catch Ljava/lang/RuntimeException; {:try_start_122 .. :try_end_12d} :catch_68c

    .line 196
    :try_start_12d
    const-string v3, "SystemServer"

    const-string v9, "Account Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 197
    const-string v3, "account"

    new-instance v9, Landroid/accounts/AccountManagerService;

    invoke-direct {v9, v4}, Landroid/accounts/AccountManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_13e
    .catch Ljava/lang/Throwable; {:try_start_12d .. :try_end_13e} :catch_680
    .catch Ljava/lang/RuntimeException; {:try_start_12d .. :try_end_13e} :catch_68c

    .line 203
    :goto_13e
    :try_start_13e
    const-string v3, "SystemServer"

    const-string v9, "Content Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 204
    const/4 v3, 0x1

    move/from16 v0, v65

    if-ne v0, v3, :cond_6a3

    const/4 v3, 0x1

    :goto_14b
    invoke-static {v4, v3}, Landroid/content/ContentService;->main(Landroid/content/Context;Z)Landroid/content/IContentService;

    .line 207
    const-string v3, "SystemServer"

    const-string v9, "System Content Providers"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 208
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->installSystemProviders()V

    .line 210
    const-string v3, "SystemServer"

    const-string v9, "Lights Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 211
    new-instance v72, Lcom/android/server/MiuiLightsService;

    move-object/from16 v0, v72

    invoke-direct {v0, v4}, Lcom/android/server/MiuiLightsService;-><init>(Landroid/content/Context;)V
    :try_end_166
    .catch Ljava/lang/RuntimeException; {:try_start_13e .. :try_end_166} :catch_68c

    .line 213
    .end local v71           #lights:Lcom/android/server/LightsService;
    .local v72, lights:Lcom/android/server/LightsService;
    :try_start_166
    const-string v3, "SystemServer"

    const-string v9, "Battery Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 214
    new-instance v5, Lcom/android/server/BatteryService;

    move-object/from16 v0, v72

    invoke-direct {v5, v4, v0}, Lcom/android/server/BatteryService;-><init>(Landroid/content/Context;Lcom/android/server/LightsService;)V
    :try_end_174
    .catch Ljava/lang/RuntimeException; {:try_start_166 .. :try_end_174} :catch_9c3

    .line 215
    .end local v44           #battery:Lcom/android/server/BatteryService;
    .local v5, battery:Lcom/android/server/BatteryService;
    :try_start_174
    const-string v3, "battery"

    invoke-static {v3, v5}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 217
    const-string v3, "SystemServer"

    const-string v9, "Vibrator Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 218
    new-instance v108, Lcom/android/server/VibratorService;

    move-object/from16 v0, v108

    invoke-direct {v0, v4}, Lcom/android/server/VibratorService;-><init>(Landroid/content/Context;)V
    :try_end_187
    .catch Ljava/lang/RuntimeException; {:try_start_174 .. :try_end_187} :catch_9cc

    .line 219
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .local v108, vibrator:Lcom/android/server/VibratorService;
    :try_start_187
    const-string v3, "vibrator"

    move-object/from16 v0, v108

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 223
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    move-object/from16 v0, v72

    invoke-virtual {v6, v4, v0, v3, v5}, Lcom/android/server/PowerManagerService;->init(Landroid/content/Context;Lcom/android/server/LightsService;Landroid/app/IActivityManager;Lcom/android/server/BatteryService;)V

    .line 225
    const-string v3, "SystemServer"

    const-string v9, "Alarm Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 226
    new-instance v7, Lcom/android/server/AlarmManagerService;

    invoke-direct {v7, v4}, Lcom/android/server/AlarmManagerService;-><init>(Landroid/content/Context;)V
    :try_end_1a3
    .catch Ljava/lang/RuntimeException; {:try_start_187 .. :try_end_1a3} :catch_9d3

    .line 227
    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .local v7, alarm:Lcom/android/server/AlarmManagerService;
    :try_start_1a3
    const-string v3, "alarm"

    invoke-static {v3, v7}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 229
    const-string v3, "SystemServer"

    const-string v9, "Init Watchdog"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 230
    invoke-static {}, Lcom/android/server/Watchdog;->getInstance()Lcom/android/server/Watchdog;

    move-result-object v3

    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v8

    invoke-virtual/range {v3 .. v8}, Lcom/android/server/Watchdog;->init(Landroid/content/Context;Lcom/android/server/BatteryService;Lcom/android/server/PowerManagerService;Lcom/android/server/AlarmManagerService;Lcom/android/server/am/ActivityManagerService;)V

    .line 233
    const-string v3, "SystemServer"

    const-string v9, "Window Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 234
    const/4 v3, 0x1

    move/from16 v0, v65

    if-eq v0, v3, :cond_6a6

    const/4 v3, 0x1

    move v9, v3

    :goto_1c8
    if-nez v67, :cond_6aa

    const/4 v3, 0x1

    :goto_1cb
    move/from16 v0, v86

    invoke-static {v4, v6, v9, v3, v0}, Lcom/android/server/wm/WindowManagerService;->main(Landroid/content/Context;Lcom/android/server/PowerManagerService;ZZZ)Lcom/android/server/wm/WindowManagerService;

    move-result-object v116

    .line 237
    const-string v3, "window"

    move-object/from16 v0, v116

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 238
    invoke-virtual/range {v116 .. v116}, Lcom/android/server/wm/WindowManagerService;->getInputManagerService()Lcom/android/server/input/InputManagerService;

    move-result-object v70

    .line 239
    const-string v3, "input"

    move-object/from16 v0, v70

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 241
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    move-object/from16 v0, v116

    invoke-virtual {v3, v0}, Lcom/android/server/am/ActivityManagerService;->setWindowManager(Lcom/android/server/wm/WindowManagerService;)V

    .line 246
    const-string v3, "ro.kernel.qemu"

    invoke-static {v3}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const-string v9, "1"

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_6ad

    .line 247
    const-string v3, "SystemServer"

    const-string v9, "No Bluetooh Service (emulator)"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_201
    .catch Ljava/lang/RuntimeException; {:try_start_1a3 .. :try_end_201} :catch_6bb

    :goto_201
    move-object/from16 v107, v108

    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v71, v72

    .line 275
    .end local v57           #cryptState:Ljava/lang/String;
    .end local v67           #firstBoot:Z
    .end local v72           #lights:Lcom/android/server/LightsService;
    .end local v86           #onlyCore:Z
    .restart local v71       #lights:Lcom/android/server/LightsService;
    :goto_205
    const/16 v58, 0x0

    .line 276
    .local v58, devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    const/16 v97, 0x0

    .line 277
    .local v97, statusBar:Lcom/android/server/StatusBarManagerService;
    const/16 v68, 0x0

    .line 278
    .local v68, imm:Lcom/android/server/InputMethodManagerService;
    const/16 v42, 0x0

    .line 279
    .local v42, appWidget:Lcom/android/server/AppWidgetService;
    const/16 v84, 0x0

    .line 280
    .local v84, notification:Lcom/android/server/NotificationManagerService;
    const/16 v110, 0x0

    .line 281
    .local v110, wallpaper:Lcom/android/server/WallpaperManagerService;
    const/16 v73, 0x0

    .line 282
    .local v73, location:Lcom/android/server/LocationManagerService;
    const/16 v55, 0x0

    .line 283
    .local v55, countryDetector:Lcom/android/server/CountryDetectorService;
    const/16 v101, 0x0

    .line 284
    .local v101, tsms:Lcom/android/server/TextServicesManagerService;
    const/16 v75, 0x0

    .line 285
    .local v75, lockSettings:Lcom/android/internal/widget/LockSettingsService;
    const/16 v62, 0x0

    .line 288
    .local v62, dreamy:Landroid/service/dreams/DreamManagerService;
    const/4 v3, 0x1

    move/from16 v0, v65

    if-eq v0, v3, :cond_24a

    .line 290
    :try_start_220
    const-string v3, "SystemServer"

    const-string v9, "Input Method Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 291
    new-instance v69, Lcom/android/server/InputMethodManagerService;

    move-object/from16 v0, v69

    move-object/from16 v1, v116

    invoke-direct {v0, v4, v1}, Lcom/android/server/InputMethodManagerService;-><init>(Landroid/content/Context;Lcom/android/server/wm/WindowManagerService;)V
    :try_end_230
    .catch Ljava/lang/Throwable; {:try_start_220 .. :try_end_230} :catch_710

    .line 292
    .end local v68           #imm:Lcom/android/server/InputMethodManagerService;
    .local v69, imm:Lcom/android/server/InputMethodManagerService;
    :try_start_230
    const-string v3, "input_method"

    move-object/from16 v0, v69

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_237
    .catch Ljava/lang/Throwable; {:try_start_230 .. :try_end_237} :catch_9b5

    move-object/from16 v68, v69

    .line 298
    .end local v69           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v68       #imm:Lcom/android/server/InputMethodManagerService;
    :goto_239
    :try_start_239
    const-string v3, "SystemServer"

    const-string v9, "Accessibility Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 299
    const-string v3, "accessibility"

    new-instance v9, Lcom/android/server/accessibility/AccessibilityManagerService;

    invoke-direct {v9, v4}, Lcom/android/server/accessibility/AccessibilityManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_24a
    .catch Ljava/lang/Throwable; {:try_start_239 .. :try_end_24a} :catch_71c

    .line 307
    :cond_24a
    :goto_24a
    :try_start_24a
    invoke-virtual/range {v116 .. v116}, Lcom/android/server/wm/WindowManagerService;->displayReady()V
    :try_end_24d
    .catch Ljava/lang/Throwable; {:try_start_24a .. :try_end_24d} :catch_728

    .line 313
    :goto_24d
    :try_start_24d
    invoke-interface/range {v87 .. v87}, Landroid/content/pm/IPackageManager;->performBootDexOpt()V
    :try_end_250
    .catch Ljava/lang/Throwable; {:try_start_24d .. :try_end_250} :catch_734

    .line 319
    :goto_250
    :try_start_250
    invoke-static {}, Landroid/app/ActivityManagerNative;->getDefault()Landroid/app/IActivityManager;

    move-result-object v3

    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v9

    const v10, 0x10403e5

    invoke-virtual {v9, v10}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v9

    const/4 v10, 0x0

    invoke-interface {v3, v9, v10}, Landroid/app/IActivityManager;->showBootMessage(Ljava/lang/CharSequence;Z)V
    :try_end_263
    .catch Landroid/os/RemoteException; {:try_start_250 .. :try_end_263} :catch_9b2

    .line 326
    :goto_263
    const/4 v3, 0x1

    move/from16 v0, v65

    if-eq v0, v3, :cond_9f0

    .line 327
    const/16 v78, 0x0

    .line 328
    .local v78, mountService:Lcom/android/server/MountService;
    const-string v3, "0"

    const-string v9, "system_init.startmountservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_28f

    .line 334
    :try_start_278
    const-string v3, "SystemServer"

    const-string v9, "Mount Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 335
    new-instance v79, Lcom/android/server/MountService;

    move-object/from16 v0, v79

    invoke-direct {v0, v4}, Lcom/android/server/MountService;-><init>(Landroid/content/Context;)V
    :try_end_286
    .catch Ljava/lang/Throwable; {:try_start_278 .. :try_end_286} :catch_740

    .line 336
    .end local v78           #mountService:Lcom/android/server/MountService;
    .local v79, mountService:Lcom/android/server/MountService;
    :try_start_286
    const-string v3, "mount"

    move-object/from16 v0, v79

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_28d
    .catch Ljava/lang/Throwable; {:try_start_286 .. :try_end_28d} :catch_9ad

    move-object/from16 v78, v79

    .line 343
    .end local v79           #mountService:Lcom/android/server/MountService;
    .restart local v78       #mountService:Lcom/android/server/MountService;
    :cond_28f
    :goto_28f
    :try_start_28f
    const-string v3, "SystemServer"

    const-string v9, "LockSettingsService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 344
    new-instance v76, Lcom/android/internal/widget/LockSettingsService;

    move-object/from16 v0, v76

    invoke-direct {v0, v4}, Lcom/android/internal/widget/LockSettingsService;-><init>(Landroid/content/Context;)V
    :try_end_29d
    .catch Ljava/lang/Throwable; {:try_start_28f .. :try_end_29d} :catch_74c

    .line 345
    .end local v75           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .local v76, lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :try_start_29d
    const-string v3, "lock_settings"

    move-object/from16 v0, v76

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2a4
    .catch Ljava/lang/Throwable; {:try_start_29d .. :try_end_2a4} :catch_9a8

    move-object/from16 v75, v76

    .line 351
    .end local v76           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v75       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :goto_2a6
    :try_start_2a6
    const-string v3, "SystemServer"

    const-string v9, "Device Policy"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 352
    new-instance v59, Lcom/android/server/DevicePolicyManagerService;

    move-object/from16 v0, v59

    invoke-direct {v0, v4}, Lcom/android/server/DevicePolicyManagerService;-><init>(Landroid/content/Context;)V
    :try_end_2b4
    .catch Ljava/lang/Throwable; {:try_start_2a6 .. :try_end_2b4} :catch_758

    .line 353
    .end local v58           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .local v59, devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :try_start_2b4
    const-string v3, "device_policy"

    move-object/from16 v0, v59

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2bb
    .catch Ljava/lang/Throwable; {:try_start_2b4 .. :try_end_2bb} :catch_9a3

    move-object/from16 v58, v59

    .line 359
    .end local v59           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v58       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :goto_2bd
    :try_start_2bd
    const-string v3, "SystemServer"

    const-string v9, "Status Bar"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 360
    new-instance v98, Lcom/android/server/StatusBarManagerService;

    move-object/from16 v0, v98

    move-object/from16 v1, v116

    invoke-direct {v0, v4, v1}, Lcom/android/server/StatusBarManagerService;-><init>(Landroid/content/Context;Lcom/android/server/wm/WindowManagerService;)V
    :try_end_2cd
    .catch Ljava/lang/Throwable; {:try_start_2bd .. :try_end_2cd} :catch_764

    .line 361
    .end local v97           #statusBar:Lcom/android/server/StatusBarManagerService;
    .local v98, statusBar:Lcom/android/server/StatusBarManagerService;
    :try_start_2cd
    const-string v3, "statusbar"

    move-object/from16 v0, v98

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2d4
    .catch Ljava/lang/Throwable; {:try_start_2cd .. :try_end_2d4} :catch_99e

    move-object/from16 v97, v98

    .line 367
    .end local v98           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v97       #statusBar:Lcom/android/server/StatusBarManagerService;
    :goto_2d6
    :try_start_2d6
    const-string v3, "SystemServer"

    const-string v9, "Clipboard Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 368
    const-string v3, "clipboard"

    new-instance v9, Lcom/android/server/ClipboardService;

    invoke-direct {v9, v4}, Lcom/android/server/ClipboardService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2e7
    .catch Ljava/lang/Throwable; {:try_start_2d6 .. :try_end_2e7} :catch_770

    .line 375
    :goto_2e7
    :try_start_2e7
    const-string v3, "SystemServer"

    const-string v9, "NetworkManagement Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 376
    invoke-static {v4}, Lcom/android/server/NetworkManagementService;->create(Landroid/content/Context;)Lcom/android/server/NetworkManagementService;

    move-result-object v13

    .line 377
    const-string v3, "network_management"

    invoke-static {v3, v13}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_2f7
    .catch Ljava/lang/Throwable; {:try_start_2e7 .. :try_end_2f7} :catch_77c

    .line 383
    :goto_2f7
    :try_start_2f7
    const-string v3, "SystemServer"

    const-string v9, "Text Service Manager Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 384
    new-instance v102, Lcom/android/server/TextServicesManagerService;

    move-object/from16 v0, v102

    invoke-direct {v0, v4}, Lcom/android/server/TextServicesManagerService;-><init>(Landroid/content/Context;)V
    :try_end_305
    .catch Ljava/lang/Throwable; {:try_start_2f7 .. :try_end_305} :catch_788

    .line 385
    .end local v101           #tsms:Lcom/android/server/TextServicesManagerService;
    .local v102, tsms:Lcom/android/server/TextServicesManagerService;
    :try_start_305
    const-string v3, "textservices"

    move-object/from16 v0, v102

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_30c
    .catch Ljava/lang/Throwable; {:try_start_305 .. :try_end_30c} :catch_999

    move-object/from16 v101, v102

    .line 391
    .end local v102           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v101       #tsms:Lcom/android/server/TextServicesManagerService;
    :goto_30e
    :try_start_30e
    const-string v3, "SystemServer"

    const-string v9, "NetworkStats Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 392
    new-instance v81, Lcom/android/server/net/NetworkStatsService;

    move-object/from16 v0, v81

    invoke-direct {v0, v4, v13, v7}, Lcom/android/server/net/NetworkStatsService;-><init>(Landroid/content/Context;Landroid/os/INetworkManagementService;Landroid/app/IAlarmManager;)V
    :try_end_31c
    .catch Ljava/lang/Throwable; {:try_start_30e .. :try_end_31c} :catch_794

    .line 393
    .end local v12           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .local v81, networkStats:Lcom/android/server/net/NetworkStatsService;
    :try_start_31c
    const-string v3, "netstats"

    move-object/from16 v0, v81

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_323
    .catch Ljava/lang/Throwable; {:try_start_31c .. :try_end_323} :catch_994

    move-object/from16 v12, v81

    .line 399
    .end local v81           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    :goto_325
    :try_start_325
    const-string v3, "SystemServer"

    const-string v9, "NetworkPolicy Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 400
    new-instance v8, Lcom/android/server/net/MiuiNetworkPolicyManagerService;

    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v10

    move-object v9, v4

    move-object v11, v6

    invoke-direct/range {v8 .. v13}, Lcom/android/server/net/MiuiNetworkPolicyManagerService;-><init>(Landroid/content/Context;Landroid/app/IActivityManager;Landroid/os/IPowerManager;Landroid/net/INetworkStatsService;Landroid/os/INetworkManagementService;)V
    :try_end_337
    .catch Ljava/lang/Throwable; {:try_start_325 .. :try_end_337} :catch_7a0

    .line 403
    .end local v80           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .local v8, networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    :try_start_337
    const-string v3, "netpolicy"

    invoke-static {v3, v8}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_33c
    .catch Ljava/lang/Throwable; {:try_start_337 .. :try_end_33c} :catch_991

    .line 409
    :goto_33c
    :try_start_33c
    const-string v3, "SystemServer"

    const-string v9, "Wi-Fi P2pService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 410
    new-instance v115, Landroid/net/wifi/p2p/WifiP2pService;

    move-object/from16 v0, v115

    invoke-direct {v0, v4}, Landroid/net/wifi/p2p/WifiP2pService;-><init>(Landroid/content/Context;)V
    :try_end_34a
    .catch Ljava/lang/Throwable; {:try_start_33c .. :try_end_34a} :catch_7ae

    .line 411
    .end local v114           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .local v115, wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :try_start_34a
    const-string v3, "wifip2p"

    move-object/from16 v0, v115

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_351
    .catch Ljava/lang/Throwable; {:try_start_34a .. :try_end_351} :catch_98c

    move-object/from16 v114, v115

    .line 417
    .end local v115           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v114       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :goto_353
    :try_start_353
    const-string v3, "SystemServer"

    const-string v9, "Wi-Fi Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 418
    new-instance v113, Lcom/android/server/WifiService;

    move-object/from16 v0, v113

    invoke-direct {v0, v4}, Lcom/android/server/WifiService;-><init>(Landroid/content/Context;)V
    :try_end_361
    .catch Ljava/lang/Throwable; {:try_start_353 .. :try_end_361} :catch_7ba

    .line 419
    .end local v112           #wifi:Lcom/android/server/WifiService;
    .local v113, wifi:Lcom/android/server/WifiService;
    :try_start_361
    const-string v3, "wifi"

    move-object/from16 v0, v113

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_368
    .catch Ljava/lang/Throwable; {:try_start_361 .. :try_end_368} :catch_987

    move-object/from16 v112, v113

    .line 425
    .end local v113           #wifi:Lcom/android/server/WifiService;
    .restart local v112       #wifi:Lcom/android/server/WifiService;
    :goto_36a
    :try_start_36a
    const-string v3, "SystemServer"

    const-string v9, "Connectivity Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 426
    new-instance v54, Lcom/android/server/ConnectivityService;

    move-object/from16 v0, v54

    invoke-direct {v0, v4, v13, v12, v8}, Lcom/android/server/ConnectivityService;-><init>(Landroid/content/Context;Landroid/os/INetworkManagementService;Landroid/net/INetworkStatsService;Landroid/net/INetworkPolicyManager;)V
    :try_end_378
    .catch Ljava/lang/Throwable; {:try_start_36a .. :try_end_378} :catch_7c6

    .line 428
    .end local v53           #connectivity:Lcom/android/server/ConnectivityService;
    .local v54, connectivity:Lcom/android/server/ConnectivityService;
    :try_start_378
    const-string v3, "connectivity"

    move-object/from16 v0, v54

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 429
    move-object/from16 v0, v54

    invoke-virtual {v12, v0}, Lcom/android/server/net/NetworkStatsService;->bindConnectivityManager(Landroid/net/IConnectivityManager;)V

    .line 430
    move-object/from16 v0, v54

    invoke-virtual {v8, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->bindConnectivityManager(Landroid/net/IConnectivityManager;)V

    .line 431
    invoke-virtual/range {v112 .. v112}, Lcom/android/server/WifiService;->checkAndStartWifi()V

    .line 432
    invoke-virtual/range {v114 .. v114}, Landroid/net/wifi/p2p/WifiP2pService;->connectivityServiceReady()V
    :try_end_38f
    .catch Ljava/lang/Throwable; {:try_start_378 .. :try_end_38f} :catch_982

    move-object/from16 v53, v54

    .line 438
    .end local v54           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v53       #connectivity:Lcom/android/server/ConnectivityService;
    :goto_391
    :try_start_391
    const-string v3, "SystemServer"

    const-string v9, "Network Service Discovery Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 439
    invoke-static {v4}, Lcom/android/server/NsdService;->create(Landroid/content/Context;)Lcom/android/server/NsdService;

    move-result-object v95

    .line 440
    const-string v3, "servicediscovery"

    move-object/from16 v0, v95

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3a3
    .catch Ljava/lang/Throwable; {:try_start_391 .. :try_end_3a3} :catch_7d2

    .line 447
    :goto_3a3
    :try_start_3a3
    const-string v3, "SystemServer"

    const-string v9, "Throttle Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 448
    new-instance v100, Lcom/android/server/ThrottleService;

    move-object/from16 v0, v100

    invoke-direct {v0, v4}, Lcom/android/server/ThrottleService;-><init>(Landroid/content/Context;)V
    :try_end_3b1
    .catch Ljava/lang/Throwable; {:try_start_3a3 .. :try_end_3b1} :catch_7de

    .line 449
    .end local v99           #throttle:Lcom/android/server/ThrottleService;
    .local v100, throttle:Lcom/android/server/ThrottleService;
    :try_start_3b1
    const-string v3, "throttle"

    move-object/from16 v0, v100

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3b8
    .catch Ljava/lang/Throwable; {:try_start_3b1 .. :try_end_3b8} :catch_97d

    move-object/from16 v99, v100

    .line 456
    .end local v100           #throttle:Lcom/android/server/ThrottleService;
    .restart local v99       #throttle:Lcom/android/server/ThrottleService;
    :goto_3ba
    :try_start_3ba
    const-string v3, "SystemServer"

    const-string v9, "UpdateLock Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 457
    const-string v3, "updatelock"

    new-instance v9, Lcom/android/server/UpdateLockService;

    invoke-direct {v9, v4}, Lcom/android/server/UpdateLockService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_3cb
    .catch Ljava/lang/Throwable; {:try_start_3ba .. :try_end_3cb} :catch_7ea

    .line 468
    :goto_3cb
    if-eqz v78, :cond_3d0

    .line 469
    invoke-virtual/range {v78 .. v78}, Lcom/android/server/MountService;->waitForAsecScan()V

    .line 473
    :cond_3d0
    :try_start_3d0
    const-string v3, "SystemServer"

    const-string v9, "Notification Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 474
    new-instance v85, Lcom/android/server/NotificationManagerService;

    move-object/from16 v0, v85

    move-object/from16 v1, v97

    move-object/from16 v2, v71

    invoke-direct {v0, v4, v1, v2}, Lcom/android/server/NotificationManagerService;-><init>(Landroid/content/Context;Lcom/android/server/StatusBarManagerService;Lcom/android/server/LightsService;)V
    :try_end_3e2
    .catch Ljava/lang/Throwable; {:try_start_3d0 .. :try_end_3e2} :catch_7f6

    .line 475
    .end local v84           #notification:Lcom/android/server/NotificationManagerService;
    .local v85, notification:Lcom/android/server/NotificationManagerService;
    :try_start_3e2
    const-string v3, "notification"

    move-object/from16 v0, v85

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 476
    move-object/from16 v0, v85

    invoke-virtual {v8, v0}, Lcom/android/server/net/NetworkPolicyManagerService;->bindNotificationManager(Landroid/app/INotificationManager;)V
    :try_end_3ee
    .catch Ljava/lang/Throwable; {:try_start_3e2 .. :try_end_3ee} :catch_978

    move-object/from16 v84, v85

    .line 482
    .end local v85           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v84       #notification:Lcom/android/server/NotificationManagerService;
    :goto_3f0
    :try_start_3f0
    const-string v3, "SystemServer"

    const-string v9, "Device Storage Monitor"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 483
    const-string v3, "devicestoragemonitor"

    new-instance v9, Lcom/android/server/DeviceStorageMonitorService;

    invoke-direct {v9, v4}, Lcom/android/server/DeviceStorageMonitorService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_401
    .catch Ljava/lang/Throwable; {:try_start_3f0 .. :try_end_401} :catch_802

    .line 490
    :goto_401
    :try_start_401
    const-string v3, "SystemServer"

    const-string v9, "Location Manager"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 491
    new-instance v74, Lcom/android/server/LocationManagerService;

    move-object/from16 v0, v74

    invoke-direct {v0, v4}, Lcom/android/server/LocationManagerService;-><init>(Landroid/content/Context;)V
    :try_end_40f
    .catch Ljava/lang/Throwable; {:try_start_401 .. :try_end_40f} :catch_80e

    .line 492
    .end local v73           #location:Lcom/android/server/LocationManagerService;
    .local v74, location:Lcom/android/server/LocationManagerService;
    :try_start_40f
    const-string v3, "location"

    move-object/from16 v0, v74

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_416
    .catch Ljava/lang/Throwable; {:try_start_40f .. :try_end_416} :catch_973

    move-object/from16 v73, v74

    .line 498
    .end local v74           #location:Lcom/android/server/LocationManagerService;
    .restart local v73       #location:Lcom/android/server/LocationManagerService;
    :goto_418
    :try_start_418
    const-string v3, "SystemServer"

    const-string v9, "Country Detector"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 499
    new-instance v56, Lcom/android/server/CountryDetectorService;

    move-object/from16 v0, v56

    invoke-direct {v0, v4}, Lcom/android/server/CountryDetectorService;-><init>(Landroid/content/Context;)V
    :try_end_426
    .catch Ljava/lang/Throwable; {:try_start_418 .. :try_end_426} :catch_81a

    .line 500
    .end local v55           #countryDetector:Lcom/android/server/CountryDetectorService;
    .local v56, countryDetector:Lcom/android/server/CountryDetectorService;
    :try_start_426
    const-string v3, "country_detector"

    move-object/from16 v0, v56

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_42d
    .catch Ljava/lang/Throwable; {:try_start_426 .. :try_end_42d} :catch_96e

    move-object/from16 v55, v56

    .line 506
    .end local v56           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v55       #countryDetector:Lcom/android/server/CountryDetectorService;
    :goto_42f
    :try_start_42f
    const-string v3, "SystemServer"

    const-string v9, "Search Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 507
    const-string v3, "search"

    new-instance v9, Landroid/server/search/SearchManagerService;

    invoke-direct {v9, v4}, Landroid/server/search/SearchManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_440
    .catch Ljava/lang/Throwable; {:try_start_42f .. :try_end_440} :catch_826

    .line 514
    :goto_440
    :try_start_440
    const-string v3, "SystemServer"

    const-string v9, "DropBox Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 515
    const-string v3, "dropbox"

    new-instance v9, Lcom/android/server/DropBoxManagerService;

    new-instance v10, Ljava/io/File;

    const-string v11, "/data/system/dropbox"

    invoke-direct {v10, v11}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-direct {v9, v4, v10}, Lcom/android/server/DropBoxManagerService;-><init>(Landroid/content/Context;Ljava/io/File;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_458
    .catch Ljava/lang/Throwable; {:try_start_440 .. :try_end_458} :catch_832

    .line 521
    :goto_458
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v9, 0x1110024

    invoke-virtual {v3, v9}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v3

    if-eqz v3, :cond_47e

    .line 524
    :try_start_465
    const-string v3, "SystemServer"

    const-string v9, "Wallpaper Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 525
    if-nez v16, :cond_47e

    .line 526
    new-instance v111, Lcom/android/server/WallpaperManagerService;

    move-object/from16 v0, v111

    invoke-direct {v0, v4}, Lcom/android/server/WallpaperManagerService;-><init>(Landroid/content/Context;)V
    :try_end_475
    .catch Ljava/lang/Throwable; {:try_start_465 .. :try_end_475} :catch_83e

    .line 527
    .end local v110           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .local v111, wallpaper:Lcom/android/server/WallpaperManagerService;
    :try_start_475
    const-string v3, "wallpaper"

    move-object/from16 v0, v111

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_47c
    .catch Ljava/lang/Throwable; {:try_start_475 .. :try_end_47c} :catch_969

    move-object/from16 v110, v111

    .line 534
    .end local v111           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v110       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :cond_47e
    :goto_47e
    const-string v3, "0"

    const-string v9, "system_init.startaudioservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_49d

    .line 536
    :try_start_48c
    const-string v3, "SystemServer"

    const-string v9, "Audio Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 537
    const-string v3, "audio"

    new-instance v9, Landroid/media/AudioService;

    invoke-direct {v9, v4}, Landroid/media/AudioService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_49d
    .catch Ljava/lang/Throwable; {:try_start_48c .. :try_end_49d} :catch_84a

    .line 544
    :cond_49d
    :goto_49d
    :try_start_49d
    const-string v3, "SystemServer"

    const-string v9, "Dock Observer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 546
    new-instance v61, Lcom/android/server/DockObserver;

    move-object/from16 v0, v61

    invoke-direct {v0, v4, v6}, Lcom/android/server/DockObserver;-><init>(Landroid/content/Context;Lcom/android/server/PowerManagerService;)V
    :try_end_4ab
    .catch Ljava/lang/Throwable; {:try_start_49d .. :try_end_4ab} :catch_856

    .end local v60           #dock:Lcom/android/server/DockObserver;
    .local v61, dock:Lcom/android/server/DockObserver;
    move-object/from16 v60, v61

    .line 552
    .end local v61           #dock:Lcom/android/server/DockObserver;
    .restart local v60       #dock:Lcom/android/server/DockObserver;
    :goto_4ad
    :try_start_4ad
    const-string v3, "SystemServer"

    const-string v9, "Wired Accessory Observer"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 554
    new-instance v3, Lcom/android/server/WiredAccessoryObserver;

    invoke-direct {v3, v4}, Lcom/android/server/WiredAccessoryObserver;-><init>(Landroid/content/Context;)V
    :try_end_4b9
    .catch Ljava/lang/Throwable; {:try_start_4ad .. :try_end_4b9} :catch_862

    .line 560
    :goto_4b9
    :try_start_4b9
    const-string v3, "SystemServer"

    const-string v9, "USB Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 562
    new-instance v106, Lcom/android/server/usb/UsbService;

    move-object/from16 v0, v106

    invoke-direct {v0, v4}, Lcom/android/server/usb/UsbService;-><init>(Landroid/content/Context;)V
    :try_end_4c7
    .catch Ljava/lang/Throwable; {:try_start_4b9 .. :try_end_4c7} :catch_86e

    .line 563
    .end local v105           #usb:Lcom/android/server/usb/UsbService;
    .local v106, usb:Lcom/android/server/usb/UsbService;
    :try_start_4c7
    const-string v3, "usb"

    move-object/from16 v0, v106

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_4ce
    .catch Ljava/lang/Throwable; {:try_start_4c7 .. :try_end_4ce} :catch_964

    move-object/from16 v105, v106

    .line 569
    .end local v106           #usb:Lcom/android/server/usb/UsbService;
    .restart local v105       #usb:Lcom/android/server/usb/UsbService;
    :goto_4d0
    :try_start_4d0
    const-string v3, "SystemServer"

    const-string v9, "Serial Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 571
    new-instance v94, Lcom/android/server/SerialService;

    move-object/from16 v0, v94

    invoke-direct {v0, v4}, Lcom/android/server/SerialService;-><init>(Landroid/content/Context;)V
    :try_end_4de
    .catch Ljava/lang/Throwable; {:try_start_4d0 .. :try_end_4de} :catch_87a

    .line 572
    .end local v93           #serial:Lcom/android/server/SerialService;
    .local v94, serial:Lcom/android/server/SerialService;
    :try_start_4de
    const-string v3, "serial"

    move-object/from16 v0, v94

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_4e5
    .catch Ljava/lang/Throwable; {:try_start_4de .. :try_end_4e5} :catch_95f

    move-object/from16 v93, v94

    .line 578
    .end local v94           #serial:Lcom/android/server/SerialService;
    .restart local v93       #serial:Lcom/android/server/SerialService;
    :goto_4e7
    :try_start_4e7
    const-string v3, "SystemServer"

    const-string v9, "UI Mode Manager Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 580
    new-instance v104, Lcom/android/server/UiModeManagerService;

    move-object/from16 v0, v104

    invoke-direct {v0, v4}, Lcom/android/server/UiModeManagerService;-><init>(Landroid/content/Context;)V
    :try_end_4f5
    .catch Ljava/lang/Throwable; {:try_start_4e7 .. :try_end_4f5} :catch_886

    .end local v103           #uiMode:Lcom/android/server/UiModeManagerService;
    .local v104, uiMode:Lcom/android/server/UiModeManagerService;
    move-object/from16 v103, v104

    .line 586
    .end local v104           #uiMode:Lcom/android/server/UiModeManagerService;
    .restart local v103       #uiMode:Lcom/android/server/UiModeManagerService;
    :goto_4f7
    :try_start_4f7
    const-string v3, "SystemServer"

    const-string v9, "Backup Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 587
    const-string v3, "backup"

    new-instance v9, Lcom/android/server/BackupManagerService;

    invoke-direct {v9, v4}, Lcom/android/server/BackupManagerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_508
    .catch Ljava/lang/Throwable; {:try_start_4f7 .. :try_end_508} :catch_892

    .line 594
    :goto_508
    :try_start_508
    const-string v3, "SystemServer"

    const-string v9, "AppWidget Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 595
    new-instance v43, Lcom/android/server/AppWidgetService;

    move-object/from16 v0, v43

    invoke-direct {v0, v4}, Lcom/android/server/AppWidgetService;-><init>(Landroid/content/Context;)V
    :try_end_516
    .catch Ljava/lang/Throwable; {:try_start_508 .. :try_end_516} :catch_89e

    .line 596
    .end local v42           #appWidget:Lcom/android/server/AppWidgetService;
    .local v43, appWidget:Lcom/android/server/AppWidgetService;
    :try_start_516
    const-string v3, "appwidget"

    move-object/from16 v0, v43

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_51d
    .catch Ljava/lang/Throwable; {:try_start_516 .. :try_end_51d} :catch_95a

    move-object/from16 v42, v43

    .line 602
    .end local v43           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v42       #appWidget:Lcom/android/server/AppWidgetService;
    :goto_51f
    :try_start_51f
    const-string v3, "SystemServer"

    const-string v9, "Recognition Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 603
    new-instance v92, Lcom/android/server/RecognitionManagerService;

    move-object/from16 v0, v92

    invoke-direct {v0, v4}, Lcom/android/server/RecognitionManagerService;-><init>(Landroid/content/Context;)V
    :try_end_52d
    .catch Ljava/lang/Throwable; {:try_start_51f .. :try_end_52d} :catch_8aa

    .end local v91           #recognition:Lcom/android/server/RecognitionManagerService;
    .local v92, recognition:Lcom/android/server/RecognitionManagerService;
    move-object/from16 v91, v92

    .line 609
    .end local v92           #recognition:Lcom/android/server/RecognitionManagerService;
    .restart local v91       #recognition:Lcom/android/server/RecognitionManagerService;
    :goto_52f
    :try_start_52f
    const-string v3, "SystemServer"

    const-string v9, "DiskStats Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 610
    const-string v3, "diskstats"

    new-instance v9, Lcom/android/server/DiskStatsService;

    invoke-direct {v9, v4}, Lcom/android/server/DiskStatsService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_540
    .catch Ljava/lang/Throwable; {:try_start_52f .. :try_end_540} :catch_8b6

    .line 620
    :goto_540
    :try_start_540
    const-string v3, "SystemServer"

    const-string v9, "SamplingProfiler Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 621
    const-string v3, "samplingprofiler"

    new-instance v9, Lcom/android/server/SamplingProfilerService;

    invoke-direct {v9, v4}, Lcom/android/server/SamplingProfilerService;-><init>(Landroid/content/Context;)V

    invoke-static {v3, v9}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_551
    .catch Ljava/lang/Throwable; {:try_start_540 .. :try_end_551} :catch_8c2

    .line 628
    :goto_551
    :try_start_551
    const-string v3, "SystemServer"

    const-string v9, "NetworkTimeUpdateService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 629
    new-instance v83, Lcom/android/server/NetworkTimeUpdateService;

    move-object/from16 v0, v83

    invoke-direct {v0, v4}, Lcom/android/server/NetworkTimeUpdateService;-><init>(Landroid/content/Context;)V
    :try_end_55f
    .catch Ljava/lang/Throwable; {:try_start_551 .. :try_end_55f} :catch_8ce

    .end local v82           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .local v83, networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    move-object/from16 v82, v83

    .line 635
    .end local v83           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .restart local v82       #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    :goto_561
    :try_start_561
    const-string v3, "SystemServer"

    const-string v9, "CommonTimeManagementService"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 636
    new-instance v51, Lcom/android/server/CommonTimeManagementService;

    move-object/from16 v0, v51

    invoke-direct {v0, v4}, Lcom/android/server/CommonTimeManagementService;-><init>(Landroid/content/Context;)V
    :try_end_56f
    .catch Ljava/lang/Throwable; {:try_start_561 .. :try_end_56f} :catch_8da

    .line 637
    .end local v50           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .local v51, commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :try_start_56f
    const-string v3, "commontime_management"

    move-object/from16 v0, v51

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_576
    .catch Ljava/lang/Throwable; {:try_start_56f .. :try_end_576} :catch_956

    move-object/from16 v50, v51

    .line 643
    .end local v51           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v50       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :goto_578
    :try_start_578
    const-string v3, "SystemServer"

    const-string v9, "CertBlacklister"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 644
    new-instance v3, Lcom/android/server/CertBlacklister;

    invoke-direct {v3, v4}, Lcom/android/server/CertBlacklister;-><init>(Landroid/content/Context;)V
    :try_end_584
    .catch Ljava/lang/Throwable; {:try_start_578 .. :try_end_584} :catch_8e6

    .line 649
    :goto_584
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v9, 0x1110038

    invoke-virtual {v3, v9}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v3

    if-eqz v3, :cond_5a8

    .line 652
    :try_start_591
    const-string v3, "SystemServer"

    const-string v9, "Dreams Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 654
    new-instance v63, Landroid/service/dreams/DreamManagerService;

    move-object/from16 v0, v63

    invoke-direct {v0, v4}, Landroid/service/dreams/DreamManagerService;-><init>(Landroid/content/Context;)V
    :try_end_59f
    .catch Ljava/lang/Throwable; {:try_start_591 .. :try_end_59f} :catch_8f2

    .line 655
    .end local v62           #dreamy:Landroid/service/dreams/DreamManagerService;
    .local v63, dreamy:Landroid/service/dreams/DreamManagerService;
    :try_start_59f
    const-string v3, "dreams"

    move-object/from16 v0, v63

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V
    :try_end_5a6
    .catch Ljava/lang/Throwable; {:try_start_59f .. :try_end_5a6} :catch_952

    move-object/from16 v62, v63

    .line 664
    .end local v63           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v78           #mountService:Lcom/android/server/MountService;
    .restart local v62       #dreamy:Landroid/service/dreams/DreamManagerService;
    :cond_5a8
    :goto_5a8
    invoke-virtual/range {v116 .. v116}, Lcom/android/server/wm/WindowManagerService;->detectSafeMode()Z

    move-result v28

    .line 665
    .local v28, safeMode:Z
    if-eqz v28, :cond_8fe

    .line 666
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/server/am/ActivityManagerService;->enterSafeMode()V

    .line 668
    const/4 v3, 0x1

    sput-boolean v3, Ldalvik/system/Zygote;->systemInSafeMode:Z

    .line 670
    invoke-static {}, Ldalvik/system/VMRuntime;->getRuntime()Ldalvik/system/VMRuntime;

    move-result-object v3

    invoke-virtual {v3}, Ldalvik/system/VMRuntime;->disableJitCompilation()V

    .line 679
    :goto_5bf
    :try_start_5bf
    invoke-virtual/range {v107 .. v107}, Lcom/android/server/VibratorService;->systemReady()V
    :try_end_5c2
    .catch Ljava/lang/Throwable; {:try_start_5bf .. :try_end_5c2} :catch_907

    .line 684
    :goto_5c2
    if-eqz v58, :cond_5c7

    .line 686
    :try_start_5c4
    invoke-virtual/range {v58 .. v58}, Lcom/android/server/DevicePolicyManagerService;->systemReady()V
    :try_end_5c7
    .catch Ljava/lang/Throwable; {:try_start_5c4 .. :try_end_5c7} :catch_913

    .line 692
    :cond_5c7
    :goto_5c7
    if-eqz v84, :cond_5cc

    .line 694
    :try_start_5c9
    invoke-virtual/range {v84 .. v84}, Lcom/android/server/NotificationManagerService;->systemReady()V
    :try_end_5cc
    .catch Ljava/lang/Throwable; {:try_start_5c9 .. :try_end_5cc} :catch_91f

    .line 701
    :cond_5cc
    :goto_5cc
    :try_start_5cc
    invoke-virtual/range {v116 .. v116}, Lcom/android/server/wm/WindowManagerService;->systemReady()V
    :try_end_5cf
    .catch Ljava/lang/Throwable; {:try_start_5cc .. :try_end_5cf} :catch_92b

    .line 706
    :goto_5cf
    if-eqz v28, :cond_5d8

    .line 707
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/server/am/ActivityManagerService;->showSafeModeOverlay()V

    .line 713
    :cond_5d8
    invoke-virtual/range {v116 .. v116}, Lcom/android/server/wm/WindowManagerService;->computeNewConfiguration()Landroid/content/res/Configuration;

    move-result-object v52

    .line 714
    .local v52, config:Landroid/content/res/Configuration;
    new-instance v77, Landroid/util/DisplayMetrics;

    invoke-direct/range {v77 .. v77}, Landroid/util/DisplayMetrics;-><init>()V

    .line 715
    .local v77, metrics:Landroid/util/DisplayMetrics;
    const-string v3, "window"

    invoke-virtual {v4, v3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v109

    check-cast v109, Landroid/view/WindowManager;

    .line 716
    .local v109, w:Landroid/view/WindowManager;
    invoke-interface/range {v109 .. v109}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    move-object/from16 v0, v77

    invoke-virtual {v3, v0}, Landroid/view/Display;->getMetrics(Landroid/util/DisplayMetrics;)V

    .line 717
    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    move-object/from16 v0, v52

    move-object/from16 v1, v77

    invoke-virtual {v3, v0, v1}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    .line 719
    invoke-virtual {v6}, Lcom/android/server/PowerManagerService;->systemReady()V

    .line 721
    :try_start_600
    invoke-interface/range {v87 .. v87}, Landroid/content/pm/IPackageManager;->systemReady()V
    :try_end_603
    .catch Ljava/lang/Throwable; {:try_start_600 .. :try_end_603} :catch_937

    .line 726
    :goto_603
    :try_start_603
    invoke-virtual/range {v75 .. v75}, Lcom/android/internal/widget/LockSettingsService;->systemReady()V
    :try_end_606
    .catch Ljava/lang/Throwable; {:try_start_603 .. :try_end_606} :catch_943

    .line 732
    :goto_606
    move-object/from16 v17, v4

    .line 733
    .local v17, contextF:Landroid/content/Context;
    move-object/from16 v18, v5

    .line 734
    .local v18, batteryF:Lcom/android/server/BatteryService;
    move-object/from16 v19, v13

    .line 735
    .local v19, networkManagementF:Lcom/android/server/NetworkManagementService;
    move-object/from16 v20, v12

    .line 736
    .local v20, networkStatsF:Lcom/android/server/net/NetworkStatsService;
    move-object/from16 v21, v8

    .line 737
    .local v21, networkPolicyF:Lcom/android/server/net/NetworkPolicyManagerService;
    move-object/from16 v22, v53

    .line 738
    .local v22, connectivityF:Lcom/android/server/ConnectivityService;
    move-object/from16 v23, v60

    .line 739
    .local v23, dockF:Lcom/android/server/DockObserver;
    move-object/from16 v24, v105

    .line 740
    .local v24, usbF:Lcom/android/server/usb/UsbService;
    move-object/from16 v34, v99

    .line 741
    .local v34, throttleF:Lcom/android/server/ThrottleService;
    move-object/from16 v25, v103

    .line 742
    .local v25, uiModeF:Lcom/android/server/UiModeManagerService;
    move-object/from16 v27, v42

    .line 743
    .local v27, appWidgetF:Lcom/android/server/AppWidgetService;
    move-object/from16 v29, v110

    .line 744
    .local v29, wallpaperF:Lcom/android/server/WallpaperManagerService;
    move-object/from16 v30, v68

    .line 745
    .local v30, immF:Lcom/android/server/InputMethodManagerService;
    move-object/from16 v26, v91

    .line 746
    .local v26, recognitionF:Lcom/android/server/RecognitionManagerService;
    move-object/from16 v32, v73

    .line 747
    .local v32, locationF:Lcom/android/server/LocationManagerService;
    move-object/from16 v33, v55

    .line 748
    .local v33, countryDetectorF:Lcom/android/server/CountryDetectorService;
    move-object/from16 v35, v82

    .line 749
    .local v35, networkTimeUpdaterF:Lcom/android/server/NetworkTimeUpdateService;
    move-object/from16 v36, v50

    .line 750
    .local v36, commonTimeMgmtServiceF:Lcom/android/server/CommonTimeManagementService;
    move-object/from16 v37, v101

    .line 751
    .local v37, textServiceManagerServiceF:Lcom/android/server/TextServicesManagerService;
    move-object/from16 v31, v97

    .line 752
    .local v31, statusBarF:Lcom/android/server/StatusBarManagerService;
    move-object/from16 v38, v62

    .line 753
    .local v38, dreamyF:Landroid/service/dreams/DreamManagerService;
    move-object/from16 v39, v70

    .line 754
    .local v39, inputManagerF:Lcom/android/server/input/InputManagerService;
    move-object/from16 v40, v45

    .line 761
    .local v40, bluetoothF:Landroid/server/BluetoothService;
    invoke-static {}, Lcom/android/server/am/ActivityManagerService;->self()Lcom/android/server/am/ActivityManagerService;

    move-result-object v3

    new-instance v14, Lcom/android/server/ServerThread$1;

    move-object/from16 v15, p0

    invoke-direct/range {v14 .. v40}, Lcom/android/server/ServerThread$1;-><init>(Lcom/android/server/ServerThread;ZLandroid/content/Context;Lcom/android/server/BatteryService;Lcom/android/server/NetworkManagementService;Lcom/android/server/net/NetworkStatsService;Lcom/android/server/net/NetworkPolicyManagerService;Lcom/android/server/ConnectivityService;Lcom/android/server/DockObserver;Lcom/android/server/usb/UsbService;Lcom/android/server/UiModeManagerService;Lcom/android/server/RecognitionManagerService;Lcom/android/server/AppWidgetService;ZLcom/android/server/WallpaperManagerService;Lcom/android/server/InputMethodManagerService;Lcom/android/server/StatusBarManagerService;Lcom/android/server/LocationManagerService;Lcom/android/server/CountryDetectorService;Lcom/android/server/ThrottleService;Lcom/android/server/NetworkTimeUpdateService;Lcom/android/server/CommonTimeManagementService;Lcom/android/server/TextServicesManagerService;Landroid/service/dreams/DreamManagerService;Lcom/android/server/input/InputManagerService;Landroid/server/BluetoothService;)V

    invoke-virtual {v3, v14}, Lcom/android/server/am/ActivityManagerService;->systemReady(Ljava/lang/Runnable;)V

    .line 875
    invoke-static {}, Landroid/os/StrictMode;->conditionallyEnableDebugLogging()Z

    move-result v3

    if-eqz v3, :cond_64f

    .line 876
    const-string v3, "SystemServer"

    const-string v9, "Enabled StrictMode for system server main thread."

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 879
    :cond_64f
    invoke-static {}, Landroid/os/Looper;->loop()V

    .line 880
    const-string v3, "SystemServer"

    const-string v9, "System ServerThread is exiting!"

    invoke-static {v3, v9}, Landroid/util/Slog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 881
    return-void

    .line 103
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
    .end local v42           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v45           #bluetooth:Landroid/server/BluetoothService;
    .end local v47           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .end local v50           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .end local v52           #config:Landroid/content/res/Configuration;
    .end local v53           #connectivity:Lcom/android/server/ConnectivityService;
    .end local v55           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v58           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v60           #dock:Lcom/android/server/DockObserver;
    .end local v62           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v65           #factoryTest:I
    .end local v66           #factoryTestStr:Ljava/lang/String;
    .end local v68           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v70           #inputManager:Lcom/android/server/input/InputManagerService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v73           #location:Lcom/android/server/LocationManagerService;
    .end local v75           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v77           #metrics:Landroid/util/DisplayMetrics;
    .end local v82           #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .end local v84           #notification:Lcom/android/server/NotificationManagerService;
    .end local v87           #pm:Landroid/content/pm/IPackageManager;
    .end local v91           #recognition:Lcom/android/server/RecognitionManagerService;
    .end local v93           #serial:Lcom/android/server/SerialService;
    .end local v95           #serviceDiscovery:Lcom/android/server/NsdService;
    .end local v97           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v99           #throttle:Lcom/android/server/ThrottleService;
    .end local v101           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v103           #uiMode:Lcom/android/server/UiModeManagerService;
    .end local v105           #usb:Lcom/android/server/usb/UsbService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .end local v109           #w:Landroid/view/WindowManager;
    .end local v110           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .end local v112           #wifi:Lcom/android/server/WifiService;
    .end local v114           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .end local v116           #wm:Lcom/android/server/wm/WindowManagerService;
    :cond_65a
    const/16 v90, 0x0

    goto/16 :goto_35

    .line 109
    .restart local v90       #reboot:Z
    :cond_65e
    const/16 v89, 0x0

    .restart local v89       #reason:Ljava/lang/String;
    goto/16 :goto_47

    .line 117
    .end local v89           #reason:Ljava/lang/String;
    .end local v90           #reboot:Z
    .restart local v66       #factoryTestStr:Ljava/lang/String;
    :cond_662
    invoke-static/range {v66 .. v66}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v65

    goto/16 :goto_60

    .line 176
    .restart local v4       #context:Landroid/content/Context;
    .restart local v6       #power:Lcom/android/server/PowerManagerService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v13       #networkManagement:Lcom/android/server/NetworkManagementService;
    .restart local v16       #headless:Z
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #battery:Lcom/android/server/BatteryService;
    .restart local v45       #bluetooth:Landroid/server/BluetoothService;
    .restart local v47       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v50       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v53       #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v57       #cryptState:Ljava/lang/String;
    .restart local v60       #dock:Lcom/android/server/DockObserver;
    .restart local v65       #factoryTest:I
    .restart local v70       #inputManager:Lcom/android/server/input/InputManagerService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    .restart local v80       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v82       #networkTimeUpdater:Lcom/android/server/NetworkTimeUpdateService;
    .restart local v86       #onlyCore:Z
    .restart local v87       #pm:Landroid/content/pm/IPackageManager;
    .restart local v91       #recognition:Lcom/android/server/RecognitionManagerService;
    .restart local v93       #serial:Lcom/android/server/SerialService;
    .restart local v95       #serviceDiscovery:Lcom/android/server/NsdService;
    .restart local v99       #throttle:Lcom/android/server/ThrottleService;
    .restart local v103       #uiMode:Lcom/android/server/UiModeManagerService;
    .restart local v105       #usb:Lcom/android/server/usb/UsbService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    .restart local v112       #wifi:Lcom/android/server/WifiService;
    .restart local v114       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v116       #wm:Lcom/android/server/wm/WindowManagerService;
    :cond_668
    :try_start_668
    const-string v3, "1"

    move-object/from16 v0, v57

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_113

    .line 177
    const-string v3, "SystemServer"

    const-string v9, "Device encrypted - only parsing core apps"

    invoke-static {v3, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 178
    const/16 v86, 0x1

    goto/16 :goto_113

    .line 181
    :cond_67d
    const/4 v3, 0x0

    goto/16 :goto_116

    .line 199
    .restart local v67       #firstBoot:Z
    :catch_680
    move-exception v64

    .line 200
    .local v64, e:Ljava/lang/Throwable;
    const-string v3, "SystemServer"

    const-string v9, "Failure starting Account Manager"

    move-object/from16 v0, v64

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_68a
    .catch Ljava/lang/RuntimeException; {:try_start_668 .. :try_end_68a} :catch_68c

    goto/16 :goto_13e

    .line 270
    .end local v57           #cryptState:Ljava/lang/String;
    .end local v64           #e:Ljava/lang/Throwable;
    .end local v67           #firstBoot:Z
    .end local v86           #onlyCore:Z
    :catch_68c
    move-exception v64

    move-object/from16 v7, v41

    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v44

    .line 271
    .end local v44           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .local v64, e:Ljava/lang/RuntimeException;
    :goto_691
    const-string v3, "System"

    const-string v9, "******************************************"

    invoke-static {v3, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 272
    const-string v3, "System"

    const-string v9, "************ Failure starting core service"

    move-object/from16 v0, v64

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_205

    .line 204
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v64           #e:Ljava/lang/RuntimeException;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #battery:Lcom/android/server/BatteryService;
    .restart local v57       #cryptState:Ljava/lang/String;
    .restart local v67       #firstBoot:Z
    .restart local v86       #onlyCore:Z
    :cond_6a3
    const/4 v3, 0x0

    goto/16 :goto_14b

    .line 234
    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v44           #battery:Lcom/android/server/BatteryService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v108       #vibrator:Lcom/android/server/VibratorService;
    :cond_6a6
    const/4 v3, 0x0

    move v9, v3

    goto/16 :goto_1c8

    :cond_6aa
    const/4 v3, 0x0

    goto/16 :goto_1cb

    .line 248
    :cond_6ad
    const/4 v3, 0x1

    move/from16 v0, v65

    if-ne v0, v3, :cond_6c1

    .line 249
    :try_start_6b2
    const-string v3, "SystemServer"

    const-string v9, "No Bluetooth Service (factory test)"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_201

    .line 270
    :catch_6bb
    move-exception v64

    move-object/from16 v107, v108

    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto :goto_691

    .line 251
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v108       #vibrator:Lcom/android/server/VibratorService;
    :cond_6c1
    const-string v3, "SystemServer"

    const-string v9, "Bluetooth Service"

    invoke-static {v3, v9}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 252
    new-instance v46, Landroid/server/BluetoothService;

    move-object/from16 v0, v46

    invoke-direct {v0, v4}, Landroid/server/BluetoothService;-><init>(Landroid/content/Context;)V
    :try_end_6cf
    .catch Ljava/lang/RuntimeException; {:try_start_6b2 .. :try_end_6cf} :catch_6bb

    .line 253
    .end local v45           #bluetooth:Landroid/server/BluetoothService;
    .local v46, bluetooth:Landroid/server/BluetoothService;
    :try_start_6cf
    const-string v3, "bluetooth"

    move-object/from16 v0, v46

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 254
    invoke-virtual/range {v46 .. v46}, Landroid/server/BluetoothService;->initAfterRegistration()V

    .line 256
    const-string v3, "0"

    const-string v9, "system_init.startaudioservice"

    invoke-static {v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_6fc

    .line 257
    new-instance v48, Landroid/server/BluetoothA2dpService;

    move-object/from16 v0, v48

    move-object/from16 v1, v46

    invoke-direct {v0, v4, v1}, Landroid/server/BluetoothA2dpService;-><init>(Landroid/content/Context;Landroid/server/BluetoothService;)V
    :try_end_6f0
    .catch Ljava/lang/RuntimeException; {:try_start_6cf .. :try_end_6f0} :catch_9dc

    .line 258
    .end local v47           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .local v48, bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    :try_start_6f0
    const-string v3, "bluetooth_a2dp"

    move-object/from16 v0, v48

    invoke-static {v3, v0}, Landroid/os/ServiceManager;->addService(Ljava/lang/String;Landroid/os/IBinder;)V

    .line 260
    invoke-virtual/range {v46 .. v46}, Landroid/server/BluetoothService;->initAfterA2dpRegistration()V
    :try_end_6fa
    .catch Ljava/lang/RuntimeException; {:try_start_6f0 .. :try_end_6fa} :catch_9e5

    move-object/from16 v47, v48

    .line 263
    .end local v48           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v47       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    :cond_6fc
    :try_start_6fc
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/android/server/ServerThread;->mContentResolver:Landroid/content/ContentResolver;

    const-string v9, "bluetooth_on"

    const/4 v10, 0x0

    invoke-static {v3, v9, v10}, Landroid/provider/Settings$Secure;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v49

    .line 265
    .local v49, bluetoothOn:I
    if-eqz v49, :cond_70c

    .line 266
    invoke-virtual/range {v46 .. v46}, Landroid/server/BluetoothService;->enable()Z
    :try_end_70c
    .catch Ljava/lang/RuntimeException; {:try_start_6fc .. :try_end_70c} :catch_9dc

    :cond_70c
    move-object/from16 v45, v46

    .end local v46           #bluetooth:Landroid/server/BluetoothService;
    .restart local v45       #bluetooth:Landroid/server/BluetoothService;
    goto/16 :goto_201

    .line 293
    .end local v49           #bluetoothOn:I
    .end local v57           #cryptState:Ljava/lang/String;
    .end local v67           #firstBoot:Z
    .end local v72           #lights:Lcom/android/server/LightsService;
    .end local v86           #onlyCore:Z
    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v42       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v55       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v58       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v62       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v68       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    .restart local v73       #location:Lcom/android/server/LocationManagerService;
    .restart local v75       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v84       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v97       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v101       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    .restart local v110       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_710
    move-exception v64

    .line 294
    .local v64, e:Ljava/lang/Throwable;
    :goto_711
    const-string v3, "starting Input Manager Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_239

    .line 301
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_71c
    move-exception v64

    .line 302
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Accessibility Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_24a

    .line 308
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_728
    move-exception v64

    .line 309
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making display ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_24d

    .line 314
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_734
    move-exception v64

    .line 315
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "performing boot dexopt"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_250

    .line 337
    .end local v64           #e:Ljava/lang/Throwable;
    .restart local v78       #mountService:Lcom/android/server/MountService;
    :catch_740
    move-exception v64

    .line 338
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_741
    const-string v3, "starting Mount Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_28f

    .line 346
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_74c
    move-exception v64

    .line 347
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_74d
    const-string v3, "starting LockSettingsService service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2a6

    .line 354
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_758
    move-exception v64

    .line 355
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_759
    const-string v3, "starting DevicePolicyService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2bd

    .line 362
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_764
    move-exception v64

    .line 363
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_765
    const-string v3, "starting StatusBarManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2d6

    .line 370
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_770
    move-exception v64

    .line 371
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Clipboard Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2e7

    .line 378
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_77c
    move-exception v64

    .line 379
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting NetworkManagement Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_2f7

    .line 386
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_788
    move-exception v64

    .line 387
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_789
    const-string v3, "starting Text Service Manager Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_30e

    .line 394
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_794
    move-exception v64

    .line 395
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_795
    const-string v3, "starting NetworkStats Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_325

    .line 404
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7a0
    move-exception v64

    move-object/from16 v8, v80

    .line 405
    .end local v80           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7a3
    const-string v3, "starting NetworkPolicy Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_33c

    .line 412
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7ae
    move-exception v64

    .line 413
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7af
    const-string v3, "starting Wi-Fi P2pService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_353

    .line 420
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7ba
    move-exception v64

    .line 421
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7bb
    const-string v3, "starting Wi-Fi Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_36a

    .line 433
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7c6
    move-exception v64

    .line 434
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7c7
    const-string v3, "starting Connectivity Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_391

    .line 442
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7d2
    move-exception v64

    .line 443
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Service Discovery Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3a3

    .line 451
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7de
    move-exception v64

    .line 452
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7df
    const-string v3, "starting ThrottleService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3ba

    .line 459
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7ea
    move-exception v64

    .line 460
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting UpdateLockService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3cb

    .line 477
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_7f6
    move-exception v64

    .line 478
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_7f7
    const-string v3, "starting Notification Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_3f0

    .line 485
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_802
    move-exception v64

    .line 486
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting DeviceStorageMonitor service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_401

    .line 493
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_80e
    move-exception v64

    .line 494
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_80f
    const-string v3, "starting Location Manager"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_418

    .line 501
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_81a
    move-exception v64

    .line 502
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_81b
    const-string v3, "starting Country Detector"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_42f

    .line 509
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_826
    move-exception v64

    .line 510
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Search Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_440

    .line 517
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_832
    move-exception v64

    .line 518
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting DropBoxManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_458

    .line 529
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_83e
    move-exception v64

    .line 530
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_83f
    const-string v3, "starting Wallpaper Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_47e

    .line 538
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_84a
    move-exception v64

    .line 539
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Audio Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_49d

    .line 547
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_856
    move-exception v64

    .line 548
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting DockObserver"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4ad

    .line 555
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_862
    move-exception v64

    .line 556
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting WiredAccessoryObserver"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4b9

    .line 564
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_86e
    move-exception v64

    .line 565
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_86f
    const-string v3, "starting UsbService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4d0

    .line 573
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_87a
    move-exception v64

    .line 574
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_87b
    const-string v3, "SystemServer"

    const-string v9, "Failure starting SerialService"

    move-object/from16 v0, v64

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_4e7

    .line 581
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_886
    move-exception v64

    .line 582
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting UiModeManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_4f7

    .line 589
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_892
    move-exception v64

    .line 590
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "SystemServer"

    const-string v9, "Failure starting Backup Service"

    move-object/from16 v0, v64

    invoke-static {v3, v9, v0}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto/16 :goto_508

    .line 597
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_89e
    move-exception v64

    .line 598
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_89f
    const-string v3, "starting AppWidget Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_51f

    .line 604
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8aa
    move-exception v64

    .line 605
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting Recognition Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_52f

    .line 611
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8b6
    move-exception v64

    .line 612
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting DiskStats Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_540

    .line 623
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8c2
    move-exception v64

    .line 624
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting SamplingProfiler Service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_551

    .line 630
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8ce
    move-exception v64

    .line 631
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting NetworkTimeUpdate service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_561

    .line 638
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8da
    move-exception v64

    .line 639
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_8db
    const-string v3, "starting CommonTimeManagementService service"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_578

    .line 645
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8e6
    move-exception v64

    .line 646
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "starting CertBlacklister"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_584

    .line 656
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_8f2
    move-exception v64

    .line 657
    .restart local v64       #e:Ljava/lang/Throwable;
    :goto_8f3
    const-string v3, "starting DreamManagerService"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5a8

    .line 673
    .end local v64           #e:Ljava/lang/Throwable;
    .end local v78           #mountService:Lcom/android/server/MountService;
    .restart local v28       #safeMode:Z
    :cond_8fe
    invoke-static {}, Ldalvik/system/VMRuntime;->getRuntime()Ldalvik/system/VMRuntime;

    move-result-object v3

    invoke-virtual {v3}, Ldalvik/system/VMRuntime;->startJitCompilation()V

    goto/16 :goto_5bf

    .line 680
    :catch_907
    move-exception v64

    .line 681
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Vibrator Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5c2

    .line 687
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_913
    move-exception v64

    .line 688
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Device Policy Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5c7

    .line 695
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_91f
    move-exception v64

    .line 696
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Notification Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5cc

    .line 702
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_92b
    move-exception v64

    .line 703
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Window Manager Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_5cf

    .line 722
    .end local v64           #e:Ljava/lang/Throwable;
    .restart local v52       #config:Landroid/content/res/Configuration;
    .restart local v77       #metrics:Landroid/util/DisplayMetrics;
    .restart local v109       #w:Landroid/view/WindowManager;
    :catch_937
    move-exception v64

    .line 723
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Package Manager Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_603

    .line 727
    .end local v64           #e:Ljava/lang/Throwable;
    :catch_943
    move-exception v64

    .line 728
    .restart local v64       #e:Ljava/lang/Throwable;
    const-string v3, "making Lock Settings Service ready"

    move-object/from16 v0, p0

    move-object/from16 v1, v64

    invoke-virtual {v0, v3, v1}, Lcom/android/server/ServerThread;->reportWtf(Ljava/lang/String;Ljava/lang/Throwable;)V

    goto/16 :goto_606

    .line 187
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v8           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v28           #safeMode:Z
    .end local v42           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v52           #config:Landroid/content/res/Configuration;
    .end local v55           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v58           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v62           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v64           #e:Ljava/lang/Throwable;
    .end local v68           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v73           #location:Lcom/android/server/LocationManagerService;
    .end local v75           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v77           #metrics:Landroid/util/DisplayMetrics;
    .end local v84           #notification:Lcom/android/server/NotificationManagerService;
    .end local v97           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v101           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v109           #w:Landroid/view/WindowManager;
    .end local v110           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #battery:Lcom/android/server/BatteryService;
    .restart local v57       #cryptState:Ljava/lang/String;
    .restart local v67       #firstBoot:Z
    .restart local v80       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v86       #onlyCore:Z
    :catch_94f
    move-exception v3

    goto/16 :goto_122

    .line 656
    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v44           #battery:Lcom/android/server/BatteryService;
    .end local v57           #cryptState:Ljava/lang/String;
    .end local v67           #firstBoot:Z
    .end local v80           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v86           #onlyCore:Z
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v42       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v55       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v58       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v63       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v68       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v73       #location:Lcom/android/server/LocationManagerService;
    .restart local v75       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v78       #mountService:Lcom/android/server/MountService;
    .restart local v84       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v97       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v101       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v110       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_952
    move-exception v64

    move-object/from16 v62, v63

    .end local v63           #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v62       #dreamy:Landroid/service/dreams/DreamManagerService;
    goto :goto_8f3

    .line 638
    .end local v50           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v51       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    :catch_956
    move-exception v64

    move-object/from16 v50, v51

    .end local v51           #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    .restart local v50       #commonTimeMgmtService:Lcom/android/server/CommonTimeManagementService;
    goto :goto_8db

    .line 597
    .end local v42           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v43       #appWidget:Lcom/android/server/AppWidgetService;
    :catch_95a
    move-exception v64

    move-object/from16 v42, v43

    .end local v43           #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v42       #appWidget:Lcom/android/server/AppWidgetService;
    goto/16 :goto_89f

    .line 573
    .end local v93           #serial:Lcom/android/server/SerialService;
    .restart local v94       #serial:Lcom/android/server/SerialService;
    :catch_95f
    move-exception v64

    move-object/from16 v93, v94

    .end local v94           #serial:Lcom/android/server/SerialService;
    .restart local v93       #serial:Lcom/android/server/SerialService;
    goto/16 :goto_87b

    .line 564
    .end local v105           #usb:Lcom/android/server/usb/UsbService;
    .restart local v106       #usb:Lcom/android/server/usb/UsbService;
    :catch_964
    move-exception v64

    move-object/from16 v105, v106

    .end local v106           #usb:Lcom/android/server/usb/UsbService;
    .restart local v105       #usb:Lcom/android/server/usb/UsbService;
    goto/16 :goto_86f

    .line 529
    .end local v110           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v111       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :catch_969
    move-exception v64

    move-object/from16 v110, v111

    .end local v111           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v110       #wallpaper:Lcom/android/server/WallpaperManagerService;
    goto/16 :goto_83f

    .line 501
    .end local v55           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v56       #countryDetector:Lcom/android/server/CountryDetectorService;
    :catch_96e
    move-exception v64

    move-object/from16 v55, v56

    .end local v56           #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v55       #countryDetector:Lcom/android/server/CountryDetectorService;
    goto/16 :goto_81b

    .line 493
    .end local v73           #location:Lcom/android/server/LocationManagerService;
    .restart local v74       #location:Lcom/android/server/LocationManagerService;
    :catch_973
    move-exception v64

    move-object/from16 v73, v74

    .end local v74           #location:Lcom/android/server/LocationManagerService;
    .restart local v73       #location:Lcom/android/server/LocationManagerService;
    goto/16 :goto_80f

    .line 477
    .end local v84           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v85       #notification:Lcom/android/server/NotificationManagerService;
    :catch_978
    move-exception v64

    move-object/from16 v84, v85

    .end local v85           #notification:Lcom/android/server/NotificationManagerService;
    .restart local v84       #notification:Lcom/android/server/NotificationManagerService;
    goto/16 :goto_7f7

    .line 451
    .end local v99           #throttle:Lcom/android/server/ThrottleService;
    .restart local v100       #throttle:Lcom/android/server/ThrottleService;
    :catch_97d
    move-exception v64

    move-object/from16 v99, v100

    .end local v100           #throttle:Lcom/android/server/ThrottleService;
    .restart local v99       #throttle:Lcom/android/server/ThrottleService;
    goto/16 :goto_7df

    .line 433
    .end local v53           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v54       #connectivity:Lcom/android/server/ConnectivityService;
    :catch_982
    move-exception v64

    move-object/from16 v53, v54

    .end local v54           #connectivity:Lcom/android/server/ConnectivityService;
    .restart local v53       #connectivity:Lcom/android/server/ConnectivityService;
    goto/16 :goto_7c7

    .line 420
    .end local v112           #wifi:Lcom/android/server/WifiService;
    .restart local v113       #wifi:Lcom/android/server/WifiService;
    :catch_987
    move-exception v64

    move-object/from16 v112, v113

    .end local v113           #wifi:Lcom/android/server/WifiService;
    .restart local v112       #wifi:Lcom/android/server/WifiService;
    goto/16 :goto_7bb

    .line 412
    .end local v114           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v115       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    :catch_98c
    move-exception v64

    move-object/from16 v114, v115

    .end local v115           #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    .restart local v114       #wifiP2p:Landroid/net/wifi/p2p/WifiP2pService;
    goto/16 :goto_7af

    .line 404
    :catch_991
    move-exception v64

    goto/16 :goto_7a3

    .line 394
    .end local v8           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .end local v12           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v80       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v81       #networkStats:Lcom/android/server/net/NetworkStatsService;
    :catch_994
    move-exception v64

    move-object/from16 v12, v81

    .end local v81           #networkStats:Lcom/android/server/net/NetworkStatsService;
    .restart local v12       #networkStats:Lcom/android/server/net/NetworkStatsService;
    goto/16 :goto_795

    .line 386
    .end local v101           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v102       #tsms:Lcom/android/server/TextServicesManagerService;
    :catch_999
    move-exception v64

    move-object/from16 v101, v102

    .end local v102           #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v101       #tsms:Lcom/android/server/TextServicesManagerService;
    goto/16 :goto_789

    .line 362
    .end local v97           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v98       #statusBar:Lcom/android/server/StatusBarManagerService;
    :catch_99e
    move-exception v64

    move-object/from16 v97, v98

    .end local v98           #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v97       #statusBar:Lcom/android/server/StatusBarManagerService;
    goto/16 :goto_765

    .line 354
    .end local v58           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v59       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    :catch_9a3
    move-exception v64

    move-object/from16 v58, v59

    .end local v59           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v58       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    goto/16 :goto_759

    .line 346
    .end local v75           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v76       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    :catch_9a8
    move-exception v64

    move-object/from16 v75, v76

    .end local v76           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v75       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    goto/16 :goto_74d

    .line 337
    .end local v78           #mountService:Lcom/android/server/MountService;
    .restart local v79       #mountService:Lcom/android/server/MountService;
    :catch_9ad
    move-exception v64

    move-object/from16 v78, v79

    .end local v79           #mountService:Lcom/android/server/MountService;
    .restart local v78       #mountService:Lcom/android/server/MountService;
    goto/16 :goto_741

    .line 323
    .end local v78           #mountService:Lcom/android/server/MountService;
    :catch_9b2
    move-exception v3

    goto/16 :goto_263

    .line 293
    .end local v68           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v69       #imm:Lcom/android/server/InputMethodManagerService;
    :catch_9b5
    move-exception v64

    move-object/from16 v68, v69

    .end local v69           #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v68       #imm:Lcom/android/server/InputMethodManagerService;
    goto/16 :goto_711

    .line 270
    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v6           #power:Lcom/android/server/PowerManagerService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v42           #appWidget:Lcom/android/server/AppWidgetService;
    .end local v55           #countryDetector:Lcom/android/server/CountryDetectorService;
    .end local v58           #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .end local v62           #dreamy:Landroid/service/dreams/DreamManagerService;
    .end local v68           #imm:Lcom/android/server/InputMethodManagerService;
    .end local v73           #location:Lcom/android/server/LocationManagerService;
    .end local v75           #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .end local v84           #notification:Lcom/android/server/NotificationManagerService;
    .end local v97           #statusBar:Lcom/android/server/StatusBarManagerService;
    .end local v101           #tsms:Lcom/android/server/TextServicesManagerService;
    .end local v110           #wallpaper:Lcom/android/server/WallpaperManagerService;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #battery:Lcom/android/server/BatteryService;
    .restart local v88       #power:Lcom/android/server/PowerManagerService;
    :catch_9ba
    move-exception v64

    move-object/from16 v7, v41

    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v44

    .end local v44           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    move-object/from16 v6, v88

    .end local v88           #power:Lcom/android/server/PowerManagerService;
    .restart local v6       #power:Lcom/android/server/PowerManagerService;
    goto/16 :goto_691

    .end local v5           #battery:Lcom/android/server/BatteryService;
    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v44       #battery:Lcom/android/server/BatteryService;
    .restart local v57       #cryptState:Ljava/lang/String;
    .restart local v67       #firstBoot:Z
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v86       #onlyCore:Z
    :catch_9c3
    move-exception v64

    move-object/from16 v7, v41

    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v5, v44

    .end local v44           #battery:Lcom/android/server/BatteryService;
    .restart local v5       #battery:Lcom/android/server/BatteryService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_691

    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    :catch_9cc
    move-exception v64

    move-object/from16 v7, v41

    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_691

    .end local v7           #alarm:Lcom/android/server/AlarmManagerService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .restart local v41       #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v108       #vibrator:Lcom/android/server/VibratorService;
    :catch_9d3
    move-exception v64

    move-object/from16 v7, v41

    .end local v41           #alarm:Lcom/android/server/AlarmManagerService;
    .restart local v7       #alarm:Lcom/android/server/AlarmManagerService;
    move-object/from16 v107, v108

    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_691

    .end local v45           #bluetooth:Landroid/server/BluetoothService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .restart local v46       #bluetooth:Landroid/server/BluetoothService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v108       #vibrator:Lcom/android/server/VibratorService;
    :catch_9dc
    move-exception v64

    move-object/from16 v45, v46

    .end local v46           #bluetooth:Landroid/server/BluetoothService;
    .restart local v45       #bluetooth:Landroid/server/BluetoothService;
    move-object/from16 v107, v108

    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_691

    .end local v45           #bluetooth:Landroid/server/BluetoothService;
    .end local v47           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .end local v71           #lights:Lcom/android/server/LightsService;
    .end local v107           #vibrator:Lcom/android/server/VibratorService;
    .restart local v46       #bluetooth:Landroid/server/BluetoothService;
    .restart local v48       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v72       #lights:Lcom/android/server/LightsService;
    .restart local v108       #vibrator:Lcom/android/server/VibratorService;
    :catch_9e5
    move-exception v64

    move-object/from16 v47, v48

    .end local v48           #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    .restart local v47       #bluetoothA2dp:Landroid/server/BluetoothA2dpService;
    move-object/from16 v45, v46

    .end local v46           #bluetooth:Landroid/server/BluetoothService;
    .restart local v45       #bluetooth:Landroid/server/BluetoothService;
    move-object/from16 v107, v108

    .end local v108           #vibrator:Lcom/android/server/VibratorService;
    .restart local v107       #vibrator:Lcom/android/server/VibratorService;
    move-object/from16 v71, v72

    .end local v72           #lights:Lcom/android/server/LightsService;
    .restart local v71       #lights:Lcom/android/server/LightsService;
    goto/16 :goto_691

    .end local v57           #cryptState:Ljava/lang/String;
    .end local v67           #firstBoot:Z
    .end local v86           #onlyCore:Z
    .restart local v42       #appWidget:Lcom/android/server/AppWidgetService;
    .restart local v55       #countryDetector:Lcom/android/server/CountryDetectorService;
    .restart local v58       #devicePolicy:Lcom/android/server/DevicePolicyManagerService;
    .restart local v62       #dreamy:Landroid/service/dreams/DreamManagerService;
    .restart local v68       #imm:Lcom/android/server/InputMethodManagerService;
    .restart local v73       #location:Lcom/android/server/LocationManagerService;
    .restart local v75       #lockSettings:Lcom/android/internal/widget/LockSettingsService;
    .restart local v84       #notification:Lcom/android/server/NotificationManagerService;
    .restart local v97       #statusBar:Lcom/android/server/StatusBarManagerService;
    .restart local v101       #tsms:Lcom/android/server/TextServicesManagerService;
    .restart local v110       #wallpaper:Lcom/android/server/WallpaperManagerService;
    :cond_9f0
    move-object/from16 v8, v80

    .end local v80           #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    .restart local v8       #networkPolicy:Lcom/android/server/net/NetworkPolicyManagerService;
    goto/16 :goto_5a8
.end method
