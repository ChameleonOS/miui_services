// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.location.Criteria;
import android.location.Location;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.WorkSource;

public interface LocationProviderInterface {

    public abstract void addListener(int i);

    public abstract void disable();

    public abstract void enable();

    public abstract void enableLocationTracking(boolean flag);

    public abstract int getAccuracy();

    public abstract String getInternalState();

    public abstract String getName();

    public abstract int getPowerRequirement();

    public abstract int getStatus(Bundle bundle);

    public abstract long getStatusUpdateTime();

    public abstract boolean hasMonetaryCost();

    public abstract boolean isEnabled();

    public abstract boolean meetsCriteria(Criteria criteria);

    public abstract void removeListener(int i);

    public abstract boolean requestSingleShotFix();

    public abstract boolean requiresCell();

    public abstract boolean requiresNetwork();

    public abstract boolean requiresSatellite();

    public abstract boolean sendExtraCommand(String s, Bundle bundle);

    public abstract void setMinTime(long l, WorkSource worksource);

    public abstract boolean supportsAltitude();

    public abstract boolean supportsBearing();

    public abstract boolean supportsSpeed();

    public abstract void updateLocation(Location location);

    public abstract void updateNetworkState(int i, NetworkInfo networkinfo);
}
