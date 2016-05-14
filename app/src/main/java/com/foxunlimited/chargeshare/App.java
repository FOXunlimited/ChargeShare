package com.foxunlimited.chargeshare;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by bohdan on 14.05.16.
 */
public class App extends Application {

    private static User user;

    public static User getUser(){
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
}