package com.example.sbhutego.doordash.network;

import com.example.sbhutego.doordash.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sbhutego on 8/18/18.
 */

public interface DoorDashAPI {

    String BASE_URL = "https://api.doordash.com/";
    String RESTAURANT_ENDPOINT = "v2/restaurant/";

    @GET(RESTAURANT_ENDPOINT)
    Call<List<Restaurant>> fetchRestaurants(@Query("lat") double lat, @Query("lng") double lng);


    @GET(RESTAURANT_ENDPOINT + "{id}/")
    Call<Restaurant> fetchRestaurant(@Path("id") int restaurantId);
}
