package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class showDetailTourActivity extends AppCompatActivity implements OnClickListener {


    private TextView dateTextView, nameTextView, provinceTextView, typeTextView, timeuseTextView, descripTextView, rateTextView;
    private Button setTimeButton, addMyProgramButton, cancelButton, submitButton;
    private String tourDateString, nameString, provinceString, typeString, timeuseString, descripString;
    private DatePicker changedateDatePicker;
    private int year, month, day;
    static final int DATE_DIALOG_ID = 999;
    private RatingBar ratingBar;

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
        //setCurrentDateView();



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



                //listMyTour();

                break;

            case R.id.btncancel:
                Intent intent = new Intent(this, MainProgramTourActivity.class);
                this.startActivity(intent);
                finish();
                break;


            case R.id.btnRating:
                ShowDialogRating();
                break;
        }


        }

    private void ShowDialogRating() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);
        //rating.setMax(5);
        rating.setMax(5);
        rating.setNumStars(5);
//        rating.setStepSize(0.1f);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Vote!! ");
        popDialog.setView(rating);
              // popDialog.setView(rating.setMax(5));

        // Button OK

        popDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ratingBar.setRating(rating.getRating());

               rateTextView.setText(String.valueOf(rating.getProgress()));
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

    private void listMyTour() {


        final String[] nameStrings;

        final String[] tourDateString;
        final String[] HrStart;
        final String[] HrEnd;

        int intCount = Integer.parseInt(timeuseString);
        while (intCount < 7) {

           // nameStrings[] = getString(nameStrings);


            intCount =  intCount + intCount;




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
//        final int intCount = cursor.getCount();
//            nameString
//            timeuseString
//
//        final String[] nameStrings = new String[intCount];
//        final String[] provinceStrings = new String[intCount];
//
//        for (int i = 0; i < intCount; i++) {
//
//            nameStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
//            provinceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Province));
//            timeUseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));
//            typeStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Type));
//            descripStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Description));
//
//            cursor.moveToNext(); // ขยับ cursor เป็นค่าถัดไป
//        }
//        cursor.close();
//
//        TourAdaptor tourAdapter = new TourAdaptor(ShowProgramTourActivity.this,
//                nameStrings, provinceStrings, timeUseStrings);
//        tourListViewListView.setAdapter(tourAdapter);
//
//        tourListViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(ShowProgramTourActivity.this, showDetailTourActivity.class );//โยนค่าไปหน้าใหม่
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

    private void updateToMySQL() {

        //change policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
        StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http
        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_TimeUse, timeuseString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_DateStart, tourDateString ));
            //objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrStart, positionString));
           // objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrEnd, positionString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_mytour_buk");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(showDetailTourActivity.this, "อัพเดทข้อมูลเรียบร้อยแล้ว",
                    Toast.LENGTH_SHORT).show();  //Toast คือคำสั่งข้อความที่ขึ้นมาแล้วหายไป
            finish();

        } catch (Exception e) {
            Toast.makeText(showDetailTourActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                    Toast.LENGTH_SHORT).show();//short = 4 วิ

        }



    }

}//main class