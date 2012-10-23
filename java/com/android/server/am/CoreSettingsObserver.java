// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.util.Log;
import java.util.*;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService

class CoreSettingsObserver extends ContentObserver {

    public CoreSettingsObserver(ActivityManagerService activitymanagerservice) {
        super(activitymanagerservice.mHandler);
        mActivityManagerService = activitymanagerservice;
        beginObserveCoreSettings();
        sendCoreSettings();
    }

    private void beginObserveCoreSettings() {
        android.net.Uri uri;
        for(Iterator iterator = sCoreSettingToTypeMap.keySet().iterator(); iterator.hasNext(); mActivityManagerService.mContext.getContentResolver().registerContentObserver(uri, false, this))
            uri = android.provider.Settings.Secure.getUriFor((String)iterator.next());

    }

    private void populateCoreSettings(Bundle bundle) {
        Context context;
        Iterator iterator;
        context = mActivityManagerService.mContext;
        iterator = sCoreSettingToTypeMap.entrySet().iterator();
_L2:
        String s;
        Class class1;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        s = (String)entry.getKey();
        class1 = (Class)entry.getValue();
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        if(class1 == java/lang/String) {
            bundle.putString(s, android.provider.Settings.Secure.getString(context.getContentResolver(), s));
            continue; /* Loop/switch isn't completed */
        }
        try {
            if(class1 == Integer.TYPE)
                bundle.putInt(s, android.provider.Settings.Secure.getInt(context.getContentResolver(), s));
            else
            if(class1 == Float.TYPE)
                bundle.putFloat(s, android.provider.Settings.Secure.getFloat(context.getContentResolver(), s));
            else
            if(class1 == Long.TYPE)
                bundle.putLong(s, android.provider.Settings.Secure.getLong(context.getContentResolver(), s));
        }
        // Misplaced declaration of an exception variable
        catch(android.provider.Settings.SettingNotFoundException settingnotfoundexception) {
            Log.w(LOG_TAG, (new StringBuilder()).append("Cannot find setting \"").append(s).append("\"").toString(), settingnotfoundexception);
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void sendCoreSettings() {
        populateCoreSettings(mCoreSettings);
        mActivityManagerService.onCoreSettingsChange(mCoreSettings);
    }

    public Bundle getCoreSettingsLocked() {
        return (Bundle)mCoreSettings.clone();
    }

    public void onChange(boolean flag) {
        ActivityManagerService activitymanagerservice = mActivityManagerService;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        sendCoreSettings();
        return;
    }

    private static final String LOG_TAG = com/android/server/am/CoreSettingsObserver.getSimpleName();
    private static final Map sCoreSettingToTypeMap;
    private final ActivityManagerService mActivityManagerService;
    private final Bundle mCoreSettings = new Bundle();

    static  {
        sCoreSettingToTypeMap = new HashMap();
        sCoreSettingToTypeMap.put("long_press_timeout", Integer.TYPE);
    }
}
