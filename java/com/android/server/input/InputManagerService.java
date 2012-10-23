// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.input;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.database.ContentObserver;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.KeyboardLayout;
import android.os.*;
import android.server.BluetoothService;
import android.util.*;
import android.view.*;
import android.widget.Toast;
import com.android.internal.util.XmlUtils;
import com.android.server.Watchdog;
import java.io.*;
import java.util.*;
import libcore.io.Streams;
import libcore.util.Objects;
import org.xmlpull.v1.XmlPullParser;

// Referenced classes of package com.android.server.input:
//            PersistentDataStore, InputFilter, InputWindowHandle, InputApplicationHandle

public class InputManagerService extends android.hardware.input.IInputManager.Stub
    implements com.android.server.Watchdog.Monitor {
    private final class VibratorToken
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            onVibratorTokenDied(this);
        }

        public final int mDeviceId;
        public final IBinder mToken;
        public final int mTokenValue;
        public boolean mVibrating;
        final InputManagerService this$0;

        public VibratorToken(int i, IBinder ibinder, int j) {
            this$0 = InputManagerService.this;
            super();
            mDeviceId = i;
            mToken = ibinder;
            mTokenValue = j;
        }
    }

    private final class InputDevicesChangedListenerRecord
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            onInputDevicesChangedListenerDied(mPid);
        }

        public void notifyInputDevicesChanged(int ai[]) {
            mListener.onInputDevicesChanged(ai);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("InputManager", (new StringBuilder()).append("Failed to notify process ").append(mPid).append(" that input devices changed, assuming it died.").toString(), remoteexception);
            binderDied();
              goto _L1
        }

        private final IInputDevicesChangedListener mListener;
        private final int mPid;
        final InputManagerService this$0;

        public InputDevicesChangedListenerRecord(int i, IInputDevicesChangedListener iinputdeviceschangedlistener) {
            this$0 = InputManagerService.this;
            super();
            mPid = i;
            mListener = iinputdeviceschangedlistener;
        }
    }

    private static interface KeyboardLayoutVisitor {

        public abstract void visitKeyboardLayout(Resources resources, String s, String s1, String s2, int i);
    }

    private static final class KeyboardLayoutDescriptor {

        public static String format(String s, String s1, String s2) {
            return (new StringBuilder()).append(s).append("/").append(s1).append("/").append(s2).toString();
        }

        public static KeyboardLayoutDescriptor parse(String s) {
            KeyboardLayoutDescriptor keyboardlayoutdescriptor;
            int i;
            keyboardlayoutdescriptor = null;
            i = s.indexOf('/');
            if(i >= 0 && i + 1 != s.length()) goto _L2; else goto _L1
_L1:
            return keyboardlayoutdescriptor;
_L2:
            int j = s.indexOf('/', i + 1);
            if(j >= i + 2 && j + 1 != s.length()) {
                keyboardlayoutdescriptor = new KeyboardLayoutDescriptor();
                keyboardlayoutdescriptor.packageName = s.substring(0, i);
                keyboardlayoutdescriptor.receiverName = s.substring(i + 1, j);
                keyboardlayoutdescriptor.keyboardLayoutName = s.substring(j + 1);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public String keyboardLayoutName;
        public String packageName;
        public String receiverName;

        private KeyboardLayoutDescriptor() {
        }
    }

    private final class InputFilterHost
        implements InputFilter.Host {

        public void disconnectLocked() {
            mDisconnected = true;
        }

        public void sendInputEvent(InputEvent inputevent, int i) {
            if(inputevent == null)
                throw new IllegalArgumentException("event must not be null");
            Object obj = mInputFilterLock;
            obj;
            JVM INSTR monitorenter ;
            if(!mDisconnected)
                InputManagerService.nativeInjectInputEvent(mPtr, inputevent, 0, 0, 0, 0, i | 0x4000000);
            return;
        }

        private boolean mDisconnected;
        final InputManagerService this$0;

        private InputFilterHost() {
            this$0 = InputManagerService.this;
            super();
        }

    }

    private final class InputManagerHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 5: default 40
        //                       1 41
        //                       2 61
        //                       3 79
        //                       4 89
        //                       5 99;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
            return;
_L2:
            deliverInputDevicesChanged((InputDevice[])(InputDevice[])message.obj);
            continue; /* Loop/switch isn't completed */
_L3:
            handleSwitchKeyboardLayout(message.arg1, message.arg2);
            continue; /* Loop/switch isn't completed */
_L4:
            reloadKeyboardLayouts();
            continue; /* Loop/switch isn't completed */
_L5:
            updateKeyboardLayouts();
            continue; /* Loop/switch isn't completed */
_L6:
            reloadDeviceAliases();
            if(true) goto _L1; else goto _L7
_L7:
        }

        final InputManagerService this$0;

        private InputManagerHandler() {
            this$0 = InputManagerService.this;
            super();
        }

    }

    public static interface Callbacks {

        public abstract KeyEvent dispatchUnhandledKey(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i);

        public abstract int getPointerLayer();

        public abstract long interceptKeyBeforeDispatching(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i);

        public abstract int interceptKeyBeforeQueueing(KeyEvent keyevent, int i, boolean flag);

        public abstract int interceptMotionBeforeQueueingWhenScreenOff(int i);

        public abstract long notifyANR(InputApplicationHandle inputapplicationhandle, InputWindowHandle inputwindowhandle);

        public abstract void notifyConfigurationChanged();

        public abstract void notifyInputChannelBroken(InputWindowHandle inputwindowhandle);

        public abstract void notifyLidSwitchChanged(long l, boolean flag);
    }


    public InputManagerService(Context context, Callbacks callbacks) {
        mInputDevicesLock = new Object();
        mInputDevices = new InputDevice[0];
        mVibratorLock = new Object();
        mVibratorTokens = new HashMap();
        mContext = context;
        mCallbacks = callbacks;
        Slog.i("InputManager", "Initializing input manager");
        mPtr = nativeInit(this, mContext, mHandler.getLooper().getQueue());
    }

    private void cancelVibrateIfNeeded(VibratorToken vibratortoken) {
        vibratortoken;
        JVM INSTR monitorenter ;
        if(vibratortoken.mVibrating) {
            nativeCancelVibrate(mPtr, vibratortoken.mDeviceId, vibratortoken.mTokenValue);
            vibratortoken.mVibrating = false;
        }
        return;
    }

    private boolean checkCallingPermission(String s, String s1) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_2;
        if(Binder.getCallingPid() != Process.myPid() && mContext.checkCallingPermission(s) != 0) {
            Slog.w("InputManager", (new StringBuilder()).append("Permission Denial: ").append(s1).append(" from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" requires ").append(s).toString());
            flag = false;
        }
        return flag;
    }

    private boolean checkInjectEventsPermission(int i, int j) {
        boolean flag;
        if(mContext.checkPermission("android.permission.INJECT_EVENTS", i, j) == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean containsInputDeviceWithDescriptor(InputDevice ainputdevice[], String s) {
        int i;
        int j;
        i = ainputdevice.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_35;
        if(!ainputdevice[j].getDescriptor().equals(s)) goto _L2; else goto _L1
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

    private void deliverInputDevicesChanged(InputDevice ainputdevice[]) {
        mTempInputDevicesChangedListenersToNotify.clear();
        mTempFullKeyboards.clear();
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        if(mInputDevicesChangedPending) goto _L2; else goto _L1
_L2:
        int i;
        int k;
        int ai[];
        mInputDevicesChangedPending = false;
        i = mInputDevicesChangedListeners.size();
        for(int j = 0; j < i; j++)
            mTempInputDevicesChangedListenersToNotify.add(mInputDevicesChangedListeners.valueAt(j));

        k = mInputDevices.length;
        ai = new int[k * 2];
        int l;
        int i1;
        l = 0;
        i1 = 0;
_L7:
        if(l >= k) goto _L4; else goto _L3
_L3:
        InputDevice inputdevice1;
        inputdevice1 = mInputDevices[l];
        ai[l * 2] = inputdevice1.getId();
        ai[1 + l * 2] = inputdevice1.getGeneration();
        if(inputdevice1.isVirtual() || !inputdevice1.isFullKeyboard())
            break MISSING_BLOCK_LABEL_408;
        if(containsInputDeviceWithDescriptor(ainputdevice, inputdevice1.getDescriptor())) goto _L6; else goto _L5
_L5:
        ArrayList arraylist = mTempFullKeyboards;
        int i2 = i1 + 1;
        arraylist.add(i1, inputdevice1);
_L15:
        l++;
        i1 = i2;
          goto _L7
_L6:
        mTempFullKeyboards.add(inputdevice1);
        break MISSING_BLOCK_LABEL_408;
_L4:
        Exception exception;
        for(int j1 = 0; j1 < i; j1++)
            ((InputDevicesChangedListenerRecord)mTempInputDevicesChangedListenersToNotify.get(j1)).notifyInputDevicesChanged(ai);

        mTempInputDevicesChangedListenersToNotify.clear();
          goto _L8
        exception;
_L14:
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L8:
        if(mNotificationManager == null) goto _L10; else goto _L9
_L9:
        int k1;
        boolean flag;
        boolean flag1;
        k1 = mTempFullKeyboards.size();
        flag = false;
        flag1 = false;
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        Exception exception1;
        InputDevice inputdevice;
        for(int l1 = 0; l1 >= k1; l1++)
            break MISSING_BLOCK_LABEL_350;

        inputdevice = (InputDevice)mTempFullKeyboards.get(l1);
        if(mDataStore.getCurrentKeyboardLayout(inputdevice.getDescriptor()) == null) {
            flag = true;
            if(l1 < i1)
                flag1 = true;
        }
        break MISSING_BLOCK_LABEL_415;
        if(!flag) goto _L12; else goto _L11
_L11:
        if(flag1)
            showMissingKeyboardLayoutNotification();
_L10:
        mTempFullKeyboards.clear();
        i1;
        break; /* Loop/switch isn't completed */
        exception1;
        persistentdatastore;
        JVM INSTR monitorexit ;
        throw exception1;
_L12:
        if(mKeyboardLayoutNotificationShown)
            hideMissingKeyboardLayoutNotification();
        if(true) goto _L10; else goto _L13
_L13:
        break; /* Loop/switch isn't completed */
        exception;
        i1;
        if(true) goto _L14; else goto _L1
_L1:
        return;
        i2 = i1;
          goto _L15
    }

    private KeyEvent dispatchUnhandledKey(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i) {
        return mCallbacks.dispatchUnhandledKey(inputwindowhandle, keyevent, i);
    }

    private String getDeviceAlias(String s) {
        String s1;
        if(mBluetoothService != null && BluetoothAdapter.checkBluetoothAddress(s))
            s1 = mBluetoothService.getRemoteAlias(s);
        else
            s1 = null;
        return s1;
    }

    private int getDoubleTapTimeout() {
        return ViewConfiguration.getDoubleTapTimeout();
    }

    private String[] getExcludedDeviceNames() {
        ArrayList arraylist;
        File file;
        FileReader filereader;
        arraylist = new ArrayList();
        file = new File(Environment.getRootDirectory(), "etc/excluded-input-devices.xml");
        filereader = null;
        FileReader filereader1 = new FileReader(file);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(filereader1);
        XmlUtils.beginDocument(xmlpullparser, "devices");
_L3:
        boolean flag;
        XmlUtils.nextElement(xmlpullparser);
        flag = "device".equals(xmlpullparser.getName());
        if(flag) goto _L2; else goto _L1
_L1:
        FileNotFoundException filenotfoundexception;
        if(filereader1 != null)
            try {
                filereader1.close();
            }
            catch(IOException ioexception3) { }
_L4:
        return (String[])arraylist.toArray(new String[arraylist.size()]);
_L2:
        String s = xmlpullparser.getAttributeValue(null, "name");
        if(s != null)
            arraylist.add(s);
          goto _L3
        filenotfoundexception;
        filereader = filereader1;
_L8:
        if(filereader != null)
            try {
                filereader.close();
            }
            catch(IOException ioexception2) { }
        break MISSING_BLOCK_LABEL_90;
        Exception exception;
        exception;
_L7:
        Slog.e("InputManager", (new StringBuilder()).append("Exception while parsing '").append(file.getAbsolutePath()).append("'").toString(), exception);
        if(filereader != null)
            try {
                filereader.close();
            }
            catch(IOException ioexception1) { }
          goto _L4
        Exception exception1;
        exception1;
_L6:
        if(filereader != null)
            try {
                filereader.close();
            }
            catch(IOException ioexception) { }
        throw exception1;
        exception1;
        filereader = filereader1;
        if(true) goto _L6; else goto _L5
_L5:
        exception;
        filereader = filereader1;
          goto _L7
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
          goto _L8
    }

    private int getHoverTapSlop() {
        return ViewConfiguration.getHoverTapSlop();
    }

    private int getHoverTapTimeout() {
        return ViewConfiguration.getHoverTapTimeout();
    }

    private int getKeyRepeatDelay() {
        return ViewConfiguration.getKeyRepeatDelay();
    }

    private int getKeyRepeatTimeout() {
        return ViewConfiguration.getKeyRepeatTimeout();
    }

    private String[] getKeyboardLayoutOverlay(String s) {
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        final String result[] = null;
_L4:
        return result;
_L2:
        String s1 = getCurrentKeyboardLayoutForInputDevice(s);
        if(s1 == null) {
            result = null;
        } else {
            result = new String[2];
            visitKeyboardLayout(s1, new KeyboardLayoutVisitor() {

                public void visitKeyboardLayout(Resources resources, String s2, String s3, String s4, int i) {
                    result[0] = s2;
                    result[1] = Streams.readFully(new InputStreamReader(resources.openRawResource(i)));
_L2:
                    return;
                    android.content.res.Resources.NotFoundException notfoundexception;
                    notfoundexception;
                    continue; /* Loop/switch isn't completed */
                    IOException ioexception;
                    ioexception;
                    if(true) goto _L2; else goto _L1
_L1:
                }

                final InputManagerService this$0;
                final String val$result[];

             {
                this$0 = InputManagerService.this;
                result = as;
                super();
            }
            });
            if(result[0] == null) {
                Log.w("InputManager", (new StringBuilder()).append("Could not get keyboard layout with descriptor '").append(s1).append("'.").toString());
                result = null;
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private int getLongPressTimeout() {
        return ViewConfiguration.getLongPressTimeout();
    }

    private PointerIcon getPointerIcon() {
        return PointerIcon.getDefaultIcon(mContext);
    }

    private int getPointerLayer() {
        return mCallbacks.getPointerLayer();
    }

    private int getPointerSpeedSetting() {
        int i = 0;
        int j = android.provider.Settings.System.getInt(mContext.getContentResolver(), "pointer_speed");
        i = j;
_L2:
        return i;
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        settingnotfoundexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private int getShowTouchesSetting(int i) {
        int j = i;
        int k = android.provider.Settings.System.getInt(mContext.getContentResolver(), "show_touches");
        j = k;
_L2:
        return j;
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        settingnotfoundexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private int getVirtualKeyQuietTimeMillis() {
        return mContext.getResources().getInteger(0x10e0024);
    }

    private void handleSwitchKeyboardLayout(int i, int j) {
        String s;
        InputDevice inputdevice = getInputDevice(i);
        s = inputdevice.getDescriptor();
        if(inputdevice == null)
            break MISSING_BLOCK_LABEL_128;
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        boolean flag;
        String s1;
        flag = mDataStore.switchKeyboardLayout(s, j);
        s1 = mDataStore.getCurrentKeyboardLayout(s);
        mDataStore.saveIfNeeded();
        persistentdatastore;
        JVM INSTR monitorexit ;
        if(flag) {
            if(mSwitchedKeyboardLayoutToast != null) {
                mSwitchedKeyboardLayoutToast.cancel();
                mSwitchedKeyboardLayoutToast = null;
            }
            if(s1 != null) {
                KeyboardLayout keyboardlayout = getKeyboardLayout(s1);
                if(keyboardlayout != null) {
                    mSwitchedKeyboardLayoutToast = Toast.makeText(mContext, keyboardlayout.getLabel(), 0);
                    mSwitchedKeyboardLayoutToast.show();
                }
            }
            reloadKeyboardLayouts();
        }
        return;
        Exception exception;
        exception;
        mDataStore.saveIfNeeded();
        throw exception;
        Exception exception1;
        exception1;
        throw exception1;
    }

    private void hideMissingKeyboardLayoutNotification() {
        if(mKeyboardLayoutNotificationShown) {
            mKeyboardLayoutNotificationShown = false;
            mNotificationManager.cancel(0x104044b);
        }
    }

    private long interceptKeyBeforeDispatching(InputWindowHandle inputwindowhandle, KeyEvent keyevent, int i) {
        return mCallbacks.interceptKeyBeforeDispatching(inputwindowhandle, keyevent, i);
    }

    private int interceptKeyBeforeQueueing(KeyEvent keyevent, int i, boolean flag) {
        return mCallbacks.interceptKeyBeforeQueueing(keyevent, i, flag);
    }

    private int interceptMotionBeforeQueueingWhenScreenOff(int i) {
        return mCallbacks.interceptMotionBeforeQueueingWhenScreenOff(i);
    }

    private static native void nativeCancelVibrate(int i, int j, int k);

    private static native String nativeDump(int i);

    private static native int nativeGetKeyCodeState(int i, int j, int k, int l);

    private static native int nativeGetScanCodeState(int i, int j, int k, int l);

    private static native int nativeGetSwitchState(int i, int j, int k, int l);

    private static native boolean nativeHasKeys(int i, int j, int k, int ai[], boolean aflag[]);

    private static native int nativeInit(InputManagerService inputmanagerservice, Context context, MessageQueue messagequeue);

    private static native int nativeInjectInputEvent(int i, InputEvent inputevent, int j, int k, int l, int i1, int j1);

    private static native void nativeMonitor(int i);

    private static native void nativeRegisterInputChannel(int i, InputChannel inputchannel, InputWindowHandle inputwindowhandle, boolean flag);

    private static native void nativeReloadDeviceAliases(int i);

    private static native void nativeReloadKeyboardLayouts(int i);

    private static native void nativeSetDisplayOrientation(int i, int j, int k, int l);

    private static native void nativeSetDisplaySize(int i, int j, int k, int l, int i1, int j1);

    private static native void nativeSetFocusedApplication(int i, InputApplicationHandle inputapplicationhandle);

    private static native void nativeSetInputDispatchMode(int i, boolean flag, boolean flag1);

    private static native void nativeSetInputFilterEnabled(int i, boolean flag);

    private static native void nativeSetInputWindows(int i, InputWindowHandle ainputwindowhandle[]);

    private static native void nativeSetPointerSpeed(int i, int j);

    private static native void nativeSetShowTouches(int i, boolean flag);

    private static native void nativeSetSystemUiVisibility(int i, int j);

    private static native void nativeStart(int i);

    private static native boolean nativeTransferTouchFocus(int i, InputChannel inputchannel, InputChannel inputchannel1);

    private static native void nativeUnregisterInputChannel(int i, InputChannel inputchannel);

    private static native void nativeVibrate(int i, int j, long al[], int k, int l);

    private long notifyANR(InputApplicationHandle inputapplicationhandle, InputWindowHandle inputwindowhandle) {
        return mCallbacks.notifyANR(inputapplicationhandle, inputwindowhandle);
    }

    private void notifyConfigurationChanged(long l) {
        mCallbacks.notifyConfigurationChanged();
    }

    private void notifyInputChannelBroken(InputWindowHandle inputwindowhandle) {
        mCallbacks.notifyInputChannelBroken(inputwindowhandle);
    }

    private void notifyInputDevicesChanged(InputDevice ainputdevice[]) {
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        if(!mInputDevicesChangedPending) {
            mInputDevicesChangedPending = true;
            mHandler.obtainMessage(1, mInputDevices).sendToTarget();
        }
        mInputDevices = ainputdevice;
        return;
    }

    private void notifyLidSwitchChanged(long l, boolean flag) {
        mCallbacks.notifyLidSwitchChanged(l, flag);
    }

    private void onInputDevicesChangedListenerDied(int i) {
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        mInputDevicesChangedListeners.remove(i);
        return;
    }

    private void registerPointerSpeedSettingObserver() {
        mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("pointer_speed"), true, new ContentObserver(mHandler) {

            public void onChange(boolean flag) {
                updatePointerSpeedFromSettings();
            }

            final InputManagerService this$0;

             {
                this$0 = InputManagerService.this;
                super(handler);
            }
        });
    }

    private void registerShowTouchesSettingObserver() {
        mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("show_touches"), true, new ContentObserver(mHandler) {

            public void onChange(boolean flag) {
                updateShowTouchesFromSettings();
            }

            final InputManagerService this$0;

             {
                this$0 = InputManagerService.this;
                super(handler);
            }
        });
    }

    private void reloadDeviceAliases() {
        nativeReloadDeviceAliases(mPtr);
    }

    private void reloadKeyboardLayouts() {
        nativeReloadKeyboardLayouts(mPtr);
    }

    private void setPointerSpeedUnchecked(int i) {
        int j = Math.min(Math.max(i, -7), 7);
        nativeSetPointerSpeed(mPtr, j);
    }

    private void showMissingKeyboardLayoutNotification() {
        if(!mKeyboardLayoutNotificationShown) {
            if(mKeyboardLayoutIntent == null) {
                Intent intent = new Intent("android.settings.INPUT_METHOD_SETTINGS");
                intent.setFlags(0x14200000);
                mKeyboardLayoutIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            }
            Resources resources = mContext.getResources();
            android.app.Notification notification = (new android.app.Notification.Builder(mContext)).setContentTitle(resources.getString(0x104044b)).setContentText(resources.getString(0x104044c)).setContentIntent(mKeyboardLayoutIntent).setSmallIcon(0x1080358).setPriority(-1).build();
            mNotificationManager.notify(0x104044b, notification);
            mKeyboardLayoutNotificationShown = true;
        }
    }

    private void updateKeyboardLayouts() {
        final HashSet availableKeyboardLayouts;
        availableKeyboardLayouts = new HashSet();
        visitAllKeyboardLayouts(new KeyboardLayoutVisitor() {

            public void visitKeyboardLayout(Resources resources, String s, String s1, String s2, int i) {
                availableKeyboardLayouts.add(s);
            }

            final InputManagerService this$0;
            final HashSet val$availableKeyboardLayouts;

             {
                this$0 = InputManagerService.this;
                availableKeyboardLayouts = hashset;
                super();
            }
        });
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        mDataStore.removeUninstalledKeyboardLayouts(availableKeyboardLayouts);
        mDataStore.saveIfNeeded();
        persistentdatastore;
        JVM INSTR monitorexit ;
        reloadKeyboardLayouts();
        return;
        Exception exception;
        exception;
        mDataStore.saveIfNeeded();
        throw exception;
        Exception exception1;
        exception1;
        throw exception1;
    }

    private void visitAllKeyboardLayouts(KeyboardLayoutVisitor keyboardlayoutvisitor) {
        PackageManager packagemanager = mContext.getPackageManager();
        for(Iterator iterator = packagemanager.queryBroadcastReceivers(new Intent("android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS"), 128).iterator(); iterator.hasNext(); visitKeyboardLayoutsInPackage(packagemanager, ((ResolveInfo)iterator.next()).activityInfo, null, keyboardlayoutvisitor));
    }

    private void visitKeyboardLayout(String s, KeyboardLayoutVisitor keyboardlayoutvisitor) {
        KeyboardLayoutDescriptor keyboardlayoutdescriptor;
        PackageManager packagemanager;
        keyboardlayoutdescriptor = KeyboardLayoutDescriptor.parse(s);
        if(keyboardlayoutdescriptor == null)
            break MISSING_BLOCK_LABEL_52;
        packagemanager = mContext.getPackageManager();
        visitKeyboardLayoutsInPackage(packagemanager, packagemanager.getReceiverInfo(new ComponentName(keyboardlayoutdescriptor.packageName, keyboardlayoutdescriptor.receiverName), 128), keyboardlayoutdescriptor.keyboardLayoutName, keyboardlayoutvisitor);
_L2:
        return;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void visitKeyboardLayoutsInPackage(PackageManager packagemanager, ActivityInfo activityinfo, String s, KeyboardLayoutVisitor keyboardlayoutvisitor) {
        int i;
        Bundle bundle = activityinfo.metaData;
        if(bundle != null) {
label0:
            {
                i = bundle.getInt("android.hardware.input.metadata.KEYBOARD_LAYOUTS");
                if(i != 0)
                    break label0;
                Log.w("InputManager", (new StringBuilder()).append("Missing meta-data 'android.hardware.input.metadata.KEYBOARD_LAYOUTS' on receiver ").append(((ComponentInfo) (activityinfo)).packageName).append("/").append(((ComponentInfo) (activityinfo)).name).toString());
            }
        }
_L1:
        return;
        String s1;
        Resources resources;
        XmlResourceParser xmlresourceparser;
        String s2;
        CharSequence charsequence = activityinfo.loadLabel(packagemanager);
        Exception exception;
        if(charsequence != null)
            s1 = charsequence.toString();
        else
            s1 = "";
        resources = packagemanager.getResourcesForApplication(activityinfo.applicationInfo);
        xmlresourceparser = resources.getXml(i);
        XmlUtils.beginDocument(xmlresourceparser, "keyboard-layouts");
_L2:
        XmlUtils.nextElement(xmlresourceparser);
        s2 = xmlresourceparser.getName();
label1:
        {
            if(s2 != null)
                break label1;
            try {
                xmlresourceparser.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception) {
                Log.w("InputManager", (new StringBuilder()).append("Could not parse keyboard layout resource from receiver ").append(((ComponentInfo) (activityinfo)).packageName).append("/").append(((ComponentInfo) (activityinfo)).name).toString(), exception);
            }
        }
          goto _L1
        TypedArray typedarray;
        if(!s2.equals("keyboard-layout"))
            break MISSING_BLOCK_LABEL_387;
        typedarray = resources.obtainAttributes(xmlresourceparser, com.android.internal.R.styleable.KeyboardLayout);
        String s3;
        String s4;
        int j;
        s3 = typedarray.getString(1);
        s4 = typedarray.getString(0);
        j = typedarray.getResourceId(2, 0);
        if(s3 != null && s4 != null && j != 0)
            break MISSING_BLOCK_LABEL_329;
        Log.w("InputManager", (new StringBuilder()).append("Missing required 'name', 'label' or 'keyboardLayout' attributes in keyboard layout resource from receiver ").append(((ComponentInfo) (activityinfo)).packageName).append("/").append(((ComponentInfo) (activityinfo)).name).toString());
_L3:
        typedarray.recycle();
          goto _L2
        Exception exception1;
        exception1;
        xmlresourceparser.close();
        throw exception1;
        String s5 = KeyboardLayoutDescriptor.format(((ComponentInfo) (activityinfo)).packageName, ((ComponentInfo) (activityinfo)).name, s3);
        if(s == null || s3.equals(s))
            keyboardlayoutvisitor.visitKeyboardLayout(resources, s5, s4, s1, j);
          goto _L3
        Exception exception2;
        exception2;
        typedarray.recycle();
        throw exception2;
        Log.w("InputManager", (new StringBuilder()).append("Skipping unrecognized element '").append(s2).append("' in keyboard layout resource from receiver ").append(((ComponentInfo) (activityinfo)).packageName).append("/").append(((ComponentInfo) (activityinfo)).name).toString());
          goto _L2
    }

    public void addKeyboardLayoutForInputDevice(String s, String s1) {
        if(!checkCallingPermission("android.permission.SET_KEYBOARD_LAYOUT", "addKeyboardLayoutForInputDevice()"))
            throw new SecurityException("Requires SET_KEYBOARD_LAYOUT permission");
        if(s == null)
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        if(s1 == null)
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        String s2 = mDataStore.getCurrentKeyboardLayout(s);
        if(mDataStore.addKeyboardLayout(s, s1) && !Objects.equal(s2, mDataStore.getCurrentKeyboardLayout(s)))
            mHandler.sendEmptyMessage(3);
        mDataStore.saveIfNeeded();
        persistentdatastore;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        mDataStore.saveIfNeeded();
        throw exception;
    }

    public void cancelVibrate(int i, IBinder ibinder) {
        VibratorToken vibratortoken;
        synchronized(mVibratorLock) {
            vibratortoken = (VibratorToken)mVibratorTokens.get(ibinder);
            if(vibratortoken == null || vibratortoken.mDeviceId != i)
                break MISSING_BLOCK_LABEL_57;
        }
        cancelVibrateIfNeeded(vibratortoken);
          goto _L1
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump InputManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        printwriter.println("INPUT MANAGER (dumpsys input)\n");
        String s = nativeDump(mPtr);
        if(s != null)
            printwriter.println(s);
        if(true) goto _L4; else goto _L3
_L3:
    }

    final boolean filterInputEvent(InputEvent inputevent, int i) {
        boolean flag;
        synchronized(mInputFilterLock) {
            if(mInputFilter != null) {
                mInputFilter.filterInputEvent(inputevent, i);
                flag = false;
                break MISSING_BLOCK_LABEL_50;
            }
        }
        inputevent.recycle();
        flag = true;
          goto _L1
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
        return flag;
    }

    public String getCurrentKeyboardLayoutForInputDevice(String s) {
        if(s == null)
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        String s1 = mDataStore.getCurrentKeyboardLayout(s);
        return s1;
    }

    public InputDevice getInputDevice(int i) {
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        int j = mInputDevices.length;
        int k = 0;
        do {
label0:
            {
                InputDevice inputdevice;
                if(k < j) {
                    inputdevice = mInputDevices[k];
                    if(inputdevice.getId() != i)
                        break label0;
                } else {
                    inputdevice = null;
                }
                return inputdevice;
            }
            k++;
        } while(true);
    }

    public int[] getInputDeviceIds() {
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        int i = mInputDevices.length;
        int ai[] = new int[i];
        for(int j = 0; j < i; j++)
            ai[j] = mInputDevices[j].getId();

        return ai;
    }

    public InputDevice[] getInputDevices() {
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        InputDevice ainputdevice[] = mInputDevices;
        return ainputdevice;
    }

    public int getKeyCodeState(int i, int j, int k) {
        return nativeGetKeyCodeState(mPtr, i, j, k);
    }

    public KeyboardLayout getKeyboardLayout(String s) {
        if(s == null)
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        final KeyboardLayout result[] = new KeyboardLayout[1];
        visitKeyboardLayout(s, new KeyboardLayoutVisitor() {

            public void visitKeyboardLayout(Resources resources, String s1, String s2, String s3, int i) {
                result[0] = new KeyboardLayout(s1, s2, s3);
            }

            final InputManagerService this$0;
            final KeyboardLayout val$result[];

             {
                this$0 = InputManagerService.this;
                result = akeyboardlayout;
                super();
            }
        });
        if(result[0] == null)
            Log.w("InputManager", (new StringBuilder()).append("Could not get keyboard layout with descriptor '").append(s).append("'.").toString());
        return result[0];
    }

    public KeyboardLayout[] getKeyboardLayouts() {
        final ArrayList list = new ArrayList();
        visitAllKeyboardLayouts(new KeyboardLayoutVisitor() {

            public void visitKeyboardLayout(Resources resources, String s, String s1, String s2, int i) {
                list.add(new KeyboardLayout(s, s1, s2));
            }

            final InputManagerService this$0;
            final ArrayList val$list;

             {
                this$0 = InputManagerService.this;
                list = arraylist;
                super();
            }
        });
        return (KeyboardLayout[])list.toArray(new KeyboardLayout[list.size()]);
    }

    public String[] getKeyboardLayoutsForInputDevice(String s) {
        if(s == null)
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        String as[] = mDataStore.getKeyboardLayouts(s);
        return as;
    }

    public int getScanCodeState(int i, int j, int k) {
        return nativeGetScanCodeState(mPtr, i, j, k);
    }

    public int getSwitchState(int i, int j, int k) {
        return nativeGetSwitchState(mPtr, i, j, k);
    }

    public boolean hasKeys(int i, int j, int ai[], boolean aflag[]) {
        if(ai == null)
            throw new IllegalArgumentException("keyCodes must not be null.");
        if(aflag == null || aflag.length < ai.length)
            throw new IllegalArgumentException("keyExists must not be null and must be at least as large as keyCodes.");
        else
            return nativeHasKeys(mPtr, i, j, ai, aflag);
    }

    public boolean injectInputEvent(InputEvent inputevent, int i) {
        int j;
        int k;
        long l;
        if(inputevent == null)
            throw new IllegalArgumentException("event must not be null");
        if(i != 0 && i != 2 && i != 1)
            throw new IllegalArgumentException("mode is invalid");
        j = Binder.getCallingPid();
        k = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        int i1 = nativeInjectInputEvent(mPtr, inputevent, j, k, i, 30000, 0x8000000);
        Binder.restoreCallingIdentity(l);
        i1;
        JVM INSTR tableswitch 0 3: default 112
    //                   0 203
    //                   1 160
    //                   2 112
    //                   3 209;
           goto _L1 _L2 _L3 _L1 _L4
_L1:
        boolean flag;
        Slog.w("InputManager", (new StringBuilder()).append("Input event injection from pid ").append(j).append(" failed.").toString());
        flag = false;
_L6:
        return flag;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L3:
        Slog.w("InputManager", (new StringBuilder()).append("Input event injection from pid ").append(j).append(" permission denied.").toString());
        throw new SecurityException("Injecting to another application requires INJECT_EVENTS permission");
_L2:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L4:
        Slog.w("InputManager", (new StringBuilder()).append("Input event injection from pid ").append(j).append(" timed out.").toString());
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void monitor() {
        synchronized(mInputFilterLock) { }
        nativeMonitor(mPtr);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public InputChannel monitorInput(String s) {
        if(s == null) {
            throw new IllegalArgumentException("inputChannelName must not be null.");
        } else {
            InputChannel ainputchannel[] = InputChannel.openInputChannelPair(s);
            nativeRegisterInputChannel(mPtr, ainputchannel[0], null, true);
            ainputchannel[0].dispose();
            return ainputchannel[1];
        }
    }

    void onVibratorTokenDied(VibratorToken vibratortoken) {
        synchronized(mVibratorLock) {
            mVibratorTokens.remove(vibratortoken.mToken);
        }
        cancelVibrateIfNeeded(vibratortoken);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void registerInputChannel(InputChannel inputchannel, InputWindowHandle inputwindowhandle) {
        if(inputchannel == null) {
            throw new IllegalArgumentException("inputChannel must not be null.");
        } else {
            nativeRegisterInputChannel(mPtr, inputchannel, inputwindowhandle, false);
            return;
        }
    }

    public void registerInputDevicesChangedListener(IInputDevicesChangedListener iinputdeviceschangedlistener) {
        if(iinputdeviceschangedlistener == null)
            throw new IllegalArgumentException("listener must not be null");
        Object obj = mInputDevicesLock;
        obj;
        JVM INSTR monitorenter ;
        int i;
        i = Binder.getCallingPid();
        if(mInputDevicesChangedListeners.get(i) != null)
            throw new SecurityException("The calling process has already registered an InputDevicesChangedListener.");
        break MISSING_BLOCK_LABEL_55;
        Exception exception;
        exception;
        throw exception;
        InputDevicesChangedListenerRecord inputdeviceschangedlistenerrecord = new InputDevicesChangedListenerRecord(i, iinputdeviceschangedlistener);
        try {
            iinputdeviceschangedlistener.asBinder().linkToDeath(inputdeviceschangedlistenerrecord, 0);
        }
        catch(RemoteException remoteexception) {
            throw new RuntimeException(remoteexception);
        }
        mInputDevicesChangedListeners.put(i, inputdeviceschangedlistenerrecord);
        obj;
        JVM INSTR monitorexit ;
    }

    public void removeKeyboardLayoutForInputDevice(String s, String s1) {
        if(!checkCallingPermission("android.permission.SET_KEYBOARD_LAYOUT", "removeKeyboardLayoutForInputDevice()"))
            throw new SecurityException("Requires SET_KEYBOARD_LAYOUT permission");
        if(s == null)
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        if(s1 == null)
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        String s2 = mDataStore.getCurrentKeyboardLayout(s);
        if(mDataStore.removeKeyboardLayout(s, s1) && !Objects.equal(s2, mDataStore.getCurrentKeyboardLayout(s)))
            mHandler.sendEmptyMessage(3);
        mDataStore.saveIfNeeded();
        persistentdatastore;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        mDataStore.saveIfNeeded();
        throw exception;
    }

    public void setCurrentKeyboardLayoutForInputDevice(String s, String s1) {
        if(!checkCallingPermission("android.permission.SET_KEYBOARD_LAYOUT", "setCurrentKeyboardLayoutForInputDevice()"))
            throw new SecurityException("Requires SET_KEYBOARD_LAYOUT permission");
        if(s == null)
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        if(s1 == null)
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        PersistentDataStore persistentdatastore = mDataStore;
        persistentdatastore;
        JVM INSTR monitorenter ;
        if(mDataStore.setCurrentKeyboardLayout(s, s1))
            mHandler.sendEmptyMessage(3);
        mDataStore.saveIfNeeded();
        persistentdatastore;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        mDataStore.saveIfNeeded();
        throw exception;
    }

    public void setDisplayOrientation(int i, int j, int k) {
        if(j < 0 || j > 3) {
            throw new IllegalArgumentException("Invalid rotation.");
        } else {
            nativeSetDisplayOrientation(mPtr, i, j, k);
            return;
        }
    }

    public void setDisplaySize(int i, int j, int k, int l, int i1) {
        if(j <= 0 || k <= 0 || l <= 0 || i1 <= 0) {
            throw new IllegalArgumentException("Invalid display id or dimensions.");
        } else {
            nativeSetDisplaySize(mPtr, i, j, k, l, i1);
            return;
        }
    }

    public void setFocusedApplication(InputApplicationHandle inputapplicationhandle) {
        nativeSetFocusedApplication(mPtr, inputapplicationhandle);
    }

    public void setInputDispatchMode(boolean flag, boolean flag1) {
        nativeSetInputDispatchMode(mPtr, flag, flag1);
    }

    public void setInputFilter(InputFilter inputfilter) {
        Object obj = mInputFilterLock;
        obj;
        JVM INSTR monitorenter ;
        InputFilter inputfilter1 = mInputFilter;
        if(inputfilter1 != inputfilter) {
            if(inputfilter1 != null) {
                mInputFilter = null;
                mInputFilterHost.disconnectLocked();
                mInputFilterHost = null;
                inputfilter1.uninstall();
            }
            if(inputfilter != null) {
                mInputFilter = inputfilter;
                mInputFilterHost = new InputFilterHost();
                inputfilter.install(mInputFilterHost);
            }
            int i = mPtr;
            boolean flag;
            if(inputfilter != null)
                flag = true;
            else
                flag = false;
            nativeSetInputFilterEnabled(i, flag);
        }
        return;
    }

    public void setInputWindows(InputWindowHandle ainputwindowhandle[]) {
        nativeSetInputWindows(mPtr, ainputwindowhandle);
    }

    public void setSystemUiVisibility(int i) {
        nativeSetSystemUiVisibility(mPtr, i);
    }

    public void start() {
        Slog.i("InputManager", "Starting input manager");
        nativeStart(mPtr);
        Watchdog.getInstance().addMonitor(this);
        registerPointerSpeedSettingObserver();
        registerShowTouchesSettingObserver();
        updatePointerSpeedFromSettings();
        updateShowTouchesFromSettings();
    }

    public void switchKeyboardLayout(int i, int j) {
        mHandler.obtainMessage(2, i, j).sendToTarget();
    }

    public void systemReady(BluetoothService bluetoothservice) {
        mBluetoothService = bluetoothservice;
        mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
        mSystemReady = true;
        IntentFilter intentfilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentfilter.addDataScheme("package");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                updateKeyboardLayouts();
            }

            final InputManagerService this$0;

             {
                this$0 = InputManagerService.this;
                super();
            }
        }, intentfilter, null, mHandler);
        IntentFilter intentfilter1 = new IntentFilter("android.bluetooth.device.action.ALIAS_CHANGED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                reloadDeviceAliases();
            }

            final InputManagerService this$0;

             {
                this$0 = InputManagerService.this;
                super();
            }
        }, intentfilter1, null, mHandler);
        mHandler.sendEmptyMessage(5);
        mHandler.sendEmptyMessage(4);
    }

    public boolean transferTouchFocus(InputChannel inputchannel, InputChannel inputchannel1) {
        if(inputchannel == null)
            throw new IllegalArgumentException("fromChannel must not be null.");
        if(inputchannel1 == null)
            throw new IllegalArgumentException("toChannel must not be null.");
        else
            return nativeTransferTouchFocus(mPtr, inputchannel, inputchannel1);
    }

    public void tryPointerSpeed(int i) {
        if(!checkCallingPermission("android.permission.SET_POINTER_SPEED", "tryPointerSpeed()"))
            throw new SecurityException("Requires SET_POINTER_SPEED permission");
        if(i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        } else {
            setPointerSpeedUnchecked(i);
            return;
        }
    }

    public void unregisterInputChannel(InputChannel inputchannel) {
        if(inputchannel == null) {
            throw new IllegalArgumentException("inputChannel must not be null.");
        } else {
            nativeUnregisterInputChannel(mPtr, inputchannel);
            return;
        }
    }

    public void updatePointerSpeedFromSettings() {
        setPointerSpeedUnchecked(getPointerSpeedSetting());
    }

    public void updateShowTouchesFromSettings() {
        boolean flag = false;
        int i = getShowTouchesSetting(0);
        int j = mPtr;
        if(i != 0)
            flag = true;
        nativeSetShowTouches(j, flag);
    }

    public void vibrate(int i, long al[], int j, IBinder ibinder) {
        if(j >= al.length)
            throw new ArrayIndexOutOfBoundsException();
        Object obj = mVibratorLock;
        obj;
        JVM INSTR monitorenter ;
        VibratorToken vibratortoken;
        vibratortoken = (VibratorToken)mVibratorTokens.get(ibinder);
        if(vibratortoken != null)
            break MISSING_BLOCK_LABEL_93;
        int k = mNextVibratorTokenValue;
        mNextVibratorTokenValue = k + 1;
        vibratortoken = new VibratorToken(i, ibinder, k);
        try {
            ibinder.linkToDeath(vibratortoken, 0);
        }
        catch(RemoteException remoteexception) {
            throw new RuntimeException(remoteexception);
        }
        mVibratorTokens.put(ibinder, vibratortoken);
        vibratortoken;
        JVM INSTR monitorenter ;
        vibratortoken.mVibrating = true;
        nativeVibrate(mPtr, i, al, j, vibratortoken.mTokenValue);
        vibratortoken;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        vibratortoken;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public static final int BTN_MOUSE = 272;
    static final boolean DEBUG = false;
    private static final String EXCLUDED_DEVICES_PATH = "etc/excluded-input-devices.xml";
    private static final int INJECTION_TIMEOUT_MILLIS = 30000;
    private static final int INPUT_EVENT_INJECTION_FAILED = 2;
    private static final int INPUT_EVENT_INJECTION_PERMISSION_DENIED = 1;
    private static final int INPUT_EVENT_INJECTION_SUCCEEDED = 0;
    private static final int INPUT_EVENT_INJECTION_TIMED_OUT = 3;
    public static final int KEY_STATE_DOWN = 1;
    public static final int KEY_STATE_UNKNOWN = -1;
    public static final int KEY_STATE_UP = 0;
    public static final int KEY_STATE_VIRTUAL = 2;
    private static final int MSG_DELIVER_INPUT_DEVICES_CHANGED = 1;
    private static final int MSG_RELOAD_DEVICE_ALIASES = 5;
    private static final int MSG_RELOAD_KEYBOARD_LAYOUTS = 3;
    private static final int MSG_SWITCH_KEYBOARD_LAYOUT = 2;
    private static final int MSG_UPDATE_KEYBOARD_LAYOUTS = 4;
    public static final int SW_KEYPAD_SLIDE = 10;
    public static final int SW_LID = 0;
    static final String TAG = "InputManager";
    private BluetoothService mBluetoothService;
    private final Callbacks mCallbacks;
    private final Context mContext;
    private final PersistentDataStore mDataStore = new PersistentDataStore();
    private final InputManagerHandler mHandler = new InputManagerHandler();
    private InputDevice mInputDevices[];
    private final SparseArray mInputDevicesChangedListeners = new SparseArray();
    private boolean mInputDevicesChangedPending;
    private Object mInputDevicesLock;
    InputFilter mInputFilter;
    InputFilterHost mInputFilterHost;
    final Object mInputFilterLock = new Object();
    private PendingIntent mKeyboardLayoutIntent;
    private boolean mKeyboardLayoutNotificationShown;
    private int mNextVibratorTokenValue;
    private NotificationManager mNotificationManager;
    private final int mPtr;
    private Toast mSwitchedKeyboardLayoutToast;
    private boolean mSystemReady;
    private final ArrayList mTempFullKeyboards = new ArrayList();
    private final ArrayList mTempInputDevicesChangedListenersToNotify = new ArrayList();
    private Object mVibratorLock;
    private HashMap mVibratorTokens;








}
