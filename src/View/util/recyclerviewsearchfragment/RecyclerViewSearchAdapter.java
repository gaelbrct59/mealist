package com.example.mealist.View.util.recyclerviewsearchfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.Model.Meal;
import com.example.mealist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchHolder> {
    private final List<Meal> mealArrayList;
    private final CallBackRecyclerViewDetailsActivity callBack;
    boolean isLinearView;

    public RecyclerViewSearchAdapter(List<Meal> mealArrayList, final CallBackRecyclerViewDetailsActivity callBack) {
        this.mealArrayList = mealArrayList;
        this.callBack = callBack;
        isLinearView = false;
    }

    @NonNull
    @Override
    public RecyclerViewSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tmp;
        if(isLinearView){
            tmp = inflater.inflate(R.layout.disp_recycler_view, parent, false);
        }
        else{
            tmp = inflater.inflate(R.layout.grid_displayer_recyclerview_searchfragment, parent, false);
        }


        tmp.setOnClickListener(v -> this.callBack.onClickMealListener(v));

        return new RecyclerViewSearchHolder(tmp);
    }

    /**
     * Change layout for switch type of view
     */
    public void changeView(){
        this.isLinearView = !isLinearView;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSearchHolder holder, int position) {
        Meal meal = this.mealArrayList.get(position);
        Picasso.get().load(meal.getImg()).into(holder.imgView_icon);
        holder.txtView_title.setText(meal.getTitle());
        //holder.txtView_id.setText(meal.getId());
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }

}

