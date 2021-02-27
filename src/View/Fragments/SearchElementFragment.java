package com.example.mealist.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mealist.R;
import com.example.mealist.viewmodel.MealistViewModel;

/**
 * Fragment which get the search of the user
 */
public class SearchElementFragment extends Fragment {

    private MealistViewModel mealistViewModel;
    private boolean hasAlreadyBeenInit = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initDataBinding(inflater, container);
        mealistViewModel.configureRecyclerViewMeal(view.findViewById(R.id.recyclerView_searchFragment));
        mealistViewModel.favoritesMeals.observe(getViewLifecycleOwner(), meals -> {
            if(!hasAlreadyBeenInit){
                mealistViewModel.updateUISearch(meals);
                hasAlreadyBeenInit = true;
            }}
        );
       /* mealistViewModel.getFindMeal().observe(getViewLifecycleOwner(), meal -> {
            Log.i("passe", "observer");
            if(meal != null)
                mealistViewModel.updateCountOfClickedMeals(meal.getId(), meal.getCount() + 1);
            else{
                mealistViewModel.insertMeal(mealistViewModel.selectedMeal);
                mealistViewModel.updateCountOfClickedMeals(mealistViewModel.selectedMeal.getId(), mealistViewModel.selectedMeal.getCount() + 1);
            }
        });*/

        return view;
    }


    /**
     * Init data binding to transfer data from {@link MealistViewModel}
     * @param inflater : LayoutInflater
     * @param container : ViewGroup
     * @return view : View
     */
    private View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        com.example.mealist.databinding.FragmentSearchElementBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_element, container, false);
        View view = binding.getRoot();
        binding.setViewModel(mealistViewModel);
        binding.setLifecycleOwner(this);
        return view;
    }

    /**
     * Set view model for dataBinding
     * @param mvm : {@link MealistViewModel}
     */
    public void setViewModel(MealistViewModel mvm){
        mealistViewModel = mvm;
    }

}