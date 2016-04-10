package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HubServiceActivity extends AppCompatActivity implements View.OnClickListener {


        //Explicit
    private TextView showNameTextview;
    private Button authenButton, listtourButton, warningButton, trackingButton, recommendButton, listuserButton ;
    private String nameString, meIDString, Uname;
    public static final double centerLat = 14.47723421;
    public static final double centerLng = 100.64575195;
    private double myLat, myLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_service);


        bindWidget();

        //check section 4 ภาค
        checkSection();

        //show name
        showName();


        //button controller

        buttonController();


        //Bind wicket ผูกตัวแปร
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
        warningButton.setOnClickListener(this);
        trackingButton.setOnClickListener(this);
        recommendButton.setOnClickListener(this);
        listtourButton.setOnClickListener(this);


    }

    private void bindWidget() {

        showNameTextview = (TextView) findViewById(R.id.textView2);
        authenButton = (Button) findViewById(R.id.btnauthen);
        listtourButton = (Button) findViewById(R.id.btnlisttour);
        warningButton = (Button) findViewById(R.id.btnwarning);
        trackingButton = (Button) findViewById(R.id.btntracking);
        recommendButton = (Button) findViewById(R.id.btnrecommend);
        listuserButton = (Button) findViewById(R.id.btnlistuser);

        Uname = getIntent().getStringExtra("Uname");

    }//bind wicket

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnauthen:

                Intent authenIntent = new Intent(HubServiceActivity.this, RegisterActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                startActivity(authenIntent);

                break;
            case R.id.btnlisttour:
                //โปรแกรมทัวร์
                Intent intent = new Intent(HubServiceActivity.this, ShowProgramTourActivity.class);
                intent.putExtra("Lat", myLat);
                intent.putExtra("Lng", myLng);
                intent.putExtra("Uname", Uname);
                startActivity(intent);//sent value

//                Intent intent = new Intent(HubServiceActivity.this, MainProgramTourActivity.class);
//               // this.startActivity(intent);
//                intent.putExtra("Lat", myLat);
//                intent.putExtra("Lng", myLng);
//                startActivity(intent);//sent value

                break;
            case R.id.btnwarning:
                Intent intent1 = new Intent(HubServiceActivity.this, MyTagActivity.class);
                intent1.putExtra("Lat", myLat);
                intent1.putExtra("Lng", myLng);
                intent1.putExtra("meID", meIDString);
                startActivity(intent1);//sent value

                break;
            case R.id.btntracking:
                break;
            case R.id.btnrecommend:
                Intent mytourIntent = new Intent(HubServiceActivity.this, ShowMyTourActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                startActivity(mytourIntent);
                break;
            case R.id.btnlistuser:

                Intent userIntent = new Intent(HubServiceActivity.this, ShowUserActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                startActivity(userIntent);

                break;

        }//switch


    }//on click

}//main class
