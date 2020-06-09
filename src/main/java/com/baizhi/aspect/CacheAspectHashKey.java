package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import javax.annotation.Resource;

@Aspect
@Configuration
public class CacheAspectHashKey {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //加入缓存
    //@Around("execution(* com.baizhi.zcn.serviceImpl.*.query*(..))")
    //@Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object AddCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("===进入环绕通知===");

        /*序列化化解决乱码*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //判断时候有缓存
        //根据key获取缓存数据
        //key=类的全限定名+方法名+实参  value=数据

        //com.baizhi.zcn.serviceImpl
        //KEY,key,value

        //com.baizhi.zcn.serviceImpl      queryAllPage,数据
        //queryOne,数据

        StringBuilder sb = new StringBuilder();

        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName); //拼接方法名

        //获取实参
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg); //拼接实参
        }

        //获取拼接好的key
        String key = sb.toString();

        HashOperations hash = redisTemplate.opsForHash();

        //判断key是否存在
        Boolean aBoolean = hash.hasKey(className, key);
        Object result = null;
        if (aBoolean) {
            //存在   有缓存   直接取出缓存返回数据
            result = hash.get(className, key);
        } else {
            //不存在  没有缓存
            //放行切到的方法，继续执行方法代码，知道返回结果
            result = proceedingJoinPoint.proceed();
            //查询结果加入缓存
            hash.put(className, key, result);
        }
        return result;
    }

    //清除缓存的方法   通知
    //@After("execution(* com.baizhi.zcn.serviceImpl.*.*(..)) && !execution(* com.baizhi.zcn.serviceImpl.*.query*(..)) ")
    //@After("@annotation(com.baizhi.annotation.DelCache)")
    public void DelCache(JoinPoint joinPoint) {

        System.out.println("===后置通知 ==清除缓存===");

        //com.baizhi.zcn.serviceImpl  根据类的全限定名清除缓存
        String className = joinPoint.getTarget().getClass().getName();

        //删除Key
        stringRedisTemplate.delete(className);

    }

}
