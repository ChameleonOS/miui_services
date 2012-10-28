// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.net;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.net.*;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.*;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.*;
import com.android.internal.os.AtomicFile;
import com.android.internal.util.*;
import com.google.android.collect.*;
import java.io.*;
import java.net.ProtocolException;
import java.util.*;
import libcore.io.IoUtils;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.net:
//            NetworkAlertObserver

public class NetworkPolicyManagerService extends android.net.INetworkPolicyManager.Stub {
    public static class XmlUtils {

        public static boolean readBooleanAttribute(XmlPullParser xmlpullparser, String s) {
            return Boolean.parseBoolean(xmlpullparser.getAttributeValue(null, s));
        }

        public static int readIntAttribute(XmlPullParser xmlpullparser, String s) throws IOException {
            String s1 = xmlpullparser.getAttributeValue(null, s);
            int i;
            try {
                i = Integer.parseInt(s1);
            }
            catch(NumberFormatException numberformatexception) {
                throw new ProtocolException((new StringBuilder()).append("problem parsing ").append(s).append("=").append(s1).append(" as int").toString());
            }
            return i;
        }

        public static long readLongAttribute(XmlPullParser xmlpullparser, String s) throws IOException {
            String s1 = xmlpullparser.getAttributeValue(null, s);
            long l;
            try {
                l = Long.parseLong(s1);
            }
            catch(NumberFormatException numberformatexception) {
                throw new ProtocolException((new StringBuilder()).append("problem parsing ").append(s).append("=").append(s1).append(" as long").toString());
            }
            return l;
        }

        public static void writeBooleanAttribute(XmlSerializer xmlserializer, String s, boolean flag) throws IOException {
            xmlserializer.attribute(null, s, Boolean.toString(flag));
        }

        public static void writeIntAttribute(XmlSerializer xmlserializer, String s, int i) throws IOException {
            xmlserializer.attribute(null, s, Integer.toString(i));
        }

        public static void writeLongAttribute(XmlSerializer xmlserializer, String s, long l) throws IOException {
            xmlserializer.attribute(null, s, Long.toString(l));
        }

        public XmlUtils() {
        }
    }

    static class Injector {

        static long adjustMobileDataUsage(NetworkPolicyManagerService networkpolicymanagerservice, NetworkTemplate networktemplate, long l, long l1) {
            long l2 = 0L;
            Context context = networkpolicymanagerservice.getContext();
            if(networktemplate.getMatchRule() == 1) {
                long l3 = android.provider.Settings.Secure.getLong(context.getContentResolver(), "data_usage_adjusting_time", l2);
                if(l3 >= l && l3 <= l1)
                    l2 = Math.max(l2, android.provider.Settings.Secure.getLong(context.getContentResolver(), "data_usage_adjustment", l2));
            }
            return l2;
        }

        static boolean isIntervalValid(int i) {
            boolean flag;
            if(System.currentTimeMillis() - sLastNotificationTimeArr[i] > 0x5265c00L)
                flag = true;
            else
                flag = false;
            return flag;
        }

        static void setInterval(int i) {
            sLastNotificationTimeArr[i] = System.currentTimeMillis();
        }

        static void setNetworkTemplateEnabled(NetworkPolicyManagerService networkpolicymanagerservice, NetworkTemplate networktemplate, boolean flag) {
            TelephonyManager telephonymanager = (TelephonyManager)networkpolicymanagerservice.getContext().getSystemService("phone");
            networktemplate.getMatchRule();
            JVM INSTR tableswitch 1 3: default 44
        //                       1 45
        //                       2 45
        //                       3 45;
               goto _L1 _L2 _L2 _L2
_L1:
            return;
_L2:
            if((flag || isIntervalValid(2)) && Objects.equal(telephonymanager.getSubscriberId(), networktemplate.getSubscriberId())) {
                android.content.ContentResolver contentresolver = networkpolicymanagerservice.getContext().getContentResolver();
                int i;
                if(flag)
                    i = 1;
                else
                    i = 0;
                android.provider.Settings.Secure.putInt(contentresolver, "mobile_policy", i);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        private static long sLastNotificationTimeArr[];

        static  {
            long al[] = new long[4];
            al[0] = 0L;
            al[1] = 0L;
            al[2] = 0L;
            al[3] = 0L;
            sLastNotificationTimeArr = al;
        }

        Injector() {
        }
    }


    public NetworkPolicyManagerService(Context context, IActivityManager iactivitymanager, IPowerManager ipowermanager, INetworkStatsService inetworkstatsservice, INetworkManagementService inetworkmanagementservice) {
        this(context, iactivitymanager, ipowermanager, inetworkstatsservice, inetworkmanagementservice, ((TrustedTime) (NtpTrustedTime.getInstance(context))), getSystemDir(), false);
    }

    public NetworkPolicyManagerService(Context context, IActivityManager iactivitymanager, IPowerManager ipowermanager, INetworkStatsService inetworkstatsservice, INetworkManagementService inetworkmanagementservice, TrustedTime trustedtime, File file, 
            boolean flag) {
        mRulesLock = new Object();
        mNetworkPolicy = Maps.newHashMap();
        mNetworkRules = Maps.newHashMap();
        mAppPolicy = new SparseIntArray();
        mUidRules = new SparseIntArray();
        mMeteredIfaces = Sets.newHashSet();
        mOverLimitNotified = Sets.newHashSet();
        mActiveNotifs = Sets.newHashSet();
        mUidForeground = new SparseBooleanArray();
        mUidPidForeground = new SparseArray();
        mListeners = new RemoteCallbackList();
        mContext = (Context)Preconditions.checkNotNull(context, "missing context");
        mActivityManager = (IActivityManager)Preconditions.checkNotNull(iactivitymanager, "missing activityManager");
        mPowerManager = (IPowerManager)Preconditions.checkNotNull(ipowermanager, "missing powerManager");
        mNetworkStats = (INetworkStatsService)Preconditions.checkNotNull(inetworkstatsservice, "missing networkStats");
        mNetworkManager = (INetworkManagementService)Preconditions.checkNotNull(inetworkmanagementservice, "missing networkManagement");
        mTime = (TrustedTime)Preconditions.checkNotNull(trustedtime, "missing TrustedTime");
        mHandlerThread = new HandlerThread("NetworkPolicy");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper(), mHandlerCallback);
        mSuppressDefaultPolicy = flag;
        mPolicyFile = new AtomicFile(new File(file, "netpolicy.xml"));
    }

    private void addNetworkPolicyLocked(NetworkPolicy networkpolicy) {
        mNetworkPolicy.put(networkpolicy.template, networkpolicy);
        updateNetworkEnabledLocked();
        updateNetworkRulesLocked();
        updateNotificationsLocked();
        writePolicyLocked();
    }

    private static Intent buildAllowBackgroundDataIntent() {
        return new Intent("com.android.server.net.action.ALLOW_BACKGROUND");
    }

    private static Intent buildNetworkOverLimitIntent(NetworkTemplate networktemplate) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.net.NetworkOverLimitActivity"));
        intent.addFlags(0x10000000);
        intent.putExtra("android.net.NETWORK_TEMPLATE", networktemplate);
        return intent;
    }

    private String buildNotificationTag(NetworkPolicy networkpolicy, int i) {
        return (new StringBuilder()).append("NetworkPolicy:").append(networkpolicy.template.hashCode()).append(":").append(i).toString();
    }

    private static Intent buildSnoozeWarningIntent(NetworkTemplate networktemplate) {
        Intent intent = new Intent("com.android.server.net.action.SNOOZE_WARNING");
        intent.putExtra("android.net.NETWORK_TEMPLATE", networktemplate);
        return intent;
    }

    private static Intent buildViewDataUsageIntent(NetworkTemplate networktemplate) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW_DATA_USAGE_SUMMARY");
        intent.addFlags(0x10000000);
        intent.putExtra("android.net.NETWORK_TEMPLATE", networktemplate);
        return intent;
    }

    private void cancelNotification(String s) {
        String s1 = mContext.getPackageName();
        mNotifManager.cancelNotificationWithTag(s1, s, 0);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static void collectKeys(SparseBooleanArray sparsebooleanarray, SparseBooleanArray sparsebooleanarray1) {
        int i = sparsebooleanarray.size();
        for(int j = 0; j < i; j++)
            sparsebooleanarray1.put(sparsebooleanarray.keyAt(j), true);

    }

    private static void collectKeys(SparseIntArray sparseintarray, SparseBooleanArray sparsebooleanarray) {
        int i = sparseintarray.size();
        for(int j = 0; j < i; j++)
            sparsebooleanarray.put(sparseintarray.keyAt(j), true);

    }

    private void computeUidForegroundLocked(int i) {
        SparseBooleanArray sparsebooleanarray = (SparseBooleanArray)mUidPidForeground.get(i);
        boolean flag = false;
        int j = sparsebooleanarray.size();
        int k = 0;
        do {
label0:
            {
                if(k < j) {
                    if(!sparsebooleanarray.valueAt(k))
                        break label0;
                    flag = true;
                }
                if(mUidForeground.get(i, false) != flag) {
                    mUidForeground.put(i, flag);
                    updateRulesForUidLocked(i);
                }
                return;
            }
            k++;
        } while(true);
    }

    private long currentTimeMillis() {
        long l;
        if(mTime.hasCache())
            l = mTime.currentTimeMillis();
        else
            l = System.currentTimeMillis();
        return l;
    }

    private static void dumpSparseBooleanArray(PrintWriter printwriter, SparseBooleanArray sparsebooleanarray) {
        printwriter.print("[");
        int i = sparsebooleanarray.size();
        for(int j = 0; j < i; j++) {
            printwriter.print((new StringBuilder()).append(sparsebooleanarray.keyAt(j)).append("=").append(sparsebooleanarray.valueAt(j)).toString());
            if(j < i - 1)
                printwriter.print(",");
        }

        printwriter.print("]");
    }

    private void enqueueNotification(NetworkPolicy networkpolicy, int i, long l) {
        String s;
        android.app.Notification.Builder builder;
        Resources resources;
        s = buildNotificationTag(networkpolicy, i);
        builder = new android.app.Notification.Builder(mContext);
        builder.setOnlyAlertOnce(true);
        builder.setWhen(0L);
        resources = mContext.getResources();
        i;
        JVM INSTR tableswitch 1 3: default 72
    //                   1 118
    //                   2 230
    //                   3 402;
           goto _L1 _L2 _L3 _L4
_L1:
        String s2 = mContext.getPackageName();
        int ai[] = new int[1];
        mNotifManager.enqueueNotificationWithTag(s2, s, 0, builder.getNotification(), ai);
        mActiveNotifs.add(s);
_L17:
        return;
_L2:
        CharSequence charsequence3 = resources.getText(0x10404d7);
        String s3 = resources.getString(0x10404d8);
        builder.setSmallIcon(0x1080078);
        builder.setTicker(charsequence3);
        builder.setContentTitle(charsequence3);
        builder.setContentText(s3);
        Intent intent2 = buildSnoozeWarningIntent(networkpolicy.template);
        builder.setDeleteIntent(PendingIntent.getBroadcast(mContext, 0, intent2, 0x8000000));
        Intent intent3 = buildViewDataUsageIntent(networkpolicy.template);
        builder.setContentIntent(PendingIntent.getActivity(mContext, 0, intent3, 0x8000000));
          goto _L1
_L3:
        CharSequence charsequence1 = resources.getText(0x10404dd);
        networkpolicy.template.getMatchRule();
        JVM INSTR tableswitch 1 4: default 276
    //                   1 376
    //                   2 350
    //                   3 363
    //                   4 389;
           goto _L5 _L6 _L7 _L8 _L9
_L5:
        CharSequence charsequence2 = null;
_L10:
        builder.setOngoing(true);
        builder.setSmallIcon(0x1080510);
        builder.setTicker(charsequence2);
        builder.setContentTitle(charsequence2);
        builder.setContentText(charsequence1);
        Intent intent1 = buildNetworkOverLimitIntent(networkpolicy.template);
        builder.setContentIntent(PendingIntent.getActivity(mContext, 0, intent1, 0x8000000));
          goto _L1
_L7:
        charsequence2 = resources.getText(0x10404d9);
          goto _L10
_L8:
        charsequence2 = resources.getText(0x10404da);
          goto _L10
_L6:
        charsequence2 = resources.getText(0x10404db);
          goto _L10
_L9:
        charsequence2 = resources.getText(0x10404dc);
          goto _L10
_L4:
        String s1;
        long l1 = l - networkpolicy.limitBytes;
        Object aobj[] = new Object[1];
        aobj[0] = Formatter.formatFileSize(mContext, l1);
        s1 = resources.getString(0x10404e2, aobj);
        networkpolicy.template.getMatchRule();
        JVM INSTR tableswitch 1 4: default 480
    //                   1 580
    //                   2 554
    //                   3 567
    //                   4 593;
           goto _L11 _L12 _L13 _L14 _L15
_L11:
        CharSequence charsequence = null;
_L16:
        builder.setOngoing(true);
        builder.setSmallIcon(0x1080078);
        builder.setTicker(charsequence);
        builder.setContentTitle(charsequence);
        builder.setContentText(s1);
        Intent intent = buildViewDataUsageIntent(networkpolicy.template);
        builder.setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0x8000000));
          goto _L1
_L13:
        charsequence = resources.getText(0x10404de);
          goto _L16
_L14:
        charsequence = resources.getText(0x10404df);
          goto _L16
_L12:
        charsequence = resources.getText(0x10404e0);
          goto _L16
_L15:
        charsequence = resources.getText(0x10404e1);
          goto _L16
        RemoteException remoteexception;
        remoteexception;
          goto _L17
    }

    private void enqueueRestrictedNotification(String s) {
        android.app.Notification.Builder builder;
        Resources resources = mContext.getResources();
        builder = new android.app.Notification.Builder(mContext);
        CharSequence charsequence = resources.getText(0x10404e3);
        String s1 = resources.getString(0x10404e4);
        builder.setOnlyAlertOnce(true);
        builder.setOngoing(true);
        builder.setSmallIcon(0x1080078);
        builder.setTicker(charsequence);
        builder.setContentTitle(charsequence);
        builder.setContentText(s1);
        Intent intent = buildAllowBackgroundDataIntent();
        builder.setContentIntent(PendingIntent.getBroadcast(mContext, 0, intent, 0x8000000));
        String s2 = mContext.getPackageName();
        int ai[] = new int[1];
        mNotifManager.enqueueNotificationWithTag(s2, s, 0, builder.getNotification(), ai);
        mActiveNotifs.add(s);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void enqueueValidNotification(NetworkPolicy networkpolicy, int i, long l) {
        if(Injector.isIntervalValid(i)) {
            Injector.setInterval(i);
            enqueueNotification(networkpolicy, i, l);
        }
    }

    private void ensureActiveMobilePolicyLocked() {
        if(!mSuppressDefaultPolicy) goto _L2; else goto _L1
_L1:
        return;
_L2:
        TelephonyManager telephonymanager = TelephonyManager.from(mContext);
        if(telephonymanager.getSimState() == 5) {
            String s = telephonymanager.getSubscriberId();
            NetworkIdentity networkidentity = new NetworkIdentity(0, 0, s, null, false);
            boolean flag = false;
            Iterator iterator = mNetworkPolicy.values().iterator();
            do {
                if(!iterator.hasNext())
                    break;
                if(((NetworkPolicy)iterator.next()).template.matches(networkidentity))
                    flag = true;
            } while(true);
            if(!flag) {
                Slog.i("NetworkPolicy", "no policy for active mobile network; generating default policy");
                long l = 0x100000L * (long)mContext.getResources().getInteger(0x10e0031);
                Time time = new Time();
                time.setToNow();
                int i = time.monthDay;
                String s1 = time.timezone;
                addNetworkPolicyLocked(new NetworkPolicy(NetworkTemplate.buildTemplateMobileAll(s), i, s1, l, -1L, -1L, -1L, true, true));
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private NetworkPolicy findPolicyForNetworkLocked(NetworkIdentity networkidentity) {
        Iterator iterator = mNetworkPolicy.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        NetworkPolicy networkpolicy = (NetworkPolicy)iterator.next();
        if(!networkpolicy.template.matches(networkidentity)) goto _L4; else goto _L3
_L3:
        return networkpolicy;
_L2:
        networkpolicy = null;
        if(true) goto _L3; else goto _L5
_L5:
    }

    private NetworkQuotaInfo getNetworkQuotaInfoUnchecked(NetworkState networkstate) {
        NetworkIdentity networkidentity = NetworkIdentity.buildNetworkIdentity(mContext, networkstate);
        NetworkPolicy networkpolicy;
        synchronized(mRulesLock) {
            networkpolicy = findPolicyForNetworkLocked(networkidentity);
        }
        NetworkQuotaInfo networkquotainfo;
        if(networkpolicy == null || !networkpolicy.hasCycle()) {
            networkquotainfo = null;
        } else {
            long l = currentTimeMillis();
            long l1 = NetworkPolicyManager.computeLastCycleBoundary(l, networkpolicy);
            long l2 = getTotalBytes(networkpolicy.template, l1, l);
            long l3;
            long l4;
            if(networkpolicy.warningBytes != -1L)
                l3 = networkpolicy.warningBytes;
            else
                l3 = -1L;
            if(networkpolicy.limitBytes != -1L)
                l4 = networkpolicy.limitBytes;
            else
                l4 = -1L;
            networkquotainfo = new NetworkQuotaInfo(l2, l3, l4);
        }
        return networkquotainfo;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static File getSystemDir() {
        return new File(Environment.getDataDirectory(), "system");
    }

    private long getTotalBytes(NetworkTemplate networktemplate, long l, long l1) {
        long l3;
        long l4;
        l3 = mNetworkStats.getNetworkTotalBytes(networktemplate, l, l1);
        l4 = Injector.adjustMobileDataUsage(this, networktemplate, l, l1);
        long l2 = l3 + l4;
_L2:
        return l2;
        RuntimeException runtimeexception;
        runtimeexception;
        Slog.w("NetworkPolicy", (new StringBuilder()).append("problem reading network stats: ").append(runtimeexception).toString());
        l2 = 0L;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        l2 = 0L;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private boolean isBandwidthControlEnabled() {
        long l = Binder.clearCallingIdentity();
        boolean flag1 = mNetworkManager.isBandwidthControlEnabled();
        boolean flag = flag1;
_L2:
        Binder.restoreCallingIdentity(l);
        return flag;
        RemoteException remoteexception;
        remoteexception;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private boolean isTemplateRelevant(NetworkTemplate networktemplate) {
        TelephonyManager telephonymanager = TelephonyManager.from(mContext);
        networktemplate.getMatchRule();
        JVM INSTR tableswitch 1 3: default 40
    //                   1 44
    //                   2 44
    //                   3 44;
           goto _L1 _L2 _L2 _L2
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        if(telephonymanager.getSimState() == 5)
            flag = Objects.equal(telephonymanager.getSubscriberId(), networktemplate.getSubscriberId());
        else
            flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static boolean isUidValidForRules(int i) {
        boolean flag;
        if(i == 1013 || i == 1019 || UserId.isApp(i))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void maybeRefreshTrustedTime() {
        if(mTime.getCacheAge() > 0x5265c00L)
            mTime.forceRefresh();
    }

    private void notifyOverLimitLocked(NetworkTemplate networktemplate) {
        if(!mOverLimitNotified.contains(networktemplate)) {
            mContext.startActivity(buildNetworkOverLimitIntent(networktemplate));
            mOverLimitNotified.add(networktemplate);
        }
    }

    private void notifyUnderLimitLocked(NetworkTemplate networktemplate) {
        mOverLimitNotified.remove(networktemplate);
    }

    private void performSnooze(NetworkTemplate networktemplate, int i) {
        long l;
        maybeRefreshTrustedTime();
        l = currentTimeMillis();
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        NetworkPolicy networkpolicy;
        networkpolicy = (NetworkPolicy)mNetworkPolicy.get(networktemplate);
        if(networkpolicy == null)
            throw new IllegalArgumentException((new StringBuilder()).append("unable to find policy for ").append(networktemplate).toString());
        break MISSING_BLOCK_LABEL_72;
        Exception exception;
        exception;
        throw exception;
        i;
        JVM INSTR tableswitch 1 2: default 96
    //                   1 107
    //                   2 133;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException("unexpected type");
_L2:
        networkpolicy.lastWarningSnooze = l;
_L5:
        updateNetworkEnabledLocked();
        updateNetworkRulesLocked();
        updateNotificationsLocked();
        writePolicyLocked();
        obj;
        JVM INSTR monitorexit ;
        return;
_L3:
        networkpolicy.lastLimitSnooze = l;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void readPolicyLocked() {
        java.io.FileInputStream fileinputstream;
        mNetworkPolicy.clear();
        mAppPolicy.clear();
        fileinputstream = null;
        XmlPullParser xmlpullparser;
        int i;
        fileinputstream = mPolicyFile.openRead();
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
        i = 1;
_L4:
        int j = xmlpullparser.next();
        if(j == 1) goto _L2; else goto _L1
_L1:
        String s = xmlpullparser.getName();
        if(j != 2) goto _L4; else goto _L3
_L3:
        if(!"policy-list".equals(s)) goto _L6; else goto _L5
_L5:
        i = XmlUtils.readIntAttribute(xmlpullparser, "version");
        if(i < 3) goto _L8; else goto _L7
_L7:
        mRestrictBackground = XmlUtils.readBooleanAttribute(xmlpullparser, "restrictBackground");
          goto _L4
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        upgradeLegacyBackgroundData();
_L2:
        IoUtils.closeQuietly(fileinputstream);
        return;
_L8:
        mRestrictBackground = false;
          goto _L4
        IOException ioexception;
        ioexception;
        Log.wtf("NetworkPolicy", "problem reading network policy", ioexception);
          goto _L2
        Exception exception;
        exception;
        IoUtils.closeQuietly(fileinputstream);
        throw exception;
_L6:
        if(!"network-policy".equals(s)) goto _L10; else goto _L9
_L9:
        int l1;
        String s1;
        l1 = XmlUtils.readIntAttribute(xmlpullparser, "networkTemplate");
        s1 = xmlpullparser.getAttributeValue(null, "subscriberId");
        if(i < 9) goto _L12; else goto _L11
_L11:
        String s2 = xmlpullparser.getAttributeValue(null, "networkId");
_L23:
        int i2 = XmlUtils.readIntAttribute(xmlpullparser, "cycleDay");
        if(i < 6) goto _L14; else goto _L13
_L13:
        String s3 = xmlpullparser.getAttributeValue(null, "cycleTimezone");
_L24:
        long l2;
        long l3;
        l2 = XmlUtils.readLongAttribute(xmlpullparser, "warningBytes");
        l3 = XmlUtils.readLongAttribute(xmlpullparser, "limitBytes");
        if(i < 5) goto _L16; else goto _L15
_L15:
        long l4 = XmlUtils.readLongAttribute(xmlpullparser, "lastLimitSnooze");
_L27:
        if(i < 4) goto _L18; else goto _L17
_L17:
        boolean flag = XmlUtils.readBooleanAttribute(xmlpullparser, "metered");
_L31:
        if(i < 5) goto _L20; else goto _L19
_L19:
        long l5 = XmlUtils.readLongAttribute(xmlpullparser, "lastWarningSnooze");
_L56:
        if(i < 7) goto _L22; else goto _L21
_L21:
        boolean flag1 = XmlUtils.readBooleanAttribute(xmlpullparser, "inferred");
_L57:
        NetworkTemplate networktemplate = new NetworkTemplate(l1, s1, s2);
        mNetworkPolicy.put(networktemplate, new NetworkPolicy(networktemplate, i2, s3, l2, l3, l5, l4, flag, flag1));
          goto _L4
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Log.wtf("NetworkPolicy", "problem reading network policy", xmlpullparserexception);
          goto _L2
_L12:
        s2 = null;
          goto _L23
_L14:
        s3 = "UTC";
          goto _L24
_L16:
        if(i < 2) goto _L26; else goto _L25
_L25:
        l4 = XmlUtils.readLongAttribute(xmlpullparser, "lastSnooze");
          goto _L27
_L10:
        if("uid-policy".equals(s) && i < 8) {
            int i1 = XmlUtils.readIntAttribute(xmlpullparser, "uid");
            int j1 = XmlUtils.readIntAttribute(xmlpullparser, "policy");
            int k1 = UserId.getAppId(i1);
            if(UserId.isApp(k1))
                setAppPolicyUnchecked(k1, j1, false);
            else
                Slog.w("NetworkPolicy", (new StringBuilder()).append("unable to apply policy to UID ").append(i1).append("; ignoring").toString());
        } else
        if("app-policy".equals(s) && i >= 8) {
            int k = XmlUtils.readIntAttribute(xmlpullparser, "appId");
            int l = XmlUtils.readIntAttribute(xmlpullparser, "policy");
            if(UserId.isApp(k))
                setAppPolicyUnchecked(k, l, false);
            else
                Slog.w("NetworkPolicy", (new StringBuilder()).append("unable to apply policy to appId ").append(k).append("; ignoring").toString());
        }
          goto _L4
_L26:
        l4 = -1L;
          goto _L27
_L18:
        l1;
        JVM INSTR tableswitch 1 3: default 636
    //                   1 642
    //                   2 642
    //                   3 642;
           goto _L28 _L29 _L29 _L29
_L33:
        if(true) goto _L31; else goto _L30
_L30:
_L35:
        if(true) goto _L33; else goto _L32
_L32:
_L37:
        if(true) goto _L35; else goto _L34
_L34:
_L39:
        if(true) goto _L37; else goto _L36
_L36:
_L41:
        if(true) goto _L39; else goto _L38
_L38:
_L43:
        if(true) goto _L41; else goto _L40
_L40:
_L45:
        if(true) goto _L43; else goto _L42
_L42:
_L47:
        if(true) goto _L45; else goto _L44
_L44:
_L49:
        if(true) goto _L47; else goto _L46
_L46:
_L51:
        if(true) goto _L49; else goto _L48
_L48:
_L53:
        if(true) goto _L51; else goto _L50
_L50:
_L55:
        if(true) goto _L53; else goto _L52
_L52:
        if(true) goto _L55; else goto _L54
_L54:
_L28:
        flag = false;
          goto _L31
_L29:
        flag = true;
          goto _L31
_L20:
        l5 = -1L;
          goto _L56
_L22:
        flag1 = false;
          goto _L57
    }

    private void removeInterfaceQuota(String s) {
        mNetworkManager.removeInterfaceQuota(s);
_L2:
        return;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Log.wtf("NetworkPolicy", "problem removing interface quota", illegalstateexception);
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void setAppPolicyUnchecked(int i, int j, boolean flag) {
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        getAppPolicy(i);
        mAppPolicy.put(i, j);
        updateRulesForAppLocked(i);
        if(flag)
            writePolicyLocked();
        return;
    }

    private void setInterfaceQuota(String s, long l) {
        mNetworkManager.setInterfaceQuota(s, l);
_L2:
        return;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Log.wtf("NetworkPolicy", "problem setting interface quota", illegalstateexception);
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void setNetworkTemplateEnabled(NetworkTemplate networktemplate, boolean flag) {
        TelephonyManager telephonymanager;
        Injector.setNetworkTemplateEnabled(this, networktemplate, flag);
        telephonymanager = TelephonyManager.from(mContext);
        networktemplate.getMatchRule();
        JVM INSTR tableswitch 1 5: default 52
    //                   1 63
    //                   2 63
    //                   3 63
    //                   4 99
    //                   5 108;
           goto _L1 _L2 _L2 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException("unexpected template");
_L2:
        if(telephonymanager.getSimState() == 5 && Objects.equal(telephonymanager.getSubscriberId(), networktemplate.getSubscriberId())) {
            setPolicyDataEnable(0, flag);
            setPolicyDataEnable(6, flag);
        }
_L6:
        return;
_L3:
        setPolicyDataEnable(1, flag);
        continue; /* Loop/switch isn't completed */
_L4:
        setPolicyDataEnable(9, flag);
        if(true) goto _L6; else goto _L5
_L5:
    }

    private void setPolicyDataEnable(int i, boolean flag) {
        mConnManager.setPolicyDataEnable(i, flag);
_L2:
        return;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void setUidNetworkRules(int i, boolean flag) {
        mNetworkManager.setUidNetworkRules(i, flag);
_L2:
        return;
        IllegalStateException illegalstateexception;
        illegalstateexception;
        Log.wtf("NetworkPolicy", "problem setting uid rules", illegalstateexception);
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void updateNetworkEnabledLocked() {
        long l = currentTimeMillis();
        do {
            for(Iterator iterator = mNetworkPolicy.values().iterator(); iterator.hasNext();) {
                NetworkPolicy networkpolicy = (NetworkPolicy)iterator.next();
                if(networkpolicy.limitBytes == -1L || !networkpolicy.hasCycle()) {
                    setNetworkTemplateEnabled(networkpolicy.template, true);
                } else {
                    long l1 = NetworkPolicyManager.computeLastCycleBoundary(l, networkpolicy);
                    boolean flag;
                    boolean flag1;
                    if(networkpolicy.isOverLimit(getTotalBytes(networkpolicy.template, l1, l)) && networkpolicy.lastLimitSnooze < l1)
                        flag = true;
                    else
                        flag = false;
                    if(!flag)
                        flag1 = true;
                    else
                        flag1 = false;
                    setNetworkTemplateEnabled(networkpolicy.template, flag1);
                }
            }

            return;
        } while(true);
    }

    private void updateNetworkRulesLocked() {
        NetworkState anetworkstate[];
        try {
            anetworkstate = mConnManager.getAllNetworkState();
            break MISSING_BLOCK_LABEL_10;
        }
        catch(RemoteException remoteexception) { }
_L2:
        return;
        HashMap hashmap = Maps.newHashMap();
        int i = anetworkstate.length;
        for(int j = 0; j < i; j++) {
            NetworkState networkstate = anetworkstate[j];
            if(networkstate.networkInfo.isConnected()) {
                String s2 = networkstate.linkProperties.getInterfaceName();
                hashmap.put(NetworkIdentity.buildNetworkIdentity(mContext, networkstate), s2);
            }
        }

        mNetworkRules.clear();
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = mNetworkPolicy.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            NetworkPolicy networkpolicy1 = (NetworkPolicy)iterator.next();
            arraylist.clear();
            Iterator iterator3 = hashmap.entrySet().iterator();
            do {
                if(!iterator3.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator3.next();
                NetworkIdentity networkidentity = (NetworkIdentity)entry.getKey();
                if(networkpolicy1.template.matches(networkidentity))
                    arraylist.add((String)entry.getValue());
            } while(true);
            if(arraylist.size() > 0) {
                String as2[] = (String[])arraylist.toArray(new String[arraylist.size()]);
                mNetworkRules.put(networkpolicy1, as2);
            }
        } while(true);
        long l = 0x7fffffffffffffffL;
        HashSet hashset = Sets.newHashSet();
        long l1 = currentTimeMillis();
        Iterator iterator1 = mNetworkRules.keySet().iterator();
        do {
            if(!iterator1.hasNext())
                break;
            NetworkPolicy networkpolicy = (NetworkPolicy)iterator1.next();
            String as1[] = (String[])mNetworkRules.get(networkpolicy);
            long l2;
            long l3;
            boolean flag;
            boolean flag1;
            if(networkpolicy.hasCycle()) {
                l2 = NetworkPolicyManager.computeLastCycleBoundary(l1, networkpolicy);
                l3 = getTotalBytes(networkpolicy.template, l2, l1);
            } else {
                l2 = 0x7fffffffffffffffL;
                l3 = 0L;
            }
            if(networkpolicy.warningBytes != -1L)
                flag = true;
            else
                flag = false;
            if(networkpolicy.limitBytes != -1L)
                flag1 = true;
            else
                flag1 = false;
            if(flag1 || networkpolicy.metered) {
                long l4;
                int k;
                if(!flag1)
                    l4 = 0x7fffffffffffffffL;
                else
                if(networkpolicy.lastLimitSnooze >= l2)
                    l4 = 0x7fffffffffffffffL;
                else
                    l4 = Math.max(1L, networkpolicy.limitBytes - l3);
                if(as1.length > 1)
                    Slog.w("NetworkPolicy", "shared quota unsupported; generating rule for each iface");
                k = as1.length;
                for(int i1 = 0; i1 < k; i1++) {
                    String s1 = as1[i1];
                    removeInterfaceQuota(s1);
                    setInterfaceQuota(s1, l4);
                    hashset.add(s1);
                }

            }
            if(flag && networkpolicy.warningBytes < l)
                l = networkpolicy.warningBytes;
            if(flag1 && networkpolicy.limitBytes < l)
                l = networkpolicy.limitBytes;
        } while(true);
        mHandler.obtainMessage(7, Long.valueOf(l)).sendToTarget();
        Iterator iterator2 = mMeteredIfaces.iterator();
        do {
            if(!iterator2.hasNext())
                break;
            String s = (String)iterator2.next();
            if(!hashset.contains(s))
                removeInterfaceQuota(s);
        } while(true);
        mMeteredIfaces = hashset;
        String as[] = (String[])mMeteredIfaces.toArray(new String[mMeteredIfaces.size()]);
        mHandler.obtainMessage(2, as).sendToTarget();
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void updateNotificationsLocked() {
        HashSet hashset = Sets.newHashSet();
        hashset.addAll(mActiveNotifs);
        mActiveNotifs.clear();
        long l = currentTimeMillis();
        Iterator iterator = mNetworkPolicy.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            NetworkPolicy networkpolicy = (NetworkPolicy)iterator.next();
            if(isTemplateRelevant(networkpolicy.template) && networkpolicy.hasCycle()) {
                long l1 = NetworkPolicyManager.computeLastCycleBoundary(l, networkpolicy);
                long l2 = getTotalBytes(networkpolicy.template, l1, l);
                if(networkpolicy.isOverLimit(l2)) {
                    if(networkpolicy.lastLimitSnooze >= l1) {
                        enqueueValidNotification(networkpolicy, 3, l2);
                    } else {
                        enqueueValidNotification(networkpolicy, 2, l2);
                        notifyOverLimitLocked(networkpolicy.template);
                    }
                } else {
                    notifyUnderLimitLocked(networkpolicy.template);
                    if(networkpolicy.isOverWarning(l2) && networkpolicy.lastWarningSnooze < l1)
                        enqueueValidNotification(networkpolicy, 1, l2);
                }
            }
        } while(true);
        if(mRestrictBackground)
            enqueueRestrictedNotification("NetworkPolicy:allowBackground");
        Iterator iterator1 = hashset.iterator();
        do {
            if(!iterator1.hasNext())
                break;
            String s = (String)iterator1.next();
            if(!mActiveNotifs.contains(s))
                cancelNotification(s);
        } while(true);
    }

    private void updateRulesForAppLocked(int i) {
        for(Iterator iterator = mContext.getPackageManager().getUsers().iterator(); iterator.hasNext(); updateRulesForUidLocked(UserId.getUid(((UserInfo)iterator.next()).id, i)));
    }

    private void updateRulesForRestrictBackgroundLocked() {
        for(Iterator iterator = mContext.getPackageManager().getInstalledApplications(0).iterator(); iterator.hasNext(); updateRulesForAppLocked(UserId.getAppId(((ApplicationInfo)iterator.next()).uid)));
        updateRulesForUidLocked(1013);
        updateRulesForUidLocked(1019);
    }

    private void updateRulesForScreenLocked() {
        int i = mUidForeground.size();
        for(int j = 0; j < i; j++)
            if(mUidForeground.valueAt(j))
                updateRulesForUidLocked(mUidForeground.keyAt(j));

    }

    private void updateRulesForUidLocked(int i) {
        if(isUidValidForRules(i)) {
            int j = getAppPolicy(UserId.getAppId(i));
            boolean flag = isUidForeground(i);
            int k = 0;
            if(!flag && (j & 1) != 0)
                k = 1;
            if(!flag && mRestrictBackground)
                k = 1;
            boolean flag1;
            if(k == 0)
                mUidRules.delete(i);
            else
                mUidRules.put(i, k);
            if((k & 1) != 0)
                flag1 = true;
            else
                flag1 = false;
            setUidNetworkRules(i, flag1);
            mHandler.obtainMessage(1, i, k).sendToTarget();
            try {
                mNetworkStats.setUidForeground(i, flag);
            }
            catch(RemoteException remoteexception) { }
        }
    }

    private void updateScreenOn() {
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        Exception exception;
        try {
            mScreenOn = mPowerManager.isScreenOn();
        }
        catch(RemoteException remoteexception) { }
        finally {
            obj;
        }
        updateRulesForScreenLocked();
        return;
        throw exception;
    }

    private void upgradeLegacyBackgroundData() {
        boolean flag = true;
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "background_data", flag) == flag)
            flag = false;
        mRestrictBackground = flag;
        if(mRestrictBackground) {
            Intent intent = new Intent("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED");
            mContext.sendBroadcast(intent);
        }
    }

    private void writePolicyLocked() {
        java.io.FileOutputStream fileoutputstream = null;
        IOException ioexception;
        FastXmlSerializer fastxmlserializer;
        fileoutputstream = mPolicyFile.startWrite();
        fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(fileoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.startTag(null, "policy-list");
        XmlUtils.writeIntAttribute(fastxmlserializer, "version", 9);
        XmlUtils.writeBooleanAttribute(fastxmlserializer, "restrictBackground", mRestrictBackground);
        for(Iterator iterator = mNetworkPolicy.values().iterator(); iterator.hasNext(); fastxmlserializer.endTag(null, "network-policy")) {
            NetworkPolicy networkpolicy = (NetworkPolicy)iterator.next();
            NetworkTemplate networktemplate = networkpolicy.template;
            fastxmlserializer.startTag(null, "network-policy");
            XmlUtils.writeIntAttribute(fastxmlserializer, "networkTemplate", networktemplate.getMatchRule());
            String s = networktemplate.getSubscriberId();
            if(s != null)
                fastxmlserializer.attribute(null, "subscriberId", s);
            String s1 = networktemplate.getNetworkId();
            if(s1 != null)
                fastxmlserializer.attribute(null, "networkId", s1);
            XmlUtils.writeIntAttribute(fastxmlserializer, "cycleDay", networkpolicy.cycleDay);
            fastxmlserializer.attribute(null, "cycleTimezone", networkpolicy.cycleTimezone);
            XmlUtils.writeLongAttribute(fastxmlserializer, "warningBytes", networkpolicy.warningBytes);
            XmlUtils.writeLongAttribute(fastxmlserializer, "limitBytes", networkpolicy.limitBytes);
            XmlUtils.writeLongAttribute(fastxmlserializer, "lastWarningSnooze", networkpolicy.lastWarningSnooze);
            XmlUtils.writeLongAttribute(fastxmlserializer, "lastLimitSnooze", networkpolicy.lastLimitSnooze);
            XmlUtils.writeBooleanAttribute(fastxmlserializer, "metered", networkpolicy.metered);
            XmlUtils.writeBooleanAttribute(fastxmlserializer, "inferred", networkpolicy.inferred);
        }

          goto _L1
_L3:
        return;
_L1:
        int i = 0;
        do {
            try {
label0:
                {
                    if(i < mAppPolicy.size()) {
                        int j = mAppPolicy.keyAt(i);
                        int k = mAppPolicy.valueAt(i);
                        if(k != 0) {
                            fastxmlserializer.startTag(null, "app-policy");
                            XmlUtils.writeIntAttribute(fastxmlserializer, "appId", j);
                            XmlUtils.writeIntAttribute(fastxmlserializer, "policy", k);
                            fastxmlserializer.endTag(null, "app-policy");
                        }
                        break label0;
                    }
                    fastxmlserializer.endTag(null, "policy-list");
                    fastxmlserializer.endDocument();
                    mPolicyFile.finishWrite(fileoutputstream);
                }
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) {
                if(fileoutputstream != null)
                    mPolicyFile.failWrite(fileoutputstream);
            }
            if(true)
                continue;
            i++;
        } while(true);
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void addIdleHandler(android.os.MessageQueue.IdleHandler idlehandler) {
        mHandler.getLooper().getQueue().addIdleHandler(idlehandler);
    }

    public void bindConnectivityManager(IConnectivityManager iconnectivitymanager) {
        mConnManager = (IConnectivityManager)Preconditions.checkNotNull(iconnectivitymanager, "missing IConnectivityManager");
    }

    public void bindNotificationManager(INotificationManager inotificationmanager) {
        mNotifManager = (INotificationManager)Preconditions.checkNotNull(inotificationmanager, "missing INotificationManager");
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        IndentingPrintWriter indentingprintwriter;
        HashSet hashset;
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "NetworkPolicy");
        indentingprintwriter = new IndentingPrintWriter(printwriter, "  ");
        hashset = new HashSet();
        int i = as.length;
        for(int j = 0; j < i; j++)
            hashset.add(as[j]);

        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        if(hashset.contains("--unsnooze")) {
            for(Iterator iterator1 = mNetworkPolicy.values().iterator(); iterator1.hasNext(); ((NetworkPolicy)iterator1.next()).clearSnooze());
            break MISSING_BLOCK_LABEL_132;
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
        updateNetworkEnabledLocked();
        updateNetworkRulesLocked();
        updateNotificationsLocked();
        writePolicyLocked();
        indentingprintwriter.println("Cleared snooze timestamps");
        obj;
        JVM INSTR monitorexit ;
          goto _L2
_L1:
        indentingprintwriter.print("Restrict background: ");
        indentingprintwriter.println(mRestrictBackground);
        indentingprintwriter.println("Network policies:");
        indentingprintwriter.increaseIndent();
        for(Iterator iterator = mNetworkPolicy.values().iterator(); iterator.hasNext(); indentingprintwriter.println(((NetworkPolicy)iterator.next()).toString()));
        indentingprintwriter.decreaseIndent();
        indentingprintwriter.println("Policy for apps:");
        indentingprintwriter.increaseIndent();
        int k = mAppPolicy.size();
        for(int l = 0; l < k; l++) {
            int j2 = mAppPolicy.keyAt(l);
            int k2 = mAppPolicy.valueAt(l);
            indentingprintwriter.print("appId=");
            indentingprintwriter.print(j2);
            indentingprintwriter.print(" policy=");
            NetworkPolicyManager.dumpPolicy(indentingprintwriter, k2);
            indentingprintwriter.println();
        }

        indentingprintwriter.decreaseIndent();
        SparseBooleanArray sparsebooleanarray = new SparseBooleanArray();
        collectKeys(mUidForeground, sparsebooleanarray);
        collectKeys(mUidRules, sparsebooleanarray);
        indentingprintwriter.println("Status for known UIDs:");
        indentingprintwriter.increaseIndent();
        int i1 = sparsebooleanarray.size();
        int j1 = 0;
        while(j1 < i1)  {
            int k1 = sparsebooleanarray.keyAt(j1);
            indentingprintwriter.print("UID=");
            indentingprintwriter.print(k1);
            indentingprintwriter.print(" foreground=");
            int l1 = mUidPidForeground.indexOfKey(k1);
            int i2;
            if(l1 < 0)
                indentingprintwriter.print("UNKNOWN");
            else
                dumpSparseBooleanArray(indentingprintwriter, (SparseBooleanArray)mUidPidForeground.valueAt(l1));
            indentingprintwriter.print(" rules=");
            i2 = mUidRules.indexOfKey(k1);
            if(i2 < 0)
                indentingprintwriter.print("UNKNOWN");
            else
                NetworkPolicyManager.dumpRules(indentingprintwriter, mUidRules.valueAt(i2));
            indentingprintwriter.println();
            j1++;
        }
        indentingprintwriter.decreaseIndent();
        obj;
        JVM INSTR monitorexit ;
_L2:
    }

    public int getAppPolicy(int i) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        int j = mAppPolicy.get(i, 0);
        return j;
    }

    public int[] getAppsWithPolicy(int i) {
        int ai[];
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        ai = new int[0];
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        int j = 0;
        do {
            if(j < mAppPolicy.size()) {
                int k = mAppPolicy.keyAt(j);
                if(mAppPolicy.valueAt(j) == i)
                    ai = ArrayUtils.appendInt(ai, k);
            } else {
                return ai;
            }
            j++;
        } while(true);
    }

    Context getContext() {
        return mContext;
    }

    public NetworkPolicy[] getNetworkPolicies() {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        mContext.enforceCallingOrSelfPermission("android.permission.READ_PHONE_STATE", "NetworkPolicy");
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        NetworkPolicy anetworkpolicy[] = (NetworkPolicy[])mNetworkPolicy.values().toArray(new NetworkPolicy[mNetworkPolicy.size()]);
        return anetworkpolicy;
    }

    public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState networkstate) {
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", "NetworkPolicy");
        l = Binder.clearCallingIdentity();
        NetworkQuotaInfo networkquotainfo = getNetworkQuotaInfoUnchecked(networkstate);
        Binder.restoreCallingIdentity(l);
        return networkquotainfo;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public boolean getRestrictBackground() {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = mRestrictBackground;
        return flag;
    }

    public boolean isNetworkMetered(NetworkState networkstate) {
        boolean flag;
        NetworkIdentity networkidentity;
        flag = true;
        networkidentity = NetworkIdentity.buildNetworkIdentity(mContext, networkstate);
        if(!networkidentity.getRoaming()) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        NetworkPolicy networkpolicy;
        synchronized(mRulesLock) {
            networkpolicy = findPolicyForNetworkLocked(networkidentity);
        }
        if(networkpolicy != null) {
            flag = networkpolicy.metered;
        } else {
            int i = networkstate.networkInfo.getType();
            if(!ConnectivityManager.isNetworkTypeMobile(i) && i != 6)
                flag = false;
        }
        continue; /* Loop/switch isn't completed */
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean isUidForeground(int i) {
        boolean flag;
        flag = false;
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        if(mUidForeground.get(i, false) && mScreenOn)
            flag = true;
        return flag;
    }

    public void registerListener(INetworkPolicyListener inetworkpolicylistener) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkPolicy");
        mListeners.register(inetworkpolicylistener);
    }

    public void setAppPolicy(int i, int j) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        if(!UserId.isApp(i)) {
            throw new IllegalArgumentException((new StringBuilder()).append("cannot apply policy to appId ").append(i).toString());
        } else {
            setAppPolicyUnchecked(i, j, true);
            return;
        }
    }

    public void setNetworkPolicies(NetworkPolicy anetworkpolicy[]) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        maybeRefreshTrustedTime();
        Object obj = mRulesLock;
        obj;
        JVM INSTR monitorenter ;
        mNetworkPolicy.clear();
        int i = anetworkpolicy.length;
        for(int j = 0; j < i; j++) {
            NetworkPolicy networkpolicy = anetworkpolicy[j];
            mNetworkPolicy.put(networkpolicy.template, networkpolicy);
        }

        updateNetworkEnabledLocked();
        updateNetworkRulesLocked();
        updateNotificationsLocked();
        writePolicyLocked();
        return;
    }

    public void setRestrictBackground(boolean flag) {
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        maybeRefreshTrustedTime();
        synchronized(mRulesLock) {
            mRestrictBackground = flag;
            updateRulesForRestrictBackgroundLocked();
            updateNotificationsLocked();
            writePolicyLocked();
        }
        Handler handler = mHandler;
        int i;
        if(flag)
            i = 1;
        else
            i = 0;
        handler.obtainMessage(6, i, 0).sendToTarget();
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void snoozeLimit(NetworkTemplate networktemplate) {
        long l;
        mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", "NetworkPolicy");
        l = Binder.clearCallingIdentity();
        performSnooze(networktemplate, 2);
        Binder.restoreCallingIdentity(l);
        return;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    public void systemReady() {
        if(!isBandwidthControlEnabled()) {
            Slog.w("NetworkPolicy", "bandwidth controls disabled, unable to enforce policy");
        } else {
            synchronized(mRulesLock) {
                readPolicyLocked();
                if(mRestrictBackground) {
                    updateRulesForRestrictBackgroundLocked();
                    updateNotificationsLocked();
                }
            }
            updateScreenOn();
            IntentFilter intentfilter;
            IntentFilter intentfilter1;
            IntentFilter intentfilter2;
            IntentFilter intentfilter3;
            IntentFilter intentfilter4;
            IntentFilter intentfilter5;
            IntentFilter intentfilter6;
            IntentFilter intentfilter7;
            try {
                mActivityManager.registerProcessObserver(mProcessObserver);
                mNetworkManager.registerObserver(mAlertObserver);
            }
            catch(RemoteException remoteexception) { }
            intentfilter = new IntentFilter();
            intentfilter.addAction("android.intent.action.SCREEN_ON");
            intentfilter.addAction("android.intent.action.SCREEN_OFF");
            mContext.registerReceiver(mScreenReceiver, intentfilter);
            intentfilter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
            mContext.registerReceiver(mConnReceiver, intentfilter1, "android.permission.CONNECTIVITY_INTERNAL", mHandler);
            intentfilter2 = new IntentFilter();
            intentfilter2.addAction("android.intent.action.PACKAGE_ADDED");
            intentfilter2.addAction("android.intent.action.UID_REMOVED");
            mContext.registerReceiver(mPackageReceiver, intentfilter2, null, mHandler);
            intentfilter3 = new IntentFilter("com.android.server.action.NETWORK_STATS_UPDATED");
            mContext.registerReceiver(mStatsReceiver, intentfilter3, "android.permission.READ_NETWORK_USAGE_HISTORY", mHandler);
            intentfilter4 = new IntentFilter("com.android.server.net.action.ALLOW_BACKGROUND");
            mContext.registerReceiver(mAllowReceiver, intentfilter4, "android.permission.MANAGE_NETWORK_POLICY", mHandler);
            intentfilter5 = new IntentFilter("com.android.server.net.action.SNOOZE_WARNING");
            mContext.registerReceiver(mSnoozeWarningReceiver, intentfilter5, "android.permission.MANAGE_NETWORK_POLICY", mHandler);
            intentfilter6 = new IntentFilter("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
            mContext.registerReceiver(mWifiConfigReceiver, intentfilter6, "android.permission.CONNECTIVITY_INTERNAL", mHandler);
            intentfilter7 = new IntentFilter("android.net.wifi.STATE_CHANGE");
            mContext.registerReceiver(mWifiStateReceiver, intentfilter7, "android.permission.CONNECTIVITY_INTERNAL", mHandler);
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void unregisterListener(INetworkPolicyListener inetworkpolicylistener) {
        mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkPolicy");
        mListeners.unregister(inetworkpolicylistener);
    }

    public static final String ACTION_ALLOW_BACKGROUND = "com.android.server.net.action.ALLOW_BACKGROUND";
    public static final String ACTION_SNOOZE_WARNING = "com.android.server.net.action.SNOOZE_WARNING";
    private static final String ATTR_APP_ID = "appId";
    private static final String ATTR_CYCLE_DAY = "cycleDay";
    private static final String ATTR_CYCLE_TIMEZONE = "cycleTimezone";
    private static final String ATTR_INFERRED = "inferred";
    private static final String ATTR_LAST_LIMIT_SNOOZE = "lastLimitSnooze";
    private static final String ATTR_LAST_SNOOZE = "lastSnooze";
    private static final String ATTR_LAST_WARNING_SNOOZE = "lastWarningSnooze";
    private static final String ATTR_LIMIT_BYTES = "limitBytes";
    private static final String ATTR_METERED = "metered";
    private static final String ATTR_NETWORK_ID = "networkId";
    private static final String ATTR_NETWORK_TEMPLATE = "networkTemplate";
    private static final String ATTR_POLICY = "policy";
    private static final String ATTR_RESTRICT_BACKGROUND = "restrictBackground";
    private static final String ATTR_SUBSCRIBER_ID = "subscriberId";
    private static final String ATTR_UID = "uid";
    private static final String ATTR_VERSION = "version";
    private static final String ATTR_WARNING_BYTES = "warningBytes";
    private static final boolean LOGD = false;
    private static final boolean LOGV = false;
    private static final int MSG_ADVISE_PERSIST_THRESHOLD = 7;
    private static final int MSG_FOREGROUND_ACTIVITIES_CHANGED = 3;
    private static final int MSG_LIMIT_REACHED = 5;
    private static final int MSG_METERED_IFACES_CHANGED = 2;
    private static final int MSG_PROCESS_DIED = 4;
    private static final int MSG_RESTRICT_BACKGROUND_CHANGED = 6;
    private static final int MSG_RULES_CHANGED = 1;
    private static final int MSG_SCREEN_ON_CHANGED = 8;
    private static final String TAG = "NetworkPolicy";
    private static final String TAG_ALLOW_BACKGROUND = "NetworkPolicy:allowBackground";
    private static final String TAG_APP_POLICY = "app-policy";
    private static final String TAG_NETWORK_POLICY = "network-policy";
    private static final String TAG_POLICY_LIST = "policy-list";
    private static final String TAG_UID_POLICY = "uid-policy";
    private static final long TIME_CACHE_MAX_AGE = 0x5265c00L;
    public static final int TYPE_LIMIT = 2;
    public static final int TYPE_LIMIT_SNOOZED = 3;
    public static final int TYPE_WARNING = 1;
    private static final int VERSION_ADDED_INFERRED = 7;
    private static final int VERSION_ADDED_METERED = 4;
    private static final int VERSION_ADDED_NETWORK_ID = 9;
    private static final int VERSION_ADDED_RESTRICT_BACKGROUND = 3;
    private static final int VERSION_ADDED_SNOOZE = 2;
    private static final int VERSION_ADDED_TIMEZONE = 6;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_LATEST = 9;
    private static final int VERSION_SPLIT_SNOOZE = 5;
    private static final int VERSION_SWITCH_APP_ID = 8;
    private HashSet mActiveNotifs;
    private final IActivityManager mActivityManager;
    private INetworkManagementEventObserver mAlertObserver = new NetworkAlertObserver() {

        public void limitReached(String s, String s1) {
            mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "NetworkPolicy");
            if(!"globalAlert".equals(s))
                mHandler.obtainMessage(5, s1).sendToTarget();
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private BroadcastReceiver mAllowReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            setRestrictBackground(false);
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private SparseIntArray mAppPolicy;
    private IConnectivityManager mConnManager;
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            maybeRefreshTrustedTime();
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            ensureActiveMobilePolicyLocked();
            updateNetworkEnabledLocked();
            updateNetworkRulesLocked();
            updateNotificationsLocked();
            return;
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private final Context mContext;
    private final Handler mHandler;
    private android.os.Handler.Callback mHandlerCallback = new android.os.Handler.Callback() {

        public boolean handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 1 8: default 52
        //                       1 56
        //                       2 144
        //                       3 230
        //                       4 346
        //                       5 424
        //                       6 513
        //                       7 603
        //                       8 640;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
            boolean flag1 = false;
_L11:
            return flag1;
_L2:
            int k2 = message.arg1;
            int l2 = message.arg2;
            int i3 = mListeners.beginBroadcast();
            int j3 = 0;
            while(j3 < i3)  {
                INetworkPolicyListener inetworkpolicylistener2 = (INetworkPolicyListener)mListeners.getBroadcastItem(j3);
                long l;
                long l1;
                boolean flag2;
                int i;
                int j;
                INetworkPolicyListener inetworkpolicylistener;
                String s;
                Object obj;
                Exception exception;
                boolean flag3;
                int k;
                int i1;
                Object obj1;
                SparseBooleanArray sparsebooleanarray;
                int j1;
                int k1;
                boolean flag4;
                Object obj2;
                SparseBooleanArray sparsebooleanarray1;
                String as[];
                int i2;
                int j2;
                INetworkPolicyListener inetworkpolicylistener1;
                if(inetworkpolicylistener2 != null)
                    try {
                        inetworkpolicylistener2.onUidRulesChanged(k2, l2);
                    }
                    catch(RemoteException remoteexception4) { }
                j3++;
            }
            mListeners.finishBroadcast();
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L3:
            as = (String[])(String[])message.obj;
            i2 = mListeners.beginBroadcast();
            j2 = 0;
            while(j2 < i2)  {
                inetworkpolicylistener1 = (INetworkPolicyListener)mListeners.getBroadcastItem(j2);
                if(inetworkpolicylistener1 != null)
                    try {
                        inetworkpolicylistener1.onMeteredIfacesChanged(as);
                    }
                    catch(RemoteException remoteexception3) { }
                j2++;
            }
            mListeners.finishBroadcast();
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L4:
            j1 = message.arg1;
            k1 = message.arg2;
            flag4 = ((Boolean)message.obj).booleanValue();
            obj2 = mRulesLock;
            obj2;
            JVM INSTR monitorenter ;
            sparsebooleanarray1 = (SparseBooleanArray)mUidPidForeground.get(k1);
            if(sparsebooleanarray1 == null) {
                sparsebooleanarray1 = new SparseBooleanArray(2);
                mUidPidForeground.put(k1, sparsebooleanarray1);
            }
            sparsebooleanarray1.put(j1, flag4);
            computeUidForegroundLocked(k1);
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L5:
            k = message.arg1;
            i1 = message.arg2;
            obj1 = mRulesLock;
            obj1;
            JVM INSTR monitorenter ;
            sparsebooleanarray = (SparseBooleanArray)mUidPidForeground.get(i1);
            if(sparsebooleanarray != null) {
                sparsebooleanarray.delete(k);
                computeUidForegroundLocked(i1);
            }
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L6:
            s = (String)message.obj;
            maybeRefreshTrustedTime();
            obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            flag3 = mMeteredIfaces.contains(s);
            if(flag3) {
                RemoteException remoteexception;
                RemoteException remoteexception1;
                try {
                    mNetworkStats.forceUpdate();
                }
                catch(RemoteException remoteexception2) { }
                finally {
                    obj;
                }
                updateNetworkEnabledLocked();
                updateNotificationsLocked();
            }
            obj;
            JVM INSTR monitorexit ;
            flag1 = true;
            continue; /* Loop/switch isn't completed */
            throw exception;
_L7:
            if(message.arg1 != 0)
                flag2 = true;
            else
                flag2 = false;
            i = mListeners.beginBroadcast();
            j = 0;
            while(j < i)  {
                inetworkpolicylistener = (INetworkPolicyListener)mListeners.getBroadcastItem(j);
                if(inetworkpolicylistener != null)
                    try {
                        inetworkpolicylistener.onRestrictBackgroundChanged(flag2);
                    }
                    // Misplaced declaration of an exception variable
                    catch(RemoteException remoteexception1) { }
                j++;
            }
            mListeners.finishBroadcast();
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L8:
            l = ((Long)message.obj).longValue();
            try {
                l1 = l / 1000L;
                mNetworkStats.advisePersistThreshold(l1);
            }
            // Misplaced declaration of an exception variable
            catch(RemoteException remoteexception) { }
            flag1 = true;
            continue; /* Loop/switch isn't completed */
_L9:
            updateScreenOn();
            flag1 = true;
            if(true) goto _L11; else goto _L10
_L10:
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private final HandlerThread mHandlerThread;
    private final RemoteCallbackList mListeners;
    private HashSet mMeteredIfaces;
    private final INetworkManagementService mNetworkManager;
    private HashMap mNetworkPolicy;
    private HashMap mNetworkRules;
    private final INetworkStatsService mNetworkStats;
    private INotificationManager mNotifManager;
    private HashSet mOverLimitNotified;
    private BroadcastReceiver mPackageReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            String s;
            int i;
            s = intent.getAction();
            i = UserId.getAppId(intent.getIntExtra("android.intent.extra.UID", 0));
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            if(!"android.intent.action.PACKAGE_ADDED".equals(s)) goto _L2; else goto _L1
_L1:
            updateRulesForAppLocked(i);
_L4:
            obj;
            JVM INSTR monitorexit ;
            return;
_L2:
            if("android.intent.action.UID_REMOVED".equals(s)) {
                mAppPolicy.delete(i);
                updateRulesForAppLocked(i);
                writePolicyLocked();
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private final AtomicFile mPolicyFile;
    private final IPowerManager mPowerManager;
    private IProcessObserver mProcessObserver = new android.app.IProcessObserver.Stub() {

        public void onForegroundActivitiesChanged(int i, int j, boolean flag1) {
            mHandler.obtainMessage(3, i, j, Boolean.valueOf(flag1)).sendToTarget();
        }

        public void onImportanceChanged(int i, int j, int k) {
        }

        public void onProcessDied(int i, int j) {
            mHandler.obtainMessage(4, i, j).sendToTarget();
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private volatile boolean mRestrictBackground;
    private final Object mRulesLock;
    private volatile boolean mScreenOn;
    private BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            mHandler.obtainMessage(8).sendToTarget();
            return;
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private BroadcastReceiver mSnoozeWarningReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            NetworkTemplate networktemplate = (NetworkTemplate)intent.getParcelableExtra("android.net.NETWORK_TEMPLATE");
            performSnooze(networktemplate, 1);
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private BroadcastReceiver mStatsReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            maybeRefreshTrustedTime();
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            updateNetworkEnabledLocked();
            updateNotificationsLocked();
            return;
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private final boolean mSuppressDefaultPolicy;
    private final TrustedTime mTime;
    private SparseBooleanArray mUidForeground;
    private SparseArray mUidPidForeground;
    private SparseIntArray mUidRules;
    private BroadcastReceiver mWifiConfigReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            NetworkTemplate networktemplate;
            if(intent.getIntExtra("changeReason", 0) != 1)
                break MISSING_BLOCK_LABEL_101;
            WifiConfiguration wificonfiguration = (WifiConfiguration)intent.getParcelableExtra("wifiConfiguration");
            if(wificonfiguration.SSID == null)
                break MISSING_BLOCK_LABEL_101;
            networktemplate = NetworkTemplate.buildTemplateWifi(WifiInfo.removeDoubleQuotes(wificonfiguration.SSID));
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            if(mNetworkPolicy.containsKey(networktemplate)) {
                mNetworkPolicy.remove(networktemplate);
                writePolicyLocked();
            }
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };
    private BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {

        public void onReceive(Context context1, Intent intent) {
            if(((NetworkInfo)intent.getParcelableExtra("networkInfo")).isConnected()) goto _L2; else goto _L1
_L1:
            return;
_L2:
            boolean flag1;
            NetworkTemplate networktemplate;
            WifiInfo wifiinfo = (WifiInfo)intent.getParcelableExtra("wifiInfo");
            flag1 = wifiinfo.getMeteredHint();
            networktemplate = NetworkTemplate.buildTemplateWifi(WifiInfo.removeDoubleQuotes(wifiinfo.getSSID()));
            Object obj = mRulesLock;
            obj;
            JVM INSTR monitorenter ;
            NetworkPolicy networkpolicy = (NetworkPolicy)mNetworkPolicy.get(networktemplate);
            if(networkpolicy != null || !flag1) goto _L4; else goto _L3
_L3:
            NetworkPolicy networkpolicy1 = new NetworkPolicy(networktemplate, -1, "UTC", -1L, -1L, -1L, -1L, flag1, true);
            addNetworkPolicyLocked(networkpolicy1);
_L6:
            obj;
            JVM INSTR monitorexit ;
              goto _L1
            Exception exception;
            exception;
            throw exception;
_L4:
            if(networkpolicy == null) goto _L6; else goto _L5
_L5:
            if(!networkpolicy.inferred) goto _L6; else goto _L7
_L7:
            networkpolicy.metered = flag1;
            updateNetworkRulesLocked();
              goto _L6
        }

        final NetworkPolicyManagerService this$0;

             {
                this$0 = NetworkPolicyManagerService.this;
                super();
            }
    };




















}
