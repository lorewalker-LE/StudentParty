package com.zhbitsoft.studentparty.module.login.present;

import android.content.Context;
import android.widget.CheckBox;

public interface LoginPresent {
     boolean firstLogin();
    boolean remenberPassword();
    boolean autoLogin();
    String getLocalName();
    String getLocalPassword();
    void loadUserName(String account);
    void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login,String password);

}
