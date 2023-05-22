package com.glwm.mapper;

import com.glwm.dto.DishAndOrder;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Order;
import com.glwm.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    //分页查询所有订单
    List<OrderAndAddress> selectPageOrder(Long merchantId, Integer page, Integer pageSize, String number, Date beginTime,Date endTime);

    //查询订单总条数
    Long selectTotal(Long merchantId, String number, Date beginTime,Date endTime);

    //客户端提交并支付订单
    void insertOrder(Order order);

    //客户端提交订单新增订单详情表
    void insertOrderDetail(List<OrderDetail> orderDetail);

    //客户端根据商家ID查询订单
    List<DishAndOrder> selectUserOrderMerchant(Long userId, Long merchantId, Integer page, Integer pageSize);
}
