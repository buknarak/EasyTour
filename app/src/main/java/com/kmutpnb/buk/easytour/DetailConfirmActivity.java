package com.kmutpnb.buk.easytour;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.internal.LocationRequestUpdateData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DetailConfirmActivity extends AppCompatActivity {
    private EditText Name,time;
    private Button btnedit;
    private String nameString, timeString,id;
    Spinner spinnerS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_confirm);
        ///detailMyTour
//        spinnerS.setEnabled(false);
//        Name.setEnabled(false);

        bindwicket();



        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                spinnerS.setEnabled(true);
//                Name.setEnabled(true);
//                setValue();

//                String name1 = Name.getText().toString();
//                String time1 = time.getText().toString();
//                updateToMySQL(name1,time1);
//               Log.d("Test1", time1);
//                synMytourtable();

            }
        });

    }//main method

    private void synMytourtable() {

////        delete UserTable
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            sqLiteDatabase.delete(MyManageTable.table_mytour, null,null);

            //// Connect Protocal
            StrictMode.ThreadPolicy threadPolicy  =  new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            InputStream inputStream =null;
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_get_mytour_buk.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            }catch (Exception e){

                Log.d("31", "Input ==> " +e.toString());

            }

            String strJson = null;
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine())!= null) {

                    stringBuilder.append(strLine);
                }
                inputStream.close();
                strJson = stringBuilder.toString();

            }catch (Exception e){
                Log.d("31", "Input ==> " + e.toString());
            }

            try {
                JSONArray jsonArray =new JSONArray(strJson);

                for (int i=0; i<jsonArray.length();i++ ){


                    JSONObject jsonobject = jsonArray.getJSONObject(i);

                    String Name = jsonobject.getString(MyManageTable.column_name);
                    String time = jsonobject.getString(MyManageTable.column_HrStart);


                    MyManageTable myManageTable = new MyManageTable(this);
                    myManageTable.addMyTour(Name,time,null,null,null);
                }

            } catch (Exception e) {
                Log.d("31", "Update ==> " + e.toString());
            }

    }

    private void setValue() {


        ArrayList<String> startList = new ArrayList<String>();
        startList.add("8:00");
        startList.add("9:00");
        startList.add("10:00");
        startList.add("11:00");
        startList.add("12:00");
        startList.add("13:00");
        startList.add("14:00");
        startList.add("15:00");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, startList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerS.setAdapter(myAdapter);
    }

    private void bindwicket() {

        Name = (EditText) findViewById(R.id.tvNamePlace);
        time = (EditText) findViewById(R.id.tvtime);
        spinnerS = (Spinner) findViewById(R.id.spinnerS);
        btnedit = (Button) findViewById(R.id.btnedit);

        nameString = getIntent().getStringExtra("Name");
        timeString = getIntent().getStringExtra("Hr");
        id = getIntent().getStringExtra("id");

        Name.setText(nameString);
        time.setText(timeString);
    }
//
//
//    //เอา ละ ลอง ทั้งหมดมาแสดง (เฉพาะ Status 1 (มัคคุเทศฏ์์))
//    SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
//            MODE_PRIVATE, null);
//
//    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE Status = 1", null);
//    /// Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
//    cursor.moveToFirst();
//    int intcount = cursor.getCount();
//
//    for(
//    int i = 0;
//    i<intcount;i++)
//
//    {
//
//        String strName = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
//        String strLat = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Lat));
//        String strLng = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Lng));
//        //  String strStatus = cursor.getString(cursor.getColumnIndex(MyManageTable.column_status));
//
//
//        Log.d("dist", "distance [" + strName + " ] " + douDistance);
////            String strnamedis = strName + " อยู่ห่าง = " + douDistance;
////            createMakerUser(strnamedis, strLat, strLng);
////            Log.d("test", strnamedis);
//
//        cursor.moveToNext(); //ทำต่อไปเรื่อยๆ
//    }//for
//
//    //where เฉพาะ 0 ดึงค่า double สร้างมาเกอร์
//    //เอาพิกัด admin ไปเก็บที่ server
//    updateToMySQL(meIDString, Double.toString(latADouble),Double
//
//    .
//
//    toString(lngADouble)
//
//    );//ส่งค่าไปเลยทีเดียว
//}


    private void updateToMySQL(String name1, String time1) {



        //change policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
        StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http
        try {

            ArrayList<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            valuePairs.add(new BasicNameValuePair("isAdd", "true"));
            valuePairs.add(new BasicNameValuePair("id", id));
            valuePairs.add(new BasicNameValuePair("Name", name1));
            valuePairs.add(new BasicNameValuePair("HrStart", time1));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_edit_mytour.php");
            httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
            httpClient.execute(httpPost);

//            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
//            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
//            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_user, userString));
//            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_password, passwordString));
//            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
//            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_status, positionString));


//
//            HttpClient objHttpClient = new DefaultHttpClient();
//            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_edit_mytour.php");
//            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
//            objHttpClient.execute(objHttpPost);

            Toast.makeText(DetailConfirmActivity.this, "อัพเดทข้อมูลเรียบร้อยแล้ว",
                    Toast.LENGTH_SHORT).show();  //Toast คือคำสั่งข้อความที่ขึ้นมาแล้วหายไป
            finish();

        } catch (Exception e) {
            Toast.makeText(DetailConfirmActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                    Toast.LENGTH_SHORT).show();//short = 4 วิ
        }


    }// update

}//main
