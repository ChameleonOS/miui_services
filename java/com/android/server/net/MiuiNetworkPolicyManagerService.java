// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.app.IActivityManager;
import android.content.Context;
import android.net.*;
import android.os.INetworkManagementService;
import android.os.IPowerManager;
import android.telephony.TelephonyManager;
import com.android.internal.util.Objects;

// Referenced classes of package com.android.server.net:
//            NetworkPolicyManagerService

public class MiuiNetworkPolicyManagerService extends NetworkPolicyManagerService {

    public MiuiNetworkPolicyManagerService(Context context, IActivityManager iactivitymanager, IPowerManager ipowermanager, INetworkStatsService inetworkstatsservice, INetworkManagementService inetworkmanagementservice) {
        super(context, iactivitymanager, ipowermanager, inetworkstatsservice, inetworkmanagementservice);
        mContext = context;
        mTelephony = (TelephonyManager)mContext.getSystemService("phone");
    }

    private boolean isIntervalValid(int i) {
        boolean flag;
        if(System.currentTimeMillis() - sLastNotificationTimeArr[i] > 0x5265c00L)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected void enqueueNotification(NetworkPolicy networkpolicy, int i, long l) {
        if(isIntervalValid(i)) {
            sLastNotificationTimeArr[i] = System.currentTimeMillis();
            super.enqueueNotification(networkpolicy, i, l);
        }
    }

    protected void setNetworkTemplateEnabled(NetworkTemplate networktemplate, boolean flag) {
        networktemplate.getMatchRule();
        JVM INSTR tableswitch 1 3: default 32
    //                   1 39
    //                   2 39
    //                   3 39;
           goto _L1 _L2 _L2 _L2
_L1:
        super.setNetworkTemplateEnabled(networktemplate, flag);
_L4:
        return;
_L2:
        if(!flag && !isIntervalValid(2)) goto _L4; else goto _L3
_L3:
        if(Objects.equal(mTelephony.getSubscriberId(), networktemplate.getSubscriberId())) {
            android.content.ContentResolver contentresolver = mContext.getContentResolver();
            int i;
            if(flag)
                i = 1;
            else
                i = 0;
            android.provider.Settings.Secure.putInt(contentresolver, "mobile_policy", i);
        }
        if(true) goto _L1; else goto _L5
_L5:
    }

    private static long sLastNotificationTimeArr[];
    private Context mContext;
    private TelephonyManager mTelephony;

    static  {
        long al[] = new long[4];
        al[0] = 0L;
        al[1] = 0L;
        al[2] = 0L;
        al[3] = 0L;
        sLastNotificationTimeArr = al;
    }
}
