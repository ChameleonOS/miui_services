// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.backup.*;
import android.content.pm.*;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import java.io.*;
import java.util.*;

public class PackageManagerBackupAgent extends BackupAgent {
    public class Metadata {

        public Signature signatures[];
        final PackageManagerBackupAgent this$0;
        public int versionCode;

        Metadata(int i, Signature asignature[]) {
            this$0 = PackageManagerBackupAgent.this;
            super();
            versionCode = i;
            signatures = asignature;
        }
    }


    PackageManagerBackupAgent(PackageManager packagemanager, List list) {
        mStateVersions = new HashMap();
        mPackageManager = packagemanager;
        mAllPackages = list;
        mRestoredSignatures = null;
        mHasMetadata = false;
    }

    private void parseStateFile(ParcelFileDescriptor parcelfiledescriptor) {
        mExisting.clear();
        mStateVersions.clear();
        mStoredSdkVersion = 0;
        mStoredIncrementalVersion = null;
        DataInputStream datainputstream = new DataInputStream(new FileInputStream(parcelfiledescriptor.getFileDescriptor()));
        byte[] _tmp = new byte[256];
        try {
            if(datainputstream.readUTF().equals("@meta@")) {
                mStoredSdkVersion = datainputstream.readInt();
                mStoredIncrementalVersion = datainputstream.readUTF();
                mExisting.add("@meta@");
                do {
                    String s = datainputstream.readUTF();
                    int i = datainputstream.readInt();
                    mExisting.add(s);
                    mStateVersions.put(s, new Metadata(i, null));
                } while(true);
            }
            Slog.e("PMBA", "No global metadata in state file!");
        }
        catch(EOFException eofexception) { }
        catch(IOException ioexception) {
            Slog.e("PMBA", (new StringBuilder()).append("Unable to read Package Manager state file: ").append(ioexception).toString());
        }
    }

    private static Signature[] readSignatureArray(DataInputStream datainputstream) {
        int i = datainputstream.readInt();
        if(i <= 20) goto _L2; else goto _L1
_L1:
        Signature asignature[];
        try {
            Slog.e("PMBA", "Suspiciously large sig count in restore data; aborting");
            throw new IllegalStateException("Bad restore state");
        }
        catch(IOException ioexception) {
            Slog.e("PMBA", "Unable to read signatures");
        }
        asignature = null;
_L5:
        return asignature;
        EOFException eofexception;
        eofexception;
        Slog.w("PMBA", "Read empty signature block");
        asignature = null;
        continue; /* Loop/switch isn't completed */
_L2:
        int j;
        asignature = new Signature[i];
        j = 0;
_L3:
        if(j >= i)
            continue; /* Loop/switch isn't completed */
        byte abyte0[] = new byte[datainputstream.readInt()];
        datainputstream.read(abyte0);
        asignature[j] = new Signature(abyte0);
        j++;
          goto _L3
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static void writeEntity(BackupDataOutput backupdataoutput, String s, byte abyte0[]) throws IOException {
        backupdataoutput.writeEntityHeader(s, abyte0.length);
        backupdataoutput.writeEntityData(abyte0, abyte0.length);
    }

    private static void writeSignatureArray(DataOutputStream dataoutputstream, Signature asignature[]) throws IOException {
        dataoutputstream.writeInt(asignature.length);
        int i = asignature.length;
        for(int j = 0; j < i; j++) {
            byte abyte0[] = asignature[j].toByteArray();
            dataoutputstream.writeInt(abyte0.length);
            dataoutputstream.write(abyte0);
        }

    }

    private void writeStateFile(List list, ParcelFileDescriptor parcelfiledescriptor) {
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(parcelfiledescriptor.getFileDescriptor()));
        try {
            dataoutputstream.writeUTF("@meta@");
            dataoutputstream.writeInt(android.os.Build.VERSION.SDK_INT);
            dataoutputstream.writeUTF(android.os.Build.VERSION.INCREMENTAL);
            PackageInfo packageinfo;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); dataoutputstream.writeInt(packageinfo.versionCode)) {
                packageinfo = (PackageInfo)iterator.next();
                dataoutputstream.writeUTF(packageinfo.packageName);
            }

        }
        catch(IOException ioexception) {
            Slog.e("PMBA", "Unable to write package manager state file!");
        }
    }

    public Metadata getRestoredMetadata(String s) {
        Metadata metadata;
        if(mRestoredSignatures == null) {
            Slog.w("PMBA", "getRestoredMetadata() before metadata read!");
            metadata = null;
        } else {
            metadata = (Metadata)mRestoredSignatures.get(s);
        }
        return metadata;
    }

    public Set getRestoredPackages() {
        Set set;
        if(mRestoredSignatures == null) {
            Slog.w("PMBA", "getRestoredPackages() before metadata read!");
            set = null;
        } else {
            set = mRestoredSignatures.keySet();
        }
        return set;
    }

    public boolean hasMetadata() {
        return mHasMetadata;
    }

    public void onBackup(ParcelFileDescriptor parcelfiledescriptor, BackupDataOutput backupdataoutput, ParcelFileDescriptor parcelfiledescriptor1) {
        ByteArrayOutputStream bytearrayoutputstream;
        DataOutputStream dataoutputstream;
        bytearrayoutputstream = new ByteArrayOutputStream();
        dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        parseStateFile(parcelfiledescriptor);
        if(mStoredIncrementalVersion == null || !mStoredIncrementalVersion.equals(android.os.Build.VERSION.INCREMENTAL)) {
            Slog.i("PMBA", (new StringBuilder()).append("Previous metadata ").append(mStoredIncrementalVersion).append(" mismatch vs ").append(android.os.Build.VERSION.INCREMENTAL).append(" - rewriting").toString());
            mExisting.clear();
        }
        if(mExisting.contains("@meta@")) goto _L2; else goto _L1
_L1:
        dataoutputstream.writeInt(android.os.Build.VERSION.SDK_INT);
        dataoutputstream.writeUTF(android.os.Build.VERSION.INCREMENTAL);
        writeEntity(backupdataoutput, "@meta@", bytearrayoutputstream.toByteArray());
_L11:
        Iterator iterator = mAllPackages.iterator();
_L6:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        String s1;
        boolean flag;
        s1 = ((PackageInfo)iterator.next()).packageName;
        flag = s1.equals("@meta@");
        if(flag) goto _L6; else goto _L5
_L5:
        PackageInfo packageinfo = mPackageManager.getPackageInfo(s1, 64);
        if(!mExisting.contains(s1)) goto _L8; else goto _L7
_L7:
        mExisting.remove(s1);
        if(packageinfo.versionCode == ((Metadata)mStateVersions.get(s1)).versionCode) goto _L6; else goto _L8
_L8:
        if(packageinfo.signatures != null && packageinfo.signatures.length != 0) goto _L10; else goto _L9
_L9:
        IOException ioexception;
        Slog.w("PMBA", (new StringBuilder()).append("Not backing up package ").append(s1).append(" since it appears to have no signatures.").toString());
          goto _L6
_L13:
        return;
_L2:
        mExisting.remove("@meta@");
          goto _L11
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        mExisting.add(s1);
          goto _L6
_L10:
        bytearrayoutputstream.reset();
        dataoutputstream.writeInt(packageinfo.versionCode);
        writeSignatureArray(dataoutputstream, packageinfo.signatures);
        writeEntity(backupdataoutput, s1, bytearrayoutputstream.toByteArray());
          goto _L6
_L4:
        Iterator iterator1 = mExisting.iterator();
_L12:
        String s;
        if(!iterator1.hasNext())
            break MISSING_BLOCK_LABEL_435;
        s = (String)iterator1.next();
        backupdataoutput.writeEntityHeader(s, -1);
          goto _L12
        IOException ioexception1;
        ioexception1;
        try {
            Slog.e("PMBA", "Unable to write package deletions!");
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception) {
            Slog.e("PMBA", "Unable to write package backup data file!");
        }
          goto _L13
        writeStateFile(mAllPackages, parcelfiledescriptor1);
          goto _L13
    }

    public void onRestore(BackupDataInput backupdatainput, int i, ParcelFileDescriptor parcelfiledescriptor) throws IOException {
        ArrayList arraylist;
        HashMap hashmap;
        arraylist = new ArrayList();
        hashmap = new HashMap();
_L5:
        String s;
        DataInputStream datainputstream;
        if(!backupdatainput.readNextHeader())
            break MISSING_BLOCK_LABEL_239;
        s = backupdatainput.getKey();
        int j = backupdatainput.getDataSize();
        byte abyte0[] = new byte[j];
        backupdatainput.readEntityData(abyte0, 0, j);
        datainputstream = new DataInputStream(new ByteArrayInputStream(abyte0));
        if(!s.equals("@meta@")) goto _L2; else goto _L1
_L1:
        int l = datainputstream.readInt();
        if(-1 <= android.os.Build.VERSION.SDK_INT) goto _L4; else goto _L3
_L3:
        Slog.w("PMBA", "Restore set was from a later version of Android; not restoring");
_L6:
        return;
_L4:
        mStoredSdkVersion = l;
        mStoredIncrementalVersion = datainputstream.readUTF();
        mHasMetadata = true;
          goto _L5
_L2:
        int k = datainputstream.readInt();
        Signature asignature[] = readSignatureArray(datainputstream);
        if(asignature == null || asignature.length == 0) {
            Slog.w("PMBA", (new StringBuilder()).append("Not restoring package ").append(s).append(" since it appears to have no signatures.").toString());
        } else {
            ApplicationInfo applicationinfo = new ApplicationInfo();
            applicationinfo.packageName = s;
            arraylist.add(applicationinfo);
            hashmap.put(s, new Metadata(k, asignature));
        }
          goto _L5
        mRestoredSignatures = hashmap;
          goto _L6
    }

    private static final boolean DEBUG = false;
    private static final String GLOBAL_METADATA_KEY = "@meta@";
    private static final String TAG = "PMBA";
    private List mAllPackages;
    private final HashSet mExisting = new HashSet();
    private boolean mHasMetadata;
    private PackageManager mPackageManager;
    private HashMap mRestoredSignatures;
    private HashMap mStateVersions;
    private String mStoredIncrementalVersion;
    private int mStoredSdkVersion;
}
