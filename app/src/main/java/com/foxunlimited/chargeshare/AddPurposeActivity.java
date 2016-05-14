package com.foxunlimited.chargeshare;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * Created by bewus on 5/14/2016.
 */
public class AddPurposeActivity extends AppCompatActivity {

    EditText placeView;
    EditText phoneNumberView;
    EditText description;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purpose);
        placeView = (EditText) findViewById(R.id.txt_place);
        phoneNumberView = (EditText)findViewById(R.id.txt_phone_number);
        description = (EditText)findViewById(R.id.txt_description);
        user = App.getUser();
        placeView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openAutocompleteActivity();
            }
        });
        placeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View arg0, boolean arg1) {
                if(placeView.hasFocus()) {
                    openAutocompleteActivity();
                }
            }
        });
        //Nathalie, add!!!
        Button confirmAddPurpose = (Button)findViewById(R.id.btn_confirm_add_purpose);
        if(user == null){
            confirmAddPurpose.setEnabled(false);
        }
        confirmAddPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurposeInfo purposeInfo = new PurposeInfo(getLocationFromAddress(AddPurposeActivity.this, placeView.getText().toString()),
                        phoneNumberView.getText().toString(),description.getText().toString());
                UserFirebaseManager.AddPurpose(user, getLocationFromAddress(AddPurposeActivity.this,
                        placeView.getText().toString()), phoneNumberView.getText().toString(),description.getText().toString());
                App.getUser().purposes.add(purposeInfo);
            }
        });
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("asf", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("autocomplete", "Place Selected: " + place.getName());
                placeView.setText(place.getAddress());

            }
        }
    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
