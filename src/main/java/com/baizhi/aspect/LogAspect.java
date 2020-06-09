package com.baizhi.aspect;

import com.baizhi.annotation.AddLog;
import com.baizhi.zcy.dao.LogDao;
import com.baizhi.zcy.entity.Admin;
import com.baizhi.zcy.entity.Log;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    LogDao logDao;
    @Resource
    HttpSession session;

    //环绕通知
    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint joinPoint) {
        System.out.println("=====进入切面编程");
        //谁  时间  操作   是否成功
        //获取管理员信息
        Admin admin = (Admin) session.getAttribute("admin");
        System.out.println("切面 admin = " + admin);

        //获取操作的方法
        String methodName = joinPoint.getSignature().getName();

        //获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("method = " + method);
        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);

        //获取注解中属性的值
        String description = addLog.description();

        Object proceed = null;
        String message = null;
        try {
            proceed = joinPoint.proceed();
            message = "Success";

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message = "Error";
        }

        Log log = new Log(UUID.randomUUID().toString(), admin.getAdmin_name(), new Date(), methodName + "(" + description + ")", message);
        //System.out.println("===管理员："+admin.getUsername()+" 时间："+date+" 操作方法："+methodName+" 是否成功:"+message);
        System.out.println("==日志信息入库" + log);
        logDao.addLog(log);
        return proceed;
    }

    //环绕通知
    //@Around("execution(* com.baizhi.zcn.serviceImpl.*.*(..)) && !execution(* com.baizhi.zcn.serviceImpl.*.query*(..))")
   /* public Object addLog(ProceedingJoinPoint joinPoint){

        //谁  时间  操作   是否成功

        //获取管理员信息
        Admin admin = (Admin) session.getAttribute("admin");

        //时间
        Date date = new Date();

        //获取操作的方法
        String methodName = joinPoint.getSignature().getName();

        Object proceed =null;
        String message=null;
        try {
            proceed = joinPoint.proceed();

            message="Success";

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message="Error";
        }

        System.out.println("===管理员："+admin.getAdmin_name()+" 时间："+date+" 操作方法："+methodName+" 是否成功:"+message);

        return proceed;
    }
*/
}






















