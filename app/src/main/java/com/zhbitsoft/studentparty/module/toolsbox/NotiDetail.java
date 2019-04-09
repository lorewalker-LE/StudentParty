package com.zhbitsoft.studentparty.module.toolsbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

public class NotiDetail extends AppCompatActivity {

    private Notification notification = new Notification();
    private ImageView back;
    private TextView title;
    private TextView author;
    private TextView time;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_detail);
        notification=(Notification)getIntent().getSerializableExtra("notify");
        back=findViewById(R.id.back);
        title=findViewById(R.id.title);
        author=findViewById(R.id.author);
        time=findViewById(R.id.time);
        content=findViewById(R.id.content);
        title.setText(notification.getTitle());
        author.setText(notification.getAuthor());
        time.setText(notification.getTime().toString());
        content.setText(notification.getContent().replace("\\r","\r").replace("\\n","\n").replace("\\t","\t"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
