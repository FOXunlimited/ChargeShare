package com.foxunlimited.chargeshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by bewus on 5/14/2016.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*if(Prefs.getUser().userId!=null){
            UserFirebaseManager.GetProposes(App.getUser());
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        */
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        if(btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserFirebaseManager.LoginUser(((EditText) findViewById(R.id.txt_email)).getText().toString(), ((EditText) findViewById(R.id.txt_password)).getText().toString(), new UserFirebaseManager.UserLoginListener() {
                        @Override
                        public void onSuccess(String id) {
                            UserFirebaseManager.GetUserById(id, new UserFirebaseManager.GetUserListener() {
                                @Override
                                public void onDone(User user) {
                                    App.setUser(user);
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }

                        @Override
                        public void onFailure() {

                        }
                    });

                }
            });
        }

        TextView signUp = (TextView) findViewById(R.id.sign_up);
        signUp.setClickable(true);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView skip = (TextView) findViewById(R.id.txt_skip_login);
        skip.setClickable(true);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
