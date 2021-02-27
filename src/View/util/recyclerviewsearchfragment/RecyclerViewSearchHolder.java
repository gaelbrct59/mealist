package com.example.mealist.View.util.recyclerviewsearchfragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.R;

class RecyclerViewSearchHolder extends RecyclerView.ViewHolder {
    ImageView imgView_icon;
    TextView txtView_title;

    public RecyclerViewSearchHolder(@NonNull View itemView) {
        super(itemView);
        //Set the changes
        imgView_icon = itemView.findViewById(R.id.recycler_mealImage);
        txtView_title = itemView.findViewById(R.id.recycler_mealTitle);

    }

}