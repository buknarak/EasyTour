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

    //timer
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    private ListView listAlarm;
    public static ArrayList<String> listValue;
    private TextView textView2;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;
    //timer


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

       // alarmTimer();


       //startService(new Intent(HubTourActivity.this, MyServiceUser.class));

//intent.putExtra("name", nameString);

       // Intent intent = new Intent(HubTourActivity.this, MyServiceUser.class);
        //intent.putExtra("meID",meIDString);
//        startService(intent);


    }//main method

//    private void alarmTimer() {
//
//        //int callCount=0;
//
//        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
//                MODE_PRIVATE, null);
//
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE WHERE DateStart = current_date", null);
//        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mytourTABLE ", null);
//
//        cursor.moveToFirst();
//
//        //float count = cursor.getFloat(cursor.getColumnIndex("DateStart1"));
//        //  Log.d("tree" , "cout " + count);
//
//        while (!cursor.isAfterLast()) { // Loop until all vales have been seen
//
//            String name = cursor.getString(1);
//
//            Log.d("tree", "name tour " + name);
//            // Log.d("aess", "66 " + datestartString);
//
//            String time = cursor.getString(4);
//            String[] parts = time.split(":"); //Split  String Value stored in db
//            String part1 = parts[0]; // hour
//            String part2 = parts[1]; // minute
//            int hr = Integer.parseInt(part1);
//            int min = Integer.parseInt(part2);
//
//            Log.d("tree", "hour " + hr);
//            Log.d("tree", "MINUTE " + min);
//////                if (callCount == 0) {
//            // Do something with the time chosen by the user
//            Calendar cal = Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY, hr);
//            cal.set(Calendar.MINUTE, min);
////                    int a = 10;
////                    int b = 00;
////                    cal.set(Calendar.HOUR_OF_DAY, a);
////                    cal.set(Calendar.MINUTE, b);
//
//            setAlarm(cal);
//
//            cursor.moveToNext();
//        }///while
//        cursor.close();

   // }//method


//    private void setAlarm(Calendar targetCal) {
//
//        listValue.add(targetCal.getTime() + "");
//        Log.d("tree", "list " + listValue);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
//        listAlarm.setAdapter(adapter);
//
//
//        final int _id = (int) System.currentTimeMillis();
//
//        Intent intent = new Intent(getBaseContext(), AlarmReceiverT.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
//        listAlarm.setAdapter(adapter);
//    }

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
