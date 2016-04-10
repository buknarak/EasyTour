package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.sql.SQLData;
import java.util.ArrayList;

public class ConfirmMytourActivity extends AppCompatActivity {

    private TextView dateTextView, nameTextView;
    private String dateString, nameString, timeuseString, hrStartString, hrStopString;
    private ListView confirmmytourListView;
    private int timeuseint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_mytour);

        //timeuseint = Integer.parseInt(timeuseString);

        bindwidget();
        showmytour();
        createlistview();
    }

    public void clickConfirm(View view) {

            MyManageTable objMyManageTable = new MyManageTable(this);
        String[] strName = objMyManageTable.readAllMyTour(1);
        String[] strTimeUse = objMyManageTable.readAllMyTour(2);
        for (int i = 0; i < strName.length; i++) {

            try {

                //change policy
                StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                        .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
                StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http

                ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
                objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, strName[i]));
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_TimeUse, strTimeUse[i]));
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_DateStart, dateString));
               // objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrStart, dateString));
               // objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrEnd, dateString));

                HttpClient objHttpClient = new DefaultHttpClient();
                HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_mytour_buk.php");
                objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
                objHttpClient.execute(objHttpPost);


            } catch (Exception e) {
                Toast.makeText(ConfirmMytourActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                        Toast.LENGTH_SHORT).show();//short = 4 วิ
            }

        }//for

        //delete

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("easyTour.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("mytourTABLE", null, null);

        Toast.makeText(ConfirmMytourActivity.this, "Program Finish", Toast.LENGTH_SHORT).show();
        Intent objIntent = new Intent(ConfirmMytourActivity.this, ShowProgramTourActivity.class);
        startActivity(objIntent);
        finish();
    }

    private void createlistview() {

            MyManageTable objMyManageTable = new MyManageTable(this);
            String[] strName = objMyManageTable.readAllMyTour(1);
            String[] strTimeUse = objMyManageTable.readAllMyTour(2);
            ConfirmMyTourAdapter objConfirmMyTourAdapter = new ConfirmMyTourAdapter(ConfirmMytourActivity.this, strName, strTimeUse);
            confirmmytourListView.setAdapter(objConfirmMyTourAdapter);


        confirmmytourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editAndDeleteMytour(i);
            }
        });
    }

    private void editAndDeleteMytour(final int intPosition) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle("Are you Sure ?");
        objBuilder.setMessage("Delete this Program ?");
        objBuilder.setPositiveButton("Delete Program", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteprogram(intPosition);
                createlistview();
                dialogInterface.dismiss();

            }
        });
        objBuilder.show();
    }

    private void deleteprogram(int intPosition) {

        int id = intPosition + 1;
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("easyTour.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("mytourTABLE", "_id" + "=" + id, null);
    }

    private void showmytour() {

        dateString = getIntent().getStringExtra("date");
        nameString = getIntent().getStringExtra("Name");
        hrStartString = getIntent().getStringExtra("HrStart");
        hrStopString = getIntent().getStringExtra("HrStop");
        timeuseString = getIntent().getStringExtra("TimeUse");
        dateTextView.setText(dateString);

        // timeuseint = getIntent().getIntExtra("timetour", -1);



        Log.d("time", timeuseString);


    }

    private void bindwidget() {

        dateTextView = (TextView) findViewById(R.id.tvDateTour);
        confirmmytourListView = (ListView) findViewById(R.id.listviewConfirmMyTour);
    }
}
