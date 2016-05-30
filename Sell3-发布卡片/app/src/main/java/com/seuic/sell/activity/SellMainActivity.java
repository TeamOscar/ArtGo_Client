package com.seuic.sell.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.seuic.sell.Image.ImageDownloader;
import com.seuic.sell.Image.OnImageDownload;
import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;

import java.util.List;

public class SellMainActivity extends BaseActivity implements View.OnClickListener{
    private ListView lv_sellitem;
    private ImageView iv_home,iv_liaotian,iv_set;
    private List<Sell> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("加载中...");
        BusinessService.getInstance().findAllSell();
    }

    @Override
    public void findAllSellCallback(List<Sell> list) {
        super.findAllSellCallback(list);
        MyDialog.getInstance().dismiss();
        if(list!=null&&list.size()>0){
            //说明下载到数据
            this.list = list;
        }
        lv_sellitem.setAdapter(new MyAdapter(this));
    }

    private void initView(){
        lv_sellitem = (ListView)findViewById(R.id.lv_sellitem);

        iv_home = (ImageView)findViewById(R.id.iv_home);
        iv_home.setOnClickListener(this);

        iv_liaotian = (ImageView)findViewById(R.id.iv_liaotian);
        iv_liaotian.setOnClickListener(this);

        iv_set = (ImageView)findViewById(R.id.iv_set);
        iv_set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.iv_home:
                showPopupWindow(v);
                break;
            case R.id.iv_liaotian:
                startActivity(new Intent(this,MessageActivity.class));
                break;
            case R.id.iv_set:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }
    PopupWindow popupWindow;
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popupwindow, null);
        // 设置按钮的点击事件
        RelativeLayout button = (RelativeLayout) contentView.findViewById(R.id.rl_sell);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellMainActivity.this,SellItemActivity.class));
                popupWindow.dismiss();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        popupWindow= new PopupWindow(contentView,
                dm.widthPixels,360);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.blackTransparent5));

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());

    }

    ImageDownloader mDownloader;

    public final class ViewHolder{
        public ImageView img;
        public TextView sign;
        public TextView address;
        public TextView time;
        public Button priceBtn;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list==null?0:list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.selllistitem, null);
                holder.img = (ImageView)convertView.findViewById(R.id.iv_sellitem);
                holder.sign = (TextView)convertView.findViewById(R.id.tv_sign);
                holder.address = (TextView)convertView.findViewById(R.id.tv_address);
                holder.time = (TextView)convertView.findViewById(R.id.tv_time);
                holder.priceBtn = (Button)convertView.findViewById(R.id.bt_price);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }
            holder.img.setTag(list.get(position).getPath());
            holder.sign.setText(list.get(position).getDescription());
            holder.address.setText(list.get(position).getAddress());
            holder.time.setText(list.get(position).getTime());
            holder.priceBtn.setText(list.get(position).getPrice()+"");
            if (mDownloader == null) {
                mDownloader = new ImageDownloader();
            }
            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
            holder.img.setImageResource(R.mipmap.ic_launcher);

            if (mDownloader != null) {
                //异步下载图片
                mDownloader.imageDownload((Common.uploadPicUrl+list.get(position).getPath()).replace('\\','/'), holder.img, "/sell",SellMainActivity.this, new OnImageDownload() {
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

            holder.priceBtn.setOnClickListener(new View.OnClickListener() {
                int index = position;

                @Override
                public void onClick(View v) {
                    if(FastClick.isFastClick()){
                        return;
                    }
                    Intent intent = new Intent(SellMainActivity.this,SellActivity.class);
                    intent.putExtra("sell", JSON.toJSONString(list.get(index)));
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }
}
