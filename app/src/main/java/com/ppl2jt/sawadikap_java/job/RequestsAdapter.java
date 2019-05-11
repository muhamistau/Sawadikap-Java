package com.ppl2jt.sawadikap_java.job;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ppl2jt.sawadikap_java.R;
import com.ppl2jt.sawadikap_java.model.Requests;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    ArrayList<Requests> requestsArrayList;
    CustomItemClickListener listener;

    public RequestsAdapter(ArrayList<Requests> requestsArrayList, CustomItemClickListener listener) {
        this.requestsArrayList = requestsArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        holder.type.setText(requestsArrayList.get(position).getRequestDate());
        holder.location.setText(requestsArrayList.get(position).getRequestDate());
        holder.status.setText(requestsArrayList.get(position).getStatus());

        if (requestsArrayList.get(position).getStatus().contains("sedekah")) {
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.colorAccent));
            holder.cardView.setRadius(16);
            holder.icon.setImageResource(R.drawable.ic_check_black_24dp);
            holder.status.setTextColor(res.getColor(R.color.colorAccent));
        } else if (requestsArrayList.get(position).getStatus().contains("kurir")) {
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.materialYellow));
            holder.cardView.setRadius(16);
            holder.icon.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
            holder.status.setTextColor(res.getColor(R.color.materialYellow));
        } else {
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.materialRed));
            holder.cardView.setRadius(16);
            holder.icon.setImageResource(R.drawable.ic_close_black_24dp);
            holder.status.setTextColor(res.getColor(R.color.materialRed));
        }
    }

    @Override
    public int getItemCount() {
        return requestsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView type;
        public TextView location;
        public TextView status;
        public CardView cardView;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.category);
            location = itemView.findViewById(R.id.location);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.image);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, this.getAdapterPosition());

        }
    }
}
