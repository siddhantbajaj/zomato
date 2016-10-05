package com.example.siddhant.zomato1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment1;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment2;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment3;
import com.example.siddhant.zomato1.Fragment.ScreenSlidePageFragment4;

/**
 * Created by ABC on 10-07-2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        if(position==1)
//        return new ScreenSlidePageFragment();
//        else
//            return new ScreenSlidePageFragment1();
        switch (position){
            case 0:return new ScreenSlidePageFragment();

            case 1:return new ScreenSlidePageFragment1();

            case 2:return new ScreenSlidePageFragment2();

            case 3:return new ScreenSlidePageFragment3();

            case 4:return new ScreenSlidePageFragment4();

            default:return new ScreenSlidePageFragment();

        }


    }

    @Override
    public int getCount() {
        return 5;
    }
}
