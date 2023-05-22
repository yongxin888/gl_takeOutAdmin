package com.glwm.service;

import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Category;
import com.glwm.entity.Merchants;

import java.util.List;

public interface CategoryService {
    //分页查询分类
    List<Category> pageCategory(Integer page, Integer pageSize, Long merchantsId);

    //新增分类
    void insertCategory(Category category);

    //更新菜品
    void updateCategory(Category category);

    //查询分类总条数
    Long categoryTotal(Long merchantsId);

    //根据ID删除菜品分类
    void deleteCategory(Long ids);

    //根据商铺ID查询菜品分类
    List<DishAndCategory> selectCategoryAndFlavor(Long merchantId, Integer type);

    //客户端根据商铺ID查询菜品所有分类
    List<Category> selectClientCategory(Long merchantId);

    //客户端获取商铺信息
    Merchants selectMerchants(Long id);
}
