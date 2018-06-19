package com.example.precious.practicalproject.DataHolder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Precious on 1/16/2018.
 */

public class ContentHolder  implements Parcelable{
    private String course_title;
    private String course_link;
    private String course_code;
    boolean isChecked = false;

    public ContentHolder(String course_code, String course_title,String course_link ) {
        this.course_title = course_title;
        this.course_code = course_code;
        this.course_link = course_link;
    }

    public String getCourse_title() {
        return course_title;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getCourse_link() {
        return course_link;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private ContentHolder (Parcel in) {
        course_title = in.readString();
        course_code = in.readString();
        course_title = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(course_title);
        dest.writeString(course_code);
        dest.writeString(course_title);

    }

    public static final Parcelable.Creator<ContentHolder> CREATOR = new Parcelable.Creator<ContentHolder>() {
        public ContentHolder createFromParcel(Parcel in) {
            return new ContentHolder(in);
        }

        public ContentHolder[] newArray(int size) {
            return new ContentHolder[size];
        }
    };
}
