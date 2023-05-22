package com.glwm.dto;

import com.glwm.entity.Merchants;
import com.glwm.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantsAndUserPage implements Serializable {
    //商户数据
    private Merchants merchants;
    //用户数据
    private User user;
    //当前页码
    private Integer currentPage;
    //每页记录数
    private Integer pageSize;
    //总页数
    private Long totalPages;
    //总记录数
    private Long total;
}
