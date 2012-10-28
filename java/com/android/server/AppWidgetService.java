// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProviderInfo;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.util.*;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.widget.IRemoteViewsAdapterConnection;
import java.io.*;
import java.util.*;

// Referenced classes of package com.android.server:
//            AppWidgetServiceImpl

class AppWidgetService extends com.android.internal.appwidget.IAppWidgetService.Stub {
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

    static class Injector {

        static boolean handleAction(AppWidgetService appwidgetservice, String s) {
            boolean flag;
            if("android.intent.action.RESTORE_FINISH".equals(s)) {
                appwidgetservice.getImplForUser().reload();
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        }

        static void receiveRestoreFinish(AppWidgetService appwidgetservice) {
            appwidgetservice.mContext.registerReceiver(appwidgetservice.mBroadcastReceiver, new IntentFilter("android.intent.action.RESTORE_FINISH"), null, null);
        }

        Injector() {
        }
    }


    AppWidgetService(Context context) {
        mInstalledProviders = new ArrayList();
        mNextAppWidgetId = 1;
        mHosts = new ArrayList();
        mBroadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                String s = intent.getAction();
                if(!Injector.handleAction(AppWidgetService.this, s)) goto _L2; else goto _L1
_L1:
                return;
_L2:
                if(!"android.intent.action.BOOT_COMPLETED".equals(s))
                    break; /* Loop/switch isn't completed */
                getImplForUser().sendInitialBroadcasts();
                if(true) goto _L1; else goto _L3
_L3:
                if(!"android.intent.action.CONFIGURATION_CHANGED".equals(s))
                    break; /* Loop/switch isn't completed */
                int j = 0;
                while(j < mAppWidgetServices.size())  {
                    ((AppWidgetServiceImpl)mAppWidgetServices.valueAt(j)).onConfigurationChanged();
                    j++;
                }
                if(true) goto _L1; else goto _L4
_L4:
                int i = 0;
                while(i < mAppWidgetServices.size())  {
                    ((AppWidgetServiceImpl)mAppWidgetServices.valueAt(i)).onBroadcastReceived(intent);
                    i++;
                }
                if(true) goto _L1; else goto _L5
_L5:
            }

            final AppWidgetService this$0;

             {
                this$0 = AppWidgetService.this;
                super();
            }
        };
        mContext = context;
        AppWidgetServiceImpl appwidgetserviceimpl = new AppWidgetServiceImpl(context, 0);
        mAppWidgetServices.append(0, appwidgetserviceimpl);
    }

    static int[] getAppWidgetIds(Provider provider) {
        int i = provider.instances.size();
        int ai[] = new int[i];
        for(int j = 0; j < i; j++)
            ai[j] = ((AppWidgetId)provider.instances.get(j)).appWidgetId;

        return ai;
    }

    private AppWidgetServiceImpl getImplForUser() {
        int i = Binder.getOrigCallingUser();
        AppWidgetServiceImpl appwidgetserviceimpl = (AppWidgetServiceImpl)mAppWidgetServices.get(i);
        if(appwidgetserviceimpl == null) {
            Slog.e("AppWidgetService", "Unable to find AppWidgetServiceImpl for the current user");
            appwidgetserviceimpl = new AppWidgetServiceImpl(mContext, i);
            appwidgetserviceimpl.systemReady(mSafeMode);
            appwidgetserviceimpl.sendInitialBroadcasts();
            mAppWidgetServices.append(i, appwidgetserviceimpl);
        }
        return appwidgetserviceimpl;
    }

    public int allocateAppWidgetId(String s, int i) throws RemoteException {
        return getImplForUser().allocateAppWidgetId(s, i);
    }

    public void bindAppWidgetId(int i, ComponentName componentname) throws RemoteException {
        getImplForUser().bindAppWidgetId(i, componentname);
    }

    public boolean bindAppWidgetIdIfAllowed(String s, int i, ComponentName componentname) throws RemoteException {
        return getImplForUser().bindAppWidgetIdIfAllowed(s, i, componentname);
    }

    public void bindRemoteViewsService(int i, Intent intent, IBinder ibinder) throws RemoteException {
        getImplForUser().bindRemoteViewsService(i, intent, ibinder);
    }

    public void deleteAllHosts() throws RemoteException {
        getImplForUser().deleteAllHosts();
    }

    public void deleteAppWidgetId(int i) throws RemoteException {
        getImplForUser().deleteAppWidgetId(i);
    }

    public void deleteHost(int i) throws RemoteException {
        getImplForUser().deleteHost(i);
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        for(int i = 0; i < mAppWidgetServices.size(); i++)
            ((AppWidgetServiceImpl)mAppWidgetServices.valueAt(i)).dump(filedescriptor, printwriter, as);

    }

    public int[] getAppWidgetIds(ComponentName componentname) throws RemoteException {
        return getImplForUser().getAppWidgetIds(componentname);
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int i) throws RemoteException {
        return getImplForUser().getAppWidgetInfo(i);
    }

    public Bundle getAppWidgetOptions(int i) {
        return getImplForUser().getAppWidgetOptions(i);
    }

    public RemoteViews getAppWidgetViews(int i) throws RemoteException {
        return getImplForUser().getAppWidgetViews(i);
    }

    public List getInstalledProviders() throws RemoteException {
        return getImplForUser().getInstalledProviders();
    }

    public boolean hasBindAppWidgetPermission(String s) throws RemoteException {
        return getImplForUser().hasBindAppWidgetPermission(s);
    }

    public void notifyAppWidgetViewDataChanged(int ai[], int i) throws RemoteException {
        getImplForUser().notifyAppWidgetViewDataChanged(ai, i);
    }

    public void onUserRemoved(int i) {
        AppWidgetServiceImpl appwidgetserviceimpl = (AppWidgetServiceImpl)mAppWidgetServices.get(i);
        if(i >= 1)
            if(appwidgetserviceimpl == null)
                AppWidgetServiceImpl.getSettingsFile(i).delete();
            else
                appwidgetserviceimpl.onUserRemoved();
    }

    public void partiallyUpdateAppWidgetIds(int ai[], RemoteViews remoteviews) throws RemoteException {
        getImplForUser().partiallyUpdateAppWidgetIds(ai, remoteviews);
    }

    public void setBindAppWidgetPermission(String s, boolean flag) throws RemoteException {
        getImplForUser().setBindAppWidgetPermission(s, flag);
    }

    public int[] startListening(IAppWidgetHost iappwidgethost, String s, int i, List list) throws RemoteException {
        return getImplForUser().startListening(iappwidgethost, s, i, list);
    }

    public void stopListening(int i) throws RemoteException {
        getImplForUser().stopListening(i);
    }

    public void systemReady(boolean flag) {
        mSafeMode = flag;
        ((AppWidgetServiceImpl)mAppWidgetServices.get(0)).systemReady(flag);
        Injector.receiveRestoreFinish(this);
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"), null, null);
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"), null, null);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentfilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter.addDataScheme("package");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        intentfilter1.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter1);
        IntentFilter intentfilter2 = new IntentFilter();
        intentfilter2.addAction("android.intent.action.USER_REMOVED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                onUserRemoved(intent.getIntExtra("android.intent.extra.user_id", -1));
            }

            final AppWidgetService this$0;

             {
                this$0 = AppWidgetService.this;
                super();
            }
        }, intentfilter2);
    }

    public void unbindRemoteViewsService(int i, Intent intent) throws RemoteException {
        getImplForUser().unbindRemoteViewsService(i, intent);
    }

    public void updateAppWidgetIds(int ai[], RemoteViews remoteviews) throws RemoteException {
        getImplForUser().updateAppWidgetIds(ai, remoteviews);
    }

    public void updateAppWidgetOptions(int i, Bundle bundle) {
        getImplForUser().updateAppWidgetOptions(i, bundle);
    }

    public void updateAppWidgetProvider(ComponentName componentname, RemoteViews remoteviews) throws RemoteException {
        getImplForUser().updateAppWidgetProvider(componentname, remoteviews);
    }

    private static final String TAG = "AppWidgetService";
    AlarmManager mAlarmManager;
    final ArrayList mAppWidgetIds = new ArrayList();
    private final SparseArray mAppWidgetServices = new SparseArray(5);
    BroadcastReceiver mBroadcastReceiver;
    Context mContext;
    ArrayList mHosts;
    ArrayList mInstalledProviders;
    Locale mLocale;
    int mNextAppWidgetId;
    PackageManager mPackageManager;
    boolean mSafeMode;


}
