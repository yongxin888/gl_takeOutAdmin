package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.glwm.common.MD5Utils;
import com.glwm.common.R;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Address;
import com.glwm.entity.Order;
import com.glwm.entity.User;
import com.glwm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *客户端用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody User user) {
        String phone = user.getPhone();
        String code = MD5Utils.code(user.getPassword());

        User loginUser = userService.loginUser(phone, code);

        if (loginUser != null) {
            //将用户信息存入redis
            redisTemplate.opsForValue().set("userClient",  JSON.toJSONString(loginUser));
            return R.success(loginUser);
        }

        return R.error("登录失败!");
    }

    /**
     * 客户端登录后查询其所有订单
     * @return
     */
    @PostMapping("/loginOrder")
    public R<List<OrderAndAddress>> loginOrder() {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User userClient = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        List<OrderAndAddress> order = userService.loginOrder(userClient.getId());
        return R.success(order);
    }

    /**
     * 客户端登录后查询其所有地址
     * @return
     */
    @PostMapping("/loginAddress")
    public R<List<Address>> loginAddress() {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User userClient = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        List<Address> addresses = userService.loginAddress(userClient.getId());
        return R.success(addresses);
    }

    /**
     * 客户端新增用户地址
     * @param address
     * @return
     */
    @PostMapping("/addAddress")
    public R<String> addAddress(@RequestBody Address address) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User userClient = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        Long id = userClient.getId();
        address.setUserId(id);
        userService.addAddress(address);
        return R.success("新增成功");
    }
}
