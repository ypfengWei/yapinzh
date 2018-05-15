package cn.yapin.gzh.service;

import cn.yapin.gzh.entity.Sellers;

public interface ISellersManagerService {
    Sellers login(Sellers sellers);

    void addSellers(Sellers sellers) throws Exception;
}
