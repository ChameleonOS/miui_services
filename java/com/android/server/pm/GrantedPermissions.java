// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import java.util.HashSet;

class GrantedPermissions {

    GrantedPermissions(int i) {
        grantedPermissions = new HashSet();
        setFlags(i);
    }

    GrantedPermissions(GrantedPermissions grantedpermissions) {
        grantedPermissions = new HashSet();
        pkgFlags = grantedpermissions.pkgFlags;
        grantedPermissions = (HashSet)grantedpermissions.grantedPermissions.clone();
        if(grantedpermissions.gids != null)
            gids = (int[])grantedpermissions.gids.clone();
    }

    void setFlags(int i) {
        pkgFlags = 0xa0040001 & i;
    }

    int gids[];
    HashSet grantedPermissions;
    int pkgFlags;
}
