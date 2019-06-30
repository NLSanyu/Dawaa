package com.example.android.dawaa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    public static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                /*if(mAuth.getCurrentUser() != null){
                    Intent HomeIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(HomeIntent);
                    finish();
                } else {
                    Intent LoginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(LoginIntent);
                    finish();
                }*/

                Intent LoginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
                finish();
            }

        },SPLASH_TIME_OUT);




    }
}
