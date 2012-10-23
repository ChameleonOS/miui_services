// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.location.*;
import android.os.*;
import android.util.Slog;
import com.android.server.location.ComprehensiveCountryDetector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;

public class CountryDetectorService extends android.location.ICountryDetector.Stub
    implements Runnable {
    private final class Receiver
        implements android.os.IBinder.DeathRecipient {

        public void binderDied() {
            removeListener(mKey);
        }

        public boolean equals(Object obj) {
            boolean flag;
            if(obj instanceof Receiver)
                flag = mKey.equals(((Receiver)obj).mKey);
            else
                flag = false;
            return flag;
        }

        public ICountryListener getListener() {
            return mListener;
        }

        public int hashCode() {
            return mKey.hashCode();
        }

        private final IBinder mKey;
        private final ICountryListener mListener;
        final CountryDetectorService this$0;

        public Receiver(ICountryListener icountrylistener) {
            this$0 = CountryDetectorService.this;
            super();
            mListener = icountrylistener;
            mKey = icountrylistener.asBinder();
        }
    }


    public CountryDetectorService(Context context) {
        mContext = context;
    }

    private void addListener(ICountryListener icountrylistener) {
        HashMap hashmap = mReceivers;
        hashmap;
        JVM INSTR monitorenter ;
        Receiver receiver = new Receiver(icountrylistener);
        try {
            icountrylistener.asBinder().linkToDeath(receiver, 0);
            mReceivers.put(icountrylistener.asBinder(), receiver);
            if(mReceivers.size() == 1) {
                Slog.d("CountryDetector", "The first listener is added");
                setCountryListener(mLocationBasedDetectorListener);
            }
        }
        catch(RemoteException remoteexception) {
            Slog.e("CountryDetector", "linkToDeath failed:", remoteexception);
        }
        hashmap;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private void initialize() {
        mCountryDetector = new ComprehensiveCountryDetector(mContext);
        mLocationBasedDetectorListener = new CountryListener() {

            public void onCountryDetected(final Country country) {
                mHandler.post(new Runnable() {

                    public void run() {
                        notifyReceivers(country);
                    }

                    final _cls1 this$1;
                    final Country val$country;

                     {
                        this$1 = _cls1.this;
                        country = country1;
                        super();
                    }
                });
            }

            final CountryDetectorService this$0;

             {
                this$0 = CountryDetectorService.this;
                super();
            }
        };
    }

    private void removeListener(IBinder ibinder) {
        HashMap hashmap = mReceivers;
        hashmap;
        JVM INSTR monitorenter ;
        mReceivers.remove(ibinder);
        if(mReceivers.isEmpty()) {
            setCountryListener(null);
            Slog.d("CountryDetector", "No listener is left");
        }
        return;
    }

    public void addCountryListener(ICountryListener icountrylistener) throws RemoteException {
        if(!mSystemReady) {
            throw new RemoteException();
        } else {
            addListener(icountrylistener);
            return;
        }
    }

    public Country detectCountry() throws RemoteException {
        if(!mSystemReady)
            throw new RemoteException();
        else
            return mCountryDetector.detectCountry();
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "CountryDetector");
    }

    boolean isSystemReady() {
        return mSystemReady;
    }

    protected void notifyReceivers(Country country) {
        HashMap hashmap = mReceivers;
        hashmap;
        JVM INSTR monitorenter ;
        Iterator iterator = mReceivers.values().iterator();
_L1:
        Receiver receiver;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_77;
        receiver = (Receiver)iterator.next();
        try {
            receiver.getListener().onCountryDetected(country);
        }
        catch(RemoteException remoteexception) {
            Slog.e("CountryDetector", "notifyReceivers failed:", remoteexception);
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
    }

    public void removeCountryListener(ICountryListener icountrylistener) throws RemoteException {
        if(!mSystemReady) {
            throw new RemoteException();
        } else {
            removeListener(icountrylistener.asBinder());
            return;
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        Looper.prepare();
        mHandler = new Handler();
        initialize();
        mSystemReady = true;
        Looper.loop();
    }

    protected void setCountryListener(final CountryListener listener) {
        mHandler.post(new Runnable() {

            public void run() {
                mCountryDetector.setCountryListener(listener);
            }

            final CountryDetectorService this$0;
            final CountryListener val$listener;

             {
                this$0 = CountryDetectorService.this;
                listener = countrylistener;
                super();
            }
        });
    }

    void systemReady() {
        (new Thread(this, "CountryDetectorService")).start();
    }

    private static final boolean DEBUG = false;
    private static final String TAG = "CountryDetector";
    private final Context mContext;
    private ComprehensiveCountryDetector mCountryDetector;
    private Handler mHandler;
    private CountryListener mLocationBasedDetectorListener;
    private final HashMap mReceivers = new HashMap();
    private boolean mSystemReady;



}
