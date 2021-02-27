package com.example.mealist.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;
    private String image;
    private String metric;
    private float baseQuantity;
    private int nbBaseQuantity;



    public Ingredient(String id, String name, String image, String metric, float baseQuantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.metric = metric;
        this.baseQuantity = baseQuantity;
        this.nbBaseQuantity = 1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getMetric() {
        return metric;
    }
    public float getBaseQuantity() {
        return baseQuantity;
    }

    public int getNbBaseQuantity() {
        return nbBaseQuantity;
    }

    public void setNbBaseQuantity(int nbBaseQuantity) {
        this.nbBaseQuantity = nbBaseQuantity;
    }
    @Ignore
    public Ingredient(){

    }
}
