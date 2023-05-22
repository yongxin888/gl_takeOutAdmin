package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("setmeal")
public class Setmeal implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //菜品分类
    private Long categoryId;
    //商户ID
    private Long merchantId;
    //套餐名称
    private String name;
    //套餐价格
    private Double price;
    //状态 0 停售 1 起售
    private Integer status;
    //描述信息
    private String description;
    //图片
    private String image;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
