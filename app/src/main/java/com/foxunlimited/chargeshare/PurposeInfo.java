package com.foxunlimited.chargeshare;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by bewus on 5/14/2016.
 */
    public class PurposeInfo{
        public double Lat;
        public double Lng;
        public String phone;
        public String description;

        public PurposeInfo()
        {

        }

        public PurposeInfo(LatLng coords, String phone, String description){
            Lat = coords.latitude;
            Lng = coords.longitude;
            this.phone = phone;
            this.description = description;
        }
}
