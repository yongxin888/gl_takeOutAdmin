package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.glwm.common.R;
import com.glwm.dto.AddressAndUser;
import com.glwm.entity.User;
import com.glwm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressClientController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressAndUser> selectAddress() {
        AddressAndUser addressAndUsers = addressService.selectAddressAndUser();
        return R.success(addressAndUsers);
    }

    /**
     * 设置默认地址
     * @param addressAndUser
     * @return
     */
    @PutMapping("/default")
    public R<AddressAndUser> selectAddressDefault(@RequestBody AddressAndUser addressAndUser) {
        Long id = addressAndUser.getId();
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        addressService.updateAddressDefault(0, null, user.getId());
        addressService.updateAddressDefault(1, id, null);
        return R.success(addressAndUser);
    }

    /**
     * 根据用户ID查询所有用户地址
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressAndUser>> selectAddressList() {
        List<AddressAndUser> addressAndUsers = addressService.selectAddressList(1L);
        return R.success(addressAndUsers);
    }
}
