package com.example.mealist.View.util.recyclerviewdetailsactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.Model.Ingredient;
import com.example.mealist.Model.Repository.IngredientRepository;
import com.example.mealist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewDetailsAdapter extends RecyclerView.Adapter<RecyclerViewDetailsHolder> {

    private final ArrayList<Ingredient> ingredients;
    private final OnClickAddToList callback;

    public RecyclerViewDetailsAdapter(ArrayList<Ingredient> ingredients, final OnClickAddToList callback) {
        this.ingredients = ingredients;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tmp = inflater.inflate(R.layout.recyclerview_details, parent, false);
        tmp.findViewById(R.id.button_addToShoppingList).setOnClickListener(callback::addToShoppingList);
        return new RecyclerViewDetailsHolder(tmp);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDetailsHolder holder, int position) {
        Ingredient ing = this.ingredients.get(position);
        holder.nameIngredient.setText(ing.getName());
        holder.quantityIngredient.setText(ing.getBaseQuantity() + ing.getMetric());
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + ing.getImage()).into(holder.imageIngredient);
    }

    @Override
    public int getItemCount() {
        return this.ingredients.size();
    }
}
