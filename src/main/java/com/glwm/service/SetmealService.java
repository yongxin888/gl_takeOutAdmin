package com.glwm.service;

import com.glwm.dto.SetmealAndCategory;
import com.glwm.entity.Setmeal;

import java.util.List;

public interface SetmealService {
    //根据商户ID分页查询套餐信息
    List<SetmealAndCategory> pageSetmeal(Long merchantId, Integer page, Integer pageSize, String name);

    //根据商户ID查询总条数
    Long selectSetmealTotal(Long merchantId, String name);

    //添加套餐以及对应的菜品信息
    void insertSetmealAndDish(SetmealAndCategory setmealAndCategory);

    //根据ID更新套餐状态
    void updateSetmealStatus(Integer status, Long[] ids);

    //根据ID查询套餐信息
    SetmealAndCategory selectSetmeal(Long id);

    //修改套餐信息
    void updateSetmeal(SetmealAndCategory setmealAndCategory);

    //根据ID批量删除套餐
    void deleteSetmel(Long[] ids);

    //客户端根据菜品分类ID查询套餐
    List<Setmeal> selectClientSetmeal(Long categoryId, Integer status);

    //客户端根据套餐ID查询菜品
    List<SetmealAndCategory> selectClientSetmealDish(Long id);
}
