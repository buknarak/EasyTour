package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    //explicate
    private MyManageTable objMyManageTable;
    private EditText userEditText, passwordEditText;
    private String useString, passString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Blind winget ผูก widget

        blidWidget();


                //request db
                objMyManageTable = new MyManageTable(this);

        //test add value
       // testAddValue();
        //deleteAllSQlite

      deleteAllSQlite();

        //synchronize โหลดแค่ข้อมูล json to sqlite
      synJsontoSQlite();

        //Get location
        getLocation();


    }//Main Method

    private void getLocation() {



    }//getlocation

    public void clickLogin(View view) {

        //clickLogin
        useString = userEditText.getText().toString().trim(); //trimตัดช่องว่างทิ้งทั้งหน้าหลัง
        passString = passwordEditText.getText().toString().trim();

        //check speace มีช่องว่างไหม
        if (useString.equals("")|| passString.equals("")) {

            //have space ว่างเปล่า
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "มีช่องว่าง", "กรุณากรอกข้อมูลให้ครบทุกช่องค่ะ");


        } else {

            //no space
            checkUser();

        }//if



    }// clicklogin

    private void checkUser() {

        try {

            String[] myResultStrings = objMyManageTable.searchUser(useString);

            Log.d("Tour", "Pass =" + myResultStrings[2]);
            //check password
            checkPassword(myResultStrings[2], myResultStrings[3], myResultStrings[4]); //โยน อากิวเม้น 3 ตัว แสดงชื่อ ตำแหน่ง



        }
        catch (Exception e) {

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "No This User",
                    "No " + useString + " in my database");

        }


    }//checkUser

    private void checkPassword(String strPassword, String strName, String strStatus) {

        if (passString.equals(strPassword)) {

            welcome(strName, strStatus);

        } else {

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "Password False",
                  "Please Try again"  );

        }//if

    }//check password

    private void welcome(final String strName, final String strStatus) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_myaccount);
        objBuilder.setTitle("Welcome");
        objBuilder.setMessage("ยินดีต้อนรับ" + strName + "\n" + checkPosition(strStatus));
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {//เมื่อมีการกด ตกลง
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (Integer.parseInt(strStatus))
                {
                    case 1:
                            Intent adminIntent = new Intent(MainActivity.this, HubServiceActivity.class);
                        adminIntent.putExtra("Name",strName);
                        startActivity(adminIntent);
                       break;
                    case 0:
                        Intent tourIntent = new Intent(MainActivity.this, HubTourActivity.class);
                        tourIntent.putExtra("Name", strName);
                        startActivity(tourIntent);

                        break;
                }
                finish();


//                Intent objIntent = new Intent(MainActivity.this, HubServiceActivity.class); //ย้ายการทำานจากเมน ไป หน้า ฮับ
//                objIntent.putExtra("Name", strName);
//                objIntent.putExtra("Status", strStatus);
//                startActivity(objIntent);
//                finish();//ปิดหน้าแรก

            }
        });//event
        objBuilder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userEditText.setText("");
                passwordEditText.setText("");
                dialogInterface.dismiss(); //ล้างค่าทิ้ง
            }
        });

        objBuilder.show();

    }//welcome

    private String checkPosition(String strStatus) {

        int intStatus = Integer.parseInt(strStatus);
        String strPosition = null;

        switch (intStatus) {

            case 0:
                strPosition = "สถานะ : นักท่องเที่ยว";
                break;
            case 1:
                strPosition = "สถานะ : มัคคุเทศน์";
                break;

        }



        return strPosition;
    }

    private void blidWidget() {

        userEditText = (EditText) findViewById(R.id.editTextUser);
        passwordEditText = (EditText) findViewById(R.id.editTextPass);

    }

    private void synJsontoSQlite() {

        //การ permission policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);//เชื่อมต่ออเน็ตอนุญาติเข้าถึงโปโตคอล

        int intTable = 1; //amount of table
        String tag = "tour";
        while (intTable <= 1) {

            //การซิงค์ 3กระบวนการ 1.Create input stream
            InputStream objInputStream = null;
            String strURLuser = "http://swiftcodingthai.com/puk/php_get_user_buk.php";
            HttpPost objHttpPost = null;
            try {

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTable) {

                    case 1:
                        objHttpPost = new HttpPost(strURLuser);
                        break;
                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();
            }catch (Exception e) {

                Log.d(tag, "InputStream ==> " + e.toString());

            }

            //2.change input stream to json string
            String strJson = null;
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8")); //ถ้าbufferreader แปลงกับเป็น UTF8
                StringBuilder objStringBuilder = new StringBuilder(); //หลังจากตัด แล้วมาทำการต่อ string
                String strLine = null;

                while ((strLine=objBufferedReader.readLine()) !=null ) {

                    objStringBuilder.append(strLine);


                }//while
                objInputStream.close();
                strJson = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d(tag, "strJSON ==> " + e.toString());
            }


            //3.chenge json string to sqllite
            try {

                JSONArray obJsonArray = new JSONArray(strJson);
                for (int i = 0; i < obJsonArray.length(); i++) {

                    JSONObject object = obJsonArray.getJSONObject(i);
                    switch (intTable) {
                        case 1:
                            //for user table
                            String strUser = object.getString(MyManageTable.column_user);
                            String strPassword = object.getString(MyManageTable.column_password);
                            String strName = object.getString(MyManageTable.column_name);
                            String strStatus = object.getString(MyManageTable.column_status);

                            objMyManageTable.addUser(strUser, strPassword, strName, strStatus);

                            break;

                    }//switch


                }//for


            } catch (Exception e) {
                Log.d(tag, "update ==> " + e.toString());
            }


            intTable += 1;
        }//while

    }//synJsontosqllite

    private void deleteAllSQlite() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManageTable.table_user, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว
    }

    private void testAddValue() {

        objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus");

    }//void คือ ไม่ต้องรีเทริืค่า
}//MainClass
