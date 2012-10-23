// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.location.*;
import android.net.NetworkInfo;
import android.os.*;
import android.util.Log;
import android.util.PrintWriterPrinter;
import java.io.PrintWriter;

// Referenced classes of package com.android.server.location:
//            LocationProviderInterface

public class MockProvider
    implements LocationProviderInterface {

    public MockProvider(String s, ILocationManager ilocationmanager, boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, 
            boolean flag5, boolean flag6, int i, int j) {
        mName = s;
        mLocationManager = ilocationmanager;
        mRequiresNetwork = flag;
        mRequiresSatellite = flag1;
        mRequiresCell = flag2;
        mHasMonetaryCost = flag3;
        mSupportsAltitude = flag4;
        mSupportsBearing = flag6;
        mSupportsSpeed = flag5;
        mPowerRequirement = i;
        mAccuracy = j;
        mLocation = new Location(s);
    }

    public void addListener(int i) {
    }

    public void clearLocation() {
        mHasLocation = false;
    }

    public void clearStatus() {
        mHasStatus = false;
        mStatusUpdateTime = 0L;
    }

    public void disable() {
        mEnabled = false;
    }

    public void dump(PrintWriter printwriter, String s) {
        printwriter.println((new StringBuilder()).append(s).append(mName).toString());
        printwriter.println((new StringBuilder()).append(s).append("mHasLocation=").append(mHasLocation).toString());
        printwriter.println((new StringBuilder()).append(s).append("mLocation:").toString());
        mLocation.dump(new PrintWriterPrinter(printwriter), (new StringBuilder()).append(s).append("  ").toString());
        printwriter.println((new StringBuilder()).append(s).append("mHasStatus=").append(mHasStatus).toString());
        printwriter.println((new StringBuilder()).append(s).append("mStatus=").append(mStatus).toString());
        printwriter.println((new StringBuilder()).append(s).append("mStatusUpdateTime=").append(mStatusUpdateTime).toString());
        printwriter.println((new StringBuilder()).append(s).append("mExtras=").append(mExtras).toString());
    }

    public void enable() {
        mEnabled = true;
    }

    public void enableLocationTracking(boolean flag) {
    }

    public int getAccuracy() {
        return mAccuracy;
    }

    public String getInternalState() {
        return null;
    }

    public String getName() {
        return mName;
    }

    public int getPowerRequirement() {
        return mPowerRequirement;
    }

    public int getStatus(Bundle bundle) {
        int i;
        if(mHasStatus) {
            bundle.clear();
            bundle.putAll(mExtras);
            i = mStatus;
        } else {
            i = 2;
        }
        return i;
    }

    public long getStatusUpdateTime() {
        return mStatusUpdateTime;
    }

    public boolean hasMonetaryCost() {
        return mHasMonetaryCost;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public boolean meetsCriteria(Criteria criteria) {
        boolean flag = false;
        if(criteria.getAccuracy() == 0 || criteria.getAccuracy() >= mAccuracy) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        int i = criteria.getPowerRequirement();
        if((i == 0 || i >= mPowerRequirement) && (!criteria.isAltitudeRequired() || mSupportsAltitude) && (!criteria.isSpeedRequired() || mSupportsSpeed) && (!criteria.isBearingRequired() || mSupportsBearing))
            flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void removeListener(int i) {
    }

    public boolean requestSingleShotFix() {
        return false;
    }

    public boolean requiresCell() {
        return mRequiresCell;
    }

    public boolean requiresNetwork() {
        return mRequiresNetwork;
    }

    public boolean requiresSatellite() {
        return mRequiresSatellite;
    }

    public boolean sendExtraCommand(String s, Bundle bundle) {
        return false;
    }

    public void setLocation(Location location) {
        mLocation.set(location);
        mHasLocation = true;
        mLocationManager.reportLocation(mLocation, false);
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Log.e("MockProvider", "RemoteException calling reportLocation");
          goto _L1
    }

    public void setMinTime(long l, WorkSource worksource) {
    }

    public void setStatus(int i, Bundle bundle, long l) {
        mStatus = i;
        mStatusUpdateTime = l;
        mExtras.clear();
        if(bundle != null)
            mExtras.putAll(bundle);
        mHasStatus = true;
    }

    public boolean supportsAltitude() {
        return mSupportsAltitude;
    }

    public boolean supportsBearing() {
        return mSupportsBearing;
    }

    public boolean supportsSpeed() {
        return mSupportsSpeed;
    }

    public void updateLocation(Location location) {
    }

    public void updateNetworkState(int i, NetworkInfo networkinfo) {
    }

    private static final String TAG = "MockProvider";
    private final int mAccuracy;
    private boolean mEnabled;
    private final Bundle mExtras = new Bundle();
    private boolean mHasLocation;
    private final boolean mHasMonetaryCost;
    private boolean mHasStatus;
    private final Location mLocation;
    private final ILocationManager mLocationManager;
    private final String mName;
    private final int mPowerRequirement;
    private final boolean mRequiresCell;
    private final boolean mRequiresNetwork;
    private final boolean mRequiresSatellite;
    private int mStatus;
    private long mStatusUpdateTime;
    private final boolean mSupportsAltitude;
    private final boolean mSupportsBearing;
    private final boolean mSupportsSpeed;
}
