package com.zhbitsoft.studentparty.module.mine;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class AboutMe extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        titleOnlyBack=(TitleOnlyBack)findViewById(R.id.title_ob);
        titleOnlyBack.setTitle("关于我");
        ImageView copyWechat = (ImageView)findViewById(R.id.copywechat);
        ImageView copyEmail = (ImageView) findViewById(R.id.copyemail);
        copyWechat.setOnClickListener(new CopyClickListener());
        copyEmail.setOnClickListener(new CopyClickListener());
    }
    class CopyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.copywechat:
                    ClipboardManager wechat = (ClipboardManager) AboutMe.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    wechat.setText("13192265963");
                    Toast.makeText(AboutMe.this,"已复制",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.copyemail:
                    ClipboardManager email = (ClipboardManager) AboutMe.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    email.setText("308850906@qq.com");
                    Toast.makeText(AboutMe.this,"已复制",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
