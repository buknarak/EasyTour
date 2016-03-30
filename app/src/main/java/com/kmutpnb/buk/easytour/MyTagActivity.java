package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MyTagActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double meLatADouble, meLngADouble;
    private LatLng meLatLng;

    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private Boolean GPSABoolean, networkABoolearn;
    private double latADouble, lngADouble;
    private String meIDString;


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


        //Get location
        getLocation();

    }//Main Method

    @Override
    protected void onResume() {
        super.onResume();

        objLocationManager.removeUpdates(objLocationListener1);
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
                Toast.makeText(MyTagActivity.this, "ไม่สามารถหาพิกัดได้", Toast.LENGTH_SHORT);

            } //if

        }//if

    }//on start

    @Override
    protected void onStop() {
        super.onStop();
        objLocationManager.removeUpdates(objLocationListener1);

    }//onStop โหมดปิด

    public Location requestLocation(String strProvider, String strError) { //strProvider ส่งค่าพิกัดได้ String strError ส่งค่าพิกัดไม่ได้

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener1); // 1000ถ้าตั้งเฉยๆ ทุกหนึ่งวินาที หาพิกัด  // เคลื่อนที่ทุก 10 เมตร
            objLocation = objLocationManager.getLastKnownLocation(strProvider);


        } else {
            Log.d("Tour", strError);
        } //if


        return objLocation;
    }

    //create class ทำหน้าที่โชว์ ละ ลอง เมื่อีการเปลี่ยนแปลง
    public final LocationListener objLocationListener1 =new LocationListener() {
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
    }

    private void setupLatLng() {

        //for start app
        meLatLng = new LatLng(meLatADouble, meLngADouble); //เอาค่าไปใส่แผนที่

    }

    private void getLatLngForIntent() {

        meLatADouble = getIntent().getDoubleExtra("Lat", 14.47723421);
        meLngADouble = getIntent().getDoubleExtra("Lng", 100.64575195);

        meIDString = getIntent().getStringExtra("meID");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // กำหนดจุดเริ่มต้นให้แผนที่
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meLatLng, 16)); //ถ้าเลขยิ่งเยอะยิ่งใกล้


        createMakerMe();

        myLoopCreateMarker();


    }//Maps Method

    private void myLoopCreateMarker() {

        Log.d("31", "meID ==> " + meIDString);
        Log.d("31", "meID ==> " + latADouble);
        Log.d("31", "meID ==> " + lngADouble);

        updateToMySQL(meIDString, Double.toString(latADouble), Double.toString(lngADouble));//ส่งค่าไปเลยทีเดียว
        //แปลง double to string ด้วย

        meLatLng = new LatLng(latADouble, lngADouble);
        mMap.clear();
        createMakerMe();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoopCreateMarker();
            }
        }, 3000); //3 วินาที

    }//myLoopCreateMarker

    private void updateToMySQL(String meIDString, String strLat, String strLng) {


        //connect
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            ArrayList<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            valuePairs.add(new BasicNameValuePair("isAdd", "true"));
            valuePairs.add(new BasicNameValuePair("_id", meIDString));
            valuePairs.add(new BasicNameValuePair("Lat", strLat));
            valuePairs.add(new BasicNameValuePair("Lng", strLng));


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_edit_location.php");
            httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
            httpClient.execute(httpPost);

        }catch (Exception e){


            Log.d("31", "ERROR Update ==> " +e.toString());
        }


    }// update


    private void createMakerMe() {

        mMap.addMarker(new MarkerOptions()
                .position(meLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));
    }//createMakerMe


}//Main Class
