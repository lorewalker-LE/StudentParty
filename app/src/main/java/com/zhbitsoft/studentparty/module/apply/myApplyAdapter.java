package com.zhbitsoft.studentparty.module.apply;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhbitsoft.studentparty.MySubject;
import com.zhbitsoft.studentparty.R;

import java.util.List;

public class myApplyAdapter extends RecyclerView.Adapter<myApplyAdapter.ViewHolder> {

    private List<ApplyItem> myApplyList ;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView type;
        TextView reason;
        ImageView status;
        LinearLayout layout;
        public ViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.type);
            reason = view.findViewById(R.id.reason);
            status = view.findViewById(R.id.status);
            layout = view.findViewById(R.id.layout);
        }
    }
    public myApplyAdapter(Context context, List<ApplyItem> ySubjectList){
        this.context=context;
        myApplyList=ySubjectList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apply_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
       holder.layout.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View v) {
               int position = holder.getAdapterPosition();
                   ApplyItem applyItem = myApplyList.get(position);
                   Intent intent = new Intent(context, applyDetail.class);
                   intent.putExtra("apply", applyItem);
                   context.startActivity(intent);
           }
       });
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        if (myApplyList!=null&&myApplyList.size()>0){
            ApplyItem myApply = myApplyList.get(i);
            viewHolder.type.setText(myApply.getTitle());
            viewHolder.reason.setText(myApply.getContent());
            if (myApply.getHandle()==1){
                if(myApply.getDecision()==1){
                    viewHolder.status.setImageResource(R.drawable.pass);
                }else {
                    viewHolder.status.setImageResource(R.drawable.deny);
                }
            }else {
                viewHolder.status.setImageResource(R.drawable.status);
            }
        }else {
            viewHolder.reason.setText("暂无请假信息");
        }


    }

    @Override
    public int getItemCount() {
        return myApplyList.size();
    }

}
