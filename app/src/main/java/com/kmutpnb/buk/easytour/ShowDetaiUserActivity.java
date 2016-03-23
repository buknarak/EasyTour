package com.kmutpnb.buk.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowDetaiUserActivity extends AppCompatActivity implements View.OnClickListener  {


    private String userString, passString, nameString, positionString;
    private RadioGroup positionRadioGroup;
    private TextView userTextView, passTextView, nameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detai_user);

        //bindwidget
        bindWidget();


        //show textview
        showTextView();

        radioController();


    }

    private void showTextView() {

        nameString = getIntent().getStringExtra("Name");
        userString = getIntent().getStringExtra("User");
        positionString = getIntent().getStringExtra("Status");
        passString = getIntent().getStringExtra("Pass");



        nameTextView.setText(nameString);
        userTextView.setText(userString);
        passTextView.setText(passString);

        if (positionString == "นักท่องเที่ยว") {

            Log.d("Status", positionString);
            positionRadioGroup.check(R.id.rdTour);
        } else {
            Log.d("Status", positionString );
            positionRadioGroup.check(R.id.rdAdmin);
        }

    }

    private void bindWidget() {

        nameTextView = (TextView) findViewById(R.id.tvNamee);
        userTextView = (TextView) findViewById(R.id.tvUserr);
        passTextView = (TextView) findViewById(R.id.tvPasss);
        positionRadioGroup = (RadioGroup) findViewById(R.id.ragPosition);


    }

    private void radioController() {

    }

    @Override
    public void onClick(View view) {

    }
};
