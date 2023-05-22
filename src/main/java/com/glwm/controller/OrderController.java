package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.R;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.User;
import com.glwm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/page")
    public R<Page> selectPageOrder(Integer page, Integer pageSize, String number, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        //判断当前页
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        List<OrderAndAddress> orderAndAddresses = orderService.selectPageOrder(user.getMerchantId(), pageNum, pageSize, number, beginTime, endTime);

        Page<OrderAndAddress> orderAndAddressPage = new Page<>(page, pageSize);
        orderAndAddressPage.setRecords(orderAndAddresses);
        Long aLong = orderService.selectTotal(user.getMerchantId(), number, beginTime, endTime);
        orderAndAddressPage.setTotal(aLong);
        return R.success(orderAndAddressPage);
    }
}
