package com.example.siddhant.zomato1.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.siddhant.zomato1.CNInterface;
import com.example.siddhant.zomato1.CNclient;
import com.example.siddhant.zomato1.Item.CategoryItem;
import com.example.siddhant.zomato1.MainActivity;
import com.example.siddhant.zomato1.Response.CategoryResponse;
import com.example.siddhant.zomato1.City;
import com.example.siddhant.zomato1.Response.CityResponse;
import com.example.siddhant.zomato1.R;
import com.example.siddhant.zomato1.Adapter.imageAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    GridView category_gridView;
    imageAdapter category_adapter;
    ArrayList<CategoryItem>category_Array=new ArrayList<>();
    ArrayList<City>city_Array=new ArrayList<>();
    contract mActivity;
    MainActivity ma=new MainActivity();


    public CategoryFragment() {
        // Required empty public constructor
    }
    public interface contract{
        public void category_clicked(CategoryItem item);
        public String getCityName();
    }
    public void setListener(contract obj)
    {
        mActivity=obj;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_category, container, false);
        category_gridView=(GridView)v.findViewById(R.id.gridview_category);
        category_adapter=new imageAdapter(getActivity(),category_Array);
        category_gridView.setAdapter(category_adapter);
        category_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivity.category_clicked(category_Array.get(position));
            }
        });
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final CNInterface service= CNclient.getService();

        Call<CityResponse> call=service.getCity("application/json","ec0c14b3b6fcb260c79e323df7697b1d",mActivity.getCityName());
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.isSuccessful())
                {

                    ArrayList<City >batchdata=response.body().getLocation_suggestions();
                    if (batchdata == null)
                        return;
                    city_Array.clear();
                    for (City b: batchdata) {
                        city_Array.add(b);
                    }
                    if(city_Array.size()==0)
                    {
                        Toast.makeText(getActivity(),"City Not Found",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        return;
                    }
                    CNInterface service_1=CNclient.getService();
                    Call<CategoryResponse>call_1=service_1.getCategories("application/json","ec0c14b3b6fcb260c79e323df7697b1d",city_Array.get(0).getId());
                    call_1.enqueue(new Callback<CategoryResponse>() {
                        @Override
                        public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                            if (response.isSuccessful())
                            {
                                ArrayList<CategoryItem>collection=response.body().getCollections();
                                if(collection==null)
                                {
                                    return;
                                }
                                category_Array.clear();
                                for(CategoryItem c:collection)
                                {
                                    category_Array.add(c);
                                }
                                category_adapter.notifyDataSetChanged();

                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CategoryResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
        return v;
    }

}
