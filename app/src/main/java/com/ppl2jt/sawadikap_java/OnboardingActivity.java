package com.ppl2jt.sawadikap_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ppl2jt.sawadikap_java.job.OnboardingPagerAdapter;

public class OnboardingActivity extends AppCompatActivity {

    OnboardingPagerAdapter onboardingPagerAdapter;
    ViewPager onboardingViewPager;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        onboardingPagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());
        onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingPagerAdapter);
        nextButton = findViewById(R.id.buttonLanjut);
    }

    public void next(View view) {
        onboardingViewPager.setCurrentItem(
                onboardingViewPager.getCurrentItem() + 1, true);
    }

    public void skip(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
