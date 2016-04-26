package com.kmutpnb.buk.easytour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChooseTimeActivity extends AppCompatActivity {

    private Button btnChooseTime, btnChooseDate;
    private Spinner HrStartSpinner, HrEndSpinner;
    private String Hrstart, Hrend;
    private String tourDateString, nameString, timeuseString,timeTour;
    private String dateChooseString,Uname, nameString1,meIDString ;
    private TextView tvtourdatechoose;
    private DatePicker changedateDatePicker;
    private int year, month, day;
    static final int DATE_DIALOG_ID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);


        nameString1  = getIntent().getStringExtra("name");
        meIDString = getIntent().getStringExtra("MeID");
        bindwicket();

        //getTimeShow();
       setCurrentDateView();
        addListITEM();

        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tourDateString = tvtourdatechoose.getText().toString();
                Hrstart = String.valueOf(HrStartSpinner.getSelectedItem());
                Hrend = String.valueOf(HrEndSpinner.getSelectedItem());
                Log.d("abb", Hrstart);
                Log.d("abb", Hrend);


                listMyTour();
                upToSQLite();

            }
        });
    }
    private void setCurrentDateView() {


        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String formatted = format1.format(c.getTime());

        tvtourdatechoose.setText(formatted);
        //set current view

//        tvtourdatechoose.setText(new StringBuilder()
//                //month base is 0 just +1
//                .append(year).append("-").append(month + 1).append("-")
//                .append(day).append(" "));

//        set curent date into datepicker
//        dpChange.init(year, month, day, null);
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
            month = selectedMonth+1;
            day = selectedDay;

            String formattedDay = (String.valueOf(day));
            String formattedMonth = (String.valueOf(month));

            if(day < 10)
            {
                formattedDay = "0" + day;
            }

            if(month< 10)
            {
                formattedMonth = "0" + month;
            }

            //set selected date into textview
            tvtourdatechoose.setText(new StringBuilder().append(year)
                    .append("-").append(formattedMonth).append("-").append(formattedDay)
                    .append(" "));
//        // set selected date into datepicker
            // dpChange.init(year, month, day, null);

        }

    };


    private void upToSQLite() {

        MyManageTable objMyManageTable = new MyManageTable(this);
        objMyManageTable.addtourtmp(nameString, timeuseString, tourDateString, Hrstart, Hrend);
    }

    private void listMyTour() {

        Intent objintent = new Intent(ChooseTimeActivity.this, ConfirmMytourActivity.class );//โยนค่าไปหน้าใหม่
        objintent.putExtra("date", tourDateString);
        objintent.putExtra("Name", nameString);
        objintent.putExtra("HrStart", Hrstart);
        objintent.putExtra("HrStop", Hrend);
        objintent.putExtra("TimeUse", timeuseString);
        objintent.putExtra("name", nameString1);
        objintent.putExtra("MeID", meIDString);
        startActivity(objintent);

    }

    private void addListITEM() {

        ArrayList<String> startList = new ArrayList<String>();
        startList.add("8:00");
        startList.add("9:00");
        startList.add("10:00");
        startList.add("11:00");
        startList.add("12:00");
        startList.add("13:00");
        startList.add("14:00");
        startList.add("15:00");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, startList);
       myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        HrStartSpinner.setAdapter(myAdapter);

        ArrayList<String> endList = new ArrayList<String>();
        endList.add("9:00");
        endList.add("10:00");
        endList.add("11:00");
        endList.add("12:00");
        endList.add("13:00");
        endList.add("14:00");
        endList.add("15:00");
        endList.add("16:00");
        endList.add("17:00");
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, endList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        HrEndSpinner.setAdapter(myAdapter1);

    }

    private void bindwicket() {

        btnChooseTime = (Button) findViewById(R.id.btnChooseTime);
        HrStartSpinner = (Spinner) findViewById(R.id.spinnerStart);
        HrEndSpinner = (Spinner) findViewById(R.id.spinnerEnd);
        tvtourdatechoose = (TextView) findViewById(R.id.tvtourdatechoose);
         changedateDatePicker = (DatePicker) findViewById(R.id.dpchoosedate);
        btnChooseDate = (Button) findViewById(R.id.btnchoosedate1);

        nameString = getIntent().getStringExtra("Name");
      //  timeTour = getIntent().getStringExtra("timetour");
        timeuseString = getIntent().getStringExtra("TimeUse");
        //tourDateString = getIntent().getStringExtra("date");

    }
}
