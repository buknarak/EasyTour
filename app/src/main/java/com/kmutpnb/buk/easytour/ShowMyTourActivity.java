package com.kmutpnb.buk.easytour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class ShowMyTourActivity extends AppCompatActivity {


    static final int DATE_DIALOG_ID = 999;
    private ListView mytourListViewListView;
    private Button btnchoosedate;
    private DatePicker datePicker;
    private int year, month, day;
    private TextView textViewPro;
    private String dateString, nameString, curdateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_tour);

        bindwicket();
        setdateshow();
        showView();


    }

    private void bindwicket() {

        mytourListViewListView = (ListView) findViewById(R.id.lvMytour);
        btnchoosedate = (Button) findViewById(R.id.btnchoosedate);
        datePicker = (DatePicker) findViewById(R.id.dpmytour);
        textViewPro = (TextView) findViewById(R.id.textView7);

        nameString = "หาดสิชล";
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

}
