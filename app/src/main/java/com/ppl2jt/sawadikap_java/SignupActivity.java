package com.ppl2jt.sawadikap_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ppl2jt.sawadikap_java.job.SignupPagerAdapter;

public class SignupActivity extends AppCompatActivity {

    SignupPagerAdapter pagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new SignupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
}
