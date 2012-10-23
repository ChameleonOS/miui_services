// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.appwidget.AppWidgetProviderInfo;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.net.Uri;
import android.os.*;
import android.util.*;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.os.AtomicFile;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.widget.IRemoteViewsAdapterConnection;
import com.android.internal.widget.IRemoteViewsFactory;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;

class AppWidgetServiceImpl {
    static class ServiceConnectionProxy
        implements ServiceConnection {

        public void disconnect() {
            IRemoteViewsAdapterConnection iremoteviewsadapterconnection = com.android.internal.widget.IRemoteViewsAdapterConnection.Stub.asInterface(mConnectionCb);
            iremoteviewsadapterconnection.onServiceDisconnected();
_L1:
            return;
            Exception exception;
            exception;
            exception.printStackTrace();
              goto _L1
        }

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            IRemoteViewsAdapterConnection iremoteviewsadapterconnection = com.android.internal.widget.IRemoteViewsAdapterConnection.Stub.asInterface(mConnectionCb);
            iremoteviewsadapterconnection.onServiceConnected(ibinder);
_L1:
            return;
            Exception exception;
            exception;
            exception.printStackTrace();
              goto _L1
        }

        public void onServiceDisconnected(ComponentName componentname) {
            disconnect();
        }

        private final IBinder mConnectionCb;

        ServiceConnectionProxy(Pair pair, IBinder ibinder) {
            mConnectionCb = ibinder;
        }
    }

    static class AppWidgetId {

        int appWidgetId;
        Host host;
        Bundle options;
        Provider provider;
        RemoteViews views;

        AppWidgetId() {
        }
    }

    static class Host {

        IAppWidgetHost callbacks;
        int hostId;
        ArrayList instances;
        String packageName;
        int tag;
        int uid;
        boolean zombie;

        Host() {
            instances = new ArrayList();
        }
    }

    static class Provider {

        PendingIntent broadcast;
        AppWidgetProviderInfo info;
        ArrayList instances;
        int tag;
        int uid;
        boolean zombie;

        Provider() {
            instances = new ArrayList();
        }
    }


    AppWidgetServiceImpl(Context context, int i) {
        mInstalledProviders = new ArrayList();
        mNextAppWidgetId = 1;
        mHosts = new ArrayList();
        mPackagesWithBindWidgetPermission = new HashSet();
        mDeletedProviders = new ArrayList();
        mDeletedHosts = new ArrayList();
        mContext = context;
        mPm = AppGlobals.getPackageManager();
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        mUserId = i;
        computeMaximumWidgetBitmapMemory();
    }

    private void bindAppWidgetIdImpl(int i, ComponentName componentname) {
        long l = Binder.clearCallingIdentity();
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        AppWidgetId appwidgetid;
        ensureStateLoadedLocked();
        appwidgetid = lookupAppWidgetIdLocked(i);
        if(appwidgetid == null)
            throw new IllegalArgumentException("bad appWidgetId");
        break MISSING_BLOCK_LABEL_56;
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        if(appwidgetid.provider != null)
            throw new IllegalArgumentException((new StringBuilder()).append("appWidgetId ").append(i).append(" already bound to ").append(appwidgetid.provider.info.provider).toString());
        Provider provider = lookupProviderLocked(componentname);
        if(provider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("not a appwidget provider: ").append(componentname).toString());
        if(provider.zombie)
            throw new IllegalArgumentException((new StringBuilder()).append("can't bind to a 3rd party provider in safe mode: ").append(componentname).toString());
        appwidgetid.provider = provider;
        provider.instances.add(appwidgetid);
        if(provider.instances.size() == 1)
            sendEnableIntentLocked(provider);
        int ai[] = new int[1];
        ai[0] = i;
        sendUpdateIntentLocked(provider, ai);
        registerForBroadcastsLocked(provider, getAppWidgetIds(provider));
        saveStateLocked();
        arraylist;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
    }

    private boolean callerHasBindAppWidgetPermission(String s) {
        boolean flag;
        flag = false;
        int i = Binder.getCallingUid();
        boolean flag1;
        try {
            flag1 = UserId.isSameApp(i, getUidForPackage(s));
        }
        catch(Exception exception) {
            continue; /* Loop/switch isn't completed */
        }
        if(flag1) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        flag = mPackagesWithBindWidgetPermission.contains(s);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void decrementAppWidgetServiceRefCount(AppWidgetId appwidgetid) {
        Iterator iterator = mRemoteViewsServicesAppWidgets.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            android.content.Intent.FilterComparison filtercomparison = (android.content.Intent.FilterComparison)iterator.next();
            HashSet hashset = (HashSet)mRemoteViewsServicesAppWidgets.get(filtercomparison);
            if(hashset.remove(Integer.valueOf(appwidgetid.appWidgetId)) && hashset.isEmpty()) {
                destroyRemoteViewsService(filtercomparison.getIntent(), appwidgetid);
                iterator.remove();
            }
        } while(true);
    }

    private void destroyRemoteViewsService(final Intent intent, AppWidgetId appwidgetid) {
        ServiceConnection serviceconnection;
        int i;
        long l;
        serviceconnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
                IRemoteViewsFactory iremoteviewsfactory = com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(ibinder);
                try {
                    iremoteviewsfactory.onDestroy(intent);
                }
                catch(RemoteException remoteexception) {
                    remoteexception.printStackTrace();
                }
                catch(RuntimeException runtimeexception) {
                    runtimeexception.printStackTrace();
                }
                mContext.unbindService(this);
            }

            public void onServiceDisconnected(ComponentName componentname) {
            }

            final AppWidgetServiceImpl this$0;
            final Intent val$intent;

             {
                this$0 = AppWidgetServiceImpl.this;
                intent = intent1;
                super();
            }
        };
        i = UserId.getUserId(appwidgetid.provider.uid);
        l = Binder.clearCallingIdentity();
        mContext.bindService(intent, serviceconnection, 1, i);
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void dumpAppWidgetId(AppWidgetId appwidgetid, int i, PrintWriter printwriter) {
        printwriter.print("  [");
        printwriter.print(i);
        printwriter.print("] id=");
        printwriter.println(appwidgetid.appWidgetId);
        printwriter.print("    hostId=");
        printwriter.print(appwidgetid.host.hostId);
        printwriter.print(' ');
        printwriter.print(appwidgetid.host.packageName);
        printwriter.print('/');
        printwriter.println(appwidgetid.host.uid);
        if(appwidgetid.provider != null) {
            printwriter.print("    provider=");
            printwriter.println(appwidgetid.provider.info.provider.flattenToShortString());
        }
        if(appwidgetid.host != null) {
            printwriter.print("    host.callbacks=");
            printwriter.println(appwidgetid.host.callbacks);
        }
        if(appwidgetid.views != null) {
            printwriter.print("    views=");
            printwriter.println(appwidgetid.views);
        }
    }

    private void dumpHost(Host host, int i, PrintWriter printwriter) {
        printwriter.print("  [");
        printwriter.print(i);
        printwriter.print("] hostId=");
        printwriter.print(host.hostId);
        printwriter.print(' ');
        printwriter.print(host.packageName);
        printwriter.print('/');
        printwriter.print(host.uid);
        printwriter.println(':');
        printwriter.print("    callbacks=");
        printwriter.println(host.callbacks);
        printwriter.print("    instances.size=");
        printwriter.print(host.instances.size());
        printwriter.print(" zombie=");
        printwriter.println(host.zombie);
    }

    private void dumpProvider(Provider provider, int i, PrintWriter printwriter) {
        AppWidgetProviderInfo appwidgetproviderinfo = provider.info;
        printwriter.print("  [");
        printwriter.print(i);
        printwriter.print("] provider ");
        printwriter.print(appwidgetproviderinfo.provider.flattenToShortString());
        printwriter.println(':');
        printwriter.print("    min=(");
        printwriter.print(appwidgetproviderinfo.minWidth);
        printwriter.print("x");
        printwriter.print(appwidgetproviderinfo.minHeight);
        printwriter.print(")   minResize=(");
        printwriter.print(appwidgetproviderinfo.minResizeWidth);
        printwriter.print("x");
        printwriter.print(appwidgetproviderinfo.minResizeHeight);
        printwriter.print(") updatePeriodMillis=");
        printwriter.print(appwidgetproviderinfo.updatePeriodMillis);
        printwriter.print(" resizeMode=");
        printwriter.print(appwidgetproviderinfo.resizeMode);
        printwriter.print(" autoAdvanceViewId=");
        printwriter.print(appwidgetproviderinfo.autoAdvanceViewId);
        printwriter.print(" initialLayout=#");
        printwriter.print(Integer.toHexString(appwidgetproviderinfo.initialLayout));
        printwriter.print(" zombie=");
        printwriter.println(provider.zombie);
    }

    private void ensureStateLoadedLocked() {
        if(!mStateLoaded) {
            loadAppWidgetList();
            loadStateLocked();
            mStateLoaded = true;
        }
    }

    static int[] getAppWidgetIds(Provider provider) {
        int i = provider.instances.size();
        int ai[] = new int[i];
        for(int j = 0; j < i; j++)
            ai[j] = ((AppWidgetId)provider.instances.get(j)).appWidgetId;

        return ai;
    }

    static File getSettingsFile(int i) {
        return new File((new StringBuilder()).append("/data/system/users/").append(i).append("/").append("appwidgets.xml").toString());
    }

    private void incrementAppWidgetServiceRefCount(int i, android.content.Intent.FilterComparison filtercomparison) {
        HashSet hashset;
        if(mRemoteViewsServicesAppWidgets.containsKey(filtercomparison)) {
            hashset = (HashSet)mRemoteViewsServicesAppWidgets.get(filtercomparison);
        } else {
            hashset = new HashSet();
            mRemoteViewsServicesAppWidgets.put(filtercomparison, hashset);
        }
        hashset.add(Integer.valueOf(i));
    }

    private Provider parseProviderInfoXml(ComponentName componentname, ResolveInfo resolveinfo) {
        ActivityInfo activityinfo;
        XmlResourceParser xmlresourceparser;
        activityinfo = resolveinfo.activityInfo;
        xmlresourceparser = null;
        xmlresourceparser = activityinfo.loadXmlMetaData(mContext.getPackageManager(), "android.appwidget.provider");
        if(xmlresourceparser != null) goto _L2; else goto _L1
_L1:
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("No android.appwidget.provider meta-data for AppWidget provider '").append(componentname).append('\'').toString());
        Provider provider;
        provider = null;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
_L3:
        return provider;
_L2:
        android.util.AttributeSet attributeset;
        attributeset = Xml.asAttributeSet(xmlresourceparser);
        int i;
        do
            i = xmlresourceparser.next();
        while(i != 1 && i != 2);
        if("appwidget-provider".equals(xmlresourceparser.getName()))
            break MISSING_BLOCK_LABEL_171;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("Meta-data does not start with appwidget-provider tag for AppWidget provider '").append(componentname).append('\'').toString());
        provider = null;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
          goto _L3
        provider = new Provider();
        AppWidgetProviderInfo appwidgetproviderinfo;
        TypedArray typedarray;
        TypedValue typedvalue;
        appwidgetproviderinfo = new AppWidgetProviderInfo();
        provider.info = appwidgetproviderinfo;
        appwidgetproviderinfo.provider = componentname;
        provider.uid = activityinfo.applicationInfo.uid;
        typedarray = mContext.getPackageManager().getResourcesForApplication(activityinfo.applicationInfo).obtainAttributes(attributeset, com.android.internal.R.styleable.AppWidgetProviderInfo);
        typedvalue = typedarray.peekValue(0);
        if(typedvalue == null) goto _L5; else goto _L4
_L4:
        int j = typedvalue.data;
_L10:
        TypedValue typedvalue1;
        appwidgetproviderinfo.minWidth = j;
        typedvalue1 = typedarray.peekValue(1);
        if(typedvalue1 == null) goto _L7; else goto _L6
_L6:
        int k = typedvalue1.data;
_L11:
        TypedValue typedvalue2;
        appwidgetproviderinfo.minHeight = k;
        typedvalue2 = typedarray.peekValue(8);
        if(typedvalue2 == null) goto _L9; else goto _L8
_L8:
        int l = typedvalue2.data;
_L12:
        int i1;
        appwidgetproviderinfo.minResizeWidth = l;
        TypedValue typedvalue3 = typedarray.peekValue(9);
        if(typedvalue3 == null)
            break MISSING_BLOCK_LABEL_515;
        i1 = typedvalue3.data;
_L13:
        appwidgetproviderinfo.minResizeHeight = i1;
        appwidgetproviderinfo.updatePeriodMillis = typedarray.getInt(2, 0);
        appwidgetproviderinfo.initialLayout = typedarray.getResourceId(3, 0);
        String s = typedarray.getString(4);
        if(s != null)
            appwidgetproviderinfo.configure = new ComponentName(componentname.getPackageName(), s);
        appwidgetproviderinfo.label = activityinfo.loadLabel(mContext.getPackageManager()).toString();
        appwidgetproviderinfo.icon = resolveinfo.getIconResource();
        appwidgetproviderinfo.previewImage = typedarray.getResourceId(5, 0);
        appwidgetproviderinfo.autoAdvanceViewId = typedarray.getResourceId(6, -1);
        appwidgetproviderinfo.resizeMode = typedarray.getInt(7, 0);
        typedarray.recycle();
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        provider;
          goto _L3
_L5:
        j = 0;
          goto _L10
_L7:
        k = 0;
          goto _L11
_L9:
        l = appwidgetproviderinfo.minWidth;
          goto _L12
        i1 = appwidgetproviderinfo.minHeight;
          goto _L13
        Exception exception1;
        exception1;
_L16:
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("XML parsing failed for AppWidget provider '").append(componentname).append('\'').toString(), exception1);
        provider = null;
        if(xmlresourceparser != null)
            xmlresourceparser.close();
          goto _L3
        Exception exception;
        exception;
_L15:
        if(xmlresourceparser != null)
            xmlresourceparser.close();
        throw exception;
        exception;
        provider;
        if(true) goto _L15; else goto _L14
_L14:
        exception1;
        provider;
          goto _L16
    }

    private void unbindAppWidgetRemoteViewsServicesLocked(AppWidgetId appwidgetid) {
        int i = appwidgetid.appWidgetId;
        Iterator iterator = mBoundRemoteViewsServices.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break;
            Pair pair = (Pair)iterator.next();
            if(((Integer)pair.first).intValue() == i) {
                ServiceConnectionProxy serviceconnectionproxy = (ServiceConnectionProxy)mBoundRemoteViewsServices.get(pair);
                serviceconnectionproxy.disconnect();
                mContext.unbindService(serviceconnectionproxy);
                iterator.remove();
            }
        } while(true);
        decrementAppWidgetServiceRefCount(appwidgetid);
    }

    boolean addProviderLocked(ResolveInfo resolveinfo) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        while(true)  {
            do
                return flag;
            while((0x40000 & resolveinfo.activityInfo.applicationInfo.flags) != 0 || !resolveinfo.activityInfo.isEnabled());
            Provider provider = parseProviderInfoXml(new ComponentName(((ComponentInfo) (resolveinfo.activityInfo)).packageName, ((ComponentInfo) (resolveinfo.activityInfo)).name), resolveinfo);
            if(provider != null) {
                mInstalledProviders.add(provider);
                flag = true;
            }
        }
    }

    void addProvidersForPackageLocked(String s) {
        Intent intent;
        intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setPackage(s);
        List list = mPm.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(mContext.getContentResolver()), 128, mUserId);
        ResolveInfo resolveinfo;
        ActivityInfo activityinfo;
        RemoteException remoteexception;
        int i;
        int j;
        if(list == null)
            i = 0;
        else
            i = list.size();
        j = 0;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        resolveinfo = (ResolveInfo)list.get(j);
        activityinfo = resolveinfo.activityInfo;
        if((0x40000 & activityinfo.applicationInfo.flags) == 0 && s.equals(((ComponentInfo) (activityinfo)).packageName))
            addProviderLocked(resolveinfo);
        j++;
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_58;
        remoteexception;
_L1:
    }

    public int allocateAppWidgetId(String s, int i) {
        int j = enforceCallingUid(s);
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        int k = mNextAppWidgetId;
        mNextAppWidgetId = k + 1;
        Host host = lookupOrAddHostLocked(j, s, i);
        AppWidgetId appwidgetid = new AppWidgetId();
        appwidgetid.appWidgetId = k;
        appwidgetid.host = host;
        host.instances.add(appwidgetid);
        mAppWidgetIds.add(appwidgetid);
        saveStateLocked();
        return k;
    }

    public void bindAppWidgetId(int i, ComponentName componentname) {
        mContext.enforceCallingPermission("android.permission.BIND_APPWIDGET", (new StringBuilder()).append("bindAppWidgetId appWidgetId=").append(i).append(" provider=").append(componentname).toString());
        bindAppWidgetIdImpl(i, componentname);
    }

    public boolean bindAppWidgetIdIfAllowed(String s, int i, ComponentName componentname) {
        mContext.enforceCallingPermission("android.permission.BIND_APPWIDGET", null);
_L4:
        boolean flag;
        bindAppWidgetIdImpl(i, componentname);
        flag = true;
_L2:
        return flag;
        SecurityException securityexception;
        securityexception;
        if(callerHasBindAppWidgetPermission(s))
            continue; /* Loop/switch isn't completed */
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void bindRemoteViewsService(int i, Intent intent, IBinder ibinder) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        AppWidgetId appwidgetid;
        ensureStateLoadedLocked();
        appwidgetid = lookupAppWidgetIdLocked(i);
        if(appwidgetid == null)
            throw new IllegalArgumentException("bad appWidgetId");
        break MISSING_BLOCK_LABEL_43;
        Exception exception;
        exception;
        throw exception;
        ComponentName componentname = intent.getComponent();
        try {
            if(!"android.permission.BIND_REMOTEVIEWS".equals(mContext.getPackageManager().getServiceInfo(componentname, 4096).permission))
                throw new SecurityException((new StringBuilder()).append("Selected service does not require android.permission.BIND_REMOTEVIEWS: ").append(componentname).toString());
            break MISSING_BLOCK_LABEL_136;
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) { }
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown component ").append(componentname).toString());
        android.content.Intent.FilterComparison filtercomparison;
        Pair pair;
        int j;
        long l;
        ServiceConnectionProxy serviceconnectionproxy = null;
        filtercomparison = new android.content.Intent.FilterComparison(intent);
        pair = Pair.create(Integer.valueOf(i), filtercomparison);
        if(mBoundRemoteViewsServices.containsKey(pair)) {
            serviceconnectionproxy = (ServiceConnectionProxy)mBoundRemoteViewsServices.get(pair);
            serviceconnectionproxy.disconnect();
            mContext.unbindService(serviceconnectionproxy);
            mBoundRemoteViewsServices.remove(pair);
        }
        serviceconnectionproxy;
        j = UserId.getUserId(appwidgetid.provider.uid);
        l = Binder.clearCallingIdentity();
        ServiceConnectionProxy serviceconnectionproxy1 = new ServiceConnectionProxy(pair, ibinder);
        mContext.bindService(intent, serviceconnectionproxy1, 1, j);
        mBoundRemoteViewsServices.put(pair, serviceconnectionproxy1);
        Binder.restoreCallingIdentity(l);
        incrementAppWidgetServiceRefCount(i, filtercomparison);
        arraylist;
        JVM INSTR monitorexit ;
        return;
_L2:
        Exception exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        exception1;
        continue; /* Loop/switch isn't completed */
        exception1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    boolean canAccessAppWidgetId(AppWidgetId appwidgetid, int i) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_2;
        if(appwidgetid.host.uid != i && (appwidgetid.provider == null || appwidgetid.provider.uid != i) && mContext.checkCallingOrSelfPermission("android.permission.BIND_APPWIDGET") != 0)
            flag = false;
        return flag;
    }

    void cancelBroadcasts(Provider provider) {
        long l;
        if(provider.broadcast == null)
            break MISSING_BLOCK_LABEL_38;
        mAlarmManager.cancel(provider.broadcast);
        l = Binder.clearCallingIdentity();
        provider.broadcast.cancel();
        Binder.restoreCallingIdentity(l);
        provider.broadcast = null;
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    void computeMaximumWidgetBitmapMemory() {
        WindowManager windowmanager = (WindowManager)mContext.getSystemService("window");
        mMaxWidgetBitmapMemory = windowmanager.getDefaultDisplay().getRawHeight() * (6 * windowmanager.getDefaultDisplay().getRawWidth());
    }

    public void deleteAllHosts() {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        int i = Binder.getCallingUid();
        int j = mHosts.size();
        boolean flag = false;
        int k = j - 1;
        do {
            if(k >= 0) {
                Host host = (Host)mHosts.get(k);
                if(host.uid == i) {
                    deleteHostLocked(host);
                    flag = true;
                }
            } else {
                if(flag)
                    saveStateLocked();
                return;
            }
            k--;
        } while(true);
    }

    public void deleteAppWidgetId(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        AppWidgetId appwidgetid = lookupAppWidgetIdLocked(i);
        if(appwidgetid != null) {
            deleteAppWidgetLocked(appwidgetid);
            saveStateLocked();
        }
        return;
    }

    void deleteAppWidgetLocked(AppWidgetId appwidgetid) {
        unbindAppWidgetRemoteViewsServicesLocked(appwidgetid);
        Host host = appwidgetid.host;
        host.instances.remove(appwidgetid);
        pruneHostLocked(host);
        mAppWidgetIds.remove(appwidgetid);
        Provider provider = appwidgetid.provider;
        if(provider != null) {
            provider.instances.remove(appwidgetid);
            if(!provider.zombie) {
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_DELETED");
                intent.setComponent(provider.info.provider);
                intent.putExtra("appWidgetId", appwidgetid.appWidgetId);
                mContext.sendBroadcast(intent, mUserId);
                if(provider.instances.size() == 0) {
                    cancelBroadcasts(provider);
                    Intent intent1 = new Intent("android.appwidget.action.APPWIDGET_DISABLED");
                    intent1.setComponent(provider.info.provider);
                    mContext.sendBroadcast(intent1, mUserId);
                }
            }
        }
    }

    public void deleteHost(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        Host host = lookupHostLocked(Binder.getCallingUid(), i);
        if(host != null) {
            deleteHostLocked(host);
            saveStateLocked();
        }
        return;
    }

    void deleteHostLocked(Host host) {
        for(int i = -1 + host.instances.size(); i >= 0; i--)
            deleteAppWidgetLocked((AppWidgetId)host.instances.get(i));

        host.instances.clear();
        mHosts.remove(host);
        mDeletedHosts.add(host);
        host.callbacks = null;
    }

    void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mInstalledProviders.size();
        printwriter.println("Providers:");
        for(int j = 0; j < i; j++)
            dumpProvider((Provider)mInstalledProviders.get(j), j, printwriter);

        int k = mAppWidgetIds.size();
        printwriter.println(" ");
        printwriter.println("AppWidgetIds:");
        for(int l = 0; l < k; l++)
            dumpAppWidgetId((AppWidgetId)mAppWidgetIds.get(l), l, printwriter);

        int i1 = mHosts.size();
        printwriter.println(" ");
        printwriter.println("Hosts:");
        for(int j1 = 0; j1 < i1; j1++)
            dumpHost((Host)mHosts.get(j1), j1, printwriter);

        int k1 = mDeletedProviders.size();
        printwriter.println(" ");
        printwriter.println("Deleted Providers:");
        for(int l1 = 0; l1 < k1; l1++)
            dumpProvider((Provider)mDeletedProviders.get(l1), l1, printwriter);

        int i2 = mDeletedHosts.size();
        printwriter.println(" ");
        printwriter.println("Deleted Hosts:");
        for(int j2 = 0; j2 < i2; j2++)
            dumpHost((Host)mDeletedHosts.get(j2), j2, printwriter);

        if(true) goto _L4; else goto _L3
_L3:
    }

    int enforceCallingUid(String s) throws IllegalArgumentException {
        int i = Binder.getCallingUid();
        int j;
        try {
            j = getUidForPackage(s);
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            throw new IllegalArgumentException((new StringBuilder()).append("packageName and uid don't match packageName=").append(s).toString());
        }
        if(!UserId.isSameApp(i, j))
            throw new IllegalArgumentException((new StringBuilder()).append("packageName and uid don't match packageName=").append(s).toString());
        else
            return i;
    }

    public int[] getAppWidgetIds(ComponentName componentname) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        Provider provider = lookupProviderLocked(componentname);
        int ai[];
        if(provider != null && Binder.getCallingUid() == provider.uid)
            ai = getAppWidgetIds(provider);
        else
            ai = new int[0];
        return ai;
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        AppWidgetId appwidgetid = lookupAppWidgetIdLocked(i);
        AppWidgetProviderInfo appwidgetproviderinfo;
        if(appwidgetid != null && appwidgetid.provider != null && !appwidgetid.provider.zombie)
            appwidgetproviderinfo = appwidgetid.provider.info;
        else
            appwidgetproviderinfo = null;
        return appwidgetproviderinfo;
    }

    public Bundle getAppWidgetOptions(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        AppWidgetId appwidgetid = lookupAppWidgetIdLocked(i);
        Bundle bundle;
        if(appwidgetid != null && appwidgetid.options != null)
            bundle = appwidgetid.options;
        else
            bundle = Bundle.EMPTY;
        return bundle;
    }

    public RemoteViews getAppWidgetViews(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        AppWidgetId appwidgetid = lookupAppWidgetIdLocked(i);
        RemoteViews remoteviews;
        if(appwidgetid != null)
            remoteviews = appwidgetid.views;
        else
            remoteviews = null;
        return remoteviews;
    }

    public List getInstalledProviders() {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        int i = mInstalledProviders.size();
        ArrayList arraylist1 = new ArrayList(i);
        int j = 0;
        do {
            if(j < i) {
                Provider provider = (Provider)mInstalledProviders.get(j);
                if(!provider.zombie)
                    arraylist1.add(provider.info);
            } else {
                return arraylist1;
            }
            j++;
        } while(true);
    }

    int getUidForPackage(String s) throws android.content.pm.PackageManager.NameNotFoundException {
        PackageInfo packageinfo = null;
        PackageInfo packageinfo1 = mPm.getPackageInfo(s, 0, mUserId);
        packageinfo = packageinfo1;
_L2:
        if(packageinfo == null || packageinfo.applicationInfo == null)
            throw new android.content.pm.PackageManager.NameNotFoundException();
        else
            return packageinfo.applicationInfo.uid;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean hasBindAppWidgetPermission(String s) {
        mContext.enforceCallingPermission("android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS", (new StringBuilder()).append("hasBindAppWidgetPermission packageName=").append(s).toString());
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        boolean flag = mPackagesWithBindWidgetPermission.contains(s);
        return flag;
    }

    void loadAppWidgetList() {
        Intent intent;
        int j;
        intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        break MISSING_BLOCK_LABEL_11;
_L2:
        int k;
        for(; k < j; k++)
            addProviderLocked((ResolveInfo)list.get(k));

          goto _L1
        int i;
        List list = mPm.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(mContext.getContentResolver()), 128, mUserId);
        if(list == null) {
            j = 0;
            break MISSING_BLOCK_LABEL_96;
        }
        i = list.size();
        j = i;
        break MISSING_BLOCK_LABEL_96;
        RemoteException remoteexception;
        remoteexception;
_L1:
        return;
        k = 0;
          goto _L2
    }

    void loadStateLocked() {
        AtomicFile atomicfile = savedStateFile();
        FileInputStream fileinputstream;
        fileinputstream = atomicfile.openRead();
        readStateFromFileLocked(fileinputstream);
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_27;
        fileinputstream.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
        try {
            Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("Failed to close state FileInputStream ").append(ioexception).toString());
        }
        catch(FileNotFoundException filenotfoundexception) {
            Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("Failed to read state: ").append(filenotfoundexception).toString());
        }
          goto _L1
    }

    AppWidgetId lookupAppWidgetIdLocked(int i) {
        int j;
        int k;
        int l;
        j = Binder.getCallingUid();
        k = mAppWidgetIds.size();
        l = 0;
_L3:
        AppWidgetId appwidgetid;
        if(l >= k)
            break MISSING_BLOCK_LABEL_63;
        appwidgetid = (AppWidgetId)mAppWidgetIds.get(l);
        if(appwidgetid.appWidgetId != i || !canAccessAppWidgetId(appwidgetid, j)) goto _L2; else goto _L1
_L1:
        return appwidgetid;
_L2:
        l++;
          goto _L3
        appwidgetid = null;
          goto _L1
    }

    Host lookupHostLocked(int i, int j) {
        int k;
        int l;
        k = mHosts.size();
        l = 0;
_L3:
        Host host;
        if(l >= k)
            break MISSING_BLOCK_LABEL_58;
        host = (Host)mHosts.get(l);
        if(host.uid != i || host.hostId != j) goto _L2; else goto _L1
_L1:
        return host;
_L2:
        l++;
          goto _L3
        host = null;
          goto _L1
    }

    Host lookupOrAddHostLocked(int i, String s, int j) {
        int k;
        int l;
        k = mHosts.size();
        l = 0;
_L3:
        Host host1;
        if(l >= k)
            break MISSING_BLOCK_LABEL_63;
        host1 = (Host)mHosts.get(l);
        if(host1.hostId != j || !host1.packageName.equals(s)) goto _L2; else goto _L1
_L1:
        return host1;
_L2:
        l++;
          goto _L3
        Host host = new Host();
        host.packageName = s;
        host.uid = i;
        host.hostId = j;
        mHosts.add(host);
        host1 = host;
          goto _L1
    }

    Provider lookupProviderLocked(ComponentName componentname) {
        int i;
        int j;
        i = mInstalledProviders.size();
        j = 0;
_L3:
        Provider provider;
        if(j >= i)
            break MISSING_BLOCK_LABEL_52;
        provider = (Provider)mInstalledProviders.get(j);
        if(!provider.info.provider.equals(componentname)) goto _L2; else goto _L1
_L1:
        return provider;
_L2:
        j++;
          goto _L3
        provider = null;
          goto _L1
    }

    public void notifyAppWidgetViewDataChanged(int ai[], int i) {
_L2:
        return;
        if(ai == null || ai.length == 0) goto _L2; else goto _L1
_L1:
        int j = ai.length;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        for(int k = 0; k < j; k++)
            notifyAppWidgetViewDataChangedInstanceLocked(lookupAppWidgetIdLocked(ai[k]), i);

        if(true) goto _L2; else goto _L3
_L3:
    }

    void notifyAppWidgetViewDataChangedInstanceLocked(AppWidgetId appwidgetid, int i) {
        long l;
        if(appwidgetid == null || appwidgetid.provider == null || appwidgetid.provider.zombie || appwidgetid.host.zombie)
            break MISSING_BLOCK_LABEL_206;
        Iterator iterator;
        android.content.Intent.FilterComparison filtercomparison;
        Intent intent;
        ServiceConnection serviceconnection;
        int j;
        if(appwidgetid.host.callbacks != null)
            try {
                appwidgetid.host.callbacks.viewDataChanged(appwidgetid.appWidgetId, i);
            }
            catch(RemoteException remoteexception) {
                appwidgetid.host.callbacks = null;
            }
        if(appwidgetid.host.callbacks != null)
            break MISSING_BLOCK_LABEL_206;
        iterator = mRemoteViewsServicesAppWidgets.keySet().iterator();
        do {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_206;
            filtercomparison = (android.content.Intent.FilterComparison)iterator.next();
        } while(!((HashSet)mRemoteViewsServicesAppWidgets.get(filtercomparison)).contains(Integer.valueOf(appwidgetid.appWidgetId)));
        intent = filtercomparison.getIntent();
        serviceconnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
                IRemoteViewsFactory iremoteviewsfactory = com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(ibinder);
                try {
                    iremoteviewsfactory.onDataSetChangedAsync();
                }
                catch(RemoteException remoteexception1) {
                    remoteexception1.printStackTrace();
                }
                catch(RuntimeException runtimeexception) {
                    runtimeexception.printStackTrace();
                }
                mContext.unbindService(this);
            }

            public void onServiceDisconnected(ComponentName componentname) {
            }

            final AppWidgetServiceImpl this$0;

             {
                this$0 = AppWidgetServiceImpl.this;
                super();
            }
        };
        j = UserId.getUserId(appwidgetid.provider.uid);
        l = Binder.clearCallingIdentity();
        mContext.bindService(intent, serviceconnection, 1, j);
        Binder.restoreCallingIdentity(l);
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_81;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    void onBroadcastReceived(Intent intent) {
        String s;
        boolean flag;
        String as[];
        boolean flag1;
        s = intent.getAction();
        flag = false;
        if("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(s)) {
            as = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
            flag1 = true;
        } else {
label0:
            {
                if(!"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(s))
                    break label0;
                as = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                flag1 = false;
            }
        }
_L5:
        if(as != null && as.length != 0) goto _L2; else goto _L1
_L1:
        return;
        Uri uri = intent.getData();
        if(uri == null) goto _L1; else goto _L3
_L3:
        String s1 = uri.getSchemeSpecificPart();
        if(s1 == null) goto _L1; else goto _L4
_L4:
        as = new String[1];
        as[0] = s1;
        flag1 = "android.intent.action.PACKAGE_ADDED".equals(s);
        flag = "android.intent.action.PACKAGE_CHANGED".equals(s);
          goto _L5
_L2:
        if(!flag1 && !flag)
            break MISSING_BLOCK_LABEL_256;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        Bundle bundle = intent.getExtras();
        if(flag || bundle != null && bundle.getBoolean("android.intent.extra.REPLACING", false)) {
            String as1[] = as;
            int i = as1.length;
            for(int j = 0; j < i; j++)
                updateProvidersForPackageLocked(as1[j]);

        } else {
            String as2[] = as;
            int k = as2.length;
            for(int l = 0; l < k; l++)
                addProvidersForPackageLocked(as2[l]);

        }
        saveStateLocked();
          goto _L1
        Bundle bundle1 = intent.getExtras();
        if(bundle1 != null && bundle1.getBoolean("android.intent.extra.REPLACING", false)) goto _L1; else goto _L6
_L6:
        ArrayList arraylist1 = mAppWidgetIds;
        arraylist1;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        String as3[] = as;
        int i1 = as3.length;
        for(int j1 = 0; j1 < i1; j1++) {
            removeProvidersForPackageLocked(as3[j1]);
            saveStateLocked();
        }

          goto _L1
    }

    void onConfigurationChanged() {
        Locale locale = Locale.getDefault();
        if(locale != null && mLocale != null && locale.equals(mLocale))
            break MISSING_BLOCK_LABEL_104;
        mLocale = locale;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        for(int i = -1 + mInstalledProviders.size(); i >= 0; i--)
            updateProvidersForPackageLocked(((Provider)mInstalledProviders.get(i)).info.provider.getPackageName());

        saveStateLocked();
    }

    void onUserRemoved() {
        for(int i = -1 + mInstalledProviders.size(); i >= 0; i--)
            cancelBroadcasts((Provider)mInstalledProviders.get(i));

        getSettingsFile(mUserId).delete();
    }

    public void partiallyUpdateAppWidgetIds(int ai[], RemoteViews remoteviews) {
_L2:
        return;
        if(ai == null || ai.length == 0) goto _L2; else goto _L1
_L1:
        int i = ai.length;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        for(int j = 0; j < i; j++)
            updateAppWidgetInstanceLocked(lookupAppWidgetIdLocked(ai[j]), remoteviews, true);

        if(true) goto _L2; else goto _L3
_L3:
    }

    void pruneHostLocked(Host host) {
        if(host.instances.size() == 0 && host.callbacks == null)
            mHosts.remove(host);
    }

    void readStateFromFileLocked(FileInputStream fileinputstream) {
        boolean flag = false;
        XmlPullParser xmlpullparser;
        int l;
        HashMap hashmap;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
        l = 0;
        hashmap = new HashMap();
_L6:
        int i1 = xmlpullparser.next();
        if(i1 != 2) goto _L2; else goto _L1
_L1:
        String s = xmlpullparser.getName();
        if(!"p".equals(s)) goto _L4; else goto _L3
_L3:
        String s3;
        String s4;
        PackageManager packagemanager;
        s3 = xmlpullparser.getAttributeValue(null, "pkg");
        s4 = xmlpullparser.getAttributeValue(null, "cl");
        packagemanager = mContext.getPackageManager();
        ComponentName componentname = new ComponentName(s3, s4);
        packagemanager.getReceiverInfo(componentname, 0);
_L7:
        ComponentName componentname1 = new ComponentName(s3, s4);
        Provider provider = lookupProviderLocked(componentname1);
        if(provider == null && mSafeMode) {
            provider = new Provider();
            provider.info = new AppWidgetProviderInfo();
            AppWidgetProviderInfo appwidgetproviderinfo = provider.info;
            ComponentName componentname2 = new ComponentName(s3, s4);
            appwidgetproviderinfo.provider = componentname2;
            provider.zombie = true;
            mInstalledProviders.add(provider);
        }
        if(provider != null)
            hashmap.put(Integer.valueOf(l), provider);
        l++;
_L2:
        if(i1 != 1) goto _L6; else goto _L5
_L5:
        flag = true;
_L10:
        IndexOutOfBoundsException indexoutofboundsexception;
        IOException ioexception;
        XmlPullParserException xmlpullparserexception;
        NumberFormatException numberformatexception;
        NullPointerException nullpointerexception;
        Host host;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        AppWidgetId appwidgetid;
        String s1;
        int j1;
        String s2;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception1;
        String as[];
        if(flag) {
            for(int k = -1 + mHosts.size(); k >= 0; k--)
                pruneHostLocked((Host)mHosts.get(k));

        } else {
            Slog.w("AppWidgetServiceImpl", "Failed to read state, clearing widgets and hosts.");
            mAppWidgetIds.clear();
            mHosts.clear();
            int i = mInstalledProviders.size();
            for(int j = 0; j < i; j++)
                ((Provider)mInstalledProviders.get(j)).instances.clear();

        }
        break MISSING_BLOCK_LABEL_898;
        namenotfoundexception1;
        as = new String[1];
        as[0] = s3;
        s3 = packagemanager.currentToCanonicalPackageNames(as)[0];
          goto _L7
_L4:
        if(!"h".equals(s)) goto _L9; else goto _L8
_L8:
        host = new Host();
        host.packageName = xmlpullparser.getAttributeValue(null, "pkg");
        host.uid = getUidForPackage(host.packageName);
_L11:
        if(!host.zombie || mSafeMode) {
            host.hostId = Integer.parseInt(xmlpullparser.getAttributeValue(null, "id"), 16);
            mHosts.add(host);
        }
          goto _L2
        nullpointerexception;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("failed parsing ").append(nullpointerexception).toString());
          goto _L10
        namenotfoundexception;
        host.zombie = true;
          goto _L11
        numberformatexception;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("failed parsing ").append(numberformatexception).toString());
          goto _L10
_L9:
        if(!"b".equals(s)) goto _L13; else goto _L12
_L12:
        s2 = xmlpullparser.getAttributeValue(null, "packageName");
        if(s2 != null)
            mPackagesWithBindWidgetPermission.add(s2);
          goto _L2
        xmlpullparserexception;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("failed parsing ").append(xmlpullparserexception).toString());
          goto _L10
_L13:
        if(!"g".equals(s)) goto _L2; else goto _L14
_L14:
        appwidgetid = new AppWidgetId();
        appwidgetid.appWidgetId = Integer.parseInt(xmlpullparser.getAttributeValue(null, "id"), 16);
        if(appwidgetid.appWidgetId >= mNextAppWidgetId)
            mNextAppWidgetId = 1 + appwidgetid.appWidgetId;
        s1 = xmlpullparser.getAttributeValue(null, "p");
        if(s1 == null) goto _L16; else goto _L15
_L15:
        appwidgetid.provider = (Provider)hashmap.get(Integer.valueOf(Integer.parseInt(s1, 16)));
        if(appwidgetid.provider == null) goto _L2; else goto _L16
_L16:
        j1 = Integer.parseInt(xmlpullparser.getAttributeValue(null, "h"), 16);
        appwidgetid.host = (Host)mHosts.get(j1);
        if(appwidgetid.host != null) {
            if(appwidgetid.provider != null)
                appwidgetid.provider.instances.add(appwidgetid);
            appwidgetid.host.instances.add(appwidgetid);
            mAppWidgetIds.add(appwidgetid);
        }
          goto _L2
        ioexception;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("failed parsing ").append(ioexception).toString());
          goto _L10
        indexoutofboundsexception;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("failed parsing ").append(indexoutofboundsexception).toString());
          goto _L10
          goto _L7
    }

    void registerForBroadcastsLocked(Provider provider, int ai[]) {
        long l;
        boolean flag = true;
        if(provider.info.updatePeriodMillis <= 0)
            break MISSING_BLOCK_LABEL_129;
        Intent intent;
        long l1;
        if(provider.broadcast == null)
            flag = false;
        intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", ai);
        intent.setComponent(provider.info.provider);
        l = Binder.clearCallingIdentity();
        provider.broadcast = PendingIntent.getBroadcast(mContext, 1, intent, 0x8000000);
        Binder.restoreCallingIdentity(l);
        if(!flag) {
            l1 = provider.info.updatePeriodMillis;
            if(l1 < 0x1b7740L)
                l1 = 0x1b7740L;
            mAlarmManager.setInexactRepeating(2, l1 + SystemClock.elapsedRealtime(), l1, provider.broadcast);
        }
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    void removeProviderLocked(int i, Provider provider) {
        int j = provider.instances.size();
        for(int k = 0; k < j; k++) {
            AppWidgetId appwidgetid = (AppWidgetId)provider.instances.get(k);
            updateAppWidgetInstanceLocked(appwidgetid, null);
            cancelBroadcasts(provider);
            appwidgetid.host.instances.remove(appwidgetid);
            mAppWidgetIds.remove(appwidgetid);
            appwidgetid.provider = null;
            pruneHostLocked(appwidgetid.host);
            appwidgetid.host = null;
        }

        provider.instances.clear();
        mInstalledProviders.remove(i);
        mDeletedProviders.add(provider);
        cancelBroadcasts(provider);
    }

    void removeProvidersForPackageLocked(String s) {
        for(int i = -1 + mInstalledProviders.size(); i >= 0; i--) {
            Provider provider = (Provider)mInstalledProviders.get(i);
            if(s.equals(provider.info.provider.getPackageName()))
                removeProviderLocked(i, provider);
        }

        for(int j = -1 + mHosts.size(); j >= 0; j--) {
            Host host = (Host)mHosts.get(j);
            if(s.equals(host.packageName))
                deleteHostLocked(host);
        }

    }

    void saveStateLocked() {
        AtomicFile atomicfile = savedStateFile();
        try {
            FileOutputStream fileoutputstream = atomicfile.startWrite();
            if(writeStateToFileLocked(fileoutputstream)) {
                atomicfile.finishWrite(fileoutputstream);
            } else {
                atomicfile.failWrite(fileoutputstream);
                Slog.w("AppWidgetServiceImpl", "Failed to save state, restoring backup.");
            }
        }
        catch(IOException ioexception) {
            Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("Failed open state file for write: ").append(ioexception).toString());
        }
    }

    AtomicFile savedStateFile() {
        File file = new File((new StringBuilder()).append("/data/system/users/").append(mUserId).toString());
        File file1 = getSettingsFile(mUserId);
        if(!file1.exists() && mUserId == 0) {
            if(!file.exists())
                file.mkdirs();
            (new File("/data/system/appwidgets.xml")).renameTo(file1);
        }
        return new AtomicFile(file1);
    }

    void sendEnableIntentLocked(Provider provider) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_ENABLED");
        intent.setComponent(provider.info.provider);
        mContext.sendBroadcast(intent, mUserId);
    }

    void sendInitialBroadcasts() {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        int i = mInstalledProviders.size();
        int j = 0;
        do {
            if(j < i) {
                Provider provider = (Provider)mInstalledProviders.get(j);
                if(provider.instances.size() > 0) {
                    sendEnableIntentLocked(provider);
                    int ai[] = getAppWidgetIds(provider);
                    sendUpdateIntentLocked(provider, ai);
                    registerForBroadcastsLocked(provider, ai);
                }
            } else {
                return;
            }
            j++;
        } while(true);
    }

    void sendUpdateIntentLocked(Provider provider, int ai[]) {
        if(ai != null && ai.length > 0) {
            Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
            intent.putExtra("appWidgetIds", ai);
            intent.setComponent(provider.info.provider);
            mContext.sendBroadcast(intent, mUserId);
        }
    }

    public void setBindAppWidgetPermission(String s, boolean flag) {
        mContext.enforceCallingPermission("android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS", (new StringBuilder()).append("setBindAppWidgetPermission packageName=").append(s).toString());
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        if(!flag)
            break MISSING_BLOCK_LABEL_61;
        mPackagesWithBindWidgetPermission.add(s);
_L2:
        saveStateLocked();
        return;
        mPackagesWithBindWidgetPermission.remove(s);
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public int[] startListening(IAppWidgetHost iappwidgethost, String s, int i, List list) {
        int j = enforceCallingUid(s);
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        Host host = lookupOrAddHostLocked(j, s, i);
        host.callbacks = iappwidgethost;
        list.clear();
        ArrayList arraylist1 = host.instances;
        int k = arraylist1.size();
        int ai[] = new int[k];
        for(int l = 0; l < k; l++) {
            AppWidgetId appwidgetid = (AppWidgetId)arraylist1.get(l);
            ai[l] = appwidgetid.appWidgetId;
            list.add(appwidgetid.views);
        }

        return ai;
    }

    public void stopListening(int i) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        Host host = lookupHostLocked(Binder.getCallingUid(), i);
        if(host != null) {
            host.callbacks = null;
            pruneHostLocked(host);
        }
        return;
    }

    public void systemReady(boolean flag) {
        mSafeMode = flag;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        return;
    }

    public void unbindRemoteViewsService(int i, Intent intent) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        Pair pair;
        ensureStateLoadedLocked();
        pair = Pair.create(Integer.valueOf(i), new android.content.Intent.FilterComparison(intent));
        if(mBoundRemoteViewsServices.containsKey(pair)) {
            if(lookupAppWidgetIdLocked(i) == null)
                throw new IllegalArgumentException("bad appWidgetId");
            break MISSING_BLOCK_LABEL_65;
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
        ServiceConnectionProxy serviceconnectionproxy = (ServiceConnectionProxy)mBoundRemoteViewsServices.get(pair);
        serviceconnectionproxy.disconnect();
        mContext.unbindService(serviceconnectionproxy);
        mBoundRemoteViewsServices.remove(pair);
_L3:
        arraylist;
        JVM INSTR monitorexit ;
        return;
_L1:
        Log.e("AppWidgetService", "Error (unbindRemoteViewsService): Connection not bound");
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void updateAppWidgetIds(int ai[], RemoteViews remoteviews) {
        if(ai != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j;
        int i = 0;
        if(remoteviews != null)
            i = remoteviews.estimateMemoryUsage();
        if(i > mMaxWidgetBitmapMemory)
            throw new IllegalArgumentException((new StringBuilder()).append("RemoteViews for widget update exceeds maximum bitmap memory usage (used: ").append(i).append(", max: ").append(mMaxWidgetBitmapMemory).append(") The total memory cannot exceed that required to").append(" fill the device's screen once.").toString());
        if(ai.length == 0)
            continue; /* Loop/switch isn't completed */
        j = ai.length;
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        for(int k = 0; k < j; k++)
            updateAppWidgetInstanceLocked(lookupAppWidgetIdLocked(ai[k]), remoteviews);

        if(true) goto _L1; else goto _L3
_L3:
    }

    void updateAppWidgetInstanceLocked(AppWidgetId appwidgetid, RemoteViews remoteviews) {
        updateAppWidgetInstanceLocked(appwidgetid, remoteviews, false);
    }

    void updateAppWidgetInstanceLocked(AppWidgetId appwidgetid, RemoteViews remoteviews, boolean flag) {
        if(appwidgetid == null || appwidgetid.provider == null || appwidgetid.provider.zombie || appwidgetid.host.zombie)
            break MISSING_BLOCK_LABEL_67;
        if(!flag)
            appwidgetid.views = remoteviews;
        if(appwidgetid.host.callbacks == null)
            break MISSING_BLOCK_LABEL_67;
        appwidgetid.host.callbacks.updateAppWidget(appwidgetid.appWidgetId, remoteviews);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        appwidgetid.host.callbacks = null;
          goto _L1
    }

    public void updateAppWidgetOptions(int i, Bundle bundle) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        ensureStateLoadedLocked();
        AppWidgetId appwidgetid = lookupAppWidgetIdLocked(i);
        if(appwidgetid != null) {
            Provider provider = appwidgetid.provider;
            appwidgetid.options = bundle;
            Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS");
            intent.setComponent(provider.info.provider);
            intent.putExtra("appWidgetId", appwidgetid.appWidgetId);
            intent.putExtra("appWidgetOptions", bundle);
            mContext.sendBroadcast(intent, mUserId);
        }
        return;
    }

    public void updateAppWidgetProvider(ComponentName componentname, RemoteViews remoteviews) {
        ArrayList arraylist = mAppWidgetIds;
        arraylist;
        JVM INSTR monitorenter ;
        Provider provider;
        ensureStateLoadedLocked();
        provider = lookupProviderLocked(componentname);
        if(provider != null) goto _L2; else goto _L1
_L1:
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("updateAppWidgetProvider: provider doesn't exist: ").append(componentname).toString());
          goto _L3
_L2:
        ArrayList arraylist1;
        int i;
        int j;
        int k;
        arraylist1 = provider.instances;
        i = Binder.getCallingUid();
        j = arraylist1.size();
        k = 0;
_L5:
        if(k < j) {
            AppWidgetId appwidgetid = (AppWidgetId)arraylist1.get(k);
            if(canAccessAppWidgetId(appwidgetid, i))
                updateAppWidgetInstanceLocked(appwidgetid, remoteviews);
            k++;
            continue; /* Loop/switch isn't completed */
        }
_L3:
        return;
        if(true) goto _L5; else goto _L4
_L4:
    }

    void updateProvidersForPackageLocked(String s) {
        HashSet hashset;
        Intent intent;
        hashset = new HashSet();
        intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setPackage(s);
        List list = mPm.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(mContext.getContentResolver()), 128, mUserId);
        ResolveInfo resolveinfo;
        ActivityInfo activityinfo;
        RemoteException remoteexception;
        int i;
        int j;
        if(list == null)
            i = 0;
        else
            i = list.size();
        j = 0;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        resolveinfo = (ResolveInfo)list.get(j);
        activityinfo = resolveinfo.activityInfo;
        if((0x40000 & activityinfo.applicationInfo.flags) == 0 && s.equals(((ComponentInfo) (activityinfo)).packageName)) {
            ComponentName componentname = new ComponentName(((ComponentInfo) (activityinfo)).packageName, ((ComponentInfo) (activityinfo)).name);
            Provider provider1 = lookupProviderLocked(componentname);
            if(provider1 == null) {
                if(addProviderLocked(resolveinfo))
                    hashset.add(((ComponentInfo) (activityinfo)).name);
            } else {
                Provider provider2 = parseProviderInfoXml(componentname, resolveinfo);
                if(provider2 != null) {
                    hashset.add(((ComponentInfo) (activityinfo)).name);
                    provider1.info = provider2.info;
                    int l = provider1.instances.size();
                    if(l > 0) {
                        int ai[] = getAppWidgetIds(provider1);
                        cancelBroadcasts(provider1);
                        registerForBroadcastsLocked(provider1, ai);
                        int i1 = 0;
                        while(i1 < l)  {
                            AppWidgetId appwidgetid = (AppWidgetId)provider1.instances.get(i1);
                            appwidgetid.views = null;
                            if(appwidgetid.host != null && appwidgetid.host.callbacks != null)
                                try {
                                    appwidgetid.host.callbacks.providerChanged(appwidgetid.appWidgetId, provider1.info);
                                }
                                catch(RemoteException remoteexception1) {
                                    appwidgetid.host.callbacks = null;
                                }
                            i1++;
                        }
                        sendUpdateIntentLocked(provider1, ai);
                    }
                }
            }
        }
        j++;
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_66;
        remoteexception;
_L4:
        return;
_L1:
        int k = -1 + mInstalledProviders.size();
        while(k >= 0)  {
            Provider provider = (Provider)mInstalledProviders.get(k);
            if(s.equals(provider.info.provider.getPackageName()) && !hashset.contains(provider.info.provider.getClassName()))
                removeProviderLocked(k, provider);
            k--;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    boolean writeStateToFileLocked(FileOutputStream fileoutputstream) {
        FastXmlSerializer fastxmlserializer;
        int i;
        int j;
        int k;
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.startTag(null, "gs");
        i = 0;
        j = mInstalledProviders.size();
        k = 0;
_L6:
        if(k >= j) goto _L2; else goto _L1
_L1:
        Provider provider = (Provider)mInstalledProviders.get(k);
        if(provider.instances.size() > 0) {
            fastxmlserializer.startTag(null, "p");
            fastxmlserializer.attribute(null, "pkg", provider.info.provider.getPackageName());
            fastxmlserializer.attribute(null, "cl", provider.info.provider.getClassName());
            fastxmlserializer.endTag(null, "p");
            provider.tag = i;
            i++;
        }
          goto _L3
_L2:
        int l = mHosts.size();
        for(int i1 = 0; i1 < l; i1++) {
            Host host = (Host)mHosts.get(i1);
            fastxmlserializer.startTag(null, "h");
            fastxmlserializer.attribute(null, "pkg", host.packageName);
            fastxmlserializer.attribute(null, "id", Integer.toHexString(host.hostId));
            fastxmlserializer.endTag(null, "h");
            host.tag = i1;
        }

        int j1 = mAppWidgetIds.size();
        for(int k1 = 0; k1 < j1; k1++) {
            AppWidgetId appwidgetid = (AppWidgetId)mAppWidgetIds.get(k1);
            fastxmlserializer.startTag(null, "g");
            fastxmlserializer.attribute(null, "id", Integer.toHexString(appwidgetid.appWidgetId));
            fastxmlserializer.attribute(null, "h", Integer.toHexString(appwidgetid.host.tag));
            if(appwidgetid.provider != null)
                fastxmlserializer.attribute(null, "p", Integer.toHexString(appwidgetid.provider.tag));
            fastxmlserializer.endTag(null, "g");
        }

        for(Iterator iterator = mPackagesWithBindWidgetPermission.iterator(); iterator.hasNext(); fastxmlserializer.endTag(null, "b")) {
            fastxmlserializer.startTag(null, "b");
            fastxmlserializer.attribute(null, "packageName", (String)iterator.next());
        }

          goto _L4
        IOException ioexception;
        ioexception;
        boolean flag;
        Slog.w("AppWidgetServiceImpl", (new StringBuilder()).append("Failed to write state: ").append(ioexception).toString());
        flag = false;
_L5:
        return flag;
_L4:
        fastxmlserializer.endTag(null, "gs");
        fastxmlserializer.endDocument();
        flag = true;
        if(true) goto _L5; else goto _L3
_L3:
        k++;
          goto _L6
    }

    private static final int MIN_UPDATE_PERIOD = 0x1b7740;
    private static final String SETTINGS_FILENAME = "appwidgets.xml";
    private static final String TAG = "AppWidgetServiceImpl";
    AlarmManager mAlarmManager;
    final ArrayList mAppWidgetIds = new ArrayList();
    private final HashMap mBoundRemoteViewsServices = new HashMap();
    Context mContext;
    ArrayList mDeletedHosts;
    ArrayList mDeletedProviders;
    ArrayList mHosts;
    ArrayList mInstalledProviders;
    Locale mLocale;
    int mMaxWidgetBitmapMemory;
    int mNextAppWidgetId;
    HashSet mPackagesWithBindWidgetPermission;
    IPackageManager mPm;
    private final HashMap mRemoteViewsServicesAppWidgets = new HashMap();
    boolean mSafeMode;
    boolean mStateLoaded;
    int mUserId;
}
