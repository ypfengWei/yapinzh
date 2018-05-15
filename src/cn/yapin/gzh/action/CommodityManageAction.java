package cn.yapin.gzh.action;

import cn.yapin.gzh.entity.BasePage;
import cn.yapin.gzh.entity.Commodity;
import cn.yapin.gzh.entity.CommodityAssociationClassification;
import cn.yapin.gzh.entity.CommodityClassification;
import cn.yapin.gzh.service.ICommodityManagementService;
import cn.yapin.gzh.utils.Rename_PIC;
import cn.yapin.gzh.utils.UrlTools;
import cn.yapin.gzh.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static cn.yapin.gzh.utils.WebUtils.logResult;

public class CommodityManageAction extends BaseAction {
    private Commodity commodity;
    private CommodityClassification commodityClassification;
    private String media_id;
    private ICommodityManagementService commodityManagementService;
    private String commodityId;
    private String classifications;

    public String getClassifications() {
        return classifications;
    }

    public void setClassifications(String classifications) {
        this.classifications = classifications;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public CommodityClassification getCommodityClassification() {
        return commodityClassification;
    }

    public void setCommodityClassification(CommodityClassification commodityClassification) {
        this.commodityClassification = commodityClassification;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public void setCommodityManagementService(ICommodityManagementService commodityManagementService) {
        this.commodityManagementService = commodityManagementService;
    }

    public String addClassify() {
        int count = commodityManagementService.checkCommodityClassificationExist(commodityClassification.getCommodityType());
        if (count == 0) {
            CommodityClassification classification = new CommodityClassification(commodityClassification.getCommodityType());
            try {
                commodityManagementService.insertCommodityClassificationSelective(classification);
                respJSON.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        respJSON.put("msg", "类名已存在");
        return ERROR;
    }

    /*查询有商品的类别列表*/
    public String getClassify() {
        List<CommodityClassification> list = commodityManagementService.selectCommodityClassifications();
        if (!list.isEmpty()) {
            respJSON.put("classify", JSON.toJSON(list));
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }

    /*查询所有类别列表*/
    public String getClassifyAll() {
        List<CommodityClassification> list = commodityManagementService.selectCommodityClassificationAll();
        if (!list.isEmpty()) {
            respJSON.put("classify", JSON.toJSON(list));
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }

    //上传商品
    public String insertCommodity() {
        if (commodity != null && commodityClassification != null) {
            String imageName = Rename_PIC.renameUtil() + ".jpg";
            commodity.setIntroducePicture(imageName);
            String commodityid = UUID.randomUUID().toString();
            commodity.setId(commodityid);
            commodity.setVisible(true);
            CommodityAssociationClassification commodityAssociationClassification = new CommodityAssociationClassification();
            commodityAssociationClassification.setCommodityId(commodityid);
            commodityAssociationClassification.setClassificationId(commodityClassification.getId());
            try {
                commodityManagementService.insertCommoditySelective(commodity, commodityAssociationClassification);
                WebUtils.downloadImage(getAccessToken().getAccess_token(), media_id, UrlTools.PICTURE_DIR, imageName);
                respJSON.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                logResult(e.getMessage());
            }

        }
        respJSON.put("success", false);
        return ERROR;
    }

    //分页获取全部类别菜品
    public String getCommodityArray() {
        BasePage basePage = new BasePage();
        basePage.setFields("commodity.id,commodity.`name`,commodity.price,commodity.introduce_picture,commodity.introduce_details,visible");
        basePage.setTables("commodity");
        if (commodityClassification == null || commodityClassification.getId() == 0) {
            basePage.setWheres("");
        } else {
            basePage.setWheres("visible=1 AND id in (select commodity_id from commodity_association_classification where classification_id =" + commodityClassification.getId() + ")");
        }
        basePage.setGrops("");
        basePage.setPageindex(pageIndex);
        basePage.setPageCount(pageCount);
        basePage.setOrders("commodity.create_date");
        try {
            List<Commodity> orders = commodityManagementService.callCommodityArray(basePage);
            if (orders != null && !orders.isEmpty()) {
                respJSON.put("commodityArray", ((JSONArray) JSON.toJSON(orders)).toJSONString());
                respJSON.put("totalPage", basePage.getTotalPage());
                respJSON.put("totalSize", basePage.getTotalSize());
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String loadCommodity() {
        Commodity commodity = commodityManagementService.selectCommodityCarryClassification(commodityId);
        if (commodity == null) {
            commodity = commodityManagementService.selectCommodityByID(commodityId);
        }
        if (commodity != null) {
            respJSON.put("commodity", JSON.toJSON(commodity));
            respJSON.put("success", true);
            return SUCCESS;
        }
        respJSON.put("success", false);
        return ERROR;
    }

    public String commodityVisible() {
        if (commodity != null) {
            try {
                commodityManagementService.updateCommoditySelectiveByID(commodity);
                respJSON.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }

    public String updateCommodity() {
        String imgName = Rename_PIC.renameUtil() + ".jpg";
        Commodity foundCommodity = commodityManagementService.selectCommodityByID(commodity.getId());
        String oldimg = foundCommodity.getIntroducePicture();
        if (foundCommodity != null) {
            foundCommodity.setDiscount(commodity.getDiscount());
            foundCommodity.setName(commodity.getName());
            foundCommodity.setIntroduceDetails(commodity.getIntroduceDetails());
            foundCommodity.setPrice(commodity.getPrice());
            try {
                if (!"".equals(media_id)) {
                    foundCommodity.setIntroducePicture(imgName);
                }
                commodityManagementService.updateCommodityCarryClassification(foundCommodity, JSON.parseArray(classifications, CommodityAssociationClassification.class));
                if (!"".equals(media_id)) {
                    WebUtils.downloadImage(getAccessToken().getAccess_token(), media_id, UrlTools.PICTURE_DIR, imgName);
                    File file = new File(UrlTools.PICTURE_DIR, oldimg);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                respJSON.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }

    public String deleteCommodity() {
        Commodity foundCommodity = commodityManagementService.selectCommodityByID(commodity.getId());
        try {
            commodityManagementService.deleteCommodity(foundCommodity);
            File file = new File(UrlTools.PICTURE_DIR, foundCommodity.getIntroducePicture());
            if (file.exists()) {
                file.delete();
            }
            respJSON.put("success", true);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        respJSON.put("success", false);
        return ERROR;
    }

}
