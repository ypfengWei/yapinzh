package cn.yapin.gzh.service.impl;

import cn.yapin.gzh.dao.AccessTokenMapper;
import cn.yapin.gzh.entity.CommodityOrder;
import cn.yapin.gzh.entity.LianYunPringAccessToken;
import cn.yapin.gzh.entity.OrderDetails;
import cn.yapin.gzh.service.IPrintService;
import cn.yapin.gzh.yilianyunprint.Methods;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrintServiceImpl implements IPrintService {
    @Autowired
    private AccessTokenMapper tokenMapper;

    @Override
    public String printOrderPaper(String token, CommodityOrder commodityOrder, List<OrderDetails> orderDetailsList) {
        return Methods.getInstance().print(token, Methods.MACHINE_CODE, jointContext(commodityOrder, orderDetailsList), commodityOrder.getId());
    }

    private String jointContext(CommodityOrder commodityOrder, List<OrderDetails> orderDetailsList) {
        StringBuilder builder = new StringBuilder();

        builder.append("@@2 闪多外卖\r\n" + "下单时间:").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\r\n").append("订单编号:").append(commodityOrder.getId()).append("\r\n").append("**************商品**************");
        for (OrderDetails orderDetails : orderDetailsList) {
            builder.append("@@2").append(orderDetails.getCommodityName()).append("\tx").append(orderDetails.getCommodityNumber()).append("\r\n");
        }
        builder.append("********************************\r\n" + "@@2订单总价:￥").append(commodityOrder.getTotalPrice()).append("\r\n");
        if ("1".equals(commodityOrder.getSign())) {
            builder.append("@@2座号:").append(commodityOrder.getUserAddress()).append("\r\n");
        } else {
            builder.append(commodityOrder.getUserAddress()).append("\r\n");
        }
        if (commodityOrder.getAppointmentTime().length() > 0) {
            builder.append("@@2预约时间:").append(commodityOrder.getAppointmentTime()).append("\r\n");
        }
        if (commodityOrder.getRemarks().length() > 0) {
            builder.append("@@2备注:").append(commodityOrder.getRemarks()).append("\r\n");
        }
        builder.append("@@2**************完**************");
        return builder.toString();
    }

    @Override
    public LianYunPringAccessToken getLocalLianYunPringAccessToken() {
        return tokenMapper.getLocalLianYunPringAccessToken(Methods.MACHINE_CODE);
    }

    @Override
    public void insertYunPringAccessToken(LianYunPringAccessToken token) {
        try {
            tokenMapper.insertYunPringAccessToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateYunPringAccessToken(LianYunPringAccessToken token) {
        try {
            tokenMapper.updateYunPringAccessToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
