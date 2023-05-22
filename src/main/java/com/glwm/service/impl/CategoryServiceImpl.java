package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.dto.DishAndCategory;
import com.glwm.entity.Category;
import com.glwm.entity.Merchants;
import com.glwm.entity.User;
import com.glwm.mapper.CategoryMapper;
import com.glwm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询指定商家的菜品分类
     * @param page
     * @param pageSize
     * @param merchantsId
     * @return
     */
    @Override
    public List<Category> pageCategory(Integer page, Integer pageSize, Long merchantsId) {
        return categoryMapper.pageCategory(page, pageSize, merchantsId);
    }

    /**
     * 新增指定商家的菜品分类
     * @param category
     */
    @Override
    public void insertCategory(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        category.setMerchantId(user.getMerchantId());
        categoryMapper.insertCategory(category);
    }

    /**
     * 更新菜品分类
     * @param category
     */
    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(new Date());
        categoryMapper.updateCategory(category);
    }

    /**
     * 查询分类总条数
     * @param merchantsId
     * @return
     */
    @Override
    public Long categoryTotal(Long merchantsId) {
        return categoryMapper.categoryTotal(merchantsId);
    }

    /**
     * 根据ID删除菜品分类
     * @param ids
     */
    @Override
    public void deleteCategory(Long ids) {
        categoryMapper.deleteCategory(ids);
    }

    /**
     * 根据商铺ID查询菜品分类
     * @param merchantId
     * @param type
     * @return
     */
    @Override
    public List<DishAndCategory> selectCategoryAndFlavor(Long merchantId, Integer type) {
        return categoryMapper.selectCategoryAndFlavor(merchantId, type);
    }

    /**
     * 客户端根据商铺ID查询菜品所有分类
     * @param merchantId
     * @return
     */
    @Override
    public List<Category> selectClientCategory(Long merchantId) {
        return categoryMapper.selectClientCategory(merchantId);
    }

    /**
     * 客户端获取商铺信息
     * @param id
     * @return
     */
    @Override
    public Merchants selectMerchants(Long id) {
        return categoryMapper.selectMerchants(id);
    }

}
