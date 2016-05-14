package com.foxunlimited.chargeshare;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by bewus on 5/14/2016.
 */
    public class PurposeInfo{
        LatLng coords;
        String phone;
        String description;

        public PurposeInfo(LatLng coords, String phone, String description){
            this.coords = coords;
            this.phone = phone;
            this.description = description;
        }
}
