package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        boolean firstRun = true;
        boolean isLoggedIn = false;

        //* Check firstRun State from sharedPreferences
        //! Commented for development purpose
        // Todo: Uncomment before Demo
        firstRun = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
                .getBoolean("firstRun", true);

        isLoggedIn = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);

        if (firstRun && !isLoggedIn) {
            //* Intent to Onboarding Page
            intent = new Intent(this, OnboardingActivity.class);
        } else if (!firstRun && isLoggedIn) {
            intent = new Intent(this, MainActivity.class);
        } else {
            //* Intent to Login Page
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
