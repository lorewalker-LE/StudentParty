package com.zhbitsoft.studentparty.module.login.dao;

public interface StudentDao {
    boolean isExistsUser(String studentId);
    boolean isLoginSuccess(String studentId,String password);
}
