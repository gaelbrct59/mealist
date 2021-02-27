package com.example.mealist.Model.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealist.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface IngredientDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Delete
    void delete(Ingredient ingredient);

    @Query("UPDATE ingredient_table SET metric = :mquantity WHERE id = :ing_id")
    void updateQuantity(String ing_id, String mquantity);

    @Query("UPDATE ingredient_table SET nbBaseQuantity = :mNbBase WHERE id = :ing_id")
    void updateCount(String ing_id, int mNbBase);

    @Query("SELECT * FROM ingredient_table")
    LiveData<List<Ingredient>> getAllIngredients();
}
