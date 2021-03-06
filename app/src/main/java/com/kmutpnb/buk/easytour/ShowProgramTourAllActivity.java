package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowProgramTourAllActivity extends AppCompatActivity {


    /** Called when the activity is first created. */

    private TextView showCatTextView;
    private ListView tourListViewListView;
    private String statusString, placeString,uname;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour_all);

        statusString = getIntent().getStringExtra("status");
        placeString = getIntent().getStringExtra("place");
        uname = getIntent().getStringExtra("Uname");

        bindwidget();

        if (placeString == "place".trim()) {

            showView1();
        } else{
            //show view
            showView();
        }
    }

    private void showView1() {

        showCatTextView.setText("แหล่งท่องเที่ยวทั้งหมด");
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE ORDER BY NAME ASC", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameStrings = new String[intCount];
        final String[] provinceStrings = new String[intCount];
        final String[] typeStrings = new String[intCount];
        final String[] descripStrings = new String[intCount];
        final String[] timeUseStrings = new String[intCount];
        final String[] imageStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            provinceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Province));
            timeUseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));
            typeStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Type));
            descripStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Description));
            imageStrings [i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Image));
            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป
        }
        cursor.close();

        TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourAllActivity.this,
                nameStrings, provinceStrings, timeUseStrings);
        tourListViewListView.setAdapter(tourAdapter);

        tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ShowProgramTourAllActivity.this, ShowDetailPlaceActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameStrings[i]);
                intent.putExtra("Province", provinceStrings[i]);
                intent.putExtra("Type", typeStrings[i]);
                intent.putExtra("TimeUse", timeUseStrings[i]);
                intent.putExtra("Descrip", descripStrings[i]);
                intent.putExtra("status", statusString);
                intent.putExtra("Uname", uname);
                intent.putExtra("Img", imageStrings[i]);
                startActivity(intent);

            }//on item
        });



    }

    private void showView() {

        showCatTextView.setText("แหล่งท่องเที่ยวทั้งหมด");
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE ORDER BY NAME ASC", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameStrings = new String[intCount];
        final String[] provinceStrings = new String[intCount];
        final String[] typeStrings = new String[intCount];
        final String[] descripStrings = new String[intCount];
        final String[] timeUseStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            provinceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Province));
            timeUseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));
            typeStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Type));
            descripStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Description));

            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป
        }
        cursor.close();

        TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourAllActivity.this,
                nameStrings, provinceStrings, timeUseStrings);
        tourListViewListView.setAdapter(tourAdapter);

        tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ShowProgramTourAllActivity.this, showDetailTourActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameStrings[i]);
                intent.putExtra("Province", provinceStrings[i]);
                intent.putExtra("Type", typeStrings[i]);
                intent.putExtra("TimeUse", timeUseStrings[i]);
                intent.putExtra("Descrip", descripStrings[i]);
                intent.putExtra("status", statusString);
                startActivity(intent);

            }//on item
        });


    }

    private void bindwidget() {

        showCatTextView = (TextView) findViewById(R.id.textView7);
        tourListViewListView = (ListView) findViewById(R.id.listView);
    }
}



