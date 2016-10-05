package com.example.siddhant.zomato1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siddhant.zomato1.Item.Cusine_Item;
import com.example.siddhant.zomato1.R;

import java.util.ArrayList;

/**
 * Created by ABC on 08-07-2016.
 */
public class CusineAdapter extends ArrayAdapter<Cusine_Item> {
    Context mcontext;
    ArrayList<Cusine_Item>marr;

    public CusineAdapter(Context context,  ArrayList<Cusine_Item> objects) {
        super(context, 0, objects);
        mcontext=context;
        marr=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v= LayoutInflater.from(mcontext).inflate(R.layout.cusine_item,parent,false);
        }
        TextView txt=(TextView)v.findViewById(R.id.text4);
        txt.setText(marr.get(position).getCuisine().getCuisine_name());
        return v;
    }
}
