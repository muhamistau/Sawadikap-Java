package com.ppl2jt.sawadikap_java.job;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ppl2jt.sawadikap_java.model.Clothes;

import java.util.List;

public class ClothesAdapter extends BaseAdapter {

    private List<Clothes> clothesList;
    private Context context;

    public ClothesAdapter(Context context, List<Clothes> clothesList) {
        this.clothesList = clothesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = View.inflate(context, android.R.layout.simple_list_item_1, null);
        Clothes c = clothesList.get(position);
        ((TextView) rowView.findViewById(android.R.id.text1)).
                setText(c.getId() + " " + c.getGender());
        return rowView;
    }
}
