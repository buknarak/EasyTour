package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BUK on 01-Feb-16.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit
    public static final String database_name = "easyTour.db";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text, " +
            "Status text, " +
            "Lat text," +
            "Lng text);";

    private static final String create_tour_table = "create table tourTABLE (" +
            "_id integer primary key, " +
            "Category text, " +
            "Name text, " +
            "Province text, " +
            "Description text, " +
            "Type text, " +
            "TimeUse text, " +
            "Lat text, " +
            "Lng text, " +
            "TotalScore text);";

    private static final String create_mytour_table = "create table mytourTABLE (" +
            "_id integer primary key, " +
            "Name text, " +
            "TimeUse text, " +
            "DateStart text, " +
            "HrStart text, " +
            "HrEnd text);";

    private static final String create_rating_table = "create table ratingTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Name text, " +
            "Score text);";


    public MyOpenHelper(Context contex) {
        super(contex, database_name, null, database_version);
            //ทำงานก่อน ทำหน้าที่ต่อท่อ

    }// constructor

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(create_user_table);//สร้าง db ที่มีตารางตามนี้
            db.execSQL(create_tour_table);//สร้าง db ที่มีตารางตามนี้
            db.execSQL(create_mytour_table);//สร้าง db ที่มีตารางตามนี้
            db.execSQL(create_rating_table);//สร้าง db ที่มีตารางตามนี้
    }//ถ้าไม่มีก้อสร้าง

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//ถ้ามีจะทำการเช็คเวอร์ และอัพเดท
}// Main Class
