package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.R;
import com.glwm.dto.SetmealAndCategory;
import com.glwm.entity.Setmeal;
import com.glwm.entity.User;
import com.glwm.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页以及模糊查询套餐信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageSetmeal(Integer page, Integer pageSize, String name) {
        //判断当前页
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        List<SetmealAndCategory> setmeals = setmealService.pageSetmeal(user.getMerchantId(), pageNum, pageSize, name);

        Page<SetmealAndCategory> setmealPage = new Page<>(page, pageSize);
        setmealPage.setRecords(setmeals);
        setmealPage.setTotal(setmealService.selectSetmealTotal(user.getMerchantId(), name));
        return R.success(setmealPage);
    }

    /**
     * 新增套餐
     * @param setmealAndCategory
     * @return
     */
    @PostMapping
    public R<String> insertSetmeal(@RequestBody SetmealAndCategory setmealAndCategory) {
        setmealService.insertSetmealAndDish(setmealAndCategory);
        return R.success("新增套餐成功");
    }

    /**
     * 根据ID更新套餐状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateSetmealStatus(@PathVariable Integer status, Long[] ids) {
        setmealService.updateSetmealStatus(status, ids);
        return R.success("状态修改成功");
    }

    /**
     * 根据ID查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealAndCategory> selectSetmeal(@PathVariable Long id) {
        SetmealAndCategory setmealAndCategory = setmealService.selectSetmeal(id);
        return R.success(setmealAndCategory);
    }

    /**
     * 修改套餐信息
     * @param setmealAndCategory
     * @return
     */
    @PutMapping
    public R<String> updateSetmealDish(@RequestBody SetmealAndCategory setmealAndCategory) {
        setmealService.updateSetmeal(setmealAndCategory);
        return R.success("修改套餐信息成功");
    }

    @DeleteMapping
    public R<String> deleteSetmeal(Long[] ids) {
        setmealService.deleteSetmel(ids);
        return R.success("删除套餐成功");
    }
}
