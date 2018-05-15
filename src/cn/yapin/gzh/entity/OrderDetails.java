package cn.yapin.gzh.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderDetails implements Serializable {
    private Integer id;

    private String orderId;
    private String commodityName;

    private String commodityId;

    private Integer commodityNumber;

    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(Integer commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}