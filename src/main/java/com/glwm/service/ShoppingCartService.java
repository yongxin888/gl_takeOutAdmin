package com.glwm.service;

import com.glwm.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    //客户端添加菜品到购物车
    void addShoppingCart(ShoppingCart shoppingCart);

    //查询菜品是否在购物车中
    ShoppingCart selectCount(Long dishId, Long setmealId, Long merchantId);

    //更新菜品数量数据
    void updateDishNumber(Long dishId, Long setmealId, Integer number);

    //查看购物车
    List<ShoppingCart> selectShoppingCart();

    //删除购物车菜品
    void deleteShoppingCartNumber(Long dishId, Long setmealId);

    //根据用户ID查询购物车数据
    List<ShoppingCart> selectUserIDShoppingCart(Long id, Long merchantId);
}
