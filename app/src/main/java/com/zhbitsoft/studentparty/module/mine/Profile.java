package com.zhbitsoft.studentparty.module.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;


public class Profile extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    private  TextView stuName;
    private TextView stuSex;
    private TextView stuNum;
    private TextView stuTel;
    private TextView stuCol;
    private TextView stuMaj;
    private TextView stuCla;
    Student student = Student.getStudent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        event();
    }
    private void init(){
        titleOnlyBack = findViewById(R.id.title_profile);
        titleOnlyBack.setTitle("我的信息");
        stuName = findViewById(R.id.profile_name);
        stuSex = findViewById(R.id.profile_sex);
        stuNum = findViewById(R.id.profile_id);
        stuTel = findViewById(R.id.profile_pho);
        stuCol = findViewById(R.id.profile_col);
        stuMaj = findViewById(R.id.profile_maj);
        stuCla = findViewById(R.id.profile_cla);
    }
    private void event(){
        stuName.setText(student.getStudentName());
        stuSex.setText(student.getSex());
        stuNum.setText(student.getStudentId());
        stuTel.setText(student.getStuTel());
        stuCol.setText(student.getCollegeId());
        stuMaj.setText(student.getProfessional());
        stuCla.setText(student.getClassId());
    }
}
