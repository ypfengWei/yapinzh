package cn.yapin.gzh.service.impl;

import cn.yapin.gzh.dao.SellersManagerMapper;
import cn.yapin.gzh.entity.Sellers;
import cn.yapin.gzh.service.ISellersManagerService;
import org.springframework.beans.factory.annotation.Autowired;

public class SellersManagerServiceImpl implements ISellersManagerService {
    @Autowired
    private SellersManagerMapper sellersManagerMapper;

    @Override
    public Sellers login(Sellers sellers) {
        try {
            return sellersManagerMapper.getSellers(sellers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addSellers(Sellers sellers) throws Exception {
        sellersManagerMapper.insertSellers(sellers);
    }
}
