package com.example.precious.practicalproject.DataHolder;

/**
 * Created by Precious on 3/21/2018.
 */

public class checkedItemHolder {
    public String course_Link;
    public String course_Code;

    public checkedItemHolder(String course_Link, String course_Code) {
        this.course_Link = course_Link;
        this.course_Code = course_Code;
    }

    public String getCourse_Link() {
        return course_Link;
    }

    public String getCourse_Code() {
        return course_Code;
    }
}
