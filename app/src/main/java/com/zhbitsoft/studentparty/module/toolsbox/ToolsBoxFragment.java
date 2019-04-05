package com.zhbitsoft.studentparty.module.toolsbox;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsBoxFragment extends Fragment {

    private View view;
    private ImageView select;
    private ImageView note;
    private ImageView scan;
    private TextView notiMore;
    private TextView newsMore;
    public ToolsBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_tools_box, container, false);
        init();
        even();
        return view;
    }

    private void init(){
        select = view.findViewById(R.id.mySelect);
        note = view.findViewById(R.id.myNote);
        scan = view.findViewById(R.id.myScan);
        notiMore = view.findViewById(R.id.notiMore);
        newsMore = view.findViewById(R.id.newsMore);

    }
    private void even(){
        select.setOnClickListener(new imageOnclickListener());
    }

    class imageOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mySelect:
                    Intent i = new Intent(getActivity(),getGrade.class);
                    startActivity(i);
                    break;
                case R.id.myNote:
                    break;
                case R.id.myScan:
                    break;
                default:
                        break;
            }
        }
    }
}
