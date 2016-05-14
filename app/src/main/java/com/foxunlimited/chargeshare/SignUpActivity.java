package com.foxunlimited.chargeshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

/**
 * Created by bewus on 5/14/2016.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtConfirmPassword;
    private User user;
    Button btnSignUp;

    public User getUser(){
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        txtName = (EditText)findViewById(R.id.txt_name);
        txtEmail = (EditText)findViewById(R.id.txt_email);
        txtPassword = (EditText)findViewById(R.id.txt_password);
        txtConfirmPassword = (EditText)findViewById(R.id.txt_confirm_password);
        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                    user = new User(txtEmail.getText().toString(), txtPassword.getText().toString(), txtName.getText().toString());
                    UserFirebaseManager.CreateUser(user, new UserFirebaseManager.UserLoginListener() {
                        @Override
                        public void onSuccess() {
                            Intent i  = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords don`t match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
