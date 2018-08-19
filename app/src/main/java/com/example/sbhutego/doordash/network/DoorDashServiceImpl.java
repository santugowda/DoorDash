package com.example.sbhutego.doordash.network;

import android.util.Log;

import com.example.sbhutego.doordash.model.Restaurant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sbhutego.doordash.network.DoorDashAPI.BASE_URL;

/**
 * Created by sbhutego on 8/18/18.
 */

public class DoorDashServiceImpl implements DoorDashAPI {

    private static String TAG = DoorDashServiceImpl.class.getSimpleName();
    private static DoorDashServiceImpl sInstance;
    private DoorDashAPI mDoorDashAPI;
    private static final double DEFAULT_LAT = 37.422740;
    private static final double DEFAULT_LNG = -122.139956;

    private DoorDashServiceImpl() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mDoorDashAPI = retrofit.create(DoorDashAPI.class);
    }

    public static DoorDashServiceImpl getInstance() {
        if (sInstance == null) {
            sInstance = new DoorDashServiceImpl();
        }
        return sInstance;
    }

    public void setDoorDashAPI(DoorDashAPI doorDashAPI) {
        mDoorDashAPI = doorDashAPI;
    }

    @Override
    public Call<List<Restaurant>> fetchRestaurants(double lat, double lng) {
        return mDoorDashAPI.fetchRestaurants(lat, lng);
    }

    @Override
    public Call<Restaurant> fetchRestaurant(int restaurantId) {
        return mDoorDashAPI.fetchRestaurant(restaurantId);
    }

    public Call<List<Restaurant>> fetchRestaurants() {
        return fetchRestaurants(DEFAULT_LAT, DEFAULT_LNG);
    }
}
