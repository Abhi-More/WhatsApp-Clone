package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp.Model.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Chat_Activity extends AppCompatActivity {

    ImageButton btn_emoji;
    TextView tvChatName;
    ImageView img_chat;
    ImageButton img_btn_back;
    ImageButton ib_chat_mic;
    EditText et_chat_input;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView rv_chat;
    ImageButton img_btn_chat_more;
    String receiverId, profilePic, userName;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tvChatName = findViewById(R.id.tvChatName);
        img_btn_back = findViewById(R.id.img_btn_back);
        img_chat = findViewById(R.id.img_chat);
        ib_chat_mic = findViewById(R.id.ib_chat_mic);
        et_chat_input = findViewById(R.id.et_chat_input);
        img_btn_chat_more = findViewById(R.id.img_btn_chat_more);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        receiverId = getIntent().getStringExtra("Chat_Id");
        profilePic = getIntent().getStringExtra("Chat_Img");
        userName = getIntent().getStringExtra("Chat_Name");

        Picasso.get().load(profilePic).placeholder(R.drawable.whatsapp_avatar).into(img_chat);
        tvChatName.setText(userName);

        ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChattMessageAdapter chattMessageAdapter = new ChattMessageAdapter(messageModels, this, receiverId);
        rv_chat = findViewById(R.id.rv_chat);
        rv_chat.setAdapter(chattMessageAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_chat.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);


        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;

        database.getReference().child("chats")
                        .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            model.setMessageId(dataSnapshot.getKey());
                            messageModels.add(model);
                        }
                        chattMessageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("chats")
                .child(senderRoom).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Chat_Activity.this);
                        rv_chat.setLayoutManager(layoutManager);
                        layoutManager.setStackFromEnd(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        et_chat_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String message = et_chat_input.getText().toString().trim();
                if(!message.isEmpty())
                {
                    ib_chat_mic.setImageResource(R.drawable.send_img);
                    ib_chat_mic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final MessageModel model = new MessageModel(senderId, et_chat_input.getText().toString());
                            model.setTimeStamp(new Date().getTime());
                            et_chat_input.setText("");

                            database.getReference().child("chats")
                                    .child(senderRoom)
                                    .push()
                                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            database.getReference().child("chats")
                                                    .child(receiverRoom)
                                                    .push()
                                                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
                }
                else
                    ib_chat_mic.setImageResource(R.drawable.mic_logo);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Chat_Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_emoji = findViewById(R.id.btn_emoji);
        btn_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        });

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUserDetail();
            }
        });
        tvChatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUserDetail();
            }
        });
    }
    public void gotoUserDetail() {
        Intent i = new Intent(Chat_Activity.this, User_Detail_Activity.class);
        i.putExtra("Chat_Id", receiverId);
        startActivity(i);
    }
}