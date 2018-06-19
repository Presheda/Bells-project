package com.example.precious.practicalproject.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Type;
import com.example.precious.practicalproject.MainActivity;
import com.example.precious.practicalproject.MyBounceInterpolator;
import com.example.precious.practicalproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class e_library extends Fragment {

    View view;
    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public e_library() {
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
        final Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.slidein);

        view.startAnimation(bounce);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.e_library, container, false);


        Log.e("Download", "pagerAdapter is " + MainActivity.pagerAdapter   );

       final LinearLayout viewFlipper = (LinearLayout) rootView.findViewById(R.id.e_library_linearlayout);


       final Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);

        in.setDuration(1500);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        out.setDuration(1500);




        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setAnimation(in);
                Snackbar snackbar =  Snackbar.make(rootView, "I said am coming", Snackbar.LENGTH_LONG);
                snackbar.show();




            }
        });


        view =  rootView;

        return view;


    }



}
