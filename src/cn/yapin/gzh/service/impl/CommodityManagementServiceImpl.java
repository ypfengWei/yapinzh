package cn.yapin.gzh.service.impl;

import cn.yapin.gzh.dao.CommodityAssociationClassificationMapper;
import cn.yapin.gzh.dao.CommodityClassificationMapper;
import cn.yapin.gzh.dao.CommodityMapper;
import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.Commodity;
import cn.yapin.gzh.entity.CommodityAssociationClassification;
import cn.yapin.gzh.entity.CommodityClassification;
import cn.yapin.gzh.service.ICommodityManagementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommodityManagementServiceImpl implements ICommodityManagementService {
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private CommodityClassificationMapper commodityClassificationMapper;
    @Autowired
    private CommodityAssociationClassificationMapper commodityAssociationClassificationMapper;

    @Override
    public void deleteCommodity(Commodity commodity) throws Exception {
        commodityMapper.deleteByPrimaryKey(commodity);
        commodityAssociationClassificationMapper.deleteCommodityRecord(commodity);
    }

    @Override
    public void insertCommodity(Commodity record, CommodityAssociationClassification commodityAssociationClassification) throws Exception {
        commodityMapper.insert(record);
        commodityAssociationClassificationMapper.insert(commodityAssociationClassification);
    }

    @Override
    public void insertCommoditySelective(Commodity record, CommodityAssociationClassification commodityAssociationClassification) throws Exception {
        commodityMapper.insertSelective(record);
        commodityAssociationClassificationMapper.insert(commodityAssociationClassification);
    }

    @Override
    public Commodity selectCommodityByID(String id) {
        try {
            return commodityMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Commodity selectCommodityCarryClassification(String id) {
        try {
            return commodityMapper.selectCommodityCarryClassification(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCommodityByID(Commodity record) throws Exception {
        commodityMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateCommoditySelectiveByID(Commodity record) throws Exception {
        commodityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void updateCommodityCarryClassification(Commodity record, List<CommodityAssociationClassification> commodityClassificationList) throws Exception {
        commodityMapper.updateByPrimaryKeySelective(record);
        commodityAssociationClassificationMapper.deleteCommodityRecord(record);
        commodityAssociationClassificationMapper.batchInsert(commodityClassificationList);
    }

    @Override
    public void deleteCommodityClassification(CommodityClassification commodityClassification) throws Exception {
        commodityClassificationMapper.deleteByPrimaryKey(commodityClassification.getId());
    }

    @Override
    public void insertCommodityClassification(CommodityClassification record) throws Exception {
        commodityClassificationMapper.insert(record);
    }

    @Override
    public void insertCommodityClassificationSelective(CommodityClassification record) throws Exception {
        commodityClassificationMapper.insertSelective(record);
    }

    @Override
    public List<CommodityClassification> selectCommodityClassificationAll() {
        try {
            return commodityClassificationMapper.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CommodityClassification> selectCommodityClassifications() {
        try {
            return commodityClassificationMapper.getCommodityClassifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCommodityClassificationByID(CommodityClassification record) throws Exception {
        commodityClassificationMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateCommodityClassificationSelectiveByID(CommodityClassification record) throws Exception {
        commodityClassificationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int checkCommodityClassificationExist(String commodityType) {
        try {
            return commodityClassificationMapper.checkExist(commodityType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deleteCommodityLinkClassification(CommodityAssociationClassification commodityAssociationClassification) throws Exception {
        commodityAssociationClassificationMapper.deleteByPrimaryKey(commodityAssociationClassification);
    }

    @Override
    public List<Commodity> callCommodityArray(BasePage basePage) {
        try {
            return commodityMapper.callCommodityArray(basePage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
