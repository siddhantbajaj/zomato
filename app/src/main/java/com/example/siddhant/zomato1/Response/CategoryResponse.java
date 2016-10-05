package com.example.siddhant.zomato1.Response;

import com.example.siddhant.zomato1.Item.CategoryItem;

import java.util.ArrayList;

/**
 * Created by ABC on 07-07-2016.
 */
public class CategoryResponse {
    ArrayList<CategoryItem>collections;

    public ArrayList<CategoryItem> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<CategoryItem> collections) {
        this.collections = collections;
    }
}
