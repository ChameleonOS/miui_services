// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.PackageStats;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Slog;
import java.io.*;

class Installer {

    Installer() {
        buf = new byte[1024];
        buflen = 0;
    }

    private boolean connect() {
        boolean flag = true;
        if(mSocket == null) {
            Slog.i("Installer", "connecting...");
            try {
                mSocket = new LocalSocket();
                LocalSocketAddress localsocketaddress = new LocalSocketAddress("installd", android.net.LocalSocketAddress.Namespace.RESERVED);
                mSocket.connect(localsocketaddress);
                mIn = mSocket.getInputStream();
                mOut = mSocket.getOutputStream();
            }
            catch(IOException ioexception) {
                disconnect();
                flag = false;
            }
        }
        return flag;
    }

    private void disconnect() {
        Slog.i("Installer", "disconnecting...");
        try {
            if(mSocket != null)
                mSocket.close();
        }
        catch(IOException ioexception) { }
        try {
            if(mIn != null)
                mIn.close();
        }
        catch(IOException ioexception1) { }
        try {
            if(mOut != null)
                mOut.close();
        }
        catch(IOException ioexception2) { }
        mSocket = null;
        mIn = null;
        mOut = null;
    }

    private int execute(String s) {
        String s1 = transaction(s);
        int j = Integer.parseInt(s1);
        int i = j;
_L2:
        return i;
        NumberFormatException numberformatexception;
        numberformatexception;
        i = -1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private boolean readBytes(byte abyte0[], int i) {
        boolean flag;
        int j;
        flag = false;
        j = 0;
        if(i >= 0) goto _L2; else goto _L1
_L1:
        return flag;
_L4:
        int k;
        j += k;
_L2:
        if(j == i)
            break MISSING_BLOCK_LABEL_71;
        k = mIn.read(abyte0, j, i - j);
        if(k > 0) goto _L4; else goto _L3
_L3:
        Slog.e("Installer", (new StringBuilder()).append("read error ").append(k).toString());
_L6:
        IOException ioexception;
        if(j == i)
            flag = true;
        else
            disconnect();
        if(true) goto _L1; else goto _L5
_L5:
        ioexception;
        Slog.e("Installer", "read exception");
          goto _L6
    }

    private boolean readReply() {
        boolean flag;
        flag = false;
        buflen = 0;
        if(readBytes(buf, 2)) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        int i = 0xff & buf[0] | (0xff & buf[1]) << 8;
        if(i < 1 || i > 1024) {
            Slog.e("Installer", (new StringBuilder()).append("invalid reply length (").append(i).append(")").toString());
            disconnect();
        } else
        if(readBytes(buf, i)) {
            buflen = i;
            flag = true;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    /**
     * @deprecated Method transaction is deprecated
     */

    private String transaction(String s) {
        this;
        JVM INSTR monitorenter ;
        if(connect()) goto _L2; else goto _L1
_L1:
        String s1;
        Slog.e("Installer", "connection failed");
        s1 = "-1";
_L4:
        this;
        JVM INSTR monitorexit ;
        return s1;
_L2:
        if(!writeCommand(s)) {
            Slog.e("Installer", "write command failed? reconnect!");
            if(!connect() || !writeCommand(s))
                break MISSING_BLOCK_LABEL_96;
        }
        if(readReply())
            s1 = new String(buf, 0, buflen);
        else
            s1 = "-1";
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        s1 = "-1";
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean writeCommand(String s) {
        boolean flag = true;
        byte abyte0[] = s.getBytes();
        int i = abyte0.length;
        if(i < flag || i > 1024) {
            flag = false;
        } else {
            buf[0] = (byte)(i & 0xff);
            buf[flag] = (byte)(0xff & i >> 8);
            try {
                mOut.write(buf, 0, 2);
                mOut.write(abyte0, 0, i);
            }
            catch(IOException ioexception) {
                Slog.e("Installer", "write error");
                disconnect();
                flag = false;
            }
        }
        return flag;
    }

    public int clearUserData(String s, int i) {
        StringBuilder stringbuilder = new StringBuilder("rmuserdata");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        return execute(stringbuilder.toString());
    }

    public int cloneUserData(int i, int j, boolean flag) {
        StringBuilder stringbuilder = new StringBuilder("cloneuserdata");
        stringbuilder.append(' ');
        stringbuilder.append(i);
        stringbuilder.append(' ');
        stringbuilder.append(j);
        stringbuilder.append(' ');
        char c;
        if(flag)
            c = '1';
        else
            c = '0';
        stringbuilder.append(c);
        return execute(stringbuilder.toString());
    }

    public int createUserData(String s, int i, int j) {
        StringBuilder stringbuilder = new StringBuilder("mkuserdata");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        stringbuilder.append(' ');
        stringbuilder.append(j);
        return execute(stringbuilder.toString());
    }

    public int deleteCacheFiles(String s) {
        StringBuilder stringbuilder = new StringBuilder("rmcache");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        return execute(stringbuilder.toString());
    }

    public int dexopt(String s, int i, boolean flag) {
        StringBuilder stringbuilder = new StringBuilder("dexopt");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        String s1;
        if(flag)
            s1 = " 1";
        else
            s1 = " 0";
        stringbuilder.append(s1);
        return execute(stringbuilder.toString());
    }

    public int fixUid(String s, int i, int j) {
        StringBuilder stringbuilder = new StringBuilder("fixuid");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        stringbuilder.append(' ');
        stringbuilder.append(j);
        return execute(stringbuilder.toString());
    }

    public int freeCache(long l) {
        StringBuilder stringbuilder = new StringBuilder("freecache");
        stringbuilder.append(' ');
        stringbuilder.append(String.valueOf(l));
        return execute(stringbuilder.toString());
    }

    public int getSizeInfo(String s, String s1, String s2, String s3, PackageStats packagestats) {
        int i;
        String as[];
        i = -1;
        StringBuilder stringbuilder = new StringBuilder("getsize");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(s1);
        stringbuilder.append(' ');
        if(s2 == null)
            s2 = "!";
        stringbuilder.append(s2);
        stringbuilder.append(' ');
        if(s3 == null)
            s3 = "!";
        stringbuilder.append(s3);
        as = transaction(stringbuilder.toString()).split(" ");
        if(as != null && as.length == 5) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        int j;
        packagestats.codeSize = Long.parseLong(as[1]);
        packagestats.dataSize = Long.parseLong(as[2]);
        packagestats.cacheSize = Long.parseLong(as[3]);
        packagestats.externalCodeSize = Long.parseLong(as[4]);
        j = Integer.parseInt(as[0]);
        i = j;
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public int install(String s, int i, int j) {
        StringBuilder stringbuilder = new StringBuilder("install");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        stringbuilder.append(' ');
        stringbuilder.append(j);
        return execute(stringbuilder.toString());
    }

    public int linkNativeLibraryDirectory(String s, String s1) {
        int i = -1;
        if(s == null)
            Slog.e("Installer", "unlinkNativeLibraryDirectory dataPath is null");
        else
        if(s1 == null) {
            Slog.e("Installer", "unlinkNativeLibraryDirectory nativeLibPath is null");
        } else {
            StringBuilder stringbuilder = new StringBuilder("linklib ");
            stringbuilder.append(s);
            stringbuilder.append(' ');
            stringbuilder.append(s1);
            i = execute(stringbuilder.toString());
        }
        return i;
    }

    public int moveFiles() {
        return execute("movefiles");
    }

    public int movedex(String s, String s1) {
        StringBuilder stringbuilder = new StringBuilder("movedex");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(s1);
        return execute(stringbuilder.toString());
    }

    public boolean ping() {
        boolean flag;
        if(execute("ping") < 0)
            flag = false;
        else
            flag = true;
        return flag;
    }

    public int remove(String s, int i) {
        StringBuilder stringbuilder = new StringBuilder("remove");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        return execute(stringbuilder.toString());
    }

    public int removeUserDataDirs(int i) {
        StringBuilder stringbuilder = new StringBuilder("rmuser");
        stringbuilder.append(' ');
        stringbuilder.append(i);
        return execute(stringbuilder.toString());
    }

    public int rename(String s, String s1) {
        StringBuilder stringbuilder = new StringBuilder("rename");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(s1);
        return execute(stringbuilder.toString());
    }

    public int rmdex(String s) {
        StringBuilder stringbuilder = new StringBuilder("rmdex");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        return execute(stringbuilder.toString());
    }

    public int setForwardLockPerm(String s, int i) {
        StringBuilder stringbuilder = new StringBuilder("protect");
        stringbuilder.append(' ');
        stringbuilder.append(s);
        stringbuilder.append(' ');
        stringbuilder.append(i);
        return execute(stringbuilder.toString());
    }

    public int unlinkNativeLibraryDirectory(String s) {
        int i;
        if(s == null) {
            Slog.e("Installer", "unlinkNativeLibraryDirectory dataPath is null");
            i = -1;
        } else {
            StringBuilder stringbuilder = new StringBuilder("unlinklib ");
            stringbuilder.append(s);
            i = execute(stringbuilder.toString());
        }
        return i;
    }

    private static final boolean LOCAL_DEBUG = false;
    private static final String TAG = "Installer";
    byte buf[];
    int buflen;
    InputStream mIn;
    OutputStream mOut;
    LocalSocket mSocket;
}
