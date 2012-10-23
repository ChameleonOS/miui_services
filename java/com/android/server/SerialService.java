// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.content.res.Resources;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.util.ArrayList;

public class SerialService extends android.hardware.ISerialManager.Stub {

    public SerialService(Context context) {
        mContext = context;
        mSerialPorts = context.getResources().getStringArray(0x1070020);
    }

    private native ParcelFileDescriptor native_open(String s);

    public String[] getSerialPorts() {
        mContext.enforceCallingOrSelfPermission("android.permission.SERIAL_PORT", null);
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < mSerialPorts.length; i++) {
            String s = mSerialPorts[i];
            if((new File(s)).exists())
                arraylist.add(s);
        }

        String as[] = new String[arraylist.size()];
        arraylist.toArray(as);
        return as;
    }

    public ParcelFileDescriptor openSerialPort(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.SERIAL_PORT", null);
        return native_open(s);
    }

    private final Context mContext;
    private final String mSerialPorts[];
}
