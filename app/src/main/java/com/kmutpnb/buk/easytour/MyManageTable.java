package com.kmutpnb.buk.easytour;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BUK on 01-Feb-16.
 */
public class MyManageTable {

    //Explicate
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String table_user = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_user = "User";
    public static final String column_password = "Password";
    public static final String column_name = "Name";
    public static final String column_status = "Status";



    public MyManageTable(Context context) {

        //create and connect db
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();


    }//contructor

    public long addUser(String strUser,
                        String strPassword,
                        String strName,
                        String strStatus) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_user, strUser);
        objContentValues.put(column_password, strPassword);
        objContentValues.put(column_name, strName);
        objContentValues.put(column_status, strStatus);

        return writeSqLiteDatabase.insert(table_user, null, objContentValues); //แปลง string to long int
    }

}//main class
