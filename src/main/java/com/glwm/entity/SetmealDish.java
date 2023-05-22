package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("setmeal_dish")
public class SetmealDish implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键ID
    private Long id;
    //套餐ID
    private Long setmealId;
    //菜品ID
    private Long dishId;
    //菜品名称（冗余字段）
    private String name;
    //菜品价格（冗余字段）
    private Double price;
    //份数
    private Integer copies;
    //排序
    private Integer sort;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
