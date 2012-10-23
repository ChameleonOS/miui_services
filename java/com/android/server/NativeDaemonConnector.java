// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.*;
import android.util.LocalLog;
import android.util.Slog;
import com.google.android.collect.Lists;
import java.io.*;
import java.nio.charset.Charsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// Referenced classes of package com.android.server:
//            INativeDaemonConnectorCallbacks, NativeDaemonEvent, NativeDaemonConnectorException

final class NativeDaemonConnector
    implements Runnable, android.os.Handler.Callback, Watchdog.Monitor {
    private static class ResponseQueue {
        private static class Response {

            public int cmdNum;
            public String request;
            public LinkedList responses;

            public Response(int i, String s) {
                responses = new LinkedList();
                cmdNum = i;
                request = s;
            }
        }


        public void add(int i, NativeDaemonEvent nativedaemonevent) {
            LinkedList linkedlist = mResponses;
            linkedlist;
            JVM INSTR monitorenter ;
            Iterator iterator = mResponses.iterator();
_L4:
            if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
            Response response3;
            int j;
            response3 = (Response)iterator.next();
            j = response3.cmdNum;
            if(j != i) goto _L4; else goto _L3
_L3:
            Response response = response3;
_L9:
            if(response != null)
                break MISSING_BLOCK_LABEL_247;
            Response response2;
            for(; mResponses.size() >= mMaxCount; Slog.e("NativeDaemonConnector.ResponseQueue", (new StringBuilder()).append("Removing request: ").append(response2.request).append(" (").append(response2.cmdNum).append(")").toString())) {
                Slog.e("NativeDaemonConnector.ResponseQueue", (new StringBuilder()).append("more buffered than allowed: ").append(mResponses.size()).append(" >= ").append(mMaxCount).toString());
                response2 = (Response)mResponses.remove();
            }

              goto _L5
            Exception exception;
            exception;
            response;
_L6:
            linkedlist;
            JVM INSTR monitorexit ;
            throw exception;
_L5:
            Response response1 = new Response(i, null);
            mResponses.add(response1);
_L7:
            response1.responses.add(nativedaemonevent);
            linkedlist;
            JVM INSTR monitorexit ;
            response1;
            JVM INSTR monitorenter ;
            response1.notify();
            return;
            exception;
              goto _L6
            response1 = response;
              goto _L7
_L2:
            response = null;
            if(true) goto _L9; else goto _L8
_L8:
        }

        public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
            printwriter.println("Pending requests:");
            LinkedList linkedlist = mResponses;
            linkedlist;
            JVM INSTR monitorenter ;
            Response response;
            for(Iterator iterator = mResponses.iterator(); iterator.hasNext(); printwriter.println((new StringBuilder()).append("  Cmd ").append(response.cmdNum).append(" - ").append(response.request).toString()))
                response = (Response)iterator.next();

            break MISSING_BLOCK_LABEL_97;
            Exception exception;
            exception;
            throw exception;
            linkedlist;
            JVM INSTR monitorexit ;
        }

        public NativeDaemonEvent remove(int i, int j, String s) {
            long l;
            Response response;
            l = SystemClock.uptimeMillis() + (long)j;
            response = null;
_L14:
            LinkedList linkedlist = mResponses;
            linkedlist;
            JVM INSTR monitorenter ;
            Iterator iterator = mResponses.iterator();
            Response response1 = response;
_L9:
            if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
            Response response2;
            int k;
            response2 = (Response)iterator.next();
            k = response2.cmdNum;
            if(k != i) goto _L4; else goto _L3
_L3:
            Response response3 = response2;
            response2.responses.size();
            JVM INSTR tableswitch 0 1: default 104
        //                       0 152
        //                       1 129;
               goto _L5 _L6 _L7
_L5:
            NativeDaemonEvent nativedaemonevent;
            response2.request = s;
            nativedaemonevent = (NativeDaemonEvent)response2.responses.remove();
            linkedlist;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
_L7:
            mResponses.remove(response2);
            if(true) goto _L5; else goto _L8
_L15:
            linkedlist;
            JVM INSTR monitorexit ;
            Exception exception;
            throw exception;
_L4:
            response3 = response1;
_L6:
            response1 = response3;
              goto _L9
_L2:
            long l1 = SystemClock.uptimeMillis();
            if(l > l1) goto _L11; else goto _L10
_L10:
            Slog.e("NativeDaemonConnector.ResponseQueue", "Timeout waiting for response");
            nativedaemonevent = null;
            linkedlist;
            JVM INSTR monitorexit ;
            response1;
              goto _L8
_L11:
            if(response1 != null) goto _L13; else goto _L12
_L12:
            response = new Response(i, s);
            mResponses.add(response);
_L16:
            linkedlist;
            JVM INSTR monitorexit ;
            response;
            JVM INSTR monitorenter ;
            long l2 = l - l1;
            response.wait(l2);
              goto _L14
            InterruptedException interruptedexception;
            interruptedexception;
              goto _L14
            exception;
            response1;
              goto _L15
_L13:
            response = response1;
              goto _L16
_L8:
            return nativedaemonevent;
            exception;
              goto _L15
        }

        private int mMaxCount;
        private final LinkedList mResponses = new LinkedList();

        ResponseQueue(int i) {
            mMaxCount = i;
        }
    }

    public static class Command {

        public Command appendArg(Object obj) {
            mArguments.add(obj);
            return this;
        }

        private ArrayList mArguments;
        private String mCmd;



        public transient Command(String s, Object aobj[]) {
            mArguments = Lists.newArrayList();
            mCmd = s;
            int i = aobj.length;
            for(int j = 0; j < i; j++)
                appendArg(aobj[j]);

        }
    }

    private static class NativeDaemonFailureException extends NativeDaemonConnectorException {

        public NativeDaemonFailureException(String s, NativeDaemonEvent nativedaemonevent) {
            super(s, nativedaemonevent);
        }
    }

    private static class NativeDaemonArgumentException extends NativeDaemonConnectorException {

        public IllegalArgumentException rethrowAsParcelableException() {
            throw new IllegalArgumentException(getMessage(), this);
        }

        public NativeDaemonArgumentException(String s, NativeDaemonEvent nativedaemonevent) {
            super(s, nativedaemonevent);
        }
    }


    NativeDaemonConnector(INativeDaemonConnectorCallbacks inativedaemonconnectorcallbacks, String s, int i, String s1, int j) {
        mCallbacks = inativedaemonconnectorcallbacks;
        mSocket = s;
        mResponseQueue = new ResponseQueue(i);
        mSequenceNumber = new AtomicInteger(0);
        if(s1 == null)
            s1 = "NativeDaemonConnector";
        TAG = s1;
        mLocalLog = new LocalLog(j);
    }

    static void appendEscaped(StringBuilder stringbuilder, String s) {
        boolean flag;
        int i;
        int j;
        if(s.indexOf(' ') >= 0)
            flag = true;
        else
            flag = false;
        if(flag)
            stringbuilder.append('"');
        i = s.length();
        j = 0;
        while(j < i)  {
            char c = s.charAt(j);
            if(c == '"')
                stringbuilder.append("\\\"");
            else
            if(c == '\\')
                stringbuilder.append("\\\\");
            else
                stringbuilder.append(c);
            j++;
        }
        if(flag)
            stringbuilder.append('"');
    }

    private void listenToSocket() throws IOException {
        LocalSocket localsocket = null;
        LocalSocket localsocket1 = new LocalSocket();
        InputStream inputstream;
        localsocket1.connect(new LocalSocketAddress(mSocket, android.net.LocalSocketAddress.Namespace.RESERVED));
        inputstream = localsocket1.getInputStream();
        synchronized(mDaemonLock) {
            mOutputStream = localsocket1.getOutputStream();
        }
        byte abyte0[];
        int i;
        mCallbacks.onDaemonConnected();
        abyte0 = new byte[4096];
        i = 0;
_L7:
        int j = inputstream.read(abyte0, i, 4096 - i);
        if(j >= 0) goto _L2; else goto _L1
_L1:
        loge((new StringBuilder()).append("got ").append(j).append(" reading with start = ").append(i).toString());
        Object obj2 = mDaemonLock;
        obj2;
        JVM INSTR monitorenter ;
        OutputStream outputstream1 = mOutputStream;
        if(outputstream1 == null) goto _L4; else goto _L3
_L3:
        loge((new StringBuilder()).append("closing stream for ").append(mSocket).toString());
        mOutputStream.close();
_L10:
        mOutputStream = null;
_L4:
        obj2;
        JVM INSTR monitorexit ;
        if(localsocket1 == null)
            break MISSING_BLOCK_LABEL_198;
        localsocket1.close();
_L9:
        return;
        exception2;
        obj1;
        JVM INSTR monitorexit ;
        throw exception2;
        IOException ioexception2;
        ioexception2;
        localsocket = localsocket1;
_L11:
        loge((new StringBuilder()).append("Communications error: ").append(ioexception2).toString());
        throw ioexception2;
        Exception exception;
        exception;
_L13:
        Object obj = mDaemonLock;
        obj;
        JVM INSTR monitorenter ;
        OutputStream outputstream = mOutputStream;
        if(outputstream == null) goto _L6; else goto _L5
_L5:
        loge((new StringBuilder()).append("closing stream for ").append(mSocket).toString());
        mOutputStream.close();
_L8:
        mOutputStream = null;
_L6:
        obj;
        JVM INSTR monitorexit ;
        int l;
        int i1;
        Exception exception1;
        int k;
        String s;
        int j1;
        String s1;
        IllegalArgumentException illegalargumentexception;
        NativeDaemonEvent nativedaemonevent;
        if(localsocket != null)
            try {
                localsocket.close();
            }
            catch(IOException ioexception) {
                loge((new StringBuilder()).append("Failed closing socket: ").append(ioexception).toString());
            }
        throw exception;
_L2:
        k = j + i;
        l = 0;
        i1 = 0;
_L12:
        if(i1 >= k)
            break MISSING_BLOCK_LABEL_487;
        if(abyte0[i1] != 0)
            break MISSING_BLOCK_LABEL_722;
        s1 = new String(abyte0, l, i1 - l, Charsets.UTF_8);
        log((new StringBuilder()).append("RCV <- {").append(s1).append("}").toString());
        nativedaemonevent = NativeDaemonEvent.parseRawEvent(s1);
        if(nativedaemonevent.isClassUnsolicited())
            mCallbackHandler.sendMessage(mCallbackHandler.obtainMessage(nativedaemonevent.getCode(), nativedaemonevent.getRawEvent()));
        else
            mResponseQueue.add(nativedaemonevent.getCmdNumber(), nativedaemonevent);
        break MISSING_BLOCK_LABEL_716;
        illegalargumentexception;
        log((new StringBuilder()).append("Problem parsing message: ").append(s1).append(" - ").append(illegalargumentexception).toString());
        break MISSING_BLOCK_LABEL_716;
        if(l == 0) {
            s = new String(abyte0, l, k, Charsets.UTF_8);
            log((new StringBuilder()).append("RCV incomplete <- {").append(s).append("}").toString());
        }
        if(l == k)
            break MISSING_BLOCK_LABEL_573;
        j1 = 4096 - l;
        System.arraycopy(abyte0, l, abyte0, 0, j1);
        i = j1;
          goto _L7
        i = 0;
          goto _L7
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        IOException ioexception1;
        ioexception1;
        loge((new StringBuilder()).append("Failed closing output stream: ").append(ioexception1).toString());
          goto _L8
        Exception exception3;
        exception3;
        obj2;
        JVM INSTR monitorexit ;
        throw exception3;
        IOException ioexception3;
        ioexception3;
        loge((new StringBuilder()).append("Failed closing socket: ").append(ioexception3).toString());
          goto _L9
        IOException ioexception4;
        ioexception4;
        loge((new StringBuilder()).append("Failed closing output stream: ").append(ioexception4).toString());
          goto _L10
        ioexception2;
          goto _L11
        l = i1 + 1;
        i1++;
          goto _L12
        exception;
        localsocket = localsocket1;
          goto _L13
    }

    private void log(String s) {
        mLocalLog.log(s);
    }

    private void loge(String s) {
        Slog.e(TAG, s);
        mLocalLog.log(s);
    }

    private transient void makeCommand(StringBuilder stringbuilder, String s, Object aobj[]) throws NativeDaemonConnectorException {
        if(s.indexOf('\0') >= 0)
            throw new IllegalArgumentException((new StringBuilder()).append("unexpected command: ").append(s).toString());
        stringbuilder.append(s);
        int i = aobj.length;
        for(int j = 0; j < i; j++) {
            Object obj = aobj[j];
            String s1 = String.valueOf(obj);
            if(s1.indexOf('\0') >= 0)
                throw new IllegalArgumentException((new StringBuilder()).append("unexpected argument: ").append(obj).toString());
            stringbuilder.append(' ');
            appendEscaped(stringbuilder, s1);
        }

    }

    public ArrayList doCommand(String s) throws NativeDaemonConnectorException {
        ArrayList arraylist = Lists.newArrayList();
        NativeDaemonEvent anativedaemonevent[] = executeForList(s, new Object[0]);
        int i = anativedaemonevent.length;
        for(int j = 0; j < i; j++)
            arraylist.add(anativedaemonevent[j].getRawEvent());

        return arraylist;
    }

    public String[] doListCommand(String s, int i) throws NativeDaemonConnectorException {
        ArrayList arraylist = Lists.newArrayList();
        NativeDaemonEvent anativedaemonevent[] = executeForList(s, new Object[0]);
        for(int j = 0; j < -1 + anativedaemonevent.length;) {
            NativeDaemonEvent nativedaemonevent1 = anativedaemonevent[j];
            int k = nativedaemonevent1.getCode();
            if(k == i) {
                arraylist.add(nativedaemonevent1.getMessage());
                j++;
            } else {
                throw new NativeDaemonConnectorException((new StringBuilder()).append("unexpected list response ").append(k).append(" instead of ").append(i).toString());
            }
        }

        NativeDaemonEvent nativedaemonevent = anativedaemonevent[-1 + anativedaemonevent.length];
        if(!nativedaemonevent.isClassOk())
            throw new NativeDaemonConnectorException((new StringBuilder()).append("unexpected final event: ").append(nativedaemonevent).toString());
        else
            return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        mLocalLog.dump(filedescriptor, printwriter, as);
        printwriter.println();
        mResponseQueue.dump(filedescriptor, printwriter, as);
    }

    public NativeDaemonEvent execute(Command command) throws NativeDaemonConnectorException {
        return execute(command.mCmd, command.mArguments.toArray());
    }

    public transient NativeDaemonEvent execute(String s, Object aobj[]) throws NativeDaemonConnectorException {
        NativeDaemonEvent anativedaemonevent[] = executeForList(s, aobj);
        if(anativedaemonevent.length != 1)
            throw new NativeDaemonConnectorException((new StringBuilder()).append("Expected exactly one response, but received ").append(anativedaemonevent.length).toString());
        else
            return anativedaemonevent[0];
    }

    public transient NativeDaemonEvent[] execute(int i, String s, Object aobj[]) throws NativeDaemonConnectorException {
        ArrayList arraylist;
        int j;
        long l;
        String s1;
        String s2;
        arraylist = Lists.newArrayList();
        j = mSequenceNumber.incrementAndGet();
        StringBuilder stringbuilder = (new StringBuilder(Integer.toString(j))).append(' ');
        l = SystemClock.elapsedRealtime();
        makeCommand(stringbuilder, s, aobj);
        s1 = stringbuilder.toString();
        log((new StringBuilder()).append("SND -> {").append(s1).append("}").toString());
        stringbuilder.append('\0');
        s2 = stringbuilder.toString();
        Object obj = mDaemonLock;
        obj;
        JVM INSTR monitorenter ;
        if(mOutputStream == null)
            throw new NativeDaemonConnectorException("missing output stream");
        break MISSING_BLOCK_LABEL_132;
        Exception exception;
        exception;
        throw exception;
        mOutputStream.write(s2.getBytes(Charsets.UTF_8));
        obj;
        JVM INSTR monitorexit ;
_L2:
        NativeDaemonEvent nativedaemonevent;
        nativedaemonevent = mResponseQueue.remove(j, i, s2);
        if(nativedaemonevent == null) {
            loge((new StringBuilder()).append("timed-out waiting for response to ").append(s1).toString());
            throw new NativeDaemonFailureException(s1, nativedaemonevent);
        }
        break MISSING_BLOCK_LABEL_221;
        IOException ioexception;
        ioexception;
        throw new NativeDaemonConnectorException("problem sending command", ioexception);
        log((new StringBuilder()).append("RMV <- {").append(nativedaemonevent).append("}").toString());
        arraylist.add(nativedaemonevent);
        if(!nativedaemonevent.isClassContinue()) {
            long l1 = SystemClock.elapsedRealtime();
            if(l1 - l > 500L)
                loge((new StringBuilder()).append("NDC Command {").append(s1).append("} took too long (").append(l1 - l).append("ms)").toString());
            if(nativedaemonevent.isClassClientError())
                throw new NativeDaemonArgumentException(s1, nativedaemonevent);
            if(nativedaemonevent.isClassServerError())
                throw new NativeDaemonFailureException(s1, nativedaemonevent);
            else
                return (NativeDaemonEvent[])arraylist.toArray(new NativeDaemonEvent[arraylist.size()]);
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public NativeDaemonEvent[] executeForList(Command command) throws NativeDaemonConnectorException {
        return executeForList(command.mCmd, command.mArguments.toArray());
    }

    public transient NativeDaemonEvent[] executeForList(String s, Object aobj[]) throws NativeDaemonConnectorException {
        return execute(60000, s, aobj);
    }

    public boolean handleMessage(Message message) {
        String s = (String)message.obj;
        try {
            if(!mCallbacks.onEvent(message.what, s, NativeDaemonEvent.unescapeArgs(s))) {
                Object aobj[] = new Object[1];
                aobj[0] = s;
                log(String.format("Unhandled event '%s'", aobj));
            }
        }
        catch(Exception exception) {
            loge((new StringBuilder()).append("Error handling '").append(s).append("': ").append(exception).toString());
        }
        return true;
    }

    public void monitor() {
        Object obj = mDaemonLock;
        obj;
        JVM INSTR monitorenter ;
    }

    public void run() {
        HandlerThread handlerthread = new HandlerThread((new StringBuilder()).append(TAG).append(".CallbackHandler").toString());
        handlerthread.start();
        mCallbackHandler = new Handler(handlerthread.getLooper(), this);
        do {
            try {
                do
                    listenToSocket();
                while(true);
            }
            catch(Exception exception) {
                loge((new StringBuilder()).append("Error in NativeDaemonConnector: ").append(exception).toString());
            }
            SystemClock.sleep(5000L);
        } while(true);
    }

    private static final int DEFAULT_TIMEOUT = 60000;
    private static final boolean LOGD = false;
    private static final long WARN_EXECUTE_DELAY_MS = 500L;
    private final int BUFFER_SIZE = 4096;
    private final String TAG;
    private Handler mCallbackHandler;
    private INativeDaemonConnectorCallbacks mCallbacks;
    private final Object mDaemonLock = new Object();
    private LocalLog mLocalLog;
    private OutputStream mOutputStream;
    private final ResponseQueue mResponseQueue;
    private AtomicInteger mSequenceNumber;
    private String mSocket;
}
