package com.kmutpnb.buk.easytour;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyTagActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double meLatADouble, meLngADouble;
    private LatLng meLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tag);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //get Value from Intent
        getLatLngForIntent();


        //setup latLng
            setupLatLng();

    }//Main Method

    private void setupLatLng() {

        //for start app
        meLatLng = new LatLng(meLatADouble, meLngADouble); //เอาค่าไปใส่แผนที่

    }

    private void getLatLngForIntent() {

        meLatADouble = getIntent().getDoubleExtra("Lat", 14.47723421);
        meLngADouble = getIntent().getDoubleExtra("Lat", 100.64575195);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // กำหนดจุดเริ่มต้นให้แผนที่
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meLatLng, 16)); //ถ้าเลขยิ่งเยอะยิ่งใกล้
        mMap.addMarker(new MarkerOptions()
        .position(meLatLng)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));


    }//Maps Method


}//Main Class
