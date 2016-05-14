package com.foxunlimited.chargeshare;

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
    public Firebase ref = new Firebase("https://chargeshare.firebaseio.com/");
    public String userId;
    public String mail;
    public String pass;
    public String nick;
    public List<PurposeInfo> purposes;


    public UserFirebaseManager(String mail, String pass, String nick) {
        this.mail = mail;
        this.pass = pass;
        this.nick = nick;
    }

    public void CreateUser() {
        ref.createUser(mail, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.EMAIL_TAKEN:
                        System.out.println("The new user account cannot be created because the email is already in use.");
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        System.out.println("The specified email is not a valid email.");
                        break;
                    default:
                        System.out.println(firebaseError.getMessage());
                }
            }
        });
    }

    public void LoginUser() {
        ref.authWithPassword(mail, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                userId = ref.getAuth().getUid();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        System.out.println("User does not exist");
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        System.out.println("Wrong password");
                        break;
                    default:
                        System.out.println(firebaseError.getMessage());
                }
            }
        });
    }

    public void AddPurpose(LatLng coords, String phone, String description) {
        PurposeInfo info = new PurposeInfo(coords, phone, description);
        purposes.add(info);
        Firebase userRef = ref.child("users").child(userId).child("purposes");//.child(Integer.toString(purposes.size()));
        userRef.push().setValue(info);
    }

    /*public List<purposeInfo> SearchForCharge(String cur_adress)
    {

    }*/

    // public void RemovePropose
}
