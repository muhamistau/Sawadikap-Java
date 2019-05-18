package com.ppl2jt.sawadikap_java.fragments.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.constant.Url;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView name;
    TextView username;
    TextView counter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        name = view.findViewById(R.id.nama);
        username = view.findViewById(R.id.username);
        counter = view.findViewById(R.id.jumlahSedekah);

        fetchJSON();

        name.setText(getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getString("username", "no name"));
        username.setText(getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getString("email", "no name"));

        return view;
    }

    private void fetchJSON() {

        OkHttpClient client = new OkHttpClient();

        int userId = getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getInt("userId", 0);

        final Request request = new Request.Builder()
                .url(Url.userNumberOfSedekah(userId))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error", "Failed to connect: " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText("0");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Success", "Success: " + response.code());
                if (response.code() == 200) {

                    final String stringResponse = response.body().string();

                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                counter.setText(stringResponse);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
