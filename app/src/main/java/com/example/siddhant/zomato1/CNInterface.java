package com.example.siddhant.zomato1;

import com.example.siddhant.zomato1.Response.CategoryResponse;
import com.example.siddhant.zomato1.Response.CityResponse;
import com.example.siddhant.zomato1.Response.CusineResponse;
import com.example.siddhant.zomato1.Response.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by ABC on 04-07-2016.
 */
 public interface CNInterface {
    @GET("/api/v2.1/cities")

    Call<CityResponse>getCity(@Header("Accept")String header1, @Header("user-key")String header2, @Query("q")String city);
//    @GET("/api/v1/android/students")
//    Call<StudentResponse>getStudents(@Query(("batch_id")) int batchId);
@GET("/api/v2.1/collections")

Call<CategoryResponse>getCategories(@Header("Accept")String header1, @Header("user-key")String header2, @Query("city_id")int city_id);

    @GET("/api/v2.1/cuisines")

    Call<CusineResponse>getCusines(@Header("Accept")String header1, @Header("user-key")String header2, @Query("city_id")int city_id);

    @GET("/api/v2.1/search")
    Call<RestaurantResponse>getRestaurants(@Header("Accept")String header1, @Header("user-key")String header2, @Query("entity_id")int city_id,@Query("entity_type")String Type,@Query("cuisines")int Cusine_id);
}
