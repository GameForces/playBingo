package com.example.playbingo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.BLACK)
                .withHeaderText("")
                .withFooterText("Welcome Back")
                .withBeforeLogoText("App developed by")
                .withAfterLogoText("Gourav Goel \n Vivek Moar")
                .withLogo(R.drawable.logo2);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);

        View easySplashScreen=config.create();
        setContentView(easySplashScreen);
    }
}
