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
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.User;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;

import java.util.UUID;

public class ModifyNameActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_modifyitem;
    private ImageView iv_left,iv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);
        initView();
    }

    private void initView(){
        et_modifyitem = (EditText)findViewById(R.id.et_modifyitem);

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
                modifyName();
                break;
        }
    }

    private void modifyName(){
        if(TextUtils.isEmpty(et_modifyitem.getText().toString().trim())){
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User();
        user.setId( Common.mUser.getId());
        user.setName(et_modifyitem.getText().toString().trim());
        user.setAddress(Common.mUser.getAddress());
        user.setSign(Common.mUser.getSign());
        user.setEmail(Common.mUser.getEmail());
        user.setPassword(Common.mUser.getPassword());
        user.setPhone(Common.mUser.getPhone());


        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("修改用户名中...");
        BusinessService.getInstance().modifyName(user);
    }

    @Override
    public void modifyNameCallback(boolean b) {
        super.modifyNameCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            Common.mUser.setName(et_modifyitem.getText().toString().trim());
            finish();
        }else{
            Toast.makeText(this,"修改失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
