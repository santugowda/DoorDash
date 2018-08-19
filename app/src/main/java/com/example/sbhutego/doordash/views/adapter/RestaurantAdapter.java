package com.example.sbhutego.doordash.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sbhutego.doordash.R;
import com.example.sbhutego.doordash.model.Restaurant;

import java.util.List;

/**
 * Created by sbhutego on 8/18/18.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private Context mContext;
    private List<Restaurant> mRestaurants;
    private OnRestaurantClickEventListener mOnRestaurantClickListener;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        mContext = context;
        mRestaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.restaurant_list_item, parent, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position) {
        final Restaurant restaurant = mRestaurants.get(position);

        // Download and set image
        Glide.with(mContext)
                .load(restaurant.getCoverImgUrl())
                .into(holder.mRestaurantCoverImage);

        holder.mRestaurantName.setText(restaurant.getName());
        holder.mRestaurantDescription.setText(restaurant.getDescription());
        holder.mRestaurantDeliveryFee.setText(restaurant.getDisplayDeliveryFee());
        holder.mRestaurantStatus.setText(restaurant.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRestaurantClickListener != null) {
                    mOnRestaurantClickListener.onRestaurantClick(restaurant);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public void setOnRestaurantClickListener(OnRestaurantClickEventListener listener) {
        mOnRestaurantClickListener = listener;
    }

    public interface OnRestaurantClickEventListener {
        void onRestaurantClick(Restaurant restaurant);
    }
}
