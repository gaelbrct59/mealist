package com.example.mealist.View.util.recyclerviewdetailsactivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.R;

public class RecyclerViewDetailsHolder extends RecyclerView.ViewHolder {
    TextView nameIngredient;
    TextView quantityIngredient;
    ImageView imageIngredient;

    public RecyclerViewDetailsHolder(@NonNull View itemView) {
        super(itemView);
        nameIngredient = itemView.findViewById(R.id.textView_recyclerView_detailsActivity);
        quantityIngredient = itemView.findViewById(R.id.detailsIngredient_quantity);
        imageIngredient = itemView.findViewById(R.id.detailsIngredient_image);

    }
}
