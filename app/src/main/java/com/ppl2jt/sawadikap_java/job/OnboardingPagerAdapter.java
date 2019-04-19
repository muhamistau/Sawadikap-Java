package com.ppl2jt.sawadikap_java.job;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ppl2jt.sawadikap_java.fragments.onboarding.On1Fragment;
import com.ppl2jt.sawadikap_java.fragments.onboarding.On2Fragment;
import com.ppl2jt.sawadikap_java.fragments.onboarding.On3Fragment;
import com.ppl2jt.sawadikap_java.fragments.onboarding.On4Fragment;

public class OnboardingPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 4;

    public OnboardingPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new On1Fragment();
            case 1: return new On2Fragment();
            case 2: return new On3Fragment();
            default: return new On4Fragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
