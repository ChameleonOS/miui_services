// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.NetworkStats;
import android.os.*;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThrottleService extends android.net.IThrottleManager.Stub {
    private static class DataRecorder {

        private void checkAndDeleteLRUDataFile(File file) {
            File afile[] = file.listFiles();
            if(afile != null && afile.length > 3) {
                Slog.d("ThrottleService", "Too many data files");
                do {
                    File file1 = null;
                    File afile1[] = afile;
                    int i = afile1.length;
                    for(int j = 0; j < i; j++) {
                        File file2 = afile1[j];
                        if(file1 == null || file1.lastModified() > file2.lastModified())
                            file1 = file2;
                    }

                    if(file1 == null)
                        break;
                    Slog.d("ThrottleService", (new StringBuilder()).append(" deleting ").append(file1).toString());
                    file1.delete();
                    afile = file.listFiles();
                } while(afile.length > 3);
            }
        }

        private void checkForSubscriberId() {
            if(mImsi == null) goto _L2; else goto _L1
_L1:
            return;
_L2:
            mImsi = mTelephonyManager.getSubscriberId();
            if(mImsi != null)
                retrieve();
            if(true) goto _L1; else goto _L3
_L3:
        }

        private File getDataFile() {
            File file = new File(Environment.getDataDirectory(), "system/throttle");
            file.mkdirs();
            String s = mTelephonyManager.getSubscriberId();
            File file1;
            if(s == null)
                file1 = useMRUFile(file);
            else
                file1 = new File(file, Integer.toString(s.hashCode()));
            file1.setLastModified(System.currentTimeMillis());
            checkAndDeleteLRUDataFile(file);
            return file1;
        }

        private void record() {
            StringBuilder stringbuilder;
            BufferedWriter bufferedwriter;
            stringbuilder = new StringBuilder();
            stringbuilder.append(1);
            stringbuilder.append(":");
            stringbuilder.append(mPeriodCount);
            stringbuilder.append(":");
            for(int i = 0; i < mPeriodCount; i++) {
                stringbuilder.append(mPeriodRxData[i]);
                stringbuilder.append(":");
            }

            for(int j = 0; j < mPeriodCount; j++) {
                stringbuilder.append(mPeriodTxData[j]);
                stringbuilder.append(":");
            }

            stringbuilder.append(mCurrentPeriod);
            stringbuilder.append(":");
            stringbuilder.append(mPeriodStart.getTimeInMillis());
            stringbuilder.append(":");
            stringbuilder.append(mPeriodEnd.getTimeInMillis());
            bufferedwriter = null;
            BufferedWriter bufferedwriter1 = new BufferedWriter(new FileWriter(getDataFile()), 256);
            bufferedwriter1.write(stringbuilder.toString());
            if(bufferedwriter1 == null)
                break MISSING_BLOCK_LABEL_203;
            bufferedwriter1.close();
_L1:
            return;
            IOException ioexception1;
            ioexception1;
_L3:
            Slog.e("ThrottleService", "Error writing data file");
            if(bufferedwriter != null)
                try {
                    bufferedwriter.close();
                }
                catch(Exception exception2) { }
              goto _L1
            Exception exception;
            exception;
_L2:
            if(bufferedwriter != null)
                try {
                    bufferedwriter.close();
                }
                catch(Exception exception1) { }
            throw exception;
            Exception exception3;
            exception3;
              goto _L1
            exception;
            bufferedwriter = bufferedwriter1;
              goto _L2
            IOException ioexception;
            ioexception;
            bufferedwriter = bufferedwriter1;
              goto _L3
        }

        private void retrieve() {
            File file;
            FileInputStream fileinputstream;
            zeroData(0);
            file = getDataFile();
            fileinputstream = null;
            byte abyte0[];
            FileInputStream fileinputstream1;
            abyte0 = new byte[(int)file.length()];
            fileinputstream1 = new FileInputStream(file);
            fileinputstream1.read(abyte0);
            Exception exception;
            Exception exception1;
            IOException ioexception;
            Exception exception2;
            String s;
            String as[];
            int i;
            Exception exception3;
            int j;
            int k;
            long al[];
            int l;
            long al1[];
            int i1;
            int j1;
            int k1;
            GregorianCalendar gregoriancalendar;
            GregorianCalendar gregoriancalendar1;
            int l1;
            int i2;
            if(fileinputstream1 != null)
                try {
                    fileinputstream1.close();
                }
                catch(Exception exception6) { }
            s = new String(abyte0);
            if(s == null || s.length() == 0) {
                Slog.d("ThrottleService", "data file empty");
            } else {
label0:
                {
                    as = s.split(":");
                    if(as.length >= 6)
                        break label0;
                    Slog.e("ThrottleService", "reading data file with insufficient length - ignoring");
                }
            }
            return;
            ioexception;
_L8:
            Slog.e("ThrottleService", "Error reading data file");
            if(fileinputstream != null)
                try {
                    fileinputstream.close();
                }
                // Misplaced declaration of an exception variable
                catch(Exception exception2) { }
            break MISSING_BLOCK_LABEL_81;
            exception;
_L6:
            if(fileinputstream != null)
                try {
                    fileinputstream.close();
                }
                // Misplaced declaration of an exception variable
                catch(Exception exception1) { }
            throw exception;
label1:
            {
                if(Integer.parseInt(as[0]) == 1)
                    break label1;
                Slog.e("ThrottleService", "reading data file with bad version - ignoring");
            }
            if(true)
                break MISSING_BLOCK_LABEL_81;
            j = i + 1;
label2:
            {
                k = Integer.parseInt(as[i]);
                if(as.length == 5 + k * 2)
                    break label2;
                Slog.e("ThrottleService", (new StringBuilder()).append("reading data file with bad length (").append(as.length).append(" != ").append(5 + k * 2).append(") - ignoring").toString());
            }
            if(true)
                break MISSING_BLOCK_LABEL_81;
            al = new long[k];
            l = 0;
            i = j;
_L1:
            if(l >= k)
                break MISSING_BLOCK_LABEL_300;
            i2 = i + 1;
            al[l] = Long.parseLong(as[i]);
            l++;
            i = i2;
              goto _L1
            al1 = new long[k];
            i1 = 0;
_L2:
            if(i1 >= k)
                break MISSING_BLOCK_LABEL_345;
            l1 = i + 1;
            al1[i1] = Long.parseLong(as[i]);
            i1++;
            i = l1;
              goto _L2
            j1 = i + 1;
            k1 = Integer.parseInt(as[i]);
            gregoriancalendar = new GregorianCalendar();
            i = j1 + 1;
            gregoriancalendar.setTimeInMillis(Long.parseLong(as[j1]));
            gregoriancalendar1 = new GregorianCalendar();
            i + 1;
            gregoriancalendar1.setTimeInMillis(Long.parseLong(as[i]));
            synchronized(mParent) {
                mPeriodCount = k;
                mPeriodRxData = al;
                mPeriodTxData = al1;
                mCurrentPeriod = k1;
                mPeriodStart = gregoriancalendar;
                mPeriodEnd = gregoriancalendar1;
            }
            break MISSING_BLOCK_LABEL_81;
            exception3;
            i;
_L4:
            Slog.e("ThrottleService", "Error parsing data file - ignoring");
            break MISSING_BLOCK_LABEL_81;
            exception5;
            throttleservice;
            JVM INSTR monitorexit ;
            throw exception5;
            Exception exception4;
            exception4;
            if(true) goto _L4; else goto _L3
_L3:
            exception;
            fileinputstream = fileinputstream1;
            if(true) goto _L6; else goto _L5
_L5:
            IOException ioexception1;
            ioexception1;
            fileinputstream = fileinputstream1;
            if(true) goto _L8; else goto _L7
_L7:
        }

        private void setPeriodEnd(Calendar calendar) {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            mPeriodEnd = calendar;
            return;
        }

        private void setPeriodStart(Calendar calendar) {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            mPeriodStart = calendar;
            return;
        }

        private File useMRUFile(File file) {
            File file1 = null;
            File afile[] = file.listFiles();
            if(afile != null) {
                int i = afile.length;
                for(int j = 0; j < i; j++) {
                    File file2 = afile[j];
                    if(file1 == null || file1.lastModified() < file2.lastModified())
                        file1 = file2;
                }

            }
            if(file1 == null)
                file1 = new File(file, "temp");
            return file1;
        }

        private void zeroData(int i) {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            for(int j = 0; j < mPeriodCount; j++) {
                mPeriodRxData[j] = 0L;
                mPeriodTxData[j] = 0L;
            }

            mCurrentPeriod = 0;
            return;
        }

        void addData(long l, long l1) {
            checkForSubscriberId();
            synchronized(mParent) {
                long al[] = mPeriodRxData;
                int i = mCurrentPeriod;
                al[i] = l + al[i];
                long al1[] = mPeriodTxData;
                int j = mCurrentPeriod;
                al1[j] = l1 + al1[j];
            }
            record();
            return;
            exception;
            throttleservice;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public int getPeriodCount() {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            int i = mPeriodCount;
            return i;
        }

        public long getPeriodEnd() {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            long l = mPeriodEnd.getTimeInMillis();
            return l;
        }

        long getPeriodRx(int i) {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            long l;
            if(i > mPeriodCount) {
                l = 0L;
            } else {
                int j = mCurrentPeriod - i;
                if(j < 0)
                    j += mPeriodCount;
                l = mPeriodRxData[j];
            }
            return l;
        }

        public long getPeriodStart() {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            long l = mPeriodStart.getTimeInMillis();
            return l;
        }

        long getPeriodTx(int i) {
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            long l;
            if(i > mPeriodCount) {
                l = 0L;
            } else {
                int j = mCurrentPeriod - i;
                if(j < 0)
                    j += mPeriodCount;
                l = mPeriodTxData[j];
            }
            return l;
        }

        boolean setNextPeriod(Calendar calendar, Calendar calendar1) {
            boolean flag;
            checkForSubscriberId();
            flag = true;
            if(!calendar.equals(mPeriodStart) || !calendar1.equals(mPeriodEnd)) goto _L2; else goto _L1
_L1:
            flag = false;
_L4:
            setPeriodStart(calendar);
            setPeriodEnd(calendar1);
            record();
            return flag;
_L2:
            ThrottleService throttleservice = mParent;
            throttleservice;
            JVM INSTR monitorenter ;
            mCurrentPeriod = 1 + mCurrentPeriod;
            if(mCurrentPeriod >= mPeriodCount)
                mCurrentPeriod = 0;
            mPeriodRxData[mCurrentPeriod] = 0L;
            mPeriodTxData[mCurrentPeriod] = 0L;
            if(true) goto _L4; else goto _L3
_L3:
        }

        private static final int DATA_FILE_VERSION = 1;
        private static final int MAX_SIMS_SUPPORTED = 3;
        Context mContext;
        int mCurrentPeriod;
        String mImsi;
        ThrottleService mParent;
        int mPeriodCount;
        Calendar mPeriodEnd;
        long mPeriodRxData[];
        Calendar mPeriodStart;
        long mPeriodTxData[];
        TelephonyManager mTelephonyManager;

        DataRecorder(Context context, ThrottleService throttleservice) {
            mImsi = null;
            mContext = context;
            mParent = throttleservice;
            mTelephonyManager = (TelephonyManager)mContext.getSystemService("phone");
            ThrottleService throttleservice1 = mParent;
            throttleservice1;
            JVM INSTR monitorenter ;
            mPeriodCount = 6;
            mPeriodRxData = new long[mPeriodCount];
            mPeriodTxData = new long[mPeriodCount];
            mPeriodStart = Calendar.getInstance();
            mPeriodEnd = Calendar.getInstance();
            retrieve();
            return;
        }
    }

    private class MyHandler extends Handler {

        private Calendar calculatePeriodEnd(long l) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTimeInMillis(l);
            int i = calendar.get(5);
            calendar.set(5, mPolicyResetDay);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            if(i >= mPolicyResetDay) {
                int j = calendar.get(2);
                if(j == 11) {
                    calendar.set(1, 1 + calendar.get(1));
                    j = -1;
                }
                calendar.set(2, j + 1);
            }
            if(SystemProperties.get("persist.throttle.testing").equals("true")) {
                calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(l);
                calendar.add(13, 600);
            }
            return calendar;
        }

        private Calendar calculatePeriodStart(Calendar calendar) {
            Calendar calendar1 = (Calendar)calendar.clone();
            int i = calendar.get(2);
            if(calendar.get(2) == 0) {
                i = 12;
                calendar1.set(1, -1 + calendar1.get(1));
            }
            calendar1.set(2, i - 1);
            if(SystemProperties.get("persist.throttle.testing").equals("true")) {
                calendar1 = (Calendar)calendar.clone();
                calendar1.add(13, -600);
            }
            return calendar1;
        }

        private void checkThrottleAndPostNotification(long l) {
            long l1 = mPolicyThreshold.get();
            if(l1 != 0L) goto _L2; else goto _L1
_L1:
            clearThrottleAndNotification();
_L4:
            return;
_L2:
            if(!mTime.hasCache())
                Slog.w("ThrottleService", "missing trusted time, skipping throttle check");
            else
            if(l > l1) {
                if(mThrottleIndex.get() != 1) {
                    mThrottleIndex.set(1);
                    Slog.d("ThrottleService", (new StringBuilder()).append("Threshold ").append(l1).append(" exceeded!").toString());
                    Intent intent;
                    try {
                        mNMService.setInterfaceThrottle(mIface, mPolicyThrottleValue.get(), mPolicyThrottleValue.get());
                    }
                    catch(Exception exception) {
                        Slog.e("ThrottleService", (new StringBuilder()).append("error setting Throttle: ").append(exception).toString());
                    }
                    mNotificationManager.cancel(0x1080559);
                    postNotification(0x104048c, 0x104048d, 0x1080559, 2);
                    intent = new Intent("android.net.thrott.THROTTLE_ACTION");
                    intent.putExtra("level", mPolicyThrottleValue.get());
                    mContext.sendStickyBroadcast(intent);
                }
            } else {
                clearThrottleAndNotification();
                if((2 & mPolicyNotificationsAllowedMask) != 0) {
                    long l2 = mRecorder.getPeriodStart();
                    long l3 = mRecorder.getPeriodEnd() - l2;
                    long l4 = System.currentTimeMillis() - l2;
                    if(l > (l4 * (2L * l1)) / (l4 + l3) && l > l1 / 4L) {
                        if(!mWarningNotificationSent) {
                            mWarningNotificationSent = true;
                            mNotificationManager.cancel(0x1080559);
                            postNotification(0x104048a, 0x104048b, 0x1080559, 0);
                        }
                    } else
                    if(mWarningNotificationSent) {
                        mNotificationManager.cancel(0x1080559);
                        mWarningNotificationSent = false;
                    }
                }
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        private void clearThrottleAndNotification() {
            if(mThrottleIndex.get() != 0) {
                mThrottleIndex.set(0);
                Intent intent;
                try {
                    mNMService.setInterfaceThrottle(mIface, -1, -1);
                }
                catch(Exception exception) {
                    Slog.e("ThrottleService", (new StringBuilder()).append("error clearing Throttle: ").append(exception).toString());
                }
                intent = new Intent("android.net.thrott.THROTTLE_ACTION");
                intent.putExtra("level", -1);
                mContext.sendStickyBroadcast(intent);
                mNotificationManager.cancel(0x1080559);
                mWarningNotificationSent = false;
            }
        }

        private void onIfaceUp() {
            if(mThrottleIndex.get() != 1)
                break MISSING_BLOCK_LABEL_76;
            mNMService.setInterfaceThrottle(mIface, -1, -1);
            mNMService.setInterfaceThrottle(mIface, mPolicyThrottleValue.get(), mPolicyThrottleValue.get());
_L1:
            return;
            Exception exception;
            exception;
            Slog.e("ThrottleService", (new StringBuilder()).append("error setting Throttle: ").append(exception).toString());
              goto _L1
        }

        private void onPolicyChanged() {
            boolean flag = SystemProperties.get("persist.throttle.testing").equals("true");
            int i = mContext.getResources().getInteger(0x10e0025);
            mPolicyPollPeriodSec = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "throttle_polling_sec", i);
            long l = mContext.getResources().getInteger(0x10e0026);
            int j = mContext.getResources().getInteger(0x10e0027);
            long l1 = android.provider.Settings.Secure.getLong(mContext.getContentResolver(), "throttle_threshold_bytes", l);
            int k = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "throttle_value_kbitsps", j);
            mPolicyThreshold.set(l1);
            mPolicyThrottleValue.set(k);
            if(flag) {
                mPolicyPollPeriodSec = 60;
                mPolicyThreshold.set(0x100000L);
            }
            mPolicyResetDay = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "throttle_reset_day", -1);
            if(mPolicyResetDay == -1 || mPolicyResetDay < 1 || mPolicyResetDay > 28) {
                Random random = new Random();
                mPolicyResetDay = 1 + random.nextInt(28);
                android.provider.Settings.Secure.putInt(mContext.getContentResolver(), "throttle_reset_day", mPolicyResetDay);
            }
            if(mIface == null)
                mPolicyThreshold.set(0L);
            int i1 = mContext.getResources().getInteger(0x10e0028);
            mPolicyNotificationsAllowedMask = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "throttle_notification_type", i1);
            int j1 = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "throttle_max_ntp_cache_age_sec", 0x15180);
            mMaxNtpCacheAge = (long)(j1 * 1000);
            if(mPolicyThreshold.get() != 0L)
                Slog.d("ThrottleService", (new StringBuilder()).append("onPolicyChanged testing=").append(flag).append(", period=").append(mPolicyPollPeriodSec).append(", threshold=").append(mPolicyThreshold.get()).append(", value=").append(mPolicyThrottleValue.get()).append(", resetDay=").append(mPolicyResetDay).append(", noteType=").append(mPolicyNotificationsAllowedMask).append(", mMaxNtpCacheAge=").append(mMaxNtpCacheAge).toString());
            mThrottleIndex.set(-1);
            onResetAlarm();
            onPollAlarm();
            Intent intent = new Intent("android.net.thrott.POLICY_CHANGED_ACTION");
            mContext.sendBroadcast(intent);
        }

        private void onPollAlarm() {
            long l;
            long l1;
            long l2;
            l = SystemClock.elapsedRealtime() + (long)(1000 * mPolicyPollPeriodSec);
            if(mTime.getCacheAge() > mMaxNtpCacheAge && mTime.forceRefresh())
                dispatchReset();
            l1 = 0L;
            l2 = 0L;
            NetworkStats networkstats;
            int i;
            networkstats = mNMService.getNetworkStatsSummaryDev();
            i = networkstats.findIndex(mIface, -1, 0, 0);
            if(i == -1) goto _L2; else goto _L1
_L1:
            android.net.NetworkStats.Entry entry = networkstats.getValues(i, null);
            l1 = entry.rxBytes - mLastRead;
            l2 = entry.txBytes - mLastWrite;
              goto _L3
_L5:
            l1 += mLastRead;
            l2 += mLastWrite;
            mLastRead = 0L;
            mLastWrite = 0L;
_L4:
            boolean flag = "true".equals(SystemProperties.get("gsm.operator.isroaming"));
            if(!flag)
                mRecorder.addData(l1, l2);
            long l3 = mRecorder.getPeriodRx(0);
            long l4 = mRecorder.getPeriodTx(0);
            long l5 = l3 + l4;
            if(mPolicyThreshold.get() != 0L)
                Slog.d("ThrottleService", (new StringBuilder()).append("onPollAlarm - roaming =").append(flag).append(", read =").append(l1).append(", written =").append(l2).append(", new total =").append(l5).toString());
            long l1 = <no variable> + 
// JavaClassFileOutputException: get_constant: invalid tag

        private void onRebootRecovery() {
            mThrottleIndex.set(-1);
            mRecorder = new DataRecorder(mContext, ThrottleService.this);
            mHandler.obtainMessage(1).sendToTarget();
            mHandler.sendMessageDelayed(mHandler.obtainMessage(2), 0x15f90L);
        }

        private void onResetAlarm() {
            if(mPolicyThreshold.get() != 0L)
                Slog.d("ThrottleService", (new StringBuilder()).append("onResetAlarm - last period had ").append(mRecorder.getPeriodRx(0)).append(" bytes read and ").append(mRecorder.getPeriodTx(0)).append(" written").toString());
            if(mTime.getCacheAge() > mMaxNtpCacheAge)
                mTime.forceRefresh();
            if(mTime.hasCache()) {
                long l = mTime.currentTimeMillis();
                Calendar calendar = calculatePeriodEnd(l);
                Calendar calendar1 = calculatePeriodStart(calendar);
                if(mRecorder.setNextPeriod(calendar1, calendar))
                    onPollAlarm();
                mAlarmManager.cancel(mPendingResetIntent);
                long l1 = calendar.getTimeInMillis() - l;
                mAlarmManager.set(3, l1 + SystemClock.elapsedRealtime(), mPendingResetIntent);
            }
        }

        private void postNotification(int i, int j, int k, int l) {
            Intent intent = new Intent();
            intent.setClassName("com.android.phone", "com.android.phone.DataUsage");
            intent.setFlags(0x40000000);
            PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
            Resources resources = Resources.getSystem();
            CharSequence charsequence = resources.getText(i);
            CharSequence charsequence1 = resources.getText(j);
            if(mThrottlingNotification == null) {
                mThrottlingNotification = new Notification();
                mThrottlingNotification.when = 0L;
                mThrottlingNotification.icon = k;
                Notification notification = mThrottlingNotification;
                notification.defaults = -2 & notification.defaults;
            }
            mThrottlingNotification.flags = l;
            mThrottlingNotification.tickerText = charsequence;
            mThrottlingNotification.setLatestEventInfo(mContext, charsequence, charsequence1, pendingintent);
            mNotificationManager.notify(mThrottlingNotification.icon, mThrottlingNotification);
        }

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 0 4: default 40
        //                       0 41
        //                       1 48
        //                       2 55
        //                       3 62
        //                       4 69;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
            return;
_L2:
            onRebootRecovery();
            continue; /* Loop/switch isn't completed */
_L3:
            onPolicyChanged();
            continue; /* Loop/switch isn't completed */
_L4:
            onPollAlarm();
            continue; /* Loop/switch isn't completed */
_L5:
            onResetAlarm();
            continue; /* Loop/switch isn't completed */
_L6:
            onIfaceUp();
            if(true) goto _L1; else goto _L7
_L7:
        }

        final ThrottleService this$0;

        public MyHandler(Looper looper) {
            this$0 = ThrottleService.this;
            super(looper);
        }
    }

    private static class SettingsObserver extends ContentObserver {

        public void onChange(boolean flag) {
            mHandler.obtainMessage(mMsg).sendToTarget();
        }

        void register(Context context) {
            ContentResolver contentresolver = context.getContentResolver();
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_polling_sec"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_threshold_bytes"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_value_kbitsps"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_reset_day"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_notification_type"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_help_uri"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("throttle_max_ntp_cache_age_sec"), false, this);
        }

        void unregister(Context context) {
            context.getContentResolver().unregisterContentObserver(this);
        }

        private Handler mHandler;
        private int mMsg;

        SettingsObserver(Handler handler, int i) {
            super(handler);
            mHandler = handler;
            mMsg = i;
        }
    }

    private static class InterfaceObserver extends android.net.INetworkManagementEventObserver.Stub {

        public void interfaceAdded(String s) {
            if(TextUtils.equals(s, mIface))
                mHandler.obtainMessage(mMsg).sendToTarget();
        }

        public void interfaceLinkStateChanged(String s, boolean flag) {
        }

        public void interfaceRemoved(String s) {
        }

        public void interfaceStatusChanged(String s, boolean flag) {
            if(flag && TextUtils.equals(s, mIface))
                mHandler.obtainMessage(mMsg).sendToTarget();
        }

        public void limitReached(String s, String s1) {
        }

        private Handler mHandler;
        private String mIface;
        private int mMsg;

        InterfaceObserver(Handler handler, int i, String s) {
            mHandler = handler;
            mMsg = i;
            mIface = s;
        }
    }


    public ThrottleService(Context context) {
        ThrottleService(context, getNetworkManagementService(), ((TrustedTime) (NtpTrustedTime.getInstance(context))), context.getResources().getString(0x104001e));
    }

    public ThrottleService(Context context, INetworkManagementService inetworkmanagementservice, TrustedTime trustedtime, String s) {
        mMaxNtpCacheAge = 0x5265c00L;
        mWarningNotificationSent = false;
        mContext = context;
        mPolicyThreshold = new AtomicLong();
        mPolicyThrottleValue = new AtomicInteger();
        mThrottleIndex = new AtomicInteger();
        mIface = s;
        mAlarmManager = (AlarmManager)mContext.getSystemService("alarm");
        Intent intent = new Intent("com.android.server.ThrottleManager.action.POLL", null);
        mPendingPollIntent = PendingIntent.getBroadcast(mContext, POLL_REQUEST, intent, 0);
        Intent intent1 = new Intent("com.android.server.ThorottleManager.action.RESET", null);
        mPendingResetIntent = PendingIntent.getBroadcast(mContext, RESET_REQUEST, intent1, 0);
        mNMService = inetworkmanagementservice;
        mTime = trustedtime;
        mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
    }

    private void enforceAccessPermission() {
        mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", "ThrottleService");
    }

    private static INetworkManagementService getNetworkManagementService() {
        return android.os.INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    private long ntpToWallTime(long l) {
        long l1;
        if(mTime.hasCache())
            l1 = mTime.currentTimeMillis();
        else
            l1 = System.currentTimeMillis();
        return System.currentTimeMillis() + (l - l1);
    }

    void dispatchPoll() {
        mHandler.obtainMessage(2).sendToTarget();
    }

    void dispatchReset() {
        mHandler.obtainMessage(3).sendToTarget();
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printwriter.println((new StringBuilder()).append("Permission Denial: can't dump ThrottleService from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
        } else {
            printwriter.println();
            printwriter.println((new StringBuilder()).append("The threshold is ").append(mPolicyThreshold.get()).append(", after which you experince throttling to ").append(mPolicyThrottleValue.get()).append("kbps").toString());
            printwriter.println((new StringBuilder()).append("Current period is ").append((mRecorder.getPeriodEnd() - mRecorder.getPeriodStart()) / 1000L).append(" seconds long ").append("and ends in ").append((getResetTime(mIface) - System.currentTimeMillis()) / 1000L).append(" seconds.").toString());
            printwriter.println((new StringBuilder()).append("Polling every ").append(mPolicyPollPeriodSec).append(" seconds").toString());
            printwriter.println((new StringBuilder()).append("Current Throttle Index is ").append(mThrottleIndex.get()).toString());
            printwriter.println((new StringBuilder()).append("mMaxNtpCacheAge=").append(mMaxNtpCacheAge).toString());
            int i = 0;
            while(i < mRecorder.getPeriodCount())  {
                printwriter.println((new StringBuilder()).append(" Period[").append(i).append("] - read:").append(mRecorder.getPeriodRx(i)).append(", written:").append(mRecorder.getPeriodTx(i)).toString());
                i++;
            }
        }
    }

    public long getByteCount(String s, int i, int j, int k) {
        enforceAccessPermission();
        if(j != 0 || mRecorder == null) goto _L2; else goto _L1
_L1:
        if(i != 0) goto _L4; else goto _L3
_L3:
        long l = mRecorder.getPeriodTx(k);
_L6:
        return l;
_L4:
        if(i == 1) {
            l = mRecorder.getPeriodRx(k);
            continue; /* Loop/switch isn't completed */
        }
_L2:
        l = 0L;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public int getCliffLevel(String s, int i) {
        enforceAccessPermission();
        int j;
        if(i == 1)
            j = mPolicyThrottleValue.get();
        else
            j = 0;
        return j;
    }

    public long getCliffThreshold(String s, int i) {
        enforceAccessPermission();
        long l;
        if(i == 1)
            l = mPolicyThreshold.get();
        else
            l = 0L;
        return l;
    }

    public String getHelpUri() {
        enforceAccessPermission();
        return android.provider.Settings.Secure.getString(mContext.getContentResolver(), "throttle_help_uri");
    }

    public long getPeriodStartTime(String s) {
        long l = 0L;
        enforceAccessPermission();
        if(mRecorder != null)
            l = mRecorder.getPeriodStart();
        return ntpToWallTime(l);
    }

    public long getResetTime(String s) {
        enforceAccessPermission();
        long l = 0L;
        if(mRecorder != null)
            l = mRecorder.getPeriodEnd();
        return ntpToWallTime(l);
    }

    public int getThrottle(String s) {
        enforceAccessPermission();
        int i;
        if(mThrottleIndex.get() == 1)
            i = mPolicyThrottleValue.get();
        else
            i = 0;
        return i;
    }

    void shutdown() {
        if(mThread != null)
            mThread.quit();
        if(mSettingsObserver != null)
            mSettingsObserver.unregister(mContext);
        if(mPollStickyBroadcast != null)
            mContext.removeStickyBroadcast(mPollStickyBroadcast);
    }

    void systemReady() {
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                dispatchPoll();
            }

            final ThrottleService this$0;

             {
                this$0 = ThrottleService.this;
                super();
            }
        }, new IntentFilter("com.android.server.ThrottleManager.action.POLL"));
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                dispatchReset();
            }

            final ThrottleService this$0;

             {
                this$0 = ThrottleService.this;
                super();
            }
        }, new IntentFilter("com.android.server.ThorottleManager.action.RESET"));
        mThread = new HandlerThread("ThrottleService");
        mThread.start();
        mHandler = new MyHandler(mThread.getLooper());
        mHandler.obtainMessage(0).sendToTarget();
        mInterfaceObserver = new InterfaceObserver(mHandler, 4, mIface);
        try {
            mNMService.registerObserver(mInterfaceObserver);
        }
        catch(RemoteException remoteexception) {
            Slog.e("ThrottleService", (new StringBuilder()).append("Could not register InterfaceObserver ").append(remoteexception).toString());
        }
        mSettingsObserver = new SettingsObserver(mHandler, 1);
        mSettingsObserver.register(mContext);
    }

    private static final String ACTION_POLL = "com.android.server.ThrottleManager.action.POLL";
    private static final String ACTION_RESET = "com.android.server.ThorottleManager.action.RESET";
    private static final boolean DBG = true;
    private static final int EVENT_IFACE_UP = 4;
    private static final int EVENT_POLICY_CHANGED = 1;
    private static final int EVENT_POLL_ALARM = 2;
    private static final int EVENT_REBOOT_RECOVERY = 0;
    private static final int EVENT_RESET_ALARM = 3;
    private static final int INITIAL_POLL_DELAY_SEC = 90;
    private static final long MAX_NTP_CACHE_AGE = 0x5265c00L;
    private static final int NOTIFICATION_WARNING = 2;
    private static int POLL_REQUEST = 0;
    private static int RESET_REQUEST = 0;
    private static final String TAG = "ThrottleService";
    private static final String TESTING_ENABLED_PROPERTY = "persist.throttle.testing";
    private static final int TESTING_POLLING_PERIOD_SEC = 60;
    private static final int TESTING_RESET_PERIOD_SEC = 600;
    private static final long TESTING_THRESHOLD = 0x100000L;
    private static final int THROTTLE_INDEX_UNINITIALIZED = -1;
    private static final int THROTTLE_INDEX_UNTHROTTLED;
    private static final boolean VDBG;
    private AlarmManager mAlarmManager;
    private Context mContext;
    private Handler mHandler;
    private String mIface;
    private InterfaceObserver mInterfaceObserver;
    private long mLastRead;
    private long mLastWrite;
    private long mMaxNtpCacheAge;
    private INetworkManagementService mNMService;
    private NotificationManager mNotificationManager;
    private PendingIntent mPendingPollIntent;
    private PendingIntent mPendingResetIntent;
    private int mPolicyNotificationsAllowedMask;
    private int mPolicyPollPeriodSec;
    private int mPolicyResetDay;
    private AtomicLong mPolicyThreshold;
    private AtomicInteger mPolicyThrottleValue;
    private Intent mPollStickyBroadcast;
    private DataRecorder mRecorder;
    private SettingsObserver mSettingsObserver;
    private HandlerThread mThread;
    private AtomicInteger mThrottleIndex;
    private Notification mThrottlingNotification;
    private TrustedTime mTime;
    private boolean mWarningNotificationSent;

    static  {
        POLL_REQUEST = 0;
        RESET_REQUEST = 1;
    }





/*
    static long access$1002(ThrottleService throttleservice, long l) {
        throttleservice.mMaxNtpCacheAge = l;
        return l;
    }

*/


/*
    static DataRecorder access$102(ThrottleService throttleservice, DataRecorder datarecorder) {
        throttleservice.mRecorder = datarecorder;
        return datarecorder;
    }

*/





/*
    static long access$1302(ThrottleService throttleservice, long l) {
        throttleservice.mLastRead = l;
        return l;
    }

*/


/*
    static long access$1314(ThrottleService throttleservice, long l) {
        long l1 = l + throttleservice.mLastRead;
        throttleservice.mLastRead = l1;
        return l1;
    }

*/



/*
    static long access$1402(ThrottleService throttleservice, long l) {
        throttleservice.mLastWrite = l;
        return l;
    }

*/


/*
    static long access$1414(ThrottleService throttleservice, long l) {
        long l1 = l + throttleservice.mLastWrite;
        throttleservice.mLastWrite = l1;
        return l1;
    }

*/


/*
    static Intent access$1502(ThrottleService throttleservice, Intent intent) {
        throttleservice.mPollStickyBroadcast = intent;
        return intent;
    }

*/






/*
    static boolean access$1902(ThrottleService throttleservice, boolean flag) {
        throttleservice.mWarningNotificationSent = flag;
        return flag;
    }

*/




/*
    static Notification access$2002(ThrottleService throttleservice, Notification notification) {
        throttleservice.mThrottlingNotification = notification;
        return notification;
    }

*/





/*
    static int access$402(ThrottleService throttleservice, int i) {
        throttleservice.mPolicyPollPeriodSec = i;
        return i;
    }

*/





/*
    static int access$702(ThrottleService throttleservice, int i) {
        throttleservice.mPolicyResetDay = i;
        return i;
    }

*/




/*
    static int access$902(ThrottleService throttleservice, int i) {
        throttleservice.mPolicyNotificationsAllowedMask = i;
        return i;
    }

*/
}
