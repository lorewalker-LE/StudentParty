package com.zhbitsoft.studentparty.module.login.view;

public interface LoginView {

    void setPasswordVisibility();
    //显示进度条
    void showLoading();
    //隐藏进度条
    void hideLoading();
    void initData();
    void setTextNameAndPassword();
    void setTextName( );
    void initViews();
    void event();
}
