package com.glwm.dto;

import com.glwm.entity.Merchants;
import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantsAndUser extends Merchants implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    private String username;
}
