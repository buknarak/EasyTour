package com.kmutpnb.buk.easytour;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    //explicate
    private MyManageTable objMyManageTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request db
        objMyManageTable = new MyManageTable(this);

        //test add value
        //testAddValue();
        //deleteAllSQlite

        deleteAllSQlite();


    }//Main Method

    private void deleteAllSQlite() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManageTable.table_user, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว
    }

    private void testAddValue() {

        objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus");

    }//void คือ ไม่ต้องรีเทริืค่า
}//MainClass
