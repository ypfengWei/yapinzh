package cn.yapin.gzh.dao;


import cn.yapin.gzh.entity.CommodityClassification;

import java.util.List;

public interface CommodityClassificationMapper {
    void deleteByPrimaryKey(Integer id) throws Exception;

    void insert(CommodityClassification record) throws Exception;

    int checkExist(String commodityType) throws Exception;

    void insertSelective(CommodityClassification record) throws Exception;

    //查询所以类别
    List<CommodityClassification> selectAll() throws Exception;

    void updateByPrimaryKeySelective(CommodityClassification record) throws Exception;

    void updateByPrimaryKey(CommodityClassification record) throws Exception;

    //查询全部有商品的类别
    List<CommodityClassification> getCommodityClassifications() throws Exception;
}