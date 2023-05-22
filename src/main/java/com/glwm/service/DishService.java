package com.glwm.service;

import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Dish;
import com.glwm.entity.DishFlavor;

import java.util.List;

public interface DishService {
    //根据商户ID分页查询菜品
    List<DishAndCategory> pageDishAndCategory(Long merchantId, Integer page, Integer pageSize, String name);

    //根据商铺ID查询总条数
    Long selectDishTotal(Long merchantId, String name);

    //新增菜品以及口味信息
    void insertDishAndFlavor(DishAndCategory dishAndCategory);

    //根据ID批量修改菜品的状态信息
    void updateStatus(Integer status, Integer[] ids);

    //根据ID查询菜品信息
    DishAndCategory selectDishAndFlavor(Long id);

    //更新菜品以及菜品口味信息
    void updateDishAndFlavor(DishAndCategory dishAndCategory);

    //删除菜品信息
    void deleteDish(Long[] ids);

    //根据商铺ID查询所以菜品信息
    List<DishAndCategory> selectDishList(Long categoryId);

    //客户端根据菜品分类ID查询菜品
    List<DishAndCategory> selectClientDish(Long categoryId, Integer status);
}
