package com.example.precious.practicalproject.DataHolder;

/**
 * Created by Precious on 3/17/2018.
 */

public class VerifyMatric {

    private int Matric;
    private String Name;
    private String Programme;
    private String College;
    private String Level;

    public VerifyMatric(int matric, String name, String programme, String college, String level) {
        Matric = matric;
        Name = name;
        Programme = programme;
        College = college;
        Level = level;
    }

    public int getMatric() {
        return Matric;
    }

    public String getName() {
        return Name;
    }

    public String getProgramme() {
        return Programme;
    }

    public String getCollege() {
        return College;
    }

    public String getLevel() {
        return Level;
    }
}
