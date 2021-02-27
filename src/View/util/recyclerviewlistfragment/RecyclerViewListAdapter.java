package com.example.mealist.View.util.recyclerviewlistfragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mealist.Model.Ingredient;
import com.example.mealist.R;
import com.squareup.picasso.Picasso;

public class RecyclerViewListAdapter extends ListAdapter<Ingredient, RecyclerViewListHolder> {

    public OnClickManageIngredientsInDB callBack;
    public RecyclerViewListAdapter(OnClickManageIngredientsInDB callback) {
        super(DIFF_CALLBACK);
        this.callBack = callback;
    }

    // méthodes qui permettent d'établir les différences entre les données avant et après modification/insertion/suppression...
    public static final DiffUtil.ItemCallback<Ingredient> DIFF_CALLBACK = new DiffUtil.ItemCallback<Ingredient>() {
        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getMetric().equals(newItem.getMetric())
                    && oldItem.getImage().equals(newItem.getImage());
        }
    };

    @NonNull
    @Override
    public RecyclerViewListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tmp = inflater.inflate(R.layout.recyclerview_list, parent, false);
        tmp.findViewById(R.id.button_addQuantity).setOnClickListener(v -> callBack.ManageIngredient(v));
        tmp.findViewById(R.id.button_lessQuantity).setOnClickListener(v -> callBack.ManageIngredient(v));
        return new RecyclerViewListHolder(tmp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListHolder holder, int position) {

        Ingredient currentIngredient = getItem(position);
        holder.nameIngredient.setText(currentIngredient.getName());
        holder.quantityIngredient.setText((currentIngredient.getBaseQuantity() *
                currentIngredient.getNbBaseQuantity()) + " " + currentIngredient.getMetric());
        holder.nbIngredient.setText(currentIngredient.getNbBaseQuantity() +"");
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + currentIngredient.getImage()).into(holder.imageIngredient);
    }


}
