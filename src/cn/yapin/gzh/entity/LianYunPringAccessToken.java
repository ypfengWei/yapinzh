package cn.yapin.gzh.entity;

import java.io.Serializable;

//全局易联云打印机接口调用凭据access_token
public class LianYunPringAccessToken implements Serializable {
    private Integer tokenid;
    private String machine_code;//易联云终端号
    private String access_token;
    private String refresh_token;
    private int expires_in;//access_token有效时间（单位:秒）
    private long create_date;//创建时间（单位:毫秒）

    public LianYunPringAccessToken() {
    }

    public LianYunPringAccessToken(String access_token, String refresh_token, int expires_in, long create_date) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.create_date = create_date;
    }

    public LianYunPringAccessToken(String machine_code, String access_token, String refresh_token, int expires_in, long create_date) {
        this.machine_code = machine_code;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.create_date = create_date;
    }

    public Integer getTokenid() {
        return tokenid;
    }

    public void setTokenid(Integer tokenid) {
        this.tokenid = tokenid;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
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
}
