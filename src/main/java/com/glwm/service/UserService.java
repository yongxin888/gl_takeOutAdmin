package com.glwm.service;

import com.glwm.dto.MerchantsAndUser;
import com.glwm.dto.MerchantsAndUserPage;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Address;
import com.glwm.entity.Merchants;
import com.glwm.entity.Order;
import com.glwm.entity.User;

import java.util.List;

public interface UserService {
    //登录
    User Login(String username, String password);

    //分页查询商户
    List<MerchantsAndUserPage> pageMerchants(String name, Integer page, Integer pageSize);

    //查询总记录数
    Integer pageTotal(String name);

    //插入用户数据
    void insertUserAndMerchants(MerchantsAndUser merchantsAndUser);

    //根据ID查询商户数据
    MerchantsAndUser selectMerchantsAndUser(Long id);

    //根据ID修改状态
    void updateStatus(Merchants merchants);

    //客户端用户登录
    User loginUser(String phone, String password);

    //客户端登录后查询其所有订单
    List<OrderAndAddress> loginOrder(Long userId);

    //客户端登陆后查询其地址
    List<Address> loginAddress(Long userId);

    //客户端新增用户地址
    void addAddress(Address address);
}
