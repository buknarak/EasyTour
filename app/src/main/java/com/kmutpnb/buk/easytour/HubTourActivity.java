package com.kmutpnb.buk.easytour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class HubTourActivity extends AppCompatActivity implements View.OnClickListener {


    //Explicit
    private TextView showNameTextview;
    private ImageButton listtourButton, warningButton, trackingButton, recommendButton, listuserButton ;
    private String nameString, meIDString, status, Uname;
    public static final double centerLat = 14.47723421;
    public static final double centerLng = 100.64575195;
    private double myLat, myLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_tour);

        //Bind wicket ผูกตัวแปร
        bindWidget();

        //check section 4 ภาค
        checkSection();

        //show name
        showName();


        //button controller

        buttonController();
        Log.d("abc", meIDString);


    }//main method

    private void checkSection() {


        String tag = "Section";
        myLat = getIntent().getDoubleExtra("Lat", 0);//ถ้ารับค่าไม่ได้ 0 default
        myLng = getIntent().getDoubleExtra("Lng", 0);//ถ้ารับค่าไม่ได้ 0 default

        meIDString = getIntent().getStringExtra("meID");

        Log.d(tag, "myLat ==> " + myLat);
        Log.d(tag, "myLng ==> " + myLng);

    }

    private void showName() {

        nameString = getIntent().getStringExtra("Name"); //รับค่าให้ได้ก่อน
        showNameTextview.setText("Welcome : " + nameString);

    }//showName

    private void buttonController() {


        listuserButton.setOnClickListener(this);
       listtourButton.setOnClickListener(this);
        trackingButton.setOnClickListener(this);
        recommendButton.setOnClickListener(this);
        //listtourButton.setOnClickListener(this);


    }

    private void bindWidget() {

        showNameTextview = (TextView) findViewById(R.id.textView2);

//        listtourButton = (Button) findViewById(R.id.btnHulist);
//        warningButton = (Button) findViewById(R.id.btnwarning);
//        trackingButton = (Button) findViewById(R.id.btntracking);
//        recommendButton = (Button) findViewById(R.id.btnrecommend);
//        listuserButton = (Button) findViewById(R.id.btnlistuser);

        listtourButton = (ImageButton) findViewById(R.id.btnHprogram);
        trackingButton = (ImageButton) findViewById(R.id.btnHtracking);
        recommendButton = (ImageButton) findViewById(R.id.btnHplace);
        listuserButton = (ImageButton) findViewById(R.id.btnHulist);

        Uname = getIntent().getStringExtra("Uname");
        status = getIntent().getStringExtra("status");

        Log.d("260459", status);

    }//bind wicket

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnHprogram:
//                  //list my program tour
                Intent mytourIntent = new Intent(HubTourActivity.this, ShowMyTourActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                mytourIntent.putExtra("status1", status);
                mytourIntent.putExtra("Uname", Uname);
                startActivity(mytourIntent);
                break;
            case R.id.btnHtracking:

                Intent intent1 = new Intent(HubTourActivity.this, MyTagActivity.class);
                intent1.putExtra("Lat", myLat);
                intent1.putExtra("Lng", myLng);
                intent1.putExtra("meID", meIDString);
                intent1.putExtra("name",nameString);
                intent1.putExtra("status", status);
                startActivity(intent1);//sent value

                break;
            case R.id.btnHplace:
                //สถานที่ท่องเที่ยว
                //โปรแกรมทัวร์ เดิม

                Intent intent3 = new Intent(HubTourActivity.this, MainProgramTourActivity.class);
                intent3.putExtra("Lat", myLat);
                intent3.putExtra("Lng", myLng);
                intent3.putExtra("status", status);
                intent3.putExtra("Uname", Uname);
                startActivity(intent3);//sent value

                break;
            case R.id.btnHulist:

                Intent userIntent = new Intent(HubTourActivity.this, ShowUserActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                userIntent.putExtra("status", status);
                startActivity(userIntent);

                break;

        }//switch


    }//on click

}//main class
