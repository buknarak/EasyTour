package com.kmutpnb.buk.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class showDetailTourActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView dateTextView, nameTextView, provinceTextView, typeTextView, timeuseTextView, descripTextView;
    private Button setTimeButton, addMyProgramButton, cancelButton;
    private String tourDateString, nameString, provinceString, typeString, timeuseString, descripString;

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


    }//main method

    private void buttonController() {

            setTimeButton.setOnClickListener(this);
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
             setTimeButton = (Button) findViewById(R.id.btnaddmyprograme);
             cancelButton = (Button) findViewById(R.id.btncancel);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button9:
                //set time


                break;
            case R.id.btnaddmyprograme:


                break;
            case R.id.btncancel:


                finish();
                break;



        }//switch


    }
}//main class
