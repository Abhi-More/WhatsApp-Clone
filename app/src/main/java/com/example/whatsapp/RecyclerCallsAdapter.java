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

public class RecyclerCallsAdapter extends RecyclerView.Adapter<RecyclerCallsAdapter.ViewHolder> {

    Context context;
    ArrayList<CallsModel> arrayCalls;

    public RecyclerCallsAdapter(Context context, ArrayList<CallsModel> arrayCalls) {
        this.context = context;
        this.arrayCalls = arrayCalls;
    }

    @NonNull
    @Override
    public RecyclerCallsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calls_row_layout, parent, false);
        RecyclerCallsAdapter.ViewHolder viewHolder = new RecyclerCallsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCallsAdapter.ViewHolder holder, int position) {
        holder.call_img.setImageResource(arrayCalls.get(position).img);
        holder.call_name.setText(arrayCalls.get(position).name);
        holder.call_time.setText(arrayCalls.get(position).time);
        holder.call_status_img.setImageResource(arrayCalls.get(position).call_status_img);
        holder.call_type_img.setImageResource(arrayCalls.get(position).call_type_img);
    }

    @Override
    public int getItemCount() {
        return arrayCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView call_img, call_status_img, call_type_img;
        TextView call_name, call_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            call_img = itemView.findViewById(R.id.call_img);
            call_status_img = itemView.findViewById(R.id.call_status_img);
            call_type_img = itemView.findViewById(R.id.call_type_img);
            call_name = itemView.findViewById(R.id.tv_call_name);
            call_time = itemView.findViewById((R.id.tv_call_time));
        }
    }
}
