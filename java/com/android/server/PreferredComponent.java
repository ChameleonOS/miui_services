// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.ComponentName;
import android.content.pm.ComponentInfo;
import android.content.pm.ResolveInfo;
import android.util.Slog;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.xmlpull.v1.*;

public class PreferredComponent {
    public static interface Callbacks {

        public abstract boolean onReadTag(String s, XmlPullParser xmlpullparser) throws XmlPullParserException, IOException;
    }


    public PreferredComponent(Callbacks callbacks, int i, ComponentName acomponentname[], ComponentName componentname) {
        int j;
        String as[];
        String as1[];
        String as2[];
        int k;
        mCallbacks = callbacks;
        mMatch = 0xfff0000 & i;
        mComponent = componentname;
        mShortComponent = componentname.flattenToShortString();
        mParseError = null;
        if(acomponentname == null)
            break MISSING_BLOCK_LABEL_169;
        j = acomponentname.length;
        as = new String[j];
        as1 = new String[j];
        as2 = new String[j];
        k = 0;
_L5:
        if(k >= j) goto _L2; else goto _L1
_L1:
        ComponentName componentname1 = acomponentname[k];
        if(componentname1 != null) goto _L4; else goto _L3
_L3:
        mSetPackages = null;
        mSetClasses = null;
        mSetComponents = null;
_L6:
        return;
_L4:
        as[k] = componentname1.getPackageName().intern();
        as1[k] = componentname1.getClassName().intern();
        as2[k] = componentname1.flattenToShortString().intern();
        k++;
          goto _L5
_L2:
        mSetPackages = as;
        mSetClasses = as1;
        mSetComponents = as2;
          goto _L6
        mSetPackages = null;
        mSetClasses = null;
        mSetComponents = null;
          goto _L6
    }

    public PreferredComponent(Callbacks callbacks, XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        int j;
        String as[];
        String as1[];
        String as2[];
        int k;
        String s2;
        String s3;
        mCallbacks = callbacks;
        mShortComponent = xmlpullparser.getAttributeValue(null, "name");
        mComponent = ComponentName.unflattenFromString(mShortComponent);
        if(mComponent == null)
            mParseError = (new StringBuilder()).append("Bad activity name ").append(mShortComponent).toString();
        String s = xmlpullparser.getAttributeValue(null, "match");
        int i;
        String s1;
        int l;
        int i1;
        if(s != null)
            i = Integer.parseInt(s, 16);
        else
            i = 0;
        mMatch = i;
        s1 = xmlpullparser.getAttributeValue(null, "set");
        if(s1 != null)
            j = Integer.parseInt(s1);
        else
            j = 0;
        if(j > 0)
            as = new String[j];
        else
            as = null;
        if(j > 0)
            as1 = new String[j];
        else
            as1 = null;
        if(j > 0)
            as2 = new String[j];
        else
            as2 = null;
        k = 0;
        l = xmlpullparser.getDepth();
_L7:
        i1 = xmlpullparser.next();
        if(i1 == 1 || i1 == 3 && xmlpullparser.getDepth() <= l)
            break; /* Loop/switch isn't completed */
        if(i1 == 3 || i1 == 4)
            continue; /* Loop/switch isn't completed */
        s2 = xmlpullparser.getName();
        if(!s2.equals("set")) goto _L2; else goto _L1
_L1:
        s3 = xmlpullparser.getAttributeValue(null, "name");
        if(s3 != null) goto _L4; else goto _L3
_L3:
        if(mParseError == null)
            mParseError = (new StringBuilder()).append("No name in set tag in preferred activity ").append(mShortComponent).toString();
_L5:
        XmlUtils.skipCurrentTag(xmlpullparser);
        continue; /* Loop/switch isn't completed */
_L4:
        if(k >= j) {
            if(mParseError == null)
                mParseError = (new StringBuilder()).append("Too many set tags in preferred activity ").append(mShortComponent).toString();
        } else {
            ComponentName componentname = ComponentName.unflattenFromString(s3);
            if(componentname == null) {
                if(mParseError == null)
                    mParseError = (new StringBuilder()).append("Bad set name ").append(s3).append(" in preferred activity ").append(mShortComponent).toString();
            } else {
                as[k] = componentname.getPackageName();
                as1[k] = componentname.getClassName();
                as2[k] = s3;
                k++;
            }
        }
        if(true) goto _L5; else goto _L2
_L2:
        if(!mCallbacks.onReadTag(s2, xmlpullparser)) {
            Slog.w("PreferredComponent", (new StringBuilder()).append("Unknown element: ").append(xmlpullparser.getName()).toString());
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
        if(true) goto _L7; else goto _L6
_L6:
        if(k != j && mParseError == null)
            mParseError = (new StringBuilder()).append("Not enough set tags (expected ").append(j).append(" but found ").append(k).append(") in ").append(mShortComponent).toString();
        mSetPackages = as;
        mSetClasses = as1;
        mSetComponents = as2;
        return;
    }

    public void dump(PrintWriter printwriter, String s, Object obj) {
        printwriter.print(s);
        printwriter.print(Integer.toHexString(System.identityHashCode(obj)));
        printwriter.print(' ');
        printwriter.print(mComponent.flattenToShortString());
        printwriter.print(" match=0x");
        printwriter.println(Integer.toHexString(mMatch));
        if(mSetComponents != null) {
            printwriter.print(s);
            printwriter.println("  Selected from:");
            for(int i = 0; i < mSetComponents.length; i++) {
                printwriter.print(s);
                printwriter.print("    ");
                printwriter.println(mSetComponents[i]);
            }

        }
    }

    public String getParseError() {
        return mParseError;
    }

    public boolean sameSet(List list, int i) {
        boolean flag = false;
        if(mSetPackages != null) goto _L2; else goto _L1
_L1:
        return flag;
_L2:
        int j;
        int k;
        int l;
        int i1;
        j = list.size();
        k = mSetPackages.length;
        l = 0;
        i1 = 0;
_L4:
        ResolveInfo resolveinfo;
        if(i1 >= j)
            break MISSING_BLOCK_LABEL_143;
        resolveinfo = (ResolveInfo)list.get(i1);
        if(resolveinfo.priority == i)
            break; /* Loop/switch isn't completed */
_L6:
        i1++;
        if(true) goto _L4; else goto _L3
_L3:
        android.content.pm.ActivityInfo activityinfo;
        boolean flag1;
        int j1;
        activityinfo = resolveinfo.activityInfo;
        flag1 = false;
        j1 = 0;
_L7:
        if(j1 >= k)
            continue; /* Loop/switch isn't completed */
        if(!mSetPackages[j1].equals(((ComponentInfo) (activityinfo)).packageName) || !mSetClasses[j1].equals(((ComponentInfo) (activityinfo)).name))
            break MISSING_BLOCK_LABEL_137;
        l++;
        flag1 = true;
        if(flag1) goto _L6; else goto _L5
_L5:
        continue; /* Loop/switch isn't completed */
        j1++;
          goto _L7
        if(l == k)
            flag = true;
        if(true) goto _L1; else goto _L8
_L8:
    }

    public void writeToXml(XmlSerializer xmlserializer) throws IOException {
        int i;
        if(mSetClasses != null)
            i = mSetClasses.length;
        else
            i = 0;
        xmlserializer.attribute(null, "name", mShortComponent);
        if(mMatch != 0)
            xmlserializer.attribute(null, "match", Integer.toHexString(mMatch));
        xmlserializer.attribute(null, "set", Integer.toString(i));
        for(int j = 0; j < i; j++) {
            xmlserializer.startTag(null, "set");
            xmlserializer.attribute(null, "name", mSetComponents[j]);
            xmlserializer.endTag(null, "set");
        }

    }

    private final Callbacks mCallbacks;
    public final ComponentName mComponent;
    public final int mMatch;
    private String mParseError;
    private final String mSetClasses[];
    private final String mSetComponents[];
    private final String mSetPackages[];
    private final String mShortComponent;
}
