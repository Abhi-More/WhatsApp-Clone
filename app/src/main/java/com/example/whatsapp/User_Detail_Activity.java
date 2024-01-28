package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class User_Detail_Activity extends AppCompatActivity {

    ImageButton ib_back_activity;
    String receiverId, profilePic, userName, about, email;
    ImageView iv_user_img;
    TextView tv_user_name, tv_username, tv_description, tv_email;
    FirebaseDatabase database;
    User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ib_back_activity = findViewById(R.id.ib_back_activity);
        iv_user_img = findViewById(R.id.iv_user_img);
//        tv_user_name = findViewById(R.id.tv_user_name);
        tv_username = findViewById(R.id.tv_username);
        tv_email = findViewById(R.id.tv_email);
        tv_description = findViewById(R.id.tv_description);


        receiverId = getIntent().getStringExtra("Chat_Id");
//        profilePic = getIntent().getStringExtra("Chat_Img");
//        userName = getIntent().getStringExtra("Chat_Name");

        database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(receiverId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user = snapshot.getValue(User.class);

                                profilePic = user.getProfilePic();
                                userName = user.getUserName();
                                about = user.getAbout();
                                email = user.getMail();

                                Picasso.get().load(profilePic).placeholder(R.drawable.whatsapp_avatar).into(iv_user_img);
//                                tv_user_name.setText(userName);

                                tv_username.setText(userName);
                                tv_email.setText(email);
                                tv_description.setText(about);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        ib_back_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(User_Detail_Activity.this, Chat_Activity.class);

                i.putExtra("Chat_Id", receiverId);
                i.putExtra("Chat_Img", profilePic);
                i.putExtra("Chat_Name", userName);
                startActivity(i);
                finish();
            }
        });
    }
}