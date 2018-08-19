package com.example.sbhutego.doordash.network;

import com.example.sbhutego.doordash.model.Restaurant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by sbhutego on 8/19/18.
 */
public class DoorDashServiceImplTest {

    private MockWebServer mServer;
    private DoorDashServiceImpl mDoorDashService;

    @Before
    public void setUp() {
        mServer = new MockWebServer();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mDoorDashService = DoorDashServiceImpl.getInstance();
        mDoorDashService.setDoorDashAPI(retrofit.create(DoorDashAPI.class));
    }

    @After
    public void tearDown()
            throws IOException {
        mServer.shutdown();
    }

    @Test
    public void fetchRestaurant()
            throws IOException, InterruptedException {
        mServer.enqueue(new MockResponse().setBody(stringFromFile("restaurant5.txt")));

        Restaurant restaurant = mDoorDashService.fetchRestaurant(5).execute().body();
        assertNotNull(restaurant);
        assertEquals(5, restaurant.getId());

        RecordedRequest request = mServer.takeRequest();
        assertEquals("/v2/restaurant/5/", request.getPath());
    }

    @Test
    public void fetchRestaurantsWithNoParams()
            throws IOException, InterruptedException {
        mServer.enqueue
                (new MockResponse().setBody(stringFromFile("restaurants12.txt")));

        List<Restaurant> restaurants = mDoorDashService.fetchRestaurants().execute().body();
        assertNotNull(restaurants);
        assertEquals(12, restaurants.size());

        RecordedRequest request = mServer.takeRequest();
        assertEquals("/v2/restaurant/?lat=37.42274&lng=-122.139956", request.getPath());
    }

    @Test
    public void fetchRestaurants()
            throws IOException, InterruptedException {
        mServer.enqueue(new MockResponse().setBody(stringFromFile("restaurants10.txt")));

        List<Restaurant> restaurants =
                mDoorDashService.fetchRestaurants(37.544233, -122.2456898).execute().body();
        assertNotNull(restaurants);
        assertEquals(10, restaurants.size());

        RecordedRequest request = mServer.takeRequest();
        assertEquals("/v2/restaurant/?lat=37.544233&lng=-122.2456898", request.getPath());
    }


    private String stringFromFile(String fileName)
            throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        StringBuffer buffer = new StringBuffer();
        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } finally {
            if (isr != null) {
                isr.close();
            }

            if (is != null) {
                is.close();
            }
        }
        return buffer.toString();
    }


}