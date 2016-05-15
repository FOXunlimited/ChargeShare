package com.foxunlimited.chargeshare;

import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nathalie Britan on 14.05.2016.
 */
public class UserFirebaseManager {

    interface UserLoginListener {
        void onSuccess(String id);

        void onFailure();
    }

    interface GetUserListener {
        void onDone(User user);

        void onFailure();
    }

    interface GetProposesListener{
        void onGet(ArrayList<User> proposes);

        void onCancel();

    }

    public static Firebase ref = new Firebase("https://chargeshare.firebaseio.com/");

    public static void CreateUser(final User user, final UserLoginListener listener) {

        ref.createUser(user.mail, user.pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid");
                user.userId = (String) result.get("uid");
                Firebase usersRef = ref.child("users").child(user.userId).child("userId");
                usersRef.setValue(user.userId);
                usersRef = ref.child("users").child(user.userId).child("nick");
                usersRef.setValue(user.nick);
                usersRef = ref.child("users").child(user.userId).child("mail");
                usersRef.setValue(user.mail);
                usersRef = ref.child("users").child(user.userId).child("pass");
                usersRef.setValue(user.pass);
                listener.onSuccess(user.userId);
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

    public static void LoginUser(final String mail, final String pass, final UserLoginListener loginListener) {
        ref.authWithPassword(mail, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                loginListener.onSuccess(authData.getUid());

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
                loginListener.onFailure();
            }
        });
    }

    public static void AddPurpose(User user, LatLng coords, String phone, String description) {
        PurposeInfo info = new PurposeInfo(coords, phone, description);
        user.purposes.add(info);
        Firebase userRef = ref.child("users").child(user.userId).child("purposes").push();
        userRef.setValue(info);
    }

    public static void  GetAllUsers(final GetProposesListener listener) {
        Firebase usersref = ref.child("users");
        final User[] users = {null};
        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int countUsers = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    GetProposes(user);
                    users[countUsers] = user;
                    countUsers++;
                }
                ArrayList<User> allUsers = (ArrayList)Arrays.asList(users);
                listener.onGet(allUsers);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onCancel();
            }
        });
    }

    public static void GetProposes(final User user) {
        Firebase usersref = ref.child("users").child(user.userId).child("purpose");
        final PurposeInfo[] purposes = {null};
        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int countPurposes = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    PurposeInfo info = postSnapshot.getValue(PurposeInfo.class);
                    purposes[countPurposes] = info;
                    countPurposes++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        List<PurposeInfo> all_purposes = Arrays.asList(purposes);
        user.purposes = all_purposes;
    }

    public static void GetUserById(final String id, final GetUserListener userListener) {
        ref.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                GetProposes(user);
                userListener.onDone(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                userListener.onFailure();
            }
        });
    }
}
