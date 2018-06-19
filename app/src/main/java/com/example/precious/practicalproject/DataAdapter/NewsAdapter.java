package com.example.precious.practicalproject.DataAdapter;

import android.app.Activity;

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
import android.widget.TextView;

import com.example.precious.practicalproject.DataHolder.NewsHolder;
import com.example.precious.practicalproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Precious on 1/26/2018.
 */




public class NewsAdapter extends ArrayAdapter<NewsHolder> {

    Context context;

    public NewsAdapter(Context context, ArrayList<NewsHolder> resource) {
        super(context, 0,  resource);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView =  convertView;

        if(listView ==  null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.populatenews, parent, false);
        }

        final  NewsHolder news = getItem(position);

        TextView newsTitle =  (TextView) listView.findViewById(R.id.news_title);
        newsTitle.setText(news.getNewTitle());
        newsTitle.setTextColor(Color.WHITE);

        TextView datPublished =  (TextView) listView.findViewById(R.id.news_datePublished);
        datPublished.setText(news.getDate());
        datPublished.setTextColor(Color.WHITE);
        TextView publishOn = (TextView) listView.findViewById(R.id.publish_on);
        publishOn.setTextColor(Color.WHITE);

//        TextView publishedDate = (TextView) listView.findViewById(R.id.date_published);
//        publishedDate.setText(news.getDate());


        Animation slideLeft = AnimationUtils.loadAnimation(context, R.anim.listview_slide_left);

        listView.startAnimation(slideLeft);
        return listView;
    }
}
