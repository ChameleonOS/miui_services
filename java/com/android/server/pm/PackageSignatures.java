// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.Signature;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.pm:
//            PackageManagerService

class PackageSignatures {

    PackageSignatures() {
    }

    PackageSignatures(PackageSignatures packagesignatures) {
        if(packagesignatures != null && packagesignatures.mSignatures != null)
            mSignatures = (Signature[])packagesignatures.mSignatures.clone();
    }

    PackageSignatures(Signature asignature[]) {
        assignSignatures(asignature);
    }

    void assignSignatures(Signature asignature[]) {
        if(asignature == null) {
            mSignatures = null;
        } else {
            mSignatures = new Signature[asignature.length];
            int i = 0;
            while(i < asignature.length)  {
                mSignatures[i] = asignature[i];
                i++;
            }
        }
    }

    void readXml(XmlPullParser xmlpullparser, ArrayList arraylist) throws IOException, XmlPullParserException {
        int i;
        int j;
        int k;
        String s = xmlpullparser.getAttributeValue(null, "count");
        if(s == null) {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <signatures> has no count at ").append(xmlpullparser.getPositionDescription()).toString());
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
        i = Integer.parseInt(s);
        mSignatures = new Signature[i];
        j = 0;
        k = xmlpullparser.getDepth();
_L11:
        int l;
        do {
            l = xmlpullparser.next();
            if(l == 1 || l == 3 && xmlpullparser.getDepth() <= k)
                break MISSING_BLOCK_LABEL_564;
        } while(l == 3 || l == 4);
        if(!xmlpullparser.getName().equals("cert"))
            break MISSING_BLOCK_LABEL_533;
        if(j >= i) goto _L2; else goto _L1
_L1:
        String s1 = xmlpullparser.getAttributeValue(null, "index");
        if(s1 == null) goto _L4; else goto _L3
_L3:
        int i1;
        String s2;
        i1 = Integer.parseInt(s1);
        s2 = xmlpullparser.getAttributeValue(null, "key");
        if(s2 != null) goto _L6; else goto _L5
_L5:
        if(i1 < 0 || i1 >= arraylist.size()) goto _L8; else goto _L7
_L7:
        if((Signature)arraylist.get(i1) == null) goto _L10; else goto _L9
_L9:
        mSignatures[j] = (Signature)arraylist.get(i1);
        j++;
_L12:
        XmlUtils.skipCurrentTag(xmlpullparser);
          goto _L11
_L10:
        try {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <cert> index ").append(s1).append(" is not defined at ").append(xmlpullparser.getPositionDescription()).toString());
        }
        catch(NumberFormatException numberformatexception) {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <cert> index ").append(s1).append(" is not a number at ").append(xmlpullparser.getPositionDescription()).toString());
        }
        catch(IllegalArgumentException illegalargumentexception) {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <cert> index ").append(s1).append(" has an invalid signature at ").append(xmlpullparser.getPositionDescription()).append(": ").append(illegalargumentexception.getMessage()).toString());
        }
          goto _L12
_L8:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <cert> index ").append(s1).append(" is out of bounds at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L12
_L6:
        for(; arraylist.size() <= i1; arraylist.add(null));
        Signature signature = new Signature(s2);
        arraylist.set(i1, signature);
        mSignatures[j] = signature;
        j++;
          goto _L12
_L4:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: <cert> has no index at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L12
_L2:
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Error in package manager settings: too many <cert> tags, expected ").append(i).append(" at ").append(xmlpullparser.getPositionDescription()).toString());
          goto _L12
        PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <cert>: ").append(xmlpullparser.getName()).toString());
          goto _L12
        if(j < i) {
            Signature asignature[] = new Signature[j];
            System.arraycopy(mSignatures, 0, asignature, 0, j);
            mSignatures = asignature;
        }
        return;
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer(128);
        stringbuffer.append("PackageSignatures{");
        stringbuffer.append(Integer.toHexString(System.identityHashCode(this)));
        stringbuffer.append(" [");
        if(mSignatures != null) {
            for(int i = 0; i < mSignatures.length; i++) {
                if(i > 0)
                    stringbuffer.append(", ");
                stringbuffer.append(Integer.toHexString(System.identityHashCode(mSignatures[i])));
            }

        }
        stringbuffer.append("]}");
        return stringbuffer.toString();
    }

    void writeXml(XmlSerializer xmlserializer, String s, ArrayList arraylist) throws IOException {
        if(mSignatures != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        xmlserializer.startTag(null, s);
        xmlserializer.attribute(null, "count", Integer.toString(mSignatures.length));
        int i = 0;
        do {
label0:
            {
                if(i >= mSignatures.length)
                    break label0;
                xmlserializer.startTag(null, "cert");
                Signature signature = mSignatures[i];
                int j = signature.hashCode();
                int k = arraylist.size();
                int l = 0;
                do {
label1:
                    {
                        if(l < k) {
                            Signature signature1 = (Signature)arraylist.get(l);
                            if(signature1.hashCode() != j || !signature1.equals(signature))
                                break label1;
                            xmlserializer.attribute(null, "index", Integer.toString(l));
                        }
                        if(l >= k) {
                            arraylist.add(signature);
                            xmlserializer.attribute(null, "index", Integer.toString(k));
                            xmlserializer.attribute(null, "key", signature.toCharsString());
                        }
                        xmlserializer.endTag(null, "cert");
                        i++;
                    }
                    if(true)
                        break;
                    l++;
                } while(true);
            }
        } while(true);
        xmlserializer.endTag(null, s);
        if(true) goto _L1; else goto _L3
_L3:
    }

    Signature mSignatures[];
}
