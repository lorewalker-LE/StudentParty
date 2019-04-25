package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.login.LoginActivity;
import com.zhbitsoft.studentparty.utils.SharedPreferencesUtils;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class SettingsActivity extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;

    private LinearLayout acc_safe;
    private LinearLayout notify;
    private LinearLayout feedback;
    private LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        acc_safe = findViewById(R.id.acc_safe);
        feedback = findViewById(R.id.help_feedback);
        logout = findViewById(R.id.logout);
        titleOnlyBack=(TitleOnlyBack)findViewById(R.id.title_set);
        titleOnlyBack.setTitle("我的设置");
        acc_safe.setOnClickListener(new SetOnClickListener());
        feedback.setOnClickListener(new SetOnClickListener());
        logout.setOnClickListener(new SetOnClickListener());
    }

    class SetOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.acc_safe:
                    Intent t = new Intent(SettingsActivity.this,AccountSafe.class);
                    startActivity(t);
                    break;
                case R.id.help_feedback:
                    Intent i = new Intent(SettingsActivity.this,HelpFeedback.class);
                    startActivity(i);
                    break;
                case R.id.logout:
                    SharedPreferencesUtils helper = new SharedPreferencesUtils(getBaseContext(), "setting");
                    helper.putValues( new SharedPreferencesUtils.ContentValue("autoLogin", false));
                    Intent intent = new Intent(SettingsActivity.this,LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
