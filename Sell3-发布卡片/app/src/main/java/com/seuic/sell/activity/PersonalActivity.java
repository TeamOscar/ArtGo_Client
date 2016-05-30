package com.seuic.sell.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.seuic.sell.Image.ImageDownloader;
import com.seuic.sell.Image.OnImageDownload;
import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends BaseActivity {
    private TextView tv_name,tv_changzhu,tv_qianming;
    private ListView lv_maiitem;
    ImageDownloader mDownloader;

    class listItem{
        listItem(String path1, String path2) {
            this.path1 = path1;
            this.path2 = path2;
        }

        public String path1;
        public String path2;
    }

    List<listItem> list = new ArrayList<listItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        initData();
    }

    private void initData() {
        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("加载中...");
        BusinessService.getInstance().findSell(Common.mUser.getId());
    }


    @Override
    public void findSellCallback(List<Sell> list) {
        super.findSellCallback(list);
        MyDialog.getInstance().dismiss();
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i=i+2){
                if(i+1>=list.size()){
                    this.list.add(new listItem(list.get(i).getPath(),""));
                    break;
                }
                this.list.add(new listItem(list.get(i).getPath(),list.get(i+1).getPath()));

            }
        }
        lv_maiitem.setAdapter(new MyAdapter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_name.setText(Common.mUser.getName());
        tv_changzhu.setText(Common.mUser.getAddress());
        tv_qianming.setText(Common.mUser.getSign());
    }

    private void initView(){
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_changzhu = (TextView)findViewById(R.id.tv_changzhu);
        tv_qianming = (TextView)findViewById(R.id.tv_qianming);

        lv_maiitem = (ListView)findViewById(R.id.lv_maiitem);

    }


    public final class ViewHolder{
        public ImageView img1;
        public ImageView img2;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.personallistitem, null);
                holder.img1 = (ImageView)convertView.findViewById(R.id.iv_1);
                holder.img2 = (ImageView)convertView.findViewById(R.id.iv_2);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }

            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
            holder.img1.setImageResource(R.mipmap.ic_launcher);
            holder.img2.setImageResource(R.mipmap.ic_launcher);
            if (mDownloader == null) {
                mDownloader = new ImageDownloader();
            }
            if (mDownloader != null) {
                //异步下载图片
                mDownloader.imageDownload((Common.uploadPicUrl+list.get(position).path1).replace('\\','/'), holder.img1, "/sell",PersonalActivity.this, new OnImageDownload() {
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
                holder.img2.setVisibility(View.INVISIBLE);
                if(!TextUtils.isEmpty(list.get(position).path2)){
                    holder.img2.setVisibility(View.VISIBLE);
                    mDownloader.imageDownload((Common.uploadPicUrl+list.get(position).path2).replace('\\','/'), holder.img2, "/sell",PersonalActivity.this, new OnImageDownload() {
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

            return convertView;
        }

    }

}
