package com.example.mealist.Model.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.mealist.Model.Meal;

import java.util.List;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAll();

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM meal_table ORDER BY count DESC")
    LiveData<List<Meal>> getAllMeals();

    @Query("UPDATE meal_table SET count = :mquantity WHERE id = :meal_id")
    void updateCount(String meal_id, int mquantity);

    @Query("SELECT * FROM meal_table ORDER BY count DESC LIMIT 10")
    LiveData<List<Meal>> getFavoritesMeals();

    @Query("SELECT * FROM meal_table WHERE id=:meal_id")
    LiveData<Meal> isMealInDB(String meal_id);
}
