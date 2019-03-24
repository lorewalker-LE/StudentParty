package com.zhbitsoft.studentparty.module.mine;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbitsoft.studentparty.BaseFragment;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.User;
import com.zhbitsoft.studentparty.module.mine.setting.SettingsActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    private User user = User.getUser();
    private View view;
    private  LinearLayout myMes;
    private  LinearLayout myCollect;
    private  LinearLayout myScan;
    private LinearLayout mySet;
    private  LinearLayout myabout;
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void initView(){
        initUserInfo();
    }

    private void initUserInfo(){
        TextView myName = view.findViewById(R.id.myName);
        TextView myNum = view.findViewById(R.id.myNum);
        if(myName==null){Log.e("sad","error");}
        String userName = user.getStudentName();
        String userNum  = user.getStudentId();
        myName.setText(userName);
        myNum.setText(userNum);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
         myMes = (LinearLayout) view.findViewById(R.id.myMes);
         myCollect = (LinearLayout) view.findViewById(R.id.myCollect);
         myScan = (LinearLayout) view.findViewById(R.id.myscan);
         mySet = (LinearLayout) view.findViewById(R.id.myset);
         myabout = (LinearLayout) view.findViewById(R.id.myabout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myMes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Profile.class);
                startActivity(intent);
            }
        });
        myabout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AboutMe.class);
                startActivity(intent);
            }
        });
        mySet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
