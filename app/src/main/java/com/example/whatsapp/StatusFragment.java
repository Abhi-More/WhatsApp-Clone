package com.example.whatsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusFragment extends Fragment {

    View statusFragView;
    CircleImageView status_img;
    RecyclerView recyclerViewStatus;
    FirebaseAuth auth;
    FirebaseDatabase database;
    User user;
    ArrayList<StatusModel> statusRow = new ArrayList<>();
    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        statusFragView = inflater.inflate(R.layout.fragment_status, container, false);

        recyclerViewStatus = statusFragView.findViewById(R.id.recyclerViewStatus);
        recyclerViewStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        status_img = statusFragView.findViewById(R.id.status_img);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                        if(user != null)
                        Picasso.get()
                                .load(user.getProfilePic()).placeholder(R.drawable.whatsapp_avatar)
                                .into(status_img);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        statusRow.clear();
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "5 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Darshan", "7 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Saish Thorat", "10 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "10 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "10 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Darshan", "10 minutes ago"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Saish Thorat", "Today, 11:56 am"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "Today, 11:56 am"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "Today, 11:56 am"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Darshan", "Today, 11:56 am"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Saish Thorat", "Today, 11:56 am"));
        statusRow.add(new StatusModel(R.drawable.whatsapp_avatar, "Abhishek More", "Today, 11:56 am"));

        RecyclerStatusAdapter adapter = new RecyclerStatusAdapter(getActivity(), statusRow);
        recyclerViewStatus.setAdapter(adapter);

        return statusFragView;
    }

}