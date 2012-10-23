// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.*;
import android.content.pm.*;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.content.PackageMonitor;
import java.util.List;

public class RecognitionManagerService extends Binder {
    class MyPackageMonitor extends PackageMonitor {

        public void onSomePackagesChanged() {
            ComponentName componentname = getCurRecognizer();
            if(componentname != null) goto _L2; else goto _L1
_L1:
            if(anyPackagesAppearing()) {
                ComponentName componentname1 = findAvailRecognizer(null);
                if(componentname1 != null)
                    setCurRecognizer(componentname1);
            }
_L4:
            return;
_L2:
            int i = isPackageDisappearing(componentname.getPackageName());
            if(i == 3 || i == 2)
                setCurRecognizer(findAvailRecognizer(null));
            else
            if(isPackageModified(componentname.getPackageName()))
                setCurRecognizer(findAvailRecognizer(componentname.getPackageName()));
            if(true) goto _L4; else goto _L3
_L3:
        }

        final RecognitionManagerService this$0;

        MyPackageMonitor() {
            this$0 = RecognitionManagerService.this;
            super();
        }
    }


    RecognitionManagerService(Context context) {
        mContext = context;
        mMonitor.register(context, null, true);
    }

    ComponentName findAvailRecognizer(String s) {
        List list;
        int i;
        list = mContext.getPackageManager().queryIntentServices(new Intent("android.speech.RecognitionService"), 0);
        i = list.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        ComponentName componentname;
        Slog.w("RecognitionManagerService", "no available voice recognition services found");
        componentname = null;
_L4:
        return componentname;
_L2:
        if(s != null) {
            int j = 0;
            do {
                if(j >= i)
                    break;
                android.content.pm.ServiceInfo serviceinfo1 = ((ResolveInfo)list.get(j)).serviceInfo;
                if(s.equals(((ComponentInfo) (serviceinfo1)).packageName)) {
                    componentname = new ComponentName(((ComponentInfo) (serviceinfo1)).packageName, ((ComponentInfo) (serviceinfo1)).name);
                    continue; /* Loop/switch isn't completed */
                }
                j++;
            } while(true);
        }
        if(i > 1)
            Slog.w("RecognitionManagerService", "more than one voice recognition service found, picking first");
        android.content.pm.ServiceInfo serviceinfo = ((ResolveInfo)list.get(0)).serviceInfo;
        componentname = new ComponentName(((ComponentInfo) (serviceinfo)).packageName, ((ComponentInfo) (serviceinfo)).name);
        if(true) goto _L4; else goto _L3
_L3:
    }

    ComponentName getCurRecognizer() {
        String s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "voice_recognition_service");
        ComponentName componentname;
        if(TextUtils.isEmpty(s))
            componentname = null;
        else
            componentname = ComponentName.unflattenFromString(s);
        return componentname;
    }

    void setCurRecognizer(ComponentName componentname) {
        android.content.ContentResolver contentresolver = mContext.getContentResolver();
        String s;
        if(componentname != null)
            s = componentname.flattenToShortString();
        else
            s = "";
        android.provider.Settings.Secure.putString(contentresolver, "voice_recognition_service", s);
    }

    public void systemReady() {
        ComponentName componentname;
        componentname = getCurRecognizer();
        if(componentname == null)
            break MISSING_BLOCK_LABEL_45;
        mContext.getPackageManager().getServiceInfo(componentname, 0);
_L1:
        return;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        ComponentName componentname2 = findAvailRecognizer(null);
        if(componentname2 != null)
            setCurRecognizer(componentname2);
          goto _L1
        ComponentName componentname1 = findAvailRecognizer(null);
        if(componentname1 != null)
            setCurRecognizer(componentname1);
          goto _L1
    }

    static final String TAG = "RecognitionManagerService";
    final Context mContext;
    final MyPackageMonitor mMonitor = new MyPackageMonitor();
}
