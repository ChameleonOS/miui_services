// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.gesture.*;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.*;
import android.view.accessibility.AccessibilityEvent;
import com.android.server.input.InputFilter;
import java.util.ArrayList;
import java.util.Arrays;

// Referenced classes of package com.android.server.accessibility:
//            AccessibilityManagerService

public class TouchExplorer {
    class ReceivedPointerTracker {

        private float computePointerDeltaMove(int i, MotionEvent motionevent) {
            int j = motionevent.getPointerId(i);
            float f = motionevent.getX(i) - mReceivedPointerDownX[j];
            float f1 = motionevent.getY(i) - mReceivedPointerDownY[j];
            return (float)Math.hypot(f, f1);
        }

        private void detectActivePointers(MotionEvent motionevent) {
            int i = 0;
            int j = motionevent.getPointerCount();
            do {
                if(i >= j)
                    break;
                int k = motionevent.getPointerId(i);
                if((!mHasMovingActivePointer || !isActivePointer(k)) && (double)computePointerDeltaMove(i, motionevent) > mThresholdActivePointer) {
                    mActivePointers = 1 << k | mActivePointers;
                    mHasMovingActivePointer = true;
                }
                i++;
            } while(true);
        }

        private int findPrimaryActivePointer() {
            int i = -1;
            long l = 0x7fffffffffffffffL;
            int j = 0;
            for(int k = mReceivedPointerDownTime.length; j < k; j++) {
                if(!isActivePointer(j))
                    continue;
                long l1 = mReceivedPointerDownTime[j];
                if(l1 < l) {
                    l = l1;
                    i = j;
                }
            }

            return i;
        }

        private void handleReceivedPointerDown(int i, MotionEvent motionevent) {
            int j = motionevent.getPointerId(i);
            int k = 1 << j;
            mLastReceivedUpPointerId = 0;
            mLastReceivedUpPointerDownTime = 0L;
            mLastReceivedUpPointerActive = false;
            mLastReceivedUpPointerDownX = 0.0F;
            mLastReceivedUpPointerDownX = 0.0F;
            mReceivedPointersDown = k | mReceivedPointersDown;
            mReceivedPointerDownX[j] = motionevent.getX(i);
            mReceivedPointerDownY[j] = motionevent.getY(i);
            mReceivedPointerDownTime[j] = motionevent.getEventTime();
            if(!mHasMovingActivePointer) {
                mActivePointers = k;
                mPrimaryActivePointerId = j;
            } else {
                mActivePointers = k | mActivePointers;
            }
        }

        private void handleReceivedPointerMove(MotionEvent motionevent) {
            detectActivePointers(motionevent);
        }

        private void handleReceivedPointerUp(int i, MotionEvent motionevent) {
            int j = motionevent.getPointerId(i);
            int k = 1 << j;
            mLastReceivedUpPointerId = j;
            mLastReceivedUpPointerDownTime = getReceivedPointerDownTime(j);
            mLastReceivedUpPointerActive = isActivePointer(j);
            mLastReceivedUpPointerDownX = mReceivedPointerDownX[j];
            mLastReceivedUpPointerDownY = mReceivedPointerDownY[j];
            mReceivedPointersDown = mReceivedPointersDown & ~k;
            mActivePointers = mActivePointers & ~k;
            mReceivedPointerDownX[j] = 0.0F;
            mReceivedPointerDownY[j] = 0.0F;
            mReceivedPointerDownTime[j] = 0L;
            if(mActivePointers == 0)
                mHasMovingActivePointer = false;
            if(mPrimaryActivePointerId == j)
                mPrimaryActivePointerId = -1;
        }

        public void clear() {
            Arrays.fill(mReceivedPointerDownX, 0.0F);
            Arrays.fill(mReceivedPointerDownY, 0.0F);
            Arrays.fill(mReceivedPointerDownTime, 0L);
            mReceivedPointersDown = 0;
            mActivePointers = 0;
            mPrimaryActivePointerId = 0;
            mHasMovingActivePointer = false;
            mLastReceivedUpPointerDownTime = 0L;
            mLastReceivedUpPointerId = 0;
            mLastReceivedUpPointerActive = false;
            mLastReceivedUpPointerDownX = 0.0F;
            mLastReceivedUpPointerDownY = 0.0F;
        }

        public int getActivePointerCount() {
            return Integer.bitCount(mActivePointers);
        }

        public int getActivePointers() {
            return mActivePointers;
        }

        public MotionEvent getLastReceivedEvent() {
            return mLastReceivedEvent;
        }

        public long getLastReceivedUpPointerDownTime() {
            return mLastReceivedUpPointerDownTime;
        }

        public float getLastReceivedUpPointerDownX() {
            return mLastReceivedUpPointerDownX;
        }

        public float getLastReceivedUpPointerDownY() {
            return mLastReceivedUpPointerDownY;
        }

        public int getLastReceivedUpPointerId() {
            return mLastReceivedUpPointerId;
        }

        public int getPrimaryActivePointerId() {
            if(mPrimaryActivePointerId == -1)
                mPrimaryActivePointerId = findPrimaryActivePointer();
            return mPrimaryActivePointerId;
        }

        public int getReceivedPointerDownCount() {
            return Integer.bitCount(mReceivedPointersDown);
        }

        public long getReceivedPointerDownTime(int i) {
            return mReceivedPointerDownTime[i];
        }

        public float getReceivedPointerDownX(int i) {
            return mReceivedPointerDownX[i];
        }

        public float getReceivedPointerDownY(int i) {
            return mReceivedPointerDownY[i];
        }

        public boolean isActiveOrWasLastActiveUpPointer(int i) {
            boolean flag;
            if(isActivePointer(i) || mLastReceivedUpPointerId == i && mLastReceivedUpPointerActive)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isActivePointer(int i) {
            boolean flag = true;
            if((flag << i & mActivePointers) == 0)
                flag = false;
            return flag;
        }

        public boolean isReceivedPointerDown(int i) {
            boolean flag = true;
            if((flag << i & mReceivedPointersDown) == 0)
                flag = false;
            return flag;
        }

        public void onMotionEvent(MotionEvent motionevent) {
            if(mLastReceivedEvent != null)
                mLastReceivedEvent.recycle();
            mLastReceivedEvent = MotionEvent.obtain(motionevent);
            motionevent.getActionMasked();
            JVM INSTR tableswitch 0 6: default 68
        //                       0 69
        //                       1 101
        //                       2 93
        //                       3 68
        //                       4 68
        //                       5 81
        //                       6 113;
               goto _L1 _L2 _L3 _L4 _L1 _L1 _L5 _L6
_L1:
            return;
_L2:
            handleReceivedPointerDown(motionevent.getActionIndex(), motionevent);
            continue; /* Loop/switch isn't completed */
_L5:
            handleReceivedPointerDown(motionevent.getActionIndex(), motionevent);
            continue; /* Loop/switch isn't completed */
_L4:
            handleReceivedPointerMove(motionevent);
            continue; /* Loop/switch isn't completed */
_L3:
            handleReceivedPointerUp(motionevent.getActionIndex(), motionevent);
            continue; /* Loop/switch isn't completed */
_L6:
            handleReceivedPointerUp(motionevent.getActionIndex(), motionevent);
            if(true) goto _L1; else goto _L7
_L7:
        }

        public void populateActivePointerIds(int ai[]) {
            int i = 0;
            for(int j = mActivePointers; j != 0;) {
                int k = Integer.numberOfTrailingZeros(j);
                j &= -1 ^ 1 << k;
                ai[i] = k;
                i++;
            }

        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("=========================");
            stringbuilder.append("\nDown pointers #");
            stringbuilder.append(getReceivedPointerDownCount());
            stringbuilder.append(" [ ");
            for(int i = 0; i < 32; i++)
                if(isReceivedPointerDown(i)) {
                    stringbuilder.append(i);
                    stringbuilder.append(" ");
                }

            stringbuilder.append("]");
            stringbuilder.append("\nActive pointers #");
            stringbuilder.append(getActivePointerCount());
            stringbuilder.append(" [ ");
            for(int j = 0; j < 32; j++)
                if(isActivePointer(j)) {
                    stringbuilder.append(j);
                    stringbuilder.append(" ");
                }

            stringbuilder.append("]");
            stringbuilder.append("\nPrimary active pointer id [ ");
            stringbuilder.append(getPrimaryActivePointerId());
            stringbuilder.append(" ]");
            stringbuilder.append("\n=========================");
            return stringbuilder.toString();
        }

        public boolean wasLastReceivedUpPointerActive() {
            return mLastReceivedUpPointerActive;
        }

        private static final int COEFFICIENT_ACTIVE_POINTER = 2;
        private static final String LOG_TAG_RECEIVED_POINTER_TRACKER = "ReceivedPointerTracker";
        private int mActivePointers;
        private boolean mHasMovingActivePointer;
        private MotionEvent mLastReceivedEvent;
        private boolean mLastReceivedUpPointerActive;
        private long mLastReceivedUpPointerDownTime;
        private float mLastReceivedUpPointerDownX;
        private float mLastReceivedUpPointerDownY;
        private int mLastReceivedUpPointerId;
        private int mPrimaryActivePointerId;
        private final long mReceivedPointerDownTime[] = new long[32];
        private final float mReceivedPointerDownX[] = new float[32];
        private final float mReceivedPointerDownY[] = new float[32];
        private int mReceivedPointersDown;
        private final double mThresholdActivePointer;
        final TouchExplorer this$0;

        public ReceivedPointerTracker(Context context) {
            this$0 = TouchExplorer.this;
            super();
            mThresholdActivePointer = 2 * ViewConfiguration.get(context).getScaledTouchSlop();
        }
    }

    class InjectedPointerTracker {

        public void clear() {
            mInjectedPointersDown = 0;
        }

        public int getInjectedPointerDownCount() {
            return Integer.bitCount(mInjectedPointersDown);
        }

        public int getInjectedPointersDown() {
            return mInjectedPointersDown;
        }

        public long getLastInjectedDownEventTime() {
            return mLastInjectedDownEventTime;
        }

        public MotionEvent getLastInjectedHoverEvent() {
            return mLastInjectedHoverEvent;
        }

        public MotionEvent getLastInjectedHoverEventForClick() {
            return mLastInjectedHoverEventForClick;
        }

        public boolean isInjectedPointerDown(int i) {
            boolean flag = true;
            if((flag << i & mInjectedPointersDown) == 0)
                flag = false;
            return flag;
        }

        public void onMotionEvent(MotionEvent motionevent) {
            motionevent.getActionMasked();
            JVM INSTR tableswitch 0 10: default 64
        //                       0 65
        //                       1 95
        //                       2 64
        //                       3 64
        //                       4 64
        //                       5 65
        //                       6 95
        //                       7 134
        //                       8 64
        //                       9 134
        //                       10 134;
               goto _L1 _L2 _L3 _L1 _L1 _L1 _L2 _L3 _L4 _L1 _L4 _L4
_L1:
            return;
_L2:
            mInjectedPointersDown = 1 << motionevent.getPointerId(motionevent.getActionIndex()) | mInjectedPointersDown;
            mLastInjectedDownEventTime = motionevent.getDownTime();
            continue; /* Loop/switch isn't completed */
_L3:
            int i = 1 << motionevent.getPointerId(motionevent.getActionIndex());
            mInjectedPointersDown = mInjectedPointersDown & ~i;
            if(mInjectedPointersDown == 0)
                mLastInjectedDownEventTime = 0L;
            continue; /* Loop/switch isn't completed */
_L4:
            if(mLastInjectedHoverEvent != null)
                mLastInjectedHoverEvent.recycle();
            mLastInjectedHoverEvent = MotionEvent.obtain(motionevent);
            if(mLastInjectedHoverEventForClick != null)
                mLastInjectedHoverEventForClick.recycle();
            mLastInjectedHoverEventForClick = MotionEvent.obtain(motionevent);
            if(true) goto _L1; else goto _L5
_L5:
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("=========================");
            stringbuilder.append("\nDown pointers #");
            stringbuilder.append(Integer.bitCount(mInjectedPointersDown));
            stringbuilder.append(" [ ");
            for(int i = 0; i < 32; i++)
                if((i & mInjectedPointersDown) != 0) {
                    stringbuilder.append(i);
                    stringbuilder.append(" ");
                }

            stringbuilder.append("]");
            stringbuilder.append("\n=========================");
            return stringbuilder.toString();
        }

        private static final String LOG_TAG_INJECTED_POINTER_TRACKER = "InjectedPointerTracker";
        private int mInjectedPointersDown;
        private long mLastInjectedDownEventTime;
        private MotionEvent mLastInjectedHoverEvent;
        private MotionEvent mLastInjectedHoverEventForClick;
        final TouchExplorer this$0;



/*
        static MotionEvent access$302(InjectedPointerTracker injectedpointertracker, MotionEvent motionevent) {
            injectedpointertracker.mLastInjectedHoverEventForClick = motionevent;
            return motionevent;
        }

*/

        InjectedPointerTracker() {
            this$0 = TouchExplorer.this;
            super();
        }
    }

    class SendHoverDelayed
        implements Runnable {

        private void clear() {
            if(isPending()) {
                mPrototype.recycle();
                mPrototype = null;
                mPointerIdBits = -1;
                mPolicyFlags = 0;
            }
        }

        private boolean isPending() {
            boolean flag;
            if(mPrototype != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public void forceSendAndRemove() {
            if(isPending()) {
                run();
                remove();
            }
        }

        public float getX() {
            float f;
            if(isPending())
                f = mPrototype.getX();
            else
                f = 0.0F;
            return f;
        }

        public float getY() {
            float f;
            if(isPending())
                f = mPrototype.getY();
            else
                f = 0.0F;
            return f;
        }

        public void post(MotionEvent motionevent, int i, int j) {
            remove();
            mPrototype = MotionEvent.obtain(motionevent);
            mPointerIdBits = i;
            mPolicyFlags = j;
            mHandler.postDelayed(this, mDetermineUserIntentTimeout);
        }

        public void remove() {
            mHandler.removeCallbacks(this);
            clear();
        }

        public void run() {
            if(mGestureStarted)
                mAms.touchExplorationGestureStarted();
            else
                mAms.touchExplorationGestureEnded();
            sendMotionEvent(mPrototype, mHoverAction, mPointerIdBits, mPolicyFlags);
            clear();
        }

        private final String LOG_TAG_SEND_HOVER_DELAYED = com/android/server/accessibility/TouchExplorer$SendHoverDelayed.getName();
        private final boolean mGestureStarted;
        private final int mHoverAction;
        private int mPointerIdBits;
        private int mPolicyFlags;
        private MotionEvent mPrototype;
        final TouchExplorer this$0;


        public SendHoverDelayed(int i, boolean flag) {
            this$0 = TouchExplorer.this;
            super();
            mHoverAction = i;
            mGestureStarted = flag;
        }
    }

    private final class PerformLongPressDelayed
        implements Runnable {

        private void clear() {
            if(isPenidng()) {
                mEvent.recycle();
                mEvent = null;
                mPolicyFlags = 0;
            }
        }

        private boolean isPenidng() {
            boolean flag;
            if(mEvent != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public void post(MotionEvent motionevent, int i) {
            mEvent = MotionEvent.obtain(motionevent);
            mPolicyFlags = i;
            mHandler.postDelayed(this, ViewConfiguration.getLongPressTimeout());
        }

        public void remove() {
            if(isPenidng()) {
                mHandler.removeCallbacks(this);
                clear();
            }
        }

        public void run() {
            if(mReceivedPointerTracker.getActivePointerCount() != 0) goto _L2; else goto _L1
_L1:
            return;
_L2:
            int i;
            int j;
            MotionEvent motionevent;
            int l;
            int i1;
            i = mEvent.getPointerId(mEvent.getActionIndex());
            j = mEvent.findPointerIndex(i);
            motionevent = mInjectedPointerTracker.getLastInjectedHoverEventForClick();
            if(motionevent != null)
                break; /* Loop/switch isn't completed */
            Rect rect2 = mTempRect;
            if(!mAms.getAccessibilityFocusBoundsInActiveWindow(rect2))
                continue; /* Loop/switch isn't completed */
            l = rect2.centerX();
            i1 = rect2.centerY();
_L4:
            mLongPressingPointerId = i;
            mLongPressingPointerDeltaX = (int)mEvent.getX(j) - l;
            mLongPressingPointerDeltaY = (int)mEvent.getY(j) - i1;
            sendExitEventsIfNeeded(mPolicyFlags);
            mCurrentState = 4;
            sendDownForAllActiveNotInjectedPointers(mEvent, mPolicyFlags);
            clear();
            if(true) goto _L1; else goto _L3
_L3:
            int k = motionevent.getActionIndex();
            l = (int)motionevent.getX(k);
            i1 = (int)motionevent.getY(k);
            Rect rect = mTempRect;
            if(mLastTouchedWindowId == mAms.getActiveWindowId()) {
                mAms.getActiveWindowBounds(rect);
                if(rect.contains(l, i1)) {
                    Rect rect1 = mTempRect;
                    if(mAms.getAccessibilityFocusBoundsInActiveWindow(rect1) && !rect1.contains(l, i1)) {
                        l = rect1.centerX();
                        i1 = rect1.centerY();
                    }
                }
            }
              goto _L4
            if(true) goto _L1; else goto _L5
_L5:
        }

        private MotionEvent mEvent;
        private int mPolicyFlags;
        final TouchExplorer this$0;

        private PerformLongPressDelayed() {
            this$0 = TouchExplorer.this;
            super();
        }

    }

    private final class ExitGestureDetectionModeDelayed
        implements Runnable {

        public void post() {
            mHandler.postDelayed(this, 2000L);
        }

        public void remove() {
            mHandler.removeCallbacks(this);
        }

        public void run() {
            clear();
        }

        final TouchExplorer this$0;

        private ExitGestureDetectionModeDelayed() {
            this$0 = TouchExplorer.this;
            super();
        }

    }

    private class DoubleTapDetector {

        private boolean eventsWithinTimeoutAndDistance(MotionEvent motionevent, MotionEvent motionevent1, int i, int j) {
            boolean flag = false;
            if(!isTimedOut(motionevent, motionevent1, i)) goto _L2; else goto _L1
_L1:
            return flag;
_L2:
            int k = motionevent.getActionIndex();
            int l = motionevent1.getActionIndex();
            float f = motionevent1.getX(l) - motionevent.getX(k);
            float f1 = motionevent1.getY(l) - motionevent.getY(k);
            if(Math.hypot(f, f1) < (double)j)
                flag = true;
            if(true) goto _L1; else goto _L3
_L3:
        }

        private boolean isDoubleTap(MotionEvent motionevent, MotionEvent motionevent1) {
            return eventsWithinTimeoutAndDistance(motionevent, motionevent1, mDoubleTapTimeout, mDoubleTapSlop);
        }

        private boolean isSamePointerContext(MotionEvent motionevent, MotionEvent motionevent1) {
            boolean flag;
            if(motionevent.getPointerIdBits() == motionevent1.getPointerIdBits() && motionevent.getPointerId(motionevent.getActionIndex()) == motionevent1.getPointerId(motionevent1.getActionIndex()))
                flag = true;
            else
                flag = false;
            return flag;
        }

        private boolean isTimedOut(MotionEvent motionevent, MotionEvent motionevent1, int i) {
            boolean flag;
            if(motionevent1.getEventTime() - motionevent.getEventTime() >= (long)i)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public void clear() {
            if(mDownEvent != null) {
                mDownEvent.recycle();
                mDownEvent = null;
            }
            if(mFirstTapEvent != null) {
                mFirstTapEvent.recycle();
                mFirstTapEvent = null;
            }
        }

        public boolean firstTapDetected() {
            boolean flag;
            if(mFirstTapEvent != null && SystemClock.uptimeMillis() - mFirstTapEvent.getEventTime() < (long)mDoubleTapTimeout)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isTap(MotionEvent motionevent, MotionEvent motionevent1) {
            return eventsWithinTimeoutAndDistance(motionevent, motionevent1, mTapTimeout, mTouchSlop);
        }

        public void onDoubleTap(MotionEvent motionevent, int i) {
            if(motionevent.getPointerCount() <= 2) goto _L2; else goto _L1
_L1:
            return;
_L2:
            int j;
            MotionEvent motionevent1;
            int l;
            int i1;
            mSendHoverEnterDelayed.remove();
            mSendHoverExitDelayed.remove();
            mPerformLongPressDelayed.remove();
            sendExitEventsIfNeeded(i);
            j = motionevent.findPointerIndex(motionevent.getPointerId(motionevent.getActionIndex()));
            motionevent1 = mInjectedPointerTracker.getLastInjectedHoverEventForClick();
            if(motionevent1 != null)
                break; /* Loop/switch isn't completed */
            Rect rect2 = mTempRect;
            if(!mAms.getAccessibilityFocusBoundsInActiveWindow(rect2))
                continue; /* Loop/switch isn't completed */
            l = rect2.centerX();
            i1 = rect2.centerY();
_L4:
            android.view.MotionEvent.PointerProperties apointerproperties[] = new android.view.MotionEvent.PointerProperties[1];
            apointerproperties[0] = new android.view.MotionEvent.PointerProperties();
            motionevent.getPointerProperties(j, apointerproperties[0]);
            android.view.MotionEvent.PointerCoords apointercoords[] = new android.view.MotionEvent.PointerCoords[1];
            apointercoords[0] = new android.view.MotionEvent.PointerCoords();
            apointercoords[0].x = l;
            apointercoords[0].y = i1;
            MotionEvent motionevent2 = MotionEvent.obtain(motionevent.getDownTime(), motionevent.getEventTime(), 0, 1, apointerproperties, apointercoords, 0, 0, 1.0F, 1.0F, motionevent.getDeviceId(), 0, motionevent.getSource(), motionevent.getFlags());
            sendActionDownAndUp(motionevent2, i);
            motionevent2.recycle();
            if(true) goto _L1; else goto _L3
_L3:
            int k = motionevent1.getActionIndex();
            l = (int)motionevent1.getX(k);
            i1 = (int)motionevent1.getY(k);
            Rect rect = mTempRect;
            if(mLastTouchedWindowId == mAms.getActiveWindowId()) {
                mAms.getActiveWindowBounds(rect);
                if(rect.contains(l, i1)) {
                    Rect rect1 = mTempRect;
                    if(mAms.getAccessibilityFocusBoundsInActiveWindow(rect1) && !rect1.contains(l, i1)) {
                        l = rect1.centerX();
                        i1 = rect1.centerY();
                    }
                }
            }
              goto _L4
            if(true) goto _L1; else goto _L5
_L5:
        }

        public void onMotionEvent(MotionEvent motionevent, int i) {
            motionevent.getActionMasked();
            JVM INSTR tableswitch 0 6: default 48
        //                       0 49
        //                       1 83
        //                       2 48
        //                       3 48
        //                       4 48
        //                       5 49
        //                       6 83;
               goto _L1 _L2 _L3 _L1 _L1 _L1 _L2 _L3
_L1:
            return;
_L2:
            if(mFirstTapEvent != null && !isSamePointerContext(mFirstTapEvent, motionevent))
                clear();
            mDownEvent = MotionEvent.obtain(motionevent);
            continue; /* Loop/switch isn't completed */
_L3:
            if(mDownEvent == null)
                continue; /* Loop/switch isn't completed */
            if(!isSamePointerContext(mDownEvent, motionevent)) {
                clear();
                continue; /* Loop/switch isn't completed */
            }
            if(!isTap(mDownEvent, motionevent))
                break; /* Loop/switch isn't completed */
            if(mFirstTapEvent == null || isTimedOut(mFirstTapEvent, motionevent, mDoubleTapTimeout)) {
                mFirstTapEvent = MotionEvent.obtain(motionevent);
                mDownEvent.recycle();
                mDownEvent = null;
                continue; /* Loop/switch isn't completed */
            }
            if(isDoubleTap(mFirstTapEvent, motionevent)) {
                onDoubleTap(motionevent, i);
                mFirstTapEvent.recycle();
                mFirstTapEvent = null;
                mDownEvent.recycle();
                mDownEvent = null;
                continue; /* Loop/switch isn't completed */
            }
            mFirstTapEvent.recycle();
            mFirstTapEvent = null;
_L6:
            mDownEvent.recycle();
            mDownEvent = null;
            if(true) goto _L1; else goto _L4
_L4:
            if(mFirstTapEvent == null) goto _L6; else goto _L5
_L5:
            mFirstTapEvent.recycle();
            mFirstTapEvent = null;
              goto _L6
        }

        private MotionEvent mDownEvent;
        private MotionEvent mFirstTapEvent;
        final TouchExplorer this$0;

        private DoubleTapDetector() {
            this$0 = TouchExplorer.this;
            super();
        }

    }


    public TouchExplorer(InputFilter inputfilter, Context context, AccessibilityManagerService accessibilitymanagerservice) {
        mCurrentState = 1;
        mAms = accessibilitymanagerservice;
        mReceivedPointerTracker = new ReceivedPointerTracker(context);
        mInputFilter = inputfilter;
        mDetermineUserIntentTimeout = (int)(1.5F * (float)mTapTimeout);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDoubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        mHandler = new Handler(context.getMainLooper());
        mGestureLibrary = GestureLibraries.fromRawResource(context, 0x1100000);
        mGestureLibrary.setOrientationStyle(8);
        mGestureLibrary.setSequenceType(2);
        mGestureLibrary.load();
        float f = context.getResources().getDisplayMetrics().density;
        mScaledMinPointerDistanceToUseMiddleLocation = (int)(200F * f);
        mScaledGestureDetectionVelocity = (int)(1000F * f);
    }

    private int computeInjectionAction(int i, int j) {
        i;
        JVM INSTR lookupswitch 3: default 36
    //                   0: 38
    //                   5: 38
    //                   6: 63;
           goto _L1 _L2 _L2 _L3
_L1:
        return i;
_L2:
        if(mInjectedPointerTracker.getInjectedPointerDownCount() == 0)
            i = 0;
        else
            i = 5 | j << 8;
        continue; /* Loop/switch isn't completed */
_L3:
        if(mInjectedPointerTracker.getInjectedPointerDownCount() == 1)
            i = 1;
        else
            i = 6 | j << 8;
        if(true) goto _L1; else goto _L4
_L4:
    }

    private int getNotInjectedActivePointerCount(ReceivedPointerTracker receivedpointertracker, InjectedPointerTracker injectedpointertracker) {
        return Integer.bitCount(receivedpointertracker.getActivePointers() & (-1 ^ injectedpointertracker.getInjectedPointersDown()));
    }

    private static String getStateSymbolicName(int i) {
        i;
        JVM INSTR tableswitch 1 5: default 36
    //                   1 64
    //                   2 70
    //                   3 36
    //                   4 77
    //                   5 84;
           goto _L1 _L2 _L3 _L1 _L4 _L5
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown state: ").append(i).toString());
_L2:
        String s = "STATE_TOUCH_EXPLORING";
_L7:
        return s;
_L3:
        s = "STATE_DRAGGING";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "STATE_DELEGATING";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "STATE_GESTURE_DETECTING";
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void handleMotionEventGestureDetecting(MotionEvent motionevent, int i) {
        motionevent.getActionMasked();
        JVM INSTR tableswitch 0 3: default 36
    //                   0 37
    //                   1 179
    //                   2 87
    //                   3 356;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return;
_L2:
        float f6 = motionevent.getX();
        float f7 = motionevent.getY();
        mPreviousX = f6;
        mPreviousY = f7;
        mStrokeBuffer.add(new GesturePoint(f6, f7, motionevent.getEventTime()));
        continue; /* Loop/switch isn't completed */
_L4:
        float f2 = motionevent.getX();
        float f3 = motionevent.getY();
        float f4 = Math.abs(f2 - mPreviousX);
        float f5 = Math.abs(f3 - mPreviousY);
        if(f4 >= 3F || f5 >= 3F) {
            mPreviousX = f2;
            mPreviousY = f3;
            mStrokeBuffer.add(new GesturePoint(f2, f3, motionevent.getEventTime()));
        }
        continue; /* Loop/switch isn't completed */
_L3:
        float f = motionevent.getX();
        float f1 = motionevent.getY();
        mStrokeBuffer.add(new GesturePoint(f, f1, motionevent.getEventTime()));
        Gesture gesture = new Gesture();
        gesture.addStroke(new GestureStroke(mStrokeBuffer));
        ArrayList arraylist = mGestureLibrary.recognize(gesture);
        if(!arraylist.isEmpty()) {
            Prediction prediction = (Prediction)arraylist.get(0);
            if(prediction.score >= 2D)
                try {
                    int j = Integer.parseInt(prediction.name);
                    mAms.onGesture(j);
                }
                catch(NumberFormatException numberformatexception) {
                    Slog.w("TouchExplorer", (new StringBuilder()).append("Non numeric gesture id:").append(prediction.name).toString());
                }
        }
        mStrokeBuffer.clear();
        mExitGestureDetectionModeDelayed.remove();
        mCurrentState = 1;
        continue; /* Loop/switch isn't completed */
_L5:
        clear(motionevent, i);
        if(true) goto _L1; else goto _L6
_L6:
    }

    private void handleMotionEventStateDelegating(MotionEvent motionevent, int i) {
        motionevent.getActionMasked();
        JVM INSTR tableswitch 0 6: default 48
    //                   0 55
    //                   1 93
    //                   2 66
    //                   3 127
    //                   4 48
    //                   5 48
    //                   6 93;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L1 _L3
_L1:
        sendMotionEventStripInactivePointers(motionevent, i);
        return;
_L2:
        throw new IllegalStateException("Delegating state can only be reached if there is at least one pointer down!");
_L4:
        if(getNotInjectedActivePointerCount(mReceivedPointerTracker, mInjectedPointerTracker) > 0)
            sendDownForAllActiveNotInjectedPointers(MotionEvent.obtain(motionevent), i);
        continue; /* Loop/switch isn't completed */
_L3:
        mLongPressingPointerId = -1;
        mLongPressingPointerDeltaX = 0;
        mLongPressingPointerDeltaY = 0;
        if(mReceivedPointerTracker.getActivePointerCount() == 0)
            mCurrentState = 1;
        continue; /* Loop/switch isn't completed */
_L5:
        clear(motionevent, i);
        if(true) goto _L1; else goto _L6
_L6:
    }

    private void handleMotionEventStateDragging(MotionEvent motionevent, int i) {
        int j = 1 << mDraggingPointerId;
        motionevent.getActionMasked();
        JVM INSTR tableswitch 0 6: default 52
    //                   0 53
    //                   1 335
    //                   2 86
    //                   3 343
    //                   4 52
    //                   5 64
    //                   6 290;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L6 _L7
_L1:
        return;
_L2:
        throw new IllegalStateException("Dragging state can be reached only if two pointers are already down");
_L6:
        mCurrentState = 4;
        sendMotionEvent(motionevent, 1, j, i);
        sendDownForAllActiveNotInjectedPointers(motionevent, i);
        continue; /* Loop/switch isn't completed */
_L4:
        switch(mReceivedPointerTracker.getActivePointerCount()) {
        default:
            mCurrentState = 4;
            sendMotionEvent(motionevent, 1, j, i);
            sendDownForAllActiveNotInjectedPointers(motionevent, i);
            break;

        case 2: // '\002'
            if(isDraggingGesture(motionevent)) {
                int ai[] = mTempPointerIds;
                mReceivedPointerTracker.populateActivePointerIds(ai);
                int k = motionevent.findPointerIndex(ai[0]);
                int l = motionevent.findPointerIndex(ai[1]);
                float f = motionevent.getX(k);
                float f1 = motionevent.getY(k);
                float f2 = motionevent.getX(l);
                float f3 = motionevent.getY(l);
                float f4 = f - f2;
                float f5 = f1 - f3;
                if(Math.hypot(f4, f5) > (double)mScaledMinPointerDistanceToUseMiddleLocation)
                    motionevent.setLocation(f4 / 2.0F, f5 / 2.0F);
                sendMotionEvent(motionevent, 2, j, i);
            } else {
                mCurrentState = 4;
                sendMotionEvent(motionevent, 1, j, i);
                sendDownForAllActiveNotInjectedPointers(motionevent, i);
            }
            break;

        case 1: // '\001'
            break;
        }
        if(false)
            ;
        continue; /* Loop/switch isn't completed */
_L7:
        switch(mReceivedPointerTracker.getActivePointerCount()) {
        default:
            mCurrentState = 1;
            break;

        case 1: // '\001'
            sendMotionEvent(motionevent, 1, j, i);
            break;
        }
        if(false)
            ;
        continue; /* Loop/switch isn't completed */
_L3:
        mCurrentState = 1;
        continue; /* Loop/switch isn't completed */
_L5:
        clear(motionevent, i);
        if(true) goto _L1; else goto _L8
_L8:
    }

    private void handleMotionEventStateTouchExploring(MotionEvent motionevent, int i) {
        ReceivedPointerTracker receivedpointertracker;
        int j;
        receivedpointertracker = mReceivedPointerTracker;
        j = receivedpointertracker.getActivePointerCount();
        if(mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(motionevent);
        mDoubleTapDetector.onMotionEvent(motionevent, i);
        motionevent.getActionMasked();
        JVM INSTR tableswitch 0 6: default 88
    //                   0 89
    //                   1 670
    //                   2 209
    //                   3 772
    //                   4 88
    //                   5 95
    //                   6 677;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L6 _L7
_L11:
        return;
_L2:
        handleMotionEventGestureDetecting(motionevent, i);
_L6:
        switch(j) {
        case 0: // '\0'
            throw new IllegalStateException("The must always be one active pointer intouch exploring state!");

        case 1: // '\001'
            if(mSendHoverEnterDelayed.isPending()) {
                mSendHoverEnterDelayed.remove();
                mSendHoverExitDelayed.remove();
            }
            mPerformLongPressDelayed.remove();
            if(mDoubleTapDetector.firstTapDetected()) {
                mPerformLongPressDelayed.post(motionevent, i);
            } else {
                int k1 = 1 << receivedpointertracker.getPrimaryActivePointerId();
                mSendHoverEnterDelayed.post(motionevent, k1, i);
            }
            break;
        }
_L1:
        if(false)
            ;
        continue; /* Loop/switch isn't completed */
_L4:
        int l = receivedpointertracker.getPrimaryActivePointerId();
        int i1 = motionevent.findPointerIndex(l);
        int j1 = 1 << l;
        switch(j) {
        default:
            float f;
            float f1;
            float f2;
            float f3;
            if(mSendHoverEnterDelayed.isPending()) {
                mSendHoverEnterDelayed.remove();
                mSendHoverExitDelayed.remove();
                mPerformLongPressDelayed.remove();
            } else {
                mPerformLongPressDelayed.remove();
                sendExitEventsIfNeeded(i);
            }
            mCurrentState = 4;
            sendDownForAllActiveNotInjectedPointers(motionevent, i);
            continue; /* Loop/switch isn't completed */

        case 0: // '\0'
            continue; /* Loop/switch isn't completed */

        case 1: // '\001'
            if(mSendHoverEnterDelayed.isPending()) {
                handleMotionEventGestureDetecting(motionevent, i);
                f2 = receivedpointertracker.getReceivedPointerDownX(l) - motionevent.getX(i1);
                f3 = receivedpointertracker.getReceivedPointerDownY(l) - motionevent.getY(i1);
                if(Math.hypot(f2, f3) > (double)mDoubleTapSlop) {
                    mVelocityTracker.computeCurrentVelocity(1000);
                    if(Math.max(Math.abs(mVelocityTracker.getXVelocity(l)), Math.abs(mVelocityTracker.getYVelocity(l))) > (float)mScaledGestureDetectionVelocity) {
                        mCurrentState = 5;
                        mSendHoverEnterDelayed.remove();
                        mSendHoverExitDelayed.remove();
                        mPerformLongPressDelayed.remove();
                        mExitGestureDetectionModeDelayed.post();
                    } else {
                        mSendHoverEnterDelayed.forceSendAndRemove();
                        mSendHoverExitDelayed.remove();
                        mPerformLongPressDelayed.remove();
                        sendMotionEvent(motionevent, 7, j1, i);
                    }
                }
            } else
            if(!mDoubleTapDetector.firstTapDetected()) {
                sendEnterEventsIfNeeded(i);
                sendMotionEvent(motionevent, 7, j1, i);
            }
            continue; /* Loop/switch isn't completed */

        case 2: // '\002'
            break;
        }
        if(mSendHoverEnterDelayed.isPending()) {
            mSendHoverEnterDelayed.remove();
            mSendHoverExitDelayed.remove();
            mPerformLongPressDelayed.remove();
        } else {
            mPerformLongPressDelayed.remove();
            f = receivedpointertracker.getReceivedPointerDownX(l) - motionevent.getX(i1);
            f1 = receivedpointertracker.getReceivedPointerDownY(l) - motionevent.getY(i1);
            if(Math.hypot(f, f1) < (double)mDoubleTapSlop)
                continue; /* Loop/switch isn't completed */
            sendExitEventsIfNeeded(i);
        }
        mStrokeBuffer.clear();
        if(isDraggingGesture(motionevent)) {
            mCurrentState = 2;
            mDraggingPointerId = l;
            sendMotionEvent(motionevent, 0, j1, i);
        } else {
            mCurrentState = 4;
            sendDownForAllActiveNotInjectedPointers(motionevent, i);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        mStrokeBuffer.clear();
_L7:
        int k = 1 << receivedpointertracker.getLastReceivedUpPointerId();
        j;
        JVM INSTR tableswitch 0 0: default 704
    //                   0 726;
           goto _L8 _L9
_L8:
        if(mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker = null;
        }
        continue; /* Loop/switch isn't completed */
_L9:
        if(receivedpointertracker.wasLastReceivedUpPointerActive()) {
            mPerformLongPressDelayed.remove();
            if(mSendHoverEnterDelayed.isPending())
                mSendHoverExitDelayed.post(motionevent, k, i);
            else
                sendExitEventsIfNeeded(i);
        }
          goto _L8
_L5:
        clear(motionevent, i);
        if(true) goto _L11; else goto _L10
_L10:
    }

    private boolean isDraggingGesture(MotionEvent motionevent) {
        ReceivedPointerTracker receivedpointertracker = mReceivedPointerTracker;
        int ai[] = mTempPointerIds;
        receivedpointertracker.populateActivePointerIds(ai);
        int i = motionevent.findPointerIndex(ai[0]);
        int j = motionevent.findPointerIndex(ai[1]);
        float f = motionevent.getX(i);
        float f1 = motionevent.getY(i);
        float f2 = motionevent.getX(j);
        float f3 = motionevent.getY(j);
        float f4 = f - receivedpointertracker.getReceivedPointerDownX(i);
        float f5 = f1 - receivedpointertracker.getReceivedPointerDownY(i);
        boolean flag;
        if(f4 == 0.0F && f5 == 0.0F) {
            flag = true;
        } else {
            float f6 = (float)Math.sqrt(f4 * f4 + f5 * f5);
            float f7;
            float f8;
            float f9;
            float f10;
            if(f6 > 0.0F)
                f7 = f4 / f6;
            else
                f7 = f4;
            if(f6 > 0.0F)
                f8 = f5 / f6;
            else
                f8 = f5;
            f9 = f2 - receivedpointertracker.getReceivedPointerDownX(j);
            f10 = f3 - receivedpointertracker.getReceivedPointerDownY(j);
            if(f9 == 0.0F && f10 == 0.0F) {
                flag = true;
            } else {
                float f11 = (float)Math.sqrt(f9 * f9 + f10 * f10);
                float f12;
                float f13;
                if(f11 > 0.0F)
                    f12 = f9 / f11;
                else
                    f12 = f9;
                if(f11 > 0.0F)
                    f13 = f10 / f11;
                else
                    f13 = f10;
                if(f7 * f12 + f8 * f13 < 0.525322F)
                    flag = false;
                else
                    flag = true;
            }
        }
        return flag;
    }

    private void sendActionDownAndUp(MotionEvent motionevent, int i) {
        int j = 1 << motionevent.getPointerId(motionevent.getActionIndex());
        sendMotionEvent(motionevent, 0, j, i);
        sendMotionEvent(motionevent, 1, j, i);
    }

    private void sendDownForAllActiveNotInjectedPointers(MotionEvent motionevent, int i) {
        ReceivedPointerTracker receivedpointertracker = mReceivedPointerTracker;
        InjectedPointerTracker injectedpointertracker = mInjectedPointerTracker;
        int j = 0;
        int k = motionevent.getPointerCount();
        for(int l = 0; l < k; l++) {
            int k1 = motionevent.getPointerId(l);
            if(injectedpointertracker.isInjectedPointerDown(k1))
                j |= 1 << k1;
        }

        int i1 = 0;
        do {
            if(i1 >= k)
                break;
            int j1 = motionevent.getPointerId(i1);
            if(receivedpointertracker.isActivePointer(j1) && !injectedpointertracker.isInjectedPointerDown(j1)) {
                j |= 1 << j1;
                sendMotionEvent(motionevent, computeInjectionAction(0, i1), j, i);
            }
            i1++;
        } while(true);
    }

    private void sendEnterEventsIfNeeded(int i) {
        MotionEvent motionevent = mInjectedPointerTracker.getLastInjectedHoverEvent();
        if(motionevent != null && motionevent.getActionMasked() == 10) {
            int j = motionevent.getPointerIdBits();
            mAms.touchExplorationGestureStarted();
            sendMotionEvent(motionevent, 9, j, i);
        }
    }

    private void sendExitEventsIfNeeded(int i) {
        MotionEvent motionevent = mInjectedPointerTracker.getLastInjectedHoverEvent();
        if(motionevent != null && motionevent.getActionMasked() != 10) {
            int j = motionevent.getPointerIdBits();
            mAms.touchExplorationGestureEnded();
            sendMotionEvent(motionevent, 10, j, i);
        }
    }

    private void sendMotionEvent(MotionEvent motionevent, int i, int j, int k) {
        motionevent.setAction(i);
        MotionEvent motionevent1;
        if(j == -1)
            motionevent1 = motionevent;
        else
            motionevent1 = motionevent.split(j);
        if(i == 0) {
            long l2 = motionevent1.getEventTime();
            motionevent1.setDownTime(l2);
        } else {
            long l = mInjectedPointerTracker.getLastInjectedDownEventTime();
            motionevent1.setDownTime(l);
        }
        if(mLongPressingPointerId >= 0) {
            int j1 = mLongPressingPointerId;
            int k1 = motionevent1.findPointerIndex(j1);
            int l1 = motionevent1.getPointerCount();
            android.view.MotionEvent.PointerProperties apointerproperties[] = android.view.MotionEvent.PointerProperties.createArray(l1);
            android.view.MotionEvent.PointerCoords apointercoords[] = android.view.MotionEvent.PointerCoords.createArray(l1);
            for(int i2 = 0; i2 < l1; i2++) {
                android.view.MotionEvent.PointerProperties pointerproperties = apointerproperties[i2];
                motionevent1.getPointerProperties(i2, pointerproperties);
                android.view.MotionEvent.PointerCoords pointercoords = apointercoords[i2];
                motionevent1.getPointerCoords(i2, pointercoords);
                if(i2 == k1) {
                    android.view.MotionEvent.PointerCoords pointercoords1 = apointercoords[i2];
                    pointercoords1.x = pointercoords1.x - (float)mLongPressingPointerDeltaX;
                    android.view.MotionEvent.PointerCoords pointercoords2 = apointercoords[i2];
                    pointercoords2.y = pointercoords2.y - (float)mLongPressingPointerDeltaY;
                }
            }

            MotionEvent motionevent2 = MotionEvent.obtain(motionevent1.getDownTime(), motionevent1.getEventTime(), motionevent1.getAction(), motionevent1.getPointerCount(), apointerproperties, apointercoords, motionevent1.getMetaState(), motionevent1.getButtonState(), 1.0F, 1.0F, motionevent1.getDeviceId(), motionevent1.getEdgeFlags(), motionevent1.getSource(), motionevent1.getFlags());
            if(motionevent1 != motionevent)
                motionevent1.recycle();
            motionevent1 = motionevent2;
        }
        int i1 = k | 0x40000000;
        mInputFilter.sendInputEvent(motionevent1, i1);
        mInjectedPointerTracker.onMotionEvent(motionevent1);
        if(motionevent1 != motionevent)
            motionevent1.recycle();
    }

    private void sendMotionEventStripInactivePointers(MotionEvent motionevent, int i) {
        ReceivedPointerTracker receivedpointertracker = mReceivedPointerTracker;
        if(motionevent.getPointerCount() != receivedpointertracker.getActivePointerCount()) goto _L2; else goto _L1
_L1:
        sendMotionEvent(motionevent, motionevent.getAction(), -1, i);
_L4:
        return;
_L2:
        if(receivedpointertracker.getActivePointerCount() != 0 || receivedpointertracker.wasLastReceivedUpPointerActive()) {
            int j = motionevent.getActionMasked();
            int k = motionevent.getPointerId(motionevent.getActionIndex());
            if(j == 2 || receivedpointertracker.isActiveOrWasLastActiveUpPointer(k)) {
                int l = 0;
                int i1 = motionevent.getPointerCount();
                for(int j1 = 0; j1 < i1; j1++) {
                    int k1 = motionevent.getPointerId(j1);
                    if(receivedpointertracker.isActiveOrWasLastActiveUpPointer(k1))
                        l |= 1 << k1;
                }

                sendMotionEvent(motionevent, motionevent.getAction(), l, i);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void sendUpForInjectedDownPointers(MotionEvent motionevent, int i) {
        InjectedPointerTracker injectedpointertracker = mInjectedPointerTracker;
        int j = 0;
        int k = motionevent.getPointerCount();
        int l = 0;
        while(l < k)  {
            int i1 = motionevent.getPointerId(l);
            if(injectedpointertracker.isInjectedPointerDown(i1)) {
                j |= 1 << i1;
                sendMotionEvent(motionevent, computeInjectionAction(1, l), j, i);
            }
            l++;
        }
    }

    public void clear() {
        if(mReceivedPointerTracker.getLastReceivedEvent() != null)
            clear(mReceivedPointerTracker.getLastReceivedEvent(), 0x2000000);
    }

    public void clear(MotionEvent motionevent, int i) {
        mCurrentState;
        JVM INSTR tableswitch 1 5: default 40
    //                   1 111
    //                   2 119
    //                   3 40
    //                   4 134
    //                   5 143;
           goto _L1 _L2 _L3 _L1 _L4 _L5
_L1:
        mSendHoverEnterDelayed.remove();
        mSendHoverExitDelayed.remove();
        mPerformLongPressDelayed.remove();
        mExitGestureDetectionModeDelayed.remove();
        mReceivedPointerTracker.clear();
        mInjectedPointerTracker.clear();
        mDoubleTapDetector.clear();
        mLongPressingPointerId = -1;
        mLongPressingPointerDeltaX = 0;
        mLongPressingPointerDeltaY = 0;
        mCurrentState = 1;
        return;
_L2:
        sendExitEventsIfNeeded(i);
        continue; /* Loop/switch isn't completed */
_L3:
        mDraggingPointerId = -1;
        sendUpForInjectedDownPointers(motionevent, i);
        continue; /* Loop/switch isn't completed */
_L4:
        sendUpForInjectedDownPointers(motionevent, i);
        continue; /* Loop/switch isn't completed */
_L5:
        mStrokeBuffer.clear();
        if(true) goto _L1; else goto _L6
_L6:
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityevent) {
        accessibilityevent.getEventType();
        JVM INSTR lookupswitch 4: default 48
    //                   32: 49
    //                   128: 87
    //                   256: 87
    //                   32768: 49;
           goto _L1 _L2 _L3 _L3 _L2
_L1:
        return;
_L2:
        if(mInjectedPointerTracker.mLastInjectedHoverEventForClick != null) {
            mInjectedPointerTracker.mLastInjectedHoverEventForClick.recycle();
            mInjectedPointerTracker.mLastInjectedHoverEventForClick = null;
        }
        mLastTouchedWindowId = -1;
        continue; /* Loop/switch isn't completed */
_L3:
        mLastTouchedWindowId = accessibilityevent.getWindowId();
        if(true) goto _L1; else goto _L4
_L4:
    }

    public void onMotionEvent(MotionEvent motionevent, int i) {
        mReceivedPointerTracker.onMotionEvent(motionevent);
        mCurrentState;
        JVM INSTR tableswitch 1 5: default 48
    //                   1 79
    //                   2 86
    //                   3 48
    //                   4 95
    //                   5 104;
           goto _L1 _L2 _L3 _L1 _L4 _L5
_L1:
        throw new IllegalStateException((new StringBuilder()).append("Illegal state: ").append(mCurrentState).toString());
_L2:
        handleMotionEventStateTouchExploring(motionevent, i);
_L7:
        return;
_L3:
        handleMotionEventStateDragging(motionevent, i);
        continue; /* Loop/switch isn't completed */
_L4:
        handleMotionEventStateDelegating(motionevent, i);
        continue; /* Loop/switch isn't completed */
_L5:
        handleMotionEventGestureDetecting(motionevent, i);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public String toString() {
        return "TouchExplorer";
    }

    private static final int ALL_POINTER_ID_BITS = -1;
    private static final boolean DEBUG = false;
    private static final int EXIT_GESTURE_DETECTION_TIMEOUT = 2000;
    private static final int GESTURE_DETECTION_VELOCITY_DIP = 1000;
    private static final int INVALID_POINTER_ID = -1;
    private static final String LOG_TAG = "TouchExplorer";
    private static final float MAX_DRAGGING_ANGLE_COS = 0.525322F;
    private static final int MAX_POINTER_COUNT = 32;
    private static final int MIN_POINTER_DISTANCE_TO_USE_MIDDLE_LOCATION_DIP = 200;
    private static final float MIN_PREDICTION_SCORE = 2F;
    private static final int STATE_DELEGATING = 4;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_GESTURE_DETECTING = 5;
    private static final int STATE_TOUCH_EXPLORING = 1;
    private static final int TOUCH_TOLERANCE = 3;
    private final AccessibilityManagerService mAms;
    private int mCurrentState;
    private final int mDetermineUserIntentTimeout;
    private final DoubleTapDetector mDoubleTapDetector = new DoubleTapDetector();
    private final int mDoubleTapSlop;
    private final int mDoubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
    private int mDraggingPointerId;
    private final ExitGestureDetectionModeDelayed mExitGestureDetectionModeDelayed = new ExitGestureDetectionModeDelayed();
    private GestureLibrary mGestureLibrary;
    private final Handler mHandler;
    private final InjectedPointerTracker mInjectedPointerTracker = new InjectedPointerTracker();
    private final InputFilter mInputFilter;
    private int mLastTouchedWindowId;
    private int mLongPressingPointerDeltaX;
    private int mLongPressingPointerDeltaY;
    private int mLongPressingPointerId;
    private final PerformLongPressDelayed mPerformLongPressDelayed = new PerformLongPressDelayed();
    private float mPreviousX;
    private float mPreviousY;
    private final ReceivedPointerTracker mReceivedPointerTracker;
    private final int mScaledGestureDetectionVelocity;
    private final int mScaledMinPointerDistanceToUseMiddleLocation;
    private final SendHoverDelayed mSendHoverEnterDelayed = new SendHoverDelayed(9, true);
    private final SendHoverDelayed mSendHoverExitDelayed = new SendHoverDelayed(10, false);
    private final ArrayList mStrokeBuffer = new ArrayList(100);
    private final int mTapTimeout = ViewConfiguration.getTapTimeout();
    private final int mTempPointerIds[] = new int[32];
    private final Rect mTempRect = new Rect();
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker;












/*
    static int access$2002(TouchExplorer touchexplorer, int i) {
        touchexplorer.mLongPressingPointerId = i;
        return i;
    }

*/


/*
    static int access$2102(TouchExplorer touchexplorer, int i) {
        touchexplorer.mLongPressingPointerDeltaX = i;
        return i;
    }

*/


/*
    static int access$2202(TouchExplorer touchexplorer, int i) {
        touchexplorer.mLongPressingPointerDeltaY = i;
        return i;
    }

*/


/*
    static int access$2302(TouchExplorer touchexplorer, int i) {
        touchexplorer.mCurrentState = i;
        return i;
    }

*/








}
