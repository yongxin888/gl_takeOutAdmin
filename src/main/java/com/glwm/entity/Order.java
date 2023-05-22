package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("order")
public class Order implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //订单号
    private String number;
    //订单状态 0 待付款 1 派送中 2 已派送 3 已完成 4 已取消
    private Integer status;
    //用户ID
    private Long userId;
    //地址ID
    private Long addressId;
    //下单时间
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orderTime;
    //支付时间
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date checkoutTime;
    //支付方式
    private Integer payMethod;
    //实收金额
    private Double amount;
    //商户ID
    private Long merchantId;
    //手机号
    private String phone;
    //备注
    private String remark;
}
