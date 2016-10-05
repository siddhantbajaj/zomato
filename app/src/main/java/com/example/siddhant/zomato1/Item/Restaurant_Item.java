package com.example.siddhant.zomato1.Item;

/**
 * Created by ABC on 08-07-2016.
 */
public class Restaurant_Item {
    public class cate
    {
        String url;
        String featured_image;
        String name;

        public String getUrl() {
            return url;
        }

        public String getFeatured_image() {
            return featured_image;
        }

        public String getName() {
            return name;
        }
    }
    cate restaurant;

    public cate getRestaurant() {
        return restaurant;
    }
}
