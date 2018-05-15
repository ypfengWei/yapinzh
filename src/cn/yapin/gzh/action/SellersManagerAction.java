package cn.yapin.gzh.action;

import cn.yapin.gzh.entity.Sellers;
import cn.yapin.gzh.service.ISellersManagerService;
import cn.yapin.gzh.utils.WebUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SellersManagerAction extends BaseAction {
    private Sellers sellers;
    private ISellersManagerService sellersManagerService;
    private String sellersId;

    public void setSellersManagerService(ISellersManagerService sellersManagerService) {
        this.sellersManagerService = sellersManagerService;
    }

    public String getSellersId() {
        return sellersId;
    }

    public void setSellersId(String sellersId) {
        this.sellersId = sellersId;
    }

    public Sellers getSellers() {
        return sellers;
    }

    public void setSellers(Sellers sellers) {
        this.sellers = sellers;
    }

    public String login() {
        if (sellers != null) {
            try {
                String decodePwd = new String(WebUtils.decode(sellers.getLoginPassword()));
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(decodePwd.getBytes());
                sellers.setLoginPassword(new BigInteger(md.digest()).toString(32));
                Sellers findSellers = sellersManagerService.login(sellers);
                if (findSellers != null) {
                    respJSON.put("sellersId", findSellers.getSellersId());
                    respJSON.put("success", true);
                    return SUCCESS;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        respJSON.put("success", false);
        return ERROR;
    }
}
