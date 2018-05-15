package cn.yapin.gzh.action;

import cn.yapin.gzh.entity.*;
import cn.yapin.gzh.service.IOrderService;
import cn.yapin.gzh.service.IPrintService;
import cn.yapin.gzh.utils.Config;
import cn.yapin.gzh.utils.WebUtils;
import cn.yapin.gzh.utils.wxpay.ResponseHandler;
import cn.yapin.gzh.yilianyunprint.Methods;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.yapin.gzh.utils.WebUtils.*;

public class OrderManageAction extends BaseAction {
    private String commodityOrder;
    private String orderDetailsList;
    private IOrderService orderService;
    private IPrintService printService;
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private String orderId;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public IOrderService getOrderService() {
        return orderService;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityOrder() {
        return commodityOrder;
    }

    public void setPrintService(IPrintService printService) {
        this.printService = printService;
    }

    public void setCommodityOrder(String commodityOrder) {
        if (commodityOrder != null && commodityOrder.length() > 0) {
            this.commodityOrder = commodityOrder;
        }
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public String getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(String orderDetailsList) {
        if (orderDetailsList != null && orderDetailsList.length() > 0) {
            this.orderDetailsList = orderDetailsList;
        }
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    //生成订单并返回支付数据
    public String addOrder() {
        if (commodityOrder != null && orderDetailsList != null) {
            SecureRandom random = new SecureRandom();
            try {
                String orderid = WebUtils.getOrderNum();
                CommodityOrder order = JSON.parseObject(commodityOrder, CommodityOrder.class);
                order.setId(orderid);
                order.setPaymentStatus(false);
                List<OrderDetails> orderDetailsList1 = JSON.parseArray(orderDetailsList, OrderDetails.class);
                Double totalPrice = calculatePrice(orderid, orderDetailsList1);
                order.setTotalPrice(totalPrice);
                order.setState("0");
                orderService.addOrder(order, orderDetailsList1);
                Map<String, Object> params = new HashMap<>();
                params.put("appid", Config.APPID);
                params.put("mch_id", Config.PARTNER);//商户号
                params.put("nonce_str", new BigInteger(32, random).toString(8));
                params.put("body", "点餐消费");
                params.put("out_trade_no", orderid);
                params.put("total_fee", (int) (totalPrice * 100));
                params.put("spbill_create_ip", getRemortIP(httpServletRequest));//终端IP
                params.put("notify_url", Config.NOTIFYURL);//异步通知地址
                params.put("trade_type", "JSAPI");
                params.put("openid", order.getOpenid());
                params.put("sign", genAppSign(params));
                JSONObject backData = unifiedOrder(params);
                if (backData != null) {
                    respJSON.put("data", backData);
                    respJSON.put("success", true);
                    return SUCCESS;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }

    /*计算订单总价格*/
    private Double calculatePrice(String orderid, List<OrderDetails> orderDetailsList) {
        BigDecimal b1 = new BigDecimal(0.00);
        for (OrderDetails orderDetails : orderDetailsList) {
            orderDetails.setOrderId(orderid);
            BigDecimal num = new BigDecimal(orderDetails.getCommodityNumber());
            BigDecimal price = new BigDecimal(orderDetails.getPrice());
            BigDecimal b2 = num.multiply(price);
            b1 = b1.add(b2);
        }
        return b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /*微信支付异步通知*/
    public String wxPayNotify() throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        //创建支付应答对象
        ResponseHandler resHandler = new ResponseHandler(httpServletRequest, httpServletResponse);
        if ("SUCCESS".equals(resHandler.getParameter("return_code")) && "SUCCESS".equals(resHandler.getParameter("result_code"))) {
            resHandler.setKey(Config.PARTNER_KEY);
            //取结果参数做业务处理
            String appid = resHandler.getParameter("appid");//应用号
            String mch_id = resHandler.getParameter("mch_id");//商户号
            String out_trade_no = resHandler.getParameter("out_trade_no");//商户订单号
            String transaction_id = resHandler.getParameter("transaction_id");//微信支付订单号
            int total_fee = Integer.parseInt(resHandler.getParameter("total_fee"));//总金额(分)
            //判断签名
            if (resHandler.isTenpaySign()) {
                if (Config.PARTNER.equals(mch_id)) {
                    try {
                        String openid = orderService.getOpenid(out_trade_no);
                        AccessToken accessToken = getAccessToken();
                        orderService.paySuccessCall(out_trade_no);//支付成功后更改订单支付状态与订单状态
                        CommodityOrder order = orderService.getOrderByID(out_trade_no);
                        List<OrderDetails> orderDetailsList = orderService.getOrderDetailsArray(order.getId());
                        Map<String, Object> map = new HashMap<>();
                        map.put("openid", openid);
                        map.put("template_id", Config.PAYSUCCESSTEMPLATEID);
                        map.put("orderDetailsList", orderDetailsList);
                        map.put("price", order.getTotalPrice());
                        Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("pay_success_template.json");
                        String res = WebUtils.sendTemplateMessage(accessToken.getAccess_token(), FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map));//发送模版消息
                        logResult("模版消息:" + res);
                        printService.printOrderPaper(getLocalToken(), order, orderDetailsList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logResult("更新订单支付状态出错");
                    }
                } else {
                    logResult("商户号不一致，或支付金额不一致!订单号:" + out_trade_no);
                }
                writer.println("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
                writer.flush();
                writer.close();
                return NONE;
            }
            logResult("签名错误,订单号:" + out_trade_no);
            writer.println("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
            writer.flush();
            writer.close();
        }
        return NONE;
    }

    /*获取易联云token*/
    private String getLocalToken() {
        LianYunPringAccessToken token = printService.getLocalLianYunPringAccessToken();
        if (token == null) {
            JSONObject jsonObject = Methods.getInstance().getFreedomToken();
            token = new LianYunPringAccessToken(Methods.MACHINE_CODE, jsonObject.getString("access_token"), jsonObject.getString("refresh_token"), Integer.parseInt(jsonObject.getString("expires_in")), System.currentTimeMillis());
            try {
                printService.insertYunPringAccessToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if ((System.currentTimeMillis() - token.getCreate_date()) >= token.getExpires_in()) {
                JSONObject jsonObject = Methods.getInstance().getFreedomToken();
                token = new LianYunPringAccessToken(Methods.MACHINE_CODE, jsonObject.getString("access_token"), jsonObject.getString("refresh_token"), Integer.parseInt(jsonObject.getString("expires_in")), System.currentTimeMillis());
                try {
                    printService.updateYunPringAccessToken(token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return token.getAccess_token();
    }

    public String loadOrders() {
        BasePage basePage = new BasePage();
        basePage.setFields("commodity_order.id,commodity_order.total_price,commodity_order.remarks,commodity_order.sign,commodity_order.user_address,commodity_order.payment_time,appointment_time,order_details.id AS order_details_id,order_details.commodity_name,order_details.price,order_details.commodity_number");
        basePage.setTables("commodity_order,order_details");
        basePage.setWheres("commodity_order.id=order_details.order_id AND commodity_order.state=" + state);
        basePage.setGrops("");
        basePage.setPageindex(pageIndex);
        basePage.setPageCount(pageCount);
        basePage.setOrders("commodity_order.payment_time");
        try {
            List<CommodityOrder> list = orderService.callCommodityOrderArray(basePage);
            if (list != null) {
                respJSON.put("commodityOrders", ((JSONArray) JSON.toJSON(list)).toJSONString());
                respJSON.put("totalPage", basePage.getTotalPage());
                respJSON.put("totalSize", basePage.getTotalSize());
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /*确认接单*/
    public String confirmOrder() {
        CommodityOrder commodityOrder = orderService.getOrderByID(orderId);
        List<OrderDetails> orderDetailsList = orderService.getOrderDetailsArray(orderId);
        if (commodityOrder.isPaymentStatus()) {
            try {
                commodityOrder.setState("2");
                orderService.updateOrder(commodityOrder);
                //TODO 推送商家已接单通知给客户
                Map<String, Object> map = new HashMap<>();
                map.put("openid", commodityOrder.getOpenid());
                map.put("template_id", Config.BUYSUCCESSTEMPLATEID);
                map.put("orderDetailsList", orderDetailsList);
                Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("buy_success_template.json");
                String res = WebUtils.sendTemplateMessage(getAccessToken().getAccess_token(), FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map));//发送模版消息
                logResult("" + res);
                respJSON.put("success", true);
                respJSON.put("msg", "已接单");
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }

    /*取消订单*/
    public String cancelOrder() {
        CommodityOrder commodityOrder = orderService.getOrderByID(orderId);
        if (commodityOrder.isPaymentStatus()) {
            try {
                orderService.cancelOrder(commodityOrder);
                //TODO 推送商家取消订单通知给客户
                Map<String, Object> map = new HashMap<>();
                map.put("openid", commodityOrder.getOpenid());
                map.put("template_id", Config.REFUNDTEMPLATEID);
                map.put("reason", "商家取消");
                map.put("price", commodityOrder.getTotalPrice());
                Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("refund_template.json");
                String res = WebUtils.sendTemplateMessage(getAccessToken().getAccess_token(), FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map));//发送模版消息
                logResult("" + res);
                respJSON.put("success", true);
                respJSON.put("msg", "订单已取消");
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }

    /*订单完成*/
    public String completeOrder() {
        CommodityOrder commodityOrder = orderService.getOrderByID(orderId);
        commodityOrder.setState("4");
        try {
            orderService.updateOrder(commodityOrder);
            respJSON.put("success", true);
            respJSON.put("msg", "订单已完成");
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        respJSON.put("success", false);
        return ERROR;
    }
}
