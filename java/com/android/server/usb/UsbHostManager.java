// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.usb;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.usb.*;
import android.os.*;
import android.util.Slog;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;

// Referenced classes of package com.android.server.usb:
//            UsbSettingsManager

public class UsbHostManager {

    public UsbHostManager(Context context, UsbSettingsManager usbsettingsmanager) {
        mContext = context;
        mSettingsManager = usbsettingsmanager;
        mHostBlacklist = context.getResources().getStringArray(0x107001f);
    }

    private boolean isBlackListed(int i, int j, int k) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_3;
        if(i != 9 && (i != 3 || j != flag))
            flag = false;
        return flag;
    }

    private boolean isBlackListed(String s) {
        int i;
        int j;
        i = mHostBlacklist.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_38;
        if(!s.startsWith(mHostBlacklist[j])) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private native void monitorUsbHostBus();

    private native ParcelFileDescriptor nativeOpenDevice(String s);

    private void usbDeviceAdded(String s, int i, int j, int k, int l, int i1, int ai[], 
            int ai1[]) {
        if(!isBlackListed(s) && !isBlackListed(k, l, i1)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(mDevices.get(s) == null)
            break MISSING_BLOCK_LABEL_82;
        Slog.w(TAG, (new StringBuilder()).append("device already on mDevices list: ").append(s).toString());
          goto _L1
        Exception exception;
        exception;
        throw exception;
        int j1;
        UsbInterface ausbinterface[];
        j1 = ai.length / 5;
        ausbinterface = new UsbInterface[j1];
        int k1;
        int l1;
        int i2;
        k1 = 0;
        l1 = 0;
        i2 = 0;
_L8:
        if(l1 >= j1) goto _L4; else goto _L3
_L3:
        int j2 = i2 + 1;
        int k2 = ai[i2];
        int l2 = j2 + 1;
        int i3 = ai[j2];
        int j3 = l2 + 1;
        int k3 = ai[l2];
        l2 = j3 + 1;
        int l3 = ai[j3];
        int i4 = l2 + 1;
        int j4;
        UsbEndpoint ausbendpoint[];
        int k4;
        int l4;
        j4 = ai[l2];
        ausbendpoint = new UsbEndpoint[j4];
        k4 = 0;
        l4 = k1;
_L5:
        int i5;
        int j5;
        if(k4 >= j4)
            break MISSING_BLOCK_LABEL_272;
        i5 = l4 + 1;
        j5 = ai1[l4];
        l4 = i5 + 1;
        int k5 = ai1[i5];
        int l5 = l4 + 1;
        int i6 = ai1[l4];
        l4 = l5 + 1;
        ausbendpoint[k4] = new UsbEndpoint(j5, k5, i6, ai1[l5]);
        k4++;
          goto _L5
        boolean flag = isBlackListed(i3, k3, l3);
        if(!flag) goto _L7; else goto _L6
_L6:
        obj;
        JVM INSTR monitorexit ;
          goto _L1
_L7:
        ausbinterface[l1] = new UsbInterface(k2, i3, k3, l3, ausbendpoint);
        l1++;
        k1 = l4;
        i2 = i4;
          goto _L8
        Exception exception1;
        exception1;
_L9:
        Slog.e(TAG, "error parsing USB descriptors", exception1);
        obj;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        UsbDevice usbdevice = new UsbDevice(s, i, j, k, l, i1, ausbinterface);
        mDevices.put(s, usbdevice);
        mSettingsManager.deviceAttached(usbdevice);
        obj;
        JVM INSTR monitorexit ;
          goto _L1
        exception1;
        l2;
          goto _L9
        exception1;
        l4;
          goto _L9
    }

    private void usbDeviceRemoved(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        UsbDevice usbdevice = (UsbDevice)mDevices.remove(s);
        if(usbdevice != null)
            mSettingsManager.deviceDetached(usbdevice);
        return;
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("  USB Host State:");
        String s;
        for(Iterator iterator = mDevices.keySet().iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s).append(": ").append(mDevices.get(s)).toString()))
            s = (String)iterator.next();

        break MISSING_BLOCK_LABEL_100;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
    }

    public void getDeviceList(Bundle bundle) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        String s;
        for(Iterator iterator = mDevices.keySet().iterator(); iterator.hasNext(); bundle.putParcelable(s, (Parcelable)mDevices.get(s)))
            s = (String)iterator.next();

        break MISSING_BLOCK_LABEL_69;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
    }

    public ParcelFileDescriptor openDevice(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(isBlackListed(s))
            throw new SecurityException("USB device is on a restricted bus");
        break MISSING_BLOCK_LABEL_30;
        Exception exception;
        exception;
        throw exception;
        ParcelFileDescriptor parcelfiledescriptor;
        UsbDevice usbdevice = (UsbDevice)mDevices.get(s);
        if(usbdevice == null)
            throw new IllegalArgumentException((new StringBuilder()).append("device ").append(s).append(" does not exist or is restricted").toString());
        mSettingsManager.checkPermission(usbdevice);
        parcelfiledescriptor = nativeOpenDevice(s);
        obj;
        JVM INSTR monitorexit ;
        return parcelfiledescriptor;
    }

    public void systemReady() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        (new Thread(null, new Runnable() {

            public void run() {
                monitorUsbHostBus();
            }

            final UsbHostManager this$0;

             {
                this$0 = UsbHostManager.this;
                super();
            }
        }, "UsbService host thread")).start();
        return;
    }

    private static final boolean LOG;
    private static final String TAG = com/android/server/usb/UsbHostManager.getSimpleName();
    private final Context mContext;
    private final HashMap mDevices = new HashMap();
    private final String mHostBlacklist[];
    private final Object mLock = new Object();
    private final UsbSettingsManager mSettingsManager;


}
