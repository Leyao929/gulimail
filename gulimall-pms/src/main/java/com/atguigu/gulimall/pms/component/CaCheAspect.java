package com.atguigu.gulimall.pms.component;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.pms.annotation.GuliCache;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;


/**
 * 缓存的切面类  加入到ioc容器中
 */
@Aspect
@Component
public class CaCheAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 环绕通知:切入点表达式表示切入标了这个注解的方法
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.atguigu.gulimall.pms.annotation.GuliCache)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object result = null;

        String prefix = null;

        try {

            //目标方法执行前执行
            //拿到方法的所有参数
            Object[] args = point.getArgs();

            //拿到前缀
           MethodSignature methodSignature = (MethodSignature)point.getSignature();

            GuliCache annotation = methodSignature.getMethod().getAnnotation(GuliCache.class);

            if(annotation == null){

                return point.proceed(args);
            }


            //存到redis中的键
             prefix = annotation.prefix();

            if(args != null && args.length > 0){

                for (Object arg : args) {
                    prefix += ":" + arg.toString();
                }
            }

            //先查询缓存中有没有
            String s = redisTemplate.opsForValue().get(prefix);

            if(StringUtils.isEmpty(s)){
                //缓存中没有就查询数据库

                //加锁
                lock.lock();

                //二次判断
                s = redisTemplate.opsForValue().get(prefix);
                if(StringUtils.isEmpty(s)){
                    //缓存中没有
                    result = point.proceed(args);
                    //把结果放入缓存中
                    redisTemplate.opsForValue().set(prefix,JSON.toJSONString(result));
                }else{
                    //直接从缓存中拿
                    Class returnType = methodSignature.getReturnType();

                    result = JSON.parseObject(s,returnType);
                }




            }else{
                //缓存中有直接查询缓存中的
                String s1 = redisTemplate.opsForValue().get(prefix);

                Class returnType = methodSignature.getReturnType();

                result = JSON.parseObject(s1,returnType);
            }


        }catch(Exception e){

            //发生异常把缓存清空
            redisTemplate.delete(prefix);


        }finally {

            if(lock.isLocked()){
                lock.unlock();
            }

        }


        return result;
    }

}
