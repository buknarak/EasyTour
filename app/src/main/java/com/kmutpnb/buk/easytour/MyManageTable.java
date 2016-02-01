package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BUK on 01-Feb-16.
 */
public class MyManageTable {

    //Explicate
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;





    public MyManageTable(Context context) {

        //create and connect db
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();



    }//contructor
}//main class
