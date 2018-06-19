package com.example.precious.practicalproject.DataHolder;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Precious on 1/26/2018.
 */


public class NewsHolder implements Parcelable {

    private String newTitle;
    private String Uri;
    private int level;
    private String date;

    public  NewsHolder(String newTitle, String Uri, String date, int level){
        this.newTitle = newTitle;
        this.Uri =  Uri;
        this.level =  level;
        this.date =  date;
    }

    public  NewsHolder(String newTitle, String date){
        this.newTitle = newTitle;
        this.date =  date;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getDate() {
        return date;
    }

    public String getUri() {
        return Uri;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private NewsHolder(Parcel in) {
        newTitle = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newTitle);

    }

    public static final Parcelable.Creator<NewsHolder> CREATOR = new Parcelable.Creator<NewsHolder>() {
        public NewsHolder createFromParcel(Parcel in) {
            return new NewsHolder(in);
        }

        public NewsHolder[] newArray(int size) {
            return new NewsHolder[size];
        }
    };
}
