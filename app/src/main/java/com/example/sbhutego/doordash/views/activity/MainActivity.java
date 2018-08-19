package com.example.sbhutego.doordash.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sbhutego.doordash.R;
import com.example.sbhutego.doordash.model.Restaurant;
import com.example.sbhutego.doordash.network.DoorDashServiceImpl;
import com.example.sbhutego.doordash.views.adapter.RestaurantAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    public static final String RESTAURANT_ID_KEY = "restaurantId";
    private RecyclerView mRestaurantListView;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessage = findViewById(R.id.error_msg);
        mRestaurantListView = findViewById(R.id.restaurant_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRestaurantListView.setLayoutManager(layoutManager);

        fetchRestaurants();
    }

    private void fetchRestaurants() {
        DoorDashServiceImpl.getInstance()
                .fetchRestaurants()
                .enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        RestaurantAdapter adapter =
                                new RestaurantAdapter(MainActivity.this, response.body());
                        adapter.setOnRestaurantClickListener
                                (new RestaurantAdapter.OnRestaurantClickEventListener() {
                                    @Override
                                    public void onRestaurantClick(Restaurant restaurant) {
                                        startRestaurantDetailsActivity(restaurant);
                                    }
                                });
                        mRestaurantListView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                        setErrorMsg(getString(R.string.error_message) + t.getLocalizedMessage());
                    }
                });
    }

    private void setErrorMsg(String errorMsg) {
        mErrorMessage.setText(errorMsg);
        mErrorMessage.setVisibility(errorMsg == null ? View.GONE : View.VISIBLE);
    }

    private void startRestaurantDetailsActivity(Restaurant restaurant) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra(RESTAURANT_ID_KEY, restaurant.getId());
        startActivity(intent);
    }
}