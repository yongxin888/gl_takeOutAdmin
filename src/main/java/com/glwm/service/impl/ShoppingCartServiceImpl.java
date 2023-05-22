package com.glwm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.glwm.entity.ShoppingCart;
import com.glwm.entity.User;
import com.glwm.mapper.ShoppingCartMapper;
import com.glwm.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 客户端添加菜品到购物车
     *
     * @param shoppingCart
     */
    @Override
    public void addShoppingCart(ShoppingCart shoppingCart) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        assert user != null;
        shoppingCart.setUserId(user.getId());
        shoppingCartMapper.addShoppingCart(shoppingCart);
    }

    /**
     * 查询菜品是否在购物车中
     * @param dishId
     * @param setmealId
     * @return
     */
    @Override
    public ShoppingCart selectCount(Long dishId, Long setmealId, Long merchantId) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        return shoppingCartMapper.selectCount(dishId, setmealId, user.getId(), merchantId);
    }

    /**
     * 更新菜品数量数据
     * @param dishId
     * @param setmealId
     * @param number
     */
    @Override
    public void updateDishNumber(Long dishId, Long setmealId, Integer number) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        shoppingCartMapper.updateDishNumber(dishId, setmealId, number, user.getId());
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> selectShoppingCart() {
        return shoppingCartMapper.selectShoppingCart();
    }

    /**
     * 删除购物车菜品
     * @param dishId
     * @param setmealId
     */
    @Override
    public void deleteShoppingCartNumber(Long dishId, Long setmealId) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        shoppingCartMapper.deleteShoppingCartNumber(dishId, setmealId,user.getId());
    }

    /**
     * 根据用户ID查询购物车数据
     * @param id
     * @return
     */
    @Override
    public List<ShoppingCart> selectUserIDShoppingCart(Long id, Long merchantId) {
        return shoppingCartMapper.selectUserIDShoppingCart(id, merchantId);
    }
}
