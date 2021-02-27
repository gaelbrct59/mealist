package com.example.mealist.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealist.Model.Ingredient;
import com.example.mealist.Model.Meal;
import com.example.mealist.Model.Repository.IngredientRepository;
import com.example.mealist.Model.Repository.MealRepository;
import com.example.mealist.R;
import com.example.mealist.View.DetailsActivity;
import com.example.mealist.View.util.recyclerviewlistfragment.OnClickManageIngredientsInDB;
import com.example.mealist.View.util.recyclerviewlistfragment.RecyclerViewListAdapter;
import com.example.mealist.View.util.recyclerviewsearchfragment.CallBackRecyclerViewDetailsActivity;
import com.example.mealist.View.util.recyclerviewsearchfragment.RecyclerViewSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealistViewModel extends AndroidViewModel {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    //For SearchElementFragment
    private List<Meal> availableMeals;
    public MutableLiveData<String> resFromEditText;
    private RecyclerViewSearchAdapter recyclerViewSearchAdapter;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewSearch;
    private boolean isLinearState;
    private final MealRepository mealRepository;
    public LiveData<List<Meal>> favoritesMeals;
    //public Meal selectedMeal;
    //private LiveData<Meal> findMeal;

    //For ShoppingListElement
    public RecyclerViewListAdapter recyclerViewListAdapter;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewList;
    private final IngredientRepository ingredientRepository;
    private LiveData<List<Ingredient>> allDbIngredient;
    public LiveData<String> nbOfIngredient;

    /**
     * Constructor with AndroidViewModel
     * @param application : Application
     */
    public MealistViewModel(@NonNull Application application) {
        super(application);
        initVariables(application.getApplicationContext());

        isLinearState = false;
        mealRepository = new MealRepository(application);
        ingredientRepository = new IngredientRepository(application);
        allDbIngredient = ingredientRepository.getAllIngredients();
        favoritesMeals = mealRepository.getFavoritesMeals();
        nbOfIngredient = new MutableLiveData<>();
    }

    /**
     * Init variables
     * availableMeals => stock list of meals
     * resFromEditText to get the result from editText
     * @param context : View Context
     */
    private void initVariables(Context context) {
        this.availableMeals = new ArrayList<>();
        this.resFromEditText = new MutableLiveData<>(); //Get the result from edit text
        this.allDbIngredient = new MutableLiveData<>();
        this.favoritesMeals = new MutableLiveData<>();
        this.context = context;
    }

    /**
     * Set the request's callBack to get the list of meals found with the research
     * made by the user.
     */
    private void searchMealList() {
        mealRepository.searchListOfAvailableMealFromQuery(resFromEditText.getValue(), response -> {
            ArrayList<Meal> tabOfMeal = new ArrayList<>();
            try {
                JSONArray jsonArray = response.getJSONArray("results");
                //Search element by element on the JsonObject
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Meal meal = new Meal(jsonObject.getString("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("image"));
                    tabOfMeal.add(meal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateUISearch(tabOfMeal);
        });
    }

    /**
     * Verify text given or not in the search bar to search in API.
     */
    public void verifySearchBar(){
        if(this.resFromEditText.getValue() != null){
            searchMealList();
        }else{
            Toast.makeText(this.context, "Please put text on the field", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Configure recycler view, set Adapter & Layout manager for SearchElementFragment
     * @param rv : RecyclerView
     */
    public void configureRecyclerViewMeal(RecyclerView rv){

        //Get the view from SearchElementFragment
        this.recyclerViewSearch = rv;
        this.recyclerViewSearchAdapter = new RecyclerViewSearchAdapter(this.availableMeals, setUpCallBack());

        changeStateLayout();
    }

    /**
     * Configure recycler view, set Adapter & Layout manager for ShoppingListFragment
     * @param rv : RecyclerView
     */
    public void configureRecyclerViewIngredient(RecyclerView rv){

        //Get the view from ShoppingListFragment
        this.recyclerViewList = rv;

        //ingredientsShoppingList = ingredientRepository.getAllIngredients().getValue();
        this.recyclerViewListAdapter = new RecyclerViewListAdapter(setUpCallBackForShoppingList());
        this.recyclerViewList.setAdapter(this.recyclerViewListAdapter);
        this.recyclerViewList.setLayoutManager(new LinearLayoutManager(this.context));
    }

    /**
     * Callback of the click on "+" or "-" from the RecyclerViewAdapter.
     * If user click on the "+", the base quantity is added to the quantity
     * If user click on the "-", the base quantity is subtracted. If quantity = 0, the ingredient is deleted from database.
     * @return OnClickManageIngredientsInDB : Callback
     */
    private OnClickManageIngredientsInDB setUpCallBackForShoppingList(){
        return v -> {
            int itemPos = recyclerViewList.getChildLayoutPosition((View) v.getParent().getParent());
            Ingredient ingredientClick = Objects.requireNonNull(allDbIngredient.getValue()).get(itemPos);
            //If user click on the + button
            Log.i("TAG", "setUpCallBackForShoppingList: " + ingredientClick.getNbBaseQuantity());
            if(v.getId() == R.id.button_addQuantity){
                // Add quantity, and update DB to display the String
                this.updateCountNbBaseIngredient(ingredientClick.getId(),ingredientClick.getNbBaseQuantity()+1);
            }else{ //if user click on the - button
                //if there is one last "copy" of the ingredient, must delete it
                if (ingredientClick.getNbBaseQuantity() - 1 == 0){
                    this.deleteIngredient(ingredientClick);
                }else{
                    this.updateCountNbBaseIngredient(ingredientClick.getId(), ingredientClick.getNbBaseQuantity()-1);
                }
            }

        };
    }

    /**
     * Change LayoutManager to switch between Grid and Linear layout.
     */
    public void changeStateLayout(){
        this.recyclerViewSearchAdapter.changeView();
        isLinearState = !isLinearState;

        if (isLinearState) {
            this.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this.context));
        } else {
            this.recyclerViewSearch.setLayoutManager(new GridLayoutManager(this.context, 3));
        }
        this.recyclerViewSearch.setAdapter(this.recyclerViewSearchAdapter);
    }

    /**
     * start the DetailsActivity when user click on a meal
     * @return CallBackRecyclerViewDetailsActivity
     */
    private CallBackRecyclerViewDetailsActivity setUpCallBack(){
        return v -> {

            int itemPos = this.recyclerViewSearch.getChildLayoutPosition(v);
            //this.selectedMeal = this.availableMeals.get(itemPos);
            Meal item = this.availableMeals.get(itemPos);
       /*     Meal selectedMeal = findMeal(item.getId()).getValue();
            Log.i("passe", ""+selectedMeal.getTitle());
            if(selectedMeal != null)
                this.updateCountOfClickedMeals(selectedMeal.getId(), selectedMeal.getCount() + 1);
            else {
                this.insertMeal(item);
                this.updateCountOfClickedMeals(item.getId(), item.getCount() + 1);
            }*/

            insertMeal(item);
            updateCountOfClickedMeals(item.getId(), item.getCount()+1);

            Intent i = new Intent(this.context, DetailsActivity.class);
            //i.putExtra("id", this.selectedMeal.getId());
            i.putExtra("id", item.getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            this.context.startActivity(i);

        };
    }

    /**
     * Update the UI of SearchElementFragment, to see the result of the research
     * @param allMeals : ArrayList<Meal>
     */
    public void updateUISearch(List<Meal> allMeals){
        if (this.availableMeals != null){
            this.availableMeals.clear();
        }
        assert this.availableMeals != null;
        this.availableMeals.addAll(allMeals);
        this.recyclerViewSearchAdapter.notifyDataSetChanged();
    }

    /**
     * Get all ingredients from database
     * @return allDbIngredient : LiveData
     */
    public LiveData<List<Ingredient>> getAllDbIngredient(){
        return allDbIngredient;
    }


    public void deleteAllIngredients() {ingredientRepository.deleteAllIngredients();}
    public void deleteIngredient(Ingredient ingredient) {ingredientRepository.delete(ingredient);}
    public void updateCountNbBaseIngredient(String id, int count) {ingredientRepository.updateCountNbBaseQuantity(id, count);}

    public void insertMeal(Meal meal){mealRepository.insert(meal);}
    public void updateCountOfClickedMeals(String id, int quantity){mealRepository.updateQuantity(id,quantity);}
}
