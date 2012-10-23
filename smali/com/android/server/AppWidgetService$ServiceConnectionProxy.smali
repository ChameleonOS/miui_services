.class Lcom/android/server/AppWidgetService$ServiceConnectionProxy;
.super Ljava/lang/Object;
.source "AppWidgetService.java"

# interfaces
.implements Landroid/content/ServiceConnection;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/AppWidgetService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "ServiceConnectionProxy"
.end annotation


# instance fields
.field private final mConnectionCb:Landroid/os/IBinder;


# direct methods
.method constructor <init>(Landroid/util/Pair;Landroid/os/IBinder;)V
    .registers 3
    .parameter
    .parameter "connectionCb"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/util/Pair",
            "<",
            "Ljava/lang/Integer;",
            "Landroid/content/Intent$FilterComparison;",
            ">;",
            "Landroid/os/IBinder;",
            ")V"
        }
    .end annotation

    .prologue
    .line 103
    .local p1, key:Landroid/util/Pair;,"Landroid/util/Pair<Ljava/lang/Integer;Landroid/content/Intent$FilterComparison;>;"
    invoke-direct/range {p0 .. p0}, Ljava/lang/Object;-><init>()V

    .line 104
    iput-object p2, p0, Lcom/android/server/AppWidgetService$ServiceConnectionProxy;->mConnectionCb:Landroid/os/IBinder;

    .line 105
    return-void
.end method


# virtual methods
.method public disconnect()V
    .registers 4

    .prologue
    .line 119
    iget-object v2, p0, Lcom/android/server/AppWidgetService$ServiceConnectionProxy;->mConnectionCb:Landroid/os/IBinder;

    invoke-static {v2}, Lcom/android/internal/widget/IRemoteViewsAdapterConnection$Stub;->asInterface(Landroid/os/IBinder;)Lcom/android/internal/widget/IRemoteViewsAdapterConnection;

    move-result-object v0

    .line 122
    .local v0, cb:Lcom/android/internal/widget/IRemoteViewsAdapterConnection;
    :try_start_6
    invoke-interface {v0}, Lcom/android/internal/widget/IRemoteViewsAdapterConnection;->onServiceDisconnected()V
    :try_end_9
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_9} :catch_a

    .line 126
    :goto_9
    return-void

    .line 123
    :catch_a
    move-exception v1

    .line 124
    .local v1, e:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_9
.end method

.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .registers 6
    .parameter "name"
    .parameter "service"

    .prologue
    .line 107
    iget-object v2, p0, Lcom/android/server/AppWidgetService$ServiceConnectionProxy;->mConnectionCb:Landroid/os/IBinder;

    invoke-static {v2}, Lcom/android/internal/widget/IRemoteViewsAdapterConnection$Stub;->asInterface(Landroid/os/IBinder;)Lcom/android/internal/widget/IRemoteViewsAdapterConnection;

    move-result-object v0

    .line 110
    .local v0, cb:Lcom/android/internal/widget/IRemoteViewsAdapterConnection;
    :try_start_6
    invoke-interface {v0, p2}, Lcom/android/internal/widget/IRemoteViewsAdapterConnection;->onServiceConnected(Landroid/os/IBinder;)V
    :try_end_9
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_9} :catch_a

    .line 114
    :goto_9
    return-void

    .line 111
    :catch_a
    move-exception v1

    .line 112
    .local v1, e:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_9
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .registers 2
    .parameter "name"

    .prologue
    .line 116
    invoke-virtual {p0}, Lcom/android/server/AppWidgetService$ServiceConnectionProxy;->disconnect()V

    .line 117
    return-void
.end method
