package com.example.mealist.Model.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mealist.Model.Ingredient;
import com.example.mealist.Model.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meal.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class MealistRoomDatabase extends RoomDatabase {

    public abstract IngredientDAO ingredientDAO();
    public abstract MealDAO mealDAO();

    private static volatile MealistRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static MealistRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            synchronized (MealistRoomDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MealistRoomDatabase.class, "mealist_database")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                MealDAO mealdao = INSTANCE.mealDAO();
                IngredientDAO ingredientDAO = INSTANCE.ingredientDAO();
                mealdao.deleteAll();
                ingredientDAO.deleteAll();
            });
        }
    };

}
