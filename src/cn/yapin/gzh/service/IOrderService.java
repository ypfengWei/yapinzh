package cn.yapin.gzh.service;

import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.CommodityOrder;
import cn.yapin.gzh.entity.DiningLocation;
import cn.yapin.gzh.entity.OrderDetails;

import java.util.List;

public interface IOrderService {
    void addOrder(CommodityOrder commodityOrder, List<OrderDetails> orderDetails) throws Exception;

    void updateOrder(CommodityOrder commodityOrder) throws Exception;

    CommodityOrder getOrderByID(String orderid);

    void paySuccessCall(String orderid) throws Exception;

    String getOpenid(String orderid) throws Exception;

    List<OrderDetails> getOrderDetailsArray(String id);

    List<CommodityOrder> callCommodityOrderArray(BasePage page);


    DiningLocation getSeatNumber(String openid);

    void insertDiningLocation(DiningLocation diningLocation) throws Exception;

    void updateDiningLocation(DiningLocation diningLocation) throws Exception;

    void cancelOrder(CommodityOrder commodityOrder) throws Exception;
}
