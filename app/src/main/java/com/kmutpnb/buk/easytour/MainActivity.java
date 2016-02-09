package com.kmutpnb.buk.easytour;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request db
        objMyManageTable = new MyManageTable(this);

        //test add value
        //testAddValue();
        //deleteAllSQlite

        deleteAllSQlite();

        //synchronize โหลดแค่ข้อมูล json to sqlite
        synJsontoSQlite();


    }//Main Method

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
