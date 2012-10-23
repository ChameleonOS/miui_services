// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.PendingIntent;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.location.*;
import android.net.ConnectivityManager;
import android.os.*;
import android.util.*;
import com.android.internal.content.PackageMonitor;
import com.android.server.location.GeocoderProxy;
import com.android.server.location.GpsLocationProvider;
import com.android.server.location.LocationProviderInterface;
import com.android.server.location.LocationProviderProxy;
import com.android.server.location.MockProvider;
import com.android.server.location.PassiveProvider;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;

public class LocationManagerService extends android.location.ILocationManager.Stub
    implements Runnable {
    private class LocationWorkerHandler extends Handler {

        public void handleMessage(Message message) {
            boolean flag = true;
            if(message.what != flag) goto _L2; else goto _L1
_L1:
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            Location location = (Location)message.obj;
            String s3 = location.getProvider();
            Exception exception;
            String s;
            String s1;
            String s2;
            int i;
            LocationProviderInterface locationproviderinterface;
            if(message.arg1 != flag)
                flag = false;
            if(flag) goto _L4; else goto _L3
_L3:
            i = -1 + mProviders.size();
_L6:
            if(i >= 0) {
                locationproviderinterface = (LocationProviderInterface)mProviders.get(i);
                if(!s3.equals(locationproviderinterface.getName()))
                    locationproviderinterface.updateLocation(location);
                i--;
                continue; /* Loop/switch isn't completed */
            }
_L4:
            if(isAllowedBySettingsLocked(s3))
                handleLocationChangedLocked(location, flag);
            break; /* Loop/switch isn't completed */
_L2:
            try {
                if(message.what == 2) {
                    s = (String)message.obj;
                    if(mNetworkLocationProviderPackageName != null && mPackageManager.resolveService((new Intent("com.android.location.service.NetworkLocationProvider")).setPackage(s), 0) != null) {
                        s2 = findBestPackage("com.android.location.service.NetworkLocationProvider", mNetworkLocationProviderPackageName);
                        if(s.equals(s2)) {
                            mNetworkLocationProvider.reconnect(s2);
                            mNetworkLocationProviderPackageName = s;
                        }
                    }
                    if(mGeocodeProviderPackageName != null && mPackageManager.resolveService((new Intent("com.android.location.service.GeocodeProvider")).setPackage(s), 0) != null) {
                        s1 = findBestPackage("com.android.location.service.GeocodeProvider", mGeocodeProviderPackageName);
                        if(s.equals(s1)) {
                            mGeocodeProvider.reconnect(s1);
                            mGeocodeProviderPackageName = s;
                        }
                    }
                }
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception) {
                Slog.e("LocationManagerService", "Exception in LocationWorkerHandler.handleMessage:", exception);
            }
            break; /* Loop/switch isn't completed */
            if(true) goto _L6; else goto _L5
_L5:
        }

        final LocationManagerService this$0;

        private LocationWorkerHandler() {
            this$0 = LocationManagerService.this;
            super();
        }

    }

    class ProximityListener extends android.location.ILocationListener.Stub
        implements android.app.PendingIntent.OnFinished {

        public void onLocationChanged(Location location) {
            if(location.getProvider().equals("gps"))
                isGpsAvailable = true;
            if(!isGpsAvailable || !location.getProvider().equals("network")) goto _L2; else goto _L1
_L1:
            return;
_L2:
            long l;
            double d;
            double d1;
            float f;
            ArrayList arraylist;
            Iterator iterator;
            l = System.currentTimeMillis();
            d = location.getLatitude();
            d1 = location.getLongitude();
            f = location.getAccuracy();
            arraylist = null;
            iterator = mProximityAlerts.values().iterator();
_L4:
            ProximityAlert proximityalert1;
            PendingIntent pendingintent1;
            boolean flag;
            boolean flag1;
            Intent intent1;
            if(!iterator.hasNext())
                break; /* Loop/switch isn't completed */
            proximityalert1 = (ProximityAlert)iterator.next();
            pendingintent1 = proximityalert1.getIntent();
            long l1 = proximityalert1.getExpiration();
            if(l1 != -1L && l > l1)
                break MISSING_BLOCK_LABEL_386;
            flag = mProximitiesEntered.contains(proximityalert1);
            flag1 = proximityalert1.isInProximity(d, d1, f);
            if(flag || !flag1)
                break MISSING_BLOCK_LABEL_272;
            mProximitiesEntered.add(proximityalert1);
            intent1 = new Intent();
            intent1.putExtra("entering", true);
            this;
            JVM INSTR monitorenter ;
            pendingintent1.send(mContext, 0, intent1, this, mLocationHandler, "android.permission.ACCESS_FINE_LOCATION");
            incrementPendingBroadcasts();
            continue; /* Loop/switch isn't completed */
            android.app.PendingIntent.CanceledException canceledexception1;
            canceledexception1;
            if(arraylist == null)
                arraylist = new ArrayList();
            arraylist.add(pendingintent1);
            continue; /* Loop/switch isn't completed */
            Intent intent;
            if(!flag || flag1)
                continue; /* Loop/switch isn't completed */
            mProximitiesEntered.remove(proximityalert1);
            intent = new Intent();
            intent.putExtra("entering", false);
            this;
            JVM INSTR monitorenter ;
            pendingintent1.send(mContext, 0, intent, this, mLocationHandler, "android.permission.ACCESS_FINE_LOCATION");
            incrementPendingBroadcasts();
            continue; /* Loop/switch isn't completed */
            android.app.PendingIntent.CanceledException canceledexception;
            canceledexception;
            if(arraylist == null)
                arraylist = new ArrayList();
            arraylist.add(pendingintent1);
            continue; /* Loop/switch isn't completed */
            if(arraylist == null)
                arraylist = new ArrayList();
            PendingIntent pendingintent2 = proximityalert1.getIntent();
            arraylist.add(pendingintent2);
            if(true) goto _L4; else goto _L3
_L3:
            if(arraylist != null) {
                Iterator iterator1 = arraylist.iterator();
                while(iterator1.hasNext())  {
                    PendingIntent pendingintent = (PendingIntent)iterator1.next();
                    ProximityAlert proximityalert = (ProximityAlert)mProximityAlerts.get(pendingintent);
                    mProximitiesEntered.remove(proximityalert);
                    removeProximityAlertLocked(pendingintent);
                }
            }
            if(true) goto _L1; else goto _L5
_L5:
        }

        public void onProviderDisabled(String s) {
            if(s.equals("gps"))
                isGpsAvailable = false;
        }

        public void onProviderEnabled(String s) {
        }

        public void onSendFinished(PendingIntent pendingintent, Intent intent, int i, String s, Bundle bundle) {
            this;
            JVM INSTR monitorenter ;
            decrementPendingBroadcasts();
            return;
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
            if(s.equals("gps") && i != 2)
                isGpsAvailable = false;
        }

        boolean isGpsAvailable;
        final LocationManagerService this$0;

        ProximityListener() {
            this$0 = LocationManagerService.this;
            super();
            isGpsAvailable = false;
        }
    }

    class ProximityAlert {

        void dump(PrintWriter printwriter, String s) {
            printwriter.println((new StringBuilder()).append(s).append(this).toString());
            printwriter.println((new StringBuilder()).append(s).append("mLatitude=").append(mLatitude).append(" mLongitude=").append(mLongitude).toString());
            printwriter.println((new StringBuilder()).append(s).append("mRadius=").append(mRadius).append(" mExpiration=").append(mExpiration).toString());
            printwriter.println((new StringBuilder()).append(s).append("mIntent=").append(mIntent).toString());
            printwriter.println((new StringBuilder()).append(s).append("mLocation:").toString());
            mLocation.dump(new PrintWriterPrinter(printwriter), (new StringBuilder()).append(s).append("  ").toString());
        }

        long getExpiration() {
            return mExpiration;
        }

        PendingIntent getIntent() {
            return mIntent;
        }

        boolean isInProximity(double d, double d1, float f) {
            Location location = new Location("");
            location.setLatitude(d);
            location.setLongitude(d1);
            boolean flag;
            if((double)location.distanceTo(mLocation) <= (double)Math.max(mRadius, f))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public String toString() {
            return (new StringBuilder()).append("ProximityAlert{").append(Integer.toHexString(System.identityHashCode(this))).append(" uid ").append(mUid).append(mIntent).append("}").toString();
        }

        final long mExpiration;
        final PendingIntent mIntent;
        final double mLatitude;
        final Location mLocation = new Location("");
        final double mLongitude;
        final float mRadius;
        final int mUid;
        final LocationManagerService this$0;

        public ProximityAlert(int i, double d, double d1, float f, 
                long l, PendingIntent pendingintent) {
            this$0 = LocationManagerService.this;
            super();
            mUid = i;
            mLatitude = d;
            mLongitude = d1;
            mRadius = f;
            mExpiration = l;
            mIntent = pendingintent;
            mLocation.setLatitude(d);
            mLocation.setLongitude(d1);
        }
    }

    private class UpdateRecord {

        void disposeLocked() {
            ArrayList arraylist = (ArrayList)mRecordsByProvider.get(mProvider);
            if(arraylist != null)
                arraylist.remove(this);
        }

        void dump(PrintWriter printwriter, String s) {
            printwriter.println((new StringBuilder()).append(s).append(this).toString());
            printwriter.println((new StringBuilder()).append(s).append("mProvider=").append(mProvider).append(" mReceiver=").append(mReceiver).toString());
            printwriter.println((new StringBuilder()).append(s).append("mMinTime=").append(mMinTime).append(" mMinDistance=").append(mMinDistance).toString());
            printwriter.println((new StringBuilder()).append(s).append("mSingleShot=").append(mSingleShot).toString());
            printwriter.println((new StringBuilder()).append(s).append("mUid=").append(mUid).toString());
            printwriter.println((new StringBuilder()).append(s).append("mLastFixBroadcast:").toString());
            if(mLastFixBroadcast != null)
                mLastFixBroadcast.dump(new PrintWriterPrinter(printwriter), (new StringBuilder()).append(s).append("  ").toString());
            printwriter.println((new StringBuilder()).append(s).append("mLastStatusBroadcast=").append(mLastStatusBroadcast).toString());
        }

        public String toString() {
            return (new StringBuilder()).append("UpdateRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(" mProvider: ").append(mProvider).append(" mUid: ").append(mUid).append("}").toString();
        }

        Location mLastFixBroadcast;
        long mLastStatusBroadcast;
        final float mMinDistance;
        final long mMinTime;
        final String mProvider;
        final Receiver mReceiver;
        final boolean mSingleShot;
        final int mUid;
        final LocationManagerService this$0;

        UpdateRecord(String s, long l, float f, boolean flag, Receiver receiver, 
                int i) {
            this$0 = LocationManagerService.this;
            super();
            mProvider = s;
            mReceiver = receiver;
            mMinTime = l;
            mMinDistance = f;
            mSingleShot = flag;
            mUid = i;
            ArrayList arraylist = (ArrayList)mRecordsByProvider.get(s);
            if(arraylist == null) {
                arraylist = new ArrayList();
                mRecordsByProvider.put(s, arraylist);
            }
            if(!arraylist.contains(this))
                arraylist.add(this);
        }
    }

    private class LpCapabilityComparator
        implements Comparator {

        private int score(LocationProviderInterface locationproviderinterface) {
            byte byte0 = 4;
            byte byte1;
            byte byte2;
            int i;
            if(locationproviderinterface.supportsAltitude())
                byte1 = byte0;
            else
                byte1 = 0;
            if(locationproviderinterface.supportsBearing())
                byte2 = byte0;
            else
                byte2 = 0;
            i = byte1 + byte2;
            if(!locationproviderinterface.supportsSpeed())
                byte0 = 0;
            return i + byte0;
        }

        public int compare(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            return score(locationproviderinterface1) - score(locationproviderinterface);
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((LocationProviderInterface)obj, (LocationProviderInterface)obj1);
        }

        public boolean equals(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            boolean flag;
            if(score(locationproviderinterface) == score(locationproviderinterface1))
                flag = true;
            else
                flag = false;
            return flag;
        }

        private static final int ALTITUDE_SCORE = 4;
        private static final int BEARING_SCORE = 4;
        private static final int SPEED_SCORE = 4;
        final LocationManagerService this$0;

        private LpCapabilityComparator() {
            this$0 = LocationManagerService.this;
            super();
        }

    }

    private class LpAccuracyComparator
        implements Comparator {

        public int compare(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            return locationproviderinterface.getAccuracy() - locationproviderinterface1.getAccuracy();
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((LocationProviderInterface)obj, (LocationProviderInterface)obj1);
        }

        public boolean equals(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            boolean flag;
            if(locationproviderinterface.getAccuracy() == locationproviderinterface1.getAccuracy())
                flag = true;
            else
                flag = false;
            return flag;
        }

        final LocationManagerService this$0;

        private LpAccuracyComparator() {
            this$0 = LocationManagerService.this;
            super();
        }

    }

    private class LpPowerComparator
        implements Comparator {

        public int compare(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            return locationproviderinterface.getPowerRequirement() - locationproviderinterface1.getPowerRequirement();
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((LocationProviderInterface)obj, (LocationProviderInterface)obj1);
        }

        public boolean equals(LocationProviderInterface locationproviderinterface, LocationProviderInterface locationproviderinterface1) {
            boolean flag;
            if(locationproviderinterface.getPowerRequirement() == locationproviderinterface1.getPowerRequirement())
                flag = true;
            else
                flag = false;
            return flag;
        }

        final LocationManagerService this$0;

        private LpPowerComparator() {
            this$0 = LocationManagerService.this;
            super();
        }

    }

    private final class SettingsObserver
        implements Observer {

        public void update(Observable observable, Object obj) {
            Object obj1 = mLock;
            obj1;
            JVM INSTR monitorenter ;
            updateProvidersLocked();
            return;
        }

        final LocationManagerService this$0;

        private SettingsObserver() {
            this$0 = LocationManagerService.this;
            super();
        }

    }

    private final class Receiver
        implements android.os.IBinder.DeathRecipient, android.app.PendingIntent.OnFinished {

        private void decrementPendingBroadcastsLocked() {
            int i = -1 + mPendingBroadcasts;
            mPendingBroadcasts = i;
            if(i == 0)
                decrementPendingBroadcasts();
        }

        private void incrementPendingBroadcastsLocked() {
            int i = mPendingBroadcasts;
            mPendingBroadcasts = i + 1;
            if(i == 0)
                incrementPendingBroadcasts();
        }

        public void binderDied() {
            synchronized(mLock) {
                removeUpdatesLocked(this);
            }
            this;
            JVM INSTR monitorenter ;
            if(mPendingBroadcasts > 0) {
                decrementPendingBroadcasts();
                mPendingBroadcasts = 0;
            }
            this;
            JVM INSTR monitorexit ;
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
            Exception exception1;
            exception1;
            this;
            JVM INSTR monitorexit ;
            throw exception1;
        }

        public boolean callLocationChangedLocked(Location location) {
            if(mListener == null)
                break MISSING_BLOCK_LABEL_57;
            this;
            JVM INSTR monitorenter ;
            mListener.onLocationChanged(location);
            if(mListener != mProximityListener)
                incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_127;
            RemoteException remoteexception;
            remoteexception;
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_130;
            Intent intent;
            intent = new Intent();
            intent.putExtra("location", location);
            this;
            JVM INSTR monitorenter ;
            mPendingIntent.send(mContext, 0, intent, this, mLocationHandler, mRequiredPermissions);
            incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_127;
            android.app.PendingIntent.CanceledException canceledexception;
            canceledexception;
            flag = false;
            break MISSING_BLOCK_LABEL_130;
            flag = true;
            return flag;
        }

        public boolean callProviderEnabledLocked(String s, boolean flag) {
            if(mListener == null)
                break MISSING_BLOCK_LABEL_74;
            this;
            JVM INSTR monitorenter ;
            if(flag)
                mListener.onProviderEnabled(s);
            else
                mListener.onProviderDisabled(s);
            if(mListener != mProximityListener)
                incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_144;
            RemoteException remoteexception;
            remoteexception;
            boolean flag1;
            flag1 = false;
            break MISSING_BLOCK_LABEL_147;
            Intent intent;
            intent = new Intent();
            intent.putExtra("providerEnabled", flag);
            this;
            JVM INSTR monitorenter ;
            mPendingIntent.send(mContext, 0, intent, this, mLocationHandler, mRequiredPermissions);
            incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_144;
            android.app.PendingIntent.CanceledException canceledexception;
            canceledexception;
            flag1 = false;
            break MISSING_BLOCK_LABEL_147;
            flag1 = true;
            return flag1;
        }

        public boolean callStatusChangedLocked(String s, int i, Bundle bundle) {
            if(mListener == null)
                break MISSING_BLOCK_LABEL_59;
            this;
            JVM INSTR monitorenter ;
            mListener.onStatusChanged(s, i, bundle);
            if(mListener != mProximityListener)
                incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_139;
            RemoteException remoteexception;
            remoteexception;
            boolean flag;
            flag = false;
            break MISSING_BLOCK_LABEL_142;
            Intent intent;
            intent = new Intent();
            intent.putExtras(bundle);
            intent.putExtra("status", i);
            this;
            JVM INSTR monitorenter ;
            mPendingIntent.send(mContext, 0, intent, this, mLocationHandler, mRequiredPermissions);
            incrementPendingBroadcastsLocked();
            break MISSING_BLOCK_LABEL_139;
            android.app.PendingIntent.CanceledException canceledexception;
            canceledexception;
            flag = false;
            break MISSING_BLOCK_LABEL_142;
            flag = true;
            return flag;
        }

        public boolean equals(Object obj) {
            boolean flag;
            if(obj instanceof Receiver)
                flag = mKey.equals(((Receiver)obj).mKey);
            else
                flag = false;
            return flag;
        }

        public ILocationListener getListener() {
            if(mListener != null)
                return mListener;
            else
                throw new IllegalStateException("Request for non-existent listener");
        }

        public PendingIntent getPendingIntent() {
            if(mPendingIntent != null)
                return mPendingIntent;
            else
                throw new IllegalStateException("Request for non-existent intent");
        }

        public int hashCode() {
            return mKey.hashCode();
        }

        public boolean isListener() {
            boolean flag;
            if(mListener != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isPendingIntent() {
            boolean flag;
            if(mPendingIntent != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public void onSendFinished(PendingIntent pendingintent, Intent intent, int i, String s, Bundle bundle) {
            this;
            JVM INSTR monitorenter ;
            decrementPendingBroadcastsLocked();
            return;
        }

        public String toString() {
            String s;
            if(mListener != null)
                s = (new StringBuilder()).append("Receiver{").append(Integer.toHexString(System.identityHashCode(this))).append(" Listener ").append(mKey).append("}").toString();
            else
                s = (new StringBuilder()).append("Receiver{").append(Integer.toHexString(System.identityHashCode(this))).append(" Intent ").append(mKey).append("}").toString();
            return (new StringBuilder()).append(s).append("mUpdateRecords: ").append(mUpdateRecords).toString();
        }

        final Object mKey;
        final ILocationListener mListener;
        int mPendingBroadcasts;
        final PendingIntent mPendingIntent;
        String mRequiredPermissions;
        final HashMap mUpdateRecords;
        final LocationManagerService this$0;


        Receiver(PendingIntent pendingintent) {
            this$0 = LocationManagerService.this;
            super();
            mUpdateRecords = new HashMap();
            mPendingIntent = pendingintent;
            mListener = null;
            mKey = pendingintent;
        }

        Receiver(ILocationListener ilocationlistener) {
            this$0 = LocationManagerService.this;
            super();
            mUpdateRecords = new HashMap();
            mListener = ilocationlistener;
            mPendingIntent = null;
            mKey = ilocationlistener.asBinder();
        }
    }


    public LocationManagerService(Context context) {
        mLastWriteTime = new HashMap();
        mWakeLock = null;
        mProximityReceiver = null;
        mProximityListener = null;
        mProximityAlerts = new HashMap();
        mProximitiesEntered = new HashSet();
        mLastKnownLocation = new HashMap();
        mNetworkState = 1;
        mContext = context;
        Resources resources = context.getResources();
        mNetworkLocationProviderPackageName = resources.getString(0x104001c);
        mGeocodeProviderPackageName = resources.getString(0x104001d);
        mPackageMonitor.register(context, null, true);
    }

    private List _getAllProvidersLocked() {
        ArrayList arraylist = new ArrayList(mProviders.size());
        for(int i = -1 + mProviders.size(); i >= 0; i--)
            arraylist.add(((LocationProviderInterface)mProviders.get(i)).getName());

        return arraylist;
    }

    private Location _getLastKnownLocationLocked(String s) {
        Location location;
        location = null;
        checkPermissionsSafe(s, null);
        break MISSING_BLOCK_LABEL_9;
        if((LocationProviderInterface)mProvidersByName.get(s) != null && isAllowedBySettingsLocked(s))
            location = (Location)mLastKnownLocation.get(s);
        return location;
    }

    private Bundle _getProviderInfoLocked(String s) {
        Bundle bundle = null;
        LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
        if(locationproviderinterface != null) {
            checkPermissionsSafe(s, null);
            bundle = new Bundle();
            bundle.putBoolean("network", locationproviderinterface.requiresNetwork());
            bundle.putBoolean("satellite", locationproviderinterface.requiresSatellite());
            bundle.putBoolean("cell", locationproviderinterface.requiresCell());
            bundle.putBoolean("cost", locationproviderinterface.hasMonetaryCost());
            bundle.putBoolean("altitude", locationproviderinterface.supportsAltitude());
            bundle.putBoolean("speed", locationproviderinterface.supportsSpeed());
            bundle.putBoolean("bearing", locationproviderinterface.supportsBearing());
            bundle.putInt("power", locationproviderinterface.getPowerRequirement());
            bundle.putInt("accuracy", locationproviderinterface.getAccuracy());
        }
        return bundle;
    }

    private List _getProvidersLocked(Criteria criteria, boolean flag) {
        ArrayList arraylist = new ArrayList(mProviders.size());
        int i = -1 + mProviders.size();
        do {
            if(i < 0)
                break;
            LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProviders.get(i);
            String s = locationproviderinterface.getName();
            if(isAllowedProviderSafe(s) && (!flag || isAllowedBySettingsLocked(s)) && (criteria == null || locationproviderinterface.meetsCriteria(criteria)))
                arraylist.add(s);
            i--;
        } while(true);
        return arraylist;
    }

    private boolean _isProviderEnabledLocked(String s) {
        checkPermissionsSafe(s, null);
        boolean flag;
        if((LocationProviderInterface)mProvidersByName.get(s) == null)
            flag = false;
        else
            flag = isAllowedBySettingsLocked(s);
        return flag;
    }

    private void _loadProvidersLocked() {
        if(GpsLocationProvider.isSupported()) {
            GpsLocationProvider gpslocationprovider = new GpsLocationProvider(mContext, this);
            mGpsStatusProvider = gpslocationprovider.getGpsStatusProvider();
            mNetInitiatedListener = gpslocationprovider.getNetInitiatedListener();
            addProvider(gpslocationprovider);
            mGpsLocationProvider = gpslocationprovider;
        }
        PassiveProvider passiveprovider = new PassiveProvider(this);
        addProvider(passiveprovider);
        mEnabledProviders.add(passiveprovider.getName());
        if(mNetworkLocationProviderPackageName != null) {
            String s1 = findBestPackage("com.android.location.service.NetworkLocationProvider", mNetworkLocationProviderPackageName);
            if(s1 != null) {
                mNetworkLocationProvider = new LocationProviderProxy(mContext, "network", s1, mLocationHandler);
                mNetworkLocationProviderPackageName = s1;
                addProvider(mNetworkLocationProvider);
            }
        }
        if(mGeocodeProviderPackageName != null) {
            String s = findBestPackage("com.android.location.service.GeocodeProvider", mGeocodeProviderPackageName);
            if(s != null) {
                mGeocodeProvider = new GeocoderProxy(mContext, s);
                mGeocodeProviderPackageName = s;
            }
        }
        updateProvidersLocked();
    }

    private void addProvider(LocationProviderInterface locationproviderinterface) {
        mProviders.add(locationproviderinterface);
        mProvidersByName.put(locationproviderinterface.getName(), locationproviderinterface);
    }

    private void addProximityAlertLocked(double d, double d1, float f, long l, 
            PendingIntent pendingintent) {
        if(!isAllowedProviderSafe("gps") || !isAllowedProviderSafe("network"))
            throw new SecurityException("Requires ACCESS_FINE_LOCATION permission");
        if(l != -1L)
            l += System.currentTimeMillis();
        ProximityAlert proximityalert = new ProximityAlert(Binder.getCallingUid(), d, d1, f, l, pendingintent);
        mProximityAlerts.put(pendingintent, proximityalert);
        if(mProximityReceiver == null) {
            mProximityListener = new ProximityListener();
            mProximityReceiver = new Receiver(mProximityListener);
            for(int i = -1 + mProviders.size(); i >= 0; i--)
                requestLocationUpdatesLocked(((LocationProviderInterface)mProviders.get(i)).getName(), 1000L, 1.0F, false, mProximityReceiver);

        }
    }

    private LocationProviderInterface best(List list) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        arraylist = new ArrayList(list.size());
        String s;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(mProvidersByName.get(s)))
            s = (String)iterator.next();

        break MISSING_BLOCK_LABEL_75;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        LocationProviderInterface locationproviderinterface;
        if(arraylist.size() < 2) {
            locationproviderinterface = (LocationProviderInterface)arraylist.get(0);
        } else {
            Collections.sort(arraylist, new LpPowerComparator());
            int i = ((LocationProviderInterface)arraylist.get(0)).getPowerRequirement();
            if(i < ((LocationProviderInterface)arraylist.get(1)).getPowerRequirement()) {
                locationproviderinterface = (LocationProviderInterface)arraylist.get(0);
            } else {
                ArrayList arraylist1 = new ArrayList();
                int j = 0;
                for(int k = arraylist.size(); j < k && ((LocationProviderInterface)arraylist.get(j)).getPowerRequirement() == i; j++)
                    arraylist1.add(arraylist.get(j));

                Collections.sort(arraylist1, new LpAccuracyComparator());
                int l = ((LocationProviderInterface)arraylist1.get(0)).getAccuracy();
                if(l < ((LocationProviderInterface)arraylist1.get(1)).getAccuracy()) {
                    locationproviderinterface = (LocationProviderInterface)arraylist1.get(0);
                } else {
                    ArrayList arraylist2 = new ArrayList();
                    int i1 = 0;
                    for(int j1 = arraylist1.size(); i1 < j1 && ((LocationProviderInterface)arraylist1.get(i1)).getAccuracy() == l; i1++)
                        arraylist2.add(arraylist1.get(i1));

                    Collections.sort(arraylist2, new LpCapabilityComparator());
                    locationproviderinterface = (LocationProviderInterface)arraylist2.get(0);
                }
            }
        }
        return locationproviderinterface;
    }

    private void checkMockPermissionsSafe() {
        int i = 1;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "mock_location", 0) != i)
            i = 0;
        if(i == 0)
            throw new SecurityException("Requires ACCESS_MOCK_LOCATION secure setting");
        if(mContext.checkCallingPermission("android.permission.ACCESS_MOCK_LOCATION") != 0)
            throw new SecurityException("Requires ACCESS_MOCK_LOCATION permission");
        else
            return;
    }

    private String checkPermissionsSafe(String s, String s1) {
        if(!"gps".equals(s) && !"passive".equals(s)) goto _L2; else goto _L1
_L1:
        if(mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0)
            throw new SecurityException((new StringBuilder()).append("Provider ").append(s).append(" requires ACCESS_FINE_LOCATION permission").toString());
        s1 = "android.permission.ACCESS_FINE_LOCATION";
_L4:
        return s1;
_L2:
        if(mContext.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
            if(!"android.permission.ACCESS_FINE_LOCATION".equals(s1))
                s1 = "android.permission.ACCESS_COARSE_LOCATION";
        } else
        if(mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0)
            s1 = "android.permission.ACCESS_FINE_LOCATION";
        else
            throw new SecurityException((new StringBuilder()).append("Provider ").append(s).append(" requires ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION permission").toString());
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void decrementPendingBroadcasts() {
        android.os.PowerManager.WakeLock wakelock = mWakeLock;
        wakelock;
        JVM INSTR monitorenter ;
        int i;
        i = -1 + mPendingBroadcasts;
        mPendingBroadcasts = i;
        if(i != 0) goto _L2; else goto _L1
_L1:
        if(!mWakeLock.isHeld()) goto _L4; else goto _L3
_L3:
        mWakeLock.release();
        log("Released wakelock");
_L2:
        wakelock;
        JVM INSTR monitorexit ;
        return;
_L4:
        Exception exception;
        try {
            log("Can't release wakelock again!");
        }
        catch(Exception exception1) {
            Slog.e("LocationManagerService", "exception in releaseWakeLock()", exception1);
        }
        finally {
            wakelock;
        }
        if(true) goto _L2; else goto _L5
_L5:
        JVM INSTR monitorexit ;
        throw exception;
    }

    private long getMinTimeLocked(String s) {
        long l = 0x7fffffffffffffffL;
        ArrayList arraylist = (ArrayList)mRecordsByProvider.get(s);
        mTmpWorkSource.clear();
        if(arraylist != null) {
            for(int i = -1 + arraylist.size(); i >= 0; i--) {
                long l2 = ((UpdateRecord)arraylist.get(i)).mMinTime;
                if(l2 < l)
                    l = l2;
            }

            long l1 = (3L * l) / 2L;
            for(int j = -1 + arraylist.size(); j >= 0; j--) {
                UpdateRecord updaterecord = (UpdateRecord)arraylist.get(j);
                if(updaterecord.mMinTime <= l1)
                    mTmpWorkSource.add(updaterecord.mUid);
            }

        }
        return l;
    }

    private Receiver getReceiver(PendingIntent pendingintent) {
        Receiver receiver = (Receiver)mReceivers.get(pendingintent);
        if(receiver == null) {
            receiver = new Receiver(pendingintent);
            mReceivers.put(pendingintent, receiver);
        }
        return receiver;
    }

    private Receiver getReceiver(ILocationListener ilocationlistener) {
        Receiver receiver;
        IBinder ibinder = ilocationlistener.asBinder();
        receiver = (Receiver)mReceivers.get(ibinder);
        if(receiver != null)
            break MISSING_BLOCK_LABEL_66;
        receiver = new Receiver(ilocationlistener);
        mReceivers.put(ibinder, receiver);
        if(receiver.isListener())
            receiver.getListener().asBinder().linkToDeath(receiver, 0);
        Receiver receiver1 = receiver;
_L2:
        return receiver1;
        RemoteException remoteexception;
        remoteexception;
        Slog.e("LocationManagerService", "linkToDeath failed:", remoteexception);
        receiver1 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void handleLocationChangedLocked(Location location, boolean flag) {
        String s;
        ArrayList arraylist;
        if(flag)
            s = "passive";
        else
            s = location.getProvider();
        arraylist = (ArrayList)mRecordsByProvider.get(s);
        if(arraylist != null && arraylist.size() != 0) goto _L2; else goto _L1
_L1:
        LocationProviderInterface locationproviderinterface;
        return;
_L2:
        if((locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s)) != null) {
            Location location1 = (Location)mLastKnownLocation.get(s);
            long l;
            Bundle bundle;
            int i;
            ArrayList arraylist1;
            int j;
            int k;
            if(location1 == null) {
                HashMap hashmap = mLastKnownLocation;
                Location location3 = new Location(location);
                hashmap.put(s, location3);
            } else {
                location1.set(location);
            }
            l = locationproviderinterface.getStatusUpdateTime();
            bundle = new Bundle();
            i = locationproviderinterface.getStatus(bundle);
            arraylist1 = null;
            j = arraylist.size();
            k = 0;
            while(k < j)  {
                UpdateRecord updaterecord = (UpdateRecord)arraylist.get(k);
                Receiver receiver = updaterecord.mReceiver;
                boolean flag1 = false;
                Location location2 = updaterecord.mLastFixBroadcast;
                if(location2 == null || shouldBroadcastSafe(location, location2, updaterecord)) {
                    long l1;
                    if(location2 == null)
                        updaterecord.mLastFixBroadcast = new Location(location);
                    else
                        location2.set(location);
                    if(!receiver.callLocationChangedLocked(location)) {
                        Slog.w("LocationManagerService", (new StringBuilder()).append("RemoteException calling onLocationChanged on ").append(receiver).toString());
                        flag1 = true;
                    }
                }
                l1 = updaterecord.mLastStatusBroadcast;
                if(l > l1 && (l1 != 0L || i != 2)) {
                    updaterecord.mLastStatusBroadcast = l;
                    if(!receiver.callStatusChangedLocked(s, i, bundle)) {
                        flag1 = true;
                        Slog.w("LocationManagerService", (new StringBuilder()).append("RemoteException calling onStatusChanged on ").append(receiver).toString());
                    }
                }
                if(!flag1 && !updaterecord.mSingleShot)
                    continue;
                if(arraylist1 == null)
                    arraylist1 = new ArrayList();
                if(!arraylist1.contains(receiver))
                    arraylist1.add(receiver);
                k++;
            }
            if(arraylist1 != null) {
                int i1 = -1 + arraylist1.size();
                while(i1 >= 0)  {
                    removeUpdatesLocked((Receiver)arraylist1.get(i1));
                    i1--;
                }
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void incrementPendingBroadcasts() {
        android.os.PowerManager.WakeLock wakelock = mWakeLock;
        wakelock;
        JVM INSTR monitorenter ;
        int i;
        i = mPendingBroadcasts;
        mPendingBroadcasts = i + 1;
        Exception exception;
        if(i == 0)
            try {
                mWakeLock.acquire();
                log("Acquired wakelock");
            }
            catch(Exception exception1) {
                Slog.e("LocationManagerService", "exception in acquireWakeLock()", exception1);
            }
            finally {
                wakelock;
            }
        wakelock;
        JVM INSTR monitorexit ;
        return;
        throw exception;
    }

    private void initialize() {
        mWakeLock = ((PowerManager)mContext.getSystemService("power")).newWakeLock(1, "LocationManagerService");
        mPackageManager = mContext.getPackageManager();
        loadProviders();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentfilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        mContext.registerReceiver(mBroadcastReceiver, intentfilter1);
        ContentResolver contentresolver = mContext.getContentResolver();
        android.net.Uri uri = android.provider.Settings.Secure.CONTENT_URI;
        String as[] = new String[1];
        as[0] = "location_providers_allowed";
        mSettings = new ContentQueryMap(contentresolver.query(uri, null, "(name=?)", as, null), "name", true, mLocationHandler);
        SettingsObserver settingsobserver = new SettingsObserver();
        mSettings.addObserver(settingsobserver);
    }

    private boolean isAllowedBySettingsLocked(String s) {
        boolean flag;
        if(mEnabledProviders.contains(s))
            flag = true;
        else
        if(mDisabledProviders.contains(s))
            flag = false;
        else
            flag = android.provider.Settings.Secure.isLocationProviderEnabled(mContext.getContentResolver(), s);
        return flag;
    }

    private boolean isAllowedProviderSafe(String s) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
        if((!"gps".equals(s) && !"passive".equals(s) || mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) && (!"network".equals(s) || mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || mContext.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0))
            flag = true;
        return flag;
    }

    private void loadProviders() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if(!sProvidersLoaded) {
            loadProvidersLocked();
            sProvidersLoaded = true;
        }
        return;
    }

    private void loadProvidersLocked() {
        _loadProvidersLocked();
_L1:
        return;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "Exception loading providers:", exception);
          goto _L1
    }

    private void log(String s) {
        if(Log.isLoggable("LocationManagerService", 2))
            Slog.d("LocationManagerService", s);
    }

    private int nextAccuracy(int i) {
        byte byte0;
        if(i == 1)
            byte0 = 2;
        else
            byte0 = 0;
        return byte0;
    }

    private int nextPower(int i) {
        int j = 0;
        i;
        JVM INSTR tableswitch 1 3: default 28
    //                   1 30
    //                   2 35
    //                   3 28;
           goto _L1 _L2 _L3 _L1
_L1:
        return j;
_L2:
        j = 2;
        continue; /* Loop/switch isn't completed */
_L3:
        j = 3;
        if(true) goto _L1; else goto _L4
_L4:
    }

    private boolean providerHasListener(String s, int i, Receiver receiver) {
        boolean flag;
        ArrayList arraylist;
        flag = true;
        arraylist = (ArrayList)mRecordsByProvider.get(s);
        if(arraylist == null) goto _L2; else goto _L1
_L1:
        int j = -1 + arraylist.size();
_L6:
        if(j < 0) goto _L2; else goto _L3
_L3:
        UpdateRecord updaterecord = (UpdateRecord)arraylist.get(j);
        if(updaterecord.mUid != i || updaterecord.mReceiver == receiver) goto _L5; else goto _L4
_L4:
        return flag;
_L5:
        j--;
          goto _L6
_L2:
        Iterator iterator = mProximityAlerts.values().iterator();
_L9:
        if(!iterator.hasNext()) goto _L8; else goto _L7
_L7:
        if(((ProximityAlert)iterator.next()).mUid != i) goto _L9; else goto _L4
_L8:
        flag = false;
          goto _L4
    }

    private void removeProvider(LocationProviderInterface locationproviderinterface) {
        mProviders.remove(locationproviderinterface);
        mProvidersByName.remove(locationproviderinterface.getName());
    }

    private void removeProximityAlertLocked(PendingIntent pendingintent) {
        mProximityAlerts.remove(pendingintent);
        if(mProximityAlerts.size() == 0) {
            if(mProximityReceiver != null)
                removeUpdatesLocked(mProximityReceiver);
            mProximityReceiver = null;
            mProximityListener = null;
        }
    }

    private void removeUpdatesLocked(Receiver receiver) {
        int i;
        int j;
        long l;
        i = Binder.getCallingPid();
        j = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        if(mReceivers.remove(receiver.mKey) == null || !receiver.isListener()) goto _L2; else goto _L1
_L1:
        receiver.getListener().asBinder().unlinkToDeath(receiver, 0);
        receiver;
        JVM INSTR monitorenter ;
        if(receiver.mPendingBroadcasts > 0) {
            decrementPendingBroadcasts();
            receiver.mPendingBroadcasts = 0;
        }
        receiver;
        JVM INSTR monitorexit ;
_L2:
        HashSet hashset;
        HashMap hashmap;
        hashset = new HashSet();
        hashmap = receiver.mUpdateRecords;
        if(hashmap == null)
            break MISSING_BLOCK_LABEL_205;
        UpdateRecord updaterecord;
        for(Iterator iterator1 = hashmap.values().iterator(); iterator1.hasNext(); updaterecord.disposeLocked()) {
            updaterecord = (UpdateRecord)iterator1.next();
            if(!providerHasListener(updaterecord.mProvider, j, receiver)) {
                LocationProviderInterface locationproviderinterface1 = (LocationProviderInterface)mProvidersByName.get(updaterecord.mProvider);
                if(locationproviderinterface1 != null)
                    locationproviderinterface1.removeListener(j);
            }
        }

        break MISSING_BLOCK_LABEL_194;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        Exception exception1;
        exception1;
        receiver;
        JVM INSTR monitorexit ;
        throw exception1;
        hashset.addAll(hashmap.keySet());
        Iterator iterator = hashset.iterator();
        do {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(isAllowedBySettingsLocked(s)) {
                boolean flag = false;
                ArrayList arraylist = (ArrayList)mRecordsByProvider.get(s);
                if(arraylist != null && arraylist.size() > 0)
                    flag = true;
                LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
                if(locationproviderinterface != null)
                    if(flag) {
                        long l1 = getMinTimeLocked(s);
                        Slog.i("LocationManagerService", (new StringBuilder()).append("remove ").append(s).append(" (pid ").append(i).append("), next minTime = ").append(l1).toString());
                        locationproviderinterface.setMinTime(l1, mTmpWorkSource);
                    } else {
                        Slog.i("LocationManagerService", (new StringBuilder()).append("remove ").append(s).append(" (pid ").append(i).append("), disabled").toString());
                        locationproviderinterface.enableLocationTracking(false);
                    }
            }
        } while(true);
        Binder.restoreCallingIdentity(l);
        return;
    }

    private void requestLocationUpdatesLocked(String s, long l, float f, boolean flag, Receiver receiver) {
        long l1;
        String s1;
        LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
        if(locationproviderinterface == null)
            throw new IllegalArgumentException((new StringBuilder()).append("requested provider ").append(s).append(" doesn't exisit").toString());
        receiver.mRequiredPermissions = checkPermissionsSafe(s, receiver.mRequiredPermissions);
        int i = Binder.getCallingPid();
        int j = Binder.getCallingUid();
        boolean flag1;
        UpdateRecord updaterecord;
        UpdateRecord updaterecord1;
        long l2;
        StringBuilder stringbuilder;
        if(!providerHasListener(s, j, null))
            flag1 = true;
        else
            flag1 = false;
        l1 = Binder.clearCallingIdentity();
        updaterecord = new UpdateRecord(s, l, f, flag, receiver, j);
        updaterecord1 = (UpdateRecord)receiver.mUpdateRecords.put(s, updaterecord);
        if(updaterecord1 != null)
            updaterecord1.disposeLocked();
        if(flag1)
            locationproviderinterface.addListener(j);
        if(!isAllowedBySettingsLocked(s)) goto _L2; else goto _L1
_L1:
        l2 = getMinTimeLocked(s);
        stringbuilder = (new StringBuilder()).append("request ").append(s).append(" (pid ").append(i).append(") ").append(l).append(" ").append(l2);
        if(!flag) goto _L4; else goto _L3
_L3:
        s1 = " (singleshot)";
_L5:
        Slog.i("LocationManagerService", stringbuilder.append(s1).toString());
        locationproviderinterface.setMinTime(l2, mTmpWorkSource);
        if(!flag || !locationproviderinterface.requestSingleShotFix())
            locationproviderinterface.enableLocationTracking(true);
_L6:
        Binder.restoreCallingIdentity(l1);
        return;
_L4:
        s1 = "";
          goto _L5
_L2:
        receiver.callProviderEnabledLocked(s, false);
          goto _L6
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l1);
        throw exception;
          goto _L5
    }

    private static boolean shouldBroadcastSafe(Location location, Location location1, UpdateRecord updaterecord) {
        boolean flag = true;
        if(location1 != null) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        long l = updaterecord.mMinTime;
        if(location.getTime() - location1.getTime() < l - 100L) {
            flag = false;
        } else {
            double d = updaterecord.mMinDistance;
            if(d > 0.0D && (double)location.distanceTo(location1) <= d)
                flag = false;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void updateProviderListenersLocked(String s, boolean flag) {
        int i;
        LocationProviderInterface locationproviderinterface;
        i = 0;
        locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
        if(locationproviderinterface != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ArrayList arraylist = null;
        ArrayList arraylist1 = (ArrayList)mRecordsByProvider.get(s);
        if(arraylist1 != null) {
            int k = arraylist1.size();
            for(int l = 0; l < k; l++) {
                UpdateRecord updaterecord = (UpdateRecord)arraylist1.get(l);
                if(!updaterecord.mReceiver.callProviderEnabledLocked(s, flag)) {
                    if(arraylist == null)
                        arraylist = new ArrayList();
                    arraylist.add(updaterecord.mReceiver);
                }
                i++;
            }

        }
        if(arraylist != null) {
            for(int j = -1 + arraylist.size(); j >= 0; j--)
                removeUpdatesLocked((Receiver)arraylist.get(j));

        }
        if(flag) {
            locationproviderinterface.enable();
            if(i > 0) {
                locationproviderinterface.setMinTime(getMinTimeLocked(s), mTmpWorkSource);
                locationproviderinterface.enableLocationTracking(true);
            }
        } else {
            locationproviderinterface.enableLocationTracking(false);
            locationproviderinterface.disable();
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void updateProvidersLocked() {
        boolean flag = false;
        int i = -1 + mProviders.size();
        while(i >= 0)  {
            LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProviders.get(i);
            boolean flag1 = locationproviderinterface.isEnabled();
            String s = locationproviderinterface.getName();
            boolean flag2 = isAllowedBySettingsLocked(s);
            if(flag1 && !flag2) {
                updateProviderListenersLocked(s, false);
                flag = true;
            } else
            if(!flag1 && flag2) {
                updateProviderListenersLocked(s, true);
                flag = true;
            }
            i--;
        }
        if(flag)
            mContext.sendBroadcast(new Intent("android.location.PROVIDERS_CHANGED"));
    }

    public boolean addGpsStatusListener(IGpsStatusListener igpsstatuslistener) {
        boolean flag = false;
        if(mGpsStatusProvider != null) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        if(mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0)
            throw new SecurityException("Requires ACCESS_FINE_LOCATION permission");
        mGpsStatusProvider.addGpsStatusListener(igpsstatuslistener);
        flag = true;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        Slog.e("LocationManagerService", "mGpsStatusProvider.addGpsStatusListener failed", remoteexception);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void addProximityAlert(double d, double d1, float f, long l, 
            PendingIntent pendingintent) {
        validatePendingIntent(pendingintent);
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        addProximityAlertLocked(d, d1, f, l, pendingintent);
        break MISSING_BLOCK_LABEL_64;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "addProximityAlert got exception:", exception);
    }

    public void addTestProvider(String s, boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, boolean flag5, 
            boolean flag6, int i, int j) {
        long l;
        checkMockPermissionsSafe();
        if("passive".equals(s))
            throw new IllegalArgumentException("Cannot mock the passive location provider");
        l = Binder.clearCallingIdentity();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = new MockProvider(s, this, flag, flag1, flag2, flag3, flag4, flag5, flag6, i, j);
        if("gps".equals(s) || "network".equals(s)) {
            LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
            if(locationproviderinterface != null) {
                locationproviderinterface.enableLocationTracking(false);
                removeProvider(locationproviderinterface);
            }
        }
        if(mProvidersByName.get(s) != null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" already exists").toString());
        break MISSING_BLOCK_LABEL_171;
        Exception exception;
        exception;
        throw exception;
        addProvider(mockprovider);
        mMockProviders.put(s, mockprovider);
        mLastKnownLocation.put(s, null);
        updateProvidersLocked();
        obj;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        return;
    }

    public void clearTestProviderEnabled(String s) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        if((MockProvider)mMockProviders.get(s) == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_64;
        Exception exception;
        exception;
        throw exception;
        long l = Binder.clearCallingIdentity();
        mEnabledProviders.remove(s);
        mDisabledProviders.remove(s);
        updateProvidersLocked();
        Binder.restoreCallingIdentity(l);
        obj;
        JVM INSTR monitorexit ;
    }

    public void clearTestProviderLocation(String s) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_68;
        Exception exception;
        exception;
        throw exception;
        mockprovider.clearLocation();
        obj;
        JVM INSTR monitorexit ;
    }

    public void clearTestProviderStatus(String s) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_68;
        Exception exception;
        exception;
        throw exception;
        mockprovider.clearStatus();
        obj;
        JVM INSTR monitorexit ;
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump LocationManagerService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L3:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("Current Location Manager state:");
        printwriter.println((new StringBuilder()).append("  sProvidersLoaded=").append(sProvidersLoaded).toString());
        printwriter.println("  Listeners:");
        int i = mReceivers.size();
        for(int j = 0; j < i; j++)
            printwriter.println((new StringBuilder()).append("    ").append(mReceivers.get(Integer.valueOf(j))).toString());

        printwriter.println("  Location Listeners:");
        for(Iterator iterator = mReceivers.values().iterator(); iterator.hasNext();) {
            Receiver receiver = (Receiver)iterator.next();
            printwriter.println((new StringBuilder()).append("    ").append(receiver).append(":").toString());
            Iterator iterator10 = receiver.mUpdateRecords.entrySet().iterator();
            while(iterator10.hasNext())  {
                java.util.Map.Entry entry3 = (java.util.Map.Entry)iterator10.next();
                printwriter.println((new StringBuilder()).append("      ").append((String)entry3.getKey()).append(":").toString());
                ((UpdateRecord)entry3.getValue()).dump(printwriter, "        ");
            }
        }

        break MISSING_BLOCK_LABEL_339;
        Exception exception;
        exception;
        throw exception;
        printwriter.println("  Records by Provider:");
        for(Iterator iterator1 = mRecordsByProvider.entrySet().iterator(); iterator1.hasNext();) {
            java.util.Map.Entry entry2 = (java.util.Map.Entry)iterator1.next();
            printwriter.println((new StringBuilder()).append("    ").append((String)entry2.getKey()).append(":").toString());
            Iterator iterator9 = ((ArrayList)entry2.getValue()).iterator();
            while(iterator9.hasNext())  {
                UpdateRecord updaterecord = (UpdateRecord)iterator9.next();
                printwriter.println((new StringBuilder()).append("      ").append(updaterecord).append(":").toString());
                updaterecord.dump(printwriter, "        ");
            }
        }

        printwriter.println("  Last Known Locations:");
        java.util.Map.Entry entry1;
        for(Iterator iterator2 = mLastKnownLocation.entrySet().iterator(); iterator2.hasNext(); ((Location)entry1.getValue()).dump(new PrintWriterPrinter(printwriter), "      ")) {
            entry1 = (java.util.Map.Entry)iterator2.next();
            printwriter.println((new StringBuilder()).append("    ").append((String)entry1.getKey()).append(":").toString());
        }

        if(mProximityAlerts.size() > 0) {
            printwriter.println("  Proximity Alerts:");
            java.util.Map.Entry entry;
            for(Iterator iterator8 = mProximityAlerts.entrySet().iterator(); iterator8.hasNext(); ((ProximityAlert)entry.getValue()).dump(printwriter, "      ")) {
                entry = (java.util.Map.Entry)iterator8.next();
                printwriter.println((new StringBuilder()).append("    ").append(entry.getKey()).append(":").toString());
            }

        }
        if(mProximitiesEntered.size() > 0) {
            printwriter.println("  Proximities Entered:");
            ProximityAlert proximityalert;
            for(Iterator iterator7 = mProximitiesEntered.iterator(); iterator7.hasNext(); proximityalert.dump(printwriter, "      ")) {
                proximityalert = (ProximityAlert)iterator7.next();
                printwriter.println((new StringBuilder()).append("    ").append(proximityalert).append(":").toString());
            }

        }
        printwriter.println((new StringBuilder()).append("  mProximityReceiver=").append(mProximityReceiver).toString());
        printwriter.println((new StringBuilder()).append("  mProximityListener=").append(mProximityListener).toString());
        if(mEnabledProviders.size() > 0) {
            printwriter.println("  Enabled Providers:");
            String s2;
            for(Iterator iterator6 = mEnabledProviders.iterator(); iterator6.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s2).toString()))
                s2 = (String)iterator6.next();

        }
        if(mDisabledProviders.size() > 0) {
            printwriter.println("  Disabled Providers:");
            String s1;
            for(Iterator iterator5 = mDisabledProviders.iterator(); iterator5.hasNext(); printwriter.println((new StringBuilder()).append("    ").append(s1).toString()))
                s1 = (String)iterator5.next();

        }
        if(mMockProviders.size() > 0) {
            printwriter.println("  Mock Providers:");
            for(Iterator iterator4 = mMockProviders.entrySet().iterator(); iterator4.hasNext(); ((MockProvider)((java.util.Map.Entry)iterator4.next()).getValue()).dump(printwriter, "      "));
        }
        Iterator iterator3 = mProviders.iterator();
        do {
            if(!iterator3.hasNext())
                break;
            LocationProviderInterface locationproviderinterface = (LocationProviderInterface)iterator3.next();
            String s = locationproviderinterface.getInternalState();
            if(s != null) {
                printwriter.println((new StringBuilder()).append(locationproviderinterface.getName()).append(" Internal State:").toString());
                printwriter.write(s);
            }
        } while(true);
        obj;
        JVM INSTR monitorexit ;
          goto _L3
    }

    String findBestPackage(String s, String s1) {
        Intent intent = new Intent(s);
        List list = mPackageManager.queryIntentServices(intent, 128);
        String s2;
        if(list == null) {
            s2 = null;
        } else {
            int i = 0x80000000;
            s2 = null;
            Iterator iterator = list.iterator();
            while(iterator.hasNext())  {
                ResolveInfo resolveinfo = (ResolveInfo)iterator.next();
                String s3 = ((ComponentInfo) (resolveinfo.serviceInfo)).packageName;
                if(mPackageManager.checkSignatures(s3, s1) != 0) {
                    Slog.w("LocationManagerService", (new StringBuilder()).append(s3).append(" implements ").append(s).append(" but its signatures don't match those in ").append(s1).append(", ignoring").toString());
                } else {
                    int j = 0;
                    if(resolveinfo.serviceInfo.metaData != null)
                        j = resolveinfo.serviceInfo.metaData.getInt("version", 0);
                    if(j > i) {
                        i = j;
                        s2 = s3;
                    }
                }
            }
        }
        return s2;
    }

    public boolean geocoderIsPresent() {
        boolean flag;
        if(mGeocodeProvider != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public List getAllProviders() {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        List list;
        list = _getAllProvidersLocked();
        break MISSING_BLOCK_LABEL_46;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "getAllProviders got exception:", exception);
        list = null;
        return list;
    }

    public String getBestProvider(Criteria criteria, boolean flag) {
        List list = getProviders(criteria, flag);
        String s;
        if(!list.isEmpty()) {
            s = best(list).getName();
        } else {
            Criteria criteria1 = new Criteria(criteria);
            for(int i = criteria1.getPowerRequirement(); list.isEmpty() && i != 0; list = getProviders(criteria1, flag)) {
                i = nextPower(i);
                criteria1.setPowerRequirement(i);
            }

            if(!list.isEmpty()) {
                s = best(list).getName();
            } else {
                for(int j = criteria1.getAccuracy(); list.isEmpty() && j != 0; list = getProviders(criteria1, flag)) {
                    j = nextAccuracy(j);
                    criteria1.setAccuracy(j);
                }

                if(!list.isEmpty()) {
                    s = best(list).getName();
                } else {
                    criteria1.setBearingRequired(false);
                    List list1 = getProviders(criteria1, flag);
                    if(!list1.isEmpty()) {
                        s = best(list1).getName();
                    } else {
                        criteria1.setSpeedRequired(false);
                        List list2 = getProviders(criteria1, flag);
                        if(!list2.isEmpty()) {
                            s = best(list2).getName();
                        } else {
                            criteria1.setAltitudeRequired(false);
                            List list3 = getProviders(criteria1, flag);
                            if(!list3.isEmpty())
                                s = best(list3).getName();
                            else
                                s = null;
                        }
                    }
                }
            }
        }
        return s;
    }

    public String getFromLocation(double d, double d1, int i, GeocoderParams geocoderparams, List list) {
        String s;
        if(mGeocodeProvider != null)
            s = mGeocodeProvider.getFromLocation(d, d1, i, geocoderparams, list);
        else
            s = null;
        return s;
    }

    public String getFromLocationName(String s, double d, double d1, double d2, 
            double d3, int i, GeocoderParams geocoderparams, List list) {
        String s1;
        if(mGeocodeProvider != null)
            s1 = mGeocodeProvider.getFromLocationName(s, d, d1, d2, d3, i, geocoderparams, list);
        else
            s1 = null;
        return s1;
    }

    public Location getLastKnownLocation(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Location location;
        location = _getLastKnownLocationLocked(s);
        break MISSING_BLOCK_LABEL_49;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "getLastKnownLocation got exception:", exception);
        location = null;
        return location;
    }

    public Bundle getProviderInfo(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Bundle bundle;
        bundle = _getProviderInfoLocked(s);
        break MISSING_BLOCK_LABEL_54;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "_getProviderInfo got exception:", exception);
        bundle = null;
        return bundle;
    }

    public List getProviders(Criteria criteria, boolean flag) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        List list;
        list = _getProvidersLocked(criteria, flag);
        break MISSING_BLOCK_LABEL_50;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "getProviders got exception:", exception);
        list = null;
        return list;
    }

    public boolean isProviderEnabled(String s) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag;
        flag = _isProviderEnabledLocked(s);
        break MISSING_BLOCK_LABEL_49;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "isProviderEnabled got exception:", exception);
        flag = false;
        return flag;
    }

    public void locationCallbackFinished(ILocationListener ilocationlistener) {
        Receiver receiver;
        IBinder ibinder = ilocationlistener.asBinder();
        receiver = (Receiver)mReceivers.get(ibinder);
        if(receiver == null)
            break MISSING_BLOCK_LABEL_51;
        receiver;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        receiver.decrementPendingBroadcastsLocked();
        Binder.restoreCallingIdentity(l);
    }

    public boolean providerMeetsCriteria(String s, Criteria criteria) {
        LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
        if(locationproviderinterface == null)
            throw new IllegalArgumentException((new StringBuilder()).append("provider=").append(s).toString());
        else
            return locationproviderinterface.meetsCriteria(criteria);
    }

    public void removeGpsStatusListener(IGpsStatusListener igpsstatuslistener) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Exception exception;
        try {
            mGpsStatusProvider.removeGpsStatusListener(igpsstatuslistener);
        }
        catch(Exception exception1) {
            Slog.e("LocationManagerService", "mGpsStatusProvider.removeGpsStatusListener failed", exception1);
        }
        finally {
            obj;
        }
        return;
        throw exception;
    }

    public void removeProximityAlert(PendingIntent pendingintent) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        removeProximityAlertLocked(pendingintent);
        break MISSING_BLOCK_LABEL_49;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "removeProximityAlert got exception:", exception);
    }

    public void removeTestProvider(String s) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_68;
        Exception exception;
        exception;
        throw exception;
        long l;
        l = Binder.clearCallingIdentity();
        removeProvider((LocationProviderInterface)mProvidersByName.get(s));
        mMockProviders.remove(mockprovider);
        if(!"gps".equals(s) || mGpsLocationProvider == null) goto _L2; else goto _L1
_L1:
        addProvider(mGpsLocationProvider);
_L4:
        mLastKnownLocation.put(s, null);
        updateProvidersLocked();
        Binder.restoreCallingIdentity(l);
        obj;
        JVM INSTR monitorexit ;
        return;
_L2:
        if("network".equals(s) && mNetworkLocationProvider != null)
            addProvider(mNetworkLocationProvider);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void removeUpdates(ILocationListener ilocationlistener) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        removeUpdatesLocked(getReceiver(ilocationlistener));
        break MISSING_BLOCK_LABEL_53;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "removeUpdates got exception:", exception);
    }

    public void removeUpdatesPI(PendingIntent pendingintent) {
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        removeUpdatesLocked(getReceiver(pendingintent));
        break MISSING_BLOCK_LABEL_53;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "removeUpdates got exception:", exception);
    }

    public void reportLocation(Location location, boolean flag) {
        int i = 1;
        if(mContext.checkCallingOrSelfPermission("android.permission.INSTALL_LOCATION_PROVIDER") != 0)
            throw new SecurityException("Requires INSTALL_LOCATION_PROVIDER permission");
        mLocationHandler.removeMessages(i, location);
        Message message = Message.obtain(mLocationHandler, i, location);
        if(!flag)
            i = 0;
        message.arg1 = i;
        mLocationHandler.sendMessageAtFrontOfQueue(message);
    }

    public void requestLocationUpdates(String s, Criteria criteria, long l, float f, boolean flag, ILocationListener ilocationlistener) {
        if(criteria != null) {
            s = getBestProvider(criteria, true);
            if(s == null)
                throw new IllegalArgumentException("no providers found for criteria");
        }
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Receiver receiver = getReceiver(ilocationlistener);
        requestLocationUpdatesLocked(s, l, f, flag, receiver);
        break MISSING_BLOCK_LABEL_92;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "requestUpdates got exception:", exception);
    }

    public void requestLocationUpdatesPI(String s, Criteria criteria, long l, float f, boolean flag, PendingIntent pendingintent) {
        validatePendingIntent(pendingintent);
        if(criteria != null) {
            s = getBestProvider(criteria, true);
            if(s == null)
                throw new IllegalArgumentException("no providers found for criteria");
        }
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Receiver receiver = getReceiver(pendingintent);
        requestLocationUpdatesLocked(s, l, f, flag, receiver);
        break MISSING_BLOCK_LABEL_98;
        SecurityException securityexception;
        securityexception;
        throw securityexception;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        throw illegalargumentexception;
        Exception exception;
        exception;
        Slog.e("LocationManagerService", "requestUpdates got exception:", exception);
    }

    public void run() {
        Process.setThreadPriority(10);
        Looper.prepare();
        mLocationHandler = new LocationWorkerHandler();
        initialize();
        Looper.loop();
    }

    public boolean sendExtraCommand(String s, String s1, Bundle bundle) {
        if(s == null)
            throw new NullPointerException();
        checkPermissionsSafe(s, null);
        if(mContext.checkCallingOrSelfPermission("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS") != 0)
            throw new SecurityException("Requires ACCESS_LOCATION_EXTRA_COMMANDS permission");
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        LocationProviderInterface locationproviderinterface = (LocationProviderInterface)mProvidersByName.get(s);
        boolean flag;
        if(locationproviderinterface == null)
            flag = false;
        else
            flag = locationproviderinterface.sendExtraCommand(s1, bundle);
        return flag;
    }

    public boolean sendNiResponse(int i, int j) {
        if(Binder.getCallingUid() != Process.myUid())
            throw new SecurityException("calling sendNiResponse from outside of the system is not allowed");
        boolean flag1 = mNetInitiatedListener.sendNiResponse(i, j);
        boolean flag = flag1;
_L2:
        return flag;
        RemoteException remoteexception;
        remoteexception;
        Slog.e("LocationManagerService", "RemoteException in LocationManagerService.sendNiResponse");
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void setTestProviderEnabled(String s, boolean flag) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_70;
        Exception exception;
        exception;
        throw exception;
        long l = Binder.clearCallingIdentity();
        if(flag) {
            mockprovider.enable();
            mEnabledProviders.add(s);
            mDisabledProviders.remove(s);
        } else {
            mockprovider.disable();
            mEnabledProviders.remove(s);
            mDisabledProviders.add(s);
        }
        updateProvidersLocked();
        Binder.restoreCallingIdentity(l);
        obj;
        JVM INSTR monitorexit ;
    }

    public void setTestProviderLocation(String s, Location location) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_70;
        Exception exception;
        exception;
        throw exception;
        long l = Binder.clearCallingIdentity();
        mockprovider.setLocation(location);
        Binder.restoreCallingIdentity(l);
        obj;
        JVM INSTR monitorexit ;
    }

    public void setTestProviderStatus(String s, int i, Bundle bundle, long l) {
        checkMockPermissionsSafe();
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        MockProvider mockprovider;
        mockprovider = (MockProvider)mMockProviders.get(s);
        if(mockprovider == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Provider \"").append(s).append("\" unknown").toString());
        break MISSING_BLOCK_LABEL_73;
        Exception exception;
        exception;
        throw exception;
        mockprovider.setStatus(i, bundle, l);
        obj;
        JVM INSTR monitorexit ;
    }

    void systemReady() {
        (new Thread(null, this, "LocationManagerService")).start();
    }

    void validatePendingIntent(PendingIntent pendingintent) {
        if(!pendingintent.isTargetedToPackage())
            Slog.i("LocationManagerService", (new StringBuilder()).append("Given Intent does not require a specific package: ").append(pendingintent).toString());
    }

    private static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final String ACCESS_LOCATION_EXTRA_COMMANDS = "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS";
    private static final String ACCESS_MOCK_LOCATION = "android.permission.ACCESS_MOCK_LOCATION";
    private static final String INSTALL_LOCATION_PROVIDER = "android.permission.INSTALL_LOCATION_PROVIDER";
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_PROVIDER_SCHEDULING_JITTER = 100;
    private static final int MESSAGE_LOCATION_CHANGED = 1;
    private static final int MESSAGE_PACKAGE_UPDATED = 2;
    private static final String TAG = "LocationManagerService";
    private static final String WAKELOCK_KEY = "LocationManagerService";
    private static boolean sProvidersLoaded = false;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            String s;
            boolean flag;
            s = intent.getAction();
            flag = s.equals("android.intent.action.QUERY_PACKAGE_RESTART");
            if(!flag && !s.equals("android.intent.action.PACKAGE_REMOVED") && !s.equals("android.intent.action.PACKAGE_RESTARTED") && !s.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) goto _L2; else goto _L1
_L1:
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            int ai1[];
            if(s.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                ai1 = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
            } else {
                int ai[] = new int[1];
                ai[0] = intent.getIntExtra("android.intent.extra.UID", -1);
                ai1 = ai;
            }
            if(ai1 != null && ai1.length != 0) goto _L4; else goto _L3
_L4:
            int ai2[];
            int i;
            int j;
            ai2 = ai1;
            i = ai2.length;
            j = 0;
_L24:
            if(j >= i) goto _L3; else goto _L5
_L5:
            int k = ai2[j];
            if(k < 0) goto _L7; else goto _L6
_L6:
            ArrayList arraylist;
            Iterator iterator;
            arraylist = null;
            iterator = mRecordsByProvider.values().iterator();
_L11:
            if(!iterator.hasNext()) goto _L9; else goto _L8
_L8:
            ArrayList arraylist2;
            int j1;
            arraylist2 = (ArrayList)iterator.next();
            j1 = -1 + arraylist2.size();
_L22:
            if(j1 < 0) goto _L11; else goto _L10
_L10:
            UpdateRecord updaterecord = (UpdateRecord)arraylist2.get(j1);
            if(!updaterecord.mReceiver.isPendingIntent() || updaterecord.mUid != k) goto _L13; else goto _L12
_L12:
            if(!flag) goto _L15; else goto _L14
_L14:
            setResultCode(-1);
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L15:
            if(arraylist != null)
                break MISSING_BLOCK_LABEL_275;
            arraylist = new ArrayList();
            Receiver receiver = updaterecord.mReceiver;
            if(!arraylist.contains(receiver)) {
                Receiver receiver1 = updaterecord.mReceiver;
                arraylist.add(receiver1);
            }
              goto _L13
_L9:
            ArrayList arraylist1;
            Iterator iterator1;
            arraylist1 = null;
            iterator1 = mProximityAlerts.values().iterator();
_L19:
            ProximityAlert proximityalert;
            if(!iterator1.hasNext())
                break; /* Loop/switch isn't completed */
            proximityalert = (ProximityAlert)iterator1.next();
            if(proximityalert.mUid != k)
                continue; /* Loop/switch isn't completed */
            if(!flag) goto _L17; else goto _L16
_L16:
            setResultCode(-1);
            obj;
            JVM INSTR monitorexit ;
              goto _L3
_L17:
            if(arraylist1 == null)
                arraylist1 = new ArrayList();
            if(!arraylist1.contains(proximityalert))
                arraylist1.add(proximityalert);
            if(true) goto _L19; else goto _L18
_L18:
            if(arraylist != null) {
                for(int i1 = -1 + arraylist.size(); i1 >= 0; i1--)
                    removeUpdatesLocked((Receiver)arraylist.get(i1));

            }
            if(arraylist1 != null) {
                for(int l = -1 + arraylist1.size(); l >= 0; l--)
                    removeProximityAlertLocked(((ProximityAlert)arraylist1.get(l)).mIntent);

            }
              goto _L7
_L2:
            if(!s.equals("android.net.conn.CONNECTIVITY_CHANGE")) goto _L3; else goto _L20
_L20:
            Object obj1;
            android.net.NetworkInfo networkinfo;
            int k1;
            LocationProviderInterface locationproviderinterface;
            if(!intent.getBooleanExtra("noConnectivity", false))
                mNetworkState = 2;
            else
                mNetworkState = 1;
            networkinfo = ((ConnectivityManager)context1.getSystemService("connectivity")).getActiveNetworkInfo();
            obj1 = mLock;
            obj1;
            JVM INSTR monitorenter ;
            k1 = -1 + mProviders.size();
_L21:
            if(k1 < 0)
                break MISSING_BLOCK_LABEL_644;
            locationproviderinterface = (LocationProviderInterface)mProviders.get(k1);
            if(locationproviderinterface.requiresNetwork())
                locationproviderinterface.updateNetworkState(mNetworkState, networkinfo);
            k1--;
              goto _L21
            obj1;
            JVM INSTR monitorexit ;
              goto _L3
            Exception exception1;
            exception1;
            throw exception1;
_L3:
            return;
_L13:
            j1--;
              goto _L22
_L7:
            j++;
            if(true) goto _L24; else goto _L23
_L23:
        }

        final LocationManagerService this$0;

             {
                this$0 = LocationManagerService.this;
                super();
            }
    };
    private final Context mContext;
    private final Set mDisabledProviders = new HashSet();
    private final Set mEnabledProviders = new HashSet();
    private GeocoderProxy mGeocodeProvider;
    private String mGeocodeProviderPackageName;
    LocationProviderInterface mGpsLocationProvider;
    private IGpsStatusProvider mGpsStatusProvider;
    private HashMap mLastKnownLocation;
    private HashMap mLastWriteTime;
    private LocationWorkerHandler mLocationHandler;
    private final Object mLock = new Object();
    private final HashMap mMockProviders = new HashMap();
    private INetInitiatedListener mNetInitiatedListener;
    LocationProviderProxy mNetworkLocationProvider;
    private String mNetworkLocationProviderPackageName;
    private int mNetworkState;
    private PackageManager mPackageManager;
    private final PackageMonitor mPackageMonitor = new PackageMonitor() {

        public void onPackageAdded(String s, int i) {
            Message.obtain(mLocationHandler, 2, s).sendToTarget();
        }

        public void onPackageUpdateFinished(String s, int i) {
            Message.obtain(mLocationHandler, 2, s).sendToTarget();
        }

        final LocationManagerService this$0;

             {
                this$0 = LocationManagerService.this;
                super();
            }
    };
    private int mPendingBroadcasts;
    private final ArrayList mProviders = new ArrayList();
    private final HashMap mProvidersByName = new HashMap();
    private HashSet mProximitiesEntered;
    private HashMap mProximityAlerts;
    private ILocationListener mProximityListener;
    private Receiver mProximityReceiver;
    private final HashMap mReceivers = new HashMap();
    private final HashMap mRecordsByProvider = new HashMap();
    private ContentQueryMap mSettings;
    private final WorkSource mTmpWorkSource = new WorkSource();
    private android.os.PowerManager.WakeLock mWakeLock;














/*
    static String access$2102(LocationManagerService locationmanagerservice, String s) {
        locationmanagerservice.mNetworkLocationProviderPackageName = s;
        return s;
    }

*/




/*
    static String access$2302(LocationManagerService locationmanagerservice, String s) {
        locationmanagerservice.mGeocodeProviderPackageName = s;
        return s;
    }

*/




/*
    static int access$2502(LocationManagerService locationmanagerservice, int i) {
        locationmanagerservice.mNetworkState = i;
        return i;
    }

*/





}
