package com.foxunlimited.chargeshare;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);
        purposerName = (TextView)findViewById(R.id.txt_purposer_name);
        purposerNumber = (TextView)findViewById(R.id.txt_purposer_phone_number);
        purposerAdress = (TextView)findViewById(R.id.txt_purposer_adress);
        purposerDescription = (TextView)findViewById(R.id.txt_purposer_description);
        Bundle bundle = getIntent().getExtras();
        PurposeInfo purposeInfo = App.getUsersArray().get(bundle.getInt("user_index")).purposes.get(bundle.getInt("purpose_index"));
        purposerName.setText(App.getUsersArray().get(bundle.getInt("user_index")).nick);
        purposerNumber.setText(purposeInfo.phone);
        purposerAdress.setText(getCompleteAddressString(purposeInfo.coords.latitude, purposeInfo.coords.longitude));
        purposerDescription.setText(purposeInfo.description);
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
