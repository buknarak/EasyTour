package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

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

        String[] positionStrings = {"ลูกทัวร์","มัคคุเทศน์"};
        int intIndex = Integer.parseInt(positionString);//position 0 1
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_myaccount);
        objBuilder.setTitle("โปรดตรวจข้อมูล");
        objBuilder.setMessage("User = " + userString + "\n" +
                "Password = " + passwordString + "\n" +
                "Name = "  + nameString + "\n" +
                "Position = " + positionStrings[intIndex]);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //update to mysql
                updateToMySQL();
                dialogInterface.dismiss(); //ทำให้ pop up หายไป


            }
        });
        objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        objBuilder.show();
    }//confirm register

    private void updateToMySQL() {

        //change policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
        StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http
        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_user, userString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_password, passwordString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_status, positionString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_user_buk.php");
                objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(RegisterActivity.this, "อัพเดทข้อมูลเรียบร้อยแล้ว",
                    Toast.LENGTH_SHORT).show();  //Toast คือคำสั่งข้อความที่ขึ้นมาแล้วหายไป
            finish();

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                    Toast.LENGTH_SHORT).show();//short = 4 วิ

        }




    }//method update to mysql


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
