package com.zhbitsoft.studentparty.module.apply;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplyItem  implements Serializable {
    String title;
    String content;
    String applyer;
    String handler;
    Timestamp applyTime;
    Timestamp backTime;
    String feedback=null;
    int handle;
    int decision;

    public ApplyItem(){}
    public ApplyItem(String title,String content,int handle,int decision){
        this.title = title;
        this.content = content;
        this.handle = handle;
        this.decision = decision;
    }
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }


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

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public Timestamp getBackTime() {
        return backTime;
    }

    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

}
