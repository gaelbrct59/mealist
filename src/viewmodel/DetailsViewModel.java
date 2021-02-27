package com.example.mealist.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.Model.Ingredient;
import com.example.mealist.Model.Repository.IngredientRepository;
import com.example.mealist.View.DetailsActivity;
import com.example.mealist.View.util.recyclerviewdetailsactivity.OnClickAddToList;
import com.example.mealist.View.util.recyclerviewdetailsactivity.RecyclerViewDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsViewModel extends AndroidViewModel {

    private final ArrayList<Ingredient> ingredients;
    private final Context context;
    private RecyclerView recyclerView;
    private RecyclerViewDetailsAdapter recyclerViewAdapter;
    private final IngredientRepository ingredientRepository;


    //public LiveData<List<Ingredient>> getAllIngredientsFromDB;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        this.ingredients = new ArrayList<>();
        this.context = application.getApplicationContext();

        ingredientRepository = new IngredientRepository(application);
       // getAllIngredientsFromDB = ingredientRepository.getAllIngredients();
    }

    /**
     * Check if user is connected to network
     * @return boolean
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    /**
     * Set the request's callBack to get the list of ingredient found with the click
     * made by the user on the chosen meal.
     * @param id : String => id of the clicked meal
     */
    public void setIdFromActivityAndSearchInApi(String id){
        ingredientRepository.searchIngredientListWithIdFromApi(id, response -> {
            ArrayList<Ingredient> tabOfIngredient = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray("ingredients");

            for (int i = 0; i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject amount = jsonObject.getJSONObject("amount");
                JSONObject metric = amount.getJSONObject("metric");
                tabOfIngredient.add(new Ingredient(
                        id + i,
                        jsonObject.get("name").toString(),
                        jsonObject.get("image").toString(),
                        metric.get("unit").toString(),
                        Float.parseFloat(metric.get("value").toString())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateUI(tabOfIngredient);
        });
    }

    /**
     * Configure recycler view, set Adapter & Layout manager
     * @param rv : RecyclerView
     */
    public void configureRecyclerView(RecyclerView rv){
        this.recyclerView = rv;
        this.recyclerViewAdapter = new RecyclerViewDetailsAdapter(this.ingredients, setUpCallBackToDatabase());

        this.recyclerView.setAdapter(this.recyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
    }

    /**
     * Get the ingredient and put it on the Database
     * @return View : OnClickAddToList
     */
    private OnClickAddToList setUpCallBackToDatabase(){
        return v -> {
            int itemPos = recyclerView.getChildLayoutPosition((View) v.getParent().getParent());
            Ingredient ingredientClick = ingredients.get(itemPos);
            this.insert(ingredientClick);//todo be sure quantity is well stock
            Toast.makeText(context,  ingredientClick.getName() + " added to the Shopping List ", Toast.LENGTH_SHORT).show();
        };
    }

    /**
     * Update the UI, to see the result of the research
     * @param allIngredients : ArrayList<Ingredient>
     */
    private void updateUI(ArrayList<Ingredient> allIngredients){
        if (this.ingredients != null){
            this.ingredients.clear();
        }
        assert this.ingredients != null;
        this.ingredients.addAll(allIngredients);
        this.recyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * Insert ingredient in database, with repository
     * @param ingredient : Ingredient
     */
    public void insert(Ingredient ingredient) { ingredientRepository.insert(ingredient); }
   // public void delete(Ingredient ingredient) {ingredientRepository.delete(ingredient);}

}
