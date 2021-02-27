package com.example.mealist.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealist.R;
import com.example.mealist.View.Fragments.SearchElementFragment;
import com.example.mealist.View.Fragments.ShoppingListFragment;
import com.example.mealist.View.util.OrdersPagerAdapter;
import com.example.mealist.viewmodel.MealistViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Activity which manage both of the Fragments {@link ShoppingListFragment} & {@link SearchElementFragment}
 */
public class MealistActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MealistViewModel mealistViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealist);
        initVariables();
        configureAndAttachTabLayoutMediator();

    }
    /**
     * Init variables and ViewPager2
     */
    private void initVariables() {
        this.mealistViewModel = new ViewModelProvider(this).get(MealistViewModel.class);
        this.tabLayout = findViewById(R.id.tabLayout);
        this.viewPager2 = findViewById(R.id.viewpager2);
        this.viewPager2.setAdapter(new OrdersPagerAdapter(this));
    }


    /**
     * Configure, show and attach tabLayoutMediator with fragments & orders pager adapter.
     */
    private void configureAndAttachTabLayoutMediator() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                this.tabLayout, this.viewPager2, (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setIcon(R.drawable.loupetablayout);
                            tab.setText("Search Meal");
                            break;
                        case 1:
                            tab.setIcon(R.drawable.list);
                            tab.setText("My list");
                            break;
                    }
                }
        );
        tabLayoutMediator.attach();
    }


    /**
     * Apply and attach fragments
     * @param fragment : Fragment
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof SearchElementFragment){
            SearchElementFragment searchElementFragment = (SearchElementFragment) fragment;
            searchElementFragment.setViewModel(this.mealistViewModel);
        }
        else if(fragment instanceof ShoppingListFragment){
            ShoppingListFragment shoppingListFragment = (ShoppingListFragment) fragment;
            shoppingListFragment.setViewModel(this.mealistViewModel);
        }
    }

}