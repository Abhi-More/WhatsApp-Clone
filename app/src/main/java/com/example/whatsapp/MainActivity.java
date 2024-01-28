package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayoutMain;
    ViewPager viewPagerMain;
    Intent intent;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        tabLayoutMain = findViewById(R.id.tabLayoutMain);
        viewPagerMain = findViewById(R.id.viewPagerMain);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerMain.setAdapter(adapter);
        tabLayoutMain.setupWithViewPager(viewPagerMain);

        auth = FirebaseAuth.getInstance();
    }

//  To Add bottons on appBar
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        switch (item.getItemId()) {
            case R.id.action_camera:
                intent = new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(intent);
                break;

            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, Setting_Activity.class));
//                break;
//            case R.id.action_log_out:
//                auth.signOut();
//                startActivity(new Intent(MainActivity.this, SignInActivity.class));
//                finishAffinity();
//                break;
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}