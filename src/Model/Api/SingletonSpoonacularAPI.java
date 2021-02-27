package com.example.mealist.Model.Api;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public final class SingletonSpoonacularAPI {
    private Context context;

    private SingletonSpoonacularAPI(){}

    public void init(Context context){
        this.context = context;
    }

    public static SingletonSpoonacularAPI SPOONACULAR_API = new SingletonSpoonacularAPI();

    /**
     * Create a request which find all recipes corresponding to the search
     */
    public void searchListOfAvailableMealFromQuery(String query, final VolleyCallBack callBack){
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://api.spoonacular.com/recipes/complexSearch?apiKey=592e8de922b54aa2b92777f115b58583&query="+query.toLowerCase()+"&number=30",
                null,
                callBack::onResponseSpoonacularAPI,
                error -> Log.e("ErrorRequestAPI", "searchAllIdFromAPIForSearchedMeal: " + error.toString())
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
    }

    /**
     * Create a request which find all ingredients corresponding to the recipe
     */
    public void searchIngredientListWithIdFromApi(String id, final VolleyCallBack callBack){
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://api.spoonacular.com/recipes/" + id + "/ingredientWidget.json?apiKey=592e8de922b54aa2b92777f115b58583",
                null,
                callBack::onResponseSpoonacularAPI,
                error -> Log.e("ErrorRequestAPI", "searchIngredientListWithIdFromApi: " + error.toString())
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
    }


}
