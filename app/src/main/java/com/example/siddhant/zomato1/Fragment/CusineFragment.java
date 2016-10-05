package com.example.siddhant.zomato1.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siddhant.zomato1.CNInterface;
import com.example.siddhant.zomato1.CNclient;
import com.example.siddhant.zomato1.Item.CategoryItem;
import com.example.siddhant.zomato1.City;
import com.example.siddhant.zomato1.MainActivity;
import com.example.siddhant.zomato1.Response.CityResponse;
import com.example.siddhant.zomato1.Adapter.CusineAdapter;
import com.example.siddhant.zomato1.Response.CusineResponse;
import com.example.siddhant.zomato1.Item.Cusine_Item;
import com.example.siddhant.zomato1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CusineFragment extends Fragment {
    ListView listView;
    CusineAdapter cusine_adapter;
    contract mActivity;
    ArrayList<City>city_Array=new ArrayList<>();
    ArrayList<Cusine_Item>cusine_array=new ArrayList<>();
    int City_id;
    MainActivity ma=new MainActivity();

    public CusineFragment() {
        // Required empty public constructor
    }
    public interface contract{
        public void cusine_clicked(Cusine_Item item,int City_id);
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
        View v= inflater.inflate(R.layout.fragment_cusine, container, false);
        ListView listView=(ListView)v.findViewById(R.id.listVire_cusine);
        cusine_adapter=new CusineAdapter(getActivity(),cusine_array);
        listView.setAdapter(cusine_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivity.cusine_clicked(cusine_array.get(position),City_id);
            }
        });
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final CNInterface service= CNclient.getService();

        Call<CityResponse> call=service.getCity("application/json","ec0c14b3b6fcb260c79e323df7697b1d",mActivity.getCityName());
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.isSuccessful())
                {
                    ArrayList<City > batchdata=response.body().getLocation_suggestions();
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
                    City_id=city_Array.get(0).getId();
                    CNInterface service_1=CNclient.getService();
                    Call<CusineResponse>call_1=service_1.getCusines("application/json","ec0c14b3b6fcb260c79e323df7697b1d",city_Array.get(0).getId());
                    call_1.enqueue(new Callback<CusineResponse>() {
                        @Override
                        public void onResponse(Call<CusineResponse> call, Response<CusineResponse> response) {
                            if (response.isSuccessful())
                            {
                                ArrayList<Cusine_Item>collection=response.body().getCuisines();
                                if(collection==null)
                                {
                                    return;
                                }
                                cusine_array.clear();
                                for(Cusine_Item c:collection)
                                {
                                    cusine_array.add(c);
                                }
                                cusine_adapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CusineResponse> call, Throwable t) {
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
