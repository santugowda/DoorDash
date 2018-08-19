package com.example.sbhutego.doordash.model;

import android.content.Context;

/**
 * Created by sbhutego on 8/18/18.
 */

public class Restaurant {

    private static final String CURRENCY_SYMBOL = "$"; //cents
    private static final String PRICE_FREE = "FREE";
    private static final double RATING = 5.0;

    private int id;
    private String name;
    private String description;
    private String coverImgUrl;
    private String status;
    private int deliveryFee;
    private transient String displayDeliveryFee;
    private double averageRating;
    private int numberOfRatings;
    private transient String displayRating;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public String getStatus() {
        return status;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public String getDisplayDeliveryFee() {
        if (displayDeliveryFee == null) {
            updateDisplayDeliveryFee();
        }

        return displayDeliveryFee;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public String getDisplayRating() {
        if (displayRating == null) {
            updateDisplayRating();
        }

        return displayRating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
        updateDisplayDeliveryFee();
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
        updateDisplayRating();
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
        updateDisplayRating();
    }

    private void updateDisplayDeliveryFee() {
        String display;
        if (deliveryFee <= 0) {
            display = PRICE_FREE;
        } else {
            int dollars = deliveryFee / 100;
            int cents = deliveryFee % 100;
            display = CURRENCY_SYMBOL + dollars + ".";

            if (cents < 10) {
                display += "0";
            }
            display += cents;
        }
        displayDeliveryFee = display;
    }

    private void updateDisplayRating() {
        displayRating = averageRating + " / " + RATING + " (" + numberOfRatings + " ratings)";
    }
}
