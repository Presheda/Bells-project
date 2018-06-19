package com.example.precious.practicalproject.Queries;

import android.util.Log;

import java.net.URL;
import java.util.ArrayList;


import com.example.precious.practicalproject.DataHolder.NewsHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.precious.practicalproject.Queries.GeneralQuery.*;


/**
 * Created by Precious on 2/21/2018.
 */

public class NewsQuery {

    public static final String LOG_TAG =  NewsQuery.class.getName();


    public NewsQuery(){

    }


    public static ArrayList<NewsHolder> fetchNews(String url){
        URL urllist =  creatURL(url);

        String Json =  null;

         try {
             Json = makeConnection(urllist);
         }

         catch (Exception e){
             Log.v(LOG_TAG, "Problem fetching data");
         }

         ArrayList<NewsHolder> dataTopresent = extractNews(Json);

         return dataTopresent;
    }

    public static ArrayList<NewsHolder> extractNews(String JsonResponse){

        ArrayList<NewsHolder>  Newsdata =  new ArrayList<>();

        try{

            JSONArray jsonArray =  new JSONArray(JsonResponse);

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String Title =  jsonObject.getString("Title");
                String Uri =  jsonObject.getString("Uri");
                String Date = jsonObject.getString("Date");
                int Level =  jsonObject.getInt("Level");

                Newsdata.add(new NewsHolder(Title, Uri, Date, Level));
            }

        }

        catch (JSONException e){
            Log.v(LOG_TAG, "Problem parsing the JSON data");
        }

        return  Newsdata;
    }

}
