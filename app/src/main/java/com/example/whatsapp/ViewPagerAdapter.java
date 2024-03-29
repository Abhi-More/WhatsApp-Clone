package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    public Fragment getItem(int position) {

        if (position == 0) {
            return new ChatFragment();
        } else if (position == 1) {
            return new StatusFragment();
        } else {
            return new CallsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;  // returning number of fragments (tabs)
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Chats";
        } else if (position == 1) {
            return "Status";
        } else {
            return "Calls";
        }
    }
}
