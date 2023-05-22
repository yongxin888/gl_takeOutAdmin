package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.R;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Category;
import com.glwm.entity.User;
import com.glwm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询分类数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageCategory(int page, int pageSize) {
        //判断当前页
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);

        List<Category> categories = categoryService.pageCategory(pageNum, pageSize, user.getMerchantId());
        Long categoryTotal = categoryService.categoryTotal(user.getMerchantId());

        //转换成Page格式
        Page<Category> categoryPage = new Page<>(page, pageSize);
        categoryPage.setRecords(categories);
        categoryPage.setTotal(categoryTotal);

        return R.success(categoryPage);
    }

    /**
     * 新增指定商家的菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> insertCategory(@RequestBody Category category) {
        categoryService.insertCategory(category);
        return R.success("新增菜品分类成功");
    }

    /**
     * 查询分类总条数
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return R.success("更新菜品分类成功");
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteCategory(Long ids) {
        categoryService.deleteCategory(ids);
        return R.success("删除菜品分类成功");
    }

    /**
     * 根据商铺ID查询菜品分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R<List<DishAndCategory>> categoryList(Integer type) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        List<DishAndCategory> dishAndCategories = categoryService.selectCategoryAndFlavor(user.getMerchantId(), type);
        return R.success(dishAndCategories);
    }
}
