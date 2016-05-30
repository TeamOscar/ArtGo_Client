package com.seuic.sell.HttpService;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Buy;
import com.seuic.sell.entity.Order;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.entity.User;
import com.seuic.sell.util.HttpClientUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ServiceImp {

    public boolean addUser(User mUser){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",JSON.toJSONString(mUser));
            map.put("method","addUser");
            String result = getHttpResponse(map, Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    public boolean modifyName(User mUser){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",JSON.toJSONString(mUser));
            map.put("method","modifyName");
            String result = getHttpResponse(map,Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    public boolean modifyAddress(User mUser){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",JSON.toJSONString(mUser));
            map.put("method","modifyAddress");
            String result = getHttpResponse(map,Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    public boolean modifySign(User mUser){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user",JSON.toJSONString(mUser));
            map.put("method","modifySign");
            String result = getHttpResponse(map,Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    public User findUser(String email,String password){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("email",email);
            map.put("method","findUser");
            map.put("password",password);
            String result = getHttpResponse(map,Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,User.class);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public User findUserById(String id){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);
            map.put("method","findUserById");
            String result = getHttpResponse(map,Common.serviceUrl+"UserServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,User.class);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }



    public boolean addSell(Sell mSell){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("sell", JSON.toJSONString(mSell));
            map.put("method","add");
            String result = getHttpResponse(map,Common.serviceUrl+"SellServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public List<Sell> findSell(String sellId){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("sellId", sellId);
            map.put("method","find");
            String result = getHttpResponse(map,Common.serviceUrl+"SellServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,new TypeReference<List<Sell>>() {});
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<Sell> findAllSell(){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("method","findAll");
            String result = getHttpResponse(map,Common.serviceUrl+"SellServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,new TypeReference<List<Sell>>() {});
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }


    public boolean addBuy(Buy mBuy){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("buy", JSON.toJSONString(mBuy));
            map.put("method","add");
            String result = getHttpResponse(map,Common.serviceUrl+"BuyServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public List<Order> findByUserIdBuy(String id){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("method","findByUserId");
            String result = getHttpResponse(map,Common.serviceUrl+"BuyServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,new TypeReference<List<Order>>() {});
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public Buy findBuy(String id){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("method","find");
            String result = getHttpResponse(map,Common.serviceUrl+"BuyServlet");
            if(!TextUtils.isEmpty(result)){
                return JSON.parseObject(result,Buy.class);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public boolean updateReceiveFalg(String id){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("method","updateReceiveFalg");
            String result = getHttpResponse(map,Common.serviceUrl+"BuyServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean updateSendFalg(String id){
        try{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("method","updateSendFalg");
            String result = getHttpResponse(map,Common.serviceUrl+"BuyServlet");
            if(!TextUtils.isEmpty(result)&&result.equals("true")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public String getHttpResponse(HashMap<String,String> param,String URL){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        String result=null;
        for(int i = 0;i<2;i++) {
            result = HttpClientUtils.requestResult(HttpClientUtils.POST_METHOD, URL,
                    null, params);
            if (result != null) {
                break;
            }
        }

        return result;
    }

}
