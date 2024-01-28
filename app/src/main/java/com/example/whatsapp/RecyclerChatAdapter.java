package com.example.whatsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerChatAdapter extends RecyclerView.Adapter<RecyclerChatAdapter.ViewHolder> {
    Context context;
//    public ArrayList<ChatModel> arrayChat;
    public  ArrayList<User> arrayChat;
    RecyclerChatAdapter(Context context, ArrayList<User> arrayChat) {
         this.context = context;
         this.arrayChat = arrayChat;
    }

    @NonNull
    @Override
    public RecyclerChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChatAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        User user = arrayChat.get(position);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.whatsapp_avatar).into(holder.chat_img);
        holder.tv_chat_name.setText(arrayChat.get(position).getUserName());

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUserId())
                .orderByChild("timeStamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                            for(DataSnapshot snapshot1 : snapshot.getChildren())
                            {
                                holder.tv_chat_msg.setText(snapshot1.child("message").getValue(String.class));
                                try
                                {
                                    Date netDate = new Date(String.valueOf(snapshot.child("timeStamp")));
                                    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
                                    holder.tv_chat_time.setText(sdf.format(netDate));
                                }
                                catch (Exception e)
                                {}
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Chat_Activity.class);

                i.putExtra("Chat_Id", user.getUserId());
                i.putExtra("Chat_Img", user.getProfilePic());
                i.putExtra("Chat_Name", user.getUserName());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayChat.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder {

        TextView tv_chat_name, tv_chat_time, tv_chat_msg, tv_chat_unread_msg;
        ImageView chat_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_chat_name = itemView.findViewById(R.id.tv_chat_name);
//            tv_chat_time = itemView.findViewById(R.id.tv_chat_time);
            tv_chat_msg = itemView.findViewById(R.id.tv_chat_msg);
//            tv_chat_unread_msg = itemView.findViewById(R.id.tv_chat_unread_msg);
            chat_img = itemView.findViewById(R.id.chat_img);
        }
    }
}
