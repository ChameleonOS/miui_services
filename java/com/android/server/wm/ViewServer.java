// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.wm;

import android.util.Slog;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Referenced classes of package com.android.server.wm:
//            WindowManagerService

class ViewServer
    implements Runnable {
    class ViewServerWorker
        implements Runnable, WindowManagerService.WindowChangeListener {

        private boolean windowManagerAutolistLoop() {
            BufferedWriter bufferedwriter;
            mWindowManager.addWindowChangeListener(this);
            bufferedwriter = null;
            BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
_L4:
            if(Thread.interrupted()) goto _L2; else goto _L1
_L1:
            boolean flag = false;
            boolean flag1 = false;
            this;
            JVM INSTR monitorenter ;
            while(!mNeedWindowListUpdate && !mNeedFocusedWindowUpdate) 
                wait();
              goto _L3
            Exception exception2;
            exception2;
            Exception exception;
            try {
                throw exception2;
            }
            catch(Exception exception1) {
                bufferedwriter = bufferedwriter1;
            }
            finally {
                bufferedwriter = bufferedwriter1;
            }
_L7:
            IOException ioexception2;
            if(bufferedwriter != null)
                try {
                    bufferedwriter.close();
                }
                catch(IOException ioexception1) { }
            mWindowManager.removeWindowChangeListener(this);
_L5:
            return true;
_L3:
            if(mNeedWindowListUpdate) {
                mNeedWindowListUpdate = false;
                flag = true;
            }
            if(mNeedFocusedWindowUpdate) {
                mNeedFocusedWindowUpdate = false;
                flag1 = true;
            }
            this;
            JVM INSTR monitorexit ;
            if(!flag)
                break MISSING_BLOCK_LABEL_149;
            bufferedwriter1.write("LIST UPDATE\n");
            bufferedwriter1.flush();
            if(flag1) {
                bufferedwriter1.write("FOCUS UPDATE\n");
                bufferedwriter1.flush();
            }
              goto _L4
_L6:
            if(bufferedwriter != null)
                try {
                    bufferedwriter.close();
                }
                catch(IOException ioexception) { }
            mWindowManager.removeWindowChangeListener(this);
            throw exception;
_L2:
            if(bufferedwriter1 != null)
                try {
                    bufferedwriter1.close();
                }
                // Misplaced declaration of an exception variable
                catch(IOException ioexception2) { }
            mWindowManager.removeWindowChangeListener(this);
              goto _L5
            exception;
              goto _L6
            Exception exception3;
            exception3;
              goto _L7
        }

        public void focusChanged() {
            this;
            JVM INSTR monitorenter ;
            mNeedFocusedWindowUpdate = true;
            notifyAll();
            return;
        }

        public void run() {
            BufferedReader bufferedreader = null;
            BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(mClient.getInputStream()), 1024);
            String s;
            int i;
            s = bufferedreader1.readLine();
            i = s.indexOf(' ');
            if(i != -1) goto _L2; else goto _L1
_L1:
            String s1;
            String s2;
            s1 = s;
            s2 = "";
_L5:
            if(!"PROTOCOL".equalsIgnoreCase(s1)) goto _L4; else goto _L3
_L3:
            boolean flag1 = ViewServer.writeValue(mClient, "4");
_L8:
            if(!flag1)
                Slog.w("ViewServer", (new StringBuilder()).append("An error occurred with the command: ").append(s1).toString());
            boolean flag;
            if(bufferedreader1 != null)
                try {
                    bufferedreader1.close();
                }
                catch(IOException ioexception6) {
                    ioexception6.printStackTrace();
                }
            if(mClient == null)
                break MISSING_BLOCK_LABEL_131;
            mClient.close();
_L15:
            return;
_L2:
            s1 = s.substring(0, i);
            s2 = s.substring(i + 1);
              goto _L5
_L4:
            if(!"SERVER".equalsIgnoreCase(s1)) goto _L7; else goto _L6
_L6:
            flag1 = ViewServer.writeValue(mClient, "4");
              goto _L8
_L7:
            if(!"LIST".equalsIgnoreCase(s1)) goto _L10; else goto _L9
_L9:
            flag1 = mWindowManager.viewServerListWindows(mClient);
              goto _L8
_L10:
            if(!"GET_FOCUS".equalsIgnoreCase(s1)) goto _L12; else goto _L11
_L11:
            flag1 = mWindowManager.viewServerGetFocusedWindow(mClient);
              goto _L8
_L12:
            if(!"AUTOLIST".equalsIgnoreCase(s1)) goto _L14; else goto _L13
_L13:
            flag1 = windowManagerAutolistLoop();
              goto _L8
_L14:
            flag = mWindowManager.viewServerWindowCommand(mClient, s1, s2);
            flag1 = flag;
              goto _L8
            IOException ioexception5;
            ioexception5;
            ioexception5.printStackTrace();
              goto _L15
            IOException ioexception;
            ioexception;
_L18:
            Slog.w("ViewServer", "Connection error: ", ioexception);
            if(bufferedreader != null)
                try {
                    bufferedreader.close();
                }
                catch(IOException ioexception4) {
                    ioexception4.printStackTrace();
                }
            if(mClient != null)
                try {
                    mClient.close();
                }
                catch(IOException ioexception3) {
                    ioexception3.printStackTrace();
                }
              goto _L15
            Exception exception;
            exception;
_L17:
            if(bufferedreader != null)
                try {
                    bufferedreader.close();
                }
                catch(IOException ioexception2) {
                    ioexception2.printStackTrace();
                }
            if(mClient != null)
                try {
                    mClient.close();
                }
                catch(IOException ioexception1) {
                    ioexception1.printStackTrace();
                }
            throw exception;
            exception;
            bufferedreader = bufferedreader1;
            if(true) goto _L17; else goto _L16
_L16:
            ioexception;
            bufferedreader = bufferedreader1;
              goto _L18
        }

        public void windowsChanged() {
            this;
            JVM INSTR monitorenter ;
            mNeedWindowListUpdate = true;
            notifyAll();
            return;
        }

        private Socket mClient;
        private boolean mNeedFocusedWindowUpdate;
        private boolean mNeedWindowListUpdate;
        final ViewServer this$0;

        public ViewServerWorker(Socket socket) {
            this$0 = ViewServer.this;
            super();
            mClient = socket;
            mNeedWindowListUpdate = false;
            mNeedFocusedWindowUpdate = false;
        }
    }


    ViewServer(WindowManagerService windowmanagerservice, int i) {
        mWindowManager = windowmanagerservice;
        mPort = i;
    }

    private static boolean writeValue(Socket socket, String s) {
        BufferedWriter bufferedwriter = null;
        BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
        bufferedwriter1.write(s);
        bufferedwriter1.write("\n");
        bufferedwriter1.flush();
        boolean flag;
        flag = true;
        if(bufferedwriter1 != null)
            try {
                bufferedwriter1.close();
            }
            catch(IOException ioexception2) {
                flag = false;
            }
_L2:
        return flag;
        Exception exception2;
        exception2;
_L5:
        flag = false;
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception) {
                flag = false;
            }
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception1;
        exception1;
_L4:
        if(bufferedwriter != null)
            try {
                bufferedwriter.close();
            }
            catch(IOException ioexception1) { }
        throw exception1;
        exception1;
        bufferedwriter = bufferedwriter1;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        bufferedwriter = bufferedwriter1;
          goto _L5
    }

    boolean isRunning() {
        boolean flag;
        if(mThread != null && mThread.isAlive())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void run() {
_L2:
        if(Thread.currentThread() != mThread)
            break; /* Loop/switch isn't completed */
        Exception exception;
        Socket socket;
        socket = mServer.accept();
        if(mThreadPool != null) {
            mThreadPool.submit(new ViewServerWorker(socket));
            continue; /* Loop/switch isn't completed */
        }
        socket.close();
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        try {
            ioexception.printStackTrace();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception) {
            Slog.w("ViewServer", "Connection error: ", exception);
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    boolean start() throws IOException {
        boolean flag;
        if(mThread != null) {
            flag = false;
        } else {
            mServer = new ServerSocket(mPort, 10, InetAddress.getLocalHost());
            mThread = new Thread(this, (new StringBuilder()).append("Remote View Server [port=").append(mPort).append("]").toString());
            mThreadPool = Executors.newFixedThreadPool(10);
            mThread.start();
            flag = true;
        }
        return flag;
    }

    boolean stop() {
        if(mThread == null) goto _L2; else goto _L1
_L1:
        boolean flag;
        mThread.interrupt();
        if(mThreadPool != null)
            try {
                mThreadPool.shutdownNow();
            }
            catch(SecurityException securityexception) {
                Slog.w("ViewServer", "Could not stop all view server threads");
            }
        mThreadPool = null;
        mThread = null;
        mServer.close();
        mServer = null;
        flag = true;
_L4:
        return flag;
        IOException ioexception;
        ioexception;
        Slog.w("ViewServer", "Could not close the view server");
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
    private static final String COMMAND_SERVER_VERSION = "SERVER";
    private static final String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
    private static final String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
    private static final String COMMAND_WINDOW_MANAGER_LIST = "LIST";
    private static final String LOG_TAG = "ViewServer";
    private static final String VALUE_PROTOCOL_VERSION = "4";
    private static final String VALUE_SERVER_VERSION = "4";
    public static final int VIEW_SERVER_DEFAULT_PORT = 4939;
    private static final int VIEW_SERVER_MAX_CONNECTIONS = 10;
    private final int mPort;
    private ServerSocket mServer;
    private Thread mThread;
    private ExecutorService mThreadPool;
    private final WindowManagerService mWindowManager;


}
