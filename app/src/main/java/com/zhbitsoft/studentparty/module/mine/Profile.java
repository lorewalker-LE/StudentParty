package com.zhbitsoft.studentparty.module.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class Profile extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        titleOnlyBack = findViewById(R.id.title_profile);
        titleOnlyBack.setTitle("我的信息");
    }
}
