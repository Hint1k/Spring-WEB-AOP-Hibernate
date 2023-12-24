package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    //private Logger logger = Logger.getLogger(getClass().getName());
    @Pointcut("execution(* com.example.controllers.*.*(..))")
    private void forControllerPackage(){
    }
    @Pointcut("execution(* com.example.service.*.*(..))")
    private void forServicePackage(){
    }
    @Pointcut("execution(* com.example.dao.*.*(..))")
    private void forDaoPackage(){
    }
    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){
    }
    @Before("forAppFlow()")
    public void before (JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        System.out.println("===>>> in @Before - calling method: " + method);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args){
            System.out.println("===>>> args:"+arg);
        }
    }
    @AfterReturning(pointcut = "forAppFlow()", returning="result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        String method = joinPoint.getSignature().toShortString();
        System.out.println("===>>> in @AfterReturning - calling method: " + method);
        System.out.println("===>>> result: " +  result);
    }
}
