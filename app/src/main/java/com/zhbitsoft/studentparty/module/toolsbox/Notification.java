package com.zhbitsoft.studentparty.module.toolsbox;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Notification implements Serializable {
    String title;
    String content;
    Timestamp time;
    String author;
    String receiver;

    public Notification(String title,String content){
       // time=Timestamp.valueOf(str);
        this.title=title;
        this.content=content;
    }
    public Notification(){}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
