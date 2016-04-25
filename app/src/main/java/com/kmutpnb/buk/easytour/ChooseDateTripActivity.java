package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseDateTripActivity extends AppCompatActivity {


    private CalendarView calendarView;
    private String dateChooseString,Uname, nameString,meIDString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_trip);


        nameString  = getIntent().getStringExtra("name");
        meIDString = getIntent().getStringExtra("MeID");

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                month = month + 1;
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
             //   String selectedDate = sdf.format(new Date(calendarView.getDate()));
                dateChooseString = Integer.toString(year) +
                        "-" + (formattedMonth) +
                        "-" + (formattedDay);

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
                intent.putExtra("name", nameString);
                intent.putExtra("MeID", meIDString);
                intent.putExtra("chooseDate", dateChooseString);
               intent.putExtra("Uname", Uname);
                startActivity(intent);//sent value
    }
}//main
