package com.example.mealist.Model.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealist.Model.Api.SingletonSpoonacularAPI;
import com.example.mealist.Model.Api.VolleyCallBack;
import com.example.mealist.Model.Ingredient;
import com.example.mealist.Model.RoomDatabase.IngredientDAO;
import com.example.mealist.Model.RoomDatabase.MealistRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepository {

    public void searchIngredientListWithIdFromApi(String query, VolleyCallBack callBack){
        SingletonSpoonacularAPI.SPOONACULAR_API.searchIngredientListWithIdFromApi(query, callBack);
    }

    private final IngredientDAO ingredientDAO;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public IngredientRepository(Application application) {
        MealistRoomDatabase db = MealistRoomDatabase.getDatabase(application);
        ingredientDAO = db.ingredientDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Ingredient>> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Ingredient ing) {
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.insert(ing));
    }

    public void delete(Ingredient ing){
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.delete(ing));
    }

    public void updateQuantity(String id, String quantity){
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.updateQuantity(id, quantity));
    }

    public void updateCountNbBaseQuantity(String id, int count){
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.updateCount(id, count));
    }

    public void deleteAllIngredients(){
        MealistRoomDatabase.databaseWriteExecutor.execute(ingredientDAO::deleteAll);
    }

}
