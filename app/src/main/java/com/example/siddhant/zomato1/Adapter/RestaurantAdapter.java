package com.example.siddhant.zomato1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siddhant.zomato1.Item.CategoryItem;
import com.example.siddhant.zomato1.Item.Restaurant_Item;
import com.example.siddhant.zomato1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ABC on 08-07-2016.
 */
public class RestaurantAdapter extends BaseAdapter {
    Context mycontext;
    ArrayList<Restaurant_Item> myarr;
    public RestaurantAdapter(Context context, ArrayList<Restaurant_Item> objects) {

        myarr=objects;
        mycontext=context;
    }

    @Override
    public int getCount() {
        return myarr.size();
    }

    @Override
    public Object getItem(int position) {
        return myarr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v= LayoutInflater.from(mycontext).inflate(R.layout.image_layout,parent,false);
        }
        ImageView image=(ImageView)v.findViewById(R.id.category_image);
        TextView text=(TextView)v.findViewById(R.id.category_text);
        if(myarr.get(position).getRestaurant().getFeatured_image()!=null&&myarr.get(position).getRestaurant().getFeatured_image()!="") {
            Picasso.with(mycontext).load(myarr.get(position).getRestaurant().getFeatured_image()).fit().centerCrop().into(image);
            text.setText(myarr.get(position).getRestaurant().getName());
        }





        return v;
    }
}
