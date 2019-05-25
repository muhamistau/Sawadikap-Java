package com.ppl2jt.sawadikap_java.job;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.model.Clothes;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    private ArrayList<Clothes> clothesArrayList;
    private CustomItemClickListener listener;

    public ClothesAdapter(ArrayList<Clothes> clothesArrayList, CustomItemClickListener listener) {
        this.clothesArrayList = clothesArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClothesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_item,
                parent, false);

        return new ClothesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesAdapter.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        Glide.with(holder.itemView.getContext()).load(clothesArrayList.get(position).getPicUrl())
                .into(holder.clothesImage);
        holder.category.setText(clothesArrayList.get(position).getCategory());
        holder.age.setText(clothesArrayList.get(position).getAge());
        holder.status.setText(clothesArrayList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return clothesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView clothesImage;
        public TextView category;
        public TextView age;
        public TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clothesImage = itemView.findViewById(R.id.image);
            category = itemView.findViewById(R.id.category);
            age = itemView.findViewById(R.id.age);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, this.getAdapterPosition());
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clothes_item,
//                    parent, false);
//        }
//
//        ImageView clothesImage = convertView.findViewById(R.id.image);
//        TextView clothesCategory = convertView.findViewById(R.id.category);
//        TextView clothesAge = convertView.findViewById(R.id.age);
//        TextView clothesStatus = convertView.findViewById(R.id.status);
//
//        Glide.with(context).load(clothesList.get(position).getPicUrl()).into(clothesImage);
//        clothesCategory.setText(clothesList.get(position).getCategory());
//        clothesAge.setText(clothesList.get(position).getAge());
//        clothesStatus.setText(clothesList.get(position).getStatus());
//
//        return convertView;
//    }
}

//public class ClothesAdapter extends ArrayAdapter<Clothes> {
//
//    private List<Clothes> clothesList;
//    private Context context;
//    LayoutInflater inflater;
//
//    public ClothesAdapter(Context context, List<Clothes> clothesList) {
//        super(context, 0, clothesList);
//        this.clothesList = clothesList;
//        this.context = context;
//        inflater = (LayoutInflater.from(context));
//    }
//
//    @Override
//    public int getCount() {
//        return clothesList.size();
//    }
//
//    @Override
//    public Clothes getItem(int position) {
//        return clothesList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clothes_item,
//                    parent, false);
//        }
//
//        ImageView clothesImage = convertView.findViewById(R.id.image);
//        TextView clothesCategory = convertView.findViewById(R.id.category);
//        TextView clothesAge = convertView.findViewById(R.id.age);
//        TextView clothesStatus = convertView.findViewById(R.id.status);
//
//        Glide.with(context).load(clothesList.get(position).getPicUrl()).into(clothesImage);
//        clothesCategory.setText(clothesList.get(position).getCategory());
//        clothesAge.setText(clothesList.get(position).getAge());
//        clothesStatus.setText(clothesList.get(position).getStatus());
//
//        return convertView;
//    }
//}
