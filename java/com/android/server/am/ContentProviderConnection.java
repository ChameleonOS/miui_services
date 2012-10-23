// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.os.Binder;
import android.os.SystemClock;
import android.util.TimeUtils;

// Referenced classes of package com.android.server.am:
//            ProcessRecord, ContentProviderRecord

public class ContentProviderConnection extends Binder {

    public ContentProviderConnection(ContentProviderRecord contentproviderrecord, ProcessRecord processrecord) {
        provider = contentproviderrecord;
        client = processrecord;
    }

    public String toClientString() {
        StringBuilder stringbuilder = new StringBuilder(128);
        toClientString(stringbuilder);
        return stringbuilder.toString();
    }

    public void toClientString(StringBuilder stringbuilder) {
        stringbuilder.append(client.toShortString());
        stringbuilder.append(" s");
        stringbuilder.append(stableCount);
        stringbuilder.append("/");
        stringbuilder.append(numStableIncs);
        stringbuilder.append(" u");
        stringbuilder.append(unstableCount);
        stringbuilder.append("/");
        stringbuilder.append(numUnstableIncs);
        if(waiting)
            stringbuilder.append(" WAITING");
        if(dead)
            stringbuilder.append(" DEAD");
        long l = SystemClock.elapsedRealtime();
        stringbuilder.append(" ");
        TimeUtils.formatDuration(l - createTime, stringbuilder);
    }

    public String toShortString() {
        StringBuilder stringbuilder = new StringBuilder(128);
        toShortString(stringbuilder);
        return stringbuilder.toString();
    }

    public void toShortString(StringBuilder stringbuilder) {
        stringbuilder.append(provider.toShortString());
        stringbuilder.append("->");
        toClientString(stringbuilder);
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder(128);
        stringbuilder.append("ContentProviderConnection{");
        toShortString(stringbuilder);
        stringbuilder.append('}');
        return stringbuilder.toString();
    }

    public final ProcessRecord client;
    public final long createTime = SystemClock.elapsedRealtime();
    public boolean dead;
    public int numStableIncs;
    public int numUnstableIncs;
    public final ContentProviderRecord provider;
    public int stableCount;
    public int unstableCount;
    public boolean waiting;
}
