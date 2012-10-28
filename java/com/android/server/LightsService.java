// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.os.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LightsService {
    public class Light {

        private void stopFlashing() {
            this;
            JVM INSTR monitorenter ;
            setLightLocked(mColor, 0, 0, 0, 0);
            return;
        }

        public void pulse() {
            pulse(0xffffff, 7);
        }

        public void pulse(int i, int j) {
            this;
            JVM INSTR monitorenter ;
            if(mColor == 0 && !mFlashing) {
                setLightLocked(i, 2, j, 1000, 0);
                mH.sendMessageDelayed(Message.obtain(mH, 1, this), j);
            }
            return;
        }

        public void setBrightness(int i) {
            setBrightness(i, 0);
        }

        public void setBrightness(int i, int j) {
            this;
            JVM INSTR monitorenter ;
            int k = i & 0xff;
            int l = k | (0xff000000 | k << 16 | k << 8);
            setLightLocked(l, 0, 0, 0, j);
            return;
        }

        public void setColor(int i) {
            this;
            JVM INSTR monitorenter ;
            setLightLocked(i, 0, 0, 0, 0);
            return;
        }

        public void setFlashing(int i, int j, int k, int l) {
            this;
            JVM INSTR monitorenter ;
            setLightLocked(i, j, k, l, 0);
            return;
        }

        void setLightLocked(int i, int j, int k, int l, int i1) {
            if(i != mColor || j != mMode || k != mOnMS || l != mOffMS) {
                mColor = i;
                mMode = j;
                mOnMS = k;
                mOffMS = l;
                LightsService.setLight_native(mNativePointer, mId, i, j, k, l, i1);
            }
        }

        public void turnOff() {
            this;
            JVM INSTR monitorenter ;
            setLightLocked(0, 0, 0, 0, 0);
            return;
        }

        private int mColor;
        private boolean mFlashing;
        private int mId;
        private int mMode;
        private int mOffMS;
        private int mOnMS;
        final LightsService this$0;


        private Light(int i) {
            this$0 = LightsService.this;
            super();
            mId = i;
        }

        Light(int i, int j) {
            this(i);
        }

    }


    LightsService(Context context) {
        mH = new Handler() {

            public void handleMessage(Message message) {
                ((Light)message.obj).stopFlashing();
            }

            final LightsService this$0;

             {
                this$0 = LightsService.this;
                super();
            }
        };
        mNativePointer = init_native();
        mContext = context;
        ServiceManager.addService("hardware", mLegacyFlashlightHack);
        for(int i = 0; i < 8; i++)
            mLights[i] = new Light(i);

    }

    private static native void finalize_native(int i);

    private static native int init_native();

    private static native void setLight_native(int i, int j, int k, int l, int i1, int j1, int k1);

    protected void finalize() throws Throwable {
        finalize_native(mNativePointer);
        super.finalize();
    }

    public Light getLight(int i) {
        return mLights[i];
    }

    void setLight(int i, Light light) {
        mLights[i] = light;
    }

    static final int BRIGHTNESS_MODE_SENSOR = 1;
    static final int BRIGHTNESS_MODE_USER = 0;
    private static final boolean DEBUG = false;
    static final int LIGHT_FLASH_HARDWARE = 2;
    static final int LIGHT_FLASH_NONE = 0;
    static final int LIGHT_FLASH_TIMED = 1;
    static final int LIGHT_ID_ATTENTION = 5;
    static final int LIGHT_ID_BACKLIGHT = 0;
    static final int LIGHT_ID_BATTERY = 3;
    static final int LIGHT_ID_BLUETOOTH = 6;
    static final int LIGHT_ID_BUTTONS = 2;
    static final int LIGHT_ID_COUNT = 8;
    static final int LIGHT_ID_KEYBOARD = 1;
    static final int LIGHT_ID_NOTIFICATIONS = 4;
    static final int LIGHT_ID_WIFI = 7;
    private static final String TAG = "LightsService";
    private final Context mContext;
    private Handler mH;
    private final android.os.IHardwareService.Stub mLegacyFlashlightHack = new android.os.IHardwareService.Stub() {

        public boolean getFlashlightEnabled() {
            boolean flag = false;
            int j;
            FileInputStream fileinputstream = new FileInputStream("/sys/class/leds/spotlight/brightness");
            j = fileinputstream.read();
            fileinputstream.close();
            if(j != 48)
                flag = true;
_L2:
            return flag;
            Exception exception;
            exception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public void setFlashlightEnabled(boolean flag) {
            if(mContext.checkCallingOrSelfPermission("android.permission.FLASHLIGHT") != 0 && mContext.checkCallingOrSelfPermission("android.permission.HARDWARE_TEST") != 0)
                throw new SecurityException("Requires FLASHLIGHT or HARDWARE_TEST permission");
            FileOutputStream fileoutputstream;
            byte abyte0[];
            fileoutputstream = new FileOutputStream("/sys/class/leds/spotlight/brightness");
            abyte0 = new byte[2];
            if(!flag) goto _L2; else goto _L1
_L1:
            int j = 49;
_L3:
            abyte0[0] = (byte)j;
            abyte0[1] = 10;
            fileoutputstream.write(abyte0);
            fileoutputstream.close();
_L4:
            return;
_L2:
            j = 48;
              goto _L3
            Exception exception;
            exception;
              goto _L4
        }

        private static final String FLASHLIGHT_FILE = "/sys/class/leds/spotlight/brightness";
        final LightsService this$0;

             {
                this$0 = LightsService.this;
                super();
            }
    };
    private final Light mLights[] = new Light[8];
    private int mNativePointer;




}
