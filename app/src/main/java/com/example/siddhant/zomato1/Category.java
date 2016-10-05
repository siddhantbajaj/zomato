package com.example.siddhant.zomato1;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ABC on 06-07-2016.
 */
public class Category {
    public class cate{
        int id;
        String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    @SerializedName("categories")
    cate category;

    public Category(cate category) {
        this.category = category;
    }

    public cate getCategory() {
        return category;
    }
}
