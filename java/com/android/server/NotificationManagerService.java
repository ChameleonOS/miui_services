// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.media.*;
import android.net.Uri;
import android.os.*;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.*;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.os.AtomicFile;
import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.util.FastXmlSerializer;
import java.io.*;
import java.util.*;
import libcore.io.IoUtils;
import miui.app.ExtraNotification;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server:
//            StatusBarManagerService, LightsService

public class NotificationManagerService extends android.app.INotificationManager.Stub {
    private final class WorkerHandler extends Handler {

        public void handleMessage(Message message) {
            message.what;
            JVM INSTR tableswitch 2 2: default 24
        //                       2 25;
               goto _L1 _L2
_L1:
            return;
_L2:
            handleTimeout((ToastRecord)message.obj);
            if(true) goto _L1; else goto _L3
_L3:
        }

        final NotificationManagerService this$0;

        private WorkerHandler() {
            this$0 = NotificationManagerService.this;
            super();
        }

    }

    class SettingsObserver extends ContentObserver {

        void observe() {
            mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("notification_light_pulse"), false, this);
            update();
        }

        public void onChange(boolean flag) {
            update();
        }

        public void update() {
            boolean flag = false;
            if(android.provider.Settings.System.getInt(mContext.getContentResolver(), "notification_light_pulse", 0) != 0)
                flag = true;
            if(mNotificationPulseEnabled != flag) {
                mNotificationPulseEnabled = flag;
                updateNotificationPulse();
            }
        }

        final NotificationManagerService this$0;

        SettingsObserver(Handler handler) {
            this$0 = NotificationManagerService.this;
            super(handler);
        }
    }

    private static final class ToastRecord {

        void dump(PrintWriter printwriter, String s) {
            printwriter.println((new StringBuilder()).append(s).append(this).toString());
        }

        public final String toString() {
            return (new StringBuilder()).append("ToastRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(" pkg=").append(pkg).append(" callback=").append(callback).append(" duration=").append(duration).toString();
        }

        void update(int i) {
            duration = i;
        }

        final ITransientNotification callback;
        int duration;
        final int pid;
        final String pkg;

        ToastRecord(int i, String s, ITransientNotification itransientnotification, int j) {
            pid = i;
            pkg = s;
            callback = itransientnotification;
            duration = j;
        }
    }

    private static final class NotificationRecord {

        void dump(PrintWriter printwriter, String s, Context context) {
            printwriter.println((new StringBuilder()).append(s).append(this).toString());
            printwriter.println((new StringBuilder()).append(s).append("  icon=0x").append(Integer.toHexString(notification.icon)).append(" / ").append(NotificationManagerService.idDebugString(context, pkg, notification.icon)).toString());
            printwriter.println((new StringBuilder()).append(s).append("  pri=").append(notification.priority).toString());
            printwriter.println((new StringBuilder()).append(s).append("  score=").append(score).toString());
            printwriter.println((new StringBuilder()).append(s).append("  contentIntent=").append(notification.contentIntent).toString());
            printwriter.println((new StringBuilder()).append(s).append("  deleteIntent=").append(notification.deleteIntent).toString());
            printwriter.println((new StringBuilder()).append(s).append("  tickerText=").append(notification.tickerText).toString());
            printwriter.println((new StringBuilder()).append(s).append("  contentView=").append(notification.contentView).toString());
            printwriter.println((new StringBuilder()).append(s).append("  uid=").append(uid).toString());
            printwriter.println((new StringBuilder()).append(s).append("  defaults=0x").append(Integer.toHexString(notification.defaults)).toString());
            printwriter.println((new StringBuilder()).append(s).append("  flags=0x").append(Integer.toHexString(notification.flags)).toString());
            printwriter.println((new StringBuilder()).append(s).append("  sound=").append(notification.sound).toString());
            printwriter.println((new StringBuilder()).append(s).append("  vibrate=").append(Arrays.toString(notification.vibrate)).toString());
            printwriter.println((new StringBuilder()).append(s).append("  ledARGB=0x").append(Integer.toHexString(notification.ledARGB)).append(" ledOnMS=").append(notification.ledOnMS).append(" ledOffMS=").append(notification.ledOffMS).toString());
        }

        public final String toString() {
            return (new StringBuilder()).append("NotificationRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(" pkg=").append(pkg).append(" id=").append(Integer.toHexString(id)).append(" tag=").append(tag).append(" score=").append(score).append("}").toString();
        }

        final int id;
        final int initialPid;
        final Notification notification;
        final String pkg;
        final int score;
        IBinder statusBarKey;
        final String tag;
        final int uid;

        NotificationRecord(String s, String s1, int i, int j, int k, int l, Notification notification1) {
            pkg = s;
            tag = s1;
            id = i;
            uid = j;
            initialPid = k;
            score = l;
            notification = notification1;
        }
    }

    static class Injector {

        static void updateNotificationLight(NotificationManagerService notificationmanagerservice) {
            Context context = notificationmanagerservice.mContext;
            int i = notificationmanagerservice.getDefaultNotificationColor();
            NotificationRecord notificationrecord = notificationmanagerservice.getLedNotification();
            int j = android.provider.Settings.System.getInt(context.getContentResolver(), "breathing_light_color", i);
            int k = context.getResources().getInteger(0x608000a);
            int ai[] = ExtraNotification.getLedPwmOffOn(android.provider.Settings.System.getInt(context.getContentResolver(), "breathing_light_freq", k));
            notificationrecord.notification.ledARGB = j;
            notificationrecord.notification.ledOnMS = ai[1];
            notificationrecord.notification.ledOffMS = ai[0];
        }

        Injector() {
        }
    }


    NotificationManagerService(Context context, StatusBarManagerService statusbarmanagerservice, LightsService lightsservice) {
        mScreenOn = true;
        mInCall = false;
        mLights = new ArrayList();
        mBlockedPackages = new HashSet();
        mNotificationCallbacks = new StatusBarManagerService.NotificationCallbacks() {

            public void onClearAll() {
                cancelAll();
            }

            public void onNotificationClear(String s, String s1, int i) {
                cancelNotification(s, s1, i, 0, 66, true);
            }

            public void onNotificationClick(String s, String s1, int i) {
                cancelNotification(s, s1, i, 16, 64, false);
            }

            public void onNotificationError(String s, String s1, int i, int j, int k, String s2) {
                Slog.d("NotificationService", (new StringBuilder()).append("onNotification error pkg=").append(s).append(" tag=").append(s1).append(" id=").append(i).append("; will crashApplication(uid=").append(j).append(", pid=").append(k).append(")").toString());
                cancelNotification(s, s1, i, 0, 0, false);
                long l = Binder.clearCallingIdentity();
                try {
                    ActivityManagerNative.getDefault().crashApplication(j, k, s, (new StringBuilder()).append("Bad notification posted from package ").append(s).append(": ").append(s2).toString());
                }
                catch(RemoteException remoteexception) { }
                Binder.restoreCallingIdentity(l);
            }

            public void onPanelRevealed() {
                ArrayList arraylist = mNotificationList;
                arraylist;
                JVM INSTR monitorenter ;
                long l;
                mSoundNotification = null;
                l = Binder.clearCallingIdentity();
                IRingtonePlayer iringtoneplayer = mAudioService.getRingtonePlayer();
                if(iringtoneplayer != null)
                    iringtoneplayer.stopAsync();
                Binder.restoreCallingIdentity(l);
_L1:
                long l1;
                mVibrateNotification = null;
                l1 = Binder.clearCallingIdentity();
                mVibrator.cancel();
                Binder.restoreCallingIdentity(l1);
                mLights.clear();
                mLedNotification = null;
                updateLightsLocked();
                arraylist;
                JVM INSTR monitorexit ;
                return;
                Exception exception2;
                exception2;
                Binder.restoreCallingIdentity(l);
                throw exception2;
                Exception exception;
                exception;
                throw exception;
                Exception exception1;
                exception1;
                Binder.restoreCallingIdentity(l1);
                throw exception1;
                RemoteException remoteexception;
                remoteexception;
                Binder.restoreCallingIdentity(l);
                  goto _L1
            }

            public void onSetDisabled(int i) {
                ArrayList arraylist = mNotificationList;
                arraylist;
                JVM INSTR monitorenter ;
                mDisabledNotifications = i;
                if((0x40000 & mDisabledNotifications) == 0) goto _L2; else goto _L1
_L1:
                long l = Binder.clearCallingIdentity();
                IRingtonePlayer iringtoneplayer = mAudioService.getRingtonePlayer();
                if(iringtoneplayer != null)
                    iringtoneplayer.stopAsync();
                Binder.restoreCallingIdentity(l);
_L3:
                long l1 = Binder.clearCallingIdentity();
                mVibrator.cancel();
                Binder.restoreCallingIdentity(l1);
_L2:
                arraylist;
                JVM INSTR monitorexit ;
                return;
                Exception exception2;
                exception2;
                Binder.restoreCallingIdentity(l);
                throw exception2;
                Exception exception;
                exception;
                throw exception;
                Exception exception1;
                exception1;
                Binder.restoreCallingIdentity(l1);
                throw exception1;
                RemoteException remoteexception;
                remoteexception;
                Binder.restoreCallingIdentity(l);
                  goto _L3
            }

            final NotificationManagerService this$0;

             {
                this$0 = NotificationManagerService.this;
                super();
            }
        };
        mIntentReceiver = new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent) {
                String s;
                boolean flag1;
                String as[];
                s = intent.getAction();
                boolean flag = false;
                flag1 = false;
                if(!s.equals("android.intent.action.PACKAGE_REMOVED") && !s.equals("android.intent.action.PACKAGE_RESTARTED")) {
                    flag1 = s.equals("android.intent.action.PACKAGE_CHANGED");
                    if(!flag1) {
                        flag = s.equals("android.intent.action.QUERY_PACKAGE_RESTART");
                        if(!flag && !s.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"))
                            break MISSING_BLOCK_LABEL_244;
                    }
                }
                String as1[];
                int i;
                int j;
                String s2;
                NotificationManagerService notificationmanagerservice;
                if(s.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                    as = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                } else {
label0:
                    {
                        if(!flag)
                            break label0;
                        as = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
                    }
                }
_L6:
                if(as != null && as.length > 0) {
                    as1 = as;
                    i = as1.length;
                    j = 0;
                    while(j < i)  {
                        s2 = as1[j];
                        notificationmanagerservice = NotificationManagerService.this;
                        Uri uri;
                        String s1;
                        boolean flag2;
                        int k;
                        if(!flag)
                            flag2 = true;
                        else
                            flag2 = false;
                        notificationmanagerservice.cancelAllNotificationsInt(s2, 0, 0, flag2);
                        j++;
                    }
                }
                  goto _L1
                uri = intent.getData();
                if(uri != null) goto _L2; else goto _L1
_L1:
                return;
_L2:
                s1 = uri.getSchemeSpecificPart();
                if(s1 == null) goto _L1; else goto _L3
_L3:
                if(!flag1) goto _L5; else goto _L4
_L4:
                k = mContext.getPackageManager().getApplicationEnabledSetting(s1);
                if(k == 1 || k == 0) goto _L1; else goto _L5
_L5:
                as = new String[1];
                as[0] = s1;
                  goto _L6
                if(s.equals("android.intent.action.SCREEN_ON"))
                    mScreenOn = true;
                else
                if(s.equals("android.intent.action.SCREEN_OFF"))
                    mScreenOn = false;
                else
                if(s.equals("android.intent.action.PHONE_STATE")) {
                    mInCall = intent.getStringExtra("state").equals(TelephonyManager.EXTRA_STATE_OFFHOOK);
                    updateNotificationPulse();
                } else
                if(s.equals("android.intent.action.USER_PRESENT"))
                    mNotificationLight.turnOff();
                  goto _L1
            }

            final NotificationManagerService this$0;

             {
                this$0 = NotificationManagerService.this;
                super();
            }
        };
        mContext = context;
        mVibrator = (Vibrator)context.getSystemService("vibrator");
        mToastQueue = new ArrayList();
        mHandler = new WorkerHandler();
        loadBlockDb();
        mStatusBar = statusbarmanagerservice;
        statusbarmanagerservice.setNotificationCallbacks(mNotificationCallbacks);
        mNotificationLight = lightsservice.getLight(4);
        mAttentionLight = lightsservice.getLight(5);
        Resources resources = mContext.getResources();
        mDefaultNotificationColor = resources.getColor(0x1060063);
        mDefaultNotificationLedOn = resources.getInteger(0x10e001a);
        mDefaultNotificationLedOff = resources.getInteger(0x10e001b);
        if(android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "device_provisioned", 0) == 0)
            mDisabledNotifications = 0x40000;
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.SCREEN_ON");
        intentfilter.addAction("android.intent.action.SCREEN_OFF");
        intentfilter.addAction("android.intent.action.PHONE_STATE");
        intentfilter.addAction("android.intent.action.USER_PRESENT");
        mContext.registerReceiver(mIntentReceiver, intentfilter);
        IntentFilter intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter1.addAction("android.intent.action.PACKAGE_CHANGED");
        intentfilter1.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentfilter1.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentfilter1.addDataScheme("package");
        mContext.registerReceiver(mIntentReceiver, intentfilter1);
        IntentFilter intentfilter2 = new IntentFilter("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        mContext.registerReceiver(mIntentReceiver, intentfilter2);
        (new SettingsObserver(mHandler)).observe();
    }

    private boolean areNotificationsEnabledForPackageInt(String s) {
        boolean flag;
        if(!mBlockedPackages.contains(s))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void cancelNotification(String s, String s1, int i, int j, int k, boolean flag) {
        Object aobj[] = new Object[5];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s1;
        aobj[3] = Integer.valueOf(j);
        aobj[4] = Integer.valueOf(k);
        EventLog.writeEvent(2751, aobj);
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        int l = indexOfNotificationLocked(s, s1, i);
        if(l < 0) goto _L2; else goto _L1
_L1:
        NotificationRecord notificationrecord = (NotificationRecord)mNotificationList.get(l);
        if((j & notificationrecord.notification.flags) == j && (k & notificationrecord.notification.flags) == 0) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L4:
        mNotificationList.remove(l);
        cancelNotificationLocked(notificationrecord, flag);
        updateLightsLocked();
_L2:
        arraylist;
        JVM INSTR monitorexit ;
_L3:
    }

    private void cancelNotificationLocked(NotificationRecord notificationrecord, boolean flag) {
        long l2;
        IRingtonePlayer iringtoneplayer;
        if(flag && notificationrecord.notification.deleteIntent != null)
            try {
                notificationrecord.notification.deleteIntent.send();
            }
            catch(android.app.PendingIntent.CanceledException canceledexception) {
                Slog.w("NotificationService", (new StringBuilder()).append("canceled PendingIntent for ").append(notificationrecord.pkg).toString(), canceledexception);
            }
        if(notificationrecord.notification.icon == 0)
            break MISSING_BLOCK_LABEL_60;
        l2 = Binder.clearCallingIdentity();
        mStatusBar.removeNotification(notificationrecord.statusBarKey);
        Binder.restoreCallingIdentity(l2);
        notificationrecord.statusBarKey = null;
        if(mSoundNotification == notificationrecord) {
            mSoundNotification = null;
            long l1 = Binder.clearCallingIdentity();
            long l;
            Exception exception;
            Exception exception1;
            Exception exception2;
            try {
                iringtoneplayer = mAudioService.getRingtonePlayer();
                if(iringtoneplayer != null)
                    iringtoneplayer.stopAsync();
            }
            catch(RemoteException remoteexception) { }
            finally {
                Binder.restoreCallingIdentity(l1);
            }
            Binder.restoreCallingIdentity(l1);
        }
        if(mVibrateNotification != notificationrecord)
            break MISSING_BLOCK_LABEL_136;
        mVibrateNotification = null;
        l = Binder.clearCallingIdentity();
        mVibrator.cancel();
        Binder.restoreCallingIdentity(l);
        mLights.remove(notificationrecord);
        if(mLedNotification == notificationrecord)
            mLedNotification = null;
        return;
        exception2;
        Binder.restoreCallingIdentity(l2);
        throw exception2;
        throw exception1;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void cancelToastLocked(int i) {
        ToastRecord toastrecord = (ToastRecord)mToastQueue.get(i);
        try {
            toastrecord.callback.hide();
        }
        catch(RemoteException remoteexception) {
            Slog.w("NotificationService", (new StringBuilder()).append("Object died trying to hide notification ").append(toastrecord.callback).append(" in package ").append(toastrecord.pkg).toString());
        }
        mToastQueue.remove(i);
        keepProcessAliveLocked(toastrecord.pid);
        if(mToastQueue.size() > 0)
            showNextToastLocked();
    }

    private static final int clamp(int i, int j, int k) {
        if(i >= j)
            if(i > k)
                j = k;
            else
                j = i;
        return j;
    }

    private void handleTimeout(ToastRecord toastrecord) {
        ArrayList arraylist = mToastQueue;
        arraylist;
        JVM INSTR monitorenter ;
        int i = indexOfToastLocked(toastrecord.pkg, toastrecord.callback);
        if(i >= 0)
            cancelToastLocked(i);
        return;
    }

    private static String idDebugString(Context context, String s, int i) {
        if(s == null) goto _L2; else goto _L1
_L1:
        Context context2 = context.createPackageContext(s, 0);
        Context context1 = context2;
_L3:
        Resources resources = context1.getResources();
        String s2 = resources.getResourceName(i);
        String s1 = s2;
_L4:
        return s1;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        context1 = context;
          goto _L3
_L2:
        context1 = context;
          goto _L3
        android.content.res.Resources.NotFoundException notfoundexception;
        notfoundexception;
        s1 = "<name unknown>";
          goto _L4
    }

    private int indexOfNotificationLocked(String s, String s1, int i) {
        ArrayList arraylist;
        int j;
        int k;
        arraylist = mNotificationList;
        j = arraylist.size();
        k = 0;
_L3:
        NotificationRecord notificationrecord;
        if(k >= j)
            break; /* Loop/switch isn't completed */
        notificationrecord = (NotificationRecord)arraylist.get(k);
          goto _L1
_L5:
        k++;
        if(true) goto _L3; else goto _L2
_L1:
        if((s1 != null ? !s1.equals(notificationrecord.tag) : notificationrecord.tag != null) || (notificationrecord.id != i || !notificationrecord.pkg.equals(s))) goto _L5; else goto _L4
_L4:
        return k;
_L2:
        k = -1;
        if(true) goto _L4; else goto _L6
_L6:
    }

    private int indexOfToastLocked(String s, ITransientNotification itransientnotification) {
        IBinder ibinder;
        ArrayList arraylist;
        int i;
        int j;
        ibinder = itransientnotification.asBinder();
        arraylist = mToastQueue;
        i = arraylist.size();
        j = 0;
_L3:
        ToastRecord toastrecord;
        if(j >= i)
            break MISSING_BLOCK_LABEL_77;
        toastrecord = (ToastRecord)arraylist.get(j);
        if(!toastrecord.pkg.equals(s) || toastrecord.callback.asBinder() != ibinder) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        j++;
          goto _L3
        j = -1;
          goto _L1
    }

    private void keepProcessAliveLocked(int i) {
        int j;
        j = 0;
        ArrayList arraylist = mToastQueue;
        int k = arraylist.size();
        for(int l = 0; l < k; l++)
            if(((ToastRecord)arraylist.get(l)).pid == i)
                j++;

        IActivityManager iactivitymanager;
        IBinder ibinder;
        iactivitymanager = mAm;
        ibinder = mForegroundToken;
        if(j <= 0) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L3:
        iactivitymanager.setProcessForeground(ibinder, i, flag);
_L4:
        return;
_L2:
        flag = false;
          goto _L3
        RemoteException remoteexception;
        remoteexception;
          goto _L4
    }

    private void loadBlockDb() {
        HashSet hashset = mBlockedPackages;
        hashset;
        JVM INSTR monitorenter ;
        if(mPolicyFile != null) goto _L2; else goto _L1
_L1:
        mPolicyFile = new AtomicFile(new File(new File("/data/system"), "notification_policy.xml"));
        mBlockedPackages.clear();
        java.io.FileInputStream fileinputstream = null;
        XmlPullParser xmlpullparser;
        fileinputstream = mPolicyFile.openRead();
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream, null);
_L4:
        String s;
label0:
        do
            do {
                int i = xmlpullparser.next();
                if(i == 1)
                    break MISSING_BLOCK_LABEL_304;
                s = xmlpullparser.getName();
                if(i == 2) {
                    if(!"notification-policy".equals(s))
                        continue label0;
                    Integer.parseInt(xmlpullparser.getAttributeValue(null, "version"));
                }
            } while(true);
        while(!"blocked-packages".equals(s));
_L5:
        int j;
        String s1;
        do {
            j = xmlpullparser.next();
            if(j == 1)
                break; /* Loop/switch isn't completed */
            s1 = xmlpullparser.getName();
            if(!"package".equals(s1))
                break;
            mBlockedPackages.add(xmlpullparser.getAttributeValue(null, "name"));
        } while(true);
          goto _L3
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        IoUtils.closeQuietly(fileinputstream);
_L2:
        hashset;
        JVM INSTR monitorexit ;
        return;
_L3:
        boolean flag = "blocked-packages".equals(s1);
        if(!flag || j != 3) goto _L5; else goto _L4
        IOException ioexception;
        ioexception;
        Log.wtf("NotificationService", "Unable to read blocked notifications database", ioexception);
        IoUtils.closeQuietly(fileinputstream);
          goto _L2
        Exception exception;
        exception;
        throw exception;
        NumberFormatException numberformatexception;
        numberformatexception;
        Log.wtf("NotificationService", "Unable to parse blocked notifications database", numberformatexception);
        IoUtils.closeQuietly(fileinputstream);
          goto _L2
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Log.wtf("NotificationService", "Unable to parse blocked notifications database", xmlpullparserexception);
        IoUtils.closeQuietly(fileinputstream);
          goto _L2
        Exception exception1;
        exception1;
        IoUtils.closeQuietly(fileinputstream);
        throw exception1;
        IoUtils.closeQuietly(fileinputstream);
          goto _L2
    }

    private void scheduleTimeoutLocked(ToastRecord toastrecord, boolean flag) {
        Message message = Message.obtain(mHandler, 2, toastrecord);
        long l;
        if(flag) {
            l = 0L;
        } else {
            int i;
            if(toastrecord.duration == 1)
                i = 3500;
            else
                i = 2000;
            l = i;
        }
        mHandler.removeCallbacksAndMessages(toastrecord);
        mHandler.sendMessageDelayed(message, l);
    }

    private void sendAccessibilityEvent(Notification notification, CharSequence charsequence) {
        AccessibilityManager accessibilitymanager = AccessibilityManager.getInstance(mContext);
        if(accessibilitymanager.isEnabled()) {
            AccessibilityEvent accessibilityevent = AccessibilityEvent.obtain(64);
            accessibilityevent.setPackageName(charsequence);
            accessibilityevent.setClassName(android/app/Notification.getName());
            accessibilityevent.setParcelableData(notification);
            CharSequence charsequence1 = notification.tickerText;
            if(!TextUtils.isEmpty(charsequence1))
                accessibilityevent.getText().add(charsequence1);
            accessibilitymanager.sendAccessibilityEvent(accessibilityevent);
        }
    }

    private void showNextToastLocked() {
        ToastRecord toastrecord = (ToastRecord)mToastQueue.get(0);
_L2:
        if(toastrecord == null)
            break MISSING_BLOCK_LABEL_31;
        toastrecord.callback.show();
        scheduleTimeoutLocked(toastrecord, false);
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("NotificationService", (new StringBuilder()).append("Object died trying to show notification ").append(toastrecord.callback).append(" in package ").append(toastrecord.pkg).toString());
        int i = mToastQueue.indexOf(toastrecord);
        if(i >= 0)
            mToastQueue.remove(i);
        keepProcessAliveLocked(toastrecord.pid);
        if(mToastQueue.size() > 0)
            toastrecord = (ToastRecord)mToastQueue.get(0);
        else
            toastrecord = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void updateLightsLocked() {
        if(mLedNotification == null) {
            int l = mLights.size();
            if(l > 0)
                mLedNotification = (NotificationRecord)mLights.get(l - 1);
        }
        if(mLedNotification != null && !mInCall && !mScreenOn) goto _L2; else goto _L1
_L1:
        mNotificationLight.turnOff();
_L4:
        return;
_L2:
        int i = mLedNotification.notification.ledARGB;
        int j = mLedNotification.notification.ledOnMS;
        int k = mLedNotification.notification.ledOffMS;
        if((4 & mLedNotification.notification.defaults) != 0) {
            mDefaultNotificationColor;
            mDefaultNotificationLedOn;
            mDefaultNotificationLedOff;
            Injector.updateNotificationLight(this);
            i = mLedNotification.notification.ledARGB;
            j = mLedNotification.notification.ledOnMS;
            k = mLedNotification.notification.ledOffMS;
        }
        if(mNotificationPulseEnabled)
            mNotificationLight.setFlashing(i, 1, j, k);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateNotificationPulse() {
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        updateLightsLocked();
        return;
    }

    private void writeBlockDb() {
        HashSet hashset = mBlockedPackages;
        hashset;
        JVM INSTR monitorenter ;
        Exception exception;
        java.io.FileOutputStream fileoutputstream = null;
        FastXmlSerializer fastxmlserializer;
        try {
            fileoutputstream = mPolicyFile.startWrite();
            fastxmlserializer = new FastXmlSerializer();
            fastxmlserializer.setOutput(fileoutputstream, "utf-8");
            fastxmlserializer.startDocument(null, Boolean.valueOf(true));
            fastxmlserializer.startTag(null, "notification-policy");
            fastxmlserializer.attribute(null, "version", String.valueOf(1));
            fastxmlserializer.startTag(null, "blocked-packages");
            for(Iterator iterator = mBlockedPackages.iterator(); iterator.hasNext(); fastxmlserializer.endTag(null, "package")) {
                String s = (String)iterator.next();
                fastxmlserializer.startTag(null, "package");
                fastxmlserializer.attribute(null, "name", s);
            }

            break MISSING_BLOCK_LABEL_172;
        }
        catch(IOException ioexception) { }
        finally { }
        if(fileoutputstream == null)
            break MISSING_BLOCK_LABEL_169;
        mPolicyFile.failWrite(fileoutputstream);
_L1:
        hashset;
        JVM INSTR monitorexit ;
        return;
        fastxmlserializer.endTag(null, "blocked-packages");
        fastxmlserializer.endTag(null, "notification-policy");
        fastxmlserializer.endDocument();
        mPolicyFile.finishWrite(fileoutputstream);
          goto _L1
        hashset;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean areNotificationsEnabledForPackage(String s) {
        checkCallerIsSystem();
        return areNotificationsEnabledForPackageInt(s);
    }

    void cancelAll() {
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        int i = -1 + mNotificationList.size();
        do {
            if(i >= 0) {
                NotificationRecord notificationrecord = (NotificationRecord)mNotificationList.get(i);
                if((0x22 & notificationrecord.notification.flags) == 0) {
                    mNotificationList.remove(i);
                    cancelNotificationLocked(notificationrecord, true);
                }
            } else {
                updateLightsLocked();
                return;
            }
            i--;
        } while(true);
    }

    public void cancelAllNotifications(String s) {
        checkCallerIsSystemOrSameApp(s);
        cancelAllNotificationsInt(s, 0, 64, true);
    }

    boolean cancelAllNotificationsInt(String s, int i, int j, boolean flag) {
        boolean flag1;
        flag1 = true;
        Object aobj[] = new Object[3];
        aobj[0] = s;
        aobj[flag1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        EventLog.writeEvent(2752, aobj);
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        boolean flag2;
        int l;
        int k = mNotificationList.size();
        flag2 = false;
        l = k - 1;
_L2:
        if(l >= 0) {
            NotificationRecord notificationrecord = (NotificationRecord)mNotificationList.get(l);
            if((i & notificationrecord.notification.flags) == i && (j & notificationrecord.notification.flags) == 0 && notificationrecord.pkg.equals(s)) {
                flag2 = true;
                if(!flag)
                    break; /* Loop/switch isn't completed */
                mNotificationList.remove(l);
                cancelNotificationLocked(notificationrecord, false);
            }
            break MISSING_BLOCK_LABEL_189;
        }
        break MISSING_BLOCK_LABEL_170;
        Exception exception;
        exception;
        throw exception;
        if(!flag2)
            break MISSING_BLOCK_LABEL_179;
        updateLightsLocked();
        arraylist;
        JVM INSTR monitorexit ;
        flag1 = flag2;
        break; /* Loop/switch isn't completed */
        l--;
        if(true) goto _L2; else goto _L1
_L1:
        return flag1;
    }

    public void cancelNotification(String s, int i) {
        cancelNotificationWithTag(s, null, i);
    }

    public void cancelNotificationWithTag(String s, String s1, int i) {
        checkCallerIsSystemOrSameApp(s);
        int j;
        if(Binder.getCallingUid() == 1000)
            j = 0;
        else
            j = 64;
        cancelNotification(s, s1, i, 0, j, false);
    }

    public void cancelToast(String s, ITransientNotification itransientnotification) {
        Slog.i("NotificationService", (new StringBuilder()).append("cancelToast pkg=").append(s).append(" callback=").append(itransientnotification).toString());
        if(s != null && itransientnotification != null) goto _L2; else goto _L1
_L1:
        Slog.e("NotificationService", (new StringBuilder()).append("Not cancelling notification. pkg=").append(s).append(" callback=").append(itransientnotification).toString());
_L3:
        return;
_L2:
        ArrayList arraylist = mToastQueue;
        arraylist;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        int i = indexOfToastLocked(s, itransientnotification);
        if(i < 0)
            break MISSING_BLOCK_LABEL_133;
        cancelToastLocked(i);
_L4:
        Binder.restoreCallingIdentity(l);
        arraylist;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Slog.w("NotificationService", (new StringBuilder()).append("Toast already cancelled. pkg=").append(s).append(" callback=").append(itransientnotification).toString());
          goto _L4
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
          goto _L3
    }

    void checkCallerIsSystem() {
        int i = Binder.getCallingUid();
        if(i == 1000 || i == 0)
            return;
        else
            throw new SecurityException((new StringBuilder()).append("Disallowed call for uid ").append(i).toString());
    }

    void checkCallerIsSystemOrSameApp(String s) {
        int i = Binder.getCallingUid();
        if(i != 1000 && i != 0) goto _L2; else goto _L1
_L1:
        ApplicationInfo applicationinfo;
        return;
_L2:
        if(UserId.isSameApp((applicationinfo = mContext.getPackageManager().getApplicationInfo(s, 0)).uid, i)) goto _L1; else goto _L3
_L3:
        throw new SecurityException((new StringBuilder()).append("Calling uid ").append(i).append(" gave package").append(s).append(" which is owned by uid ").append(applicationinfo.uid).toString());
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        throw new SecurityException((new StringBuilder()).append("Unknown package ").append(s).toString());
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump NotificationManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L5:
        return;
_L2:
        printwriter.println("Current Notification Manager state:");
        synchronized(mToastQueue) {
            int i = mToastQueue.size();
            if(i > 0) {
                printwriter.println("  Toast Queue:");
                for(int j1 = 0; j1 < i; j1++)
                    ((ToastRecord)mToastQueue.get(j1)).dump(printwriter, "    ");

                printwriter.println("  ");
            }
        }
        ArrayList arraylist1 = mNotificationList;
        arraylist1;
        JVM INSTR monitorenter ;
        int j;
        int i1;
        j = mNotificationList.size();
        if(j <= 0)
            break MISSING_BLOCK_LABEL_218;
        printwriter.println("  Notification List:");
        i1 = 0;
_L3:
        if(i1 >= j)
            break MISSING_BLOCK_LABEL_211;
        ((NotificationRecord)mNotificationList.get(i1)).dump(printwriter, "    ", mContext);
        i1++;
          goto _L3
        exception;
        arraylist;
        JVM INSTR monitorexit ;
        throw exception;
        printwriter.println("  ");
        int k = mLights.size();
        if(k > 0) {
            printwriter.println("  Lights List:");
            for(int l = 0; l < k; l++)
                ((NotificationRecord)mLights.get(l)).dump(printwriter, "    ", mContext);

            printwriter.println("  ");
        }
        printwriter.println((new StringBuilder()).append("  mSoundNotification=").append(mSoundNotification).toString());
        printwriter.println((new StringBuilder()).append("  mVibrateNotification=").append(mVibrateNotification).toString());
        printwriter.println((new StringBuilder()).append("  mDisabledNotifications=0x").append(Integer.toHexString(mDisabledNotifications)).toString());
        printwriter.println((new StringBuilder()).append("  mSystemReady=").append(mSystemReady).toString());
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception1;
        exception1;
        throw exception1;
    }

    public void enqueueNotification(String s, int i, Notification notification, int ai[]) {
        enqueueNotificationWithTag(s, null, i, notification, ai);
    }

    public void enqueueNotificationInternal(String s, int i, int j, String s1, int k, Notification notification, int ai[]) {
        boolean flag;
        checkCallerIsSystemOrSameApp(s);
        flag = "android".equals(s);
        if(flag) goto _L2; else goto _L1
_L1:
        ArrayList arraylist1 = mNotificationList;
        arraylist1;
        JVM INSTR monitorenter ;
        int i2 = 0;
        int j2;
        int k2;
        j2 = mNotificationList.size();
        k2 = 0;
_L42:
        if(k2 >= j2) goto _L4; else goto _L3
_L3:
        if(!((NotificationRecord)mNotificationList.get(k2)).pkg.equals(s) || ++i2 < 50) goto _L6; else goto _L5
_L5:
        Slog.e("NotificationService", (new StringBuilder()).append("Package has already posted ").append(i2).append(" notifications.  Not showing more.  package=").append(s).toString());
        arraylist1;
        JVM INSTR monitorexit ;
          goto _L7
_L4:
        arraylist1;
        JVM INSTR monitorexit ;
_L2:
        if(!s.equals("com.android.providers.downloads") || Log.isLoggable("DownloadManager", 2)) {
            Object aobj[] = new Object[4];
            aobj[0] = s;
            aobj[1] = Integer.valueOf(k);
            aobj[2] = s1;
            aobj[3] = notification.toString();
            EventLog.writeEvent(2750, aobj);
        }
        if(s == null || notification == null)
            throw new IllegalArgumentException((new StringBuilder()).append("null not allowed: pkg=").append(s).append(" id=").append(k).append(" notification=").append(notification).toString());
        break MISSING_BLOCK_LABEL_259;
        Exception exception5;
        exception5;
        arraylist1;
        JVM INSTR monitorexit ;
        throw exception5;
        int l;
        if(notification.icon != 0 && notification.contentView == null)
            throw new IllegalArgumentException((new StringBuilder()).append("contentView required: pkg=").append(s).append(" id=").append(k).append(" notification=").append(notification).toString());
        notification.priority = clamp(notification.priority, -2, 2);
        if((0x80 & notification.flags) != 0 && notification.priority < 2)
            notification.priority = 2;
        l = 10 * notification.priority;
        if(!flag && !areNotificationsEnabledForPackageInt(s)) {
            l = -1000;
            Slog.e("NotificationService", (new StringBuilder()).append("Suppressing notification from package ").append(s).append(" by user request.").toString());
        }
        if(l < -20) goto _L7; else goto _L8
_L8:
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        NotificationRecord notificationrecord;
        NotificationRecord notificationrecord1;
        int i1;
        notificationrecord = new NotificationRecord(s, s1, k, i, j, l, notification);
        notificationrecord1 = null;
        i1 = indexOfNotificationLocked(s, s1, k);
        if(i1 >= 0) goto _L10; else goto _L9
_L9:
        mNotificationList.add(notificationrecord);
_L35:
        if((0x40 & notification.flags) != 0)
            notification.flags = 0x22 | notification.flags;
        if(notification.icon == 0) goto _L12; else goto _L11
_L11:
        StatusBarNotification statusbarnotification = new StatusBarNotification(s, k, s1, notificationrecord.uid, notificationrecord.initialPid, l, notification);
        if(notificationrecord1 == null || notificationrecord1.statusBarKey == null) goto _L14; else goto _L13
_L13:
        long l3;
        notificationrecord.statusBarKey = notificationrecord1.statusBarKey;
        l3 = Binder.clearCallingIdentity();
        mStatusBar.updateNotification(notificationrecord.statusBarKey, statusbarnotification);
        Binder.restoreCallingIdentity(l3);
_L36:
        sendAccessibilityEvent(notification, s);
_L38:
        if((0x40000 & mDisabledNotifications) != 0 || notificationrecord1 != null && (8 & notification.flags) != 0 || !mSystemReady) goto _L16; else goto _L15
_L15:
        AudioManager audiomanager = (AudioManager)mContext.getSystemService("audio");
        if((1 & notification.defaults) == 0) goto _L18; else goto _L17
_L17:
        boolean flag1 = true;
_L43:
        if(!flag1 && notification.sound == null) goto _L20; else goto _L19
_L19:
        if(!flag1) goto _L22; else goto _L21
_L21:
        Uri uri = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
_L39:
        if((4 & notification.flags) == 0) goto _L24; else goto _L23
_L23:
        boolean flag2 = true;
_L44:
        if(notification.audioStreamType < 0) goto _L26; else goto _L25
_L25:
        int j1 = notification.audioStreamType;
_L45:
        mSoundNotification = notificationrecord;
        if(audiomanager.getStreamVolume(j1) == 0) goto _L20; else goto _L27
_L27:
        long l2 = Binder.clearCallingIdentity();
        IRingtonePlayer iringtoneplayer = mAudioService.getRingtonePlayer();
        if(iringtoneplayer != null)
            iringtoneplayer.playAsync(uri, flag2, j1);
        Binder.restoreCallingIdentity(l2);
_L20:
        if((2 & notification.defaults) == 0) goto _L29; else goto _L28
_L28:
        boolean flag3 = true;
_L46:
        if(!flag3 && notification.vibrate == null || audiomanager.getRingerMode() == 0) goto _L16; else goto _L30
_L30:
        Vibrator vibrator;
        mVibrateNotification = notificationrecord;
        vibrator = mVibrator;
        if(!flag3) goto _L32; else goto _L31
_L31:
        long al[] = DEFAULT_VIBRATE_PATTERN;
_L40:
        int k1;
        if((4 & notification.flags) == 0)
            break MISSING_BLOCK_LABEL_1213;
        k1 = 0;
_L47:
        vibrator.vibrate(al, k1);
_L16:
        mLights.remove(notificationrecord1);
        if(mLedNotification == notificationrecord1)
            mLedNotification = null;
        if((1 & notification.flags) == 0) goto _L34; else goto _L33
_L33:
        mLights.add(notificationrecord);
        updateLightsLocked();
_L41:
        arraylist;
        JVM INSTR monitorexit ;
        ai[0] = k;
          goto _L7
_L10:
        notificationrecord1 = (NotificationRecord)mNotificationList.remove(i1);
        mNotificationList.add(i1, notificationrecord);
        if(notificationrecord1 != null)
            notification.flags = notification.flags | 0x40 & notificationrecord1.notification.flags;
          goto _L35
        Exception exception;
        exception;
        throw exception;
        Exception exception3;
        exception3;
        Binder.restoreCallingIdentity(l3);
        throw exception3;
_L14:
        long l1 = Binder.clearCallingIdentity();
        notificationrecord.statusBarKey = mStatusBar.addNotification(statusbarnotification);
        if((1 & statusbarnotification.notification.flags) != 0)
            mAttentionLight.pulse();
        Binder.restoreCallingIdentity(l1);
          goto _L36
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l1);
        throw exception1;
_L12:
        Slog.e("NotificationService", (new StringBuilder()).append("Ignoring notification with icon==0: ").append(notification).toString());
        if(notificationrecord1 == null || notificationrecord1.statusBarKey == null) goto _L38; else goto _L37
_L37:
        long l4 = Binder.clearCallingIdentity();
        mStatusBar.removeNotification(notificationrecord1.statusBarKey);
        Binder.restoreCallingIdentity(l4);
          goto _L38
        Exception exception4;
        exception4;
        Binder.restoreCallingIdentity(l4);
        throw exception4;
_L22:
        uri = notification.sound;
          goto _L39
        Exception exception2;
        exception2;
        Binder.restoreCallingIdentity(l2);
        throw exception2;
_L32:
        al = notification.vibrate;
          goto _L40
_L34:
        if(notificationrecord1 != null && (1 & notificationrecord1.notification.flags) != 0)
            updateLightsLocked();
          goto _L41
        RemoteException remoteexception;
        remoteexception;
        Binder.restoreCallingIdentity(l2);
          goto _L20
_L7:
        return;
_L6:
        k2++;
          goto _L42
_L18:
        flag1 = false;
          goto _L43
_L24:
        flag2 = false;
          goto _L44
_L26:
        j1 = 5;
          goto _L45
_L29:
        flag3 = false;
          goto _L46
        k1 = -1;
          goto _L47
    }

    public void enqueueNotificationWithTag(String s, String s1, int i, Notification notification, int ai[]) {
        enqueueNotificationInternal(s, Binder.getCallingUid(), Binder.getCallingPid(), s1, i, notification, ai);
    }

    public void enqueueToast(String s, ITransientNotification itransientnotification, int i) {
        boolean flag;
        if(s == null || itransientnotification == null) {
            Slog.e("NotificationService", (new StringBuilder()).append("Not doing toast. pkg=").append(s).append(" callback=").append(itransientnotification).toString());
        } else {
label0:
            {
                flag = "android".equals(s);
                if(flag || areNotificationsEnabledForPackageInt(s))
                    break label0;
                Slog.e("NotificationService", (new StringBuilder()).append("Suppressing toast from package ").append(s).append(" by user request.").toString());
            }
        }
_L3:
        return;
        arraylist;
        JVM INSTR monitorenter ;
        int j;
        long l;
        j = Binder.getCallingPid();
        l = Binder.clearCallingIdentity();
        int k = indexOfToastLocked(s, itransientnotification);
        if(k < 0) goto _L2; else goto _L1
_L1:
        ((ToastRecord)mToastQueue.get(k)).update(i);
_L7:
        if(k == 0)
            showNextToastLocked();
        Binder.restoreCallingIdentity(l);
        arraylist;
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L2:
        int i1;
        if(flag)
            break MISSING_BLOCK_LABEL_291;
        i1 = 0;
        int j1;
        int k1;
        j1 = mToastQueue.size();
        k1 = 0;
_L6:
        if(k1 >= j1)
            break MISSING_BLOCK_LABEL_291;
        if(!((ToastRecord)mToastQueue.get(k1)).pkg.equals(s) || ++i1 < 50) goto _L5; else goto _L4
_L4:
        Slog.e("NotificationService", (new StringBuilder()).append("Package has already posted ").append(i1).append(" toasts. Not showing more. Package=").append(s).toString());
        Binder.restoreCallingIdentity(l);
        arraylist;
        JVM INSTR monitorexit ;
          goto _L3
_L5:
        k1++;
          goto _L6
        ToastRecord toastrecord = new ToastRecord(j, s, itransientnotification, i);
        mToastQueue.add(toastrecord);
        k = -1 + mToastQueue.size();
        keepProcessAliveLocked(j);
          goto _L7
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
          goto _L3
    }

    int getDefaultNotificationColor() {
        return mDefaultNotificationColor;
    }

    NotificationRecord getLedNotification() {
        return mLedNotification;
    }

    public void setNotificationsEnabledForPackage(String s, boolean flag) {
        checkCallerIsSystem();
        if(!flag) goto _L2; else goto _L1
_L1:
        mBlockedPackages.remove(s);
_L4:
        writeBlockDb();
        return;
_L2:
        mBlockedPackages.add(s);
        ArrayList arraylist = mNotificationList;
        arraylist;
        JVM INSTR monitorenter ;
        int i = mNotificationList.size();
        int j = 0;
        while(j < i)  {
            NotificationRecord notificationrecord = (NotificationRecord)mNotificationList.get(j);
            if(notificationrecord.pkg.equals(s))
                cancelNotificationLocked(notificationrecord, false);
            j++;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    void systemReady() {
        mAudioService = android.media.IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        mSystemReady = true;
    }

    private static final String ATTR_NAME = "name";
    private static final String ATTR_VERSION = "version";
    private static final boolean DBG = false;
    private static final int DB_VERSION = 1;
    private static final int DEFAULT_STREAM_TYPE = 5;
    private static final long DEFAULT_VIBRATE_PATTERN[];
    private static final boolean ENABLE_BLOCKED_NOTIFICATIONS = true;
    private static final boolean ENABLE_BLOCKED_TOASTS = true;
    private static final int JUNK_SCORE = -1000;
    private static final int LONG_DELAY = 3500;
    private static final int MAX_PACKAGE_NOTIFICATIONS = 50;
    private static final int MESSAGE_TIMEOUT = 2;
    private static final int NOTIFICATION_PRIORITY_MULTIPLIER = 10;
    private static final int SCORE_DISPLAY_THRESHOLD = -20;
    private static final boolean SCORE_ONGOING_HIGHER = false;
    private static final int SHORT_DELAY = 2000;
    private static final String TAG = "NotificationService";
    private static final String TAG_BLOCKED_PKGS = "blocked-packages";
    private static final String TAG_BODY = "notification-policy";
    private static final String TAG_PACKAGE = "package";
    final IActivityManager mAm = ActivityManagerNative.getDefault();
    private LightsService.Light mAttentionLight;
    private IAudioService mAudioService;
    private HashSet mBlockedPackages;
    final Context mContext;
    private int mDefaultNotificationColor;
    private int mDefaultNotificationLedOff;
    private int mDefaultNotificationLedOn;
    private int mDisabledNotifications;
    final IBinder mForegroundToken = new Binder();
    private WorkerHandler mHandler;
    private boolean mInCall;
    private BroadcastReceiver mIntentReceiver;
    private NotificationRecord mLedNotification;
    private ArrayList mLights;
    private StatusBarManagerService.NotificationCallbacks mNotificationCallbacks;
    private LightsService.Light mNotificationLight;
    private final ArrayList mNotificationList = new ArrayList();
    private boolean mNotificationPulseEnabled;
    private AtomicFile mPolicyFile;
    private boolean mScreenOn;
    private NotificationRecord mSoundNotification;
    private StatusBarManagerService mStatusBar;
    private boolean mSystemReady;
    private ArrayList mToastQueue;
    private NotificationRecord mVibrateNotification;
    private Vibrator mVibrator;

    static  {
        long al[] = new long[4];
        al[0] = 0L;
        al[1] = 250L;
        al[2] = 250L;
        al[3] = 250L;
        DEFAULT_VIBRATE_PATTERN = al;
    }





/*
    static boolean access$1102(NotificationManagerService notificationmanagerservice, boolean flag) {
        notificationmanagerservice.mScreenOn = flag;
        return flag;
    }

*/


/*
    static boolean access$1202(NotificationManagerService notificationmanagerservice, boolean flag) {
        notificationmanagerservice.mInCall = flag;
        return flag;
    }

*/





/*
    static boolean access$1502(NotificationManagerService notificationmanagerservice, boolean flag) {
        notificationmanagerservice.mNotificationPulseEnabled = flag;
        return flag;
    }

*/




/*
    static int access$202(NotificationManagerService notificationmanagerservice, int i) {
        notificationmanagerservice.mDisabledNotifications = i;
        return i;
    }

*/





/*
    static NotificationRecord access$602(NotificationManagerService notificationmanagerservice, NotificationRecord notificationrecord) {
        notificationmanagerservice.mSoundNotification = notificationrecord;
        return notificationrecord;
    }

*/


/*
    static NotificationRecord access$702(NotificationManagerService notificationmanagerservice, NotificationRecord notificationrecord) {
        notificationmanagerservice.mVibrateNotification = notificationrecord;
        return notificationrecord;
    }

*/



/*
    static NotificationRecord access$902(NotificationManagerService notificationmanagerservice, NotificationRecord notificationrecord) {
        notificationmanagerservice.mLedNotification = notificationrecord;
        return notificationrecord;
    }

*/
}
