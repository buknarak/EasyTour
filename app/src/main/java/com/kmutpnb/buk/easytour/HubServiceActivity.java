package com.kmutpnb.buk.easytour;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
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

public class HubServiceActivity extends AppCompatActivity implements View.OnClickListener {


        //Explicit
    private TextView showNameTextview;
    private ImageButton authenButton, listtourButton, addProgramtourButton, trackingButton, recommendButton, listuserButton ;
    private String nameString, meIDString, Uname, datestartString, status;
    public static final double centerLat = 14.47723421;
    public static final double centerLng = 100.64575195;
    private double myLat, myLng;
    private String status1 = "1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_service);


        bindWidget();
       //Bind wicket ผูกตัวแปร

        //check section 4 ภาค
        checkSection();

        //show name
        showName();


        //button controller
        buttonController();

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

        authenButton.setOnClickListener(this);
        listuserButton.setOnClickListener(this);
       addProgramtourButton.setOnClickListener(this);
        trackingButton.setOnClickListener(this);
        recommendButton.setOnClickListener(this);
        listtourButton.setOnClickListener(this);


    }

    private void bindWidget() {

        showNameTextview = (TextView) findViewById(R.id.textView2);
        authenButton = (ImageButton) findViewById(R.id.btnSadduser);
        listtourButton = (ImageButton) findViewById(R.id.btnSprogram);
        addProgramtourButton = (ImageButton) findViewById(R.id.btnSaddprogram);
        trackingButton = (ImageButton) findViewById(R.id.btnStracking);
        recommendButton = (ImageButton) findViewById(R.id.btnSplace);
        listuserButton = (ImageButton) findViewById(R.id.btnSulist);

        Uname = getIntent().getStringExtra("Uname");
        status = getIntent().getStringExtra("status");


       // datestartString = getIntent().getStringExtra("setdate");
       // Log.d("tree", datestartString);

    }//bind wicket

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSadduser:
                ///add user
                Intent authenIntent = new Intent(HubServiceActivity.this, RegisterActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                authenIntent.putExtra("Lat", myLat);
                authenIntent.putExtra("Lng", myLng);
                startActivity(authenIntent);

                break;
            case R.id.btnSaddprogram:
                // add program tour

                Intent intent = new Intent(HubServiceActivity.this, ChooseDateTripActivity.class);
                intent.putExtra("Lat", myLat);
                intent.putExtra("Lng", myLng);
                intent.putExtra("Uname", Uname);
                intent.putExtra("status", status);
                intent.putExtra("name", nameString);
                intent.putExtra("MeID", meIDString);
                //intent.putExtra("Name", nameString);
                startActivity(intent);//sent value

                //โปรแกรมทัวร์ เดิม
//                Intent intent = new Intent(HubServiceActivity.this, ShowProgramTourActivity.class);
//                intent.putExtra("Lat", myLat);
//                intent.putExtra("Lng", myLng);
//                intent.putExtra("Uname", Uname);
//                startActivity(intent);//sent value
                break;
            case R.id.btnSprogram:
                //list my program tour
                Intent mytourIntent = new Intent(HubServiceActivity.this, ShowMyTourActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                mytourIntent.putExtra("status1", status1);
                startActivity(mytourIntent);

                break;
            case R.id.btnStracking:
                // การติดตาม

                Intent intent1 = new Intent(HubServiceActivity.this, MyTagActivity.class);
                intent1.putExtra("Lat", myLat);
                intent1.putExtra("Lng", myLng);
                intent1.putExtra("meID", meIDString);
                intent1.putExtra("name",nameString);
                intent1.putExtra("Uname", Uname);
                intent1.putExtra("status", status);
                startActivity(intent1);//sent value

                break;
            case R.id.btnSplace:
                //สถานที่ท่องเที่ยว
                //โปรแกรมทัวร์ เดิม


                Intent intent3 = new Intent(HubServiceActivity.this, MainProgramTourActivity.class);
                intent3.putExtra("Lat", myLat);
                intent3.putExtra("Lng", myLng);
                intent3.putExtra("Uname", Uname);
                intent3.putExtra("status1", status1);
                startActivity(intent3);//sent value
                break;
            case R.id.btnSulist:
                // list user
                Intent userIntent = new Intent(HubServiceActivity.this, ShowUserActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                userIntent.putExtra("status", status);
                startActivity(userIntent);

                break;

        }//switch


    }//on click

}//main class
