// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.miui.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.ApnSetting;
import java.io.File;
import java.util.*;
import miui.net.FirewallManager;
import miui.util.CommandLineUtils;

public class FirewallService extends miui.net.IFirewall.Stub {

    private FirewallService(Context context) {
        mLastUsingMmsUids = new HashMap();
        mIfnames = new HashMap();
        mCurrentMmsIfname = null;
        mContext = context;
    }

    private String addQuoteMark(String s) {
        if(!s.startsWith("\"") || !s.endsWith("\"")) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        StringBuilder stringbuilder;
        int i;
        int j;
        stringbuilder = new StringBuilder();
        stringbuilder.append('"');
        i = s.length();
        j = 0;
_L8:
        char c;
        if(j >= i)
            break MISSING_BLOCK_LABEL_139;
        c = s.charAt(j);
        c;
        JVM INSTR lookupswitch 3: default 96
    //                   34: 109
    //                   36: 129
    //                   92: 119;
           goto _L3 _L4 _L5 _L6
_L5:
        break MISSING_BLOCK_LABEL_129;
_L4:
        break; /* Loop/switch isn't completed */
_L3:
        stringbuilder.append(c);
_L9:
        j++;
        if(true) goto _L8; else goto _L7
_L7:
        stringbuilder.append("\\\"");
          goto _L9
_L6:
        stringbuilder.append("\\\\");
          goto _L9
        stringbuilder.append("\\$");
          goto _L9
        stringbuilder.append('"');
        s = stringbuilder.toString();
        if(true) goto _L1; else goto _L10
_L10:
    }

    private void addWhiteListChain(String s, String s1, Iterable iterable) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(REMOVE_CHAIN_CMD.replaceAll("%0%", s));
        stringbuilder.append(ADD_CHAIN_FOR_REJECT_CMD.replaceAll("%0%", s).replaceAll("%1%", s1));
        if(iterable != null) {
            int i;
            for(Iterator iterator = iterable.iterator(); iterator.hasNext(); stringbuilder.append(INSERT_ACCEPT_RULE_TO_CHAIN_CMD.replaceAll("%0%", s).replaceAll("%1%", String.valueOf(i))))
                i = ((Integer)iterator.next()).intValue();

        }
        runCommands(stringbuilder.toString());
    }

    private void clearChains() {
        runCommands(CLEAR_ALL_MIUI_CHAIN_CMD);
    }

    private static String getAwkCommand() {
        String s;
        if(hasCommand("awk"))
            s = "awk";
        else
        if(hasCommand("busybox"))
            s = "busybox awk";
        else
            s = null;
        return s;
    }

    private static String getGrepCommand() {
        String s;
        if(hasCommand("grep"))
            s = "grep";
        else
        if(hasCommand("busybox"))
            s = "busybox grep";
        else
            s = null;
        return s;
    }

    private static String getIptablesCommand() {
        String s;
        if(hasCommand("iptables"))
            s = "iptables";
        else
            s = null;
        return s;
    }

    private static boolean hasCommand(String s) {
        boolean flag;
        if((new File("/system/xbin/", s)).exists() || (new File("/system/bin/", s)).exists())
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void insertWhiteListRule(String s, int i) {
        runCommands(INSERT_ACCEPT_RULE_TO_CHAIN_CMD.replaceAll("%0%", s).replaceAll("%1%", String.valueOf(i)));
    }

    private void removeChain(String s) {
        runCommands(REMOVE_CHAIN_CMD.replaceAll("%0%", s));
    }

    private void removeRule(String s, int i) {
        runCommands(REMOVE_RULE_OF_CHAIN_CMD.replaceAll("%0%", s).replaceAll("%1%", String.valueOf(i)));
    }

    private void runCommands(String s) {
        CommandLineUtils.run("root", (new StringBuilder()).append("sh -c ").append(addQuoteMark(s)).toString(), new Object[0]);
    }

    public static void setupService(Context context) {
        if(IPTABLES_CMD != null && GREP_CMD != null && AWK_CMD != null) {
            FirewallService firewallservice = new FirewallService(context);
            firewallservice.clearChains();
            ServiceManager.addService("miui.Firewall", firewallservice.asBinder());
        } else {
            Log.e("FirewallService", (new StringBuilder()).append("failed to setup service due to iptables=").append(IPTABLES_CMD).append(",grep=").append(GREP_CMD).append(",awk=").append(AWK_CMD).toString());
        }
    }

    public void addAccessControlPass(String s) {
        HashSet hashset = mAccessControlPassPackages;
        hashset;
        JVM INSTR monitorenter ;
        mAccessControlPassPackages.add(s);
        return;
    }

    public boolean checkAccessControlPass(String s) {
        HashSet hashset = mAccessControlPassPackages;
        hashset;
        JVM INSTR monitorenter ;
        boolean flag = mAccessControlPassPackages.contains(s);
        return flag;
    }

    public boolean getAlarmBootCompleted() {
        return mAlarmBootCompleted;
    }

    public void onDataConnected(int i, String s, String s1) {
        mIfnames.put(s, s1);
        if(ConnectivityManager.isNetworkTypeMobile(i) && android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "mobile_data", 1) == 0) {
            ApnSetting apnsetting = FirewallManager.decodeApnSetting(s);
            if(apnsetting != null && apnsetting.canHandleType("mms")) {
                mCurrentMmsIfname = s1;
                addWhiteListChain((new StringBuilder()).append("miui_").append(mCurrentMmsIfname).toString(), mCurrentMmsIfname, mLastUsingMmsUids.keySet());
            }
        }
    }

    public void onDataDisconnected(int i, String s) {
        String s1 = (String)mIfnames.remove(s);
        if(!TextUtils.isEmpty(s1)) {
            removeChain((new StringBuilder()).append("miui_").append(s1).toString());
            if(s1.equals(mCurrentMmsIfname)) {
                mCurrentMmsIfname = null;
                mLastUsingMmsUids.clear();
            }
        }
    }

    public void onStartUsingNetworkFeature(int i, int j, int k) {
        if(k == 2) {
            HashSet hashset = (HashSet)mLastUsingMmsUids.get(Integer.valueOf(i));
            boolean flag = false;
            if(hashset == null) {
                flag = true;
                hashset = new HashSet();
                mLastUsingMmsUids.put(Integer.valueOf(i), hashset);
            }
            hashset.add(Integer.valueOf(j));
            if(flag && !TextUtils.isEmpty(mCurrentMmsIfname))
                insertWhiteListRule((new StringBuilder()).append("miui_").append(mCurrentMmsIfname).toString(), i);
        }
    }

    public void onStopUsingNetworkFeature(int i, int j, int k) {
        if(k == 2) {
            HashSet hashset = (HashSet)mLastUsingMmsUids.get(Integer.valueOf(i));
            if(hashset != null) {
                hashset.remove(Integer.valueOf(j));
                if(hashset.size() == 0) {
                    mLastUsingMmsUids.remove(Integer.valueOf(i));
                    hashset = null;
                }
            }
            if(hashset == null && !TextUtils.isEmpty(mCurrentMmsIfname))
                removeRule((new StringBuilder()).append("miui_").append(mCurrentMmsIfname).toString(), i);
        }
    }

    public void removeAccessControlPass(String s) {
        HashSet hashset = mAccessControlPassPackages;
        hashset;
        JVM INSTR monitorenter ;
        if("*".equals(s))
            mAccessControlPassPackages.clear();
        else
            mAccessControlPassPackages.remove(s);
        return;
    }

    public void setAlarmBootCompleted() {
        mAlarmBootCompleted = true;
    }

    private static final String ADD_CHAIN_FOR_REJECT_CMD = (new StringBuilder()).append(IPTABLES_CMD).append(" -N %0%; ").append(IPTABLES_CMD).append(" -A %0% -j REJECT; ").append(IPTABLES_CMD).append(" -A OUTPUT -o %1% -j %0%; ").toString();
    private static final String AWK_CMD = getAwkCommand();
    private static final String CLEAR_ALL_MIUI_CHAIN_CMD = (new StringBuilder()).append("for chain in `").append(IPTABLES_CMD).append(" -L | ").append(GREP_CMD).append(" \"^Chain ").append("miui_").append("\" | ").append(AWK_CMD).append(" '{print $2}'`; do ").append(IPTABLES_CMD).append(" -D OUTPUT `").append(IPTABLES_CMD).append(" -S OUTPUT | ").append(AWK_CMD).append(" -v chain=${chain} '$6==chain {print NR-2}'`; ").append(IPTABLES_CMD).append(" -F $chain; ").append(IPTABLES_CMD).append(" -X $chain; ").append("done;").toString();
    private static final boolean DEBUG = false;
    private static final String GREP_CMD = getGrepCommand();
    private static final String INSERT_ACCEPT_RULE_TO_CHAIN_CMD = (new StringBuilder()).append(IPTABLES_CMD).append(" -I %0% -m owner --uid-owner %1% -j ACCEPT; ").toString();
    private static final String IPTABLES_CMD = getIptablesCommand();
    private static final String LOG_TAG = "FirewallService";
    private static final String MIUI_CHAIN_PREFIX = "miui_";
    private static final String REMOVE_CHAIN_CMD = (new StringBuilder()).append(IPTABLES_CMD).append(" -D OUTPUT `").append(IPTABLES_CMD).append(" -S OUTPUT | ").append(AWK_CMD).append(" '$6==\"%0%\" {print NR-1}'`; ").append(IPTABLES_CMD).append(" -F %0%; ").append(IPTABLES_CMD).append(" -X %0%; ").toString();
    private static final String REMOVE_RULE_OF_CHAIN_CMD = (new StringBuilder()).append(IPTABLES_CMD).append(" -D %0% `").append(IPTABLES_CMD).append(" -S %0% | ").append(AWK_CMD).append(" '$6==\"%1%\" {print NR-1}'`; ").toString();
    private final HashSet mAccessControlPassPackages = new HashSet();
    private boolean mAlarmBootCompleted;
    private Context mContext;
    private String mCurrentMmsIfname;
    private HashMap mIfnames;
    private HashMap mLastUsingMmsUids;

}
