// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.app.backup.BackupManager;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.os.*;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.util.*;
import android.view.*;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import java.io.*;
import java.util.List;
import org.xmlpull.v1.*;

class WallpaperManagerService extends android.app.IWallpaperManager.Stub {
    class MyPackageMonitor extends PackageMonitor {

        boolean doPackagesChangedLocked(boolean flag, WallpaperData wallpaperdata) {
            boolean flag1 = false;
            if(wallpaperdata.wallpaperComponent != null) {
                int j = isPackageDisappearing(wallpaperdata.wallpaperComponent.getPackageName());
                if(j == 3 || j == 2) {
                    flag1 = true;
                    if(flag) {
                        Slog.w("WallpaperService", (new StringBuilder()).append("Wallpaper uninstalled, removing: ").append(wallpaperdata.wallpaperComponent).toString());
                        clearWallpaperLocked(false, wallpaperdata.userId);
                    }
                }
            }
            if(wallpaperdata.nextWallpaperComponent != null) {
                int i = isPackageDisappearing(wallpaperdata.nextWallpaperComponent.getPackageName());
                if(i == 3 || i == 2)
                    wallpaperdata.nextWallpaperComponent = null;
            }
            if(wallpaperdata.wallpaperComponent != null && isPackageModified(wallpaperdata.wallpaperComponent.getPackageName()))
                try {
                    mContext.getPackageManager().getServiceInfo(wallpaperdata.wallpaperComponent, 0);
                }
                catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception1) {
                    Slog.w("WallpaperService", (new StringBuilder()).append("Wallpaper component gone, removing: ").append(wallpaperdata.wallpaperComponent).toString());
                    clearWallpaperLocked(false, wallpaperdata.userId);
                }
            if(wallpaperdata.nextWallpaperComponent != null && isPackageModified(wallpaperdata.nextWallpaperComponent.getPackageName()))
                try {
                    mContext.getPackageManager().getServiceInfo(wallpaperdata.nextWallpaperComponent, 0);
                }
                catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                    wallpaperdata.nextWallpaperComponent = null;
                }
            return flag1;
        }

        public boolean onHandleForceStop(Intent intent, String as[], int i, boolean flag) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            boolean flag1 = false;
            for(int j = 0; j < mWallpaperMap.size(); j++)
                flag1 |= doPackagesChangedLocked(flag, (WallpaperData)mWallpaperMap.valueAt(j));

            return flag1;
        }

        public void onPackageModified(String s) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            int i = 0;
_L2:
            if(i < mWallpaperMap.size()) {
                WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.valueAt(i);
                if(wallpaperdata.wallpaperComponent != null && wallpaperdata.wallpaperComponent.getPackageName().equals(s))
                    doPackagesChangedLocked(true, wallpaperdata);
                break MISSING_BLOCK_LABEL_89;
            }
            break MISSING_BLOCK_LABEL_86;
            Exception exception;
            exception;
            throw exception;
            obj;
            JVM INSTR monitorexit ;
            return;
            i++;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public void onPackageUpdateFinished(String s, int i) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            int j = 0;
            do {
                if(j < mWallpaperMap.size()) {
                    WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.valueAt(j);
                    if(wallpaperdata.wallpaperComponent != null && wallpaperdata.wallpaperComponent.getPackageName().equals(s)) {
                        wallpaperdata.wallpaperUpdating = false;
                        ComponentName componentname = wallpaperdata.wallpaperComponent;
                        clearWallpaperComponentLocked(wallpaperdata);
                        if(wallpaperdata.userId == mCurrentUserId && !bindWallpaperComponentLocked(componentname, false, false, wallpaperdata)) {
                            Slog.w("WallpaperService", "Wallpaper no longer available; reverting to default");
                            clearWallpaperLocked(false, wallpaperdata.userId);
                        }
                    }
                } else {
                    return;
                }
                j++;
            } while(true);
        }

        public void onPackageUpdateStarted(String s, int i) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            int j = 0;
            do {
                if(j < mWallpaperMap.size()) {
                    WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.valueAt(j);
                    if(wallpaperdata.wallpaperComponent != null && wallpaperdata.wallpaperComponent.getPackageName().equals(s))
                        wallpaperdata.wallpaperUpdating = true;
                } else {
                    return;
                }
                j++;
            } while(true);
        }

        public void onSomePackagesChanged() {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            for(int i = 0; i < mWallpaperMap.size(); i++)
                doPackagesChangedLocked(true, (WallpaperData)mWallpaperMap.valueAt(i));

            return;
        }

        final WallpaperManagerService this$0;

        MyPackageMonitor() {
            this$0 = WallpaperManagerService.this;
            super();
        }
    }

    class WallpaperConnection extends android.service.wallpaper.IWallpaperConnection.Stub
        implements ServiceConnection {

        public void attachEngine(IWallpaperEngine iwallpaperengine) {
            mEngine = iwallpaperengine;
        }

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            if(mWallpaper.connection == this) {
                mWallpaper.lastDiedTime = SystemClock.uptimeMillis();
                mService = android.service.wallpaper.IWallpaperService.Stub.asInterface(ibinder);
                attachServiceLocked(this, mWallpaper);
                saveSettingsLocked(mWallpaper);
            }
            return;
        }

        public void onServiceDisconnected(ComponentName componentname) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            mService = null;
            mEngine = null;
            if(mWallpaper.connection == this) {
                Slog.w("WallpaperService", (new StringBuilder()).append("Wallpaper service gone: ").append(mWallpaper.wallpaperComponent).toString());
                if(!mWallpaper.wallpaperUpdating && 10000L + mWallpaper.lastDiedTime > SystemClock.uptimeMillis() && mWallpaper.userId == mCurrentUserId) {
                    Slog.w("WallpaperService", "Reverting to built-in wallpaper!");
                    clearWallpaperLocked(true, mWallpaper.userId);
                }
            }
            return;
        }

        public ParcelFileDescriptor setWallpaper(String s) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            ParcelFileDescriptor parcelfiledescriptor;
            if(mWallpaper.connection == this)
                parcelfiledescriptor = updateWallpaperBitmapLocked(s, mWallpaper);
            else
                parcelfiledescriptor = null;
            return parcelfiledescriptor;
        }

        IWallpaperEngine mEngine;
        final WallpaperInfo mInfo;
        IWallpaperService mService;
        final Binder mToken = new Binder();
        WallpaperData mWallpaper;
        final WallpaperManagerService this$0;

        public WallpaperConnection(WallpaperInfo wallpaperinfo, WallpaperData wallpaperdata) {
            this$0 = WallpaperManagerService.this;
            super();
            mInfo = wallpaperinfo;
            mWallpaper = wallpaperdata;
        }
    }

    static class WallpaperData {

        private RemoteCallbackList callbacks;
        WallpaperConnection connection;
        int height;
        ComponentName imageWallpaperComponent;
        boolean imageWallpaperPending;
        long lastDiedTime;
        String name;
        ComponentName nextWallpaperComponent;
        int userId;
        ComponentName wallpaperComponent;
        File wallpaperFile;
        WallpaperObserver wallpaperObserver;
        boolean wallpaperUpdating;
        int width;


        WallpaperData(int i) {
            name = "";
            imageWallpaperComponent = new ComponentName("com.android.systemui", "com.android.systemui.ImageWallpaper");
            callbacks = new RemoteCallbackList();
            width = -1;
            height = -1;
            userId = i;
            wallpaperFile = new File(WallpaperManagerService.getWallpaperDir(i), "wallpaper");
        }
    }

    private class WallpaperObserver extends FileObserver {

        public void onEvent(int i, String s) {
            if(s != null) goto _L2; else goto _L1
_L1:
            return;
_L2:
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            long l = Binder.clearCallingIdentity();
            (new BackupManager(mContext)).dataChanged();
            Binder.restoreCallingIdentity(l);
            File file = new File(mWallpaperDir, s);
            if(mWallpaperFile.equals(file)) {
                notifyCallbacksLocked(mWallpaper);
                if(mWallpaper.wallpaperComponent == null || i != 8 || mWallpaper.imageWallpaperPending) {
                    if(i == 8)
                        mWallpaper.imageWallpaperPending = false;
                    bindWallpaperComponentLocked(mWallpaper.imageWallpaperComponent, true, false, mWallpaper);
                    saveSettingsLocked(mWallpaper);
                }
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        final WallpaperData mWallpaper;
        final File mWallpaperDir;
        final File mWallpaperFile;
        final WallpaperManagerService this$0;

        public WallpaperObserver(WallpaperData wallpaperdata) {
            this$0 = WallpaperManagerService.this;
            super(WallpaperManagerService.getWallpaperDir(wallpaperdata.userId).getAbsolutePath(), 1544);
            mWallpaperDir = WallpaperManagerService.getWallpaperDir(wallpaperdata.userId);
            mWallpaper = wallpaperdata;
            mWallpaperFile = new File(mWallpaperDir, "wallpaper");
        }
    }


    public WallpaperManagerService(Context context) {
        mWallpaperMap = new SparseArray();
        mContext = context;
        mMonitor.register(context, null, true);
        WALLPAPER_BASE_DIR.mkdirs();
        loadSettingsLocked(0);
    }

    private void checkPermission(String s) {
        if(mContext.checkCallingOrSelfPermission(s) != 0)
            throw new SecurityException((new StringBuilder()).append("Access denied to process: ").append(Binder.getCallingPid()).append(", must have permission ").append(s).toString());
        else
            return;
    }

    private static File getWallpaperDir(int i) {
        return new File((new StringBuilder()).append(WALLPAPER_BASE_DIR).append("/").append(i).toString());
    }

    private void loadSettingsLocked(int i) {
        FileInputStream fileinputstream;
        File file;
        WallpaperData wallpaperdata;
        boolean flag;
        JournaledFile journaledfile = makeJournaledFile(i);
        fileinputstream = null;
        file = journaledfile.chooseForRead();
        if(!file.exists())
            migrateFromOld();
        wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        if(wallpaperdata == null) {
            wallpaperdata = new WallpaperData(i);
            mWallpaperMap.put(i, wallpaperdata);
        }
        flag = false;
        FileInputStream fileinputstream1 = new FileInputStream(file);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
_L4:
        int k;
        String s;
        k = xmlpullparser.next();
        if(k != 2 || !"wp".equals(xmlpullparser.getName()))
            continue; /* Loop/switch isn't completed */
        wallpaperdata.width = Integer.parseInt(xmlpullparser.getAttributeValue(null, "width"));
        wallpaperdata.height = Integer.parseInt(xmlpullparser.getAttributeValue(null, "height"));
        wallpaperdata.name = xmlpullparser.getAttributeValue(null, "name");
        s = xmlpullparser.getAttributeValue(null, "component");
        if(s == null) goto _L2; else goto _L1
_L1:
        ComponentName componentname = ComponentName.unflattenFromString(s);
_L5:
        wallpaperdata.nextWallpaperComponent = componentname;
        if(wallpaperdata.nextWallpaperComponent == null || "android".equals(wallpaperdata.nextWallpaperComponent.getPackageName()))
            wallpaperdata.nextWallpaperComponent = wallpaperdata.imageWallpaperComponent;
        if(k != 1) goto _L4; else goto _L3
_L3:
        flag = true;
        fileinputstream = fileinputstream1;
_L6:
        NullPointerException nullpointerexception;
        NumberFormatException numberformatexception;
        XmlPullParserException xmlpullparserexception;
        IOException ioexception1;
        IndexOutOfBoundsException indexoutofboundsexception;
        int j;
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        if(!flag) {
            wallpaperdata.width = -1;
            wallpaperdata.height = -1;
            wallpaperdata.name = "";
        }
        j = ((WindowManager)mContext.getSystemService("window")).getDefaultDisplay().getMaximumSizeDimension();
        if(wallpaperdata.width < j)
            wallpaperdata.width = j;
        if(wallpaperdata.height < j)
            wallpaperdata.height = j;
        return;
_L2:
        componentname = null;
          goto _L5
        nullpointerexception;
_L11:
        Slog.w("WallpaperService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(nullpointerexception).toString());
          goto _L6
        numberformatexception;
_L10:
        Slog.w("WallpaperService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(numberformatexception).toString());
          goto _L6
        xmlpullparserexception;
_L9:
        Slog.w("WallpaperService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(xmlpullparserexception).toString());
          goto _L6
        ioexception1;
_L8:
        Slog.w("WallpaperService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(ioexception1).toString());
          goto _L6
        indexoutofboundsexception;
_L7:
        Slog.w("WallpaperService", (new StringBuilder()).append("failed parsing ").append(file).append(" ").append(indexoutofboundsexception).toString());
          goto _L6
        indexoutofboundsexception;
        fileinputstream = fileinputstream1;
          goto _L7
        ioexception1;
        fileinputstream = fileinputstream1;
          goto _L8
        xmlpullparserexception;
        fileinputstream = fileinputstream1;
          goto _L9
        numberformatexception;
        fileinputstream = fileinputstream1;
          goto _L10
        nullpointerexception;
        fileinputstream = fileinputstream1;
          goto _L11
    }

    private static JournaledFile makeJournaledFile(int i) {
        String s = (new StringBuilder()).append(getWallpaperDir(i)).append("/").append("wallpaper_info.xml").toString();
        return new JournaledFile(new File(s), new File((new StringBuilder()).append(s).append(".tmp").toString()));
    }

    private void migrateFromOld() {
        File file = new File("/data/data/com.android.settings/files/wallpaper");
        File file1 = new File("/data/system/wallpaper_info.xml");
        if(file.exists())
            file.renameTo(new File(getWallpaperDir(0), "wallpaper"));
        if(file1.exists())
            file1.renameTo(new File(getWallpaperDir(0), "wallpaper_info.xml"));
    }

    private void notifyCallbacksLocked(WallpaperData wallpaperdata) {
        int i = wallpaperdata.callbacks.beginBroadcast();
        int j = 0;
        while(j < i)  {
            Intent intent;
            try {
                ((IWallpaperManagerCallback)wallpaperdata.callbacks.getBroadcastItem(j)).onWallpaperChanged();
            }
            catch(RemoteException remoteexception) { }
            j++;
        }
        wallpaperdata.callbacks.finishBroadcast();
        intent = new Intent("android.intent.action.WALLPAPER_CHANGED");
        mContext.sendBroadcast(intent);
    }

    private void saveSettingsLocked(WallpaperData wallpaperdata) {
        JournaledFile journaledfile;
        FileOutputStream fileoutputstream;
        journaledfile = makeJournaledFile(wallpaperdata.userId);
        fileoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(journaledfile.chooseForWrite(), false);
        FastXmlSerializer fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream1, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.startTag(null, "wp");
        fastxmlserializer.attribute(null, "width", Integer.toString(wallpaperdata.width));
        fastxmlserializer.attribute(null, "height", Integer.toString(wallpaperdata.height));
        fastxmlserializer.attribute(null, "name", wallpaperdata.name);
        if(wallpaperdata.wallpaperComponent != null && !wallpaperdata.wallpaperComponent.equals(wallpaperdata.imageWallpaperComponent))
            fastxmlserializer.attribute(null, "component", wallpaperdata.wallpaperComponent.flattenToShortString());
        fastxmlserializer.endTag(null, "wp");
        fastxmlserializer.endDocument();
        fileoutputstream1.close();
        journaledfile.commit();
_L1:
        return;
        IOException ioexception2;
        ioexception2;
_L2:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception1) { }
        journaledfile.rollback();
          goto _L1
        IOException ioexception;
        ioexception;
        fileoutputstream = fileoutputstream1;
          goto _L2
    }

    void attachServiceLocked(WallpaperConnection wallpaperconnection, WallpaperData wallpaperdata) {
        wallpaperconnection.mService.attach(wallpaperconnection, wallpaperconnection.mToken, 2013, false, wallpaperdata.width, wallpaperdata.height);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("WallpaperService", "Failed attaching wallpaper; clearing", remoteexception);
        if(!wallpaperdata.wallpaperUpdating)
            bindWallpaperComponentLocked(null, false, false, wallpaperdata);
          goto _L1
    }

    boolean bindWallpaperComponentLocked(ComponentName componentname, boolean flag, boolean flag1, WallpaperData wallpaperdata) {
        if(flag || wallpaperdata.connection == null) goto _L2; else goto _L1
_L1:
        if(wallpaperdata.wallpaperComponent != null) goto _L4; else goto _L3
_L3:
        if(componentname != null) goto _L2; else goto _L5
_L5:
        boolean flag2 = true;
_L13:
        return flag2;
_L4:
        if(wallpaperdata.wallpaperComponent.equals(componentname)) {
            flag2 = true;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        if(componentname != null)
            break MISSING_BLOCK_LABEL_85;
        String s4 = mContext.getString(0x104001b);
        if(s4 != null)
            componentname = ComponentName.unflattenFromString(s4);
        if(componentname == null)
            componentname = wallpaperdata.imageWallpaperComponent;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        ServiceInfo serviceinfo;
        String s3;
        serviceinfo = mContext.getPackageManager().getServiceInfo(componentname, 4224);
        if("android.permission.BIND_WALLPAPER".equals(serviceinfo.permission))
            break MISSING_BLOCK_LABEL_203;
        s3 = (new StringBuilder()).append("Selected service does not require android.permission.BIND_WALLPAPER: ").append(componentname).toString();
        if(flag1)
            throw new SecurityException(s3);
        break MISSING_BLOCK_LABEL_189;
        Slog.w("WallpaperService", s3);
        flag2 = false;
        continue; /* Loop/switch isn't completed */
        wallpaperinfo = null;
        intent = new Intent("android.service.wallpaper.WallpaperService");
        if(componentname == null) goto _L7; else goto _L6
_L6:
        componentname2 = wallpaperdata.imageWallpaperComponent;
        if(componentname.equals(componentname2)) goto _L7; else goto _L8
_L8:
        int j;
        list = mContext.getPackageManager().queryIntentServices(intent, 128);
        j = 0;
_L14:
        if(j >= list.size()) goto _L10; else goto _L9
_L9:
        ServiceInfo serviceinfo1 = ((ResolveInfo)list.get(j)).serviceInfo;
        if(!((ComponentInfo) (serviceinfo1)).name.equals(((ComponentInfo) (serviceinfo)).name))
            break MISSING_BLOCK_LABEL_738;
        flag3 = ((ComponentInfo) (serviceinfo1)).packageName.equals(((ComponentInfo) (serviceinfo)).packageName);
        {
            if(!flag3)
                break MISSING_BLOCK_LABEL_738;
            try {
                wallpaperinfo = new WallpaperInfo(mContext, (ResolveInfo)list.get(j));
            }
            catch(XmlPullParserException xmlpullparserexception) {
                if(flag1)
                    throw new IllegalArgumentException(xmlpullparserexception);
                Slog.w("WallpaperService", xmlpullparserexception);
                flag2 = false;
                continue; /* Loop/switch isn't completed */
            }
            catch(IOException ioexception) {
                if(flag1)
                    throw new IllegalArgumentException(ioexception);
                Slog.w("WallpaperService", ioexception);
                flag2 = false;
                continue; /* Loop/switch isn't completed */
            }
            // Misplaced declaration of an exception variable
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                break MISSING_BLOCK_LABEL_153;
            }
        }
_L10:
        if(wallpaperinfo != null) goto _L7; else goto _L11
_L11:
        s2 = (new StringBuilder()).append("Selected service is not a wallpaper: ").append(componentname).toString();
        if(flag1)
            throw new SecurityException(s2);
        break MISSING_BLOCK_LABEL_449;
        Slog.w("WallpaperService", s2);
        flag2 = false;
        continue; /* Loop/switch isn't completed */
_L7:
        wallpaperconnection = new WallpaperConnection(wallpaperinfo, wallpaperdata);
        intent.setComponent(componentname);
        int i = wallpaperdata.userId;
        ComponentName componentname1 = wallpaperdata.imageWallpaperComponent;
        if(componentname.equals(componentname1))
            i = 0;
        intent.putExtra("android.intent.extra.client_label", 0x1040478);
        intent.putExtra("android.intent.extra.client_intent", PendingIntent.getActivity(mContext, 0, Intent.createChooser(new Intent("android.intent.action.SET_WALLPAPER"), mContext.getText(0x1040479)), 0));
        if(!mContext.bindService(intent, wallpaperconnection, 1, i)) {
            String s1 = (new StringBuilder()).append("Unable to bind service: ").append(componentname).toString();
            if(flag1)
                throw new IllegalArgumentException(s1);
            Slog.w("WallpaperService", s1);
            flag2 = false;
            continue; /* Loop/switch isn't completed */
        }
        if(wallpaperdata.userId == mCurrentUserId && mLastWallpaper != null)
            detachWallpaperLocked(mLastWallpaper);
        wallpaperdata.wallpaperComponent = componentname;
        wallpaperdata.connection = wallpaperconnection;
        wallpaperdata.lastDiedTime = SystemClock.uptimeMillis();
        try {
            if(wallpaperdata.userId == mCurrentUserId) {
                mIWindowManager.addWindowToken(wallpaperconnection.mToken, 2013);
                mLastWallpaper = wallpaperdata;
            }
        }
        catch(RemoteException remoteexception) { }
        flag2 = true;
        continue; /* Loop/switch isn't completed */
        String s = (new StringBuilder()).append("Unknown component ").append(componentname).toString();
        WallpaperInfo wallpaperinfo;
        Intent intent;
        WallpaperConnection wallpaperconnection;
        ComponentName componentname2;
        List list;
        String s2;
        boolean flag3;
        if(flag1)
            throw new IllegalArgumentException(s);
        Slog.w("WallpaperService", s);
        flag2 = false;
        if(true) goto _L13; else goto _L12
_L12:
        j++;
          goto _L14
    }

    public void clearWallpaper() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        clearWallpaperLocked(false, UserId.getCallingUserId());
        return;
    }

    void clearWallpaperComponentLocked(WallpaperData wallpaperdata) {
        wallpaperdata.wallpaperComponent = null;
        detachWallpaperLocked(wallpaperdata);
    }

    void clearWallpaperLocked(boolean flag, int i) {
        WallpaperData wallpaperdata;
        long l;
        Object obj;
        wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        File file = new File(getWallpaperDir(i), "wallpaper");
        if(file.exists())
            file.delete();
        l = Binder.clearCallingIdentity();
        obj = null;
        int j;
        wallpaperdata.imageWallpaperPending = false;
        j = mCurrentUserId;
        if(i == j) goto _L2; else goto _L1
_L1:
        Binder.restoreCallingIdentity(l);
_L6:
        return;
_L2:
        if(!flag) goto _L4; else goto _L3
_L3:
        ComponentName componentname = wallpaperdata.imageWallpaperComponent;
_L7:
        boolean flag1 = bindWallpaperComponentLocked(componentname, true, false, wallpaperdata);
        if(flag1) goto _L1; else goto _L5
_L5:
        Binder.restoreCallingIdentity(l);
        Slog.e("WallpaperService", "Default wallpaper component not found!", ((Throwable) (obj)));
        clearWallpaperComponentLocked(wallpaperdata);
          goto _L6
_L4:
        componentname = null;
          goto _L7
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        obj = illegalargumentexception;
          goto _L5
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
          goto _L6
    }

    void detachWallpaperLocked(WallpaperData wallpaperdata) {
        if(wallpaperdata.connection != null) {
            if(wallpaperdata.connection.mEngine != null)
                try {
                    wallpaperdata.connection.mEngine.destroy();
                }
                catch(RemoteException remoteexception1) { }
            mContext.unbindService(wallpaperdata.connection);
            try {
                mIWindowManager.removeWindowToken(wallpaperdata.connection.mToken);
            }
            catch(RemoteException remoteexception) { }
            wallpaperdata.connection.mService = null;
            wallpaperdata.connection.mEngine = null;
            wallpaperdata.connection = null;
        }
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump wallpaper service from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("Current Wallpaper Service state:");
        int i = 0;
        while(i < mWallpaperMap.size())  {
            WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.valueAt(i);
            printwriter.println((new StringBuilder()).append(" User ").append(wallpaperdata.userId).append(":").toString());
            printwriter.print("  mWidth=");
            printwriter.print(wallpaperdata.width);
            printwriter.print(" mHeight=");
            printwriter.println(wallpaperdata.height);
            printwriter.print("  mName=");
            printwriter.println(wallpaperdata.name);
            printwriter.print("  mWallpaperComponent=");
            printwriter.println(wallpaperdata.wallpaperComponent);
            if(wallpaperdata.connection != null) {
                WallpaperConnection wallpaperconnection = wallpaperdata.connection;
                printwriter.print("  Wallpaper connection ");
                printwriter.print(wallpaperconnection);
                printwriter.println(":");
                if(wallpaperconnection.mInfo != null) {
                    printwriter.print("    mInfo.component=");
                    printwriter.println(wallpaperconnection.mInfo.getComponent());
                }
                printwriter.print("    mToken=");
                printwriter.println(wallpaperconnection.mToken);
                printwriter.print("    mService=");
                printwriter.println(wallpaperconnection.mService);
                printwriter.print("    mEngine=");
                printwriter.println(wallpaperconnection.mEngine);
                printwriter.print("    mLastDiedTime=");
                printwriter.println(wallpaperdata.lastDiedTime - SystemClock.uptimeMillis());
            }
            i++;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void finalize() throws Throwable {
        super.finalize();
        for(int i = 0; i < mWallpaperMap.size(); i++)
            ((WallpaperData)mWallpaperMap.valueAt(i)).wallpaperObserver.stopWatching();

    }

    public int getHeightHint() throws RemoteException {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = ((WallpaperData)mWallpaperMap.get(UserId.getCallingUserId())).height;
        return i;
    }

    String getName() {
        return ((WallpaperData)mWallpaperMap.get(0)).name;
    }

    public ParcelFileDescriptor getWallpaper(IWallpaperManagerCallback iwallpapermanagercallback, Bundle bundle) {
        ParcelFileDescriptor parcelfiledescriptor = null;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = Binder.getCallingUid();
        if(i != 1000) goto _L2; else goto _L1
_L1:
        int k = mCurrentUserId;
_L6:
        WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.get(k);
        if(bundle == null)
            break MISSING_BLOCK_LABEL_70;
        bundle.putInt("width", wallpaperdata.width);
        bundle.putInt("height", wallpaperdata.height);
        File file;
        boolean flag;
        wallpaperdata.callbacks.register(iwallpapermanagercallback);
        file = new File(getWallpaperDir(k), "wallpaper");
        flag = file.exists();
        if(flag) goto _L4; else goto _L3
_L3:
        obj;
        JVM INSTR monitorexit ;
          goto _L5
_L2:
        int j = UserId.getUserId(i);
        k = j;
          goto _L6
_L4:
        ParcelFileDescriptor parcelfiledescriptor1 = ParcelFileDescriptor.open(file, 0x10000000);
        parcelfiledescriptor = parcelfiledescriptor1;
        obj;
        JVM INSTR monitorexit ;
          goto _L5
        Exception exception;
        exception;
        throw exception;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w("WallpaperService", "Error getting wallpaper", filenotfoundexception);
        obj;
        JVM INSTR monitorexit ;
_L5:
        return parcelfiledescriptor;
    }

    public WallpaperInfo getWallpaperInfo() {
        int i = UserId.getCallingUserId();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        WallpaperInfo wallpaperinfo;
        if(wallpaperdata.connection != null)
            wallpaperinfo = wallpaperdata.connection.mInfo;
        else
            wallpaperinfo = null;
        return wallpaperinfo;
    }

    public int getWidthHint() throws RemoteException {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = ((WallpaperData)mWallpaperMap.get(UserId.getCallingUserId())).width;
        return i;
    }

    void removeUser(int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        if(wallpaperdata != null) {
            wallpaperdata.wallpaperObserver.stopWatching();
            mWallpaperMap.remove(i);
        }
        (new File(getWallpaperDir(i), "wallpaper")).delete();
        (new File(getWallpaperDir(i), "wallpaper_info.xml")).delete();
        return;
    }

    boolean restoreNamedResourceLocked(WallpaperData wallpaperdata) {
        if(wallpaperdata.name.length() <= 4 || !"res:".equals(wallpaperdata.name.substring(0, 4))) goto _L2; else goto _L1
_L1:
        String s;
        String s1;
        String s2;
        String s3;
        s = wallpaperdata.name.substring(4);
        s1 = null;
        int i = s.indexOf(':');
        if(i > 0)
            s1 = s.substring(0, i);
        s2 = null;
        int j = s.lastIndexOf('/');
        if(j > 0)
            s2 = s.substring(j + 1);
        s3 = null;
        if(i > 0 && j > 0 && j - i > 1)
            s3 = s.substring(i + 1, j);
        if(s1 == null || s2 == null || s3 == null) goto _L2; else goto _L3
_L3:
        int k;
        InputStream inputstream;
        FileOutputStream fileoutputstream;
        k = -1;
        inputstream = null;
        fileoutputstream = null;
        Resources resources;
        resources = mContext.createPackageContext(s1, 4).getResources();
        k = resources.getIdentifier(s, null, null);
        if(k != 0)
            break MISSING_BLOCK_LABEL_248;
        Slog.e("WallpaperService", (new StringBuilder()).append("couldn't resolve identifier pkg=").append(s1).append(" type=").append(s3).append(" ident=").append(s2).toString());
        Exception exception;
        IOException ioexception4;
        FileOutputStream fileoutputstream1;
        boolean flag = false;
        IOException ioexception;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        IOException ioexception5;
        android.content.res.Resources.NotFoundException notfoundexception;
        IOException ioexception6;
        if(false)
            try {
                throw null;
            }
            catch(IOException ioexception9) { }
        if(false) {
            FileUtils.sync(null);
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception1;
            byte abyte0[];
            int l;
            try {
                throw null;
            }
            catch(IOException ioexception7) { }
        }
        return flag;
        inputstream = resources.openRawResource(k);
        if(wallpaperdata.wallpaperFile.exists())
            wallpaperdata.wallpaperFile.delete();
        fileoutputstream1 = new FileOutputStream(wallpaperdata.wallpaperFile);
        abyte0 = new byte[32768];
        do {
            l = inputstream.read(abyte0);
            if(l <= 0)
                break;
            fileoutputstream1.write(abyte0, 0, l);
        } while(true);
          goto _L4
        namenotfoundexception1;
        fileoutputstream = fileoutputstream1;
_L12:
        Slog.e("WallpaperService", (new StringBuilder()).append("Package name ").append(s1).append(" not found").toString());
        if(inputstream != null)
            try {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception3) { }
        if(fileoutputstream != null) {
            FileUtils.sync(fileoutputstream);
            try {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception2) { }
        }
_L2:
        flag = false;
        break MISSING_BLOCK_LABEL_246;
_L4:
        Slog.v("WallpaperService", (new StringBuilder()).append("Restored wallpaper: ").append(s).toString());
        flag = true;
        if(inputstream != null)
            try {
                inputstream.close();
            }
            catch(IOException ioexception8) { }
        if(fileoutputstream1 == null)
            break MISSING_BLOCK_LABEL_246;
        FileUtils.sync(fileoutputstream1);
        fileoutputstream1.close();
        break MISSING_BLOCK_LABEL_246;
        notfoundexception;
_L10:
        Slog.e("WallpaperService", (new StringBuilder()).append("Resource not found: ").append(k).toString());
        if(inputstream != null)
            try {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception6) { }
        if(fileoutputstream == null) goto _L2; else goto _L5
_L5:
        FileUtils.sync(fileoutputstream);
        fileoutputstream.close();
          goto _L2
        ioexception4;
_L9:
        Slog.e("WallpaperService", "IOException while restoring wallpaper ", ioexception4);
        if(inputstream != null)
            try {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception5) { }
        if(fileoutputstream == null) goto _L2; else goto _L6
_L6:
        FileUtils.sync(fileoutputstream);
        fileoutputstream.close();
          goto _L2
        exception;
_L8:
        if(inputstream != null)
            try {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        if(fileoutputstream != null) {
            FileUtils.sync(fileoutputstream);
            try {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) { }
        }
        throw exception;
        exception;
        fileoutputstream = fileoutputstream1;
        if(true) goto _L8; else goto _L7
_L7:
        ioexception4;
        fileoutputstream = fileoutputstream1;
          goto _L9
        android.content.res.Resources.NotFoundException notfoundexception1;
        notfoundexception1;
        fileoutputstream = fileoutputstream1;
          goto _L10
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        if(true) goto _L12; else goto _L11
_L11:
    }

    public void setDimensionHints(int i, int j) throws RemoteException {
        int k;
        WallpaperData wallpaperdata;
        checkPermission("android.permission.SET_WALLPAPER_HINTS");
        k = UserId.getCallingUserId();
        wallpaperdata = (WallpaperData)mWallpaperMap.get(k);
        if(wallpaperdata == null)
            throw new IllegalStateException((new StringBuilder()).append("Wallpaper not yet initialized for user ").append(k).toString());
        if(i <= 0 || j <= 0)
            throw new IllegalArgumentException("width and height must be > 0");
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        IWallpaperEngine iwallpaperengine;
        if(i == wallpaperdata.width && j == wallpaperdata.height)
            break MISSING_BLOCK_LABEL_179;
        wallpaperdata.width = i;
        wallpaperdata.height = j;
        saveSettingsLocked(wallpaperdata);
        if(mCurrentUserId != k)
            break MISSING_BLOCK_LABEL_198;
        if(wallpaperdata.connection == null)
            break MISSING_BLOCK_LABEL_179;
        iwallpaperengine = wallpaperdata.connection.mEngine;
        if(iwallpaperengine != null) {
            Exception exception;
            try {
                wallpaperdata.connection.mEngine.setDesiredSize(i, j);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
            notifyCallbacksLocked(wallpaperdata);
        }
        obj;
        JVM INSTR monitorexit ;
        if(true)
            break MISSING_BLOCK_LABEL_198;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public ParcelFileDescriptor setWallpaper(String s) {
        WallpaperData wallpaperdata;
        int i = UserId.getCallingUserId();
        wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        if(wallpaperdata == null)
            throw new IllegalStateException((new StringBuilder()).append("Wallpaper not yet initialized for user ").append(i).toString());
        checkPermission("android.permission.SET_WALLPAPER");
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        ParcelFileDescriptor parcelfiledescriptor;
        parcelfiledescriptor = updateWallpaperBitmapLocked(s, wallpaperdata);
        if(parcelfiledescriptor != null)
            wallpaperdata.imageWallpaperPending = true;
        Binder.restoreCallingIdentity(l);
        obj;
        JVM INSTR monitorexit ;
        return parcelfiledescriptor;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public void setWallpaperComponent(ComponentName componentname) {
        WallpaperData wallpaperdata;
        int i = UserId.getCallingUserId();
        wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        if(wallpaperdata == null)
            throw new IllegalStateException((new StringBuilder()).append("Wallpaper not yet initialized for user ").append(i).toString());
        checkPermission("android.permission.SET_WALLPAPER_COMPONENT");
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        wallpaperdata.imageWallpaperPending = false;
        bindWallpaperComponentLocked(componentname, false, true, wallpaperdata);
        Binder.restoreCallingIdentity(l);
        obj;
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

    void settingsRestored() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        WallpaperData wallpaperdata;
        loadSettingsLocked(0);
        wallpaperdata = (WallpaperData)mWallpaperMap.get(0);
        if(wallpaperdata.nextWallpaperComponent != null && !wallpaperdata.nextWallpaperComponent.equals(wallpaperdata.imageWallpaperComponent)) {
            if(!bindWallpaperComponentLocked(wallpaperdata.nextWallpaperComponent, false, false, wallpaperdata))
                bindWallpaperComponentLocked(null, false, false, wallpaperdata);
            break MISSING_BLOCK_LABEL_209;
        }
          goto _L1
_L7:
        boolean flag1;
        if(!flag1) {
            Slog.e("WallpaperService", (new StringBuilder()).append("Failed to restore wallpaper: '").append(wallpaperdata.name).append("'").toString());
            wallpaperdata.name = "";
            getWallpaperDir(0).delete();
        }
        synchronized(mLock) {
            saveSettingsLocked(wallpaperdata);
        }
        return;
_L1:
        if(!"".equals(wallpaperdata.name)) goto _L3; else goto _L2
_L2:
        flag1 = true;
_L5:
        if(flag1)
            bindWallpaperComponentLocked(wallpaperdata.nextWallpaperComponent, false, false, wallpaperdata);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L3:
        boolean flag = restoreNamedResourceLocked(wallpaperdata);
        flag1 = flag;
        if(true) goto _L5; else goto _L4
_L4:
        exception1;
        obj1;
        JVM INSTR monitorexit ;
        throw exception1;
        flag1 = true;
        if(true) goto _L7; else goto _L6
_L6:
    }

    void switchUser(int i) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        mCurrentUserId = i;
        WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.get(i);
        if(wallpaperdata == null) {
            wallpaperdata = new WallpaperData(i);
            mWallpaperMap.put(i, wallpaperdata);
            loadSettingsLocked(i);
            wallpaperdata.wallpaperObserver = new WallpaperObserver(wallpaperdata);
            wallpaperdata.wallpaperObserver.startWatching();
        }
        switchWallpaper(wallpaperdata);
        return;
    }

    void switchWallpaper(WallpaperData wallpaperdata) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Object obj1 = null;
        if(wallpaperdata.wallpaperComponent == null) goto _L2; else goto _L1
_L1:
        ComponentName componentname = wallpaperdata.wallpaperComponent;
_L5:
        boolean flag = bindWallpaperComponentLocked(componentname, true, false, wallpaperdata);
        if(!flag) goto _L4; else goto _L3
_L3:
        obj;
        JVM INSTR monitorexit ;
_L6:
        return;
_L2:
        componentname = wallpaperdata.nextWallpaperComponent;
          goto _L5
        RuntimeException runtimeexception;
        runtimeexception;
        obj1 = runtimeexception;
_L4:
        Slog.w("WallpaperService", "Failure starting previous wallpaper", ((Throwable) (obj1)));
        clearWallpaperLocked(false, wallpaperdata.userId);
          goto _L6
        Exception exception;
        exception;
        throw exception;
          goto _L5
    }

    public void systemReady() {
        WallpaperData wallpaperdata = (WallpaperData)mWallpaperMap.get(0);
        switchWallpaper(wallpaperdata);
        wallpaperdata.wallpaperObserver = new WallpaperObserver(wallpaperdata);
        wallpaperdata.wallpaperObserver.startWatching();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.USER_SWITCHED");
        intentfilter.addAction("android.intent.action.USER_REMOVED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                String s = intent.getAction();
                if(!"android.intent.action.USER_SWITCHED".equals(s)) goto _L2; else goto _L1
_L1:
                switchUser(intent.getIntExtra("android.intent.extra.user_id", 0));
_L4:
                return;
_L2:
                if("android.intent.action.USER_REMOVED".equals(s))
                    removeUser(intent.getIntExtra("android.intent.extra.user_id", 0));
                if(true) goto _L4; else goto _L3
_L3:
            }

            final WallpaperManagerService this$0;

             {
                this$0 = WallpaperManagerService.this;
                super();
            }
        }, intentfilter);
    }

    ParcelFileDescriptor updateWallpaperBitmapLocked(String s, WallpaperData wallpaperdata) {
        if(s == null)
            s = "";
        ParcelFileDescriptor parcelfiledescriptor;
        try {
            File file = getWallpaperDir(wallpaperdata.userId);
            if(!file.exists()) {
                file.mkdir();
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
            parcelfiledescriptor = ParcelFileDescriptor.open(new File(file, "wallpaper"), 0x38000000);
            wallpaperdata.name = s;
        }
        catch(FileNotFoundException filenotfoundexception) {
            Slog.w("WallpaperService", "Error setting wallpaper", filenotfoundexception);
            parcelfiledescriptor = null;
        }
        return parcelfiledescriptor;
    }

    static final boolean DEBUG = false;
    static final long MIN_WALLPAPER_CRASH_TIME = 10000L;
    static final String TAG = "WallpaperService";
    static final String WALLPAPER = "wallpaper";
    static final File WALLPAPER_BASE_DIR = new File("/data/system/users");
    static final String WALLPAPER_INFO = "wallpaper_info.xml";
    final Context mContext;
    int mCurrentUserId;
    final IWindowManager mIWindowManager = android.view.IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
    WallpaperData mLastWallpaper;
    final Object mLock = new Object[0];
    final MyPackageMonitor mMonitor = new MyPackageMonitor();
    SparseArray mWallpaperMap;




}
