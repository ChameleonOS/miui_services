.class final Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;
.super Landroid/view/InputEventReceiver;
.source "WindowManagerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/server/wm/WindowManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x10
    name = "DragInputEventReceiver"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/server/wm/WindowManagerService;


# direct methods
.method public constructor <init>(Lcom/android/server/wm/WindowManagerService;Landroid/view/InputChannel;Landroid/os/Looper;)V
    .registers 4
    .parameter
    .parameter "inputChannel"
    .parameter "looper"

    .prologue
    .line 683
    iput-object p1, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    .line 684
    invoke-direct {p0, p2, p3}, Landroid/view/InputEventReceiver;-><init>(Landroid/view/InputChannel;Landroid/os/Looper;)V

    .line 685
    return-void
.end method


# virtual methods
.method public onInputEvent(Landroid/view/InputEvent;)V
    .registers 11
    .parameter "event"

    .prologue
    .line 689
    const/4 v3, 0x0

    .line 691
    .local v3, handled:Z
    :try_start_1
    instance-of v7, p1, Landroid/view/MotionEvent;

    if-eqz v7, :cond_37

    invoke-virtual {p1}, Landroid/view/InputEvent;->getSource()I

    move-result v7

    and-int/lit8 v7, v7, 0x2

    if-eqz v7, :cond_37

    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v7, v7, Lcom/android/server/wm/WindowManagerService;->mDragState:Lcom/android/server/wm/DragState;

    if-eqz v7, :cond_37

    .line 694
    move-object v0, p1

    check-cast v0, Landroid/view/MotionEvent;

    move-object v4, v0

    .line 695
    .local v4, motionEvent:Landroid/view/MotionEvent;
    const/4 v2, 0x0

    .line 696
    .local v2, endDrag:Z
    invoke-virtual {v4}, Landroid/view/MotionEvent;->getRawX()F

    move-result v5

    .line 697
    .local v5, newX:F
    invoke-virtual {v4}, Landroid/view/MotionEvent;->getRawY()F

    move-result v6

    .line 699
    .local v6, newY:F
    invoke-virtual {v4}, Landroid/view/MotionEvent;->getAction()I

    move-result v7

    packed-switch v7, :pswitch_data_74

    .line 727
    :goto_27
    :pswitch_27
    if-eqz v2, :cond_36

    .line 730
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v8, v7, Lcom/android/server/wm/WindowManagerService;->mWindowMap:Ljava/util/HashMap;

    monitor-enter v8
    :try_end_2e
    .catchall {:try_start_1 .. :try_end_2e} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_2e} :catch_4c

    .line 731
    :try_start_2e
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v7, v7, Lcom/android/server/wm/WindowManagerService;->mDragState:Lcom/android/server/wm/DragState;

    invoke-virtual {v7}, Lcom/android/server/wm/DragState;->endDragLw()V

    .line 732
    monitor-exit v8
    :try_end_36
    .catchall {:try_start_2e .. :try_end_36} :catchall_71

    .line 735
    :cond_36
    const/4 v3, 0x1

    .line 740
    .end local v2           #endDrag:Z
    .end local v4           #motionEvent:Landroid/view/MotionEvent;
    .end local v5           #newX:F
    .end local v6           #newY:F
    :cond_37
    invoke-virtual {p0, p1, v3}, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->finishInputEvent(Landroid/view/InputEvent;Z)V

    .line 742
    :goto_3a
    return-void

    .line 707
    .restart local v2       #endDrag:Z
    .restart local v4       #motionEvent:Landroid/view/MotionEvent;
    .restart local v5       #newX:F
    .restart local v6       #newY:F
    :pswitch_3b
    :try_start_3b
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v8, v7, Lcom/android/server/wm/WindowManagerService;->mWindowMap:Ljava/util/HashMap;

    monitor-enter v8
    :try_end_40
    .catchall {:try_start_3b .. :try_end_40} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_3b .. :try_end_40} :catch_4c

    .line 709
    :try_start_40
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v7, v7, Lcom/android/server/wm/WindowManagerService;->mDragState:Lcom/android/server/wm/DragState;

    invoke-virtual {v7, v5, v6}, Lcom/android/server/wm/DragState;->notifyMoveLw(FF)V

    .line 710
    monitor-exit v8

    goto :goto_27

    :catchall_49
    move-exception v7

    monitor-exit v8
    :try_end_4b
    .catchall {:try_start_40 .. :try_end_4b} :catchall_49

    :try_start_4b
    throw v7
    :try_end_4c
    .catchall {:try_start_4b .. :try_end_4c} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_4b .. :try_end_4c} :catch_4c

    .line 737
    .end local v2           #endDrag:Z
    .end local v4           #motionEvent:Landroid/view/MotionEvent;
    .end local v5           #newX:F
    .end local v6           #newY:F
    :catch_4c
    move-exception v1

    .line 738
    .local v1, e:Ljava/lang/Exception;
    :try_start_4d
    const-string v7, "WindowManager"

    const-string v8, "Exception caught by drag handleMotion"

    invoke-static {v7, v8, v1}, Landroid/util/Slog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_54
    .catchall {:try_start_4d .. :try_end_54} :catchall_6a

    .line 740
    invoke-virtual {p0, p1, v3}, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->finishInputEvent(Landroid/view/InputEvent;Z)V

    goto :goto_3a

    .line 716
    .end local v1           #e:Ljava/lang/Exception;
    .restart local v2       #endDrag:Z
    .restart local v4       #motionEvent:Landroid/view/MotionEvent;
    .restart local v5       #newX:F
    .restart local v6       #newY:F
    :pswitch_58
    :try_start_58
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v8, v7, Lcom/android/server/wm/WindowManagerService;->mWindowMap:Ljava/util/HashMap;

    monitor-enter v8
    :try_end_5d
    .catchall {:try_start_58 .. :try_end_5d} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_58 .. :try_end_5d} :catch_4c

    .line 717
    :try_start_5d
    iget-object v7, p0, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->this$0:Lcom/android/server/wm/WindowManagerService;

    iget-object v7, v7, Lcom/android/server/wm/WindowManagerService;->mDragState:Lcom/android/server/wm/DragState;

    invoke-virtual {v7, v5, v6}, Lcom/android/server/wm/DragState;->notifyDropLw(FF)Z

    move-result v2

    .line 718
    monitor-exit v8

    goto :goto_27

    :catchall_67
    move-exception v7

    monitor-exit v8
    :try_end_69
    .catchall {:try_start_5d .. :try_end_69} :catchall_67

    :try_start_69
    throw v7
    :try_end_6a
    .catchall {:try_start_69 .. :try_end_6a} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_69 .. :try_end_6a} :catch_4c

    .line 740
    .end local v2           #endDrag:Z
    .end local v4           #motionEvent:Landroid/view/MotionEvent;
    .end local v5           #newX:F
    .end local v6           #newY:F
    :catchall_6a
    move-exception v7

    invoke-virtual {p0, p1, v3}, Lcom/android/server/wm/WindowManagerService$DragInputEventReceiver;->finishInputEvent(Landroid/view/InputEvent;Z)V

    throw v7

    .line 723
    .restart local v2       #endDrag:Z
    .restart local v4       #motionEvent:Landroid/view/MotionEvent;
    .restart local v5       #newX:F
    .restart local v6       #newY:F
    :pswitch_6f
    const/4 v2, 0x1

    goto :goto_27

    .line 732
    :catchall_71
    move-exception v7

    :try_start_72
    monitor-exit v8
    :try_end_73
    .catchall {:try_start_72 .. :try_end_73} :catchall_71

    :try_start_73
    throw v7
    :try_end_74
    .catchall {:try_start_73 .. :try_end_74} :catchall_6a
    .catch Ljava/lang/Exception; {:try_start_73 .. :try_end_74} :catch_4c

    .line 699
    :pswitch_data_74
    .packed-switch 0x0
        :pswitch_27
        :pswitch_58
        :pswitch_3b
        :pswitch_6f
    .end packed-switch
.end method
