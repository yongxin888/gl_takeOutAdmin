package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.R;
import com.glwm.dto.DishAndOrder;
import com.glwm.entity.Order;
import com.glwm.entity.User;
import com.glwm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/order")
public class OrderClientController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 下单商品
     * @param order
     * @return
     */
    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody Order order) {
        orderService.submitOrder(order);
        return R.success("下单成功");
    }

    /**
     * 分页查询用户在某个商户的订单
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPageOrder(int page, int pageSize, Long merchantId) {
        //判断当前页
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        List<DishAndOrder> dishAndOrders = orderService.selectUserOrderMerchant(user.getId(), merchantId, pageNum, pageSize);
        Page<DishAndOrder> orderPage = new Page<>(page, pageSize);
        orderPage.setRecords(dishAndOrders);
        return R.success(orderPage);
    }
}
