package com.example.siddhant.zomato1.Response;

import com.example.siddhant.zomato1.City;

import java.util.ArrayList;

/**
 * Created by ABC on 06-07-2016.
 */
public class CityResponse {
    ArrayList<City>location_suggestions;

    public ArrayList<City> getLocation_suggestions() {
        return location_suggestions;
    }

    public void setLocation_suggestions(ArrayList<City> location_suggestions) {
        this.location_suggestions = location_suggestions;
    }
}
