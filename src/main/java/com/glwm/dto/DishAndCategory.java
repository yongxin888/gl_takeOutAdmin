package com.glwm.dto;

import com.glwm.entity.Dish;
import com.glwm.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DishAndCategory extends Dish implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    private List<DishFlavor> flavors;
    private String categoryName;
}
