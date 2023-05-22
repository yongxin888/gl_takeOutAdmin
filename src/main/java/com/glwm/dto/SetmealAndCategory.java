package com.glwm.dto;

import com.glwm.entity.Dish;
import com.glwm.entity.Setmeal;
import com.glwm.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SetmealAndCategory extends Setmeal implements Serializable {
    //serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;
    //菜品分类
    private String categoryName;
    //菜品
    private List<Dish> dishList;
    //份数
    private Integer copies;
    //套餐菜品
    private List<SetmealDish> setmealDishes;
}
