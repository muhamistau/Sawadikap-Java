package com.ppl2jt.sawadikap_java.job;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ppl2jt.sawadikap_java.fragments.signup.AddressFragment;
import com.ppl2jt.sawadikap_java.fragments.signup.EmailFragment;
import com.ppl2jt.sawadikap_java.fragments.signup.PasswordFragment;

public class SignupPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;

    public SignupPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new EmailFragment();
            case 1: return new PasswordFragment();
            default: return new AddressFragment();
//            default: return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
