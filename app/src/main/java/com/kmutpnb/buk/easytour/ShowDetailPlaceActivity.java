package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowDetailPlaceActivity  extends AppCompatActivity implements OnClickListener {


    private TextView  nameTextView, provinceTextView, typeTextView, timeuseTextView, descripTextView, rateTextView;
    private Button  submitButton;
    private String  nameString, provinceString, typeString, timeuseString, descripString, Uname, raingString,statusString;
    private RatingBar ratingBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_place);

        //bindwidget
        bindWidget();

        //get current time and show
      //  getTimeShow();


        //show textview
        showTextView();

        //Button Controller
         buttonController();
        // setCurrentDateView();

        //select Rating
        showRating();
        //  timeTour = Integer.parseInt(timeuseString.trim());

//        Log.d("aaa",Uname);
//        Log.d("status", statusString);


    }//main method

    private void showRating() {

        SynRatingTable();
        //int id = intPosition + 1;
//        int id = getIntent().getIntExtra("ID",0);
//       int positionint = id + 1;
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        // Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ratingTABLE", "_id" + "=" + positionint, null);
        //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE,ratingTABLE WHERE Name = " + "'" + nameString + "'", null);
        // Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM ratingTABLE WHERE Name = " + "'" + nameString + "'", null);
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tourTABLE.TotalScore, " +
//                "ratingTABLE.Name ,ratingTABLE.Score FROM tourTABLE JOIN ratingTABLE ON tourTABLE.Name = ratingTABLE.Name " +
//                "WHERE ratingTABLE.Name = " + "'" + nameString + "'", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ratingTABLE.Name ,count(Score)AS countscore , avg(Score)AS avgscore " +
                "FROM ratingTABLE " +
                "WHERE ratingTABLE.Name = " + "'" + nameString + "'", null);
        cursor.moveToFirst();

        float avgScore = cursor.getFloat(cursor.getColumnIndex("avgscore"));
        int totalCount = cursor.getInt(cursor.getColumnIndex("countscore"));
        int intcount = cursor.getCount();

        if (totalCount == 0) {
            Log.d("abc", "แถว " +totalCount);
        } else {
            for (int i=0 ; i <intcount;i++) {
                String strName = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
                //String strScore = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Score));
                Log.d("abc", strName);
                // Log.d("abc", strScore);
                Log.d("abc", "จำนวนคะแนน " +avgScore);
                Log.d("abc", "แถว " +totalCount);
                ratingBar.setRating(avgScore);
                String strAvg=Float.toString(avgScore);
                rateTextView.setText(strAvg);
            }
        }
    }

    private void SynRatingTable() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManageTable.table_rating, null,null);

        //// Connect Protocal
        StrictMode.ThreadPolicy threadPolicy  =  new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        InputStream inputStream =null;
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_get_rating_buk.php");
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

                String strUser = jsonobject.getString(MyManageTable.column_user);
                String strName = jsonobject.getString(MyManageTable.column_name);
                String strScore = jsonobject.getString(MyManageTable.column_Score);


                MyManageTable myManageTable = new MyManageTable(this);
                myManageTable.addRating(strUser,strName,strScore);
            }

        } catch (Exception e) {
            Log.d("31", "Update ==> " + e.toString());
        }


    }

    private void buttonController() {

        // setTimeButton.setOnClickListener(this);
//        addMyProgramButton.setOnClickListener(this);
//        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    private void showTextView() {

        nameString = getIntent().getStringExtra("Name");
        provinceString = getIntent().getStringExtra("Province");
        typeString = getIntent().getStringExtra("Type");
        timeuseString = getIntent().getStringExtra("TimeUse");
        descripString = getIntent().getStringExtra("Descrip");

        nameTextView.setText(nameString);
        provinceTextView.setText(provinceString);
        typeTextView.setText(typeString);
        timeuseTextView.setText(timeuseString + " ชั่วโมง");
        descripTextView.setText(descripString);


        String strImage = getIntent().getStringExtra("Img");
        Picasso.with(this).load(strImage).resize(280,200).into(imageView);
    }

//    private void getTimeShow() {
//        tourDateString = getIntent().getStringExtra("Date");
//
//        if (tourDateString == null) {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = new Date();
//            tourDateString = dateFormat.format(date);
//
//            Log.d("ades", tourDateString);
//            dateTextView.setText(tourDateString);
//        } else {
//            dateTextView.setText(tourDateString);
//        }
//
//    }//get time

    private void bindWidget() {

     //   dateTextView = (TextView) findViewById(R.id.tvdate);
        imageView = (ImageView) findViewById(R.id.imgPlace);
        nameTextView = (TextView) findViewById(R.id.tvnamet);
        provinceTextView = (TextView) findViewById(R.id.tvprovince);
        typeTextView = (TextView) findViewById(R.id.tvtype);
        timeuseTextView = (TextView) findViewById(R.id.tvtimeuse);
        descripTextView = (TextView) findViewById(R.id.tvdescrip);
       // addMyProgramButton = (Button) findViewById(R.id.btnaddmyprograme);
       // cancelButton = (Button) findViewById(R.id.btncancel);
        // changedateDatePicker = (DatePicker) findViewById(R.id.dpChange);
        //  setTimeButton = (Button) findViewById(R.id.button9);

        submitButton = (Button) findViewById(R.id.btnRating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        rateTextView = (TextView) findViewById(R.id.tvRateview1);

        Uname = getIntent().getStringExtra("Uname");

        Log.d("999", Uname);
        statusString = getIntent().getStringExtra("status1");
        //int i = Integer.parseInt(statusString.trim());

//        Log.d("status", statusString);
//        Log.d("status", "i = " +i);
//        if (i == 1) {
//            View a = findViewById(R.id.btnaddmyprograme);
//            a.setVisibility(View.GONE);
//        } else {
//            View a = findViewById(R.id.btnaddmyprograme);
//            a.setVisibility(View.VISIBLE);
//            Log.d("status", statusString);
//
//        }
//        Log.d("status", statusString);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.btnaddmyprograme:
//
//                tourDateString = dateTextView.getText().toString();
//
//                Intent objintent = new Intent(showDetailTourActivity.this, ChooseTimeActivity.class );//โยนค่าไปหน้าใหม่
//                objintent.putExtra("date", tourDateString);
//                objintent.putExtra("Name", nameString);
////                intent.putExtra("HrStart", hrStart);
////                intent.putExtra("HrStop", hrStop);
//                objintent.putExtra("TimeUse", timeuseString);
//                // objintent.putExtra("timetour", timeTour);
//                startActivity(objintent);
//
//                break;
//
//            case R.id.btncancel:
//                Intent intent = new Intent(this, HubServiceActivity.class);
//                this.startActivity(intent);
//                finish();
//                break;
            case R.id.btnRating:
                ShowDialogRating();
                break;
        }

    }

//    private void upToSQLite() {
//
//        MyManageTable objMyManageTable = new MyManageTable(this);
//        objMyManageTable.addMyTour(nameString, timeuseString, tourDateString, hrStart, hrStop);
//    }

    private void ShowDialogRating() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);

        rating.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        rating.setMax(7);
        rating.setNumStars(7);
        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("ระดับความพึงพอใจ!! ");
        popDialog.setView(rating);

        // Button OK
        popDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ratingBar.setRating(rating.getRating());
                rateTextView.setText(String.valueOf(rating.getProgress()));
                raingString = rateTextView.getText().toString();
                //   updateToSQLiteRating();
                //   Log.d("aaa", raingString);
                updateToDB();
                dialog.dismiss();
            }
        })
                // Button Cancel
                .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        popDialog.create();
        popDialog.show();
    }

    private void updateToDB() {

        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build(); //ปลด policy ให้สามารถอัพเดทได้
        StrictMode.setThreadPolicy(myPolicy);//สามารถเชื่อมต่อ potocal http

        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true")); //isAdd ตัวแปร php ในการแอดข้อมูล
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_user, Uname));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_Score, raingString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_rating_buk.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(ShowDetailPlaceActivity.this, "ระบบได้ผลโหวตแล้วค่ะ",
                    Toast.LENGTH_SHORT).show();//short = 4 วิ

        } catch (Exception e) {
            Toast.makeText(ShowDetailPlaceActivity.this, "ไม่สามารถเชื่อมต่อ server ได้",
                    Toast.LENGTH_SHORT).show();//short = 4 วิ
        }
    }

}//main class