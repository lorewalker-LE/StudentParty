package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.utils.HideKeybordUtils;
import com.zhbitsoft.studentparty.widget.LoadingDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class feedback_bug extends AppCompatActivity {

    public static final String DEGREE_BUG = "1";
    public static final int IMAGE_OPEN = 1;
    private ImageView back;
    private EditText content;
    private EditText wechat;
    private EditText phone;
    private LinearLayout submit;

    private String msg;
    private LoadingDialog mLoadingDialog;

    private String getpath[] = new String[3];
    private String pathImage;                //选择图片路径
    private Bitmap bmp;
    private GridView gridView;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_feedback_bug);
        init();
        event();
    }
    private void init(){
        content = (EditText)findViewById(R.id.content);
        wechat = (EditText)findViewById(R.id.wechat);
        phone = (EditText)findViewById(R.id.phone);
        back = findViewById(R.id.back);
        gridView = (GridView) findViewById(R.id.showPicGrid);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.gridview_addpic);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage",bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,imageItem,R.layout.griditem_addpic,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap)data);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(simpleAdapter);
        content = findViewById(R.id.content);
        wechat = findViewById(R.id.wechat);
        phone = findViewById(R.id.phone);
        submit = findViewById(R.id.submit);
    }
    private void event(){
        back.setOnClickListener(new NextClickListener());
        submit.setOnClickListener(new NextClickListener());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && imageItem.size() == 4){
                    Toast.makeText(feedback_bug.this,"图片数3张已满",Toast.LENGTH_SHORT).show();

                }else if (position == 0){
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                }else {
                   dialog(position);
                }
            }
        });
    }
    class NextClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.submit:
                    Log.v("te",content.getText().toString());
                    for (int i=0;i<3;i++){
                        if (getpath[i]!=null) {
                           Log.d("int",String.valueOf(i));
                            Log.v("show", getpath[i]);
                        }
                    }
                    if (getpath[0]==null){
                        Toast.makeText(feedback_bug.this,"请至少附上一张你的图片",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if ("".equals(content.getText().toString().trim())){
                        Toast.makeText(feedback_bug.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if ("".equals(wechat.getText().toString().trim())&&"".equals(phone.getText().toString().trim())){
                        Toast.makeText(feedback_bug.this,"联系方式不能为空",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    StringBuilder con = new StringBuilder();

                    if ("".equals(wechat.getText().toString().trim())&&!"".equals(phone.getText().toString().trim()))
                    {
                        if (phone.getText().length()==11) {
                            con.append("phone:").append(phone.getText().toString().trim());
                        }
                        else {
                            Toast.makeText(feedback_bug.this,"手机号码长度不对",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!"".equals(wechat.getText().toString().trim())&&"".equals(phone.getText().toString().trim()))
                    {
                        con.append("wechat:").append(wechat.getText().toString().trim());
                    }
                    if (!"".equals(wechat.getText().toString().trim())&&!"".equals(phone.getText().toString().trim()))
                    {
                        if (phone.getText().length()==11) {
                            con.append("wechat:").append(wechat.getText().toString().trim())
                               .append("&&phone:").append(phone.getText().toString().trim());
                        }
                        else {
                            Toast.makeText(feedback_bug.this,"手机号码长度不对",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    String contact = con.toString();
                    Log.d("contact", contact);
                    setBtnClickable(false);
                    showLoading();
                    fileUpload(getpath,content.getText().toString(),contact);
                    break;
                default:
                    break;
            }
        }
    }
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(feedback_bug.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                getpath[position-1]=null;
                for (int i=position-1;i<2;i++){
                    getpath[i]=getpath[i+1];
                    getpath[i+1]=null;
                    if (i==0){
                        getpath[i+1]=getpath[i+2];
                        getpath[i+2]=null;
                    }
                }
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
     //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
               for (int a=0;a<3;a++){
                    if (getpath[a]==null){
                        getpath[a]=pathImage;
                        break;
                    };
                }
            }
        }  //end if 打开图片
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)){
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }
    public void setBtnClickable(boolean clickable) {
        submit.setClickable(clickable);
    }
    public void fileUpload(String[] str,String content,String contact){
        List<File> files= new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
           if (str[i]!=null&&!"".equals(str[i])){
               File file1 = new File(str[i]);
               files.add(file1);

           }
        }
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
        if (files==null){
            Toast.makeText(getApplicationContext(), "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://192.168.43.65:8080/studentParty/FeedbackServlet";
        MultipartBody.Builder multiBuilder=new MultipartBody.Builder();
        for (File file : files) {
            multiBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file));
        }
       Map<String,String> params  = new HashMap<>();
       params.put("degree",DEGREE_BUG);
       params .put("content",content);
       params .put("contact",contact);
       if (params != null && !params.isEmpty()) {
           for (String key : params.keySet()) {
                multiBuilder.addPart(
                       Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                       RequestBody.create(null, params.get(key)));
          }
      }
        RequestBody multiBody=multiBuilder.build();
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new   Request.Builder().url(url).post(multiBody).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    msg = "连接超时";
                    showToast();
                    setBtnClickable(true);
                }
                if (e instanceof ConnectException) {
                    msg = "连接异常";
                    showToast();
                    setBtnClickable(true);
                }
                hideLoading();//隐藏加载框
                e.printStackTrace();//打印异常原因+异常名称+出现异常的位置
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                try {
                              String responseData = response.body().string();
                            Log.d("response", responseData);
                            JSONObject jsonObject = (JSONObject) new JSONObject(responseData).get("feedback");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")){
                                msg="提交成功";
                                showToast();
                                hideLoading();
                                finish();
                            }else {
                                msg="提交失败,请稍后再试";
                                showToast();
                                hideLoading();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            msg = "异常";
                            setBtnClickable(true);
                            showToast();
                            hideLoading();
                        }
                    }
        });
    }


    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(feedback_bug.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoading() {
        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(feedback_bug.this, "正在提交", false);
        }
        mLoadingDialog.show();
    }


    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                HideKeybordUtils.hideKeyboard(ev, view, feedback_bug.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onDestroy(){
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        super.onDestroy();
    }
}

