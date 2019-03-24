package com.zhbitsoft.studentparty.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

public class TitleOnlyBack extends LinearLayout {

    private FitHeightTextView title;
    public TitleOnlyBack(Context context) {
        super(context,null);
    }
    public TitleOnlyBack(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_onlyback,this);
        ImageView back = (ImageView)findViewById(R.id.to_back);
        title=(FitHeightTextView) findViewById(R.id.to_title);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();;
            }
        });
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
}
