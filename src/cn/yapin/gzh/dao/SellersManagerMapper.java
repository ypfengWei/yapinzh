package cn.yapin.gzh.dao;

import cn.yapin.gzh.entity.Sellers;

public interface SellersManagerMapper {
    void insertSellers(Sellers sellers) throws Exception;
    Sellers getSellers(Sellers sellers) throws Exception;
}
