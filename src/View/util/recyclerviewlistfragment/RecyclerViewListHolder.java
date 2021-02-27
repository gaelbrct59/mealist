package com.example.mealist.View.util.recyclerviewlistfragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.R;

class RecyclerViewListHolder extends RecyclerView.ViewHolder {
    TextView nameIngredient;
    TextView quantityIngredient;
    ImageView imageIngredient;
    TextView nbIngredient;

    public RecyclerViewListHolder(@NonNull View itemView) {
        super(itemView);
        nameIngredient = itemView.findViewById(R.id.textView_recyclerView_FragmentList);
        quantityIngredient = itemView.findViewById(R.id.listIngredient_quantity);
        imageIngredient = itemView.findViewById(R.id.listIngredient_image);
        nbIngredient = itemView.findViewById(R.id.nbUnite_ingredient);
    }
}
