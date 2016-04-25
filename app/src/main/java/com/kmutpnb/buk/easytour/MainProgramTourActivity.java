package com.kmutpnb.buk.easytour;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.TabHost;
import android.app.TabActivity;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainProgramTourActivity extends TabActivity implements OnTabChangeListener {

    private double myLat, myLng;
    private String statusString,Uname, placeString = "place";

    /** Called when the activity is first created. */
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_program_tour);

        myLat = getIntent().getDoubleExtra("Lat", HubServiceActivity.centerLat);//10power -6 ขยับจากจุดศูนย์กลางนิดนึง
        myLng = getIntent().getDoubleExtra("Lng", HubServiceActivity.centerLng);
        statusString = getIntent().getStringExtra("status");
        Uname = getIntent().getStringExtra("Uname");

        // Get TabHost Refference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ShowProgramTourActivity.class)
        .putExtra("Lat", myLat).putExtra("Lng", myLng).putExtra("status", statusString).putExtra("place", placeString).putExtra("Uname", Uname);
        spec = tabHost.newTabSpec("First").setIndicator("By GPS")
                .setContent(intent);


        //Add intent to tab
        tabHost.addTab(spec);

        /************* TAB2 ************/
        intent = new Intent().setClass(this, ShowProgramTourAllActivity.class).putExtra("status", statusString).putExtra("place", placeString).putExtra("Uname", Uname);;
        spec = tabHost.newTabSpec("Second").setIndicator("ALL")
                .setContent(intent);
        tabHost.addTab(spec);
//
//        /************* TAB3 ************/
//        intent = new Intent().setClass(this, Tab3.class);
//        spec = tabHost.newTabSpec("Third").setIndicator("")
//                .setContent(intent);
//        tabHost.addTab(spec);

        // Set drawable images to tab
        tabHost.getTabWidget().getChildAt(1);
      //  tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab3);

        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0);


    }

    @Override
    public void onTabChanged(String tabId) {

        /************ Called when tab changed *************/

        //********* Check current selected tab and change according images *******/

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0){
                tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#000000"));}
            else if(i==1){
                tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#000000"));}
//            else if(i==2)
//                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab3);
        }


        Log.i("tabs", "CurrentTab: "+tabHost.getCurrentTab());

        if(tabHost.getCurrentTab()==0){
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#ffffff"));}
        else if(tabHost.getCurrentTab()==1){
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));}
//        else if(tabHost.getCurrentTab()==2)
//            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab3_over);

    }

}
