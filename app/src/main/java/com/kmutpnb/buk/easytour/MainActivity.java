package com.kmutpnb.buk.easytour;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private Boolean GPSABoolean, networkABoolearn;
    private double latADouble, lngADouble;
    private String meIDString = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Blind winget ผูก widget
        blidWidget();

        //request db
        objMyManageTable = new MyManageTable(this);

        //deleteAllSQlite
        deleteAllSQlite();

        //test add value
       // testAddValue();

        //synchronize โหลดแค่ข้อมูล json to sqlite
        synJsontoSQlite();

        //Get location
        getLocation();

    }//Main Method


    @Override
    protected void onResume() {
        super.onResume();

        objLocationManager.removeUpdates(objLocationListener);
        latADouble = 0;
        lngADouble = 0;

        Location networkLocation = requestLocation(LocationManager.NETWORK_PROVIDER, "no internet");
        if (networkLocation != null) {

            latADouble = networkLocation.getLatitude();
            lngADouble = networkLocation.getLongitude();

        }//if
        Location GPSLocation = requestLocation(LocationManager.GPS_PROVIDER, "No GPS Card");
        if (GPSLocation != null) {

            latADouble = GPSLocation.getLatitude();
            lngADouble = GPSLocation.getLongitude();

        }//if
        //show Log
        Log.d("Tour", "Lat ==> " + latADouble);
        Log.d("Tour", "Lng ==> " + lngADouble);


    }//onresume มีการขยับ เปลี่ยนหน้า ให้กลับมาเหมือนเดิม

    @Override
    protected void onStart() {
        super.onStart();
        GPSABoolean = objLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); //ถ้ามือถือมี card gps อยู่ จะ true
        if (!GPSABoolean) {

            networkABoolearn = objLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!networkABoolearn) {
                Toast.makeText(MainActivity.this, "ไม่สามารถหาพิกัดได้", Toast.LENGTH_SHORT);

            } //if

        }//if

    }//on start

    @Override
    protected void onStop() {
        super.onStop();
        objLocationManager.removeUpdates(objLocationListener);

    }//onStop โหมดปิด

    public Location requestLocation(String strProvider, String strError) { //strProvider ส่งค่าพิกัดได้ String strError ส่งค่าพิกัดไม่ได้

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener); // 1000ถ้าตั้งเฉยๆ ทุกหนึ่งวินาที หาพิกัด  // เคลื่อนที่ทุก 10 เมตร
            objLocation = objLocationManager.getLastKnownLocation(strProvider);


        } else {
                    Log.d("Tour", strError);
        } //if


        return objLocation;
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    //create class ทำหน้าที่โชว์ ละ ลอง เมื่อีการเปลี่ยนแปลง
        public final LocationListener objLocationListener =new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();
            //show Log
            Log.d("Tour", "Lat ==> " + latADouble);
            Log.d("Tour", "Lng ==> " + lngADouble);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };//class ที่ทำงานเมื่อ โลเคชั่นมีการเปลี่ยนแปลง

    private void getLocation() {

        //open service
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);//ค้นหาโลเคชั่นอย่างละเอียด
        objCriteria.setAltitudeRequired(false);//หาแค่ ละลองเฉย ไม่ระยะห่างแนวดิ่ง
        objCriteria.setBearingRequired(false);//หาแค่ ละลองเฉย ไม่ระยะห่างแนวดิ่ง


    }//getlocation

    public void clickLogin(View view) {

        //clickLogin
        useString = userEditText.getText().toString().trim(); //trimตัดช่องว่างทิ้งทั้งหน้าหลัง
        passString = passwordEditText.getText().toString().trim();

        //check space มีช่องว่างไหม
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

            meIDString = myResultStrings[0];

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

                    case 0:
                        Intent tourIntent = new Intent(MainActivity.this, HubTourActivity.class);
                        tourIntent.putExtra("Name", strName);
                        tourIntent.putExtra("Lat", latADouble);
                        tourIntent.putExtra("Lng", lngADouble);
                        tourIntent.putExtra("meID",meIDString);
                        tourIntent.putExtra("Uname",useString);
                        startActivity(tourIntent);
                        break;
                    case 1:
                            Intent adminIntent = new Intent(MainActivity.this, HubServiceActivity.class);
                        adminIntent.putExtra("Name",strName);
                        adminIntent.putExtra("Lat",latADouble);
                        adminIntent.putExtra("Lng",lngADouble);
                        adminIntent.putExtra("meID",meIDString);
                        adminIntent.putExtra("Uname",useString);
                        startActivity(adminIntent);
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


        String[] urlStrings = {"0",
                "http://swiftcodingthai.com/puk/php_get_user_buk.php",
                "http://swiftcodingthai.com/puk/php_get_tour_buk.php",
                "http://swiftcodingthai.com/puk/php_get_mytour_buk.php"};
        while (intTable <= 3) {

            //การซิงค์ 3กระบวนการ 1.Create input stream
            InputStream objInputStream = null;
           try {

                HttpClient objHttpClient = new DefaultHttpClient();
                HttpPost objHttpPost = new HttpPost(urlStrings[intTable]);
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


            //3.change json string to sqllite
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
                            String strLat1 = object.getString(MyManageTable.column_Lat);
                            String strLng1 = object.getString(MyManageTable.column_Lng);

                            objMyManageTable.addUser(strUser, strPassword, strName, strStatus, strLat1,  strLng1);

                            break;
                        case 2:

                            //for tour table
                            String strCategory = object.getString(MyManageTable.column_Category);
                            String strNametour = object.getString(MyManageTable.column_name);
                            String strProvince = object.getString(MyManageTable.column_Province);
                            String strDescription = object.getString(MyManageTable.column_Description);
                            String strType = object.getString(MyManageTable.column_Type);
                            String strTimeUse = object.getString(MyManageTable.column_TimeUse);
                            String strLat = object.getString(MyManageTable.column_Lat);
                            String strLng = object.getString(MyManageTable.column_Lng);
                            String strTotalScore = object.getString(MyManageTable.column_TotalScore);

                            objMyManageTable.addTour(strCategory, strNametour, strProvince,strDescription,
                                    strType, strTimeUse, strLat, strLng, strTotalScore);
                           Log.d("Dooo", strCategory);
                            break;

                        case 3:
                            //for mytour table
                            String strMyTourName = object.getString(MyManageTable.column_name);
                            String strMyTimeUse = object.getString(MyManageTable.column_TimeUse);
                            String strDateStart = object.getString(MyManageTable.column_DateStart);
                            String strHrStart = object.getString(MyManageTable.column_HrStart);
                            String strHrEnd = object.getString(MyManageTable.column_HrEnd);

                            objMyManageTable.addMyTour(strMyTourName, strMyTimeUse, strDateStart, strHrStart, strHrEnd);
                            Log.d("Dooo", strMyTourName);////////////////////////////////////////////////////////////////////
                            break;

                    }//switch
                }//for

            } catch (Exception e) {
                Log.d(tag, "update ==> " + e.toString());
            }
            intTable += 1;
        }//while

        Log.d("Dooo", Integer.toString(intTable));
    }//synJsontosqllite

    private void deleteAllSQlite() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManageTable.table_user, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว
        objSqLiteDatabase.delete(MyManageTable.table_tour, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว
        objSqLiteDatabase.delete(MyManageTable.table_mytour, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว
       // objSqLiteDatabase.delete(MyManageTable.table_rating, null, null); //ลบทั้งหมด ไม่ได้เลือกลบแค่บางแถว

    }

    private void testAddValue() {

       objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus", "Lat", "Lng");
        objMyManageTable.addMyTour("test", "test", "test", "test", "test");

    }//void คือ ไม่ต้องรีเทริืค่า
}//MainClass
