// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.net.Uri;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

// Referenced classes of package com.android.server.am:
//            UriPermissionOwner

class UriPermission {

    UriPermission(int i, Uri uri1) {
        modeFlags = 0;
        globalModeFlags = 0;
        uid = i;
        uri = uri1;
    }

    void clearModes(int i) {
        if((i & 1) != 0) {
            globalModeFlags = -2 & globalModeFlags;
            modeFlags = -2 & modeFlags;
            if(readOwners.size() > 0) {
                for(Iterator iterator1 = readOwners.iterator(); iterator1.hasNext(); ((UriPermissionOwner)iterator1.next()).removeReadPermission(this));
                readOwners.clear();
            }
        }
        if((i & 2) != 0) {
            globalModeFlags = -3 & globalModeFlags;
            modeFlags = -3 & modeFlags;
            if(writeOwners.size() > 0) {
                for(Iterator iterator = writeOwners.iterator(); iterator.hasNext(); ((UriPermissionOwner)iterator.next()).removeWritePermission(this));
                writeOwners.clear();
            }
        }
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("modeFlags=0x");
        printwriter.print(Integer.toHexString(modeFlags));
        printwriter.print(" uid=");
        printwriter.print(uid);
        printwriter.print(" globalModeFlags=0x");
        printwriter.println(Integer.toHexString(globalModeFlags));
        if(readOwners.size() != 0) {
            printwriter.print(s);
            printwriter.println("readOwners:");
            UriPermissionOwner uripermissionowner1;
            for(Iterator iterator1 = readOwners.iterator(); iterator1.hasNext(); printwriter.println(uripermissionowner1)) {
                uripermissionowner1 = (UriPermissionOwner)iterator1.next();
                printwriter.print(s);
                printwriter.print("  * ");
            }

        }
        if(writeOwners.size() != 0) {
            printwriter.print(s);
            printwriter.println("writeOwners:");
            UriPermissionOwner uripermissionowner;
            for(Iterator iterator = writeOwners.iterator(); iterator.hasNext(); printwriter.println(uripermissionowner)) {
                uripermissionowner = (UriPermissionOwner)iterator.next();
                printwriter.print(s);
                printwriter.print("  * ");
            }

        }
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("UriPermission{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(uri);
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    int globalModeFlags;
    int modeFlags;
    final HashSet readOwners = new HashSet();
    String stringName;
    final int uid;
    final Uri uri;
    final HashSet writeOwners = new HashSet();
}
