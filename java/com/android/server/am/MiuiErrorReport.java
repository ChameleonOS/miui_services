// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.*;
import android.content.pm.*;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.*;
import miui.os.Build;
import miui.util.ErrorReportUtils;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.android.server.am:
//            ProcessRecord

public class MiuiErrorReport {

    public MiuiErrorReport() {
    }

    private static String getAnrStackTrack() {
        StringBuilder stringbuilder;
        String s;
        stringbuilder = new StringBuilder();
        s = SystemProperties.get("dalvik.vm.stack-trace-file", null);
        if(s != null && s.length() != 0) goto _L2; else goto _L1
_L1:
        String s1 = "";
_L7:
        return s1;
_L2:
        File file;
        BufferedReader bufferedreader;
        file = new File(s);
        bufferedreader = null;
        BufferedReader bufferedreader1 = new BufferedReader(new FileReader(file));
        boolean flag;
        int i;
        flag = false;
        i = 0;
_L6:
        String s2;
        s2 = bufferedreader1.readLine();
        if(s2 == null)
            break; /* Loop/switch isn't completed */
        if(!s2.startsWith("DALVIK THREADS:")) goto _L4; else goto _L3
_L3:
        flag = true;
_L8:
        if(!flag)
            continue; /* Loop/switch isn't completed */
        stringbuilder.append(s2);
        stringbuilder.append("\n");
        i++;
        if(i <= 300) goto _L6; else goto _L5
_L5:
        Exception exception;
        boolean flag1;
        if(bufferedreader1 != null)
            try {
                bufferedreader1.close();
            }
            catch(IOException ioexception3) { }
_L9:
        s1 = stringbuilder.toString();
          goto _L7
_L4:
        if(!flag)
            break; /* Loop/switch isn't completed */
        flag1 = s2.startsWith("-----");
        if(!flag1) goto _L8; else goto _L5
        exception;
_L10:
        IOException ioexception1;
        IOException ioexception4;
        if(bufferedreader != null)
            try {
                bufferedreader.close();
            }
            catch(IOException ioexception2) { }
        throw exception;
        ioexception4;
_L11:
        if(bufferedreader != null)
            try {
                bufferedreader.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
          goto _L9
        exception;
        bufferedreader = bufferedreader1;
          goto _L10
        IOException ioexception;
        ioexception;
        bufferedreader = bufferedreader1;
          goto _L11
    }

    private static String getDeviceString() {
        String s = SystemProperties.get("ro.product.mod_device", null);
        if(TextUtils.isEmpty(s))
            s = Build.DEVICE;
        return s;
    }

    private static String getIMEI() {
        String s = TelephonyManager.getDefault().getDeviceId();
        if(TextUtils.isEmpty(s))
            s = "";
        return s;
    }

    private static String getNetworkName(Context context) {
        return ((TelephonyManager)context.getSystemService("phone")).getNetworkOperatorName();
    }

    private static String getPackageVersion(Context context, String s) {
label0:
        {
            PackageManager packagemanager = context.getPackageManager();
            String s1;
            PackageInfo packageinfo;
            try {
                packageinfo = packagemanager.getPackageInfo(s, 0);
            }
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
                namenotfoundexception.printStackTrace();
                s1 = "";
                if(false)
                    ;
                else
                    break label0;
            }
            if((1 & packageinfo.applicationInfo.flags) != 0 || (0x80 & packageinfo.applicationInfo.flags) != 0)
                s1 = android.os.Build.VERSION.INCREMENTAL;
            else
                s1 = (new StringBuilder()).append(packageinfo.versionName).append(" (").append(packageinfo.versionCode).append(")").toString();
        }
        return s1;
    }

    protected static void populateAnrData(JSONObject jsonobject, ProcessRecord processrecord) {
        try {
            jsonobject.put("error_type", "anr");
            jsonobject.put("anr_cause", processrecord.notRespondingReport.shortMsg);
            String s;
            if(processrecord.notRespondingReport.tag == null)
                s = "";
            else
                s = processrecord.notRespondingReport.tag;
            jsonobject.put("anr_activity", s);
            jsonobject.put("stack_track", getAnrStackTrack());
        }
        catch(JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
    }

    protected static void populateCommonData(JSONObject jsonobject, Context context, ProcessRecord processrecord) {
        jsonobject.put("network", getNetworkName(context));
        jsonobject.put("device", getDeviceString());
        jsonobject.put("imei", getIMEI());
        jsonobject.put("platform", android.os.Build.VERSION.RELEASE);
        jsonobject.put("build_version", android.os.Build.VERSION.INCREMENTAL);
        jsonobject.put("package_name", processrecord.info.packageName);
        jsonobject.put("app_version", getPackageVersion(context, processrecord.info.packageName));
_L1:
        return;
        JSONException jsonexception;
        jsonexception;
        jsonexception.printStackTrace();
          goto _L1
    }

    protected static void populateFcData(JSONObject jsonobject, android.app.ApplicationErrorReport.CrashInfo crashinfo) {
        if(crashinfo != null)
            try {
                jsonobject.put("error_type", "fc");
                jsonobject.put("exception_class", crashinfo.exceptionClassName);
                jsonobject.put("exception_source_method", (new StringBuilder()).append(crashinfo.throwClassName).append(".").append(crashinfo.throwMethodName).toString());
                jsonobject.put("stack_track", crashinfo.stackTrace);
            }
            catch(JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
    }

    public static void sendAnrErrorReport(Context context, ProcessRecord processrecord, boolean flag) {
        try {
            if(Build.isOfficialVersion() && (flag || ErrorReportUtils.isUserAllowed(context) && ErrorReportUtils.isWifiConnected(context))) {
                JSONObject jsonobject = new JSONObject();
                populateCommonData(jsonobject, context, processrecord);
                populateAnrData(jsonobject, processrecord);
                ErrorReportUtils.postErrorReport(context, jsonobject);
            }
        }
        catch(Exception exception) { }
    }

    public static void sendFcErrorReport(Context context, ProcessRecord processrecord, android.app.ApplicationErrorReport.CrashInfo crashinfo, boolean flag) {
        JSONObject jsonobject;
        Intent intent;
        if(!Build.isOfficialVersion())
            break MISSING_BLOCK_LABEL_128;
        jsonobject = new JSONObject();
        populateCommonData(jsonobject, context, processrecord);
        populateFcData(jsonobject, crashinfo);
        if(!flag)
            break MISSING_BLOCK_LABEL_102;
        intent = new Intent();
        intent.setPackage("com.miui.bugreport");
        intent.setClassName("com.miui.bugreport", "com.miui.bugreport.MiuiFcPreviewActivity");
        intent.putExtra("extra_fc_report", jsonobject.toString());
        intent.setFlags(0x10000000);
        try {
            context.startActivity(intent);
        }
        catch(ActivityNotFoundException activitynotfoundexception) { }
        break MISSING_BLOCK_LABEL_128;
        try {
            if(ErrorReportUtils.isUserAllowed(context) && ErrorReportUtils.isWifiConnected(context))
                ErrorReportUtils.postErrorReport(context, jsonobject);
        }
        catch(Exception exception) { }
    }
}
