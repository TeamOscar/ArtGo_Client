package com.seuic.sell.activity;

import android.app.Activity;
import android.os.Bundle;

import com.seuic.sell.HttpService.IServiceCallback;
import com.seuic.sell.MyApplication;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.entity.Buy;
import com.seuic.sell.entity.Order;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class BaseActivity extends Activity implements IServiceCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加Activity到堆栈
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusinessService.getInstance().setServiceCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从栈中移除该Activity
        MyApplication.getInstance().finishActivity(this);
    }

    @Override
    public void addUserCallback(boolean b) {

    }

    @Override
    public void modifyNameCallback(boolean b) {

    }

    @Override
    public void modifyAddressCallback(boolean b) {

    }

    @Override
    public void modifySignCallback(boolean b) {

    }

    @Override
    public void findUserCallback(User mUser) {

    }

    @Override
    public void findUserByIdCallback(User mUser) {

    }

    @Override
    public void addSellCallback(boolean b) {

    }

    @Override
    public void findSellCallback(List<Sell> list) {

    }

    @Override
    public void findAllSellCallback(List<Sell> list) {

    }

    @Override
    public void addBuyCallback(boolean b) {

    }

    @Override
    public void findByUserIdBuyCallback(List<Order> list) {

    }

    @Override
    public void findBuyCallback(Buy mBuy) {

    }

    @Override
    public void updateReceiveFalgCallback(boolean b) {

    }

    @Override
    public void updateSendFalgCallback(boolean b) {

    }
}
