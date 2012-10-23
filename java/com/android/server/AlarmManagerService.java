// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.net.Uri;
import android.os.*;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

class AlarmManagerService extends android.app.IAlarmManager.Stub {
    class UninstallReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            String s;
            String as[];
            s = intent.getAction();
            as = null;
            if(!"android.intent.action.QUERY_PACKAGE_RESTART".equals(s)) goto _L2; else goto _L1
_L1:
            String as2[];
            int k;
            int l;
            as2 = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
            k = as2.length;
            l = 0;
_L14:
            if(l >= k) goto _L4; else goto _L3
_L3:
            String s3 = as2[l];
            if(!lookForPackageLocked(s3)) goto _L6; else goto _L5
_L5:
            setResultCode(-1);
              goto _L4
            Exception exception;
            exception;
            throw exception;
_L2:
            if(!"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(s)) goto _L8; else goto _L7
_L7:
            as = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
_L12:
            if(as != null && as.length > 0) {
                String as1[] = as;
                int i = as1.length;
                for(int j = 0; j < i; j++) {
                    String s2 = as1[j];
                    removeLocked(s2);
                    mBroadcastStats.remove(s2);
                }

            }
            break; /* Loop/switch isn't completed */
_L8:
            if(!"android.intent.action.PACKAGE_REMOVED".equals(s) || !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) goto _L10; else goto _L9
_L9:
            obj;
            JVM INSTR monitorexit ;
              goto _L4
_L10:
            Uri uri = intent.getData();
            if(uri != null) {
                String s1 = uri.getSchemeSpecificPart();
                if(s1 != null) {
                    as = new String[1];
                    as[0] = s1;
                }
            }
            if(true) goto _L12; else goto _L11
_L11:
            obj;
            JVM INSTR monitorexit ;
_L4:
            return;
_L6:
            l++;
            if(true) goto _L14; else goto _L13
_L13:
        }

        final AlarmManagerService this$0;

        public UninstallReceiver() {
            this$0 = AlarmManagerService.this;
            super();
            IntentFilter intentfilter = new IntentFilter();
            intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentfilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            intentfilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
            intentfilter.addDataScheme("package");
            mContext.registerReceiver(this, intentfilter);
            IntentFilter intentfilter1 = new IntentFilter();
            intentfilter1.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            mContext.registerReceiver(this, intentfilter1);
        }
    }

    class ClockReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if(!intent.getAction().equals("android.intent.action.TIME_TICK")) goto _L2; else goto _L1
_L1:
            scheduleTimeTickEvent();
_L4:
            return;
_L2:
            if(intent.getAction().equals("android.intent.action.DATE_CHANGED")) {
                int i = TimeZone.getTimeZone(SystemProperties.get("persist.sys.timezone")).getOffset(System.currentTimeMillis());
                setKernelTimezone(mDescriptor, -(i / 60000));
                scheduleDateChangedEvent();
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public void scheduleDateChangedEvent() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(10, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.add(5, 1);
            set(1, calendar.getTimeInMillis(), mDateChangeSender);
        }

        public void scheduleTimeTickEvent() {
            Calendar calendar = Calendar.getInstance();
            long l = System.currentTimeMillis();
            calendar.setTimeInMillis(l);
            calendar.add(12, 1);
            calendar.set(13, 0);
            calendar.set(14, 0);
            long l1 = calendar.getTimeInMillis() - l;
            set(3, l1 + SystemClock.elapsedRealtime(), mTimeTickSender);
        }

        final AlarmManagerService this$0;

        public ClockReceiver() {
            this$0 = AlarmManagerService.this;
            super();
            IntentFilter intentfilter = new IntentFilter();
            intentfilter.addAction("android.intent.action.TIME_TICK");
            intentfilter.addAction("android.intent.action.DATE_CHANGED");
            mContext.registerReceiver(this, intentfilter);
        }
    }

    private class AlarmHandler extends Handler {

        public void handleMessage(Message message) {
            if(message.what == 1) {
                ArrayList arraylist = new ArrayList();
                synchronized(mLock) {
                    long l = System.currentTimeMillis();
                    triggerAlarmsLocked(mRtcWakeupAlarms, arraylist, l);
                    triggerAlarmsLocked(mRtcAlarms, arraylist, l);
                    triggerAlarmsLocked(mElapsedRealtimeWakeupAlarms, arraylist, l);
                    triggerAlarmsLocked(mElapsedRealtimeAlarms, arraylist, l);
                }
                Iterator iterator = arraylist.iterator();
                do {
                    if(!iterator.hasNext())
                        break;
                    Alarm alarm = (Alarm)iterator.next();
                    try {
                        alarm.operation.send();
                    }
                    catch(android.app.PendingIntent.CanceledException canceledexception) {
                        if(alarm.repeatInterval > 0L)
                            remove(alarm.operation);
                    }
                } while(true);
            }
            break MISSING_BLOCK_LABEL_174;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public static final int ALARM_EVENT = 1;
        public static final int DATE_CHANGE_EVENT = 3;
        public static final int MINUTE_CHANGE_EVENT = 2;
        final AlarmManagerService this$0;

        public AlarmHandler() {
            this$0 = AlarmManagerService.this;
            super();
        }
    }

    private class AlarmThread extends Thread {

        public void run() {
_L5:
            int i;
            ArrayList arraylist;
            i = waitForAlarm(mDescriptor);
            arraylist = new ArrayList();
            if((0x10000 & i) != 0) {
                remove(mTimeTickSender);
                mClockReceiver.scheduleTimeTickEvent();
                Intent intent = new Intent("android.intent.action.TIME_SET");
                intent.addFlags(0x28000000);
                mContext.sendBroadcast(intent);
            }
            Object obj = mLock;
            obj;
            JVM INSTR monitorenter ;
            long l1;
            Iterator iterator;
            long l = System.currentTimeMillis();
            l1 = SystemClock.elapsedRealtime();
            if((i & 1) != 0)
                triggerAlarmsLocked(mRtcWakeupAlarms, arraylist, l);
            if((i & 2) != 0)
                triggerAlarmsLocked(mRtcAlarms, arraylist, l);
            if((i & 4) != 0)
                triggerAlarmsLocked(mElapsedRealtimeWakeupAlarms, arraylist, l1);
            if((i & 8) != 0)
                triggerAlarmsLocked(mElapsedRealtimeAlarms, arraylist, l1);
            iterator = arraylist.iterator();
_L3:
            Alarm alarm;
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_458;
            alarm = (Alarm)iterator.next();
            BroadcastStats broadcaststats;
            alarm.operation.send(mContext, 0, AlarmManagerService.mBackgroundIntent.putExtra("android.intent.extra.ALARM_COUNT", alarm.count), mResultReceiver, mHandler);
            if(mBroadcastRefCount == 0) {
                setWakelockWorkSource(alarm.operation);
                mWakeLock.acquire();
            }
            mInFlight.add(alarm.operation);
            int i = ((Alarm) (this)).count;
            broadcaststats = getStatsLocked(alarm.operation);
            if(broadcaststats.nesting != 0) goto _L2; else goto _L1
_L1:
            broadcaststats.startTime = l1;
_L4:
            if(alarm.type == 2 || alarm.type == 0) {
                broadcaststats.numWakeup = 1 + broadcaststats.numWakeup;
                ActivityManagerNative.noteWakeupAlarm(alarm.operation);
            }
              goto _L3
            android.app.PendingIntent.CanceledException canceledexception;
            canceledexception;
            if(alarm.repeatInterval > 0L)
                remove(alarm.operation);
              goto _L3
            Exception exception;
            exception;
            throw exception;
_L2:
            broadcaststats.nesting = 1 + broadcaststats.nesting;
              goto _L4
            RuntimeException runtimeexception;
            runtimeexception;
            Slog.w("AlarmManager", "Failure sending alarm.", runtimeexception);
              goto _L3
            obj;
            JVM INSTR monitorexit ;
              goto _L5
        }

        final AlarmManagerService this$0;

        public AlarmThread() {
            this$0 = AlarmManagerService.this;
            super("AlarmManager");
        }
    }

    private static class Alarm {

        public void dump(PrintWriter printwriter, String s, long l) {
            printwriter.print(s);
            printwriter.print("type=");
            printwriter.print(type);
            printwriter.print(" when=");
            TimeUtils.formatDuration(when, l, printwriter);
            printwriter.print(" repeatInterval=");
            printwriter.print(repeatInterval);
            printwriter.print(" count=");
            printwriter.println(count);
            printwriter.print(s);
            printwriter.print("operation=");
            printwriter.println(operation);
        }

        public String toString() {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("Alarm{");
            stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringbuilder.append(" type ");
            stringbuilder.append(type);
            stringbuilder.append(" ");
            stringbuilder.append(operation.getTargetPackage());
            stringbuilder.append('}');
            return stringbuilder.toString();
        }

        public int count;
        public PendingIntent operation;
        public long repeatInterval;
        public int type;
        public long when;

        public Alarm() {
            when = 0L;
            repeatInterval = 0L;
            operation = null;
        }
    }

    public static class IncreasingTimeOrder
        implements Comparator {

        public int compare(Alarm alarm, Alarm alarm1) {
            long l = alarm.when;
            long l1 = alarm1.when;
            int i;
            if(l - l1 > 0L)
                i = 1;
            else
            if(l - l1 < 0L)
                i = -1;
            else
                i = 0;
            return i;
        }

        public volatile int compare(Object obj, Object obj1) {
            return compare((Alarm)obj, (Alarm)obj1);
        }

        public IncreasingTimeOrder() {
        }
    }

    private static final class BroadcastStats {

        long aggregateTime;
        HashMap filterStats;
        int nesting;
        int numWakeup;
        long startTime;

        private BroadcastStats() {
            filterStats = new HashMap();
        }

    }

    private static final class FilterStats {

        int count;

        private FilterStats() {
        }

    }


    public AlarmManagerService(Context context) {
        mLock = new Object();
        mBroadcastRefCount = 0;
        mInFlight = new LinkedList();
        mContext = context;
        mDescriptor = init();
        String s = SystemProperties.get("persist.sys.timezone");
        if(s != null)
            setTimeZone(s);
        mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "AlarmManager");
        mTimeTickSender = PendingIntent.getBroadcast(context, 0, (new Intent("android.intent.action.TIME_TICK")).addFlags(0x40000000), 0);
        Intent intent = new Intent("android.intent.action.DATE_CHANGED");
        intent.addFlags(0x20000000);
        mDateChangeSender = PendingIntent.getBroadcast(context, 0, intent, 0);
        mClockReceiver = new ClockReceiver();
        mClockReceiver.scheduleTimeTickEvent();
        mClockReceiver.scheduleDateChangedEvent();
        mUninstallReceiver = new UninstallReceiver();
        if(mDescriptor != -1)
            mWaitThread.start();
        else
            Slog.w("AlarmManager", "Failed to open alarm driver. Falling back to a handler.");
    }

    private int addAlarmLocked(Alarm alarm) {
        ArrayList arraylist = getAlarmList(alarm.type);
        int i = Collections.binarySearch(arraylist, alarm, mIncreasingTimeOrder);
        if(i < 0)
            i = -1 + (0 - i);
        arraylist.add(i, alarm);
        return i;
    }

    private native void close(int i);

    private static final void dumpAlarmList(PrintWriter printwriter, ArrayList arraylist, String s, String s1, long l) {
        for(int i = -1 + arraylist.size(); i >= 0; i--) {
            Alarm alarm = (Alarm)arraylist.get(i);
            printwriter.print(s);
            printwriter.print(s1);
            printwriter.print(" #");
            printwriter.print(i);
            printwriter.print(": ");
            printwriter.println(alarm);
            alarm.dump(printwriter, (new StringBuilder()).append(s).append("  ").toString(), l);
        }

    }

    private ArrayList getAlarmList(int i) {
        i;
        JVM INSTR tableswitch 0 3: default 32
    //                   0 36
    //                   1 44
    //                   2 52
    //                   3 60;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        ArrayList arraylist = null;
_L7:
        return arraylist;
_L2:
        arraylist = mRtcWakeupAlarms;
        continue; /* Loop/switch isn't completed */
_L3:
        arraylist = mRtcAlarms;
        continue; /* Loop/switch isn't completed */
_L4:
        arraylist = mElapsedRealtimeWakeupAlarms;
        continue; /* Loop/switch isn't completed */
_L5:
        arraylist = mElapsedRealtimeAlarms;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private final BroadcastStats getStatsLocked(PendingIntent pendingintent) {
        String s = pendingintent.getTargetPackage();
        BroadcastStats broadcaststats = (BroadcastStats)mBroadcastStats.get(s);
        if(broadcaststats == null) {
            broadcaststats = new BroadcastStats();
            mBroadcastStats.put(s, broadcaststats);
        }
        return broadcaststats;
    }

    private native int init();

    private boolean lookForPackageLocked(ArrayList arraylist, String s) {
        int i = -1 + arraylist.size();
_L3:
        if(i < 0)
            break MISSING_BLOCK_LABEL_45;
        if(!((Alarm)arraylist.get(i)).operation.getTargetPackage().equals(s)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        i--;
          goto _L3
        flag = false;
          goto _L4
    }

    private void removeLocked(ArrayList arraylist, PendingIntent pendingintent) {
        if(arraylist.size() > 0) {
            Iterator iterator = arraylist.iterator();
            while(iterator.hasNext()) 
                if(((Alarm)iterator.next()).operation.equals(pendingintent))
                    iterator.remove();
        }
    }

    private void removeLocked(ArrayList arraylist, String s) {
        if(arraylist.size() > 0) {
            Iterator iterator = arraylist.iterator();
            while(iterator.hasNext()) 
                if(((Alarm)iterator.next()).operation.getTargetPackage().equals(s))
                    iterator.remove();
        }
    }

    private native void set(int i, int j, long l, long l1);

    private native int setKernelTimezone(int i, int j);

    private void setLocked(Alarm alarm) {
        if(mDescriptor != -1) {
            long l;
            long l1;
            if(alarm.when < 0L) {
                l = 0L;
                l1 = 0L;
            } else {
                l = alarm.when / 1000L;
                l1 = 1000L * (1000L * (alarm.when % 1000L));
            }
            set(mDescriptor, alarm.type, l, l1);
        } else {
            Message message = Message.obtain();
            message.what = 1;
            mHandler.removeMessages(1);
            mHandler.sendMessageAtTime(message, alarm.when);
        }
    }

    private void triggerAlarmsLocked(ArrayList arraylist, ArrayList arraylist1, long l) {
        Iterator iterator = arraylist.iterator();
        ArrayList arraylist2 = new ArrayList();
label0:
        do {
            Alarm alarm1;
label1:
            {
                if(iterator.hasNext()) {
                    alarm1 = (Alarm)iterator.next();
                    if(alarm1.when <= l)
                        break label1;
                }
                Alarm alarm;
                for(Iterator iterator1 = arraylist2.iterator(); iterator1.hasNext(); addAlarmLocked(alarm)) {
                    alarm = (Alarm)iterator1.next();
                    alarm.when = alarm.when + (long)alarm.count * alarm.repeatInterval;
                }

                break label0;
            }
            alarm1.count = 1;
            if(alarm1.repeatInterval > 0L)
                alarm1.count = (int)((long)alarm1.count + (l - alarm1.when) / alarm1.repeatInterval);
            arraylist1.add(alarm1);
            iterator.remove();
            if(alarm1.repeatInterval > 0L)
                arraylist2.add(alarm1);
        } while(true);
        if(arraylist.size() > 0)
            setLocked((Alarm)arraylist.get(0));
    }

    private native int waitForAlarm(int i);

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump AlarmManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L3:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        printwriter.println("Current Alarm Manager state:");
        if(mRtcWakeupAlarms.size() > 0 || mRtcAlarms.size() > 0) {
            long l = System.currentTimeMillis();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            printwriter.println(" ");
            printwriter.print("  Realtime wakeup (now=");
            printwriter.print(simpledateformat.format(new Date(l)));
            printwriter.println("):");
            if(mRtcWakeupAlarms.size() > 0)
                dumpAlarmList(printwriter, mRtcWakeupAlarms, "  ", "RTC_WAKEUP", l);
            if(mRtcAlarms.size() > 0)
                dumpAlarmList(printwriter, mRtcAlarms, "  ", "RTC", l);
        }
        if(mElapsedRealtimeWakeupAlarms.size() > 0 || mElapsedRealtimeAlarms.size() > 0) {
            long l1 = SystemClock.elapsedRealtime();
            printwriter.println(" ");
            printwriter.print("  Elapsed realtime wakeup (now=");
            TimeUtils.formatDuration(l1, printwriter);
            printwriter.println("):");
            if(mElapsedRealtimeWakeupAlarms.size() > 0)
                dumpAlarmList(printwriter, mElapsedRealtimeWakeupAlarms, "  ", "ELAPSED_WAKEUP", l1);
            if(mElapsedRealtimeAlarms.size() > 0)
                dumpAlarmList(printwriter, mElapsedRealtimeAlarms, "  ", "ELAPSED", l1);
        }
        printwriter.println(" ");
        printwriter.print("  Broadcast ref count: ");
        printwriter.println(mBroadcastRefCount);
        printwriter.println(" ");
        printwriter.println("  Alarm Stats:");
        for(Iterator iterator = mBroadcastStats.entrySet().iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            BroadcastStats broadcaststats = (BroadcastStats)entry.getValue();
            printwriter.print("  ");
            printwriter.println((String)entry.getKey());
            printwriter.print("    ");
            printwriter.print(broadcaststats.aggregateTime);
            printwriter.print("ms running, ");
            printwriter.print(broadcaststats.numWakeup);
            printwriter.println(" wakeups");
            Iterator iterator1 = broadcaststats.filterStats.entrySet().iterator();
            while(iterator1.hasNext())  {
                java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator1.next();
                printwriter.print("    ");
                printwriter.print(((FilterStats)entry1.getValue()).count);
                printwriter.print(" alarms: ");
                printwriter.println(((android.content.Intent.FilterComparison)entry1.getKey()).getIntent().toShortString(false, true, false, true));
            }
        }

        break MISSING_BLOCK_LABEL_547;
        Exception exception;
        exception;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
          goto _L3
    }

    protected void finalize() throws Throwable {
        close(mDescriptor);
        super.finalize();
        return;
        Exception exception;
        exception;
        super.finalize();
        throw exception;
    }

    public boolean lookForPackageLocked(String s) {
        boolean flag;
        if(lookForPackageLocked(mRtcWakeupAlarms, s) || lookForPackageLocked(mRtcAlarms, s) || lookForPackageLocked(mElapsedRealtimeWakeupAlarms, s) || lookForPackageLocked(mElapsedRealtimeAlarms, s))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void remove(PendingIntent pendingintent) {
        if(pendingintent != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        removeLocked(pendingintent);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void removeLocked(PendingIntent pendingintent) {
        removeLocked(mRtcWakeupAlarms, pendingintent);
        removeLocked(mRtcAlarms, pendingintent);
        removeLocked(mElapsedRealtimeWakeupAlarms, pendingintent);
        removeLocked(mElapsedRealtimeAlarms, pendingintent);
    }

    public void removeLocked(String s) {
        removeLocked(mRtcWakeupAlarms, s);
        removeLocked(mRtcAlarms, s);
        removeLocked(mElapsedRealtimeWakeupAlarms, s);
        removeLocked(mElapsedRealtimeAlarms, s);
    }

    public void set(int i, long l, PendingIntent pendingintent) {
        setRepeating(i, l, 0L, pendingintent);
    }

    public void setInexactRepeating(int i, long l, long l1, PendingIntent pendingintent) {
        if(pendingintent == null)
            Slog.w("AlarmManager", "setInexactRepeating ignored because there is no intent");
        else
        if(l1 <= 0L)
            Slog.w("AlarmManager", (new StringBuilder()).append("setInexactRepeating ignored because interval ").append(l1).append(" is invalid").toString());
        else
        if(l1 % 0xdbba0L != 0L) {
            setRepeating(i, l, l1, pendingintent);
        } else {
            boolean flag;
            long l2;
            long l3;
            long l4;
            if(i == 1 || i == 0)
                flag = true;
            else
                flag = false;
            if(flag)
                l2 = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            else
                l2 = 0L;
            l3 = (l - l2) % 0xdbba0L;
            if(l3 != 0L)
                l4 = 0xdbba0L + (l - l3);
            else
                l4 = l;
            setRepeating(i, l4, l1, pendingintent);
        }
    }

    public void setRepeating(int i, long l, long l1, PendingIntent pendingintent) {
        if(pendingintent != null) goto _L2; else goto _L1
_L1:
        Slog.w("AlarmManager", "set/setRepeating ignored because there is no intent");
_L4:
        return;
_L2:
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        Alarm alarm = new Alarm();
        alarm.type = i;
        alarm.when = l;
        alarm.repeatInterval = l1;
        alarm.operation = pendingintent;
        removeLocked(pendingintent);
        if(addAlarmLocked(alarm) == 0)
            setLocked(alarm);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setTime(long l) {
        mContext.enforceCallingOrSelfPermission("android.permission.SET_TIME", "setTime");
        SystemClock.setCurrentTimeMillis(l);
    }

    public void setTimeZone(String s) {
        mContext.enforceCallingOrSelfPermission("android.permission.SET_TIME_ZONE", "setTimeZone");
        if(!TextUtils.isEmpty(s)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        TimeZone timezone = TimeZone.getTimeZone(s);
        boolean flag = false;
        this;
        JVM INSTR monitorenter ;
        String s1 = SystemProperties.get("persist.sys.timezone");
        if(s1 == null || !s1.equals(timezone.getID())) {
            flag = true;
            SystemProperties.set("persist.sys.timezone", timezone.getID());
        }
        int i = timezone.getOffset(System.currentTimeMillis());
        setKernelTimezone(mDescriptor, -(i / 60000));
        this;
        JVM INSTR monitorexit ;
        TimeZone.setDefault(null);
        if(flag) {
            Intent intent = new Intent("android.intent.action.TIMEZONE_CHANGED");
            intent.addFlags(0x20000000);
            intent.putExtra("time-zone", timezone.getID());
            mContext.sendBroadcast(intent);
        }
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void setWakelockWorkSource(PendingIntent pendingintent) {
        int i = ActivityManagerNative.getDefault().getUidForIntentSender(pendingintent.getTarget());
        if(i < 0)
            break MISSING_BLOCK_LABEL_34;
        mWakeLock.setWorkSource(new WorkSource(i));
_L1:
        return;
        Exception exception;
        exception;
        mWakeLock.setWorkSource(null);
          goto _L1
    }

    public long timeToNextAlarm() {
        long l = 0x7fffffffffffffffL;
        Object obj = mLock;
        obj;
        JVM INSTR monitorenter ;
        int i = 0;
        do {
            if(i <= 3) {
                ArrayList arraylist = getAlarmList(i);
                if(arraylist.size() > 0) {
                    Alarm alarm = (Alarm)arraylist.get(0);
                    if(alarm.when < l)
                        l = alarm.when;
                }
            } else {
                return l;
            }
            i++;
        } while(true);
    }

    private static final int ALARM_EVENT = 1;
    private static final String ClockReceiver_TAG = "ClockReceiver";
    private static final int ELAPSED_REALTIME_MASK = 8;
    private static final int ELAPSED_REALTIME_WAKEUP_MASK = 4;
    private static final long LATE_ALARM_THRESHOLD = 10000L;
    private static final long QUANTUM = 0xdbba0L;
    private static final int RTC_MASK = 2;
    private static final int RTC_WAKEUP_MASK = 1;
    private static final String TAG = "AlarmManager";
    private static final String TIMEZONE_PROPERTY = "persist.sys.timezone";
    private static final int TIME_CHANGED_MASK = 0x10000;
    private static final boolean localLOGV;
    private static final Intent mBackgroundIntent = (new Intent()).addFlags(4);
    private int mBroadcastRefCount;
    private final HashMap mBroadcastStats = new HashMap();
    private ClockReceiver mClockReceiver;
    private final Context mContext;
    private final PendingIntent mDateChangeSender;
    private int mDescriptor;
    private final ArrayList mElapsedRealtimeAlarms = new ArrayList();
    private final ArrayList mElapsedRealtimeWakeupAlarms = new ArrayList();
    private final AlarmHandler mHandler = new AlarmHandler();
    private LinkedList mInFlight;
    private final IncreasingTimeOrder mIncreasingTimeOrder = new IncreasingTimeOrder();
    private Object mLock;
    private final ResultReceiver mResultReceiver = new ResultReceiver();
    private final ArrayList mRtcAlarms = new ArrayList();
    private final ArrayList mRtcWakeupAlarms = new ArrayList();
    private final PendingIntent mTimeTickSender;
    private UninstallReceiver mUninstallReceiver;
    private final AlarmThread mWaitThread = new AlarmThread();
    private android.os.PowerManager.WakeLock mWakeLock;










/*
    static int access$1408(AlarmManagerService alarmmanagerservice) {
        int i = alarmmanagerservice.mBroadcastRefCount;
        alarmmanagerservice.mBroadcastRefCount = i + 1;
        return i;
    }

*/


/*
    static int access$1410(AlarmManagerService alarmmanagerservice) {
        int i = alarmmanagerservice.mBroadcastRefCount;
        alarmmanagerservice.mBroadcastRefCount = i - 1;
        return i;
    }

*/














}
