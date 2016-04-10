package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class showDetailTourActivity extends AppCompatActivity implements OnClickListener {


    private TextView dateTextView, nameTextView, provinceTextView, typeTextView, timeuseTextView, descripTextView, rateTextView;
    private Button setTimeButton, addMyProgramButton, cancelButton, submitButton;
    private String tourDateString, nameString, provinceString, typeString, timeuseString, descripString,hrStart,hrStop, Uname, raingString;
    private DatePicker changedateDatePicker;
    private int year, month, day, timeTour, timetourall = 6;
    static final int DATE_DIALOG_ID = 999;
    private RatingBar ratingBar;
    private RelativeLayout rateRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_tour);

        //bindwidget
        bindWidget();

        //get current time andd show
        getTimeShow();


        //show textview
        showTextView();

        //Button Controller
        buttonController();
        setCurrentDateView();

        timeTour = Integer.parseInt(timeuseString.trim());

    }//main method

    private void setCurrentDateView() {


        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //set current view

        dateTextView.setText(new StringBuilder()
                //month base is 0 just +1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

        //set curent date into datepicker
        //dpChange.init(year, month, day, null);
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


    private void buttonController() {

       // setTimeButton.setOnClickListener(this);
       addMyProgramButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    private void showTextView() {

        nameString = getIntent().getStringExtra("Name");
        provinceString = getIntent().getStringExtra("Province");
        typeString = getIntent().getStringExtra("Type");
        timeuseString = getIntent().getStringExtra("TimeUse");
        descripString = getIntent().getStringExtra("Descrip");

        nameTextView.setText(nameString);
        provinceTextView.setText(provinceString);
        typeTextView.setText(typeString);
        timeuseTextView.setText(timeuseString);
        descripTextView.setText(descripString);
    }

    private void getTimeShow() {

        DateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        tourDateString = dateFormat.format(date);
        showDate(tourDateString);

    }//get time

    private void showDate(String showDate) {
        dateTextView.setText(showDate);

    }//showdate

    private void bindWidget() {

        dateTextView = (TextView) findViewById(R.id.tvdate);
        nameTextView = (TextView) findViewById(R.id.tvnamet);
        provinceTextView = (TextView) findViewById(R.id.tvprovince);
        typeTextView = (TextView) findViewById(R.id.tvtype);
        timeuseTextView = (TextView) findViewById(R.id.tvtimeuse);
        descripTextView = (TextView) findViewById(R.id.tvdescrip);
        addMyProgramButton = (Button) findViewById(R.id.btnaddmyprograme);
        cancelButton = (Button) findViewById(R.id.btncancel);
        changedateDatePicker = (DatePicker) findViewById(R.id.dpChange);
        setTimeButton = (Button) findViewById(R.id.button9);

        submitButton = (Button) findViewById(R.id.btnRating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rateTextView = (TextView) findViewById(R.id.tvRateview);

        Uname = getIntent().getStringExtra("Uname");

    }

    private DatePickerDialog.OnDateSetListener dataPickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            //set selected date into textview
            dateTextView.setText(new StringBuilder().append(day)
                    .append("-").append(month + 1).append("-").append(year)
                    .append(" "));
//        // set selected date into datepicker
            // dpChange.init(year, month, day, null);

        }

    };

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btnaddmyprograme:

                tourDateString = dateTextView.getText().toString();

//               Log.d("xx", timeuseString);
//                Log.d("ADebugTag", "Value: " + Integer.toString(timeTour));

                int timetotal,time,timetotal1;
                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                        MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT TimeUse FROM mytourTABLE", null);
                cursor.moveToFirst();
                int intcount = cursor.getCount();

                for (int i=0 ; i <intcount;i++) {


                    String strtimeuse = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));

                    time = Integer.parseInt(strtimeuse.trim());
                    timetotal = time + 0;
                    if (i == 1) {
                        timetotal1 = time + timetotal;
                        Log.d("test", "timetal " + timetotal1);
                    }
                }//for




                listMyTour();
                upToSQLite();

                break;

            case R.id.btncancel:
                Intent intent = new Intent(this, ShowProgramTourActivity.class);
                this.startActivity(intent);
                finish();
                break;


            case R.id.btnRating:
                ShowDialogRating();
                break;
        }


        }

    private void upToSQLite() {

        MyManageTable objMyManageTable = new MyManageTable(this);
        objMyManageTable.addMyTour(nameString, timeuseString, tourDateString, hrStart, hrStop);
    }

    private void ShowDialogRating() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);

        rating.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        rating.setMax(7);
        rating.setNumStars(7);
        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Vote!! ");
        popDialog.setView(rating);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ratingBar.setRating(rating.getRating());
               rateTextView.setText(String.valueOf(rating.getProgress()));
                raingString = rateTextView.getText().toString();
             //   updateToSQLiteRating();
                updateToDB();
                           dialog.dismiss();
          }
                })
        // Button Cancel
             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();
                 }
             });
        popDialog.create();
        popDialog.show();
    }

    private void updateToDB() {

            try {
                //change policy
                StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                        .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
                StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http

                ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
                objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_user, Uname));
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_Score, raingString));

                HttpClient objHttpClient = new DefaultHttpClient();
                HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_rating_buk.php");
                objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
                objHttpClient.execute(objHttpPost);

            } catch (Exception e) {
                Toast.makeText(showDetailTourActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                        Toast.LENGTH_SHORT).show();//short = 4 วิ
            }
        }

    private void updateToSQLiteRating() {

        MyManageTable objMyManageTable = new MyManageTable(this);
        objMyManageTable.addRating(Uname, nameString, raingString);

    }

    private void listMyTour() {

//        int timeuseint = Integer.parseInt(timeuseString);

      //  timeTour = Integer.parseInt(timeuseString);



        Intent objIntent = new Intent(showDetailTourActivity.this, ConfirmMytourActivity.class);
        objIntent.putExtra("date", tourDateString);
        objIntent.putExtra("Name", nameString);
        objIntent.putExtra("HrStart", hrStart);
        objIntent.putExtra("HrStop", hrStop);
        objIntent.putExtra("TimeUse", timeuseString);
        objIntent.putExtra("timetour", timeTour);
        startActivity(objIntent);

//        while (timeTour < 6) {

//            AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
//            objBuilder.setTitle("ต้องการเพิ่มรายการทัวร์นี้ใช่หรือไม่");
//            objBuilder.setMessage("สถานที่ท่องเที่ยว = " + nameString);
//            objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    //update to mysql
//                    updateToMySQL();
//                    dialogInterface.dismiss(); //ทำให้ pop up หายไป
//                }
//            });
//            objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    dialogInterface.dismiss();
//                }
//            });
//
//            objBuilder.show();
    }


}//main class