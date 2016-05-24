package com.kmutpnb.buk.easytour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class ShowMyTourActivity extends AppCompatActivity {


    static final int DATE_DIALOG_ID = 999;
    private ListView mytourListViewListView;
    private Button btnchoosedate;
    private DatePicker datePicker;
    private int year, month, day;
    private TextView textViewPro;
    private String dateString, curdateString, status,uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_tour);

        bindwicket();
        setdateshow();

        status = getIntent().getStringExtra("status1");
        if (status == null ) {
            status = "0";
        }

        int i = Integer.parseInt(status.trim());

        if (i == 0) {
            ///  0 "สถานะ : นักท่องเที่ยว"
            Log.d("260459", " สถานะ "+ i);
            showViewuser();
        } else {
            Log.d("260459", " สถานะ "+ i);
            showView();
        }
        ///showView();
    }

    private void showViewuser() {
        synMytourtable();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        // Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE ", null);
        //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = " + "'" + dateString + "'", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE INNER JOIN tourTABLE on mytourTABLE.Name = tourTABLE.Name WHERE DateStart = current_date" , null);
       // Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = current_date", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameString = new String[intCount];
        final String[] dateStrings = new String[intCount];
        final String[] hrStartString = new String[intCount];
        final String[] id = new String[intCount];
        final String[] type = new String[intCount];
        final String[] Descrip = new String[intCount];
        final String[] Img = new String[intCount];
        final String[] province = new String[intCount];
        final String[] timeuse = new String[intCount];


        for (int i = 0; i < intCount; i++) {


            nameString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            dateStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_DateStart));
            hrStartString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_HrStart));
            id [i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_id));
            type[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Type));
            Descrip[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Description));
            Img[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Image));
            province[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Province));
            timeuse[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));

            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป

            Log.d("tree", "int = " + i);

            curdateString = dateStrings[i];
        }
        cursor.close();

        MytourAdaptor mytourAdapter = new MytourAdaptor(ShowMyTourActivity.this,
                dateStrings, hrStartString, nameString);
        mytourListViewListView.setAdapter(mytourAdapter);
        textViewPro.setText(getResources().getString(R.string.listtourdate) + " " + curdateString);
        mytourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowMyTourActivity.this, ShowDetailPlaceActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameString[i]);
                intent.putExtra("Province", province[i]);
                intent.putExtra("Type", type[i]);
                intent.putExtra("TimeUse", timeuse[i]);
                intent.putExtra("Descrip", Descrip[i]);
                intent.putExtra("Img", Img[i]);
                intent.putExtra("Uname", uname);
                startActivity(intent);
            }//on item
        });


    }

    private void bindwicket() {

        mytourListViewListView = (ListView) findViewById(R.id.lvMytour);
        btnchoosedate = (Button) findViewById(R.id.btnchoosedate);
        datePicker = (DatePicker) findViewById(R.id.dpmytour);
        textViewPro = (TextView) findViewById(R.id.textView7);

        uname = getIntent().getStringExtra("Uname");

        //nameString = "หาดสิชล";
    }

    private void setdateshow() {

        btnchoosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                //set date picker as current date


                return new DatePickerDialog(this, dataPickerListener,
                        year, month, day);
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener dataPickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            //set selected date into textview
            textViewPro.setText(new StringBuilder().append(day)
                    .append("/").append(month + 1).append("/").append(year)
                    .append(" "));

//        // set selected date into datepicker
            // dpChange.init(year, month, day, null);

            dateString = textViewPro.getText().toString();

            Log.d("tree", dateString);
            //  showView();
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            // Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE ", null);
            //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = " + "'" + dateString + "'", null);
            //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE Name = "หาดสิชล" " , null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = current_date", null);
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

                Log.d("tree", "int = " + i);


            }
            cursor.close();

            MytourAdaptor mytourAdapter = new MytourAdaptor(ShowMyTourActivity.this,
                    dateStrings, hrStartString, nameString);
            mytourListViewListView.setAdapter(mytourAdapter);

        }

    };

    private void showView() {

        synMytourtable();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        // Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE ", null);
        //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = " + "'" + dateString + "'", null);
        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE Name = "หาดสิชล" " , null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = current_date", null);
        cursor.moveToFirst();


        final int intCount = cursor.getCount();

        final String[] nameString = new String[intCount];
        final String[] dateStrings = new String[intCount];
        final String[] hrStartString = new String[intCount];
        final String[] id = new String[intCount];


        for (int i = 0; i < intCount; i++) {


            nameString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            dateStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_DateStart));
            hrStartString[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_HrStart));
            id [i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_id));

            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป

            Log.d("tree", "int = " + i);

            curdateString = dateStrings[i];
        }
        cursor.close();

        MytourAdaptor mytourAdapter = new MytourAdaptor(ShowMyTourActivity.this,
                dateStrings, hrStartString, nameString);
        mytourListViewListView.setAdapter(mytourAdapter);
        textViewPro.setText(getResources().getString(R.string.listtourdate) + " " + curdateString);
        mytourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(ShowMyTourActivity.this, DetailConfirmActivity.class );//โยนค่าไปหน้าใหม่
                intent.putExtra("Name", nameString[i]);
                intent.putExtra("Date",  dateStrings[i]);
                intent.putExtra("Hr",  hrStartString[i]);
                intent.putExtra("id",  id[i]);
                startActivity(intent);
            }
        });



    }

    private void synMytourtable() {

////        delete UserTable
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManageTable.table_mytour, null,null);

        //// Connect Protocal
        StrictMode.ThreadPolicy threadPolicy  =  new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        InputStream inputStream =null;
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_get_mytour_buk.php");
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

                String Name = jsonobject.getString(MyManageTable.column_name);
                String timeuse = jsonobject.getString(MyManageTable.column_TimeUse);
                String date = jsonobject.getString(MyManageTable.column_DateStart);
                String hrstart = jsonobject.getString(MyManageTable.column_HrStart);
                String hrstop = jsonobject.getString(MyManageTable.column_HrEnd);


                MyManageTable myManageTable = new MyManageTable(this);
                myManageTable.addMyTour(Name,timeuse,date,hrstart,hrstop);
            }

        } catch (Exception e) {
            Log.d("31", "Update ==> " + e.toString());
        }

    }

}
