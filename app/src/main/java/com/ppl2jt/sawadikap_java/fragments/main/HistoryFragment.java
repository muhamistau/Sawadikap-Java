package com.ppl2jt.sawadikap_java.fragments.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.job.RequestsAdapter;
import com.ppl2jt.sawadikap_java.model.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Requests> requestsArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        recyclerView = view.findViewById(R.id.recyclerView);

        requestsArrayList = new ArrayList<>();
        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah disedekahkan"));

        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah diambil oleh kurir"));

        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah ditolak"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJSON();
            }
        });

        return view;
    }

    private void fetchJSON() {

        swipeRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();
        String url = "http://sawadikap-endpoint.herokuapp.com/api/request/";

        int userId = getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getInt("userId", 0);

        Request request = new Request.Builder()
                .url(url + userId)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error", "Failed to connect: " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Success", "Success: " + response.code());
                if (response.code() == 200) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                    String stringResponse = response.body().string();

                    try {


                        Log.d("JSONSTRING", stringResponse);
                        JSONArray dataArray = new JSONArray(stringResponse);
                        Log.d("JSONArray", dataArray.length() + "");

                        JSONObject dataObject;

                        requestsArrayList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {

                            dataObject = dataArray.getJSONObject(i);

                            requestsArrayList.add(new Requests(
                                    dataObject.getInt("id_request"),
                                    dataObject.getInt("id_user"),
                                    dataObject.getString("request_date"),
                                    dataObject.getString("status")));

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutManager = new LinearLayoutManager(getActivity());
                                adapter = new RequestsAdapter(requestsArrayList);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchJSON();
    }

}
