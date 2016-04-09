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


    private TextView showCatTextView;
    private ListView myourListViewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_tour);

        //bindWindget;
        bindwidget();
      //show view
        showView();
    }


    private void bindwidget() {

        showCatTextView = (TextView) findViewById(R.id.textView7);
        myourListViewListView = (ListView) findViewById(R.id.lvMytour);

    }

    private void showView() {

        //showCatTextView.setText(getResources().getString(R.string.listtour) + " " + category);
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE", null);
        cursor.moveToFirst();

        final int intCount = cursor.getCount();
        final String[] MyTourNameStrings = new String[intCount];
        final String[] MyTimeUseStrings = new String[intCount];
        final String[] DateStartStrings = new String[intCount];
        final String[] HrStartStrings = new String[intCount];
        final String[] HrEndStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            MyTourNameStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            MyTimeUseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));
            DateStartStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_DateStart));
            HrStartStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_HrStart));
            HrEndStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_HrEnd));

            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป
        }
        cursor.close();

        MytourAdaptor mytourAdapter = new MytourAdaptor(ShowMyTourActivity.this,
                DateStartStrings, HrStartStrings, MyTourNameStrings);
        myourListViewListView.setAdapter(mytourAdapter);

       // tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(ShowMyTourActivity.this, showDetailTourActivity.class );//โยนค่าไปหน้าใหม่
//                intent.putExtra("Name", nameStrings[i]);
//                intent.putExtra("Province", provinceStrings[i]);
//                intent.putExtra("Type", typeStrings[i]);
//                intent.putExtra("TimeUse", timeUseStrings[i]);
//                intent.putExtra("Descrip", descripStrings[i]);
//                startActivity(intent);
//
//            }//on item
//        });

    }

}
