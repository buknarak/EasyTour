package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ShowProgramTourActivity extends AppCompatActivity {


    //explicite
    private double userLatADouble, userLngAdouble;
    private String categoryString, category, uname, choosedateString, statusString, placeString, nameString,meIDString;
    private TextView showCatTextView;
    private ListView tourListViewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour);


        choosedateString = getIntent().getStringExtra("chooseDate");
        statusString = getIntent().getStringExtra("status");
        placeString = getIntent().getStringExtra("place");
        nameString  = getIntent().getStringExtra("name");
        meIDString = getIntent().getStringExtra("MeID");

        //bindWindget;
        bindwidget();

        //recive lat lng
        receiveAndSep();

        if (placeString == "place".trim()) {

            showView1();
        } else{
            //show view
            showView();
        }


    }//main method

    private void showView1() {

        showCatTextView.setText(getResources().getString(R.string.listtour) + " " + category);
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE WHERE Category = " + "'" + categoryString + "' ORDER BY NAME ASC", null);
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

        TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourActivity.this,
                nameStrings, provinceStrings, timeUseStrings);
        tourListViewListView.setAdapter(tourAdapter);

        tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ShowProgramTourActivity.this, ShowDetailPlaceActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameStrings[i]);
                intent.putExtra("Province", provinceStrings[i]);
                intent.putExtra("Type", typeStrings[i]);
                intent.putExtra("TimeUse", timeUseStrings[i]);
                intent.putExtra("Descrip", descripStrings[i]);
                intent.putExtra("Date",choosedateString);
                intent.putExtra("Uname",uname);
                intent.putExtra("status", statusString);
                intent.putExtra("ID", i);
                intent.putExtra("Img", imageStrings[i]);
                startActivity(intent);

            }//on item
        });


    }

    private void bindwidget() {


        showCatTextView = (TextView) findViewById(R.id.textView7);
        tourListViewListView = (ListView) findViewById(R.id.listView);


       uname = getIntent().getStringExtra("Uname");


    }

    private void showView() {

        showCatTextView.setText(getResources().getString(R.string.listtour) + " " + category);
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE WHERE Category = " + "'" + categoryString + "' ORDER BY NAME ASC", null);
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

             TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourActivity.this,
                nameStrings, provinceStrings, timeUseStrings);
        tourListViewListView.setAdapter(tourAdapter);

        tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ShowProgramTourActivity.this, showDetailTourActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameStrings[i]);
                intent.putExtra("Province", provinceStrings[i]);
                intent.putExtra("Type", typeStrings[i]);
                intent.putExtra("TimeUse", timeUseStrings[i]);
                intent.putExtra("Descrip", descripStrings[i]);
                intent.putExtra("Date",choosedateString);
                intent.putExtra("Uname",uname);
                intent.putExtra("name", nameString);
                intent.putExtra("MeID", meIDString);
                intent.putExtra("status", statusString);
                intent.putExtra("ID", i);
                startActivity(intent);

            }//on item
        });

    }


    private void receiveAndSep() {


        userLatADouble = getIntent().getDoubleExtra("Lat", HubServiceActivity.centerLat);//10power -6 ขยับจากจุดศูนย์กลางนิดนึง
        userLngAdouble = getIntent().getDoubleExtra("Lng", HubServiceActivity.centerLng);

        //find w or e
        if (userLatADouble > HubServiceActivity.centerLat) {

            if (userLatADouble > HubServiceActivity.centerLng) {

                categoryString = "NE";
                category = "ภาคตะวันออกเฉียงเหนือ";
            } else {

                categoryString = "NW";
                category = "ภาคเหนือ";
            }

        } else {
            //eath
            if (userLngAdouble > HubServiceActivity.centerLng) {
                //SE
                categoryString = "SE";
                category = "ภาคตะวันออก";
            } else {
                if (userLatADouble < 10.978161) {

                    categoryString = "SS";
                    category = "ภาคใต้";
                } else {
                    //SW
                    categoryString = "SW";
                    category = "ภาคกลาง";
                }

            }


        }//if 1

        Log.d("11/03/59", "category = " + categoryString);

    }//receiveandSep


}//main class