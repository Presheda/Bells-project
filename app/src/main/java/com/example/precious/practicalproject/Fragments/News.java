package com.example.precious.practicalproject.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.precious.practicalproject.DataAdapter.NewsAdapter;
import com.example.precious.practicalproject.DataHolder.NewsHolder;
import com.example.precious.practicalproject.MainActivity;
import com.example.precious.practicalproject.MyBounceInterpolator;
import com.example.precious.practicalproject.Queries.NewsQuery;
import com.example.precious.practicalproject.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class News extends Fragment {



    public LinearLayout emptyView;
    ProgressDialog dialog;
    AlertDialog.Builder builder1;
    AlertDialog allert12;
    public ListView listView;
    public ArrayList<NewsHolder> result;
    public String LOG_TAG =  News.class.getName();
    View view;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public News() {
        // Required empty public constructor
    }



    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted){
            viewDidAppear();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewDidAppear();
        }
    }

    public void viewDidAppear() {
        // your logic
        Log.e("Download", "viewDidAppear() is called");
        final Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);
        view.startAnimation(bounce);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.newslayout, container, false);


        Log.e("Download", "pagerAdapter is " + MainActivity.pagerAdapter  );

        listView = (ListView) rootView.findViewById(R.id.news_listView);

        final Button fetch_button = (Button) rootView.findViewById(R.id.new_fetch_button);


        emptyView = (LinearLayout) rootView.findViewById(R.id.newsEmptyView);

        final Animation animScale = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);

        listView.setEmptyView(emptyView);

        dialog = new ProgressDialog(getContext());
        dialog.setIcon(R.drawable.bellslogo);
        dialog.setTitle("Fetching News");
        dialog.setMessage("Loading, please wait");
        dialog.setCancelable(false);

        builder1 = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);

        builder1.setIcon(R.drawable.bellslogo);

        builder1.setCancelable(true);


        builder1.setNegativeButton(
                "Cancle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //dialog.cancel();
                    }
                });





            fetch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_button.startAnimation(animScale);
                backgroundTask newTask = new backgroundTask();
                newTask.execute("http://techeda.com/apiexample/pdf_file.php");
                dialog.show();
            }
        });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NewsHolder newsHolder = result.get(i);


                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( "http://docs.google.com/viewer?url=" +
                            newsHolder.getUri()));

                    startActivity(intent);



                }
            });


            try{
                if(savedInstanceState != null){
                    result  = savedInstanceState.getParcelableArrayList("Name");

                    if(listView != null){

                        if(result != null ){

                            NewsAdapter newsAdapter = new NewsAdapter(getContext(), result);
                            listView.setAdapter(newsAdapter);
                        }


                    }

                }
            }
            catch (ExceptionInInitializerError e ){
                Log.e(LOG_TAG, " Error fetching data from save instance ");
            }


            view = rootView;


        return view;
    }


    public class backgroundTask extends AsyncTask<String, Void, ArrayList<NewsHolder>>{

        @Override
        protected void onPostExecute(ArrayList<NewsHolder> newsHolders) {



           if(dialog.isShowing()){
               dialog.dismiss();
           }

            result = newsHolders;

            if(result != null && result.size() > 1){

                if(getContext() != null){

                    NewsAdapter newsAdapter = new NewsAdapter(getContext(), result);

                    listView.setAdapter(newsAdapter);

                }

            }


            else if(result ==  null || result.size()==0){

                builder1.setTitle("No Internet");

                builder1.setMessage("Please check internet connection");
                AlertDialog alert11;
                alert11 = builder1.create();
                allert12= alert11;
                allert12.show();
            }




        }

        @Override
        protected ArrayList<NewsHolder> doInBackground(String... strings) {

            if(strings.length< 1 || strings[0] == null){
                return null;
            }

            else{
                Log.v(LOG_TAG, "doInBackground is about to call query class to fetch the data");
                result = NewsQuery.fetchNews(strings[0]);
            }
            Log.v(LOG_TAG, "doInBackground just fetched the data, its about to return it");
            
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e){
                Log.e("Thread", "The thread slept caused a problem");
            }

            return result;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(result != null){
            if(result.size() > 0 ){

                super.onSaveInstanceState(outState);
                outState.putParcelableArrayList("Name", result);
            }
        }
    }




    @Override
    public void onPause() {
        super.onPause();
        if(dialog!=null){
            dialog.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(dialog!=null || dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (dialog!=null && dialog.isShowing() ){
            dialog.cancel();
        }


    }

}
