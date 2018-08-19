package com.example.sbhutego.doordash.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sbhutego.doordash.R;

/**
 * Created by sbhutego on 8/18/18.
 */

class RestaurantViewHolder extends RecyclerView.ViewHolder {
    ImageView mRestaurantCoverImage;
    TextView mRestaurantName;
    TextView mRestaurantDescription;
    TextView mRestaurantDeliveryFee;
    TextView mRestaurantStatus;

    RestaurantViewHolder(View view) {
        super(view);

        mRestaurantCoverImage = view.findViewById(R.id.restaurant_list_image);
        mRestaurantName = view.findViewById(R.id.restaurant_list_name);
        mRestaurantDescription = view.findViewById(R.id.restaurant_list_description);
        mRestaurantDeliveryFee = view.findViewById(R.id.restaurant_list_delivery_fee);
        mRestaurantStatus = view.findViewById(R.id.restaurant_list_status);
    }
}
