// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.location.*;
import android.net.NetworkInfo;
import android.os.*;
import android.util.Log;

// Referenced classes of package com.android.server.location:
//            LocationProviderInterface

public class PassiveProvider
    implements LocationProviderInterface {

    public PassiveProvider(ILocationManager ilocationmanager) {
        mLocationManager = ilocationmanager;
    }

    public void addListener(int i) {
    }

    public void disable() {
    }

    public void enable() {
    }

    public void enableLocationTracking(boolean flag) {
        mTracking = flag;
    }

    public int getAccuracy() {
        return -1;
    }

    public String getInternalState() {
        return null;
    }

    public String getName() {
        return "passive";
    }

    public int getPowerRequirement() {
        return -1;
    }

    public int getStatus(Bundle bundle) {
        byte byte0;
        if(mTracking)
            byte0 = 2;
        else
            byte0 = 1;
        return byte0;
    }

    public long getStatusUpdateTime() {
        return -1L;
    }

    public boolean hasMonetaryCost() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean meetsCriteria(Criteria criteria) {
        return false;
    }

    public void removeListener(int i) {
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public boolean requiresCell() {
        return false;
    }

    public boolean requiresNetwork() {
        return false;
    }

    public boolean requiresSatellite() {
        return false;
    }

    public boolean sendExtraCommand(String s, Bundle bundle) {
        return false;
    }

    public void setMinTime(long l, WorkSource worksource) {
    }

    public boolean supportsAltitude() {
        return false;
    }

    public boolean supportsBearing() {
        return false;
    }

    public boolean supportsSpeed() {
        return false;
    }

    public void updateLocation(Location location) {
        if(!mTracking)
            break MISSING_BLOCK_LABEL_18;
        mLocationManager.reportLocation(location, true);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Log.e("PassiveProvider", "RemoteException calling reportLocation");
          goto _L1
    }

    public void updateNetworkState(int i, NetworkInfo networkinfo) {
    }

    private static final String TAG = "PassiveProvider";
    private final ILocationManager mLocationManager;
    private boolean mTracking;
}
