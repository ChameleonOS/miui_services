// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) nonlb 

package com.android.server.pm;

import android.content.pm.UserInfo;
import android.os.*;
import android.util.*;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastXmlSerializer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.*;

// Referenced classes of package com.android.server.pm:
//            Installer

public class UserManager {

    public UserManager(Installer installer, File file) {
        this(Environment.getDataDirectory(), file);
        mInstaller = installer;
    }

    UserManager(File file, File file1) {
        mUsers = new SparseArray();
        mUsersDir = new File(file, USER_INFO_DIR);
        mUsersDir.mkdirs();
        (new File(mUsersDir, "0")).mkdirs();
        mBaseUserPath = file1;
        FileUtils.setPermissions(mUsersDir.toString(), 509, -1, -1);
        mUserListFile = new File(mUsersDir, "userlist.xml");
        readUserList();
    }

    private boolean createPackageFolders(int i, File file) {
        if(mInstaller != null) {
            file.mkdir();
            FileUtils.setPermissions(file.toString(), 505, -1, -1);
            mInstaller.cloneUserData(0, i, false);
        }
        return true;
    }

    private void fallbackToSingleUserLocked() {
        UserInfo userinfo = new UserInfo(0, "Primary", 3);
        mUsers.put(0, userinfo);
        updateUserIdsLocked();
        writeUserListLocked();
        writeUserLocked(userinfo);
    }

    private int getNextAvailableId() {
        int i = 0;
        do {
            if(i >= 0x7fffffff || mUsers.indexOfKey(i) < 0)
                return i;
            i++;
        } while(true);
    }

    private UserInfo readUser(int i) {
        int j;
        String s;
        FileInputStream fileinputstream;
        j = 0;
        s = null;
        fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(new File(mUsersDir, (new StringBuilder()).append(Integer.toString(i)).append(".xml").toString()));
        XmlPullParser xmlpullparser;
        int k;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
        do
            k = xmlpullparser.next();
        while(k != 2 && k != 1);
        if(k == 2) goto _L2; else goto _L1
_L1:
        Slog.e("UserManager", (new StringBuilder()).append("Unable to read user ").append(i).toString());
        UserInfo userinfo;
        Exception exception;
        userinfo = null;
        IOException ioexception2;
        int l;
        IOException ioexception6;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception5) { }
_L8:
        return userinfo;
_L2:
        if(k != 2)
            break MISSING_BLOCK_LABEL_269;
        if(!xmlpullparser.getName().equals("user"))
            break MISSING_BLOCK_LABEL_269;
        if(Integer.parseInt(xmlpullparser.getAttributeValue(null, "id")) == i)
            break MISSING_BLOCK_LABEL_194;
        Slog.e("UserManager", "User id does not match the file name");
        userinfo = null;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception4) { }
        break MISSING_BLOCK_LABEL_129;
        j = Integer.parseInt(xmlpullparser.getAttributeValue(null, "flags"));
        do
            l = xmlpullparser.next();
        while(l != 2 && l != 1);
        if(l == 2 && xmlpullparser.getName().equals("name") && xmlpullparser.next() == 4)
            s = xmlpullparser.getText();
        userinfo = new UserInfo(i, s, j);
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception3) { }
        break MISSING_BLOCK_LABEL_129;
        exception;
_L5:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception2) { }
        throw exception;
        ioexception6;
_L7:
        if(fileinputstream == null) goto _L4; else goto _L3
_L3:
        fileinputstream.close();
          goto _L4
_L6:
        if(fileinputstream != null)
            fileinputstream.close();
          goto _L4
        exception;
        fileinputstream = fileinputstream1;
          goto _L5
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        fileinputstream = fileinputstream1;
          goto _L6
        IOException ioexception;
        ioexception;
        fileinputstream = fileinputstream1;
          goto _L7
_L4:
        userinfo = null;
          goto _L8
        IOException ioexception1;
        ioexception1;
          goto _L4
        XmlPullParserException xmlpullparserexception1;
        xmlpullparserexception1;
          goto _L6
    }

    private void readUserList() {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        readUserListLocked();
        return;
    }

    private void readUserListLocked() {
        if(mUserListFile.exists()) goto _L2; else goto _L1
_L1:
        fallbackToSingleUserLocked();
_L3:
        return;
_L2:
        FileInputStream fileinputstream = null;
        FileInputStream fileinputstream1 = new FileInputStream(mUserListFile);
        XmlPullParser xmlpullparser;
        xmlpullparser = Xml.newPullParser();
        xmlpullparser.setInput(fileinputstream1, null);
        int i;
        do
            i = xmlpullparser.next();
        while(i != 2 && i != 1);
        if(i == 2)
            break MISSING_BLOCK_LABEL_98;
        Slog.e("UserManager", "Unable to read user list");
        fallbackToSingleUserLocked();
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception1) { }
          goto _L3
        do {
            int j = xmlpullparser.next();
            if(j == 1)
                break;
            if(j == 2 && xmlpullparser.getName().equals("user")) {
                UserInfo userinfo = readUser(Integer.parseInt(xmlpullparser.getAttributeValue(null, "id")));
                if(userinfo != null)
                    mUsers.put(userinfo.id, userinfo);
            }
        } while(true);
          goto _L4
        IOException ioexception2;
        ioexception2;
        fileinputstream = fileinputstream1;
_L10:
        fallbackToSingleUserLocked();
        if(fileinputstream == null) goto _L3; else goto _L5
_L5:
        fileinputstream.close();
          goto _L3
_L4:
        updateUserIdsLocked();
        Exception exception;
        XmlPullParserException xmlpullparserexception1;
        if(fileinputstream1 != null)
            try {
                fileinputstream1.close();
            }
            catch(IOException ioexception3) { }
          goto _L3
        xmlpullparserexception1;
_L9:
        fallbackToSingleUserLocked();
        if(fileinputstream == null) goto _L3; else goto _L6
_L6:
        fileinputstream.close();
          goto _L3
        exception;
_L8:
        if(fileinputstream != null)
            try {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
        exception;
        fileinputstream = fileinputstream1;
        if(true) goto _L8; else goto _L7
_L7:
        XmlPullParserException xmlpullparserexception;
        xmlpullparserexception;
        fileinputstream = fileinputstream1;
          goto _L9
        IOException ioexception4;
        ioexception4;
          goto _L10
    }

    private boolean removeUserLocked(int i) {
        boolean flag;
        if((UserInfo)mUsers.get(i) != null) {
            mUsers.remove(i);
            (new File(mUsersDir, (new StringBuilder()).append(i).append(".xml").toString())).delete();
            writeUserListLocked();
            updateUserIdsLocked();
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private void updateUserIdsLocked() {
        if(mUserIds == null || mUserIds.length != mUsers.size())
            mUserIds = new int[mUsers.size()];
        for(int i = 0; i < mUsers.size(); i++)
            mUserIds[i] = mUsers.keyAt(i);

    }

    private void writeUserListLocked() {
        FileOutputStream fileoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(mUserListFile);
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream1);
        FastXmlSerializer fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "users");
        for(int i = 0; i < mUsers.size(); i++) {
            UserInfo userinfo = (UserInfo)mUsers.valueAt(i);
            fastxmlserializer.startTag(null, "user");
            fastxmlserializer.attribute(null, "id", Integer.toString(userinfo.id));
            fastxmlserializer.endTag(null, "user");
        }

        fastxmlserializer.endTag(null, "users");
        fastxmlserializer.endDocument();
        if(fileoutputstream1 == null)
            break MISSING_BLOCK_LABEL_179;
        fileoutputstream1.close();
_L1:
        return;
        IOException ioexception4;
        ioexception4;
_L3:
        Slog.e("UserManager", "Error writing user list");
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L1
        Exception exception;
        exception;
_L2:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        IOException ioexception3;
        ioexception3;
          goto _L1
        exception;
        fileoutputstream = fileoutputstream1;
          goto _L2
        IOException ioexception;
        ioexception;
        fileoutputstream = fileoutputstream1;
          goto _L3
    }

    private void writeUserLocked(UserInfo userinfo) {
        FileOutputStream fileoutputstream = null;
        FileOutputStream fileoutputstream1 = new FileOutputStream(new File(mUsersDir, (new StringBuilder()).append(userinfo.id).append(".xml").toString()));
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(fileoutputstream1);
        FastXmlSerializer fastxmlserializer = new FastXmlSerializer();
        fastxmlserializer.setOutput(bufferedoutputstream, "utf-8");
        fastxmlserializer.startDocument(null, Boolean.valueOf(true));
        fastxmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastxmlserializer.startTag(null, "user");
        fastxmlserializer.attribute(null, "id", Integer.toString(userinfo.id));
        fastxmlserializer.attribute(null, "flags", Integer.toString(userinfo.flags));
        fastxmlserializer.startTag(null, "name");
        fastxmlserializer.text(userinfo.name);
        fastxmlserializer.endTag(null, "name");
        fastxmlserializer.endTag(null, "user");
        fastxmlserializer.endDocument();
        if(fileoutputstream1 == null)
            break MISSING_BLOCK_LABEL_204;
        fileoutputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        Slog.e("UserManager", (new StringBuilder()).append("Error writing user info ").append(userinfo.id).append("\n").append(ioexception).toString());
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception2) { }
          goto _L1
        Exception exception;
        exception;
_L2:
        if(fileoutputstream != null)
            try {
                fileoutputstream.close();
            }
            catch(IOException ioexception1) { }
        throw exception;
        IOException ioexception3;
        ioexception3;
          goto _L1
        exception;
        fileoutputstream = fileoutputstream1;
          goto _L2
        ioexception;
        fileoutputstream = fileoutputstream1;
          goto _L3
    }

    public void clearUserDataForAllUsers(String s) {
        int ai[] = mUserIds;
        int i = ai.length;
        int j = 0;
        while(j < i)  {
            int k = ai[j];
            if(k != 0)
                mInstaller.clearUserData(s, k);
            j++;
        }
    }

    public UserInfo createUser(String s, int i) {
        int j;
        UserInfo userinfo;
        j = getNextAvailableId();
        userinfo = new UserInfo(j, s, i);
        if(createPackageFolders(j, new File(mBaseUserPath, Integer.toString(j)))) goto _L2; else goto _L1
_L1:
        userinfo = null;
_L4:
        return userinfo;
_L2:
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        mUsers.put(j, userinfo);
        writeUserListLocked();
        writeUserLocked(userinfo);
        updateUserIdsLocked();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean exists(int i) {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        boolean flag = ArrayUtils.contains(mUserIds, i);
        return flag;
    }

    public UserInfo getUser(int i) {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        UserInfo userinfo = (UserInfo)mUsers.get(i);
        return userinfo;
    }

    int[] getUserIds() {
        return mUserIds;
    }

    public List getUsers() {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        ArrayList arraylist = new ArrayList(mUsers.size());
        for(int i = 0; i < mUsers.size(); i++)
            arraylist.add(mUsers.valueAt(i));

        return arraylist;
    }

    public void installPackageForAllUsers(String s, int i) {
        int ai[] = mUserIds;
        int j = ai.length;
        int k = 0;
        while(k < j)  {
            int l = ai[k];
            if(l != 0)
                mInstaller.createUserData(s, UserId.getUid(l, i), l);
            k++;
        }
    }

    boolean removePackageFolders(int i) {
        if(mInstaller != null)
            mInstaller.removeUserDataDirs(i);
        return true;
    }

    public void removePackageForAllUsers(String s) {
        int ai[] = mUserIds;
        int i = ai.length;
        int j = 0;
        while(j < i)  {
            int k = ai[j];
            if(k != 0)
                mInstaller.remove(s, k);
            j++;
        }
    }

    public boolean removeUser(int i) {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        boolean flag = removeUserLocked(i);
        return flag;
    }

    public void updateUserName(int i, String s) {
        SparseArray sparsearray = mUsers;
        sparsearray;
        JVM INSTR monitorenter ;
        UserInfo userinfo = (UserInfo)mUsers.get(i);
        if(s != null && !s.equals(userinfo.name)) {
            userinfo.name = s;
            writeUserLocked(userinfo);
        }
        return;
    }

    private static final String ATTR_FLAGS = "flags";
    private static final String ATTR_ID = "id";
    private static final String LOG_TAG = "UserManager";
    private static final String TAG_NAME = "name";
    private static final String TAG_USER = "user";
    private static final String TAG_USERS = "users";
    private static final String USER_INFO_DIR;
    private static final String USER_LIST_FILENAME = "userlist.xml";
    private File mBaseUserPath;
    private Installer mInstaller;
    private int mUserIds[];
    private final File mUserListFile;
    private SparseArray mUsers;
    private final File mUsersDir;

    static  {
        USER_INFO_DIR = (new StringBuilder()).append("system").append(File.separator).append("users").toString();
    }
}
