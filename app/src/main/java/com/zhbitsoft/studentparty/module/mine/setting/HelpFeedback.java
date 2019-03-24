package com.zhbitsoft.studentparty.module.mine.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class HelpFeedback extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        titleOnlyBack = findViewById(R.id.title_feedback);
        titleOnlyBack.setTitle("帮助与反馈");
    }
}
