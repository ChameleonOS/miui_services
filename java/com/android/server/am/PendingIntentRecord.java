// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.*;
import android.util.Slog;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.HashMap;

// Referenced classes of package com.android.server.am:
//            ActivityManagerService, ActivityRecord, ActivityStack

class PendingIntentRecord extends android.content.IIntentSender.Stub {
    static final class Key {

        public boolean equals(Object obj) {
            boolean flag = false;
            if(obj != null) goto _L2; else goto _L1
_L1:
            return flag;
_L2:
            Key key1 = (Key)obj;
            String s;
            if(type == key1.type && packageName.equals(key1.packageName) && activity == key1.activity && (who == key1.who || (who == null ? key1.who == null : who.equals(key1.who))) && requestCode == key1.requestCode && (requestIntent == key1.requestIntent || (requestIntent == null ? key1.requestIntent == null : requestIntent.filterEquals(key1.requestIntent))) && (requestResolvedType == key1.requestResolvedType || (requestResolvedType == null ? (s = key1.requestResolvedType) == null : requestResolvedType.equals(key1.requestResolvedType))) && flags == key1.flags)
                flag = true;
            continue; /* Loop/switch isn't completed */
            ClassCastException classcastexception;
            classcastexception;
            if(true) goto _L1; else goto _L3
_L3:
        }

        public int hashCode() {
            return hashCode;
        }

        public String toString() {
            StringBuilder stringbuilder = (new StringBuilder()).append("Key{").append(typeName()).append(" pkg=").append(packageName).append(" intent=");
            String s;
            if(requestIntent != null)
                s = requestIntent.toShortString(false, true, false, false);
            else
                s = "<null>";
            return stringbuilder.append(s).append(" flags=0x").append(Integer.toHexString(flags)).append("}").toString();
        }

        String typeName() {
            type;
            JVM INSTR tableswitch 1 4: default 36
        //                       1 52
        //                       2 46
        //                       3 64
        //                       4 58;
               goto _L1 _L2 _L3 _L4 _L5
_L1:
            String s = Integer.toString(type);
_L7:
            return s;
_L3:
            s = "startActivity";
            continue; /* Loop/switch isn't completed */
_L2:
            s = "broadcastIntent";
            continue; /* Loop/switch isn't completed */
_L5:
            s = "startService";
            continue; /* Loop/switch isn't completed */
_L4:
            s = "activityResult";
            if(true) goto _L7; else goto _L6
_L6:
        }

        private static final int ODD_PRIME_NUMBER = 37;
        final ActivityRecord activity;
        Intent allIntents[];
        String allResolvedTypes[];
        final int flags;
        final int hashCode;
        final Bundle options;
        final String packageName;
        final int requestCode;
        final Intent requestIntent;
        final String requestResolvedType;
        final int type;
        final String who;

        Key(int i, String s, ActivityRecord activityrecord, String s1, int j, Intent aintent[], String as[], 
                int k, Bundle bundle) {
            String s2 = null;
            super();
            type = i;
            packageName = s;
            activity = activityrecord;
            who = s1;
            requestCode = j;
            Intent intent;
            int l;
            if(aintent != null)
                intent = aintent[-1 + aintent.length];
            else
                intent = null;
            requestIntent = intent;
            if(as != null)
                s2 = as[-1 + as.length];
            requestResolvedType = s2;
            allIntents = aintent;
            allResolvedTypes = as;
            flags = k;
            options = bundle;
            l = j + 37 * (k + 851);
            if(s1 != null)
                l = l * 37 + s1.hashCode();
            if(activityrecord != null)
                l = l * 37 + activityrecord.hashCode();
            if(requestIntent != null)
                l = l * 37 + requestIntent.filterHashCode();
            if(requestResolvedType != null)
                l = l * 37 + requestResolvedType.hashCode();
            hashCode = i + 37 * (l * 37 + s.hashCode());
        }
    }


    PendingIntentRecord(ActivityManagerService activitymanagerservice, Key key1, int i) {
        sent = false;
        canceled = false;
        owner = activitymanagerservice;
        key = key1;
        uid = i;
    }

    public void completeFinalize() {
        ActivityManagerService activitymanagerservice = owner;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        if((WeakReference)owner.mIntentSenderRecords.get(key) == ref)
            owner.mIntentSenderRecords.remove(key);
        return;
    }

    void dump(PrintWriter printwriter, String s) {
        printwriter.print(s);
        printwriter.print("uid=");
        printwriter.print(uid);
        printwriter.print(" packageName=");
        printwriter.print(key.packageName);
        printwriter.print(" type=");
        printwriter.print(key.typeName());
        printwriter.print(" flags=0x");
        printwriter.println(Integer.toHexString(key.flags));
        if(key.activity != null || key.who != null) {
            printwriter.print(s);
            printwriter.print("activity=");
            printwriter.print(key.activity);
            printwriter.print(" who=");
            printwriter.println(key.who);
        }
        if(key.requestCode != 0 || key.requestResolvedType != null) {
            printwriter.print(s);
            printwriter.print("requestCode=");
            printwriter.print(key.requestCode);
            printwriter.print(" requestResolvedType=");
            printwriter.println(key.requestResolvedType);
        }
        if(key.requestIntent != null) {
            printwriter.print(s);
            printwriter.print("requestIntent=");
            printwriter.println(key.requestIntent.toShortString(false, true, true, true));
        }
        if(sent || canceled) {
            printwriter.print(s);
            printwriter.print("sent=");
            printwriter.print(sent);
            printwriter.print(" canceled=");
            printwriter.println(canceled);
        }
    }

    protected void finalize() throws Throwable {
        if(!canceled)
            owner.mHandler.sendMessage(owner.mHandler.obtainMessage(23, this));
        super.finalize();
        return;
        Exception exception;
        exception;
        super.finalize();
        throw exception;
    }

    public int send(int i, Intent intent, String s, IIntentReceiver iintentreceiver, String s1) {
        return sendInner(i, intent, s, iintentreceiver, s1, null, null, 0, 0, 0, null);
    }

    int sendInner(int i, Intent intent, String s, IIntentReceiver iintentreceiver, String s1, IBinder ibinder, String s2, 
            int j, int k, int l, Bundle bundle) {
        ActivityManagerService activitymanagerservice = owner;
        activitymanagerservice;
        JVM INSTR monitorenter ;
        if(canceled) goto _L2; else goto _L1
_L1:
        sent = true;
        if((0x40000000 & key.flags) != 0) {
            owner.cancelIntentSenderLocked(this, true);
            canceled = true;
        }
        if(key.requestIntent == null) goto _L4; else goto _L3
_L3:
        Intent intent1 = new Intent(key.requestIntent);
_L14:
        if(intent == null) goto _L6; else goto _L5
_L5:
        if((2 & intent1.fillIn(intent, key.flags)) == 0)
            s = key.requestResolvedType;
_L15:
        long l1;
        boolean flag;
        int i1 = k & -4;
        intent1.setFlags(l & i1 | intent1.getFlags() & ~i1);
        l1 = Binder.clearCallingIdentity();
        if(iintentreceiver == null)
            break MISSING_BLOCK_LABEL_685;
        flag = true;
_L23:
        int j1 = key.type;
        j1;
        JVM INSTR tableswitch 1 4: default 188
    //                   1 543
    //                   2 259
    //                   3 501
    //                   4 633;
           goto _L7 _L8 _L9 _L10 _L11
_L7:
        byte byte0;
        Exception exception;
        RuntimeException runtimeexception;
        RuntimeException runtimeexception1;
        ActivityManagerService activitymanagerservice1;
        String s3;
        int k1;
        boolean flag1;
        int i2;
        Bundle bundle1;
        RuntimeException runtimeexception2;
        Intent aintent[];
        String as[];
        if(flag)
            try {
                iintentreceiver.performReceive(new Intent(intent1), 0, null, null, false, false);
            }
            catch(RemoteException remoteexception) { }
            finally {
                activitymanagerservice;
            }
        Binder.restoreCallingIdentity(l1);
        byte0 = 0;
          goto _L12
_L4:
        intent1 = new Intent();
        if(true) goto _L14; else goto _L13
_L13:
        JVM INSTR monitorexit ;
        throw exception;
_L6:
        s = key.requestResolvedType;
          goto _L15
_L9:
        if(bundle != null) goto _L17; else goto _L16
_L16:
        bundle = key.options;
_L21:
        if(key.allIntents == null || key.allIntents.length <= 1) goto _L19; else goto _L18
_L18:
        aintent = new Intent[key.allIntents.length];
        as = new String[key.allIntents.length];
        System.arraycopy(key.allIntents, 0, aintent, 0, key.allIntents.length);
        if(key.allResolvedTypes != null)
            System.arraycopy(key.allResolvedTypes, 0, as, 0, key.allResolvedTypes.length);
        aintent[-1 + aintent.length] = intent1;
        as[-1 + as.length] = s;
        owner.startActivitiesInPackage(uid, aintent, as, ibinder, bundle);
          goto _L7
        runtimeexception2;
        Slog.w("ActivityManager", "Unable to send startActivity intent", runtimeexception2);
          goto _L7
_L17:
        if(key.options == null) goto _L21; else goto _L20
_L20:
        bundle1 = new Bundle(key.options);
        bundle1.putAll(bundle);
        bundle = bundle1;
          goto _L21
_L19:
        owner.startActivityInPackage(uid, intent1, s, ibinder, s2, j, 0, bundle);
          goto _L7
_L10:
        key.activity.stack.sendActivityResultLocked(-1, key.activity, key.who, key.requestCode, i, intent1);
          goto _L7
_L8:
        activitymanagerservice1 = owner;
        s3 = key.packageName;
        k1 = uid;
        if(iintentreceiver == null)
            break MISSING_BLOCK_LABEL_612;
        flag1 = true;
_L22:
        i2 = UserId.getUserId(uid);
        activitymanagerservice1.broadcastIntentInPackage(s3, k1, intent1, s, iintentreceiver, i, null, null, s1, flag1, false, i2);
        flag = false;
          goto _L7
        flag1 = false;
          goto _L22
        runtimeexception1;
        Slog.w("ActivityManager", "Unable to send startActivity intent", runtimeexception1);
          goto _L7
_L11:
        owner.startServiceInPackage(uid, intent1, s);
          goto _L7
        runtimeexception;
        Slog.w("ActivityManager", "Unable to send startService intent", runtimeexception);
          goto _L7
_L2:
        activitymanagerservice;
        JVM INSTR monitorexit ;
        byte0 = -6;
_L12:
        return byte0;
        flag = false;
          goto _L23
    }

    public String toString() {
        String s;
        if(stringName != null) {
            s = stringName;
        } else {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("PendingIntentRecord{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(' ');
            stringbuilder.append(key.packageName);
            stringbuilder.append(' ');
            stringbuilder.append(key.typeName());
            stringbuilder.append('}');
            s = stringbuilder.toString();
            stringName = s;
        }
        return s;
    }

    boolean canceled;
    final Key key;
    final ActivityManagerService owner;
    final WeakReference ref = new WeakReference(this);
    boolean sent;
    String stringName;
    final int uid;
}
