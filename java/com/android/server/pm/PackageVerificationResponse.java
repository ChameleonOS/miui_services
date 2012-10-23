// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;


public class PackageVerificationResponse {

    public PackageVerificationResponse(int i, int j) {
        code = i;
        callerUid = j;
    }

    public final int callerUid;
    public final int code;
}
