package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zh
 * @Date: 2025/6/7 - 06 - 07 - 14:20
 * @Description: com.hmdp.utils
 * @version: 2.1
 */
public class LoginInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.前一个拦截器已经存入用户到ThreadLocal，这个拦截器直接从ThreadLocal获取用户判断拦截就行
        if(UserHolder.getUser()==null){
            //用户不存在，需要拦截，设置状态码
            response.setStatus(401);
            //拦截
            return false;
        }

        //放行
        return true;
    }


}