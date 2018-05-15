package cn.yapin.gzh.service.impl;

import cn.yapin.gzh.dao.AccessTokenMapper;
import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.entity.JsAccessToken;
import cn.yapin.gzh.entity.JsApiTicket;
import cn.yapin.gzh.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.yapin.gzh.utils.WebUtils.getWXAccess_token;
import static cn.yapin.gzh.utils.WebUtils.getWXJsApiTicket;

public class TokenServiceImpl implements ITokenService {
    @Autowired
    private AccessTokenMapper tokenMapper;



    @Override
    public AccessToken getLocalAccessToken(String appid) {
        try {
            return tokenMapper.getLocalAccessToken(appid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdateAccessToken(AccessToken accessToken) {
        try {
            if (accessToken.getTokenid() == null) {
                tokenMapper.insertAccessToken(accessToken);
            } else {
                tokenMapper.updateAccessToken(accessToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsAccessToken getJsAccessToken(String openid) {
        try {
            return tokenMapper.getLocalJsAccessToken(openid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveOrUpdateJsAccessToken(JsAccessToken jsAccessToken) {
        try {
            if (jsAccessToken.getJs_tokenid() == null) {
                tokenMapper.insertJsAccessToken(jsAccessToken);
            } else {
                tokenMapper.updateJsAccessToken(jsAccessToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public JsApiTicket getLocalJsApiTicket(String appid) {
        try {
            return tokenMapper.getLocalJsApiTicket(appid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdateJsApiTicket(JsApiTicket jsApiTicket) {
        try {
            if (jsApiTicket.getJs_api_tokenid() == null) {
                tokenMapper.insertJsApiTicket(jsApiTicket);
            } else {
                tokenMapper.updateJsApiTicket(jsApiTicket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
