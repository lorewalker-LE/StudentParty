package com.zhbitsoft.studentparty.module.login.bean;

public class User {
    private String studentId;
    private String password;

    public User(){

    }

    public User(String studentId, String password){
        this.studentId = studentId;
        this.password = password;
    }

    public String studentId(){
        return studentId;
    }

    public void setStudentId(String studentId){
        this.studentId = studentId;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return "StudentId{" +
                "StudentId='" + studentId + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }
}
