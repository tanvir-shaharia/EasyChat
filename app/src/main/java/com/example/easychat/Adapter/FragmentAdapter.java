package com.example.easychat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.easychat.fragment.FeedFragment;
import com.example.easychat.fragment.NotificationFragment;
import com.example.easychat.fragment.UserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    String [] alltabs={"NewsFeed","User","Notifications"};

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FeedFragment();
            case 1:
                return new UserFragment();
            case 2:
                return new NotificationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return alltabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return alltabs[position];
    }
}
