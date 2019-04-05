package com.zhbitsoft.studentparty.module.toolsbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

import java.util.ArrayList;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private List<Grade> mGradeList ;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView grade;
        public ViewHolder(View view) {
            super(view);
            courseName = view.findViewById(R.id.courseName);
            grade = view.findViewById(R.id.grade);
        }
    }
    public GradeAdapter(List<Grade> gradeList){
        mGradeList=gradeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grade_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Grade grade = mGradeList.get(i);
        viewHolder.courseName.setText(grade.getCourseName());
        viewHolder.grade.setText(String.valueOf(grade.getGrade()));
    }


    @Override
    public int getItemCount() {
        return mGradeList.size();
    }
}
