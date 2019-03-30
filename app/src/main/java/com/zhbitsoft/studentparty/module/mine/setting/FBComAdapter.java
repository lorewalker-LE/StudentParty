package com.zhbitsoft.studentparty.module.mine.setting;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.anim.ExpandableViewHoldersUtil;

import java.util.Random;

public class FBComAdapter extends RecyclerView.Adapter<FBComAdapter.FBComViewHolder> {

    static final Random random = new Random(System.currentTimeMillis());

    public static class ItemName{
        final String name;
        final String content;
        final int color;;
        public ItemName(String name,String content){
            this.name = name;
            this.content=content;
            //this.color = Color.rgb(128 + random.nextInt(128), 128 + random.nextInt(128), 128 + random.nextInt(128));;
            this.color = Color.rgb(240,240,240);
        }
    }
    final ItemName[] contacts = new ItemName[]{
            new ItemName("1.怎么修改密码","点击‘我的’-》‘我的设置’-》账号与安全，选择修改密码的方式，通过手机验证或通过旧密码验证"),
            new ItemName("2.怎么关闭或打开通知","点击‘我的’-》‘我的设置’-》通知与提醒，选择要打开或关闭的选项"),
            new ItemName("3.怎么签到","点击‘我的’-》‘扫一扫’扫取老师所展示的二维码即可进行签到"),
            new ItemName("4.怎么查询成绩","点击‘工具’选择上方的成绩查询，选择相应学期，即可查询成绩"),
            new ItemName("5.怎么更改绑定的手机号","通过联系管理员或者在其他反馈中向管理员提交此问题的申请"),
            new ItemName("6.软件使用过程中遇到网络问题","如有遇到网络异常提醒，请检查自己的网络，如遇到提交失败，请联系管理员咨询处理"),
            new ItemName("7.个人相关信息发现有误","请联系管理员进行更改"),
            new ItemName("8.怎么选择课程表的周数","点击‘课程表’，找到上方的周数，点击周数，再选择要查看的周"),
            new ItemName("9.我为什么要上传自己的笔记","乐于助人是，是一种朴实的中国传统美德。每个人都有遇到困难的时候，最需要的是别人给予的帮助。如果人人都献出一点爱，将不再会看到别人得不到帮助时焦急的脸庞。")
    };
    ExpandableViewHoldersUtil.KeepOneH<FBComViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<FBComViewHolder>();

    @Override
    public FBComViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new FBComViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_common_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FBComViewHolder holder, int pos) {
        final ItemName contact = contacts[pos];
        holder.bind(pos, contact.name,contact.content, contact.color);
    }

    @Override
    public int getItemCount() {
        return contacts.length;
    }

    public class FBComViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {

        public final TextView contactNameTV;
        public final TextView infos;

        public FBComViewHolder(ViewGroup itemView) {
            super(itemView);
            contactNameTV = ((TextView) itemView.findViewById(R.id.contactName));
            infos = ((TextView) itemView.findViewById(R.id.infos));

            itemView.setOnClickListener(this);
        }

        public void bind(int pos, String name,String content, int color) {
            contactNameTV.setText(name);
            infos.setText(content);
            keepOne.bind(this, pos);

            itemView.setBackgroundColor(color);
        }

        @Override
        public void onClick(View v) {
            keepOne.toggle(this);
        }

        @Override
        public View getExpandView() {
            return infos;
        }
    }
}
