package com.example.sbhutego.doordash.views.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sbhutego.doordash.R;
import com.example.sbhutego.doordash.model.Restaurant;
import com.example.sbhutego.doordash.network.DoorDashServiceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sbhutego.doordash.views.activity.MainActivity.RESTAURANT_ID_KEY;


/**
 * Created by sbhutego on 8/18/18.
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private static String TAG = RestaurantDetailActivity.class.getSimpleName();
    private TextView mErrorMsgView;
    private TextView mRestaurantNameView;
    private TextView mRestaurantDescriptionView;
    private TextView mRestaurantRatingView;
    private TextView mRestaurantDeliveryFeeView;
    private TextView mRestaurantStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);

        mErrorMsgView = findViewById(R.id.restaurant_details_error_msg);
        mRestaurantNameView = findViewById(R.id.restaurant_details_name);
        mRestaurantDescriptionView = findViewById(R.id.restaurant_details_description);
        mRestaurantRatingView = findViewById(R.id.restaurant_details_rating);
        mRestaurantDeliveryFeeView = findViewById(R.id.restaurant_details_delivery_fee);
        mRestaurantStatusView = findViewById(R.id.restaurant_details_status);

        int restaurantId = getIntent().getIntExtra(RESTAURANT_ID_KEY, 0);
        fetchRestaurant(restaurantId);
    }

    private void fetchRestaurant(int restaurantId) {
        DoorDashServiceImpl.getInstance()
                .fetchRestaurant(restaurantId)
                .enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(@NonNull Call<Restaurant> call, Response<Restaurant> response) {
                        displayRestaurantDetails(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<Restaurant> call, Throwable t) {
                        setErrorMsg("Unable to retrieve restaurant details: " + t.getLocalizedMessage());
                    }
                });
    }

    private void displayRestaurantDetails(Restaurant restaurant) {
        mRestaurantNameView.setText(restaurant.getName());
        mRestaurantDescriptionView.setText(restaurant.getDescription());
        mRestaurantRatingView.setText(restaurant.getDisplayRating());
        mRestaurantDeliveryFeeView.setText(restaurant.getDisplayDeliveryFee());
        mRestaurantStatusView.setText(restaurant.getStatus());
    }

    private void setErrorMsg(String errorMsg) {
        mErrorMsgView.setText(errorMsg);
        mErrorMsgView.setVisibility(errorMsg == null ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
