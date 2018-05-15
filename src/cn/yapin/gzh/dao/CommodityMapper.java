package cn.yapin.gzh.dao;


import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.Commodity;

import java.util.List;

public interface CommodityMapper {
    void deleteByPrimaryKey(Commodity record) throws Exception;

    void insert(Commodity record) throws Exception;

    int insertSelective(Commodity record) throws Exception;

    Commodity selectByPrimaryKey(String id) throws Exception;

    Commodity selectCommodityCarryClassification(String id) throws Exception;

    List<Commodity> selectCommodityArray() throws Exception;

    void updateByPrimaryKeySelective(Commodity record) throws Exception;

    void updateByPrimaryKey(Commodity record) throws Exception;

    List<Commodity> callCommodityArray(BasePage basePage) throws Exception;
}