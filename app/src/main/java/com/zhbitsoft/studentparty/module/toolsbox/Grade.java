package com.zhbitsoft.studentparty.module.toolsbox;

public class Grade {
    String courseName;
    int grade;

    public Grade(){}
    public Grade(String courseName,int grade){
        this.courseName=courseName;
        this.grade=grade;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
