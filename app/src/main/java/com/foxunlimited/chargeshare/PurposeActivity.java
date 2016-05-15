package com.foxunlimited.chargeshare;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

/**
 * Created by bewus on 5/15/2016.
 */
public class PurposeActivity extends AppCompatActivity {

    TextView purposerName;
    TextView purposerNumber;
    TextView purposerAdress;
    TextView purposerDescription;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);

        purposerName = (TextView)findViewById(R.id.txt_purposer_name);
        purposerNumber = (TextView)findViewById(R.id.txt_purposer_phone_number);
        purposerAdress = (TextView)findViewById(R.id.txt_purposer_adress);
        purposerDescription = (TextView)findViewById(R.id.txt_purposer_description);
        Bundle bundle = getIntent().getExtras();
        lat = App.getUsersArray().get(bundle.getInt("user_index")).Lat;
        lng = App.getUsersArray().get(bundle.getInt("user_index")).Lng;
        purposerName.setText(App.getUsersArray().get(bundle.getInt("user_index")).nick);
        purposerNumber.setText(App.getUsersArray().get(bundle.getInt("user_index")).phone);
        purposerAdress.setText(getCompleteAddressString(lat,
                lng));
        purposerDescription.setText(App.getUsersArray().get(bundle.getInt("user_index")).description);
        Button btnNavi = (Button) findViewById(R.id.btn_navigation);
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
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
