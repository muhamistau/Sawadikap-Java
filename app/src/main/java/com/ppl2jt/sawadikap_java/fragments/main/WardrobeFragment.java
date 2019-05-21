package com.ppl2jt.sawadikap_java.fragments.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.WardrobeDetailActivity;
import com.ppl2jt.sawadikap_java.constant.Url;
import com.ppl2jt.sawadikap_java.job.ClothesAdapter;
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
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    private ArrayList<Clothes> clothesArrayList;
    private ClothesAdapter clothesAdapter;
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
        clothesArrayList = new ArrayList<>();

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        listView = view.findViewById(R.id.listView);
        refreshText = view.findViewById(R.id.refreshText);
//        clothesArrayList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Item clicked " + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WardrobeDetailActivity.class);
                intent.putExtra("id_pakaian", clothesArrayList.get(position).getId());
                intent.putExtra("id_user", clothesArrayList.get(position).getIdUser());
                intent.putExtra("id_request", clothesArrayList.get(position).getIdRequest());
                intent.putExtra("jenis_ukuran", clothesArrayList.get(position).getSize());
                intent.putExtra("jenis_gender", clothesArrayList.get(position).getGender());
                intent.putExtra("jenis_usia", clothesArrayList.get(position).getAge());
                intent.putExtra("jenis_baju", clothesArrayList.get(position).getCategory());
                intent.putExtra("foto", clothesArrayList.get(position).getPicUrl());
                intent.putExtra("status", clothesArrayList.get(position).getStatus());
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJSON();
            }
        });

        fetchJSON();

        return view;
    }

    private void fetchJSON() {

//        showSimpleProgressDialog(getActivity(), "Loading...", "Fetching Json",
//                false);

        swipeRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();

        int userId = getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getInt("userId", 0);

        Request request = new Request.Builder()
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
                                clothesAdapter = new ClothesAdapter(getActivity(), clothesArrayList);
                                listView.setAdapter(clothesAdapter);
                                swipeRefreshLayout.setRefreshing(false);
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
