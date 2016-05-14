package com.foxunlimited.chargeshare;

import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
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

    public static void CreateUser(final User user, final UserLoginListener listener) {

        ref.createUser(user.mail, user.pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid");
                user.userId = (String) result.get("uid");
                Firebase usersRef = ref.child("users").child(user.userId);
                usersRef.setValue(user);
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

    public static void LoginUser(final User user) {

        ref.authWithPassword(user.mail, user.pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Firebase usersref = ref.child("users").child(authData.getUid());
                Map <String, Object> map = new HashMap<String, Object>();
                map.put("provider", authData.getProvider());
//                user = map.get("provider").User.class;
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
        Firebase userRef = ref.child("users").child(user.userId).child("purposes").push();
        userRef.setValue(info);
    }

    public static User[] GetAllUsers()
    {
        Firebase usersref = ref.child("users");
        final User[] users = {null};
        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int countUsers = 0;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users[countUsers] = user;
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return users;
    }

    /*public List<purposeInfo> GetProposes(String cur_adress)
    {

    }*/

    // public void RemovePropose
}
