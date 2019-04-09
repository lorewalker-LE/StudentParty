package com.zhbitsoft.studentparty.module.toolsbox;

import java.io.Serializable;
import java.sql.Timestamp;

public class News implements Serializable {
    String title;
    String imgPath;
    String url;
    Timestamp time;

    public News(){}
    public News(String title,String imgPath,String time){
        this.title=title;
        this.imgPath=imgPath;
        this.time=Timestamp.valueOf(time);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
