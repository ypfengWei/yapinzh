package cn.yapin.gzh.dao;


import cn.yapin.gzh.entity.OrderDetails;

import java.util.List;

public interface OrderDetailsMapper {
    void deleteByPrimaryKey(Integer id);

    void insert(OrderDetails record);

    void insertSelective(OrderDetails record);

    void batchInsert(List<OrderDetails> record);

    List<OrderDetails> selectByPrimaryKey(String id);
}