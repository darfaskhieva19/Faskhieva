package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import java.util.Objects;

public class ScreenSplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenSplashActivity.this,MainActivity.class);
                ScreenSplashActivity.this.startActivity(intent);
                ScreenSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
