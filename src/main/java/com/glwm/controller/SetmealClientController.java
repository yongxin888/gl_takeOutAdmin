package com.glwm.controller;

import com.glwm.common.R;
import com.glwm.dto.SetmealAndCategory;
import com.glwm.entity.Setmeal;
import com.glwm.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/setmeal")
public class SetmealClientController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 客户端根据菜品分类ID查询套餐
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> selectSetmealList(Long categoryId, Integer status) {
        List<Setmeal> setmeals = setmealService.selectClientSetmeal(categoryId, status);
        return R.success(setmeals);
    }

    /**
     * 客户端根据套餐ID查询菜品
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    public R<List<SetmealAndCategory>> selectClientDish(@PathVariable Long id) {
        List<SetmealAndCategory> setmealAndCategories = setmealService.selectClientSetmealDish(id);
        return R.success(setmealAndCategories);
    }

}
