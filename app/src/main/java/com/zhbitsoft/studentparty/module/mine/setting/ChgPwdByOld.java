package com.zhbitsoft.studentparty.module.mine.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

import static java.security.AccessController.getContext;

public class ChgPwdByOld extends AppCompatActivity {

    private Student student = Student.getStudent();
    private LinearLayout next;
    private EditText oldpwd;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chg_pwd_by_old);

        init();
        event();
    }
    private void init(){
        oldpwd = findViewById(R.id.oldpwd);
        next = findViewById(R.id.by_pho_next);
        back = findViewById(R.id.to_back);
    }
    private void event(){
        next.setOnClickListener(new NextClickListener());
        back.setOnClickListener(new NextClickListener());
    }
    class NextClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.by_pho_next:
                    if(oldpwd.getText().toString().equals(student.getPassword())){
                        Intent i = new Intent(ChgPwdByOld.this,InputNewPwd.class);
                        startActivity(i);
                    }else {
                        oldpwd.setText(null);
                        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(oldpwd.getWindowToken(), 0);
                        Toast.makeText(getApplicationContext(), "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.to_back:
                    finish();;
                    break;
                default:
                    break;
            }
        }
    }
}
