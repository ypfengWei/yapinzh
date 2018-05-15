package cn.yapin.gzh.service;

import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.Commodity;
import cn.yapin.gzh.entity.CommodityAssociationClassification;
import cn.yapin.gzh.entity.CommodityClassification;

import java.util.List;

public interface ICommodityManagementService {
    /*商品表crud*/

    /**
     * 删除商品
     */
    void deleteCommodity(Commodity commodity) throws Exception;

    /**
     * 新建商品（字段非空）
     */
    void insertCommodity(Commodity record, CommodityAssociationClassification commodityAssociationClassification) throws Exception;

    /**
     * 新建商品（字段可空）
     */
    void insertCommoditySelective(Commodity record, CommodityAssociationClassification commodityAssociationClassification) throws Exception;

    /**
     * 查询单个商品
     */
    Commodity selectCommodityByID(String id);

    Commodity selectCommodityCarryClassification(String id);

    /**
     * 更新单个商品（字段非空）
     */
    void updateCommodityByID(Commodity record) throws Exception;

    /**
     * 更新单个商品（字段可空）
     */
    void updateCommoditySelectiveByID(Commodity record) throws Exception;


    /*商品类别表crud*/

    void updateCommodityCarryClassification(Commodity record, List<CommodityAssociationClassification> commodityClassificationList) throws Exception;

    /**
     * 删除单个类别
     */
    void deleteCommodityClassification(CommodityClassification commodityClassification) throws Exception;

    /**
     * 新建类别（字段非空）
     */
    void insertCommodityClassification(CommodityClassification record) throws Exception;

    /**
     * 新建类别（字段可空）
     */
    void insertCommodityClassificationSelective(CommodityClassification record) throws Exception;

    /**
     * 查询所有类别
     */
    List<CommodityClassification> selectCommodityClassificationAll();

    /**
     * 查询有商品的类别列表
     */
    List<CommodityClassification> selectCommodityClassifications();

    /**
     * 更新单个类别（字段非空）
     */
    void updateCommodityClassificationByID(CommodityClassification record) throws Exception;

    /**
     * 更新单个类别（字段可空）
     */
    void updateCommodityClassificationSelectiveByID(CommodityClassification record) throws Exception;

    /**
     * 检查类别名是否已存在
     */
    int checkCommodityClassificationExist(String commodityType);

    /*商品与类别关联表crud*/

    /**
     * 删除一个商品分类关联表条目
     */
    void deleteCommodityLinkClassification(CommodityAssociationClassification commodityAssociationClassification) throws Exception;

    /**
     * 根据类别id查询类别下的商品
     */
    List<Commodity> callCommodityArray(BasePage basePage);

}
