// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.util.SparseArray;
import java.util.HashMap;

public class ProcessMap {

    public ProcessMap() {
    }

    public Object get(String s, int i) {
        SparseArray sparsearray = (SparseArray)mMap.get(s);
        Object obj;
        if(sparsearray == null)
            obj = null;
        else
            obj = sparsearray.get(i);
        return obj;
    }

    public HashMap getMap() {
        return mMap;
    }

    public Object put(String s, int i, Object obj) {
        SparseArray sparsearray = (SparseArray)mMap.get(s);
        if(sparsearray == null) {
            sparsearray = new SparseArray(2);
            mMap.put(s, sparsearray);
        }
        sparsearray.put(i, obj);
        return obj;
    }

    public void remove(String s, int i) {
        SparseArray sparsearray = (SparseArray)mMap.get(s);
        if(sparsearray != null) {
            sparsearray.remove(i);
            if(sparsearray.size() == 0)
                mMap.remove(s);
        }
    }

    final HashMap mMap = new HashMap();
}
