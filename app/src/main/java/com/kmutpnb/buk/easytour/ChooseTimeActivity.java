package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChooseTimeActivity extends AppCompatActivity {

    private Button btnChooseTime;
    private Spinner HrStartSpinner, HrEndSpinner;
    private String Hrstart, Hrend;
    private String tourDateString, nameString, timeuseString,timeTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);


        bindwicket();
        addListITEM();

        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Hrstart = String.valueOf(HrStartSpinner.getSelectedItem());
                Hrend = String.valueOf(HrEndSpinner.getSelectedItem());
                Log.d("abb", Hrstart);
                Log.d("abb", Hrend);





                listMyTour();
                upToSQLite();

            }
        });
    }

    private void upToSQLite() {

        MyManageTable objMyManageTable = new MyManageTable(this);
        objMyManageTable.addMyTour(nameString, timeuseString, tourDateString, Hrstart, Hrend);
    }

    private void listMyTour() {

        Intent objintent = new Intent(ChooseTimeActivity.this, ConfirmMytourActivity.class );//โยนค่าไปหน้าใหม่
        objintent.putExtra("date", tourDateString);
        objintent.putExtra("Name", nameString);
        objintent.putExtra("HrStart", Hrstart);
        objintent.putExtra("HrStop", Hrend);
        objintent.putExtra("TimeUse", timeuseString);
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


        nameString = getIntent().getStringExtra("Name");
      //  timeTour = getIntent().getStringExtra("timetour");
        timeuseString = getIntent().getStringExtra("TimeUse");
        tourDateString = getIntent().getStringExtra("date");

    }
}
