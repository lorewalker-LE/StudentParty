package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class HelpFeedback extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    private LinearLayout fb_change;
    private LinearLayout fb_bug;
    private LinearLayout fb_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        titleOnlyBack = findViewById(R.id.title_feedback);
        titleOnlyBack.setTitle("帮助与反馈");
        init();
        event();
    }
    private void init(){
        fb_change = findViewById(R.id.feedback_colchange);
        fb_bug = findViewById(R.id.feedback_bug);
        fb_other = findViewById(R.id.feedback_other);
    }
    private void event(){
        fb_change.setOnClickListener(new NextClickListener());
        fb_bug.setOnClickListener(new NextClickListener());
        fb_other.setOnClickListener(new NextClickListener());
    }
    class NextClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.feedback_colchange:
                    Intent i = new Intent(HelpFeedback.this,feedback_change.class);
                    startActivity(i);
                    break;
                case R.id.feedback_bug:
                    Intent t = new Intent(HelpFeedback.this,feedback_bug.class);
                    startActivity(t);
                    break;
                case R.id.feedback_other:
                    Intent n = new Intent(HelpFeedback.this,feedback_other.class);
                    startActivity(n);
                    break;
                default:
                    break;
            }
        }
    }
}
