// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.usb;

import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.usb.UsbAccessory;
import android.os.*;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Pair;
import android.util.Slog;
import java.io.*;
import java.util.*;

// Referenced classes of package com.android.server.usb:
//            UsbSettingsManager

public class UsbDeviceManager {
    private final class UsbHandler extends Handler {

        private void setAdbEnabled(boolean flag) {
            if(flag != mAdbEnabled) {
                mAdbEnabled = flag;
                setEnabledFunctions(mDefaultFunctions, true);
                updateAdbNotification();
            }
        }

        private void setEnabledFunctions(String s, boolean flag) {
            if(s == null || !flag || needsOemUsbOverride()) goto _L2; else goto _L1
_L1:
            String s3;
            if(mAdbEnabled)
                s3 = UsbDeviceManager.addFunction(s, "adb");
            else
                s3 = UsbDeviceManager.removeFunction(s, "adb");
            if(!mDefaultFunctions.equals(s3))
                if(!setUsbConfig("none")) {
                    Slog.e(UsbDeviceManager.TAG, "Failed to disable USB");
                    setUsbConfig(mCurrentFunctions);
                } else {
                    SystemProperties.set("persist.sys.usb.config", s3);
                    if(waitForState(s3)) {
                        mCurrentFunctions = s3;
                        mDefaultFunctions = s3;
                    } else {
                        Slog.e(UsbDeviceManager.TAG, (new StringBuilder()).append("Failed to switch persistent USB config to ").append(s3).toString());
                        SystemProperties.set("persist.sys.usb.config", mDefaultFunctions);
                    }
                }
_L4:
            return;
_L2:
            if(s == null)
                s = mDefaultFunctions;
            String s1 = processOemUsbOverride(s);
            String s2;
            if(mAdbEnabled)
                s2 = UsbDeviceManager.addFunction(s1, "adb");
            else
                s2 = UsbDeviceManager.removeFunction(s1, "adb");
            if(!mCurrentFunctions.equals(s2))
                if(!setUsbConfig("none")) {
                    Slog.e(UsbDeviceManager.TAG, "Failed to disable USB");
                    setUsbConfig(mCurrentFunctions);
                } else
                if(setUsbConfig(s2)) {
                    mCurrentFunctions = s2;
                } else {
                    Slog.e(UsbDeviceManager.TAG, (new StringBuilder()).append("Failed to switch USB config to ").append(s2).toString());
                    setUsbConfig(mCurrentFunctions);
                }
            if(true) goto _L4; else goto _L3
_L3:
        }

        private boolean setUsbConfig(String s) {
            SystemProperties.set("sys.usb.config", s);
            return waitForState(s);
        }

        private void updateAdbNotification() {
            if(mNotificationManager != null) goto _L2; else goto _L1
_L1:
            return;
_L2:
            if(mAdbEnabled && mConnected) {
                if(!"0".equals(SystemProperties.get("persist.adb.notify")) && !mAdbNotificationShown) {
                    Resources resources = mContext.getResources();
                    CharSequence charsequence = resources.getText(0x1040444);
                    CharSequence charsequence1 = resources.getText(0x1040445);
                    Notification notification = new Notification();
                    notification.icon = 0x1080518;
                    notification.when = 0L;
                    notification.flags = 2;
                    notification.tickerText = charsequence;
                    notification.defaults = 0;
                    notification.sound = null;
                    notification.vibrate = null;
                    notification.priority = -1;
                    Intent intent = Intent.makeRestartActivityTask(new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings"));
                    PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
                    notification.setLatestEventInfo(mContext, charsequence, charsequence1, pendingintent);
                    mAdbNotificationShown = true;
                    mNotificationManager.notify(0x1040444, notification);
                }
            } else
            if(mAdbNotificationShown) {
                mAdbNotificationShown = false;
                mNotificationManager.cancel(0x1040444);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        private void updateAudioSourceFunction() {
            boolean flag = UsbDeviceManager.containsFunction(mCurrentFunctions, "audio_source");
            if(flag != mAudioSourceEnabled) {
                Intent intent = new Intent("android.intent.action.USB_AUDIO_ACCESSORY_PLUG");
                intent.addFlags(0x20000000);
                intent.addFlags(0x40000000);
                int i;
                if(flag)
                    i = 1;
                else
                    i = 0;
                intent.putExtra("state", i);
                if(flag)
                    try {
                        Scanner scanner = new Scanner(new File("/sys/class/android_usb/android0/f_audio_source/pcm"));
                        int j = scanner.nextInt();
                        int k = scanner.nextInt();
                        intent.putExtra("card", j);
                        intent.putExtra("device", k);
                    }
                    catch(FileNotFoundException filenotfoundexception) {
                        Slog.e(UsbDeviceManager.TAG, "could not open audio source PCM file", filenotfoundexception);
                    }
                mContext.sendStickyBroadcast(intent);
                mAudioSourceEnabled = flag;
            }
        }

        private void updateCurrentAccessory() {
            if(mHasUsbAccessory) goto _L2; else goto _L1
_L1:
            return;
_L2:
            if(mConfigured) {
                if(mAccessoryStrings != null) {
                    mCurrentAccessory = new UsbAccessory(mAccessoryStrings);
                    Slog.d(UsbDeviceManager.TAG, (new StringBuilder()).append("entering USB accessory mode: ").append(mCurrentAccessory).toString());
                    if(mBootCompleted)
                        mSettingsManager.accessoryAttached(mCurrentAccessory);
                } else {
                    Slog.e(UsbDeviceManager.TAG, "nativeGetAccessoryStrings failed");
                }
            } else
            if(!mConnected) {
                Slog.d(UsbDeviceManager.TAG, "exited USB accessory mode");
                setEnabledFunctions(mDefaultFunctions, false);
                if(mCurrentAccessory != null) {
                    if(mBootCompleted)
                        mSettingsManager.accessoryDetached(mCurrentAccessory);
                    mCurrentAccessory = null;
                    mAccessoryStrings = null;
                }
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        private void updateUsbNotification() {
            if(mNotificationManager != null && mUseUsbNotification) goto _L2; else goto _L1
_L1:
            return;
_L2:
            int i;
            Resources resources;
            i = 0;
            resources = mContext.getResources();
            if(mConnected) {
                if(!UsbDeviceManager.containsFunction(mCurrentFunctions, "mtp"))
                    break; /* Loop/switch isn't completed */
                i = 0x104043c;
            }
_L4:
            if(i != mUsbNotificationId) {
                if(mUsbNotificationId != 0) {
                    mNotificationManager.cancel(mUsbNotificationId);
                    mUsbNotificationId = 0;
                }
                if(i != 0) {
                    CharSequence charsequence = resources.getText(0x1040440);
                    CharSequence charsequence1 = resources.getText(i);
                    Notification notification = new Notification();
                    notification.icon = 0x1080536;
                    notification.when = 0L;
                    notification.flags = 2;
                    notification.tickerText = charsequence1;
                    notification.defaults = 0;
                    notification.sound = null;
                    notification.vibrate = null;
                    notification.priority = -2;
                    Intent intent = Intent.makeRestartActivityTask(new ComponentName("com.android.settings", "com.android.settings.UsbSettings"));
                    PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
                    notification.setLatestEventInfo(mContext, charsequence1, charsequence, pendingintent);
                    mNotificationManager.notify(i, notification);
                    mUsbNotificationId = i;
                }
            }
            if(true) goto _L1; else goto _L3
_L3:
            if(UsbDeviceManager.containsFunction(mCurrentFunctions, "ptp"))
                i = 0x104043d;
            else
            if(UsbDeviceManager.containsFunction(mCurrentFunctions, "mass_storage"))
                i = 0x104043e;
            else
            if(UsbDeviceManager.containsFunction(mCurrentFunctions, "accessory"))
                i = 0x104043f;
            else
            if(!UsbDeviceManager.containsFunction(mCurrentFunctions, "rndis"))
                Slog.e(UsbDeviceManager.TAG, "No known USB function in updateUsbNotification");
              goto _L4
            if(true) goto _L1; else goto _L5
_L5:
        }

        private void updateUsbState() {
            Intent intent = new Intent("android.hardware.usb.action.USB_STATE");
            intent.addFlags(0x20000000);
            intent.putExtra("connected", mConnected);
            intent.putExtra("configured", mConfigured);
            if(mCurrentFunctions != null) {
                String as[] = mCurrentFunctions.split(",");
                for(int i = 0; i < as.length; i++)
                    intent.putExtra(as[i], true);

            }
            mContext.sendStickyBroadcast(intent);
        }

        private boolean waitForState(String s) {
            int i = 0;
_L3:
            if(i >= 20)
                break MISSING_BLOCK_LABEL_39;
            if(!s.equals(SystemProperties.get("sys.usb.state"))) goto _L2; else goto _L1
_L1:
            boolean flag = true;
_L4:
            return flag;
_L2:
            SystemClock.sleep(50L);
            i++;
              goto _L3
            Slog.e(UsbDeviceManager.TAG, (new StringBuilder()).append("waitForState(").append(s).append(") FAILED").toString());
            flag = false;
              goto _L4
        }

        public void dump(FileDescriptor filedescriptor, PrintWriter printwriter) {
            printwriter.println("  USB Device State:");
            printwriter.println((new StringBuilder()).append("    Current Functions: ").append(mCurrentFunctions).toString());
            printwriter.println((new StringBuilder()).append("    Default Functions: ").append(mDefaultFunctions).toString());
            printwriter.println((new StringBuilder()).append("    mConnected: ").append(mConnected).toString());
            printwriter.println((new StringBuilder()).append("    mConfigured: ").append(mConfigured).toString());
            printwriter.println((new StringBuilder()).append("    mCurrentAccessory: ").append(mCurrentAccessory).toString());
            printwriter.println((new StringBuilder()).append("    Kernel state: ").append(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 0, null).trim()).toString());
            printwriter.println((new StringBuilder()).append("    Kernel function list: ").append(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/functions"), 0, null).trim()).toString());
            printwriter.println((new StringBuilder()).append("    Mass storage backing file: ").append(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/f_mass_storage/lun/file"), 0, null).trim()).toString());
_L1:
            return;
            IOException ioexception;
            ioexception;
            printwriter.println((new StringBuilder()).append("IOException: ").append(ioexception).toString());
              goto _L1
        }

        public UsbAccessory getCurrentAccessory() {
            return mCurrentAccessory;
        }

        public void handleMessage(Message message) {
            boolean flag = true;
            message.what;
            JVM INSTR tableswitch 0 4: default 40
        //                       0 41
        //                       1 144
        //                       2 165
        //                       3 202
        //                       4 221;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
            return;
_L2:
            boolean flag2;
            if(message.arg1 == flag)
                flag2 = flag;
            else
                flag2 = false;
            mConnected = flag2;
            if(message.arg2 != flag)
                flag = false;
            mConfigured = flag;
            updateUsbNotification();
            updateAdbNotification();
            if(UsbDeviceManager.containsFunction(mCurrentFunctions, "accessory"))
                updateCurrentAccessory();
            if(!mConnected)
                setEnabledFunctions(mDefaultFunctions, false);
            if(mBootCompleted) {
                updateUsbState();
                updateAudioSourceFunction();
            }
            continue; /* Loop/switch isn't completed */
_L3:
            if(message.arg1 != flag)
                flag = false;
            setAdbEnabled(flag);
            continue; /* Loop/switch isn't completed */
_L4:
            String s = (String)message.obj;
            boolean flag1;
            if(message.arg1 == flag)
                flag1 = flag;
            else
                flag1 = false;
            setEnabledFunctions(s, flag1);
            continue; /* Loop/switch isn't completed */
_L5:
            updateUsbNotification();
            updateAdbNotification();
            updateUsbState();
            updateAudioSourceFunction();
            continue; /* Loop/switch isn't completed */
_L6:
            mBootCompleted = flag;
            if(mCurrentAccessory != null)
                mSettingsManager.accessoryAttached(mCurrentAccessory);
            if(true) goto _L1; else goto _L7
_L7:
        }

        public void sendMessage(int i, Object obj) {
            removeMessages(i);
            Message message = Message.obtain(this, i);
            message.obj = obj;
            sendMessage(message);
        }

        public void sendMessage(int i, Object obj, boolean flag) {
            removeMessages(i);
            Message message = Message.obtain(this, i);
            message.obj = obj;
            int j;
            if(flag)
                j = 1;
            else
                j = 0;
            message.arg1 = j;
            sendMessage(message);
        }

        public void sendMessage(int i, boolean flag) {
            removeMessages(i);
            Message message = Message.obtain(this, i);
            int j;
            if(flag)
                j = 1;
            else
                j = 0;
            message.arg1 = j;
            sendMessage(message);
        }

        public void updateState(String s) {
            int i;
            int j;
            Message message;
            long l;
            if("DISCONNECTED".equals(s)) {
                i = 0;
                j = 0;
            } else
            if("CONNECTED".equals(s)) {
                i = 1;
                j = 0;
            } else {
label0:
                {
                    if(!"CONFIGURED".equals(s))
                        break label0;
                    i = 1;
                    j = 1;
                }
            }
            removeMessages(0);
            message = Message.obtain(this, 0);
            message.arg1 = i;
            message.arg2 = j;
            if(i == 0)
                l = 1000L;
            else
                l = 0L;
            sendMessageDelayed(message, l);
            return;
            Slog.e(UsbDeviceManager.TAG, (new StringBuilder()).append("unknown state ").append(s).toString());
            if(false)
                ;
            else
                break MISSING_BLOCK_LABEL_58;
        }

        private boolean mAdbNotificationShown;
        private final BroadcastReceiver mBootCompletedReceiver;
        private boolean mConfigured;
        private boolean mConnected;
        private UsbAccessory mCurrentAccessory;
        private String mCurrentFunctions;
        private String mDefaultFunctions;
        private int mUsbNotificationId;
        final UsbDeviceManager this$0;

        public UsbHandler(Looper looper) {
            this$0 = UsbDeviceManager.this;
            super(looper);
            mBootCompletedReceiver = new BroadcastReceiver() {

                public void onReceive(Context context, Intent intent) {
                    mHandler.sendEmptyMessage(4);
                }

                final UsbHandler this$1;

                 {
                    this$1 = UsbHandler.this;
                    super();
                }
            };
            String s;
            mDefaultFunctions = SystemProperties.get("persist.sys.usb.config", "adb");
            mDefaultFunctions = processOemUsbOverride(mDefaultFunctions);
            if(!SystemProperties.get("sys.usb.config", "none").equals(mDefaultFunctions)) {
                Slog.w(UsbDeviceManager.TAG, (new StringBuilder()).append("resetting config to persistent property: ").append(mDefaultFunctions).toString());
                SystemProperties.set("sys.usb.config", mDefaultFunctions);
            }
            mCurrentFunctions = mDefaultFunctions;
            updateState(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 0, null).trim());
            mAdbEnabled = UsbDeviceManager.containsFunction(mCurrentFunctions, "adb");
            s = SystemProperties.get("persist.service.adb.enable", "");
            if(s.length() <= 0) goto _L2; else goto _L1
_L1:
            char c = s.charAt(0);
            if(c != '1') goto _L4; else goto _L3
_L3:
            setAdbEnabled(true);
_L6:
            SystemProperties.set("persist.service.adb.enable", "");
_L2:
            mContentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("adb_enabled"), false, new AdbSettingsObserver());
            mUEventObserver.startObserving("DEVPATH=/devices/virtual/android_usb/android0");
            mUEventObserver.startObserving("DEVPATH=/devices/virtual/misc/usb_accessory");
            mContext.registerReceiver(mBootCompletedReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
            break; /* Loop/switch isn't completed */
_L4:
            if(c == '0')
                setAdbEnabled(false);
            if(true) goto _L6; else goto _L5
            Exception exception;
            exception;
            Slog.e(UsbDeviceManager.TAG, "Error initializing UsbHandler", exception);
_L5:
        }
    }

    private class AdbSettingsObserver extends ContentObserver {

        public void onChange(boolean flag) {
            boolean flag1 = false;
            if(android.provider.Settings.Secure.getInt(mContentResolver, "adb_enabled", 0) > 0)
                flag1 = true;
            mHandler.sendMessage(1, flag1);
        }

        final UsbDeviceManager this$0;

        public AdbSettingsObserver() {
            this$0 = UsbDeviceManager.this;
            super(null);
        }
    }


    public UsbDeviceManager(Context context, UsbSettingsManager usbsettingsmanager) {
        mContext = context;
        mContentResolver = context.getContentResolver();
        mSettingsManager = usbsettingsmanager;
        mHasUsbAccessory = mContext.getPackageManager().hasSystemFeature("android.hardware.usb.accessory");
        initRndisAddress();
        readOemUsbOverrideConfig();
        HandlerThread handlerthread = new HandlerThread("UsbDeviceManager", 10);
        handlerthread.start();
        mHandler = new UsbHandler(handlerthread.getLooper());
        if(nativeIsStartRequested())
            startAccessoryMode();
    }

    private static String addFunction(String s, String s1) {
        if(!"none".equals(s)) {
            if(!containsFunction(s, s1)) {
                if(s.length() > 0)
                    s = (new StringBuilder()).append(s).append(",").toString();
                s = (new StringBuilder()).append(s).append(s1).toString();
            }
            s1 = s;
        }
        return s1;
    }

    private static boolean containsFunction(String s, String s1) {
        boolean flag;
        int i;
        flag = false;
        i = s.indexOf(s1);
        break MISSING_BLOCK_LABEL_8;
        while(true)  {
            do
                return flag;
            while(i < 0 || i > 0 && s.charAt(i - 1) != ',');
            int j = i + s1.length();
            if(j >= s.length() || s.charAt(j) == ',')
                flag = true;
        }
    }

    private static void initRndisAddress() {
        String s1;
        int ai[] = new int[6];
        ai[0] = 2;
        String s = SystemProperties.get("ro.serialno", "1234567890ABCDEF");
        int i = s.length();
        for(int j = 0; j < i; j++) {
            int k = 1 + j % 5;
            ai[k] = ai[k] ^ s.charAt(j);
        }

        Object aobj[] = new Object[6];
        aobj[0] = Integer.valueOf(ai[0]);
        aobj[1] = Integer.valueOf(ai[1]);
        aobj[2] = Integer.valueOf(ai[2]);
        aobj[3] = Integer.valueOf(ai[3]);
        aobj[4] = Integer.valueOf(ai[4]);
        aobj[5] = Integer.valueOf(ai[5]);
        s1 = String.format("%02X:%02X:%02X:%02X:%02X:%02X", aobj);
        FileUtils.stringToFile("/sys/class/android_usb/android0/f_rndis/ethaddr", s1);
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.e(TAG, "failed to write to /sys/class/android_usb/android0/f_rndis/ethaddr");
          goto _L1
    }

    private native String[] nativeGetAccessoryStrings();

    private native int nativeGetAudioMode();

    private native boolean nativeIsStartRequested();

    private native ParcelFileDescriptor nativeOpenAccessory();

    private boolean needsOemUsbOverride() {
        boolean flag = false;
        if(mOemModeMap != null) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        String s = SystemProperties.get("ro.bootmode", "unknown");
        if(mOemModeMap.get(s) != null)
            flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private String processOemUsbOverride(String s) {
        if(s != null && mOemModeMap != null) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        String s1 = SystemProperties.get("ro.bootmode", "unknown");
        List list = (List)mOemModeMap.get(s1);
        if(list == null)
            continue; /* Loop/switch isn't completed */
        Iterator iterator = list.iterator();
        Pair pair;
        do {
            if(!iterator.hasNext())
                continue; /* Loop/switch isn't completed */
            pair = (Pair)iterator.next();
        } while(!((String)pair.first).equals(s));
        Slog.d(TAG, (new StringBuilder()).append("OEM USB override: ").append((String)pair.first).append(" ==> ").append((String)pair.second).toString());
        s = (String)pair.second;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void readOemUsbOverrideConfig() {
        String as[] = mContext.getResources().getStringArray(0x107002e);
        if(as != null) {
            int i = as.length;
            for(int j = 0; j < i; j++) {
                String as1[] = as[j].split(":");
                if(as1.length != 3)
                    continue;
                if(mOemModeMap == null)
                    mOemModeMap = new HashMap();
                Object obj = (List)mOemModeMap.get(as1[0]);
                if(obj == null) {
                    obj = new LinkedList();
                    mOemModeMap.put(as1[0], obj);
                }
                ((List) (obj)).add(new Pair(as1[1], as1[2]));
            }

        }
    }

    private static String removeFunction(String s, String s1) {
        String as[] = s.split(",");
        for(int i = 0; i < as.length; i++)
            if(s1.equals(as[i]))
                as[i] = null;

        String s2;
        if(as.length == 1 && as[0] == null) {
            s2 = "none";
        } else {
            StringBuilder stringbuilder = new StringBuilder();
            for(int j = 0; j < as.length; j++) {
                String s3 = as[j];
                if(s3 == null)
                    continue;
                if(stringbuilder.length() > 0)
                    stringbuilder.append(",");
                stringbuilder.append(s3);
            }

            s2 = stringbuilder.toString();
        }
        return s2;
    }

    private void startAccessoryMode() {
        boolean flag;
        boolean flag1;
        String s;
        mAccessoryStrings = nativeGetAccessoryStrings();
        if(nativeGetAudioMode() == 1)
            flag = true;
        else
            flag = false;
        if(mAccessoryStrings != null && mAccessoryStrings[0] != null && mAccessoryStrings[1] != null)
            flag1 = true;
        else
            flag1 = false;
        s = null;
        if(!flag1 || !flag) goto _L2; else goto _L1
_L1:
        s = "accessory,audio_source";
_L4:
        if(s != null)
            setCurrentFunctions(s, false);
        return;
_L2:
        if(flag1)
            s = "accessory";
        else
        if(flag)
            s = "audio_source";
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter) {
        if(mHandler != null)
            mHandler.dump(filedescriptor, printwriter);
    }

    public UsbAccessory getCurrentAccessory() {
        return mHandler.getCurrentAccessory();
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory usbaccessory) {
        UsbAccessory usbaccessory1 = mHandler.getCurrentAccessory();
        if(usbaccessory1 == null)
            throw new IllegalArgumentException("no accessory attached");
        if(!usbaccessory1.equals(usbaccessory)) {
            throw new IllegalArgumentException((new StringBuilder()).append(usbaccessory.toString()).append(" does not match current accessory ").append(usbaccessory1).toString());
        } else {
            mSettingsManager.checkPermission(usbaccessory);
            return nativeOpenAccessory();
        }
    }

    public void setCurrentFunctions(String s, boolean flag) {
        mHandler.sendMessage(2, s, flag);
    }

    public void setMassStorageBackingFile(String s) {
        if(s == null)
            s = "";
        FileUtils.stringToFile("/sys/class/android_usb/android0/f_mass_storage/lun/file", s);
_L1:
        return;
        IOException ioexception;
        ioexception;
        Slog.e(TAG, "failed to write to /sys/class/android_usb/android0/f_mass_storage/lun/file");
          goto _L1
    }

    public void systemReady() {
        int i = 1;
        mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
        boolean flag = false;
        StorageVolume astoragevolume[] = ((StorageManager)mContext.getSystemService("storage")).getVolumeList();
        if(astoragevolume.length > 0)
            flag = astoragevolume[0].allowMassStorage();
        boolean flag1;
        ContentResolver contentresolver;
        if(!flag)
            flag1 = i;
        else
            flag1 = false;
        mUseUsbNotification = flag1;
        contentresolver = mContentResolver;
        if(!mAdbEnabled)
            i = 0;
        android.provider.Settings.Secure.putInt(contentresolver, "adb_enabled", i);
        mHandler.sendEmptyMessage(3);
    }

    private static final String ACCESSORY_START_MATCH = "DEVPATH=/devices/virtual/misc/usb_accessory";
    private static final int AUDIO_MODE_NONE = 0;
    private static final int AUDIO_MODE_SOURCE = 1;
    private static final String AUDIO_SOURCE_PCM_PATH = "/sys/class/android_usb/android0/f_audio_source/pcm";
    private static final String BOOT_MODE_PROPERTY = "ro.bootmode";
    private static final boolean DEBUG = false;
    private static final String FUNCTIONS_PATH = "/sys/class/android_usb/android0/functions";
    private static final String MASS_STORAGE_FILE_PATH = "/sys/class/android_usb/android0/f_mass_storage/lun/file";
    private static final int MSG_BOOT_COMPLETED = 4;
    private static final int MSG_ENABLE_ADB = 1;
    private static final int MSG_SET_CURRENT_FUNCTIONS = 2;
    private static final int MSG_SYSTEM_READY = 3;
    private static final int MSG_UPDATE_STATE = 0;
    private static final String RNDIS_ETH_ADDR_PATH = "/sys/class/android_usb/android0/f_rndis/ethaddr";
    private static final String STATE_PATH = "/sys/class/android_usb/android0/state";
    private static final String TAG = com/android/server/usb/UsbDeviceManager.getSimpleName();
    private static final int UPDATE_DELAY = 1000;
    private static final String USB_STATE_MATCH = "DEVPATH=/devices/virtual/android_usb/android0";
    private String mAccessoryStrings[];
    private boolean mAdbEnabled;
    private boolean mAudioSourceEnabled;
    private boolean mBootCompleted;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private UsbHandler mHandler;
    private final boolean mHasUsbAccessory;
    private NotificationManager mNotificationManager;
    private Map mOemModeMap;
    private final UsbSettingsManager mSettingsManager;
    private final UEventObserver mUEventObserver = new UEventObserver() {

        public void onUEvent(android.os.UEventObserver.UEvent uevent) {
            String s;
            String s1;
            s = uevent.get("USB_STATE");
            s1 = uevent.get("ACCESSORY");
            if(s == null) goto _L2; else goto _L1
_L1:
            mHandler.updateState(s);
_L4:
            return;
_L2:
            if("START".equals(s1))
                startAccessoryMode();
            if(true) goto _L4; else goto _L3
_L3:
        }

        final UsbDeviceManager this$0;

             {
                this$0 = UsbDeviceManager.this;
                super();
            }
    };
    private boolean mUseUsbNotification;









/*
    static String[] access$1302(UsbDeviceManager usbdevicemanager, String as[]) {
        usbdevicemanager.mAccessoryStrings = as;
        return as;
    }

*/



/*
    static boolean access$1402(UsbDeviceManager usbdevicemanager, boolean flag) {
        usbdevicemanager.mBootCompleted = flag;
        return flag;
    }

*/




/*
    static boolean access$1602(UsbDeviceManager usbdevicemanager, boolean flag) {
        usbdevicemanager.mAudioSourceEnabled = flag;
        return flag;
    }

*/








/*
    static boolean access$502(UsbDeviceManager usbdevicemanager, boolean flag) {
        usbdevicemanager.mAdbEnabled = flag;
        return flag;
    }

*/




}
