package com.example.clever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity {

    // Properties instantiation
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Activity initialisation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Splash screen handling
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);
    }
}
