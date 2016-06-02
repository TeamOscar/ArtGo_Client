package com.seuic.sell.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.seuic.sell.Image.ImageDownloader;
import com.seuic.sell.Image.OnImageDownload;
import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Buy;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.entity.User;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;
import com.seuic.sell.view.MyRadioGroup;

import java.util.UUID;

public class SellActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_name,tv_phone,tv_showprice;
    private ImageView iv_zuoping,iv_left;
    private MyRadioGroup rg_pay;
    private RadioButton rb_baidu,rb_weixin,rb_zhifubao;
    private Button bt_ok;
    ImageDownloader mDownloader;
    Sell mSell;
    User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        initView();
        initData();
    }

    private void initData() {
        String str = getIntent().getStringExtra("sell");
        mSell = JSON.parseObject(str,Sell.class);
        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("加载中...");
        BusinessService.getInstance().findUserById(mSell.getUserId());

        if (mDownloader == null) {
            mDownloader = new ImageDownloader();
        }
        //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
        iv_zuoping.setImageResource(R.mipmap.ic_launcher);

        if (mDownloader != null) {
            //异步下载图片
            mDownloader.imageDownload((Common.uploadPicUrl+mSell.getPath()).replace('\\','/'), iv_zuoping, "/sell",SellActivity.this, new OnImageDownload() {
                @Override
                public void onDownloadSucc(Bitmap bitmap,
                                           String c_url,ImageView imageView) {
                    //ImageView imageView = (ImageView) lv_sellitem.findViewWithTag(c_url);
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                        imageView.setTag("");
                    }
                }
            });
        }
    }

    @Override
    public void findUserByIdCallback(User mUser) {
        super.findUserByIdCallback(mUser);
        MyDialog.getInstance().dismiss();
        if(mUser!=null){
            this.mUser = mUser;
            tv_name.setText(mUser.getName());
            tv_phone.setText(mUser.getPhone());
            tv_showprice.setText(mSell.getPrice()+"");

        }else{
            Toast.makeText(this,"加载失败！",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    private void initView(){
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_showprice = (TextView)findViewById(R.id.tv_showprice);

        iv_zuoping = (ImageView)findViewById(R.id.iv_zuoping);
        iv_left = (ImageView)findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);

        bt_ok = (Button)findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(this);


        rg_pay = (MyRadioGroup)findViewById(R.id.rg_pay);
        rg_pay.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {

            }
        });

        rb_baidu = (RadioButton)findViewById(R.id.rb_baidu);
        rb_weixin = (RadioButton)findViewById(R.id.rb_weixin);
        rb_zhifubao = (RadioButton)findViewById(R.id.rb_zhifubao);
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
            case R.id.bt_ok:
                submit();
                break;
        }
    }

    private void submit(){
        Buy mBuy = new Buy();
        mBuy.setId(UUID.randomUUID().toString());
        mBuy.setSellOrderId(mSell.getId());
        mBuy.setReceiveFalg(0);
        mBuy.setSendFalg(0);
        mBuy.setBuyId(Common.mUser.getId());
        mBuy.setSellId(mSell.getUserId());

        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("发布中...");
        BusinessService.getInstance().addBuy(mBuy);
    }

    @Override
    public void addBuyCallback(boolean b) {
        super.addBuyCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            finish();
            Toast.makeText(this,"购买成功！",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"购买失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
