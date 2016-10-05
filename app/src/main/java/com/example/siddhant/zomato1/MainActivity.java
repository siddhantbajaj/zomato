package com.example.siddhant.zomato1;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.siddhant.zomato1.Adapter.ScreenSlidePagerAdapter;
import com.example.siddhant.zomato1.Adapter.TabSlidePagerAdapter;
import com.example.siddhant.zomato1.Fragment.CategoryFragment;
import com.example.siddhant.zomato1.Fragment.CusineFragment;
import com.example.siddhant.zomato1.Fragment.HomeFragment;
import com.example.siddhant.zomato1.Fragment.RestaurantFragment;
import com.example.siddhant.zomato1.Item.CategoryItem;
import com.example.siddhant.zomato1.Item.Cusine_Item;
import com.example.siddhant.zomato1.Item.Restaurant_Item;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity  extends AppCompatActivity implements CategoryFragment.contract,CusineFragment.contract,RestaurantFragment.contract,HomeFragment.contract {
    private static final String[] CITIES = new String[] {
            "Delhi", "Goa", "Mumbai", "ahmedabad", "Kerala","Dehradun"
    };
    ArrayList<Category>data=new ArrayList<>();

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private Toolbar toolbar_tabs;
    private ActionBar action_bar;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ViewPager TabPager1;
    private PagerAdapter TabPagerAdapter1;
    int currentPage=0;
    ProgressBar progressBar;
    FragmentManager fm;
    String cityName="Delhi";
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    TabLayout tabLayout;
    Spinner spinner;
   //  MainActivity ma=new MainActivity();

    @Override
    public void onBackPressed() {
        Intent i=new Intent();
        i.setClass(MainActivity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void Restaurant_clicked(Restaurant_Item item) {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getRestaurant().getUrl()));
        startActivity(i);

    }

    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;




    @Override
    public void category_clicked(CategoryItem itemSelected) {

        Intent i=new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse(itemSelected.getCollection().getUrl()));
       startActivity(i);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        MenuItem item = menu.findItem(R.id.spinner);
//         spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array
//                , R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);

        return true;
    }

    @Override
    public void cusine_clicked(Cusine_Item item,int city_id) {

        RestaurantFragment fragment = null;
        try {

            fragment = new RestaurantFragment();
            fragment.setListener(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.ID(city_id,item.getCuisine().getCuisine_id());

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
       fragmentManager.beginTransaction().replace(R.id.change, fragment).commit();
        fragmentManager.executePendingTransactions();

    }

    @Override
    public  String getCityName() {
        if(spinner!=null)
        cityName=spinner.getSelectedItem().toString();

        return cityName;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoryFragment f1=new CategoryFragment();
        f1.setListener(this);
        HomeFragment f2=new HomeFragment();
        f2.setListener(this);
        CusineFragment f3=new CusineFragment();
                f3.setListener(this);
       //FAB
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Raj", "Fab 1");
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Raj", "Fab 2");
            }
        });

        //tablayout
         tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        TabPager1 = (ViewPager) findViewById(R.id.viewpager);
        TabPagerAdapter1 = new TabSlidePagerAdapter(getSupportFragmentManager(),this,this);
        TabPager1.setAdapter(TabPagerAdapter1);
        TabPager1.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(TabPager1);
        setupTabIcons();

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));


//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                if(tab.getText().equals("Tab 2"))
//                {
//                    // fragmentClass = CategoryFragment.class;
//
//                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("Categories");
//                    final View v=getLayoutInflater().inflate(R.layout.category_layout,null);
//                    builder.setView(v);
//                    final AutoCompleteTextView city=(AutoCompleteTextView) v.findViewById(R.id.category);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
//                            android.R.layout.simple_dropdown_item_1line, CITIES);
//                    city.setAdapter(adapter);
//                    //  final Class finalFragmentClass = fragmentClass;
//                    builder.setPositiveButton("done", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            cityName=String.valueOf(city.getText());
//                            CategoryFragment fragment = null;
//                            try {
//
//                                fragment = new CategoryFragment();
//                                fragment.setListener(MainActivity.this);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            // Insert the fragment by replacing any existing fragment
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//                            fragmentManager.executePendingTransactions();
//
//
//                            dialog.dismiss();
//
//                        }
//                    });
//                    builder.create().show();
//                }
//                else if (tab.getText().equals("Tab 3"))
//                {
//                    AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
//                    builder1.setTitle("Cusines");
//                    final View v1=getLayoutInflater().inflate(R.layout.category_layout,null);
//                    builder1.setView(v1);
//                    final AutoCompleteTextView city1=(AutoCompleteTextView) v1.findViewById(R.id.category);
//                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,
//                            android.R.layout.simple_dropdown_item_1line, CITIES);
//                    city1.setAdapter(adapter1);
//                    //  final Class finalFragmentClass = fragmentClass;
//                    builder1.setPositiveButton("done", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            cityName=String.valueOf(city1.getText());
//                            CusineFragment fragment = null;
//                            try {
//
//                                fragment = new CusineFragment();
//                                fragment.setListener(MainActivity.this);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            // Insert the fragment by replacing any existing fragment
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//                            fragmentManager.executePendingTransactions();
//
//                            dialog.dismiss();
//
//                        }
//                    });
//                    builder1.create().show();
//                }
//                else if(tab.getText().equals("Tab 1"))
//                {
//                    HomeFragment fragment=new HomeFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//                    fragmentManager.executePendingTransactions();
//                    mPager = (ViewPager) findViewById(R.id.pager);
//                    mPagerAdapter = new ScreenSlidePagerAdapter(fm);
//                    mPager.setAdapter(mPagerAdapter);
//
//                    final Handler handler = new Handler();
//                    final Runnable Update = new Runnable() {
//                        public void run() {
//
//                            if (currentPage == NUM_PAGES) {
//                                currentPage = 0;
//                            }
//                            mPager.setCurrentItem(currentPage++, true);
//                        }
//                    };
//
//                    Timer swipeTimer = new Timer();
//                    swipeTimer.schedule(new TimerTask() {
//
//                        @Override
//                        public void run() {
//                            handler.post(Update);
//                        }
//                    }, 500, 3000);
//
//
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//
//        mPager.setAdapter(mPagerAdapter);
//
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//       Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 500, 3000);




        //SWIPE TABS
//        TabPager= (ViewPager) findViewById(R.id.tabPager);
//        mPagerAdapter = new TabSlidePagerAdapter(getSupportFragmentManager());


        // Set a Toolbar to replace the ActionBar.
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Spinner check=(Spinner)this.findViewById(R.id.spinner1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array
                , R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        check.setAdapter(adapter);
       mDrawerToggle=setupDrawerToggle();
        // Find our drawer view

        mDrawer.addDrawerListener(mDrawerToggle);



        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        //Navigation Dreawer



    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_collections_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_restaurant_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.opendarwer,  R.string.closeddarwer);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(final MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        final Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
               // fragmentClass = CategoryFragment.class;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Categories");
               final View v=getLayoutInflater().inflate(R.layout.category_layout,null);
                builder.setView(v);
                final AutoCompleteTextView city=(AutoCompleteTextView) v.findViewById(R.id.category);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, CITIES);
                city.setAdapter(adapter);
              //  final Class finalFragmentClass = fragmentClass;
                builder.setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // progressBar = (ProgressBar)findViewById(R.id.progress);
                       //progressBar.setVisibility(View.VISIBLE);

                         cityName=String.valueOf(city.getText());
                        CategoryFragment fragment = null;
                        try {

                            fragment = new CategoryFragment();
                            fragment.setListener(MainActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                     fragmentManager.beginTransaction().replace(R.id.change, fragment).commit();
                        fragmentManager.executePendingTransactions();

                        // Highlight the selected item has been done by NavigationView
                        menuItem.setChecked(true);
                        // Set action bar title
                        setTitle(menuItem.getTitle());
                        // Close the navigation drawer
                        mDrawer.closeDrawers();
                        //progressBar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                    }
                });
                builder.create().show();
                break;


            case R.id.nav_second_fragment:
                AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Cusines");
                final View v1=getLayoutInflater().inflate(R.layout.category_layout,null);
                builder1.setView(v1);
                final AutoCompleteTextView city1=(AutoCompleteTextView) v1.findViewById(R.id.category);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, CITIES);
                city1.setAdapter(adapter1);
                //  final Class finalFragmentClass = fragmentClass;
                builder1.setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cityName=String.valueOf(city1.getText());
                        CusineFragment fragment = null;
                        try {

                            fragment = new CusineFragment();
                            fragment.setListener(MainActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.change, fragment).commit();
                        fragmentManager.executePendingTransactions();

                        // Highlight the selected item has been done by NavigationView
                        menuItem.setChecked(true);
                        // Set action bar title
                        setTitle(menuItem.getTitle());
                        // Close the navigation drawer
                        mDrawer.closeDrawers();
                        dialog.dismiss();

                    }
                });
                builder1.create().show();

                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = ThirdFragment.class;
//                break;
//            default:
//                fragmentClass = FirstFragment.class;
        }


    }


    @Override
    public void home_item_clicked(CategoryItem item) {

    }
}
