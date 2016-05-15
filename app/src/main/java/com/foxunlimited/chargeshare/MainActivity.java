package com.foxunlimited.chargeshare;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
    LinearLayout listMyProposes;
    RelativeLayout yourPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yourPurpose = (RelativeLayout)findViewById(R.id.your_purpose);
        yourPurpose.setHovered(true);



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
        ArrayList<User> users = App.getUsersArray();
        final ArrayList<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>();
        for (int i = 0;users !=null && i < users.size(); i++) {
            for (int j = 0;users.get(i).purposes!=null && j < users.get(i).purposes.size(); j++) {
                LatLng latLng = new LatLng(users.get(i).purposes.get(j).Lat,users.get(i).purposes.get(j).Lng);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
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
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}