package cn.yapin.gzh.entity;

import java.io.Serializable;

public class CommodityAssociationClassification implements Serializable {

    private String commodityId;

    private Integer classificationId;

    public CommodityAssociationClassification() {
    }

    public CommodityAssociationClassification(String commodityId, Integer classificationId) {
        this.commodityId = commodityId;
        this.classificationId = classificationId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
        result = prime * result + ((classificationId == null) ? 0 : classificationId.hashCode());
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
        final CommodityAssociationClassification other = (CommodityAssociationClassification) obj;
        if (commodityId == null) {
            if (other.commodityId != null)
                return false;
        } else if (!commodityId.equals(other.commodityId))
            return false;
        if (classificationId == null) {
            if (other.classificationId != null)
                return false;
        } else if (!classificationId.equals(other.classificationId))
            return false;
        return true;
    }
}