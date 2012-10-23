// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.util.FloatMath;

public class TwilightCalculator {

    public TwilightCalculator() {
    }

    public void calculateTwilight(long l, double d, double d1) {
        float f = (float)(l - 0xdc6d62da00L) / 8.64E+07F;
        float f1 = 6.24006F + 0.01720197F * f;
        float f2 = 3.141593F + (1.796593F + (f1 + 0.0334196F * FloatMath.sin(f1) + 0.000349066F * FloatMath.sin(2.0F * f1) + 5.236E-06F * FloatMath.sin(3F * f1)));
        double d2 = -d1 / 360D;
        double d3 = d2 + (double)(0.0009F + (float)Math.round((double)(f - 0.0009F) - d2)) + (double)(0.0053F * FloatMath.sin(f1)) + (double)(-0.0069F * FloatMath.sin(2.0F * f2));
        double d4 = Math.asin(FloatMath.sin(f2) * FloatMath.sin(0.4092797F));
        double d5 = d * 0.01745329238474369D;
        double d6 = ((double)FloatMath.sin(-0.1047198F) - Math.sin(d5) * Math.sin(d4)) / (Math.cos(d5) * Math.cos(d4));
        if(d6 >= 1.0D) {
            mState = 1;
            mSunset = -1L;
            mSunrise = -1L;
        } else
        if(d6 <= -1D) {
            mState = 0;
            mSunset = -1L;
            mSunrise = -1L;
        } else {
            float f3 = (float)(Math.acos(d6) / 6.2831853071795862D);
            mSunset = 0xdc6d62da00L + Math.round(86400000D * (d3 + (double)f3));
            mSunrise = 0xdc6d62da00L + Math.round(86400000D * (d3 - (double)f3));
            if(mSunrise < l && mSunset > l)
                mState = 0;
            else
                mState = 1;
        }
    }

    private static final float ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.1047198F;
    private static final float C1 = 0.0334196F;
    private static final float C2 = 0.000349066F;
    private static final float C3 = 5.236E-06F;
    public static final int DAY = 0;
    private static final float DEGREES_TO_RADIANS = 0.01745329F;
    private static final float J0 = 0.0009F;
    public static final int NIGHT = 1;
    private static final float OBLIQUITY = 0.4092797F;
    private static final long UTC_2000 = 0xdc6d62da00L;
    public int mState;
    public long mSunrise;
    public long mSunset;
}
