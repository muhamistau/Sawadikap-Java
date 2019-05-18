package com.ppl2jt.sawadikap_java.fragments.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.constant.Url;
import com.ppl2jt.sawadikap_java.job.CustomItemClickListener;
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

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Requests> requestsArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CardView statusCard;
    private ImageView statusIcon;
    private TextView statusText;
    private TextView clothesText;
    private TextView locationText;
    private TextView timeText;
    private TextView refreshText;

    private BottomSheetBehavior bottomSheetBehavior;

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
        View bottomSheet = view.findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        statusCard = view.findViewById(R.id.statusCard);
        statusIcon = view.findViewById(R.id.statusIcon);
        statusText = view.findViewById(R.id.statusText);
        clothesText = view.findViewById(R.id.clothesText);
        locationText = view.findViewById(R.id.locationText);
        timeText = view.findViewById(R.id.timeText);
        refreshText = view.findViewById(R.id.refreshText);

        requestsArrayList = new ArrayList<>();

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

        int userId = getActivity().getSharedPreferences("PREFERENCE_STORY",
                getActivity().MODE_PRIVATE).getInt("userId", 0);

        final Request request = new Request.Builder()
                .url(Url.userRequest(userId))
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
                                    dataObject.getString("penerima"),
                                    dataObject.getString("request_date"),
                                    dataObject.getString("status"),
                                    dataObject.getString("jenis_baju")
                            ));

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutManager = new LinearLayoutManager(getActivity());
                                adapter = new RequestsAdapter(requestsArrayList,
                                        new CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(View v, int position) {
//                                                Toast.makeText(getActivity(), "Item " + position,
//                                                        Toast.LENGTH_SHORT).show();

                                                if (bottomSheetBehavior.getState() ==
                                                        BottomSheetBehavior.STATE_EXPANDED) {
                                                    bottomSheetBehavior
                                                            .setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                } else {
                                                    bottomSheetBehavior
                                                            .setState(BottomSheetBehavior.STATE_EXPANDED);
                                                }

                                                if (requestsArrayList.get(position).getStatus()
                                                        .contains("sedekah")) {
                                                    statusIcon.setImageResource(
                                                            R.drawable.ic_check_black_24dp);
                                                    statusCard.setCardBackgroundColor(getResources()
                                                            .getColor(R.color.colorAccent));
                                                } else if (requestsArrayList.get(position).getStatus()
                                                        .contains("kurir")) {
                                                    statusIcon.setImageResource(
                                                            R.drawable.ic_arrow_forward_black_24dp);
                                                    statusCard.setCardBackgroundColor(getResources()
                                                            .getColor(R.color.materialYellow));
                                                } else {
                                                    statusIcon.setImageResource(
                                                            R.drawable.ic_close_black_24dp);
                                                    statusCard.setCardBackgroundColor(getResources()
                                                            .getColor(R.color.materialRed));
                                                }
                                                // Set Status Text from clicked Request
                                                statusText.setText(requestsArrayList.get(position)
                                                        .getStatus());
                                                clothesText.setText(requestsArrayList.get(position)
                                                        .getClothesType());
                                                // Set Location Text from clicked Request
                                                if (requestsArrayList.get(position)
                                                        .getReceiver().equals("") ||
                                                        requestsArrayList.get(position)
                                                                .getReceiver() == NULL) {
                                                    locationText.setText("Belum disedekahkan");
                                                } else {
                                                    locationText.setText(requestsArrayList.get(position)
                                                            .getReceiver());
                                                }
                                                // Set Time Text from clicked Request
                                                timeText.setText(requestsArrayList.get(position)
                                                        .getRequestDate());

                                            }
                                        });
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
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
