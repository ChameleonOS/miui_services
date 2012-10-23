// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.am;

import android.os.*;
import java.io.*;

class TransferPipe
    implements Runnable {
    static interface Caller {

        public abstract void go(IInterface iinterface, FileDescriptor filedescriptor, String s, String as[]) throws RemoteException;
    }


    TransferPipe() throws IOException {
    }

    static void go(Caller caller, IInterface iinterface, FileDescriptor filedescriptor, String s, String as[]) throws IOException, RemoteException {
        go(caller, iinterface, filedescriptor, s, as, 5000L);
    }

    static void go(Caller caller, IInterface iinterface, FileDescriptor filedescriptor, String s, String as[], long l) throws IOException, RemoteException {
        if(!(iinterface.asBinder() instanceof Binder)) goto _L2; else goto _L1
_L1:
        caller.go(iinterface, filedescriptor, s, as);
_L4:
        return;
_L2:
        TransferPipe transferpipe = new TransferPipe();
        caller.go(iinterface, transferpipe.getWriteFd().getFileDescriptor(), s, as);
        transferpipe.go(filedescriptor, l);
        transferpipe.kill();
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        transferpipe.kill();
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    static void goDump(IBinder ibinder, FileDescriptor filedescriptor, String as[]) throws IOException, RemoteException {
        goDump(ibinder, filedescriptor, as, 5000L);
    }

    static void goDump(IBinder ibinder, FileDescriptor filedescriptor, String as[], long l) throws IOException, RemoteException {
        if(!(ibinder instanceof Binder)) goto _L2; else goto _L1
_L1:
        ibinder.dump(filedescriptor, as);
_L4:
        return;
_L2:
        TransferPipe transferpipe = new TransferPipe();
        ibinder.dumpAsync(transferpipe.getWriteFd().getFileDescriptor(), as);
        transferpipe.go(filedescriptor, l);
        transferpipe.kill();
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        transferpipe.kill();
        throw exception;
        RemoteException remoteexception;
        remoteexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    void closeFd(int i) {
        if(mFds[i] != null) {
            try {
                mFds[i].close();
            }
            catch(IOException ioexception) { }
            mFds[i] = null;
        }
    }

    ParcelFileDescriptor getReadFd() {
        return mFds[0];
    }

    ParcelFileDescriptor getWriteFd() {
        return mFds[1];
    }

    void go(FileDescriptor filedescriptor) throws IOException {
        go(filedescriptor, 5000L);
    }

    void go(FileDescriptor filedescriptor, long l) throws IOException {
        this;
        JVM INSTR monitorenter ;
        mOutFd = filedescriptor;
        mEndTime = l + SystemClock.uptimeMillis();
        closeFd(1);
        mThread.start();
_L2:
        Exception exception1;
        long l1;
        if(mFailure != null || mComplete)
            break; /* Loop/switch isn't completed */
        l1 = mEndTime - SystemClock.uptimeMillis();
        if(l1 <= 0L) {
            mThread.interrupt();
            throw new IOException("Timeout");
        }
        if(true)
            break MISSING_BLOCK_LABEL_92;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        kill();
        throw exception;
        try {
            wait(l1);
        }
        catch(InterruptedException interruptedexception) { }
        finally {
            this;
        }
        if(true) goto _L2; else goto _L1
_L1:
        if(mFailure != null)
            throw new IOException(mFailure);
        this;
        JVM INSTR monitorexit ;
        kill();
        return;
    }

    void kill() {
        closeFd(0);
        closeFd(1);
    }

    public void run() {
        byte abyte0[];
        FileInputStream fileinputstream;
        FileOutputStream fileoutputstream;
        byte abyte1[];
        boolean flag;
        abyte0 = new byte[1024];
        fileinputstream = new FileInputStream(getReadFd().getFileDescriptor());
        fileoutputstream = new FileOutputStream(mOutFd);
        abyte1 = null;
        flag = true;
        if(mBufferPrefix != null)
            abyte1 = mBufferPrefix.getBytes();
_L5:
        int i = fileinputstream.read(abyte0);
        if(i <= 0) goto _L2; else goto _L1
_L1:
        if(abyte1 != null) goto _L4; else goto _L3
_L3:
        fileoutputstream.write(abyte0, 0, i);
          goto _L5
        IOException ioexception;
        ioexception;
        this;
        JVM INSTR monitorenter ;
        mFailure = ioexception.toString();
        notifyAll();
        this;
        JVM INSTR monitorexit ;
_L9:
        return;
_L4:
        int j;
        int k;
        j = 0;
        k = 0;
_L10:
        if(k >= i) goto _L7; else goto _L6
_L6:
        if(abyte0[k] == 10)
            break MISSING_BLOCK_LABEL_260;
        if(k > j)
            fileoutputstream.write(abyte0, j, k - j);
        j = k;
        if(flag) {
            fileoutputstream.write(abyte1);
            flag = false;
        }
        while(++k < i && abyte0[k] != 10) ;
          goto _L8
_L7:
        if(i > j)
            fileoutputstream.write(abyte0, j, i - j);
          goto _L5
_L2:
        boolean flag1 = mThread.isInterrupted();
        if(!flag1);
        this;
        JVM INSTR monitorenter ;
        mComplete = true;
        notifyAll();
          goto _L9
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L8:
        if(k < i)
            flag = true;
        k++;
          goto _L10
    }

    void setBufferPrefix(String s) {
        mBufferPrefix = s;
    }

    static final boolean DEBUG = false;
    static final long DEFAULT_TIMEOUT = 5000L;
    static final String TAG = "TransferPipe";
    String mBufferPrefix;
    boolean mComplete;
    long mEndTime;
    String mFailure;
    final ParcelFileDescriptor mFds[] = ParcelFileDescriptor.createPipe();
    FileDescriptor mOutFd;
    final Thread mThread = new Thread(this, "TransferPipe");
}
