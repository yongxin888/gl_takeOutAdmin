package com.glwm.controller;

import com.glwm.common.R;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Dish;
import com.glwm.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/dish")
public class DishClientController {

    @Autowired
    private DishService dishService;

    /**
     * 客户端根据菜品分类ID查询菜品
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public R<List<DishAndCategory>> selectClientDish(Long categoryId, Integer status) {
        List<DishAndCategory> dishes = dishService.selectClientDish(categoryId, status);
        return R.success(dishes);
    }
}
