package cn.yapin.gzh.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CommodityOrder implements Serializable {
    private String id;
    private String openid;
    private String tradeNumber;

    private Double packingFee;

    private Double distributionFee;

    private Double totalPrice;

    private String userAddress;

    private String sign;
    private boolean paymentStatus;//是否支付
    private String state;//'0''未付款''1''已付款''2''配送中''3''已取消''4''已收货',

    private String remarks;

    private Date createDate;
    private String appointmentTime;//预约时间

    private Date updateDate;

    private String delFlag;

    private List<OrderDetails> orderDetailsList;

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber == null ? null : tradeNumber.trim();
    }

    public Double getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(Double packingFee) {
        this.packingFee = packingFee;
    }

    public Double getDistributionFee() {
        return distributionFee;
    }

    public void setDistributionFee(Double distributionFee) {
        this.distributionFee = distributionFee;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}