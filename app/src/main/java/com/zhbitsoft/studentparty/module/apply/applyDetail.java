package com.zhbitsoft.studentparty.module.apply;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

public class applyDetail extends AppCompatActivity {

    private ApplyItem applyItem = new ApplyItem();
    private ImageView back;
    private TextView type;
    private TextView applyer;
    private TextView applyTime;
    private TextView backTime;
    private TextView content;
    private ImageView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_detail);
        applyItem=(ApplyItem) getIntent().getSerializableExtra("apply");
        back=findViewById(R.id.back);
        type=findViewById(R.id.type);
        applyer=findViewById(R.id.applyer);
        applyTime=findViewById(R.id.applyTime);
        backTime=findViewById(R.id.backTime);
        content=findViewById(R.id.content);
        state=findViewById(R.id.state);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type.setText(applyItem.getTitle());
        applyer.setText(applyItem.getApplyer());
        applyTime.setText(applyItem.getApplyTime().toString());
        backTime.setText(applyItem.getBackTime().toString());
        content.setText(applyItem.getContent());
        if (applyItem.handle==1){
            if (applyItem.decision==1){
                state.setImageResource(R.drawable.passed);
            }else {
                state.setImageResource(R.drawable.refusal);
            }
        }
    }
}
