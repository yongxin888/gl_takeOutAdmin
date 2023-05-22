package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Dish;
import com.glwm.entity.DishFlavor;
import com.glwm.entity.User;
import com.glwm.mapper.DishMapper;
import com.glwm.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据商户ID分页查询菜品
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<DishAndCategory> pageDishAndCategory(Long merchantId, Integer page, Integer pageSize, String name) {

        List<DishAndCategory> dishAndCategories = dishMapper.pageDishAndCategory(merchantId, page, pageSize, name);

        return dishAndCategories;
    }

    /**
     * 根据商铺ID查询总条数
     * @param merchantId
     * @return
     */
    @Override
    public Long selectDishTotal(Long merchantId, String name) {
        return dishMapper.selectDishTotal(merchantId, name);
    }

    /**
     * 新增菜品以及口味信息
     * @param dishAndCategory
     */
    @Override
    @Transactional
    public void insertDishAndFlavor(DishAndCategory dishAndCategory) {
        dishAndCategory.setSort(1);
        dishAndCategory.setCreateTime(new Date());
        dishAndCategory.setUpdateTime(new Date());

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        dishAndCategory.setMerchantId(user.getMerchantId());
        dishMapper.insertDish(dishAndCategory);

        //获取菜品口味信息
        List<DishFlavor> dishFlavors = dishAndCategory.getFlavors();

        //循环遍历菜品口味信息
        dishFlavors.forEach(item -> {
            item.setDishId(dishAndCategory.getId());
            item.setCreateTime(new Date());
            item.setUpdateTime(new Date());
            dishMapper.insertDishFlavor(item);
        });
    }

    /**
     * 根据ID批量修改菜品的状态信息
     * @param status
     * @param ids
     */
    @Override
    public void updateStatus(Integer status, Integer[] ids) {
        dishMapper.updateStatus(status, ids);
    }

    /**
     * 根据ID查询菜品信息
     * @param id
     * @return
     */
    @Override
    public DishAndCategory selectDishAndFlavor(Long id) {
        return dishMapper.selectDishAndFlavor(id);
    }

    /**
     * 更新菜品以及菜品口味信息
     * @param dishAndCategory
     */
    @Override
    @Transactional
    public void updateDishAndFlavor(DishAndCategory dishAndCategory) {
        dishAndCategory.setUpdateTime(new Date());
        dishMapper.updateDish(dishAndCategory);

        //获取菜品口味信息
        List<DishFlavor> dishFlavors = dishAndCategory.getFlavors();

        dishMapper.deleteDishFlavor(dishAndCategory.getId());
        dishMapper.insertDishFlavors(dishFlavors, dishAndCategory.getId());
    }

    /**
     * 批量删除菜品信息以及其口味信息
     * @param ids
     */
    @Override
    @Transactional
    public void deleteDish(Long[] ids) {
        dishMapper.deleteDish(ids);
        //遍历数组删除菜品的口味信息
        for (Long id : ids) {
            dishMapper.deleteDishFlavor(id);
        }
    }

    /**
     * 根据商铺ID查询所以菜品信息
     * @return
     */
    @Override
    public List<DishAndCategory> selectDishList(Long categoryId) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        return dishMapper.selectDishList(user.getMerchantId(), categoryId);
    }

    /**
     * 客户端根据菜品分类ID查询菜品
     * @param categoryId
     * @param status
     * @return
     */
    @Override
    public List<DishAndCategory> selectClientDish(Long categoryId, Integer status) {
        return dishMapper.selectClientDish(categoryId, status);
    }

}
