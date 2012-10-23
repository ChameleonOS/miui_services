// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.location;

import android.content.Context;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;

public class GpsXtraDownloader {

    GpsXtraDownloader(Context context, Properties properties) {
        mContext = context;
        int i = 0;
        String s = properties.getProperty("XTRA_SERVER_1");
        String s1 = properties.getProperty("XTRA_SERVER_2");
        String s2 = properties.getProperty("XTRA_SERVER_3");
        if(s != null)
            i = 0 + 1;
        if(s1 != null)
            i++;
        if(s2 != null)
            i++;
        if(i == 0) {
            Log.e("GpsXtraDownloader", "No XTRA servers were specified in the GPS configuration");
        } else {
            mXtraServers = new String[i];
            int j;
            int k;
            if(s != null) {
                String as2[] = mXtraServers;
                j = 0 + 1;
                as2[0] = s;
            } else {
                j = 0;
            }
            if(s1 != null) {
                String as1[] = mXtraServers;
                int l = j + 1;
                as1[j] = s1;
                j = l;
            }
            if(s2 != null) {
                String as[] = mXtraServers;
                k = j + 1;
                as[j] = s2;
            } else {
                k = j;
            }
            mNextServerIndex = (new Random()).nextInt(k);
        }
    }

    protected static byte[] doDownload(String s, boolean flag, String s1, int i) {
        AndroidHttpClient androidhttpclient = null;
        HttpResponse httpresponse;
        int j;
        androidhttpclient = AndroidHttpClient.newInstance("Android");
        HttpGet httpget = new HttpGet(s);
        if(flag) {
            HttpHost httphost = new HttpHost(s1, i);
            ConnRouteParams.setDefaultProxy(httpget.getParams(), httphost);
        }
        httpget.addHeader("Accept", "*/*, application/vnd.wap.mms-message, application/vnd.wap.sic");
        httpget.addHeader("x-wap-profile", "http://www.openmobilealliance.org/tech/profiles/UAPROF/ccppschema-20021212#");
        httpresponse = androidhttpclient.execute(httpget);
        j = httpresponse.getStatusLine().getStatusCode();
        if(j == 200) goto _L2; else goto _L1
_L1:
        byte abyte0[] = null;
        if(androidhttpclient == null) goto _L4; else goto _L3
_L3:
        androidhttpclient.close();
_L4:
        return abyte0;
_L2:
        HttpEntity httpentity = httpresponse.getEntity();
        abyte0 = null;
        if(httpentity == null) goto _L6; else goto _L5
_L5:
        DataInputStream datainputstream;
        if(httpentity.getContentLength() <= 0L)
            break MISSING_BLOCK_LABEL_185;
        abyte0 = new byte[(int)httpentity.getContentLength()];
        datainputstream = new DataInputStream(httpentity.getContent());
        datainputstream.readFully(abyte0);
        Exception exception;
        Exception exception2;
        Exception exception3;
        IOException ioexception;
        try {
            datainputstream.close();
        }
        catch(IOException ioexception1) {
            Log.e("GpsXtraDownloader", "Unexpected IOException.", ioexception1);
        }
        finally {
            if(httpentity == null) goto _L0; else goto _L0
        }
        if(httpentity == null) goto _L6; else goto _L7
_L7:
        httpentity.consumeContent();
_L6:
        if(androidhttpclient == null) goto _L4; else goto _L3
        exception3;
        datainputstream.close();
_L8:
        throw exception3;
        try {
            httpentity.consumeContent();
            throw exception2;
        }
        catch(Exception exception1) { }
        finally {
            if(androidhttpclient == null) goto _L0; else goto _L0
        }
        if(androidhttpclient != null)
            androidhttpclient.close();
        abyte0 = null;
          goto _L4
        androidhttpclient.close();
        throw exception;
        ioexception;
        Log.e("GpsXtraDownloader", "Unexpected IOException.", ioexception);
          goto _L8
    }

    byte[] downloadXtraData() {
        String s = Proxy.getHost(mContext);
        int i = Proxy.getPort(mContext);
        boolean flag;
        byte abyte0[];
        int j;
        byte abyte1[];
        if(s != null && i != -1)
            flag = true;
        else
            flag = false;
        abyte0 = null;
        j = mNextServerIndex;
        if(mXtraServers == null) {
            abyte1 = null;
        } else {
            do {
                if(abyte0 != null)
                    break;
                abyte0 = doDownload(mXtraServers[mNextServerIndex], flag, s, i);
                mNextServerIndex = 1 + mNextServerIndex;
                if(mNextServerIndex == mXtraServers.length)
                    mNextServerIndex = 0;
            } while(mNextServerIndex != j);
            abyte1 = abyte0;
        }
        return abyte1;
    }

    static final boolean DEBUG = false;
    private static final String TAG = "GpsXtraDownloader";
    private Context mContext;
    private int mNextServerIndex;
    private String mXtraServers[];
}
