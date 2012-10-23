// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.*;
import android.location.GeocoderParams;
import android.location.IGeocodeProvider;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

public class GeocoderProxy {
    private class Connection
        implements ServiceConnection {

        public IGeocodeProvider getProvider() {
            this;
            JVM INSTR monitorenter ;
            IGeocodeProvider igeocodeprovider = mProvider;
            return igeocodeprovider;
        }

        public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            this;
            JVM INSTR monitorenter ;
            mProvider = android.location.IGeocodeProvider.Stub.asInterface(ibinder);
            return;
        }

        public void onServiceDisconnected(ComponentName componentname) {
            this;
            JVM INSTR monitorenter ;
            mProvider = null;
            return;
        }

        private IGeocodeProvider mProvider;
        final GeocoderProxy this$0;

        private Connection() {
            this$0 = GeocoderProxy.this;
            super();
        }

    }


    public GeocoderProxy(Context context, String s) {
        mContext = context;
        reconnect(s);
    }

    public String getFromLocation(double d, double d1, int i, GeocoderParams geocoderparams, List list) {
        IGeocodeProvider igeocodeprovider;
        synchronized(mMutex) {
            igeocodeprovider = mServiceConnection.getProvider();
        }
        if(igeocodeprovider == null) goto _L2; else goto _L1
_L1:
        String s1 = igeocodeprovider.getFromLocation(d, d1, i, geocoderparams, list);
        String s = s1;
_L4:
        return s;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Log.e("GeocoderProxy", "getFromLocation failed", remoteexception);
_L2:
        s = "Service not Available";
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String getFromLocationName(String s, double d, double d1, double d2, 
            double d3, int i, GeocoderParams geocoderparams, List list) {
        IGeocodeProvider igeocodeprovider;
        synchronized(mMutex) {
            igeocodeprovider = mServiceConnection.getProvider();
        }
        if(igeocodeprovider == null) goto _L2; else goto _L1
_L1:
        String s2 = igeocodeprovider.getFromLocationName(s, d, d1, d2, d3, i, geocoderparams, list);
        String s1 = s2;
_L4:
        return s1;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        Log.e("GeocoderProxy", "getFromLocationName failed", remoteexception);
_L2:
        s1 = "Service not Available";
        if(true) goto _L4; else goto _L3
_L3:
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

    public static final String SERVICE_ACTION = "com.android.location.service.GeocodeProvider";
    private static final String TAG = "GeocoderProxy";
    private final Context mContext;
    private final Intent mIntent = new Intent("com.android.location.service.GeocodeProvider");
    private final Object mMutex = new Object();
    private Connection mServiceConnection;
}
