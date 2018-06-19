package com.example.precious.practicalproject.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AlertDialog;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.precious.practicalproject.DataAdapter.ContentAdapter;
import com.example.precious.practicalproject.DataHolder.ContentHolder;
import com.example.precious.practicalproject.DataHolder.VerifyMatric;

import com.example.precious.practicalproject.MyBounceInterpolator;
import com.example.precious.practicalproject.Queries.QueryClass;
import com.example.precious.practicalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.precious.practicalproject.Queries.QueryClass.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Download extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    ArrayList<VerifyMatric> firstdataHolders = new ArrayList<>();

    View view;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    TextView student_name, student_matric, student_college, student_program, student_level;
    ArrayList<ContentHolder> contentHolderRes = new ArrayList<>();
    ListView listView;
    LinearLayout empty;
    AlertDialog.Builder builder1;
    AlertDialog allert12;
    EditText checkMatric;
    String formattedMatric;
    ProgressDialog dialog;
    ContentAdapter contentAdapter;
    Context cont;
    String name, matric, program, level, college;

    FloatingActionButton floatingActionButton;
    ArrayList<ContentHolder> courseMaterial = new ArrayList<>();
    ArrayList<String> whatToDownloadLink = new ArrayList<>();
    ArrayList<String> whatToDownloadCode = new ArrayList<>();
    private Boolean isStarted = false;
    private Boolean isVisible = false;






    public Download() {
        // Required empty public constructor
    }



    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.cancel();

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (dialog != null || dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }

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




    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.downloadlayout, container, false);





        cont = getContext();

        student_name = (TextView) rootView.findViewById(R.id.student_name);
        student_matric = (TextView) rootView.findViewById(R.id.student_matric);
        student_college = (TextView) rootView.findViewById(R.id.student_college);
        student_program = (TextView) rootView.findViewById(R.id.student_program);
        student_level = (TextView) rootView.findViewById(R.id.student_Level);

        listView = (ListView) rootView.findViewById(R.id.list_check);
        checkMatric = (EditText) rootView.findViewById(R.id.InputMatric);
        checkMatric.setHint("Input your matric");
        checkMatric.setGravity(Gravity.CENTER_HORIZONTAL);



        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabb);

        final Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                floatingActionButton.startAnimation(bounce);


                for (int i = 0; i < contentAdapter.getSelectedCountry().size(); i++) {

                    String link = contentAdapter.getSelectedCountry().get(i).getCourse_Link();
                    String code = contentAdapter.getSelectedCountry().get(i).getCourse_Code();


                    whatToDownloadLink.add(link);
                    whatToDownloadCode.add(code);


                }

                if (whatToDownloadLink.size() == 0) {
                    Snackbar snackbar = Snackbar.make(rootView, "Nothing selected!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {


                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                       requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                               MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
                               );


                    } else {
                        //TODO

                        Snackbar snackbar = Snackbar.make(rootView, "Download started, you will be notified once it's completed: " + "(" + whatToDownloadLink.size() + ")", Snackbar.LENGTH_LONG);
                        snackbar.show();


                        for (int i = 0; i < whatToDownloadLink.size(); i++) {


                            String url = whatToDownloadLink.get(i);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setDescription("Download in progress");
                            request.setTitle("Downloading " + whatToDownloadCode.get(i));
// in order for this if to run, you must use the android 3.2 to compile your app
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, whatToDownloadCode.get(i) + ".PDF");

// get download service and enqueue file
                            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);

                        }
                        whatToDownloadLink.clear();

                    }


                }
            }
        });


        dialog = new ProgressDialog(getContext());
        dialog.setIcon(R.drawable.bellslogo);
        dialog.setTitle("Fetching data");

        dialog.setMessage(getString(R.string.progressDialogMessage));




        final Button button = (Button) rootView.findViewById(R.id.clickButton);
        button.setText(R.string.fetch);
        ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.startAnimation(bounce);

                String matricNumber = checkMatric.getText().toString();

                if(matricNumber.length() > 9 || matricNumber.length() < 9){
                    Toast.makeText(getContext(), "Invalid Matric Number.. Please try again", Toast.LENGTH_LONG).show();
                }

                else {


                    formattedMatric = matricNumber.replace("/", "");
                    if (formattedMatric.length() > 8 || formattedMatric.length() < 8) {
                        Toast.makeText(getContext(), "Invalid Matric Number.. Please try again", Toast.LENGTH_LONG).show();
                    } else if (!isInteger(formattedMatric)) {

                        Toast.makeText(getContext(), "Invalid Matric Number.. Please try again", Toast.LENGTH_LONG).show();
                    } else {


                        backgroundWork newTask = new backgroundWork();
                        newTask.execute("http://techeda.com/apiexample/verifymatric.php");

                        dialog.setCancelable(false);
                        dialog.show();


                    }

                }


            }
        });


        empty = (LinearLayout) rootView.findViewById(R.id.empty);


        listView.setEmptyView(empty);


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


        try {
            if (savedInstanceState != null) {
                courseMaterial = savedInstanceState.getParcelableArrayList("adapterContent");
                name = savedInstanceState.getString("Name");
                college = savedInstanceState.getString("college");
                program = savedInstanceState.getString("program");
                matric = savedInstanceState.getString("matric");
                level = savedInstanceState.getString("level");


                if (getContext() != null) {
                    contentAdapter = new ContentAdapter(getActivity(), courseMaterial);
                    listView.setAdapter(contentAdapter);
                    student_name.setText(name);
                    student_college.setText(college);
                    student_program.setText(program);
                    student_matric.setText(matric);
                    student_level.setText(level);

                }


            }
        } catch (ExceptionInInitializerError e) {
            Log.e(LOG_TAG, " Error fetching data from save instance ");
        }


        ViewFlipper viewFlipper = (ViewFlipper) rootView.findViewById(R.id.e_library_linearlayout);


        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        in.setDuration(1500);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        out.setDuration(1500);

//         set the animation type to ViewFlipper
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(8000);
        viewFlipper.setAutoStart(true);






        view = rootView;


            return view;


    }


    public class backgroundWork extends AsyncTask<String, Void, ArrayList<VerifyMatric>> {

        @Override
        protected void onPostExecute(ArrayList<VerifyMatric> contentHolders) {

//            allert12.cancel();


            Log.e("MainActivity", "The size of firstData is" + firstdataHolders.size());


            contentHolders = firstdataHolders;


            if (contentHolders != null && contentHolders.size() != 0) {

                Log.e("MainActivity", "The size of ContentHolder is" + contentHolders.size());

                listView.setEmptyView(empty);


                college = contentHolders.get(0).getCollege();
                level = contentHolders.get(0).getLevel();
                name = contentHolders.get(0).getName();

                program = contentHolders.get(0).getProgramme();


                ArrayList<ContentHolder> peter = getCourse(college);
                if (peter != null && peter.size() != 0) {

                    if (dialog.isShowing()) {
                        for (int i = 0; i < peter.size(); i++) {
                            courseMaterial.add(peter.get(i));
                        }
                    }

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }


                    if (getContext() != null) {
                        contentAdapter = new ContentAdapter(getContext(), courseMaterial);
                        listView.setAdapter(contentAdapter);
                        student_college.setText(college);
                        student_name.setText(contentHolders.get(0).getName());
                        student_level.setText(contentHolders.get(0).getLevel() + "L");
                        matric = String.valueOf(contentHolders.get(0).getMatric());

                        String newMatric = "";
                        char c = matric.charAt(3);
                        newMatric = Character.toString(c);
                        matric = matric.replace(newMatric, newMatric + "/");

                        student_matric.setText(matric);
                        student_program.setText(contentHolders.get(0).getProgramme());

                    }


                } else if (peter.size() == 0) {
                    dialog.dismiss();

                    builder1.setTitle("No Internet");

                    builder1.setMessage("Please check internet connection");

                    AlertDialog alert11 = builder1.create();
                    allert12 = alert11;
                    allert12.show();

                }


            } else if (!QueryClass.isConnected) {

                dialog.dismiss();

                builder1.setTitle("No Internet");

                builder1.setMessage("Please check internet connection");

                AlertDialog alert11 = builder1.create();
                allert12 = alert11;
                allert12.show();

            } else if (!QueryClass.matricIsCorrect) {

                dialog.dismiss();

                builder1.setTitle("Incorrect Matric");

                builder1.setMessage("Matric number is Incorrect");
                AlertDialog alert11 = builder1.create();
                allert12 = alert11;
                allert12.show();
            }

            Log.e("MainActivity", " " + QueryClass.matricIsCorrect);

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            Log.e("ProgressUPdate", "The progress update is running");
            Log.e("ProgressUPdate", "The progress update is running");


//            AlertDialog alert11 = builder1.create();
//            allert12= alert11;
//            allert12.show();


        }


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected ArrayList<VerifyMatric> doInBackground(String... urls) {


            Log.v("Mainactivity", "doInBackground just started running");

            if (urls.length < 1 || urls[0] == null) {
                return null;
            } else {


                firstdataHolders = QueryClass.fetchDataHolder(urls[0], formattedMatric);
            }

            publishProgress();

//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                Log.e("Thread", "The thread slept caused a problem");
//            }
            return firstdataHolders;
        }

    }


    public ArrayList<ContentHolder> getCourse(final String college) {

        String JSON_URL = "http://techeda.com/apiexample/queryall.php";


        //getting the progressbar


        //making the progressbar visible


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        ArrayList<ContentHolder> NewContentHolder = new ArrayList<>();


                        try {
                            //getting the whole json object from the response
                            JSONArray array = new JSONArray(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array

                            Log.e("MainActivity", "The Array size is " + array.length());
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                Log.e("MainActivity", "The object size is " + object.length());

                                String precious = college.substring(0, 1).toUpperCase() + college.substring(1);


                                for (int p = 0; p < object.length(); p++) {

                                    JSONArray jsonArray = null;

                                    Boolean isOBJ = true;

                                    try {
                                        jsonArray = object.getJSONArray(precious + "F");
                                    } catch (JSONException e) {
                                        isOBJ = false;
                                    }

                                    if (isOBJ) {

                                        for (int b = 0; b < jsonArray.length(); b++) {
                                            JSONObject obj = jsonArray.getJSONObject(b);
                                            String Course_Code = obj.getString("Course_Code");
                                            String Course_Title = obj.getString("Course_Title");
                                            String Course_Link = obj.getString("Course_Link");
                                            ContentHolder contentHolder1 = new ContentHolder(Course_Code, Course_Title, Course_Link);
                                            contentHolderRes.add(contentHolder1);
                                        }

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("MainActivity", "Problem parsing the data in custom volley" + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);


        Log.e("MainActivity", "The contentHolderRes size is " + contentHolderRes.size());

        return contentHolderRes;
    }


    public boolean isInteger(String value) {

        boolean isInteger = true;

        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            isInteger = false;
        }

        return isInteger;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("adapterContent", courseMaterial);
        outState.putString("Name", name);
        outState.putString("college", college);
        outState.putString("program", program);
        outState.putString("matric", matric);
        outState.putString("level", level);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("Download", "Snackbar is not showing to the user");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO

                    Snackbar snackbar = Snackbar.make(getView(), "Download started, you will be notified once it's completed: " + "(" + whatToDownloadLink.size() + ")", Snackbar.LENGTH_LONG);
                    snackbar.show();


                    for (int i = 0; i < whatToDownloadLink.size(); i++) {


                        String url = whatToDownloadLink.get(i);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setDescription("Download in progress");
                        request.setTitle("Downloading " + whatToDownloadCode.get(i));
// in order for this if to run, you must use the android 3.2 to compile your app
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                        }
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, whatToDownloadCode.get(i) + ".PDF");

// get download service and enqueue file
                        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);

                    }
                    whatToDownloadLink.clear();
                    whatToDownloadCode.clear();

                } else {

                    Snackbar snackbar = Snackbar.make(getView(), "Permission Denied cannot continue with file download", Snackbar.LENGTH_LONG);
                    snackbar.show();


                    Log.e("Download", "Snackbar is not showing to the user");

                    whatToDownloadCode.clear();
                    whatToDownloadLink.clear();
                }

                break;

            default:
                break;
        }
    }

}
