package cn.yapin.gzh.dao;


import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.Census;
import cn.yapin.gzh.entity.CommodityOrder;
import cn.yapin.gzh.entity.DiningLocation;

import java.util.List;

public interface CommodityOrderMapper {
    void deleteByPrimaryKey(String id) throws Exception;

    void insert(CommodityOrder record) throws Exception;

    void insertSelective(CommodityOrder record) throws Exception;

    CommodityOrder selectByPrimaryKey(String id) throws Exception;

    void updateByPrimaryKeySelective(CommodityOrder record) throws Exception;

    void updateByPrimaryKey(CommodityOrder record) throws Exception;

    void paySuccessCall(String id) throws Exception;

    String getOpenid(String orderid) throws Exception;

    DiningLocation getSeatNumber(String openid) throws Exception;

    void insertDiningLocation(DiningLocation diningLocation) throws Exception;

    void updateDiningLocation(DiningLocation diningLocation) throws Exception;

    List<CommodityOrder> callCommodityOrderArray(BasePage page) throws Exception;

    Census businessStatistics();
}