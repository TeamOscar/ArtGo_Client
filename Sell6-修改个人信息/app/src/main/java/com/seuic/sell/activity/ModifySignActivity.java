package com.seuic.sell.activity;

import android.app.Activity;
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

public class ModifySignActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_modifyitem;
    private ImageView iv_left,iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sign);
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
                modifySign();
                break;
        }
    }

    private void modifySign(){
        if(TextUtils.isEmpty(et_modifyitem.getText().toString().trim())){
            Toast.makeText(this, "请输入签名", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User();
        user.setId( Common.mUser.getId());
        user.setName(Common.mUser.getName());
        user.setAddress(Common.mUser.getAddress());
        user.setSign(et_modifyitem.getText().toString().trim());
        user.setEmail(Common.mUser.getEmail());
        user.setPassword(Common.mUser.getPassword());
        user.setPhone(Common.mUser.getPhone());


        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("修改签名中...");
        BusinessService.getInstance().modifySign(user);
    }

    @Override
    public void modifySignCallback(boolean b) {
        super.modifySignCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            Common.mUser.setSign(et_modifyitem.getText().toString().trim());
            finish();
        }else{
            Toast.makeText(this,"修改失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
