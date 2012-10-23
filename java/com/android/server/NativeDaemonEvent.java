// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import com.google.android.collect.Lists;
import java.util.ArrayList;

public class NativeDaemonEvent {

    private NativeDaemonEvent(int i, int j, String s, String s1) {
        mCmdNumber = i;
        mCode = j;
        mMessage = s;
        mRawEvent = s1;
        mParsed = null;
    }

    public static String[] filterMessageList(NativeDaemonEvent anativedaemonevent[], int i) {
        ArrayList arraylist = Lists.newArrayList();
        int j = anativedaemonevent.length;
        for(int k = 0; k < j; k++) {
            NativeDaemonEvent nativedaemonevent = anativedaemonevent[k];
            if(nativedaemonevent.getCode() == i)
                arraylist.add(nativedaemonevent.getMessage());
        }

        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    private static boolean isClassUnsolicited(int i) {
        boolean flag;
        if(i >= 600 && i < 700)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static NativeDaemonEvent parseRawEvent(String s) {
        String as[] = s.split(" ");
        if(as.length < 2)
            throw new IllegalArgumentException("Insufficient arguments");
        int i;
        int j;
        int k;
        int l;
        try {
            i = Integer.parseInt(as[0]);
            j = as[0].length();
        }
        catch(NumberFormatException numberformatexception) {
            throw new IllegalArgumentException("problem parsing code", numberformatexception);
        }
        k = j + 1;
        l = -1;
        if(!isClassUnsolicited(i)) {
            if(as.length < 3)
                throw new IllegalArgumentException("Insufficient arguemnts");
            int i1;
            try {
                l = Integer.parseInt(as[1]);
                i1 = as[1].length();
            }
            catch(NumberFormatException numberformatexception1) {
                throw new IllegalArgumentException("problem parsing cmdNumber", numberformatexception1);
            }
            k += i1 + 1;
        }
        return new NativeDaemonEvent(l, i, s.substring(k), s);
    }

    public static String[] unescapeArgs(String s) {
        ArrayList arraylist;
        int i;
        int j;
        boolean flag;
        arraylist = new ArrayList();
        i = s.length();
        j = 0;
        flag = false;
        if(s.charAt(0) == '"') {
            flag = true;
            j = 0 + 1;
        }
_L5:
        int j1;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        if(!flag)
            break MISSING_BLOCK_LABEL_196;
        j1 = j;
_L3:
        int k = s.indexOf('"', j1);
        if(k != -1 && s.charAt(k - 1) == '\\') goto _L2; else goto _L1
_L1:
        if(k == -1)
            k = i;
        String s1 = s.substring(j, k);
        j += s1.length();
        int l;
        int i1;
        if(!flag)
            s1 = s1.trim();
        else
            j++;
        s1.replace("\\\\", "\\");
        s1.replace("\\\"", "\"");
        arraylist.add(s1);
        l = s.indexOf(' ', j);
        i1 = s.indexOf(" \"", j);
        if(i1 > -1 && i1 <= l) {
            flag = true;
            j = i1 + 2;
        } else {
            flag = false;
            if(l > -1)
                j = l + 1;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        j1 = k + 1;
          goto _L3
        k = s.indexOf(' ', j);
          goto _L1
        if(true) goto _L5; else goto _L4
_L4:
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public void checkCode(int i) {
        if(mCode != i)
            throw new IllegalStateException((new StringBuilder()).append("Expected ").append(i).append(" but was: ").append(this).toString());
        else
            return;
    }

    public int getCmdNumber() {
        return mCmdNumber;
    }

    public int getCode() {
        return mCode;
    }

    public String getField(int i) {
        if(mParsed == null)
            mParsed = unescapeArgs(mRawEvent);
        int j = i + 2;
        String s;
        if(j > mParsed.length)
            s = null;
        else
            s = mParsed[j];
        return s;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getRawEvent() {
        return mRawEvent;
    }

    public boolean isClassClientError() {
        boolean flag;
        if(mCode >= 500 && mCode < 600)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isClassContinue() {
        boolean flag;
        if(mCode >= 100 && mCode < 200)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isClassOk() {
        boolean flag;
        if(mCode >= 200 && mCode < 300)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isClassServerError() {
        boolean flag;
        if(mCode >= 400 && mCode < 500)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isClassUnsolicited() {
        return isClassUnsolicited(mCode);
    }

    public String toString() {
        return mRawEvent;
    }

    private final int mCmdNumber;
    private final int mCode;
    private final String mMessage;
    private String mParsed[];
    private final String mRawEvent;
}
