package com.glwm.mapper;

import com.glwm.dto.MerchantsAndUser;
import com.glwm.dto.MerchantsAndUserPage;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Address;
import com.glwm.entity.Merchants;
import com.glwm.entity.Order;
import com.glwm.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //登录功能查询
    User Login(String username, String password);

    //分页查询商户数据
    List<MerchantsAndUserPage> pageMerchants(@Param("name") String name, Integer page, Integer pageSize);

    //查询总记录数
    Integer pageTotal(String name);

    //插入用户数据
    void insertUser(User user);

    //插入商户数据
    void insertMerchants(MerchantsAndUser merchantsAndUser);

    //根据ID查询商户数据
    MerchantsAndUser selectMerchantsAndUser(Long id);

    //根据ID修改商户状态
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
