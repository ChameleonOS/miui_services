// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.accounts.AccountManagerService;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.*;
import android.content.pm.IPackageManager;
import android.content.res.Resources;
import android.media.AudioService;
import android.net.wifi.p2p.WifiP2pService;
import android.os.*;
import android.server.BluetoothA2dpService;
import android.server.BluetoothService;
import android.server.search.SearchManagerService;
import android.service.dreams.DreamManagerService;
import android.util.*;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.os.BinderInternal;
import com.android.internal.widget.LockSettingsService;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.am.ActivityManagerService;
import com.android.server.input.InputManagerService;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.net.NetworkStatsService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.ShutdownThread;
import com.android.server.usb.UsbService;
import com.android.server.wm.WindowManagerService;
import dalvik.system.VMRuntime;
import dalvik.system.Zygote;
import java.io.File;

// Referenced classes of package com.android.server:
//            EntropyMixer, PowerManagerService, TelephonyRegistry, AttributeCache, 
//            MiuiLightsService, BatteryService, VibratorService, AlarmManagerService, 
//            Watchdog, InputMethodManagerService, MountService, DevicePolicyManagerService, 
//            StatusBarManagerService, ClipboardService, NetworkManagementService, TextServicesManagerService, 
//            WifiService, ConnectivityService, NsdService, ThrottleService, 
//            UpdateLockService, NotificationManagerService, DeviceStorageMonitorService, LocationManagerService, 
//            CountryDetectorService, DropBoxManagerService, WallpaperManagerService, DockObserver, 
//            WiredAccessoryObserver, SerialService, UiModeManagerService, BackupManagerService, 
//            AppWidgetService, RecognitionManagerService, DiskStatsService, SamplingProfilerService, 
//            NetworkTimeUpdateService, CommonTimeManagementService, CertBlacklister

class ServerThread extends Thread {

    ServerThread() {
    }

    static final void startSystemUi(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.SystemUIService"));
        Slog.d("SystemServer", (new StringBuilder()).append("Starting service: ").append(intent).toString());
        context.startService(intent);
    }

    void reportWtf(String s, Throwable throwable) {
        Slog.w("SystemServer", "***********************************************");
        Log.wtf("SystemServer", (new StringBuilder()).append("BOOT FAILURE ").append(s).toString(), throwable);
    }

    public void run() {
        int i;
        AccountManagerService accountmanagerservice;
        MiuiLightsService miuilightsservice;
        VibratorService vibratorservice;
        NetworkStatsService networkstatsservice;
        ConnectivityService connectivityservice;
        WifiP2pService wifip2pservice;
        WifiService wifiservice;
        Context context;
        BluetoothService bluetoothservice;
        UsbService usbservice;
        ThrottleService throttleservice;
        CommonTimeManagementService commontimemanagementservice;
        AlarmManagerService alarmmanagerservice;
        BatteryService batteryservice;
        PowerManagerService powermanagerservice;
        DevicePolicyManagerService devicepolicymanagerservice;
        StatusBarManagerService statusbarmanagerservice;
        InputMethodManagerService inputmethodmanagerservice;
        AppWidgetService appwidgetservice;
        NotificationManagerService notificationmanagerservice;
        WallpaperManagerService wallpapermanagerservice;
        LocationManagerService locationmanagerservice;
        CountryDetectorService countrydetectorservice;
        TextServicesManagerService textservicesmanagerservice;
        LockSettingsService locksettingsservice;
        DreamManagerService dreammanagerservice;
        NetworkPolicyManagerService networkpolicymanagerservice;
        MountService mountservice;
        DreamManagerService dreammanagerservice1;
        CommonTimeManagementService commontimemanagementservice1;
        AppWidgetService appwidgetservice1;
        UsbService usbservice1;
        WallpaperManagerService wallpapermanagerservice1;
        CountryDetectorService countrydetectorservice1;
        LocationManagerService locationmanagerservice1;
        NotificationManagerService notificationmanagerservice1;
        ThrottleService throttleservice1;
        ConnectivityService connectivityservice1;
        WifiService wifiservice1;
        WifiP2pService wifip2pservice1;
        NetworkStatsService networkstatsservice1;
        TextServicesManagerService textservicesmanagerservice1;
        StatusBarManagerService statusbarmanagerservice1;
        DevicePolicyManagerService devicepolicymanagerservice1;
        LockSettingsService locksettingsservice1;
        MountService mountservice1;
        InputMethodManagerService inputmethodmanagerservice1;
        String s2;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        MiuiLightsService miuilightsservice1;
        VibratorService vibratorservice1;
        boolean flag4;
        boolean flag5;
        AccountManagerService accountmanagerservice1;
        EventLog.writeEvent(3010, SystemClock.uptimeMillis());
        Looper.prepare();
        Process.setThreadPriority(-2);
        BinderInternal.disableBackgroundScheduling(true);
        Process.setCanSelfBackground(false);
        String s = SystemProperties.get("sys.shutdown.requested", "");
        String s1;
        if(s != null && s.length() > 0) {
            final boolean headless;
            ContentService contentservice;
            NetworkManagementService networkmanagementservice;
            IPackageManager ipackagemanager;
            WindowManagerService windowmanagerservice;
            DockObserver dockobserver;
            UiModeManagerService uimodemanagerservice;
            RecognitionManagerService recognitionmanagerservice;
            NetworkTimeUpdateService networktimeupdateservice;
            InputManagerService inputmanagerservice;
            final boolean safeMode;
            android.content.res.Configuration configuration;
            DisplayMetrics displaymetrics;
            final Context contextF;
            final BatteryService batteryF;
            final NetworkManagementService networkManagementF;
            final NetworkStatsService networkStatsF;
            final NetworkPolicyManagerService networkPolicyF;
            final ConnectivityService connectivityF;
            final DockObserver dockF;
            final UsbService usbF;
            final ThrottleService throttleF;
            final UiModeManagerService uiModeF;
            final AppWidgetService appWidgetF;
            final WallpaperManagerService wallpaperF;
            final InputMethodManagerService immF;
            final RecognitionManagerService recognitionF;
            final LocationManagerService locationF;
            final CountryDetectorService countryDetectorF;
            final NetworkTimeUpdateService networkTimeUpdaterF;
            final CommonTimeManagementService commonTimeMgmtServiceF;
            final TextServicesManagerService textServiceManagerServiceF;
            final StatusBarManagerService statusBarF;
            final DreamManagerService dreamyF;
            final InputManagerService inputManagerF;
            final BluetoothService bluetoothF;
            NetworkTimeUpdateService networktimeupdateservice1;
            RecognitionManagerService recognitionmanagerservice1;
            UiModeManagerService uimodemanagerservice1;
            SerialService serialservice;
            DockObserver dockobserver1;
            ActivityManagerService activitymanagerservice;
            IPackageManager ipackagemanager1;
            boolean flag6;
            boolean flag7;
            String s3;
            if(s.charAt(0) == '1')
                flag7 = true;
            else
                flag7 = false;
            if(s.length() > 1)
                s3 = s.substring(1, s.length());
            else
                s3 = null;
            ShutdownThread.rebootOrShutdown(flag7, s3);
        }
        s1 = SystemProperties.get("ro.factorytest");
        if("".equals(s1))
            i = 0;
        else
            i = Integer.parseInt(s1);
        headless = "1".equals(SystemProperties.get("ro.config.headless", "0"));
        accountmanagerservice = null;
        contentservice = null;
        miuilightsservice = null;
        vibratorservice = null;
        networkmanagementservice = null;
        networkstatsservice = null;
        connectivityservice = null;
        wifip2pservice = null;
        wifiservice = null;
        ipackagemanager = null;
        context = null;
        windowmanagerservice = null;
        bluetoothservice = null;
        dockobserver = null;
        usbservice = null;
        uimodemanagerservice = null;
        recognitionmanagerservice = null;
        throttleservice = null;
        networktimeupdateservice = null;
        commontimemanagementservice = null;
        inputmanagerservice = null;
        Slog.i("SystemServer", "Entropy Mixer");
        ServiceManager.addService("entropy", new EntropyMixer());
        Slog.i("SystemServer", "Power Manager");
        powermanagerservice = new PowerManagerService();
        ServiceManager.addService("power", powermanagerservice);
        Slog.i("SystemServer", "Activity Manager");
        context = ActivityManagerService.main(i);
        Slog.i("SystemServer", "Telephony Registry");
        ServiceManager.addService("telephony.registry", new TelephonyRegistry(context));
        Slog.i("SystemServer", "Scheduling Policy");
        ServiceManager.addService("scheduling_policy", new SchedulingPolicyService());
        AttributeCache.init(context);
        Slog.i("SystemServer", "Package Manager");
        s2 = SystemProperties.get("vold.decrypt");
        flag = false;
        if(!"trigger_restart_min_framework".equals(s2)) goto _L2; else goto _L1
_L1:
        Slog.w("SystemServer", "Detected encryption in progress - only parsing core apps");
        flag = true;
          goto _L3
_L69:
        ipackagemanager1 = PackageManagerService.main(context, flag1, flag);
        ipackagemanager = ipackagemanager1;
        flag2 = false;
        flag6 = ipackagemanager.isFirstBoot();
        flag2 = flag6;
_L45:
        ActivityManagerService.setSystemProcess();
        mContentResolver = context.getContentResolver();
        Slog.i("SystemServer", "Account Manager");
        accountmanagerservice1 = new AccountManagerService(context);
        ServiceManager.addService("account", accountmanagerservice1);
        accountmanagerservice = accountmanagerservice1;
_L15:
        Slog.i("SystemServer", "Content Manager");
        if(i != 1) goto _L5; else goto _L4
_L4:
        flag3 = true;
_L17:
        contentservice = ContentService.main(context, flag3);
        Slog.i("SystemServer", "System Content Providers");
        ActivityManagerService.installSystemProviders();
        Slog.i("SystemServer", "Lights Service");
        miuilightsservice1 = new MiuiLightsService(context);
        Slog.i("SystemServer", "Battery Service");
        batteryservice = new BatteryService(context, miuilightsservice1);
        ServiceManager.addService("battery", batteryservice);
        Slog.i("SystemServer", "Vibrator Service");
        vibratorservice1 = new VibratorService(context);
        ServiceManager.addService("vibrator", vibratorservice1);
        powermanagerservice.init(context, miuilightsservice1, ActivityManagerService.self(), batteryservice);
        Slog.i("SystemServer", "Alarm Manager");
        alarmmanagerservice = new AlarmManagerService(context);
        ServiceManager.addService("alarm", alarmmanagerservice);
        Slog.i("SystemServer", "Init Watchdog");
        Watchdog.getInstance().init(context, batteryservice, powermanagerservice, alarmmanagerservice, ActivityManagerService.self());
        Slog.i("SystemServer", "Window Manager");
        if(i == 1) goto _L7; else goto _L6
_L6:
        flag4 = true;
          goto _L8
_L18:
        windowmanagerservice = WindowManagerService.main(context, powermanagerservice, flag4, flag5, flag);
        ServiceManager.addService("window", windowmanagerservice);
        inputmanagerservice = windowmanagerservice.getInputManagerService();
        ServiceManager.addService("input", inputmanagerservice);
        ActivityManagerService.self().setWindowManager(windowmanagerservice);
        if(!SystemProperties.get("ro.kernel.qemu").equals("1")) goto _L10; else goto _L9
_L9:
        Slog.i("SystemServer", "No Bluetooh Service (emulator)");
_L19:
        vibratorservice = vibratorservice1;
        miuilightsservice = miuilightsservice1;
_L16:
        devicepolicymanagerservice = null;
        statusbarmanagerservice = null;
        inputmethodmanagerservice = null;
        appwidgetservice = null;
        notificationmanagerservice = null;
        wallpapermanagerservice = null;
        locationmanagerservice = null;
        countrydetectorservice = null;
        textservicesmanagerservice = null;
        locksettingsservice = null;
        dreammanagerservice = null;
        if(i == 1) goto _L12; else goto _L11
_L11:
        Slog.i("SystemServer", "Input Method Service");
        inputmethodmanagerservice1 = new InputMethodManagerService(context, windowmanagerservice);
        ServiceManager.addService("input_method", inputmethodmanagerservice1);
        inputmethodmanagerservice = inputmethodmanagerservice1;
_L20:
        RuntimeException runtimeexception;
        Throwable throwable47;
        Throwable throwable49;
        BluetoothService bluetoothservice1;
        try {
            Slog.i("SystemServer", "Accessibility Manager");
            ServiceManager.addService("accessibility", new AccessibilityManagerService(context));
        }
        catch(Throwable throwable48) {
            reportWtf("starting Accessibility Manager", throwable48);
        }
_L12:
        try {
            windowmanagerservice.displayReady();
        }
        catch(Throwable throwable) {
            reportWtf("making display ready", throwable);
        }
        try {
            ipackagemanager.performBootDexOpt();
        }
        catch(Throwable throwable1) {
            reportWtf("performing boot dexopt", throwable1);
        }
        Throwable throwable2;
        Throwable throwable3;
        Throwable throwable4;
        Throwable throwable5;
        Throwable throwable6;
        Throwable throwable7;
        Throwable throwable8;
        Throwable throwable9;
        Throwable throwable10;
        Throwable throwable11;
        Throwable throwable12;
        Throwable throwable13;
        Throwable throwable14;
        Throwable throwable15;
        Throwable throwable16;
        Throwable throwable17;
        Throwable throwable18;
        Throwable throwable19;
        Throwable throwable20;
        Throwable throwable21;
        Throwable throwable22;
        Throwable throwable23;
        Throwable throwable24;
        Throwable throwable25;
        Throwable throwable26;
        Throwable throwable27;
        Throwable throwable28;
        Throwable throwable29;
        Throwable throwable30;
        Throwable throwable31;
        Throwable throwable32;
        Throwable throwable33;
        Throwable throwable34;
        Throwable throwable35;
        Throwable throwable36;
        Throwable throwable37;
        Throwable throwable38;
        Throwable throwable39;
        Throwable throwable40;
        Throwable throwable41;
        Throwable throwable42;
        Throwable throwable43;
        Throwable throwable44;
        Throwable throwable45;
        Throwable throwable46;
        RemoteException remoteexception1;
        try {
            ActivityManagerNative.getDefault().showBootMessage(context.getResources().getText(0x10403e5), false);
        }
        catch(RemoteException remoteexception) { }
        if(i == 1) goto _L14; else goto _L13
_L13:
        mountservice = null;
        if("0".equals(SystemProperties.get("system_init.startmountservice")))
            break MISSING_BLOCK_LABEL_866;
        Slog.i("SystemServer", "Mount Service");
        mountservice1 = new MountService(context);
        ServiceManager.addService("mount", mountservice1);
        mountservice = mountservice1;
_L21:
        Slog.i("SystemServer", "LockSettingsService");
        locksettingsservice1 = new LockSettingsService(context);
        ServiceManager.addService("lock_settings", locksettingsservice1);
        locksettingsservice = locksettingsservice1;
_L22:
        Slog.i("SystemServer", "Device Policy");
        devicepolicymanagerservice1 = new DevicePolicyManagerService(context);
        ServiceManager.addService("device_policy", devicepolicymanagerservice1);
        devicepolicymanagerservice = devicepolicymanagerservice1;
_L23:
        Slog.i("SystemServer", "Status Bar");
        statusbarmanagerservice1 = new StatusBarManagerService(context, windowmanagerservice);
        ServiceManager.addService("statusbar", statusbarmanagerservice1);
        statusbarmanagerservice = statusbarmanagerservice1;
_L24:
        BluetoothA2dpService bluetootha2dpservice;
        try {
            Slog.i("SystemServer", "Clipboard Service");
            ServiceManager.addService("clipboard", new ClipboardService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable11) {
            reportWtf("starting Clipboard Service", throwable11);
        }
        try {
            Slog.i("SystemServer", "NetworkManagement Service");
            networkmanagementservice = NetworkManagementService.create(context);
            ServiceManager.addService("network_management", networkmanagementservice);
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable12) {
            reportWtf("starting NetworkManagement Service", throwable12);
        }
        Slog.i("SystemServer", "Text Service Manager Service");
        textservicesmanagerservice1 = new TextServicesManagerService(context);
        ServiceManager.addService("textservices", textservicesmanagerservice1);
        textservicesmanagerservice = textservicesmanagerservice1;
_L25:
        Slog.i("SystemServer", "NetworkStats Service");
        networkstatsservice1 = new NetworkStatsService(context, networkmanagementservice, alarmmanagerservice);
        ServiceManager.addService("netstats", networkstatsservice1);
        networkstatsservice = networkstatsservice1;
_L26:
        Slog.i("SystemServer", "NetworkPolicy Service");
        activitymanagerservice = ActivityManagerService.self();
        networkpolicymanagerservice = new NetworkPolicyManagerService(context, activitymanagerservice, powermanagerservice, networkstatsservice, networkmanagementservice);
        ServiceManager.addService("netpolicy", networkpolicymanagerservice);
_L27:
        Slog.i("SystemServer", "Wi-Fi P2pService");
        wifip2pservice1 = new WifiP2pService(context);
        ServiceManager.addService("wifip2p", wifip2pservice1);
        wifip2pservice = wifip2pservice1;
_L28:
        Slog.i("SystemServer", "Wi-Fi Service");
        wifiservice1 = new WifiService(context);
        ServiceManager.addService("wifi", wifiservice1);
        wifiservice = wifiservice1;
_L29:
        Slog.i("SystemServer", "Connectivity Service");
        connectivityservice1 = new ConnectivityService(context, networkmanagementservice, networkstatsservice, networkpolicymanagerservice);
        ServiceManager.addService("connectivity", connectivityservice1);
        networkstatsservice.bindConnectivityManager(connectivityservice1);
        networkpolicymanagerservice.bindConnectivityManager(connectivityservice1);
        wifiservice.checkAndStartWifi();
        wifip2pservice.connectivityServiceReady();
        connectivityservice = connectivityservice1;
_L30:
        try {
            Slog.i("SystemServer", "Network Service Discovery Service");
            ServiceManager.addService("servicediscovery", NsdService.create(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable19) {
            reportWtf("starting Service Discovery Service", throwable19);
        }
        Slog.i("SystemServer", "Throttle Service");
        throttleservice1 = new ThrottleService(context);
        ServiceManager.addService("throttle", throttleservice1);
        throttleservice = throttleservice1;
_L31:
        try {
            Slog.i("SystemServer", "UpdateLock Service");
            ServiceManager.addService("updatelock", new UpdateLockService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable21) {
            reportWtf("starting UpdateLockService", throwable21);
        }
        if(mountservice != null)
            mountservice.waitForAsecScan();
        if(accountmanagerservice != null)
            try {
                accountmanagerservice.systemReady();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable45) {
                reportWtf("making Account Manager Service ready", throwable45);
            }
        if(contentservice != null)
            try {
                contentservice.systemReady();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable44) {
                reportWtf("making Content Service ready", throwable44);
            }
        Slog.i("SystemServer", "Notification Manager");
        notificationmanagerservice1 = new NotificationManagerService(context, statusbarmanagerservice, miuilightsservice);
        ServiceManager.addService("notification", notificationmanagerservice1);
        networkpolicymanagerservice.bindNotificationManager(notificationmanagerservice1);
        notificationmanagerservice = notificationmanagerservice1;
_L32:
        try {
            Slog.i("SystemServer", "Device Storage Monitor");
            ServiceManager.addService("devicestoragemonitor", new DeviceStorageMonitorService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable23) {
            reportWtf("starting DeviceStorageMonitor service", throwable23);
        }
        Slog.i("SystemServer", "Location Manager");
        locationmanagerservice1 = new LocationManagerService(context);
        ServiceManager.addService("location", locationmanagerservice1);
        locationmanagerservice = locationmanagerservice1;
_L33:
        Slog.i("SystemServer", "Country Detector");
        countrydetectorservice1 = new CountryDetectorService(context);
        ServiceManager.addService("country_detector", countrydetectorservice1);
        countrydetectorservice = countrydetectorservice1;
_L34:
        try {
            Slog.i("SystemServer", "Search Service");
            ServiceManager.addService("search", new SearchManagerService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable26) {
            reportWtf("starting Search Service", throwable26);
        }
        try {
            Slog.i("SystemServer", "DropBox Service");
            ServiceManager.addService("dropbox", new DropBoxManagerService(context, new File("/data/system/dropbox")));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable27) {
            reportWtf("starting DropBoxManagerService", throwable27);
        }
        if(!context.getResources().getBoolean(0x1110024))
            break MISSING_BLOCK_LABEL_1593;
        Slog.i("SystemServer", "Wallpaper Service");
        if(headless)
            break MISSING_BLOCK_LABEL_1593;
        wallpapermanagerservice1 = new WallpaperManagerService(context);
        ServiceManager.addService("wallpaper", wallpapermanagerservice1);
        wallpapermanagerservice = wallpapermanagerservice1;
_L35:
        if(!"0".equals(SystemProperties.get("system_init.startaudioservice")))
            try {
                Slog.i("SystemServer", "Audio Service");
                ServiceManager.addService("audio", new AudioService(context));
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable42) {
                reportWtf("starting Audio Service", throwable42);
            }
        Slog.i("SystemServer", "Dock Observer");
        dockobserver1 = new DockObserver(context, powermanagerservice);
        dockobserver = dockobserver1;
_L36:
        try {
            Slog.i("SystemServer", "Wired Accessory Observer");
            new WiredAccessoryObserver(context);
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable29) {
            reportWtf("starting WiredAccessoryObserver", throwable29);
        }
        Slog.i("SystemServer", "USB Service");
        usbservice1 = new UsbService(context);
        ServiceManager.addService("usb", usbservice1);
        usbservice = usbservice1;
_L37:
        Slog.i("SystemServer", "Serial Service");
        serialservice = new SerialService(context);
        ServiceManager.addService("serial", serialservice);
_L38:
        Slog.i("SystemServer", "UI Mode Manager Service");
        uimodemanagerservice1 = new UiModeManagerService(context);
        uimodemanagerservice = uimodemanagerservice1;
_L39:
        try {
            Slog.i("SystemServer", "Backup Service");
            ServiceManager.addService("backup", new BackupManagerService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable33) {
            Slog.e("SystemServer", "Failure starting Backup Service", throwable33);
        }
        Slog.i("SystemServer", "AppWidget Service");
        appwidgetservice1 = new AppWidgetService(context);
        ServiceManager.addService("appwidget", appwidgetservice1);
        appwidgetservice = appwidgetservice1;
_L40:
        Slog.i("SystemServer", "Recognition Service");
        recognitionmanagerservice1 = new RecognitionManagerService(context);
        recognitionmanagerservice = recognitionmanagerservice1;
_L41:
        try {
            Slog.i("SystemServer", "DiskStats Service");
            ServiceManager.addService("diskstats", new DiskStatsService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable36) {
            reportWtf("starting DiskStats Service", throwable36);
        }
        try {
            Slog.i("SystemServer", "SamplingProfiler Service");
            ServiceManager.addService("samplingprofiler", new SamplingProfilerService(context));
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable37) {
            reportWtf("starting SamplingProfiler Service", throwable37);
        }
        Slog.i("SystemServer", "NetworkTimeUpdateService");
        networktimeupdateservice1 = new NetworkTimeUpdateService(context);
        networktimeupdateservice = networktimeupdateservice1;
_L42:
        Slog.i("SystemServer", "CommonTimeManagementService");
        commontimemanagementservice1 = new CommonTimeManagementService(context);
        ServiceManager.addService("commontime_management", commontimemanagementservice1);
        commontimemanagementservice = commontimemanagementservice1;
_L43:
        try {
            Slog.i("SystemServer", "CertBlacklister");
            new CertBlacklister(context);
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable40) {
            reportWtf("starting CertBlacklister", throwable40);
        }
        if(!context.getResources().getBoolean(0x1110038))
            break MISSING_BLOCK_LABEL_2009;
        Slog.i("SystemServer", "Dreams Service");
        dreammanagerservice1 = new DreamManagerService(context);
        ServiceManager.addService("dreams", dreammanagerservice1);
        dreammanagerservice = dreammanagerservice1;
_L44:
        safeMode = windowmanagerservice.detectSafeMode();
        if(safeMode) {
            ActivityManagerService.self().enterSafeMode();
            Zygote.systemInSafeMode = true;
            VMRuntime.getRuntime().disableJitCompilation();
        } else {
            VMRuntime.getRuntime().startJitCompilation();
        }
        try {
            vibratorservice.systemReady();
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable2) {
            reportWtf("making Vibrator Service ready", throwable2);
        }
        if(devicepolicymanagerservice != null)
            try {
                devicepolicymanagerservice.systemReady();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable7) {
                reportWtf("making Device Policy Service ready", throwable7);
            }
        if(notificationmanagerservice != null)
            try {
                notificationmanagerservice.systemReady();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable6) {
                reportWtf("making Notification Service ready", throwable6);
            }
        try {
            windowmanagerservice.systemReady();
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable3) {
            reportWtf("making Window Manager Service ready", throwable3);
        }
        if(safeMode)
            ActivityManagerService.self().showSafeModeOverlay();
        configuration = windowmanagerservice.computeNewConfiguration();
        displaymetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displaymetrics);
        context.getResources().updateConfiguration(configuration, displaymetrics);
        powermanagerservice.systemReady();
        try {
            ipackagemanager.systemReady();
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable4) {
            reportWtf("making Package Manager Service ready", throwable4);
        }
        try {
            locksettingsservice.systemReady();
        }
        // Misplaced declaration of an exception variable
        catch(Throwable throwable5) {
            reportWtf("making Lock Settings Service ready", throwable5);
        }
        contextF = context;
        batteryF = batteryservice;
        networkManagementF = networkmanagementservice;
        networkStatsF = networkstatsservice;
        networkPolicyF = networkpolicymanagerservice;
        connectivityF = connectivityservice;
        dockF = dockobserver;
        usbF = usbservice;
        throttleF = throttleservice;
        uiModeF = uimodemanagerservice;
        appWidgetF = appwidgetservice;
        wallpaperF = wallpapermanagerservice;
        immF = inputmethodmanagerservice;
        recognitionF = recognitionmanagerservice;
        locationF = locationmanagerservice;
        countryDetectorF = countrydetectorservice;
        networkTimeUpdaterF = networktimeupdateservice;
        commonTimeMgmtServiceF = commontimemanagementservice;
        textServiceManagerServiceF = textservicesmanagerservice;
        statusBarF = statusbarmanagerservice;
        dreamyF = dreammanagerservice;
        inputManagerF = inputmanagerservice;
        bluetoothF = bluetoothservice;
        ActivityManagerService.self().systemReady(new Runnable() {

            public void run() {
                Slog.i("SystemServer", "Making services ready");
                if(!headless)
                    ServerThread.startSystemUi(contextF);
                try {
                    if(batteryF != null)
                        batteryF.systemReady();
                }
                catch(Throwable throwable50) {
                    reportWtf("making Battery Service ready", throwable50);
                }
                try {
                    if(networkManagementF != null)
                        networkManagementF.systemReady();
                }
                catch(Throwable throwable51) {
                    reportWtf("making Network Managment Service ready", throwable51);
                }
                try {
                    if(networkStatsF != null)
                        networkStatsF.systemReady();
                }
                catch(Throwable throwable52) {
                    reportWtf("making Network Stats Service ready", throwable52);
                }
                try {
                    if(networkPolicyF != null)
                        networkPolicyF.systemReady();
                }
                catch(Throwable throwable53) {
                    reportWtf("making Network Policy Service ready", throwable53);
                }
                try {
                    if(connectivityF != null)
                        connectivityF.systemReady();
                }
                catch(Throwable throwable54) {
                    reportWtf("making Connectivity Service ready", throwable54);
                }
                try {
                    if(dockF != null)
                        dockF.systemReady();
                }
                catch(Throwable throwable55) {
                    reportWtf("making Dock Service ready", throwable55);
                }
                try {
                    if(usbF != null)
                        usbF.systemReady();
                }
                catch(Throwable throwable56) {
                    reportWtf("making USB Service ready", throwable56);
                }
                try {
                    if(uiModeF != null)
                        uiModeF.systemReady();
                }
                catch(Throwable throwable57) {
                    reportWtf("making UI Mode Service ready", throwable57);
                }
                try {
                    if(recognitionF != null)
                        recognitionF.systemReady();
                }
                catch(Throwable throwable58) {
                    reportWtf("making Recognition Service ready", throwable58);
                }
                Watchdog.getInstance().start();
                try {
                    if(appWidgetF != null)
                        appWidgetF.systemReady(safeMode);
                }
                catch(Throwable throwable59) {
                    reportWtf("making App Widget Service ready", throwable59);
                }
                try {
                    if(wallpaperF != null)
                        wallpaperF.systemReady();
                }
                catch(Throwable throwable60) {
                    reportWtf("making Wallpaper Service ready", throwable60);
                }
                try {
                    if(immF != null)
                        immF.systemReady(statusBarF);
                }
                catch(Throwable throwable61) {
                    reportWtf("making Input Method Service ready", throwable61);
                }
                try {
                    if(locationF != null)
                        locationF.systemReady();
                }
                catch(Throwable throwable62) {
                    reportWtf("making Location Service ready", throwable62);
                }
                try {
                    if(countryDetectorF != null)
                        countryDetectorF.systemReady();
                }
                catch(Throwable throwable63) {
                    reportWtf("making Country Detector Service ready", throwable63);
                }
                try {
                    if(throttleF != null)
                        throttleF.systemReady();
                }
                catch(Throwable throwable64) {
                    reportWtf("making Throttle Service ready", throwable64);
                }
                try {
                    if(networkTimeUpdaterF != null)
                        networkTimeUpdaterF.systemReady();
                }
                catch(Throwable throwable65) {
                    reportWtf("making Network Time Service ready", throwable65);
                }
                try {
                    if(commonTimeMgmtServiceF != null)
                        commonTimeMgmtServiceF.systemReady();
                }
                catch(Throwable throwable66) {
                    reportWtf("making Common time management service ready", throwable66);
                }
                try {
                    if(textServiceManagerServiceF != null)
                        textServiceManagerServiceF.systemReady();
                }
                catch(Throwable throwable67) {
                    reportWtf("making Text Services Manager Service ready", throwable67);
                }
                try {
                    if(dreamyF != null)
                        dreamyF.systemReady();
                }
                catch(Throwable throwable68) {
                    reportWtf("making DreamManagerService ready", throwable68);
                }
                if(inputManagerF != null)
                    inputManagerF.systemReady(bluetoothF);
_L1:
                return;
                Throwable throwable69;
                throwable69;
                reportWtf("making InputManagerService ready", throwable69);
                  goto _L1
            }

            final ServerThread this$0;
            final AppWidgetService val$appWidgetF;
            final BatteryService val$batteryF;
            final BluetoothService val$bluetoothF;
            final CommonTimeManagementService val$commonTimeMgmtServiceF;
            final ConnectivityService val$connectivityF;
            final Context val$contextF;
            final CountryDetectorService val$countryDetectorF;
            final DockObserver val$dockF;
            final DreamManagerService val$dreamyF;
            final boolean val$headless;
            final InputMethodManagerService val$immF;
            final InputManagerService val$inputManagerF;
            final LocationManagerService val$locationF;
            final NetworkManagementService val$networkManagementF;
            final NetworkPolicyManagerService val$networkPolicyF;
            final NetworkStatsService val$networkStatsF;
            final NetworkTimeUpdateService val$networkTimeUpdaterF;
            final RecognitionManagerService val$recognitionF;
            final boolean val$safeMode;
            final StatusBarManagerService val$statusBarF;
            final TextServicesManagerService val$textServiceManagerServiceF;
            final ThrottleService val$throttleF;
            final UiModeManagerService val$uiModeF;
            final UsbService val$usbF;
            final WallpaperManagerService val$wallpaperF;

             {
                this$0 = ServerThread.this;
                headless = flag;
                contextF = context;
                batteryF = batteryservice;
                networkManagementF = networkmanagementservice;
                networkStatsF = networkstatsservice;
                networkPolicyF = networkpolicymanagerservice;
                connectivityF = connectivityservice;
                dockF = dockobserver;
                usbF = usbservice;
                uiModeF = uimodemanagerservice;
                recognitionF = recognitionmanagerservice;
                appWidgetF = appwidgetservice;
                safeMode = flag1;
                wallpaperF = wallpapermanagerservice;
                immF = inputmethodmanagerservice;
                statusBarF = statusbarmanagerservice;
                locationF = locationmanagerservice;
                countryDetectorF = countrydetectorservice;
                throttleF = throttleservice;
                networkTimeUpdaterF = networktimeupdateservice;
                commonTimeMgmtServiceF = commontimemanagementservice;
                textServiceManagerServiceF = textservicesmanagerservice;
                dreamyF = dreammanagerservice;
                inputManagerF = inputmanagerservice;
                bluetoothF = bluetoothservice;
                super();
            }
        });
        if(StrictMode.conditionallyEnableDebugLogging())
            Slog.i("SystemServer", "Enabled StrictMode for system server main thread.");
        Looper.loop();
        Slog.d("SystemServer", "System ServerThread is exiting!");
        return;
_L2:
        if("1".equals(s2)) {
            Slog.w("SystemServer", "Device encrypted - only parsing core apps");
            flag = true;
        }
          goto _L3
_L68:
        Slog.e("SystemServer", "Failure starting Account Manager", throwable49);
          goto _L15
        runtimeexception;
        alarmmanagerservice = null;
        batteryservice = null;
_L67:
        Slog.e("System", "******************************************");
        Slog.e("System", "************ Failure starting core service", runtimeexception);
          goto _L16
_L5:
        flag3 = false;
          goto _L17
_L7:
        flag4 = false;
          goto _L8
_L71:
        flag5 = false;
          goto _L18
_L10:
        if(i != 1)
            break MISSING_BLOCK_LABEL_2457;
        Slog.i("SystemServer", "No Bluetooth Service (factory test)");
          goto _L19
        Slog.i("SystemServer", "Bluetooth Service");
        bluetoothservice1 = new BluetoothService(context);
        ServiceManager.addService("bluetooth", bluetoothservice1);
        bluetoothservice1.initAfterRegistration();
        if("0".equals(SystemProperties.get("system_init.startaudioservice")))
            break MISSING_BLOCK_LABEL_2530;
        bluetootha2dpservice = new BluetoothA2dpService(context, bluetoothservice1);
        ServiceManager.addService("bluetooth_a2dp", bluetootha2dpservice);
        bluetoothservice1.initAfterA2dpRegistration();
        if(android.provider.Settings.Secure.getInt(mContentResolver, "bluetooth_on", 0) != 0)
            bluetoothservice1.enable();
        bluetoothservice = bluetoothservice1;
          goto _L19
        throwable47;
_L66:
        reportWtf("starting Input Manager Service", throwable47);
          goto _L20
        throwable46;
_L65:
        reportWtf("starting Mount Service", throwable46);
          goto _L21
        throwable8;
_L64:
        reportWtf("starting LockSettingsService service", throwable8);
          goto _L22
        throwable9;
_L63:
        reportWtf("starting DevicePolicyService", throwable9);
          goto _L23
        throwable10;
_L62:
        reportWtf("starting StatusBarManagerService", throwable10);
          goto _L24
        throwable13;
_L61:
        reportWtf("starting Text Service Manager Service", throwable13);
          goto _L25
        throwable14;
_L60:
        reportWtf("starting NetworkStats Service", throwable14);
          goto _L26
        throwable15;
        networkpolicymanagerservice = null;
_L59:
        reportWtf("starting NetworkPolicy Service", throwable15);
          goto _L27
        throwable16;
_L58:
        reportWtf("starting Wi-Fi P2pService", throwable16);
          goto _L28
        throwable17;
_L57:
        reportWtf("starting Wi-Fi Service", throwable17);
          goto _L29
        throwable18;
_L56:
        reportWtf("starting Connectivity Service", throwable18);
          goto _L30
        throwable20;
_L55:
        reportWtf("starting ThrottleService", throwable20);
          goto _L31
        throwable22;
_L54:
        reportWtf("starting Notification Manager", throwable22);
          goto _L32
        throwable24;
_L53:
        reportWtf("starting Location Manager", throwable24);
          goto _L33
        throwable25;
_L52:
        reportWtf("starting Country Detector", throwable25);
          goto _L34
        throwable43;
_L51:
        reportWtf("starting Wallpaper Service", throwable43);
          goto _L35
        throwable28;
        reportWtf("starting DockObserver", throwable28);
          goto _L36
        throwable30;
_L50:
        reportWtf("starting UsbService", throwable30);
          goto _L37
        throwable31;
_L49:
        Slog.e("SystemServer", "Failure starting SerialService", throwable31);
          goto _L38
        throwable32;
        reportWtf("starting UiModeManagerService", throwable32);
          goto _L39
        throwable34;
_L48:
        reportWtf("starting AppWidget Service", throwable34);
          goto _L40
        throwable35;
        reportWtf("starting Recognition Service", throwable35);
          goto _L41
        throwable38;
        reportWtf("starting NetworkTimeUpdate service", throwable38);
          goto _L42
        throwable39;
_L47:
        reportWtf("starting CommonTimeManagementService service", throwable39);
          goto _L43
        throwable41;
_L46:
        reportWtf("starting DreamManagerService", throwable41);
          goto _L44
        remoteexception1;
          goto _L45
        throwable41;
        dreammanagerservice = dreammanagerservice1;
          goto _L46
        throwable39;
        commontimemanagementservice = commontimemanagementservice1;
          goto _L47
        throwable34;
        appwidgetservice = appwidgetservice1;
          goto _L48
        throwable31;
          goto _L49
        throwable30;
        usbservice = usbservice1;
          goto _L50
        throwable43;
        wallpapermanagerservice = wallpapermanagerservice1;
          goto _L51
        throwable25;
        countrydetectorservice = countrydetectorservice1;
          goto _L52
        throwable24;
        locationmanagerservice = locationmanagerservice1;
          goto _L53
        throwable22;
        notificationmanagerservice = notificationmanagerservice1;
          goto _L54
        throwable20;
        throttleservice = throttleservice1;
          goto _L55
        throwable18;
        connectivityservice = connectivityservice1;
          goto _L56
        throwable17;
        wifiservice = wifiservice1;
          goto _L57
        throwable16;
        wifip2pservice = wifip2pservice1;
          goto _L58
        throwable15;
          goto _L59
        throwable14;
        networkstatsservice = networkstatsservice1;
          goto _L60
        throwable13;
        textservicesmanagerservice = textservicesmanagerservice1;
          goto _L61
        throwable10;
        statusbarmanagerservice = statusbarmanagerservice1;
          goto _L62
        throwable9;
        devicepolicymanagerservice = devicepolicymanagerservice1;
          goto _L63
        throwable8;
        locksettingsservice = locksettingsservice1;
          goto _L64
        throwable46;
        mountservice = mountservice1;
          goto _L65
        throwable47;
        inputmethodmanagerservice = inputmethodmanagerservice1;
          goto _L66
        runtimeexception;
        alarmmanagerservice = null;
        batteryservice = null;
        powermanagerservice = null;
          goto _L67
        runtimeexception;
        alarmmanagerservice = null;
        batteryservice = null;
        accountmanagerservice = accountmanagerservice1;
          goto _L67
        runtimeexception;
        alarmmanagerservice = null;
        batteryservice = null;
        miuilightsservice = miuilightsservice1;
          goto _L67
        runtimeexception;
        alarmmanagerservice = null;
        miuilightsservice = miuilightsservice1;
          goto _L67
        runtimeexception;
        alarmmanagerservice = null;
        vibratorservice = vibratorservice1;
        miuilightsservice = miuilightsservice1;
          goto _L67
        runtimeexception;
        bluetoothservice = bluetoothservice1;
        vibratorservice = vibratorservice1;
        miuilightsservice = miuilightsservice1;
          goto _L67
        runtimeexception;
        bluetoothservice = bluetoothservice1;
        vibratorservice = vibratorservice1;
        miuilightsservice = miuilightsservice1;
          goto _L67
        throwable49;
        accountmanagerservice = accountmanagerservice1;
          goto _L68
_L14:
        networkpolicymanagerservice = null;
          goto _L44
_L3:
        if(i != 0)
            flag1 = true;
        else
            flag1 = false;
          goto _L69
_L8:
        if(flag2) goto _L71; else goto _L70
_L70:
        flag5 = true;
          goto _L18
        throwable49;
          goto _L68
        runtimeexception;
        vibratorservice = vibratorservice1;
        miuilightsservice = miuilightsservice1;
          goto _L67
    }

    private static final String ENCRYPTED_STATE = "1";
    private static final String ENCRYPTING_STATE = "trigger_restart_min_framework";
    private static final String TAG = "SystemServer";
    ContentResolver mContentResolver;
}
