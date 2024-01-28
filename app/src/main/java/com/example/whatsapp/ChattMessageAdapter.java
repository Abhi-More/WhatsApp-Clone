package com.example.whatsapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChattMessageAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChattMessageAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    public ChattMessageAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                String recRoom = recId + FirebaseAuth.getInstance().getUid();
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModels.get(position).getMessageId())
                                        .setValue(null);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });

        MessageModel model = messageModels.get(position);
        if(holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(model.getMessage());
            try
            {
                Date netDate = new Date(model.getTimeStamp());
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
                ((SenderViewHolder)holder).senderTime.setText(sdf.format(netDate));
            }
            catch (Exception e)
            {}
        }
        else
        {
            ((ReceiverViewHolder)holder).receiverMsg.setText(model.getMessage());
            try
            {
                Date netDate = new Date(model.getTimeStamp());
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
                ((ReceiverViewHolder)holder).receiverTime.setText(sdf.format(netDate));
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMsg, receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.tv_receiver_text);
            receiverTime = itemView.findViewById(R.id.tv_receiver_time);
        }
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.tv_sender_text);
            senderTime = itemView.findViewById(R.id.tv_sender_time);
        }
    }
}
