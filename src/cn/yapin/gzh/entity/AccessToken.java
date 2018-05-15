package cn.yapin.gzh.entity;

import java.io.Serializable;

//微信公众平台全局唯一接口调用凭据access_token
public class AccessToken implements Serializable {
    private Integer tokenid;
    private String appid;
    private String access_token;
    private int expires_in;
    private long create_date;

    public Integer getTokenid() {
        return tokenid;
    }

    public void setTokenid(Integer tokenid) {
        this.tokenid = tokenid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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
