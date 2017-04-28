package com.lqr.wechat.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lqr.wechat.R;
import com.lqr.wechat.ui.base.BaseActivity;
import com.lqr.wechat.ui.presenter.RegisterAtPresenter;
import com.lqr.wechat.ui.view.IRegisterAtView;
import com.lqr.wechat.util.UIUtils;

import butterknife.Bind;

public class RegisterActivity extends BaseActivity<IRegisterAtView, RegisterAtPresenter> implements IRegisterAtView {

    @Bind(R.id.etNick)
    EditText mEtNick;
    @Bind(R.id.vLineNick)
    View mVLineNick;

    @Bind(R.id.etPhone)
    EditText mEtPhone;
    @Bind(R.id.vLinePhone)
    View mVLinePhone;

    @Bind(R.id.etPwd)
    EditText mEtPwd;
    @Bind(R.id.ivSeePwd)
    ImageView mIvSeePwd;
    @Bind(R.id.vLinePwd)
    View mVLinePwd;

    @Bind(R.id.etVerifyCode)
    EditText mEtVerifyCode;
    @Bind(R.id.btnSendCode)
    Button mBtnSendCode;
    @Bind(R.id.vLineVertifyCode)
    View mVLineVertifyCode;

    @Bind(R.id.btnRegister)
    Button mBtnRegister;
    /**
     * EidtText监听
     */
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBtnRegister.setEnabled(canRegister());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void initListener() {
        mEtNick.addTextChangedListener(watcher);
        mEtPwd.addTextChangedListener(watcher);
        mEtPhone.addTextChangedListener(watcher);
        mEtVerifyCode.addTextChangedListener(watcher);

        /**
         * 通过监听事件判断EditText下面的基线颜色
         */
        mEtNick.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVLineNick.setBackgroundColor(UIUtils.getColor(R.color.green0));
            } else {
                mVLineNick.setBackgroundColor(UIUtils.getColor(R.color.line));
            }
        });
        mEtPwd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVLinePwd.setBackgroundColor(UIUtils.getColor(R.color.green0));
            } else {
                mVLinePwd.setBackgroundColor(UIUtils.getColor(R.color.line));
            }
        });
        mEtPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVLinePhone.setBackgroundColor(UIUtils.getColor(R.color.green0));
            } else {
                mVLinePhone.setBackgroundColor(UIUtils.getColor(R.color.line));
            }
        });
        mEtVerifyCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVLineVertifyCode.setBackgroundColor(UIUtils.getColor(R.color.green0));
            } else {
                mVLineVertifyCode.setBackgroundColor(UIUtils.getColor(R.color.line));
            }
        });

        /**
         * 查看密码点击事件
         */
        mIvSeePwd.setOnClickListener(v -> {

            if (mEtPwd.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

            mEtPwd.setSelection(mEtPwd.getText().toString().trim().length());
        });

        /**
         * 发送验证的事件 MVP实现
         */
        mBtnSendCode.setOnClickListener(v -> {
            if (mBtnSendCode.isEnabled()) {
                mPresenter.sendCode();
            }
        });
        /**
         * 注册的事件 MVP实现
         */
        mBtnRegister.setOnClickListener(v -> {
            mPresenter.register();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    /**
     * 是否符合长度要求
     * @return
     */
    private boolean canRegister() {
        int nickNameLength = mEtNick.getText().toString().trim().length();
        int pwdLength = mEtPwd.getText().toString().trim().length();
        int phoneLength = mEtPhone.getText().toString().trim().length();
        int codeLength = mEtVerifyCode.getText().toString().trim().length();
        if (nickNameLength > 0 && pwdLength > 0 && phoneLength > 0 && codeLength > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected RegisterAtPresenter createPresenter() {
        return new RegisterAtPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public EditText getEtNickName() {
        return mEtNick;
    }

    @Override
    public EditText getEtPhone() {
        return mEtPhone;
    }

    @Override
    public EditText getEtPwd() {
        return mEtPwd;
    }

    @Override
    public EditText getEtVerifyCode() {
        return mEtVerifyCode;
    }

    @Override
    public Button getBtnSendCode() {
        return mBtnSendCode;
    }
}
