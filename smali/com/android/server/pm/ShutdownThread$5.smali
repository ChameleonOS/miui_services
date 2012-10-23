.class Lcom/android/server/pm/ShutdownThread$5;
.super Ljava/lang/Thread;
.source "ShutdownThread.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/server/pm/ShutdownThread;->shutdownRadios(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/pm/ShutdownThread;

.field final synthetic val$done:[Z

.field final synthetic val$endTime:J


# direct methods
.method constructor <init>(Lcom/android/server/pm/ShutdownThread;J[Z)V
    .registers 5
    .parameter
    .parameter
    .parameter

    .prologue
    .line 424
    iput-object p1, p0, Lcom/android/server/pm/ShutdownThread$5;->this$0:Lcom/android/server/pm/ShutdownThread;

    iput-wide p2, p0, Lcom/android/server/pm/ShutdownThread$5;->val$endTime:J

    iput-object p4, p0, Lcom/android/server/pm/ShutdownThread$5;->val$done:[Z

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 15

    .prologue
    const/16 v13, 0xa

    const/4 v7, 0x1

    const/4 v8, 0x0

    .line 430
    const-string v9, "nfc"

    invoke-static {v9}, Landroid/os/ServiceManager;->checkService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v9

    invoke-static {v9}, Landroid/nfc/INfcAdapter$Stub;->asInterface(Landroid/os/IBinder;)Landroid/nfc/INfcAdapter;

    move-result-object v3

    .line 432
    .local v3, nfc:Landroid/nfc/INfcAdapter;
    const-string v9, "phone"

    invoke-static {v9}, Landroid/os/ServiceManager;->checkService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v9

    invoke-static {v9}, Lcom/android/internal/telephony/ITelephony$Stub;->asInterface(Landroid/os/IBinder;)Lcom/android/internal/telephony/ITelephony;

    move-result-object v5

    .line 434
    .local v5, phone:Lcom/android/internal/telephony/ITelephony;
    const-string v9, "bluetooth"

    invoke-static {v9}, Landroid/os/ServiceManager;->checkService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v9

    invoke-static {v9}, Landroid/bluetooth/IBluetooth$Stub;->asInterface(Landroid/os/IBinder;)Landroid/bluetooth/IBluetooth;

    move-result-object v0

    .line 439
    .local v0, bluetooth:Landroid/bluetooth/IBluetooth;
    if-eqz v3, :cond_2a

    :try_start_24
    invoke-interface {v3}, Landroid/nfc/INfcAdapter;->getState()I

    move-result v9

    if-ne v9, v7, :cond_bd

    :cond_2a
    move v4, v7

    .line 441
    .local v4, nfcOff:Z
    :goto_2b
    if-nez v4, :cond_38

    .line 442
    const-string v9, "ShutdownThread"

    const-string v10, "Turning off NFC..."

    invoke-static {v9, v10}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 443
    const/4 v9, 0x0

    invoke-interface {v3, v9}, Landroid/nfc/INfcAdapter;->disable(Z)Z
    :try_end_38
    .catch Landroid/os/RemoteException; {:try_start_24 .. :try_end_38} :catch_c0

    .line 451
    :cond_38
    :goto_38
    if-eqz v0, :cond_40

    :try_start_3a
    invoke-interface {v0}, Landroid/bluetooth/IBluetooth;->getBluetoothState()I

    move-result v9

    if-ne v9, v13, :cond_cb

    :cond_40
    move v1, v7

    .line 453
    .local v1, bluetoothOff:Z
    :goto_41
    if-nez v1, :cond_4e

    .line 454
    const-string v9, "ShutdownThread"

    const-string v10, "Disabling Bluetooth..."

    invoke-static {v9, v10}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 455
    const/4 v9, 0x0

    invoke-interface {v0, v9}, Landroid/bluetooth/IBluetooth;->disable(Z)Z
    :try_end_4e
    .catch Landroid/os/RemoteException; {:try_start_3a .. :try_end_4e} :catch_ce

    .line 463
    :cond_4e
    :goto_4e
    if-eqz v5, :cond_56

    :try_start_50
    invoke-interface {v5}, Lcom/android/internal/telephony/ITelephony;->isRadioOn()Z

    move-result v9

    if-nez v9, :cond_d9

    :cond_56
    move v6, v7

    .line 464
    .local v6, radioOff:Z
    :goto_57
    if-nez v6, :cond_64

    .line 465
    const-string v9, "ShutdownThread"

    const-string v10, "Turning off radio..."

    invoke-static {v9, v10}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 466
    const/4 v9, 0x0

    invoke-interface {v5, v9}, Lcom/android/internal/telephony/ITelephony;->setRadio(Z)Z
    :try_end_64
    .catch Landroid/os/RemoteException; {:try_start_50 .. :try_end_64} :catch_dc

    .line 473
    :cond_64
    :goto_64
    const-string v9, "ShutdownThread"

    const-string v10, "Waiting for NFC, Bluetooth and Radio..."

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 475
    :goto_6b
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v9

    iget-wide v11, p0, Lcom/android/server/pm/ShutdownThread$5;->val$endTime:J

    cmp-long v9, v9, v11

    if-gez v9, :cond_bc

    .line 476
    if-nez v1, :cond_87

    .line 478
    :try_start_77
    invoke-interface {v0}, Landroid/bluetooth/IBluetooth;->getBluetoothState()I
    :try_end_7a
    .catch Landroid/os/RemoteException; {:try_start_77 .. :try_end_7a} :catch_e9

    move-result v9

    if-ne v9, v13, :cond_e7

    move v1, v7

    .line 484
    :goto_7e
    if-eqz v1, :cond_87

    .line 485
    const-string v9, "ShutdownThread"

    const-string v10, "Bluetooth turned off."

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 488
    :cond_87
    if-nez v6, :cond_99

    .line 490
    :try_start_89
    invoke-interface {v5}, Lcom/android/internal/telephony/ITelephony;->isRadioOn()Z
    :try_end_8c
    .catch Landroid/os/RemoteException; {:try_start_89 .. :try_end_8c} :catch_f5

    move-result v9

    if-nez v9, :cond_f3

    move v6, v7

    .line 495
    :goto_90
    if-eqz v6, :cond_99

    .line 496
    const-string v9, "ShutdownThread"

    const-string v10, "Radio turned off."

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 499
    :cond_99
    if-nez v4, :cond_ab

    .line 501
    :try_start_9b
    invoke-interface {v3}, Landroid/nfc/INfcAdapter;->getState()I
    :try_end_9e
    .catch Landroid/os/RemoteException; {:try_start_9b .. :try_end_9e} :catch_101

    move-result v9

    if-ne v9, v7, :cond_ff

    move v4, v7

    .line 506
    :goto_a2
    if-eqz v6, :cond_ab

    .line 507
    const-string v9, "ShutdownThread"

    const-string v10, "NFC turned off."

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 511
    :cond_ab
    if-eqz v6, :cond_10b

    if-eqz v1, :cond_10b

    if-eqz v4, :cond_10b

    .line 512
    const-string v9, "ShutdownThread"

    const-string v10, "NFC, Radio and Bluetooth shutdown complete."

    invoke-static {v9, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 513
    iget-object v9, p0, Lcom/android/server/pm/ShutdownThread$5;->val$done:[Z

    aput-boolean v7, v9, v8

    .line 518
    :cond_bc
    return-void

    .end local v1           #bluetoothOff:Z
    .end local v4           #nfcOff:Z
    .end local v6           #radioOff:Z
    :cond_bd
    move v4, v8

    .line 439
    goto/16 :goto_2b

    .line 445
    :catch_c0
    move-exception v2

    .line 446
    .local v2, ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during NFC shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 447
    const/4 v4, 0x1

    .restart local v4       #nfcOff:Z
    goto/16 :goto_38

    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_cb
    move v1, v8

    .line 451
    goto/16 :goto_41

    .line 457
    :catch_ce
    move-exception v2

    .line 458
    .restart local v2       #ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during bluetooth shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 459
    const/4 v1, 0x1

    .restart local v1       #bluetoothOff:Z
    goto/16 :goto_4e

    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_d9
    move v6, v8

    .line 463
    goto/16 :goto_57

    .line 468
    :catch_dc
    move-exception v2

    .line 469
    .restart local v2       #ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during radio shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 470
    const/4 v6, 0x1

    .restart local v6       #radioOff:Z
    goto/16 :goto_64

    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_e7
    move v1, v8

    .line 478
    goto :goto_7e

    .line 480
    :catch_e9
    move-exception v2

    .line 481
    .restart local v2       #ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during bluetooth shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 482
    const/4 v1, 0x1

    goto :goto_7e

    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_f3
    move v6, v8

    .line 490
    goto :goto_90

    .line 491
    :catch_f5
    move-exception v2

    .line 492
    .restart local v2       #ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during radio shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 493
    const/4 v6, 0x1

    goto :goto_90

    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_ff
    move v4, v8

    .line 501
    goto :goto_a2

    .line 502
    :catch_101
    move-exception v2

    .line 503
    .restart local v2       #ex:Landroid/os/RemoteException;
    const-string v9, "ShutdownThread"

    const-string v10, "RemoteException during NFC shutdown"

    invoke-static {v9, v10, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 504
    const/4 v4, 0x1

    goto :goto_a2

    .line 516
    .end local v2           #ex:Landroid/os/RemoteException;
    :cond_10b
    const-wide/16 v9, 0x1f4

    invoke-static {v9, v10}, Landroid/os/SystemClock;->sleep(J)V

    goto/16 :goto_6b
.end method
