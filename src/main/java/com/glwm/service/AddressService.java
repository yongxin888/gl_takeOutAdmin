package com.glwm.service;

import com.glwm.dto.AddressAndUser;
import com.glwm.entity.Address;

import java.util.List;

public interface AddressService {
    //查询用户地址
    AddressAndUser selectAddressAndUser();

    //根据用户ID查询所有用户地址
    List<AddressAndUser> selectAddressList(Long id);

    //根据ID更改地址默认值
    void updateAddressDefault(Integer isDefault, Long id, Long userId);
}
