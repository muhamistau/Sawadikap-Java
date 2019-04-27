package com.ppl2jt.sawadikap_java.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ppl2jt.sawadikap_java.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {

    public WardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

}
