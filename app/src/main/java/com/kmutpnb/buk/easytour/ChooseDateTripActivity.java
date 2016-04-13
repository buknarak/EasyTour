package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DateFormat;

public class ChooseDateTripActivity extends AppCompatActivity {


    private CalendarView calendarView;
    private String dateChooseString,Uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_trip);



        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                dateChooseString = Integer.toString(i2) +
                        "/" + Integer.toString(i1+1) +
                        "/" + Integer.toString(i);

            }
        });


    }//main method


    public void clickSelectDate(View view){

        Uname = getIntent().getStringExtra("Uname");
        Double myLat = getIntent().getDoubleExtra("Lat",0);
        Double myLng = getIntent().getDoubleExtra("Lng",0);

                Intent intent = new Intent(ChooseDateTripActivity.this, ShowProgramTourActivity.class);
                intent.putExtra("Lat", myLat);
                intent.putExtra("Lng", myLng);
                intent.putExtra("chooseDate", dateChooseString);
               intent.putExtra("Uname", Uname);
                startActivity(intent);//sent value
    }
}//main
