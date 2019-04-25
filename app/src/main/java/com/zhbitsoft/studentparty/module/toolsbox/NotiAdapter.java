package com.zhbitsoft.studentparty.module.toolsbox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;

import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {

    private List<Notification> mNotiList;
    private Context context;
    private LinearLayout layout;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        TextView time;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.notification);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }
    public NotiAdapter(Context context,List<Notification> notiList){
        this.context=context;
        mNotiList = notiList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (!"".equals(mNotiList.get(position).title.trim())&&!"暂无通知".equals(mNotiList.get(position).content.trim())) {
                    Notification notification = mNotiList.get(position);
                    Intent intent = new Intent(context, NotiDetail.class);
                    intent.putExtra("notify", notification);
                    context.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (mNotiList!=null&&mNotiList.size()>0) {
            Notification notification = mNotiList.get(i);
            viewHolder.title.setText(notification.title);
            viewHolder.content.setText(notification.content.replace("\\r", " ").replace("\\n", "").replace("\\t", ""));
            if (notification.time != null) {
                viewHolder.time.setText(notification.time.toString());
            } else {
                viewHolder.time.setText("");
            }
        }else {
            viewHolder.title.setText(" ");
            viewHolder.content.setText(" ");
            viewHolder.time.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mNotiList.size();
    }
}
