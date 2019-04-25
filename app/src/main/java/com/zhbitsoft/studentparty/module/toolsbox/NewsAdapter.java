package com.zhbitsoft.studentparty.module.toolsbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.widget.LoadingDialog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final int SHOW=1;
    private final int HIDE=2;
    private List<News> mNewsList;
    private Context context;
    private LinearLayout layout;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView title;
        ImageView img;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.news);
            time=itemView.findViewById(R.id.time);
            title=itemView.findViewById(R.id.title);
            img=itemView.findViewById(R.id.img);
        }
    }
    public NewsAdapter(Context context,List<News> newsList){
        this.context=context;
        mNewsList=newsList;
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
                Intent intent = new Intent(context,NewsDetail.class);
                intent.putExtra("news",news);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int i) {
        if (mNewsList!=null&&mNewsList.size()>0) {
            News news = mNewsList.get(i);
            if (news.time != null) {
                viewHolder.time.setText(news.time.toString());
            } else {
                viewHolder.time.setText("");
            }
            viewHolder.title.setText(news.title);
            if (news.imgPath != null && !"".equals(news.imgPath.trim())) {
                setPicBitmap(viewHolder.img, news.imgPath);
            }
        }else {
            viewHolder.time.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setPicBitmap(final ImageView ivPic, final String pic_url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(pic_url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivPic.setImageBitmap(bitmap);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
