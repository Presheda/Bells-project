package com.example.precious.practicalproject.Queries;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Precious on 2/21/2018.
 */

public class GeneralQuery {

    public static final String LOG_TAG = GeneralQuery.class.getSimpleName();

    public GeneralQuery(){

    }

    /**  Creates a url */
    public static URL creatURL(String Stringurl){

        URL url = null;

        try {
            url = new URL(Stringurl);
        }

        catch (MalformedURLException e){
            Log.v(LOG_TAG, "Error Creating Url");
        }

        return url;
    }


    /**  Makes a connection to a given url */
    public  static String makeConnection(URL url) throws Exception{


        String someUrl ="";

        if (url == null){
            return someUrl;
        }

        HttpURLConnection urlConnection = null;
        InputStream stream = null;

        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); /* Milliseconds */
            urlConnection.setConnectTimeout(15000); /*Milliseconds */
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //if the request was successful, the response code is 200
            if(urlConnection.getResponseCode() == 200){
                stream = urlConnection.getInputStream();
                someUrl = readFromInputStream(stream);
            }

            else {
                Log.v(LOG_TAG, "The response code is" + urlConnection.getResponseCode());
            }

        }

        catch (IOException e){
            Log.v(LOG_TAG, "Problem making the connection" + e);
        }

        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            else if(stream != null){
                stream.close();
            }
        }

        Log.v(LOG_TAG, "This method fetches the data");

        return someUrl;
    }



    public static String readFromInputStream(InputStream stream) throws  Exception{
        StringBuilder output = new StringBuilder();
        if(stream != null){
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader newReader = new BufferedReader(reader);
            String line  = newReader.readLine();

            while (line != null){
                output.append(line);
                line = newReader.readLine();
            }

        }

        return output.toString();
    }



}
