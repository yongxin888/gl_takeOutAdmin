package com.glwm.controller;

import com.alibaba.fastjson2.JSON;
import com.glwm.common.R;
import com.glwm.entity.ShoppingCart;
import com.glwm.entity.User;
import com.glwm.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartClientController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(Long merchantId) {
        //获取Redis中的user信息  redisTemplate.opsForValue().get("userClient"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("userClient"), User.class);
        assert user != null;
        List<ShoppingCart> shoppingCarts = shoppingCartService.selectUserIDShoppingCart(user.getId(), merchantId);
        return R.success(shoppingCarts);
    }

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        //查询当前菜品是否在购物车中
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        Long merchantId = shoppingCart.getMerchantId();
        ShoppingCart aLong = shoppingCartService.selectCount(dishId, setmealId, merchantId);

        if (aLong != null) {
            //如果已经存在，就在原来数量基础上加一
            Integer number = aLong.getNumber();
            shoppingCartService.updateDishNumber(dishId, setmealId, number + 1);
        }else {
            //如果不存在。则添加到购物车中，数量默认为一
            shoppingCart.setNumber(1);
            shoppingCartService.addShoppingCart(shoppingCart);
            aLong = shoppingCart;
        }
        return R.success(aLong);
    }

    /**
     * 删除购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<List<ShoppingCart>> sub(@RequestBody ShoppingCart shoppingCart) {
        //查询当前菜品在购物车中的数量
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        Long merchantId = shoppingCart.getMerchantId();
        ShoppingCart aLong = shoppingCartService.selectCount(dishId, setmealId, merchantId);

        if (aLong != null) {
            //如果已经存在，且菜品数量为1时，删除菜品
            if (aLong.getNumber() == 1) {
                shoppingCartService.deleteShoppingCartNumber(dishId, setmealId);
            }else {
                Integer number = aLong.getNumber();
                shoppingCartService.updateDishNumber(dishId, setmealId, number - 1);
            }
        }
        List<ShoppingCart> shoppingCarts = shoppingCartService.selectShoppingCart();
        return R.success(shoppingCarts);
    }
}
