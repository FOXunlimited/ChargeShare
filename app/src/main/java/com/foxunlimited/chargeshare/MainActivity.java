package com.foxunlimited.chargeshare;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TabHost;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Tabs initialization*/
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.purposes);
        spec.setIndicator("Purposes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.add_purpose);
        spec.setIndicator("My Purposes");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        /*Map initiqalization*/
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }
}
