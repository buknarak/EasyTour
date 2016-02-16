package com.kmutpnb.buk.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {


    //explicit
    private EditText userEditText, passEditText, nameEditText;
    private RadioGroup positonRadioGroup;
    private RadioButton tourRadioButton, adminRadioButton;
    private Button registerButton;
    private String userString, passwordString, nameString, positionString="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //bindwidget
        bindWidget();

        //Radio Controller
        radioController();
        buttonController();

    }//main method

    private void buttonController() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userString = userEditText.getText().toString().trim();
                passwordString = passEditText.getText().toString().trim();
                nameString = nameEditText.getText().toString().trim();

                //check space เช็คค่าว่าง

                if (checkSpace()) {

                    //have space
                    MyAlertDialog objMyAlertDialog = new MyAlertDialog();
                    objMyAlertDialog.myDialog(RegisterActivity.this,
                            "มีช่องว่าง", "กรุณากรอกข้อมูลทุกช่องค่ะ ");


                } //if

                else {

                    //no space
                    checkUser();

                }

            }//event
        });


    }//butttoncontroller

    private void checkUser() {

        try {
         //user not ok ซ้ำกัน
            MyManageTable objMyManageTable = new MyManageTable(this);
            String[] resultString = objMyManageTable.searchUser(userString);

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(RegisterActivity.this, "User ซ้ำ",
                    "เปลี่ยนยูเซอร์ใหม มี่" + resultString[1]+ "แล้ว" );


        }
            catch(Exception e) {
                //user ok
                confirmRegis();

            }

    }//checkuser

    private void confirmRegis() {


    }//confirmregister

    private boolean checkSpace() {


        return userString.equals("") || passwordString.equals("")|| nameString.equals("");
    }

    private void radioController() {

        positonRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.ragtour:

                        positionString = "0";
                        break;
                    case R.id.ragadmin:
                        positionString = "1";
                        break;
                }//swicth
            }//event
        });


    }//radiocontroller

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.editTextuser111);
        passEditText = (EditText) findViewById(R.id.editTextpass222);
        nameEditText = (EditText) findViewById(R.id.editTextname);
        positonRadioGroup = (RadioGroup) findViewById(R.id.ragposition);
        tourRadioButton = (RadioButton) findViewById(R.id.ragtour);
        adminRadioButton = (RadioButton) findViewById(R.id.ragadmin);
        registerButton = (Button) findViewById(R.id.btnregister);




    }//bind wicket
}// main class
