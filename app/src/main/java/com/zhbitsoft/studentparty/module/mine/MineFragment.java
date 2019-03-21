package com.zhbitsoft.studentparty.module.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private View view;
    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

   // public void refresh(String studentName,String studentNum){
      //  TextView myName = (TextView) view.findViewById(R.id.myName);
      //  TextView myNum = (TextView) view.findViewById(R.id.myNum);
      //  myName.setText(studentName);
      //  myNum.setText(studentNum);
  //  }
}
