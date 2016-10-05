package com.example.siddhant.zomato1.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.siddhant.zomato1.Fragment.CategoryFragment;
import com.example.siddhant.zomato1.Fragment.CusineFragment;
import com.example.siddhant.zomato1.Fragment.HomeFragment;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment1;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment2;
import com.example.siddhant.zomato1.MainActivity;

/**
 * Created by ABC on 12-07-2016.
 */
public class TabSlidePagerAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;
    CategoryFragment.contract mcontext;
    CusineFragment.contract mcontext1;
    public TabSlidePagerAdapter(FragmentManager fm,CategoryFragment.contract obj,CusineFragment.contract obj1) {
        super(fm);
        mcontext=obj;
        mcontext1=obj1;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new HomeFragment();

            case 1:
                CategoryFragment fm=new CategoryFragment();
                fm.setListener(mcontext);
                return fm;

            case 2:
                CusineFragment fm1=new CusineFragment();
                fm1.setListener(mcontext1);
                return fm1;
            default:
                return new ScreenSlidePageFragment2();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
