package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.dto.SetmealAndCategory;
import com.glwm.entity.Setmeal;
import com.glwm.entity.SetmealDish;
import com.glwm.entity.User;
import com.glwm.mapper.SetmealMapper;
import com.glwm.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页并模糊查询套餐数据
     * @param merchantId
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public List<SetmealAndCategory> pageSetmeal(Long merchantId, Integer page, Integer pageSize, String name) {
        return setmealMapper.pageSetmeal(merchantId, page, pageSize, name);
    }

    /**
     * 查询套餐总记录数
     * @param merchantId
     * @param name
     * @return
     */
    @Override
    public Long selectSetmealTotal(Long merchantId, String name) {
        return setmealMapper.selectTotal(merchantId, name);
    }

    /**
     * 添加套餐以及对应的菜品信息
     * @param setmealAndCategory
     */
    @Override
    @Transactional
    public void insertSetmealAndDish(SetmealAndCategory setmealAndCategory) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        setmealAndCategory.setMerchantId(user.getMerchantId());

        setmealMapper.insertSetmeal(setmealAndCategory);

        List<SetmealDish> setmealDishes = setmealAndCategory.getSetmealDishes();
        setmealMapper.insertSetmealDish(setmealDishes, setmealAndCategory.getId());

    }

    /**
     * 根据ID更新套餐状态
     * @param status
     * @param ids
     */
    @Override
    public void updateSetmealStatus(Integer status, Long[] ids) {
        setmealMapper.updateSetmealStatus(status, ids);
    }

    /**
     * 根据ID查询套餐信息
     * @param id
     * @return
     */
    @Override
    public SetmealAndCategory selectSetmeal(Long id) {
        return setmealMapper.selectSetmeal(id);
    }

    /**
     * 修改套餐信息
     * @param setmealAndCategory
     */
    @Override
    @Transactional
    public void updateSetmeal(SetmealAndCategory setmealAndCategory) {
        setmealMapper.updateSetmeal(setmealAndCategory);
        setmealMapper.deleteSetmealDish(setmealAndCategory.getId());
        setmealMapper.insertSetmealDish(setmealAndCategory.getSetmealDishes(), setmealAndCategory.getId());
    }

    /**
     * 根据ID批量删除套餐
     * @param ids
     */
    @Override
    @Transactional
    public void deleteSetmel(Long[] ids) {
        setmealMapper.deleteSetmeal(ids);
        //遍历删除关联的套餐菜品信息
        for (Long id : ids) {
            setmealMapper.deleteSetmealDish(id);
        }
    }

    /**
     * 客户端根据菜品分类ID查询套餐
     * @return
     */
    @Override
    public List<Setmeal> selectClientSetmeal(Long categoryId, Integer status) {
        return setmealMapper.selectClientSetmeal(categoryId, status);
    }

    /**
     * 客户端根据套餐ID查询套餐中的菜品
     * @param id
     * @return
     */
    @Override
    public List<SetmealAndCategory> selectClientSetmealDish(Long id) {
        return setmealMapper.selectClientSetmealDish(id);
    }
}
