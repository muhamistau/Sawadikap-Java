package com.ppl2jt.sawadikap_java.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.model.Clothes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    private List<Clothes> clothesList;
    private Context context;
    LayoutInflater inflater;

    public ClothesAdapter(Context context, List<Clothes> clothesList) {
        super(context, 0, clothesList);
        this.clothesList = clothesList;
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return clothesList.size();
    }

    @Override
    public Clothes getItem(int position) {
        return clothesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clothes_item,
                    parent, false);
        }

        ImageView clothesImage = convertView.findViewById(R.id.image);
        TextView clothesCategory = convertView.findViewById(R.id.category);
        TextView clothesAge = convertView.findViewById(R.id.age);
        TextView clothesStatus = convertView.findViewById(R.id.status);

        Picasso.get().load(clothesList.get(position).getPicUrl()).into(clothesImage);
        clothesCategory.setText(clothesList.get(position).getCategory());
        clothesAge.setText(clothesList.get(position).getAge());
        clothesStatus.setText(clothesList.get(position).getStatus());

        return convertView;
    }
}
