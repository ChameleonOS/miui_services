// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.net.NetworkIdentity;
import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class NetworkIdentitySet extends HashSet {

    public NetworkIdentitySet() {
    }

    public NetworkIdentitySet(DataInputStream datainputstream) throws IOException {
        int i = datainputstream.readInt();
        int j = datainputstream.readInt();
        int k = 0;
        while(k < j)  {
            if(i <= 1)
                datainputstream.readInt();
            int l = datainputstream.readInt();
            int i1 = datainputstream.readInt();
            String s = readOptionalString(datainputstream);
            String s1;
            if(i >= 3)
                s1 = readOptionalString(datainputstream);
            else
                s1 = null;
            if(i >= 2)
                datainputstream.readBoolean();
            add(new NetworkIdentity(l, i1, s, s1, false));
            k++;
        }
    }

    private static String readOptionalString(DataInputStream datainputstream) throws IOException {
        String s;
        if(datainputstream.readByte() != 0)
            s = datainputstream.readUTF();
        else
            s = null;
        return s;
    }

    private static void writeOptionalString(DataOutputStream dataoutputstream, String s) throws IOException {
        if(s != null) {
            dataoutputstream.writeByte(1);
            dataoutputstream.writeUTF(s);
        } else {
            dataoutputstream.writeByte(0);
        }
    }

    public void writeToStream(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(3);
        dataoutputstream.writeInt(size());
        NetworkIdentity networkidentity;
        for(Iterator iterator = iterator(); iterator.hasNext(); dataoutputstream.writeBoolean(networkidentity.getRoaming())) {
            networkidentity = (NetworkIdentity)iterator.next();
            dataoutputstream.writeInt(networkidentity.getType());
            dataoutputstream.writeInt(networkidentity.getSubType());
            writeOptionalString(dataoutputstream, networkidentity.getSubscriberId());
            writeOptionalString(dataoutputstream, networkidentity.getNetworkId());
        }

    }

    private static final int VERSION_ADD_NETWORK_ID = 3;
    private static final int VERSION_ADD_ROAMING = 2;
    private static final int VERSION_INIT = 1;
}
