// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.ComponentName;
import android.content.IContentProvider;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.android.server.am:
//            ProcessRecord, ContentProviderConnection, ActivityManagerService

class ContentProviderRecord {
    private class ExternalProcessHandle
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            ActivityManagerService activitymanagerservice = service;
            activitymanagerservice;
            JVM INSTR monitorenter ;
            if(hasExternalProcessHandles() && externalProcessTokenToHandle.get(mToken) != null)
                removeExternalProcessHandleInternalLocked(mToken);
            return;
        }

        public void unlinkFromOwnDeathLocked() {
            mToken.unlinkToDeath(this, 0);
        }

        private static final String LOG_TAG = "ExternalProcessHanldle";
        private int mAcquisitionCount;
        private final IBinder mToken;
        final ContentProviderRecord this$0;



/*
        static int access$008(ExternalProcessHandle externalprocesshandle) {
            int i = externalprocesshandle.mAcquisitionCount;
            externalprocesshandle.mAcquisitionCount = i + 1;
            return i;
        }

*/


/*
        static int access$010(ExternalProcessHandle externalprocesshandle) {
            int i = externalprocesshandle.mAcquisitionCount;
            externalprocesshandle.mAcquisitionCount = i - 1;
            return i;
        }

*/

        public ExternalProcessHandle(IBinder ibinder) {
            this$0 = ContentProviderRecord.this;
            super();
            mToken = ibinder;
            ibinder.linkToDeath(this, 0);
_L1:
            return;
            RemoteException remoteexception;
            remoteexception;
            Slog.e("ExternalProcessHanldle", (new StringBuilder()).append("Couldn't register for death for token: ").append(mToken).toString(), remoteexception);
              goto _L1
        }
    }


    public ContentProviderRecord(ActivityManagerService activitymanagerservice, ProviderInfo providerinfo, ApplicationInfo applicationinfo, ComponentName componentname) {
        connections = new ArrayList();
        service = activitymanagerservice;
        info = providerinfo;
        uid = applicationinfo.uid;
        appInfo = applicationinfo;
        name = componentname;
        boolean flag;
        if(uid == 0 || uid == 1000)
            flag = true;
        else
            flag = false;
        noReleaseNeeded = flag;
    }

    public ContentProviderRecord(ContentProviderRecord contentproviderrecord) {
        connections = new ArrayList();
        service = contentproviderrecord.service;
        info = contentproviderrecord.info;
        uid = contentproviderrecord.uid;
        appInfo = contentproviderrecord.appInfo;
        name = contentproviderrecord.name;
        noReleaseNeeded = contentproviderrecord.noReleaseNeeded;
    }

    private void removeExternalProcessHandleInternalLocked(IBinder ibinder) {
        ((ExternalProcessHandle)externalProcessTokenToHandle.get(ibinder)).unlinkFromOwnDeathLocked();
        externalProcessTokenToHandle.remove(ibinder);
        if(externalProcessTokenToHandle.size() == 0)
            externalProcessTokenToHandle = null;
    }

    public void addExternalProcessHandleLocked(IBinder ibinder) {
        if(ibinder == null) {
            externalProcessNoHandleCount = 1 + externalProcessNoHandleCount;
        } else {
            if(externalProcessTokenToHandle == null)
                externalProcessTokenToHandle = new HashMap();
            ExternalProcessHandle externalprocesshandle = (ExternalProcessHandle)externalProcessTokenToHandle.get(ibinder);
            if(externalprocesshandle == null) {
                externalprocesshandle = new ExternalProcessHandle(ibinder);
                externalProcessTokenToHandle.put(ibinder, externalprocesshandle);
            }
            int i = 
// JavaClassFileOutputException: get_constant: invalid tag

    public boolean canRunHere(ProcessRecord processrecord) {
        boolean flag;
        if((info.multiprocess || info.processName.equals(processrecord.processName)) && (uid == 1000 || uid == processrecord.info.uid))
            flag = true;
        else
            flag = false;
        return flag;
    }

    void dump(PrintWriter printwriter, String s, boolean flag) {
        if(flag) {
            printwriter.print(s);
            printwriter.print("package=");
            printwriter.print(info.applicationInfo.packageName);
            printwriter.print(" process=");
            printwriter.println(info.processName);
        }
        printwriter.print(s);
        printwriter.print("proc=");
        printwriter.println(proc);
        if(launchingApp != null) {
            printwriter.print(s);
            printwriter.print("launchingApp=");
            printwriter.println(launchingApp);
        }
        if(flag) {
            printwriter.print(s);
            printwriter.print("uid=");
            printwriter.print(uid);
            printwriter.print(" provider=");
            printwriter.println(provider);
        }
        printwriter.print(s);
        printwriter.print("authority=");
        printwriter.println(info.authority);
        if(flag && (info.isSyncable || info.multiprocess || info.initOrder != 0)) {
            printwriter.print(s);
            printwriter.print("isSyncable=");
            printwriter.print(info.isSyncable);
            printwriter.print(" multiprocess=");
            printwriter.print(info.multiprocess);
            printwriter.print(" initOrder=");
            printwriter.println(info.initOrder);
        }
        if(!flag) goto _L2; else goto _L1
_L1:
        if(hasExternalProcessHandles()) {
            printwriter.print(s);
            printwriter.print("externals=");
            printwriter.println(externalProcessTokenToHandle.size());
        }
_L4:
        if(connections.size() > 0) {
            if(flag) {
                printwriter.print(s);
                printwriter.println("Connections:");
            }
            for(int i = 0; i < connections.size(); i++) {
                ContentProviderConnection contentproviderconnection = (ContentProviderConnection)connections.get(i);
                printwriter.print(s);
                printwriter.print("  -> ");
                printwriter.println(contentproviderconnection.toClientString());
                if(contentproviderconnection.provider != this) {
                    printwriter.print(s);
                    printwriter.print("    *** WRONG PROVIDER: ");
                    printwriter.println(contentproviderconnection.provider);
                }
            }

        }
        break; /* Loop/switch isn't completed */
_L2:
        if(connections.size() > 0 || externalProcessNoHandleCount > 0) {
            printwriter.print(s);
            printwriter.print(connections.size());
            printwriter.print(" connections, ");
            printwriter.print(externalProcessNoHandleCount);
            printwriter.println(" external handles");
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean hasExternalProcessHandles() {
        boolean flag;
        if(externalProcessTokenToHandle != null || externalProcessNoHandleCount > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public android.app.IActivityManager.ContentProviderHolder newHolder(ContentProviderConnection contentproviderconnection) {
        android.app.IActivityManager.ContentProviderHolder contentproviderholder = new ContentProviderHolder(info);
        contentproviderholder.provider = provider;
        contentproviderholder.noReleaseNeeded = noReleaseNeeded;
        contentproviderholder.connection = contentproviderconnection;
        return contentproviderholder;
    }

    public boolean removeExternalProcessHandleLocked(IBinder ibinder) {
        boolean flag = true;
        if(!hasExternalProcessHandles()) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
        if(externalProcessTokenToHandle == null) goto _L4; else goto _L3
_L3:
        ExternalProcessHandle externalprocesshandle = (ExternalProcessHandle)externalProcessTokenToHandle.get(ibinder);
        if(externalprocesshandle == null) goto _L4; else goto _L5
_L5:
        flag1 = true;
        int i = 
// JavaClassFileOutputException: get_constant: invalid tag

    public String toShortString() {
        String s;
        if(shortStringName != null) {
            s = shortStringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append('/');
            stringbuilder.append(name.flattenToShortString());
            s = stringbuilder.toString();
            shortStringName = s;
        }
        return s;
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("ContentProviderRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(name.flattenToShortString());
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    final ApplicationInfo appInfo;
    final ArrayList connections;
    int externalProcessNoHandleCount;
    HashMap externalProcessTokenToHandle;
    public final ProviderInfo info;
    ProcessRecord launchingApp;
    final ComponentName name;
    public boolean noReleaseNeeded;
    ProcessRecord proc;
    public IContentProvider provider;
    final ActivityManagerService service;
    String shortStringName;
    String stringName;
    final int uid;

}
