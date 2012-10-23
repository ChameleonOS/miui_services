// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.util.Log;
import java.util.List;
import miui.util.AudioOutputHelper;

// Referenced classes of package com.android.server.am:
//            BroadcastFilter, ReceiverList, ProcessRecord

public class ExtraActivityManagerService {

    public ExtraActivityManagerService() {
    }

    public static void adjustMediaButtonReceivers(List list, List list1) {
        List list2 = AudioOutputHelper.getActiveClientNameList(list1, null, true);
        if(list2 != null) {
            int i = 0;
            int j = 0;
            while(j < list.size())  {
                Object obj = list.get(j);
                String s = getProcessName(obj);
                if(s != null && list2.contains(s)) {
                    list.remove(j);
                    list.add(i, obj);
                    i++;
                }
                j++;
            }
        }
    }

    public static void adjustMediaButtonReceivers(List list, List list1, String s) {
        if("android.intent.action.MEDIA_BUTTON".equals(s))
            adjustMediaButtonReceivers(list, list1);
    }

    private static String getProcessName(Object obj) {
        String s = null;
        if(!(obj instanceof BroadcastFilter)) goto _L2; else goto _L1
_L1:
        BroadcastFilter broadcastfilter = (BroadcastFilter)obj;
        if(broadcastfilter.receiverList != null && broadcastfilter.receiverList.app != null)
            s = broadcastfilter.receiverList.app.processName;
_L4:
        return s;
_L2:
        if(obj instanceof ResolveInfo) {
            ResolveInfo resolveinfo = (ResolveInfo)obj;
            if(resolveinfo.activityInfo != null)
                s = resolveinfo.activityInfo.processName;
        } else {
            Log.e(TAG, (new StringBuilder()).append("unknown receiver type ").append(obj).toString());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static String TAG = com/android/server/am/ExtraActivityManagerService.getName();

}
