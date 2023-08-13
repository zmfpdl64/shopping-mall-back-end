package com.supercoding.shoppingmallbackend.controller.aop;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;


    @Before("execution(* com.supercoding.shoppingmallbackend..*Controller.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        String endpoint = request.getRequestURI(); // 현재 요청의 엔드포인트 경로를 얻어옴

        log.info("\u001B[34mAPI 호출! - "+endpoint + "\u001B[0m");
    }

    @AfterReturning(pointcut  = "execution(* com.supercoding.shoppingmallbackend..*Controller.*(..))",  returning = "returnValue")
    public void afterAdvice(JoinPoint joinPoint, Object returnValue) {
        String endpoint = request.getRequestURI(); // 현재 요청의 엔드포인트 경로를 얻어옴

        log.info("\u001B[32mAPI 호출 완료! - "+endpoint + " : "+ ((Status) ((CommonResponse) returnValue).getStatus()).getMessage() +"\u001B[0m");
    }

}
