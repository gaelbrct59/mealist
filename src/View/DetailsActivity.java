package com.example.mealist.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mealist.R;
import com.example.mealist.databinding.ActivityDetailsBinding;
import com.example.mealist.viewmodel.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the view model
        this.detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        initDataBinding();
        this.detailsViewModel.configureRecyclerView(findViewById(R.id.recyclerview_DetailActivity));
        this.detailsViewModel.setIdFromActivityAndSearchInApi(getIntent().getStringExtra("id"));
    }

    /**
     * Init data binding
     */
    private void initDataBinding(){
        ActivityDetailsBinding activityDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        activityDetailsBinding.setViewModel(this.detailsViewModel);
    }

}