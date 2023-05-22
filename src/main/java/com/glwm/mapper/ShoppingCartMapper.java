package com.glwm.mapper;

import com.glwm.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    //客户端添加菜品到购物车
    void addShoppingCart(ShoppingCart shoppingCart);

    //查询菜品是否在购物车中
    ShoppingCart selectCount(Long dishId, Long setmealId, Long userId, Long merchantId);

    //更新菜品数量数据
    void updateDishNumber(Long dishId, Long setmealId, Integer number, Long userId);

    //查看购物车
    List<ShoppingCart> selectShoppingCart();

    //删除购物车菜品
    void deleteShoppingCartNumber(Long dishId, Long setmealId, Long userId);

    //根据用户ID查询购物车数据
    List<ShoppingCart> selectUserIDShoppingCart(Long id, Long merchantId);
}
