package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("order_detail")
public class OrderDetail implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //订单号
    private String number;
    //菜品ID
    private Long dishId;
    //菜品口味ID
    private String dishFlavor;
    //套餐ID
    private Long setmealId;
    //金额
    private Double money;
    //数量
    private Integer amount;
    //菜品名称（冗余字段）
    private String dishName;
}
