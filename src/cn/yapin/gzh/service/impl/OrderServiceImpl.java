package cn.yapin.gzh.service.impl;

import cn.yapin.gzh.dao.CommodityOrderMapper;
import cn.yapin.gzh.dao.OrderDetailsMapper;
import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.CommodityOrder;
import cn.yapin.gzh.entity.DiningLocation;
import cn.yapin.gzh.entity.OrderDetails;
import cn.yapin.gzh.service.IOrderService;
import cn.yapin.gzh.utils.Config;
import cn.yapin.gzh.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.yapin.gzh.utils.WebUtils.genAppSign;

public class OrderServiceImpl implements IOrderService {
    @Autowired
    private CommodityOrderMapper commodityOrderMapper;
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Override
    public void addOrder(CommodityOrder commodityOrder, List<OrderDetails> orderDetailsList) throws Exception {
        commodityOrderMapper.insertSelective(commodityOrder);
        orderDetailsMapper.batchInsert(orderDetailsList);
    }

    @Override
    public void updateOrder(CommodityOrder commodityOrder) throws Exception {
        commodityOrderMapper.updateByPrimaryKeySelective(commodityOrder);
    }

    @Override
    public CommodityOrder getOrderByID(String orderid) {
        try {
            return commodityOrderMapper.selectByPrimaryKey(orderid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void paySuccessCall(String orderid) throws Exception {
        commodityOrderMapper.paySuccessCall(orderid);
    }

    @Override
    public String getOpenid(String orderid) {
        try {
            return commodityOrderMapper.getOpenid(orderid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderDetails> getOrderDetailsArray(String id) {
        try {
            return orderDetailsMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DiningLocation getSeatNumber(String openid) {
        try {
            return commodityOrderMapper.getSeatNumber(openid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertDiningLocation(DiningLocation diningLocation) throws Exception {
        commodityOrderMapper.insertDiningLocation(diningLocation);
    }

    @Override
    public void updateDiningLocation(DiningLocation diningLocation) throws Exception {
        commodityOrderMapper.updateDiningLocation(diningLocation);
    }

    @Override
    public List<CommodityOrder> callCommodityOrderArray(BasePage page) {
        try {
            return commodityOrderMapper.callCommodityOrderArray(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cancelOrder(CommodityOrder commodityOrder) throws Exception {
//执行微信退款
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = new HashMap<>();
        params.put("appid", Config.APPID);
        params.put("mch_id", Config.PARTNER);
        params.put("nonce_str", new BigInteger(32, random).toString(8));//生成随机字符串
//            params.put("transaction_id", order.getTradeNo());//微信交易号
        params.put("out_trade_no", commodityOrder.getId());
        params.put("out_refund_no", WebUtils.getOrderNum());
        params.put("total_fee", (int) (commodityOrder.getTotalPrice() * 100));
        params.put("refund_fee", (int) (commodityOrder.getTotalPrice() * 100));
        params.put("sign", genAppSign(params));
        commodityOrder.setState("3");
        commodityOrderMapper.updateByPrimaryKeySelective(commodityOrder);
        WebUtils.wxPayRefund(params);
    }
}
