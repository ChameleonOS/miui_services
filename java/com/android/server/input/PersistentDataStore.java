// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.input;

import android.util.Slog;
import android.util.Xml;
import com.android.internal.os.AtomicFile;
import com.android.internal.util.*;
import java.io.*;
import java.util.*;
import libcore.io.IoUtils;
import libcore.util.Objects;
import org.xmlpull.v1.*;

final class PersistentDataStore {
    private static final class InputDeviceState {

        private void updateCurrentKeyboardLayoutIfRemoved(String s, int i) {
            if(Objects.equal(mCurrentKeyboardLayout, s))
                if(!mKeyboardLayouts.isEmpty()) {
                    int j = i;
                    if(j == mKeyboardLayouts.size())
                        j = 0;
                    mCurrentKeyboardLayout = (String)mKeyboardLayouts.get(j);
                } else {
                    mCurrentKeyboardLayout = null;
                }
        }

        public boolean addKeyboardLayout(String s) {
            int i = Collections.binarySearch(mKeyboardLayouts, s);
            boolean flag;
            if(i >= 0) {
                flag = false;
            } else {
                mKeyboardLayouts.add(-1 + -i, s);
                if(mCurrentKeyboardLayout == null)
                    mCurrentKeyboardLayout = s;
                flag = true;
            }
            return flag;
        }

        public String getCurrentKeyboardLayout() {
            return mCurrentKeyboardLayout;
        }

        public String[] getKeyboardLayouts() {
            String as[];
            if(mKeyboardLayouts.isEmpty())
                as = (String[])(String[])ArrayUtils.emptyArray(java/lang/String);
            else
                as = (String[])mKeyboardLayouts.toArray(new String[mKeyboardLayouts.size()]);
            return as;
        }

        public void loadFromXml(XmlPullParser xmlpullparser) throws IOException, XmlPullParserException {
            int i = xmlpullparser.getDepth();
            do {
                if(!XmlUtils.nextElementWithin(xmlpullparser, i))
                    break;
                if(xmlpullparser.getName().equals("keyboard-layout")) {
                    String s = xmlpullparser.getAttributeValue(null, "descriptor");
                    if(s == null)
                        throw new XmlPullParserException("Missing descriptor attribute on keyboard-layout.");
                    String s1 = xmlpullparser.getAttributeValue(null, "current");
                    if(mKeyboardLayouts.contains(s))
                        throw new XmlPullParserException("Found duplicate keyboard layout.");
                    mKeyboardLayouts.add(s);
                    if(s1 != null && s1.equals("true")) {
                        if(mCurrentKeyboardLayout != null)
                            throw new XmlPullParserException("Found multiple current keyboard layouts.");
                        mCurrentKeyboardLayout = s;
                    }
                }
            } while(true);
            Collections.sort(mKeyboardLayouts);
            if(mCurrentKeyboardLayout == null && !mKeyboardLayouts.isEmpty())
                mCurrentKeyboardLayout = (String)mKeyboardLayouts.get(0);
        }

        public boolean removeKeyboardLayout(String s) {
            int i = Collections.binarySearch(mKeyboardLayouts, s);
            boolean flag;
            if(i < 0) {
                flag = false;
            } else {
                mKeyboardLayouts.remove(i);
                updateCurrentKeyboardLayoutIfRemoved(s, i);
                flag = true;
            }
            return flag;
        }

        public boolean removeUninstalledKeyboardLayouts(Set set) {
            boolean flag = false;
            int i = mKeyboardLayouts.size();
            do {
                int j = i - 1;
                if(i > 0) {
                    String s = (String)mKeyboardLayouts.get(j);
                    if(!set.contains(s)) {
                        Slog.i("InputManager", (new StringBuilder()).append("Removing uninstalled keyboard layout ").append(s).toString());
                        mKeyboardLayouts.remove(j);
                        updateCurrentKeyboardLayoutIfRemoved(s, j);
                        flag = true;
                    }
                    i = j;
                } else {
                    return flag;
                }
            } while(true);
        }

        public void saveToXml(XmlSerializer xmlserializer) throws IOException {
            for(Iterator iterator = mKeyboardLayouts.iterator(); iterator.hasNext(); xmlserializer.endTag(null, "keyboard-layout")) {
                String s = (String)iterator.next();
                xmlserializer.startTag(null, "keyboard-layout");
                xmlserializer.attribute(null, "descriptor", s);
                if(s.equals(mCurrentKeyboardLayout))
                    xmlserializer.attribute(null, "current", "true");
            }

        }

        public boolean setCurrentKeyboardLayout(String s) {
            boolean flag;
            if(Objects.equal(mCurrentKeyboardLayout, s)) {
                flag = false;
            } else {
                addKeyboardLayout(s);
                mCurrentKeyboardLayout = s;
                flag = true;
            }
            return flag;
        }

        public boolean switchKeyboardLayout(int i) {
            int j = mKeyboardLayouts.size();
            boolean flag;
            if(j < 2) {
                flag = false;
            } else {
                int k = Collections.binarySearch(mKeyboardLayouts, mCurrentKeyboardLayout);
                if(!$assertionsDisabled && k < 0)
                    throw new AssertionError();
                int l;
                if(i > 0)
                    l = (k + 1) % j;
                else
                    l = (-1 + (k + j)) % j;
                mCurrentKeyboardLayout = (String)mKeyboardLayouts.get(l);
                flag = true;
            }
            return flag;
        }

        static final boolean $assertionsDisabled;
        private String mCurrentKeyboardLayout;
        private ArrayList mKeyboardLayouts;

        static  {
            boolean flag;
            if(!com/android/server/input/PersistentDataStore.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        private InputDeviceState() {
            mKeyboardLayouts = new ArrayList();
        }

    }


    public PersistentDataStore() {
    }

    private void clearState() {
        mInputDevices.clear();
    }

    private InputDeviceState getInputDeviceState(String s, boolean flag) {
        loadIfNeeded();
        InputDeviceState inputdevicestate = (InputDeviceState)mInputDevices.get(s);
        if(inputdevicestate == null && flag) {
            inputdevicestate = new InputDeviceState();
            mInputDevices.put(s, inputdevicestate);
            setDirty();
        }
        return inputdevicestate;
    }

    private void load() {
        clearState();
        java.io.FileInputStream fileinputstream = mAtomicFile.openRead();
        XmlPullParser xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(new BufferedInputStream(fileinputstream), null);
        loadFromXml(xmlpullparser);
        IoUtils.closeQuietly(fileinputstream);
_L1:
        return;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
          goto _L1
        IOException ioexception;
        ioexception;
        Slog.w("InputManager", "Failed to load input manager persistent store data.", ioexception);
        clearState();
        IoUtils.closeQuietly(fileinputstream);
          goto _L1
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        Slog.w("InputManager", "Failed to load input manager persistent store data.", xmlpullparserexception);
        clearState();
        IoUtils.closeQuietly(fileinputstream);
          goto _L1
        Exception exception;
        exception;
        IoUtils.closeQuietly(fileinputstream);
        throw exception;
    }

    private void loadFromXml(XmlPullParser xmlpullparser) throws IOException, XmlPullParserException {
        XmlUtils.beginDocument(xmlpullparser, "input-manager-state");
        int i = xmlpullparser.getDepth();
        do {
            if(!XmlUtils.nextElementWithin(xmlpullparser, i))
                break;
            if(xmlpullparser.getName().equals("input-devices"))
                loadInputDevicesFromXml(xmlpullparser);
        } while(true);
    }

    private void loadIfNeeded() {
        if(!mLoaded) {
            load();
            mLoaded = true;
        }
    }

    private void loadInputDevicesFromXml(XmlPullParser xmlpullparser) throws IOException, XmlPullParserException {
        int i = xmlpullparser.getDepth();
        do {
            if(!XmlUtils.nextElementWithin(xmlpullparser, i))
                break;
            if(xmlpullparser.getName().equals("input-device")) {
                String s = xmlpullparser.getAttributeValue(null, "descriptor");
                if(s == null)
                    throw new XmlPullParserException("Missing descriptor attribute on input-device.");
                if(mInputDevices.containsKey(s))
                    throw new XmlPullParserException("Found duplicate input device.");
                InputDeviceState inputdevicestate = new InputDeviceState();
                inputdevicestate.loadFromXml(xmlpullparser);
                mInputDevices.put(s, inputdevicestate);
            }
        } while(true);
    }

    private void save() {
        java.io.FileOutputStream fileoutputstream = mAtomicFile.startWrite();
        FastXmlSerializer fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(new BufferedOutputStream(fileoutputstream), "utf-8");
        saveToXml(fastxmlserializer);
        fastxmlserializer.flush();
        if(false) goto _L2; else goto _L1
_L1:
        mAtomicFile.finishWrite(fileoutputstream);
          goto _L3
_L2:
        mAtomicFile.failWrite(fileoutputstream);
          goto _L3
        IOException ioexception;
        ioexception;
        Slog.w("InputManager", "Failed to save input manager persistent store data.", ioexception);
          goto _L3
        Exception exception;
        exception;
        if(true) goto _L5; else goto _L4
_L4:
        mAtomicFile.finishWrite(fileoutputstream);
_L6:
        throw exception;
_L5:
        mAtomicFile.failWrite(fileoutputstream);
        if(true) goto _L6; else goto _L3
_L3:
    }

    private void saveToXml(XmlSerializer xmlserializer) throws IOException {
        xmlserializer.startDocument(null, Boolean.valueOf(true));
        xmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlserializer.startTag(null, "input-manager-state");
        xmlserializer.startTag(null, "input-devices");
        for(Iterator iterator = mInputDevices.entrySet().iterator(); iterator.hasNext(); xmlserializer.endTag(null, "input-device")) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String s = (String)entry.getKey();
            InputDeviceState inputdevicestate = (InputDeviceState)entry.getValue();
            xmlserializer.startTag(null, "input-device");
            xmlserializer.attribute(null, "descriptor", s);
            inputdevicestate.saveToXml(xmlserializer);
        }

        xmlserializer.endTag(null, "input-devices");
        xmlserializer.endTag(null, "input-manager-state");
        xmlserializer.endDocument();
    }

    private void setDirty() {
        mDirty = true;
    }

    public boolean addKeyboardLayout(String s, String s1) {
        boolean flag = true;
        if(getInputDeviceState(s, flag).addKeyboardLayout(s1))
            setDirty();
        else
            flag = false;
        return flag;
    }

    public String getCurrentKeyboardLayout(String s) {
        InputDeviceState inputdevicestate = getInputDeviceState(s, false);
        String s1;
        if(inputdevicestate != null)
            s1 = inputdevicestate.getCurrentKeyboardLayout();
        else
            s1 = null;
        return s1;
    }

    public String[] getKeyboardLayouts(String s) {
        InputDeviceState inputdevicestate = getInputDeviceState(s, false);
        String as[];
        if(inputdevicestate == null)
            as = (String[])(String[])ArrayUtils.emptyArray(java/lang/String);
        else
            as = inputdevicestate.getKeyboardLayouts();
        return as;
    }

    public boolean removeKeyboardLayout(String s, String s1) {
        boolean flag = true;
        if(getInputDeviceState(s, flag).removeKeyboardLayout(s1))
            setDirty();
        else
            flag = false;
        return flag;
    }

    public boolean removeUninstalledKeyboardLayouts(Set set) {
        boolean flag = false;
        Iterator iterator = mInputDevices.values().iterator();
        do {
            if(!iterator.hasNext())
                break;
            if(((InputDeviceState)iterator.next()).removeUninstalledKeyboardLayouts(set))
                flag = true;
        } while(true);
        boolean flag1;
        if(flag) {
            setDirty();
            flag1 = true;
        } else {
            flag1 = false;
        }
        return flag1;
    }

    public void saveIfNeeded() {
        if(mDirty) {
            save();
            mDirty = false;
        }
    }

    public boolean setCurrentKeyboardLayout(String s, String s1) {
        boolean flag = true;
        if(getInputDeviceState(s, flag).setCurrentKeyboardLayout(s1))
            setDirty();
        else
            flag = false;
        return flag;
    }

    public boolean switchKeyboardLayout(String s, int i) {
        boolean flag = false;
        InputDeviceState inputdevicestate = getInputDeviceState(s, false);
        if(inputdevicestate != null && inputdevicestate.switchKeyboardLayout(i)) {
            setDirty();
            flag = true;
        }
        return flag;
    }

    static final String TAG = "InputManager";
    private final AtomicFile mAtomicFile = new AtomicFile(new File("/data/system/input-manager-state.xml"));
    private boolean mDirty;
    private final HashMap mInputDevices = new HashMap();
    private boolean mLoaded;
}
