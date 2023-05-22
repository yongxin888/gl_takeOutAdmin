package com.glwm.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glwm.common.FileUtils;
import com.glwm.common.MD5Utils;
import com.glwm.common.R;
import com.glwm.dto.MerchantsAndUser;
import com.glwm.dto.MerchantsAndUserPage;
import com.glwm.entity.Merchants;
import com.glwm.entity.User;
import com.glwm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 商户管理
 */
@Controller
@RequestMapping("/employee")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录
     * @param user
     * @ResponseBody 这个注解， 就表明该方法的返回值直接写入到 HTTP Response Body 中。
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R<User> Login(@RequestBody User user) {
        //MD5加密

        String code = MD5Utils.code(user.getPassword());
        User loginUser = userService.Login(user.getUsername(), code);

        //设置用户登录唯一标识(序列化)
        redisTemplate.opsForValue().set("user", JSON.toJSONString(loginUser));

        //如果是商家则登录
        if (loginUser != null && loginUser.getRole() != 2) {
            return R.success(loginUser);
        }

        return R.error("登录失败!您不是商家或用户名密码错误!");
    }

    /**
     * 分页查询商户
     * @return
     */
    @GetMapping("/page")
    @ResponseBody
    public R<Page> pageMerchants(String name, int page, int pageSize) {
        int pageNum = page;
        if (pageNum <= 0) {
            pageNum = 0;
        }else {
            pageNum = (page - 1 ) * pageSize;
        }

        //获取Redis中的user信息  redisTemplate.opsForValue().get("user"); 并反序列化
        User user = JSON.parseObject((String) redisTemplate.opsForValue().get("user"), User.class);
        assert user != null;
        if (!Objects.equals(user.getUsername(), "admin")) {
            return R.error("您不是管理员，无法查看商家信息");
        }

        List<MerchantsAndUserPage> merchantsAndUserPages = userService.pageMerchants(name, pageNum, pageSize);

        Page<MerchantsAndUserPage> userPage = new Page<>(page, pageSize);
        userPage.setRecords(merchantsAndUserPages);
        userPage.setTotal(userService.pageTotal(name));

        return R.success(userPage);
    }

    /**
     * 新增商户
     * @param merchantsAndUser
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<String> insert(@RequestBody MerchantsAndUser merchantsAndUser) {
        userService.insertUserAndMerchants(merchantsAndUser);
        return R.success("新增商户成功");
    }

    /**
     * 根据ID查询商户数据
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public R<MerchantsAndUser> update(@PathVariable Long id) {
        MerchantsAndUser merchantsAndUser = userService.selectMerchantsAndUser(id);
        if (merchantsAndUser != null) {
            return R.success(merchantsAndUser);
        }
        return R.error("没有查询到相应的商户信息");
    }

    /**
     * 根据ID修改状态
     * @param merchants
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<String> putStatus(@RequestBody Merchants merchants) {
        userService.updateStatus(merchants);
        return R.success("修改成功");
    }

    /**
     * 用户退出登录
     * @return
     */
    @PostMapping("/logout ")
    public R<String> outUser() {
        redisTemplate.delete("user");
        return R.success("退出成功");
    }
}
