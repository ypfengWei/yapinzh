package cn.yapin.gzh.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Commodity implements Serializable {
    private String id;

    private String name;

    private String introducePicture;

    private String introduceDetails;

    private Double price;

    private Short discount;

    private String remarks;

    private Date createDate;

    private Date updateDate;

    private Boolean visible;

    private Set<CommodityClassification> commodityClassificationSet = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroducePicture() {
        return introducePicture;
    }

    public void setIntroducePicture(String introducePicture) {
        this.introducePicture = introducePicture == null ? null : introducePicture.trim();
    }

    public String getIntroduceDetails() {
        return introduceDetails;
    }

    public void setIntroduceDetails(String introduceDetails) {
        this.introduceDetails = introduceDetails == null ? null : introduceDetails.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
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

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Set<CommodityClassification> getCommodityClassificationSet() {
        return commodityClassificationSet;
    }

    public void setCommodityClassificationSet(Set<CommodityClassification> commodityClassificationSet) {
        this.commodityClassificationSet = commodityClassificationSet;
    }
}