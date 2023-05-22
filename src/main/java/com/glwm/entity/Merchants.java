package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "merchants")
public class Merchants implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //店名
    private String name;
    //联系电话
    private String phone;
    //商铺Logo
    private String shopPhoto;
    //商铺地址
    private String shopAddress;
    //商铺分类
    private String shopSort;
    //是否免配送 0 否 1 是
    private Integer distribution;
    //留言
    private String message;
    //起送价格
    private Double minPrice;
    //用户ID
    private Long userId;
    //状态 0 停业 1 营业
    private Integer status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
