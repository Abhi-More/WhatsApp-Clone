package com.example.whatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerStatusAdapter extends RecyclerView.Adapter<RecyclerStatusAdapter.ViewHolder> {
    Context context;
    ArrayList<StatusModel> arrayStatus;
    RecyclerStatusAdapter(Context context, ArrayList<StatusModel> arrayStatus) {
                this.context = context;
                this.arrayStatus = arrayStatus;
    }

    @NonNull
    @Override
    public RecyclerStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_row_layout, parent, false);
        RecyclerStatusAdapter.ViewHolder viewHolder = new RecyclerStatusAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerStatusAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(arrayStatus.get(position).img);
        holder.tv_status_name.setText(arrayStatus.get(position).name);
        holder.tv_status_time.setText(arrayStatus.get(position).time);
    }

    @Override
    public int getItemCount() {
        return arrayStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv_status_name, tv_status_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_status_name = itemView.findViewById(R.id.tv_status_name);
            tv_status_time = itemView.findViewById(R.id.time_ago);
            img = itemView.findViewById(R.id.status_img);
        }
    }
}
