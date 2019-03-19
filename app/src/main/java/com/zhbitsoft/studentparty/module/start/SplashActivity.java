package com.zhbitsoft.studentparty.module.start;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.config.Const;
import com.zhbitsoft.studentparty.main.Main2Activity;
import com.zhbitsoft.studentparty.module.login.LoginActivity;
import com.zhbitsoft.studentparty.module.start.welcome.WelcomeActivity;
import com.zhbitsoft.studentparty.utils.SPUtils;
import com.zhbitsoft.studentparty.utils.StateBarTranslucentUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity implements SplashView {

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        mPresenter = new SplashPresenterImpl(this);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtils.get(this, Const.FIRST_OPEN, false);
        mPresenter.isFirstOpen(isFirstOpen);
    }
    @Override
    public void initContentView() {

    }

    //=======================动态权限的申请===========================================================<
    @Override
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startWelcomeGuideActivity() {
        WelcomeActivity.start(this);
        finish();
    }


    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
//                .setPositiveButton(R.string.imtrue, (dialog, button) -> request.proceed())
//                .setNegativeButton(R.string.cancel, (dialog, button) -> request.cancel())
                .setPositiveButton(R.string.imtrue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show()
        ;
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied(){
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("退出应用",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(SplashActivity.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "不再询问授权权限！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //=======================动态权限的申请===========================================================>
    @Override
    public void startHomeActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
