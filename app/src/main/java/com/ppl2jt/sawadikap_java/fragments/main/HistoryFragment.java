package com.ppl2jt.sawadikap_java.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.job.RequestsAdapter;
import com.ppl2jt.sawadikap_java.model.Requests;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    ArrayList<Requests> requestsArrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        requestsArrayList = new ArrayList<>();
        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah disedekahkan"));

        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah diambil oleh kurir"));

        requestsArrayList.add(new Requests(0, 1, "test",
                "Telah ditolak"));

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RequestsAdapter(requestsArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
