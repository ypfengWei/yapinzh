package cn.yapin.gzh.action;

import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.entity.JsApiTicket;
import cn.yapin.gzh.service.ITokenService;
import cn.yapin.gzh.utils.Config;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.yapin.gzh.utils.WebUtils.getWXAccess_token;
import static cn.yapin.gzh.utils.WebUtils.getWXJsApiTicket;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    HttpServletRequest httpServletRequest;
    HttpServletResponse httpServletResponse;
    private ITokenService tokenService;
    JSONObject respJSON = new JSONObject();

    int pageIndex;
    int pageCount;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public JSONObject getRespJSON() {
        return respJSON;
    }

    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
        this.httpServletResponse.setContentType("text/html;charset=utf-8");
    }

    JsApiTicket getJsApiTicket() {
        JsApiTicket jsApiTicket = tokenService.getLocalJsApiTicket(Config.APPID);
        AccessToken token;
        if (jsApiTicket == null) {
            token = getAccessToken();
            if (token != null) {
                jsApiTicket = getWXJsApiTicket(token.getAccess_token());
                if (jsApiTicket != null) {
                    jsApiTicket.setAppid(Config.APPID);
                    jsApiTicket.setCreate_date(System.currentTimeMillis());
                    tokenService.saveOrUpdateJsApiTicket(jsApiTicket);
                }
            }
        } else {
            long newTime = System.currentTimeMillis() / 1000;
            long oldTime = jsApiTicket.getCreate_date() / 1000;
            if ((int) (newTime - oldTime) >= jsApiTicket.getExpires_in()) {
                token = getAccessToken();
                if (token != null) {
                    JsApiTicket newJsApiTicket = getWXJsApiTicket(token.getAccess_token());
                    if (newJsApiTicket != null) {
                        jsApiTicket.setCreate_date(System.currentTimeMillis());
                        jsApiTicket.setExpires_in(newJsApiTicket.getExpires_in());
                        jsApiTicket.setTicket(newJsApiTicket.getTicket());
                        tokenService.saveOrUpdateJsApiTicket(jsApiTicket);
                    }
                }
            }
        }
        return jsApiTicket;
    }

    AccessToken getAccessToken() {
        AccessToken accessToken = tokenService.getLocalAccessToken(Config.APPID);
        if (accessToken == null) {
            accessToken = getWXAccess_token(Config.APPID, Config.SECRET);
            if (accessToken != null) {
                accessToken.setAppid(Config.APPID);
                accessToken.setCreate_date(System.currentTimeMillis());
                tokenService.saveOrUpdateAccessToken(accessToken);
            }
        } else {
            long newTime = System.currentTimeMillis() / 1000;
            long oldTime = accessToken.getCreate_date() / 1000;
            if ((int) (newTime - oldTime) >= accessToken.getExpires_in()) {
                AccessToken newAccessToken = getWXAccess_token(Config.APPID, Config.SECRET);
                if (newAccessToken != null) {
                    accessToken.setCreate_date(System.currentTimeMillis());
                    accessToken.setAccess_token(newAccessToken.getAccess_token());
                    accessToken.setExpires_in(newAccessToken.getExpires_in());
                    tokenService.saveOrUpdateAccessToken(accessToken);
                }
            }
        }
        return accessToken;
    }
}
