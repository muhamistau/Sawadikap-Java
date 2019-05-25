package com.ppl2jt.sawadikap_java.fragments.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.WardrobeDetailActivity;
import com.ppl2jt.sawadikap_java.constant.Url;
import com.ppl2jt.sawadikap_java.job.ClothesAdapter;
import com.ppl2jt.sawadikap_java.job.CustomItemClickListener;
import com.ppl2jt.sawadikap_java.model.Clothes;

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
public class WardrobeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerFrameLayout;
    private static ProgressDialog mProgressDialog;
    private ArrayList<Clothes> clothesArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private TextView refreshText;

    public WardrobeFragment() {
        // Required empty public constructor
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshText = view.findViewById(R.id.refreshText);

        clothesArrayList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJSON();
            }
        });

        return view;
    }

    private void fetchJSON() {

//        showSimpleProgressDialog(getActivity(), "Loading...", "Fetching Json",
//                false);

        swipeRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();

        int userId = getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getInt("userId", 0);

        final Request request = new Request.Builder()
                .url(Url.userPakaian(userId))
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
                        swipeRefreshLayout.setRefreshing(false);
                        refreshText.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                    }
                });
//                removeSimpleProgressDialog();
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

                        JSONArray dataArray = new JSONArray(stringResponse);

                        JSONObject dataObject;

                        clothesArrayList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {

                            dataObject = dataArray.getJSONObject(i);
                            Log.d("Object" + i, dataObject.getString("jenis_baju"));

                            clothesArrayList.add(
                                    new Clothes(
                                            dataObject.getInt("id_pakaian"),
                                            dataObject.getInt("id_user"),
                                            dataObject.getInt("id_request"),
                                            dataObject.getString("jenis_ukuran"),
                                            dataObject.getString("jenis_gender"),
                                            dataObject.getString("jenis_usia"),
                                            dataObject.getString("jenis_baju"),
                                            dataObject.getString("foto"),
                                            dataObject.getString("status")
                                    )
                            );

                            Log.d("DATA", "Data: " + dataObject.getString("foto"));

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutManager = new LinearLayoutManager(getActivity());
                                adapter = new ClothesAdapter(clothesArrayList,
                                        new CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(View v, int position) {
                                                Intent intent = new Intent(getActivity(),
                                                        WardrobeDetailActivity.class);
                                                intent.putExtra("foto",
                                                        clothesArrayList.get(position).getPicUrl());
                                                intent.putExtra("jenis_baju",
                                                        clothesArrayList.get(position).getCategory());
                                                intent.putExtra("jenis_usia",
                                                        clothesArrayList.get(position).getAge());
                                                intent.putExtra("jenis_ukuran",
                                                        clothesArrayList.get(position).getSize());
                                                intent.putExtra("status",
                                                        clothesArrayList.get(position).getStatus());
                                                intent.putExtra("id_pakaian",
                                                        clothesArrayList.get(position).getId());
                                                startActivity(intent);
                                            }
                                        });
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                refreshText.setVisibility(View.GONE);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
