package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.R;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Category;
import com.glwm.entity.User;
import com.glwm.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询菜品分页以及模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageDishAndCategory(Integer page, Integer pageSize, String name) {
        //判断当前页
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);

        List<DishAndCategory> dishAndCategories = dishService.pageDishAndCategory(user.getMerchantId(), pageNum, pageSize, name);
        Long total = dishService.selectDishTotal(user.getMerchantId(), name);

        //转换成page对象
        Page<DishAndCategory> dishAndCategoryPage = new Page<>(page, pageSize);
        dishAndCategoryPage.setRecords(dishAndCategories);
        dishAndCategoryPage.setTotal(total);

        return R.success(dishAndCategoryPage);
    }

    /**
     * 新增菜品以及口味信息
     * @param dishAndCategory
     * @return
     */
    @PostMapping
    public R<String> insertDish(@RequestBody DishAndCategory dishAndCategory) {
        dishService.insertDishAndFlavor(dishAndCategory);
        return R.success("新增菜品成功");
    }

    /**
     * 批量修改菜品状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Integer[] ids) {
        dishService.updateStatus(status, ids);
        return R.success("修改菜品状态成功");
    }

    /**
     * 根据ID查询菜品以及口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishAndCategory> selectDishAndFlavor(@PathVariable Long id) {
        DishAndCategory dishAndCategory = dishService.selectDishAndFlavor(id);
        return R.success(dishAndCategory);
    }

    /**
     * 修改菜品信息
     * @param dishAndCategory
     * @return
     */
    @PutMapping
    public R<String> updateDishAndFlavor(@RequestBody DishAndCategory dishAndCategory) {
        dishService.updateDishAndFlavor(dishAndCategory);
        return R.success("修改菜品信息成功");
    }

    /**
     * 删除菜品信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteDish(Long[] ids) {
        dishService.deleteDish(ids);
        return R.success("删除菜品信息成功");
    }

    /**
     * 根据菜品分类ID查询套餐
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public R<List<DishAndCategory>> selectDishList(Long categoryId) {
        List<DishAndCategory> dishAndCategories = dishService.selectDishList(categoryId);
        return R.success(dishAndCategories);
    }
}
