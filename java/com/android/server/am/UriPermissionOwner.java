// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import java.util.HashSet;
import java.util.Iterator;

// Referenced classes of package com.android.server.am:
//            UriPermission, ActivityManagerService

class UriPermissionOwner {
    class ExternalToken extends Binder {

        UriPermissionOwner getOwner() {
            return UriPermissionOwner.this;
        }

        final UriPermissionOwner this$0;

        ExternalToken() {
            this$0 = UriPermissionOwner.this;
            super();
        }
    }


    UriPermissionOwner(ActivityManagerService activitymanagerservice, Object obj) {
        service = activitymanagerservice;
        owner = obj;
    }

    static UriPermissionOwner fromExternalToken(IBinder ibinder) {
        UriPermissionOwner uripermissionowner;
        if(ibinder instanceof ExternalToken)
            uripermissionowner = ((ExternalToken)ibinder).getOwner();
        else
            uripermissionowner = null;
        return uripermissionowner;
    }

    public void addReadPermission(UriPermission uripermission) {
        if(readUriPermissions == null)
            readUriPermissions = new HashSet();
        readUriPermissions.add(uripermission);
    }

    public void addWritePermission(UriPermission uripermission) {
        if(writeUriPermissions == null)
            writeUriPermissions = new HashSet();
        writeUriPermissions.add(uripermission);
    }

    Binder getExternalTokenLocked() {
        if(externalToken == null)
            externalToken = new ExternalToken();
        return externalToken;
    }

    public void removeReadPermission(UriPermission uripermission) {
        readUriPermissions.remove(uripermission);
        if(readUriPermissions.size() == 0)
            readUriPermissions = null;
    }

    void removeUriPermissionLocked(Uri uri, int i) {
        if((i & 1) != 0 && readUriPermissions != null) {
            Iterator iterator1 = readUriPermissions.iterator();
            do {
                if(!iterator1.hasNext())
                    break;
                UriPermission uripermission1 = (UriPermission)iterator1.next();
                if(uri.equals(uripermission1.uri)) {
                    uripermission1.readOwners.remove(this);
                    if(uripermission1.readOwners.size() == 0 && (1 & uripermission1.globalModeFlags) == 0) {
                        uripermission1.modeFlags = -2 & uripermission1.modeFlags;
                        service.removeUriPermissionIfNeededLocked(uripermission1);
                    }
                    iterator1.remove();
                }
            } while(true);
            if(readUriPermissions.size() == 0)
                readUriPermissions = null;
        }
        if((i & 2) != 0 && writeUriPermissions != null) {
            Iterator iterator = writeUriPermissions.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                UriPermission uripermission = (UriPermission)iterator.next();
                if(uri.equals(uripermission.uri)) {
                    uripermission.writeOwners.remove(this);
                    if(uripermission.writeOwners.size() == 0 && (2 & uripermission.globalModeFlags) == 0) {
                        uripermission.modeFlags = -3 & uripermission.modeFlags;
                        service.removeUriPermissionIfNeededLocked(uripermission);
                    }
                    iterator.remove();
                }
            } while(true);
            if(writeUriPermissions.size() == 0)
                writeUriPermissions = null;
        }
    }

    void removeUriPermissionsLocked() {
        removeUriPermissionsLocked(3);
    }

    void removeUriPermissionsLocked(int i) {
        if((i & 1) != 0 && readUriPermissions != null) {
            Iterator iterator1 = readUriPermissions.iterator();
            do {
                if(!iterator1.hasNext())
                    break;
                UriPermission uripermission1 = (UriPermission)iterator1.next();
                uripermission1.readOwners.remove(this);
                if(uripermission1.readOwners.size() == 0 && (1 & uripermission1.globalModeFlags) == 0) {
                    uripermission1.modeFlags = -2 & uripermission1.modeFlags;
                    service.removeUriPermissionIfNeededLocked(uripermission1);
                }
            } while(true);
            readUriPermissions = null;
        }
        if((i & 2) != 0 && writeUriPermissions != null) {
            Iterator iterator = writeUriPermissions.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                UriPermission uripermission = (UriPermission)iterator.next();
                uripermission.writeOwners.remove(this);
                if(uripermission.writeOwners.size() == 0 && (2 & uripermission.globalModeFlags) == 0) {
                    uripermission.modeFlags = -3 & uripermission.modeFlags;
                    service.removeUriPermissionIfNeededLocked(uripermission);
                }
            } while(true);
            writeUriPermissions = null;
        }
    }

    public void removeWritePermission(UriPermission uripermission) {
        writeUriPermissions.remove(uripermission);
        if(writeUriPermissions.size() == 0)
            writeUriPermissions = null;
    }

    public String toString() {
        return owner.toString();
    }

    Binder externalToken;
    final Object owner;
    HashSet readUriPermissions;
    final ActivityManagerService service;
    HashSet writeUriPermissions;
}
