// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.IContentProvider;
import android.content.pm.ApplicationInfo;
import android.os.*;
import android.util.SparseArray;
import java.io.*;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ContentProviderRecord, ProcessRecord, TransferPipe

public class ProviderMap {

    public ProviderMap() {
    }

    private void dumpProvider(String s, FileDescriptor filedescriptor, PrintWriter printwriter, ContentProviderRecord contentproviderrecord, String as[], boolean flag) {
        String s1 = (new StringBuilder()).append(s).append("  ").toString();
        this;
        JVM INSTR monitorenter ;
        printwriter.print(s);
        printwriter.print("PROVIDER ");
        printwriter.print(contentproviderrecord);
        printwriter.print(" pid=");
        if(contentproviderrecord.proc == null) goto _L2; else goto _L1
_L1:
        printwriter.println(contentproviderrecord.proc.pid);
_L3:
        if(flag)
            contentproviderrecord.dump(printwriter, s1, true);
        this;
        JVM INSTR monitorexit ;
        if(contentproviderrecord.proc == null || contentproviderrecord.proc.thread == null)
            break MISSING_BLOCK_LABEL_174;
        printwriter.println("    Client:");
        printwriter.flush();
        TransferPipe transferpipe = new TransferPipe();
        contentproviderrecord.proc.thread.dumpProvider(transferpipe.getWriteFd().getFileDescriptor(), contentproviderrecord.provider.asBinder(), as);
        transferpipe.setBufferPrefix("      ");
        transferpipe.go(filedescriptor, 2000L);
        transferpipe.kill();
_L4:
        return;
_L2:
        printwriter.println("(not running)");
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        try {
            transferpipe.kill();
            throw exception1;
        }
        catch(IOException ioexception) {
            printwriter.println((new StringBuilder()).append("      Failure while dumping the provider: ").append(ioexception).toString());
        }
        catch(RemoteException remoteexception) {
            printwriter.println("      Got a RemoteException while dumping the service");
        }
          goto _L4
    }

    private void dumpProvidersByClassLocked(PrintWriter printwriter, boolean flag, HashMap hashmap) {
        ContentProviderRecord contentproviderrecord;
        for(Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext(); contentproviderrecord.dump(printwriter, "    ", flag)) {
            contentproviderrecord = (ContentProviderRecord)((java.util.Map.Entry)iterator.next()).getValue();
            printwriter.print("  * ");
            printwriter.println(contentproviderrecord);
        }

    }

    private void dumpProvidersByNameLocked(PrintWriter printwriter, HashMap hashmap) {
        ContentProviderRecord contentproviderrecord;
        for(Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext(); printwriter.println(contentproviderrecord.toShortString())) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            contentproviderrecord = (ContentProviderRecord)entry.getValue();
            printwriter.print("  ");
            printwriter.print((String)entry.getKey());
            printwriter.print(": ");
        }

    }

    private HashMap getProvidersByName(int i) {
        int j;
        HashMap hashmap;
        HashMap hashmap1;
        if(i >= 0)
            j = i;
        else
            j = Binder.getOrigCallingUser();
        hashmap = (HashMap)mProvidersByNamePerUser.get(j);
        if(hashmap == null) {
            hashmap1 = new HashMap();
            mProvidersByNamePerUser.put(j, hashmap1);
        } else {
            hashmap1 = hashmap;
        }
        return hashmap1;
    }

    protected boolean dumpProvider(FileDescriptor filedescriptor, PrintWriter printwriter, String s, String as[], int i, boolean flag) {
        ArrayList arraylist = new ArrayList();
        if(!"all".equals(s)) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator1 = getProvidersByClass(-1).values().iterator(); iterator1.hasNext(); arraylist.add((ContentProviderRecord)iterator1.next()));
        break MISSING_BLOCK_LABEL_72;
        Exception exception1;
        exception1;
        throw exception1;
        this;
        JVM INSTR monitorexit ;
_L3:
        ComponentName componentname;
        int j;
        Exception exception;
        Iterator iterator;
        boolean flag2;
        ContentProviderRecord contentproviderrecord;
        int l;
        if(arraylist.size() <= 0) {
            flag2 = false;
        } else {
            boolean flag1 = false;
            for(int k = 0; k < arraylist.size(); k++) {
                if(flag1)
                    printwriter.println();
                flag1 = true;
                dumpProvider("", filedescriptor, printwriter, (ContentProviderRecord)arraylist.get(k), as, flag);
            }

            flag2 = true;
        }
        return flag2;
_L2:
        if(s != null)
            componentname = ComponentName.unflattenFromString(s);
        else
            componentname = null;
        j = 0;
        if(componentname != null)
            break MISSING_BLOCK_LABEL_123;
        l = Integer.parseInt(s, 16);
        j = l;
        s = null;
        componentname = null;
_L7:
        this;
        JVM INSTR monitorenter ;
        iterator = getProvidersByClass(-1).values().iterator();
_L5:
        while(iterator.hasNext())  {
            contentproviderrecord = (ContentProviderRecord)iterator.next();
            if(componentname == null)
                break MISSING_BLOCK_LABEL_205;
            if(contentproviderrecord.name.equals(componentname))
                arraylist.add(contentproviderrecord);
        }
          goto _L3
        exception;
        throw exception;
        if(s == null)
            break MISSING_BLOCK_LABEL_235;
        if(!contentproviderrecord.name.flattenToString().contains(s)) goto _L5; else goto _L4
_L4:
        arraylist.add(contentproviderrecord);
          goto _L5
        if(System.identityHashCode(contentproviderrecord) == j)
            arraylist.add(contentproviderrecord);
        if(true) goto _L5; else goto _L6
_L6:
          goto _L3
        RuntimeException runtimeexception;
        runtimeexception;
          goto _L7
    }

    void dumpProvidersLocked(PrintWriter printwriter, boolean flag) {
        if(mGlobalByClass.size() > 0) {
            if(false)
                printwriter.println(" ");
            printwriter.println("  Published content providers (by class):");
            dumpProvidersByClassLocked(printwriter, flag, mGlobalByClass);
        }
        if(mProvidersByClassPerUser.size() > 1) {
            printwriter.println("");
            for(int j = 0; j < mProvidersByClassPerUser.size(); j++) {
                HashMap hashmap = (HashMap)mProvidersByClassPerUser.valueAt(j);
                printwriter.println((new StringBuilder()).append("  User ").append(mProvidersByClassPerUser.keyAt(j)).append(":").toString());
                dumpProvidersByClassLocked(printwriter, flag, hashmap);
                printwriter.println(" ");
            }

        } else
        if(mProvidersByClassPerUser.size() == 1)
            dumpProvidersByClassLocked(printwriter, flag, (HashMap)mProvidersByClassPerUser.valueAt(0));
        if(flag) {
            printwriter.println(" ");
            printwriter.println("  Authority to provider mappings:");
            dumpProvidersByNameLocked(printwriter, mGlobalByName);
            for(int i = 0; i < mProvidersByNamePerUser.size(); i++) {
                if(i > 0)
                    printwriter.println((new StringBuilder()).append("  User ").append(mProvidersByNamePerUser.keyAt(i)).append(":").toString());
                dumpProvidersByNameLocked(printwriter, (HashMap)mProvidersByNamePerUser.valueAt(i));
            }

        }
    }

    ContentProviderRecord getProviderByClass(ComponentName componentname) {
        return getProviderByClass(componentname, -1);
    }

    ContentProviderRecord getProviderByClass(ComponentName componentname, int i) {
        ContentProviderRecord contentproviderrecord = (ContentProviderRecord)mGlobalByClass.get(componentname);
        if(contentproviderrecord == null)
            contentproviderrecord = (ContentProviderRecord)getProvidersByClass(i).get(componentname);
        return contentproviderrecord;
    }

    ContentProviderRecord getProviderByName(String s) {
        return getProviderByName(s, -1);
    }

    ContentProviderRecord getProviderByName(String s, int i) {
        ContentProviderRecord contentproviderrecord = (ContentProviderRecord)mGlobalByName.get(s);
        if(contentproviderrecord == null)
            contentproviderrecord = (ContentProviderRecord)getProvidersByName(i).get(s);
        return contentproviderrecord;
    }

    HashMap getProvidersByClass(int i) {
        int j;
        HashMap hashmap;
        HashMap hashmap1;
        if(i >= 0)
            j = i;
        else
            j = Binder.getOrigCallingUser();
        hashmap = (HashMap)mProvidersByClassPerUser.get(j);
        if(hashmap == null) {
            hashmap1 = new HashMap();
            mProvidersByClassPerUser.put(j, hashmap1);
        } else {
            hashmap1 = hashmap;
        }
        return hashmap1;
    }

    void putProviderByClass(ComponentName componentname, ContentProviderRecord contentproviderrecord) {
        if(contentproviderrecord.appInfo.uid < 10000)
            mGlobalByClass.put(componentname, contentproviderrecord);
        else
            getProvidersByClass(UserId.getUserId(contentproviderrecord.appInfo.uid)).put(componentname, contentproviderrecord);
    }

    void putProviderByName(String s, ContentProviderRecord contentproviderrecord) {
        if(contentproviderrecord.appInfo.uid < 10000)
            mGlobalByName.put(s, contentproviderrecord);
        else
            getProvidersByName(UserId.getUserId(contentproviderrecord.appInfo.uid)).put(s, contentproviderrecord);
    }

    void removeProviderByClass(ComponentName componentname, int i) {
        if(mGlobalByClass.containsKey(componentname))
            mGlobalByClass.remove(componentname);
        else
            getProvidersByClass(i).remove(componentname);
    }

    void removeProviderByName(String s, int i) {
        if(mGlobalByName.containsKey(s))
            mGlobalByName.remove(s);
        else
            getProvidersByName(i).remove(s);
    }

    private static final boolean DBG = false;
    private static final String TAG = "ProviderMap";
    private final HashMap mGlobalByClass = new HashMap();
    private final HashMap mGlobalByName = new HashMap();
    private final SparseArray mProvidersByClassPerUser = new SparseArray();
    private final SparseArray mProvidersByNamePerUser = new SparseArray();
}
