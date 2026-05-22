package com.dixie.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *) && !execution(void deleteExpiredImagerPosts())")
    public void serviceLayer() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerLayer() {
    }

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("ENTER | {}.{} | Args:{}", className, methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterRunning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("EXIT | {}.{} | Result:{}", className, methodName, result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.error("ERROR | {}.{} | Exception:{}", className, methodName, ex.getMessage(), ex);
    }

    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("FINISHED | {}.{} | Time: {} ms", className, methodName, duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            log.error("FAILED | {}.{} | Time: {} ms | Exception:{}",
                    className, methodName, duration, ex.getMessage(), ex);
            throw ex;
        }
    }
}
