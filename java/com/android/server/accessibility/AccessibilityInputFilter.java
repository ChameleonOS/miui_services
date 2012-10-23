// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.accessibility;

import android.content.Context;
import android.os.PowerManager;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import com.android.server.input.InputFilter;

// Referenced classes of package com.android.server.accessibility:
//            TouchExplorer, AccessibilityManagerService

public class AccessibilityInputFilter extends InputFilter {
    public static interface Explorer {

        public abstract void clear();

        public abstract void clear(MotionEvent motionevent, int i);

        public abstract void onMotionEvent(MotionEvent motionevent, int i);
    }


    public AccessibilityInputFilter(Context context, AccessibilityManagerService accessibilitymanagerservice) {
        super(context.getMainLooper());
        mContext = context;
        mAms = accessibilitymanagerservice;
        mPm = (PowerManager)context.getSystemService("power");
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityevent) {
        if(mTouchExplorer != null)
            mTouchExplorer.onAccessibilityEvent(accessibilityevent);
    }

    public void onInputEvent(InputEvent inputevent, int i) {
        if(inputevent.getSource() == 4098) {
            MotionEvent motionevent = (MotionEvent)inputevent;
            int j = inputevent.getDeviceId();
            if(mTouchscreenSourceDeviceId != j) {
                mTouchscreenSourceDeviceId = j;
                mTouchExplorer.clear(motionevent, i);
            }
            if((0x40000000 & i) != 0) {
                mPm.userActivity(inputevent.getEventTime(), false);
                mTouchExplorer.onMotionEvent(motionevent, i);
            } else {
                mTouchExplorer.clear(motionevent, i);
            }
        } else {
            super.onInputEvent(inputevent, i);
        }
    }

    public void onInstalled() {
        mTouchExplorer = new TouchExplorer(this, mContext, mAms);
        super.onInstalled();
    }

    public void onUninstalled() {
        mTouchExplorer.clear();
        super.onUninstalled();
    }

    private static final boolean DEBUG = false;
    private static final String TAG = "AccessibilityInputFilter";
    private final AccessibilityManagerService mAms;
    private final Context mContext;
    private final PowerManager mPm;
    private TouchExplorer mTouchExplorer;
    private int mTouchscreenSourceDeviceId;
}
