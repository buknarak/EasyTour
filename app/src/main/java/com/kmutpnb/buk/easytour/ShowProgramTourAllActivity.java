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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour_all);

        bindwidget();

      showView();
    }

    private void showView() {

        showCatTextView.setText(getResources().getString(R.string.listtour));
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE", null);
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
                startActivity(intent);

            }//on item
        });


    }

    private void bindwidget() {

        showCatTextView = (TextView) findViewById(R.id.textView7);
        tourListViewListView = (ListView) findViewById(R.id.listView);
    }
}



