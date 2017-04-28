package com.lqr.wechat.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.lqr.wechat.R;
import com.lqr.wechat.model.cache.UserCache;
import com.lqr.wechat.ui.base.BaseActivity;
import com.lqr.wechat.ui.base.BasePresenter;
import com.lqr.wechat.util.UIUtils;

import butterknife.Bind;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * @创建者 CSDN_LQR
 * @描述 微信闪屏页
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.rlButton)
    RelativeLayout mRlButton;
    @Bind(R.id.btnLogin)
    Button mBtnLogin;
    @Bind(R.id.btnRegister)
    Button mBtnRegister;

    @Override
    public void init() {
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        //电话通讯录
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
                        //位置
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.CAMERA,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_SETTINGS
                )
                .request();
        /**
         * 判断缓存是否为空
         */
        if (!TextUtils.isEmpty(UserCache.getToken())) {
            Intent intent = new Intent(this, MainActivity.class);
            //清空栈内所有的activity启动新的activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            jumpToActivity(intent);
            finish();
        }
    }

    @Override
    public void initView() {
        /**
            设置状态栏的颜色或者是否是沉浸式
         */
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.black));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        mRlButton.startAnimation(alphaAnimation);//设置布局启动动画
    }

    @Override
    public void initListener() {
        /**
         * 登录
         */
        mBtnLogin.setOnClickListener(v -> {
            jumpToActivity(LoginActivity.class);
            finish();
        });
        /**
         * 注册
         */
        mBtnRegister.setOnClickListener(v -> {
            jumpToActivity(RegisterActivity.class);
            finish();
        });
    }

    /**
     * 不启用Mvp模式所以传null
     * @return
     */
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 布局ID
     * @return
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }
}
