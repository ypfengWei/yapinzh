package cn.yapin.gzh.entity;

import java.io.Serializable;

//公众号用于调用微信JS接口的临时票据
public class JsApiTicket implements Serializable {
    private Integer js_api_tokenid;
    private String appid;
    private String ticket;
    private int expires_in;
    private long create_date;

    public Integer getJs_api_tokenid() {
        return js_api_tokenid;
    }

    public void setJs_api_tokenid(Integer js_api_tokenid) {
        this.js_api_tokenid = js_api_tokenid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }
}
