// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.*;
import android.util.Slog;
import android.util.SparseArray;
import android.view.*;
import android.view.accessibility.*;
import com.android.internal.content.PackageMonitor;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.wm.WindowManagerService;
import java.io.IOException;
import java.util.*;
import org.xmlpull.v1.XmlPullParserException;

// Referenced classes of package com.android.server.accessibility:
//            AccessibilityInputFilter

public class AccessibilityManagerService extends android.view.accessibility.IAccessibilityManager.Stub {
    final class SecurityPolicy {

        private boolean canDispatchAccessibilityEvent(AccessibilityEvent accessibilityevent) {
            boolean flag;
            if(accessibilityevent.getEventType() != 2048 || accessibilityevent.getWindowId() == mActiveWindowId)
                flag = true;
            else
                flag = false;
            return flag;
        }

        private void enforceCallingPermission(String s, String s1) {
            while(AccessibilityManagerService.OWN_PROCESS_ID == Binder.getCallingPid() || mContext.checkCallingPermission(s) == 0) 
                return;
            throw new SecurityException((new StringBuilder()).append("You do not have ").append(s).append(" required to call ").append(s1).toString());
        }

        private int getFocusedWindowId() {
            IBinder ibinder;
            SparseArray sparsearray;
            int j;
            int k;
            ibinder = mWindowManagerService.getFocusedWindowClientToken();
            if(ibinder == null)
                break MISSING_BLOCK_LABEL_64;
            sparsearray = mWindowIdToWindowTokenMap;
            j = sparsearray.size();
            k = 0;
_L3:
            if(k >= j)
                break MISSING_BLOCK_LABEL_64;
            if(sparsearray.valueAt(k) != ibinder) goto _L2; else goto _L1
_L1:
            int i = sparsearray.keyAt(k);
_L4:
            return i;
_L2:
            k++;
              goto _L3
            i = -1;
              goto _L4
        }

        private boolean isActionPermitted(int i) {
            boolean flag;
            if((i & 0x3fff) != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        private boolean isRetrievalAllowingWindow(int i) {
            boolean flag;
            if(mActiveWindowId == i)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean canGetAccessibilityNodeInfoLocked(Service service, int i) {
            boolean flag;
            if(canRetrieveWindowContent(service) && isRetrievalAllowingWindow(i))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean canPerformActionLocked(Service service, int i, int j, Bundle bundle) {
            boolean flag;
            if(canRetrieveWindowContent(service) && isRetrievalAllowingWindow(i) && isActionPermitted(j))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean canRetrieveWindowContent(Service service) {
            return service.mCanRetrieveScreenContent;
        }

        public void enforceCanRetrieveWindowContent(Service service) throws RemoteException {
            if(!canRetrieveWindowContent(service)) {
                Slog.e("AccessibilityManagerService", (new StringBuilder()).append("Accessibility serivce ").append(service.mComponentName).append(" does not ").append("declare android:canRetrieveWindowContent.").toString());
                throw new RemoteException();
            } else {
                return;
            }
        }

        public int getRetrievalAllowingWindowLocked() {
            return mActiveWindowId;
        }

        public void updateActiveWindowAndEventSourceLocked(AccessibilityEvent accessibilityevent) {
            int i;
            int j;
            i = accessibilityevent.getWindowId();
            j = accessibilityevent.getEventType();
            j;
            JVM INSTR lookupswitch 4: default 52
        //                       32: 65
        //                       128: 81
        //                       256: 81
        //                       1024: 89;
               goto _L1 _L2 _L3 _L3 _L4
_L1:
            if((0x1b9bf & j) == 0)
                accessibilityevent.setSource(null);
            return;
_L2:
            if(getFocusedWindowId() == i)
                mActiveWindowId = i;
            continue; /* Loop/switch isn't completed */
_L3:
            mActiveWindowId = i;
            continue; /* Loop/switch isn't completed */
_L4:
            mActiveWindowId = getFocusedWindowId();
            if(true) goto _L1; else goto _L5
_L5:
        }

        private static final int RETRIEVAL_ALLOWING_EVENT_TYPES = 0x1b9bf;
        private static final int VALID_ACTIONS = 16383;
        private int mActiveWindowId;
        final AccessibilityManagerService this$0;




        SecurityPolicy() {
            this$0 = AccessibilityManagerService.this;
            super();
        }
    }

    class Service extends android.accessibilityservice.IAccessibilityServiceConnection.Stub
        implements ServiceConnection, android.os.IBinder.DeathRecipient {

        private void expandStatusBar() {
            long l = Binder.clearCallingIdentity();
            ((StatusBarManager)mContext.getSystemService("statusbar")).expand();
            Binder.restoreCallingIdentity(l);
        }

        private float getCompatibilityScale(int i) {
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i);
            return mWindowManagerService.getWindowCompatibilityScale(ibinder);
        }

        private IAccessibilityInteractionConnection getConnectionLocked(int i) {
            AccessibilityConnectionWrapper accessibilityconnectionwrapper = (AccessibilityConnectionWrapper)mWindowIdToInteractionConnectionWrapperMap.get(i);
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection;
            if(accessibilityconnectionwrapper != null && accessibilityconnectionwrapper.mConnection != null)
                iaccessibilityinteractionconnection = accessibilityconnectionwrapper.mConnection;
            else
                iaccessibilityinteractionconnection = null;
            return iaccessibilityinteractionconnection;
        }

        private void notifyAccessibilityEventInternal(int i) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            IAccessibilityServiceClient iaccessibilityserviceclient;
            AccessibilityEvent accessibilityevent;
            iaccessibilityserviceclient = mServiceInterface;
            if(iaccessibilityserviceclient == null)
                break MISSING_BLOCK_LABEL_180;
            accessibilityevent = (AccessibilityEvent)mPendingEvents.get(i);
            if(accessibilityevent == null)
                break MISSING_BLOCK_LABEL_180;
            break MISSING_BLOCK_LABEL_54;
            Exception exception;
            exception;
            throw exception;
            mPendingEvents.remove(i);
            if(!mSecurityPolicy.canRetrieveWindowContent(this))
                break MISSING_BLOCK_LABEL_110;
            accessibilityevent.setConnectionId(mId);
_L1:
            accessibilityevent.setSealed(true);
            obj;
            JVM INSTR monitorexit ;
            iaccessibilityserviceclient.onAccessibilityEvent(accessibilityevent);
            accessibilityevent.recycle();
            break MISSING_BLOCK_LABEL_180;
            accessibilityevent.setSource(null);
              goto _L1
            RemoteException remoteexception;
            remoteexception;
            Slog.e("AccessibilityManagerService", (new StringBuilder()).append("Error during sending ").append(accessibilityevent).append(" to ").append(iaccessibilityserviceclient).toString(), remoteexception);
            accessibilityevent.recycle();
            break MISSING_BLOCK_LABEL_180;
            Exception exception1;
            exception1;
            accessibilityevent.recycle();
            throw exception1;
        }

        private void notifyGestureInternal(int i) {
            IAccessibilityServiceClient iaccessibilityserviceclient;
            iaccessibilityserviceclient = mServiceInterface;
            if(iaccessibilityserviceclient == null)
                break MISSING_BLOCK_LABEL_16;
            iaccessibilityserviceclient.onGesture(i);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.e("AccessibilityManagerService", (new StringBuilder()).append("Error during sending gesture ").append(i).append(" to ").append(mService).toString(), remoteexception);
              goto _L1
        }

        private void openRecents() {
            long l = Binder.clearCallingIdentity();
            IStatusBarService istatusbarservice = com.android.internal.statusbar.IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            try {
                istatusbarservice.toggleRecentApps();
            }
            catch(RemoteException remoteexception) {
                Slog.e("AccessibilityManagerService", "Error toggling recent apps.");
            }
            Binder.restoreCallingIdentity(l);
        }

        private int resolveAccessibilityWindowId(int i) {
            if(i == -1)
                i = mSecurityPolicy.mActiveWindowId;
            return i;
        }

        private void sendDownAndUpKeyEvents(int i) {
            long l = Binder.clearCallingIdentity();
            long l1 = SystemClock.uptimeMillis();
            KeyEvent keyevent = KeyEvent.obtain(l1, l1, 0, i, 0, 0, -1, 0, 8, 257, null);
            InputManager.getInstance().injectInputEvent(keyevent, 0);
            keyevent.recycle();
            KeyEvent keyevent1 = KeyEvent.obtain(l1, SystemClock.uptimeMillis(), 1, i, 0, 0, -1, 0, 8, 257, null);
            InputManager.getInstance().injectInputEvent(keyevent1, 0);
            keyevent1.recycle();
            Binder.restoreCallingIdentity(l);
        }

        public boolean bind() {
            boolean flag;
            if(!mIsAutomation && mService == null)
                flag = mContext.bindService(mIntent, this, 1);
            else
                flag = false;
            return flag;
        }

        public void binderDied() {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            tryRemoveServiceLocked(this);
            if(mIsAutomation) {
                mUiAutomationService = null;
                populateEnabledAccessibilityServicesLocked();
                populateTouchExplorationGrantedAccessibilityServicesLocked();
                handleAccessibilityEnabledSettingChangedLocked();
                sendStateToClientsLocked();
                handleTouchExplorationEnabledSettingChangedLocked();
                updateInputFilterLocked();
                populateAccessibilityServiceListLocked();
                manageServicesLocked();
            }
            return;
        }

        public void dispose() {
            try {
                mServiceInterface.setConnection(null, mId);
            }
            catch(RemoteException remoteexception) { }
            mService = null;
            mServiceInterface = null;
        }

        public float findAccessibilityNodeInfoByAccessibilityId(int i, long l, int j, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, int k, long l1) throws RemoteException {
            int i1 = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mSecurityPolicy.enforceCanRetrieveWindowContent(this);
            if(mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this, i1)) goto _L2; else goto _L1
_L1:
            float f = 0.0F;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(i1);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            f = 0.0F;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            int j1;
            int k1;
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i1);
            mWindowManagerService.getWindowFrame(ibinder, mTempBounds);
            j1 = mTempBounds.left;
            k1 = mTempBounds.top;
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int i2;
            int j2;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            i2 = k | byte0;
            j2 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.findAccessibilityNodeInfoByAccessibilityId(l, j1, k1, j, iaccessibilityinteractionconnectioncallback, i2, j2, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            f = getCompatibilityScale(i1);
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return f;
        }

        public float findAccessibilityNodeInfoByViewId(int i, long l, int j, int k, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, long l1) throws RemoteException {
            int i1 = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mSecurityPolicy.enforceCanRetrieveWindowContent(this);
            if(mSecurityPolicy.canRetrieveWindowContent(this)) goto _L2; else goto _L1
_L1:
            float f = 0.0F;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(i1);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            f = 0.0F;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            int j1;
            int k1;
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i1);
            mWindowManagerService.getWindowFrame(ibinder, mTempBounds);
            j1 = mTempBounds.left;
            k1 = mTempBounds.top;
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int i2;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            i2 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.findAccessibilityNodeInfoByViewId(l, j, j1, k1, k, iaccessibilityinteractionconnectioncallback, byte0, i2, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            f = getCompatibilityScale(i1);
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return f;
        }

        public float findAccessibilityNodeInfosByText(int i, long l, String s, int j, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, long l1) throws RemoteException {
            int k = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mSecurityPolicy.enforceCanRetrieveWindowContent(this);
            if(mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this, k)) goto _L2; else goto _L1
_L1:
            float f = 0.0F;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(k);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            f = 0.0F;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            int i1;
            int j1;
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(k);
            mWindowManagerService.getWindowFrame(ibinder, mTempBounds);
            i1 = mTempBounds.left;
            j1 = mTempBounds.top;
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int k1;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            k1 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.findAccessibilityNodeInfosByText(l, s, i1, j1, j, iaccessibilityinteractionconnectioncallback, byte0, k1, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            f = getCompatibilityScale(k);
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return f;
        }

        public float findFocus(int i, long l, int j, int k, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, long l1) throws RemoteException {
            int i1 = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mSecurityPolicy.enforceCanRetrieveWindowContent(this);
            if(mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this, i1)) goto _L2; else goto _L1
_L1:
            float f = 0.0F;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(i1);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            f = 0.0F;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            int j1;
            int k1;
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i1);
            mWindowManagerService.getWindowFrame(ibinder, mTempBounds);
            j1 = mTempBounds.left;
            k1 = mTempBounds.top;
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int i2;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            i2 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.findFocus(l, j, j1, k1, k, iaccessibilityinteractionconnectioncallback, byte0, i2, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            f = getCompatibilityScale(i1);
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return f;
        }

        public float focusSearch(int i, long l, int j, int k, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, long l1) throws RemoteException {
            int i1 = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mSecurityPolicy.enforceCanRetrieveWindowContent(this);
            if(mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this, i1)) goto _L2; else goto _L1
_L1:
            float f = 0.0F;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(i1);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            f = 0.0F;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            int j1;
            int k1;
            IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i1);
            mWindowManagerService.getWindowFrame(ibinder, mTempBounds);
            j1 = mTempBounds.left;
            k1 = mTempBounds.top;
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int i2;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            i2 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.focusSearch(l, j, j1, k1, k, iaccessibilityinteractionconnectioncallback, byte0, i2, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            f = getCompatibilityScale(i1);
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return f;
        }

        public AccessibilityServiceInfo getServiceInfo() {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            AccessibilityServiceInfo accessibilityserviceinfo = mAccessibilityServiceInfo;
            return accessibilityserviceinfo;
        }

        public boolean isConfigured() {
            boolean flag;
            if(mEventTypes != 0 && mFeedbackType != 0 && mService != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public void linkToOwnDeath() throws RemoteException {
            mService.linkToDeath(this, 0);
        }

        public void notifyAccessibilityEvent(AccessibilityEvent accessibilityevent) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            int i = accessibilityevent.getEventType();
            AccessibilityEvent accessibilityevent1 = AccessibilityEvent.obtain(accessibilityevent);
            AccessibilityEvent accessibilityevent2 = (AccessibilityEvent)mPendingEvents.get(i);
            mPendingEvents.put(i, accessibilityevent1);
            if(accessibilityevent2 != null) {
                mHandler.removeMessages(i);
                accessibilityevent2.recycle();
            }
            Message message = mHandler.obtainMessage(i);
            mHandler.sendMessageDelayed(message, mNotificationTimeout);
            return;
        }

        public void notifyGesture(int i) {
            mHandler.obtainMessage(0x80000000, i, 0).sendToTarget();
        }

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            mService = ibinder;
            mServiceInterface = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(ibinder);
            mServiceInterface.setConnection(this, mId);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            tryAddServiceLocked(this);
            break MISSING_BLOCK_LABEL_90;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("AccessibilityManagerService", (new StringBuilder()).append("Error while setting Controller for service: ").append(ibinder).toString(), remoteexception);
        }

        public void onServiceDisconnected(ComponentName componentname) {
        }

        public boolean performAccessibilityAction(int i, long l, int j, Bundle bundle, int k, IAccessibilityInteractionConnectionCallback iaccessibilityinteractionconnectioncallback, 
                long l1) {
            int i1 = resolveAccessibilityWindowId(i);
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            if(mSecurityPolicy.canPerformActionLocked(this, i1, j, bundle)) goto _L2; else goto _L1
_L1:
            boolean flag = false;
              goto _L3
_L2:
            IAccessibilityInteractionConnection iaccessibilityinteractionconnection = getConnectionLocked(i1);
            if(iaccessibilityinteractionconnection != null) goto _L5; else goto _L4
_L4:
            flag = false;
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L5:
            obj;
            JVM INSTR monitorexit ;
            long l2;
            byte byte0;
            int j1;
            if(mIncludeNotImportantViews)
                byte0 = 8;
            else
                byte0 = 0;
            j1 = Binder.getCallingPid();
            l2 = Binder.clearCallingIdentity();
            iaccessibilityinteractionconnection.performAccessibilityAction(l, j, bundle, k, iaccessibilityinteractionconnectioncallback, byte0, j1, l1);
            Binder.restoreCallingIdentity(l2);
_L6:
            flag = true;
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Binder.restoreCallingIdentity(l2);
            if(true) goto _L6; else goto _L3
            Exception exception1;
            exception1;
            Binder.restoreCallingIdentity(l2);
            throw exception1;
_L3:
            return flag;
        }

        public boolean performGlobalAction(int i) {
            boolean flag = true;
            i;
            JVM INSTR tableswitch 1 4: default 32
        //                       1 36
        //                       2 44
        //                       3 52
        //                       4 59;
               goto _L1 _L2 _L3 _L4 _L5
_L1:
            flag = false;
_L7:
            return flag;
_L2:
            sendDownAndUpKeyEvents(4);
            continue; /* Loop/switch isn't completed */
_L3:
            sendDownAndUpKeyEvents(3);
            continue; /* Loop/switch isn't completed */
_L4:
            openRecents();
            continue; /* Loop/switch isn't completed */
_L5:
            expandStatusBar();
            if(true) goto _L7; else goto _L6
_L6:
        }

        public void setDynamicallyConfigurableProperties(AccessibilityServiceInfo accessibilityserviceinfo) {
            boolean flag = true;
            mEventTypes = accessibilityserviceinfo.eventTypes;
            mFeedbackType = accessibilityserviceinfo.feedbackType;
            String as[] = accessibilityserviceinfo.packageNames;
            if(as != null)
                mPackageNames.addAll(Arrays.asList(as));
            mNotificationTimeout = accessibilityserviceinfo.notificationTimeout;
            boolean flag1;
            if((1 & accessibilityserviceinfo.flags) != 0)
                flag1 = flag;
            else
                flag1 = false;
            mIsDefault = flag1;
            if(mIsAutomation || accessibilityserviceinfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion >= 16) {
                boolean flag2;
                Object obj;
                if((2 & accessibilityserviceinfo.flags) != 0)
                    flag2 = flag;
                else
                    flag2 = false;
                mIncludeNotImportantViews = flag2;
            }
            if((4 & accessibilityserviceinfo.flags) == 0)
                flag = false;
            mRequestTouchExplorationMode = flag;
            obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            if(isConfigured())
                if(mRequestTouchExplorationMode)
                    tryEnableTouchExplorationLocked(this);
                else
                    tryDisableTouchExplorationLocked(this);
            return;
        }

        public void setServiceInfo(AccessibilityServiceInfo accessibilityserviceinfo) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            AccessibilityServiceInfo accessibilityserviceinfo1 = mAccessibilityServiceInfo;
            if(accessibilityserviceinfo1 != null) {
                accessibilityserviceinfo1.updateDynamicallyConfigurableProperties(accessibilityserviceinfo);
                setDynamicallyConfigurableProperties(accessibilityserviceinfo1);
            } else {
                setDynamicallyConfigurableProperties(accessibilityserviceinfo);
            }
            return;
        }

        public boolean unbind() {
            boolean flag;
            if(mService != null) {
                synchronized(mLock) {
                    tryRemoveServiceLocked(this);
                }
                if(!mIsAutomation)
                    mContext.unbindService(this);
                flag = true;
            } else {
                flag = false;
            }
            return flag;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public void unlinkToOwnDeath() {
            mService.unlinkToDeath(this, 0);
        }

        private static final int MSG_ON_GESTURE = 0x80000000;
        AccessibilityServiceInfo mAccessibilityServiceInfo;
        boolean mCanRetrieveScreenContent;
        ComponentName mComponentName;
        int mEventTypes;
        int mFeedbackType;
        public Handler mHandler;
        int mId;
        boolean mIncludeNotImportantViews;
        Intent mIntent;
        boolean mIsAutomation;
        boolean mIsDefault;
        long mNotificationTimeout;
        Set mPackageNames;
        final SparseArray mPendingEvents = new SparseArray();
        boolean mReqeustTouchExplorationMode;
        boolean mRequestTouchExplorationMode;
        final ResolveInfo mResolveInfo;
        IBinder mService;
        IAccessibilityServiceClient mServiceInterface;
        final Rect mTempBounds = new Rect();
        final AccessibilityManagerService this$0;



        public Service(ComponentName componentname, AccessibilityServiceInfo accessibilityserviceinfo, boolean flag) {
            boolean flag1 = true;
            this$0 = AccessibilityManagerService.this;
            super();
            mId = 0;
            mPackageNames = new HashSet();
            mHandler = new Handler(mMainHandler.getLooper()) {

                public void handleMessage(Message message) {
                    int i = message.what;
                    i;
                    JVM INSTR tableswitch -2147483648 -2147483648: default 24
                //                               -2147483648 33;
                       goto _L1 _L2
_L1:
                    notifyAccessibilityEventInternal(i);
_L4:
                    return;
_L2:
                    int j = message.arg1;
                    notifyGestureInternal(j);
                    if(true) goto _L4; else goto _L3
_L3:
                }

                final Service this$1;

                 {
                    this$1 = Service.this;
                    super(looper);
                }
            };
            mResolveInfo = accessibilityserviceinfo.getResolveInfo();
            mId = int i = 
// JavaClassFileOutputException: get_constant: invalid tag
    }

    private class MainHanler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 3: default 32
        //                       1 60
        //                       2 33
        //                       3 301;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L3:
            int i = message.arg1;
            android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "touch_exploration_enabled", i);
            continue; /* Loop/switch isn't completed */
_L2:
            final Service service;
            String s;
            service = (Service)message.obj;
            s = service.mResolveInfo.loadLabel(mContext.getPackageManager()).toString();
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            if(mIsTouchExplorationEnabled)
                continue; /* Loop/switch isn't completed */
            break MISSING_BLOCK_LABEL_126;
            Exception exception;
            exception;
            throw exception;
            if(mEnableTouchExplorationDialog == null || !mEnableTouchExplorationDialog.isShowing())
                break MISSING_BLOCK_LABEL_155;
            obj;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
            AccessibilityManagerService accessibilitymanagerservice = AccessibilityManagerService.this;
            android.app.AlertDialog.Builder builder = (new android.app.AlertDialog.Builder(mContext)).setIcon(0x1080027).setPositiveButton(0x104000a, new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int j) {
                    mTouchExplorationGrantedServices.add(service.mComponentName);
                    persistComponentNamesToSettingLocked("touch_exploration_granted_accessibility_services", mTouchExplorationGrantedServices);
                    android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "touch_exploration_enabled", 1);
                }

                final MainHanler this$1;
                final Service val$service;

                 {
                    this$1 = MainHanler.this;
                    service = service1;
                    super();
                }
            }).setNegativeButton(0x1040000, new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int j) {
                    dialoginterface.dismiss();
                }

                final MainHanler this$1;

                 {
                    this$1 = MainHanler.this;
                    super();
                }
            }).setTitle(0x10403a1);
            Context context = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = s;
            accessibilitymanagerservice.mEnableTouchExplorationDialog = builder.setMessage(context.getString(0x10403a2, aobj)).create();
            mEnableTouchExplorationDialog.getWindow().setType(2012);
            mEnableTouchExplorationDialog.setCanceledOnTouchOutside(true);
            mEnableTouchExplorationDialog.show();
            obj;
            JVM INSTR monitorexit ;
            continue; /* Loop/switch isn't completed */
_L4:
            AccessibilityEvent accessibilityevent = (AccessibilityEvent)message.obj;
            if(mHasInputFilter && mInputFilter != null)
                mInputFilter.onAccessibilityEvent(accessibilityevent);
            accessibilityevent.recycle();
            if(true) goto _L1; else goto _L5
_L5:
        }

        final AccessibilityManagerService this$0;

        private MainHanler() {
            this$0 = AccessibilityManagerService.this;
            super();
        }

    }

    private class AccessibilityConnectionWrapper
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            unlinkToDeath();
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            removeAccessibilityInteractionConnectionLocked(mWindowId);
            return;
        }

        public void linkToDeath() throws RemoteException {
            mConnection.asBinder().linkToDeath(this, 0);
        }

        public void unlinkToDeath() {
            mConnection.asBinder().unlinkToDeath(this, 0);
        }

        private final IAccessibilityInteractionConnection mConnection;
        private final int mWindowId;
        final AccessibilityManagerService this$0;


        public AccessibilityConnectionWrapper(int i, IAccessibilityInteractionConnection iaccessibilityinteractionconnection) {
            this$0 = AccessibilityManagerService.this;
            Object();
            mWindowId = i;
            mConnection = iaccessibilityinteractionconnection;
        }
    }


    public AccessibilityManagerService(Context context) {
        mHandledFeedbackTypes = 0;
        mContext = context;
        mPackageManager = mContext.getPackageManager();
        registerPackageChangeAndBootCompletedBroadcastReceiver();
        registerSettingsContentObservers();
    }

    private boolean canDispathEventLocked(Service service, AccessibilityEvent accessibilityevent, int i) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_3;
        while(true)  {
            do
                return flag;
            while(!service.isConfigured() || !accessibilityevent.isImportantForAccessibility() && !service.mIncludeNotImportantViews);
            int j = accessibilityevent.getEventType();
            if((j & service.mEventTypes) == j) {
                Set set = service.mPackageNames;
                CharSequence charsequence = accessibilityevent.getPackageName();
                if(set.isEmpty() || set.contains(charsequence)) {
                    int k = service.mFeedbackType;
                    if((i & k) != k || k == 16)
                        flag = true;
                }
            }
        }
    }

    private Service getQueryBridge() {
        if(mQueryBridge == null)
            mQueryBridge = new Service(null, new AccessibilityServiceInfo(), true);
        return mQueryBridge;
    }

    private int getState() {
        int i = 0;
        if(mIsAccessibilityEnabled)
            i = false | true;
        if(mIsAccessibilityEnabled && mIsTouchExplorationEnabled)
            i |= 2;
        return i;
    }

    private void handleAccessibilityEnabledSettingChangedLocked() {
        boolean flag = true;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "accessibility_enabled", 0) != flag)
            flag = false;
        mIsAccessibilityEnabled = flag;
        if(mIsAccessibilityEnabled)
            manageServicesLocked();
        else
            unbindAllServicesLocked();
    }

    private void handleTouchExplorationEnabledSettingChangedLocked() {
        boolean flag = true;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "touch_exploration_enabled", 0) != flag)
            flag = false;
        mIsTouchExplorationEnabled = flag;
    }

    private void manageServicesLocked() {
        int i = updateServicesStateLocked(mInstalledServices, mEnabledServices);
        if(mIsAccessibilityEnabled && i == 0)
            android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "accessibility_enabled", 0);
    }

    private void notifyAccessibilityServicesDelayedLocked(AccessibilityEvent accessibilityevent, boolean flag) {
        int i = 0;
        int j = mServices.size();
_L1:
        if(i >= j)
            break MISSING_BLOCK_LABEL_85;
        Service service = (Service)mServices.get(i);
        if(service.mIsDefault == flag && canDispathEventLocked(service, accessibilityevent, mHandledFeedbackTypes)) {
            mHandledFeedbackTypes = mHandledFeedbackTypes | service.mFeedbackType;
            service.notifyAccessibilityEvent(accessibilityevent);
        }
        i++;
          goto _L1
        IndexOutOfBoundsException indexoutofboundsexception;
        indexoutofboundsexception;
    }

    private boolean notifyGestureLocked(int i, boolean flag) {
        int j = -1 + mServices.size();
_L3:
        Service service;
        if(j < 0)
            break MISSING_BLOCK_LABEL_67;
        service = (Service)mServices.get(j);
        if(!service.mReqeustTouchExplorationMode || service.mIsDefault != flag) goto _L2; else goto _L1
_L1:
        boolean flag1;
        service.notifyGesture(i);
        flag1 = true;
_L4:
        return flag1;
_L2:
        j--;
          goto _L3
        flag1 = false;
          goto _L4
    }

    private void persistComponentNamesToSettingLocked(String s, Set set) {
        StringBuilder stringbuilder = new StringBuilder();
        ComponentName componentname;
        for(Iterator iterator = set.iterator(); iterator.hasNext(); stringbuilder.append(componentname.flattenToShortString())) {
            componentname = (ComponentName)iterator.next();
            if(stringbuilder.length() > 0)
                stringbuilder.append(':');
        }

        android.provider.Settings.Secure.putString(mContext.getContentResolver(), s, stringbuilder.toString());
    }

    private void populateAccessibilityServiceListLocked() {
        mInstalledServices.clear();
        List list = mPackageManager.queryIntentServices(new Intent("android.accessibilityservice.AccessibilityService"), 132);
        int i = 0;
        int j = list.size();
        while(i < j)  {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(i);
            ServiceInfo serviceinfo = resolveinfo.serviceInfo;
            if(serviceinfo.applicationInfo.targetSdkVersion >= 16 && !"android.permission.BIND_ACCESSIBILITY_SERVICE".equals(serviceinfo.permission))
                Slog.w("AccessibilityManagerService", (new StringBuilder()).append("Skipping accessibilty service ").append((new ComponentName(((ComponentInfo) (serviceinfo)).packageName, ((ComponentInfo) (serviceinfo)).name)).flattenToShortString()).append(": it does not require the permission ").append("android.permission.BIND_ACCESSIBILITY_SERVICE").toString());
            else
                try {
                    AccessibilityServiceInfo accessibilityserviceinfo = new AccessibilityServiceInfo(resolveinfo, mContext);
                    mInstalledServices.add(accessibilityserviceinfo);
                }
                catch(XmlPullParserException xmlpullparserexception) {
                    Slog.e("AccessibilityManagerService", "Error while initializing AccessibilityServiceInfo", xmlpullparserexception);
                }
                catch(IOException ioexception) {
                    Slog.e("AccessibilityManagerService", "Error while initializing AccessibilityServiceInfo", ioexception);
                }
            i++;
        }
    }

    private void populateComponentNamesFromSettingLocked(String s, Set set) {
        set.clear();
        String s1 = android.provider.Settings.Secure.getString(mContext.getContentResolver(), s);
        if(s1 != null) {
            android.text.TextUtils.SimpleStringSplitter simplestringsplitter = mStringColonSplitter;
            simplestringsplitter.setString(s1);
            do {
                if(!simplestringsplitter.hasNext())
                    break;
                String s2 = simplestringsplitter.next();
                if(s2 != null && s2.length() > 0) {
                    ComponentName componentname = ComponentName.unflattenFromString(s2);
                    if(componentname != null)
                        set.add(componentname);
                }
            } while(true);
        }
    }

    private void populateEnabledAccessibilityServicesLocked() {
        populateComponentNamesFromSettingLocked("enabled_accessibility_services", mEnabledServices);
    }

    private void populateTouchExplorationGrantedAccessibilityServicesLocked() {
        populateComponentNamesFromSettingLocked("touch_exploration_granted_accessibility_services", mTouchExplorationGrantedServices);
    }

    private void registerPackageChangeAndBootCompletedBroadcastReceiver() {
        Context context = mContext;
        PackageMonitor packagemonitor = new PackageMonitor() {

            public boolean onHandleForceStop(Intent intent, String as[], int i, boolean flag) {
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                Iterator iterator = mEnabledServices.iterator();
label0:
                do {
label1:
                    {
                        if(iterator.hasNext()) {
                            String s = ((ComponentName)iterator.next()).getPackageName();
                            int j = as.length;
                            boolean flag1;
                            for(int k = 0; k >= j; k++)
                                continue label0;

                            if(!s.equals(as[k]))
                                break label1;
                            if(!flag) {
                                flag1 = true;
                            } else {
                                iterator.remove();
                                persistComponentNamesToSettingLocked("enabled_accessibility_services", mEnabledServices);
                                break label1;
                            }
                        } else {
                            flag1 = false;
                        }
                        return flag1;
                    }
                } while(true);
            }

            public void onPackageRemoved(String s, int i) {
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                Iterator iterator = mEnabledServices.iterator();
                do {
                    if(!iterator.hasNext())
                        break;
                    ComponentName componentname = (ComponentName)iterator.next();
                    if(!componentname.getPackageName().equals(s))
                        continue;
                    iterator.remove();
                    persistComponentNamesToSettingLocked("enabled_accessibility_services", mEnabledServices);
                    mTouchExplorationGrantedServices.remove(componentname);
                    persistComponentNamesToSettingLocked("touch_exploration_granted_accessibility_services", mEnabledServices);
                    break;
                } while(true);
                return;
            }

            public void onReceive(Context context1, Intent intent) {
                if(intent.getAction() != "android.intent.action.BOOT_COMPLETED")
                    break MISSING_BLOCK_LABEL_90;
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    populateAccessibilityServiceListLocked();
                    populateEnabledAccessibilityServicesLocked();
                    populateTouchExplorationGrantedAccessibilityServicesLocked();
                    handleAccessibilityEnabledSettingChangedLocked();
                    handleTouchExplorationEnabledSettingChangedLocked();
                    updateInputFilterLocked();
                    sendStateToClientsLocked();
                }
                break MISSING_BLOCK_LABEL_96;
                super.onReceive(context1, intent);
            }

            public void onSomePackagesChanged() {
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    populateAccessibilityServiceListLocked();
                    manageServicesLocked();
                }
                return;
            }

            final AccessibilityManagerService this$0;

             {
                this$0 = AccessibilityManagerService.this;
                super();
            }
        };
        packagemonitor.register(context, null, true);
        IntentFilter intentfilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
        mContext.registerReceiver(packagemonitor, intentfilter, null, packagemonitor.getRegisteredHandler());
    }

    private void registerSettingsContentObservers() {
        ContentResolver contentresolver = mContext.getContentResolver();
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("accessibility_enabled"), false, new ContentObserver(new Handler()) {

            public void onChange(boolean flag) {
                super.onChange(flag);
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    handleAccessibilityEnabledSettingChangedLocked();
                    updateInputFilterLocked();
                    sendStateToClientsLocked();
                }
                return;
            }

            final AccessibilityManagerService this$0;

             {
                this$0 = AccessibilityManagerService.this;
                ContentObserver(handler);
            }
        });
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("touch_exploration_enabled"), false, new ContentObserver(new Handler()) {

            public void onChange(boolean flag) {
                super.onChange(flag);
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    handleTouchExplorationEnabledSettingChangedLocked();
                    updateInputFilterLocked();
                    sendStateToClientsLocked();
                }
                return;
            }

            final AccessibilityManagerService this$0;

             {
                this$0 = AccessibilityManagerService.this;
                ContentObserver(handler);
            }
        });
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("enabled_accessibility_services"), false, new ContentObserver(new Handler()) {

            public void onChange(boolean flag) {
                super.onChange(flag);
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    populateEnabledAccessibilityServicesLocked();
                    manageServicesLocked();
                }
                return;
            }

            final AccessibilityManagerService this$0;

             {
                this$0 = AccessibilityManagerService.this;
                ContentObserver(handler);
            }
        });
        contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("touch_exploration_granted_accessibility_services"), false, new ContentObserver(new Handler()) {

            public void onChange(boolean flag) {
                super.onChange(flag);
                Object obj = mLock;
                obj;
                JVM INSTR monitorenter ;
                if(mUiAutomationService == null) {
                    populateTouchExplorationGrantedAccessibilityServicesLocked();
                    unbindAllServicesLocked();
                    manageServicesLocked();
                }
                return;
            }

            final AccessibilityManagerService this$0;

             {
                this$0 = AccessibilityManagerService.this;
                ContentObserver(handler);
            }
        });
    }

    private void removeAccessibilityInteractionConnectionLocked(int i) {
        mWindowIdToWindowTokenMap.remove(i);
        mWindowIdToInteractionConnectionWrapperMap.remove(i);
    }

    private void sendStateToClientsLocked() {
        int i = getState();
        int j = 0;
        int k = mClients.size();
        do {
            if(j >= k)
                break;
            try {
                ((IAccessibilityManagerClient)mClients.get(j)).setState(i);
            }
            catch(RemoteException remoteexception) {
                mClients.remove(j);
                k--;
                j--;
            }
            j++;
        } while(true);
    }

    private void tryAddServiceLocked(Service service) {
        try {
            if(!mServices.contains(service) && service.isConfigured()) {
                service.linkToOwnDeath();
                mServices.add(service);
                mComponentNameToServiceMap.put(service.mComponentName, service);
                updateInputFilterLocked();
                tryEnableTouchExplorationLocked(service);
            }
        }
        catch(RemoteException remoteexception) { }
    }

    private void tryDisableTouchExplorationLocked(Service service) {
        if(!mIsTouchExplorationEnabled) goto _L2; else goto _L1
_L1:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = mServices.size();
        j = 0;
_L7:
        if(j >= i) goto _L4; else goto _L3
_L3:
        Service service1 = (Service)mServices.get(j);
        if(service1 == service || !service1.mRequestTouchExplorationMode) goto _L5; else goto _L2
_L4:
        mMainHandler.obtainMessage(2, 0, 0).sendToTarget();
_L2:
        return;
_L5:
        j++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void tryEnableTouchExplorationLocked(Service service) {
        if(!mIsTouchExplorationEnabled && service.mRequestTouchExplorationMode) {
            boolean flag = mTouchExplorationGrantedServices.contains(service.mComponentName);
            if(!service.mIsAutomation && !flag)
                mMainHandler.obtainMessage(1, service).sendToTarget();
            else
                mMainHandler.obtainMessage(2, 1, 0).sendToTarget();
        }
    }

    private boolean tryRemoveServiceLocked(Service service) {
        boolean flag = mServices.remove(service);
        if(!flag) {
            flag = false;
        } else {
            mComponentNameToServiceMap.remove(service.mComponentName);
            service.unlinkToOwnDeath();
            service.dispose();
            updateInputFilterLocked();
            tryDisableTouchExplorationLocked(service);
        }
        return flag;
    }

    private void unbindAllServicesLocked() {
        List list = mServices;
        int i = 0;
        for(int j = list.size(); i < j; i++)
            if(((Service)list.get(i)).unbind()) {
                i--;
                j--;
            }

    }

    private void updateInputFilterLocked() {
        if(!mIsAccessibilityEnabled || !mIsTouchExplorationEnabled) goto _L2; else goto _L1
_L1:
        if(!mHasInputFilter) {
            mHasInputFilter = true;
            if(mInputFilter == null)
                mInputFilter = new AccessibilityInputFilter(mContext, this);
            mWindowManagerService.setInputFilter(mInputFilter);
        }
_L4:
        return;
_L2:
        if(mHasInputFilter) {
            mHasInputFilter = false;
            mWindowManagerService.setInputFilter(null);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private int updateServicesStateLocked(List list, Set set) {
        Map map = mComponentNameToServiceMap;
        boolean flag = mIsAccessibilityEnabled;
        int i = 0;
        int j = 0;
        int k = list.size();
        while(j < k)  {
            AccessibilityServiceInfo accessibilityserviceinfo = (AccessibilityServiceInfo)list.get(j);
            ComponentName componentname = ComponentName.unflattenFromString(accessibilityserviceinfo.getId());
            Service service = (Service)map.get(componentname);
            if(flag) {
                if(set.contains(componentname)) {
                    if(service == null)
                        service = new Service(componentname, accessibilityserviceinfo, false);
                    service.bind();
                    i++;
                } else
                if(service != null)
                    service.unbind();
            } else
            if(service != null)
                service.unbind();
            j++;
        }
        return i;
    }

    public int addAccessibilityInteractionConnection(IWindow iwindow, IAccessibilityInteractionConnection iaccessibilityinteractionconnection) throws RemoteException {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = sNextWindowId;
        sNextWindowId = i + 1;
        AccessibilityConnectionWrapper accessibilityconnectionwrapper = new AccessibilityConnectionWrapper(i, iaccessibilityinteractionconnection);
        accessibilityconnectionwrapper.linkToDeath();
        mWindowIdToWindowTokenMap.put(i, iwindow.asBinder());
        mWindowIdToInteractionConnectionWrapperMap.put(i, accessibilityconnectionwrapper);
        return i;
    }

    public int addClient(final IAccessibilityManagerClient addedClient) throws RemoteException {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        mClients.add(addedClient);
        addedClient.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() {

            public void binderDied() {
                Object obj1 = mLock;
                obj1;
                JVM INSTR monitorenter ;
                addedClient.asBinder().unlinkToDeath(this, 0);
                mClients.remove(addedClient);
                return;
            }

            final AccessibilityManagerService this$0;
            final IAccessibilityManagerClient val$addedClient;

             {
                this$0 = AccessibilityManagerService.this;
                addedClient = iaccessibilitymanagerclient;
                Object();
            }
        }, 0);
        int i = getState();
        return i;
    }

    boolean getAccessibilityFocusBoundsInActiveWindow(Rect rect) {
        boolean flag;
        int i;
        AccessibilityInteractionClient accessibilityinteractionclient;
        flag = false;
        Service service = getQueryBridge();
        i = service.mId;
        accessibilityinteractionclient = AccessibilityInteractionClient.getInstance();
        accessibilityinteractionclient.addConnection(i, service);
        AccessibilityNodeInfo accessibilitynodeinfo = AccessibilityInteractionClient.getInstance().getRootInActiveWindow(i);
        if(accessibilitynodeinfo != null) goto _L2; else goto _L1
_L1:
        accessibilityinteractionclient.removeConnection(i);
_L3:
        return flag;
_L2:
        AccessibilityNodeInfo accessibilitynodeinfo1 = accessibilitynodeinfo.findFocus(2);
label0:
        {
            if(accessibilitynodeinfo1 != null)
                break label0;
            accessibilityinteractionclient.removeConnection(i);
        }
          goto _L3
        accessibilitynodeinfo1.getBoundsInScreen(rect);
        flag = true;
        accessibilityinteractionclient.removeConnection(i);
          goto _L3
        Exception exception;
        exception;
        accessibilityinteractionclient.removeConnection(i);
        throw exception;
    }

    void getActiveWindowBounds(Rect rect) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = mSecurityPolicy.mActiveWindowId;
        IBinder ibinder = (IBinder)mWindowIdToWindowTokenMap.get(i);
        mWindowManagerService.getWindowFrame(ibinder, rect);
        return;
    }

    int getActiveWindowId() {
        return mSecurityPolicy.mActiveWindowId;
    }

    public List getEnabledAccessibilityServiceList(int i) {
        List list;
        List list1;
        list = mEnabledServicesForFeedbackTempList;
        list.clear();
        list1 = mServices;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
_L7:
        if(i == 0) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        int l;
        j = 1 << Integer.numberOfTrailingZeros(i);
        i &= ~j;
        k = list1.size();
        l = 0;
_L5:
        if(l < k) {
            Service service = (Service)list1.get(l);
            if((j & service.mFeedbackType) != 0)
                list.add(service.mAccessibilityServiceInfo);
            break MISSING_BLOCK_LABEL_115;
        }
          goto _L3
_L2:
        return list;
        l++;
        if(true) goto _L5; else goto _L4
_L4:
_L3:
        if(true) goto _L7; else goto _L6
_L6:
    }

    public List getInstalledAccessibilityServiceList() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        List list = mInstalledServices;
        return list;
    }

    public void interrupt() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = 0;
        int j = mServices.size();
_L1:
        Service service;
        if(i >= j)
            break MISSING_BLOCK_LABEL_99;
        service = (Service)mServices.get(i);
        try {
            service.mServiceInterface.onInterrupt();
        }
        catch(RemoteException remoteexception) {
            Slog.e("AccessibilityManagerService", (new StringBuilder()).append("Error during sending interrupt request to ").append(service.mService).toString(), remoteexception);
        }
        i++;
          goto _L1
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
    }

    boolean onGesture(int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = notifyGestureLocked(i, false);
        if(!flag)
            flag = notifyGestureLocked(i, true);
        return flag;
    }

    public void registerUiTestAutomationService(IAccessibilityServiceClient iaccessibilityserviceclient, AccessibilityServiceInfo accessibilityserviceinfo) {
        mSecurityPolicy.enforceCallingPermission("android.permission.RETRIEVE_WINDOW_CONTENT", "registerUiTestAutomationService");
        ComponentName componentname = new ComponentName("foo.bar", "AutomationAccessibilityService");
        synchronized(mLock) {
            int i = mServices.size();
            for(int j = 0; j < i; j++)
                ((Service)mServices.get(j)).unbind();

            if(!mIsAccessibilityEnabled) {
                mIsAccessibilityEnabled = true;
                sendStateToClientsLocked();
            }
        }
        mUiAutomationService = new Service(componentname, accessibilityserviceinfo, true);
        mUiAutomationService.onServiceConnected(componentname, iaccessibilityserviceclient.asBinder());
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void removeAccessibilityInteractionConnection(IWindow iwindow) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = mWindowIdToWindowTokenMap.size();
        int j = 0;
        do {
label0:
            {
                if(j < i) {
                    if(mWindowIdToWindowTokenMap.valueAt(j) != iwindow.asBinder())
                        break label0;
                    int k = mWindowIdToWindowTokenMap.keyAt(j);
                    ((AccessibilityConnectionWrapper)mWindowIdToInteractionConnectionWrapperMap.get(k)).unlinkToDeath();
                    removeAccessibilityInteractionConnectionLocked(k);
                }
                return;
            }
            j++;
        } while(true);
    }

    public boolean sendAccessibilityEvent(AccessibilityEvent accessibilityevent) {
        boolean flag = true;
        int i = accessibilityevent.getEventType();
        if(i == 128 && mTouchExplorationGestureStarted) {
            mTouchExplorationGestureStarted = false;
            sendAccessibilityEvent(AccessibilityEvent.obtain(512));
        }
        synchronized(mLock) {
            if(mSecurityPolicy.canDispatchAccessibilityEvent(accessibilityevent)) {
                mSecurityPolicy.updateActiveWindowAndEventSourceLocked(accessibilityevent);
                notifyAccessibilityServicesDelayedLocked(accessibilityevent, false);
                notifyAccessibilityServicesDelayedLocked(accessibilityevent, true);
            }
            if(mHasInputFilter && mInputFilter != null)
                mMainHandler.obtainMessage(3, AccessibilityEvent.obtain(accessibilityevent)).sendToTarget();
            accessibilityevent.recycle();
            mHandledFeedbackTypes = 0;
        }
        if(i == 256 && mTouchExplorationGestureEnded) {
            mTouchExplorationGestureEnded = false;
            sendAccessibilityEvent(AccessibilityEvent.obtain(1024));
        }
        if(OWN_PROCESS_ID == Binder.getCallingPid())
            flag = false;
        return flag;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void touchExplorationGestureEnded() {
        mTouchExplorationGestureEnded = true;
    }

    public void touchExplorationGestureStarted() {
        mTouchExplorationGestureStarted = true;
    }

    public void unregisterUiTestAutomationService(IAccessibilityServiceClient iaccessibilityserviceclient) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(mUiAutomationService != null && mUiAutomationService.mServiceInterface == iaccessibilityserviceclient)
            mUiAutomationService.binderDied();
        return;
    }

    private static final char COMPONENT_NAME_SEPARATOR = 58;
    private static final boolean DEBUG = false;
    private static final String FUNCTION_REGISTER_UI_TEST_AUTOMATION_SERVICE = "registerUiTestAutomationService";
    private static final String LOG_TAG = "AccessibilityManagerService";
    private static final int MSG_SEND_ACCESSIBILITY_EVENT_TO_INPUT_FILTER = 3;
    private static final int MSG_SHOW_ENABLE_TOUCH_EXPLORATION_DIALOG = 1;
    private static final int MSG_TOGGLE_TOUCH_EXPLORATION = 2;
    private static final int OWN_PROCESS_ID = Process.myPid();
    private static int sIdCounter = 0;
    private static int sNextWindowId;
    final List mClients = new ArrayList();
    final Map mComponentNameToServiceMap = new HashMap();
    final Context mContext;
    private AlertDialog mEnableTouchExplorationDialog;
    private final Set mEnabledServices = new HashSet();
    private final List mEnabledServicesForFeedbackTempList = new ArrayList();
    private int mHandledFeedbackTypes;
    private boolean mHasInputFilter;
    private AccessibilityInputFilter mInputFilter;
    private final List mInstalledServices = new ArrayList();
    private boolean mIsAccessibilityEnabled;
    private boolean mIsTouchExplorationEnabled;
    final Object mLock = new Object();
    private final MainHanler mMainHandler = new MainHanler();
    private PackageManager mPackageManager;
    private Service mQueryBridge;
    private final SecurityPolicy mSecurityPolicy = new SecurityPolicy();
    final List mServices = new ArrayList();
    private final android.text.TextUtils.SimpleStringSplitter mStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(':');
    private boolean mTouchExplorationGestureEnded;
    private boolean mTouchExplorationGestureStarted;
    private final Set mTouchExplorationGrantedServices = new HashSet();
    private Service mUiAutomationService;
    private final SparseArray mWindowIdToInteractionConnectionWrapperMap = new SparseArray();
    private final SparseArray mWindowIdToWindowTokenMap = new SparseArray();
    private final WindowManagerService mWindowManagerService = (WindowManagerService)ServiceManager.getService("window");





/*
    static Service access$102(AccessibilityManagerService accessibilitymanagerservice, Service service) {
        accessibilitymanagerservice.mUiAutomationService = service;
        return service;
    }

*/








/*
    static AlertDialog access$1902(AccessibilityManagerService accessibilitymanagerservice, AlertDialog alertdialog) {
        accessibilitymanagerservice.mEnableTouchExplorationDialog = alertdialog;
        return alertdialog;
    }

*/






/*
    static int access$2508() {
        int i = sIdCounter;
        sIdCounter = i + 1;
        return i;
    }

*/
















}
