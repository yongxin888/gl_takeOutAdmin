package com.glwm.dto;

import com.glwm.entity.Order;
import com.glwm.entity.OrderDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DishAndOrder extends Order implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    private String name;
    private String image;
    private List<OrderDetail> orderDetailList;
}
