.class Lcom/android/server/MountService$ObbActionHandler;
.super Landroid/os/Handler;
.source "MountService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/MountService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "ObbActionHandler"
.end annotation


# instance fields
.field private final mActions:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lcom/android/server/MountService$ObbAction;",
            ">;"
        }
    .end annotation
.end field

.field private mBound:Z

.field final synthetic this$0:Lcom/android/server/MountService;


# direct methods
.method constructor <init>(Lcom/android/server/MountService;Landroid/os/Looper;)V
    .registers 4
    .parameter
    .parameter "l"

    .prologue
    .line 2003
    iput-object p1, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    .line 2004
    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 2000
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    .line 2001
    new-instance v0, Ljava/util/LinkedList;

    invoke-direct {v0}, Ljava/util/LinkedList;-><init>()V

    iput-object v0, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    .line 2005
    return-void
.end method

.method private connectToService()Z
    .registers 5

    .prologue
    const/4 v1, 0x1

    .line 2143
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    sget-object v3, Lcom/android/server/MountService;->DEFAULT_CONTAINER_COMPONENT:Landroid/content/ComponentName;

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    move-result-object v0

    .line 2144
    .local v0, service:Landroid/content/Intent;
    iget-object v2, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mContext:Landroid/content/Context;
    invoke-static {v2}, Lcom/android/server/MountService;->access$2400(Lcom/android/server/MountService;)Landroid/content/Context;

    move-result-object v2

    iget-object v3, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mDefContainerConn:Lcom/android/server/MountService$DefaultContainerConnection;
    invoke-static {v3}, Lcom/android/server/MountService;->access$2300(Lcom/android/server/MountService;)Lcom/android/server/MountService$DefaultContainerConnection;

    move-result-object v3

    invoke-virtual {v2, v0, v3, v1}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    move-result v2

    if-eqz v2, :cond_21

    .line 2145
    iput-boolean v1, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    .line 2148
    :goto_20
    return v1

    :cond_21
    const/4 v1, 0x0

    goto :goto_20
.end method

.method private disconnectService()V
    .registers 3

    .prologue
    .line 2152
    iget-object v0, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    const/4 v1, 0x0

    #setter for: Lcom/android/server/MountService;->mContainerService:Lcom/android/internal/app/IMediaContainerService;
    invoke-static {v0, v1}, Lcom/android/server/MountService;->access$1902(Lcom/android/server/MountService;Lcom/android/internal/app/IMediaContainerService;)Lcom/android/internal/app/IMediaContainerService;

    .line 2153
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    .line 2154
    iget-object v0, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/android/server/MountService;->access$2400(Lcom/android/server/MountService;)Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mDefContainerConn:Lcom/android/server/MountService$DefaultContainerConnection;
    invoke-static {v1}, Lcom/android/server/MountService;->access$2300(Lcom/android/server/MountService;)Lcom/android/server/MountService$DefaultContainerConnection;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 2155
    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .registers 15
    .parameter "msg"

    .prologue
    const/4 v9, 0x2

    const/4 v10, 0x0

    .line 2009
    iget v8, p1, Landroid/os/Message;->what:I

    packed-switch v8, :pswitch_data_160

    .line 2137
    :cond_7
    :goto_7
    return-void

    .line 2011
    :pswitch_8
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Lcom/android/server/MountService$ObbAction;

    .line 2019
    .local v0, action:Lcom/android/server/MountService$ObbAction;
    iget-boolean v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    if-nez v8, :cond_21

    .line 2022
    invoke-direct {p0}, Lcom/android/server/MountService$ObbActionHandler;->connectToService()Z

    move-result v8

    if-nez v8, :cond_21

    .line 2023
    const-string v8, "MountService"

    const-string v9, "Failed to bind to media container service"

    invoke-static {v8, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2024
    invoke-virtual {v0}, Lcom/android/server/MountService$ObbAction;->handleError()V

    goto :goto_7

    .line 2029
    :cond_21
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_7

    .line 2035
    .end local v0           #action:Lcom/android/server/MountService$ObbAction;
    :pswitch_27
    iget-object v8, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    if-eqz v8, :cond_34

    .line 2036
    iget-object v9, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    iget-object v8, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v8, Lcom/android/internal/app/IMediaContainerService;

    #setter for: Lcom/android/server/MountService;->mContainerService:Lcom/android/internal/app/IMediaContainerService;
    invoke-static {v9, v8}, Lcom/android/server/MountService;->access$1902(Lcom/android/server/MountService;Lcom/android/internal/app/IMediaContainerService;)Lcom/android/internal/app/IMediaContainerService;

    .line 2038
    :cond_34
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mContainerService:Lcom/android/internal/app/IMediaContainerService;
    invoke-static {v8}, Lcom/android/server/MountService;->access$1900(Lcom/android/server/MountService;)Lcom/android/internal/app/IMediaContainerService;

    move-result-object v8

    if-nez v8, :cond_5f

    .line 2040
    const-string v8, "MountService"

    const-string v9, "Cannot bind to media container service"

    invoke-static {v8, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2041
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .local v3, i$:Ljava/util/Iterator;
    :goto_49
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_59

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/server/MountService$ObbAction;

    .line 2043
    .restart local v0       #action:Lcom/android/server/MountService$ObbAction;
    invoke-virtual {v0}, Lcom/android/server/MountService$ObbAction;->handleError()V

    goto :goto_49

    .line 2045
    .end local v0           #action:Lcom/android/server/MountService$ObbAction;
    :cond_59
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->clear()V

    goto :goto_7

    .line 2046
    .end local v3           #i$:Ljava/util/Iterator;
    :cond_5f
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v8

    if-lez v8, :cond_75

    .line 2047
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/server/MountService$ObbAction;

    .line 2048
    .restart local v0       #action:Lcom/android/server/MountService$ObbAction;
    if-eqz v0, :cond_7

    .line 2049
    invoke-virtual {v0, p0}, Lcom/android/server/MountService$ObbAction;->execute(Lcom/android/server/MountService$ObbActionHandler;)V

    goto :goto_7

    .line 2053
    .end local v0           #action:Lcom/android/server/MountService$ObbAction;
    :cond_75
    const-string v8, "MountService"

    const-string v9, "Empty queue"

    invoke-static {v8, v9}, Landroid/util/Slog;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_7

    .line 2060
    :pswitch_7d
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v8

    if-lez v8, :cond_7

    .line 2061
    iget-boolean v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    if-eqz v8, :cond_8c

    .line 2062
    invoke-direct {p0}, Lcom/android/server/MountService$ObbActionHandler;->disconnectService()V

    .line 2064
    :cond_8c
    invoke-direct {p0}, Lcom/android/server/MountService$ObbActionHandler;->connectToService()Z

    move-result v8

    if-nez v8, :cond_7

    .line 2065
    const-string v8, "MountService"

    const-string v9, "Failed to bind to media container service"

    invoke-static {v8, v9}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2066
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .restart local v3       #i$:Ljava/util/Iterator;
    :goto_9f
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_af

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/server/MountService$ObbAction;

    .line 2068
    .restart local v0       #action:Lcom/android/server/MountService$ObbAction;
    invoke-virtual {v0}, Lcom/android/server/MountService$ObbAction;->handleError()V

    goto :goto_9f

    .line 2070
    .end local v0           #action:Lcom/android/server/MountService$ObbAction;
    :cond_af
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->clear()V

    goto/16 :goto_7

    .line 2080
    .end local v3           #i$:Ljava/util/Iterator;
    :pswitch_b6
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v8

    if-lez v8, :cond_c3

    .line 2081
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8, v10}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    .line 2083
    :cond_c3
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mActions:Ljava/util/List;

    invoke-interface {v8}, Ljava/util/List;->size()I

    move-result v8

    if-nez v8, :cond_d4

    .line 2084
    iget-boolean v8, p0, Lcom/android/server/MountService$ObbActionHandler;->mBound:Z

    if-eqz v8, :cond_7

    .line 2085
    invoke-direct {p0}, Lcom/android/server/MountService$ObbActionHandler;->disconnectService()V

    goto/16 :goto_7

    .line 2091
    :cond_d4
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mObbActionHandler:Lcom/android/server/MountService$ObbActionHandler;
    invoke-static {v8}, Lcom/android/server/MountService;->access$000(Lcom/android/server/MountService;)Lcom/android/server/MountService$ObbActionHandler;

    move-result-object v8

    invoke-virtual {v8, v9}, Lcom/android/server/MountService$ObbActionHandler;->sendEmptyMessage(I)Z

    goto/16 :goto_7

    .line 2096
    :pswitch_df
    iget-object v7, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v7, Ljava/lang/String;

    .line 2101
    .local v7, path:Ljava/lang/String;
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mObbMounts:Ljava/util/Map;
    invoke-static {v8}, Lcom/android/server/MountService;->access$2000(Lcom/android/server/MountService;)Ljava/util/Map;

    move-result-object v9

    monitor-enter v9

    .line 2102
    :try_start_ea
    new-instance v6, Ljava/util/LinkedList;

    invoke-direct {v6}, Ljava/util/LinkedList;-><init>()V

    .line 2104
    .local v6, obbStatesToRemove:Ljava/util/List;,"Ljava/util/List<Lcom/android/server/MountService$ObbState;>;"
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #getter for: Lcom/android/server/MountService;->mObbPathToStateMap:Ljava/util/Map;
    invoke-static {v8}, Lcom/android/server/MountService;->access$2100(Lcom/android/server/MountService;)Ljava/util/Map;

    move-result-object v8

    invoke-interface {v8}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v8

    invoke-interface {v8}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 2106
    .local v2, i:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/server/MountService$ObbState;>;>;"
    :cond_fd
    :goto_fd
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_120

    .line 2107
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map$Entry;

    .line 2114
    .local v4, obbEntry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/server/MountService$ObbState;>;"
    invoke-interface {v4}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-virtual {v8, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v8

    if-eqz v8, :cond_fd

    .line 2115
    invoke-interface {v4}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v8

    invoke-interface {v6, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_fd

    .line 2133
    .end local v2           #i:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/server/MountService$ObbState;>;>;"
    .end local v4           #obbEntry:Ljava/util/Map$Entry;,"Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/server/MountService$ObbState;>;"
    .end local v6           #obbStatesToRemove:Ljava/util/List;,"Ljava/util/List<Lcom/android/server/MountService$ObbState;>;"
    :catchall_11d
    move-exception v8

    monitor-exit v9
    :try_end_11f
    .catchall {:try_start_ea .. :try_end_11f} :catchall_11d

    throw v8

    .line 2119
    .restart local v2       #i:Ljava/util/Iterator;,"Ljava/util/Iterator<Ljava/util/Map$Entry<Ljava/lang/String;Lcom/android/server/MountService$ObbState;>;>;"
    .restart local v6       #obbStatesToRemove:Ljava/util/List;,"Ljava/util/List<Lcom/android/server/MountService$ObbState;>;"
    :cond_120
    :try_start_120
    invoke-interface {v6}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .restart local v3       #i$:Ljava/util/Iterator;
    :goto_124
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_15c

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/android/server/MountService$ObbState;

    .line 2123
    .local v5, obbState:Lcom/android/server/MountService$ObbState;
    iget-object v8, p0, Lcom/android/server/MountService$ObbActionHandler;->this$0:Lcom/android/server/MountService;

    #calls: Lcom/android/server/MountService;->removeObbStateLocked(Lcom/android/server/MountService$ObbState;)V
    invoke-static {v8, v5}, Lcom/android/server/MountService;->access$2200(Lcom/android/server/MountService;Lcom/android/server/MountService$ObbState;)V
    :try_end_135
    .catchall {:try_start_120 .. :try_end_135} :catchall_11d

    .line 2126
    :try_start_135
    iget-object v8, v5, Lcom/android/server/MountService$ObbState;->token:Landroid/os/storage/IObbActionListener;

    iget-object v10, v5, Lcom/android/server/MountService$ObbState;->filename:Ljava/lang/String;

    iget v11, v5, Lcom/android/server/MountService$ObbState;->nonce:I

    const/4 v12, 0x2

    invoke-interface {v8, v10, v11, v12}, Landroid/os/storage/IObbActionListener;->onObbResult(Ljava/lang/String;II)V
    :try_end_13f
    .catchall {:try_start_135 .. :try_end_13f} :catchall_11d
    .catch Landroid/os/RemoteException; {:try_start_135 .. :try_end_13f} :catch_140

    goto :goto_124

    .line 2128
    :catch_140
    move-exception v1

    .line 2129
    .local v1, e:Landroid/os/RemoteException;
    :try_start_141
    const-string v8, "MountService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Couldn\'t send unmount notification for  OBB: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    iget-object v11, v5, Lcom/android/server/MountService$ObbState;->filename:Ljava/lang/String;

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v10}, Landroid/util/Slog;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_124

    .line 2133
    .end local v1           #e:Landroid/os/RemoteException;
    .end local v5           #obbState:Lcom/android/server/MountService$ObbState;
    :cond_15c
    monitor-exit v9
    :try_end_15d
    .catchall {:try_start_141 .. :try_end_15d} :catchall_11d

    goto/16 :goto_7

    .line 2009
    nop

    :pswitch_data_160
    .packed-switch 0x1
        :pswitch_8
        :pswitch_27
        :pswitch_b6
        :pswitch_7d
        :pswitch_df
    .end packed-switch
.end method
