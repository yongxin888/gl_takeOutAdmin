package com.glwm.service.impl;

import com.glwm.common.MD5Utils;
import com.glwm.dto.MerchantsAndUser;
import com.glwm.dto.MerchantsAndUserPage;
import com.glwm.dto.OrderAndAddress;
import com.glwm.entity.Address;
import com.glwm.entity.Merchants;
import com.glwm.entity.Order;
import com.glwm.entity.User;
import com.glwm.mapper.UserMapper;
import com.glwm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User Login(String username, String password) {
        User user = userMapper.Login(username, password);
        return user;
    }

    /**
     * 分页查询商户
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<MerchantsAndUserPage> pageMerchants(String name, Integer page, Integer pageSize) {
        return userMapper.pageMerchants(name, page, pageSize);
    }

    /**
     * 查询总记录数
     * @return
     */
    @Override
    public Integer pageTotal(String name) {
        return userMapper.pageTotal(name);
    }

    /**
     * 新增商户以及用户数据
     * @param merchantsAndUser
     */
    @Transactional
    @Override
    public void insertUserAndMerchants(MerchantsAndUser merchantsAndUser) {
        User user = new User();
        //设置默认密码并进行MD5加密
        String code = MD5Utils.code("123456");
        user.setUsername(merchantsAndUser.getUsername());
        user.setPassword(code);
        user.setRole(1);
        //user.setUpdate_time(LocalDateTime.now());
        userMapper.insertUser(user);
        merchantsAndUser.setCreateTime(new Date());
        merchantsAndUser.setUpdateTime(new Date());
        merchantsAndUser.setUserId(user.getId());
        //截取图片名称
        String shopPhoto = merchantsAndUser.getShopPhoto();
        String suffix = shopPhoto.substring(shopPhoto.lastIndexOf("=") + 1);
        merchantsAndUser.setShopPhoto(suffix);
        userMapper.insertMerchants(merchantsAndUser);
    }

    /**
     * 根据ID查询商户数据
     * @param id
     * @return
     */
    @Override
    public MerchantsAndUser selectMerchantsAndUser(Long id) {
        return userMapper.selectMerchantsAndUser(id);
    }

    /**
     * 根据ID修改状态
     * @param merchants
     */
    @Override
    public void updateStatus(Merchants merchants) {
        userMapper.updateStatus(merchants);
    }

    /**
     * 客户端用户登录
     * @param phone
     * @param password
     * @return
     */
    @Override
    public User loginUser(String phone, String password) {
        return userMapper.loginUser(phone, password);
    }

    /**
     * 客户端登录后查询其所有订单
     * @param userId
     * @return
     */
    @Override
    public List<OrderAndAddress> loginOrder(Long userId) {
        return userMapper.loginOrder(userId);
    }

    /**
     * 客户端登陆后查询其地址
     * @param userId
     * @return
     */
    @Override
    public List<Address> loginAddress(Long userId) {
        return userMapper.loginAddress(userId);
    }

    /**
     * 客户端新增用户地址
     * @param address
     */
    @Override
    public void addAddress(Address address) {
        address.setIsDefault(0);
        userMapper.addAddress(address);
    }

}
