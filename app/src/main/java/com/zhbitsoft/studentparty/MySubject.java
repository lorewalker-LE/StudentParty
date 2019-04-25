package com.zhbitsoft.studentparty;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class MySubject extends DataSupport implements Serializable{
    private String name;
    private String room;
    private String teacher;
    private int startweek;
    private int endweek;
    private int start;
    private int step;
    private int day1;
    private int day2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getStartweek() {
        return startweek;
    }

    public void setStartweek(int startweek) {
        this.startweek = startweek;
    }

    public int getEndweek() {
        return endweek;
    }

    public void setEndweek(int endweek) {
        this.endweek = endweek;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }
}
