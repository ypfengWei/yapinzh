package cn.yapin.gzh.service;

import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.entity.JsAccessToken;
import cn.yapin.gzh.entity.JsApiTicket;

public interface ITokenService {

    AccessToken getLocalAccessToken(String appid);

    void saveOrUpdateAccessToken(AccessToken accessToken);

    JsAccessToken getJsAccessToken(String openid);

    JsApiTicket getLocalJsApiTicket(String appid);

    void saveOrUpdateJsApiTicket(JsApiTicket jsApiTicket);
}
