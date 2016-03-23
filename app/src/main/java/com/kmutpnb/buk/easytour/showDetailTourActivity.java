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


    private TextView dateTextView, nameTextView, provinceTextView, typeTextView, timeuseTextView, descripTextView;
    private Button setTimeButton, addMyProgramButton, cancelButton;
    private String tourDateString, nameString, provinceString, typeString, timeuseString, descripString;
    private DatePicker changedateDatePicker;
    private int year, month, day;
    static final int DATE_DIALOG_ID = 999;

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

                AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
                objBuilder.setTitle("ต้องการเพิ่มรายการทัวร์นี้ใช่หรือไม่");
                objBuilder.setMessage("สถานที่ท่องเที่ยว = " + nameString);
                objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //update to mysql
                        updateToMySQL();
                        dialogInterface.dismiss(); //ทำให้ pop up หายไป
                    }
                });
                objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                objBuilder.show();
                break;

            case R.id.btncancel:
                Intent intent = new Intent(this, ShowProgramTourActivity.class);
                this.startActivity(intent);
                finish();
                break;
        }


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