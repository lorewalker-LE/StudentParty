package com.zhbitsoft.studentparty.module.timetable;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhbitsoft.studentparty.MySubject;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.utils.HttpUtil;
import com.zhbitsoft.studentparty.widget.LoadingDialog;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;
;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {

    private Student student = Student.getStudent();
    private TimetableView mTimetableView;
    private WeekView mWeekView;
    private ImageView moreButton;
    private LinearLayout layout;
    TextView titleTextView;

    //记录切换的周次，不一定是当前周
    int target = -1;
    int curweek;

    private View view;
    private String msg;
    private LoadingDialog mLoadingDialog;
    private List<MySubject>listlocal = new ArrayList<>();
    private List<Subject> subjects =new ArrayList<>();
    public TimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_time_table, container, false);
        listlocal = DataSupport.findAll(MySubject.class);
        init(view);
        return view;
    }
    public void init(View view){
        moreButton=view.findViewById(R.id.id_more);
        titleTextView=view.findViewById(R.id.id_title);
        layout=view.findViewById(R.id.id_layout);
        try {
            curweek=getCurWeek();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initTimetableView(view);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listlocal.clear();
                showLoading();
                getSubjectRequest(student.getStudentId());
                listlocal = DataSupport.findAll(MySubject.class);
                initTimetableView(view);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeekView.isShowing()) {
                    mWeekView.isShow(false);
                    titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
                    int cur = mTimetableView.curWeek();
                    mTimetableView.onDateBuildListener()
                            .onUpdateDate(cur, cur);
                    mTimetableView.changeWeekOnly(cur);
                } else {
                    mWeekView.isShow(true);
                    titleTextView.setTextColor(getResources().getColor(R.color.app_red));
                }
            }
        });
    }

    public  void getSubjectRequest(final String studentId) {
        //请求地址
        String url = "http://192.168.43.65:8080/studentParty/stuTimeTableServlet";
       // setBtnClickable(false);//点击登录后，设置登录按钮不可点击状态
        RequestBody requestBody = new FormBody.Builder()
                .add("studentId",studentId)
                .build();
        HttpUtil.sendOkHttpRequestPost(requestBody,url, new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //setLoadLayout(true);
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    msg = "连接超时";
                 //   showToast();
                   // setBtnClickable(true);
                    hideLoading();
                }
                if (e instanceof ConnectException) {
                    msg = "连接异常";
                    showToast();
                    //setBtnClickable(true);
                }
               hideLoading();//隐藏加载框
                e.printStackTrace();//打印异常原因+异常名称+出现异常的位置
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    String responseData = response.body().string();
                    Log.d("response", responseData);
                    Type listType = new TypeToken<List<MySubject>>(){}.getType();
                    Gson gson = new Gson();
                    List<MySubject>listWeb = new ArrayList<>();
                    listWeb = gson.fromJson(responseData,listType);
                     if(listWeb!=null&&!listWeb.isEmpty()) {
                    Iterator<MySubject> iter = listWeb.iterator();
                    while (iter.hasNext()){
                        MySubject mySubject = iter.next();
                        List<MySubject> rep = DataSupport.select("name").where("name = ?",mySubject.getName()).find(MySubject.class);
                        if (rep.size()==0) {
                            mySubject.save();
                        }
                    }
                }
                   // setLoadLayout(false);
                   // setBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                   hideLoading();//隐藏加载框
            }
        });
    }


    public void showToast() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoading() {
        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(getActivity(), "正在加载", false);
        }
        mLoadingDialog.show();
    }


    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }

    public List<Subject> getSubject(List<MySubject> list){
        List<Subject> subjects = new ArrayList<>();
        if (list.size()!=0&&list!=null){
            Iterator<MySubject> iterator = list.iterator();
            while (iterator.hasNext()){
                MySubject mySubject = iterator.next();
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
                }
            }
        }
        return subjects;
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView(View view) {
        //获取控件
        mWeekView = view.findViewById(R.id.id_weekview);
        mWeekView.hideLeftLayout();
        mTimetableView = view.findViewById(R.id.id_timetableView);
       subjects=getSubject(listlocal);
        //设置周次选择属性
        mWeekView.source(subjects)
                .curWeek(curweek)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        //onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.source(subjects)
                .curWeek(curweek)
                .curTerm("大三下学期")//waitfor
                .maxSlideItem(15)
                .monthWidthDp(30)
                //透明度
                //日期栏0.1f、侧边栏0.1f，周次选择栏0.6f
                //透明度范围为0->1，0为全透明，1为不透明
//                .alpha(0.1f, 0.1f, 0.6f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(new ISchedule.OnSpaceItemClickListener() {
                    @Override
                    public void onSpaceItemClick(int day, int start) {
                    }

                    @Override
                    public void onInit(LinearLayout flagLayout, int monthWidth, int itemWidth, int itemHeight, int marTop, int marLeft) {

                    }

                })
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                    }
                })
                .showView();
    }
    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    public void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }
    /**
     * 显示内容
     *getActivity().getApplicationContext()
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        StringBuilder a1 = new StringBuilder("课程名:  ");
        StringBuilder a2 = new StringBuilder("老师:  ");
        StringBuilder a3 = new StringBuilder("教室:  ");
        StringBuilder a4 = new StringBuilder("节数:  ");
        for (Schedule bean : beans) {
           a1.append(bean.getName());
            a2.append(bean.getTeacher());
           a3.append(bean.getRoom());
           a4.append(bean.getStep());
        }
        final String cl = a1.toString();
        final String t1 = a2.toString();
        final String cla = a3.toString();
        final String st = a4.toString();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("课程信息");builder.setItems(new String[]{cl,t1,cla,st},null);
                builder.setPositiveButton("确定",null);
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
    }

    /**
     * 显示WeekView
     */
    protected void showWeekView() {
        mWeekView.isShow(true);
    }

    /**
     * 隐藏WeekView
     */
    protected void hideWeekView() {
        mWeekView.isShow(false);
    }

    public int getCurWeek() throws ParseException {
        int curWeek=20;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//获取当前时间
        Date datenow = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month>=3&&month<7){
            StringBuilder or = new StringBuilder(String.valueOf(year));
            or.append("-").append("03").append("-").append("10");
            Date datestart = simpleDateFormat.parse(or.toString());
            long daysBetween=((datenow.getTime())-datestart.getTime())/(60*60*24*1000);
            curWeek=(int)Math.ceil(daysBetween/7);
            Log.d("x",String.valueOf(curWeek));

        }
        if (month>=9||month-1==0){
            StringBuilder or = new StringBuilder();
            if (month-1==0) {
                 or.append(String.valueOf(year-1));
            }else {
                or.append(String.valueOf(year));
            }
            or.append("-").append("09").append("-").append("10");
            Date datestart = simpleDateFormat.parse(or.toString());
            long daysBetween=((datenow.getTime())-datestart.getTime())/(60*60*24*1000);
            curWeek=(int)Math.ceil(daysBetween/7);
        }
        return curWeek;

    }
}