package com.example.demo1.utils;

import com.example.demo1.mapper.TokentableMapper;
import com.example.demo1.model.Tokentable;
import com.mysql.cj.result.IntegerValueFactory;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Interceptor implements HandlerInterceptor {

//    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private TokentableMapper tokentableMapper;
    @Autowired
    private Tokentable tokentable;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        if(id == null || token == null)
        {
            response.sendRedirect("/login");
            response.setStatus(401);
            return false;
        }

        tokentable = tokentableMapper.selectByPrimaryKey(Integer.valueOf(id));
        if(tokentable == null || !token.equals(tokentable.getToken()))
            return false;
        else
        {
            long nowTime = new Date().getTime();
            long preTime = tokentable.getStarttime().getTime();
            if(nowTime - preTime <= 86400000)
                return true;
            else
            {
                tokentableMapper.deleteByPrimaryKey(Integer.valueOf(id));
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    public static RedisTemplate getRedisTemplate(JedisConnectionFactory connectionFactory){

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);

        /**必须执行这个函数,初始化RedisTemplate*/
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
