// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.usb;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.*;
import java.io.*;

// Referenced classes of package com.android.server.usb:
//            UsbSettingsManager, UsbHostManager, UsbDeviceManager

public class UsbService extends android.hardware.usb.IUsbManager.Stub {

    public UsbService(Context context) {
        mContext = context;
        mSettingsManager = new UsbSettingsManager(context);
        if(mContext.getPackageManager().hasSystemFeature("android.hardware.usb.host"))
            mHostManager = new UsbHostManager(context, mSettingsManager);
        if((new File("/sys/class/android_usb")).exists())
            mDeviceManager = new UsbDeviceManager(context, mSettingsManager);
    }

    public void clearDefaults(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        mSettingsManager.clearDefaults(s);
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printwriter.println((new StringBuilder()).append("Permission Denial: can't dump UsbManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
        } else {
            printwriter.println("USB Manager State:");
            if(mDeviceManager != null)
                mDeviceManager.dump(filedescriptor, printwriter);
            if(mHostManager != null)
                mHostManager.dump(filedescriptor, printwriter);
            mSettingsManager.dump(filedescriptor, printwriter);
        }
    }

    public UsbAccessory getCurrentAccessory() {
        UsbAccessory usbaccessory;
        if(mDeviceManager != null)
            usbaccessory = mDeviceManager.getCurrentAccessory();
        else
            usbaccessory = null;
        return usbaccessory;
    }

    public void getDeviceList(Bundle bundle) {
        if(mHostManager != null)
            mHostManager.getDeviceList(bundle);
    }

    public void grantAccessoryPermission(UsbAccessory usbaccessory, int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        mSettingsManager.grantAccessoryPermission(usbaccessory, i);
    }

    public void grantDevicePermission(UsbDevice usbdevice, int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        mSettingsManager.grantDevicePermission(usbdevice, i);
    }

    public boolean hasAccessoryPermission(UsbAccessory usbaccessory) {
        return mSettingsManager.hasPermission(usbaccessory);
    }

    public boolean hasDefaults(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        return mSettingsManager.hasDefaults(s);
    }

    public boolean hasDevicePermission(UsbDevice usbdevice) {
        return mSettingsManager.hasPermission(usbdevice);
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory usbaccessory) {
        ParcelFileDescriptor parcelfiledescriptor;
        if(mDeviceManager != null)
            parcelfiledescriptor = mDeviceManager.openAccessory(usbaccessory);
        else
            parcelfiledescriptor = null;
        return parcelfiledescriptor;
    }

    public ParcelFileDescriptor openDevice(String s) {
        ParcelFileDescriptor parcelfiledescriptor;
        if(mHostManager != null)
            parcelfiledescriptor = mHostManager.openDevice(s);
        else
            parcelfiledescriptor = null;
        return parcelfiledescriptor;
    }

    public void requestAccessoryPermission(UsbAccessory usbaccessory, String s, PendingIntent pendingintent) {
        mSettingsManager.requestPermission(usbaccessory, s, pendingintent);
    }

    public void requestDevicePermission(UsbDevice usbdevice, String s, PendingIntent pendingintent) {
        mSettingsManager.requestPermission(usbdevice, s, pendingintent);
    }

    public void setAccessoryPackage(UsbAccessory usbaccessory, String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        mSettingsManager.setAccessoryPackage(usbaccessory, s);
    }

    public void setCurrentFunction(String s, boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        if(mDeviceManager != null) {
            mDeviceManager.setCurrentFunctions(s, flag);
            return;
        } else {
            throw new IllegalStateException("USB device mode not supported");
        }
    }

    public void setDevicePackage(UsbDevice usbdevice, String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        mSettingsManager.setDevicePackage(usbdevice, s);
    }

    public void setMassStorageBackingFile(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        if(mDeviceManager != null) {
            mDeviceManager.setMassStorageBackingFile(s);
            return;
        } else {
            throw new IllegalStateException("USB device mode not supported");
        }
    }

    public void systemReady() {
        if(mDeviceManager != null)
            mDeviceManager.systemReady();
        if(mHostManager != null)
            mHostManager.systemReady();
    }

    private final Context mContext;
    private UsbDeviceManager mDeviceManager;
    private UsbHostManager mHostManager;
    private final UsbSettingsManager mSettingsManager;
}
