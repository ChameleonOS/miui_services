// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.Context;
import android.location.Country;
import android.location.CountryListener;
import android.os.Handler;

public abstract class CountryDetectorBase {

    public CountryDetectorBase(Context context) {
        mContext = context;
    }

    public abstract Country detectCountry();

    protected void notifyListener(Country country) {
        if(mListener != null)
            mListener.onCountryDetected(country);
    }

    public void setCountryListener(CountryListener countrylistener) {
        mListener = countrylistener;
    }

    public abstract void stop();

    protected final Context mContext;
    protected Country mDetectedCountry;
    protected final Handler mHandler = new Handler();
    protected CountryListener mListener;
}
