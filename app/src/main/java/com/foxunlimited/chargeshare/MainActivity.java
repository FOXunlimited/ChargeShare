package com.foxunlimited.chargeshare;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    class MarkerInfo {
        Marker marker;
        int i;
        int j;

        public MarkerInfo(Marker marker, int i, int j) {
            this.marker = marker;
            this.i = i;
            this.j = j;
        }
    }

    SupportMapFragment map;
    GoogleMapOptions options = new GoogleMapOptions();
    boolean loginStatus;
    int globalI;
    int globalJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding purposes intent
        Button btnAddPurpose = (Button) findViewById(R.id.btn_add_purpose);
        btnAddPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddPurposeActivity.class);
                startActivity(i);
            }
        });

        /*Tabs initialization*/
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.purposes);
        spec.setIndicator("Purposes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.my_purposes);
        spec.setIndicator("My Purposes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setIndicator("Log Out");

        tabs.setCurrentTab(0);

        /*Map initialization*/
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(true)
                .rotateGesturesEnabled(true);
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
// Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        }
        ArrayList<User> users = App.getTestArray();
        final ArrayList<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>();
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.get(i).purposes.size(); j++) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(users.get(i).purposes.get(j).coords)
                        .draggable(false));
                markerInfos.add(new MarkerInfo(marker, i, j));
            }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker mark) {
                    for (int i = 0; i < markerInfos.size(); i++) {
                        if (markerInfos.get(i).marker.equals(mark)) {
                            Intent intent = new Intent(App.getContext(), PurposeActivity.class);
                            intent.putExtra("user_index", markerInfos.get(i).i);
                            intent.putExtra("purpose_index", markerInfos.get(i).j);
                            startActivity(intent);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}