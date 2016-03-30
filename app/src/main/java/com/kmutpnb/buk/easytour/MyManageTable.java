package com.kmutpnb.buk.easytour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public static final String table_tour = "tourTABLE";
    public static final String column_Category = "Category";
    public static final String column_Province = "Province";
    public static final String column_Description = "Description";
    public static final String column_Type = "Type";
    public static final String column_TimeUse = "TimeUse";
    public static final String column_Lat = "Lat";
    public static final String column_Lng = "Lng";

    public static final String table_mytour = "myTourTABLE";
    public static final String column_DateStart = "DateStart";
    public static final String column_HrStart = "HrStart";
    public static final String column_HrEnd = "HrEnd";


    public MyManageTable(Context context) {

        //create and connect db
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();


    }//contructor

    public long addMyTour(String strName,
                          String strTimeUse,
                          String strDateStart,
                          String strHrStart,
                          String strHrEnd){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, strName);
        contentValues.put(column_TimeUse, strTimeUse);
        contentValues.put(column_DateStart, strDateStart);
        contentValues.put(column_HrStart, strHrStart);
        contentValues.put(column_HrEnd, strHrEnd);

        return writeSqLiteDatabase.insert(table_mytour, null, contentValues);
    }//string to integer

    public long addTour(String strCategory,
                        String strName,
                        String strProvince,
                        String strDescription,
                        String strType,
                        String strTimeUse,
                        String strLat,
                        String strLng) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_Category, strCategory);
        contentValues.put(column_name, strName);
        contentValues.put(column_Province, strProvince);
        contentValues.put(column_Description, strDescription);
        contentValues.put(column_Type, strType);
        contentValues.put(column_TimeUse, strTimeUse);
        contentValues.put(column_Lat, strLat);
        contentValues.put(column_Lng, strLng);

        return writeSqLiteDatabase.insert(table_tour, null, contentValues);
    }

    public String[] searchUser(String strUser) {


        try{

            String[] resultStrings = null;
            Cursor objCursor = readSqLiteDatabase.query(table_user,
                    new String[]{column_id,column_user, column_password, column_name, column_status},
                   column_user + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);//null พวก security
            if (objCursor != null) {

                if (objCursor.moveToFirst()) {

                    resultStrings = new String[5]; //5 คอลัม
                    for (int i=0;i<5;i++) {

                        resultStrings[i] = objCursor.getString(i);


                    } //for

                }//if ตัวสอง หาจากบนลงล่าง

            } //if ตัวแรก ถ้าตารางว่างป่าวไม่คุย

            objCursor.close(); //คืนแรม

            return resultStrings;

        }catch (Exception e){

            return null;//return ความว่างป่าว
        }

    }

    public long addUser(String strUser,
                        String strPassword,
                        String strName,
                        String strStatus,
                        String strLat,
                        String strLng) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_user, strUser);
        objContentValues.put(column_password, strPassword);
        objContentValues.put(column_name, strName);
        objContentValues.put(column_status, strStatus);
        objContentValues.put(column_Lat, strLat);
        objContentValues.put(column_Lng, strLng);

        return writeSqLiteDatabase.insert(table_user, null, objContentValues); //แปลง string to long int
    }

}//main class
