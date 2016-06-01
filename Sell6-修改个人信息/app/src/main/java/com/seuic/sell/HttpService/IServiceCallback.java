package com.seuic.sell.HttpService;

import com.seuic.sell.entity.Buy;
import com.seuic.sell.entity.Order;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public interface IServiceCallback {
    void addUserCallback(boolean b);
    void modifyNameCallback(boolean b);
    void modifyAddressCallback(boolean b);
    void modifySignCallback(boolean b);
    void findUserCallback(User mUser);
    void findUserByIdCallback(User mUser);
    void addSellCallback(boolean b);
    void findSellCallback(List<Sell> list);
    void findAllSellCallback(List<Sell> list);
    void addBuyCallback(boolean b);
    void findByUserIdBuyCallback(List<Order> list);
    void findBuyCallback(Buy mBuy);
    void updateReceiveFalgCallback(boolean b);
    void updateSendFalgCallback(boolean b);
}
