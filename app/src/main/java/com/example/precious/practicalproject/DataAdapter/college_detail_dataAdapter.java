package com.example.precious.practicalproject.DataAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.precious.practicalproject.DataHolder.ContentHolder;
import com.example.precious.practicalproject.DataHolder.college_detail_data;
import com.example.precious.practicalproject.R;

import java.util.ArrayList;


/**
 * Created by Precious on 2/28/2018.
 */

public class college_detail_dataAdapter extends ArrayAdapter<college_detail_data> {


    public college_detail_dataAdapter(Context context, ArrayList<college_detail_data> resource) {
        super(context, 0, resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listview = convertView;

        if(listview ==  null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.college_detail_populate, parent, false);
        }


        final college_detail_data currentOne = getItem(position);

        TextView course_title =  (TextView) listview.findViewById(R.id.college_detail_program);
        course_title.setText(currentOne.getProgram());



        return listview;
    }


}
