package com.foxunlimited.chargeshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bewus on 5/14/2016.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nathalie check, please, is entered email exist
                if(false){
                    //Nathalie, please, check is entered password correct
                    if(false){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "E-mail or password incorrect", Toast.LENGTH_SHORT);
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "E-mail incorrect", Toast.LENGTH_SHORT);
                }
            }
        });

        TextView signUp = (TextView) findViewById(R.id.sign_up);
        signUp.setClickable(true);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        TextView skip = (TextView)findViewById(R.id.txt_skip_login);
        skip.setClickable(true);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
