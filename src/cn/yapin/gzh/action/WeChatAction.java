package cn.yapin.gzh.action;

import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.entity.DiningLocation;
import cn.yapin.gzh.entity.JsAccessToken;
import cn.yapin.gzh.entity.JsApiTicket;
import cn.yapin.gzh.message.MessageType;
import cn.yapin.gzh.model.receiveEvent.*;
import cn.yapin.gzh.model.receiveMsg.*;
import cn.yapin.gzh.open.WxAdapter;
import cn.yapin.gzh.service.IOrderService;
import cn.yapin.gzh.utils.Config;
import cn.yapin.gzh.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Template;
import org.apache.http.HttpEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static cn.yapin.gzh.utils.Config.*;
import static cn.yapin.gzh.utils.WebUtils.*;

public class WeChatAction extends BaseAction {
    private String signature;//微信服务端加密字符串
    private String timestamp;
    private String nonce;//随机字符串
    private String echostr;
    private Map<String, Object> wxMsg = new HashMap<>();
    private String code;
    private String pageUrl;//要使用js调用接口的页面路径
    private IOrderService orderService;
    private String openid;
    private String seat_number;
    private String fileName;
    private InputStream inputStream;
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private WxAdapter wxAdapter;

    public void setWxAdapter(WxAdapter wxAdapter) {
        this.wxAdapter = wxAdapter;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getCode() {
        return code;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String wxRedirectMessage() throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        wxAdapter.setWriter(writer);
        wxAdapter.setRequest(httpServletRequest);
        wxAdapter.setResponse(httpServletResponse);
        wxAdapter.setServletContext(httpServletRequest.getServletContext());
        if (echostr == null) {
            Map<String, String> map = doXMLParse(readBuffer(httpServletRequest));
            String MsgType = map.get("MsgType");
            switch (MsgType) {
                case MessageType.TEXT:
                    //文本消息
                    onReceiveText(map);
                    break;
                case MessageType.IMAGE:
                    //图片消息
                    onReceiveImage(map);
                    break;
                case MessageType.VOICE:
                    //语音消息
                    onReceiveVoice(map);
                    break;
                case MessageType.VIDEO:
                    //视频消息
                    onReceiveVideo(map);
                    break;
                case MessageType.SHORTVIDEO:
                    //小视频消息
                    onReceiveShortvideo(map);
                    break;
                case MessageType.LOCATION:
                    //地理位置消息
                    onReceiveLocation(map);
                    break;
                case MessageType.LINK:
                    //用户分享链接消息
                    onReceiveLink(map);
                    break;
                case MessageType.EVENT:
                    onReceiveEvent(map);
                    break;
            }
            writer.print("");
            writer.flush();
            writer.close();
        } else {
            //接入端口验证
            List<String> list = new ArrayList<>();
            list.add(TOKEN);
            list.add(timestamp);
            list.add(nonce);
            Collections.sort(list);
            String signStr = "";
            for (String str : list) {
                signStr += str;
            }
            if (signature.equals(SHA1(signStr))) {
                writer.print(echostr);
            } else {
                writer.print("error");
                writer.flush();
                writer.close();
            }
        }
        return NONE;
    }

    private void onReceiveText(Map<String, String> map) {
        ReceiveTextMessage message = new ReceiveTextMessage();
        message.setContent(map.get("Content"));
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));

        wxAdapter.onReceiveText(message);
    }

    private void onReceiveVoice(Map<String, String> map) {
        ReceiveVoiceMessage message = new ReceiveVoiceMessage();
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setFormat(map.get("Format"));
        message.setMediaId(map.get("MediaId"));
        message.setRecognition(map.get("Recognition"));
        wxAdapter.onReceiveVoice(message);
    }

    private void onReceiveVideo(Map<String, String> map) {
        ReceiveVideoMessage message = new ReceiveVideoMessage();

        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setMediaId(map.get("MediaId"));
        message.setThumbMediaId(map.get("ThumbMediaId"));

        wxAdapter.onReceiveVideo(message);
    }

    private void onReceiveShortvideo(Map<String, String> map) {
        ReceiveShortvideoMessage message = new ReceiveShortvideoMessage();
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setMediaId(map.get("MediaId"));
        message.setThumbMediaId(map.get("ThumbMediaId"));

        wxAdapter.onReceiveShortvideo(message);
    }

    private void onReceiveLocation(Map<String, String> map) {
        ReceiveLocationMessage message = new ReceiveLocationMessage();
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setLocation_X(map.get("Location_X"));
        message.setLocation_Y(map.get("Location_Y"));
        message.setLabel(map.get("Label"));
        message.setScale(map.get("Scale"));
        wxAdapter.onReceiveLocation(message);
    }

    private void onReceiveImage(Map<String, String> map) {
        ReceiveImageMessage message = new ReceiveImageMessage();
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setPicUrl(map.get("PicUrl"));
        message.setMediaId(map.get("MediaId"));
        wxAdapter.onReceiveImage(message);
    }

    private void onReceiveLink(Map<String, String> map) {
        ReceiveLinkMessage message = new ReceiveLinkMessage();
        message.setCreateTime(Long.valueOf(map.get("CreateTime")));
        message.setFromUserName(map.get("FromUserName"));
        message.setToUserName(map.get("ToUserName"));
        message.setMsgId(map.get("MsgId"));
        message.setMsgType(map.get("MsgType"));
        message.setDescription(map.get("Description"));
        message.setTitle(map.get("Title"));
        message.setUrl(map.get("Url"));
        wxAdapter.onReceiveLink(message);
    }

    private void onReceiveEvent(Map<String, String> map) {
        //事件推送
        String Event = map.get("Event");
        String eventKey = map.get("EventKey");
        switch (Event) {
            case "subscribe":
                //用户未关注时，进行关注后的事件推picEvent2
                try {
                    myEvent(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (eventKey == null) {
                    ReceiveEvent e = new ReceiveEvent();
                    e.setCreateTime(Long.valueOf(map.get("CreateTime")));
                    e.setEvent(map.get("Event"));
                    e.setFromUserName(map.get("FromUserName"));
                    e.setMsgType(map.get("MsgType"));
                    e.setToUserName(map.get("ToUserName"));
                    wxAdapter.onSubscriptionEvent(e);
                } else {
                    ReceiveScanEvent e = new ReceiveScanEvent();
                    e.setCreateTime(Long.valueOf(map.get("CreateTime")));
                    e.setEvent(map.get("Event"));
                    e.setFromUserName(map.get("FromUserName"));
                    e.setMsgType(map.get("MsgType"));
                    e.setToUserName(map.get("ToUserName"));
                    e.setEventKey(map.get("EventKey"));
                    e.setTicket(map.get("Ticket"));
                    wxAdapter.onScanWithSubscribeEvent(e);
                }
                break;
            case "unsubscribe":
                //用户取消关注
                ReceiveEvent receive = new ReceiveEvent();
                receive.setCreateTime(Long.valueOf(map.get("CreateTime")));
                receive.setEvent(map.get("Event"));
                receive.setFromUserName(map.get("FromUserName"));
                receive.setMsgType(map.get("MsgType"));
                receive.setToUserName(map.get("ToUserName"));
                wxAdapter.onUnSubscriptionEvent(receive);
                break;
            case "SCAN":
                //用户扫描带场景值二维码，已关注时的事件推送
                try {
                    myEvent(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ReceiveScanEvent scanEvent = new ReceiveScanEvent();
                scanEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                scanEvent.setEvent(map.get("Event"));
                scanEvent.setFromUserName(map.get("FromUserName"));
                scanEvent.setMsgType(map.get("MsgType"));
                scanEvent.setToUserName(map.get("ToUserName"));
                scanEvent.setEventKey(map.get("EventKey"));
                scanEvent.setTicket(map.get("Ticket"));
                wxAdapter.onScanWithSubscribedEvent(scanEvent);
                break;
            case "LOCATION":
                //上报地理位置事件
                ReceiveLocationEvent locationEvent = new ReceiveLocationEvent();
                locationEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                locationEvent.setEvent(map.get("Event"));
                locationEvent.setFromUserName(map.get("FromUserName"));
                locationEvent.setMsgType(map.get("MsgType"));
                locationEvent.setToUserName(map.get("ToUserName"));
                locationEvent.setLatitude(map.get("Latitude"));
                locationEvent.setLongitude(map.get("Longitude"));
                locationEvent.setPrecision(map.get("Precision"));
                wxAdapter.onLocationEvent(locationEvent);
                break;
            case "CLICK":
                //点击菜单拉取消息时的事件推送
                ReceiveClickMenuEvent menuEvent = new ReceiveClickMenuEvent();
                menuEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                menuEvent.setEvent(map.get("Event"));
                menuEvent.setFromUserName(map.get("FromUserName"));
                menuEvent.setMsgType(map.get("MsgType"));
                menuEvent.setToUserName(map.get("ToUserName"));
                menuEvent.setEventKey(map.get("EventKey"));

                wxAdapter.onClickMenuEvent(menuEvent);
                break;
            case "VIEW":
                //点击菜单跳转链接时的事件推送
                ReceiveViewMenuEvent viewMenuEvent = new ReceiveViewMenuEvent();
                viewMenuEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                viewMenuEvent.setEvent(map.get("Event"));
                viewMenuEvent.setFromUserName(map.get("FromUserName"));
                viewMenuEvent.setMsgType(map.get("MsgType"));
                viewMenuEvent.setToUserName(map.get("ToUserName"));
                viewMenuEvent.setEventKey(map.get("EventKey"));
                viewMenuEvent.setMenuID(map.get("MenuId"));
                wxAdapter.onViewMenuEvent(viewMenuEvent);
                break;
            case "scancode_push":
                // 扫码推事件
                ReceiveScanCodeEvent scanCodeEvent = new ReceiveScanCodeEvent();
                scanCodeEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                scanCodeEvent.setEvent(map.get("Event"));
                scanCodeEvent.setFromUserName(map.get("FromUserName"));
                scanCodeEvent.setMsgType(map.get("MsgType"));
                scanCodeEvent.setToUserName(map.get("ToUserName"));
                scanCodeEvent.setScanCodeInfo(map.get("ScanCodeInfo"));
                scanCodeEvent.setScanResult(map.get("ScanResult"));
                scanCodeEvent.setScanType(map.get("ScanType"));
                scanCodeEvent.setMenuID(map.get("MenuId"));
                scanCodeEvent.setEventKey(map.get("EventKey"));
                wxAdapter.onScanCodePushEvent(scanCodeEvent);
                break;
            case "scancode_waitmsg":
                // 扫码带提示
                ReceiveScanCodeEvent codeEvent = new ReceiveScanCodeEvent();
                codeEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                codeEvent.setEvent(map.get("Event"));
                codeEvent.setFromUserName(map.get("FromUserName"));
                codeEvent.setMsgType(map.get("MsgType"));
                codeEvent.setToUserName(map.get("ToUserName"));
                codeEvent.setScanCodeInfo(map.get("ScanCodeInfo"));
                codeEvent.setScanResult(map.get("ScanResult"));
                codeEvent.setScanType(map.get("ScanType"));
                codeEvent.setMenuID(map.get("MenuId"));
                codeEvent.setEventKey(map.get("EventKey"));
                wxAdapter.onScanCodeWaitMsgEvent(codeEvent);
                break;
            case "pic_sysphoto":
                // 系统拍照发图
                ReceivePicEvent picEvent = new ReceivePicEvent();
                picEvent.setCreateTime(Long.valueOf(map.get("CreateTime")));
                picEvent.setEvent(map.get("Event"));
                picEvent.setFromUserName(map.get("FromUserName"));
                picEvent.setMsgType(map.get("MsgType"));
                picEvent.setToUserName(map.get("ToUserName"));
                picEvent.setPicList(map.get("PicList"));
                picEvent.setPicMd5Sum(map.get("PicMd5Sum"));
                picEvent.setSendPicsInfo(map.get("SendPicsInfo"));
                picEvent.setCount(map.get("Count"));

                wxAdapter.onPicSysPhotoEvent(picEvent);
                break;
            case "pic_photo_or_album":
                // 拍照或者相册发图
                ReceivePicEvent picEvent2 = new ReceivePicEvent();
                picEvent2.setCreateTime(Long.valueOf(map.get("CreateTime")));
                picEvent2.setEvent(map.get("Event"));
                picEvent2.setFromUserName(map.get("FromUserName"));
                picEvent2.setMsgType(map.get("MsgType"));
                picEvent2.setToUserName(map.get("ToUserName"));
                picEvent2.setPicList(map.get("PicList"));
                picEvent2.setPicMd5Sum(map.get("PicMd5Sum"));
                picEvent2.setSendPicsInfo(map.get("SendPicsInfo"));
                picEvent2.setCount(map.get("Count"));

                wxAdapter.onPicPhotoOrAlbumnEvent(picEvent2);
                break;
            case "pic_weixin":
                // 微信相册发图
                ReceivePicEvent picEvent3 = new ReceivePicEvent();
                picEvent3.setCreateTime(Long.valueOf(map.get("CreateTime")));
                picEvent3.setEvent(map.get("Event"));
                picEvent3.setFromUserName(map.get("FromUserName"));
                picEvent3.setMsgType(map.get("MsgType"));
                picEvent3.setToUserName(map.get("ToUserName"));
                picEvent3.setPicList(map.get("PicList"));
                picEvent3.setPicMd5Sum(map.get("PicMd5Sum"));
                picEvent3.setSendPicsInfo(map.get("SendPicsInfo"));
                picEvent3.setCount(map.get("Count"));
                wxAdapter.onPicWeixinEvent(picEvent3);
                break;
            case "location_select":
                // 发送位置
                ReceiveLocationSelectEvent e = new ReceiveLocationSelectEvent();
                e.setCreateTime(Long.valueOf(map.get("CreateTime")));
                e.setEvent(map.get("Event"));
                e.setFromUserName(map.get("FromUserName"));
                e.setMsgType(map.get("MsgType"));
                e.setToUserName(map.get("ToUserName"));
                e.setEventKey(eventKey);
                e.setSendLocationInfo(map.get("SendLocationInfo"));
                e.setScale(map.get("Scale"));
                e.setPoiname(map.get("Poiname"));
                e.setLocation_Y(map.get("Location_Y"));
                e.setLocation_X(map.get("Location_X"));
                e.setLabel(map.get("Label"));

                wxAdapter.onLocationSelectEvent(e);
                break;
            case "MASSSENDJOBFINISH":
                // 大量发送工作完成事件
                wxAdapter.onMassSendJobFinishEvent(map);
                break;
        }
    }

    private void myEvent(Map<String, String> parameters) throws Exception {
        String eventKey = parameters.get("EventKey");
        if (eventKey.startsWith("yaping_")) {
            String num = eventKey.split("_")[1];
            String openid = parameters.get("FromUserName");
            DiningLocation diningLocation = orderService.getSeatNumber(openid);
            if (diningLocation == null) {
                diningLocation = new DiningLocation(openid, num);
                orderService.insertDiningLocation(diningLocation);
            } else {
                diningLocation.setSeat_number(num);
                orderService.updateDiningLocation(diningLocation);
            }
        }
    }

    /*获取访问公众号用户openid*/
    public String getOpenid() {
        respJSON.clear();
        JsAccessToken accessToken = getWXJsAccess_token(APPID, SECRET, code);
        if (accessToken != null) {
            respJSON.put("openid", accessToken.getOpenid());
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }

    /*配置微信网页jsapi调用环境*/
    public String initJS_SDK() {
        JsApiTicket jsapi_ticket = getJsApiTicket();
        if (jsapi_ticket != null) {
            SecureRandom random = new SecureRandom();
            StringBuilder builder = new StringBuilder();
            String noncestr = new BigInteger(32, random).toString(8);
            long timestamp = System.currentTimeMillis();
            builder.append("jsapi_ticket=").append(jsapi_ticket.getTicket())
                    .append("&noncestr=").append(noncestr)
                    .append("&timestamp=").append(timestamp)
                    .append("&url=").append(pageUrl);
            String signature = SHA1(builder.toString());
            respJSON.put("noncestr", noncestr);
            respJSON.put("timestamp", timestamp);
            respJSON.put("appid", APPID);
            respJSON.put("signature", signature);
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }

    //创建自定义菜单
    public String createMenu() {
        try {
            AccessToken token = getAccessToken();
            if (token != null) {
                JSONObject jsonObject = JSON.parseObject(getMenu(token.getAccess_token()));
                if (jsonObject.containsKey("errcode")) {
                    jsonObject.clear();
                    Map<String, Object> map = new HashMap<>();
                    map.put("appid", Config.APPID);
                    map.put("uri", Config.HOST);
                    Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("menu.json");
                    respJSON = JSON.parseObject(WebUtils.createMenu(token.getAccess_token(), FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    //删除自定义菜单
    public String delMenu() {
        try {
            AccessToken token = getAccessToken();
            respJSON = JSON.parseObject(WebUtils.delMenu(token.getAccess_token()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /*获取包含座号信息的二维码*/
    public String create_qrcode() {
        if (seat_number != null) {
            JSONObject jsonObject = WebUtils.create_qrcode(getAccessToken().getAccess_token(), seat_number);
            if (jsonObject != null) {
                HttpEntity entity = WebUtils.download_qrcode(jsonObject.getString("ticket"));
                if (entity != null) {
                    try {
                        setFileName("wx_NUMBER.jpg".replace("NUMBER", seat_number));
                        setInputStream(entity.getContent());
                        return SUCCESS;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return NONE;
    }

    public String getSeatNumber() {
        DiningLocation diningLocation = orderService.getSeatNumber(openid);
        if (diningLocation != null) {
            respJSON.put("seatNumber", diningLocation.getSeat_number());
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }
}
