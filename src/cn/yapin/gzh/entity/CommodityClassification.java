package cn.yapin.gzh.entity;

import java.io.Serializable;
import java.util.Date;

public class CommodityClassification implements Serializable {
    private Integer id;

    private String commodityType;

    private String remarks;

    private Date createDate;

    private Date updateDate;

    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityType() {
        return commodityType;
    }

    public CommodityClassification() {
    }

    public CommodityClassification(String commodityType) {
        this.commodityType = commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType == null ? null : commodityType.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CommodityClassification other = (CommodityClassification) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}