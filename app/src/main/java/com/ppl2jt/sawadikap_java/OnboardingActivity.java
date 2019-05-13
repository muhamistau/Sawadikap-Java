package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ppl2jt.sawadikap_java.job.OnboardingPagerAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class OnboardingActivity extends AppCompatActivity {

    OnboardingPagerAdapter onboardingPagerAdapter;
    ViewPager onboardingViewPager;
    Button nextButton;
    WormDotsIndicator dotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        dotsIndicator = findViewById(R.id.worm_dots_indicator);

        onboardingPagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());
        onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingPagerAdapter);
        dotsIndicator.setViewPager(onboardingViewPager);
        nextButton = findViewById(R.id.buttonLanjut);
    }

    public void next(View view) {
        if (onboardingViewPager.getCurrentItem() == onboardingPagerAdapter.getCount() - 1) {
            intentToAndSetFirstRun();
        } else {
            onboardingViewPager.setCurrentItem(
                    onboardingViewPager.getCurrentItem() + 1, true);
        }
    }

    public void skip(View view) {
        intentToAndSetFirstRun();
    }

    public void intentToAndSetFirstRun() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

        //* Check firstRun State from sharedPreferences
        //! Commented for development purpose
        // Todo: Uncomment before Demo
        getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE).edit()
                .putBoolean("firstRun", false).apply();
    }
}
