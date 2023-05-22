package com.glwm.dto;

import com.glwm.entity.Address;
import com.glwm.entity.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAndAddress extends Order implements Serializable {
    //地址
    private Address address;
    //用户名
    private String username;
    //商家名字
    private String name;
    //商家图片
    private String shopAddress;
    //昵称
    private String nickName;
    //用户头像
    private String headPhoto;
}
