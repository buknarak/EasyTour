package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ShowUserActivity extends AppCompatActivity {

    //private TextView userTextView, passTextView, nameTextView, positionTextView, passwordTextView;

   // private TextView showUserTextView;
    private ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);


        //bindWindget;

       //bindwidget();
       showView();

    }

    private void showView() {


        //read or where
       SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
               MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameString = new String[intCount];
        final String[] userStrings = new String[intCount];
        final String[] positionString = new String[intCount];
        final String[] passString = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            userStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_user));
           positionString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_status));

            int intStatus = Integer.parseInt(positionString[i]);
             positionString[i]= null;
            switch (intStatus) {
                case 0:
                    positionString[i] = "นักท่องเที่ยว";
                    break;
                case 1:
                    positionString[i] = "มัคคุเทศน์";
                    break;

            }

            passString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_password));


            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป


        }
        cursor.close();

        UserAdaptor userAdaptor = new UserAdaptor(ShowUserActivity.this, nameString, userStrings, positionString, passString);

        userListView = (ListView) findViewById(R.id.listViewListUser);
        userListView.setAdapter(userAdaptor);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowUserActivity.this, ShowDetaiUserActivity.class );//โยนค่าไปหน้าใหม่
            intent.putExtra("Name", nameString[i]);
            intent.putExtra("User", userStrings[i]);
            intent.putExtra("Status", positionString[i]);
            intent.putExtra("Pass", passString[i]);
            startActivity(intent);
            }
        });
    }
//    TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourActivity.this,
//            nameStrings, provinceStrings, timeUseStrings);
//    tourListViewListView.setAdapter(tourAdapter);
//
//    tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            Intent intent = new Intent(ShowProgramTourActivity.this, showDetailTourActivity.class );//โยนค่าไปหน้าใหม่
//            intent.putExtra("Name", nameStrings[i]);
//            intent.putExtra("Province", provinceStrings[i]);
//            intent.putExtra("Type", typeStrings[i]);
//            intent.putExtra("TimeUse", timeUseStrings[i]);
//            intent.putExtra("Descrip", descripStrings[i]);
//            startActivity(intent);


    private void bindwidget() {

      //  showUserTextView = (TextView) findViewById(R.id.tvShowUser);
       //userListView = (ListView) findViewById(R.id.listViewUser);


   }//bind wicket
}//main
