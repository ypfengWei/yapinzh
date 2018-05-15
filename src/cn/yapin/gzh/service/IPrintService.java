package cn.yapin.gzh.service;

import cn.yapin.gzh.entity.CommodityOrder;
import cn.yapin.gzh.entity.LianYunPringAccessToken;
import cn.yapin.gzh.entity.OrderDetails;

import java.util.List;

public interface IPrintService {
    String printOrderPaper(String token, CommodityOrder commodityOrder, List<OrderDetails> orderDetailsList);

    LianYunPringAccessToken getLocalLianYunPringAccessToken();

    void insertYunPringAccessToken(LianYunPringAccessToken token);

    void updateYunPringAccessToken(LianYunPringAccessToken token);
}
