package com.foxunlimited.chargeshare;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bewus on 5/14/2016.
 */
public class User implements Parcelable {
    public String userId;
    public String mail;
    public String pass;
    public String nick;
    public List<PurposeInfo> purposes;

    public User()
    {

    }
    public User(String mail, String pass, String nick) {
        this.mail = mail;
        this.pass = pass;
        this.nick = nick;
    }

    protected User(Parcel in) {
        userId = in.readString();
        mail = in.readString();
        pass = in.readString();
        nick = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(userId);
        dest.writeString(mail);
        dest.writeString(pass);
        dest.writeString(nick);
    }
}
