package com.zhbitsoft.studentparty.module.apply;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotePartyFragment extends Fragment {
    private Student student = Student.getStudent();
    private LinearLayout layout;
    private View view;
    public NotePartyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_note_party, container, false);
        layout=view.findViewById(R.id.qingjia);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),apply.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
