// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.*;
import android.location.*;
import android.net.NetworkInfo;
import android.os.*;
import android.util.Log;
import com.android.internal.location.DummyLocationProvider;

// Referenced classes of package com.android.server.location:
//            LocationProviderInterface

public class LocationProviderProxy
    implements LocationProviderInterface {
    private class Connection
        implements ServiceConnection, Runnable {

        /**
         * @deprecated Method getCachedAttributes is deprecated
         */

        public DummyLocationProvider getCachedAttributes() {
            this;
            JVM INSTR monitorenter ;
            DummyLocationProvider dummylocationprovider = mCachedAttributes;
            this;
            JVM INSTR monitorexit ;
            return dummylocationprovider;
            Exception exception;
            exception;
            throw exception;
        }

        /**
         * @deprecated Method getProvider is deprecated
         */

        public ILocationProvider getProvider() {
            this;
            JVM INSTR monitorenter ;
            ILocationProvider ilocationprovider = mProvider;
            this;
            JVM INSTR monitorexit ;
            return ilocationprovider;
            Exception exception;
            exception;
            throw exception;
        }

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            this;
            JVM INSTR monitorenter ;
            mProvider = android.location.ILocationProvider.Stub.asInterface(ibinder);
            if(mProvider != null)
                mHandler.post(this);
            return;
        }

        public void onServiceDisconnected(ComponentName componentname) {
            this;
            JVM INSTR monitorenter ;
            mProvider = null;
            return;
        }

        public void run() {
            Object obj = mMutex;
            obj;
            JVM INSTR monitorenter ;
            if(mServiceConnection == this) goto _L2; else goto _L1
_L2:
            ILocationProvider ilocationprovider = getProvider();
            if(ilocationprovider != null) goto _L3; else goto _L1
            Exception exception;
            exception;
            throw exception;
_L3:
            DummyLocationProvider dummylocationprovider;
            try {
                if(mEnabled)
                    ilocationprovider.enable();
                if(mLocationTracking)
                    ilocationprovider.enableLocationTracking(true);
                if(mMinTime >= 0L)
                    ilocationprovider.setMinTime(mMinTime, mMinTimeSource);
                if(mNetworkInfo != null)
                    ilocationprovider.updateNetworkState(mNetworkState, mNetworkInfo);
            }
            catch(RemoteException remoteexception) { }
            dummylocationprovider = mCachedAttributes;
            if(dummylocationprovider != null)
                break MISSING_BLOCK_LABEL_287;
            try {
                mCachedAttributes = new DummyLocationProvider(mName, null);
                mCachedAttributes.setRequiresNetwork(ilocationprovider.requiresNetwork());
                mCachedAttributes.setRequiresSatellite(ilocationprovider.requiresSatellite());
                mCachedAttributes.setRequiresCell(ilocationprovider.requiresCell());
                mCachedAttributes.setHasMonetaryCost(ilocationprovider.hasMonetaryCost());
                mCachedAttributes.setSupportsAltitude(ilocationprovider.supportsAltitude());
                mCachedAttributes.setSupportsSpeed(ilocationprovider.supportsSpeed());
                mCachedAttributes.setSupportsBearing(ilocationprovider.supportsBearing());
                mCachedAttributes.setPowerRequirement(ilocationprovider.getPowerRequirement());
                mCachedAttributes.setAccuracy(ilocationprovider.getAccuracy());
            }
            catch(RemoteException remoteexception1) {
                mCachedAttributes = null;
            }
            obj;
            JVM INSTR monitorexit ;
_L1:
        }

        private DummyLocationProvider mCachedAttributes;
        private ILocationProvider mProvider;
        final LocationProviderProxy this$0;

        private Connection() {
            this$0 = LocationProviderProxy.this;
            super();
        }

    }


    public LocationProviderProxy(Context context, String s, String s1, Handler handler) {
        mLocationTracking = false;
        mEnabled = false;
        mMinTime = -1L;
        mMinTimeSource = new WorkSource();
        mContext = context;
        mName = s;
        mHandler = handler;
        reconnect(s1);
    }

    private DummyLocationProvider getCachedAttributes() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        DummyLocationProvider dummylocationprovider = mServiceConnection.getCachedAttributes();
        return dummylocationprovider;
    }

    public void addListener(int i) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.addListener(i);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public void disable() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider;
        mEnabled = false;
        ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.disable();
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public void enable() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider;
        mEnabled = true;
        ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.enable();
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public void enableLocationTracking(boolean flag) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider;
        mLocationTracking = flag;
        if(!flag) {
            mMinTime = -1L;
            mMinTimeSource.clear();
        }
        ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.enableLocationTracking(flag);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public int getAccuracy() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        int i;
        if(dummylocationprovider != null)
            i = dummylocationprovider.getAccuracy();
        else
            i = -1;
        return i;
    }

    public String getInternalState() {
        ILocationProvider ilocationprovider;
        synchronized(mMutex) {
            ilocationprovider = mServiceConnection.getProvider();
        }
        if(ilocationprovider == null) goto _L2; else goto _L1
_L1:
        String s1 = ilocationprovider.getInternalState();
        String s = s1;
_L4:
        return s;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Log.e("LocationProviderProxy", "getInternalState failed", remoteexception);
_L2:
        s = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public long getMinTime() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        long l = mMinTime;
        return l;
    }

    public String getName() {
        return mName;
    }

    public int getPowerRequirement() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        int i;
        if(dummylocationprovider != null)
            i = dummylocationprovider.getPowerRequirement();
        else
            i = -1;
        return i;
    }

    public int getStatus(Bundle bundle) {
        ILocationProvider ilocationprovider;
        synchronized(mMutex) {
            ilocationprovider = mServiceConnection.getProvider();
        }
        if(ilocationprovider == null) goto _L2; else goto _L1
_L1:
        int j = ilocationprovider.getStatus(bundle);
        int i = j;
_L4:
        return i;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
_L2:
        i = 0;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public long getStatusUpdateTime() {
        ILocationProvider ilocationprovider;
        synchronized(mMutex) {
            ilocationprovider = mServiceConnection.getProvider();
        }
        if(ilocationprovider == null) goto _L2; else goto _L1
_L1:
        long l1 = ilocationprovider.getStatusUpdateTime();
        long l = l1;
_L4:
        return l;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
_L2:
        l = 0L;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean hasMonetaryCost() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.hasMonetaryCost();
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = mEnabled;
        return flag;
    }

    public boolean isLocationTracking() {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = mLocationTracking;
        return flag;
    }

    public boolean meetsCriteria(Criteria criteria) {
        boolean flag = false;
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider = mServiceConnection.getProvider();
        if(ilocationprovider == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = ilocationprovider.meetsCriteria(criteria);
        flag = flag1;
        obj;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        obj;
        JVM INSTR monitorexit ;
        if(criteria.getAccuracy() == 0 || criteria.getAccuracy() >= getAccuracy()) {
            int i = criteria.getPowerRequirement();
            if((i == 0 || i >= getPowerRequirement()) && (!criteria.isAltitudeRequired() || supportsAltitude()) && (!criteria.isSpeedRequired() || supportsSpeed()) && (!criteria.isBearingRequired() || supportsBearing()))
                flag = true;
        }
          goto _L3
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
_L3:
        return flag;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L4
_L4:
    }

    public void reconnect(String s) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        if(mServiceConnection != null)
            mContext.unbindService(mServiceConnection);
        mServiceConnection = new Connection();
        mIntent.setPackage(s);
        mContext.bindService(mIntent, mServiceConnection, 21);
        return;
    }

    public void removeListener(int i) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.removeListener(i);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public boolean requiresCell() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.requiresCell();
        else
            flag = false;
        return flag;
    }

    public boolean requiresNetwork() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.requiresNetwork();
        else
            flag = false;
        return flag;
    }

    public boolean requiresSatellite() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.requiresSatellite();
        else
            flag = false;
        return flag;
    }

    public boolean sendExtraCommand(String s, Bundle bundle) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider = mServiceConnection.getProvider();
        if(ilocationprovider == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = ilocationprovider.sendExtraCommand(s, bundle);
        boolean flag = flag1;
        obj;
        JVM INSTR monitorexit ;
          goto _L3
_L2:
        obj;
        JVM INSTR monitorexit ;
        flag = false;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L3:
        return flag;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L4
_L4:
    }

    public void setMinTime(long l, WorkSource worksource) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider;
        mMinTime = l;
        mMinTimeSource.set(worksource);
        ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.setMinTime(l, worksource);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public boolean supportsAltitude() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.supportsAltitude();
        else
            flag = false;
        return flag;
    }

    public boolean supportsBearing() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.supportsBearing();
        else
            flag = false;
        return flag;
    }

    public boolean supportsSpeed() {
        DummyLocationProvider dummylocationprovider = getCachedAttributes();
        boolean flag;
        if(dummylocationprovider != null)
            flag = dummylocationprovider.supportsSpeed();
        else
            flag = false;
        return flag;
    }

    public void updateLocation(Location location) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.updateLocation(location);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public void updateNetworkState(int i, NetworkInfo networkinfo) {
        Object obj = mMutex;
        obj;
        JVM INSTR monitorenter ;
        ILocationProvider ilocationprovider;
        mNetworkState = i;
        mNetworkInfo = networkinfo;
        ilocationprovider = mServiceConnection.getProvider();
        Exception exception;
        if(ilocationprovider != null)
            try {
                ilocationprovider.updateNetworkState(i, networkinfo);
            }
            catch(RemoteException remoteexception) { }
            finally {
                obj;
            }
        obj;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    public static final String SERVICE_ACTION = "com.android.location.service.NetworkLocationProvider";
    private static final String TAG = "LocationProviderProxy";
    private final Context mContext;
    private boolean mEnabled;
    private final Handler mHandler;
    private final Intent mIntent = new Intent("com.android.location.service.NetworkLocationProvider");
    private boolean mLocationTracking;
    private long mMinTime;
    private WorkSource mMinTimeSource;
    private final Object mMutex = new Object();
    private final String mName;
    private NetworkInfo mNetworkInfo;
    private int mNetworkState;
    private Connection mServiceConnection;










}
