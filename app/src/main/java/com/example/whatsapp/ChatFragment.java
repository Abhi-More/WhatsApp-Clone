package com.example.whatsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.whatsapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    View chatFragView;
    RecyclerView recyclerViewChat;

//    ArrayList<ChatModel> chatRow = new ArrayList<>();
    ArrayList<User> chatRow = new ArrayList<>();
    FirebaseDatabase database;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        chatFragView =  inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerViewChat = chatFragView.findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));

        database = FirebaseDatabase.getInstance();
        chatRow.clear();
        RecyclerChatAdapter adapter = new RecyclerChatAdapter(getActivity(), chatRow);
        recyclerViewChat.setAdapter(adapter);

        database.getReference().child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatRow.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            assert user != null;
                            user.setUserId(dataSnapshot.getKey());

                            if(user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                            {
                                user.setUserName(user.getUserName() + "(You)");
                            }
                            chatRow.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatRow.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    user.setUserId(dataSnapshot.getKey());

                    if(user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        user.setUserName(user.getUserName() + "(You)");
                    }
                    chatRow.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatFragView;
    }
}