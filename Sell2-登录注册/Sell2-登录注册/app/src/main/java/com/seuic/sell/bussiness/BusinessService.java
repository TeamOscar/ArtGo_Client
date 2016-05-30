package com.seuic.sell.bussiness;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.seuic.sell.HttpService.IServiceCallback;
import com.seuic.sell.HttpService.ServiceImp;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Buy;
import com.seuic.sell.entity.Order;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.entity.User;
import com.seuic.sell.util.BitmapUtils;
import com.seuic.sell.util.HttpClientUtils;
import com.seuic.sell.util.ThreadPoolUtils;

import org.apache.http.protocol.HttpService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BusinessService {
    private static ServiceImp mServiceImp = new ServiceImp();
    private static BusinessService mBusinessService;
    private IServiceCallback mServiceCallback;

    public static BusinessService getInstance(){
        if(mBusinessService==null){
            synchronized (BusinessService.class) {
                if(mBusinessService==null) {
                    mBusinessService = new BusinessService();
                }
            }
        }
        return mBusinessService;
    }

    public void setServiceCallback(IServiceCallback mServiceCallback){
        this.mServiceCallback = mServiceCallback;
    }

    /**
     * 注册
     * @param mUser
     */
    public void addUser(User mUser){
        ThreadPoolUtils.execute(new AddUserRunnable(mUser));
    }

    private class AddUserRunnable implements Runnable {
        User mUser;
        AddUserRunnable(User mUser){
            this.mUser = mUser;
        }
        @Override
        public void run() {
            final boolean b = mServiceImp.addUser(mUser);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.addUserCallback(b);
                }
            });
        }
    }

    /**
     * 修改用户名
     * @param mUser
     */
    public void modifyName(User mUser){
        ThreadPoolUtils.execute(new ModifyNameRunnable(mUser));
    }

    private class ModifyNameRunnable implements Runnable {
        User mUser;
        ModifyNameRunnable(User mUser){
            this.mUser = mUser;
        }
        @Override
        public void run() {
            final boolean b = mServiceImp.modifyName(mUser);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.modifyNameCallback(b);
                }
            });
        }
    }

    /**
     * 修改常驻地
     */
    public void modifyAddress(User mUser){
        ThreadPoolUtils.execute(new ModifyAddressRunnable(mUser));
    }

    private class ModifyAddressRunnable implements Runnable {
        User mUser;
        ModifyAddressRunnable(User mUser){
            this.mUser = mUser;
        }
        @Override
        public void run() {
            final boolean b = mServiceImp.modifyAddress(mUser);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.modifyAddressCallback(b);
                }
            });
        }
    }


    /**
     * 修改签名
     */
    public void modifySign(User mUser){
        ThreadPoolUtils.execute(new ModifySignRunnable(mUser));
    }

    private class ModifySignRunnable implements Runnable {
        User mUser;
        ModifySignRunnable(User mUser){
            this.mUser = mUser;
        }
        @Override
        public void run() {
            final boolean b = mServiceImp.modifySign(mUser);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.modifySignCallback(b);
                }
            });
        }
    }


    /**
     * 登录
     */
    public void findUser(String email,String password){
        ThreadPoolUtils.execute(new FindUserRunnable(email,password));
    }

    private class FindUserRunnable implements Runnable {
        String email,password;
        FindUserRunnable(String email,String password){
            this.email = email;
            this.password = password;
        }
        @Override
        public void run() {
            final User user = mServiceImp.findUser(email,password);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findUserCallback(user);
                }
            });
        }
    }

    /**
     * 通过人员id，查询人员
     */
    public void findUserById(String id){
        ThreadPoolUtils.execute(new findUserByIdRunnable(id));
    }

    private class findUserByIdRunnable implements Runnable {
        String id;
        findUserByIdRunnable(String id){
            this.id = id;
        }
        @Override
        public void run() {
            final User user = mServiceImp.findUserById(id);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findUserByIdCallback(user);
                }
            });
        }
    }



    /**
     * 查询指定人员的销售商品
     */
    public void findSell(String sellId){
        ThreadPoolUtils.execute(new findSellRunnable(sellId));
    }

    private class findSellRunnable implements Runnable {
        String sellId;
        findSellRunnable(String sellId){
            this.sellId = sellId;
        }
        @Override
        public void run() {
            final List<Sell> list = mServiceImp.findSell(sellId);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findSellCallback(list);
                }
            });
        }
    }

    /**
     * 查询所有人员的销售商品
     */
    public void findAllSell(){
        ThreadPoolUtils.execute(new findAllSellRunnable());
    }

    private class findAllSellRunnable implements Runnable {
        findAllSellRunnable(){
        }
        @Override
        public void run() {
            final List<Sell> list = mServiceImp.findAllSell();

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findAllSellCallback(list);
                }
            });
        }
    }

    /**
     * 查询所有人员的销售商品
     */
    public void addSell(Sell mSell,String path){
        ThreadPoolUtils.execute(new addSellRunnable(mSell,path));
    }

    private class addSellRunnable implements Runnable {
        Sell mSell;
        String path;
        addSellRunnable(Sell mSell,String path){
            this.mSell = mSell;
            this.path = path;
        }

        @Override
        public void run() {
            final boolean b = mServiceImp.addSell(mSell);
            if(b){
                //上传照片
                uploadPicture(path,mSell.getId());
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.addSellCallback(b);
                }
            });
        }
    }

    /**
     * 问题件拍照上传
     * @param path
     */
    public boolean uploadPicture(String path,String id){
        String newPath = null;
        try{
            String[] str = path.split("/");
            String pictureName = str[str.length-1];
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            Bitmap mBitmap = BitmapUtils.decodeSampledBitmapFromFile(path, 300, 200);
            newPath = directory+ File.separator+pictureName;
            if(mBitmap!=null&&save(mBitmap,newPath)){
                String result=null;
                for(int i = 0;i<2;i++){
                    result =  HttpClientUtils.post(newPath, Common.serviceUrl + "UpPicServlet?id="+id);
                    if(result!=null){
                        break;
                    }
                }
                if(result!=null&&result.equals("true")){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(newPath!=null){
                File file = new File(newPath);
                if(file.exists()){
                    file.delete();
                }
            }

        }
        return false;
    }

    public boolean save(Bitmap mBitmap,String path) {
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            return mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                fos = null;
            }
        }
        return false;

    }

    /**
     * 增加购买商品
     */
    public void addBuy(Buy mBuy){
        ThreadPoolUtils.execute(new addBuyRunnable(mBuy));
    }

    private class addBuyRunnable implements Runnable {
        Buy mBuy;
        addBuyRunnable(Buy mBuy){
            this.mBuy = mBuy;
        }

        @Override
        public void run() {
            final boolean b = mServiceImp.addBuy(mBuy);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.addBuyCallback(b);
                }
            });
        }
    }

    /**
     * 通过用户id查询 所有关联的购买记录
     */
    public void findByUserIdBuy(String id){
        ThreadPoolUtils.execute(new findByUserIdBuyRunnable(id));
    }

    private class findByUserIdBuyRunnable implements Runnable {
        String id;
        findByUserIdBuyRunnable(String id){
            this.id = id;
        }

        @Override
        public void run() {
            final List<Order> list = mServiceImp.findByUserIdBuy(id);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findByUserIdBuyCallback(list);
                }
            });
        }
    }

    /**
     * 通过用户id查询 所有关联的购买记录
     */
    public void findBuy(String id){
        ThreadPoolUtils.execute(new findBuyRunnable(id));
    }

    private class findBuyRunnable implements Runnable {
        String id;
        findBuyRunnable(String id){
            this.id = id;
        }

        @Override
        public void run() {
            final Buy mBuy= mServiceImp.findBuy(id);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.findBuyCallback(mBuy);
                }
            });
        }
    }


    /**
     * 确认收货
     */
    public void updateReceiveFalg(String id){
        ThreadPoolUtils.execute(new updateReceiveFalgRunnable(id));
    }

    private class updateReceiveFalgRunnable implements Runnable {
        String id;
        updateReceiveFalgRunnable(String id){
            this.id = id;
        }

        @Override
        public void run() {
            final boolean b= mServiceImp.updateReceiveFalg(id);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.updateReceiveFalgCallback(b);
                }
            });
        }
    }

    /**
     * 确认发货
     */
    public void updateSendFalg(String id){
        ThreadPoolUtils.execute(new updateSendFalgRunnable(id));
    }

    private class updateSendFalgRunnable implements Runnable {
        String id;
        updateSendFalgRunnable(String id){
            this.id = id;
        }

        @Override
        public void run() {
            final boolean b= mServiceImp.updateSendFalg(id);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mServiceCallback.updateSendFalgCallback(b);
                }
            });
        }
    }

}
