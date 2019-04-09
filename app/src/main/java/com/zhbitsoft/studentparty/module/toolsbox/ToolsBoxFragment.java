package com.zhbitsoft.studentparty.module.toolsbox;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsBoxFragment extends Fragment {

    public final int UPDATE_NOTI = 1;
    public final int UPDATE_NEWS = 2;
    public final int UPDATE_IMG=3;
    private Student student = Student.getStudent();
    private View view;
    private ImageView select;
    private ImageView note;
    private ImageView scan;
    private TextView notiMore;
    private TextView newsMore;
    private TextView notiTitle;
    private TextView notiContent;

    private ImageView newsImg;
    private TextView newsTitle;
    private LinearLayout noti;
    private LinearLayout ne;
    private String msg;
    private Notification notification = new Notification();
    private News news = new News();

    private Bitmap bitmap;
    public ToolsBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tools_box, container, false);
        init();
        even();
        return view;
    }

    private void init() {
        select = view.findViewById(R.id.mySelect);
        note = view.findViewById(R.id.myNote);
        scan = view.findViewById(R.id.myScan);
        notiMore = view.findViewById(R.id.notiMore);
        newsMore = view.findViewById(R.id.newsMore);
        notiTitle = view.findViewById(R.id.title);
        notiContent = view.findViewById(R.id.content);
        noti = view.findViewById(R.id.notification);
        ne = view.findViewById(R.id.news);
        newsTitle = view.findViewById(R.id.news_title);
        newsImg = view.findViewById(R.id.news_img);
    }

    private void even() {
        getNotiRequest(student.getStudentId());
        getNewsRequest();
        select.setOnClickListener(new imageOnclickListener());
        notiMore.setOnClickListener(new imageOnclickListener());
        noti.setOnClickListener(new imageOnclickListener());
        ne.setOnClickListener(new imageOnclickListener());
        newsMore.setOnClickListener(new imageOnclickListener());
    }

    class imageOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mySelect:
                    Intent i = new Intent(getActivity(), getGrade.class);
                    startActivity(i);
                    break;
                case R.id.myNote:
                    break;
                case R.id.myScan:
                    break;
                case R.id.newsMore:
                    Intent it = new Intent(getActivity(),GetNews.class);
                    startActivity(it);
                    break;
                case R.id.notiMore:
                    Intent i4 = new Intent(getActivity(), GetNotification.class);
                    startActivity(i4);
                    break;
                case R.id.news:
                    if (news.time == null && news.url == null) {
                        msg = "暂无资讯";
                        showToast();
                    } else {
                        Intent intent = new Intent(getActivity(), NewsDetail.class);
                        intent.putExtra("news", news);
                        startActivity(intent);
                    }
                    break;
                case R.id.notification:
                    if (notification.time == null && notification.receiver == null) {
                        msg = "暂无通知";
                        showToast();
                    } else {
                        Intent intent = new Intent(getActivity(), NotiDetail.class);
                        intent.putExtra("notify", notification);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public void getNotiRequest(final String studentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/GetNotiServlet";
                    RequestBody requestBody = new FormBody.Builder()
                            .add("studentId", studentId)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<Notification>>() {
                        }.getType();
                        Gson gson = new Gson();
                        if (gson.fromJson(responseData, listType) != null) {
                            List<Notification> webList = gson.fromJson(responseData, listType);
                            notification = webList.get(0);
                            webList.clear();
                        } else {
                            notification.setTitle(" ");
                            notification.setContent("暂无通知");
                        }
                        Message message = new Message();
                        message.what = UPDATE_NOTI;
                        handler.sendMessage(message);
                    } else {
                        throw new IOException("Unexpected code" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void getNewsRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/GetNewsServlet";
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<News>>() {
                        }.getType();
                        Gson gson = new Gson();
                        if (gson.fromJson(responseData, listType) != null) {
                            List<News> webList = gson.fromJson(responseData, listType);
                            news = webList.get(0);
                            webList.clear();
                        } else {
                            news.setTitle(" ");
                            news.setImgPath(" ");
                        }
                        Message message = new Message();
                        message.what = UPDATE_NEWS;
                        handler.sendMessage(message);
                    } else {
                        throw new IOException("Unexpected code" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_NOTI:
                    notiTitle.setText(notification.getTitle());
                    notiContent.setText(notification.getContent().replace("\\r", "").replace("\\n", "").replace("\\t", ""));
                    break;
                case UPDATE_NEWS:
                    newsTitle.setText(news.title);
                    setPicBitmap(news.imgPath);
                    break;
                case UPDATE_IMG:
                    newsImg.setImageBitmap(bitmap);
                default:
                    break;
            }
        }
    };

    private void showToast() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setPicBitmap(final String pic_url) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) new URL(pic_url).openConnection();

                   conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    Message message = new Message();
                    message.what = UPDATE_IMG;
                    handler.sendMessage(message);
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
