package com.example.mealist.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mealist.R;
import com.example.mealist.viewmodel.MealistViewModel;

public class ShoppingListFragment extends Fragment {

    private MealistViewModel mealistViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the observer which updates the UI.
        this.mealistViewModel.getAllDbIngredient().observe(this, ingredients -> {
            this.mealistViewModel.recyclerViewListAdapter.submitList(ingredients);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initDataBinding(inflater, container);
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_list, container, false);
        mealistViewModel.configureRecyclerViewIngredient(view.findViewById(R.id.recyclerview_FragmentList));

        return view;
    }

    /**
     * Init data binding to transfer data from {@link MealistViewModel}
     * @param inflater : LayoutInflater
     * @param container : ViewGroup
     * @return view : View
     */
    private View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        com.example.mealist.databinding.FragmentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        View view = binding.getRoot();
        binding.setViewModel(mealistViewModel);
        binding.setLifecycleOwner(this);
        return view;
    }

    /**
     * Set view model
     * @param mvm : {@link MealistViewModel}
     */
    public void setViewModel(MealistViewModel mvm){
        mealistViewModel = mvm;
    }

}