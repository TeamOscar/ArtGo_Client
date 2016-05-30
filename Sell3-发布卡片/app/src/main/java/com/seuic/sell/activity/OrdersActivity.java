package com.seuic.sell.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import com.seuic.sell.Image.ImageDownloader;
import com.seuic.sell.Image.OnImageDownload;
import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Order;
import com.seuic.sell.util.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {
    private ListView lv_listitem;
    List<Order> list = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initView();
        initData();
    }

    private void initData() {
        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("加载中...");
        BusinessService.getInstance().findByUserIdBuy(Common.mUser.getId());
    }

    @Override
    public void findByUserIdBuyCallback(List<Order> list) {
        super.findByUserIdBuyCallback(list);
        MyDialog.getInstance().dismiss();
        if(list!=null&&list.size()>0){
            //说明下载到数据
            this.list = list;
        }
        lv_listitem.setAdapter(new MyAdapter(this));
    }

    private void initView(){
        lv_listitem = (ListView)findViewById(R.id.lv_listitem);

    }

    ImageDownloader mDownloader;
    public final class ViewHolder{
        public TextView tv_orderStatus;
        public TextView tv_role;
        public TextView maijia;
        public ImageView iv_zuoping;
        public Button type;
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
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.orderitem, null);
                holder.tv_orderStatus = (TextView)convertView.findViewById(R.id.tv_orderStatus);
                holder.tv_role = (TextView)convertView.findViewById(R.id.tv_role);
                holder.maijia = (TextView)convertView.findViewById(R.id.maijia);
                holder.iv_zuoping = (ImageView)convertView.findViewById(R.id.iv_zuoping);
                holder.type = (Button)convertView.findViewById(R.id.type);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }
            final Order mOrder = list.get(position);
            if(mOrder.getBuyId().equals(Common.mUser.getId())){
                //当前用户是买家
                if(mOrder.getReceiveFalg()==0){
                    holder.tv_orderStatus.setText("订单状态：待收货");
                    holder.type.setText("收货");
                    holder.type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyDialog.getInstance().setContext(OrdersActivity.this);
                            MyDialog.getInstance().show();
                            MyDialog.getInstance().setDisplay("收货中...");
                            BusinessService.getInstance().updateReceiveFalg(mOrder.getId());
                        }
                    });

                }else{
                    holder.tv_orderStatus.setText("订单状态：已收货");
                    holder.type.setVisibility(View.INVISIBLE);
                }
                holder.tv_role.setText("订单角色：买家");
                holder.maijia.setText("卖家："+mOrder.getSellName());

            }else{
                //当前用户是卖家
                if(mOrder.getSendFalg()==0){
                    holder.tv_orderStatus.setText("订单状态：备货");
                    holder.type.setText("交货");
                    holder.type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyDialog.getInstance().setContext(OrdersActivity.this);
                            MyDialog.getInstance().show();
                            MyDialog.getInstance().setDisplay("交货中...");
                            BusinessService.getInstance().updateSendFalg(mOrder.getId());
                        }
                    });

                }else{
                    holder.tv_orderStatus.setText("订单状态：已发货");
                    holder.type.setVisibility(View.INVISIBLE);
                }
                holder.tv_role.setText("订单角色：卖家");
                holder.maijia.setText("买家："+mOrder.getBuyName());
            }


            if (mDownloader == null) {
                mDownloader = new ImageDownloader();
            }
            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
            holder.iv_zuoping.setImageResource(R.mipmap.ic_launcher);

            if (mDownloader != null) {
                //异步下载图片
                mDownloader.imageDownload((Common.uploadPicUrl+list.get(position).getPath()).replace('\\','/'), holder.iv_zuoping, "/sell",OrdersActivity.this, new OnImageDownload() {
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

            return convertView;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void updateReceiveFalgCallback(boolean b) {
        super.updateReceiveFalgCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            Toast.makeText(this, "收货成功！", Toast.LENGTH_LONG).show();
            initData();
        }else{
            Toast.makeText(this,"收货失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void updateSendFalgCallback(boolean b) {
        super.updateSendFalgCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            Toast.makeText(this, "发货成功！", Toast.LENGTH_LONG).show();
            initData();
        }else{
            Toast.makeText(this,"发货失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }
}
