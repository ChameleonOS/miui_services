// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.AppGlobals;
import android.app.IApplicationThread;
import android.content.pm.*;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.*;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.os.AtomicFile;
import com.android.internal.util.FastXmlSerializer;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService, ActivityStack, ActivityRecord, ProcessRecord

public class CompatModePackages {

    public CompatModePackages(ActivityManagerService activitymanagerservice, File file) {
        FileInputStream fileinputstream;
        TAG = "ActivityManager";
        DEBUG_CONFIGURATION = false;
        mPackages = new HashMap();
        mHandler = new Handler() {

            public void handleMessage(Message message) {
                message.what;
                JVM INSTR tableswitch 300 300: default 24
            //                           300 30;
                   goto _L1 _L2
_L1:
                super.handleMessage(message);
_L4:
                return;
_L2:
                saveCompatModes();
                if(true) goto _L4; else goto _L3
_L3:
            }

            final CompatModePackages this$0;

             {
                this$0 = CompatModePackages.this;
                super();
            }
        };
        mService = activitymanagerservice;
        mFile = new AtomicFile(new File(file, "packages-compat.xml"));
        fileinputstream = null;
        XmlPullParser xmlpullparser;
        fileinputstream = mFile.openRead();
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
        for(int i = xmlpullparser.getEventType(); i != 2; i = xmlpullparser.next());
        if(!"compat-packages".equals(xmlpullparser.getName())) goto _L2; else goto _L1
_L1:
        int j = xmlpullparser.next();
_L7:
        if(j != 2) goto _L4; else goto _L3
_L3:
        String s = xmlpullparser.getName();
        if(xmlpullparser.getDepth() != 2 || !"pkg".equals(s)) goto _L4; else goto _L5
_L5:
        String s1 = xmlpullparser.getAttributeValue(null, "name");
        if(s1 == null) goto _L4; else goto _L6
_L6:
        String s2 = xmlpullparser.getAttributeValue(null, "mode");
        int l;
        l = 0;
        if(s2 == null)
            break MISSING_BLOCK_LABEL_223;
        int i1 = Integer.parseInt(s2);
        l = i1;
_L9:
        mPackages.put(s1, Integer.valueOf(l));
_L4:
        int k = xmlpullparser.next();
        j = k;
        if(j != 1) goto _L7; else goto _L2
_L2:
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_265;
        fileinputstream.close();
_L8:
        return;
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Slog.w("ActivityManager", "Error reading compat-packages", xmlpullparserexception);
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception3) { }
          goto _L8
        IOException ioexception1;
        ioexception1;
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_310;
        Slog.w("ActivityManager", "Error reading compat-packages", ioexception1);
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L8
        Exception exception;
        exception;
        IOException ioexception4;
        NumberFormatException numberformatexception;
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        numberformatexception;
          goto _L9
        ioexception4;
          goto _L8
    }

    private int getPackageFlags(String s) {
        Integer integer = (Integer)mPackages.get(s);
        int i;
        if(integer != null)
            i = integer.intValue();
        else
            i = 0;
        return i;
    }

    private void setPackageScreenCompatModeLocked(ApplicationInfo applicationinfo, int i) {
        String s;
        int j;
        s = applicationinfo.packageName;
        j = getPackageFlags(s);
        i;
        JVM INSTR tableswitch 0 2: default 40
    //                   0 71
    //                   1 358
    //                   2 364;
           goto _L1 _L2 _L3 _L4
_L1:
        Slog.w("ActivityManager", (new StringBuilder()).append("Unknown screen compat mode req #").append(i).append("; ignoring").toString());
_L11:
        return;
_L2:
        boolean flag;
        CompatibilityInfo compatibilityinfo1;
        ActivityRecord activityrecord;
        flag = false;
        break; /* Loop/switch isn't completed */
_L3:
        flag = true;
          goto _L5
_L4:
        if((j & 2) == 0)
            flag = true;
        else
            flag = false;
_L5:
        int i1;
        int k;
        CompatibilityInfo compatibilityinfo;
        Message message;
        if(flag)
            k = j | 2;
        else
            k = j & -3;
        compatibilityinfo = compatibilityInfoForPackageLocked(applicationinfo);
        if(compatibilityinfo.alwaysSupportsScreen()) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Ignoring compat mode change of ").append(s).append("; compatibility never needed").toString());
            k = 0;
        }
        if(compatibilityinfo.neverSupportsScreen()) {
            Slog.w("ActivityManager", (new StringBuilder()).append("Ignoring compat mode change of ").append(s).append("; compatibility always needed").toString());
            k = 0;
        }
        if(k == j)
            continue; /* Loop/switch isn't completed */
        if(k != 0)
            mPackages.put(s, Integer.valueOf(k));
        else
            mPackages.remove(s);
        compatibilityinfo1 = compatibilityInfoForPackageLocked(applicationinfo);
        mHandler.removeMessages(300);
        message = mHandler.obtainMessage(300);
        mHandler.sendMessageDelayed(message, 10000L);
        activityrecord = mService.mMainStack.topRunningActivityLocked(null);
        for(int l = -1 + mService.mMainStack.mHistory.size(); l >= 0; l--) {
            ActivityRecord activityrecord1 = (ActivityRecord)mService.mMainStack.mHistory.get(l);
            if(((ComponentInfo) (activityrecord1.info)).packageName.equals(s)) {
                activityrecord1.forceNewConfig = true;
                if(activityrecord != null && activityrecord1 == activityrecord && activityrecord1.visible)
                    activityrecord1.startFreezingScreenLocked(activityrecord.app, 256);
            }
        }

        i1 = -1 + mService.mLruProcesses.size();
_L7:
        ProcessRecord processrecord;
        if(i1 < 0)
            break MISSING_BLOCK_LABEL_489;
        processrecord = (ProcessRecord)mService.mLruProcesses.get(i1);
        if(processrecord.pkgList.contains(s))
            break; /* Loop/switch isn't completed */
_L9:
        i1--;
        if(true) goto _L7; else goto _L6
_L6:
        if(processrecord.thread == null) goto _L9; else goto _L8
_L8:
        processrecord.thread.updatePackageCompatibilityInfo(s, compatibilityinfo1);
          goto _L9
        Exception exception;
        exception;
          goto _L9
        if(activityrecord != null) {
            mService.mMainStack.ensureActivityConfigurationLocked(activityrecord, 0);
            mService.mMainStack.ensureActivitiesVisibleLocked(activityrecord, 0);
        }
        if(true) goto _L11; else goto _L10
_L10:
    }

    public CompatibilityInfo compatibilityInfoForPackageLocked(ApplicationInfo applicationinfo) {
        int i = mService.mConfiguration.screenLayout;
        int j = mService.mConfiguration.smallestScreenWidthDp;
        boolean flag;
        if((2 & getPackageFlags(applicationinfo.packageName)) != 0)
            flag = true;
        else
            flag = false;
        return new CompatibilityInfo(applicationinfo, i, j, flag);
    }

    public int computeCompatModeLocked(ApplicationInfo applicationinfo) {
        byte byte0;
        boolean flag;
        CompatibilityInfo compatibilityinfo;
        byte0 = 1;
        if((2 & getPackageFlags(applicationinfo.packageName)) != 0)
            flag = byte0;
        else
            flag = false;
        compatibilityinfo = new CompatibilityInfo(applicationinfo, mService.mConfiguration.screenLayout, mService.mConfiguration.smallestScreenWidthDp, flag);
        if(!compatibilityinfo.alwaysSupportsScreen()) goto _L2; else goto _L1
_L1:
        byte0 = -2;
_L4:
        return byte0;
_L2:
        if(compatibilityinfo.neverSupportsScreen())
            byte0 = -1;
        else
        if(!flag)
            byte0 = 0;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean getFrontActivityAskCompatModeLocked() {
        ActivityRecord activityrecord = mService.mMainStack.topRunningActivityLocked(null);
        boolean flag;
        if(activityrecord == null)
            flag = false;
        else
            flag = getPackageAskCompatModeLocked(activityrecord.packageName);
        return flag;
    }

    public int getFrontActivityScreenCompatModeLocked() {
        ActivityRecord activityrecord = mService.mMainStack.topRunningActivityLocked(null);
        int i;
        if(activityrecord == null)
            i = -3;
        else
            i = computeCompatModeLocked(activityrecord.info.applicationInfo);
        return i;
    }

    public boolean getPackageAskCompatModeLocked(String s) {
        boolean flag;
        if((1 & getPackageFlags(s)) == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int getPackageScreenCompatModeLocked(String s) {
        ApplicationInfo applicationinfo = null;
        ApplicationInfo applicationinfo1 = AppGlobals.getPackageManager().getApplicationInfo(s, 0, 0);
        applicationinfo = applicationinfo1;
_L2:
        int i;
        if(applicationinfo == null)
            i = -3;
        else
            i = computeCompatModeLocked(applicationinfo);
        return i;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public HashMap getPackages() {
        return mPackages;
    }

    public void handlePackageAddedLocked(String s, boolean flag) {
        boolean flag1;
        ApplicationInfo applicationinfo;
        flag1 = false;
        applicationinfo = null;
        ApplicationInfo applicationinfo1 = AppGlobals.getPackageManager().getApplicationInfo(s, 0, 0);
        applicationinfo = applicationinfo1;
_L5:
        if(applicationinfo != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        CompatibilityInfo compatibilityinfo = compatibilityInfoForPackageLocked(applicationinfo);
        if(!compatibilityinfo.alwaysSupportsScreen() && !compatibilityinfo.neverSupportsScreen())
            flag1 = true;
        if(flag && !flag1 && mPackages.containsKey(s)) {
            mPackages.remove(s);
            mHandler.removeMessages(300);
            Message message = mHandler.obtainMessage(300);
            mHandler.sendMessageDelayed(message, 10000L);
        }
        if(true) goto _L1; else goto _L3
_L3:
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L5; else goto _L4
_L4:
    }

    void saveCompatModes() {
        HashMap hashmap;
        java.io.FileOutputStream fileoutputstream;
        synchronized(mService) {
            hashmap = new HashMap(mPackages);
        }
        fileoutputstream = null;
        FastXmlSerializer fastxmlserializer;
        IPackageManager ipackagemanager;
        int i;
        int j;
        Iterator iterator;
        fileoutputstream = mFile.startWrite();
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "compat-packages");
        ipackagemanager = AppGlobals.getPackageManager();
        i = mService.mConfiguration.screenLayout;
        j = mService.mConfiguration.smallestScreenWidthDp;
        iterator = hashmap.entrySet().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        String s;
        int k;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        s = (String)entry.getKey();
        k = ((Integer)entry.getValue()).intValue();
        if(k == 0) goto _L4; else goto _L3
_L3:
        ApplicationInfo applicationinfo = null;
        ApplicationInfo applicationinfo1 = ipackagemanager.getApplicationInfo(s, 0, 0);
        applicationinfo = applicationinfo1;
_L8:
        if(applicationinfo == null) goto _L4; else goto _L5
_L5:
        IOException ioexception;
        CompatibilityInfo compatibilityinfo = new CompatibilityInfo(applicationinfo, i, j, false);
        if(!compatibilityinfo.alwaysSupportsScreen() && !compatibilityinfo.neverSupportsScreen()) {
            fastxmlserializer.startTag(null, "pkg");
            fastxmlserializer.attribute(null, "name", s);
            fastxmlserializer.attribute(null, "mode", Integer.toString(k));
            fastxmlserializer.endTag(null, "pkg");
        }
          goto _L4
_L7:
        return;
        exception;
        activitymanagerservice;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        try {
            fastxmlserializer.endTag(null, "compat-packages");
            fastxmlserializer.endDocument();
            mFile.finishWrite(fileoutputstream);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) {
            Slog.w("ActivityManager", "Error writing compat packages", ioexception);
            if(fileoutputstream != null)
                mFile.failWrite(fileoutputstream);
        }
        if(true) goto _L7; else goto _L6
_L6:
        RemoteException remoteexception;
        remoteexception;
          goto _L8
    }

    public void setFrontActivityAskCompatModeLocked(boolean flag) {
        ActivityRecord activityrecord = mService.mMainStack.topRunningActivityLocked(null);
        if(activityrecord != null)
            setPackageAskCompatModeLocked(activityrecord.packageName, flag);
    }

    public void setFrontActivityScreenCompatModeLocked(int i) {
        ActivityRecord activityrecord = mService.mMainStack.topRunningActivityLocked(null);
        if(activityrecord == null)
            Slog.w("ActivityManager", "setFrontActivityScreenCompatMode failed: no top activity");
        else
            setPackageScreenCompatModeLocked(activityrecord.info.applicationInfo, i);
    }

    public void setPackageAskCompatModeLocked(String s, boolean flag) {
        int i = getPackageFlags(s);
        int j;
        if(flag)
            j = i & -2;
        else
            j = i | 1;
        if(i != j) {
            Message message;
            if(j != 0)
                mPackages.put(s, Integer.valueOf(j));
            else
                mPackages.remove(s);
            mHandler.removeMessages(300);
            message = mHandler.obtainMessage(300);
            mHandler.sendMessageDelayed(message, 10000L);
        }
    }

    public void setPackageScreenCompatModeLocked(String s, int i) {
        ApplicationInfo applicationinfo = null;
        ApplicationInfo applicationinfo1 = AppGlobals.getPackageManager().getApplicationInfo(s, 0, 0);
        applicationinfo = applicationinfo1;
_L2:
        if(applicationinfo == null)
            Slog.w("ActivityManager", (new StringBuilder()).append("setPackageScreenCompatMode failed: unknown package ").append(s).toString());
        else
            setPackageScreenCompatModeLocked(applicationinfo, i);
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static final int COMPAT_FLAG_DONT_ASK = 1;
    public static final int COMPAT_FLAG_ENABLED = 2;
    private static final int MSG_WRITE = 300;
    private final boolean DEBUG_CONFIGURATION;
    private final String TAG;
    private final AtomicFile mFile;
    private final Handler mHandler;
    private final HashMap mPackages;
    private final ActivityManagerService mService;
}
