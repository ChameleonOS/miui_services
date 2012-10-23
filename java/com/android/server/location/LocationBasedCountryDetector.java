// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.util.Slog;
import java.io.IOException;
import java.util.*;

// Referenced classes of package com.android.server.location:
//            CountryDetectorBase

public class LocationBasedCountryDetector extends CountryDetectorBase {

    public LocationBasedCountryDetector(Context context) {
        super(context);
        mLocationManager = (LocationManager)context.getSystemService("location");
    }

    /**
     * @deprecated Method queryCountryCode is deprecated
     */

    private void queryCountryCode(final Location location) {
        this;
        JVM INSTR monitorenter ;
        if(location != null) goto _L2; else goto _L1
_L1:
        notifyListener(null);
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(mQueryThread == null) {
            mQueryThread = new Thread(new Runnable() {

                public void run() {
                    String s = null;
                    if(location != null)
                        s = getCountryFromLocation(location);
                    if(s != null)
                        mDetectedCountry = new Country(s, 1);
                    else
                        mDetectedCountry = null;
                    notifyListener(mDetectedCountry);
                    mQueryThread = null;
                }

                final LocationBasedCountryDetector this$0;
                final Location val$location;

             {
                this$0 = LocationBasedCountryDetector.this;
                location = location1;
                super();
            }
            });
            mQueryThread.start();
        }
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method detectCountry is deprecated
     */

    public Country detectCountry() {
        this;
        JVM INSTR monitorenter ;
        if(mLocationListeners != null)
            throw new IllegalStateException();
        break MISSING_BLOCK_LABEL_22;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        List list;
        int i;
        list = getEnabledProviders();
        i = list.size();
        if(i <= 0)
            break MISSING_BLOCK_LABEL_155;
        mLocationListeners = new ArrayList(i);
        Country country;
        for(int j = 0; j < i; j++) {
            String s = (String)list.get(j);
            if(isAcceptableProvider(s)) {
                LocationListener locationlistener = new LocationListener() {

                    public void onLocationChanged(Location location) {
                        if(location != null) {
                            stop();
                            queryCountryCode(location);
                        }
                    }

                    public void onProviderDisabled(String s1) {
                    }

                    public void onProviderEnabled(String s1) {
                    }

                    public void onStatusChanged(String s1, int k, Bundle bundle) {
                    }

                    final LocationBasedCountryDetector this$0;

             {
                this$0 = LocationBasedCountryDetector.this;
                super();
            }
                };
                mLocationListeners.add(locationlistener);
                registerListener(s, locationlistener);
            }
            break MISSING_BLOCK_LABEL_166;
        }

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            public void run() {
                mTimer = null;
                stop();
                queryCountryCode(getLastKnownLocation());
            }

            final LocationBasedCountryDetector this$0;

             {
                this$0 = LocationBasedCountryDetector.this;
                super();
            }
        }, getQueryLocationTimeout());
_L1:
        country = super.mDetectedCountry;
        this;
        JVM INSTR monitorexit ;
        return country;
        queryCountryCode(getLastKnownLocation());
          goto _L1
    }

    protected String getCountryFromLocation(Location location) {
        String s;
        Geocoder geocoder;
        s = null;
        geocoder = new Geocoder(super.mContext);
        String s1;
        List list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        if(list == null || list.size() <= 0)
            break MISSING_BLOCK_LABEL_63;
        s1 = ((Address)list.get(0)).getCountryCode();
        s = s1;
_L2:
        return s;
        IOException ioexception;
        ioexception;
        Slog.w("LocationBasedCountryDetector", "Exception occurs when getting country from location");
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected List getEnabledProviders() {
        if(mEnabledProviders == null)
            mEnabledProviders = mLocationManager.getProviders(true);
        return mEnabledProviders;
    }

    protected Location getLastKnownLocation() {
        List list = mLocationManager.getAllProviders();
        Location location = null;
        Iterator iterator = list.iterator();
        do {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            Location location1 = mLocationManager.getLastKnownLocation(s);
            if(location1 != null && (location == null || location.getTime() < location1.getTime()))
                location = location1;
        } while(true);
        return location;
    }

    protected long getQueryLocationTimeout() {
        return 0x493e0L;
    }

    protected boolean isAcceptableProvider(String s) {
        return "passive".equals(s);
    }

    protected void registerListener(String s, LocationListener locationlistener) {
        mLocationManager.requestLocationUpdates(s, 0L, 0.0F, locationlistener);
    }

    /**
     * @deprecated Method stop is deprecated
     */

    public void stop() {
        this;
        JVM INSTR monitorenter ;
        if(mLocationListeners == null)
            break MISSING_BLOCK_LABEL_54;
        for(Iterator iterator = mLocationListeners.iterator(); iterator.hasNext(); unregisterListener((LocationListener)iterator.next()));
        break MISSING_BLOCK_LABEL_49;
        Exception exception;
        exception;
        throw exception;
        mLocationListeners = null;
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        this;
        JVM INSTR monitorexit ;
    }

    protected void unregisterListener(LocationListener locationlistener) {
        mLocationManager.removeUpdates(locationlistener);
    }

    private static final long QUERY_LOCATION_TIMEOUT = 0x493e0L;
    private static final String TAG = "LocationBasedCountryDetector";
    private List mEnabledProviders;
    protected List mLocationListeners;
    private LocationManager mLocationManager;
    protected Thread mQueryThread;
    protected Timer mTimer;

}
