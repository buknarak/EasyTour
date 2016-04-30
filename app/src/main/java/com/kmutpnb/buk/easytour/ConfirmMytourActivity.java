package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.internal.LocationRequestUpdateData;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmMytourActivity extends AppCompatActivity {

    private TextView dateTextView, nameTextView;
    private String dateString, nameString, timeuseString, hrStartString, hrStopString,nameString1,meIDString;
    private ListView confirmmytourListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_mytour);



        nameString1  = getIntent().getStringExtra("name");
        meIDString = getIntent().getStringExtra("MeID");


        bindwidget();
        showmytour();
        createlistview();
    }

    public void clickConfirm(View view) {

            MyManageTable objMyManageTable = new MyManageTable(this);
        String[] strName = objMyManageTable.readAllTourtmp(1);
        String[] strTimeUse = objMyManageTable.readAllTourtmp(2);
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
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrStart, hrStartString));
                objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_HrEnd, hrStopString));


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
        objSqLiteDatabase.delete("tourtmp", null, null);

        Toast.makeText(ConfirmMytourActivity.this, "สร้างโปรแกรมทัวร์เรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
        stopService(new Intent(ConfirmMytourActivity.this, MyService.class));
        Intent objIntent = new Intent(ConfirmMytourActivity.this, HubServiceActivity.class);
        objIntent.putExtra("MeID", meIDString);
        startActivity(objIntent);
        finish();
    }

    private void createlistview() {

            MyManageTable objMyManageTable = new MyManageTable(this);
            final String[] strName = objMyManageTable.readAllTourtmp(1);
           final String[] strTimeUse = objMyManageTable.readAllTourtmp(4); //timeuse = timeTour
            ConfirmMyTourAdapter objConfirmMyTourAdapter = new ConfirmMyTourAdapter(ConfirmMytourActivity.this, strName,strTimeUse);
           confirmmytourListView.setAdapter(objConfirmMyTourAdapter);
        registerForContextMenu(confirmmytourListView);
      confirmmytourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmMytourActivity.this);
//                ListView modeListview = new ListView(ConfirmMytourActivity.this);
//                String[] modes = new String[]{"Edit", "Delete"};
//                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(ConfirmMytourActivity.this,
//                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
//                modeListview.setAdapter(modeAdapter);
//                builder.setView(modeListview);
//                final Dialog dialog = builder.create();
//                dialog.show();
//                modeListview.setOnClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Clicked: ", Toast.LENGTH_LONG).show();
                            editAndDeleteMytour(masterListPosition);

//                    }
//                    });
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
        objSqLiteDatabase.delete("tourtmp", "_id" + "=" + id, null);
    }

    private void showmytour() {

        dateString = getIntent().getStringExtra("date");
        nameString = getIntent().getStringExtra("Name");
        hrStartString = getIntent().getStringExtra("HrStart");
        hrStopString = getIntent().getStringExtra("HrStop");
        timeuseString = getIntent().getStringExtra("TimeUse");
        dateTextView.setText(dateString);

        // timeuseint = getIntent().getIntExtra("timetour", -1);



//        Log.d("time", timeuseString);


    }

    private void bindwidget() {

        dateTextView = (TextView) findViewById(R.id.tvDateTour);
        confirmmytourListView = (ListView) findViewById(R.id.listviewConfirmMyTour);
    }

    public void clickedit(View view) {


    }
}
