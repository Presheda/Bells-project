package com.example.precious.practicalproject.DataAdapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.precious.practicalproject.DataHolder.ContentHolder;
import com.example.precious.practicalproject.DataHolder.checkedItemHolder;
import com.example.precious.practicalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Precious on 1/16/2018.
 */

public class ContentAdapter extends ArrayAdapter<ContentHolder> {

    Context context;

    ArrayList<ContentHolder> resourceData;
    private boolean[] checkBoxState = null;
    private HashMap<ContentHolder, Boolean> checkedForCountry = new HashMap<>();

    public ContentAdapter(Context context, ArrayList<ContentHolder> resource) {
        super(context, 0,  resource);
        this.resourceData = resource;
        this.context = context;
    }


    @Override
    public int getCount() {
        return resourceData.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Nullable
    @Override
    public ContentHolder getItem(int position) {
        return null;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View listview = convertView;

        final ViewHolder holder;

        if(listview ==  null){

            listview = LayoutInflater.from(getContext()).inflate(R.layout.populatedownload, parent,
                    false);
            holder = new ViewHolder();
            holder.course_code = (TextView) listview.findViewById(R.id.course_code);
            holder.course_title = (TextView) listview.findViewById(R.id.course_title);
            holder.checkBox = (CheckBox) listview.findViewById(R.id.ischeckbox);
            listview.setTag(holder);
        }


        else {
            holder = (ViewHolder) listview.getTag();
        }

        final ContentHolder contentHolder = resourceData.get(position);
        checkBoxState = new boolean[resourceData.size()];

        holder.course_title.setText(contentHolder.getCourse_title());
        holder.course_title.setTextColor(Color.WHITE);
        holder.course_code.setText(contentHolder.getCourse_code());
        holder.course_code.setTextColor(Color.WHITE);

        if(checkBoxState != null){
            holder.checkBox.setChecked(checkBoxState[position]);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState[position] = true;
                    ischecked(position,true);

                }

                else {
                    checkBoxState[position] = false;
                    ischecked(position, false);

                }
            }
        });


        /**if country is in checkedForCountry then set the checkBox to true **/
        if (checkedForCountry.get(contentHolder) != null) {
            holder.checkBox.setChecked(checkedForCountry.get(contentHolder));
        }

        /**Set tag to all checkBox**/
        holder.checkBox.setTag(contentHolder);


        if(holder.checkBox.isChecked()){
            contentHolder.setChecked(true);
        }

        else {
            contentHolder.setChecked(false);
        }


        Animation slideLeft = AnimationUtils.loadAnimation(context, R.anim.listview_slide_left);

        listview.startAnimation(slideLeft);

        return listview;
    }



    public void ischecked(int position,boolean flag )
    {
        checkedForCountry.put(this.resourceData.get(position), flag);
    }

    public ArrayList<checkedItemHolder> getSelectedCountry(){
        ArrayList<checkedItemHolder> List = new ArrayList<>();
        for (Map.Entry<ContentHolder, Boolean> pair : checkedForCountry.entrySet()) {
            if(pair.getValue()) {
                String title = pair.getKey().getCourse_link();
                String code = pair.getKey().getCourse_code();

                List.add(new checkedItemHolder(title, code));

            }
        }
        return List;
    }

    private class ViewHolder
    {
        TextView course_code, course_title;
        CheckBox checkBox;


    }

}
