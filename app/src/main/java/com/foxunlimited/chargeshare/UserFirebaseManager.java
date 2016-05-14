package com.foxunlimited.chargeshare;

import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

/**
 * Created by Nathalie Britan on 14.05.2016.
 */
public class UserFirebaseManager {

    interface UserLoginListener {
        void onSuccess();
        void onFailure();
    }
    public static Firebase ref = new Firebase("https://chargeshare.firebaseio.com/");

    public static void CreateUser(User user, final UserLoginListener listener) {

        ref.createUser(user.mail, user.pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid");
                listener.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.EMAIL_TAKEN:
                        Toast.makeText(App.getContext(), "The new user account cannot be created because the email is already in use.", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        Toast.makeText(App.getContext(), "The specified email is not a valid email.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(App.getContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
                listener.onFailure();
            }
        });
    }

    public static void LoginUser(User user) {
        ref.authWithPassword(user.mail, user.pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        Toast.makeText(App.getContext(), "User does not exist", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        Toast.makeText(App.getContext(), "Wrong password", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(App.getContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void AddPurpose(User user, LatLng coords, String phone, String description) {
        PurposeInfo info = new PurposeInfo(coords, phone, description);
        user.purposes.add(info);
        Firebase userRef = ref.child("users").child(user.userId).child("purposes");//.child(Integer.toString(purposes.size()));
        userRef.push().setValue(info);
    }

    /*public List<purposeInfo> SearchForCharge(String cur_adress)
    {

    }*/

    // public void RemovePropose
}
