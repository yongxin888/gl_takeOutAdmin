package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.dto.AddressAndUser;
import com.glwm.entity.Address;
import com.glwm.entity.User;
import com.glwm.mapper.AddressMapper;
import com.glwm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询用户默认地址
     * @return
     */
    @Override
    public AddressAndUser selectAddressAndUser() {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        return addressMapper.selectAddressAndUser(user.getId());
    }

    /**
     * 根据用户ID查询所有用户地址
     * @param id
     * @return
     */
    @Override
    public List<AddressAndUser> selectAddressList(Long id) {
        return addressMapper.selectAddressList(id);
    }

    /**
     * 根据ID更改地址默认值
     * @param isDefault
     * @param id
     */
    @Override
    public void updateAddressDefault(Integer isDefault, Long id, Long userId) {
        addressMapper.updateAddressDefault(isDefault, id, userId);
    }
}
