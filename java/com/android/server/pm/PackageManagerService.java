// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.admin.IDevicePolicyManager;
import android.app.backup.IBackupManager;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.net.Uri;
import android.os.*;
import android.os.storage.IMountService;
import android.security.SystemKeyStore;
import android.util.*;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.app.IMediaContainerService;
import com.android.internal.app.ResolverActivity;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.content.PackageHelper;
import com.android.internal.util.*;
import com.android.server.*;
import dalvik.system.DexFile;
import dalvik.system.StaleDexCacheError;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.*;
import libcore.io.*;
import miui.content.pm.ExtraPackageManager;
import miui.provider.ExtraGuard;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.pm:
//            Settings, Installer, UserManager, PackageSetting, 
//            GrantedPermissions, PackageSettingBase, ExtraPackageManagerServices, PreferredActivity, 
//            BasePermission, PackageSignatures, SharedUserSetting, PackageVerificationState, 
//            PackageVerificationResponse, MiuiSharedUids

public class PackageManagerService extends android.content.pm.IPackageManager.Stub {
    static class DumpState {

        public SharedUserSetting getSharedUser() {
            return mSharedUser;
        }

        public boolean getTitlePrinted() {
            return mTitlePrinted;
        }

        public boolean isDumping(int i) {
            boolean flag;
            flag = true;
            break MISSING_BLOCK_LABEL_2;
            if((mTypes != 0 || i == 1024) && (i & mTypes) == 0)
                flag = false;
            return flag;
        }

        public boolean isOptionEnabled(int i) {
            boolean flag;
            if((i & mOptions) != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean onTitlePrinted() {
            boolean flag = mTitlePrinted;
            mTitlePrinted = true;
            return flag;
        }

        public void setDump(int i) {
            mTypes = i | mTypes;
        }

        public void setOptionEnabled(int i) {
            mOptions = i | mOptions;
        }

        public void setSharedUser(SharedUserSetting sharedusersetting) {
            mSharedUser = sharedusersetting;
        }

        public void setTitlePrinted(boolean flag) {
            mTitlePrinted = flag;
        }

        public static final int DUMP_FEATURES = 2;
        public static final int DUMP_LIBS = 1;
        public static final int DUMP_MESSAGES = 64;
        public static final int DUMP_PACKAGES = 16;
        public static final int DUMP_PERMISSIONS = 8;
        public static final int DUMP_PREFERRED = 512;
        public static final int DUMP_PREFERRED_XML = 1024;
        public static final int DUMP_PROVIDERS = 128;
        public static final int DUMP_RESOLVERS = 4;
        public static final int DUMP_SHARED_USERS = 32;
        public static final int DUMP_VERIFIERS = 256;
        public static final int OPTION_SHOW_FILTERS = 1;
        private int mOptions;
        private SharedUserSetting mSharedUser;
        private boolean mTitlePrinted;
        private int mTypes;

        DumpState() {
        }
    }

    private final class ClearStorageConnection
        implements ServiceConnection {

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            this;
            JVM INSTR monitorenter ;
            mContainerService = com.android.internal.app.IMediaContainerService.Stub.asInterface(ibinder);
            notifyAll();
            return;
        }

        public void onServiceDisconnected(ComponentName componentname) {
        }

        IMediaContainerService mContainerService;
        final PackageManagerService this$0;

        private ClearStorageConnection() {
            this$0 = PackageManagerService.this;
            super();
        }

    }

    static class PackageRemovedInfo {

        void sendBroadcast(boolean flag, boolean flag1) {
            Bundle bundle = new Bundle(1);
            int i;
            if(removedUid >= 0)
                i = removedUid;
            else
                i = uid;
            bundle.putInt("android.intent.extra.UID", i);
            bundle.putBoolean("android.intent.extra.DATA_REMOVED", flag);
            if(flag1)
                bundle.putBoolean("android.intent.extra.REPLACING", true);
            if(removedPackage != null) {
                PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_REMOVED", removedPackage, bundle, null, null, -1);
                if(flag && !flag1)
                    PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_FULLY_REMOVED", removedPackage, bundle, null, null, -1);
            }
            if(removedUid >= 0)
                PackageManagerService.sendPackageBroadcast("android.intent.action.UID_REMOVED", null, bundle, null, null, UserId.getUserId(removedUid));
        }

        InstallArgs args;
        boolean isRemovedPackageSystemUpdate;
        String removedPackage;
        int removedUid;
        int uid;

        PackageRemovedInfo() {
            uid = -1;
            removedUid = -1;
            isRemovedPackageSystemUpdate = false;
            args = null;
        }
    }

    class PackageInstalledInfo {

        String name;
        android.content.pm.PackageParser.Package pkg;
        PackageRemovedInfo removedInfo;
        int returnCode;
        final PackageManagerService this$0;
        int uid;

        PackageInstalledInfo() {
            this$0 = PackageManagerService.this;
            super();
        }
    }

    class AsecInstallArgs extends InstallArgs {

        private void cleanUp() {
            PackageHelper.destroySdDir(cid);
        }

        private final boolean isExternal() {
            boolean flag;
            if((8 & super.flags) != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        private void setCachePath(String s) {
            File file = new File(s);
            libraryPath = (new File(file, "lib")).getPath();
            packagePath = (new File(file, "pkg.apk")).getPath();
            if(isFwdLocked())
                resourcePath = (new File(file, "res.zip")).getPath();
            else
                resourcePath = packagePath;
        }

        boolean checkFreeStorage(IMediaContainerService imediacontainerservice) throws RemoteException {
            boolean flag;
            mContext.grantUriPermission("com.android.defcontainer", super.packageURI, 1);
            flag = imediacontainerservice.checkExternalFreeStorage(super.packageURI, isFwdLocked());
            mContext.revokeUriPermission(super.packageURI, 1);
            return flag;
            Exception exception;
            exception;
            mContext.revokeUriPermission(super.packageURI, 1);
            throw exception;
        }

        void cleanUpResourcesLI() {
            String s = getCodePath();
            int i = mInstaller.rmdex(s);
            if(i < 0)
                Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove dex file for package:  at location ").append(s.toString()).append(", retcode=").append(i).toString());
            cleanUp();
        }

        int copyApk(IMediaContainerService imediacontainerservice, boolean flag) throws RemoteException {
            String s;
            if(flag)
                createCopyFile();
            else
                PackageHelper.destroySdDir(cid);
            mContext.grantUriPermission("com.android.defcontainer", super.packageURI, 1);
            s = imediacontainerservice.copyResourceToContainer(super.packageURI, cid, getEncryptKey(), "pkg.apk", "res.zip", isExternal(), isFwdLocked());
            mContext.revokeUriPermission(super.packageURI, 1);
            Exception exception;
            int i;
            if(s != null) {
                setCachePath(s);
                i = 1;
            } else {
                i = -18;
            }
            return i;
            exception;
            mContext.revokeUriPermission(super.packageURI, 1);
            throw exception;
        }

        void createCopyFile() {
            cid = PackageManagerService.getTempContainerId();
        }

        int doPostCopy(int i) {
            byte byte0;
            if(isFwdLocked() && (i < 10000 || !PackageHelper.fixSdPermissions(cid, i, "pkg.apk"))) {
                Slog.e("PackageManager", (new StringBuilder()).append("Failed to finalize ").append(cid).toString());
                PackageHelper.destroySdDir(cid);
                byte0 = -18;
            } else {
                byte0 = 1;
            }
            return byte0;
        }

        boolean doPostDeleteLI(boolean flag) {
            boolean flag1 = false;
            if(PackageHelper.isContainerMounted(cid))
                flag1 = PackageHelper.unMountSdDir(cid);
            if(flag1 && flag)
                cleanUpResourcesLI();
            return flag1;
        }

        int doPostInstall(int i, int j) {
            if(i == 1) goto _L2; else goto _L1
_L1:
            cleanUp();
_L4:
            return i;
_L2:
            int k;
            String s;
            if(isFwdLocked()) {
                k = j;
                s = "pkg.apk";
            } else {
                k = -1;
                s = null;
            }
            if(j < 10000 || !PackageHelper.fixSdPermissions(cid, k, s)) {
                Slog.e("PackageManager", (new StringBuilder()).append("Failed to finalize ").append(cid).toString());
                PackageHelper.destroySdDir(cid);
                i = -18;
            } else
            if(!PackageHelper.isContainerMounted(cid))
                PackageHelper.mountSdDir(cid, getEncryptKey(), Process.myUid());
            if(true) goto _L4; else goto _L3
_L3:
        }

        int doPreCopy() {
            byte byte0;
            if(isFwdLocked() && !PackageHelper.fixSdPermissions(cid, getPackageUid("com.android.defcontainer", 0), "pkg.apk"))
                byte0 = -18;
            else
                byte0 = 1;
            return byte0;
        }

        int doPreInstall(int i) {
            if(i == 1) goto _L2; else goto _L1
_L1:
            PackageHelper.destroySdDir(cid);
_L4:
            return i;
_L2:
            if(!PackageHelper.isContainerMounted(cid)) {
                String s = PackageHelper.mountSdDir(cid, getEncryptKey(), 1000);
                if(s != null)
                    setCachePath(s);
                else
                    i = -18;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        boolean doRename(int i, String s, String s1) {
            boolean flag;
            String s2;
            flag = false;
            s2 = PackageManagerService.getNextCodePath(s1, s, "/pkg.apk");
            if(!PackageHelper.isContainerMounted(cid) || PackageHelper.unMountSdDir(cid)) goto _L2; else goto _L1
_L1:
            Slog.i("PackageManager", (new StringBuilder()).append("Failed to unmount ").append(cid).append(" before renaming").toString());
_L4:
            return flag;
_L2:
            if(!PackageHelper.renameSdDir(cid, s2)) {
                Slog.e("PackageManager", (new StringBuilder()).append("Failed to rename ").append(cid).append(" to ").append(s2).append(" which might be stale. Will try to clean up.").toString());
                if(!PackageHelper.destroySdDir(s2)) {
                    Slog.e("PackageManager", (new StringBuilder()).append("Very strange. Cannot clean up stale container ").append(s2).toString());
                    continue; /* Loop/switch isn't completed */
                }
                if(!PackageHelper.renameSdDir(cid, s2)) {
                    Slog.e("PackageManager", (new StringBuilder()).append("Failed to rename ").append(cid).append(" to ").append(s2).append(" inspite of cleaning it up.").toString());
                    continue; /* Loop/switch isn't completed */
                }
            }
            String s3;
            if(!PackageHelper.isContainerMounted(s2)) {
                Slog.w("PackageManager", (new StringBuilder()).append("Mounting container ").append(s2).toString());
                s3 = PackageHelper.mountSdDir(s2, getEncryptKey(), 1000);
            } else {
                s3 = PackageHelper.getSdDir(s2);
            }
            if(s3 == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Failed to get cache path for  ").append(s2).toString());
            } else {
                Log.i("PackageManager", (new StringBuilder()).append("Succesfully renamed ").append(cid).append(" to ").append(s2).append(" at new path: ").append(s3).toString());
                cid = s2;
                setCachePath(s3);
                flag = true;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        String getCodePath() {
            return packagePath;
        }

        String getNativeLibraryPath() {
            return libraryPath;
        }

        String getPackageName() {
            return PackageManagerService.getAsecPackageName(cid);
        }

        String getResourcePath() {
            return resourcePath;
        }

        boolean matchContainer(String s) {
            boolean flag;
            if(cid.startsWith(s))
                flag = true;
            else
                flag = false;
            return flag;
        }

        static final String PUBLIC_RES_FILE_NAME = "res.zip";
        static final String RES_FILE_NAME = "pkg.apk";
        String cid;
        String libraryPath;
        String packagePath;
        String resourcePath;
        final PackageManagerService this$0;


        AsecInstallArgs(Uri uri, String s, boolean flag, boolean flag1) {
            boolean flag2 = false;
            this$0 = PackageManagerService.this;
            byte byte0;
            if(flag)
                byte0 = 8;
            else
                byte0 = 0;
            if(flag1)
                flag2 = true;
            super(uri, null, byte0 | flag2, null, null);
            cid = s;
        }

        AsecInstallArgs(InstallParams installparams) {
            this$0 = PackageManagerService.this;
            super(installparams.getPackageUri(), installparams.observer, installparams.flags, installparams.installerPackageName, installparams.manifestDigest);
        }

        AsecInstallArgs(String s, String s1, String s2, boolean flag, boolean flag1) {
            this$0 = PackageManagerService.this;
            byte byte0;
            boolean flag2;
            int i;
            String s3;
            if(flag)
                byte0 = 8;
            else
                byte0 = 0;
            if(flag1)
                flag2 = true;
            else
                flag2 = false;
            super(null, null, byte0 | flag2, null, null);
            i = s.lastIndexOf("/");
            s3 = s.substring(0, i);
            cid = s3.substring(1 + s3.lastIndexOf("/"), i);
            setCachePath(s3);
        }

        AsecInstallArgs(String s, boolean flag) {
            boolean flag1 = false;
            this$0 = PackageManagerService.this;
            byte byte0;
            if(isAsecExternal(s))
                byte0 = 8;
            else
                byte0 = 0;
            if(flag)
                flag1 = true;
            super(null, null, byte0 | flag1, null, null);
            cid = s;
            setCachePath(PackageHelper.getSdDir(s));
        }
    }

    class FileInstallArgs extends InstallArgs {

        private boolean cleanUp() {
            boolean flag = true;
            String s = getCodePath();
            String s1 = getResourcePath();
            if(s != null) {
                File file = new File(s);
                if(!file.exists()) {
                    Slog.w("PackageManager", (new StringBuilder()).append("Package source ").append(s).append(" does not exist.").toString());
                    flag = false;
                }
                file.delete();
            }
            if(s1 != null && !s1.equals(s)) {
                File file1 = new File(s1);
                if(!file1.exists())
                    Slog.w("PackageManager", (new StringBuilder()).append("Package public source ").append(file1).append(" does not exist.").toString());
                if(file1.exists())
                    file1.delete();
            }
            return flag;
        }

        private String getResourcePathFromCodePath() {
            String s = getCodePath();
            if(isFwdLocked()) {
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append(mAppInstallDir.getPath());
                stringbuilder.append('/');
                stringbuilder.append(PackageManagerService.getApkName(s));
                stringbuilder.append(".zip");
                if(s.endsWith(".tmp"))
                    stringbuilder.append(".tmp");
                s = stringbuilder.toString();
            }
            return s;
        }

        private boolean setPermissions() {
            boolean flag = true;
            if(!isFwdLocked()) {
                int i = FileUtils.setPermissions(getCodePath(), 420, -1, -1);
                if(i != 0) {
                    Slog.e("PackageManager", (new StringBuilder()).append("Couldn't set new package file permissions for ").append(getCodePath()).append(". The return code was: ").append(i).toString());
                    flag = false;
                }
            }
            return flag;
        }

        boolean checkFreeStorage(IMediaContainerService imediacontainerservice) throws RemoteException {
            DeviceStorageMonitorService devicestoragemonitorservice = (DeviceStorageMonitorService)ServiceManager.getService("devicestoragemonitor");
            if(devicestoragemonitorservice != null) goto _L2; else goto _L1
_L1:
            long l;
            Log.w("PackageManager", "Couldn't get low memory threshold; no free limit imposed");
            l = 0L;
_L6:
            boolean flag;
            mContext.grantUriPermission("com.android.defcontainer", super.packageURI, 1);
            flag = imediacontainerservice.checkInternalFreeStorage(super.packageURI, isFwdLocked(), l);
            boolean flag1;
            flag1 = flag;
            mContext.revokeUriPermission(super.packageURI, 1);
_L4:
            return flag1;
_L2:
            if(!devicestoragemonitorservice.isMemoryLow())
                break; /* Loop/switch isn't completed */
            Log.w("PackageManager", "Memory is reported as being too low; aborting package install");
            flag1 = false;
            if(true) goto _L4; else goto _L3
_L3:
            l = devicestoragemonitorservice.getMemoryLowThreshold();
            if(true) goto _L6; else goto _L5
_L5:
            Exception exception;
            exception;
            mContext.revokeUriPermission(super.packageURI, 1);
            throw exception;
        }

        void cleanUpResourcesLI() {
            String s = getCodePath();
            if(cleanUp()) {
                int i = mInstaller.rmdex(s);
                if(i < 0)
                    Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove dex file for package:  at location ").append(s).append(", retcode=").append(i).toString());
            }
        }

        int copyApk(IMediaContainerService imediacontainerservice, boolean flag) throws RemoteException {
            File file;
            if(flag)
                createCopyFile();
            file = new File(codeFileName);
            if(created) goto _L2; else goto _L1
_L1:
            int i;
            boolean flag1;
            try {
                file.createNewFile();
                flag1 = setPermissions();
            }
            catch(IOException ioexception1) {
                Slog.w("PackageManager", (new StringBuilder()).append("Failed to create file ").append(file).toString());
                i = -4;
                continue; /* Loop/switch isn't completed */
            }
            if(flag1) goto _L2; else goto _L3
_L3:
            i = -4;
_L5:
            return i;
_L2:
            ParcelFileDescriptor parcelfiledescriptor = ParcelFileDescriptor.open(file, 0x30000000);
            int j;
            mContext.grantUriPermission("com.android.defcontainer", super.packageURI, 1);
            j = imediacontainerservice.copyResource(super.packageURI, null, parcelfiledescriptor);
            i = j;
            IoUtils.closeQuietly(parcelfiledescriptor);
            mContext.revokeUriPermission(super.packageURI, 1);
            if(isFwdLocked()) {
                File file1 = new File(getResourcePath());
                try {
                    PackageHelper.extractPublicFiles(codeFileName, file1);
                }
                catch(IOException ioexception) {
                    Slog.e("PackageManager", "Couldn't create a new zip file for the public parts of a forward-locked app.");
                    file1.delete();
                    i = -4;
                }
            }
            continue; /* Loop/switch isn't completed */
            FileNotFoundException filenotfoundexception;
            filenotfoundexception;
            Slog.e("PackageManager", (new StringBuilder()).append("Failed to create file descriptor for : ").append(codeFileName).toString());
            i = -4;
            if(true) goto _L5; else goto _L4
_L4:
            Exception exception;
            exception;
            IoUtils.closeQuietly(parcelfiledescriptor);
            mContext.revokeUriPermission(super.packageURI, 1);
            throw exception;
        }

        void createCopyFile() {
            File file;
            if(isFwdLocked())
                file = mDrmAppPrivateInstallDir;
            else
                file = mAppInstallDir;
            installDir = file;
            codeFileName = createTempPackageFile(installDir).getPath();
            resourceFileName = getResourcePathFromCodePath();
            created = true;
        }

        boolean doPostDeleteLI(boolean flag) {
            cleanUpResourcesLI();
            return true;
        }

        int doPostInstall(int i, int j) {
            if(i != 1)
                cleanUp();
            return i;
        }

        int doPreInstall(int i) {
            if(i != 1)
                cleanUp();
            return i;
        }

        boolean doRename(int i, String s, String s1) {
            boolean flag = false;
            if(i == 1) goto _L2; else goto _L1
_L1:
            cleanUp();
_L4:
            return flag;
_L2:
            File file = new File(getCodePath());
            File file1 = new File(getResourcePath());
            String s2 = PackageManagerService.getNextCodePath(s1, s, ".apk");
            File file2 = new File(installDir, (new StringBuilder()).append(s2).append(".apk").toString());
            if(file.renameTo(file2)) {
                codeFileName = file2.getPath();
                File file3 = new File(getResourcePathFromCodePath());
                if(!isFwdLocked() || file1.renameTo(file3)) {
                    resourceFileName = getResourcePathFromCodePath();
                    if(setPermissions())
                        flag = true;
                }
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        String getCodePath() {
            return codeFileName;
        }

        String getNativeLibraryPath() {
            return libraryPath;
        }

        String getResourcePath() {
            return resourceFileName;
        }

        String codeFileName;
        boolean created;
        File installDir;
        String libraryPath;
        String resourceFileName;
        final PackageManagerService this$0;

        FileInstallArgs(Uri uri, String s, String s1) {
            this$0 = PackageManagerService.this;
            super(uri, null, 0, null, null);
            created = false;
            File file;
            String s2;
            if(isFwdLocked())
                file = mDrmAppPrivateInstallDir;
            else
                file = mAppInstallDir;
            installDir = file;
            s2 = PackageManagerService.getNextCodePath(null, s, ".apk");
            codeFileName = (new File(installDir, (new StringBuilder()).append(s2).append(".apk").toString())).getPath();
            resourceFileName = getResourcePathFromCodePath();
            libraryPath = (new File(s1, "lib")).getPath();
        }

        FileInstallArgs(InstallParams installparams) {
            this$0 = PackageManagerService.this;
            super(installparams.getPackageUri(), installparams.observer, installparams.flags, installparams.installerPackageName, installparams.manifestDigest);
            created = false;
        }

        FileInstallArgs(String s, String s1, String s2) {
            this$0 = PackageManagerService.this;
            super(null, null, 0, null, null);
            created = false;
            installDir = (new File(s)).getParentFile();
            codeFileName = s;
            resourceFileName = s1;
            libraryPath = s2;
        }
    }

    static abstract class InstallArgs {

        abstract boolean checkFreeStorage(IMediaContainerService imediacontainerservice) throws RemoteException;

        abstract void cleanUpResourcesLI();

        abstract int copyApk(IMediaContainerService imediacontainerservice, boolean flag) throws RemoteException;

        abstract void createCopyFile();

        int doPostCopy(int i) {
            return 1;
        }

        abstract boolean doPostDeleteLI(boolean flag);

        abstract int doPostInstall(int i, int j);

        int doPreCopy() {
            return 1;
        }

        abstract int doPreInstall(int i);

        abstract boolean doRename(int i, String s, String s1);

        abstract String getCodePath();

        abstract String getNativeLibraryPath();

        abstract String getResourcePath();

        protected boolean isFwdLocked() {
            boolean flag;
            if((1 & flags) != 0)
                flag = true;
            else
                flag = false;
            return flag;
        }

        final int flags;
        final String installerPackageName;
        final ManifestDigest manifestDigest;
        final IPackageInstallObserver observer;
        final Uri packageURI;

        InstallArgs(Uri uri, IPackageInstallObserver ipackageinstallobserver, int i, String s, ManifestDigest manifestdigest) {
            packageURI = uri;
            flags = i;
            observer = ipackageinstallobserver;
            installerPackageName = s;
            manifestDigest = manifestdigest;
        }
    }

    class MoveParams extends HandlerParams {

        void handleReturnCode() {
            byte byte0;
            targetArgs.doPostInstall(mRet, uid);
            byte0 = -6;
            if(mRet != 1) goto _L2; else goto _L1
_L1:
            byte0 = 1;
_L4:
            processPendingMove(this, byte0);
            return;
_L2:
            if(mRet == -4)
                byte0 = -1;
            if(true) goto _L4; else goto _L3
_L3:
        }

        void handleServiceError() {
            mRet = -110;
        }

        public void handleStartCopy() throws RemoteException {
            mRet = -4;
            if(targetArgs.checkFreeStorage(mContainerService)) goto _L2; else goto _L1
_L1:
            Log.w("PackageManager", "Insufficient storage to install");
_L4:
            return;
_L2:
            mRet = srcArgs.doPreCopy();
            if(mRet == 1) {
                mRet = targetArgs.copyApk(mContainerService, false);
                if(mRet != 1) {
                    srcArgs.doPostCopy(uid);
                } else {
                    mRet = srcArgs.doPostCopy(uid);
                    if(mRet == 1) {
                        mRet = targetArgs.doPreInstall(mRet);
                        if(mRet == 1);
                    }
                }
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final int flags;
        int mRet;
        final IPackageMoveObserver observer;
        final String packageName;
        final InstallArgs srcArgs;
        final InstallArgs targetArgs;
        final PackageManagerService this$0;
        int uid;

        MoveParams(InstallArgs installargs, IPackageMoveObserver ipackagemoveobserver, int i, String s, String s1, int j) {
            this$0 = PackageManagerService.this;
            super();
            srcArgs = installargs;
            observer = ipackagemoveobserver;
            flags = i;
            packageName = s;
            uid = j;
            if(installargs != null)
                targetArgs = createInstallArgs(Uri.fromFile(new File(installargs.getCodePath())), i, s, s1);
            else
                targetArgs = null;
        }
    }

    class MeasureParams extends HandlerParams {

        void handleReturnCode() {
            if(mObserver == null)
                break MISSING_BLOCK_LABEL_24;
            mObserver.onGetStatsCompleted(mStats, mSuccess);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.i("PackageManager", "Observer no longer exists.");
              goto _L1
        }

        void handleServiceError() {
            Slog.e("PackageManager", (new StringBuilder()).append("Could not measure application ").append(mStats.packageName).append(" external storage").toString());
        }

        void handleStartCopy() throws RemoteException {
            synchronized(mInstallLock) {
                mSuccess = getPackageSizeInfoLI(mStats.packageName, mStats);
            }
            boolean flag;
            if(Environment.isExternalStorageEmulated()) {
                flag = true;
            } else {
                String s = Environment.getExternalStorageState();
                if(s.equals("mounted") || s.equals("mounted_ro"))
                    flag = true;
                else
                    flag = false;
            }
            if(flag) {
                File file = Environment.getExternalStorageAppCacheDirectory(mStats.packageName);
                long l = mContainerService.calculateDirectorySize(file.getPath());
                mStats.externalCacheSize = l;
                File file1 = Environment.getExternalStorageAppDataDirectory(mStats.packageName);
                long l1 = mContainerService.calculateDirectorySize(file1.getPath());
                if(file.getParentFile().equals(file1))
                    l1 -= l;
                mStats.externalDataSize = l1;
                File file2 = Environment.getExternalStorageAppMediaDirectory(mStats.packageName);
                mStats.externalMediaSize = mContainerService.calculateDirectorySize(file2.getPath());
                File file3 = Environment.getExternalStorageAppObbDirectory(mStats.packageName);
                mStats.externalObbSize = mContainerService.calculateDirectorySize(file3.getPath());
            }
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        private final IPackageStatsObserver mObserver;
        private final PackageStats mStats;
        private boolean mSuccess;
        final PackageManagerService this$0;

        public MeasureParams(PackageStats packagestats, IPackageStatsObserver ipackagestatsobserver) {
            this$0 = PackageManagerService.this;
            super();
            mObserver = ipackagestatsobserver;
            mStats = packagestats;
        }
    }

    private abstract class HandlerParams {

        abstract void handleReturnCode();

        abstract void handleServiceError();

        abstract void handleStartCopy() throws RemoteException;

        final void serviceError() {
            handleServiceError();
            handleReturnCode();
        }

        final boolean startCopy() {
            int i;
            i = 1 + mRetries;
            mRetries = i;
            if(i <= 4) goto _L2; else goto _L1
_L1:
            boolean flag;
            Slog.w("PackageManager", "Failed to invoke remote methods on default container service. Giving up");
            mHandler.sendEmptyMessage(11);
            handleServiceError();
            flag = false;
              goto _L3
_L2:
            handleStartCopy();
            flag = true;
_L4:
            handleReturnCode();
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            mHandler.sendEmptyMessage(10);
            flag = false;
            if(true) goto _L4; else goto _L3
_L3:
            return flag;
        }

        private static final int MAX_RETRIES = 4;
        private int mRetries;
        final PackageManagerService this$0;

        private HandlerParams() {
            this$0 = PackageManagerService.this;
            super();
            mRetries = 0;
        }

    }

    private final class AppDirObserver extends FileObserver {

        public void onEvent(int i, String s) {
            String s1;
            int j;
            String s2;
            int k;
            s1 = null;
            j = -1;
            s2 = null;
            k = -1;
            Object obj = mInstallLock;
            obj;
            JVM INSTR monitorenter ;
            String s3;
            File file;
            s3 = null;
            file = null;
            if(s == null)
                break MISSING_BLOCK_LABEL_64;
            File file1 = new File(mRootDir, s);
            String s5 = file1.getPath();
            s3 = s5;
            file = file1;
            if(PackageManagerService.isPackageFilename(s)) goto _L2; else goto _L1
_L1:
            obj;
            JVM INSTR monitorexit ;
              goto _L3
_L2:
            if(!PackageManagerService.ignoreCodePath(s3)) goto _L5; else goto _L4
_L4:
            obj;
            JVM INSTR monitorexit ;
              goto _L3
_L16:
            Exception exception;
            throw exception;
_L5:
            android.content.pm.PackageParser.Package package1;
            synchronized(mPackages) {
                package1 = (android.content.pm.PackageParser.Package)mAppDirs.get(s3);
            }
            if((i & 0x248) == 0 || package1 == null)
                break MISSING_BLOCK_LABEL_171;
            removePackageLI(package1, true);
            s1 = package1.applicationInfo.packageName;
            j = package1.applicationInfo.uid;
            if((i & 0x88) == 0 || package1 != null) goto _L7; else goto _L6
_L6:
            PackageManagerService packagemanagerservice = PackageManagerService.this;
            if(!mIsRom) goto _L9; else goto _L8
_L8:
            byte byte0 = 65;
_L13:
            android.content.pm.PackageParser.Package package2 = packagemanagerservice.scanPackageLI(file, 4 | (byte0 | 2), 97, System.currentTimeMillis());
            if(package2 == null) goto _L7; else goto _L10
_L10:
            HashMap hashmap2 = mPackages;
            hashmap2;
            JVM INSTR monitorenter ;
            PackageManagerService packagemanagerservice1;
            String s4;
            packagemanagerservice1 = PackageManagerService.this;
            s4 = package2.packageName;
            if(package2.permissions.size() <= 0) goto _L12; else goto _L11
_L11:
            int l = 1;
_L14:
            packagemanagerservice1.updatePermissionsLPw(s4, package2, l);
            s2 = package2.applicationInfo.packageName;
            k = package2.applicationInfo.uid;
_L7:
            synchronized(mPackages) {
                mSettings.writeLPr();
            }
            obj;
            JVM INSTR monitorexit ;
            if(s1 != null) {
                Bundle bundle = new Bundle(1);
                bundle.putInt("android.intent.extra.UID", j);
                bundle.putBoolean("android.intent.extra.DATA_REMOVED", false);
                PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_REMOVED", s1, bundle, null, null, -1);
            }
            if(s2 != null) {
                Bundle bundle1 = new Bundle(1);
                bundle1.putInt("android.intent.extra.UID", k);
                PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", s2, bundle1, null, null, -1);
            }
              goto _L3
            exception1;
            hashmap;
            JVM INSTR monitorexit ;
            throw exception1;
_L9:
            byte0 = 0;
              goto _L13
_L12:
            l = 0;
              goto _L14
            Exception exception3;
            exception3;
            hashmap2;
            JVM INSTR monitorexit ;
            throw exception3;
            exception2;
            hashmap1;
            JVM INSTR monitorexit ;
            throw exception2;
            exception;
            continue; /* Loop/switch isn't completed */
_L3:
            return;
            exception;
            if(true) goto _L16; else goto _L15
_L15:
        }

        private final boolean mIsRom;
        private final String mRootDir;
        final PackageManagerService this$0;

        public AppDirObserver(String s, int i, boolean flag) {
            this$0 = PackageManagerService.this;
            super(s, i);
            mRootDir = s;
            mIsRom = flag;
        }
    }

    private final class ServiceIntentResolver extends IntentResolver {

        public final void addService(android.content.pm.PackageParser.Service service) {
            mServices.put(service.getComponentName(), service);
            int i = service.intents.size();
            for(int j = 0; j < i; j++) {
                android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo = (android.content.pm.PackageParser.ServiceIntentInfo)service.intents.get(j);
                if(!serviceintentinfo.debugCheck())
                    Log.w("PackageManager", (new StringBuilder()).append("==> For Service ").append(((ComponentInfo) (service.info)).name).toString());
                addFilter(serviceintentinfo);
            }

        }

        protected volatile boolean allowFilterResult(IntentFilter intentfilter, List list) {
            return allowFilterResult((android.content.pm.PackageParser.ServiceIntentInfo)intentfilter, list);
        }

        protected boolean allowFilterResult(android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo, List list) {
            ServiceInfo serviceinfo;
            int i;
            serviceinfo = serviceintentinfo.service.info;
            i = -1 + list.size();
_L3:
            ServiceInfo serviceinfo1;
            if(i < 0)
                break MISSING_BLOCK_LABEL_76;
            serviceinfo1 = ((ResolveInfo)list.get(i)).serviceInfo;
            if(((ComponentInfo) (serviceinfo1)).name != ((ComponentInfo) (serviceinfo)).name || ((ComponentInfo) (serviceinfo1)).packageName != ((ComponentInfo) (serviceinfo)).packageName) goto _L2; else goto _L1
_L1:
            boolean flag = false;
_L4:
            return flag;
_L2:
            i--;
              goto _L3
            flag = true;
              goto _L4
        }

        protected volatile void dumpFilter(PrintWriter printwriter, String s, IntentFilter intentfilter) {
            dumpFilter(printwriter, s, (android.content.pm.PackageParser.ServiceIntentInfo)intentfilter);
        }

        protected void dumpFilter(PrintWriter printwriter, String s, android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo) {
            printwriter.print(s);
            printwriter.print(Integer.toHexString(System.identityHashCode(serviceintentinfo.service)));
            printwriter.print(' ');
            printwriter.print(serviceintentinfo.service.getComponentShortName());
            printwriter.print(" filter ");
            printwriter.println(Integer.toHexString(System.identityHashCode(serviceintentinfo)));
        }

        protected volatile boolean isFilterStopped(IntentFilter intentfilter, int i) {
            return isFilterStopped((android.content.pm.PackageParser.ServiceIntentInfo)intentfilter, i);
        }

        protected boolean isFilterStopped(android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo, int i) {
            boolean flag = true;
            if(PackageManagerService.sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
            return flag;
_L2:
            android.content.pm.PackageParser.Package package1 = serviceintentinfo.service.owner;
            if(package1 != null) {
                PackageSetting packagesetting = (PackageSetting)package1.mExtras;
                if(packagesetting != null) {
                    if(!packagesetting.getStopped(i) || (1 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
                        flag = false;
                    continue; /* Loop/switch isn't completed */
                }
            }
            flag = false;
            if(true) goto _L1; else goto _L3
_L3:
        }

        protected ResolveInfo newResult(android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo, int i, int j) {
            int k;
            ResolveInfo resolveinfo;
            k = 0;
            resolveinfo = null;
            break MISSING_BLOCK_LABEL_6;
            while(true)  {
                do
                    return resolveinfo;
                while(!PackageManagerService.sUserManager.exists(j) || !mSettings.isEnabledLPr(serviceintentinfo.service.info, mFlags, j));
                android.content.pm.PackageParser.Service service = serviceintentinfo.service;
                if(!mSafeMode || (1 & service.info.applicationInfo.flags) != 0) {
                    resolveinfo = new ResolveInfo();
                    PackageSetting packagesetting = (PackageSetting)service.owner.mExtras;
                    int l = mFlags;
                    boolean flag;
                    if(packagesetting != null)
                        flag = packagesetting.getStopped(j);
                    else
                        flag = false;
                    if(packagesetting != null)
                        k = packagesetting.getEnabled(j);
                    resolveinfo.serviceInfo = PackageParser.generateServiceInfo(service, l, flag, k, j);
                    if((0x40 & mFlags) != 0)
                        resolveinfo.filter = serviceintentinfo;
                    resolveinfo.priority = serviceintentinfo.getPriority();
                    resolveinfo.preferredOrder = service.owner.mPreferredOrder;
                    resolveinfo.match = i;
                    resolveinfo.isDefault = serviceintentinfo.hasDefault;
                    resolveinfo.labelRes = serviceintentinfo.labelRes;
                    resolveinfo.nonLocalizedLabel = serviceintentinfo.nonLocalizedLabel;
                    resolveinfo.icon = serviceintentinfo.icon;
                    resolveinfo.system = PackageManagerService.isSystemApp(resolveinfo.serviceInfo.applicationInfo);
                }
            }
        }

        protected volatile Object newResult(IntentFilter intentfilter, int i, int j) {
            return newResult((android.content.pm.PackageParser.ServiceIntentInfo)intentfilter, i, j);
        }

        protected volatile String packageForFilter(IntentFilter intentfilter) {
            return packageForFilter((android.content.pm.PackageParser.ServiceIntentInfo)intentfilter);
        }

        protected String packageForFilter(android.content.pm.PackageParser.ServiceIntentInfo serviceintentinfo) {
            return serviceintentinfo.service.owner.packageName;
        }

        public List queryIntent(Intent intent, String s, int i, int j) {
            List list;
            if(!PackageManagerService.sUserManager.exists(j)) {
                list = null;
            } else {
                mFlags = i;
                boolean flag;
                if((0x10000 & i) != 0)
                    flag = true;
                else
                    flag = false;
                list = super.queryIntent(intent, s, flag, j);
            }
            return list;
        }

        public List queryIntent(Intent intent, String s, boolean flag, int i) {
            int j;
            if(flag)
                j = 0x10000;
            else
                j = 0;
            mFlags = j;
            return super.queryIntent(intent, s, flag, i);
        }

        public List queryIntentForPackage(Intent intent, String s, int i, ArrayList arraylist, int j) {
            List list;
            list = null;
            break MISSING_BLOCK_LABEL_3;
            if(PackageManagerService.sUserManager.exists(j) && arraylist != null) {
                mFlags = i;
                boolean flag;
                int k;
                ArrayList arraylist1;
                if((0x10000 & i) != 0)
                    flag = true;
                else
                    flag = false;
                k = arraylist.size();
                arraylist1 = new ArrayList(k);
                for(int l = 0; l < k; l++) {
                    ArrayList arraylist2 = ((android.content.pm.PackageParser.Service)arraylist.get(l)).intents;
                    if(arraylist2 != null && arraylist2.size() > 0)
                        arraylist1.add(arraylist2);
                }

                list = super.queryIntentFromList(intent, s, flag, arraylist1, j);
            }
            return list;
        }

        public final void removeService(android.content.pm.PackageParser.Service service) {
            mServices.remove(service.getComponentName());
            int i = service.intents.size();
            for(int j = 0; j < i; j++)
                removeFilter((android.content.pm.PackageParser.ServiceIntentInfo)service.intents.get(j));

        }

        protected void sortResults(List list) {
            Collections.sort(list, PackageManagerService.mResolvePrioritySorter);
        }

        private int mFlags;
        private final HashMap mServices;
        final PackageManagerService this$0;


        private ServiceIntentResolver() {
            this$0 = PackageManagerService.this;
            super();
            mServices = new HashMap();
        }

    }

    private final class ActivityIntentResolver extends IntentResolver {

        public final void addActivity(android.content.pm.PackageParser.Activity activity, String s) {
            boolean flag = PackageManagerService.isSystemApp(activity.info.applicationInfo);
            mActivities.put(activity.getComponentName(), activity);
            int i = activity.intents.size();
            for(int j = 0; j < i; j++) {
                android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo = (android.content.pm.PackageParser.ActivityIntentInfo)activity.intents.get(j);
                if(!flag && activityintentinfo.getPriority() > 0 && "activity".equals(s)) {
                    activityintentinfo.setPriority(0);
                    Log.w("PackageManager", (new StringBuilder()).append("Package ").append(activity.info.applicationInfo.packageName).append(" has activity ").append(activity.className).append(" with priority > 0, forcing to 0").toString());
                }
                if(!activityintentinfo.debugCheck())
                    Log.w("PackageManager", (new StringBuilder()).append("==> For Activity ").append(((ComponentInfo) (activity.info)).name).toString());
                addFilter(activityintentinfo);
            }

        }

        protected volatile boolean allowFilterResult(IntentFilter intentfilter, List list) {
            return allowFilterResult((android.content.pm.PackageParser.ActivityIntentInfo)intentfilter, list);
        }

        protected boolean allowFilterResult(android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo, List list) {
            ActivityInfo activityinfo;
            int i;
            activityinfo = activityintentinfo.activity.info;
            i = -1 + list.size();
_L3:
            ActivityInfo activityinfo1;
            if(i < 0)
                break MISSING_BLOCK_LABEL_76;
            activityinfo1 = ((ResolveInfo)list.get(i)).activityInfo;
            if(((ComponentInfo) (activityinfo1)).name != ((ComponentInfo) (activityinfo)).name || ((ComponentInfo) (activityinfo1)).packageName != ((ComponentInfo) (activityinfo)).packageName) goto _L2; else goto _L1
_L1:
            boolean flag = false;
_L4:
            return flag;
_L2:
            i--;
              goto _L3
            flag = true;
              goto _L4
        }

        protected volatile void dumpFilter(PrintWriter printwriter, String s, IntentFilter intentfilter) {
            dumpFilter(printwriter, s, (android.content.pm.PackageParser.ActivityIntentInfo)intentfilter);
        }

        protected void dumpFilter(PrintWriter printwriter, String s, android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo) {
            printwriter.print(s);
            printwriter.print(Integer.toHexString(System.identityHashCode(activityintentinfo.activity)));
            printwriter.print(' ');
            printwriter.print(activityintentinfo.activity.getComponentShortName());
            printwriter.print(" filter ");
            printwriter.println(Integer.toHexString(System.identityHashCode(activityintentinfo)));
        }

        protected volatile boolean isFilterStopped(IntentFilter intentfilter, int i) {
            return isFilterStopped((android.content.pm.PackageParser.ActivityIntentInfo)intentfilter, i);
        }

        protected boolean isFilterStopped(android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo, int i) {
            boolean flag = true;
            if(PackageManagerService.sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
            return flag;
_L2:
            android.content.pm.PackageParser.Package package1 = activityintentinfo.activity.owner;
            if(package1 != null) {
                PackageSetting packagesetting = (PackageSetting)package1.mExtras;
                if(packagesetting != null) {
                    if(!packagesetting.getStopped(i) || (1 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
                        flag = false;
                    continue; /* Loop/switch isn't completed */
                }
            }
            flag = false;
            if(true) goto _L1; else goto _L3
_L3:
        }

        protected ResolveInfo newResult(android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo, int i, int j) {
            int k;
            ResolveInfo resolveinfo;
            k = 0;
            resolveinfo = null;
            break MISSING_BLOCK_LABEL_6;
            while(true)  {
                do
                    return resolveinfo;
                while(!PackageManagerService.sUserManager.exists(j) || !mSettings.isEnabledLPr(activityintentinfo.activity.info, mFlags, j));
                android.content.pm.PackageParser.Activity activity = activityintentinfo.activity;
                if(!mSafeMode || (1 & activity.info.applicationInfo.flags) != 0) {
                    resolveinfo = new ResolveInfo();
                    PackageSetting packagesetting = (PackageSetting)activity.owner.mExtras;
                    int l = mFlags;
                    boolean flag;
                    if(packagesetting != null)
                        flag = packagesetting.getStopped(j);
                    else
                        flag = false;
                    if(packagesetting != null)
                        k = packagesetting.getEnabled(j);
                    resolveinfo.activityInfo = PackageParser.generateActivityInfo(activity, l, flag, k, j);
                    if((0x40 & mFlags) != 0)
                        resolveinfo.filter = activityintentinfo;
                    resolveinfo.priority = activityintentinfo.getPriority();
                    resolveinfo.preferredOrder = activity.owner.mPreferredOrder;
                    resolveinfo.match = i;
                    resolveinfo.isDefault = activityintentinfo.hasDefault;
                    resolveinfo.labelRes = activityintentinfo.labelRes;
                    resolveinfo.nonLocalizedLabel = activityintentinfo.nonLocalizedLabel;
                    resolveinfo.icon = activityintentinfo.icon;
                    resolveinfo.system = PackageManagerService.isSystemApp(resolveinfo.activityInfo.applicationInfo);
                }
            }
        }

        protected volatile Object newResult(IntentFilter intentfilter, int i, int j) {
            return newResult((android.content.pm.PackageParser.ActivityIntentInfo)intentfilter, i, j);
        }

        protected volatile String packageForFilter(IntentFilter intentfilter) {
            return packageForFilter((android.content.pm.PackageParser.ActivityIntentInfo)intentfilter);
        }

        protected String packageForFilter(android.content.pm.PackageParser.ActivityIntentInfo activityintentinfo) {
            return activityintentinfo.activity.owner.packageName;
        }

        public List queryIntent(Intent intent, String s, int i, int j) {
            List list;
            if(!PackageManagerService.sUserManager.exists(j)) {
                list = null;
            } else {
                mFlags = i;
                boolean flag;
                if((0x10000 & i) != 0)
                    flag = true;
                else
                    flag = false;
                list = super.queryIntent(intent, s, flag, j);
            }
            return list;
        }

        public List queryIntent(Intent intent, String s, boolean flag, int i) {
            List list;
            if(!PackageManagerService.sUserManager.exists(i)) {
                list = null;
            } else {
                int j;
                if(flag)
                    j = 0x10000;
                else
                    j = 0;
                mFlags = j;
                list = super.queryIntent(intent, s, flag, i);
            }
            return list;
        }

        public List queryIntentForPackage(Intent intent, String s, int i, ArrayList arraylist, int j) {
            List list;
            list = null;
            break MISSING_BLOCK_LABEL_3;
            if(PackageManagerService.sUserManager.exists(j) && arraylist != null) {
                mFlags = i;
                boolean flag;
                int k;
                ArrayList arraylist1;
                if((0x10000 & i) != 0)
                    flag = true;
                else
                    flag = false;
                k = arraylist.size();
                arraylist1 = new ArrayList(k);
                for(int l = 0; l < k; l++) {
                    ArrayList arraylist2 = ((android.content.pm.PackageParser.Activity)arraylist.get(l)).intents;
                    if(arraylist2 != null && arraylist2.size() > 0)
                        arraylist1.add(arraylist2);
                }

                list = super.queryIntentFromList(intent, s, flag, arraylist1, j);
            }
            return list;
        }

        public final void removeActivity(android.content.pm.PackageParser.Activity activity, String s) {
            mActivities.remove(activity.getComponentName());
            int i = activity.intents.size();
            for(int j = 0; j < i; j++)
                removeFilter((android.content.pm.PackageParser.ActivityIntentInfo)activity.intents.get(j));

        }

        protected void sortResults(List list) {
            Collections.sort(list, PackageManagerService.mResolvePrioritySorter);
        }

        private final HashMap mActivities;
        private int mFlags;
        final PackageManagerService this$0;


        private ActivityIntentResolver() {
            this$0 = PackageManagerService.this;
            super();
            mActivities = new HashMap();
        }

    }

    class PackageHandler extends Handler {

        private boolean connectToService() {
            boolean flag = true;
            Intent intent = (new Intent()).setComponent(PackageManagerService.DEFAULT_CONTAINER_COMPONENT);
            Process.setThreadPriority(0);
            if(mContext.bindService(intent, mDefContainerConn, flag)) {
                Process.setThreadPriority(10);
                mBound = flag;
            } else {
                Process.setThreadPriority(10);
                flag = false;
            }
            return flag;
        }

        private void disconnectService() {
            mContainerService = null;
            mBound = false;
            Process.setThreadPriority(0);
            mContext.unbindService(mDefContainerConn);
            Process.setThreadPriority(10);
        }

        void doHandleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 16: default 84
        //                       1 539
        //                       2 84
        //                       3 178
        //                       4 84
        //                       5 85
        //                       6 465
        //                       7 815
        //                       8 84
        //                       9 900
        //                       10 384
        //                       11 527
        //                       12 1242
        //                       13 1342
        //                       14 1409
        //                       15 1619
        //                       16 1512;
               goto _L1 _L2 _L1 _L3 _L1 _L4 _L5 _L6 _L1 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14
_L1:
            return;
_L4:
            HandlerParams handlerparams1 = (HandlerParams)message.obj;
            int k2 = mPendingInstalls.size();
            if(!mBound) {
                if(!connectToService()) {
                    Slog.e("PackageManager", "Failed to bind to media container service");
                    handlerparams1.serviceError();
                } else {
                    mPendingInstalls.add(k2, handlerparams1);
                }
            } else {
                mPendingInstalls.add(k2, handlerparams1);
                if(k2 == 0)
                    mHandler.sendEmptyMessage(3);
            }
              goto _L1
_L3:
            if(message.obj != null)
                mContainerService = (IMediaContainerService)message.obj;
            if(mContainerService == null) {
                Slog.e("PackageManager", "Cannot bind to media container service");
                for(Iterator iterator3 = mPendingInstalls.iterator(); iterator3.hasNext(); ((HandlerParams)iterator3.next()).serviceError());
                mPendingInstalls.clear();
            } else
            if(mPendingInstalls.size() > 0) {
                HandlerParams handlerparams = (HandlerParams)mPendingInstalls.get(0);
                if(handlerparams != null && handlerparams.startCopy()) {
                    if(mPendingInstalls.size() > 0)
                        mPendingInstalls.remove(0);
                    if(mPendingInstalls.size() == 0) {
                        if(mBound) {
                            removeMessages(6);
                            sendMessageDelayed(obtainMessage(6), 10000L);
                        }
                    } else {
                        mHandler.sendEmptyMessage(3);
                    }
                }
            } else {
                Slog.w("PackageManager", "Empty queue");
            }
              goto _L1
_L8:
            if(mPendingInstalls.size() > 0) {
                if(mBound)
                    disconnectService();
                if(!connectToService()) {
                    Slog.e("PackageManager", "Failed to bind to media container service");
                    for(Iterator iterator2 = mPendingInstalls.iterator(); iterator2.hasNext(); ((HandlerParams)iterator2.next()).serviceError());
                    mPendingInstalls.clear();
                }
            }
              goto _L1
_L5:
            if(mPendingInstalls.size() == 0 && mPendingVerification.size() == 0) {
                if(mBound)
                    disconnectService();
            } else
            if(mPendingInstalls.size() > 0)
                mHandler.sendEmptyMessage(3);
              goto _L1
_L9:
            mPendingInstalls.remove(0);
              goto _L1
_L2:
            Process.setThreadPriority(0);
            HashMap hashmap3 = mPackages;
            hashmap3;
            JVM INSTR monitorenter ;
            if(mPendingBroadcasts != null) goto _L15; else goto _L1
_L15:
            break MISSING_BLOCK_LABEL_579;
            Exception exception4;
            exception4;
            throw exception4;
            int j1 = mPendingBroadcasts.size();
            if(j1 > 0) goto _L17; else goto _L16
_L16:
            hashmap3;
            JVM INSTR monitorexit ;
              goto _L1
_L17:
            String as[];
            ArrayList aarraylist[];
            int ai[];
            Iterator iterator1;
            int k1;
            as = new String[j1];
            aarraylist = new ArrayList[j1];
            ai = new int[j1];
            iterator1 = mPendingBroadcasts.entrySet().iterator();
            k1 = 0;
_L19:
            int j2;
            if(!iterator1.hasNext() || k1 >= j1)
                break; /* Loop/switch isn't completed */
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            as[k1] = (String)entry.getKey();
            aarraylist[k1] = (ArrayList)entry.getValue();
            PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(entry.getKey());
            if(packagesetting == null)
                break MISSING_BLOCK_LABEL_1804;
            j2 = packagesetting.appId;
_L23:
            ai[k1] = j2;
            k1++;
            if(true) goto _L19; else goto _L18
_L18:
            int l1;
            l1 = k1;
            mPendingBroadcasts.clear();
            hashmap3;
            JVM INSTR monitorexit ;
            for(int i2 = 0; i2 < l1; i2++)
                sendPackageChangedBroadcast(as[i2], true, aarraylist[i2], ai[i2]);

            Process.setThreadPriority(10);
              goto _L1
_L6:
            String s = (String)message.obj;
            Process.setThreadPriority(0);
            synchronized(mPackages) {
                if(!mSettings.mPackagesToBeCleaned.contains(s))
                    mSettings.mPackagesToBeCleaned.add(s);
            }
            Process.setThreadPriority(10);
            startCleaningPackages();
              goto _L1
            exception3;
            hashmap2;
            JVM INSTR monitorexit ;
            throw exception3;
_L7:
            PostInstallData postinstalldata = (PostInstallData)mRunningInstalls.get(message.arg1);
            mRunningInstalls.delete(message.arg1);
            boolean flag2 = false;
            if(postinstalldata != null) {
                InstallArgs installargs2 = postinstalldata.args;
                PackageInstalledInfo packageinstalledinfo = postinstalldata.res;
                if(packageinstalledinfo.returnCode == 1) {
                    packageinstalledinfo.removedInfo.sendBroadcast(false, true);
                    Bundle bundle = new Bundle(1);
                    bundle.putInt("android.intent.extra.UID", packageinstalledinfo.uid);
                    RemoteException remoteexception2;
                    boolean flag3;
                    if(packageinstalledinfo.removedInfo.removedPackage != null)
                        flag3 = true;
                    else
                        flag3 = false;
                    if(flag3)
                        bundle.putBoolean("android.intent.extra.REPLACING", true);
                    PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", packageinstalledinfo.pkg.applicationInfo.packageName, bundle, null, null, -1);
                    if(flag3) {
                        PackageManagerService.sendPackageBroadcast("android.intent.action.PACKAGE_REPLACED", packageinstalledinfo.pkg.applicationInfo.packageName, bundle, null, null, -1);
                        PackageManagerService.sendPackageBroadcast("android.intent.action.MY_PACKAGE_REPLACED", null, null, packageinstalledinfo.pkg.applicationInfo.packageName, null, -1);
                    }
                    if(packageinstalledinfo.removedInfo.args != null)
                        flag2 = true;
                }
                Runtime.getRuntime().gc();
                if(flag2)
                    synchronized(mInstallLock) {
                        packageinstalledinfo.removedInfo.args.doPostDeleteLI(true);
                    }
                if(installargs2.observer != null)
                    try {
                        installargs2.observer.packageInstalled(packageinstalledinfo.name, packageinstalledinfo.returnCode);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RemoteException remoteexception2) {
                        Slog.i("PackageManager", "Observer no longer exists.");
                    }
            } else {
                Slog.e("PackageManager", (new StringBuilder()).append("Bogus post-install token ").append(message.arg1).toString());
            }
              goto _L1
            exception2;
            obj;
            JVM INSTR monitorexit ;
            throw exception2;
_L10:
            boolean flag;
            boolean flag1;
            if(message.arg1 == 1)
                flag = true;
            else
                flag = false;
            if(message.arg2 == 1)
                flag1 = true;
            else
                flag1 = false;
            if(flag1)
                Runtime.getRuntime().gc();
            if(message.obj != null) {
                Set set = (Set)message.obj;
                unloadAllContainers(set);
            }
            if(flag)
                try {
                    PackageHelper.getMountService().finishMediaUpdate();
                }
                catch(RemoteException remoteexception1) {
                    Log.e("PackageManager", "MountService not running?");
                }
              goto _L1
_L11:
            Process.setThreadPriority(0);
            synchronized(mPackages) {
                removeMessages(13);
                removeMessages(14);
                mSettings.writeLPr();
                mDirtyUsers.clear();
            }
            Process.setThreadPriority(10);
              goto _L1
            exception1;
            hashmap1;
            JVM INSTR monitorexit ;
            throw exception1;
_L12:
            Process.setThreadPriority(0);
            HashMap hashmap = mPackages;
            hashmap;
            JVM INSTR monitorenter ;
            removeMessages(14);
            int i1;
            for(Iterator iterator = mDirtyUsers.iterator(); iterator.hasNext(); mSettings.writePackageRestrictionsLPr(i1))
                i1 = ((Integer)iterator.next()).intValue();

            break MISSING_BLOCK_LABEL_1491;
            Exception exception;
            exception;
            throw exception;
            mDirtyUsers.clear();
            hashmap;
            JVM INSTR monitorexit ;
            Process.setThreadPriority(10);
              goto _L1
_L14:
            int l = message.arg1;
            PackageVerificationState packageverificationstate1 = (PackageVerificationState)mPendingVerification.get(l);
            if(packageverificationstate1 != null) {
                InstallArgs installargs1 = packageverificationstate1.getInstallArgs();
                Slog.i("PackageManager", (new StringBuilder()).append("Verification timed out for ").append(installargs1.packageURI.toString()).toString());
                mPendingVerification.remove(l);
                processPendingInstall(installargs1, -21);
                mHandler.sendEmptyMessage(6);
            }
              goto _L1
_L13:
            int i;
            PackageVerificationState packageverificationstate;
label0:
            {
                i = message.arg1;
                packageverificationstate = (PackageVerificationState)mPendingVerification.get(i);
                if(packageverificationstate != null)
                    break label0;
                Slog.w("PackageManager", (new StringBuilder()).append("Invalid verification token ").append(i).append(" received").toString());
            }
            if(true) goto _L1; else goto _L20
_L20:
            PackageVerificationResponse packageverificationresponse = (PackageVerificationResponse)message.obj;
            packageverificationstate.setVerifierResponse(packageverificationresponse.callerUid, packageverificationresponse.code);
            if(!packageverificationstate.isVerificationComplete()) goto _L1; else goto _L21
_L21:
            InstallArgs installargs;
            int j;
            mPendingVerification.remove(i);
            installargs = packageverificationstate.getInstallArgs();
            if(!packageverificationstate.isInstallAllowed())
                break MISSING_BLOCK_LABEL_1797;
            j = -110;
            int k = installargs.copyApk(mContainerService, true);
            j = k;
_L22:
            processPendingInstall(installargs, j);
            mHandler.sendEmptyMessage(6);
              goto _L1
            RemoteException remoteexception;
            remoteexception;
            Slog.e("PackageManager", "Could not contact the ContainerService");
              goto _L22
            j = -22;
              goto _L22
            j2 = -1;
              goto _L23
        }

        public void handleMessage(Message message) {
            Injector.doHandleMessage(PackageManagerService.this, this, message);
            Process.setThreadPriority(10);
            return;
            Exception exception;
            exception;
            Process.setThreadPriority(10);
            throw exception;
        }

        private boolean mBound;
        final ArrayList mPendingInstalls = new ArrayList();
        final PackageManagerService this$0;

        PackageHandler(Looper looper) {
            this$0 = PackageManagerService.this;
            super(looper);
            mBound = false;
        }
    }

    class PostInstallData {

        public InstallArgs args;
        public PackageInstalledInfo res;
        final PackageManagerService this$0;

        PostInstallData(InstallArgs installargs, PackageInstalledInfo packageinstalledinfo) {
            this$0 = PackageManagerService.this;
            super();
            args = installargs;
            res = packageinstalledinfo;
        }
    }

    class DefaultContainerConnection
        implements ServiceConnection {

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            IMediaContainerService imediacontainerservice = com.android.internal.app.IMediaContainerService.Stub.asInterface(ibinder);
            mHandler.sendMessage(mHandler.obtainMessage(3, imediacontainerservice));
        }

        public void onServiceDisconnected(ComponentName componentname) {
        }

        final PackageManagerService this$0;

        DefaultContainerConnection() {
            this$0 = PackageManagerService.this;
            super();
        }
    }

    static class Injector {

        static void addMiuiSharedUids(PackageManagerService packagemanagerservice) {
            MiuiSharedUids.add(packagemanagerservice.mSettings, true);
        }

        static boolean addPackageToSlice(ParceledListSlice parceledlistslice, PackageInfo packageinfo, int i) {
            boolean flag;
            if((0x20000 & i) != 0)
                if(packageinfo.activities != null && packageinfo.activities.length > 0)
                    packageinfo.activities = null;
                else
                    packageinfo = null;
            if((0x40000 & i) != 0)
                if(packageinfo.activities != null && packageinfo.activities.length > 0 || packageinfo.services != null && packageinfo.services.length > 0) {
                    packageinfo.activities = null;
                    packageinfo.services = null;
                } else {
                    packageinfo = null;
                }
            if(packageinfo != null)
                flag = parceledlistslice.append(packageinfo);
            else
                flag = false;
            return flag;
        }

        static boolean checkApk(PackageManagerService packagemanagerservice, Message message) {
label0:
            {
                {
                    HandlerParams handlerparams = (HandlerParams)message.obj;
                    if(!(handlerparams instanceof InstallParams))
                        break label0;
                    InstallParams installparams = (InstallParams)handlerparams;
                    if(ExtraGuard.checkApk(packagemanagerservice.mContext, installparams.getPackageUri()))
                        break label0;
                    boolean flag;
                    if(installparams.observer != null)
                        try {
                            installparams.observer.packageInstalled(null, -22);
                        }
                        catch(RemoteException remoteexception) { }
                    flag = false;
                }
                return flag;
            }
            flag = true;
            if(false)
                ;
            else
                break MISSING_BLOCK_LABEL_59;
        }

        static void doHandleMessage(PackageManagerService packagemanagerservice, PackageHandler packagehandler, Message message) {
            if(message.what != 5 || checkApk(packagemanagerservice, message))
                packagehandler.doHandleMessage(message);
        }

        static void ignoreMiuiFrameworkRes(PackageManagerService packagemanagerservice, HashSet hashset) {
            hashset.add((new StringBuilder()).append(packagemanagerservice.mFrameworkDir.getPath()).append("/framework-miui-res.apk").toString());
        }

        static boolean setAccessControl(PackageManagerService packagemanagerservice, String s, int i, int j) {
            HashMap hashmap = packagemanagerservice.mPackages;
            Settings settings = packagemanagerservice.mSettings;
            hashmap;
            JVM INSTR monitorenter ;
            if(i == 0x80000000) goto _L2; else goto _L1
_L1:
            boolean flag = false;
              goto _L3
_L2:
            android.content.pm.PackageParser.Package package1;
            PackageSetting packagesetting;
            package1 = (android.content.pm.PackageParser.Package)hashmap.get(s);
            packagesetting = (PackageSetting)settings.mPackages.get(s);
            if(package1 == null || packagesetting == null) goto _L5; else goto _L4
_L4:
            if(j != 0x80000000) goto _L7; else goto _L6
_L6:
            packagesetting.pkgFlags = 0x80000000 | ((GrantedPermissions) (packagesetting)).pkgFlags;
            ApplicationInfo applicationinfo1 = package1.applicationInfo;
            applicationinfo1.flags = 0x80000000 | applicationinfo1.flags;
_L8:
            settings.writeLPr();
_L5:
            flag = true;
            break; /* Loop/switch isn't completed */
            Exception exception;
            exception;
            throw exception;
_L7:
            packagesetting.pkgFlags = 0x7fffffff & ((GrantedPermissions) (packagesetting)).pkgFlags;
            ApplicationInfo applicationinfo = package1.applicationInfo;
            applicationinfo.flags = 0x7fffffff & applicationinfo.flags;
            if(true) goto _L8; else goto _L3
_L3:
            return flag;
        }

        Injector() {
        }
    }


    public PackageManagerService(Context context, boolean flag, boolean flag1) {
        Object obj;
        HashMap hashmap;
        File file;
        long l;
        int i;
        HashSet hashset;
        boolean flag2;
        String as2[];
        int k2;
        mHandlerThread = new HandlerThread("PackageManager", 10);
        mSdkVersion = android.os.Build.VERSION.SDK_INT;
        String s;
        String s1;
        String s2;
        int l2;
        if("REL".equals(android.os.Build.VERSION.CODENAME))
            s = null;
        else
            s = android.os.Build.VERSION.CODENAME;
        mSdkCodename = s;
        mInstallLock = new Object();
        mAppDirs = new HashMap();
        mOutPermissions = new int[3];
        mPackages = new HashMap();
        mSystemPermissions = new SparseArray();
        mSharedLibraries = new HashMap();
        mTmpSharedLibraries = null;
        mAvailableFeatures = new HashMap();
        mActivities = new ActivityIntentResolver();
        mReceivers = new ActivityIntentResolver();
        mServices = new ServiceIntentResolver();
        mProvidersByComponent = new HashMap();
        mProviders = new HashMap();
        mInstrumentation = new HashMap();
        mPermissionGroups = new HashMap();
        mTransferedPackages = new HashSet();
        mProtectedBroadcasts = new HashSet();
        mPendingVerification = new SparseArray();
        mDeferredDexOpt = new ArrayList();
        mPendingVerificationToken = 0;
        mResolveActivity = new ActivityInfo();
        mResolveInfo = new ResolveInfo();
        mPendingBroadcasts = new HashMap();
        mContainerService = null;
        mDirtyUsers = new HashSet();
        mDefContainerConn = new DefaultContainerConnection();
        mRunningInstalls = new SparseArray();
        mNextInstallToken = 1;
        mMediaMounted = false;
        EventLog.writeEvent(3060, SystemClock.uptimeMillis());
        if(mSdkVersion <= 0)
            Slog.w("PackageManager", "**** ro.build.version.sdk not set!");
        mContext = context;
        mFactoryTest = flag;
        mOnlyCore = flag1;
        mNoDexOpt = "eng".equals(SystemProperties.get("ro.build.type"));
        mMetrics = new DisplayMetrics();
        mSettings = new Settings();
        mSettings.addSharedUserLPw("android.uid.system", 1000, 1);
        mSettings.addSharedUserLPw("android.uid.phone", 1001, 1);
        mSettings.addSharedUserLPw("android.uid.log", 1007, 1);
        mSettings.addSharedUserLPw("android.uid.nfc", 1027, 1);
        Injector.addMiuiSharedUids(this);
        s1 = SystemProperties.get("debug.separate_processes");
        if(s1 != null && s1.length() > 0) {
            if("*".equals(s1)) {
                mDefParseFlags = 8;
                mSeparateProcesses = null;
                Slog.w("PackageManager", "Running with debug.separate_processes: * (ALL)");
            } else {
                mDefParseFlags = 0;
                mSeparateProcesses = s1.split(",");
                Slog.w("PackageManager", (new StringBuilder()).append("Running with debug.separate_processes: ").append(s1).toString());
            }
        } else {
            mDefParseFlags = 0;
            mSeparateProcesses = null;
        }
        mInstaller = new Installer();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(mMetrics);
        obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        mHandlerThread.start();
        mHandler = new PackageHandler(mHandlerThread.getLooper());
        file = Environment.getDataDirectory();
        mAppDataDir = new File(file, "data");
        mAsecInternalPath = (new File(file, "app-asec")).getPath();
        mUserAppDataDir = new File(file, "user");
        mDrmAppPrivateInstallDir = new File(file, "app-private");
        sUserManager = new UserManager(mInstaller, mUserAppDataDir);
        readPermissions();
        mRestoredSettings = mSettings.readLPw(getUsers());
        l = SystemClock.uptimeMillis();
        EventLog.writeEvent(3070, l);
        i = 417;
        if(mNoDexOpt) {
            Slog.w("PackageManager", "Running ENG build: no pre-dexopt!");
            i |= 2;
        }
        hashset = new HashSet();
        mFrameworkDir = new File(Environment.getRootDirectory(), "framework");
        mDalvikCacheDir = new File(file, "dalvik-cache");
        flag2 = false;
        s2 = System.getProperty("java.boot.class.path");
        if(s2 == null)
            break MISSING_BLOCK_LABEL_1059;
        as2 = splitString(s2, ':');
        k2 = 0;
_L1:
        l2 = as2.length;
        if(k2 >= l2)
            break MISSING_BLOCK_LABEL_1068;
        if(!DexFile.isDexOptNeeded(as2[k2]))
            break MISSING_BLOCK_LABEL_885;
        hashset.add(as2[k2]);
        mInstaller.dexopt(as2[k2], 1000, true);
        flag2 = true;
_L2:
        k2++;
          goto _L1
        FileNotFoundException filenotfoundexception2;
        filenotfoundexception2;
        Slog.w("PackageManager", (new StringBuilder()).append("Boot class path not found: ").append(as2[k2]).toString());
          goto _L2
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
        IOException ioexception2;
        ioexception2;
        Slog.w("PackageManager", (new StringBuilder()).append("Cannot dexopt ").append(as2[k2]).append("; is it an APK or JAR? ").append(ioexception2.getMessage()).toString());
          goto _L2
        Slog.w("PackageManager", "No BOOTCLASSPATH found!");
        Iterator iterator3;
        if(mSharedLibraries.size() <= 0)
            break MISSING_BLOCK_LABEL_1228;
        iterator3 = mSharedLibraries.values().iterator();
_L4:
        String s7;
        if(!iterator3.hasNext())
            break MISSING_BLOCK_LABEL_1228;
        s7 = (String)iterator3.next();
        if(!DexFile.isDexOptNeeded(s7)) goto _L4; else goto _L3
_L3:
        hashset.add(s7);
        mInstaller.dexopt(s7, 1000, true);
        flag2 = true;
          goto _L4
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
        Slog.w("PackageManager", (new StringBuilder()).append("Library not found: ").append(s7).toString());
          goto _L4
        IOException ioexception1;
        ioexception1;
        Slog.w("PackageManager", (new StringBuilder()).append("Cannot dexopt ").append(s7).append("; is it an APK or JAR? ").append(ioexception1.getMessage()).toString());
          goto _L4
        String as[];
        hashset.add((new StringBuilder()).append(mFrameworkDir.getPath()).append("/framework-res.apk").toString());
        Injector.ignoreMiuiFrameworkRes(this, hashset);
        as = mFrameworkDir.list();
        if(as == null) goto _L6; else goto _L5
_L5:
        int i2 = 0;
_L25:
        int j2 = as.length;
        if(i2 >= j2) goto _L6; else goto _L7
_L7:
        String s6;
        File file1 = new File(mFrameworkDir, as[i2]);
        s6 = file1.getPath();
        if(!hashset.contains(s6)) goto _L9; else goto _L8
_L9:
        if(s6.endsWith(".apk")) goto _L11; else goto _L10
_L10:
        boolean flag4 = s6.endsWith(".jar");
        if(!flag4) goto _L8; else goto _L11
_L11:
        if(!DexFile.isDexOptNeeded(s6)) goto _L8; else goto _L12
_L12:
        mInstaller.dexopt(s6, 1000, true);
        flag2 = true;
          goto _L8
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w("PackageManager", (new StringBuilder()).append("Jar not found: ").append(s6).toString());
          goto _L8
        IOException ioexception;
        ioexception;
        Slog.w("PackageManager", (new StringBuilder()).append("Exception reading jar: ").append(s6).toString(), ioexception);
          goto _L8
_L6:
        if(!flag2) goto _L14; else goto _L13
_L13:
        String as1[] = mDalvikCacheDir.list();
        if(as1 == null) goto _L14; else goto _L15
_L15:
        int k1 = 0;
_L26:
        int l1 = as1.length;
        if(k1 >= l1) goto _L14; else goto _L16
_L16:
        String s5 = as1[k1];
        if(s5.startsWith("data@app@") || s5.startsWith("data@app-private@")) {
            Slog.i("PackageManager", (new StringBuilder()).append("Pruning dalvik file: ").append(s5).toString());
            (new File(mDalvikCacheDir, s5)).delete();
        }
          goto _L17
_L14:
        mFrameworkInstallObserver = new AppDirObserver(mFrameworkDir.getPath(), 712, true);
        mFrameworkInstallObserver.startWatching();
        scanDirLI(mFrameworkDir, 65, i | 2, 0L);
        mSystemAppDir = new File(Environment.getRootDirectory(), "app");
        mSystemInstallObserver = new AppDirObserver(mSystemAppDir.getPath(), 712, true);
        mSystemInstallObserver.startWatching();
        scanDirLI(mSystemAppDir, 65, i, 0L);
        mVendorAppDir = new File("/vendor/app");
        mVendorInstallObserver = new AppDirObserver(mVendorAppDir.getPath(), 712, true);
        mVendorInstallObserver.startWatching();
        scanDirLI(mVendorAppDir, 65, i, 0L);
        mInstaller.moveFiles();
        ArrayList arraylist = new ArrayList();
        if(!mOnlyCore) {
            Iterator iterator2 = mSettings.mPackages.values().iterator();
            do {
                if(!iterator2.hasNext())
                    break;
                PackageSetting packagesetting1 = (PackageSetting)iterator2.next();
                if((1 & ((GrantedPermissions) (packagesetting1)).pkgFlags) != 0) {
                    android.content.pm.PackageParser.Package package2 = (android.content.pm.PackageParser.Package)mPackages.get(((PackageSettingBase) (packagesetting1)).name);
                    if(package2 != null) {
                        if(mSettings.isDisabledSystemPackageLPr(((PackageSettingBase) (packagesetting1)).name)) {
                            Slog.i("PackageManager", (new StringBuilder()).append("Expecting better updatd system app for ").append(((PackageSettingBase) (packagesetting1)).name).append("; removing system app").toString());
                            removePackageLI(package2, true);
                        }
                    } else
                    if(!mSettings.isDisabledSystemPackageLPr(((PackageSettingBase) (packagesetting1)).name)) {
                        iterator2.remove();
                        reportSettingsProblem(5, (new StringBuilder()).append("System package ").append(((PackageSettingBase) (packagesetting1)).name).append(" no longer exists; wiping its data").toString());
                        mInstaller.remove(((PackageSettingBase) (packagesetting1)).name, 0);
                        sUserManager.removePackageForAllUsers(((PackageSettingBase) (packagesetting1)).name);
                    } else {
                        PackageSetting packagesetting2 = mSettings.getDisabledSystemPkgLPr(((PackageSettingBase) (packagesetting1)).name);
                        if(((PackageSettingBase) (packagesetting2)).codePath == null || !((PackageSettingBase) (packagesetting2)).codePath.exists())
                            arraylist.add(((PackageSettingBase) (packagesetting1)).name);
                    }
                }
            } while(true);
        }
        mAppInstallDir = new File(file, "app");
        ArrayList arraylist1 = mSettings.getListOfIncompleteInstallPackagesLPr();
        int j = 0;
        do {
            int k = arraylist1.size();
            if(j >= k)
                break;
            cleanupInstallFailedPackage((PackageSetting)arraylist1.get(j));
            j++;
        } while(true);
        deleteTempPackageFiles();
        ExtraPackageManagerServices.performPreinstallApp(mSettings);
        if(!mOnlyCore) {
            EventLog.writeEvent(3080, SystemClock.uptimeMillis());
            mAppInstallObserver = new AppDirObserver(mAppInstallDir.getPath(), 712, false);
            mAppInstallObserver.startWatching();
            scanDirLI(mAppInstallDir, 0, i, 0L);
            mDrmAppInstallObserver = new AppDirObserver(mDrmAppPrivateInstallDir.getPath(), 712, false);
            mDrmAppInstallObserver.startWatching();
            scanDirLI(mDrmAppPrivateInstallDir, 16, i, 0L);
            Iterator iterator1 = arraylist.iterator();
            while(iterator1.hasNext())  {
                String s3 = (String)iterator1.next();
                android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s3);
                mSettings.removeDisabledSystemPackageLPw(s3);
                String s4;
                if(package1 == null) {
                    s4 = (new StringBuilder()).append("Updated system package ").append(s3).append(" no longer exists; wiping its data").toString();
                    mInstaller.remove(s3, 0);
                    sUserManager.removePackageForAllUsers(s3);
                } else {
                    s4 = (new StringBuilder()).append("Updated system app + ").append(s3).append(" no longer present; removing system privileges for ").append(s3).toString();
                    ApplicationInfo applicationinfo = package1.applicationInfo;
                    applicationinfo.flags = -2 & applicationinfo.flags;
                    PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s3);
                    packagesetting.pkgFlags = -2 & ((GrantedPermissions) (packagesetting)).pkgFlags;
                }
                reportSettingsProblem(5, s4);
            }
        } else {
            mAppInstallObserver = null;
            mDrmAppInstallObserver = null;
        }
        EventLog.writeEvent(3090, SystemClock.uptimeMillis());
        Slog.i("PackageManager", (new StringBuilder()).append("Time to scan packages: ").append((float)(SystemClock.uptimeMillis() - l) / 1000F).append(" seconds").toString());
        if(mSettings.mInternalSdkPlatform == mSdkVersion) goto _L19; else goto _L18
_L18:
        boolean flag3 = true;
_L27:
        if(flag3)
            Slog.i("PackageManager", (new StringBuilder()).append("Platform changed from ").append(mSettings.mInternalSdkPlatform).append(" to ").append(mSdkVersion).append("; regranting permissions for internal storage").toString());
        mSettings.mInternalSdkPlatform = mSdkVersion;
        if(!flag3) goto _L21; else goto _L20
_L20:
        byte byte0 = 6;
_L28:
        ArrayList arraylist2;
        updatePermissionsLPw(null, null, byte0 | 1);
        arraylist2 = new ArrayList();
        Iterator iterator = mSettings.mPreferredActivities.filterSet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            PreferredActivity preferredactivity1 = (PreferredActivity)iterator.next();
            if(mActivities.mActivities.get(preferredactivity1.mPref.mComponent) == null)
                arraylist2.add(preferredactivity1);
        } while(true);
        break MISSING_BLOCK_LABEL_2776;
_L24:
        int j1 = arraylist2.size();
        int i1;
        if(i1 >= j1) goto _L23; else goto _L22
_L22:
        PreferredActivity preferredactivity = (PreferredActivity)arraylist2.get(i1);
        Slog.w("PackageManager", (new StringBuilder()).append("Removing dangling preferred activity: ").append(preferredactivity.mPref.mComponent).toString());
        mSettings.mPreferredActivities.removeFilter(preferredactivity);
        i1++;
          goto _L24
_L23:
        mSettings.writeLPr();
        EventLog.writeEvent(3100, SystemClock.uptimeMillis());
        Runtime.getRuntime().gc();
        mRequiredVerifierPackage = getRequiredVerifierLPr();
        hashmap;
        JVM INSTR monitorexit ;
        obj;
        JVM INSTR monitorexit ;
        return;
_L8:
        i2++;
          goto _L25
_L17:
        k1++;
          goto _L26
_L19:
        flag3 = false;
          goto _L27
_L21:
        byte0 = 0;
          goto _L28
        i1 = 0;
          goto _L24
    }

    static int[] appendInts(int ai[], int ai1[]) {
        if(ai1 != null) goto _L2; else goto _L1
_L1:
        return ai;
_L2:
        if(ai != null)
            break; /* Loop/switch isn't completed */
        ai = ai1;
        if(true) goto _L1; else goto _L3
_L3:
        int i = ai1.length;
        int j = 0;
        while(j < i)  {
            ai = ArrayUtils.appendInt(ai, ai1[j]);
            j++;
        }
        if(true) goto _L1; else goto _L4
_L4:
    }

    static String arrayToString(int ai[]) {
        StringBuffer stringbuffer = new StringBuffer(128);
        stringbuffer.append('[');
        if(ai != null) {
            for(int i = 0; i < ai.length; i++) {
                if(i > 0)
                    stringbuffer.append(", ");
                stringbuffer.append(ai[i]);
            }

        }
        stringbuffer.append(']');
        return stringbuffer.toString();
    }

    private BasePermission checkPermissionTreeLP(String s) {
        if(s != null) {
            BasePermission basepermission = findPermissionTreeLP(s);
            if(basepermission != null)
                if(basepermission.uid == UserId.getAppId(Binder.getCallingUid()))
                    return basepermission;
                else
                    throw new SecurityException((new StringBuilder()).append("Calling uid ").append(Binder.getCallingUid()).append(" is not allowed to add to permission tree ").append(basepermission.name).append(" owned by uid ").append(basepermission.uid).toString());
        }
        throw new SecurityException((new StringBuilder()).append("No permission tree found for ").append(s).toString());
    }

    private void checkValidCaller(int i, int j) {
        if(UserId.getUserId(i) == j || i == 1000 || i == 0)
            return;
        else
            throw new SecurityException((new StringBuilder()).append("Caller uid=").append(i).append(" is not privileged to communicate with user=").append(j).toString());
    }

    private ResolveInfo chooseBestActivity(Intent intent, String s, int i, List list, int j) {
        if(list == null) goto _L2; else goto _L1
_L1:
        int k = list.size();
        if(k != 1) goto _L4; else goto _L3
_L3:
        ResolveInfo resolveinfo = (ResolveInfo)list.get(0);
_L6:
        return resolveinfo;
_L4:
        if(k > 1) {
            ResolveInfo resolveinfo1 = (ResolveInfo)list.get(0);
            ResolveInfo resolveinfo2 = (ResolveInfo)list.get(1);
            if(resolveinfo1.priority != resolveinfo2.priority || resolveinfo1.preferredOrder != resolveinfo2.preferredOrder || resolveinfo1.isDefault != resolveinfo2.isDefault) {
                resolveinfo = (ResolveInfo)list.get(0);
            } else {
                ResolveInfo resolveinfo3 = findPreferredActivity(intent, s, i, list, resolveinfo1.priority, j);
                if(resolveinfo3 != null)
                    resolveinfo = resolveinfo3;
                else
                    resolveinfo = mResolveInfo;
            }
            continue; /* Loop/switch isn't completed */
        }
_L2:
        resolveinfo = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    static String cidFromCodePath(String s) {
        int i = s.lastIndexOf("/");
        String s1 = s.substring(0, i);
        return s1.substring(1 + s1.lastIndexOf("/"), i);
    }

    private void cleanUpUser(int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        for(Iterator iterator = mSettings.mPackages.entrySet().iterator(); iterator.hasNext(); ((PackageSetting)((java.util.Map.Entry)iterator.next()).getValue()).removeUser(i));
        break MISSING_BLOCK_LABEL_64;
        Exception exception;
        exception;
        throw exception;
        if(!mDirtyUsers.remove(Integer.valueOf(i)));
        mSettings.removeUserLPr(i);
        hashmap;
        JVM INSTR monitorexit ;
    }

    private boolean clearApplicationUserDataLI(String s, int i) {
        boolean flag = false;
        if(s != null) goto _L2; else goto _L1
_L1:
        Slog.w("PackageManager", "Attempt to delete null packageName.");
_L4:
        return flag;
_L2:
        boolean flag1 = false;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        PackageSetting packagesetting;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 != null)
            break MISSING_BLOCK_LABEL_132;
        flag1 = true;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting == null || packagesetting.pkg == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_125;
        Exception exception;
        exception;
        throw exception;
        package1 = packagesetting.pkg;
        hashmap;
        JVM INSTR monitorexit ;
        if(!flag1) {
            if(package1 == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
                continue; /* Loop/switch isn't completed */
            }
            if(package1.applicationInfo == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(s).append(" has no applicationInfo.").toString());
                continue; /* Loop/switch isn't completed */
            }
        }
        if(mInstaller.clearUserData(s, i) < 0)
            Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove cache files for package: ").append(s).toString());
        else
            flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void clearExternalStorageDataSync(String s, boolean flag) {
        boolean flag1;
        if(Environment.isExternalStorageEmulated()) {
            flag1 = true;
        } else {
            String s1 = Environment.getExternalStorageState();
            if(s1.equals("mounted") || s1.equals("mounted_ro"))
                flag1 = true;
            else
                flag1 = false;
        }
        if(flag1) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ClearStorageConnection clearstorageconnection;
        Intent intent = (new Intent()).setComponent(DEFAULT_CONTAINER_COMPONENT);
        clearstorageconnection = new ClearStorageConnection();
        if(!mContext.bindService(intent, clearstorageconnection, 1))
            continue; /* Loop/switch isn't completed */
        long l = 5000L + SystemClock.uptimeMillis();
        clearstorageconnection;
        JVM INSTR monitorenter ;
        long l1 = SystemClock.uptimeMillis();
_L3:
        IMediaContainerService imediacontainerservice = clearstorageconnection.mContainerService;
        Exception exception1;
        Context context;
label0:
        {
            if(imediacontainerservice != null || l1 >= l)
                break label0;
            long l2 = l - l1;
            IMediaContainerService imediacontainerservice1;
            try {
                clearstorageconnection.wait(l2);
            }
            catch(InterruptedException interruptedexception) { }
            finally { }
        }
          goto _L3
        clearstorageconnection;
        JVM INSTR monitorexit ;
        imediacontainerservice1 = clearstorageconnection.mContainerService;
        if(imediacontainerservice1 != null)
            break; /* Loop/switch isn't completed */
        context = mContext;
_L5:
        context.unbindService(clearstorageconnection);
        if(true) goto _L1; else goto _L4
        clearstorageconnection;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        mContext.unbindService(clearstorageconnection);
        throw exception;
_L4:
        File file = Environment.getExternalStorageAppCacheDirectory(s);
        File file1;
        RemoteException remoteexception1;
        File file2;
        RemoteException remoteexception2;
        try {
            clearstorageconnection.mContainerService.clearDirectory(file.toString());
        }
        catch(RemoteException remoteexception) { }
        if(!flag)
            break MISSING_BLOCK_LABEL_271;
        file1 = Environment.getExternalStorageAppDataDirectory(s);
        try {
            clearstorageconnection.mContainerService.clearDirectory(file1.toString());
        }
        // Misplaced declaration of an exception variable
        catch(RemoteException remoteexception1) { }
        file2 = Environment.getExternalStorageAppMediaDirectory(s);
        try {
            clearstorageconnection.mContainerService.clearDirectory(file2.toString());
        }
        // Misplaced declaration of an exception variable
        catch(RemoteException remoteexception2) { }
        context = mContext;
          goto _L5
    }

    private boolean collectCertificatesLI(PackageParser packageparser, PackageSetting packagesetting, android.content.pm.PackageParser.Package package1, File file, int i) {
        boolean flag = true;
        if(packagesetting == null || !((PackageSettingBase) (packagesetting)).codePath.equals(file) || ((PackageSettingBase) (packagesetting)).timeStamp != file.lastModified()) goto _L2; else goto _L1
_L1:
        if(((PackageSettingBase) (packagesetting)).signatures.mSignatures == null || ((PackageSettingBase) (packagesetting)).signatures.mSignatures.length == 0) goto _L4; else goto _L3
_L3:
        package1.mSignatures = ((PackageSettingBase) (packagesetting)).signatures.mSignatures;
_L5:
        return flag;
_L4:
        Slog.w("PackageManager", (new StringBuilder()).append("PackageSetting for ").append(((PackageSettingBase) (packagesetting)).name).append(" is missing signatures.  Collecting certs again to recover them.").toString());
_L6:
        if(!packageparser.collectCertificates(package1, i)) {
            mLastScanError = packageparser.getParseError();
            flag = false;
        }
        if(true) goto _L5; else goto _L2
_L2:
        Log.i("PackageManager", (new StringBuilder()).append(file.toString()).append(" changed; collecting certs").toString());
          goto _L6
    }

    static boolean comparePermissionInfos(PermissionInfo permissioninfo, PermissionInfo permissioninfo1) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if(permissioninfo.icon == permissioninfo1.icon && permissioninfo.logo == permissioninfo1.logo && permissioninfo.protectionLevel == permissioninfo1.protectionLevel && compareStrings(permissioninfo.name, permissioninfo1.name) && compareStrings(permissioninfo.nonLocalizedLabel, permissioninfo1.nonLocalizedLabel) && compareStrings(permissioninfo.packageName, permissioninfo1.packageName))
            flag = true;
        return flag;
    }

    static int compareSignatures(Signature asignature[], Signature asignature1[]) {
        int i1;
        if(asignature == null) {
            if(asignature1 == null)
                i1 = 1;
            else
                i1 = -1;
        } else
        if(asignature1 == null) {
            i1 = -2;
        } else {
            HashSet hashset = new HashSet();
            int i = asignature.length;
            for(int j = 0; j < i; j++)
                hashset.add(asignature[j]);

            HashSet hashset1 = new HashSet();
            int k = asignature1.length;
            for(int l = 0; l < k; l++)
                hashset1.add(asignature1[l]);

            if(hashset.equals(hashset1))
                i1 = 0;
            else
                i1 = -3;
        }
        return i1;
    }

    static boolean compareStrings(CharSequence charsequence, CharSequence charsequence1) {
        boolean flag = false;
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        if(charsequence1 == null)
            flag = true;
_L4:
        return flag;
_L2:
        if(charsequence1 != null && charsequence.getClass() == charsequence1.getClass())
            flag = charsequence.equals(charsequence1);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private InstallArgs createInstallArgs(int i, String s, String s1, String s2) {
        boolean flag;
        Object obj;
        if(installOnSd(i))
            flag = true;
        else
        if(installForwardLocked(i) && !s.startsWith(mDrmAppPrivateInstallDir.getAbsolutePath()))
            flag = true;
        else
            flag = false;
        if(flag)
            obj = new AsecInstallArgs(s, s1, s2, installOnSd(i), installForwardLocked(i));
        else
            obj = new FileInstallArgs(s, s1, s2);
        return ((InstallArgs) (obj));
    }

    private InstallArgs createInstallArgs(Uri uri, int i, String s, String s1) {
        Object obj;
        if(installOnSd(i) || installForwardLocked(i))
            obj = new AsecInstallArgs(uri, getNextCodePath(uri.getPath(), s, "/pkg.apk"), installOnSd(i), installForwardLocked(i));
        else
            obj = new FileInstallArgs(uri, s, s1);
        return ((InstallArgs) (obj));
    }

    private InstallArgs createInstallArgs(InstallParams installparams) {
        Object obj;
        if(installOnSd(installparams.flags) || installparams.isForwardLocked())
            obj = new AsecInstallArgs(installparams);
        else
            obj = new FileInstallArgs(installparams);
        return ((InstallArgs) (obj));
    }

    private File createTempPackageFile(File file) {
label0:
        {
            File file1;
            File file2;
            try {
                file2 = File.createTempFile("vmdl", ".tmp", file);
            }
            catch(IOException ioexception) {
                Slog.e("PackageManager", "Couldn't create temp file for downloaded package file.");
                file1 = null;
                if(false)
                    ;
                else
                    break label0;
            }
            file1 = file2;
            try {
                FileUtils.setPermissions(file1.getCanonicalPath(), 384, -1, -1);
            }
            catch(IOException ioexception1) {
                Slog.e("PackageManager", "Trouble getting the canoncical path for a temp file.");
                file1 = null;
            }
        }
        return file1;
    }

    private boolean deleteApplicationCacheFilesLI(String s, int i) {
        boolean flag = false;
        if(s == null) {
            Slog.w("PackageManager", "Attempt to delete null packageName.");
        } else {
            android.content.pm.PackageParser.Package package1;
            synchronized(mPackages) {
                package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
            }
            if(package1 == null)
                Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
            else
            if(package1.applicationInfo == null)
                Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(s).append(" has no applicationInfo.").toString());
            else
            if(mInstaller.deleteCacheFiles(s) < 0)
                Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove cache files for package: ").append(s).toString());
            else
                flag = true;
        }
        return flag;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private boolean deleteInstalledPackageLI(android.content.pm.PackageParser.Package package1, boolean flag, int i, PackageRemovedInfo packageremovedinfo, boolean flag1) {
        boolean flag2 = false;
        ApplicationInfo applicationinfo = package1.applicationInfo;
        if(applicationinfo == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" has no applicationInfo.").toString());
        } else {
            if(packageremovedinfo != null)
                packageremovedinfo.uid = applicationinfo.uid;
            removePackageDataLI(package1, packageremovedinfo, i, flag1);
            if(flag) {
                byte byte0;
                if(isExternal(package1))
                    byte0 = 8;
                else
                    byte0 = 0;
                if(isForwardLocked(package1))
                    flag2 = true;
                packageremovedinfo.args = createInstallArgs(byte0 | flag2, applicationinfo.sourceDir, applicationinfo.publicSourceDir, applicationinfo.nativeLibraryDir);
            }
            flag2 = true;
        }
        return flag2;
    }

    private boolean deletePackageLI(String s, boolean flag, int i, PackageRemovedInfo packageremovedinfo, boolean flag1) {
        boolean flag2 = false;
        if(s != null) goto _L2; else goto _L1
_L1:
        Slog.w("PackageManager", "Attempt to delete null packageName.");
_L4:
        return flag2;
_L2:
        boolean flag3 = false;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        PackageSetting packagesetting;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 != null)
            break MISSING_BLOCK_LABEL_126;
        flag3 = true;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_119;
        Exception exception;
        exception;
        throw exception;
        package1 = packagesetting.pkg;
        hashmap;
        JVM INSTR monitorexit ;
        if(package1 == null)
            Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
        else
        if(flag3) {
            removePackageDataLI(package1, packageremovedinfo, i, flag1);
            flag2 = true;
        } else
        if(package1.applicationInfo == null)
            Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" has no applicationInfo.").toString());
        else
        if(isSystemApp(package1)) {
            Log.i("PackageManager", (new StringBuilder()).append("Removing system package:").append(package1.packageName).toString());
            flag2 = deleteSystemPackageLI(package1, i, packageremovedinfo, flag1);
        } else {
            Log.i("PackageManager", (new StringBuilder()).append("Removing non-system package:").append(package1.packageName).toString());
            killApplication(s, package1.applicationInfo.uid);
            flag2 = deleteInstalledPackageLI(package1, flag, i, packageremovedinfo, flag1);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private int deletePackageX(String s, boolean flag, boolean flag1, int i) {
        PackageRemovedInfo packageremovedinfo;
        IDevicePolicyManager idevicepolicymanager;
        packageremovedinfo = new PackageRemovedInfo();
        idevicepolicymanager = android.app.admin.IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if(idevicepolicymanager == null) goto _L2; else goto _L1
_L1:
        if(!idevicepolicymanager.packageHasActiveAdmins(s)) goto _L2; else goto _L3
_L3:
        Slog.w("PackageManager", (new StringBuilder()).append("Not removing package ").append(s).append(": has active device admin").toString());
        byte byte0 = -2;
_L5:
        return byte0;
        RemoteException remoteexception;
        remoteexception;
_L2:
        Object obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        int j = i | 0x10000;
        boolean flag2 = deletePackageLI(s, flag1, j, packageremovedinfo, true);
        obj;
        JVM INSTR monitorexit ;
        if(flag2 && flag) {
            boolean flag3 = packageremovedinfo.isRemovedPackageSystemUpdate;
            packageremovedinfo.sendBroadcast(flag1, flag3);
            if(flag3) {
                Bundle bundle = new Bundle(1);
                Exception exception;
                int k;
                if(packageremovedinfo.removedUid >= 0)
                    k = packageremovedinfo.removedUid;
                else
                    k = packageremovedinfo.uid;
                bundle.putInt("android.intent.extra.UID", k);
                bundle.putBoolean("android.intent.extra.REPLACING", true);
                sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", s, bundle, null, null, -1);
                sendPackageBroadcast("android.intent.action.PACKAGE_REPLACED", s, bundle, null, null, -1);
                sendPackageBroadcast("android.intent.action.MY_PACKAGE_REPLACED", null, null, s, null, -1);
            }
        }
        Runtime.getRuntime().gc();
        if(packageremovedinfo.args != null)
            synchronized(mInstallLock) {
                packageremovedinfo.args.doPostDeleteLI(flag1);
            }
        if(flag2)
            byte0 = 1;
        else
            byte0 = -1;
        if(true) goto _L5; else goto _L4
_L4:
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        obj1;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    private boolean deleteSystemPackageLI(android.content.pm.PackageParser.Package package1, int i, PackageRemovedInfo packageremovedinfo, boolean flag) {
        if(package1.applicationInfo != null) goto _L2; else goto _L1
_L1:
        boolean flag1;
        Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" has no applicationInfo.").toString());
        flag1 = false;
_L4:
        return flag1;
_L2:
        PackageSetting packagesetting;
        synchronized(mPackages) {
            packagesetting = mSettings.getDisabledSystemPkgLPr(package1.packageName);
        }
        if(packagesetting == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Attempt to delete unknown system package ").append(package1.packageName).toString());
            flag1 = false;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_121;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        android.content.pm.PackageParser.Package package2;
        Log.i("PackageManager", "Deleting system pkg from data partition");
        packageremovedinfo.isRemovedPackageSystemUpdate = true;
        int j;
        if(((PackageSettingBase) (packagesetting)).versionCode < package1.mVersionCode)
            j = i & -2;
        else
            j = i | 1;
        if(!deleteInstalledPackageLI(package1, true, j, packageremovedinfo, flag)) {
            flag1 = false;
            continue; /* Loop/switch isn't completed */
        }
        synchronized(mPackages) {
            mSettings.enableSystemPackageLPw(package1.packageName);
            NativeLibraryHelper.removeNativeBinariesLI(package1.applicationInfo.nativeLibraryDir);
        }
        package2 = scanPackageLI(((PackageSettingBase) (packagesetting)).codePath, 5, 33, 0L);
        if(package2 == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Failed to restore system package:").append(package1.packageName).append(" with error:").append(mLastScanError).toString());
            flag1 = false;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_292;
        exception1;
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception1;
        HashMap hashmap2 = mPackages;
        hashmap2;
        JVM INSTR monitorenter ;
        updatePermissionsLPw(package2.packageName, package2, 3);
        if(flag)
            mSettings.writeLPr();
        flag1 = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void deleteTempPackageFiles() {
        FilenameFilter filenamefilter = new FilenameFilter() {

            public boolean accept(File file, String s) {
                boolean flag;
                if(s.startsWith("vmdl") && s.endsWith(".tmp"))
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            final PackageManagerService this$0;

             {
                this$0 = PackageManagerService.this;
                super();
            }
        };
        String as[] = mAppInstallDir.list(filenamefilter);
        if(as != null) {
            int i = 0;
            while(i < as.length)  {
                (new File(mAppInstallDir, as[i])).delete();
                i++;
            }
        }
    }

    private static final void enforceSystemOrRoot(String s) {
        int i = Binder.getCallingUid();
        if(i != 1000 && i != 0)
            throw new SecurityException(s);
        else
            return;
    }

    private BasePermission findPermissionTreeLP(String s) {
        Iterator iterator = mSettings.mPermissionTrees.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        BasePermission basepermission = (BasePermission)iterator.next();
        if(!s.startsWith(basepermission.name) || s.length() <= basepermission.name.length() || s.charAt(basepermission.name.length()) != '.') goto _L4; else goto _L3
_L3:
        return basepermission;
_L2:
        basepermission = null;
        if(true) goto _L3; else goto _L5
_L5:
    }

    private static String fixProcessName(String s, String s1, int i) {
        if(s1 != null)
            s = s1;
        return s;
    }

    private ApplicationInfo generateApplicationInfoFromSettingsLPw(String s, int i, int j) {
        ApplicationInfo applicationinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return applicationinfo;
_L2:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting != null)
            if(packagesetting.pkg == null) {
                PackageInfo packageinfo = generatePackageInfoFromSettingsLPw(s, i, j);
                if(packageinfo != null)
                    applicationinfo = packageinfo.applicationInfo;
            } else {
                applicationinfo = PackageParser.generateApplicationInfo(packagesetting.pkg, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
            }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private PackageInfo generatePackageInfoFromSettingsLPw(String s, int i, int j) {
        PackageInfo packageinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return packageinfo;
_L2:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting != null) {
            new android.content.pm.PackageParser.Package(s);
            if(packagesetting.pkg == null) {
                packagesetting.pkg = new android.content.pm.PackageParser.Package(s);
                packagesetting.pkg.applicationInfo.packageName = s;
                packagesetting.pkg.applicationInfo.flags = ((GrantedPermissions) (packagesetting)).pkgFlags;
                packagesetting.pkg.applicationInfo.publicSourceDir = ((PackageSettingBase) (packagesetting)).resourcePathString;
                packagesetting.pkg.applicationInfo.sourceDir = ((PackageSettingBase) (packagesetting)).codePathString;
                packagesetting.pkg.applicationInfo.dataDir = getDataPathForPackage(packagesetting.pkg.packageName, 0).getPath();
                packagesetting.pkg.applicationInfo.nativeLibraryDir = ((PackageSettingBase) (packagesetting)).nativeLibraryPathString;
            }
            packageinfo = generatePackageInfo(packagesetting.pkg, i, j);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    static final PermissionInfo generatePermissionInfo(BasePermission basepermission, int i) {
        PermissionInfo permissioninfo;
        if(basepermission.perm != null) {
            permissioninfo = PackageParser.generatePermissionInfo(basepermission.perm, i);
        } else {
            permissioninfo = new PermissionInfo();
            permissioninfo.name = basepermission.name;
            permissioninfo.packageName = basepermission.sourcePackage;
            permissioninfo.nonLocalizedLabel = basepermission.name;
            permissioninfo.protectionLevel = basepermission.protectionLevel;
        }
        return permissioninfo;
    }

    static String getApkName(String s) {
        String s1 = null;
        if(s != null) goto _L2; else goto _L1
_L1:
        return s1;
_L2:
        int i;
        int j;
        i = s.lastIndexOf("/");
        j = s.lastIndexOf(".");
        if(j != -1) goto _L4; else goto _L3
_L3:
        j = s.length();
_L6:
        s1 = s.substring(i + 1, j);
        continue; /* Loop/switch isn't completed */
_L4:
        if(j != 0) goto _L6; else goto _L5
_L5:
        Slog.w("PackageManager", (new StringBuilder()).append(" Invalid code path, ").append(s).append(" Not a valid apk name").toString());
        if(true) goto _L1; else goto _L7
_L7:
    }

    static String getAsecPackageName(String s) {
        int i = s.lastIndexOf("-");
        if(i != -1)
            s = s.substring(0, i);
        return s;
    }

    private static final int getContinuationPoint(String as[], String s) {
        int j;
        if(s == null) {
            j = 0;
        } else {
            int i = Arrays.binarySearch(as, s);
            if(i < 0)
                j = -i;
            else
                j = i + 1;
        }
        return j;
    }

    private File getDataPathForPackage(String s, int i) {
        File file;
        if(i == 0)
            file = new File(mAppDataDir, s);
        else
            file = new File((new StringBuilder()).append(mUserAppDataDir.getAbsolutePath()).append(File.separator).append(i).append(File.separator).append(s).toString());
        return file;
    }

    private String getEncryptKey() {
        String s;
        s = SystemKeyStore.getInstance().retrieveKeyHexString("AppsOnSD");
        if(s != null)
            break MISSING_BLOCK_LABEL_42;
        s = SystemKeyStore.getInstance().generateNewKeyHexString(128, "AES", "AppsOnSD");
        if(s != null)
            break MISSING_BLOCK_LABEL_42;
        Slog.e("PackageManager", "Failed to create encryption keys");
        s = null;
_L2:
        return s;
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        Slog.e("PackageManager", (new StringBuilder()).append("Failed to create encryption keys with exception: ").append(nosuchalgorithmexception).toString());
        s = null;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.e("PackageManager", (new StringBuilder()).append("Failed to retrieve encryption keys with exception: ").append(ioexception).toString());
        s = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static String getNextCodePath(String s, String s1, String s2) {
        int i;
        String s5;
        i = 1;
        if(s == null)
            break MISSING_BLOCK_LABEL_110;
        String s4 = s;
        if(s4.endsWith(s2))
            s4 = s4.substring(0, s4.length() - s2.length());
        int j = s4.lastIndexOf(s1);
        if(j == -1)
            break MISSING_BLOCK_LABEL_110;
        s5 = s4.substring(j + s1.length());
        if(s5 == null)
            break MISSING_BLOCK_LABEL_110;
        if(s5.startsWith("-"))
            s5 = s5.substring("-".length());
        int k = Integer.parseInt(s5);
        String s3;
        if(k <= 1)
            i = k + 1;
        else
            i = k - 1;
_L2:
        s3 = (new StringBuilder()).append("-").append(Integer.toString(i)).toString();
        return (new StringBuilder()).append(s1).append(s3).toString();
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private boolean getPackageSizeInfoLI(String s, PackageStats packagestats) {
        if(s != null) goto _L2; else goto _L1
_L1:
        boolean flag1;
        Slog.w("PackageManager", "Attempt to get size of null packageName.");
        flag1 = false;
_L4:
        return flag1;
_L2:
        boolean flag;
        String s1;
        flag = false;
        s1 = null;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        PackageSetting packagesetting;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 != null)
            break MISSING_BLOCK_LABEL_138;
        flag = true;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting == null || packagesetting.pkg == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package named '").append(s).append("' doesn't exist.").toString());
            flag1 = false;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_131;
        Exception exception;
        exception;
        throw exception;
        package1 = packagesetting.pkg;
        if(package1 != null && (isExternal(package1) || isForwardLocked(package1))) {
            String s3 = cidFromCodePath(package1.applicationInfo.sourceDir);
            if(s3 != null)
                s1 = PackageHelper.getSdFilesystem(s3);
        }
        hashmap;
        JVM INSTR monitorexit ;
        String s2 = null;
        if(!flag) {
            ApplicationInfo applicationinfo = package1.applicationInfo;
            if(applicationinfo == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(s).append(" has no applicationInfo.").toString());
                flag1 = false;
                continue; /* Loop/switch isn't completed */
            }
            if(isForwardLocked(package1))
                s2 = applicationinfo.publicSourceDir;
        }
        if(mInstaller.getSizeInfo(s, package1.mPath, s2, s1, packagestats) < 0) {
            flag1 = false;
        } else {
            if(!isExternal(package1)) {
                packagestats.codeSize = packagestats.codeSize + packagestats.externalCodeSize;
                packagestats.externalCodeSize = 0L;
            }
            flag1 = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private String getRequiredVerifierLPr() {
        List list = queryIntentReceivers(new Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION"), "application/vnd.android.package-archive", 512, 0);
        String s = null;
        int i = list.size();
        int j = 0;
        while(j < i)  {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(j);
            if(resolveinfo.activityInfo != null) {
                String s1 = ((ComponentInfo) (resolveinfo.activityInfo)).packageName;
                PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
                if(packagesetting != null && ((GrantedPermissions) (packagesetting)).grantedPermissions.contains("android.permission.PACKAGE_VERIFICATION_AGENT")) {
                    if(s != null)
                        throw new RuntimeException("There can be only one required verifier");
                    s = s1;
                }
            }
            j++;
        }
        return s;
    }

    private static File getSettingsProblemFile() {
        return new File(new File(Environment.getDataDirectory(), "system"), "uiderrors.txt");
    }

    static String getTempContainerId() {
        int i;
        String as[];
        i = 1;
        as = PackageHelper.getSecureContainerList();
        if(as == null) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        j = as.length;
        k = 0;
_L6:
        if(k >= j) goto _L2; else goto _L3
_L3:
        String s = as[k];
        if(s != null && s.startsWith("smdl2tmp")) goto _L5; else goto _L4
_L4:
        k++;
          goto _L6
_L5:
        String s1 = s.substring("smdl2tmp".length());
        int l = Integer.parseInt(s1);
        if(l >= i)
            i = l + 1;
          goto _L4
_L2:
        return (new StringBuilder()).append("smdl2tmp").append(i).toString();
        NumberFormatException numberformatexception;
        numberformatexception;
          goto _L4
    }

    private int getUidForVerifier(VerifierInfo verifierinfo) {
        int i = -1;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(verifierinfo.packageName);
        if(package1 != null) goto _L2; else goto _L1
_L2:
        if(package1.mSignatures.length == 1) goto _L4; else goto _L3
_L3:
        Slog.i("PackageManager", (new StringBuilder()).append("Verifier package ").append(verifierinfo.packageName).append(" has more than one signature; ignoring").toString());
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L4:
        byte abyte0[] = package1.mSignatures[0].getPublicKey().getEncoded();
        if(Arrays.equals(verifierinfo.publicKey.getEncoded(), abyte0)) goto _L6; else goto _L5
_L5:
        Slog.i("PackageManager", (new StringBuilder()).append("Verifier package ").append(verifierinfo.packageName).append(" does not have the expected public key; ignoring").toString());
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
        CertificateException certificateexception;
        certificateexception;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L6:
        i = package1.applicationInfo.uid;
        hashmap;
        JVM INSTR monitorexit ;
_L1:
        return i;
    }

    private int getUidTargetSdkVersionLockedLPr(int i) {
        Object obj = mSettings.getUserIdLPr(i);
        if(!(obj instanceof SharedUserSetting)) goto _L2; else goto _L1
_L1:
        int j;
        SharedUserSetting sharedusersetting = (SharedUserSetting)obj;
        j = 10000;
        Iterator iterator = sharedusersetting.packages.iterator();
        do {
            if(!iterator.hasNext())
                break;
            PackageSetting packagesetting1 = (PackageSetting)iterator.next();
            if(packagesetting1.pkg != null) {
                int k = packagesetting1.pkg.applicationInfo.targetSdkVersion;
                if(k < j)
                    j = k;
            }
        } while(true);
          goto _L3
_L2:
        if(!(obj instanceof PackageSetting)) goto _L5; else goto _L4
_L4:
        PackageSetting packagesetting = (PackageSetting)obj;
        if(packagesetting.pkg == null) goto _L5; else goto _L6
_L6:
        j = packagesetting.pkg.applicationInfo.targetSdkVersion;
_L3:
        return j;
_L5:
        j = 10000;
        if(true) goto _L3; else goto _L7
_L7:
    }

    private long getVerificationTimeout() {
        return android.provider.Settings.Secure.getLong(mContext.getContentResolver(), "verifier_timeout", 60000L);
    }

    private void grantPermissionsLPw(android.content.pm.PackageParser.Package package1, boolean flag) {
        PackageSetting packagesetting = (PackageSetting)package1.mExtras;
        if(packagesetting != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj;
        HashSet hashset;
        boolean flag1;
        String s;
        BasePermission basepermission;
        String s1;
        boolean flag2;
        int k;
        boolean flag3;
        int i1;
        int i;
        int j;
        int l;
        android.content.pm.PackageParser.NewPermissionInfo newpermissioninfo;
        if(packagesetting.sharedUser != null)
            obj = packagesetting.sharedUser;
        else
            obj = packagesetting;
        hashset = ((GrantedPermissions) (obj)).grantedPermissions;
        flag1 = false;
        if(flag) {
            packagesetting.permissionsFixed = false;
            if(obj == packagesetting) {
                hashset = new HashSet(((GrantedPermissions) (obj)).grantedPermissions);
                ((GrantedPermissions) (obj)).grantedPermissions.clear();
                obj.gids = mGlobalGids;
            }
        }
        if(((GrantedPermissions) (obj)).gids == null)
            obj.gids = mGlobalGids;
        i = package1.requestedPermissions.size();
        j = 0;
        if(j >= i)
            break MISSING_BLOCK_LABEL_943;
        s = (String)package1.requestedPermissions.get(j);
        basepermission = (BasePermission)mSettings.mPermissions.get(s);
        if(basepermission == null || basepermission.packageSetting == null)
            break MISSING_BLOCK_LABEL_900;
        s1 = basepermission.name;
        flag2 = false;
        k = 0xf & basepermission.protectionLevel;
        if(k == 0 || k == 1)
            flag3 = true;
        else
        if(basepermission.packageSetting == null)
            flag3 = false;
        else
        if(k == 2) {
            boolean flag4;
            if(compareSignatures(basepermission.packageSetting.signatures.mSignatures, package1.mSignatures) == 0 || compareSignatures(mPlatformPackage.mSignatures, package1.mSignatures) == 0)
                flag4 = true;
            else
                flag4 = false;
            flag3 = flag4 | ExtraPackageManager.isTrustedSystemSignature(package1.mSignatures);
            if(!flag3 && (0x10 & basepermission.protectionLevel) != 0 && isSystemApp(package1))
                if(isUpdatedSystemApp(package1)) {
                    PackageSetting packagesetting1 = mSettings.getDisabledSystemPkgLPr(package1.packageName);
                    Object obj1;
                    if(packagesetting1.sharedUser != null)
                        obj1 = packagesetting1.sharedUser;
                    else
                        obj1 = packagesetting1;
                    if(((GrantedPermissions) (obj1)).grantedPermissions.contains(s1))
                        flag3 = true;
                    else
                        flag3 = false;
                } else {
                    flag3 = true;
                }
            if(!flag3 && (0x20 & basepermission.protectionLevel) != 0)
                if(hashset.contains(s1))
                    flag3 = true;
                else
                    flag3 = false;
            if(flag3)
                flag2 = true;
        } else {
            flag3 = false;
        }
        if(!flag3) goto _L4; else goto _L3
_L3:
        if((1 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0 || !((PackageSettingBase) (packagesetting)).permissionsFixed || flag2 || ((GrantedPermissions) (obj)).grantedPermissions.contains(s1)) goto _L6; else goto _L5
_L5:
        flag3 = false;
        l = PackageParser.NEW_PERMISSIONS.length;
        i1 = 0;
_L10:
        if(i1 >= l) goto _L6; else goto _L7
_L7:
        newpermissioninfo = PackageParser.NEW_PERMISSIONS[i1];
        if(!newpermissioninfo.name.equals(s1) || package1.applicationInfo.targetSdkVersion >= newpermissioninfo.sdkVersion) goto _L9; else goto _L8
_L8:
        flag3 = true;
        Log.i("PackageManager", (new StringBuilder()).append("Auto-granting ").append(s1).append(" to old pkg ").append(package1.packageName).toString());
_L6:
        if(flag3) {
            if(!((GrantedPermissions) (obj)).grantedPermissions.contains(s1)) {
                flag1 = true;
                ((GrantedPermissions) (obj)).grantedPermissions.add(s1);
                obj.gids = appendInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
            } else
            if(!((PackageSettingBase) (packagesetting)).haveGids)
                obj.gids = appendInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Not granting permission ").append(s1).append(" to package ").append(package1.packageName).append(" because it was previously installed without").toString());
        }
_L11:
        j++;
        break MISSING_BLOCK_LABEL_111;
_L9:
        i1++;
          goto _L10
_L4:
        if(((GrantedPermissions) (obj)).grantedPermissions.remove(s1)) {
            flag1 = true;
            obj.gids = removeInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
            Slog.i("PackageManager", (new StringBuilder()).append("Un-granting permission ").append(s1).append(" from package ").append(package1.packageName).append(" (protectionLevel=").append(basepermission.protectionLevel).append(" flags=0x").append(Integer.toHexString(package1.applicationInfo.flags)).append(")").toString());
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Not granting permission ").append(s1).append(" to package ").append(package1.packageName).append(" (protectionLevel=").append(basepermission.protectionLevel).append(" flags=0x").append(Integer.toHexString(package1.applicationInfo.flags)).append(")").toString());
        }
          goto _L11
        Slog.w("PackageManager", (new StringBuilder()).append("Unknown permission ").append(s).append(" in package ").append(package1.packageName).toString());
          goto _L11
        if((flag1 || flag) && !((PackageSettingBase) (packagesetting)).permissionsFixed && (1 & ((GrantedPermissions) (packagesetting)).pkgFlags) == 0 || (0x80 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
            packagesetting.permissionsFixed = true;
        packagesetting.haveGids = true;
        if(true) goto _L1; else goto _L12
_L12:
    }

    private static boolean hasPermission(android.content.pm.PackageParser.Package package1, String s) {
        int i = -1 + package1.permissions.size();
_L3:
        if(i < 0)
            break MISSING_BLOCK_LABEL_49;
        if(!((android.content.pm.PackageParser.Permission)package1.permissions.get(i)).info.name.equals(s)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        i--;
          goto _L3
        flag = false;
          goto _L4
    }

    private static boolean ignoreCodePath(String s) {
        String s1;
        int i;
        s1 = getApkName(s);
        i = s1.lastIndexOf("-");
        if(i == -1 || i + 1 >= s1.length()) goto _L2; else goto _L1
_L1:
        String s2 = s1.substring(i + 1);
        Integer.parseInt(s2);
        boolean flag = true;
_L4:
        return flag;
        NumberFormatException numberformatexception;
        numberformatexception;
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static boolean installForwardLocked(int i) {
        boolean flag;
        if((i & 1) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void installNewPackageLI(android.content.pm.PackageParser.Package package1, int i, int j, String s, PackageInstalledInfo packageinstalledinfo) {
        String s1;
        boolean flag;
        s1 = package1.packageName;
        flag = getDataPathForPackage(package1.packageName, 0).exists();
        packageinstalledinfo.name = s1;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(mSettings.mRenamedPackages.containsKey(s1)) {
            Slog.w("PackageManager", (new StringBuilder()).append("Attempt to re-install ").append(s1).append(" without first uninstalling package running as ").append((String)mSettings.mRenamedPackages.get(s1)).toString());
            packageinstalledinfo.returnCode = -1;
            break MISSING_BLOCK_LABEL_330;
        }
        if(mPackages.containsKey(s1) || mAppDirs.containsKey(package1.mPath)) {
            Slog.w("PackageManager", (new StringBuilder()).append("Attempt to re-install ").append(s1).append(" without first uninstalling.").toString());
            packageinstalledinfo.returnCode = -1;
            break MISSING_BLOCK_LABEL_330;
        }
        break MISSING_BLOCK_LABEL_195;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        mLastScanError = 1;
        android.content.pm.PackageParser.Package package2 = scanPackageLI(package1, i, j, System.currentTimeMillis());
        if(package2 == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package couldn't be installed in ").append(package1.mPath).toString());
            int l = mLastScanError;
            packageinstalledinfo.returnCode = l;
            if(l == 1)
                packageinstalledinfo.returnCode = -2;
        } else {
            updateSettingsLI(package2, s, packageinstalledinfo);
            if(packageinstalledinfo.returnCode != 1) {
                int k;
                if(flag)
                    k = 1;
                else
                    k = 0;
                deletePackageLI(s1, false, k, packageinstalledinfo.removedInfo, true);
            }
        }
    }

    private static boolean installOnSd(int i) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if((i & 0x10) == 0 && (i & 8) != 0)
            flag = true;
        return flag;
    }

    private void installPackageLI(InstallArgs installargs, boolean flag, PackageInstalledInfo packageinstalledinfo) {
        int i;
        String s;
        boolean flag2;
        boolean flag3;
        int l;
        int k1;
        android.content.pm.PackageParser.Package package1;
        String s1;
        i = installargs.flags;
        s = installargs.installerPackageName;
        File file = new File(installargs.getCodePath());
        boolean flag1;
        int j;
        int k;
        byte byte0;
        int i1;
        byte byte1;
        int j1;
        byte byte2;
        PackageParser packageparser;
        if((i & 1) != 0)
            flag1 = true;
        else
            flag1 = false;
        if((i & 8) != 0)
            flag2 = true;
        else
            flag2 = false;
        flag3 = false;
        if(flag2)
            j = 0;
        else
            j = 1;
        k = 8 | (j | 4);
        if(flag)
            byte0 = 16;
        else
            byte0 = 0;
        l = k | byte0;
        packageinstalledinfo.returnCode = 1;
        i1 = 2 | mDefParseFlags;
        if(flag1)
            byte1 = 16;
        else
            byte1 = 0;
        j1 = i1 | byte1;
        if(flag2)
            byte2 = 32;
        else
            byte2 = 0;
        k1 = j1 | byte2;
        packageparser = new PackageParser(file.getPath());
        packageparser.setSeparateProcesses(mSeparateProcesses);
        package1 = packageparser.parsePackage(file, null, mMetrics, k1);
        if(package1 == null) {
            packageinstalledinfo.returnCode = packageparser.getParseError();
        } else {
            s1 = package1.packageName;
            packageinstalledinfo.name = s1;
            if((0x100 & package1.applicationInfo.flags) != 0 && (i & 4) == 0)
                packageinstalledinfo.returnCode = -15;
            else
            if(!packageparser.collectCertificates(package1, k1)) {
                packageinstalledinfo.returnCode = packageparser.getParseError();
            } else {
label0:
                {
                    if(installargs.manifestDigest == null || installargs.manifestDigest.equals(package1.manifestDigest))
                        break label0;
                    packageinstalledinfo.returnCode = -23;
                }
            }
        }
_L5:
        return;
        String s2;
        boolean flag4;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if((i & 2) == 0) goto _L2; else goto _L1
_L1:
        String s3 = (String)mSettings.mRenamedPackages.get(s1);
        if(package1.mOriginalPackages == null || !package1.mOriginalPackages.contains(s3) || !mPackages.containsKey(s3)) goto _L4; else goto _L3
_L3:
        package1.setPackageName(s3);
        s1 = package1.packageName;
        flag3 = true;
_L2:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
        if(packagesetting != null) {
            s2 = ((PackageSettingBase) ((PackageSetting)mSettings.mPackages.get(s1))).codePathString;
            if(packagesetting.pkg != null && packagesetting.pkg.applicationInfo != null) {
                if((1 & packagesetting.pkg.applicationInfo.flags) == 0)
                    break MISSING_BLOCK_LABEL_627;
                flag4 = true;
            }
        }
_L6:
        hashmap;
        JVM INSTR monitorexit ;
        Exception exception;
        if(flag4 && flag2) {
            Slog.w("PackageManager", "Cannot install updates to system apps on sdcard");
            packageinstalledinfo.returnCode = -19;
        } else
        if(!installargs.doRename(packageinstalledinfo.returnCode, s1, s2)) {
            packageinstalledinfo.returnCode = -4;
        } else {
            setApplicationInfoPaths(package1, installargs.getCodePath(), installargs.getResourcePath());
            package1.applicationInfo.nativeLibraryDir = installargs.getNativeLibraryPath();
            if(flag3)
                replacePackageLI(package1, k1, l, s, packageinstalledinfo);
            else
                installNewPackageLI(package1, k1, l, s, packageinstalledinfo);
        }
          goto _L5
_L4:
        if(mPackages.containsKey(s1))
            flag3 = true;
          goto _L2
        exception;
        throw exception;
        flag4 = false;
          goto _L6
    }

    private boolean isAsecExternal(String s) {
        boolean flag;
        if(!PackageHelper.getSdFilesystem(s).startsWith(mAsecInternalPath))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isExternal(android.content.pm.PackageParser.Package package1) {
        boolean flag;
        if((0x40000 & package1.applicationInfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isExternal(PackageSetting packagesetting) {
        boolean flag;
        if((0x40000 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isExternalMediaAvailable() {
        boolean flag;
        if(mMediaMounted || Environment.isExternalStorageEmulated())
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isForwardLocked(android.content.pm.PackageParser.Package package1) {
        boolean flag;
        if((0x20000000 & package1.applicationInfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isForwardLocked(PackageSetting packagesetting) {
        boolean flag;
        if((0x20000000 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final boolean isPackageFilename(String s) {
        boolean flag;
        if(s != null && s.endsWith(".apk"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isPermissionEnforcedLocked(String s) {
        boolean flag = true;
        if(!"android.permission.READ_EXTERNAL_STORAGE".equals(s)) goto _L2; else goto _L1
_L1:
        if(mSettings.mReadExternalStorageEnforced == null) goto _L4; else goto _L3
_L3:
        flag = mSettings.mReadExternalStorageEnforced.booleanValue();
_L2:
        return flag;
_L4:
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "read_external_storage_enforced_default", 0) == 0)
            flag = false;
        if(true) goto _L2; else goto _L5
_L5:
    }

    private static boolean isSystemApp(ApplicationInfo applicationinfo) {
        boolean flag;
        if((1 & applicationinfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isSystemApp(android.content.pm.PackageParser.Package package1) {
        boolean flag;
        if((1 & package1.applicationInfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isSystemApp(PackageSetting packagesetting) {
        boolean flag;
        if((1 & ((GrantedPermissions) (packagesetting)).pkgFlags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isUpdatedSystemApp(android.content.pm.PackageParser.Package package1) {
        boolean flag;
        if((0x80 & package1.applicationInfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isVerificationEnabled() {
        boolean flag = true;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "verifier_enable", 0) != flag)
            flag = false;
        return flag;
    }

    private void killApplication(String s, int i) {
        IActivityManager iactivitymanager;
        iactivitymanager = ActivityManagerNative.getDefault();
        if(iactivitymanager == null)
            break MISSING_BLOCK_LABEL_16;
        iactivitymanager.killApplicationWithUid(s, i);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void loadMediaPackages(HashMap hashmap, int ai[], HashSet hashset) {
        ArrayList arraylist;
        boolean flag;
        Iterator iterator;
        arraylist = new ArrayList();
        Set set = hashmap.keySet();
        flag = false;
        iterator = set.iterator();
_L6:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        AsecInstallArgs asecinstallargs;
        String s1;
        byte byte1;
        asecinstallargs = (AsecInstallArgs)iterator.next();
        s1 = (String)hashmap.get(asecinstallargs);
        byte1 = -18;
        if(asecinstallargs.doPreInstall(1) == 1) goto _L4; else goto _L3
_L3:
        Slog.e("PackageManager", (new StringBuilder()).append("Failed to mount cid : ").append(asecinstallargs.cid).append(" when installing from sdcard").toString());
        if(byte1 == 1) goto _L6; else goto _L5
_L5:
        String s2 = asecinstallargs.cid;
_L8:
        hashset.add(s2);
          goto _L6
_L4:
        if(s1 == null)
            break MISSING_BLOCK_LABEL_151;
        if(s1.equals(asecinstallargs.getCodePath()))
            break MISSING_BLOCK_LABEL_222;
        Slog.e("PackageManager", (new StringBuilder()).append("Container ").append(asecinstallargs.cid).append(" cachepath ").append(asecinstallargs.getCodePath()).append(" does not match one in settings ").append(s1).toString());
        if(byte1 == 1) goto _L6; else goto _L7
_L7:
        s2 = asecinstallargs.cid;
          goto _L8
        int i;
        i = mDefParseFlags;
        if(asecinstallargs.isExternal())
            i |= 0x20;
        if(asecinstallargs.isFwdLocked())
            i |= 0x10;
        flag = true;
        Object obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        package1 = scanPackageLI(new File(s1), i, 0, 0L);
        if(package1 == null)
            break MISSING_BLOCK_LABEL_391;
        HashMap hashmap2 = mPackages;
        hashmap2;
        JVM INSTR monitorenter ;
        byte1 = 1;
        arraylist.add(package1.packageName);
        asecinstallargs.doPostInstall(1, package1.applicationInfo.uid);
        hashmap2;
        JVM INSTR monitorexit ;
_L10:
        obj;
        JVM INSTR monitorexit ;
        if(byte1 == 1) goto _L6; else goto _L9
_L9:
        s2 = asecinstallargs.cid;
          goto _L8
        Exception exception3;
        exception3;
        hashmap2;
        JVM INSTR monitorexit ;
        throw exception3;
        Exception exception2;
        exception2;
        throw exception2;
        Exception exception1;
        exception1;
        if(byte1 != 1)
            hashset.add(asecinstallargs.cid);
        throw exception1;
        Slog.i("PackageManager", (new StringBuilder()).append("Failed to install pkg from  ").append(s1).append(" from sdcard").toString());
          goto _L10
_L2:
        HashMap hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        if(mSettings.mExternalSdkPlatform == mSdkVersion) goto _L12; else goto _L11
_L11:
        boolean flag1 = true;
_L13:
        byte byte0;
        if(flag1)
            Slog.i("PackageManager", (new StringBuilder()).append("Platform changed from ").append(mSettings.mExternalSdkPlatform).append(" to ").append(mSdkVersion).append("; regranting permissions for external storage").toString());
        mSettings.mExternalSdkPlatform = mSdkVersion;
        if(!flag1)
            break MISSING_BLOCK_LABEL_661;
        byte0 = 6;
_L14:
        updatePermissionsLPw(null, null, byte0 | 1);
        mSettings.writeLPr();
        if(arraylist.size() > 0)
            sendResourcesChangedBroadcast(true, arraylist, ai, null);
        if(flag)
            Runtime.getRuntime().gc();
        if(hashset != null) {
            for(Iterator iterator1 = hashset.iterator(); iterator1.hasNext();) {
                String s = (String)iterator1.next();
                Exception exception;
                if(s.startsWith("smdl2tmp")) {
                    Log.i("PackageManager", (new StringBuilder()).append("Destroying stale temporary container ").append(s).toString());
                    PackageHelper.destroySdDir(s);
                } else {
                    Log.w("PackageManager", (new StringBuilder()).append("Container ").append(s).append(" is stale").toString());
                }
            }

        }
        break MISSING_BLOCK_LABEL_711;
_L12:
        flag1 = false;
          goto _L13
        byte0 = 0;
          goto _L14
        exception;
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception;
          goto _L8
    }

    public static final IPackageManager main(Context context, boolean flag, boolean flag1) {
        PackageManagerService packagemanagerservice = new PackageManagerService(context, flag, flag1);
        ServiceManager.addService("package", packagemanagerservice);
        return packagemanagerservice;
    }

    private ComponentName matchComponentForVerifier(String s, List list) {
        ActivityInfo activityinfo;
        int i;
        int j;
        activityinfo = null;
        i = list.size();
        j = 0;
_L3:
        ResolveInfo resolveinfo;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        resolveinfo = (ResolveInfo)list.get(j);
          goto _L1
_L5:
        j++;
        if(true) goto _L3; else goto _L2
_L1:
        if(resolveinfo.activityInfo == null || !s.equals(((ComponentInfo) (resolveinfo.activityInfo)).packageName)) goto _L5; else goto _L4
_L4:
        activityinfo = resolveinfo.activityInfo;
_L2:
        ComponentName componentname;
        if(activityinfo == null)
            componentname = null;
        else
            componentname = new ComponentName(((ComponentInfo) (activityinfo)).packageName, ((ComponentInfo) (activityinfo)).name);
        return componentname;
    }

    private List matchVerifiers(PackageInfoLite packageinfolite, List list, PackageVerificationState packageverificationstate) {
        Object obj;
        if(packageinfolite.verifiers.length == 0) {
            obj = null;
        } else {
            int i = packageinfolite.verifiers.length;
            obj = new ArrayList(i + 1);
            int j = 0;
            while(j < i)  {
                VerifierInfo verifierinfo = packageinfolite.verifiers[j];
                ComponentName componentname = matchComponentForVerifier(verifierinfo.packageName, list);
                if(componentname != null) {
                    int k = getUidForVerifier(verifierinfo);
                    if(k != -1) {
                        ((List) (obj)).add(componentname);
                        packageverificationstate.addSufficientVerifier(k);
                    }
                }
                j++;
            }
        }
        return ((List) (obj));
    }

    private int moveDexFilesLI(android.content.pm.PackageParser.Package package1) {
        if((4 & package1.applicationInfo.flags) == 0 || mInstaller.movedex(package1.mScanPath, package1.mPath) == 0) goto _L2; else goto _L1
_L1:
        if(!mNoDexOpt) goto _L4; else goto _L3
_L3:
        Slog.i("PackageManager", (new StringBuilder()).append("dex file doesn't exist, skipping move: ").append(package1.mPath).toString());
_L2:
        int i = 1;
_L6:
        return i;
_L4:
        Slog.e("PackageManager", (new StringBuilder()).append("Couldn't rename dex file: ").append(package1.mPath).toString());
        i = -4;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private int packageFlagsToInstallFlags(PackageSetting packagesetting) {
        int i = 0;
        if(isExternal(packagesetting))
            i = 0 | 8;
        if(isForwardLocked(packagesetting))
            i |= 1;
        return i;
    }

    private int performDexOptLI(android.content.pm.PackageParser.Package package1, boolean flag, boolean flag1) {
        byte byte0;
        boolean flag2;
        byte0 = 1;
        flag2 = false;
        if((4 & package1.applicationInfo.flags) == 0) goto _L2; else goto _L1
_L1:
        String s;
        int i;
        s = package1.mScanPath;
        i = 0;
        if(flag) goto _L4; else goto _L3
_L3:
        if(!DexFile.isDexOptNeeded(s)) goto _L5; else goto _L4
_L4:
        if(flag || !flag1) goto _L7; else goto _L6
_L6:
        mDeferredDexOpt.add(package1);
        byte0 = 2;
          goto _L8
_L7:
        Installer installer;
        int j;
        Log.i("PackageManager", (new StringBuilder()).append("Running dexopt on: ").append(package1.applicationInfo.packageName).toString());
        installer = mInstaller;
        j = package1.applicationInfo.uid;
        if(isForwardLocked(package1)) goto _L10; else goto _L9
_L9:
        boolean flag3 = byte0;
_L12:
        i = installer.dexopt(s, j, flag3);
        package1.mDidDexOpt = true;
        flag2 = true;
_L5:
        if(i >= 0)
            break; /* Loop/switch isn't completed */
        byte0 = -1;
          goto _L8
_L10:
        flag3 = false;
        continue; /* Loop/switch isn't completed */
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w("PackageManager", (new StringBuilder()).append("Apk not found for dexopt: ").append(s).toString());
        i = -1;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        Slog.w("PackageManager", (new StringBuilder()).append("IOException reading apk: ").append(s).toString(), ioexception);
        i = -1;
        continue; /* Loop/switch isn't completed */
        StaleDexCacheError staledexcacheerror;
        staledexcacheerror;
        Slog.w("PackageManager", (new StringBuilder()).append("StaleDexCacheError when reading apk: ").append(s).toString(), staledexcacheerror);
        i = -1;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        Slog.w("PackageManager", "Exception when doing dexopt : ", exception);
        i = -1;
        if(true) goto _L5; else goto _L2
_L2:
        if(!flag2)
            byte0 = 0;
_L8:
        return byte0;
        if(true) goto _L12; else goto _L11
_L11:
    }

    private void processPendingInstall(final InstallArgs args, final int currentStatus) {
        mHandler.post(new Runnable() {

            public void run() {
                mHandler.removeCallbacks(this);
                PackageInstalledInfo packageinstalledinfo = new PackageInstalledInfo();
                packageinstalledinfo.returnCode = currentStatus;
                packageinstalledinfo.uid = -1;
                packageinstalledinfo.pkg = null;
                packageinstalledinfo.removedInfo = new PackageRemovedInfo();
                if(packageinstalledinfo.returnCode == 1) {
                    args.doPreInstall(packageinstalledinfo.returnCode);
                    synchronized(mInstallLock) {
                        installPackageLI(args, true, packageinstalledinfo);
                    }
                    args.doPostInstall(packageinstalledinfo.returnCode, packageinstalledinfo.uid);
                }
                boolean flag;
                boolean flag1;
                PackageManagerService packagemanagerservice;
                int i;
                PostInstallData postinstalldata;
                if(packageinstalledinfo.removedInfo.removedPackage != null)
                    flag = true;
                else
                    flag = false;
                if(!flag && packageinstalledinfo.pkg != null && packageinstalledinfo.pkg.applicationInfo.backupAgentName != null)
                    flag1 = true;
                else
                    flag1 = false;
                if(mNextInstallToken < 0)
                    mNextInstallToken = 1;
                packagemanagerservice = PackageManagerService.this;
                i = packagemanagerservice.mNextInstallToken;
                packagemanagerservice.mNextInstallToken = i + 1;
                postinstalldata = new PostInstallData(args, packageinstalledinfo);
                mRunningInstalls.put(i, postinstalldata);
                if(packageinstalledinfo.returnCode == 1 && flag1) {
                    IBackupManager ibackupmanager = android.app.backup.IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
                    Message message;
                    if(ibackupmanager != null) {
                        try {
                            ibackupmanager.restoreAtInstall(packageinstalledinfo.pkg.applicationInfo.packageName, i);
                        }
                        catch(RemoteException remoteexception) { }
                        catch(Exception exception) {
                            Slog.e("PackageManager", "Exception trying to enqueue restore", exception);
                            flag1 = false;
                        }
                    } else {
                        Slog.e("PackageManager", "Backup Manager not found!");
                        flag1 = false;
                    }
                }
                if(!flag1) {
                    message = mHandler.obtainMessage(9, i, 0);
                    mHandler.sendMessage(message);
                }
                return;
                exception1;
                obj;
                JVM INSTR monitorexit ;
                throw exception1;
            }

            final PackageManagerService this$0;
            final InstallArgs val$args;
            final int val$currentStatus;

             {
                this$0 = PackageManagerService.this;
                currentStatus = i;
                args = installargs;
                super();
            }
        });
    }

    private void processPendingMove(final MoveParams mp, final int currentStatus) {
        mHandler.post(new Runnable() {

            public void run() {
                int i;
                mHandler.removeCallbacks(this);
                i = currentStatus;
                if(currentStatus != 1) goto _L2; else goto _L1
_L1:
                int ai[];
                ArrayList arraylist;
                ai = null;
                arraylist = null;
                HashMap hashmap1 = mPackages;
                hashmap1;
                JVM INSTR monitorenter ;
                android.content.pm.PackageParser.Package package2 = (android.content.pm.PackageParser.Package)mPackages.get(mp.packageName);
                if(package2 != null) goto _L4; else goto _L3
_L3:
                Slog.w("PackageManager", (new StringBuilder()).append(" Package ").append(mp.packageName).append(" doesn't exist. Aborting move").toString());
                i = -2;
_L11:
                if(i != 1) goto _L2; else goto _L5
_L5:
                sendResourcesChangedBroadcast(false, arraylist, ai, null);
                Object obj1 = mInstallLock;
                obj1;
                JVM INSTR monitorenter ;
                HashMap hashmap2 = mPackages;
                hashmap2;
                JVM INSTR monitorenter ;
                android.content.pm.PackageParser.Package package3 = (android.content.pm.PackageParser.Package)mPackages.get(mp.packageName);
                if(package3 != null) goto _L7; else goto _L6
_L6:
                Slog.w("PackageManager", (new StringBuilder()).append(" Package ").append(mp.packageName).append(" doesn't exist. Aborting move").toString());
                i = -2;
_L13:
                obj1;
                JVM INSTR monitorexit ;
                sendResourcesChangedBroadcast(true, arraylist, ai, null);
_L2:
                if(i == 1) goto _L9; else goto _L8
_L8:
                if(mp.targetArgs != null)
                    mp.targetArgs.doPostInstall(-110, -1);
_L23:
                IPackageMoveObserver ipackagemoveobserver;
                if(i != -7)
                    synchronized(mPackages) {
                        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(mp.packageName);
                        if(package1 != null)
                            package1.mOperationPending = false;
                    }
                ipackagemoveobserver = mp.observer;
                if(ipackagemoveobserver == null)
                    break MISSING_BLOCK_LABEL_351;
                ipackagemoveobserver.packageMoved(mp.packageName, i);
_L24:
                return;
_L4:
label0:
                {
                    if(mp.srcArgs.getCodePath().equals(package2.applicationInfo.sourceDir))
                        break label0;
                    Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(mp.packageName).append(" code path changed from ").append(mp.srcArgs.getCodePath()).append(" to ").append(package2.applicationInfo.sourceDir).append(" Aborting move and returning error").toString());
                    i = -6;
                }
                if(true) goto _L11; else goto _L10
_L10:
                int ai1[];
                ai1 = new int[1];
                ai1[0] = package2.applicationInfo.uid;
                ArrayList arraylist1 = new ArrayList();
                arraylist1.add(mp.packageName);
                arraylist = arraylist1;
                ai = ai1;
                  goto _L11
                Exception exception1;
                exception1;
_L25:
                hashmap1;
                JVM INSTR monitorexit ;
                throw exception1;
_L7:
label1:
                {
                    if(mp.srcArgs.getCodePath().equals(package3.applicationInfo.sourceDir))
                        break label1;
                    Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(mp.packageName).append(" code path changed from ").append(mp.srcArgs.getCodePath()).append(" to ").append(package3.applicationInfo.sourceDir).append(" Aborting move and returning error").toString());
                    i = -6;
                }
                if(true) goto _L13; else goto _L12
_L12:
                String s;
                String s1;
                String s2;
                String s3;
                s = package3.mPath;
                s1 = mp.targetArgs.getCodePath();
                s2 = mp.targetArgs.getResourcePath();
                s3 = mp.targetArgs.getNativeLibraryPath();
                File file;
                file = new File(s3);
                file.getParentFile().getCanonicalPath();
                if(!file.getParentFile().getCanonicalPath().equals(package3.applicationInfo.dataDir)) goto _L15; else goto _L14
_L14:
                int k = mInstaller.unlinkNativeLibraryDirectory(package3.applicationInfo.dataDir);
                if(k >= 0) goto _L17; else goto _L16
_L16:
                i = -1;
_L21:
                if(i == 1) {
                    package3.mPath = s1;
                    if(moveDexFilesLI(package3) != 1) {
                        package3.mPath = package3.mScanPath;
                        i = -1;
                    }
                }
                if(i != 1) goto _L13; else goto _L18
_L18:
                PackageSetting packagesetting;
                package3.mScanPath = s1;
                package3.applicationInfo.sourceDir = s1;
                package3.applicationInfo.publicSourceDir = s2;
                package3.applicationInfo.nativeLibraryDir = s3;
                packagesetting = (PackageSetting)package3.mExtras;
                File file1 = new File(package3.applicationInfo.sourceDir);
                packagesetting.codePath = file1;
                packagesetting.codePathString = ((PackageSettingBase) (packagesetting)).codePath.getPath();
                File file2 = new File(package3.applicationInfo.publicSourceDir);
                packagesetting.resourcePath = file2;
                packagesetting.resourcePathString = ((PackageSettingBase) (packagesetting)).resourcePath.getPath();
                packagesetting.nativeLibraryPathString = s3;
                if((8 & mp.flags) == 0) goto _L20; else goto _L19
_L19:
                ApplicationInfo applicationinfo1 = package3.applicationInfo;
                applicationinfo1.flags = 0x40000 | applicationinfo1.flags;
_L22:
                packagesetting.setFlags(package3.applicationInfo.flags);
                mAppDirs.remove(s);
                mAppDirs.put(s1, package3);
                mSettings.writeLPr();
                  goto _L13
                Exception exception3;
                exception3;
                throw exception3;
                Exception exception2;
                exception2;
                throw exception2;
_L17:
                File file3 = new File(s1);
                NativeLibraryHelper.copyNativeBinariesIfNeededLI(file3, file);
                  goto _L21
_L15:
                int j = mInstaller.linkNativeLibraryDirectory(package3.applicationInfo.dataDir, s3);
                if(j < 0)
                    i = -1;
                  goto _L21
_L20:
                ApplicationInfo applicationinfo = package3.applicationInfo;
                applicationinfo.flags = 0xfffbffff & applicationinfo.flags;
                  goto _L22
_L9:
                Runtime.getRuntime().gc();
                Object obj = mInstallLock;
                obj;
                JVM INSTR monitorenter ;
                mp.srcArgs.doPostDeleteLI(true);
                  goto _L23
                exception;
                hashmap;
                JVM INSTR monitorexit ;
                throw exception;
                RemoteException remoteexception;
                remoteexception;
                Log.i("PackageManager", "Observer no longer exists.");
                  goto _L24
                exception1;
                  goto _L25
                exception1;
                  goto _L25
                IOException ioexception;
                ioexception;
                i = -5;
                  goto _L21
            }

            final PackageManagerService this$0;
            final int val$currentStatus;
            final MoveParams val$mp;

             {
                this$0 = PackageManagerService.this;
                currentStatus = i;
                mp = moveparams;
                super();
            }
        });
    }

    private void readPermissionsFromXml(File file) {
        FileReader filereader = new FileReader(file);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(filereader);
        XmlUtils.beginDocument(xmlpullparser, "permissions");
_L4:
        XmlUtils.nextElement(xmlpullparser);
        if(xmlpullparser.getEventType() != 1) goto _L2; else goto _L1
_L1:
        filereader.close();
_L3:
        return;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Slog.w("PackageManager", (new StringBuilder()).append("Couldn't find or open permissions file ").append(file).toString());
          goto _L3
_L2:
        String s;
        s = xmlpullparser.getName();
        if(!"group".equals(s))
            break MISSING_BLOCK_LABEL_213;
        String s8 = xmlpullparser.getAttributeValue(null, "gid");
        if(s8 == null)
            break MISSING_BLOCK_LABEL_164;
        int j = Integer.parseInt(s8);
        mGlobalGids = ArrayUtils.appendInt(mGlobalGids, j);
_L5:
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L4
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Slog.w("PackageManager", "Got execption parsing permissions.", xmlpullparserexception);
          goto _L3
        Slog.w("PackageManager", (new StringBuilder()).append("<group> without gid at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L5
        IOException ioexception;
        ioexception;
        Slog.w("PackageManager", "Got execption parsing permissions.", ioexception);
          goto _L3
        if("permission".equals(s)) {
            String s7 = xmlpullparser.getAttributeValue(null, "name");
            if(s7 == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("<permission> without name at ").append(xmlpullparser.getPositionDescription()).toString());
                XmlUtils.skipCurrentTag(xmlpullparser);
            } else {
                readPermission(xmlpullparser, s7.intern());
            }
        } else
        if("assign-permission".equals(s)) {
            String s4 = xmlpullparser.getAttributeValue(null, "name");
            if(s4 == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("<assign-permission> without name at ").append(xmlpullparser.getPositionDescription()).toString());
                XmlUtils.skipCurrentTag(xmlpullparser);
            } else {
                String s5 = xmlpullparser.getAttributeValue(null, "uid");
                if(s5 == null) {
                    Slog.w("PackageManager", (new StringBuilder()).append("<assign-permission> without uid at ").append(xmlpullparser.getPositionDescription()).toString());
                    XmlUtils.skipCurrentTag(xmlpullparser);
                } else {
                    int i = Process.getUidForName(s5);
                    if(i < 0) {
                        Slog.w("PackageManager", (new StringBuilder()).append("<assign-permission> with unknown uid \"").append(s5).append("\" at ").append(xmlpullparser.getPositionDescription()).toString());
                        XmlUtils.skipCurrentTag(xmlpullparser);
                    } else {
                        String s6 = s4.intern();
                        HashSet hashset = (HashSet)mSystemPermissions.get(i);
                        if(hashset == null) {
                            hashset = new HashSet();
                            mSystemPermissions.put(i, hashset);
                        }
                        hashset.add(s6);
                        XmlUtils.skipCurrentTag(xmlpullparser);
                    }
                }
            }
        } else
        if("library".equals(s)) {
            String s2 = xmlpullparser.getAttributeValue(null, "name");
            String s3 = xmlpullparser.getAttributeValue(null, "file");
            if(s2 == null)
                Slog.w("PackageManager", (new StringBuilder()).append("<library> without name at ").append(xmlpullparser.getPositionDescription()).toString());
            else
            if(s3 == null)
                Slog.w("PackageManager", (new StringBuilder()).append("<library> without file at ").append(xmlpullparser.getPositionDescription()).toString());
            else
                mSharedLibraries.put(s2, s3);
            XmlUtils.skipCurrentTag(xmlpullparser);
        } else
        if("feature".equals(s)) {
            String s1 = xmlpullparser.getAttributeValue(null, "name");
            if(s1 == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("<feature> without name at ").append(xmlpullparser.getPositionDescription()).toString());
            } else {
                FeatureInfo featureinfo = new FeatureInfo();
                featureinfo.name = s1;
                mAvailableFeatures.put(s1, featureinfo);
            }
            XmlUtils.skipCurrentTag(xmlpullparser);
        } else {
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
          goto _L4
    }

    static int[] removeInts(int ai[], int ai1[]) {
        if(ai1 != null && ai != null) {
            int i = ai1.length;
            int j = 0;
            while(j < i)  {
                ai = ArrayUtils.removeInt(ai, ai1[j]);
                j++;
            }
        }
        return ai;
    }

    private void removePackageDataLI(android.content.pm.PackageParser.Package package1, PackageRemovedInfo packageremovedinfo, int i, boolean flag) {
        HashMap hashmap1;
        String s = package1.packageName;
        if(packageremovedinfo != null)
            packageremovedinfo.removedPackage = s;
        boolean flag1;
        PackageSetting packagesetting;
        if((0x10000 & i) != 0)
            flag1 = true;
        else
            flag1 = false;
        removePackageLI(package1, flag1);
        synchronized(mPackages) {
            packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        }
        if((i & 1) == 0) {
            int j = mInstaller.remove(s, 0);
            if(j < 0)
                Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove app data or cache directory for package: ").append(s).append(", retcode=").append(j).toString());
            else
                sUserManager.removePackageForAllUsers(s);
            schedulePackageCleaning(s);
        }
        hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        if(packagesetting == null || (i & 1) != 0)
            break MISSING_BLOCK_LABEL_213;
        if(packageremovedinfo == null)
            break MISSING_BLOCK_LABEL_166;
        packageremovedinfo.removedUid = mSettings.removePackageLPw(s);
        if(packagesetting != null) {
            updatePermissionsLPw(((PackageSettingBase) (packagesetting)).name, null, 0);
            if(packagesetting.sharedUser != null)
                mSettings.updateSharedUserPermsLPw(packagesetting, mGlobalGids);
        }
        clearPackagePreferredActivitiesLPw(((PackageSettingBase) (packagesetting)).name);
        if(flag)
            mSettings.writeLPr();
        hashmap1;
        JVM INSTR monitorexit ;
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception1;
        exception1;
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    private void replaceNonSystemPackageLI(android.content.pm.PackageParser.Package package1, android.content.pm.PackageParser.Package package2, int i, int j, String s, PackageInstalledInfo packageinstalledinfo) {
        String s1;
        boolean flag1;
        s1 = package1.packageName;
        boolean flag = true;
        flag1 = false;
        long l;
        File file;
        boolean flag2;
        int k;
        int i1;
        int j1;
        if(package2.mExtras != null)
            l = ((PackageSettingBase) ((PackageSetting)package2.mExtras)).lastUpdateTime;
        else
            l = 0L;
        if(deletePackageLI(s1, true, 1, packageinstalledinfo.removedInfo, true)) goto _L2; else goto _L1
_L1:
        packageinstalledinfo.returnCode = -10;
        flag = false;
_L4:
        if(packageinstalledinfo.returnCode != 1) {
            if(flag1)
                deletePackageLI(s1, true, 1, packageinstalledinfo.removedInfo, true);
            if(flag) {
                file = new File(package1.mPath);
                flag2 = isExternal(package1);
                k = 2 | mDefParseFlags;
                android.content.pm.PackageParser.Package package3;
                byte byte0;
                byte byte1;
                int k1;
                int l1;
                if(isForwardLocked(package1))
                    byte0 = 16;
                else
                    byte0 = 0;
                i1 = k | byte0;
                if(flag2)
                    byte1 = 32;
                else
                    byte1 = 0;
                j1 = i1 | byte1;
                if(flag2)
                    k1 = 0;
                else
                    k1 = 1;
                if(scanPackageLI(file, j1, 0x40 | (k1 | 8), l) == null) {
                    Slog.e("PackageManager", (new StringBuilder()).append("Failed to restore package : ").append(s1).append(" after failed upgrade").toString());
                } else {
                    synchronized(mPackages) {
                        updatePermissionsLPw(package1.packageName, package1, 1);
                        mSettings.writeLPr();
                    }
                    Slog.i("PackageManager", (new StringBuilder()).append("Successfully restored package : ").append(s1).append(" after failed upgrade").toString());
                }
            }
        }
        return;
_L2:
        mLastScanError = 1;
        package3 = scanPackageLI(package2, i, j | 0x40, System.currentTimeMillis());
        if(package3 == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package couldn't be installed in ").append(package2.mPath).toString());
            l1 = mLastScanError;
            packageinstalledinfo.returnCode = l1;
            if(l1 == 1)
                packageinstalledinfo.returnCode = -2;
        } else {
            updateSettingsLI(package3, s, packageinstalledinfo);
            flag1 = true;
        }
        continue; /* Loop/switch isn't completed */
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void replacePackageLI(android.content.pm.PackageParser.Package package1, int i, int j, String s, PackageInstalledInfo packageinstalledinfo) {
        String s1 = package1.packageName;
        android.content.pm.PackageParser.Package package2;
        synchronized(mPackages) {
            package2 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
            if(compareSignatures(package2.mSignatures, package1.mSignatures) != 0) {
                Slog.w("PackageManager", (new StringBuilder()).append("New package has a different signature: ").append(s1).toString());
                packageinstalledinfo.returnCode = -104;
                break MISSING_BLOCK_LABEL_132;
            }
        }
        if(isSystemApp(package2))
            replaceSystemPackageLI(package2, package1, i, j, s, packageinstalledinfo);
        else
            replaceNonSystemPackageLI(package2, package1, i, j, s, packageinstalledinfo);
          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
    }

    private void replaceSystemPackageLI(android.content.pm.PackageParser.Package package1, android.content.pm.PackageParser.Package package2, int i, int j, String s, PackageInstalledInfo packageinstalledinfo) {
        boolean flag;
        int k;
        String s1;
        flag = false;
        k = i | 3;
        s1 = package1.packageName;
        packageinstalledinfo.returnCode = -10;
        if(s1 != null) goto _L2; else goto _L1
_L1:
        Slog.w("PackageManager", "Attempt to delete null packageName.");
_L5:
        return;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package3;
        PackageSetting packagesetting;
        package3 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
        if(package3 == null || package3.applicationInfo == null || packagesetting == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Couldn't find package:").append(s1).append(" information").toString());
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_141;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        killApplication(s1, package3.applicationInfo.uid);
        packageinstalledinfo.removedInfo.uid = package3.applicationInfo.uid;
        packageinstalledinfo.removedInfo.removedPackage = s1;
        removePackageLI(package3, true);
        HashMap hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        if(mSettings.disableSystemPackageLPw(s1) || package1 == null)
            break MISSING_BLOCK_LABEL_442;
        packageinstalledinfo.removedInfo.args = createInstallArgs(0, package1.applicationInfo.sourceDir, package1.applicationInfo.publicSourceDir, package1.applicationInfo.nativeLibraryDir);
_L3:
        mLastScanError = 1;
        ApplicationInfo applicationinfo = package2.applicationInfo;
        applicationinfo.flags = 0x80 | applicationinfo.flags;
        android.content.pm.PackageParser.Package package4 = scanPackageLI(package2, k, j, 0L);
        Exception exception1;
        HashMap hashmap2;
        if(package4 == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package couldn't be installed in ").append(package2.mPath).toString());
            int l = mLastScanError;
            packageinstalledinfo.returnCode = l;
            if(l == 1)
                packageinstalledinfo.returnCode = -2;
        } else {
            if(package4.mExtras != null) {
                PackageSetting packagesetting1 = (PackageSetting)package4.mExtras;
                packagesetting1.firstInstallTime = ((PackageSettingBase) (packagesetting)).firstInstallTime;
                packagesetting1.lastUpdateTime = System.currentTimeMillis();
            }
            updateSettingsLI(package4, s, packageinstalledinfo);
            flag = true;
        }
        if(packageinstalledinfo.returnCode == 1)
            continue; /* Loop/switch isn't completed */
        if(package4 != null)
            removePackageLI(package4, true);
        scanPackageLI(package3, k, 9, 0L);
        hashmap2 = mPackages;
        hashmap2;
        JVM INSTR monitorenter ;
        if(flag) {
            mSettings.enableSystemPackageLPw(s1);
            mSettings.setInstallerPackageName(s1, ((PackageSettingBase) (packagesetting)).installerPackageName);
        }
        mSettings.writeLPr();
        continue; /* Loop/switch isn't completed */
        packageinstalledinfo.removedInfo.args = null;
          goto _L3
        exception1;
        throw exception1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    static void reportSettingsProblem(int i, String s) {
        try {
            File file = getSettingsProblemFile();
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(file, true));
            String s1 = (new SimpleDateFormat()).format(new Date(System.currentTimeMillis()));
            printwriter.println((new StringBuilder()).append(s1).append(": ").append(s).toString());
            printwriter.close();
            FileUtils.setPermissions(file.toString(), 508, -1, -1);
        }
        catch(IOException ioexception) { }
        Slog.println(i, "PackageManager", s);
    }


// JavaClassFileOutputException: Prev chain is broken

    private android.content.pm.PackageParser.Package scanPackageLI(android.content.pm.PackageParser.Package package1, int i, int j, long l) {
        File file = new File(package1.mScanPath);
        if(file != null && package1.applicationInfo.sourceDir != null && package1.applicationInfo.publicSourceDir != null) goto _L2; else goto _L1
_L1:
        Slog.w("PackageManager", " Code and resource paths haven't been set correctly");
        mLastScanError = -2;
        package1 = null;
_L5:
        return package1;
_L2:
        mScanningPath = file;
        if((i & 1) != 0) {
            ApplicationInfo applicationinfo3 = package1.applicationInfo;
            applicationinfo3.flags = 1 | applicationinfo3.flags;
        }
        if(!package1.packageName.equals("android")) goto _L4; else goto _L3
_L3:
        HashMap hashmap4 = mPackages;
        hashmap4;
        JVM INSTR monitorenter ;
        if(mAndroidApplication == null)
            break MISSING_BLOCK_LABEL_194;
        Slog.w("PackageManager", "*************************************************");
        Slog.w("PackageManager", "Core android package being redefined.  Skipping.");
        Slog.w("PackageManager", (new StringBuilder()).append(" file=").append(mScanningPath).toString());
        Slog.w("PackageManager", "*************************************************");
        mLastScanError = -5;
        package1 = null;
          goto _L5
        Exception exception3;
        exception3;
        throw exception3;
        mPlatformPackage = package1;
        int i9 = mSdkVersion;
        package1.mVersionCode = i9;
        mAndroidApplication = package1.applicationInfo;
        mResolveActivity.applicationInfo = mAndroidApplication;
        mResolveActivity.name = com/android/internal/app/ResolverActivity.getName();
        mResolveActivity.packageName = mAndroidApplication.packageName;
        mResolveActivity.processName = mAndroidApplication.processName;
        mResolveActivity.launchMode = 0;
        mResolveActivity.flags = 32;
        mResolveActivity.theme = 0x60d0020;
        mResolveActivity.exported = true;
        mResolveActivity.enabled = true;
        mResolveInfo.activityInfo = mResolveActivity;
        mResolveInfo.priority = 0;
        mResolveInfo.preferredOrder = 0;
        mResolveInfo.match = 0;
        mResolveComponentName = new ComponentName(mAndroidApplication.packageName, ((ComponentInfo) (mResolveActivity)).name);
        hashmap4;
        JVM INSTR monitorexit ;
_L4:
label0:
        {
            if(!mPackages.containsKey(package1.packageName) && !mSharedLibraries.containsKey(package1.packageName))
                break label0;
            Slog.w("PackageManager", (new StringBuilder()).append("Application package ").append(package1.packageName).append(" already installed.  Skipping duplicate.").toString());
            mLastScanError = -5;
            package1 = null;
        }
        if(true) goto _L5; else goto _L6
_L6:
        File file1;
        File file2;
        SharedUserSetting sharedusersetting;
        file1 = new File(package1.applicationInfo.sourceDir);
        file2 = new File(package1.applicationInfo.publicSourceDir);
        sharedusersetting = null;
        if(!isSystemApp(package1)) {
            package1.mOriginalPackages = null;
            package1.mRealPackage = null;
            package1.mAdoptPermissions = null;
        }
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(package1.usesLibraries == null && package1.usesOptionalLibraries == null) goto _L8; else goto _L7
_L9:
        if(j1 >= i1)
            break MISSING_BLOCK_LABEL_724;
        s27 = (String)mSharedLibraries.get(package1.usesLibraries.get(j1));
        if(s27 != null)
            break MISSING_BLOCK_LABEL_706;
        Slog.e("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" requires unavailable shared library ").append((String)package1.usesLibraries.get(j1)).append("; failing!").toString());
        mLastScanError = -9;
        package1 = null;
          goto _L5
        exception;
        throw exception;
        mTmpSharedLibraries[k] = s27;
        k++;
        j1++;
          goto _L9
        if(package1.usesOptionalLibraries == null) goto _L11; else goto _L10
_L10:
        int k1 = package1.usesOptionalLibraries.size();
          goto _L12
_L107:
        int l1;
        if(l1 >= k1) goto _L14; else goto _L13
_L13:
        String s26 = (String)mSharedLibraries.get(package1.usesOptionalLibraries.get(l1));
        if(s26 == null) {
            Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" desires unavailable shared library ").append((String)package1.usesOptionalLibraries.get(l1)).append("; ignoring!").toString());
        } else {
            mTmpSharedLibraries[k] = s26;
            k++;
        }
          goto _L15
_L14:
        if(k > 0) {
            String as2[] = new String[k];
            package1.usesLibraryFiles = as2;
            System.arraycopy(mTmpSharedLibraries, 0, package1.usesLibraryFiles, 0, k);
        }
_L8:
        if(package1.mSharedUserId == null) goto _L17; else goto _L16
_L16:
        sharedusersetting = mSettings.getSharedUserLPw(package1.mSharedUserId, package1.applicationInfo.flags, true);
        if(sharedusersetting != null) goto _L17; else goto _L18
_L18:
        Slog.w("PackageManager", (new StringBuilder()).append("Creating application package ").append(package1.packageName).append(" for shared user failed").toString());
        mLastScanError = -4;
        package1 = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L17:
        packagesetting = null;
        s = null;
        if(package1.mOriginalPackages == null) goto _L20; else goto _L19
_L19:
        s25 = (String)mSettings.mRenamedPackages.get(package1.mRealPackage);
        if(!package1.mOriginalPackages.contains(s25)) goto _L22; else goto _L21
_L21:
        s = package1.mRealPackage;
        if(!package1.packageName.equals(s25))
            package1.setPackageName(s25);
_L20:
        if(mTransferedPackages.contains(package1.packageName))
            Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" was transferred to another, but its .apk remains").toString());
        Settings settings = mSettings;
        String s1 = package1.applicationInfo.nativeLibraryDir;
        int i2 = package1.applicationInfo.flags;
        packagesetting1 = settings.getPackageLPw(package1, packagesetting, s, sharedusersetting, file1, file2, s1, i2, true, false);
        if(packagesetting1 != null) goto _L24; else goto _L23
_L23:
        Slog.w("PackageManager", (new StringBuilder()).append("Creating application package ").append(package1.packageName).append(" failed").toString());
        mLastScanError = -4;
        package1 = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L22:
        int l8 = -1 + package1.mOriginalPackages.size();
_L108:
        if(l8 < 0) goto _L20; else goto _L25
_L25:
        packagesetting = mSettings.peekPackageLPr((String)package1.mOriginalPackages.get(l8));
        if(packagesetting == null) goto _L27; else goto _L26
_L26:
        if(verifyPackageUpdateLPr(packagesetting, package1)) goto _L29; else goto _L28
_L28:
        packagesetting = null;
          goto _L27
_L29:
        if(packagesetting.sharedUser == null || packagesetting.sharedUser.name.equals(package1.mSharedUserId)) goto _L20; else goto _L30
_L30:
        Slog.w("PackageManager", (new StringBuilder()).append("Unable to migrate data from ").append(((PackageSettingBase) (packagesetting)).name).append(" to ").append(package1.packageName).append(": old uid ").append(packagesetting.sharedUser.name).append(" differs from ").append(package1.mSharedUserId).toString());
        packagesetting = null;
          goto _L27
_L24:
        ApplicationInfo applicationinfo = package1.applicationInfo;
        applicationinfo.flags = applicationinfo.flags | 0x80000000 & ((GrantedPermissions) (packagesetting1)).pkgFlags;
        if(((PackageSettingBase) (packagesetting1)).origPackage != null) {
            String s24 = ((PackageSettingBase) (packagesetting)).name;
            package1.setPackageName(s24);
            reportSettingsProblem(5, (new StringBuilder()).append("New package ").append(((PackageSettingBase) (packagesetting1)).realName).append(" renamed to replace old package ").append(((PackageSettingBase) (packagesetting1)).name).toString());
            mTransferedPackages.add(((PackageSettingBase) (packagesetting)).name);
            packagesetting1.origPackage = null;
        }
        if(s != null)
            mTransferedPackages.add(package1.packageName);
        if(mSettings.isDisabledSystemPackageLPr(package1.packageName)) {
            ApplicationInfo applicationinfo2 = package1.applicationInfo;
            applicationinfo2.flags = 0x80 | applicationinfo2.flags;
        }
        package1.applicationInfo.uid = packagesetting1.appId;
        package1.mExtras = packagesetting1;
        if(verifySignaturesLP(packagesetting1, package1)) goto _L32; else goto _L31
_L31:
        if((i & 0x40) != 0) goto _L34; else goto _L33
_L33:
        package1 = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L34:
        ((PackageSettingBase) (packagesetting1)).signatures.mSignatures = package1.mSignatures;
        if(packagesetting1.sharedUser == null || compareSignatures(packagesetting1.sharedUser.signatures.mSignatures, package1.mSignatures) == 0) goto _L36; else goto _L35
_L35:
        Log.w("PackageManager", (new StringBuilder()).append("Signature mismatch for shared user : ").append(packagesetting1.sharedUser).toString());
        mLastScanError = -104;
        package1 = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L36:
        reportSettingsProblem(5, (new StringBuilder()).append("System package ").append(package1.packageName).append(" signature changed; retaining data.").toString());
_L32:
        if((j & 0x10) == 0) goto _L38; else goto _L37
_L37:
        int i8;
        l7 = package1.providers.size();
        i8 = 0;
_L111:
        if(i8 >= l7) goto _L38; else goto _L39
_L39:
        provider4 = (android.content.pm.PackageParser.Provider)package1.providers.get(i8);
        if(provider4.info.authority == null) goto _L41; else goto _L40
_L40:
        int j8;
        as1 = provider4.info.authority.split(";");
        j8 = 0;
_L110:
        k8 = as1.length;
        if(j8 >= k8) goto _L41; else goto _L42
_L42:
        if(!mProviders.containsKey(as1[j8])) goto _L44; else goto _L43
_L43:
        provider5 = (android.content.pm.PackageParser.Provider)mProviders.get(as1[j8]);
        stringbuilder8 = (new StringBuilder()).append("Can't install because provider name ").append(as1[j8]).append(" (in package ").append(package1.applicationInfo.packageName).append(") is already used by ");
        if(provider5 == null || provider5.getComponentName() == null) goto _L46; else goto _L45
_L45:
        String s23 = provider5.getComponentName().getPackageName();
_L109:
        Slog.w("PackageManager", stringbuilder8.append(s23).toString());
        mLastScanError = -13;
        package1 = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L38:
        if(package1.mAdoptPermissions == null) goto _L48; else goto _L47
_L47:
        int k7 = -1 + package1.mAdoptPermissions.size();
_L112:
        if(k7 < 0) goto _L48; else goto _L49
_L49:
        String s22 = (String)package1.mAdoptPermissions.get(k7);
        PackageSetting packagesetting2 = mSettings.peekPackageLPr(s22);
        if(packagesetting2 != null && verifyPackageUpdateLPr(packagesetting2, package1)) {
            Slog.i("PackageManager", (new StringBuilder()).append("Adopting permissions from ").append(s22).append(" to ").append(package1.packageName).toString());
            mSettings.transferPermissionsLPw(s22, package1.packageName);
        }
          goto _L50
_L48:
        hashmap;
        JVM INSTR monitorexit ;
        s2 = package1.packageName;
        l2 = file.lastModified();
        boolean flag;
        File file5;
        if((j & 4) != 0)
            flag = true;
        else
            flag = false;
        package1.applicationInfo.processName = fixProcessName(package1.applicationInfo.packageName, package1.applicationInfo.processName, package1.applicationInfo.uid);
        if(mPlatformPackage != package1) goto _L52; else goto _L51
_L51:
        file5 = Environment.getDataDirectory();
        file3 = new File(file5, "system");
        package1.applicationInfo.dataDir = file3.getPath();
_L64:
        s3 = file.getPath();
        if(package1.applicationInfo.nativeLibraryDir == null) goto _L54; else goto _L53
_L53:
        file4 = new File(package1.applicationInfo.nativeLibraryDir);
        s18 = file3.getCanonicalPath();
        if(!isSystemApp(package1) || isUpdatedSystemApp(package1)) goto _L56; else goto _L55
_L55:
        if(NativeLibraryHelper.removeNativeBinariesFromDirLI(file4))
            Log.i("PackageManager", (new StringBuilder()).append("removed obsolete native libraries for system package ").append(s3).toString());
_L54:
        package1.mScanPath = s3;
        if((j & 2) != 0)
            break MISSING_BLOCK_LABEL_3187;
        boolean flag1;
        boolean flag3;
        IOException ioexception;
        boolean flag4;
        ErrnoException errnoexception;
        boolean flag5;
        boolean flag6;
        String s19;
        boolean flag7;
        String s20;
        String s21;
        if((j & 0x80) != 0)
            flag3 = true;
        else
            flag3 = false;
        if(performDexOptLI(package1, flag, flag3) != -1)
            break MISSING_BLOCK_LABEL_3187;
        mLastScanError = -11;
        package1 = null;
          goto _L5
_L52:
        file3 = getDataPathForPackage(package1.packageName, 0);
        flag1 = false;
        if(!file3.exists()) goto _L58; else goto _L57
_L57:
        mOutPermissions[1] = 0;
        FileUtils.getPermissions(file3.getPath(), mOutPermissions);
        if(mOutPermissions[1] == package1.applicationInfo.uid) goto _L60; else goto _L59
_L59:
        flag7 = false;
        if(mOutPermissions[1] == 0 && mInstaller.fixUid(s2, package1.applicationInfo.uid, package1.applicationInfo.uid) >= 0) {
            flag7 = true;
            reportSettingsProblem(5, (new StringBuilder()).append("Package ").append(package1.packageName).append(" unexpectedly changed to uid 0; recovered to ").append(package1.applicationInfo.uid).toString());
        }
        if(flag7 || (i & 1) == 0 && (j & 0x100) == 0) goto _L62; else goto _L61
_L61:
label1:
        {
            if(mInstaller.remove(s2, 0) < 0)
                break MISSING_BLOCK_LABEL_2591;
            sUserManager.removePackageForAllUsers(s2);
            if((i & 1) != 0)
                s21 = "System package ";
            else
                s21 = "Third party package ";
            reportSettingsProblem(5, (new StringBuilder()).append(s21).append(package1.packageName).append(" has changed from uid: ").append(mOutPermissions[1]).append(" to ").append(package1.applicationInfo.uid).append("; old data erased").toString());
            flag7 = true;
            if(mInstaller.install(s2, package1.applicationInfo.uid, package1.applicationInfo.uid) != -1)
                break label1;
            reportSettingsProblem(5, (new StringBuilder()).append(s21).append(package1.packageName).append(" could not have data directory re-created after delete.").toString());
            mLastScanError = -4;
            package1 = null;
        }
        if(true) goto _L5; else goto _L63
_L63:
        sUserManager.installPackageForAllUsers(s2, package1.applicationInfo.uid);
        if(!flag7)
            mHasSystemUidErrors = true;
_L66:
        if(!flag7) {
            package1.applicationInfo.dataDir = (new StringBuilder()).append("/mismatched_uid/settings_").append(package1.applicationInfo.uid).append("/fs_").append(mOutPermissions[1]).toString();
            package1.applicationInfo.nativeLibraryDir = package1.applicationInfo.dataDir;
            s20 = (new StringBuilder()).append("Package ").append(package1.packageName).append(" has mismatched uid: ").append(mOutPermissions[1]).append(" on disk, ").append(package1.applicationInfo.uid).append(" in settings").toString();
            synchronized(mPackages) {
                mSettings.mReadMessages.append(s20);
                mSettings.mReadMessages.append('\n');
                flag1 = true;
                if(!((PackageSettingBase) (packagesetting1)).uidError)
                    reportSettingsProblem(6, s20);
            }
        }
_L60:
        package1.applicationInfo.dataDir = file3.getPath();
_L68:
        if(package1.applicationInfo.nativeLibraryDir == null && package1.applicationInfo.dataDir != null)
            if(((PackageSettingBase) (packagesetting1)).nativeLibraryPathString == null) {
                s19 = (new File(file3, "lib")).getPath();
                package1.applicationInfo.nativeLibraryDir = s19;
                packagesetting1.nativeLibraryPathString = s19;
            } else {
                package1.applicationInfo.nativeLibraryDir = ((PackageSettingBase) (packagesetting1)).nativeLibraryPathString;
            }
        packagesetting1.uidError = flag1;
          goto _L64
_L62:
        if(flag7) goto _L66; else goto _L65
_L65:
        mLastScanError = -24;
        package1 = null;
          goto _L5
        exception2;
        hashmap3;
        JVM INSTR monitorexit ;
        throw exception2;
_L58:
label2:
        {
            if(mInstaller.install(s2, package1.applicationInfo.uid, package1.applicationInfo.uid) >= 0)
                break label2;
            mLastScanError = -4;
            package1 = null;
        }
        if(true) goto _L5; else goto _L67
_L67:
        sUserManager.installPackageForAllUsers(s2, package1.applicationInfo.uid);
        if(file3.exists()) {
            package1.applicationInfo.dataDir = file3.getPath();
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Unable to create data directory: ").append(file3).toString());
            package1.applicationInfo.dataDir = null;
        }
          goto _L68
_L56:
        flag4 = file4.getParentFile().getCanonicalPath().equals(s18);
        if(!flag4)
            break MISSING_BLOCK_LABEL_3134;
        flag6 = OsConstants.S_ISLNK(Libcore.os.lstat(file4.getPath()).st_mode);
        flag5 = flag6;
_L69:
        if(!flag5)
            break MISSING_BLOCK_LABEL_3080;
        mInstaller.unlinkNativeLibraryDirectory(s18);
        NativeLibraryHelper.copyNativeBinariesIfNeededLI(file, file4);
          goto _L54
        errnoexception;
        flag5 = true;
          goto _L69
        try {
            Slog.i("PackageManager", (new StringBuilder()).append("Linking native library dir for ").append(s3).toString());
            mInstaller.linkNativeLibraryDirectory(s18, package1.applicationInfo.nativeLibraryDir);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) {
            Log.e("PackageManager", (new StringBuilder()).append("Unable to get canonical file ").append(ioexception.toString()).toString());
        }
          goto _L54
        if(mFactoryTest && package1.requestedPermissions.contains("android.permission.FACTORY_TEST")) {
            ApplicationInfo applicationinfo1 = package1.applicationInfo;
            applicationinfo1.flags = 0x10 | applicationinfo1.flags;
        }
        if((i & 2) != 0)
            killApplication(package1.applicationInfo.packageName, package1.applicationInfo.uid);
        hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        if((j & 1) != 0)
            mAppDirs.put(package1.mPath, package1);
        mSettings.insertPackageSettingLPw(packagesetting1, package1);
        mPackages.put(package1.applicationInfo.packageName, package1);
        mSettings.mPackagesToBeCleaned.remove(s2);
        if(l == 0L) goto _L71; else goto _L70
_L70:
        StringBuilder stringbuilder;
        int k2;
        android.content.pm.PackageParser.Provider provider;
        int i7;
        android.content.pm.PackageParser.Provider provider1;
        android.content.pm.PackageParser.Provider provider2;
        if(((PackageSettingBase) (packagesetting1)).firstInstallTime == 0L) {
            packagesetting1.lastUpdateTime = l;
            packagesetting1.firstInstallTime = l;
        } else
        if((j & 0x40) != 0)
            packagesetting1.lastUpdateTime = l;
_L83:
        j2 = package1.providers.size();
        stringbuilder = null;
        k2 = 0;
_L116:
        if(k2 >= j2) goto _L73; else goto _L72
_L72:
        provider = (android.content.pm.PackageParser.Provider)package1.providers.get(k2);
        provider.info.processName = fixProcessName(package1.applicationInfo.processName, provider.info.processName, package1.applicationInfo.uid);
        mProvidersByComponent.put(new ComponentName(((ComponentInfo) (provider.info)).packageName, ((ComponentInfo) (provider.info)).name), provider);
        boolean flag2 = provider.info.isSyncable;
        provider.syncable = flag2;
        if(provider.info.authority == null) goto _L75; else goto _L74
_L74:
        as = provider.info.authority.split(";");
        provider.info.authority = null;
        i7 = 0;
        provider1 = provider;
_L113:
        j7 = as.length;
        if(i7 >= j7) goto _L77; else goto _L76
_L76:
        StringBuilder stringbuilder1;
        int j3;
        StringBuilder stringbuilder2;
        int l3;
        StringBuilder stringbuilder3;
        int j4;
        StringBuilder stringbuilder4;
        int l4;
        StringBuilder stringbuilder5;
        int j5;
        StringBuilder stringbuilder6;
        int l5;
        String s17;
        if(i7 == 1 && provider1.syncable) {
            provider2 = new android.content.pm.PackageParser.Provider(provider1);
            provider2.syncable = false;
        } else {
            provider2 = provider1;
        }
        if(mProviders.containsKey(as[i7])) goto _L79; else goto _L78
_L78:
        mProviders.put(as[i7], provider2);
        if(provider2.info.authority != null) goto _L81; else goto _L80
_L80:
        provider2.info.authority = as[i7];
          goto _L82
        exception1;
        throw exception1;
_L71:
        if(((PackageSettingBase) (packagesetting1)).firstInstallTime == 0L) {
            packagesetting1.lastUpdateTime = l2;
            packagesetting1.firstInstallTime = l2;
        } else
        if((i & 0x40) != 0 && l2 != ((PackageSettingBase) (packagesetting1)).timeStamp)
            packagesetting1.lastUpdateTime = l2;
          goto _L83
_L81:
        provider2.info.authority = (new StringBuilder()).append(provider2.info.authority).append(";").append(as[i7]).toString();
          goto _L82
_L79:
        provider3 = (android.content.pm.PackageParser.Provider)mProviders.get(as[i7]);
        stringbuilder7 = (new StringBuilder()).append("Skipping provider name ").append(as[i7]).append(" (in package ").append(package1.applicationInfo.packageName).append("): name already used by ");
        if(provider3 == null || provider3.getComponentName() == null) goto _L85; else goto _L84
_L84:
        s17 = provider3.getComponentName().getPackageName();
_L114:
        Slog.w("PackageManager", stringbuilder7.append(s17).toString());
          goto _L82
_L75:
        if((i & 2) == 0)
            continue; /* Loop/switch isn't completed */
        String s16;
        if(stringbuilder == null)
            stringbuilder = new StringBuilder(256);
        else
            stringbuilder.append(' ');
        s16 = ((ComponentInfo) (provider.info)).name;
        stringbuilder.append(s16);
        continue; /* Loop/switch isn't completed */
_L117:
        i3 = package1.services.size();
        stringbuilder1 = null;
        j3 = 0;
_L118:
        if(j3 >= i3) goto _L87; else goto _L86
_L86:
        android.content.pm.PackageParser.Service service = (android.content.pm.PackageParser.Service)package1.services.get(j3);
        service.info.processName = fixProcessName(package1.applicationInfo.processName, service.info.processName, package1.applicationInfo.uid);
        mServices.addService(service);
        if((i & 2) != 0) {
            String s15;
            if(stringbuilder1 == null)
                stringbuilder1 = new StringBuilder(256);
            else
                stringbuilder1.append(' ');
            s15 = ((ComponentInfo) (service.info)).name;
            stringbuilder1.append(s15);
        }
          goto _L88
_L119:
        k3 = package1.receivers.size();
        stringbuilder2 = null;
        l3 = 0;
_L120:
        if(l3 >= k3) goto _L90; else goto _L89
_L89:
        android.content.pm.PackageParser.Activity activity1 = (android.content.pm.PackageParser.Activity)package1.receivers.get(l3);
        activity1.info.processName = fixProcessName(package1.applicationInfo.processName, activity1.info.processName, package1.applicationInfo.uid);
        mReceivers.addActivity(activity1, "receiver");
        if((i & 2) != 0) {
            String s14;
            if(stringbuilder2 == null)
                stringbuilder2 = new StringBuilder(256);
            else
                stringbuilder2.append(' ');
            s14 = ((ComponentInfo) (activity1.info)).name;
            stringbuilder2.append(s14);
        }
          goto _L91
_L121:
        i4 = package1.activities.size();
        stringbuilder3 = null;
        j4 = 0;
_L122:
        if(j4 >= i4) goto _L93; else goto _L92
_L92:
        android.content.pm.PackageParser.Activity activity = (android.content.pm.PackageParser.Activity)package1.activities.get(j4);
        activity.info.processName = fixProcessName(package1.applicationInfo.processName, activity.info.processName, package1.applicationInfo.uid);
        mActivities.addActivity(activity, "activity");
        if((i & 2) != 0) {
            String s13;
            if(stringbuilder3 == null)
                stringbuilder3 = new StringBuilder(256);
            else
                stringbuilder3.append(' ');
            s13 = ((ComponentInfo) (activity.info)).name;
            stringbuilder3.append(s13);
        }
          goto _L94
_L123:
        k4 = package1.permissionGroups.size();
        stringbuilder4 = null;
        l4 = 0;
_L124:
        if(l4 >= k4) goto _L96; else goto _L95
_L95:
        android.content.pm.PackageParser.PermissionGroup permissiongroup = (android.content.pm.PackageParser.PermissionGroup)package1.permissionGroups.get(l4);
        android.content.pm.PackageParser.PermissionGroup permissiongroup1 = (android.content.pm.PackageParser.PermissionGroup)mPermissionGroups.get(permissiongroup.info.name);
        if(permissiongroup1 == null) {
            mPermissionGroups.put(permissiongroup.info.name, permissiongroup);
            if((i & 2) != 0) {
                String s12;
                if(stringbuilder4 == null)
                    stringbuilder4 = new StringBuilder(256);
                else
                    stringbuilder4.append(' ');
                s12 = permissiongroup.info.name;
                stringbuilder4.append(s12);
            }
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Permission group ").append(permissiongroup.info.name).append(" from package ").append(permissiongroup.info.packageName).append(" ignored: original from ").append(permissiongroup1.info.packageName).toString());
            if((i & 2) != 0) {
                String s11;
                if(stringbuilder4 == null)
                    stringbuilder4 = new StringBuilder(256);
                else
                    stringbuilder4.append(' ');
                stringbuilder4.append("DUP:");
                s11 = permissiongroup.info.name;
                stringbuilder4.append(s11);
            }
        }
          goto _L97
_L125:
        i5 = package1.permissions.size();
        stringbuilder5 = null;
        j5 = 0;
_L126:
        if(j5 >= i5) goto _L99; else goto _L98
_L98:
        permission = (android.content.pm.PackageParser.Permission)package1.permissions.get(j5);
        HashMap hashmap2;
        String s5;
        int k6;
        int l6;
        String s7;
        String s8;
        String s9;
        String s10;
        if(permission.tree)
            hashmap2 = mSettings.mPermissionTrees;
        else
            hashmap2 = mSettings.mPermissions;
        permission.group = (android.content.pm.PackageParser.PermissionGroup)mPermissionGroups.get(permission.info.group);
        if(permission.info.group != null && permission.group == null) goto _L101; else goto _L100
_L100:
        s5 = permission.info.name;
        basepermission = (BasePermission)hashmap2.get(s5);
        if(basepermission == null) {
            s8 = permission.info.name;
            s9 = permission.info.packageName;
            basepermission = new BasePermission(s8, s9, 0);
            s10 = permission.info.name;
            hashmap2.put(s10, basepermission);
        }
        if(basepermission.perm != null) goto _L103; else goto _L102
_L102:
        if(basepermission.sourcePackage == null || basepermission.sourcePackage.equals(permission.info.packageName)) {
            BasePermission basepermission1 = findPermissionTreeLP(permission.info.name);
            if(basepermission1 == null || basepermission1.sourcePackage.equals(permission.info.packageName)) {
                basepermission.packageSetting = packagesetting1;
                basepermission.perm = permission;
                l6 = package1.applicationInfo.uid;
                basepermission.uid = l6;
                if((i & 2) != 0) {
                    if(stringbuilder5 == null)
                        stringbuilder5 = new StringBuilder(256);
                    else
                        stringbuilder5.append(' ');
                    s7 = permission.info.name;
                    stringbuilder5.append(s7);
                }
            } else {
                Slog.w("PackageManager", (new StringBuilder()).append("Permission ").append(permission.info.name).append(" from package ").append(permission.info.packageName).append(" ignored: base tree ").append(basepermission1.name).append(" is from package ").append(basepermission1.sourcePackage).toString());
            }
        } else {
            Slog.w("PackageManager", (new StringBuilder()).append("Permission ").append(permission.info.name).append(" from package ").append(permission.info.packageName).append(" ignored: original from ").append(basepermission.sourcePackage).toString());
        }
_L105:
        if(basepermission.perm == permission) {
            k6 = permission.info.protectionLevel;
            basepermission.protectionLevel = k6;
        }
        break; /* Loop/switch isn't completed */
_L103:
        if((i & 2) != 0) {
            String s6;
            if(stringbuilder5 == null)
                stringbuilder5 = new StringBuilder(256);
            else
                stringbuilder5.append(' ');
            stringbuilder5.append("DUP:");
            s6 = permission.info.name;
            stringbuilder5.append(s6);
        }
        if(true) goto _L105; else goto _L104
_L101:
        Slog.w("PackageManager", (new StringBuilder()).append("Permission ").append(permission.info.name).append(" from package ").append(permission.info.packageName).append(" ignored: no group ").append(permission.group).toString());
          goto _L104
_L127:
        k5 = package1.instrumentation.size();
        stringbuilder6 = null;
        l5 = 0;
_L128:
        if(l5 >= k5)
            break MISSING_BLOCK_LABEL_5638;
        android.content.pm.PackageParser.Instrumentation instrumentation = (android.content.pm.PackageParser.Instrumentation)package1.instrumentation.get(l5);
        instrumentation.info.packageName = package1.applicationInfo.packageName;
        instrumentation.info.sourceDir = package1.applicationInfo.sourceDir;
        instrumentation.info.publicSourceDir = package1.applicationInfo.publicSourceDir;
        instrumentation.info.dataDir = package1.applicationInfo.dataDir;
        instrumentation.info.nativeLibraryDir = package1.applicationInfo.nativeLibraryDir;
        mInstrumentation.put(instrumentation.getComponentName(), instrumentation);
        if((i & 2) != 0) {
            String s4;
            if(stringbuilder6 == null)
                stringbuilder6 = new StringBuilder(256);
            else
                stringbuilder6.append(' ');
            s4 = instrumentation.info.name;
            stringbuilder6.append(s4);
        }
          goto _L106
_L129:
        if(package1.protectedBroadcasts != null) {
            int i6 = package1.protectedBroadcasts.size();
            for(int j6 = 0; j6 < i6; j6++)
                mProtectedBroadcasts.add(package1.protectedBroadcasts.get(j6));

        }
        packagesetting1.setTimeStamp(l2);
        hashmap1;
        JVM INSTR monitorexit ;
          goto _L5
_L7:
        if(mTmpSharedLibraries == null || mTmpSharedLibraries.length < mSharedLibraries.size())
            mTmpSharedLibraries = new String[mSharedLibraries.size()];
        int k = 0;
        Exception exception;
        int i1;
        int j1;
        PackageSetting packagesetting;
        String s;
        PackageSetting packagesetting1;
        String s2;
        long l2;
        File file3;
        String s3;
        HashMap hashmap1;
        Exception exception1;
        int j2;
        int i3;
        int k3;
        int i4;
        int k4;
        int i5;
        int k5;
        android.content.pm.PackageParser.Permission permission;
        BasePermission basepermission;
        String as[];
        int j7;
        android.content.pm.PackageParser.Provider provider3;
        StringBuilder stringbuilder7;
        File file4;
        String s18;
        int l7;
        android.content.pm.PackageParser.Provider provider4;
        String as1[];
        int k8;
        android.content.pm.PackageParser.Provider provider5;
        StringBuilder stringbuilder8;
        String s25;
        String s27;
        if(package1.usesLibraries != null)
            i1 = package1.usesLibraries.size();
        else
            i1 = 0;
        j1 = 0;
          goto _L9
_L12:
        l1 = 0;
          goto _L107
_L15:
        l1++;
          goto _L107
_L11:
        k1 = 0;
          goto _L12
_L27:
        l8--;
          goto _L108
_L46:
        s23 = "?";
          goto _L109
_L44:
        j8++;
          goto _L110
_L41:
        i8++;
          goto _L111
_L50:
        k7--;
          goto _L112
_L82:
        i7++;
        provider1 = provider2;
          goto _L113
_L85:
        s17 = "?";
          goto _L114
_L77:
        provider = provider1;
        if(true) goto _L75; else goto _L115
_L115:
        k2++;
          goto _L116
_L73:
        if(stringbuilder == null);
          goto _L117
_L88:
        j3++;
          goto _L118
_L87:
        if(stringbuilder1 == null);
          goto _L119
_L91:
        l3++;
          goto _L120
_L90:
        if(stringbuilder2 == null);
          goto _L121
_L94:
        j4++;
          goto _L122
_L93:
        if(stringbuilder3 == null);
          goto _L123
_L97:
        l4++;
          goto _L124
_L96:
        if(stringbuilder4 == null);
          goto _L125
_L104:
        j5++;
          goto _L126
_L99:
        if(stringbuilder5 == null);
          goto _L127
_L106:
        l5++;
          goto _L128
        if(stringbuilder6 == null);
          goto _L129
    }

    private android.content.pm.PackageParser.Package scanPackageLI(File file, int i, int j, long l) {
        int k;
        PackageParser packageparser;
        android.content.pm.PackageParser.Package package1;
        mLastScanError = 1;
        String s = file.getPath();
        k = i | mDefParseFlags;
        packageparser = new PackageParser(s);
        packageparser.setSeparateProcesses(mSeparateProcesses);
        packageparser.setOnlyCoreApps(mOnlyCore);
        package1 = packageparser.parsePackage(file, s, mMetrics, k);
        if(package1 != null) goto _L2; else goto _L1
_L1:
        android.content.pm.PackageParser.Package package2;
        mLastScanError = packageparser.getParseError();
        package2 = null;
_L3:
        return package2;
_L2:
        PackageSetting packagesetting = null;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Settings settings;
        String s2;
        String s1 = (String)mSettings.mRenamedPackages.get(package1.packageName);
        if(package1.mOriginalPackages != null && package1.mOriginalPackages.contains(s1))
            packagesetting = mSettings.peekPackageLPr(s1);
        if(packagesetting == null)
            packagesetting = mSettings.peekPackageLPr(package1.packageName);
        settings = mSettings;
        if(packagesetting == null)
            break MISSING_BLOCK_LABEL_319;
        s2 = ((PackageSettingBase) (packagesetting)).name;
_L4:
        PackageSetting packagesetting1 = settings.getDisabledSystemPkgLPr(s2);
        if(packagesetting1 == null || (k & 1) == 0 || packagesetting == null || ((PackageSettingBase) (packagesetting)).codePath.equals(file))
            break MISSING_BLOCK_LABEL_513;
        if(package1.mVersionCode >= ((PackageSettingBase) (packagesetting)).versionCode)
            break MISSING_BLOCK_LABEL_337;
        Log.i("PackageManager", (new StringBuilder()).append("Package ").append(((PackageSettingBase) (packagesetting)).name).append(" at ").append(file).append(" ignored: updated version ").append(((PackageSettingBase) (packagesetting)).versionCode).append(" better than this ").append(package1.mVersionCode).toString());
        mLastScanError = -5;
        package2 = null;
          goto _L3
        s2 = package1.packageName;
          goto _L4
        Exception exception;
        exception;
        throw exception;
        synchronized(mPackages) {
            mPackages.remove(((PackageSettingBase) (packagesetting)).name);
        }
        Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(((PackageSettingBase) (packagesetting)).name).append(" at ").append(file).append("reverting from ").append(((PackageSettingBase) (packagesetting)).codePathString).append(": new version ").append(package1.mVersionCode).append(" better than installed ").append(((PackageSettingBase) (packagesetting)).versionCode).toString());
        InstallArgs installargs1 = createInstallArgs(packageFlagsToInstallFlags(packagesetting), ((PackageSettingBase) (packagesetting)).codePathString, ((PackageSettingBase) (packagesetting)).resourcePathString, ((PackageSettingBase) (packagesetting)).nativeLibraryPathString);
        synchronized(mInstaller) {
            installargs1.cleanUpResourcesLI();
        }
        synchronized(mPackages) {
            mSettings.enableSystemPackageLPw(((PackageSettingBase) (packagesetting)).name);
        }
        if(packagesetting1 != null)
            k |= 1;
        if(collectCertificatesLI(packageparser, packagesetting, package1, file, k))
            break MISSING_BLOCK_LABEL_600;
        Slog.w("PackageManager", (new StringBuilder()).append("Failed verifying certificates for package:").append(package1.packageName).toString());
        package2 = null;
          goto _L3
        exception1;
        hashmap2;
        JVM INSTR monitorexit ;
        throw exception1;
        exception2;
        installer1;
        JVM INSTR monitorexit ;
        throw exception2;
        exception3;
        hashmap3;
        JVM INSTR monitorexit ;
        throw exception3;
        boolean flag = false;
        if(packagesetting1 != null || packagesetting == null || (k & 0x40) == 0 || isSystemApp(packagesetting)) goto _L6; else goto _L5
_L5:
        if(compareSignatures(((PackageSettingBase) (packagesetting)).signatures.mSignatures, package1.mSignatures) == 0) goto _L8; else goto _L7
_L7:
        deletePackageLI(package1.packageName, true, 0, null, false);
        packagesetting = null;
_L6:
        if(packagesetting != null && !((PackageSettingBase) (packagesetting)).codePath.equals(((PackageSettingBase) (packagesetting)).resourcePath))
            k |= 0x10;
        String s3 = null;
        int i1;
        HashMap hashmap1;
        InstallArgs installargs;
        Installer installer;
        if((k & 0x10) != 0) {
            if(packagesetting != null && ((PackageSettingBase) (packagesetting)).resourcePathString != null)
                s3 = ((PackageSettingBase) (packagesetting)).resourcePathString;
            else
                Slog.e("PackageManager", (new StringBuilder()).append("Resource path not set for pkg : ").append(package1.packageName).toString());
        } else {
            s3 = package1.mScanPath;
        }
        setApplicationInfoPaths(package1, package1.mScanPath, s3);
        i1 = j | 8;
        package2 = scanPackageLI(package1, k, i1, l);
        if(!flag) goto _L3; else goto _L9
_L9:
        hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        grantPermissionsLPw(package1, true);
        mSettings.disableSystemPackageLPw(package1.packageName);
          goto _L3
_L8:
label0:
        {
            if(package1.mVersionCode >= ((PackageSettingBase) (packagesetting)).versionCode)
                break label0;
            flag = true;
        }
        if(true) goto _L6; else goto _L10
_L10:
        Slog.w("PackageManager", (new StringBuilder()).append("Package ").append(((PackageSettingBase) (packagesetting)).name).append(" at ").append(file).append("reverting from ").append(((PackageSettingBase) (packagesetting)).codePathString).append(": new version ").append(package1.mVersionCode).append(" better than installed ").append(((PackageSettingBase) (packagesetting)).versionCode).toString());
        installargs = createInstallArgs(packageFlagsToInstallFlags(packagesetting), ((PackageSettingBase) (packagesetting)).codePathString, ((PackageSettingBase) (packagesetting)).resourcePathString, ((PackageSettingBase) (packagesetting)).nativeLibraryPathString);
        installer = mInstaller;
        installer;
        JVM INSTR monitorenter ;
        installargs.cleanUpResourcesLI();
          goto _L6
    }

    static final void sendPackageBroadcast(String s, String s1, Bundle bundle, String s2, IIntentReceiver iintentreceiver, int i) {
        IActivityManager iactivitymanager = ActivityManagerNative.getDefault();
        if(iactivitymanager == null) goto _L2; else goto _L1
_L1:
        if(i != -1) goto _L4; else goto _L3
_L3:
        int ai[] = sUserManager.getUserIds();
_L9:
        int ai1[];
        int j;
        int k;
        ai1 = ai;
        j = ai1.length;
        k = 0;
_L8:
        if(k >= j) goto _L2; else goto _L5
_L5:
        int l = ai1[k];
        if(s1 == null) goto _L7; else goto _L6
_L6:
        Uri uri = Uri.fromParts("package", s1, null);
_L10:
        Intent intent;
        boolean flag;
        intent = new Intent(s, uri);
        if(bundle != null)
            intent.putExtras(bundle);
        if(s2 != null)
            intent.setPackage(s2);
        int i1 = intent.getIntExtra("android.intent.extra.UID", -1);
        if(i1 > 0 && l > 0)
            intent.putExtra("android.intent.extra.UID", UserId.getUid(l, UserId.getAppId(i1)));
        intent.addFlags(0x8000000);
        if(iintentreceiver == null)
            break MISSING_BLOCK_LABEL_209;
        flag = true;
_L11:
        iactivitymanager.broadcastIntent(null, intent, null, iintentreceiver, 0, null, null, null, flag, false, l);
        k++;
          goto _L8
_L4:
        ai = new int[1];
        ai[0] = i;
          goto _L9
        RemoteException remoteexception;
        remoteexception;
_L2:
        return;
_L7:
        uri = null;
          goto _L10
        flag = false;
          goto _L11
    }

    private void sendPackageChangedBroadcast(String s, boolean flag, ArrayList arraylist, int i) {
        Bundle bundle = new Bundle(4);
        bundle.putString("android.intent.extra.changed_component_name", (String)arraylist.get(0));
        String as[] = new String[arraylist.size()];
        arraylist.toArray(as);
        bundle.putStringArray("android.intent.extra.changed_component_name_list", as);
        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", flag);
        bundle.putInt("android.intent.extra.UID", i);
        sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", s, bundle, null, null, UserId.getUserId(i));
    }

    private void sendResourcesChangedBroadcast(boolean flag, ArrayList arraylist, int ai[], IIntentReceiver iintentreceiver) {
        int i = arraylist.size();
        if(i > 0) {
            Bundle bundle = new Bundle();
            bundle.putStringArray("android.intent.extra.changed_package_list", (String[])arraylist.toArray(new String[i]));
            if(ai != null)
                bundle.putIntArray("android.intent.extra.changed_uid_list", ai);
            String s;
            if(flag)
                s = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
            else
                s = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
            sendPackageBroadcast(s, null, bundle, null, iintentreceiver, -1);
        }
    }

    private static void setApplicationInfoPaths(android.content.pm.PackageParser.Package package1, String s, String s1) {
        package1.mScanPath = s;
        package1.mPath = s;
        package1.applicationInfo.sourceDir = s;
        package1.applicationInfo.publicSourceDir = s1;
    }

    private void setEnabledSetting(String s, String s1, int i, int j, int k) {
        int l;
        boolean flag;
        boolean flag1;
        String s2;
        HashMap hashmap;
        PackageSetting packagesetting;
        if(i != 0 && i != 1 && i != 2 && i != 3)
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid new component state: ").append(i).toString());
        l = Binder.getCallingUid();
        int i1 = mContext.checkCallingPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE");
        checkValidCaller(l, k);
        boolean flag2;
        Exception exception;
        IllegalArgumentException illegalargumentexception1;
        if(i1 == 0)
            flag = true;
        else
            flag = false;
        flag1 = false;
        if(s1 == null)
            flag2 = true;
        else
            flag2 = false;
        if(flag2)
            s2 = s;
        else
            s2 = s1;
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting != null)
            break MISSING_BLOCK_LABEL_232;
        if(s1 == null) {
            illegalargumentexception1 = new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
            throw illegalargumentexception1;
        }
        break MISSING_BLOCK_LABEL_190;
        exception;
        throw exception;
        IllegalArgumentException illegalargumentexception2 = new IllegalArgumentException((new StringBuilder()).append("Unknown component: ").append(s).append("/").append(s1).toString());
        throw illegalargumentexception2;
        if(!flag && !UserId.isSameApp(l, packagesetting.appId)) {
            SecurityException securityexception = new SecurityException((new StringBuilder()).append("Permission Denial: attempt to change component state from pid=").append(Binder.getCallingPid()).append(", uid=").append(l).append(", package uid=").append(packagesetting.appId).toString());
            throw securityexception;
        }
        if(s1 != null) goto _L2; else goto _L1
_L1:
        if(packagesetting.getEnabled(k) != i) goto _L4; else goto _L3
_L3:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L4:
        packagesetting.setEnabled(i, k);
_L12:
        int j1;
        ArrayList arraylist;
        mSettings.writePackageRestrictionsLPr(k);
        j1 = UserId.getUid(k, packagesetting.appId);
        arraylist = (ArrayList)mPendingBroadcasts.get(s);
        if(arraylist != null) goto _L7; else goto _L6
_L6:
        boolean flag3 = true;
_L20:
        if(flag3)
            arraylist = new ArrayList();
        if(!arraylist.contains(s2))
            arraylist.add(s2);
        if((j & 1) != 0) goto _L9; else goto _L8
_L8:
        flag1 = true;
        mPendingBroadcasts.remove(s);
_L15:
        hashmap;
        JVM INSTR monitorexit ;
        long l1;
        l1 = Binder.clearCallingIdentity();
        if(!flag1)
            break MISSING_BLOCK_LABEL_465;
        android.content.pm.PackageParser.Package package1;
        IllegalArgumentException illegalargumentexception;
        boolean flag4;
        if((j & 1) != 0)
            flag4 = true;
        else
            flag4 = false;
        sendPackageChangedBroadcast(s, flag4, arraylist, j1);
        Binder.restoreCallingIdentity(l1);
          goto _L5
_L2:
        package1 = packagesetting.pkg;
        if(package1 == null || !package1.hasComponentClassName(s1)) {
            if(package1.applicationInfo.targetSdkVersion >= 16) {
                illegalargumentexception = new IllegalArgumentException((new StringBuilder()).append("Component class ").append(s1).append(" does not exist in ").append(s).toString());
                throw illegalargumentexception;
            }
            Slog.w("PackageManager", (new StringBuilder()).append("Failed setComponentEnabledSetting: component class ").append(s1).append(" does not exist in ").append(s).toString());
        }
          goto _L10
_L16:
        Slog.e("PackageManager", (new StringBuilder()).append("Invalid new component state: ").append(i).toString());
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L18:
        if(packagesetting.enableComponentLPw(s1, k)) goto _L12; else goto _L11
_L11:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L19:
        if(packagesetting.disableComponentLPw(s1, k)) goto _L12; else goto _L13
_L13:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L17:
        if(packagesetting.restoreComponentLPw(s1, k)) goto _L12; else goto _L14
_L14:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
_L9:
        if(flag3)
            mPendingBroadcasts.put(s, arraylist);
        if(!mHandler.hasMessages(1))
            mHandler.sendEmptyMessageDelayed(1, 10000L);
          goto _L15
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l1);
        throw exception1;
_L5:
        return;
_L10:
        i;
        JVM INSTR tableswitch 0 2: default 588
    //                   0 654
    //                   1 620
    //                   2 637;
           goto _L16 _L17 _L18 _L19
_L7:
        flag3 = false;
          goto _L20
    }

    static String[] splitString(String s, char c) {
        int i = 1;
        int j = 0;
        do {
            int k = s.indexOf(c, j);
            if(k < 0)
                break;
            i++;
            j = k + 1;
        } while(true);
        String as[] = new String[i];
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        do {
            int k1 = s.indexOf(c, l);
            if(k1 >= 0) {
                as[i1] = s.substring(j1, k1);
                i1++;
                l = k1 + 1;
                j1 = l;
            } else {
                as[i1] = s.substring(j1, s.length());
                return as;
            }
        } while(true);
    }

    private void unloadAllContainers(Set set) {
        Iterator iterator = set.iterator();
_L2:
        AsecInstallArgs asecinstallargs;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        asecinstallargs = (AsecInstallArgs)iterator.next();
        Object obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        asecinstallargs.doPostDeleteLI(false);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void unloadMediaPackages(HashMap hashmap, int ai[], final boolean reportStatus) {
        ArrayList arraylist;
        ArrayList arraylist1;
        final Set keys;
        Iterator iterator;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        keys = hashmap.keySet();
        iterator = keys.iterator();
_L7:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        AsecInstallArgs asecinstallargs;
        String s;
        PackageRemovedInfo packageremovedinfo;
        asecinstallargs = (AsecInstallArgs)iterator.next();
        s = asecinstallargs.getPackageName();
        packageremovedinfo = new PackageRemovedInfo();
        Object obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        if(!deletePackageLI(s, false, 1, packageremovedinfo, false)) goto _L4; else goto _L3
_L3:
        arraylist.add(s);
_L5:
        obj;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        throw exception1;
_L4:
        Slog.e("PackageManager", (new StringBuilder()).append("Failed to delete pkg from sdcard : ").append(s).toString());
        arraylist1.add(asecinstallargs);
        if(true) goto _L5; else goto _L2
_L2:
        synchronized(mPackages) {
            mSettings.writeLPr();
        }
        if(arraylist.size() > 0) {
            sendResourcesChangedBroadcast(false, arraylist, ai, new android.content.IIntentReceiver.Stub() {

                public void performReceive(Intent intent, int j, String s1, Bundle bundle, boolean flag, boolean flag1) throws RemoteException {
                    PackageHandler packagehandler1 = mHandler;
                    int k;
                    Message message1;
                    if(reportStatus)
                        k = 1;
                    else
                        k = 0;
                    message1 = packagehandler1.obtainMessage(12, k, 1, keys);
                    mHandler.sendMessage(message1);
                }

                final PackageManagerService this$0;
                final Set val$keys;
                final boolean val$reportStatus;

             {
                this$0 = PackageManagerService.this;
                reportStatus = flag;
                keys = set;
                super();
            }
            });
        } else {
            PackageHandler packagehandler = mHandler;
            int i;
            Message message;
            if(reportStatus)
                i = 1;
            else
                i = 0;
            message = packagehandler.obtainMessage(12, i, -1, keys);
            mHandler.sendMessage(message);
        }
        return;
        exception;
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void updateExternalMediaStatusInner(boolean flag, boolean flag1, boolean flag2) {
        int ai[];
        HashSet hashset;
        HashMap hashmap;
        String as[];
        ai = null;
        hashset = new HashSet();
        hashmap = new HashMap();
        as = PackageHelper.getSecureContainerList();
        if(as != null && as.length != 0) goto _L2; else goto _L1
_L1:
        Log.i("PackageManager", "No secure containers on sdcard");
_L14:
        Exception exception;
        int j;
        int k;
        int k1;
        int ai1[];
        HashMap hashmap1;
        int i;
        int l;
        String s;
        String s1;
        PackageSetting packagesetting;
        AsecInstallArgs asecinstallargs;
        int l1;
        if(flag) {
            loadMediaPackages(hashmap, ai, hashset);
            startCleaningPackages();
        } else {
            unloadMediaPackages(hashmap, ai, flag1);
        }
        return;
_L2:
        ai1 = new int[as.length];
        hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        i = as.length;
        j = 0;
        k = 0;
_L16:
        if(j >= i) goto _L4; else goto _L3
_L3:
        s = as[j];
        s1 = getAsecPackageName(s);
        if(s1 != null) goto _L6; else goto _L5
_L5:
        hashset.add(s);
        k1 = k;
          goto _L7
_L6:
        packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
        if(packagesetting != null) goto _L9; else goto _L8
_L8:
        Log.i("PackageManager", (new StringBuilder()).append("Deleting container with no matching settings ").append(s).toString());
        hashset.add(s);
        k1 = k;
          goto _L7
_L9:
        if(!flag2 || flag || isExternal(packagesetting)) goto _L11; else goto _L10
_L10:
        k1 = k;
          goto _L7
_L11:
        asecinstallargs = new AsecInstallArgs(s, isForwardLocked(packagesetting));
        if(((PackageSettingBase) (packagesetting)).codePathString == null || !((PackageSettingBase) (packagesetting)).codePathString.equals(asecinstallargs.getCodePath())) goto _L13; else goto _L12
_L12:
        hashmap.put(asecinstallargs, ((PackageSettingBase) (packagesetting)).codePathString);
        l1 = packagesetting.appId;
        if(l1 == -1)
            break MISSING_BLOCK_LABEL_475;
        k1 = k + 1;
        ai1[k] = l1;
          goto _L7
_L15:
        hashmap1;
        JVM INSTR monitorexit ;
        throw exception;
_L13:
        Log.i("PackageManager", (new StringBuilder()).append("Deleting stale container for ").append(s).toString());
        hashset.add(s);
        break MISSING_BLOCK_LABEL_475;
_L4:
        if(k > 0) {
            Arrays.sort(ai1, 0, k);
            ai = new int[k];
            ai[0] = ai1[0];
            l = 1;
            int i1 = 0;
            while(l < k)  {
                int j1;
                if(ai1[l - 1] != ai1[l]) {
                    j1 = i1 + 1;
                    ai[i1] = ai1[l];
                } else {
                    j1 = i1;
                }
                l++;
                i1 = j1;
            }
        }
          goto _L14
        exception;
        k;
          goto _L15
_L7:
        j++;
        k = k1;
          goto _L16
        exception;
          goto _L15
        k1 = k;
          goto _L7
    }

    private void updatePermissionsLPw(String s, android.content.pm.PackageParser.Package package1, int i) {
        boolean flag = true;
        Iterator iterator = mSettings.mPermissionTrees.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            BasePermission basepermission2 = (BasePermission)iterator.next();
            if(basepermission2.packageSetting == null)
                basepermission2.packageSetting = (PackageSettingBase)mSettings.mPackages.get(basepermission2.sourcePackage);
            if(basepermission2.packageSetting == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Removing dangling permission tree: ").append(basepermission2.name).append(" from package ").append(basepermission2.sourcePackage).toString());
                iterator.remove();
            } else
            if(s != null && s.equals(basepermission2.sourcePackage) && (package1 == null || !hasPermission(package1, basepermission2.name))) {
                Slog.i("PackageManager", (new StringBuilder()).append("Removing old permission tree: ").append(basepermission2.name).append(" from package ").append(basepermission2.sourcePackage).toString());
                i |= 1;
                iterator.remove();
            }
        } while(true);
        Iterator iterator1 = mSettings.mPermissions.values().iterator();
        do {
            if(!iterator1.hasNext())
                break;
            BasePermission basepermission = (BasePermission)iterator1.next();
            if(basepermission.type == 2 && basepermission.packageSetting == null && basepermission.pendingInfo != null) {
                BasePermission basepermission1 = findPermissionTreeLP(basepermission.name);
                if(basepermission1 != null && basepermission1.perm != null) {
                    basepermission.packageSetting = basepermission1.packageSetting;
                    basepermission.perm = new android.content.pm.PackageParser.Permission(basepermission1.perm.owner, new PermissionInfo(basepermission.pendingInfo));
                    basepermission.perm.info.packageName = basepermission1.perm.info.packageName;
                    basepermission.perm.info.name = basepermission.name;
                    basepermission.uid = basepermission1.uid;
                }
            }
            if(basepermission.packageSetting == null)
                basepermission.packageSetting = (PackageSettingBase)mSettings.mPackages.get(basepermission.sourcePackage);
            if(basepermission.packageSetting == null) {
                Slog.w("PackageManager", (new StringBuilder()).append("Removing dangling permission: ").append(basepermission.name).append(" from package ").append(basepermission.sourcePackage).toString());
                iterator1.remove();
            } else
            if(s != null && s.equals(basepermission.sourcePackage) && (package1 == null || !hasPermission(package1, basepermission.name))) {
                Slog.i("PackageManager", (new StringBuilder()).append("Removing old permission: ").append(basepermission.name).append(" from package ").append(basepermission.sourcePackage).toString());
                i |= 1;
                iterator1.remove();
            }
        } while(true);
        if((i & 1) != 0) {
            Iterator iterator2 = mPackages.values().iterator();
            do {
                if(!iterator2.hasNext())
                    break;
                android.content.pm.PackageParser.Package package2 = (android.content.pm.PackageParser.Package)iterator2.next();
                if(package2 != package1) {
                    boolean flag1;
                    if((i & 4) != 0)
                        flag1 = flag;
                    else
                        flag1 = false;
                    grantPermissionsLPw(package2, flag1);
                }
            } while(true);
        }
        if(package1 != null) {
            if((i & 2) == 0)
                flag = false;
            grantPermissionsLPw(package1, flag);
        }
    }

    private void updateSettingsLI(android.content.pm.PackageParser.Package package1, String s, PackageInstalledInfo packageinstalledinfo) {
        int i;
        String s1;
        int j;
        i = 1;
        s1 = package1.packageName;
        synchronized(mPackages) {
            mSettings.setInstallStatus(s1, 0);
            mSettings.writeLPr();
        }
        j = moveDexFilesLI(package1);
        packageinstalledinfo.returnCode = j;
        if(j == i) goto _L2; else goto _L1
_L1:
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        Log.d("PackageManager", (new StringBuilder()).append("New package installed in ").append(package1.mPath).toString());
        HashMap hashmap1 = mPackages;
        hashmap1;
        JVM INSTR monitorenter ;
        String s2 = package1.packageName;
        if(package1.permissions.size() <= 0)
            i = 0;
        updatePermissionsLPw(s2, package1, i | 2);
        packageinstalledinfo.name = s1;
        packageinstalledinfo.uid = package1.applicationInfo.uid;
        packageinstalledinfo.pkg = package1;
        mSettings.setInstallStatus(s1, 1);
        mSettings.setInstallerPackageName(s1, s);
        packageinstalledinfo.returnCode = 1;
        mSettings.writeLPr();
        if(true) goto _L1; else goto _L3
_L3:
    }

    private boolean verifyPackageUpdateLPr(PackageSetting packagesetting, android.content.pm.PackageParser.Package package1) {
        boolean flag = false;
        if((1 & ((GrantedPermissions) (packagesetting)).pkgFlags) == 0)
            Slog.w("PackageManager", (new StringBuilder()).append("Unable to update from ").append(((PackageSettingBase) (packagesetting)).name).append(" to ").append(package1.packageName).append(": old package not in system partition").toString());
        else
        if(mPackages.get(((PackageSettingBase) (packagesetting)).name) != null)
            Slog.w("PackageManager", (new StringBuilder()).append("Unable to update from ").append(((PackageSettingBase) (packagesetting)).name).append(" to ").append(package1.packageName).append(": old package still exists").toString());
        else
            flag = true;
        return flag;
    }

    private boolean verifySignaturesLP(PackageSetting packagesetting, android.content.pm.PackageParser.Package package1) {
        boolean flag = false;
        if(((PackageSettingBase) (packagesetting)).signatures.mSignatures != null && compareSignatures(((PackageSettingBase) (packagesetting)).signatures.mSignatures, package1.mSignatures) != 0) {
            Slog.e("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" signatures do not match the previously installed version; ignoring!").toString());
            mLastScanError = -7;
        } else
        if(packagesetting.sharedUser != null && packagesetting.sharedUser.signatures.mSignatures != null && compareSignatures(packagesetting.sharedUser.signatures.mSignatures, package1.mSignatures) != 0) {
            Slog.e("PackageManager", (new StringBuilder()).append("Package ").append(package1.packageName).append(" has no signatures that match those in shared user ").append(packagesetting.sharedUser.name).append("; ignoring!").toString());
            mLastScanError = -8;
        } else {
            flag = true;
        }
        return flag;
    }

    public void addPackageToPreferred(String s) {
        Slog.w("PackageManager", "addPackageToPreferred: this is now a no-op");
    }

    public boolean addPermission(PermissionInfo permissioninfo) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = addPermissionLocked(permissioninfo, false);
        return flag;
    }

    public boolean addPermissionAsync(PermissionInfo permissioninfo) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = addPermissionLocked(permissioninfo, true);
        return flag;
    }

    boolean addPermissionLocked(PermissionInfo permissioninfo, boolean flag) {
        if(permissioninfo.labelRes == 0 && permissioninfo.nonLocalizedLabel == null)
            throw new SecurityException("Label must be specified in permission");
        BasePermission basepermission = checkPermissionTreeLP(permissioninfo.name);
        BasePermission basepermission1 = (BasePermission)mSettings.mPermissions.get(permissioninfo.name);
        boolean flag1;
        boolean flag2;
        int i;
        PermissionInfo permissioninfo1;
        if(basepermission1 == null)
            flag1 = true;
        else
            flag1 = false;
        flag2 = true;
        i = PermissionInfo.fixProtectionLevel(permissioninfo.protectionLevel);
        if(flag1) {
            basepermission1 = new BasePermission(permissioninfo.name, basepermission.sourcePackage, 2);
        } else {
            if(basepermission1.type != 2)
                throw new SecurityException((new StringBuilder()).append("Not allowed to modify non-dynamic permission ").append(permissioninfo.name).toString());
            if(basepermission1.protectionLevel == i && basepermission1.perm.owner.equals(basepermission.perm.owner) && basepermission1.uid == basepermission.uid && comparePermissionInfos(basepermission1.perm.info, permissioninfo))
                flag2 = false;
        }
        basepermission1.protectionLevel = i;
        permissioninfo1 = new PermissionInfo(permissioninfo);
        permissioninfo1.protectionLevel = i;
        basepermission1.perm = new android.content.pm.PackageParser.Permission(basepermission.perm.owner, permissioninfo1);
        basepermission1.perm.info.packageName = basepermission.perm.info.packageName;
        basepermission1.uid = basepermission.uid;
        if(flag1)
            mSettings.mPermissions.put(permissioninfo1.name, basepermission1);
        if(flag2)
            if(!flag)
                mSettings.writeLPr();
            else
                scheduleWriteSettingsLocked();
        return flag1;
    }

    public void addPreferredActivity(IntentFilter intentfilter, int i, ComponentName acomponentname[], ComponentName componentname) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
label0:
        {
            if(mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
                if(getUidTargetSdkVersionLockedLPr(Binder.getCallingUid()) < 8) {
                    Slog.w("PackageManager", (new StringBuilder()).append("Ignoring addPreferredActivity() from uid ").append(Binder.getCallingUid()).toString());
                    break label0;
                }
                mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
            }
            Slog.i("PackageManager", (new StringBuilder()).append("Adding preferred activity ").append(componentname).append(":").toString());
            intentfilter.dump(new LogPrinter(4, "PackageManager"), "  ");
            mSettings.mPreferredActivities.addFilter(new PreferredActivity(intentfilter, i, acomponentname, componentname));
            scheduleWriteSettingsLocked();
        }
        return;
    }

    public String[] canonicalToCurrentPackageNames(String as[]) {
        String as1[] = new String[as.length];
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        int i = -1 + as.length;
        while(i >= 0)  {
            String s = (String)mSettings.mRenamedPackages.get(as[i]);
            if(s == null)
                s = as[i];
            as1[i] = s;
            i--;
        }
        return as1;
    }

    public int checkPermission(String s, String s1) {
        byte byte0 = 0;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        if(package1 != null && package1.mExtras != null) {
            PackageSetting packagesetting = (PackageSetting)package1.mExtras;
            if(packagesetting.sharedUser == null ? ((GrantedPermissions) (packagesetting)).grantedPermissions.contains(s) : ((GrantedPermissions) (packagesetting.sharedUser)).grantedPermissions.contains(s))
                break MISSING_BLOCK_LABEL_122;
        }
        break MISSING_BLOCK_LABEL_102;
        Exception exception;
        exception;
        throw exception;
        if(isPermissionEnforcedLocked(s))
            break MISSING_BLOCK_LABEL_116;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_122;
        hashmap;
        JVM INSTR monitorexit ;
        byte0 = -1;
        return byte0;
    }

    public int checkSignatures(String s, String s1) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        android.content.pm.PackageParser.Package package2 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        int i;
        if(package1 == null || package1.mExtras == null || package2 == null || package2.mExtras == null)
            i = -4;
        else
            i = compareSignatures(package1.mSignatures, package2.mSignatures);
        return i;
    }

    public int checkUidPermission(String s, int i) {
        byte byte0 = 0;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Object obj = mSettings.getUserIdLPr(UserId.getAppId(i));
        HashSet hashset;
        if(obj == null ? (hashset = (HashSet)mSystemPermissions.get(i)) != null && hashset.contains(s) : ((GrantedPermissions)obj).grantedPermissions.contains(s))
            break MISSING_BLOCK_LABEL_111;
        break MISSING_BLOCK_LABEL_91;
        Exception exception;
        exception;
        throw exception;
        if(isPermissionEnforcedLocked(s))
            break MISSING_BLOCK_LABEL_105;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_111;
        hashmap;
        JVM INSTR monitorexit ;
        byte0 = -1;
        return byte0;
    }

    public int checkUidSignatures(int i, int j) {
        int k;
        int l;
        int i1;
        k = -4;
        l = UserId.getAppId(i);
        i1 = UserId.getAppId(j);
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Object obj = mSettings.getUserIdLPr(l);
        if(obj == null) goto _L2; else goto _L1
_L1:
        if(!(obj instanceof SharedUserSetting)) goto _L4; else goto _L3
_L3:
        Signature asignature[] = ((SharedUserSetting)obj).signatures.mSignatures;
_L13:
        Object obj1 = mSettings.getUserIdLPr(i1);
        if(obj1 == null) goto _L6; else goto _L5
_L5:
        if(!(obj1 instanceof SharedUserSetting)) goto _L8; else goto _L7
_L7:
        Signature asignature1[] = ((SharedUserSetting)obj1).signatures.mSignatures;
_L11:
        k = compareSignatures(asignature, asignature1);
          goto _L6
_L4:
        if(obj instanceof PackageSetting) {
            asignature = ((PackageSettingBase) ((PackageSetting)obj)).signatures.mSignatures;
            continue; /* Loop/switch isn't completed */
        }
          goto _L6
        Exception exception;
        exception;
        throw exception;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
_L8:
        if(!(obj1 instanceof PackageSetting)) goto _L10; else goto _L9
_L9:
        asignature1 = ((PackageSettingBase) ((PackageSetting)obj1)).signatures.mSignatures;
          goto _L11
_L10:
        hashmap;
        JVM INSTR monitorexit ;
_L6:
        return k;
        if(true) goto _L13; else goto _L12
_L12:
    }

    void cleanupInstallFailedPackage(PackageSetting packagesetting) {
        Slog.i("PackageManager", (new StringBuilder()).append("Cleaning up incompletely installed app: ").append(((PackageSettingBase) (packagesetting)).name).toString());
        int i = mInstaller.remove(((PackageSettingBase) (packagesetting)).name, 0);
        if(i < 0)
            Slog.w("PackageManager", (new StringBuilder()).append("Couldn't remove app data directory for package: ").append(((PackageSettingBase) (packagesetting)).name).append(", retcode=").append(i).toString());
        else
            sUserManager.removePackageForAllUsers(((PackageSettingBase) (packagesetting)).name);
        if(((PackageSettingBase) (packagesetting)).codePath != null && !((PackageSettingBase) (packagesetting)).codePath.delete())
            Slog.w("PackageManager", (new StringBuilder()).append("Unable to remove old code file: ").append(((PackageSettingBase) (packagesetting)).codePath).toString());
        if(((PackageSettingBase) (packagesetting)).resourcePath != null && !((PackageSettingBase) (packagesetting)).resourcePath.delete() && !((PackageSettingBase) (packagesetting)).resourcePath.equals(((PackageSettingBase) (packagesetting)).codePath))
            Slog.w("PackageManager", (new StringBuilder()).append("Unable to remove old code file: ").append(((PackageSettingBase) (packagesetting)).resourcePath).toString());
        mSettings.removePackageLPw(((PackageSettingBase) (packagesetting)).name);
    }

    public void clearApplicationUserData(final String packageName, final IPackageDataObserver observer, final int userId) {
        mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_USER_DATA", null);
        checkValidCaller(Binder.getCallingUid(), userId);
        mHandler.post(new Runnable() {

            public void run() {
                boolean flag;
                mHandler.removeCallbacks(this);
                synchronized(mInstallLock) {
                    flag = clearApplicationUserDataLI(packageName, userId);
                }
                clearExternalStorageDataSync(packageName, true);
                if(flag) {
                    DeviceStorageMonitorService devicestoragemonitorservice = (DeviceStorageMonitorService)ServiceManager.getService("devicestoragemonitor");
                    if(devicestoragemonitorservice != null)
                        devicestoragemonitorservice.updateMemory();
                }
                if(observer == null)
                    break MISSING_BLOCK_LABEL_96;
                observer.onRemoveCompleted(packageName, flag);
_L1:
                return;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
                RemoteException remoteexception;
                remoteexception;
                Log.i("PackageManager", "Observer no longer exists.");
                  goto _L1
            }

            final PackageManagerService this$0;
            final IPackageDataObserver val$observer;
            final String val$packageName;
            final int val$userId;

             {
                this$0 = PackageManagerService.this;
                packageName = s;
                userId = i;
                observer = ipackagedataobserver;
                super();
            }
        });
    }

    public void clearPackagePreferredActivities(String s) {
        int i = Binder.getCallingUid();
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
label0:
        {
            android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
            if((package1 == null || package1.applicationInfo.uid != i) && mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
                if(getUidTargetSdkVersionLockedLPr(Binder.getCallingUid()) < 8) {
                    Slog.w("PackageManager", (new StringBuilder()).append("Ignoring clearPackagePreferredActivities() from uid ").append(Binder.getCallingUid()).toString());
                    break label0;
                }
                mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
            }
            if(clearPackagePreferredActivitiesLPw(s))
                scheduleWriteSettingsLocked();
        }
        return;
    }

    boolean clearPackagePreferredActivitiesLPw(String s) {
        ArrayList arraylist = null;
        Iterator iterator = mSettings.mPreferredActivities.filterIterator();
        do {
            if(!iterator.hasNext())
                break;
            PreferredActivity preferredactivity1 = (PreferredActivity)iterator.next();
            if(preferredactivity1.mPref.mComponent.getPackageName().equals(s)) {
                if(arraylist == null)
                    arraylist = new ArrayList();
                arraylist.add(preferredactivity1);
            }
        } while(true);
        boolean flag;
        if(arraylist != null) {
            for(int i = 0; i < arraylist.size(); i++) {
                PreferredActivity preferredactivity = (PreferredActivity)arraylist.get(i);
                mSettings.mPreferredActivities.removeFilter(preferredactivity);
            }

            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public UserInfo createUser(String s, int i) {
        enforceSystemOrRoot("Only the system can create users");
        UserInfo userinfo = sUserManager.createUser(s, i);
        if(userinfo != null) {
            Intent intent = new Intent("android.intent.action.USER_ADDED");
            intent.putExtra("android.intent.extra.user_id", userinfo.id);
            mContext.sendBroadcast(intent, "android.permission.MANAGE_ACCOUNTS");
        }
        return userinfo;
    }

    public String[] currentToCanonicalPackageNames(String as[]) {
        String as1[] = new String[as.length];
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        int i = -1 + as.length;
        while(i >= 0)  {
            PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(as[i]);
            String s;
            if(packagesetting != null && ((PackageSettingBase) (packagesetting)).realName != null)
                s = ((PackageSettingBase) (packagesetting)).realName;
            else
                s = as[i];
            as1[i] = s;
            i--;
        }
        return as1;
    }

    public void deleteApplicationCacheFiles(final String packageName, final IPackageDataObserver observer) {
        mContext.enforceCallingOrSelfPermission("android.permission.DELETE_CACHE_FILES", null);
        final int userId = UserId.getCallingUserId();
        mHandler.post(new Runnable() {

            public void run() {
                boolean flag;
                mHandler.removeCallbacks(this);
                synchronized(mInstallLock) {
                    flag = deleteApplicationCacheFilesLI(packageName, userId);
                }
                clearExternalStorageDataSync(packageName, false);
                if(observer == null)
                    break MISSING_BLOCK_LABEL_72;
                observer.onRemoveCompleted(packageName, flag);
_L1:
                return;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
                RemoteException remoteexception;
                remoteexception;
                Log.i("PackageManager", "Observer no longer exists.");
                  goto _L1
            }

            final PackageManagerService this$0;
            final IPackageDataObserver val$observer;
            final String val$packageName;
            final int val$userId;

             {
                this$0 = PackageManagerService.this;
                packageName = s;
                userId = i;
                observer = ipackagedataobserver;
                super();
            }
        });
    }

    public void deletePackage(final String packageName, final IPackageDeleteObserver observer, final int flags) {
        mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        mHandler.post(new Runnable() {

            public void run() {
                int i;
                mHandler.removeCallbacks(this);
                i = deletePackageX(packageName, true, true, flags);
                if(observer == null)
                    break MISSING_BLOCK_LABEL_50;
                observer.packageDeleted(packageName, i);
_L1:
                return;
                RemoteException remoteexception;
                remoteexception;
                Log.i("PackageManager", "Observer no longer exists.");
                  goto _L1
            }

            final PackageManagerService this$0;
            final int val$flags;
            final IPackageDeleteObserver val$observer;
            final String val$packageName;

             {
                this$0 = PackageManagerService.this;
                packageName = s;
                flags = i;
                observer = ipackagedeleteobserver;
                super();
            }
        });
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ActivityManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).append(" without permission ").append("android.permission.DUMP").toString());
_L6:
        return;
_L2:
        DumpState dumpstate;
        String s;
        int i;
        dumpstate = new DumpState();
        s = null;
        i = 0;
_L4:
        HashMap hashmap;
        int j = as.length;
        String s9;
        if(i < j) {
            s9 = as[i];
            if(s9 != null && s9.length() > 0 && s9.charAt(0) == '-')
                break MISSING_BLOCK_LABEL_376;
        }
        int k = as.length;
        if(i < k) {
            String s8 = as[i];
            i + 1;
            Exception exception;
            Iterator iterator3;
            String s7;
            if("android".equals(s8) || s8.contains("."))
                s = s8;
            else
            if("l".equals(s8) || "libraries".equals(s8))
                dumpstate.setDump(1);
            else
            if("f".equals(s8) || "features".equals(s8))
                dumpstate.setDump(2);
            else
            if("r".equals(s8) || "resolvers".equals(s8))
                dumpstate.setDump(4);
            else
            if("perm".equals(s8) || "permissions".equals(s8))
                dumpstate.setDump(8);
            else
            if("pref".equals(s8) || "preferred".equals(s8))
                dumpstate.setDump(512);
            else
            if("preferred-xml".equals(s8))
                dumpstate.setDump(1024);
            else
            if("p".equals(s8) || "packages".equals(s8))
                dumpstate.setDump(16);
            else
            if("s".equals(s8) || "shared-users".equals(s8))
                dumpstate.setDump(32);
            else
            if("prov".equals(s8) || "providers".equals(s8))
                dumpstate.setDump(128);
            else
            if("m".equals(s8) || "messages".equals(s8))
                dumpstate.setDump(64);
            else
            if("v".equals(s8) || "verifiers".equals(s8))
                dumpstate.setDump(256);
        }
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(dumpstate.isDumping(256) && s == null) {
            if(dumpstate.onTitlePrinted())
                printwriter.println(" ");
            printwriter.println("Verifiers:");
            printwriter.print("  Required: ");
            printwriter.print(mRequiredVerifierPackage);
            printwriter.print(" (uid=");
            printwriter.print(getPackageUid(mRequiredVerifierPackage, 0));
            printwriter.println(")");
        }
        if(dumpstate.isDumping(1) && s == null) {
            if(dumpstate.onTitlePrinted())
                printwriter.println(" ");
            printwriter.println("Libraries:");
            for(iterator3 = mSharedLibraries.keySet().iterator(); iterator3.hasNext(); printwriter.println((String)mSharedLibraries.get(s7))) {
                s7 = (String)iterator3.next();
                printwriter.print("  ");
                printwriter.print(s7);
                printwriter.print(" -> ");
            }

        }
        break MISSING_BLOCK_LABEL_919;
        exception;
        throw exception;
        i++;
        if("-a".equals(s9)) goto _L4; else goto _L3
_L3:
label0:
        {
            if(!"-h".equals(s9))
                break label0;
            printwriter.println("Package manager dump options:");
            printwriter.println("  [-h] [-f] [cmd] ...");
            printwriter.println("    -f: print details of intent filters");
            printwriter.println("    -h: print this help");
            printwriter.println("  cmd may be one of:");
            printwriter.println("    l[ibraries]: list known shared libraries");
            printwriter.println("    f[ibraries]: list device features");
            printwriter.println("    r[esolvers]: dump intent resolvers");
            printwriter.println("    perm[issions]: dump permissions");
            printwriter.println("    pref[erred]: print preferred package settings");
            printwriter.println("    preferred-xml: print preferred package settings as xml");
            printwriter.println("    prov[iders]: dump content providers");
            printwriter.println("    p[ackages]: dump installed packages");
            printwriter.println("    s[hared-users]: dump shared user IDs");
            printwriter.println("    m[essages]: print collected runtime messages");
            printwriter.println("    v[erifiers]: print package verifier info");
            printwriter.println("    <package.name>: info about given package");
        }
        if(true) goto _L6; else goto _L5
_L5:
        if("-f".equals(s9))
            dumpstate.setOptionEnabled(1);
        else
            printwriter.println((new StringBuilder()).append("Unknown argument: ").append(s9).append("; use -h for help").toString());
          goto _L4
        if(dumpstate.isDumping(2) && s == null) {
            if(dumpstate.onTitlePrinted())
                printwriter.println(" ");
            printwriter.println("Features:");
            String s6;
            for(Iterator iterator2 = mAvailableFeatures.keySet().iterator(); iterator2.hasNext(); printwriter.println(s6)) {
                s6 = (String)iterator2.next();
                printwriter.print("  ");
            }

        }
        if(!dumpstate.isDumping(4)) goto _L8; else goto _L7
_L7:
        ActivityIntentResolver activityintentresolver = mActivities;
        if(!dumpstate.getTitlePrinted()) goto _L10; else goto _L9
_L9:
        String s3 = "\nActivity Resolver Table:";
_L26:
        ActivityIntentResolver activityintentresolver1;
        if(activityintentresolver.dump(printwriter, s3, "  ", s, dumpstate.isOptionEnabled(1)))
            dumpstate.setTitlePrinted(true);
        activityintentresolver1 = mReceivers;
        if(!dumpstate.getTitlePrinted()) goto _L12; else goto _L11
_L11:
        String s4 = "\nReceiver Resolver Table:";
_L27:
        ServiceIntentResolver serviceintentresolver;
        if(activityintentresolver1.dump(printwriter, s4, "  ", s, dumpstate.isOptionEnabled(1)))
            dumpstate.setTitlePrinted(true);
        serviceintentresolver = mServices;
        if(!dumpstate.getTitlePrinted()) goto _L14; else goto _L13
_L13:
        String s5 = "\nService Resolver Table:";
_L28:
        if(serviceintentresolver.dump(printwriter, s5, "  ", s, dumpstate.isOptionEnabled(1)))
            dumpstate.setTitlePrinted(true);
_L8:
        if(!dumpstate.isDumping(512)) goto _L16; else goto _L15
_L15:
        IntentResolver intentresolver = mSettings.mPreferredActivities;
        if(!dumpstate.getTitlePrinted()) goto _L18; else goto _L17
_L17:
        String s2 = "\nPreferred Activities:";
_L29:
        if(intentresolver.dump(printwriter, s2, "  ", s, dumpstate.isOptionEnabled(1)))
            dumpstate.setTitlePrinted(true);
_L16:
        if(dumpstate.isDumping(1024)) {
            printwriter.flush();
            FileOutputStream fileoutputstream = new FileOutputStream(filedescriptor);
            BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream);
            FastXmlSerializer fastxmlserializer = new FastXmlSerializer();
            boolean flag;
            Iterator iterator;
            android.content.pm.PackageParser.Provider provider1;
            try {
                fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
                fastxmlserializer.startDocument(null, Boolean.valueOf(true));
                fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                mSettings.writePreferredActivitiesLPr(fastxmlserializer);
                fastxmlserializer.endDocument();
                fastxmlserializer.flush();
            }
            catch(IllegalArgumentException illegalargumentexception) {
                printwriter.println((new StringBuilder()).append("Failed writing: ").append(illegalargumentexception).toString());
            }
            catch(IllegalStateException illegalstateexception) {
                printwriter.println((new StringBuilder()).append("Failed writing: ").append(illegalstateexception).toString());
            }
            catch(IOException ioexception4) {
                printwriter.println((new StringBuilder()).append("Failed writing: ").append(ioexception4).toString());
            }
        }
        if(dumpstate.isDumping(8))
            mSettings.dumpPermissionsLPr(printwriter, s, dumpstate);
        if(dumpstate.isDumping(128)) {
            flag = false;
            iterator = mProvidersByComponent.values().iterator();
            do {
                if(!iterator.hasNext())
                    break;
                provider1 = (android.content.pm.PackageParser.Provider)iterator.next();
                if(s == null || s.equals(((ComponentInfo) (provider1.info)).packageName)) {
                    if(!flag) {
                        if(dumpstate.onTitlePrinted())
                            printwriter.println(" ");
                        printwriter.println("Registered ContentProviders:");
                        flag = true;
                    }
                    printwriter.print("  ");
                    printwriter.print(provider1.getComponentShortName());
                    printwriter.println(":");
                    printwriter.print("    ");
                    printwriter.println(provider1.toString());
                }
            } while(true);
            boolean flag1 = false;
            Iterator iterator1 = mProviders.entrySet().iterator();
            do {
                if(!iterator1.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
                android.content.pm.PackageParser.Provider provider = (android.content.pm.PackageParser.Provider)entry.getValue();
                if(s == null || s.equals(((ComponentInfo) (provider.info)).packageName)) {
                    if(!flag1) {
                        if(dumpstate.onTitlePrinted())
                            printwriter.println(" ");
                        printwriter.println("ContentProvider Authorities:");
                        flag1 = true;
                    }
                    printwriter.print("  [");
                    printwriter.print((String)entry.getKey());
                    printwriter.println("]:");
                    printwriter.print("    ");
                    printwriter.println(provider.toString());
                    if(provider.info != null && provider.info.applicationInfo != null) {
                        String s1 = provider.info.applicationInfo.toString();
                        printwriter.print("      applicationInfo=");
                        printwriter.println(s1);
                    }
                }
            } while(true);
        }
        if(dumpstate.isDumping(16))
            mSettings.dumpPackagesLPr(printwriter, s, dumpstate);
        if(dumpstate.isDumping(32))
            mSettings.dumpSharedUsersLPr(printwriter, s, dumpstate);
        if(!dumpstate.isDumping(64) || s != null) goto _L20; else goto _L19
_L19:
        File file;
        if(dumpstate.onTitlePrinted())
            printwriter.println(" ");
        mSettings.dumpReadMessagesLPr(printwriter, dumpstate);
        printwriter.println(" ");
        printwriter.println("Package warning messages:");
        file = getSettingsProblemFile();
        FileInputStream fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(file);
        byte abyte0[] = new byte[fileinputstream1.available()];
        fileinputstream1.read(abyte0);
        printwriter.print(new String(abyte0));
        if(fileinputstream1 == null) goto _L20; else goto _L21
_L21:
        FileNotFoundException filenotfoundexception;
        IOException ioexception1;
        Exception exception1;
        FileNotFoundException filenotfoundexception1;
        try {
            fileinputstream1.close();
        }
        catch(IOException ioexception) { }
_L20:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
        exception1;
_L23:
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_1945;
        try {
            fileinputstream.close();
        }
        catch(IOException ioexception2) { }
        throw exception1;
        filenotfoundexception1;
_L25:
        if(fileinputstream == null) goto _L20; else goto _L22
_L22:
        fileinputstream.close();
          goto _L20
_L24:
        if(fileinputstream != null)
            fileinputstream.close();
          goto _L20
        exception1;
        fileinputstream = fileinputstream1;
          goto _L23
        ioexception1;
        fileinputstream = fileinputstream1;
          goto _L24
        filenotfoundexception;
        fileinputstream = fileinputstream1;
          goto _L25
_L10:
        s3 = "Activity Resolver Table:";
          goto _L26
_L12:
        s4 = "Receiver Resolver Table:";
          goto _L27
_L14:
        s5 = "Service Resolver Table:";
          goto _L28
_L18:
        s2 = "Preferred Activities:";
          goto _L29
        IOException ioexception3;
        ioexception3;
          goto _L24
    }

    public void enterSafeMode() {
        enforceSystemOrRoot("Only the system can request entering safe mode");
        if(!mSystemReady)
            mSafeMode = true;
    }

    ResolveInfo findPreferredActivity(Intent intent, String s, int i, List list, int j, int k) {
        if(sUserManager.exists(k)) goto _L2; else goto _L1
_L1:
        ResolveInfo resolveinfo = null;
_L13:
        return resolveinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        int i2;
        int j2;
        if(intent.getSelector() != null)
            intent = intent.getSelector();
        IntentResolver intentresolver = mSettings.mPreferredActivities;
        Exception exception;
        boolean flag;
        List list1;
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        PreferredActivity preferredactivity;
        ActivityInfo activityinfo;
        ResolveInfo resolveinfo1;
        if((0x10000 & i) != 0)
            flag = true;
        else
            flag = false;
        list1 = intentresolver.queryIntent(intent, s, flag, k);
        if(list1 == null || list1.size() <= 0) goto _L4; else goto _L3
_L3:
        l = 0;
        i1 = list.size();
        for(j1 = 0; j1 < i1; j1++) {
            resolveinfo1 = (ResolveInfo)list.get(j1);
            if(resolveinfo1.match > l)
                l = resolveinfo1.match;
        }

        k1 = l & 0xfff0000;
        l1 = list1.size();
        i2 = 0;
_L14:
        if(i2 >= l1) goto _L4; else goto _L5
_L5:
        preferredactivity = (PreferredActivity)list1.get(i2);
        if(preferredactivity.mPref.mMatch == k1) goto _L7; else goto _L6
_L7:
        activityinfo = getActivityInfo(preferredactivity.mPref.mComponent, i, k);
        if(activityinfo != null) goto _L9; else goto _L8
_L8:
        Slog.w("PackageManager", (new StringBuilder()).append("Removing dangling preferred activity: ").append(preferredactivity.mPref.mComponent).toString());
        mSettings.mPreferredActivities.removeFilter(preferredactivity);
          goto _L6
        exception;
        throw exception;
_L9:
        j2 = 0;
_L15:
        if(j2 >= i1) goto _L6; else goto _L10
_L10:
        resolveinfo = (ResolveInfo)list.get(j2);
        if(!resolveinfo.activityInfo.applicationInfo.packageName.equals(activityinfo.applicationInfo.packageName) || !((ComponentInfo) (resolveinfo.activityInfo)).name.equals(((ComponentInfo) (activityinfo)).name))
            break MISSING_BLOCK_LABEL_452;
        if(preferredactivity.mPref.sameSet(list, j)) goto _L12; else goto _L11
_L11:
        Slog.i("PackageManager", (new StringBuilder()).append("Result set changed, dropping preferred activity for ").append(intent).append(" type ").append(s).toString());
        mSettings.mPreferredActivities.removeFilter(preferredactivity);
        resolveinfo = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L13
_L12:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L13
_L4:
        hashmap;
        JVM INSTR monitorexit ;
        resolveinfo = null;
          goto _L13
_L6:
        i2++;
          goto _L14
        j2++;
          goto _L15
    }

    public void finishPackageInstall(int i) {
        enforceSystemOrRoot("Only the system is allowed to finish installs");
        Message message = mHandler.obtainMessage(9, i, 0);
        mHandler.sendMessage(message);
    }

    public void freeStorage(final long freeStorageSize, final IntentSender pi) {
        mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", null);
        mHandler.post(new Runnable() {

            public void run() {
                mHandler.removeCallbacks(this);
                int i = mInstaller.freeCache(freeStorageSize);
                if(i < 0)
                    Slog.w("PackageManager", "Couldn't clear application caches");
                if(pi == null)
                    break MISSING_BLOCK_LABEL_63;
                int j;
                if(i >= 0)
                    j = 1;
                else
                    j = 0;
                pi.sendIntent(null, j, null, null, null);
_L1:
                return;
                android.content.IntentSender.SendIntentException sendintentexception;
                sendintentexception;
                Slog.i("PackageManager", "Failed to send pending intent");
                  goto _L1
            }

            final PackageManagerService this$0;
            final long val$freeStorageSize;
            final IntentSender val$pi;

             {
                this$0 = PackageManagerService.this;
                freeStorageSize = l;
                pi = intentsender;
                super();
            }
        });
    }

    public void freeStorageAndNotify(final long freeStorageSize, final IPackageDataObserver observer) {
        mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", null);
        mHandler.post(new Runnable() {

            public void run() {
                int i;
                mHandler.removeCallbacks(this);
                i = mInstaller.freeCache(freeStorageSize);
                if(i < 0)
                    Slog.w("PackageManager", "Couldn't clear application caches");
                if(observer == null) goto _L2; else goto _L1
_L1:
                IPackageDataObserver ipackagedataobserver = observer;
                if(i < 0) goto _L4; else goto _L3
_L3:
                boolean flag = true;
_L5:
                ipackagedataobserver.onRemoveCompleted(null, flag);
_L2:
                return;
_L4:
                flag = false;
                  goto _L5
                RemoteException remoteexception;
                remoteexception;
                Slog.w("PackageManager", "RemoveException when invoking call back");
                  goto _L2
            }

            final PackageManagerService this$0;
            final long val$freeStorageSize;
            final IPackageDataObserver val$observer;

             {
                this$0 = PackageManagerService.this;
                freeStorageSize = l;
                observer = ipackagedataobserver;
                super();
            }
        });
    }

    PackageInfo generatePackageInfo(android.content.pm.PackageParser.Package package1, int i, int j) {
        PackageInfo packageinfo;
        if(!sUserManager.exists(j))
            packageinfo = null;
        else
        if((i & 0x2000) != 0) {
            packageinfo = PackageParser.generatePackageInfo(package1, null, i, 0L, 0L, null, false, 0, j);
        } else {
            PackageSetting packagesetting = (PackageSetting)package1.mExtras;
            if(packagesetting == null) {
                packageinfo = null;
            } else {
                Object obj;
                ApplicationInfo applicationinfo;
                boolean flag;
                if(packagesetting.sharedUser != null)
                    obj = packagesetting.sharedUser;
                else
                    obj = packagesetting;
                packageinfo = PackageParser.generatePackageInfo(package1, ((GrantedPermissions) (obj)).gids, i, ((PackageSettingBase) (packagesetting)).firstInstallTime, ((PackageSettingBase) (packagesetting)).lastUpdateTime, ((GrantedPermissions) (obj)).grantedPermissions, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
                packageinfo.applicationInfo.enabledSetting = packagesetting.getEnabled(j);
                applicationinfo = packageinfo.applicationInfo;
                if(packageinfo.applicationInfo.enabledSetting == 0 || packageinfo.applicationInfo.enabledSetting == 1)
                    flag = true;
                else
                    flag = false;
                applicationinfo.enabled = flag;
            }
        }
        return packageinfo;
    }

    public ActivityInfo getActivityInfo(ComponentName componentname, int i, int j) {
        ActivityInfo activityinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return activityinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Activity activity = (android.content.pm.PackageParser.Activity)mActivities.mActivities.get(componentname);
        if(activity == null || !mSettings.isEnabledLPr(activity.info, i, j)) goto _L4; else goto _L3
_L3:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(componentname.getPackageName());
        if(packagesetting != null) goto _L5; else goto _L1
_L5:
        break MISSING_BLOCK_LABEL_101;
        Exception exception;
        exception;
        throw exception;
        activityinfo = PackageParser.generateActivityInfo(activity, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        if(!mResolveComponentName.equals(componentname)) goto _L7; else goto _L6
_L6:
        activityinfo = mResolveActivity;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L7:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public List getAllPermissionGroups(int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        arraylist = new ArrayList(mPermissionGroups.size());
        for(Iterator iterator = mPermissionGroups.values().iterator(); iterator.hasNext(); arraylist.add(PackageParser.generatePermissionGroupInfo((android.content.pm.PackageParser.PermissionGroup)iterator.next(), i)));
        break MISSING_BLOCK_LABEL_75;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        return arraylist;
    }

    public int getApplicationEnabledSetting(String s, int i) {
        if(sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
        int j = 2;
_L4:
        return j;
_L2:
        checkValidCaller(Binder.getCallingUid(), i);
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        j = mSettings.getApplicationEnabledSettingLPr(s, i);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public ApplicationInfo getApplicationInfo(String s, int i, int j) {
        ApplicationInfo applicationinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return applicationinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null) goto _L4; else goto _L3
_L3:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting != null) goto _L5; else goto _L1
_L5:
        break MISSING_BLOCK_LABEL_78;
        Exception exception;
        exception;
        throw exception;
        applicationinfo = PackageParser.generateApplicationInfo(package1, i, packagesetting.getStopped(j), packagesetting.getEnabled(j));
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        if(!"android".equals(s) && !"system".equals(s)) goto _L7; else goto _L6
_L6:
        applicationinfo = mAndroidApplication;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L7:
        if((i & 0x2000) == 0) goto _L9; else goto _L8
_L8:
        applicationinfo = generateApplicationInfoFromSettingsLPw(s, i, j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L9:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public int getComponentEnabledSetting(ComponentName componentname, int i) {
        if(sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
        int j = 2;
_L4:
        return j;
_L2:
        checkValidCaller(Binder.getCallingUid(), i);
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        j = mSettings.getComponentEnabledSettingLPr(componentname, i);
        if(true) goto _L4; else goto _L3
_L3:
    }

    File getDataPathForUser(int i) {
        return new File((new StringBuilder()).append(mUserAppDataDir.getAbsolutePath()).append(File.separator).append(i).toString());
    }

    public int getInstallLocation() {
        return android.provider.Settings.System.getInt(mContext.getContentResolver(), "default_install_location", 0);
    }

    public ParceledListSlice getInstalledApplications(int i, String s, int j) {
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        ParceledListSlice parceledlistslice = null;
_L9:
        return parceledlistslice;
_L2:
        String as[];
        int i1;
        int j1;
        String s1;
        ApplicationInfo applicationinfo;
        PackageSetting packagesetting;
        parceledlistslice = new ParceledListSlice();
        boolean flag;
        HashMap hashmap;
        Exception exception;
        int k;
        int l;
        if((i & 0x2000) != 0)
            flag = true;
        else
            flag = false;
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(!flag) goto _L4; else goto _L3
_L3:
        as = (String[])mSettings.mPackages.keySet().toArray(new String[mSettings.mPackages.size()]);
_L10:
        Arrays.sort(as);
        k = getContinuationPoint(as, s);
        l = as.length;
        i1 = k;
_L13:
        if(i1 >= l)
            break MISSING_BLOCK_LABEL_305;
        j1 = i1 + 1;
        s1 = as[i1];
        applicationinfo = null;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
        if(!flag) goto _L6; else goto _L5
_L5:
        if(packagesetting != null)
            applicationinfo = generateApplicationInfoFromSettingsLPw(((PackageSettingBase) (packagesetting)).name, i, j);
_L12:
        if(applicationinfo == null || !parceledlistslice.append(applicationinfo)) goto _L8; else goto _L7
_L7:
        if(j1 == l)
            parceledlistslice.setLastSlice(true);
          goto _L9
        exception;
        throw exception;
_L4:
        as = (String[])mPackages.keySet().toArray(new String[mPackages.size()]);
          goto _L10
_L6:
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        if(package1 == null || packagesetting == null) goto _L12; else goto _L11
_L11:
        ApplicationInfo applicationinfo1 = PackageParser.generateApplicationInfo(package1, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
        applicationinfo = applicationinfo1;
          goto _L12
_L8:
        i1 = j1;
          goto _L13
        j1 = i1;
          goto _L7
    }

    public ParceledListSlice getInstalledPackages(int i, String s) {
        int i1;
        int j1;
        ParceledListSlice parceledlistslice = new ParceledListSlice();
        boolean flag;
        int j;
        HashMap hashmap;
        String as[];
        int k;
        int l;
        String s1;
        PackageInfo packageinfo;
        android.content.pm.PackageParser.Package package1;
        PackageSetting packagesetting;
        if((i & 0x2000) != 0)
            flag = true;
        else
            flag = false;
        j = UserId.getCallingUserId();
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(flag)
            as = (String[])mSettings.mPackages.keySet().toArray(new String[mSettings.mPackages.size()]);
        else
            as = (String[])mPackages.keySet().toArray(new String[mPackages.size()]);
        Arrays.sort(as);
        k = getContinuationPoint(as, s);
        l = as.length;
        i1 = k;
_L6:
        if(i1 >= l) goto _L2; else goto _L1
_L1:
        j1 = i1 + 1;
        s1 = as[i1];
        packageinfo = null;
        if(flag) {
            packagesetting = (PackageSetting)mSettings.mPackages.get(s1);
            if(packagesetting != null)
                packageinfo = generatePackageInfoFromSettingsLPw(((PackageSettingBase) (packagesetting)).name, i, j);
        } else {
            package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
            if(package1 != null)
                packageinfo = generatePackageInfo(package1, i, j);
        }
        if(packageinfo == null || !Injector.addPackageToSlice(parceledlistslice, packageinfo, i)) goto _L4; else goto _L3
_L3:
        if(j1 == l)
            parceledlistslice.setLastSlice(true);
        return parceledlistslice;
_L2:
        j1 = i1;
        if(true) goto _L3; else goto _L4
_L4:
        i1 = j1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public String getInstallerPackageName(String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        String s1 = mSettings.getInstallerPackageNameLPr(s);
        return s1;
    }

    public InstrumentationInfo getInstrumentationInfo(ComponentName componentname, int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        InstrumentationInfo instrumentationinfo = PackageParser.generateInstrumentationInfo((android.content.pm.PackageParser.Instrumentation)mInstrumentation.get(componentname), i);
        return instrumentationinfo;
    }

    public String getNameForUid(int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        String s;
        Object obj = mSettings.getUserIdLPr(UserId.getAppId(i));
        if(obj instanceof SharedUserSetting) {
            SharedUserSetting sharedusersetting = (SharedUserSetting)obj;
            s = (new StringBuilder()).append(sharedusersetting.name).append(":").append(sharedusersetting.userId).toString();
            break MISSING_BLOCK_LABEL_107;
        }
        if(obj instanceof PackageSetting) {
            s = ((PackageSettingBase) ((PackageSetting)obj)).name;
            break MISSING_BLOCK_LABEL_107;
        }
        break MISSING_BLOCK_LABEL_102;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        s = null;
        return s;
    }

    public int[] getPackageGids(String s) {
        int ai[];
        synchronized(mPackages) {
            android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
            if(package1 != null) {
                PackageSetting packagesetting = (PackageSetting)package1.mExtras;
                SharedUserSetting sharedusersetting = packagesetting.sharedUser;
                if(sharedusersetting != null)
                    ai = ((GrantedPermissions) (sharedusersetting)).gids;
                else
                    ai = ((GrantedPermissions) (packagesetting)).gids;
                if(!isPermissionEnforcedLocked("android.permission.READ_EXTERNAL_STORAGE"))
                    ai = appendInts(ai, ((BasePermission)mSettings.mPermissions.get("android.permission.READ_EXTERNAL_STORAGE")).gids);
                break MISSING_BLOCK_LABEL_120;
            }
        }
        ai = new int[0];
          goto _L1
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L1:
        return ai;
    }

    public PackageInfo getPackageInfo(String s, int i, int j) {
        PackageInfo packageinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return packageinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null) goto _L4; else goto _L3
_L3:
        packageinfo = generatePackageInfo(package1, i, j);
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L4:
        if((i & 0x2000) == 0) goto _L1; else goto _L5
_L5:
        packageinfo = generatePackageInfoFromSettingsLPw(s, i, j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public void getPackageSizeInfo(String s, IPackageStatsObserver ipackagestatsobserver) {
        mContext.enforceCallingOrSelfPermission("android.permission.GET_PACKAGE_SIZE", null);
        PackageStats packagestats = new PackageStats(s);
        Message message = mHandler.obtainMessage(5);
        message.obj = new MeasureParams(packagestats, ipackagestatsobserver);
        mHandler.sendMessage(message);
    }

    public int getPackageUid(String s, int i) {
        int j = -1;
        if(sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null)
            break MISSING_BLOCK_LABEL_69;
        j = UserId.getUid(i, package1.applicationInfo.uid);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting != null && packagesetting.pkg != null && packagesetting.pkg.applicationInfo != null) goto _L4; else goto _L3
_L3:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L4:
        android.content.pm.PackageParser.Package package2 = packagesetting.pkg;
        if(package2 != null)
            j = UserId.getUid(i, package2.applicationInfo.uid);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public String[] getPackagesForUid(int i) {
        int j = UserId.getAppId(i);
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        String as[];
        Object obj = mSettings.getUserIdLPr(j);
        if(obj instanceof SharedUserSetting) {
            SharedUserSetting sharedusersetting = (SharedUserSetting)obj;
            as = new String[sharedusersetting.packages.size()];
            Iterator iterator = sharedusersetting.packages.iterator();
            int l;
            for(int k = 0; iterator.hasNext(); k = l) {
                l = k + 1;
                as[k] = ((PackageSettingBase) ((PackageSetting)iterator.next())).name;
            }

            break MISSING_BLOCK_LABEL_156;
        }
        if(obj instanceof PackageSetting) {
            PackageSetting packagesetting = (PackageSetting)obj;
            as = new String[1];
            as[0] = ((PackageSettingBase) (packagesetting)).name;
            break MISSING_BLOCK_LABEL_156;
        }
        break MISSING_BLOCK_LABEL_151;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        as = null;
        return as;
    }

    public PermissionGroupInfo getPermissionGroupInfo(String s, int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        PermissionGroupInfo permissiongroupinfo = PackageParser.generatePermissionGroupInfo((android.content.pm.PackageParser.PermissionGroup)mPermissionGroups.get(s), i);
        return permissiongroupinfo;
    }

    public PermissionInfo getPermissionInfo(String s, int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        BasePermission basepermission = (BasePermission)mSettings.mPermissions.get(s);
        PermissionInfo permissioninfo;
        if(basepermission != null)
            permissioninfo = generatePermissionInfo(basepermission, i);
        else
            permissioninfo = null;
        return permissioninfo;
    }

    public List getPersistentApplications(int i) {
        ArrayList arraylist = new ArrayList();
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mPackages.values().iterator();
        int j = UserId.getCallingUserId();
        do {
            if(!iterator.hasNext())
                break;
            android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)iterator.next();
            if(package1.applicationInfo != null && (8 & package1.applicationInfo.flags) != 0 && (!mSafeMode || isSystemApp(package1))) {
                PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(package1.packageName);
                Exception exception;
                boolean flag;
                int k;
                if(packagesetting != null)
                    flag = packagesetting.getStopped(j);
                else
                    flag = false;
                if(packagesetting != null)
                    k = packagesetting.getEnabled(j);
                else
                    k = 0;
                arraylist.add(PackageParser.generateApplicationInfo(package1, i, flag, k, j));
            }
        } while(true);
        break MISSING_BLOCK_LABEL_180;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        return arraylist;
    }

    public int getPreferredActivities(List list, List list1, String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mSettings.mPreferredActivities.filterIterator();
        do {
            if(!iterator.hasNext())
                break;
            PreferredActivity preferredactivity = (PreferredActivity)iterator.next();
            if(s == null || preferredactivity.mPref.mComponent.getPackageName().equals(s)) {
                if(list != null)
                    list.add(new IntentFilter(preferredactivity));
                if(list1 != null)
                    list1.add(preferredactivity.mPref.mComponent);
            }
        } while(true);
        break MISSING_BLOCK_LABEL_115;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        return 0;
    }

    public List getPreferredPackages(int i) {
        return new ArrayList();
    }

    public ProviderInfo getProviderInfo(ComponentName componentname, int i, int j) {
        ProviderInfo providerinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return providerinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Provider provider = (android.content.pm.PackageParser.Provider)mProvidersByComponent.get(componentname);
        if(provider == null || !mSettings.isEnabledLPr(provider.info, i, j)) goto _L1; else goto _L3
_L3:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(componentname.getPackageName());
        if(packagesetting != null) goto _L4; else goto _L1
_L4:
        break MISSING_BLOCK_LABEL_98;
        Exception exception;
        exception;
        throw exception;
        providerinfo = PackageParser.generateProviderInfo(provider, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public ActivityInfo getReceiverInfo(ComponentName componentname, int i, int j) {
        ActivityInfo activityinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return activityinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Activity activity = (android.content.pm.PackageParser.Activity)mReceivers.mActivities.get(componentname);
        if(activity == null || !mSettings.isEnabledLPr(activity.info, i, j)) goto _L1; else goto _L3
_L3:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(componentname.getPackageName());
        if(packagesetting != null) goto _L4; else goto _L1
_L4:
        break MISSING_BLOCK_LABEL_101;
        Exception exception;
        exception;
        throw exception;
        activityinfo = PackageParser.generateActivityInfo(activity, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public ServiceInfo getServiceInfo(ComponentName componentname, int i, int j) {
        ServiceInfo serviceinfo = null;
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        return serviceinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Service service = (android.content.pm.PackageParser.Service)mServices.mServices.get(componentname);
        if(service == null || !mSettings.isEnabledLPr(service.info, i, j)) goto _L1; else goto _L3
_L3:
        PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(componentname.getPackageName());
        if(packagesetting != null) goto _L4; else goto _L1
_L4:
        break MISSING_BLOCK_LABEL_101;
        Exception exception;
        exception;
        throw exception;
        serviceinfo = PackageParser.generateServiceInfo(service, i, packagesetting.getStopped(j), packagesetting.getEnabled(j), j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public FeatureInfo[] getSystemAvailableFeatures() {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Collection collection = mAvailableFeatures.values();
        int i = collection.size();
        FeatureInfo afeatureinfo[];
        if(i > 0) {
            afeatureinfo = new FeatureInfo[i + 1];
            collection.toArray(afeatureinfo);
            FeatureInfo featureinfo = new FeatureInfo();
            featureinfo.reqGlEsVersion = SystemProperties.getInt("ro.opengles.version", 0);
            afeatureinfo[i] = featureinfo;
        } else {
            afeatureinfo = null;
        }
        return afeatureinfo;
    }

    public String[] getSystemSharedLibraryNames() {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Set set = mSharedLibraries.keySet();
        int i = set.size();
        String as[];
        if(i > 0) {
            as = new String[i];
            set.toArray(as);
        } else {
            as = null;
        }
        return as;
    }

    public int getUidForSharedUser(String s) {
        int i = -1;
        if(s != null) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        SharedUserSetting sharedusersetting = mSettings.getSharedUserLPw(s, 0, false);
        if(sharedusersetting != null) goto _L3; else goto _L1
_L3:
        break MISSING_BLOCK_LABEL_45;
        Exception exception;
        exception;
        throw exception;
        i = sharedusersetting.userId;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public UserInfo getUser(int i) {
        enforceSystemOrRoot("Only the system can remove users");
        return sUserManager.getUser(i);
    }

    public List getUsers() {
        enforceSystemOrRoot("Only the system can query users");
        return sUserManager.getUsers();
    }

    public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
        mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can read the verifier device identity");
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        VerifierDeviceIdentity verifierdeviceidentity = mSettings.getVerifierDeviceIdentityLPw();
        return verifierdeviceidentity;
    }

    public void grantPermission(String s, String s1) {
        mContext.enforceCallingOrSelfPermission("android.permission.GRANT_REVOKE_PERMISSIONS", null);
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
        break MISSING_BLOCK_LABEL_71;
        Exception exception;
        exception;
        throw exception;
        BasePermission basepermission;
        PackageSetting packagesetting;
        basepermission = (BasePermission)mSettings.mPermissions.get(s1);
        if(basepermission == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown permission: ").append(s).toString());
        if(!package1.requestedPermissions.contains(s1))
            throw new SecurityException((new StringBuilder()).append("Package ").append(s).append(" has not requested permission ").append(s1).toString());
        if((0x20 & basepermission.protectionLevel) == 0)
            throw new SecurityException((new StringBuilder()).append("Permission ").append(s1).append(" is not a development permission").toString());
        packagesetting = (PackageSetting)package1.mExtras;
        if(packagesetting != null)
            break MISSING_BLOCK_LABEL_235;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_307;
        Object obj;
        if(packagesetting.sharedUser == null)
            break MISSING_BLOCK_LABEL_300;
        obj = packagesetting.sharedUser;
_L1:
        if(((GrantedPermissions) (obj)).grantedPermissions.add(s1)) {
            if(((PackageSettingBase) (packagesetting)).haveGids)
                obj.gids = appendInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
            mSettings.writeLPr();
        }
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_307;
        obj = packagesetting;
          goto _L1
    }

    public boolean hasSystemFeature(String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = mAvailableFeatures.containsKey(s);
        return flag;
    }

    public boolean hasSystemUidErrors() {
        return mHasSystemUidErrors;
    }

    public void installPackage(Uri uri, IPackageInstallObserver ipackageinstallobserver, int i) {
        installPackage(uri, ipackageinstallobserver, i, null);
    }

    public void installPackage(Uri uri, IPackageInstallObserver ipackageinstallobserver, int i, String s) {
        installPackageWithVerification(uri, ipackageinstallobserver, i, s, null, null, null);
    }

    public void installPackageWithVerification(Uri uri, IPackageInstallObserver ipackageinstallobserver, int i, String s, Uri uri1, ManifestDigest manifestdigest, ContainerEncryptionParams containerencryptionparams) {
        mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", null);
        int j = Binder.getCallingUid();
        int k;
        Message message;
        if(j == 2000 || j == 0)
            k = i | 0x20;
        else
            k = i & 0xffffffdf;
        message = mHandler.obtainMessage(5);
        message.obj = new InstallParams(uri, ipackageinstallobserver, k, s, uri1, manifestdigest, containerencryptionparams);
        mHandler.sendMessage(message);
    }

    public boolean isFirstBoot() {
        boolean flag;
        if(!mRestoredSettings)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isPermissionEnforced(String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = isPermissionEnforcedLocked(s);
        return flag;
    }

    public boolean isProtectedBroadcast(String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = mProtectedBroadcasts.contains(s);
        return flag;
    }

    public boolean isSafeMode() {
        return mSafeMode;
    }

    public boolean isStorageLow() {
        long l = Binder.clearCallingIdentity();
        boolean flag = ((DeviceStorageMonitorService)ServiceManager.getService("devicestoragemonitor")).isMemoryLow();
        Binder.restoreCallingIdentity(l);
        return flag;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void movePackage(String s, IPackageMoveObserver ipackagemoveobserver, int i) {
        byte byte0;
        int j;
        int k;
        mContext.enforceCallingOrSelfPermission("android.permission.MOVE_PACKAGE", null);
        byte0 = 1;
        j = 0;
        k = 0;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null) {
            byte0 = -2;
        } else {
label0:
            {
                if(package1.applicationInfo == null || !isSystemApp(package1))
                    break label0;
                Slog.w("PackageManager", "Cannot move system application");
                byte0 = -3;
            }
        }
_L4:
        if(byte0 == 1) goto _L2; else goto _L1
_L1:
        processPendingMove(new MoveParams(null, ipackagemoveobserver, 0, s, null, -1), byte0);
_L8:
        hashmap;
        JVM INSTR monitorexit ;
        return;
        if(true) goto _L4; else goto _L3
_L3:
        if((i & 2) == 0 || (i & 1) == 0) goto _L6; else goto _L5
_L5:
        Slog.w("PackageManager", "Ambigous flags specified for move location.");
        byte0 = -5;
_L7:
        if(byte0 == 1)
            package1.mOperationPending = true;
          goto _L4
        Exception exception;
        exception;
        throw exception;
_L6:
        if((i & 2) != 0)
            k = 8;
        else
            k = 16;
        if(!isExternal(package1))
            break MISSING_BLOCK_LABEL_359;
        j = 8;
_L9:
        if(k == j) {
            Slog.w("PackageManager", "No move required. Trying to move to same location");
            byte0 = -5;
        } else
        if(isForwardLocked(package1)) {
            j |= 1;
            k |= 1;
        }
          goto _L7
_L2:
        Message message = mHandler.obtainMessage(5);
        InstallArgs installargs = createInstallArgs(j, package1.applicationInfo.sourceDir, package1.applicationInfo.publicSourceDir, package1.applicationInfo.nativeLibraryDir);
        String s1 = package1.applicationInfo.dataDir;
        int l = package1.applicationInfo.uid;
        message.obj = new MoveParams(installargs, ipackagemoveobserver, k, s, s1, l);
        mHandler.sendMessage(message);
          goto _L8
        j = 16;
          goto _L9
    }

    public String nextPackageToClean(String s) {
        String s1 = null;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(isExternalMediaAvailable()) {
            if(s != null)
                mSettings.mPackagesToBeCleaned.remove(s);
            if(mSettings.mPackagesToBeCleaned.size() > 0)
                s1 = (String)mSettings.mPackagesToBeCleaned.get(0);
        }
        return s1;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j) throws RemoteException {
        boolean flag;
        try {
            flag = super.onTransact(i, parcel, parcel1, j);
        }
        catch(RuntimeException runtimeexception) {
            if(!(runtimeexception instanceof SecurityException) && !(runtimeexception instanceof IllegalArgumentException))
                Slog.e("PackageManager", "Package Manager Crash", runtimeexception);
            throw runtimeexception;
        }
        return flag;
    }

    public void performBootDexOpt() {
        ArrayList arraylist = null;
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist1;
        if(mDeferredDexOpt.size() <= 0)
            break MISSING_BLOCK_LABEL_42;
        arraylist1 = new ArrayList(mDeferredDexOpt);
        mDeferredDexOpt.clear();
        arraylist = arraylist1;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
        Exception exception;
        exception;
_L3:
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
_L1:
        if(arraylist != null) {
            int i = 0;
            while(i < arraylist.size())  {
                android.content.pm.PackageParser.Package package1;
                if(!isFirstBoot())
                    try {
                        IActivityManager iactivitymanager = ActivityManagerNative.getDefault();
                        Resources resources = mContext.getResources();
                        Object aobj[] = new Object[2];
                        aobj[0] = Integer.valueOf(i + 1);
                        aobj[1] = Integer.valueOf(arraylist.size());
                        iactivitymanager.showBootMessage(resources.getString(0x10403e4, aobj), true);
                    }
                    catch(RemoteException remoteexception) { }
                package1 = (android.content.pm.PackageParser.Package)arraylist.get(i);
                synchronized(mInstallLock) {
                    if(!package1.mDidDexOpt)
                        performDexOptLI(package1, false, false);
                }
                i++;
            }
        }
        return;
        exception;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public boolean performDexOpt(String s) {
        boolean flag;
        flag = false;
        enforceSystemOrRoot("Only the system can request dexopt be performed");
        if(mNoDexOpt) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null || package1.mDidDexOpt)
            continue; /* Loop/switch isn't completed */
        break MISSING_BLOCK_LABEL_62;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        Object obj = mInstallLock;
        obj;
        JVM INSTR monitorenter ;
        if(performDexOptLI(package1, false, false) == 1)
            flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public List queryContentProviders(String s, int i, int j) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mProvidersByComponent.values().iterator();
        if(s == null) goto _L2; else goto _L1
_L1:
        int j1 = UserId.getUserId(i);
        int l = j1;
_L13:
        Object obj = null;
_L18:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        android.content.pm.PackageParser.Provider provider;
        PackageSetting packagesetting;
        provider = (android.content.pm.PackageParser.Provider)iterator.next();
        packagesetting = (PackageSetting)mSettings.mPackages.get(provider.owner.packageName);
        if(provider.info.authority == null || s != null && (!provider.info.processName.equals(s) || !UserId.isSameApp(provider.info.applicationInfo.uid, i)) || !mSettings.isEnabledLPr(provider.info, j, l) || mSafeMode && (1 & provider.info.applicationInfo.flags) == 0) goto _L6; else goto _L5
_L5:
        if(obj != null) goto _L8; else goto _L7
_L7:
        Object obj1 = new ArrayList(3);
_L17:
        if(packagesetting == null) goto _L10; else goto _L9
_L9:
        boolean flag = packagesetting.getStopped(l);
_L14:
        if(packagesetting == null) goto _L12; else goto _L11
_L11:
        int i1 = packagesetting.getEnabled(l);
_L15:
        ((ArrayList) (obj1)).add(PackageParser.generateProviderInfo(provider, j, flag, i1, l));
        break MISSING_BLOCK_LABEL_308;
_L2:
        int k = UserId.getCallingUserId();
        l = k;
          goto _L13
_L10:
        flag = false;
          goto _L14
_L12:
        i1 = 0;
          goto _L15
_L4:
        hashmap;
        JVM INSTR monitorexit ;
        if(obj != null)
            Collections.sort(((List) (obj)), mProviderInitOrderSorter);
        return ((List) (obj));
        Exception exception;
        exception;
_L16:
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
        exception;
        obj;
        if(true) goto _L16; else goto _L8
_L8:
        obj1 = obj;
          goto _L17
_L6:
        obj1 = obj;
        obj = obj1;
          goto _L18
    }

    public List queryInstrumentation(String s, int i) {
        ArrayList arraylist = new ArrayList();
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mInstrumentation.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            android.content.pm.PackageParser.Instrumentation instrumentation = (android.content.pm.PackageParser.Instrumentation)iterator.next();
            if(s == null || s.equals(instrumentation.info.targetPackage))
                arraylist.add(PackageParser.generateInstrumentationInfo(instrumentation, i));
        } while(true);
        break MISSING_BLOCK_LABEL_94;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        return arraylist;
    }

    public List queryIntentActivities(Intent intent, String s, int i, int j) {
        Object obj;
        if(!sUserManager.exists(j)) {
            obj = null;
        } else {
label0:
            {
                ComponentName componentname = intent.getComponent();
                if(componentname == null && intent.getSelector() != null) {
                    intent = intent.getSelector();
                    componentname = intent.getComponent();
                }
                if(componentname == null)
                    break label0;
                obj = new ArrayList(1);
                ActivityInfo activityinfo = getActivityInfo(componentname, i, j);
                if(activityinfo != null) {
                    ResolveInfo resolveinfo = new ResolveInfo();
                    resolveinfo.activityInfo = activityinfo;
                    ((List) (obj)).add(resolveinfo);
                }
            }
        }
_L1:
        return ((List) (obj));
        hashmap;
        JVM INSTR monitorenter ;
        String s1;
        s1 = intent.getPackage();
        if(s1 != null)
            break MISSING_BLOCK_LABEL_154;
        obj = mActivities.queryIntent(intent, s, i, j);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        if(package1 == null) goto _L3; else goto _L2
_L2:
        ActivityIntentResolver activityintentresolver = mActivities;
        ArrayList arraylist = package1.activities;
        obj = activityintentresolver.queryIntentForPackage(intent, s, i, arraylist, j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L3:
        obj = new ArrayList();
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public List queryIntentActivityOptions(ComponentName componentname, Intent aintent[], String as[], Intent intent, String s, int i, int j) {
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        List list = null;
_L10:
        return list;
_L2:
        String s1;
        int k;
        int k2;
        s1 = intent.getAction();
        list = queryIntentActivities(intent, s, i | 0x40, j);
        k = 0;
        if(aintent == null)
            break MISSING_BLOCK_LABEL_377;
        k2 = 0;
_L4:
        Intent intent1;
        if(k2 >= aintent.length)
            break MISSING_BLOCK_LABEL_377;
        intent1 = aintent[k2];
        if(intent1 != null)
            break; /* Loop/switch isn't completed */
_L7:
        k2++;
        if(true) goto _L4; else goto _L3
_L3:
        String s3;
        ResolveInfo resolveinfo2;
        ComponentName componentname1;
        s3 = intent1.getAction();
        if(s1 != null && s1.equals(s3))
            s3 = null;
        resolveinfo2 = null;
        componentname1 = intent1.getComponent();
        if(componentname1 != null) goto _L6; else goto _L5
_L5:
        ActivityInfo activityinfo1;
label0:
        {
            int l2;
            int i3;
            ResolveInfo resolveinfo3;
            String s4;
            if(as != null)
                s4 = as[k2];
            else
                s4 = null;
            resolveinfo2 = resolveIntent(intent1, s4, i, j);
            if(resolveinfo2 != null) {
                if(resolveinfo2 != mResolveInfo);
                activityinfo1 = resolveinfo2.activityInfo;
                componentname1 = new ComponentName(activityinfo1.applicationInfo.packageName, ((ComponentInfo) (activityinfo1)).name);
                break label0;
            }
        }
          goto _L7
_L6:
        activityinfo1 = getActivityInfo(componentname1, i, j);
        if(activityinfo1 != null) goto _L8; else goto _L7
_L8:
        l2 = list.size();
        for(i3 = k; i3 < l2; i3++) {
            resolveinfo3 = (ResolveInfo)list.get(i3);
            if(((ComponentInfo) (resolveinfo3.activityInfo)).name.equals(componentname1.getClassName()) && resolveinfo3.activityInfo.applicationInfo.packageName.equals(componentname1.getPackageName()) || s3 != null && resolveinfo3.filter.matchAction(s3)) {
                list.remove(i3);
                if(resolveinfo2 == null)
                    resolveinfo2 = resolveinfo3;
                i3--;
                l2--;
            }
        }

        if(resolveinfo2 == null) {
            resolveinfo2 = new ResolveInfo();
            resolveinfo2.activityInfo = activityinfo1;
        }
        list.add(k, resolveinfo2);
        resolveinfo2.specificIndex = k2;
        k++;
        break; /* Loop/switch isn't completed */
        int l1;
        int i2;
        int l = list.size();
        int i1 = k;
        while(i1 < l - 1)  {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(i1);
            if(resolveinfo.filter != null) {
                Iterator iterator = resolveinfo.filter.actionsIterator();
                if(iterator != null) {
                    do {
                        if(!iterator.hasNext())
                            break;
                        String s2 = (String)iterator.next();
                        if(s1 == null || !s1.equals(s2)) {
                            int j2 = i1 + 1;
                            while(j2 < l)  {
                                ResolveInfo resolveinfo1 = (ResolveInfo)list.get(j2);
                                if(resolveinfo1.filter != null && resolveinfo1.filter.hasAction(s2)) {
                                    list.remove(j2);
                                    j2--;
                                    l--;
                                }
                                j2++;
                            }
                        }
                    } while(true);
                    if((i & 0x40) == 0)
                        resolveinfo.filter = null;
                }
            }
            i1++;
        }
        if(componentname == null)
            continue; /* Loop/switch isn't completed */
        l1 = list.size();
        i2 = 0;
_L11:
        if(i2 >= l1)
            continue; /* Loop/switch isn't completed */
        ActivityInfo activityinfo = ((ResolveInfo)list.get(i2)).activityInfo;
        if(!componentname.getPackageName().equals(activityinfo.applicationInfo.packageName) || !componentname.getClassName().equals(((ComponentInfo) (activityinfo)).name))
            break MISSING_BLOCK_LABEL_698;
        list.remove(i2);
        if((i & 0x40) != 0) goto _L10; else goto _L9
_L9:
        int j1 = list.size();
        int k1 = 0;
        while(k1 < j1)  {
            ((ResolveInfo)list.get(k1)).filter = null;
            k1++;
        }
          goto _L10
        i2++;
          goto _L11
    }

    public List queryIntentReceivers(Intent intent, String s, int i, int j) {
        Object obj;
        if(!sUserManager.exists(j)) {
            obj = null;
        } else {
label0:
            {
                ComponentName componentname = intent.getComponent();
                if(componentname == null && intent.getSelector() != null) {
                    intent = intent.getSelector();
                    componentname = intent.getComponent();
                }
                if(componentname == null)
                    break label0;
                obj = new ArrayList(1);
                ActivityInfo activityinfo = getReceiverInfo(componentname, i, j);
                if(activityinfo != null) {
                    ResolveInfo resolveinfo = new ResolveInfo();
                    resolveinfo.activityInfo = activityinfo;
                    ((List) (obj)).add(resolveinfo);
                }
            }
        }
_L1:
        return ((List) (obj));
        hashmap;
        JVM INSTR monitorenter ;
        String s1;
        s1 = intent.getPackage();
        if(s1 != null)
            break MISSING_BLOCK_LABEL_154;
        obj = mReceivers.queryIntent(intent, s, i, j);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        if(package1 == null) goto _L3; else goto _L2
_L2:
        ActivityIntentResolver activityintentresolver = mReceivers;
        ArrayList arraylist = package1.receivers;
        obj = activityintentresolver.queryIntentForPackage(intent, s, i, arraylist, j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L3:
        obj = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public List queryIntentServices(Intent intent, String s, int i, int j) {
        Object obj;
        if(!sUserManager.exists(j)) {
            obj = null;
        } else {
label0:
            {
                ComponentName componentname = intent.getComponent();
                if(componentname == null && intent.getSelector() != null) {
                    intent = intent.getSelector();
                    componentname = intent.getComponent();
                }
                if(componentname == null)
                    break label0;
                obj = new ArrayList(1);
                ServiceInfo serviceinfo = getServiceInfo(componentname, i, j);
                if(serviceinfo != null) {
                    ResolveInfo resolveinfo = new ResolveInfo();
                    resolveinfo.serviceInfo = serviceinfo;
                    ((List) (obj)).add(resolveinfo);
                }
            }
        }
_L1:
        return ((List) (obj));
        hashmap;
        JVM INSTR monitorenter ;
        String s1;
        s1 = intent.getPackage();
        if(s1 != null)
            break MISSING_BLOCK_LABEL_154;
        obj = mServices.queryIntent(intent, s, i, j);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        android.content.pm.PackageParser.Package package1 = (android.content.pm.PackageParser.Package)mPackages.get(s1);
        if(package1 == null) goto _L3; else goto _L2
_L2:
        ServiceIntentResolver serviceintentresolver = mServices;
        ArrayList arraylist = package1.services;
        obj = serviceintentresolver.queryIntentForPackage(intent, s, i, arraylist, j);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L3:
        obj = null;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
    }

    public List queryPermissionsByGroup(String s, int i) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        Iterator iterator;
        arraylist = new ArrayList(10);
        iterator = mSettings.mPermissions.values().iterator();
_L3:
        BasePermission basepermission;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_149;
        basepermission = (BasePermission)iterator.next();
        if(s != null) goto _L2; else goto _L1
_L1:
        if(basepermission.perm == null || basepermission.perm.info.group == null)
            arraylist.add(generatePermissionInfo(basepermission, i));
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L2:
        if(basepermission.perm == null || !s.equals(basepermission.perm.info.group)) goto _L3; else goto _L4
_L4:
        arraylist.add(PackageParser.generatePermissionInfo(basepermission.perm, i));
          goto _L3
        if(arraylist.size() <= 0) goto _L6; else goto _L5
_L5:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L7
_L6:
        if(!mPermissionGroups.containsKey(s))
            arraylist = null;
        hashmap;
        JVM INSTR monitorexit ;
_L7:
        return arraylist;
    }

    public void querySyncProviders(List list, List list1) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mProviders.entrySet().iterator();
        int i = UserId.getCallingUserId();
        do {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            android.content.pm.PackageParser.Provider provider = (android.content.pm.PackageParser.Provider)entry.getValue();
            PackageSetting packagesetting = (PackageSetting)mSettings.mPackages.get(provider.owner.packageName);
            if(provider.syncable && (!mSafeMode || (1 & provider.info.applicationInfo.flags) != 0)) {
                list.add(entry.getKey());
                Exception exception;
                boolean flag;
                int j;
                if(packagesetting != null)
                    flag = packagesetting.getStopped(i);
                else
                    flag = false;
                if(packagesetting != null)
                    j = packagesetting.getEnabled(i);
                else
                    j = 0;
                list1.add(PackageParser.generateProviderInfo(provider, 0, flag, j, i));
            }
        } while(true);
        break MISSING_BLOCK_LABEL_197;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
    }

    void readPermission(XmlPullParser xmlpullparser, String s) throws IOException, XmlPullParserException {
        String s1 = s.intern();
        BasePermission basepermission = (BasePermission)mSettings.mPermissions.get(s1);
        if(basepermission == null) {
            basepermission = new BasePermission(s1, null, 1);
            mSettings.mPermissions.put(s1, basepermission);
        }
        int i = xmlpullparser.getDepth();
        do {
            int j = xmlpullparser.next();
            if(j == 1 || j == 3 && xmlpullparser.getDepth() <= i)
                break;
            if(j != 3 && j != 4) {
                if("group".equals(xmlpullparser.getName())) {
                    String s2 = xmlpullparser.getAttributeValue(null, "gid");
                    if(s2 != null) {
                        int k = Process.getGidForName(s2);
                        basepermission.gids = ArrayUtils.appendInt(basepermission.gids, k);
                    } else {
                        Slog.w("PackageManager", (new StringBuilder()).append("<group> without gid at ").append(xmlpullparser.getPositionDescription()).toString());
                    }
                }
                XmlUtils.skipCurrentTag(xmlpullparser);
            }
        } while(true);
    }

    void readPermissions() {
        File file = new File(Environment.getRootDirectory(), "etc/permissions");
        if(!file.exists() || !file.isDirectory())
            Slog.w("PackageManager", (new StringBuilder()).append("No directory ").append(file).append(", skipping").toString());
        else
        if(!file.canRead()) {
            Slog.w("PackageManager", (new StringBuilder()).append("Directory ").append(file).append(" cannot be read").toString());
        } else {
            File afile[] = file.listFiles();
            int i = afile.length;
            int j = 0;
            while(j < i)  {
                File file1 = afile[j];
                if(!file1.getPath().endsWith("etc/permissions/platform.xml"))
                    if(!file1.getPath().endsWith(".xml"))
                        Slog.i("PackageManager", (new StringBuilder()).append("Non-xml file ").append(file1).append(" in ").append(file).append(" directory, ignoring").toString());
                    else
                    if(!file1.canRead())
                        Slog.w("PackageManager", (new StringBuilder()).append("Permissions library file ").append(file1).append(" cannot be read").toString());
                    else
                        readPermissionsFromXml(file1);
                j++;
            }
            readPermissionsFromXml(new File(Environment.getRootDirectory(), "etc/permissions/platform.xml"));
        }
    }

    public void removePackageFromPreferred(String s) {
        Slog.w("PackageManager", "removePackageFromPreferred: this is now a no-op");
    }

    void removePackageLI(android.content.pm.PackageParser.Package package1, boolean flag) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        int i;
        StringBuilder stringbuilder;
        int j;
        mPackages.remove(package1.applicationInfo.packageName);
        if(package1.mPath != null)
            mAppDirs.remove(package1.mPath);
        i = package1.providers.size();
        stringbuilder = null;
        j = 0;
_L22:
        if(j >= i) goto _L2; else goto _L1
_L1:
        android.content.pm.PackageParser.Provider provider;
        provider = (android.content.pm.PackageParser.Provider)package1.providers.get(j);
        mProvidersByComponent.remove(new ComponentName(((ComponentInfo) (provider.info)).packageName, ((ComponentInfo) (provider.info)).name));
        if(provider.info.authority != null) goto _L4; else goto _L3
_L4:
        StringBuilder stringbuilder1;
        int l;
        StringBuilder stringbuilder2;
        int j1;
        StringBuilder stringbuilder3;
        int l1;
        StringBuilder stringbuilder4;
        int j2;
        StringBuilder stringbuilder5;
        int l2;
        String as[] = provider.info.authority.split(";");
        Exception exception;
        int k;
        int i1;
        int k1;
        int i2;
        int k2;
        android.content.pm.PackageParser.Instrumentation instrumentation;
        android.content.pm.PackageParser.Permission permission;
        BasePermission basepermission;
        android.content.pm.PackageParser.Activity activity;
        android.content.pm.PackageParser.Activity activity1;
        android.content.pm.PackageParser.Service service;
        for(int i3 = 0; i3 < as.length; i3++)
            if(mProviders.get(as[i3]) == provider)
                mProviders.remove(as[i3]);

        if(!flag) goto _L3; else goto _L5
_L5:
        if(stringbuilder != null) goto _L7; else goto _L6
_L6:
        stringbuilder = new StringBuilder(256);
_L8:
        stringbuilder.append(((ComponentInfo) (provider.info)).name);
        break; /* Loop/switch isn't completed */
        exception;
        throw exception;
_L7:
        stringbuilder.append(' ');
        if(true) goto _L8; else goto _L3
_L23:
        k = package1.services.size();
        stringbuilder1 = null;
        l = 0;
_L24:
        if(l >= k) goto _L10; else goto _L9
_L9:
        service = (android.content.pm.PackageParser.Service)package1.services.get(l);
        mServices.removeService(service);
        if(flag) {
            if(stringbuilder1 == null)
                stringbuilder1 = new StringBuilder(256);
            else
                stringbuilder1.append(' ');
            stringbuilder1.append(((ComponentInfo) (service.info)).name);
        }
          goto _L11
_L25:
        i1 = package1.receivers.size();
        stringbuilder2 = null;
        j1 = 0;
_L26:
        if(j1 >= i1) goto _L13; else goto _L12
_L12:
        activity1 = (android.content.pm.PackageParser.Activity)package1.receivers.get(j1);
        mReceivers.removeActivity(activity1, "receiver");
        if(flag) {
            if(stringbuilder2 == null)
                stringbuilder2 = new StringBuilder(256);
            else
                stringbuilder2.append(' ');
            stringbuilder2.append(((ComponentInfo) (activity1.info)).name);
        }
          goto _L14
_L27:
        k1 = package1.activities.size();
        stringbuilder3 = null;
        l1 = 0;
_L28:
        if(l1 >= k1) goto _L16; else goto _L15
_L15:
        activity = (android.content.pm.PackageParser.Activity)package1.activities.get(l1);
        mActivities.removeActivity(activity, "activity");
        if(flag) {
            if(stringbuilder3 == null)
                stringbuilder3 = new StringBuilder(256);
            else
                stringbuilder3.append(' ');
            stringbuilder3.append(((ComponentInfo) (activity.info)).name);
        }
          goto _L17
_L29:
        i2 = package1.permissions.size();
        stringbuilder4 = null;
        j2 = 0;
_L30:
        if(j2 >= i2) goto _L19; else goto _L18
_L18:
        permission = (android.content.pm.PackageParser.Permission)package1.permissions.get(j2);
        basepermission = (BasePermission)mSettings.mPermissions.get(permission.info.name);
        if(basepermission == null)
            basepermission = (BasePermission)mSettings.mPermissionTrees.get(permission.info.name);
        if(basepermission != null && basepermission.perm == permission) {
            basepermission.perm = null;
            if(flag) {
                if(stringbuilder4 == null)
                    stringbuilder4 = new StringBuilder(256);
                else
                    stringbuilder4.append(' ');
                stringbuilder4.append(permission.info.name);
            }
        }
          goto _L20
_L31:
        k2 = package1.instrumentation.size();
        stringbuilder5 = null;
        l2 = 0;
_L32:
        if(l2 >= k2)
            break MISSING_BLOCK_LABEL_866;
        instrumentation = (android.content.pm.PackageParser.Instrumentation)package1.instrumentation.get(l2);
        mInstrumentation.remove(instrumentation.getComponentName());
        if(flag) {
            if(stringbuilder5 == null)
                stringbuilder5 = new StringBuilder(256);
            else
                stringbuilder5.append(' ');
            stringbuilder5.append(instrumentation.info.name);
        }
          goto _L21
_L33:
        return;
_L3:
        j++;
          goto _L22
_L2:
        if(stringbuilder == null);
          goto _L23
_L11:
        l++;
          goto _L24
_L10:
        if(stringbuilder1 == null);
          goto _L25
_L14:
        j1++;
          goto _L26
_L13:
        if(stringbuilder2 == null);
          goto _L27
_L17:
        l1++;
          goto _L28
_L16:
        if(stringbuilder3 == null);
          goto _L29
_L20:
        j2++;
          goto _L30
_L19:
        if(stringbuilder4 == null);
          goto _L31
_L21:
        l2++;
          goto _L32
        if(stringbuilder5 == null);
          goto _L33
    }

    public void removePermission(String s) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        checkPermissionTreeLP(s);
        BasePermission basepermission = (BasePermission)mSettings.mPermissions.get(s);
        if(basepermission == null)
            break MISSING_BLOCK_LABEL_95;
        if(basepermission.type != 2)
            throw new SecurityException((new StringBuilder()).append("Not allowed to modify non-dynamic permission ").append(s).toString());
        break MISSING_BLOCK_LABEL_76;
        Exception exception;
        exception;
        throw exception;
        mSettings.mPermissions.remove(s);
        mSettings.writeLPr();
        hashmap;
        JVM INSTR monitorexit ;
    }

    public boolean removeUser(int i) {
        enforceSystemOrRoot("Only the system can remove users");
        boolean flag;
        if(i == 0 || !sUserManager.exists(i)) {
            flag = false;
        } else {
            cleanUpUser(i);
            if(sUserManager.removeUser(i)) {
                Intent intent = new Intent("android.intent.action.USER_REMOVED");
                intent.putExtra("android.intent.extra.user_id", i);
                mContext.sendBroadcast(intent, "android.permission.MANAGE_ACCOUNTS");
            }
            sUserManager.removePackageFolders(i);
            flag = true;
        }
        return flag;
    }

    public void replacePreferredActivity(IntentFilter intentfilter, int i, ComponentName acomponentname[], ComponentName componentname) {
        if(intentfilter.countActions() != 1)
            throw new IllegalArgumentException("replacePreferredActivity expects filter to have only 1 action.");
        if(intentfilter.countCategories() != 1)
            throw new IllegalArgumentException("replacePreferredActivity expects filter to have only 1 category.");
        if(intentfilter.countDataAuthorities() != 0 || intentfilter.countDataPaths() != 0 || intentfilter.countDataSchemes() != 0 || intentfilter.countDataTypes() != 0)
            throw new IllegalArgumentException("replacePreferredActivity expects filter to have no data authorities, paths, schemes or types.");
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") == 0) goto _L2; else goto _L1
_L1:
        if(getUidTargetSdkVersionLockedLPr(Binder.getCallingUid()) >= 8) goto _L4; else goto _L3
_L3:
        Slog.w("PackageManager", (new StringBuilder()).append("Ignoring replacePreferredActivity() from uid ").append(Binder.getCallingUid()).toString());
          goto _L5
_L4:
        mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
_L2:
        ArrayList arraylist;
        arraylist = null;
        Iterator iterator = mSettings.mPreferredActivities.filterIterator();
        String s = intentfilter.getAction(0);
        String s1 = intentfilter.getCategory(0);
        do {
            if(!iterator.hasNext())
                break;
            PreferredActivity preferredactivity1 = (PreferredActivity)iterator.next();
            if(preferredactivity1.getAction(0).equals(s) && preferredactivity1.getCategory(0).equals(s1)) {
                if(arraylist == null)
                    arraylist = new ArrayList();
                arraylist.add(preferredactivity1);
                Log.i("PackageManager", (new StringBuilder()).append("Removing preferred activity ").append(preferredactivity1.mPref.mComponent).append(":").toString());
                intentfilter.dump(new LogPrinter(4, "PackageManager"), "  ");
            }
        } while(true);
        break MISSING_BLOCK_LABEL_324;
        Exception exception;
        exception;
        throw exception;
        int j;
        if(arraylist == null)
            break MISSING_BLOCK_LABEL_372;
        j = 0;
        while(j < arraylist.size())  {
            PreferredActivity preferredactivity = (PreferredActivity)arraylist.get(j);
            mSettings.mPreferredActivities.removeFilter(preferredactivity);
            j++;
        }
        addPreferredActivity(intentfilter, i, acomponentname, componentname);
        hashmap;
        JVM INSTR monitorexit ;
_L5:
    }

    public ProviderInfo resolveContentProvider(String s, int i, int j) {
        if(sUserManager.exists(j)) goto _L2; else goto _L1
_L1:
        ProviderInfo providerinfo = null;
_L4:
        return providerinfo;
_L2:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Provider provider = (android.content.pm.PackageParser.Provider)mProviders.get(s);
        PackageSetting packagesetting;
        if(provider != null)
            packagesetting = (PackageSetting)mSettings.mPackages.get(provider.owner.packageName);
        else
            packagesetting = null;
        if(provider != null && mSettings.isEnabledLPr(provider.info, i, j) && (!mSafeMode || (1 & provider.info.applicationInfo.flags) != 0)) {
            boolean flag;
            int k;
            if(packagesetting != null)
                flag = packagesetting.getStopped(j);
            else
                flag = false;
            if(packagesetting != null)
                k = packagesetting.getEnabled(j);
            else
                k = 0;
            providerinfo = PackageParser.generateProviderInfo(provider, i, flag, k, j);
        } else {
            providerinfo = null;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public ResolveInfo resolveIntent(Intent intent, String s, int i, int j) {
        ResolveInfo resolveinfo;
        if(!sUserManager.exists(j))
            resolveinfo = null;
        else
            resolveinfo = chooseBestActivity(intent, s, i, queryIntentActivities(intent, s, i, j), j);
        return resolveinfo;
    }

    public ResolveInfo resolveService(Intent intent, String s, int i, int j) {
        ResolveInfo resolveinfo;
        List list;
        resolveinfo = null;
        list = queryIntentServices(intent, s, i, j);
        break MISSING_BLOCK_LABEL_14;
        if(sUserManager.exists(j) && list != null && list.size() >= 1)
            resolveinfo = (ResolveInfo)list.get(0);
        return resolveinfo;
    }

    public void revokePermission(String s, String s1) {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        android.content.pm.PackageParser.Package package1;
        package1 = (android.content.pm.PackageParser.Package)mPackages.get(s);
        if(package1 == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown package: ").append(s).toString());
        break MISSING_BLOCK_LABEL_60;
        Exception exception;
        exception;
        throw exception;
        BasePermission basepermission;
        PackageSetting packagesetting;
        if(package1.applicationInfo.uid != Binder.getCallingUid())
            mContext.enforceCallingOrSelfPermission("android.permission.GRANT_REVOKE_PERMISSIONS", null);
        basepermission = (BasePermission)mSettings.mPermissions.get(s1);
        if(basepermission == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown permission: ").append(s).toString());
        if(!package1.requestedPermissions.contains(s1))
            throw new SecurityException((new StringBuilder()).append("Package ").append(s).append(" has not requested permission ").append(s1).toString());
        if((0x20 & basepermission.protectionLevel) == 0)
            throw new SecurityException((new StringBuilder()).append("Permission ").append(s1).append(" is not a development permission").toString());
        packagesetting = (PackageSetting)package1.mExtras;
        if(packagesetting != null)
            break MISSING_BLOCK_LABEL_249;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_331;
        Object obj;
        if(packagesetting.sharedUser == null)
            break MISSING_BLOCK_LABEL_324;
        obj = packagesetting.sharedUser;
_L1:
        if(((GrantedPermissions) (obj)).grantedPermissions.remove(s1)) {
            ((GrantedPermissions) (obj)).grantedPermissions.remove(s1);
            if(((PackageSettingBase) (packagesetting)).haveGids)
                obj.gids = removeInts(((GrantedPermissions) (obj)).gids, basepermission.gids);
            mSettings.writeLPr();
        }
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_331;
        obj = packagesetting;
          goto _L1
    }

    public void scanAvailableAsecs() {
        updateExternalMediaStatusInner(true, false, false);
    }

    void schedulePackageCleaning(String s) {
        mHandler.sendMessage(mHandler.obtainMessage(7, s));
    }

    void scheduleWritePackageRestrictionsLocked(int i) {
        if(sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        mDirtyUsers.add(Integer.valueOf(i));
        if(!mHandler.hasMessages(14))
            mHandler.sendEmptyMessageDelayed(14, 10000L);
        if(true) goto _L1; else goto _L3
_L3:
    }

    void scheduleWriteSettingsLocked() {
        if(!mHandler.hasMessages(13))
            mHandler.sendEmptyMessageDelayed(13, 10000L);
    }

    public void setApplicationEnabledSetting(String s, int i, int j, int k) {
        if(sUserManager.exists(k) && !Injector.setAccessControl(this, s, i, j))
            setEnabledSetting(s, null, i, j, k);
    }

    public void setComponentEnabledSetting(ComponentName componentname, int i, int j, int k) {
        if(sUserManager.exists(k))
            setEnabledSetting(componentname.getPackageName(), componentname.getClassName(), i, j, k);
    }

    public boolean setInstallLocation(int i) {
        boolean flag = true;
        mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS", null);
        if(getInstallLocation() != i)
            if(i == 0 || i == flag || i == 2)
                android.provider.Settings.System.putInt(mContext.getContentResolver(), "default_install_location", i);
            else
                flag = false;
        return flag;
    }

    public void setInstallerPackageName(String s, String s1) {
        int i = Binder.getCallingUid();
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        PackageSetting packagesetting;
        packagesetting = (PackageSetting)mSettings.mPackages.get(s);
        if(packagesetting == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown target package: ").append(s).toString());
        break MISSING_BLOCK_LABEL_70;
        Exception exception;
        exception;
        throw exception;
        if(s1 == null)
            break MISSING_BLOCK_LABEL_394;
        PackageSetting packagesetting1;
        packagesetting1 = (PackageSetting)mSettings.mPackages.get(s1);
        if(packagesetting1 == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown installer package: ").append(s1).toString());
_L1:
        Object obj = mSettings.getUserIdLPr(i);
        Signature asignature[];
        if(obj != null) {
            if(obj instanceof SharedUserSetting)
                asignature = ((SharedUserSetting)obj).signatures.mSignatures;
            else
            if(obj instanceof PackageSetting)
                asignature = ((PackageSettingBase) ((PackageSetting)obj)).signatures.mSignatures;
            else
                throw new SecurityException((new StringBuilder()).append("Bad object ").append(obj).append(" for uid ").append(i).toString());
            if(packagesetting1 != null && compareSignatures(asignature, ((PackageSettingBase) (packagesetting1)).signatures.mSignatures) != 0)
                throw new SecurityException((new StringBuilder()).append("Caller does not have same cert as new installer package ").append(s1).toString());
        } else {
            throw new SecurityException((new StringBuilder()).append("Unknown calling uid ").append(i).toString());
        }
        if(((PackageSettingBase) (packagesetting)).installerPackageName != null) {
            PackageSetting packagesetting2 = (PackageSetting)mSettings.mPackages.get(((PackageSettingBase) (packagesetting)).installerPackageName);
            if(packagesetting2 != null && compareSignatures(asignature, ((PackageSettingBase) (packagesetting2)).signatures.mSignatures) != 0)
                throw new SecurityException((new StringBuilder()).append("Caller does not have same cert as old installer package ").append(((PackageSettingBase) (packagesetting)).installerPackageName).toString());
        }
        packagesetting.installerPackageName = s1;
        scheduleWriteSettingsLocked();
        hashmap;
        JVM INSTR monitorexit ;
        return;
        packagesetting1 = null;
          goto _L1
    }

    public void setPackageStoppedState(String s, boolean flag, int i) {
        if(sUserManager.exists(i)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j = Binder.getCallingUid();
        boolean flag1;
        HashMap hashmap;
        if(mContext.checkCallingOrSelfPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE") == 0)
            flag1 = true;
        else
            flag1 = false;
        checkValidCaller(j, i);
        hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(mSettings.setPackageStoppedStateLPw(s, flag, flag1, j, i))
            scheduleWritePackageRestrictionsLocked(i);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void setPermissionEnforced(String s, boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.GRANT_REVOKE_PERMISSIONS", null);
        if(!"android.permission.READ_EXTERNAL_STORAGE".equals(s)) goto _L2; else goto _L1
_L1:
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        IActivityManager iactivitymanager;
        long l;
        if(mSettings.mReadExternalStorageEnforced != null && mSettings.mReadExternalStorageEnforced.booleanValue() == flag)
            break MISSING_BLOCK_LABEL_101;
        mSettings.mReadExternalStorageEnforced = Boolean.valueOf(flag);
        mSettings.writeLPr();
        iactivitymanager = ActivityManagerNative.getDefault();
        if(iactivitymanager == null)
            break MISSING_BLOCK_LABEL_101;
        l = Binder.clearCallingIdentity();
        iactivitymanager.killProcessesBelowForeground("setPermissionEnforcement");
        Binder.restoreCallingIdentity(l);
_L3:
        hashmap;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
_L2:
        throw new IllegalArgumentException((new StringBuilder()).append("No selective enforcement for ").append(s).toString());
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l);
          goto _L3
    }

    void startCleaningPackages() {
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        if(!isExternalMediaAvailable() || mSettings.mPackagesToBeCleaned.size() <= 0)
            break MISSING_BLOCK_LABEL_89;
        break MISSING_BLOCK_LABEL_42;
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
        Intent intent = new Intent("android.content.pm.CLEAN_EXTERNAL_STORAGE");
        intent.setComponent(DEFAULT_CONTAINER_COMPONENT);
        IActivityManager iactivitymanager = ActivityManagerNative.getDefault();
        if(iactivitymanager != null)
            try {
                iactivitymanager.startService(null, intent, null);
            }
            catch(RemoteException remoteexception) { }
    }

    public void systemReady() {
        boolean flag = true;
        mSystemReady = flag;
        if(android.provider.Settings.System.getInt(mContext.getContentResolver(), "compatibility_mode", flag) != flag)
            flag = false;
        PackageParser.setCompatibilityModeEnabled(flag);
    }

    public void updateExternalMediaStatus(final boolean mediaStatus, final boolean reportStatus) {
        int i = Binder.getCallingUid();
        if(i != 0 && i != 1000)
            throw new SecurityException("Media status can only be updated by the system");
        HashMap hashmap = mPackages;
        hashmap;
        JVM INSTR monitorenter ;
        StringBuilder stringbuilder = (new StringBuilder()).append("Updating external media status from ");
        if(!mMediaMounted) goto _L2; else goto _L1
_L1:
        String s = "mounted";
_L8:
        StringBuilder stringbuilder1 = stringbuilder.append(s).append(" to ");
        if(!mediaStatus) goto _L4; else goto _L3
_L3:
        String s1 = "mounted";
_L9:
        Log.i("PackageManager", stringbuilder1.append(s1).toString());
        if(mediaStatus != mMediaMounted) goto _L6; else goto _L5
_L5:
        PackageHandler packagehandler;
        int j;
        packagehandler = mHandler;
        if(!reportStatus)
            break MISSING_BLOCK_LABEL_206;
        j = 1;
_L10:
        Message message = packagehandler.obtainMessage(12, j, -1);
        mHandler.sendMessage(message);
          goto _L7
_L6:
        mMediaMounted = mediaStatus;
        mHandler.post(new Runnable() {

            public void run() {
                updateExternalMediaStatusInner(mediaStatus, reportStatus, true);
            }

            final PackageManagerService this$0;
            final boolean val$mediaStatus;
            final boolean val$reportStatus;

             {
                this$0 = PackageManagerService.this;
                mediaStatus = flag;
                reportStatus = flag1;
                super();
            }
        });
          goto _L7
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L7:
        return;
_L2:
        s = "unmounted";
          goto _L8
_L4:
        s1 = "unmounted";
          goto _L9
        j = 0;
          goto _L10
    }

    public void updateUserName(int i, String s) {
        enforceSystemOrRoot("Only the system can rename users");
        sUserManager.updateUserName(i, s);
    }

    public void verifyPendingInstall(int i, int j) throws RemoteException {
        Message message = mHandler.obtainMessage(15);
        PackageVerificationResponse packageverificationresponse = new PackageVerificationResponse(j, Binder.getCallingUid());
        message.arg1 = i;
        message.obj = packageverificationresponse;
        mHandler.sendMessage(message);
    }

    private static final int ADD_EVENTS = 136;
    static final int BROADCAST_DELAY = 10000;
    static final int CHECK_PENDING_VERIFICATION = 16;
    private static final boolean DEBUG_APP_DIR_OBSERVER = false;
    private static final boolean DEBUG_INSTALL = false;
    private static final boolean DEBUG_INTENT_MATCHING = false;
    private static final boolean DEBUG_PACKAGE_INFO = false;
    private static final boolean DEBUG_PACKAGE_SCANNING = false;
    private static final boolean DEBUG_PREFERRED = false;
    private static final boolean DEBUG_REMOVE = false;
    static final boolean DEBUG_SD_INSTALL = false;
    static final boolean DEBUG_SETTINGS = false;
    private static final boolean DEBUG_SHOW_INFO = false;
    static final boolean DEBUG_UPGRADE = false;
    private static final boolean DEBUG_VERIFY = false;
    static final ComponentName DEFAULT_CONTAINER_COMPONENT = new ComponentName("com.android.defcontainer", "com.android.defcontainer.DefaultContainerService");
    static final String DEFAULT_CONTAINER_PACKAGE = "com.android.defcontainer";
    private static final long DEFAULT_VERIFICATION_TIMEOUT = 60000L;
    private static final boolean DEFAULT_VERIFY_ENABLE = false;
    static final int DEX_OPT_DEFERRED = 2;
    static final int DEX_OPT_FAILED = -1;
    static final int DEX_OPT_PERFORMED = 1;
    static final int DEX_OPT_SKIPPED = 0;
    static final int END_COPY = 4;
    static final int FIND_INSTALL_LOC = 8;
    private static final boolean GET_CERTIFICATES = true;
    static final int INIT_COPY = 5;
    private static final String INSTALL_PACKAGE_SUFFIX = "-";
    private static final String LIB_DIR_NAME = "lib";
    private static final int LOG_UID = 1007;
    static final int MCS_BOUND = 3;
    static final int MCS_GIVE_UP = 11;
    static final int MCS_RECONNECT = 10;
    static final int MCS_UNBIND = 6;
    private static final int NFC_UID = 1027;
    private static final int OBSERVER_EVENTS = 712;
    private static final String PACKAGE_MIME_TYPE = "application/vnd.android.package-archive";
    static final int PACKAGE_VERIFIED = 15;
    static final int POST_INSTALL = 9;
    private static final int RADIO_UID = 1001;
    static final int REMOVE_CHATTY = 0x10000;
    private static final int REMOVE_EVENTS = 584;
    static final int SCAN_BOOTING = 256;
    static final int SCAN_DEFER_DEX = 128;
    static final int SCAN_FORCE_DEX = 4;
    static final int SCAN_MONITOR = 1;
    static final int SCAN_NEW_INSTALL = 16;
    static final int SCAN_NO_DEX = 2;
    static final int SCAN_NO_PATHS = 32;
    static final int SCAN_UPDATE_SIGNATURE = 8;
    static final int SCAN_UPDATE_TIME = 64;
    private static final String SD_ENCRYPTION_ALGORITHM = "AES";
    private static final String SD_ENCRYPTION_KEYSTORE_NAME = "AppsOnSD";
    static final int SEND_PENDING_BROADCAST = 1;
    static final int START_CLEANING_PACKAGE = 7;
    static final String TAG = "PackageManager";
    static final int UPDATED_MEDIA_STATUS = 12;
    static final int UPDATE_PERMISSIONS_ALL = 1;
    static final int UPDATE_PERMISSIONS_REPLACE_ALL = 4;
    static final int UPDATE_PERMISSIONS_REPLACE_PKG = 2;
    static final int WRITE_PACKAGE_RESTRICTIONS = 14;
    static final int WRITE_SETTINGS = 13;
    static final int WRITE_SETTINGS_DELAY = 10000;
    private static final Comparator mProviderInitOrderSorter = new Comparator() {

        public int compare(ProviderInfo providerinfo, ProviderInfo providerinfo1) {
            int i = providerinfo.initOrder;
            int j = providerinfo1.initOrder;
            byte byte0;
            if(i > j)
                byte0 = -1;
            else
            if(i < j)
                byte0 = 1;
            else
                byte0 = 0;
            return byte0;
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((ProviderInfo)obj, (ProviderInfo)obj1);
        }

    };
    private static final Comparator mResolvePrioritySorter = new Comparator() {

        public int compare(ResolveInfo resolveinfo, ResolveInfo resolveinfo1) {
            byte byte0;
            int i;
            int j;
            byte0 = -1;
            i = resolveinfo.priority;
            j = resolveinfo1.priority;
            if(i == j) goto _L2; else goto _L1
_L1:
            if(i <= j)
                byte0 = 1;
_L4:
            return byte0;
_L2:
            int k = resolveinfo.preferredOrder;
            int l = resolveinfo1.preferredOrder;
            if(k != l) {
                if(k <= l)
                    byte0 = 1;
            } else
            if(resolveinfo.isDefault != resolveinfo1.isDefault) {
                if(!resolveinfo.isDefault)
                    byte0 = 1;
            } else {
                int i1 = resolveinfo.match;
                int j1 = resolveinfo1.match;
                if(i1 != j1) {
                    if(i1 <= j1)
                        byte0 = 1;
                } else
                if(resolveinfo.system != resolveinfo1.system) {
                    if(!resolveinfo.system)
                        byte0 = 1;
                } else {
                    byte0 = 0;
                }
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((ResolveInfo)obj, (ResolveInfo)obj1);
        }

    };
    static final String mTempContainerPrefix = "smdl2tmp";
    static UserManager sUserManager;
    final ActivityIntentResolver mActivities;
    ApplicationInfo mAndroidApplication;
    final File mAppDataDir;
    final HashMap mAppDirs;
    final File mAppInstallDir;
    final FileObserver mAppInstallObserver;
    final String mAsecInternalPath;
    final HashMap mAvailableFeatures;
    private IMediaContainerService mContainerService;
    final Context mContext;
    final File mDalvikCacheDir;
    private final DefaultContainerConnection mDefContainerConn;
    final int mDefParseFlags;
    final ArrayList mDeferredDexOpt;
    private HashSet mDirtyUsers;
    final FileObserver mDrmAppInstallObserver;
    final File mDrmAppPrivateInstallDir;
    final boolean mFactoryTest;
    final File mFrameworkDir;
    final FileObserver mFrameworkInstallObserver;
    int mGlobalGids[];
    final PackageHandler mHandler;
    final HandlerThread mHandlerThread;
    boolean mHasSystemUidErrors;
    final Object mInstallLock;
    final Installer mInstaller;
    final HashMap mInstrumentation;
    int mLastScanError;
    private boolean mMediaMounted;
    final DisplayMetrics mMetrics;
    int mNextInstallToken;
    final boolean mNoDexOpt;
    final boolean mOnlyCore;
    final int mOutPermissions[];
    final HashMap mPackages;
    final HashMap mPendingBroadcasts;
    final SparseArray mPendingVerification;
    private int mPendingVerificationToken;
    final HashMap mPermissionGroups;
    android.content.pm.PackageParser.Package mPlatformPackage;
    final HashSet mProtectedBroadcasts;
    final HashMap mProviders;
    final HashMap mProvidersByComponent;
    final ActivityIntentResolver mReceivers;
    private final String mRequiredVerifierPackage;
    final ActivityInfo mResolveActivity;
    ComponentName mResolveComponentName;
    final ResolveInfo mResolveInfo;
    boolean mRestoredSettings;
    final SparseArray mRunningInstalls;
    boolean mSafeMode;
    File mScanningPath;
    final String mSdkCodename;
    final int mSdkVersion;
    final String mSeparateProcesses[];
    final ServiceIntentResolver mServices;
    final Settings mSettings;
    final HashMap mSharedLibraries;
    final File mSystemAppDir;
    final FileObserver mSystemInstallObserver;
    final SparseArray mSystemPermissions;
    boolean mSystemReady;
    String mTmpSharedLibraries[];
    final HashSet mTransferedPackages;
    final File mUserAppDataDir;
    final File mVendorAppDir;
    final FileObserver mVendorInstallObserver;

















/*
    static int access$2408(PackageManagerService packagemanagerservice) {
        int i = packagemanagerservice.mPendingVerificationToken;
        packagemanagerservice.mPendingVerificationToken = i + 1;
        return i;
    }

*/









/*
    static IMediaContainerService access$302(PackageManagerService packagemanagerservice, IMediaContainerService imediacontainerservice) {
        packagemanagerservice.mContainerService = imediacontainerservice;
        return imediacontainerservice;
    }

*/













}
