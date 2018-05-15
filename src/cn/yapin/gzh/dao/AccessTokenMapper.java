package cn.yapin.gzh.dao;

import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.entity.JsAccessToken;
import cn.yapin.gzh.entity.JsApiTicket;
import cn.yapin.gzh.entity.LianYunPringAccessToken;

public interface AccessTokenMapper {
    /*微信公众号基础接口调用token管理*/
    void insertAccessToken(AccessToken accessToken) throws Exception;

    AccessToken getLocalAccessToken(String appid) throws Exception;

    void updateAccessToken(AccessToken accessToken) throws Exception;

    /*微信公众号网页接口调用token管理*/
    void insertJsApiTicket(JsApiTicket accessToken) throws Exception;

    JsApiTicket getLocalJsApiTicket(String appid) throws Exception;

    void updateJsApiTicket(JsApiTicket accessToken) throws Exception;

    /*微信公众号用户接口调用token管理*/
    void insertJsAccessToken(JsAccessToken accessToken) throws Exception;

    JsAccessToken getLocalJsAccessToken(String openid);

    void updateJsAccessToken(JsAccessToken jsAccessToken) throws Exception;

    /*易联云打印机接口调用token管理*/
    void insertYunPringAccessToken(LianYunPringAccessToken accessToken) throws Exception;

    LianYunPringAccessToken getLocalLianYunPringAccessToken(String machine_code);

    void updateYunPringAccessToken(LianYunPringAccessToken jsAccessToken) throws Exception;
}
