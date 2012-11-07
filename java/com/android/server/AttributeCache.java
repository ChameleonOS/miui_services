// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

public final class AttributeCache {
    public static final class Entry {

        public final TypedArray array;
        public final Context context;

        public Entry(Context context1, TypedArray typedarray) {
            context = context1;
            array = typedarray;
        }
    }

    public static final class Package {

        public final Context context;
        private final SparseArray mMap = new SparseArray();


        public Package(Context context1) {
            context = context1;
        }
    }

    static class Injector {

        static Package getPackage(WeakHashMap weakhashmap, String s) {
            WeakReference weakreference = (WeakReference)weakhashmap.get(s);
            Package package1;
            if(weakreference != null)
                package1 = (Package)weakreference.get();
            else
                package1 = null;
            return package1;
        }

        static void putPackage(WeakHashMap weakhashmap, String s, Package package1) {
            weakhashmap.put(s, new WeakReference(package1));
        }

        Injector() {
        }
    }


    public AttributeCache(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        if(sInstance == null)
            sInstance = new AttributeCache(context);
    }

    public static AttributeCache instance() {
        return sInstance;
    }

    public Entry get(String s, int i, int ai[]) {
        Entry entry = null;
        this;
        JVM INSTR monitorenter ;
        Package package1;
        HashMap hashmap;
        Entry entry1;
        package1 = Injector.getPackage(mPackages, s);
        hashmap = null;
        entry1 = null;
        if(package1 == null) goto _L2; else goto _L1
_L1:
        hashmap = (HashMap)package1.mMap.get(i);
        if(hashmap == null) goto _L4; else goto _L3
_L3:
        entry1 = (Entry)hashmap.get(ai);
        if(entry1 == null) goto _L4; else goto _L5
_L5:
        this;
        JVM INSTR monitorexit ;
        entry = entry1;
_L8:
        return entry;
_L2:
        Context context = mContext.createPackageContext(s, 0);
        if(context != null) goto _L7; else goto _L6
_L6:
        this;
        JVM INSTR monitorexit ;
          goto _L8
        Exception exception;
        exception;
        throw exception;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        this;
        JVM INSTR monitorexit ;
          goto _L8
_L7:
        package1 = new Package(context);
        Injector.putPackage(mPackages, s, package1);
_L4:
        entry1;
        if(hashmap == null) {
            hashmap = new HashMap();
            package1.mMap.put(i, hashmap);
        }
        Entry entry2 = new Entry(package1.context, package1.context.obtainStyledAttributes(i, ai));
        hashmap.put(ai, entry2);
        this;
        JVM INSTR monitorexit ;
        entry = entry2;
          goto _L8
_L9:
        this;
        JVM INSTR monitorexit ;
          goto _L8
        android.content.res.Resources.NotFoundException notfoundexception;
        notfoundexception;
          goto _L9
        android.content.res.Resources.NotFoundException notfoundexception1;
        notfoundexception1;
          goto _L9
    }

    public void removePackage(String s) {
        this;
        JVM INSTR monitorenter ;
        mPackages.remove(s);
        return;
    }

    public void updateConfiguration(Configuration configuration) {
        this;
        JVM INSTR monitorenter ;
        if((0xbfffff5f & mConfiguration.updateFrom(configuration)) != 0)
            mPackages.clear();
        return;
    }

    private static AttributeCache sInstance = null;
    private final Configuration mConfiguration = new Configuration();
    private final Context mContext;
    private final WeakHashMap mPackages = new WeakHashMap();

}
