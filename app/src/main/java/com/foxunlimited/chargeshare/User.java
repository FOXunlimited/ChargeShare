package com.foxunlimited.chargeshare;

import java.util.List;

/**
 * Created by bewus on 5/14/2016.
 */
public class User {
    public String userId;
    public String mail;
    public String pass;
    public String nick;
    public List<PurposeInfo> purposes;

    public User(String mail, String pass, String nick) {
        this.mail = mail;
        this.pass = pass;
        this.nick = nick;
    }
}
