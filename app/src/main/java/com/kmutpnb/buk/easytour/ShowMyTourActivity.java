package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowMyTourActivity extends AppCompatActivity {


    private ListView mytourListViewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_tour);

        mytourListViewListView = (ListView) findViewById(R.id.lvMytour);
      //show view
        showView();

    }

    private void showView() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameString = new String[intCount];
        final String[] dateStrings = new String[intCount];
        final String[] hrStartString = new String[intCount];


        for (int i = 0; i < intCount; i++) {

            nameString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            dateStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_DateStart));
            hrStartString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_HrStart));

            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป

        }
        cursor.close();

        MytourAdaptor mytourAdapter = new MytourAdaptor(ShowMyTourActivity.this,
               dateStrings,hrStartString, nameString);
        mytourListViewListView.setAdapter(mytourAdapter);

    }

}
