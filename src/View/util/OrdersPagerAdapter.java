package com.example.mealist.View.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mealist.View.Fragments.ShoppingListFragment;
import com.example.mealist.View.Fragments.SearchElementFragment;

public class OrdersPagerAdapter extends FragmentStateAdapter {

    public OrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SearchElementFragment();
        }
        return new ShoppingListFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
