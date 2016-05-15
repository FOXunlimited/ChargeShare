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
    static ArrayList<User> users = new ArrayList<User>();

    public static User getUser() {
        return user;
    }

    public static void setUser(User usr) {
        user = usr;
    }

    private static Application sContext;

    public App() {
        sContext = this;
    }

    public static ArrayList<User> getUsersArray() {

        UserFirebaseManager.GetAllUsers(new UserFirebaseManager.GetProposesListener() {
            @Override
            public void onGet(ArrayList<User> proposes) {
                users = proposes;
            }

            @Override
            public void onCancel() {

            }
        });
        return users;
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