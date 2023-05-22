package com.glwm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * Serializable 序列化；序列化是将对象状态转化为可保持或者传输的格式过程，与序列化相反的是反序列化，完成序列化和反序列化，可以存储或传输数据
 */
@Data
@TableName(value = "user")
public class User implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;
    //手机号
    private String phone;
    //性别
    private Integer sex;
    //头像
    private String headPhoto;
    //账户余额
    private Double accountBalance;
    //账户积分
    private Integer accountIntegral;
    //角色 0 管理员 1 商户 2 用户
    private Integer role;
    //商户ID
    private Long merchantId;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
