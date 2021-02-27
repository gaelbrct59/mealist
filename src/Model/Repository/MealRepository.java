package com.example.mealist.Model.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealist.Model.Api.SingletonSpoonacularAPI;
import com.example.mealist.Model.Api.VolleyCallBack;
import com.example.mealist.Model.Meal;
import com.example.mealist.Model.RoomDatabase.MealDAO;
import com.example.mealist.Model.RoomDatabase.MealistRoomDatabase;

import java.util.List;

public class MealRepository {

    public void searchListOfAvailableMealFromQuery(String query, VolleyCallBack callBack){
        SingletonSpoonacularAPI.SPOONACULAR_API.searchListOfAvailableMealFromQuery(query, callBack);
    }

    private final MealDAO mealDAO;

    public MealRepository(Application application) {
        MealistRoomDatabase db = MealistRoomDatabase.getDatabase(application);
        SingletonSpoonacularAPI.SPOONACULAR_API.init(application.getApplicationContext());
        mealDAO = db.mealDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Meal>> getAllMeals() {
        return mealDAO.getAllMeals();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Meal meal) {
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> mealDAO.insert(meal));
    }

    public void delete(Meal meal){
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> mealDAO.delete(meal));
    }

    public void updateQuantity(String id, int quantity){
        MealistRoomDatabase.databaseWriteExecutor.execute(() -> mealDAO.updateCount(id, quantity));
    }

    public void deleteAllMeals(){
        MealistRoomDatabase.databaseWriteExecutor.execute(mealDAO::deleteAll);
    }

    public LiveData<List<Meal>> getFavoritesMeals(){
        return mealDAO.getFavoritesMeals();
    }

    public LiveData<Meal> isMealInDB(String id){
        return mealDAO.isMealInDB(id);
    }

}
