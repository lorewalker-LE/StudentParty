package com.zhbitsoft.studentparty.module.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String studentId;
    private String studentName;
    private String password;
    private String sex;
    private String professional;
    private String classId;
    private String collegeId;
    private String stuTel;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getStuTel() {
        return stuTel;
    }

    public void setStuTel(String stuTel) {
        this.stuTel = stuTel;
    }

    public  Student(){}

    public Student(String studentId,String studentName,String password,String sex,String professional,String classId,String collegeId,String stuTel){
        this.studentId=studentId;
        this.studentName=studentName;
        this.password=password;
        this.sex=sex;
        this.professional=professional;
        this.classId=classId;
        this.collegeId=collegeId;
        this.stuTel=stuTel;
    }

    protected Student(Parcel in){
        studentId = in.readString();
        studentName = in.readString();
        password = in.readString();
        sex = in.readString();
        professional = in.readString();
        classId = in.readString();;
        collegeId = in.readString();
        stuTel = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString());
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStudentId());
        dest.writeString(getStudentName());
        dest.writeString(getPassword());
        dest.writeString(getSex());
        dest.writeString(getProfessional());
        dest.writeString(getClassId());
        dest.writeString(getCollegeId());
        dest.writeString(getStuTel());
    }
}
