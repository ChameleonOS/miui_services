// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

// Referenced classes of package com.android.server:
//            LightsService

public class MiuiLightsService extends LightsService {
    public class Light extends LightsService.Light {

        private boolean isTurnOn() {
            boolean flag = true;
            if(android.provider.Settings.Secure.getInt(mResolver, "screen_buttons_turn_on", flag) != flag)
                flag = false;
            return flag;
        }

        void setFlashing(String s, String s1) {
            int i = mContext.getResources().getColor(0x6070009);
            setFlashing(android.provider.Settings.System.getInt(mResolver, s, i), 1, 500, 0);
            mHandler.removeMessages(1);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 500L);
        }

        void setLightLocked(int i, int j, int k, int l, int i1) {
            mColor = i;
            mMode = j;
            mOnMS = k;
            mOffMS = l;
            mBrightnessMode = i1;
            if(!mDisabled && mTurnOn)
                super.setLightLocked(i, j, k, l, i1);
        }

        private static final int LIGHT_ON_MS = 500;
        private static final int STOP_FLASH_MSG = 1;
        private int mBrightnessMode;
        private int mColor;
        private boolean mDisabled;
        private Handler mHandler;
        private int mMode;
        private int mOffMS;
        private int mOnMS;
        private boolean mTurnOn;
        final MiuiLightsService this$0;







/*
        static boolean access$202(Light light, boolean flag) {
            light.mDisabled = flag;
            return flag;
        }

*/




/*
        static boolean access$402(Light light, boolean flag) {
            light.mTurnOn = flag;
            return flag;
        }

*/






        private Light(int i) {
            this$0 = MiuiLightsService.this;
            super(MiuiLightsService.this, i, 0);
            mTurnOn = true;
            mHandler = new Handler() {

                public void handleMessage(Message message) {
                    message.what;
                    JVM INSTR tableswitch 1 1: default 24
                //                               1 25;
                       goto _L1 _L2
_L1:
                    return;
_L2:
                    turnOff();
                    if(true) goto _L1; else goto _L3
_L3:
                }

                final Light this$1;

                 {
                    this$1 = Light.this;
                    super();
                }
            };
            if(2 != i) goto _L2; else goto _L1
_L1:
            android.provider.Settings.Secure.putInt(mResolver, "screen_buttons_state", 0);
            mTurnOn = isTurnOn();
            mResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("screen_buttons_state"), true, new ContentObserver(MiuiLightsService.this) {

                public void onChange(boolean flag) {
                    Light light = Light.this;
                    boolean flag1;
                    if(android.provider.Settings.Secure.getInt(mResolver, "screen_buttons_state", 0) != 0)
                        flag1 = true;
                    else
                        flag1 = false;
                    light.mDisabled = flag1;
                    if(!mDisabled) goto _L2; else goto _L1
_L1:
                    setLightLocked(0, 0, 0, 0, 0);
_L4:
                    return;
_L2:
                    if(mTurnOn)
                        setLightLocked(mColor, mMode, mOnMS, mOffMS, mBrightnessMode);
                    if(true) goto _L4; else goto _L3
_L3:
                }

                final Light this$1;
                final MiuiLightsService val$this$0;

                 {
                    this$1 = Light.this;
                    this$0 = miuilightsservice;
                    super(final_handler);
                }
            });
            mResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("screen_buttons_turn_on"), true, new ContentObserver(MiuiLightsService.this) {

                public void onChange(boolean flag) {
                    if(!mDisabled) {
                        mTurnOn = isTurnOn();
                        if(mTurnOn)
                            setLightLocked(mColor, mMode, mOnMS, mOffMS, mBrightnessMode);
                        else
                            setLightLocked(0, mMode, 0, 0, mBrightnessMode);
                    }
                }

                final Light this$1;
                final MiuiLightsService val$this$0;

                 {
                    this$1 = Light.this;
                    this$0 = miuilightsservice;
                    super(final_handler);
                }
            });
_L4:
            return;
_L2:
            if(4 == i) {
                mResolver.registerContentObserver(android.provider.Settings.System.getUriFor("breathing_light_color"), true, new ContentObserver(MiuiLightsService.this) {

                    public void onChange(boolean flag) {
                        setFlashing("breathing_light_color", "breathing_light_freq");
                    }

                    final Light this$1;
                    final MiuiLightsService val$this$0;

                 {
                    this$1 = Light.this;
                    this$0 = miuilightsservice;
                    super(final_handler);
                }
                });
                mResolver.registerContentObserver(android.provider.Settings.System.getUriFor("call_breathing_light_color"), true, new ContentObserver(MiuiLightsService.this) {

                    public void onChange(boolean flag) {
                        setFlashing("call_breathing_light_color", "call_breathing_light_freq");
                    }

                    final Light this$1;
                    final MiuiLightsService val$this$0;

                 {
                    this$1 = Light.this;
                    this$0 = miuilightsservice;
                    super(final_handler);
                }
                });
                mResolver.registerContentObserver(android.provider.Settings.System.getUriFor("mms_breathing_light_color"), true, new ContentObserver(MiuiLightsService.this) {

                    public void onChange(boolean flag) {
                        setFlashing("mms_breathing_light_color", "mms_breathing_light_freq");
                    }

                    final Light this$1;
                    final MiuiLightsService val$this$0;

                 {
                    this$1 = Light.this;
                    this$0 = miuilightsservice;
                    super(final_handler);
                }
                });
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

    }


    MiuiLightsService(Context context) {
        super(context);
        mContext = context;
        mResolver = context.getContentResolver();
        setLight(2, new Light(2));
        setLight(4, new Light(4));
    }

    private Context mContext;
    private ContentResolver mResolver;


}
