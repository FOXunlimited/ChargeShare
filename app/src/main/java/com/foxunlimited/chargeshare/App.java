package com.foxunlimited.chargeshare;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bohdan on 14.05.16.
 */
public class App extends Application {

    private static User user;

    public static User getUser(){
        user = new User("mail", "pass", "hdfh");
        user.purposes = new ArrayList<PurposeInfo>();
        for(int i=0;i<10;i++){
            user.mail = "dsadsa";
            user.nick = "fdsfsdf";
            user.pass = "fsdfsd";
            user.userId = "fsdfsd";
            user.purposes.add(new PurposeInfo(new LatLng(50*i*0.225565642,50*i*0.45478744+20), "380938 "+ i + "567", "My name is dfgdfhh"));
        }
        return user;
    }
    public static void setUser(User usr){
        user = usr;
    }

    private static Application sContext;

    public App() {
        sContext = this;
    }

    public static Context getContext() {
        return sContext.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    public static ArrayList<User> getTestArray() {
        ArrayList<User> testArray = new ArrayList<User>();
        User user = new User();
        user.purposes = new ArrayList<PurposeInfo>();
        for(int i=0;i<10;i++){
            user.mail = "dsadsa";
            user.nick = "fdsfsdf";
            user.pass = "fsdfsd";
            user.userId = "fsdfsd";
            user.purposes.add(new PurposeInfo(new LatLng(50*i*0.225565642,50*i*0.45478744+20), "380938 "+ i + "567", "My name is dfgdfhh"));
        }
        testArray.add(user);
        return testArray;
    }
}