package com.example.precious.practicalproject.Queries;

import android.util.Log;

import com.example.precious.practicalproject.DataHolder.VerifyMatric;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Precious on 1/17/2018.
 */




public class QueryClass {


    public static boolean isConnected = true;

    public static boolean matricIsCorrect =  true;


    public static final String LOG_TAG = QueryClass.class.getSimpleName();


    public QueryClass() {
    }

    public static ArrayList<VerifyMatric> fetchDataHolder(String url, String checkMatric) {
        URL url1 = creatURL(url);

        String Json = null;

        try {
            Json = makeConnection(url1);
        } catch (Exception e) {
            isConnected = false;
            Log.e(LOG_TAG, "Problem fetching data " + e);


        }

        ArrayList<VerifyMatric> VerifyMatric = extractDataHolder(Json, checkMatric);

        return VerifyMatric;
    }


    public static ArrayList<VerifyMatric> extractDataHolder(String JsonResponse, String checkMatric) {



        Log.e(LOG_TAG, "checkMatric is " + checkMatric);

        int checkMat = Integer.parseInt(checkMatric);


        ArrayList<VerifyMatric> verifyMatrics = new ArrayList<>();

        try {
//            Log.e(LOG_TAG, checkMatric);

            Log.e(LOG_TAG, "The try-block in extractDataHolder() just started");

            JSONArray jsonArray = new JSONArray(JsonResponse);

            Log.e(LOG_TAG, "" + jsonArray.length());
            //traversing through all the object
            for (int i = 0; i < jsonArray.length(); i++) {

                Log.e(LOG_TAG, "try-block is still running");

                //getting product object from json array
                JSONObject product = jsonArray.getJSONObject(i);

                int matric = product.getInt("Matric");
                Log.e(LOG_TAG, "matric is " + matric);
                if (matric == checkMat) {
                    String name = product.getString("Name");
                    String programme = product.getString("Programme");
                    String College = product.getString("College");
                    String Level = product.getString("Level");

                    VerifyMatric verifyMatric = new VerifyMatric(matric, name, programme, College, Level);
                    verifyMatrics.add(verifyMatric);


                } else {

                    matricIsCorrect = false;
                    isConnected = true;
                }


            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON data " + e);
            matricIsCorrect = true;
            isConnected =  false;
        } finally {
            Log.e(LOG_TAG, " This is the size of the ArrayList<> " + verifyMatrics.size() + " " + checkMatric);
        }


        return verifyMatrics;
    }

    /**
     * Creates a url
     */
    public static URL creatURL(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.v(LOG_TAG, "Error Creating Url");
        }

        return url;
    }

    /**
     * Makes a connection to given url
     */

    public static String makeConnection(URL url) throws Exception {
        String someUrl = "";

        if (url == null) {
            return someUrl;
        }

        HttpURLConnection urlConnection = null;
        InputStream stream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);  /** Milliseconds */
            urlConnection.setConnectTimeout(15000); /** Milliseconds */
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //if the request was succesful, the response code is 200

            if (urlConnection.getResponseCode() == 200) {
                stream = urlConnection.getInputStream();
                someUrl = readFromInputStream(stream);
            } else {
                Log.v(LOG_TAG, "The response code is " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.v(LOG_TAG, "Problem making the connection" + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            } else if (stream != null) {
                stream.close();
            }
        }

        Log.v(LOG_TAG, "This method fetches the data");

        return someUrl;
    }

    public static String readFromInputStream(InputStream stream) throws Exception {
        StringBuilder output = new StringBuilder();
        if (stream != null) {
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader newReader = new BufferedReader(reader);
            String line = newReader.readLine();

            while (line != null) {
                output.append(line);
                line = newReader.readLine();
            }
        }

        return output.toString();
    }


}