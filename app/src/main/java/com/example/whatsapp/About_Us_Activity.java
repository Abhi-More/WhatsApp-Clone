package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

public class About_Us_Activity extends AppCompatActivity {

    ImageButton ib_back_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ib_back_activity = findViewById(R.id.ib_back_activity);
        ib_back_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(About_Us_Activity.this, Setting_Activity.class));
            }
        });
    }
}