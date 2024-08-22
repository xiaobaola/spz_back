package com.spz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(10)
public class LogAspect {

    //配置切入点，即执行的位置
    @Pointcut("execution(* com.spz.*.controller.*.*(..))")
    public void pointCut() {
    }

    //使用环绕通知，在方法的执行前后分别记录一个时间相减
    @Around("pointCut()")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info(proceedingJoinPoint.getSignature().toShortString() + "方法执行了：" + (endTime - startTime) + "ms");
        }
        return object;
    }
}
