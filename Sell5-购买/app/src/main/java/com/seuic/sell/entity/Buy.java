package com.seuic.sell.entity;

/**
 * Created by Administrator on 2016/5/17.
 */
public class Buy {
    private String id;//id
    private String sellOrderId;
    private String sellId;//卖方id
    private String buyId;//买方id
    private int receiveFalg;//接收确认标志 0 未确认收货  1确认收货
    private int sendFalg;//发货标识  0 未确认发货  1确认发货

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public int getReceiveFalg() {
        return receiveFalg;
    }

    public void setReceiveFalg(int receiveFalg) {
        this.receiveFalg = receiveFalg;
    }

    public int getSendFalg() {
        return sendFalg;
    }

    public void setSendFalg(int sendFalg) {
        this.sendFalg = sendFalg;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId;
    }
}
