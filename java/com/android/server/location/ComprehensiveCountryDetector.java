// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.Context;
import android.location.*;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.*;
import android.text.TextUtils;
import android.util.Slog;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

// Referenced classes of package com.android.server.location:
//            CountryDetectorBase, LocationBasedCountryDetector

public class ComprehensiveCountryDetector extends CountryDetectorBase {

    public ComprehensiveCountryDetector(Context context) {
        super(context);
        mStopped = false;
        mLocationBasedCountryDetectionListener = new CountryListener() {

            public void onCountryDetected(Country country) {
                mCountryFromLocation = country;
                detectCountry(true, false);
                stopLocationBasedDetector();
            }

            final ComprehensiveCountryDetector this$0;

             {
                this$0 = ComprehensiveCountryDetector.this;
                super();
            }
        };
        mTelephonyManager = (TelephonyManager)context.getSystemService("phone");
    }

    private void addToLogs(Country country) {
        if(country != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mObject;
        obj;
        JVM INSTR monitorenter ;
        if(mLastCountryAddedToLogs != null && mLastCountryAddedToLogs.equals(country))
            continue; /* Loop/switch isn't completed */
        break MISSING_BLOCK_LABEL_40;
        Exception exception;
        exception;
        throw exception;
        mLastCountryAddedToLogs = country;
        obj;
        JVM INSTR monitorexit ;
        if(mDebugLogs.size() >= 20)
            mDebugLogs.poll();
        mDebugLogs.add(country);
        if(true) goto _L1; else goto _L3
_L3:
    }

    /**
     * @deprecated Method cancelLocationRefresh is deprecated
     */

    private void cancelLocationRefresh() {
        this;
        JVM INSTR monitorenter ;
        if(mLocationRefreshTimer != null) {
            mLocationRefreshTimer.cancel();
            mLocationRefreshTimer = null;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private Country detectCountry(boolean flag, boolean flag1) {
        Country country = getCountry();
        Country country1;
        if(mCountry != null)
            country1 = new Country(mCountry);
        else
            country1 = mCountry;
        runAfterDetectionAsync(country1, country, flag, flag1);
        mCountry = country;
        return mCountry;
    }

    private Country getCountry() {
        Country country = getNetworkBasedCountry();
        if(country == null)
            country = getLastKnownLocationBasedCountry();
        if(country == null)
            country = getSimBasedCountry();
        if(country == null)
            country = getLocaleCountry();
        addToLogs(country);
        return country;
    }

    private boolean isNetworkCountryCodeAvailable() {
        boolean flag = true;
        if(mTelephonyManager.getPhoneType() != flag)
            flag = false;
        return flag;
    }

    private void notifyIfCountryChanged(Country country, Country country1) {
        if(country1 != null && super.mListener != null && (country == null || !country.equals(country1)))
            notifyListener(country1);
    }

    /**
     * @deprecated Method scheduleLocationRefresh is deprecated
     */

    private void scheduleLocationRefresh() {
        this;
        JVM INSTR monitorenter ;
        Timer timer = mLocationRefreshTimer;
        if(timer == null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        mLocationRefreshTimer = new Timer();
        mLocationRefreshTimer.schedule(new TimerTask() {

            public void run() {
                mLocationRefreshTimer = null;
                detectCountry(false, true);
            }

            final ComprehensiveCountryDetector this$0;

             {
                this$0 = ComprehensiveCountryDetector.this;
                super();
            }
        }, 0x5265c00L);
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method startLocationBasedDetector is deprecated
     */

    private void startLocationBasedDetector(CountryListener countrylistener) {
        this;
        JVM INSTR monitorenter ;
        CountryDetectorBase countrydetectorbase = mLocationBasedCountryDetector;
        if(countrydetectorbase == null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        mLocationBasedCountryDetector = createLocationBasedCountryDetector();
        mLocationBasedCountryDetector.setCountryListener(countrylistener);
        mLocationBasedCountryDetector.detectCountry();
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method stopLocationBasedDetector is deprecated
     */

    private void stopLocationBasedDetector() {
        this;
        JVM INSTR monitorenter ;
        if(mLocationBasedCountryDetector != null) {
            mLocationBasedCountryDetector.stop();
            mLocationBasedCountryDetector = null;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method addPhoneStateListener is deprecated
     */

    protected void addPhoneStateListener() {
        this;
        JVM INSTR monitorenter ;
        if(mPhoneStateListener == null) {
            mPhoneStateListener = new PhoneStateListener() ;
            mTelephonyManager.listen(mPhoneStateListener, 1);
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected CountryDetectorBase createLocationBasedCountryDetector() {
        return new LocationBasedCountryDetector(super.mContext);
    }

    public Country detectCountry() {
        boolean flag;
        if(!mStopped)
            flag = true;
        else
            flag = false;
        return detectCountry(false, flag);
    }

    protected Country getLastKnownLocationBasedCountry() {
        return mCountryFromLocation;
    }

    protected Country getLocaleCountry() {
        Locale locale = Locale.getDefault();
        Country country;
        if(locale != null)
            country = new Country(locale.getCountry(), 3);
        else
            country = null;
        return country;
    }

    protected Country getNetworkBasedCountry() {
        if(!isNetworkCountryCodeAvailable()) goto _L2; else goto _L1
_L1:
        String s = mTelephonyManager.getNetworkCountryIso();
        if(TextUtils.isEmpty(s)) goto _L2; else goto _L3
_L3:
        Country country = new Country(s, 0);
_L5:
        return country;
_L2:
        country = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected Country getSimBasedCountry() {
        String s = mTelephonyManager.getSimCountryIso();
        Country country;
        if(!TextUtils.isEmpty(s))
            country = new Country(s, 2);
        else
            country = null;
        return country;
    }

    protected boolean isAirplaneModeOff() {
        boolean flag = false;
        if(android.provider.Settings.System.getInt(super.mContext.getContentResolver(), "airplane_mode_on", 0) == 0)
            flag = true;
        return flag;
    }

    protected boolean isGeoCoderImplemented() {
        return Geocoder.isPresent();
    }

    /**
     * @deprecated Method removePhoneStateListener is deprecated
     */

    protected void removePhoneStateListener() {
        this;
        JVM INSTR monitorenter ;
        if(mPhoneStateListener != null) {
            mTelephonyManager.listen(mPhoneStateListener, 0);
            mPhoneStateListener = null;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    void runAfterDetection(Country country, Country country1, boolean flag, boolean flag1) {
        if(flag)
            notifyIfCountryChanged(country, country1);
        if(flag1 && (country1 == null || country1.getSource() > 1) && isAirplaneModeOff() && super.mListener != null && isGeoCoderImplemented())
            startLocationBasedDetector(mLocationBasedCountryDetectionListener);
        if(country1 == null || country1.getSource() >= 1) {
            scheduleLocationRefresh();
        } else {
            cancelLocationRefresh();
            stopLocationBasedDetector();
        }
    }

    protected void runAfterDetectionAsync(final Country country, final Country detectedCountry, final boolean notifyChange, final boolean startLocationBasedDetection) {
        super.mHandler.post(new Runnable() {

            public void run() {
                runAfterDetection(country, detectedCountry, notifyChange, startLocationBasedDetection);
            }

            final ComprehensiveCountryDetector this$0;
            final Country val$country;
            final Country val$detectedCountry;
            final boolean val$notifyChange;
            final boolean val$startLocationBasedDetection;

             {
                this$0 = ComprehensiveCountryDetector.this;
                country = country1;
                detectedCountry = country2;
                notifyChange = flag;
                startLocationBasedDetection = flag1;
                super();
            }
        });
    }

    public void setCountryListener(CountryListener countrylistener) {
        CountryListener countrylistener1;
        countrylistener1 = super.mListener;
        super.mListener = countrylistener;
        if(super.mListener != null) goto _L2; else goto _L1
_L1:
        removePhoneStateListener();
        stopLocationBasedDetector();
        cancelLocationRefresh();
        mStopTime = SystemClock.elapsedRealtime();
        mTotalTime = mTotalTime + mStopTime;
_L4:
        return;
_L2:
        if(countrylistener1 == null) {
            addPhoneStateListener();
            detectCountry(false, true);
            mStartTime = SystemClock.elapsedRealtime();
            mStopTime = 0L;
            mCountServiceStateChanges = 0;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void stop() {
        Slog.i("CountryDetector", "Stop the detector.");
        cancelLocationRefresh();
        removePhoneStateListener();
        stopLocationBasedDetector();
        super.mListener = null;
        mStopped = true;
    }

    public String toString() {
        long l = SystemClock.elapsedRealtime();
        long l1 = 0L;
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("ComprehensiveCountryDetector{");
        if(mStopTime == 0L) {
            l1 = l - mStartTime;
            stringbuilder.append((new StringBuilder()).append("timeRunning=").append(l1).append(", ").toString());
        } else {
            stringbuilder.append((new StringBuilder()).append("lastRunTimeLength=").append(mStopTime - mStartTime).append(", ").toString());
        }
        stringbuilder.append((new StringBuilder()).append("totalCountServiceStateChanges=").append(mTotalCountServiceStateChanges).append(", ").toString());
        stringbuilder.append((new StringBuilder()).append("currentCountServiceStateChanges=").append(mCountServiceStateChanges).append(", ").toString());
        stringbuilder.append((new StringBuilder()).append("totalTime=").append(l1 + mTotalTime).append(", ").toString());
        stringbuilder.append((new StringBuilder()).append("currentTime=").append(l).append(", ").toString());
        stringbuilder.append("countries=");
        Country country;
        for(Iterator iterator = mDebugLogs.iterator(); iterator.hasNext(); stringbuilder.append((new StringBuilder()).append("\n   ").append(country.toString()).toString()))
            country = (Country)iterator.next();

        stringbuilder.append("}");
        return stringbuilder.toString();
    }

    static final boolean DEBUG = false;
    private static final long LOCATION_REFRESH_INTERVAL = 0x5265c00L;
    private static final int MAX_LENGTH_DEBUG_LOGS = 20;
    private static final String TAG = "CountryDetector";
    private int mCountServiceStateChanges;
    private Country mCountry;
    private Country mCountryFromLocation;
    private final ConcurrentLinkedQueue mDebugLogs = new ConcurrentLinkedQueue();
    private Country mLastCountryAddedToLogs;
    private CountryListener mLocationBasedCountryDetectionListener;
    protected CountryDetectorBase mLocationBasedCountryDetector;
    protected Timer mLocationRefreshTimer;
    private final Object mObject = new Object();
    private PhoneStateListener mPhoneStateListener;
    private long mStartTime;
    private long mStopTime;
    private boolean mStopped;
    private final TelephonyManager mTelephonyManager;
    private int mTotalCountServiceStateChanges;
    private long mTotalTime;


/*
    static Country access$002(ComprehensiveCountryDetector comprehensivecountrydetector, Country country) {
        comprehensivecountrydetector.mCountryFromLocation = country;
        return country;
    }

*/




/*
    static int access$308(ComprehensiveCountryDetector comprehensivecountrydetector) {
        int i = comprehensivecountrydetector.mCountServiceStateChanges;
        comprehensivecountrydetector.mCountServiceStateChanges = i + 1;
        return i;
    }

*/


/*
    static int access$408(ComprehensiveCountryDetector comprehensivecountrydetector) {
        int i = comprehensivecountrydetector.mTotalCountServiceStateChanges;
        comprehensivecountrydetector.mTotalCountServiceStateChanges = i + 1;
        return i;
    }

*/

}
