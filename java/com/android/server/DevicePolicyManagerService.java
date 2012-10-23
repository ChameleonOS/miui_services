// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DeviceAdminInfo;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import android.view.IWindowManager;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.storage.ExternalStorageFormatter;
import com.android.internal.util.*;
import com.android.internal.widget.LockPatternUtils;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import org.xmlpull.v1.*;

public class DevicePolicyManagerService extends android.app.admin.IDevicePolicyManager.Stub {
    class MyPackageMonitor extends PackageMonitor {

        public void onSomePackagesChanged() {
            DevicePolicyManagerService devicepolicymanagerservice = DevicePolicyManagerService.this;
            devicepolicymanagerservice;
            JVM INSTR monitorenter ;
            boolean flag = false;
            int i = -1 + mAdminList.size();
_L3:
            if(i < 0) goto _L2; else goto _L1
_L1:
            ActiveAdmin activeadmin;
            boolean flag1;
            activeadmin = (ActiveAdmin)mAdminList.get(i);
            int j = isPackageDisappearing(activeadmin.info.getPackageName());
            if(j == 3 || j == 2) {
                Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Admin unexpectedly uninstalled: ").append(activeadmin.info.getComponent()).toString());
                flag = true;
                mAdminList.remove(i);
                break MISSING_BLOCK_LABEL_252;
            }
            flag1 = isPackageModified(activeadmin.info.getPackageName());
            Exception exception;
            if(flag1)
                try {
                    mContext.getPackageManager().getReceiverInfo(activeadmin.info.getComponent(), 0);
                }
                catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                    Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Admin package change removed component: ").append(activeadmin.info.getComponent()).toString());
                    flag = true;
                    mAdminList.remove(i);
                }
                finally {
                    devicepolicymanagerservice;
                }
            if(true)
                break MISSING_BLOCK_LABEL_252;
            JVM INSTR monitorexit ;
            throw exception;
_L2:
            if(!flag)
                break MISSING_BLOCK_LABEL_249;
            validatePasswordOwnerLocked();
            syncDeviceCapabilitiesLocked();
            saveSettingsLocked();
            devicepolicymanagerservice;
            JVM INSTR monitorexit ;
            return;
            i--;
              goto _L3
        }

        final DevicePolicyManagerService this$0;

        MyPackageMonitor() {
            this$0 = DevicePolicyManagerService.this;
            super();
        }
    }

    static class ActiveAdmin {

        void dump(String s, PrintWriter printwriter) {
            printwriter.print(s);
            printwriter.print("uid=");
            printwriter.println(getUid());
            printwriter.print(s);
            printwriter.println("policies:");
            ArrayList arraylist = info.getUsedPolicies();
            if(arraylist != null) {
                for(int i = 0; i < arraylist.size(); i++) {
                    printwriter.print(s);
                    printwriter.print("  ");
                    printwriter.println(((android.app.admin.DeviceAdminInfo.PolicyInfo)arraylist.get(i)).tag);
                }

            }
            printwriter.print(s);
            printwriter.print("passwordQuality=0x");
            printwriter.println(Integer.toHexString(passwordQuality));
            printwriter.print(s);
            printwriter.print("minimumPasswordLength=");
            printwriter.println(minimumPasswordLength);
            printwriter.print(s);
            printwriter.print("passwordHistoryLength=");
            printwriter.println(passwordHistoryLength);
            printwriter.print(s);
            printwriter.print("minimumPasswordUpperCase=");
            printwriter.println(minimumPasswordUpperCase);
            printwriter.print(s);
            printwriter.print("minimumPasswordLowerCase=");
            printwriter.println(minimumPasswordLowerCase);
            printwriter.print(s);
            printwriter.print("minimumPasswordLetters=");
            printwriter.println(minimumPasswordLetters);
            printwriter.print(s);
            printwriter.print("minimumPasswordNumeric=");
            printwriter.println(minimumPasswordNumeric);
            printwriter.print(s);
            printwriter.print("minimumPasswordSymbols=");
            printwriter.println(minimumPasswordSymbols);
            printwriter.print(s);
            printwriter.print("minimumPasswordNonLetter=");
            printwriter.println(minimumPasswordNonLetter);
            printwriter.print(s);
            printwriter.print("maximumTimeToUnlock=");
            printwriter.println(maximumTimeToUnlock);
            printwriter.print(s);
            printwriter.print("maximumFailedPasswordsForWipe=");
            printwriter.println(maximumFailedPasswordsForWipe);
            printwriter.print(s);
            printwriter.print("specifiesGlobalProxy=");
            printwriter.println(specifiesGlobalProxy);
            printwriter.print(s);
            printwriter.print("passwordExpirationTimeout=");
            printwriter.println(passwordExpirationTimeout);
            printwriter.print(s);
            printwriter.print("passwordExpirationDate=");
            printwriter.println(passwordExpirationDate);
            if(globalProxySpec != null) {
                printwriter.print(s);
                printwriter.print("globalProxySpec=");
                printwriter.println(globalProxySpec);
            }
            if(globalProxyExclusionList != null) {
                printwriter.print(s);
                printwriter.print("globalProxyEclusionList=");
                printwriter.println(globalProxyExclusionList);
            }
            printwriter.print(s);
            printwriter.print("encryptionRequested=");
            printwriter.println(encryptionRequested);
            printwriter.print(s);
            printwriter.print("disableCamera=");
            printwriter.println(disableCamera);
        }

        int getUid() {
            return info.getActivityInfo().applicationInfo.uid;
        }

        void readFromXml(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
            int i = xmlpullparser.getDepth();
            do {
                int j = xmlpullparser.next();
                if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                    break;
                if(j != 3 && j != 4) {
                    String s = xmlpullparser.getName();
                    if("policies".equals(s))
                        info.readPoliciesFromXml(xmlpullparser);
                    else
                    if("password-quality".equals(s))
                        passwordQuality = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-length".equals(s))
                        minimumPasswordLength = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("password-history-length".equals(s))
                        passwordHistoryLength = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-uppercase".equals(s))
                        minimumPasswordUpperCase = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-lowercase".equals(s))
                        minimumPasswordLowerCase = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-letters".equals(s))
                        minimumPasswordLetters = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-numeric".equals(s))
                        minimumPasswordNumeric = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-symbols".equals(s))
                        minimumPasswordSymbols = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("min-password-nonletter".equals(s))
                        minimumPasswordNonLetter = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("max-time-to-unlock".equals(s))
                        maximumTimeToUnlock = Long.parseLong(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("max-failed-password-wipe".equals(s))
                        maximumFailedPasswordsForWipe = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("specifies-global-proxy".equals(s))
                        specifiesGlobalProxy = Boolean.parseBoolean(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("global-proxy-spec".equals(s))
                        globalProxySpec = xmlpullparser.getAttributeValue(null, "value");
                    else
                    if("global-proxy-exclusion-list".equals(s))
                        globalProxyExclusionList = xmlpullparser.getAttributeValue(null, "value");
                    else
                    if("password-expiration-timeout".equals(s))
                        passwordExpirationTimeout = Long.parseLong(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("password-expiration-date".equals(s))
                        passwordExpirationDate = Long.parseLong(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("encryption-requested".equals(s))
                        encryptionRequested = Boolean.parseBoolean(xmlpullparser.getAttributeValue(null, "value"));
                    else
                    if("disable-camera".equals(s))
                        disableCamera = Boolean.parseBoolean(xmlpullparser.getAttributeValue(null, "value"));
                    else
                        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Unknown admin tag: ").append(s).toString());
                    XmlUtils.skipCurrentTag(xmlpullparser);
                }
            } while(true);
        }

        void writeToXml(XmlSerializer xmlserializer) throws IllegalArgumentException, IllegalStateException, IOException {
            xmlserializer.startTag(null, "policies");
            info.writePoliciesToXml(xmlserializer);
            xmlserializer.endTag(null, "policies");
            if(passwordQuality != 0) {
                xmlserializer.startTag(null, "password-quality");
                xmlserializer.attribute(null, "value", Integer.toString(passwordQuality));
                xmlserializer.endTag(null, "password-quality");
                if(minimumPasswordLength != 0) {
                    xmlserializer.startTag(null, "min-password-length");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordLength));
                    xmlserializer.endTag(null, "min-password-length");
                }
                if(passwordHistoryLength != 0) {
                    xmlserializer.startTag(null, "password-history-length");
                    xmlserializer.attribute(null, "value", Integer.toString(passwordHistoryLength));
                    xmlserializer.endTag(null, "password-history-length");
                }
                if(minimumPasswordUpperCase != 0) {
                    xmlserializer.startTag(null, "min-password-uppercase");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordUpperCase));
                    xmlserializer.endTag(null, "min-password-uppercase");
                }
                if(minimumPasswordLowerCase != 0) {
                    xmlserializer.startTag(null, "min-password-lowercase");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordLowerCase));
                    xmlserializer.endTag(null, "min-password-lowercase");
                }
                if(minimumPasswordLetters != 1) {
                    xmlserializer.startTag(null, "min-password-letters");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordLetters));
                    xmlserializer.endTag(null, "min-password-letters");
                }
                if(minimumPasswordNumeric != 1) {
                    xmlserializer.startTag(null, "min-password-numeric");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordNumeric));
                    xmlserializer.endTag(null, "min-password-numeric");
                }
                if(minimumPasswordSymbols != 1) {
                    xmlserializer.startTag(null, "min-password-symbols");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordSymbols));
                    xmlserializer.endTag(null, "min-password-symbols");
                }
                if(minimumPasswordNonLetter > 0) {
                    xmlserializer.startTag(null, "min-password-nonletter");
                    xmlserializer.attribute(null, "value", Integer.toString(minimumPasswordNonLetter));
                    xmlserializer.endTag(null, "min-password-nonletter");
                }
            }
            if(maximumTimeToUnlock != 0L) {
                xmlserializer.startTag(null, "max-time-to-unlock");
                xmlserializer.attribute(null, "value", Long.toString(maximumTimeToUnlock));
                xmlserializer.endTag(null, "max-time-to-unlock");
            }
            if(maximumFailedPasswordsForWipe != 0) {
                xmlserializer.startTag(null, "max-failed-password-wipe");
                xmlserializer.attribute(null, "value", Integer.toString(maximumFailedPasswordsForWipe));
                xmlserializer.endTag(null, "max-failed-password-wipe");
            }
            if(specifiesGlobalProxy) {
                xmlserializer.startTag(null, "specifies-global-proxy");
                xmlserializer.attribute(null, "value", Boolean.toString(specifiesGlobalProxy));
                xmlserializer.endTag(null, "specifies_global_proxy");
                if(globalProxySpec != null) {
                    xmlserializer.startTag(null, "global-proxy-spec");
                    xmlserializer.attribute(null, "value", globalProxySpec);
                    xmlserializer.endTag(null, "global-proxy-spec");
                }
                if(globalProxyExclusionList != null) {
                    xmlserializer.startTag(null, "global-proxy-exclusion-list");
                    xmlserializer.attribute(null, "value", globalProxyExclusionList);
                    xmlserializer.endTag(null, "global-proxy-exclusion-list");
                }
            }
            if(passwordExpirationTimeout != 0L) {
                xmlserializer.startTag(null, "password-expiration-timeout");
                xmlserializer.attribute(null, "value", Long.toString(passwordExpirationTimeout));
                xmlserializer.endTag(null, "password-expiration-timeout");
            }
            if(passwordExpirationDate != 0L) {
                xmlserializer.startTag(null, "password-expiration-date");
                xmlserializer.attribute(null, "value", Long.toString(passwordExpirationDate));
                xmlserializer.endTag(null, "password-expiration-date");
            }
            if(encryptionRequested) {
                xmlserializer.startTag(null, "encryption-requested");
                xmlserializer.attribute(null, "value", Boolean.toString(encryptionRequested));
                xmlserializer.endTag(null, "encryption-requested");
            }
            if(disableCamera) {
                xmlserializer.startTag(null, "disable-camera");
                xmlserializer.attribute(null, "value", Boolean.toString(disableCamera));
                xmlserializer.endTag(null, "disable-camera");
            }
        }

        static final int DEF_MAXIMUM_FAILED_PASSWORDS_FOR_WIPE = 0;
        static final long DEF_MAXIMUM_TIME_TO_UNLOCK = 0L;
        static final int DEF_MINIMUM_PASSWORD_LENGTH = 0;
        static final int DEF_MINIMUM_PASSWORD_LETTERS = 1;
        static final int DEF_MINIMUM_PASSWORD_LOWER_CASE = 0;
        static final int DEF_MINIMUM_PASSWORD_NON_LETTER = 0;
        static final int DEF_MINIMUM_PASSWORD_NUMERIC = 1;
        static final int DEF_MINIMUM_PASSWORD_SYMBOLS = 1;
        static final int DEF_MINIMUM_PASSWORD_UPPER_CASE;
        static final long DEF_PASSWORD_EXPIRATION_DATE;
        static final long DEF_PASSWORD_EXPIRATION_TIMEOUT;
        static final int DEF_PASSWORD_HISTORY_LENGTH;
        boolean disableCamera;
        boolean encryptionRequested;
        String globalProxyExclusionList;
        String globalProxySpec;
        final DeviceAdminInfo info;
        int maximumFailedPasswordsForWipe;
        long maximumTimeToUnlock;
        int minimumPasswordLength;
        int minimumPasswordLetters;
        int minimumPasswordLowerCase;
        int minimumPasswordNonLetter;
        int minimumPasswordNumeric;
        int minimumPasswordSymbols;
        int minimumPasswordUpperCase;
        long passwordExpirationDate;
        long passwordExpirationTimeout;
        int passwordHistoryLength;
        int passwordQuality;
        boolean specifiesGlobalProxy;

        ActiveAdmin(DeviceAdminInfo deviceadmininfo) {
            passwordQuality = 0;
            minimumPasswordLength = 0;
            passwordHistoryLength = 0;
            minimumPasswordUpperCase = 0;
            minimumPasswordLowerCase = 0;
            minimumPasswordLetters = 1;
            minimumPasswordNumeric = 1;
            minimumPasswordSymbols = 1;
            minimumPasswordNonLetter = 0;
            maximumTimeToUnlock = 0L;
            maximumFailedPasswordsForWipe = 0;
            passwordExpirationTimeout = 0L;
            passwordExpirationDate = 0L;
            encryptionRequested = false;
            disableCamera = false;
            specifiesGlobalProxy = false;
            globalProxySpec = null;
            globalProxyExclusionList = null;
            info = deviceadmininfo;
        }
    }


    public DevicePolicyManagerService(Context context) {
        mActivePasswordQuality = 0;
        mActivePasswordLength = 0;
        mActivePasswordUpperCase = 0;
        mActivePasswordLowerCase = 0;
        mActivePasswordLetters = 0;
        mActivePasswordNumeric = 0;
        mActivePasswordSymbols = 0;
        mActivePasswordNonLetter = 0;
        mFailedPasswordAttempts = 0;
        mPasswordOwner = -1;
        mHandler = new Handler();
        mLastMaximumTimeToLock = -1L;
        mReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                String s = intent.getAction();
                if("android.intent.action.BOOT_COMPLETED".equals(s) || "com.android.server.ACTION_EXPIRED_PASSWORD_NOTIFICATION".equals(s)) {
                    Slog.v("DevicePolicyManagerService", (new StringBuilder()).append("Sending password expiration notifications for action ").append(s).toString());
                    mHandler.post(new Runnable() {

                        public void run() {
                            handlePasswordExpirationNotification();
                        }

                        final _cls1 this$1;

                     {
                        this$1 = _cls1.this;
                        super();
                    }
                    });
                }
            }

            final DevicePolicyManagerService this$0;

             {
                this$0 = DevicePolicyManagerService.this;
                super();
            }
        };
        mContext = context;
        mMonitor.register(context, null, true);
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "DPM");
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentfilter.addAction("com.android.server.ACTION_EXPIRED_PASSWORD_NOTIFICATION");
        context.registerReceiver(mReceiver, intentfilter);
    }

    private int getEncryptionStatus() {
        String s = SystemProperties.get("ro.crypto.state", "unsupported");
        byte byte0;
        if("encrypted".equalsIgnoreCase(s))
            byte0 = 3;
        else
        if("unencrypted".equalsIgnoreCase(s))
            byte0 = 1;
        else
            byte0 = 0;
        return byte0;
    }

    private IPowerManager getIPowerManager() {
        if(mIPowerManager == null)
            mIPowerManager = android.os.IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
        return mIPowerManager;
    }

    private long getPasswordExpirationLocked(ComponentName componentname) {
        long l = 0L;
        if(componentname != null) {
            ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
            if(activeadmin1 != null)
                l = activeadmin1.passwordExpirationDate;
        } else {
            long l1 = 0L;
            int i = mAdminList.size();
            for(int j = 0; j < i; j++) {
                ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
                if(l1 == l || activeadmin.passwordExpirationDate != l && l1 > activeadmin.passwordExpirationDate)
                    l1 = activeadmin.passwordExpirationDate;
            }

            l = l1;
        }
        return l;
    }

    private IWindowManager getWindowManager() {
        if(mIWindowManager == null)
            mIWindowManager = android.view.IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        return mIWindowManager;
    }

    private void handlePasswordExpirationNotification() {
        this;
        JVM INSTR monitorenter ;
        long l;
        int i;
        l = System.currentTimeMillis();
        i = mAdminList.size();
        if(i > 0) goto _L2; else goto _L1
_L4:
        int j;
        if(j < i) {
            ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
            if(activeadmin.info.usesPolicy(6) && activeadmin.passwordExpirationTimeout > 0L && activeadmin.passwordExpirationDate > 0L && l >= activeadmin.passwordExpirationDate - 0x19bfcc00L)
                sendAdminCommandLocked(activeadmin, "android.app.action.ACTION_PASSWORD_EXPIRING");
            j++;
            continue; /* Loop/switch isn't completed */
        }
        setExpirationAlarmCheckLocked(mContext);
_L1:
        return;
_L2:
        j = 0;
        continue; /* Loop/switch isn't completed */
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean isEncryptionSupported() {
        boolean flag;
        if(getEncryptionStatus() != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isExtStorageEncrypted() {
        boolean flag;
        if(!"".equals(SystemProperties.get("vold.decrypt")))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void loadSettingsLocked() {
        FileInputStream fileinputstream;
        File file;
        JournaledFile journaledfile = makeJournaledFile();
        fileinputstream = null;
        file = journaledfile.chooseForRead();
        FileInputStream fileinputstream1 = new FileInputStream(file);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
        int i;
        do
            i = xmlpullparser.next();
        while(i != 1 && i != 2);
        String s = xmlpullparser.getName();
        if(!"policies".equals(s))
            throw new XmlPullParserException((new StringBuilder()).append("Settings do not start with policies tag: found ").append(s).toString());
          goto _L1
        NullPointerException nullpointerexception;
        nullpointerexception;
        fileinputstream = fileinputstream1;
_L17:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(nullpointerexception).toString());
_L8:
        IndexOutOfBoundsException indexoutofboundsexception;
        IOException ioexception1;
        XmlPullParserException xmlpullparserexception;
        NumberFormatException numberformatexception;
        LockPatternUtils lockpatternutils;
        int j;
        int k;
        String s1;
        String s2;
        RuntimeException runtimeexception;
        DeviceAdminInfo deviceadmininfo;
        ActiveAdmin activeadmin;
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        lockpatternutils = new LockPatternUtils(mContext);
        if(lockpatternutils.getActivePasswordQuality() < mActivePasswordQuality) {
            Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Active password quality 0x").append(Integer.toHexString(mActivePasswordQuality)).append(" does not match actual quality 0x").append(Integer.toHexString(lockpatternutils.getActivePasswordQuality())).toString());
            mActivePasswordQuality = 0;
            mActivePasswordLength = 0;
            mActivePasswordUpperCase = 0;
            mActivePasswordLowerCase = 0;
            mActivePasswordLetters = 0;
            mActivePasswordNumeric = 0;
            mActivePasswordSymbols = 0;
            mActivePasswordNonLetter = 0;
        }
        validatePasswordOwnerLocked();
        syncDeviceCapabilitiesLocked();
        updateMaximumTimeToLockLocked();
        return;
_L1:
        xmlpullparser.next();
        j = xmlpullparser.getDepth();
_L5:
        k = xmlpullparser.next();
        if(k == 1 || k == 3 && xmlpullparser.getDepth() <= j) goto _L3; else goto _L2
_L2:
        if(k == 3 || k == 4) goto _L5; else goto _L4
_L4:
        s1 = xmlpullparser.getName();
        if(!"admin".equals(s1)) goto _L7; else goto _L6
_L6:
        s2 = xmlpullparser.getAttributeValue(null, "name");
        deviceadmininfo = findAdmin(ComponentName.unflattenFromString(s2));
        if(deviceadmininfo != null) {
            activeadmin = new ActiveAdmin(deviceadmininfo);
            activeadmin.readFromXml(xmlpullparser);
            mAdminMap.put(activeadmin.info.getComponent(), activeadmin);
            mAdminList.add(activeadmin);
        }
          goto _L5
        runtimeexception;
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Failed loading admin ").append(s2).toString(), runtimeexception);
          goto _L5
        numberformatexception;
        fileinputstream = fileinputstream1;
_L16:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(numberformatexception).toString());
          goto _L8
_L7:
        if(!"failed-password-attempts".equals(s1)) goto _L10; else goto _L9
_L9:
        mFailedPasswordAttempts = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L5
        xmlpullparserexception;
        fileinputstream = fileinputstream1;
_L15:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(xmlpullparserexception).toString());
          goto _L8
_L10:
label0:
        {
            if(!"password-owner".equals(s1))
                break label0;
            mPasswordOwner = Integer.parseInt(xmlpullparser.getAttributeValue(null, "value"));
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
          goto _L5
        if(!"active-password".equals(s1)) goto _L12; else goto _L11
_L11:
        mActivePasswordQuality = Integer.parseInt(xmlpullparser.getAttributeValue(null, "quality"));
        mActivePasswordLength = Integer.parseInt(xmlpullparser.getAttributeValue(null, "length"));
        mActivePasswordUpperCase = Integer.parseInt(xmlpullparser.getAttributeValue(null, "uppercase"));
        mActivePasswordLowerCase = Integer.parseInt(xmlpullparser.getAttributeValue(null, "lowercase"));
        mActivePasswordLetters = Integer.parseInt(xmlpullparser.getAttributeValue(null, "letters"));
        mActivePasswordNumeric = Integer.parseInt(xmlpullparser.getAttributeValue(null, "numeric"));
        mActivePasswordSymbols = Integer.parseInt(xmlpullparser.getAttributeValue(null, "symbols"));
        mActivePasswordNonLetter = Integer.parseInt(xmlpullparser.getAttributeValue(null, "nonletter"));
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L5
        ioexception1;
        fileinputstream = fileinputstream1;
_L14:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(ioexception1).toString());
          goto _L8
_L12:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Unknown tag: ").append(s1).toString());
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L5
        indexoutofboundsexception;
        fileinputstream = fileinputstream1;
_L13:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(indexoutofboundsexception).toString());
          goto _L8
_L3:
        fileinputstream = fileinputstream1;
          goto _L8
        indexoutofboundsexception;
          goto _L13
        ioexception1;
          goto _L14
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
          goto _L8
        xmlpullparserexception;
          goto _L15
        numberformatexception;
          goto _L16
        nullpointerexception;
          goto _L17
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        fileinputstream = fileinputstream1;
          goto _L8
    }

    private static JournaledFile makeJournaledFile() {
        return new JournaledFile(new File("/data/system/device_policies.xml"), new File("/data/system/device_policies.xml.tmp"));
    }

    private void resetGlobalProxyLocked() {
        int i;
        int j;
        i = mAdminList.size();
        j = 0;
_L3:
        ActiveAdmin activeadmin;
        if(j >= i)
            break MISSING_BLOCK_LABEL_53;
        activeadmin = (ActiveAdmin)mAdminList.get(j);
        if(!activeadmin.specifiesGlobalProxy) goto _L2; else goto _L1
_L1:
        saveGlobalProxyLocked(activeadmin.globalProxySpec, activeadmin.globalProxyExclusionList);
_L4:
        return;
_L2:
        j++;
          goto _L3
        saveGlobalProxyLocked(null, null);
          goto _L4
    }

    private void saveGlobalProxyLocked(String s, String s1) {
        String as[];
        int i;
        if(s1 == null)
            s1 = "";
        if(s == null)
            s = "";
        as = s.trim().split(":");
        i = 8080;
        if(as.length <= 1)
            break MISSING_BLOCK_LABEL_50;
        int j = Integer.parseInt(as[1]);
        i = j;
_L2:
        String s2 = s1.trim();
        android.content.ContentResolver contentresolver = mContext.getContentResolver();
        android.provider.Settings.Secure.putString(contentresolver, "global_http_proxy_host", as[0]);
        android.provider.Settings.Secure.putInt(contentresolver, "global_http_proxy_port", i);
        android.provider.Settings.Secure.putString(contentresolver, "global_http_proxy_exclusion_list", s2);
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void saveSettingsLocked() {
        JournaledFile journaledfile;
        FileOutputStream fileoutputstream;
        journaledfile = makeJournaledFile();
        fileoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(journaledfile.chooseForWrite(), false);
        FastXmlSerializer fastxmlserializer;
        int i;
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream1, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.startTag(null, "policies");
        i = mAdminList.size();
        IOException ioexception;
        IOException ioexception1;
        IOException ioexception2;
        for(int j = 0; j < i; j++) {
            ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
            if(activeadmin != null) {
                fastxmlserializer.startTag(null, "admin");
                fastxmlserializer.attribute(null, "name", activeadmin.info.getComponent().flattenToString());
                activeadmin.writeToXml(fastxmlserializer);
                fastxmlserializer.endTag(null, "admin");
            }
            break MISSING_BLOCK_LABEL_551;
        }

        if(mPasswordOwner >= 0) {
            fastxmlserializer.startTag(null, "password-owner");
            fastxmlserializer.attribute(null, "value", Integer.toString(mPasswordOwner));
            fastxmlserializer.endTag(null, "password-owner");
        }
        if(mFailedPasswordAttempts != 0) {
            fastxmlserializer.startTag(null, "failed-password-attempts");
            fastxmlserializer.attribute(null, "value", Integer.toString(mFailedPasswordAttempts));
            fastxmlserializer.endTag(null, "failed-password-attempts");
        }
        if(mActivePasswordQuality != 0 || mActivePasswordLength != 0 || mActivePasswordUpperCase != 0 || mActivePasswordLowerCase != 0 || mActivePasswordLetters != 0 || mActivePasswordNumeric != 0 || mActivePasswordSymbols != 0 || mActivePasswordNonLetter != 0) {
            fastxmlserializer.startTag(null, "active-password");
            fastxmlserializer.attribute(null, "quality", Integer.toString(mActivePasswordQuality));
            fastxmlserializer.attribute(null, "length", Integer.toString(mActivePasswordLength));
            fastxmlserializer.attribute(null, "uppercase", Integer.toString(mActivePasswordUpperCase));
            fastxmlserializer.attribute(null, "lowercase", Integer.toString(mActivePasswordLowerCase));
            fastxmlserializer.attribute(null, "letters", Integer.toString(mActivePasswordLetters));
            fastxmlserializer.attribute(null, "numeric", Integer.toString(mActivePasswordNumeric));
            fastxmlserializer.attribute(null, "symbols", Integer.toString(mActivePasswordSymbols));
            fastxmlserializer.attribute(null, "nonletter", Integer.toString(mActivePasswordNonLetter));
            fastxmlserializer.endTag(null, "active-password");
        }
        fastxmlserializer.endTag(null, "policies");
        fastxmlserializer.endDocument();
        fileoutputstream1.close();
        journaledfile.commit();
        sendChangedNotification();
_L1:
        return;
        ioexception2;
_L2:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        journaledfile.rollback();
          goto _L1
        ioexception;
        fileoutputstream = fileoutputstream1;
          goto _L2
    }

    private void sendChangedNotification() {
        Intent intent = new Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intent.setFlags(0x40000000);
        mContext.sendBroadcast(intent);
    }

    private void setEncryptionRequested(boolean flag) {
    }

    private void updatePasswordExpirationsLocked() {
        int i = mAdminList.size();
        if(i > 0) {
            int j = 0;
            while(j < i)  {
                ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
                if(activeadmin.info.usesPolicy(6)) {
                    long l = activeadmin.passwordExpirationTimeout;
                    long l1;
                    if(l > 0L)
                        l1 = l + System.currentTimeMillis();
                    else
                        l1 = 0L;
                    activeadmin.passwordExpirationDate = l1;
                }
                j++;
            }
            saveSettingsLocked();
        }
    }

    static void validateQualityConstant(int i) {
        switch(i) {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid quality constant: 0x").append(Integer.toHexString(i)).toString());

        case 0: // '\0'
        case 32768: 
        case 65536: 
        case 131072: 
        case 262144: 
        case 327680: 
        case 393216: 
            return;
        }
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump DevicePolicyManagerService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        PrintWriterPrinter printwriterprinter = new PrintWriterPrinter(printwriter);
        this;
        JVM INSTR monitorenter ;
label0:
        {
            printwriterprinter.println("Current Device Policy Manager state:");
            printwriterprinter.println("  Enabled Device Admins:");
            int i = mAdminList.size();
            for(int j = 0; j < i; j++) {
                ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
                if(activeadmin != null) {
                    printwriter.print("  ");
                    printwriter.print(activeadmin.info.getComponent().flattenToShortString());
                    printwriter.println(":");
                    activeadmin.dump("    ", printwriter);
                }
                break label0;
            }

            printwriter.println(" ");
            printwriter.print("  mPasswordOwner=");
            printwriter.println(mPasswordOwner);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public DeviceAdminInfo findAdmin(ComponentName componentname) {
        List list;
        Intent intent = new Intent();
        intent.setComponent(componentname);
        list = mContext.getPackageManager().queryBroadcastReceivers(intent, 128);
        if(list == null || list.size() <= 0)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown admin: ").append(componentname).toString());
        DeviceAdminInfo deviceadmininfo = new DeviceAdminInfo(mContext, (ResolveInfo)list.get(0));
        DeviceAdminInfo deviceadmininfo1 = deviceadmininfo;
_L2:
        return deviceadmininfo1;
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Bad device admin requested: ").append(componentname).toString(), xmlpullparserexception);
        deviceadmininfo1 = null;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Bad device admin requested: ").append(componentname).toString(), ioexception);
        deviceadmininfo1 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    ActiveAdmin getActiveAdminForCallerLocked(ComponentName componentname, int i) throws SecurityException {
        int j = Binder.getCallingUid();
        if(componentname == null) goto _L2; else goto _L1
_L1:
        ActiveAdmin activeadmin;
        activeadmin = (ActiveAdmin)mAdminMap.get(componentname);
        if(activeadmin == null)
            throw new SecurityException((new StringBuilder()).append("No active admin ").append(componentname).toString());
        if(activeadmin.getUid() != j)
            throw new SecurityException((new StringBuilder()).append("Admin ").append(componentname).append(" is not owned by uid ").append(Binder.getCallingUid()).toString());
        if(!activeadmin.info.usesPolicy(i))
            throw new SecurityException((new StringBuilder()).append("Admin ").append(activeadmin.info.getComponent()).append(" did not specify uses-policy for: ").append(activeadmin.info.getTagForPolicy(i)).toString());
          goto _L3
_L2:
        int k;
        int l;
        k = mAdminList.size();
        l = 0;
_L6:
        if(l >= k)
            break; /* Loop/switch isn't completed */
        activeadmin = (ActiveAdmin)mAdminList.get(l);
        if(activeadmin.getUid() != j || !activeadmin.info.usesPolicy(i)) goto _L4; else goto _L3
_L3:
        return activeadmin;
_L4:
        l++;
        if(true) goto _L6; else goto _L5
_L5:
        throw new SecurityException((new StringBuilder()).append("No active admin owned by uid ").append(Binder.getCallingUid()).append(" for policy #").append(i).toString());
    }

    ActiveAdmin getActiveAdminUncheckedLocked(ComponentName componentname) {
        ActiveAdmin activeadmin = (ActiveAdmin)mAdminMap.get(componentname);
        if(activeadmin == null || !componentname.getPackageName().equals(((ComponentInfo) (activeadmin.info.getActivityInfo())).packageName) || !componentname.getClassName().equals(((ComponentInfo) (activeadmin.info.getActivityInfo())).name))
            activeadmin = null;
        return activeadmin;
    }

    public List getActiveAdmins() {
        this;
        JVM INSTR monitorenter ;
        int i = mAdminList.size();
        ArrayList arraylist;
        if(i <= 0) {
            arraylist = null;
        } else {
            arraylist = new ArrayList(i);
            for(int j = 0; j < i; j++)
                arraylist.add(((ActiveAdmin)mAdminList.get(j)).info.getComponent());

        }
        return arraylist;
    }

    public boolean getCameraDisabled(ComponentName componentname) {
        boolean flag = false;
        this;
        JVM INSTR monitorenter ;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        ActiveAdmin activeadmin = getActiveAdminUncheckedLocked(componentname);
        if(activeadmin != null)
            flag = activeadmin.disableCamera;
          goto _L3
_L2:
        int i;
        int j;
        i = mAdminList.size();
        j = 0;
_L7:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        if(!((ActiveAdmin)mAdminList.get(j)).disableCamera) goto _L5; else goto _L4
_L4:
        flag = true;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L5:
        j++;
        if(true) goto _L7; else goto _L6
_L6:
        this;
        JVM INSTR monitorexit ;
_L3:
        return flag;
    }

    public int getCurrentFailedPasswordAttempts() {
        this;
        JVM INSTR monitorenter ;
        getActiveAdminForCallerLocked(null, 1);
        int i = mFailedPasswordAttempts;
        return i;
    }

    public ComponentName getGlobalProxyAdmin() {
        this;
        JVM INSTR monitorenter ;
        int i = mAdminList.size();
        int j = 0;
        do {
label0:
            {
                ComponentName componentname;
                if(j < i) {
                    ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(j);
                    if(!activeadmin.specifiesGlobalProxy)
                        break label0;
                    componentname = activeadmin.info.getComponent();
                } else {
                    componentname = null;
                }
                return componentname;
            }
            j++;
        } while(true);
    }

    public int getMaximumFailedPasswordsForWipe(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.maximumFailedPasswordsForWipe;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i == 0)
                i = activeadmin.maximumFailedPasswordsForWipe;
            else
            if(activeadmin.maximumFailedPasswordsForWipe != 0 && i > activeadmin.maximumFailedPasswordsForWipe)
                i = activeadmin.maximumFailedPasswordsForWipe;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public long getMaximumTimeToLock(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        long l = 0L;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int j;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int i;
        long l1;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l1 = activeadmin1.maximumTimeToUnlock;
        else
            l1 = l;
          goto _L3
_L2:
        i = mAdminList.size();
        j = 0;
_L5:
        if(j < i) {
            activeadmin = (ActiveAdmin)mAdminList.get(j);
            if(l == 0L)
                l = activeadmin.maximumTimeToUnlock;
            else
            if(activeadmin.maximumTimeToUnlock != 0L && l > activeadmin.maximumTimeToUnlock)
                l = activeadmin.maximumTimeToUnlock;
            j++;
            continue; /* Loop/switch isn't completed */
        }
        l1 = l;
_L3:
        return l1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public long getPasswordExpiration(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        long l = getPasswordExpirationLocked(componentname);
        return l;
    }

    public long getPasswordExpirationTimeout(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int j;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        long l;
        int i;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.passwordExpirationTimeout;
        else
            l = 0L;
          goto _L3
_L2:
        l = 0L;
        i = mAdminList.size();
        j = 0;
_L5:
        if(j < i) {
            activeadmin = (ActiveAdmin)mAdminList.get(j);
            if(l == 0L || activeadmin.passwordExpirationTimeout != 0L && l > activeadmin.passwordExpirationTimeout)
                l = activeadmin.passwordExpirationTimeout;
            j++;
            continue; /* Loop/switch isn't completed */
        }
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordHistoryLength(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.passwordHistoryLength;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.passwordHistoryLength)
                i = activeadmin.passwordHistoryLength;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumLength(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordLength;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordLength)
                i = activeadmin.minimumPasswordLength;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumLetters(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordLetters;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordLetters)
                i = activeadmin.minimumPasswordLetters;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumLowerCase(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordLowerCase;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordLowerCase)
                i = activeadmin.minimumPasswordLowerCase;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumNonLetter(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordNonLetter;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordNonLetter)
                i = activeadmin.minimumPasswordNonLetter;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumNumeric(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordNumeric;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordNumeric)
                i = activeadmin.minimumPasswordNumeric;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumSymbols(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordSymbols;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordSymbols)
                i = activeadmin.minimumPasswordSymbols;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordMinimumUpperCase(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.minimumPasswordUpperCase;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.minimumPasswordUpperCase)
                i = activeadmin.minimumPasswordUpperCase;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getPasswordQuality(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        if(componentname == null) goto _L2; else goto _L1
_L1:
        int k;
        ActiveAdmin activeadmin1 = getActiveAdminUncheckedLocked(componentname);
        int j;
        int l;
        ActiveAdmin activeadmin;
        if(activeadmin1 != null)
            l = activeadmin1.passwordQuality;
        else
            l = 0;
          goto _L3
_L2:
        j = mAdminList.size();
        k = 0;
_L5:
        if(k < j) {
            activeadmin = (ActiveAdmin)mAdminList.get(k);
            if(i < activeadmin.passwordQuality)
                i = activeadmin.passwordQuality;
            k++;
            continue; /* Loop/switch isn't completed */
        }
        l = i;
_L3:
        return l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void getRemoveWarning(ComponentName componentname, final RemoteCallback result) {
        mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        this;
        JVM INSTR monitorenter ;
        ActiveAdmin activeadmin = getActiveAdminUncheckedLocked(componentname);
        if(activeadmin == null) {
            Exception exception;
            Intent intent;
            try {
                result.sendResult(null);
            }
            catch(RemoteException remoteexception) { }
            finally {
                this;
            }
            break MISSING_BLOCK_LABEL_100;
        }
        intent = new Intent("android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED");
        intent.setComponent(activeadmin.info.getComponent());
        mContext.sendOrderedBroadcast(intent, null, new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent1) {
                result.sendResult(getResultExtras(false));
_L2:
                return;
                RemoteException remoteexception1;
                remoteexception1;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final DevicePolicyManagerService this$0;
            final RemoteCallback val$result;

             {
                this$0 = DevicePolicyManagerService.this;
                result = remotecallback;
                super();
            }
        }, null, -1, null, null);
        if(true)
            break MISSING_BLOCK_LABEL_100;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean getStorageEncryption(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        if(componentname != null) {
            ActiveAdmin activeadmin = getActiveAdminUncheckedLocked(componentname);
            Exception exception;
            int i;
            int j;
            boolean flag;
            if(activeadmin != null)
                flag = activeadmin.encryptionRequested;
            else
                flag = false;
            break MISSING_BLOCK_LABEL_89;
        }
        i = mAdminList.size();
        j = 0;
        if(j >= i)
            break;
        if(((ActiveAdmin)mAdminList.get(j)).encryptionRequested) {
            flag = true;
            break MISSING_BLOCK_LABEL_89;
        }
        break MISSING_BLOCK_LABEL_78;
        exception;
        throw exception;
        for(j++; true;)
            break MISSING_BLOCK_LABEL_41;

        this;
        JVM INSTR monitorexit ;
        flag = false;
        return flag;
    }

    public int getStorageEncryptionStatus() {
        return getEncryptionStatus();
    }

    public boolean hasGrantedPolicy(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        ActiveAdmin activeadmin;
        activeadmin = getActiveAdminUncheckedLocked(componentname);
        if(activeadmin == null)
            throw new SecurityException((new StringBuilder()).append("No active admin ").append(componentname).toString());
        break MISSING_BLOCK_LABEL_47;
        Exception exception;
        exception;
        throw exception;
        boolean flag = activeadmin.info.usesPolicy(i);
        this;
        JVM INSTR monitorexit ;
        return flag;
    }

    public boolean isActivePasswordSufficient() {
        boolean flag = true;
        this;
        JVM INSTR monitorenter ;
        getActiveAdminForCallerLocked(null, 0);
        if(mActivePasswordQuality >= getPasswordQuality(null) && mActivePasswordLength >= getPasswordMinimumLength(null)) goto _L2; else goto _L1
_L1:
        flag = false;
          goto _L3
_L2:
        if(mActivePasswordQuality == 0x60000) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L4:
        if(mActivePasswordUpperCase < getPasswordMinimumUpperCase(null) || mActivePasswordLowerCase < getPasswordMinimumLowerCase(null) || mActivePasswordLetters < getPasswordMinimumLetters(null) || mActivePasswordNumeric < getPasswordMinimumNumeric(null) || mActivePasswordSymbols < getPasswordMinimumSymbols(null) || mActivePasswordNonLetter < getPasswordMinimumNonLetter(null))
            flag = false;
        this;
        JVM INSTR monitorexit ;
_L3:
        return flag;
    }

    public boolean isAdminActive(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        if(getActiveAdminUncheckedLocked(componentname) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void lockNow() {
        this;
        JVM INSTR monitorenter ;
        long l;
        getActiveAdminForCallerLocked(null, 3);
        l = Binder.clearCallingIdentity();
        mIPowerManager.goToSleepWithReason(SystemClock.uptimeMillis(), 1);
        getWindowManager().lockNow();
        Binder.restoreCallingIdentity(l);
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l);
          goto _L1
    }

    public boolean packageHasActiveAdmins(String s) {
        this;
        JVM INSTR monitorenter ;
        int i = mAdminList.size();
        int j = 0;
        do {
label0:
            {
                boolean flag;
                if(j < i) {
                    if(!((ActiveAdmin)mAdminList.get(j)).info.getPackageName().equals(s))
                        break label0;
                    flag = true;
                } else {
                    flag = false;
                }
                return flag;
            }
            j++;
        } while(true);
    }

    public void removeActiveAdmin(ComponentName componentname) {
        this;
        JVM INSTR monitorenter ;
        ActiveAdmin activeadmin;
        activeadmin = getActiveAdminUncheckedLocked(componentname);
        if(activeadmin != null)
            break MISSING_BLOCK_LABEL_17;
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_73;
        long l;
        if(activeadmin.getUid() != Binder.getCallingUid())
            mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        l = Binder.clearCallingIdentity();
        removeActiveAdminLocked(componentname);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_73;
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    void removeActiveAdminLocked(final ComponentName adminReceiver) {
        final ActiveAdmin admin = getActiveAdminUncheckedLocked(adminReceiver);
        if(admin != null)
            sendAdminCommandLocked(admin, "android.app.action.DEVICE_ADMIN_DISABLED", new BroadcastReceiver() {

                public void onReceive(Context context, Intent intent) {
                    DevicePolicyManagerService devicepolicymanagerservice = DevicePolicyManagerService.this;
                    devicepolicymanagerservice;
                    JVM INSTR monitorenter ;
                    boolean flag = admin.info.usesPolicy(5);
                    mAdminList.remove(admin);
                    mAdminMap.remove(adminReceiver);
                    validatePasswordOwnerLocked();
                    syncDeviceCapabilitiesLocked();
                    if(flag)
                        resetGlobalProxyLocked();
                    saveSettingsLocked();
                    updateMaximumTimeToLockLocked();
                    return;
                }

                final DevicePolicyManagerService this$0;
                final ActiveAdmin val$admin;
                final ComponentName val$adminReceiver;

             {
                this$0 = DevicePolicyManagerService.this;
                admin = activeadmin;
                adminReceiver = componentname;
                super();
            }
            });
    }

    public void reportFailedPasswordAttempt() {
        mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        mFailedPasswordAttempts = 1 + mFailedPasswordAttempts;
        saveSettingsLocked();
        int i = getMaximumFailedPasswordsForWipe(null);
        if(i > 0 && mFailedPasswordAttempts >= i)
            wipeDataLocked(0);
        sendAdminCommandLocked("android.app.action.ACTION_PASSWORD_FAILED", 1);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public void reportSuccessfulPasswordAttempt() {
        mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        this;
        JVM INSTR monitorenter ;
        long l;
        if(mFailedPasswordAttempts == 0 && mPasswordOwner < 0)
            break MISSING_BLOCK_LABEL_58;
        l = Binder.clearCallingIdentity();
        mFailedPasswordAttempts = 0;
        mPasswordOwner = -1;
        saveSettingsLocked();
        sendAdminCommandLocked("android.app.action.ACTION_PASSWORD_SUCCEEDED", 1);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean resetPassword(String s, int i) {
        this;
        JVM INSTR monitorenter ;
        int j;
        getActiveAdminForCallerLocked(null, 2);
        j = getPasswordQuality(null);
        if(j == 0) goto _L2; else goto _L1
_L1:
        int l4 = LockPatternUtils.computePasswordQuality(s);
        if(l4 >= j || j == 0x60000) goto _L4; else goto _L3
_L3:
        boolean flag;
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: password quality 0x").append(Integer.toHexString(j)).append(" does not meet required quality 0x").append(Integer.toHexString(j)).toString());
        flag = false;
          goto _L5
_L4:
        j = Math.max(l4, j);
_L2:
        int k = getPasswordMinimumLength(null);
        if(s.length() >= k) goto _L7; else goto _L6
_L6:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: password length ").append(s.length()).append(" does not meet required length ").append(k).toString());
        flag = false;
          goto _L5
        Exception exception;
        exception;
        throw exception;
_L7:
        if(j != 0x60000) goto _L9; else goto _L8
_L8:
        int j1;
        int k1;
        int i2;
        int j2;
        int k2;
        int l2;
        int i3;
        j1 = 0;
        k1 = 0;
        i2 = 0;
        j2 = 0;
        k2 = 0;
        l2 = 0;
        i3 = 0;
_L27:
        if(i3 >= s.length()) goto _L11; else goto _L10
_L10:
        char c;
        c = s.charAt(i3);
        if(c < 'A' || c > 'Z')
            break; /* Loop/switch isn't completed */
        j1++;
        k1++;
          goto _L12
_L11:
        int j3 = getPasswordMinimumLetters(null);
        if(j1 >= j3) goto _L14; else goto _L13
_L13:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of letters ").append(j1).append(" does not meet required number of letters ").append(j3).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L14:
        int k3 = getPasswordMinimumNumeric(null);
        if(j2 >= k3) goto _L16; else goto _L15
_L15:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of numerical digits ").append(j2).append(" does not meet required number of numerical digits ").append(k3).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L16:
        int l3 = getPasswordMinimumLowerCase(null);
        if(i2 >= l3) goto _L18; else goto _L17
_L17:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of lowercase letters ").append(i2).append(" does not meet required number of lowercase letters ").append(l3).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L18:
        int i4 = getPasswordMinimumUpperCase(null);
        if(k1 >= i4) goto _L20; else goto _L19
_L19:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of uppercase letters ").append(k1).append(" does not meet required number of uppercase letters ").append(i4).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L20:
        int j4 = getPasswordMinimumSymbols(null);
        if(k2 >= j4) goto _L22; else goto _L21
_L21:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of special symbols ").append(k2).append(" does not meet required number of special symbols ").append(j4).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L22:
        int k4 = getPasswordMinimumNonLetter(null);
        if(l2 >= k4) goto _L9; else goto _L23
_L23:
        Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("resetPassword: number of non-letter characters ").append(l2).append(" does not meet required number of non-letter characters ").append(k4).toString());
        flag = false;
        this;
        JVM INSTR monitorexit ;
          goto _L5
_L9:
        this;
        JVM INSTR monitorexit ;
        int l = Binder.getCallingUid();
        if(mPasswordOwner < 0 || mPasswordOwner == l) goto _L25; else goto _L24
_L24:
        Slog.w("DevicePolicyManagerService", "resetPassword: already set by another uid and not entered by user");
        flag = false;
          goto _L5
_L25:
        long l1 = Binder.clearCallingIdentity();
        (new LockPatternUtils(mContext)).saveLockPassword(s, j);
        this;
        JVM INSTR monitorenter ;
        int i1;
        if((i & 1) != 0)
            i1 = l;
        else
            i1 = -1;
        if(mPasswordOwner != i1) {
            mPasswordOwner = i1;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l1);
        flag = true;
          goto _L5
        Exception exception2;
        exception2;
        this;
        JVM INSTR monitorexit ;
        throw exception2;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l1);
        throw exception1;
_L5:
        return flag;
_L12:
        i3++;
        if(true) goto _L27; else goto _L26
_L26:
        if(c >= 'a' && c <= 'z') {
            j1++;
            i2++;
        } else
        if(c >= '0' && c <= '9') {
            j2++;
            l2++;
        } else {
            k2++;
            l2++;
        }
        if(true) goto _L12; else goto _L28
_L28:
        if(true) goto _L27; else goto _L29
_L29:
    }

    void sendAdminCommandLocked(ActiveAdmin activeadmin, String s) {
        sendAdminCommandLocked(activeadmin, s, null);
    }

    void sendAdminCommandLocked(ActiveAdmin activeadmin, String s, BroadcastReceiver broadcastreceiver) {
        Intent intent = new Intent(s);
        intent.setComponent(activeadmin.info.getComponent());
        if(s.equals("android.app.action.ACTION_PASSWORD_EXPIRING"))
            intent.putExtra("expiration", activeadmin.passwordExpirationDate);
        if(broadcastreceiver != null)
            mContext.sendOrderedBroadcast(intent, null, broadcastreceiver, mHandler, -1, null, null);
        else
            mContext.sendBroadcast(intent);
    }

    void sendAdminCommandLocked(String s, int i) {
        int j = mAdminList.size();
        if(j > 0) {
            for(int k = 0; k < j; k++) {
                ActiveAdmin activeadmin = (ActiveAdmin)mAdminList.get(k);
                if(activeadmin.info.usesPolicy(i))
                    sendAdminCommandLocked(activeadmin, s);
            }

        }
    }

    public void setActiveAdmin(ComponentName componentname, boolean flag) {
        DeviceAdminInfo deviceadmininfo;
        mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        deviceadmininfo = findAdmin(componentname);
        if(deviceadmininfo == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Bad admin: ").append(componentname).toString());
        this;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        if(flag)
            break MISSING_BLOCK_LABEL_96;
        if(getActiveAdminUncheckedLocked(componentname) != null)
            throw new IllegalArgumentException("Admin is already added");
        break MISSING_BLOCK_LABEL_96;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin;
        int i;
        activeadmin = new ActiveAdmin(deviceadmininfo);
        mAdminMap.put(componentname, activeadmin);
        i = -1;
        if(!flag) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        j = mAdminList.size();
        k = 0;
_L6:
        if(k >= j) goto _L2; else goto _L3
_L3:
        if(!((ActiveAdmin)mAdminList.get(k)).info.getComponent().equals(componentname)) goto _L5; else goto _L4
_L4:
        i = k;
_L2:
        if(i != -1)
            break MISSING_BLOCK_LABEL_217;
        mAdminList.add(activeadmin);
_L7:
        saveSettingsLocked();
        sendAdminCommandLocked(activeadmin, "android.app.action.DEVICE_ADMIN_ENABLED");
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        return;
_L5:
        k++;
          goto _L6
        mAdminList.set(i, activeadmin);
          goto _L7
    }

    public void setActivePasswordState(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1) {
        mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        validateQualityConstant(i);
        this;
        JVM INSTR monitorenter ;
        long l2;
        if(mActivePasswordQuality == i && mActivePasswordLength == j && mFailedPasswordAttempts == 0 && mActivePasswordLetters == k && mActivePasswordUpperCase == l && mActivePasswordLowerCase == i1 && mActivePasswordNumeric == j1 && mActivePasswordSymbols == k1 && mActivePasswordNonLetter == l1)
            break MISSING_BLOCK_LABEL_177;
        l2 = Binder.clearCallingIdentity();
        mActivePasswordQuality = i;
        mActivePasswordLength = j;
        mActivePasswordLetters = k;
        mActivePasswordLowerCase = i1;
        mActivePasswordUpperCase = l;
        mActivePasswordNumeric = j1;
        mActivePasswordSymbols = k1;
        mActivePasswordNonLetter = l1;
        mFailedPasswordAttempts = 0;
        saveSettingsLocked();
        updatePasswordExpirationsLocked();
        setExpirationAlarmCheckLocked(mContext);
        sendAdminCommandLocked("android.app.action.ACTION_PASSWORD_CHANGED", 0);
        Binder.restoreCallingIdentity(l2);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l2);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public void setCameraDisabled(ComponentName componentname, boolean flag) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 8);
        if(activeadmin.disableCamera != flag) {
            activeadmin.disableCamera = flag;
            saveSettingsLocked();
        }
        syncDeviceCapabilitiesLocked();
        this;
        JVM INSTR monitorexit ;
    }

    protected void setExpirationAlarmCheckLocked(Context context) {
        long l5;
        long l = getPasswordExpirationLocked(null);
        long l1 = System.currentTimeMillis();
        long l2 = l - l1;
        long l4;
        AlarmManager alarmmanager;
        PendingIntent pendingintent;
        if(l == 0L)
            l4 = 0L;
        else
        if(l2 <= 0L) {
            l4 = l1 + 0x5265c00L;
        } else {
            long l3 = l2 % 0x5265c00L;
            if(l3 == 0L)
                l3 = 0x5265c00L;
            l4 = l1 + l3;
        }
        l5 = Binder.clearCallingIdentity();
        alarmmanager = (AlarmManager)context.getSystemService("alarm");
        pendingintent = PendingIntent.getBroadcast(context, 5571, new Intent("com.android.server.ACTION_EXPIRED_PASSWORD_NOTIFICATION"), 0x48000000);
        alarmmanager.cancel(pendingintent);
        if(l4 != 0L)
            alarmmanager.set(1, l4, pendingintent);
        Binder.restoreCallingIdentity(l5);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l5);
        throw exception;
    }

    public ComponentName setGlobalProxy(ComponentName componentname, String s, String s1) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin;
        ComponentName componentname1;
        activeadmin = getActiveAdminForCallerLocked(componentname, 5);
        Iterator iterator = mAdminMap.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_100;
            componentname1 = (ComponentName)iterator.next();
        } while(!((ActiveAdmin)mAdminMap.get(componentname1)).specifiesGlobalProxy || componentname1.equals(componentname));
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_165;
        long l;
        if(s == null) {
            activeadmin.specifiesGlobalProxy = false;
            activeadmin.globalProxySpec = null;
            activeadmin.globalProxyExclusionList = null;
        } else {
            activeadmin.specifiesGlobalProxy = true;
            activeadmin.globalProxySpec = s;
            activeadmin.globalProxyExclusionList = s1;
        }
        l = Binder.clearCallingIdentity();
        resetGlobalProxyLocked();
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        componentname1 = null;
        return componentname1;
    }

    public void setMaximumFailedPasswordsForWipe(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        getActiveAdminForCallerLocked(componentname, 4);
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 1);
        if(activeadmin.maximumFailedPasswordsForWipe != i) {
            activeadmin.maximumFailedPasswordsForWipe = i;
            saveSettingsLocked();
        }
        return;
    }

    public void setMaximumTimeToLock(ComponentName componentname, long l) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 3);
        if(activeadmin.maximumTimeToUnlock != l) {
            activeadmin.maximumTimeToUnlock = l;
            saveSettingsLocked();
            updateMaximumTimeToLockLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordExpirationTimeout(ComponentName componentname, long l) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        if(l >= 0L)
            break MISSING_BLOCK_LABEL_41;
        throw new IllegalArgumentException("Timeout must be >= 0 ms");
        ActiveAdmin activeadmin;
        long l1;
        activeadmin = getActiveAdminForCallerLocked(componentname, 6);
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_139;
        l1 = l + System.currentTimeMillis();
_L1:
        activeadmin.passwordExpirationDate = l1;
        activeadmin.passwordExpirationTimeout = l;
        if(l > 0L)
            Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("setPasswordExpiration(): password will expire on ").append(DateFormat.getDateTimeInstance(2, 2).format(new Date(l1))).toString());
        saveSettingsLocked();
        setExpirationAlarmCheckLocked(mContext);
        this;
        JVM INSTR monitorexit ;
        return;
        l1 = 0L;
          goto _L1
    }

    public void setPasswordHistoryLength(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.passwordHistoryLength != i) {
            activeadmin.passwordHistoryLength = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumLength(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordLength != i) {
            activeadmin.minimumPasswordLength = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumLetters(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordLetters != i) {
            activeadmin.minimumPasswordLetters = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumLowerCase(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordLowerCase != i) {
            activeadmin.minimumPasswordLowerCase = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumNonLetter(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordNonLetter != i) {
            activeadmin.minimumPasswordNonLetter = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumNumeric(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordNumeric != i) {
            activeadmin.minimumPasswordNumeric = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumSymbols(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordSymbols != i) {
            activeadmin.minimumPasswordSymbols = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordMinimumUpperCase(ComponentName componentname, int i) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.minimumPasswordUpperCase != i) {
            activeadmin.minimumPasswordUpperCase = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public void setPasswordQuality(ComponentName componentname, int i) {
        validateQualityConstant(i);
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_28;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin = getActiveAdminForCallerLocked(componentname, 0);
        if(activeadmin.passwordQuality != i) {
            activeadmin.passwordQuality = i;
            saveSettingsLocked();
        }
        this;
        JVM INSTR monitorexit ;
    }

    public int setStorageEncryption(ComponentName componentname, boolean flag) {
        this;
        JVM INSTR monitorenter ;
        if(componentname == null)
            throw new NullPointerException("ComponentName is null");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        throw exception;
        ActiveAdmin activeadmin;
        byte byte0;
        activeadmin = getActiveAdminForCallerLocked(componentname, 7);
        if(isEncryptionSupported())
            break MISSING_BLOCK_LABEL_47;
        byte0 = 0;
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_137;
        if(activeadmin.encryptionRequested != flag) {
            activeadmin.encryptionRequested = flag;
            saveSettingsLocked();
        }
        boolean flag1 = false;
        int i = mAdminList.size();
        for(int j = 0; j < i; j++)
            flag1 |= ((ActiveAdmin)mAdminList.get(j)).encryptionRequested;

        setEncryptionRequested(flag1);
        if(!flag1)
            break MISSING_BLOCK_LABEL_131;
        byte0 = 3;
_L1:
        this;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_137;
        byte0 = 1;
          goto _L1
        return byte0;
    }

    void syncDeviceCapabilitiesLocked() {
        boolean flag;
        boolean flag1;
        flag = SystemProperties.getBoolean("sys.secpolicy.camera.disabled", false);
        flag1 = getCameraDisabled(null);
        if(flag1 == flag) goto _L2; else goto _L1
_L1:
        long l;
        l = Binder.clearCallingIdentity();
        if(!flag1)
            break MISSING_BLOCK_LABEL_70;
        String s = "1";
_L3:
        Slog.v("DevicePolicyManagerService", (new StringBuilder()).append("Change in camera state [sys.secpolicy.camera.disabled] = ").append(s).toString());
        SystemProperties.set("sys.secpolicy.camera.disabled", s);
        Binder.restoreCallingIdentity(l);
_L2:
        return;
        s = "0";
          goto _L3
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void systemReady() {
        this;
        JVM INSTR monitorenter ;
        loadSettingsLocked();
        return;
    }

    void updateMaximumTimeToLockLocked() {
        long l = getMaximumTimeToLock(null);
        if(mLastMaximumTimeToLock != l) goto _L2; else goto _L1
_L1:
        return;
_L2:
        long l1 = Binder.clearCallingIdentity();
        if(l > 0L) goto _L4; else goto _L3
_L3:
        l = 0x7fffffffL;
_L5:
        mLastMaximumTimeToLock = l;
        getIPowerManager().setMaximumScreenOffTimeount((int)l);
_L6:
        Binder.restoreCallingIdentity(l1);
          goto _L1
_L4:
        android.provider.Settings.System.putInt(mContext.getContentResolver(), "stay_on_while_plugged_in", 0);
          goto _L5
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l1);
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("DevicePolicyManagerService", "Failure talking with power manager", remoteexception);
          goto _L6
    }

    void validatePasswordOwnerLocked() {
        if(mPasswordOwner < 0) goto _L2; else goto _L1
_L1:
        boolean flag;
        int i;
        flag = false;
        i = -1 + mAdminList.size();
_L8:
        if(i < 0) goto _L4; else goto _L3
_L3:
        if(((ActiveAdmin)mAdminList.get(i)).getUid() != mPasswordOwner) goto _L6; else goto _L5
_L5:
        flag = true;
_L4:
        if(!flag) {
            Slog.w("DevicePolicyManagerService", (new StringBuilder()).append("Previous password owner ").append(mPasswordOwner).append(" no longer active; disabling").toString());
            mPasswordOwner = -1;
        }
_L2:
        return;
_L6:
        i--;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void wipeData(int i) {
        this;
        JVM INSTR monitorenter ;
        long l;
        getActiveAdminForCallerLocked(null, 4);
        l = Binder.clearCallingIdentity();
        wipeDataLocked(i);
        Binder.restoreCallingIdentity(l);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    void wipeDataLocked(int i) {
        boolean flag = true;
        boolean flag1;
        if(!Environment.isExternalStorageRemovable() && isExtStorageEncrypted())
            flag1 = flag;
        else
            flag1 = false;
        if((i & 1) == 0)
            flag = false;
        if((flag1 || flag) && !Environment.isExternalStorageEmulated()) {
            Intent intent = new Intent("com.android.internal.os.storage.FORMAT_AND_FACTORY_RESET");
            intent.setComponent(ExternalStorageFormatter.COMPONENT_NAME);
            mWakeLock.acquire(10000L);
            mContext.startService(intent);
        } else {
            try {
                RecoverySystem.rebootWipeUserData(mContext);
            }
            catch(IOException ioexception) {
                Slog.w("DevicePolicyManagerService", "Failed requesting data wipe", ioexception);
            }
        }
    }

    protected static final String ACTION_EXPIRED_PASSWORD_NOTIFICATION = "com.android.server.ACTION_EXPIRED_PASSWORD_NOTIFICATION";
    private static final long EXPIRATION_GRACE_PERIOD_MS = 0x19bfcc00L;
    private static final long MS_PER_DAY = 0x5265c00L;
    private static final int REQUEST_EXPIRE_PASSWORD = 5571;
    public static final String SYSTEM_PROP_DISABLE_CAMERA = "sys.secpolicy.camera.disabled";
    private static final String TAG = "DevicePolicyManagerService";
    int mActivePasswordLength;
    int mActivePasswordLetters;
    int mActivePasswordLowerCase;
    int mActivePasswordNonLetter;
    int mActivePasswordNumeric;
    int mActivePasswordQuality;
    int mActivePasswordSymbols;
    int mActivePasswordUpperCase;
    final ArrayList mAdminList = new ArrayList();
    final HashMap mAdminMap = new HashMap();
    final Context mContext;
    int mFailedPasswordAttempts;
    Handler mHandler;
    IPowerManager mIPowerManager;
    IWindowManager mIWindowManager;
    long mLastMaximumTimeToLock;
    final MyPackageMonitor mMonitor = new MyPackageMonitor();
    int mPasswordOwner;
    BroadcastReceiver mReceiver;
    final android.os.PowerManager.WakeLock mWakeLock;



}
