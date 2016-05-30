package com.seuic.sell.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.entity.User;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_name,et_address,et_sign,et_email,et_phone,et_password,et_comfirmpass;
    private ImageView iv_left,iv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        et_name = (EditText)findViewById(R.id.et_name);
        et_address = (EditText)findViewById(R.id.et_address);
        et_sign = (EditText)findViewById(R.id.et_sign);
        et_email = (EditText)findViewById(R.id.et_email);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_password = (EditText)findViewById(R.id.et_password);
        et_comfirmpass = (EditText)findViewById(R.id.et_comfirmpass);

        iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);

        iv_right = (ImageView)findViewById(R.id.iv_right);
        iv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                register();
                break;
        }
    }



    private void register(){
        if(TextUtils.isEmpty(et_name.getText().toString().trim())){
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_address.getText().toString().trim())){
            Toast.makeText(this, "请输入常驻地", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_sign.getText().toString().trim())){
            Toast.makeText(this, "请输入签名", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_email.getText().toString().trim())){
            Toast.makeText(this, "请输入电子邮箱", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isEmail(et_email.getText().toString().trim())){
            Toast.makeText(this, "请输入正确的电子邮箱", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_phone.getText().toString().trim())){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isMobileNO(et_phone.getText().toString().trim())){
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_password.getText().toString().trim())){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_comfirmpass.getText().toString().trim())){
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_LONG).show();
            return;
        }
        if(et_password.getText().toString().trim().length()<6){
            Toast.makeText(this, "密码不能少于六位", Toast.LENGTH_LONG).show();
            return;
        }

        if(!et_comfirmpass.getText().toString().trim().equals(et_password.getText().toString().trim())){
            Toast.makeText(this, "请输入确认密码和密码不符", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User();
        user.setId( UUID.randomUUID().toString());
        user.setName(et_name.getText().toString().trim());
        user.setAddress(et_address.getText().toString().trim());
        user.setSign(et_sign.getText().toString().trim());
        user.setEmail(et_email.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());
        user.setPhone(et_phone.getText().toString().trim());

        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("注册中...");
        BusinessService.getInstance().addUser(user);

    }

    public static boolean isEmail(String email){
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    public void addUserCallback(boolean b) {
        super.addUserCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            startActivity(new Intent(this,SucceedRegisterActivity.class));
            finish();
        }else{
            Toast.makeText(this,"注册失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
