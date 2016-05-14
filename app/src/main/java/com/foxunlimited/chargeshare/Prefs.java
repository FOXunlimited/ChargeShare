package com.foxunlimited.chargeshare;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.rightutils.rightutils.utils.CacheUtils;

/**
 * Created by bohdan on 15.05.16.
 */
public class Prefs {

    public static final String USER_KEY = "user";
    public static final String USER_MAIL_KEY = "mail";
    public static final String USER_NICK_KEY = "nick";
    public static final String USER_ID_KEY = "userId";
    public static final String USER_PASSWORD_KEY = "pass";


    public static void saveUser(User user) {
        SharedPreferences sp = App.getContext().getSharedPreferences("com.foxunlimited.chargeshare.PREFS", Context.MODE_PRIVATE);
        sp.edit()
        .putString(USER_MAIL_KEY, user.mail)
                .putString(USER_PASSWORD_KEY,user.pass)
                .putString(USER_NICK_KEY,user.nick)
                .putString(USER_ID_KEY,user.userId)
        .apply();

    }

    @Nullable
    public static User getUser() {
        SharedPreferences sp = App.getContext().getSharedPreferences("com.foxunlimited.chargeshare.PREFS", Context.MODE_PRIVATE);
        String id = sp.getString(USER_ID_KEY, null);
        String nick = sp.getString(USER_NICK_KEY,"");
        String mail = sp.getString(USER_MAIL_KEY,"");
        String pass = sp.getString(USER_PASSWORD_KEY,"");


        if (id == null) {
            return null;
        }


        User user = new User();
        user.mail = mail;
        user.userId = id;
        user.nick = nick;
        user.pass = pass;
        return user;
    }

}
