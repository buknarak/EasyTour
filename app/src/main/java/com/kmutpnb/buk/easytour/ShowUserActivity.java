package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShowUserActivity extends AppCompatActivity {

    //private TextView userTextView, passTextView, nameTextView, positionTextView, passwordTextView;

   // private TextView showUserTextView;
    private ListView userListView;
    private String status,status1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);


        //status = getIntent().getStringExtra("status");
        status1 = getIntent().getStringExtra("status1");

        //bindWindget;

       // Log.d("260459", status);

//        int i = Integer.parseInt(status.trim());
//
//        if (i == 0) {
//            ///  0 "สถานะ : นักท่องเที่ยว"
//          //  Log.d("260459", " สถานะ "+ i);
////            showViewuser();
//            showView();
//        } else {
//          //  Log.d("260459", " สถานะ "+ i);
//            showView();
//        }

//        showViewuser();

        showView();
    }

    private void showViewuser() {

        synUsertable();
        //read or where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE ORDER BY Status DESC", null);
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

           // Log.d("asd", userStrings[i]);

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

//        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(ShowUserActivity.this, ShowDetaiUserActivity.class );//โยนค่าไปหน้าใหม่
//                intent.putExtra("Name", nameString[i]);
//                intent.putExtra("User", userStrings[i]);
//                intent.putExtra("Status", positionString[i]);
//                intent.putExtra("Pass", passString[i]);
//                startActivity(intent);
//            }
//        });

    }

    private void showView() {


        synUsertable();
        //read or where
       SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
               MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE ORDER BY Status DESC", null);
        cursor.moveToFirst();

        final int intCount = cursor.getCount();

        final String[] nameString = new String[intCount];
        final String[] userStrings = new String[intCount];
        final String[] positionString = new String[intCount];
        final String[] passString = new String[intCount];
        final String[] emailStrings = new String[intCount];
        final String[] telStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            userStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_user));
           positionString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_status));
            emailStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_email));
            telStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_phone));

            Log.d("asd", userStrings[i]);

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
                    intent.putExtra("Email", emailStrings[i]);
                    intent.putExtra("Tel", telStrings[i]);
                    intent.putExtra("Status1",status1);
                    startActivity(intent);
                }
            });

    }

    private void synUsertable() {

        ///        delete UserTable
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManageTable.table_user, null,null);

        //// Connect Protocal
        StrictMode.ThreadPolicy threadPolicy  =  new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        InputStream inputStream =null;
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_get_user_buk.php");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        }catch (Exception e){

            Log.d("31", "Input ==> " +e.toString());

        }

        String strJson = null;
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = bufferedReader.readLine())!= null) {

                stringBuilder.append(strLine);
            }
            inputStream.close();
            strJson = stringBuilder.toString();

        }catch (Exception e){
            Log.d("31", "Input ==> " + e.toString());
        }

        try {
            JSONArray jsonArray =new JSONArray(strJson);

            for (int i=0; i<jsonArray.length();i++ ){


                JSONObject jsonobject = jsonArray.getJSONObject(i);

                String strUser = jsonobject.getString(MyManageTable.column_user);
                String strPassword = jsonobject.getString(MyManageTable.column_password);
                String strName = jsonobject.getString(MyManageTable.column_name);
                String strStatus = jsonobject.getString(MyManageTable.column_status);
                String strLat = jsonobject.getString(MyManageTable.column_Lat);
                String strLng = jsonobject.getString(MyManageTable.column_Lng);
                String strPhone = jsonobject.getString(MyManageTable.column_phone);
                String strEmail = jsonobject.getString(MyManageTable.column_email);
                String strImage = jsonobject.getString(MyManageTable.column_Image);

                MyManageTable myManageTable = new MyManageTable(this);
                myManageTable.addUser(strUser,strPassword,strName,strStatus,strLat,strLng,strPhone,strEmail,strImage);
            }

        } catch (Exception e) {
            Log.d("31", "Update ==> " + e.toString());
        }


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
