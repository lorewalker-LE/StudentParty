package com.zhbitsoft.studentparty.module.noteparty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhbitsoft.studentparty.MySubject;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.timetable.Subject;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotePartyFragment extends Fragment {

    private View view;
    private Button btn;
    public NotePartyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_note_party, container, false);
        btn=view.findViewById(R.id.show);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             List<MySubject> list = DataSupport.findAll(MySubject.class);
                Iterator<MySubject> iterator = list.iterator();
                while (iterator.hasNext()){
                    MySubject mySubject = iterator.next();
                    Log.d("qwe00",mySubject.getRoom());
                    /*
                   List<Subject> subjects = new ArrayList<>();
                   if (list.size()!=0&&list!=null){
                       Iterator<MySubject> iterator = list.iterator();
                       while (iterator.hasNext()){
                           MySubject mySubject = new MySubject();
                           Subject subject = new Subject();
                           subject.setName(mySubject.getName());
                           subject.setRoom(mySubject.getRoom());
                           subject.setTeacher(mySubject.getTeacher());
                           List<Integer> week = new ArrayList<>();
                           for (int i=mySubject.getStartweek();i<=mySubject.getEndweek();i++) {
                               week.add(i);
                           }
                           subject.setWeekList(week);
                           subject.setStart(mySubject.getStart());
                           subject.setStep(mySubject.getStep());
                           subject.setDay(mySubject.getDay1());
                           subjects.add(subject);
                           if (mySubject.getDay2()!=0){
                               Subject subject2 = new Subject();
                               subject2.setName(mySubject.getName());
                               subject2.setRoom(mySubject.getRoom());
                               subject2.setTeacher(mySubject.getTeacher());
                               subject2.setWeekList(week);
                               subject2.setStart(mySubject.getStart());
                               subject2.setStep(mySubject.getStep());
                               subject2.setDay(mySubject.getDay2());
                               subjects.add(subject2);

                       } }*/

              }
            //   DataSupport.deleteAll(MySubject.class,"");

            }
        });
    }
}
