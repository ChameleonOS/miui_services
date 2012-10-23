// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.connectivity;

import android.app.NotificationManager;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.*;
import android.util.Log;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import java.io.*;
import java.nio.charset.Charsets;
import java.util.Arrays;
import java.util.List;

public class Vpn extends android.net.INetworkManagementEventObserver.Stub {
    private class LegacyVpnRunner extends Thread {

        private void checkpoint(boolean flag) throws InterruptedException {
            long l = 1L;
            long l1 = SystemClock.elapsedRealtime();
            if(mTimer == -1L) {
                mTimer = l1;
                Thread.sleep(l);
            } else
            if(l1 - mTimer <= 60000L) {
                if(flag)
                    l = 200L;
                Thread.sleep(l);
            } else {
                mInfo.state = 4;
                throw new IllegalStateException("Time is up");
            }
        }

        private void execute() {
            String as2[];
            int i1;
            int j1;
            checkpoint(false);
            mInfo.state = 1;
            as2 = mDaemons;
            i1 = as2.length;
            j1 = 0;
_L5:
            if(j1 >= i1) goto _L2; else goto _L1
_L1:
            String s5 = as2[j1];
            for(String s6 = (new StringBuilder()).append("init.svc.").append(s5).toString(); !"stopped".equals(SystemProperties.get(s6, "stopped")); checkpoint(true));
              goto _L3
            Exception exception1;
            exception1;
            Log.i("LegacyVpnRunner", "Aborting", exception1);
            exit();
            if(mInfo.state == 1) {
                String as1[] = mDaemons;
                int k = as1.length;
                for(int l = 0; l < k; l++)
                    SystemProperties.set("ctl.stop", as1[l]);

            }
              goto _L4
_L3:
            j1++;
              goto _L5
_L2:
            File file;
            file = new File("/data/misc/vpn/state");
            file.delete();
            if(file.exists())
                throw new IllegalStateException("Cannot delete the state");
            break MISSING_BLOCK_LABEL_235;
            Exception exception;
            exception;
              goto _L6
            boolean flag;
            int l1;
            (new File("/data/misc/vpn/abort")).delete();
            flag = false;
            as3 = mArguments;
            k1 = as3.length;
            l1 = 0;
_L29:
            if(l1 >= k1) goto _L8; else goto _L7
_L7:
            as8 = as3[l1];
            LegacyVpnInfo legacyvpninfo;
            int i2;
            int l2;
            Exception exception4;
            if(flag || as8 != null)
                flag = true;
            else
                flag = false;
              goto _L9
_L8:
            if(flag) goto _L11; else goto _L10
_L10:
            mInfo.state = 0;
            if(mInfo.state == 1) {
                String as7[] = mDaemons;
                int l3 = as7.length;
                for(int i4 = 0; i4 < l3; i4++)
                    SystemProperties.set("ctl.stop", as7[i4]);

            }
              goto _L12
_L11:
            mInfo.state = 2;
            i2 = 0;
_L30:
            if(i2 >= mDaemons.length) goto _L14; else goto _L13
_L13:
            as6 = mArguments[i2];
            if(as6 != null) goto _L16; else goto _L15
_L16:
            String s3 = mDaemons[i2];
            SystemProperties.set("ctl.start", s3);
            for(String s4 = (new StringBuilder()).append("init.svc.").append(s3).toString(); !"running".equals(SystemProperties.get(s4)); checkpoint(true));
            mSockets[i2] = new LocalSocket();
            localsocketaddress = new LocalSocketAddress(s3, android.net.LocalSocketAddress.Namespace.RESERVED);
_L18:
            mSockets[i2].connect(localsocketaddress);
              goto _L17
            exception3;
            checkpoint(true);
              goto _L18
_L17:
            mSockets[i2].setSoTimeout(500);
            OutputStream outputstream = mSockets[i2].getOutputStream();
            int i3 = as6.length;
            for(int j3 = 0; j3 < i3; j3++) {
                byte abyte0[] = as6[j3].getBytes(Charsets.UTF_8);
                Exception exception3;
                if(abyte0.length >= 65535)
                    throw new IllegalArgumentException("Argument is too large");
                outputstream.write(abyte0.length >> 8);
                outputstream.write(abyte0.length);
                outputstream.write(abyte0);
                checkpoint(false);
            }

            outputstream.write(255);
            outputstream.write(255);
            outputstream.flush();
            inputstream = mSockets[i2].getInputStream();
_L20:
            k3 = inputstream.read();
            if(k3 == -1) goto _L15; else goto _L19
_L19:
            checkpoint(true);
              goto _L20
_L22:
            checkpoint(true);
_L14:
            if(file.exists())
                break; /* Loop/switch isn't completed */
            l2 = 0;
_L31:
            if(l2 < mDaemons.length) {
                String s2 = mDaemons[l2];
                if(mArguments[l2] != null && !"running".equals(SystemProperties.get((new StringBuilder()).append("init.svc.").append(s2).toString())))
                    throw new IllegalStateException((new StringBuilder()).append(s2).append(" is dead").toString());
                break MISSING_BLOCK_LABEL_1378;
            }
            if(true) goto _L22; else goto _L21
_L21:
            String as4[] = FileUtils.readTextFile(file, 0, null).split("\n", -1);
            if(as4.length != 6)
                throw new IllegalStateException("Cannot parse the state");
            mConfig.interfaze = as4[0].trim();
            mConfig.addresses = as4[1].trim();
            if(mConfig.routes == null || mConfig.routes.isEmpty())
                mConfig.routes = as4[2].trim();
            if(mConfig.dnsServers == null || mConfig.dnsServers.size() == 0) {
                String s = as4[3].trim();
                if(!s.isEmpty())
                    mConfig.dnsServers = Arrays.asList(s.split(" "));
            }
            if(mConfig.searchDomains == null || mConfig.searchDomains.size() == 0) {
                String s1 = as4[4].trim();
                if(!s1.isEmpty())
                    mConfig.searchDomains = Arrays.asList(s1.split(" "));
            }
            jniSetRoutes(mConfig.interfaze, mConfig.routes);
            vpn = Vpn.this;
            vpn;
            JVM INSTR monitorenter ;
            checkpoint(false);
            if(jniCheck(mConfig.interfaze) == 0) {
                IllegalStateException illegalstateexception = new IllegalStateException((new StringBuilder()).append(mConfig.interfaze).append(" is gone").toString());
                throw illegalstateexception;
            }
            break MISSING_BLOCK_LABEL_1085;
            exception2;
            throw exception2;
            mInterface = mConfig.interfaze;
            mCallback.override(mConfig.dnsServers, mConfig.searchDomains);
            showNotification(mConfig, null, null);
            Log.i("LegacyVpnRunner", "Connected!");
            mInfo.state = 3;
            mInfo.intent = VpnConfig.getIntentForStatusPanel(mContext, null);
            vpn;
            JVM INSTR monitorexit ;
            if(mInfo.state == 1) {
                String as5[] = mDaemons;
                int j2 = as5.length;
                for(int k2 = 0; k2 < j2; k2++)
                    SystemProperties.set("ctl.stop", as5[k2]);

            }
              goto _L23
_L6:
            String as3[][];
            int k1;
            Vpn vpn;
            Exception exception2;
            String as6[];
            LocalSocketAddress localsocketaddress;
            InputStream inputstream;
            int k3;
            String as8[];
            if(mInfo.state == 1) {
                String as[] = mDaemons;
                int i = as.length;
                for(int j = 0; j < i; j++)
                    SystemProperties.set("ctl.stop", as[j]);

            }
            if(mInfo.state == 1 || mInfo.state == 2)
                mInfo.state = 5;
            throw exception;
            exception4;
              goto _L19
_L4:
            if(mInfo.state != 1 && mInfo.state != 2) goto _L25; else goto _L24
_L24:
            legacyvpninfo = mInfo;
_L28:
            legacyvpninfo.state = 5;
_L25:
            return;
_L12:
            if(mInfo.state != 1 && mInfo.state != 2) goto _L25; else goto _L26
_L26:
            legacyvpninfo = mInfo;
            continue; /* Loop/switch isn't completed */
_L23:
            if(mInfo.state != 1 && mInfo.state != 2) goto _L25; else goto _L27
_L27:
            legacyvpninfo = mInfo;
            if(true) goto _L28; else goto _L9
_L9:
            l1++;
              goto _L29
_L15:
            i2++;
              goto _L30
            l2++;
              goto _L31
        }

        public void check(String s) {
            if(s.equals(mOuterInterface)) {
                Log.i("LegacyVpnRunner", (new StringBuilder()).append("Legacy VPN is going down with ").append(s).toString());
                exit();
            }
        }

        public void exit() {
            interrupt();
            LocalSocket alocalsocket[] = mSockets;
            int i = alocalsocket.length;
            int j = 0;
            while(j < i)  {
                LocalSocket localsocket = alocalsocket[j];
                try {
                    localsocket.close();
                }
                catch(Exception exception) { }
                j++;
            }
        }

        public LegacyVpnInfo getInfo() {
            if(mInfo.state == 3 && mInterface == null) {
                mInfo.state = 0;
                mInfo.intent = null;
            }
            return mInfo;
        }

        public void run() {
            Log.v("LegacyVpnRunner", "Waiting");
            "LegacyVpnRunner";
            JVM INSTR monitorenter ;
            Log.v("LegacyVpnRunner", "Executing");
            execute();
            return;
        }

        private static final String TAG = "LegacyVpnRunner";
        private final String mArguments[][];
        private final VpnConfig mConfig;
        private final String mDaemons[];
        private final LegacyVpnInfo mInfo = new LegacyVpnInfo();
        private final String mOuterInterface;
        private final LocalSocket mSockets[];
        private long mTimer;
        final Vpn this$0;

        public LegacyVpnRunner(VpnConfig vpnconfig, String as[], String as1[]) {
            this$0 = Vpn.this;
            super("LegacyVpnRunner");
            mTimer = -1L;
            mConfig = vpnconfig;
            String as2[] = new String[2];
            as2[0] = "racoon";
            as2[1] = "mtpd";
            mDaemons = as2;
            String as3[][] = new String[2][];
            as3[0] = as;
            as3[1] = as1;
            mArguments = as3;
            mSockets = new LocalSocket[mDaemons.length];
            mOuterInterface = mConfig.interfaze;
            mInfo.key = mConfig.user;
            mConfig.user = "[Legacy VPN]";
        }
    }

    private class Connection
        implements ServiceConnection {

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            mService = ibinder;
        }

        public void onServiceDisconnected(ComponentName componentname) {
            mService = null;
        }

        private IBinder mService;
        final Vpn this$0;


        private Connection() {
            this$0 = Vpn.this;
            super();
        }

    }


    public Vpn(Context context, com.android.server.ConnectivityService.VpnCallback vpncallback) {
        mPackage = "[Legacy VPN]";
        mContext = context;
        mCallback = vpncallback;
    }

    private void enforceControlPermission() {
        if(Binder.getCallingUid() != 1000) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        int j;
        ApplicationInfo applicationinfo = mContext.getPackageManager().getApplicationInfo("com.android.vpndialogs", 0);
        i = Binder.getCallingUid();
        j = applicationinfo.uid;
        if(i == j) goto _L1; else goto _L3
_L3:
        throw new SecurityException("Unauthorized Caller");
        Exception exception;
        exception;
        if(true) goto _L3; else goto _L4
_L4:
    }

    private void hideNotification() {
        NotificationManager notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        if(notificationmanager != null)
            notificationmanager.cancel(0x10805fd);
    }

    private native int jniCheck(String s);

    private native int jniCreate(int i);

    private native String jniGetName(int i);

    private native void jniProtect(int i, String s);

    private native void jniReset(String s);

    private native int jniSetAddresses(String s, String s1);

    private native int jniSetRoutes(String s, String s1);

    private void showNotification(VpnConfig vpnconfig, String s, Bitmap bitmap) {
        NotificationManager notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        if(notificationmanager != null) {
            String s1;
            String s2;
            if(s == null) {
                s1 = mContext.getString(0x104047b);
            } else {
                Context context = mContext;
                Object aobj[] = new Object[1];
                aobj[0] = s;
                s1 = context.getString(0x104047c, aobj);
            }
            if(vpnconfig.session == null) {
                s2 = mContext.getString(0x104047d);
            } else {
                Context context1 = mContext;
                Object aobj1[] = new Object[1];
                aobj1[0] = vpnconfig.session;
                s2 = context1.getString(0x104047e, aobj1);
            }
            vpnconfig.startTime = SystemClock.elapsedRealtime();
            notificationmanager.notify(0x10805fd, (new android.app.Notification.Builder(mContext)).setSmallIcon(0x10805fd).setLargeIcon(bitmap).setContentTitle(s1).setContentText(s2).setContentIntent(VpnConfig.getIntentForStatusPanel(mContext, vpnconfig)).setDefaults(0).setOngoing(true).getNotification());
        }
    }

    /**
     * @deprecated Method establish is deprecated
     */

    public ParcelFileDescriptor establish(VpnConfig vpnconfig) {
        this;
        JVM INSTR monitorenter ;
        PackageManager packagemanager = mContext.getPackageManager();
        ApplicationInfo applicationinfo = packagemanager.getApplicationInfo(mPackage, 0);
        int i;
        int j;
        i = Binder.getCallingUid();
        j = applicationinfo.uid;
        if(i == j) goto _L2; else goto _L1
_L1:
        ParcelFileDescriptor parcelfiledescriptor = null;
_L3:
        this;
        JVM INSTR monitorexit ;
        return parcelfiledescriptor;
        Exception exception1;
        exception1;
        parcelfiledescriptor = null;
          goto _L3
_L2:
        Intent intent;
        ResolveInfo resolveinfo;
        intent = new Intent("android.net.VpnService");
        intent.setClassName(mPackage, vpnconfig.user);
        resolveinfo = packagemanager.resolveService(intent, 0);
        if(resolveinfo == null)
            throw new SecurityException((new StringBuilder()).append("Cannot find ").append(vpnconfig.user).toString());
        break MISSING_BLOCK_LABEL_130;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        String s;
        Bitmap bitmap;
        ParcelFileDescriptor parcelfiledescriptor1;
        if(!"android.permission.BIND_VPN_SERVICE".equals(resolveinfo.serviceInfo.permission))
            throw new SecurityException((new StringBuilder()).append(vpnconfig.user).append(" does not require ").append("android.permission.BIND_VPN_SERVICE").toString());
        s = applicationinfo.loadLabel(packagemanager).toString();
        Drawable drawable = applicationinfo.loadIcon(packagemanager);
        bitmap = null;
        if(drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
            int k = mContext.getResources().getDimensionPixelSize(0x1050005);
            int i1 = mContext.getResources().getDimensionPixelSize(0x1050006);
            drawable.setBounds(0, 0, k, i1);
            bitmap = Bitmap.createBitmap(k, i1, android.graphics.Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.draw(canvas);
            canvas.setBitmap(null);
        }
        parcelfiledescriptor1 = ParcelFileDescriptor.adoptFd(jniCreate(vpnconfig.mtu));
        parcelfiledescriptor = parcelfiledescriptor1;
        String s1;
        s1 = jniGetName(parcelfiledescriptor.getFd());
        if(jniSetAddresses(s1, vpnconfig.addresses) < 1)
            throw new IllegalArgumentException("At least one address must be specified");
        break MISSING_BLOCK_LABEL_359;
        RuntimeException runtimeexception;
        runtimeexception;
        try {
            parcelfiledescriptor.close();
        }
        catch(Exception exception2) { }
        throw runtimeexception;
        if(vpnconfig.routes != null)
            jniSetRoutes(s1, vpnconfig.routes);
        Connection connection = new Connection();
        if(!mContext.bindService(intent, connection, 1))
            throw new IllegalStateException((new StringBuilder()).append("Cannot bind ").append(vpnconfig.user).toString());
        if(mConnection != null)
            mContext.unbindService(mConnection);
        if(mInterface != null && !mInterface.equals(s1))
            jniReset(mInterface);
        mConnection = connection;
        mInterface = s1;
        Log.i("Vpn", (new StringBuilder()).append("Established by ").append(vpnconfig.user).append(" on ").append(mInterface).toString());
        vpnconfig.user = mPackage;
        vpnconfig.interfaze = mInterface;
        long l = Binder.clearCallingIdentity();
        mCallback.override(vpnconfig.dnsServers, vpnconfig.searchDomains);
        showNotification(vpnconfig, s, bitmap);
        Binder.restoreCallingIdentity(l);
          goto _L3
    }

    /**
     * @deprecated Method getLegacyVpnInfo is deprecated
     */

    public LegacyVpnInfo getLegacyVpnInfo() {
        this;
        JVM INSTR monitorenter ;
        LegacyVpnRunner legacyvpnrunner;
        enforceControlPermission();
        legacyvpnrunner = mLegacyVpnRunner;
        if(legacyvpnrunner != null) goto _L2; else goto _L1
_L1:
        LegacyVpnInfo legacyvpninfo1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return legacyvpninfo1;
_L2:
        LegacyVpnInfo legacyvpninfo = mLegacyVpnRunner.getInfo();
        legacyvpninfo1 = legacyvpninfo;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void interfaceAdded(String s) {
    }

    public void interfaceLinkStateChanged(String s, boolean flag) {
    }

    /**
     * @deprecated Method interfaceRemoved is deprecated
     */

    public void interfaceRemoved(String s) {
        this;
        JVM INSTR monitorenter ;
        if(!s.equals(mInterface) || jniCheck(s) != 0) goto _L2; else goto _L1
_L1:
        long l = Binder.clearCallingIdentity();
        mCallback.restore();
        hideNotification();
        Binder.restoreCallingIdentity(l);
        mInterface = null;
        if(mConnection == null) goto _L4; else goto _L3
_L3:
        mContext.unbindService(mConnection);
        mConnection = null;
_L2:
        this;
        JVM INSTR monitorexit ;
        return;
_L4:
        if(mLegacyVpnRunner != null) {
            mLegacyVpnRunner.exit();
            mLegacyVpnRunner = null;
        }
        if(true) goto _L2; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method interfaceStatusChanged is deprecated
     */

    public void interfaceStatusChanged(String s, boolean flag) {
        this;
        JVM INSTR monitorenter ;
        if(flag)
            break MISSING_BLOCK_LABEL_21;
        if(mLegacyVpnRunner != null)
            mLegacyVpnRunner.check(s);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void limitReached(String s, String s1) {
    }

    /**
     * @deprecated Method prepare is deprecated
     */

    public boolean prepare(String s, String s1) {
        boolean flag = true;
        this;
        JVM INSTR monitorenter ;
        if(s == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = s.equals(mPackage);
        if(flag1) goto _L2; else goto _L3
_L3:
        flag = false;
_L5:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(s1.equals(mPackage) && !s1.equals("[Legacy VPN]")) goto _L5; else goto _L6
_L6:
        Connection connection;
        enforceControlPermission();
        if(mInterface != null) {
            jniReset(mInterface);
            long l = Binder.clearCallingIdentity();
            mCallback.restore();
            hideNotification();
            Binder.restoreCallingIdentity(l);
            mInterface = null;
        }
        connection = mConnection;
        if(connection == null) goto _L8; else goto _L7
_L7:
        Exception exception;
        try {
            mConnection.mService.transact(0xffffff, Parcel.obtain(), null, 1);
        }
        catch(Exception exception1) { }
        mContext.unbindService(mConnection);
        mConnection = null;
_L10:
        Log.i("Vpn", (new StringBuilder()).append("Switched from ").append(mPackage).append(" to ").append(s1).toString());
        mPackage = s1;
          goto _L5
        exception;
        throw exception;
_L8:
        if(mLegacyVpnRunner == null) goto _L10; else goto _L9
_L9:
        mLegacyVpnRunner.exit();
        mLegacyVpnRunner = null;
          goto _L10
    }

    public void protect(ParcelFileDescriptor parcelfiledescriptor, String s) throws Exception {
        ApplicationInfo applicationinfo = mContext.getPackageManager().getApplicationInfo(mPackage, 0);
        if(Binder.getCallingUid() != applicationinfo.uid) {
            throw new SecurityException("Unauthorized Caller");
        } else {
            jniProtect(parcelfiledescriptor.getFd(), s);
            return;
        }
    }

    /**
     * @deprecated Method startLegacyVpn is deprecated
     */

    public void startLegacyVpn(VpnConfig vpnconfig, String as[], String as1[]) {
        this;
        JVM INSTR monitorenter ;
        prepare(null, "[Legacy VPN]");
        mLegacyVpnRunner = new LegacyVpnRunner(vpnconfig, as, as1);
        mLegacyVpnRunner.start();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private static final String BIND_VPN_SERVICE = "android.permission.BIND_VPN_SERVICE";
    private static final String TAG = "Vpn";
    private final com.android.server.ConnectivityService.VpnCallback mCallback;
    private Connection mConnection;
    private final Context mContext;
    private String mInterface;
    private LegacyVpnRunner mLegacyVpnRunner;
    private String mPackage;



/*
    static String access$202(Vpn vpn, String s) {
        vpn.mInterface = s;
        return s;
    }

*/





}
