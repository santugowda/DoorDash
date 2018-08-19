package com.example.sbhutego.doordash.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created by sbhutego on 8/19/18.
 */
public class RestaurantTest {

    @Test
    public void gsonDeserialization()
            throws UnsupportedEncodingException {
        InputStream in =
                this.getClass().getClassLoader().getResourceAsStream("restaurant5.txt");
        Reader reader = new InputStreamReader(in, "UTF-8");
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Restaurant restaurant = gson.fromJson(reader, Restaurant.class);

        assertNotNull(restaurant);
        assertEquals(5, restaurant.getId());
        assertEquals("Amarin Thai Cuisine (Mountain View)", restaurant.getName());
        assertEquals("Thai Cuisine", restaurant.getDescription());
        assertEquals("https://cdn.doordash.com/media/restaurant/cover/Amarin-Thai-Cuisine.png",
                restaurant.getCoverImgUrl());
        assertEquals("Pre-order for 12:45PM", restaurant.getStatus());
        assertDeliveryFeeFree(restaurant);
        assertRating(restaurant, 4.6, 3927);
    }

    @Test
    public void displayDeliveryFee() {
        Restaurant restaurant = new Restaurant();
        assertDeliveryFeeFree(restaurant);

        restaurant.setDeliveryFee(1);
        assertDeliveryFee(restaurant, 1, "$0.01");

        restaurant.setDeliveryFee(50);
        assertDeliveryFee(restaurant, 50, "$0.50");

        restaurant.setDeliveryFee(199);
        assertDeliveryFee(restaurant, 199, "$1.99");

        restaurant.setDeliveryFee(205);
        assertDeliveryFee(restaurant, 205, "$2.05");

        restaurant.setDeliveryFee(-100);
        assertDeliveryFee(restaurant, -100, "FREE");

        restaurant.setDeliveryFee(1500);
        assertDeliveryFee(restaurant, 1500, "$15.00");

        restaurant.setDeliveryFee(0);
        assertDeliveryFeeFree(restaurant);
    }

    @Test
    public void displayRating() {
        Restaurant restaurant = new Restaurant();
        assertRating(restaurant, 0, 0);

        restaurant.setNumberOfRatings(1);
        assertRating(restaurant, 0, 1);

        restaurant.setAverageRating(5);
        assertRating(restaurant, 5, 1);

        restaurant.setAverageRating(3.9);
        restaurant.setNumberOfRatings(50712);
        assertRating(restaurant, 3.9, 50712);

        restaurant.setAverageRating(2.000001);
        restaurant.setNumberOfRatings(-3);
        assertRating(restaurant, 2.000001, -3);

        restaurant.setAverageRating(-9.2);
        restaurant.setNumberOfRatings(0);
        assertRating(restaurant, -9.2, 0);

        restaurant.setAverageRating(0);
        assertRating(restaurant, 0, 0);
    }

    private static void assertDeliveryFeeFree(Restaurant restaurant) {
        assertDeliveryFee(restaurant, 0, "FREE");
    }

    private static void assertDeliveryFee(Restaurant restaurant, int expectedDeliveryFee, String expectedDisplayDeliveryFee) {
        assertEquals(expectedDeliveryFee, restaurant.getDeliveryFee());
        assertEquals(expectedDisplayDeliveryFee, restaurant.getDisplayDeliveryFee());
    }

    private static void assertRating(Restaurant restaurant,
                                     double expectedAverageRating,
                                     int expectedNumberOfRatings) {
        assertEquals(expectedAverageRating, restaurant.getAverageRating());
        assertEquals(expectedNumberOfRatings, restaurant.getNumberOfRatings());
        assertEquals(expectedAverageRating + " / 5.0 (" + expectedNumberOfRatings + " ratings)",
                restaurant.getDisplayRating());
    }

}