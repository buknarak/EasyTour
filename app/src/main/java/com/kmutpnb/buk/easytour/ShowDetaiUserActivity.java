package com.kmutpnb.buk.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowDetaiUserActivity extends AppCompatActivity implements View.OnClickListener  {


  private Button CancelButton;
    private String userString, passString, nameString, positionString;
    private RadioGroup positonRadioGroup;
    private TextView userTextView, passTextView, nameTextView, positionTextView;
    private TextView showUserTextView;

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
        passString = getIntent().getStringExtra("Pass");
       // positionString = getIntent().getStringExtra("Position");


        nameTextView.setText(nameString);
        userTextView.setText(userString);
        passTextView.setText(passString);
        //positionTextView.setText(positionString);

    }

    private void bindWidget() {

        nameTextView = (TextView) findViewById(R.id.tvNamee);
        userTextView = (TextView) findViewById(R.id.tvUserr);
        passTextView = (TextView) findViewById(R.id.tvPasss);
      //  positionTextView = (TextView) findViewById(R.id.ragPosition);


    }

    private void radioController() {

    }

    @Override
    public void onClick(View view) {

    }
};
