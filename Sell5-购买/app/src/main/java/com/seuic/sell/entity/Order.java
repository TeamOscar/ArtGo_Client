package com.seuic.sell.entity;

/**
 * Created by Administrator on 2016/5/17.
 */
public class Order {
    private String id;//id
    private String sellId;
    private String sellName;
    private String buyId;
    private String buyName;
    private int receiveFalg;//接收确认标志 0 未确认收货  1确认收货
    private int sendFalg;//发货标识  0 未确认发货  1确认发货
    private String path;//图片路径

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

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
