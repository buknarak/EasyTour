package com.kmutpnb.buk.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    //explicate
    private MyManageTable objMyManageTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request db
        objMyManageTable = new MyManageTable(this);



    }//Main Method
}//MainClass
