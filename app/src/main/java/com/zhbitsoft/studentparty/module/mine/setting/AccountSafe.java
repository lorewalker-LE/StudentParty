package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

public class AccountSafe extends AppCompatActivity {

    private TitleOnlyBack titleOnlyBack;
    //private LinearLayout byPhone;
    private LinearLayout byOld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        init();
        event();
    }
    private void init(){
        titleOnlyBack = findViewById(R.id.title_accsafe);
        //byPhone = findViewById(R.id.chgbyphone);
        byOld = findViewById(R.id.chgbyold);
    }
    private void event(){
        titleOnlyBack.setTitle("账号与安全");
      //  byPhone.setOnClickListener(new CopyClickListener());
        byOld.setOnClickListener(new CopyClickListener());
    }
    class CopyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.chgbyold:
                    Intent t = new Intent(AccountSafe.this,ChgPwdByOld.class);
                    startActivity(t);
                    break;
                default:
                    break;
            }
        }
    }
}
