package com.atguigu.gmall.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 切面如何编写
 * 1、导入切面场景
 *         <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-aop</artifactId>
 *         </dependency>
 * 2、编写切面
 *      1）、@Aspect
 *      2）、切入点表达式
 *
 *
 *      3）、通知
 *             前置通知：方法执行之前触发
 *             后置通知：方法执行之后触发
 *             返回通知：方法正常返回之后触发
 *             异常通知：方法出现异常触发
 *
 *         正常执行：   前置通知==>返回通知==>后置通知
 *         异常执行：   前置通知==>异常通知==>后置通知
 *
 *             环绕通知：4合1；拦截方法的执行
 *
 */

//利用aop完成统一的数据校验，数据校验出错就返回给前端错误提示
    @Slf4j
@Aspect
@Component
public class DataVaildAspect {

    /**
     * 目标方法的异常，一般都需要再次抛出去。让别人感知
      * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.atguigu.gmall.admin..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint point) throws Throwable {
        Object proceed = null;

            log.debug("校验切面介入工作....");
            Object[] args = point.getArgs();
            for (Object obj:args){
                if(obj instanceof BindingResult){
                    BindingResult r = (BindingResult) obj;
                    if(r.getErrorCount()>0){
                        //框架自动校验检测到错了
                        return new CommonResult().validateFailed(r);
                    };
                }
            }
            //就是我们反射的  method.invoke();
            proceed = point.proceed(point.getArgs());
            log.debug("校验切面将目标方法已经放行....{}",proceed);

        return proceed;
    }

}
