// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.util.SparseBooleanArray;

class PackageVerificationState {

    public PackageVerificationState(int i, PackageManagerService.InstallArgs installargs) {
        mRequiredVerifierUid = i;
        mArgs = installargs;
    }

    public void addSufficientVerifier(int i) {
        mSufficientVerifierUids.put(i, true);
    }

    public PackageManagerService.InstallArgs getInstallArgs() {
        return mArgs;
    }

    public boolean isInstallAllowed() {
        boolean flag;
        if(!mRequiredVerificationPassed)
            flag = false;
        else
        if(mSufficientVerificationComplete)
            flag = mSufficientVerificationPassed;
        else
            flag = true;
        return flag;
    }

    public boolean isVerificationComplete() {
        boolean flag;
        if(!mRequiredVerificationComplete)
            flag = false;
        else
        if(mSufficientVerifierUids.size() == 0)
            flag = true;
        else
            flag = mSufficientVerificationComplete;
        return flag;
    }

    public boolean setVerifierResponse(int i, int j) {
        boolean flag = true;
        if(i != mRequiredVerifierUid) goto _L2; else goto _L1
_L1:
        mRequiredVerificationComplete = flag;
        j;
        JVM INSTR tableswitch 1 2: default 40
    //                   1 54
    //                   2 47;
           goto _L3 _L4 _L5
_L3:
        mRequiredVerificationPassed = false;
_L7:
        return flag;
_L5:
        mSufficientVerifierUids.clear();
_L4:
        mRequiredVerificationPassed = flag;
        continue; /* Loop/switch isn't completed */
_L2:
        if(mSufficientVerifierUids.get(i)) {
            if(j == flag) {
                mSufficientVerificationComplete = flag;
                mSufficientVerificationPassed = flag;
            }
            mSufficientVerifierUids.delete(i);
            if(mSufficientVerifierUids.size() == 0)
                mSufficientVerificationComplete = flag;
        } else {
            flag = false;
        }
        if(true) goto _L7; else goto _L6
_L6:
    }

    private final PackageManagerService.InstallArgs mArgs;
    private boolean mRequiredVerificationComplete;
    private boolean mRequiredVerificationPassed;
    private final int mRequiredVerifierUid;
    private boolean mSufficientVerificationComplete;
    private boolean mSufficientVerificationPassed;
    private final SparseBooleanArray mSufficientVerifierUids = new SparseBooleanArray();
}
