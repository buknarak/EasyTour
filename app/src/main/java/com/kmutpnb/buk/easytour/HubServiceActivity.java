package com.kmutpnb.buk.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HubServiceActivity extends AppCompatActivity implements View.OnClickListener {


        //Explicit
    private TextView showNameTextview;
    private Button authenButton, listtourButton, warningButton, trackingButton, recommendButton, listuserButton ;
    private String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_service);


        bindWidget();
        //show name
        showName();


        //button controller

        buttonController();


        //Bind wicket ผูกตัวแปร
    }//main method

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

    }//bind wicket

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnauthen:

                Intent authenIntent = new Intent(HubServiceActivity.this, RegisterActivity.class); //เปลี่ยนหน้าจากปัจจุบันไปหน้าใหม่
                startActivity(authenIntent);

                break;
            case R.id.btnlisttour:
                break;
            case R.id.btnwarning:
                break;
            case R.id.btntracking:
                break;
            case R.id.btnrecommend:
                break;
            case R.id.btnlistuser:
                break;

        }//switch


    }//on click
}//main class
