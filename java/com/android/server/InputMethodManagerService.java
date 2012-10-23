// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.database.ContentObserver;
import android.os.*;
import android.text.TextUtils;
import android.text.style.SuggestionSpan;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.AtomicFile;
import com.android.internal.os.HandlerCaller;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.view.*;
import com.android.server.wm.WindowManagerService;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server:
//            StatusBarManagerService

public class InputMethodManagerService extends com.android.internal.view.IInputMethodManager.Stub
    implements ServiceConnection, android.os.Handler.Callback {
    private static class InputMethodFileManager {

        private void deleteAllInputMethodSubtypes(String s) {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            mSubtypesMap.remove(s);
            writeAdditionalInputMethodSubtypes(mSubtypesMap, mAdditionalInputMethodSubtypeFile, mMethodMap);
            return;
        }

        private static void readAdditionalInputMethodSubtypes(HashMap hashmap, AtomicFile atomicfile) {
            if(hashmap != null && atomicfile != null) goto _L2; else goto _L1
_L1:
            return;
_L2:
            FileInputStream fileinputstream;
            hashmap.clear();
            fileinputstream = null;
            XmlPullParser xmlpullparser;
            fileinputstream = atomicfile.openRead();
            xmlpullparser = Xml.newPullParser();
            xmlpullparser.setInput(fileinputstream, null);
            xmlpullparser.getEventType();
            int i;
            do
                i = xmlpullparser.next();
            while(i != 2 && i != 1);
            if(!"subtypes".equals(xmlpullparser.getName()))
                throw new XmlPullParserException("Xml doesn't start with subtypes");
              goto _L3
            XmlPullParserException xmlpullparserexception;
            xmlpullparserexception;
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Error reading subtypes: ").append(xmlpullparserexception).toString());
            if(fileinputstream == null) goto _L1; else goto _L4
_L4:
            fileinputstream.close();
              goto _L1
            IOException ioexception4;
            ioexception4;
            String s;
            String s1;
            s = "InputMethodManagerService";
            s1 = "Failed to close.";
_L14:
            Slog.w(s, s1);
              goto _L1
_L3:
            int j;
            String s2;
            ArrayList arraylist;
            j = xmlpullparser.getDepth();
            s2 = null;
            arraylist = null;
_L8:
            int k = xmlpullparser.next();
            if(k == 3 && xmlpullparser.getDepth() <= j || k == 1) goto _L6; else goto _L5
_L5:
            if(k != 2) goto _L8; else goto _L7
_L7:
            String s3 = xmlpullparser.getName();
            if(!"imi".equals(s3)) goto _L10; else goto _L9
_L9:
            s2 = xmlpullparser.getAttributeValue(null, "id");
            if(!TextUtils.isEmpty(s2)) goto _L12; else goto _L11
_L11:
            Slog.w("InputMethodManagerService", "Invalid imi id found in subtypes.xml");
              goto _L8
            IOException ioexception2;
            ioexception2;
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Error reading subtypes: ").append(ioexception2).toString());
            if(fileinputstream == null) goto _L1; else goto _L13
_L13:
            fileinputstream.close();
              goto _L1
            IOException ioexception3;
            ioexception3;
            s = "InputMethodManagerService";
            s1 = "Failed to close.";
              goto _L14
_L12:
            arraylist = new ArrayList();
            hashmap.put(s2, arraylist);
              goto _L8
            NumberFormatException numberformatexception;
            numberformatexception;
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Error reading subtypes: ").append(numberformatexception).toString());
            if(fileinputstream == null) goto _L1; else goto _L15
_L15:
            fileinputstream.close();
              goto _L1
            IOException ioexception1;
            ioexception1;
            s = "InputMethodManagerService";
            s1 = "Failed to close.";
              goto _L14
_L10:
            if(!"subtype".equals(s3)) goto _L8; else goto _L16
_L16:
            if(!TextUtils.isEmpty(s2) && arraylist != null)
                break MISSING_BLOCK_LABEL_440;
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("IME uninstalled or not valid.: ").append(s2).toString());
              goto _L8
            Exception exception;
            exception;
            int l;
            InputMethodSubtype inputmethodsubtype;
            if(fileinputstream != null)
                try {
                    fileinputstream.close();
                }
                catch(IOException ioexception) {
                    Slog.w("InputMethodManagerService", "Failed to close.");
                }
            throw exception;
            l = Integer.valueOf(xmlpullparser.getAttributeValue(null, "icon")).intValue();
            inputmethodsubtype = new InputMethodSubtype(Integer.valueOf(xmlpullparser.getAttributeValue(null, "label")).intValue(), l, xmlpullparser.getAttributeValue(null, "imeSubtypeLocale"), xmlpullparser.getAttributeValue(null, "imeSubtypeMode"), xmlpullparser.getAttributeValue(null, "imeSubtypeExtraValue"), "1".equals(String.valueOf(xmlpullparser.getAttributeValue(null, "isAuxiliary"))));
            arraylist.add(inputmethodsubtype);
              goto _L8
_L6:
            if(fileinputstream == null) goto _L1; else goto _L17
_L17:
            fileinputstream.close();
              goto _L1
            IOException ioexception5;
            ioexception5;
            s = "InputMethodManagerService";
            s1 = "Failed to close.";
              goto _L14
        }

        private static void writeAdditionalInputMethodSubtypes(HashMap hashmap, AtomicFile atomicfile, HashMap hashmap1) {
            java.io.FileOutputStream fileoutputstream;
            FastXmlSerializer fastxmlserializer;
            String s;
            boolean flag;
            IOException ioexception;
            Iterator iterator;
            if(hashmap1 != null && hashmap1.size() > 0)
                flag = true;
            else
                flag = false;
            fileoutputstream = null;
            fileoutputstream = atomicfile.startWrite();
            fastxmlserializer = new FastXmlSerializer();
            fastxmlserializer.setOutput(fileoutputstream, "utf-8");
            fastxmlserializer.startDocument(null, Boolean.valueOf(true));
            fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            fastxmlserializer.startTag(null, "subtypes");
            iterator = hashmap.keySet().iterator();
_L5:
            if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
            s = (String)iterator.next();
            if(!flag || hashmap1.containsKey(s)) goto _L4; else goto _L3
_L3:
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("IME uninstalled or not valid.: ").append(s).toString());
              goto _L5
            ioexception;
            Slog.w("InputMethodManagerService", "Error writing subtypes", ioexception);
            if(fileoutputstream != null)
                atomicfile.failWrite(fileoutputstream);
_L8:
            return;
_L4:
            List list;
            int i;
            int j;
            fastxmlserializer.startTag(null, "imi");
            fastxmlserializer.attribute(null, "id", s);
            list = (List)hashmap.get(s);
            i = list.size();
            j = 0;
_L7:
            int k;
            if(j >= i)
                break; /* Loop/switch isn't completed */
            InputMethodSubtype inputmethodsubtype = (InputMethodSubtype)list.get(j);
            fastxmlserializer.startTag(null, "subtype");
            fastxmlserializer.attribute(null, "icon", String.valueOf(inputmethodsubtype.getIconResId()));
            fastxmlserializer.attribute(null, "label", String.valueOf(inputmethodsubtype.getNameResId()));
            fastxmlserializer.attribute(null, "imeSubtypeLocale", inputmethodsubtype.getLocale());
            fastxmlserializer.attribute(null, "imeSubtypeMode", inputmethodsubtype.getMode());
            fastxmlserializer.attribute(null, "imeSubtypeExtraValue", inputmethodsubtype.getExtraValue());
            if(!inputmethodsubtype.isAuxiliary())
                break MISSING_BLOCK_LABEL_431;
            k = 1;
_L9:
            fastxmlserializer.attribute(null, "isAuxiliary", String.valueOf(k));
            fastxmlserializer.endTag(null, "subtype");
            j++;
            if(true) goto _L7; else goto _L6
_L6:
            fastxmlserializer.endTag(null, "imi");
              goto _L5
_L2:
            fastxmlserializer.endTag(null, "subtypes");
            fastxmlserializer.endDocument();
            atomicfile.finishWrite(fileoutputstream);
              goto _L8
            k = 0;
              goto _L9
        }

        public void addInputMethodSubtypes(InputMethodInfo inputmethodinfo, InputMethodSubtype ainputmethodsubtype[]) {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            ArrayList arraylist = new ArrayList();
            int i = ainputmethodsubtype.length;
            int j = 0;
            do {
                if(j < i) {
                    InputMethodSubtype inputmethodsubtype = ainputmethodsubtype[j];
                    if(!arraylist.contains(inputmethodsubtype))
                        arraylist.add(inputmethodsubtype);
                } else {
                    mSubtypesMap.put(inputmethodinfo.getId(), arraylist);
                    writeAdditionalInputMethodSubtypes(mSubtypesMap, mAdditionalInputMethodSubtypeFile, mMethodMap);
                    return;
                }
                j++;
            } while(true);
        }

        public HashMap getAllAdditionalInputMethodSubtypes() {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            HashMap hashmap1 = mSubtypesMap;
            return hashmap1;
        }

        private static final String ADDITIONAL_SUBTYPES_FILE_NAME = "subtypes.xml";
        private static final String ATTR_ICON = "icon";
        private static final String ATTR_ID = "id";
        private static final String ATTR_IME_SUBTYPE_EXTRA_VALUE = "imeSubtypeExtraValue";
        private static final String ATTR_IME_SUBTYPE_LOCALE = "imeSubtypeLocale";
        private static final String ATTR_IME_SUBTYPE_MODE = "imeSubtypeMode";
        private static final String ATTR_IS_AUXILIARY = "isAuxiliary";
        private static final String ATTR_LABEL = "label";
        private static final String INPUT_METHOD_PATH = "inputmethod";
        private static final String NODE_IMI = "imi";
        private static final String NODE_SUBTYPE = "subtype";
        private static final String NODE_SUBTYPES = "subtypes";
        private static final String SYSTEM_PATH = "system";
        private final AtomicFile mAdditionalInputMethodSubtypeFile;
        private final HashMap mMethodMap;
        private final HashMap mSubtypesMap = new HashMap();


        public InputMethodFileManager(HashMap hashmap) {
            if(hashmap == null)
                throw new NullPointerException("methodMap is null");
            mMethodMap = hashmap;
            File file = new File(new File(Environment.getDataDirectory(), "system"), "inputmethod");
            if(!file.mkdirs())
                Slog.w("InputMethodManagerService", (new StringBuilder()).append("Couldn't create dir.: ").append(file.getAbsolutePath()).toString());
            File file1 = new File(file, "subtypes.xml");
            mAdditionalInputMethodSubtypeFile = new AtomicFile(file1);
            if(!file1.exists())
                writeAdditionalInputMethodSubtypes(mSubtypesMap, mAdditionalInputMethodSubtypeFile, hashmap);
            else
                readAdditionalInputMethodSubtypes(mSubtypesMap, mAdditionalInputMethodSubtypeFile);
        }
    }

    private static class InputMethodSettings {

        private static void buildEnabledInputMethodsSettingString(StringBuilder stringbuilder, Pair pair) {
            String s = (String)pair.first;
            ArrayList arraylist = (ArrayList)pair.second;
            stringbuilder.append(s);
            String s1;
            for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); stringbuilder.append(';').append(s1))
                s1 = (String)iterator.next();

        }

        private List createEnabledInputMethodAndSubtypeHashCodeListLocked(List list) {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = list.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                Pair pair = (Pair)iterator.next();
                InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(pair.first);
                if(inputmethodinfo != null)
                    arraylist.add(new Pair(inputmethodinfo, pair.second));
            } while(true);
            return arraylist;
        }

        private List createEnabledInputMethodListLocked(List list) {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = list.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                Pair pair = (Pair)iterator.next();
                InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(pair.first);
                if(inputmethodinfo != null)
                    arraylist.add(inputmethodinfo);
            } while(true);
            return arraylist;
        }

        private List getEnabledInputMethodsAndSubtypeListLocked() {
            ArrayList arraylist = new ArrayList();
            String s = getEnabledInputMethodsStr();
            if(!TextUtils.isEmpty(s)) {
                mInputMethodSplitter.setString(s);
                while(mInputMethodSplitter.hasNext())  {
                    String s1 = mInputMethodSplitter.next();
                    mSubtypeSplitter.setString(s1);
                    if(mSubtypeSplitter.hasNext()) {
                        ArrayList arraylist1 = new ArrayList();
                        String s2 = mSubtypeSplitter.next();
                        for(; mSubtypeSplitter.hasNext(); arraylist1.add(mSubtypeSplitter.next()));
                        arraylist.add(new Pair(s2, arraylist1));
                    }
                }
            }
            return arraylist;
        }

        private String getEnabledInputMethodsStr() {
            mEnabledInputMethodsStrCache = android.provider.Settings.Secure.getString(mResolver, "enabled_input_methods");
            return mEnabledInputMethodsStrCache;
        }

        private String getEnabledSubtypeHashCodeForInputMethodAndSubtypeLocked(List list, String s, String s1) {
            ArrayList arraylist;
            InputMethodInfo inputmethodinfo;
            Iterator iterator = list.iterator();
            Pair pair;
            do {
                if(!iterator.hasNext())
                    break MISSING_BLOCK_LABEL_243;
                pair = (Pair)iterator.next();
            } while(!((String)pair.first).equals(s));
            arraylist = (ArrayList)pair.second;
            inputmethodinfo = (InputMethodInfo)mMethodMap.get(s);
            if(arraylist.size() != 0) goto _L2; else goto _L1
_L1:
            if(inputmethodinfo == null || inputmethodinfo.getSubtypeCount() <= 0) goto _L4; else goto _L3
_L3:
            ArrayList arraylist1 = InputMethodManagerService.getImplicitlyApplicableSubtypesLocked(mRes, inputmethodinfo);
            if(arraylist1 == null) goto _L4; else goto _L5
_L5:
            int i;
            int j;
            i = arraylist1.size();
            j = 0;
_L9:
            if(j >= i) goto _L4; else goto _L6
_L6:
            if(!String.valueOf(((InputMethodSubtype)arraylist1.get(j)).hashCode()).equals(s1)) goto _L8; else goto _L7
_L7:
            return s1;
_L8:
            j++;
              goto _L9
_L2:
            Iterator iterator1 = arraylist.iterator();
_L12:
            if(!iterator1.hasNext()) goto _L4; else goto _L10
_L10:
            String s2 = (String)iterator1.next();
            if(!s2.equals(s1)) goto _L12; else goto _L11
_L11:
label0:
            {
                if(!InputMethodManagerService.isValidSubtypeId(inputmethodinfo, Integer.valueOf(s1).intValue()))
                    break label0;
                s1 = s2;
            }
              goto _L7
            String s3 = InputMethodManagerService.NOT_A_SUBTYPE_ID_STR;
            s1 = s3;
              goto _L7
            NumberFormatException numberformatexception;
            numberformatexception;
            s1 = InputMethodManagerService.NOT_A_SUBTYPE_ID_STR;
              goto _L7
_L4:
            s1 = InputMethodManagerService.NOT_A_SUBTYPE_ID_STR;
              goto _L7
            s1 = null;
              goto _L7
        }

        private Pair getLastSubtypeForInputMethodLockedInternal(String s) {
            List list;
            Iterator iterator;
            list = getEnabledInputMethodsAndSubtypeListLocked();
            iterator = loadInputMethodAndSubtypeHistoryLocked().iterator();
_L4:
            if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
            Pair pair1;
            String s1;
            pair1 = (Pair)iterator.next();
            s1 = (String)pair1.first;
            if(!TextUtils.isEmpty(s) && !s1.equals(s)) goto _L4; else goto _L3
_L3:
            String s2 = getEnabledSubtypeHashCodeForInputMethodAndSubtypeLocked(list, s1, (String)pair1.second);
            if(TextUtils.isEmpty(s2)) goto _L4; else goto _L5
_L5:
            Pair pair = new Pair(s1, s2);
_L7:
            return pair;
_L2:
            pair = null;
            if(true) goto _L7; else goto _L6
_L6:
        }

        private String getSubtypeHistoryStr() {
            return android.provider.Settings.Secure.getString(mResolver, "input_methods_subtype_history");
        }

        private List loadInputMethodAndSubtypeHistoryLocked() {
            ArrayList arraylist = new ArrayList();
            String s = getSubtypeHistoryStr();
            if(!TextUtils.isEmpty(s)) {
                mInputMethodSplitter.setString(s);
                while(mInputMethodSplitter.hasNext())  {
                    String s1 = mInputMethodSplitter.next();
                    mSubtypeSplitter.setString(s1);
                    if(mSubtypeSplitter.hasNext()) {
                        String s2 = InputMethodManagerService.NOT_A_SUBTYPE_ID_STR;
                        String s3 = mSubtypeSplitter.next();
                        if(mSubtypeSplitter.hasNext())
                            s2 = mSubtypeSplitter.next();
                        arraylist.add(new Pair(s3, s2));
                    }
                }
            }
            return arraylist;
        }

        private void putEnabledInputMethodsStr(String s) {
            android.provider.Settings.Secure.putString(mResolver, "enabled_input_methods", s);
            mEnabledInputMethodsStrCache = s;
        }

        private void putSubtypeHistoryStr(String s) {
            android.provider.Settings.Secure.putString(mResolver, "input_methods_subtype_history", s);
        }

        private void saveSubtypeHistory(List list, String s, String s1) {
            StringBuilder stringbuilder = new StringBuilder();
            boolean flag = false;
            if(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(s1)) {
                stringbuilder.append(s).append(';').append(s1);
                flag = true;
            }
            Iterator iterator = list.iterator();
            while(iterator.hasNext())  {
                Pair pair = (Pair)iterator.next();
                String s2 = (String)pair.first;
                String s3 = (String)pair.second;
                if(TextUtils.isEmpty(s3))
                    s3 = InputMethodManagerService.NOT_A_SUBTYPE_ID_STR;
                if(flag)
                    stringbuilder.append(':');
                else
                    flag = true;
                stringbuilder.append(s2).append(';').append(s3);
            }
            putSubtypeHistoryStr(stringbuilder.toString());
        }

        public void addSubtypeToHistory(String s, String s1) {
            List list = loadInputMethodAndSubtypeHistoryLocked();
            Iterator iterator = list.iterator();
            do {
                if(!iterator.hasNext())
                    break;
                Pair pair = (Pair)iterator.next();
                if(!((String)pair.first).equals(s))
                    continue;
                list.remove(pair);
                break;
            } while(true);
            saveSubtypeHistory(list, s, s1);
        }

        public void appendAndPutEnabledInputMethodLocked(String s, boolean flag) {
            if(flag)
                getEnabledInputMethodsStr();
            if(TextUtils.isEmpty(mEnabledInputMethodsStrCache))
                putEnabledInputMethodsStr(s);
            else
                putEnabledInputMethodsStr((new StringBuilder()).append(mEnabledInputMethodsStrCache).append(':').append(s).toString());
        }

        public boolean buildAndPutEnabledInputMethodsStrRemovingIdLocked(StringBuilder stringbuilder, List list, String s) {
            boolean flag = false;
            boolean flag1 = false;
            for(Iterator iterator = list.iterator(); iterator.hasNext();) {
                Pair pair = (Pair)iterator.next();
                if(((String)pair.first).equals(s)) {
                    flag = true;
                } else {
                    if(flag1)
                        stringbuilder.append(':');
                    else
                        flag1 = true;
                    buildEnabledInputMethodsSettingString(stringbuilder, pair);
                }
            }

            if(flag)
                putEnabledInputMethodsStr(stringbuilder.toString());
            return flag;
        }

        public void enableAllIMEsIfThereIsNoEnabledIME() {
            if(TextUtils.isEmpty(getEnabledInputMethodsStr())) {
                StringBuilder stringbuilder = new StringBuilder();
                int i = mMethodList.size();
                for(int j = 0; j < i; j++) {
                    InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodList.get(j);
                    Slog.i("InputMethodManagerService", (new StringBuilder()).append("Adding: ").append(inputmethodinfo.getId()).toString());
                    if(j > 0)
                        stringbuilder.append(':');
                    stringbuilder.append(inputmethodinfo.getId());
                }

                putEnabledInputMethodsStr(stringbuilder.toString());
            }
        }

        public List getEnabledInputMethodAndSubtypeHashCodeListLocked() {
            return createEnabledInputMethodAndSubtypeHashCodeListLocked(getEnabledInputMethodsAndSubtypeListLocked());
        }

        public List getEnabledInputMethodListLocked() {
            return createEnabledInputMethodListLocked(getEnabledInputMethodsAndSubtypeListLocked());
        }

        public List getEnabledInputMethodSubtypeListLocked(InputMethodInfo inputmethodinfo) {
            ArrayList arraylist;
label0:
            {
                List list = getEnabledInputMethodsAndSubtypeListLocked();
                arraylist = new ArrayList();
                if(inputmethodinfo == null)
                    break label0;
                Iterator iterator = list.iterator();
                Pair pair;
                InputMethodInfo inputmethodinfo1;
                do {
                    if(!iterator.hasNext())
                        break label0;
                    pair = (Pair)iterator.next();
                    inputmethodinfo1 = (InputMethodInfo)mMethodMap.get(pair.first);
                } while(inputmethodinfo1 == null || !inputmethodinfo1.getId().equals(inputmethodinfo.getId()));
                int i = inputmethodinfo1.getSubtypeCount();
label1:
                for(int j = 0; j < i; j++) {
                    InputMethodSubtype inputmethodsubtype = inputmethodinfo1.getSubtypeAt(j);
                    Iterator iterator1 = ((ArrayList)pair.second).iterator();
                    do {
                        if(!iterator1.hasNext())
                            continue label1;
                        String s = (String)iterator1.next();
                        if(String.valueOf(inputmethodsubtype.hashCode()).equals(s))
                            arraylist.add(inputmethodsubtype);
                    } while(true);
                }

            }
            return arraylist;
        }

        public Pair getLastInputMethodAndSubtypeLocked() {
            return getLastSubtypeForInputMethodLockedInternal(null);
        }

        public String getLastSubtypeForInputMethodLocked(String s) {
            Pair pair = getLastSubtypeForInputMethodLockedInternal(s);
            String s1;
            if(pair != null)
                s1 = (String)pair.second;
            else
                s1 = null;
            return s1;
        }

        public void putSelectedInputMethod(String s) {
            android.provider.Settings.Secure.putString(mResolver, "default_input_method", s);
        }

        public void putSelectedSubtype(int i) {
            android.provider.Settings.Secure.putInt(mResolver, "selected_input_method_subtype", i);
        }

        private static final char INPUT_METHOD_SEPARATER = 58;
        private static final char INPUT_METHOD_SUBTYPE_SEPARATER = 59;
        private String mEnabledInputMethodsStrCache;
        private final android.text.TextUtils.SimpleStringSplitter mInputMethodSplitter = new android.text.TextUtils.SimpleStringSplitter(':');
        private final ArrayList mMethodList;
        private final HashMap mMethodMap;
        private final Resources mRes;
        private final ContentResolver mResolver;
        private final android.text.TextUtils.SimpleStringSplitter mSubtypeSplitter = new android.text.TextUtils.SimpleStringSplitter(';');


        public InputMethodSettings(Resources resources, ContentResolver contentresolver, HashMap hashmap, ArrayList arraylist) {
            mRes = resources;
            mResolver = contentresolver;
            mMethodMap = hashmap;
            mMethodList = arraylist;
        }
    }

    private static class InputMethodAndSubtypeListManager {

        public ImeSubtypeListItem getNextInputMethod(boolean flag, InputMethodInfo inputmethodinfo, InputMethodSubtype inputmethodsubtype) {
            ImeSubtypeListItem imesubtypelistitem = null;
            if(inputmethodinfo != null) goto _L2; else goto _L1
_L1:
            return imesubtypelistitem;
_L2:
            List list = getSortedInputMethodAndSubtypeList();
            if(list.size() <= 1) goto _L1; else goto _L3
_L3:
            int i;
            int k;
            i = list.size();
            int j;
            ImeSubtypeListItem imesubtypelistitem1;
            if(inputmethodsubtype != null)
                j = InputMethodManagerService.getSubtypeIdFromHashCode(inputmethodinfo, inputmethodsubtype.hashCode());
            else
                j = -1;
            k = 0;
_L5:
            if(k < i) {
label0:
                {
                    imesubtypelistitem1 = (ImeSubtypeListItem)list.get(k);
                    if(!imesubtypelistitem1.mImi.equals(inputmethodinfo) || imesubtypelistitem1.mSubtypeId != j)
                        break MISSING_BLOCK_LABEL_188;
                    if(flag)
                        break label0;
                    imesubtypelistitem = (ImeSubtypeListItem)list.get((k + 1) % i);
                }
            }
              goto _L1
            int l = 0;
_L4:
            if(l < i - 1) {
label1:
                {
                    ImeSubtypeListItem imesubtypelistitem2 = (ImeSubtypeListItem)list.get((1 + (k + l)) % i);
                    if(!imesubtypelistitem2.mImi.equals(inputmethodinfo))
                        break label1;
                    imesubtypelistitem = imesubtypelistitem2;
                }
            }
              goto _L1
            l++;
              goto _L4
            k++;
              goto _L5
        }

        public List getSortedInputMethodAndSubtypeList() {
            return getSortedInputMethodAndSubtypeList(true, false, false);
        }

        public List getSortedInputMethodAndSubtypeList(boolean flag, boolean flag1, boolean flag2) {
            Object obj = new ArrayList();
            HashMap hashmap = mImms.getExplicitlyOrImplicitlyEnabledInputMethodsAndSubtypeListLocked();
            if(hashmap == null || hashmap.size() == 0) {
                obj = Collections.emptyList();
            } else {
                mSortedImmis.clear();
                mSortedImmis.putAll(hashmap);
                Iterator iterator = mSortedImmis.keySet().iterator();
                do {
                    if(!iterator.hasNext())
                        break;
                    InputMethodInfo inputmethodinfo = (InputMethodInfo)iterator.next();
                    if(inputmethodinfo != null) {
                        List list = (List)hashmap.get(inputmethodinfo);
                        HashSet hashset = new HashSet();
                        for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); hashset.add(String.valueOf(((InputMethodSubtype)iterator1.next()).hashCode())));
                        InputMethodManagerService.getSubtypes(inputmethodinfo);
                        CharSequence charsequence = inputmethodinfo.loadLabel(mPm);
                        if(flag && hashset.size() > 0) {
                            int i = inputmethodinfo.getSubtypeCount();
                            int j = 0;
                            while(j < i)  {
                                InputMethodSubtype inputmethodsubtype = inputmethodinfo.getSubtypeAt(j);
                                String s = String.valueOf(inputmethodsubtype.hashCode());
                                if(hashset.contains(s) && (flag1 && !flag2 || !inputmethodsubtype.isAuxiliary())) {
                                    CharSequence charsequence1;
                                    ImeSubtypeListItem imesubtypelistitem1;
                                    if(inputmethodsubtype.overridesImplicitlyEnabledSubtype())
                                        charsequence1 = null;
                                    else
                                        charsequence1 = inputmethodsubtype.getDisplayName(mContext, inputmethodinfo.getPackageName(), inputmethodinfo.getServiceInfo().applicationInfo);
                                    imesubtypelistitem1 = new ImeSubtypeListItem(charsequence, charsequence1, inputmethodinfo, j, inputmethodsubtype.getLocale(), mSystemLocaleStr);
                                    ((ArrayList) (obj)).add(imesubtypelistitem1);
                                    hashset.remove(s);
                                }
                                j++;
                            }
                        } else {
                            ImeSubtypeListItem imesubtypelistitem = new ImeSubtypeListItem(charsequence, null, inputmethodinfo, -1, null, mSystemLocaleStr);
                            ((ArrayList) (obj)).add(imesubtypelistitem);
                        }
                    }
                } while(true);
                Collections.sort(((List) (obj)));
            }
            return ((List) (obj));
        }

        private final Context mContext;
        private final InputMethodManagerService mImms;
        private final PackageManager mPm;
        private final TreeMap mSortedImmis = new TreeMap(new Comparator() {

            public int compare(InputMethodInfo inputmethodinfo, InputMethodInfo inputmethodinfo1) {
                int i;
                if(inputmethodinfo1 == null)
                    i = 0;
                else
                if(inputmethodinfo == null)
                    i = 1;
                else
                if(mPm == null) {
                    i = inputmethodinfo.getId().compareTo(inputmethodinfo1.getId());
                } else {
                    String s1 = (new StringBuilder()).append(inputmethodinfo.loadLabel(mPm)).append("/").append(inputmethodinfo.getId()).toString();
                    String s2 = (new StringBuilder()).append(inputmethodinfo1.loadLabel(mPm)).append("/").append(inputmethodinfo1.getId()).toString();
                    i = s1.toString().compareTo(s2.toString());
                }
                return i;
            }

            public volatile int compare(Object obj, Object obj1) {
                return compare((InputMethodInfo)obj, (InputMethodInfo)obj1);
            }

            final InputMethodAndSubtypeListManager this$0;

                 {
                    this$0 = InputMethodAndSubtypeListManager.this;
                    super();
                }
        });
        private final String mSystemLocaleStr;


        public InputMethodAndSubtypeListManager(Context context, InputMethodManagerService inputmethodmanagerservice) {
            mContext = context;
            mPm = context.getPackageManager();
            mImms = inputmethodmanagerservice;
            Locale locale = context.getResources().getConfiguration().locale;
            String s;
            if(locale != null)
                s = locale.toString();
            else
                s = "";
            mSystemLocaleStr = s;
        }
    }

    private static class ImeSubtypeListAdapter extends ArrayAdapter {

        public View getView(int i, View view, ViewGroup viewgroup) {
            boolean flag = false;
            View view1;
            if(view != null)
                view1 = view;
            else
                view1 = mInflater.inflate(mTextViewResourceId, null);
            if(i >= 0 && i < mItemsList.size()) {
                ImeSubtypeListItem imesubtypelistitem = (ImeSubtypeListItem)mItemsList.get(i);
                CharSequence charsequence = imesubtypelistitem.mImeName;
                CharSequence charsequence1 = imesubtypelistitem.mSubtypeName;
                TextView textview = (TextView)view1.findViewById(0x1020014);
                TextView textview1 = (TextView)view1.findViewById(0x1020015);
                RadioButton radiobutton;
                if(TextUtils.isEmpty(charsequence1)) {
                    textview.setText(charsequence);
                    textview1.setVisibility(8);
                } else {
                    textview.setText(charsequence1);
                    textview1.setText(charsequence);
                    textview1.setVisibility(0);
                }
                radiobutton = (RadioButton)view1.findViewById(0x10202d3);
                if(i == mCheckedItem)
                    flag = true;
                radiobutton.setChecked(flag);
            }
            return view1;
        }

        private final int mCheckedItem;
        private final LayoutInflater mInflater;
        private final List mItemsList;
        private final int mTextViewResourceId;

        public ImeSubtypeListAdapter(Context context, int i, List list, int j) {
            super(context, i, list);
            mTextViewResourceId = i;
            mItemsList = list;
            mCheckedItem = j;
            mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }
    }

    private static class ImeSubtypeListItem
        implements Comparable {

        public int compareTo(ImeSubtypeListItem imesubtypelistitem) {
            int i = 1;
            if(!TextUtils.isEmpty(mImeName)) goto _L2; else goto _L1
_L1:
            return i;
_L2:
            if(TextUtils.isEmpty(imesubtypelistitem.mImeName))
                i = -1;
            else
            if(!TextUtils.equals(mImeName, imesubtypelistitem.mImeName))
                i = mImeName.toString().compareTo(imesubtypelistitem.mImeName.toString());
            else
            if(TextUtils.equals(mSubtypeName, imesubtypelistitem.mSubtypeName))
                i = 0;
            else
            if(mIsSystemLocale)
                i = -1;
            else
            if(!imesubtypelistitem.mIsSystemLocale)
                if(mIsSystemLanguage)
                    i = -1;
                else
                if(!imesubtypelistitem.mIsSystemLanguage && !TextUtils.isEmpty(mSubtypeName))
                    if(TextUtils.isEmpty(imesubtypelistitem.mSubtypeName))
                        i = -1;
                    else
                        i = mSubtypeName.toString().compareTo(imesubtypelistitem.mSubtypeName.toString());
            if(true) goto _L1; else goto _L3
_L3:
        }

        public volatile int compareTo(Object obj) {
            return compareTo((ImeSubtypeListItem)obj);
        }

        public final CharSequence mImeName;
        public final InputMethodInfo mImi;
        private final boolean mIsSystemLanguage;
        private final boolean mIsSystemLocale;
        public final int mSubtypeId;
        public final CharSequence mSubtypeName;

        public ImeSubtypeListItem(CharSequence charsequence, CharSequence charsequence1, InputMethodInfo inputmethodinfo, int i, String s, String s1) {
            boolean flag = false;
            super();
            mImeName = charsequence;
            mSubtypeName = charsequence1;
            mImi = inputmethodinfo;
            mSubtypeId = i;
            if(TextUtils.isEmpty(s)) {
                mIsSystemLocale = false;
                mIsSystemLanguage = false;
            } else {
                mIsSystemLocale = s.equals(s1);
                if(mIsSystemLocale || s.startsWith(s1.substring(0, 2)))
                    flag = true;
                mIsSystemLanguage = flag;
            }
        }
    }

    private class HardKeyboardListener
        implements com.android.server.wm.WindowManagerService.OnHardKeyboardStatusChangeListener {

        public void handleHardKeyboardStatusChange(boolean flag, boolean flag1) {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            if(mSwitchingDialog != null && mSwitchingDialogTitleView != null && mSwitchingDialog.isShowing()) {
                View view = mSwitchingDialogTitleView.findViewById(0x1020290);
                int i;
                if(flag)
                    i = 0;
                else
                    i = 8;
                view.setVisibility(i);
            }
            return;
        }

        public void onHardKeyboardStatusChange(boolean flag, boolean flag1) {
            int i = 1;
            Handler handler = mHandler;
            Handler handler1 = mHandler;
            int j;
            if(flag)
                j = i;
            else
                j = 0;
            if(!flag1)
                i = 0;
            handler.sendMessage(handler1.obtainMessage(4000, j, i));
        }

        final InputMethodManagerService this$0;

        private HardKeyboardListener() {
            this$0 = InputMethodManagerService.this;
            super();
        }

    }

    private static class MethodCallback extends com.android.internal.view.IInputMethodCallback.Stub {

        public void finishedEvent(int i, boolean flag) throws RemoteException {
        }

        public void sessionCreated(IInputMethodSession iinputmethodsession) throws RemoteException {
            mParentIMMS.onSessionCreated(mMethod, iinputmethodsession);
        }

        private final IInputMethod mMethod;
        private final InputMethodManagerService mParentIMMS;

        MethodCallback(IInputMethod iinputmethod, InputMethodManagerService inputmethodmanagerservice) {
            mMethod = iinputmethod;
            mParentIMMS = inputmethodmanagerservice;
        }
    }

    class MyPackageMonitor extends PackageMonitor {

        public boolean onHandleForceStop(Intent intent, String as[], int i, boolean flag) {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            String s;
            int j;
            s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method");
            j = mMethodList.size();
            if(s == null) goto _L2; else goto _L1
_L1:
            int k = 0;
_L8:
            if(k >= j) goto _L2; else goto _L3
_L3:
            InputMethodInfo inputmethodinfo;
            int l;
            int i1;
            inputmethodinfo = (InputMethodInfo)mMethodList.get(k);
            if(!inputmethodinfo.getId().equals(s))
                continue; /* Loop/switch isn't completed */
            l = as.length;
            i1 = 0;
_L5:
            boolean flag1;
            if(i1 >= l)
                continue; /* Loop/switch isn't completed */
            String s1 = as[i1];
            if(inputmethodinfo.getPackageName().equals(s1)) {
                if(!flag) {
                    flag1 = true;
                    break; /* Loop/switch isn't completed */
                }
                resetSelectedInputMethodAndSubtypeLocked("");
                chooseNewDefaultIMELocked();
                flag1 = true;
                break; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_167;
            Exception exception;
            exception;
            throw exception;
            i1++;
            if(true) goto _L5; else goto _L4
_L4:
            if(false) goto _L7; else goto _L6
_L7:
            k++;
              goto _L8
_L2:
            hashmap;
            JVM INSTR monitorexit ;
            flag1 = false;
_L6:
            return flag1;
        }

        public void onSomePackagesChanged() {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            InputMethodInfo inputmethodinfo = null;
            String s;
            int i;
            s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method");
            i = mMethodList.size();
            if(s == null) goto _L2; else goto _L1
_L1:
            int k = 0;
_L11:
            if(k >= i) goto _L2; else goto _L3
_L3:
            InputMethodInfo inputmethodinfo1;
            int l;
            inputmethodinfo1 = (InputMethodInfo)mMethodList.get(k);
            String s1 = inputmethodinfo1.getId();
            if(s1.equals(s))
                inputmethodinfo = inputmethodinfo1;
            l = isPackageDisappearing(inputmethodinfo1.getPackageName());
            if(isPackageModified(inputmethodinfo1.getPackageName()))
                mFileManager.deleteAllInputMethodSubtypes(s1);
              goto _L4
_L9:
            Slog.i("InputMethodManagerService", (new StringBuilder()).append("Input method uninstalled, disabling: ").append(inputmethodinfo1.getComponent()).toString());
            setInputMethodEnabledLocked(inputmethodinfo1.getId(), false);
            break; /* Loop/switch isn't completed */
_L2:
            boolean flag;
            buildInputMethodListLocked(mMethodList, mMethodMap);
            flag = false;
            if(inputmethodinfo == null) goto _L6; else goto _L5
_L5:
            int j = isPackageDisappearing(inputmethodinfo.getPackageName());
            if(j != 2 && j != 3) goto _L6; else goto _L7
_L7:
            ServiceInfo serviceinfo = null;
            ServiceInfo serviceinfo1 = mContext.getPackageManager().getServiceInfo(inputmethodinfo.getComponent(), 0);
            serviceinfo = serviceinfo1;
_L8:
            if(serviceinfo == null) {
                Slog.i("InputMethodManagerService", (new StringBuilder()).append("Current input method removed: ").append(s).toString());
                setImeWindowVisibilityStatusHiddenLocked();
                if(!chooseNewDefaultIMELocked()) {
                    flag = true;
                    inputmethodinfo = null;
                    Slog.i("InputMethodManagerService", "Unsetting current input method");
                    resetSelectedInputMethodAndSubtypeLocked("");
                }
            }
_L6:
            if(inputmethodinfo == null)
                flag = chooseNewDefaultIMELocked();
            if(flag)
                updateFromSettingsLocked();
            return;
            Exception exception;
            exception;
            throw exception;
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            namenotfoundexception;
            if(true) goto _L8; else goto _L4
_L4:
            if(l != 2 && l != 3) goto _L10; else goto _L9
_L10:
            k++;
              goto _L11
        }

        final InputMethodManagerService this$0;

        MyPackageMonitor() {
            this$0 = InputMethodManagerService.this;
            super();
        }
    }

    class ScreenOnOffReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            boolean flag;
            flag = true;
            InputMethodManagerService inputmethodmanagerservice;
            IInputMethodClient iinputmethodclient;
            HandlerCaller handlercaller;
            if(intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                mScreenOn = flag;
                refreshImeWindowVisibilityLocked();
            } else {
label0:
                {
                    if(!intent.getAction().equals("android.intent.action.SCREEN_OFF"))
                        break label0;
                    mScreenOn = false;
                    setImeWindowVisibilityStatusHiddenLocked();
                }
            }
_L3:
            if(mCurClient != null && mCurClient.client != null) {
                inputmethodmanagerservice = InputMethodManagerService.this;
                iinputmethodclient = mCurClient.client;
                handlercaller = mCaller;
                if(!mScreenOn)
                    flag = false;
                inputmethodmanagerservice.executeOrSendMessage(iinputmethodclient, handlercaller.obtainMessageIO(3020, flag, mCurClient));
            }
_L2:
            return;
label1:
            {
                if(!intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS"))
                    break label1;
                hideInputMethodMenu();
            }
            if(true) goto _L2; else goto _L1
_L1:
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Unexpected intent ").append(intent).toString());
              goto _L3
        }

        final InputMethodManagerService this$0;

        ScreenOnOffReceiver() {
            this$0 = InputMethodManagerService.this;
            super();
        }
    }

    class SettingsObserver extends ContentObserver {

        public void onChange(boolean flag) {
            HashMap hashmap = mMethodMap;
            hashmap;
            JVM INSTR monitorenter ;
            updateFromSettingsLocked();
            return;
        }

        final InputMethodManagerService this$0;

        SettingsObserver(Handler handler) {
            this$0 = InputMethodManagerService.this;
            super(handler);
            ContentResolver contentresolver = mContext.getContentResolver();
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("default_input_method"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("enabled_input_methods"), false, this);
            contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("selected_input_method_subtype"), false, this);
        }
    }

    class ClientState {

        public String toString() {
            return (new StringBuilder()).append("ClientState{").append(Integer.toHexString(System.identityHashCode(this))).append(" uid ").append(uid).append(" pid ").append(pid).append("}").toString();
        }

        final InputBinding binding;
        final IInputMethodClient client;
        SessionState curSession;
        final IInputContext inputContext;
        final int pid;
        boolean sessionRequested;
        final InputMethodManagerService this$0;
        final int uid;

        ClientState(IInputMethodClient iinputmethodclient, IInputContext iinputcontext, int i, int j) {
            this$0 = InputMethodManagerService.this;
            super();
            client = iinputmethodclient;
            inputContext = iinputcontext;
            uid = i;
            pid = j;
            binding = new InputBinding(null, inputContext.asBinder(), uid, pid);
        }
    }

    class SessionState {

        public String toString() {
            return (new StringBuilder()).append("SessionState{uid ").append(client.uid).append(" pid ").append(client.pid).append(" method ").append(Integer.toHexString(System.identityHashCode(method))).append(" session ").append(Integer.toHexString(System.identityHashCode(session))).append("}").toString();
        }

        final ClientState client;
        final IInputMethod method;
        final IInputMethodSession session;
        final InputMethodManagerService this$0;

        SessionState(ClientState clientstate, IInputMethod iinputmethod, IInputMethodSession iinputmethodsession) {
            this$0 = InputMethodManagerService.this;
            super();
            client = clientstate;
            method = iinputmethod;
            session = iinputmethodsession;
        }
    }


    public InputMethodManagerService(Context context, WindowManagerService windowmanagerservice) {
        boolean flag = true;
        super();
        mNoBinding = new InputBindResult(null, null, -1);
        mMethodList = new ArrayList();
        mMethodMap = new HashMap();
        mSecureSuggestionSpans = new LruCache(20);
        mVisibleConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
            }

            public void onServiceDisconnected(ComponentName componentname) {
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        };
        mVisibleBound = false;
        mClients = new HashMap();
        mShortcutInputMethodsAndSubtypes = new HashMap();
        mScreenOn = flag;
        mBackDisposition = 0;
        mContext = context;
        mRes = context.getResources();
        mHandler = new Handler(this);
        mIWindowManager = android.view.IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        mCaller = new HandlerCaller(context, new com.android.internal.os.HandlerCaller.Callback() {

            public void executeMessage(Message message) {
                handleMessage(message);
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        });
        mWindowManagerService = windowmanagerservice;
        mHardKeyboardListener = new HardKeyboardListener();
        mImeSwitcherNotification = new Notification();
        mImeSwitcherNotification.icon = 0x108034f;
        mImeSwitcherNotification.when = 0L;
        mImeSwitcherNotification.flags = 2;
        mImeSwitcherNotification.tickerText = null;
        mImeSwitcherNotification.defaults = 0;
        mImeSwitcherNotification.sound = null;
        mImeSwitcherNotification.vibrate = null;
        Notification notification = mImeSwitcherNotification;
        String as[] = new String[flag];
        as[0] = "android.system.imeswitcher";
        notification.kind = as;
        Intent intent = new Intent("android.settings.SHOW_INPUT_METHOD_PICKER");
        mImeSwitchPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mShowOngoingImeSwitcherForPhones = false;
        synchronized(mMethodMap) {
            mFileManager = new InputMethodFileManager(mMethodMap);
        }
        mImListManager = new InputMethodAndSubtypeListManager(context, this);
        (new MyPackageMonitor()).register(mContext, null, flag);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.SCREEN_ON");
        intentfilter.addAction("android.intent.action.SCREEN_OFF");
        intentfilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        mContext.registerReceiver(new ScreenOnOffReceiver(), intentfilter);
        mNotificationShown = false;
        mSettings = new InputMethodSettings(mRes, context.getContentResolver(), mMethodMap, mMethodList);
        IntentFilter intentfilter1;
        if(TextUtils.isEmpty(android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method")))
            flag = false;
        mImeSelectedOnBoot = flag;
        buildInputMethodListLocked(mMethodList, mMethodMap);
        mSettings.enableAllIMEsIfThereIsNoEnabledIME();
        if(!mImeSelectedOnBoot) {
            Slog.w("InputMethodManagerService", "No IME selected. Choose the most applicable IME.");
            resetDefaultImeLocked(context);
        }
        mSettingsObserver = new SettingsObserver(mHandler);
        updateFromSettingsLocked();
        intentfilter1 = new IntentFilter();
        intentfilter1.addAction("android.intent.action.LOCALE_CHANGED");
        mContext.registerReceiver(new BroadcastReceiver() {

            public void onReceive(Context context1, Intent intent1) {
                HashMap hashmap1 = mMethodMap;
                hashmap1;
                JVM INSTR monitorenter ;
                checkCurrentLocaleChangedLocked();
                return;
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        }, intentfilter1);
        return;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void addShortcutInputMethodAndSubtypes(InputMethodInfo inputmethodinfo, InputMethodSubtype inputmethodsubtype) {
        if(mShortcutInputMethodsAndSubtypes.containsKey(inputmethodinfo)) {
            ((ArrayList)mShortcutInputMethodsAndSubtypes.get(inputmethodinfo)).add(inputmethodsubtype);
        } else {
            ArrayList arraylist = new ArrayList();
            arraylist.add(inputmethodsubtype);
            mShortcutInputMethodsAndSubtypes.put(inputmethodinfo, arraylist);
        }
    }

    private boolean canAddToLastInputMethod(InputMethodSubtype inputmethodsubtype) {
        boolean flag;
        flag = true;
        break MISSING_BLOCK_LABEL_2;
        if(inputmethodsubtype != null && inputmethodsubtype.isAuxiliary())
            flag = false;
        return flag;
    }

    private void checkCurrentLocaleChangedLocked() {
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Locale locale = mRes.getConfiguration().locale;
        if(locale != null && !locale.equals(mLastSystemLocale)) {
            buildInputMethodListLocked(mMethodList, mMethodMap);
            resetDefaultImeLocked(mContext);
            updateFromSettingsLocked();
            mLastSystemLocale = locale;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private boolean chooseNewDefaultIMELocked() {
        InputMethodInfo inputmethodinfo = getMostApplicableDefaultIMELocked();
        boolean flag;
        if(inputmethodinfo != null) {
            resetSelectedInputMethodAndSubtypeLocked(inputmethodinfo.getId());
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private static boolean containsSubtypeOf(InputMethodInfo inputmethodinfo, String s) {
        int i;
        int j;
        i = inputmethodinfo.getSubtypeCount();
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_39;
        if(!inputmethodinfo.getSubtypeAt(j).getLocale().startsWith(s)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private Pair findLastResortApplicableShortcutInputMethodAndSubtypeLocked(String s) {
        List list = mSettings.getEnabledInputMethodListLocked();
        InputMethodInfo inputmethodinfo = null;
        InputMethodSubtype inputmethodsubtype = null;
        boolean flag = false;
        Iterator iterator = list.iterator();
        do {
label0:
            {
                Pair pair;
                InputMethodInfo inputmethodinfo1;
                InputMethodSubtype inputmethodsubtype1;
                if(iterator.hasNext()) {
                    inputmethodinfo1 = (InputMethodInfo)iterator.next();
                    String s1 = inputmethodinfo1.getId();
                    if(flag && !s1.equals(mCurMethodId))
                        continue;
                    inputmethodsubtype1 = null;
                    List list1 = getEnabledInputMethodSubtypeList(inputmethodinfo1, true);
                    if(mCurrentSubtype != null)
                        inputmethodsubtype1 = findLastResortApplicableSubtypeLocked(mRes, list1, s, mCurrentSubtype.getLocale(), false);
                    if(inputmethodsubtype1 == null)
                        inputmethodsubtype1 = findLastResortApplicableSubtypeLocked(mRes, list1, s, null, true);
                    ArrayList arraylist = getOverridingImplicitlyEnabledSubtypes(inputmethodinfo1, s);
                    ArrayList arraylist1;
                    if(arraylist.isEmpty())
                        arraylist1 = getSubtypes(inputmethodinfo1);
                    else
                        arraylist1 = arraylist;
                    if(inputmethodsubtype1 == null && mCurrentSubtype != null)
                        inputmethodsubtype1 = findLastResortApplicableSubtypeLocked(mRes, arraylist1, s, mCurrentSubtype.getLocale(), false);
                    if(inputmethodsubtype1 == null)
                        inputmethodsubtype1 = findLastResortApplicableSubtypeLocked(mRes, arraylist1, s, null, true);
                    if(inputmethodsubtype1 == null)
                        continue;
                    if(!s1.equals(mCurMethodId))
                        break label0;
                    inputmethodinfo = inputmethodinfo1;
                    inputmethodsubtype = inputmethodsubtype1;
                }
                if(inputmethodinfo != null)
                    pair = new Pair(inputmethodinfo, inputmethodsubtype);
                else
                    pair = null;
                return pair;
            }
            if(!flag) {
                inputmethodinfo = inputmethodinfo1;
                inputmethodsubtype = inputmethodsubtype1;
                if((1 & inputmethodinfo1.getServiceInfo().applicationInfo.flags) != 0)
                    flag = true;
            }
        } while(true);
    }

    private static InputMethodSubtype findLastResortApplicableSubtypeLocked(Resources resources, List list, String s, String s1, boolean flag) {
        if(list != null && list.size() != 0) goto _L2; else goto _L1
_L1:
        InputMethodSubtype inputmethodsubtype = null;
_L4:
        return inputmethodsubtype;
_L2:
        String s2;
        boolean flag1;
        InputMethodSubtype inputmethodsubtype1;
        int i;
        int j;
        if(TextUtils.isEmpty(s1))
            s1 = resources.getConfiguration().locale.toString();
        s2 = s1.substring(0, 2);
        flag1 = false;
        inputmethodsubtype1 = null;
        inputmethodsubtype = null;
        i = list.size();
        j = 0;
_L5:
        InputMethodSubtype inputmethodsubtype2;
        String s3;
label0:
        {
            if(j < i) {
                inputmethodsubtype2 = (InputMethodSubtype)list.get(j);
                s3 = inputmethodsubtype2.getLocale();
                if(s != null && !((InputMethodSubtype)list.get(j)).getMode().equalsIgnoreCase(s))
                    break MISSING_BLOCK_LABEL_178;
                if(inputmethodsubtype == null)
                    inputmethodsubtype = inputmethodsubtype2;
                if(!s1.equals(s3))
                    break label0;
                inputmethodsubtype1 = inputmethodsubtype2;
            }
            if(inputmethodsubtype1 != null || !flag)
                inputmethodsubtype = inputmethodsubtype1;
        }
        if(true) goto _L4; else goto _L3
_L3:
        if(!flag1 && s3.startsWith(s2)) {
            inputmethodsubtype1 = inputmethodsubtype2;
            flag1 = true;
        }
        j++;
          goto _L5
    }

    private void finishSession(SessionState sessionstate) {
        if(sessionstate == null || sessionstate.session == null)
            break MISSING_BLOCK_LABEL_20;
        sessionstate.session.finishSession();
_L1:
        return;
        RemoteException remoteexception;
        remoteexception;
        Slog.w("InputMethodManagerService", "Session failed to close due to remote exception", remoteexception);
        setImeWindowVisibilityStatusHiddenLocked();
          goto _L1
    }

    private int getAppShowFlags() {
        int i = 0;
        if(!mShowForced) goto _L2; else goto _L1
_L1:
        i = 0 | 2;
_L4:
        return i;
_L2:
        if(!mShowExplicitlyRequested)
            i = false | true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private HashMap getExplicitlyOrImplicitlyEnabledInputMethodsAndSubtypeListLocked() {
        HashMap hashmap = new HashMap();
        InputMethodInfo inputmethodinfo;
        for(Iterator iterator = getEnabledInputMethodList().iterator(); iterator.hasNext(); hashmap.put(inputmethodinfo, getEnabledInputMethodSubtypeListLocked(inputmethodinfo, true)))
            inputmethodinfo = (InputMethodInfo)iterator.next();

        return hashmap;
    }

    private int getImeShowFlags() {
        int i = 0;
        if(!mShowForced) goto _L2; else goto _L1
_L1:
        i = 0 | 3;
_L4:
        return i;
_L2:
        if(mShowExplicitlyRequested)
            i = false | true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static ArrayList getImplicitlyApplicableSubtypesLocked(Resources resources, InputMethodInfo inputmethodinfo) {
        ArrayList arraylist;
        String s;
        arraylist = getSubtypes(inputmethodinfo);
        s = resources.getConfiguration().locale.toString();
        if(!TextUtils.isEmpty(s)) goto _L2; else goto _L1
_L1:
        ArrayList arraylist1 = new ArrayList();
_L4:
        return arraylist1;
_L2:
        HashMap hashmap = new HashMap();
        int i = arraylist.size();
        for(int j = 0; j < i; j++) {
            InputMethodSubtype inputmethodsubtype5 = (InputMethodSubtype)arraylist.get(j);
            if(!inputmethodsubtype5.overridesImplicitlyEnabledSubtype())
                continue;
            String s3 = inputmethodsubtype5.getMode();
            if(!hashmap.containsKey(s3))
                hashmap.put(s3, inputmethodsubtype5);
        }

        if(hashmap.size() > 0) {
            arraylist1 = new ArrayList(hashmap.values());
            continue; /* Loop/switch isn't completed */
        }
        int k = 0;
label0:
        do {
            InputMethodSubtype inputmethodsubtype3;
            String s1;
            String s2;
            InputMethodSubtype inputmethodsubtype4;
label1:
            {
                if(k >= i)
                    break label0;
                inputmethodsubtype3 = (InputMethodSubtype)arraylist.get(k);
                s1 = inputmethodsubtype3.getLocale();
                s2 = inputmethodsubtype3.getMode();
                if(!s.startsWith(s1))
                    break label1;
                inputmethodsubtype4 = (InputMethodSubtype)hashmap.get(s2);
            }
            if(inputmethodsubtype4 == null || !s.equals(inputmethodsubtype4.getLocale()) && s.equals(s1))
                hashmap.put(s2, inputmethodsubtype3);
            k++;
        } while(true);
        InputMethodSubtype inputmethodsubtype = (InputMethodSubtype)hashmap.get("keyboard");
        arraylist1 = new ArrayList(hashmap.values());
        if(inputmethodsubtype != null && !inputmethodsubtype.containsExtraValueKey("AsciiCapable")) {
            for(int l = 0; l < i; l++) {
                InputMethodSubtype inputmethodsubtype2 = (InputMethodSubtype)arraylist.get(l);
                if("keyboard".equals(inputmethodsubtype2.getMode()) && inputmethodsubtype2.containsExtraValueKey("EnabledWhenDefaultIsNotAsciiCapable"))
                    arraylist1.add(inputmethodsubtype2);
            }

        }
        if(inputmethodsubtype == null) {
            InputMethodSubtype inputmethodsubtype1 = findLastResortApplicableSubtypeLocked(resources, arraylist, "keyboard", s, true);
            if(inputmethodsubtype1 != null)
                arraylist1.add(inputmethodsubtype1);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private InputMethodInfo getMostApplicableDefaultIMELocked() {
        List list;
        int i;
        int j;
        list = mSettings.getEnabledInputMethodListLocked();
        if(list == null || list.size() <= 0)
            break MISSING_BLOCK_LABEL_110;
        i = list.size();
        j = -1;
_L5:
        if(i <= 0) goto _L2; else goto _L1
_L1:
        InputMethodInfo inputmethodinfo;
        i--;
        inputmethodinfo = (InputMethodInfo)list.get(i);
        if(!isSystemImeThatHasEnglishSubtype(inputmethodinfo) || inputmethodinfo.isAuxiliaryIme()) goto _L4; else goto _L3
_L3:
        return inputmethodinfo;
_L4:
        if(j < 0 && isSystemIme(inputmethodinfo) && !inputmethodinfo.isAuxiliaryIme())
            j = i;
          goto _L5
_L2:
        inputmethodinfo = (InputMethodInfo)list.get(Math.max(j, 0));
          goto _L3
        inputmethodinfo = null;
          goto _L3
    }

    private static ArrayList getOverridingImplicitlyEnabledSubtypes(InputMethodInfo inputmethodinfo, String s) {
        ArrayList arraylist = new ArrayList();
        int i = inputmethodinfo.getSubtypeCount();
        for(int j = 0; j < i; j++) {
            InputMethodSubtype inputmethodsubtype = inputmethodinfo.getSubtypeAt(j);
            if(inputmethodsubtype.overridesImplicitlyEnabledSubtype() && inputmethodsubtype.getMode().equals(s))
                arraylist.add(inputmethodsubtype);
        }

        return arraylist;
    }

    private int getSelectedInputMethodSubtypeId(String s) {
        int i;
        InputMethodInfo inputmethodinfo;
        i = -1;
        inputmethodinfo = (InputMethodInfo)mMethodMap.get(s);
        if(inputmethodinfo != null) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        int j = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "selected_input_method_subtype");
        i = getSubtypeIdFromHashCode(inputmethodinfo, j);
        continue; /* Loop/switch isn't completed */
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        settingnotfoundexception;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private static int getSubtypeIdFromHashCode(InputMethodInfo inputmethodinfo, int i) {
        int j;
        int k;
        if(inputmethodinfo == null)
            break MISSING_BLOCK_LABEL_36;
        k = inputmethodinfo.getSubtypeCount();
        j = 0;
_L3:
        if(j >= k)
            break MISSING_BLOCK_LABEL_36;
        if(i != inputmethodinfo.getSubtypeAt(j).hashCode()) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        j++;
          goto _L3
        j = -1;
          goto _L1
    }

    private static ArrayList getSubtypes(InputMethodInfo inputmethodinfo) {
        ArrayList arraylist = new ArrayList();
        int i = inputmethodinfo.getSubtypeCount();
        for(int j = 0; j < i; j++)
            arraylist.add(inputmethodinfo.getSubtypeAt(j));

        return arraylist;
    }

    private boolean isScreenLocked() {
        boolean flag;
        if(mKeyguardManager != null && mKeyguardManager.isKeyguardLocked() && mKeyguardManager.isKeyguardSecure())
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isSystemIme(InputMethodInfo inputmethodinfo) {
        boolean flag;
        if((1 & inputmethodinfo.getServiceInfo().applicationInfo.flags) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isSystemImeThatHasEnglishSubtype(InputMethodInfo inputmethodinfo) {
        boolean flag;
        if(!isSystemIme(inputmethodinfo))
            flag = false;
        else
            flag = containsSubtypeOf(inputmethodinfo, ENGLISH_LOCALE.getLanguage());
        return flag;
    }

    private static boolean isValidSubtypeId(InputMethodInfo inputmethodinfo, int i) {
        boolean flag;
        if(getSubtypeIdFromHashCode(inputmethodinfo, i) != -1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isValidSystemDefaultIme(InputMethodInfo inputmethodinfo, Context context) {
        boolean flag;
        flag = false;
        break MISSING_BLOCK_LABEL_2;
_L1:
        do
            return flag;
        while(!mSystemReady || !isSystemIme(inputmethodinfo));
        if(inputmethodinfo.getIsDefaultResourceId() == 0)
            break MISSING_BLOCK_LABEL_78;
        boolean flag1;
        if(!context.createPackageContext(inputmethodinfo.getPackageName(), 0).getResources().getBoolean(inputmethodinfo.getIsDefaultResourceId()))
            break MISSING_BLOCK_LABEL_78;
        flag1 = containsSubtypeOf(inputmethodinfo, context.getResources().getConfiguration().locale.getLanguage());
        if(!flag1)
            break MISSING_BLOCK_LABEL_78;
        flag = true;
          goto _L1
        android.content.res.Resources.NotFoundException notfoundexception;
        notfoundexception;
_L2:
        if(inputmethodinfo.getSubtypeCount() == 0)
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Found no subtypes in a system IME: ").append(inputmethodinfo.getPackageName()).toString());
          goto _L1
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
          goto _L2
    }

    private boolean needsToShowImeSwitchOngoingNotification() {
        boolean flag;
        if(!mShowOngoingImeSwitcherForPhones) {
            flag = false;
        } else {
label0:
            {
                if(!isScreenLocked())
                    break label0;
                flag = false;
            }
        }
_L1:
        return flag;
        hashmap;
        JVM INSTR monitorenter ;
        List list;
        int i;
        list = mSettings.getEnabledInputMethodListLocked();
        i = list.size();
        if(i <= 2)
            break MISSING_BLOCK_LABEL_68;
        flag = true;
          goto _L1
        Exception exception;
        exception;
        throw exception;
        if(i >= 1) goto _L3; else goto _L2
_L2:
        flag = false;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L17:
        int l;
        if(l >= i) goto _L5; else goto _L4
_L4:
        List list1;
        int i1;
        list1 = getEnabledInputMethodSubtypeListLocked((InputMethodInfo)list.get(l), true);
        i1 = list1.size();
        if(i1 != 0) goto _L7; else goto _L6
_L6:
        int j;
        j++;
          goto _L8
_L18:
        int j1;
        if(j1 >= i1) goto _L8; else goto _L9
_L9:
        InputMethodSubtype inputmethodsubtype2 = (InputMethodSubtype)list1.get(j1);
        if(inputmethodsubtype2.isAuxiliary()) goto _L11; else goto _L10
_L10:
        InputMethodSubtype inputmethodsubtype;
        j++;
        inputmethodsubtype = inputmethodsubtype2;
          goto _L12
_L19:
        flag = true;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L20:
        int k;
        if(j != 1 || k != 1) goto _L14; else goto _L13
_L13:
        InputMethodSubtype inputmethodsubtype1;
        if(inputmethodsubtype == null || inputmethodsubtype1 == null || !inputmethodsubtype.getLocale().equals(inputmethodsubtype1.getLocale()) && !inputmethodsubtype1.overridesImplicitlyEnabledSubtype() && !inputmethodsubtype.overridesImplicitlyEnabledSubtype() || !inputmethodsubtype.containsExtraValueKey("TrySuppressingImeSwitcher")) goto _L16; else goto _L15
_L15:
        flag = false;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L16:
        flag = true;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L14:
        flag = false;
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L3:
        j = 0;
        k = 0;
        inputmethodsubtype = null;
        inputmethodsubtype1 = null;
        l = 0;
          goto _L17
_L8:
        l++;
          goto _L17
_L7:
        j1 = 0;
          goto _L18
_L12:
        j1++;
          goto _L18
_L11:
        k++;
        inputmethodsubtype1 = inputmethodsubtype2;
          goto _L12
_L5:
        if(j <= 1 && k <= 1) goto _L20; else goto _L19
    }

    private void refreshImeWindowVisibilityLocked() {
        int i = 0;
        Configuration configuration = mRes.getConfiguration();
        boolean flag;
        boolean flag1;
        boolean flag2;
        if(configuration.keyboard != 1)
            flag = true;
        else
            flag = false;
        if(flag && configuration.hardKeyboardHidden != 2)
            flag1 = true;
        else
            flag1 = false;
        if(mKeyguardManager != null && mKeyguardManager.isKeyguardLocked() && mKeyguardManager.isKeyguardSecure())
            flag2 = true;
        else
            flag2 = false;
        if(!flag2 && (mInputShown || flag1))
            i = 3;
        mImeWindowVis = i;
        updateImeWindowStatusLocked();
    }

    private void resetDefaultImeLocked(Context context) {
        if(mCurMethodId == null || isSystemIme((InputMethodInfo)mMethodMap.get(mCurMethodId))) goto _L2; else goto _L1
_L1:
        return;
_L2:
        InputMethodInfo inputmethodinfo = null;
        Iterator iterator = mMethodList.iterator();
        do {
            if(!iterator.hasNext())
                break;
            InputMethodInfo inputmethodinfo1 = (InputMethodInfo)iterator.next();
            if(inputmethodinfo == null && isValidSystemDefaultIme(inputmethodinfo1, context)) {
                inputmethodinfo = inputmethodinfo1;
                Slog.i("InputMethodManagerService", (new StringBuilder()).append("Selected default: ").append(inputmethodinfo1.getId()).toString());
            }
        } while(true);
        if(inputmethodinfo == null && mMethodList.size() > 0) {
            inputmethodinfo = getMostApplicableDefaultIMELocked();
            Slog.i("InputMethodManagerService", (new StringBuilder()).append("No default found, using ").append(inputmethodinfo.getId()).toString());
        }
        if(inputmethodinfo != null)
            setSelectedInputMethodAndSubtypeLocked(inputmethodinfo, -1, false);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void resetSelectedInputMethodAndSubtypeLocked(String s) {
        InputMethodInfo inputmethodinfo;
        int i;
        String s1;
        inputmethodinfo = (InputMethodInfo)mMethodMap.get(s);
        i = -1;
        if(inputmethodinfo == null || TextUtils.isEmpty(s))
            break MISSING_BLOCK_LABEL_58;
        s1 = mSettings.getLastSubtypeForInputMethodLocked(s);
        if(s1 == null)
            break MISSING_BLOCK_LABEL_58;
        int j = getSubtypeIdFromHashCode(inputmethodinfo, Integer.valueOf(s1).intValue());
        i = j;
_L2:
        setSelectedInputMethodAndSubtypeLocked(inputmethodinfo, i, false);
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("HashCode for subtype looks broken: ").append(s1).toString(), numberformatexception);
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void saveCurrentInputMethodAndSubtypeToHistory() {
        String s = NOT_A_SUBTYPE_ID_STR;
        if(mCurrentSubtype != null)
            s = String.valueOf(mCurrentSubtype.hashCode());
        if(canAddToLastInputMethod(mCurrentSubtype))
            mSettings.addSubtypeToHistory(mCurMethodId, s);
    }

    private void setImeWindowVisibilityStatusHiddenLocked() {
        mImeWindowVis = 0;
        updateImeWindowStatusLocked();
    }

    private void setInputMethodWithSubtypeId(IBinder ibinder, String s, int i) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(ibinder == null) {
            if(mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0)
                throw new SecurityException("Using null token requires permission android.permission.WRITE_SECURE_SETTINGS");
            break MISSING_BLOCK_LABEL_97;
        }
        break MISSING_BLOCK_LABEL_45;
        Exception exception1;
        exception1;
        throw exception1;
        if(mCurToken == ibinder)
            break MISSING_BLOCK_LABEL_97;
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring setInputMethod of uid ").append(Binder.getCallingUid()).append(" token: ").append(ibinder).toString());
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_129;
        long l = Binder.clearCallingIdentity();
        setInputMethodLocked(s, i);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_129;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    private void setSelectedInputMethodAndSubtypeLocked(InputMethodInfo inputmethodinfo, int i, boolean flag) {
        saveCurrentInputMethodAndSubtypeToHistory();
        if(inputmethodinfo == null || i < 0) {
            mSettings.putSelectedSubtype(-1);
            mCurrentSubtype = null;
        } else
        if(i < inputmethodinfo.getSubtypeCount()) {
            InputMethodSubtype inputmethodsubtype = inputmethodinfo.getSubtypeAt(i);
            mSettings.putSelectedSubtype(inputmethodsubtype.hashCode());
            mCurrentSubtype = inputmethodsubtype;
        } else {
            mSettings.putSelectedSubtype(-1);
            mCurrentSubtype = getCurrentInputMethodSubtype();
        }
        if(mSystemReady && !flag) {
            InputMethodSettings inputmethodsettings = mSettings;
            String s;
            if(inputmethodinfo != null)
                s = inputmethodinfo.getId();
            else
                s = "";
            inputmethodsettings.putSelectedInputMethod(s);
        }
    }

    private void showConfigureInputMethods() {
        Intent intent = new Intent("android.settings.INPUT_METHOD_SETTINGS");
        intent.setFlags(0x14200000);
        mContext.startActivity(intent);
    }

    private void showInputMethodAndSubtypeEnabler(String s) {
        Intent intent = new Intent("android.settings.INPUT_METHOD_SUBTYPE_SETTINGS");
        intent.setFlags(0x14200000);
        if(!TextUtils.isEmpty(s))
            intent.putExtra("input_method_id", s);
        mContext.startActivity(intent);
    }

    private void showInputMethodMenu() {
        showInputMethodMenuInternal(false);
    }

    private void showInputMethodMenuInternal(boolean flag) {
        Context context;
        boolean flag1;
        String s;
        int i;
        context = mContext;
        context.getPackageManager();
        flag1 = isScreenLocked();
        s = android.provider.Settings.Secure.getString(context.getContentResolver(), "default_input_method");
        i = getSelectedInputMethodSubtypeId(s);
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        HashMap hashmap1 = getExplicitlyOrImplicitlyEnabledInputMethodsAndSubtypeListLocked();
        if(hashmap1 != null && hashmap1.size() != 0) goto _L2; else goto _L1
_L2:
        List list;
        int j;
        int k;
        int l;
        hideInputMethodMenuLocked();
        list = mImListManager.getSortedInputMethodAndSubtypeList(flag, mInputShown, flag1);
        if(i == -1) {
            InputMethodSubtype inputmethodsubtype = getCurrentInputMethodSubtype();
            if(inputmethodsubtype != null)
                i = getSubtypeIdFromHashCode((InputMethodInfo)mMethodMap.get(mCurMethodId), inputmethodsubtype.hashCode());
        }
        j = list.size();
        mIms = new InputMethodInfo[j];
        mSubtypeIds = new int[j];
        k = 0;
        l = 0;
_L6:
        if(l >= j) goto _L4; else goto _L3
_L4:
        TypedArray typedarray = context.obtainStyledAttributes(null, com.android.internal.R.styleable.DialogPreference, 0x101005d, 0);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        android.content.DialogInterface.OnCancelListener oncancellistener = new android.content.DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface) {
                hideInputMethodMenu();
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        };
        mDialogBuilder = builder.setOnCancelListener(oncancellistener).setIcon(typedarray.getDrawable(0));
        typedarray.recycle();
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x1090049, null);
        mDialogBuilder.setCustomTitle(view);
        mSwitchingDialogTitleView = view;
        View view1 = mSwitchingDialogTitleView.findViewById(0x1020290);
        int i1;
        Switch switch1;
        android.widget.CompoundButton.OnCheckedChangeListener oncheckedchangelistener;
        ImeSubtypeListAdapter imesubtypelistadapter;
        android.app.AlertDialog.Builder builder1;
        android.content.DialogInterface.OnClickListener onclicklistener;
        if(mWindowManagerService.isHardKeyboardAvailable())
            i1 = 0;
        else
            i1 = 8;
        view1.setVisibility(i1);
        switch1 = (Switch)mSwitchingDialogTitleView.findViewById(0x1020291);
        switch1.setChecked(mWindowManagerService.isHardKeyboardEnabled());
        oncheckedchangelistener = new android.widget.CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag2) {
                mWindowManagerService.setHardKeyboardEnabled(flag2);
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        };
        switch1.setOnCheckedChangeListener(oncheckedchangelistener);
        imesubtypelistadapter = new ImeSubtypeListAdapter(context, 0x10900ab, list, k);
        builder1 = mDialogBuilder;
        onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int k1) {
                HashMap hashmap2 = mMethodMap;
                hashmap2;
                JVM INSTR monitorenter ;
                if(mIms != null && mIms.length > k1 && mSubtypeIds != null && mSubtypeIds.length > k1) {
                    InputMethodInfo inputmethodinfo = mIms[k1];
                    int l1 = mSubtypeIds[k1];
                    hideInputMethodMenu();
                    if(inputmethodinfo != null) {
                        if(l1 < 0 || l1 >= inputmethodinfo.getSubtypeCount())
                            l1 = -1;
                        setInputMethodLocked(inputmethodinfo.getId(), l1);
                    }
                }
                return;
            }

            final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
        };
        builder1.setSingleChoiceItems(imesubtypelistadapter, k, onclicklistener);
        if(flag && !flag1) {
            android.app.AlertDialog.Builder builder2 = mDialogBuilder;
            android.content.DialogInterface.OnClickListener onclicklistener1 = new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int k1) {
                    showConfigureInputMethods();
                }

                final InputMethodManagerService this$0;

             {
                this$0 = InputMethodManagerService.this;
                super();
            }
            };
            builder2.setPositiveButton(0x1040448, onclicklistener1);
        }
        mSwitchingDialog = mDialogBuilder.create();
        mSwitchingDialog.setCanceledOnTouchOutside(true);
        mSwitchingDialog.getWindow().setType(2012);
        mSwitchingDialog.getWindow().getAttributes().setTitle("Select input method");
        mSwitchingDialog.show();
_L1:
        return;
_L3:
        ImeSubtypeListItem imesubtypelistitem = (ImeSubtypeListItem)list.get(l);
        mIms[l] = imesubtypelistitem.mImi;
        mSubtypeIds[l] = imesubtypelistitem.mSubtypeId;
        if(mIms[l].getId().equals(s)) {
            int j1 = mSubtypeIds[l];
            if(j1 == -1 || i == -1 && j1 == 0 || j1 == i)
                k = l;
        }
        l++;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private void showInputMethodSubtypeMenu() {
        showInputMethodMenuInternal(true);
    }

    private void updateImeWindowStatusLocked() {
        setImeWindowStatus(mCurToken, mImeWindowVis, mBackDisposition);
    }

    public void addClient(IInputMethodClient iinputmethodclient, IInputContext iinputcontext, int i, int j) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        mClients.put(iinputmethodclient.asBinder(), new ClientState(iinputmethodclient, iinputcontext, i, j));
        return;
    }

    InputBindResult attachNewInputLocked(boolean flag) {
        if(!mBoundToMethod) {
            executeOrSendMessage(mCurMethod, mCaller.obtainMessageOO(1010, mCurMethod, mCurClient.binding));
            mBoundToMethod = true;
        }
        SessionState sessionstate = mCurClient.curSession;
        if(flag)
            executeOrSendMessage(sessionstate.method, mCaller.obtainMessageOOO(2000, sessionstate, mCurInputContext, mCurAttribute));
        else
            executeOrSendMessage(sessionstate.method, mCaller.obtainMessageOOO(2010, sessionstate, mCurInputContext, mCurAttribute));
        if(mShowRequested)
            showCurrentInputLocked(getAppShowFlags(), null);
        return new InputBindResult(sessionstate.session, mCurId, mCurSeq);
    }

    void buildInputMethodListLocked(ArrayList arraylist, HashMap hashmap) {
        arraylist.clear();
        hashmap.clear();
        PackageManager packagemanager = mContext.getPackageManager();
        boolean flag;
        String s;
        List list;
        HashMap hashmap1;
        int i;
        if(mRes.getConfiguration().keyboard == 2)
            flag = true;
        else
            flag = false;
        s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "disabled_system_input_methods");
        if(s == null)
            s = "";
        list = packagemanager.queryIntentServices(new Intent("android.view.InputMethod"), 128);
        hashmap1 = mFileManager.getAllAdditionalInputMethodSubtypes();
        i = 0;
        while(i < list.size())  {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(i);
            ServiceInfo serviceinfo = resolveinfo.serviceInfo;
            ComponentName componentname = new ComponentName(((ComponentInfo) (serviceinfo)).packageName, ((ComponentInfo) (serviceinfo)).name);
            if(!"android.permission.BIND_INPUT_METHOD".equals(serviceinfo.permission))
                Slog.w("InputMethodManagerService", (new StringBuilder()).append("Skipping input method ").append(componentname).append(": it does not require the permission ").append("android.permission.BIND_INPUT_METHOD").toString());
            else
                try {
                    InputMethodInfo inputmethodinfo = new InputMethodInfo(mContext, resolveinfo, hashmap1);
                    arraylist.add(inputmethodinfo);
                    String s2 = inputmethodinfo.getId();
                    hashmap.put(s2, inputmethodinfo);
                    if((isValidSystemDefaultIme(inputmethodinfo, mContext) || isSystemImeThatHasEnglishSubtype(inputmethodinfo)) && (!flag || s.indexOf(s2) < 0))
                        setInputMethodEnabledLocked(s2, true);
                }
                catch(XmlPullParserException xmlpullparserexception) {
                    Slog.w("InputMethodManagerService", (new StringBuilder()).append("Unable to load input method ").append(componentname).toString(), xmlpullparserexception);
                }
                catch(IOException ioexception) {
                    Slog.w("InputMethodManagerService", (new StringBuilder()).append("Unable to load input method ").append(componentname).toString(), ioexception);
                }
            i++;
        }
        String s1 = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method");
        if(!TextUtils.isEmpty(s1))
            if(!hashmap.containsKey(s1)) {
                Slog.w("InputMethodManagerService", "Default IME is uninstalled. Choose new default IME.");
                if(chooseNewDefaultIMELocked())
                    updateFromSettingsLocked();
            } else {
                setInputMethodEnabledLocked(s1, true);
            }
    }

    void clearCurMethodLocked() {
        if(mCurMethod != null) {
            for(Iterator iterator = mClients.values().iterator(); iterator.hasNext();) {
                ClientState clientstate = (ClientState)iterator.next();
                clientstate.sessionRequested = false;
                finishSession(clientstate.curSession);
                clientstate.curSession = null;
            }

            finishSession(mEnabledSession);
            mEnabledSession = null;
            mCurMethod = null;
        }
        if(mStatusBar != null)
            mStatusBar.setIconVisibility("ime", false);
    }

    protected void dump(FileDescriptor filedescriptor, PrintWriter printwriter, String as[]) {
        if(mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) goto _L2; else goto _L1
_L1:
        printwriter.println((new StringBuilder()).append("Permission Denial: can't dump InputMethodManager from from pid=").append(Binder.getCallingPid()).append(", uid=").append(Binder.getCallingUid()).toString());
_L4:
        return;
_L2:
        PrintWriterPrinter printwriterprinter = new PrintWriterPrinter(printwriter);
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        printwriterprinter.println("Current Input Method Manager state:");
        int i = mMethodList.size();
        printwriterprinter.println("  Input Methods:");
        for(int j = 0; j < i; j++) {
            InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodList.get(j);
            printwriterprinter.println((new StringBuilder()).append("  InputMethod #").append(j).append(":").toString());
            inputmethodinfo.dump(printwriterprinter, "    ");
        }

        printwriterprinter.println("  Clients:");
        ClientState clientstate1;
        for(Iterator iterator = mClients.values().iterator(); iterator.hasNext(); printwriterprinter.println((new StringBuilder()).append("    curSession=").append(clientstate1.curSession).toString())) {
            clientstate1 = (ClientState)iterator.next();
            printwriterprinter.println((new StringBuilder()).append("  Client ").append(clientstate1).append(":").toString());
            printwriterprinter.println((new StringBuilder()).append("    client=").append(clientstate1.client).toString());
            printwriterprinter.println((new StringBuilder()).append("    inputContext=").append(clientstate1.inputContext).toString());
            printwriterprinter.println((new StringBuilder()).append("    sessionRequested=").append(clientstate1.sessionRequested).toString());
        }

        break MISSING_BLOCK_LABEL_389;
        Exception exception;
        exception;
        throw exception;
        ClientState clientstate;
        IInputMethod iinputmethod;
        printwriterprinter.println((new StringBuilder()).append("  mCurMethodId=").append(mCurMethodId).toString());
        clientstate = mCurClient;
        printwriterprinter.println((new StringBuilder()).append("  mCurClient=").append(clientstate).append(" mCurSeq=").append(mCurSeq).toString());
        printwriterprinter.println((new StringBuilder()).append("  mCurFocusedWindow=").append(mCurFocusedWindow).toString());
        printwriterprinter.println((new StringBuilder()).append("  mCurId=").append(mCurId).append(" mHaveConnect=").append(mHaveConnection).append(" mBoundToMethod=").append(mBoundToMethod).toString());
        printwriterprinter.println((new StringBuilder()).append("  mCurToken=").append(mCurToken).toString());
        printwriterprinter.println((new StringBuilder()).append("  mCurIntent=").append(mCurIntent).toString());
        iinputmethod = mCurMethod;
        printwriterprinter.println((new StringBuilder()).append("  mCurMethod=").append(mCurMethod).toString());
        printwriterprinter.println((new StringBuilder()).append("  mEnabledSession=").append(mEnabledSession).toString());
        printwriterprinter.println((new StringBuilder()).append("  mShowRequested=").append(mShowRequested).append(" mShowExplicitlyRequested=").append(mShowExplicitlyRequested).append(" mShowForced=").append(mShowForced).append(" mInputShown=").append(mInputShown).toString());
        printwriterprinter.println((new StringBuilder()).append("  mSystemReady=").append(mSystemReady).append(" mScreenOn=").append(mScreenOn).toString());
        hashmap;
        JVM INSTR monitorexit ;
        printwriterprinter.println(" ");
        if(clientstate != null) {
            printwriter.flush();
            RemoteException remoteexception;
            try {
                clientstate.client.asBinder().dump(filedescriptor, as);
            }
            catch(RemoteException remoteexception1) {
                printwriterprinter.println((new StringBuilder()).append("Input method client dead: ").append(remoteexception1).toString());
            }
        } else {
            printwriterprinter.println("No input method client.");
        }
        printwriterprinter.println(" ");
        if(iinputmethod != null) {
            printwriter.flush();
            try {
                iinputmethod.asBinder().dump(filedescriptor, as);
            }
            // Misplaced declaration of an exception variable
            catch(RemoteException remoteexception) {
                printwriterprinter.println((new StringBuilder()).append("Input method service dead: ").append(remoteexception).toString());
            }
        } else {
            printwriterprinter.println("No input method service.");
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    void executeOrSendMessage(IInterface iinterface, Message message) {
        if(iinterface.asBinder() instanceof Binder) {
            mCaller.sendMessage(message);
        } else {
            handleMessage(message);
            message.recycle();
        }
    }

    public void finishInput(IInputMethodClient iinputmethodclient) {
    }

    public InputMethodSubtype getCurrentInputMethodSubtype() {
        if(mCurMethodId != null) goto _L2; else goto _L1
_L1:
        InputMethodSubtype inputmethodsubtype = null;
_L8:
        return inputmethodsubtype;
_L2:
label0:
        {
            boolean flag = false;
            HashMap hashmap;
            Exception exception;
            InputMethodInfo inputmethodinfo;
            int i;
            List list;
            int j;
            try {
                j = android.provider.Settings.Secure.getInt(mContext.getContentResolver(), "selected_input_method_subtype");
            }
            catch(android.provider.Settings.SettingNotFoundException settingnotfoundexception) {
                break label0;
            }
            if(j != -1)
                flag = true;
            else
                flag = false;
        }
        hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
label1:
        {
            inputmethodinfo = (InputMethodInfo)mMethodMap.get(mCurMethodId);
            if(inputmethodinfo != null && inputmethodinfo.getSubtypeCount() != 0)
                break label1;
            inputmethodsubtype = null;
        }
        continue; /* Loop/switch isn't completed */
        if(flag && mCurrentSubtype != null && isValidSubtypeId(inputmethodinfo, mCurrentSubtype.hashCode())) goto _L4; else goto _L3
_L3:
        i = getSelectedInputMethodSubtypeId(mCurMethodId);
        if(i != -1)
            break MISSING_BLOCK_LABEL_233;
        list = getEnabledInputMethodSubtypeList(inputmethodinfo, true);
        if(list.size() != 1) goto _L6; else goto _L5
_L5:
        mCurrentSubtype = (InputMethodSubtype)list.get(0);
_L4:
        inputmethodsubtype = mCurrentSubtype;
        if(true) goto _L8; else goto _L7
_L7:
        exception;
        throw exception;
_L6:
        if(list.size() <= 1) goto _L4; else goto _L9
_L9:
        mCurrentSubtype = findLastResortApplicableSubtypeLocked(mRes, list, "keyboard", null, true);
        if(mCurrentSubtype == null)
            mCurrentSubtype = findLastResortApplicableSubtypeLocked(mRes, list, null, null, true);
          goto _L4
        mCurrentSubtype = (InputMethodSubtype)getSubtypes(inputmethodinfo).get(i);
          goto _L4
    }

    public List getEnabledInputMethodList() {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        List list = mSettings.getEnabledInputMethodListLocked();
        return list;
    }

    public List getEnabledInputMethodSubtypeList(InputMethodInfo inputmethodinfo, boolean flag) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        List list = getEnabledInputMethodSubtypeListLocked(inputmethodinfo, flag);
        return list;
    }

    public List getEnabledInputMethodSubtypeListLocked(InputMethodInfo inputmethodinfo, boolean flag) {
        if(inputmethodinfo == null && mCurMethodId != null)
            inputmethodinfo = (InputMethodInfo)mMethodMap.get(mCurMethodId);
        Object obj = mSettings.getEnabledInputMethodSubtypeListLocked(inputmethodinfo);
        if(flag && ((List) (obj)).isEmpty())
            obj = getImplicitlyApplicableSubtypesLocked(mRes, inputmethodinfo);
        return InputMethodSubtype.sort(mContext, 0, inputmethodinfo, ((List) (obj)));
    }

    public List getInputMethodList() {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList(mMethodList);
        return arraylist;
    }

    public InputMethodSubtype getLastInputMethodSubtype() {
        Pair pair;
        InputMethodSubtype inputmethodsubtype;
        InputMethodInfo inputmethodinfo;
label0:
        {
            synchronized(mMethodMap) {
                pair = mSettings.getLastInputMethodAndSubtypeLocked();
                if(pair == null || TextUtils.isEmpty((CharSequence)pair.first) || TextUtils.isEmpty((CharSequence)pair.second)) {
                    inputmethodsubtype = null;
                    break MISSING_BLOCK_LABEL_159;
                }
                inputmethodinfo = (InputMethodInfo)mMethodMap.get(pair.first);
                if(inputmethodinfo != null)
                    break label0;
            }
            inputmethodsubtype = null;
            break MISSING_BLOCK_LABEL_159;
        }
        int i;
        int j;
        i = getSubtypeIdFromHashCode(inputmethodinfo, Integer.valueOf((String)pair.second).intValue());
        if(i < 0)
            break MISSING_BLOCK_LABEL_121;
        j = inputmethodinfo.getSubtypeCount();
        if(i < j)
            break MISSING_BLOCK_LABEL_129;
        hashmap;
        JVM INSTR monitorexit ;
        inputmethodsubtype = null;
        break MISSING_BLOCK_LABEL_159;
        InputMethodSubtype inputmethodsubtype1 = inputmethodinfo.getSubtypeAt(i);
        inputmethodsubtype = inputmethodsubtype1;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_159;
        exception;
        throw exception;
        NumberFormatException numberformatexception;
        numberformatexception;
        hashmap;
        JVM INSTR monitorexit ;
        inputmethodsubtype = null;
        return inputmethodsubtype;
    }

    public List getShortcutInputMethodsAndSubtypes() {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        arraylist = new ArrayList();
        if(mShortcutInputMethodsAndSubtypes.size() == 0) {
            Pair pair = findLastResortApplicableShortcutInputMethodAndSubtypeLocked("voice");
            if(pair != null) {
                arraylist.add(pair.first);
                arraylist.add(pair.second);
            }
        } else {
            for(Iterator iterator = mShortcutInputMethodsAndSubtypes.keySet().iterator(); iterator.hasNext();) {
                InputMethodInfo inputmethodinfo = (InputMethodInfo)iterator.next();
                arraylist.add(inputmethodinfo);
                Iterator iterator1 = ((ArrayList)mShortcutInputMethodsAndSubtypes.get(inputmethodinfo)).iterator();
                while(iterator1.hasNext()) 
                    arraylist.add((InputMethodSubtype)iterator1.next());
            }

            break MISSING_BLOCK_LABEL_156;
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
        hashmap;
        JVM INSTR monitorexit ;
_L1:
        return arraylist;
    }

    public boolean handleMessage(Message message) {
        boolean flag;
        boolean flag1;
        flag = false;
        flag1 = true;
        message.what;
        JVM INSTR lookupswitch 16: default 148
    //                   1: 152
    //                   2: 159
    //                   3: 166
    //                   4: 186
    //                   1000: 193
    //                   1010: 213
    //                   1020: 251
    //                   1030: 293
    //                   1040: 332
    //                   1050: 370
    //                   2000: 408
    //                   2010: 467
    //                   3000: 526
    //                   3010: 550
    //                   3020: 618
    //                   4000: 709;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17
_L1:
        flag1 = false;
_L19:
        return flag1;
_L2:
        showInputMethodMenu();
        continue; /* Loop/switch isn't completed */
_L3:
        showInputMethodSubtypeMenu();
        continue; /* Loop/switch isn't completed */
_L4:
        showInputMethodAndSubtypeEnabler((String)((com.android.internal.os.HandlerCaller.SomeArgs)message.obj).arg1);
        continue; /* Loop/switch isn't completed */
_L5:
        showConfigureInputMethods();
        continue; /* Loop/switch isn't completed */
_L6:
        try {
            ((IInputMethod)message.obj).unbindInput();
        }
        catch(RemoteException remoteexception10) { }
        continue; /* Loop/switch isn't completed */
_L7:
        com.android.internal.os.HandlerCaller.SomeArgs someargs7 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethod)someargs7.arg1).bindInput((InputBinding)someargs7.arg2);
        }
        catch(RemoteException remoteexception9) { }
        continue; /* Loop/switch isn't completed */
_L8:
        com.android.internal.os.HandlerCaller.SomeArgs someargs6 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethod)someargs6.arg1).showSoftInput(message.arg1, (ResultReceiver)someargs6.arg2);
        }
        catch(RemoteException remoteexception8) { }
        continue; /* Loop/switch isn't completed */
_L9:
        com.android.internal.os.HandlerCaller.SomeArgs someargs5 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethod)someargs5.arg1).hideSoftInput(0, (ResultReceiver)someargs5.arg2);
        }
        catch(RemoteException remoteexception7) { }
        continue; /* Loop/switch isn't completed */
_L10:
        com.android.internal.os.HandlerCaller.SomeArgs someargs4 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethod)someargs4.arg1).attachToken((IBinder)someargs4.arg2);
        }
        catch(RemoteException remoteexception6) { }
        continue; /* Loop/switch isn't completed */
_L11:
        com.android.internal.os.HandlerCaller.SomeArgs someargs3 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethod)someargs3.arg1).createSession((IInputMethodCallback)someargs3.arg2);
        }
        catch(RemoteException remoteexception5) { }
        continue; /* Loop/switch isn't completed */
_L12:
        com.android.internal.os.HandlerCaller.SomeArgs someargs2 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            SessionState sessionstate1 = (SessionState)someargs2.arg1;
            setEnabledSessionInMainThread(sessionstate1);
            sessionstate1.method.startInput((IInputContext)someargs2.arg2, (EditorInfo)someargs2.arg3);
        }
        catch(RemoteException remoteexception4) { }
        continue; /* Loop/switch isn't completed */
_L13:
        com.android.internal.os.HandlerCaller.SomeArgs someargs1 = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            SessionState sessionstate = (SessionState)someargs1.arg1;
            setEnabledSessionInMainThread(sessionstate);
            sessionstate.method.restartInput((IInputContext)someargs1.arg2, (EditorInfo)someargs1.arg3);
        }
        catch(RemoteException remoteexception3) { }
        continue; /* Loop/switch isn't completed */
_L14:
        try {
            ((IInputMethodClient)message.obj).onUnbindMethod(message.arg1);
        }
        catch(RemoteException remoteexception2) { }
        continue; /* Loop/switch isn't completed */
_L15:
        com.android.internal.os.HandlerCaller.SomeArgs someargs = (com.android.internal.os.HandlerCaller.SomeArgs)message.obj;
        try {
            ((IInputMethodClient)someargs.arg1).onBindMethod((InputBindResult)someargs.arg2);
        }
        catch(RemoteException remoteexception1) {
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Client died receiving input method ").append(someargs.arg2).toString());
        }
        continue; /* Loop/switch isn't completed */
_L16:
        try {
            IInputMethodClient iinputmethodclient = ((ClientState)message.obj).client;
            if(message.arg1 != 0)
                flag = flag1;
            iinputmethodclient.setActive(flag);
        }
        catch(RemoteException remoteexception) {
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Got RemoteException sending setActive(false) notification to pid ").append(((ClientState)message.obj).pid).append(" uid ").append(((ClientState)message.obj).uid).toString());
        }
        continue; /* Loop/switch isn't completed */
_L17:
        HardKeyboardListener hardkeyboardlistener = mHardKeyboardListener;
        boolean flag2;
        if(message.arg1 == flag1)
            flag2 = flag1;
        else
            flag2 = false;
        if(message.arg2 == flag1)
            flag = flag1;
        hardkeyboardlistener.handleHardKeyboardStatusChange(flag2, flag);
        if(true) goto _L19; else goto _L18
_L18:
    }

    boolean hideCurrentInputLocked(int i, ResultReceiver resultreceiver) {
        boolean flag;
        if((i & 1) != 0 && (mShowExplicitlyRequested || mShowForced))
            flag = false;
        else
        if(mShowForced && (i & 2) != 0) {
            flag = false;
        } else {
            if(mInputShown && mCurMethod != null) {
                executeOrSendMessage(mCurMethod, mCaller.obtainMessageOO(1030, mCurMethod, resultreceiver));
                flag = true;
            } else {
                flag = false;
            }
            if(mHaveConnection && mVisibleBound) {
                mContext.unbindService(mVisibleConnection);
                mVisibleBound = false;
            }
            mInputShown = false;
            mShowRequested = false;
            mShowExplicitlyRequested = false;
            mShowForced = false;
        }
        return flag;
    }

    void hideInputMethodMenu() {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        hideInputMethodMenuLocked();
        return;
    }

    void hideInputMethodMenuLocked() {
        if(mSwitchingDialog != null) {
            mSwitchingDialog.dismiss();
            mSwitchingDialog = null;
        }
        mDialogBuilder = null;
        mIms = null;
    }

    public void hideMySoftInput(IBinder ibinder, int i) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(ibinder == null)
            break MISSING_BLOCK_LABEL_19;
        if(mCurToken == ibinder)
            break MISSING_BLOCK_LABEL_24;
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_63;
        long l = Binder.clearCallingIdentity();
        hideCurrentInputLocked(i, null);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_63;
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    public boolean hideSoftInput(IInputMethodClient iinputmethodclient, int i, ResultReceiver resultreceiver) {
        boolean flag;
        long l;
        flag = false;
        Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurClient == null || iinputmethodclient == null) goto _L2; else goto _L1
_L1:
        IBinder ibinder;
        IBinder ibinder1;
        ibinder = mCurClient.client.asBinder();
        ibinder1 = iinputmethodclient.asBinder();
        if(ibinder == ibinder1) goto _L3; else goto _L2
_L2:
        if(mIWindowManager.inputMethodClientHasFocus(iinputmethodclient)) goto _L3; else goto _L4
_L4:
        setImeWindowVisibilityStatusHiddenLocked();
        hashmap;
        JVM INSTR monitorexit ;
_L5:
        Binder.restoreCallingIdentity(l);
        return flag;
        RemoteException remoteexception;
        remoteexception;
        setImeWindowVisibilityStatusHiddenLocked();
          goto _L5
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L3:
        flag = hideCurrentInputLocked(i, resultreceiver);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
    }

    public boolean notifySuggestionPicked(SuggestionSpan suggestionspan, String s, int i) {
        boolean flag = false;
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        InputMethodInfo inputmethodinfo = (InputMethodInfo)mSecureSuggestionSpans.get(suggestionspan);
        if(inputmethodinfo == null) goto _L2; else goto _L1
_L1:
        String as[] = suggestionspan.getSuggestions();
        if(i >= 0 && i < as.length) {
            String s1 = suggestionspan.getNotificationTargetClassName();
            Intent intent = new Intent();
            intent.setClassName(inputmethodinfo.getPackageName(), s1);
            intent.setAction("android.text.style.SUGGESTION_PICKED");
            intent.putExtra("before", s);
            intent.putExtra("after", as[i]);
            intent.putExtra("hashcode", suggestionspan.hashCode());
            mContext.sendBroadcast(intent);
            flag = true;
        }
          goto _L3
        Exception exception;
        exception;
        throw exception;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
_L3:
        return flag;
    }

    public void onServiceConnected(ComponentName componentname, IBinder ibinder) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurIntent == null || !componentname.equals(mCurIntent.getComponent())) goto _L2; else goto _L1
_L1:
        mCurMethod = com.android.internal.view.IInputMethod.Stub.asInterface(ibinder);
        if(mCurToken != null) goto _L4; else goto _L3
_L3:
        Slog.w("InputMethodManagerService", "Service connected without a token!");
        unbindCurrentMethodLocked(false);
          goto _L5
_L4:
        executeOrSendMessage(mCurMethod, mCaller.obtainMessageOO(1040, mCurMethod, mCurToken));
        if(mCurClient != null)
            executeOrSendMessage(mCurMethod, mCaller.obtainMessageOO(1050, mCurMethod, new MethodCallback(mCurMethod, this)));
_L2:
        hashmap;
        JVM INSTR monitorexit ;
_L5:
    }

    public void onServiceDisconnected(ComponentName componentname) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurMethod != null && mCurIntent != null && componentname.equals(mCurIntent.getComponent())) {
            clearCurMethodLocked();
            mLastBindTime = SystemClock.uptimeMillis();
            mShowRequested = mInputShown;
            mInputShown = false;
            if(mCurClient != null)
                executeOrSendMessage(mCurClient.client, mCaller.obtainMessageIO(3000, mCurSeq, mCurClient.client));
        }
        return;
    }

    void onSessionCreated(IInputMethod iinputmethod, IInputMethodSession iinputmethodsession) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurMethod != null && iinputmethod != null && mCurMethod.asBinder() == iinputmethod.asBinder() && mCurClient != null) {
            mCurClient.curSession = new SessionState(mCurClient, iinputmethod, iinputmethodsession);
            mCurClient.sessionRequested = false;
            InputBindResult inputbindresult = attachNewInputLocked(true);
            if(inputbindresult.method != null)
                executeOrSendMessage(mCurClient.client, mCaller.obtainMessageOO(3010, mCurClient.client, inputbindresult));
        }
        return;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j) throws RemoteException {
        boolean flag;
        try {
            flag = super.onTransact(i, parcel, parcel1, j);
        }
        catch(RuntimeException runtimeexception) {
            if(!(runtimeexception instanceof SecurityException))
                Slog.e("InputMethodManagerService", "Input Method Manager Crash", runtimeexception);
            throw runtimeexception;
        }
        return flag;
    }

    public void registerSuggestionSpansForNotification(SuggestionSpan asuggestionspan[]) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(mCurMethodId);
        int i = 0;
        do {
            if(i < asuggestionspan.length) {
                SuggestionSpan suggestionspan = asuggestionspan[i];
                if(!TextUtils.isEmpty(suggestionspan.getNotificationTargetClassName())) {
                    mSecureSuggestionSpans.put(suggestionspan, inputmethodinfo);
                    (InputMethodInfo)mSecureSuggestionSpans.get(suggestionspan);
                }
            } else {
                return;
            }
            i++;
        } while(true);
    }

    public void removeClient(IInputMethodClient iinputmethodclient) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        mClients.remove(iinputmethodclient.asBinder());
        return;
    }

    public void setAdditionalInputMethodSubtypes(String s, InputMethodSubtype ainputmethodsubtype[]) {
        if(!TextUtils.isEmpty(s) && ainputmethodsubtype != null && ainputmethodsubtype.length != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(s);
        if(inputmethodinfo != null) goto _L3; else goto _L1
_L3:
        break MISSING_BLOCK_LABEL_54;
        Exception exception;
        exception;
        throw exception;
        String as[] = mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if(as == null) goto _L5; else goto _L4
_L4:
        int i;
        int j;
        i = as.length;
        j = 0;
_L9:
        if(j >= i) goto _L5; else goto _L6
_L6:
        if(!as[j].equals(inputmethodinfo.getPackageName())) goto _L8; else goto _L7
_L7:
        long l;
        mFileManager.addInputMethodSubtypes(inputmethodinfo, ainputmethodsubtype);
        l = Binder.clearCallingIdentity();
        buildInputMethodListLocked(mMethodList, mMethodMap);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
_L5:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L1
_L8:
        j++;
          goto _L9
    }

    public boolean setCurrentInputMethodSubtype(InputMethodSubtype inputmethodsubtype) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        boolean flag;
label0:
        {
            if(inputmethodsubtype != null && mCurMethodId != null) {
                int i = getSubtypeIdFromHashCode((InputMethodInfo)mMethodMap.get(mCurMethodId), inputmethodsubtype.hashCode());
                if(i != -1) {
                    setInputMethodLocked(mCurMethodId, i);
                    flag = true;
                    break label0;
                }
            }
            flag = false;
        }
        return flag;
    }

    void setEnabledSessionInMainThread(SessionState sessionstate) {
        if(mEnabledSession == sessionstate)
            break MISSING_BLOCK_LABEL_54;
        RemoteException remoteexception;
        if(mEnabledSession != null)
            try {
                mEnabledSession.method.setSessionEnabled(mEnabledSession.session, false);
            }
            catch(RemoteException remoteexception1) { }
        mEnabledSession = sessionstate;
        sessionstate.method.setSessionEnabled(sessionstate.session, true);
_L2:
        return;
        remoteexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void setImeWindowStatus(IBinder ibinder, int i, int j) {
        int k;
        long l;
        k = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        if(ibinder == null) goto _L2; else goto _L1
_L1:
        if(mCurToken == ibinder) goto _L3; else goto _L2
_L2:
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring setImeWindowStatus of uid ").append(k).append(" token: ").append(ibinder).toString());
_L9:
        Binder.restoreCallingIdentity(l);
        return;
_L3:
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        mImeWindowVis = i;
        mBackDisposition = j;
        if(mStatusBar != null)
            mStatusBar.setImeWindowStatus(ibinder, i, j);
          goto _L4
_L10:
        InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(mCurMethodId);
        boolean flag;
        if(inputmethodinfo == null || !flag || !needsToShowImeSwitchOngoingNotification()) goto _L6; else goto _L5
_L5:
        CharSequence charsequence;
        CharSequence charsequence1;
        CharSequence acharsequence[];
        PackageManager packagemanager = mContext.getPackageManager();
        charsequence = mRes.getText(0x1040447);
        charsequence1 = inputmethodinfo.loadLabel(packagemanager);
        if(mCurrentSubtype == null)
            break MISSING_BLOCK_LABEL_379;
        acharsequence = new CharSequence[2];
        acharsequence[0] = mCurrentSubtype.getDisplayName(mContext, inputmethodinfo.getPackageName(), inputmethodinfo.getServiceInfo().applicationInfo);
        if(!TextUtils.isEmpty(charsequence1)) goto _L8; else goto _L7
_L7:
        String s = "";
_L11:
        CharSequence charsequence2;
        acharsequence[1] = s;
        charsequence2 = TextUtils.concat(acharsequence);
_L15:
        mImeSwitcherNotification.setLatestEventInfo(mContext, charsequence, charsequence2, mImeSwitchPendingIntent);
        if(mNotificationManager != null) {
            mNotificationManager.notify(0x1040447, mImeSwitcherNotification);
            mNotificationShown = true;
        }
_L12:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L9
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L14:
        flag = false;
          goto _L10
_L8:
        s = (new StringBuilder()).append(" - ").append(charsequence1).toString();
          goto _L11
_L6:
        if(mNotificationShown && mNotificationManager != null) {
            mNotificationManager.cancel(0x1040447);
            mNotificationShown = false;
        }
          goto _L12
_L4:
        if((i & 1) == 0) goto _L14; else goto _L13
_L13:
        flag = true;
          goto _L10
        charsequence2 = charsequence1;
          goto _L15
    }

    public void setInputMethod(IBinder ibinder, String s) {
        setInputMethodWithSubtypeId(ibinder, s, -1);
    }

    public void setInputMethodAndSubtype(IBinder ibinder, String s, InputMethodSubtype inputmethodsubtype) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(inputmethodsubtype != null)
            setInputMethodWithSubtypeId(ibinder, s, getSubtypeIdFromHashCode((InputMethodInfo)mMethodMap.get(s), inputmethodsubtype.hashCode()));
        else
            setInputMethod(ibinder, s);
        return;
    }

    public boolean setInputMethodEnabled(String s, boolean flag) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0)
            throw new SecurityException("Requires permission android.permission.WRITE_SECURE_SETTINGS");
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        throw exception;
        long l = Binder.clearCallingIdentity();
        boolean flag1 = setInputMethodEnabledLocked(s, flag);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        return flag1;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    boolean setInputMethodEnabledLocked(String s, boolean flag) {
        List list;
        if((InputMethodInfo)mMethodMap.get(s) == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown id: ").append(mCurMethodId).toString());
        list = mSettings.getEnabledInputMethodsAndSubtypeListLocked();
        if(!flag) goto _L2; else goto _L1
_L1:
        Iterator iterator = list.iterator();
_L6:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        if(!((String)((Pair)iterator.next()).first).equals(s)) goto _L6; else goto _L5
_L5:
        boolean flag1 = true;
_L8:
        return flag1;
_L4:
        mSettings.appendAndPutEnabledInputMethodLocked(s, false);
        flag1 = false;
        continue; /* Loop/switch isn't completed */
_L2:
        StringBuilder stringbuilder = new StringBuilder();
        if(mSettings.buildAndPutEnabledInputMethodsStrRemovingIdLocked(stringbuilder, list, s)) {
            if(s.equals(android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method")) && !chooseNewDefaultIMELocked()) {
                Slog.i("InputMethodManagerService", "Can't find new IME, unsetting the current input method.");
                resetSelectedInputMethodAndSubtypeLocked("");
            }
            flag1 = true;
        } else {
            flag1 = false;
        }
        if(true) goto _L8; else goto _L7
_L7:
    }

    void setInputMethodLocked(String s, int i) {
        InputMethodInfo inputmethodinfo;
        inputmethodinfo = (InputMethodInfo)mMethodMap.get(s);
        if(inputmethodinfo == null)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown id: ").append(s).toString());
        if(!s.equals(mCurMethodId)) goto _L2; else goto _L1
_L1:
        int j = inputmethodinfo.getSubtypeCount();
        if(j > 0) {
            InputMethodSubtype inputmethodsubtype = mCurrentSubtype;
            InputMethodSubtype inputmethodsubtype1;
            if(i >= 0 && i < j)
                inputmethodsubtype1 = inputmethodinfo.getSubtypeAt(i);
            else
                inputmethodsubtype1 = getCurrentInputMethodSubtype();
            if(inputmethodsubtype1 == null || inputmethodsubtype == null)
                Slog.w("InputMethodManagerService", (new StringBuilder()).append("Illegal subtype state: old subtype = ").append(inputmethodsubtype).append(", new subtype = ").append(inputmethodsubtype1).toString());
            else
            if(inputmethodsubtype1 != inputmethodsubtype) {
                setSelectedInputMethodAndSubtypeLocked(inputmethodinfo, i, true);
                if(mCurMethod != null)
                    try {
                        refreshImeWindowVisibilityLocked();
                        mCurMethod.changeInputMethodSubtype(inputmethodsubtype1);
                    }
                    catch(RemoteException remoteexception) {
                        Slog.w("InputMethodManagerService", "Failed to call changeInputMethodSubtype");
                    }
            }
        }
_L4:
        return;
_L2:
        long l = Binder.clearCallingIdentity();
        setSelectedInputMethodAndSubtypeLocked(inputmethodinfo, i, false);
        mCurMethodId = s;
        if(ActivityManagerNative.isSystemReady()) {
            Intent intent = new Intent("android.intent.action.INPUT_METHOD_CHANGED");
            intent.addFlags(0x20000000);
            intent.putExtra("input_method_id", s);
            mContext.sendBroadcast(intent);
        }
        unbindCurrentClientLocked();
        Binder.restoreCallingIdentity(l);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
    }

    boolean showCurrentInputLocked(int i, ResultReceiver resultreceiver) {
        mShowRequested = true;
        if((i & 1) == 0)
            mShowExplicitlyRequested = true;
        if((i & 2) != 0) {
            mShowExplicitlyRequested = true;
            mShowForced = true;
        }
        if(mSystemReady) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        flag = false;
        if(mCurMethod != null) {
            executeOrSendMessage(mCurMethod, mCaller.obtainMessageIOO(1020, getImeShowFlags(), mCurMethod, resultreceiver));
            mInputShown = true;
            if(mHaveConnection && !mVisibleBound) {
                mContext.bindService(mCurIntent, mVisibleConnection, 1);
                mVisibleBound = true;
            }
            flag = true;
        } else
        if(mHaveConnection && SystemClock.uptimeMillis() >= 10000L + mLastBindTime) {
            Object aobj[] = new Object[3];
            aobj[0] = mCurMethodId;
            aobj[1] = Long.valueOf(SystemClock.uptimeMillis() - mLastBindTime);
            aobj[2] = Integer.valueOf(1);
            EventLog.writeEvent(32000, aobj);
            Slog.w("InputMethodManagerService", "Force disconnect/connect to the IME in showCurrentInputLocked()");
            mContext.unbindService(this);
            mContext.bindService(mCurIntent, this, 0x40000001);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient iinputmethodclient, String s) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurClient == null || iinputmethodclient == null || mCurClient.client.asBinder() != iinputmethodclient.asBinder())
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring showInputMethodAndSubtypeEnablerFromClient of: ").append(iinputmethodclient).toString());
        executeOrSendMessage(mCurMethod, mCaller.obtainMessageO(3, s));
        return;
    }

    public void showInputMethodPickerFromClient(IInputMethodClient iinputmethodclient) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurClient == null || iinputmethodclient == null || mCurClient.client.asBinder() != iinputmethodclient.asBinder())
            Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring showInputMethodPickerFromClient of uid ").append(Binder.getCallingUid()).append(": ").append(iinputmethodclient).toString());
        mHandler.sendEmptyMessage(2);
        return;
    }

    public void showMySoftInput(IBinder ibinder, int i) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(ibinder == null)
            break MISSING_BLOCK_LABEL_19;
        if(mCurToken == ibinder)
            break MISSING_BLOCK_LABEL_62;
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring showMySoftInput of uid ").append(Binder.getCallingUid()).append(" token: ").append(ibinder).toString());
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_101;
        long l = Binder.clearCallingIdentity();
        showCurrentInputLocked(i, null);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_101;
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
    }

    public boolean showSoftInput(IInputMethodClient iinputmethodclient, int i, ResultReceiver resultreceiver) {
        boolean flag;
        int j;
        long l;
        flag = false;
        j = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mCurClient == null || iinputmethodclient == null) goto _L2; else goto _L1
_L1:
        IBinder ibinder;
        IBinder ibinder1;
        ibinder = mCurClient.client.asBinder();
        ibinder1 = iinputmethodclient.asBinder();
        if(ibinder == ibinder1) goto _L3; else goto _L2
_L2:
        if(mIWindowManager.inputMethodClientHasFocus(iinputmethodclient)) goto _L3; else goto _L4
_L4:
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring showSoftInput of uid ").append(j).append(": ").append(iinputmethodclient).toString());
        hashmap;
        JVM INSTR monitorexit ;
_L5:
        Binder.restoreCallingIdentity(l);
        return flag;
        RemoteException remoteexception;
        remoteexception;
          goto _L5
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L3:
        flag = showCurrentInputLocked(i, resultreceiver);
        hashmap;
        JVM INSTR monitorexit ;
          goto _L5
    }

    public InputBindResult startInput(IInputMethodClient iinputmethodclient, IInputContext iinputcontext, EditorInfo editorinfo, int i) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        long l = Binder.clearCallingIdentity();
        InputBindResult inputbindresult = startInputLocked(iinputmethodclient, iinputcontext, editorinfo, i);
        Binder.restoreCallingIdentity(l);
        hashmap;
        JVM INSTR monitorexit ;
        return inputbindresult;
        Exception exception1;
        exception1;
        Binder.restoreCallingIdentity(l);
        throw exception1;
        Exception exception;
        exception;
        throw exception;
    }

    InputBindResult startInputInnerLocked() {
        InputBindResult inputbindresult;
        if(mCurMethodId == null)
            inputbindresult = mNoBinding;
        else
        if(!mSystemReady) {
            inputbindresult = new InputBindResult(null, mCurMethodId, mCurSeq);
        } else {
            InputMethodInfo inputmethodinfo = (InputMethodInfo)mMethodMap.get(mCurMethodId);
            if(inputmethodinfo == null)
                throw new IllegalArgumentException((new StringBuilder()).append("Unknown id: ").append(mCurMethodId).toString());
            unbindCurrentMethodLocked(false);
            mCurIntent = new Intent("android.view.InputMethod");
            mCurIntent.setComponent(inputmethodinfo.getComponent());
            mCurIntent.putExtra("android.intent.extra.client_label", 0x1040475);
            mCurIntent.putExtra("android.intent.extra.client_intent", PendingIntent.getActivity(mContext, 0, new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0));
            if(mContext.bindService(mCurIntent, this, 0x40000001)) {
                mLastBindTime = SystemClock.uptimeMillis();
                mHaveConnection = true;
                mCurId = inputmethodinfo.getId();
                mCurToken = new Binder();
                try {
                    mIWindowManager.addWindowToken(mCurToken, 2011);
                }
                catch(RemoteException remoteexception) { }
                inputbindresult = new InputBindResult(null, mCurId, mCurSeq);
            } else {
                mCurIntent = null;
                Slog.w("InputMethodManagerService", (new StringBuilder()).append("Failure connecting to input method service: ").append(mCurIntent).toString());
                inputbindresult = null;
            }
        }
        return inputbindresult;
    }

    InputBindResult startInputLocked(IInputMethodClient iinputmethodclient, IInputContext iinputcontext, EditorInfo editorinfo, int i) {
        if(mCurMethodId != null) goto _L2; else goto _L1
_L1:
        InputBindResult inputbindresult = mNoBinding;
_L4:
        return inputbindresult;
_L2:
        ClientState clientstate;
        clientstate = (ClientState)mClients.get(iinputmethodclient.asBinder());
        if(clientstate == null)
            throw new IllegalArgumentException((new StringBuilder()).append("unknown client ").append(iinputmethodclient.asBinder()).toString());
        if(mIWindowManager.inputMethodClientHasFocus(clientstate.client))
            break MISSING_BLOCK_LABEL_161;
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Starting input on non-focused client ").append(clientstate.client).append(" (uid=").append(clientstate.uid).append(" pid=").append(clientstate.pid).append(")").toString());
        inputbindresult = null;
        continue; /* Loop/switch isn't completed */
        RemoteException remoteexception;
        remoteexception;
        inputbindresult = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
        if(true) goto _L4; else goto _L3
_L3:
    }

    InputBindResult startInputUncheckedLocked(ClientState clientstate, IInputContext iinputcontext, EditorInfo editorinfo, int i) {
        boolean flag = true;
        if(mCurMethodId != null) goto _L2; else goto _L1
_L1:
        InputBindResult inputbindresult = mNoBinding;
_L4:
        return inputbindresult;
_L2:
        if(mCurClient != clientstate) {
            unbindCurrentClientLocked();
            if(mScreenOn) {
                IInputMethodClient iinputmethodclient = clientstate.client;
                HandlerCaller handlercaller = mCaller;
                int j;
                if(mScreenOn)
                    j = ((flag) ? 1 : 0);
                else
                    j = 0;
                executeOrSendMessage(iinputmethodclient, handlercaller.obtainMessageIO(3020, j, clientstate));
            }
        }
        mCurSeq = 1 + mCurSeq;
        if(mCurSeq <= 0)
            mCurSeq = ((flag) ? 1 : 0);
        mCurClient = clientstate;
        mCurInputContext = iinputcontext;
        mCurAttribute = editorinfo;
        if(mCurId != null && mCurId.equals(mCurMethodId)) {
            if(clientstate.curSession != null) {
                if((i & 0x100) == 0)
                    flag = false;
                inputbindresult = attachNewInputLocked(flag);
                continue; /* Loop/switch isn't completed */
            }
            if(mHaveConnection) {
                if(mCurMethod != null) {
                    if(!clientstate.sessionRequested) {
                        clientstate.sessionRequested = flag;
                        executeOrSendMessage(mCurMethod, mCaller.obtainMessageOO(1050, mCurMethod, new MethodCallback(mCurMethod, this)));
                    }
                    inputbindresult = new InputBindResult(null, mCurId, mCurSeq);
                    continue; /* Loop/switch isn't completed */
                }
                if(SystemClock.uptimeMillis() < 10000L + mLastBindTime) {
                    inputbindresult = new InputBindResult(null, mCurId, mCurSeq);
                    continue; /* Loop/switch isn't completed */
                }
                Object aobj[] = new Object[3];
                aobj[0] = mCurMethodId;
                aobj[flag] = Long.valueOf(SystemClock.uptimeMillis() - mLastBindTime);
                aobj[2] = Integer.valueOf(0);
                EventLog.writeEvent(32000, aobj);
            }
        }
        inputbindresult = startInputInnerLocked();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean switchToLastInputMethod(IBinder ibinder) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        int k;
        Pair pair = mSettings.getLastInputMethodAndSubtypeLocked();
        InputMethodInfo inputmethodinfo;
        Object obj;
        int i;
        boolean flag;
        List list;
        int j;
        String s;
        InputMethodInfo inputmethodinfo1;
        InputMethodSubtype inputmethodsubtype;
        boolean flag1;
        int l;
        int i1;
        if(pair != null)
            inputmethodinfo = (InputMethodInfo)mMethodMap.get(pair.first);
        else
            inputmethodinfo = null;
        obj = null;
        i = -1;
        if(pair == null || inputmethodinfo == null) goto _L2; else goto _L1
_L1:
        flag1 = inputmethodinfo.getId().equals(mCurMethodId);
        l = Integer.valueOf((String)pair.second).intValue();
        if(mCurrentSubtype == null)
            i1 = -1;
        else
            i1 = mCurrentSubtype.hashCode();
          goto _L3
_L7:
        obj = (String)pair.first;
        i = getSubtypeIdFromHashCode(inputmethodinfo, l);
_L2:
        if(!TextUtils.isEmpty(((CharSequence) (obj))) || canAddToLastInputMethod(mCurrentSubtype)) goto _L5; else goto _L4
_L4:
        list = mSettings.getEnabledInputMethodListLocked();
        if(list == null) goto _L5; else goto _L6
_L8:
        if(k < j) {
            inputmethodinfo1 = (InputMethodInfo)list.get(k);
            if(inputmethodinfo1.getSubtypeCount() <= 0 || !isSystemIme(inputmethodinfo1))
                break MISSING_BLOCK_LABEL_370;
            inputmethodsubtype = findLastResortApplicableSubtypeLocked(mRes, getSubtypes(inputmethodinfo1), "keyboard", s, true);
            if(inputmethodsubtype == null)
                break MISSING_BLOCK_LABEL_370;
            obj = inputmethodinfo1.getId();
            i = getSubtypeIdFromHashCode(inputmethodinfo1, inputmethodsubtype.hashCode());
            if(!inputmethodsubtype.getLocale().equals(s))
                break MISSING_BLOCK_LABEL_370;
        }
          goto _L5
_L3:
        if(flag1 && l == i1) goto _L2; else goto _L7
_L6:
        j = list.size();
        if(mCurrentSubtype == null)
            s = mRes.getConfiguration().locale.toString();
        else
            s = mCurrentSubtype.getLocale();
        k = 0;
          goto _L8
_L5:
        if(!TextUtils.isEmpty(((CharSequence) (obj)))) {
            setInputMethodWithSubtypeId(ibinder, ((String) (obj)), i);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
        k++;
          goto _L8
    }

    public boolean switchToNextInputMethod(IBinder ibinder, boolean flag) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        ImeSubtypeListItem imesubtypelistitem = mImListManager.getNextInputMethod(flag, (InputMethodInfo)mMethodMap.get(mCurMethodId), mCurrentSubtype);
        boolean flag1;
        if(imesubtypelistitem == null) {
            flag1 = false;
        } else {
            setInputMethodWithSubtypeId(ibinder, imesubtypelistitem.mImi.getId(), imesubtypelistitem.mSubtypeId);
            flag1 = true;
        }
        return flag1;
    }

    public void systemReady(StatusBarManagerService statusbarmanagerservice) {
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(mSystemReady)
            break MISSING_BLOCK_LABEL_153;
        mSystemReady = true;
        mKeyguardManager = (KeyguardManager)mContext.getSystemService("keyguard");
        mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
        mStatusBar = statusbarmanagerservice;
        statusbarmanagerservice.setIconVisibility("ime", false);
        updateImeWindowStatusLocked();
        mShowOngoingImeSwitcherForPhones = mRes.getBoolean(0x1110004);
        if(mShowOngoingImeSwitcherForPhones)
            mWindowManagerService.setOnHardKeyboardStatusChangeListener(mHardKeyboardListener);
        buildInputMethodListLocked(mMethodList, mMethodMap);
        if(!mImeSelectedOnBoot) {
            Slog.w("InputMethodManagerService", "Reset the default IME as \"Resource\" is ready here.");
            checkCurrentLocaleChangedLocked();
        }
        mLastSystemLocale = mRes.getConfiguration().locale;
        try {
            startInputInnerLocked();
        }
        catch(RuntimeException runtimeexception) {
            Slog.w("InputMethodManagerService", "Unexpected exception", runtimeexception);
        }
        hashmap;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    void unbindCurrentClientLocked() {
        if(mCurClient != null) {
            if(mBoundToMethod) {
                mBoundToMethod = false;
                if(mCurMethod != null)
                    executeOrSendMessage(mCurMethod, mCaller.obtainMessageO(1000, mCurMethod));
            }
            executeOrSendMessage(mCurClient.client, mCaller.obtainMessageIO(3020, 0, mCurClient));
            executeOrSendMessage(mCurClient.client, mCaller.obtainMessageIO(3000, mCurSeq, mCurClient.client));
            mCurClient.sessionRequested = false;
            mCurClient = null;
            hideInputMethodMenuLocked();
        }
    }

    void unbindCurrentMethodLocked(boolean flag) {
        if(mVisibleBound) {
            mContext.unbindService(mVisibleConnection);
            mVisibleBound = false;
        }
        if(mHaveConnection) {
            mContext.unbindService(this);
            mHaveConnection = false;
        }
        if(mCurToken != null) {
            try {
                if((1 & mImeWindowVis) != 0)
                    mWindowManagerService.saveLastInputMethodWindowForTransition();
                mIWindowManager.removeWindowToken(mCurToken);
            }
            catch(RemoteException remoteexception) { }
            mCurToken = null;
        }
        mCurId = null;
        clearCurMethodLocked();
        if(flag && mCurClient != null)
            executeOrSendMessage(mCurClient.client, mCaller.obtainMessageIO(3000, mCurSeq, mCurClient.client));
    }

    void updateFromSettingsLocked() {
        String s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method");
        if(TextUtils.isEmpty(s) && chooseNewDefaultIMELocked())
            s = android.provider.Settings.Secure.getString(mContext.getContentResolver(), "default_input_method");
        if(!TextUtils.isEmpty(s)) {
            try {
                setInputMethodLocked(s, getSelectedInputMethodSubtypeId(s));
            }
            catch(IllegalArgumentException illegalargumentexception) {
                Slog.w("InputMethodManagerService", (new StringBuilder()).append("Unknown input method from prefs: ").append(s).toString(), illegalargumentexception);
                mCurMethodId = null;
                unbindCurrentMethodLocked(true);
            }
            mShortcutInputMethodsAndSubtypes.clear();
        } else {
            mCurMethodId = null;
            unbindCurrentMethodLocked(true);
        }
    }

    public void updateStatusIcon(IBinder ibinder, String s, int i) {
        int j;
        long l;
        j = Binder.getCallingUid();
        l = Binder.clearCallingIdentity();
        if(ibinder == null) goto _L2; else goto _L1
_L1:
        if(mCurToken == ibinder) goto _L3; else goto _L2
_L2:
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Ignoring setInputMethod of uid ").append(j).append(" token: ").append(ibinder).toString());
_L6:
        Binder.restoreCallingIdentity(l);
        return;
_L3:
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        if(i != 0) goto _L5; else goto _L4
_L4:
        if(mStatusBar != null)
            mStatusBar.setIconVisibility("ime", false);
_L8:
        hashmap;
        JVM INSTR monitorexit ;
          goto _L6
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
_L5:
        if(s == null) goto _L8; else goto _L7
_L7:
        CharSequence charsequence = null;
        CharSequence charsequence1;
        PackageManager packagemanager = mContext.getPackageManager();
        charsequence1 = packagemanager.getApplicationLabel(packagemanager.getApplicationInfo(s, 0));
        charsequence = charsequence1;
_L11:
        if(mStatusBar == null) goto _L8; else goto _L9
_L9:
        StatusBarManagerService statusbarmanagerservice;
        String s1;
        statusbarmanagerservice = mStatusBar;
        if(charsequence == null)
            break MISSING_BLOCK_LABEL_206;
        s1 = charsequence.toString();
_L10:
        statusbarmanagerservice.setIcon("ime", s, i, 0, s1);
        mStatusBar.setIconVisibility("ime", true);
          goto _L8
        s1 = null;
          goto _L10
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
          goto _L11
    }

    public InputBindResult windowGainedFocus(IInputMethodClient iinputmethodclient, IBinder ibinder, int i, int j, int k, EditorInfo editorinfo, IInputContext iinputcontext) {
        InputBindResult inputbindresult;
        long l;
        inputbindresult = null;
        l = Binder.clearCallingIdentity();
        HashMap hashmap = mMethodMap;
        hashmap;
        JVM INSTR monitorenter ;
        ClientState clientstate;
        clientstate = (ClientState)mClients.get(iinputmethodclient.asBinder());
        if(clientstate == null)
            throw new IllegalArgumentException((new StringBuilder()).append("unknown client ").append(iinputmethodclient.asBinder()).toString());
        break MISSING_BLOCK_LABEL_91;
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception;
        exception;
        Binder.restoreCallingIdentity(l);
        throw exception;
        if(mIWindowManager.inputMethodClientHasFocus(clientstate.client)) goto _L2; else goto _L1
_L1:
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Focus gain on non-focused client ").append(clientstate.client).append(" (uid=").append(clientstate.uid).append(" pid=").append(clientstate.pid).append(")").toString());
        InputBindResult inputbindresult1 = null;
        hashmap;
        JVM INSTR monitorexit ;
_L18:
        Binder.restoreCallingIdentity(l);
_L4:
        return inputbindresult1;
        RemoteException remoteexception;
        remoteexception;
_L2:
        if(mCurFocusedWindow != ibinder)
            break MISSING_BLOCK_LABEL_266;
        Slog.w("InputMethodManagerService", (new StringBuilder()).append("Window already focused, ignoring focus gain of: ").append(iinputmethodclient).append(" attribute=").append(editorinfo).toString());
        if(editorinfo == null)
            break MISSING_BLOCK_LABEL_257;
        inputbindresult1 = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
        hashmap;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        inputbindresult1 = null;
        hashmap;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        mCurFocusedWindow = ibinder;
        boolean flag;
        boolean flag1;
        boolean flag2;
        if((j & 0xf0) != 16 && !mRes.getConfiguration().isLayoutSizeAtLeast(3))
            flag = false;
        else
            flag = true;
          goto _L3
_L7:
        if(!flag2 && editorinfo != null)
            inputbindresult = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
        hashmap;
        JVM INSTR monitorexit ;
        Binder.restoreCallingIdentity(l);
        inputbindresult1 = inputbindresult;
          goto _L4
_L9:
        flag1 = false;
          goto _L5
_L11:
        if(flag1 && flag)
            break MISSING_BLOCK_LABEL_377;
        if(!android.view.WindowManager.LayoutParams.mayUseInputMethod(k)) goto _L7; else goto _L6
_L6:
        hideCurrentInputLocked(2, null);
          goto _L7
        if(flag1 && flag && (j & 0x100) != 0) {
            if(editorinfo != null) {
                inputbindresult = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
                flag2 = true;
            }
            showCurrentInputLocked(1, null);
        }
          goto _L7
_L12:
        if((j & 0x100) != 0)
            hideCurrentInputLocked(0, null);
          goto _L7
_L13:
        hideCurrentInputLocked(0, null);
          goto _L7
_L14:
        if((j & 0x100) != 0) {
            if(editorinfo != null) {
                inputbindresult = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
                flag2 = true;
            }
            showCurrentInputLocked(1, null);
        }
          goto _L7
_L15:
        if(editorinfo != null) {
            inputbindresult = startInputUncheckedLocked(clientstate, iinputcontext, editorinfo, i);
            flag2 = true;
        }
        showCurrentInputLocked(1, null);
          goto _L7
_L3:
        if((i & 2) == 0) goto _L9; else goto _L8
_L8:
        flag1 = true;
_L5:
        flag2 = false;
        j & 0xf;
        JVM INSTR tableswitch 0 5: default 299
    //                   0 349
    //                   1 299
    //                   2 427
    //                   3 446
    //                   4 456
    //                   5 496;
           goto _L10 _L11 _L10 _L12 _L13 _L14 _L15
_L10:
        if(true) goto _L7; else goto _L16
_L16:
        if(true) goto _L18; else goto _L17
_L17:
    }

    static final boolean DEBUG = false;
    private static final Locale ENGLISH_LOCALE = new Locale("en");
    static final int MSG_ATTACH_TOKEN = 1040;
    static final int MSG_BIND_INPUT = 1010;
    static final int MSG_BIND_METHOD = 3010;
    static final int MSG_CREATE_SESSION = 1050;
    static final int MSG_HARD_KEYBOARD_SWITCH_CHANGED = 4000;
    static final int MSG_HIDE_SOFT_INPUT = 1030;
    static final int MSG_RESTART_INPUT = 2010;
    static final int MSG_SET_ACTIVE = 3020;
    static final int MSG_SHOW_IM_CONFIG = 4;
    static final int MSG_SHOW_IM_PICKER = 1;
    static final int MSG_SHOW_IM_SUBTYPE_ENABLER = 3;
    static final int MSG_SHOW_IM_SUBTYPE_PICKER = 2;
    static final int MSG_SHOW_SOFT_INPUT = 1020;
    static final int MSG_START_INPUT = 2000;
    static final int MSG_UNBIND_INPUT = 1000;
    static final int MSG_UNBIND_METHOD = 3000;
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final String NOT_A_SUBTYPE_ID_STR = String.valueOf(-1);
    static final int SECURE_SUGGESTION_SPANS_MAX_SIZE = 20;
    private static final String SUBTYPE_MODE_KEYBOARD = "keyboard";
    private static final String SUBTYPE_MODE_VOICE = "voice";
    static final String TAG = "InputMethodManagerService";
    private static final String TAG_ASCII_CAPABLE = "AsciiCapable";
    private static final String TAG_ENABLED_WHEN_DEFAULT_IS_NOT_ASCII_CAPABLE = "EnabledWhenDefaultIsNotAsciiCapable";
    private static final String TAG_TRY_SUPPRESSING_IME_SWITCHER = "TrySuppressingImeSwitcher";
    static final long TIME_TO_RECONNECT = 10000L;
    int mBackDisposition;
    boolean mBoundToMethod;
    final HandlerCaller mCaller;
    final HashMap mClients;
    final Context mContext;
    EditorInfo mCurAttribute;
    ClientState mCurClient;
    IBinder mCurFocusedWindow;
    String mCurId;
    IInputContext mCurInputContext;
    Intent mCurIntent;
    IInputMethod mCurMethod;
    String mCurMethodId;
    int mCurSeq;
    IBinder mCurToken;
    private InputMethodSubtype mCurrentSubtype;
    private android.app.AlertDialog.Builder mDialogBuilder;
    SessionState mEnabledSession;
    private final InputMethodFileManager mFileManager;
    final Handler mHandler;
    private final HardKeyboardListener mHardKeyboardListener;
    boolean mHaveConnection;
    final IWindowManager mIWindowManager;
    private final InputMethodAndSubtypeListManager mImListManager;
    private final boolean mImeSelectedOnBoot;
    private PendingIntent mImeSwitchPendingIntent;
    private Notification mImeSwitcherNotification;
    int mImeWindowVis;
    private InputMethodInfo mIms[];
    boolean mInputShown;
    private KeyguardManager mKeyguardManager;
    long mLastBindTime;
    private Locale mLastSystemLocale;
    final ArrayList mMethodList;
    final HashMap mMethodMap;
    final InputBindResult mNoBinding;
    private NotificationManager mNotificationManager;
    private boolean mNotificationShown;
    final Resources mRes;
    boolean mScreenOn;
    private final LruCache mSecureSuggestionSpans;
    final InputMethodSettings mSettings;
    final SettingsObserver mSettingsObserver;
    private final HashMap mShortcutInputMethodsAndSubtypes;
    boolean mShowExplicitlyRequested;
    boolean mShowForced;
    private boolean mShowOngoingImeSwitcherForPhones;
    boolean mShowRequested;
    private StatusBarManagerService mStatusBar;
    private int mSubtypeIds[];
    private AlertDialog mSwitchingDialog;
    private View mSwitchingDialogTitleView;
    boolean mSystemReady;
    boolean mVisibleBound;
    final ServiceConnection mVisibleConnection;
    private final WindowManagerService mWindowManagerService;



















}
