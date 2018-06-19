package com.example.precious.practicalproject;


import android.app.Fragment;
import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.precious.practicalproject.Fragments.Download;
import com.example.precious.practicalproject.Fragments.News;
import com.example.precious.practicalproject.Fragments.e_library;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static int pagerAdapter;
    public static final String message = "message";
    public static String title;
    TabLayout tabLayout;
    private int[] navIcons =  {R.mipmap.ic_file_download_white_48dp, R.mipmap.ic_file_download_white_48dp, R.mipmap.ic_file_download_white_48dp};
    private int[] navIconsActive =  {R.mipmap.ic_file_download_white_48dp, R
            .mipmap.ic_file_download_white_48dp, R.mipmap.ic_file_download_white_48dp};
    private String[] navLabels = {"Tab 1", "Tab 2", "Tab 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ViewPager viewPager =  (ViewPager) findViewById(R.id.viewpager);


         tabLayout =  (TabLayout) findViewById(R.id.sliding_tabs);

        final Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

         tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
         tabLayout.startAnimation(bounce);







        FragmentPagerAdapter fragmentPagerAdapter =  new FragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(fragmentPagerAdapter);









        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {




            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_file_download_white_48dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_library_books_white_48dp);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_notifications_active_white_48dp);


//        setUpTabIcons();




        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);



       final Spinner  spinner = (Spinner) navigationView.getMenu().findItem(R.id. spinner).getActionView();
        final Spinner  admission_spinner =  (Spinner) navigationView.getMenu().findItem(R.id.admission_spinner).getActionView();




        final String[] langs = {"Places in Bells", "Multipurpose Hall", "Edozien Lecture Theatre", "Library", "Upview Daytime",
                "Upview At Night", "Uptown"};

        final ArrayAdapter<String> adapternew = new   ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, langs){

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                TextView tv = (TextView) view;

                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;

            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

               if(position == 0){
                   ((TextView) v).setTextColor(Color.GRAY);
               }

               return v;
            }
        };


//        spinner.setGravity();
//        spinner.setPopupBackgroundDrawable();
        spinner.setAdapter(adapternew);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //trying to avoid undesired spinner selection changed event, a known problem
                if (position == 0) {
                    if(view !=null){
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                }
                if (drawer.isDrawerOpen(navigationView)) {
                    drawer.closeDrawer(navigationView);
                }

                if (position != 0) {
                  final  Intent intent = new Intent(MainActivity.this, PlacesInBells.class);
                    String dataToSend = langs[position] + position;
                    intent.putExtra(message, dataToSend);

                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            startActivity(intent);
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 500);


                }

                    spinner.setGravity(5);
                    spinner.setSelection(0);



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        final String[] array =  {"Admission", "Undergraduate", "Postgraduate  ", "Foundation Programme"};

        final ArrayAdapter<String> arrayAdapter = new  ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array){

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                 View view =  super.getDropDownView(position, convertView, parent);

                TextView tv = (TextView) view;

                 if(position == 0){
                     tv.setTextColor(Color.GRAY);

                 }

                 else {
                     tv.setTextColor(Color.BLACK);
                 }



                return view;
            }


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                if(position == 0){
                    ((TextView) v).setTextColor(Color.GRAY);
                }

                return v;
            }
        };



        admission_spinner.setAdapter(arrayAdapter);

        admission_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String url="";
                    if(i == 0){
                        if(view != null){

                        }

                    }

                    else if(i ==  1){
                        url="http://bellsuniversity.edu.ng/admissions/undergraduates/";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }


                else if(i ==  2){
                    url="http://bellsuniversity.edu.ng/admissions/postgraduates/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }


                else {
                        url="http://bellsuniversity.edu.ng/admissions/predegree/";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }


                admission_spinner.setGravity(5);
                admission_spinner.setSelection(0);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }




    public void setUpTabIcons(){

        // loop through all navigation tabs
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nav_tab, null);

            // get child TextView and ImageView from this layout for the icon and label
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_label.setText(navLabels[i]);

            // set the home to be active at first
            if(i == 0) {
                tab_label.setTextColor(getResources().getColor(R.color.colorAccent));
                tab_icon.setImageResource(navIconsActive[i]);
            } else {
                tab_icon.setImageResource(navIcons[i]);
            }

            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String url ="";
        if(id == R.id.college){

            boolean isDrawerClose =  false;

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawers();
//           isDrawerClose =   drawer.isDrawerVisible(navigationView);
//
//            isDrawerClose = true;

            final Intent intent = new Intent(MainActivity.this, college.class);



            Runnable r = new Runnable() {
                @Override
                public void run(){
                    startActivity(intent);
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 500);



        }

        else if (id == R.id.academic_calender){

            url="http://docs.google.com/viewer?url=http://www.bellsuniversity.edu.ng/docs/2016-2017%20ACADEMIC%20CALENDAR.pdf";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);


        }

       else if(id == R.id.time_table ){
            url="http://docs.google.com/viewer?url=http://www.bellsuniversity.edu.ng/docs/2016-2017%20LECTURE%20TIME%20TABLE.pdf";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);


        }

        else if (id == R.id.Our_Achievement) {
              url = "http://www.bellsuniversity.edu.ng.cp-50.webhostbox.net/about/index.php?page=achievements";
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
              startActivity(intent);
        }

        else if(id == R.id.Our_History){

              url = "http://www.bellsuniversity.edu.ng.cp-50.webhostbox.net/about/index.php?page=history";
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
              startActivity(intent);
          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
