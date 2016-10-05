package com.example.siddhant.zomato1.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.siddhant.zomato1.Adapter.RestaurantAdapter;
import com.example.siddhant.zomato1.Adapter.imageAdapter;
import com.example.siddhant.zomato1.CNInterface;
import com.example.siddhant.zomato1.CNclient;
import com.example.siddhant.zomato1.City;
import com.example.siddhant.zomato1.Item.CategoryItem;
import com.example.siddhant.zomato1.Item.Restaurant_Item;
import com.example.siddhant.zomato1.MainActivity;
import com.example.siddhant.zomato1.R;
import com.example.siddhant.zomato1.Response.CategoryResponse;
import com.example.siddhant.zomato1.Response.RestaurantResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {
    GridView restaurant_gridView;
    RestaurantAdapter restaurant_adapter;
    ArrayList<Restaurant_Item> restaurant_Array=new ArrayList<>();
    ArrayList<City>city_Array=new ArrayList<>();
    contract mActivity;
    int CityId;
    int CusineId;
    MainActivity ma=new MainActivity();


    public RestaurantFragment() {
        // Required empty public constructor
    }
    public interface contract{
        public void Restaurant_clicked(Restaurant_Item item);
        public String getCityName();
    }
    public void setListener(contract obj)
    {
        mActivity=obj;
    }
    public void ID(int cityId,int cusineId)
    {
        CityId=cityId;
        CusineId=cusineId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_restaurant, container, false);
        restaurant_gridView=(GridView)v.findViewById(R.id.gridview_restaurant);
        restaurant_adapter=new RestaurantAdapter(getActivity(),restaurant_Array);
        restaurant_gridView.setAdapter(restaurant_adapter);
        restaurant_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivity.Restaurant_clicked(restaurant_Array.get(position));
            }
        });
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final CNInterface service_1= CNclient.getService();
        Call<RestaurantResponse> call_1=service_1.getRestaurants("application/json","ec0c14b3b6fcb260c79e323df7697b1d",CityId,"city",CusineId);
        call_1.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful())
                {
                    ArrayList<Restaurant_Item>collection=response.body().getRestaurants();
                    if(collection==null)
                    {
                        return;
                    }
                    restaurant_Array.clear();
                    for(Restaurant_Item c:collection)
                    {
                        restaurant_Array.add(c);
                    }
                    restaurant_adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        return v;
    }

}
