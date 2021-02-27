package com.example.mealist.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_table")
public class Meal {

    @PrimaryKey
    @NonNull
    private String id;

    private String title;
    private String img;
    private int count;


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImg() {
        return this.img;
    }

    public Meal(String id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.count = 0;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Ignore
    public Meal(){

    }
}
