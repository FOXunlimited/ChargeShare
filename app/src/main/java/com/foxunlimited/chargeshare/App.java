package com.foxunlimited.chargeshare;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by bohdan on 14.05.16.
 */
public class App extends Application {

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
