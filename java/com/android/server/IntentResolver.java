// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.*;
import java.io.PrintWriter;
import java.util.*;

public abstract class IntentResolver {
    private class IteratorWrapper
        implements Iterator {

        public boolean hasNext() {
            return mI.hasNext();
        }

        public IntentFilter next() {
            IntentFilter intentfilter = (IntentFilter)mI.next();
            mCur = intentfilter;
            return intentfilter;
        }

        public volatile Object next() {
            return next();
        }

        public void remove() {
            if(mCur != null)
                removeFilterInternal(mCur);
            mI.remove();
        }

        private IntentFilter mCur;
        private final Iterator mI;
        final IntentResolver this$0;

        IteratorWrapper(Iterator iterator) {
            this$0 = IntentResolver.this;
            super();
            mI = iterator;
        }
    }


    public IntentResolver() {
    }

    private void buildResolveList(Intent intent, FastImmutableArraySet fastimmutablearrayset, boolean flag, boolean flag1, String s, String s1, List list, 
            List list1, int i) {
        String s2;
        android.net.Uri uri;
        String s3;
        boolean flag3;
        IntentFilter intentfilter;
        s2 = intent.getAction();
        uri = intent.getData();
        s3 = intent.getPackage();
        boolean flag2 = intent.isExcludingStopped();
        int j;
        int k;
        if(list != null)
            j = list.size();
        else
            j = 0;
        flag3 = false;
        k = 0;
        if(k >= j)
            break MISSING_BLOCK_LABEL_421;
        intentfilter = (IntentFilter)list.get(k);
        if(flag)
            Slog.v("IntentResolver", (new StringBuilder()).append("Matching against filter ").append(intentfilter).toString());
        if(!flag2 || !isFilterStopped(intentfilter, i))
            break; /* Loop/switch isn't completed */
        if(flag)
            Slog.v("IntentResolver", "  Filter's target is stopped; skipping");
_L9:
        k++;
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_44;
_L1:
        if(s3 != null && !s3.equals(packageForFilter(intentfilter))) {
            if(flag)
                Slog.v("IntentResolver", (new StringBuilder()).append("  Filter is not from package ").append(s3).append("; skipping").toString());
        } else {
label0:
            {
                if(allowFilterResult(intentfilter, list1))
                    break label0;
                if(flag)
                    Slog.v("IntentResolver", "  Filter's target already added");
            }
        }
        continue; /* Loop/switch isn't completed */
        int l;
        for(l = intentfilter.match(s2, s, s1, uri, fastimmutablearrayset, "IntentResolver"); l >= 0; l = intentfilter.match(s2, s, s1, uri, fastimmutablearrayset, "IntentResolver")) {
            if(flag)
                Slog.v("IntentResolver", (new StringBuilder()).append("  Filter matched!  match=0x").append(Integer.toHexString(l)).toString());
            if(!flag1 || intentfilter.hasCategory("android.intent.category.DEFAULT")) {
                Object obj = newResult(intentfilter, l, i);
                if(obj != null)
                    list1.add(obj);
            } else {
                flag3 = true;
            }
            continue; /* Loop/switch isn't completed */
        }

        if(!flag)
            continue; /* Loop/switch isn't completed */
        l;
        JVM INSTR tableswitch -4 -1: default 360
    //                   -4 400
    //                   -3 393
    //                   -2 407
    //                   -1 414;
           goto _L3 _L4 _L5 _L6 _L7
_L5:
        break; /* Loop/switch isn't completed */
_L7:
        break MISSING_BLOCK_LABEL_414;
_L3:
        String s4 = "unknown reason";
_L10:
        Slog.v("IntentResolver", (new StringBuilder()).append("  Filter did not match: ").append(s4).toString());
        if(true) goto _L9; else goto _L8
_L8:
        s4 = "action";
          goto _L10
_L4:
        s4 = "category";
          goto _L10
_L6:
        s4 = "data";
          goto _L10
        s4 = "type";
          goto _L10
        if(list1.size() == 0 && flag3)
            Slog.w("IntentResolver", "resolveIntent failed: found match, but none with Intent.CATEGORY_DEFAULT");
        return;
    }

    private static FastImmutableArraySet getFastIntentCategories(Intent intent) {
        Set set = intent.getCategories();
        FastImmutableArraySet fastimmutablearrayset;
        if(set == null)
            fastimmutablearrayset = null;
        else
            fastimmutablearrayset = new FastImmutableArraySet(set.toArray(new String[set.size()]));
        return fastimmutablearrayset;
    }

    private final int register_intent_filter(IntentFilter intentfilter, Iterator iterator, HashMap hashmap, String s) {
        int i;
        if(iterator == null) {
            i = 0;
        } else {
            i = 0;
            while(iterator.hasNext())  {
                String s1 = (String)iterator.next();
                i++;
                ArrayList arraylist = (ArrayList)hashmap.get(s1);
                if(arraylist == null) {
                    arraylist = new ArrayList();
                    hashmap.put(s1, arraylist);
                }
                arraylist.add(intentfilter);
            }
        }
        return i;
    }

    private final int register_mime_types(IntentFilter intentfilter, String s) {
        Iterator iterator = intentfilter.typesIterator();
        int i;
        if(iterator == null) {
            i = 0;
        } else {
            i = 0;
            while(iterator.hasNext())  {
                String s1 = (String)iterator.next();
                i++;
                String s2 = s1;
                int j = s1.indexOf('/');
                ArrayList arraylist;
                if(j > 0)
                    s2 = s1.substring(0, j).intern();
                else
                    s1 = (new StringBuilder()).append(s1).append("/*").toString();
                arraylist = (ArrayList)mTypeToFilter.get(s1);
                if(arraylist == null) {
                    arraylist = new ArrayList();
                    mTypeToFilter.put(s1, arraylist);
                }
                arraylist.add(intentfilter);
                if(j > 0) {
                    ArrayList arraylist2 = (ArrayList)mBaseTypeToFilter.get(s2);
                    if(arraylist2 == null) {
                        arraylist2 = new ArrayList();
                        mBaseTypeToFilter.put(s2, arraylist2);
                    }
                    arraylist2.add(intentfilter);
                } else {
                    ArrayList arraylist1 = (ArrayList)mWildTypeToFilter.get(s2);
                    if(arraylist1 == null) {
                        arraylist1 = new ArrayList();
                        mWildTypeToFilter.put(s2, arraylist1);
                    }
                    arraylist1.add(intentfilter);
                }
            }
        }
        return i;
    }

    private final boolean remove_all_objects(List list, Object obj) {
        boolean flag = false;
        if(list != null) {
            int i = list.size();
            for(int j = 0; j < i; j++)
                if(list.get(j) == obj) {
                    list.remove(j);
                    j--;
                    i--;
                }

            if(i > 0)
                flag = true;
        }
        return flag;
    }

    private final int unregister_intent_filter(IntentFilter intentfilter, Iterator iterator, HashMap hashmap, String s) {
        int i;
        if(iterator == null) {
            i = 0;
        } else {
            i = 0;
            while(iterator.hasNext())  {
                String s1 = (String)iterator.next();
                i++;
                if(!remove_all_objects((List)hashmap.get(s1), intentfilter))
                    hashmap.remove(s1);
            }
        }
        return i;
    }

    private final int unregister_mime_types(IntentFilter intentfilter, String s) {
        Iterator iterator = intentfilter.typesIterator();
        int i;
        if(iterator == null) {
            i = 0;
        } else {
            i = 0;
            while(iterator.hasNext())  {
                String s1 = (String)iterator.next();
                i++;
                String s2 = s1;
                int j = s1.indexOf('/');
                if(j > 0)
                    s2 = s1.substring(0, j).intern();
                else
                    s1 = (new StringBuilder()).append(s1).append("/*").toString();
                if(!remove_all_objects((List)mTypeToFilter.get(s1), intentfilter))
                    mTypeToFilter.remove(s1);
                if(j > 0) {
                    if(!remove_all_objects((List)mBaseTypeToFilter.get(s2), intentfilter))
                        mBaseTypeToFilter.remove(s2);
                } else
                if(!remove_all_objects((List)mWildTypeToFilter.get(s2), intentfilter))
                    mWildTypeToFilter.remove(s2);
            }
        }
        return i;
    }

    public void addFilter(IntentFilter intentfilter) {
        mFilters.add(intentfilter);
        int i = register_intent_filter(intentfilter, intentfilter.schemesIterator(), mSchemeToFilter, "      Scheme: ");
        int j = register_mime_types(intentfilter, "      Type: ");
        if(i == 0 && j == 0)
            register_intent_filter(intentfilter, intentfilter.actionsIterator(), mActionToFilter, "      Action: ");
        if(j != 0)
            register_intent_filter(intentfilter, intentfilter.actionsIterator(), mTypedActionToFilter, "      TypedAction: ");
    }

    protected boolean allowFilterResult(IntentFilter intentfilter, List list) {
        return true;
    }

    public boolean dump(PrintWriter printwriter, String s, String s1, String s2, boolean flag) {
        String s3 = (new StringBuilder()).append(s1).append("  ").toString();
        String s4 = (new StringBuilder()).append("\n").append(s1).toString();
        String s5 = (new StringBuilder()).append(s).append("\n").append(s1).toString();
        if(dumpMap(printwriter, s5, "Full MIME Types:", s3, mTypeToFilter, s2, flag))
            s5 = s4;
        if(dumpMap(printwriter, s5, "Base MIME Types:", s3, mBaseTypeToFilter, s2, flag))
            s5 = s4;
        if(dumpMap(printwriter, s5, "Wild MIME Types:", s3, mWildTypeToFilter, s2, flag))
            s5 = s4;
        if(dumpMap(printwriter, s5, "Schemes:", s3, mSchemeToFilter, s2, flag))
            s5 = s4;
        if(dumpMap(printwriter, s5, "Non-Data Actions:", s3, mActionToFilter, s2, flag))
            s5 = s4;
        if(dumpMap(printwriter, s5, "MIME Typed Actions:", s3, mTypedActionToFilter, s2, flag))
            s5 = s4;
        boolean flag1;
        if(s5 == s4)
            flag1 = true;
        else
            flag1 = false;
        return flag1;
    }

    protected void dumpFilter(PrintWriter printwriter, String s, IntentFilter intentfilter) {
        printwriter.print(s);
        printwriter.println(intentfilter);
    }

    boolean dumpMap(PrintWriter printwriter, String s, String s1, String s2, Map map, String s3, boolean flag) {
        String s4 = (new StringBuilder()).append(s2).append("  ").toString();
        String s5 = (new StringBuilder()).append(s2).append("    ").toString();
        boolean flag1 = false;
        PrintWriterPrinter printwriterprinter = null;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            ArrayList arraylist = (ArrayList)entry.getValue();
            int i = arraylist.size();
            boolean flag2 = false;
            int j = 0;
            while(j < i)  {
                IntentFilter intentfilter = (IntentFilter)arraylist.get(j);
                if(s3 == null || s3.equals(packageForFilter(intentfilter))) {
                    if(s1 != null) {
                        printwriter.print(s);
                        printwriter.println(s1);
                        s1 = null;
                    }
                    if(!flag2) {
                        printwriter.print(s4);
                        printwriter.print((String)entry.getKey());
                        printwriter.println(":");
                        flag2 = true;
                    }
                    flag1 = true;
                    dumpFilter(printwriter, s5, intentfilter);
                    if(flag) {
                        if(printwriterprinter == null)
                            printwriterprinter = new PrintWriterPrinter(printwriter);
                        intentfilter.dump(printwriterprinter, (new StringBuilder()).append(s5).append("  ").toString());
                    }
                }
                j++;
            }
        }

        return flag1;
    }

    public Iterator filterIterator() {
        return new IteratorWrapper(mFilters.iterator());
    }

    public Set filterSet() {
        return Collections.unmodifiableSet(mFilters);
    }

    protected boolean isFilterStopped(IntentFilter intentfilter, int i) {
        return false;
    }

    protected Object newResult(IntentFilter intentfilter, int i, int j) {
        return intentfilter;
    }

    protected abstract String packageForFilter(IntentFilter intentfilter);

    public List queryIntent(Intent intent, String s, boolean flag, int i) {
        ArrayList arraylist;
        boolean flag1;
        ArrayList arraylist1;
        ArrayList arraylist2;
        String s2;
        String s1 = intent.getScheme();
        arraylist = new ArrayList();
        ArrayList arraylist3;
        ArrayList arraylist4;
        FastImmutableArraySet fastimmutablearrayset;
        Iterator iterator;
        Object obj;
        int j;
        if((8 & intent.getFlags()) != 0)
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("Resolving type ").append(s).append(" scheme ").append(s1).append(" of intent ").append(intent).toString());
        arraylist1 = null;
        arraylist2 = null;
        arraylist3 = null;
        arraylist4 = null;
        if(s == null) goto _L2; else goto _L1
_L1:
        j = s.indexOf('/');
        if(j <= 0) goto _L2; else goto _L3
_L3:
        s2 = s.substring(0, j);
        if(s2.equals("*")) goto _L5; else goto _L4
_L4:
        if(s.length() == j + 2 && s.charAt(j + 1) == '*') goto _L7; else goto _L6
_L6:
        arraylist1 = (ArrayList)mTypeToFilter.get(s);
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("First type cut: ").append(arraylist1).toString());
        arraylist2 = (ArrayList)mWildTypeToFilter.get(s2);
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("Second type cut: ").append(arraylist2).toString());
_L10:
        arraylist3 = (ArrayList)mWildTypeToFilter.get("*");
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("Third type cut: ").append(arraylist3).toString());
_L2:
        if(s1 != null) {
            arraylist4 = (ArrayList)mSchemeToFilter.get(s1);
            if(flag1)
                Slog.v("IntentResolver", (new StringBuilder()).append("Scheme list: ").append(arraylist4).toString());
        }
        if(s == null && s1 == null && intent.getAction() != null) {
            arraylist1 = (ArrayList)mActionToFilter.get(intent.getAction());
            if(flag1)
                Slog.v("IntentResolver", (new StringBuilder()).append("Action list: ").append(arraylist1).toString());
        }
        fastimmutablearrayset = getFastIntentCategories(intent);
        if(arraylist1 != null)
            buildResolveList(intent, fastimmutablearrayset, flag1, flag, s, s1, arraylist1, arraylist, i);
        if(arraylist2 != null)
            buildResolveList(intent, fastimmutablearrayset, flag1, flag, s, s1, arraylist2, arraylist, i);
        if(arraylist3 != null)
            buildResolveList(intent, fastimmutablearrayset, flag1, flag, s, s1, arraylist3, arraylist, i);
        if(arraylist4 != null)
            buildResolveList(intent, fastimmutablearrayset, flag1, flag, s, s1, arraylist4, arraylist, i);
        sortResults(arraylist);
        if(flag1) {
            Slog.v("IntentResolver", "Final result list:");
            for(iterator = arraylist.iterator(); iterator.hasNext(); Slog.v("IntentResolver", (new StringBuilder()).append("  ").append(obj).toString()))
                obj = iterator.next();

        }
        break; /* Loop/switch isn't completed */
_L7:
        arraylist1 = (ArrayList)mBaseTypeToFilter.get(s2);
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("First type cut: ").append(arraylist1).toString());
        arraylist2 = (ArrayList)mWildTypeToFilter.get(s2);
        if(flag1)
            Slog.v("IntentResolver", (new StringBuilder()).append("Second type cut: ").append(arraylist2).toString());
        continue; /* Loop/switch isn't completed */
_L5:
        if(intent.getAction() != null) {
            arraylist1 = (ArrayList)mTypedActionToFilter.get(intent.getAction());
            if(flag1)
                Slog.v("IntentResolver", (new StringBuilder()).append("Typed Action list: ").append(arraylist1).toString());
        }
        if(true) goto _L2; else goto _L8
_L8:
        return arraylist;
        if(true) goto _L10; else goto _L9
_L9:
    }

    public List queryIntentFromList(Intent intent, String s, boolean flag, ArrayList arraylist, int i) {
        ArrayList arraylist1 = new ArrayList();
        boolean flag1;
        FastImmutableArraySet fastimmutablearrayset;
        String s1;
        int j;
        if((8 & intent.getFlags()) != 0)
            flag1 = true;
        else
            flag1 = false;
        fastimmutablearrayset = getFastIntentCategories(intent);
        s1 = intent.getScheme();
        j = arraylist.size();
        for(int k = 0; k < j; k++)
            buildResolveList(intent, fastimmutablearrayset, flag1, flag, s, s1, (List)arraylist.get(k), arraylist1, i);

        sortResults(arraylist1);
        return arraylist1;
    }

    public void removeFilter(IntentFilter intentfilter) {
        removeFilterInternal(intentfilter);
        mFilters.remove(intentfilter);
    }

    void removeFilterInternal(IntentFilter intentfilter) {
        int i = unregister_intent_filter(intentfilter, intentfilter.schemesIterator(), mSchemeToFilter, "      Scheme: ");
        int j = unregister_mime_types(intentfilter, "      Type: ");
        if(i == 0 && j == 0)
            unregister_intent_filter(intentfilter, intentfilter.actionsIterator(), mActionToFilter, "      Action: ");
        if(j != 0)
            unregister_intent_filter(intentfilter, intentfilter.actionsIterator(), mTypedActionToFilter, "      TypedAction: ");
    }

    protected void sortResults(List list) {
        Collections.sort(list, mResolvePrioritySorter);
    }

    private static final boolean DEBUG = false;
    private static final String TAG = "IntentResolver";
    private static final boolean localLOGV;
    private static final Comparator mResolvePrioritySorter = new Comparator() {

        public int compare(Object obj, Object obj1) {
            int i = ((IntentFilter)obj).getPriority();
            int j = ((IntentFilter)obj1).getPriority();
            byte byte0;
            if(i > j)
                byte0 = -1;
            else
            if(i < j)
                byte0 = 1;
            else
                byte0 = 0;
            return byte0;
        }

    };
    private final HashMap mActionToFilter = new HashMap();
    private final HashMap mBaseTypeToFilter = new HashMap();
    private final HashSet mFilters = new HashSet();
    private final HashMap mSchemeToFilter = new HashMap();
    private final HashMap mTypeToFilter = new HashMap();
    private final HashMap mTypedActionToFilter = new HashMap();
    private final HashMap mWildTypeToFilter = new HashMap();

}
