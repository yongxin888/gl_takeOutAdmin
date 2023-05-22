package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.glwm.common.R;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Category;
import com.glwm.entity.Merchants;
import com.glwm.entity.User;
import com.glwm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/category")
public class CategoryClientController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据商铺ID查询菜品分类
     * @param
     * @return
     */
    @GetMapping("/list/{merchantId}")
    public R<List<Category>> categoryList(@PathVariable Long merchantId) {
        List<Category> categories = categoryService.selectClientCategory(merchantId);
        return R.success(categories);
    }

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    @GetMapping("/merchant/{id}")
    public R<Merchants> selectMerchants(@PathVariable Long id) {
        Merchants merchants = categoryService.selectMerchants(id);
        return R.success(merchants);
    }
}
