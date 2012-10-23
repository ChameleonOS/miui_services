// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.content.pm.*;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.*;
import android.text.TextUtils;
import android.util.Slog;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSubtype;
import com.android.internal.content.PackageMonitor;
import com.android.internal.textservice.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import org.xmlpull.v1.XmlPullParserException;

public class TextServicesManagerService extends com.android.internal.textservice.ITextServicesManager.Stub {
    private class InternalDeathRecipient
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            mGroup.removeListener(mScListener);
        }

        public boolean hasSpellCheckerListener(ISpellCheckerSessionListener ispellcheckersessionlistener) {
            return ispellcheckersessionlistener.asBinder().equals(mScListener.asBinder());
        }

        public final Bundle mBundle;
        private final SpellCheckerBindGroup mGroup;
        public final ISpellCheckerSessionListener mScListener;
        public final String mScLocale;
        public final ITextServicesSessionListener mTsListener;
        public final int mUid;
        final TextServicesManagerService this$0;


        public InternalDeathRecipient(SpellCheckerBindGroup spellcheckerbindgroup, ITextServicesSessionListener itextservicessessionlistener, String s, ISpellCheckerSessionListener ispellcheckersessionlistener, int i, Bundle bundle) {
            this$0 = TextServicesManagerService.this;
            super();
            mTsListener = itextservicessessionlistener;
            mScListener = ispellcheckersessionlistener;
            mScLocale = s;
            mGroup = spellcheckerbindgroup;
            mUid = i;
            mBundle = bundle;
        }
    }

    private class InternalServiceConnection
        implements ServiceConnection {

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            ISpellCheckerService ispellcheckerservice = com.android.internal.textservice.ISpellCheckerService.Stub.asInterface(ibinder);
            SpellCheckerBindGroup spellcheckerbindgroup = (SpellCheckerBindGroup)mSpellCheckerBindGroups.get(mSciId);
            if(spellcheckerbindgroup != null && this == spellcheckerbindgroup.mInternalConnection)
                spellcheckerbindgroup.onServiceConnected(ispellcheckerservice);
            return;
        }

        public void onServiceDisconnected(ComponentName componentname) {
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            SpellCheckerBindGroup spellcheckerbindgroup = (SpellCheckerBindGroup)mSpellCheckerBindGroups.get(mSciId);
            if(spellcheckerbindgroup != null && this == spellcheckerbindgroup.mInternalConnection)
                mSpellCheckerBindGroups.remove(mSciId);
            return;
        }

        private final Bundle mBundle;
        private final String mLocale;
        private final String mSciId;
        final TextServicesManagerService this$0;


        public InternalServiceConnection(String s, String s1, Bundle bundle) {
            this$0 = TextServicesManagerService.this;
            super();
            mSciId = s;
            mLocale = s1;
            mBundle = bundle;
        }
    }

    private class SpellCheckerBindGroup {

        private void cleanLocked() {
            if(mBound && mListeners.isEmpty()) {
                mBound = false;
                String s = mInternalConnection.mSciId;
                if((SpellCheckerBindGroup)mSpellCheckerBindGroups.get(s) == this)
                    mSpellCheckerBindGroups.remove(s);
                mContext.unbindService(mInternalConnection);
            }
        }

        public InternalDeathRecipient addListener(ITextServicesSessionListener itextservicessessionlistener, String s, ISpellCheckerSessionListener ispellcheckersessionlistener, int i, Bundle bundle) {
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            int j;
            int k;
            j = mListeners.size();
            k = 0;
_L9:
            if(k >= j) goto _L2; else goto _L1
_L1:
            boolean flag = ((InternalDeathRecipient)mListeners.get(k)).hasSpellCheckerListener(ispellcheckersessionlistener);
            if(!flag) goto _L4; else goto _L3
_L3:
            hashmap;
            JVM INSTR monitorexit ;
            InternalDeathRecipient internaldeathrecipient1 = null;
_L6:
            return internaldeathrecipient1;
_L4:
            k++;
            continue; /* Loop/switch isn't completed */
_L2:
            InternalDeathRecipient internaldeathrecipient;
            Exception exception;
            try {
                internaldeathrecipient = new InternalDeathRecipient(this, itextservicessessionlistener, s, ispellcheckersessionlistener, i, bundle);
            }
            catch(RemoteException remoteexception) {
                internaldeathrecipient = null;
                break MISSING_BLOCK_LABEL_114;
            }
            finally { }
            try {
                ispellcheckersessionlistener.asBinder().linkToDeath(internaldeathrecipient, 0);
                mListeners.add(internaldeathrecipient);
            }
            catch(RemoteException remoteexception1) { }
            cleanLocked();
            hashmap;
            JVM INSTR monitorexit ;
            internaldeathrecipient1 = internaldeathrecipient;
            if(true) goto _L6; else goto _L5
_L5:
            throw exception;
            exception;
            if(true) goto _L5; else goto _L7
_L7:
            if(true) goto _L9; else goto _L8
_L8:
        }

        public void onServiceConnected(ISpellCheckerService ispellcheckerservice) {
            Iterator iterator = mListeners.iterator();
_L2:
            InternalDeathRecipient internaldeathrecipient;
            if(!iterator.hasNext())
                break; /* Loop/switch isn't completed */
            internaldeathrecipient = (InternalDeathRecipient)iterator.next();
            com.android.internal.textservice.ISpellCheckerSession ispellcheckersession = ispellcheckerservice.getISpellCheckerSession(internaldeathrecipient.mScLocale, internaldeathrecipient.mScListener, internaldeathrecipient.mBundle);
            HashMap hashmap1 = mSpellCheckerMap;
            hashmap1;
            JVM INSTR monitorenter ;
            if(mListeners.contains(internaldeathrecipient))
                internaldeathrecipient.mTsListener.onServiceConnected(ispellcheckersession);
            if(true) goto _L2; else goto _L1
_L1:
            break; /* Loop/switch isn't completed */
            RemoteException remoteexception;
            remoteexception;
            Slog.e(TAG, "Exception in getting the spell checker session.Reconnect to the spellchecker. ", remoteexception);
            removeAll();
_L4:
            return;
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            mSpellChecker = ispellcheckerservice;
            mConnected = true;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public void removeAll() {
            Slog.e(TAG, "Remove the spell checker bind unexpectedly.");
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            int i = mListeners.size();
            for(int j = 0; j < i; j++) {
                InternalDeathRecipient internaldeathrecipient = (InternalDeathRecipient)mListeners.get(j);
                internaldeathrecipient.mScListener.asBinder().unlinkToDeath(internaldeathrecipient, 0);
            }

            mListeners.clear();
            cleanLocked();
            return;
        }

        public void removeListener(ISpellCheckerSessionListener ispellcheckersessionlistener) {
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            int i = mListeners.size();
            ArrayList arraylist = new ArrayList();
            int j = 0;
            do {
                if(j < i) {
                    InternalDeathRecipient internaldeathrecipient1 = (InternalDeathRecipient)mListeners.get(j);
                    if(internaldeathrecipient1.hasSpellCheckerListener(ispellcheckersessionlistener))
                        arraylist.add(internaldeathrecipient1);
                } else {
                    int k = arraylist.size();
                    for(int l = 0; l < k; l++) {
                        InternalDeathRecipient internaldeathrecipient = (InternalDeathRecipient)arraylist.get(l);
                        internaldeathrecipient.mScListener.asBinder().unlinkToDeath(internaldeathrecipient, 0);
                        mListeners.remove(internaldeathrecipient);
                    }

                    cleanLocked();
                    return;
                }
                j++;
            } while(true);
        }

        private final String TAG = com/android/server/TextServicesManagerService$SpellCheckerBindGroup.getSimpleName();
        public boolean mBound;
        public boolean mConnected;
        private final InternalServiceConnection mInternalConnection;
        private final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
        public ISpellCheckerService mSpellChecker;
        final TextServicesManagerService this$0;



        public SpellCheckerBindGroup(InternalServiceConnection internalserviceconnection, ITextServicesSessionListener itextservicessessionlistener, String s, ISpellCheckerSessionListener ispellcheckersessionlistener, int i, Bundle bundle) {
            this$0 = TextServicesManagerService.this;
            super();
            mInternalConnection = internalserviceconnection;
            mBound = true;
            mConnected = false;
            addListener(itextservicessessionlistener, s, ispellcheckersessionlistener, i, bundle);
        }
    }

    private class TextServicesMonitor extends PackageMonitor {

        public void onSomePackagesChanged() {
            HashMap hashmap = mSpellCheckerMap;
            hashmap;
            JVM INSTR monitorenter ;
            TextServicesManagerService.buildSpellCheckerMapLocked(mContext, mSpellCheckerList, mSpellCheckerMap);
            SpellCheckerInfo spellcheckerinfo = getCurrentSpellChecker(null);
            if(spellcheckerinfo != null) {
                String s = spellcheckerinfo.getPackageName();
                int i = isPackageDisappearing(s);
                if(i == 3 || i == 2 || isPackageModified(s)) {
                    SpellCheckerInfo spellcheckerinfo1 = findAvailSpellCheckerLocked(null, s);
                    if(spellcheckerinfo1 != null)
                        setCurrentSpellCheckerLocked(spellcheckerinfo1.getId());
                }
            }
            return;
        }

        final TextServicesManagerService this$0;

        private TextServicesMonitor() {
            this$0 = TextServicesManagerService.this;
            super();
        }

    }


    public TextServicesManagerService(Context context) {
        mSpellCheckerMap = new HashMap();
        mSpellCheckerList = new ArrayList();
        mSpellCheckerBindGroups = new HashMap();
        mSystemReady = false;
        mContext = context;
        mMonitor = new TextServicesMonitor();
        mMonitor.register(context, null, true);
        synchronized(mSpellCheckerMap) {
            buildSpellCheckerMapLocked(context, mSpellCheckerList, mSpellCheckerMap);
        }
        if(getCurrentSpellChecker(null) == null) {
            SpellCheckerInfo spellcheckerinfo = findAvailSpellCheckerLocked(null, null);
            if(spellcheckerinfo != null)
                setCurrentSpellCheckerLocked(spellcheckerinfo.getId());
        }
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static void buildSpellCheckerMapLocked(Context context, ArrayList arraylist, HashMap hashmap) {
        List list;
        int i;
        int j;
        arraylist.clear();
        hashmap.clear();
        list = context.getPackageManager().queryIntentServices(new Intent("android.service.textservice.SpellCheckerService"), 128);
        i = list.size();
        j = 0;
_L2:
        ResolveInfo resolveinfo;
        ComponentName componentname;
        if(j >= i)
            break MISSING_BLOCK_LABEL_286;
        resolveinfo = (ResolveInfo)list.get(j);
        ServiceInfo serviceinfo = resolveinfo.serviceInfo;
        componentname = new ComponentName(((ComponentInfo) (serviceinfo)).packageName, ((ComponentInfo) (serviceinfo)).name);
        if("android.permission.BIND_TEXT_SERVICE".equals(serviceinfo.permission))
            break; /* Loop/switch isn't completed */
        Slog.w(TAG, (new StringBuilder()).append("Skipping text service ").append(componentname).append(": it does not require the permission ").append("android.permission.BIND_TEXT_SERVICE").toString());
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        SpellCheckerInfo spellcheckerinfo;
        XmlPullParserException xmlpullparserexception;
label0:
        {
            spellcheckerinfo = new SpellCheckerInfo(context, resolveinfo);
            if(spellcheckerinfo.getSubtypeCount() > 0)
                break label0;
            Slog.w(TAG, (new StringBuilder()).append("Skipping text service ").append(componentname).append(": it does not contain subtypes.").toString());
        }
          goto _L3
        try {
            arraylist.add(spellcheckerinfo);
            hashmap.put(spellcheckerinfo.getId(), spellcheckerinfo);
        }
        // Misplaced declaration of an exception variable
        catch(XmlPullParserException xmlpullparserexception) {
            Slog.w(TAG, (new StringBuilder()).append("Unable to load the spell checker ").append(componentname).toString(), xmlpullparserexception);
        }
        catch(IOException ioexception) {
            Slog.w(TAG, (new StringBuilder()).append("Unable to load the spell checker ").append(componentname).toString(), ioexception);
        }
          goto _L3
    }

    private SpellCheckerInfo findAvailSpellCheckerLocked(String s, String s1) {
        int i = mSpellCheckerList.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        SpellCheckerInfo spellcheckerinfo;
        Slog.w(TAG, "no available spell checker services found");
        spellcheckerinfo = null;
_L4:
        return spellcheckerinfo;
_L2:
        if(s1 != null) {
            for(int j = 0; j < i; j++) {
                spellcheckerinfo = (SpellCheckerInfo)mSpellCheckerList.get(j);
                if(s1.equals(spellcheckerinfo.getPackageName()))
                    continue; /* Loop/switch isn't completed */
            }

        }
        if(i > 1)
            Slog.w(TAG, "more than one spell checker service found, picking first");
        spellcheckerinfo = (SpellCheckerInfo)mSpellCheckerList.get(0);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean isSpellCheckerEnabledLocked() {
        boolean flag;
        long l;
        flag = true;
        l = Binder.clearCallingIdentity();
        int i = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "spell_checker_enabled", 1);
        if(i != flag)
            flag = false;
        Binder.restoreCallingIdentity(l);
        return flag;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void setCurrentSpellCheckerLocked(String s) {
        if(!TextUtils.isEmpty(s) && mSpellCheckerMap.containsKey(s)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        SpellCheckerInfo spellcheckerinfo = getCurrentSpellChecker(null);
        if(spellcheckerinfo != null && spellcheckerinfo.getId().equals(s)) goto _L1; else goto _L3
_L3:
        long l = Binder.clearCallingIdentity();
        android.provider.Settings.Secure.putString(mContext.getContentResolver(), "selected_spell_checker", s);
        setCurrentSpellCheckerSubtypeLocked(0);
        Binder.restoreCallingIdentity(l);
          goto _L1
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void setCurrentSpellCheckerSubtypeLocked(int i) {
        SpellCheckerInfo spellcheckerinfo;
        int j;
        int k;
        spellcheckerinfo = getCurrentSpellChecker(null);
        j = 0;
        k = 0;
_L2:
        long l;
        if(spellcheckerinfo != null && k < spellcheckerinfo.getSubtypeCount()) {
            if(spellcheckerinfo.getSubtypeAt(k).hashCode() != i)
                break MISSING_BLOCK_LABEL_68;
            j = i;
        }
        l = Binder.clearCallingIdentity();
        android.provider.Settings.Secure.putString(mContext.getContentResolver(), "selected_spell_checker_subtype", String.valueOf(j));
        Binder.restoreCallingIdentity(l);
        return;
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void setSpellCheckerEnabledLocked(boolean flag) {
        long l = Binder.clearCallingIdentity();
        android.content.ContentResolver contentresolver;
        int i;
        contentresolver = mContext.getContentResolver();
        if(!flag)
            break MISSING_BLOCK_LABEL_35;
        i = 1;
_L1:
        android.provider.Settings.Secure.putInt(contentresolver, "spell_checker_enabled", i);
        Binder.restoreCallingIdentity(l);
        return;
        i = 0;
          goto _L1
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void startSpellCheckerServiceInnerLocked(SpellCheckerInfo spellcheckerinfo, String s, ITextServicesSessionListener itextservicessessionlistener, ISpellCheckerSessionListener ispellcheckersessionlistener, int i, Bundle bundle) {
        String s1 = spellcheckerinfo.getId();
        InternalServiceConnection internalserviceconnection = new InternalServiceConnection(s1, s, bundle);
        Intent intent = new Intent("android.service.textservice.SpellCheckerService");
        intent.setComponent(spellcheckerinfo.getComponent());
        if(!mContext.bindService(intent, internalserviceconnection, 1)) {
            Slog.e(TAG, "Failed to get a spell checker service.");
        } else {
            SpellCheckerBindGroup spellcheckerbindgroup = new SpellCheckerBindGroup(internalserviceconnection, itextservicessessionlistener, s, ispellcheckersessionlistener, i, bundle);
            mSpellCheckerBindGroups.put(s1, spellcheckerbindgroup);
        }
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump TextServicesManagerService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        printwriter.println("Current Text Services Manager state:");
        printwriter.println("  Spell Checker Map:");
        for(Iterator iterator = mSpellCheckerMap.entrySet().iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
            printwriter.print("    ");
            printwriter.print((String)entry1.getKey());
            printwriter.println(":");
            SpellCheckerInfo spellcheckerinfo = (SpellCheckerInfo)entry1.getValue();
            printwriter.print("      ");
            printwriter.print("id=");
            printwriter.println(spellcheckerinfo.getId());
            printwriter.print("      ");
            printwriter.print("comp=");
            printwriter.println(spellcheckerinfo.getComponent().toShortString());
            int k = spellcheckerinfo.getSubtypeCount();
            int l = 0;
            while(l < k)  {
                SpellCheckerSubtype spellcheckersubtype = spellcheckerinfo.getSubtypeAt(l);
                printwriter.print("      ");
                printwriter.print("Subtype #");
                printwriter.print(l);
                printwriter.println(":");
                printwriter.print("        ");
                printwriter.print("locale=");
                printwriter.println(spellcheckersubtype.getLocale());
                printwriter.print("        ");
                printwriter.print("extraValue=");
                printwriter.println(spellcheckersubtype.getExtraValue());
                l++;
            }
        }

        printwriter.println("");
        printwriter.println("  Spell Checker Bind Groups:");
        for(Iterator iterator1 = mSpellCheckerBindGroups.entrySet().iterator(); iterator1.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            SpellCheckerBindGroup spellcheckerbindgroup = (SpellCheckerBindGroup)entry.getValue();
            printwriter.print("    ");
            printwriter.print((String)entry.getKey());
            printwriter.print(" ");
            printwriter.print(spellcheckerbindgroup);
            printwriter.println(":");
            printwriter.print("      ");
            printwriter.print("mInternalConnection=");
            printwriter.println(spellcheckerbindgroup.mInternalConnection);
            printwriter.print("      ");
            printwriter.print("mSpellChecker=");
            printwriter.println(spellcheckerbindgroup.mSpellChecker);
            printwriter.print("      ");
            printwriter.print("mBound=");
            printwriter.print(spellcheckerbindgroup.mBound);
            printwriter.print(" mConnected=");
            printwriter.println(spellcheckerbindgroup.mConnected);
            int i = spellcheckerbindgroup.mListeners.size();
            int j = 0;
            while(j < i)  {
                InternalDeathRecipient internaldeathrecipient = (InternalDeathRecipient)spellcheckerbindgroup.mListeners.get(j);
                printwriter.print("      ");
                printwriter.print("Listener #");
                printwriter.print(j);
                printwriter.println(":");
                printwriter.print("        ");
                printwriter.print("mTsListener=");
                printwriter.println(internaldeathrecipient.mTsListener);
                printwriter.print("        ");
                printwriter.print("mScListener=");
                printwriter.println(internaldeathrecipient.mScListener);
                printwriter.print("        ");
                printwriter.print("mGroup=");
                printwriter.println(internaldeathrecipient.mGroup);
                printwriter.print("        ");
                printwriter.print("mScLocale=");
                printwriter.print(internaldeathrecipient.mScLocale);
                printwriter.print(" mUid=");
                printwriter.println(internaldeathrecipient.mUid);
                j++;
            }
        }

        if(true) goto _L4; else goto _L3
_L3:
    }

    public void finishSpellCheckerService(ISpellCheckerSessionListener ispellcheckersessionlistener) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        arraylist = new ArrayList();
        Iterator iterator = mSpellCheckerBindGroups.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            SpellCheckerBindGroup spellcheckerbindgroup = (SpellCheckerBindGroup)iterator.next();
            if(spellcheckerbindgroup != null)
                arraylist.add(spellcheckerbindgroup);
        } while(true);
        break MISSING_BLOCK_LABEL_73;
        Exception exception;
        exception;
        throw exception;
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
            ((SpellCheckerBindGroup)arraylist.get(j)).removeListener(ispellcheckersessionlistener);

        hashmap;
        JVM INSTR monitorexit ;
    }

    public SpellCheckerInfo getCurrentSpellChecker(String s) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        String s1 = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "selected_spell_checker");
        SpellCheckerInfo spellcheckerinfo;
        if(TextUtils.isEmpty(s1))
            spellcheckerinfo = null;
        else
            spellcheckerinfo = (SpellCheckerInfo)mSpellCheckerMap.get(s1);
        return spellcheckerinfo;
    }

    public SpellCheckerSubtype getCurrentSpellCheckerSubtype(String s, boolean flag) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        String s1;
        SpellCheckerInfo spellcheckerinfo;
        s1 = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "selected_spell_checker_subtype");
        spellcheckerinfo = getCurrentSpellChecker(null);
        if(spellcheckerinfo != null && spellcheckerinfo.getSubtypeCount() != 0) goto _L2; else goto _L1
_L1:
        SpellCheckerSubtype spellcheckersubtype = null;
          goto _L3
_L2:
        Exception exception;
        int i;
        if(!TextUtils.isEmpty(s1))
            i = Integer.valueOf(s1).intValue();
        else
            i = 0;
        if(i != 0 || flag) goto _L5; else goto _L4
_L4:
        spellcheckersubtype = null;
          goto _L3
        exception;
        throw exception;
_L5:
        String s2 = null;
        if(i != 0) goto _L7; else goto _L6
_L6:
        InputMethodManager inputmethodmanager = (InputMethodManager)mContext.getSystemService("input_method");
        if(inputmethodmanager != null) {
            InputMethodSubtype inputmethodsubtype = inputmethodmanager.getCurrentInputMethodSubtype();
            if(inputmethodsubtype != null) {
                String s4 = inputmethodsubtype.getLocale();
                if(!TextUtils.isEmpty(s4))
                    s2 = s4;
            }
        }
        if(s2 == null)
            s2 = mContext.getResources().getConfiguration().locale.toString();
          goto _L7
_L14:
        int j;
        if(j >= spellcheckerinfo.getSubtypeCount()) goto _L9; else goto _L8
_L8:
        spellcheckersubtype = spellcheckerinfo.getSubtypeAt(j);
        if(i != 0) goto _L11; else goto _L10
_L10:
        String s3 = spellcheckersubtype.getLocale();
        if(!s2.equals(s3)) goto _L13; else goto _L12
_L12:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L13:
        SpellCheckerSubtype spellcheckersubtype1;
        if(spellcheckersubtype1 == null && s2.length() >= 2 && s3.length() >= 2 && s2.startsWith(s3))
            spellcheckersubtype1 = spellcheckersubtype;
        break MISSING_BLOCK_LABEL_302;
_L11:
        if(spellcheckersubtype.hashCode() != i)
            break MISSING_BLOCK_LABEL_302;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L3
_L9:
        hashmap;
        JVM INSTR monitorexit ;
        spellcheckersubtype = spellcheckersubtype1;
_L3:
        return spellcheckersubtype;
_L7:
        spellcheckersubtype1 = null;
        j = 0;
          goto _L14
        j++;
          goto _L14
    }

    public SpellCheckerInfo[] getEnabledSpellCheckers() {
        return (SpellCheckerInfo[])mSpellCheckerList.toArray(new SpellCheckerInfo[mSpellCheckerList.size()]);
    }

    public void getSpellCheckerService(String s, String s1, ITextServicesSessionListener itextservicessessionlistener, ISpellCheckerSessionListener ispellcheckersessionlistener, Bundle bundle) {
        if(mSystemReady) {
label0:
            {
                if(!TextUtils.isEmpty(s) && itextservicessessionlistener != null && ispellcheckersessionlistener != null)
                    break label0;
                Slog.e(TAG, "getSpellCheckerService: Invalid input.");
            }
        }
_L1:
        return;
        hashmap;
        JVM INSTR monitorenter ;
        if(mSpellCheckerMap.containsKey(s)) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_71;
        Exception exception;
        exception;
        throw exception;
        SpellCheckerInfo spellcheckerinfo;
        int i;
        spellcheckerinfo = (SpellCheckerInfo)mSpellCheckerMap.get(s);
        i = Binder.getCallingUid();
        if(!mSpellCheckerBindGroups.containsKey(s)) goto _L4; else goto _L3
_L3:
        SpellCheckerBindGroup spellcheckerbindgroup = (SpellCheckerBindGroup)mSpellCheckerBindGroups.get(s);
        if(spellcheckerbindgroup == null) goto _L4; else goto _L5
_L5:
        InternalDeathRecipient internaldeathrecipient = ((SpellCheckerBindGroup)mSpellCheckerBindGroups.get(s)).addListener(itextservicessessionlistener, s1, ispellcheckersessionlistener, i, bundle);
        if(internaldeathrecipient != null) goto _L7; else goto _L6
_L6:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L7:
        boolean flag;
        if(spellcheckerbindgroup.mSpellChecker != null)
            break MISSING_BLOCK_LABEL_331;
        flag = true;
_L13:
        if(!(flag & spellcheckerbindgroup.mConnected)) goto _L9; else goto _L8
_L8:
        Slog.e(TAG, "The state of the spell checker bind group is illegal.");
        spellcheckerbindgroup.removeAll();
_L4:
        long l = Binder.clearCallingIdentity();
        startSpellCheckerServiceInnerLocked(spellcheckerinfo, s1, itextservicessessionlistener, ispellcheckersessionlistener, i, bundle);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L9:
        ISpellCheckerService ispellcheckerservice = spellcheckerbindgroup.mSpellChecker;
        if(ispellcheckerservice == null) goto _L4; else goto _L10
_L10:
        com.android.internal.textservice.ISpellCheckerSession ispellcheckersession = spellcheckerbindgroup.mSpellChecker.getISpellCheckerSession(internaldeathrecipient.mScLocale, internaldeathrecipient.mScListener, bundle);
        if(ispellcheckersession == null) goto _L12; else goto _L11
_L11:
        itextservicessessionlistener.onServiceConnected(ispellcheckersession);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L12:
        spellcheckerbindgroup.removeAll();
          goto _L4
        RemoteException remoteexception;
        remoteexception;
        Slog.e(TAG, (new StringBuilder()).append("Exception in getting spell checker session: ").append(remoteexception).toString());
        spellcheckerbindgroup.removeAll();
          goto _L4
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        flag = false;
          goto _L13
    }

    public boolean isSpellCheckerEnabled() {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag = isSpellCheckerEnabledLocked();
        return flag;
    }

    public void setCurrentSpellChecker(String s, String s1) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0)
            throw new SecurityException("Requires permission android.permission.WRITE_SECURE_SETTINGS");
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        throw exception;
        setCurrentSpellCheckerLocked(s1);
        hashmap;
        JVM INSTR monitorexit ;
    }

    public void setCurrentSpellCheckerSubtype(String s, int i) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0)
            throw new SecurityException("Requires permission android.permission.WRITE_SECURE_SETTINGS");
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        throw exception;
        setCurrentSpellCheckerSubtypeLocked(i);
        hashmap;
        JVM INSTR monitorexit ;
    }

    public void setSpellCheckerEnabled(boolean flag) {
        HashMap hashmap = mSpellCheckerMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0)
            throw new SecurityException("Requires permission android.permission.WRITE_SECURE_SETTINGS");
        break MISSING_BLOCK_LABEL_36;
        Exception exception;
        exception;
        throw exception;
        setSpellCheckerEnabledLocked(flag);
        hashmap;
        JVM INSTR monitorexit ;
    }

    public void systemReady() {
        if(!mSystemReady)
            mSystemReady = true;
    }

    private static final boolean DBG;
    private static final String TAG = com/android/server/TextServicesManagerService.getSimpleName();
    private final Context mContext;
    private final TextServicesMonitor mMonitor;
    private final HashMap mSpellCheckerBindGroups;
    private final ArrayList mSpellCheckerList;
    private final HashMap mSpellCheckerMap;
    private boolean mSystemReady;








}
