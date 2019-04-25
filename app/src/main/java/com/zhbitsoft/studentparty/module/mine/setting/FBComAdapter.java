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
            new ItemName("1.怎么修改密码","点击‘我的’-》‘我的设置’-》账号与安全，选择修改密码的方式，旧密码验证"),
            new ItemName("2.怎么请假","点击‘工具箱’-》‘请假条呀’-》根据选项进行填写，点击提交"),
            new ItemName("2.怎么申请场地","点击‘工具箱’-》‘场地申请’-》根据选项进行填写，点击提交"),
            new ItemName("4.怎么查询成绩","点击‘工具’选择上方的成绩查询，选择相应学期，即可查询成绩"),
            new ItemName("5.怎么更改绑定的手机号","通过联系管理员或者在其他反馈中向管理员提交此问题的申请"),
            new ItemName("6.软件使用过程中遇到网络问题","如有遇到网络异常提醒，请检查自己的网络，如遇到提交失败，请联系管理员咨询处理"),
            new ItemName("7.个人相关信息发现有误","请联系管理员进行更改"),
            new ItemName("8.怎么选择课程表的周数","点击‘课程表’，找到上方的周数，点击周数，再选择要查看的周"),
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
