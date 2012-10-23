// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.content.res.*;
import android.net.Uri;
import android.os.*;
import android.os.storage.*;
import android.text.TextUtils;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.app.IMediaContainerService;
import com.android.internal.util.XmlUtils;
import com.android.server.am.ActivityManagerService;
import com.android.server.pm.PackageManagerService;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.xmlpull.v1.XmlPullParserException;

// Referenced classes of package com.android.server:
//            INativeDaemonConnectorCallbacks, NativeDaemonConnector, NativeDaemonConnectorException, NativeDaemonEvent

class MountService extends android.os.storage.IMountService.Stub
    implements INativeDaemonConnectorCallbacks, Watchdog.Monitor {
    class UnmountObbAction extends ObbAction {

        public void handleError() {
            sendNewStatusOrIgnore(20);
        }

        public void handleExecute() throws IOException {
            waitForReady();
            warnOnNotMounted();
            ObbInfo obbinfo = getObbInfo();
            ObbState obbstate;
            synchronized(mObbMounts) {
                obbstate = (ObbState)mObbPathToStateMap.get(obbinfo.filename);
            }
            if(obbstate == null)
                sendNewStatusOrIgnore(23);
            else
            if(obbstate.callerUid != super.mObbState.callerUid) {
                Slog.w("MountService", (new StringBuilder()).append("Permission denied attempting to unmount OBB ").append(obbinfo.filename).append(" (owned by ").append(obbinfo.packageName).append(")").toString());
                sendNewStatusOrIgnore(25);
            } else {
                super.mObbState.filename = obbinfo.filename;
                byte byte0 = 0;
                try {
                    Object aobj[] = new Object[2];
                    aobj[0] = "unmount";
                    aobj[1] = super.mObbState.filename;
                    NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("obb", aobj);
                    if(mForceUnmount)
                        command.appendArg("force");
                    mConnector.execute(command);
                }
                catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
                    int i = nativedaemonconnectorexception.getCode();
                    if(i == 405)
                        byte0 = -7;
                    else
                    if(i == 406)
                        byte0 = 0;
                    else
                        byte0 = -1;
                }
                if(byte0 == 0) {
                    synchronized(mObbMounts) {
                        removeObbStateLocked(obbstate);
                    }
                    sendNewStatusOrIgnore(2);
                } else {
                    Slog.w("MountService", (new StringBuilder()).append("Could not mount OBB: ").append(super.mObbState.filename).toString());
                    sendNewStatusOrIgnore(22);
                }
            }
            return;
            exception;
            map;
            JVM INSTR monitorexit ;
            throw exception;
            exception1;
            map1;
            JVM INSTR monitorexit ;
            throw exception1;
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("UnmountObbAction{");
            stringbuilder.append("filename=");
            String s;
            String s1;
            String s2;
            if(super.mObbState.filename != null)
                s = super.mObbState.filename;
            else
                s = "null";
            stringbuilder.append(s);
            stringbuilder.append(",force=");
            stringbuilder.append(mForceUnmount);
            stringbuilder.append(",callerUid=");
            stringbuilder.append(super.mObbState.callerUid);
            stringbuilder.append(",token=");
            if(super.mObbState.token != null)
                s1 = super.mObbState.token.toString();
            else
                s1 = "null";
            stringbuilder.append(s1);
            stringbuilder.append(",binder=");
            if(super.mObbState.token != null)
                s2 = super.mObbState.getBinder().toString();
            else
                s2 = "null";
            stringbuilder.append(s2);
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        private final boolean mForceUnmount;
        final MountService this$0;

        UnmountObbAction(ObbState obbstate, boolean flag) {
            this$0 = MountService.this;
            super(obbstate);
            mForceUnmount = flag;
        }
    }

    class MountObbAction extends ObbAction {

        public void handleError() {
            sendNewStatusOrIgnore(20);
        }

        public void handleExecute() throws IOException, RemoteException {
            ObbInfo obbinfo;
            waitForReady();
            warnOnNotMounted();
            obbinfo = getObbInfo();
            if(isUidOwnerOfPackageOrSystem(obbinfo.packageName, super.mObbState.callerUid)) goto _L2; else goto _L1
_L1:
            Slog.w("MountService", (new StringBuilder()).append("Denied attempt to mount OBB ").append(obbinfo.filename).append(" which is owned by ").append(obbinfo.packageName).toString());
            sendNewStatusOrIgnore(25);
_L4:
            return;
_L2:
            boolean flag;
            synchronized(mObbMounts) {
                flag = mObbPathToStateMap.containsKey(obbinfo.filename);
            }
            if(flag) {
                Slog.w("MountService", (new StringBuilder()).append("Attempt to mount OBB which is already mounted: ").append(obbinfo.filename).toString());
                sendNewStatusOrIgnore(24);
                continue; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_164;
            exception;
            map;
            JVM INSTR monitorexit ;
            throw exception;
            super.mObbState.filename = obbinfo.filename;
            String s1;
            int i;
            if(mKey == null) {
                s1 = "none";
            } else {
                String s;
                try {
                    s = (new BigInteger(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(mKey.toCharArray(), obbinfo.salt, 1024, 128)).getEncoded())).toString(16);
                }
                catch(NoSuchAlgorithmException nosuchalgorithmexception) {
                    Slog.e("MountService", "Could not load PBKDF2 algorithm", nosuchalgorithmexception);
                    sendNewStatusOrIgnore(20);
                    continue; /* Loop/switch isn't completed */
                }
                catch(InvalidKeySpecException invalidkeyspecexception) {
                    Slog.e("MountService", "Invalid key spec when loading PBKDF2 algorithm", invalidkeyspecexception);
                    sendNewStatusOrIgnore(20);
                    continue; /* Loop/switch isn't completed */
                }
                s1 = s;
            }
            i = 0;
            try {
                NativeDaemonConnector nativedaemonconnector = mConnector;
                Object aobj[] = new Object[4];
                aobj[0] = "mount";
                aobj[1] = super.mObbState.filename;
                aobj[2] = s1;
                aobj[3] = Integer.valueOf(super.mObbState.callerUid);
                nativedaemonconnector.execute("obb", aobj);
            }
            catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
                if(nativedaemonconnectorexception.getCode() != 405)
                    i = -1;
            }
            if(i == 0) {
                synchronized(mObbMounts) {
                    addObbStateLocked(super.mObbState);
                }
                sendNewStatusOrIgnore(1);
            } else {
                Slog.e("MountService", (new StringBuilder()).append("Couldn't mount OBB file: ").append(i).toString());
                sendNewStatusOrIgnore(21);
            }
            continue; /* Loop/switch isn't completed */
            exception1;
            map1;
            JVM INSTR monitorexit ;
            throw exception1;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("MountObbAction{");
            stringbuilder.append("filename=");
            stringbuilder.append(super.mObbState.filename);
            stringbuilder.append(",callerUid=");
            stringbuilder.append(super.mObbState.callerUid);
            stringbuilder.append(",token=");
            String s;
            String s1;
            if(super.mObbState.token != null)
                s = super.mObbState.token.toString();
            else
                s = "NULL";
            stringbuilder.append(s);
            stringbuilder.append(",binder=");
            if(super.mObbState.token != null)
                s1 = super.mObbState.getBinder().toString();
            else
                s1 = "null";
            stringbuilder.append(s1);
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        private final String mKey;
        final MountService this$0;

        MountObbAction(ObbState obbstate, String s) {
            this$0 = MountService.this;
            super(obbstate);
            mKey = s;
        }
    }

    abstract class ObbAction {

        public void execute(ObbActionHandler obbactionhandler) {
            try {
                mRetries = 1 + mRetries;
                if(mRetries > 3) {
                    Slog.w("MountService", "Failed to invoke remote methods on default container service. Giving up");
                    mObbActionHandler.sendEmptyMessage(3);
                    handleError();
                } else {
                    handleExecute();
                    mObbActionHandler.sendEmptyMessage(3);
                }
            }
            catch(RemoteException remoteexception) {
                mObbActionHandler.sendEmptyMessage(4);
            }
            catch(Exception exception) {
                handleError();
                mObbActionHandler.sendEmptyMessage(3);
            }
        }

        protected ObbInfo getObbInfo() throws IOException {
            ObbInfo obbinfo1 = mContainerService.getObbInfo(mObbState.filename);
            ObbInfo obbinfo = obbinfo1;
_L1:
            RemoteException remoteexception;
            if(obbinfo == null)
                throw new IOException((new StringBuilder()).append("Couldn't read OBB file: ").append(mObbState.filename).toString());
            else
                return obbinfo;
            remoteexception;
            Slog.d("MountService", (new StringBuilder()).append("Couldn't call DefaultContainerService to fetch OBB info for ").append(mObbState.filename).toString());
            obbinfo = null;
              goto _L1
        }

        abstract void handleError();

        abstract void handleExecute() throws RemoteException, IOException;

        protected void sendNewStatusOrIgnore(int i) {
            if(mObbState != null && mObbState.token != null)
                try {
                    mObbState.token.onObbResult(mObbState.filename, mObbState.nonce, i);
                }
                catch(RemoteException remoteexception) {
                    Slog.w("MountService", "MountServiceListener went away while calling onObbStateChanged");
                }
        }

        private static final int MAX_RETRIES = 3;
        ObbState mObbState;
        private int mRetries;
        final MountService this$0;

        ObbAction(ObbState obbstate) {
            this$0 = MountService.this;
            super();
            mObbState = obbstate;
        }
    }

    private class ObbActionHandler extends Handler {

        private boolean connectToService() {
            boolean flag = true;
            Intent intent = (new Intent()).setComponent(MountService.DEFAULT_CONTAINER_COMPONENT);
            if(mContext.bindService(intent, mDefContainerConn, flag))
                mBound = flag;
            else
                flag = false;
            return flag;
        }

        private void disconnectService() {
            mContainerService = null;
            mBound = false;
            mContext.unbindService(mDefContainerConn);
        }

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 5: default 40
        //                       1 41
        //                       2 95
        //                       3 323
        //                       4 236
        //                       5 387;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
            return;
_L2:
            ObbAction obbaction1 = (ObbAction)message.obj;
            if(!mBound && !connectToService()) {
                Slog.e("MountService", "Failed to bind to media container service");
                obbaction1.handleError();
            } else {
                mActions.add(obbaction1);
            }
              goto _L1
_L3:
            if(message.obj != null)
                mContainerService = (IMediaContainerService)message.obj;
            if(mContainerService == null) {
                Slog.e("MountService", "Cannot bind to media container service");
                for(Iterator iterator3 = mActions.iterator(); iterator3.hasNext(); ((ObbAction)iterator3.next()).handleError());
                mActions.clear();
            } else
            if(mActions.size() > 0) {
                ObbAction obbaction = (ObbAction)mActions.get(0);
                if(obbaction != null)
                    obbaction.execute(this);
            } else {
                Slog.w("MountService", "Empty queue");
            }
              goto _L1
_L5:
            if(mActions.size() > 0) {
                if(mBound)
                    disconnectService();
                if(!connectToService()) {
                    Slog.e("MountService", "Failed to bind to media container service");
                    for(Iterator iterator2 = mActions.iterator(); iterator2.hasNext(); ((ObbAction)iterator2.next()).handleError());
                    mActions.clear();
                }
            }
              goto _L1
_L4:
            if(mActions.size() > 0)
                mActions.remove(0);
            if(mActions.size() == 0) {
                if(mBound)
                    disconnectService();
            } else {
                mObbActionHandler.sendEmptyMessage(2);
            }
              goto _L1
_L6:
            String s = (String)message.obj;
            Map map = mObbMounts;
            map;
            JVM INSTR monitorenter ;
            LinkedList linkedlist;
            linkedlist = new LinkedList();
            Iterator iterator = mObbPathToStateMap.entrySet().iterator();
            do {
                if(!iterator.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                if(((String)entry.getKey()).startsWith(s))
                    linkedlist.add(entry.getValue());
            } while(true);
            break MISSING_BLOCK_LABEL_497;
            Exception exception;
            exception;
            throw exception;
            Iterator iterator1 = linkedlist.iterator();
_L7:
            ObbState obbstate;
            if(!iterator1.hasNext())
                break MISSING_BLOCK_LABEL_595;
            obbstate = (ObbState)iterator1.next();
            removeObbStateLocked(obbstate);
            obbstate.token.onObbResult(obbstate.filename, obbstate.nonce, 2);
              goto _L7
            RemoteException remoteexception;
            remoteexception;
            Slog.i("MountService", (new StringBuilder()).append("Couldn't send unmount notification for  OBB: ").append(obbstate.filename).toString());
              goto _L7
            map;
            JVM INSTR monitorexit ;
              goto _L1
        }

        private final List mActions = new LinkedList();
        private boolean mBound;
        final MountService this$0;

        ObbActionHandler(Looper looper) {
            this$0 = MountService.this;
            super(looper);
            mBound = false;
        }
    }

    private final class MountServiceBinderListener
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            ArrayList arraylist = mListeners;
            arraylist;
            JVM INSTR monitorenter ;
            mListeners.remove(this);
            mListener.asBinder().unlinkToDeath(this, 0);
            return;
        }

        final IMountServiceListener mListener;
        final MountService this$0;

        MountServiceBinderListener(IMountServiceListener imountservicelistener) {
            this$0 = MountService.this;
            super();
            mListener = imountservicelistener;
        }
    }

    class MountServiceHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 3: default 32
        //                       1 33
        //                       2 79
        //                       3 395;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L2:
            UnmountCallBack unmountcallback1 = (UnmountCallBack)message.obj;
            mForceUnmounts.add(unmountcallback1);
            if(!mUpdatingStatus) {
                mUpdatingStatus = true;
                mPms.updateExternalMediaStatus(false, true);
            }
            continue; /* Loop/switch isn't completed */
_L3:
            mUpdatingStatus = false;
            int i = mForceUnmounts.size();
            int ai[] = new int[i];
            ActivityManagerService activitymanagerservice = (ActivityManagerService)ServiceManager.getService("activity");
            int j = 0;
            int k = 0;
            while(j < i)  {
                UnmountCallBack unmountcallback = (UnmountCallBack)mForceUnmounts.get(j);
                String s = unmountcallback.path;
                boolean flag = false;
                int i1;
                if(!unmountcallback.force) {
                    flag = true;
                } else {
                    int ai1[] = getStorageUsers(s);
                    if(ai1 == null || ai1.length == 0) {
                        flag = true;
                    } else {
                        activitymanagerservice.killPids(ai1, "unmount media", true);
                        int ai2[] = getStorageUsers(s);
                        if(ai2 == null || ai2.length == 0)
                            flag = true;
                    }
                }
                if(!flag && unmountcallback.retries < 4) {
                    Slog.i("MountService", "Retrying to kill storage users again");
                    Handler handler = mHandler;
                    Handler handler1 = mHandler;
                    int j1 = unmountcallback.retries;
                    unmountcallback.retries = j1 + 1;
                    handler.sendMessageDelayed(handler1.obtainMessage(2, Integer.valueOf(j1)), 30L);
                    i1 = k;
                } else {
                    if(unmountcallback.retries >= 4)
                        Slog.i("MountService", "Failed to unmount media inspite of 4 retries. Forcibly killing processes now");
                    i1 = k + 1;
                    ai[k] = j;
                    mHandler.sendMessage(mHandler.obtainMessage(3, unmountcallback));
                }
                j++;
                k = i1;
            }
            int l = k - 1;
            while(l >= 0)  {
                mForceUnmounts.remove(ai[l]);
                l--;
            }
            continue; /* Loop/switch isn't completed */
_L4:
            ((UnmountCallBack)message.obj).handleFinished();
            if(true) goto _L1; else goto _L5
_L5:
        }

        ArrayList mForceUnmounts;
        boolean mUpdatingStatus;
        final MountService this$0;

        MountServiceHandler(Looper looper) {
            this$0 = MountService.this;
            super(looper);
            mForceUnmounts = new ArrayList();
            mUpdatingStatus = false;
        }
    }

    class ShutdownCallBack extends UnmountCallBack {

        void handleFinished() {
            int i;
            i = doUnmountVolume(super.path, true, super.removeEncryption);
            if(observer == null)
                break MISSING_BLOCK_LABEL_34;
            observer.onShutDownComplete(i);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.w("MountService", "RemoteException when shutting down");
              goto _L1
        }

        IMountShutdownObserver observer;
        final MountService this$0;

        ShutdownCallBack(String s, IMountShutdownObserver imountshutdownobserver) {
            this$0 = MountService.this;
            super(s, true, false);
            observer = imountshutdownobserver;
        }
    }

    class UmsEnableCallBack extends UnmountCallBack {

        void handleFinished() {
            super.handleFinished();
            doShareUnshareVolume(super.path, method, true);
        }

        final String method;
        final MountService this$0;

        UmsEnableCallBack(String s, String s1, boolean flag) {
            this$0 = MountService.this;
            super(s, flag, false);
            method = s1;
        }
    }

    class UnmountCallBack {

        void handleFinished() {
            doUnmountVolume(path, true, removeEncryption);
        }

        final boolean force;
        final String path;
        final boolean removeEncryption;
        int retries;
        final MountService this$0;

        UnmountCallBack(String s, boolean flag, boolean flag1) {
            this$0 = MountService.this;
            super();
            retries = 0;
            path = s;
            force = flag;
            removeEncryption = flag1;
        }
    }

    class DefaultContainerConnection
        implements ServiceConnection {

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            IMediaContainerService imediacontainerservice = com.android.internal.app.IMediaContainerService.Stub.asInterface(ibinder);
            mObbActionHandler.sendMessage(mObbActionHandler.obtainMessage(2, imediacontainerservice));
        }

        public void onServiceDisconnected(ComponentName componentname) {
        }

        final MountService this$0;

        DefaultContainerConnection() {
            this$0 = MountService.this;
            super();
        }
    }

    class ObbState
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            UnmountObbAction unmountobbaction = new UnmountObbAction(this, true);
            mObbActionHandler.sendMessage(mObbActionHandler.obtainMessage(1, unmountobbaction));
        }

        public IBinder getBinder() {
            return token.asBinder();
        }

        public void link() throws RemoteException {
            getBinder().linkToDeath(this, 0);
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder("ObbState{");
            stringbuilder.append("filename=");
            stringbuilder.append(filename);
            stringbuilder.append(",token=");
            stringbuilder.append(token.toString());
            stringbuilder.append(",callerUid=");
            stringbuilder.append(callerUid);
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        public void unlink() {
            getBinder().unlinkToDeath(this, 0);
        }

        public final int callerUid;
        String filename;
        final int nonce;
        final MountService this$0;
        final IObbActionListener token;

        public ObbState(String s, int i, IObbActionListener iobbactionlistener, int j) throws RemoteException {
            this$0 = MountService.this;
            super();
            filename = s;
            callerUid = i;
            token = iobbactionlistener;
            nonce = j;
        }
    }

    class VoldResponseCode {

        public static final int AsecListResult = 111;
        public static final int AsecPathResult = 211;
        public static final int OpFailedMediaBlank = 402;
        public static final int OpFailedMediaCorrupt = 403;
        public static final int OpFailedNoMedia = 401;
        public static final int OpFailedStorageBusy = 405;
        public static final int OpFailedStorageNotFound = 406;
        public static final int OpFailedVolNotMounted = 404;
        public static final int ShareEnabledResult = 212;
        public static final int ShareStatusResult = 210;
        public static final int StorageUsersListResult = 112;
        public static final int VolumeBadRemoval = 632;
        public static final int VolumeDiskInserted = 630;
        public static final int VolumeDiskRemoved = 631;
        public static final int VolumeListResult = 110;
        public static final int VolumeStateChange = 605;
        final MountService this$0;

        VoldResponseCode() {
            this$0 = MountService.this;
            super();
        }
    }

    class VolumeState {

        public static final int Checking = 3;
        public static final int Formatting = 6;
        public static final int Idle = 1;
        public static final int Init = -1;
        public static final int Mounted = 4;
        public static final int NoMedia = 0;
        public static final int Pending = 2;
        public static final int Shared = 7;
        public static final int SharedMnt = 8;
        public static final int Unmounting = 5;
        final MountService this$0;

        VolumeState() {
            this$0 = MountService.this;
            super();
        }
    }


    public MountService(Context context) {
        mUmsAvailable = false;
        mBooted = false;
        mConnectedSignal = new CountDownLatch(1);
        mAsecsScanned = new CountDownLatch(1);
        mSendUmsConnectedOnBoot = false;
        mEmulateExternalStorage = false;
        mContainerService = null;
        mContext = context;
        readStorageList();
        if(mPrimaryVolume != null) {
            mExternalStoragePath = mPrimaryVolume.getPath();
            mEmulateExternalStorage = mPrimaryVolume.isEmulated();
            if(mEmulateExternalStorage) {
                Slog.d("MountService", "using emulated external storage");
                mVolumeStates.put(mExternalStoragePath, "mounted");
            }
        }
        mPms = (PackageManagerService)ServiceManager.getService("package");
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.BOOT_COMPLETED");
        if(mPrimaryVolume != null && mPrimaryVolume.allowMassStorage())
            intentfilter.addAction("android.hardware.usb.action.USB_STATE");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter, null, null);
        mHandlerThread.start();
        mHandler = new MountServiceHandler(mHandlerThread.getLooper());
        mObbActionHandler = new ObbActionHandler(mHandlerThread.getLooper());
        mConnector = new NativeDaemonConnector(this, "vold", 500, "VoldConnector", 25);
        (new Thread(mConnector, "VoldConnector")).start();
    }

    private void addObbStateLocked(ObbState obbstate) throws RemoteException {
        IBinder ibinder = obbstate.getBinder();
        Object obj = (List)mObbMounts.get(ibinder);
        if(obj == null) {
            obj = new ArrayList();
            mObbMounts.put(ibinder, obj);
        } else {
            Iterator iterator = ((List) (obj)).iterator();
            while(iterator.hasNext()) 
                if(((ObbState)iterator.next()).filename.equals(obbstate.filename))
                    throw new IllegalStateException("Attempt to add ObbState twice. This indicates an error in the MountService logic.");
        }
        ((List) (obj)).add(obbstate);
        try {
            obbstate.link();
        }
        catch(RemoteException remoteexception) {
            ((List) (obj)).remove(obbstate);
            if(((List) (obj)).isEmpty())
                mObbMounts.remove(ibinder);
            throw remoteexception;
        }
        mObbPathToStateMap.put(obbstate.filename, obbstate);
    }

    private int doFormatVolume(String s) {
        int i = 0;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "format";
            aobj[1] = s;
            nativedaemonconnector.execute("volume", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            int j = nativedaemonconnectorexception.getCode();
            if(j == 401)
                i = -2;
            else
            if(j == 403)
                i = -4;
            else
                i = -1;
        }
        return i;
    }

    private boolean doGetVolumeShared(String s, String s1) {
        boolean flag = false;
        NativeDaemonEvent nativedaemonevent;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[3];
        aobj[0] = "shared";
        aobj[1] = s;
        aobj[2] = s1;
        nativedaemonevent = nativedaemonconnector.execute("volume", aobj);
        if(nativedaemonevent.getCode() == 212)
            flag = nativedaemonevent.getMessage().endsWith("enabled");
_L2:
        return flag;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        Slog.e("MountService", (new StringBuilder()).append("Failed to read response to volume shared ").append(s).append(" ").append(s1).toString());
        if(true) goto _L2; else goto _L1
_L1:
    }

    private int doMountVolume(String s) {
        int i = 0;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[2];
        aobj[0] = "mount";
        aobj[1] = s;
        nativedaemonconnector.execute("volume", aobj);
_L2:
        return i;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        String s1 = null;
        int j = nativedaemonconnectorexception.getCode();
        if(j == 401)
            i = -2;
        else
        if(j == 402) {
            updatePublicVolumeState(s, "nofs");
            s1 = "android.intent.action.MEDIA_NOFS";
            i = -3;
        } else
        if(j == 403) {
            updatePublicVolumeState(s, "unmountable");
            s1 = "android.intent.action.MEDIA_UNMOUNTABLE";
            i = -4;
        } else {
            i = -1;
        }
        if(s1 != null)
            sendStorageIntent(s1, s);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void doShareUnshareVolume(String s, String s1, boolean flag) {
        if(!s1.equals("ums")) {
            Object aobj1[] = new Object[1];
            aobj1[0] = s1;
            throw new IllegalArgumentException(String.format("Method %s not supported", aobj1));
        }
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            String s2;
            if(flag)
                s2 = "share";
            else
                s2 = "unshare";
            aobj[0] = s2;
            aobj[1] = s;
            aobj[2] = s1;
            nativedaemonconnector.execute("volume", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("MountService", "Failed to share/unshare", nativedaemonconnectorexception);
        }
    }

    private int doUnmountVolume(String s, boolean flag, boolean flag1) {
        char c = '\u0194';
        if(getVolumeState(s).equals("mounted")) goto _L2; else goto _L1
_L1:
        return c;
_L2:
        Runtime.getRuntime().gc();
        mPms.updateExternalMediaStatus(false, false);
        NativeDaemonConnector.Command command;
        Object aobj[] = new Object[2];
        aobj[0] = "unmount";
        aobj[1] = s;
        command = new NativeDaemonConnector.Command("volume", aobj);
        if(!flag1) goto _L4; else goto _L3
_L3:
        command.appendArg("force_and_revert");
_L6:
        mConnector.execute(command);
        synchronized(mAsecMountSet) {
            mAsecMountSet.clear();
        }
        c = '\0';
        continue; /* Loop/switch isn't completed */
_L4:
        if(!flag) goto _L6; else goto _L5
_L5:
        NativeDaemonConnectorException nativedaemonconnectorexception;
        int i;
        command.appendArg("force");
          goto _L6
        if(true) goto _L1; else goto _L7
_L7:
        exception;
        hashset;
        JVM INSTR monitorexit ;
        try {
            throw exception;
        }
        // Misplaced declaration of an exception variable
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            i = nativedaemonconnectorexception.getCode();
            if(i == c)
                c = '\uFFFB';
            else
            if(i == 405)
                c = '\uFFF9';
            else
                c = '\uFFFF';
        }
        continue; /* Loop/switch isn't completed */
    }

    private boolean getUmsEnabling() {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        boolean flag = mUmsEnabling;
        return flag;
    }

    private boolean isUidOwnerOfPackageOrSystem(String s, int i) {
        boolean flag = true;
        if(i != 1000) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        if(s == null)
            flag = false;
        else
        if(i != mPms.getPackageUid(s, UserId.getUserId(i)))
            flag = false;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void notifyShareAvailabilityChange(boolean flag) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        int i;
        mUmsAvailable = flag;
        i = -1 + mListeners.size();
_L1:
        MountServiceBinderListener mountservicebinderlistener;
        if(i < 0)
            break MISSING_BLOCK_LABEL_105;
        mountservicebinderlistener = (MountServiceBinderListener)mListeners.get(i);
        mountservicebinderlistener.mListener.onUsbMassStorageConnectionChanged(flag);
_L2:
        i--;
          goto _L1
        RemoteException remoteexception;
        remoteexception;
        Slog.e("MountService", "Listener dead");
        mListeners.remove(i);
          goto _L2
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Slog.e("MountService", "Listener failed", exception1);
          goto _L2
        arraylist;
        JVM INSTR monitorexit ;
        final String path;
        if(mBooted)
            sendUmsIntent(flag);
        else
            mSendUmsConnectedOnBoot = flag;
        path = Environment.getExternalStorageDirectory().getPath();
        if(!flag && getVolumeState(path).equals("shared"))
            (new Thread() {

                public void run() {
                    Slog.w("MountService", "Disabling UMS after cable disconnect");
                    doShareUnshareVolume(path, "ums", false);
                    int j = doMountVolume(path);
                    if(j != 0) {
                        Object aobj[] = new Object[2];
                        aobj[0] = path;
                        aobj[1] = Integer.valueOf(j);
                        Slog.e("MountService", String.format("Failed to remount {%s} on UMS enabled-disconnect (%d)", aobj));
                    }
_L1:
                    return;
                    Exception exception2;
                    exception2;
                    Slog.w("MountService", "Failed to mount media on UMS enabled-disconnect", exception2);
                      goto _L1
                }

                final MountService this$0;
                final String val$path;

             {
                this$0 = MountService.this;
                path = s;
                super();
            }
            }).start();
        return;
    }

    private void notifyVolumeStateChange(String s, String s1, int i, int j) {
        String s2;
        String s3;
        s2 = getVolumeState(s1);
        s3 = null;
        if(i == 7 && j != i)
            sendStorageIntent("android.intent.action.MEDIA_UNSHARED", s1);
          goto _L1
_L3:
        if(s3 != null)
            sendStorageIntent(s3, s1);
_L4:
        return;
_L1:
        if(j == -1 || j == 0) goto _L3; else goto _L2
_L2:
label0:
        {
            if(j == 1) {
                if(!s2.equals("bad_removal") && !s2.equals("nofs") && !s2.equals("unmountable") && !getUmsEnabling()) {
                    updatePublicVolumeState(s1, "unmounted");
                    s3 = "android.intent.action.MEDIA_UNMOUNTED";
                }
                continue; /* Loop/switch isn't completed */
            }
            if(j == 2)
                continue; /* Loop/switch isn't completed */
            if(j == 3) {
                updatePublicVolumeState(s1, "checking");
                s3 = "android.intent.action.MEDIA_CHECKING";
                continue; /* Loop/switch isn't completed */
            }
            if(j == 4) {
                updatePublicVolumeState(s1, "mounted");
                s3 = "android.intent.action.MEDIA_MOUNTED";
                continue; /* Loop/switch isn't completed */
            }
            if(j == 5) {
                s3 = "android.intent.action.MEDIA_EJECT";
                continue; /* Loop/switch isn't completed */
            }
            if(j == 6)
                continue; /* Loop/switch isn't completed */
            if(j == 7) {
                updatePublicVolumeState(s1, "unmounted");
                sendStorageIntent("android.intent.action.MEDIA_UNMOUNTED", s1);
                updatePublicVolumeState(s1, "shared");
                s3 = "android.intent.action.MEDIA_SHARED";
                continue; /* Loop/switch isn't completed */
            }
            if(j != 8)
                break label0;
            Slog.e("MountService", "Live shared mounts not supported yet!");
        }
          goto _L4
        Slog.e("MountService", (new StringBuilder()).append("Unhandled VolumeState {").append(j).append("}").toString());
        if(true) goto _L3; else goto _L5
_L5:
    }

    private void readStorageList() {
        Resources resources;
        XmlResourceParser xmlresourceparser;
        android.util.AttributeSet attributeset;
        resources = mContext.getResources();
        xmlresourceparser = resources.getXml(0x10f000c);
        attributeset = Xml.asAttributeSet(xmlresourceparser);
        XmlUtils.beginDocument(xmlresourceparser, "StorageList");
_L2:
        String s;
        XmlUtils.nextElement(xmlresourceparser);
        s = xmlresourceparser.getName();
        if(s == null) {
            int i1 = mVolumes.size();
            for(int j1 = 0; j1 < i1; j1++)
                ((StorageVolume)mVolumes.get(j1)).setStorageId(j1);

            break MISSING_BLOCK_LABEL_520;
        }
        if(!"storage".equals(s)) goto _L2; else goto _L1
_L1:
        TypedArray typedarray;
        CharSequence charsequence;
        int k;
        CharSequence charsequence1;
        boolean flag;
        boolean flag1;
        boolean flag2;
        int l;
        boolean flag3;
        long l1;
        typedarray = resources.obtainAttributes(attributeset, com.android.internal.R.styleable.Storage);
        charsequence = typedarray.getText(0);
        k = typedarray.getResourceId(1, -1);
        charsequence1 = typedarray.getText(1);
        flag = typedarray.getBoolean(2, false);
        flag1 = typedarray.getBoolean(3, false);
        flag2 = typedarray.getBoolean(4, false);
        l = typedarray.getInt(5, 0);
        flag3 = typedarray.getBoolean(6, false);
        l1 = 1024L * (1024L * (long)typedarray.getInt(7, 0));
        Slog.d("MountService", (new StringBuilder()).append("got storage path: ").append(charsequence).append(" description: ").append(charsequence1).append(" primary: ").append(flag).append(" removable: ").append(flag1).append(" emulated: ").append(flag2).append(" mtpReserve: ").append(l).append(" allowMassStorage: ").append(flag3).append(" maxFileSize: ").append(l1).toString());
        if(charsequence != null && charsequence1 != null) goto _L4; else goto _L3
_L3:
        Slog.e("MountService", "path or description is null in readStorageList");
_L9:
        typedarray.recycle();
          goto _L2
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        RuntimeException runtimeexception1 = new RuntimeException(xmlpullparserexception);
        throw runtimeexception1;
        Exception exception;
        exception;
        break MISSING_BLOCK_LABEL_346;
_L4:
        s1 = charsequence.toString();
        storagevolume = new StorageVolume(s1, k, flag1, flag2, l, flag3, l1);
        if(!flag) goto _L6; else goto _L5
_L5:
        if(mPrimaryVolume != null) goto _L8; else goto _L7
_L7:
        mPrimaryVolume = storagevolume;
_L6:
        if(mPrimaryVolume != storagevolume)
            break MISSING_BLOCK_LABEL_498;
        mVolumes.add(0, storagevolume);
_L10:
        mVolumeMap.put(s1, storagevolume);
          goto _L9
        ioexception;
        RuntimeException runtimeexception = new RuntimeException(ioexception);
        throw runtimeexception;
_L8:
        Slog.e("MountService", "multiple primary volumes in storage list");
          goto _L6
        mVolumes.add(storagevolume);
          goto _L10
        int i = mVolumes.size();
        IOException ioexception;
        String s1;
        StorageVolume storagevolume;
        for(int j = 0; j < i; j++)
            ((StorageVolume)mVolumes.get(j)).setStorageId(j);

        xmlresourceparser.close();
        throw exception;
        xmlresourceparser.close();
        return;
          goto _L9
    }

    private void removeObbStateLocked(ObbState obbstate) {
        IBinder ibinder = obbstate.getBinder();
        List list = (List)mObbMounts.get(ibinder);
        if(list != null) {
            if(list.remove(obbstate))
                obbstate.unlink();
            if(list.isEmpty())
                mObbMounts.remove(ibinder);
        }
        mObbPathToStateMap.remove(obbstate.filename);
    }

    private void sendStorageIntent(String s, String s1) {
        Intent intent = new Intent(s, Uri.parse((new StringBuilder()).append("file://").append(s1).toString()));
        intent.putExtra("storage_volume", (Parcelable)mVolumeMap.get(s1));
        Slog.d("MountService", (new StringBuilder()).append("sendStorageIntent ").append(intent).toString());
        mContext.sendBroadcast(intent);
    }

    private void sendUmsIntent(boolean flag) {
        Context context = mContext;
        String s;
        if(flag)
            s = "android.intent.action.UMS_CONNECTED";
        else
            s = "android.intent.action.UMS_DISCONNECTED";
        context.sendBroadcast(new Intent(s));
    }

    private void setUmsEnabling(boolean flag) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        mUmsEnabling = flag;
        return;
    }

    private void updatePublicVolumeState(String s, String s1) {
        String s2;
        synchronized(mVolumeStates) {
            s2 = (String)mVolumeStates.put(s, s1);
        }
        if(!s1.equals(s2)) goto _L2; else goto _L1
_L1:
        Object aobj[] = new Object[3];
        aobj[0] = s1;
        aobj[1] = s1;
        aobj[2] = s;
        Slog.w("MountService", String.format("Duplicate state transition (%s -> %s) for %s", aobj));
_L5:
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        ArrayList arraylist;
        int i;
        Slog.d("MountService", (new StringBuilder()).append("volume state changed for ").append(s).append(" (").append(s2).append(" -> ").append(s1).append(")").toString());
        MountServiceBinderListener mountservicebinderlistener;
        if(s.equals(mExternalStoragePath) && !mEmulateExternalStorage)
            if("unmounted".equals(s1)) {
                mPms.updateExternalMediaStatus(false, false);
                mObbActionHandler.sendMessage(mObbActionHandler.obtainMessage(5, s));
            } else
            if("mounted".equals(s1))
                mPms.updateExternalMediaStatus(true, false);
        arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        i = -1 + mListeners.size();
_L3:
        if(i < 0)
            break MISSING_BLOCK_LABEL_311;
        mountservicebinderlistener = (MountServiceBinderListener)mListeners.get(i);
        mountservicebinderlistener.mListener.onStorageStateChanged(s, s2, s1);
_L4:
        i--;
          goto _L3
        RemoteException remoteexception;
        remoteexception;
        Slog.e("MountService", "Listener dead");
        mListeners.remove(i);
          goto _L4
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception2;
        exception2;
        Slog.e("MountService", "Listener failed", exception2);
          goto _L4
        arraylist;
        JVM INSTR monitorexit ;
          goto _L5
    }

    private void validatePermission(String s) {
        if(mContext.checkCallingOrSelfPermission(s) != 0) {
            Object aobj[] = new Object[1];
            aobj[0] = s;
            throw new SecurityException(String.format("Requires %s permission", aobj));
        } else {
            return;
        }
    }

    private void waitForLatch(CountDownLatch countdownlatch) {
        if(countdownlatch != null) goto _L2; else goto _L1
_L1:
        return;
_L3:
        Slog.w("MountService", (new StringBuilder()).append("Thread ").append(Thread.currentThread().getName()).append(" still waiting for MountService ready...").toString());
_L2:
        boolean flag = countdownlatch.await(5000L, TimeUnit.MILLISECONDS);
        if(!flag) goto _L3; else goto _L1
        InterruptedException interruptedexception;
        interruptedexception;
        Slog.w("MountService", "Interrupt while waiting for MountService to be ready.");
          goto _L2
    }

    private void waitForReady() {
        waitForLatch(mConnectedSignal);
    }

    private void warnOnNotMounted() {
        if(!Environment.getExternalStorageState().equals("mounted"))
            Slog.w("MountService", "getSecureContainerList() called when storage not mounted");
    }

    public int changeEncryptionPassword(String s) {
        if(TextUtils.isEmpty(s))
            throw new IllegalArgumentException("password cannot be empty");
        mContext.enforceCallingOrSelfPermission("android.permission.CRYPT_KEEPER", "no permission to access the crypt keeper");
        waitForReady();
        int j;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[2];
        aobj[0] = "changepw";
        aobj[1] = s;
        j = Integer.parseInt(nativedaemonconnector.execute("cryptfs", aobj).getMessage());
        int i = j;
_L2:
        return i;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        i = nativedaemonconnectorexception.getCode();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public int createSecureContainer(String s, int i, String s1, String s2, int j, boolean flag) {
        int k;
        validatePermission("android.permission.ASEC_CREATE");
        waitForReady();
        warnOnNotMounted();
        k = 0;
        NativeDaemonConnector nativedaemonconnector;
        Object aobj[];
        nativedaemonconnector = mConnector;
        aobj = new Object[7];
        aobj[0] = "create";
        aobj[1] = s;
        aobj[2] = Integer.valueOf(i);
        aobj[3] = s1;
        aobj[4] = s2;
        aobj[5] = Integer.valueOf(j);
        if(!flag) goto _L2; else goto _L1
_L1:
        String s3 = "1";
_L3:
        aobj[6] = s3;
        nativedaemonconnector.execute("asec", aobj);
_L4:
        if(k == 0)
            synchronized(mAsecMountSet) {
                mAsecMountSet.add(s);
            }
        return k;
_L2:
        s3 = "0";
          goto _L3
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        k = -1;
          goto _L4
        exception;
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
          goto _L3
    }

    public int decryptStorage(String s) {
        if(TextUtils.isEmpty(s))
            throw new IllegalArgumentException("password cannot be empty");
        mContext.enforceCallingOrSelfPermission("android.permission.CRYPT_KEEPER", "no permission to access the crypt keeper");
        waitForReady();
        int i;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "checkpw";
            aobj[1] = s;
            i = Integer.parseInt(nativedaemonconnector.execute("cryptfs", aobj).getMessage());
            if(i == 0)
                mHandler.postDelayed(new Runnable() {

                    public void run() {
                        NativeDaemonConnector nativedaemonconnector1 = mConnector;
                        Object aobj1[] = new Object[1];
                        aobj1[0] = "restart";
                        nativedaemonconnector1.execute("cryptfs", aobj1);
_L1:
                        return;
                        NativeDaemonConnectorException nativedaemonconnectorexception1;
                        nativedaemonconnectorexception1;
                        Slog.e("MountService", "problem executing in background", nativedaemonconnectorexception1);
                          goto _L1
                    }

                    final MountService this$0;

             {
                this$0 = MountService.this;
                super();
            }
                }, 1000L);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            i = nativedaemonconnectorexception.getCode();
        }
        return i;
    }

    public int destroySecureContainer(String s, boolean flag) {
        validatePermission("android.permission.ASEC_DESTROY");
        waitForReady();
        warnOnNotMounted();
        Runtime.getRuntime().gc();
        int i = 0;
        try {
            Object aobj[] = new Object[2];
            aobj[0] = "destroy";
            aobj[1] = s;
            NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("asec", aobj);
            if(flag)
                command.appendArg("force");
            mConnector.execute(command);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            if(nativedaemonconnectorexception.getCode() == 405)
                i = -7;
            else
                i = -1;
        }
        if(i == 0)
            synchronized(mAsecMountSet) {
                if(mAsecMountSet.contains(s))
                    mAsecMountSet.remove(s);
            }
        return i;
        exception;
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ActivityManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L4:
        return;
_L2:
        Map map = mObbMounts;
        map;
        JVM INSTR monitorenter ;
        printwriter.println("  mObbMounts:");
        for(Iterator iterator = mObbMounts.entrySet().iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
            printwriter.print("    Key=");
            printwriter.println(((IBinder)entry1.getKey()).toString());
            Iterator iterator2 = ((List)entry1.getValue()).iterator();
            while(iterator2.hasNext())  {
                ObbState obbstate = (ObbState)iterator2.next();
                printwriter.print("      ");
                printwriter.println(obbstate.toString());
            }
        }

        break MISSING_BLOCK_LABEL_208;
        Exception exception;
        exception;
        throw exception;
        printwriter.println("");
        printwriter.println("  mObbPathToStateMap:");
        java.util.Map.Entry entry;
        for(Iterator iterator1 = mObbPathToStateMap.entrySet().iterator(); iterator1.hasNext(); printwriter.println(((ObbState)entry.getValue()).toString())) {
            entry = (java.util.Map.Entry)iterator1.next();
            printwriter.print("    ");
            printwriter.print((String)entry.getKey());
            printwriter.print(" -> ");
        }

        map;
        JVM INSTR monitorexit ;
        printwriter.println("");
        synchronized(mVolumes) {
            printwriter.println("  mVolumes:");
            int i = mVolumes.size();
            for(int j = 0; j < i; j++) {
                StorageVolume storagevolume = (StorageVolume)mVolumes.get(j);
                printwriter.print("    ");
                printwriter.println(storagevolume.toString());
            }

        }
        printwriter.println();
        printwriter.println("  mConnection:");
        mConnector.dump(filedescriptor, printwriter, as);
        if(true) goto _L4; else goto _L3
_L3:
        exception1;
        arraylist;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public int encryptStorage(String s) {
        int i = 0;
        if(TextUtils.isEmpty(s))
            throw new IllegalArgumentException("password cannot be empty");
        mContext.enforceCallingOrSelfPermission("android.permission.CRYPT_KEEPER", "no permission to access the crypt keeper");
        waitForReady();
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "enablecrypto";
            aobj[1] = "inplace";
            aobj[2] = s;
            nativedaemonconnector.execute("cryptfs", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            i = nativedaemonconnectorexception.getCode();
        }
        return i;
    }

    public int finalizeSecureContainer(String s) {
        validatePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        int i = 0;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[2];
            aobj[0] = "finalize";
            aobj[1] = s;
            nativedaemonconnector.execute("asec", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            i = -1;
        }
        return i;
    }

    public void finishMediaUpdate() {
        mHandler.sendEmptyMessage(2);
    }

    public int fixPermissionsSecureContainer(String s, int i, String s1) {
        validatePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        int j = 0;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[4];
            aobj[0] = "fixperms";
            aobj[1] = s;
            aobj[2] = Integer.valueOf(i);
            aobj[3] = s1;
            nativedaemonconnector.execute("asec", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            j = -1;
        }
        return j;
    }

    public int formatVolume(String s) {
        validatePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        waitForReady();
        return doFormatVolume(s);
    }

    public int getEncryptionState() {
        int i;
        i = -1;
        mContext.enforceCallingOrSelfPermission("android.permission.CRYPT_KEEPER", "no permission to access the crypt keeper");
        waitForReady();
        int j;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[1];
        aobj[0] = "cryptocomplete";
        j = Integer.parseInt(nativedaemonconnector.execute("cryptfs", aobj).getMessage());
        i = j;
_L2:
        return i;
        NumberFormatException numberformatexception;
        numberformatexception;
        Slog.w("MountService", "Unable to parse result from cryptfs cryptocomplete");
        continue; /* Loop/switch isn't completed */
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        Slog.w("MountService", "Error in communicating with cryptfs in validating");
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String getMountedObbPath(String s) {
        if(s == null)
            throw new IllegalArgumentException("filename cannot be null");
        waitForReady();
        warnOnNotMounted();
        String s2;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj1[] = new Object[2];
        aobj1[0] = "path";
        aobj1[1] = s;
        NativeDaemonEvent nativedaemonevent = nativedaemonconnector.execute("obb", aobj1);
        nativedaemonevent.checkCode(211);
        s2 = nativedaemonevent.getMessage();
        String s1 = s2;
_L2:
        return s1;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        int i = nativedaemonconnectorexception.getCode();
        if(i == 406) {
            s1 = null;
        } else {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            throw new IllegalStateException(String.format("Unexpected response code %d", aobj));
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String getSecureContainerFilesystemPath(String s) {
        validatePermission("android.permission.ASEC_ACCESS");
        waitForReady();
        warnOnNotMounted();
        String s2;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj2[] = new Object[2];
        aobj2[0] = "fspath";
        aobj2[1] = s;
        NativeDaemonEvent nativedaemonevent = nativedaemonconnector.execute("asec", aobj2);
        nativedaemonevent.checkCode(211);
        s2 = nativedaemonevent.getMessage();
        String s1 = s2;
_L2:
        return s1;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        int i = nativedaemonconnectorexception.getCode();
        if(i == 406) {
            Object aobj1[] = new Object[1];
            aobj1[0] = s;
            Slog.i("MountService", String.format("Container '%s' not found", aobj1));
            s1 = null;
        } else {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            throw new IllegalStateException(String.format("Unexpected response code %d", aobj));
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String[] getSecureContainerList() {
        validatePermission("android.permission.ASEC_ACCESS");
        waitForReady();
        warnOnNotMounted();
        String as1[];
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[1];
        aobj[0] = "list";
        as1 = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("asec", aobj), 111);
        String as[] = as1;
_L2:
        return as;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        as = new String[0];
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String getSecureContainerPath(String s) {
        validatePermission("android.permission.ASEC_ACCESS");
        waitForReady();
        warnOnNotMounted();
        String s2;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj2[] = new Object[2];
        aobj2[0] = "path";
        aobj2[1] = s;
        NativeDaemonEvent nativedaemonevent = nativedaemonconnector.execute("asec", aobj2);
        nativedaemonevent.checkCode(211);
        s2 = nativedaemonevent.getMessage();
        String s1 = s2;
_L2:
        return s1;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        int i = nativedaemonconnectorexception.getCode();
        if(i == 406) {
            Object aobj1[] = new Object[1];
            aobj1[0] = s;
            Slog.i("MountService", String.format("Container '%s' not found", aobj1));
            s1 = null;
        } else {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            throw new IllegalStateException(String.format("Unexpected response code %d", aobj));
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public int[] getStorageUsers(String s) {
        validatePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        waitForReady();
        int ai[];
        String as[];
        int i;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[2];
        aobj[0] = "users";
        aobj[1] = s;
        as = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("storage", aobj), 112);
        ai = new int[as.length];
        i = 0;
_L1:
        String as1[];
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_135;
        as1 = as[i].split(" ");
        ai[i] = Integer.parseInt(as1[0]);
        i++;
          goto _L1
        NumberFormatException numberformatexception;
        numberformatexception;
        try {
            Object aobj1[] = new Object[1];
            aobj1[0] = as1[0];
            Slog.e("MountService", String.format("Error parsing pid %s", aobj1));
            ai = new int[0];
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            Slog.e("MountService", "Failed to retrieve storage users list", nativedaemonconnectorexception);
            ai = new int[0];
        }
        return ai;
    }

    public Parcelable[] getVolumeList() {
        ArrayList arraylist = mVolumes;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mVolumes.size();
        Parcelable aparcelable[] = new Parcelable[i];
        for(int j = 0; j < i; j++)
            aparcelable[j] = (Parcelable)mVolumes.get(j);

        return aparcelable;
    }

    public String getVolumeState(String s) {
        HashMap hashmap = mVolumeStates;
        hashmap;
        JVM INSTR monitorenter ;
label0:
        {
            String s1 = (String)mVolumeStates.get(s);
            if(s1 == null) {
                Slog.w("MountService", (new StringBuilder()).append("getVolumeState(").append(s).append("): Unknown volume").toString());
                if(SystemProperties.get("vold.encrypt_progress").length() == 0)
                    break label0;
                s1 = "removed";
            }
            return s1;
        }
        throw new IllegalArgumentException();
    }

    public boolean isExternalStorageEmulated() {
        return mEmulateExternalStorage;
    }

    public boolean isObbMounted(String s) {
        if(s == null)
            throw new IllegalArgumentException("filename cannot be null");
        Map map = mObbMounts;
        map;
        JVM INSTR monitorenter ;
        boolean flag = mObbPathToStateMap.containsKey(s);
        return flag;
    }

    public boolean isSecureContainerMounted(String s) {
        validatePermission("android.permission.ASEC_ACCESS");
        waitForReady();
        warnOnNotMounted();
        HashSet hashset = mAsecMountSet;
        hashset;
        JVM INSTR monitorenter ;
        boolean flag = mAsecMountSet.contains(s);
        return flag;
    }

    public boolean isUsbMassStorageConnected() {
        waitForReady();
        if(!getUmsEnabling()) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        flag = mUmsAvailable;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean isUsbMassStorageEnabled() {
        waitForReady();
        return doGetVolumeShared(Environment.getExternalStorageDirectory().getPath(), "ums");
    }

    public void monitor() {
        if(mConnector != null)
            mConnector.monitor();
    }

    public void mountObb(String s, String s1, IObbActionListener iobbactionlistener, int i) throws RemoteException {
        if(s == null)
            throw new IllegalArgumentException("filename cannot be null");
        if(iobbactionlistener == null) {
            throw new IllegalArgumentException("token cannot be null");
        } else {
            MountObbAction mountobbaction = new MountObbAction(new ObbState(s, Binder.getCallingUid(), iobbactionlistener, i), s1);
            mObbActionHandler.sendMessage(mObbActionHandler.obtainMessage(1, mountobbaction));
            return;
        }
    }

    public int mountSecureContainer(String s, String s1, int i) {
        validatePermission("android.permission.ASEC_MOUNT_UNMOUNT");
        waitForReady();
        warnOnNotMounted();
        HashSet hashset = mAsecMountSet;
        hashset;
        JVM INSTR monitorenter ;
        if(!mAsecMountSet.contains(s)) goto _L2; else goto _L1
_L1:
        byte byte0 = -6;
          goto _L3
_L2:
        byte0 = 0;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[4];
        aobj[0] = "mount";
        aobj[1] = s;
        aobj[2] = s1;
        aobj[3] = Integer.valueOf(i);
        nativedaemonconnector.execute("asec", aobj);
_L4:
        if(byte0 != 0)
            break; /* Loop/switch isn't completed */
        HashSet hashset1 = mAsecMountSet;
        hashset1;
        JVM INSTR monitorenter ;
        mAsecMountSet.add(s);
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        if(nativedaemonconnectorexception.getCode() != 405)
            byte0 = -1;
        if(true) goto _L4; else goto _L3
_L3:
        return byte0;
    }

    public int mountVolume(String s) {
        validatePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        waitForReady();
        return doMountVolume(s);
    }

    public void onDaemonConnected() {
        (new Thread("MountService#onDaemonConnected") {

            public void run() {
                String as[];
                int i;
                int j;
                NativeDaemonConnector nativedaemonconnector = mConnector;
                Object aobj[] = new Object[1];
                aobj[0] = "list";
                as = NativeDaemonEvent.filterMessageList(nativedaemonconnector.executeForList("volume", aobj), 110);
                i = as.length;
                j = 0;
_L9:
                if(j >= i) goto _L2; else goto _L1
_L1:
                String s;
                int k;
                String as1[] = as[j].split(" ");
                s = as1[1];
                k = Integer.parseInt(as1[2]);
                if(k != 0) goto _L4; else goto _L3
_L3:
                String s1 = "removed";
_L8:
                if(s1 != null)
                    updatePublicVolumeState(s, s1);
                  goto _L5
_L11:
                if(k != 4) goto _L7; else goto _L6
_L6:
                s1 = "mounted";
                Slog.i("MountService", "Media already mounted on daemon connection");
                  goto _L8
                Exception exception;
                exception;
                Slog.e("MountService", "Error processing initial volume state", exception);
                updatePublicVolumeState(mExternalStoragePath, "removed");
_L2:
                mConnectedSignal.countDown();
                mConnectedSignal = null;
                mPms.scanAvailableAsecs();
                mAsecsScanned.countDown();
                mAsecsScanned = null;
                return;
_L7:
                if(k != 7)
                    break MISSING_BLOCK_LABEL_225;
                s1 = "shared";
                Slog.i("MountService", "Media shared on daemon connection");
                  goto _L8
                Object aobj1[] = new Object[1];
                aobj1[0] = Integer.valueOf(k);
                throw new Exception(String.format("Unexpected state %d", aobj1));
_L5:
                j++;
                  goto _L9
_L4:
                if(k != 1) goto _L11; else goto _L10
_L10:
                s1 = "unmounted";
                  goto _L8
            }

            final MountService this$0;

             {
                this$0 = MountService.this;
                super(s);
            }
        }).start();
    }

    public boolean onEvent(int i, String s, String as[]) {
        if(i != 605) goto _L2; else goto _L1
_L1:
        notifyVolumeStateChange(as[2], as[3], Integer.parseInt(as[7]), Integer.parseInt(as[10]));
_L5:
        boolean flag = true;
_L6:
        return flag;
_L2:
        String s1;
        final String path;
        if(i != 630 && i != 631 && i != 632)
            break MISSING_BLOCK_LABEL_291;
        s1 = null;
        as[2];
        path = as[3];
        try {
            String as1[] = as[6].substring(1, -1 + as[6].length()).split(":");
            Integer.parseInt(as1[0]);
            Integer.parseInt(as1[1]);
        }
        catch(Exception exception) {
            Slog.e("MountService", "Failed to parse major/minor", exception);
        }
        if(i != 630) goto _L4; else goto _L3
_L3:
        (new Thread() {

            public void run() {
                int j = doMountVolume(path);
                if(j != 0) {
                    Object aobj1[] = new Object[1];
                    aobj1[0] = Integer.valueOf(j);
                    Slog.w("MountService", String.format("Insertion mount failed (%d)", aobj1));
                }
_L1:
                return;
                Exception exception1;
                exception1;
                Slog.w("MountService", "Failed to mount media on insertion", exception1);
                  goto _L1
            }

            final MountService this$0;
            final String val$path;

             {
                this$0 = MountService.this;
                path = s;
                super();
            }
        }).start();
_L7:
        if(s1 != null)
            sendStorageIntent(s1, path);
          goto _L5
_L4:
label0:
        {
            if(i != 631)
                break MISSING_BLOCK_LABEL_227;
            if(!getVolumeState(path).equals("bad_removal"))
                break label0;
            flag = true;
        }
          goto _L6
        updatePublicVolumeState(path, "unmounted");
        sendStorageIntent("unmounted", path);
        updatePublicVolumeState(path, "removed");
        s1 = "android.intent.action.MEDIA_REMOVED";
          goto _L7
        if(i == 632) {
            updatePublicVolumeState(path, "unmounted");
            updatePublicVolumeState(path, "bad_removal");
            s1 = "android.intent.action.MEDIA_BAD_REMOVAL";
        } else {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            Slog.e("MountService", String.format("Unknown code {%d}", aobj));
        }
          goto _L7
        flag = false;
          goto _L6
    }

    public void registerListener(IMountServiceListener imountservicelistener) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        MountServiceBinderListener mountservicebinderlistener = new MountServiceBinderListener(imountservicelistener);
        try {
            imountservicelistener.asBinder().linkToDeath(mountservicebinderlistener, 0);
            mListeners.add(mountservicebinderlistener);
        }
        catch(RemoteException remoteexception) {
            Slog.e("MountService", "Failed to link to listener death");
        }
        arraylist;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public int renameSecureContainer(String s, String s1) {
        byte byte0;
        validatePermission("android.permission.ASEC_RENAME");
        waitForReady();
        warnOnNotMounted();
        synchronized(mAsecMountSet) {
            if(mAsecMountSet.contains(s) || mAsecMountSet.contains(s1)) {
                byte0 = -6;
                break MISSING_BLOCK_LABEL_117;
            }
        }
        byte0 = 0;
        try {
            NativeDaemonConnector nativedaemonconnector = mConnector;
            Object aobj[] = new Object[3];
            aobj[0] = "rename";
            aobj[1] = s;
            aobj[2] = s1;
            nativedaemonconnector.execute("asec", aobj);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            byte0 = -1;
        }
          goto _L1
        exception;
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
        return byte0;
    }

    public void setUsbMassStorageEnabled(boolean flag) {
        waitForReady();
        validatePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        String s = Environment.getExternalStorageDirectory().getPath();
        String s1 = getVolumeState(s);
        if(flag && s1.equals("mounted")) {
            setUmsEnabling(flag);
            UmsEnableCallBack umsenablecallback = new UmsEnableCallBack(s, "ums", true);
            mHandler.sendMessage(mHandler.obtainMessage(1, umsenablecallback));
            setUmsEnabling(false);
        }
        if(!flag) {
            doShareUnshareVolume(s, "ums", flag);
            if(doMountVolume(s) != 0)
                Slog.e("MountService", (new StringBuilder()).append("Failed to remount ").append(s).append(" after disabling share method ").append("ums").toString());
        }
    }

    public void shutdown(IMountShutdownObserver imountshutdownobserver) {
        validatePermission("android.permission.SHUTDOWN");
        Slog.i("MountService", "Shutting down");
        HashMap hashmap = mVolumeStates;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mVolumeStates.keySet().iterator();
_L7:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        String s;
        String s1;
        s = (String)iterator.next();
        s1 = (String)mVolumeStates.get(s);
        if(!s1.equals("shared")) goto _L4; else goto _L3
_L3:
        setUsbMassStorageEnabled(false);
_L9:
        if(!s1.equals("mounted")) goto _L6; else goto _L5
_L5:
        ShutdownCallBack shutdowncallback = new ShutdownCallBack(s, imountshutdownobserver);
        mHandler.sendMessage(mHandler.obtainMessage(1, shutdowncallback));
          goto _L7
        Exception exception;
        exception;
        throw exception;
_L4:
        if(!s1.equals("checking")) goto _L9; else goto _L8
_L8:
        int i = 30;
_L10:
        boolean flag = s1.equals("checking");
        int j;
        if(!flag)
            break MISSING_BLOCK_LABEL_260;
        j = i - 1;
        if(i < 0)
            break MISSING_BLOCK_LABEL_212;
        Thread.sleep(1000L);
        s1 = Environment.getExternalStorageState();
        i = j;
          goto _L10
        InterruptedException interruptedexception;
        interruptedexception;
        Slog.e("MountService", "Interrupted while waiting for media", interruptedexception);
_L12:
        if(j == 0)
            Slog.e("MountService", "Timed out waiting for media to check");
          goto _L9
_L6:
        if(imountshutdownobserver == null) goto _L7; else goto _L11
_L11:
        imountshutdownobserver.onShutDownComplete(0);
          goto _L7
        RemoteException remoteexception;
        remoteexception;
        Slog.w("MountService", "RemoteException when shutting down");
          goto _L7
_L2:
        return;
        j = i;
          goto _L12
    }

    public void unmountObb(String s, boolean flag, IObbActionListener iobbactionlistener, int i) throws RemoteException {
        if(s == null) {
            throw new IllegalArgumentException("filename cannot be null");
        } else {
            UnmountObbAction unmountobbaction = new UnmountObbAction(new ObbState(s, Binder.getCallingUid(), iobbactionlistener, i), flag);
            mObbActionHandler.sendMessage(mObbActionHandler.obtainMessage(1, unmountobbaction));
            return;
        }
    }

    public int unmountSecureContainer(String s, boolean flag) {
        byte byte0;
        validatePermission("android.permission.ASEC_MOUNT_UNMOUNT");
        waitForReady();
        warnOnNotMounted();
        synchronized(mAsecMountSet) {
            if(!mAsecMountSet.contains(s)) {
                byte0 = -5;
                break MISSING_BLOCK_LABEL_179;
            }
        }
        Runtime.getRuntime().gc();
        byte0 = 0;
        HashSet hashset1;
        try {
            Object aobj[] = new Object[2];
            aobj[0] = "unmount";
            aobj[1] = s;
            NativeDaemonConnector.Command command = new NativeDaemonConnector.Command("asec", aobj);
            if(flag)
                command.appendArg("force");
            mConnector.execute(command);
        }
        catch(NativeDaemonConnectorException nativedaemonconnectorexception) {
            if(nativedaemonconnectorexception.getCode() == 405)
                byte0 = -7;
            else
                byte0 = -1;
        }
        if(byte0 != 0)
            break MISSING_BLOCK_LABEL_179;
        hashset1 = mAsecMountSet;
        hashset1;
        JVM INSTR monitorenter ;
        mAsecMountSet.remove(s);
        break MISSING_BLOCK_LABEL_179;
        exception;
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
        return byte0;
    }

    public void unmountVolume(String s, boolean flag, boolean flag1) {
        validatePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        waitForReady();
        String s1 = getVolumeState(s);
        if(!"unmounted".equals(s1) && !"removed".equals(s1) && !"shared".equals(s1) && !"unmountable".equals(s1)) {
            UnmountCallBack unmountcallback = new UnmountCallBack(s, flag, flag1);
            mHandler.sendMessage(mHandler.obtainMessage(1, unmountcallback));
        }
    }

    public void unregisterListener(IMountServiceListener imountservicelistener) {
        ArrayList arraylist = mListeners;
        arraylist;
        JVM INSTR monitorenter ;
        Iterator iterator = mListeners.iterator();
        do {
            if(!iterator.hasNext())
                break;
            MountServiceBinderListener mountservicebinderlistener = (MountServiceBinderListener)iterator.next();
            if(mountservicebinderlistener.mListener != imountservicelistener)
                continue;
            mListeners.remove(mListeners.indexOf(mountservicebinderlistener));
            break;
        } while(true);
        return;
    }

    public int verifyEncryptionPassword(String s) throws RemoteException {
        if(Binder.getCallingUid() != 1000)
            throw new SecurityException("no permission to access the crypt keeper");
        mContext.enforceCallingOrSelfPermission("android.permission.CRYPT_KEEPER", "no permission to access the crypt keeper");
        if(TextUtils.isEmpty(s))
            throw new IllegalArgumentException("password cannot be empty");
        waitForReady();
        int j;
        NativeDaemonConnector nativedaemonconnector = mConnector;
        Object aobj[] = new Object[2];
        aobj[0] = "verifypw";
        aobj[1] = s;
        NativeDaemonEvent nativedaemonevent = nativedaemonconnector.execute("cryptfs", aobj);
        Slog.i("MountService", (new StringBuilder()).append("cryptfs verifypw => ").append(nativedaemonevent.getMessage()).toString());
        j = Integer.parseInt(nativedaemonevent.getMessage());
        int i = j;
_L2:
        return i;
        NativeDaemonConnectorException nativedaemonconnectorexception;
        nativedaemonconnectorexception;
        i = nativedaemonconnectorexception.getCode();
        if(true) goto _L2; else goto _L1
_L1:
    }

    void waitForAsecScan() {
        waitForLatch(mAsecsScanned);
    }

    private static final int CRYPTO_ALGORITHM_KEY_SIZE = 128;
    private static final boolean DEBUG_EVENTS = false;
    private static final boolean DEBUG_OBB = false;
    private static final boolean DEBUG_UNMOUNT = false;
    static final ComponentName DEFAULT_CONTAINER_COMPONENT = new ComponentName("com.android.defcontainer", "com.android.defcontainer.DefaultContainerService");
    private static final int H_UNMOUNT_MS = 3;
    private static final int H_UNMOUNT_PM_DONE = 2;
    private static final int H_UNMOUNT_PM_UPDATE = 1;
    private static final boolean LOCAL_LOGD = false;
    private static final int MAX_CONTAINERS = 250;
    private static final int MAX_UNMOUNT_RETRIES = 4;
    private static final int OBB_FLUSH_MOUNT_STATE = 5;
    private static final int OBB_MCS_BOUND = 2;
    private static final int OBB_MCS_RECONNECT = 4;
    private static final int OBB_MCS_UNBIND = 3;
    private static final int OBB_RUN_ACTION = 1;
    private static final int PBKDF2_HASH_ROUNDS = 1024;
    private static final int RETRY_UNMOUNT_DELAY = 30;
    private static final String TAG = "MountService";
    private static final String TAG_STORAGE = "storage";
    private static final String TAG_STORAGE_LIST = "StorageList";
    private static final String VOLD_TAG = "VoldConnector";
    private static final boolean WATCHDOG_ENABLE;
    private final HashSet mAsecMountSet = new HashSet();
    private CountDownLatch mAsecsScanned;
    private boolean mBooted;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            boolean flag;
            String s;
            flag = true;
            s = intent.getAction();
            if(!s.equals("android.intent.action.BOOT_COMPLETED")) goto _L2; else goto _L1
_L1:
            mBooted = flag;
            if("simulator".equals(SystemProperties.get("ro.product.device")))
                notifyVolumeStateChange(null, "/sdcard", 0, 4);
            else
                (new Thread() {

                    public void run() {
                        int i;
                        String as[];
                        String as1[];
                        int k;
                        synchronized(mVolumeStates) {
                            Set set = mVolumeStates.keySet();
                            i = set.size();
                            as = (String[])set.toArray(new String[i]);
                            as1 = new String[i];
                            for(int j = 0; j < i; j++)
                                as1[j] = (String)mVolumeStates.get(as[j]);

                        }
                        k = 0;
_L4:
                        if(k >= i)
                            break; /* Loop/switch isn't completed */
                        String s1;
                        String s2;
                        s1 = as[k];
                        s2 = as1[k];
                        if(!s2.equals("unmounted")) goto _L2; else goto _L1
_L1:
                        int l = doMountVolume(s1);
                        if(l != 0) {
                            Object aobj[] = new Object[1];
                            aobj[0] = Integer.valueOf(l);
                            Slog.e("MountService", String.format("Boot-time mount failed (%d)", aobj));
                        }
_L6:
                        k++;
                        if(true) goto _L4; else goto _L3
                        exception1;
                        hashmap;
                        JVM INSTR monitorexit ;
                        try {
                            throw exception1;
                        }
                        catch(Exception exception) {
                            Slog.e("MountService", "Boot-time mount exception", exception);
                        }
_L7:
                        return;
_L2:
                        if(!s2.equals("shared")) goto _L6; else goto _L5
_L5:
                        notifyVolumeStateChange(null, s1, 0, 7);
                          goto _L6
_L3:
                        if(mEmulateExternalStorage)
                            notifyVolumeStateChange(null, Environment.getExternalStorageDirectory().getPath(), 0, 4);
                        if(mSendUmsConnectedOnBoot) {
                            sendUmsIntent(true);
                            mSendUmsConnectedOnBoot = false;
                        }
                          goto _L7
                    }

                    final _cls1 this$1;

                     {
                        this$1 = _cls1.this;
                        super();
                    }
                }).start();
_L4:
            return;
_L2:
            if(s.equals("android.hardware.usb.action.USB_STATE")) {
                if(!intent.getBooleanExtra("connected", false) || !intent.getBooleanExtra("mass_storage", false))
                    flag = false;
                notifyShareAvailabilityChange(flag);
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final MountService this$0;

             {
                this$0 = MountService.this;
                super();
            }
    };
    private CountDownLatch mConnectedSignal;
    private NativeDaemonConnector mConnector;
    private IMediaContainerService mContainerService;
    private Context mContext;
    private final DefaultContainerConnection mDefContainerConn = new DefaultContainerConnection();
    private boolean mEmulateExternalStorage;
    private String mExternalStoragePath;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread("MountService");
    private final ArrayList mListeners = new ArrayList();
    private final ObbActionHandler mObbActionHandler;
    private final Map mObbMounts = new HashMap();
    private final Map mObbPathToStateMap = new HashMap();
    private PackageManagerService mPms;
    private StorageVolume mPrimaryVolume;
    private boolean mSendUmsConnectedOnBoot;
    private boolean mUmsAvailable;
    private boolean mUmsEnabling;
    private final HashMap mVolumeMap = new HashMap();
    private final HashMap mVolumeStates = new HashMap();
    private final ArrayList mVolumes = new ArrayList();






/*
    static boolean access$1002(MountService mountservice, boolean flag) {
        mountservice.mSendUmsConnectedOnBoot = flag;
        return flag;
    }

*/









/*
    static CountDownLatch access$1702(MountService mountservice, CountDownLatch countdownlatch) {
        mountservice.mConnectedSignal = countdownlatch;
        return countdownlatch;
    }

*/



/*
    static CountDownLatch access$1802(MountService mountservice, CountDownLatch countdownlatch) {
        mountservice.mAsecsScanned = countdownlatch;
        return countdownlatch;
    }

*/



/*
    static IMediaContainerService access$1902(MountService mountservice, IMediaContainerService imediacontainerservice) {
        mountservice.mContainerService = imediacontainerservice;
        return imediacontainerservice;
    }

*/














/*
    static boolean access$502(MountService mountservice, boolean flag) {
        mountservice.mBooted = flag;
        return flag;
    }

*/




}
