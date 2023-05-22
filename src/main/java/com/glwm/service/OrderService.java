package com.glwm.service;

import com.glwm.dto.DishAndOrder;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    //分页查询订单
    List<OrderAndAddress> selectPageOrder(Long merchantId, Integer page, Integer pageSize, String number, Date beginTime,Date endTime);

    //查询订单总条数
    Long selectTotal(Long merchantId, String number, Date beginTime,Date endTime);

    //客户端提交订单
    void submitOrder(Order order);

    //客户端根据商家ID查询订单
    List<DishAndOrder> selectUserOrderMerchant(Long userId, Long merchantId, Integer page, Integer pageSize);
}
