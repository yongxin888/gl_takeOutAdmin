package com.glwm.mapper;

import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Dish;
import com.glwm.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    //根据商铺ID以及菜品ID查询分页菜品
    List<DishAndCategory> pageDishAndCategory(Long merchantId, Integer page, Integer pageSize, String name);

    //根据商铺ID查询总条数
    Long selectDishTotal(Long merchantId, String name);

    //新增菜品信息
    void insertDish(Dish dish);

    //新增菜品口味信息
    void insertDishFlavor(DishFlavor dishFlavor);

    //批量新增菜品口味信息
    void insertDishFlavors(List<DishFlavor> list, Long dishId);

    //根据状态批量修改数据
    void updateStatus(Integer status, Integer[] ids);

    //根据ID查询菜品和口味
    DishAndCategory selectDishAndFlavor(Long id);

    //修改菜品信息
    void updateDish(DishAndCategory dishAndCategory);

    //删除菜品口味信息
    void deleteDishFlavor(Long id);

    //批量删除菜品信息
    void deleteDish(Long[] ids);

    //根据商铺ID查询所有菜品信息
    List<DishAndCategory> selectDishList(Long merchantId, Long categoryId);

    //客户端根据菜品分类ID查询菜品
    List<DishAndCategory> selectClientDish(Long categoryId, Integer status);
}
