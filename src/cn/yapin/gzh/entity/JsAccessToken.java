package cn.yapin.gzh.entity;

import java.io.Serializable;

//微信网页js接口调用凭据
public class JsAccessToken implements Serializable {
    private Integer js_tokenid;
    private String openid;
    private String access_token;
    private String refresh_token;
    private String unionid;//微信开放平台绑定应用后唯一字符串
    private int expires_in;//access_token接口调用凭证超时时间，单位（秒）
    private long create_date;//保存access_token的当前的计算机时间（毫秒）

    public Integer getJs_tokenid() {
        return js_tokenid;
    }

    public void setJs_tokenid(Integer js_tokenid) {
        this.js_tokenid = js_tokenid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
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

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
