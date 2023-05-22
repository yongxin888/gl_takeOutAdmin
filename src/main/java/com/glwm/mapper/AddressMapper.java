package com.glwm.mapper;

import com.glwm.dto.AddressAndUser;
import com.glwm.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    //查询用户默认地址
    AddressAndUser selectAddressAndUser(Long userId);

    //根据用户ID查询所有地址
    List<AddressAndUser> selectAddressList(Long id);

    //根据ID更改地址默认值
    void updateAddressDefault(Integer isDefault, Long id, Long userId);
}
