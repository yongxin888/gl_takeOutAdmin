package com.glwm.mapper;

import com.glwm.dto.SetmealAndCategory;
import com.glwm.entity.Setmeal;
import com.glwm.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {
    //根据商户ID分页查询套餐信息
    List<SetmealAndCategory> pageSetmeal(Long merchantId, Integer page, Integer pageSize, String name);

    //根据商户ID查询套餐总条数
    Long selectTotal(Long merchantId, String name);

    //根据商户ID新增套餐
    void insertSetmeal(SetmealAndCategory setmealAndCategory);

    //根据套餐ID批量新增菜品口味
    void insertSetmealDish(List<SetmealDish> setmealDishList, Long setmealId);

    //根据ID更新套餐状态
    void updateSetmealStatus(Integer status, Long[] ids);

    //根据ID查询套餐信息
    SetmealAndCategory selectSetmeal(Long id);

    //根据ID修改套餐信息
    void updateSetmeal(SetmealAndCategory setmealAndCategory);

    //根据套餐ID删除菜品
    void deleteSetmealDish(Long id);

    //根据ID批量删除套餐
    void deleteSetmeal(Long[] ids);

    //客户端根据菜品分类ID查询套餐
    List<Setmeal> selectClientSetmeal(Long categoryId, Integer status);

    //客户端根据套餐ID查询套餐中的菜品
    List<SetmealAndCategory> selectClientSetmealDish(Long id);
}
