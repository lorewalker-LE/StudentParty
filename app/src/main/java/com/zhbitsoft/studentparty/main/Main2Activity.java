package com.zhbitsoft.studentparty.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.User;
import com.zhbitsoft.studentparty.module.mine.MineFragment;
import com.zhbitsoft.studentparty.module.noteparty.NotePartyFragment;
import com.zhbitsoft.studentparty.module.timetable.TimeTableFragment;
import com.zhbitsoft.studentparty.module.toolsbox.ToolsBoxFragment;

public class Main2Activity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private User user = User.getUser();

    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new ToolsBoxFragment());
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_toolsbox:
                    transaction.replace(R.id.content,new ToolsBoxFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_timetable:
                    transaction.replace(R.id.content,new TimeTableFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_noteparty:
                    transaction.replace(R.id.content,new NotePartyFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_mine:
                    MineFragment mineFragment = new MineFragment();
                   // mineFragment.refresh(user.getStudentName(),user.getStudentId());
                    Log.d("sa",user.getStudentName());
                    transaction.replace(R.id.content,mineFragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setDefaultFragment();
    }

}
