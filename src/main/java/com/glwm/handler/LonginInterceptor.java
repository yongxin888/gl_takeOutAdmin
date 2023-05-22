package com.glwm.handler;

import com.glwm.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LonginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        System.out.println("执行了preHandle方法");
        try {
            //统一拦截,查询当前session是否存在user,这里user每次登录后会存入session
            User user = (User)request.getSession().getAttribute("user");
            if (user != null){
                return true;
            }
            response.sendRedirect("http://localhost:9090/page/login/login.html");//对拦截到的进行重定向
            //response.sendRedirect(request.getContextPath()+"/admin/login");
        }catch (IOException io){
            io.printStackTrace();
        }
        return false;//如果设置为false时,被请求时,拦截器执行到此不再执行,相反会继续执行下面的操作
    }

}
