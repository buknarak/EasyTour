package com.kmutpnb.buk.easytour;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ShowProgramTourActivity extends AppCompatActivity {


    //explicite
    private double userLatADouble, userLngAdouble;
    private String categoryString;
    private TextView showCatTextView;
    private ListView tourListViewListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour);

        //recive lat lng

        //bindWindget;
        bindwidget();


        receiveAndSep();

        //show view
        showView();

    }//main method

    private void bindwidget() {

        showCatTextView = (TextView) findViewById(R.id.textView7);
        tourListViewListView = (ListView) findViewById(R.id.listView);


    }

    private void showView() {

        showCatTextView.setText(getResources().getString(R.string.listtour)+ " " + categoryString);
    }





    private void receiveAndSep() {



        userLatADouble = getIntent().getDoubleExtra("Lat", HubServiceActivity.centerLat );//10power -6 ขยับจากจุดศูนย์กลางนิดนึง
        userLngAdouble = getIntent().getDoubleExtra("Lng", HubServiceActivity.centerLng );

        //find w or e
        if (userLatADouble > HubServiceActivity.centerLat) {

            if (userLatADouble>HubServiceActivity.centerLng) {

                    categoryString = "NE";
            } else {

                categoryString = "NW";
            }

        } else {
            //eath
            if (userLngAdouble>HubServiceActivity.centerLng) {
                //SE
                categoryString = "SE";
            } else {
                //SW
                categoryString = "SW";
            }


        }//if 1

        Log.d("11/03/59", "category = " + categoryString);

    }//receiveandSep
}//main class
