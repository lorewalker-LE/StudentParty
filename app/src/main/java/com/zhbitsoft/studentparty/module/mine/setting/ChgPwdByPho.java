package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class ChgPwdByPho extends AppCompatActivity {
    private TitleOnlyBack titleOnlyBack;
    private LinearLayout next;
    private TextView phone;
    private Student student = Student.getStudent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chg_pwd_by_pho);
        init();
        event();
    }
    private void init(){
        titleOnlyBack = findViewById(R.id.title_chgbypho);
        next = findViewById(R.id.by_pho_next);
        phone = findViewById(R.id.phoneNum);
    }
    private void event(){
        titleOnlyBack.setTitle("手机验证");
        phone.setText(student.getStuTel());
        next.setOnClickListener(new NextClickListener());
    }
    class NextClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.by_pho_next:

                    break;
                default:
                    break;
            }
        }
    }
}
