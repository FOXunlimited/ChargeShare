package com.foxunlimited.chargeshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bewus on 5/14/2016.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtConfirmPassword;
    User user;
    Button btnSignUp;

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
                if(txtPassword.getText() == txtConfirmPassword.getText()) {
                    user = new User(txtEmail.getText().toString(), txtPassword.getText().toString(), txtName.getText().toString());
                    UserFirebaseManager.CreateUser(user);

                    Intent i  = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords don`t match", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}
