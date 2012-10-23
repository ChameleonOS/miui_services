// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.IThumbnailReceiver;
import java.util.HashSet;

class PendingThumbnailsRecord {

    PendingThumbnailsRecord(IThumbnailReceiver ithumbnailreceiver) {
        receiver = ithumbnailreceiver;
        pendingRecords = new HashSet();
        finished = false;
    }

    boolean finished;
    HashSet pendingRecords;
    final IThumbnailReceiver receiver;
}
