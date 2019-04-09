package com.zhbitsoft.studentparty.module.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class Collection extends AppCompatActivity {

    private TextView zixun;
    private TextView tongzhi;
    private TextView biji;
    private TitleOnlyBack titleOnlyBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        init();
    }
    private void init(){
        zixun=findViewById(R.id.zixun);
        tongzhi=findViewById(R.id.tongzhi);
        biji=findViewById(R.id.biji);
        titleOnlyBack=findViewById(R.id.title);
        titleOnlyBack.setTitle("我的收藏");
    }
    private void event(){
        zixun.setOnClickListener(new SelOnclick());
        tongzhi.setOnClickListener(new SelOnclick());
        biji.setOnClickListener(new SelOnclick());
    }

    class SelOnclick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.zixun:
                    break;
                case R.id.tongzhi:
                    break;
                case R.id.biji:
                    break;
                default:
                    break;
            }
        }
    }
}
