package com.zhbitsoft.studentparty.module.login.dao;

public class StudentDaoImpl implements StudentDao {

    @Override
    public boolean isExistsUser(String studentId) {
        return false;
    }

    @Override
    public boolean isLoginSuccess(String studentId, String password) {
        return false;
    }
}
