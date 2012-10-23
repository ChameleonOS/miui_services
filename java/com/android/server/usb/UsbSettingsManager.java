// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.usb;

import android.app.PendingIntent;
import android.content.*;
import android.content.pm.*;
import android.content.res.XmlResourceParser;
import android.hardware.usb.*;
import android.os.*;
import android.util.*;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;

class UsbSettingsManager {
    private class MyPackageMonitor extends PackageMonitor {

        public void onPackageAdded(String s, int i) {
            handlePackageUpdate(s);
        }

        public void onPackageChanged(String s, int i, String as[]) {
            handlePackageUpdate(s);
        }

        public void onPackageRemoved(String s, int i) {
            clearDefaults(s);
        }

        final UsbSettingsManager this$0;

        private MyPackageMonitor() {
            this$0 = UsbSettingsManager.this;
            super();
        }

    }

    private static class AccessoryFilter {

        public static AccessoryFilter read(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
            String s = null;
            String s1 = null;
            String s2 = null;
            int i = xmlpullparser.getAttributeCount();
            int j = 0;
            while(j < i)  {
                String s3 = xmlpullparser.getAttributeName(j);
                String s4 = xmlpullparser.getAttributeValue(j);
                if("manufacturer".equals(s3))
                    s = s4;
                else
                if("model".equals(s3))
                    s1 = s4;
                else
                if("version".equals(s3))
                    s2 = s4;
                j++;
            }
            return new AccessoryFilter(s, s1, s2);
        }

        public boolean equals(Object obj) {
            boolean flag = true;
            if(mManufacturer != null && mModel != null && mVersion != null) goto _L2; else goto _L1
_L1:
            flag = false;
_L4:
            return flag;
_L2:
            if(obj instanceof AccessoryFilter) {
                AccessoryFilter accessoryfilter = (AccessoryFilter)obj;
                if(!mManufacturer.equals(accessoryfilter.mManufacturer) || !mModel.equals(accessoryfilter.mModel) || !mVersion.equals(accessoryfilter.mVersion))
                    flag = false;
            } else
            if(obj instanceof UsbAccessory) {
                UsbAccessory usbaccessory = (UsbAccessory)obj;
                if(!mManufacturer.equals(usbaccessory.getManufacturer()) || !mModel.equals(usbaccessory.getModel()) || !mVersion.equals(usbaccessory.getVersion()))
                    flag = false;
            } else {
                flag = false;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public int hashCode() {
            int i = 0;
            int j;
            int k;
            boolean flag;
            if(mManufacturer == null)
                j = 0;
            else
                j = mManufacturer.hashCode();
            if(mModel == null)
                k = 0;
            else
                k = mModel.hashCode();
            flag = j ^ k;
            if(mVersion != null)
                i = mVersion.hashCode();
            return flag ^ i;
        }

        public boolean matches(UsbAccessory usbaccessory) {
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_2;
            if((mManufacturer == null || usbaccessory.getManufacturer().equals(mManufacturer)) && (mModel == null || usbaccessory.getModel().equals(mModel)) && (mVersion == null || usbaccessory.getVersion().equals(mVersion)))
                flag = true;
            return flag;
        }

        public boolean matches(AccessoryFilter accessoryfilter) {
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_2;
            if((mManufacturer == null || accessoryfilter.mManufacturer.equals(mManufacturer)) && (mModel == null || accessoryfilter.mModel.equals(mModel)) && (mVersion == null || accessoryfilter.mVersion.equals(mVersion)))
                flag = true;
            return flag;
        }

        public String toString() {
            return (new StringBuilder()).append("AccessoryFilter[mManufacturer=\"").append(mManufacturer).append("\", mModel=\"").append(mModel).append("\", mVersion=\"").append(mVersion).append("\"]").toString();
        }

        public void write(XmlSerializer xmlserializer) throws IOException {
            xmlserializer.startTag(null, "usb-accessory");
            if(mManufacturer != null)
                xmlserializer.attribute(null, "manufacturer", mManufacturer);
            if(mModel != null)
                xmlserializer.attribute(null, "model", mModel);
            if(mVersion != null)
                xmlserializer.attribute(null, "version", mVersion);
            xmlserializer.endTag(null, "usb-accessory");
        }

        public final String mManufacturer;
        public final String mModel;
        public final String mVersion;

        public AccessoryFilter(UsbAccessory usbaccessory) {
            mManufacturer = usbaccessory.getManufacturer();
            mModel = usbaccessory.getModel();
            mVersion = usbaccessory.getVersion();
        }

        public AccessoryFilter(String s, String s1, String s2) {
            mManufacturer = s;
            mModel = s1;
            mVersion = s2;
        }
    }

    private static class DeviceFilter {

        private boolean matches(int i, int j, int k) {
            boolean flag;
            if((mClass == -1 || i == mClass) && (mSubclass == -1 || j == mSubclass) && (mProtocol == -1 || k == mProtocol))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public static DeviceFilter read(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
            int i = -1;
            int j = -1;
            int k = -1;
            int l = -1;
            int i1 = -1;
            int j1 = xmlpullparser.getAttributeCount();
            int k1 = 0;
            while(k1 < j1)  {
                String s = xmlpullparser.getAttributeName(k1);
                int l1 = Integer.parseInt(xmlpullparser.getAttributeValue(k1));
                if("vendor-id".equals(s))
                    i = l1;
                else
                if("product-id".equals(s))
                    j = l1;
                else
                if("class".equals(s))
                    k = l1;
                else
                if("subclass".equals(s))
                    l = l1;
                else
                if("protocol".equals(s))
                    i1 = l1;
                k1++;
            }
            return new DeviceFilter(i, j, k, l, i1);
        }

        public boolean equals(Object obj) {
            boolean flag = true;
            if(mVendorId != -1 && mProductId != -1 && mClass != -1 && mSubclass != -1 && mProtocol != -1) goto _L2; else goto _L1
_L1:
            flag = false;
_L4:
            return flag;
_L2:
            if(obj instanceof DeviceFilter) {
                DeviceFilter devicefilter = (DeviceFilter)obj;
                if(devicefilter.mVendorId != mVendorId || devicefilter.mProductId != mProductId || devicefilter.mClass != mClass || devicefilter.mSubclass != mSubclass || devicefilter.mProtocol != mProtocol)
                    flag = false;
            } else
            if(obj instanceof UsbDevice) {
                UsbDevice usbdevice = (UsbDevice)obj;
                if(usbdevice.getVendorId() != mVendorId || usbdevice.getProductId() != mProductId || usbdevice.getDeviceClass() != mClass || usbdevice.getDeviceSubclass() != mSubclass || usbdevice.getDeviceProtocol() != mProtocol)
                    flag = false;
            } else {
                flag = false;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public int hashCode() {
            return (mVendorId << 16 | mProductId) ^ (mClass << 16 | mSubclass << 8 | mProtocol);
        }

        public boolean matches(UsbDevice usbdevice) {
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_2;
_L2:
            int i;
            int j;
            do
                return flag;
            while(mVendorId != -1 && usbdevice.getVendorId() != mVendorId || mProductId != -1 && usbdevice.getProductId() != mProductId);
            if(matches(usbdevice.getDeviceClass(), usbdevice.getDeviceSubclass(), usbdevice.getDeviceProtocol())) {
                flag = true;
                continue; /* Loop/switch isn't completed */
            }
            i = usbdevice.getInterfaceCount();
            j = 0;
_L3:
            if(j < i) {
label0:
                {
                    UsbInterface usbinterface = usbdevice.getInterface(j);
                    if(!matches(usbinterface.getInterfaceClass(), usbinterface.getInterfaceSubclass(), usbinterface.getInterfaceProtocol()))
                        break label0;
                    flag = true;
                }
            }
            if(true) goto _L2; else goto _L1
_L1:
            j++;
              goto _L3
            if(true) goto _L2; else goto _L4
_L4:
        }

        public boolean matches(DeviceFilter devicefilter) {
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_2;
            if((mVendorId == -1 || devicefilter.mVendorId == mVendorId) && (mProductId == -1 || devicefilter.mProductId == mProductId))
                flag = matches(devicefilter.mClass, devicefilter.mSubclass, devicefilter.mProtocol);
            return flag;
        }

        public String toString() {
            return (new StringBuilder()).append("DeviceFilter[mVendorId=").append(mVendorId).append(",mProductId=").append(mProductId).append(",mClass=").append(mClass).append(",mSubclass=").append(mSubclass).append(",mProtocol=").append(mProtocol).append("]").toString();
        }

        public void write(XmlSerializer xmlserializer) throws IOException {
            xmlserializer.startTag(null, "usb-device");
            if(mVendorId != -1)
                xmlserializer.attribute(null, "vendor-id", Integer.toString(mVendorId));
            if(mProductId != -1)
                xmlserializer.attribute(null, "product-id", Integer.toString(mProductId));
            if(mClass != -1)
                xmlserializer.attribute(null, "class", Integer.toString(mClass));
            if(mSubclass != -1)
                xmlserializer.attribute(null, "subclass", Integer.toString(mSubclass));
            if(mProtocol != -1)
                xmlserializer.attribute(null, "protocol", Integer.toString(mProtocol));
            xmlserializer.endTag(null, "usb-device");
        }

        public final int mClass;
        public final int mProductId;
        public final int mProtocol;
        public final int mSubclass;
        public final int mVendorId;

        public DeviceFilter(int i, int j, int k, int l, int i1) {
            mVendorId = i;
            mProductId = j;
            mClass = k;
            mSubclass = l;
            mProtocol = i1;
        }

        public DeviceFilter(UsbDevice usbdevice) {
            mVendorId = usbdevice.getVendorId();
            mProductId = usbdevice.getProductId();
            mClass = usbdevice.getDeviceClass();
            mSubclass = usbdevice.getDeviceSubclass();
            mProtocol = usbdevice.getDeviceProtocol();
        }
    }


    public UsbSettingsManager(Context context) {
        mDevicePermissionMap = new HashMap();
        mAccessoryPermissionMap = new HashMap();
        mDevicePreferenceMap = new HashMap();
        mAccessoryPreferenceMap = new HashMap();
        mLock = new Object();
        mPackageMonitor = new MyPackageMonitor();
        mContext = context;
        mPackageManager = context.getPackageManager();
        synchronized(mLock) {
            readSettingsLocked();
        }
        mPackageMonitor.register(context, null, true);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private boolean clearCompatibleMatchesLocked(String s, AccessoryFilter accessoryfilter) {
        boolean flag = false;
        Iterator iterator = mAccessoryPreferenceMap.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            AccessoryFilter accessoryfilter1 = (AccessoryFilter)iterator.next();
            if(accessoryfilter.matches(accessoryfilter1)) {
                mAccessoryPreferenceMap.remove(accessoryfilter1);
                flag = true;
            }
        } while(true);
        return flag;
    }

    private boolean clearCompatibleMatchesLocked(String s, DeviceFilter devicefilter) {
        boolean flag = false;
        Iterator iterator = mDevicePreferenceMap.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            DeviceFilter devicefilter1 = (DeviceFilter)iterator.next();
            if(devicefilter.matches(devicefilter1)) {
                mDevicePreferenceMap.remove(devicefilter1);
                flag = true;
            }
        } while(true);
        return flag;
    }

    private boolean clearPackageDefaultsLocked(String s) {
        boolean flag = false;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(!mDevicePreferenceMap.containsValue(s)) goto _L2; else goto _L1
_L1:
        Object aobj1[];
        int j;
        aobj1 = mDevicePreferenceMap.keySet().toArray();
        j = 0;
_L4:
        if(j < aobj1.length) {
            Object obj2 = aobj1[j];
            Object aobj[];
            Object obj1;
            if(s.equals(mDevicePreferenceMap.get(obj2))) {
                mDevicePreferenceMap.remove(obj2);
                flag = true;
            }
            j++;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        if(mAccessoryPreferenceMap.containsValue(s)) {
            aobj = mAccessoryPreferenceMap.keySet().toArray();
            for(int i = 0; i < aobj.length; i++) {
                obj1 = aobj[i];
                if(s.equals(mAccessoryPreferenceMap.get(obj1))) {
                    mAccessoryPreferenceMap.remove(obj1);
                    flag = true;
                }
                break MISSING_BLOCK_LABEL_174;
            }

        }
        return flag;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private final ArrayList getAccessoryMatchesLocked(UsbAccessory usbaccessory, Intent intent) {
        ArrayList arraylist = new ArrayList();
        List list = mPackageManager.queryIntentActivities(intent, 128);
        int i = list.size();
        for(int j = 0; j < i; j++) {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(j);
            if(packageMatchesLocked(resolveinfo, intent.getAction(), null, usbaccessory))
                arraylist.add(resolveinfo);
        }

        return arraylist;
    }

    private final ArrayList getDeviceMatchesLocked(UsbDevice usbdevice, Intent intent) {
        ArrayList arraylist = new ArrayList();
        List list = mPackageManager.queryIntentActivities(intent, 128);
        int i = list.size();
        for(int j = 0; j < i; j++) {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(j);
            if(packageMatchesLocked(resolveinfo, intent.getAction(), usbdevice, null))
                arraylist.add(resolveinfo);
        }

        return arraylist;
    }

    private void handlePackageUpdate(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = false;
        PackageInfo packageinfo = mPackageManager.getPackageInfo(s, 129);
        ActivityInfo aactivityinfo[] = packageinfo.activities;
        if(aactivityinfo != null) goto _L2; else goto _L1
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        Slog.e("UsbSettingsManager", (new StringBuilder()).append("handlePackageUpdate could not find package ").append(s).toString(), namenotfoundexception);
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L2:
        int i = 0;
_L7:
        if(i >= aactivityinfo.length) goto _L4; else goto _L3
_L3:
        if(handlePackageUpdateLocked(s, aactivityinfo[i], "android.hardware.usb.action.USB_DEVICE_ATTACHED"))
            flag = true;
        if(handlePackageUpdateLocked(s, aactivityinfo[i], "android.hardware.usb.action.USB_ACCESSORY_ATTACHED"))
            flag = true;
          goto _L5
_L4:
        if(flag)
            writeSettingsLocked();
        obj;
        JVM INSTR monitorexit ;
_L1:
        return;
_L5:
        i++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private boolean handlePackageUpdateLocked(String s, ActivityInfo activityinfo, String s1) {
        XmlResourceParser xmlresourceparser;
        boolean flag;
        xmlresourceparser = null;
        flag = false;
        XmlResourceParser xmlresourceparser1 = activityinfo.loadXmlMetaData(mPackageManager, s1);
        xmlresourceparser = xmlresourceparser1;
        if(xmlresourceparser != null) goto _L2; else goto _L1
_L1:
        boolean flag1;
        flag1 = false;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
_L11:
        return flag1;
_L2:
        XmlUtils.nextElement(xmlresourceparser);
_L8:
        if(xmlresourceparser.getEventType() == 1) goto _L4; else goto _L3
_L3:
        String s2 = xmlresourceparser.getName();
        if(!"usb-device".equals(s2)) goto _L6; else goto _L5
_L5:
        if(clearCompatibleMatchesLocked(s, DeviceFilter.read(xmlresourceparser)))
            flag = true;
_L13:
        XmlUtils.nextElement(xmlresourceparser);
        if(true) goto _L8; else goto _L7
_L7:
        Exception exception1;
        exception1;
        Slog.w("UsbSettingsManager", (new StringBuilder()).append("Unable to load component info ").append(activityinfo.toString()).toString(), exception1);
        if(xmlresourceparser == null) goto _L10; else goto _L9
_L9:
        xmlresourceparser.close();
_L10:
        flag1 = flag;
          goto _L11
_L6:
        if(!"usb-accessory".equals(s2)) goto _L13; else goto _L12
_L12:
        boolean flag2 = clearCompatibleMatchesLocked(s, AccessoryFilter.read(xmlresourceparser));
        if(flag2)
            flag = true;
          goto _L13
        Exception exception;
        exception;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        throw exception;
_L4:
        if(xmlresourceparser == null) goto _L10; else goto _L9
    }

    private boolean packageMatchesLocked(ResolveInfo resolveinfo, String s, UsbDevice usbdevice, UsbAccessory usbaccessory) {
        boolean flag;
        ActivityInfo activityinfo;
        XmlResourceParser xmlresourceparser;
        flag = false;
        activityinfo = resolveinfo.activityInfo;
        xmlresourceparser = null;
        xmlresourceparser = activityinfo.loadXmlMetaData(mPackageManager, s);
        if(xmlresourceparser != null) goto _L2; else goto _L1
_L1:
        Slog.w("UsbSettingsManager", (new StringBuilder()).append("no meta-data for ").append(resolveinfo).toString());
        if(xmlresourceparser == null) goto _L4; else goto _L3
_L3:
        xmlresourceparser.close();
_L4:
        return flag;
_L2:
        XmlUtils.nextElement(xmlresourceparser);
_L7:
        String s1;
        if(xmlresourceparser.getEventType() == 1)
            continue; /* Loop/switch isn't completed */
        s1 = xmlresourceparser.getName();
        if(usbdevice == null || !"usb-device".equals(s1)) goto _L6; else goto _L5
_L5:
        boolean flag2 = DeviceFilter.read(xmlresourceparser).matches(usbdevice);
        if(!flag2)
            break MISSING_BLOCK_LABEL_194;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        flag = true;
          goto _L4
_L6:
        if(usbaccessory == null)
            break MISSING_BLOCK_LABEL_194;
        boolean flag1;
        if(!"usb-accessory".equals(s1))
            break MISSING_BLOCK_LABEL_194;
        flag1 = AccessoryFilter.read(xmlresourceparser).matches(usbaccessory);
        if(!flag1)
            break MISSING_BLOCK_LABEL_194;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        flag = true;
          goto _L4
        XmlUtils.nextElement(xmlresourceparser);
          goto _L7
        Exception exception1;
        exception1;
        Slog.w("UsbSettingsManager", (new StringBuilder()).append("Unable to load component info ").append(resolveinfo.toString()).toString(), exception1);
        if(xmlresourceparser == null) goto _L4; else goto _L8
_L8:
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        throw exception;
        if(xmlresourceparser == null) goto _L4; else goto _L3
    }

    private void readPreference(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        String s = null;
        int i = xmlpullparser.getAttributeCount();
        int j = 0;
        do {
label0:
            {
                if(j < i) {
                    if(!"package".equals(xmlpullparser.getAttributeName(j)))
                        break label0;
                    s = xmlpullparser.getAttributeValue(j);
                }
                XmlUtils.nextElement(xmlpullparser);
                if("usb-device".equals(xmlpullparser.getName())) {
                    DeviceFilter devicefilter = DeviceFilter.read(xmlpullparser);
                    mDevicePreferenceMap.put(devicefilter, s);
                } else
                if("usb-accessory".equals(xmlpullparser.getName())) {
                    AccessoryFilter accessoryfilter = AccessoryFilter.read(xmlpullparser);
                    mAccessoryPreferenceMap.put(accessoryfilter, s);
                }
                XmlUtils.nextElement(xmlpullparser);
                return;
            }
            j++;
        } while(true);
    }

    private void readSettingsLocked() {
        FileInputStream fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(sSettingsFile);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
        XmlUtils.nextElement(xmlpullparser);
_L5:
        if(xmlpullparser.getEventType() == 1) goto _L2; else goto _L1
_L1:
        if(!"preference".equals(xmlpullparser.getName())) goto _L4; else goto _L3
_L3:
        readPreference(xmlpullparser);
          goto _L5
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        fileinputstream = fileinputstream1;
_L10:
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_80;
        fileinputstream.close();
_L6:
        return;
_L4:
        XmlUtils.nextElement(xmlpullparser);
          goto _L5
        Exception exception1;
        exception1;
        fileinputstream = fileinputstream1;
_L9:
        Slog.e("UsbSettingsManager", "error reading settings file, deleting to start fresh", exception1);
        sSettingsFile.delete();
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception1) { }
          goto _L6
_L2:
        Exception exception;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception2) { }
          goto _L6
        exception;
_L8:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L8; else goto _L7
_L7:
        exception1;
          goto _L9
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
          goto _L10
    }

    private void requestPermissionDialog(Intent intent, String s, PendingIntent pendingintent) {
        long l;
        int i = Binder.getCallingUid();
        try {
            if(mPackageManager.getApplicationInfo(s, 0).uid != i)
                throw new IllegalArgumentException((new StringBuilder()).append("package ").append(s).append(" does not match caller's uid ").append(i).toString());
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            throw new IllegalArgumentException((new StringBuilder()).append("package ").append(s).append(" not found").toString());
        }
        l = Binder.clearCallingIdentity();
        intent.setClassName("com.android.systemui", "com.android.systemui.usb.UsbPermissionActivity");
        intent.addFlags(0x10000000);
        intent.putExtra("android.intent.extra.INTENT", pendingintent);
        intent.putExtra("package", s);
        intent.putExtra("uid", i);
        mContext.startActivity(intent);
_L2:
        Binder.restoreCallingIdentity(l);
        return;
        ActivityNotFoundException activitynotfoundexception;
        activitynotfoundexception;
        Slog.e("UsbSettingsManager", "unable to start UsbPermissionActivity");
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void resolveActivity(Intent intent, ArrayList arraylist, String s, UsbDevice usbdevice, UsbAccessory usbaccessory) {
        int i = arraylist.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        Intent intent2;
        if(usbaccessory == null)
            break MISSING_BLOCK_LABEL_97;
        String s1 = usbaccessory.getUri();
        if(s1 == null || s1.length() <= 0)
            break MISSING_BLOCK_LABEL_97;
        intent2 = new Intent();
        intent2.setClassName("com.android.systemui", "com.android.systemui.usb.UsbAccessoryUriActivity");
        intent2.addFlags(0x10000000);
        intent2.putExtra("accessory", usbaccessory);
        intent2.putExtra("uri", s1);
        mContext.startActivity(intent2);
_L3:
        return;
        ActivityNotFoundException activitynotfoundexception2;
        activitynotfoundexception2;
        Slog.e("UsbSettingsManager", "unable to start UsbAccessoryUriActivity");
          goto _L3
_L2:
        ResolveInfo resolveinfo;
        resolveinfo = null;
        if(i == 1 && s == null) {
            ResolveInfo resolveinfo2 = (ResolveInfo)arraylist.get(0);
            if(resolveinfo2.activityInfo != null && resolveinfo2.activityInfo.applicationInfo != null && (1 & resolveinfo2.activityInfo.applicationInfo.flags) != 0)
                resolveinfo = resolveinfo2;
        }
        if(resolveinfo != null || s == null) goto _L5; else goto _L4
_L4:
        int j = 0;
_L6:
        if(j < i) {
            ResolveInfo resolveinfo1 = (ResolveInfo)arraylist.get(j);
            if(resolveinfo1.activityInfo == null || !s.equals(((ComponentInfo) (resolveinfo1.activityInfo)).packageName))
                break MISSING_BLOCK_LABEL_313;
            resolveinfo = resolveinfo1;
        }
_L5:
        if(resolveinfo != null) {
            if(usbdevice != null)
                grantDevicePermission(usbdevice, resolveinfo.activityInfo.applicationInfo.uid);
            else
            if(usbaccessory != null)
                grantAccessoryPermission(usbaccessory, resolveinfo.activityInfo.applicationInfo.uid);
            try {
                intent.setComponent(new ComponentName(((ComponentInfo) (resolveinfo.activityInfo)).packageName, ((ComponentInfo) (resolveinfo.activityInfo)).name));
                mContext.startActivity(intent);
            }
            catch(ActivityNotFoundException activitynotfoundexception1) {
                Slog.e("UsbSettingsManager", "startActivity failed", activitynotfoundexception1);
            }
        } else {
            Intent intent1 = new Intent();
            intent1.addFlags(0x10000000);
            if(i == 1) {
                intent1.setClassName("com.android.systemui", "com.android.systemui.usb.UsbConfirmActivity");
                intent1.putExtra("rinfo", (Parcelable)arraylist.get(0));
                ActivityNotFoundException activitynotfoundexception;
                if(usbdevice != null)
                    intent1.putExtra("device", usbdevice);
                else
                    intent1.putExtra("accessory", usbaccessory);
            } else {
                intent1.setClassName("com.android.systemui", "com.android.systemui.usb.UsbResolverActivity");
                intent1.putParcelableArrayListExtra("rlist", arraylist);
                intent1.putExtra("android.intent.extra.INTENT", intent);
            }
            try {
                mContext.startActivity(intent1);
            }
            // Misplaced declaration of an exception variable
            catch(ActivityNotFoundException activitynotfoundexception) {
                Slog.e("UsbSettingsManager", (new StringBuilder()).append("unable to start activity ").append(intent1).toString());
            }
        }
          goto _L3
        j++;
          goto _L6
    }

    private void writeSettingsLocked() {
        FileOutputStream fileoutputstream;
        BufferedOutputStream bufferedoutputstream;
        FastXmlSerializer fastxmlserializer;
        Exception exception;
        fileoutputstream = new FileOutputStream(sSettingsFile);
        bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "settings");
        for(Iterator iterator = mDevicePreferenceMap.keySet().iterator(); iterator.hasNext(); fastxmlserializer.endTag(null, "preference")) {
            DeviceFilter devicefilter = (DeviceFilter)iterator.next();
            fastxmlserializer.startTag(null, "preference");
            fastxmlserializer.attribute(null, "package", (String)mDevicePreferenceMap.get(devicefilter));
            devicefilter.write(fastxmlserializer);
        }

          goto _L1
_L3:
        return;
_L1:
        try {
            for(Iterator iterator1 = mAccessoryPreferenceMap.keySet().iterator(); iterator1.hasNext(); fastxmlserializer.endTag(null, "preference")) {
                AccessoryFilter accessoryfilter = (AccessoryFilter)iterator1.next();
                fastxmlserializer.startTag(null, "preference");
                fastxmlserializer.attribute(null, "package", (String)mAccessoryPreferenceMap.get(accessoryfilter));
                accessoryfilter.write(fastxmlserializer);
            }

            fastxmlserializer.endTag(null, "settings");
            fastxmlserializer.endDocument();
            bufferedoutputstream.flush();
            FileUtils.sync(fileoutputstream);
            bufferedoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception) {
            Slog.e("UsbSettingsManager", "error writing settings file, deleting to start fresh", exception);
            sSettingsFile.delete();
        }
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void accessoryAttached(UsbAccessory usbaccessory) {
        Intent intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        intent.putExtra("accessory", usbaccessory);
        intent.addFlags(0x10000000);
        ArrayList arraylist;
        String s;
        synchronized(mLock) {
            arraylist = getAccessoryMatchesLocked(usbaccessory, intent);
            s = (String)mAccessoryPreferenceMap.get(new AccessoryFilter(usbaccessory));
        }
        resolveActivity(intent, arraylist, s, null, usbaccessory);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void accessoryDetached(UsbAccessory usbaccessory) {
        mAccessoryPermissionMap.remove(usbaccessory);
        Intent intent = new Intent("android.hardware.usb.action.USB_ACCESSORY_DETACHED");
        intent.putExtra("accessory", usbaccessory);
        mContext.sendBroadcast(intent);
    }

    public void checkPermission(UsbAccessory usbaccessory) {
        if(!hasPermission(usbaccessory))
            throw new SecurityException((new StringBuilder()).append("User has not given permission to accessory ").append(usbaccessory).toString());
        else
            return;
    }

    public void checkPermission(UsbDevice usbdevice) {
        if(!hasPermission(usbdevice))
            throw new SecurityException((new StringBuilder()).append("User has not given permission to device ").append(usbdevice).toString());
        else
            return;
    }

    public void clearDefaults(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(clearPackageDefaultsLocked(s))
            writeSettingsLocked();
        return;
    }

    public void deviceAttached(UsbDevice usbdevice) {
        Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intent.putExtra("device", usbdevice);
        intent.addFlags(0x10000000);
        ArrayList arraylist;
        String s;
        synchronized(mLock) {
            arraylist = getDeviceMatchesLocked(usbdevice, intent);
            s = (String)mDevicePreferenceMap.get(new DeviceFilter(usbdevice));
        }
        resolveActivity(intent, arraylist, s, usbdevice, null);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void deviceDetached(UsbDevice usbdevice) {
        mDevicePermissionMap.remove(usbdevice.getDeviceName());
        Intent intent = new Intent("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intent.putExtra("device", usbdevice);
        mContext.sendBroadcast(intent);
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("  Device permissions:");
        for(Iterator iterator = mDevicePermissionMap.keySet().iterator(); iterator.hasNext(); printwriter.println("")) {
            String s = (String)iterator.next();
            printwriter.print((new StringBuilder()).append("    ").append(s).append(": ").toString());
            SparseBooleanArray sparsebooleanarray1 = (SparseBooleanArray)mDevicePermissionMap.get(s);
            int k = sparsebooleanarray1.size();
            for(int l = 0; l < k; l++)
                printwriter.print((new StringBuilder()).append(Integer.toString(sparsebooleanarray1.keyAt(l))).append(" ").toString());

        }

        break MISSING_BLOCK_LABEL_168;
        Exception exception;
        exception;
        throw exception;
        printwriter.println("  Accessory permissions:");
        for(Iterator iterator1 = mAccessoryPermissionMap.keySet().iterator(); iterator1.hasNext(); printwriter.println("")) {
            UsbAccessory usbaccessory = (UsbAccessory)iterator1.next();
            printwriter.print((new StringBuilder()).append("    ").append(usbaccessory).append(": ").toString());
            SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mAccessoryPermissionMap.get(usbaccessory);
            int i = sparsebooleanarray.size();
            for(int j = 0; j < i; j++)
                printwriter.print((new StringBuilder()).append(Integer.toString(sparsebooleanarray.keyAt(j))).append(" ").toString());

        }

        printwriter.println("  Device preferences:");
        DeviceFilter devicefilter;
        for(Iterator iterator2 = mDevicePreferenceMap.keySet().iterator(); iterator2.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(devicefilter).append(": ").append((String)mDevicePreferenceMap.get(devicefilter)).toString()))
            devicefilter = (DeviceFilter)iterator2.next();

        printwriter.println("  Accessory preferences:");
        AccessoryFilter accessoryfilter;
        for(Iterator iterator3 = mAccessoryPreferenceMap.keySet().iterator(); iterator3.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(accessoryfilter).append(": ").append((String)mAccessoryPreferenceMap.get(accessoryfilter)).toString()))
            accessoryfilter = (AccessoryFilter)iterator3.next();

        obj;
        JVM INSTR monitorexit ;
    }

    public void grantAccessoryPermission(UsbAccessory usbaccessory, int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mAccessoryPermissionMap.get(usbaccessory);
        if(sparsebooleanarray == null) {
            sparsebooleanarray = new SparseBooleanArray(1);
            mAccessoryPermissionMap.put(usbaccessory, sparsebooleanarray);
        }
        sparsebooleanarray.put(i, true);
        return;
    }

    public void grantDevicePermission(UsbDevice usbdevice, int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        String s = usbdevice.getDeviceName();
        SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mDevicePermissionMap.get(s);
        if(sparsebooleanarray == null) {
            sparsebooleanarray = new SparseBooleanArray(1);
            mDevicePermissionMap.put(s, sparsebooleanarray);
        }
        sparsebooleanarray.put(i, true);
        return;
    }

    public boolean hasDefaults(String s) {
        boolean flag = true;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(!mDevicePreferenceMap.values().contains(s) && !mAccessoryPreferenceMap.values().contains(s)) goto _L2; else goto _L1
        Exception exception;
        exception;
        throw exception;
_L2:
        flag = false;
        obj;
        JVM INSTR monitorexit ;
_L1:
        return flag;
    }

    public boolean hasPermission(UsbAccessory usbaccessory) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mAccessoryPermissionMap.get(usbaccessory);
        boolean flag;
        if(sparsebooleanarray == null)
            flag = false;
        else
            flag = sparsebooleanarray.get(Binder.getCallingUid());
        return flag;
    }

    public boolean hasPermission(UsbDevice usbdevice) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mDevicePermissionMap.get(usbdevice.getDeviceName());
        boolean flag;
        if(sparsebooleanarray == null)
            flag = false;
        else
            flag = sparsebooleanarray.get(Binder.getCallingUid());
        return flag;
    }

    public void requestPermission(UsbAccessory usbaccessory, String s, PendingIntent pendingintent) {
        Intent intent = new Intent();
        if(!hasPermission(usbaccessory)) goto _L2; else goto _L1
_L1:
        intent.putExtra("accessory", usbaccessory);
        intent.putExtra("permission", true);
        pendingintent.send(mContext, 0, intent);
_L4:
        return;
_L2:
        intent.putExtra("accessory", usbaccessory);
        requestPermissionDialog(intent, s, pendingintent);
        continue; /* Loop/switch isn't completed */
        android.app.PendingIntent.CanceledException canceledexception;
        canceledexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void requestPermission(UsbDevice usbdevice, String s, PendingIntent pendingintent) {
        Intent intent = new Intent();
        if(!hasPermission(usbdevice)) goto _L2; else goto _L1
_L1:
        intent.putExtra("device", usbdevice);
        intent.putExtra("permission", true);
        pendingintent.send(mContext, 0, intent);
_L4:
        return;
_L2:
        intent.putExtra("device", usbdevice);
        requestPermissionDialog(intent, s, pendingintent);
        continue; /* Loop/switch isn't completed */
        android.app.PendingIntent.CanceledException canceledexception;
        canceledexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setAccessoryPackage(UsbAccessory usbaccessory, String s) {
        AccessoryFilter accessoryfilter = new AccessoryFilter(usbaccessory);
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
label0:
        {
            {
                if(s != null)
                    break label0;
                boolean flag;
                if(mAccessoryPreferenceMap.remove(accessoryfilter) != null)
                    flag = true;
                else
                    flag = false;
            }
            if(flag)
                writeSettingsLocked();
            return;
        }
        if(!s.equals(mAccessoryPreferenceMap.get(accessoryfilter)))
            flag = true;
        else
            flag = false;
        if(flag)
            mAccessoryPreferenceMap.put(accessoryfilter, s);
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_36;
    }

    public void setDevicePackage(UsbDevice usbdevice, String s) {
        DeviceFilter devicefilter = new DeviceFilter(usbdevice);
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
label0:
        {
            {
                if(s != null)
                    break label0;
                boolean flag;
                if(mDevicePreferenceMap.remove(devicefilter) != null)
                    flag = true;
                else
                    flag = false;
            }
            if(flag)
                writeSettingsLocked();
            return;
        }
        if(!s.equals(mDevicePreferenceMap.get(devicefilter)))
            flag = true;
        else
            flag = false;
        if(flag)
            mDevicePreferenceMap.put(devicefilter, s);
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_36;
    }

    private static final boolean DEBUG = false;
    private static final String TAG = "UsbSettingsManager";
    private static final File sSettingsFile = new File("/data/system/usb_device_manager.xml");
    private final HashMap mAccessoryPermissionMap;
    private final HashMap mAccessoryPreferenceMap;
    private final Context mContext;
    private final HashMap mDevicePermissionMap;
    private final HashMap mDevicePreferenceMap;
    private final Object mLock;
    private final PackageManager mPackageManager;
    MyPackageMonitor mPackageMonitor;


}
