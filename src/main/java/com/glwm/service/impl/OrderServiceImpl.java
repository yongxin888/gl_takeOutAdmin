package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.common.CustomException;
import com.glwm.dto.AddressAndUser;
import com.glwm.dto.DishAndOrder;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Order;
import com.glwm.entity.OrderDetail;
import com.glwm.entity.ShoppingCart;
import com.glwm.entity.User;
import com.glwm.mapper.AddressMapper;
import com.glwm.mapper.OrderMapper;
import com.glwm.mapper.ShoppingCartMapper;
import com.glwm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页模糊查询订单
     * @param merchantId
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<OrderAndAddress> selectPageOrder(Long merchantId, Integer page, Integer pageSize, String number, Date beginTime, Date endTime) {
        return orderMapper.selectPageOrder(merchantId, page, pageSize, number, beginTime, endTime);
    }

    /**
     * 查询订单总条数
     * @param merchantId
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Long selectTotal(Long merchantId, String number, Date beginTime, Date endTime) {
        return orderMapper.selectTotal(merchantId, number, beginTime, endTime);
    }

    /**
     * 客户端提交订单
     * @param order
     */
    @Override
    @Transactional
    public void submitOrder(Order order) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        //查询指定用户的购物车数据
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectUserIDShoppingCart(user.getId(), order.getMerchantId());
        //如果购物车为空，不能下单
        if (shoppingCarts == null) {
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户的默认下单地址
        AddressAndUser addressAndUser = addressMapper.selectAddressAndUser(user.getId());

        //生成一个订单号
        String replace = UUID.randomUUID().toString().replace("-", "");

        AtomicReference<Double> amount = new AtomicReference<>(0.00);

        //将数据填充到口味表中
        List<OrderDetail> orderDetailList = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setNumber(replace);
            orderDetail.setDishId(item.getDishId());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setMoney(item.getAmount());
            orderDetail.setAmount(item.getNumber());
            amount.updateAndGet(v -> v + item.getAmount());   //累加金额
            return orderDetail;
        }).collect(Collectors.toList());

        //将数据填充到订单中
        order.setNumber(replace);
        order.setStatus(1);
        order.setUserId(user.getId());
        order.setAddressId(addressAndUser.getId());
        order.setAmount(amount.get());
        order.setPhone(addressAndUser.getPhone());

        //提交订单
        orderMapper.insertOrderDetail(orderDetailList);
        orderMapper.insertOrder(order);
    }

    /**
     * 客户端根据商家ID以及用户ID查询订单
     * @param userId
     * @param merchantId
     * @return
     */
    @Override
    public List<DishAndOrder> selectUserOrderMerchant(Long userId, Long merchantId, Integer page, Integer pageSize) {
        return orderMapper.selectUserOrderMerchant(userId, merchantId, page, pageSize);
    }
}
