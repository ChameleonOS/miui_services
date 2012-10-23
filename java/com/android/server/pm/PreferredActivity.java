// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.ComponentName;
import android.content.IntentFilter;
import com.android.internal.util.XmlUtils;
import com.android.server.PreferredComponent;
import java.io.IOException;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.pm:
//            PackageManagerService

class PreferredActivity extends IntentFilter
    implements com.android.server.PreferredComponent.Callbacks {

    PreferredActivity(IntentFilter intentfilter, int i, ComponentName acomponentname[], ComponentName componentname) {
        super(intentfilter);
        mPref = new PreferredComponent(this, i, acomponentname, componentname);
    }

    PreferredActivity(XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        mPref = new PreferredComponent(this, xmlpullparser);
    }

    public boolean onReadTag(String s, XmlPullParser xmlpullparser) throws XmlPullParserException, IOException {
        if(s.equals("filter")) {
            readFromXml(xmlpullparser);
        } else {
            PackageManagerService.reportSettingsProblem(5, (new StringBuilder()).append("Unknown element under <preferred-activities>: ").append(xmlpullparser.getName()).toString());
            XmlUtils.skipCurrentTag(xmlpullparser);
        }
        return true;
    }

    public void writeToXml(XmlSerializer xmlserializer) throws IOException {
        mPref.writeToXml(xmlserializer);
        xmlserializer.startTag(null, "filter");
        super.writeToXml(xmlserializer);
        xmlserializer.endTag(null, "filter");
    }

    private static final boolean DEBUG_FILTERS = false;
    private static final String TAG = "PreferredActivity";
    final PreferredComponent mPref;
}
