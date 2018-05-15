package cn.yapin.gzh.entity;

public class DiningLocation {
    private String openid;
    private String seat_number;

    public DiningLocation() {
    }

    public DiningLocation(String openid, String seat_number) {
        this.openid = openid;
        this.seat_number = seat_number;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }
}
