package com.ppl2jt.sawadikap_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        Boolean firstRun = true;

        //* Check firstRun State from sharedPreferences
        //! Commented for development purpose
        // Todo: Uncomment before Demo
//        firstRun = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
//                .getBoolean("firstRun", true);

        if (firstRun){
            //* Intent to Onboarding Page
            intent = new Intent(this, OnboardingActivity.class);
        } else {
            //* Intent to Login Page
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
